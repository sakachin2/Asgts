//*CID://+1A2jR~: update#= 121;                                    //+1A2jR~
//**********************************************************************//~1A2jI~
//1A2j 2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//                that cause RunTimeException not looper           //~1A2jR~
//**********************************************************************//~1107I~
//*main view                                                       //~1107I~
//**********************************************************************//~1107I~
package com.Asgts;                                         //~1107R~  //~1108R~//~1109R~//~@@@@R~//~@@@2R~

import jagoclient.Dump;

import com.Asgts.AG;                                                //~@@@@R~//~@@@2R~
import com.Asgts.R;                                                 //~@@@@R~//~@@@2R~

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
//**********************************************************************//~1107I~
public class ProgDlg                                            //~1107R~//~1211R~//~@@@@R~//~@@@2R~
	implements URunnableI                                          //~1A2jI~
{                                                                  //~0914I~
	private static final int RUNUI_NOCALLBACK=1;                   //~1A2jI~
	private static final int RUNUI_CALLBACK=2;                     //~1A2jI~
	private ProgDlgI callback;                                  //~1425R~//~@@@@R~//~@@@2R~
	ProgressDialog androidDialog;                                  //~@@@2I~
    private boolean dismissCallback=true;                                  //~@@@2I~
    private boolean showing;                                       //~@@@2I~
    private int mtTitleid;                                         //~1A2jI~
    private int mtMsgid;                                           //~1A2jI~
    private boolean mtCancelable;                                  //~1A2jI~
    private boolean mtAGPtr;                                       //~1A2jI~
    private boolean mtCancelCB;                                       //~1A2jI~
    private String mtMsg;                                         //~1A2jI~
	private ProgDlgI mtDlgI;                                       //~1A2jI~
//**********************************                               //~1211I~
//*                                                                //~1211R~
//**********************************                               //~1211I~
	private ProgDlg()                                              //~1A2jR~
    {                                                              //~1A2jI~
    }                                                              //~1A2jI~
	private void initAndroid()                       //~1211I~ //~@@@@R~     //~@@@2R~//~1A2jR~
    {                                                              //~1211I~
    	androidDialog=new ProgressDialog(AG.activity);             //~@@@2I~
        androidDialog.setOnDismissListener((OnDismissListener) new dismissListener()); //~1326I~//~@@@@R~//~@@@2I~
    }                                                              //~1211I~
//**********************************                               //~@@@2I~
//* spinner type                                                   //~@@@2I~
//**********************************                               //~@@@2I~
	private ProgDlg(int Ptitle,int Pmsg,boolean Pcancelable)        //~@@@2R~//~1A2jR~
    {                                                              //~@@@2I~
    	this(Ptitle!=0?AG.resource.getString(Ptitle):"",Pmsg!=0?AG.resource.getString(Pmsg):"",Pcancelable);//~@@@2R~
    }                                                              //~@@@2I~
//**********************************                               //~@@@2I~
	private ProgDlg(int Ptitle,String Pmsg,boolean Pcancelable)     //~@@@2I~//~1A2jR~
    {                                                              //~@@@2I~
    	this(Ptitle!=0?AG.resource.getString(Ptitle):"",Pmsg,Pcancelable);//~@@@2I~
    }                                                              //~@@@2I~
//**********************************                               //~@@@2I~
	private ProgDlg(String Ptitle,String Pmsg,boolean Pcancelable)  //~@@@2I~//~1A2jR~
    {                                                              //~@@@2I~
    	this();                                                    //~@@@2I~
        initAndroid();                                             //~1A2jI~
        if (Ptitle!=null)                                          //~@@@2I~
	        androidDialog.setTitle(Ptitle);                        //~@@@2I~
        if (Pmsg!=null)                                            //~@@@2I~
	        androidDialog.setMessage(Pmsg);                        //~@@@2I~
	    androidDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);    //~@@@2I~
	    androidDialog.setIndeterminate(true);                      //~@@@2I~
	    androidDialog.setCancelable(Pcancelable);                  //~@@@2I~
        setButton();                                               //~@@@2I~
    }                                                              //~@@@2I~
//**********************************                               //~@@@2I~
	private void setCallback(ProgDlgI Pcallback,boolean Pcancelcallback,boolean Pdismisscallback)//~@@@2R~//~1A2jR~
    {                                                              //~@@@2I~
    	callback=Pcallback;                                        //~@@@2I~
        dismissCallback=Pdismisscallback;                           //~@@@2I~
        if (Pcancelcallback)                                       //~@@@2I~
        {                                                          //~@@@2I~
            androidDialog.setOnCancelListener                      //~@@@2R~
            (                                                      //~@@@2R~
                new DialogInterface.OnCancelListener()             //~@@@2R~
                {                                                  //~@@@2R~
                    @Override                                      //~@@@2R~
                    public void onCancel(DialogInterface Pdlg)      //~@@@2R~
                    {                                              //~@@@2R~
                        if (Dump.Y) Dump.println("ProgressDialog onCancel");//~@@@2R~
					    showing=false;                             //~@@@2I~
                        callback(0);                               //~@@@2R~
                        androidDialog.dismiss();                                 //~@@@2I~
                    }                                              //~@@@2R~
                }                                                  //~@@@2R~
            );                                                     //~@@@2R~
        }                                                          //~@@@2I~
        if (Dump.Y) Dump.println("ProgDlg setcallback");           //~@@@2I~
    }                                                              //~@@@2I~
