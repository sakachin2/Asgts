//*CID://+1Af4R~:                             update#=  158;       //~1Ad3R~//~1Af4R~
//*****************************************************************//~v101I~
//1Ad3 2016/07/09 (Ahsv)Bypass NFCSelect, by NFS-WD and NFC-BT button directly.//~1Ad3I~
//1Af8 2016/07/09 (Ahsv)DialogNFCBT,not nfc msg when listening status//~1Af7I~
//1Af7 2016/07/08 (Ahsv)IllegalArgumentException:null is not valid Bluetooth addr at ConnectButton//~1Af6I~
//1Af6 2016/07/08 NPE when bluetooth is not supported              //~1Af6I~
//1Af4 2016/07/07 add Secure optio to NFCBT                        //~1Af4I~
//1Af3 2016/07/06 (Ajagot1w)Display Discoverable status            //~1Af3I~
//1Af2 2016/07/06 (Ajagot1w)Msg "Not NFC dialog" is not shown when NFC dialog proceeed to connected dialog//~1Af2I~
//1Af1 2016/07/05 (Ajagot1w)update bluetooth connection dialog from bluetooth receiver//~1Af1I~
//1Aem 2015/08/09 show NFC-BT dialog even when bluetooth not supported//~1AemI~
//1Aed 2015/07/30 show secure/insecure option to waiting msg       //~1AedI~
//1AbB 2015/06/22 mask mac addr for security                       //~1AbBI~
//1AbA 2015/06/22 NFCBT:use button label "Accept"/"Connect" for both Secure and InSecure case.//~1AbAI~
//1Abz 2015/06/21 NFC:use helpdialog for help                      //~1AbzI~
//1Abs 2015/06/17 NFC:Ignore NFC msg when already connected        //~1AbsI~
//1Abr 2015/06/16 NFC:colored background only when accept NFC tap. //~1AbrI~
//1Abp 2015/06/16 NFCBT:sleep requred to keep sequece of Accept-->Connect//~1AbpI~
//1Abo 2015/06/16 NFCBT:no need to be bonded; del 1Abn             //~1AbnI~
//1Abn 2015/06/16 NFCBT:change chkBonded from sleep to thread-run  //~1AbnI~
//1Abm 2015/06/16 NFCBT:chk secure level of requester and intent receiver//~1AbmI~
//1Abk 2015/06/16 NFC:errmsg when intent is for other side of NFC/NFCBT//~1AbkI~
//1Abj 2015/06/15 NFCBT:dismiss dialog at boardQuestion            //~1AbjI~
//1Abi 2015/06/15 NFCBT:put DisconnectButton after Game butrton    //~1AbiI~
//1Abh 2015/06/15 NFCBT:change msg when already connected          //~1AbhI~
//1Abg 2015/06/15 NFCBT:transfer to NFCBT or NFCWD if active session exist//~1AbgI~
//1Abf 2015/06/15 NFCBT:no waiting ProgDialog is shown,dispose()=dismiss()->dismiss listener is sheduled before waitingMsg is set)//~1AbfI~
//1Abe 2015/06/15 NFCBT:disable button when no cuurent session     //~1AbeI~
//1Abb 2015/06/15 NFCBT:try insecure only(if accepting both,connect insecure request is accepted by secure socket)//~1AbbI~
//1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//1A6r 2015/02/16 BT:update connection status when lost connection //~1A6rI~
//1A6k 2015/02/15 re-open IPConnection/BTConnection dialog when diconnected when dislog is opened.//~1A6kI~
//1A6i 2015/02/14 Bluetooth;display current bonded(paired) device list not initial status.//~1A6iI~
//1A6f 2015/02/13 support cutom layout of ListView for BluetoothConnection to show available/paired status//~1A6fI~
//1A6c 2015/02/13 Bluetooth;identify paired device and discovered device//~1A6cI~
//1A62 2015/01/27 keep BT dialog when discoverable button          //~1A62I~
//1A61 2015/01/27 avoid to fill screen when listview has few entry.(Motorolla is dencity=1.0(mdpi)//~1A60I~//~1A62I~
//1A60 2015/01/25 also support Bluetooth secure SPP(Bluetooth 2.1) //~1A60I~
//1A2j 2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//101i 2013/02/09 for the case Bluetoothe disabled at initial      //~v101I~
//*****************************************************************//~v101I~
//package jagoclient.partner;                                      //~1Ab8R~
package wifidirect;                                                //~1Ab8I~
//import com.Asgts.awt.GridLayout;                                  //~2C26R~//~v101R~
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Locale;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;                                    //~1Af4R~

import com.Asgts.ABT;                                              //~v101R~
import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;                                            //~v101R~
import com.Asgts.Alert;
import com.Asgts.ProgDlg;                                          //~v101R~
import com.Asgts.Prop;
import com.Asgts.R;                                                //~v101R~
import com.Asgts.UiThread;
import com.Asgts.UiThreadI;
import com.Asgts.Utils;
import com.Asgts.awt.Component;

import jagoclient.Dump;
import jagoclient.Global;                                          //~3203I~
import jagoclient.board.GoFrame;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.Message;
import jagoclient.gui.ButtonAction;
import jagoclient.partner.BluetoothConnection;                     //~1Ab8I~


/**
Question to accept or decline a game with received parameters.
*/

