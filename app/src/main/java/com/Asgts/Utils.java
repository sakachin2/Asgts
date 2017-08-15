//*CID://+1Ac0R~: update#= 171;                                    //~1Ac0R~
//**********************************************************************//~1107I~
//1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//~1Ac0I~
//1A8bk2015/02/28 (BUG)old pertner communication fail by multiple IP address//~1A8bI~
//1A86 2015/02/26 get IPAddr by MacAddr                            //~1A86I~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A67I~//~1A6aI~
//1A67 2015/02/05 (kan)                                            //~1A67I~
//1A4A 2014/12/09 function to show youtube                         //~1A4AI~
//1A4x 2014/12/08 (Bug)Clipboard (String)item.getText() may cause invalid cast exception; .toString() is valid//~1A4xI~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//1A05 2013/03/02 reject Accept/Connect if IP addr not avail       //~1A05I~
//101a 2013/01/30 IP connection                                    //~v101I~
//1075:121207 control dumptrace by manifest debuggable option      //~v105I~
//1063:121124 menu to display ip address for pertner connection    //~v106I~
//**********************************************************************//~1107I~//~v106M~
package com.Asgts;                                         //~1107R~  //~1108R~//~1109R~//~v107R~//~v101R~


import java.net.Inet6Address;
import java.net.InetAddress;                                       //~v106R~
import java.net.NetworkInterface;                                  //~v106R~
import java.net.Socket;
import java.util.Enumeration;                                      //~v106I~
import java.text.SimpleDateFormat;
import java.util.Date;

import jagoclient.Dump;

import android.annotation.TargetApi;
import android.content.Context;                                    //~v107R~
import android.content.pm.ApplicationInfo;                         //~v107R~
import android.content.pm.PackageManager;                          //~v107R~
import android.content.pm.PackageManager.NameNotFoundException;    //~v107R~
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextPaint;
import android.widget.TextView;
import android.content.ClipDescription;
import android.content.ClipboardManager;                           //~1A4sI~
import android.content.ClipData;                                   //~1A4sI~
import android.content.Intent;

import com.Asgts.AG;                                                //~v107R~//~v101R~
//**********************************************************************//~1107I~
public class Utils                                            //~1309R~//~@@@@R~
{                                                                  //~0914I~

