//*CID://+1Ai1R~:                             update#=  230;       //+1Ai1R~
//******************************************************************************************************************//~v101R~
//1Ai1 2016/12/29 (Bug)unicode of Black/White sign was reversed u2616/u2617(appears if it is visible)//+1Ai1I~
//1Ahc 2016/11/23 onDraw corruption;try specify density same as display//~1AhcI~
//1Aha 2016/11/19 add option trace X for special debug in notrace mode//~1AhaI~
//1Ah0 2015/10/15 1Ad7 2015/07/20 Canvas/UiThread TraceOption was not effective if OptionDialog is opened//~1Ah0R~
//1Abg 2015/06/15 NFCBT:transfer to NFCBT or NFCWD if active session exist//~1AbgI~
//1Ab7 2015/05/03 NFC Bluetooth handover v2                        //~1Ab7I~
//1Ab2 2015/05/04 (BUG)fileNF err when MainOption:FixedFolder and prop.SaveDir=null//~1Ab2I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A8fk2015/03/01 display remote IP address                        //~1A8fI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A85 2015/02/25 close each time partnerframe for IP Connection   //~1A85I~
//1A81 2015/02/24 ANFC is not used now                             //~1A81I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//~1A6AI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6p 2015/02/16 display.getWidth()/getHeight() was deprecated at api13,use getSize(Point)//~1A6pI~
//1A6k 2015/02/15 re-open IPConnection/BTConnection dialog when diconnected when dislog is opened.//~1A6kI~
//1A6c 2015/02/13 Bluetooth;identify paired device and discovered device//~1A6cI~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6aI~
//1A4r 2014/12/05 change savedir to editable                       //~1A4rI~
//1A4m 2014/12/04 memory leak:EndGameQuestion do close then setDialogClosed;set currentDialog to closed frame view(the view has bitmap reference)//~1A4mI~
//1A4j 2014/12/04 memory leak:after LGF closed static AG.currentDialog remains.//~1A4jI~
//                It refers Frame through parentFrame              //~1A4jI~
//1A4h 2014/12/03 catch OutOfMemory                                //~1A4hI~
//1A41 2014/09/19 avoid exceoption msg for help text not found     //~1A41I~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//1A35 2013/04/19 show mark of last moved from position            //~1A35I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A26 2013/03/25 save folder fix option(no dislog popup for save game)//~1A26I~
//1A1k 2013/03/20 default one touch mode                           //~1A1kI~
//1A1j 2013/03/19 change Help file encoding to utf8                //~1A1jI~
//1A13 2013/03/10 1touch option                                    //~1A13R~
//1A12 2013/03/08 Option to diaplay time is for tiomeout=0(if not 0 diaplay timer)//~1A12I~
//1A0k 2013/03/07 coordinate on opposit side                       //~1A0kI~
//1A0c 2013/03/05 mach info in title                               //~1A0cI~
//1A08 2013/03/02 add sound "gameover"                             //~1A08I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//1078:121208 add "menu" to option menu if landscape               //~v107I~
//1077:121208 control greeting by app start counter                //~v107I~
//1075:121207 control dumptrace by manifest debuggable option      //~v107I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//v101:120514 (Axe)android3(honeycomb) tablet has System bar at bottom that hide xe button line with 48pix height//~v101I~
//******************************************************************************************************************//~v101I~
//*Ajago Globals *****                                             //~1107I~
//********************                                             //~1107I~
package com.Asgts;                                                    //~1108R~//~1109R~//~v107R~//~@@@@R~

import java.util.Locale;

import wifidirect.WDANFC;

import android.os.Build;                                           //~vab0R~//~v101I~
import android.os.IBinder;

                                                                   import com.Asgts.awt.Color;//~@@@@R~
import com.Asgts.awt.Component;                                    //~@@@@R~
import com.Asgts.awt.Dialog;                                        //~v107R~//~@@@@R~
import com.Asgts.awt.FileDialog;                                   //~1Ab2R~
import com.Asgts.awt.Font;                                         //~@@@@R~
import com.Asgts.awt.Frame;                                         //~v107R~//~@@@@R~
import com.Asgts.awt.Spinner;
import com.Asgts.awt.Window;                                        //~v107R~//~@@@@R~

import jagoclient.partner.BluetoothConnection;
import jagoclient.partner.IPConnection;
import jagoclient.partner.partner.MsgThread;
//import com.Asgts.jagoclient.partner.PartnerFrame;                  //~@@@@R~//~1A8gR~
import com.Asgts.R;                                               //~1120I~//~v107R~//~@@@@R~
import jagoclient.MainFrameOptions;                                //~1Ad7I~//~1AbgI~
import jagoclient.Dump;
import jagoclient.Go;
import jagoclient.dialogs.SayDialog;
//import jagoclient.igs.ConnectionFrame;                           //~@@@@R~
//import jagoclient.igs.games.GamesFrame;                          //~@@@@R~
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;


