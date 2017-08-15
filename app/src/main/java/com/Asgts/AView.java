//*CID://+1AhcR~: update#= 210;                                    //+1AhcR~
//**********************************************************************//~1A40I~
//1Ahc 2016/11/23 onDraw corruption;try specify density same as display//+1AhcI~
//1Afa 2016/10/11 2016/07/11 Delete main function to avoid selected main as entrypoint for eclips starter//~1AfaI~
//1A6p 2015/02/16 display.getWidth()/getHeight() was deprecated at api13,use getSize(Point)//~1A6pI~
//1A6h 2015/02/14 NFC;if orientation changed ANFC activity was destryed by configuration change//~1A6hI~
//1A6c 2015/02/13 Bluetooth;identify paired device and discovered device//~1A6cI~//~1A6hI~
//1A4h 2014/12/03 catch OutOfMemory                                //~1A4hI~
//1A43 2014/10/05 orientation for tbi11m is 0 degree for landscape, switch to reverse orientation//~1A43I~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//**********************************************************************//~v101I~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Asgts;                                         //~1107R~  //~1108R~//~1109R~//~v106R~//~@@@@R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.Go;                                              //~@@@@I~

import com.Asgts.awt.Frame;                                        //~@@@@R~
import com.Asgts.awt.Window;                                       //~@@@@R~


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;                                    //~0913I~
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
//import android.widget.TabHost;                                   //~@@@@R~
//import android.widget.TabHost.TabSpec;                           //~@@@@R~
import android.widget.Toast;

import android.widget.TextView;
import android.view.WindowManager;


