//*CID://+1Ah0R~: update#= 264;                                    //~1Ab0R~//+1Ah0R~
//**********************************************************************//~1107I~
//1Ah0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //+1Ah0I~
//1Ab0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //~1Ab0I~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//1A4k 2014/12/04 memory leak:DAL_filemenu is continue to refer FGF after FGF closed(DAL_playmenu is MainFrame)//~1A4kI~
//1A43 2014/10/03 actionBAr as alternative of menu button for api>=11//~1A43I~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//1A33 2013/04/17 load tsumego file on freeboard                   //~1A33I~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A27 2013/03/25 File button --> Suspend button on Local/Remote board//~1A27I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A21 2013/03/22 File Dialog on Free Board                        //~1A21I~
//1A11 2013/03/08 playMenu                                         //~1A11I~
//1091:121124 Menubar list OutOfBoundsException                    //~v109I~
//1078:121208 add "menu" to option menu if high resolusion         //~v107I~
//1076:121208 drop debugtrace menu item if release version         //~v107I~
//V104:121109 onContextMenuItemClosed sheduled before submenu display,NPE abend//~v104I~
//**********************************************************************//~v104I~
//*Menu                                                            //~v104R~
//**********************************************************************//~1107I~
package com.Asgts;                                         //~1107R~  //~1108R~//~1109R~//~@@@@R~

import jagoclient.Dump;
import jagoclient.FreeGoFrame;
import jagoclient.MainFrame;
import jagoclient.gui.DoActionListener;
import jagoclient.dialogs.SayDialog;

import com.Asgts.awt.Container;
import com.Asgts.awt.MenuBar;                                      //~@@@@R~
import com.Asgts.Alert;                                            //~@@@@R~
import com.Asgts.AG;                                               //~@@@@R~