class IdTblEntry                                                   //~1120I~
{                                                                  //~1120I~
    String name; int id;                                           //~1120I~
    public IdTblEntry(String Pname,int Pid)                        //~1120I~
    {                                                              //~1120I~
        name=Pname; id=Pid;                                        //~1120I~
    }                                                              //~1120I~
}                                                                  //~1120I~
//**************************                                       //~1120I~
public class AG                                                    //~1107R~
{                                                                  //~1109R~
    public static com.Asgts.awt.Graphics  gBoardImage,gActiveImage;     //@@@X//~1AhaI~
    public static com.Asgts.awt.Canvas  ajagocCanvas;     //@@@X  //~1AhaI~
    public static final int ACTIVITY_REQUEST_ENABLE_BT = 2;        //~1A6aI~
    public static final int ACTIVITY_REQUEST_NFCBEAM   = 3;        //~1A6aI~
                                                                   //~1A6aI~
    public final static String HELP_ENCODING="UTF-8";              //~1A1jI~
    public final static String PKEY_PIECE_TYPE="PieceType";        //~@@@@R~
    public final static String PKEY_BOARD_SIZE="BoardSize";       //~@@@@I~
    public static final String PKEY_OPTIONS="Options";             //~@@@@I~
    public static final String PKEY_YOURNAME="YourName";           //~@@@@I~
    public static final String PKEY_SAVEDIR="SaveDir";             //~1A4rR~
    public static final Color cursorColor=new Color(0x00,0xff,0x20);//~@@@2I~//~@@@@R~
	public static final Color selectedColor=new Color(0x00,0x20,0xff);//~@@@2R~//~@@@@R~
    public static final Color capturedColor=new Color(0xff,0x00,0x00);//~@@@@I~//~@@@2I~//~@@@@I~
    public static final Color lastPosColor=new Color(0xff,0x00,0x80);//~@@@@R~
	public static final Color lastFromColor=new Color(0x60,0x60,0x00);//~1A35R~
                                                                   //~@@@@I~
//    public static final String DEBUGTRACE_CFGKEY  ="debugtrace"; //~@@@@R~
                                                                   //~@@@@I~
	public final static String SshogiVJ="\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d";//~@@@@I~//~v1A0R~
	public final static String SshogiVE="abcdefghi";               //~v1A0R~
	public final static String SshogiVN="123456789";               //~v1A0I~
	public       static String SshogiVJE;                          //~v1A0I~
	public       static String SshogiV;                            //~v1A0I~
	public final static String SshogiH="987654321";                //~@@@@I~
//    public final static String SchessV="87654321";                 //~@@@@I~//~v1A0R~
//    public final static String SchessH="abcdefgh";                 //~@@@@R~//~v1A0R~
    public static final String COMMON_BUTTON_NAME="Button"; //Butonnxx//~@@@@I~
    public static final int BOARDSIZE_SHOGI=9;                     //~@@@@R~
//    public static final int BOARDSIZE_CHESS=8;                     //~@@@@I~//~v1A0R~
    public static final int EXTRA_MOVE=1;      //move per extratime//~@@@@I~
//    public static       int propBoardSize;                         //~@@@@R~//~v1A0R~
    public static       Color chessBoardBlack=new Color(0x90,0x60,0x40);//~@@@@R~
    public static       Color chessBoardWhite=new Color(0xE6,0xD4,0xAE);//~@@@@I~
                                                                   //~@@@@I~
    public static int Options;                                     //~@@@@I~
//  public static final int OPTIONS_TIMER_IN_TITLE      	=0x0001;//~@@@@R~//~1A0cR~
//  public static final int OPTIONS_BIG_TIMER           	=0x0002;//~@@@@R~//~1A12R~
    public static final int OPTIONS_NOSOUND             	=0x0004;//~@@@@R~
    public static final int OPTIONS_BEEP_ONLY           	=0x0008;//~@@@@R~
    public static final int OPTIONS_TIMER_WARNING       	=0x0010;//~@@@@R~
    public static final int OPTIONS_SHOW_LAST           	=0x0020;//~@@@@R~
    public static final int OPTIONS_COORDINATE          	=0x0040;//~@@@@R~
    public static final int OPTIONS_TRACE               	=0x0080;//~@@@@R~
    public static final int OPTIONS_GOFRAME_CLOSE_CONFIRM 	=0x0100;//~@@@@I~
    public static final int OPTIONS_DIGITALCOORDINATE     	=0x0200;//~v1A0I~
    public static final int OPTIONS_NO_GAMEOVER_SOUND     	=0x0400;//~1A08I~
    public static final int OPTIONS_AUTO_CHECKMATE        	=0x0800;//~1A0cI~
    public static final int OPTIONS_1TOUCH_FB             	=0x1000;  //1touch on freeboard//~1A13R~
    public static final int OPTIONS_1TOUCH                	=0x2000;  //1touch on freeboard//~1A13I~
    public static final int OPTIONS_FIXSAVEDIR            	=0x4000;  //1touch on freeboard//~1A26I~
    public static final int OPTIONS_TRACE_CANVAS           	=0x040000;//~1A6AI~
    public static final int OPTIONS_TRACE_UITHREAD         	=0x080000;//~1A6AI~
    public static final int OPTIONS_TRACE_X                	=0x100000;//~1AhaI~
    public static String YourName,LocalOpponentName;                                                               //~@@@@I~
    public static String SaveDir;                                  //~1A4rI~
    public static String language;                                 //~1531I~//~@@@@I~
    public static String Glocale;                                  //~@@@@I~
    public static boolean isLangJP;                                //~@@@@I~
 	public static boolean isDebuggable;                            //~v107I~
//  public static int testdump=0;	//@@@@test                     //~1AhcR~
 	public static Activity activity;                                 //~1109I~//~1111R~
 	public static Context context;                                 //~1111I~
// 	public static PartnerFrame aPartnerFrame;                      //~@@@@I~//~1A8gR~
 	public static IPConnection aIPConnection;                      //~1A6kI~
 	public static BluetoothConnection aBTConnection;               //~1A6kI~
//  public static jagoclient.partner.PartnerFrame aPartnerFrameIP; //~@@@@I~//~1A85R~
    public static jagoclient.partner.PartnerFrame aPartnerFrame;   //~1A8gI~
    public static wifidirect.PartnerFrame aPartnerFrameIP;         //~1A8cI~
    public static int RemoteStatus;                                //~@@@@R~
    public static int RemoteStatusAccept;                          //~@@@@I~
    public static final int RS_IP=1;                               //~@@@@R~
    public static final int RS_BT=2;                               //~@@@@I~
    public static final int RS_IPLISTENING=RS_IP+4;                //~@@@@R~
    public static final int RS_BTLISTENING_SECURE=RS_BT+4;         //~@@@@R~
    public static final int RS_IPCONNECTED=RS_IP+8;                 //~@@@@R~
    public static final int RS_BTCONNECTED=RS_BT+8;                //~@@@@R~
    public static final int RS_BTLISTENING_INSECURE=RS_BT+16;      //~@@@@I~
    public static final int DEFAULT_SERVER_PORT=6974;              //~@@@@I~//~1A08R~
    public static String RemoteInetAddress;                        //~@@@@I~
    public static String LocalInetAddress;                         //~1A6sI~
    public static String RemoteInetAddressLAN;                     //~1A8fI~
    public static String LocalInetAddressLAN;                      //~1A8fI~
    public static String PartnerName;                              //~@@@@I~
    public static String RemoteDeviceName;                         //~@@@@I~
    public static String LocalDeviceName;                          //~@@@@I~
	public static AMain aMain;                             //~1107R~//~@@@@R~
	public static AMenu aMenu;                             //~1107I~//~@@@@R~
	public static AView aView;                                //~1111I~//~@@@@R~
	public static Resources  resource;                              //~1109I~
	public static int       component;                             //~1109I~
	public static Go        go;                                    //~1109I~
//  public static int scrW,scrH;                                   //~1428R~//~v107R~
	public static float dip2pix;                                   //~1428I~
	public static float sp2pix;                                    //~@@@@I~
//  public static boolean landscape;                               //~1428R~//~v107R~
    public static final String SD_go_cfg="go.cfg.save";             //~1308R~
    public static Frame mainframe;                                 //~1111I~
    public static LayoutInflater inflater;                          //~1113I~
//  public static Canvas    androidCanvasMain;                     //~1A40R~
//    public static boolean   appStart;                              //~1428R~//~@@@@R~
    public static boolean   portrait;                              //~1428R~
    public static String    appName;                               //~1428R~
    public static String    pkgName;                               //~1A6aI~
    public static String    appVersion;                            //~1506I~
    public static int       scrWidth,scrHeight;                    //~1428R~
    private static View      currentLayout;//~1120I~               //~1428R~
    public static int       currentLayoutId;                       //~1428R~
    private static int       currentLayoutLabelSeqNo;              //~1428R~
    private static int       currentLayoutTextFieldSeqNo;          //~1428R~
    private static int       currentLayoutTextAreaSeqNo;           //~1428R~
    private static int       currentLayoutButtonSeqNo;             //~1428R~
    private static int       currentLayoutCheckBoxSeqNo;           //~1428R~
//    private static int       currentLayoutSpinnerSeqNo;            //~1428R~//~1A30R~
    private static int       currentLayoutSeekBarSeqNo;            //~1428R~
    public static Dialog    currentDialog;                    //~1215I~//~1428R~
    public static Frame     currentFrame;                          //~1428R~
    public static int       mainframeTag;                          //~1428R~
    public static boolean currentIsDialog;                         //~1428R~
                                                                   //~1211I~
    public static int       listViewRowId=R.layout.textrowlist;       //~1211I~//~1219R~
    public static int       viewerRowId  =R.layout.textrowviewer;  //~1219R~
    public static int       listViewRowIdMultipleChoice=android.R.layout.simple_list_item_multiple_choice;//~1211I~
    public static final int TIME_LONGPRESS=1000;//milliSeconds     //~1412I~
    public static int currentTabLayoutId;                          //~1428R~
    public static int titleBarTop;                                 //~1428R~
    public static int titleBarBottom;                              //~1428R~
    public static ABT aBT;                                 //~v107R~//~@@@@R~
//  public static WDANFC aWDANFC;                                  //~1A6aI~//~1A81R~
//  public static ANFC aANFC;                                      //~1A6aI~//~1A81R~
    public static final String PKEY_STARTUPCTR="startupctr";       //~v107I~
    public static int startupCtr;                                  //~v107I~
    public static int activeSessionType;                           //~1A8gI~
    public static final int AST_IP=1;                              //~1A8gI~
    public static final int AST_WD=2;                              //~1A8gI~
    public static final int AST_BT=3;                              //~1A8gI~
    public static String BlackSign;                          //~v1A0R~
    public static String WhiteSign;                          //~v1A0R~
    public static String Promoted;                                 //~v1A0R~
    public static String Move1stSign,Move2ndSign;                  //~v1A0I~
    public static String BlackName,WhiteName;                      //~@@@@I~
    public static MsgThread msgThread;                             //~@@@@R~
    public static SayDialog sayDialog;                             //~@@@@I~
    public static boolean smallButton;                             //~@@@@R~
    public static int smallViewHeight;                         //~@@@@I~
    public static int smallImageHeight;	//for captured List        //~@@@@I~
    public static int displayDensity;                              //~1AhcI~
    public static boolean screenDencityMdpi;                       //~1A40I~
    public static boolean screenDencityMdpiSmallH,screenDencityMdpiSmallV;//~1A40R~
    public static boolean layoutMdpi;                              //~1A6cI~
    public static int smallTextSize;                               //~@@@@I~
//  public static Font smallFont;                                  //~@@@@I~//~1A40R~
    public static ProgDlg progDlg;                                       //~@@@@M~
	public static String[] pieceName;                               //~v1A0I~
	public static String[] pieceNameHandicap;                      //~v1A0I~
	public static boolean tryHelpFileOpen;                         //~1A41R~
    public static  boolean swNFCBT=true;   //support NFC Bluetooth handover//~1Ab7I~
    public static  boolean swSecureNFCBT;  //current active NFCBT session is secure//~1AbgI~
    public static  boolean isNFCBT; 		//BT is by NFC         //~1AbgI~
                                                                   //~1120I~
//****************                                                 //~1109I~
	public  static final String ListServer ="ListView_Server";     //~1120I~
	public  static final String ListPartner="ListView_Partner";    //~1120I~
//****************                                                 //~1122I~
//    public  static final String tabName_ServerConnections   ="Server Connections";//~1122R~//~@@@@R~
//    public  static final String tabName_PartnerConnections  ="Partner Connections";//~1122R~//~@@@@R~
//    public  static final int    TabLayoutID_Servers     =R.id.MainFrameTabLayout_ServerConnections;//~1122I~//~@@@@R~
//    public  static final int    TabLayoutID_Partners    =R.id.MainFrameTabLayout_PartnerConnections;//~1122R~//~@@@@R~
//****************                                                 //~1120I~
//    public  static final String layout_ServerConnections   ="ServerConnections";//~1120I~//~@@@@R~
//    public  static final String layout_PartnerConnections  ="PartnerConnections";//~1120I~//~@@@@R~
//    public  static final String layout_SingleServer        ="SingleServer";//~1120I~//~@@@@R~
//    public  static final String layout_GamesFrame          ="GamesFrame";//~1120I~//~@@@@R~
//    public  static final String layout_ConnectionFrame     ="ConnectionFrame";//~1120I~//~@@@@R~
//    public  static final String layout_ConnectedGoFrame    ="ConnectedGoFrame";//~1121I~//~@@@@R~
	public  static final String layout_MainFrame           ="MainFrame";//~1125I~
                                                                   //~1121I~
    public  static final int frameId_MainFrame             =R.layout.mainframe; //initial//~1125R~
//    public  static final int frameId_ServerConnections     =R.layout.mainsl;//~1121I~//~@@@@R~
//    public  static final int frameId_PartnerConnections    =R.layout.mainpl;//~1121I~//~@@@@R~
//    public  static final int frameId_SingleServer          =R.layout.mainsv;//~1121I~//~@@@@R~
//    public  static final int frameId_GamesFrame            =R.layout.gamesframe;//~1121I~//~@@@@R~
//    public  static final int frameId_ConnectionFrame       =R.layout.connectionframe;//~1121I~//~@@@@R~
    public  static final int frameId_ConnectedGoFrame      =R.layout.connectedgoframe;//~1121I~
//    public  static final int frameId_WhoFrame              =R.layout.whoframe;//~1306I~//~@@@@R~
    public  static final int frameId_MessageDialog         =R.layout.messagedialog;//~1310I~
    public  static final int frameId_SayDialog             =R.layout.saydialog;//~1311I~
//  public  static final int frameId_PartnerFrame          =R.layout.partnerframe;//~1318I~//~1A40R~
//    public  static final int frameId_LocalViewer           =R.layout.localviewer;//~1323I~//~@@@@R~
//    public  static final int frameId_Help                  =R.layout.help;//~1326I~
//    public  static final int frameId_MessageFilter         =R.layout.messagefilter;//~1331I~//~@@@@R~
//    public  static final int frameId_GMPConnection         =R.layout.gmpconnection;//~1404I~//~@@@@R~
//    public  static final int frameId_OpenPartners          =R.layout.openpartners;//~1405I~//~@@@@R~
    public  static final int frameId_PartnerSendQuestion   =R.layout.partnersendquestion;//~1405I~
                                                                   //~1216I~
                                                                   //~1214I~
    public  static final int dialogId_Message              =R.layout.message;//~1214I~//~1310R~
    public  static final int dialogId_GetParameter         =R.layout.getparameter;//~1215R~//~1310R~
//    public  static final int dialogId_Password             =R.layout.password;//~1125I~//~1215R~//~1310R~//~@@@@R~
    public  static final int dialogId_Information          =R.layout.information;//~1307I~//~1310R~
    public  static final int dialogId_BluetoothConnection  =R.layout.bluetooth;//~@@@@I~
    public  static final int dialogId_MessageDialog        =R.layout.messagedialog;//~1310R~
    public  static final int dialogId_MatchQuestion        =R.layout.matchquestion;//~1310R~
    public  static final int dialogId_MatchDialog          =R.layout.matchdialog;//~1310I~
    public  static final int dialogId_TellQuestion         =R.layout.tellquestion;//~1311I~
//    public  static final int dialogId_EditConnection       =R.layout.editconnection;//~1314I~//~@@@@R~
//    public  static final int dialogId_EditPartner          =R.layout.editpartner;//~1318I~//~@@@@R~
//    public  static final int dialogId_FileDialog           =R.layout.filedialog;//~1326I~//~@@@@R~
    public  static final int dialogId_HelpDialog           =R.layout.helpdialog;//~1327I~
//    public  static final int dialogId_AdvancedOptionEdit   =R.layout.advancedoptionedit;                                                              //~1125I~//~@@@@R~
    public  static final int dialogId_Question             =R.layout.question;//~1329I~
//    public  static final int dialogId_FunctionKeys         =R.layout.functionkeys;//~1330I~//~@@@@R~
//    public  static final int dialogId_EditFilter           =R.layout.editfilter;//~1331I~//~@@@@R~
//    public  static final int dialogId_EditButtons          =R.layout.editbuttons;//~1331I~//~@@@@R~
//    public  static final int dialogId_RelayServer          =R.layout.relayserver;//~1331I~//~@@@@R~
//    public  static final int dialogId_FontSize             =R.layout.fontsize;//~1331I~//~@@@@R~
//    public  static final int dialogId_GlobalGray           =R.layout.globalgray;//~1331I~//~@@@@R~
//    public  static final int dialogId_ColorEdit            =R.layout.editcolor;//~1331I~//~@@@@R~
//    public  static final int dialogId_MailDialog           =R.layout.maildialog;//~1404I~//~@@@@R~
//    public  static final int dialogId_GMPWait              =R.layout.gmpwait;//~1404I~//~@@@@R~
    public  static final int dialogId_Warning              =R.layout.warning;//~1404I~
//    public  static final int dialogId_GameQuestion         =R.layout.gamequestion;//~1405R~//~@@@@R~
//    public  static final int dialogId_BoardQuestion        =R.layout.boardquestion;//~1405I~//~@@@@R~
    public  static final int dialogId_GameInformation      =R.layout.gameinformation;//~1405I~
//    public  static final int dialogId_TextMark             =R.layout.textmark;//~1405I~//~@@@@R~
    public  static final int dialogId_SendQuestion         =R.layout.sendquestion;//~1405I~
    public  static final int dialogId_PartnerSendQuestion  =R.layout.partnersendquestion;//~1405I~
                                                                   //~1331I~
//    public  static final int menuLayoutId_WhoPopup           =R.menu.whopopup;//~1307I~//~@@@@R~
//    public  static final int menuLayoutId_GamesPopup         =R.menu.gamespopup;//~1307I~//~@@@@R~
                                                                   //~1121I~
	private static final IdTblEntry[] layouttbl={
//                     new IdTblEntry(layout_MainFrame,             frameId_MainFrame),//~1125I~//~@@@@R~
//                     new IdTblEntry(layout_ServerConnections,     frameId_ServerConnections),//~1121R~//~@@@@R~
//                     new IdTblEntry(layout_PartnerConnections,    frameId_PartnerConnections),//~1121R~//~@@@@R~
//                     new IdTblEntry(layout_SingleServer,          frameId_SingleServer),//~1121R~//~@@@@R~
//  				 new IdTblEntry(layout_GamesFrame,            frameId_GamesFrame),//~1121R~//~1217R~
//  				 new IdTblEntry(layout_ConnectionFrame,       frameId_ConnectionFrame),//~1121R~//~1217R~
//                     new IdTblEntry(layout_ConnectedGoFrame,      frameId_ConnectedGoFrame),//~1121R~//~1217R~//~@@@@R~
    				 new IdTblEntry("dummy",-1)//~1120I~
                     };                                             //~1120I~
//*ViewID                                                          //~1121I~
                                                                   //~1216I~
	public  static final int    viewId_BigTimerLabel  =R.id.BigTimer;//~1216R~
	public  static final int    viewId_BoardPanel     =R.id.BoardPanel;//~1121I~//~1217I~
//  public  static final int    viewId_ExampleCanvas  =R.id.ExampleCanvas;//not used//~1331I~//~1A40R~
//  public  static final int    viewId_ListView       =R.id.ListView;//~1219I~//~1A40R~
    public  static final int    viewId_Lister         =R.id.Lister;                                                               //~1216I~
//    public  static final int    viewId_IconBar        =R.id.IconBar;//~1322I~
//    public  static final int    viewId_IconBar1       =R.id.IconBar1;//~1324I~
//    public  static final int    viewId_IconBar2       =R.id.IconBar2;//~1324I~
//    public  static final int    viewId_NavigationPanel=R.id.NavigationPanel;//~1415I~
//    public  static final int    viewId_SeekBarRed     =R.id.SeekBar1;//~1401I~//~v1A0R~
//    public  static final int    viewId_SeekBarGreen   =R.id.SeekBar2;//~1401I~//~v1A0R~
//    public  static final int    viewId_SeekBarBlue    =R.id.SeekBar3;//~1401I~//~v1A0R~
//    public  static final int    viewId_ContainerRed   =R.id.ContainerRed;//~1401I~//~v1A0R~
//    public  static final int    viewId_ContainerGreen =R.id.ContainerGreen;//~1401I~//~v1A0R~
//    public  static final int    viewId_ContainerBlue  =R.id.ContainerBlue;//~1401I~//~v1A0R~
//  public  static final int    viewId_ContainerFontSizeField=R.id.ContainerFontSizeField;//del:not used//~1401I~//~1A40R~
	public  static final int    viewId_DialogBackground=R.id.DialogBackground;//~1404I~
//	public  static final int    viewId_Comment        =R.id.Comment;//~1416I~
//	public  static final int    viewId_AllComments    =R.id.AllComments;//~1416I~//~@@@@R~
	public  static final int    viewId_SetStone       =R.id.SetStone;//~1424I~//~@@@@R~

