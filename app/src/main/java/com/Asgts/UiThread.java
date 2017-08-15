//*CID://+1Ah0R~: update#= 129;                                    //+1Ah0R~
//**********************************************************************//~v106I~
//1Ah0 2016/10/15 v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//+1Ah0I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//~1A6AI~
//1A6g 2015/02/14 UiThread support on not Main activity(old UiThared use AG.activity, so finsh() destroy main activity)//~1A6gI~//~@@@@I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//**********************************************************************//~1107I~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Asgts;                                         //~1107R~  //~1108R~//~1109R~//~@@@@R~

import jagoclient.Dump;

import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.os.Handler;

//**********************************************************************//~1107I~
public class UiThread                                         //~1214R~//~@@@@R~
{                                                                  //~0914I~
	public static final int LID_GMP=0;                             //~v106I~
	private static	CountDownLatch[] Slatch=new CountDownLatch[1]; //~v106I~
                                                                   //~1211I~
	private UiThreadI callback=null;                           //~1214R~//~@@@@R~
	private Object parm=null;                                      //~1214R~
    
    
//**********************************                               //~1211I~
//*                                                                //~1211R~
//**********************************                               //~1211I~
	public UiThread(UiThreadI Pcallback,Object Pparm)    //~1214R~ //~@@@@R~
    {                                                              //~1214I~
    	callback=Pcallback;                                        //~1214I~
        parm=Pparm;                                                //~1214R~
    }                                                              //~1214I~
//===============================================================================//~1309I~
//=on UI Thread;default NoSync                                     //~1309I~
//===============================================================================//~1309I~
    public static void runOnUiThread(UiThreadI Pcallback,Object Pparm)//~1309I~//~@@@@R~
    {                                                              //~1309I~
		runOnUiThreadNoSync(Pcallback,Pparm); //~1309I~
    }                                                              //~1309I~
//===============================================================================//~1310I~
//=on UI Thread;with wait parameter                                //~1310I~
//===============================================================================//~1310I~
    public static void runOnUiThread(boolean Pwait,UiThreadI Pcallback,Object Pparm)//~1310I~//~@@@@R~
    {                                                              //~1310I~
    	if (Pwait)                                                 //~1310I~
			runOnUiThreadSync(Pcallback,Pparm);                    //~1310I~
        else                                                       //~1310I~
			runOnUiThreadNoSync(Pcallback,Pparm);                  //~1310I~
    }                                                              //~1310I~
//===============================================================================//~1309I~
//=on UI Thread;wait postback                                      //~1309I~
//===============================================================================//~1309I~
    public static void runOnUiThreadWait(UiThreadI Pcallback,Object Pparm)//~1309I~//~@@@@R~
    {                                                              //~1309I~
		runOnUiThreadSync(Pcallback,Pparm);   //~1309I~
    }                                                              //~1309I~
//===============================================================================//~1309I~
//=on UI Thread;no wait postback                                   //~1309I~
//===============================================================================//~1309I~
    public static void runOnUiThreadXfer(UiThreadI Pcallback,Object Pparm)//~1309I~//~@@@@R~
    {                                                              //~1309I~
		runOnUiThreadNoSync(Pcallback,Pparm);                      //~1309I~
    }                                                              //~1309I~
//===============================================================================//~1A6gI~//~@@@@I~
    public static void runOnUiThreadXfer(Activity Pactivity,UiThreadI Pcallback,Object Pparm)//~1A6gI~//~@@@@I~
    {                                                              //~1A6gI~//~@@@@I~
		runOnUiThreadNoSync(Pactivity,Pcallback,Pparm);            //~1A6gI~//~@@@@I~
    }                                                              //~1A6gI~//~@@@@I~
//===============================================================================//~v@@@I~//~1212I~//~1214M~
//=no wait UI Thread end                                           //~1220I~
//===============================================================================//~1220I~
    private static void runOnUiThreadNoSync(UiThreadI Pcallback,Object Pparm)//~1214R~//~1221R~//~1309R~//~@@@@R~
    {                                                              //~1214I~
    	UiThread aut=new UiThread(Pcallback,Pparm);//~1214R~       //~@@@@R~
    	if (AG.isMainThread())                                     //~1214M~
        {                                                          //~1214M~
        	try                                                    //~1512I~
            {                                                      //~1512I~
            	if (Dump.T) Dump.println("runOnUiThreadNoSync mainthread before callback");//~@@@@I~//~1A6AR~
        		Pcallback.runOnUiThread(aut.parm);                 //~1512R~
            	if (Dump.T) Dump.println("runOnUiThreadNoSync mainthread after callback");//~@@@@I~//~1A6AR~
            }                                                      //~1512I~
            catch (Exception e)                                    //~1512I~
            {                                                      //~1512I~
                Dump.println(e,"runOnUiThreadNoSync(Mainthread) Exception");//~1512I~
            }                                                      //~1512I~
            return;                                                //~1214M~
        }                                                          //~1214M~
    	aut.runOnUiThreadNoSync2(aut);                  //~1214I~
    }                                                              //~1214I~
//===============================================================================//~1A6gI~//~@@@@I~
    private static void runOnUiThreadNoSync(Activity Pactivity,UiThreadI Pcallback,Object Pparm)//~1A6gI~//~@@@@I~
    {                                                              //~1A6gI~//~@@@@I~
    	UiThread aut=new UiThread(Pcallback,Pparm);                //~1A6gI~//~@@@@I~
    	if (AG.isMainThread())                                     //~1A6gI~//~@@@@I~
        {                                                          //~1A6gI~//~@@@@I~
        	try                                                    //~1A6gI~//~@@@@I~
            {                                                      //~1A6gI~//~@@@@I~
            	if (Dump.T) Dump.println("runOnUiThreadNoSync mainthread before callback");//~1A6gI~//~@@@@I~//~1A6AR~
        		Pcallback.runOnUiThread(aut.parm);                 //~1A6gI~//~@@@@I~
            	if (Dump.T) Dump.println("runOnUiThreadNoSync mainthread after callback");//~1A6gI~//~@@@@I~//~1A6AR~
            }                                                      //~1A6gI~//~@@@@I~
            catch (Exception e)                                    //~1A6gI~//~@@@@I~
            {                                                      //~1A6gI~//~@@@@I~
                Dump.println(e,"runOnUiThreadNoSync(Mainthread) Exception");//~1A6gI~//~@@@@I~
            }                                                      //~1A6gI~//~@@@@I~
            return;                                                //~1A6gI~//~@@@@I~
        }                                                          //~1A6gI~//~@@@@I~
    	aut.runOnUiThreadNoSync2(Pactivity,aut);                   //~1A6gI~//~@@@@I~
    }                                                              //~1A6gI~//~@@@@I~
//===============================================================================//~v@@@I~//~1212I~
    private void runOnUiThreadNoSync2(UiThread Paut)//~1212I~//~1214R~//~1221R~//~@@@@R~
    {                                                              //~1212I~
        final UiThread aut=Paut;                                    //~1214I~//~@@@@R~
        AG.activity.runOnUiThread(              //execute on mainthread after posted//~1214I~
            new Runnable()                                         //~1214I~
            {                                                      //~1214I~
                @Override                                          //~1214I~
                public void run()                                  //~1214I~
                {                                                  //~1214I~
			        if (Dump.T) Dump.println("RunOnUiThreadNoSync2 start");           //~1214I~//~1308R~//~1506R~//~1A6AR~
                    if (aut.callback!=null)                        //~1214R~
                    {                                              //~1214I~
			        	if (Dump.T) Dump.println("RunOnUiThreadNoSync2 parm="+(aut.parm==null?"null":aut.parm.toString()));//~1506R~//~1A6AR~
        				try                                        //~1311I~
        				{                                          //~1311I~
	                    	aut.callback.runOnUiThread(aut.parm);      //~1214R~//~1311R~
                        }                                          //~1311I~
                        catch (Exception e)             //~1311I~
                        {                                          //~1311I~
                            Dump.println(e,"runOnUiThreadNoSync2 Exception");//~1311I~
                        }                                          //~1311I~
                    }                                              //~1214I~
			        if (Dump.T) Dump.println("RunOnUiThreadNoSync2 end");//~1506R~//~1A6AR~
                }                                                  //~1214I~
            }                                                      //~1214I~
                                  );                               //~1214I~
    }                                                              //~1212I~
//===============================================================================//~1A6gI~//~@@@@I~
    private void runOnUiThreadNoSync2(final Activity Pactivity,UiThread Paut)//~1A6gI~//~@@@@I~
    {                                                              //~1A6gI~//~@@@@I~
        final UiThread aut=Paut;                                   //~1A6gI~//~@@@@I~
//      AG.activity.runOnUiThread(              //execute on mainthread after posted//~1A6gI~//~@@@@I~
        Pactivity.runOnUiThread(              //execute on mainthread after posted//~1A6gI~//~@@@@I~
            new Runnable()                                         //~1A6gI~//~@@@@I~
            {                                                      //~1A6gI~//~@@@@I~
                @Override                                          //~1A6gI~//~@@@@I~
                public void run()                                  //~1A6gI~//~@@@@I~
                {                                                  //~1A6gI~//~@@@@I~
			        if (Dump.T) Dump.println("RunOnUiThreadNoSync2  start activity="+Pactivity.toString());//~1A6gI~//~@@@@I~//~1A6AR~
                    if (aut.callback!=null)                        //~1A6gI~//~@@@@I~
                    {                                              //~1A6gI~//~@@@@I~
			        	if (Dump.T) Dump.println("RunOnUiThreadNoSync2 parm="+(aut.parm==null?"null":aut.parm.toString()));//~1A6gI~//~@@@@I~//~1A6AR~
        				try                                        //~1A6gI~//~@@@@I~
        				{                                          //~1A6gI~//~@@@@I~
	                    	aut.callback.runOnUiThread(aut.parm);  //~1A6gI~//~@@@@I~
                        }                                          //~1A6gI~//~@@@@I~
                        catch (Exception e)                        //~1A6gI~//~@@@@I~
                        {                                          //~1A6gI~//~@@@@I~
                            Dump.println(e,"runOnUiThreadNoSync2 Exception");//~1A6gI~//~@@@@I~
                        }                                          //~1A6gI~//~@@@@I~
                    }                                              //~1A6gI~//~@@@@I~
			        if (Dump.T) Dump.println("RunOnUiThreadNoSync2 end");//~1A6gI~//~@@@@I~//~1A6AR~
                }                                                  //~1A6gI~//~@@@@I~
            }                                                      //~1A6gI~//~@@@@I~
                                  );                               //~1A6gI~//~@@@@I~
    }                                                              //~1A6gI~//~@@@@I~
//===============================================================================//~1214I~
//= wait UI Thread end                                             //~1220I~
//===============================================================================//~1220I~
    private static void runOnUiThreadSync(UiThreadI Pcallback,Object Pparm)//~1214I~//~1309R~//~@@@@R~
    {                                                              //~1214I~
    	UiThread aut=new UiThread(Pcallback,Pparm);//~1214I~       //~@@@@R~
		if (Dump.T) Dump.println("RunOnUiThreadSync parm="+(Pparm==null?"null":Pparm.toString()));//~1506R~//~1A6AR~
    	if (AG.isMainThread())                                     //~1214I~
        {                                                          //~1214I~
        	try                                                    //~1512I~
            {                                                      //~1512I~
            	if (Dump.T) Dump.println("runOnUiThreadSync mainthread before callback");//~@@@@I~//~1A6AR~
	        	Pcallback.runOnUiThread(aut.parm);                 //~1512R~
            	if (Dump.T) Dump.println("runOnUiThreadSync mainthread after callback");//~@@@@I~//~1A6AR~
            }                                                      //~1512I~
            catch (Exception e)                                    //~1512I~
            {                                                      //~1512I~
                Dump.println(e,"runOnUiThreadSync(Mainthread) Exception");//~1512I~
            }                                                      //~1512I~
			if (Dump.T) Dump.println("RunOnUiThreadSync mainthread return parm="+(Pparm==null?"null":Pparm.toString()));//~@@@@I~//~1A6AR~
            return;                                                //~1214I~
        }                                                          //~1214I~
    	aut.runOnUiThreadSync2();                         //~1214I~
    }                                                              //~1214I~
//===============================================================================//~1214I~
    private void runOnUiThreadSync2()            //~1214I~
    {                                                              //~1214I~
        if (Dump.T) Dump.println("AjagoUIThread:runOnUiThreadSync2");              //~1214I~//~1506R~//~1A6AR~
 //create handler is aval on Main thread only                      //~1214R~
//  	Handler handler=new Handler();                             //~1214I~
		CountDownLatch latch=new CountDownLatch(1/*posted by counddown once*/);
        runOnUiThreadSub sub=new runOnUiThreadSub();                                    //~1214R~
        sub.init(this,latch);                                      //~1214I~
        try                                                        //~1214I~
        {                                                          //~1214I~
	        AG.activity.runOnUiThread(sub);                        //~1513I~
	        if (Dump.T) Dump.println("UiThread  wait");            //~@@@@I~//~1A6AR~
        	latch.await();   //subthread wakeup by dismiss         //~1214I~
	        if (Dump.T) Dump.println("UiThread posted wait");                     //~1214I~//~1506R~//~@@@@R~//~1A6AR~
        }                                                          //~1214I~
        catch (InterruptedException e)                             //~1214I~
        {                                                          //~1214I~
        	Dump.println(e,"Modal Thread Interrupted ");//~1214I~//~1308R~//~1311R~
        }                                                          //~1214I~
        if (Dump.T) Dump.println("AjagoUIThread:runOnUiThreadSync2 return");//~@@@@I~//~1A6AR~
    }                                                              //~1214I~
//*******************************                                  //~1214I~
// wait/pos by latch                                               //~v106I~
//===============================================================================//~v106I~
    public static void initlatch(int Platchid)                     //~v106I~
    {                                                              //~v106I~
		Slatch[Platchid]=new CountDownLatch(1/*posted by counddown once*/);//~v106I~
    }                                                              //~v106I~
    public static void wait(int Platchid)                          //~v106I~
    {                                                              //~v106I~
		if (Dump.T) Dump.println("UiThread Wait id="+Platchid);//~v106I~//~@@@@R~//~1A6AR~
    	if (AG.isMainThread())                                     //~v106I~
        	return;    //not supported                             //~v106I~
        try                                                        //~v106I~
        {                                                          //~v106I~
	        if (Dump.T) Dump.println("UiThread await");            //~@@@@I~//~1A6AR~
        	Slatch[Platchid].await();   //subthread wakeup by dismiss//~v106I~
	        if (Dump.T) Dump.println("UiThread await:posted"); //~v106I~//~@@@@R~//~1A6AR~
			Slatch[Platchid]=null;                                 //~v106I~
        }                                                          //~v106I~
        catch (InterruptedException e)                             //~v106I~
        {                                                          //~v106I~
        	Dump.println(e,"wait Thread Interrupted ");            //~v106I~
        }                                                          //~v106I~
    }                                                              //~v106I~
    public static void post(int Platchid)                          //~v106I~
    {                                                              //~v106I~
		if (Dump.T) Dump.println("UiThread post id="+Platchid);//~v106I~//~@@@@R~//~1A6AR~
        Slatch[Platchid].countDown();  //count exausted,post latch.await()//~v106I~
	    if (Dump.T) Dump.println("UiThread post");            //~v106I~//~@@@@R~//~1A6AR~
    }                                                              //~v106I~
//*******************************                                  //~1214I~
    class runOnUiThreadSub                                  //~1214I~
			implements Runnable                                    //~1214I~
    {                                                              //~1214I~
    	private UiThread aut;                                      //~@@@@R~
    	CountDownLatch latch;
    	public runOnUiThreadSub()                                  //~1214R~
        {                                                          //~1214I~
        }                                                          //~1214I~
    	public void init(UiThread Paut,CountDownLatch Platch) //~1214I~//~@@@@R~
        {                                                          //~1214I~
        	aut=Paut;                                              //~1214I~
        	latch=Platch;                                          //~1214I~
        }                                                          //~1214I~
        @Override                                                  //~1214I~
        public void run()                                          //~1214I~
        {                                                          //~1214I~
			if (Dump.T) Dump.println("before runOnUiTHreadSub");         //~1214I~//~1506R~//~1A6AR~
        	try                                                    //~1311I~
        	{                                                      //~1311I~
		        if (Dump.T) Dump.println("UiThreadSub before callback");//~@@@@I~//~1A6AR~
            	aut.callback.runOnUiThread(aut.parm);                  //~1214I~//~1311R~
		        if (Dump.T) Dump.println("UiThreadSub after callback");//~@@@@I~//~1A6AR~
            }                                                      //~1311I~
        	catch (Exception e)                         //~1311I~
        	{                                                      //~1311I~
        		Dump.println(e,"runOnUiThreadcSub(Sync) Exception");//~1311I~
        	}                                                      //~1311I~
			if (Dump.T) Dump.println("runOnUiThreadSub contdown"); //~@@@@R~//~1A6AR~
            latch.countDown();  //count exausted,post latch.await()//~1214I~
			if (Dump.T) Dump.println("runOnUiThreadSub return");   //~@@@@I~//~1A6AR~
        }                                                          //~1214I~
    }                                                              //~1214I~
//**************************************************************************//~v1B6I~//+1Ah0I~
//*schedule Runnable on MainThread                                 //~v1B6I~//+1Ah0I~
//**************************************************************************//~v1B6I~//+1Ah0I~
	public static void invokeLater(Runnable Pcallback)             //~v1B6M~//+1Ah0I~
    {                                                              //~v1B6M~//+1Ah0I~
    	Handler main=new Handler(AG.context.getMainLooper());      //~v1B6M~//+1Ah0I~
        main.post(Pcallback);                                      //~v1B6M~//+1Ah0I~
    }                                                              //~v1B6M~//+1Ah0I~
}//class UiThread                                             //~1214R~//~@@@@R~
