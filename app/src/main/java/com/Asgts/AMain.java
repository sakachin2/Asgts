//*CID://+1AbkR~:                             update#=   81;       //+1AbkR~
//**********************************************************************//~v107I~
//1Abk 2015/06/16 NFC:errmsg when intent is for other side of NFC/NFCBT//+1AbkI~
//1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//1Ab7 2015/05/03 NFC Bluetooth handover v2                        //~1Ab7I~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6j 2015/02/14 set dummy NFC function for main Activity to avoid current activity re-created.//~1A6bI~//~1A6jI~
//1A6b 2015/02/13 (BUG) BTDiscovery intent receiver was not unregister.(Following msg on logcat)//~1A6bI~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6aI~
//1A65 2015/01/29 implement Wifi-Direct function(>=Api14:android4.0)//~1A65I~//~1A43I~
//1A43 2014/10/03 actionBar as alternative of menu button for api>=11//~1A43R~
//                When requestWindowFeature(FEATURE_LEFT_ICON). onCreateOptionsMenu is not called(No ActionBar on android>=3.0)//~1A43R~
//1A08 2013/03/02 add sound "gameover" (chk also sdcard)           //~1A08I~
//1078:121208 add "menu" to option menu if landscape               //~v107R~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//**********************************************************************//~v107I~
package com.Asgts;                                                  //~v107R~//~@@@@R~

import wifidirect.DialogNFC;
import wifidirect.DialogNFCSelect;
import wifidirect.WDA;
import wifidirect.WDANFC;

import com.Asgts.awt.Frame;                                        //~@@@@R~
import com.Asgts.rene.util.sound.SoundList;

import jagoclient.Dump;
import jagoclient.board.TimedGoFrame;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;                                          //~1109I~
                                                                   //~1109I~
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;                                      //~1109I~
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
                                                                   //~1109I~
//********************************************************************//~1212I~
//* Android Jago Client  EntryPoint **********************************//~1212I~
//********************************************************************//~1212I~
public class AMain                                                //~1122R~//~v107R~//~@@@@R~
//                    extends TabActivity                          //~@@@@R~
                      extends Activity                             //~@@@@I~
//                    implements OnTabChangeListener               //~@@@@I~
//                               ,Callback                         //~@@@@I~
                      implements Callback//~1122I~                 //~@@@@R~
                      , URunnableI	//at stop APP                  //~@@@@I~
//android 4					, CreateNdefMessageCallback, OnNdefPushCompleteCallback//~1A6jR~
{                                                                  //~1109I~
//    private static boolean destroying=false;                              //~1218I~//~@@@@R~
//*************************                                        //~1109I~
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)                //~1329R~
    {                                                              //~1329I~
        try                                                        //~1329I~
        {                                                          //~1329I~
            super.onCreate(savedInstanceState);                    //~1329R~
//          requestWindowFeature(Window.FEATURE_LEFT_ICON);             //~0914R~//~0915R~//~0A09R~//~1A43R~
            AG.init(this);                                         //~1402I~
            if (AG.osVersion<AG.HONEYCOMB)  //<android3=api-11     //~1A43R~
            	requestWindowFeature(Window.FEATURE_LEFT_ICON);    //~1A43R~
                                                                   //~1329I~
            Dump.openEx("dump.dat");	//write exception only     //~1504R~//~@@@@R~
//          Dump.open("dump.dat");	//write exception only         //~@@@@R~
	        if(Dump.Y) Dump.println("onCreate");                   //~@@@@I~
                                                                   //~1329I~
            AG.aView=new AView();                             //~1329R~//~v107R~//~@@@@R~
            AG.aMenu=new AMenu();                              //~1125I~//~1329R~//~v107R~//~@@@@R~
            AG.aBT=ABT.createABT();                      //~v107R~ //~@@@@R~
        }                                                          //~1329I~
        catch(Exception e)                                         //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"onCreate");                            //~1329I~
        }                                                          //~1329I~
    }