//public class BluetoothConnection extends CloseDialog               //~3105R~//~1Ab8R~
//  	implements ProgDlgI                                         //~3203I~//~1Ab8R~
@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14                        //~1Ab8I~
public class DialogNFCBT extends BluetoothConnection               //~1Ab8I~
{                                                                  //~2C29R~
	GoFrame GF;                                                    //~3105R~
    private static final int ID_SETTING=R.id.BluetoothSettings;    //~1Ad3I~
    private static final int ID_SETTING_NFC=R.id.NFCSettings;      //~1Ad3I~
    private static final int STRING_ID_SETTING=R.string.BluetoothSettingsButton;//~1Ad3I~
    private static final int STRING_ID_SETTING_NFC=R.string.SelectDialogWifiNFCSettings;//~1Ad3I~
    private int waitingDialog=0;                                   //~3201I~
    private ButtonAction acceptButton,gameButton,connectButton;                              //~3201I~//~3208R~
//  private ButtonAction connectSecureButton;                      //~1A60I~//~1AbAR~
    private ButtonAction disconnectButton;                         //~1AbiI~
    private TextView tvRemoteDevice;                               //~1A6fI~
    private LinearLayout nfcButtons;                               //~1AbeI~
//    private static String[] DeviceList; //not each time to avoid deadlock                                    //~3203I~//~3204R~//~3205R~//~3206R~//~1Ab8R~
//    private ListBT DL;      //listview                             //~1A6fI~//~1Ab8R~
//    private boolean onDiscovery;                                   //~3203I~//~1Ab8R~
    int waitingid;//~3203I~
    private String myDevice,connectingDevice;                      //~v101I~
//    private boolean swCancelDiscover;                                      //~3205I~//~1Ab8R~
//    private static int lastSelected=-1;                            //~3205I~//~1Ab8R~
    private static final int connectedColor=android.graphics.Color.argb(0xff,0xff,0xff,0x00);//~3207I~
//    private static int ID_STATUS_PAIRED=R.string.BTStatusPaired;   //~1A6fI~//~1Ab8R~
//    private static int ID_STATUS_DISCOVERED=R.string.BTStatusDiscovered;//~1A6fI~//~1Ab8R~
//    private static int ID_STATUS_CONNECTED=R.string.BTStatusConnected;//~1A6fI~//~1Ab8R~
//    private static int ID_STATUS_DISCONNECTED=R.string.BTStatusDisConnected;//~1A6rI~//~1Ab8R~
//    private String statusPaired,statusDiscovered,statusConnected;//~1A6fR~//~1A6kR~//~1Ab8R~
//    private String statusDisconnected;                             //~1A6rI~//~1Ab8R~
                                                                   //~1A6fI~
//        private static final int BTROW_NAME=R.id.ListViewLine;     //~1A6fI~//~1Ab8R~
//        private static final int BTROW_STATUS=R.id.ListViewLineStatus;//~1A6fI~//~1Ab8R~
//        private static final Color COLOR_STATUS_PAIRED=new Color(0, 0x80, 0x80);//~1A6fM~//~1A6kR~//~1Ab8R~
//        private static final Color COLOR_STATUS_DISCOVERED=new Color(0, 0xc0, 0xc0);//~1A6fM~//~1A6kR~//~1Ab8R~
//        private static final Color COLOR_STATUS_CONNECTED=Color.orange;//~1A6kR~//~1Ab8R~
                                                                   //~1A6fI~
    private boolean swSecure;                                      //~1Ab8I~
	public static DialogNFCBT SdialogNFC;                          //~1Ab8I~
    private boolean swTouch;  //to chk already paired at NFC button//~1Ab8I~
    String deviceName,deviceAddr,deviceStatus,peerAddr,peerName="";//~1Ab8I~
 	String mimetype,macAddr;                                       //~1Ab8I~
    public static final String  DATA_PREFIX="MacAddressBT=";       //~1Ab8I~
    public static final String  DATA_SEP=",";                      //~1Ab8I~
    private boolean isNFCenabled;                                  //~1Ab8I~
    private TextView tvMsg;                                       //~1Ab8I~
    private static final int TEXTID_INITIAL=R.string.BTNFCInitialMsg;//~1Ab8I~
    private static final int TEXTID_CONNECTED=R.string.BTNFCInitialConnectedMsg;//~1AbhI~
    private static final int TEXTID_LISTENING=R.string.BTNFCInitialListeningMsg;//~1Af8I~
//  private static final int TEXTID_HELP=R.string.Help_BTNFC;      //~1Ab8I~//~1AbzR~
    private static final int HELPTITLEID=R.string.HelpTitle_NFCBT; //~1AbzI~
    private static final int CHKBONDED_TIMEOUT=30000;     //30sec  //~1AbnR~
    private static final int CHKBONDED_INTERVAL=300;   //300ms     //~1AbnI~
    private static final int DELAY_BEFORE_CONNECT=2000; //2sec     //~1AbpR~
    private static final boolean swOOB=false;                      //~1Ab8R~
                                                                   //~1Af4I~
    private static final String PKEY_BTSECURE_NFC="BTSecureOptionNFC";//~1Af4I~
    private static final int BTSECURE_DEFAULT_NFC=0; //default InSecure//~1Af4I~
                                                                   //~1Af4I~
 	static final byte[] TYPE_BT_OOB = "application/vnd.bluetooth.ep.oob".getBytes(Charset.forName("US_ASCII"));//~1Ab8I~
 	static final int CARRIER_POWER_STATE_INACTIVE = 0;             //~1Ab8I~
 	static final int CARRIER_POWER_STATE_ACTIVE = 1;               //~1Ab8I~
 	static final int CARRIER_POWER_STATE_ACTIVATING = 2;           //~1Ab8I~
 	static final int CARRIER_POWER_STATE_UNKNOWN = 3;              //~1Ab8I~
	static final byte[] RTD_COLLISION_RESOLUTION = {0x63, 0x72}; // "cr";//~1Ab8I~
    private static String SconflictMsg;                            //~1AbmI~
    private static ChkBondedThread threadCB;                       //~1AbnR~
    private static final int STRID_BTACCEPT=R.string.BTAcceptCommon; //~1AbAI~
    private static final int STRID_BTCONNECT=R.string.BTConnectCommon;//~1AbAI~
    private CheckBox cbSecureOption;                               //~1Af4I~
    //********************************************************************//~1Ab8I~
    public static DialogNFCBT showDialog(GoFrame Pgf,int PhandoverType)//~1Ab8R~
    {                                                              //~1Ab8I~
        DialogNFCBT dlg=new DialogNFCBT(Pgf,PhandoverType);        //~1Ab8R~
        SdialogNFC=dlg;                                            //~1Ab8I~
        return dlg;                                                //~1Ab8R~
    }                                                              //~1Ab8I~
    //********************************************************************//~1A6fI~
//	public BluetoothConnection (GoFrame Pgf)                       //~3105R~//~1Ab8R~
	public DialogNFCBT(GoFrame Pgf,int PhandoverType)              //~1Ab8R~
	{                                                              //~3105R~
//  	super(Pgf,AG.resource.getString(R.string.Title_Bluetooth), //~1A60I~//~1Ab8R~
//  			(AG.layoutMdpi/*mdpi and height or width <=320*/ ? R.layout.bluetooth_mdpi : R.layout.bluetooth),//~1A61R~//~1A60I~//~1Ab8R~
//  			true,false);                                       //~1A60I~//~1Ab8R~
    	super(Pgf,                                                 //~1Ab8I~
//  			(PhandoverType==DialogNFCSelect.NFCTYPE_BT_SECURE?R.string.Title_NFCBT_Secure:R.string.Title_NFCBT_InSecure),//~1Ab8I~//~1Af4R~
				R.string.Title_NFCBT_Init,                         //~1Af4I~
    			(AG.layoutMdpi/*mdpi and height or width <=320*/ ? R.layout.dialognfcbt_mdpi : R.layout.dialognfcbt));//~1Ab8I~
        swSecure=PhandoverType==DialogNFCSelect.NFCTYPE_BT_SECURE; //~1Ab8I~
        init();                                                    //~1Ab8I~
//      if (deviceAddr==null)	//bluetooth not supported          //~1Ab8I~//~1AemR~
//      	return;                                                //~1Ab8I~//~1AemR~
//        GF=Pgf;                                                    //~3105R~//~1Ab8R~
//        statusPaired=AG.resource.getString(ID_STATUS_PAIRED);      //~1A6fI~//~1Ab8R~
//        statusDiscovered=AG.resource.getString(ID_STATUS_DISCOVERED);//~1A6fI~//~1Ab8R~
//        statusConnected=AG.resource.getString(ID_STATUS_CONNECTED);//~1A6fI~//~1Ab8R~
//        statusDisconnected=AG.resource.getString(ID_STATUS_DISCONNECTED);//~1A6rI~//~1Ab8R~
        tvRemoteDevice=(TextView)(findViewById(R.id.RemoteDevice));//~1A6fI~
        tvMsg=(TextView)(findViewById(R.id.Msg));                  //~1Ab8I~
//      showMsg(TEXTID_INITIAL);                                   //~1Ab8I~//~1AbhR~
//      int initmsgid=AG.activeSessionType==0 ? TEXTID_INITIAL : TEXTID_CONNECTED;//~1AbhI~//~1Af8R~
        int initmsgid=AG.activeSessionType==0                      //~1Af8I~
			 ? ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0 ? TEXTID_LISTENING : TEXTID_INITIAL)//~1Af8I~
             : TEXTID_CONNECTED;                                   //~1Af8I~
        showMsg(initmsgid);                                        //~1AbhI~
        cbSecureOption=(CheckBox)(findViewById(R.id.BTSecureOption));//~1Af4I~
    	cbSecureOption.setChecked(swSecure);                       //~1Af4I~
        if (AG.activeSessionType!=0)	//connected status         //~1Af4I~
	    	cbSecureOption.setEnabled(false);                      //~1Af4I~
        displayDevice();                                           //~3202I~
//        getDeviceList();                               //~v101R~   //~@@@2R~//~1Ab8R~
//        setSelection();                                            //~3204I~//~1Ab8R~
        acceptButton=new ButtonAction(this,0,R.id.BTAccept);  //~3208I~
        gameButton=new ButtonAction(this,0,R.id.BTGame);      //~3208I~
        connectButton=new ButtonAction(this,0,R.id.BTConnect);//~3208I~
//      connectSecureButton=new ButtonAction(this,0,R.id.BTConnectSecure);//~1A60I~//~1AbAR~
        disconnectButton=new ButtonAction(this,0,R.id.BTDisConnect);//~1AbiI~
        new ButtonAction(this,0,ID_SETTING);                       //~1Ad3I~
        new ButtonAction(this,0,ID_SETTING_NFC);                   //~1Ad3I~
        nfcButtons=(LinearLayout)(findViewById(R.id.ButtonsNFCBT));//~1AbeI~
      if (AG.RemoteStatus==AG.RS_BTCONNECTED)                      //~1AbiI~
      {                                                            //~1AbiI~
//      connectButton.setVisibility(View.GONE);                    //~1AbiI~//~1AbAR~
//      connectSecureButton.setVisibility(View.GONE);              //~1AbiI~//~1AbAR~
        connectButton.setEnabled(false);                           //~1AbAI~
      }                                                            //~1AbiI~
      else                                                         //~1AbiI~
      {                                                            //~1AbiI~
        disconnectButton.setVisibility(View.GONE);                 //~1AbiI~
//      if (swSecure)                                              //~1Ab8I~//~1AbAR~
//      	connectButton.setVisibility(View.GONE);                //~1Ab8I~//~1AbAR~
//      else                                                       //~1Ab8I~//~1AbAR~
//      	connectSecureButton.setVisibility(View.GONE);          //~1Ab8I~//~1AbAR~
        ;                                                          //~1AbAI~
      }                                                            //~1AbiI~
//        new ButtonAction(this,0,R.id.Discoverable);           //~3208I~//~1Ab8R~
//        new ButtonAction(this,0,R.id.Discover);               //~3208I~//~1Ab8R~
        new ButtonAction(this,0,R.id.Cancel);                 //~3208I~
        new ButtonAction(this,0,R.id.Help);                   //~3208I~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)                    //~3201R~
        {                                                          //~3201I~
            acceptButton.setEnabled(false);                              //~3201I~//~3208R~
//          if ((AG.RemoteStatusAccept & AG.RS_BTLISTENING_SECURE)!=0)//~1A60I~//~1Ab8R~
//          {                                                        //~1A60I~//~1Ab8R~
//          connectSecureButton.setAction(R.string.BTDisConnect);  //~1A60I~//~1AbiR~
//          connectButton.setEnabled(false);                       //~1A60I~//~1Ab8R~
//          }                                                        //~1A60I~//~1Ab8R~
//          else                                                     //~1A60I~//~1Ab8R~
//          {                                                        //~1A60I~//~1Ab8R~
//          connectButton.setAction(R.string.BTDisConnect);                   //~2C30I~//~3105R~//~3117R~//~3201R~//~3208R~//~1AbiR~
//          connectSecureButton.setEnabled(false);                 //~1A60I~//~1Ab8R~
//          }                                                        //~1A60I~//~1Ab8R~
        }                                                          //~3201I~
        else                                                       //~3201I~
    	if ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0)                    //~3201I~//~3207R~//~3208R~
        {                                                          //~3201I~
	        gameButton.setEnabled(false);                                //~3201I~//~3208R~
        	acceptButton.setAction(R.string.BTStopAccept);//~3201R~//~3203R~//~3204R~//~3208R~
    		if (AG.RemoteDeviceName==null)                         //~1Af7R~
        		connectButton.setEnabled(false);                   //~1Af7R~
        }                                                          //~3201I~
        else                                                       //~3201I~
        {                                                          //~3201I~
//          gameButton.setEnabled(false);                                //~3201I~//~3208R~//~1AbeR~
            new Component().setVisibility(nfcButtons,View.GONE);   //~1AbeR~
        }                                                          //~3201I~
        setDismissActionListener(this/*DoActionListener*/);        //~3201I~
		validate();
        show();  
        AG.aBTConnection=this;	//used when PartnerThread detected err//~1A6kI~
	}
    //******************************************                   //~1A6kI~
    public void updateViewDisconnected()                           //~1A6kI~
    {                                                              //~1A6kI~
    	acceptButton.setEnabled(true);                             //~1A6kI~
//      acceptButton.setAction(R.string.BTAccept);                 //~1A6kI~//~1AbAR~
        connectButton.setEnabled(true);                            //~1A6kI~
//      connectButton.setAction(R.string.BTConnect);               //~1A6kI~//~1AbAR~
//      connectSecureButton.setEnabled(true);                      //~1A6kI~//~1AbAR~
//      connectSecureButton.setAction(R.string.BTConnectSecure);   //~1A6kI~//~1AbAR~
        gameButton.setEnabled(false);                              //~1A6kI~
        String s=AG.resource.getString(R.string.NoSession);        //~1A6kI~
        new Component().setText(tvRemoteDevice,s);	//do on main thread//~1A6kI~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED && AG.RemoteDeviceName!=null)//~1A6rI~
        {                                                          //~1A6rI~
//            DL.update(AG.RemoteDeviceName,statusDisconnected);     //~1A6rI~//~1Ab8R~
//            setSelection();                                        //~1A6rI~//~1Ab8R~
        }                                                          //~1A6rI~
    }                                                              //~1A6kI~
	//*************************************************************************//~1Ab8I~
    private void showMsg(int Presid)                               //~1Ab8I~
    {                                                              //~1Ab8I~
        String msg=AG.resource.getString(Presid);                  //~1Ab8I~
        tvMsg.setText(msg);                                        //~1Ab8I~
//      if (AG.activeSessionType!=0)                               //~1AbrI~//~1Af8R~
		if (Presid!=TEXTID_INITIAL)                                //~1Af8I~
            tvMsg.setBackgroundColor(Color.WHITE);                 //~1AbrI~
    }                                                              //~1Ab8I~
    //******************************************                   //~1A6kI~
	public void doAction (String o)
	{                                                              //~2C26R~
    	if (Dump.Y) Dump.println("DialogNFCBT:doAction="+o);       //~1Ab8I~
    	try                                                        //~3117I~
        {                                                          //~3117I~
            if (deviceAddr==null)   //bluetooth not supported      //~1AemI~
            {                                                      //~1AemI~
                if (o.equals(AG.resource.getString(R.string.Cancel)))//~1AemI~
                {                                                  //~1AemI~
                    setVisible(false); dispose();                  //~1AemI~
                }                                                  //~1AemI~
                else                                               //~1AemI~
                if (o.equals(AG.resource.getString(R.string.Help)))//~1AemI~
                {                                                  //~1AemI~
                    new HelpDialog(Global.frame(),HELPTITLEID,"nfcbt");//~1AemI~
                }                                                  //~1AemI~
                else                                             //~1AemR~
                if (o.equals(AG.resource.getString(STRING_ID_SETTING_NFC)))  //modal but no inputWait//~1AemR~
                {                                                //~1AemR~
                    callSettingsNFC();                           //~1AemR~
                }                                                //~1AemR~
                else                                               //~1AemI~
                    ABT.BTNotAvailable();                          //~1AemI~
                return;                                            //~1AemI~
            }                                                      //~1AemI~
            if (o.equals(AG.resource.getString(R.string.BTGame)))  //~3201I~
            {                                                      //~3201I~
			    if (startGame())                                   //~3201I~
			    {                                                  //~3201I~
			    	setVisible(false); dispose();                  //~3201I~
			    }                                                  //~3201I~
            }                                                      //~3201I~
            else                                                   //~3201I~
//          if (o.equals(AG.resource.getString(R.string.BTConnect)))     //~@@@@I~//~2C30I~//~3105R~//~3117R~//~3201R~//~1AbAR~
            if (o.equals(AG.resource.getString(STRID_BTCONNECT)))  //~1AbAI~
            {                                                          //~2C30R~//~3117R~
//  		    if (connectPartner(false))                         //~1A60I~//~1AbAR~
    		    if (connectPartner(swSecure))                      //~1AbAI~
                {                                                  //~3203I~
//                  setVisible(false); dispose();                  //~3203I~//~1AbfR~
//                  waitingDialog=R.string.BTConnect;              //~3203I~//~1AbAR~
                    waitingDialog=STRID_BTCONNECT;                 //~1AbAI~
                    setVisible(false); dispose();                  //~1AbfI~
                }                                                  //~3203I~
            }                                                      //~3117R~
//            else                                                   //~3201I~//~1AbAR~
//            if (o.equals(AG.resource.getString(R.string.BTConnectSecure)))//~1A60I~//~1AbAR~
//            {                                                      //~1A60I~//~1AbAR~
//                if (connectPartner(true))                          //~1A60I~//~1AbAR~
//                {                                                  //~1A60I~//~1AbAR~
////                  setVisible(false); dispose();                  //~1A60I~//~1AbfR~//~1AbAR~
//                    waitingDialog=R.string.BTConnect;              //~1A60I~//~1AbAR~
//                    setVisible(false); dispose();                  //~1AbfI~//~1AbAR~
//                }                                                  //~1A60I~//~1AbAR~
//            }                                                      //~1A60I~//~1AbAR~
            else                                                   //~1A60I~
            if (o.equals(AG.resource.getString(R.string.BTDisConnect)))//~3201I~
            {                                                      //~3201I~
//              setVisible(false); dispose();                      //~3201I~//~1AbfR~
                waitingDialog=R.string.BTDisConnect;               //~3207I~
                setVisible(false); dispose();                      //~1AbfI~
            }                                                      //~3201I~
            else                                                   //~3117R~
//          if (o.equals(AG.resource.getString(R.string.BTAccept))) //~3117R~//~3201R~//~1AbAR~
            if (o.equals(AG.resource.getString(STRID_BTACCEPT)))   //~1AbAI~
            {                                                      //~3117R~
//              setVisible(false); dispose();                      //~3117R~//~1AbfR~
//              boolean rc=ABT.startListen();                                         //~3105I~//~3117R~//~v101R~//~1AbbR~
                boolean rc=ABT.startListen(swSecure);              //~1AbbI~
              if (rc)                                              //~v101I~
              {                                                    //~v101I~
	            acceptButton.setEnabled(false);                          //~3201I~//~3208R~
//              waitingDialog=R.string.BTAccept;           //~3201I~//~1AbAR~
                waitingDialog=STRID_BTACCEPT;                      //~1AbAI~
              }                                                    //~v101I~
                setVisible(false); dispose();                      //~1AbfI~
            }                                                      //~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.BTStopAccept)))//~3204I~
            {                                                      //~3204I~
	            stopListen();                                      //~3204I~
//      		acceptButton.setAction(R.string.BTAccept);//~3204I~//~3208R~//~1AbAR~
        		acceptButton.setAction(STRID_BTACCEPT);            //~1AbAI~
            }                                                      //~3204I~
//            else                                                   //~3204I~//~1Ab8R~
//            if (o.equals(AG.resource.getString(R.string.Discoverable)))//~3117R~//~1Ab8R~
//            {                                                          //~3105R~//~3117R~//~1Ab8R~
//                AG.aBT.setDiscoverable();                              //~3105I~//~3117R~//~1Ab8R~
//            }                                                      //~3117R~//~1Ab8R~
//            else                                                   //~3117R~//~1Ab8R~
//            if (o.equals(AG.resource.getString(R.string.Discover)))//~3203I~//~1Ab8R~
//            {                                                      //~3203I~//~1Ab8R~
//                if (AG.RemoteStatus==AG.RS_BTCONNECTED)            //~3206I~//~1Ab8R~
//                {                                                  //~3206I~//~1Ab8R~
//                    errConnectingForScan();                         //~3206I~//~1Ab8R~
//                    return;                                        //~3206I~//~1Ab8R~
//                }                                                  //~3206I~//~1Ab8R~
//                onDiscovery=true;                                  //~3203I~//~1Ab8R~
//                discover();                                        //~3203R~//~1Ab8R~
//                afterDismiss(R.string.Discover);                   //~3203I~//~1Ab8R~
//            }                                                      //~3203I~//~1Ab8R~
//            else                                                   //~3203I~//~1Ab8R~
//            if (o.equals(AG.resource.getString(R.string.ActionDiscovered)))//~3203I~//~1Ab8R~
//            {                                                      //~3203I~//~1Ab8R~
//                if (!onDiscovery)                                  //~3205I~//~1Ab8R~
//                    return;                                        //~3205I~//~1Ab8R~
//                onDiscovery=false;                                 //~3203I~//~1Ab8R~
//                discovered();                                      //~3203I~//~1Ab8R~
//            }                                                      //~3203I~//~1Ab8R~
            else                                                   //~3203I~
            if (o.equals(AG.resource.getString(R.string.Cancel)))  //~3117R~
            {                                                          //~2C30I~//~3117R~
                setVisible(false); dispose();                      //~3117R~
            }                                                          //~2C30I~//~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Help)))    //~3117R~
            {                                                      //~3117R~
//              new HelpDialog(null,R.string.HelpTitle_Bluetooth,"BTConnectionNFC");//~v101R~//~1Ab8R~
//      		showMsg(TEXTID_HELP);                              //~1Ab8I~//~1AbzR~
        		new HelpDialog(Global.frame(),HELPTITLEID,"nfcbt");//~1AbzI~
            }                                                      //~3117R~
            else                                                   //~3201I~
            if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~3201I~
            {               //callback from Dialog after currentLayout restored//~3201I~
//                cancelDiscover();                                  //~3203R~//~1Ab8R~
                afterDismiss(waitingDialog);                       //~3201I~
            }                                                      //~3201I~
            else                                                   //~1Ad3I~
            if (o.equals(AG.resource.getString(STRING_ID_SETTING)))  //modal but no inputWait//~1Ad3I~
            {                                                      //~1Ad3I~
	        	callSettings();                                    //~1Ad3I~
            }                                                      //~1Ad3I~
            else                                                   //~1Ad3I~
            if (o.equals(AG.resource.getString(STRING_ID_SETTING_NFC)))  //modal but no inputWait//~1Ad3I~
            {                                                      //~1Ad3I~
	        	callSettingsNFC();                                 //~1Ad3I~
            }                                                      //~1Ad3I~
            else super.doAction(o);                                //~3117R~
        }                                                          //~3117I~
        catch(Exception e)                                         //~3117I~
        {                                                          //~3117I~
            Dump.println(e,"BluetoothConnection:doAction:"+o);     //~3117I~
        }                                                          //~3117I~
	}
