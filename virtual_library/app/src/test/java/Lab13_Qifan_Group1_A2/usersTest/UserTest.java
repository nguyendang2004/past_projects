package Lab13_Qifan_Group1_A2.usersTest;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.users.Admin;
import Lab13_Qifan_Group1_A2.users.Guest;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import org.junit.Test;

import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class UserTest
{
	private Admin admin;
	private Guest guest;
	private RegisteredUser user;
	private String userPwd = "a";
	private String guestPwd = "ccc";
	private Scroll scroll;
	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	private final PrintStream sysOut = System.out;

	@Before
	public void setUp()
	{
		Database.dropTables();
		Database.createTables();
		admin = new Admin("123", "test_admin",
				"ad", "min", "a@a.com", "1234", 0);
		guest = new Guest("456", "test_guest",
				"a", "b", "a@b.com", "3434", 0);
		user = new RegisteredUser("789", "test_user",
				"c", "d", "c@c.com", "3423", 0);
		scroll = new Scroll(1, new Date(), "a",
				guest, "0101", 0);
		System.setOut(new PrintStream(testOut));
	}

	@After
	public void restoreStreams()
	{
		Database.dropTables();
		System.setOut(sysOut);
	}

	@Test
	public void testGetUserID()
	{
		assertEquals("123", admin.getUserID());
		assertEquals("456", guest.getUserID());
		assertEquals("789", user.getUserID());
	}

	@Test
	public void testSetUserID()
	{
		admin.setUserID("abc");
		guest.setUserID("def");
		user.setUserID("ghi");
		assertEquals("abc", admin.getUserID());
		assertEquals("def", guest.getUserID());
		assertEquals("ghi", user.getUserID());
	}

	@Test
	public void testGetUsername()
	{
		assertEquals("test_admin", admin.getUsername());
		assertEquals("test_guest", guest.getUsername());
		assertEquals("test_user", user.getUsername());
	}

	@Test
	public void testSetUsername()
	{
		admin.setUsername("TEST_ADMIN");
		guest.setUsername("TEST_GUEST");
		user.setUsername("TEST_USER");
		assertEquals("TEST_ADMIN", admin.getUsername());
		assertEquals("TEST_GUEST", guest.getUsername());
		assertEquals("TEST_USER", user.getUsername());
	}

	@Test
	public void testGetFirstname()
	{
		assertEquals("ad", admin.getFirstName());
		assertEquals("a", guest.getFirstName());
		assertEquals("c", user.getFirstName());
	}

	@Test
	public void testSetFirstname()
	{
		admin.setFirstName("Mom");
		guest.setFirstName("Mum");
		user.setFirstName("Mamamia");
		assertEquals("Mom", admin.getFirstName());
		assertEquals("Mum", guest.getFirstName());
		assertEquals("Mamamia", user.getFirstName());
	}

	@Test
	public void testGetLastname()
	{
		assertEquals("min", admin.getLastName());
		assertEquals("b", guest.getLastName());
		assertEquals("d", user.getLastName());
	}

	@Test
	public void testSetLastname()
	{
		admin.setLastName("Mom");
		guest.setLastName("Mum");
		user.setLastName("Mamamia");
		assertEquals("Mom", admin.getLastName());
		assertEquals("Mum", guest.getLastName());
		assertEquals("Mamamia", user.getLastName());
	}

	@Test
	public void testGetEmail()
	{
		assertEquals("a@a.com", admin.getEmail());
		assertEquals("a@b.com", guest.getEmail());
		assertEquals("c@c.com", user.getEmail());
	}

	@Test
	public void testSetEmail()
	{
		admin.setEmail("test_admin@example.com");
		guest.setEmail("test_guest@example.com");
		user.setEmail("test_user@example.com");
		assertEquals("test_admin@example.com", admin.getEmail());
		assertEquals("test_guest@example.com", guest.getEmail());
		assertEquals("test_user@example.com", user.getEmail());
	}

	@Test
	public void testGetPhoneNumber()
	{
		assertEquals("1234", admin.getPhoneNumber());
		assertEquals("3434", guest.getPhoneNumber());
		assertEquals("3423", user.getPhoneNumber());
	}

	@Test
	public void testSetPhoneNumber()
	{
		admin.setPhoneNumber("1234567890");
		guest.setPhoneNumber("13579");
		user.setPhoneNumber("24680");
		assertEquals("1234567890", admin.getPhoneNumber());
		assertEquals("13579", guest.getPhoneNumber());
		assertEquals("24680", user.getPhoneNumber());
	}

	@Test
	public void testGetUploadCount()
	{
		assertEquals(0, admin.getUploadCount());
		assertEquals(0, guest.getUploadCount());
		assertEquals(0, user.getUploadCount());
	}

	@Test
	public void testSetUploadCount()
	{
		admin.setUploadCount(10);
		guest.setUploadCount(100);
		user.setUploadCount(1000);
		assertEquals(10, admin.getUploadCount());
		assertEquals(100, guest.getUploadCount());
		assertEquals(1000, user.getUploadCount());
	}

	@Test
	public void testIsSameUser()
	{
		admin.setUserID("12345");
		user.setUserID("54321");
		assertFalse(user.isSameUser(admin));
		user.setUserID("12345");
		assertTrue(user.isSameUser(admin));
	}

	@Test
	public void testVerifyRemoveScrollIDNull() {
		UserTable.insertUser(this.guest, this.guestPwd);
		ScrollTable.insertScroll(this.scroll);
		assertEquals(guest.verifyRemoveScrollID(10), 0);
	}

	@Test
	public void testVerifyRemoveScrollIDNo(){
		Guest guest2 = new Guest("c", "c", "c", "c",
				"c@c.com", "0101", 0);
		String guest2Pwd = "aaaa";

		UserTable.insertUser(this.guest, this.guestPwd);
		UserTable.insertUser(guest2, guest2Pwd);
		ScrollTable.insertScroll(this.scroll);
		assertEquals(guest2.verifyRemoveScrollID(1), 1);
	}

	@Test
	public void testVerifyRemoveScrollIDYes(){
		UserTable.insertUser(this.guest, this.guestPwd);
		ScrollTable.insertScroll(this.scroll);
		assertEquals(guest.verifyRemoveScrollID(1), 2);
	}

	@Test
	public void testVerifyEditScrollIDNull() {
		UserTable.insertUser(this.user, this.userPwd);
		ScrollTable.insertScroll(this.scroll);
		assertEquals(user.verifyEditScrollID(10), 0);
	}

	@Test
	public void testVerifyEditScrollIDNo(){
		RegisteredUser user2 = new RegisteredUser("c", "c", "c", "c",
				"c@c.com", "0101", 0);
		String user2Pwd = "aaaa";

		UserTable.insertUser(this.guest, guestPwd);
		UserTable.insertUser(user2, user2Pwd);
		ScrollTable.insertScroll(this.scroll);
		assertEquals(user2.verifyEditScrollID(1), 1);
	}

	@Test
	public void testVerifyEditScrollIDYes() {
		UserTable.insertUser(this.guest, this.guestPwd);
		ScrollTable.insertScroll(this.scroll);
		assertEquals(guest.verifyEditScrollID(1), 2);
	}
}