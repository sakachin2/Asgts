//*CID://+1Ah0R~:                                   update#=  386; //~1Ah0R~
//***********************************************************************//~@@@1I~
//1Ah2*2016/10/28 (Bug)process @@!move was not on Synchthread      //~1Ah2I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//1Agb 2016/10/10 AxeDialog is 2 two place,com.axe and com.Asgts.axe, del later.//~1AgbR~
//1Ag5 2016/10/09 checkbox pie,set on as default when version>=5   //~1Ag5I~
//1Ag0 2014/10/05 displaying bot version number on menu delays until next restart(menu itemname is set at start and not changed)//~1Ag0I~
//1Afu 2014/10/04 android5 allows only PIE for ndk binary          //~1AfuI~
//1Aft 2014/10/04 reinstall bookfile was deleted                   //~1AftI~
//1Afs 2014/10/04 Asset file open err (getAsset().openFD:it is probably compressed) for Agnugo when size over 64k//~1AftR~
//                reset v1C8(back zip to png)                      //~1AfsI~
//1Afr 2016/10/02 add gnugo gtp mode                               //~1AfrI~
//1Afo 2016/09/28 pachi dialog change                              //~1AfoI~
//1Afj 2016/09/25 replace Agnugo/Apachi armeabi-v7a(hardware floating point calc)//~1AfjI~
//1Afh 2016/09/24 drop yourname from title of computer mach        //~1AfcI~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//v1Dh 2014/11/12 add predefined parameter settion for pach/fuego match-settiong dialog//~v1DhI~
//v1D3 2014/10/07 set timestamp to filename on filedialog when save(current is *.sgf)//~v1D3I~
//v1C8 2014/09/01 Agnugo.png-->Agnugo.zip(unzip at first time)     //~v1C8I~
//v1C2 2014/08/23 pachi hepl filename not found exception          //~v1C2I~
//v1C1 2014/08/21 install fuego as GTP client                      //~v1C1I~
//v1C0 2014/08/15 pachi:-s(seed) parameter for more randamizing    //~v1C0I~
//v1Bb 2014/08/14 -f book.dat support for pachi                    //~v1BbI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//@@@1 Modal dialog                                                //~@@@1I~//~v106R~
//***********************************************************************//~@@@1I~
package com.Asgts.gtp;                                             //~1AgbR~

//import java.util.*;

import java.util.ArrayList;

import com.Asgts.AView;                                        //~1AgbR~
import com.Asgts.R;                                                //~1AgbR~
import com.Asgts.awt.FileDialogI;
import com.Asgts.java.File;                                        //~1AgbR~
import com.Asgts.Alert;                                       //~1AgbR~

import jagoclient.Dump;
import jagoclient.MainFrame;
import jagoclient.board.Board;
import jagoclient.board.Notes;
import jagoclient.board.NotesFmtCsa;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.CloseFrame;
import jagoclient.partner.EndGameQuestion;
import jagoclient.partner.GameQuestion;
import jagoclient.sound.JagoSound;

import com.Asgts.AG;                                               //~1AgbR~


class OkAdapter
{	public void gotOk() {}
}

