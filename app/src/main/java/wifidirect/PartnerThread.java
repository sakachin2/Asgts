//*CID://+1Ac3R~:                             update#=   28;       //+1Ac3R~
//*******************************************************************//~3202I~
//1Ac3 2015/07/06 WD:Unpare after active session was closed        //+1Ac3I~
//1Ac1 2015/07/06 WD:try for exclusivity of WiFi and WiFiDirect,like as BT:1AbM exchange @@@@end & @@@@!end before close//~1Ac1I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//101a 2013/01/30 IP connection                                    //~3202I~
//*******************************************************************//~3202I~
package wifidirect;                                                //~1A8cR~

import jagoclient.Dump;
import jagoclient.partner.partner.MsgThread;

//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import wifidirect.PartnerFrame;                                    //~1A8cI~

import com.Asgts.AG;
import com.Asgts.R;
import com.Asgts.Utils;
import com.Asgts.awt.TextField;                                     //~2C26R~

//import rene.viewer.Viewer;                                       //~1318R~
import com.Asgts.rene.viewer.Viewer;                              //~1318I~//~2C26R~

/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

//public class PartnerThread extends Thread                        //~1A8cR~
public class PartnerThread extends jagoclient.partner.PartnerThread//~1A8cI~
{                                                                  //~1A8cR~
//    BufferedReader In;                                           //~1A8cI~
//    PrintWriter Out;                                             //~1A8cR~
//    Viewer T;                                                    //~1A8cR~
//    PartnerFrame PF;                                             //~1A8cR~
	TextField Input;
	private boolean swUnpair;                                      //+1Ac3I~
	public PartnerThread (BufferedReader in, PrintWriter out,
		TextField input/*null*/, Viewer t/*null*/, PartnerFrame pf)//~3201R~
//  {	In=in; Out=out; T=t; PF=pf; Input=input;                   //~1A8cR~
//      if (Dump.Y) Dump.println("PartnerThread start msgThraed"); //~3207I~//~1A8cR~
//  	startMsgThread();                                          //~3202I~//~1A8cR~
    {                                                              //~1A8cI~
        super(in,out,input,t,pf);                                  //~1A8cI~
	}
	public void run ()
//  {	try                                                        //~1A8gR~
    {                                                              //~1A8gI~
    	int swEnd=0;                                               //~1Ac1R~
        AG.activeSessionType=AG.AST_WD;                            //~1A8gI~
		try                                                        //~1A8gI~
//  	{	while (true)                                           //~@@@9R~
    	{                                                          //~@@@9I~
            if (Dump.Y) Dump.println("PartnerThread=====started="+this.toString());//~@@@9I~
    	 	while (true)                                           //~@@@9I~
			{                                                      //~3207R~
				if (Dump.Y) Dump.println("wifidirect:PartnerThread readLine");//~1A84I~//~1A8gI~
    		 	String s=In.readLine();                            //~3207I~//~@@@9R~
				if (Dump.Y) Dump.println("wifidirect:From Partner: "+s);      //~1506R~//~v1DjM~//~1A8gI~
//  			if (s==null || s.equals("@@@@end")) throw new IOException();//~1Ac1R~
    			if (s==null) throw new IOException();              //~1Ac1R~
				if (s.equals("@@@@end"))                           //~1Ac1R~
                {                                                  //~1Ac1R~
                	PF.out("@@@@!end");	//                         //~1Ac1R~
                	swEnd=1;                                       //~1Ac1R~
                    Utils.sleep(100);	//time to receive @@@@!end before close//~1Ac1R~
					throw new IOException();                       //~1Ac1R~
                }                                                  //~1Ac1R~
				if (s.equals("@@@@!end"))	//close request        //~1Ac1R~
                {                                                  //~1Ac1R~
                	swEnd=2;                                       //~1Ac1R~
					throw new IOException();                       //~1Ac1R~
                }                                                  //~1Ac1R~
				if (Dump.Y) Dump.println("From Partner:"+s+";");      //~1506R~//~@@@9R~
				if (s.startsWith("@@busy"))
//              {   T.append(Global.resourceString("____Server_is_busy____"));//~3201R~
                {                                                  //~3201I~
					return;
				}
				if (s.startsWith("@@msg "))                        //~3202I~
                {                                                  //~3202I~
                	MsgThread.enqRecvMsg(s.substring(6));          //~3202I~
                }                                                  //~3202I~
//  			else if (s.startsWith("@@")) PF.interpret(s);      //~3201R~
    			else if (s.startsWith("@@"))                       //~3201I~
    			{                                                  //~3201I~
                	try                                            //~3201I~
                    {                                              //~3201I~
    					PF.interpret(s);                           //~3201I~
                    }                                              //~3201I~
                    catch(Exception e)                             //~3201I~
                    {                                              //~3201I~
                    	Dump.println(e,"PathnerThread interpret:"+s);//~3201I~
                    }                                              //~3201I~
                }                                                  //~3201I~
				else
//              {   T.append(s+"\n");                              //~3201R~
                {                                                  //~3201I~
//  				Input.requestFocus();                          //~3201R~
				}
			}
		}
		catch (IOException e)
//  	{	T.append(Global.resourceString("_____Connection_Error")+"\n");//~3201R~
    	{                                                          //~3201I~
	        if (Dump.Y) Dump.println("IP PartnerThread IOErr swEnd="+swEnd);     //~3201I~//~1A8gR~
            if (swEnd==1)  //received @@@@end(partner terminated),replyed @@@@!end//~1Ac1R~
            {                                                      //~1Ac1R~
	        	if (Dump.Y) Dump.println("wifidirect.PartnerThread ioe after sent @@@@end");//~1Ac1R~
            }                                                      //~1Ac1R~
            else                                                   //~1Ac1R~
            if (swEnd==2)	//received @@@@!end,I'm closing        //~1Ac1R~
            {                                                      //~1Ac1R~
	        	if (Dump.Y) Dump.println("wifidirect.PartnerThread ioe after receive @@@@!end");//~1Ac1R~
            }                                                      //~1Ac1R~
            else                                                   //~1Ac1R~
	        	Dump.println(e,"wifidirect.PartnerThread");        //~1Ac1R~
//          new Message(PF,Global.resourceString("_____Connection_Error"));//~3201R~
            PF.interrupted(R.string.IPConnectionErr);//gameover(conn failed) msg//~3201I~
		}
		catch (Exception e)                                        //~1Ac1R~
        {                                                          //~1Ac1R~
        	Dump.println(e,"wifidirect.PartnerThread");            //~1Ac1R~
		}                                                          //~1Ac1R~
        if (swUnpair)                                              //+1Ac3I~
        	WDA.unpairFromPT();                                    //+1Ac3I~
        if (Dump.Y) Dump.println("wifidirect.PartnerThread===== Run Terminated");  //~3120I~//~@@@9R~//+1Ac3R~
        AG.RemoteStatus=0;                                             //~3131I~
        AG.RemoteInetAddress=null;                                 //~1A8fI~
        AG.LocalInetAddress=null;                                  //~1A8fI~
        AG.activeSessionType=0;                                    //~1A8gI~
	}
    //****************************************************         //+1Ac3I~
    public void unpair(boolean Punpair)                            //+1Ac3I~
    {                                                              //+1Ac3I~
        if (Dump.Y) Dump.println("wifidirect.PartnerThread unpair="+Punpair);//+1Ac3I~
        swUnpair=Punpair;                                          //+1Ac3I~
    }                                                              //+1Ac3I~
}

