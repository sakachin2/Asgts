//*CID://+1A68R~:                             update#=   53;       //~1A68R~
//********************************************************************//~v101I~
//1A68 2015/02/06 set dialog size(Why IPConnection fill screen?)   //~1A68I~
//1A4s 2014/12/06 utilize clipboard(view dialog)                   //~1A4sI~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//********************************************************************//~v101I~
package com.Asgts.awt;                                            //~1112I~//~2C26R~//~v101R~

import java.util.ArrayList;

import com.Asgts.AG;                                                //~2C26R~//~v101R~
import com.Asgts.Modal;                                      //~2C26R~//~@@@@R~//~v101R~
import com.Asgts.ModalI;                                     //~2C26R~//~@@@@R~//~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.UiThread;                                          //~2C26R~//~v101R~
import com.Asgts.UiThreadI;                                         //~2C26R~//~v101R~
import com.Asgts.AView;                                         //~2C26R~//~@@@@R~//~v101R~
//import com.Asgts.awt.Viewer;                                      //~2C26R~//~v101R~


import jagoclient.Dump;
import jagoclient.Global;
//import jagoclient.igs.MatchDialog;                               //~2C26R~
import jagoclient.gui.CloseDialog;
import jagoclient.gui.DoActionListener;
import jagoclient.partner.PartnerSendQuestion;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static android.view.Window.FEATURE_NO_TITLE;                //~1A40I~

