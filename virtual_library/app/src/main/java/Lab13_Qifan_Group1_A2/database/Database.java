package Lab13_Qifan_Group1_A2.database;
import java.sql.*;

/**
 * Database class responsible for handling the creation and dropping of all tables,
 * and allowing clients to connect to the database
 */
public class Database {
    private static final String DB_PATH = "jdbc:sqlite:database.db";
    private static final String CLASS_NAME = "org.sqlite.JDBC";
    private static final String[] TABLE_NAMES = {"User", "Scroll"};
    private static final String[] CREATE_TABLES = {
            """
            CREATE TABLE IF NOT EXISTS User(
                userID TEXT PRIMARY KEY,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                phoneNumber TEXT NOT NULL,
                email TEXT NOT NULL,
                firstName TEXT NOT NULL,
                lastName TEXT NOT NULL,
                numUploads INTEGER NOT NULL,
                userType TEXT NOT NULL
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS Scroll(
                scrollID INTEGER PRIMARY KEY,
                name TEXT UNIQUE NOT NULL,
                author TEXT NOT NULL,
                uploadDate TEXT NOT NULL,
                numDownloads INTEGER NOT NULL,
                path TEXT NOT NULL,
                FOREIGN KEY (author) REFERENCES User(username)
            );
            """
    };

    /**
     * Allows connection to the database
     * @return Connection object
     * @throws ClassNotFoundException if JDBC class cannot be registered
     * @throws SQLException if error occurs getting connection object
     */
    public static Connection connectToDatabase()
            throws ClassNotFoundException, SQLException {
        // Registers JDBC driver
        Class.forName(CLASS_NAME);
        // Returns connection object
        return DriverManager.getConnection(DB_PATH);
    }

    public static boolean createTables() {
        for (String table : CREATE_TABLES) {
            try (Connection conn = connectToDatabase();
                Statement s = conn.createStatement()) {
                s.execute(table);
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean dropTables() {
        for (String name : TABLE_NAMES) {
            try (Connection conn = connectToDatabase();
                 Statement s = conn.createStatement()) {
                s.executeUpdate("DROP TABLE IF EXISTS " + name + ";");
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }
}