    public static final String	IPA_NA="N/A";                      //~1A05I~
//**********************************                               //~@@@@I~
//*from Alert,replyed Yes                                          //~@@@@I~
//**********************************                               //~@@@@I~
	public static void stopFinish()		//from Alert by Stop:Yes   //~@@@2I~
    {                                                              //~@@@2I~
    	if (Dump.Y) Dump.println("AjagoUtils stopFinish");         //~@@@2I~
       try                                                        //~@@@2I~
        {                                                          //~@@@2I~
        	AG.aMain.destroyClose();                               //~@@@2R~
        }                                                          //~@@@2I~
        catch (Exception e)                                               //~@@@2I~
        {                                                          //~@@@2I~
        	Dump.println(e,"stopFinish");                          //~@@@2I~
            finish();                                              //~@@@2I~
        }                                                          //~@@@2I~
    }                                                              //~@@@2I~
//**********************************                               //~1211I~
	public static void exit(int Pexitcode)	//from Mainframe:doclose()                         //~1309I~//~@@@2R~
    {                                                              //~1309I~
    	if (Dump.Y) Dump.println("AjagoUtils exit()");//~1309I~//~1506R~//~@@@2R~
		finish();                                                  //~1309R~
    }                                                              //~1309I~
//*******************************************************          //~@@@@R~
//*from onDestroy,kill process                                                  //~@@@@I~//~@@@2R~
//*******************************************************          //~@@@@I~
	public static void exit(int Pexitcode,boolean Pkill)           //~1329I~
    {                                                              //~1329I~
    	if (Dump.Y) Dump.println("AjagoUtils kill exit() code="+Pexitcode+",kill="+Pkill);//~1506R~//~@@@2R~
        if (Pkill)                                                 //~1329I~
        {                                                          //~1503I~
//            System.runFinalizersOnExit(true);   //depricated unsafe//~@@@@R~
//            System.exit(Pexitcode);                                //~1329I~//~@@@@R~
    		if (Dump.Y) Dump.println("AjagoUtils kill exit() killProcess");//~@@@2I~
            Dump.close();                                          //~1503I~//~@@@2M~
            android.os.Process.killProcess(android.os.Process.myPid());                  //~@@@@I~//~@@@2R~
//**          finish only,check static is reused                   //~@@@2I~
        }                                                          //~1503I~
//        else                                                       //~@@@@I~//~@@@2R~
//        {                                                        //~@@@2R~
//            if (Dump.Y) Dump.println("AjagoUtils kill exit() exit()");//~@@@2R~
////            exit(Pexitcode);                                           //~1329I~//~@@@@R~//~@@@2R~
//        }                                                        //~@@@2R~
//        finish();                                                //~@@@2R~
    }                                                              //~1329I~
//**********************************                               //~@@@@I~
//*from Alert,replyed Yes                                          //~@@@@I~
//**********************************                               //~@@@@I~
//    public static void finish()                                         //~1309I~//~@@@@R~
//    {                                                              //~1309I~//~@@@@R~
//        if (Dump.Y) Dump.println("AjagoUtils finish requested "+finished);//~1506R~//~@@@@R~
//        if (finished)                                              //~1309M~//~@@@@R~
//            return ;                                               //~1309M~//~@@@@R~
//        AG.aMain.destroyClose();                                 //~@@@@R~
//        AG.aMain.finish();                                        //~1309I~//~@@@@R~
//        if (Dump.Y) Dump.println("AjagoUtils context finish request");//~1506R~//~@@@@R~
//        sleep(1200);//wait subtread termination  1.2sec            //~1503R~//~@@@@R~
//        if (Dump.Y) Dump.println("AjagoUtils context finish request after sleep 1200");//~1506R~//~@@@@R~
//        Dump.close();                                              //~1503I~//~@@@@R~
//        finished=true;                                             //~1309I~//~@@@@R~
//    }                                                              //~1309I~//~@@@@R~
//**********************************                               //~@@@2I~
//*Activity:finish()                                               //~@@@2I~
//**********************************                               //~@@@2I~
    public static void finish()                                    //~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("Utils finish");             //~@@@@I~//~@@@2R~
//        closeFinish();                                             //~@@@@I~//~@@@2R~
        AG.aMain.finish();	//schedule onDestroy                   //~@@@2I~
    }                                                              //~@@@@I~
//**********************************                               //~@@@2I~
//    public static void closeFinish()                               //~@@@@I~//~@@@2R~
//    {                                                              //~@@@@I~//~@@@2R~
//        if (Dump.Y) Dump.println("Utils closeFinish finished="+AG.Utils_finished);//~@@@@M~//~@@@2R~
//        if (AG.Utils_finished)                                              //~@@@@I~//~@@@2R~
//            return ;                                               //~@@@@I~//~@@@2R~
//        AG.aMain.destroyClose();    //close stream the finish      //~@@@@I~//~@@@2R~
//    }                                                              //~@@@@I~//~@@@2R~
//    public static void closedFinish()                              //~@@@@I~//~@@@2R~
//    {                                                              //~@@@@I~//~@@@2R~
//        if (Dump.Y) Dump.println("Utils closedFinish finished="+AG.Utils_finished);//~@@@@I~//~@@@2R~
//        if (AG.Utils_finished)                                              //~@@@@I~//~@@@2R~
//            return ;                                               //~@@@@I~//~@@@2R~
//        AG.aMain.finish();  //schedule onDestroy                   //~@@@@I~//~@@@2R~
//        if (Dump.Y) Dump.println("Utils context finish request");//~@@@@I~//~@@@2R~
//        sleep(200);//wait subtread termination  1.2sec             //~@@@@R~//~@@@2R~
//        if (Dump.Y) Dump.println("Utils context finish request after sleep 200");//~@@@@I~//~@@@2R~
//        Dump.close();                                              //~@@@@I~//~@@@2R~
//        AG.Utils_finished=true;                                             //~@@@@I~//~@@@2R~
//    }                                                              //~@@@@I~//~@@@2R~
//**********************************                               //~@@@@I~
	public static void sleep(long Pmilisec)                        //~1503I~
    {                                                              //~1503I~
        try                                                        //~1503I~
        {                                                          //~1503I~
        	Thread.sleep(Pmilisec);//wait subtread termination  1.2sec//~1503I~
        }                                                          //~1503I~
        catch(InterruptedException e)                              //~1503I~
		{                                                          //~1503I~
        	Dump.println(e,"sleep interrupted Exception");         //~1503I~
		}                                                          //~1503I~
    }                                                              //~1503I~
