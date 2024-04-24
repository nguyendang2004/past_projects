package Lab13_Qifan_Group1_A2.users;
import Lab13_Qifan_Group1_A2.users.User;
import Lab13_Qifan_Group1_A2.database.UserTable;

public class Admin extends User
{
	public Admin(String userID, String username,
				 String firstName, String lastName,
				 String email, String phoneNumber, long uploadCount)
	{
		super(userID, username, firstName, lastName, email, phoneNumber, uploadCount);
	}
}