//public class GTPConnection extends AxeDialog                     //~1Ah0R~
//public class GtpFrame  extends LocalFrame                          //~1Ah0R~//~@@@9R~
public class GtpFrame  extends CloseFrame                          //~@@@9I~
	implements GTPInterface , FileDialogI                          //~1Ah0R~
{                                                                  //~v1B6R~
            static final int LAYOUT=R.layout.dialogbonanza;        //~1Ah0I~
            static final int TITLE =R.string.DialogTitle_gtpconnection;//~v1C1I~//~1Ah0R~
//	private static final String DEFAULT_GTPSERVER="Apachi";        //~v1B6R~
    public  static final String DEFAULT_GTPSERVER_ORGNAME="pachi"; //~1Ag0I~
                   final String DEFAULT_BOOK_ORGNAME="book.dat";   //~v1C1I~
//    private static final String DEFAULT_BOOK_ORGNAME2="bookra6.dat";//~v1C1R~
            static final String DEFAULT_BOOK="Abook.dat";          //~v1C1R~
            static final String DEFAULT_BOOK2="Abookra6.dat";      //~v1C1I~
//    private static final String DEFAULT_BOOK_ZIPFILE="Abook.png";  //2 file in,book.dat and book_ra6_dat//~1AfsI~
            static final int DEFAULT_WHITE=GTPConnector.BLACK; //your are BLACK//~v1C1I~
            static final int DEFAULT_BOARDSIZE=19;                 //~v1C1I~
            static final String DEFAULT_YOURNAME="You";            //~v1C1I~
            static final String DEFAULT_TIMESETTINGMOVE="1";       //~v1C1I~
//            static final int DEFAULT_HANDICAP=0;                 //~1Ah0R~
            static final boolean DEFAULT_PONDERING=true;           //~1AfoR~
            static final int DEFAULT_KOMI=0;                       //~1AfoI~
	public  static final int RULE_JAPANESE=GTPConnector.JAPANESE;  //~v1B6R~
	public  static final int RULE_CHINESE=GTPConnector.SST;        //~v1B6R~
            static final int DEFAULT_RULE=RULE_JAPANESE;           //~v1C1I~
//	private static final String PACHI_RULE_JAPANESE=" -r japanese"; //pachi commandline parameter//~1AfoR~
//            static final int MAX_HANDICAP=9;                     //~1Ah0R~
            static final String POSTFIX_PIE=".pie";	//pie executable posfix//~1AfuI~
                                                                   //~1Ag0I~
    public  static final int GTPID_GNUGO  =1;                      //~1Ag0I~
    public  static final int GTPID_PACHI  =2;                      //~1Ag0R~
    public  static final int GTPID_FUEGO  =3;                      //~1Ag0R~
    public  static final int GTPID_RAY    =4;                      //~1Ag0R~
                                                                   //~1Ah0I~
    public  static final String SENDCMD_RESIGN="resign T";         //~1Ah0R~
    public  static final String SENDCMD_QUIT="quit";               //~@@@9I~
                                                                   //~1Ah0I~
    public boolean Block=true;                                     //~1Ah0I~
                                                                   //~1Ag0I~
                                                                   //~v1C1I~
	                     String default_GTPSERVER_ORGNAME;         //~v1C1I~
	                     String default_GTPSERVER="Apachi";        //~v1C1I~
                                                                   //~v1BbI~
            static long installedBookSize;  //share with fuego     //~v1C8I~
      public static String Sversion="";                            //~1AfhI~
	
                                                                   //~1AfoI~
            int Playmode;                                          //~1AfoR~
                                                                   //~1AfoI~
                                                                   //~1AfoI~
    public String strTimesettings="";                              //~v1B6R~
	public int Komi=DEFAULT_KOMI;                                  //~v1B6R~
    public int Handicap;                                           //~1Ah0R~
    public int BoardSize;                                          //~1Ah0R~
//    public int Rules=DEFAULT_RULE;                               //~1Ah0R~
    public int MyColor;                                            //~1Ah0R~
	public GtpClient gtpclient;                                    //~v1B6I~
	public GuiGtpClient guigtpclient;                              //~v1B6I~
    public boolean Verbose;                                        //~1Ah0R~
	public boolean swPassed;                                       //~v1B6I~
	public boolean useBook;                                        //~v1BbI~
//            boolean Pondering;                                   //~1Ah0R~
//            CheckBox  cbPondering;                               //~1Ah0R~
//    private Button btnOptionChange;                              //~1Ah0R~
            String cmdOption="";                                   //~1AfoR~
            boolean swShowOption;                                  //~1AfoI~
            String showOption="";                                  //~1AfoI~
//    private static Menu Smenu;                                   //~1Ah0R~
      private GtpGoParm gtpGoParm;                                 //~1Ah0R~
      private MainFrame PF;                                        //~1Ah0I~
      private ArrayList<String> subcmdList;                        //~1Ah0I~
      private boolean swReload;                                    //~1Ah0I~
      private Notes reloadNotes;                                    //~1Ah0I~
	public GtpFrame(MainFrame f,GtpGoParm Pparm)                //~1Ah0R~
    {                                                              //~1AfcI~
//        this(f,LAYOUT);                                          //~1Ah0M~
//      super(f);                                                  //~1Ah0I~//~@@@9R~
        super(0/*no layout*/,"GtpFrame");                          //~@@@9I~
		PF=f;	//mainframe                                        //~1Ah0I~
    	gtpGoParm=Pparm;                                           //~1Ah0I~
        MyColor=Pparm.color;                                       //~1Ah0I~
        BoardSize=Pparm.size;                                      //~1Ah0I~
//      Verbose=Pparm.verbose;                                     //~1Ah0R~
        Handicap=Pparm.handicap;                                   //~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame:MyColor="+MyColor);     //~1Ah0I~
    }                                                              //~1AfcI~
//**************************************************************   //~1Ah0I~
//* init valiable after reloaded file                              //~1Ah0I~
//**************************************************************   //~1Ah0I~
	public void initReload()                                       //~1Ah0I~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame:initReload");           //~1Ah0I~
//      super(0/*no layout*/,"GtpFrame");                          //~1Ah0I~
//  	PF=f;	//mainframe                                        //~1Ah0I~
//  	gtpGoParm=Pparm;                                           //~1Ah0I~
		swReload=true;                                             //~1Ah0I~
        MyColor=gtpGoParm.color;                                       //~1Ah0I~
        BoardSize=gtpGoParm.size;                                      //~1Ah0I~
        Handicap=gtpGoParm.handicap;                                   //~1Ah0I~
    }                                                              //~1Ah0I~
//**************************************************************   //~v1B6I~
	GTPConnector C;
	GtpGoFrame F;
	GtpFrame Co=this;                                              //~1Ah0R~
//          boolean play()                                         //~1Ah0R~
    public  boolean play(String Pcmd,String Pcmdlineparm,ArrayList<String> PsubcmdList)//~1Ah0R~
    {                                                              //~v1B6I~
//  	String enginarg="";                                        //~1Ah0R~
//  	String text=getCommandParm();                              //~1Ah0R~
    	String text=Pcmd;                                          //~1Ah0I~
        subcmdList=PsubcmdList;                                    //~1Ah0I~
        File f=new File(text);                                        //~v1B6I~
        if (!f.exists())                                           //~v1B6I~
        {                                                          //~v1B6R~
	    	int flag=Alert.BUTTON_CLOSE|Alert.SHOW_DIALOG;       //~1604I~//~1Ah0R~
        	Alert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~1Ah0R~
        			AG.resource.getString(R.string.GtpErr_Pgm_NotFound)+":\n"+text,flag);//~v1B6R~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6I~
        File pathFile=new File(f.getParent());                     //~1Ah0R~
        if (Dump.Y) Dump.println("GtpFrame:play pgm path="+pathFile.getName());//~1Ah0R~
        text+=" "+Pcmdlineparm;                                    //~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame:play text="+text); //~1AfoR~//~1Ah0R~
//      C=new GTPConnector(text,this);                             //~1Ah0R~
        C=new GTPConnector(text,this,pathFile);                    //~1Ah0I~
        C.setGTPInterface(this);                                   //~v1B6I~
        try                                                        //~v1B6I~
        {                                                          //~v1B6R~
            setOk(new OkAdapter ()                                 //~v1B6R~
                {   public void gotOk () //callback from C.connect //~v1B6R~
                    {                                              //~v1B6R~
                        if (Dump.Y) Dump.println("GtpFrame:gotOk");//~v1B6I~//~1Ah0R~
                        initializedGoGui();                        //~v1B6I~
                    }                                              //~v1B6R~
                }                                                  //~v1B6R~
            );                                                     //~v1B6R~
//          C.connect();                                           //~v1B6I~//~1Ah0R~
            C.connect(subcmdList);                                 //~1Ah0I~
        }                                                          //~v1B6I~
        catch (Exception e)                                        //~v1B6I~
        {                                                          //~v1B6R~
            Dump.println(e,"Play Exception");                      //~v1B6R~
	    	int flag=Alert.BUTTON_CLOSE|Alert.SHOW_DIALOG;         //~1Ah0R~
        	Alert.simpleAlertDialog(null/*callback*/,AG.resource.getString(TITLE),//~1Ah0R~
					AG.resource.getString(R.string.GtpErr_Pgm_Failed)+e.toString(),flag);//~v1B6I~
            return false;                                          //~v1B6R~
        }                                                          //~v1B6I~
        return true;                                               //~v1B6I~
    }//play                                                        //~v1B6I~
