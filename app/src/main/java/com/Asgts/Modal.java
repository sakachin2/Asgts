//*CID://+@@@@R~: update#= 112;                                    //~1107R~//~@@@@R~
//**********************************************************************//~1107I~
//*Button Click Listener                                           //~1112R~
//**********************************************************************//~1107I~
package com.Asgts;                                         //~1107R~  //~1108R~//~1109R~//~2C26R~//~@@@@R~

import java.util.concurrent.CountDownLatch;

import jagoclient.Dump;
import jagoclient.gui.DoActionListener;                            //~1112R~
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import com.Asgts.UiThread;                                          //~2C26R~//~@@@@R~
import com.Asgts.UiThreadI;                                         //~2C26R~//~@@@@R~
import com.Asgts.ModalI;                                     //~1330I~//~2C26R~//~@@@@R~
import com.Asgts.awt.ActionEvent;                                   //~2C26R~//~@@@@R~
import com.Asgts.awt.Dialog;                                        //~2C26R~//~@@@@R~


//**********************************************************************//~1127I~
// Modal from UiThread                                             //~1410R~
//      UI Thread                                Sub Thread        //~1410I~
//         showModal                                               //~1127I~
//                            ---------->        runOnSubThread    //~1127I~
//                                                 Async Xfer to UI thread//~1311R~
//                            <----------                          //~1311R~
//         runOnUiThread                                          //~1127I~//~1311R~
//           show                                                  //~1127I~
//                                                                 //~1127I~
//         onDismiss                                               //~1127I~
//           countDown        ----------->                         //~1127I~
//                                                   await         //~1127I~
//                                                   afterDismiss()//~1311R~
// Modal from SubThread                                            //~1410I~
//      UI Thread                                Sub Thread        //~1410I~
//                                                                 //~1410I~
//                                               runOnSubThread    //~1410I~
//                                                 ASync Xfer to UI thread//~1410I~
//                            <----------                          //~1410I~
//         runOnUiThread                                           //~1410I~
//           show                                                  //~1410I~
//         ButtonAction                                            //~1410I~
//               ActionEvent  ---------->          await           //~1410I~
//                                                 DoACtion()(diapose())//~1410I~
//                            <----------                          //~1410I~
//         dismiss()                                               //~1410I~
//           (no DismissListener setup,Dialog dose it)                            //~1410I~//~@@@@R~
//**********************************************************************//~1127I~
public class Modal implements UiThreadI                                            //~1126R~//~1214R~//~@@@@R~
{                                                              //~1111I~//~1112I~
//*******************************                                  //~1126M~
//*******************************                                  //~1126M~
//*******************************                        
	public static final int FREE        =0;                        //~1127I~
	public static final int SHOWING     =1;                        //~1127I~
	public static final int DISMISS     =2;                        //~1127I~
	static private int modalstatus=FREE;                           //~1127R~
                                                                   //~1326I~
	DoActionListener doactionlistener=null;
	ModalI dismissListener;                                   //~1330I~//~@@@@R~
	String actioncmd=null;//~1126M~
    private Dialog dialog=null;
    RunOnSubThread subthread=null;//~1215I~
    private boolean modalOnSubthread=false;                        //~1410R~
    private CountDownLatch latch;                                  //~1410I~
    private ActionEvent actionEvent;                               //~1410I~
//**************                                                   //~1215I~
	private Modal(boolean PisMainThread,Dialog Pdialog,ModalI PdismissListener)//~1410R~//~@@@@R~
    {                                                              //~1330I~
    	dialog=Pdialog;                                            //~1330I~
    	dismissListener=PdismissListener;                          //~1330I~
		modalOnSubthread=!PisMainThread;                           //~1410I~
        if (PisMainThread)                                         //~1410I~
    		subthread=new RunOnSubThread();                        //~1410R~
    }                                                              //~1330I~
    public static Modal show(Dialog Pdialog,ModalI PdismissListener)//~1410R~//~@@@@R~
    {                                                              //~1330I~
    	boolean isMainThread=AG.isMainThread();                    //~1410I~
    	Modal ajagomodal=new Modal(isMainThread,Pdialog,PdismissListener);//~1410R~//~@@@@R~
    	if (isMainThread)                                          //~1410R~
        {                                                          //~1330I~
        	if (Dump.Y) Dump.println("Modal:start show dialog on mainthread");//~1506R~//~@@@@R~
        	ajagomodal.subthread.init(ajagomodal);                 //~1330I~
        	new Thread(ajagomodal.subthread).start();              //~1330I~
        }                                                          //~1330I~
        else	//already in sub thread                            //~1330I~
        {                                                          //~1330I~
        	if (Dump.Y) Dump.println("Modal:start show dialog on subthread");//~1506R~//~@@@@R~
		    ajagomodal.showOnUiThread();                           //~1330I~
        }                                                          //~1330I~
        return ajagomodal;	//waiting until dismiss                //~1410R~
    }                                                              //~1330I~
//*******************************                                  //~1126M~//~1215M~
//*sub thread start point                                          //~1126M~//~1215M~
//*******************************                                  //~1126M~//~1215M~
    public class RunOnSubThread                                     //~1126M~//~1127R~//~1215M~
			implements Runnable                                    //~1126M~//~1215M~
    {                                                              //~1215M~
    	Modal ajagomodal;                                          //~@@@@R~
    	public RunOnSubThread()
    	{
    	}
    	public void init(Modal Pajagomodal)     //~1127R~//~1215I~ //~@@@@R~
    	{                                                          //~1215M~
    		ajagomodal=Pajagomodal;//~1126M~                       //~1215I~
    	}                                                          //~1215M~
        @Override                                                  //~1126M~//~1215M~
        public void run()                                          //~1126M~//~1215M~
        {                                                          //~1126M~//~1215M~
			ajagomodal.showOnUiThread();                                    //~1127I~//~1215I~
        }                                                          //~1126M~//~1215M~
    }                                                              //~1126M~//~1215M~
//***********************************************                  //~1215I~
//*show dialog on UI thread and wait dismiss                       //~1215I~
//***********************************************                  //~1215I~
    private void showOnUiThread()                                  //~1215I~
    {                                                              //~1215I~
		if (modalOnSubthread)                                      //~1411R~
        	dialog.onSubthreadDoAction(this);	//notfy Modal to Dialog to DoAction berfor ActionPerformed//~1411I~//~@@@@R~
        else                                                       //~1411I~
        	if (modalstatus==SHOWING)	//duplicate modal req                                     //~1215I~//~1407R~
            {                                                      //~1408I~
            	if (Dump.Y) Dump.println("duplicated Modal req");  //~1506R~
            	return;                                            //~1407R~
            }                                                      //~1408I~
        latch=new CountDownLatch(1/*posted by counddown once*/);   //~1410R~
        actionEvent=null;                                          //~1410I~
    	UiThread.runOnUiThreadXfer(this,latch); //request callback runOnUiThread and wait post//~@@@@R~
        try                                                        //~1215I~
        {                                                          //~1215I~
        	latch.await();   //subthread wakeup by dismiss or ActionEvent(when SubThreadModal)//~1410R~
	        if (Dump.Y) Dump.println("Modal:posted wait");    //~1506R~//~@@@@R~
        }                                                          //~1215I~
        catch (InterruptedException e)                             //~1215I~
        {                                                          //~1215I~
        	Dump.println(e,"Modal Thread Interrupted ");      //~1410R~//~@@@@R~
        }                                                          //~1215I~
		if (modalOnSubthread)                                      //~1410I~
        {                                                          //~1410I~
        	                                                       //~1411I~
        	if (actionEvent!=null)                                 //~1410I~
            {                                                      //~1411I~
	        	ActionEvent.actionPerform(0/*ActionTranslator*/,actionEvent);//~1411R~
            }                                                      //~1411I~
            afterDoAction();                                       //~1410I~
        }                                                          //~1410I~
        else                                                       //~1410I~
        	afterDismiss();                                        //~1410R~
    }                                                              //~1215I~
//**************                                                   //~1215I~
    @Override                                                      //~1215I~
    public void runOnUiThread(Object Pparm)//callback from RunOnUiThread//~1407R~
    {   
    	android.app.Dialog androidDialog=dialog.androidDialog;//~1215I~
    	if (Pparm instanceof CountDownLatch)                       //~1220I~
        {                                                          //~1220I~
            CountDownLatch latch=(CountDownLatch)Pparm;                //~1215I~//~1220R~
			if (!modalOnSubthread)                                 //~1410I~
            	androidDialog.setOnDismissListener(new dismissListener(latch));   //~1215I~//~1410R~
            dialog.setModalStatus_Show(modalOnSubthread);           //~1410I~
            androidDialog.show();                                             //~1215I~//~1220R~
            modalstatus=SHOWING;                                       //~1215I~//~1220R~
        }                                                          //~1220I~
        else                                                       //~1330R~
    	if (Pparm instanceof ModalI)                          //~1330I~//~@@@@R~
        {                                                          //~1330I~
			dismissListener.onDismissModalDialog(modalOnSubthread);//~1407R~
        }                                                          //~1330I~
    }                                                              //~1215I~
//****************************************************             //~1410I~
//*EventAction request on SubThread from ActionEvent               //~1410I~
//****************************************************             //~1410I~
    public void actionPerforme(ActionEvent Pae)                    //~1410R~
    {                                                              //~1410I~
        actionEvent=Pae;                                           //~1410I~
		if (Dump.Y) Dump.println("ActionPerformed for subthreadModal countdown");//~1506R~//+@@@@R~
        latch.countDown();  //count exausted,post latch.await()    //~1410I~
    }                                                              //~1410I~
//*******************************                                  //~1126M~//~1215M~
//*dismiss listener                                                //~1126I~//~1215M~
//*******************************                                  //~1126I~//~1215M~
    public class dismissListener                                   //~1126M~//~1215M~
    		implements OnDismissListener                           //~1126M~//~1215M~
	{                                                              //~1126M~//~1215M~
    	CountDownLatch latch;                                      //~1126M~//~1215M~
	    public dismissListener(CountDownLatch Platch)              //~1126M~//~1215M~
        {                                                          //~1126M~//~1215M~
	    	latch=Platch;                                          //~1126M~//~1215M~
        }                                                          //~1126M~//~1215M~
        @Override                                                  //~1126M~//~1215M~
        public void onDismiss(DialogInterface Pdialog)             //~1126M~//~1215M~
        {                                                          //~1126M~//~1215M~
			if (Dump.Y) Dump.println("Modal dismiss scheduled,latch countdown");        //~1127I~//~1506R~//+@@@@R~
          try                                                      //~@@@@I~
          {                                                        //~@@@@I~
            latch.countDown();  //count exausted,post latch.await()//~1126M~//~1215M~
          }
          catch(Exception e)                                       //~@@@@I~
          {                                                        //~@@@@I~
    		Dump.println(e,"Modal:onDismiss");                     //~@@@@I~
          }                                                        //~@@@@I~
        }                                                          //~1126M~//~1215M~
    }                                                              //~1126M~//~1215M~
//**********************************                               //~1126I~//~1127M~//~1215M~
//*show dialog then wait dismiss                                   //~1126I~//~1127M~//~1215M~
//**********************************                               //~1126I~//~1127M~//~1215M~
	private void afterDismiss()                                   //~1126I~//~1127M~//~1410R~
    {                                                              //~1127M~//~1215M~
//        AG.setDialogClosed();                                      //~1326I~//~@@@@R~
        AG.setDialogClosed(dialog);                                //~@@@@I~
        setAfterDismiss();                                         //~1410R~
		if (Dump.Y) Dump.println("Modal:afterDismiss cancelflag="+dialog.canceled);                        //~1127I~//~1215M~//~1506R~//~@@@@R~
		if (Dump.Y) Dump.println("Modal:afterDismiss callback doAction");               //~1127I~//~1215M~//~1506R~//~@@@@R~
//      AjagoUiThread.runOnUiThreadXfer(this,doactionlistener); //excute DoAction On UI thread//~1220R~//~1330R~
        UiThread.runOnUiThreadXfer(this,dismissListener); //excute DoAction On UI thread//~1330I~//~@@@@R~
    }                                                              //~1126I~//~1127M~//~1215M~
//**********************************                               //~1410I~
//*show dialog then wait dismiss                                   //~1410I~
//**********************************                               //~1410I~
	private void afterDoAction()                                   //~1410I~
    {                                                              //~1410I~
//        AG.setDialogClosed();                                      //~1410I~//~@@@@R~
        AG.setDialogClosed(dialog);                                //~@@@@I~
	    resetAfterDismiss();                                        //~1410I~
		if (Dump.Y) Dump.println("afterDismiss cancelflag="+dialog.canceled);//~1506R~
		if (Dump.Y) Dump.println("callback doAction");             //~1506R~
		dismissListener.onDismissModalDialog(modalOnSubthread);    //~1410I~
    }                                                              //~1410I~
//********************                                             //~1215I~
//**********************************                               //~1127I~//~1215R~
    private void setAfterDismiss()                           //~1127I~//~1215R~//~1326R~
    {                                                              //~1127I~//~1215R~
        modalstatus=DISMISS;                              //~1127I~//~1215R~//~1326R~
    }                                                              //~1127I~//~1215R~
    public static void resetAfterDismiss()                         //~1324I~
    {                                                              //~1324I~
        modalstatus=FREE;	//for the case recursive modal dialog from afterdismiss DoActionListener//~1324I~
    }                                                              //~1324I~
    public boolean isModalOnSubthread()                            //~1410I~
    {                                                              //~1410I~
		return modalOnSubthread;                                   //~1410I~
    }                                                              //~1410I~
}//class                                                              //~1111I~//~1112I~
