//*CID://+v101R~:                             update#=   29;       //+v101R~
//*************************************************************************//~v107I~
//101a 2013/01/30 IP connection                                    //~v101I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//*************************************************************************//~v107I~
package jagoclient.partner;

import jagoclient.Dump;
import jagoclient.Global;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;                                            //~v101R~
import com.Asgts.R;                                                //~v101R~

/**
This is the server thread for partner connections. If anyone connects
to the server, a new PartnerFrame will open to handle the connection.
If the server starts, it will open a new PartnerServerThread, which
checks for datagrams that announce open partners.
*/

public class Server extends Thread
{	int Port;
	boolean Public;
//    static public PartnerServerThread PST=null;                  //~v107R~//~v101R~
//    ServerSocket SS;                                             //~v101R~
    static ServerSocket SS;                                        //~v101I~
    static boolean swCancel;                                               //~v101I~
	/**
	@param p the server port
	@param publ server is public or not
	*/
	public Server (){}  //for extended(Asgts/jagoclient/partner/Server//~v107R~//~v101R~
	public Server (int p, boolean publ)
	{	Port=p; Public=publ;
		start();
	}
	public void run ()
//  {   if (PST==null)                                             //~v107R~
    {                                                              //~v107I~
    	if (Dump.Y) Dump.println("Server:run===== started:"+this.toString());//~v101I~
//        if (PST==null)                                           //~v107I~//~v101R~
//          PST=new PartnerServerThread(Global.getParameter("serverport",6970)+2);//~v107R~//~v101R~
//        try { sleep(1000); } catch (Exception e) {}              //~v107R~//~v101R~
        try                                                      //~v107R~//~v101R~
        {   SS=new ServerSocket(Port);                           //~v107R~//~v101R~
        	infoListening();                                       //~v107I~//~v101M~
//            while (true)                                         //~v107R~//~v101R~
            {                                                      //~v101R~
	        	AG.RemoteStatusAccept=AG.RS_IPLISTENING;           //~v101I~
                Socket S=SS.accept();                              //~v101I~
//                if (Global.Busy) // user set the busy checkbox   //~v107R~//~v101R~
//                {   PrintWriter o=new PrintWriter(               //~v107R~//~v101R~
//                        new DataOutputStream(S.getOutputStream()),true);//~v107R~//~v101R~
//                    o.println("@@busy");                         //~v107R~//~v101R~
//                    S.close();                                   //~v107R~//~v101R~
//                    continue;                                    //~v107R~//~v101R~
//                }                                                //~v107R~//~v101R~
		        AG.RemoteStatusAccept=0;                           //~v101I~
                PartnerFrame cf=                                 //~v107R~//~v101R~
//                  new PartnerFrame(Global.resourceString("Server"),true);//~v107R~//~v101R~
                    new PartnerFrame(AG.resource.getString(R.string.IPServer),true);//~v101I~
//                Global.setwindow(cf,"partner",500,400);          //~v107R~//~v101R~
//              cf.show();                                       //~v107R~//~v101R~
                cf.open(S);                                      //~v107R~//~v101R~
                SS.close();                                         //~v101I~
                SS=null;                                           //~v101I~
            }                                                    //~v107R~//~v101R~
        }                                                        //~v107R~//~v101R~
        catch (Exception e)                                      //~v107R~//~v101R~
        {                                                          //~v101R~
	        AG.RemoteStatusAccept=0;                               //~v101I~
        	if (!swCancel)                                         //~v101I~
			   Dump.println(e,"Server Error");//@@@@ add e         //~v101I~
            else                                                   //~v101I~
			   if (Dump.Y) Dump.println("Server Accept canceled");//@@@@ add e//~v101I~
        }                                                        //~v107R~//~v101R~
    	if (Dump.Y) Dump.println("Server:run===== end");           //~v101I~
	}
	