//***************************************************************************//~v1B6I~
            void initializedGoGui()                                //~v1C1I~
    {                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GtpFrame:gotOk");           //~v1B6I~//~1Ah0R~
        guigtpclient=C.gogui.m_gtp;                                //~v1B6I~
        gtpclient=guigtpclient.m_gtp;                              //~v1B6R~
        F=new GtpGoFrame(PF,this,TITLE,gtpGoParm);                 //~1Ah0R~
        if (Dump.Y) Dump.println("GtpFrame:after new GTPGoFrame");//~v1B6I~//~1Ah0R~
        if (reloadNotes!=null)                                     //~1Ah0R~
        {                                                          //~1Ah0I~
//      	reloadNotes=gtpGoParm.reloadNotes; 	//of GoFrame       //~1Ah0R~
        	F.loadFileNotes(reloadNotes); //TGF remaining time,tray,piece//~1A24R~//~1Ah0R~
        }                                                          //~1Ah0I~
//      F.setVisible(true);                                        //~v1B6I~
//        GoColor gc=null;                                           //~v1B6I~//~1Ah0R~
////      if (Handicap>1)                                            //~v1B6I~//~1Ah0R~
//        if (Handicap!=0)                                         //~1Ah0R~
//        {                                                          //~v1B6I~//~1Ah0R~
////          if (MyColor==GTPConnector.BLACK) //computer:White      //~v1B6R~//~1Ah0R~
//            if (MyColor==Board.COLOR_BLACK) //computer:White     //~1Ah0R~
//                gc=GoColor.BLACK;     //first stone=computer:White;//~v1B6I~//~1Ah0R~
//        }                                                          //~v1B6I~//~1Ah0R~
//        else    //no handicap                                      //~v1B6I~//~1Ah0R~
//        {                                                          //~v1B6I~//~1Ah0R~
////          if (MyColor==GTPConnector.WHITE) //computer:Black      //~v1B6I~//~1Ah0R~
//            if (MyColor==Board.COLOR_WHITE) //computer:Black     //~1Ah0R~
//                gc=GoColor.WHITE;     //first stone=computer:BLACK //~v1B6I~//~1Ah0R~
//        }                                                          //~v1B6I~//~1Ah0R~
        initGtpCommand();                                        //~v1C1I~//~1Ah0R~
        Block=false;                                               //~1Ah0I~//~@@@9M~//~1Ah0M~
//      F.start();	//start timer                                  //~1Ah0R~
    }//initializedGoGui                                            //~v1B6I~
//***************************************************************************//~v1C1I~
    protected void initGtpCommand()                     //~v1C1I~  //~1Ah0R~
    {                                                              //~v1C1I~
//        if (Pgc!=null)                                             //~v1C1I~
//            C.gogui.generateMove(false/*singleMove*/,Pgc/*human color*/);//~v1C1I~
//      if ((gtpGoParm.gameoptions & GameQuestion.GAMEOPT_COMPUTERFIRST)!=0)//~1Ah0R~
//      	F.B.requestComputerMove(this);	//to Canvas,callback GF.requestComputerMove//~1Ah0R~
        F.B.requestStartTimer(this);	                           //~1Ah0I~
    }//initializedGoGui                                            //~v1C1I~
    //**********************************************************   //~1Ah0I~
    private void requestComputerMove()                             //~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y)  Dump.println("GtpFrame:requestComputerMove"); //~1Ah0I~
        int color=(MyColor==Board.COLOR_BLACK) ? GTPConnector.BLACK : GTPConnector.WHITE;//~1Ah0I~
		try                                                        //~1Ah0I~
        {                                                          //~1Ah0M~
    		C.move(color,GoGui.CMD_COMPUTER_MOVE,false/*drop*/);	//GTPConenctor//~1Ah0R~
        }	                                                       //~1Ah0M~
		catch (Exception e)                                        //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"GtpFrame:requestComputerMove");        //~1Ah0I~
		}                                                          //~1Ah0I~
    }                                                              //~1Ah0I~
    //**********************************************************   //~1Ah0I~
    public void startTimer()                                       //~1Ah0I~
    {                                                              //~1Ah0I~
        if (Dump.Y)  Dump.println("GtpFrame:startTimer");          //~1Ah0I~
        F.start();	//start timer                                  //~1Ah0I~
		if (swReload)                                              //~1Ah0I~
        {                                                          //~1Ah0I~
        	if (Dump.Y) Dump.println("GtpFrame:startTimer MyColor="+MyColor+",Notes last color="+reloadNotes.color);//~1Ah0I~
        	if (MyColor!=reloadNotes.color)                        //~1Ah0I~
        		requestComputerMove();                             //~1Ah0I~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
        	if ((gtpGoParm.gameoptions & GameQuestion.GAMEOPT_COMPUTERFIRST)!=0)//~1Ah0R~
        		requestComputerMove();	                           //~1Ah0R~
        }                                                          //~1Ah0I~
    }                                                              //~1Ah0I~
    //**********************************************************   //~1Ah0I~
	public int getColor ()
	{	return MyColor;
	}
    public int getBoardSize ()                                     //~1Ah0R~
    {                                                              //~1Ah0R~
    	return gtpGoParm.size;                                     //~1Ah0I~
    }                                                              //~1Ah0R~
