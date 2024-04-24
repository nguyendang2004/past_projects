package Lab13_Qifan_Group1_A2.usersTest;

import Lab13_Qifan_Group1_A2.users.Guest;
import org.junit.Test;

import Lab13_Qifan_Group1_A2.users.User;
import static org.junit.Assert.*;

public class GuestTest {
    @Test
    public void testConstructor() {
        User guest = new Guest("a", "a", "a",
                "a", "a@a.com", "101010", 0);
        assertNotNull(guest);
    }
}