//*******************************                                  //~@@@2I~
	private void setButton()                                       //~@@@2I~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("ProgressDialog setButton");      //~@@@2I~
        androidDialog.setButton(                                   //~@@@2I~
        		DialogInterface.BUTTON_NEGATIVE,                   //~@@@2I~
                AG.resource.getString(R.string.Close),             //~@@@2R~
                new OnClickListener()              //~@@@2I~
                	{                                              //~@@@2I~
                    	@Override                                  //~@@@2I~
                        public void onClick(DialogInterface Pdlg,int Pid)//~@@@2I~
                        {                                          //~@@@2I~
					        if (Dump.Y) Dump.println("ProgressDialog CancelButton Click");//~@@@2I~
                        	androidDialog.cancel();                 //~@@@2I~
                        }                                          //~@@@2I~
                    }
        		);//~@@@2I~
    }                                                              //~@@@2I~
//*******************************                                  //~@@@2I~
	public void dismiss(boolean Pdismisscallback)                  //~@@@2R~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("ProgressDialog dismiss");        //~@@@2I~
        dismissCallback=Pdismisscallback;                          //~@@@2I~
        androidDialog.dismiss();                                   //~@@@2I~
        showing=false;                                             //~@@@2I~
    }                                                              //~@@@2I~
//*******************************                                  //~@@@2I~
//*callback                                                        //~@@@2I~
//*parm 0:cancel,1:dismiss                                         //~@@@2I~
//*******************************                                  //~@@@2I~
	private void callback(int Preason)                             //~@@@2I~
    {                                                              //~@@@2I~
    	try                                                        //~@@@2I~
        {                                                          //~@@@2I~
        	if (callback!=null)                                    //~@@@2I~
	        	callback.onCancelProgDlg(Preason);          //~@@@2I~
        }                                                          //~@@@2I~
        catch(Exception e)                                         //~@@@2I~
        {                                                          //~@@@2I~
    		Dump.println(e,"ProgressDialog:callback="+Preason);    //~@@@2I~
        }                                                          //~@@@2I~
    }                                                              //~@@@2I~
//*******************************                                  //~1326I~//~@@@2I~
//* show in-modal dialog                                           //~1330I~//~@@@2I~
//*******************************                                  //~1330I~//~@@@2I~
    private void show()	                                          //~1214M~//~1330R~//~@@@2I~//~1A2jR~
    {                                                              //~1214M~//~@@@2I~
    	androidDialog.show();                                      //~1309R~//~1325R~//~@@@2I~
	    showing=true;                                              //~@@@2I~
        if (Dump.Y) Dump.println("ProgDlg show");                  //~@@@2I~
    }                                                              //~1214M~//~@@@2I~
    public boolean isShowing()                                     //~@@@2I~
    {                                                              //~@@@2I~
	    return showing;                                            //~@@@2I~
    }                                                              //~@@@2I~
//****************************************                                  //~1326I~//~1330R~//~@@@2I~
//*dismiss listener for inmodal dialog                                                //~1326I~//~1330R~//~@@@2I~
//****************************************                         //~1326I~//~1330R~//~@@@2I~
    public class dismissListener                                   //~1326I~//~@@@2I~
    		implements OnDismissListener                           //~1326I~//~@@@2I~
	{                                                              //~1326I~//~@@@2I~
        @Override                                                  //~1326I~//~@@@2I~
        public void onDismiss(DialogInterface Pdialog)             //~1326I~//~@@@2I~
        {                                                          //~1326I~//~@@@2I~
			if (Dump.Y) Dump.println("ProgDialog dialog dismiss listener dismisscallback="+dismissCallback); //~1326I~//~1506R~//~@@@2I~
            if (dismissCallback)                                   //~@@@2I~
            	callback(1);                                       //~@@@2I~
		    showing=false;                                          //~@@@2I~
        	AG.progDlg=null;                                       //~@@@2M~
        }                                                          //~@@@2I~
    }                                                              //~1326I~//~@@@2I~
//****************************************                         //~@@@2I~
    public static boolean dismiss()                                //~@@@2I~
    {                                                              //~@@@2I~
    	boolean rc=false;                                          //~@@@2I~
    	if (AG.progDlg!=null && AG.progDlg.isShowing())            //~@@@2R~
    	{                                                          //~@@@2I~
        	AG.progDlg.dismiss(false);                             //~@@@2I~
            rc=true;                                               //~@@@2I~
        }                                                          //~@@@2I~
    	if (Dump.Y) Dump.println("ProgDlg static dismiss rc="+rc); //~@@@2I~
        return rc;                                                 //~@@@2I~
    }                                                              //~@@@2I~
