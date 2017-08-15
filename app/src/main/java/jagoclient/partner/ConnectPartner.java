//*CID://+1A1cR~:                             update#=    3;       //~v101I~//~1A1cR~
//*******************************************************************************//~v101I~
//1A1c 2013/03/17 display portNo in msg "No connection"            //~1A1cI~
//101a 2013/01/30 IP connection                                    //~v101I~
//*******************************************************************************//~v101I~
package jagoclient.partner;

import com.Asgts.AG;
import com.Asgts.R;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;
import jagoclient.partner.partner.Partner;

/**
A thread, which will try to connect to a go partner.
If it is successfull, a Partner Frame will open.
Otherwise, an error message will appear.
*/

public class ConnectPartner extends Thread
{	Partner P;
	PartnerFrame PF;
	public ConnectPartner (Partner p, PartnerFrame pf)
	{	P=p; PF=pf;
		start();
	}
	public void run ()
	{	P.Trying=true;
//        if (Global.getParameter("userelay",false))               //~2C26R~
//        {   if (!PF.connectvia(P.Server,P.Port,                  //~2C26R~
//                Global.getParameter("relayserver","localhost"),  //~2C26R~
//                Global.getParameter("relayport",6971)))          //~2C26R~
//            {   PF.setVisible(false); PF.dispose();              //~2C26R~
//                new Message(Global.frame(),                      //~2C26R~
//                    Global.resourceString("No_connection_to_")+P.Server);//~2C26R~
//                try                                              //~2C26R~
//                {   sleep(10000);                                //~2C26R~
//                }                                                //~2C26R~
//                catch (Exception e)                              //~2C26R~
//                {   P.Trying=false;                              //~2C26R~
//                }                                                //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
      try                                     //~2C26I~            //+1A1cR~
      {                                                            //+1A1cI~
		/*else*/ if (!PF.connect(P.Server,P.Port))
//  	{	PF.setVisible(false); PF.dispose();                    //~v101R~
		{                                                          //~v101R~
            PartnerFrame.dismissWaitingDialog();                             //~v101R~
			new Message(Global.frame(),
//  			Global.resourceString("No_connection_to_")+P.Server);//~1A1cR~
    			AG.resource.getString(R.string.Err_No_connection_to_,P.Server,P.Port));//~1A1cI~
			try
			{	sleep(10000);
			}
			catch (Exception e)
			{	P.Trying=false;
			}
		}
		P.Trying=false;
      }                                                            //+1A1cI~
      catch (Exception e)                                          //+1A1cI~
      {                                                            //+1A1cI~
        Dump.println(e,"ConnectPartner");                          //+1A1cI~
      }                                                            //+1A1cI~
                                                                   //+1A1cI~
	}
}

