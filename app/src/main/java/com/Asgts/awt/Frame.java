//*CID://+v101R~:                             update#=   66;       //~v107I~//~v108R~//~@@@@R~//~@@@2R~//~v101R~
//**********************************************************************//~v107I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//1084:121215 connection frame input field is untachable when restored after Who frame//~v108I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//**********************************************************************//~v107I~
package com.Asgts.awt;                                                //~1108R~//~1109R~//~@@@@R~//~v101R~
                                                                   //~1221I~
import jagoclient.Dump;
import jagoclient.Global;
//import jagoclient.gmp.GMPConnection;                             //~@@@@R~
import jagoclient.gui.CloseFrame;
import jagoclient.gui.DoActionListener;

import com.Asgts.AG;                                                //~@@@@R~//~v101R~
//import com.Asgts.AjagoGMP;                                        //~@@@@R~//~v101R~
import com.Asgts.AKey;                                              //~@@@@R~//~v101R~
import com.Asgts.AView;                                             //~@@@@R~//~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.awt.Window;                                        //~@@@@R~//~v101R~
import android.view.View;
//import android.view.inputmethod.InputMethodManager;              //~v108R~

import android.widget.LinearLayout;

public class Frame extends Window //skip Window of Container-->Window-->Frame                                                 //~1111R~//~1116R~//~1124R~
{                                                                  //~1111I~

	public static final int MAXIMIZED_BOTH  =3;                    //~1213R~
	public static Frame MainFrame;                                 //~1420R~

