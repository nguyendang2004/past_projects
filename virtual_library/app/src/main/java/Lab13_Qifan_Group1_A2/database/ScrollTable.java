package Lab13_Qifan_Group1_A2.database;

import Lab13_Qifan_Group1_A2.Scroll;
import static Lab13_Qifan_Group1_A2.database.Database.connectToDatabase;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScrollTable class handles operations related to Scroll table
 */
public class ScrollTable {
    public static void insertScroll(Scroll scroll) {
        String query =
                """
                INSERT INTO
                Scroll(name, author, uploadDate, numDownloads, path)
                VALUES(?, ?, ?, ?, ?);
                """;

        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, scroll.getName());
            ps.setString(2, scroll.getAuthor().getUsername());
            ps.setString(3, DatabaseUtils.convertDateToString(scroll.getDate()));
            ps.setInt(4, scroll.getNumDownloads());
            ps.setString(5, scroll.getPath());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Scroll getScroll(String name) {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                WHERE name = ?
                """;

        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                return DatabaseUtils.createScroll(rs);
            }
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Scroll getScroll(int scrollID) {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                WHERE scrollID = ?
                """;

        try (Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, scrollID);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                return DatabaseUtils.createScroll(rs);
            }
        } catch (SQLException | ParseException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Scroll> getAllScrolls() {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll;
                """;

        try (Connection conn = connectToDatabase();
             Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery(query);
            return DatabaseUtils.createScrollList(rs);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Scroll> getUserScrolls(String username) {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                WHERE author = ?;
                """;

        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return DatabaseUtils.createScrollList(rs);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean scrollExists(String name) {
        return getScroll(name) != null;
    }

    public static boolean scrollExists(int scrollID) {
        return getScroll(scrollID) != null;
    }

    public static int scrollCount() {
        String query =
                """
                SELECT COUNT(scrollID) AS "count"
                FROM Scroll
                """;

        try (Connection conn = connectToDatabase();
             Statement s = conn.createStatement()) {

            ResultSet rs = s.executeQuery(query);
            return rs.getInt("count");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static Scroll getRandomScroll() {
        if (scrollCount() == 0) {
            // No scrolls in table, so cannot get random scroll.
            return null;
        }

        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                ORDER BY RANDOM()
                LIMIT 1;
                """;
        try (Connection conn = connectToDatabase();
            Statement s = conn.createStatement()) {
            return DatabaseUtils.createScroll(s.executeQuery(query));
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void removeScroll(String name) {
        String query =
                """
                DELETE FROM Scroll
                WHERE name = ?
                """;
        try (Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeScroll(int scrollID) {
        String query =
                """
                DELETE FROM Scroll
                WHERE scrollID = ?
                """;
        try (Connection conn = connectToDatabase();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, scrollID);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * You can update everything except scrollID
     * @param updatedScroll defines all new scroll attributes for that specific scrollID
     */
    public static void updateScroll(Scroll updatedScroll) {
        String query =
                """
                Update Scroll
                SET name = ?, author = ?, uploadDate = ?, numDownloads = ?, path = ?
                WHERE scrollID = ?
                """;
        try (Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, updatedScroll.getName());
            ps.setString(2, updatedScroll.getAuthor().getUsername());
            ps.setString(3, DatabaseUtils.convertDateToString(updatedScroll.getDate()));
            ps.setInt(4, updatedScroll.getNumDownloads());
            ps.setString(5, updatedScroll.getPath());
            ps.setInt(6, updatedScroll.getScrollID());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Scroll> searchScrollByUploaderID(String uploaderID) {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll S JOIN User U ON (S.author = U.username)
                WHERE userID = ?
                """;
        try(Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uploaderID);
            ResultSet rs = ps.executeQuery();
            return DatabaseUtils.createScrollList(rs);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Scroll> searchScrollByScrollID(String scrollID) {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                WHERE scrollID = ?
                """;
        try(Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, scrollID);
            ResultSet rs = ps.executeQuery();
            return DatabaseUtils.createScrollList(rs);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Scroll> searchScrollByName(String scrollName) {
        String query =
                """
                SELECT scrollID, name, author, uploadDate, numDownloads, path
                FROM Scroll
                WHERE name = ?
                """;
        try(Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, scrollName);
            ResultSet rs = ps.executeQuery();
            return DatabaseUtils.createScrollList(rs);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Scroll> searchScrollByUploadDate(String uploadDate) {
        String query =
               """
               SELECT scrollID, name, author, uploadDate, numDownloads, path
               FROM Scroll
               WHERE uploadDate LIKE ?
               """;
        try(Connection conn = connectToDatabase();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uploadDate + "%");
            ResultSet rs = ps.executeQuery();
            return DatabaseUtils.createScrollList(rs);
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
