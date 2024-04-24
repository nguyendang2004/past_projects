package Lab13_Qifan_Group1_A2;

import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.home.RegisteredHome;
import Lab13_Qifan_Group1_A2.parsing.Parser;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import org.junit.Before;
import org.junit.Test;

import Lab13_Qifan_Group1_A2.users.User;

import java.io.ByteArrayInputStream;
import java.sql.Date;

import static org.junit.Assert.*;
public class ScrollTest {
    private static final String NEWLINE = System.lineSeparator();
    private ByteArrayInputStream testIn;
    private Scroll scroll;
    private long millis;
    private User user;
    java.sql.Date date;
    private final RegisteredUser user1 = new RegisteredUser(
            "dfdfd", "bob123", "bob", "bees",
            "bob@bees.com", "0134", 1);
    private final String pwd1 = "bob";

    private final RegisteredUser user2 = new RegisteredUser(
            "ghd", "bob123", "bob", "bees",
            "bob@bees.com", "0134", 1);
    private final String pwd2 = "bioioiob";

    private final Scroll scroll1 = new Scroll(
            1, new java.util.Date(), "first", user1,
            "./uploads/test/test.bin", 1);

    @Before
    public void prepare (){
        millis=System.currentTimeMillis();
        date=new java.sql.Date(millis);
        user = new RegisteredUser("henry", "username",
                "hen", "ry", "henry@henry.com",
                "0134", 2);
        scroll = new Scroll(1, date, "scrolly things", user, "1010", 5);
    }

    //test Date methods
    @Test
    public void getSetDateTest(){
        assertTrue(scroll.getDate().equals(new Date(millis)) );
        millis= millis * 2;
        scroll.setDate(new Date(millis));
        assertTrue(scroll.getDate().equals(new Date(millis)) );
    }

    //test Author methods
    @Test
    public void getSetAuthorTest(){
        assertTrue(scroll.getAuthor().equals(user));
        user = new RegisteredUser("doraemon", "username",
                "do", "ray", "mon@mon.com",
                "0134", 5);
        scroll.setAuthor(user);
        assertTrue(scroll.getAuthor().equals(user));
    }

    //test name methods
    @Test
    public void getSetNameTest(){
        assertTrue(scroll.getName().equals("scrolly things"));
        scroll.setName("not scrolly things");
        assertTrue(scroll.getName().equals("not scrolly things"));
    }

    //test content methods
    @Test
    public void getSetContentTest(){
        assertTrue(scroll.getPath().equals("1010"));
        scroll.setPath("101010");
        assertTrue(scroll.getPath().equals("101010"));
    }


    @Test
    public void getSetNumDownloads(){
        assertTrue(scroll.getNumDownloads()==5);
        scroll.setNumDownloads(15);
        assertTrue(scroll.getNumDownloads()==15);
        assertFalse(scroll.getNumDownloads()==5);
    }
    @Test
    public void isSameScrollTestSuccess(){
        Scroll newScroll = new Scroll(1, date, "scrolly things", user, "1010", 5);

        assertTrue(scroll.isSameScroll(newScroll));
    }
    @Test
    public void isSameScrollTestFail(){
        Scroll newScroll = new Scroll(2, date, "scrolly things", user, "1010", 5);
        assertFalse(scroll.isSameScroll(newScroll));
    }
    @Test
    public void getSetScrollIDTest(){
        assertTrue(scroll.getScrollID()==1);
        scroll.setScrollID(7);
        assertTrue(scroll.getScrollID()==7);
        assertFalse(scroll.getNumDownloads()==1);
    }
    /* @Test
    public void checkContentTestSuccess(){
        assertTrue(scroll.checkContent());
    }
    @Test
    public void checkContentTestFail(){
        scroll.setContent("09090");
        assertFalse(scroll.checkContent());
        scroll.setContent("01010");
    } */
   /* @Test
    public void getContentTest(){
        Database.dropTables();
        Database.createTables();
        User user = new RegisteredUser("x34","wil","chicky", "chic","cc@","090090909", 2000);
        String line = "101001" + NEWLINE + "test" + NEWLINE + "test" + NEWLINE + "Y" + NEWLINE;
        testIn = new ByteArrayInputStream(line.getBytes());
        System.setIn(testIn);

        scroll= new Scroll(1,new Date(677687686), "test",user, "./upload/test/test.bin", 9);
        Parser parser = new Parser();
        AppState home = new RegisteredHome(parser);
        home.addScroll(user);
        String h  =scroll.getContent();
        assertEquals(h, "101001");
    }*/
}

