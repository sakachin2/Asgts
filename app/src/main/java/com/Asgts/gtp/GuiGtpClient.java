//*CID://+1Ah0R~:                             update#=   51;       //~v1B6I~//~1Ah0R~
//*************************************************************************//~v1B6I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*************************************************************************//~v1B6I~
// GuiGtpClient.java

//package net.sf.gogui.gui;                                        //~v1B6R~
package com.Asgts.gtp;                                             //~v1B6R~

//import java.awt.Component;                                       //~v1B6R~

import jagoclient.Dump;

import java.text.MessageFormat;                                  //~v1B6R~


//import javax.swing.SwingUtilities;                               //~v1B6R~
import com.Asgts.Alert;                                       //~v1B6R~
import com.Asgts.AlertI;                                      //~v1B6R~
import com.Asgts.UiThread;                                    //~v1B6R~
import com.Asgts.R;                                                //~v1B6R~
import com.Asgts.gtp.TimeSettings;                                 //~v1B6R~
//import com.Asgts.gtp.ConstBoard;                                 //~v1B6R~
import com.Asgts.gtp.Komi;                                         //~v1B6R~
//import com.Asgts.gtp.Move;                                       //~v1B6R~
import com.Asgts.gtp.GtpClient;                                    //~v1B6R~
import com.Asgts.gtp.GtpClientBase;                                //~v1B6R~
import com.Asgts.gtp.GtpError;                                     //~v1B6R~
import com.Asgts.gtp.GtpSynchronizer;                              //~v1B6R~


//import com.Asgts.gtp.GTPConnector;                               //~v1B6R~
//import static net.sf.gogui.gui.I18n.i18n;                        //~v1B6R~
import static com.Asgts.gtp.GoGui.Rstr;                            //~v1B6R~

/** Wrapper around gtp.GtpClient to be used in a GUI environment.
    Allows to send fast commands with the GtpClientBase.send() function
    immediately in the event dispatch thread and potentially slow commands in
    a separate thread with a callback in the event thread after the command
    finished.
    Fast commands are ones that the Go engine is supposed to answer quickly
    (like boardsize, play and undo), however they have a timeout to
    prevent the GUI to hang, if the program does not respond.
    After the timeout a dialog is opened that allows to kill the program or
    continue to wait.
    This class also contains a GtpSynchronizer. */
