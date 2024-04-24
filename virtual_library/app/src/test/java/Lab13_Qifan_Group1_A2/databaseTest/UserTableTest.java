package Lab13_Qifan_Group1_A2.databaseTest;

import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.hasher.Hasher;
import Lab13_Qifan_Group1_A2.users.Admin;
import Lab13_Qifan_Group1_A2.users.Guest;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import java.util.List;

public class UserTableTest {
    // Test user 1
    private final String userID1 = "dfdfd";
    private final String username1 = "bob123";
    private final String firstName1 = "bob";
    private final String lastName1 = "bees";
    private final String email1 = "bob@bees.com";
    private final String phoneNumber1 = "0134";
    private final long uploadCount1 = 1;

    // Test user 2
    private final String userID2 = "ADMIN";
    private final String username2 = "admin";
    private final String firstName2 = "ad";
    private final String lastName2 = "min";
    private final String email2 = "ad@min.com";
    private final String phoneNumber2 = "01343243";
    private final long uploadCount2 = 0;

    @Before
    public void prepare() {
        Database.dropTables();
        Database.createTables();
    }

    @After
    public void cleanUp() {
        Database.dropTables();
    }

    @Test
    public void testInsertRegisteredUser() {
        User expected = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");

        User actual = UserTable.getUser(username1);
        assertNotNull(actual);
        assertTrue(expected.isSameUser(actual));
        assertTrue(actual instanceof RegisteredUser);
    }

    @Test
    public void testInsertGuest() {
        User expected = new Guest(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");

        User actual = UserTable.getUser(username1);
        assertNotNull(actual);
        assertTrue(expected.isSameUser(actual));
        assertTrue(actual instanceof Guest);
    }

    @Test
    public void testInsertAdmin() {
        User expected = new Admin(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");

        User actual = UserTable.getUser(username1);
        assertNotNull(actual);
        assertTrue(expected.isSameUser(actual));
        assertTrue(actual instanceof Admin);
    }

    @Test
    public void testGetUser() {
        // testInsertUser already tested getting an actual user
        // so this will test if user doesn't exist
        assertNull(UserTable.getUser("bob"));
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);

        User user2 = new Admin(
                userID2, username2, firstName2, lastName2, email2, phoneNumber2, uploadCount2);

        UserTable.insertUser(user1, "bob");
        UserTable.insertUser(user2, "boss");

        boolean user1Visited = false;
        boolean user2Visited = false;
        List<User> users = UserTable.getAllUsers();
        assertNotNull(users);

        for (User user : users) {
            if (!user1Visited && user.getUserID().equals(user1.getUserID())) {
                user1Visited = true;
                assertTrue(user.isSameUser(user1));
            } else if (!user2Visited && user.getUserID().equals(user2.getUserID())) {
                user2Visited = true;
                assertTrue(user.isSameUser(user2));
            }
        }
        assertTrue(user1Visited);
        assertTrue(user2Visited);
    }

    @Test
    public void testUserExists() {
        User expected = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");
        assertTrue(UserTable.userExists(expected.getUsername()));
        assertFalse(UserTable.userExists("don't exist"));
    }

    @Test
    public void testGetPassword() {
        User expected = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");

        assertEquals(UserTable.getPassword(expected.getUsername()), "bob");
        assertNull(UserTable.getPassword("non-exist"));
    }

    @Test
    public void testUserCount() {
        assertEquals(0, UserTable.userCount());

        User expected = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");
        assertEquals(1, UserTable.userCount());
    }

    @Test
    public void testIdExists() {
        User expected = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");

        boolean actual = UserTable.idExists(userID1);
        assertTrue(actual);
    }

    @Test
    public void testIdDoesNotExists() {
        User expected = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(expected, "bob");

        boolean actual = UserTable.idExists("babooey");
        assertFalse(actual);
    }

    @Test
    public void testDeleteUser() {
        User toDelete = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(toDelete, "bob");
        assertTrue(UserTable.userExists(toDelete.getUsername()));
        assertTrue(UserTable.idExists(toDelete.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.deleteUser(toDelete.getUsername());
        assertFalse(UserTable.userExists(toDelete.getUsername()));
        assertFalse(UserTable.idExists(toDelete.getUserID()));
        assertEquals(0+0, UserTable.userCount());
    }

    @Test
    public void testSetUploads() {
        User uploader = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(uploader, "bob");
        assertTrue(UserTable.userExists(uploader.getUsername()));
        assertTrue(UserTable.idExists(uploader.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.setUploads(username1, 12);
        uploader = UserTable.getUser(username1);
        assertEquals(12, uploader.getUploadCount());
    }

    @Test
    public void testGetUploads() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.setUploads(username1, 12);
        long uploads = UserTable.getUploads(username1);
        assertEquals(12, uploads);
    }

    @Test
    public void testUpdateUserID() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.updateUserID(userID1, "newID");
        User returnedUser = UserTable.getUser(username1);
        assertNotNull(returnedUser);
        String userID = returnedUser.getUserID();
        assertEquals("newID", userID);
    }

    @Test
    public void testUpdateUsername() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.updateUsername(userID1, "newUsername");
        User returnedUser = UserTable.getUser("newUsername");
        assertNotNull(returnedUser);
        String username = returnedUser.getUsername();
        assertEquals("newUsername", username);
    }

    @Test
    public void testUpdateFirstname() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.updateFirstname(userID1, "newFirstName");
        User returnedUser = UserTable.getUser(username1);
        assertNotNull(returnedUser);
        String firstname = returnedUser.getFirstName();
        assertEquals("newFirstName", firstname);
    }

    @Test
    public void testUpdateLastname() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.updateLastname(userID1, "newLastName");
        User returnedUser = UserTable.getUser(username1);
        assertNotNull(returnedUser);
        String lastname = returnedUser.getLastName();
        assertEquals("newLastName", lastname);
    }

    @Test
    public void testUpdateEmail() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.updateEmail(userID1, "newEmail");
        User returnedUser = UserTable.getUser(username1);
        assertNotNull(returnedUser);
        String email = returnedUser.getEmail();
        assertEquals("newEmail", email);
    }

    @Test
    public void testUpdatePhoneNumber() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        UserTable.updatePhoneNumber(userID1, "10101010100101010");
        User returnedUser = UserTable.getUser(username1);
        assertNotNull(returnedUser);
        String phoneNumber = returnedUser.getPhoneNumber();
        assertEquals("10101010100101010", phoneNumber);
    }

    @Test
    public void testUpdatePassword() {
        User user = new RegisteredUser(
                userID1, username1, firstName1, lastName1, email1, phoneNumber1, uploadCount1);
        UserTable.insertUser(user, "bob");
        assertTrue(UserTable.userExists(user.getUsername()));
        assertTrue(UserTable.idExists(user.getUserID()));
        assertEquals(1, UserTable.userCount());

        String pwdHash = Hasher.createHash("newPassword");
        UserTable.updatePassword(username1, pwdHash);

        String actualPwdHash = UserTable.getPassword(username1);
        assertEquals(pwdHash, actualPwdHash);
    }
}
