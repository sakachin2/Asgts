//*CID://+1Ah0R~: update#=  163;                                   //~1Ah0R~
//*********************************                                //~@@@1I~
//1Ah0 2016/10/15 add robot:bonanza                                //~1Ah0I~
//1Afm 2016/09/26 fuego;not genmove but reg_genmove is required for timelimit per move.//~1AfmI~
//v1Ba 2014/08/14 Canvas enqRequest callback for gtp callback gotMoved, to prevent deadlock wait on main thread//~v1BaI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//@@@1 20110503:read stderr show by Toast                          //~@@@1R~
//*********************************                                //~@@@1I~
package com.Asgts.gtp;                                             //~1AfmR~

import java.io.*;
import java.util.ArrayList;

import jagoclient.Dump;
//import static java.text.MessageFormat.format;                      //~v1B6I~


import com.Asgts.gtp.GtpFrame;                                //~1AfmR~
import com.Asgts.gtp.GtpClient;                                    //~1AfmR~
//import com.Asgts.gtp.GtpSynchronizer;                            //~1AfmR~
//import com.Asgts.gtp.GtpClient.TimeoutCallback;                  //~1AfmR~

class GTPCloser extends Thread
{	GTPConnector C;
	public GTPCloser (GTPConnector c)
	{	C=c;
    	if (Dump.Y) Dump.println("GTPCloser");                      //~1511I~
		start();
	}
	public void run ()
	{   try                                                        //~@@@1R~
        {                                                          //~1512I~
			C.destroy();                                           //~1512I~
        }                                                          //~1512I~
		catch (Exception e)                                        //~1512I~
        {                                                          //~1512I~
        	Dump.println(e,"GTPCloser run exception");             //~1512I~
        }                                                          //~1512I~
	}
}

