//*CID://+1AbMR~:                                   update#=   70; //+1AbMR~
//******************************************************************************************************************//~v107I~
//1AbM 2015/07/03 BT:short sleep for BT disconnet fo exchange @@@@end and @@@@!end//+1AbMI~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//******************************************************************************************************************//~v107I~
//package jagoclient.partner;                                      //~v107R~
package com.Asgts.jagoclient.partner;                               //~v107R~//~@@@@R~

import jagoclient.Dump;
import jagoclient.partner.partner.MsgThread;
//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.Asgts.AG;                                                //~v107R~//~@@@@R~
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.Utils;
import com.Asgts.awt.TextField;                                     //~v107R~//~@@@@R~
//import rene.viewer.Viewer;                                       //~1318R~
import com.Asgts.rene.viewer.Viewer;                              //~1318I~//~v107R~//~@@@@R~

/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

//public class PartnerThread extends Thread                        //~v107R~
public class PartnerThread extends jagoclient.partner.PartnerThread        //~v107I~
{   BufferedReader In;                                           
    PrintWriter Out;                                             
    Viewer T;                                                    
    PartnerFrame PF;                                             
    TextField Input;                                             
    public PartnerThread (BufferedReader in, PrintWriter out,    
        TextField input, Viewer t, PartnerFrame pf)              
//  {   In=in; Out=out; T=t; PF=pf; Input=input;                   //~v107R~
    {                                                              //~v107I~
        super(in,out,input,t,pf);                                  //~v107I~
        In=in; Out=out; T=t; PF=pf; Input=input;                   //~v107I~
//        new MsgThread(this,In,Out).start();                        //~@@@2I~//~@@@@R~
        if (Dump.Y) Dump.println("com.PartnerThread constructor in="+In.toString()+",out="+Out.toString());//~@@@@R~
    }                                                            
	public void run ()
	{                                                              //~@@@@R~
    	int swEnd=0;                                           //~@@@@R~
        AG.activeSessionType=AG.AST_BT;                            //~1A8gI~
		try                                                        //~@@@@I~
		{	while (true)
			{                                                      //~@@@@R~
				if (Dump.Y) Dump.println("com.ParthnerThread readline");//~@@@@R~
			 	String s=In.readLine();                            //~@@@@I~
				if (Dump.Y) Dump.println("From Partner: "+(s==null?"null":s));      //~1506R~//~@@@@M~
//                if (s==null || s.equals("@@@@end")) throw new IOException();//~@@@@R~
				if (s==null) throw new IOException();              //~@@@@I~
				if (s.equals("@@@@end"))                           //~@@@@R~
                {                                                  //~@@@@I~
                	PF.out("@@@@!end");	//                         //~@@@@I~
                	swEnd=1;                                       //~@@@@R~
                    Utils.sleep(100);	//time to receive @@@@!end before close//+1AbMI~
					throw new IOException();                       //~@@@@I~
                }                                                  //~@@@@I~
				if (s.equals("@@@@!end"))	//close request        //~@@@@I~
                {                                                  //~@@@@I~
                	swEnd=2;                                       //~@@@@I~
					throw new IOException();                       //~@@@@I~
                }                                                  //~@@@@I~
				if (s.startsWith("@@busy"))
//              {   T.append(Global.resourceString("____Server_is_busy____"));//~v107R~
                {                                                  //~v107I~
					return;
				}
				if (s.startsWith("@@msg "))                        //~@@@@I~
                {                                                  //~@@@@I~
                	MsgThread.enqRecvMsg(s.substring(6));          //~@@@@I~
                }                                                  //~@@@@I~
				else if (s.startsWith("@@")) PF.interpret(s);
//                else                                             //~v107R~
//                {   T.append(s+"\n");                            //~v107R~
//                    Input.requestFocus();                        //~v107R~
//                }                                                //~v107R~
			}
		}
		catch (IOException ioe)                                    //~@@@@R~
//      {   T.append(Global.resourceString("_____Connection_Error")+"\n");//~v107R~
        {                                                          //~v107I~
	        if (Dump.Y) Dump.println("PartnerThread IOErr swend="+swEnd);//~@@@@R~
            closeIO(); 	//should close stream before socket close  //~@@@@R~
            if (AG.aBT.swDestroy)	//socket close at at termination   //~@@@@I~
            {                                                      //~@@@@I~
	        	if (Dump.Y) Dump.println("PartnerThread IOErr at destroy");//~@@@@I~
            }                                                      //~@@@@I~
            else                                                   //~@@@@I~
            if (swEnd==1)  //received @@@@end(partner terminated),replyed @@@@!end//~@@@@R~
            {                                                      //~@@@@I~
//                AView.showToastLong(R.string.BTConnectionErr);         //~v107I~//~@@@@R~
                AG.aBT.connectionLost();	//close socket                               //~v107R~//~@@@@R~
                PF.interrupted(R.string.BTConnectionErr);//gameover(conn failed) msg//~@@@@R~
            }                                                      //~@@@@I~
            else                                                   //~@@@@R~
            if (swEnd==2)	//received @@@@!end,I'm closing        //~@@@@I~
            {                                                      //~@@@@I~
	        	if (Dump.Y) Dump.println("PartnerThread received @@!end connLost will close socket");//~@@@@I~
                AG.aBT.connectionLost();   //close socket          //~@@@@I~
            }                                                      //~@@@@I~
            else	//not sceduled close                           //~@@@@I~
            {                                                      //~@@@@I~
	        	Dump.println(ioe,"PartnerThread");                 //~@@@@I~
//                AView.showToastLong(R.string.BTConnectionErr);   //~@@@@R~
                AG.aBT.connectionLost();                           //~@@@@I~
                PF.interrupted(R.string.BTConnectionErr);          //~@@@@I~
            }                                                      //~@@@@I~
		}
		catch (Exception e)                                        //~@@@@I~
        {                                                          //~@@@@I~
        	Dump.println(e,"PartnerThread");                       //~@@@@I~
            closeIO();                                             //~@@@@R~
//          AView.showToastLong(R.string.BTConnectionErr);         //~@@@@R~
//            Window.popConnectedGoFrame();                        //~@@@@R~
        	AG.aBT.connectionLost();                               //~@@@@I~
            PF.interrupted(R.string.BTConnectionErr);              //~@@@@R~
		}                                                          //~@@@@I~
        AG.RemoteStatus=0;                                         //~@@@@I~
        AG.activeSessionType=0;                                    //~1A8gI~
	}
    private void closeIO()                                         //~@@@@R~
    {                                                              //~@@@@I~
	    if (Dump.Y) Dump.println("PartnerThread closeIO");         //~@@@@I~
        try
        {
        	if (Out!=null)                                         //~@@@@I~
            {                                                      //~@@@@I~
	    		Out.close();                                       //~@@@@R~
	    		if (Dump.Y) Dump.println("PartnerThread closed Out");//~@@@@I~
            }                                                      //~@@@@I~
	        if (In!=null)                                          //~@@@@I~
            {                                                      //~@@@@I~
		        In.close();                                        //~@@@@R~
	    		if (Dump.Y) Dump.println("PartnerThread closed In");//~@@@@I~
            }                                                      //~@@@@I~
        }
        catch(Exception e)
        {
        	Dump.println(e,"partnerThread:closeIO()");
        }//~@@@@I~
        
	    if (Dump.Y) Dump.println("PartnerThread closeIO end");     //~@@@@I~
    }                                                              //~@@@@I~
}

