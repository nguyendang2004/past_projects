package Lab13_Qifan_Group1_A2.databaseTest;

import org.junit.Test;

import Lab13_Qifan_Group1_A2.database.UserType;

import static org.junit.Assert.*;

public class UserTypeTest {
    @Test
    public void testEnums() {
        assertEquals(UserType.ADMIN.name(), "ADMIN");
        assertEquals(UserType.GUEST.name(), "GUEST");
        assertEquals(UserType.REGISTERED_USER.name(), "REGISTERED_USER");
    }
}
