package Lab13_Qifan_Group1_A2.parsingTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Lab13_Qifan_Group1_A2.parsing.Command;
import Lab13_Qifan_Group1_A2.parsing.Parser;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParserTest {
    private static final String NEWLINE = System.lineSeparator();
    private ByteArrayInputStream testIn;
    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private Parser parser;

    @Before
    public void prepare() {
        System.setOut(new PrintStream(testOut));
    }

    @Test
    public void AtestCheckCommandSuccess() {
        String line = "register" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        assertEquals(parser.checkCommand(), Command.register);
    }

    @Test
    public void testCheckCommandFail() {
        String line = "reg" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        assertTrue(parser.checkCommand() == null);

    }

    @Test
    public void testGetParam() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("peppapig");
        input.add("LoveBacon");
        String line = "peppapig" + NEWLINE + "LoveBacon" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.login);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertTrue(output.get(i).equals(input.get(i)) == true);
        }
    }

    @Test
    public void testGetParamTypeL() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("hoi");
        String line = "13" + NEWLINE + "hoi4" + NEWLINE+"hoi" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.editOption);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamBlank() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("13");
        String line = "peppa pig" + NEWLINE+ ""+ NEWLINE + "13" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.removeScroll);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamTypeP() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("013");
        String line = "898 0909" + NEWLINE + "013" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.phonenumber);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }

    @Test
    public void testGetParamTypeB() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("1110");
        String line = "hoi"+NEWLINE+"1293"+NEWLINE+"1110"+ NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.insertContent);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    //F
    @Test
    public void testGetParamTypeD() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("02341");
        String line = "hoi4"+ NEWLINE + "02341" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.phonenumber);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamTypeA() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("hoi4");
        String line = "%^&@#!$/defd*/rgr/8]]["+ NEWLINE + "hoi4" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.downloadScroll);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamTypeFPass() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("1010");
        input.add("./chicken");
        input.add("gayrussia");
        String line = "1010"+NEWLINE+"./chicken"+ NEWLINE + "gayrussia";
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.addScroll);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamTypeFFailedDot() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("1010");
        input.add("./chicken");
        input.add("gayrussia");
        String line = "1010"+NEWLINE+"./c.hicken"+ NEWLINE+"./chicken"+ NEWLINE + "gayrussia";
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.addScroll);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamTypeFDot() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("1010");
        input.add(".");
        input.add("gayrussia");
        String line = "1010"+NEWLINE+"./c.hicken"+ NEWLINE+"."+ NEWLINE + "gayrussia";
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.addScroll);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }
    @Test
    public void testGetParamTypeFFailedProhibit() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("1010");
        input.add(".");
        input.add("gayrussia");
        String line = "1010"+NEWLINE+"./c?hicken"+ NEWLINE+"."+ NEWLINE + "gayrussia";
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);
        parser = new Parser();
        ArrayList<String> output = parser.getParameter(Command.addScroll);
        for (int i = 0; i < output.size(); i = i + 1) {
            assertEquals(output.get(i), input.get(i));
        }
    }


    @Test
    public void testPrintConfirm(){
        ArrayList<String> input = new ArrayList<String>();
        input.add("peppa pig");
        input.add("LoveBacon");

        parser = new Parser();
        parser.printConfirm(Command.login, input);
        String expected = String.format(
                "Please check your inputs again" + NEWLINE +
                        "username: peppa pig" + NEWLINE +
                        "password: LoveBacon" + NEWLINE
        );
        assertEquals(expected, testOut.toString());
    }

    @Test
    public void testConfirmNo(){
        ArrayList<String> input = new ArrayList<String>();
        String line = "no" + NEWLINE + "No" + NEWLINE + "N" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);

        parser = new Parser();
        boolean confirmOut = parser.confirm();
        assertFalse(confirmOut);
        String expected = String.format(
                "Please confirm. (Y/N)" + NEWLINE +
                "Invalid input" + NEWLINE +
                "Invalid input" + NEWLINE
        );
        assertEquals(expected,testOut.toString());
    }
    @Test
    public void testTypeFail(){
        parser = new Parser();
        boolean out = parser.checkTypeParam("j j", 'P');
        assertEquals(out, false);
    }

    @Test
    public void testTypeSuccess(){
        parser = new Parser();
        boolean out = parser.checkTypeParam("13", 'I');
        assertEquals(out, true);
    }

    @Test
    public void testConfirmYes() {
        ArrayList<String> input = new ArrayList<String>();
        String line = "yes" + NEWLINE + "Yes" + NEWLINE + "Y" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);

        parser = new Parser();
        boolean confirmOut = parser.confirm();
        assertTrue(confirmOut);
        String expected = String.format(
                "Please confirm. (Y/N)" + NEWLINE +
                "Invalid input" + NEWLINE +
                "Invalid input" + NEWLINE
        );
        assertEquals(expected,testOut.toString());
        parser.closeScanner();
    }

}