//**********************************                               //~1412I~
//*elapsed time calc                                               //~1412I~
//**********************************                               //~1412I~
	public static final int TSID_TITLE_TOUCH=0;                   //~1412I~
	private static final int TSID_MAX        =1;                   //~1412I~
	private static long[] Stimestamp=new long[TSID_MAX];                                 //~1412I~
	public static long setTimeStamp(int Pid)                       //~1412I~
    {                                                              //~1412I~
        if (Pid>=TSID_MAX)                                         //~1412I~
            return 0;                                              //~1412I~
        long t=System.currentTimeMillis();                         //~1412I~
        Stimestamp[Pid]=t;                                         //~1412I~
    	if (Dump.Y) Dump.println("AjagoUtils setTimeStamp id="+Pid+",ts="+Long.toHexString(t));//~1506R~
        return t;                                                  //~1412I~
    }                                                              //~1412I~
	public static int getElapsedTimeMillis(int Pid)                //~1412I~
    {                                                              //~1412I~
        if (Pid>=TSID_MAX)                                         //~1412I~
            return 0;                                              //~1412I~
        if (Stimestamp[Pid]==0)                                    //~1413I~
            return 0;                                              //~1413I~
        long t=System.currentTimeMillis();                         //~1412I~
    	if (Dump.Y) Dump.println("AjagoUtils getElapsed now id="+Pid+",ts="+Long.toHexString(t));//~1506R~
        int  elapsed=(int)(t-Stimestamp[Pid]);                     //~1412I~
    	if (Dump.Y) Dump.println("AjagoUtils getElapsetTimeMillis id="+Pid+",ts="+Integer.toHexString(elapsed));//~1506R~
        Stimestamp[Pid]=0;                                         //~1413I~
        return elapsed;                                            //~1412I~
    }                                                              //~1412I~
//**********************************                               //~1425I~
//*edit date/time                                                  //~1425I~
//**********************************                               //~1425I~
	public static final int TS_DATE_TIME=1;                        //~1425I~
	public static final int TS_MILI_TIME=2;                        //~1425I~
	private static final SimpleDateFormat fmtdt=new SimpleDateFormat("yyyyMMdd-HHmmss");//~1425I~
	private static final SimpleDateFormat fmtms=new SimpleDateFormat("HHmmss.SSS");//~1425I~
	public static String getTimeStamp(int Popt)                    //~1425I~
    {                                                              //~1425I~
        SimpleDateFormat f;                                        //~1425I~
    //**********************:                                      //~1425I~
    	switch(Popt)                                               //~1425I~
        {                                                          //~1425I~
        case TS_DATE_TIME:                                         //~1425I~
        	f=fmtdt;                                               //~1425I~
            break;                                                 //~1425I~
        case TS_MILI_TIME:                                         //~1425I~
        	f=fmtms;                                               //~1425I~
            break;                                                 //~1425I~
        default:                                                   //~1425I~
        	return null;                                           //~1425I~
        }                                                          //~1425I~
        return f.format(new Date());                               //~1425I~
    }                                                              //~1425I~
//**********************************                               //~1425I~
//* Digit Thread ID                                                //~1425I~
//**********************************                               //~1425I~
	public static String getThreadId()                             //~1425I~
    {                                                              //~1425I~
    //**********************:                                      //~1425I~
    	int tid=(int)Thread.currentThread().getId();               //~1425I~
        if (tid<10)                                                //~1425I~
        	return "0"+tid;                                        //~1425I~
        return Integer.toString(tid);                              //~1425I~
    }                                                              //~1425I~
//**********************************                               //~1425I~
	public static String getThreadTimeStamp()                      //~1425I~
    {                                                              //~1425I~
    //**********************:                                      //~1425I~
    	String tidts=getThreadId()+":"+getTimeStamp(TS_MILI_TIME);  //~1425I~
        return tidts;                                              //~1425I~
    }                                                              //~1425I~
//***************************************************************************//~1A8bI~
	private static int SswDirect;                                  //~1A8bI~
    private static final int MAC_LOCAL_ADDRESS=0x02;	//if off global address//~1A8bI~