//*************************                                        //~@@@@I~
    @Override                                                      //~@@@@I~
    public void onStart()                                          //~@@@@I~
    {                                                              //~@@@@I~
        super.onStart();                                           //~@@@@I~
        if(Dump.Y) Dump.println("onStart");                        //~@@@@I~
    }                                                              //~@@@@I~
//*************************                                        //~@@@@I~
//    @Override            API11                                   //~@@@@R~
//    public void onCreateView(View Pparent,String Pname,Context Pcontext,AttributeSet Pattr)//~@@@@R~
//    {                                                            //~@@@@R~
//        if(Dump.Y) Dump.println("onCreateView name="+Pname);     //~@@@@R~
//        AView.getTitleBarHeight();                               //~@@@@R~
//    }                                                            //~@@@@R~
//*************************                                        //~@@@@I~
    @Override                                                      //~v107I~
    public synchronized void onResume() {                          //~v107I~
        super.onResume();                                          //~v107I~
      try                                                          //~1A65R~
      {                                                            //~1A65R~
        if(Dump.Y) Dump.println("+ ON RESUME +");                  //~v107I~
        // Performing this check in onResume() covers the case in which BT was//~v107I~
        // not enabled during onStart(), so we were paused to enable it...//~v107I~
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.//~v107I~
        if (AG.aBT!=null)                                       //~v107R~//~@@@@R~
			AG.aBT.resume();                                    //~v107R~//~@@@@R~
        if (WDA.SWDA!=null)                                        //~1A65R~
			WDA.SWDA.onResume();                                   //~1A65R~
//       if (AG.swNFCBT)                                             //~1Ab7I~//~1Ab8R~
//        DialogNFCSelect.onResume();                                //~1Ab7I~//~1Ab8R~
//       else                                                        //~1Ab7I~//~1Ab8R~
		WDANFC.onResume();                                         //~1A6bI~
      }                                                            //~1A65R~
      catch(Exception e)                                           //~1A65R~
      {                                                            //~1A65R~
      	Dump.println(e,"AMain:onResume");                          //~1A65R~
      }                                                            //~1A65R~
    }                                                              //~v107I~
//*************************                                        //~1A65R~
    @Override                                                      //~1A65R~
    public synchronized void onPause() {                           //~1A65R~
        super.onPause();                                           //~1A65R~
    	try                                                        //~1A65R~
        {                                                          //~1A65R~
        	if(Dump.Y) Dump.println("+ ON PAUSE +");               //~1A65R~
        	if (WDA.SWDA!=null)                                    //~1A65R~
				WDA.SWDA.onPause();                                //~1A65R~
//           if (AG.swNFCBT)                                         //~1Ab7I~//~1Ab8R~
//             DialogNFCSelect.onPause();                            //~1Ab7I~//~1Ab8R~
//           else                                                    //~1Ab7I~//~1Ab8R~
			WDANFC.onPause();                                      //~1A6bR~
        }                                                          //~1A65R~
        catch(Exception e)                                         //~1A65R~
        {                                                          //~1A65R~
        	Dump.println(e,"AMain:onPause");                       //~1A65R~
        }                                                          //~1A65R~
    }                                                              //~1A65R~