//****************************************                         //~1A2jI~
//*force to execute on MainThread                                  //~1A2jI~
//****************************************                         //~1A2jI~
//from GameQuestion                                                //~1A2jI~
//****************************************                         //~1A2jI~
    public static void showProgDlg(boolean Pagptr,int Ptitleid,int Pmsgid,boolean Pcancelable)//~1A2jI~
    {                                                              //~1A2jI~
    	ProgDlg pd=new ProgDlg();	//empty                                //~1A2jI~
        pd.mtTitleid=Ptitleid;                                     //~1A2jI~
        pd.mtMsgid=Pmsgid;                                         //~1A2jI~
        pd.mtCancelable=Pcancelable;                               //~1A2jI~
        pd.mtAGPtr=Pagptr;                                         //~1A2jI~
        URunnable.setRunFuncDirect(pd/*Runnable*/,pd/*objparm*/,RUNUI_NOCALLBACK/*int parm*/);//~1A2jR~
    }                                                              //~1A2jI~
    public void showProgDlgUI(boolean Pagptr,int Ptitleid,int Pmsgid,boolean Pcancelable)//~1A2jI~
    {                                                              //~1A2jI~
    	ProgDlg pd=new ProgDlg(Ptitleid,Pmsgid,Pcancelable);	//empty    //~1A2jI~
        if (Pagptr)                                                //~1A2jI~
        	AG.progDlg=pd;                                         //~1A2jI~
        pd.show();                                                 //~1A2jI~
    }                                                              //~1A2jI~
//****************************************                         //~1A2jI~
//from IPConnection/BTConnection                                   //~1A2jI~
//****************************************                         //~1A2jI~
    public static void showProgDlg(ProgDlgI Ppdi,boolean Pcancelcb,int Ptitleid,String Pmsg,boolean Pcancelable)//~1A2jR~
    {                                                              //~1A2jI~
    	ProgDlg pd=new ProgDlg();	//empty                        //~1A2jI~
        pd.mtTitleid=Ptitleid;                                     //~1A2jI~
        pd.mtMsg=Pmsg;                                             //~1A2jI~
        pd.mtCancelable=Pcancelable;                               //~1A2jI~
        pd.mtCancelCB=Pcancelcb;                                   //~1A2jR~
        pd.mtDlgI=Ppdi;                                            //~1A2jI~
        URunnable.setRunFuncDirect(pd/*Runnable*/,pd/*objparm*/,RUNUI_CALLBACK/*int parm*/);//~1A2jI~
    }                                                              //~1A2jI~
    public static void showProgDlg(ProgDlgI Ppdi,boolean PcancelCB,int Ptitleid,int Pmsgid,boolean Pcancelable)//~1A2jR~
    {                                                              //~1A2jI~
		showProgDlg(Ppdi,PcancelCB,Ptitleid,AG.resource.getString(Pmsgid),Pcancelable);//~1A2jR~
    }                                                              //~1A2jI~
    public void showProgDlgUI(ProgDlgI Pdlgi,boolean Pcancelcb,int Ptitleid,String Pmsg,boolean Pcancelable)//~1A2jR~
    {                                                              //~1A2jI~
    	ProgDlg pd=new ProgDlg(Ptitleid,Pmsg,Pcancelable);	//empty//~1A2jI~
        pd.setCallback(Pdlgi,Pcancelcb/*cancel CB*/,false/*dismisscallback*/);//~1A2jR~
        AG.progDlg=pd;                                             //~1A2jR~
        pd.show();                                                 //~1A2jI~
    }                                                              //~1A2jI~
//===============================================================================//~@@@2I~//~1A2jI~
//=run on UIThread                                                 //~@@@2I~//~1A2jI~
//===============================================================================//~@@@2I~//~1A2jI~
	public void runFunc(Object Pobj,int Pint)                           //~@@@2I~//~1A2jI~
    {                                                              //~@@@2I~//~1A2jI~
    	ProgDlg pd=(ProgDlg)Pobj;                                  //~1A2jI~
        int caseno=Pint;                                           //~1A2jI~
        if (caseno==RUNUI_NOCALLBACK)                              //~1A2jI~
        {                                                          //~1A2jI~
			showProgDlgUI(pd.mtAGPtr,pd.mtTitleid,pd.mtMsgid,pd.mtCancelable);//~1A2jI~
        }                                                          //~1A2jI~
        else                                                       //~1A2jI~
        if (caseno==RUNUI_CALLBACK)                                //~1A2jI~
        {                                                          //~1A2jI~
			showProgDlgUI(pd.mtDlgI,pd.mtCancelCB,pd.mtTitleid,pd.mtMsg,pd.mtCancelable);//~1A2jR~
        }                                                          //~1A2jI~
    }                                                              //~@@@2I~//~1A2jI~
}//class ProgDlg                                                //~1211R~//~@@@@R~//~@@@2R~