public class Dialog extends Window//extends android.app.Dialog                     //~1124R~//~1214R~//~1216R~
	implements UiThreadI , ModalI                                      //~1214I~//~1330R~//~2C26R~//~@@@@R~
{
	private final static int CASE_CONSTRUCT =1;                    //~1214I~
    private final static int CASE_SHOW      =2;                    //~1214I~
    private final static int CASE_TITLE     =3;
    private final static int CASE_DISMISS   =4;                    //~1214I~
    private final static int CASE_HIDE      =5;                    //~1214I~
    public int layoutresourceid;                                   //~1125R~//~1425R~
    public String dialogname="";                                   //~1310I~
    public View layoutview;                                        //~1425R~
    public int dialogstatus;	//windowListener ctl               //~1425R~
    public WindowListener windowlistener;	//windowListener ctl   //~1425R~
    public DoActionListener dialogDoActionListener;                                 //~1215R~//~1425R~
    private boolean modal;                                         //~1425R~
	public android.app.Dialog  androidDialog;                      //~1425R~
	public boolean shown;                                          //~1512R~
	public boolean afterDismiss;                                   //~1425R~
	public boolean canceled;                                       //~1425R~
	public boolean modalToGetInput;                                //~1425R~
	public Frame parentFrame;                                      //~1425R~
	public Frame layoutFrame; 	//at dismiss,recover frame before dialog popup//~@@@@I~
	public Dialog parentDialog;                                    //~@@@@I~
    private ArrayList<String> savedTextForModal=null;              //~1410R~
                                                                   //~1410I~
	public int modalStatus=0;                                      //~1410R~
    private static final int SHOW_SUBTHREAD_MODAL=1;               //~1410I~
    private static final int SHOW_MAINTHREAD_MODAL=2;              //~1410I~
    private Modal ajagoModal;                                 //~1425R~//~@@@@R~
    private boolean modalOnSubthread;                              //~1425R~
    private Dialog dialog_beforeDismiss;                           //~1410I~
    private TextView customTitle;                                  //~1A40I~
    public DoActionListener dismissListener;    //callback when dismiss from AG.setDialogClosed()//~@@@@R~
                                                                   //~1128I~
//*************************************************************    //~1309I~
    public class ThreadData                                        //~1309I~
    {                                                              //~1309I~
        int caseno;                                                //~1309I~
        String text;                                               //~1309I~
        public ThreadData(int Pcaseno,String Ptext)               //~1309I~
		{                                                          //~1309I~
        	caseno=Pcaseno;                                        //~1309I~
            text=Ptext;                                            //~1309I~
        }                                                          //~1309I~
    }                                                              //~1309I~
//****************************************                         //~1309I~
	public Dialog()                                                //~1215I~
    {                                                              //~1215I~
    	super();
        parentDialog=AG.getCurrentDialog();	//dialog from dialog such as Help dialog for Dialog//~@@@@I~
        if (Dump.Y) Dump.println("Dialog setcomponenttype");       //+1A68I~
    	setComponentType(this);//~1310R~
    }                                                              //~1215I~
	public Dialog(Frame Pframe,String Ptitle)   //for CloseFarme->CloseDialog//~1311I~
	{                                                              //~1311I~
    	this(Pframe,Ptitle,true);                                  //~1311I~
	}                                                              //~1311I~
	public Dialog(Frame Pframe,String Ptitle,boolean Pmodal)                   //~1125I~//~1311R~
	{                                                              //~1125I~
    	this();                                                    //~1310I~
        parentFrame=Pframe;                                        //~1330I~
        layoutFrame=AG.currentFrame;	//having layout resourceid //~@@@@I~
        modal=Pmodal;                                              //~1128I~//~1214I~
        modalToGetInput=false;                                     //~1327I~//~@@@@R~
        if (!Ptitle.equals(""))                                    //~@@@@I~
    		setLayoutId(Ptitle);                                       //~1326I~//~@@@@R~
	    if (layoutresourceid!=0)	//title specified              //~1327I~
	    	runOnUiThreadDlg(true,new ThreadData(CASE_CONSTRUCT,Ptitle));//~1311I~
        if (!Ptitle.equals(""))                                    //~@@@@I~
		    setTitle(Ptitle);                                      //~@@@@R~
	}
//**********************************                               //~@@@@I~
	public Dialog(Frame Pframe,String Ptitle,int Presid,boolean Pmodal,boolean Pwaitinput)//~@@@@R~
	{                                                              //~@@@@I~
    	this();                                                    //~@@@@I~
        parentFrame=Pframe;                                        //~@@@@I~
        layoutFrame=AG.currentFrame;	//having layout resourceid //~@@@@I~
        modal=Pmodal;                                              //~@@@@I~
        modalToGetInput=Pwaitinput;                                //~@@@@R~
    //*setLayoutId                                                 //~@@@@I~
        dialogname=Ptitle;                                         //~@@@@M~
        layoutresourceid=Presid;                                   //~@@@@I~
        afterDismiss=evaluateAfterDismiss();                       //~@@@@I~
        if (Dump.Y) Dump.println("Dialog name="+Ptitle+",layoutid="+Integer.toHexString(Presid));//~@@@@I~
        if (Dump.Y) Dump.println("AfterDismiss="+afterDismiss);    //~@@@@I~
    //*                                                            //~@@@@I~
	    if (layoutresourceid!=0)	//title specified              //~@@@@I~
	    	runOnUiThreadDlg(true,new ThreadData(CASE_CONSTRUCT,Ptitle));//~@@@@I~
	    setTitle(Ptitle);                                          //~@@@@I~
	}                                                              //~@@@@I~
//**********************************                               //~1A4sI~
	public Dialog(Dialog Pparent,String Ptitle,int Presid,boolean Pmodal,boolean Pwaitinput)//~1A4sI~
	{                                                              //~1A4sI~
    	this();                                                    //~1A4sI~
//      parentFrame=Pframe;                                        //~1A4sI~
        parentDialog=Pparent;                                       //~1A4sI~
        layoutFrame=AG.currentFrame;	//having layout resourceid //~1A4sI~
        modal=Pmodal;                                              //~1A4sI~
        modalToGetInput=Pwaitinput;                                //~1A4sI~
    //*setLayoutId                                                 //~1A4sI~
        dialogname=Ptitle;                                         //~1A4sI~
        layoutresourceid=Presid;                                   //~1A4sI~
        afterDismiss=evaluateAfterDismiss();                       //~1A4sI~
        if (Dump.Y) Dump.println("Dialog name="+Ptitle+",layoutid="+Integer.toHexString(Presid));//~1A4sI~
        if (Dump.Y) Dump.println("AfterDismiss="+afterDismiss);    //~1A4sI~
	    if (layoutresourceid!=0)	//title specified              //~1A4sI~
	    	runOnUiThreadDlg(true,new ThreadData(CASE_CONSTRUCT,Ptitle));//~1A4sI~
	    setTitle(Ptitle);                                          //~1A4sI~
	}                                                              //~1A4sI~
//*********                                                        //~1310I~
    private void setLayoutId(String s)                             //~1310I~
    {                                                              //~1310I~
    	int layoutid=0;                                            //~1310I~
    //************                                                 //~1310I~
        if (Dump.Y) Dump.println("Dialog setlayoutId s=:"+s);           //~1506R~//~@@@@R~
        dialogname=s;                                              //~1310I~
        if (s.equals(""))                                          //~1327R~
        	return;                                                //~1327I~
//        if (s.equals(Global.resourceString("Password"))) //Go      //~1327I~//~@@@@R~
//        {                                                          //~1310I~//~@@@@R~
//            layoutid=AG.dialogId_Password;                         //~1310I~//~@@@@R~
//        }                                                          //~1310I~//~@@@@R~
//        else                                                       //~1310I~//~@@@@R~
//		if (s.equals(AG.resource.getString(R.string.BluetoothConnection)))//~3105I~//~@@@@I~
//    		layoutid=AG.dialogId_BluetoothConnection;              //~@@@@I~
//        else                                                       //~@@@@I~
        if (s.equals(Global.resourceString("_Information_"))) //InformationDialog//~1310I~
        {                                                          //~1310I~
    		layoutid=AG.dialogId_Information;                      //~1310I~
        }                                                          //~1310I~
        else                                                       //~1310I~
        if (s.equals(Global.resourceString("Match"))) //MatchQuestion//~1310I~
        {                                                          //~1310I~
    		layoutid=AG.dialogId_MatchQuestion;                    //~1310I~
        }                                                          //~1310I~
        else                                                       //~1310I~
        if (s.equals(Global.resourceString("Message"))) //Message//~1310R~
        {                                                          //~1310I~
        	modalToGetInput=false;                                 //~1407I~
//            if (windowlistener!=null && windowlistener instanceof MatchDialog)  //BoardQuestion or GameQuestion for "Game_setup";//~1502I~//~@@@@R~
//                layoutid=AG.dialogId_MatchDialog;                  //~1502I~//~@@@@R~
//            else                                                   //~1502I~//~@@@@R~
	    		layoutid=AG.dialogId_Message;                      //~1502R~
        }                                                          //~1310I~
        else                                                       //~1310I~
        if (s.equals(Global.resourceString("_Message_"))) //MessageDialog//~1310M~
        {                                                          //~1310M~
    		layoutid=AG.dialogId_MessageDialog;                    //~1310I~
        }                                                          //~1310M~
        else                                                       //~1310I~
        if (s.equals(Global.resourceString("Tell"))) //TellQuestion//~1311I~
        {                                                          //~1311I~
    		layoutid=AG.dialogId_TellQuestion;                     //~1311I~
        }                                                          //~1311I~
        else                                                       //~1311I~
        if (s.equals("Warning"))    //rene.dialog.Warning          //~1404R~
        {                                                          //~1404I~
        	modalToGetInput=false;                                 //~1404I~
	    	layoutid=AG.dialogId_Warning;                          //~1404I~
        }                                                          //~1404I~
        else                                                       //~1404I~
        if (s.equals(Global.resourceString("Send")))               //~1405I~
        {                                                          //~1405I~
        	modalToGetInput=false;                                 //~1405I~
	      	if (windowlistener!=null && windowlistener instanceof PartnerSendQuestion)	//get DoActionListener from CloseDialog for dialog button action redo//~1405I~
            	layoutid=AG.dialogId_PartnerSendQuestion;          //~1405I~
            else                                                   //~1405I~
            	layoutid=AG.dialogId_SendQuestion;                 //~1405I~
        }                                                          //~1405I~
        else                                                       //~1405I~
        if (false                                                  //~1327R~
        ||  s.equals(Global.resourceString("Port")) //MainFrame    //~1327I~
        ||  s.equals(Global.resourceString("Your_Name"))  //MainFrame//~1327I~
        ||  s.equals("Language")                    //MainFrame    //~1327I~
        ||  s.equals(Global.resourceString("Encoding")) //GoFrame  //~1310I~
        ||  s.equals(Global.resourceString("Board_size")) //GoFrame//~1310I~
        ||  s.equals(Global.resourceString("Node_Name")) //GoFrame //~1310I~
        ||  s.equals(Global.resourceString("Wait_for_Player")) //ConnectionFrame//~1310I~
        ||  s.equals(Global.resourceString("Auto_Reply")) //ConnectionFrame//~1310I~
        )                                                          //~1310I~
        {                                                          //~1310I~
        	modalToGetInput=false;                                 //~1327I~
    		layoutid=AG.dialogId_GetParameter;                     //~1310I~
        }                                                          //~1310I~
        else                                                       //~1329I~
        if (s.equals(Global.resourceString("Game_Information"))) //GoFrame//~1405I~
        {                                                          //~1405I~
        	modalToGetInput=false;                                 //~1405I~
    		layoutid=AG.dialogId_GameInformation;                  //~1405I~
        }                                                          //~1405I~
        else                                                       //~1405I~
        if (false                                                  //~1407I~
        ||  s.equals(Global.resourceString("Accept")) //partner.PartnerFrame//~1407M~
        ||  s.equals(Global.resourceString("Yes")) //partner.PartnerFrame:save game//~1407I~
        ||  s.equals(Global.resourceString("Change_Game_Tree")) //GoFrame.AskInsertQuestion//~1407M~
        ||  s.equals(Global.resourceString("Delete_Tree")) //GoFrame.AskUndoQuestion//~1407M~
        ||  s.equals(Global.resourceString("Close_Board")) //GoFrame.CloseQuestion//~1407M~
//        ||  s.equals(Global.resourceString("Exit")) //CloseMainQuestion//~1407M~//~@@@@R~
//        ||  s.equals(Global.resourceString("Close")) //ConnectionFrame.CloseConnectionQuestion//~1407M~//~@@@@R~
                                                     //partner.ClosePartnerQuestion//~1407M~
//        ||  s.equals(Global.resourceString("End")) //partner.EndGameQuestion//~1407M~//~@@@@R~
        ||  s.equals(Global.resourceString("Undo")) //partner.UndoQuestion//~1407M~
//        ||  s.equals(Global.resourceString("Result")) //partner.ResultQuestion//~1407M~//~@@@@R~
        )                                                          //~1407I~
        {                                                          //~1407I~
    		layoutid=AG.dialogId_Question;                         //~1407I~
        }                                                          //~1407I~
        layoutresourceid=layoutid;                                 //~1310I~
        afterDismiss=evaluateAfterDismiss();                       //~1410R~
        if (Dump.Y) Dump.println("Dialog name="+s+",layoutid="+Integer.toHexString(layoutid));//~1310I~//~1506R~
        if (Dump.Y) Dump.println("AfterDismiss="+afterDismiss);    //~1506R~
    }                                                              //~1310I~
    public void runOnUiThreadDlg(boolean Pwait,ThreadData Pparm)                                //~1214I~//~1307R~//~1309R~
    {                                                              //~1214I~
    	if (Pwait)                                                 //~1309I~
			UiThread.runOnUiThreadWait(this,Pparm);           //~1309I~//~@@@@R~
        else                                                       //~1309I~
			UiThread.runOnUiThreadXfer(this,Pparm);                    //~1214I~//~1307R~//~1309R~//~@@@@R~
    }                                                              //~1214I~
//**********************                                           //~1214I~
	@Override                                                      //~1214I~
    public void runOnUiThread(Object Pparm)                                    //~1214I~//~1309R~
    {                                                              //~1214I~
        if (Dump.Y) Dump.println("Dialog runOnUi case="+((ThreadData)Pparm).caseno);                      //~1214I~//~1511R~
        switch(((ThreadData)Pparm).caseno)                                       //~1214I~//~1309R~
        {                                                          //~1214I~
        case CASE_CONSTRUCT:	//constructor                      //~1214R~
        	initUI((ThreadData)Pparm);                                              //~1214I~//~1307R~//~1309R~//~1310R~//~1311R~
            break;                                                 //~1214I~
        case CASE_SHOW:	//show                                     //~1214R~
        	showUI();                                              //~1214I~
            break;                                                 //~1214I~
        case CASE_TITLE:	//3:                                   //~@@@@R~
        	setTitleUI((ThreadData)Pparm);                                     //~1214I~//~1307R~//~1309R~
            break;                                                 //~1214I~
        case CASE_DISMISS:                                         //~1214I~
        	dismissUI();                                           //~1214I~
            break;                                                 //~1214I~
        case CASE_HIDE:                                            //~1214I~
        	hideUI();                                              //~1214I~
            break;                                                 //~1214I~
        }                                                          //~1214I~
    }                                                              //~1214I~
//*****************                                                //~1214I~
	public void initUI(ThreadData Pdata)                                           //~1214R~//~1307R~//~1309R~//~1310R~//~1311R~
	{                                                              //~1214I~
    	androidDialog=new android.app.Dialog(AG.context);                               //~1214I~//~1307R~//~1325R~
        if (Dump.Y) Dump.println("Dialog initUI androidDialog="+androidDialog.toString());//~@@@@I~
        androidDialog.setTitle(Pdata.text);                        //~1311I~//~1325R~
	    setLayout();                                               //~1310I~
	}                                                              //~1214I~
//**********************************************                   //~1214I~//~1310M~
    private void setLayout()                          //~1214I~    //~1310I~
    {                                                              //~1214I~//~1310M~
        if (afterDismiss)                                          //~1410I~
        {                                                          //~1410I~
        	layoutview=AView.inflateLayout(layoutresourceid,dialog_beforeDismiss.layoutview);//~1410R~//~@@@@R~
        }                                                          //~1410I~
        else                                                       //~1410I~
        {                                                          //~1410I~
        	layoutview=AView.inflateView(layoutresourceid);    //~1410R~//~@@@@R~
            customTitle=(TextView)layoutview.findViewById(R.id.CustomTitle);//~1A40I~
            if (customTitle!=null)                                 //~1A40I~
            {                                                      //~1A40I~
            	androidDialog.requestWindowFeature(FEATURE_NO_TITLE);//~1A40I~
			}                                                      //~1A40I~
            androidDialog.setContentView(layoutview);                        //~1214I~//~1310I~//~1325R~
		    setContainerLayoutView(layoutview);                     //~v101I~
        }                                                          //~1410I~
        componentView=layoutview;	//for Component.requestFocus;  //~1410M~
    }                                                              //~1214I~//~1310M~
//*****************                                                //~1124I~//~1311M~
    public void setTitle(String Ptitle)                            //~1125I~//~1311M~
    {                                                              //~1125I~//~1311M~
    	runOnUiThreadDlg(true,new ThreadData(CASE_TITLE,Ptitle));                                 //~1214I~//~1307R~//~1309R~//~1310R~//~1311M~
    }                                                              //~1125I~//~1311M~
    private void setTitleUI(ThreadData Pdata)                                //~1214I~//~1309R~//~1311M~
    {                                                              //~1214I~//~1311M~
                                             //~1215I~//~1311M~
	//************                                                 //~1215I~//~1311M~
    	if (layoutview==null) //not yet setup layuout              //~1311I~
        {                                                          //~1326I~
        	if (layoutresourceid==0)                               //~1326I~
    			setLayoutId(Pdata.text);                           //~1326I~
        	initUI(Pdata);	//set layout                           //~1311I~
        }                                                          //~1326I~
        else                                                       //~1311I~
        {                                                          //~1A40I~
          if (customTitle!=null)                                   //~1A40R~
            setCustomTitle(Pdata.text);                            //~1A40R~
          else                                                     //~1A40I~
        	androidDialog.setTitle(Pdata.text);	//new title                                    //~1214I~//~1309R~//~1311I~//~1325R~
        }                                                          //~1A40I~
    }                                                              //~1214I~//~1311M~
//*on Ui Thread **************                                     //~1A40I~
    private void setCustomTitle(String Ptitle)                     //~1A40R~
    {                                                              //~1A40I~
		customTitle.setText(Ptitle);                               //~1A40R~
		customTitle.setVisibility(View.VISIBLE);                   //~1A40I~
    }                                                              //~1A40I~
//*****************                                                //~1214I~
    public void show()                                             //~1214M~
    {                                                              //~1214M~
		if (Dump.Y) Dump.println("show "+dialogname+",shown="+shown);//~1506R~
        if (shown)                                                 //~1310I~
            return;                                                //~1310I~
        if (AG.isMainThread())                                     //~1410I~
        {                                                          //~1410I~
            if (modalToGetInput)                                                 //~1215I~//~1410R~
            {                                                      //~1410R~
                if (!afterDismiss)  //rescheduled DoaAction after Dismiss for modal dialog//~1410R~
                    showModal();                                   //~1410R~
                else                                                   //~1325I~//~1410R~
                    ActionEvent.redoDialogAction(this); //re-execute dialog button action//~1325R~//~1329R~//~1410R~
            }                                                      //~1410R~
            else                                                   //~1410R~
                runOnUiThreadDlg(false/*no wait*/,new ThreadData(CASE_SHOW,null));//~1410R~
        }                                                          //~1410I~
        else	//on subthread                                     //~1410I~
        {                                                          //~1410I~
            if (modal)                                             //~1410I~
            	showModal();                                       //~1410I~
            else                                                   //~1410I~
                runOnUiThreadDlg(false/*no wait*/,new ThreadData(CASE_SHOW,null));//~1410I~
        }                                                          //~1410I~
    }                                                              //~1214M~
//*******************************                                  //~1326I~
//* show in-modal dialog                                           //~1330I~
//*******************************                                  //~1330I~
    private void showUI()	                                          //~1214M~//~1330R~
    {                                                              //~1214M~
        androidDialog.setOnDismissListener((OnDismissListener) new dismissListener(this)); //~1326I~//~@@@@R~
    	androidDialog.show();                                      //~1309R~//~1325R~
		shown=true;                                                //~1329I~
    }                                                              //~1214M~
//****************************************                                  //~1326I~//~1330R~
//*dismiss listener for inmodal dialog                                                //~1326I~//~1330R~
//****************************************                         //~1326I~//~1330R~
    public class dismissListener                                   //~1326I~
    		implements OnDismissListener                           //~1326I~
	{                                                              //~1326I~
    	Dialog dialog;                                             //~@@@@I~
        public dismissListener(Dialog Pdialog)                     //~@@@@I~
        {                                                          //~@@@@I~
        	dialog=Pdialog;                                         //~@@@@I~
        }                                                          //~@@@@I~
        @Override                                                  //~1326I~
        public void onDismiss(DialogInterface Pdialog)             //~1326I~
        {                                                          //~1326I~
			shown=false;                                           //~1330I~
			if (Dump.Y) Dump.println("inmodal dialog dismiss listener"); //~1326I~//~1506R~
          try                                                      //~@@@@I~
          {                                                        //~@@@@I~
			AG.setDialogClosed(dialog);                                   //~1326I~//~@@@@R~
          }                                                        //~@@@@I~
          catch(Exception e)                                       //~@@@@I~
          {                                                        //~@@@@I~
    		Dump.println(e,"Dialog:onDismiss:"+dialogname);         //~@@@@I~
          }                                                        //~@@@@I~
        }
    }                                                              //~1326I~
//*******************************                                  //~1326I~
    private void showModal()                                       //~1215I~
    {                                                              //~1215I~
    	ActionEvent.resetDialogAction(this);                       //~1408R~
		shown=true;	//for do action lisntener's dispose()                                                //~1310I~//~1407R~
    	ajagoModal=Modal.show(this/*dialog*/,this/*dismissListener*/);//~1215R~                    //~1325R~//~1417R~//~@@@@R~
		modalOnSubthread=ajagoModal.isModalOnSubthread();          //~1410I~
                                                                   //~1410I~
    }                                                              //~1215I~
//**************************************************************   //~1411I~
//*get Modal when subthread Modal                             //~1411I~//~@@@@R~
//* notify ajogoModal before show() returns for the case Modal on Subthread//~1417I~
//  for subthreadModalActionPerform                                //~1417I~
//**************************************************************   //~1411I~
    public void onSubthreadDoAction(Modal PajagoModal)        //~1411I~//~2C26R~
    {                                                              //~1411I~
    	ajagoModal=PajagoModal;                                    //~1411I~
    }                                                              //~1411I~
//**************************************************************   //~1330I~
//*dismiss listener for modal dialog;callback from Modal      //~1330I~//~@@@@R~
//**************************************************************   //~1330I~
	@Override                                                      //~1330I~
    public void onDismissModalDialog(boolean PmodalOnSubthread)   //~1407R~
    {                                                              //~1330I~
		shown=false;                                               //~1330I~
        if (PmodalOnSubthread)                                     //~1407I~
        	afterDismiss=true;	//for isAfterDismiss for FileDialog//~1410R~
        else                                                       //~1407I~
        {                                                          //~1407I~
	        setAfterDismiss();	//set static for re-scheduled entry//~1407I~
          if (parentFrame!=null)                                   //~1A4sI~
			ActionEvent.redoFrameAction(parentFrame);              //~1407R~
        }                                                          //~1407I~
    }                                                              //~1330I~
//*****************                                                //~1214I~
    public void hide()                                             //~1214I~
    {                                                              //~1214I~
    	runOnUiThreadDlg(false,new ThreadData(CASE_HIDE,null));                                  //~1214I~//~1307R~//~1309R~
    }                                                              //~1214I~
    private void hideUI()                                          //~1214I~
    {                                                              //~1214I~
        androidDialog.hide();                          //~1214I~   //~1325R~
    }                                                              //~1214I~
//******************                                               //~1215I~
    public void setViewerText(Viewer Pviewer,String Ptext)         //~1215I~//~2C26R~//~@@@@R~
    {                                                              //~1215I~//~2C26R~//~@@@@R~
        Pviewer.append(Ptext);                                   //~2C26R~//~@@@@R~
    }                                                              //~1215I~//~2C26R~//~@@@@R~
//*****************                                                //~1125I~
//    public void add(String Ppos,Panel3D Ppanel3D)                  //~1124I~//~2C26R~
//    {                                                              //~1124I~//~2C26R~
//    }                                                              //~1124I~//~2C26R~
//    public void add(String Ppos,Viewer Pviewer)                    //~1124I~//~2C26R~
//    {                                                              //~1124I~//~2C26R~
//    }                                                              //~1124I~//~2C26R~
    public void add(String Ppos,Panel Ppanel)                      //~1124I~
    {                                                              //~1124I~
    }                                                              //~1124I~
    public void add(String Ppos,View Pview)                        //~1125I~
    {                                                              //~1125I~
    }                                                              //~1125I~
//*********************************************************        //~1330I~
//* from CloseDialog                                               //~1330I~
//* ignore,onClosing() only execute dispose()                      //~1330I~
//*********************************************************        //~1330I~
    public void addWindowListener(WindowListener Pwl)              //~1125I~//~1128I~
    {                                                              //~1125I~//~1128I~
    	windowlistener=Pwl;                                        //~1128I~
      	if (Pwl instanceof CloseDialog)	//get DoActionListener from CloseDialog for dialog button action redo                            //~1215I~//~1330R~
      	{                                                          //~1215I~//~1330R~
          	dialogDoActionListener=(DoActionListener)Pwl;                //~1215I~//~1330R~
      	}                                                          //~1215I~//~1330R~
    }                                                              //~1125I~//~1128I~
//******************                                               //~1127I~
    public void setBackground(Color Pcolor)                        //~1127I~
    {                                                              //~1127I~
//        View layout=AG.findViewById(AG.viewId_DialogBackground);   //~1404R~//~v101R~
        View layout=findViewById(AG.viewId_DialogBackground);      //~v101I~
        if (layout!=null)                                          //~1215I~
	    	layout.setBackgroundColor(Pcolor.getRGB());            //~1215I~
    }                                                              //~1127I~
//******************                                               //~1128I~//~1325R~//~1327R~
    public void setVisible(boolean Pvisible)                       //~1327R~
    {                                                              //~1128I~//~1325R~//~1327R~
        if (Dump.Y) Dump.println("setVisible "+dialogname+",shown="+shown);   //~1310I~//~1325R~//~1506R~
        if (Pvisible)                                              //~1327I~
	        show();                                                    //~1310R~//~1325R~//~1327R~
        Window.setVisible(this,Pvisible);                                 //~1128I~//~1325R~//~1327R~
    }                                                              //~1128I~//~1325R~//~1327R~
//******************                                               //~1327I~
	public void dispose()                                          //~1124I~//~1128I~//~1327I~
    {                                                              //~1124I~//~1128I~//~1327I~
    	if (shown)                                                 //~1327I~
			dismiss();                                             //~1327I~
    }                                                              //~1124I~//~1128I~//~1327I~
//******************                                               //~1214I~
    public void validate()                                         //~1310R~
    {                                                              //~1310I~
    }                                                              //~1310I~
//******************                                               //~1310I~
    public void dismiss()                                          //~1214I~
    {                                                              //~1214I~
    	runOnUiThreadDlg(false,new ThreadData(CASE_DISMISS,null));                               //~1214I~//~1307R~//~1309R~
    }                                                              //~1214I~
    private void dismissUI()                                       //~1214I~
    {                                                              //~1214I~
        if (Dump.Y) Dump.println("Dialog dismissUI androidDialog="+androidDialog.toString());//~@@@@I~
    	androidDialog.dismiss();                                   //~1214I~//~1325R~
    }                                                              //~1214I~
//******************                                               //~1306I~
	public Dimension getSize()                                     //~1117I~//~1216I~//~1306I~
    {                                                              //~1117I~//~1216I~//~1306I~
    	int x,y;                                                   //~1216I~//~1306I~
    	if (layoutview==null)                                 //~1216I~//~1306I~
        	x=y=0;                                                 //~1216I~//~1306I~
        else                                                       //~1216I~//~1306I~
        {                                                          //~1216I~//~1306I~
    		x=layoutview.getMeasuredWidth();                  //~1216R~//~1306I~
    		y=layoutview.getMeasuredHeight();                 //~1216I~//~1306I~
        }                                                          //~1216I~//~1306I~
        if (Dump.Y) Dump.println("Dialog getsize MesuredWidth x="+x+",y="+y);          //~1216I~//~1511R~
        if (Dump.Y) Dump.println("Dialog getsize GetWidthx="+layoutview.getWidth()+",y="+layoutview.getHeight());//~1511R~
    	return new Dimension(x,y);                                 //~1216I~//~1306I~
    }                                                              //~1117I~//~1216I~//~1306I~
//@@@@ for the case extension changed from CloseFrame to CloseDialog likeas MessageDialog temporary,see CloseFrame//~1311I~
	public void seticon (String file)                              //~1311I~
	{                                                              //~1113R~//~1311I~
    	//@@@@                                                     //~1311I~
    }                                                              //~1311I~
	public void doclose()                                          //~1311I~
	{                                                              //~1311I~
    	dismiss(); //@@@@ temporary,see CloseFrame                 //~1311I~
    }                                                              //~1311I~
//*****************************************************************************************//~1330I~
//*dismiss flag control                                            //~1330I~
    private boolean evaluateAfterDismiss()                         //~1410I~
    {                                                              //~1410I~
        boolean reentry=false;                                     //~1410I~
    //*********                                                    //~1410I~
        if (parentFrame!=null)                                     //~1410I~
        {                                                          //~1410I~
        	dialog_beforeDismiss=parentFrame.modalDialog_beforeDismiss;//~1410I~
        	if (dialog_beforeDismiss!=null                         //~1410I~
            && layoutresourceid==dialog_beforeDismiss.layoutresourceid              //~1410I~
            )                                                      //~1410I~
            {                                                      //~1410I~
	        	parentFrame.modalDialog_beforeDismiss=null;      //~1410I~
            	reentry=true;                                      //~1410I~
            }                                                      //~1410I~
        }                                                          //~1410I~
        if (Dump.Y) Dump.println("evaluateDismiss reentry="+reentry);//~1506R~
        return reentry;   //this instance is afterdismiss          //~1410I~
    }                                                              //~1410I~
    private void setAfterDismiss()                                 //~1410I~
    {                                                              //~1410I~
        if (Dump.Y) Dump.println("setAfterDismiss");               //~1506R~
        if (parentFrame!=null)                                     //~1410I~
        {                                                          //~1410I~
        	parentFrame.modalDialog_beforeDismiss=this;                 //~1410I~
        }                                                          //~1410I~
    }                                                              //~1410I~
	public  boolean isAfterDismiss()                               //~1410M~
    {                                                              //~1410M~
    	if (Dump.Y) Dump.println("Dialog:isAfterDismiss ="+afterDismiss);//~1506R~
    	return afterDismiss;                                       //~1410M~
    }                                                              //~1410M~
//*****************************************************************************************//~1330I~
//*TextField return "" when Modal dialog showing which requies input on "Input" TextField//~1330I~
//*****************************************************************************************//~1330I~
	public boolean isWaitForInput()                                //~1403R~
	{                                                              //~1330I~
    	boolean rc=                                                //~1403R~
                modalToGetInput	//layout is Modal dialog           //~1403I~
			&&  !modalOnSubthread	//subthread wait in the place  //~1410R~
//          &&  !swNoRecall                                        //~1411R~
            ;                                                      //~1330I~
        if (Dump.Y) Dump.println("Dialog isWaitForInput:"+rc);     //~1506R~
        return rc;                                                 //~1403I~
    }                                                              //~1330I~
//    public void getAt1STCall()                                   //~1411R~
//    {                                                            //~1411R~
//        swNoRecall=true;                                         //~1411R~
//    }                                                            //~1411R~
//*****************************************************************************************//~1410R~
//  for EditText.getText() of modal Dialog                         //~1410R~
//*****************************************************************************************//~1410R~
    public void modalSaveText(String Ptext)                 //~1410R~
    {                                                              //~1410R~
    	if (savedTextForModal==null)                                //~1410I~
			savedTextForModal=new ArrayList<String>();      //reset TextField input save//~1410I~
        savedTextForModal.add(Ptext);                              //~1410R~
        if (Dump.Y) Dump.println("Dialog:modalSaveText add="+Ptext);//~1506R~
    }                                                              //~1410R~
//*****************************************************************************************//~1410R~
    public String modalGetText()                            //~1410R~
    {                                                              //~1410R~
;                   //~1410I~
        if (dialog_beforeDismiss==null)                                          //~1410I~
        	return null;                                           //~1410I~
    	if (savedTextForModal==null)                                //~1410I~
			savedTextForModal=dialog_beforeDismiss.savedTextForModal;	//copy saved at before dismiss//~1410I~
        int ctr=savedTextForModal.size();                          //~1410R~
        if (Dump.Y) Dump.println("Dialog:modalGetText ctr="+ctr);  //~1506R~
        if (ctr==0)                                                //~1410R~
            return null;                                           //~1410R~
        String text=savedTextForModal.get(0);                      //~1410R~
        savedTextForModal.remove(0);                               //~1410R~
        if (Dump.Y) Dump.println("Dialog:modalGetText text="+text);//~1506R~
        return text;                                               //~1410R~
    }                                                              //~1410R~
//*****************************************************************************************//~1410I~
	public void setModalStatus_Show(boolean PsubthreadModal)       //~1410I~
    {                                                              //~1410I~
    	if (PsubthreadModal)                                       //~1410I~
        	modalStatus=SHOW_SUBTHREAD_MODAL;                      //~1410I~
        else                                                       //~1410I~
        	modalStatus=SHOW_MAINTHREAD_MODAL;                     //~1410I~
    }                                                              //~1410I~
	public boolean isSubthreadModal()                              //~1410I~
    {                                                              //~1410I~
    	return modalStatus==SHOW_SUBTHREAD_MODAL;                  //~1410I~
    }                                                              //~1410I~
	public void subthreadModalActionPerform(ActionEvent Pae)    //~1410R~
    {                                                              //~1410I~
    	ajagoModal.actionPerforme(Pae);                            //~1410R~
    }                                                              //~1410I~
//*****************************************************************************************//~@@@@I~
	public void setDismissActionListener(DoActionListener Pdal)    //~@@@@I~
    {                                                              //~@@@@I~
    	dismissListener=Pdal;   //called from AG.setDialogClosed   //~@@@@R~
    }                                                              //~@@@@I~
//*****************************************************************************************//~1A68I~
//*set dialog widow width/height                                   //~1A68I~
//*use after setContentView                                        //~1A68I~
//*0:wrap_content,-1:match_parent,else percent                     //~1A68I~
//*****************************************************************************************//~1A68I~
	public void setWindowSize(int Pratew,int Prateh,boolean PminWH/*apply for min(Width,Height)*/)//~1A68I~
    {                                                              //~1A68I~
    	int ww,hh,scrw,scrh;                                       //~1A68I~
        scrw=AG.scrWidth;                                          //~1A68I~
        scrh=AG.scrHeight;                                         //~1A68I~
        if (PminWH)                                                //~1A68I~
        {                                                          //~1A68I~
        	scrw=Math.min(scrw,scrh);                              //~1A68I~
        	scrh=scrw;                                             //~1A68I~
        }                                                          //~1A68I~
                                                                   //~1A68I~
        if (Pratew==-1)                                            //~1A68I~
        	ww=ViewGroup.LayoutParams.MATCH_PARENT;                //~1A68I~
        else                                                       //~1A68I~
        if (Pratew==0)                                             //~1A68I~
			ww=ViewGroup.LayoutParams.WRAP_CONTENT;                //~1A68I~
        else                                                       //~1A68I~
        	ww=scrw*Math.min(Pratew,100)/100;                      //~1A68I~
        if (Prateh==-1)                                            //~1A68I~
        	hh=ViewGroup.LayoutParams.MATCH_PARENT;                //~1A68I~
        else                                                       //~1A68I~
        if (Prateh==0)                                             //~1A68I~
			hh=ViewGroup.LayoutParams.WRAP_CONTENT;                //~1A68I~
        else                                                       //~1A68I~
        	hh=scrh*Math.min(Prateh,100)/100;                      //~1A68I~
        if (Dump.Y) Dump.println("Dialog:setWindowSize parm w="+Pratew+",h="+Prateh+",pix w="+ww+",h="+hh+",scr w="+AG.scrWidth+",h="+AG.scrHeight+",PminWH="+PminWH);//~1A68I~
        androidDialog.getWindow().setLayout(ww,hh);                //~1A68I~
    }                                                              //~1A68I~
}//class                                                           //~1112I~