//*************************                                        //~1413I~
    @Override                                                      //~1413I~
    public void onDestroy()                                        //~1413I~
	{                                                              //~1413I~
        super.onDestroy();                                         //~1413I~
        if (Dump.Y) Dump.println("OnDestroy");                     //~@@@@I~
////      destroying=true;                                           //~1413I~//~@@@@R~
//        try                                                        //~1513I~//~@@@@R~
//        {                                                          //~1513I~//~@@@@R~
//            if (Dump.Y) Dump.println("OnDestroy");                 //~1513R~//~@@@@R~
//            if (Utils_stopFinish)                                //~@@@@I~
//                destroyClose(); //close socket,timer,Board       //~@@@@R~
////            if (Go.F!=null) //MainFrame instance                   //~1513R~//~@@@@R~
////                Go.F.doclose(); //write option parameter then exit,stop canbas boardsync thread//~@@@@R~
//        }                                                          //~1513I~//~@@@@R~
//        catch(Exception e)                                         //~1513I~//~@@@@R~
//        {                                                          //~1513I~//~@@@@R~
//            Dump.println(e,"onDestroy");                           //~1513I~//~@@@@R~
//        }                                                          //~1513I~//~@@@@R~
////        Dump.close();                                              //~1413I~//~@@@@R~
        try                                                        //~1A6bI~
        {                                                          //~1A6bI~
			AG.aBT.onDestroy();                                    //~1A6bI~
		SoundList.release();	//release MediaPlayer              //~1A08I~
        }                                                          //~1A6bI~
        catch(Exception e)                                         //~1A6bI~
        {                                                          //~1A6bI~
            Dump.println(e,"onDestroy");                           //~1A6bI~
        }                                                          //~1A6bI~
		if (Dump.Y) Dump.println("OnDestoy status="+AG.status);    //~@@@@I~
        if (AG.status==AG.STATUS_STOPFINISH)                       //~@@@@I~
        	Utils.exit(0,true); //System.exit() to kill myself to clear static variable//~1413I~//~@@@@R~
    }                                                              //~1413I~
//*************************                                        //~1120I~
    @Override                                                      //~1120I~
    public void onConfigurationChanged(Configuration Pcfg)         //~1120I~
	{                                                              //~1120I~
        super.onConfigurationChanged(Pcfg);                        //~1120I~//~1413R~
    	try                                                        //~1513I~
        {                                                          //~1513I~
            if (Dump.Y) Dump.println("Ajoagc configuration changed");        //~1120I~//~1513R~
            AG.aView.getScreenSize();                             //~1513R~//~v107R~//~@@@@R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onConfiurationChanged");               //~1513I~
        }                                                          //~1513I~
	}                                                              //~1120I~
//**ContextMenu***********************                             //~1121I~
    @Override                                                      //~1121I~
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenuInfo info)//~1121R~
    {                                                              //~1121I~
	    super.onCreateContextMenu(menu,view,info);                 //~1121I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
	        AG.aMenu.onCreateContextMenu(menu,view,info);      //~1513R~//~v107R~//~@@@@R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onCreteContextMenu");                  //~1513I~
        }                                                          //~1513I~
    }                                                              //~1121I~
//**************                                                   //~1307I~
    @Override                                                      //~1307I~
    public void onContextMenuClosed(Menu Pmenu)                    //~1307I~
    {                                                              //~1307I~
	    super.onContextMenuClosed(Pmenu);                          //~1307I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
	        AG.aMenu.onContextMenuClosed(Pmenu);               //~1513R~//~v107R~//~@@@@R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onContextMenuClosed");                 //~1513I~
        }                                                          //~1513I~
    }                                                              //~1307I~
//*************************                                      //~1121I~//~1128R~
    @Override                                                      //~1121I~
    public boolean onContextItemSelected(MenuItem item)            //~1121I~
    {                                                              //~1121I~
        boolean rc=false;                                                //~1513I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
            rc=AG.aMenu.onContextItemSelected(item);           //~1513R~//~v107R~//~@@@@R~
            if (!rc)    //not processed                            //~1513R~
                rc=super.onContextItemSelected(item);              //~1513R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onContextItemChanged");                //~1513I~
        }                                                          //~1513I~
        return rc;                                                 //~1121I~
    }                                                              //~1121I~
//*********************************************************        //~1128I~//~@@@@R~
//*titlebar height will be gotten at this time            *        //~@@@@R~
//*********************************************************        //~@@@@I~
    @Override
    public void onWindowFocusChanged(boolean Phasfocus)
    {
        if (Dump.Y) Dump.println("WindowFocusChanged "+Phasfocus);//~1513R~//~@@@@I~
    	try                                                        //~1513I~
        {                                                          //~1513I~
            if (AG.status<AG.STATUS_MAINFRAME_OPEN)                //~1513R~
            {                                                      //~@@@@I~
                AG.aView.startMain();   //open MainView            //~@@@@I~
                return;                                            //~1513R~
            }                                                      //~@@@@I~
            AG.aView.windowFocusChanged(Phasfocus);               //~1513R~//~v107R~//~@@@@R~
        }                                                          //~1513I~
        catch(Exception e)                                         //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"onWindowFocusChanged");                //~1513I~
        }                                                          //~1513I~
    }
