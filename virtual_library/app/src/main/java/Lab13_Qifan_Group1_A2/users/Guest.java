package Lab13_Qifan_Group1_A2.users;
import Lab13_Qifan_Group1_A2.Scroll;

public class Guest extends User
{
	protected static final String NEWLINE = System.lineSeparator();

	public Guest(String userID, String username, String firstName, String lastName, String email, String phoneNumber, long uploadCount)
	{
		super(userID, username, firstName, lastName, email, phoneNumber, uploadCount);
	}
}