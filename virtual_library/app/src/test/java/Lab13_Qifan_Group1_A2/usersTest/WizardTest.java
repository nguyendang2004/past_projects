package Lab13_Qifan_Group1_A2.usersTest;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.Database;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.users.*;
import org.junit.Test;

import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;
import java.util.Date;

public class WizardTest {
    private Wizard wizard;
    private Scroll scroll;
    private String wizardPwd = "dfdfd";

    @Before
    public void setup() {
        wizard = new Wizard("a", "a", "a", "a",
                "a@a.com", "010101", 0);
        scroll = new Scroll(1, new Date(), "bb", wizard, "010101", 0);
        Database.dropTables();
        Database.createTables();
    }

    @After
    public void cleanUp() {
        Database.dropTables();
    }

    @Test
    public void testConstructor() {
        User wizard = new Wizard("a", "a", "a", "a",
                "a@a.com", "101010", 0);
        assertNotNull(wizard);
    }

    @Test
    public void testVerifyEditScrollIDNull() {
        UserTable.insertUser(this.wizard, this.wizardPwd);
        ScrollTable.insertScroll(this.scroll);
        assertEquals(wizard.verifyEditScrollID(10), 0);
    }

    @Test
    public void testVerifyEditScrollIDYes() {
        UserTable.insertUser(this.wizard, this.wizardPwd);
        ScrollTable.insertScroll(this.scroll);
        assertEquals(wizard.verifyEditScrollID(1), 2);
    }
}