//***********                                                      //~1A8bI~
    public static String getIPAddressDirect()                      //~1A8bI~
    {                                                              //~1A8bI~
    	SswDirect=1; //local only                                  //~1A8bI~
    	String ipa=getIPAddress(false);                            //~1A8bI~
    	SswDirect=0;                                               //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~1A8bI~
//***********                                                      //~1A8bI~
    public static String getIPAddressAll()                         //~1A8bI~
    {                                                              //~1A8bI~
    	SswDirect=2; //both global and local                       //~1A8bI~
    	String ipa=getIPAddress(false);                            //~1A8bI~
    	SswDirect=0;                                               //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~1A8bI~
//**********************************                               //~v106I~
    public static String getIPAddress(boolean Pipv6)                            //~v106I~//~v101R~
    {                                                              //~v106I~
    	String ipa=IPA_NA;                                          //~v106R~//~1A05R~
    	String ipv6="";                                           //~v106I~
        int ctr=0;                                                 //~1A67I~
    //**********************:                                      //~v106I~
        try                                                        //~v106I~
        {                                                          //~v106I~
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();//~v106I~
            while(interfaces.hasMoreElements())                    //~v106I~
            {                                                      //~v106I~
                NetworkInterface network = interfaces.nextElement();//~v106I~
    		    if (Dump.Y) Dump.println("getIPAddress mac="+getMacString(network.getHardwareAddress()));//~1A67I~
    		    byte[] bmac=network.getHardwareAddress();          //~1A8bI~
                if (bmac!=null)                                    //~1A8bI~
                {                                                  //~1A8bI~
                    if ((bmac[0] & MAC_LOCAL_ADDRESS)!=0)          //~1A8bI~
                    {                                              //~1A8bI~
                        if (SswDirect==0) //global only            //~1A8bI~
                            continue;                              //~1A8bI~
                    }                                              //~1A8bI~
                    else    //global                               //~1A8bI~
                    {                                              //~1A8bI~
                        if (SswDirect==1)   //local only           //~1A8bI~
                            continue;                              //~1A8bI~
                    }                                              //~1A8bI~
                }                                                  //~1A8bI~
    		    if (Dump.Y) Dump.println("isPont2point="+network.isPointToPoint()+",isUp="+network.isUp());//~1A6aI~
    		    if (Dump.Y) Dump.println("name="+network.getName()+",displayName="+network.getDisplayName());//~1A6aI~
    		    if (Dump.Y) Dump.println("toString="+network.toString());//~1A6aI~
                Enumeration<InetAddress> addresses = network.getInetAddresses();//~v106I~
                while(addresses.hasMoreElements())                 //~v106I~
                {                                                  //~v106I~
                    InetAddress na=addresses.nextElement();        //~v106R~
                    String ipa2=na.getHostAddress();               //~v106I~
                    if (na.isLoopbackAddress()                     //~v106R~
                    ||  na.isLinkLocalAddress()                    //~v106R~
//                  ||  na.isSiteLocalAddress()                    //~v106R~
                    )                                              //~v106I~
                    	continue;                                  //~v106I~
                    if (na instanceof Inet6Address)//ipv6          //~v106M~
                    {                                              //~v106I~
                    	ipv6=ipa2;                                 //~v106I~
                    	continue;                                  //~v106M~
                    }                                              //~v106I~
			        if (Dump.Y) Dump.println("getIPAddress:"+ipa2);//~v106R~
                  if (ctr++==0)                                    //~1A67I~
                    ipa=ipa2;                                      //~v106R~
				  else                                             //~1A67I~
                    ipa+=";"+ipa2;                                 //~1A67I~
                    break;                                         //~v106R~
                }                                                  //~v106I~
            }                                                      //~v106I~
        }                                                          //~v106I~
        catch(Exception e)                                         //~v106I~
        {                                                          //~v106I~
        	Dump.println(e,"getIPAddress");                        //~v106I~
        }                                                          //~v106I~
//      if (!Pipv6)                                                //~v101I~//~1A8bI~
        if (!Pipv6 || ipv6.equals(""))                             //~1B10R~//~1A8bI~
	        return ipa;                                             //~v101I~
        return ipa+","+ipv6;                                       //~v106R~
    }                                                              //~v106I~
//**********************************                               //~1A86I~
    public static String getIPAddressFromMacAddr(String Pmacaddr)  //~1A86I~
    {                                                              //~1A86I~
    	String ipa=IPA_NA;                                         //~1A86I~
//  	String ipv6="";                                            //~1A86I~
        int ctr=0;                                                 //~1A86I~
    //**********************:                                      //~1A86I~
        try                                                        //~1A86I~
        {                                                          //~1A86I~
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();//~1A86I~
            while(interfaces.hasMoreElements())                    //~1A86I~
            {                                                      //~1A86I~
                NetworkInterface network = interfaces.nextElement();//~1A86I~
                String macaddr=getMacString(network.getHardwareAddress());//~1A86I~
                if (Dump.Y) Dump.println("getIPAddressFromMacaddr mac="+macaddr+",parm="+Pmacaddr);//~1A86I~
    		    if (Dump.Y) Dump.println("isPont2point="+network.isPointToPoint()+",isUp="+network.isUp());//~1A86I~
    		    if (Dump.Y) Dump.println("name="+network.getName()+",displayName="+network.getDisplayName());//~1A86I~
    		    if (Dump.Y) Dump.println("toString="+network.toString());//~1A86I~
                if (!macaddr.equals(Pmacaddr))                     //~1A86I~
                	continue;                                      //~1A86I~
                Enumeration<InetAddress> addresses = network.getInetAddresses();//~1A86I~
                while(addresses.hasMoreElements())                 //~1A86I~
                {                                                  //~1A86I~
                    InetAddress na=addresses.nextElement();        //~1A86I~
                    String ipa2=na.getHostAddress();               //~1A86I~
                    if (na.isLoopbackAddress()                     //~1A86I~
                    ||  na.isLinkLocalAddress()                    //~1A86I~
//                  ||  na.isSiteLocalAddress()                    //~1A86I~
                    )                                              //~1A86I~
                    	continue;                                  //~1A86I~
                    if (na instanceof Inet6Address)//ipv6          //~1A86I~
                    {                                              //~1A86I~
//                    	ipv6=ipa2;                                 //~1A86I~
                    	continue;                                  //~1A86I~
                    }                                              //~1A86I~
			        if (Dump.Y) Dump.println("getIPAddress:"+ipa2);//~1A86I~
                  if (ctr++==0)                                    //~1A86I~
                    ipa=ipa2;                                      //~1A86I~
				  else                                             //~1A86I~
                    ipa+=";"+ipa2;                                 //~1A86I~
                    break;                                         //~1A86I~
                }                                                  //~1A86I~
            }                                                      //~1A86I~
        }                                                          //~1A86I~
        catch(Exception e)                                         //~1A86I~
        {                                                          //~1A86I~
        	Dump.println(e,"getIPAddressFromMacAddr");             //~1A86I~
        }                                                          //~1A86I~
//      if (!Pipv6 || ipv6.equals(""))                             //~1A86I~
//          return ipa;                                            //~1A86I~
//      return ipa+","+ipv6;                                       //~1A86I~
        return ipa;                                                //~1A86I~
    }                                                              //~1A86I~
//***********************************************************************//~v107R~
    public static boolean isDebuggable(Context ctx)                //~v107R~
    {                                                              //~v107R~
        PackageManager manager = ctx.getPackageManager();          //~v107R~
        ApplicationInfo appInfo = null;                            //~v107R~
        try                                                        //~v107R~
        {                                                          //~v107R~
            appInfo = manager.getApplicationInfo(ctx.getPackageName(), 0);//~v107R~
        }                                                          //~v107R~
        catch (NameNotFoundException e)                            //~v107R~
        {                                                          //~v107R~
            return false;                                          //~v107R~
        }                                                          //~v107R~
        if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE)//~v107R~
            return true;                                           //~v107R~
        return false;                                              //~v107R~
    }                                                              //~v107R~
//***********************************************************************//~v101R~
    public static boolean canDisplay(TextView Pview,int Pcodepoint)//~v101R~
    {                                                              //~v101R~
    	float w=stringWidth(Pview,Pcodepoint);                     //~v101I~
    	float wff=stringWidth(Pview,0xffff);                       //~v101I~
        return w>wff;                                              //~v101I~
    }                                                              //~v101R~
//***********************************************************************//~v101I~
    public static float stringWidth(TextView Pview,int Pcodepoint) //~v101I~
    {                                                              //~v101I~
    	TextPaint p=Pview.getPaint();                              //~v101I~
		return paintStringWidth(p,Pcodepoint);                     //~v101I~
    }                                                              //~v101I~
//***********************************************************************//~v101I~
    public static float stringWidth(Paint Ppaint,int Pcodepoint)   //~v101I~
    {                                                              //~v101I~
		return paintStringWidth(Ppaint,Pcodepoint);                //~v101I~
    }                                                              //~v101I~
//***********************************************************************//~v101I~
    public static float stringWidth(int Pcodepoint)                //~v101I~
    {                                                              //~v101I~
        Paint p=new Paint();                                       //~v101I~
		return paintStringWidth(p,Pcodepoint);                     //~v101I~
    }                                                              //~v101I~
//***********************************************************************//~v101I~
    private static float paintStringWidth(Paint Ppaint,int Pcodepoint)//~v101I~
    {                                                              //~v101I~
        int[] cp=new int[1];                                       //~v101I~
        cp[0]=Pcodepoint;                                          //~v101I~
        String s=new String(cp,0,1);                               //~v101I~
        float w=Ppaint.measureText(s);                             //~v101I~
        if (Dump.Y) Dump.println("Util:canDisplay cp="+Integer.toHexString(Pcodepoint)+",str="+s+",width="+w);//~v101I~
        return w;                                                  //~v101I~
    }                                                              //~v101I~
//***********************************************************      //~1A4sI~
//*Clipboard                                                       //~1A4sI~
//***********************************************************      //~1A4sI~
  	public static Object clipboard_getcm()                         //~1A4sI~
	{                                                              //~1A4sI~
    	Object cm=getcm();                                         //~1A4sI~
		if (Dump.Y) Dump.println("Utils clipboard_getcm="+cm.toString());//~1A4sI~
        return cm;                                                 //~1A4sI~
	}                                                              //~1A4sI~
//***********************************************************      //~1A4sI~
	public static String clipboard_getText()                       //~1A4sI~
	{                                                              //~1A4sI~
    	String text;                                               //~1A4sI~
        try                                                        //~1A4sI~
        {                                                          //~1A4sI~
            if (AG.osVersion>=AG.HONEYCOMB)  //android3            //~1A4sR~
                text=getContents_V11();                            //~1A4sR~
            else                                                   //~1A4sR~
                text=getContents_deprecated();                     //~1A4sR~
        }                                                          //~1A4sI~
        catch(Exception e)                                         //~1A4sI~
        {                                                          //~1A4sI~
			Dump.println(e,"Utils:clipboard_getText");             //~1A4sI~
            AView.showToast(R.string.ErrClipboardGetTextFailed);   //~1A4sI~
            text=null;                                             //~1A4sR~
        }                                                          //~1A4sI~
		if (Dump.Y) Dump.println("Utils:clipboard_getText="+text); //~1A4sI~
    	return text;                                               //~1A4sI~
	}                                                              //~1A4sI~
//***********************************************************      //~1A4sI~
	public static void clipboard_setText(String Pstr)              //~1A4sI~
	{                                                              //~1A4sI~
		if (Dump.Y) Dump.println("Utils:clipboard_setText="+Pstr); //~1A4sI~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~1A4sI~
            setContents_V11(Pstr);                                 //~1A4sI~
        else                                                       //~1A4sI~
            setContents_deprecated(Pstr);                          //~1A4sI~
	}                                                              //~1A4sI~
//********                                                         //~1A4sI~
    private static Object getcm()                                  //~1A4sI~
    {                                                              //~1A4sI~
    	Object cm;                                                 //~1A4sI~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~1A4sI~
			cm=getcm_V11();                                        //~1A4sI~
        else                                                       //~1A4sI~
			cm=getcm_deprecated();                                 //~1A4sI~
        return cm;                                                 //~1A4sI~
    }                                                              //~1A4sI~
    //*******************************************************************//~1A4sI~
    @SuppressWarnings("deprecation")                               //~1A4sI~
    private static Object getcm_deprecated()                       //~1A4sI~
    {                                                              //~1A4sI~
		android.text.ClipboardManager cm=(android.text.ClipboardManager)AG.context.getSystemService(Context.CLIPBOARD_SERVICE);//~1A4sI~
        return cm;                                                 //~1A4sI~
    }                                                              //~1A4sI~
	//********                                                     //~1A4sI~
	@TargetApi(AG.HONEYCOMB)                                       //~1A4sI~
    private static Object getcm_V11()                              //~1A4sI~
    {                                                              //~1A4sI~
    	ClipboardManager cm=(ClipboardManager)AG.context.getSystemService(Context.CLIPBOARD_SERVICE);//~1A4sI~
        return cm;                                                 //~1A4sI~
    }                                                              //~1A4sI~
    //*******************************************************************//~1A4sI~
    @SuppressWarnings("deprecation")                               //~1A4sI~
	private static String getContents_deprecated()                 //~1A4sI~
	{                                                              //~1A4sI~
        String str;                                                //~1A4sI~
		android.text.ClipboardManager cm=(android.text.ClipboardManager)getcm();//~1A4sI~
        if (!cm.hasText())                                         //~1A4sI~
        {                                                          //~1A4sI~
            str=null;                                              //~1A4sI~
        	if (Dump.Y) Dump.println("Clipboard deprecated getText:null");//~1A4sI~
        }                                                          //~1A4sI~
        else                                                       //~1A4sI~
        {                                                          //~1A4sI~
            str=cm.getText().toString();                           //~1A4sI~
        	if (Dump.Y) Dump.println("Clipboard deprecated getText:"+str);//~1A4sI~
        }                                                          //~1A4sI~
        return str;                                                //~1A4sI~
	}                                                              //~1A4sI~
    //*******                                                      //~1A4sI~
	@TargetApi(AG.HONEYCOMB)                                       //~1A4sI~
	private static String getContents_V11()                        //~1A4sI~
	{                                                              //~1A4sI~
    	String str;                                                //~1A4sI~
    	ClipData.Item item;                                        //~1A4sI~
		ClipboardManager cm=(ClipboardManager)getcm();             //~1A4sI~
        if (cm.hasPrimaryClip())                                   //~1A4sI~
        {                                                          //~1A4sI~
    		item=cm.getPrimaryClip().getItemAt(0);                 //~1A4sI~
//          str=(String)item.getText().toString();                 //~1A4xR~
            str=item.getText().toString();                         //~1A4xI~
	        if (Dump.Y) Dump.println("Clipboard getTextv11:"+str); //~1A4sI~
        }                                                          //~1A4sI~
        else                                                       //~1A4sI~
        {                                                          //~1A4sI~
        	str=null;                                              //~1A4sI~
	        if (Dump.Y) Dump.println("Clipboard getTextv11:null"); //~1A4sI~
        }                                                          //~1A4sI~
		return str;                                                //~1A4sI~
	}                                                              //~1A4sI~
    //*******************************************************************//~1A4sI~
    @SuppressWarnings("deprecation")                               //~1A4sI~
	private static void setContents_deprecated(String Pstr)        //~1A4sI~
    {                                                              //~1A4sI~
		android.text.ClipboardManager cm=(android.text.ClipboardManager)getcm();//~1A4sI~
		cm.setText(Pstr);                                          //~1A4sI~
	    if (Dump.Y) Dump.println("Clipboard setText deprecated:"+Pstr);//~1A4sI~
    }                                                              //~1A4sI~
    //*********                                                    //~1A4sI~
	@TargetApi(AG.HONEYCOMB)                                       //~1A4sI~
	private static void setContents_V11(String Pstr)               //~1A4sI~
	{                                                              //~1A4sI~
		ClipboardManager cm=(ClipboardManager)getcm();             //~1A4sI~
    	ClipData.Item item=new ClipData.Item(Pstr);                //~1A4sI~
        String[] mymetype=new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN};//~1A4sI~
        ClipData cd=new ClipData("data",mymetype,item);            //~1A4sI~
		cm.setPrimaryClip(cd);                                     //~1A4sI~
	    if (Dump.Y) Dump.println("Clipboard setTextv11:"+Pstr);    //~1A4sI~
	}                                                              //~1A4sI~