//*************************                                        //~1128I~
//  @Override apilevel5                                            //~1128I~
    public void onAttachedToWindow()                               //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("OnAttachedToWindow");            //~1506R~
    }                                                              //~1128I~
//  @Override apilevel5                                           //~1128I~//~v107R~
    public void onDetachedFromWindow()                             //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("OnDetachedFromWindow");          //~1506R~
    }                                                              //~1128I~
//***********************************                              //~@@@@I~
//*direct from setContentView                                      //~@@@@I~
//***********************************                              //~@@@@I~
    @Override                                                      //~1128I~
    public void onContentChanged()                                 //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("OnContextChanged");              //~1506R~
    }                                                              //~1128I~
    //~1122I~
//*********************************************                                               //~0914I~//~1212I~
//* callback after OnKey if the view has focus                     //~1212I~
//*********************************************                    //~1212I~
    @Override                                                      //~0914I~//~1212I~
    public boolean onKeyDown(int keyCode,KeyEvent event)           //~1212I~
	{                                                              //~1212I~
        if (AG.status<AG.STATUS_MAINFRAME_OPEN)                    //~1329I~
            return false;                                                //~1329I~
        try                                                        //~1329I~
        {                                                          //~1329I~
        	if (AKey.onKeyDown(keyCode,event))  //true//~0A05I~//~1212I~//~1329R~//~@@@@R~
            	return true;                                       //~0A05I~//~1212I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"AMain:OnKeyDown");                      //~1329I~//~v107R~//~@@@@R~
        }                                                          //~1329I~
        return super.onKeyDown(keyCode,event);                     //~0914I~//~1212I~
    }                                                              //~0914I~//~1212I~
//******************                                               //~0914I~//~1212I~
    @Override                                                      //~0914I~//~1212I~
    public boolean onKeyUp(int keyCode,KeyEvent event)             //~1212I~
	{                                                              //~1212I~
        if (AG.status<AG.STATUS_MAINFRAME_OPEN)                    //~1329I~
            return false;                                                //~1329I~
        try                                                        //~1329I~
        {                                                          //~1329I~
        	if (AKey.onKeyUp(keyCode,event))    //true//~1212I~//~1329R~//~@@@@R~
            	return true;                                       //~1212I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"AMain:OnKeyUp");                        //~1329I~//~v107R~//~@@@@R~
        }                                                          //~1329I~
        return super.onKeyUp(keyCode,event);                       //~0914I~//~1212I~
    }                                                              //~0914I~//~1212I~
//******************                                               //~0914I~//~1212I~
    @Override                                                      //~0914I~//~1212I~
    public boolean onTouchEvent(MotionEvent event)                 //~1212I~
	{                                                              //~1212I~
    	int flag,action;                                               //~@@@@I~//~1212I~
    	Point point=new Point();                                           //~@@@@I~//~1212I~
    //********************                                         //~@@@@I~//~1212I~
        if (AG.status<AG.STATUS_MAINFRAME_OPEN)                    //~1329I~
            return false;                                                //~1329I~
    	if (Dump.Y) Dump.println("AMain:OnTouch action="+event.getAction()+",x="+event.getX()+",y="+event.getY());//~1506R~//~v107R~//~@@@@R~
        try                                                        //~1329I~
        {                                                          //~1329I~
            if (AG.status>0)                                           //~1212I~//~1329R~
            {                                                          //~@@@@I~//~1212I~//~1329R~
                point.x=(int)event.getX();                             //~@@@@I~//~1212I~//~1329R~
                point.y=(int)event.getY();                             //~@@@@I~//~1212I~//~1329R~
                action=event.getAction();                              //~@@@@R~//~1212I~//~1329R~
                if (action!=MotionEvent.ACTION_OUTSIDE)                //~@@@@I~//~1212I~//~1329R~
                {                                                      //~@@@@I~//~1212I~//~1329R~
                    if (action==MotionEvent.ACTION_DOWN)              //~@@@@R~//~1212I~//~1329R~
                        flag=1;                                        //~@@@@I~//~1212I~//~1329R~
                    else                                               //~@@@@I~//~1212I~//~1329R~
                    if (action==MotionEvent.ACTION_UP)                //~@@@@I~//~1212I~//~1329R~
                        flag=0;                                        //~@@@@I~//~1212I~//~1329R~
                    else                                               //~@@@@I~//~1212I~//~1329R~
                        flag=-1;                                       //~@@@@I~//~1212I~//~1329R~
                    if (flag>=0)                                       //~@@@@I~//~1212I~//~1329R~
                        if (AKey.onTouch(action,point))         //~@@@@R~//~1212R~//~1413R~//~@@@@R~
                            return true;                                               //~0914I~//~@@@@R~//~1212I~//~1329R~
                }                                                      //~@@@@I~//~1212I~//~1329R~
            }                                                          //~@@@@I~//~1212I~//~1329R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"AMain:OnTouch");                        //~1329I~//~v107R~//~@@@@R~
        }                                                          //~1329I~
        return false;                                              //~@@@@I~//~1212I~
    }                                                              //~0914I~//~1212I~
