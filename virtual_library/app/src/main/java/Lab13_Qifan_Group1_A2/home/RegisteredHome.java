package Lab13_Qifan_Group1_A2.home;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.FileWriter;
import java.io.IOException;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.TextEditor;
import Lab13_Qifan_Group1_A2.database.*;
import Lab13_Qifan_Group1_A2.parsing.*;
import Lab13_Qifan_Group1_A2.users.*;
import Lab13_Qifan_Group1_A2.hasher.*;

/* UserHome
 * Includes all functions related to the user home screen
 */
public class RegisteredHome extends GuestHome {
    private static final String ROOT = "." + File.separator + "uploads" + File.separator;
    private static final String DOWNLOADS = "." + File.separator + "downloads" + File.separator;
    private static final String WORKDIR = System.getProperty("user.dir");


	public RegisteredHome(Parser parser)
	{
		super(parser);
	}

	@Override
	public void displayScreen()
	{
		System.out.print(String.join(NEWLINE,
		"-- Home --",
		"> Add scroll\t\t(addScroll)",
		"> Edit scroll\t\t(editScroll)",
		"> Remove scroll\t\t(removeScroll)",
		"> View scrolls\t\t(viewScrolls)",
        "> Download scrolls\t(downloadScroll)",
		"> Edit user details\t(editDetails)",
		"> Search scrolls\t(searchScroll)",
		"> Preview scrolls\t(preview)",
		"> Logout\t\t(logout)",
		"====================================================================", NEWLINE));
	}

    /*
     * addScroll
     * Adds a scroll to the library under the profile of the author
     * who uploaded it. The user will be prompted to insert the
     * contents of the file, the path to the file, and the file
     * name itself. A scroll is valid if:
     *    1. The contents are binary
     *    2. The scroll name does not already exist
     * The root upload folder is app/uploads. If a file has been
     * removed from the library but a user uploads to the same path,
     * overwrite the old contents.
     * @param user: the User who is uploading the file
     */
	@Override
	public void addScroll(User user) {
		// Retrieving the input and making the scroll
		ArrayList<String> output = parser.getParameter(Command.addScroll);
        String content = output.get(0);
        String path = output.get(1);
        String name = output.get(2);
		Date date = new java.sql.Date(System.currentTimeMillis());
		Scroll inputScroll = new Scroll(-1, date, name, user, path, 0);

        // Parsing and verifying the input
        if (ScrollTable.scrollExists(name)) {
            System.out.println("This scroll name already exists. Please choose another one.");
            return;
        }
		parser.printConfirm(Command.addScroll, output);
		if (!parser.confirm()){
			System.out.println("Ok, the scroll was not added");
            return;
		}

        // Writing the scroll
        if (path.indexOf('/') != -1) {         // Ensure it works on all OS
            path = path.replace("/", File.separator);
        }
        if (path.indexOf('\\') != -1) {
            path = path.replace("\\", File.separator);
        }
        try {
            // Create the scroll
            String pathname = ROOT+path+File.separator+name+".bin";
            Path newScroll = Paths.get(pathname);
            if (!Files.exists(newScroll)) {
                Files.createDirectories(newScroll.getParent());
                Files.createFile(newScroll);
            }

            // Write the contents into it
            FileWriter writer = new FileWriter(pathname, false);
            writer.write(content);
            writer.close();

            // Inserting the scroll
            inputScroll.setPath(pathname);
            ScrollTable.insertScroll(inputScroll);
            user.setUploadCount(user.getUploadCount() + 1);
            UserTable.setUploads(user.getUsername(), user.getUploadCount());
            System.out.printf("%s added to .%s"+NEWLINE, name,
                              File.separator+"uploads"+File.separator+path);
        } 
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
	}

	@Override
	public void removeScroll(User user)
	{
		ArrayList<String> output = parser.getParameter(Command.removeScroll);
        int scrollID = Integer.parseInt(output.get(0));
		parser.printConfirm(Command.removeScroll, output);
		
		if (parser.confirm()) {
			if (user.verifyRemoveScrollID(scrollID) == 0) {
				System.out.println("Invalid scrollID");
			}
			else if (user.verifyRemoveScrollID(scrollID) == 1) {
				System.out.println("You are not allowed to remove this.");
			}
			else {
				ScrollTable.removeScroll(scrollID);
				System.out.println("Scroll removed!");
			}
		}
	}

