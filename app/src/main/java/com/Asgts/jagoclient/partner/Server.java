//*CID://+v107R~:                             update#=   30;       //~v107I~
//******************************************************************************************************************//~v101R~//~v107I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//******************************************************************************************************************//~v107I~
package com.Asgts.jagoclient.partner;                              //~v107R~

import jagoclient.Dump;
//import jagoclient.datagram.DatagramMessage;
//import jagoclient.partner.PartnerFrame;                          //~v107R~
import com.Asgts.AG;                                               //+v107R~
import com.Asgts.AView;                                            //+v107R~
import com.Asgts.R;                                                //+v107R~
import com.Asgts.jagoclient.partner.PartnerFrame;                  //+v107R~
//import jagoclient.partner.partner.Partner;

import android.bluetooth.BluetoothSocket;

//import rene.util.list.ListElement;

/**
This is the server thread for partner connections. If anyone connects
to the server, a new PartnerFrame will open to handle the connection.
If the server starts, it will open a new PartnerServerThread, which
checks for datagrams that announce open partners.
*/

public class Server extends jagoclient.partner.Server              //~v107R~
{	int Port;
	boolean Public;
//    static public PartnerServerThread PST=null;                  //~v107R~
//    ServerSocket SS;                                             //~v107R~
    BluetoothSocket mBTSocket;                                    //~v107I~
//    InputStream mIS;                                             //~v107R~
//    OutputStream mOS;                                            //~v107R~
    String deviceName;                                             //~v107I~
    String localDeviceName;                                        //~v107I~
	/**
	@param p the server port
	@param publ server is public or not
	*/
//  public Server (int p, boolean publ)                            //~v107R~
    public Server (BluetoothSocket Psocket,String Pdevicename,String Plocaldevicename)//~v107R~
//  {	Port=p; Public=publ;                                       //~v107R~
	{                                                              //~v107I~
        super();    //dont execute start() to avoid "Thread already started" exception//~v107R~
        if (Dump.Y) Dump.println("Asgts Server constructor");      //+v107R~
        mBTSocket=Psocket;                                         //~v107I~
        deviceName=Pdevicename;                                    //~v107I~
        localDeviceName=Plocaldevicename;                          //~v107I~
//        mIS=BTService.getBTInputStream(mBTSocket);  //duplicate to PF.open()//~v107R~
//        if (mIS!=null)                                           //~v107R~
//        {                                                        //~v107R~
//            mOS=BTService.getBTOutputStream(mBTSocket);          //~v107R~
//            if (mOS!=null)                                       //~v107R~
				start();                                           //~v107R~
//        }                                                        //~v107R~
	}
	public void run ()
//  {	if (PST==null)                                             //~v107R~
    {                                                              //~v107I~
        if (Dump.Y) Dump.println("Asgts Server run start");        //+v107R~
//  	if (PST==null)                                             //~v107I~
//  		PST=new PartnerServerThread(Global.getParameter("serverport",6970)+2);//~v107R~
//  	try { sleep(1000); } catch (Exception e) {}                //~v107R~
		try
//      {   SS=new ServerSocket(Port);                             //~v107R~
        {                                                          //~v107I~
//  		while (true)                                           //~v107R~
//  		{	Socket S=SS.accept();                              //~v107R~
				BluetoothSocket S=mBTSocket;                        //~v107I~
//                if (Global.Busy) // user set the busy checkbox   //~v107R~
//                {   PrintWriter o=new PrintWriter(               //~v107R~
//                        new DataOutputStream(S.getOutputStream()),true);//~v107R~
//                    o.println("@@busy");                         //~v107R~
//                    S.close();                                   //~v107R~
//                    continue;                                    //~v107R~
//                }                                                //~v107R~
				PartnerFrame cf=
//  				new PartnerFrame(Global.resourceString("Server"),true,S);//~v107R~
//  				new PartnerFrame(Global.resourceString("Server"),deviceName,localDeviceName,true,S);//~v107R~
  					new PartnerFrame(AG.resource.getString(R.string.BTServer),deviceName,localDeviceName,true,S);//~v107I~
//                Global.setwindow(cf,"partner",500,400);          //~v107R~
//                cf.show();                                       //~v107R~
				cf.open(S);
//  		}                                                      //~v107R~
		}
		catch (Exception e)
		{	Dump.println(e,"Server Error");//@@@@ add e            //~1506R~
		}
        if (Dump.Y) Dump.println("Asgts Server run end");          //+v107R~
	}
	
	/**
	This is called, when the server is opened. It will announce
	the opening to known servers by a datagram.
	*/
	public void open ()
//  {   if (Public)                                                //~v107R~
    {                                                              //~v107I~
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
//  	Global.Busy=false;                                         //~v107R~
	}
	/**
	This is called, when the server is closed. It will announce
	the closing to known servers by a datagram.
	*/
	public void close()
//  {   if (!Public) return;                                       //~v107R~
    {                                                              //~v107I~
//        if (!Public) return;                                     //~v107I~
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
//        Global.Busy=true;                                        //~v107R~
	}
}
