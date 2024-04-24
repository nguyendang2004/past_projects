package Lab13_Qifan_Group1_A2.parsing;
public enum Command {

	// Start page commands
	guest(new String[] {}, new String[] {}),
	login(new String[] {"P", "P"}, new String[] {"username", "password"}),
	register(new String[] {"PA", "PA", "P", "PL", "PL", "P", "PD"}, new String[]{"ID", "username", "password", "firstname", "lastname", "email", "phone number"}),
	exit(new String[] {}, new String[] {}),

	// Admin only commands
	removeAccount (new String[] {"PA"}, new String[]{"username of the account that you want to remove"}),
	addAccount(new String[] {"PA", "PL", "PL", "PA", "PD", "P"}, new String[]{"username", "firstname", "lastname", "password", "phone number", "email"}),
	viewProfiles(new String[] {}, new String[] {}),
	viewUploads(new String[] {}, new String[] {}),
	viewDownloads(new String[] {}, new String[] {}),
	
	// User commands
	addScroll(new String[] {"PB", "F", "PA"}, new String[] {"content", "path to the directory where you want to store your scroll", "scroll name"}),
	viewProfile(new String[]{"PA"}, new String[] {"username"}),
	removeScroll(new String[] {"PD"}, new String[] {"scroll ID"}),
	editDetails(new String[] {"P", "P", "P", "P", "PD", "P"}, new String[]{"username", "firstname", "lastname", "password", "phone number", "email"}),
	downloadScroll(new String[] {"PA"}, new String[]{"scroll name"}),
	preview(new String[] {"PD"}, new String[]{"scroll ID"}),

	editOption(new String[] {"PL"}, new String[]{"field to change"}),
	userid(new String[] {"PA"}, new String[]{"new userid"}),
	username(new String[] {"PA"}, new String[]{"new username"}),
	firstname(new String[] {"PL"}, new String[]{"new firstname"}),
	lastname(new String[] {"PL"}, new String[]{"new lastname"}),
	password(new String[] {"PA"}, new String[]{"new password"}),
	phonenumber(new String[] {"PD"}, new String[]{"new phoneNumber"}),
	email(new String[] {"P"}, new String[]{"new email"}),

	//just for editing scroll, that annoying horrendous javafx thing
	editScroll(new String[] {"PD"}, new String[] {"scroll ID"}),
	editChoice(new String[] {"PD"}, new String[] {"choice"}),
	insertContent(new String[] {"PB"}, new String[] {"content"}),
	deleteContent(new String[] {"PD"}, new String[] {"the length to delete"}),

	// General commands
	logout(new String[] {}, new String[] {}),
	viewScrolls(new String[] {}, new String[] {}),
	randomScrolls(new String[] {}, new String[] {}),

	// Search by uploaderID, scrollID, name, uploadDate
	searchScroll(new String[]{"LP"}, new String[]{"field to search by"}),
	uploaderID(new String[]{"AP"}, new String[]{"uploaderID (Alphanumeric with no space)"}),
	scrollID(new String[]{"DP"}, new String[]{"scrollID (Numeric with no space)"}),
	scrollName(new String[]{"PA"}, new String[]{"scrollName (Alphanumeric with no space)"}),
	uploadDate(new String[]{"P"}, new String[]{"uploadDate (dd/MM/yyyy)"});

	public final String[] argNum;
	public final String[] argPrompt;
	
	private Command(String[] argNum, String[] argPrompt)
	{
		this.argNum = argNum;
		this.argPrompt= argPrompt;
	}
}