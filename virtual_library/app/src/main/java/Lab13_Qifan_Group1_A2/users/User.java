package Lab13_Qifan_Group1_A2.users;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;

public abstract class User
{
	private String userID;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private long uploadCount;

	public User(String userID, String username,
				String firstName, String lastName,
				String email, String phoneNumber, long uploadCount)
	{
		this.userID = userID;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.uploadCount = uploadCount;
	}
	public String getUserID()
	{
		return userID;
	}
	public void setUserID(String userID)
	{
		this.userID = userID;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public long getUploadCount()
	{
		return uploadCount;
		// return UserTable.getUploads(username);
	}
	public void setUploadCount(long uploadCount)
	{
		this.uploadCount = uploadCount;
	}

//	public void viewProfile()
//	{
//		// Unimplemented. Requires database methods
//		if (this.isAdmin())
//		{
//			System.out.print( "===================================================================================" + System.lineSeparator() +
//							  "\tUsername\t\t\tUser ID\t\t\tUploads\t\t\tDownloads\t\t\t" 							+ System.lineSeparator() +
//							//    + System.lineSeparator() +
//							  "===================================================================================" + System.lineSeparator()
//							);
//		}
//	}

	public boolean isSameUser(User crazyDave)
	{
		if (this.getUserID().equals(crazyDave.getUserID()))
		{
			return true;
		}
		return false;
	}

//	public boolean isAdmin()
//	{
//		if (this.getUserID().equals("ADMIN"))
//		{
//			return true;
//		}
//		return false;
//	}
//	public boolean isGuest()
//	{
//		if (this.getUserID().equals("GUEST"))
//		{
//			return true;
//		}
//		return false;
//	}
//
//	public boolean isUser()
//	{
//		if (!this.getUserID().equals("ADMIN") && !this.getUserID().equals("GUEST"))
//		{
//			return true;
//		}
//		return false;
//	}

	public int verifyRemoveScrollID(int scrollID)
	{
		Scroll scroll = ScrollTable.getScroll(scrollID);
		if (scroll == null)
		{
			return 0;
		}
		String authorID = scroll.getAuthor().getUserID();
		if (authorID.equals( this.getUserID()) == false)
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	public int verifyEditScrollID(int scrollID)
	{
		Scroll scroll = ScrollTable.getScroll(scrollID);
		if (scroll == null)
		{
			return 0;
		}
		String authorID = scroll.getAuthor().getUserID();
		if (authorID.equals(this.getUserID()) == false)
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
}
