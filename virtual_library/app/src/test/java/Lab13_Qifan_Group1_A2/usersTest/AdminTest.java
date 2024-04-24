package Lab13_Qifan_Group1_A2.usersTest;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.users.Admin;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;
import Lab13_Qifan_Group1_A2.hasher.*;

public class AdminTest {
//	private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
//	private final PrintStream sysOut = System.out;
//
//	@Before
//	public void setUp()
//	{
//		System.setOut(new PrintStream(testOut));
//		// Database.dropTables();
//		// Database.createTables();
//		// User user1 = new RegisteredUser("111", "qqq", "aaa", "aaa", "aaa", "0", 0);
//		// User user2 = new RegisteredUser("222", "www", "aaa", "aaa", "aaa", "0", 0);
//		// User user3 = new RegisteredUser("333", "qqq", "aaa", "aaa", "aaa", "0", 0);
//		// User user4 = new RegisteredUser("444", "qqq", "aaa", "aaa", "aaa", "0", 0);
//		// User user5 = new RegisteredUser("555", "qqq", "aaa", "aaa", "aaa", "0", 0);
//		// UserTable.insertUser(user1, Hasher.createHash("xxx"));
//		// UserTable.insertUser(user2, Hasher.createHash("xxx"));
//		// UserTable.insertUser(user3, Hasher.createHash("xxx"));
//		// UserTable.insertUser(user4, Hasher.createHash("xxx"));
//		// UserTable.insertUser(user5, Hasher.createHash("xxx"));
//	}
//
//	@After
//	public void restoreStreams() {
//		System.setOut(sysOut);
//		Database.dropTables();
//	}

	@Test
	public void testConstructor() {
		User admin = new Admin("a", "a", "a", "a",
				"a@a.com", "101010", 0);
		assertNotNull(admin);
	}
}