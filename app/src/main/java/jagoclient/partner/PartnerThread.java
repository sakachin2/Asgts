//*CID://+1A8gR~:                             update#=   13;       //+1A8gR~
//*******************************************************************//~3202I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //+1A8gI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//101a 2013/01/30 IP connection                                    //~3202I~
//*******************************************************************//~3202I~
package jagoclient.partner;

import jagoclient.Dump;
import jagoclient.partner.partner.MsgThread;

//import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.Asgts.AG;                                               //~3213R~
import com.Asgts.R;                                                //~3213R~
import com.Asgts.awt.TextField;                                     //~2C26R~//~3213R~

//import rene.viewer.Viewer;                                       //~1318R~
import com.Asgts.rene.viewer.Viewer;                              //~1318I~//~2C26R~//~3213R~

/**
A thrad to expect input from a partner. The input is checked here
for commands (starting with @@).
*/

public class PartnerThread extends Thread
//{	BufferedReader In;                                             //~1A8cR~
{                                                                  //~1A8cI~
  protected                                                        //~1A8cI~
  	BufferedReader In;                                             //~1A8cI~
  protected                                                        //~1A8cI~
	PrintWriter Out;
  protected                                                        //~1A8cI~
	Viewer T;
  protected                                                        //~1A8cI~
	PartnerFrame PF;
	TextField Input;
	public PartnerThread (BufferedReader in, PrintWriter out,
		TextField input/*null*/, Viewer t/*null*/, PartnerFrame pf)//~3201R~
	{	In=in; Out=out; T=t; PF=pf; Input=input;
        if (Dump.Y) Dump.println("PartnerThread start msgThraed"); //~3207I~
    	startMsgThread();                                          //~3202I~
	}
	public void run ()
//  {	try                                                        //+1A8gR~
    {                                                              //+1A8gI~
        AG.activeSessionType=AG.AST_IP;                            //+1A8gI~
    	try                                                        //+1A8gI~
//  	{	while (true)                                           //~5222R~
    	{                                                          //~5222I~
            if (Dump.Y) Dump.println("PartnerThread=====started="+this.toString());//~5222I~
    	 	while (true)                                           //~5222I~
			{                                                      //~3207R~
				if (Dump.Y) Dump.println("PartnerThread before readline");//~3207I~//~5223R~
			 	String s=In.readLine();                            //~3207I~
				if (s==null || s.equals("@@@@end")) throw new IOException();
				if (Dump.Y) Dump.println("From Partner:"+s+",In="+In.toString());      //~1506R~//~5223R~
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
	        if (Dump.Y) Dump.println("IP PartnerThread IOErr");     //~3201I~
//          new Message(PF,Global.resourceString("_____Connection_Error"));//~3201R~
            PF.interrupted(R.string.IPConnectionErr);//gameover(conn failed) msg//~3201I~
		}
        if (Dump.Y) Dump.println("PartnerThread===== Run Terminated");//~5222I~
        AG.RemoteStatus=0;                                             //~3131I~
        AG.RemoteInetAddressLAN=null;                              //~1A8fI~
        AG.LocalInetAddressLAN=null;                               //~1A8fI~
        AG.activeSessionType=0;                                    //+1A8gI~
	}
    private void startMsgThread()                                       //~3202I~
    {                                                              //~3202I~
    	if (AG.msgThread==null                                     //~3202I~
        ||  !AG.msgThread.isAlive())                               //~3202I~
			AG.msgThread=new MsgThread(this,In,Out);
        else                                                       //~3202I~
			AG.msgThread=MsgThread.switchIO(this,In,Out);          //~3202R~
		AG.msgThread.start();                                      //~3202I~
    }                                                              //~3202I~
}

