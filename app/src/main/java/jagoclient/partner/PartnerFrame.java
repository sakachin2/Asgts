//*CID://+1AecR~:                                   update#=  218; //+1AecR~
//****************************************************************************//~@@@1I~
//1Aec 2015/07/26 set connection type for Server                   //+1AecI~
//1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//~1Ac0I~
//1AbM 2015/07/03 BT:short sleep for BT disconnet fo exchange @@@@end and @@@@!end//~1AbMI~
//1Abv 2015/06/19 BT:dismiss dialog at boardQuestion for also BT like as 1Abj//~1AbvI~
//1Abj 2015/06/15 NFCBT:dismiss dialog at boardQuestion            //~1AbjI~
//1Ab3 2015/05/04 save file folder may differ for partnermatch     //~1Ab3I~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A85 2015/02/25 close each time partnerframe for IP Connection   //~1A85I~
//1A76 2015/02/23 modal dialog(BoardQuestion) could not be posted if requested Game when IPConnection dialog is showen//~1A76I~
//                Dismiss IPConnection dialog se Current is Frame after BoardQuestion dialog started.//~1A76I~
//                By isCurrentDialog() is off,ActionEvent did not post(countdown latch).//~1A76I~
//                insert sleep.                                    //~1A76I~
//1A75 2015/02/23 (Ahsv:1A6B) is not proper to Asgts (WD/NFC on title)//~1A75I~
//1A6B 2015/02/21 IP game title;identify IP and WifiDirect(WD)     //~1A6BI~
//1A6z 2015/02/20 PartnerFrame NPE at adjourn before board created by tcp delay//~1A6zI~
//1A6y 2015/02/20 dismiss ipconnection dialog when boardquestiondialog up by opponent game requested//~1A6yI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6k 2015/02/15 re-open IPConnection/BTConnection dialog when diconnected when dislog is opened.//~1A53I~//~1A6kI~
//1A48 2014/11/01 SayDialig title;missing partnername for game requester//~1A48I~
//1A39 2013/04/22 Over 1A37,out dummy response for !move           //~1A39I~
//                @@move b-->,<--@@!move b,@@!!move b-->,<--@@move w,...//~1A39I~
//1A38 2013/04/22 (BUG)When response(@@!move) delayed,accept next select.//~1A38I~
//                Then move confuse to move to old dest from new selected.//~1A38I~
//1A37 2013/04/20 avoid TCP delay bynagle algolism(witre-write-read is bad)//~1A37I~
//                So no send @@select                              //~1A37I~
//1A2k 2013/04/03 (Bug)when partner closed after resign+close adjourn NPE by PGF=null//~1A2eI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A2b 2013/03/27 In time to save(after resign and before close), game request disappears(no response)//~1A2bI~
//1A28 2013/03/25 when received adjourn,not close boad for availability to save game//~1A28I~
//1A25 2013/03/25 StringParser:consideration quotation             //~1A25I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A23 2013/03/23 File Dialog on PartnerGoFrame                    //~1A23I~
//1A0g 2013/03/06 judge checkmate                                  //~1A0gI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~1A0eI~
//1A0c 2013/03/05 mach info in title                               //~1A0cI~
//1A09 2013/03/03 use not stone but piecedown at yourMove()        //~1A09I~
//                yourMove is not called for LGF, nop for PGF      //~1A09I~
//                pieceMoved call piecedown for LGF,no sound for PGF//~1A09I~
//1A06 2013/03/02 remaining ExtraTime was accounted.               //~1A06I~
//1A04 2013/03/01 after timeout,setstone ignored by Block=true remains(new PGF under one PF)//~1A04I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//101h 2013/02/09 TcpNoDelay for socket                            //~v101I~
//1081:121213 partner-match:when server requested endgame,Block=true remains and//~v108I~
//            on next match on same connection(same PartnerFrame),client setstone is blocked//~v108I~
//            (                                                    //~v108I~
//            endgame by interrupt @@endgame set Block=true after call EndGameQuestion//~v108I~
//            EndGameQuestion call doendgame/declineendgame(set Block=false)//~v108I~
//            modal dialog is protected to return before reply,so results Block=true)//~v108I~
//            )                                                    //~v108I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//*@@@1 20110430 FunctionKey support                               //~@@@1I~
//****************************************************************************//~@@@1I~
package jagoclient.partner;

import jagoclient.CloseConnection;
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.board.ConnectedBoard;
import jagoclient.board.GoFrame;
import jagoclient.board.Notes;
import jagoclient.board.NotesTree;
import jagoclient.dialogs.Message;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.CloseFrame;
import jagoclient.partner.GameQuestion;
import jagoclient.sound.JagoSound;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.provider.ContactsContract.CommonDataKinds.Note;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.ProgDlg;                                          //~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.Utils;
import com.Asgts.awt.Component;
import com.Asgts.awt.FileDialogI;

import rene.util.parser.StringParser;
import wifidirect.DialogNFC;
import wifidirect.DialogNFCBT;

//class PartnerMove                                                //~@@@2R~
//{   public String Type;                                          //~@@@2R~
//    public int P1,P2,P3,P4,P5,P6;                                //~@@@2R~
//    public PartnerMove (String type, int p1, int p2, int p3, int p4, int p5, int p6)//~@@@2R~
//    {   Type=type; P1=p1; P2=p2; P3=p3; P4=p4; P5=p5; P6=p6;     //~@@@2R~
//    }                                                            //~@@@2R~
//    public PartnerMove (String type, int p1, int p2, int p3, int p4)//~@@@2R~
//    {   Type=type; P1=p1; P2=p2; P3=p3; P4=p4;                   //~@@@2R~
//    }                                                            //~@@@2R~
//}                                                                //~@@@2R~

/**
The partner frame contains a simple chat dialog and a button
to start a game or restore an old game. This class contains an
interpreters for the partner commands.
*/

