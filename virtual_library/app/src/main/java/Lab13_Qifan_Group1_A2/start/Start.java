package Lab13_Qifan_Group1_A2.start;

import java.lang.String;
import java.util.ArrayList;

import Lab13_Qifan_Group1_A2.AppState;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.parsing.*;
import Lab13_Qifan_Group1_A2.users.*;
import Lab13_Qifan_Group1_A2.hasher.*;

/* Start
 * Includes all functions related to the starting screen a user
 * would face upon program startup
 */
public class Start extends AppState {

	public Start(Parser parser) {
		super(parser);
	}

	@Override
	public void displayScreen() {
		System.out.print(String.join(NEWLINE,
		"====================================================================",
		"-- Choose a Login Option --",
		"> Log in as Guest\t(guest)",
		"> Log in as User\t(login)",
		"> Register\t\t(register)",
		"> Exit\t\t\t(exit)",
		"====================================================================", NEWLINE));
	}

	/* register
	 * Users can register a new account.
	 * Users will be sent back to the Start interface if
	 * their inputs are empty.
	 * @return the new user, if successful.
	 */
	@Override
	public User register() {
		ArrayList<String> param = parser.getParameter(Command.register);
		for (int i = 0; i < param.size()-1; i++) {
			if (param.get(i).length() == 0 || param.get(i).equals(NEWLINE)) {
				System.out.println("Inputs cannot be empty!" + NEWLINE);
				return null;
			}
		}
		if (UserTable.idExists(param.get(0))) {
			System.out.println("ID already exists! Please choose another one" + NEWLINE);
		}
		else if (UserTable.userExists(param.get(1))) {
			System.out.println("Username already exists! Please choose another one" + NEWLINE);
		}
		else {
			User newUser = new RegisteredUser(param.get(0), param.get(1), 
										  param.get(3), param.get(4), 
										  param.get(5), param.get(6), 0);
			UserTable.insertUser(newUser, Hasher.createHash(param.get(2)));
			System.out.println("Registration successful!" + NEWLINE);
			return newUser;
		}
		return null;
	}

	/* guestLogin
	 * Simply access the library without any other inputs.
	 * @return the guest account.
	 */
	@Override
	public User guestLogin() {
		System.out.println("Welcome to the Library of Agility, guest" + NEWLINE);
		return UserTable.getUser("guest");
	}

	/* registeredLogin
	 * Registered users can attempt to login. If they input
	 * the incorrect details, they will just get sent back to
	 * the Start interface.
	 * @return the associated user, if successful.
	 */
	@Override
	public User registeredLogin() {
		ArrayList<String> param = parser.getParameter(Command.login);
		String name = param.get(0);
		String pass = Hasher.createHash(param.get(1));
		User user = UserTable.getUser(name);
		if (user != null) {
			if (UserTable.getPassword(name).equals(pass)) {
				System.out.printf("Welcome to the Library of Agility, %s" + NEWLINE, name);
				return user;
			}
			System.out.println("Incorrect password!" + NEWLINE);
		}
		else {
			System.out.println("Username does not exist!" + NEWLINE);
		}
		return null;
	}

	// Exiting the program
	@Override
	public void exit() {
		parser.closeScanner();
		System.out.println("See you next time!");
		System.exit(0);
	}
}