public class GuiGtpClient
    extends GtpClientBase
{
//  public GuiGtpClient(GtpClient gtp, Component owner,            //~v1B6R~
    public GuiGtpClient(GtpClient gtp, Object owner,               //~v1B6R~
                        GtpSynchronizer.Listener listener,
                        MessageDialogs messageDialogs)             //~v1B6R~
                                                               //~v1B6I~
    {
        m_gtp = gtp;
//      m_owner = owner;                                           //~v1B6R~
        m_owner = (GoGui)owner;                                    //~v1B6I~
//      m_messageDialogs = messageDialogs;                         //~v1B6R~
        m_gtpSynchronizer = new GtpSynchronizer(this, listener, false);
        Thread thread = new Thread() {
                public void run() {
                    synchronized (m_mutex)
                    {
                        boolean firstWait = true;
                        while (true)
                        {
                            try
                            {
                                if (m_command == null || ! firstWait)
                                {                                  //~1Ah0I~
                                	if (Dump.Y) Dump.println("GuiGtpClient:run mutex wait");//~1Ah0I~
                                    m_mutex.wait();
                                	if (Dump.Y) Dump.println("GuiGtpClient:run mutex wait posted");//~1Ah0I~
                                }                                  //~1Ah0I~
                            }
                            catch (InterruptedException e)
                            {
                                System.err.println("Interrupted");
                            }
                            firstWait = false;
                            m_response = null;
                            m_exception = null;
                            try
                            {
                                if (Dump.Y) Dump.println("GuiGtpClient:run send cmd="+m_command);//~1Ah0I~
                                m_response = m_gtp.send(m_command);
                            }
                            catch (GtpError e)
                            {
                                Dump.println(e,"GuiGtpClient:run exception by send cmd="+m_command);//~1Ah0I~
                                m_exception = e;
                            }
//                          SwingUtilities.invokeLater(m_callback);//~v1B6R~
							if (m_callback!=null)                  //~v1B6I~
	                            UiThread.invokeLater(m_callback);//~v1B6R~
                        }
                    }
                }
            };
        thread.start();
    }

    public void close()
    {
        if (! isProgramDead())
        {
            m_gtp.close();
            TimeoutCallback timeoutCallback = new TimeoutCallback(null);
            m_gtp.waitForExit(TIMEOUT, timeoutCallback);
        }
    }

    public void destroyGtp()
    {
        m_gtp.destroyProcess();
    }

    public boolean getAnyCommandsResponded()
    {
        return m_gtp.getAnyCommandsResponded();
    }

    /** Get exception of asynchronous command.
        You must call this before you are allowed to send new a command. */
    public GtpError getException()
    {
        synchronized (m_mutex)
        {
//            assert SwingUtilities.isEventDispatchThread();       //~v1B6R~
//            assert m_commandInProgress;                          //~1Ah0R~
            if (Dump.Y) Dump.println("GuiGtpClient:getException cmd-in-progress before="+m_commandInProgress);//~1Ah0I~
            m_commandInProgress = false;
            if (Dump.Y) Dump.println("GuiGtpClient:getException cmd-in-progress after ="+m_commandInProgress);//~1Ah0I~
            return m_exception;
        }
    }

    public String getProgramCommand()
    {
        return m_gtp.getProgramCommand();
    }

    /** Get response to asynchronous command.
        You must call getException() first. */
    public String getResponse()
    {
        synchronized (m_mutex)
        {
//            assert SwingUtilities.isEventDispatchThread();       //~v1B6R~
//            assert ! m_commandInProgress;                        //~1Ah0R~
            return m_response;
        }
    }

//  public void initSynchronize(ConstBoard board, Komi komi,       //~v1B6R~
//                              TimeSettings timeSettings) throws GtpError//~v1B6R~
    public void initSynchronize(int Psize,Komi komi,TimeSettings timeSettings) throws GtpError//~v1B6R~
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        assert ! m_commandInProgress;                            //~1Ah0R~
//      m_gtpSynchronizer.init(board, komi, timeSettings);         //~v1B6R~
        m_gtpSynchronizer.init(Psize,komi,timeSettings);           //~v1B6R~
    }

    public boolean isCommandInProgress()
    {
        return m_commandInProgress;
    }

    public boolean isOutOfSync()
    {
        return m_gtpSynchronizer.isOutOfSync();
    }

    public boolean isProgramDead()
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
        return m_gtp.isProgramDead();
    }

    /** Send asynchronous command. */
    public void send(String command, Runnable callback)
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        if (!AG.isMainThread())                                  //~v1B6R~
//        {                                                        //~v1B6R~
//            sendUI(command,callback);                            //~v1B6R~
//            return;                                              //~v1B6R~
//        }                                                        //~v1B6R~
//        assert ! m_commandInProgress;                            //~1Ah0R~
		if (Dump.Y) Dump.println("GuiGtpClient:send with mutex cmd="+command);//~1Ah0I~
        synchronized (m_mutex)
        {
            m_command = command;
            m_callback = callback;
            m_commandInProgress = true;
			if (Dump.Y) Dump.println("GuiGtpClient:send with mutex notifyAll cmd="+command);//~1Ah0I~
            m_mutex.notifyAll();
        }
    }
//    private void sendUI(final String command, final Runnable callback)//~v1B6R~
//    {                                                            //~v1B6R~
//        AjagoUiThread.runOnUiThread(new AjagoUiThreadI()         //~v1B6R~
//                        {                                        //~v1B6R~
//                            String UIcommand=command;            //~v1B6R~
//                            Runnable UIcallback=callback;        //~v1B6R~
//                            public void runOnUiThread(Object Pany)//~v1B6R~
//                            {                                    //~v1B6R~
//                                try                              //~v1B6R~
//                                {                                //~v1B6R~
//                                    send(UIcommand,UIcallback);  //~v1B6R~
//                                }                                //~v1B6R~
//                                catch(Exception e)               //~v1B6R~
//                                {                                //~v1B6R~
//                                    Dump.println(e,"sendUI");    //~v1B6R~
//                                }                                //~v1B6R~
//                            }                                    //~v1B6R~
//                        },this/*Pany*/);                         //~v1B6R~

