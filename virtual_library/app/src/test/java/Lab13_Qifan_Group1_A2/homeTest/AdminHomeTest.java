package Lab13_Qifan_Group1_A2.homeTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;

import Lab13_Qifan_Group1_A2.AppState;
import Lab13_Qifan_Group1_A2.Scroll;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.hasher.Hasher;
import Lab13_Qifan_Group1_A2.home.AdminHome;
import Lab13_Qifan_Group1_A2.parsing.Parser;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;

public class AdminHomeTest {
	private AppState home;
	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private final PrintStream sysOut = System.out;
	private static final String NEWLINE = System.lineSeparator();
	private ByteArrayInputStream testIn;

	@Before
	public void prepare() {
		System.setOut(new PrintStream(testOut));
		Database.dropTables();
		Database.createTables();
		Path uploads = Paths.get("."+File.separator+"uploads");
        Path downloads = Paths.get("."+File.separator+"downloads");

		if (Files.exists(uploads)) {
			try {
				Files
				.walk(uploads)
				.sorted(Comparator.reverseOrder())
				.forEach(path -> {
					try {
						Files.delete(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (Files.exists(downloads)) {
			try {
				Files
				.walk(downloads)
				.sorted(Comparator.reverseOrder())
				.forEach(path -> {
					try {
						Files.delete(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@After
	public void reset() {
		testOut.reset();
		System.setOut(sysOut);
		Database.dropTables();
	}



	@Test
	public void testDisplayAllUsers() {
		// Admin admin = new Admin("admin", "admin", "Joe", "Mama", "joe@mama.com", "0810202311", 0);
		// UserTable.insertUser(admin, Hasher.createHash("admin"));
		User user1 = new RegisteredUser("111", "qqq", "aaa", "aaa", "aaa", "0", 0);
		User user2 = new RegisteredUser("222", "www", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));
		UserTable.insertUser(user2, Hasher.createHash("xxx"));

		String output = "Total users: 2" + NEWLINE + "UserID\t\t\tUsername\t\tFirstName\t\tLastName\t\temail\t\t\t\tphoneNumber\t\t\tUploadCount" + NEWLINE +
						// "admin                   admin                   Joe                     Mama                    joe@mama.com                    0810202311                      0" + NEWLINE +
						// "guest                   guest                   Saul                    Goodman                 better@call.com                 5058425662                      0" + NEWLINE +
						"111\t\t\tqqq\t\t\taaa\t\t\taaa\t\t\taaa\t\t\t0\t\t\t0" + NEWLINE +
						"222\t\t\twww\t\t\taaa\t\t\taaa\t\t\taaa\t\t\t0\t\t\t0" + NEWLINE + NEWLINE + NEWLINE;

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.displayAllUsers();
		assertEquals(output, testOut.toString());
	}

	@Test
	public void displayScreenTest() {
		String display = String.join(NEWLINE,
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
		"====================================================================");

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.displayScreen();
		assertEquals(display, testOut.toString().trim());
	}

	@Test
	public void testRemoveAccountSuccess() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		String input = "111" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		String expected = "Enter your username of the account that you want to remove:" + NEWLINE + "User removed!";

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.removeAccount("a");
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void testRemoveAccountFailure() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		String input = "jwoief" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		String expected = "Enter your username of the account that you want to remove:" + NEWLINE + "Username cannot be found in the database!";

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.removeAccount("a");
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void testRemoveAccountWithDownloadSuccess() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		Scroll scroll = new Scroll(90, new Date(), "hello", user1, ".", 20);
		ScrollTable.insertScroll(scroll);
		String input = "hello" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		Parser parser = new Parser();
		home = new AdminHome(parser);
		home.downloadScroll("111");
		testOut.reset();

		input = "111" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		String expected = "Enter your username of the account that you want to remove:" + NEWLINE + "User removed!";

		parser = new Parser();
        home = new AdminHome(parser);
		home.removeAccount("a");
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void testRemoveAccountSelf() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		String input = "111" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		String expected = "Enter your username of the account that you want to remove:" + NEWLINE + "You cannot delete yourself!";

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.removeAccount("111");
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void testViewUploads() {
		User user1 = new RegisteredUser("111", "111", "aaa",
				"aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));
		UserTable.setUploads("111", 12);

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.displayUploads();

		String expected = "Username       Uploads" + NEWLINE + "111            12";

		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void testDisplayDownloadsNoScrolls() {
		User user1 = new RegisteredUser("111", "111", "aaa",
				"aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		Parser parser = new Parser();
		home = new AdminHome(parser);
		home.displayDownloads();

		String expected1 = "Scroll Name    Downloads"+NEWLINE+"No scrolls yet...";
		assertEquals(expected1, testOut.toString().trim());
	}

	@Test
	public void testDisplayDownloadsNotEmpty() {
		User user1 = new RegisteredUser("111", "111", "aaa",
				"aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		Scroll scroll = new Scroll(90, new Date(), "hello", user1, "010101", 20);
		ScrollTable.insertScroll(scroll);

		Parser parser = new Parser();
		home = new AdminHome(parser);
		home.displayDownloads();

		String expected2 = "Scroll Name    Downloads"+NEWLINE+"hello          20";
		assertEquals(expected2, testOut.toString().trim());
	}
}