                                                                   //~1413I~
                                                                   //~1404I~
	private static final IdTblEntry[] viewtbl={  //by actionName   //~1124I~
                                                                   //~1124I~
//					new IdTblEntry("Observe"       ,              R.id.Observe    ),//~1124I~
//					new IdTblEntry("Peek"          ,              R.id.Peek       ),//~1124I~
//					new IdTblEntry("Status"        ,              R.id.Status     ),//~1124I~
					new IdTblEntry("Information"   ,              R.id.Information),//~1125I~//~1216R~//~1307R~

					new IdTblEntry("Viewer"        ,              R.id.Viewer     ),//~1215I~
					new IdTblEntry("Lister"        ,              R.id.Lister     ),//~1220I~
					new IdTblEntry("SetStone"        ,            AG.viewId_SetStone),//~1424I~

                                                                   //~1328I~
    //Iconbar                                                      //~1322I~
//                    new IdTblEntry("undo"                ,        R.id.undo       ),//~1322R~//~@@@@R~
//					new IdTblEntry("sendforward"         ,        R.id.sendforward),//~1322R~
//                    new IdTblEntry("allback"             ,        R.id.allback    ),//~1322R~//~@@@@R~
//                    new IdTblEntry("fastback"            ,        R.id.fastback   ),//~1322R~//~@@@@R~
//                    new IdTblEntry("back"                ,        R.id.back       ),//~1322R~//~@@@@R~
//                    new IdTblEntry("forward"             ,        R.id.forward    ),//~1322R~//~@@@@R~
//                    new IdTblEntry("fastforward"         ,        R.id.fastforward),//~1322R~//~@@@@R~
//                    new IdTblEntry("allforward"          ,        R.id.allforward ),//~1322R~//~@@@@R~
//                    new IdTblEntry("variationback"       ,        R.id.variationback),//~1322R~//~@@@@R~
//                    new IdTblEntry("variationstart"      ,        R.id.variationstart),//~1322R~//~@@@@R~
//                    new IdTblEntry("variationforward"    ,        R.id.variationforward),//~1322R~//~@@@@R~
//                    new IdTblEntry("main"                ,        R.id.main       ),//~1322R~//~@@@@R~
//                    new IdTblEntry("mainend"             ,        R.id.mainend    ),//~1322R~//~@@@@R~
//					new IdTblEntry("send"                ,        R.id.send       ),//~1322R~
//                    new IdTblEntry("mark"                ,        R.id.mark       ),//~1322R~//~@@@@R~
//                    new IdTblEntry("square"              ,        R.id.square     ),//~1322R~//~@@@@R~
//                    new IdTblEntry("triangle"            ,        R.id.triangle   ),//~1322R~//~@@@@R~
//                    new IdTblEntry("circle"              ,        R.id.circle     ),//~1322R~//~@@@@R~
//                    new IdTblEntry("letter"              ,        R.id.letter     ),//~1322R~//~@@@@R~
//                    new IdTblEntry("text"                ,        R.id.text       ),//~1322R~//~@@@@R~
//                    new IdTblEntry("black"               ,        R.id.black      ),//~1322R~//~@@@@R~
//                    new IdTblEntry("white"               ,        R.id.white      ),//~1322R~//~@@@@R~
//                    new IdTblEntry("setblack"            ,        R.id.setblack   ),//~1322R~//~@@@@R~
//                    new IdTblEntry("setwhite"            ,        R.id.setwhite   ),//~1323R~//~@@@@R~
//                    new IdTblEntry("delete"              ,        R.id.delete     ),//~1322R~//~@@@@R~
//                    new IdTblEntry("deletemarks"         ,        R.id.deletemarks),//~1323I~//~@@@@R~
//                    new IdTblEntry("play"                ,        R.id.play),//~1323I~//~@@@@R~
                                                                   //~1322I~
    				new IdTblEntry("dummy",-1)                     //~1124I~
                     };                                            //~1124I~
//*                                                                //~v101I~
    public static int bottomSpaceHeight;                           //~v101I~
    public static final int SYSTEMBAR_HEIGHT=48;                   //~v101I~
	public static String PREFKEY_BOTTOMSPACE_HIGHT="BottomSpaceHeight";//~v101I~
    public static int osVersion;                                   //~vab0I~//~v101I~
    public static final int HONEYCOMB=11; //android3.0 (GINGERBREAD=9)//~vab0I~//~v101I~
    public static final int HONEYCOMB_MR1=12; //android3.1         //~1A4hI~
    public static final int HONEYCOMB_MR2=13; //android3.2         //~1A6pI~
    public static final int ICE_CREAM_SANDWICH=14; //android4.0    //~vab0I~//~v101I~
    public static final int LOLLIPOP=21; //android5.0.1 only support PIE(Position Independent Executable)//~1Ah0I~
//************************************                                                 //~1402I~//~@@@@R~
//*static to be creaded at create                                  //~@@@@I~
//************************************                             //~@@@@I~
    public static int       status=0;                              //~1212I~//~@@@@M~
    public static final int STATUS_MAINFRAME_OPEN=1;               //~1212I~//~@@@@M~
    public static final int STATUS_STOPFINISH=9;                   //~@@@@I~
//    public static boolean   Utils_stopFinish;   //Utils                                 //~1309I~//~@@@@R~
    public static int Board_SpieceImageSize;                       //~@@@@I~
//************************************                             //~@@@@I~
	public static void init(AMain Pmain)                        //~1402I~//~v107R~//~@@@@R~
    {                                                              //~1402I~
//******************************************************************************//~@@@@R~
//*recreate case;need clear static                                 //~@@@@R~
//* static field is cleared when class is re-loaded at restart after finish()//~@@@@I~
//* else it should be initialized                                  //~@@@@I~
//******************************************************************************//~@@@@R~
    	status=0;	//for create() after finsh() even process active//~@@@@I~
    	currentIsDialog=false;                                     //~@@@@I~
//        Utils_stopFinish=false;                                  //~@@@@R~
//******************************************************           //~@@@@I~
    	osVersion=Build.VERSION.SDK_INT;                //~vab0I~  //~v101I~
        aMain=Pmain;                                            //~1109I~//~1329R~//~1402I~//~@@@@R~
        activity=(Activity)Pmain;                                //~1402I~//~@@@@R~
        context=(Context)Pmain;                                  //~1402I~//~@@@@R~
        isDebuggable=Utils.isDebuggable(context);             //~v107I~//~@@@@R~
        startupCtr=Prop.getPreference(PKEY_STARTUPCTR,0);    //~v107I~//~@@@@R~
        Prop.putPreference(PKEY_STARTUPCTR,startupCtr+1);    //~v107I~//~@@@@R~
        resource=Pmain.getResources();                                //~1109I~//~1329R~//~1402I~//~@@@@R~
        inflater=Pmain.getLayoutInflater();                           //~1113I~//~1329R~//~1402I~//~@@@@R~
		appName=context.getText(R.string.app_name).toString();     //~1402I~
		pkgName=context.getPackageName();                          //~1A6aI~
		appVersion=context.getText(R.string.Version).toString();   //~1506I~
                                                                   //~v101I~
        if (osVersion>=HONEYCOMB && osVersion<ICE_CREAM_SANDWICH)  //android3 api11-13//~vab0R~//~v101I~
        	bottomSpaceHeight=SYSTEMBAR_HEIGHT;                    //~vab0I~//~v101I~
    	mainThread=Thread.currentThread();                         //~@@@@I~
        Locale locale=Locale.getDefault();                         //~@@@@I~
        Glocale=locale.toString();	//ja_JP                        //~1820R~//~@@@@I~
        language=locale.getLanguage();   //ja(Locale.JAPANESE) or ja_JP(Locale.JAPAN)//~1531R~//~@@@@I~
        isLangJP=language.substring(0,2).equals(Locale.JAPANESE.getLanguage());  //~@@@@I~
	    Options=Prop.getPreference(PKEY_OPTIONS,                   //~@@@@R~
                0                                                  //~@@@@I~
//  			|OPTIONS_BIG_TIMER                                 //~@@@@I~//~1A12R~
    			|OPTIONS_SHOW_LAST                                 //~@@@@I~
    			|OPTIONS_COORDINATE                                //~@@@@I~
    			|OPTIONS_1TOUCH                                    //~1A1kI~
    			|OPTIONS_FIXSAVEDIR                                //~1A26I~
                );                                                 //~@@@@R~
        MainFrameOptions.initialOptions();                         //~1Ad7I~//~1AbgI~
	    YourName=Prop.getPreference(PKEY_YOURNAME,resource.getString(R.string.DefaultYourName));//~@@@@I~
        SaveDir=Prop.getPreference(PKEY_SAVEDIR,"");               //~1A4rI~
        if (SaveDir.equals(""))                                    //~1Ab2I~
            SaveDir=FileDialog.getSaveDir();                       //~1Ab2I~
	    LocalOpponentName=resource.getString(R.string.LocalOpponentName);//~@@@@I~
//        BlackSign=resource.getString(R.string.BlackSign);          //~@@@@R~//~v1A0R~
//        WhiteSign=resource.getString(R.string.WhiteSign);          //~@@@@R~//~v1A0R~
		setBWSign();                                               //~v1A0R~
        BlackName=resource.getString(R.string.Black);              //~@@@@I~
        WhiteName=resource.getString(R.string.White);              //~@@@@I~
        Move1stSign=resource.getString(R.string.Move1st);          //~@@@@I~
        Move2ndSign=resource.getString(R.string.Move2nd);          //~@@@@I~
		pieceName=resource.getStringArray(R.array.PieceName);      //~v1A0I~
		pieceNameHandicap=resource.getStringArray(R.array.PieceNameHandicap);//~v1A0I~
        Promoted=resource.getString(R.string.Promoted);            //~v1A0I~
        if (isLangJP)                                              //~v1A0I~
			SshogiVJE=SshogiVJ; //japanese rank                    //~v1A0I~
        else                                                       //~v1A0I~
			SshogiVJE=SshogiVE; //letter rank                      //~v1A0I~
		if ((Options & OPTIONS_DIGITALCOORDINATE)!=0)         //~1A0kI~
            AG.SshogiV=AG.SshogiVN;	//digital rank                 //~1A0kI~
		else                                                       //~1A0kI~
			SshogiV=SshogiVJE;                                     //~1A0kR~
        screenDencityMdpi=resource.getDisplayMetrics().density==1.0;//~1A40I~
        Spinner.setLayout();                                       //~1A40I~
    }                                                              //~1402I~
//****************                                                 //~v1A0I~
	private static void setBWSign()                                //~v1A0R~
    {                                                              //~v1A0I~
		BlackSign=resource.getString(R.string.ColorSignBlack);//default//~v1A0R~
		WhiteSign=resource.getString(R.string.ColorSignWhite);     //~v1A0R~
    }                                                              //~v1A0I~
//****************                                                 //~v1A0I~
	public static void setBWSign(TextView Pview)                  //~v1A0I~
    {                                                              //~v1A0I~
        if (Utils.canDisplay(Pview,0x2616))                         //~v1A0I~
        {                                                          //~v1A0I~
//          BlackSign=new String(new int[]{0x2616},0,1);           //+1Ai1R~
//          WhiteSign=new String(new int[]{0x2617},0,1);           //+1Ai1R~
            BlackSign=new String(new int[]{0x2617},0,1);           //+1Ai1I~
            WhiteSign=new String(new int[]{0x2616},0,1);           //+1Ai1I~
        }                                                          //~v1A0I~
        else                                                       //~v1A0I~
        if (Utils.canDisplay(Pview,0x25b2))    //triangle           //~v1A0I~
        {                                                          //~v1A0I~
            BlackSign=new String(new int[]{0x25b2},0,1);                          //~v1A0I~
            WhiteSign=new String(new int[]{0x25b3},0,1);                          //~v1A0I~
        }                                                          //~v1A0I~
    }                                                              //~v1A0I~
//****************                                                 //~1402I~
	public static int findLayoutIdByName(String Pname)               //~1120I~//~1125R~
    {                                                              //~1120I~
    	for (int ii=0;ii<layouttbl.length;ii++)                      //~1120I~
    		if (Pname.equals(layouttbl[ii].name))                   //~1120I~
            {                                                      //~1120I~
    			if (Dump.Y) Dump.println(Pname+" id="+Integer.toString(layouttbl[ii].id,16));//~1126R~//~1506R~
    			return layouttbl[ii].id;                             //~1120I~
            }                                                      //~1120I~
        if (Dump.Y) Dump.println(Pname+" not found(LayoutID)");    //~1506R~
    	return -1;
        //out of bound                                 //~1120I~
    }                                                              //~1120I~
//****************                                                 //~1213I~
	private static final IdTblEntry[] icontbl={                    //~1213I~//~1328M~
					new IdTblEntry("ijago.gif"     ,              R.drawable.ijago),//~1213I~//~1328M~
//                    new IdTblEntry("iboard.gif"    ,              R.drawable.iboard),//~1213I~//~1328M~//~v1A0R~
					new IdTblEntry("ihelp.gif"     ,              R.drawable.ihelp),//~1213I~//~1328M~
//                    new IdTblEntry("iconn.gif"     ,              R.drawable.iconn),//~1213I~//~1328M~//~v1A0R~
//                    new IdTblEntry("iwho.gif"      ,              R.drawable.iwho),//~1213I~//~1328M~//~v1A0R~
					new IdTblEntry("igames.gif"    ,              R.drawable.igames) //~1213I~//~1327R~//~1328M~
                    };                                              //~1213I~//~1328M~
	public static int findIconId(String Pname)                     //~1213I~
    {                                                              //~1213I~
		int id=findViewIdByName(icontbl,Pname);                        //~1328I~
    	if (Dump.Y) Dump.println("icon search "+Pname+" id="+Integer.toHexString(id));//~1213I~//~1506R~
    	return id;                                                 //~1213I~//~1328R~
    }                                                              //~1213I~
//****************                                                 //~1327I~
//    private static final IdTblEntry[] soundtbl={                   //~1327I~//~1328M~//~1A08R~
////                    new IdTblEntry("high"          ,              R.raw.high),//~1327I~//~1328M~//~1A08R~
////                    new IdTblEntry("message"       ,              R.raw.message),//~1327I~//~1328M~//~1A08R~
//                    new IdTblEntry("click"         ,              R.raw.click),//~1327I~//~1328M~//~1A08R~
//                    new IdTblEntry("stone"         ,              R.raw.stone),//~1327I~//~1328M~//~1A08R~
//                    new IdTblEntry("wip"           ,              R.raw.wip),//~1327I~//~1328M~//~1A08R~
////                    new IdTblEntry("yourmove"      ,              R.raw.yourmove),//~1327I~//~1328M~//~1A08R~
////                    new IdTblEntry("game"          ,              R.raw.game),//~1327I~//~1328M~//~1A08R~
//                    new IdTblEntry("pieceup"       ,              R.raw.pieceup),//~@@@@I~//~1A08R~
//                    new IdTblEntry("piecedown"     ,              R.raw.piecedown),//~@@@@I~//~1A08R~
////                    new IdTblEntry("piececaptured" ,              R.raw.piececaptured),//~@@@@I~//~1A08R~
//                    };                                             //~1327I~//~1328M~//~1A08R~
//    public static int findSoundId(String Pname)                    //~1327I~//~1A08R~
//    {                                                              //~1327I~//~1A08R~
//        int id=findViewIdByName(soundtbl,Pname);                       //~1328I~//~1A08R~
//        if (Dump.Y) Dump.println("Sound "+Pname+",id="+Integer.toHexString(id));//~1506R~//~1A08R~
//        return id;                                                 //~1327I~//~1328R~//~1A08R~
//    }                                                              //~1327I~//~1A08R~
//****************                                                 //~1125I~
//    public static String findFrameNameByInstance(Object PframeObject) //~1125I~//~@@@@R~
//    {                                                              //~1125I~//~@@@@R~
//        String framename;                                          //~1125I~//~@@@@R~
//    //****************                                             //~1125I~//~@@@@R~
//        if (PframeObject instanceof MainFrame)                     //~1125I~//~@@@@R~
//            framename=layout_MainFrame;                            //~1125I~//~@@@@R~
////        else                                                       //~1125I~//~@@@@R~
////        if (PframeObject instanceof Go)                            //~1125I~//~@@@@R~
////            framename=layout_SingleServer;                         //~1125I~//~@@@@R~
//        else                                                       //~1125I~//~@@@@R~
//        if (PframeObject instanceof ConnectedGoFrame)             //~1125I~//~@@@@R~
//            framename=layout_ConnectedGoFrame;                     //~1125I~//~@@@@R~
////        else                                                       //~1125I~//~@@@@R~
////        if (PframeObject instanceof GamesFrame)                   //~1125I~//~@@@@R~
////            framename=layout_GamesFrame;                           //~1125I~//~@@@@R~
////        else                                                       //~1125I~//~@@@@R~
////        if (PframeObject instanceof ConnectionFrame)              //~1125I~//~@@@@R~
////            framename=layout_ConnectionFrame;                      //~1125I~//~@@@@R~
//        else                                                       //~1125I~//~@@@@R~
//            framename=null;                                        //~1125I~//~@@@@R~
//        return framename;                                          //~1125I~//~@@@@R~
//    }                                                              //~1125I~//~@@@@R~
//****************                                                 //~1120I~
	public static int findViewIdByName(String Pname)               //~1120R~
    {                                                              //~1120I~
    	return findViewIdByName(viewtbl,Pname);                    //~1328I~
    }                                                              //~1120I~
//****************                                                 //~1328I~
	public static int findViewIdByName(IdTblEntry[] Pviewtbl,String Pname)//~1328R~
    {                                                              //~1328I~
    	int id=-1,sz=Pviewtbl.length;                              //~1328R~
    	for (int ii=0;ii<sz;ii++)                                  //~1328I~
    		if (Pname.equals(Pviewtbl[ii].name))                   //~1328I~
            {                                                      //~1328R~
    			id=Pviewtbl[ii].id;                                //~1328R~
                break;                                             //~1328I~
            }                                                      //~1328I~
    	if (Dump.Y) Dump.println("FindViewByName "+Pname+" id="+Integer.toHexString(id));//~1506R~
    	return id;                                                 //~1328R~
    }                                                              //~1328I~
//****************                                                 //~1328I~
	public static View findViewByName(IdTblEntry[] Pviewtbl,String Pname)//~1328I~
    {                                                              //~1328I~
		int id=findViewIdByName(Pviewtbl,Pname);                   //~1328I~
        if (id<0)                                                  //~1328I~
        	return null;                                           //~1328I~
    	return findViewById(id);                                   //~1328I~
    }                                                              //~1328I~
//****************                                                 //~1120I~
	public static View findViewByName(String Pname)                //~1120I~
    {                                                              //~1120I~
        return findViewByName(AG.currentLayout,Pname);             //~1121R~
    }                                                              //~1120I~
//****************                                                 //~1216I~
	public static View findViewById(int Presid)                   //~1216I~
    {                                                              //~1216I~
        return AG.currentLayout.findViewById(Presid);              //~1216I~
    }                                                              //~1216I~
//****************                                                 //~1416I~
	public static View findViewById(View Playout,int Pid)          //~1416I~
    {                                                              //~1416I~
		return Playout.findViewById(Pid);                          //~1416I~
    }                                                              //~1416I~
//****************                                                 //~1121I~//~1216M~
	public static View findViewByName(View Playout,String Pname)   //~1121I~//~1216M~
    {                                                              //~1121I~//~1216M~
        int id=findViewIdByName(Pname);                            //~1216M~
		return Playout.findViewById(id);                             //~1121I~//~1216M~
    }                                                              //~1121I~//~1216M~
//****************                                                 //~1216I~
	public static void setCurrentView(int Presourceid,View Pview)         //~1216I~
    {                                                              //~1216I~
        if (Pview==null)                                           //~v106I~
        {                                                          //~v106I~
        	if (Dump.Y) Dump.println("setCurrentLayout null resid="+Integer.toHexString(Presourceid));//~v106I~
            return;                                                //~v106I~
        }                                                          //~v106I~
        currentLayout=Pview;                                       //~1216I~
        currentLayoutId=Presourceid;                               //~1216I~
        currentLayoutLabelSeqNo=0;                                 //~1216R~
        currentLayoutTextFieldSeqNo=0;                             //~1216I~
        currentLayoutTextAreaSeqNo=0;                               //~1416I~
        currentLayoutButtonSeqNo=0;                                //~1306I~
        currentLayoutCheckBoxSeqNo=0;                               //~1328I~
//        currentLayoutSpinnerSeqNo=0;                               //~1331I~//~1A30R~
        currentLayoutSeekBarSeqNo=0;                               //~1331I~
        if (Dump.Y) Dump.println("setCurrentLayout resid="+Integer.toHexString(Presourceid)+",Viewid="+(Pview==null?"null":Pview.toString()));//~1A4jR~
                                                                   //~v106I~
    }
//****************                                                 //~1216I~
	public static View getCurrentLayout()
	{
		return currentLayout;//~1216I~
	}
//****************                                                 //~1216I~
	public static void setCurrentDialog(Dialog Pdialog)            //~1216I~
	{                                                              //~1216I~
    	currentIsDialog=true;                                      //~1311I~
		currentDialog=Pdialog;                                     //~1216I~
        if (Dump.Y) Dump.println("Ag:setCurrentDialog="+Pdialog.toString());//~1A4hI~
	}                                                              //~1216I~
//****************                                                 //~1311I~
	public static void setCurrentFrame(Frame Pframe)               //~1311I~
	{                                                              //~1311I~
    	currentIsDialog=false;                                     //~1311I~
		currentDialog=null;                                        //~1A4jI~
		currentFrame=Pframe;                                       //~1311I~
        if (Dump.Y) Dump.println("AG:setCurrentFrame name="+Pframe.framename+"="+Pframe.toString());//~@@@@R~
//        resetIM(Pframe);                                         //~1A4jR~
    	setCurrentView(Pframe.framelayoutresourceid,Pframe.framelayoutview);//for wrap operation//~1504I~
	}                                                              //~1311I~
//****************                                                 //~1216I~
//    public static void setDialogClosed()                           //~1325I~//~@@@@R~
    public static void setDialogClosed(Dialog Pdialog)             //~@@@@I~
	{                                                              //~1325I~
        if (Dump.Y) Dump.println("AG:setDialogClosed dialogname="+Pdialog.dialogname+",currentIsDialog="+currentIsDialog+",currentLayout="+currentLayout.toString());//~1A4mR~
     if (currentIsDialog)                                          //~1A4mR~
     {                                                             //~1A4mR~
      if (Pdialog.parentDialog!=null)	//dialog on dialog         //~@@@@R~
      {                                                            //~@@@@I~
        currentDialog=Pdialog.parentDialog;                        //~@@@@I~
        if (Dump.Y) Dump.println("AG:setDialogClosed parent is dialog="+currentDialog.toString());//~1A4hR~
      }                                                            //~@@@@I~
      else                                                         //~@@@@I~
      {                                                            //~@@@@I~
    	currentIsDialog=false;                                     //~1325I~
//      Frame f=Pdialog.parentFrame;                               //~@@@@R~
        Frame f=Pdialog.layoutFrame;                               //~@@@@I~
        if (Dump.Y) Dump.println("AG:setDialogClosed parent is frame="+(f==null?"null":f.framename));//~@@@@I~
        if (f!=null && f.framelayoutresourceid!=0)	//should recover framelayoutview//~@@@@R~
        {                                                          //~@@@@I~
        	if (Dump.Y) Dump.println("AG:setDialogClosed parent is frame="+f.framename);//~@@@@I~
//            AG.setCurrentFrame(f);  //restore currentLayout for new Canvas for changeBoard//~@@@@R~
			setCurrentView(f.framelayoutresourceid,f.framelayoutview);//~@@@@I~
        }                                                          //~@@@@I~
      }                                                            //~@@@@I~
     }//maintain current view                                      //~1A4mI~
      	if (Pdialog.dismissListener!=null)                         //~@@@@M~
      	{                                                          //~@@@@M~
        	if (Dump.Y) Dump.println("AG:setDialogClosed dismisslistenercall");//~@@@@I~
            Pdialog.dismissListener.doAction(AG.resource.getString(R.string.ActionDismissDialog));	//notify to Dialog callet to new frame  will be opened//~@@@@R~
      	}                                                          //~@@@@M~
	}                                                              //~1325I~
//****************                                                 //~1325I~
	public static Dialog getCurrentDialog()                          //~1216I~
	{                                                              //~1216I~
        if (currentIsDialog)                                       //~1401I~
			return currentDialog;                                      //~1216I~//~1401R~
        return null;                                               //~1401I~
	}                                                              //~1216I~
//****************                                                 //~1324I~
	public static boolean currentIsDialog()                        //~1427R~
	{                                                              //~1324I~
		return currentIsDialog;                                    //~1427R~
	}                                                              //~1324I~
	public static Frame getCurrentFrame()                          //~1427I~
	{                                                              //~1427I~
		return currentFrame;                                       //~1427I~
	}                                                              //~1427I~
//****************                                                 //~1216I~
	private static final int[] labelviewtbl={               //~1328I~
					R.id.Label1     ,//~1307I~//~1328M~           //~1331R~
					R.id.Label2     ,//~1216I~//~1328M~           //~1331R~
					R.id.Label3     ,//~1216I~//~1328M~           //~1331R~
					R.id.Label4     ,//~1310I~//~1328M~           //~1331R~
					R.id.Label5     ,//~1310I~//~1328M~           //~1331R~
					R.id.Label6     ,//~1314I~//~1328M~           //~1331R~
					R.id.Label7     ,//~1314I~//~1328M~           //~1331R~
					R.id.Label8     ,//~1330I~                    //~1331R~
					R.id.Label9     ,//~1330I~                    //~1331R~
					R.id.Label10    ,//~1330I~                    //~1331R~
                    };                                             //~1328I~
	public static View findLabelView()                             //~1216I~
    {                                                              //~1216I~
    	int id=labelviewtbl[currentLayoutLabelSeqNo++];            //~1331I~
        if (Dump.Y) Dump.println("findLabelView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                               //~1216I~//~1328R~//~1331R~
    }                                                              //~1216I~
//****************                                                 //~1216I~
	private static final int[] textfieldviewtbl={           //~1328I~
					R.id.TextField1 ,//~1216I~//~1328M~           //~1331R~
					R.id.TextField2 ,//~1216I~//~1328M~           //~1331R~
					R.id.TextField3 ,//~1216I~//~1328M~           //~1331R~
					R.id.TextField4 ,//~1310I~//~1328M~           //~1331R~
					R.id.TextField5 ,//~1314I~//~1328M~           //~1331R~
					R.id.TextField6 ,//~1314I~//~1328M~           //~1331R~
					R.id.TextField7 ,//~1330I~                    //~1331R~
					R.id.TextField8 ,//~1330I~                    //~1331R~
					R.id.TextField9 ,//~1330I~                    //~1331R~
					R.id.TextField10,//~1330I~                    //~1331R~
                    };                                             //~1328I~
	public static View findTextFieldView()                         //~1216I~
    {                                                              //~1216I~
    	int id=textfieldviewtbl[currentLayoutTextFieldSeqNo++];    //~1331I~
        if (Dump.Y) Dump.println("findTextFieldView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                               //~1216I~//~1328R~//~1331R~
    }                                                              //~1216I~
//****************                                                 //~1416I~
	private static final int[] textareaviewtbl={                   //~1416I~
					R.id.TextArea1 ,                               //~1416I~
                    };                                             //~1416I~
	public static View findTextAreaView()                          //~1416I~
    {                                                              //~1416I~
    	int id=textareaviewtbl[currentLayoutTextAreaSeqNo++];      //~1416I~
        if (Dump.Y) Dump.println("findTextAreaView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                                   //~1416I~
    }                                                              //~1416I~
//****************                                                 //~1306I~
	private static final int[] buttonviewtbl={              //~1328I~
					R.id.Button1    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button2    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button3    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button4    ,//~1306I~//~1328M~           //~1331R~
					R.id.Button5    ,//~1310I~//~1328M~           //~1331R~
                    };                                             //~1328I~
	public static View findButtonView()                            //~1306I~
    {                                                              //~1306I~
    	int id=buttonviewtbl[currentLayoutButtonSeqNo++];          //~1331I~
        if (Dump.Y) Dump.println("findButtonView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                               //~1306I~//~1328R~//~1331R~
    }                                                              //~1306I~
	public static int disableButton()                              //~@@@@R~
    {                                                              //~@@@@I~
    	int ctr=0;                                                 //~@@@@I~
    //**************************                                   //~@@@@I~
    	for (int ii=currentLayoutButtonSeqNo;ii<buttonviewtbl.length;ii++,ctr++)//~@@@@R~
        {                                                          //~@@@@I~
    		int id=buttonviewtbl[ii];                              //~@@@@I~
			Button v=(Button)findViewById(id);                     //~@@@@I~
            Component dmy=new Component();                         //~@@@@I~
            if (v!=null)                                           //~@@@@I~
            {                                                      //~@@@@I~
            	dmy.setVisibility((View)v,View.GONE);              //~@@@@R~
            }                                                      //~@@@@I~
    	}                                                          //~@@@@I~
    	currentLayoutButtonSeqNo=0; //for next frame               //~@@@@I~
    	return ctr;                                                //~@@@@I~
    }                                                              //~@@@@I~
//****************                                                 //~@@@@I~
	public static boolean disableButton(int Presid)                    //~@@@@I~
    {                                                              //~@@@@I~
    //**************************                                   //~@@@@I~
		Button v=(Button)findViewById(Presid);                     //~@@@@I~
        if (v==null)                                               //~@@@@I~
        	return false;                                                    //~@@@@I~
        Component dmy=new Component();                             //~@@@@I~
                                           //~@@@@I~
            dmy.setVisibility((View)v,View.GONE);                  //~@@@@I~
        return true;//~@@@@I~
    }                                                              //~@@@@I~
//****************                                                 //~1328I~
	private static final int[] chkboxviewtbl={              //~1328I~//~1331R~
//                     R.id.CheckBox1  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox2  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox3  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox4  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox5  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox6  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox7  ,//~1328M~                   //~1331R~//~@@@@R~
//                     R.id.CheckBox8  ,//~1328M~                   //~1331R~//~@@@@R~
                    };                                             //~1328I~
	public static View findCheckBoxView()                          //~1328I~
    {                                                              //~1328I~
    	int id=chkboxviewtbl[currentLayoutCheckBoxSeqNo++];        //~1331I~
        if (Dump.Y) Dump.println("findCheckBoxView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                 //~1328R~         //~1331R~
    }                                                              //~1328I~
//****************                                                 //~1331I~
//    private static final int[] spinnerviewtbl={                    //~1331R~//~1A30R~
//                    R.id.Spinner1  ,                              //~1331R~//~1A30R~
//                    R.id.Spinner2  ,                              //~1331R~//~1A30R~
//                    };                                             //~1331I~//~1A30R~
//    public static View findSpinnerView()                           //~1331I~//~1A30R~
//    {                                                              //~1331I~//~1A30R~
//        int id=spinnerviewtbl[currentLayoutSpinnerSeqNo++];        //~1331R~//~1A30R~
//        if (Dump.Y) Dump.println("findSpinnerView id="+Integer.toHexString(id));//~1506R~//~1A30R~
//        return findViewById(id);                                   //~1331I~//~1A30R~
//    }                                                              //~1331I~//~1A30R~
//****************                                                 //~1331I~
	private static final int[] seekbarviewtbl={                    //~1331I~
//                    R.id.SeekBar1  ,                               //~1331I~//~v1A0R~
//                    R.id.SeekBar2  ,                               //~1331I~//~v1A0R~
//                    R.id.SeekBar3  ,                               //~1331I~//~v1A0R~
                    };                                             //~1331I~
	public static View findSeekBarView()                           //~1331I~
    {                                                              //~1331I~
    	int id=seekbarviewtbl[currentLayoutSeekBarSeqNo++];        //~1331I~
        if (Dump.Y) Dump.println("findSeekBarView id="+Integer.toHexString(id));//~1506R~
		return findViewById(id);                                   //~1331I~
    }                                                              //~1331I~
//****************                                                 //~1326I~
	public static boolean isTopFrame()                             //~1326I~
    {                                                              //~1326I~
        Frame frame=Window.getCurrentFrame();                      //~1326I~
        if (Dump.Y) Dump.println("isTopFrame current frame:"+frame.framename);//~1506R~
        return frame==AG.mainframe;                                //~1326I~
    }                                                              //~1326I~
	public static boolean isMainFrame(Frame Pframe)                //~1331I~
    {                                                              //~1331I~
	    return (Pframe.framelayoutresourceid==AG.frameId_MainFrame);//~1331I~
    }                                                              //~1331I~
//****************                                                 //~1126I~
//*for Modal Dialog                                                //~1126I~
//****************                                                 //~1126I~
	public static Thread mainThread;                               //~1126I~
    public static boolean isMainThread()                           //~1126M~
    {                                                              //~1126M~
    	return (Thread.currentThread()==AG.mainThread);              //~1126M~
    }                                                              //~1126M~
////**************************************************************   //~@@@@I~//~v1A0R~
//    public static boolean isChessBoard()                           //~@@@@I~//~v1A0R~
//    {                                                              //~@@@@I~//~v1A0R~
//        return (propBoardSize==BOARDSIZE_CHESS);                    //~@@@@I~//~v1A0R~
//    }                                                              //~@@@@I~//~v1A0R~
//**************************************************************   //~1A4jI~
//* windowDismissed is hiden method                                //~1A4jI~
//**************************************************************   //~1A4jI~
//    public static void resetIM(Frame Poldframe)                  //~1A4jR~
//    {                                                            //~1A4jR~
//        InputMethodManager im=(InputMethodManager)AG.context.getSystemService(Context.INPUT_METHOD_SERVICE);//~1A4jR~
//        IBinder ib;                                              //~1A4jR~
//        im.windowDismissed(Poldframe.framelayoutview.getWindow().getDecorView().getWindowToken());//+1A4jI~//~1A4jR~
//        View v=null;                                             //~1A4jR~
//        im.startGettingWindowFocus(v);                           //~1A4jR~
//    }                                                            //~1A4jR~

//*******************                                              //~1120I~
}//class AG                                                        //~1107R~