public class AView extends View                                  //~0914R~//~dataR~//~@@@@R~
	implements View.OnClickListener, UiThreadI                     //~@@@@R~
{                                                                  //~0914I~
    private static final int HIGHT_DPI_TV=72;   //?                //~@@@@R~
    private static final int HIGHT_DPI_XHIGH=50;//not tested       //~@@@@I~
    private static final int HIGHT_DPI_HIGH=38;                    //~@@@@I~
    private static final int HIGHT_DPI_MED=25;                    //~@@@@I~
    private static final int HIGHT_DPI_LOW=19;                    //~@@@@I~
    private static final int GREETING_LONG=3;                      //~v107R~//~@@@@R~
    private static final int GREETING_SHORT=6;                    //~v107R~//~@@@@R~
    private static final int SMALL_VIEW_LIMIT=400;             //~@@@@I~
    private static final int SMALL_VIEW_HEIGHT=28;	//dp           //~@@@@R~
    private static final int SMALL_IMAGE_HEIGHT=20;	//dp           //~@@@@I~
    private static final int SMALL_TEXT_SIZE=8;	//sp               //~@@@@R~
//    private static final String[] Stab_tags={"MainFrame_tag_Servers","MainFrame_tag_Partners"};//~1122I~//~@@@@R~
//    private Button[] tabbtns=new Button[2];                          //~1122I~
//    private TabHost tabhost;                                       //~1122I~//~@@@@R~
//    static private View contentView;                               //~1425R~//~@@@@R~
    private View contentView;                                      //~@@@@I~
//    private int tabctr;                                            //~1425R~//~@@@@R~
    private static boolean idLong;//showToast parameter            //~@@@@R~
	public AView()                                //~0914R~//~dataR~//~1107R~//~1111R~//~@@@@R~
    {                                                              //~0914I~
    	super(AG.context);                                         //~1111I~
        if (Dump.Y) Dump.println("AView Constructor");         //~1506R~//~@@@@R~
        setDummyMainView();  //set dummy view to get titlebar hight(could'nt get at onCreate())//~@@@@R~
    }                                                              //~0914I~
//*************************                                        //~1109I~//~1111I~//~1122M~
	public void startMain()                                       //~1120I~//~1122I~
    {                                                              //~1120I~//~1122M~
        try                                                        //~1109I~//~1120M~//~1122M~
        {                                                          //~1109I~//~1120M~//~1122M~
	    	getScreenSize();                                       //~1122I~
//          initMainFrame();                                       //~1122I~//~@@@@R~
//          AG.ahsvMain.startMain();                              //~1122I~//~@@@@R~
            AG.status=AG.STATUS_MAINFRAME_OPEN;                   //~1120M~//~1212R~//~1329R~//~@@@@I~
            fixOrientation(true);                                  //~@@@@I~
//            if (AG.startupCtr<GREETING_LONG)                         //~v107I~//~@@@@R~
//                AView.showToastLong(R.string.Greeting);                    //~1314I~//~1329R~//~2B15R~//~@@@@R~
//            else                                                     //~v107I~//~@@@@R~
//            if (AG.startupCtr<GREETING_SHORT)                        //~v107I~//~@@@@R~
//                AView.showToast(R.string.Greeting);                //~v107I~//~@@@@R~
            startPlay();                                           //~@@@@I~
        }                                                          //~1109I~//~1120M~//~1122M~
        catch(Exception e)                                         //~1109I~//~1120M~//~1122M~
        {                                                          //~1109I~//~1120M~//~1122M~
    		Dump.println(e,"AView startMain exception");//~1109I~//~1120M~//~1122M~//~1329R~//~@@@@R~
        }                                                          //~1109I~//~1120M~//~1122M~
    }                                                              //~1120I~//~1122M~
//*************************                                        //~@@@@I~
	private void fixOrientation(boolean Pfix)                      //~@@@@I~
    {                                                              //~@@@@I~
        int ori2=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;      //~@@@@I~
    	if (Pfix)                                                  //~@@@@I~
        {                                                          //~@@@@I~
            int ori=AG.resource.getConfiguration().orientation;    //~@@@@I~
////          int rot=AG.activity.getWindowManager().getDefaultDisplay().getOrientation();//~1A43R~
//            int rot=AG.activity.getWindowManager().getDefaultDisplay().getRotation();//~1A43R~
//            if (rot==Surface.ROTATION_0||rot==Surface.ROTATION_90)//~1A43R~
//                if (ori==Configuration.ORIENTATION_LANDSCAPE)    //~1A43R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;//~1A43R~
//                else                                             //~1A43R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;//~1A43R~
//            else                                                 //~1A43R~
//                if (ori==Configuration.ORIENTATION_LANDSCAPE)    //~1A43R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;//~1A43R~
//                else                                             //~1A43R~
//                    ori2=ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;//~1A43R~
            if (ori==Configuration.ORIENTATION_LANDSCAPE)          //~1A43I~
                ori2=ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;//~1A43I~
            else                                                   //~1A43I~
                ori2=ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;//~1A43I~
        }                                                          //~@@@@I~
        AG.activity.setRequestedOrientation(ori2);                 //~@@@@I~
    }                                                              //~@@@@I~
//*************************                                        //~1A6hI~
	public void fixOrientation(Activity Pactivity,boolean Pfix)    //~1A6hI~
    {                                                              //~1A6hI~
        int ori2=ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;      //~1A6hI~
    	if (Pfix)                                                  //~1A6hI~
        {                                                          //~1A6hI~
            int ori=AG.resource.getConfiguration().orientation;    //~1A6hI~
            if (ori==Configuration.ORIENTATION_LANDSCAPE)          //~1A6hI~
                ori2=ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;//~1A6hI~
            else                                                   //~1A6hI~
                ori2=ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;//~1A6hI~
        }                                                          //~1A6hI~
        Pactivity.setRequestedOrientation(ori2);                   //~1A6hI~
    }                                                              //~1A6hI~
//*************************                                        //~@@@@I~
	public void startPlay()                                        //~@@@@I~
    {                                                              //~@@@@I~
        String[] args=new String[1];                               //~@@@@I~
        args[0]="-h";                                              //~@@@@I~
    	AG.go=new Go();                                            //~@@@@R~
//      Go.main(args);                                             //~1AfaR~
        Go.GoMain(args);                                           //~1AfaI~
    }                                                              //~@@@@I~
//*************************                                        //~1122M~
	public void getScreenSize()                                    //~1122M~
    {                                                              //~1122M~
		Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~1122M~
//      AG.scrWidth=display.getWidth();                            //~1122M~//~1A6pR~
//      AG.scrHeight=display.getHeight();                          //~1122M~//~1A6pR~
        Point p=new Point();                                       //~1A6pI~
        getDisplaySize(display,p);                                 //~1A6pI~
        AG.scrWidth=p.x;	//by pixel                             //~1A6pI~
        AG.scrHeight=p.y;   //                                     //~1A6pI~
        AG.displayDensity=AG.resource.getDisplayMetrics().densityDpi;//+1AhcI~
        if (Dump.Y) Dump.println("AView: getScreenSize w="+p.x+",h="+p.y+",density="+AG.displayDensity);//~1506R~//~@@@@R~//+1AhcR~
        AG.dip2pix=AG.resource.getDisplayMetrics().density;        //~1428I~
        AG.sp2pix=AG.resource.getDisplayMetrics().scaledDensity;   //~@@@@I~
        if (Dump.Y) Dump.println("AView: dp2pix="+AG.dip2pix); //~1506R~//~@@@@R~
        AG.portrait=(AG.scrWidth<AG.scrHeight);                    //~1223R~
        if (AG.scrHeight-AG.scrWidth<SMALL_VIEW_LIMIT)             //~@@@@R~
        {                                                          //~@@@@R~
            AG.smallButton=true;                                   //~@@@@R~
            AG.smallViewHeight=(int)(SMALL_VIEW_HEIGHT*AG.dip2pix);//pix//~@@@@R~
            AG.smallImageHeight=(int)(SMALL_IMAGE_HEIGHT*AG.dip2pix);//pix//~@@@@R~
            AG.smallTextSize=SMALL_TEXT_SIZE;                      //~@@@@R~
        }                                                          //~@@@@R~
        AG.screenDencityMdpiSmallH=(AG.screenDencityMdpi && AG.scrWidth<=320);//~1A40I~
        AG.screenDencityMdpiSmallV=(AG.screenDencityMdpi && AG.scrHeight<=320);//~1A40I~
        AG.layoutMdpi=(AG.screenDencityMdpiSmallH || AG.screenDencityMdpiSmallV);//~1A6cR~//~1A6hI~
        getTitleBarHeight();                                       //~1413M~
    }                                                              //~1122M~
    public static void getTitleBarHeight()                         //~1413R~
    {                                                              //~1413M~
        Rect rect=new Rect();                                      //~1413M~
        android.view.Window w=AG.activity.getWindow();                                 //~1413M~
        View v=w.getDecorView();                                   //~1413M~
        v.getWindowVisibleDisplayFrame(rect);                      //~1413M~
        if (Dump.Y) Dump.println("Asgts DecorView rect="+rect.toString());//~1506R~//~v106R~//~@@@@R~
        v=w.findViewById(android.view.Window.ID_ANDROID_CONTENT);               //~1413M~
        AG.titleBarTop=rect.top;                                   //~1413M~
        AG.titleBarBottom=v.getTop();                              //~1413M~
        if (Dump.Y) Dump.println("Asgts TitleBar top="+AG.titleBarTop+",bottom="+AG.titleBarBottom);//~1506R~//~v106R~//~@@@@R~
    }                                                              //~1413M~
    public static Point getTitleBarPosition()                      //~1413I~
    {                                                              //~1413I~
    	if (AG.titleBarBottom==0)                                  //~1413I~
        	getTitleBarHeight();                                   //~1413I~
        return new Point(AG.titleBarTop,AG.titleBarBottom);        //~1413I~
    }                                                              //~1413I~
    public static int getFramePosition()                         //~1413I~
    {                                                              //~1413I~
    	if (AG.titleBarBottom==0)                                  //~1413I~
        {                                                          //~@@@@I~
        	getTitleBarHeight();                                   //~1413I~
			if (AG.titleBarBottom==0) //not yet drawn once(in onCreate())//~@@@@I~
            {                                                      //~@@@@I~
            	return getDefaultTitlebarHeight();                 //~@@@@I~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
        return AG.titleBarBottom;                                  //~1413I~
    }                                                              //~1413I~
//******************                                               //~@@@@I~
    public static int getDefaultTitlebarHeight()                   //~@@@@I~
    {                                                              //~@@@@I~
        int	h=HIGHT_DPI_TV;                                        //~@@@@R~
        int density=AG.resource.getDisplayMetrics().densityDpi;    //~@@@@I~
        switch(density)                                            //~@@@@I~
        {                                                          //~@@@@I~
        case DisplayMetrics.DENSITY_MEDIUM:                        //~@@@@I~
        	h=HIGHT_DPI_MED;                                      //~@@@@I~
            break;                                                 //~@@@@I~
        case DisplayMetrics.DENSITY_LOW:                           //~@@@@I~
        	h=HIGHT_DPI_LOW;                                      //~@@@@I~
            break;                                                 //~@@@@I~
        case DisplayMetrics.DENSITY_HIGH:                          //~@@@@I~
	        h=HIGHT_DPI_HIGH;                                      //~@@@@I~
            break;                                                 //~@@@@I~
        case DisplayMetrics.DENSITY_XHIGH:                         //~@@@@I~
	        h=HIGHT_DPI_XHIGH;                                     //~@@@@I~
            break;                                                 //~@@@@I~
        }                                                          //~@@@@I~
        return h;                                           //~@@@@I~
    }                                                              //~@@@@I~
//******************                                               //~@@@@I~
    public static int getMargin()                                  //~v101I~
    {                                                              //~v101I~
    	int top=getFramePosition();                                //~v101I~
        return top+AG.bottomSpaceHeight;                           //~v101I~
    }                                                              //~v101I~
//******************                                               //~1326I~
    public void setContentView(Frame Pframe)                       //~1326I~
    {                                                              //~1326I~
        UiThread.runOnUiThreadWait(this,Pframe);              //~1326I~//~@@@@R~
    }                                                              //~1326I~
    public void runOnUiThread(Object Pparm)                        //~1326I~
    {                                                              //~1326I~
    	if (Pparm instanceof Frame)                                //~1513I~
        {                                                          //~1513I~
            Frame frame=(Frame)Pparm;                              //~1513R~
            View  view=frame.framelayoutview;                      //~1513R~
            if (Dump.Y) Dump.println("setContentView start id="+Integer.toHexString(view.getId()));//~1513R~
            if (Dump.Y) Dump.println("frame name="+frame.framename+",view="+view.toString());//~1513R~//~@@@@R~
            AG.aMain.setContentView(view);                        //~1513R~//~@@@@R~
            if (frame.currentTitle!=null)                          //~1513R~
                frame.setTitle(frame.currentTitle);     //update also title which may be changed//~1513R~
            else                                                   //~1513R~
                frame.setTitle(frame.framename);                           //~1326I~//~1513R~
            frame.seticonImage(null);//restore icon                //~1513R~
            contentView=view;    //save current for layouting      //~1513R~
            AG.setCurrentFrame(frame);                             //~1513R~
            if (Dump.Y) Dump.println("setContentView end id="+Integer.toHexString(contentView.getId()));//~1513R~
            return;                                                //~1513I~
        }                                                          //~1513I~
    	if (Pparm instanceof String) 	//toast                    //~1513I~
        {                                                          //~1513I~
        	String msg=(String)Pparm;                              //~1513I~
	    	if (Dump.Y) Dump.println("UIshowToast msg="+msg);       //~1513I~
            if (idLong)                                            //~1514I~
		        Toast.makeText(AG.context,msg,Toast.LENGTH_LONG).show();//~1514I~
            else                                                   //~1514I~
		        Toast.makeText(AG.context,msg,Toast.LENGTH_SHORT).show();//~1514R~
            return;                                                //~1513I~
        }                                                          //~1513I~
    }                                                              //~1326I~

//*************************                                        //~1128I~
	static public View inflateView(int Presid)                     //~1128I~
    {                                                              //~1128I~
		View layoutview=inflateLayout(Presid);                     //~1128I~
        return layoutview;                                         //~1128I~
    }                                                              //~1128I~
//******************                                               //~1124I~//~1216M~
	static private View inflateLayout(int Presid)                   //~1122I~//~1216I~
    {                                                              //~1122I~//~1216M~
    	View layoutView=AG.inflater.inflate(Presid,null);          //~1122I~//~1216M~
        if (Dump.Y) Dump.println("AView:inflateLayout res="+Integer.toHexString(Presid)+",view="+layoutView.toString());//~@@@@R~
    	AG.setCurrentView(Presid,layoutView);                      //~1216I~
        return layoutView;                                         //~1122I~//~1216M~
    }                                                              //~1122I~//~1216M~
//**************************************************************   //~1410I~
//*from Dialog,reuse old layout for redo modalDialog Action        //~1410I~
//**************************************************************   //~1410I~
	static public View inflateLayout(int Presid,View PlayoutView) //~1410I~
    {                                                              //~1410I~
        if (Dump.Y) Dump.println("AView:inflateLayout2 res="+Integer.toHexString(Presid)+",view="+PlayoutView.toString());//~@@@@R~
    	AG.setCurrentView(Presid,PlayoutView);                     //~1410I~
        return PlayoutView;                                        //~1410I~
    }                                                              //~1410I~
////******************                                               //~1124I~//~1128M~//~@@@@R~
//    static public View getContentView()                            //~1122I~//~1128M~//~@@@@R~
//    {                                                              //~1122I~//~1128M~//~@@@@R~
//        return contentView;                                        //~1122I~//~1128M~//~@@@@R~
//    }                                                              //~1122I~//~1128M~//~@@@@R~
//******************                                               //~1122I~
//******************                                               //~1122I~
////******************                                               //~1122I~//~@@@@I~
////* MainFrameTab ***                                               //~1122I~//~@@@@I~
////******************                                               //~1122I~//~@@@@I~
//    public void initMainFrameTab(Frame Pmainframe) //from Applet(Go)//~1125R~//~@@@@I~
//    {                                                              //~1122M~//~@@@@I~
//    //************                                                 //~1122M~//~@@@@I~
//        if (Dump.Y) Dump.println("AView:initMainFrameTab tabctr="+tabctr);//~1506R~//~@@@@R~
//        if (tabctr==0)                                             //~1125R~//~@@@@I~
//        {                                                          //~1125I~//~@@@@I~
//            initTab();                                                 //~1122I~//~1125R~//~@@@@I~
//        }                                                          //~1125I~//~@@@@I~
//        initCardPanelLayout(tabctr);                               //~1125R~//~@@@@I~
//        tabctr++;                                                  //~1125I~//~@@@@I~
//    }                                                              //~1122M~//~@@@@I~
//******************                                               //~1122I~
//    public void initTab()                                          //~1122I~//~@@@@R~
//    {                                                              //~1122I~//~@@@@R~
//        TabSpec tab;                                               //~1122I~//~@@@@R~
//        Button btn;                                                //~1122I~//~@@@@R~
//    //************                                                 //~1122I~//~@@@@R~
//        tabhost=(TabHost)(contentView.findViewById(android.R.id.tabhost));             //~1122I~//~1124R~s//~@@@@R~
//        if (Dump.Y) Dump.println("AView:initTab tabhost="+((Object)tabhost).toString());//~1506R~//~@@@@R~
//        tabhost.setup();            //method by without TabActibity//~1124R~//~@@@@R~
//        tab=tabhost.newTabSpec(Stab_tags[0]);                      //~1122R~//~@@@@R~
//        btn=new Button(AG.context);                                //~1122R~//~@@@@R~
//        btn.setText(AG.tabName_ServerConnections);                 //~1122R~//~@@@@R~
//        tabbtns[0]=btn;                                            //~1122I~//~@@@@R~
//        tab.setIndicator(btn);                                     //~1122R~//~@@@@R~
//        tab.setContent(AG.TabLayoutID_Servers);                    //~1122R~//~@@@@R~
//                                                                   //~1122I~//~@@@@R~
//        tabhost.addTab(tab);                                       //~1122I~//~@@@@R~
//                                                                   //~1122I~//~@@@@R~
//        tab=tabhost.newTabSpec(Stab_tags[1]);                      //~1122R~//~@@@@R~
//        btn=new Button(AG.context);                                //~1122R~//~@@@@R~
//        btn.setText(AG.tabName_PartnerConnections);                //~1122I~//~@@@@R~
//        tabbtns[1]=btn;                                            //~1122I~//~@@@@R~
//        tab.setIndicator(btn);                                     //~1122R~//~@@@@R~
//        tab.setContent(AG.TabLayoutID_Partners);                   //~1122I~//~@@@@R~
//                                                                   //~1122I~//~@@@@R~
//        tabhost.addTab(tab);                                       //~1122I~//~@@@@R~
//        tabhost.setCurrentTab(AG.mainframeTag);                  //~@@@@R~
//        tabhost.setOnTabChangedListener(AG.ajagoc);  //~1122R~   //~@@@@R~
//    }                                                              //~1122I~//~@@@@R~
//******************                                               //~1122I~
//    public void onTabChanged(String Ptag)                          //~1122I~//~@@@@R~
//    {                                                              //~1122I~//~@@@@R~
//    //************                                                 //~1122I~//~@@@@R~
//        if (Ptag.equals(Stab_tags[0]))                              //~1122I~//~@@@@R~
//            AG.mainframeTag=0;                                     //~1122I~//~@@@@R~
//        else                                                       //~1122I~//~@@@@R~
//            AG.mainframeTag=1;                                     //~1122I~//~@@@@R~
//        if (Dump.Y) Dump.println("tab select "+Ptag+",idx="+AG.mainframeTag);//~1506R~//~@@@@R~
//    }                                                              //~1122I~//~@@@@R~
//******************                                               //~1122I~
//    public void initCardPanelLayout(int Ppanelctr)                 //~1125R~//~@@@@R~
//    {                                                              //~1122I~//~@@@@R~
//        int layoutid;                                              //~1125I~//~@@@@R~
//    //****************                                             //~1125I~//~@@@@R~
//        if (Ppanelctr==0)                                          //~1125R~//~@@@@R~
//            layoutid=AG.frameId_ServerConnections;                 //~1125I~//~@@@@R~
//        else                                                     //~@@@@R~
//            layoutid=AG.frameId_PartnerConnections;                //~1125I~//~@@@@R~
//        View layoutview=inflateView(layoutid);               //~1125I~//~1128R~//~@@@@R~
//        setTabLayout(layoutid,layoutview);                     //~1122R~//~1125R~//~@@@@R~
//    }                                                              //~1125I~//~@@@@R~
//******************                                               //~1125I~
//    public void setTabLayout(int Playoutid,View Playout)           //~1125I~//~@@@@R~
//    {                                                              //~1125I~//~@@@@R~
//        int containerID;                                           //~1125I~//~@@@@R~
//        LinearLayout container;                                    //~1125I~//~@@@@R~
//    //************                                                 //~1122I~//~@@@@R~
//        if (Dump.Y) Dump.println("AView:setTabLayoutt Playoutid="+Integer.toHexString(Playoutid));//~1506R~//~@@@@R~
//        if (Playoutid==AG.frameId_ServerConnections)               //~1125I~//~@@@@R~
//        {                                                          //~1122I~//~@@@@R~
//            containerID=AG.TabLayoutID_Servers;                    //~1122I~//~@@@@R~
//        }                                                          //~1122I~//~@@@@R~
//        else                                                       //~1122I~//~@@@@R~
//        {                                                          //~1122I~//~@@@@R~
//            containerID=AG.TabLayoutID_Partners;                 //~@@@@R~
//        }                                                          //~1122I~//~@@@@R~
//        FrameLayout framelayout=tabhost.getTabContentView();       //~1122R~//~@@@@R~
//        container=(LinearLayout)framelayout.findViewById(containerID);       //~1122I~//~@@@@R~
//        container.addView(Playout,0/*pos*/);                       //~1122R~//~@@@@R~
//    }                                                              //~1122I~//~@@@@R~
//*************************************************                //~@@@@I~
//*set dummy ContentView to get titilebar height                   //~@@@@I~
//*************************************************                //~@@@@I~
    private void setDummyMainView()                                //~@@@@R~
    {                                                              //~@@@@I~
    //****************                                             //~@@@@I~
        View layoutview=inflateView(R.layout.main);                //~@@@@I~
        AG.aMain.setContentView(layoutview);                             //~@@@@I~
    }                                                              //~@@@@I~
//******************                                               //~@@@@I~
    @Override                                                      //~1111I~
    public void onClick(View Pview)                                 //~1109I~//~1111I~
	{                                                              //~1109I~//~1111I~
//        AG.ajagoMain.onClick(Pview);                                //~1109I~//~1111I~//~1112R~
	}                                                              //~1109I~//~1111I~
//************                                                     //~1113I~
    static public void setEnabled(View Playout,int Presid)         //~1113I~
    {                                                              //~1113I~
    	View view=(View)Playout.findViewById(Presid);              //~1113I~
    	view.setEnabled(true);                                     //~1113I~
    }                                                              //~1113I~
//************                                                     //~1114I~
    static public void setText(View Playout,int Presid,String Ptext)//~1114I~
    {                                                              //~1114I~
    	TextView tv=(TextView)Playout.findViewById(Presid);            //~1114I~
    	tv.setText(Ptext);                                         //~1114I~
    }                                                              //~1114I~
//************                                                     //~1128I~
    public void windowFocusChanged(boolean Phasfocus)              //~1128R~
    {                                                              //~1128R~
        if (Dump.Y) Dump.println("AView OnFocusChangeListener focus="+Phasfocus);//~1506R~//~@@@@R~
        Window.onFocusChanged(Phasfocus);                          //~1513R~
    }                                                              //~1128R~
//************                                                     //~1217I~
//    public Button getTabButton(int PbuttonNo)   //from Button<--CardPanel//~1217I~//~@@@@R~
//    {                                                              //~1217I~//~@@@@R~
//        if (Dump.Y) Dump.println("getTabButton from CardButton ="+PbuttonNo);//~1506R~//~@@@@R~
//        return tabbtns[PbuttonNo];                                 //~1217I~//~@@@@R~
//    }                                                              //~1217I~//~@@@@R~
//**********************************************************       //~1314I~
    public static void showToast(int Presid)                       //~1314I~
    {                                                              //~1314I~
		showToast(Presid,"");                                      //~1513I~
    }                                                              //~1314I~
//**********************************************************       //~1514I~
    public static void showToastLong(int Presid)                   //~1514I~
    {                                                              //~1514I~
		showToastLong(Presid,"");                                  //~1514I~
    }                                                              //~1514I~
//**********************************************************       //~1421I~
    public static void showToast(int Presid,String Ptext)          //~1421I~
    {                                                              //~1421I~
        String msg=AG.resource.getString(Presid)+Ptext;            //~1421I~
    	if (Dump.Y) Dump.println("showToast msg="+msg);             //~1513I~
        if (AG.status==AG.STATUS_STOPFINISH)                          //~@@@@I~
            return;                                                //~@@@@I~
        idLong=false;                                              //~1514I~
    	UiThread.runOnUiThreadXfer(AG.aView,msg);                 //~1513I~//~@@@@R~
    }                                                              //~1421I~
//**********************************************************       //~1514I~
    public static void showToastLong(int Presid,String Ptext)      //~1514I~
    {                                                              //~1514I~
        String msg=AG.resource.getString(Presid)+Ptext;            //~1514I~
    	if (Dump.Y) Dump.println("showToastLong msg="+msg);        //~1514I~
        if (AG.status==AG.STATUS_STOPFINISH)                          //~@@@@I~
            return;                                                //~@@@@I~
        idLong=true;                                               //~1514I~
    	UiThread.runOnUiThreadXfer(AG.aView,msg);            //~1514I~//~@@@@R~
    }                                                              //~1514I~
//**********************************************************       //~@@@@I~
    public static void showToast(String Ptext)                     //~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("showToast msg="+Ptext);          //~@@@@I~
        if (AG.status==AG.STATUS_STOPFINISH)                          //~@@@@I~
            return;                                                //~@@@@I~
        idLong=false;                                              //~@@@@I~
    	UiThread.runOnUiThreadXfer(AG.aView,Ptext);                //~@@@@I~
    }                                                              //~@@@@I~
//**********************************************************       //~@@@@I~
    public static void showToastLong(String Ptext)                 //~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("showToastLong msg="+Ptext);      //~@@@@I~
        if (AG.status==AG.STATUS_STOPFINISH)                          //~@@@@I~
            return;                                                //~@@@@I~
        idLong=true;                                               //~@@@@I~
    	UiThread.runOnUiThreadXfer(AG.aView,Ptext);                //~@@@@I~
    }                                                              //~@@@@I~
//**********************************                               //~v106I~
    public static void endGameConfirmed()                          //~v106I~
    {                                                              //~v106I~
    	showToastLong(R.string.endGameConfirmed);                   //~v106R~
	}                                                              //~v106I~
//**********************************                               //~v106I~
    public static void lockContention(String Ptext)                //~v106I~
    {                                                              //~v106I~
    	showToastLong(R.string.lockContention,Ptext);              //~v106I~
	}                                                              //~v106I~
//**********************************                               //~1A4hI~
    public static void memoryShortage(String Ptext)                //~1A4hI~
    {                                                              //~1A4hI~
    	showToastLong(R.string.ErrOutOfMemory,Ptext);              //~1A4hI~
	}                                                              //~1A4hI~
//**********************************                               //~1A6pI~
    public static void getDisplaySize(Display Pdisplay,Point Ppoint)//~1A6pI~
    {                                                              //~1A6pI~
        if (AG.osVersion<AG.HONEYCOMB_MR2)  //android3.2=api-13    //~1A6pI~
			getDisplaySize_deprecated(Pdisplay,Ppoint);            //~1A6pI~
        else                                                       //~1A6pI~
			getDisplaySize_new(Pdisplay,Ppoint);                   //~1A6pI~
    }                                                              //~1A6pI~
	@TargetApi(AG.HONEYCOMB_MR2)                                   //~1A6pI~
    private static void getDisplaySize_new(Display Pdisplay,Point Ppoint)//~1A6pI~
    {                                                              //~1A6pI~
        Pdisplay.getSize(Ppoint);                                  //~1A6pI~
    }                                                              //~1A6pI~
    @SuppressWarnings("deprecation")                               //~1A6pI~
    private static void getDisplaySize_deprecated(Display Pdisplay,Point Ppoint)//~1A6pI~
    {                                                              //~1A6pI~
		Ppoint.x=Pdisplay.getWidth();                              //~1A6pI~
		Ppoint.y=Pdisplay.getHeight();                             //~1A6pI~
    }                                                              //~1A6pI~
}//class AView                                                 //~dataR~//~@@@@R~
