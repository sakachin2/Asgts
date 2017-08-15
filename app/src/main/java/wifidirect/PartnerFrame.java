//*CID://+1AecR~:                                   update#=  180; //+1AecR~
//****************************************************************************//~@@@1I~
//1Aec 2015/07/26 set connection type for Server                   //+1AecI~
//1Ae4 2015/07/24 addtional to 1Ab1. move setconnectiontype from wifidirect.PartnerFrame to jagoclient.partnerframe//~1Ae4I~
//1Ac3 2015/07/06 WD:Unpare after active session was closed        //~1Ac3R~
//1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//~1Ac0I~
//1A8o 2015/04/09 (BUG)disconnect is nop by aPartnetFrameIP==null for server size PartnerFrame//~1A8oI~
//1A8i 2015/03/05 set connection type to PartnerFrame title        //~1A8iI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~//~1A8oI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A85 2015/02/25 close each time partnerframe for IP Connection   //~1A85I~
//1A90 2015/04/18 (like as 1A84)WiFiDirect from Top panel          //~1A90I~
//1A76 2015/02/23 modal dialog(BoardQuestion) could not be posted if requested Game when IPConnection dialog is showen//~1A76I~
//                Dismiss IPConnection dialog se Current is Frame after BoardQuestion dialog started.//~1A76I~
//                By isCurrentDialog() is off,ActionEvent did not post(countdown latch).//~1A76I~
//                insert sleep.                                    //~1A76I~
//1A6B 2015/02/21 IP game title;identify IP and WifiDirect(WD)     //~1A6BI~
//1A6z 2015/02/20 PartnerFrame NPE at adjourn before board created by tcp delay//~1A6zI~
//1A6y 2015/02/20 dismiss ipconnection dialog when boardquestiondialog up by opponent game requested//~1A6yI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6k 2015/02/15 re-open IPConnection/BTConnection dialog when diconnected when dislog is opened.//~1A53I~
//1A53 2014/11/01 SayDialig title;missing partnername for game requester//~1A53I~
//1A39 2013/04/22 Over 1A37,out dummy response for !move           //~1A39I~
//                @@move b-->,<--@@!move b,@@!!move b-->,<--@@move w,...//~1A39I~
//1A38 2013/04/22 (BUG)When response(@@!move) delayed,accept next select.//~1A38I~
//                Then move confuse to move to old dest from new selected.//~1A38I~
//1A37 2013/04/20 avoid TCP delay bynagle algolism(witre-write-read is bad)//~1A37I~
//                So no send @@select                              //~1A37I~
//                PartnerFrame                                     //~1A37I~
//1A25 2013/03/25 StringParser:consideration quotation             //~1A25I~
//1A09 2013/03/03 use not stone but piecedown at yourMove()        //~1A09I~//~1A06I~
//                yourMove is not called for LGF, nop for PGF      //~1A09I~//~1A06I~
//                pieceMoved call piecedown for LGF,no sound for PGF//~1A09I~//~1A06I~
//1A06 2013/03/02 remaining ExtraTime was accounted.               //~1A06I~
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
//package jagoclient.partner;                                      //~1A8cR~
package wifidirect; //~1A8cI~

import jagoclient.CloseConnection;
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.Message;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.Asgts.AG;
import com.Asgts.ProgDlg;
import com.Asgts.R;
import com.Asgts.URunnable;
import com.Asgts.URunnableI;
import com.Asgts.Utils;

import rene.util.parser.StringParser;
import wifidirect.DialogNFC;

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
 * The partner frame contains a simple chat dialog and a button to start a game
 * or restore an old game. This class contains an interpreters for the partner
 * commands.
 */

// public class PartnerFrame extends CloseFrame //~1A8cR~
public class PartnerFrame extends jagoclient.partner.PartnerFrame // ~1A8cI~
		// implements KeyListener //~@@@1I~//~@@@2R~
		implements URunnableI // ~1Ac0I~
