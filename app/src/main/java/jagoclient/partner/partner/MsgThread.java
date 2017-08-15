//*CID://+@@@@R~:                                   update#=   59; //~v107I~//~@@@@R~
//******************************************************************************************************************//~v107I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//******************************************************************************************************************//~v107I~
package jagoclient.partner.partner;                                      //~v107R~//~@@@@R~
//package com.Asgts.jagoclient.partner;                               //~v107R~//~@@@@R~

import jagoclient.Dump;
import jagoclient.StopThread;
import jagoclient.dialogs.SayDialog;
import jagoclient.partner.PartnerThread;

//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import com.Asgts.AG;                                                //~v107R~//+@@@@R~
import com.Asgts.AView;                                            //+@@@@R~
import com.Asgts.R;                                                //+@@@@R~


/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

//public class PartnerThread extends Thread                        //~v107R~
public class MsgThread extends StopThread                           //~@@@@R~
//{ BufferedReader In;                                             //~@@@@R~
{                                                                  //~@@@@I~
    PrintWriter Out;                                             
    PartnerThread PT;                                              //~@@@@R~
    private boolean swIO;                                                  //~@@@@I~
    private        LinkedList<String> waitList=new LinkedList<String>();//~@@@2R~//~@@@@I~
    private static LinkedList<String> sendList=new LinkedList<String>();//~@@@@M~
    private static LinkedList<String> recvList=new LinkedList<String>();//~@@@2I~//~@@@@M~
    public MsgThread (PartnerThread pt,BufferedReader in, PrintWriter out)//~@@@@R~
    {                                                              //~v107I~
        super();                                                    //~@@@@R~
        PT=pt;                                                     //~@@@@R~
//        In=in;                                                   //~@@@@R~
        Out=out;                                                   //~@@@@I~
    	if (Dump.Y) Dump.println("MsgThread pt="+pt.toString()+",in="+in.toString()+",out="+out.toString());//~@@@@I~
    }                                                            
//***************************************************************  //~@@@@I~
	public void run ()
	{	try
		{                                                          //~@@@@I~
			while (!stopped())                                     //~@@@@I~
			{                                                      //~@@@@R~
                try                                                //~@@@@I~
                {                                                  //~@@@@I~
                    synchronized(waitList)                         //~@@@@I~
                    {                                              //~@@@@I~
                    	waitList.wait();                           //~@@@@R~
                    }                                              //~@@@@I~
      				if (Dump.Y) Dump.println("MsgThread posted");  //~@@@@I~
		            if (stopped())                                 //~@@@@I~
                    	break;                                     //~@@@@I~
                    String[] sa;                                   //~@@@@I~
                    sa=deqSendMsg();                               //~@@@@I~
                    if (sa.length!=0)                              //~@@@@I~
                    	sendMsg(sa);                                 //~@@@@I~
                    sa=deqRecvMsg();                               //~@@@@R~
                    if (sa.length!=0)                              //~@@@@R~
                    	recvMsg(sa);                               //~@@@@R~
                }                                                  //~@@@@I~
                catch (InterruptedException e)                     //~@@@@I~
                {                                                  //~@@@@I~
                	if (stopped())                                  //~@@@@I~
	                    Dump.println("MsgThread:requestList wait interrupted");//~@@@@I~
                    else                                           //~@@@@I~
                    	Dump.println(e,"MsgThread:requestList wait interrupted");//~@@@@R~
                }//wait notify                                     //~@@@@I~
			}
                                                                   //~@@@@I~
		}
		catch (Exception e)                                        //~@@@@I~
        {                                                          //~@@@@I~
            if (stopped())                                          //~@@@@I~
	        	Dump.println("MsgThread:exception at stopit");     //~@@@@I~
            else                                                   //~@@@@I~
        		Dump.println(e,"MsgThread");                       //~@@@@R~
		}                                                          //~@@@@I~
    	if (Dump.Y) Dump.println("MsgThread run end");             //~@@@@I~
	}
//***************************************************************  //~@@@@I~
	public static MsgThread switchIO(PartnerThread pt,BufferedReader in, PrintWriter out)//~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("MsgThread swithIO pt="+pt.toString());//~@@@@I~
        if (AG.msgThread!=null)                                    //~@@@@I~
        {                                                          //~@@@@I~
	    	if (Dump.Y) Dump.println("MsgThread stop old pt="+AG.msgThread.PT.toString());//~@@@@I~
        	AG.msgThread.stopit();                                 //~@@@@R~
            synchronized(AG.msgThread.waitList)                                 //~@@@@I~
            {                                                      //~@@@@I~
		    	AG.msgThread.waitList.notifyAll();    //pos wait                //~@@@@R~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
		return new MsgThread(pt,in,out);                           //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************  //~@@@@I~
	private void sendMsg(String[] Psa)                             //~@@@@I~
    {                                                              //~@@@@I~
    	if (!PT.isAlive())                                         //~@@@@I~
        {                                                          //~@@@@I~
        	return;                                                //~@@@@I~
        }                                                          //~@@@@I~
        for (int ii=0;ii<Psa.length;ii++)                          //~@@@@I~
        {                                                          //~@@@@I~
			Out.println("@@msg "+Psa[ii]);                         //~@@@@I~
      		if (Dump.Y) Dump.println("MsgThread sendOut="+Out.toString()+":"+Psa[ii]);//~@@@@R~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************  //~@@@@I~
	private void recvMsg(String[] Psa)                             //~@@@@I~
    {                                                              //~@@@@I~
      	if (Dump.Y) Dump.println("MsgThread recvMsg ctr="+Psa.length);//~@@@@I~
        if (AG.sayDialog==null)                                    //~@@@@I~
        {                                                          //~@@@@I~
	        new SayDialog(AG.currentFrame);                    //~@@@@I~
        }                                                          //~@@@@I~
		AG.sayDialog.receiveMsg(Psa);                              //~@@@@R~
    }                                                              //~@@@@I~
//***************************************************************  //~@@@2I~//~@@@@M~
	public static void enqSendMsg(String Pmsg)                     //~@@@2I~//~@@@@M~
    {                                                              //~@@@2I~//~@@@@M~
      	if (Dump.Y) Dump.println("MsgTHread enqSendMsg="+Pmsg); //~@@@2R~//~@@@@R~
    	synchronized(sendList)                                     //~@@@2R~//~@@@@M~
        {                                                          //~@@@2I~//~@@@@M~
        	sendList.add(Pmsg);                                    //~@@@2R~//~@@@@M~
        }                                                          //~@@@2I~//~@@@@M~
        if (AG.msgThread!=null && AG.msgThread.isAlive())          //~@@@@I~
        {                                                          //~@@@@I~
            synchronized(AG.msgThread.waitList)                                 //~@@@@I~
            {                                                      //~@@@@I~
	        	AG.msgThread.waitList.notifyAll();    //pos wait                //~@@@@I~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
        else                                                       //~@@@@I~
            AView.showToast(R.string.ErrNoConnectionForConversation);//~@@@@I~
    }                                                              //~@@@2I~//~@@@@M~
//***************************************************************  //~@@@2I~//~@@@@M~
	public static String[] deqSendMsg()                            //~@@@2R~//~@@@@M~
    {                                                              //~@@@2I~//~@@@@M~
        String[] sa;                                               //~@@@2I~//~@@@@M~
      	if (Dump.Y) Dump.println("MsgThread deqSendMsg");       //~@@@2R~//~@@@@R~
        synchronized(sendList)                                     //~@@@2R~//~@@@@M~
        {                                                          //~@@@2I~//~@@@@M~
        	sa=sendList.toArray(new String[0]);                    //~@@@2R~//~@@@@M~
            sendList.clear();                                      //~@@@2R~//~@@@@M~
        }                                                          //~@@@2I~//~@@@@M~
      	if (Dump.Y) Dump.println("MsgThread deqSendMsg ctr="+sa.length);//~@@@@I~
        return sa;                                                 //~@@@2I~//~@@@@M~
    }                                                              //~@@@2I~//~@@@@M~
//***************************************************************  //~@@@2I~//~@@@@M~
//*from PartnerThread                                              //~@@@@I~
//***************************************************************  //~@@@@I~
	public static void enqRecvMsg(String Pmsg)                     //~@@@2I~//~@@@@M~
    {                                                              //~@@@2I~//~@@@@M~
      	if (Dump.Y) Dump.println("MsgTHread enqRecvMsg="+Pmsg); //~@@@2I~//~@@@@R~
    	synchronized(recvList)                                     //~@@@2I~//~@@@@M~
        {                                                          //~@@@2I~//~@@@@M~
        	recvList.add(Pmsg);                                    //~@@@2I~//~@@@@M~
            if (AG.msgThread!=null && AG.msgThread.isAlive())      //~@@@@I~
            synchronized(AG.msgThread.waitList)                                 //~@@@@I~
            {                                                      //~@@@@I~
		        AG.msgThread.waitList.notifyAll();    //pos wait                //~@@@@R~
            }                                                      //~@@@@I~
        }                                                          //~@@@2I~//~@@@@M~
    }                                                              //~@@@2I~//~@@@@M~
//***************************************************************  //~@@@2I~//~@@@@M~
	public static String[] deqRecvMsg()                            //~@@@2I~//~@@@@M~
    {                                                              //~@@@2I~//~@@@@M~
        String[] sa;                                               //~@@@2I~//~@@@@M~
        synchronized(recvList)                                     //~@@@2I~//~@@@@M~
        {                                                          //~@@@2I~//~@@@@M~
        	sa=recvList.toArray(new String[0]);                    //~@@@2I~//~@@@@M~
            recvList.clear();                                      //~@@@2I~//~@@@@M~
        }                                                          //~@@@2I~//~@@@@M~
      	if (Dump.Y) Dump.println("MsgThread deqRecvMsg ctr="+sa.length);          //~@@@2I~//~@@@@I~
        return sa;                                                 //~@@@2I~//~@@@@M~
    }                                                              //~@@@2I~//~@@@@M~
}

