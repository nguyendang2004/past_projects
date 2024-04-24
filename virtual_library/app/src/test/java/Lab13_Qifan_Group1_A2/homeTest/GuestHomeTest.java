package Lab13_Qifan_Group1_A2.homeTest;

import Lab13_Qifan_Group1_A2.App;
import Lab13_Qifan_Group1_A2.AppState;
import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.AppState;
import Lab13_Qifan_Group1_A2.Scroll;
import org.junit.Test;

import Lab13_Qifan_Group1_A2.database.*;
import Lab13_Qifan_Group1_A2.hasher.Hasher;
import Lab13_Qifan_Group1_A2.parsing.*;
import Lab13_Qifan_Group1_A2.home.GuestHome;
import Lab13_Qifan_Group1_A2.users.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class GuestHomeTest
{
	private AppState home;
	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private static final String NEWLINE = System.lineSeparator();
	private ByteArrayInputStream testIn;

	@Before
	public void prepare()
	{
		System.setOut(new PrintStream(testOut));
		Database.dropTables();
		Database.createTables();
		Guest guest = new Guest("guest", "guest", "Good", "Guest", "better@call.com", "5058425662", 0);
		UserTable.insertUser(guest, Hasher.createHash("guest"));
		System.setOut(new PrintStream(testOut));
	}
	@After
	public void reset()
	{
		testOut.reset();
		Database.dropTables();
		System.setIn(System.in);
	}

	@Test
	public void displayScreenTest()
	{
		String display = String.join(NEWLINE,
		"-- Home --",
		"> View scrolls\t\t(viewScrolls)",
		"> Search scrolls\t(searchScroll)",
		"> Preview scrolls\t(preview)",
		"> Logout\t\t(logout)",
		"====================================================================");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.displayScreen();
		assertEquals(display, testOut.toString().trim());
	}

	@Test
	public void testViewScrolls()
	{
		int scrollID = ScrollTable.scrollCount();
		Date date = new java.sql.Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        //String expDate = sdf.format(cal.getTime());

		Guest guest = new Guest("guest", "guest", "Good", "Guest", "better@call.com", "5058425662", 0);

		Scroll testScroll1 = new Scroll(scrollID, date, "testScroll1", guest, "10101010", 0);
		Scroll testScroll2 = new Scroll(scrollID, date, "testScroll2", guest, "10101010", 0);

		ScrollTable.insertScroll(testScroll1);
		ScrollTable.insertScroll(testScroll2);
		String expDate=  String.format("%tb %te %tY", testScroll1.getDate(), testScroll1.getDate(), testScroll1.getDate());
		String expected = String.format("%-5s%-25s%-15s%s%n", "ID", "Name", "Author", "Date");
		expected += "1    testScroll1              Good Guest     " + expDate + NEWLINE;
		expected += "2    testScroll2              Good Guest     " + expDate;
		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.viewScrolls();
		assertEquals(expected, testOut.toString().trim());
	}

//	@Test
//	public void testValidateSearchFields() {
//		Parser parser = new Parser();
//		GuestHome home = new GuestHome(parser);
//		String[] validFields = {"uploaderID", "scrollID", "scrollName", "uploadDate"};
//		assertFalse(home.validateSearchFields(validFields, new String[]{"joe"}));
//		assertTrue(home.validateSearchFields(validFields, new String[]{"uploaderID", "scrollID"}));
//	}

	@Test
	public void testSearchScrollInvalidParam() {
		User author = new RegisteredUser("author", "author",
				"author", "author", "author@a.com", "001010", 0);
		java.util.Date currentDate = new java.util.Date();
		Scroll scroll = new Scroll(1, currentDate, "CrazyScroll", author, "01001", 0);

		UserTable.insertUser(author, Hasher.createHash("author"));
		ScrollTable.insertScroll(scroll);

		String line = String.join(NEWLINE, "invalid", "uploaderID", "author");
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68),
				"Enter your field to search by:",
				"Please enter a valid search field!",
				"Enter your field to search by:",
				"Enter your uploaderID (Alphanumeric with no space):",
				"",
				String.format("%-5s%-25s%-15s%s", "ID", "Name", "Author", "Date"),
				String.format("%-5d%-25s%-15s%s", 1, "CrazyScroll",
						"author author", DatabaseUtils.convertDateToString(currentDate)),
				"");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.searchScroll();
		assertEquals(expected, testOut.toString());
	}

	@Test
	public void testSearchScrollNoResult() {
		User author = new RegisteredUser("author", "author",
				"author", "author", "author@a.com", "001010", 0);
		java.util.Date currentDate = new java.util.Date();
		Scroll scroll = new Scroll(1, currentDate, "CrazyScroll", author, "01001", 0);

		UserTable.insertUser(author, Hasher.createHash("author"));
		ScrollTable.insertScroll(scroll);

		String line = String.join(NEWLINE, "uploaderID", "unexist");
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68),
				"Enter your field to search by:",
				"Enter your uploaderID (Alphanumeric with no space):",
				"",
				"No results found.",
				"");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.searchScroll();
		assertEquals(expected, testOut.toString());
	}

	@Test
	public void testSearchScrollByUploaderID() {
		User author = new RegisteredUser("author", "author",
				"author", "author", "author@a.com", "001010", 0);
		java.util.Date currentDate = new java.util.Date();
		Scroll scroll = new Scroll(1, currentDate, "CrazyScroll", author, "01001", 0);

		UserTable.insertUser(author, Hasher.createHash("author"));
		ScrollTable.insertScroll(scroll);

		String line = String.join(NEWLINE, "uploaderID", "author");
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68),
				"Enter your field to search by:",
				"Enter your uploaderID (Alphanumeric with no space):",
				"",
				String.format("%-5s%-25s%-15s%s", "ID", "Name", "Author",  "Date"),
				String.format("%-5d%-25s%-15s%s", 1, "CrazyScroll",
						"author author",  DatabaseUtils.convertDateToString(currentDate)),
				"");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.searchScroll();
		assertEquals(expected, testOut.toString());
	}

	@Test
	public void testSearchScrollByScrollID() {
		User author = new RegisteredUser("author", "author",
				"author", "author", "author@a.com", "001010", 0);
		java.util.Date currentDate = new java.util.Date();
		Scroll scroll = new Scroll(1, currentDate, "CrazyScroll", author, "01001", 0);

		UserTable.insertUser(author, Hasher.createHash("author"));
		ScrollTable.insertScroll(scroll);

		String line = String.join(NEWLINE, "scrollID", "1");
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68),
				"Enter your field to search by:",
				"Enter your scrollID (Numeric with no space):",
				"",
				String.format("%-5s%-25s%-15s%s", "ID", "Name", "Author", "Date"),
				String.format("%-5d%-25s%-15s%s", 1, "CrazyScroll",
						"author author",  DatabaseUtils.convertDateToString(currentDate)),
				"");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.searchScroll();
		assertEquals(expected, testOut.toString());
	}

	@Test
	public void testSearchScrollByName() {
		User author = new RegisteredUser("author", "author",
				"author", "author", "author@a.com", "001010", 0);
		java.util.Date currentDate = new java.util.Date();
		Scroll scroll = new Scroll(1, currentDate, "CrazyScroll", author, "01001", 0);

		UserTable.insertUser(author, Hasher.createHash("author"));
		ScrollTable.insertScroll(scroll);

		String line = String.join(NEWLINE, "scrollName", "CrazyScroll");
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68),
				"Enter your field to search by:",
				"Enter your scrollName (Alphanumeric with no space):",
				"",
				String.format("%-5s%-25s%-15s%s", "ID", "Name", "Author", "Date"),
				String.format("%-5d%-25s%-15s%s", 1, "CrazyScroll",
						"author author",  DatabaseUtils.convertDateToString(currentDate)),
				"");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.searchScroll();
		assertEquals(expected, testOut.toString());
	}

	public String formatDate(java.util.Date date) {
		final String SEARCH_DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat formatter = new SimpleDateFormat(SEARCH_DATE_FORMAT);
		return formatter.format(date);
	}

	@Test
	public void testSearchScrollByUploadDate() {
		User author = new RegisteredUser("author", "author",
				"author", "author", "author@a.com", "001010", 0);
		java.util.Date currentDate = new java.util.Date();
		Scroll scroll = new Scroll(1, currentDate, "CrazyScroll", author, "01001", 0);

		UserTable.insertUser(author, Hasher.createHash("author"));
		ScrollTable.insertScroll(scroll);

		String line = String.join(NEWLINE, "uploadDate", "134341/34343", formatDate(currentDate));
		testIn = new ByteArrayInputStream(line.getBytes());
		System.setIn(testIn);
		String expected = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68),
				"Enter your field to search by:",
				"Enter your uploadDate (dd/MM/yyyy):",
				"Invalid input. Please try again.",
				"Enter your uploadDate (dd/MM/yyyy):",
				"",
				String.format("%-5s%-25s%-15s%s", "ID", "Name", "Author", "Date"),
				String.format("%-5d%-25s%-15s%s", 1, "CrazyScroll",
						"author author",  DatabaseUtils.convertDateToString(currentDate)),
				"");

		Parser parser = new Parser();
		home = new GuestHome(parser);
		home.searchScroll();
		assertEquals(expected, testOut.toString());
	}
}