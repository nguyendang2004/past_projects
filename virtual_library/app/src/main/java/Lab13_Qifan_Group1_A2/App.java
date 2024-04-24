/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Lab13_Qifan_Group1_A2;

import Lab13_Qifan_Group1_A2.database.*;
import Lab13_Qifan_Group1_A2.hasher.Hasher;
import Lab13_Qifan_Group1_A2.home.*;
import Lab13_Qifan_Group1_A2.parsing.*;
import Lab13_Qifan_Group1_A2.start.Start;
import Lab13_Qifan_Group1_A2.users.*;

import java.sql.Date;
import java.util.ArrayList;

public class App {
	private static final String NEWLINE = System.lineSeparator();
	public AppState currentAppState;
	public User user = null;
	public Parser parser;
	public String scrollOfDayName;

	public App() {
		parser = new Parser();
		currentAppState = new Start(parser);
	}

	/* dbSetup
	 * Prepares the database for the program.
	 * If the admin or guest doesn't exist, they will be made.
	 */
	public static void dbSetup() {
		Database.createTables();
        
		if (!UserTable.userTypeExists("WIZARD")) {
            Wizard wizard = new Wizard("wizard", "wizard", "wizard", "Almighty", "wizard@almighty.com", "000000", 0);
			UserTable.insertUser(wizard, Hasher.createHash("wizard"));
		}
		if (!UserTable.userTypeExists("ADMIN")) {
            Admin admin = new Admin("admin", "admin", "Joe", "Mama", "joe@mama.com", "0810202311", 0);
			UserTable.insertUser(admin, Hasher.createHash("admin"));
		}
		if (!UserTable.userTypeExists("GUEST")) {
			Guest guest = new Guest("guest", "guest", "Saul", "Goodman", "better@call.com", "5058425662", 0);
			UserTable.insertUser(guest, Hasher.createHash("guest"));
		}
	}

	/* login
	 * Handles all procedures relating the start interface.
	 * Will only move on from this when a user has been loaded.
	 */
	public void login() {
		while (currentAppState instanceof Start) {
			currentAppState.displayScreen();
			Command cmd = currentAppState.parser.checkCommand();

			if (cmd == Command.register) {
				currentAppState.register();
			}
			else if (cmd == Command.login) {
				user = currentAppState.registeredLogin();
			}
			else if (cmd == Command.guest) {
				user = currentAppState.guestLogin();
			}
			else if (cmd == Command.exit) {
				currentAppState.parser.closeScanner();
				currentAppState.exit();
			}
			else {
				System.out.println("Command not recognised!");
				continue;
			}

			if (user instanceof Admin) {
				currentAppState = new AdminHome(parser);
			}
			else if (user instanceof Guest) {
				currentAppState = new GuestHome(parser);
			}
			else if (user instanceof RegisteredUser) {
				currentAppState = new RegisteredHome(parser);
			}
		}
	}

	public void home() {
		while (currentAppState instanceof GuestHome) {
			displayUser();
			displayRandomScroll();
			currentAppState.displayScreen();
			Command cmd = currentAppState.parser.checkCommand();

			if (cmd == Command.logout) {
				currentAppState = new Start(parser);
                scrollOfDayName = null;
				user = null;
				System.out.println("Farewell, wise Whisker...");
			}
			else if (cmd == Command.addScroll && currentAppState instanceof RegisteredHome) {
				currentAppState.addScroll(user);
			}
			else if (cmd == Command.preview && currentAppState instanceof GuestHome) {
				currentAppState.preview();
			}
			else if (cmd == Command.viewDownloads && currentAppState instanceof AdminHome) {
				currentAppState.displayDownloads();
			}
			else if (cmd == Command.removeScroll && currentAppState instanceof RegisteredHome) {
				currentAppState.removeScroll(user);
			}
			else if (cmd == Command.addAccount && currentAppState instanceof AdminHome) {
				currentAppState.addAccount();
			}
			else if (cmd == Command.removeAccount && currentAppState instanceof AdminHome) {
				currentAppState.removeAccount(user.getUsername());
			}
			else if (cmd == Command.viewProfiles && currentAppState instanceof AdminHome) {
				currentAppState.displayAllUsers();
			}
			else if (cmd == Command.viewUploads && currentAppState instanceof AdminHome) {
				currentAppState.displayUploads();
			}
			else if (cmd == Command.viewScrolls && currentAppState instanceof GuestHome)
			{
				currentAppState.viewScrolls();
			}
			else if (cmd == Command.editDetails && currentAppState instanceof RegisteredHome) {
				currentAppState.editDetails(user);
			}
			else if (cmd == Command.editScroll && currentAppState instanceof RegisteredHome) {
				currentAppState.editScroll(user);
			}
            else if (cmd == Command.downloadScroll && currentAppState instanceof RegisteredHome) {
				currentAppState.downloadScroll(user.getUsername());
			}
			else if (cmd == Command.searchScroll && currentAppState instanceof GuestHome) {
				currentAppState.searchScroll();
			}
			else {
				System.out.println("Command not recognised!");
				continue;
			}
		}
	}

	public void displayUser() {
		System.out.printf(NEWLINE + 
						  "====================================================================" + NEWLINE +
						  "Hello, %s %s" + NEWLINE, user.getFirstName(), user.getLastName());
		if (user instanceof Wizard) {
			System.out.printf("You are a Wizard" + NEWLINE + NEWLINE);
		}
        else if (user instanceof Admin) {
			System.out.printf("You are an Admin" + NEWLINE + NEWLINE);
		}
		else if (user instanceof Guest) {
			System.out.printf("You are a Guest" + NEWLINE + NEWLINE);
		}
		else {
			System.out.printf("You are a Registered User" + NEWLINE + NEWLINE);
		}
	}


	public void displayRandomScroll() {
        // If the scroll has been deleted, reset the name
        if (scrollOfDayName != null && !ScrollTable.scrollExists(scrollOfDayName)) {
            scrollOfDayName = null;
        }
        // If there is no name of the scroll of the day, get one if a scroll exists
        if (scrollOfDayName == null && ScrollTable.scrollCount() > 0) {
            scrollOfDayName = ScrollTable.getRandomScroll().getName();
        }
        // If no scroll exists
        else if (scrollOfDayName == null) {
            System.out.println(NEWLINE + "~SCROLL OF THE DAY~" + 
                               NEWLINE + "There are no scrolls to display ;(" +
                               NEWLINE);
            return;
        }
        
        Scroll randomScroll = ScrollTable.getScroll(scrollOfDayName);
        long id = randomScroll.getScrollID();
        String author = randomScroll.getAuthor().getFirstName() + " " 
                        + randomScroll.getAuthor().getLastName();
        System.out.printf(String.join(NEWLINE,
        "~SCROLL OF THE DAY~",
        "Name: %s",
        "ID: %s",
        "Author: %s", NEWLINE),
        scrollOfDayName, id, author);
	}


	public static void main(String[] args) {
		App app = new App();
		dbSetup();
		while (true) {
			app.login();
			app.home();
		}
	}
}


// To Fix
// change \t in prints to space padding for consistency