//*******************                                              //~1314I~
//* Option Menu *****                                              //~1314I~
//*******************                                              //~1314I~
//*** called only once***                                          //~1326I~
    @Override                                                      //~1314I~
    public boolean onCreateOptionsMenu(Menu Pmenu)                 //~1314R~
	{                                                              //~1314I~
        try                                                        //~1329I~
        {                                                          //~1329I~
	        AG.aMenu.onCreateOptionMenu(Pmenu);                    //~1314R~//~1329R~//~v107R~//~@@@@R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"AMain:OnCreateOptionMenu");             //~1329I~//~v107R~//~@@@@R~
        }                                                          //~1329I~
    	return super.onCreateOptionsMenu(Pmenu);                          //~1314R~//~1326I~
	}                                                              //~1314I~
//*** called each time to display                                  //~v107I~
    @Override                                                      //~v107I~
    public boolean onPrepareOptionsMenu(Menu Pmenu)                //~v107I~
	{                                                              //~v107I~
        try                                                        //~v107I~
        {                                                          //~v107I~
	        AG.aMenu.onPrepareOptionMenu(Pmenu);                //~v107R~//~@@@@R~
        }                                                          //~v107I~
        catch (Exception e)                                        //~v107I~
        {                                                          //~v107I~
        	Dump.println(e,"AMain:OnPrepareOptionMenu");            //~v107R~//~@@@@R~
        }                                                          //~v107I~
    	return super.onPrepareOptionsMenu(Pmenu);                  //~v107I~
	}                                                              //~v107I~
//**                                                               //~1314I~
    @Override                                                      //~1314I~
    public boolean onOptionsItemSelected(MenuItem item)            //~1314I~
	{                                                              //~1314I~
        try                                                        //~1329I~
        {                                                          //~1329I~
	    	AG.aMenu.onOptionMenuSelected(item);		//setup menu//~1314I~//~1329R~//~v107R~//~@@@@R~
        }                                                          //~1329I~
        catch (Exception e)                                        //~1329I~
        {                                                          //~1329I~
        	Dump.println(e,"AMain:OnOptionItemSelected");           //~1329I~//~v107R~//~@@@@R~
        }                                                          //~1329I~
    	return true;                                               //~1314I~
	}                                                              //~1314I~
//****************                                                 //~1218I~
//****************                                                 //~1314I~
//****************                                                 //~1314I~
//    public static boolean isTerminating()                          //~1218I~//~@@@@R~
//    {                                                              //~1218I~//~@@@@R~
//        if (Dump.Y) Dump.println("AMain:isTerminating="+destroying);//~@@@@I~
//        return destroying;                                         //~1218I~//~@@@@R~
//    }                                                              //~1218I~//~@@@@R~
//****************                                                 //~1411I~
//*Hide StatusBar                                                  //~1412I~
//    getWindow().addFlag(Windowmanager.LayoutParams.FLAG_FULLSCREEN);//~1412I~
//    getWindow().clearFlag(Windowmanager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//~1412I~
//***************************************************************************//~v107I~
	@Override                                                      //~v107I~
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//~v107I~
        if(Dump.Y) Dump.println("AMain:onActivityResult req="+requestCode+",result="+ resultCode);//~v107I~//~1A6aI~
		if (AG.aBT!=null)                                       //~v107R~//~@@@@R~
			AG.aBT.activityResult(requestCode,resultCode,data); //~v107R~//~@@@@R~
