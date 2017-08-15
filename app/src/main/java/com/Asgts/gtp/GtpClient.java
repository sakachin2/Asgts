//*CID://+1Ah0R~:                             update#=  149;       //~1Ah0R~
//***********************************************************************
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)
//***********************************************************************
// GtpClient.java
package com.Asgts.gtp;                                             //~v1B6R~


import jagoclient.Dump;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
//import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
                                                                   //~v1B6I~

/** Interface to a Go program that uses GTP over the standard I/O streams.//~v1B6R~
    <p>                                                            //~v1B6R~
    This class is final because it starts a thread in its constructor which//~v1B6R~
    might conflict with subclassing because the subclass constructor will//~v1B6R~
    be called after the thread is started.                         //~v1B6R~
    </p>                                                           //~v1B6R~
    <p>                                                            //~v1B6R~
    Callbacks can be registered to monitor the input, output and error stream//~v1B6R~
    and to handle timeout and invalid responses.                   //~v1B6R~
    </p> */                                                        //~v1B6R~
public final class GtpClient                                       //~v1B6R~
    extends GtpClientBase                                          //~v1B6R~
{                                                                  //~v1B6R~
	public  static final String PROMPT_BLACK="Black";              //~1Ah0M~
	public  static final String PROMPT_WHITE="White";              //~1Ah0M~
	public  static final String BLACKID="B";                       //~1Ah0I~
	public  static final String WHITEID="W";                       //~1Ah0I~
//  public  static final int    RESPONSEID_COMPUTER_MOVED=0x07;	//=0x07(bell) stdout input for computer move//~1Ah0R~
    public  static final int    RESPONSEID_COMPUTER_MOVED='!';	//modified bonanza to avoid beep and dropped by trim()//~1Ah0I~
    public  static final String RESPONSEID_COMPUTER_MOVED_STRING="!";	//modified bonanza to avoid beep and dropped by trim()//~1Ah0I~
	public  static final String RESPONSEID=">";             //stdout input:at end,when putque at end//~1Ah0R~
	public  static final String RESPONSEID_ECHO="<";        //bonanza was modified to echo to stdout//~1Ah0R~
	public  static final String RESPONSEID_COMPUITER_PLAY="=";     //~1Ah0I~
    private static final int SENDPLAY_COMPLETED=0;                  //~1Ah0I~
    private static final int SENDPLAY_REQUESTED=1;                  //~1Ah0I~
    private static final int SENDPLAY_RESPONSED=2;                  //~1Ah0I~
    private int sendPlay=SENDPLAY_COMPLETED;                      //~1Ah0R~
    private static final int SENDCMD_COMPLETED=0;                  //~1Ah0I~
    private static final int SENDCMD_REQUESTED=1;                  //~1Ah0I~
    private int sendCmd=SENDCMD_COMPLETED;                         //~1Ah0I~
    private int promptCtr;                                       //~1Ah0I~
                                                                   //~1Ah0I~
                                                                   //~1Ah0I~
    /** The timeout for commands that are expected to be fast.     //~v1B6I~
        GoGui 0.9.4 used 8 sec, but this was not enough on some machines//~v1B6I~
        when starting up engines like Aya in the Wine emulator. */ //~v1B6I~
                                                                   //~v1B6I~
    public String m_pgm;                                           //~1Ah0I~
    /** Exception thrown if executing a GTP engine failed. */      //~v1B6R~
    @SuppressWarnings("serial")                                     //~1Ah0M~
    public static class ExecFailed                                 //~v1B6R~
        extends GtpError                                           //~v1B6R~
    {                                                              //~v1B6R~
        public String m_program;                                   //~v1B6R~

        public ExecFailed(String program, String message)          //~v1B6R~
        {                                                          //~v1B6R~
            super(message);                                        //~v1B6R~
            m_program = program;                                   //~v1B6R~
        }                                                          //~v1B6R~

        public ExecFailed(String program, IOException e)           //~v1B6R~
        {                                                          //~v1B6R~
            this(program, e.getMessage());                         //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** Callback if a timeout occured. */                          //~v1B6R~
    public interface TimeoutCallback                               //~v1B6R~
    {                                                              //~v1B6R~
        /** Ask for continuation.                                  //~v1B6R~
            If this function returns true, Gtp.send will wait for another//~v1B6R~
            timeout period, if it returns false, the program will be killed. *///~v1B6R~
        boolean askContinue();                                     //~v1B6R~
    }                                                              //~v1B6R~

    /** Callback if an invalid response occured.                   //~v1B6R~
        Can be used to display invalid responses (without a status character)//~v1B6R~
        immediately, because send will not abort on an invalid response//~v1B6R~
        but continue to wait for a valid response line.            //~v1B6R~
        This is necessary for some Go programs with broken GTP implementation//~v1B6R~
        which write debug data to standard output (e.g. Wallyplus 0.1.2). *///~v1B6R~
    public interface InvalidResponseCallback                       //~v1B6R~
    {                                                              //~v1B6R~
        void show(String line);                                    //~v1B6R~
    }                                                              //~v1B6R~

//    /** Callback interface for logging or displaying the GTP stream.
//        Note that some of the callback functions are called from different
//        threads. */
    public interface IOCallback                                    //~v1B6R~
    {                                                              //~v1B6R~
        void receivedInvalidResponse(String s);                    //~v1B6R~

        void receivedResponse(boolean error, String s);            //~v1B6R~

        void receivedStdErr(String s);                             //~v1B6R~

        void sentCommand(String s);                                //~v1B6R~
    }                                                              //~v1B6R~

    /** Constructor.                                               //~v1B6R~
        @param program Command line for program.                   //~v1B6R~
        Will be split into words with respect to " as in StringUtil.tokenize.//~v1B6R~
        If the command line contains the string "%SRAND", it will be replaced//~v1B6R~
        by a random seed. This is useful if the random seed can be set by//~v1B6R~
        a command line option to produce deterministic randomness (the//~v1B6R~
        command returned by getProgramCommand() will contain the actual//~v1B6R~
        random seed used).                                         //~v1B6R~
        @param workingDirectory The working directory to run the program in or//~v1B6R~
        null for the current directory                             //~v1B6R~
        @param log Log input, output and error stream to standard error.//~v1B6R~
        @param callback Callback for external display of the streams. *///~v1B6R~
    public GtpClient(String program, File workingDirectory, boolean log,//~v1B6R~
                       IOCallback callback)                        //~v1B6R~
        throws GtpClient.ExecFailed                                //~v1B6R~
    {                                                              //~v1B6R~
        m_log = log;                                               //~v1B6R~
        m_callback = callback;                                     //~v1B6R~
        m_wasKilled = false;                                       //~v1B6R~
        m_program = program;                                       //~v1B6R~
		int pos=m_program.lastIndexOf('/');                         //~v1B6I~
        if (pos>=0)                                                //~v1B6I~
        	m_pgm=m_program.substring(pos+1);                      //~v1B6I~
        else                                                       //~v1B6I~
        	m_pgm=m_program;                                       //~v1B6I~
		logOut(program);                                           //~v1B6I~
        if (StringUtil.isEmpty(program))                           //~v1B6R~
            throw new ExecFailed(program,                          //~v1B6R~
                                 "Command for invoking Go program must be"//~v1B6R~
                                 + " not empty.");                 //~v1B6R~
        Runtime runtime = Runtime.getRuntime();                    //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            // Create command array with StringUtil::splitArguments//~v1B6R~
            // because Runtime.exec(String) uses a default StringTokenizer//~v1B6R~
            // which does not respect ".                           //~v1B6R~
            String[] cmdArray = StringUtil.splitArguments(program);//~v1B6R~
            // Make file name absolute, if working directory is not current//~v1B6R~
            // directory. With Java 1.5, it seems that Runtime.exec succeeds//~v1B6R~
            // if the relative path is valid from the current, but not from//~v1B6R~
            // the given working directory, but the process is not usable//~v1B6R~
            // (reading from its input stream immediately returns  //~v1B6R~
            // end-of-stream)                                      //~v1B6R~
            if (cmdArray.length > 0)                               //~v1B6R~
            {                                                      //~v1B6R~
                File file = new File(cmdArray[0]);                 //~v1B6R~
                // Only replace if executable is a path to a file, not//~v1B6R~
                // an executable in the exec-path                  //~v1B6R~
                if (file.exists())                                 //~v1B6R~
                    cmdArray[0] = file.getAbsolutePath();          //~v1B6R~
            }                                                      //~v1B6R~
            m_process = runtime.exec(cmdArray, null, workingDirectory);//~v1B6R~
        }                                                          //~v1B6R~
        catch (IOException e)                                      //~v1B6R~
        {                                                          //~v1B6R~
            throw new ExecFailed(program, e);                      //~v1B6R~
        }                                                          //~v1B6R~
        init(m_process.getInputStream(), m_process.getOutputStream(),//~v1B6R~
             m_process.getErrorStream());                          //~v1B6R~
    }                                                              //~v1B6R~


    /** Close the output stream to the program.                    //~v1B6R~
        Some engines don't handle closing the command stream without an//~v1B6R~
        explicit quit command well, so the preferred way to terminate a//~v1B6R~
        connection is to send a quit command. Closing the output stream after//~v1B6R~
        a quit is not strictly necessary, but may improve compatibility with//~v1B6R~
        engines that read the input stream in a different thread *///~v1B6R~
    public void close()                                            //~v1B6R~
    {                                                              //~v1B6R~
        m_out.close();                                             //~v1B6R~
    }                                                              //~v1B6R~

    /** Kill the Go program. */                                    //~v1B6R~
    public void destroyProcess()                                   //~v1B6R~
    {                                                              //~v1B6R~
    	if (Dump.Y) Dump.println("GtpClient destroy Process");     //~1Ah0I~
        if (m_process != null)                                     //~v1B6R~
        {                                                          //~v1B6R~
            m_wasKilled = true;                                    //~v1B6R~
            m_process.destroy();                                   //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

//    /** Did the engine ever send a valid response to a command? */
    public boolean getAnyCommandsResponded()                       //~v1B6R~
    {                                                              //~v1B6R~
        return m_anyCommandsResponded;                             //~v1B6R~
    }                                                              //~v1B6R~

    /** Get response to last command sent. */                      //~v1B6R~
    public String getResponse()                                    //~v1B6R~
    {                                                              //~v1B6R~
        return m_response;                                         //~v1B6R~
    }                                                              //~v1B6R~


    /** Get the command line that was used for invoking the Go program.//~v1B6R~
        @return The command line that was given to the constructor. *///~v1B6R~
    public String getProgramCommand()                              //~v1B6R~
    {                                                              //~v1B6R~
        return m_program;                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** Check if program is dead. */                               //~v1B6R~
    public boolean isProgramDead()                                 //~v1B6R~
    {                                                              //~v1B6R~
    	if (Dump.Y) Dump.println("GtpClient:isProgramDead="+m_isProgramDead);//~1Ah0I~
        return m_isProgramDead;                                    //~v1B6R~
    }                                                              //~v1B6R~

//    /** Send a command.
//        @return The response text of the successful response not including
//        the status character.
//        @throws GtpError containing the response if the command fails. */
    //****************************************************************//~1Ah0I~
    public String send(String command) throws GtpError             //~v1B6R~
    {                                                              //~v1B6R~
        return send(command, -1, null);                            //~v1B6R~
    }                                                              //~v1B6R~
    //****************************************************************//~1Ah0I~
    public String send(String command,boolean Psendplay) throws GtpError//~1Ah0I~
    {                                                              //~1Ah0I~
    	if (Psendplay)                         //~1Ah0R~
    		sendPlay=SENDPLAY_REQUESTED;                           //~1Ah0R~
        send(command, -1, null);                                   //~1Ah0R~
        return null;                                               //~1Ah0R~
    }                                                              //~1Ah0I~
    //****************************************************************//~1Ah0I~
    public String send(String command,boolean Psendplay,boolean Pwait) throws GtpError//~1Ah0I~
    {                                                              //~1Ah0I~
    	if (Pwait)                                                 //~1Ah0I~
        	return send(command,Psendplay);                        //~1Ah0I~
    	if (Psendplay)                                             //~1Ah0I~
    		sendPlay=SENDPLAY_REQUESTED;                           //~1Ah0I~
        String rc=send(command,0,null);                            //~1Ah0I~
        return rc;                                                 //~1Ah0I~
    }                                                              //~1Ah0I~


    public String send(String command, long timeout,               //~v1B6R~
                       TimeoutCallback timeoutCallback) throws GtpError//~v1B6R~
    {                                                              //~v1B6R~
        sendCmd=SENDCMD_COMPLETED;                                 //~1Ah0I~
        if (timeout!=0)                                            //~1Ah0I~
        	if (sendPlay==SENDPLAY_COMPLETED) //not sendplay cmd   //~1Ah0I~
        		sendCmd=SENDCMD_REQUESTED;   //putmessage() by simple prompt//~1Ah0R~
    	if (Dump.Y) Dump.println("GtpClient:send cmd="+command+",timeout="+timeout+",sendPlay="+sendPlay+",sendCmd="+sendCmd);   //~v1B6I~//~1Ah0I~
//      assert ! command.trim().equals("");                        //~v1B6R~//~1Ah0R~
//      assert ! command.trim().startsWith("#");                   //~v1B6R~//~1Ah0R~
        m_timeoutCallback = timeoutCallback;                       //~v1B6R~
        m_fullResponse = "";                                       //~v1B6R~
        m_response = "";                                           //~v1B6R~
        ++m_commandNumber;                                         //~v1B6R~
        if (m_autoNumber)                                          //~v1B6R~
            command = Integer.toString(m_commandNumber) + " " + command;//~v1B6R~
        if (m_log)                                                 //~v1B6R~
            logOut(command);                                       //~v1B6R~
        m_out.println(command);                                    //~v1B6R~
        m_out.flush();                                             //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            if (m_out.checkError())                                //~v1B6R~
            {                                                      //~v1B6R~
                throwProgramDied();                                //~v1B6R~
            }                                                      //~v1B6R~
            if (m_callback != null)                                //~v1B6R~
                m_callback.sentCommand(command);                   //~v1B6R~
          if (timeout!=0)                                          //~1Ah0I~
            readResponse(timeout);                                 //~v1B6R~
            return m_response;                                     //~v1B6R~
        }                                                          //~v1B6R~
        catch (GtpError e)                                         //~v1B6R~
        {                                                          //~v1B6R~
            e.setCommand(command);                                 //~v1B6R~
            throw e;                                               //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~
    //****************************************************************//~1Ah0R~
    public void sendPlay(Move move, long timeout,                  //~v1B6R~
                         TimeoutCallback timeoutCallback) throws GtpError//~v1B6R~
    {                                                              //~v1B6R~
        send(getCommandPlay(move), timeout, timeoutCallback);      //~v1B6R~
    }                                                              //~v1B6R~
    //****************************************************************//~1Ah0I~
    //*from GoGui, at human resign etc                             //~1Ah0I~
    //****************************************************************//~1Ah0I~
    public void sendCmd(String Pcmd,boolean Pwait) throws GtpError //~1Ah0R~
    {                                                              //~1Ah0I~
        send(Pcmd,true/*expect response and prompt*/,Pwait);   //timeout=-1//~1Ah0R~
    }                                                              //~1Ah0I~
    //****************************************************************//~1Ah0I~

//    /** Send comment.
//        @param comment comment line (must start with '#'). */
    public void sendComment(String comment)                        //~v1B6R~
    {                                                              //~v1B6R~
//      assert comment.trim().startsWith("#");                     //~v1B6R~//~1Ah0R~
        if (m_log)                                                 //~v1B6R~
            logOut(comment);                                       //~v1B6R~
        if (m_callback != null)                                    //~v1B6R~
            m_callback.sentCommand(comment);                       //~v1B6R~
        m_out.println(comment);                                    //~v1B6R~
        m_out.flush();                                             //~v1B6R~
    }                                                              //~v1B6R~

//    /** Enable auto-numbering commands.
//        Every command will be prepended by an integer as defined in the GTP
//        standard, the integer is incremented after each command. */
    public void setAutoNumber(boolean enable)                      //~v1B6R~
    {                                                              //~v1B6R~
        m_autoNumber = enable;                                     //~v1B6R~
    }                                                              //~v1B6R~

//    /** Set the callback for invalid responses.
//        @see InvalidResponseCallback */
    public void setInvalidResponseCallback(InvalidResponseCallback callback)//~v1B6R~
    {                                                              //~v1B6R~
        m_invalidResponseCallback = callback;                      //~v1B6R~
    }                                                              //~v1B6R~

//    /** Wait until the process of the program exits. */
    public void waitForExit()                                      //~v1B6R~
    {                                                              //~v1B6R~
        if (m_process == null)                                     //~v1B6R~
            return;                                                //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            m_process.waitFor();                                   //~v1B6R~
            m_errorThread.join();                                  //~v1B6R~
            m_inputThread.join();                                  //~v1B6R~
        }                                                          //~v1B6R~
        catch (InterruptedException e)                             //~v1B6R~
        {                                                          //~v1B6R~
            printInterrupted();                                    //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** More sophisticated version of waitFor with timeout. */     //~v1B6R~
    public void waitForExit(int timeout, TimeoutCallback timeoutCallback)//~v1B6R~
    {                                                              //~v1B6R~
        if (m_process == null)                                     //~v1B6R~
            return;                                                //~v1B6R~
        while (true)                                               //~v1B6R~
        {                                                          //~v1B6R~
            if (ProcessUtil.waitForExit(m_process, timeout))       //~v1B6R~
                break;                                             //~v1B6R~
        	if (Dump.Y) Dump.println("GtpClient:waitForExit call askContinue");//~1Ah0I~
            if (! timeoutCallback.askContinue())                   //~v1B6R~
            {                                                      //~v1B6R~
                m_process.destroy();                               //~v1B6R~
                return;                                            //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        try                                                        //~v1B6R~
        {                                                          //~v1B6R~
            m_errorThread.join(timeout);                           //~v1B6R~
            m_inputThread.join(timeout);                           //~v1B6R~
        }                                                          //~v1B6R~
        catch (InterruptedException e)                             //~v1B6R~
        {                                                          //~v1B6R~
            printInterrupted();                                    //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    /** Was program forcefully terminated by calling destroyProcess() *///~v1B6R~
    public boolean wasKilled()                                     //~v1B6R~
    {                                                              //~v1B6R~
        return m_wasKilled;                                        //~v1B6R~
    }                                                              //~v1B6R~

    private static final class Message                             //~v1B6R~
    {                                                              //~v1B6R~
        public Message(String text)                                //~v1B6R~
        {                                                          //~v1B6R~
            m_text = text;                                         //~v1B6R~
        }                                                          //~v1B6R~

        public String m_text;                                      //~v1B6R~
    }                                                              //~v1B6R~

    private class InputThread                                      //~v1B6R~
        extends Thread                                             //~v1B6R~
    {                                                              //~v1B6R~
        InputThread(InputStream in, BlockingQueue<Message> queue)  //~v1B6R~
        {                                                          //~v1B6R~
            m_in = new BufferedReader(new InputStreamReader(in));  //~v1B6R~
            m_queue = queue;                                       //~v1B6R~
        }                                                          //~v1B6R~

        public void run()                                          //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                mainLoop();                                        //~v1B6R~
            }                                                      //~v1B6R~
            catch (Throwable t)                                    //~v1B6R~
            {                                                      //~v1B6R~
                StringUtil.printException(t);                      //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private final BufferedReader m_in;                         //~v1B6R~

      private final BlockingQueue<Message> m_queue;                //~v1B6R~

        private final StringBuilder m_buffer = new StringBuilder(1024);//~v1B6R~

        private void appendBuffer(String line)                     //~v1B6R~
        {                                                          //~v1B6R~
        	if (Dump.Y) Dump.println("GtpClient:appendBuffer line="+line);//~1Ah0I~
            m_buffer.append(line);                                 //~v1B6R~
            m_buffer.append('\n');                                 //~v1B6R~
        	if (Dump.Y) Dump.println("GtpClient:appendBuffer m_buffer="+m_buffer.toString());//~1Ah0I~
        }                                                          //~v1B6R~

        private boolean isResponseStart(String line)               //~v1B6R~
        {                                                          //~v1B6R~
        	if (Dump.Y) Dump.println("GtpClient:isResponseStart line="+line);//~1Ah0I~
//            if (line.length() < 1)                                 //~v1B6R~//~1Ah0R~
//                return false;                                      //~v1B6R~//~1Ah0R~
//            char c = line.charAt(0);                               //~v1B6R~//~1Ah0R~
//            return (c == '=' || c == '?');                         //~v1B6R~//~1Ah0R~
			String s=line.trim();                                  //~1Ah0I~
			boolean rc=s.startsWith(RESPONSEID);                   //~1Ah0R~
        	if (Dump.Y) Dump.println("GtpClient:isResponseStart rc="+rc+",line="+line);//~1Ah0I~
            return rc;                                             //~1Ah0I~
        }                                                          //~v1B6R~

        private void mainLoop() throws InterruptedException        //~v1B6R~
        {                                                          //~v1B6R~
            while (true)                                           //~v1B6R~
            {                                                      //~v1B6R~
//              String line = readLine();                          //~v1B6R~//~1Ah0R~
//              if (line == null)                                  //~v1B6R~//~1Ah0R~
                String line = readLine();                          //~@@@@I~
                if (line == null)                                  //~@@@@I~
//              ArrayList<String> lines = readLine();              //~1Ah0I~//~@@@@R~
//              if (lines == null)                                 //~1Ah0I~//~@@@@R~
                {                                                  //~v1B6R~
                	if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop readLine==null");//~1Ah0I~
//                  putMessage(null);                              //~v1B6R~//~1Ah0R~
                    return;                                        //~v1B6R~
                }                                                  //~v1B6R~
//            for (int ii=0;ii<lines.size();ii++)                  //~1Ah0I~//~@@@@R~
//            {                                                    //~1Ah0I~//~@@@@R~
//            	String line=lines.get(ii);                         //~1Ah0I~//~@@@@R~
                if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop line="+line);//~1Ah0I~
                appendBuffer(line);                                //~v1B6R~
//                if (isEndOfGreeting(line))                       //~1Ah0R~
//                {                                                //~1Ah0R~
//                    putMessage();   //enq from appendBuffer(line)//~1Ah0R~
//                    continue;                                    //~1Ah0R~
//                }                                                //~1Ah0R~
                                                                   //~1Ah0I~
                if (! isResponseStart(line))                       //~v1B6R~
                {                                                  //~v1B6R~
                	if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop not responsestart line="+line);//~1Ah0M~
                    if (line.startsWith(RESPONSEID_ECHO))          //~1Ah0I~
                    {                                              //~1Ah0I~
                		if (sendPlay==SENDPLAY_REQUESTED)//	//first stdout msg after sendPlay//~1Ah0R~
                        {                                          //~1Ah0I~
                    		sendPlay=SENDPLAY_RESPONSED;           //once return to requester//~1Ah0R~
                			putMessage();       					//post wait,clear buffer//~1Ah0I~
                    		continue;                              //~1Ah0I~
                        }                                          //~1Ah0I~
                    	m_buffer.setLength(0);                     //~1Ah0I~
                    	continue;                                  //~1Ah0I~
                    }                                              //~1Ah0I~
                    if (! line.trim().equals(""))                  //~v1B6R~
                    {                                              //~v1B6R~
                        if (m_callback != null)                    //~v1B6R~
                        //*GoGui:ioCallback                        //~1Ah0I~
                            m_callback.receivedInvalidResponse(line);//~v1B6R~
                        else                                       //~1Ah0I~
                			if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop not responsestart m_callback=null");//~1Ah0I~
                        if (m_invalidResponseCallback != null)     //~v1B6R~
                        //*GoGui:invalidResponseCallback           //~1Ah0I~
                            m_invalidResponseCallback.show(line);  //~v1B6R~
                        else                                       //~1Ah0I~
                			if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop not responsestart m_invalidResponseCallback=null");//~1Ah0I~
                    }                                              //~v1B6R~
                    m_buffer.setLength(0);                         //~v1B6R~
                    continue;                                      //~v1B6R~
                }                                                  //~v1B6R~
//                while (true)                                       //~v1B6R~//~1Ah0R~
//                {                                                  //~v1B6R~//~1Ah0R~
//                    line = readLine();                             //~v1B6R~//~1Ah0R~
//                    if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop responsestart line="+line);//~1Ah0R~
//                    appendBuffer(line);                            //~v1B6R~//~1Ah0R~
//                    if (line == null)                              //~v1B6R~//~1Ah0R~
//                    {                                              //~v1B6R~//~1Ah0R~
//                        putMessage(null);                          //~v1B6R~//~1Ah0R~
//                        return;                                    //~v1B6R~//~1Ah0R~
//                    }                                              //~v1B6R~//~1Ah0R~
//                    if (line.equals(""))                           //~v1B6R~//~1Ah0R~
//                    {                                              //~v1B6R~//~1Ah0R~
//                        putMessage();                              //~v1B6R~//~1Ah0R~
//                        break;                                     //~v1B6R~//~1Ah0R~
//                    }                                              //~v1B6R~//~1Ah0R~
//                }                                                  //~v1B6R~//~1Ah0R~
//                String s=line.trim();                            //~1Ah0R~
//                while(true)                                      //~1Ah0R~
//                {                                                //~1Ah0R~
//                    int pos=line.indexOf(RESPONSEID);            //~1Ah0R~
//                    if (pos<0)                                   //~1Ah0R~
//                    {                                            //~1Ah0R~
//                        if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop not response appendBuffer s="+s);//~1Ah0R~
//                        appendBuffer(s);                         //~1Ah0R~
//                        break;                                   //~1Ah0R~
//                    }                                            //~1Ah0R~
//                    String ss=s.substring(0,pos+1);              //~1Ah0R~
//                    if (Dump.Y) Dump.println("GtpClient:InputThread.mainLoop response appendBuffer s="+s+",ss="+ss);//~1Ah0R~
//                    appendBuffer(ss);                            //~1Ah0R~
//                    if (pos+1==s.length())                       //~1Ah0R~
//                        break;                                   //~1Ah0R~
//                    s=s.substring(pos+1).trim();                 //~1Ah0R~
//                }                                                //~1Ah0R~
//*prompt msg                                                      //~1Ah0I~
				if (Dump.Y) Dump.println("GtpClient:mainloop sendPlay="+sendPlay);//~1Ah0I~
                if (sendPlay!=SENDPLAY_COMPLETED)                  //~1Ah0I~
                {                                                  //~1Ah0R~
                	sendPlay=SENDPLAY_COMPLETED;                   //~1Ah0M~
                    if (m_callback != null)    //*GoGui:ioCallback //~1Ah0I~//~@@@@R~
                    {                                              //~1Ah0I~//~@@@@R~
                        m_callback.receivedResponse(false/*error*/,line);//~1Ah0I~//~@@@@R~
                    	m_buffer.setLength(0);                     //~@@@@I~
                        continue;                                  //~1Ah0I~//~@@@@R~
                    }                                              //~1Ah0I~//~@@@@R~
                }                                                  //~1Ah0R~
                else                                               //~1Ah0I~
                if (chkOtherPrompt(line))   //">White Mated"  etc  //~1Ah0I~
                {                                                  //~1Ah0I~
	                if (sendCmd!=SENDCMD_COMPLETED)  //response for read handicap.csa//~1Ah0I~
    	            {                                              //~1Ah0I~
        	        	sendPlay=SENDCMD_COMPLETED;                //~1Ah0I~
            		    putMessage();	//enq 1st prompt for sendInitialCmd()//~1Ah0I~
	                    m_callback.receivedResponse(true/*error*/,line);//+1Ah0I~
            	    }                                              //~1Ah0I~
                    else                                           //+1Ah0I~
                    {                                              //+1Ah0I~
	                    m_callback.receivedResponse(false/*error*/,line);//+1Ah0R~
                    	continue;                                  //+1Ah0R~
                    }                                              //+1Ah0I~
                }                                                  //~1Ah0I~
				if (Dump.Y) Dump.println("GtpClient:mainloop promptCtr="+promptCtr+",sendCmd="+sendCmd);//~1Ah0R~
                if (promptCtr==0)                                  //~1Ah0R~
            	    putMessage();	//enq 1st prompt for sendInitialCmd()//~1Ah0R~
                else                                               //~1Ah0I~
                if (sendCmd!=SENDCMD_COMPLETED)                    //~1Ah0I~
                {                                                  //~1Ah0I~
                	sendPlay=SENDCMD_COMPLETED;                    //~1Ah0I~
            	    putMessage();	//enq 1st prompt for sendInitialCmd()//~1Ah0I~
                }                                                  //~1Ah0I~
                else                                               //~1Ah0I~
                {                                                  //~1Ah0I~
					if (Dump.Y) Dump.println("GtpClient:mainloop ignored prompt="+line);//~1Ah0I~
                    m_buffer.setLength(0);	//ignore prompt after computer move prompt//~1Ah0I~
                }                                                  //~1Ah0I~
                promptCtr++;                                       //~1Ah0I~
//            }//ArrayList                                         //~1Ah0I~//~@@@@R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private void putMessage()                                  //~v1B6R~
        {                                                          //~v1B6R~
            // Calling Thread.yield increases the probability that the IO//~v1B6R~
            // callbacks for stderr and stdout are called in the right order//~v1B6R~
            // for the typical use case of a program writing to stderr//~v1B6R~
            // before writing the response. The yield costs some performance//~v1B6R~
            // however and could have a negative effect, if the program//~v1B6R~
            // writes to stderr immediately after the response (e.g. logging//~v1B6R~
            // output during pondering).                           //~v1B6R~
            Thread.yield();                                        //~v1B6R~//~1Ah0R~
            if (Dump.Y) Dump.println("GtpClient:void putMessage before putmessage");//~1Ah0I~
            putMessage(m_buffer.toString());                       //~v1B6R~
            if (Dump.Y) Dump.println("GtpClient:void putMessage after  putmessage");//~1Ah0I~
            m_buffer.setLength(0);                                 //~v1B6R~
        }                                                          //~v1B6R~

        private void putMessage(String text)                       //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
            	if (Dump.Y) Dump.println("GtpClient:InputThread.putMessage text="+(text==null?"null":text));//~1Ah0I~
                m_queue.put(new Message(text));                    //~v1B6R~
            }                                                      //~v1B6R~
            catch (InterruptedException e)                         //~v1B6R~
            {                                                      //~v1B6R~
                printInterrupted();                                //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

////      private String readLine()                                  //~v1B6R~//~1Ah0R~//~@@@@R~
//        private String pendingLine=null;                           //~1Ah0I~//~@@@@R~
//        private ArrayList<String> readLine()                       //~1Ah0I~//~@@@@R~
//        {                                                          //~v1B6R~//~@@@@R~
//            char[] buffer = new char[4096];                        //~1Ah0I~//~@@@@R~
//            String line;                                          //~1Ah0I~//~@@@@R~
//            try                                                    //~v1B6R~//~@@@@R~
//            {                                                      //~v1B6R~//~@@@@R~
//                if (Dump.Y) Dump.println("GtpClient:InputThread.readLine m_in="+m_in.toString());//~1Ah0I~//~@@@@R~
////              String line = m_in.readLine();                     //~v1B6R~//~1Ah0R~//~@@@@R~
//                int n=m_in.read(buffer);                           //~1Ah0I~//~@@@@R~
//                if (Dump.Y) Dump.println("GtpClient:InputThread.readLine n="+n);//~1Ah0I~//~@@@@R~
//                if (n>0)                                           //~1Ah0I~//~@@@@R~
//                    line=new String(buffer,0,n);                   //~1Ah0R~//~@@@@R~
//                else                                               //~1Ah0I~//~@@@@R~
//                    line=null;                                     //~1Ah0I~//~@@@@R~
//                if (m_log && line != null)                         //~v1B6R~//~@@@@R~
//                    logIn(line);                                   //~v1B6R~//~@@@@R~
//                if (Dump.Y) Dump.println("GtpClient:InputThread.readLine n="+n+",line="+line);//~1Ah0R~//~@@@@R~
//                if (line==null)                                    //~1Ah0I~//~@@@@R~
//                    return null;                                       //~v1B6R~//~1Ah0R~//~@@@@R~
//            }                                                      //~v1B6R~//~@@@@R~
//            catch (IOException e)                                  //~v1B6R~//~@@@@R~
//            {                                                      //~v1B6R~//~@@@@R~
//                Dump.println(e,"GtpClient:readLine");              //~v1B6I~//~@@@@R~
//                return null;                                       //~v1B6R~//~@@@@R~
//            }                                                      //~v1B6R~//~@@@@R~
//                                                                   //~1Ah0I~//~@@@@R~
//            ArrayList<String> al=new ArrayList<String>();                  //~1Ah0I~//~@@@@R~
////          String s=line.trim();   //this may drop top 0x07       //~1Ah0R~//~@@@@R~
//            String s;                                              //~1Ah0I~//~@@@@R~
//            if (pendingLine!=null)                                 //~1Ah0I~//~@@@@R~
//            {                                                      //~1Ah0I~//~@@@@R~
//                s=pendingLine+line;                                //~1Ah0I~//~@@@@R~
//                pendingLine=null;                                  //~1Ah0I~//~@@@@R~
//            }                                                      //~1Ah0I~//~@@@@R~
//            else                                                   //~1Ah0R~//~@@@@R~
//                s=line;                                            //~1Ah0I~//~@@@@R~
//            while(true)                                            //~1Ah0I~//~@@@@R~
//            {                                                      //~1Ah0I~//~@@@@R~
//                int pos=s.indexOf('\n');                               //~1Ah0I~//~@@@@R~
//                if (pos>=0)                                        //~1Ah0I~//~@@@@R~
//                {                                                  //~1Ah0I~//~@@@@R~
//                    line=s.substring(0,pos);                       //~1Ah0I~//~@@@@R~
//                    al.add(line);                                  //~1Ah0R~//~@@@@R~
//                    if (Dump.Y) Dump.println("GtpClient:readLine line="+line);//~1Ah0R~//~@@@@R~
//                    if (pos+1==s.length())                         //~1Ah0I~//~@@@@R~
//                        break;                                     //~1Ah0I~//~@@@@R~
//                    s=s.substring(pos+1);                          //~1Ah0I~//~@@@@R~
//                }                                                  //~1Ah0I~//~@@@@R~
//                else                                               //~1Ah0I~//~@@@@R~
//                {                                                  //~1Ah0I~//~@@@@R~
//                    if (s.charAt(0)==RESPONSEID_COMPUTER_MOVED) //coputer move line eas cut//~1Ah0I~//~@@@@R~
//                    {                                              //~1Ah0I~//~@@@@R~
//                        pendingLine=s;                             //~1Ah0I~//~@@@@R~
//                        break;                                     //~1Ah0I~//~@@@@R~
//                    }                                              //~1Ah0I~//~@@@@R~
//                    line=s;                                        //~1Ah0I~//~@@@@R~
//                    addprompt(al,line);                            //~1Ah0R~//~@@@@R~
//                    break;                                         //~1Ah0I~//~@@@@R~
//                }                                                  //~1Ah0I~//~@@@@R~
//            }                                                      //~1Ah0I~//~@@@@R~
//            return al;                                             //~1Ah0I~//~@@@@R~
//        }                                                          //~v1B6R~//~@@@@R~
//    }                                                              //~v1B6R~//~@@@@R~
                                                                   //~@@@@I~
        private String readLine()                                  //~@@@@I~
        {                                                          //~@@@@I~
            try                                                    //~@@@@I~
            {                                                      //~@@@@I~
                String line = m_in.readLine();                     //~@@@@I~
                if (m_log && line != null)                         //~@@@@I~
                    logIn(line);                                   //~@@@@I~
                if (Dump.Y) Dump.println("GtpClient:InputThread.readLine line="+line);//~@@@@I~
                if (line==null)                                    //~@@@@I~
                	return null;                                   //~@@@@I~
                if (line.length()>0 && line.charAt(0)==RESPONSEID_COMPUTER_MOVED)     //'!';  //modified bonanza to avoid beep and dropped by trim()//~1Ah0I~//~@@@@R~
                    line=getComputerMove(line,m_in);               //~1Ah0R~
                else                                               //~@@@@I~
	                line=chkprompt(line,0);                          //~@@@@R~//~1Ah0R~
                return line;                                       //~@@@@I~
            }                                                      //~@@@@I~
            catch (IOException e)                                  //~@@@@I~
            {                                                      //~@@@@I~
                Dump.println(e,"GtpClient:readLine");              //~@@@@I~
                return null;                                       //~@@@@I~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~
//    //*************************************************************//~1Ah0I~//~@@@@R~
//    //*split prompt line(it has no \n)                             //~1Ah0I~//~@@@@R~
//    //*************************************************************//~1Ah0I~//~@@@@R~
//    private void addprompt(ArrayList<String> Plist,String Pline)   //~1Ah0I~//~@@@@R~
//    {                                                              //~1Ah0I~//~@@@@R~
//        String s=Pline;                                            //~1Ah0I~//~@@@@R~
//        if (Dump.Y) Dump.println("GtpClient:InputThread.addprompt line="+Pline);//~1Ah0I~//~@@@@R~
//        int pos=s.indexOf(RESPONSEID);                             //~1Ah0R~//~@@@@R~
//        if (pos<0)                                                 //~1Ah0R~//~@@@@R~
//        {                                                          //~1Ah0R~//~@@@@R~
//            if (Dump.Y) Dump.println("GtpClient:InputThread.addprompt s="+s);//~1Ah0R~//~@@@@R~
//            Plist.add(s);                                          //~1Ah0R~//~@@@@R~
//        }                                                          //~1Ah0R~//~@@@@R~
//        else                                                       //~1Ah0I~//~@@@@R~
//        {                                                          //~1Ah0I~//~@@@@R~
//            String ss=s.substring(pos+1).trim();  //after ">"      //~1Ah0R~//~@@@@R~
//            if (ss.length()==0                                     //~1Ah0R~//~@@@@R~
//            &&  (s.startsWith(PROMPT_BLACK) || s.startsWith(PROMPT_WHITE)))//~1Ah0I~//~@@@@R~
//            {                                                      //~1Ah0I~//~@@@@R~
//                ss=RESPONSEID+s.substring(0,pos);  //set responseid at top//~1Ah0R~//~@@@@R~
//                Plist.add(ss);                                     //~1Ah0R~//~@@@@R~
//                if (Dump.Y) Dump.println("GtpClient:InputThread.addprompt prompt="+ss);//~1Ah0I~//~@@@@R~
//            }                                                      //~1Ah0I~//~@@@@R~
//            else                                                   //~1Ah0I~//~@@@@R~
//            {                                                      //~1Ah0I~//~@@@@R~
//                if (Dump.Y) Dump.println("GtpClient:InputThread.addprompt s="+s);//~1Ah0I~//~@@@@R~
//                Plist.add(s);                                      //~1Ah0I~//~@@@@R~
//            }                                                      //~1Ah0I~//~@@@@R~
//        }                                                          //~1Ah0I~//~@@@@R~
//    }//addprompt                                                   //~1Ah0I~//~@@@@R~
    //*************************************************************//~@@@@I~
    //*get prompt line data, move last ">" to top                  //~1Ah0R~
    //*************************************************************//~@@@@I~
    private String chkprompt(String Pline,int Ppos)                         //~@@@@I~//~1Ah0R~
    {                                                              //~@@@@I~
        String s=Pline;                                            //~@@@@I~
        if (Dump.Y) Dump.println("GtpClient:InputThread.chkprompt line="+Pline+",Ppos="+Ppos);//~@@@@I~//~1Ah0R~
        int pos=s.indexOf(RESPONSEID);                             //~@@@@I~
        if (pos>=0)                                                //~@@@@I~
        {                                                          //~@@@@I~
            String ss=s.substring(pos+1).trim();  //after ">"      //~@@@@I~
            if (ss.length()==0                                     //~@@@@I~
            &&  (s.startsWith(PROMPT_BLACK,Ppos) || s.startsWith(PROMPT_WHITE,Ppos)))//~@@@@I~//~1Ah0R~
            {                                                      //~@@@@I~
                s=RESPONSEID+s.substring(0,pos);  //">Black xxx" or ">!White xxx//~1Ah0R~
                if (Dump.Y) Dump.println("GtpClient:InputThread.chkprompt prompt="+s);//~@@@@I~//~1Ah0R~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
        return s;                                                  //~@@@@I~
    }//chkprompt                                                   //~@@@@I~
    //*************************************************************//~1Ah0I~
    //*chk ">White mated" etc                                      //~1Ah0I~
    //*************************************************************//~1Ah0I~
    private boolean chkOtherPrompt(String Pline)                   //~1Ah0I~
    {                                                              //~1Ah0I~
        boolean rc=false;                                                //~1Ah0I~
        if (Pline.startsWith(GoGui.PROMPT_MATED,GoGui.PROMPT_MOVEID_POS)) //">White Mated"//~1Ah0R~
        	rc=true;                                               //~1Ah0R~
        else                                                       //~1Ah0I~
        if (Pline.startsWith(GoGui.PROMPT_RESIGNED,GoGui.PROMPT_MOVEID_POS)) //">White Resigned"//~1Ah0I~
        	rc=true;                                               //~1Ah0R~
        if (Dump.Y) Dump.println("GtpClient:InputThread.chkOtherPrompt rc="+rc+",line="+Pline);//~1Ah0I~
        return rc;
    }//chkprompt                                                   //~1Ah0I~
    //*************************************************************//~1Ah0I~
    //*construct computermove msg with following line  of !White nn>//~1Ah0I~
    //*output is patern of ">!White nn!1716FU"  , ">!White Resigned"//~1Ah0I~
    //*************************************************************//~1Ah0I~
    private String getComputerMove(String Pline,BufferedReader Pinp)//~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpClient:getComputerMove entry line"+Pline);//~1Ah0I~
        String line=Pline;                                         //~1Ah0I~
        String prompt=chkprompt(line,1);                           //~1Ah0I~
        if (prompt.startsWith(RESPONSEID))                         //~1Ah0I~
        {                                                          //~1Ah0I~
            int pos=prompt.indexOf(' ');                           //~1Ah0I~
            if (pos>0)                                             //~1Ah0I~
            {                                                      //~1Ah0I~
                String movenumber=prompt.substring(pos).trim();    //~1Ah0I~
                int num=movenumber.charAt(0);                      //~1Ah0I~
                if (num>=0 && num<=9);                             //~1Ah0I~
                {                                                  //~1Ah0I~
                    try                                            //~1Ah0I~
                    {                                              //~1Ah0I~
                        String nextline=Pinp.readLine();           //~1Ah0R~
				        if (Dump.Y) Dump.println("GtpClient:getComputerMove nextline="+nextline);//~1Ah0I~
                        line=prompt.substring(0,pos)+RESPONSEID_COMPUTER_MOVED_STRING+nextline.trim(); //">!White nn!1716FU..."//~1Ah0R~
                    }                                              //~1Ah0I~
                    catch (IOException e)                          //~1Ah0I~
                    {                                              //~1Ah0I~
                        Dump.println(e,"GtpClient:getComputerMove read nextline");//~1Ah0I~
                    }                                              //~1Ah0I~
                }                                                  //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
//          String line=Pline;                                     //~1Ah0I~
//          int pos=line.indexOf(RESPONSEID);                      //~1Ah0I~
//          if (pos>0)                                             //~1Ah0I~
//              try                                                //~1Ah0I~
//              {                                                  //~1Ah0I~
//                  String nextline=m_in.readLine();               //~1Ah0I~
//                  line+=nextline; // "!White 2>1716FU..."        //~1Ah0I~
//              }                                                  //~1Ah0I~
//              catch (IOException e)                              //~1Ah0I~
//              {                                                  //~1Ah0I~
//                  Dump.println(e,"GtpClient:getComputerMove read nextline");//~1Ah0I~
//              }                                                  //~1Ah0I~
//                                                                 //~1Ah0I~
        if (Dump.Y) Dump.println("GtpClient:getComputerMove return line="+line);//~1Ah0R~
        return line;                                               //~1Ah0I~
    }                                                              //~1Ah0I~
    private class ErrorThread                                      //~v1B6R~
        extends Thread                                             //~v1B6R~
    {                                                              //~v1B6R~
        public ErrorThread(InputStream in, BlockingQueue<Message> queue)//~v1B6R~
        {                                                          //~v1B6R~
            m_in = new InputStreamReader(in);                      //~v1B6R~
            m_queue = queue;                                       //~v1B6R~
        }                                                          //~v1B6R~

        public void run()                                          //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                char[] buffer = new char[4096];                    //~v1B6R~
                while (true)                                       //~v1B6R~
                {                                                  //~v1B6R~
                    int n;                                         //~v1B6R~
                    try                                            //~v1B6R~
                    {                                              //~v1B6R~
                        n = m_in.read(buffer);                     //~v1B6R~
                    }                                              //~v1B6R~
                    catch (IOException e)                          //~v1B6R~
                    {                                              //~v1B6R~
                        return;                                    //~v1B6R~
                    }                                              //~v1B6R~
                    if (n <= 0)                                    //~v1B6R~
                        return;                                    //~v1B6R~
                    String text = new String(buffer, 0, n);        //~v1B6R~
	            	if (Dump.Y) Dump.println("GtpClient:errorThread.run text="+text);//~1Ah0I~
                    if (m_callback != null)                        //~v1B6R~
                        m_callback.receivedStdErr(text);           //~v1B6R~
                    if (m_log)                                     //~v1B6R~
                        logError(text);                            //~v1B6R~
                }                                                  //~v1B6R~
            }                                                      //~v1B6R~
            catch (Throwable t)                                    //~v1B6R~
            {                                                      //~v1B6R~
                StringUtil.printException(t);                      //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~

        private final Reader m_in;                                 //~v1B6R~
    }                                                              //~v1B6R~

    private InvalidResponseCallback m_invalidResponseCallback;     //~v1B6R~

    private boolean m_autoNumber;                                  //~v1B6R~

    private boolean m_anyCommandsResponded;                        //~v1B6R~

    private boolean m_isProgramDead;                               //~v1B6R~

    private boolean m_wasKilled;                                   //~v1B6R~

    private final boolean m_log;                                   //~v1B6R~

    private int m_commandNumber;                                   //~v1B6R~

    private IOCallback m_callback;                                 //~v1B6R~

    private PrintWriter m_out;                                     //~v1B6R~

    private Process m_process;                                     //~v1B6R~

    private String m_fullResponse;                                 //~v1B6R~

    private String m_response;                                     //~v1B6R~

    private String m_logPrefix;                                    //~v1B6R~

    private String m_program;                                      //~v1B6R~

    private BlockingQueue<Message> m_queue;                        //~v1B6R~

    private TimeoutCallback m_timeoutCallback;                     //~v1B6R~

    private InputThread m_inputThread;                             //~v1B6R~

    private ErrorThread m_errorThread;                             //~v1B6R~

    private void init(InputStream in, OutputStream out, InputStream err)//~v1B6R~
    {                                                              //~v1B6R~
        m_out = new PrintWriter(out);                              //~v1B6R~
        m_isProgramDead = false;                                   //~v1B6R~
        m_queue = new ArrayBlockingQueue<Message>(10);             //~v1B6R~
        m_inputThread = new InputThread(in, m_queue);              //~v1B6R~
        if (err != null)                                           //~v1B6R~
        {                                                          //~v1B6R~
            m_errorThread = new ErrorThread(err, m_queue);         //~v1B6R~
            m_errorThread.start();                                 //~v1B6R~
        }                                                          //~v1B6R~
        m_inputThread.start();                                     //~v1B6R~
    }                                                              //~v1B6R~

    private synchronized void logError(String text)                //~v1B6R~
    {                                                              //~v1B6R~
        System.err.print(text);                                    //~v1B6R~
    }                                                              //~v1B6R~

    private synchronized void logIn(String msg)                    //~v1B6R~
    {                                                              //~v1B6R~
        if (m_logPrefix != null)                                   //~v1B6R~
            System.err.print(m_logPrefix);                         //~v1B6R~
        System.err.print("<< ");                                   //~v1B6R~
        System.err.println(msg);                                   //~v1B6R~
    }                                                              //~v1B6R~

    private synchronized void logOut(String msg)                   //~v1B6R~
    {                                                              //~v1B6R~
        if (m_logPrefix != null)                                   //~v1B6R~
            System.err.print(m_logPrefix);                         //~v1B6R~
        System.err.print(">> ");                                   //~v1B6R~
        System.err.println(msg);                                   //~v1B6R~
    }                                                              //~v1B6R~

    /** Print information about occurence of InterruptedException. //~v1B6R~
        An InterruptedException should never happen, because we don't call//~v1B6R~
        Thread.interrupt */                                        //~v1B6R~
    private void printInterrupted()                                //~v1B6R~
    {                                                              //~v1B6R~
        System.err.println("GtpClient: InterruptedException");     //~v1B6R~
        Thread.dumpStack();                                        //~v1B6R~
    }                                                              //~v1B6R~

    private String readResponse(long timeout) throws GtpError      //~v1B6R~
    {                                                              //~v1B6R~
    	if (Dump.Y) Dump.println("GtpClient:readResponse timeout="+timeout);//~1Ah0I~
        while (true)                                               //~v1B6R~
        {                                                          //~v1B6R~
            Message message = waitForMessage(timeout);             //~v1B6R~
            String response = message.m_text;                      //~v1B6R~
    		if (Dump.Y) Dump.println("GtpClient:readResponse response="+response);//~1Ah0I~
            if (response == null)                                  //~v1B6R~
            {                                                      //~v1B6R~
                m_isProgramDead = true;                            //~v1B6R~
                throwProgramDied();                                //~v1B6R~
            }                                                      //~v1B6R~
            m_anyCommandsResponded = true;                         //~v1B6R~
//          boolean error = (response.charAt(0) != '=');           //~v1B6R~//~1Ah0R~
            boolean error=false;                                   //~1Ah0I~
//  		if (Dump.Y) Dump.println("GtpClient:readResponse error="+error);//~1Ah0R~
            m_fullResponse = response;                             //~v1B6R~
            if (m_callback != null)                                //~v1B6R~
            //*GoGui:ioCallback                                    //~1Ah0I~
                m_callback.receivedResponse(error, m_fullResponse);//~v1B6R~
//          assert response.length() >= 3;                         //~v1B6R~//~1Ah0R~
//          int index = response.indexOf(' ');                     //~v1B6R~//~1Ah0R~
//          int length = response.length();                        //~v1B6R~//~1Ah0R~
//          if (index < 0)                                         //~v1B6R~//~1Ah0R~
//              m_response = response.substring(1, length - 2);    //~v1B6R~//~1Ah0R~
//          else                                                   //~v1B6R~//~1Ah0R~
//              m_response = response.substring(index + 1, length - 2);//~v1B6R~//~1Ah0R~
//            if (response.endsWith(">"))                           //~1Ah0I~//~@@@@R~
//                response=response.substring(0,response.length()-1);//~1Ah0I~//~@@@@R~
            if (error)                                             //~v1B6R~
                throw new GtpError(m_response);                    //~v1B6R~
    		if (Dump.Y) Dump.println("GtpClient:readResponse return response="+response);//~1Ah0I~
            return m_response;                                     //~v1B6R~
        }                                                          //~v1B6R~
    }                                                              //~v1B6R~

    private void throwProgramDied() throws GtpError                //~v1B6R~
    {                                                              //~v1B6R~
        m_isProgramDead = true;                                    //~v1B6R~
        String name = m_name;                                      //~v1B6R~
        if (name == null)                                          //~v1B6R~
//          name = "The Go program";                               //~v1B6R~
        	name="Program:"+m_pgm;	//after drop path              //~v1B6I~
    	if (Dump.Y) Dump.println("GtpClient:throwProgramDied name="+name);//~1Ah0I~
        if (m_wasKilled)                                           //~v1B6R~
            throw new GtpError(name + " terminated.");             //~v1B6R~
        else                                                       //~v1B6R~
            throw new GtpError(name + " terminated unexpectedly.");//~v1B6R~
    }                                                              //~v1B6R~

    private Message waitForMessage(long timeout) throws GtpError   //~v1B6R~
    {                                                              //~v1B6R~
    	if (Dump.Y) Dump.println("GtpClient:waitForMessage timeout="+timeout);//~1Ah0I~
        Message message = null;                                    //~v1B6R~
        if (timeout < 0)                                           //~v1B6R~
        {                                                          //~v1B6R~
            try                                                    //~v1B6R~
            {                                                      //~v1B6R~
                message = m_queue.take();                          //~v1B6R~
            }                                                      //~v1B6R~
            catch (InterruptedException e)                         //~v1B6R~
            {                                                      //~v1B6R~
    			Dump.println(e,"GtpClient:timeout<0");             //~1Ah0I~
                printInterrupted();                                //~v1B6R~
                destroyProcess();                                  //~v1B6R~
                throwProgramDied();                                //~v1B6R~
            }                                                      //~v1B6R~
    		if (Dump.Y) Dump.println("GtpClient:waitForMessage timeout<0 msg text="+(message!=null?message.m_text:"null"));//~1Ah0R~
        }                                                          //~v1B6R~
        else                                                       //~v1B6R~
        {                                                          //~v1B6R~
            message = null;                                        //~v1B6R~
            while (message == null)                                //~v1B6R~
            {                                                      //~v1B6R~
                try                                                //~v1B6R~
                {                                                  //~v1B6R~
	    			if (Dump.Y) Dump.println("GtpClient:before m_queue.poll");//~1Ah0I~
                    message = m_queue.poll(timeout, TimeUnit.MILLISECONDS);//~v1B6R~
	    			if (Dump.Y) Dump.println("GtpClient:waitForMessage after  m_queue.poll message="+(message==null?"null":message.m_text));//~1Ah0R~
                }                                                  //~v1B6R~
                catch (InterruptedException e)                     //~v1B6R~
                {                                                  //~v1B6R~
	    			Dump.println(e,"GtpClient:timeout<0");         //~1Ah0I~
                    printInterrupted();                            //~v1B6R~
                }                                                  //~v1B6R~
	    		if (Dump.Y) Dump.println("GtpClient:waitForMessage timeout>=0 msg text="+(message!=null?message.m_text:"null"));//~1Ah0R~
                if (message == null)                               //~v1B6R~
                {                                                  //~v1B6R~
//                  assert m_timeoutCallback != null;              //~v1B6R~//~1Ah0R~
		        	if (Dump.Y) Dump.println("GtpClient:waitForMessage message==null call askContinue");//~1Ah0R~
                    if (! m_timeoutCallback.askContinue())         //~v1B6R~
                    {                                              //~v1B6R~
                        destroyProcess();                          //~v1B6R~
                        throwProgramDied();                        //~v1B6R~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~
            }                                                      //~v1B6R~
        }                                                          //~v1B6R~
        return message;                                            //~v1B6R~
    }                                                              //~v1B6R~
//    private boolean isEndOfGreeting(String line)                 //~1Ah0R~
//    {                                                            //~1Ah0R~
//        String s=line.trim();                                    //~1Ah0R~
//        boolean rc=s.startsWith(GoGui.END_OF_GREETING);          //~1Ah0R~
////      boolean rc=s.startsWith(GoGui.WARNING_INVALIDCMD);       //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpClient:isEndOfGreeting rc="+rc+",line="+line+",def="+GoGui.END_OF_GREETING);//~1Ah0R~
//        return rc;                                               //~1Ah0R~
//    }                                                            //~1Ah0R~
    public void waitFirstPrompt(String Pcmd,int Ptimeout,TimeoutCallback timeoutCallback) throws GtpError//~1Ah0R~
    {                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpClient:waitFirstPrompt cmd="+Pcmd+",timeout="+Ptimeout);//~1Ah0R~
        m_timeoutCallback = timeoutCallback;                       //~1Ah0I~
        m_fullResponse = "";                                       //~1Ah0I~
        m_response = "";                                           //~1Ah0I~
//      ++m_commandNumber;                                         //~1Ah0I~
//      if (m_autoNumber)                                          //~1Ah0I~
//          command = Integer.toString(m_commandNumber) + " " + command;//~1Ah0I~
//      if (m_log)                                                 //~1Ah0I~
//          logOut(command);                                       //~1Ah0I~
//      m_out.println(command);                                    //~1Ah0I~
//      m_out.flush();                                             //~1Ah0I~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
//          if (m_out.checkError())                                //~1Ah0I~
//          {                                                      //~1Ah0I~
//              throwProgramDied();                                //~1Ah0I~
//          }                                                      //~1Ah0I~
//          if (m_callback != null)                                //~1Ah0I~
//              m_callback.sentCommand(command);                   //~1Ah0I~
            readResponse(Ptimeout);                                 //~1Ah0I~
//          return m_response;                                     //~1Ah0I~
        }                                                          //~1Ah0I~
        catch (GtpError e)                                         //~1Ah0I~
        {                                                          //~1Ah0I~
            e.setCommand("waitFirstPrompt");                       //~1Ah0I~
            throw e;                                               //~1Ah0I~
        }                                                          //~1Ah0I~
    }                                                              //~1Ah0I~
}                                                                  //~v1B6R~
