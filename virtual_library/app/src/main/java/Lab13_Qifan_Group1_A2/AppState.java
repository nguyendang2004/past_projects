package Lab13_Qifan_Group1_A2;

import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;
import Lab13_Qifan_Group1_A2.parsing.Parser;

/*
 * AppState
 * An abstract class intended to follow the state of the app
 * The appropriate classes that extend AppState will override
 * them with the correct functionality
 */
public abstract class AppState {
	protected static final String NEWLINE = System.lineSeparator();
	public Parser parser;

	public AppState(Parser parser) {
		this.parser = parser;
	}

	public void displayScreen() {
		return;
	}

	public User register() {
		return null;
	}

	public User guestLogin() {
		return null;
	}

	public User registeredLogin() {
		return null;
	}

	public void addScroll(User user) {
		return;
	}

	public void addAccount() {
		return;
	}

	public void removeScroll(User user) {
		return;
	}
	public void editScroll(User user) {
		return;
	}

    public void downloadScroll(String username) {
		return;
	}

	public void exit() {
		return;
	}

	public void viewScrolls() {
		return;
	}
	public void preview() {
		return;
	}

	public void displayDownloads() {
		return;
	}

	public void displayUploads() {
		return;
	}

	public void removeAccount(String selfUsername) {
		return;
	}

	public void editDetails(User user) {
		return;
	}

	public void displayAllUsers() {
		return;
	}

	public void searchScroll() {}
}