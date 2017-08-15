//*CID://+1A4SR~:                             update#=   17;       //~1A4SR~
//*************************************************************************//~1A4SI~
//1A4s 2014/12/06 utilize clipboard(view dialog)                   //~1A4SI~
//*************************************************************************//~1A4SI~
package com.Asgts.awt;                                            //~1112I~//~@@@@R~


import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.R;                                                //~@@@@R~
import jagoclient.Dump;
import jagoclient.Global;
//import jagoclient.gmp.GMPConnection;                             //~@@@@R~
import jagoclient.gui.ActionTranslator;
import jagoclient.gui.DoActionListener;
import android.view.View;
import android.widget.TextView;

public class ActionEvent                                           //~1112R~
{                                                                  //~1112I~
    public static String currentActionCommand;                //~1324I~
	private View v;
//    private List list;                                        //~1410R~//~@@@@R~
    private String actionCommand="";                               //~1422R~
                                                                   //~1408I~
    public ActionTranslator scheduledAT;                     //~1408I~
    private Window containerWindow;                           //~1408R~
    private ActionListener scheduledAL;                       //~1408I~
                                                                   //~1408I~
//*****************************************                        //~1325I~
	public ActionEvent(ActionTranslator Pat,Component Pcomponent)  //~1408I~
	{                                                              //~1408I~
    	scheduledAT=Pat;                                           //~1408I~
    	containerWindow=Pcomponent.parentWindow;	//Frame or Dialog//~1408R~
        if (containerWindow instanceof Dialog)                     //~1408R~
        {                                                          //~1408I~
//          Frame parent=((Dialog)containerWindow).parentFrame;    //~1A4SR~
		    Window parent=getDialogParent(containerWindow);        //~1A4SI~
	    	parent.scheduledDialogAE=this;                         //~1408I~
        }                                                          //~1408I~
        else                                                       //~1408I~
    		containerWindow.scheduledAE=this;                      //~1408R~
	}                                                              //~1408I~
    private static Window getDialogParent(Window Pwindow)          //~1A4SR~
    {                                                              //~1A4SI~
    	Dialog dlg=(Dialog)Pwindow;                        //~1A4SI~
        return dlg.parentFrame!=null ? dlg.parentFrame :dlg.parentDialog;//~1A4SI~
    }                                                              //~1A4SI~
	public ActionEvent(Frame Pframe,ActionTranslator Pat)	//for optionMenuClose//~1422I~
	{                                                              //~1422I~
    	scheduledAT=Pat;                                           //~1422I~
        containerWindow=Pframe;                                    //~1422I~
    	containerWindow.scheduledAE=this;                          //~1422I~
	}                                                              //~1422I~
//dialog reschedule Event                                          //~1410I~
	public ActionEvent(ActionEvent Pae,Dialog Pdialog)             //~1410I~
	{                                                              //~1410I~
		DoActionListener dal=Pdialog.dialogDoActionListener;       //~1410I~
		ActionTranslator at=Pae.scheduledAT;                       //~1410I~
		at=new ActionTranslator(dal,at.Name);   //dialog was new created at after dismiss//~1410I~
                                                                   //~1410I~
    	scheduledAT=at;                                           //~1410I~
    	containerWindow=Pdialog;	//Frame or Dialog              //~1410I~
//      Frame parent=Pdialog.parentFrame;                          //~1A4SR~
		Window parent=getDialogParent(containerWindow);            //~1A4SI~
	    parent.scheduledDialogAE=this;                             //~1410I~
	}                                                              //~1410I~
//menu event                                                       //~1410I~
	public ActionEvent(ActionTranslator Pat,MenuBar Pmenubar,MenuItem PmenuItem)//~1408I~
	{                                                              //~1408I~
    	scheduledAT=Pat;                                           //~1408I~
    	containerWindow=Pmenubar.frame;                            //~1408R~
    	containerWindow.scheduledAE=this;                          //~1408I~
	}                                                              //~1408I~
	public ActionEvent(ActionListener Pal,KeyEvent PkeyEvent)      //~1408I~
	{                                                              //~1408I~
    	scheduledAL=Pal;                                           //~1408I~
	}                                                              //~1408I~
//*****************************************                        //~1408I~
//**********rene.lister.ListerMouseEvent                           //~1214I~
	public ActionEvent(Object P1,int P2,String P3)                 //~1214I~
	{                                                              //~1214I~
	}                                                              //~1214I~
//    public ActionEvent(Lister lister, int i, String selectedItem) {//~@@@@R~

//    }                                                            //~@@@@R~
	//**********                                                       //~1112I~
//  public View getSource()                                        //~1407R~
//  {return v;}                                                    //~1407R~
//*for Go,compile only;doAction() is not executed because ItemClick event is not supporterd//~1407I~
//*Connect Button execute do Action alternatively                  //~1407I~
//    public List getSource()                                        //~1407I~//~@@@@R~
//    {                                                              //~1407I~//~@@@@R~
//        return list;                                               //~1407I~//~@@@@R~
//    }                                                              //~1407I~//~@@@@R~
////**********                                                     //~1407R~
//    public void setSource(View Pv)                               //~1407R~
//    {v=Pv;}                                                      //~1407R~
//**********                                                       //~1112I~
    public String getActionCommand()                                 //~1112I~
	{                                                              //~1112I~
    	if (v!=null)                                               //~1422I~
    		actionCommand=((TextView)v).getText().toString();      //~1422R~
        saveActionCommand(actionCommand);                          //~1324R~
        if (Dump.Y) Dump.println("getActionCommand:"+actionCommand);//~1506R~
    	return actionCommand;                                                //~1324I~
	}                                                              //~1112I~
//***************************************************************************//~1325I~
//*from ActionTranslator * to reschedule DoAction after modal dialog dismiss//~1325I~
//***************************************************************************//~1325I~
    public void setActionCommand(String Pcmd)                      //~1324I~
	{                                                              //~1324I~
    	actionCommand=Pcmd;                                        //~1324I~
        if (Dump.Y) Dump.println("setActionCommand:"+actionCommand);//~1506R~
        saveActionCommand(actionCommand);                          //~1324R~
	}                                                              //~1324I~
    public void saveActionCommand(String Pcmd)                     //~1324I~
	{                                                              //~1324I~
    	currentActionCommand=Pcmd;                                 //~1324I~
        if (Dump.Y) Dump.println("currentActionCommand:"+Pcmd);    //~1506R~
	}                                                              //~1324I~
    public static String getCurrentActionCommand()                 //~1324I~
	{                                                              //~1324I~
        if (Dump.Y) Dump.println("getCurrentActionCommand:"+currentActionCommand);//~1506R~
    	return currentActionCommand;                               //~1324I~
	}                                                              //~1324I~
    public static void actionPerformed(ActionTranslator Pat,Component Pcomponent)//~1408I~
    {                                                              //~1408I~
    	boolean subthreadModalAction=false;                        //~1410I~
        Dialog dialog=null;                                             //~1410I~
        boolean swhelp=false;                                      //~@@@@I~
    //************                                                 //~1410I~
    	Window window=Pcomponent.parentWindow;	//Frame or Dialog//~1408I~
        ActionEvent ev=new ActionEvent(Pat,Pcomponent);            //~1408I~
        if (window instanceof Frame)    //cause of create Dialog   //~1408I~
        {                                                          //~1408I~
//          frameAT=Pat;                                           //~1408I~
//          frameEvent=ev;                                         //~1408I~
        	if (Dump.Y) Dump.println("ActionEvent actionPerformed window is frame="+((Frame)window).framename);//+1A4SI~
        }                                                          //~1408I~
        else                                                       //~1408I~
        {                                                          //~1408I~
        	if (Dump.Y) Dump.println("ActionEvent actionPerformed window is dialog="+((Dialog)window).dialogname);//+1A4SI~
//          dialogAT=Pat;                                          //~1408I~
//          dialogEvent=ev;                                        //~1408I~
//          if (((TextView)Pv).getText().toString().equals(Global.resourceString("Cancel")))//~1408I~
			if (Pcomponent instanceof Button)                      //~1408I~
            {                                                      //~1408I~
            	TextView tv=(TextView)(((Button)Pcomponent).androidButton);//~1408I~
        		if (Dump.Y) Dump.println("ActionEvent actionPerformed text="+tv.getText().toString());//~2B14I~
        		if (Dump.Y) Dump.println("ActionEvent cancel          text="+AG.resource.getString(R.string.Cancel));//~2B14I~//~@@@@R~
//  			if (txt.equals(Global.resourceString("Cancel")))   //~@@@@I~
				String txt=tv.getText().toString();                       //~@@@@R~
				if (txt.equals(AG.resource.getString(R.string.Cancel)))//~@@@@I~
            	{                                                  //~1408I~
        			if (Dump.Y) Dump.println("ActionEvent actionPerformed set canceled=true");//~@@@@I~
                	((Dialog)window).canceled=true; //no afterDismiss schedule//~1408I~
            	}                                                  //~1408I~
                swhelp=txt.equals(AG.resource.getString(R.string.Help));//~@@@@R~
            }                                                      //~1408I~
        	if (Dump.Y) Dump.println("swhelp="+swhelp);            //~@@@@I~
          if (!swhelp) //HelpButton shows modeless dialog,do not post current dialog wait//~@@@@R~
          {                                                        //~@@@@I~
            dialog=((Dialog)window);                               //~1410I~
            subthreadModalAction=dialog.isSubthreadModal();        //~1410I~
        	if (Dump.Y) Dump.println("subthreadModalAction="+subthreadModalAction+",dialog="+dialog.dialogname);//~@@@@I~
          }                                                        //~@@@@I~
        }                                                          //~1408I~
        if (subthreadModalAction)                                  //~1410I~
        	dialog.subthreadModalActionPerform(ev);                //~1410R~
        else                                                       //~1410I~
			actionPerform(0/*ActionTranslator*/,ev);               //~1410R~
    }                                                              //~1408I~
//****************                                                 //~1408I~
	public static void actionPerform(int Psw,ActionEvent Pev)     //~1408R~
    {                                                              //~1408I~
		if (Dump.Y) Dump.println("ActionEvent:actionPerform sw="+Psw);//~1506R~//~1A4SR~
        try                                                        //~1408I~
        {                                                          //~1408I~
        	if (Psw==1)	//ActionListener                           //~1422R~
            {                                                      //~1408I~
            	if (Pev.scheduledAL!=null)                         //~1408I~
            		Pev.scheduledAL.actionPerformed(Pev);          //~1408R~
            }                                                      //~1408I~
            else        //ActionTranslator                         //~1422R~
            {                                                      //~1408I~
            	if (Pev.scheduledAT!=null)                         //~1408I~
                {                                                  //~1408I~
					if (Dump.Y) Dump.println("ActionEvent:AT="+Pev.scheduledAT.Name);//~1506R~
					if (!scheduleBoardAction(Pev))                 //~1421I~
	        			Pev.scheduledAT.actionPerformed(Pev);    //don't chage original ActionName by DialogButton Action name//~1421R~
                }                                                  //~1408I~
            }                                                      //~1408I~
        }                                                          //~1408I~
        catch(Exception e)                                         //~1408I~
        {                                                          //~1408I~
			Dump.println(e,"ActionEvent:actionPerform");           //~1506R~
        }                                                          //~1408I~

    }                                                              //~1408I~
//****************                                                 //~1408R~
//*from AjagoMenu*                                                 //~1408R~
//****************                                                 //~1408R~
    public static void actionPerformedMenu(MenuBar Pmenubar,MenuItem Pmenuitem)//~1408R~
    {                                                              //~1408I~
		if (Dump.Y) Dump.println("ActionEvent:MenuItem"+Pmenuitem.name);//~1506R~
		ActionTranslator actiontranslator=Pmenuitem.actiontranslator;//~1408I~
        ActionEvent ev=new ActionEvent(actiontranslator,Pmenubar,Pmenuitem);//~1408R~
        actionPerform(0/*ActionTranslator*/,ev);                   //~1408R~
    }                                                              //~1408I~
//****************                                                 //~1408I~
//*from AjagoKey                                                   //~1408I~
//****************                                                 //~1408I~
    public static void actionPerformedKey(ActionListener Pal,View Pview,android.view.KeyEvent Pandroidkeyevent)//~1408I~
    {                                                              //~1408I~
		if (Dump.Y) Dump.println("ActionEvent:KayEvent code="+Pandroidkeyevent.getKeyCode());//~1506R~
        KeyEvent awtkeyevent=new KeyEvent(Pandroidkeyevent);       //~1408I~
        ActionEvent ev=new ActionEvent(Pal,awtkeyevent);           //~1408I~
        actionPerform(1/*ActionListener*/,ev);                     //~1408R~
    }                                                              //~1408I~
//************************************************                                        //~1325I~//~1330R~
//* redo modal dialog DoActionListener at diamiss call             //~1330R~
//************************************************                                        //~1325I~//~1330R~
    public static void redoDialogAction(Dialog Pdialog)            //~1408I~
    {                                                              //~1408I~
//      Frame parent=Pdialog.parentFrame;                          //~1A4SR~
		Window parent=getDialogParent(Pdialog);                    //~1A4SI~
        ActionEvent	ae=parent.scheduledDialogAE;	//saved on parent Frame//~1408R~
		if (ae!=null)                                              //~1408I~
        {                                                          //~1408I~
        	ae=new ActionEvent(ae,Pdialog);                        //~1410I~
			actionPerform(0/*ActionTranslator*/,ae);               //~1408I~
	        parent.scheduledDialogAE=null;	//saved on parent Frame//~1408I~
        }                                                          //~1408I~
    }                                                              //~1408I~
    public static void resetDialogAction(Dialog Pdialog)           //~1408R~
    {                                                              //~1408I~
//      dialogAT=null;  //clear before showModal                   //~1408R~
//      Frame parent=Pdialog.parentFrame;                          //~1A4SR~
		Window parent=getDialogParent(Pdialog);                    //~1A4SI~
        parent.scheduledDialogAE=null;                             //~1408I~
    }                                                              //~1408I~
//*************************************                            //~1330R~
//* redo frame Button action                                       //~1330R~
//*  frame is same as 1st schedule                                 //~1330I~
//*************************************                            //~1330R~
    public static void redoFrameAction(Frame Pframe)              //~1330R~
    {                                                              //~1330I~
        ActionEvent	ae=Pframe.scheduledAE;                          //~1408I~
		if (ae!=null)                                              //~1408I~
        {                                                          //~1408I~
            if (!Pframe.isDestroyed)                               //~1429I~
				actionPerform(0/*ActionTranslator*/,ae);           //~1429R~
	        Pframe.scheduledAE=null;                                //~1408I~
        }                                                          //~1408I~
    }                                                              //~1330I~
//**************************************************               //~1421R~
//* for Board Action;shedule on Board subthread                    //~1421I~
//*   IconBar ButtonAction and menu ItemAction                     //~1421I~
//*   doAction of dialog on board frame will execute updateall()   //~1421I~
//*************************************************                //~1421R~
    public static boolean scheduleBoardAction(ActionEvent Pev)     //~1421I~
    {                                                              //~1421I~
//        Frame f=AG.getCurrentFrame();                              //~1421I~//~@@@@R~
//        if (f.isBoardFrame                                         //~1421R~//~@@@@R~
//        &&  AG.isMainThread()                                      //~1421R~//~@@@@R~
//        )                                                          //~1421I~//~@@@@R~
//        {   BoardRequest r=f.boardCanvas.new BoardRequest(Canvas.BOARD_DOACTION,(Object)Pev,null);                                                       //~1421I~//~@@@@R~
//            f.boardCanvas.enqRequest(r);//~1421I~                //~@@@@R~
//            return true;                                           //~1421I~//~@@@@R~
//        }                                                          //~1421I~//~@@@@R~
//**ModalDialog by Button on BoardFrame put BoadSync thread in wait;//~@@@@I~
//**And next button action such as help will not executed          //~@@@@I~
        return false;                                              //~1421I~
    }                                                              //~1421I~
//**************************************************               //~1422I~
//* from AjagoMenu:Close Button                                    //~1422I~
//*   schedule doClose                                             //~1422I~
//*   rc:doAction() scheduled                                      //~1422I~
//*************************************************                //~1422I~
    public static boolean optionMenuClose()                        //~1422I~
    {                                                              //~1422I~
        boolean rc=false;                                              //~1516I~
        Frame f=AG.getCurrentFrame();                              //~1422I~
        DoActionListener dal=f.frameDoActionListener;              //~1422I~
        if (dal!=null)                                             //~1422I~
        {                                                          //~1422I~
    		String cmd=Global.resourceString("Close");                    //~1422I~
			ActionTranslator at=new ActionTranslator(dal,cmd);     //~1422I~
			ActionEvent ae=new ActionEvent(f,at);                  //~1422I~
            if (Dump.Y) Dump.println("optionMenuClose call frame="+f.toString());//~1516I~
			actionPerform(0/*AT*/,ae);                              //~1422I~
            if (Dump.Y) Dump.println("optionMenuClose call end frame="+f.toString());//~1516I~
            rc=true;                                               //~1516I~
        }                                                          //~1422I~
//        WindowListener wl=f.windowlistener;                        //~1517R~//~@@@@R~
//        if (wl!=null && wl instanceof GMPConnection)   //GMPConnection doAction process "Play" but no action for "Close"//~1517R~//~@@@@R~
//        {                                                          //~1517R~//~@@@@R~
//            if (Dump.Y) Dump.println("optionMenuClose windowListener call frame="+f.toString());//~1517R~//~@@@@R~
//            Window.kickWindowListener(Window.WINDOW_CLOSING,wl);   //~1517R~//~@@@@R~
//            if (Dump.Y) Dump.println("optionMenuClose windowListener callend frame="+f.toString());//~1517R~//~@@@@R~
//            rc=true;                                               //~1517R~//~@@@@R~
//        }                                                          //~1517R~//~@@@@R~
        return rc;                                                 //~1516R~
    }                                                              //~1422I~
}//class                                                           //~1112I~