public class GTPConnector
  	implements Runnable                                            //~v1B6R~
{	String Program;
	Process P;
	InputStream In,Err;
	OutputStream Out;
//    boolean log;                                                 //~v1B6R~
    public static final int MAX_ERRLINESZ=200;                     //~@@@1I~
    int errLinesz;                                                 //~@@@1I~
    byte[] errLine=new byte[MAX_ERRLINESZ];                        //~@@@1R~
	public  GtpClient gtpclient;                                   //~v1B6R~
	public GtpFrame gtpconnection;                            //~v1B6R~
	public GoGui gogui;                                            //~v1B6I~
	private String strTimesettings;                                //~v1B6R~
    private int statusGameover;                                    //~v1B6I~
    public static final int GO_COMPUTER_RESIGNED=1;                //~v1B6R~
//  private boolean reg_genmove;                                   //~1AfmR~
    private File workingDir;                                       //~1Ah0I~
                                                                   //~@@@1I~
	/**
	@param program The GTP program's file name.
	*/	
//  public GTPConnector (String program,GTPConnection Pconnection) //~1Ah0R~
    public GTPConnector (String program,GtpFrame Pconnection,File Pwkdir)//~1Ah0I~
	{	Program=program;
        gtpconnection=Pconnection;                                 //~v1B6I~
        workingDir=Pwkdir;                                         //~1Ah0I~
	}                                                              //~v1B6I~
//    public GTPConnector (String program,GTPConnection Pconnection,boolean Preg_genmove)//~1Ah0R~
//    {                                                            //~1Ah0R~
//        Program=program;                                         //~1Ah0R~
//        gtpconnection=Pconnection;                               //~1Ah0R~
//        reg_genmove=Preg_genmove;                                //~1Ah0R~
//    }                                                            //~1Ah0R~
	
    private ArrayList<String> subcmdList;                          //~1Ah0I~
//  public void connect()                                          //~v1BaR~//~1Ah0R~
    public void connect(ArrayList<String> PsubcmdList)             //~1Ah0I~
		throws IOException
      {                                                            //~v1B6R~
        subcmdList=PsubcmdList;                                    //~1Ah0I~
		strTimesettings=gtpconnection.strTimesettings;             //~v1B6M~
        if (strTimesettings!=null && strTimesettings.equals(""))   //~v1B6M~
        	strTimesettings=null;                                  //~v1B6M~
        new Thread(this).start();                                  //~v1B6R~
	}
	
	
	boolean Sequence=false;
	
	
	boolean End,MineAnswer=true,His=false;
	int Command,Argument;
	
	
//	static public final int BLACK=2,WHITE=1;                       //~1Ah0R~
  	static public final int BLACK=1,WHITE=-1;                      //~1Ah0I~
	static public final int JAPANESE=1,SST=2;
	static public final int EVEN=1;
	
	/**
	Send a move.
	@param color The color of the move (BLACK,WHITE).
	@param pos The board position from 0 to 360.
	 * @throws GtpError 
	*/
    //*****************************************************************//~1Ah0I~
    //*Human move                                                  //~1Ah0I~
    //*from GtpFrame:moveset                                       //~1Ah0R~
    //*****************************************************************//~1Ah0I~
//  public void move(int color, int pos) throws GtpError           //~1Ah0R~
    public void move(int color,String Pmovestr,boolean Pdrop) throws GtpError    //~1Ah0R~
    {                                                              //~v1B6R~
		if (statusGameover!=0)                                     //~v1B6I~
        {                                                          //~v1B6I~
        	gtpconnection.gameovered(statusGameover);              //~v1B6I~
            return;                                                //~v1B6I~
        }                                                          //~v1B6I~
//      GoColor c=color2GoColor(color);                            //~v1B6R~//~1Ah0R~
//      GoPoint p=null;                                            //~1Ah0R~
//      GoPoint pold=null;                                         //~1Ah0R~
//      if (pos!=0) //pass                                         //~1Ah0R~
//      	p=pos2GoPoint(pos);                                    //~1Ah0R~
//  	Move m=Move.get(c,p);                                      //~1Ah0R~
//      gtpconnection.gtpclient.sendPlay(m);                       //~1Ah0I~
//      GtpMove m=GtpMove.create(Ppiece,pos,Pposold,Pdrop);        //~1Ah0R~
//  	String csamove=m.getMoveString();                          //~1Ah0R~
//      gtpconnection.gtpclient.sendPlay(Pmovestr);                //~1Ah0R~
        gogui.sendPlay(Pmovestr);                                  //~1Ah0I~
	}
    //*****************************************************************//~1Ah0I~
    //*Human cmd such as resign                                    //~1Ah0I~
    //*from GtpFrame:sendCmd                                       //~1Ah0I~
    //*****************************************************************//~1Ah0I~
    public void sendCmd(int Pcmdtype,String Pcmd,boolean Pwait) throws GtpError//~1Ah0R~
    {                                                              //~1Ah0I~
		if (statusGameover!=0)                                     //~1Ah0I~
        {                                                          //~1Ah0I~
        	gtpconnection.gameovered(statusGameover);              //~1Ah0R~
            return;                                                //~1Ah0I~
        }                                                          //~1Ah0I~
        gogui.sendCmd(Pcmdtype,Pcmd,Pwait);                        //~1Ah0R~
	}                                                              //~1Ah0I~
//*****************************                                    //~v1B6I~
	
	/**
	Take back n moves.
	@param n Number of moves to take back.
	*/
    public boolean takeback (int n)                                //~v1B6I~
		throws GtpError                                            //~v1B6I~
    {                                                              //~v1B6I~
	    return gogui.m_gtp.m_gtpSynchronizer.undo(n);               //~v1B6R~
	}

	GTPInterface I=null;
	public void setGTPInterface (GTPInterface i) { I=i; }
	public void removeGTPInterface (GTPInterface i) { I=null; }

    /**                                                            //~v1B6R~
    Start the IO thread. I.e., continually get something from the  //~v1B6R~
    program, and auto treat it in the answer() function.           //~v1B6R~
    */                                                             //~v1B6R~
    public void run()                                              //~v1B6R~
    {   try                                                        //~v1B6R~
        {                                                          //~v1B6R~
          	createGoGui();                                         //~v1B6I~
        }                                                          //~v1B6R~
        catch (Exception e)                                        //~1511R~//~v1B6R~
        {                                                          //~1511I~//~v1B6R~
            Dump.println(e,"GTPConnector run exception");          //~1511I~//~v1B6R~
        }                                                          //~1511I~//~v1B6R~
    }                                                              //~v1B6R~
	
	public void doclose ()
	{	new GTPCloser(this);
	}

	/**
	Destroy the program and close streams.
	*/	
	public void destroy ()
    {                                                              //~v1B6R~
        if (gogui!=null)                                           //~v1B6I~
            gogui.actionQuit();                                    //~v1B6I~
	}

//*****************************                                    //~v1B6I~
    GoColor color2GoColor(int color)                               //~v1B6R~
    {                                                              //~v1B6I~
    	if (color==BLACK)                                          //~v1B6I~
    		return GoColor.BLACK;                                  //~v1B6I~
    	return GoColor.WHITE;                                      //~v1B6I~
    }                                                              //~v1B6I~
//**************************************************************   //~v1B6I~
//*from move                                                       //~v1B6I~
//*pos(botom-left start) to cordinate  of bottom-left origin       //~v1B6I~
//**************************************************************   //~v1B6I~
//    private GoPoint pos2GoPoint(int Ppos)                        //~1Ah0R~
//    {                                                            //~1Ah0R~
//        int boardSize=getBoardSize();                            //~1Ah0R~
//        int i=(Ppos-1)%boardSize;                                //~1Ah0R~
//        int j=(Ppos-1)/boardSize;                                //~1Ah0R~
//        if (Dump.Y) Dump.println("pos2Point pos="+Ppos+"=>i="+i+",j="+j);//~1Ah0R~
//        return GoPoint.get(i,j);                                 //~1Ah0R~
//    }                                                            //~1Ah0R~
//**************************************************************   //~v1B6R~
//*cordinate to position by bottom-left origin(start=1)            //~v1B6I~
//**************************************************************   //~v1B6I~
//    private int point2Pos(GoPoint Ppoint)                        //~1Ah0R~
//    {                                                            //~1Ah0R~
//        int boardSize=getBoardSize();                            //~1Ah0R~
//        int i=Ppoint.getX();                                     //~1Ah0R~
//        int j=Ppoint.getY();                                     //~1Ah0R~
//        int pos=j*boardSize+i+1;                                 //~1Ah0R~
//        if (Dump.Y) Dump.println("point2Pos pos="+pos+"<==i="+i+",j="+j);//~1Ah0R~
//        return pos;                                              //~1Ah0R~
//    }                                                            //~1Ah0R~
//*****************************                                    //~v1B6I~
    public int getBoardSize()                                      //~v1B6I~
    {                                                              //~v1B6I~
    	int boardSize=gtpconnection.BoardSize;                     //~v1B6I~
    	return  boardSize;                                         //~v1B6R~
    }                                                              //~v1B6I~
    //*************************************************            //~v1B6I~
    private void createGoGui()                                     //~v1B6R~
    {                                                              //~v1B6I~
    	int move=0;                                                //~v1B6I~
    	boolean verbose=gtpconnection.Verbose;                     //~v1B6R~
    	boolean initComputerColor=true;                            //~v1B6I~
    	boolean computerBlack;                                     //~v1B6I~
    	boolean computerWhite;                                     //~v1B6I~
        if (gtpconnection.MyColor==BLACK)                          //~v1B6I~
        {                                                          //~v1B6I~
        	computerBlack=false;                                   //~v1B6I~
        	computerWhite=true;                                    //~v1B6I~
        }                                                          //~v1B6I~
        else                                                       //~v1B6I~
        {                                                          //~v1B6I~
        	computerBlack=true;                                    //~v1B6I~
        	computerWhite=false;                                   //~v1B6I~
        }                                                          //~v1B6I~
    	String gtpFile=null;                                       //~v1BaR~
    	String gtpCommand="";	//@@@@FIXME                        //~1Ah0R~
        File analyzeCommandsFile=null;                             //~v1BaR~
//  		gogui=new GoGui(this,Program,move,strTimesettings,verbose,initComputerColor,//~1Ah0R~
    		gogui=new GoGui(this,Program,workingDir,move,strTimesettings,verbose,initComputerColor,//~1Ah0I~
//  				computerBlack,computerWhite,gtpFile,gtpCommand,analyzeCommandsFile,reg_genmove);//~1AfmI~//~1Ah0R~
    				computerBlack,computerWhite,gtpFile,gtpCommand,analyzeCommandsFile,subcmdList);//~1Ah0I~
    }                                                              //~v1B6I~
    public void setVersion(String Pversion)                             //~1Ah0I~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GTPConnector.setversion ="+Pversion);//~1Ah0I~
        gtpconnection.setVersion(Pversion);   //GtpFrame           //~1Ah0I~
    }                                                              //~1Ah0I~
    //*************************************************            //~v1B6I~
    public Komi getKomi()                                          //~v1B6R~
    {                                                              //~v1B6I~
        int intkomi=gtpconnection.Komi;                            //~v1B6I~
        double komi=(intkomi==0 ? 0.0 : intkomi+0.5);              //~v1B6I~
        return new Komi(komi);                                     //~v1B6I~
    }                                                              //~v1B6I~
	public void gotOk()                                                 //~v1B6I~
    {                                                              //~v1B6I~
		if (Dump.Y) Dump.println("GTPConnector gotOk");            //~v1B6I~//~1Ah0R~
		if (I!=null)  //GTPConnection                              //~v1B6I~
			 I.gotOk();                                            //~v1B6I~
    }                                                              //~v1B6R~