	@Override
	public void editDetails(User user)
	{
		viewUserDetails(user);

		ArrayList<String> output = parser.getParameter(Command.editOption);

		String userID = user.getUserID();
		String field = output.get(0);
		if (field.equals("userid"))
		{
			ArrayList<String> values = parser.getParameter(Command.userid);
			String value = values.get(0);
			if (UserTable.idExists(value))
			{
				System.out.println("ID already exists! Please choose another one" + NEWLINE);
			}
			else
			{
				UserTable.updateUserID(userID, value);
				user.setUserID(value);
				System.out.println("User ID changed to " + value + ".");
			}
		}
		else if (field.equals("username"))
		{
			ArrayList<String> values = parser.getParameter(Command.username);
			String value = values.get(0);
			if (UserTable.userExists(value)) {
				System.out.println("Username already exists! Please choose another one" + NEWLINE);
			}
			else {
                // If the user has a downloads folder already, change its name
                File dir = new File(DOWNLOADS + user.getUsername());
                if (dir.isDirectory()) {
                    File newDir = new File(dir.getParent()+File.separator+value);
                    dir.renameTo(newDir);
                } 

				UserTable.updateUsername(userID, value);
				List<Scroll> scrolls = ScrollTable.getUserScrolls(user.getUsername());
				user.setUsername(value);
				for (Scroll scroll : scrolls)
				{
					scroll.setAuthor(user);
					ScrollTable.updateScroll(scroll);
				}
				System.out.println("Username changed to " + value + ".");
			}
		}
		else if (field.equals("firstname"))
		{
			ArrayList<String> values = parser.getParameter(Command.firstname);
			String value = values.get(0);
			UserTable.updateFirstname(userID, value);
			user.setFirstName(value);
			for (Scroll scroll : ScrollTable.getUserScrolls(user.getUsername()))
			{
				scroll.setAuthor(user);
			}
			System.out.println("Firstname changed to " + value + ".");
		}
		else if (field.equals("lastname"))
		{
			ArrayList<String> values = parser.getParameter(Command.lastname);
			String value = values.get(0);
			UserTable.updateLastname(userID, value);
			user.setLastName(value);
			for (Scroll scroll : ScrollTable.getUserScrolls(user.getUsername()))
			{
				scroll.setAuthor(user);
			}
			System.out.println("Lastname changed to " + value + ".");

		}
		else if (field.equals("email"))
		{
			ArrayList<String> values = parser.getParameter(Command.email);
			String value = values.get(0);
			UserTable.updateEmail(userID, value);
			System.out.println("Email changed!");
		}
		else if (field.equals("phoneNumber"))
		{
			ArrayList<String> values = parser.getParameter(Command.phonenumber);
			String value = values.get(0);
			UserTable.updatePhoneNumber(userID, value);
			System.out.println("Phone number changed to " + value + ".");
		}
		else if (field.equals("password"))
		{
			ArrayList<String> values = parser.getParameter(Command.password);
			String value = Hasher.createHash(values.get(0));
			UserTable.updatePassword(user.getUsername(), value);
			System.out.println("Password has been changed!");
		}
		else
		{
			System.out.print("Enter the correct field name you illiterate!");
		}
	}

	public void viewUserDetails(User user)
	{
		User updatedUser = UserTable.getUser(user.getUsername());
		String userDetails= String.format("> Username: %-35s(username)", updatedUser.getUsername()) + NEWLINE + 
							String.format("> UserID: %-37s(userid)", updatedUser.getUserID()) + NEWLINE + 
							String.format("> First name: %-33s(firstname)", updatedUser.getFirstName()) + NEWLINE + 
							String.format("> Last name: %-34s(lastname)", updatedUser.getLastName()) + NEWLINE + 
							String.format("> Email: %-38s(email)", updatedUser.getEmail()) + NEWLINE + 
							String.format("> Phone number: %-31s(phoneNumber)", updatedUser.getPhoneNumber()) + NEWLINE + 
							String.format("> Password: ********%27s(password)", "") + NEWLINE;
		System.out.print(userDetails);
		System.out.print("Please select a field to edit." + NEWLINE);
	}

    /*
     * downloadScroll
     * Downloads a scroll to the downloads folder of the current
     * user. The user will simply be prompted to input the name
     * of the scroll to download. This will then print the
     * path to the downloaded scroll. A download is valid if:
     *    1. The scroll exists in the library
     * The root download folder for each user is app/downloads/<username>.
     * If a downloaded scroll has been downloaded before, it will
     * just be overwritten
     */
    @Override
    public void downloadScroll(String username) {
		String name = parser.getParameter(Command.downloadScroll).get(0);
        if (!ScrollTable.scrollExists(name)) {
            System.out.println("This scroll does not exist!");
            return;
        }
        Scroll scroll = ScrollTable.getScroll(name);
        Path source = Paths.get(scroll.getPath());    // Scroll to copy

        // Preview
		scrollPreview(scroll, source);
        if (!parser.confirm()) {
            System.out.println("Ok, the scroll will not be downloaded");
            return;
        }

        try {
            // Create a folder for the user if they don't have one already
            Path userFolder = Paths.get(String.format(DOWNLOADS + "%s", username));
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
            }

            // Copy the file
            Path target = Paths.get(userFolder+File.separator+name+".bin");
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            // Update downloads
            scroll.setNumDownloads(scroll.getNumDownloads() + 1);
            ScrollTable.updateScroll(scroll);
            System.out.printf("%s downloaded to %s!"+NEWLINE, name, 
                              WORKDIR+File.separator+"downloads"+File.separator+username);
        }
        catch (IOException e) {
            System.out.println("You shouldn't be seeing this..."+e.getMessage());
        }
    }



	@Override
	public void editScroll(User user)
	{
		ArrayList<String> output = parser.getParameter(Command.editScroll);
		int scrollID = Integer.parseInt(output.get(0));
		parser.printConfirm(Command.editScroll, output);

		if (parser.confirm()) {
			if (user.verifyEditScrollID(scrollID) == 0) {
				System.out.println("Invalid scrollID");
			}
			else if (user.verifyEditScrollID(scrollID) == 1 && !(user instanceof Wizard)) {
				System.out.println("You are not allowed to edit this.");
			}
			else {
				//take the content of the scroll
				Scroll target = ScrollTable.getScroll(scrollID);
				try
				{
					String content = target.getContent();
					System.out.println(content);
					TextEditor textEditor = new TextEditor(parser, content);

					String edit = textEditor.edit();
					try
					{
						FileWriter writer = new FileWriter(target.getPath());
						writer.write(edit);
						writer.close();
						System.out.println("Scroll edited!");
					}
					catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
				catch (NoSuchElementException e)
				{
					System.out.println("Scroll is empty!");
				}
			}
		}
	}
}