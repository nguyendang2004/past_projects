package Lab13_Qifan_Group1_A2.users;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.*;

public class RegisteredUser extends User
{
	public RegisteredUser(String userID, String username, String firstName, String lastName, String email, String phoneNumber, long uploadCount)
	{
		super(userID, username, firstName, lastName, email, phoneNumber, uploadCount);
	}

	@Override
	public int verifyRemoveScrollID(int scrollID){
		// 0 = no because null, 1 = no because permission, 2 = yes please.
		Scroll h = ScrollTable.getScroll(scrollID);
		if (h == null) {
			return 0;
		}
		User author = h.getAuthor();
		if (!author.getUserID().equals(this.getUserID()))
		{
			return 1;
		}
		else{
			return 2;
		}
	}
}