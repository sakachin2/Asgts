//*CID://+1AecR~:                                   update#=   79; //+1AecR~
//******************************************************************************************************************//~v107I~
//1Aec 2015/07/26 set connection type for Server                   //+1AecI~
//1AbM 2015/07/03 BT:short sleep for BT disconnet fo exchange @@@@end and @@@@!end//~1AbMI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A0c 2013/03/05 mach info in title                               //~1A0cI~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//******************************************************************************************************************//~v107I~
//*@@@1 20110430 FunctionKey support                               //~@@@1I~
//****************************************************************************//~@@@1I~
package com.Asgts.jagoclient.partner;                               //~v107R~//~@@@2R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;
//import jagoclient.gui.HistoryTextField;
//import jagoclient.partner.PartnerGoFrame;
//import jagoclient.partner.PartnerThread;                         //~v107R~
import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.URunnable;
import com.Asgts.URunnableI;
import com.Asgts.Utils;
import com.Asgts.jagoclient.partner.PartnerThread;                  //~v107R~//~@@@2R~

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FileDialog;
//import java.awt.Menu;
//import java.awt.MenuBar;
//import java.awt.Panel;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import android.bluetooth.BluetoothSocket;

//import rene.util.list.ListClass;
//import rene.viewer.SystemViewer;                                 //~1318R~
//import rene.viewer.Viewer;                                       //~1318R~
//import com.Asgts.rene.viewer.Viewer;                              //~1318I~//~v107R~//~@@@2R~

/**
The partner frame contains a simple chat dialog and a button
to start a game or restore an old game. This class contains an
interpreters for the partner commands.
*/

