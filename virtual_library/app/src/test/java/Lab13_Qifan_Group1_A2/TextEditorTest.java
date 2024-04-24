package Lab13_Qifan_Group1_A2;

import Lab13_Qifan_Group1_A2.parsing.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TextEditorTest {
    private static final String NEWLINE = System.lineSeparator();
    private ByteArrayInputStream testIn;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    @Before
    public void prepare() {
        System.setOut(new PrintStream(testOut));
    }

    @Test
    public void displayTextTest(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        String expected = old + NEWLINE+ "      " + "^"+NEWLINE;
        editor.displayText();
        assertEquals(expected, testOut.toString());
    }

    @Test
    public void insertTextTest(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        int position = editor.getPosition();
        editor.insertText("101");
        assertEquals(position+3, editor.getPosition());
        assertEquals("101010101", editor.getString());
    }

    @Test
    public void deleteTextTest(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        int position = editor.getPosition();
        editor.deleteText(1);
        assertEquals(position, editor.getPosition());
        assertEquals("10110", editor.getString());
    }
    @Test
    public void deleteTextTestTooMuch(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        int position = editor.getPosition();
        editor.deleteText(100);
        assertEquals(position, editor.getPosition());
        assertEquals("101010", editor.getString());
    }
    @Test
    public void deleteTextTestTooMuch2(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        int position = editor.getPosition();
        editor.deleteText(100);
        assertEquals(position, editor.getPosition());
        assertEquals("101010", editor.getString());
    }

    @Test
    public void moveRightTest(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorRight();
        assertEquals(6-3+1, editor.getPosition());
    }
    @Test
    public void moveRightTestTooMuch(){
        Parser parser = new Parser();
        String old = "10";
        TextEditor editor = new TextEditor(parser, old);
        editor.moveCursorRight();
        editor.moveCursorRight();
        editor.moveCursorRight();
        editor.moveCursorRight();
        editor.moveCursorRight();
        editor.moveCursorRight();
        editor.moveCursorRight();
        assertEquals(2, editor.getPosition());
    }
    @Test
    public void moveLeftTestTooMuch(){
        Parser parser = new Parser();
        String old = "10";
        TextEditor editor = new TextEditor(parser, old);
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        editor.moveCursorLeft();
        assertEquals(0, editor.getPosition());
    }


    @Test
    public void repeatTestSuccess(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        assertEquals(true, editor.repeated("4444",'4'));
    }

    @Test
    public void repeatTestFail(){
        Parser parser = new Parser();
        String old = "101010";
        TextEditor editor = new TextEditor(parser, old);
        assertEquals(false, editor.repeated("4454",'4'));
    }

    @Test
    public void editTest(){
       //1010 -> 110 -> 11010
        String old = "10";
        String line = "0" + NEWLINE + "1" + NEWLINE+ "10" +NEWLINE +
                "333" + NEWLINE + "2" +NEWLINE + "20" +NEWLINE
                + "2" +NEWLINE + "1" +NEWLINE+ "443" +NEWLINE+ "44"+NEWLINE + "334"+NEWLINE+"3"+NEWLINE +"1"+
                NEWLINE + "01" + NEWLINE + "5" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        Parser parser = new Parser();
        TextEditor editor = new TextEditor(parser, old);
        String result = editor.edit();
        assertEquals(result, "11010");
    }
}














