package Lab13_Qifan_Group1_A2.databaseTest;

import Lab13_Qifan_Group1_A2.*;
import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.DatabaseUtils;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.users.Admin;
import Lab13_Qifan_Group1_A2.users.Guest;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Lab13_Qifan_Group1_A2.database.Database.connectToDatabase;
import static org.junit.Assert.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DatabaseUtilsTest {
    private final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private final String username = "abc";
    private final String firstName = "joe";
    private final String lastName = "bob";
    private final String email = "joe@joe.com";
    private final String phoneNumber = "04343";
    private final long uploadCount = 5;

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
    public void testConvertDateToString() {
        Date date = new Date();

        String expected = new SimpleDateFormat(DATE_FORMAT).format(date);
        String actual = DatabaseUtils.convertDateToString(date);
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertStringToDate() throws ParseException {
        String dateStr = "10/07/2015 10:30:11";

        Date expected = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
        Date actual = DatabaseUtils.convertStringToDate(dateStr);
        assertEquals(expected, actual);
    }

    @Test
    public void testIsAdmin() {
        assertTrue(DatabaseUtils.isAdmin("ADMIN"));
        assertFalse(DatabaseUtils.isAdmin("dfadfdaf"));
    }

    @Test
    public void testIsGuest() {
        assertTrue(DatabaseUtils.isGuest("GUEST"));
        assertFalse(DatabaseUtils.isGuest("ADMIN"));
    }

    @Test
    public void testIsUser() {
        assertTrue(DatabaseUtils.isUser("REGISTERED_USER"));
        assertFalse(DatabaseUtils.isUser("GUEST"));
        assertFalse(DatabaseUtils.isUser("ADMIN"));
    }

//    @Test
//    public void testCreateAdminUserObject() {
//        String userID = "ADMIN";
//
//        User expected = new Admin(
//                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
//
//        User actual = DatabaseUtils.createUserObject(
//                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
//
//        assertNotNull(actual);
//        assertTrue(expected.isSameUser(actual));
//    }
//
//    @Test
//    public void testCreateGuestUserObject() {
//        String userID = "GUEST";
//
//        User expected = new Guest(
//                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
//
//        User actual = DatabaseUtils.createUserObject(
//                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
//
//        assertNotNull(actual);
//        assertTrue(expected.isSameUser(actual));
//    }
//
//    @Test
//    public void testCreateRegisteredUserObject() {
//        String userID = "dfdfd";
//
//        User expected = new RegisteredUser(
//                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
//
//        User actual = DatabaseUtils.createUserObject(
//                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
//
//        assertNotNull(actual);
//        assertTrue(expected.isSameUser(actual));
//    }

    @Test
    public void testCreateAdminFromQuery() throws SQLException, ClassNotFoundException {
        String userID = "dfdfd";

        User expected = new Admin(
                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
        UserTable.insertUser(expected, "bob");
        UserTable.getUser(username);

        String query =
                """
                SELECT userID, username, password, phoneNumber, email, firstName, lastName, numUploads, userType
                FROM User
                WHERE username = ?
                """;

        ResultSet rs;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            rs = ps.executeQuery();

            assertNotNull(rs);
            User actual = DatabaseUtils.createUserFromQuery(rs);
            assertNotNull(actual);
            assertTrue(expected.isSameUser(actual));
            assertTrue(actual instanceof Admin);
        }
    }

    @Test
    public void testCreateGuestFromQuery() throws SQLException, ClassNotFoundException {
        String userID = "dfdfd";

        User expected = new Guest(
                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
        UserTable.insertUser(expected, "bob");
        UserTable.getUser(username);

        String query =
                """
                SELECT userID, username, password, phoneNumber, email, firstName, lastName, numUploads, userType
                FROM User
                WHERE username = ?
                """;

        ResultSet rs;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            rs = ps.executeQuery();

            assertNotNull(rs);
            User actual = DatabaseUtils.createUserFromQuery(rs);
            assertNotNull(actual);
            assertTrue(expected.isSameUser(actual));
            assertTrue(actual instanceof Guest);
        }
    }

    @Test
    public void testCreateRegisteredUserFromQuery() throws SQLException, ClassNotFoundException {
        String userID = "dfdfd";

        User expected = new RegisteredUser(
                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
        UserTable.insertUser(expected, "bob");
        UserTable.getUser(username);

        String query =
                """
                SELECT userID, username, password, phoneNumber, email, firstName, lastName, numUploads, userType
                FROM User
                WHERE username = ?
                """;

        ResultSet rs;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            rs = ps.executeQuery();

            assertNotNull(rs);
            User actual = DatabaseUtils.createUserFromQuery(rs);
            assertNotNull(actual);
            assertTrue(expected.isSameUser(actual));
            assertTrue(actual instanceof RegisteredUser);
        }
    }

    @Test
    public void testCreateScroll()
            throws SQLException, ClassNotFoundException, ParseException {
        String userID = "dfdfd";
        int scrollID = 1;

        User user = new RegisteredUser(
                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
        UserTable.insertUser(user, "bob");

        Scroll expected = new Scroll(scrollID, new Date(), "first", user, "010101", 1);
        ScrollTable.insertScroll(expected);

        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                WHERE scrollID = ?
                """;

        ResultSet rs;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, scrollID);
            rs = ps.executeQuery();

            assertNotNull(rs);
            Scroll actual = DatabaseUtils.createScroll(rs);
            assertNotNull(actual);
            assertTrue(expected.isSameScroll(actual));
        }
    }

    @Test
    public void testCreateScrollList()
            throws SQLException, ClassNotFoundException, ParseException {
        String userID = "dfdfd";

        User user = new RegisteredUser(
                userID, username, firstName, lastName, email, phoneNumber, uploadCount);
        UserTable.insertUser(user, "bob");

        Scroll scroll1 = new Scroll(1, new Date(), "first", user, "010101", 1);
        ScrollTable.insertScroll(scroll1);
        Scroll scroll2 = new Scroll(2, new Date(), "second", user, "010101", 2);
        ScrollTable.insertScroll(scroll2);

        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                """;

        ResultSet rs;
        List<Scroll> scrolls;
        try (Connection conn = connectToDatabase();
             Statement s = conn.createStatement()) {
            rs = s.executeQuery(query);
            scrolls = DatabaseUtils.createScrollList(rs);
        }

        boolean scroll1Visited = false;
        boolean scroll2Visited = false;
        for (Scroll scroll : scrolls) {
            if (!scroll1Visited && scroll.getScrollID() == scroll1.getScrollID()) {
                scroll1Visited = true;
                assertTrue(scroll.isSameScroll(scroll1));
            } else if (!scroll2Visited && scroll.getScrollID() == scroll2.getScrollID()) {
                scroll2Visited = true;
                assertTrue(scroll.isSameScroll(scroll2));
            }
        }
        assertTrue(scroll1Visited);
        assertTrue(scroll2Visited);
    }
}