//        if (AG.aWDANFC!=null)                                      //~1A6aI~//~1A6sR~
//            AG.aWDANFC.activityResult(requestCode,resultCode,data);//~1A6aI~//~1A6sR~
    }                                                              //~v107I~
//***********************************************************      //~@@@@I~//~@@@2I~//~@@@@I~
    public void destroyClose()                                     //~@@@@I~//~@@@2I~//~@@@@I~
    {                                                              //~@@@@I~//~@@@2I~//~@@@@I~
        if (Dump.Y) Dump.println("destroyClose");//~@@@@I~//~@@@2I~//~@@@@I~
        AG.status=AG.STATUS_STOPFINISH;                               //~@@@@I~
	    URunnable.setRunFunc(this/*RunnableI*/,0/*sleep*/,null/*parm*/,0/*phase*/);//~@@@@R~//~@@@2I~//~@@@@I~
    }                                                              //~@@@@I~//~@@@2I~//~@@@@I~
//*************************                                        //~@@@@I~//~@@@2I~//~@@@@I~
//*callback from Runnable *                                        //~@@@@I~//~@@@2I~//~@@@@I~
//*************************                                        //~@@@@I~//~@@@2I~//~@@@@I~
    public void runFunc(Object Pparmobj,int Pphase)                //~@@@@I~//~@@@2I~//~@@@@I~
    {                                                              //~@@@@I~//~@@@2I~//~@@@@I~
        int wait=0,wait2=100;                                   //~@@@2I~//~@@@@R~
    //*********************                                        //~@@@2I~//~@@@@I~
    	if (Dump.Y) Dump.println("destroyClose runfunc phase="+Pphase);   //~@@@@I~//~@@@2I~//~@@@@I~
    	if (Pphase==0)	//initial call,close socket streamIO       //~@@@@I~//~@@@2I~//~@@@@I~
        {                                                          //~@@@@I~//~@@@2I~//~@@@@I~
	    	if (Dump.Y) Dump.println("destroyClose runfunc phase=0 closeStream");//~@@@@I~//~@@@2I~//~@@@@I~
        	if (AG.aPartnerFrame!=null)                        //~@@@2I~//~@@@@I~//~@@@2I~//~@@@@I~
            {                                                      //~@@@@I~//~@@@2I~//~@@@@I~
            	AG.aPartnerFrame.closeStream(); //before close socket//~@@@2I~//~@@@@R~//~@@@2I~//~@@@@I~
            }                                                      //~@@@@M~//~@@@2I~//~@@@@I~
		    URunnable.setRunFunc(this/*RunnableI*/,wait2/*sleep ms*/,null/*parm*/,1/*phase*/);//~@@@@R~//~@@@2I~//~@@@@R~
	        return;                                                //~@@@@R~//~@@@2I~//~@@@@I~
        }                                                          //~@@@@I~//~@@@2I~//~@@@@I~
    	if (Pphase==1)	//stop timer,board                         //~@@@2I~//~@@@@I~
        {                                                          //~@@@@I~//~@@@2I~//~@@@@I~
    		if (Dump.Y) Dump.println("destroyClose runfunc phase=1 stopBoard");//~@@@@I~//~@@@2I~//~@@@@I~
        	Frame f=AG.currentFrame;                               //~@@@@I~//~@@@2I~//~@@@@I~
        	if (f!=null && f instanceof TimedGoFrame)              //~@@@@R~//~@@@2I~//~@@@@I~
            {                                                      //~@@@@I~//~@@@2I~//~@@@@I~
            	boolean rc=((TimedGoFrame)f).stopBoard(true/*stop thread*/);	//stop GoTimer,  stop BoardSync thread//~@@@@R~//~@@@2I~//~@@@@I~
            	if (rc)                                            //~@@@@I~//~@@@2I~//~@@@@I~
                	wait=1000+wait2;                           //~@@@@I~//~@@@2I~//~@@@@R~
                else                                               //~@@@@I~//~@@@2I~//~@@@@I~
                	wait=wait2;                                //~@@@@I~//~@@@2I~//~@@@@R~
            }                                                      //~@@@@I~//~@@@2I~//~@@@@I~
            URunnable.setRunFunc(this/*RunnableI*/,wait/*sleep ms*/,null/*parm*/,2/*phase*/);//~@@@@R~//~@@@2I~//~@@@@I~
            return;                                            //~@@@@I~//~@@@2I~//~@@@@I~
        }                                                          //~@@@@I~//~@@@2I~//~@@@@I~
    	if (Pphase==2)	//stop timer,board                         //~@@@2I~//~@@@@I~
        {                                                          //~@@@2I~//~@@@@I~
    		if (Dump.Y) Dump.println("destroyClose runfunc phase=2 stop BT");//~@@@@I~
	        if (AG.aBT!=null)                                          //~@@@@R~//~@@@2I~//~@@@@I~
            {                                                      //~@@@2I~//~@@@@I~
            	wait=wait2;                                          //~@@@2I~//~@@@@R~
    	        AG.aBT.destroy();                                      //~@@@@R~//~@@@2I~//~@@@@I~
            }                                                      //~@@@2I~//~@@@@I~
            URunnable.setRunFunc(this/*RunnableI*/,wait/*sleep ms*/,null/*parm*/,3/*phase*/);//~@@@2I~//~@@@@I~
            return;                                                //~@@@2I~//~@@@@I~
        }                                                          //~@@@2I~//~@@@@I~
    	if (Dump.Y) Dump.println("destroyClose runfunc phase=3"); //~@@@@I~//~@@@2I~//~@@@@R~
		finish();	//shedule ondestroy                            //~@@@2I~//~@@@@I~
    }                                                              //~@@@@I~//~@@@2I~//~@@@@I~
