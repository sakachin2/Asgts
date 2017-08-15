//*CID://+1Ai2R~: update#= 186;                                    //+1Ai2R~
//****************************************************************************************//+1Ai2R~
//1Ai2 2016/12/29 Android 5 isssue WARNING "linker unused DT entry", ignore it//+1Ai2I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0R~
//1Afm 2016/09/26 fuego;not genmove but reg_genmove is required for timelimit per move.//~1AfmI~
//1Afg 2016/09/24 issue waiting msg while go soft initializing     //~1AfgI~
//v1C3 2014/08/24 display errmsg when received "?" msg             //~v1C3I~
//v1C1 2014/08/21 install fuego as GTP client                      //~v1C1I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)
//****************************************************************************************//+1Ai2R~
// GoGui.java

//package net.sf.gogui.gogui;
package com.Asgts.gtp;                                             //~1AfmR~

import java.io.BufferedReader;                                     //~v1B6R~

import jagoclient.Dump;

import java.io.File;                                               //~v1B6R~
import java.io.IOException;                                        //~v1B6R~
import java.io.Reader;                                             //~v1B6R~
import java.io.StringReader;                                       //~v1B6R~
import java.util.ArrayList;

import static java.text.MessageFormat.format;                      //~v1B6R~

import com.Asgts.AG;                                               //~1AfmR~
import com.Asgts.Alert;                                       //~1AfmR~
import com.Asgts.UiThread;                                    //~1AfmR~
import com.Asgts.R;                                                //~1AfmR~
import com.Asgts.URunnable;                                        //~1AfmR~
import com.Asgts.URunnableData;                                    //~1AfmR~


public class GoGui                                                 //~v1B6I~
{                                                                  //~v1B6I~
                                                                   //~v1B6I~
//  public static final String CMD_BLACK="Black ";                 //~1Ah0R~
//  public static final String CMD_WHITE="White ";                 //~1Ah0R~
	public  static final String PROMPT_RESIGNED="Resigned";        //~1Ah0R~
//  private static final String PROMPT_RESIGNED2="resign";         //~1Ah0R~
	public  static final String PROMPT_MATED="Mated";              //~1Ah0R~
	public  static final int    PROMPT_MOVEID_POS=7;               //~1Ah0R~
	public static final String ERR_COMPUTER_TIMEOUT="time() failed.";//~1Ah0I~
	public  static final String END_OF_GREETING="rand seed ";      //~1Ah0R~
	private static final String INITIAL_CMD="version";    //to "get invalid command line" as bonanza initialization end after prompt("Black 1>")//~1Ah0R~
    public  static final String WARNING_INVALIDCMD="WARNING: invalid command line";//to stderr//~1Ah0R~
	public static final String CMD_COMPUTER_MOVE="move";            //~1Ah0R~
	public static final int CMDTYPE_MOVE=1;                        //~1Ah0R~
	public static final int CMDTYPE_RESIGN=2;                      //~1Ah0R~
	public static final int CMDTYPE_QUIT=3;                        //~1Ah0R~
	private static final String WARNING_BUSY="I\'m busy in thinking now";//~1Ah0I~
    private static final int RID_WARNING_BUSY=R.string.WarningComputerThinking;//~1Ah0I~
    private static final String MSG_UNUSED_DT="unused DT entry";   //+1Ai2I~
//	private boolean reg_genmove;//for fuego                        //~1AfmI~
    private GTPConnector gtpconnector;                             //~v1B6I~
    private int boardSize;                                         //~v1B6I~
    public boolean swInitialized;	//responsed gotok              //~v1B6I~
    private int ctrSendPlay;                                       //~1Ah0R~
    private File m_workingDir;                                          //~1Ah0I~
    private boolean swGameStarted/*,swAccepted*/;                  //~1Ah0R~
//    private boolean swWhiteFirst;                                //~1Ah0R~
    private ArrayList<String> subcmdList;                          //~1Ah0I~
    private GtpClient gtpClient;                                    //~1Ah0I~
    public int cmdType=CMDTYPE_MOVE;                               //~1Ah0I~
    public String computerMove;                                    //~1Ah0I~
    public String pgmName;                                         //~1Ah0I~

    public GoGui(Object owner,                                     //~v1B6R~
//               String program,            int move, String time, //~1Ah0R~
                 String program,File Pwkdir,int move, String time, //~1Ah0I~
                 boolean verbose, boolean initComputerColor,       //~v1B6I~
                 boolean computerBlack, boolean computerWhite,     //~v1B6R~
                                   String gtpFile, String gtpCommand,//~v1B6I~
//               File analyzeCommandsFile,boolean Preg_genmove)    //~1AfmI~//~1Ah0R~
                 File analyzeCommandsFile,ArrayList<String> PsubcmdList)//~1Ah0I~
    {                                                              //~v1B6R~
//      reg_genmove=Preg_genmove;                                  //~1AfmI~//~1Ah0R~
        subcmdList=PsubcmdList;                                    //~1Ah0I~
        m_workingDir=Pwkdir;                                       //~1Ah0I~
      try                                                          //~v1B6I~
      {                                                            //~v1B6I~
        gtpconnector=(GTPConnector)owner;                          //~v1B6I~
        boardSize = gtpconnector.getBoardSize();                   //~v1B6R~
          m_gtpCommand = gtpCommand;                               //~v1B6R~
        m_verbose = verbose;                                       //~v1B6R~

        m_programCommand = program;                                //~v1B6R~
        if (m_programCommand != null && m_programCommand.trim().equals(""))//~v1B6R~
            m_programCommand = null;                               //~v1B6R~
        if (time != null)                                          //~v1B6R~
            m_timeSettings = TimeSettings.parse(time);             //~v1B6R~
		Runnable runinit=new Runnable() {                          //~v1B6I~
                public void run() {                                //~v1B6I~
                	try                                            //~v1B6I~
                    {                                              //~v1B6I~
                      boolean rc=                                  //~1Ah0I~
                    	initialize();                              //~v1B6R~
                        swInitialized=true;                        //~v1B6I~
                        if (Dump.Y) Dump.println("GoGui:run after initialize  rc="+rc);//~1Ah0I~
	            		waitingMsg(1);   //dismiss waiting Alert   //~1Ah0I~
                      if (rc)                                      //~1Ah0I~
						gtpconnector.gotOk();                      //~v1B6R~
                    }                                              //~v1B6I~
                    catch(Exception e)                             //~v1B6I~
                    {                                              //~v1B6I~
						Dump.println(e,"GoGui initialize");        //~v1B6I~
                    }                                              //~v1B6I~
                } };                                              //~v1B6I~
            new Thread(runinit).start();//exe on subthread because boardsize cmd is lengthy if book option is on//~1Ah0R~
      }                                                            //~v1B6I~
      catch(GtpError e)                                            //~v1B6I~
      {                                                            //~v1B6I~
		Dump.println(e,"GoGui:constructor GtpError");              //~v1B6I~
        showError(e);                                              //~v1B6I~
      }                                                            //~v1B6I~
      catch(ErrorMessage e)                                        //~v1B6I~
      {                                                            //~v1B6I~
		Dump.println(e,"GoGui:constructor ErrorMessage");          //~v1B6I~
        showError(Rstr(R.string.GtpErr_ErrorMessage), e);          //~v1B6I~
      }                                                            //~v1B6I~
    }                                                              //~v1B6R~


