
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'main.dart'; 
import 'dart:async'; 
import 'package:crypto/crypto.dart';
import 'dart:convert';
import 'login_page.dart'; 

class UserChange extends StatefulWidget {
  final String userId;
  String password = 'hello';

  UserChange({required this.userId});

  @override
  _UserChangeState createState() => _UserChangeState();
}

String hashPassword(String password) {
  // SHA-256 for hashing
  var bytes = utf8.encode(password);
  var hashedPassword = sha256.convert(bytes).toString();
  return hashedPassword;
}



class _UserChangeState extends State<UserChange> {

  TextEditingController firstNameController = TextEditingController();
  TextEditingController emailAddressController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController confirmPasswordController = TextEditingController();

  String errorMessage = '';

  

  bool isValidEmail(String email) {
  // Using regex for basic email validation
    RegExp emailRegex = RegExp(r'^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$');
    return emailRegex.hasMatch(email);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<DocumentSnapshot>(
      future: FirebaseFirestore.instance.collection('users').doc(widget.userId).get(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return CircularProgressIndicator();
        } else if (snapshot.hasError) {
          return Text('Error: ${snapshot.error}');
        } else if (!snapshot.hasData || !snapshot.data!.exists) {
          print(widget.userId);
          print(snapshot.hasData);
          print(snapshot.data!.exists);
          return Text('Document does not exist.cccc');
        } else {
          Map<String, dynamic> data = snapshot.data!.data() as Map<String, dynamic>;
          String email = data['email'];
          emailAddressController.text = email;
          String firstName = data['firstname'];
          firstNameController.text = firstName;
          String password = data['password'];
          widget.password = password;


          return Scaffold(
            // appBar: AppBar(
            //   title: const Text('Sign Up'),
            // ),
            appBar: AppBar(
              title: Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 8.0),
                    child: Image.asset(
                      'assets/logo.png',
                      width: 60,
                      height: 60,
                    ),
                  ),
                  const Text(
                    'ReVibe',
                    style: TextStyle(
                      fontSize: 20,
                    ),
                  ),
                ],
              ),
              backgroundColor: const Color.fromRGBO(221, 242, 232, 1),
            ),
            
            body: Container(
              color: const Color(0xFFF0F3EF),
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(20.0, 10.0, 10.0, 10.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Text(
                        'Sign Up',
                        style: GoogleFonts.workSans(
                          textStyle: const TextStyle(
                            fontSize: 35,
                            fontWeight: FontWeight.bold,
                            color: Color(0xFF107208),
                            height: 1, 
                          ),
                        ),
                      ),
                      const SizedBox(height: 75.0),

                      TextField(
                        controller: firstNameController,
                        decoration: InputDecoration(labelText: 'First Name'),
                      ),
                      const SizedBox(height: 16),
                      TextField(
                        controller: emailAddressController,
                        decoration: InputDecoration(labelText: 'Email Address'),
                      ),
                      const SizedBox(height: 16),
                      TextField(
                        controller: passwordController,
                        decoration: InputDecoration(labelText: 'Password (leave empty if use old password)'),
                        obscureText: true,
                      ),
                      const SizedBox(height: 16),
                      TextField(
                        controller: confirmPasswordController,
                        decoration: InputDecoration(labelText: 'Confirm Password (leave empty if use old password)'),
                        obscureText: true,
                      ),
                      const SizedBox(height: 16),

                      ElevatedButton(
                        onPressed: () async {
                          // form submission logic here
                          String firstName = firstNameController.text;
                          String emailAddress = emailAddressController.text;
                          String password = passwordController.text;
                          String confirmPassword = confirmPasswordController.text;

                      
                          print('test: $confirmPassword $password');

                          // Validate the input
                          if (firstName.isEmpty || emailAddress.isEmpty) {
                            
                            setState(() {
                              errorMessage = 'Name and email is required';
                            });
                            return;
                          }

                          if (!isValidEmail(emailAddress)) {
                         
                            setState(() {
                              errorMessage = 'Invalid email address';
                            });
                            return;
                          }

                          if (password != confirmPassword) {
                        
                            setState(() {
                              errorMessage = 'Passwords do not match';
                            });
                            return;
                          }
                           String hashedPassword ="helllo";
                          // Hash the password
                          if (confirmPassword.isEmpty && password.isEmpty){
                            hashedPassword = widget.password;
                          }
                          else{
                            hashedPassword = hashPassword(password);
                          }

                      
                          await _submitToFirebase(context, firstName, emailAddress, hashedPassword);

                        },
                        style: ButtonStyle(
                          backgroundColor: MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
                            if (states.contains(MaterialState.hovered)) {
                              return const Color(0xFF107208); 
                            }
                            return Colors.white;
                          }),
                          foregroundColor: MaterialStateProperty.resolveWith<Color>((Set<MaterialState> states) {
                            if (states.contains(MaterialState.hovered)) {
                              return Colors.white; 
                            }
                            return Colors.black;
                          }),
                          minimumSize: MaterialStateProperty.all<Size>(const Size(300, 50)),
                        ),
                        child: const Text('Submit'),
                      ),

                      if (errorMessage.isNotEmpty)
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Text(
                            errorMessage,
                            style: const TextStyle(color: Colors.red),
                          ),
                      ),

                    ],
                  ),
                ),
              )
            )
          );
        }
      }
    );
  }


  Future<void> _submitToFirebase(
      BuildContext context, String firstName, String emailAddress, String password) async {
   
    final collection = FirebaseFirestore.instance.collection('users');
    try {
      await collection.doc(widget.userId).set(
        {
          'timestamp': FieldValue.serverTimestamp(),
          'firstname': firstName,
          'email': emailAddress,
          'password': password,

        },
      );
      // ignore: use_build_context_synchronously
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => LoginPage(userType: 1),
        ),
      );
    } catch (error) {
  
      setState(() {
        errorMessage = 'Error submitting to Firebase: $error';
      });
      
    }

  }
}