//    //*************************************************************************//~1A6jR~
//    @Override                                                    //~1A6jR~
//    public void onNdefPushComplete(NfcEvent Pevent)              //~1A6jR~
//    {                                                            //~1A6jR~
//        if (Dump.Y) Dump.println("AMain:onNdefPushComplete");    //~1A6jR~
//    }                                                            //~1A6jR~
//    //*************************************************************************//~1A6jR~
//    @Override                                                    //~1A6jR~
//    public NdefMessage createNdefMessage(NfcEvent Pevent)        //~1A6jR~
//    {                                                            //~1A6jR~
//        if (Dump.Y) Dump.println("AMain:createNdefMessage");     //~1A6jR~
//        return null;                                             //~1A6jR~
//    }                                                            //~1A6jR~
	//*************************************************************************//~1A6jR~
    @Override                                                      //~1A6jR~
    public void onNewIntent(Intent intent)                         //~1A6jR~
    {                                                              //~1A6jR~
        if (Dump.Y) Dump.println("Amain:onNewIntent");             //~1A6jR~
       if (AG.swNFCBT)                                             //~1Ab7I~
       {                                                           //~1Ab7I~
        if (DialogNFCSelect.onNewIntent(intent))                   //~1Ab7I~
        	return;                                                //~1Ab7I~
       }                                                           //~1Ab7I~
       else                                                        //~1Ab7I~
       {                                                           //~1Ab7I~
        if (DialogNFC.onNewIntent(intent))                         //~1A6sI~
        	return;                                                //~1A6sI~
       }                                                           //~1Ab7I~
//  	AView.showToast(R.string.WarningIgnoredNewIntent);         //~1A6jR~//+1AbkR~
    	AView.showToastLong(R.string.WarningIgnoredNewIntent);     //+1AbkI~
    }                                                              //~1A6jR~
}//class                                                           //~1A6jR~