//    //********************************************************** //~1Ah0R~
//    //*Computer move                                             //~1Ah0R~
//    //*from GtpConnector:callbackGotMoved on canvas thread       //~1Ah0R~
//    //********************************************************** //~1Ah0R~
//    public void gotMove (int color, int pos,int Pposfrom,int Ppiece,boolean Pdrop)//~1AgbI~//~1Ah0R~
//    {                                                            //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame gotMove: by computer move parm color="+color+",pos="+pos+",old="+Pposfrom+",piece="+Ppiece);//~1Ah0R~
//        yourMove(); //sound                                        //~@@@9I~//~1Ah0R~
//        Point p=GtpMove.pos2Point(pos);                          //~1Ah0R~
//        int i=p.x;                                               //~1Ah0R~
//        int j=p.y;                                               //~1Ah0R~
//        p=GtpMove.pos2Point(Pposfrom);                           //~1Ah0R~
//        int ifrom=p.x;                                           //~1Ah0R~
//        int jfrom=p.y;                                           //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame gotMove:i="+i+",j="+j+",piece="+Ppiece+",from=("+ifrom+","+jfrom+")");//~v106I~//~1AgbR~//~1Ah0R~
//        if (!Pdrop)                                               //~@@@9I~//~1Ah0R~
//        {                                                          //~@@@9I~//~1Ah0R~
//            selectPiece(color,ifrom,jfrom);                          //~@@@9I~//~1Ah2R~//~1Ah0R~
//        }                                                          //~@@@9I~//~1Ah0R~
//////        if (i<0 || j<0) F.gotPass(color);                        //~1Ah0R~//~@@@9R~//~1Ah0R~
//////        chkpassdoubled(false/*not human*/,(i<0 || j<0));    //chk both passed//~v1B6I~//~1Ah0R~//~@@@9R~//~1Ah0R~
////        if (color==MyColor)                                        //~1AgbI~//~@@@9R~//~1Ah0R~
//////          F.gotSet(color,i,BoardSize-j-1,ifrom,BoardSize-jfrom-1,Ppiece);//~1AgbI~//~1Ah0R~//~@@@9R~//~1Ah0R~
//////          F.gotSet(color,i,BoardSize-j-1,Ppiece);  //GtpGoFrame.gotSet//~1Ah0R~//~@@@9R~//~1Ah0R~
////            F.gotSet(color,i,j,Ppiece);  //GtpGoFrame.gotSet       //~1Ah0I~//~@@@9R~//~1Ah0R~
////        else                                                       //~1AgbR~//~@@@9R~//~1Ah0R~
////        {                                                          //~1Ah0I~//~@@@9R~//~1Ah0R~
//////          F.gotMove(color,i,BoardSize-j-1,Ppiece);//~1AgbI~      //~1Ah0R~//~@@@9R~//~1Ah0R~
////            F.gotMove(color,i,j,Ppiece,ifrom,jfrom,Pdrop);         //~1Ah0R~//~@@@9R~//~1Ah0R~
////        }                                                          //~1Ah0I~//~@@@9R~//~1Ah0R~
////        Block=false;                                               //~1Ah0I~//~@@@9R~//~1Ah0R~
//        if (color==Board.COLOR_BLACK)                              //~@@@9I~//~1Ah0R~
//            F.black(i,j,Ppiece,Pdrop);  //GtpGoFrame               //~@@@9I~//~1Ah0R~
//        else                                                       //~@@@9I~//~1Ah0R~
//            F.white(i,j,Ppiece,Pdrop);                              //~@@@9I~//~1Ah0R~
//    }                                                            //~1Ah0R~
	OkAdapter Ok=null;
	public void setOk (OkAdapter ok)                               //~1513R~
    {                                                              //~1513I~
		Ok=ok;                                                     //~1513I~
    	if (Dump.Y) Dump.println("GtpFrame setOk:"+(Ok==null?"null":Ok.toString()));//~1513R~//~1Ah0R~
    }                                                              //~1513I~
	public void gotOk ()
	{                                                              //~1513R~
    	if (Dump.Y) Dump.println("GtpFrame gotOk:"+(Ok==null?"null":Ok.toString()));//~1513I~//~1Ah0R~
	 	if (Ok!=null) Ok.gotOk();                                  //~1513I~
		Ok=null;
	}
	public void gotAnswer (int a)
	{
	}
	
	public int I,J;
	public int Piece;                                              //~1Ah0R~
