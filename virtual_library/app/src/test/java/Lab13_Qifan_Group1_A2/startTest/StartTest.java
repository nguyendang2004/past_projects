package Lab13_Qifan_Group1_A2.startTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Lab13_Qifan_Group1_A2.AppState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.hasher.Hasher;
import Lab13_Qifan_Group1_A2.parsing.Parser;
import Lab13_Qifan_Group1_A2.start.Start;
import Lab13_Qifan_Group1_A2.users.Guest;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;

public class StartTest {
	private AppState start;
	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private static final String NEWLINE = System.lineSeparator();
	private ByteArrayInputStream testIn;

	// Test user 1
	private final String userID1 = "Zero";
	private final String username1 = "Lulu";
	private final String firstName1 = "Lelouch";
	private final String lastName1 = "vi Britannia";
	private final String email1 = "LL@a11.com";
	private final String phoneNumber1 = "012345678";
	private final long uploadCount1 = 11;

	// Test user 2
	private final String userID2 = "C2";
	private final String username2 = "CC";
	private final String password2 = "<3";
	private final String firstName2 = "REDACTED";
	private final String lastName2 = "REDACTED";
	private final String email2 = "c@c.com";
	private final String phoneNumber2 = "01343243";
	private final long uploadCount2 = 0;

	@Before
	public void prepare() {
		System.setOut(new PrintStream(testOut));
		Database.dropTables();
		Database.createTables();
	}

	@After
	public void reset() {
		testOut.reset();
		Database.dropTables();
	}

	@Test
	public void displayScreenTest(){
		String display = String.join(NEWLINE,
		"====================================================================",
		"-- Choose a Login Option --",
		"> Log in as Guest\t(guest)",
		"> Log in as User\t(login)",
		"> Register\t\t(register)",
		"> Exit\t\t\t(exit)",
		"====================================================================");

		Parser parser = new Parser();
		start = new Start(parser);
		start.displayScreen();
		assertEquals(display, testOut.toString().trim());
	}

	@Test
	public void guestLoginTest(){
		Guest guest = new Guest("guest", "guest", "Saul", "Goodman", "better@call.com", "5058425662", 0);
		UserTable.insertUser(guest, "guest");
		String prompt = "Welcome to the Library of Agility, guest";

		Parser parser = new Parser();
		start = new Start(parser);
		start.guestLogin();
		assertEquals(prompt, testOut.toString().trim());
	}

	@Test
	public void registerSuccessfulTest(){
		User lelouch = new RegisteredUser(userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, "1234");
		String expected = String.join(NEWLINE, "Enter your ID:", "Enter your username:", "Enter your password:", "Enter your firstname:", "Enter your lastname:", "Enter your email:", "Enter your phone number:", "Registration successful!");
		String input = String.join(NEWLINE, userID2, username2, password2, firstName2, lastName2, email2, phoneNumber2);
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.register();
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void registerInvalidIDTest(){
		User lelouch = new RegisteredUser(userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, "1234");
		String expected = String.join(NEWLINE, "Enter your ID:", "Enter your username:", "Enter your password:", "Enter your firstname:", "Enter your lastname:", "Enter your email:", "Enter your phone number:", "ID already exists! Please choose another one");
		String input = String.join(NEWLINE, userID1, username2, password2, firstName2, lastName2, email2, phoneNumber2);
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.register();
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void registerInvalidUserTest(){
		User lelouch = new RegisteredUser(userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, "1234");
		String expected =  String.join(NEWLINE, "Enter your ID:", "Enter your username:", "Enter your password:", "Enter your firstname:", "Enter your lastname:", "Enter your email:", "Enter your phone number:", "Username already exists! Please choose another one");
		String input = String.join(NEWLINE, userID2, username1, password2, firstName2, lastName2, email2, phoneNumber2);
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.register();
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void registerInvalidInputTest(){
		User lelouch = new RegisteredUser(
				userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, "1234");
		String expected = String.join(NEWLINE, "Enter your ID:", "Enter your username:", "Enter your password:","Invalid input. Please try again.","Enter your password:", "Enter your firstname:", "Enter your lastname:", "Enter your email:", "Enter your phone number:","Registration successful!");
		String input = String.join(NEWLINE, "jjjjj", "username1", "", password2, firstName2, lastName2, email1, "0908789898");
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.register();
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void registeredLoginTestSuccessful(){
		User lelouch = new RegisteredUser(
				userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, Hasher.createHash("1234"));
		String expected = String.join(NEWLINE, "Enter your username:", "Enter your password:", "Welcome to the Library of Agility, Lulu");
		String input = String.join(NEWLINE, "Lulu", "1234");
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.registeredLogin();
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void registeredLoginTestInvalidPassword(){
		User lelouch = new RegisteredUser(
				userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, "1234");
		String expected = String.join(NEWLINE, "Enter your username:", "Enter your password:", "Incorrect password!");
		String input = String.join(NEWLINE, "Lulu", "ohnobro");
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.registeredLogin();
		assertEquals(expected, testOut.toString().trim());
	}

	@Test
	public void registeredLoginTestInvalidUsername(){
		User lelouch = new RegisteredUser(
				userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
		UserTable.insertUser(lelouch, "1234");
		String expected = String.join(NEWLINE, "Enter your username:", "Enter your password:", "Username does not exist!");
		String input = String.join(NEWLINE, "lulu", "1234");
		testIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(testIn);

		Parser parser = new Parser();
		start = new Start(parser);
		start.registeredLogin();
		assertEquals(expected, testOut.toString().trim());
	}
}