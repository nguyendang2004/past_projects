package Lab13_Qifan_Group1_A2.databaseTest;

import Lab13_Qifan_Group1_A2.database.Database;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import java.sql.SQLException;

public class DatabaseTest {
    @Before
    public void prepare() {
        Database.dropTables();
    }

    @Test
    public void testConnectionToDatabase()
            throws SQLException, ClassNotFoundException {
        assertNotNull(Database.connectToDatabase());
    }

    @Test
    public void testCreateTables() {
        assertTrue(Database.createTables());
    }

    @Test
    public void testDropTables() {
        assertTrue(Database.dropTables());
    }

    @After
    public void cleanUp() {
        Database.dropTables();
    }
}