//***********************************************************      //~1A4AI~
	public static void showWebSite(String Purl)                    //~1A4AI~
	{                                                              //~1A4AI~
		if (Dump.Y) Dump.println("Utils:showWebSite url="+Purl);   //~1A4AI~
        Intent intent=new Intent(Intent.ACTION_VIEW);              //~1A4AI~
        intent.setData(Uri.parse(Purl));                           //~1A4AI~
        AG.activity.startActivity(intent);                         //~1A4AI~
	}                                                              //~1A4AI~
//***********************************************************************//~1A67I~//~1A6aI~
    public static String getMacString(byte[] Pbytemacaddr)                //~1A67R~//~1A6aI~
    {                                                              //~1A67R~//~1A6aI~
        if (Pbytemacaddr==null)                                    //~1A6aI~
            return "";                                             //~1A6aI~
        StringBuilder sb=new StringBuilder("");                      //~1A67R~//~1A6aI~
        int sz=Pbytemacaddr.length;                                //~1A67R~//~1A6aI~
        for (int ii=0;ii<sz;ii++)                                  //~1A67R~//~1A6aI~
        {                                                          //~1A67R~//~1A6aI~
            sb.append(String.format("%s%02x",((ii==0) ? "" : ":"),Pbytemacaddr[ii]));//~1A67R~//~1A6aI~
        }                                                          //~1A67R~//~1A6aI~
        return new String(sb);                                     //~1A67R~//~1A6aI~
    }                                                              //~1A67R~//~1A6aI~