//*****************************************************************//~v1B6I~
//*from GoGui:computerMoved                                        //~v1B6I~
//*to GTPConnection:gotMove                                        //~v1B6I~
//*****************************************************************//~v1B6I~
	//*****************************************************************//~1Ah0I~
	//*from GoGui:computerMoved  -->GtpFrame.GtpGoFrame.requestCallback//~1Ah0R~
	//*****************************************************************//~1Ah0I~
//  public void gotMoved(GoPoint Ppoint,boolean Presign)           //~v1B6R~//~1Ah0R~
    public void gotMoved(String Pcsamove,int Pmoveid)              //~1Ah0R~
    {                                                              //~v1B6I~
    	if (Dump.Y) Dump.println("GTPConnector:gotMoved move="+Pcsamove+",moveid="+Pmoveid);//+1Ah0R~
//    	CanvasCallbackParm p=new CanvasCallbackParm(FUNCID_GOTMOVED,Ppoint,Presign);//~v1BaI~//~1Ah0R~
		int color=(Pcsamove.startsWith(GtpClient.BLACKID)?BLACK:WHITE);//~1Ah0I~
      	GtpMove p=new GtpMove(GtpMove.GTPMOVE_COMPUTER_MOVE,color,Pcsamove.substring(1),Pmoveid);//~1Ah0R~
        gtpconnection.F.requestCallback(p);                        //~v1BaI~
    }                                                              //~v1BaI~
	//*****************************************************************//~1Ah0I~
	//*from GoGui:computerMoved  -->GtpFrame.cmdResponseGotten 
    //return true if err ~1Ah0I~
	//*****************************************************************//~1Ah0I~
    public boolean cmdResponseGotten(int Pcmdtype,String Pmsg,boolean Perr)//~1Ah0R~
    {                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GTPConnector:cmdResponseGotten err="+Perr+",cmdtype="+Pcmdtype);//~1Ah0R~
        return gtpconnection.cmdResponseGotten(Pcmdtype,Pmsg,Perr);//~1Ah0R~
    }                                                              //~1Ah0I~