public class PartnerFrame extends CloseFrame
//    implements KeyListener                                         //~@@@1I~//~@@@2R~
//{	BufferedReader In;                                             //~v107R~
      implements FileDialogI                                       //~1A24I~
{	                                                               //~v107I~
    private static final String CONTYPE_PREFIX=";";                //~1A6BI~
	public static final String CONN_TITLE_IP="IP ";                //~1A8iI~
	public static final String CONN_TITLE_WD="WD ";                //~1A8iI~
	public static final String CONN_TITLE_BT="BT ";                //~1A8iI~
	public static final String CONN_TITLE_NFC="NFC ";              //~1A8iI~
	public static final String CONN_TITLE_NFC_WD="NFC-WD ";        //+1AecI~
	public static final String CONN_TITLE_NFC_BT="NFC-BT ";        //+1AecI~
	protected BufferedReader In;                                   //~v107I~
  	protected PrintWriter Out;                                      //~v107I~
	Socket Server;
	public PartnerThread PT;                                    //~v107I~//~@@@2R~
	public PartnerGoFrame PGF;
	boolean Serving;
	boolean Block;
	String Dir;
  public                                                           //~1A8iI~
    String PartnerName;                                            //~@@@2I~
    boolean GameRequester;                                         //~@@@2R~
    protected boolean BTConnection;                                          //~1A0cI~
    private Notes loadedNotes,receivedNotes;                                           //~1A24R~//~1A23R~
    private int sendiSelected,sendjSelected;                       //~1A37I~
    public boolean swSuspendRequester;                                     //~1A24I~
	public String myName="";                        //~v1D3R~      //~1A8iI~
    public int connectionType;                                     //~1A6BI~
    private String titleName;                                      //~1A8iI~
    private String suspendedFilename;                              //~1Ab3I~
                                                                   //~1A6BI~
	public PartnerFrame (String name, boolean serving)
//  {   super(name);                                               //~@@@2R~
    {                                                              //~@@@2I~
       super(0/*layouid,have no view*/,name);                      //~@@@2I~
		if (Dump.Y) Dump.println("jagoclient:@@PartnerFrame@@="+this.toString());   //~@@@2I~//~v1A0R~
		Serving=serving;
		PGF=null;
		Block=false;
		Dir="";
//        Moves=new ListClass();                                   //~@@@2R~
//        seticon("iconn.gif");                                    //~@@@2R~
//        addKeyListener(this);                                      //~@@@1I~//~@@@2R~
//      AG.aPartnerFrameIP=this;                                   //~@@@2I~//~1A85R~
        AG.aPartnerFrame=this;                                     //~1A85I~
        AG.aPartnerFrameIP=null;                                   //~1A85I~
        titleName=name;                                            //~1A8iI~
	}
	public PartnerFrame (String name, boolean serving,int Pconnectiontype)//~1A6BI~
    {                                                              //~1A6BI~
        this(name,serving);                                        //~1A6BI~
		if (Dump.Y) Dump.println("jagoclient:@@PartnerFrame@@");   //~1A6BI~
		connectionType=Pconnectiontype;                            //~1A6BI~
	}                                                              //~1A6BI~
	public PartnerFrame (String name, boolean serving,String Ptitleprefix)//~1A8iI~
	{                                                              //~1A8iI~
		this(name,serving);                                        //~1A8iI~
        setTitle(Ptitleprefix+titleName);                          //~1A8iI~
	}                                                              //~1A8iI~

	public boolean connect (String s, int p)
	{	if (Dump.Y) Dump.println("Starting partner connection="+s+",port="+p);//~v101R~
		try
		{	Server=new Socket(s,p);
            Server.setTcpNoDelay(true);                            //~v101R~
			Out=new PrintWriter(
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
		{                                                          //~v101R~
            Dump.println(e,"PartnerFrame connect");                //~v101I~
			return false;                                          //~v101I~
		}
        if (Dump.Y) Dump.println("PartnetFrame client after open");//~1Ac0R~
//      Utils.chkNetwork();        //@@@@test                      //~1Ac0R~
//  	PT=new PartnerThread(In,Out,Input,Output,this);            //~v108R~
    	PT=new PartnerThread(In,Out,null,null,this);               //~v108I~
		PT.start();
		out("@@name "+                                             //~@@@2R~
//  		Global.getParameter("yourname","No Name"));            //~@@@2R~
//  		AG.YourName);                                          //~@@@2I~//~1A6BR~
    		AG.YourName+CONTYPE_PREFIX+connectionType);            //~1A6BI~
//        show();                                                  //~@@@2R~
        AG.RemoteStatus=AG.RS_IPCONNECTED;                                 //~@@@2I~
		getHostAddr(Server);                                       //~@@@2I~
        doAction(AG.resource.getString(R.string.Game));	//popup GameQuestion//~@@@2I~
		return true;
	}


	public void open (Socket server)
	{	if (Dump.Y) Dump.println("Starting partner server");       //~@@@1R~
		Server=server;
		try
		{                                                          //~v101R~
            Server.setTcpNoDelay(true);                            //~v101R~
		 	Out=new PrintWriter(                                   //~v101I~
				new DataOutputStream(Server.getOutputStream()),true);
			In=new BufferedReader(new InputStreamReader(
			    new DataInputStream(Server.getInputStream())));
		}
		catch (Exception e)
		{	if (Dump.Y) Dump.println("---> no connection");        //~@@@1R~
			new Message(this,Global.resourceString("Got_no_Connection_"));
			return;
		}
        if (Dump.Y) Dump.println("PartnetFrame server after open");//~1Ac0R~
//      Utils.chkNetwork();        //@@@@test                      //~1Ac0R~
//  	PT=new PartnerThread(In,Out,Input,Output,this);            //~v108R~
 	  	PT=new PartnerThread(In,Out,null,null,this);               //~v108I~//~@@@2R~
		PT.start();
        AG.RemoteStatus=AG.RS_IPCONNECTED;                                 //~@@@2I~
		getHostAddr(Server);                                       //~@@@2I~
	}

	public void doAction (String o)
	{                                                              //~v108I~
        if (o.equals(AG.resource.getString(R.string.Game))) //~v108I~//~@@@2R~
		{                                                          //~@@@2R~
        	if (Dump.Y) Dump.println("PartnerFrame doAction Game");//~v101I~
            dismissWaitingDialog();                                //~@@@2I~
            loadedNotes=null;   //GoFrame                          //~1A24R~
            if (Dump.Y) Dump.println("PartnerFrame new GameQuestion");//~1A75I~
			new GameQuestion(this);                                //~@@@2I~
		}
		else super.doAction(o);
	}

	public void doclose ()
    {                                                              //~v108I~
        if (Dump.Y) Dump.println("jagoclient.PartnerFrame doclose");//~1AbMI~
		out("@@@@end");                                            //~@@@2R~
		Out.close();
		new CloseConnection(Server,In);
		super.doclose();
	}
	public void doclose2()                                         //~1AbMI~
    {                                                              //~1AbMI~
        if (Dump.Y) Dump.println("jagoclient.PartnerFrame doclose2");//~1AbMI~
//  	out("@@@@end");                                            //~1AbMI~
//  	Out.close();                                               //~1AbMI~
		new CloseConnection(Server,In); //Close Serve and In       //~1AbMI~
		super.doclose();                                           //~1AbMI~
	}                                                              //~1AbMI~

	public boolean close ()
	{	if (PT.isAlive())
		{	new ClosePartnerQuestion(this);
			return false;
		}
		else return true;
	}

	/**
	The interpreter for the partner commands (all start with @@).
	*/
	public void interpret (String s)
//  {	if (s.startsWith("@@name"))                                //~v107R~
	{                                                              //~v107I~
        if (Dump.Y) Dump.println("PartnetFrame interpret:"+s);     //~v107I~
		if (s.startsWith("@@name"))                                //~v107I~
		{	StringParser p=new StringParser(s);
			p.skip("@@name");
//  		setTitle(Global.resourceString("Connection_to_")+p.upto('!'));//~1A6BR~
            String name=p.upto(CONTYPE_PREFIX.charAt(0)).trim();   //~1A6BI~
            PartnerName=name;                                      //~1A8iI~
//  		setTitle(Global.resourceString("Connection_to_")+name);//~1A6BI~//~1A85R~
//  		setTitle(CONN_TITLE_IP+Global.resourceString("Connection_to_")+name);//~1A8iR~//~1A85R~//+1AecR~
    		setTitle((AG.isNFCBT ? CONN_TITLE_NFC_BT :CONN_TITLE_BT)//+1AecI~
						+Global.resourceString("Connection_to_")+name);//+1AecI~
            p.advance();                                           //~1A6BI~
            int ct=p.parseint();                                   //~1A6BI~
            setConnectionType(ct);                                 //~1A6BI~
        	if (Dump.Y) Dump.println("PartnetFrame setTitle:"+Global.resourceString("Connection_to_")+name);//~1A6BI~
        	if (Dump.Y) Dump.println("PartnetFrame connectiontype="+connectionType);//~1A6BI~
			out("@@!name "+AG.YourName);                           //~1A37I~
		}
        else                                                       //~1A37I~
		if (s.startsWith("@@!name"))                               //~1A37I~
		{                                                          //~1A37I~
			StringParser p=new StringParser(s);                    //~1A37I~
			p.skip("@@!name");                                     //~1A37I~
        	if (Dump.Y) Dump.println("PartnetFrame partnername="+p.upto('!'));//~1A37I~
		}                                                          //~1A37I~
		else if (s.startsWith("@@board"))
//  	{	if (PGF!=null) return;                                 //~1A2bR~
    	{                                                          //~1A2bI~
    		if (PGF!=null)                                         //~1A2bI~
            {                                                      //~1A2bI~
				declineboard(R.string.ErrPartnerDeclineInUse);     //~1A2bI~
                return;                                            //~1A2bI~
            }                                                      //~1A2bI~
        	receivedNotes=null;                                    //~1A23I~
			StringParser p=new StringParser(s);
			p.skip("@@board");
			String color=p.parseword();
//			int C;
//			if (color.equals("b")) C=1;
//			else C=-1;
			int Size=p.parseint();
//            int TotalTime=p.parseint();                          //~v108R~
//            int ExtraTime=p.parseint();                          //~v108R~
//            int ExtraMoves=p.parseint();                         //~v108R~
//            int Handicap=p.parseint();                           //~v108R~
//            new BoardQuestion(this,Size,color,Handicap,TotalTime,//~v108I~
//                ExtraTime,ExtraMoves);                           //~v108I~
//            int Gameover=p.parseint();                             //~@@@2I~//~v1A0R~
//            int Gameover2=p.parseint();                            //~@@@2I~//~v1A0R~
            int TotalTime=p.parseint();                            //~v108I~
            int ExtraTime=p.parseint();                            //~@@@@I~//~@@@2M~
//            int Bishop=p.parseint();                               //~v108I~//~v1A0R~
//            int Knight=p.parseint();                               //~v108M~//~v1A0R~
            int GameOptions=p.parseint();                          //~@@@2R~
            int Handicap=p.parseint();                             //~v1A0I~
//          PartnerName=p.parseword();                             //~@@@2R~//~1A25R~
            PartnerName=p.parsewordDQ();                           //~1A25I~
//          if (PartnerName.length()>2)                            //~@@@2I~//~1A25R~
//          	PartnerName=PartnerName.substring(1,PartnerName.length()-1);//drop embedding dquote//~@@@2I~//~1A25R~
//          else                                                   //~@@@2I~//~1A25R~
//          	PartnerName="?";                                   //~@@@2I~//~1A25R~
            AG.PartnerName=PartnerName;                            //~@@@2I~
            String matchname="";                                      //~1A24I~
//            if ((GameOptions & GameQuestion.GAMEOPT_NOTES)!=0)  //reload Game//~1A24I~//~1A23R~
//            {                                                      //~1A24I~//~1A23R~
//                matchname=p.parsewordDQ();                         //~1A24R~//~1A23R~
//            }                                                      //~1A24I~//~1A23R~
            dismissWaitingDialog();                                //~@@@2I~
//  		new BoardQuestion(this,Size,color,Gameover,Gameover2,TotalTime,ExtraTime,//~v108R~//~@@@2R~//~v1A0R~
//  			Bishop,Knight,GameOptions,PartnerName);                                    //~v108R~//~@@@@R~//~@@@2R~//~v1A0R~
			try { Thread.sleep(200); } catch (Exception e) {}      //~1A76R~
            if (Dump.Y) Dump.println("PartnerFrame new BoardQuestion");//~1A75I~
    		new BoardQuestion(this,Size,color,TotalTime,ExtraTime, //~v1A0I~
//  			GameOptions,Handicap,PartnerName);                 //~v1A0R~//~1A24R~
    			GameOptions,Handicap,PartnerName,matchname);       //~1A24R~
		}
		else if (s.startsWith("@@!board"))
//*parm: b/w, size, totaltime, bishop, knight, gameoptions      //~v108I~//~@@@2R~
		{	if (PGF!=null) return;
        	ProgDlg.dismiss();                                     //~v1A0I~
			StringParser p=new StringParser(s);
			p.skip("@@!board");
			String color=p.parseword();
			int C;
			if (color.equals("b")) C=1;
			else C=-1;
			int Size=p.parseint();
//            int Gameover=p.parseint();                             //~@@@2I~//~v1A0R~
//            int Gameover2=p.parseint();                            //~@@@2I~//~v1A0R~
			int TotalTime=p.parseint();
  			int ExtraTime=p.parseint();                            //~@@@@I~//~@@@2M~
//            int Bishop=p.parseint();                               //~v108I~//~v1A0R~
//            int Knight=p.parseint();                               //~v108I~//~v1A0R~
  			int GameOptions=p.parseint();                          //~@@@2R~
  			int Handicap=p.parseint();                             //~v1A0I~
//			PartnerName=p.parseword();                             //~@@@2I~//~1A25R~
  			PartnerName=p.parsewordDQ();                           //~1A25I~
//          if (PartnerName.length()>2)                            //~@@@2I~//~1A25R~
//          	PartnerName=PartnerName.substring(1,PartnerName.length()-1);//drop embedding dquote//~@@@2I~//~1A25R~
//          else                                                   //~@@@2I~//~1A25R~
//          	PartnerName="?";                                   //~@@@2I~//~1A25R~
            AG.PartnerName=PartnerName;                            //~1A48I~
            GameRequester=true;                                    //~@@@2I~
//  		PGF=new PartnerGoFrame(this,Global.resourceString("Partner_Game"),//~@@@2R~
    		PGF=new PartnerGoFrame(this,                           //~@@@2I~
//  			C,Size,TotalTime*60,ExtraTime*60,ExtraMoves,Handicap);//~v108R~
//                C,Size,Gameover,Gameover2,TotalTime*60,ExtraTime,Bishop,Knight,GameOptions);  //~v108I~//~@@@@R~//~@@@2R~//~v1A0R~
                C,Size,TotalTime*60,ExtraTime,GameOptions,Handicap);        //~v1A0I~
            if (loadedNotes!=null)                                 //~1A24R~
            {                                                      //~1A24I~
            	PGF.reloadNotes=loadedNotes;	//GoFrame          //~1A24I~
            	PGF.reloadMatchTitle=loadedNotes.name;             //~1A24I~
            	PGF.loadFileNotes(loadedNotes); //TGF            //~1A24R~
			    setGameTitle(loadedNotes.name);                     //~1A24I~
            }                                                      //~1A24I~
			out("@@start");                                        //~@@@2R~
			Block=false;
//            Moves=new ListClass();                               //~@@@2R~
//            Moves.append(new ListElement(                        //~@@@2R~
//                new PartnerMove("board",C,Size,                  //~@@@2R~
//  				TotalTime,ExtraTime,ExtraMoves,Handicap)));    //~v108R~
		}
		else if (s.startsWith("@@-board"))
		{                                                          //~v1A0R~
        	ProgDlg.dismiss();                                     //~v1A0I~
//  		new Message(this,Global.resourceString("Partner_declines_the_game_"));//~v1A0I~//~1A24R~
    		new Message(this,AG.resource.getString(R.string.Partner_declines_the_game));//~1A24I~
			Block=false;
		}		
		else if (s.startsWith("@@start"))
		{	if (PGF==null) return;
			PGF.start();
			out("@@!start");                                       //~@@@2R~
		}
		else if (s.startsWith("@@!start"))
		{	if (PGF==null) return;
			PGF.start();
		}
		else if (s.startsWith("@@move"))
		{	if (PGF==null) return;
			yourMove();	//sound                                    //~v101I~
			StringParser p=new StringParser(s);
			p.skip("@@move");
			String color=p.parseword();
			int piece=p.parseint();                                //~v1A0R~
//  		int captured=p.parseint();     //non promoted          //~v1A0R~
    		int dropped=p.parseint();   //1:drop,0:move            //~v1A0I~
			int i=p.parseint(),j=p.parseint();
    		int ifrom=p.parseint();                                //~1A37I~
    		int jfrom=p.parseint();                                //~1A37I~
			int bt=p.parseint(),bm=p.parseint();
			int wt=p.parseint(),wm=p.parseint();
			int enteredExtraTime=p.parseint();                     //~1A06I~
			if (Dump.Y) Dump.println("Move of "+color+" at "+i+","+j+",drop="+dropped+",ifrom="+ifrom+",jfrom="+jfrom);//~@@@1R~//~v1A0R~//~1A37R~
            if (dropped==0)                                        //~1A37R~
            {                                                      //~1A37I~
            	receiveSelected(ifrom,jfrom);                      //~1A37I~
            }                                                      //~1A37I~
            ((GoFrame)PGF).B.swDropped=dropped==1;                                //~v1A0I~
			if (color.equals("b"))
			{	if (PGF.maincolor()<0) return;
//                if (captured!=0)                                 //~v1A0R~
//                    ((ConnectedBoard)PGF.B).partnerCapturePiece(i,j,-1,captured);//~v1A0R~
//				PGF.black(i,j);                                    //~@@@2R~
//				PGF.black(i,j,true/*reverse coordinate*/,piece);   //~@@@2R~//~1A0eR~
				PGF.black(i,j,true/*reverse coordinate*/,piece,dropped);//~1A0eI~
//                Moves.append(new ListElement                     //~@@@2R~
//                    (new PartnerMove("b",i,j,bt,bm,wt,wm)));     //~@@@2R~
				if (enteredExtraTime!=0)                           //~1A06I~
					PGF.resetExtraTime(1/*black*/);                //~1A06I~
			}
			else
			{	if (PGF.maincolor()>0) return;
//                if (captured!=0)                                 //~v1A0R~
//                    ((ConnectedBoard)PGF.B).partnerCapturePiece(i,j,1,captured);//~v1A0R~
//                PGF.white(i,j);                                  //~@@@2R~
//              PGF.white(i,j,true/*reverse coordinate*/,piece);   //~@@@2R~//~1A0eR~
                PGF.white(i,j,true/*reverse coordinate*/,piece,dropped);//~1A0eI~
//                Moves.append(new ListElement                     //~@@@2R~
//                    (new PartnerMove("w",i,j,bt,bm,wt,wm)));     //~@@@2R~
				if (enteredExtraTime!=0)                           //~1A06I~
					PGF.resetExtraTime(-1/*white*/);               //~1A06I~
			}
			PGF.settimes(bt,bm,wt,wm);
//            out("@@!move "+color+" "+i+" "+j+" "+                //~@@@2R~
//          out("@@!move "+color+" "+piece+" "+i+" "+j+" "+        //~@@@2I~//~v1A0R~
          out("@@!move "+color+" "+piece+" "+dropped+" "+i+" "+j+" "+//~v1A0I~
				bt+" "+bm+" "+wt+" "+wm);
		}
		else if (s.startsWith("@@!move"))
		{	if (PGF==null) return;
          out("@@!!move");	//to keep up/down sequense             //~1A39I~
        //********move piece at partner responsed time             //~@@@2I~
			StringParser p=new StringParser(s);
			p.skip("@@!move");
			String color=p.parseword();
			int piece=p.parseint();                                //~v1A0R~
//  		int captured=p.parseint();                             //~v1A0I~//~1A0eR~
    		int dropped=p.parseint();                              //~1A0eI~
			int i=p.parseint(),j=p.parseint();
			int bt=p.parseint(),bm=p.parseint();
			int wt=p.parseint(),wm=p.parseint();
			if (Dump.Y) Dump.println("Move of "+color+" piece:"+piece+" at "+i+","+j);//~@@@1R~//~@@@2R~
            if (color.equals("b"))                                 //~@@@2R~
            {   if (PGF.maincolor()<0) return;                     //~@@@2R~
//              PGF.black(i,j);                                    //~@@@2R~//~v101R~
//              PGF.black(i,j,piece);                              //~v101I~//~1A0eR~
                PGF.black(i,j,piece);             //~1A0eI~
//                Moves.append(new ListElement                     //~@@@2R~
//                    (new PartnerMove("b",i,j,bt,bm,wt,wm)));     //~@@@2R~
            }                                                      //~@@@2R~
            else                                                   //~@@@2R~
            {   if (PGF.maincolor()>0) return;                     //~@@@2R~
//              PGF.white(i,j);                                    //~@@@2R~//~v101R~
//              PGF.white(i,j,piece);                              //~v101I~//~1A0eR~
                PGF.white(i,j,piece);          //~1A0eI~
//                Moves.append(new ListElement                     //~@@@2R~
//                    (new PartnerMove("w",i,j,bt,bm,wt,wm)));     //~@@@2R~
            }                                                      //~@@@2R~
            PGF.settimes(bt,bm,wt,wm);                             //~@@@2R~
	        PGF.swWaitingPartnerResponse=false;                    //~1A38I~
		}
//        else if (s.startsWith("@@selected"))                       //~@@@2I~//~v1A0R~//~1A37R~
//        {   if (PGF==null) return;                                 //~@@@2I~//~v1A0R~//~1A37R~
//            StringParser p=new StringParser(s);                    //~@@@2I~//~v1A0R~//~1A37R~
//            p.skip("@@selected");                                  //~@@@2I~//~v1A0R~//~1A37R~
//            int i=p.parseint(),j=p.parseint();                     //~@@@2I~//~v1A0R~//~1A37R~
//            if (Dump.Y) Dump.println("@@selected at "+i+","+j);    //~@@@2I~//~v1A0R~//~1A37R~
//            PGF.receiveSelected(i,j);                              //~@@@2I~//~v1A0R~//~1A37R~
//            out("@@!selected "+i+" "+j);                           //~@@@2R~//~v1A0R~//~1A37R~
//        }                                                          //~@@@2I~//~v1A0R~//~1A37R~
//        else if (s.startsWith("@@!selected"))                      //~@@@2I~//~v1A0R~//~1A37R~
//        {   if (PGF==null) return;                                 //~@@@2I~//~v1A0R~//~1A37R~
//            StringParser p=new StringParser(s);                    //~@@@2I~//~v1A0R~//~1A37R~
//            p.skip("@@!selected");                                 //~@@@2I~//~v1A0R~//~1A37R~
//            int i=p.parseint(),j=p.parseint();                     //~@@@2I~//~v1A0R~//~1A37R~
//            if (Dump.Y) Dump.println("@@!selected at "+i+","+j);   //~@@@2I~//~v1A0R~//~1A37R~
//        }                                                          //~@@@2I~//~v1A0R~//~1A37R~
        else if (s.startsWith("@@pass"))                         //~v108R~//~@@@2R~
        {   if (PGF==null) return;                               //~v108R~//~@@@2R~
            StringParser p=new StringParser(s);                  //~v108R~//~@@@2R~
            p.skip("@@pass");                                    //~v108R~//~@@@2R~
            int bt=p.parseint(),bm=p.parseint();                 //~v108R~//~@@@2R~
            int wt=p.parseint(),wm=p.parseint();                 //~v108R~//~@@@2R~
            if (Dump.Y) Dump.println("Pass");                      //~@@@1R~//~v108R~//~@@@2R~
//            PGF.notifiedPass(); //received pass                    //~@@@2R~//~v1A0R~
            PGF.dopass();                                        //~v108R~//~@@@2R~
            PGF.settimes(bt,bm,wt,wm);                           //~v108R~//~@@@2R~
//            Moves.append(new ListElement                         //~v108R~//~@@@2R~
//                (new PartnerMove("pass",bt,bm,wt,wm)));          //~v108R~//~@@@2R~
            out("@@!pass "+bt+" "+bm+" "+wt+" "+wm);     //~v108R~ //~@@@2R~
        }                                                        //~v108R~//~@@@2R~
        else if (s.startsWith("@@!pass"))                        //~v108R~//~@@@2R~
        {   if (PGF==null) return;                               //~v108R~//~@@@2R~
            StringParser p=new StringParser(s);                  //~v108R~//~@@@2R~
            p.skip("@@!pass");                                   //~v108R~//~@@@2R~
            int bt=p.parseint(),bm=p.parseint();                 //~v108R~//~@@@2R~
            int wt=p.parseint(),wm=p.parseint();                 //~v108R~//~@@@2R~
            if (Dump.Y) Dump.println("Pass");                      //~@@@1R~//~v108R~//~@@@2R~
            PGF.dopass();                                        //~v108R~//~@@@2R~
//            Moves.append(new ListElement                         //~v108R~//~@@@2R~
//                (new PartnerMove("pass",bt,bm,wt,wm)));          //~v108R~//~@@@2R~
            PGF.settimes(bt,bm,wt,wm);                           //~v108R~//~@@@2R~
        }                                                        //~v108R~//~@@@2R~
		else if (s.startsWith("@@resign "))                        //~@@@2R~
		{                                                          //~@@@2I~
  			resignrequested(s.substring(9));                       //~@@@2R~
		}                                                          //~@@@2I~
		else if (s.startsWith("@@gameover "))                      //~v1A0I~
		{                                                          //~v1A0I~
            if (PGF==null) return;                                 //~v1A0I~
            StringParser p=new StringParser(s);                    //~v1A0I~
            p.skip("@@gameover ");                                 //~v1A0I~
            int reason=p.parseint();                               //~v1A0I~
            if (Dump.Y) Dump.println("@@gameover reason="+Integer.toHexString(reason));//~v1A0I~
            gameoverNotified(reason);                              //~v1A0I~
		}                                                          //~v1A0I~
		else if (s.startsWith("@@!gameover "))                     //~v1A0I~
		{                                                          //~v1A0I~
            if (Dump.Y) Dump.println("@@!gameover response");      //~v1A0I~
		}                                                          //~v1A0I~
		else if (s.startsWith("@@endgame"))
		{	if (PGF==null) return;
  			Block=true;                                            //~v108I~
			new EndGameQuestion(this);
//			Block=true;                                            //~v108R~
		}
		else if (s.startsWith("@@suspendgame"))                    //~1A23I~
		{	if (PGF==null) return;                                 //~1A23I~
		    swSuspendRequester=false;                              //~1A24I~
  			Block=true;                                            //~1A23I~
			new EndGameQuestion(this,true/*suspend*/);             //~1A23I~
		}                                                          //~1A23I~
		else if (s.startsWith("@@!endgame"))
		{	if (PGF==null) return;
//  		PGF.doscore();                                         //~@@@2R~
    		PGF.doscore(-PGF.Col/*winner is partner*/);  //partner accepted resign on EndGameQuestion//~@@@2R~
			Block=false;
		}
		else if (s.startsWith("@@-endgame"))
		{	if (PGF==null) return;
			new Message(this,"Partner declines game end!");
			Block=false;
		}
		else if (s.startsWith("@@!suspendgame "))                   //~1A23I~//~1A24R~
		{	if (PGF==null) return;                                 //~1A23I~
        	String partnerNotesName=s.substring(15);               //~1A24I~
            dismissWaitingDialog();                                //~1A24I~
        	PGF.receivedSuspendOK(partnerNotesName);                               //~1A23R~//~1A24R~
		}                                                          //~1A23I~
		else if (s.startsWith("@@!!suspendgame"))	//requester saved//~1A24R~
		{	if (PGF==null) return;                                 //~1A24I~
            dismissWaitingDialog();                                //~1A24I~
            if (suspendedFilename!=null)                           //~1Ab3I~
			new Message(this,AG.resource.getString(R.string.SuspendComplete,suspendedFilename));//~1Ab3I~
            else                                                   //~1Ab3I~
			new Message(this,AG.resource.getString(R.string.SuspendComplete,s.substring(16)));          //~1A24I~//~1A23R~
		}                                                          //~1A24I~
		else if (s.startsWith("@@!-suspendgame"))//requester save failed//~1A24R~
		{	if (PGF==null) return;                                 //~1A24I~
            dismissWaitingDialog();                                //~1A24I~
			new Message(this,R.string.SuspendSyncErr);            //~1A24I~
		}                                                          //~1A24I~
		else if (s.startsWith("@@-suspendgame"))                   //~1A23I~
		{	if (PGF==null) return;                                 //~1A23I~
            dismissWaitingDialog();                                //~1A24I~
			new Message(this,AG.resource.getString(R.string.DeclineSuspend));//~1A23I~
			Block=false;                                           //~1A23I~
		}                                                          //~1A23I~
		else if (s.startsWith("@@result"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@result");
			int b=p.parseint();
			int w=p.parseint();
			if (Dump.Y) Dump.println("Result "+b+" "+w);           //~@@@1R~
			new ResultQuestion(this,Global.resourceString("Accept_result__B_")+b+Global.resourceString("__W_")+w+"?",b,w);
			Block=true;
		}
		else if (s.startsWith("@@!result"))
		{	if (PGF==null) return;
			StringParser p=new StringParser(s);
			p.skip("@@!result");
			int b=p.parseint();
			int w=p.parseint();
			if (Dump.Y) Dump.println("Result "+b+" "+w);           //~@@@1R~
//            Output.append(Global.resourceString("Game_Result__B_")+b+Global.resourceString("__W_")+w+"\n",Color.green.darker());//~v108R~
			new Message(this,Global.resourceString("Result__B_")+b+Global.resourceString("__W_")+w+" was accepted!");
			Block=false;
		}
		else if (s.startsWith("@@-result"))
		{	if (PGF==null) return;
			new Message(this,Global.resourceString("Partner_declines_result_"));
			Block=false;
		}
		else if (s.startsWith("@@adjourn"))
		{	adjourn();
		}
		else if (s.startsWith("@@checkmate"))                      //~1A0eI~
		{	if (PGF==null) return;                                 //~1A0eI~
        	PGF.receiveCheckmate();                                //~1A0eI~
		}                                                          //~1A0eI~
		else if (s.startsWith("@@!checkmate"))                     //~1A0gI~
		{	if (PGF==null) return;                                 //~1A0gI~
        	PGF.responseReceivedCheckmate(true);                   //~1A0gR~
		}                                                          //~1A0gI~
		else if (s.startsWith("@@-checkmate"))                     //~1A0gI~
		{	if (PGF==null) return;                                 //~1A0gI~
        	PGF.responseReceivedCheckmate(false);                  //~1A0gR~
		}                                                          //~1A0gI~
		else if (s.startsWith("@@reload"))                         //~1A23I~
//  	{	if (PGF!=null) return;                                 //~1A23I~//~1A2bR~
    	{                                                          //~1A2bI~
    		if (PGF!=null)                                         //~1A2bI~
            {                                                      //~1A2bI~
				declineboard(R.string.ErrPartnerDeclineInUse);     //~1A2bI~
                return;                                            //~1A2bI~
            }                                                      //~1A2bI~
			StringParser p=new StringParser(s);                    //~1A23I~
			p.skip("@@reload");                                    //~1A23I~
	        PartnerName=p.parsewordDQ();                           //~1A23I~
            out("@@!reload");                                      //~1A23I~
		}                                                          //~1A23I~
		else if (s.startsWith("@@!reload"))                        //~1A23I~
		{	if (PGF!=null) return;                                 //~1A23I~
        	sendNotes();                                           //~1A23I~
		}                                                          //~1A23I~
		else if (s.startsWith("@@notes"))                          //~1A23I~
		{	if (PGF!=null) return;                                 //~1A23I~
        	recvNotes(s.substring(7));                             //~1A23I~
		}                                                          //~1A23I~
		else if (s.startsWith("@@!notes"))                         //~1A23I~
		{	if (PGF!=null) return;                                 //~1A23I~
		}                                                          //~1A23I~
		else if (s.startsWith("@@-notes"))                         //~1A23I~
		{	if (PGF!=null) return;                                 //~1A23I~
        	ProgDlg.dismiss();                                     //~1A23I~
    		new Message(this,AG.resource.getString(R.string.ErrNotesSend));//~1A23I~
			Block=false;                                           //~1A23I~
		}                                                          //~1A23I~
	}
//from PartnerGoFrame                                              //~v108R~
//parm: color, pos1, pos2, Black remaining Time , Black Moves remaining count in the time, White time, White moves//~v108I~
//  public boolean moveset (String c, int i, int j, int bt, int bm,//~@@@2R~
//  public boolean moveset (String c, int Ppiece,int i, int j, int bt, int bm,//~@@@2I~//~v1A0R~
//  public boolean moveset (String c, int Ppiece,int Pcaptured, int i, int j, int bt, int bm,//~v1A0R~
    public boolean moveset (String c, int Ppiece,int Pdropped, int i, int j, int bt, int bm,//~v1A0I~
//  	int wt, int wm)                                            //~1A06R~
    	int wt, int wm,int PenteredExtraTime)                      //~1A06I~
	{                                                              //~v1A0R~
		if (Dump.Y) Dump.println("PF:moveset Block="+Block);       //~v1A0I~
		if (Block) return false;                                   //~v1A0I~
        PGF.swWaitingPartnerResponse=true;                         //~1A38I~
//  	out("@@move "+c+" "+i+" "+j+" "+bt+" "+bm+" "+wt+" "+wm);  //~@@@2R~
//  	out("@@move "+c+" "+Ppiece+" "+Pcaptured+" "+i+" "+j+" "+bt+" "+bm+" "+wt+" "+wm);//~@@@2I~//~v1A0R~
//  	out("@@move "+c+" "+Ppiece+" "+Pdropped+" "+i+" "+j+" "+bt+" "+bm+" "+wt+" "+wm);//~v1A0I~//~1A06R~
//  	out("@@move "+c+" "+Ppiece+" "+Pdropped+" "+i+" "+j+" "+bt+" "+bm+" "+wt+" "+wm+" "+PenteredExtraTime);//~1A06I~//~1A37R~
    	out("@@move "+c+" "+Ppiece+" "+Pdropped+" "+i+" "+j+" "+sendiSelected+" "+sendjSelected+" "+bt+" "+bm+" "+wt+" "+wm+" "+PenteredExtraTime);//~1A37I~
        yourMove();	//sound stone                                  //~v101I~
		if (Dump.Y) Dump.println("PF:moveset send @@move c="+c+",piece="+Ppiece+",dropped="+Pdropped+",i="+i+",j="+j+"ifrom="+sendiSelected+",jfrom="+sendjSelected+",bt="+bt+",wt="+wt+",exterExtra="+PenteredExtraTime);//~@@@2R~//~v1A0R~//~1A06R~//~1A37R~
		return true;
	}
//*******************************************************************//~@@@2I~
//from PartnerGoFrame                                              //~@@@2I~
//send piece selected                                              //~@@@2I~
//*******************************************************************//~@@@2I~
	public boolean sendSelected (int Pi, int Pj)                    //~@@@2R~
	{	if (Block) return false;                                   //~@@@2I~
//        Out.println("@@selected "+Pi+" "+Pj);                    //~@@@2R~
//     	out("@@selected "+Pi+" "+Pj);                              //~@@@2I~//~v1A0R~//~1A37R~
		sendiSelected=Pi; sendjSelected=Pj;                        //~1A37I~
    	JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~@@@2I~
		return true;                                               //~@@@2I~
	}                                                              //~@@@2I~
//*******************************************************************//~1A37I~
    private void receiveSelected(int Pi,int Pj)                    //~1A37I~
    {                                                              //~1A37I~
        if (Dump.Y) Dump.println("receiveSelected at "+Pi+","+Pj); //~1A37I~
        PGF.receiveSelected(Pi,Pj);                                //~1A37I~
	}                                                              //~1A37I~
//*******************************************************************//~1A37I~
	public void out (String s)
	{                                                              //~@@@2R~
    	try                                                        //~@@@2I~
        {                                                          //~@@@2I~
    	if (Dump.Y) Dump.println("PartnerFrame before out="+s+",Out="+Out.toString());//~1A23I~//~1A6zR~
			Out.println(s);                                        //~@@@2I~
    	if (Dump.Y) Dump.println("PartnerFrame println end");      //~1A6zI~
//            Out.flush(); //printwriter with flash option                                        //~v101R~//~1A39R~
//    	if (Dump.Y) Dump.println("PartnerFrame out flushed");      //~v101R~//~1A39R~
        }                                                          //~@@@2I~
        catch(Exception e)                                         //~@@@2I~
        {                                                          //~@@@2I~
        	Dump.println(e,"PatrnerFrame out():"+s);               //~@@@2I~
        }                                                          //~@@@2I~
	}

	public void refresh ()
	{
	}

	public void set (int i, int j)
	{
	}
//**from PGF.movepass()                                            //~@@@2R~
	public void pass (int bt, int bm, int wt, int wm)
	{                                                              //~@@@2R~
//		Out.println("@@pass "+bt+" "+bm+" "+wt+" "+wm);            //~@@@2I~
		out("@@pass "+bt+" "+bm+" "+wt+" "+wm);                    //~@@@2I~
	}

	public void endgame ()
	{	if (Block) return;
		Block=true;
//      Out.println("@@endgame");                                  //~@@@2R~
        out("@@endgame");                                          //~@@@2I~
	}
//*resign button pushed ********************                       //~@@@2R~
	public void resign ()	//from PartnerGoFrame                  //~@@@2R~
	{                                                              //~@@@2I~
		new EndGameQuestion(this,AG.resource.getString(R.string.EndgameResign),true);//~@@@2I~
	}                                                              //~@@@2I~
//***Ok for EndgameQuestion(Resign)(requester) **************      //~@@@2I~
	public void doresign ()                                        //+@@@2I~                                                              //~@@@2I~
	{	if (Block) return;                                         //~@@@2I~
		Block=true;                                                //~@@@2I~
		if (Dump.Y) Dump.println("doresign send @@resign");        //~@@@2I~
		out("@@resign \""+AG.YourName+"\"");                       //~@@@2R~
        ((ConnectedBoard)(PGF.B)).infoMsg(R.string.Info_I_Resign);               //~@@@@I~//~@@@2I~
    }                                                              //~@@@2R~
//***received @@resign **************                              //~@@@2I~
//***doendgame() will be called if replyed OK **************       //~@@@2I~
	public void resignrequested(String Ppartnername)               //~@@@2R~
	{                                                              //~@@@2M~
//        ((ConnectedBoard)(PGF.B)).infoMsg(Ppartnername+" ",R.string.Info_Opponent_Resign);//~@@@2R~
        ((ConnectedBoard)(((GoFrame)PGF).B)).infoMsg(AG.resource.getString(R.string.Info_Opponent_Resign,Ppartnername));//~@@@2I~
//        new EndGameQuestion(this,AG.resource.getString(R.string.DoYouAcceptResign),false);//~@@@2R~
        doendgame();    //no question but msg only then            //~@@@2R~
	 	new Message(this,Ppartnername+" "+AG.resource.getString(R.string.Msg_Partner_Resigned));//~@@@2R~
	}                                                              //~@@@2M~
//****************************************************             //~@@@2R~
//*EndGameQuetion replyed OK send @@!endgame	                   //~@@@2R~
	public void doendgame ()
	{                                                              //~@@@2R~
		out("@@!endgame");                                         //~@@@2I~
//  	PGF.doscore();                                             //~@@@2R~
    	PGF.doscore(PGF.Col/*winner is me*/); //accepted endgame   //~@@@2R~
		Block=false;
	}
    //**EndGameQuestion reply Yes                                  //~1A23I~
	public void dosuspendgame ()                                   //~1A23I~
	{                                                              //~1A23I~
    	PGF.acceptSuspend();                                        //~1A23R~
//  	out("@@!suspendgame");                                     //~1A23I~//~1A24R~
	}                                                              //~1A23I~
	public void sendSuspendOK(String Pnotename)                    //~1A24R~
	{                                                              //~1A24I~
        suspendedFilename=Pnotename;                              //~1Ab3I~
	    out("@@!suspendgame "+Pnotename);                          //~1A24R~
    	GameQuestion.waitingResponse();                            //~1A24I~
	}                                                              //~1A24I~
//*EndGameQuetion replyed NO                                       //~@@@2I~
	public void declineendgame ()
	{                                                              //~@@@2R~
		out("@@-endgame");                                         //~@@@2I~
		Block=false;
	}
    //****************************************                     //~1A23I~
	public void declinesuspendgame ()                              //~1A23I~
	{                                                              //~1A23I~
		out("@@-suspendgame");                                     //~1A23I~
		Block=false;                                               //~1A23I~
	}                                                              //~1A23I~
    //****************************************                     //~1A23I~
	public void sendSuspend()                                      //~1A23I~
	{                                                              //~1A23I~
		out("@@suspendgame");                                      //~1A23I~
		Block=true;                                                //~1A23I~
	    swSuspendRequester=true;                                   //~1A24I~
    	GameQuestion.waitingResponse();                            //~1A24I~
	}                                                              //~1A23I~
    //****************************************                     //~1A24I~
	public void sendSuspendSync(String Pnotesname)                                  //~1A24I~//~1A23R~
	{                                                              //~1A24I~
		out("@@!!suspendgame "+Pnotesname);                                    //~1A24I~//~1A23R~
		new Message(this,AG.resource.getString(R.string.SuspendComplete,Pnotesname));              //~1A24I~//~1A23R~
	}                                                              //~1A24I~
    //****************************************                     //~1A24I~
	public void sendSuspendNoSync()                                //~1A24I~
	{                                                              //~1A24I~
		out("@@!-suspendgame");                                    //~1A24I~
	}                                                              //~1A24I~
    //****************************************                     //~v1A0I~
	public void gameoverNotify (int Preason)                       //~v1A0I~
	{                                                              //~v1A0I~
		Block=true;                                                //~v1A0I~
		if (Dump.Y) Dump.println("PF:gameoverNotify send @@gameover reason=x"+Integer.toHexString(Preason));//~v1A0R~
		out("@@gameover "+Preason);                                //~v1A0I~
    }                                                              //~v1A0I~
    //****************************************                     //~v1A0I~
	public void gameoverNotified (int Preason)                     //~v1A0I~
	{                                                              //~v1A0I~
		Block=true;                                                //~v1A0I~
		if (Dump.Y) Dump.println("PF:gameoverNotified reason="+Integer.toHexString(Preason));//~v1A0I~
        PGF.gameoverNotified(PGF.Col,Preason);                             //~v1A0I~
		out("@@!gameover");                                        //~v1A0R~
    }                                                              //~v1A0I~
    //****************************************                     //~v1A0I~

	public void doresult (int b, int w)
	{                                                              //~@@@2R~
		out("@@!result "+b+" "+w);                                 //~@@@2I~
//        Output.append(Global.resourceString("Game_Result__B_")+b+Global.resourceString("__W_")+w+"\n",Color.green.darker());//~v108R~
		Block=false;
	}

	public void declineresult ()
	{                                                              //~@@@2R~
		out("@@-result");                                          //~@@@2I~
		Block=false;
	}

//*parm:boardsize, b/w,gameover,gameover2,totaltime,extratime,bishop,knight,gameoptions//~@@@2R~
//  public void dorequest (int s, String c, int h, int tt, int et, int em)//~@@@@R~
//  {	Out.println("@@board "+c+" "+s+" "+tt+" "+et+" "+em+" "+h);//~@@@@R~
//    public void dorequest (int s, String c, int gameover,int gameover2,int tt, int et, int bishop, int knight, int gameoptions)//~@@@@I~//~@@@2R~//~v1A0R~
    public void dorequest (int s, String c,int tt, int et,int gameoptions,int Phandicap)//~v1A0I~
    {                                                              //~@@@@I~
//  	Out.println("@@board "+c+" "+s+" "+gameover+" "+gameover2+" "+tt+" "+et+" "+bishop+" "+knight+" "+gameoptions+" \""+AG.YourName+"\"");//~@@@2R~
//        out("@@board "+c+" "+s+" "+gameover+" "+gameover2+" "+tt+" "+et+" "+bishop+" "+knight+" "+gameoptions+" \""+AG.YourName+"\"");//~@@@2I~//~v1A0R~
        out("@@board "+c+" "+s+" "+tt+" "+et+" "+gameoptions+" "+Phandicap+" \""+AG.YourName+"\"");//~v1A0I~
		Block=true;
	}
	//*****************************************************************************//~1A23I~
	//*-->@@reload                                                 //~1A23I~
	//*<--@@!reload                                                //~1A23I~
	//*-->@@notes                                                  //~1A23I~
	//*-->@@notes                                                  //~1A23I~
	//*<--@@!notes/@@-notes                                        //~1A23I~
	//*<--@@!board/@@-board (BoardQuestion)                        //~1A23I~
	//*****************************************************************************//~1A23I~
    public void dorequest_reload(Notes Pnotes)                     //~1A24I~
    {                                                              //~1A24I~
        loadedNotes=Pnotes;   //GoFrame                            //~1A24R~
        out("@@reload"+" \""+AG.YourName+"\"");                    //~1A23I~
    }                                                              //~1A24I~
	//*****************************************************************************//~1A23I~
	//*received @@!reload                                          //~1A23I~
	//*****************************************************************************//~1A23I~
    public void sendNotes()                                        //~1A23I~
    {                                                              //~1A23I~
    	if (loadedNotes==null)                                     //~1A23I~
        	return;                                                //~1A23I~
        loadedNotes.sendNotes(Out,"@@notes");                      //~1A23I~
    }                                                              //~1A23I~
	//*****************************************************************************//~1A23I~
	//*received @@@@notes                                          //~1A23I~
	//*****************************************************************************//~1A23I~
    public void recvNotes(String Pline/*lineno+____*/)             //~1A23I~
    {                                                              //~1A23I~
    	String line;                                               //~1A23I~
        boolean top;                                               //~1A2bI~
    //***********************                                      //~1A23I~
    	if (Dump.Y) Dump.println("recvNote line="+Pline);          //~1A23I~
        int len=Pline.length();                                      //~1A23I~
        if (len>0)                                                 //~1A23I~
        {                                                          //~1A23I~
            if (Pline.startsWith(NotesTree.TREE_PREFIX))                     //~1A2bI~
            {                                                      //~1A2bI~
            	line=Pline;                                        //~1A2bI~
            	top=false;	//1st line of @@notes                  //~1A2bI~
            }                                                      //~1A2bI~
            else                                                   //~1A2bI~
            {                                                      //~1A2bI~
        		line=Pline.substring(1);	//drop lineno                              //~1A23I~//~1A2bR~
            	top=Pline.charAt(0)=='0';                      //~1A23R~//~1A2bR~
            }                                                      //~1A2bI~
	        Notes.receiveNotes(line,top);                          //~1A23I~
            return;                                                //~1A23I~
        }                                                          //~1A23I~
	    Notes notes=Notes.receiveNotes("",false);	//eof                      //~1A23I~
        if (notes==null)                                            //~1A23I~
        {                                                          //~1A23I~
        	out("@@-notes");                                       //~1A23I~
	    	AView.showToast(R.string.ErrNotesReceive);             //~1A23I~
            return;                                                //~1A23I~
        }                                                          //~1A23I~
        out("@@!notes");                                           //~1A23M~
        String color=notes.yourcolor>0?"b":"w";                    //~1A23I~
        int Size=AG.BOARDSIZE_SHOGI;                                   //~1A23I~
        int TotalTime=notes.totalTime;                                 //~1A23I~
        int ExtraTime=notes.extraTime;                               //~1A23I~
//      int GameOptions=notes.gameoptions;                             //~1A23I~//~1A2eR~
        int GameOptions=notes.gameoptions|GameQuestion.GAMEOPT_RECEIVENOTES;//~1A2eI~
        notes.gameoptions=GameOptions;                             //~1A2eI~
//      int Handicap=0;                                                //~1A23I~//~1A2eR~
        int Handicap=notes.handicap;                               //~1A2eI~
//      PartnerName;                                               //~1A23I~
        AG.PartnerName=PartnerName;                                //~1A23I~
        String matchname=notes.name;                               //~1A23I~
        receivedNotes=notes;                                       //~1A23I~
        new BoardQuestion(this,Size,color,TotalTime,ExtraTime,     //~1A23I~
            GameOptions,Handicap,PartnerName,matchname);           //~1A23I~
    }                                                              //~1A23I~

//*****************************************************************************//~@@@2I~
//*from BoardQuestion                                              //~@@@2I~
//*Accepted/Declined                                               //~@@@2I~
//*****************************************************************************//~@@@2I~
//*parm:boardsize, b/w,gameover,gameover2, totaltime,extratime, bishop, knight,gameoptions//~@@@2R~
//	public void doboard (int Size, String C, int Handicap,         //~v108R~
//			int TotalTime, int ExtraTime, int ExtraMoves)          //~v108R~
//    public void doboard (int Size, String C, int Gameover,int Gameover2,//~@@@2R~//~v1A0R~
//            int TotalTime,int ExtraTime, int Bishop, int Knight, int GameOptions)                 //~v108I~//~@@@@R~//~@@@2R~//~v1A0R~
    public void doboard (int Size, String C,                       //~v1A0I~
            int TotalTime,int ExtraTime,int GameOptions,int Phandicap)//~v1A0R~
//  {	PGF=new PartnerGoFrame(this,"Partner Game",                //~@@@2R~
    {                                                              //~@@@2R~
        GameRequester=false;                                       //~@@@2I~
    	PGF=new PartnerGoFrame(this,                               //~@@@2I~
//  		C.equals("b")?-1:1,Size,TotalTime*60,ExtraTime*60,ExtraMoves,Handicap);//~v108R~
//            C.equals("b")?-1:1,Size,Gameover,Gameover2,TotalTime*60,ExtraTime,Bishop,Knight,GameOptions);//~v108I~//~@@@@R~//~@@@2R~//~v1A0R~
            C.equals("b")?-1:1,Size,TotalTime*60,ExtraTime,GameOptions,Phandicap);//~v1A0R~
        if (receivedNotes!=null)                                   //~1A23I~
        {                                                          //~1A23I~
            PGF.reloadNotes=receivedNotes;    //GoFrame            //~1A23I~
            PGF.reloadMatchTitle=receivedNotes.name;               //~1A23I~
            PGF.loadFileNotes(receivedNotes); //TGF                //~1A23I~
            setGameTitle(receivedNotes.name);                      //~1A23I~
        }                                                          //~1A23I~
		if (C.equals("b"))                                         //~@@@2R~
			out("@@!board b"+                                      //~@@@2I~
//  		" "+Size+" "+TotalTime+" "+ExtraTime+" "+ExtraMoves+" "+Handicap);//~v108R~
//            " "+Size+" "+Gameover+" "+Gameover2+" "+TotalTime+" "+ExtraTime+" "+Bishop+" "+Knight+" "+GameOptions+" \""+AG.YourName+"\"");//~v108I~//~@@@@R~//~@@@2R~//~v1A0R~
            " "+Size+" "+TotalTime+" "+ExtraTime+" "+GameOptions+" "+Phandicap+" \""+AG.YourName+"\"");//~v1A0R~
		else                                                       //~@@@2R~
			out("@@!board w"+                                      //~@@@2I~
//  		" "+Size+" "+TotalTime+" "+ExtraTime+" "+ExtraMoves+" "+Handicap);//~v108R~
//            " "+Size+" "+Gameover+" "+Gameover2+" "+TotalTime+" "+ExtraTime+" "+Bishop+" "+Knight+" "+GameOptions+" \""+AG.YourName+"\"");//~@@@2R~//~v1A0R~
            " "+Size+" "+TotalTime+" "+ExtraTime+" "+GameOptions+" "+Phandicap+" \""+AG.YourName+"\"");//~v1A0R~
//        Moves=new ListClass();                                   //~@@@2R~
//        Moves.append(new ListElement(                            //~@@@2R~
//            new PartnerMove("board",C.equals("b")?-1:1,          //~@@@2R~
//  			Size,TotalTime,ExtraTime,ExtraMoves,Handicap)));   //~v108R~
	}

	public void declineboard ()
	{                                                              //~@@@2R~
		out("@@-board");                                           //~@@@2I~
	}
	private void declineboard (int Presid)                         //~1A2bI~
	{                                                              //~1A2bI~
    	new Message(this,Presid);                                  //~1A2bI~
		declineboard();                                  //~1A2bI~
	}                                                              //~1A2bI~

//********************************************************         //~@@@@I~
//*from PartnerGoFrame:doclose                                     //~@@@@I~
//********************************************************         //~@@@@I~
	public void boardclosed (PartnerGoFrame pgf)
	{	if (PGF==pgf)
		{                                                          //~@@@2R~
			out("@@adjourn");                                      //~@@@2I~
//            savemoves();                                         //~v108R~
		}
	}

	public void adjourn ()
//  {	new Message(this,Global.resourceString("Your_Partner_closed_the_board_"));//~1A28R~
    {                                                              //~1A28I~
    	if (Dump.Y) Dump.println("PF:adjourn");                    //~1A2eI~
		if (PGF==null)	//doclose set null                         //~1A2eI~
        	return;                                                //~1A2eI~
    	new Message(this,R.string.InfoYourPartnerClosedTheBoard);      //~1A28I~
//        savemoves();                                             //~v108R~
        if (Dump.Y) Dump.println("adjourn filedsaved="+PGF.swFileSaved);//~1A28I~
      if (PGF.swFileSaved)                                         //~1A28I~
      {                                                            //~1A28I~
		PGF.acceptClosed();		//close frame                      //~@@@@I~
		PGF=null;
      }                                                            //~1A28I~
	}


	void acceptrestore ()
	{                                                              //~@@@2R~
		out("@@!restore");                                         //~@@@2I~
	}

	void declinerestore ()
	{                                                              //~@@@2R~
		out("@@-restore");                                         //~@@@2I~
	}

//***************************************************************  //~@@@2I~
	public void disconnect()	//from IPConnection                //~@@@2I~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("PartnerFrame Disconnect");       //~@@@2I~
        if (PGF==null)	//not game started                         //~@@@2I~
        {                                                          //~@@@2I~
			doclose();	//CloseConnection                          //~@@@2I~
        }                                                          //~@@@2I~
        else                                                       //~@@@2I~
    	if (PT!=null && PT.isAlive())                              //~@@@2I~
			resign();                                              //~@@@2I~
        else                                                       //~@@@2I~
			doclose();	//CloseConnection	                       //~@@@2I~
    }                                                              //~@@@2I~
//***************************************************************  //~@@@2I~
	public static void dismissWaitingDialog()                      //~@@@2R~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("PartnerFrame DismissWaitiingDialog");//~@@@2R~
		ProgDlg.dismiss();                                         //~@@@2I~
        IPConnection.closeDialog();                                //~1A6yI~
        BluetoothConnection.closeDialog();                         //~1AbvI~
        DialogNFC.closeDialog();	//close DialogNFC if showing   //~1A6sI~
        DialogNFCBT.closeDialog();	//close DialogNFCBT if showing //~1AbjI~
    }                                                              //~@@@2I~
//***************************************************************  //~@@@2M~
//*from PartnerThread when connection failed                       //~@@@2M~
//*change Resign button to Close                                   //~@@@2M~
//***************************************************************  //~@@@2M~
	public void interrupted(int Pstrid)                            //~@@@2M~
    {                                                              //~@@@2M~
      	if (Dump.Y) Dump.println("PartnerFrame interrupted pgf="+(PGF==null?"null":PGF.toString()));//~@@@2I~
      try                                                          //~1A6kI~
      {                                                            //~1A53I~//~1A6kI~
        if (PGF!=null)                                             //~@@@2M~
	        PGF.gameInterrupted(Pstrid);                           //~@@@2M~
        else                                                       //~@@@2I~
        {                                                          //~1A6kI~
        	if (AG.aIPConnection!=null)                            //~1A6kI~
            	AG.aIPConnection.updateViewDisconnected();         //~1A6kI~
        	if (AG.aBTConnection!=null)                            //~1A6kI~
            	AG.aBTConnection.updateViewDisconnected();         //~1A6kI~
            new Message(this,R.string.ErrDisconnected);              //~@@@2I~
        }                                                          //~1A6kI~
      }                                                            //~1A6kI~
      catch(Exception e)                                           //~1A6kI~
      {                                                            //~1A6kI~
        Dump.println(e,"PartnerFrame:interrupted");                //~1A6kI~
      }                                                            //~1A6kI~
    }                                                              //~@@@2M~
//***************************************************************  //~@@@2I~
	private void getHostAddr(Socket Psocket)                       //~@@@2I~
    {                                                              //~@@@2I~
//        InetAddress ia=Psocket.getInetAddress();                   //~@@@2I~//~1A8fR~
//        if (ia!=null)                                              //~@@@2M~//~1A8fR~
//        {                                                          //~@@@2M~//~1A8fR~
//            if (Dump.Y) Dump.println("server inet address="+ia.getHostAddress()+",name="+ia.getHostName());//~@@@2I~//~1A8fR~
//            if (Dump.Y) Dump.println("server inet address="+ia.toString());//~@@@2I~//~1A8fR~
//            AG.RemoteInetAddress=ia.getHostAddress();              //~@@@2R~//~1A8fR~
//        }                                                          //~@@@2M~//~1A8fR~
////      if (Dump.Y) Dump.println("client local address="+Server.getLocalAddress().toString());//~@@@2M~//~1A6sR~//~1A8fR~
//        ia=Psocket.getLocalAddress();                              //~1A6sI~//~1A8fR~
//        if (ia!=null)                                              //~1A6sI~//~1A8fR~
//        {                                                          //~1A6sI~//~1A8fR~
//            AG.LocalInetAddress=ia.getHostAddress();               //~1A6sI~//~1A8fR~
//            if (Dump.Y) Dump.println("server inet localaddress="+AG.LocalInetAddress);//~1A6sI~//~1A8fR~
//        }                                                          //~1A6sI~//~1A8fR~
        AG.RemoteInetAddressLAN=Utils.getRemoteIPAddr(Psocket,null);//~1A8fI~
        AG.LocalInetAddressLAN=Utils.getLocalIPAddr(Psocket,null); //~1A8fI~
	    if (Dump.Y) Dump.println("PartnerFrame:getHostAddr remote="+AG.RemoteInetAddressLAN+",local="+AG.LocalInetAddressLAN);//~1A8fI~
    }                                                              //~@@@2I~
//***************************************************************  //~1A04I~
	private void yourMove()                                        //~v101I~
    {                                                              //~v101I~
    	if (Dump.Y) Dump.println("PF:yourMove sound stone start"); //~v101I~
//  	JagoSound.play("stone","click",true);                    //~@@@@R~//~v101I~//~1A09R~
    	JagoSound.play("piecedown",false/*not change to beep when beeponly option is on*/);//~1A09I~
    	if (Dump.Y) Dump.println("PF:yourMove sound stone end");   //~v101I~
	}                                                              //~v101I~
//***************************************************************  //~1A6BI~
//  private void setConnectionType(int Pcontype)                   //~1A8iI~
    public void setConnectionType(int Pcontype)                    //~1A8iI~
    {                                                              //~1A6BI~
    	int ct=Pcontype;                                           //~1A6BI~
    	if (Pcontype==IPConnection.NFC_CLIENT)                     //~1A6BI~
        	ct=IPConnection.NFC_SERVER;                            //~1A6BI~
        else                                                       //~1A6BI~
        if (ct==IPConnection.WD_CLIENT)                            //~1A6BI~
            ct=IPConnection.WD_SERVER;                             //~1A6BI~
        connectionType=ct;                                         //~1A6BI~
    }                                                              //~1A6BI~
//***************************************************************  //~1A04I~
//*reset for new PGF                                               //~1A04I~
//***************************************************************  //~1A04I~
	public void initPF()                                           //~1A04I~
    {                                                              //~1A04I~
    	if (Dump.Y) Dump.println("initPF");                        //~1A04I~
		Block=false;                                               //~1A04I~
	}                                                              //~1A04I~
//***************************************************************  //~1A0gR~
	public void sendCheckmate()                                    //~1A0gR~
    {                                                              //~1A0gR~
    	if (Dump.Y) Dump.println("sendCheckmate");                 //~1A0gR~
		out("@@checkmate");                                        //~1A0gR~
	}                                                              //~1A0gR~
//***************************************************************  //~1A0gI~
	public void sendResponseCheckmate(boolean Pagree)              //~1A0gR~
    {                                                              //~1A0gI~
    	if (Dump.Y) Dump.println("responseCheckmate agree="+Pagree);//~1A0gI~
        if (Pagree)                                                //~1A0gI~
			out("@@!checkmate");                                   //~1A0gR~
        else                                                       //~1A0gI~
			out("@@-checkmate");                                   //~1A0gR~
	}                                                              //~1A0gI~
    //***************************                                  //~1A24I~
    private void setGameTitle(String Pname)                        //~1A24I~
    {                                                              //~1A24I~
//  	int id=BTConnection?R.string.Title_PartnerMatchBT:R.string.Title_PartnerMatchIP;//~1A24I~//~1A75R~
		int id;                                                    //~1A75I~
      	if (connectionType==IPConnection.NFC_SERVER||connectionType==IPConnection.NFC_CLIENT)//~1A75I~
			id=R.string.Title_PartnerMatchNFC;                     //~1A75I~
      	else                                                       //~1A75I~
      	if (connectionType==IPConnection.WD_SERVER||connectionType==IPConnection.WD_CLIENT)//~1A75I~
			id=R.string.Title_PartnerMatchWD;                      //~1A75I~
      	else                                                       //~1A75I~
    	    id=BTConnection?R.string.Title_PartnerMatchBT:R.string.Title_PartnerMatchIP;//~1A75I~
        String title;                                              //~1A24I~
        title=AG.resource.getString(id)+":"+Pname;                 //~1A24I~
        (new Component()).setTitle(title);                         //~1A24I~
    }                                                              //~1A24I~
//************************************************************     //~1A24I~
    @Override //FileDialogI   fromGameQuestion:reload              //~1A24R~
    public void fileDialogLoaded(CloseDialog Pgq,Notes Pnotes) //GameQuestion request Reload File//~1A24I~
    {                                                              //~1A24I~
    	GameQuestion.waitingResponse();                            //~1A24I~
	    dorequest_reload (Pnotes);                                 //~1A24I~
    }                                                              //~1A24I~
	public void fileDialogSaved(String Pnotename){}                //~1A24R~
	public void fileDialogNotSaved(){}                             //~1A24I~
//**************************************************************************//~v110I~//~1A85I~
//*from AMain at appStop,send @@adjourn and cose out to notify termination//~v110I~//~1A85I~
//**************************************************************************//~v110I~//~1A85I~
    public void closeStream()                                      //~v110I~//~1A85I~
    {                                                              //~v110I~//~1A85I~
	    if (Dump.Y) Dump.println("PartnerFrame close Stream");     //~v110I~//~1A85I~
//      stopTimer();                                               //~v110M~//~1A85I~
        if (Out!=null)                                             //~v110I~//~1A85I~
        {                                                          //~v110I~//~1A85I~
            if (Out!=null)                                         //~v110I~//~1A85I~
            {                                                      //~v110I~//~1A85I~
			    if (Dump.Y) Dump.println("out @@adjourn");         //~v110I~//~1A85I~
				out("@@adjourn");                          //~v110I~//~1A8cR~//~1A85I~
			    if (Dump.Y) Dump.println("out close");             //~v110I~//~1A85I~
                Out.close();                                       //~v110I~//~1A85I~
            }                                                      //~v110I~//~1A85I~
        }                                                          //~v110I~//~1A85I~
    }                                                              //~v110I~//~1A85I~
}