//********************************************************         //~v1B6R~
//*from GTPGoFrame:moveset                                         //~v1B6I~
//*player set stone                                                //~v1B6I~
//********************************************************         //~v1B6I~
//  public void moveset (int i, int j)                             //~1Ah0R~
    public void moveset (int i, int j,final int Ppiece,boolean Pdrop,int Poldi,int Poldj)//~1Ah0R~
	{                                                              //~v1B6R~
        if (Dump.Y) Dump.println("GtpFrame.moveset i="+i+",j="+j+",piece="+Ppiece+",drop="+Pdrop+",oldi="+Poldi+",Poldj="+j);//~1Ah0R~
    	if (isProgramDead())                                         //~1Ah0I~
        	return;                                                //~1Ah0I~
		if (Block)                                                 //~1Ah0I~
			return;                                                //~1Ah0I~
//        Block=true;                                                //~1Ah0I~//~@@@9R~
//  	int pos=(BoardSize-j-1)*BoardSize+i+1;                     //~v1B6I~//~1Ah0R~
		I=i; J=j;
        Piece=Ppiece;  //for gotMove;                              //~1Ah0I~
//      yourMove();//piecedown                                     //~@@@9I~//~1Ah0R~
		try
		{                                                          //~1Ah0R~
            setOk(new OkAdapter ()                                 //~1Ah0R~
            	{   public void gotOk ()                           //~1Ah0R~
//                //*when sendPlay response was gotten             //~1Ah0R~
//                  {F.gotMove(MyColor,I,J);                       //~1Ah0R~
                    {                                              //~1Ah0R~
                        if (Dump.Y) Dump.println("GtpFrame:moveset.gotOk i="+I+",j="+J+",piece="+Ppiece);//~1Ah0R~
//                      F.gotMove(MyColor,I,J,Ppiece,-1/*no need to set iselected*/,-1,false);        //GtpGoFrame set human move//~1Ah0R~//~@@@9R~
//                      F.gotMove(MyColor,I,J,Ppiece);        //GtpGoFrame set human move//~@@@9R~//~1Ah2R~
                        GtpMove m=new GtpMove(GtpMove.GTPMOVE_HUMAN_MOVE,MyColor,Ppiece,I,J,-1/*oldx id of response of human move*/,-1/*oldy*/,false/*drop*/);//~1Ah2R~//+1Ah0R~
                        m.status=GtpMove.GTPMOVE_STATUS_SENDPLAY_RESPONSE; //+1Ah0I~
                        F.requestCallback(m);	//excute gotMove on Canvas thread//~1Ah2I~
                        if (Dump.Y) Dump.println("GtpFrame:moveset.gotOk end i="+I+",j="+J+",piece="+Ppiece);//~@@@9I~
                    }                                              //~1Ah0R~
                }                                                  //~1Ah0R~
            );                                                     //~1Ah0R~
//        if (I<0)  //pass                                         //~v1B6I~//~1Ah0R~
//  		C.move(MyColor,0);                                     //~1Ah0R~
//  		C.move(MyColor,0,0,false,0); //to GTPConnector sendPlay//~1Ah0R~
//        else                                                     //~v1B6I~//~1Ah0R~
//  		C.move(MyColor,pos);                                   //~1Ah0R~
//          chkpassdoubled(true/*human*/,I<0);    //chk both passed//~v1B6I~//~1Ah0R~
            String moveStr;                                        //~1Ah0I~
        	if (MyColor==Board.COLOR_WHITE)  //your are front of board, bonanz Black is front//~1Ah0I~
            {                                                      //~1Ah0I~
            	int ii=BoardSize-i-1;                              //~1Ah0I~
            	int jj=BoardSize-j-1;                              //~1Ah0I~
                int iio=0; int jjo=0;                                  //~1Ah0I~
            	if (!Pdrop)                                        //~1Ah0I~
                {                                                  //~1Ah0I~
					iio=BoardSize+-Poldi-1;                        //~1Ah0I~
					jjo=BoardSize+-Poldj-1;                        //~1Ah0I~
                }                                                  //~1Ah0I~
				moveStr=NotesFmtCsa.getMoveString(Ppiece,ii,jj,iio,jjo,Pdrop);//~1Ah0I~
            }                                                      //~1Ah0I~
            else                                                   //~1Ah0I~
				moveStr=NotesFmtCsa.getMoveString(Ppiece,i,j,Poldi,Poldj,Pdrop);//~1Ah0I~
            int color=(MyColor==Board.COLOR_BLACK) ? GTPConnector.BLACK : GTPConnector.WHITE;//~1Ah0I~
            if (Dump.X) Dump.println("X:GtpFrame;moveset after color="+color+",cmd="+moveStr);//~1Ah0I~
    		C.move(color,moveStr,Pdrop);	//GTPConenctor         //~1Ah0I~
            if (Dump.Y) Dump.println("GtpFrame;moveset after color="+color+",cmd="+moveStr);//~1Ah0I~
//          gtpclient.getResponse();  //throw exception if err     //~v1B6R~//~1Ah0R~
            gotOk();    //OkAdapter.gotOk() schedule GGF.gotMOve after moveset returned//~1Ah2R~
//          GoColor gc=(MyColor==GTPConnector.BLACK ? GoColor.BLACK : GoColor.WHITE);//~v1B6I~//~1Ah0R~
//          C.gogui.generateMove(false/*singleMove*/,gc);           //~v1B6I~//~1Ah0R~
//          resetGameover();                                       //~v1B6I~//~1Ah0R~
            if (Dump.Y) Dump.println("GF:moveset return");         //~@@@9I~
		}
		catch (Exception e)                                        //~v1B6R~
        {                                                          //~v1B6I~
            Dump.println(e,"GtpFrame:moveset");               //~v1B6I~//~1Ah0R~
		}                                                          //~v1B6I~
	}