//    //*****************************************************************//~1Ah0R~
//    //*from GtpGoFrame by computermove on Canvas thred           //~1Ah0R~
//    //*****************************************************************//~1Ah0R~
//    public void canvasCallback(Object Pobj)                        //~v1BaI~//~1Ah0R~
//    {                                                              //~v1BaI~//~1Ah0R~
////      CanvasCallbackParm parm=(CanvasCallbackParm)Pobj;        //~1Ah0R~
//        GtpMove parm=(GtpMove)Pobj;                              //~1Ah0R~
////      GoPoint point=parm.point;                                //~1Ah0R~
//        int piece=parm.piece;                                    //~1Ah0R~
//        boolean resign=parm.resign;                                //~v1BaI~//~1Ah0R~
////      int funcid=parm.funcid;                                    //~v1BaI~//~1Ah0R~
////      if (funcid==CanvasCallbackParm.FUNCID_GOTMOVED)          //~1Ah0R~
////          callbackGotMoved(point,resign);                        //~v1BaI~//~1Ah0R~
//            callbackGotMoved(parm.xx,parm.yy,parm.oldxx,parm.oldyy,piece,resign,parm.drop);//~1Ah0R~
//    }                                                              //~v1BaI~//~1Ah0R~
//  public void callbackGotMoved(GoPoint Ppoint,boolean Presign)   //~v1BaI~//~1Ah0R~
//    public void callbackGotMoved(int Px,int Py,int Pxold,int Pyold,int Ppiece,boolean Presign,boolean Pdrop)//~1Ah0R~
//    {                                                              //~v1BaI~//~1Ah0R~
//        int pos;                                                 //~1Ah0R~
//        int posfrom;                                             //~1Ah0R~
//        if (Dump.Y) Dump.println("GTPConnector:callbackGotMoved x="+Px+",y="+Py+",piece="+Ppiece+",oldx="+Pxold+",oldy="+Pyold);//~1Ah0R~
//        if (Presign)                                               //~v1B6I~//~1Ah0R~
//        {                                                          //~v1B6I~//~1Ah0R~
//            statusGameover=GO_COMPUTER_RESIGNED;                   //~v1B6R~//~1Ah0R~
//            gtpconnection.resign();                                 //~v1B6I~//~1Ah0R~
//            return;                                                //~v1B6I~//~1Ah0R~
//        }                                                          //~v1B6I~//~1Ah0R~
////      if (Ppoint==null)//pass                                  //~1Ah0R~
////          pos=-1;                                              //~1Ah0R~
////      else                                                     //~1Ah0R~
////          pos=point2Pos(Ppoint);                               //~1Ah0R~
//            pos=GtpMove.point2Pos(Px,Py);                        //~1Ah0R~
////      if (Ppointfrom==null)//pass                              //~1Ah0R~
////          posfrom=-1;                                          //~1Ah0R~
////      else                                                     //~1Ah0R~
//            posfrom=GtpMove.point2Pos(Pxold,Pyold);              //~1Ah0R~
//        if (I!=null)                                               //~v1B6I~//~1Ah0R~
//        {                                                          //~v1B6I~//~1Ah0R~
//            if (gtpconnection.MyColor==BLACK)                      //~v1B6R~//~1Ah0R~
////              I.gotMove(WHITE,pos);                              //~v1B6I~//~1Ah0R~
////GtpFrame.gotMove                                               //~1Ah0R~
//                I.gotMove(WHITE,pos,posfrom,Ppiece,Pdrop);       //~1Ah0R~
//            else                                                   //~v1B6R~//~1Ah0R~
////              I.gotMove(BLACK,pos);                              //~v1B6I~//~1Ah0R~
//                I.gotMove(BLACK,pos,posfrom,Ppiece,Pdrop);       //~1Ah0R~
//        }                                                        //~1Ah0R~
//    }//~v1B6I~                                                   //~1Ah0R~
}