//***********************************************************************//~1A6aI~
	public static String getRemoteIPAddr(Socket Psocket,String Pnullopt)                       //~@@@2I~//~1A8bI~
    {                                                              //~@@@2I~//~1A8bI~
    	String ipa=null;                                           //~1A8bI~
        InetAddress ia=Psocket.getInetAddress();                   //~@@@2I~//~1A8bI~
        if (ia!=null)                                              //~@@@2M~//~1A8bI~
        {                                                          //~@@@2M~//~1A8bI~
	        ipa=ia.getHostAddress();              //~@@@2R~        //~1A8bI~
	        if (Dump.Y) Dump.println("AjagoUtils:getRemoteIPAddr="+ipa+",name="+ia.getHostName());//~@@@2I~//~1A8bI~
        }                                                          //~@@@2M~//~1A8bI~
        if (ipa==null)                                             //~1A8bI~
        {                                                          //~1A8bI~
        	ipa=Pnullopt;                                          //~1A8bI~
        }                                                          //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~@@@2I~//~1A8bI~
//***********************************************************************//~1A8bI~
	public static String getLocalIPAddr(Socket Psocket,String Pnullopt)//~1A8bI~
    {                                                              //~1A8bI~
    	String ipa=null;                                           //~1A8bI~
        InetAddress ia=Psocket.getLocalAddress();                  //~1A8bI~
        if (ia!=null)                                              //~1A6sI~//~1A8bI~
        {                                                          //~1A6sI~//~1A8bI~
	        ipa=ia.getHostAddress();               //~1A6sI~       //~1A8bI~
	        if (Dump.Y) Dump.println("AjagoUtils:getLocalIPAddr="+ipa+",name="+ia.getHostName());//~1A8bI~
        }                                                          //~1A6sI~//~1A8bI~
        if (ipa==null)                                             //~1A8bI~
        {                                                          //~1A8bI~
        	ipa=Pnullopt;                                          //~1A8bI~
        }                                                          //~1A8bI~
        return ipa;                                                //~1A8bI~
    }                                                              //~1A8bI~
//***********************************************************************//~1Ac0I~
    public static void chkNetwork()                                //~1Ac0I~
    {                                                              //~1Ac0I~
        ConnectivityManager cm=getCM();                              //~1Ac0I~
        NetworkInfo[] infos=cm.getAllNetworkInfo();                //~1Ac0I~
        if (Dump.Y) Dump.println("Utils:chkNetwork ctr="+infos.length);//+1Ac0I~
        for (NetworkInfo ni:infos)                               //~1Ac0I~
        {                                                          //~1Ac0I~
        	String typename=ni.getTypeName();                      //~1Ac0I~
        	String subtypename=ni.getSubtypeName();                //~1Ac0I~
        	boolean connected=ni.isConnected();                    //~1Ac0I~
            if (Dump.Y) Dump.println("Utils:chkNetwork :type="+typename+",subtype="+subtypename+",connected="+connected+",tostring="+ni.toString());//~1Ac0R~
        }                                                          //~1Ac0I~
    }                                                              //~1Ac0I~
//***********************************************************************//~1Ac0I~
    public static ConnectivityManager getCM()                      //~1Ac0I~
    {                                                              //~1Ac0I~
        return (ConnectivityManager)AG.context.getSystemService(Context.CONNECTIVITY_SERVICE);//~1Ac0I~
    }                                                              //~1Ac0I~
}//class AjagoUtils                                                //~1309R~
