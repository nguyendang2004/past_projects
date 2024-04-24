package Lab13_Qifan_Group1_A2.usersTest;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import org.junit.Test;

import Lab13_Qifan_Group1_A2.users.User;

import org.junit.Before;
import org.junit.After;

import java.util.Date;

import static org.junit.Assert.*;

public class RegisteredUserTest {
    private final RegisteredUser user1 = new RegisteredUser(
            "dfdfd", "bob123", "bob", "bees",
            "bob@bees.com", "0134", 1);
    private final String pwd1 = "bob";

    private final RegisteredUser user2 = new RegisteredUser(
            "ghd", "bob123", "bob", "bees",
            "bob@bees.com", "0134", 1);
    private final String pwd2 = "bioioiob";

    private final Scroll scroll1 = new Scroll(
            1, new Date(), "first", user1,
            "010101", 1);

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
    public void testConstructor() {
        User registered = new RegisteredUser(
                "a", "a", "a",
                "a", "a@a.com", "101001", 0);
        assertNotNull(registered);
    }

    @Test
    public void testVerifyRemoveScrollIDNull() {
        UserTable.insertUser(this.user1, pwd1);
        ScrollTable.insertScroll(this.scroll1);
        assertEquals(user1.verifyRemoveScrollID(3), 0);
    }

    @Test
    public void testVerifyRemoveScrollIDNo(){
        UserTable.insertUser(this.user1, pwd1);
        UserTable.insertUser(this.user2, pwd2);
        ScrollTable.insertScroll(this.scroll1);
        assertEquals(user2.verifyRemoveScrollID(1), 1);
    }

    @Test
    public void testVerifyRemoveScrollIDYes(){
        UserTable.insertUser(this.user1, pwd1);
        ScrollTable.insertScroll(this.scroll1);
        assertEquals(user1.verifyRemoveScrollID(1), 2);
    }
}
