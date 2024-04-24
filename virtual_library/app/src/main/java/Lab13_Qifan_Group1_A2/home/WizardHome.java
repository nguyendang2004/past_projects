package Lab13_Qifan_Group1_A2.home;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import Lab13_Qifan_Group1_A2.Scroll;
import Lab13_Qifan_Group1_A2.database.ScrollTable;
import Lab13_Qifan_Group1_A2.database.UserTable;
import Lab13_Qifan_Group1_A2.parsing.*;
import Lab13_Qifan_Group1_A2.users.RegisteredUser;
import Lab13_Qifan_Group1_A2.users.User;
import Lab13_Qifan_Group1_A2.hasher.*;

public class WizardHome extends AdminHome
{
    public WizardHome(Parser parser)
	{
		super(parser);
	}
}