//    }                                                            //~v1B6R~
    public void sendComment(String comment)
    {
        m_gtp.sendComment(comment);
    }

    /** Send command in event dispatch thread. */
    public String send(String command) throws GtpError
    {
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        assert ! m_commandInProgress;                            //~1Ah0R~
        TimeoutCallback timeoutCallback = new TimeoutCallback(command);
        if (Dump.Y) Dump.println("GuiGtpClient send with timeout="+TIMEOUT+",cmd="+command);//~1Ah0I~
        return m_gtp.send(command, TIMEOUT, timeoutCallback);
    }

    public void setAutoNumber(boolean enable)
    {
        m_gtp.setAutoNumber(enable);
    }

//    public void synchronize(ConstBoard board, Komi komi,         //~v1B6R~
//                            TimeSettings timeSettings) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
////        assert SwingUtilities.isEventDispatchThread();         //~v1B6R~
//        assert ! m_commandInProgress;                            //~v1B6R~
//        m_gtpSynchronizer.synchronize(board, komi, timeSettings);//~v1B6R~
//    }                                                            //~v1B6R~

//    public void updateAfterGenmove(ConstBoard board)             //~v1B6R~
//    {                                                            //~v1B6R~
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        assert ! m_commandInProgress;                            //~v1B6R~
//        m_gtpSynchronizer.updateAfterGenmove(board);             //~v1B6R~
//    }                                                            //~v1B6R~

//    public void updateHumanMove(ConstBoard board, Move move) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
//        assert SwingUtilities.isEventDispatchThread();           //~v1B6R~
//        assert ! m_commandInProgress;                            //~v1B6R~
//        m_gtpSynchronizer.updateHumanMove(board, move);          //~v1B6R~
//    }                                                            //~v1B6R~

    public void waitForExit()
    {
        m_gtp.waitForExit();
    }

    public boolean wasKilled()
    {
        return m_gtp.wasKilled();
    }

    private class TimeoutCallback
        implements GtpClient.TimeoutCallback
    {
        private Alert alertAskContinue;                            //~v1B6I~//~1Ah0I~
        TimeoutCallback(String command)
        {
            m_command = command;
        }

        public boolean askContinue()
        {
//          String mainMessage = i18n("MSG_PROGRAM_NOT_RESPONDING");//~v1B6R~
//          String mainMessage = Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING);//~v1B6R~
            String optionalMessage;                                //~v1B6R~
            if (Dump.Y) Dump.println("GuiGtpClient:askContinue swInit="+m_owner.swInitialized);//~v1B6I~//~1Ah0R~
            if (!m_owner.swInitialized)                            //~v1B6R~
            {                                                      //~v1B6I~
                optionalMessage=MessageFormat.format(Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING_4),m_command);//~v1B6I~
                AlertI cb=new AlertI()                   //~v1B6I~
                				{                                  //~v1B6I~
                                	public int alertButtonAction(int Pbuttonid,int Pitemois)//~v1B6I~
                                    {                              //~v1B6I~
                                    	if (Pbuttonid==Alert.BUTTON_POSITIVE)//~v1B6I~
                                        { 
                                        	if (Dump.Y) Dump.println("GuiGtpClient:askContinue replyed Yes");//~v1B6I~//~1Ah0R~
					                        m_gtp.destroyProcess();//~v1B6I~
                                        }                          //~v1B6I~
                                        return Pbuttonid;          //~v1B6I~
                                    }                              //~v1B6I~
                                };                                  //~v1B6I~
	    		int flag=Alert.BUTTON_YNC|Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE|Alert.SHOW_DIALOG;//~v1B6I~
              if (alertAskContinue==null || alertAskContinue.pdlg==null && !alertAskContinue.pdlg.isShowing())//~v1B6I~
              {                                                    //~v1B6I~
	            if (Dump.Y) Dump.println("GuiGtpClient:askContinue issue Alert");//~v1B6I~//~1Ah0R~
               alertAskContinue=                                   //~v1B6I~
        		Alert.simpleAlertDialog(cb/*callback*/,R.string.DialogTitle_gtpconnection,optionalMessage,flag);//notify only//~v1B6I~
              }                                                    //~v1B6I~
              else                                                 //~1Ah0I~
	            if (Dump.Y) Dump.println("GuiGtpClient:AskContinue duplicated Alert");//~1Ah0I~
            	return true;                                       //~v1B6I~
            }                                                      //~v1B6I~
//          String destructiveOption;                              //~v1B6R~
            if (m_command == null)                                 //~v1B6R~
            {                                                      //~v1B6R~
//              optionalMessage = i18n("MSG_PROGRAM_NOT_RESPONDING_2");//~v1B6R~
                optionalMessage = Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING_2);//~v1B6I~
//              destructiveOption ="";// Rstr(R.string.GtpLB_FORCE_QUIT_PROGRAM);//~v1B6R~
            }                                                      //~v1B6R~
            else                                                   //~v1B6R~
            {                                                      //~v1B6R~
                optionalMessage =                                  //~v1B6R~
                    MessageFormat.format(Rstr(R.string.GtpFmt_MSG_PROGRAM_NOT_RESPONDING_3),m_command);//~v1B6R~
//              destructiveOption ="";// Rstr(R.string.GtpLB_TERMINATE_PROGRAM);//~v1B6R~
            }                                                      //~v1B6R~
            //rc:false=destroy                                     //~v1B6I~
