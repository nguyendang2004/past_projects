package Lab13_Qifan_Group1_A2.database;

import Lab13_Qifan_Group1_A2.*;
import Lab13_Qifan_Group1_A2.users.*;

import java.sql.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseUtils {
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static String convertDateToString(Date date) {
        Format formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    public static Date convertStringToDate(String str)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.parse(str);
    }

    public static boolean isWizard(String userType) {
        return userType.equals(UserType.WIZARD.name());
    }

    public static boolean isAdmin(String userType) {
        return userType.equals(UserType.ADMIN.name());
    }

    public static boolean isGuest(String userType) {
        return userType.equals(UserType.GUEST.name());
    }

    public static boolean isUser(String userType) {
        return userType.equals(UserType.REGISTERED_USER.name());
    }

    public static User createUserFromQuery(ResultSet rs)
            throws SQLException {
        String userID = rs.getString("userID");
        String username = rs.getString("username");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("email");
        String phoneNumber = rs.getString("phoneNumber");
        long uploadCount = rs.getLong("numUploads");
        String userTypeStr = rs.getString("userType");

        if (isWizard(userTypeStr)) {
            return new Wizard(userID, username, firstName,
                    lastName, email, phoneNumber, uploadCount);
        } else if (isAdmin(userTypeStr)) {
            return new Admin(userID, username, firstName,
                    lastName, email, phoneNumber, uploadCount);
        } else if (isGuest(userTypeStr)) {
            return new Guest(userID, username, firstName,
                    lastName, email, phoneNumber, uploadCount);
        } else if (isUser(userTypeStr)) {
            return new RegisteredUser(userID, username, firstName,
                    lastName, email, phoneNumber, uploadCount);
        } 
        return null;
    }

    public static Scroll createScroll(ResultSet rs)
            throws SQLException, ParseException {
        return new Scroll(
                rs.getInt("scrollID"),
                convertStringToDate(rs.getString("uploadDate")),
                rs.getString("name"),
                UserTable.getUser(rs.getString("author")),
                rs.getString("path"),
                rs.getInt("numDownloads")
        );
    }

    public static List<Scroll> createScrollList(ResultSet rs)
            throws SQLException, ParseException {
        List<Scroll> scrolls = new ArrayList<>();

        while (rs.next()) {
            Scroll scroll = createScroll(
                    rs
            );
            scrolls.add(scroll);
        }
        return scrolls;
    }
}
