package Lab13_Qifan_Group1_A2.home;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.parsing.*;
import Lab13_Qifan_Group1_A2.users.*;
import Lab13_Qifan_Group1_A2.hasher.*;


/* AdminHome
 * Includes all functions related to the admin home screen
 * Because admins can access all the features a user has
 * access to, it extends UserHome
 */
public class AdminHome extends RegisteredHome {

	public AdminHome(Parser parser) {
		super(parser);
	}

	@Override
	public void displayScreen()
	{
		System.out.print(String.join(NEWLINE,
		"-- Home --",
		"> Add scroll\t\t(addScroll)",
		"> Add new user\t\t(addAccount)",
		"> Remove user\t\t(removeAccount)",
		"> View user profiles\t(viewProfiles)",
		"> View total uploads\t(viewUploads)",
		"> view downloads\t(viewDownloads)",
		"> View scrolls\t\t(viewScrolls)",
		"> Remove scroll\t\t(removeScroll)",
		"> Edit scroll\t\t(editScroll)",
		"> Download scrolls\t(downloadScroll)",
		"> Edit user details\t(editDetails)",
		"> Search scrolls\t(searchScroll)",
		"> Preview scrolls\t(preview)",
		"> Logout\t\t(logout)",
		"====================================================================", NEWLINE));
	}

	public void displayAllUsers() {
		System.out.print("Total users: " + UserTable.userCount() + NEWLINE);

		System.out.print("UserID\t\t\tUsername\t\tFirstName\t\tLastName\t\temail\t\t\t\tphoneNumber\t\t\tUploadCount"+NEWLINE);
		for (User user : UserTable.getAllUsers()) {
			System.out.print(user.getUserID() + "\t\t\t" + 
			user.getUsername() + "\t\t\t" + 
			user.getFirstName() + "\t\t\t" + 
			user.getLastName() +  "\t\t\t" + 
			user.getEmail() +  "\t\t\t" + 
			user.getPhoneNumber() +  "\t\t\t" + 
			user.getUploadCount() + 
			System.lineSeparator());
		}
		System.out.print(NEWLINE+NEWLINE);
	}

	public void addAccount() {
		ArrayList<String> param = parser.getParameter(Command.register);
		for (int i = 0; i < param.size()-1; i++) {
			if (param.get(i).length() == 0 || param.get(i).equals(System.lineSeparator())) {
				System.out.println("Inputs cannot be empty!"+NEWLINE);
				return;
			}
		}
		if (UserTable.idExists(param.get(0))) {
			System.out.println("ID already exists! Please choose another one"+NEWLINE);
		}
		else if (UserTable.userExists(param.get(1))) {
			System.out.println("Username already exists! Please choose another one"+NEWLINE);
		}
		else {
			User newUser = new RegisteredUser(param.get(0), param.get(1), 
										  param.get(3), param.get(4), 
										  param.get(5), param.get(6), 0);
			UserTable.insertUser(newUser, Hasher.createHash(param.get(2)));
			System.out.println("Registration successful!"+NEWLINE);
		}
	}

	public void removeAccount(String selfUsername) {
		String username = parser.getParameter(Command.removeAccount).get(0);

		if (UserTable.userExists(username)) {
			User toDelete = UserTable.getUser(username);

			if (username.equals(selfUsername)) {
			System.out.println("You cannot delete yourself!");
			}
			else if (toDelete instanceof Wizard) {
				System.out.println("You cannot delete the wizard account!");
			}
			else if (toDelete instanceof Admin) {
				System.out.println("You cannot delete the admin account!");
			}
			else if (toDelete instanceof Guest) {
				System.out.println("You cannot delete the guest account!");
			}
			else {
				List<Scroll> userScrolls = ScrollTable.getUserScrolls(username);
				for (Scroll scroll : userScrolls) {
					ScrollTable.removeScroll(scroll.getName());
				}
				UserTable.deleteUser(username);

				// Remove their downloads folder, if they have one
				Path dir = Paths.get("."+File.separator+"downloads"+File.separator+username);
				if (Files.exists(dir)) {
					try {	
						Files
						.walk(dir)
						.sorted(Comparator.reverseOrder())
						.forEach(path -> {
							try {
								Files.delete(path);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				System.out.println("User removed!");
			}
		}
		else {
			System.out.println("Username cannot be found in the database!");
		}
	}

	@Override
	public void displayUploads() {
		List<User> allUsers = UserTable.getAllUsers();
		System.out.printf("%-15sUploads"+NEWLINE, "Username");
		for (int i = 0; i < allUsers.size(); i++) {
			String name = allUsers.get(i).getUsername();
			long count = allUsers.get(i).getUploadCount();
			System.out.printf("%-15s%s"+NEWLINE, name, count);
		}
	}

	@Override
	public void displayDownloads() {
		List<Scroll> scrolls = ScrollTable.getAllScrolls();
		System.out.printf("%-15sDownloads"+NEWLINE, "Scroll Name");

		if (scrolls == null) return;

		if (scrolls.isEmpty()) {
			System.out.println("No scrolls yet...");
			return;
		}

		for (Scroll scroll : scrolls) {
			String name = scroll.getName();
			int downloads = scroll.getNumDownloads();
			System.out.printf("%-15s%s"+NEWLINE, name, downloads);
		}
	}
}