//*****************************************************************************************************//~1Ah0I~
	public String sentCmd;                                         //~1Ah0I~
    public void sendCmd(int Pcmdtype,String Pcmd,boolean Pwait)    //~1Ah0R~
	{                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame.sendcmd cmd="+Pcmd);    //~1Ah0I~
    	if (isProgramDead())                                       //~1Ah0I~
        	return;                                                //~1Ah0I~
        sentCmd=Pcmd;                                              //~1Ah0I~
		try                                                        //~1Ah0I~
		{                                                          //~1Ah0I~
        	if (Pwait)                                             //~1Ah0I~
                setOk(new OkAdapter ()                             //~1Ah0R~
                    {   public void gotOk ()                       //~1Ah0R~
                        {                                          //~1Ah0R~
                            if (Dump.Y) Dump.println("GtpFrame:sendcmd.gotOk cmd="+sentCmd);//~1Ah0R~
                        }                                          //~1Ah0R~
                    }                                              //~1Ah0R~
                );                                                 //~1Ah0R~
//            Block=true;                                            //~1Ah0I~//~@@@9R~
    		C.sendCmd(Pcmdtype,Pcmd,Pwait);	//GTPConenctor         //~1Ah0R~
            if (Dump.Y) Dump.println("GtpFrame:sendCmd after cmd="+Pcmd);//~1Ah0I~
            if (Pwait)                                             //~1Ah0I~
	            gotOk();    //OkAdapter.gotOk():F.gotMove,         //~1Ah0R~
		}                                                          //~1Ah0I~
		catch (Exception e)                                        //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"GtpFrame:moveset");                    //~1Ah0I~
		}                                                          //~1Ah0I~
	}                                                              //~1Ah0I~
    //**********************************************************   //~1Ah0I~
    //*rc:err(errmsg dialog)                                       //~1Ah0I~
    //**********************************************************   //~1Ah0I~
    public boolean cmdResponseGotten(int Pcmdtype,String Pmsg,boolean Perr)//~1Ah0R~
	{                                                              //~1Ah0I~
    	boolean swmsg=false;                                       //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpFrame:sendCmdResponseGotten err="+Perr+",cmdtype="+Pcmdtype+",msg="+Pmsg);//~1Ah0R~
//        Block=false;                                               //~1Ah0I~//~@@@9R~
        if (Perr)                                                  //~1Ah0I~
        {                                                          //~1Ah0I~
		    cmdResponseGottenError(Pcmdtype,Pmsg);           //~1Ah0I~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
            if (Pcmdtype==GoGui.CMDTYPE_RESIGN)  
            {                                                      //~1Ah0I~
            	if (Pmsg.indexOf(GoGui.PROMPT_RESIGNED)>=0         //~1Ah0R~
                || Pmsg.indexOf(GoGui.PROMPT_MATED)>=0)	//case of reload Mated file//~1Ah0R~
                {                                                  //~1Ah0I~
             	    humanResigned(Pmsg);                           //~1Ah0R~
                    swmsg=true;	                                   //~1Ah0I~
                }                                                  //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
	    if (swmsg)                                                 //~1Ah0I~
        {                                                          //~1Ah0I~
        	String s=AG.resource.getString(R.string.InfoCmdResponseGotten,Pmsg);//~1Ah0I~
        	AView.showToast(s);                                    //~1Ah0I~
        }                                                          //~1Ah0I~
        return false;	//not err                                  //~1Ah0I~
	}                                                              //~1Ah0I~
    //**********************************************************   //~1Ah0I~
    public boolean cmdResponseGottenError(int Pcmdtype,String Pmsg)//~1Ah0I~
	{                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpFrame:sendCmdResponseGottenError cmdtype="+Pcmdtype+",msg="+Pmsg);//~1Ah0I~
        if (Pcmdtype==GoGui.CMDTYPE_MOVE)                          //~1Ah0I~
        {                                                          //~1Ah0I~
        	if (Pmsg.startsWith(GoGui.ERR_COMPUTER_TIMEOUT))       //~1Ah0R~
            {                                                      //~1Ah0I~
		        int color=F.maincolor();                           //~1Ah0I~
	    		if (Dump.Y) Dump.println("GtpFrame:cmdResponseGottenError winner="+color);//~1Ah0I~
			    F.gameoverMessage(color/*winner*/,3/*timeout*/);   //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
        return false;	//not err                                  //~1Ah0I~
	}                                                              //~1Ah0I~
    //**********************************************************   //~1Ah0I~
    private boolean humanResigned(String Pmsg)                     //~1Ah0I~
    {                                                              //~1Ah0I~
        int color=-F.maincolor();                                  //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpFrame humanResigned winner="+color);//~1Ah0I~
	    F.gameoverMessage(color/*winner*/,4/*resign*/);            //~1Ah0I~
        return true;                                               //~1Ah0I~
    }                                                              //~1Ah0I~
////*************************************************************************//~v1B6R~//~@@@9R~
////*from GTPGoFrame:notepass()                                      //~v1B6I~//~@@@9R~
////*************************************************************************//~v1B6I~//~@@@9R~
//    public void pass ()                                          //~@@@9R~
//    {                                                              //~v1B6R~//~@@@9R~
////      moveset(-1,-1);                                            //~1Ah0R~//~@@@9R~
//        if (Dump.Y) Dump.println("GtpFrame:pass logical err");     //~1Ah0R~//~@@@9R~
//    }                                                            //~@@@9R~
//                                                                 //~@@@9R~
//    public boolean undo ()                                         //~v1B6R~//~@@@9R~
//    {                                                              //~v1B6R~//~@@@9R~
//        boolean rc=false;                                          //~v1B6I~//~@@@9R~
//        try                                                        //~v1B6I~//~@@@9R~
//        {                                                          //~v1B6R~//~@@@9R~
//            rc=C.takeback(2);                                      //~v1B6I~//~@@@9R~
//        }                                                        //~@@@9R~
//        catch (GtpError e)                                 //~v1B6I~//~@@@9R~
//        {                                                          //~v1B6I~//~@@@9R~
//            Dump.println(e,"GtpFrame:undo");                  //~v1B6I~//~1Ah0R~//~@@@9R~
//            AView.showToastLong(R.string.GtpErr_Undo_Failed,"\n"+e.toString());//~1Ah0R~//~@@@9R~
//        }                                                          //~v1B6I~//~@@@9R~
//        catch (Exception e)                                        //~v1B6R~//~@@@9R~
//        {                                                          //~v1B6I~//~@@@9R~
//            Dump.println(e,"GtpFrame:undo");                  //~v1B6I~//~1Ah0R~//~@@@9R~
//        }                                                          //~v1B6I~//~@@@9R~
//        return rc;                                                 //~v1B6I~//~@@@9R~
//    }                                                            //~@@@9R~
//                                                                 //~@@@9R~
//************************************************************************//~@@@9I~
	public void stoprobot()                                        //~@@@9I~
    {                                                              //~v108I~//~@@@9M~
        if (Dump.Y) Dump.println("GtpFrame doclose");//~1AbMI~     //~@@@9M~
	    sendCmd(GoGui.CMDTYPE_QUIT,SENDCMD_QUIT,false/*nowait*/);  //~@@@9M~
		super.doclose();                                           //~@@@9M~
	}                                                              //~@@@9M~
//**************************************************************   //~@@@9M~
//    public void doclose ()                                       //~@@@9R~
//    {   if (C!=null) C.doclose();                                //~@@@9R~
//    }                                                            //~@@@9R~
	public void doclose ()                                         //~@@@9I~
	{                                                              //~@@@9R~
    	if (Dump.Y) Dump.println("GtpFrame:doclose");                   //~1A29I~//~@@@9R~
		stoprobot();                                               //~@@@9I~
	}                                                              //~@@@9I~
//************************************************************************//~v1B6I~
//*bonanza resigned                                                  //~v1B6I~//~1Ah0R~
//************************************************************************//~v1B6I~
	public void resignrequested()                                           //~v1B6I~//~1Ah0R~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GtpFrame:resignrequested");          //~v1B6I~//~1Ah0R~
		//@@@@Fixme                                                //~1Ah0I~
	}                                                              //~v1B6I~
//************************************************************************//~v1B6I~
//*human resign by button push                                     //~1Ah0R~
//*from GtpGoFrame:doAction                                        //~1Ah0I~
//************************************************************************//~v1B6I~
	public void resign()                 //~v1B6I~                 //~1Ah0R~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GtpFrame:resign by button");     //~v1B6I~//~1Ah0R~
//        if (Block)                                                 //~1Ah0I~//~@@@9R~
//        {                                                          //~1Ah0I~//~@@@9R~
//            AView.showToast(R.string.ErrOnComputersTurn);          //~1Ah0I~//~@@@9R~
//            return;                                                //~1Ah0I~//~@@@9R~
//        }                                                          //~1Ah0I~//~@@@9R~
		new EndGameQuestion(this,AG.resource.getString(R.string.EndgameResign),AG.resource.getString(R.string.Title_EndGameQuestion),true/*resign*/,false/*suspend*/);//~@@@2I~//~1Ah0R~
	}                                                              //~@@@2I~//~1Ah0I~
//***Ok for EndgameQuestion(Resign)(requester) **************      //~@@@2I~//~1Ah0I~
	public void doresign ()                                        //+@@@2I~                                                              //~@@@2I~//~1Ah0I~
	{                                                              //~1Ah0R~
		if (Dump.Y) Dump.println("GtpFrame:doresign Block="+Block);//~1Ah0I~
		if (Block) return;                                         //~1Ah0I~
        Block=true;                                                //~@@@9I~
        sendCmd(GoGui.CMDTYPE_RESIGN,SENDCMD_RESIGN,false/*no wait response*/);//~1Ah0R~
    }                                                              //~@@@2R~//~1Ah0I~
//    public void suspend()                                        //~1Ah0R~
//    {                                                            //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame:suspend by button");  //~1Ah0R~
//        new EndGameQuestion(this,AG.resource.getString(R.string.Suspend_the_game),AG.resource.getString(R.string.Title_SuspendGame),false/*resign*/,true/*suspend*/);//~1Ah0R~
//    }                                                            //~1Ah0R~
//    public void dosuspend()                                      //~1Ah0R~
//    {                                                            //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame:dosuspend Block="+Block);//~1Ah0R~
//        Block=true;                                              //~1Ah0R~
//        if (Dump.Y) Dump.println("doresign suspend");            //~1Ah0R~
//        F.saveGame(null);  //TGF @@@@FIXME                       //~1Ah0R~
//    }                                                            //~1Ah0R~
//************************************************************************//~@@@9I~
//*computer resigned                                               //~@@@9I~
//************************************************************************//~@@@9I~
	public void resignrequested(String Ppartnername)               //~@@@2R~//~@@@9I~
	{                                                              //~@@@2M~//~@@@9I~
        //@@@FIXME                                                 //~@@@9I~
//        doendgame();    //no question but msg only then            //~@@@2R~//~@@@9I~
//	 	new Message(this,Ppartnername+" "+AG.resource.getString(R.string.Msg_Partner_Resigned));//~@@@2R~//~@@@9I~
	}                                                              //~@@@2M~//~@@@9I~
//************************************************************************//~v1B6I~
//* from GtpConnection                                             //~1Ah0R~
//************************************************************************//~v1B6I~
	public void gameovered(int Pstatus)                            //~v1B6I~//~1Ah0R~
	{                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GtpFrame:gameovered status="+Pstatus);//~v1B6I~//~1Ah0R~
		Block=true;                                                //~@@@9I~
        //@@@@FIXME                                                //~1Ah0I~
	}                                                              //~v1B6I~
//************************************************************************//~1Ah0I~
	private boolean isProgramDead()                                //~1Ah0I~
    {                                                              //~1Ah0I~
        boolean rc=C.gogui.isProgramDead();                        //~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame:isProgramDead="+rc);    //~1Ah0I~
        if (rc)                                                    //~1Ah0I~
        	AView.showToast(R.string.ProgramDead);                 //~1Ah0I~
        return rc;                                                 //~1Ah0I~
    }                                                              //~1Ah0I~
//********************************************************         //~@@@@I~//~1Ah0I~
//*from GtpGoFrame:doclose                                     //~@@@@I~//~1Ah0I~
//********************************************************         //~@@@@I~//~1Ah0I~
	public void boardclosed (GtpGoFrame pgf)		               //~1Ah0I~
	{                                                              //~@@@2R~//~1Ah0I~
		doclose();	//stop bonanza                                 //~1Ah0I~
	}                                                              //~1Ah0I~
//*******************************************************************//~@@@2I~//~@@@9M~
//from GtpGoFrame                                              //~@@@2I~//~@@@9M~
//send piece selected                                              //~@@@2I~//~@@@9M~
//*******************************************************************//~@@@2I~//~@@@9M~
	public boolean sendSelected (int Pi, int Pj)                    //~@@@2R~//~@@@9M~
	{	if (Block) return false;                                   //~@@@2I~//~@@@9M~
//  	sendiSelected=Pi; sendjSelected=Pj;                        //~1A37I~//~@@@9M~
    	JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~@@@2I~//~@@@9M~
		return true;                                               //~@@@2I~//~@@@9M~
	}                                                              //~@@@2I~//~@@@9M~
////***************************************************************  //~1A04I~//~@@@9I~//~1Ah0R~
//    private void yourMove()                                        //~v101I~//~@@@9I~//~1Ah0R~
//    {                                                              //~v101I~//~@@@9I~//~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame:yourMove sound stone start"); //~v101I~//~@@@9I~//~1Ah0R~
//        JagoSound.play("piecedown",false/*not change to beep when beeponly option is on*/);//~1A09I~//~@@@9I~//~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame:yourMove sound stone end");   //~v101I~//~@@@9I~//~1Ah0R~
//    }                                                              //~v101I~//~@@@9I~//~1Ah0R~
//*******************************************************************//~1A37I~//~@@@9I~
//    private void selectPiece(int Pcolor,int Pi,int Pj)                    //~1A37I~//~@@@9I~//~1Ah2R~//~1Ah0R~
//    {                                                              //~1A37I~//~@@@9I~//~1Ah0R~
//        if (Dump.Y) Dump.println("GtpFrame:selectPiece at "+Pi+","+Pj+",color="+Pcolor); //~1A37I~//~@@@9I~//~1Ah2R~//~1Ah0R~
//        F.selectPiece(Pcolor,Pi,Pj);   //GtpGoFrame                             //~1A37I~//~@@@9I~//~1Ah2R~//~1Ah0R~
//    }                                                              //~1A37I~//~@@@9I~//~1Ah0R~
    //****************************************                     //~1A23I~//~@@@9I~
	public void sendSuspend()                                      //~1A23I~//~@@@9I~
	{                                                              //~1A23I~//~@@@9I~
//  	out("@@suspendgame");                                      //~1A23I~//~@@@9I~
//  	Block=true;                                                //~1A23I~//~@@@9I~
//      swSuspendRequester=true;                                   //~1A24I~//~@@@9I~
//  	GameQuestion.waitingResponse();                            //~1A24I~//~@@@9I~
        if (Dump.Y) Dump.println("GtpFrame:dosuspend Block="+Block);//~1Ah0I~
        Block=true;                                                //~1Ah0I~
        F.receivedSuspendOK();                               //~1A23R~//~1A24R~//~@@@9I~
	}                                                              //~1A23I~//~@@@9I~
    //****************************************                     //~1Ah0I~
    //*from GTPConnector                                           //~1Ah0I~
    //****************************************                     //~1Ah0I~
	public void setVersion(String Pversion)                        //~1Ah0I~
	{                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame:setVersion ="+Pversion);//~1Ah0I~
        Sversion=Pversion;                                         //~1Ah0I~
	}                                                              //~1Ah0I~
//************************************************************     //~1A24I~//~1Ah0I~
    @Override //FileDialogI   from GtpGameQuestion:reload              //~1A24R~//~1Ah0I~
    public void fileDialogLoaded(CloseDialog Pgq/*GtpGameQuestion*/,Notes Pnotes) //GtpGameQuestion request Reload File//~1A24I~//~1Ah0I~
    {                                                              //~1A24I~//~1Ah0I~
        if (Dump.Y) Dump.println("GtpFrame.fileDialogLoaded");     //~1Ah0I~
    	gtpGoParm.reload(Pnotes);                                  //~1Ah0I~
        reloadNotes=Pnotes;                                        //~1Ah0I~
        initReload();                                              //~1Ah0I~
        String pgm=GtpGameQuestion.getPGM();                       //~1Ah0I~
        String cmdlineparm=GtpGameQuestion.getCmdlineParm();       //~1Ah0I~
        String readsubcmd=GtpGameQuestion.outputPieceLayout(Pnotes);//~1Ah0I~
        if (readsubcmd==null)                                       //~1Ah0I~
        {                                                          //~1Ah0I~
        	AView.showToastLong(R.string.ErrReloadData);           //~1Ah0I~
        	return;                                                 //~1Ah0I~
        }                                                          //~1Ah0I~
        int pos=gtpGoParm.subcmdList.indexOf(GtpGameQuestion.SUBCMD_HANDICAP);//~1Ah0I~
        if (pos>=0)                                                //~1Ah0I~
	        gtpGoParm.subcmdList.remove(pos);  //del previous piecelayout//~1Ah0I~
        gtpGoParm.subcmdList.add(readsubcmd);                      //~1Ah0I~
    	play(pgm,cmdlineparm,gtpGoParm.subcmdList);                //~1Ah0R~
     }                                                              //~1A24I~//~1Ah0I~
	public void fileDialogSaved(String Pnotename){}                //~1A24R~//~1Ah0I~
	public void fileDialogNotSaved(){}                             //~1A24I~//~1Ah0I~
}