    public  String framename;                                      //~1420R~
    public  View framelayoutview;                                  //~1420R~
    public  int framelayoutresourceid;                           //~1125R~//~1420R~
    private boolean setcontentview_at_show;                        //~1420R~
                                                                   //~1217I~
    public  int frametopviewid;                                    //~1420R~
    public MenuBar framemenubar;                                   //~1420R~
    public  View contextMenuView;                                  //~1420R~
    public  WindowListener windowlistener;                         //~1420R~
    public  FocusListener focuslistener;                           //~1420R~
    public MouseListener mouselistener;                            //~1420R~
    public DoActionListener frameDoActionListener;                 //~1420R~
    public int framestatus;	//update by Window.java                                     //~1128R~//~1420R~
    public boolean isBoardFrame;                                   //~1420R~
    public boolean isDestroyed;                                    //~1429I~
//    public boolean isObserveGame;                                  //~1503I~//~@@@2R~
    public Canvas boardCanvas;                                     //~1420R~
    public String currentTitle;                                    //~1420R~
    public Dialog modalDialog_beforeDismiss;                       //~1420R~
    public KeyListener framekeylistener;                           //~1427I~
    private boolean disposed;                                      //~1516I~
//*********                                                        //~1111I~
//*for warning                                                     //~1111I~
//*********                                                        //~1111I~
    public Frame()                                                 //~1111I~
    {                                                              //~1111I~
    	super();                                               //~1310R~
//      if (!(this instanceof PartnerFrame))                          //~@@@@I~//~@@@2R~
//      if (!(this instanceof LocalFrame))                         //~@@@2R~
//        setComponentType(this);                             //~1310I~//~@@@2R~
    }                                                              //~1111I~
    public Frame(boolean Phavingview)                              //~@@@2I~
    {                                                              //~@@@2I~
    	this();                                                    //~@@@2I~
      	if (Phavingview)                                           //~@@@2I~
        {                                                          //+v101I~
	        if (Dump.Y) Dump.println("Frame setcomponenttype");    //+v101I~
	        setComponentType(this);                                //~@@@2I~
        }                                                          //+v101I~
    }                                                              //~@@@2I~
//*********                                                        //~1113I~
    public Frame(String s)                                         //~1113I~
    {                                                              //~1113I~
    	this(true);                                                    //~1310I~//~@@@2R~
    	if (s.equals(""))                                          //~1217I~
        	return;                                                //~1217I~
    	setFrameLayout(s);                                              //~1217I~//~1218R~
    }                                                              //~1113I~
//*********                                                        //~@@@2I~
    public Frame(int Presid,String s)                              //~@@@2I~
    {                                                              //~@@@2I~
    	this(Presid!=0);                                           //~@@@2R~
    	setFrameLayout(Presid,s);	//set framename,so skip setFrameLayout by setTitle()//~@@@2R~
    }                                                              //~@@@2I~
//*********************************************************        //~1217I~
//*CloseFrame constructer is super("") then setTitle(s)             //~1217I~
//*********************************************************        //~1217I~
    public void setTitle(String s)                                 //~1217R~
    {                                                              //~1217I~
//  	if (framename==null && s.startsWith(Global.resourceString("_Jago_")))//~1506R~//~@@@@R~
//        if (framename==null && s.startsWith(AG.appName))         //~@@@@R~
//        {                                                          //~1506I~//~@@@@R~
//          s+=" ("+AG.appName+AG.appVersion+")";                  //~1506I~//~@@@@R~
//        }                                                          //~1506I~//~@@@@R~
//        if (s.startsWith(AG.resource.getString(R.string.BTClient))//~@@@@R~
//        ||  s.startsWith(AG.resource.getString(R.string.BTServer)))//~@@@@R~
        if (framelayoutresourceid==0)	//have no layout           //~@@@2I~
			return;                                                //~@@@2I~
//        if ((this instanceof PartnerFrame))                        //~@@@@I~//~@@@2R~
//        {                                                          //~@@@@I~//~@@@2R~
//            super.setTitle(s);  //Component:                       //~@@@@I~//~@@@2R~
//            return; //ignore partnerframe                          //~@@@@I~//~@@@2R~
//        }                                                          //~@@@@I~//~@@@2R~
//        if ((this instanceof LocalFrame))                        //~@@@2R~
//        {                                                        //~@@@2R~
//            super.setTitle(s);  //Component:                     //~@@@2R~
//            return; //ignore partnerframe                        //~@@@2R~
//        }                                                        //~@@@2R~
    	currentTitle=s;                                            //~1330I~
    	if (framename==null)                                       //~1217I~
    		setFrameLayout(s);                                          //~1217I~//~1218R~
        if (framelayoutview!=null)                                 //~1217I~
        {                                                          //~1221I~
	        super.setTitle(s);	//Component:                               //~1217I~//~1221R~
        }                                                          //~1221I~
    }                                                              //~1217I~
//*********                                                        //~1217I~
//*for search then layout and inflate                              //~1217I~
//*********                                                        //~1217I~
    private void setFrameLayout(String s)                               //~1217I~//~1218R~
    {                                                              //~1217I~
    	int resid=0;                                               //~1217I~
    //************                                                 //~1217I~
        framename=s;                                               //~1217I~
//  	if (s.startsWith(Global.resourceString("_Jago_")))         //~1217R~//~@@@@R~
//        if (s.startsWith(AG.appName))                              //~@@@@I~//~@@@2R~
//        {                                                          //~1506I~//~@@@2R~
////            AjagoGMP.setup();   //set GMPserver pgm name after readparameter(go.cfg)//~1511I~//~@@@@R~//~@@@2R~
////            Dump.checkOption(); //AjagoOption check after go.cfg was read//~1507I~//~@@@@R~//~@@@2R~
//            resid=AG.frameId_MainFrame; //tab layout             //~@@@2R~
//        }                                                          //~1506I~//~@@@2R~
//        else                                                       //~1218I~//~@@@2R~
//        if (s.startsWith(Global.resourceString("Connection_to_")))//~1217I~//~@@@@R~
//        {                                                          //~1318I~//~@@@@R~
//            if (s.startsWith(Global.resourceString("Connection_to_")+com.Asgts.jagoclient.partner.PartnerFrame.CONNECT_TO_BT))//~v107R~//~@@@@R~//~v101R~
//                resid=AG.frameId_PartnerFrame;   //Bluetooth connection frame//~v107I~//~@@@@R~
////            else                                                   //~v107I~//~@@@@R~
////            if (AG.mainframeTag==0) //Cardpanel:ServerConnection   //~1318R~//~@@@@R~
////                resid=AG.frameId_ConnectionFrame;                  //~1318I~//~@@@@R~
////            else                                                   //~1318I~//~@@@@R~
////                resid=AG.frameId_PartnerFrame;   //Go*2            //~1405R~//~@@@@R~
//        }                                                          //~1318I~//~@@@@R~
//        else                                                       //~1217I~//~@@@@R~
//        if (s.startsWith(Global.resourceString("Server")))         //~1405I~//~@@@@R~
//            resid=AG.frameId_PartnerFrame;  //partner.Server       //~1405I~//~@@@@R~
//        else                                                       //~1405I~//~@@@@R~
//        if (s.equals(Global.resourceString("Peek_game"))     //igs.ConnectionFrame//~1405R~//~@@@2R~
//        ||  s.equals("Peek game")     //igs.ConnectionFrame        //~1501I~//~@@@2R~
//        ||  s.equals(Global.resourceString("Play_game"))           //~1217I~//~@@@2R~
//        ||  s.equals("Play game")                           //IgsStream//~1501R~//~@@@2R~
//        ||  s.equals(Global.resourceString("Partner_Game"))  //PartnerFrame//~1405I~//~@@@2R~
//        ||  s.equals("Partner Game")                         //PartnerFrame//~1501R~//~@@@2R~
//        )                                                          //~1217I~//~@@@2R~
//            resid=AG.frameId_ConnectedGoFrame;                     //~1217I~//~@@@2R~
//        else                                                       //~1503I~//~@@@2R~
//        if (s.equals(Global.resourceString("Observe_game"))        //~1503I~//~@@@@R~
//        ||  s.equals("Observe game")                               //~1503M~//~@@@@R~
//        )                                                          //~1503I~//~@@@@R~
//        {                                                          //~1503I~//~@@@@R~
//            resid=AG.frameId_ConnectedGoFrame;                     //~1503I~//~@@@@R~
//            isObserveGame=true;                                    //~1503I~//~@@@@R~
//        }                                                          //~1503I~//~@@@@R~
//        else                                                       //~1217I~//~@@@@R~
//        if (s.equals(Global.resourceString("_Games_")))            //~1217I~//~@@@@R~
//            resid=AG.frameId_GamesFrame;                           //~1217I~//~@@@@R~
//        else                                                       //~1306I~//~@@@@R~
//        if (s.equals(Global.resourceString("_Who_")))              //~1306I~//~@@@@R~
//            resid=AG.frameId_WhoFrame;                             //~1306I~//~@@@@R~
//        else                                                       //~1310I~//~@@@@R~
        if (s.equals(Global.resourceString("_Message_")))          //~1310I~
            resid=AG.frameId_MessageDialog;                        //~1310I~
        else                                                       //~1311I~
        if (s.equals(Global.resourceString("Say")))                //~1311I~
            resid=AG.frameId_SayDialog;                            //~1311I~
        else                                                       //~1323I~
//        if (s.equals(Global.resourceString("Local_Viewer")))       //~1323I~//~@@@@R~//~@@@2R~
//            resid=AG.frameId_LocalViewer;                          //~1323I~//~@@@@R~//~@@@2R~
//        else                                                       //~1326I~//~@@@@R~//~@@@2R~
//      if (s.equals(Global.resourceString("Help")))               //~1326I~//~@@@2R~
//        if (s.equals(AG.resource.getString(R.string.Help)))        //~@@@2I~
//            resid=AG.frameId_Help;                                 //~1326I~
//        else                                                       //~1331I~
//        if (s.equals(Global.resourceString("Message_Filter")))     //~1331I~//~@@@@R~
//            resid=AG.frameId_MessageFilter;                        //~1331I~//~@@@@R~
//        else                                                       //~1404I~//~@@@@R~
//        if (s.equals(Global.resourceString("Play_Go")))            //~1404I~//~@@@2R~
//        {                                                          //~1512I~//~@@@2R~
//            if (windowlistener instanceof GMPConnection)      //CloseFrame pass "this" as WindowListener//~1512I~//~@@@@R~
//                resid=AG.frameId_GMPConnection;                    //~1512R~//~@@@@R~
//            else                                                   //~1512I~//~@@@@R~
//                resid=AG.frameId_ConnectedGoFrame;                 //~1512I~//~@@@2R~
//        }                                                          //~1512I~//~@@@2R~
//        else                                                       //~1405I~//~@@@2R~
//        if (s.equals(Global.resourceString("Open_Partners")))      //~1405I~//~@@@@R~
//            resid=AG.frameId_OpenPartners;     //partner.OpenPartnerFrame//~1405I~//~@@@@R~
//        else                                                       //~1405I~//~@@@@R~
        if (s.equals(Global.resourceString("Send")))               //~1405I~
            resid=AG.frameId_PartnerSendQuestion;    		 //partner.PartnerSendQuestion;//~1405I~
        if (Dump.Y) Dump.println("Frame name="+s+",layoutid="+Integer.toString(resid,16));//~1506R~
        if (resid!=0)                                              //~1217I~
        {                                                          //~1217I~
        	framelayoutresourceid=resid;                           //~1217I~
        	framelayoutview=AView.inflateView(resid);	//no setContentView until show()//~1217I~//~@@@@R~
//            setGoFrameVisibility();                                //~1415R~//~@@@@R~
	        componentView=framelayoutview;	//for Component.requestFocus;//~1405I~
	        setContainerLayoutView(framelayoutview);	//for findViewById     //~@@@2I~//~v101R~
            if (framelayoutresourceid==AG.frameId_MainFrame)       //~1218I~
				initMainFrame();                                   //~1218I~
            else                                                   //~1218I~
            	setcontentview_at_show=true;	//push at show                           //~1217I~//~1218R~
        }                                                          //~1217I~
    }                                                              //~1217I~
//**************************************************************** //~@@@2I~
//*by layout id                                                    //~@@@2I~
//**************************************************************** //~@@@2I~
    private void setFrameLayout(int Presid,String s/*title*/)      //~@@@2I~
    {                                                              //~@@@2I~
    //************                                                 //~@@@2I~
        if (Dump.Y) Dump.println("Frame name="+s+",layoutid="+Integer.toString(Presid,16));//~@@@2I~
        framename=s;                                               //~@@@2I~
        framelayoutresourceid=Presid;                              //~@@@2I~
        if (Presid==0)                                             //~@@@2I~
        	return;                                                //~@@@2I~
        framelayoutview=AView.inflateView(Presid);   //no setContentView until show()//~@@@2I~
        componentView=framelayoutview;  //for Component.requestFocus;//~@@@2I~
	    setContainerLayoutView(framelayoutview);	//for findViewById//~v101I~
        if (framelayoutresourceid==AG.frameId_MainFrame)           //~@@@2I~
            initMainFrame();                                       //~@@@2I~
        else                                                       //~@@@2I~
            setcontentview_at_show=true;    //push at show         //~@@@2I~
    }                                                              //~@@@2I~
////***************************                                      //~1415I~//~@@@@R~
////*set IconBar Visible                                             //~1415I~//~@@@@R~
////***************************                                      //~1415I~//~@@@@R~
//    private void setGoFrameVisibility()                            //~1415R~//~@@@@R~
//    {                                                              //~1415I~//~@@@@R~
//        boolean visibleIB=true,visibleNavi=true;                   //~1415R~//~@@@@R~
//    //***********                                                  //~1415I~//~@@@@R~
//        if (framelayoutresourceid==AG.frameId_ConnectedGoFrame)    //~1415R~//~@@@@R~
//        {                                                          //~1415I~//~@@@@R~
//            visibleIB=Global.getParameter("showbuttonsconnected",true);//~1415R~//~@@@@R~
//        }                                                          //~1415I~//~@@@@R~
////        else                                                       //~1415I~//~@@@@R~
////        if (framelayoutresourceid==AG.frameId_LocalViewer)         //~1415I~//~@@@@R~
////        {                                                          //~1415I~//~@@@@R~
////            visibleIB=Global.getParameter("showbuttons",true);     //~1415R~//~@@@@R~
////            visibleNavi=Global.getParameter("shownavigationtree",true);//~1415M~//~@@@@R~
////        }                                                          //~1415I~//~@@@@R~
//        if (!visibleIB)                                            //~1415R~//~@@@@R~
//        {                                                          //~1415R~//~@@@@R~
//            View v=AG.findViewById(AG.viewId_IconBar);            //~1415R~//~@@@@R~
//            if (v!=null)                                           //~1415R~//~@@@@R~
//            {                                                      //~1415I~//~@@@@R~
//                v.setVisibility(View.GONE); //disappear            //~1415R~//~@@@@R~
//                if (Dump.Y) Dump.println("Frame:setGoFrameVisibility set GONE layoutid(IconBar)="+Integer.toHexString(framelayoutresourceid));//~1506R~//~@@@@R~
//            }                                                      //~1415I~//~@@@@R~
//        }                                                          //~1415I~//~@@@@R~
//        if (!visibleNavi)                                          //~1415I~//~@@@@R~
//        {                                                          //~1415I~//~@@@@R~
//            View v=AG.findViewById(AG.viewId_NavigationPanel);     //~1415I~//~@@@@R~
//            if (v!=null)                                           //~1415I~//~@@@@R~
//            {                                                      //~1415I~//~@@@@R~
//                v.setVisibility(View.GONE); //disappear            //~1415I~//~@@@@R~
//                if (Dump.Y) Dump.println("Frame:setGoFrameVisibility set GONE layoutid(NavigationPanel)="+Integer.toHexString(framelayoutresourceid));//~1506R~//~@@@@R~
//            }                                                      //~1415I~//~@@@@R~
//        }                                                          //~1415I~//~@@@@R~
//    }                                                              //~1415I~//~@@@@R~
//*********                                                        //~1111I~
//*for dialog                                                      //~1111I~
//*********                                                        //~1111I~
    public Frame(int Pid)                                          //~1111R~
    {                                                              //~1111I~
        layout=(LinearLayout)AG.inflater.inflate(Pid,null);                //~1111I~//~1113R~
    }                                                              //~1111I~
//*********                                                        //~1311I~
    public void setFrameType(Canvas Pcanvas)                   //~1310I~//~1311I~
    {                                                              //~1310I~//~1311M~
    	boardCanvas=Pcanvas;                                            //~1310I~//~1311I~
      if (Pcanvas!=null)                                           //~@@@2I~
      {                                                            //~@@@2I~
        isBoardFrame=true;                                         //~1311I~
        if (Dump.Y) Dump.println("Frame:Canvas created="+((Object)Pcanvas).toString());//~1506R~
      }                                                            //~@@@2I~
      else                                                         //~@@@2I~
        isBoardFrame=false;                                        //~@@@2I~
    }                                                              //~1310I~//~1311M~
//******************************************************************//~1217I~
//* from AView for mainFrame 1st Tab                           //~1217R~//~@@@@R~
//*****************************************************************//~1217I~
    private void initMainFrame()                          //~1125I~//~1217R~//~1218R~
    {                                                              //~1125I~//~1217R~
    //***********                                                  //~1125I~//~1217R~
    	MainFrame=this;                                            //~1218I~
        Window.pushFrame(this);                                    //~1218I~
//        AG.ajagov.initMainFrameTab(this);                          //~1218I~//~@@@@R~
        if (framemenubar!=null)        //null when setLayout then setMenuBar//~1125R~//~1217R~//~1218R~
        {                                                      //~1125I~//~1217R~//~1218R~
            framemenubar.setMenuBar(this);	//registerMenuBar                     //~1125I~//~1217R~//~1218R~//~1402R~
        }                                                          //~1125I~//~1217R~//~1218R~
    }                                                              //~1217R~
//******************************************************************//~1125I~
//* menu                                                           //~1125I~
//******************************************************************//~1125I~
    public void setMenuBar(MenuBar Pmenubar)                       //~1125I~
    {                                                              //~1125I~
        if (Pmenubar!=null) //null when from closeframe:doclose()  //~1125R~
        {                                                          //~1125I~
        	framemenubar=Pmenubar;                                 //~1125I~
            if (framelayoutview!=null)  //null when setMenu then setLayout//~1125R~
	            Pmenubar.setMenuBar(this);                         //~1125R~
        }                                                          //~1125I~
    }                                                              //~1125I~
    public void addWindowListener(WindowListener Pwl)              //~1125I~
    {                                                              //~1125I~
    	windowlistener=Pwl;                                        //~1128I~
        if (Pwl instanceof CloseFrame)      //CloseFrame pass "this" as WindowListener//~1330R~
        {                                                          //~1325I~//~1330R~
            frameDoActionListener=(DoActionListener)Pwl;                //~1325I~//~1330R~
        }                                                          //~1325I~//~1330R~
    }                                                              //~1125I~
    public void addKeyListener(KeyListener Pkl)                    //~1127I~
    {                                                              //~1127I~
    	if (framelayoutview!=null)                                       //~1127I~
        {                                                          //~1127I~
        	AKey.addKeyListener(framelayoutview,Pkl);           //~1127I~//~@@@@R~
            framekeylistener=Pkl;   //call component listenr the frame listener like as awt//~1427I~
        }                                                          //~1127I~
    }                                                              //~1127I~
	public void show()                                             //~1128I~
    {                                                              //~1128I~
    	if (setcontentview_at_show)                                //~1217I~
        {                                                          //~1217I~
    		setcontentview_at_show=false;                          //~1217I~
        	Window.pushFrame(this);	//inflate and push             //~1218I~
            if (Dump.Y) Dump.println("Frame:show push frame");     //~1506R~
        }                                                          //~1217I~
    	setVisible(true);                                           //~1128I~
    }                                                              //~1128I~
	public void setVisible(boolean Pvisible)                       //~1128I~
    {                                                              //~1128I~
    	if (Pvisible)                                              //~1327I~
            if (setcontentview_at_show) //not yet shown                //~1221I~//~1327R~
            {                                                          //~1221I~//~1327R~
                if (Dump.Y) Dump.println("Frame:setvisible push frame");     //~1221I~//~1506R~
                show();                 //set ContentView              //~1221I~//~1327R~
                return;                                                //~1221I~//~1327R~
            }                                                          //~1221I~//~1327R~
    	Window.setVisible(this,Pvisible);                          //~1128I~
    }                                                              //~1128I~
	public void dispose()                                          //~1124I~//~1128I~
    {                                                              //~1124I~//~1128I~
        if (Dump.Y) Dump.println("Frame:dispose current dispose status="+disposed+",frame="+this.toString());//~1516R~
        if (disposed)                                              //~1516I~
        	return;                                                //~1516I~
        disposed=true;                                             //~1516I~
        if (Dump.Y) Dump.println("Frame:dispose current at show status="+setcontentview_at_show);//~1516I~
    	if (!setcontentview_at_show)	//already setContentView   //~1217I~
    		Window.popFrame(this);		//callback onDestroy();frame may be intermediate(GMPConnection)                                       //~1124I~//~1128I~//~1217R~//~1314R~//~1512R~
    }                                                              //~1124I~//~1128I~
//*********************************************                    //~1503I~
//*from window after set contentview by Back key                   //~1503I~
//*********************************************                    //~1503I~
	public void onRestore()                                        //~1503I~
    {                                                              //~1503I~
    	View v;                                                    //~1503I~
    //*****************                                            //~1503I~
    	if (Dump.Y) Dump.println("Frame.onRestore layoutid="+Integer.toHexString(framelayoutresourceid));//~1506R~
        AG.setCurrentFrame(this);	//Canvas:ondraw chk this       //~@@@@I~
        switch(framelayoutresourceid)                              //~1504R~
        {                                                          //~1504R~
//        case AG.frameId_ConnectionFrame:                           //~1504R~//~@@@@R~
////          v=AG.findViewById(R.id.TextField1);                    //~1504R~//~v108R~//~@@@@R~
////          if (Dump.Y) Dump.println("ConnFrame focusable="+v.isFocusable()+",touch="+v.isFocusableInTouchMode()+",isfocus="+v.isFocused());//~1506R~//~v108R~//~@@@@R~
////          v.requestFocus();                                    //~1504R~//~v108R~//~@@@@R~
////          framelayoutview.requestFocus();                        //~v108R~//~@@@@R~
//            requestFocus(); //component:OnUiThread                 //~v108M~//~@@@@R~
//            requestFocusFromTouch();    //run on unithread         //~v108R~//~@@@@R~
////          v=AG.findViewById(framelayoutview,R.id.Viewer);        //~v108R~//~@@@@R~
////          if (v!=null)                                           //~v108R~//~@@@@R~
////              v.requestFocus(); //connectionFrame:windowOpened call requestFocus to InputField//~v108R~//~@@@@R~
//            break;                                                 //~1504R~//~@@@@R~
        case AG.frameId_MainFrame:                                 //~@@@@I~
            frameDoActionListener.doAction(AG.resource.getString(R.string.ActionRestoreFrame));	//change button text//~@@@@I~//~@@@2R~
            break;                                                 //~@@@@I~
//        case AG.frameId_ConnectedGoFrame:                          //~v108I~//~@@@2R~
//            requestFocus();     //component:OnUiThread             //~v108R~//~@@@2R~
//            requestFocusFromTouch();    //run on unithread         //~v108I~//~@@@2R~
//            break;                                                 //~v108I~//~@@@2R~
        }                                                          //~1504R~
		if (windowlistener!=null)                                  //~1503I~
        {                                                          //~1503I~
			setVisible(this,true);	//restore=open;execute requestFocus()//~1503R~
        }                                                          //~1503R~
    }                                                              //~1503I~
//*********************************************                    //~1424I~
//*from window before contentview updated                          //~1424R~
	public void onDestroy()                                        //~1402I~
    {                                                              //~1402I~
        isDestroyed=true;                                          //~1503M~
        if (Dump.Y) Dump.println("Frame:onDestroy this="+this.toString());//~1513I~
        if (boardCanvas!=null)                                     //~1422R~
        	boardCanvas.stopThread();                              //~1422I~
//        if (windowlistener!=null                                   //~1512I~//~@@@@R~
//        &&  windowlistener instanceof GMPConnection                //~1512I~//~@@@@R~
//        )                                                          //~1512I~//~@@@@R~
//        {                                                          //~1512I~//~@@@@R~
//            Dialog dlg=AG.currentDialog;                           //~1512I~//~@@@@R~
//            if (dlg!=null                                           //~1512I~//~@@@@R~
//            &&  dlg.parentFrame==this                              //~1512I~//~@@@@R~
//            &&  dlg.shown                                          //~1512I~//~@@@@R~
//            )                                                      //~1512I~//~@@@@R~
//                dlg.dismiss();                                     //~1512I~//~@@@@R~
//        }                                                          //~1512I~//~@@@@R~
    }                                                              //~1402I~
//*********************************************                    //~1424I~
//*from window after contentview updated                           //~1424I~
	public void onDestroy2()                                       //~1424I~
    {                                                              //~1424I~
        if (framemenubar!=null)        //null when setLayout then setMenuBar//~1424M~
        {                                                          //~1424M~
            framemenubar.resetMenuBar(this);	//remove from menubarlist//~1424M~
		    framemenubar=null;                                     //~1424M~
        }                                                          //~1424M~
    }                                                              //~1424I~
//*********************************************                    //~1424I~
//*for rene.CloseFrame                                             //~1213I~
	public int getExtendedState()                                  //~1213I~
    {                                                              //~1213I~
    	return 0;                                                  //~1213I~
    }                                                              //~1213I~
	public void setExtendedState(int Pmaximized)                   //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
	public void toFront()                                          //~1213I~
    {                                                              //~1213I~
    }                                                              //~1213I~
    public void repaint()                                          //~1215I~
    {                                                              //~1215I~
    }                                                              //~1215I~
	public Dimension getSize()                                     //~1117I~//~1216I~
    {                                                              //~1117I~//~1216I~
    	int x,y;                                                   //~1216I~
    	if (framelayoutview==null)                                 //~1216I~
        	x=y=0;                                                 //~1216I~
        else                                                       //~1216I~
        {                                                          //~1216I~
    		x=framelayoutview.getMeasuredWidth();                  //~1216R~
    		y=framelayoutview.getMeasuredHeight();                 //~1216I~
        }                                                          //~1216I~
        if (Dump.Y) Dump.println("Frame getsize x="+x+",y="+y);    //~1506R~
    	return new Dimension(x,y);                                 //~1216I~
    }                                                              //~1117I~//~1216I~
}//class                                                           //~1415R~
