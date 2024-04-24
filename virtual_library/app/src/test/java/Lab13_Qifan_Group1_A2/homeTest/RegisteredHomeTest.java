package Lab13_Qifan_Group1_A2.homeTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
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
import Lab13_Qifan_Group1_A2.home.RegisteredHome;
import Lab13_Qifan_Group1_A2.parsing.Parser;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;

public class RegisteredHomeTest {
	private AppState home;
	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private static final String NEWLINE = System.lineSeparator();
	private ByteArrayInputStream testIn;
    private static final String WORKDIR = System.getProperty("user.dir");

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
		System.setIn(System.in);
	}


	// Test the registered user home interface
	@Test
	public void displayScreenTest(){
		String display = String.join(NEWLINE,
		"-- Home --",
				"> Add scroll\t\t(addScroll)",
				"> Edit scroll\t\t(editScroll)",
				"> Remove scroll\t\t(removeScroll)",
				"> View scrolls\t\t(viewScrolls)",
				"> Download scrolls\t(downloadScroll)",
				"> Edit user details\t(editDetails)",
				"> Search scrolls\t(searchScroll)",
				"> Preview scrolls\t(preview)",
				"> Logout\t\t(logout)",
		"====================================================================");

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.displayScreen();
		assertEquals(display, testOut.toString().trim());
	}

	@Test
	public void addScrollTestNo() {
		User user = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		String line = "101001" + NEWLINE + "." + NEWLINE + "hello" + NEWLINE + "N" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
				"Enter your content:" + NEWLINE +
                "Enter your path to the directory where you want to store your scroll:" + NEWLINE +
				"Enter your scroll name:" + NEWLINE + 
				"Please check your inputs again" + NEWLINE + 
				"content: 101001" + NEWLINE +
                "path to the directory where you want to store your scroll: ." + NEWLINE +
				"scroll name: hello" + NEWLINE + 
                "Please confirm. (Y/N)" + NEWLINE +
                "Ok, the scroll was not added" + NEWLINE
		);

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.addScroll(user);
		assertEquals (expected,testOut.toString());
	}

	@Test
	public void addScrollTestYes() {
		User user = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		String line = "0" + NEWLINE + "test/.\\.////.\\\\.\\.\\moopa" +
                      NEWLINE + "hello" + NEWLINE + "Y" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
				"Enter your content:" + NEWLINE +
                "Enter your path to the directory where you want to store your scroll:" + NEWLINE +
				"Enter your scroll name:" + NEWLINE +
				"Please check your inputs again" + NEWLINE +
				"content: 0" + NEWLINE +
                "path to the directory where you want to store your scroll: test/.\\.////.\\\\.\\.\\moopa" + NEWLINE +
				"scroll name: hello" + NEWLINE +
				"Please confirm. (Y/N)" + NEWLINE +
				"hello added to ./uploads/test/././///.//././moopa".replaceAll("/", "\\" + File.separator) + NEWLINE
		);

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.addScroll(user);
		assertEquals (expected,testOut.toString());
	}

    @Test
	public void addScrollTestExists() {
		User user = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		Scroll scroll = new Scroll(-1, new java.sql.Date(System.currentTimeMillis()), "hello", user, ".", 0);
        ScrollTable.insertScroll(scroll);

        String line = "0" + NEWLINE + "test/.\\.////.\\\\.\\.\\moopa" + 
                      NEWLINE + "hello" + NEWLINE + "Y" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
				"Enter your content:" + NEWLINE + 
                "Enter your path to the directory where you want to store your scroll:" + NEWLINE +
				"Enter your scroll name:" + NEWLINE + 
				"This scroll name already exists. Please choose another one." + NEWLINE
		);
		
		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.addScroll(user);
		assertEquals (expected,testOut.toString());
	}

	@Test
	public void removeScrollTestYes() {
		Database.dropTables();
		Database.createTables();
		RegisteredUser user1 = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		Scroll scroll1 = new Scroll(
				1, new Date(), "first", user1,
				"010101", 1);
		UserTable.insertUser(user1, "hello");
		ScrollTable.insertScroll(scroll1);

		String line = "1" + NEWLINE + "Y" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
			"Enter your scroll ID:" + NEWLINE +
			"Please check your inputs again" + NEWLINE +
			"scroll ID: 1" + NEWLINE +
			"Please confirm. (Y/N)" + NEWLINE +
			"Scroll removed!" + NEWLINE
		);

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.removeScroll(user1);
		assertEquals (expected,testOut.toString());
	}

	@Test
	public void removeScrollTestUnconfirm() {
		Database.dropTables();
		Database.createTables();
		RegisteredUser user1 = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		Scroll scroll1 = new Scroll(
				1, new Date(), "first", user1,
				"010101", 1);
		UserTable.insertUser(user1, "hello");
		ScrollTable.insertScroll(scroll1);

		String line = "1" + NEWLINE + "N" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
				"Enter your scroll ID:" + NEWLINE +
				"Please check your inputs again" + NEWLINE +
				"scroll ID: 1" + NEWLINE +
				"Please confirm. (Y/N)"+ NEWLINE
		);

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.removeScroll(user1);
		assertEquals (expected,testOut.toString());
	}

	@Test
	public void removeScrollTestFailPermission() {
		Database.dropTables();
		Database.createTables();
		RegisteredUser user1 = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		RegisteredUser user2 = new RegisteredUser("gugu","wiel","chil", "chice","coc@","0908090909", 2000);
		Scroll scroll1 = new Scroll(
				1, new Date(), "first", user1,
				"010101", 1);
		UserTable.insertUser(user1, "hello");
		UserTable.insertUser(user2, "cello");
		ScrollTable.insertScroll(scroll1);

		String line = "1" + NEWLINE + "Y" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
				"Enter your scroll ID:" + NEWLINE +
				"Please check your inputs again" + NEWLINE +
				"scroll ID: 1" + NEWLINE +
				"Please confirm. (Y/N)" + NEWLINE +
				"You are not allowed to remove this." + NEWLINE
		);

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.removeScroll(user2);
		assertEquals (expected,testOut.toString());
	}

	@Test
	public void removeScrollTestFailNull() {
		Database.dropTables();
		Database.createTables();
		RegisteredUser user1 = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
		RegisteredUser user2 = new RegisteredUser("gugu","wiel","chil", "chice","coc@","0908090909", 2000);
		Scroll scroll1 = new Scroll(
				1, new Date(), "first", user1,
				"010101", 1);
		UserTable.insertUser(user1, "hello");
		UserTable.insertUser(user2, "cello");
		ScrollTable.insertScroll(scroll1);

		String line = "2" + NEWLINE + "Y" + NEWLINE;
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.format(
				"Enter your scroll ID:" + NEWLINE +
				"Please check your inputs again" + NEWLINE +
				"scroll ID: 2" + NEWLINE +
				"Please confirm. (Y/N)" + NEWLINE +
				"Invalid scrollID" + NEWLINE
		);

		Parser parser = new Parser();
		home = new RegisteredHome(parser);
		home.removeScroll(user2);
		assertEquals (expected, testOut.toString());
	}

    @Test
    public void editUsernameWithDownloadSuccess() {
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

        input = "username" + NEWLINE + "Suzaku" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        String userDetails = String.format("> Username: %-35s(username)", "111") + NEWLINE + 
							String.format("> UserID: %-37s(userid)", "111") + NEWLINE + 
							String.format("> First name: %-33s(firstname)", "aaa") + NEWLINE + 
							String.format("> Last name: %-34s(lastname)", "aaa") + NEWLINE + 
							String.format("> Email: %-38s(email)", "aaa") + NEWLINE + 
							String.format("> Phone number: %-31s(phoneNumber)", "0") + NEWLINE + 
							String.format("> Password: ********%27s(password)", "") + NEWLINE;
		String expected = userDetails + 
                          "Please select a field to edit." + NEWLINE +
                          "Enter your field to change:" + NEWLINE +
                          "Enter your new username:" + NEWLINE + 
                          "Username changed to Suzaku.";

		parser = new Parser();
        home = new AdminHome(parser);
		home.editDetails(user1);
		assertEquals(expected, testOut.toString().trim());
	}

    @Test
    public void editUsernameSuccess() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

        String input = "username" + NEWLINE + "Suzaku" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        String userDetails = String.format("> Username: %-35s(username)", "111") + NEWLINE + 
							String.format("> UserID: %-37s(userid)", "111") + NEWLINE + 
							String.format("> First name: %-33s(firstname)", "aaa") + NEWLINE + 
							String.format("> Last name: %-34s(lastname)", "aaa") + NEWLINE + 
							String.format("> Email: %-38s(email)", "aaa") + NEWLINE + 
							String.format("> Phone number: %-31s(phoneNumber)", "0") + NEWLINE + 
							String.format("> Password: ********%27s(password)", "") + NEWLINE;
		String expected = userDetails + 
                          "Please select a field to edit." + NEWLINE +
                          "Enter your field to change:" + NEWLINE +
                          "Enter your new username:" + NEWLINE + 
                          "Username changed to Suzaku.";

		Parser parser = new Parser();
        home = new AdminHome(parser);
		home.editDetails(user1);
		assertEquals(expected, testOut.toString().trim());
	}

    @Test
    public void downloadScrollExists() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));

		String input = "hello" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        String expected = "Enter your scroll name:" + NEWLINE + 
                          "This scroll does not exist!";

		Parser parser = new Parser();
		home = new AdminHome(parser);
		home.downloadScroll("111");
		assertEquals(expected, testOut.toString().trim());
	}

    @Test
    public void downloadScrollNo() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));
        String input = "0" + NEWLINE + "." + NEWLINE + "hello" + NEWLINE + "Y";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Parser parser = new Parser();
		home = new AdminHome(parser);
		home.addScroll(user1);
        testOut.reset();

		input = "hello" + NEWLINE + "N" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        String preview = NEWLINE + String.format("~PREVIEW~") + NEWLINE + 
                        String.format("Name: hello") + NEWLINE + 
                        String.format("Author: aaa aaa") + NEWLINE +
                        String.format("Content:") + NEWLINE + 
                        String.format("0") + NEWLINE;
        String expected = "Enter your scroll name:" + NEWLINE +
                          preview + NEWLINE +
                          "Please confirm. (Y/N)" + NEWLINE +
                          "Ok, the scroll will not be downloaded";

		parser = new Parser();
		home = new AdminHome(parser);
		home.downloadScroll("111");
		assertEquals(expected, testOut.toString().trim());
	}

    @Test
    public void firstDownloadScrollSuccess() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));
        String input = "0" + NEWLINE + "." + NEWLINE + "hello" + NEWLINE + "Y";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Parser parser = new Parser();
		home = new AdminHome(parser);
		home.addScroll(user1);
        testOut.reset();

		input = "hello" + NEWLINE + "Y" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        String preview = NEWLINE + String.format("~PREVIEW~") + NEWLINE + 
                        String.format("Name: hello") + NEWLINE + 
                        String.format("Author: aaa aaa") + NEWLINE +
                        String.format("Content:") + NEWLINE + 
                        String.format("0") + NEWLINE;
        String expected = "Enter your scroll name:" + NEWLINE +
                          preview + NEWLINE +
                          "Please confirm. (Y/N)" + NEWLINE +
                          String.format("hello downloaded to %s!", 
                                        WORKDIR+File.separator+"downloads"+File.separator+"111");

		parser = new Parser();
		home = new AdminHome(parser);
		home.downloadScroll("111");
		assertEquals(expected, testOut.toString().trim());
	}

    @Test
    public void secondDownloadScrollSuccess() {
		User user1 = new RegisteredUser("111", "111", "aaa", "aaa", "aaa", "0", 0);
		UserTable.insertUser(user1, Hasher.createHash("xxx"));
        String input = "0" + NEWLINE + "." + NEWLINE + "hello" + NEWLINE + "Y";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Parser parser = new Parser();
		home = new AdminHome(parser);
		home.addScroll(user1);

		input = "hello" + NEWLINE + "Y" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
		parser = new Parser();
		home = new AdminHome(parser);
		home.downloadScroll("111");
		testOut.reset();

		input = "hello" + NEWLINE + "Y" + NEWLINE;
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        String preview = NEWLINE + String.format("~PREVIEW~") + NEWLINE + 
                        String.format("Name: hello") + NEWLINE + 
                        String.format("Author: aaa aaa") + NEWLINE +
                        String.format("Content:") + NEWLINE + 
                        String.format("0") + NEWLINE;
        String expected = "Enter your scroll name:" + NEWLINE +
                          preview + NEWLINE +
                          "Please confirm. (Y/N)" + NEWLINE +
                          String.format("hello downloaded to %s!", 
                                        WORKDIR+File.separator+"downloads"+File.separator+"111");

		parser = new Parser();
		home = new AdminHome(parser);
		home.downloadScroll("111");
		assertEquals(expected, testOut.toString().trim());
	}
}