import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
//**********************************************************************//~1107I~
public class AMenu                                             //~1310R~//~@@@@R~
		 implements AlertI,  DoActionListener                 //~1507R~//~@@@@R~
{                                                                  //~0914I~
	DoActionListener DAL_PlayMenu;                                 //~1A11I~
	DoActionListener DAL_FileMenu;                                 //~1A21I~
//    DoActionListener DAL_FileMenuLGF;                              //~1A22I~//~1A27R~
	View viewPlayMenu;                                             //~1A11I~
	View viewFileMenu;                                             //~1A21I~
//    View viewFileMenuLGF;                                          //~1A22I~//~1A27R~
//	static private ArrayList<MenuBar> menubarlist;                 //~1425R~
	public View menuRegisteredView;                                //~1425R~
//	private boolean swPopupSubmenu;                                //~1425R~
//	private boolean swShowRequest;                                 //~1427I~
//    private boolean listerPopup;                                   //~1425R~
//    private MenuBar currentMenuBar;                                //~1425R~
                                                                   //~1314I~
	private static String []menuDesc;                              //~1425R~
    public static final int  MENU_STOP=0;                         //~1314R~//~1326R~
//  public static final int  MENU_CLOSE=1;                         //~1326I~//~@@@@R~
//  public static final int  MENU_HELP=2;                          //~1314I~//~1412R~//~@@@@R~
//    public static final int  MENU_CTR=3;                           //~1314I~//~1412R~//~v107R~
//  public static final int  MENU_MENU=3;                          //~v107I~//~@@@@R~
//  public static final int  MENU_CTR=4;                           //~v107I~//~@@@@R~
//  public static final int  MENUMENU_SIZE=700;                    //~v107R~//~@@@@R~
    public static final int  MENU_CANCEL=1;                        //~@@@@R~
    public static final int  MENU_MSG=2;                           //~@@@@I~
    public static final int  MENU_CTR=3;                           //~@@@@R~
                                                                   //~1411I~
//    private static final String HELPTEXT_AJAGOC="aboutAsgts";    //~@@@@R~
//  private static String HELPITEM_AJAGOC="About Android Version";//~1411I~//~v107R~
//    private static String HELPITEM_AJAGOC="Asgts?";               //~v107I~//~@@@@R~
                                                                   //~1328I~
    public static final int  POPUPSUBMENUID=0x80;                  //~1328I~
    public static final int  AJAGOHELPMENUID=0x7f00;               //~1411I~
	private static final int menuId[]={                                  //~1314I~//~v107R~
				MENU_STOP,                                        //~1314R~//~1326R~
//  			MENU_CLOSE,                                        //~1326I~//~@@@@R~
//              MENU_HELP,                                         //~1314I~//~@@@@R~
    			MENU_CANCEL,                                       //~@@@@I~
                MENU_MSG,                                          //~@@@@I~
//  			MENU_MENU,                                         //~v107I~//~@@@@R~
				MENU_CTR};                                         //~1314I~
	private static final int icons[]={                                   //~1314I~//~v107R~
								android.R.drawable.ic_delete,      //~1412R~
//  							android.R.drawable.ic_menu_close_clear_cancel,//~1404I~//~@@@@R~
//  							android.R.drawable.ic_menu_help,   //~1314I~//~@@@@R~
    							android.R.drawable.ic_menu_revert, //~@@@@I~
    							android.R.drawable.ic_dialog_email,//~@@@@I~
//  							R.drawable.ic_menu_moreoverflow,   //~v107R~//~@@@@R~
								0							};     //~1314I~
//******************                                               //~1121I~
	public AMenu()                                             //~1107R~//~@@@@R~
    {                                                              //~1107I~
//    	menubarlist=new ArrayList<MenuBar>();                      //~1121I~
		showMenuButton();                                          //~1A40I~
    }                                                              //~1107I~
//*****************************                                               //~1121I~//~1122R~
//*from component:setMenu()-->menubar-->                           //~1524R~
//*****************************                                    //~1122I~
    public void registerMenuBar(MenuBar Pmenubar)                  //~1123I~
    {                                                              //~1123I~
//        View view;                                                 //~1307I~//~@@@@R~
//    //*********                                                    //~1307I~//~@@@@R~
//        Frame frame=Pmenubar.frame;                                //~1125I~//~@@@@R~
//        if (Pmenubar.childView!=null)   //from Lister for Who/Games//~1411R~//~@@@@R~
//            view=Pmenubar.childView;                               //~1307R~//~@@@@R~
//        else                                                       //~1307I~//~@@@@R~
//        {                                                          //~1331I~//~@@@@R~
//            view=frame.framelayoutview;                                 //~1125I~//~1307R~//~@@@@R~
//            frame.contextMenuView=view; //used by showContextMenu from optionmenu                                //~1314I~//~1331R~//~@@@@R~
//        }                                                          //~1331I~//~@@@@R~
//        Pmenubar.relatedView=view;                                 //~1331I~//~@@@@R~
//        Pmenubar.seqno=menubarlist.size();                          //~1123I~//~@@@@R~
//        Pmenubar.frameid=view.getId();                             //~1125I~//~@@@@R~
//        AG.activity.registerForContextMenu(view); //request callback onCreateContextMenu()//~1123I~//~1125R~//~@@@@R~
//        menubarlist.add(Pmenubar);                                 //~1123I~//~@@@@R~
//        if (Dump.Y) Dump.println("Ajagomenu:add menubar newctr="+menubarlist.size()+":"+((Object)Pmenubar).toString());//~1506R~//~@@@@R~
//        if (Dump.Y) Dump.println("Ajagomenu:register contextmenu frame="+frame.framename+",frameresourceid="+Integer.toHexString(frame.framelayoutresourceid)+//~1125I~//~1506R~//~@@@@R~
//                                ",registeredView id="+Integer.toString(view.getId())+//~1306R~//~@@@@R~
//                                "menubar.frameid="+Integer.toHexString(Pmenubar.frameid));//~1306I~//~1402R~//~@@@@R~
//        if (Dump.Y) Dump.println("Ajagomenu:register contextmenu view="+view.toString());//~1506R~//~@@@@R~
    }                                                              //~1123I~
//*****************************                                    //~1402I~
    public void removeMenuBar(MenuBar PmenuBar)                    //~1402I~
    {                                                              //~1402I~
//        int idx;                                                   //~v109I~//~@@@@R~
//    //*********                                                    //~1402I~//~@@@@R~
//        if (Dump.Y) Dump.println("Ajagomenu:remove menubar oldctr="+menubarlist.size()+":"+((Object)PmenuBar).toString());//~1506R~//~@@@@R~
////      menubarlist.remove(PmenuBar);                              //~1402I~//~v109R~//~@@@@R~
//        idx=PmenuBar.seqno;                                        //~v109I~//~@@@@R~
//        menubarlist.set(idx,null);                                 //~v109I~//~@@@@R~
//        if (Dump.Y) Dump.println("Ajagomenu:remove menubar newctr="+menubarlist.size());//~1506R~//~@@@@R~
    }                                                              //~1402I~
//******************                                               //~1121I~
//    private MenuBar findMenuBar(View Pview)                      //~1121I~//~@@@@R~
//    {                                                              //~1121I~//~@@@@R~
//        MenuBar menubar,menubarfound;                              //~1411R~//~@@@@R~
//        if (Pview instanceof TabHost)   //for Mainframe            //~1123I~//~@@@@R~
//            return menubarlist.get(0);                             //~1123I~//~@@@@R~
//        int viewid=Pview.getId();                                  //~1124I~//~@@@@R~
//        menubarfound=Window.getCurrentFrame().framemenubar;        //~1411R~//~@@@@R~
//        for (int ii=0;ii<menubarlist.size();ii++)                      //~1121I~//~@@@@R~
//        {                                                          //~1121I~//~@@@@R~
//            menubar=menubarlist.get(ii);                           //~1121I~//~@@@@R~
//            if (menubar==null)                                     //~v109I~//~@@@@R~
//                continue;                                          //~v109I~//~@@@@R~
//            if (Dump.Y) Dump.println("AMenu:findView viewId="+Integer.toHexString(viewid)+":menu frameid="+Integer.toHexString(menubar.frameid));//~1124I~//~1125R~//~1506R~//~@@@@R~
//            if (menubar.childView!=null)                           //~1411I~//~@@@@R~
//            {                                                      //~1411I~//~@@@@R~
//                if (menubar.childView==Pview)   //id is both Lister for Who and Games frame//~1411I~//~@@@@R~
//                {                                                  //~1411I~//~@@@@R~
//                    return menubar;                                //~1411I~//~@@@@R~
//                }                                                  //~1411I~//~@@@@R~
//            }                                                      //~1411I~//~@@@@R~
//        }                                                          //~1121I~//~@@@@R~
//        return menubarfound;                                       //~1411R~//~@@@@R~
//    }                                                              //~1121I~//~@@@@R~
////******************                                               //~1124I~//~@@@@R~
//    private MenuBar findMenuBar(int Pitemid)                       //~1124I~//~@@@@R~
//    {                                                              //~1124I~//~@@@@R~
//        int menubarid;                                             //~1124I~//~@@@@R~
//    //*********************                                        //~1124I~//~@@@@R~
//        menubarid=Pitemid>>24;                                     //~1124I~//~@@@@R~
//        if (menubarlist.size()<menubarid)                          //~v109R~//~@@@@R~
//            return null;                                           //~v109I~//~@@@@R~
//        return menubarlist.get(menubarid-1);                       //~1124I~//~@@@@R~
//    }                                                              //~1124I~//~@@@@R~
//*************************************************                //~1122R~
//*menu created at requested by long pressing                   //~1122I~//~v107R~
//*************************************************                //~1122I~
    public void onCreateContextMenu(ContextMenu Pmenu,View Pview,ContextMenuInfo Pinfo)//~1121I~
    {                                                                  //~1107I~//~1121R~
        if (Dump.Y) Dump.println("AMenu:onCreateContextMenu View="+((Object)Pview).toString());//~1A11I~
        if (Pview==viewPlayMenu)                                   //~1A11I~
        {                                                          //~1A11R~
            if (AG.screenDencityMdpiSmallV) //mdoi & height<=320   //~1A40R~
            {                                                      //~1A40I~
            	setMenuItemHeight(Pmenu,Pview);                    //~1A40I~
                return;                                            //~1A40I~
            }                                                      //~1A40I~
        	Pmenu.setHeaderTitle(AG.resource.getString(R.string.Title_PlayMenu));//~1A11I~
        	MenuInflater inf=AG.activity.getMenuInflater();        //~1A11I~
            inf.inflate(R.menu.mainframe,Pmenu);                    //~1A11I~
        	if (Dump.Y) Dump.println("AMenu:onCreateContextMenu mainframe");//~1A11I~
	        if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android4        //~1A6sI~//~1Ab0I~
	        {                                                          //~1A90I~//~1Ab0I~
	            MenuItem wifidirect=Pmenu.findItem(R.id.MenuitemWiFiDirect);//~1Ab0I~
                if (wifidirect!=null)                              //~1Ab0I~
                	wifidirect.setEnabled(false);                  //~1Ab0I~
	        }                                                          //~1A90I~//~1Ab0I~
        }                                                          //~1A11I~
        else                                                       //~1A21I~
        if (Pview==viewFileMenu)                                   //~1A21I~
        {                                                          //~1A21I~
        	Pmenu.setHeaderTitle(AG.resource.getString(R.string.Title_FileMenu));//~1A21I~
        	MenuInflater inf=AG.activity.getMenuInflater();        //~1A21I~
            FreeGoFrame fgf=(FreeGoFrame)DAL_FileMenu;             //~1A33I~
          if (fgf.FB.swFreeBoardStarted)                           //~1A33I~
            inf.inflate(R.menu.filedialog,Pmenu);                  //~1A21I~
          else                                                     //~1A33I~
            inf.inflate(R.menu.filedialogtsumego,Pmenu);         //~1A33I~
        	if (Dump.Y) Dump.println("AMenu:onCreateContextMenu freeboard");//~1A21I~
        }                                                          //~1A21I~
//        else                                                       //~1A22I~//~1A27R~
//        if (Pview==viewFileMenuLGF)                                //~1A22I~//~1A27R~
//        {                                                          //~1A22I~//~1A27R~
//            Pmenu.setHeaderTitle(AG.resource.getString(R.string.Title_FileMenu));//~1A22I~//~1A27R~
//            MenuInflater inf=AG.activity.getMenuInflater();        //~1A22I~//~1A27R~
//            inf.inflate(R.menu.filedialogcgf,Pmenu);               //~1A22R~//~1A27R~
//            if (Dump.Y) Dump.println("AMenu:onCreateContextMenu LGF");//~1A22I~//~1A27R~
//        }                                                          //~1A22I~//~1A27R~
    }                                                                  //~1107I~//~1121R~
////****************************************************             //~1123I~//~@@@@R~
    public boolean onContextItemSelected(MenuItem Pitem)           //~1121R~//~@@@@R~
    {                                                              //~1121I~//~@@@@R~
        boolean rc=true;                                           //~1121I~//~@@@@R~
//        Object awtitem;                                          //~@@@@R~
//        MenuBar menubar;                                           //~1307I~//~@@@@R~
//        //************************                                     //~1124I~//~@@@@R~
//        int itemid=Pitem.getItemId();                            //~@@@@R~
//        if (Dump.Y) Dump.println("AMenu:menubar itemid="+Integer.toHexString(itemid));//~1121R~     //~1123R~//~1124I~//~1506R~//~@@@@R~
//        menubar=findMenuBar(itemid);                                //~1307I~//~@@@@R~
//        if (menubar==null)  //internal logic err                   //~v109I~//~@@@@R~
//            return true;                                           //~v109I~//~@@@@R~
//        if ((itemid & AJAGOHELPMENUID)==AJAGOHELPMENUID)           //~1411I~//~@@@@R~
//        {                                                          //~1411I~//~@@@@R~
//            helpAsgts();                                          //~1411I~//~@@@@R~
//            return true;                                           //~1411I~//~@@@@R~
//        }                                                          //~1411I~//~@@@@R~
//        awtitem=findItem(menubar,itemid);                          //~1124R~//~1211R~//~1307R~//~@@@@R~
//        if ((itemid & 0xff)==POPUPSUBMENUID)    //3rd level submenu starter//~1328R~//~@@@@R~
//        {                                                          //~1211M~//~@@@@R~
//            if (Dump.Y) Dump.println("AMenu:Sub of Submenu");                  //~1211M~//~1506R~//~@@@@R~
//            popupSubmenu((com.Asgts.awt.Menu)awtitem,itemid);            //~1211I~//~1328R~//~@@@@R~
//            showContextMenu();  //show submenu                     //~v104I~//~@@@@R~
//            return rc;                                             //~v104R~//~@@@@R~
//        }                                                          //~1211M~//~@@@@R~
//        try                                                        //~1309I~//~@@@@R~
//        {                                                          //~1309I~//~@@@@R~
//            rc=itemAction(menubar,Pitem,awtitem);                              //~1211R~//~1307R~//~1309R~//~@@@@R~
//        }                                                          //~1309I~//~@@@@R~
//        catch(Exception e)                                         //~1309I~//~@@@@R~
//        {                                                          //~1309I~//~@@@@R~
//            Dump.println(e,"AMenu:ContextItemnSelected Exception");       //~1309R~//~1402R~//~@@@@R~
//        }                                                          //~1309I~//~@@@@R~
                                                                   //~1A11I~
//        Object awtitem;                                          //~1A11I~
//        MenuBar menubar;                                         //~1A11I~
//        //************************                               //~1A11I~
        int itemid=Pitem.getItemId();                              //~1A11I~
        if (Dump.Y) Dump.println("AMenu:menubar itemid="+Integer.toHexString(itemid));//~1A11I~
        if (itemid==R.id.StudyBoard)                               //~1A11I~
        {                                                          //~1A11I~
        	if (DAL_PlayMenu!=null);	                           //~1A11I~
            	DAL_PlayMenu.doAction(AG.resource.getString(R.string.StudyBoard));//~1A11I~
        }                                                          //~1A11I~
        else                                                       //~1A11I~
        if (itemid==R.id.LocalGame)                                //~1A11I~
        {                                                          //~1A11I~
        	if (DAL_PlayMenu!=null);                               //~1A11I~
            	DAL_PlayMenu.doAction(AG.resource.getString(R.string.LocalGame));//~1A11I~
        }                                                          //~1A11I~
        else                                                       //~1A11I~
        if (itemid==R.id.RemoteGame)                               //~1A11I~
        {                                                          //~1A11I~
        	if (DAL_PlayMenu!=null);                               //~1A11I~
            	DAL_PlayMenu.doAction(AG.resource.getString(R.string.RemoteGame));//~1A11I~
        }                                                          //~1A11I~
        else                                                       //~1A11I~
        if (itemid==R.id.RemoteIP)                                 //~1A11I~
        {                                                          //~1A11I~
        	if (DAL_PlayMenu!=null);                               //~1A11I~
            	DAL_PlayMenu.doAction(AG.resource.getString(R.string.RemoteIP));//~1A11I~
        }
        else
        if (itemid==R.id.MenuitemWiFiDirect)                       //~1Ab0I~
        {                                                          //~1Ab0I~
        	if (DAL_PlayMenu!=null);                               //~1Ab0I~
            	DAL_PlayMenu.doAction(AG.resource.getString(R.string.MenuitemWiFiDirect));//~1Ab0I~
        }                                                          //~1Ab0I~
        else                                                       //~1Ab0I~
        if (itemid==R.id.Cancel)                                   //~1A11I~
        {                                                          //~1A11I~
        }                                                          //~1A11I~
        else                                                       //~1A21I~
        if (itemid==R.id.LoadFile)                                 //~1A21I~
        {                                                          //~1A21I~
        	if (DAL_FileMenu!=null)                               //~1A21I~
            	DAL_FileMenu.doAction(AG.resource.getString(R.string.LoadFile));//~1A21I~
        }                                                          //~1A22I~
        else                                                       //~1A33I~
        if (itemid==R.id.LoadNotesFile)                            //~1A33I~
        {                                                          //~1A33I~
        	if (DAL_FileMenu!=null)                                //~1A33I~
            	DAL_FileMenu.doAction(AG.resource.getString(R.string.LoadNotesFile));//~1A33I~
        }                                                          //~1A33I~
        else                                                       //~1A4sI~
        if (itemid==R.id.LoadNotesFileClipboard)                   //~1A4sI~
        {                                                          //~1A4sI~
        	if (DAL_FileMenu!=null)                                //~1A4sI~
            	DAL_FileMenu.doAction(AG.resource.getString(R.string.LoadNotesFileClipboard));//~1A4sI~
        }                                                          //~1A4sI~
//        else                                                     //~1A27I~
//        if (itemid==R.id.ReloadFile)                               //~1A22I~//~1A27R~
//        {                                                          //~1A22I~//~1A27R~
//            if (DAL_FileMenuLGF!=null)                            //~1A22I~//~1A27R~
//                DAL_FileMenuLGF.doAction(AG.resource.getString(R.string.ReloadFile));//~1A22R~//~1A27R~
//        }                                                          //~1A21I~//~1A27R~
        else                                                       //~1A2dI~
        if (itemid==R.id.ReplayFile)                               //~1A2dI~
        {                                                          //~1A2dI~
        	if (DAL_PlayMenu!=null);                               //~1A2dI~
                DAL_PlayMenu.doAction(AG.resource.getString(R.string.ReplayFile));//~1A2dR~
        }                                                          //~1A2dI~
        else                                                       //~1A21I~
        if (itemid==R.id.ReplayFileClipboard)                      //~1A4sI~
        {                                                          //~1A4sI~
        	if (DAL_PlayMenu!=null);                               //~1A4sI~
                DAL_PlayMenu.doAction(AG.resource.getString(R.string.ReplayFileClipboard));//~1A4sI~
        }                                                          //~1A4sI~
        else                                                       //~1A4sI~
        if (itemid==R.id.SaveFile)                                 //~1A21I~
        {                                                          //~1A21I~
        	if (DAL_FileMenu!=null)                               //~1A21I~
            	DAL_FileMenu.doAction(AG.resource.getString(R.string.SaveFile));//~1A21I~
        }                                                          //~1A21I~
//        else                                                       //~1A11I~//~1A27R~
//        if (itemid==R.id.SuspendGame)                              //~1A22I~//~1A27R~
//        {                                                          //~1A22I~//~1A27R~
//            if (DAL_FileMenuLGF!=null)                             //~1A22I~//~1A27R~
//                DAL_FileMenuLGF.doAction(AG.resource.getString(R.string.SuspendGame));//~1A22I~//~1A27R~
//        }                                                          //~1A22I~//~1A27R~
        else                                                       //~1A22I~
        if (itemid==R.id.CloseBoard)                               //~1A21I~
        {                                                          //~1A21I~
        	if (DAL_FileMenu!=null)                               //~1A21I~
            	DAL_FileMenu.doAction(AG.resource.getString(R.string.CloseBoard));//~1A21I~
        }                                                          //~1A21I~
        else                                                       //+1Ah0I~
        if (itemid==R.id.VsBonanza)                                //+1Ah0I~
        {                                                          //+1Ah0I~
        	if (DAL_PlayMenu!=null);                               //+1Ah0I~
                DAL_PlayMenu.doAction(AG.resource.getString(R.string.VsBonanza));//+1Ah0I~
        }                                                          //+1Ah0I~
        else                                                       //~1A21I~
        	rc=false;//~1A11I~
        return rc;                                                 //~1121R~//~@@@@R~
    }                                                              //~1121I~//~@@@@R~
//****************************************************             //~1211I~
//    public boolean itemAction(MenuBar Pmenubar,MenuItem Pitem,Object Pawtitem)      //~1211R~//~1307R~//~@@@@R~
//    {                                                              //~1211I~//~@@@@R~
//        boolean rc=true;                                           //~1211I~//~@@@@R~
//        CheckboxMenuItem awtchkboxitem;                          //~@@@@R~
//    //************************                                     //~1211I~//~@@@@R~
//        if (Pitem.isCheckable())                                   //~1211R~//~@@@@R~
//        {                                                          //~1211I~//~@@@@R~
//            awtchkboxitem=(CheckboxMenuItem)Pawtitem;              //~1211R~//~@@@@R~
//            if (Pitem.isChecked())                                 //~1211R~//~@@@@R~
//            {                                                      //~1211I~//~@@@@R~
//                Pitem.setChecked(false);                           //~1211R~//~@@@@R~
//                awtchkboxitem.setState(false);                     //~1211R~//~@@@@R~
//            }                                                      //~1211I~//~@@@@R~
//            else                                                   //~1211I~//~@@@@R~
//            {                                                      //~1211I~//~@@@@R~
//                Pitem.setChecked(true);                            //~1211R~//~@@@@R~
//                awtchkboxitem.setState(true);                      //~1211R~//~@@@@R~
//            }                                                      //~1211I~//~@@@@R~
//            if (awtchkboxitem.checkboxtranslator!=null)//ItemListener defined//~1211R~//~@@@@R~
//            {                                                      //~1211I~//~@@@@R~
//                awtchkboxitem.checkboxtranslator.itemStateChanged(new ItemEvent());//~1211R~//~@@@@R~
//            }                                                      //~1211I~//~@@@@R~
//        }                                                          //~1211I~//~@@@@R~
//        if (Pawtitem instanceof MenuItemAction)                    //~1211R~//~@@@@R~
//        {                                                          //~1211I~//~@@@@R~
//            if (Dump.Y) Dump.println("AMenu:MenuitemAction");                  //~1211I~//~1506R~//~@@@@R~
//            ActionEvent.actionPerformedMenu(Pmenubar,(com.Asgts.awt.MenuItem)Pawtitem);  //execute DoAction//~1408I~//~@@@@R~
//        }                                                          //~1211I~//~@@@@R~
//        return rc;                                                 //~1211I~//~@@@@R~
//    }                                                              //~1211I~//~@@@@R~
//***********************                                          //~1124I~
//    private Object findItem(MenuBar Pmenubar,int Pitemid)          //~1124R~//~@@@@R~
//    {                                                              //~1124I~//~@@@@R~
//        int menuid,itemid,nestid;                        //~1124I~//~@@@@R~
//        com.Asgts.awt.Menu awtmenu,awtmenunest;                   //~1124I~//~@@@@R~
//        Object awtitem;                                            //~1524R~//~@@@@R~
//    //*******************                                          //~1124I~//~@@@@R~
//        menuid=(Pitemid>>16) & 0xff;                               //~1124I~//~@@@@R~
//        itemid=(Pitemid>>8)  & 0xff;                               //~1124I~//~@@@@R~
//        nestid=Pitemid & 0xff;                                     //~1124I~//~@@@@R~
//        awtmenu=Pmenubar.getMenu(menuid-1);                        //~1124R~//~@@@@R~
//        if (itemid!=0)                                             //~1124I~//~@@@@R~
//        {                                                          //~1124I~//~@@@@R~
//            awtitem=awtmenu.getItem(itemid-1);                        //~1124I~//~@@@@R~
//            if (nestid!=0 && nestid!=POPUPSUBMENUID)               //~1124I~//~1211R~//~1328R~//~@@@@R~
//            {                                                      //~1124I~//~1211R~//~1328R~//~@@@@R~
//                awtmenunest=(com.Asgts.awt.Menu) awtitem;                               //~1124I~//~1211R~//~1328R~//~@@@@R~
//                awtitem=awtmenunest.getItem(nestid-1);             //~1124I~//~1211R~//~1328R~//~@@@@R~
//            }                                                      //~1124I~//~1211R~//~1328R~//~@@@@R~
//        }                                                          //~1124I~//~@@@@R~
//        else                                                       //~1124I~//~@@@@R~
//            awtitem=awtmenu;                                       //~1124I~//~@@@@R~
//        return awtitem;                                          //~@@@@R~
//    }//~1124I~                                                   //~@@@@R~
//****************************************************             //~1307I~
    public void onContextMenuClosed(Menu Pmenu)                    //~1307I~//~@@@@R~
    {                                                              //~1307I~//~@@@@R~
//        listerPopup=false;                                         //~1307I~//~@@@@R~
//        if (swPopupSubmenu)                                        //~1328R~//~@@@@R~
//            showContextMenu();  //show submenu                     //~1328I~//~@@@@R~
//      else                                                       //~v104R~//~@@@@R~
//          currentMenuBar=null;                                       //~1307I~//~v104R~//~@@@@R~
    }                                                              //~1124I~//~@@@@R~
////****************************************************             //~1123I~//~@@@@R~
////*create menu from stacked Menu/submenu/item                      //~1123I~//~@@@@R~
////*itemid=menubarid(8)+menuid(8)+item(8)+level3 item(8);start from 1//~1123I~//~@@@@R~
////****************************************************             //~1123I~//~@@@@R~
//    private void createMenu(ContextMenu Pcontextmenu,MenuBar Pmenubar)//~1123I~//~@@@@R~
//    {                                                              //~1123I~//~@@@@R~
//        com.Asgts.awt.Menu awtmenu;                              //~@@@@R~
//        com.Asgts.awt.Menu helpMenu;                              //~1411I~//~@@@@R~
//        Object awtitem;                                            //~1123I~//~@@@@R~
//        MenuItem androiditem;                                      //~1123I~//~@@@@R~
//        SubMenu  androidsubmenu;                                   //~1123I~//~@@@@R~
//        String itemname;                                           //~1123I~//~@@@@R~
//        int menubarid,menuid,itemctr,itemid,itemid2;               //~1524R~//~@@@@R~
//        int none=android.view.Menu.NONE;                                         //~1123I~//~@@@@R~
//        boolean swHelpOnly;                                        //~1411I~//~@@@@R~
////*******************                                              //~1123I~//~@@@@R~
//        swHelpOnly=Pmenubar.getShowHelpOnly();                     //~1411I~//~@@@@R~
//        Pmenubar.setShowHelpOnly(false);                           //~1411R~//~@@@@R~
//        helpMenu=Pmenubar.helpMenu;                                //~1411I~//~@@@@R~
//        if (Dump.Y) Dump.println("AMenu:createMenu helponly="+swHelpOnly);//~1506R~//~@@@@R~
//        menubarid=(Pmenubar.seqno+1)<<24;                          //~1123R~//~@@@@R~
//        for (int ii=0;;ii++)                                       //~1123I~//~@@@@R~
//        {                                                          //~1123I~//~@@@@R~
//            awtmenu=Pmenubar.getMenu(ii);                  //~1123R~//~@@@@R~
//            if (awtmenu==null)                                     //~1123I~//~@@@@R~
//                break;                                             //~1123I~//~@@@@R~
//            menuid=(ii+1)<<16;                                     //~1123R~//~@@@@R~
//            itemname=awtmenu.name;                               //~@@@@R~
//            itemid=menubarid+menuid;//~1123I~                    //~@@@@R~
//            if (swHelpOnly)                                        //~1412M~//~@@@@R~
//            {                                                      //~1412M~//~@@@@R~
//                if (awtmenu!=helpMenu)                             //~1412M~//~@@@@R~
//                    continue;                                      //~1412M~//~@@@@R~
//                createHelpOnlyMenu(Pcontextmenu,Pmenubar,helpMenu,itemid);//~1412R~//~@@@@R~
//                return;                                            //~1412M~//~@@@@R~
//            }                                                      //~1412M~//~@@@@R~
//            itemid2=1<<8;                                          //~1123I~//~@@@@R~
//            itemctr=awtmenu.getItemCtr();                          //~1412M~//~@@@@R~
//            if (itemctr==0)                                        //~1123I~//~@@@@R~
//            {                                                      //~1123I~//~@@@@R~
//            //*menu item under menubar                             //~1123I~//~@@@@R~
//                itemid+=itemid2;                                   //~1123I~//~@@@@R~
//                androiditem=Pcontextmenu.add(none/*group id*/,itemid,none/*order*/,itemname);//~1123I~//~@@@@R~
//                setItem(androiditem,awtmenu);                      //~1124I~//~@@@@R~
//                if (Dump.Y) Dump.println("AMenu:menuitem "+Integer.toHexString(ii)+"="+itemname);//~1123R~//~1506R~//~@@@@R~
//            }                                                      //~1123I~//~@@@@R~
//            else                                                   //~1123I~//~@@@@R~
//            {                                                      //~1123I~//~@@@@R~
//            //*menu having submenu/menu item                       //~1123R~//~@@@@R~
//                androidsubmenu=Pcontextmenu.addSubMenu(none/*group id*/,itemid,none,itemname);//~1123I~//~@@@@R~
//                setMenu((Menu)androidsubmenu,awtmenu);             //~1124I~//~@@@@R~
//                if (Dump.Y) Dump.println("submenu "+Integer.toHexString(itemid)+"="+itemname);//~1123R~//~1506R~//~@@@@R~
//                setAsgtsHelpMenuItem(Pcontextmenu,Pmenubar,awtmenu,androidsubmenu,itemid);//~1412R~//~@@@@R~
//                for (int jj=0;;jj++)                               //~1123R~//~@@@@R~
//                {                                                  //~1123I~//~@@@@R~
//                    itemid2=(itemid+((jj+1)<<8));                         //~1123I~//~@@@@R~
//                    awtitem=awtmenu.getItem(jj);                   //~1123R~//~@@@@R~
//                    if (awtitem==null)                             //~1123I~//~@@@@R~
//                        break;                                     //~1123I~//~@@@@R~
//                    if (awtitem instanceof com.Asgts.awt.Menu)                  //~1123I~//~@@@@R~
//                    {                                              //~1123I~//~@@@@R~
//                    //*submenu having submenu                      //~1123I~//~@@@@R~
//                        itemname=((com.Asgts.awt.Menu)awtitem).name+" +";//~1123I~//~1211R~//~@@@@R~
//                        if (Dump.Y) Dump.println("AMenu:menuitem "+Integer.toString(itemid2,16)+"="+itemname);//~1123I~//~1506R~//~@@@@R~
//                        androiditem=androidsubmenu.add(none/*group id*/,itemid2+POPUPSUBMENUID/*sub of sub id*/,none,itemname);//~1123R~//~1211R~//~1328R~//~@@@@R~
//                    }                                              //~1123I~//~@@@@R~
//                    else                                           //~1123I~//~@@@@R~
//                    {                                              //~1123I~//~@@@@R~
//                    //*menuitem under submenu                      //~1123I~//~@@@@R~
//                        itemname=((com.Asgts.awt.MenuItem)awtitem).name;//~1123I~//~@@@@R~
//                        if (Dump.Y) Dump.println("Ajagomenu:menuitem "+Integer.toString(itemid2,16)+"="+itemname);//~1123I~//~1506R~//~@@@@R~
//                        androiditem=androidsubmenu.add(none/*group id*/,itemid2,none,itemname);//~1123R~//~@@@@R~
//                        setItem(androiditem,awtitem);              //~1123I~//~@@@@R~
//                    }                                              //~1123I~//~@@@@R~
//                }                                                  //~1123I~//~@@@@R~
//            }                                                      //~1123I~//~@@@@R~
//                                          //~1123I~              //~@@@@R~
//        }                                                          //~1123I~//~@@@@R~
//    }                                                              //~1123I~//~@@@@R~
//****************************************************             //~1328I~
//*3rd level submenu                                               //~1328I~
//****************************************************             //~1328I~
//    private void createPopupSubmenu(ContextMenu Pcontextmenu,MenuBar Pmenubar,com.Asgts.awt.Menu Psubmenu)//~1328I~//~@@@@R~
//    {                                                              //~1328I~//~@@@@R~
//        com.Asgts.awt.Menu awtmenu;                               //~1328I~//~@@@@R~
//        com.Asgts.awt.MenuItem awtitem2;                          //~1328I~//~@@@@R~
//        MenuItem androiditem;                                      //~1328I~//~@@@@R~
//        String itemname,submenutitle;                                           //~1328I~//~@@@@R~
//        int itemid2,itemid3;//~1328I~                            //~@@@@R~
//        int none=android.view.Menu.NONE;                           //~1328I~//~@@@@R~
//    //*******************                                          //~1411R~//~@@@@R~
//        awtmenu=Psubmenu;                                          //~1328I~//~@@@@R~
//        itemid2=(Psubmenu.itemid & ~POPUPSUBMENUID);               //~1328R~//~@@@@R~
//        submenutitle=awtmenu.name;                                  //~1328I~//~@@@@R~
//        Pcontextmenu.setHeaderTitle(submenutitle);                  //~1328I~//~@@@@R~
//        for (int kk=0;;kk++)                                       //~1328I~//~@@@@R~
//        {                                                          //~1328I~//~@@@@R~
//            itemid3=itemid2+kk+1;                                  //~1328I~//~@@@@R~
//            awtitem2=(com.Asgts.awt.MenuItem)(awtmenu.getItem(kk));//~1328I~//~@@@@R~
//            if (awtitem2==null)                                    //~1328I~//~@@@@R~
//                break;                                             //~1328I~//~@@@@R~
//            itemname=awtitem2.name;                                //~1328I~//~@@@@R~
//            androiditem=Pcontextmenu.add(none/*groupid*/,itemid3,none/*order*/,itemname);//~1328I~//~@@@@R~
//            setItem(androiditem,awtitem2);                         //~1328I~//~@@@@R~
//        }                                                          //~1328I~//~@@@@R~
//    }                                                              //~1328I~//~@@@@R~
//****************************************************             //~1412I~
//*help submenu only                                               //~1412I~
//****************************************************             //~1412I~
//    private void createHelpOnlyMenu(ContextMenu Pcontextmenu,MenuBar Pmenubar,com.Asgts.awt.Menu Psubmenu,int Pitemid)//~1412I~//~@@@@R~
//    {                                                              //~1412I~//~@@@@R~
//        com.Asgts.awt.Menu awtmenu;                               //~1412I~//~@@@@R~
//        com.Asgts.awt.MenuItem awtitem2;                          //~1412I~//~@@@@R~
//        MenuItem androiditem;                                      //~1412I~//~@@@@R~
//        String itemname,submenutitle;                              //~1412I~//~@@@@R~
//        int itemid2;                                               //~1412I~//~@@@@R~
//        int none=android.view.Menu.NONE;                           //~1412I~//~@@@@R~
//    //*******************                                          //~1412I~//~@@@@R~
//        awtmenu=Psubmenu;                                          //~1412I~//~@@@@R~
//        submenutitle=awtmenu.name;                                 //~1412I~//~@@@@R~
//        Pcontextmenu.setHeaderTitle(submenutitle);                 //~1412I~//~@@@@R~
//        setAsgtsHelpMenuItem(Pcontextmenu,Pmenubar,Psubmenu,null,Pitemid);//~1412I~//~@@@@R~
//        for (int jj=0;;jj++)                                       //~1412I~//~@@@@R~
//        {                                                          //~1412I~//~@@@@R~
//            itemid2=Pitemid+((jj+1)<<8);                           //~1412R~//~@@@@R~
//            awtitem2=(com.Asgts.awt.MenuItem)(awtmenu.getItem(jj));//~1412I~//~@@@@R~
//            if (awtitem2==null)                                    //~1412I~//~@@@@R~
//                break;                                             //~1412I~//~@@@@R~
//            itemname=awtitem2.name;                                //~1412I~//~@@@@R~
//            if (Dump.Y) Dump.println("Ajagomenu:createHelpOnlyMenu name="+itemname);//~1506R~//~@@@@R~
//            androiditem=Pcontextmenu.add(none/*groupid*/,itemid2,none/*order*/,itemname);//~1412I~//~@@@@R~
//            setItem(androiditem,awtitem2);                         //~1412I~//~@@@@R~
//        }                                                          //~1412I~//~@@@@R~
//    }                                                              //~1412I~//~@@@@R~
//****************************************************             //~1411I~
//    private void setAsgtsHelpMenuItem(ContextMenu Pcontextmenu,MenuBar Pmenubar,com.Asgts.awt.Menu Psubmenu,SubMenu Pandroidsubmenu,int Pitemid)//~1412R~//~@@@@R~
//    {                                                              //~1411I~//~@@@@R~
//        int itemid2;                                               //~1411I~//~@@@@R~
//        int none=android.view.Menu.NONE;                           //~1411I~//~@@@@R~
//    //*******************                                          //~1411I~//~@@@@R~
////      if (Pmenubar.seqno!=0 || !Psubmenu.name.equals(Global.resourceString("Help")))//~1412R~//~@@@@R~
//        if (Pmenubar.seqno!=0 || !Psubmenu.name.equals(AG.resource.getString(R.string.Help)))//~@@@@R~
//            return;                                                //~1411I~//~@@@@R~
//        itemid2=Pitemid|AJAGOHELPMENUID;                           //~1412R~//~@@@@R~
//        if (Dump.Y) Dump.println("AMenu:add AjagoHelp itemid="+Integer.toHexString(itemid2));//~1506R~//~@@@@R~
//        if (Pandroidsubmenu!=null)                                 //~1411I~//~@@@@R~
//            Pandroidsubmenu.add(none/*groupid*/,itemid2,none/*order*/,HELPITEM_AJAGOC);//~1411R~//~@@@@R~
//        else                                                       //~1411I~//~@@@@R~
//            Pcontextmenu.add(none/*groupid*/,itemid2,none/*order*/,HELPITEM_AJAGOC);//~1411I~//~@@@@R~
//    }                                                              //~1411I~//~@@@@R~
//****************************************************             //~1306I~
//*create WhoFrame ActionMenu from stacked Menu/submenu/item       //~1306I~
//*itemid=menubarid(8)+menuid(8)+item(8)+level3 item(8);start from 1//~1306I~
//****************************************************             //~1306I~
//    private void createListerPopupMenu(ContextMenu Pcontextmenu,MenuBar Pmenubar,ContextMenuInfo Pinfo )//~1306I~//~1307R~//~@@@@R~
//    {                                                              //~1306I~//~@@@@R~
//        com.Asgts.awt.Menu awtmenu;                               //~1306I~//~@@@@R~
//        Object awtitem;                                            //~1306I~//~@@@@R~
//        MenuItem androiditem;                                      //~1306I~//~@@@@R~
//        String itemname;                                           //~1306I~//~@@@@R~
//        int menubarid,menuid,itemctr,itemid,itemid2;               //~1524R~//~@@@@R~
//        int none=android.view.Menu.NONE;                           //~1306I~//~@@@@R~
//        CharSequence selectedItem=null;                                  //~1307I~//~@@@@R~
//        AdapterContextMenuInfo info;                             //~@@@@R~
//        TextView tv;//~1307I~                                    //~@@@@R~
////*******************                                              //~1306I~//~@@@@R~
//        listerPopup=true;                                          //~1307M~//~@@@@R~
//                                                                   //~1307I~//~@@@@R~
//        if (Pinfo!=null)                                           //~1307I~//~@@@@R~
//        {                                                          //+1307I~//~1307I~//~@@@@R~
//            info=(AdapterContextMenuInfo)Pinfo;                    //~1307I~//~@@@@R~
//            if (info.targetView!=null)                             //~1307I~//~@@@@R~
//            {                                                      //~1307I~//~@@@@R~
//                tv=(TextView)info.targetView;                      //~1307I~//~@@@@R~
//                selectedItem=tv.getText();                         //~1307I~//~@@@@R~
//                Pcontextmenu.setHeaderTitle(selectedItem);         //~1307I~//~@@@@R~
//            }                                                      //~1307I~//~@@@@R~
//        }                                                          //~1307I~//~@@@@R~
//        menubarid=(Pmenubar.seqno+1)<<24;                          //~1306I~//~1307R~//~@@@@R~
//        awtmenu=Pmenubar.getMenu(0);                              //~1306I~//~1307R~//~@@@@R~
//        if (awtmenu==null)                                         //~1306I~//~1307R~//~@@@@R~
//            return;                                              //~1306I~//~1307R~//~@@@@R~
//        menuid=1<<16;                                         //~1306I~//~1307R~//~@@@@R~
//        itemctr=awtmenu.getItemCtr();                              //~1306I~//~1307R~//~@@@@R~
//        itemname=awtmenu.name;                                     //~1306I~//~1307R~//~@@@@R~
//        itemid=menubarid+menuid;                                   //~1306I~//~1307R~//~@@@@R~
//        itemid2=1<<8;                                              //~1306I~//~1307R~//~@@@@R~
//        if (itemctr!=0)                                            //~1306I~//~1307R~//~@@@@R~
//        {                                                          //~1306I~//~1307R~//~@@@@R~
//            for (int jj=0;;jj++)                                   //~1306I~//~1307R~//~@@@@R~
//            {                                                      //~1306I~//~1307R~//~@@@@R~
//                itemid2=(itemid+((jj+1)<<8));                      //~1306I~//~1307R~//~@@@@R~
//                awtitem=awtmenu.getItem(jj);                       //~1306I~//~1307R~//~@@@@R~
//                if (awtitem==null)                                 //~1306I~//~1307R~//~@@@@R~
//                    break;                                         //~1306I~//~1307R~//~@@@@R~
//                itemname=((com.Asgts.awt.MenuItem)awtitem).name;  //~1306I~//~1307R~//~@@@@R~
//                if (Dump.Y) Dump.println("AMenu:menuitem "+Integer.toHexString(itemid2)+"="+itemname);//~1306I~//~1307R~//~1506R~//~@@@@R~
//                androiditem=Pcontextmenu.add(none/*group id*/,itemid2,none,itemname);//~1306I~//~1307R~//~@@@@R~
//                setItem(androiditem,awtitem);                      //~1306I~//~1307R~//~@@@@R~
//            }                                                      //~1306I~//~1307R~//~@@@@R~
//        }                                                          //~1306I~//~1307R~//~@@@@R~
//    }                                                              //~1306I~//~@@@@R~
//***********************                                          //~1123I~
//    private void setMenu(Menu Pandroiditem,com.Asgts.awt.Menu Pmenu)       //~1123I~//~1124R~//~@@@@R~
//    {                                                              //~1123I~//~@@@@R~
//    //*******************                                          //~1124I~//~@@@@R~
//        if (Pmenu.font!=null)                                      //~1124I~//~@@@@R~
//        {                                                          //~1124I~//~@@@@R~
////          Pmenu.font.setFont((View)Pandroiditem);                            //~1124I~//~@@@@R~
////          Menu dose not support to change font                   //~1124I~//~@@@@R~
//        }                                                          //~1124I~//~@@@@R~
//    }                                                              //~1123I~//~@@@@R~
//***********************                                          //~1124I~
//    private void setItem(MenuItem Pandroiditem,Object Pitem)       //~1124I~//~@@@@R~
//    {                                                              //~1124I~//~@@@@R~
//        com.Asgts.awt.MenuItem awtitem;                           //~1124I~//~@@@@R~
//    //*******************                                          //~1124I~//~@@@@R~
//        awtitem=(com.Asgts.awt.MenuItem)Pitem;                    //~1124I~//~@@@@R~
//        if (awtitem instanceof CheckboxMenuItem                    //~1124I~//~@@@@R~
//        ||  awtitem instanceof CheckboxMenuItemAction              //~1124I~//~@@@@R~
//        )                                                          //~1124I~//~@@@@R~
//        {                                                          //~1124I~//~@@@@R~
//            Pandroiditem.setCheckable(true);                       //~1124I~//~@@@@R~
//            Pandroiditem.setChecked(((com.Asgts.awt.CheckboxMenuItem)awtitem).getState());           //~1124I~//~@@@@R~
//        }                                                          //~1124I~//~@@@@R~
//        if (awtitem.font!=null)                                    //~1124I~//~@@@@R~
//        {                                                          //~1124I~//~@@@@R~
////          font.setFont(Pandroiditem);                            //~1124I~//~@@@@R~
////          Menu dose not support to change font                   //~1124I~//~@@@@R~
//        }                                                          //~1124I~//~@@@@R~
//    }                                                              //~1124I~//~@@@@R~
//***********************                                          //~1211I~
//    private void popupSubmenu(com.Asgts.awt.Menu Psubmenu,int Pitemid)//~1328I~//~@@@@R~
//    {                                                              //~1328I~//~@@@@R~
//        swPopupSubmenu=true;    //onContextMenuClosed request recursive showContextMenu//~1328I~//~@@@@R~
//        currentMenuBar.popupSubmenu=Psubmenu;   //                 //~1328I~//~@@@@R~
//        Psubmenu.itemid=Pitemid;                                   //~1328I~//~@@@@R~
//    }                                                              //~1328I~//~@@@@R~
//**********************************************************************//~1211I~
//*callback                                                        //~1211I~
//**********************************************************************//~1211I~
    public int alertButtonAction(int Pbuttonid,int Pitempos)       //~1211R~//~@@@@R~
    {                                                              //~1211I~//~@@@@R~
          int rc=1;   //dismiss                                      //~1211I~//~1328R~//~@@@@R~
          return rc;                                                 //~1211M~//~1328R~//~@@@@R~
    }                                                              //~1211I~//~@@@@R~
//****************************************************             //~1411I~
//    private void helpAsgts()                                      //~1411I~//~@@@@R~
//    {                                                              //~1411I~//~@@@@R~
//    //*******************                                          //~1411I~//~@@@@R~
//        new Help(HELPTEXT_AJAGOC);                                     //~1411I~//~@@@@R~
//        if (Dump.Y) Dump.println("AMenu:AjagoHelp");           //~1506R~//~@@@@R~
//    }                                                              //~1411I~//~@@@@R~
//*********************************************                    //~1314I~
//*Option Menu ********************************                    //~1314I~
//*********************************************                    //~1314I~
//* called only once                                               //~1326I~
 	public  void onCreateOptionMenu(Menu Pmenu)                    //~1314R~
	{                                                              //~1314I~
        String str;                                                //~1314I~
    //********************                                         //~1314I~
        if (AG.osVersion>=AG.HONEYCOMB)  //android3                //~1A43I~
        {                                                          //~1A43I~
            onCreateOptionMenu_V11(Pmenu);                         //~1A43I~
            return;                                                //~1A43I~
        }                                                          //~1A43I~
	    menuDesc=AG.resource.getStringArray(R.array.MenuText);  //~1314I~//~1326R~
        for (int ii=0;ii<MENU_CTR;ii++)                            //~1326R~
		{                                                          //~1314I~
        	str=menuDesc[ii];                                      //~1314I~
            int id=menuId[ii];                                     //~1314I~
            MenuItem item=Pmenu.add(0,id,0,str);                    //~1314I~
            item.setIcon(icons[ii]);                               //~1314I~
        }                                                          //~1314I~
    }                                                              //~1314I~
//**************                                                   //~1A43I~
 	public void onCreateOptionMenu_V11(Menu Pmenu)                 //~1A43I~
	{                                                              //~1A43I~
        MenuInflater inf=AG.activity.getMenuInflater();            //~1A43I~
        inf.inflate(R.menu.actionbar,Pmenu);                       //~1A43I~
    }                                                              //~1A43I~
//**************                                                   //~v107I~
//* called each time to diaply                                     //~v107I~
 	public  void onPrepareOptionMenu(Menu Pmenu)                   //~v107I~
	{                                                              //~v107I~
    //********************                                         //~v107I~
//        if (AG.portrait                                            //~v107R~//~@@@@R~
////      ||  AG.scrWidth<MENUMENU_SIZE                              //~v107R~//~@@@@R~
//        )                                                          //~v107I~//~@@@@R~
//            Pmenu.findItem(MENU_MENU).setVisible(false);        //~v107I~//~@@@@R~
//        else                                                       //~v107I~//~@@@@R~
//            Pmenu.findItem(MENU_MENU).setVisible(true);            //~v107I~//~@@@@R~
    }                                                              //~v107I~
//**************                                                   //~1314I~
 	public  int onOptionMenuSelected(MenuItem item)                //~1314I~
	{                                                              //~1314I~
        int itemid=item.getItemId();                               //~1314I~
        if (Dump.Y) Dump.println("AMenu:OptionMenuSelected="+itemid);//~1314I~ //~1326R~//~1506R~//~@@@@R~
        switch(itemid)                                             //~1314I~
        {                                                          //~1314I~
        case    MENU_STOP:                                       //~1314R~//~1326R~
        case    R.id.MENU_STOP:   //action bar menu                //~1A43I~
            stopApp();                                             //~1412I~
            break;                                                 //~1412I~
//        case    MENU_CLOSE:                                        //~1326I~//~@@@@R~
//            closeFrame();                                          //~1314R~//~@@@@R~
//            break;                                                 //~1314I~//~@@@@R~
          case    MENU_CANCEL:                                     //~@@@@I~
              AG.activity.closeOptionsMenu();                                   //~@@@@I~
              break;                                               //~@@@@I~
//        case    MENU_HELP:                                         //~1314I~//~@@@@R~
//            optionMenuHelp();                                      //~1314R~//~@@@@R~
//            break;                                                 //~1314I~//~@@@@R~
        case    MENU_MSG:                                          //~@@@@I~
        case    R.id.MENU_MSG:                                     //~1A43I~
            optionMenuMsg();                                       //~@@@@I~
            break;                                                 //~@@@@I~
//        case    MENU_MENU:                                         //~v107I~//~@@@@R~
//            optionMenuMenu();                                      //~v107I~//~@@@@R~
//            break;                                                 //~v107I~//~@@@@R~
        }                                                          //~1314I~
        return 0;                                                  //~1314I~
    }//selected                                                    //~1314I~
//**************                                                   //~1412I~
    public void stopApp()                                          //~1412I~
    {                                                              //~1412I~
        confirmStop();                                             //~1412I~
    }                                                              //~1412I~
//**************                                                   //~1314I~
//    public void closeFrame()                                       //~1314R~//~@@@@R~
//    {                                                              //~1314I~//~@@@@R~
//        try                                                        //~1423I~//~@@@@R~
//        {                                                          //~1423I~//~@@@@R~
//            if (AG.isTopFrame())                                   //~1423R~//~@@@@R~
//                confirmStop();                                     //~1423R~//~@@@@R~
//            else                                                   //~1423R~//~@@@@R~
//            {                                                      //~1423R~//~@@@@R~
//                if (!ActionEvent.optionMenuClose()) //CloseFrame doAction not scheduled//~1423R~//~@@@@R~
//                    Window.popFrame(true);  //close                //~1423R~//~@@@@R~
//            }                                                      //~1423R~//~@@@@R~
//        }                                                          //~1423I~//~@@@@R~
//        catch(Exception e)                                         //~1423I~//~@@@@R~
//        {                                                          //~1423I~//~@@@@R~
//            Dump.println(e,"AMenu:closeFrame from OptionMenu");//~1423I~//~@@@@R~
//        }                                                          //~1423I~//~@@@@R~
//    }                                                              //~1314I~//~@@@@R~
//**************                                                   //~1314I~
    public void confirmStop()                                      //~1314I~
    {                                                              //~1314I~
    	int flag=Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE|Alert.EXIT;//~1314I~//~@@@@R~
        Alert.simpleAlertDialog(null/*callback*/,null/*title*/,R.string.Qexit,flag);//~1314I~//~@@@@R~
    }                                                              //~1314I~
//**************                                                   //~1314I~
//    public void showContextMenu()                                  //~1314I~//~@@@@R~
//    {                                                              //~1314I~//~@@@@R~
//        View view=Window.getCurrentFrame().contextMenuView;        //~1314I~//~@@@@R~
//        if (view==null)                                            //~1314I~//~@@@@R~
//        {                                                          //~1314I~//~@@@@R~
//            AView.showToast(R.string.NoRegisteredContextMenu); //~1314I~//~@@@@R~
//            return;                                                //~1314I~//~@@@@R~
//        }                                                          //~1314I~//~@@@@R~
//        if (Dump.Y) Dump.println("AMenu showContextMenu view="+view.toString());//~1516M~//~@@@@R~
//        swShowRequest=true;                                        //~1427I~//~@@@@R~
//        view.showContextMenu();                                    //~v104R~//~@@@@R~
////      AG.activity.openContextMenu(view);                         //~v104R~//~@@@@R~
//    }                                                              //~1314I~//~@@@@R~
//**************                                                   //~1A11I~
    public void playMenu(DoActionListener Pdal)                    //~1A11I~
    {                                                              //~1A11I~
        if (Dump.Y) Dump.println("AMenu:playMenu");                //~1A11I~
        DAL_PlayMenu=Pdal;                                         //~1A11I~
        MainFrame MF=(MainFrame)Pdal;                              //~1A11I~
        View view=MF.framelayoutview;                              //~1A11I~
        viewPlayMenu=view;                                         //~1A11I~
        AG.activity.registerForContextMenu(view);                  //~1A11I~
        AG.activity.openContextMenu(view);                         //~1A11R~
    }                                                              //~1A11I~
//**************                                                   //~1A21I~
    public void fileMenu(DoActionListener Pdal)                    //~1A21I~
    {                                                              //~1A21I~
//        DAL_FileMenuLGF=null;                                      //~1A22I~//~1A27R~
        if (Dump.Y) Dump.println("AMenu:fileMenu");                //~1A21I~
        if (AG.screenDencityMdpiSmallV) //mdoi & height<=320       //~1A40R~
        {                                                          //~1A40I~
            setMenuItemHeightTsumego(Pdal);                        //~1A40I~
            return;                                                //~1A40I~
        }                                                          //~1A40I~
        DAL_FileMenu=Pdal;                                         //~1A21I~
        viewFileMenu=((Container)Pdal).containerLayoutView;        //~1A21I~
        AG.activity.registerForContextMenu(viewFileMenu);                  //~1A21I~
        AG.activity.openContextMenu(viewFileMenu);                         //~1A21I~
    }                                                              //~1A21I~
//**************                                                   //~1A4kI~
    public void fileMenuReset()                                    //~1A4kI~
    {                                                              //~1A4kI~
        DAL_FileMenu=null;  //refe to FeeGoFrame                //~1A4kI~
        viewFileMenu=null;                                         //~1A4kI~
        if (Dump.Y) Dump.println("AMenu:fileMenuReset");           //~1A4kI~
    }                                                              //~1A4kI~
////**************                                                   //~1A22I~//~1A27R~
//    public void fileMenuLGF(DoActionListener Pdal)                 //~1A22I~//~1A27R~
//    {                                                              //~1A22I~//~1A27R~
//        DAL_FileMenu=null;                                         //~1A22I~//~1A27R~
//        if (Dump.Y) Dump.println("AMenu:fileMenuLGF");             //~1A22I~//~1A27R~
//        DAL_FileMenuLGF=Pdal;                                      //~1A22I~//~1A27R~
//        viewFileMenuLGF=((Container)Pdal).containerLayoutView;     //~1A22I~//~1A27R~
//        AG.activity.registerForContextMenu(viewFileMenuLGF);       //~1A22I~//~1A27R~
//        AG.activity.openContextMenu(viewFileMenuLGF);              //~1A22I~//~1A27R~
//    }                                                              //~1A22I~//~1A27R~
//**************                                                   //~1314I~
//    public void optionMenuHelp()                                        //~1314I~//~@@@@R~
//    {                                                              //~1314I~//~@@@@R~
//        Frame frame=Window.getCurrentFrame();                      //~1411I~//~@@@@R~
//        MenuBar menubar=frame.framemenubar;                        //~1411I~//~@@@@R~
//        if (menubar==null|| menubar.helpMenu==null)                //~1411I~//~@@@@R~
//        {                                                          //~1411I~//~@@@@R~
//            AView.showToast(R.string.NoRegisteredHelpMenu);    //~1411I~//~@@@@R~
//            return;                                                //~1411I~//~@@@@R~
//        }                                                          //~1411I~//~@@@@R~
//        menubar.setShowHelpOnly(true);                           //~@@@@R~
//        View view=frame.contextMenuView;//~1411I~                //~@@@@R~
//        if (Dump.Y) Dump.println("AMenu optionMenuHelp view="+view.toString());//~1506R~//~@@@@R~
//        swShowRequest=true;                                        //~1427I~//~@@@@R~
//        view.showContextMenu();                                    //~1411I~//~@@@@R~
//        AView.showToastLong(R.string.Help_OptionMenu);           //~@@@@R~
//    }                                                              //~1314I~//~@@@@R~
//**************                                                   //~v107I~
//    public void optionMenuMenu()                                   //~v107I~//~@@@@R~
//    {                                                              //~v107I~//~@@@@R~
//        showContextMenu();  //show submenu                         //~v107I~//~@@@@R~
//    }                                                              //~v107I~//~@@@@R~
//******************************************************           //~@@@@I~
    public void optionMenuMsg()                                    //~@@@@R~
    {                                                              //~@@@@I~
    	if ((AG.RemoteStatus!=AG.RS_IPCONNECTED                    //~@@@@I~
             &&  AG.RemoteStatus!=AG.RS_BTCONNECTED                //~@@@@I~
            )                                                      //~@@@@I~
    	||  AG.msgThread==null                                     //~@@@@R~
        ||  !AG.msgThread.isAlive())                           //~@@@@I~
        {                                                          //~@@@@I~
        	AView.showToast(R.string.ErrNoConnectionForConversation);//~@@@@R~
            return;                                                //~@@@@I~
        }                                                          //~@@@@I~
        new SayDialog(AG.currentFrame);                            //~@@@@R~
    }                                                              //~@@@@I~
//************************************************                 //~1507I~
//*Ajago Option menu add before help of MainFrame menubar          //~1507I~
//************************************************                 //~1507I~
//    private com.Asgts.awt.Menu ajagocOptions;                             //~1507I~//~@@@@R~
//    private CheckboxMenuItemAction opt1,opt2,opt3;          //~1507I~//~@@@@R~
//                                                                   //~1507I~//~@@@@R~
//    private static final String DIRECTIONKEY="Use DirectionKey as Shortcut";//~1507I~//~@@@@R~
//    private static final String SEARCHKEY   ="Use Search button as Enter";//~1507I~//~@@@@R~
////    private static final String DEBUGTRACE  ="DebugTrace";         //~1507I~//~@@@@R~
//                                                                   //~1507I~//~@@@@R~
//    public  static final String DIRECTIONKEY_CFGKEY="directionkey";//~1507R~//~@@@@R~
//    public  static final String SEARCHKEY_CFGKEY   ="searchkey";   //~1507R~//~@@@@R~
////    public  static final String DEBUGTRACE_CFGKEY  ="debugtrace";  //~1507R~//~@@@@R~
//                                                                   //~1507I~//~@@@@R~
    public void addAjagoOptionMenu(MenuBar Pmenubar)        //~1507I~//~@@@@R~
    {                                                              //~1507I~//~@@@@R~
//        boolean flag;                                              //~1507R~//~@@@@R~
//        if (ajagocOptions==null)                                   //~1507R~//~@@@@R~
//        {                                                          //~1507R~//~@@@@R~
//            ajagocOptions=new MyMenu(AG.appName+" "+Global.resourceString("Options"));//~1507R~//~@@@@R~
////            opt1=new CheckboxMenuItemAction(this,DIRECTIONKEY);   //~1507R~//~@@@@R~
////            opt2=new CheckboxMenuItemAction(this,SEARCHKEY);     //~1507R~//~@@@@R~
////            opt3=new CheckboxMenuItemAction(this,DEBUGTRACE);    //~1507R~//~@@@@R~
////            ajagocOptions.add(opt1);                                     //~1507R~//~@@@@R~
////            ajagocOptions.add(opt2);                                     //~1507R~//~@@@@R~
////          if (AG.isDebuggable)                                     //~v107I~//~@@@@R~
////            ajagocOptions.add(opt3);                                     //~1507R~//~@@@@R~
//        }                                                          //~1511M~//~@@@@R~
////        flag=Global.getParameter(DIRECTIONKEY_CFGKEY,false);       //~1511R~//~@@@@R~
////        if (Dump.Y) Dump.println("addAjagoOptionMenu directionkey="+flag);//~1511R~//~@@@@R~
////        opt1.setState(flag);                                       //~1511R~//~@@@@R~
////        flag=Global.getParameter(SEARCHKEY_CFGKEY,true);           //~1511R~//~@@@@R~
////        if (Dump.Y) Dump.println("addAjagoOptionMenu searchkey="+flag);//~1511R~//~@@@@R~
////        opt2.setState(flag);                                       //~1511R~//~@@@@R~
////        flag=Global.getParameter(DEBUGTRACE_CFGKEY,false);         //~1511R~//~@@@@R~
////        if (Dump.Y) Dump.println("addAjagoOptionMenu debugtrace="+flag);//~1511R~//~@@@@R~
////        opt3.setState(flag);                                       //~1511R~//~@@@@R~
//        Pmenubar.menuList.add(ajagocOptions);                               //~1507R~//~@@@@R~
    }                                                              //~1507I~//~@@@@R~
    @Override                                                      //~1507I~//~@@@@R~
    public void doAction(String o)                                 //~1507I~//~@@@@R~
    {                                                              //~1507I~//~@@@@R~
    }                                                              //~1507I~//~@@@@R~
    @Override                                                      //~1507I~//~@@@@R~
    public void itemAction(String o,boolean flag)                  //~1507I~//~@@@@R~
    {                                                              //~1507I~//~@@@@R~
//        if (Dump.Y) Dump.println("Ajagomenu ItemAction for AjagoOption "+o+"="+flag);//~1507I~//~@@@@R~
//        if (o.equals(DIRECTIONKEY))                                //~1507I~//~@@@@R~
//        {                                                          //~1507I~//~@@@@R~
//            Global.setParameter(DIRECTIONKEY_CFGKEY,flag);         //~1507I~//~@@@@R~
//            Canvas.optionChanged(flag);                            //~1507I~//~@@@@R~
//        }                                                          //~1507I~//~@@@@R~
//        else                                                       //~1507I~//~@@@@R~
//        if (o.equals(SEARCHKEY))                                   //~1507I~//~@@@@R~
//        {                                                          //~1507I~//~@@@@R~
//            Global.setParameter(SEARCHKEY_CFGKEY,flag);            //~1507I~//~@@@@R~
//            AKey.optionChanged(flag);                          //~1507I~//~@@@@R~
//        }                                                          //~1507I~//~@@@@R~
////        else                                                       //~1507I~//~@@@@R~
////        if (o.equals(DEBUGTRACE))                                  //~1507I~//~@@@@R~
////        {                                                          //~1507I~//~@@@@R~
////            Global.setParameter(DEBUGTRACE_CFGKEY,flag);           //~1507I~//~@@@@R~
////            Dump.setOption(flag);                                  //~1507I~//~@@@@R~
////        }                                                          //~1507I~//~@@@@R~
    }                                                              //~1507I~//~@@@@R~
    //**************************************************************//~1A40I~
    private void setMenuItemHeight(ContextMenu Pmenu,View Pview)   //~1A40I~
    {                                                              //~1A40I~
//        int itemctr=Pmenu.size();                                //~1A40R~
//        for (int ii=0;ii<itemctr;ii++)                           //~1A40R~
//        {                                                        //~1A40R~
//            MenuItem item=Pmenu.getItem(ii);                     //~1A40R~
//            String itemtitle=(String)item.getTitle();            //~1A40R~
//            if (Dump.Y) Dump.println("menuitem="+itemtitle);     //~1A40R~
////          TextView tv=getMenuItemView(item);                   //~1A40R~
//        }                                                        //~1A40R~
//        AG.activity.getLayoutInflater().setFactory(              //~1A40R~
//            new Factory()                                        //~1A40R~
//            {                                                    //~1A40R~
//                public View onCreateView(String Pname,android.content.Context Pcontext,android.util.AttributeSet Pattrs)//~1A40R~
//                {                                                //~1A40R~
//                    try                                          //~1A40R~
//                    {                                            //~1A40R~
//                        LayoutInflater li=LayoutInflater.from(Pcontext);//~1A40R~
//                        final View v=li.createView(Pname,null,Pattrs);//~1A40R~
//                        new Handler().post(                      //~1A40R~
//                            new Runnable()                       //~1A40R~
//                            {                                    //~1A40R~
//                                public void run()                //~1A40R~
//                                {                                //~1A40R~
//                                    ((TextView)v).setTextSize(10);//~1A40R~
//                                }                                //~1A40R~
//                            }             );                     //~1A40R~
//                            return v;                            //~1A40R~
//                    }                                            //~1A40R~
//                    catch (InflateException e)                   //~1A40R~
//                    {                                            //~1A40R~
//                        Dump.println(e,"SetMenuItemHeight");     //~1A40R~
//                    }                                            //~1A40R~
//                    catch (ClassNotFoundException e)             //~1A40R~
//                    {                                            //~1A40R~
//                        Dump.println(e,"SetMenuItemHeight");     //~1A40R~
//                    }                                            //~1A40R~
//                    catch (Exception e)                          //~1A40R~
//                    {                                            //~1A40R~
//                        Dump.println(e,"SetMenuItemHeight");     //~1A40R~
//                    }                                            //~1A40R~
//                    return null;                                 //~1A40R~
//                }//onCreateView                                  //~1A40R~
//            }/*new Factory*/                    );               //~1A40R~
		AMenuDialog md=new AMenuDialog(AG.mainframe,R.string.Title_PlayMenu,R.layout.menudialogplay,"PlayMenu",R.string.HelpTitle_PlayMenu);//~1A40R~
    }                                                              //~1A40R~
    //**************************************************************//~1A40I~
    private void setMenuItemHeightTsumego(DoActionListener Pdal)   //~1A40R~
    {                                                              //~1A40I~
    	FreeGoFrame gf=(FreeGoFrame)Pdal;                          //~1A40I~
		AMenuDialog md=new AMenuDialog(gf,R.string.Title_FileMenu,R.layout.menudialogfiletsumego);//~1A40R~
    }                                                              //~1A40I~
    //**************************************************************//~1A40I~
    private void showMenuButton()                                  //~1A40I~
    {                                                              //~1A40I~
//        boolean hasMenu=ViewConfiguration.get(AG.activity).hasPermanentMenuKey();//~1A40R~
//        try                                                      //~1A40R~
//        {                                                        //~1A40R~
//            getWindow.addFlags(Windowmanager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null);//~1A40R~
//        }                                                        //~1A40R~
//        catch(NoSuchFieldException e)                            //~1A40R~
//        {                                                        //~1A40R~
//        }                                                        //~1A40R~
//        catch (IllegalAccessException e)                         //~1A40R~
//        {                                                        //~1A40R~
//            Dump.y(e,"ShowMenuButton");                          //~1A40R~
//        }                                                        //~1A40R~
    }                                                              //~1A40I~
}//class AMenu                                                 //~1211R~//~@@@@R~