    public void actionQuit()                                       //~v1B6R~
    {                                                              //~v1B6R~
        close();                                                   //~v1B6R~
    }                                                              //~v1B6R~

    public String getProgramName()                                 //~v1B6R~
    {                                                              //~v1B6R~
        String name = null;                                        //~v1B6R~
        if (m_gtp != null)                                         //~v1B6R~
            name = m_gtp.getName();                                //~v1B6R~
        if (name == null)                                          //~1Ah0I~
        {                                                          //~1Ah0I~
	        name=m_programCommand;                                 //~1Ah0I~
        	int pos=name.lastIndexOf('/');                         //~1Ah0I~
        	if (pos>0)                                             //~1Ah0I~
        		name=name.substring(pos+1);                        //~1Ah0I~
        	pos=name.indexOf(' ');                              //~1Ah0I~
        	if (pos>0)                                             //~1Ah0I~
        		name=name.substring(0,pos);                        //~1Ah0I~
        }                                                          //~1Ah0I~
        if (name == null)                                          //~v1B6R~
            name = Rstr(R.string.GtpFmt_MSG_UNKNOWN_PROGRAM_NAME);    //~v1B6I~
        if (Dump.Y) Dump.println("GoGui.getProgramName="+name);     //~1Ah0I~
        return name;                                               //~v1B6R~
    }                                                              //~v1B6R~


    public boolean isCommandInProgress()                           //~v1B6R~
    {                                                              //~v1B6R~
        return (m_gtp != null && m_gtp.isCommandInProgress());     //~v1B6R~
    }                                                              //~v1B6R~

    private class ShowInvalidResponse                              //~v1B6I~
    {                                                              //~v1B6I~
        public ShowInvalidResponse(boolean error/*false faital*/,String line)//~v1B6I~
        {                                                          //~v1B6I~
        	if (Dump.Y) Dump.println("GoGui:ShowInvalidResponse error="+error+",line="+line);//~1Ah0I~
        	showResponse(error,line);                              //~v1B6I~
        }                                                          //~v1B6I~
                                                                   //~v1B6I~
        public void showResponse(boolean error,String line)        //~v1B6I~
        {                                                          //~v1B6I~
        	if (Dump.Y) Dump.println("GoGui:showResponse error="+error+",line="+line);//~1Ah0I~
            String mainMessage;                                    //~v1B6I~
            String name = getProgramName();                        //~v1B6I~
		  if (line.indexOf(WARNING_BUSY)>=0)                         //~1Ah0I~
    	    mainMessage=AG.resource.getString(RID_WARNING_BUSY);   //~1Ah0I~
          else                                                     //~1Ah0I~
          {                                                        //~1Ah0I~
            if (error)                                             //~v1B6I~
            	mainMessage = format(Rstr(R.string.GtpFmt_MSG_ERROR_RESPONSE), name);//~v1B6I~
            else                                                   //~v1B6I~
            	mainMessage = format(Rstr(R.string.GtpFmt_MSG_INVALID_RESPONSE), name);//~v1B6I~
            mainMessage+="\n"+line;                                //~v1B6I~
          }                                                        //~1Ah0I~
	    	int flag=Alert.BUTTON_CLOSE|Alert.SHOW_DIALOG;//~v1B6I~
        	Alert.simpleAlertDialog(null/*callback*/,R.string.DialogTitle_gtpconnection,mainMessage,flag);//~v1B6I~
        }                                                          //~v1B6I~
    }                                                              //~v1B6I~


    private boolean m_autoNumber;                                  //~v1B6R~

//    private boolean m_computerBlack;                               //~v1B6R~//~1Ah0R~

//   private boolean m_computerWhite;                               //~v1B6R~//~1Ah0R~


    private boolean m_resigned;                                    //~v1B6R~

      private boolean m_verbose;                                   //~v1B6I~

    public  GuiGtpClient m_gtp;                                    //~v1B6I~


    private final MessageDialogs m_messageDialogs = new MessageDialogs();//~v1B6R~

    private String m_gtpCommand;                                   //~v1B6I~


    private String m_programCommand;                               //~v1B6R~

    public  String m_version = "";                                 //~v1B6I~//~1Ah0R~

    public  TimeSettings m_timeSettings;                           //~v1B6I~