//          return ! m_messageDialogs.showWarningQuestion(null, m_owner,//~v1B6R~
//                                                        mainMessage,//~v1B6R~
//                                                        optionalMessage,//~v1B6R~
//                                                        destructiveOption,//~v1B6R~
//                                                        Rstr(R.string.GtpLB_WAIT),//~v1B6R~
//                                                        true);   //~v1B6R~
	    	int flag=Alert.BUTTON_CLOSE|Alert.SHOW_DIALOG;//~v1B6R~
        	Alert.simpleAlertDialog(null/*callback*/,R.string.DialogTitle_gtpconnection,optionalMessage,flag);//notify only//~v1B6R~
            return true;                                           //~v1B6I~
        }

        private final String m_command;
    };

    /** The timeout for commands that are expected to be fast.
        GoGui 0.9.4 used 8 sec, but this was not enough on some machines
        when starting up engines like Aya in the Wine emulator. */
//  private static final int TIMEOUT = 15000;                      //~v1B6R~//~1Ah0R~
    public  static final int TIMEOUT = 15000;                      //~1Ah0I~

    private boolean m_commandInProgress;

//  private final GtpClient m_gtp;                                 //~v1B6R~
    public  final GtpClient m_gtp;                                 //~v1B6I~

    private GtpError m_exception;

//  private final GtpSynchronizer m_gtpSynchronizer;               //~v1B6R~
    public  final GtpSynchronizer m_gtpSynchronizer;               //~v1B6I~

//    private final Component m_owner;                             //~v1B6R~
    private final GoGui m_owner;                                   //~v1B6I~

//  private final MessageDialogs m_messageDialogs;                 //~v1B6R~

    private final Object m_mutex = new Object();

    private Runnable m_callback;

    private String m_command;

    private String m_response;
    public void waitFirstPrompt(String Pcmd)                       //~1Ah0I~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GuiGtpClient:waitFirstPrompt cmd="+Pcmd);//~1Ah0I~
        TimeoutCallback timeoutCallback = new TimeoutCallback(Pcmd);//~1Ah0I~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
			m_gtp.waitFirstPrompt(Pcmd,GuiGtpClient.TIMEOUT,timeoutCallback);//~1Ah0R~
        }                                                          //~1Ah0I~
        catch (GtpError e)                                         //~1Ah0I~
        {                                                          //~1Ah0I~
        	Dump.println(e,"GuiGtpClient:waitFirstPrompt");        //~1Ah0I~
            m_exception = e;                                       //~1Ah0I~
        }                                                          //~1Ah0I~
    }                                                              //~1Ah0I~
    //************************************************************ //+1Ah0I~
    //* for abstruct func                                          //+1Ah0I~
    //************************************************************ //+1Ah0I~
    public String send(String command,boolean Psendplay) throws GtpError//+1Ah0I~
    {                                                              //+1Ah0I~
    	if (Dump.Y) Dump.println("GuiGtpClient:send for sendplay cmd="+command);//+1Ah0I~
        return null;                                               //+1Ah0I~
    }                                                              //+1Ah0I~
}