//    //******************************************                   //~3203I~//~1Ab8R~
//    private void discover()                                             //~3203I~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        swCancelDiscover=false;                                    //~3205I~//~1Ab8R~
//        AG.aBT.discover(this);                                     //~3203I~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
//    //******************************************                   //~3203I~//~1Ab8R~
//    private void cancelDiscover()                                       //~3203I~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        if (onDiscovery)                                           //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            AG.aBT.cancelDiscover();                               //~3203I~//~1Ab8R~
//            onDiscovery=false;                                     //~3203I~//~1Ab8R~
//            swCancelDiscover=true;                                 //~3205I~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
    //******************************************                   //~3201I~
    private boolean startGame()                                    //~3201I~
    {                                                              //~3201I~
    	if (AG.aBT.isConnectionAlive())                               //~@@@2I~//~3201I~
        {                                                          //~@@@2I~//~3201I~
            AG.aPartnerFrame.doAction(AG.resource.getString(R.string.Game));//~@@@2I~//~3201I~
            return true;                                                //~@@@2I~//~3201I~
        }                                                          //~@@@2I~//~3201I~
        errNoThread();                                             //~3201I~
        return false;                                              //~3201I~
    }                                                              //~3201I~
    //******************************************                   //~3201I~
    private void disconnectPartner()                               //~3201I~
    {                                                              //~3201I~
    	if (AG.aPartnerFrame!=null)                                //~3201I~
        	AG.aPartnerFrame.disconnect();                         //~3201I~
  	}                                                              //~3201I~
    //******************************************                   //~3201I~
    private void stopListen()                                      //~3201I~
    {                                                              //~3201I~
        AG.aBT.stopListen();                                       //~3201R~
  	}                                                              //~3201I~
    //******************************************                   //~3202I~
    private void displayDevice()                                   //~3202I~
    {                                                              //~3202I~
        String dev;
        TextView v;//~3202I~
    //************************                                     //~3202I~
        v=(TextView)(findViewById(R.id.LocalDevice));              //~v101I~
//      if (AG.LocalDeviceName!=null)                              //~3202I~//~1Ab8R~
        if (deviceName!=null)                                      //~1Ab8I~
//      	dev=AG.LocalDeviceName;                                //~3202I~//~1Ab8R~
        	dev=deviceName;                                        //~1Ab8I~
        else                                                       //~3202I~
        	dev=AG.resource.getString(R.string.UnknownDeviceName); //~3202I~
//      myDevice=dev+" ( "+deviceAddr+" )";                        //~v101I~//~1Ab8R~//~1AbBR~
        myDevice=dev+" ( "+DialogNFC.maskMacAddr(deviceAddr)+" )"; //~1AbBR~
//      v.setText(dev);                                            //~3202I~//~1Ab8R~
        v.setText(myDevice);                                       //~1Ab8I~
        displayDiscoverableStatus();                               //~1Af3I~
        //*                                                        //~3202I~
        v=tvRemoteDevice;                                          //~1A6fI~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED && AG.RemoteDeviceName!=null)//~3202I~
        {                                                          //~3207I~
        	dev=AG.RemoteDeviceName;                               //~3202I~
	        v.setTextColor(connectedColor);                        //~3207I~
        }                                                          //~3207I~
        else                                                       //~3202I~
        	dev=AG.resource.getString(R.string.NoSession);         //~3202I~
        v.setText(dev);                                            //~3202I~
    }                                                              //~3202I~
