package Lab13_Qifan_Group1_A2.users;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.ScrollTable;

public class Wizard extends Admin
{
	public Wizard(String userID, String username, String firstName, String lastName, String email, String phoneNumber, long uploadCount)
	{
		super(userID, username, firstName, lastName, email, phoneNumber, uploadCount);
	}
	
	@Override
	public int verifyEditScrollID(int scrollID) {
		Scroll h = ScrollTable.getScroll(scrollID);
		if (h == null){//can't find scroll
			return 0;
		}
		return 2;
	}
}