	/**
	This is called, when the server is opened. It will announce
	the opening to known servers by a datagram.
	*/
//    public void open ()                                          //~v101R~
//	  {	if (Public)                                                //~v107R~//~v101R~
//        {   ListElement pe=Global.PartnerList.first();           //~v107R~
//            while (pe!=null)                                     //~v107R~
//            {   Partner p=(Partner)pe.content();                 //~v107R~
//                if (p.State>0)                                   //~v107R~
//                {   DatagramMessage d=new DatagramMessage();     //~v107R~
//                    d.add("open");                               //~v107R~
//                    d.add(Global.getParameter("yourname","Unknown"));//~v107R~
//                    try                                          //~v107R~
//                    {   String s=InetAddress.getLocalHost().toString();//~v107R~
//                        d.add(s.substring(s.lastIndexOf('/')+1));//~v107R~
//                    }                                            //~v107R~
//                    catch (Exception e) { d.add("Unknown Host"); }//~v107R~
//                    d.add(""+Global.getParameter("serverport",6970));//~v107R~
//                    d.add(""+p.State);                           //~v107R~
//                    d.send(p.Server,p.Port+2);                   //~v107R~
//                }                                                //~v107R~
//                pe=pe.next();                                    //~v107R~
//            }                                                    //~v107R~
//        }                                                        //~v107R~
//          Global.Busy=false;                                     //~v101R~
//    }                                                            //~v101R~
	/**
	This is called, when the server is closed. It will announce
	the closing to known servers by a datagram.
	*/
    public void close ()                                    //~v101R~
//  {	if (!Public) return;                                      //~v107R~//~v101R~
    {                                                              //~v101I~
    	if (SS!=null)                                              //~v101I~
        try                                                        //~v101I~
        {                                                          //~v101I~
        	SS.close();                                            //~v101I~
        }                                                          //~v101I~
        catch (Exception e)                                        //~v101I~
        {                                                          //~v101I~
			Dump.println(e,"Server:SS close");                     //~v101I~
        }                                                          //~v101I~
//    	if (!Public) return;                                       //~v101I~
//        ListElement pe=Global.PartnerList.first();               //~v107R~
//        DatagramMessage d=new DatagramMessage();                 //~v107R~
//        d.add("close");                                          //~v107R~
//        d.add(Global.getParameter("yourname","Unknown"));        //~v107R~
//        try                                                      //~v107R~
//        {   String s=InetAddress.getLocalHost().toString();      //~v107R~
//            d.add(s.substring(s.lastIndexOf('/')+1));            //~v107R~
//        }                                                        //~v107R~
//        catch (Exception e) { d.add("Unknown Host"); }           //~v107R~
//        while (pe!=null)                                         //~v107R~
//        {   Partner p=(Partner)pe.content();                     //~v107R~
//            if (p.State>0) d.send(p.Server,p.Port+2);            //~v107R~
//            pe=pe.next();                                        //~v107R~
//        }                                                        //~v107R~
//          Global.Busy=true;                                      //~v101R~
    }                                                              //~v101R~
    public static void cancel()                                    //~v101I~
    {                                                              //~v101I~
    	if (SS!=null)                                              //~v101I~
        {                                                          //~v101I~
        	swCancel=true;                                         //~v101I~
            try                                                        //~v101I~
            {                                                          //~v101I~
            	SS.close();                                            //~v101I~
			    infoStopListening();                                //~v101I~
            }                                                          //~v101I~
            catch (Exception e)                                        //~v101I~
            {                                                          //~v101I~
    			Dump.println(e,"Server:SS close");                     //~v101I~
            }                                                          //~v101I~
        	swCancel=false;                                        //~v101I~
        }                                                          //~v101I~
	}                                                              //~v101I~
    private void infoListening()                                   //~v107I~//~v101M~
    {                                                              //~v107I~//~v101M~
//        AView.showToast(R.string.InfoIPListening);                 //~v107I~//~v101R~
	}                                                              //~v107I~//~v101M~
    private static void infoStopListening()                               //~v101I~
    {                                                              //~v101I~
    	AView.showToast(R.string.InfoIPStopListening);             //~v101I~
	}                                                              //~v101I~
}