//    //******************************************                   //~3203I~//~1Ab8R~
//    private void getDeviceList()                      //~v101R~    //~@@@2R~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        DL=new ListBT(this,R.id.DeviceList,(AG.layoutMdpi ? R.layout.textrowlist_bt_mdpi : R.layout.textrowlist_bt));//~1A6fR~//~1Ab8R~
//        DL.addActionListener(this);                                //~3203I~//~3209R~//~@@@2R~//~1Ab8R~
//        DL.setBackground(Global.gray);                             //~3203I~//~3209R~//~@@@2R~//~1Ab8R~
//            DeviceList=AG.aBT.getPairDeviceList();                  //~3203I~//~3204R~//~1Ab8R~
//        String[] sa=DeviceList;                                    //~3204I~//~1Ab8R~
//        if (sa!=null)                                              //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            String remoteDevice=tvRemoteDevice.getText().toString();//~1A6fI~//~1Ab8R~
//            int ctr=sa.length/2;                                       //~3203I~//~1Ab8R~
//            for (int ii=0;ii<ctr;ii++)                             //~3203I~//~1Ab8R~
//            {                                                      //~3203I~//~1Ab8R~
//                if (Dump.Y) Dump.println("initial device add="+sa[ii*2]);//~3203I~//~1Ab8R~
//                String nm=sa[ii*2];                                //~1A6fI~//~1Ab8R~
//                if (remoteDevice!=null && remoteDevice.equals(nm)) //~1A6fI~//~1Ab8R~
//                    DL.add(nm,statusConnected,ID_STATUS_CONNECTED);//~1A6fI~//~1Ab8R~
//                 else                                              //~1A6fI~//~1Ab8R~
//                    DL.add(nm,statusPaired,ID_STATUS_PAIRED);      //~1A6fI~//~1Ab8R~
//            }                                                      //~3203I~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
//    //******************************************                   //~3204I~//~1Ab8R~
//    private void setSelection()                                    //~3204I~//~1Ab8R~
//    {                                                              //~3204I~//~1Ab8R~
//        int idx;                                                   //~3204I~//~1Ab8R~
//        idx=setSelection(AG.RemoteDeviceName);                     //~3204I~//~1Ab8R~
//        if (idx<0)                                                 //~3204I~//~1Ab8R~
//            if (lastSelected>=0)                                   //~3205I~//~1Ab8R~
//                idx=lastSelected;                                  //~3205I~//~1Ab8R~
//            else                                                   //~3205I~//~1Ab8R~
//                idx=0;                                                 //~3204I~//~3205R~//~1Ab8R~
//        if (idx<DL.getItemCount())                                         //~3204I~//~1Ab8R~
//        {                                                          //~3204I~//~1Ab8R~
//            DL.select(idx);                                        //~3204I~//~1Ab8R~
//            if (Dump.Y) Dump.println("setselect="+idx);            //~3204I~//~1Ab8R~
//        }                                                          //~3204I~//~1Ab8R~
//    }                                                              //~3204I~//~1Ab8R~
//    //******************************************                   //~3204I~//~1Ab8R~
//    private int setSelection(String Pdevice)                   //~3204I~//~1Ab8R~
//    {                                                              //~3204I~//~1Ab8R~
//        int idx=-1;                                                //~3204I~//~1Ab8R~
//        if (Pdevice==null)                                         //~3204I~//~1Ab8R~
//            return idx;                                            //~3204I~//~1Ab8R~
//        if (DeviceList!=null)                                      //~3204I~//~1Ab8R~
//        {                                                          //~3204I~//~1Ab8R~
//            int ctr=DeviceList.length/2;                           //~3204I~//~1Ab8R~
//            for (int ii=0;ii<ctr;ii++)                             //~3204I~//~1Ab8R~
//            {                                                      //~3204I~//~1Ab8R~
//                if (Pdevice.equals(DeviceList[ii*2]))             //~3204I~//~1Ab8R~
//                {                                                  //~3204I~//~1Ab8R~
//                    idx=ii;                                        //~3204I~//~1Ab8R~
//                    break;                                         //~3204I~//~1Ab8R~
//                }                                                  //~3204I~//~1Ab8R~
//            }                                                      //~3204I~//~1Ab8R~
//        }                                                          //~3204I~//~1Ab8R~
//        return idx;                                                //~3204I~//~1Ab8R~
//    }                                                              //~3204I~//~1Ab8R~
//    //******************************************                   //~3203I~//~1Ab8R~
//    private void discovered()                                      //~3203I~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        String[] sa=AG.aBT.getNewDevice();                         //~3203I~//~1Ab8R~
//        if (sa==null)                                              //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            if (swCancelDiscover)                                  //~3205I~//~1Ab8R~
//                infoDiscoverCanceled();                            //~3205I~//~1Ab8R~
//            else                                                   //~3205I~//~1Ab8R~
//                errNoNewDevice();                                      //~3203I~//~3205R~//~1Ab8R~
//            ProgDlg.dismiss();                                     //~3203I~//~1Ab8R~
//            return;                                                //~3203I~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//        int ctr=sa.length/2;                                       //~3203I~//~1Ab8R~
//        int ctr2,addctr=0;                                         //~3206I~//~1Ab8R~
//        if (DeviceList==null)                                      //~3206I~//~1Ab8R~
//            ctr2=0;                                                //~3206I~//~1Ab8R~
//        else                                                       //~3206I~//~1Ab8R~
//            ctr2=DeviceList.length/2;                              //~3206I~//~1Ab8R~
//        for (int ii=0;ii<ctr;ii++)                                 //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            if (Dump.Y) Dump.println("new device add="+sa[ii*2]);  //~3203I~//~1Ab8R~
//            int jj;                                                //~3206I~//~1Ab8R~
//            for (jj=0;jj<ctr2;jj++)                                //~3206I~//~1Ab8R~
//            {                                                      //~3206I~//~1Ab8R~
//                if (sa[ii*2].equals(DeviceList[jj*2]))             //~3206I~//~1Ab8R~
//                    break;                                         //~3206I~//~1Ab8R~
//            }                                                      //~3206I~//~1Ab8R~
//            if (jj==ctr2)                                          //~3206I~//~1Ab8R~
//            {                                                      //~3206I~//~1Ab8R~
//                addctr++;                                          //~3206I~//~1Ab8R~
//                DL.add(sa[ii*2],statusDiscovered,ID_STATUS_DISCOVERED);//~1A6fI~//~1Ab8R~
//                if (Dump.Y) Dump.println("discovered device add="+sa[ii*2]);//~1A6fI~//~1Ab8R~
//            }                                                      //~3206I~//~1Ab8R~
//            else                                                   //~1A6fI~//~1Ab8R~
//            {                                                      //~1A6fI~//~1Ab8R~
//                DL.update(sa[ii*2],statusDiscovered);              //~1A6fI~//~1Ab8R~
//                if (Dump.Y) Dump.println("discovered device update="+sa[ii*2]);//~1A6fI~//~1Ab8R~
//            }                                                      //~1A6fI~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//        if (DeviceList==null)                                      //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            DL.select(0); //first added pos                        //~3203I~//~1Ab8R~
//            DeviceList=sa;                                         //~3203I~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//        else                                                       //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            DL.select(ctr2); //first added pos      //~3203R~      //~3206R~//~1Ab8R~
//            String[] nl=new String[(ctr2+addctr)*2];   //~3203R~   //~3206R~//~1Ab8R~
//            System.arraycopy(DeviceList,0,nl,0,ctr2*2); //~3203R~  //~3206R~//~1Ab8R~
//            addctr=0;                                              //~3206I~//~1Ab8R~
//            for (int ii=0;ii<ctr;ii++)                             //~3206I~//~1Ab8R~
//            {                                                      //~3206I~//~1Ab8R~
//                int jj;                                            //~3206I~//~1Ab8R~
//                for (jj=0;jj<ctr2;jj++)                            //~3206I~//~1Ab8R~
//                {                                                  //~3206I~//~1Ab8R~
//                    if (sa[ii*2].equals(DeviceList[jj*2]))         //~3206I~//~1Ab8R~
//                        break;                                     //~3206I~//~1Ab8R~
//                }                                                  //~3206I~//~1Ab8R~
//                if (jj==ctr2)                                      //~3206I~//~1Ab8R~
//                {                                                  //~3206I~//~1Ab8R~
//                    nl[(ctr2+addctr)*2]=sa[ii*2];                  //~3206R~//~1Ab8R~
//                    nl[(ctr2+addctr)*2+1]=sa[ii*2+1];              //~3206R~//~1Ab8R~
//                    if (Dump.Y) Dump.println("scaned ="+nl[(ctr+addctr)*2]+"="+nl[(ctr+addctr)*2+1]);//~3206I~//~1Ab8R~
//                    addctr++;                                      //~3206I~//~1Ab8R~
//                }                                                  //~3206I~//~1Ab8R~
//            }                                                      //~3206I~//~1Ab8R~
//            DeviceList=nl;                                         //~3203R~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//        ProgDlg.dismiss();                                         //~3203R~//~1Ab8R~
//        infoNewDevice(sa.length/2);                                //~3203R~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
    //******************************************                   //~3203I~
    private boolean connectPartner(boolean Psecure)                //~1A60I~
    {                                                              //~3203I~
//        int idx=getSelected();                                     //~3203I~//~1Ab8R~
//        if (idx==-1)                                               //~3203I~//~1Ab8R~
//            return false;                                          //~3203I~//~1Ab8R~
//        lastSelected=idx;                                          //~3205I~//~1Ab8R~
//        String name=DeviceList[idx*2];                             //~3203R~//~1Ab8R~
//        String addr=DeviceList[idx*2+1];                           //~3203I~//~1Ab8R~
		String name=peerName;                                      //~1Ab8I~
		String addr=peerAddr;                                      //~1Ab8I~
        connectingDevice=name;                                     //~v101I~
        if (Dump.Y) Dump.println("DialogNFCBT:connectPartner name="+name+",addr="+addr);//~1Ab8I~
//      chkBonded(peerAddr);                                       //~1Ab8I~//~1AbnR~
//      Utils.sleep(10000);                                        //~1Ab8I~//~1AbnR~
//      chkBonded(peerAddr);                                       //~1Ab8I~//~1AbnR~
        Utils.sleep(DELAY_BEFORE_CONNECT);                         //~1AbpI~
        if (!chkBonded(addr)) //thread schedule callbackBonded     //~1AbnI~
        {                                                          //~1AbnI~
//  		AView.showToastLong(R.string.WarningCheckBondedThreadStart);//~1AbnR~
//      	chkBondedByThread(name,addr,Psecure);                  //~1AbnR~
    		AView.showToast(R.string.WarningConnectingToNotPaired);//~1AbnI~
        }                                                          //~1AbnI~
//      AG.aBT.connect(name,addr,Psecure);                         //~1A60I~//~1AbgR~
        AG.aBT.connectNFC(name,addr,Psecure);                      //~1AbgI~
        return true;                                               //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~1AbnI~
    public void callbackBonded(boolean Pbonded,String Pname,String Paddr,boolean Psecure)//~1AbnI~
    {                                                              //~1AbnI~
    	if (Dump.Y) Dump.println("callbackBonded bonded="+Pbonded+",name="+Pname+",addr="+Paddr+",secure="+Psecure);//~1AbnI~
    	if (Pbonded)                                                //~1AbnI~
	        AG.aBT.connectNFC(Pname,Paddr,Psecure);                //~1AbnI~
        else                                                       //~1AbnI~
        {                                                          //~1AbnI~
        	int to=CHKBONDED_TIMEOUT/1000;                         //~1AbnI~
        	String msg=AG.resource.getString(R.string.ErrTimeoutChkBonded,to);//~1AbnR~
		    int flag=Alert.BUTTON_POSITIVE;                        //~1AbnI~
			Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbnI~
        }                                                          //~1AbnI~
    }                                                              //~1AbnI~
    //******************************************                   //~1AbnI~
    private boolean chkBonded(String Paddr)                        //~1Ab8I~
    {                                                              //~1Ab8I~
	    BluetoothAdapter mBluetoothAdapter = null;                 //~1Ab8I~
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();  //~1Ab8I~
    	BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(Paddr);//~1Ab8I~
        int status=device.getBondState();                          //~1Ab8I~
        boolean rc=(status==BluetoothDevice.BOND_BONDED);           //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:chkBonded rc="+rc+",addr="+Paddr+",status="+status);//~1Ab8I~
        return rc;                                                 //~1Ab8I~
    }                                                              //~1Ab8I~
    //******************************************                   //~1AbnI~
    private void chkBondedByThread(String Pname,String Paddr,boolean Psecure)//~1AbnR~
    {                                                              //~1AbnI~
    	ChkBondedThread threadCB=new ChkBondedThread(this,Pname,Paddr,Psecure);//~1AbnI~
        threadCB.start();                                          //~1AbnI~
    }                                                              //~1AbnI~
//    //******************************************                   //~1A6fI~//~1Ab8R~
//    private ListData getListData(int Pidx)                         //~1A6fI~//~1Ab8R~
//    {                                                              //~1A6fI~//~1Ab8R~
//        ListData ld;                                               //~1A6fI~//~1Ab8R~
//        try                                                        //~1A6fI~//~1Ab8R~
//        {                                                          //~1A6fI~//~1Ab8R~
//            ld=DL.arrayData.get(Pidx);                             //~1A6fI~//~1Ab8R~
//        }                                                          //~1A6fI~//~1Ab8R~
//        catch(Exception e)                                         //~1A6fI~//~1Ab8R~
//        {                                                          //~1A6fI~//~1Ab8R~
//            Dump.println(e,"BluetoothConnection:getListData");     //~1A6fI~//~1Ab8R~
//            ld=new ListData("OutOfBound",Color.black/*dummy*/);    //~1A6fI~//~1Ab8R~
//        }                                                          //~1A6fI~//~1Ab8R~
//        return ld;                                                 //~1A6fI~//~1Ab8R~
//    }                                                              //~1A6fI~//~1Ab8R~
//    //******************************************                   //~3203I~//~1Ab8R~
//    private int getSelected()                                      //~3203I~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        int idx=DL.getValidSelectedPos();                           //~3203I~//~1Ab8R~
//        if (idx==-1)                                               //~3203I~//~1Ab8R~
//        {                                                          //~3203I~//~1Ab8R~
//            IPConnection.errNotSelected();                         //~3203R~//~1Ab8R~
//        }                                                          //~3203I~//~1Ab8R~
//        return idx;                                                //~3203I~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
    //******************************************                   //~3201I~
	public void afterDismiss(int Pwaiting)                         //~3201I~
    {                                                              //~3201I~
        waitingid=Pwaiting;                                        //~3203I~
    	if (Dump.Y) Dump.println("DialogNFCBT afterDismiss");     //~3201I~//~3204R~//~1Ab8R~
//      if (Pwaiting==R.string.BTAccept)                   //~3201I~//~1AbAR~
        if (Pwaiting==STRID_BTACCEPT)                              //~1AbAI~
        {                                                          //~v101I~
//          String msg=AG.resource.getString(R.string.Msg_WaitingAccept,myDevice);//~v101I~//~1AedR~
            String msg=BluetoothConnection.getMsgStringBTAccepting(myDevice,swSecure);//~1AedI~
			waitingResponse(R.string.Title_WaitingAccept,msg);//~3201I~//~v101R~
        }                                                          //~v101I~
        else                                                       //~3201I~
//      if (Pwaiting==R.string.BTConnect)                     //~3201I~//~1AbAR~
        if (Pwaiting==STRID_BTCONNECT)                             //~1AbAI~
        {                                                          //~v101I~
//          String msg=AG.resource.getString(R.string.Msg_WaitingConnect,connectingDevice);//~v101I~//~1AedR~
            String msg=BluetoothConnection.getMsgStringBTConnecting(connectingDevice,swSecure);//~1AedI~
			waitingResponse(R.string.Title_WaitingConnect,msg);//~3201I~//~v101R~
        }                                                          //~v101I~
        else                                                       //~3203I~
        if (Pwaiting==R.string.Discover)                           //~3203I~
			waitingResponse(R.string.Title_WaitingDiscover,R.string.Msg_WaitingDiscover);//~3203I~
        else                                                       //~3207I~
        if (Pwaiting==R.string.BTDisConnect)                       //~3207I~
			disconnectPartner();                               //~3201I~//~3207I~
    	if (Dump.Y) Dump.println("DialogNFCBT afterDismiss return");//~3207I~//~1Ab8R~
        AG.aBTConnection=null;                                     //~1A6kI~
    	stopCBThread();                                            //~1AbnI~
        SdialogNFC=null;                                           //~1Ab8I~
    }                                                              //~3201I~
    //******************************************                   //~3201I~
	private void waitingResponse(int Ptitleresid,int Pmsgresid)    //~3201R~
    {                                                              //~3201I~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,true/*cancel CB*/,Ptitleresid,Pmsgresid,true/*cancelable*/);//~1A2jI~
    }                                                              //~3201I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,String Pmsg)      //~v101I~
    {                                                              //~v101I~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,true/*cancel CB*/,Ptitleresid,Pmsg,true/*cancelable*/);//~1A2jI~
    }                                                              //~v101I~
    //******************************************                   //~3201I~
	private void errNoThread()                                     //~3201I~
    {                                                              //~3201I~
    	AView.showToast(R.string.ErrNoThread);                     //~3201R~
    }                                                              //~3201I~
//    //******************************************                   //~3203I~//~1Ab8R~
//    private void errNoNewDevice()                                  //~3203I~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        AView.showToast(R.string.ErrNoNewDevice);                  //~3203I~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
//    //******************************************                   //~3206I~//~1Ab8R~
//    private void errConnectingForScan()                            //~3206I~//~1Ab8R~
//    {                                                              //~3206I~//~1Ab8R~
//        AView.showToast(R.string.ErrConnectingForScan);            //~3206I~//~1Ab8R~
//    }                                                              //~3206I~//~1Ab8R~
//    private void infoDiscoverCanceled()                             //~3205I~//~1Ab8R~
//    {                                                              //~3205I~//~1Ab8R~
//        AView.showToast(R.string.InfoDiscoverCanceled);            //~3205I~//~1Ab8R~
//    }                                                              //~3205I~//~1Ab8R~
//    //******************************************                   //~3203I~//~1Ab8R~
//    private void infoNewDevice(int Pctr)                           //~3203I~//~1Ab8R~
//    {                                                              //~3203I~//~1Ab8R~
//        AView.showToast(AG.resource.getString(R.string.InfoNewDevice,Pctr));//~3203I~//~1Ab8R~
//    }                                                              //~3203I~//~1Ab8R~
    //******************************************                   //~3203I~
    //*reason:0:cancel,1:dismiss                                   //~3203I~
    //******************************************                   //~3203I~
    @Override                                                      //~3203I~
	public void onCancelProgDlg(int Preason)                       //~3203I~
    {                                                              //~3203I~
    	if (Dump.Y) Dump.println("onCancelProgDlgI reason="+Preason);//~3203I~
        if (Preason==0)	//cancel                                   //~3203I~
        {                                                          //~3203I~
	    	if (Dump.Y) Dump.println("onCancelProgDlgI waitingID="+Integer.toHexString(waitingid));//~3203I~
//            if (waitingid==R.string.Discover)                       //~3203I~//~1Ab8R~
//                cancelDiscover();                                 //~3203I~//~1Ab8R~
        }                                                          //~3203I~
    }                                                              //~3203I~
//    class ListBT extends List                                                  //~1114//~1A6fI~//~1Ab8R~
//    {                                                              //~1A6fI~//~1Ab8R~
//    //*****************                                            //~1A6fI~//~1Ab8R~
//        public ListBT(Container Pcontainer,int Presid,int Prowresid)//~1A6fI~//~1Ab8R~
//        {                                                          //~1A6fI~//~1Ab8R~
//            super(Pcontainer,Presid,Prowresid);                    //~1A6fI~//~1Ab8R~
//        }                                                          //~1A6fI~//~1Ab8R~
//    //**********************************************************************//~1A6fI~//~1Ab8R~
//        @Override                                                  //~1A6fI~//~1Ab8R~
//        public View getViewCustom(int Ppos, View Pview,ViewGroup Pparent)//~1A6fI~//~1Ab8R~
//        {                                                          //~1A6fI~//~1Ab8R~
//        //*******************                                      //~1A6fI~//~1Ab8R~
//            if (Dump.Y) Dump.println("ListBT:getViewCustom Ppos="+Ppos+"CheckedItemPos="+((ListView)Pparent).getCheckedItemPosition());//~1A6fI~//~1Ab8R~
//            View v=Pview;                                          //~1A6fI~//~1Ab8R~
//            if (v == null)                                         //~1A6fI~//~1Ab8R~
//            {                                                      //~1A6fI~//~1Ab8R~
//                v=AG.inflater.inflate(rowId/*super*/,null);            //~1A65I~//~1A6fI~//~1Ab8R~
//            }                                                      //~1A6fI~//~1Ab8R~
//            TextView v1=(TextView)v.findViewById(BTROW_NAME);      //~1A6fI~//~1Ab8R~
//            TextView v2=(TextView)v.findViewById(BTROW_STATUS);    //~1A6fI~//~1Ab8R~
//            if (font!=null)                                        //~1A6fI~//~1Ab8R~
//            {                                                      //~1A6fI~//~1Ab8R~
//                font.setFont(v1);                                  //~1A6fI~//~1Ab8R~
//                font.setFont(v2);                                  //~1A6fI~//~1Ab8R~
//            }                                                      //~1A6fI~//~1Ab8R~
//            ListData ld=getListData(Ppos);                         //~1A6fI~//~1Ab8R~
//            v1.setText(ld.itemtext);                               //~1A6fI~//~1Ab8R~
//            if (Ppos==selectedpos)                                 //~1A6fI~//~1Ab8R~
//            {                                                      //~1A6fI~//~1Ab8R~
//                v1.setBackgroundColor(bgColorSelected.getRGB());   //~1A6fI~//~1Ab8R~
//                v1.setTextColor(bgColor.getRGB());                 //~1A6fI~//~1Ab8R~
//            }                                                      //~1A6fI~//~1Ab8R~
//            else                                                   //~1A6fI~//~1Ab8R~
//            {                                                      //~1A6fI~//~1Ab8R~
//                v1.setBackgroundColor(bgColor.getRGB());           //~1A6fI~//~1Ab8R~
//                v1.setTextColor(ld.itemcolor.getRGB());            //~1A6fI~//~1Ab8R~
//            }                                                      //~1A6fI~//~1Ab8R~
//            String status;                                         //~1A6fI~//~1Ab8R~
//            if (ld.itemtext2==null)                                //~1A6fI~//~1Ab8R~
//                status="";                                         //~1A6fI~//~1Ab8R~
//            else                                                   //~1A6fI~//~1Ab8R~
//                status=ld.itemtext2;                               //~1A6fI~//~1Ab8R~
//            v2.setText(status);                                    //~1A6fI~//~1Ab8R~
//            v2.setBackgroundColor(bgColor.getRGB());               //~1A6fI~//~1Ab8R~
//            if (status.equals(statusPaired))                       //~1A6fI~//~1Ab8R~
//                v2.setTextColor(COLOR_STATUS_PAIRED.getRGB());     //~1A6fI~//~1Ab8R~
//            else                                                   //~1A6fI~//~1Ab8R~
//            if (status.equals(statusConnected))                    //~1A6kR~//~1Ab8R~
//                v2.setTextColor(COLOR_STATUS_CONNECTED.getRGB());  //~1A6kR~//~1Ab8R~
//            else                                                   //~1A6kR~//~1Ab8R~
//                v2.setTextColor(COLOR_STATUS_DISCOVERED.getRGB()); //~1A6fI~//~1Ab8R~
//            return v;                                              //~1A6fI~//~1Ab8R~
//        }                                                          //~1A6fI~//~1Ab8R~
//    }//class                                                       //~1A6fI~//~1Ab8R~
	//*************************************************************************//~1Ab8I~
    private void init()                                            //~1Ab8I~
    {                                                              //~1Ab8I~
        chkNFCenabled();                                           //~1Ab8I~
 		mimetype=("application/"+AG.pkgName).toLowerCase(Locale.ENGLISH);//~1Ab8I~
//        portNo=getPortNo();                                      //~1Ab8I~
        getLocalMacAddr();                                         //~1Ab8I~
        SconflictMsg=null;                                         //~1AbmI~
    }                                                              //~1Ab8I~
    //******************************************                   //~1Ab8I~
	private void chkNFCenabled()                                   //~1Ab8I~
    {                                                              //~1Ab8I~
        NfcManager mgr=(NfcManager)AG.context.getSystemService(Context.NFC_SERVICE);//~1Ab8I~
        NfcAdapter mNfcAdapter = mgr.getDefaultAdapter();                       //~1Ab8I~
        if (mNfcAdapter==null)                                     //~1Ab8I~
	    	AView.showToast(R.string.ErrNoNFCAttachment);          //~1Ab8I~
        else                                                       //~1Ab8I~
        if (!mNfcAdapter.isEnabled())                              //~1Ab8I~
	    	AView.showToast(R.string.ErrNFCDisabled);              //~1Ab8I~
        else                                                       //~1Ab8I~
        	isNFCenabled=true;                                     //~1Ab8I~
    }                                                              //~1Ab8I~
    //******************************************                   //~1Ab8I~
    private void getLocalMacAddr()                                 //~1Ab8I~
    {                                                              //~1Ab8I~
	    BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();//~1Ab8I~
        if (adapter!=null)                                         //~1Ab8I~
        {                                                          //~1Ab8I~
        	deviceAddr=adapter.getAddress();                       //~1Ab8I~
        	deviceName=adapter.getName();                          //~1Ab8I~
        }                                                          //~1Ab8I~
        else                                                       //~1Ab8I~
	        AView.showToastLong(R.string.noBTadapter);             //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*from DialogNFCSelect                                        //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    public static NdefMessage createNFCMsg(NfcEvent Pevent)        //~1Ab8I~
    {                                                              //~1Ab8I~
    	if (Dump.Y) Dump.println("DialogNFCBT:createNFCMsg");      //~1Ab8R~
//      if (SdialogNFC==null)                                      //~1Ab8I~//~1Af2R~
        if (SdialogNFC==null                                       //~1Af2I~
        ||  AG.activeSessionType!=0)                               //~1Af2I~
        {                                                          //~1Ab8I~
			new Message(Global.frame(),R.string.InfoCreateNullNFCMsg);//~1Ab8I~
        	return null;                                           //~1Ab8I~
        }                                                          //~1Ab8I~
        SdialogNFC.swTouch=true;	//NFC contact                  //~1Ab8I~
        NdefMessage msg=null;                                      //~1Ab8I~
        try                                                        //~1Ab8I~
        {                                                          //~1Ab8I~
          if (swOOB)                                               //~1Ab8I~
          {                                                        //~1Ab8I~
            msg=createNFCMsgOOB(Pevent);                        //~1Ab8I~
          }                                                        //~1Ab8I~
          else                                                     //~1Ab8I~
          {                                                        //~1Ab8I~
    		NdefRecord mime=createAppRecord();                     //~1Ab8I~
        	NdefRecord records[]=new NdefRecord[]{mime};           //~1Ab8I~
			msg = new NdefMessage(records);                        //~1Ab8I~
          }                                                        //~1Ab8I~
		}                                                          //~1Ab8I~
        catch (Exception e)                                        //~1Ab8I~
        {                                                          //~1Ab8I~
			Dump.println(e,"DialogNFCBT:createNFCMessage");        //~1Ab8R~
        	return null;                                           //~1Ab8I~
		}                                                          //~1Ab8I~
        return msg;                                                //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    public static NdefRecord createAppRecord()                     //~1Ab8I~
    {                                                              //~1Ab8I~
		    String macaddr="";                                     //~1Ab8I~
            String addr=SdialogNFC.deviceAddr;                     //~1Ab8I~
            String name=SdialogNFC.deviceName;                     //~1Ab8I~
            SdialogNFC.swSecure=SdialogNFC.getSecureOption();      //~1Af4I~
    		SdialogNFC.setToPropSecureOption(SdialogNFC.swSecure); //~1Af4I~
//          WifiManager mgr=(WifiManager)AG.context.getSystemService(Context.WIFI_SERVICE);//~1Ab8I~
//		    if (Dump.Y) Dump.println("DialogNFCBT:wifiinfo="+((WifiInfo)mgr.getConnectionInfo()).toString());//~1Ab8I~
//            if (addr==null)                                      //~1Ab8I~
//            {                                                    //~1Ab8I~
//                WifiInfo info=mgr.getConnectionInfo();           //~1Ab8I~
//                if (Dump.Y) Dump.println("DialogNFC:addr from ConnectionInfo="+info.getMacAddress());//~1Ab8I~
//                macaddr=DATA_PREFIX+info.getMacAddress();//dis addr is for wifi but nbot wifi-direct(it can be make to by 0x20(Local) bit on)//~1Ab8I~
//            }                                                    //~1Ab8I~
//            else                                                 //~1Ab8I~
//            {                                                    //~1Ab8I~
                if (name!=null && !name.equals(""))                //~1Ab8I~
	        		macaddr=DATA_PREFIX+addr+DATA_SEP+name;        //~1Ab8I~
                else                                               //~1Ab8I~
	        		macaddr=DATA_PREFIX+addr+DATA_SEP;             //~1Ab8I~
                macaddr+=DATA_SEP+(SdialogNFC.swSecure?"1":"0");   //~1Ab8I~
//            }                                                    //~1Ab8I~
	 		String mimetype=SdialogNFC.mimetype;                   //~1Ab8I~
    	    String text=macaddr;                                   //~1Ab8I~
	   	    if (Dump.Y) Dump.println("DialogNFCBT:createAppRecord mime="+mimetype+",text="+text);//~1Ab8I~
//      	NdefRecord mime=NdefRecord.createMime(MIMETYPE,text.getBytes());//~API16//~1Ab8I~
        	NdefRecord mime=SdialogNFC.createMimeRecord(mimetype,text.getBytes());//~API16//~1Ab8I~
            return mime;                                           //~1Ab8I~
    }	                                                           //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*from DialogNFCSelect:onNdefPushComplete                     //~1Ab8R~
	//*************************************************************************//~1Ab8I~
    public static void sendNFCMsg(NfcEvent Pevent)                 //~1Ab8I~
    {                                                              //~1Ab8I~
    	if (Dump.Y) Dump.println("DialogNFCBT:sendNFCMsg");        //~1Ab8R~
        if (SdialogNFC==null)                                      //~1Ab8I~
        	return;                                                //~1Ab8I~
        try                                                        //~1Ab8I~
        {                                                          //~1Ab8I~
    		DialogNFCBT dlg=SdialogNFC;                              //~1Ab8I~
        	if (dlg==null)                                         //~1Ab8I~
        		return;                                            //~1Ab8I~
//  		if (dlg.thisDevice==null)	//not received broadcast msg//~1Ab8R~
    		if (dlg.deviceAddr==null)	//not received broadcast msg//~1Ab8I~
        		return;                                            //~1Ab8I~
//            SdialogNFC.discover(false);//to enable connect from macaddr receiver//~1Ab8R~
			dlg.startServerNFC();                                  //~1Ab8I~
	        dlg.swTouch=true;	//NFC contact                      //~1Ab8I~
		}                                                          //~1Ab8I~
        catch (Exception e)                                        //~1Ab8I~
        {                                                          //~1Ab8I~
			Dump.println(e,"DialogNFCBT:createNFCMessage");        //~1Ab8R~
		}                                                          //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private void startServerNFC()                                  //~1Ab8I~
    {                                                              //~1Ab8I~
    	if (Dump.Y) Dump.println("DialogNFCBT:startServerNFC");    //~1Ab8I~
//	    setDiscoverable();                                         //~1Ab8R~
//  	doAction(AG.resource.getString(R.string.BTAccept));        //~1Ab8R~//~1AbAR~
    	doAction(AG.resource.getString(STRID_BTACCEPT));           //~1AbAI~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*NdefRecord.createMime is from API16                         //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {//~1Ab8I~
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));//~1Ab8I~
        NdefRecord mimeRecord = new NdefRecord(                    //~1Ab8I~
//              NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);//~1Ab8I~
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, null/*api20*/, payload);//~1Ab8I~
        return mimeRecord;                                         //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*from AMain                                                  //~1Ab8I~
	//*return true if processed intent                             //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    public static boolean onNewIntent(Intent Pintent)              //~1Ab8I~
    {                                                              //~1Ab8I~
    	DialogNFCBT dlg=SdialogNFC;                                  //~1Ab8I~
        if (dlg==null)                                             //~1Ab8I~
        	return false;                                          //~1Ab8I~
//  	if (dlg.thisDevice==null)	//not received broadcast msg   //~1Ab8R~
    	if (dlg.deviceAddr==null)	//not received broadcast msg   //~1Ab8I~
        	return false;                                          //~1Ab8I~
	    dlg.swTouch=true;	//NFC contact                          //~1Ab8I~
        return dlg.processIntent(Pintent);                         //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private boolean processIntent(Intent Pintent)                  //~1Ab8I~
    {                                                              //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:processIntent:discovered tag width intent:"+Pintent);//~1Ab8R~
        try                                                        //~1Ab8I~
        {                                                          //~1Ab8I~
          if (swOOB)                                               //~1Ab8I~
          {                                                        //~1Ab8I~
            parseNFCDataOOB(Pintent);                              //~1Ab8I~
          }                                                        //~1Ab8I~
          else                                                     //~1Ab8I~
          {                                                        //~1Ab8I~
        	if (AG.activeSessionType!=0)                           //~1AbsR~
            {                                                      //~1AbsR~
                String msg=AG.resource.getString(R.string.WarningIgnoredNewIntentConnected);//~1AbsR~
		    	int flag=Alert.BUTTON_POSITIVE;                    //~1AbsR~
				Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbsR~
            	return false;                                      //~1AbsR~
            }                                                      //~1AbsR~
            String nfcdata=getNFCData(Pintent);                    //~1Ab8I~
//  		if (nfcdata==null || !nfcdata.startsWith(DATA_PREFIX)) //~1Ab8I~//~1AbkR~
    		if (nfcdata==null)                                     //~1AbkI~
            	return false;                                      //~1Ab8I~
    		if (nfcdata.startsWith(DialogNFC.DATA_PREFIX))         //~1AbkI~
            {                                                      //~1AbkI~
                String msg=AG.resource.getString(R.string.WarningIgnoredNewIntentConflict);//~1AbkI~
		    	int flag=Alert.BUTTON_POSITIVE;                    //~1AbkI~
				Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbkI~
            	return false;                                      //~1AbkI~
            }                                                      //~1AbkI~
    		if (!nfcdata.startsWith(DATA_PREFIX))                  //~1AbkI~
            	return false;                                      //~1AbkI~
            peerAddr=nfcdata.substring(DATA_PREFIX.length());      //~1Ab8I~
            int offs=peerAddr.indexOf(DATA_SEP);                   //~1Ab8I~
            if (offs<=0)                                           //~1Ab8R~
            	return false;                                      //~1Ab8I~
            peerName=peerAddr.substring(offs+1);                   //~1Ab8I~
            peerAddr=peerAddr.substring(0,offs);                   //~1Ab8I~
            offs=peerName.indexOf(DATA_SEP);                       //~1Ab8I~
            if (offs<=0)                                           //~1Ab8I~
            	return false;                                      //~1Ab8I~
            String opt=peerName.substring(offs+1);                 //~1Ab8I~
            peerName=peerName.substring(0,offs);                   //~1Ab8I~
//          swSecure=opt.equals("1");                              //~1Ab8R~//~1AbmR~
            boolean swsecure=opt.equals("1");                      //~1AbmR~
            swSecure=getSecureOption(); //chjeckbox status at intent received//~1Af4I~
    		setToPropSecureOption(swSecure);                       //+1Af4I~
    		chkSecureLevel(swSecure,swsecure);                      //~1AbmI~
            swSecure=swsecure;                                     //~1AbmI~
          }                                                        //~1Ab8I~
        	if (peerName.equals(""))                               //~1Ab8I~
        		peerName=AG.resource.getString(R.string.UnknownDeviceName);//~1Ab8I~
	        if (Dump.Y) Dump.println("DialogNFCBT:processIntent:addr="+peerAddr+",name="+peerName+",secure="+swSecure);//~1Ab8I~
            receivedFromPeer();                                    //~1Ab8I~
        }                                                          //~1Ab8I~
        catch (Exception e)                                        //~1Ab8I~
        {                                                          //~1Ab8I~
			Dump.println(e,"DialogNFCBT:processIntent");           //~1Ab8R~
           	return false;                                          //~1Ab8I~
        }                                                          //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:processIntent:peerAddr="+peerAddr+",peerName="+peerName);//~1Ab8I~
        return true;                                               //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private String getNFCData(Intent Pintent)                      //~1Ab8I~
    {                                                              //~1Ab8I~
    	Parcelable[] rawmsgs=Pintent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);//~1Ab8I~
        NdefMessage msg=(NdefMessage)rawmsgs[0];                   //~1Ab8I~
        NdefRecord[] recs=msg.getRecords();                        //~1Ab8I~
        NdefRecord rec0=recs[0];                                   //~1Ab8I~
        String msgout=new String(rec0.getPayload());               //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:getNFCData:"+msgout);//~1Ab8R~
        return msgout;                                             //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private void receivedFromPeer()                                //~1Ab8I~
    {                                                              //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:receivedFromPeer");  //~1Ab8R~
//        if (!discover(true))                                     //~1Ab8R~
//            return;                                              //~1Ab8R~
//      setDiscoverable();                                         //~1Ab8R~
//  	if (swSecure)                                              //~1Ab8I~//~1AbAR~
//  		doAction(AG.resource.getString(R.string.BTConnectSecure));//~1Ab8R~//~1AbAR~
//      else                                                       //~1Ab8I~//~1AbAR~
//  		doAction(AG.resource.getString(R.string.BTConnect));   //~1Ab8I~//~1AbAR~
  		doAction(AG.resource.getString(STRID_BTCONNECT));          //~1AbAI~
    }                                                              //~1Ab8I~
//    //*************************************************************************//~1Ab8R~
//    private void setDiscoverable()                               //~1Ab8R~
//    {                                                            //~1Ab8R~
//        AG.aBT.setDiscoverable();                                //~1Ab8R~
//    }                                                            //~1Ab8R~
	//*************************************************************************//~1Ab8I~
	//*from DialogNFCSelect                                        //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    public static NdefMessage createNFCMsgOOB(NfcEvent Pevent)     //~1Ab8I~
    {                                                              //~1Ab8I~
    	                                                           //~1Ab8I~
        NdefRecord r0=createHandoverRequestRecord();               //~1Ab8R~
//		NdefRecord r1=createBluetoothOobDataRecord();              //~1Ab8I~
		NdefRecord r2=createAppRecord();                           //~1Ab8I~
//      NdefRecord[] records=new NdefRecord[]{r0,r1,r2};           //~1Ab8R~
        NdefRecord[] records=new NdefRecord[]{r2,r0};              //~1Ab8I~
		NdefMessage msg = new NdefMessage(records);                //~1Ab8R~
        if (Dump.Y) Dump.println("DialogNFCBT:createNFCMsgOOB",msg.toByteArray());   //~1Ab8I~
        return msg;                                                //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*from DialogNFCSelect                                        //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private static NdefRecord createBluetoothOobDataRecord()       //~1Ab8I~
    {                                                              //~1Ab8I~
    	if (Dump.Y) Dump.println("DialogNFCBT:createNFCMsgOOB");   //~1Ab8I~
        int len,offs;                                              //~1Ab8I~
        String addr=SdialogNFC.deviceAddr;                         //~1Ab8I~
        String name=SdialogNFC.deviceName;                         //~1Ab8I~
		if (Dump.Y) Dump.println("DialogNFCBTOOB:addr="+addr+",name="+name);//~1Ab8I~
        byte[] bytename;
		try {
			bytename = name.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			bytename=name.getBytes();
		}                     //~1Ab8R~
//        byte[] byteprefix=DATA_PREFIX.getByte("UTF-8");          //~1Ab8I~
        int payloadsz=(2+6)+(1+1+2)+(1+1+3)+(1+1+bytename.length);	//len(2)+macaddr(6)//~1Ab8R~
//      payloadsz+=1+byteprefix+bytename.length();	//len(2)+macaddr(6)+secure(1)+name.length;//~1Ab8I~
        byte[] payload=new byte[payloadsz];                        //~1Ab8I~
    	payload[1]=0;                                              //~1Ab8R~
    	payload[0]=(byte)payload.length;   //little endian         //~1Ab8R~
        byte[] byteaddr=addressToReverseBytes(addr);               //~1Ab8I~
	    System.arraycopy(byteaddr,0,payload,2,6);                  //~1Ab8I~
        offs=(2+6);                                                //~1Ab8I~
	    payload[offs]=0x03;    //EIR data len                      //~1Ab8I~
	    payload[offs+1]=0x03;  //EIR data type:16bit srervice class UUID//~1Ab8I~
	    payload[offs+2]=0x01;  //1101:SPP(Serial port profile)     //~1Ab8I~
	    payload[offs+3]=0x11;  //                                  //~1Ab8I~
        offs+=(1+3);                                               //~1Ab8R~
        payload[offs]=0x04;    //EIR data len                      //~1Ab8R~
        payload[offs+1]=0x0d;     //EIR data type:class of device  //~1Ab8R~
        payload[offs+2]=0x0c;   //CoD 40020c    40:telephony,02:phone,0c:smart phone//~1Ab8R~
        payload[offs+3]=0x02;   //                                 //~1Ab8R~
        payload[offs+4]=0x40;   //                                 //~1Ab8R~
        offs+=(1+4);                                               //~1Ab8R~
        len=bytename.length;                                       //~1Ab8I~
	    payload[offs]=(byte) (len+1);    //EIR data len                     //~1Ab8I~
	    payload[offs+1]=0x09;     //EIR data type:localname        //~1Ab8I~
        System.arraycopy(bytename,0,payload,offs+2,len);          //~1Ab8I~
//      payload[2+6]=(byte)(swSecure?'1':'0');                     //~1Ab8I~
//      System.arraycopy(byteprefix,0,payload+2+6+1,byteprefix.length);//~1Ab8I~
//      System.arraycopy(bytename,0,payload+2+6+1+byteprefix.length,bytename.length);//~1Ab8I~
		NdefRecord r1=new NdefRecord(NdefRecord.TNF_MIME_MEDIA,TYPE_BT_OOB,//~1Ab8I~
//  			new byte[]{'b'},                                   //~1Ab8R~
    			new byte[]{'0'},   //as asmple                     //~1Ab8I~
				payload);                                          //~1Ab8I~
        return r1;                                                 //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private static NdefRecord createHandoverRequestRecord()        //~1Ab8I~
    {                                                              //~1Ab8I~
//      NdefMessage nestedMessage = new NdefMessage(createCollisionRecord(),//~1Ab8R~
//              createBluetoothAlternateCarrierRecord(false));     //~1Ab8R~
        NdefRecord[] records=new NdefRecord[]{createCollisionRecord(),createBluetoothAlternateCarrierRecord(false)};//~1Ab8R~
        NdefMessage nestedMessage = new NdefMessage(records);      //~1Ab8R~
        byte[] nestedPayload = nestedMessage.toByteArray();        //~1Ab8I~
        ByteBuffer payload = ByteBuffer.allocate(nestedPayload.length + 1);//~1Ab8I~
        payload.put((byte)0x12);  // connection handover v1.2      //~1Ab8I~
        payload.put(nestedMessage.toByteArray());                  //~1Ab8I~
        byte[] payloadBytes = new byte[payload.position()];        //~1Ab8I~
        payload.position(0);                                       //~1Ab8I~
        payload.get(payloadBytes);                                 //~1Ab8I~
        NdefRecord r0=new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_HANDOVER_REQUEST, null,//~1Ab8I~
                payloadBytes);                                     //~1Ab8I~
        return r0;                                                 //~1Ab8I~
    }                                                              //~1Ab8I~
    private static NdefRecord createCollisionRecord()              //~1Ab8I~
    {                                                              //~1Ab8I~
        byte[] random = new byte[2];                               //~1Ab8I~
//      new Random().nextBytes(random);                            //~1Ab8R~
        random[0]=0x01; //as sample                                //~1Ab8I~
        random[1]=0x02;                                            //~1Ab8I~
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, RTD_COLLISION_RESOLUTION, null, random);//~1Ab8I~
    }                                                              //~1Ab8I~
    private static NdefRecord createBluetoothAlternateCarrierRecord(boolean activating)//~1Ab8I~
	{                                                              //~1Ab8I~
		byte[] payload = new byte[4];                              //~1Ab8I~
		payload[0] = (byte) (activating ? CARRIER_POWER_STATE_ACTIVATING ://~1Ab8I~
			CARRIER_POWER_STATE_ACTIVE);  // Carrier Power State: Activating or active//~1Ab8I~
		payload[1] = 1;   // length of carrier data reference      //~1Ab8I~
//  	payload[2] = 'b'; // carrier data reference: ID for Bluetooth OOB data record//~1Ab8R~
    	payload[2] = '0'; // as sample                             //~1Ab8I~
		payload[3] = 0;  // Auxiliary data reference count         //~1Ab8I~
		return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_ALTERNATIVE_CARRIER, null, payload);//~1Ab8I~
	}                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    private void parseNFCDataOOB(Intent Pintent)                  //~1Ab8I~
    {                                                              //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:parseNFCDataOOB");   //~1Ab8I~
    	Parcelable[] rawmsgs=Pintent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);//~1Ab8I~
        NdefMessage msg=(NdefMessage)rawmsgs[0];                   //~1Ab8I~
        NdefRecord[] recs=msg.getRecords();                        //~1Ab8I~
        NdefRecord rec0=recs[0];                                   //~1Ab8I~
        ByteBuffer bbpayload=ByteBuffer.wrap(rec0.getPayload());   //~1Ab8I~
        bbpayload.position(2);                                     //~1Ab8I~
        byte [] byteaddr=new byte[6];                              //~1Ab8I~
        bbpayload.get(byteaddr);                                   //~1Ab8I~
        for (int ii=0;ii<3;ii++)	//LitteleEndian to Big Endian  //~1Ab8I~
        {                                                          //~1Ab8I~
        	byte tmp=byteaddr[ii];                                 //~1Ab8I~
        	byteaddr[ii]=byteaddr[5-ii];;                          //~1Ab8I~
        	byteaddr[5-ii]=tmp;                                    //~1Ab8I~
        }                                                          //~1Ab8I~
        peerAddr=Utils.getMacString(byteaddr);                      //~1Ab8I~
        if (Dump.Y) Dump.println("DialogNFCBT:parseNFCDataOOB macaddr="+peerAddr);//~1Ab8I~
        swSecure=true;                                             //~1Ab8I~
        peerName="";                                               //~1Ab8I~
        if (peerName.equals(""))                                   //~1Ab8I~
        	peerName=AG.resource.getString(R.string.UnknownDeviceName);//~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    static byte[] addressToReverseBytes(String address)            //~1Ab8I~
    {                                                              //~1Ab8I~
        String[] split = address.split(":");                       //~1Ab8I~
        byte[] result = new byte[split.length];                    //~1Ab8I~
        for (int i = 0; i < split.length; i++) {                   //~1Ab8I~
            result[split.length - 1 - i] = (byte)Integer.parseInt(split[i], 16);//~1Ab8I~
        }                                                          //~1Ab8I~
        return result;                                             //~1Ab8I~
    }                                                              //~1Ab8I~
	//***********************************************************************************//~1AbjI~
	//*from PartnerFrame                                           //~1AbjI~
	//***********************************************************************************//~1AbjI~
    public static void closeDialog()                               //~1AbjI~
    {                                                              //~1AbjI~
    	if (Dump.Y) Dump.println("DialogNFCBT:closeDialog");       //~1AbjI~
        if (SdialogNFC!=null)                                      //~1AbjR~
        {                                                          //~1AbjI~
	    	if (Dump.Y) Dump.println("DialogNFCBT:closeDialog dismiss");//~1AbjI~
        	SdialogNFC.dismiss();                                  //~1AbjI~
        }                                                          //~1AbjI~
    }                                                              //~1AbjI~
	//***********************************************************************************//~1AbmI~
	//*from ABT at after GameQuestion                              //~1AbmR~
	//*chk secure level of requester and receiver                  //~1AbmI~
	//***********************************************************************************//~1AbmI~
    private void chkSecureLevel(boolean Poption,boolean Prequest)  //~1AbmR~
    {                                                              //~1AbmI~
        if (Poption==Prequest)                                     //~1AbmR~
            return;                                                //~1AbmR~
        String msg=AG.resource.getString(R.string.WarningNFCBTSecureLevelConflict,Prequest?"Secure":"InSecure",Poption?"Secure":"InSecure");//~1AbmR~
        SconflictMsg=msg;                                          //~1AbmR~
    }                                                              //~1AbmI~
    public static void chkSecureLevelMsg()                         //~1AbmI~
    {                                                              //~1AbmI~
        if (SconflictMsg==null)                                    //~1AbmI~
            return;                                                //~1AbmI~
        int flag=Alert.BUTTON_POSITIVE;                            //~1AbmI~
        String msg=SconflictMsg;                                   //~1AbmI~
        Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbmI~
        SconflictMsg=null;                                         //~1AbmI~
    }                                                              //~1AbmI~
//***************************************************************************//~1AbnI~
    private void stopCBThread()                                    //~1AbnR~
	{                                                              //~1AbnI~
        ChkBondedThread t=threadCB;                          //~1AbnI~
        if (t!=null && t.isAlive())                               //~1AbnI~
        {                                                          //~1AbnI~
            t.cancel();                                            //~1AbnI~
            try                                                    //~1AbnI~
            {                                                      //~1AbnI~
                Thread.sleep(CHKBONDED_INTERVAL*2); //600ms        //~1AbnI~
            }                                                      //~1AbnI~
            catch(InterruptedException e)                          //~1AbnI~
            {                                                      //~1AbnI~
            }                                                      //~1AbnI~
        }                                                          //~1AbnI~
    }                                                              //~1AbnI~
//***************************************************************************//~1AbnI~
    private class ChkBondedThread extends Thread implements UiThreadI//~1AbnR~
    {                                                              //~1AbnI~
        private String remoteAddr,remoteName;                      //~1AbnI~
        private DialogNFCBT nfcBT;                                 //~1AbnI~
        private boolean swCancel,swBonded,optSecure;               //~1AbnI~
    //***********************************************              //~1AbnI~
        public ChkBondedThread(DialogNFCBT PnfcBT,String Pname,String Paddr,boolean Psecure)//~1AbnI~
		{                                                          //~1AbnI~
            remoteName=Pname;                                     //~1AbnI~
            remoteAddr=Paddr;                                      //~1AbnI~
            optSecure=Psecure;                                    //~1AbnI~
            nfcBT=PnfcBT;                                          //~1AbnI~
            if (Dump.Y) Dump.println("chkBondedThread remoteAddr="+remoteAddr);//~1AbnI~
        }                                                          //~1AbnI~
        public void run()                                          //~1AbnI~
        {                                                          //~1AbnI~
            if (Dump.Y) Dump.println("ChkBondedThread:run()");     //~1AbnR~
            int sleepctr=0;                                        //~1AbnI~
            while (!swBonded)                                      //~1AbnR~
			{                                                      //~1AbnI~
                try                                                //~1AbnI~
                {                                                  //~1AbnI~
                  	Thread.sleep(CHKBONDED_INTERVAL); //300ms      //~1AbnR~
                }                                                  //~1AbnI~
                catch(InterruptedException e)                      //~1AbnI~
                {                                                  //~1AbnI~
                }                                                  //~1AbnI~
                sleepctr++;                                        //~1AbnI~
				if (swCancel)                                      //~1AbnI~
                	break;                                         //~1AbnI~
                try                                                //~1AbnI~
				{                                                  //~1AbnI~
    				swBonded=chkBonded(remoteAddr);                //~1AbnI~
                }                                                  //~1AbnI~
				catch (Exception e)                                //~1AbnI~
				{                                                  //~1AbnI~
	                Dump.println(e,"ChkBondedThread run()");       //~1AbnI~
                    break;                                         //~1AbnI~
                }                                                  //~1AbnI~
                if (CHKBONDED_TIMEOUT<=CHKBONDED_INTERVAL*sleepctr)//~1AbnI~
                	break;                                         //~1AbnI~
				if (swCancel)                                      //~1AbnI~
                	break;                                         //~1AbnI~
            }//while                                               //~1AbnI~
            if (Dump.Y) Dump.println("END ChkBondedThread bonded="+swBonded+",time="+(sleepctr*CHKBONDED_INTERVAL));//~1AbnI~
            DialogNFCBT.threadCB=null;                             //~1AbnR~
            try                                                    //~1AbnI~
			{                                                      //~1AbnI~
	            callback();                                        //~1AbnR~
            }                                                      //~1AbnI~
			catch (Exception e)                                    //~1AbnI~
			{                                                      //~1AbnI~
	        	Dump.println(e,"ChkBondedThread callbackBonded");  //~1AbnI~
            }                                                      //~1AbnI~
        }                                                          //~1AbnI~
        public void cancel()                                       //~1AbnI~
		{                                                          //~1AbnI~
            if (Dump.Y) Dump.println("chkBondedThread:cancel");    //~1AbnI~
            swCancel=true;                                         //~1AbnI~
        }                                                          //~1AbnI~
        private void callback()                                         //~1AbnI~
		{                                                          //~1AbnI~
            if (Dump.Y) Dump.println("chkBondedThread:callback");  //~1AbnI~
	        UiThread.runOnUiThread(this/*UiThreadI*/,null);                 //~1AbnI~
        }                                                          //~1AbnI~
        @Override                                                  //~1AbnI~
		public void runOnUiThread(Object Pparm)                    //~1AbnI~
        {                                                          //~1AbnI~
            if (Dump.Y) Dump.println("chkBondedThread:callback onUiThread");//~1AbnI~
    		nfcBT.callbackBonded(swBonded,remoteName,remoteAddr,optSecure);//~1AbnI~
        }                                                          //~1AbnI~
    }
	//***********************************************************************************//~1Ad3I~
    private void callSettings()                                    //~1Ad3I~
    {                                                              //~1Ad3I~
    	DialogNFCSelect.callSettingsBT();                          //~1Ad3I~
    }                                                              //~1Ad3I~
	//***********************************************************************************//~1Ad3I~
    private void callSettingsNFC()                                 //~1Ad3I~
    {                                                              //~1Ad3I~
    	DialogNFCSelect.callSettings();                            //~1Ad3I~
    }                                                              //~1Ad3I~
    //******************************************                   //~1Af1I~
    //*on MainThread                                               //~1Af1I~
    //******************************************                   //~1Af1I~
    @Override                                                      //~1Af1I~
	public void renewal()                                          //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("DialogNFCBT.renewal");           //~1Af1I~
//        super(Pgf,                                               //~1Af1R~
//                (PhandoverType==DialogNFCSelect.NFCTYPE_BT_SECURE?R.string.Title_NFCBT_Secure:R.string.Title_NFCBT_InSecure),//~1Af1R~
//                (AG.layoutMdpi/*mdpi and height or width <=320*/ ? R.layout.dialognfcbt_mdpi : R.layout.dialognfcbt));//~1Af1R~
//        swSecure=PhandoverType==DialogNFCSelect.NFCTYPE_BT_SECURE;//~1Af1R~
        init();                                                    //~1Af1I~
//        tvRemoteDevice=(TextView)(findViewById(R.id.RemoteDevice));//~1Af1R~
//        tvMsg=(TextView)(findViewById(R.id.Msg));                //~1Af1R~
//        int initmsgid=AG.activeSessionType==0 ? TEXTID_INITIAL : TEXTID_CONNECTED;//~1Af1R~
//        showMsg(initmsgid);                                      //~1Af1R~
        displayDevice();                                           //~1Af1I~
//        acceptButton=new ButtonAction(this,0,R.id.BTAccept);     //~1Af1R~
//        gameButton=new ButtonAction(this,0,R.id.BTGame);         //~1Af1R~
//        connectButton=new ButtonAction(this,0,R.id.BTConnect);   //~1Af1R~
//        disconnectButton=new ButtonAction(this,0,R.id.BTDisConnect);//~1Af1R~
//        nfcButtons=(LinearLayout)(findViewById(R.id.ButtonsNFCBT));//~1Af1R~
      if (AG.RemoteStatus==AG.RS_BTCONNECTED)                      //~1Af1I~
      {                                                            //~1Af1I~
        connectButton.setEnabled(false);                           //~1Af1I~
      }                                                            //~1Af1I~
      else                                                         //~1Af1I~
      {                                                            //~1Af1I~
        disconnectButton.setVisibility(View.GONE);                 //~1Af1I~
        ;                                                          //~1Af1I~
      }                                                            //~1Af1I~
//        new ButtonAction(this,0,R.id.Cancel);                    //~1Af1R~
//        new ButtonAction(this,0,R.id.Help);                      //~1Af1R~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)                    //~1Af1I~
        {                                                          //~1Af1I~
            acceptButton.setEnabled(false);                        //~1Af1I~
        }                                                          //~1Af1I~
        else                                                       //~1Af1I~
    	if ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0)//~1Af1I~
        {                                                          //~1Af1I~
	        gameButton.setEnabled(false);                          //~1Af1I~
        	acceptButton.setAction(R.string.BTStopAccept);         //~1Af1I~
        }                                                          //~1Af1I~
        else                                                       //~1Af1I~
        {                                                          //~1Af1I~
            new Component().setVisibility(nfcButtons,View.GONE);   //~1Af1I~
        }                                                          //~1Af1I~
//        setDismissActionListener(this/*DoActionListener*/);      //~1Af1R~
//        validate();                                              //~1Af1R~
//        show();                                                  //~1Af1R~
//        AG.aBTConnection=this;  //used when PartnerThread detected err//~1Af1R~
        if (Dump.Y) Dump.println("DialogNFCBT.renewal return");    //~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af3I~
    private void displayDiscoverableStatus()                       //~1Af3I~
    {                                                              //~1Af3I~
        String dev;                                                //~1Af3I~
        TextView v;                                                //~1Af3I~
    //************************                                     //~1Af3I~
        v=(TextView)(findViewById(R.id.LocalDeviceDiscoverable));  //~1Af3I~
        if (deviceAddr==null)   //bluetooth not supported          //~1Af6I~
        {                                                          //~1Af6I~
        	dev=AG.resource.getString(R.string.noBTadapter);       //~1Af6I~
        }                                                          //~1Af6I~
        else                                                       //~1Af6I~
        if (ABT.BTisDiscoverable())                                //~1Af3I~
        {                                                          //~1Af3I~
        	dev=AG.resource.getString(R.string.StatusDiscoverable);//~1Af3I~
        }                                                          //~1Af3I~
        else                                                       //~1Af3I~
        {                                                          //~1Af3I~
        	dev=AG.resource.getString(R.string.StatusNotDiscoverable);//~1Af3I~
        }                                                          //~1Af3I~
        v.setText(dev);                                            //~1Af3I~
    }                                                              //~1Af3I~
    //**********************************************************************//~1Af4R~
    private boolean getSecureOption()                              //~1Af4R~
    {                                                              //~1Af4R~
        boolean rc=cbSecureOption.isChecked();                     //~1Af4R~
        if (Dump.Y) Dump.println("DialogNFCBT.getSecureOption secure="+rc);//~1Af4R~
        return rc;                                                 //~1Af4R~
    }                                                              //~1Af4R~
	//**********************************************************************//~1Af4I~
    public static boolean getSecureOptionFromProf()                //~1Af4I~
    {                                                              //~1Af4I~
    	boolean rc=Prop.getPreference(PKEY_BTSECURE_NFC,BTSECURE_DEFAULT_NFC)!=0;//~1Af4I~
        if (Dump.Y) Dump.println("DialogNFCBT.getSecureOptionFromProf secure="+rc);//~1Af4I~
        return rc;                                                 //~1Af4I~
    }                                                              //~1Af4I~
	//**********************************************************************//~1Af4I~
    private void setToPropSecureOption(boolean Pswsecure)          //~1Af4I~
    {                                                              //~1Af4I~
    	Prop.putPreference(PKEY_BTSECURE_NFC,Pswsecure?1:0);       //~1Af4I~
        if (Dump.Y) Dump.println("DialogNFCBT.setToPropSecureOption Pswsecure="+Pswsecure);//~1Af4I~
    }                                                              //~1Af4I~
}
