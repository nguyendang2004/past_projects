package Lab13_Qifan_Group1_A2;

import org.junit.Test;

import Lab13_Qifan_Group1_A2.database.*;
import Lab13_Qifan_Group1_A2.hasher.Hasher;
import Lab13_Qifan_Group1_A2.home.GuestHome;
import Lab13_Qifan_Group1_A2.start.Start;
import Lab13_Qifan_Group1_A2.users.*;
import Lab13_Qifan_Group1_A2.parsing.*;


public class AppStateTest {

    // Test everything. They're empty so no need to do anything
    @Test
    public void coverageTest(){
        Parser parser = new Parser();
        AppState state = new Start(parser);
        state.addScroll(null);
        state.viewScrolls();
        state.displayAllUsers();
        state.displayDownloads();
        state.displayUploads();
        state.removeAccount(null);
        state.editDetails(null);
        state.addAccount();
        state.searchScroll();

        state = new GuestHome(parser);
        state.register();
        state.guestLogin();
        state.registeredLogin();
        state.exit();
    }
}