//public class PartnerFrame extends CloseFrame                     //~v107R~
public class PartnerFrame extends jagoclient.partner.PartnerFrame  //~v107R~
    implements URunnableI
{                                                                  //~v107R~
//  Socket Server;                                                 //~v107R~
    private BluetoothSocket Server;                                        //~v107I~
    private String localDeviceName/*,remoteDeviceName*/;                                //~v107R~

	public PartnerFrame (String name,String Premotedevicename,String Plocaldevicename,boolean serving,BluetoothSocket PBTSocket)//~v107R~
    {                                                              //~v107I~
//  	super(name+Premotedevicename,serving);       //~v107R~     //~@@@@R~//~1A8iR~
//  	super(name+Premotedevicename,serving,jagoclient.partner.PartnerFrame.CONN_TITLE_BT);//~1A8iI~//+1AecR~
    	super(name+Premotedevicename,serving,                      //+1AecI~
        		(AG.isNFCBT ? jagoclient.partner.PartnerFrame.CONN_TITLE_NFC_BT : jagoclient.partner.PartnerFrame.CONN_TITLE_BT));//+1AecI~
		if (Dump.Y) Dump.println("Asgts:@@PartnerFrame@@ name="+name);//~@@@2R~
        BTConnection=true;	//for title                            //~1A0cI~
        localDeviceName=Plocaldevicename;                          //~v107R~
        AG.RemoteDeviceName=Premotedevicename;                     //~@@@2I~
//        remoteDeviceName=Premotedevicename;                        //~v107I~
		Server=PBTSocket;                                          //~v107R~
        AG.aPartnerFrame=this;                                     //~@@@@R~
        AG.aPartnerFrameIP=null;	//set by super()               //~@@@2I~
		if (Dump.Y) Dump.println("Asgts:PartnerFrame constructor namd="+name+",remote="+Premotedevicename+",local="+Plocaldevicename);//~v107R~//~@@@2R~
//        changeButton();                                            //~@@@@I~//~@@@2R~
	}
//****************************************************************************//~@@@@I~
//	private void changeButton()                                    //~@@@@I~
//    {                                                              //~@@@@R~//~@@@2R~
//        ButtonAction.init(this,R.string.BTGame,R.id.Connect);//change doaction label//~@@@@R~//~@@@2R~
//        ButtonAction.init(this,-1,R.id.Discoverable);              //~@@@@R~//~@@@2R~
//        ButtonAction.init(this,-1,R.id.ChangeBoard);               //~@@@@I~//~@@@2R~
//        ButtonAction.init(this,-1,R.id.ChangePiece);               //~@@@@I~//~@@@2R~
//    }                                                              //~@@@@I~//~@@@2R~
//****************************************************************************//~v107I~
//*from BTControl                                                  //~v107I~
//****************************************************************************//~v107I~
	public boolean connect ()                                      //~v107R~
	{                                                              //~v107R~
		if (Dump.Y) Dump.println("Asgts:PartnerFrame connect localdevicename="+localDeviceName);//~v107R~//~@@@2R~
		try
//  	{	Server=new Socket(s,p);                                //~v107R~
    	{                                                          //~v107R~
			Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
			if (Dump.Y) Dump.println("com.PartnerFrame in="+In.toString()+",out="+Out.toString());//~@@@2I~
		}
		catch (Exception e)
//  	{   return false;                                          //~v107R~
    	{                                                          //~v107I~
            Dump.println(e,"Asgts:PartnerFrame:connect");           //~v107R~//~@@@2R~
            return false;                                          //~v107I~
		}
//  	PT=new PartnerThread(In,Out,Input,Output,this);            //~@@@@R~
    	PT=new PartnerThread(In,Out,null,null,this);               //~@@@@I~
		PT.start();
		out("@@name "+                                     //~@@@2R~
//  		Global.getParameter("yourname","No Name"));            //~v107R~
    		localDeviceName);                                      //~v107R~
//        show();                                                  //~@@@@R~
        AG.RemoteStatus=AG.RS_BTCONNECTED;                         //~@@@2I~
		return true;
	}

//    public boolean connectvia (String server, int port,          //~@@@@R~
//        String relayserver, int relayport)                       //~@@@@R~
//    {                                                              //~v107R~//~@@@@R~
//        return false;   //userelay is off                          //~v107R~//~@@@@R~
//    }                                                            //~@@@@R~

//****************************************************************************//~v107I~
//*from Server.java                                                //~v107I~
//****************************************************************************//~v107I~
//  public void open (Socket server)                               //~v107R~
    public void open (BluetoothSocket server)                      //~v107I~
	{	if (Dump.Y) Dump.println("Starting partner server");       //~@@@1R~
		Server=server;
	    if (Dump.Y) Dump.println("Asgts:PartnerFrame:open BTSocket="+Server);//~v107R~//~@@@2R~
		try
		{	Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
			if (Dump.Y) Dump.println("partnerFrame open Out="+Out.toString()+",in="+In.toString());//~@@@2I~
		}
		catch (Exception e)
		{	if (Dump.Y) Dump.println("---> no connection");        //~@@@1R~
			new Message(this,Global.resourceString("Got_no_Connection_"));
			return;
		}
//  	PT=new PartnerThread(In,Out,Input,Output,this);            //~@@@@R~
    	PT=new PartnerThread(In,Out,null,null,this);               //~@@@@I~
		PT.start();
        AG.RemoteStatus=AG.RS_BTCONNECTED;                         //~@@@2I~
	}


	public void doclose ()
//  {	Global.notewindow(this,"partner");	                       //~@@@@R~
    {                                                              //~@@@@I~
//  	Global.notewindow(this,"partner");                         //~@@@@I~
      try	                                                       //~v107I~
      {                                                            //~v107I~
		out("@@@@end");                                    //~@@@2R~
//  	Utils.sleep(200);    //wait receive @@@@!end and partners.IN IOexception//~1AbMR~
        if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose after sent @@@@end");//~1AbMI~
        URunnable.setRunFunc(this/*URunnableI*/,500/*delay*/,this/*objparm*/,0/*int parm*/);//~1A2jR~//~1AbMR~
//        //Out.close() cause IOException on my PartnerThread,Close by Pathnerthread at @@@@!end//~1AbMR~
//        Out.close();                                             //~1AbMR~
//        if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose Out closed after sent @@@@end");//~1AbMR~
////      new CloseConnection(Server,In);                            //~v107R~//~1AbMR~
//        if (Dump.Y) Dump.println("Asgts:PartnerFrame:sleep before close IN");//~1AbMR~
//        if (In!=null)                                              //~v107I~//~1AbMR~
//            In.close();                                            //~v107I~//~1AbMR~
//        if (Server!=null)                                          //~v107I~//~1AbMR~
//        {                                                          //~v107I~//~1AbMR~
//            if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose close BTSocket="+Server);//~v107R~//~@@@2R~//~1AbMR~
//            Server.close();                                        //~v107I~//~1AbMR~
//        }                                                          //~v107I~//~1AbMR~
//        super.doclose();                                         //~1AbMR~
      }                                                            //~v107I~
      catch(Exception e)                                          //~v107I~
      {                                                            //~v107I~
      	Dump.println(e,"Asgts:partnerFrame:doclose");               //~v107R~//~@@@2R~
      }                                                            //~v107I~
	}
//***************************************************************  //~@@@2I~
//*ReadLine blocks close() so interupt then close               *  //~@@@2I~
//***************************************************************  //~@@@2I~
    public void closeStream()                                         //~@@@@I~//~@@@2R~
    {                                                              //~@@@@I~//~@@@2M~
	    if (Dump.Y) Dump.println("PartnerFrame close Stream");    //~@@@@I~//~@@@2R~
        if (PT.isAlive())                                          //~@@@2I~
        {                                                          //~@@@2I~
		    if (Dump.Y) Dump.println("PartnerFrame put @@@@end");  //~@@@2R~
            if (Out!=null)                                         //~@@@2I~
            {                                                      //~@@@2I~
                out("@@@@end"); //partnerthread chk this and throw IOException//~@@@2I~
            }                                                      //~@@@2I~
        }                                                          //~@@@2I~
    }                                                              //~@@@@I~//~@@@2M~
//***************************************************************  //~1AbMI~
	@Override                                                      //~1AbMI~
	public void runFunc(Object parmObj,int parmInt/*0*/)                        //~1214R~//~@@@@R~//~1AbMI~
    {                                                              //~1AbMI~
    	PartnerFrame pf=(PartnerFrame)parmObj;                    //~1AbMI~
        if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose runFunc");//~1AbMI~
      	try                                                        //~1AbMI~
      	{                                                          //~1AbMI~
            Out.close();  //Out.close() cause IOException on my PartnerThread,Close by Pathnerthread at @@@@!end//~1AbMI~
            if (Dump.Y) Dump.println("Asgts:PartnerFrame:runFunc Out closed Out="+Out);//~1AbMI~
//          if (In!=null)                                          //~1AbMR~
//  			In.close();                                        //~1AbMR~
//          if (Server!=null)                                      //~1AbMR~
//          {                                                      //~1AbMR~
//              if (Dump.Y) Dump.println("Asgts:PartnerFrame:doclose close BTSocket="+Server);//~1AbMR~
//              Server.close();                                    //~1AbMR~
//          }                                                      //~1AbMR~
//  	    super.doclose();                                       //~1AbMR~
    	    doclose2();  //do not out @@@@end,CloseConnection close Server and In//~1AbMI~
      	}                                                          //~1AbMI~
      	catch(Exception e)                                         //~1AbMI~
      	{                                                          //~1AbMI~
      		Dump.println(e,"Asgts:partnerFrame:runfunc");          //~1AbMR~
      	}                                                          //~1AbMI~
    }                                                              //~1AbMI~
}
