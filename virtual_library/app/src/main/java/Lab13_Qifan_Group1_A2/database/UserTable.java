package Lab13_Qifan_Group1_A2.database;

import Lab13_Qifan_Group1_A2.users.*;
import static Lab13_Qifan_Group1_A2.database.Database.connectToDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserTable class handles all operations related to the User table
 */
public class UserTable {
	public static void insertUser(User user, String password) {
		String query = """
				INSERT INTO
				User(userID, username, password, phoneNumber, email, firstName, lastName, numUploads, userType)
				VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, user.getUserID());
			ps.setString(2, user.getUsername());
			ps.setString(3, password);
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getFirstName());
			ps.setString(7, user.getLastName());
			ps.setLong(8, user.getUploadCount());

            if (user instanceof Wizard) {
				ps.setString(9, UserType.WIZARD.name());
			} else if (user instanceof Admin) {
				ps.setString(9, UserType.ADMIN.name());
			} else if (user instanceof RegisteredUser) {
				ps.setString(9, UserType.REGISTERED_USER.name());
			} else if (user instanceof Guest) {
				ps.setString(9, UserType.GUEST.name());
			} 

			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static User getUser(String username) {
		String query = """
				SELECT userID, username, password, phoneNumber, email, firstName, lastName, numUploads, userType
				FROM User
				WHERE username = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				return DatabaseUtils.createUserFromQuery(rs);
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static List<User> getAllUsers() {
		String query = """
				SELECT userID, username, password, phoneNumber, email, firstName, lastName, numUploads, userType
				FROM User;
				""";
		List<User> users = new ArrayList<>();

		try (Connection conn = connectToDatabase();
				Statement s = conn.createStatement()) {

			ResultSet rs = s.executeQuery(query);

			while (rs.next()) {
				User user = DatabaseUtils.createUserFromQuery(rs);
				users.add(user);
			}

			return users;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static boolean userExists(String username) {
		return getUser(username) != null;
	}

	public static boolean idExists(String userID) {
		String query = """
				SELECT userID
				FROM User
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, userID);
			return (ps.executeQuery().getString("userID") != null);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

    public static boolean userTypeExists(String userType) {
		String query = """
				SELECT userType
				FROM User
				WHERE userType = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, userType);
			return (ps.executeQuery().getString("userType") != null);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static String getPassword(String username) {
		String query = """
				SELECT password
				FROM User
				WHERE username = ?;
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.isBeforeFirst()) {
				return rs.getString("password");
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static int userCount() {
		String query = """
				SELECT COUNT(userID) AS "count"
				FROM User
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

	public static void deleteUser(String username) {
		String query = """
				DELETE FROM User
				WHERE username = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, username);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void setUploads(String username, long count) {
		String query = """
				UPDATE User
				SET numUploads = ?
				WHERE username = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setLong(1, count);
			ps.setString(2, username);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static long getUploads(String username) {
		String query = """
				SELECT numUploads
				FROM User
				WHERE username = ?
				""";

		try (Connection conn = connectToDatabase();
			 PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong("numUploads");
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return -1;
	}

	public static void updateUserID(String userID, String newID) {
		String query = """
				UPDATE User
				SET userID = ?
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, newID);
			ps.setString(2, userID);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateUsername(String userID, String username) {
		String query = """
				UPDATE User
				SET username = ?
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, username);
			ps.setString(2, userID);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateFirstname(String userID, String firstName) {
		String query = """
				UPDATE User
				SET firstName = ?
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, firstName);
			ps.setString(2, userID);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateLastname(String userID, String lastName) {
		String query = """
				UPDATE User
				SET lastName = ?
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, lastName);
			ps.setString(2, userID);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateEmail(String userID, String email) {
		String query = """
				UPDATE User
				SET email = ?
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, email);
			ps.setString(2, userID);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updatePhoneNumber(String userID, String phoneNumber) {
		String query = """
				UPDATE User
				SET phoneNumber = ?
				WHERE userID = ?
				""";

		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, phoneNumber);
			ps.setString(2, userID);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updatePassword(String username, String password) {
		String query = """
				UPDATE User
				SET password = ?
				WHERE username = ?
				""";
		// System.out.println("Password to 2" + password + ".");
		try (Connection conn = connectToDatabase();
				PreparedStatement ps = conn.prepareStatement(query)) {
			// System.out.println("Password to " + password + ".");
			ps.setString(1, password);
			ps.setString(2, username);
			ps.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			// System.out.println("Password to 3" + password + ".");
			System.out.println(e.getMessage());
		}
	}
}