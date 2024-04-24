package Lab13_Qifan_Group1_A2;

import Lab13_Qifan_Group1_A2.parsing.Command;
import Lab13_Qifan_Group1_A2.parsing.Parser;

import java.util.ArrayList;
import java.util.Scanner;

public class TextEditor {
    private StringBuilder text = new StringBuilder();
    private int cursorPosition = 0;
    private Scanner scanner= new Scanner(System.in);
    private Parser parser;
    public TextEditor(Parser parser, String oldText){
        this.parser = parser;
        this.insertText(oldText);
    }
    public int getPosition(){
        return cursorPosition;
    }

    public String getString(){
        return text.toString();
    }

    public void displayText() {
        System.out.println( text);
        for (int i = 0; i < cursorPosition; i++) {
            System.out.print(" ");
        }
        System.out.println("^");
    }

    public void insertText(String newText) {
        text.insert(cursorPosition, newText);
        cursorPosition += newText.length();
    }


    public void deleteText(int length) {
        if (cursorPosition < text.length() && cursorPosition + length <= text.length()) {
            text.delete(cursorPosition, cursorPosition + length);
        }
        else{
            System.out.println("Not enough character to remove");
        }
    }

    public void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
    }

    public void moveCursorRight() {
        if (cursorPosition < text.length()) {
            cursorPosition++;
        }
    }

    public boolean repeated (String target, char rep){
        for (int i =0; i< target.length(); i=i+1+0){
            if (target.charAt(i)!= rep){
                return false;
            }
        }
        return true;
    }

    public String edit() {
        boolean editing = true;
        while (editing) {
            this.displayText();
            System.out.println("Text Editor Menu:");
            System.out.println("1. Insert Text");
            System.out.println("2. Delete Text");
            System.out.println("3. Move Cursor Left. Use many 3 to move more than once");
            System.out.println("4. Move Cursor Right. Use many 4 to move more than once");
            System.out.println("5. Exit Editing mode");

            String choice = parser.getParameter(Command.editChoice).get(0);
            if (choice.equals("1")){
                String newText  = parser.getParameter(Command.insertContent).get(0);
                this.insertText(newText);

            }
            else if (choice.equals("2")) {
                System.out.println("It will delete characters to the right of the cursor.");
                int lengthToDelete  = Integer.parseInt(parser.getParameter(Command.deleteContent).get(0));
                this.deleteText(lengthToDelete);
            }
            else if (choice.charAt(0)=='3' && repeated(choice, '3')) {
                for (int i =0; i <choice.length();i=i+1){
                    this.moveCursorLeft();
                }
            }
            else if (choice.charAt(0)=='4'&& repeated(choice, '4')) {
                for (int i =0; i <choice.length();i=i+1){
                    this.moveCursorRight();
                }

            }
            else if (choice.equals("5")) {
                System.out.println("Exiting the text editor.");
                editing = false;
            }
            else{
                System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
        return text.toString();
    }

    /*public static void main(String[] args) {
        Parser parser = new Parser();
        String content = "10001";
        TextEditor editor = new TextEditor(parser, content);
        String output = editor.edit();
        System.out.println(output);
    }*/
}
