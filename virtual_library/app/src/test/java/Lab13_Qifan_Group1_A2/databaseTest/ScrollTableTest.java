package Lab13_Qifan_Group1_A2.databaseTest;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScrollTableTest {
    // Test user 1
    private final User user1 = new RegisteredUser(
            "dfdfd", "bob123", "bob", "bees",
            "bob@bees.com", "0134", 1);
    private final String pwd1 = "bob";

    // Test user 2
    private final User user2 = new RegisteredUser(
            "bobs", "new username", "bob", "bees",
            "bob@bees.com", "0134", 1);
    private final String pwd2 = "joe";

    // Test scroll 1
    private final Scroll scroll1 = new Scroll(
            1, new Date(), "first", user1,
            "010101", 1);

    // Test scroll 2
    private final Scroll scroll2 = new Scroll(
            2, new Date(), "second", user2,
            "011101", 3);

    @Before
    public void prepare() {
        Database.dropTables();
        Database.createTables();
    }

    @After
    public void cleanUp() {
        Database.dropTables();
    }

    @Test
    public void testInsertScroll() {
        UserTable.insertUser(this.user1, pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);

        Scroll actual = ScrollTable.getScroll(expected.getScrollID());
        assertNotNull(actual);
        assertTrue(expected.isSameScroll(actual));
    }

    @Test
    public void testGetScrollUsingName() {
        UserTable.insertUser(this.user1, pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);

        Scroll actual = ScrollTable.getScroll(expected.getName());
        assertNotNull(actual);
        assertTrue(expected.isSameScroll(actual));

        assertNull(ScrollTable.getScroll("I don't exist!!!"));
    }

    @Test
    public void testGetScrollUsingScrollID() {
        UserTable.insertUser(this.user1, pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);

        Scroll actual = ScrollTable.getScroll(expected.getScrollID());
        assertNotNull(actual);
        assertTrue(expected.isSameScroll(actual));

        assertNull(ScrollTable.getScroll(334343));
    }

    @Test
    public void testGetAllScrolls() {
        User user1 = this.user1;
        UserTable.insertUser(user1, pwd1);

        User user2 = this.user2;
        UserTable.insertUser(user2, pwd2);

        Scroll scroll1 = this.scroll1;
        ScrollTable.insertScroll(scroll1);

        Scroll scroll2 = this.scroll2;
        ScrollTable.insertScroll(scroll2);

        boolean scroll1Visited = false;
        boolean scroll2Visited = false;
        List<Scroll> scrolls = ScrollTable.getAllScrolls();
        assertNotNull(scrolls);

        for (Scroll scroll : scrolls) {
            if (!scroll1Visited && scroll.getScrollID() == scroll1.getScrollID()) {
                scroll1Visited = true;
                assertTrue(scroll.isSameScroll(scroll1));
            } else if (!scroll2Visited && scroll.getScrollID() == scroll2.getScrollID()) {
                scroll2Visited = true;
                assertTrue(scroll.isSameScroll(scroll2));
            }
        }
        assertTrue(scroll1Visited);
        assertTrue(scroll2Visited);
    }

    @Test
    public void testGetUserScrolls() {
        User user = this.user1;
        UserTable.insertUser(user, pwd1);

        Scroll scroll1 = this.scroll1;
        ScrollTable.insertScroll(scroll1);

        // Since we want user1 to have 2 scrolls
        Scroll scroll2 = new Scroll(
                2, new Date(), "second", user1,
                "011101", 3);
        ScrollTable.insertScroll(scroll2);

        boolean scroll1Visited = false;
        boolean scroll2Visited = false;
        List<Scroll> scrolls = ScrollTable.getUserScrolls(user.getUsername());
        assertNotNull(scrolls);

        for (Scroll scroll : scrolls) {
            if (!scroll1Visited && scroll.getScrollID() == scroll1.getScrollID()) {
                scroll1Visited = true;
                assertTrue(scroll.isSameScroll(scroll1));
            } else if (!scroll2Visited && scroll.getScrollID() == scroll2.getScrollID()) {
                scroll2Visited = true;
                assertTrue(scroll.isSameScroll(scroll2));
            }
        }
        assertTrue(scroll1Visited);
        assertTrue(scroll2Visited);
    }

    @Test
    public void testScrollExistsUsingName() {
        assertFalse(ScrollTable.scrollExists("not yet"));

        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);

        assertTrue(ScrollTable.scrollExists(expected.getName()));
    }

    @Test
    public void testScrollExistsUsingScrollID() {
        assertFalse(ScrollTable.scrollExists(1));

        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);

        assertTrue(ScrollTable.scrollExists(expected.getScrollID()));
    }

    @Test
    public void testScrollCount() {
        UserTable.insertUser(this.user1, pwd1);

        Scroll scroll1 = this.scroll1;
        ScrollTable.insertScroll(scroll1);

        Scroll scroll2 = this.scroll2;
        ScrollTable.insertScroll(scroll2);

        assertEquals(2, ScrollTable.scrollCount());
    }

    @Test
    public void testGetRandomScroll() {
        assertNull(ScrollTable.getRandomScroll());

        UserTable.insertUser(this.user1, pwd1);
        Scroll scroll1 = this.scroll1;
        ScrollTable.insertScroll(scroll1);
        Scroll scroll2 = this.scroll2;
        ScrollTable.insertScroll(scroll2);

        Scroll random = ScrollTable.getRandomScroll();
        assertNotNull(random);

        boolean expectedScrolls =
                random.isSameScroll(scroll1) ||
                random.isSameScroll(scroll2);
        assertTrue(expectedScrolls);
    }

    @Test
    public void testRemoveScrollByName() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);
        ScrollTable.removeScroll(expected.getName());

        assertNull(ScrollTable.getRandomScroll());
        assertEquals(0, ScrollTable.scrollCount());
        assertNull(ScrollTable.getScroll(expected.getName()));
        assertNull(ScrollTable.getScroll(expected.getScrollID()));
    }

    @Test
    public void testRemoveScrollByScrollID() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);
        ScrollTable.removeScroll(expected.getScrollID());

        assertNull(ScrollTable.getRandomScroll());
        assertEquals(0, ScrollTable.scrollCount());
        assertNull(ScrollTable.getScroll(expected.getName()));
        assertNull(ScrollTable.getScroll(expected.getScrollID()));
    }

    @Test
    public void testUpdateScroll() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);

        expected.setPath("00000000000000000000000000000");
        expected.setName("UPDATED SCROLL!");
        ScrollTable.updateScroll(expected);

        Scroll actual = ScrollTable.getScroll(expected.getScrollID());
        assertNotNull(actual);
        assertEquals(expected.getPath(), actual.getPath());
        assertEquals(expected.getName(), actual.getName());
        assertTrue(actual.isSameScroll(expected));

    }

    @Test
    public void testSearchScrollByUploaderID() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);
        List<Scroll> scroll = ScrollTable.searchScrollByUploaderID(this.user1.getUserID());
        assertNotNull(scroll);
        assertTrue(scroll.get(0).isSameScroll(expected));
    }

    @Test
    public void testSearchScrollByScrollID() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);
        List<Scroll> scroll = ScrollTable.searchScrollByScrollID(
                String.valueOf(this.scroll1.getScrollID()));
        assertNotNull(scroll);
        assertTrue(scroll.get(0).isSameScroll(expected));
    }

    @Test
    public void testSearchScrollByName() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);
        List<Scroll> scroll = ScrollTable.searchScrollByName(this.scroll1.getName());
        assertNotNull(scroll);
        assertTrue(scroll.get(0).isSameScroll(expected));
    }

    public String formatDate(java.util.Date date) {
        final String SEARCH_DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(SEARCH_DATE_FORMAT);
        return formatter.format(date);
    }

    @Test
    public void testSearchScrollByUploadDate() {
        UserTable.insertUser(this.user1, this.pwd1);

        Scroll expected = this.scroll1;
        ScrollTable.insertScroll(expected);
        List<Scroll> scroll = ScrollTable.searchScrollByUploadDate(
                formatDate(this.scroll1.getDate()));
        assertNotNull(scroll);
        assertTrue(scroll.get(0).isSameScroll(expected));
    }
}
