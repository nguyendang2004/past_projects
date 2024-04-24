package Lab13_Qifan_Group1_A2.parsing;

import java.io.ByteArrayInputStream;
import java.util.*;
public class Parser {
	private Scanner scanner;
	private static final String NEWLINE = System.lineSeparator();
	private static final String PROHIBITED_FILE_CHARS = "?%*:|\"<>,;= ";

	public Parser()
	{
		scanner = new Scanner(System.in);
	}

	// check if command is valid, return null if not
	public Command checkCommand()
	{
		String userInput = scanner.nextLine().trim();
		for (Command command : Command.values())
		{
			if (command.name().equalsIgnoreCase(userInput))
			{
				return command;
			}
		}
		return null;
	}

	// P = no space, L = Letters, D = Digits, A = Alphanumerical, B = Binary
	// Checks a string against a given type
	// Return true if passes, return false if fails
	public boolean checkTypeParam(String string, char type)
	{
		if (type == 'P')					// P = no space
		{
			if (string.contains(" "))
			{
				return false;
			}
		}
		else if (type == 'L')				// L = Letters only
		{
			for (char c : string.toCharArray())
			{
				if (!Character.isLetter(c))
				{
					return false;
				}
			}
		}
		else if (type == 'D')				// D = Digits only
		{
			for (char c : string.toCharArray())
			{
				if (!Character.isDigit(c))
				{
					return false;
				}
			}
		}
		else if (type == 'A')				// A = Alphanumerical (letters and digits)
		{
			for (char c : string.toCharArray())
			{
				if (!Character.isLetter(c) && !Character.isDigit(c))
				{
					return false;
				}
			}
		}
		else if (type == 'B')
		{
			for (char c : string.toCharArray())
			{
				if (c != '0' && c != '1')
				{
					return false;
				}
			}
		}
		else if (type == 'F')
		{
			int len = string.length();
			for (int i = 0; i < len; i++) {
				char c = string.charAt(i);
				if (c == '.' && (len==1 || ((i==0 || string.charAt(i-1)=='\\' 
												 || string.charAt(i-1)=='/') 
											&& (i==len-1 || string.charAt(i+1)=='\\' 
								 				 		 || string.charAt(i+1)=='/')))) {
					continue;
				}
				else if (c == '.') {
					return false;
				}
				for (char prohibitedChar : PROHIBITED_FILE_CHARS.toCharArray()) {
					if (c == prohibitedChar) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public ArrayList<String> getParameter(Command input)
	{
		ArrayList<String> output = new ArrayList<String>();
		int i = 0;
		while (i < input.argNum.length)
		{
			System.out.print("Enter your " + input.argPrompt[i] + ":" + NEWLINE);
			String answer = scanner.nextLine().trim();
			if (!answer.isEmpty() && isValidInput(answer, input.argNum[i]))
			{
				output.add(answer);
				i++;
			}
			else
			{
				System.out.println("Invalid input. Please try again.");
			}
		}
		return output;
	}

	private boolean isValidInput(String answer, String argType)
	{
		for (char type : argType.toCharArray())
		{
			if (!checkTypeParam(answer, type))
			{
				return false;
			}
		}
		return true;
	}


	public void printConfirm(Command command, ArrayList<String> input) {
		System.out.println("Please check your inputs again");
		for (int i = 0; i < command.argNum.length; i++) {
			System.out.printf("%s: %s" + System.lineSeparator(), command.argPrompt[i], input.get(i));
		}
	}

	public boolean confirm() {
		System.out.println("Please confirm. (Y/N)");
		boolean ask = true;
		String confirmation = "Start";
		while (ask && scanner.hasNextLine()) {
			confirmation = scanner.nextLine().trim();
			if (confirmation.equals("Y")) {
				ask = false;
			} else if (confirmation.equals("N")) {

				ask = false;
			} else {
				System.out.println("Invalid input");
			}
		}
		return confirmation.equals("Y");
	}

	public void closeScanner() {
			scanner.close();

	}

	/* public static void main(String[] args) {
		Parser h = new Parser();
		h.getParameter(Command.addScroll);
	} */

	// Counts number of quotation marks " in the string
	/*public static int countQuote (String string) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '"') {
				count++;
			}
		}
		return count;
	}*/
}