// { BufferedReader In; //~v107R~
{ // ~v107I~
	public static final int NFC_SERVER = 1; // identify connection
											// type//~1A6BI~//~1A8oI~
	public static final int NFC_CLIENT = 2; // ~1A6BI~//~1A8oI~
	private static final String CONTYPE_PREFIX = ";"; // ~1A6BI~
	public static final String CONN_TITLE_IP = "IP "; // ~1A8iI~
	public static final String CONN_TITLE_WD = "WD "; // ~1A8iI~
	public static final String CONN_TITLE_BT = "BT "; // ~1A8iI~
	public static final String CONN_TITLE_NFC = "NFC "; // ~1A8iI~
	// public static int test=2; //~@@@9R~//~1A6BR~
	// protected BufferedReader In; //~v107I~//~1A8cR~
	// protected PrintWriter Out; //~v107I~//~1A8cR~
	Socket Server;
	// public PartnerThread PT; //~v107I~//~@@@2R~//~1A8cR~
	// public PartnerGoFrame PGF; //~1A8cR~
	// boolean Serving; //~1A8cR~
	// boolean Block; //~1A8cR~
	// String Dir; //~1A8cR~
	// String PartnerName; //~@@@2I~//~1A8cR~
	boolean GameRequester; // ~@@@2R~

	// private OutputStream OS; //~@@@9R~//~1A6BR~
	// public static InputStream IS; //~@@@9I~//~1A6BR~
	// public int connectionType; //~1A6BI~//~1Ae4R~
	public PartnerFrame(String name, boolean serving) // ~1A8cI~
	{ // ~1A8cI~
		super(name, serving); // ~1A8cI~
		AG.aPartnerFrameIP = this; // ~1A8oI~
	} // ~1A8cI~

	public PartnerFrame(String name, boolean serving, int Pconnectiontype)// ~1A6BI~
	{ // ~1A6BI~
	// this(name,serving); //~1A6BI~//+1AecR~
		super(
				name,
				serving, // +1AecI~
				(Pconnectiontype == NFC_SERVER || Pconnectiontype == NFC_CLIENT ? CONN_TITLE_NFC_WD
						: CONN_TITLE_WD)// +1AecI~
		); // +1AecI~
		AG.aPartnerFrameIP = this; // ~1A8cI~
		if (Dump.Y)
			Dump.println("jagoclient:@@PartnerFrame@@"); // ~1A6BI~
		connectionType = Pconnectiontype; // ~1A6BI~
	} // ~1A6BI~

	@Override
	// ~1A8cI~
	public boolean connect(String s, int p) {
		if (Dump.Y)
			Dump.println("wifidirect:PartnerFrame:connect Starting partner connection"); // ~@@@1R~//~1A8cI~
		try {
			Server = new Socket(s, p);
			Server.setTcpNoDelay(true); // ~v101R~
			Out = new PrintWriter(
					new DataOutputStream(Server.getOutputStream()), true);
			In = new BufferedReader(new InputStreamReader(new DataInputStream(
					Server.getInputStream())));
		} catch (Exception e) { // ~v101R~
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:connect cause="
						+ e.getCause() + ",msg=" + e.getMessage());// ~1B1jR~//~1A8cI~
			Dump.println(e, "wifidirect.PartnerFrame:connect " + s + ",port="
					+ p); // ~1B1jR~//~1A8cI~
			return false; // ~v101I~
		}
		if (Dump.Y)
			Dump.println("wifidirect.PartnetFrame client after open");// ~1Ac0I~
			// Utils.chkNetwork(); //@@@@test //~1Ac0I~//~1Ac3R~
			// PT=new PartnerThread(In,Out,Input,Output,this); //~v108R~
		PT = new PartnerThread(In, Out, null, null, this); // ~v108I~
		PT.start();
		out("@@name " + // ~@@@2R~
				// Global.getParameter("yourname","No Name")); //~@@@2R~
				// AG.YourName); //~@@@2I~//~1A6BR~
				AG.YourName + CONTYPE_PREFIX + connectionType); // ~1A6BR~
		// show(); //~@@@2R~
		AG.RemoteStatus = AG.RS_IPCONNECTED; // ~@@@2I~
		getHostAddr(Server); // ~@@@2I~
		dismissWaitingDialog(); // ~1A84I~//~1A90I~
		doAction(AG.resource.getString(R.string.Game)); // popup
														// GameQuestion//~@@@2I~
		return true;
	}

	@Override
	// ~1A8cI~
	public void open(Socket server) {
		if (Dump.Y)
			Dump.println("wifidirect.PartnerFrame:open Starting partner server"); // ~@@@1R~//~1A8cI~
		Server = server;
		try { // ~v101R~
			Server.setTcpNoDelay(true); // ~v101R~
			Out = new PrintWriter( // ~v101I~
					new DataOutputStream(Server.getOutputStream()), true);
			In = new BufferedReader(new InputStreamReader(new DataInputStream(
					Server.getInputStream())));
		} catch (Exception e) {
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:open ---> no connection"); // ~@@@1R~//~1A8cI~
			new Message(this, Global.resourceString("Got_no_Connection_"));
			return;
		}
		if (Dump.Y)
			Dump.println("wifidirect.PartnetFrame server after open");// ~1Ac0I~
			// Utils.chkNetwork(); //@@@@test //~1Ac0I~//~1Ac3R~
			// PT=new PartnerThread(In,Out,Input,Output,this); //~v108R~
		PT = new PartnerThread(In, Out, null, null, this); // ~v108I~//~@@@2R~
		PT.start();
		AG.RemoteStatus = AG.RS_IPCONNECTED; // ~@@@2I~
		getHostAddr(Server); // ~@@@2I~
	}

	public void doclose() { // ~v108I~
		try // ~1Ac0I~
		{ // ~1Ac0I~
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:doclose()"); // ~1A6BI~//~1A8cI~
			out("@@@@end"); // ~@@@2R~
			// Out.close(); //~1Ac0R~
			// if (Dump.Y)
			// Dump.println("wifidirect.PartnerFrame:doclose issue Out.close()");//~1A6BI~//~1A8cI~//~1Ac0R~
			// new CloseConnection(Server,In); //~1Ac0R~
			// super.doclose(); //~1Ac0R~
			URunnable.setRunFunc(this/* URunnableI */, 500/* delay */,
					this/* objparm */, 0/* int parm */);// ~1Ac0I~
		} // ~1Ac0I~
		catch (Exception e) // ~1Ac0I~
		{ // ~1Ac0I~
			Dump.println(e, "wifidirect.partnerFrame:doclose"); // ~1Ac0I~
		} // ~1Ac0I~
	}

	/**
	 * The interpreter for the partner commands (all start with @@).
	 */
	@Override
	// ~1A8cI~
	public void interpret(String s)
	// { if (s.startsWith("@@name")) //~v107R~
	{ // ~v107I~
		if (Dump.Y)
			Dump.println("wifidirect.PartnerFrame:interpret:" + s); // ~v107I~//~1A8cI~
		if (s.startsWith("@@name")) // ~v107I~
		{
			StringParser p = new StringParser(s);
			p.skip("@@name");
			// setTitle(Global.resourceString("Connection_to_")+p.upto('!'));//~1A6BR~
			String name = p.upto(CONTYPE_PREFIX.charAt(0)).trim(); // ~1A6BR~
			PartnerName = name; // ~1A8oI~
			// setTitle(Global.resourceString("Connection_to_")+name);
			// //~1A6BI~//~1A85R~
			p.advance(); // ~1A6BI~
			int ct = p.parseint(); // ~1A6BR~
			setConnectionType(ct); // ~1A6BI~
			setTitle(getConnectionTypeTitleString(ct)
					+ Global.resourceString("Connection_to_") + name);// ~1A8iI~//~1A8oR~
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:interpret setTitle:"
						+ Global.resourceString("Connection_to_") + name);// ~1A6BI~//~1A8cI~//~1A8oR~
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:interpret connectiontype="
						+ connectionType);// ~1A6BI~//~1A8cI~
			out("@@!name " + AG.YourName); // ~1A37I~
		} else // ~1A37I~
		if (s.startsWith("@@!name")) // ~1A37I~
		{ // ~1A37I~
			StringParser p = new StringParser(s); // ~1A37I~
			p.skip("@@!name"); // ~1A37I~
			String pn = p.upto('!').trim(); // ~1A6BI~//~1A8oI~
			myName = Global.getParameter("yourname", "No Name"); // ~1A6BI~//~1A8oI~
			if (!pn.equals(myName)) // same name //~1A6BI~//~1A8oI~
				PartnerName = pn.trim(); // ~1A6BI~//~1A8oI~
			setTitle(getConnectionTypeTitleString(connectionType)
					+ Global.resourceString("Connection_to_") + PartnerName);// ~1A8iI~//~1A8oR~
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:interpret receivedWD:partnername="
						+ PartnerName);// ~1A6BI~//~1A8aR~//~1A8cI~//~1A8oR~
		} // ~1A37I~
		else if (s.startsWith("@@board")) // ~1A8cI~
		{
			if (PGF != null)
				return; // ~1A8cI~
			dismissWaitingDialog(); // ~1A8cI~
			super.interpret(s); // ~1A8cI~
		} // ~1A8cI~
		else
			// ~1A8cI~
			super.interpret(s); // ~1A8cI~
	}

	// *************************************************************** //~@@@2I~
	public void disconnect() // from IPConnection //~@@@2I~
	{ // ~@@@2I~
		if (Dump.Y)
			Dump.println("PartnerFrame Disconnect"); // ~@@@2I~
		if (PGF == null) // not game started //~@@@2I~
		{ // ~@@@2I~
			doclose(); // CloseConnection //~@@@2I~
		} // ~@@@2I~
		else // ~@@@2I~
		if (PT != null && PT.isAlive()) // ~@@@2I~
			resign(); // ~@@@2I~
		else
			// ~@@@2I~
			doclose(); // CloseConnection //~@@@2I~
	} // ~@@@2I~
	// *************************************************************** //~1Ac3R~

	public void disconnect(boolean Punpair) // from IPConnection //~1Ac3R~
	{ // ~1Ac3R~
		if (Dump.Y)
			Dump.println("PartnerFrame Disconnect Punpair=" + Punpair);// ~1Ac3R~
		if (PT != null) // ~1Ac3R~
			((wifidirect.PartnerThread) PT).unpair(Punpair); // ~1Ac3R~
		doclose(); // CloseConnection //~1Ac3R~
	} // ~1Ac3R~
	// *************************************************************** //~@@@2I~

	public static void dismissWaitingDialog() // ~@@@2R~
	{ // ~@@@2I~
		if (Dump.Y)
			Dump.println("PartnerFrame DismissWaitiingDialog");// ~@@@2R~
		ProgDlg.dismiss(); // ~@@@2I~
		// IPConnection.closeDialog(); //~1A6yI~//~1A8iR~
		DialogNFC.closeDialog(); // close DialogNFC if showing //~1A6sI~
	} // ~@@@2I~
	// ***************************************************************
	// //~@@@2I~//~1A8oI~

	private void getHostAddr(Socket Psocket) // ~@@@2I~//~1A8oI~
	{ // ~@@@2I~//~1A8oI~
		AG.RemoteInetAddress = Utils.getRemoteIPAddr(Psocket, null);// ~1A8fI~//~1A8oI~
		AG.LocalInetAddress = Utils.getLocalIPAddr(Psocket, null);// ~1A8fR~//~1A8oI~
		if (Dump.Y)
			Dump.println("wifidirect.PartnerFrame:getHostAddr remote="
					+ AG.RemoteInetAddressLAN + ",local="
					+ AG.LocalInetAddressLAN);// ~1A8fI~//~1A8oI~
	} // ~@@@2I~//~1A8oI~
	// *************************************************************** //~1A8iI~

	private String getConnectionTypeTitleString(int Pct) // ~1A8iI~
	{ // ~1A8iI~
		if (Pct == NFC_CLIENT || Pct == NFC_SERVER) // ~1A8iI~
		// return jagoclient.partner.PartnerFrame.CONN_TITLE_NFC;
		// //~1A8iI~//+1AecR~
			return jagoclient.partner.PartnerFrame.CONN_TITLE_NFC_WD;// +1AecI~
		return jagoclient.partner.PartnerFrame.CONN_TITLE_WD; // ~1A8iI~
	} // ~1A8iI~
	// *************************************************************** //~1Ac0I~

	@Override
	// ~1Ac0I~
	public void runFunc(Object parmObj, int parmInt/* 0 */) // ~1Ac0I~
	{ // ~1Ac0I~
	// PartnerFrame pf=(PartnerFrame)parmObj; //~1Ac0I~
		if (Dump.Y)
			Dump.println("Asgts:PartnerFrame:doclose runFunc");// ~1Ac0I~
		try // ~1Ac0I~
		{ // ~1Ac0I~
			Out.close(); // ~1Ac0I~
			if (Dump.Y)
				Dump.println("wifidirect.PartnerFrame:doclose issue Out.close()");// ~1Ac0I~
			new CloseConnection(Server, In); // ~1Ac0I~
			doclose2(); // do not out @@@@end,CloseConnection close Server and
						// In//~1Ac0I~
		} // ~1Ac0I~
		catch (Exception e) // ~1Ac0I~
		{ // ~1Ac0I~
			Dump.println(e, "wifidirect.partnerFrame:runfunc"); // ~1Ac0I~
		} // ~1Ac0I~
	} // ~1Ac0I~
} // ~1A8iR~
