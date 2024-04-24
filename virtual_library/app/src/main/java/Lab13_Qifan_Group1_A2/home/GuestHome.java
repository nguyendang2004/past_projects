package Lab13_Qifan_Group1_A2.home;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import Lab13_Qifan_Group1_A2.AppState;
import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.DatabaseUtils;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.parsing.Command;
import Lab13_Qifan_Group1_A2.parsing.Parser;
import Lab13_Qifan_Group1_A2.users.User;

/* GuestHome
 * Overrides all methods that would be shared by all user home interfaces as
 * every user will have access to commands available to a guest
 * So far, this is just logout
 */
public class GuestHome extends AppState {

	public GuestHome(Parser parser) {
		super(parser);
	}

	@Override
	public void displayScreen() {
		System.out.print(String.join(NEWLINE,
		"-- Home --",
		"> View scrolls\t\t(viewScrolls)",
		"> Search scrolls\t(searchScroll)",
		"> Preview scrolls\t(preview)",
		"> Logout\t\t(logout)",
		"====================================================================", NEWLINE));
	}
	@Override
	public void preview(){
   // Scroll to copy
		List<String> input;
		// Preview
		input = parser.getParameter(Command.preview);
		if (!ScrollTable.scrollExists(Integer.parseInt(input.get(0)))) {
			System.out.println("This scroll does not exist!");
			return;
		}
		Scroll scroll = ScrollTable.getScroll(Integer.parseInt(input.get(0)));
		Path source = Paths.get(scroll.getPath());
		scrollPreview(scroll, source);
	}
	public void scrollPreview(Scroll scroll, Path path) {
		User author = scroll.getAuthor();
		String authorName = author.getFirstName() + " " + author.getLastName();
		try {
			String contents = Files.readAllLines(path).get(0);
			String preview = NEWLINE + String.format("~PREVIEW~") + NEWLINE +
					String.format("Name: %s", scroll.getName()) + NEWLINE +
					String.format("Author: %s", authorName) + NEWLINE +
					String.format("Content:") + NEWLINE +
					String.format("%s", contents) + NEWLINE;
			System.out.println(preview);
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("The scroll is empty");
		}
		catch (IOException e) {
			System.out.println("There's no way you actually failed the preview... (loser)"+e.getMessage());
		}
	}

	@Override
	public void viewScrolls()
	{
		List<Scroll> scrolls = ScrollTable.getAllScrolls();
		System.out.printf("%-5s%-25s%-15s%s%n", "ID", "Name", "Author", "Date");
		for (Scroll scroll : scrolls)
		{
			System.out.printf("%-5d", scroll.getScrollID());
			System.out.printf("%-25s", scroll.getName());
			System.out.printf("%-15s", scroll.getAuthor().getFirstName() + " " + scroll.getAuthor().getLastName());
			System.out.printf("%s" + NEWLINE, String.format("%tb %te %tY", scroll.getDate(), scroll.getDate(), scroll.getDate()));
		}
	}

	@Override
	public void searchScroll() {
		final String SEARCH_DATE_FORMAT = "dd/MM/yyyy";
		List<String> validFields = Arrays.asList("uploaderID", "scrollID", "scrollName", "uploadDate");

		/* Display valid fields for the user */
		String display = String.join(NEWLINE,
				"=".repeat(68),
				"List of fields to search by:",
				"\t> uploaderID",
				"\t> scrollID",
				"\t> scrollName",
				"\t> uploadDate",
				"=".repeat(68));
		System.out.println(display);

		/* Get user search filters */
		List<String> input; // Stores raw input
		String searchFilter = ""; // Stores the filter they picked
		boolean validInput = false;
		while (!validInput) { // Stop looping if they enter valid input
			input = parser.getParameter(Command.searchScroll);
			searchFilter = input.get(0);
			if (validFields.contains(searchFilter)) {
				validInput = true;
			} else {
				System.out.println("Please enter a valid search field!");
			}
		}

		/* Get the value for the chosen search field */
		List<Scroll> returnedScrolls = null;
		switch(searchFilter) {
			case "uploaderID" -> {
				String uploaderID = parser.getParameter(Command.uploaderID).get(0);
				returnedScrolls = ScrollTable.searchScrollByUploaderID(uploaderID);
			}
			case "scrollID" -> {
				String scrollID = parser.getParameter(Command.scrollID).get(0);
				returnedScrolls = ScrollTable.searchScrollByScrollID(scrollID);
			}
			case "scrollName" -> {
				String scrollName = parser.getParameter(Command.scrollName).get(0);
				returnedScrolls = ScrollTable.searchScrollByName(scrollName);
			}
			case "uploadDate" -> {
				String uploadDate = "";

				boolean validDate = false;
				while (!validDate) {
					uploadDate = parser.getParameter(Command.uploadDate).get(0);
					try {
						// Check date is valid syntax
						SimpleDateFormat formatter = new SimpleDateFormat(SEARCH_DATE_FORMAT);
						formatter.setLenient(false);
						formatter.parse(uploadDate);
						validDate = true;
					} catch (ParseException e) {
						System.out.println("Invalid input. Please try again.");
					}
				}
				returnedScrolls = ScrollTable.searchScrollByUploadDate(uploadDate);
			}
		}

		/* Print the results */
		if (returnedScrolls == null || returnedScrolls.isEmpty()) {
			System.out.println(NEWLINE + "No results found.");
			return;
		}

		System.out.printf(NEWLINE + "%-5s%-25s%-15s%s%n", "ID", "Name", "Author", "Date");
		for (Scroll scroll : returnedScrolls) {
			System.out.printf("%-5d", scroll.getScrollID());
			System.out.printf("%-25s", scroll.getName());
			System.out.printf("%-15s", scroll.getAuthor().getFirstName() + " " + scroll.getAuthor().getLastName());
			System.out.printf("%s" + NEWLINE, DatabaseUtils.convertDateToString(scroll.getDate()));
		}
	}
}