    /** Attach program.                                            //~v1B6R~
        @param programCommand Command line for running program.    //~v1B6R~
        @param program Program information (may be null)           //~v1B6R~
        @param register Create an entry for this program in the Program menu.//~v1B6R~
        @return true if program was successfully attached. */      //~v1B6R~
    private boolean attachProgram(String programCommand)           //~v1B6I~
    {                                                              //~v1B6R~
        programCommand = programCommand.trim();                    //~v1B6R~
        if (programCommand.equals(""))                             //~v1B6R~
            return false;                                          //~v1B6R~
        m_programCommand = programCommand;                         //~v1B6R~
        GtpClient.InvalidResponseCallback invalidResponseCallback =//~v1B6R~
            new GtpClient.InvalidResponseCallback()                //~v1B6R~
            {                                                      //~v1B6R~
                public void show(String line)                      //~v1B6R~
                {                                                  //~v1B6R~
                    if (Dump.Y) Dump.println("GoGui:attachProgram-1 GtpClent.InvalidResponseCallback:"+line);//~v1B6I~//~1Ah0M~
//                  new ShowInvalidResponse(false,line);           //~v1B6I~//~1Ah0R~
                }                                                  //~v1B6R~
            };                                                     //~v1B6R~
        GtpClient.IOCallback ioCallback = new GtpClient.IOCallback()//~v1B6R~
            {                                                      //~v1B6R~
                public void receivedInvalidResponse(String s)      //~v1B6R~
                {                                                  //~v1B6R~
                    if (Dump.Y) Dump.println("GoGui:attachProgram-2 GtpClient.IOCallback.receivedInvalidResponse:"+s);//~v1B6I~//~1Ah0R~
                    if (skipMonologue(s))                          //~1Ah0R~
                        return;                                    //~1Ah0R~
                    new ShowInvalidResponse(true,s);               //~v1B6I~
                }                                                  //~v1B6R~

                public void receivedResponse(boolean error, String s)//~v1B6R~
                {                                                  //~v1B6R~
                  try                                              //~1Ah0I~
                  {                                                //~1Ah0I~
                    if (Dump.Y) Dump.println("GoGui:attachProgram-3 GtpClient.IOCallback.receivedResponse err="+error+",msg="+s);//~v1C3R~//~1Ah0R~
            		waitingMsg(1);   //dismiss waiting Alert       //~1AfgI~
                    if (!error)                                    //~1Ah0I~
                    	if (s.startsWith(GtpClient.RESPONSEID))    //~1Ah0I~
    						error=computerMoved(s,false/*not err*/);//~1Ah0R~
                    if (error)                                     //~v1B6I~
    	                new ShowInvalidResponse(error,s);          //~v1B6I~
                  }                                                //~1Ah0I~
                  catch(Exception e)                               //~1Ah0I~
                  {                                                //~1Ah0I~
                  	Dump.println("GoGui:receivedResponse string="+s);//~1Ah0I~
                  }                                                //~1Ah0I~
                }                                                  //~v1B6R~

                public void receivedStdErr(String s)               //~v1B6R~
                {                                                  //~v1B6R~
                    if (Dump.Y) Dump.println("GoGui:attachProgram-4 GtpClient.IOCallback.receivedStdErr response="+s);//~v1C3I~//~1Ah0R~
                    m_lineReader.add(s);                           //~v1B6R~
                    while (m_lineReader.hasLines())                //~v1B6R~
                    {                                              //~v1B6R~
                        String line = m_lineReader.getLine();      //~v1B6R~
//                        if (expectedResponse(line))              //~1Ah0R~
//                            break;                               //~1Ah0R~
                        boolean isWarning =                        //~v1B6R~
                            line.startsWith("warning:")            //~v1B6R~
                            || line.startsWith("Warning:")         //~v1B6R~
                            || line.startsWith("WARNING:");        //~v1B6R~
                    	if (Dump.Y) Dump.println("GoGui:attachProgram-5 GtpClient.IOCallback.receivedStdErr: response line="+line);//~v1B6I~//~1Ah0R~
                        if (isWarning)                             //~v1B6I~
                        {                                          //+1Ai2I~
                          if (line.indexOf(MSG_UNUSED_DT)<0)       //+1Ai2I~
	    	                new ShowInvalidResponse(isWarning,line);//~v1B6R~
                        }                                          //+1Ai2I~
                        else                                       //~1Ah0R~
                        if (line.startsWith("?")                   //~v1C3R~
                        ||  line.indexOf("Error")>=0                //~v1C3I~
                        ||  line.indexOf("ERROR")>=0               //~1Ah0I~
                        ||  line.indexOf("WARNING")>=0             //~1Ah0I~
                        ||  line.indexOf("error")>=0                //~v1C3I~
                        ||  line.indexOf("Invalid")>=0              //~v1C3I~
                        ||  line.indexOf("innvalid")>=0             //~v1C3I~
                        ||  line.indexOf("failed")>=0              //~v1C3I~
                        )                                          //~v1C3I~
                        {                                          //~1Ah0I~
	    	                showErrInStderr(line);                 //~v1C3I~
	                        if (line.startsWith("ERROR:"))         //~1Ah0I~
								computerMoved(s.substring(6).trim(),true/*err*/);//~1Ah0I~
                        }                                          //~1Ah0I~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~

                public void sentCommand(String s)                  //~v1B6R~
                {                                                  //~v1B6R~
                	if (Dump.Y) Dump.println("GoGui:sentCommand="+s);//~v1C3I~
                }                                                  //~v1B6R~

                private final LineReader m_lineReader = new LineReader();//~v1B6R~

            };                                                     //~v1B6R~
        GtpSynchronizer.Listener synchronizerCallback =            //~v1B6R~
            new GtpSynchronizer.Listener() {                       //~v1B6R~
                public void moveNumberChanged(int moveNumber) {    //~v1B6R~
                }                                                  //~v1B6R~
            };                                                     //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
//          File workingDirectory = null;                          //~1Ah0R~
            File workingDirectory = m_workingDir;                  //~1Ah0I~
            waitingMsg(0);   //isseu waiting Alert                 //~1AfgI~
            GtpClient gtp =                                        //~v1B6R~
                new GtpClient(m_programCommand, workingDirectory,  //~v1B6R~
                              m_verbose, ioCallback);              //~v1B6R~
            gtpClient=gtp;                                         //~1Ah0I~
            gtp.setInvalidResponseCallback(invalidResponseCallback);//~v1B6R~
            gtp.setAutoNumber(m_autoNumber);                       //~v1B6R~
            m_gtp = new GuiGtpClient(gtp, this, synchronizerCallback,//~v1B6R~
                                     m_messageDialogs);            //~v1B6R~
//          m_gtp.queryName();  //invalid cmd                      //~1Ah0R~
//          m_gtp.queryProtocolVersion();                          //~1Ah0R~
//          try                                                    //~1Ah0R~
//          {                                                      //~1Ah0R~
//              m_version = m_gtp.queryVersion();                  //~1Ah0R~
//              m_gtp.querySupportedCommands();                    //~1Ah0R~
//              m_gtp.queryInterruptSupport();                     //~1Ah0R~
//          }                                                      //~1Ah0R~
//          catch (GtpError e)                                     //~1Ah0R~
//          {                                                      //~1Ah0R~
//          	                                                   //~1Ah0I~
//              Dump.println(e,"attachProgram");                   //~1Ah0R~
//          }                                                      //~1Ah0R~
//              m_gtp.getSupportedCommands();                      //~v1B6R~//~1Ah0R~
//          if (reg_genmove)                                       //~1Ah0R~
//  			if (!m_gtp.isSupported(REG_GENMOVE))               //~1Ah0R~
//          		reg_genmove=false;                             //~1Ah0R~
//         if (!reg_genmove)                                       //~1Ah0R~
//          if (! m_gtp.isGenmoveSupported())                      //~1Ah0R~
//          {                                                      //~1Ah0R~
//              m_computerBlack = false;                           //~1Ah0R~
//              m_computerWhite = false;                           //~1Ah0R~
//          }                                                      //~1Ah0R~
//          initGtp();                                             //~1Ah0R~
            sendInitialCmd();                                      //~1Ah0I~
            boolean rc=initGtp();                                  //~1Ah0I~
            if (!rc)                                               //~1Ah0I~
            {                                                      //~1Ah0I~
		        if (Dump.Y) Dump.println("GoGui:attachProgram initGtp rc:false return false");//~1Ah0I~
            	return false;                                      //~1Ah0I~
            }                                                      //~1Ah0I~
            if (! m_gtpCommand.equals(""))                         //~v1B6R~
                sendGtpString(m_gtpCommand);                       //~v1B6R~
            if (m_gtp.isProgramDead())                             //~1Ah0I~
            {                                                      //~1Ah0I~
		        if (Dump.Y) Dump.println("GoGui:attachProgram isProgramDead=true return false");//~1Ah0I~
            	return false;                                      //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~v1B6R~
        catch (GtpError e)                                         //~v1B6R~
        {                                                          //~v1B6R~
            showError(e);                                          //~v1B6R~
	        if (Dump.Y) Dump.println("GoGui:attachProgram GtpError return false");//~1Ah0I~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6R~
        finally                                                    //~v1B6R~
        {                                                          //~v1B6R~
        }                                                          //~v1B6R~
        if (Dump.Y) Dump.println("GoGui:attachProgram return true");//~1Ah0I~
        return true;                                               //~v1B6R~
    }//attachProgram                                               //~v1B6R~


    private void close()                                           //~v1B6R~
    {                                                              //~v1B6R~
        UiThread.invokeLater(new Runnable() {                 //~v1B6I~
                public void run() {                                //~v1B6R~
                    if (Dump.Y) Dump.println("GoGui:close(run)");  //~v1B6I~
                  try                                              //~v1B6I~
                  {                                                //~v1B6I~
                    if (m_gtp != null)                             //~v1B6R~
                    {                                              //~v1B6R~
                        detachProgram();                           //~v1B6R~
                    }                                              //~v1B6R~
                  }                                                //~v1B6I~
                  catch(Exception e)                               //~v1B6I~
                  {                                                //~v1B6I~
                    Dump.println(e,"GoGui:close(run)");            //~v1B6I~
                  }                                                //~v1B6I~
                }                                                  //~v1B6R~
            });                                                    //~v1B6R~
    }                                                              //~v1B6R~

//    private void computerMoved()                                   //~v1B6R~//~1Ah0R~
//    {                                                              //~v1B6R~//~1Ah0R~
//        if (! endLengthyCommand())                                 //~v1B6R~//~1Ah0R~
//            return;                                                //~v1B6R~//~1Ah0R~
//        if (m_gtp==null)    //after detached                       //~v1B6I~//~1Ah0R~
//            return;                                                //~v1B6I~//~1Ah0R~
////      GoPoint point=null;                                      //~1Ah0R~
//        try                                                        //~v1B6R~//~1Ah0R~
//        {                                                          //~v1B6R~//~1Ah0R~
//            String response = m_gtp.getResponse();                 //~v1B6R~//~1Ah0R~
//            if (response==null)                                    //~v1B6I~//~1Ah0R~
//                throw new GtpResponseFormatError("computerMoved:null response");//~v1B6R~//~1Ah0R~
//            if (response.equalsIgnoreCase("resign"))               //~v1B6R~//~1Ah0R~
//            {                                                      //~v1B6R~//~1Ah0R~
//                m_resigned = true;                                 //~v1B6R~//~1Ah0R~
//            }                                                      //~v1B6R~//~1Ah0R~
//            else                                                   //~v1B6R~//~1Ah0R~
//            {                                                      //~v1B6R~//~1Ah0R~
//                m_resigned = false;                                //~v1B6R~//~1Ah0R~
//            }                                                      //~v1B6R~//~1Ah0R~
////          gtpconnector.gotMoved(point,m_resigned);                //~v1B6R~//~1Ah0R~
//            gtpconnector.gotMoved(response,m_resigned);          //~1Ah0R~
//        }                                                          //~v1B6R~//~1Ah0R~
//        catch (GtpResponseFormatError e)                           //~v1B6R~//~1Ah0R~
//        {                                                          //~v1B6R~//~1Ah0R~
//            showError(e);                                          //~v1B6R~//~1Ah0R~
//        }                                                          //~v1B6R~//~1Ah0R~
//    }                                                              //~v1B6R~//~1Ah0R~



    private void detachProgram()                                   //~v1B6R~
    {                                                              //~v1B6R~
        if (isCommandInProgress())                                 //~v1B6R~
        {                                                          //~v1B6R~
            m_gtp.destroyGtp();                                    //~v1B6R~
            m_gtp.close();                                         //~v1B6R~
        }                                                          //~v1B6R~
        else                                                       //~v1B6R~
        {                                                          //~v1B6R~
            if (m_gtp != null && ! m_gtp.isProgramDead())          //~v1B6R~
            {                                                      //~v1B6R~
                // Some programs do not handle closing the GTP stream//~v1B6R~
                // correctly, so we send a quit before             //~v1B6R~
                try                                                //~v1B6R~
                {                                                  //~v1B6R~
                    if (m_gtp.isSupported("quit"))                 //~v1B6R~
                        m_gtp.send("quit");                        //~v1B6R~
                }                                                  //~v1B6R~
                catch (GtpError e)                                 //~v1B6R~
                {                                                  //~v1B6R~
                    Dump.println(e,"GoGui:detachProgram");         //~v1B6I~
                }                                                  //~v1B6R~
                m_gtp.close();                                     //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        m_gtp = null;                                              //~v1B6R~
    }                                                              //~v1B6R~

    private boolean endLengthyCommand()                            //~v1B6R~
    {                                                              //~v1B6R~
        return endLengthyCommand(true, true);                      //~v1B6R~
    }                                                              //~v1B6R~


    private boolean endLengthyCommand(boolean isCritical,          //~v1B6R~
                                      boolean showError)           //~v1B6R~
    {                                                              //~v1B6R~
        // Program could have been killed in actionDetachProgram() //~v1B6R~
        if (m_gtp == null)                                         //~v1B6R~
            return false;                                          //~v1B6R~
        GtpError error = m_gtp.getException();                     //~v1B6R~
        if (error != null && showError)                            //~v1B6R~
        {                                                          //~v1B6R~
            showError(error, isCritical);                          //~v1B6R~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6R~
        return true;                                               //~v1B6R~
    }                                                              //~v1B6R~


//    public void generateMove(boolean isSingleMove,GoColor Phumancolor)//~v1B6R~//~1Ah0R~
//    {                                                              //~v1B6I~//~1Ah0R~
//        GoColor toMove = Phumancolor.otherColor();;                //~v1B6I~//~1Ah0R~
//        String command;                                            //~v1B6I~//~1Ah0R~
//            command = m_gtp.getCommandGenmove(toMove,reg_genmove?REG_GENMOVE:null);//~1AfmR~//~1Ah0R~
//        Runnable callback = new Runnable()                         //~v1B6I~//~1Ah0R~
//            {                                                      //~v1B6I~//~1Ah0R~
//                public void run()                                  //~v1B6I~//~1Ah0R~
//                {                                                  //~v1B6I~//~1Ah0R~
//                    try                                            //~v1B6I~//~1Ah0R~
//                    {                                              //~v1B6I~//~1Ah0R~
//                        computerMoved();                           //~v1B6R~//~1Ah0R~
//                    }                                              //~v1B6I~//~1Ah0R~
//                    catch(Exception e)                             //~v1B6I~//~1Ah0R~
//                    {                                              //~v1B6I~//~1Ah0R~
//                        Dump.println(e,"computerMoved");           //~v1B6I~//~1Ah0R~
//                    }                                              //~v1B6I~//~1Ah0R~
//                }                                                  //~v1B6I~//~1Ah0R~
//            };                                                     //~v1B6I~//~1Ah0R~
//        runLengthyCommand(command, callback);                      //~v1B6I~//~1Ah0R~
//    }                                                              //~v1B6I~//~1Ah0R~


    private boolean initGtp()                                      //~v1B6R~
    {                                                              //~v1B6R~
        if (m_gtp != null)                                         //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
//              m_gtp.initSynchronize(gtpconnector.getBoardSize(),gtpconnector.getKomi(),m_timeSettings);//~1Ah0R~
                m_gtp.initSynchronize(boardSize,gtpconnector.getKomi(),m_timeSettings);//~1Ah0I~
            }                                                      //~v1B6R~
            catch (GtpError error)                                 //~v1B6R~
            {                                                      //~v1B6R~
                showError(error);                                  //~v1B6R~
                return false;                                      //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        return ! isOutOfSync();                                    //~v1B6R~
    }                                                              //~v1B6R~

//  private void initialize()                                      //~1Ah0R~
    private boolean initialize()                                   //~1Ah0I~
    {                                                              //~v1B6R~
//      if (m_programCommand != null)                              //~1Ah0R~
//      {                                                          //~1Ah0R~
          boolean rc=                                              //~1Ah0I~
            attachProgram(m_programCommand);                       //~v1B6I~
//      }                                                          //~1Ah0R~
		return rc;                                                 //~1Ah0I~
    }//initialize                                                  //~v1B6R~


//    private boolean isComputerBoth()                               //~v1B6R~
//    {                                                              //~v1B6R~
//        return (m_computerBlack && m_computerWhite);               //~v1B6R~
//    }                                                              //~v1B6R~


    private boolean isOutOfSync()                                  //~v1B6R~
    {                                                              //~v1B6R~
        return (m_gtp != null && m_gtp.isOutOfSync());             //~v1B6R~
    }                                                              //~v1B6R~



    private void runLengthyCommand(String cmd, Runnable callback)  //~v1B6R~
    {                                                              //~v1B6R~
        assert m_gtp != null;                                      //~v1B6R~
        m_gtp.send(cmd, callback);                                 //~v1B6R~
    }                                                              //~v1B6R~


    private void sendGtp(Reader reader)                            //~v1B6R~
    {                                                              //~v1B6R~
        if (m_gtp == null)                                         //~v1B6R~
            return;                                                //~v1B6R~
        java.io.BufferedReader in;                                 //~v1B6R~
        in = new BufferedReader(reader);                           //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            while (true)                                           //~v1B6R~
            {                                                      //~v1B6R~
                try                                                //~v1B6R~
                {                                                  //~v1B6R~
                    String line = in.readLine();                   //~v1B6R~
                    if (line == null)                              //~v1B6R~
                        break;                                     //~v1B6R~
                    if (! GtpUtil.isCommand(line))                 //~v1B6R~
                        continue;                                  //~v1B6R~
                    if (GtpUtil.isStateChangingCommand(line))      //~v1B6R~
                    {                                              //~v1B6R~
                        showError(Rstr(R.string.GtpFmt_MSG_BOARD_CHANGING_COMMAND), "");//~v1B6I~
                        break;                                     //~v1B6R~
                    }                                              //~v1B6R~
                    try                                            //~v1B6R~
                    {                                              //~v1B6R~
                        m_gtp.send(line);                          //~v1B6R~
                    }                                              //~v1B6R~
                    catch (GtpError e)                             //~v1B6R~
                    {                                              //~v1B6R~
                        showError(e);                              //~v1B6R~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~
                catch (IOException e)                              //~v1B6R~
                {                                                  //~v1B6R~
                    showError(Rstr(R.string.GtpFmt_MSG_COULD_NOT_READ_FILE), e);//~v1B6I~
                    break;                                         //~v1B6R~
                }                                                  //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        finally                                                    //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                in.close();                                        //~v1B6R~
            }                                                      //~v1B6R~
            catch (IOException e)                                  //~v1B6R~
            {                                                      //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
    }//sendGtp                                                     //~v1B6R~

    public  void sendGtpString(String commands)                    //~v1C1I~
    {                                                              //~v1B6R~
        commands = commands.replaceAll("\\\\n", "\n");             //~v1B6R~
        sendGtp(new StringReader(commands));                       //~v1B6R~
    }                                                              //~v1B6R~


    private void showError(String message, Exception e)            //~v1B6R~
    {                                                              //~v1B6R~
        showError(message, e, true);                               //~v1B6R~
    }                                                              //~v1B6R~

    private void showError(String message, Exception e, boolean isCritical)//~v1B6R~
    {                                                              //~v1B6R~
        m_messageDialogs.showError(this, message, e, isCritical);  //~v1B6R~
    }                                                              //~v1B6R~

    private void showError(GtpError error)                         //~v1B6R~
    {                                                              //~v1B6R~
        showError(error, true);                                    //~v1B6R~
    }                                                              //~v1B6R~

    private void showError(GtpResponseFormatError e)               //~v1B6R~
    {                                                              //~v1B6R~
        String name = getProgramName();                            //~v1B6R~
        String mainMessage = format(Rstr(R.string.GtpFmt_MSG_INVALID_RESPONSE), name);//~v1B6I~
        String optionalMessage =                                   //~v1B6R~
              format(Rstr(R.string.GtpFmt_MSG_INVALID_RESPONSE_2), name, e.getMessage());//~v1B6I~
        showError(mainMessage, optionalMessage, true);             //~v1B6R~
    }                                                              //~v1B6R~

    private void showError(GtpError e, boolean isCritical)         //~v1B6R~
    {                                                              //~v1B6R~
        String name = getProgramName();                            //~v1B6R~
        String mainMessage;                                        //~v1B6R~
        String optionalMessage;                                    //~v1B6R~
        if (Dump.Y) Dump.println("GoGui:showError isdead="+(m_gtp==null?"m_gtp=null":(m_gtp.isProgramDead()?"dead":"alive")));//~1Ah0I~
        if (m_gtp != null && m_gtp.isProgramDead())                //~v1B6R~
        {                                                          //~v1B6R~
            if (m_gtp.wasKilled())                                 //~v1B6R~
                mainMessage = format(Rstr(R.string.GtpFmt_MSG_PROGRAM_TERMINATED), name);//~v1B6I~
            else                                                   //~v1B6R~
                mainMessage = Rstr(R.string.GtpFmt_MSG_PROGRAM_TERMINATED_UNEXPECTEDLY);//~v1B6R~
              optionalMessage =errInStdErr;                        //~v1C3I~
            waitingMsg(1);   //dismiss waiting Alert               //~1Ah0I~
        }                                                          //~v1B6R~
        else if (e instanceof GtpClient.ExecFailed)                //~v1B6R~
        {                                                          //~v1B6R~
            mainMessage = Rstr(R.string.GtpFmt_MSG_COULD_NOT_EXECUTE);//~v1B6I~
            if (StringUtil.isEmpty(e.getMessage()))                //~v1B6R~
                optionalMessage = Rstr(R.string.GtpFmt_MSG_COULD_NOT_EXECUTE_2);//~v1B6I~
            else                                                   //~v1B6R~
                optionalMessage =                                  //~v1B6R~
                    format(Rstr(R.string.GtpFmt_MSG_COULD_NOT_EXECUTE_3), e.getMessage());//~v1B6I~
        }                                                          //~v1B6R~
        else                                                       //~v1B6R~
        {                                                          //~v1B6R~
            mainMessage = Rstr(R.string.GtpFmt_MSG_COMMAND_FAILED);//~v1B6I~
            if (e.getMessage().trim().equals(""))                  //~v1B6R~
                optionalMessage =                                  //~v1B6R~
                    format(Rstr(R.string.GtpFmt_MSG_COMMAND_FAILED_2), e.getCommand());//~v1B6I~
            else                                                   //~v1B6R~
                optionalMessage =                                  //~v1B6R~
                    format(Rstr(R.string.GtpFmt_MSG_COMMAND_FAILED_3), e.getCommand(),//~v1B6I~
                           e.getMessage());                        //~v1B6R~
        }                                                          //~v1B6R~
        showError(mainMessage, optionalMessage, isCritical);       //~v1B6R~
    }                                                              //~v1B6R~

    private void showError(String mainMessage, String optionalMessage)//~v1B6R~
    {                                                              //~v1B6R~
        showError(mainMessage, optionalMessage, true);             //~v1B6R~
    }                                                              //~v1B6R~

    private void showError(String mainMessage, String optionalMessage,//~v1B6R~
                           boolean isCritical)                     //~v1B6R~
    {                                                              //~v1B6R~
    	if (Dump.Y) Dump.println("GoGui:showError before msgDialog iscritical="+isCritical+",mainmsg="+mainMessage+",optionMsg="+optionalMessage);//~1Ah0I~
        m_messageDialogs.showError(this, mainMessage, optionalMessage,//~v1B6R~
                                   isCritical);                    //~v1B6R~
    }                                                              //~v1B6R~

	//*************************************************************************//~v1C3I~
    public static String Rstr(int Pstrid)                          //~v1B6R~
    {                                                              //~v1B6I~
        return AG.resource.getString(Pstrid);                      //~v1B6I~
    }                                                              //~v1B6I~
	//*************************************************************************//~v1C3I~
    private String errInStdErr="";                                 //~v1C3I~
    private void showErrInStderr(String Pline)                     //~v1C3I~
    {                                                              //~v1C3I~
    	if (Dump.Y) Dump.println("GoGui:showErrInStderr before Alert line="+Pline);//~1Ah0I~
//  	AView.showToastLong(R.string.GtpErr_ErrInStdErr,"\n"+Pline);//~1Ah0R~
		Alert.simpleAlertDialog(null,R.string.GtpErr_ErrInStdErr,Pline,Alert.BUTTON_CLOSE);//~1Ah0R~
        errInStdErr+=Pline;                                        //~v1C3I~
    }                                                              //~v1C3I~
	//*************************************************************************//~1AfgI~
    private URunnableData progressDialog;                          //~1AfgI~
//  private void waitingMsg(int Paction)                           //~1Ah0R~
    public  void waitingMsg(int Paction)                           //~1Ah0I~
    {                                                              //~1AfgI~
    	if (Dump.Y) Dump.println("GoGui:waitingMsg action="+Paction);//~1AfgI~
    	if (Paction==0)                                            //~1AfgI~
        {                                                          //~1AfgI~
       	 	progressDialog=progressDialogShow(R.string.ProgressDialogTitleGoGui,//~1AfgR~
            		    				AG.resource.getString(R.string.WaitingComputerPlayerInitiated),//~1AfgI~
												true/*indeterminate*/, true/*cancelable*/);//~1AfgI~
        }                                                          //~1AfgI~
        else                                                       //~1AfgI~
        {                                                          //~1Ah0I~
    	  	if (progressDialog!=null)                 //~1AfgI~    //~1Ah0R~
            {                                                      //~1Ah0I~
    			dismissProgressDialog(progressDialog);             //~1Ah0I~
	    	  	progressDialog=null;                              //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
    }                                                              //~1AfgI~
    private URunnableData progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1AfgI~
    {                                                              //~1AfgI~
        if (Dump.Y) Dump.println("ProgDialog:simpleProgressDialogShow");//~1AfgI~
        return URunnable.simpleProgressDialogShow(Ptitleid,Pmsg,Pindeterminate,Pcancelable);//~1AfgI~
    }                                                              //~1AfgI~
    private static void dismissProgressDialog(URunnableData Pdlg)  //~1AfgI~
    {                                                              //~1AfgI~
        if (Dump.Y) Dump.println("GoGui:dismissProgressDialog");   //~1AfgI~
        URunnable.dismissDialog(Pdlg);                             //~1AfgI~
    }                                                              //~1AfgI~
    //********************************************                 //~1Ah0I~
    //*rc:true:ignore response                                     //~1Ah0I~
    //********************************************                 //~1Ah0I~
    private boolean skipMonologue(String Presponse)                //~1Ah0R~
    {                                                              //~1Ah0I~
		if (Dump.Y) Dump.println("GoGui:skipMonologue swGameStarted="+swGameStarted+",response="+Presponse);//~1Ah0R~
        if (!swGameStarted)                                          //~1Ah0I~
            if (Presponse.startsWith("Bonanza "))                  //~1Ah0R~
            {                                                      //~1Ah0R~
                m_version=Presponse.substring(8);                  //~1Ah0R~
                if (Dump.Y) Dump.println("GoGui:skipMonologue version="+m_version);//~1Ah0R~
                gtpconnector.setVersion(m_version);                //~1Ah0I~
            }                                                      //~1Ah0R~
//        if (swGameStarted)                                       //~1Ah0R~
//            if (chkComputerMoved(Presponse))    //               //~1Ah0R~
//                return false;    //show err                      //~1Ah0R~
//        else                                                     //~1Ah0R~
//        if (Presponse.startsWith(CMD_BLACK))                     //~1Ah0R~
//        {                                                        //~1Ah0R~
//            swGameStarted=true;                                  //~1Ah0R~
//            swWhiteFirst=false;                                  //~1Ah0R~
//            if (Dump.Y) Dump.println("GoGui:skipMonologue BLACK> WhiteFirst="+swWhiteFirst);//~1Ah0R~
//        }                                                        //~1Ah0R~
//        else                                                     //~1Ah0R~
//        if (Presponse.startsWith(CMD_WHITE))                     //~1Ah0R~
//        {                                                        //~1Ah0R~
//            swGameStarted=true;                                  //~1Ah0R~
//            swWhiteFirst=true;                                   //~1Ah0R~
//            if (Dump.Y) Dump.println("GoGui:skipMonologue WHITE> WhiteFirst="+swWhiteFirst);//~1Ah0R~
//        }                                                        //~1Ah0R~
    	return true;  //ignore msg                                 //~1Ah0R~
    }                                                              //~1Ah0I~
//    //********************************************               //~1Ah0R~
//    //*rc:true:ignore 1st invalid cmd                            //~1Ah0R~
//    //********************************************               //~1Ah0R~
//    private boolean expectedResponse(String Presponse)           //~1Ah0R~
//    {                                                            //~1Ah0R~
//        boolean rc=false;                                        //~1Ah0R~
//        if (Dump.Y) Dump.println("GoGui:expectedResponse response="+Presponse);//~1Ah0R~
//        if (!swGameStarted)                                      //~1Ah0R~
//        {                                                        //~1Ah0R~
//            if (Presponse.startsWith(WARNING_INVALIDCMD))        //~1Ah0R~
//                if (!swAccepted)                                 //~1Ah0R~
//                {                                                //~1Ah0R~
//                    swAccepted=true;                             //~1Ah0R~
//                    rc=true;                                     //~1Ah0R~
//                }                                                //~1Ah0R~
//        }                                                        //~1Ah0R~
//        if (Dump.Y) Dump.println("GoGui:expectedResponse rc="+rc+",swAccepted="+swAccepted+",response="+Presponse);//~1Ah0R~
//        return rc;                                               //~1Ah0R~
//    }                                                            //~1Ah0R~
    //********************************************                 //~1Ah0I~
    //*send initial command to get version etc                     //~1Ah0I~
    //********************************************                 //~1Ah0I~
    private void sendInitialCmd()                                  //~1Ah0I~
    {                                                              //~1Ah0I~
		m_gtp.waitFirstPrompt(INITIAL_CMD);  //GuiGtpClient                   //~1Ah0R~
		if (Dump.Y) Dump.println("GoGui:sendInitialCmd waitFirstPrompt returned");//~1Ah0R~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
            for (String s:subcmdList)                              //~1Ah0R~
            {                                                      //~1Ah0R~
                if (m_gtp.m_gtp.isProgramDead())    //GuiGtpClient.GtpClient//~1Ah0R~
                    break;                                         //~1Ah0R~
                if (Dump.Y) Dump.println("GoGui:sendInitialCmd subcmd="+s);//~1Ah0R~
                m_gtp.send(s);  //GuiGtpClient-->GtpClient         //~1Ah0R~
                if (Dump.Y) Dump.println("GoGui:sendInitialCmd responsed subcmd="+s);//~1Ah0I~
            }                                                      //~1Ah0R~
            if (Dump.Y) Dump.println("GoGui:sendInitialCmd GameStarted on");//~1Ah0I~
            swGameStarted=true;                                    //~1Ah0I~
        }                                                          //~1Ah0I~
        catch (GtpError e)                                         //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"GoGui:senInitialCmd");                 //~1Ah0I~
        }                                                          //~1Ah0I~
    }                                                              //~1Ah0I~
    //**************************************************************//~1Ah0I~
    //*from GTPConnector to GtpClient thru GoGui                   //~1Ah0I~
    //**************************************************************//~1Ah0I~
    public void sendPlay(String Pmovecmd) throws GtpError           //~1Ah0I~
    {                                                              //~1Ah0I~
	    cmdType=CMDTYPE_MOVE;                                      //~1Ah0I~
        GtpError ee=null;                                          //~1Ah0I~
        ctrSendPlay++;                                             //~1Ah0R~
        computerMove=null;                                         //~1Ah0R~
		if (Dump.Y) Dump.println("GoGui:sendPlay cmd="+Pmovecmd);  //~1Ah0I~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
	        gtpClient.sendPlay(Pmovecmd);                          //~1Ah0I~
        }                                                          //~1Ah0I~
        catch (GtpError e)                                         //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"GoGui:sendPlay");                      //~1Ah0I~
	        showError(e);                                          //~1Ah0I~
            ee=e;                                                  //~1Ah0I~
        }                                                          //~1Ah0I~
        if (ee!=null)                                              //~1Ah0I~
	    	throw ee;                                              //~1Ah0I~
		if (Dump.Y) Dump.println("GoGui:sendPlay return cmd="+Pmovecmd);//~1Ah0I~
    }                                                              //~1Ah0I~
    //**************************************************************//~1Ah0I~
    //*from GTPConnector to GtpClient thru GoGui                   //~1Ah0I~
    //**************************************************************//~1Ah0I~
    public void sendCmd(int Pcmdtype,String Pcmd,boolean Pwait) throws GtpError//~1Ah0R~
    {                                                              //~1Ah0I~
    	cmdType=Pcmdtype;                                          //~1Ah0I~
        GtpError ee=null;                                          //~1Ah0I~
        ctrSendPlay++;                                             //~1Ah0I~
        computerMove=null;                                         //~1Ah0R~
		if (Dump.Y) Dump.println("GoGui:sendCmd cmd="+Pcmd);       //~1Ah0I~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
	        gtpClient.sendCmd(Pcmd,Pwait);                         //~1Ah0R~
        }                                                          //~1Ah0I~
        catch (GtpError e)                                         //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"GoGui:sendCmd");                       //~1Ah0I~
	        showError(e);                                          //~1Ah0I~
            ee=e;                                                  //~1Ah0I~
        }                                                          //~1Ah0I~
        if (ee!=null)                                              //~1Ah0I~
	    	throw ee;                                              //~1Ah0I~
		if (Dump.Y) Dump.println("GoGui:sendCmd return cmd="+Pcmd);//~1Ah0I~
    }                                                              //~1Ah0I~
    //**************************************************************//~1Ah0I~
    public boolean isProgramDead()                                 //~1Ah0I~
    {                                                              //~1Ah0I~
    	if (m_gtp!=null && m_gtp.m_gtp !=null)                     //~1Ah0I~
			return m_gtp.m_gtp.isProgramDead();    //GuiGtpClient.GtpClient//~1Ah0I~
        return false;                                              //~1Ah0I~
    }                                                              //~1Ah0I~
//    //**************************************************************//~1Ah0R~
//    //*rc:true:error                                             //~1Ah0R~
//    //**************************************************************//~1Ah0R~
//    private boolean chkComputerMoved(String Pline)               //~1Ah0R~
//    {                                                            //~1Ah0R~
//        String color;                                            //~1Ah0R~
//        if (Dump.Y) Dump.println("GoGui:chkComputerMoved line="+Pline);//~1Ah0R~
//        if (Pline.charAt(0)!=GtpClient.RESPONSEID_COMPUTER_MOVED)//~1Ah0R~
//            return false;   //ignore                             //~1Ah0R~
//        String ss=Pline.substring(1);                            //~1Ah0R~
//        if (ss.startsWith(GtpClient.PROMPT_BLACK))               //~1Ah0R~
//            color=GtpClient.BLACKID;                             //~1Ah0R~
//        else                                                     //~1Ah0R~
//        if (ss.startsWith(GtpClient.PROMPT_WHITE))               //~1Ah0R~
//            color=GtpClient.WHITEID;                             //~1Ah0R~
//        else                                                     //~1Ah0R~
//            return false;   //ignore                             //~1Ah0R~
//                                                                 //~1Ah0R~
//        int pos=Pline.indexOf(GtpClient.RESPONSEID);             //~1Ah0R~
//        if (pos<0)                                               //~1Ah0R~
//            return true;     //err                               //~1Ah0R~
//        String s=Pline.substring(pos+1).trim();                  //~1Ah0R~
//        if (Dump.Y) Dump.println("GoGui:chkComputerMoved s="+s); //~1Ah0R~
//        pos=s.indexOf(' ');                                      //~1Ah0R~
//        if (pos<0)                                               //~1Ah0R~
//            return true;     //err                               //~1Ah0R~
//        computerMove=color+s.substring(0,pos);  //used at prompt gotten//~1Ah0R~
//        if (Dump.Y) Dump.println("GoGui:chkComputerMoved computerMove="+computerMove);//~1Ah0R~
//        return false;   //no err                                 //~1Ah0R~
//    }//chkComputerMoved                                          //~1Ah0R~
    //***************************************************************************//~1Ah0R~
    //*chk >!White .. pattern  ">!White Resigned" , "  "!White nn!1716FU ..."//~1Ah0R~
    //*out "W1716FU", "WResigned"                                  //~1Ah0I~
    //***************************************************************************//~1Ah0R~
    private String chkComputerMoved(String Pline)                  //~1Ah0R~
    {                                                              //~1Ah0R~
        int pos;                                                   //~1Ah0R~
        String computermove,color;                                 //~1Ah0R~
    //***********************                                      //~1Ah0I~
        if (Dump.Y) Dump.println("GoGui:chkComputerMoved line="+Pline);//~1Ah0R~
        if (Pline.charAt(1)!=GtpClient.RESPONSEID_COMPUTER_MOVED)  //~1Ah0R~
            return null;   //ignore                                //~1Ah0R~
        color=Pline.substring(2,3);	//"B"/"W"                      //~1Ah0R~
        pos=Pline.indexOf(GtpClient.RESPONSEID_COMPUTER_MOVED,3);  //"!"//~1Ah0R~
        if (pos>0)                                                 //~1Ah0R~
            computermove=color+Pline.substring(pos+1);             //~1Ah0R~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
	        pos=Pline.indexOf(' ',3);                              //~1Ah0I~
            if (pos>0)                                             //~1Ah0I~
            	computermove=color+Pline.substring(pos+1);         //~1Ah0I~
            else                                                   //~1Ah0I~
            	return null;                                       //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("GoGui:chkComputerMoved computermove="+computermove);//~1Ah0R~
        return computermove;   //no err                            //~1Ah0R~
    }//chkComputerMoved                                            //~1Ah0R~
    //**************************************************************//~1Ah0I~
    //*rc:true:error                                               //~1Ah0I~
    //**************************************************************//~1Ah0I~
    private boolean computerMoved(String Pmsg,boolean Perr)        //~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GoGui:computerMoved err="+Perr+",msg="+Pmsg);//~1Ah0R~
        if (ctrSendPlay==0)  //move and other cmd ctr              //~1Ah0I~
        	return false;                                          //~1Ah0M~
        if (Perr)	//move received ERROR                          //~1Ah0M~
        {                                                          //~1Ah0M~
        	if (Dump.Y) Dump.println("GoGui:computerMoved move received err;ctrSendPlay="+ctrSendPlay);//~1Ah0M~
			return gtpconnector.cmdResponseGotten(cmdType,Pmsg,Perr);//~1Ah0M~
        }                                                          //~1Ah0M~
//        if (cmdType==CMDTYPE_RESIGN)                             //~1Ah0R~
//        {                                                        //~1Ah0R~
//            if (Dump.Y) Dump.println("GoGui:computerMoved Human resigned;ctrSendPlay="+ctrSendPlay);//~1Ah0R~
//            return gtpconnector.cmdResponseGotten(cmdType,Pmsg,Perr);//~1Ah0R~
//        }                                                        //~1Ah0R~
    	if (cmdType!=CMDTYPE_MOVE)                                 //~1Ah0R~
        {                                                          //~1Ah0I~
        	if (Dump.Y) Dump.println("GoGui:computerMoved not move;ctrSendPlay="+ctrSendPlay);//~1Ah0I~
			return gtpconnector.cmdResponseGotten(cmdType,Pmsg,Perr);//~1Ah0R~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("GoGui:computerMoved move response ctrSendPlay="+ctrSendPlay);//~1Ah0R~
        int promptid=GtpMove.MOVEID_MOVED;                         //~1Ah0I~
        if (Pmsg.startsWith(PROMPT_RESIGNED,PROMPT_MOVEID_POS))   //">White Resigned"//~1Ah0R~
        {                                                          //~1Ah0I~
            promptid=GtpMove.MOVEID_RESIGNED;                      //~1Ah0I~
	        if (Dump.Y) Dump.println("GoGui:computerMoved resign by prompt such as \">White Resigned\"");//~1Ah0R~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        if (Pmsg.startsWith(PROMPT_MATED,PROMPT_MOVEID_POS)) //">White Mated"//~1Ah0R~
        {                                                          //~1Ah0I~
            promptid=GtpMove.MOVEID_MATED;                         //~1Ah0I~
	        if (Dump.Y) Dump.println("GoGui:computerMoved Mated by prompt such as \">White mated\"");//~1Ah0I~
        }                                                          //~1Ah0I~
//      String computermove=computerMove;                          //~1Ah0R~
        String computermove=chkComputerMoved(Pmsg);                //~1Ah0I~
    	if (computermove==null)     // ">White Resigned" etc       //~1Ah0R~
        {                                                          //~1Ah0I~
            if (promptid==GtpMove.MOVEID_MOVED)                    //~1Ah0R~
	        	return false;    //ignore prompt after ">!White was gotten"//~1Ah0R~
            computermove=Pmsg.substring(1);  //">White Resiged"-->"White Resigned"//~1Ah0R~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
        	if (computermove.startsWith(PROMPT_RESIGNED,1) //"WResigned"//~1Ah0R~
//      	||  computermove.startsWith(PROMPT_RESIGNED2,1)  //"Wresign" later "WResigned" will come//~1Ah0R~
        	)                                                      //~1Ah0I~
            {                                                      //~1Ah0I~
	        	if (Dump.Y) Dump.println("GoGui:computerMoved resign by computerMove msg such as \"!White Resigned\"");//~1Ah0I~
	            promptid=GtpMove.MOVEID_RESIGNED;                  //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("GoGui:computerMoved resp="+computermove+",moveid="+promptid);//~1Ah0R~
		gtpconnector.gotMoved(computermove,promptid);              //~1Ah0R~                                        //~1Ah0I~
        computerMove=null;                                         //~1Ah0I~
        return false;                                              //~1Ah0I~
    }//ComputerMoved                                               //~1Ah0R~
}
