//*CID://+1Af6R~:                             update#=  130;       //+1Af6R~
//*****************************************************************//~v101I~
//1Af6 2016/07/08 NPE when bluetooth is not supported              //+1Af6I~
//1Af3 2016/07/06 (Ajagot1w)Display Discoverable status            //~1Af3I~
//1Af1 2016/07/05 (Ajagot1w)update bluetooth connection dialog from bluetooth receiver//~1Af1I~
//1Aep*2015/08/09 (Bug) NPE when Bluetooth is not available        //~1AepI~
//1Aed 2015/07/30 show secure/insecure option to waiting msg       //~1AedI~
//1Ac5 2015/07/09 NFCBT:confirmation dialog is not show and fails to pairig//~1Ac5I~
//                (LocalBluetoothPreference:"Found no reason to show dialog",requires discovaring status in the 60 sec before)//~1Ac5I~
//                Issue waring whne NFCBT-Secure                   //~1Ac5I~
//1AbW 2015/07/05 BT:chk Bluetooth supported foy system bluetooth  //~1AbVI~
//1AbV 2015/07/05 BT:default change to secure                      //~1AbVI~
//1AbU 2015/07/04 BT:change String[] DeviceList to LinkedHashList  //~1AbTI~
//1AbT 2015/07/04 BT:keep discovered device in the session to avoid discovery//~1AbTI~
//1AbS 2015/07/03 BT:LeastRecentlyUsed List for once conneceted device list//~1AbSI~
//1AbR 2015/07/03 BT:additional to paired, add device not paired but connected to devlice list//~1AbRI~
//1AbE 2015/06/27 Add setting button to Remote-Bluetooth           //~1AbEI~
//1Abv 2015/06/19 BT:dismiss dialog at boardQuestion for also BT like as 1Abj//~1AbvI~
//1Abu 2015/06/18 BT:Secure option by radio button(Incsecure connection is only when insecure Accept & Insecure Connect)//~1AbuI~
//1Abt 2015/06/18 BT:Accept not both of Secure/Insecure but one of two.//~1AbtI~
//1Abi 2015/06/15 NFCBT:put DisconnectButton after Game button     //~1AbiI~
//1Abf 2015/06/15 NFCBT:no waiting ProgDialog is shown,dispose()=dismiss()->dismiss listener is sheduled before waitingMsg is set)//~1AbtI~
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
package jagoclient.partner;
//import com.Asgts.awt.GridLayout;                                  //~2C26R~//~v101R~
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.Asgts.ABT;                                              //~v101R~
import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;                                            //~v101R~
import com.Asgts.Alert;
import com.Asgts.AlertI;
import com.Asgts.ProgDlg;                                          //~v101R~
import com.Asgts.ProgDlgI;                                         //~v101R~
import com.Asgts.Prop;
import com.Asgts.R;                                                //~v101R~
import com.Asgts.URunnable;
import com.Asgts.URunnableI;
import com.Asgts.awt.Color;
import com.Asgts.awt.Component;
import com.Asgts.awt.Container;
import com.Asgts.awt.List;                                          //~3203I~//~v101R~
import com.Asgts.awt.ListData;

import jagoclient.Dump;
import jagoclient.Global;                                          //~3203I~
//import jagoclient.gui.ButtonAction;                              //~2C26R~
import jagoclient.board.GoFrame;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;

//import java.awt.GridLayout;
//import java.awt.Panel;
//import java.awt.TextField;

/**
Question to accept or decline a game with received parameters.
*/

public class BluetoothConnection extends CloseDialog               //~3105R~
		implements ProgDlgI                                         //~3203I~
{                                                                  //~2C29R~
    private static final String PKEY_BTSECURE="BTSecureOptionNonNFC";//~1AbuI~
//  private static final int BTSECURE_DEFAULT=0; //default InSecure//~1AbuI~//~1AbVR~
    private static final int BTSECURE_DEFAULT=1; //default Secure  //~1AbVI~
    private static final int STRID_ACCEPT=R.string.BTAcceptCommon; //~1AbuI~
    private static final int STRID_CONNECT=R.string.BTConnectCommon;//~1AbuI~
    private static final int MAX_LRU=5;                            //~1AbSI~
    private static final String PROPKEY_BTLRU="BTConnectedOnceLRU";   //~1AbSI~
	GoFrame GF;                                                    //~3105R~
    private int waitingDialog=0;                                   //~3201I~
    private ButtonAction acceptButton,gameButton,connectButton;                              //~3201I~//~3208R~
//  private ButtonAction settingsButton;                           //~1AbEI~//~1AbUR~
//  private ButtonAction connectSecureButton;                      //~1A60I~//~1AbuR~
//  private ButtonAction acceptSecureButton;                       //~1AbtI~//~1AbuR~
    private ButtonAction disconnectButton;                         //~1AbiI~
    private ButtonAction discoverableButton;                       //~1Af3I~
//  private ButtonAction deleteButton;                             //~1AbSI~//~1AbUR~
    private CheckBox cbSecureOption;                               //~1AbuI~
    private TextView tvRemoteDevice;                               //~1A6fI~
//  private static String[] DeviceList;	//not each time to avoid deadlock                                    //~3203I~//~3204R~//~3205R~//~3206R~//~1AbTR~
//  private static String[] discoveredDeviceList;	//not each time to avoid deadlock//~1AbTI~//~1AbUR~
    private static DeviceDataList SdeviceList;                                   //~1AbTR~//~1AbUR~
//  private static String[] pairedList;                            //~1AbTR~
    private static String[] pairedDeviceList;                      //~1AbTI~
//  private static String[] DeviceListOld;                         //~3206I~//~1A6iR~
//  private List DL;   	//listview                                 //~3203I~//~1A6fR~
    private ListBT DL;   	//listview                             //~1A6fI~
    private boolean onDiscovery;                                   //~3203I~
    int waitingid;//~3203I~
//  private String SlastConnectName;                               //~3204I~
    private String myDevice,connectingDevice;                      //~v101I~
    private boolean swCancelDiscover;                                      //~3205I~
    private static int lastSelected=-1;                            //~3205I~
    private static final int connectedColor=android.graphics.Color.argb(0xff,0xff,0xff,0x00);//~3207I~
    private static final int ID_STATUS_PAIRED=R.string.BTStatusPaired;   //~1A6fI~//~1AbUR~
    private static final int ID_STATUS_DISCOVERED=R.string.BTStatusDiscovered;//~1A6fI~//~1AbUR~
    private static final int ID_STATUS_CONNECTED=R.string.BTStatusConnected;//~1A6fI~//~1AbUR~
    private static final int ID_STATUS_CONNECTED_ONCE=R.string.BTStatusConnectedOnce;//~1AbRI~//~1AbUR~
//  private static final int ID_STATUS_DISCONNECTED=R.string.BTStatusDisConnected;//~1A6rI~//~1AbUR~
    private String statusPaired,statusDiscovered,statusConnected;//~1A6fR~//~1A6kR~
//  private String statusDisconnected;                             //~1A6rI~//~1AbUR~
    private String statusConnectedOnce;                              //~1AbRI~
    private boolean swSecure;                                      //~1AbuI~
                                                                   //~1A6fI~
    	private static final int BTROW_NAME=R.id.ListViewLine;     //~1A6fI~
    	private static final int BTROW_STATUS=R.id.ListViewLineStatus;//~1A6fI~
	    private static final Color COLOR_STATUS_PAIRED=new Color(0, 0x80, 0x80);//~1A6fM~//~1A6kR~
	    private static final Color COLOR_STATUS_DISCOVERED=new Color(0, 0xc0, 0xc0);//~1A6fM~//~1A6kR~
	    private static final Color COLOR_STATUS_CONNECTED=Color.orange;//~1A6kR~
    private static Map<String,String> SdeviceListLRU=new LinkedHashMap<String,String>();//~1AbSR~//~1AbTR~//~1AbUR~
    private static boolean swLRULoaded;                            //~1AbSI~
    private static boolean swServer,swAlertAction;                 //~1Ac5R~
    //********************************************************************//~1A6fI~
	public BluetoothConnection (GoFrame Pgf)                       //~3105R~
	{                                                              //~3105R~
//  	super(Pgf,AG.resource.getString(R.string.Title_Bluetooth),R.layout.bluetooth,true,false);//~3105I~//~3118R~//~1A60R~
    	super(Pgf,AG.resource.getString(R.string.Title_Bluetooth), //~1A60I~
				(AG.layoutMdpi/*mdpi and height or width <=320*/ ? R.layout.bluetooth_mdpi : R.layout.bluetooth),//~1A61R~//~1A60I~
				true,false);                                       //~1A60I~
		GF=Pgf;                                                    //~3105R~
        statusPaired=AG.resource.getString(ID_STATUS_PAIRED);      //~1A6fI~
        statusDiscovered=AG.resource.getString(ID_STATUS_DISCOVERED);//~1A6fI~
        statusConnected=AG.resource.getString(ID_STATUS_CONNECTED);//~1A6fI~
        statusConnectedOnce=AG.resource.getString(ID_STATUS_CONNECTED_ONCE);//~1AbRI~
//      statusDisconnected=AG.resource.getString(ID_STATUS_DISCONNECTED);//~1A6rI~//~1AbUR~
        tvRemoteDevice=(TextView)(findViewById(R.id.RemoteDevice));//~1A6fI~
        cbSecureOption=(CheckBox)(findViewById(R.id.BTSecureOption));//~1AbuI~
        discoverableButton=new ButtonAction(this,0,R.id.Discoverable);//~1Af3I~
        displayDevice();                                           //~3202I~
        if (SdeviceList==null)                                          //~1AbTI~//~1AbUR~
			SdeviceList=new DeviceDataList();                                    //~1AbTI~//~1AbUR~
	    getDeviceList();                               //~v101R~   //~@@@2R~
	    setSelection();                                            //~3204I~
//      settingsButton=new ButtonAction(this,0,R.id.BTSettings);   //~1AbEI~//~1AbUR~
        new ButtonAction(this,0,R.id.BTSettings);                  //~1AbUI~
//        acceptButton=ButtonAction.init(this,0,R.id.BTAccept);                     //~3117I~//~3201R~//~3208R~
//        gameButton=ButtonAction.init(this,0,R.id.BTGame);                //~3201R~//~3208R~
//        connectButton=ButtonAction.init(this,0,R.id.BTConnect);  //~3208R~
        acceptButton=new ButtonAction(this,0,R.id.BTAccept);  //~3208I~
//      acceptSecureButton=new ButtonAction(this,0,R.id.BTAcceptSecure);//~1AbtI~//~1AbuR~
        gameButton=new ButtonAction(this,0,R.id.BTGame);      //~3208I~
        connectButton=new ButtonAction(this,0,R.id.BTConnect);//~3208I~
//      connectSecureButton=new ButtonAction(this,0,R.id.BTConnectSecure);//~1A60I~//~1AbuR~
        disconnectButton=new ButtonAction(this,0,R.id.BTDisConnect);//~1AbiI~
        new ButtonAction(this,0,R.id.Delete);         //~1AbSI~    //~1AbUR~
//        ButtonAction.init(this,0,R.id.Discoverable);                  //~2C30I~//~3105R~//~3203M~//~3208R~
//        ButtonAction.init(this,0,R.id.Discover);                   //~3203I~//~3208R~
//        ButtonAction.init(this,0,R.id.Cancel);                     //~2C30I~//~3105R~//~3117R~//~3208R~
//        ButtonAction.init(this,0,R.id.Help);                       //~3117I~//~3208R~
//      new ButtonAction(this,0,R.id.Discoverable);           //~3208I~//~1Af3R~
        new ButtonAction(this,0,R.id.Discover);               //~3208I~
        new ButtonAction(this,0,R.id.Cancel);                 //~3208I~
        new ButtonAction(this,0,R.id.Help);                   //~3208I~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)                    //~3201R~
        {                                                          //~3201I~
//          acceptButton.setEnabled(false);                              //~3201I~//~3208R~//~1AbtR~
//          acceptButton.setVisibility(View.GONE);                 //~1AbtR~//~1AbuR~
//          acceptSecureButton.setVisibility(View.GONE);           //~1AbtI~//~1AbuR~
//  	  if ((AG.RemoteStatusAccept & AG.RS_BTLISTENING_SECURE)!=0)//~1A60I~//~1AbiR~
//        {                                                        //~1A60I~//~1AbiR~
//          connectSecureButton.setAction(R.string.BTDisConnect);  //~1A60I~//~1AbiR~
//          connectButton.setEnabled(false);                       //~1A60I~//~1AbtR~
//        }                                                        //~1A60I~//~1AbiR~
//        else                                                     //~1A60I~//~1AbiR~
//        {                                                        //~1A60I~//~1AbiR~
//          connectButton.setAction(R.string.BTDisConnect);                   //~2C30I~//~3105R~//~3117R~//~3201R~//~3208R~//~1AbiR~
//          connectSecureButton.setEnabled(false);                 //~1A60I~//~1AbtR~
//        }                                                        //~1A60I~//~1AbiR~
//          connectButton.setVisibility(View.GONE);                //~1AbtI~//~1AbiM~//~1AbuR~
//          connectSecureButton.setVisibility(View.GONE);           //~1AbtI~//~1AbiM~//~1AbuR~
            acceptButton.setEnabled(false);                        //~1AbuI~
            connectButton.setEnabled(false);                       //~1AbuI~
        }                                                          //~3201I~
        else                                                       //~3201I~
    	if ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0)                    //~3201I~//~3207R~//~3208R~
        {                                                          //~3201I~
//	        gameButton.setEnabled(false);                                //~3201I~//~3208R~//~1AbiR~
//          gameButton.setVisibility(View.GONE);                   //~1AbiI~//~1AbuR~
  	        gameButton.setEnabled(false);                          //~1AbuI~
//          disconnectButton.setVisibility(View.GONE);             //~1AbiI~//~1AbuR~
            disconnectButton.setEnabled(false);                    //~1AbuI~
//  	  if ((AG.RemoteStatusAccept & AG.RS_BTLISTENING_INSECURE)!=0) //~1AbtI~//~1AbuR~
//        {                                                        //~1AbtI~//~1AbuR~
//      	acceptButton.setAction(R.string.BTStopAccept);//~3201R~//~3203R~//~3204R~//~3208R~//~1AbuR~
//          acceptSecureButton.setEnabled(false);                  //~1AbtI~//~1AbuR~
//        }                                                        //~1AbtI~//~1AbuR~
//        else                                                     //~1AbtI~//~1AbuR~
//        {                                                        //~1AbtI~//~1AbuR~
//      	acceptSecureButton.setAction(R.string.BTStopAccept);   //~1AbtI~//~1AbuR~
//          acceptButton.setEnabled(false);                //~1AbtI~//~1AbuR~
//        }                                                        //~1AbtI~//~1AbuR~
        	acceptButton.setAction(R.string.BTStopAccept);         //~1AbuI~
        }                                                          //~3201I~
        else                                                       //~3201I~
        {                                                          //~3201I~
//          gameButton.setEnabled(false);                                //~3201I~//~3208R~//~1AbtR~
//          gameButton.setVisibility(View.GONE);                   //~1AbtI~//~1AbuR~
            gameButton.setEnabled(false);                          //~1AbuI~
//          disconnectButton.setVisibility(View.GONE);             //~1AbiI~//~1AbuR~
            disconnectButton.setEnabled(false);                    //~1AbuI~
        }                                                          //~3201I~
        setDismissActionListener(this/*DoActionListener*/);        //~3201I~
    	setFromPropSecureOption();                                 //~1AbuI~
		validate();
		show();
        AG.aBTConnection=this;	//used when PartnerThread detected err//~1A6kI~
	}
    //********************************************************************//~1Ab8I~
	public BluetoothConnection (GoFrame Pgf,int Ptitleid,int Playoutid)//~1Ab8I~
	{                                                              //~1Ab8I~
    	super(Pgf,AG.resource.getString(Ptitleid),Playoutid,false/*modal*/,false/*modalinput*/);//~1Ab8I~
    }                                                              //~1Ab8I~
    //******************************************                   //~1A6kI~
    public void updateViewDisconnected()                           //~1A6kI~
    {                                                              //~1A6kI~
    	acceptButton.setEnabled(true);                             //~1A6kI~//~1AbiR~
//  	acceptSecureButton.setEnabled(true);                       //~1AbiI~//~1AbuR~
//  	acceptButton.setVisibility(View.VISIBLE);                  //~1AbiI~//~1AbuR~
//  	acceptSecureButton.setVisibility(View.VISIBLE);            //~1AbiI~//~1AbuR~
//      acceptButton.setAction(R.string.BTAccept);                 //~1A6kI~//~1AbuR~
        acceptButton.setAction(STRID_ACCEPT);             //~1AbuR~
//      acceptSecureButton.setAction(R.string.BTAcceptSecure);     //~1AbiI~//~1AbuR~
//      connectButton.setEnabled(true);                            //~1A6kI~//~1AbiR~
//      connectButton.setVisibility(View.VISIBLE);                 //~1AbiI~//~1AbuR~
    	connectButton.setEnabled(true);                            //~1AbiI~
//      connectButton.setAction(R.string.BTConnect);               //~1A6kI~//~1AbiR~
//      connectSecureButton.setEnabled(true);                      //~1A6kI~//~1AbiR~//~1AbuR~
//      connectSecureButton.setVisibility(View.VISIBLE);           //~1AbiI~//~1AbuR~
//      connectSecureButton.setAction(R.string.BTConnectSecure);   //~1A6kI~//~1AbiR~
//      gameButton.setEnabled(true);                              //~1A6kI~//~1AbiR~//~1AbuR~
//      gameButton.setVisibility(View.GONE);                       //~1AbiI~//~1AbuR~
        gameButton.setEnabled(false);                              //~1AbuI~
        disconnectButton.setEnabled(false);                        //~1AbuI~
        String s=AG.resource.getString(R.string.NoSession);        //~1A6kI~
        new Component().setText(tvRemoteDevice,s);	//do on main thread//~1A6kI~
//        if (AG.RemoteStatus==AG.RS_BTCONNECTED && AG.RemoteDeviceName!=null)//~1A6rI~//~1AbSR~
//        {                                                          //~1A6rI~//~1AbSR~
//            DL.update(AG.RemoteDeviceName,statusDisconnected);     //~1A6rI~//~1AbSR~
//            setSelection();                                        //~1A6rI~//~1AbSR~
//        }                                                          //~1A6rI~//~1AbSR~
    }                                                              //~1A6kI~
    //******************************************                   //~1A6kI~
	public void doAction (String o)
	{                                                              //~2C26R~
    	try                                                        //~3117I~
        {                                                          //~3117I~
            if (o.equals(AG.resource.getString(R.string.BTGame)))  //~3201I~
            {                                                      //~3201I~
			    if (startGame())                                   //~3201I~
			    {                                                  //~3201I~
			    	setVisible(false); dispose();                  //~3201I~
			    }                                                  //~3201I~
            }                                                      //~3201I~
            else                                                   //~3201I~
//          if (o.equals(AG.resource.getString(R.string.BTConnect)))     //~@@@@I~//~2C30I~//~3105R~//~3117R~//~3201R~//~1AbuR~
            if (o.equals(AG.resource.getString(STRID_CONNECT)))    //~1AbuI~
            {                                                          //~2C30R~//~3117R~
                if (AG.aBT.mBTC==null)                             //~1AepI~
                {                                                  //~1AemI~//~1AepI~
                    ABT.BTNotAvailable();                      //~1AemI~//~1AepI~
                    return;                                        //~1AepI~
                }                                                  //~1AemI~//~1AepI~
//                setVisible(false); dispose();                      //~3117R~//~3203R~
//                AG.aBT.connect();                                      //~3105I~//~3117R~//~3203R~
//                waitingDialog=R.string.BTConnect;             //~3201I~//~3203R~
//  		    if (connectPartner())                              //~3203I~//~1A60R~
//  		    if (connectPartner(false))                         //~1A60I~//~1AbuR~
                swSecure=getSecureOption();                        //~1AbuI~
    		    if (connectPartner(swSecure))                      //~1AbuI~
                {                                                  //~3203I~
//                  setVisible(false); dispose();                  //~3203I~//~1AbfR~
	            	acceptButton.setEnabled(false);                //~1AbiI~
//              	acceptSecureButton.setEnabled(false);          //~1AbiI~//~1AbuR~
	            	connectButton.setEnabled(false);               //~1AbiI~
//              	connectSecureButton.setEnabled(false);         //~1AbiI~//~1AbuR~
//                  waitingDialog=R.string.BTConnect;              //~3203I~//~1AbuR~
                    waitingDialog=STRID_CONNECT;                   //~1AbuI~
                    setVisible(false); dispose();                  //~1AbfI~
                }                                                  //~3203I~
            }                                                      //~3117R~
//            else                                                   //~3201I~//~1AbuR~
//            if (o.equals(AG.resource.getString(R.string.BTConnectSecure)))//~1A60I~//~1AbuR~
//            {                                                      //~1A60I~//~1AbuR~
//                if (connectPartner(true))                          //~1A60I~//~1AbuR~
//                {                                                  //~1A60I~//~1AbuR~
////                  setVisible(false); dispose();                  //~1A60I~//~1AbfR~//~1AbuR~
//                    acceptButton.setEnabled(false);                //~1AbiI~//~1AbuR~
////                  acceptSecureButton.setEnabled(false);          //~1AbiI~//~1AbuR~
//                    connectButton.setEnabled(false);               //~1AbiI~//~1AbuR~
////                  connectSecureButton.setEnabled(false);         //~1AbiI~//~1AbuR~
//                    waitingDialog=R.string.BTConnect;              //~1A60I~//~1AbuR~
//                    setVisible(false); dispose();                  //~1AbfI~//~1AbuR~
//                }                                                  //~1A60I~//~1AbuR~
//            }                                                      //~1A60I~//~1AbuR~
            else                                                   //~1A60I~
            if (o.equals(AG.resource.getString(R.string.BTDisConnect)))//~3201I~
            {                                                      //~3201I~
//              setVisible(false); dispose();                      //~3201I~//~1AbfR~
                waitingDialog=R.string.BTDisConnect;               //~3207I~
                setVisible(false); dispose();                      //~1AbfI~
            }                                                      //~3201I~
            else                                                   //~3117R~
//          if (o.equals(AG.resource.getString(R.string.BTAccept))) //~3117R~//~3201R~//~1AbuR~
            if (o.equals(AG.resource.getString(STRID_ACCEPT)))     //~1AbuI~
            {                                                      //~3117R~
                if (AG.aBT.mBTC==null)                             //~1AepI~
                {                                                  //~1AepI~
                    ABT.BTNotAvailable();                      //~1AepI~
                    return;                                        //~1AepI~
                }                                                  //~1AepI~
    			if (!chkDiscoverable(swSecure,true/*server*/))     //~1Ac5I~
                	return;                                        //~1Ac5I~
//              setVisible(false); dispose();                      //~3117R~//~1AbfR~
//              boolean rc=ABT.startListen();                                         //~3105I~//~3117R~//~v101R~//~1AbtR~
//              boolean rc=startListen(false);                     //~1AbtI~//~1AbuR~
                swSecure=getSecureOption();                        //~1AbuI~
                boolean rc=startListen(swSecure);                  //~1AbuI~
              if (rc)                                              //~v101I~
              {                                                    //~v101I~
//              acceptButton.setEnabled(false);                          //~3201I~//~3208R~//~1AbuR~
//              acceptSecureButton.setEnabled(false);              //~1AbtI~//~1AbuR~
//              connectButton.setEnabled(false);                   //~1AbiI~//~1AbuR~
//              connectSecureButton.setEnabled(false);             //~1AbiI~//~1AbuR~
//              waitingDialog=R.string.BTAccept;           //~3201I~//~1AbuR~
                waitingDialog=STRID_ACCEPT;                        //~1AbuI~
              }                                                    //~v101I~
                setVisible(false); dispose();                      //~1AbfI~
            }                                                      //~3117R~
//            else                                                   //~1AbtI~//~1AbuR~
//            if (o.equals(AG.resource.getString(R.string.BTAcceptSecure)))//~1AbtI~//~1AbuR~
//            {                                                      //~1AbtI~//~1AbuR~
////              setVisible(false); dispose();                      //~1AbtI~//~1AbfR~//~1AbuR~
//                boolean rc=startListen(true);                     //~1AbtI~//~1AbiR~//~1AbuR~
//              if (rc)                                              //~1AbtI~//~1AbuR~
//              {                                                    //~1AbtI~//~1AbuR~
////              acceptSecureButton.setEnabled(false);              //~1AbtI~//~1AbuR~
//                acceptButton.setEnabled(false);            //~1AbtI~//~1AbuR~
//                connectButton.setEnabled(false);                   //~1AbiI~//~1AbuR~
////              connectSecureButton.setEnabled(false);             //~1AbiI~//~1AbuR~
//                waitingDialog=R.string.BTAcceptSecure;             //~1AbtI~//~1AbuR~
//              }                                                    //~1AbtI~//~1AbuR~
//                setVisible(false); dispose();                      //~1AbfI~//~1AbuR~
//            }                                                      //~1AbtI~//~1AbuR~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.BTStopAccept)))//~3204I~
            {                                                      //~3204I~
	            stopListen();                                      //~3204I~
//      		acceptButton.setAction(R.string.BTAccept);//~3204I~//~3208R~//~1AbuR~
        		acceptButton.setAction(STRID_ACCEPT);              //~1AbuR~
//      		acceptSecureButton.setAction(R.string.BTAcceptSecure);//~1AbiI~//~1AbuR~
	            acceptButton.setEnabled(true);                     //~1AbiI~
//              acceptSecureButton.setEnabled(true);               //~1AbiI~//~1AbuR~
	            connectButton.setEnabled(true);                    //~1AbiI~
//              connectSecureButton.setEnabled(true);              //~1AbiI~//~1AbuR~
            }                                                      //~3204I~
            else                                                   //~3204I~
            if (o.equals(AG.resource.getString(R.string.Discoverable)))//~3117R~
            {                                                          //~3105R~//~3117R~
//              setVisible(false); dispose();                      //~3117R~//~1A62R~
                AG.aBT.setDiscoverable();                              //~3105I~//~3117R~
            }                                                      //~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Discover)))//~3203I~
            {                                                      //~3203I~
		    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)            //~3206I~
                {                                                  //~3206I~
					errConnectingForScan();                         //~3206I~
                    return;                                        //~3206I~
                }                                                  //~3206I~
              if (discover())                                      //~1AbWI~
              {                                                    //~1AbWI~
    			onDiscovery=true;                                  //~3203I~
//              discover();                                        //~3203R~//~1AbWR~
                afterDismiss(R.string.Discover);                   //~3203I~
              }                                                    //~1AbWI~
            }                                                      //~3203I~
            else                                                   //~1AbSI~
            if (o.equals(AG.resource.getString(R.string.Delete)))  //~1AbSI~
            {                                                      //~1AbSI~
            	deleteEntry();                                     //~1AbSI~
            }                                                      //~1AbSI~
            else                                                   //~3203I~
            if (o.equals(AG.resource.getString(R.string.ActionDiscovered)))//~3203I~
            {                                                      //~3203I~
            	if (!onDiscovery)                                  //~3205I~
                	return;                                        //~3205I~
    			onDiscovery=false;                                 //~3203I~
            	discovered();                                      //~3203I~
            }                                                      //~3203I~
            else                                                   //~3203I~
            if (o.equals(AG.resource.getString(R.string.Cancel)))  //~3117R~
            {                                                          //~2C30I~//~3117R~
                setVisible(false); dispose();                      //~3117R~
            }                                                          //~2C30I~//~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Help)))    //~3117R~
            {                                                      //~3117R~
//              new HelpDialog(null,R.string.Help_BluetoothConnection); //~3117R~//~v101R~
                new HelpDialog(null,R.string.HelpTitle_Bluetooth,"BTConnection");//~v101R~
            }                                                      //~3117R~
            else                                                   //~3201I~
            if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~3201I~
            {               //callback from Dialog after currentLayout restored//~3201I~
    			cancelDiscover();                                  //~3203R~
                afterDismiss(waitingDialog);                       //~3201I~
            }                                                      //~3201I~
            else                                                   //~1AbEI~
            if (o.equals(AG.resource.getString(R.string.BTSettingsButton)))  //modal but no inputWait//~1AbEI~
            {                                                      //~1AbEI~
            	callSettingsBT();                                  //~1AbEI~
            }                                                      //~1AbEI~
            else super.doAction(o);                                //~3117R~
        }                                                          //~3117I~
        catch(Exception e)                                         //~3117I~
        {                                                          //~3117I~
            Dump.println(e,"BluetoothConnection:doAction:"+o);     //~3117I~
        }                                                          //~3117I~
	}
    //******************************************                   //~3203I~
//  private void discover()                                             //~3203I~//~1AbWR~
    private boolean discover()                                     //~1AbWI~
    {                                                              //~3203I~
		swCancelDiscover=false;                                    //~3205I~
      return                                                       //~1AbWI~
    	AG.aBT.discover(this);                                     //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3203I~
    private void cancelDiscover()                                       //~3203I~
    {                                                              //~3203I~
    	if (onDiscovery)                                           //~3203I~
        {                                                          //~3203I~
	        AG.aBT.cancelDiscover();                               //~3203I~
            onDiscovery=false;                                     //~3203I~
		    swCancelDiscover=true;                                 //~3205I~
        }                                                          //~3203I~
    }                                                              //~3203I~
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
//        if (AG.RemoteStatus==AG.RS_BTLISTENING)                    //~3201I~//~3207R~
//        {                                                          //~3201I~//~3207R~
//            stopListen();                                          //~3201I~//~3207R~
//            return;                                                //~3201I~//~3207R~
//        }                                                          //~3201I~//~3207R~
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
//      v=(TextView)(AG.findViewById(R.id.LocalDevice));  //~3202I~//~v101R~
        v=(TextView)(findViewById(R.id.LocalDevice));              //~v101I~
        if (AG.LocalDeviceName!=null)                              //~3202I~
        	dev=AG.LocalDeviceName;                                //~3202I~
        else                                                       //~3202I~
        	dev=AG.resource.getString(R.string.UnknownDeviceName); //~3202I~
        myDevice=dev;                                              //~v101I~
        v.setText(dev);                                            //~3202I~
        displayDiscoverableStatus();                               //~1Af3I~
        //*                                                        //~3202I~
//      v=(TextView)(AG.findViewById(R.id.RemoteDevice)); //~3202I~//~v101R~
//      v=(TextView)(findViewById(R.id.RemoteDevice));             //~v101I~//~1A6fR~
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
    //******************************************                   //~3203I~
    //*at opened dialog                                            //~1AbRI~
    //******************************************                   //~1AbRI~
    private void getDeviceList()                      //~v101R~    //~@@@2R~
    {                                                              //~3203I~
        if (Dump.Y) Dump.println("BluetoothConnection getDeviceList");//~1AbSI~
//      String l;                                                  //~3203I~
//      DL=new List(this,R.id.DeviceList,R.layout.textrowlist);         //~3203I~//~3209R~//~@@@2R~//~1A6cR~
//      DL=new List(this,R.id.DeviceList,(AG.layoutMdpi ? R.layout.textrowlist_bt_mdpi : R.layout.textrowlist_bt));//~1A6cI~//~1A6fR~
        DL=new ListBT(this,R.id.DeviceList,(AG.layoutMdpi ? R.layout.textrowlist_bt_mdpi : R.layout.textrowlist_bt));//~1A6fR~
        DL.addActionListener(this);                                //~3203I~//~3209R~//~@@@2R~
        DL.setBackground(Global.gray);                             //~3203I~//~3209R~//~@@@2R~
//      if (DeviceList==null)                                      //~3204I~//~1A6iR~
//      {                                                          //~3206I~//~1A6iR~
//      	DeviceList=AG.aBT.getPairDeviceList();                  //~3203I~//~3204R~//~1AbTR~
//          DeviceListOld=DeviceList;                              //~3206I~//~1A6iR~
//      }                                                          //~3206I~//~1A6iR~
//      else                                                       //~3206I~//~1A6iR~
//      {                                                          //~3206I~//~1A6iR~
//          DeviceList=DeviceListOld;   //newly added my not be registered,avoid scan begin for deadlock//~3206I~//~1A6iR~
//      }                                                          //~3206I~//~1A6iR~
//      pairedList=AG.aBT.getPairDeviceList();                     //~1AbTR~
//        if (DeviceList==null)                                    //~1AbTR~
//            DeviceList=pairedList;                               //~1AbTR~
//        else                                                     //~1AbTR~
//            if (pairedList!=null)                                //~1AbTR~
//                mergePairedDevice();                             //~1AbTR~
        pairedDeviceList=AG.aBT.getPairDeviceList();               //~1AbTI~
        updateDeviceStatus(ID_STATUS_PAIRED,ID_STATUS_DISCOVERED);	//clear paird previously//~1AbUI~
        if (pairedDeviceList!=null)                                //~1AbUI~
	        SdeviceList.merge(pairedDeviceList,ID_STATUS_PAIRED);           //~1AbTI~//~1AbUR~
        mergeLRU();                                                //~1AbTI~
//		setupListView();                                           //~1AbSI~//~1AbTR~
  		initListView();                                            //~1AbTI~
	}                                                              //~1AbSI~
    //******************************************                   //~1AbSI~
//    private void setupListView()                                   //~1AbSI~//~1AbTR~
//    {                                                              //~1AbSI~//~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection setupListView");//~1AbSI~//~1AbTR~
//        String[] sa=DeviceList;                                    //~3204I~//~1AbTR~
//        if (sa!=null)                                              //~3203I~//~1AbTR~
//        {                                                          //~3203I~//~1AbTR~
//            String remoteDevice=tvRemoteDevice.getText().toString();//~1A6fI~//~1AbTR~
//            int ctr=sa.length/2;                                       //~3203I~//~1AbTR~
//            for (int ii=0;ii<ctr;ii++)                             //~3203I~//~1AbTR~
//            {                                                      //~3203I~//~1AbTR~
//                if (Dump.Y) Dump.println("BluetoothConnection:setupListView DeviceList ii="+ii+",addr="+sa[ii*2]);//~3203I~//~1AbTR~
////              DL.add(sa[ii*2]);                                  //~3203I~//~1A6fR~//~1AbTR~
//                String nm=sa[ii*2];                                //~1A6fI~//~1AbTR~
//                if (remoteDevice!=null && remoteDevice.equals(nm)) //~1A6fI~//~1AbTR~
//                    DL.add(nm,statusConnected,ID_STATUS_CONNECTED);//~1A6fI~//~1AbTR~
//                 else                                              //~1A6fI~//~1AbTR~
//                if (chkPaired(sa[ii*2+1])!=null) //if defined in pairedList//~1AbTR~
//                    DL.add(nm,statusPaired,ID_STATUS_PAIRED);      //~1A6fI~//~1AbTR~
//                else                                             //~1AbTR~
//                    DL.add(nm,statusDiscovered,ID_STATUS_DISCOVERED);//~1AbTR~
//            }                                                      //~3203I~//~1AbTR~
//        }                                                          //~3203I~//~1AbTR~
////      mergeConnectedDevices();                                   //~1AbRR~//~1AbTR~
//    }                                                              //~3203I~//~1AbTR~
    //******************************************                   //~1AbTI~
    private void initListView()                                    //~1AbTI~
    {                                                              //~1AbTI~
        if (Dump.Y) Dump.println("BluetoothConnection initListView");//~1AbTI~
        String remoteDevice=tvRemoteDevice.getText().toString();   //~1AbTI~
        ArrayList<String> keys=new ArrayList<String>(SdeviceList.devlist.keySet());//~1AbTI~//~1AbUR~
        int ctr=keys.size();                                       //~1AbTI~
        for (int ii=0;ii<ctr;ii++)                                 //~1AbTI~
        {                                                          //~1AbTI~
        	String nm=keys.get(ii);                                //~1AbTI~
            DeviceData data=SdeviceList.get(nm);                        //~1AbTR~//~1AbUR~
            String addr=data.addr;                                 //~1AbTI~
            int stat=data.stat;                                    //~1AbTI~
            if (Dump.Y) Dump.println("BluetoothConnection:initListView DeviceList ii="+ii+",addr="+addr+",name="+nm);//~1AbTI~
            if (remoteDevice!=null && remoteDevice.equals(nm))     //~1AbTI~
                DL.add(nm,statusConnected,ID_STATUS_CONNECTED);    //~1AbTI~
            else                                                   //~1AbTI~
            {                                                      //~1AbTI~
            	switch(stat)                                       //~1AbTI~
                {                                                  //~1AbTI~
                case ID_STATUS_PAIRED:                             //~1AbTI~
	                DL.add(nm,statusPaired,ID_STATUS_PAIRED);      //~1AbTI~
                	break;                                         //~1AbTI~
                case ID_STATUS_CONNECTED_ONCE:                     //~1AbTI~
	                DL.add(nm,statusConnectedOnce,ID_STATUS_CONNECTED_ONCE);//~1AbTI~
                	break;                                         //~1AbTI~
                default:                                           //~1AbTI~
	                DL.add(nm,statusDiscovered,ID_STATUS_DISCOVERED);//~1AbTI~
                }                                                  //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
    }                                                              //~1AbTI~
    //******************************************                   //~1AbUI~
    private void resetListView()                                   //~1AbUI~
    {                                                              //~1AbUI~
        if (Dump.Y) Dump.println("BluetoothConnection resetListView");//~1AbUI~
        DL.removeAll();                                            //~1AbUI~
    	initListView();                                            //~1AbUI~
    }                                                              //~1AbUI~
    //******************************************                   //~3204I~
	private void setSelection()                                    //~3204I~
    {                                                              //~3204I~
    	int idx;                                                   //~3204I~
		idx=setSelection(AG.RemoteDeviceName);                     //~3204I~
        if (idx<0)                                                 //~3204I~
        	if (lastSelected>=0)                                   //~3205I~
            	idx=lastSelected;                                  //~3205I~
        	else                                                   //~3205I~
				idx=0;                                                 //~3204I~//~3205R~
        if (idx<DL.getItemCount())                                         //~3204I~
        {                                                          //~3204I~
			DL.select(idx);                                        //~3204I~
            if (Dump.Y) Dump.println("setselect="+idx);            //~3204I~
        }                                                          //~3204I~
    }                                                              //~3204I~
    //******************************************                   //~3204I~
	private int setSelection(String Pdevice)                   //~3204I~
    {                                                              //~3204I~
    	int idx=-1;                                                //~3204I~
        if (Pdevice==null)                                         //~3204I~
        	return idx;                                            //~3204I~
//        if (DeviceList!=null)                                      //~3204I~//~1AbUR~
//        {                                                          //~3204I~//~1AbUR~
//            int ctr=DeviceList.length/2;                           //~3204I~//~1AbUR~
//            for (int ii=0;ii<ctr;ii++)                             //~3204I~//~1AbUR~
//            {                                                      //~3204I~//~1AbUR~
//                if (Pdevice.equals(DeviceList[ii*2]))             //~3204I~//~1AbUR~
//                {                                                  //~3204I~//~1AbUR~
//                    idx=ii;                                        //~3204I~//~1AbUR~
//                    break;                                         //~3204I~//~1AbUR~
//                }                                                  //~3204I~//~1AbUR~
//            }                                                      //~3204I~//~1AbUR~
//        }                                                          //~3204I~//~1AbUR~
        idx=SdeviceList.search(Pdevice);                           //~1AbUR~
        if (idx>=0)                                                //~1AbUR~
        DL.select(idx);                                            //~1AbUR~
        if (Dump.Y) Dump.println("BluetoothConnection:setSelection idx="+idx);//~1AbUI~
        return idx;                                                //~3204I~
    }                                                              //~3204I~
    //******************************************                   //~3203I~
    private void discovered()                                      //~3203I~
    {                                                              //~3203I~
    	String[] sa=AG.aBT.getNewDevice();                         //~3203I~
        if (sa==null)                                              //~3203I~
        {                                                          //~3203I~
            if (swCancelDiscover)                                  //~3205I~
	        	infoDiscoverCanceled();                            //~3205I~
            else                                                   //~3205I~
	        	errNoNewDevice();                                      //~3203I~//~3205R~
	        ProgDlg.dismiss();                                     //~3203I~
            return;                                                //~3203I~
        }                                                          //~3203I~
//        int ctr=sa.length/2;                                       //~3203I~//~1AbUR~
//        int ctr2,addctr=0;                                         //~3206I~//~1AbUR~
//        if (DeviceList==null)                                      //~3206I~//~1AbUR~
//            ctr2=0;                                                //~3206I~//~1AbUR~
//        else                                                       //~3206I~//~1AbUR~
//            ctr2=DeviceList.length/2;                              //~3206I~//~1AbUR~
//        for (int ii=0;ii<ctr;ii++)                                 //~3203I~//~1AbUR~
//        {                                                          //~3203I~//~1AbUR~
//            if (Dump.Y) Dump.println("new device add="+sa[ii*2]);  //~3203I~//~1AbUR~
//            int jj;                                                //~3206I~//~1AbUR~
//            for (jj=0;jj<ctr2;jj++)                                //~3206I~//~1AbUR~
//            {                                                      //~3206I~//~1AbUR~
//                if (sa[ii*2].equals(DeviceList[jj*2]))             //~3206I~//~1AbUR~
//                    break;                                         //~3206I~//~1AbUR~
//            }                                                      //~3206I~//~1AbUR~
//            if (jj==ctr2)                                          //~3206I~//~1AbUR~
//            {                                                      //~3206I~//~1AbUR~
//                addctr++;                                          //~3206I~//~1AbUR~
////              DL.add(sa[ii*2]);                                      //~3203I~//~3206R~//~1A6fR~//~1AbUR~
//                DL.add(sa[ii*2],statusDiscovered,ID_STATUS_DISCOVERED);//~1A6fI~//~1AbUR~
//                if (Dump.Y) Dump.println("discovered device add="+sa[ii*2]);//~1A6fI~//~1AbUR~
//            }                                                      //~3206I~//~1AbUR~
//            else                                                   //~1A6fI~//~1AbUR~
//            {                                                      //~1A6fI~//~1AbUR~
//                DL.update(sa[ii*2],statusDiscovered);              //~1A6fI~//~1AbUR~
//                if (Dump.Y) Dump.println("discovered device update="+sa[ii*2]);//~1A6fI~//~1AbUR~
//            }                                                      //~1A6fI~//~1AbUR~
//        }                                                          //~3203I~//~1AbUR~
		SdeviceList.merge(sa,ID_STATUS_DISCOVERED);                //~1AbUR~
		resetListView();                                           //~1AbUI~
//        if (DeviceList==null)                                      //~3203I~//~1AbUR~
//        {                                                          //~3203I~//~1AbUR~
//            DL.select(0); //first added pos                        //~3203I~//~1AbUR~
//            DeviceList=sa;                                         //~3203I~//~1AbUR~
//        }                                                          //~3203I~//~1AbUR~
//        else                                                       //~3203I~//~1AbUR~
//        {                                                          //~3203I~//~1AbUR~
//            DL.select(ctr2); //first added pos      //~3203R~      //~3206R~//~1AbUR~
//            String[] nl=new String[(ctr2+addctr)*2];   //~3203R~   //~3206R~//~1AbUR~
//            System.arraycopy(DeviceList,0,nl,0,ctr2*2); //~3203R~  //~3206R~//~1AbUR~
//            addctr=0;                                              //~3206I~//~1AbUR~
//            for (int ii=0;ii<ctr;ii++)                             //~3206I~//~1AbUR~
//            {                                                      //~3206I~//~1AbUR~
//                int jj;                                            //~3206I~//~1AbUR~
//                for (jj=0;jj<ctr2;jj++)                            //~3206I~//~1AbUR~
//                {                                                  //~3206I~//~1AbUR~
//                    if (sa[ii*2].equals(DeviceList[jj*2]))         //~3206I~//~1AbUR~
//                        break;                                     //~3206I~//~1AbUR~
//                }                                                  //~3206I~//~1AbUR~
//                if (jj==ctr2)                                      //~3206I~//~1AbUR~
//                {                                                  //~3206I~//~1AbUR~
//                    nl[(ctr2+addctr)*2]=sa[ii*2];                  //~3206R~//~1AbUR~
//                    nl[(ctr2+addctr)*2+1]=sa[ii*2+1];              //~3206R~//~1AbUR~
//                    if (Dump.Y) Dump.println("BluetoothConnection:discovered add by scan ="+nl[(ctr2+addctr)*2]+"="+nl[(ctr2+addctr)*2+1]);//~3206I~//~1AbSR~//~1AbUR~
//                    addctr++;                                      //~3206I~//~1AbUR~
//                }                                                  //~3206I~//~1AbUR~
//            }                                                      //~3206I~//~1AbUR~
//            DeviceList=nl;                                         //~3203R~//~1AbUR~
//        }                                                          //~3203I~//~1AbUR~
		int ctr=sa.length/2;                                       //~1AbUR~
        int idx=0;                                                 //~1AbUI~
        if (ctr>0)                                                 //~1AbUI~
        {                                                          //~1AbUI~
        	String name=sa[(ctr-1)*2];                            //~1AbUI~
            idx=SdeviceList.search(name);                          //~1AbUR~
        }                                                          //~1AbUI~
        if (idx>=0)                                                //~1AbUI~
		    DL.select(idx); //last added pos                       //~1AbUI~
        ProgDlg.dismiss();                                         //~3203R~
        infoNewDevice(sa.length/2);                                //~3203R~
    }                                                              //~3203I~
    //******************************************                   //~3203I~
//  private boolean connectPartner()                               //~3203I~//~1A60R~
    private boolean connectPartner(boolean Psecure)                //~1A60I~
    {                                                              //~3203I~
	    int idx=getSelected();                                     //~3203I~
        if (idx==-1)                                               //~3203I~
        	return false;                                          //~3203I~
        lastSelected=idx;                                          //~3205I~
//      String name=DeviceList[idx*2];                             //~3203R~//~1AbUR~
//      String addr=DeviceList[idx*2+1];                           //~3203I~//~1AbUR~
		ListData ld=getListData(idx);                              //~1AbUI~
        String name=ld.itemtext;                                   //~1AbUI~
        DeviceData data=SdeviceList.get(name);                     //~1AbUR~
        String addr=data.addr;                                     //~1AbUI~
        connectingDevice=name;                                     //~v101I~
        if (ld.itemint!=ID_STATUS_PAIRED)                          //~1Ac5R~
    		if (!chkDiscoverable(Psecure,false/*not server*/))     //~1Ac5I~
                return false;                                      //~1Ac5I~
//      AG.aBT.connect(name,addr);                                 //~3203R~//~1A60R~
        AG.aBT.connect(name,addr,Psecure);                         //~1A60I~
        return true;                                               //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~1AbSI~
    private boolean deleteEntry()                                  //~1AbSI~
    {                                                              //~1AbSI~
	    int idx=getSelected();                                     //~1AbSI~
        if (idx==-1)                                               //~1AbSI~
        	return false;                                          //~1AbSI~
        lastSelected=0;                                            //~1AbSI~
		ListData ld=getListData(idx);                              //~1AbSI~
        String name=ld.itemtext;                                   //~1AbSR~
        String status=ld.itemtext2;                                //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection deleteEntry name="+name+",status="+status);//~1AbSR~
        if (status.equals(statusPaired))                           //~1AbSI~
    		AView.showToast(R.string.WarningDeletingPaired);      //~1AbSI~
//      deleteConnectedDevice(name,idx);                           //~1AbSR~//~1AbTR~
        deleteDeviceList(name);                                        //~1AbTI~//~1AbUR~
        deleteFromListView(idx);                                   //~1AbSR~
        return true;                                               //~1AbSI~
    }                                                              //~1AbSI~
    //******************************************                   //~1AbtI~
    private boolean startListen(boolean Psecure)                   //~1AbtI~
    {                                                              //~1AbtI~
    	boolean rc=ABT.startListenNonNFC(Psecure);                 //~1AbtR~
        return rc;                                                 //~1AbtI~
    }                                                              //~1AbtI~
    //******************************************                   //~1A6fI~
	private ListData getListData(int Pidx)                         //~1A6fI~
    {                                                              //~1A6fI~
    	ListData ld;                                               //~1A6fI~
        try                                                        //~1A6fI~
        {                                                          //~1A6fI~
    		ld=DL.arrayData.get(Pidx);                             //~1A6fI~
        }                                                          //~1A6fI~
        catch(Exception e)                                         //~1A6fI~
        {                                                          //~1A6fI~
        	Dump.println(e,"BluetoothConnection:getListData");     //~1A6fI~
            ld=new ListData("OutOfBound",Color.black/*dummy*/);    //~1A6fI~
        }                                                          //~1A6fI~
        return ld;                                                 //~1A6fI~
    }                                                              //~1A6fI~
    //******************************************                   //~3203I~
	private int getSelected()                                      //~3203I~
    {                                                              //~3203I~
	    int idx=DL.getValidSelectedPos();                           //~3203I~
        if (idx==-1)                                               //~3203I~
        {                                                          //~3203I~
			IPConnection.errNotSelected();                         //~3203R~
        }                                                          //~3203I~
        return idx;                                                //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3201I~
	public void afterDismiss(int Pwaiting)                         //~3201I~
    {                                                              //~3201I~
    	setToPropSecureOption();                                   //~1AbuR~
        waitingid=Pwaiting;                                        //~3203I~
    	if (Dump.Y) Dump.println("BTConnection afterDismiss");     //~3201I~//~3204R~
//      if (Pwaiting==R.string.BTAccept)                   //~3201I~//~1AbuR~
        if (Pwaiting==STRID_ACCEPT)                                //~1AbuI~
        {                                                          //~v101I~
//          String msg=AG.resource.getString(R.string.Msg_WaitingAccept,myDevice);//~v101I~//~1AedR~
            String msg=getMsgStringBTAccepting(myDevice,swSecure); //~1AedI~
			waitingResponse(R.string.Title_WaitingAccept,msg);//~3201I~//~v101R~
        }                                                          //~v101I~
        else                                                       //~3201I~
//      if (Pwaiting==R.string.BTConnect)                     //~3201I~//~1AbuR~
        if (Pwaiting==STRID_CONNECT)                               //~1AbuI~
        {                                                          //~v101I~
//          String msg=AG.resource.getString(R.string.Msg_WaitingConnect,connectingDevice);//~v101I~//~1AedR~
            String msg=getMsgStringBTConnecting(connectingDevice,swSecure);//~1AedI~
			waitingResponse(R.string.Title_WaitingConnect,msg);//~3201I~//~v101R~
        }                                                          //~v101I~
        else                                                       //~3203I~
        if (Pwaiting==R.string.Discover)                           //~3203I~
			waitingResponse(R.string.Title_WaitingDiscover,R.string.Msg_WaitingDiscover);//~3203I~
        else                                                       //~3207I~
        if (Pwaiting==R.string.BTDisConnect)                       //~3207I~
			disconnectPartner();                               //~3201I~//~3207I~
    	if (Dump.Y) Dump.println("BTConnection afterDismiss return");//~3207I~
        putLRU();                                                  //~1AbSI~
        AG.aBTConnection=null;                                     //~1A6kI~
    }                                                              //~3201I~
    //******************************************                   //~3201I~
	private void waitingResponse(int Ptitleresid,int Pmsgresid)    //~3201R~
    {                                                              //~3201I~
//        new MessageWaiting(parentFrame,AG.resource.getString(Ptitleresid),//~3201I~//~3203R~
//                            AG.resource.getString(Pmsgresid));     //~3201I~//~3203R~
//        AG.progDlg=new ProgDlg(Ptitleresid,Pmsgresid,true/*cancelable*/);//~3203R~//~1A2jR~
//        AG.progDlg.setCallback(this,true/*cancel CB*/,false/*dismisscallback*/);//~3203R~//~1A2jR~
//        AG.progDlg.show();                                         //~3203R~//~1A2jR~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,true/*cancel CB*/,Ptitleresid,Pmsgresid,true/*cancelable*/);//~1A2jI~
    }                                                              //~3201I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,String Pmsg)      //~v101I~
    {                                                              //~v101I~
//        AG.progDlg=new ProgDlg(Ptitleresid,Pmsg,true/*cancelable*/);//~v101I~//~1A2jR~
//        AG.progDlg.setCallback(this,true/*cancel CB*/,false/*dismisscallback*/);//~v101I~//~1A2jR~
//        AG.progDlg.show();                                         //~v101I~//~1A2jR~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,true/*cancel CB*/,Ptitleresid,Pmsg,true/*cancelable*/);//~1A2jI~
    }                                                              //~v101I~
    //******************************************                   //~3201I~
	private void errNoThread()                                     //~3201I~
    {                                                              //~3201I~
    	AView.showToast(R.string.ErrNoThread);                     //~3201R~
    }                                                              //~3201I~
    //******************************************                   //~3203I~
	private void errNoNewDevice()                                  //~3203I~
    {                                                              //~3203I~
    	AView.showToast(R.string.ErrNoNewDevice);                  //~3203I~
    }                                                              //~3203I~
    //******************************************                   //~3206I~
	private void errConnectingForScan()                            //~3206I~
    {                                                              //~3206I~
    	AView.showToast(R.string.ErrConnectingForScan);            //~3206I~
    }                                                              //~3206I~
	private void infoDiscoverCanceled()                             //~3205I~
    {                                                              //~3205I~
    	AView.showToast(R.string.InfoDiscoverCanceled);            //~3205I~
    }                                                              //~3205I~
    //******************************************                   //~3203I~
	private void infoNewDevice(int Pctr)                           //~3203I~
    {                                                              //~3203I~
    	AView.showToast(AG.resource.getString(R.string.InfoNewDevice,Pctr));//~3203I~
    }                                                              //~3203I~
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
        	if (waitingid==R.string.Discover)                       //~3203I~
	        	cancelDiscover();                                 //~3203I~
        }                                                          //~3203I~
    }                                                              //~3203I~
    class ListBT extends List                                                  //~1114//~1A6fI~
    {                                                              //~1A6fI~
    //*****************                                            //~1A6fI~
        public ListBT(Container Pcontainer,int Presid,int Prowresid)//~1A6fI~
        {                                                          //~1A6fI~
            super(Pcontainer,Presid,Prowresid);                    //~1A6fI~
        }                                                          //~1A6fI~
    //**********************************************************************//~1A6fI~
        @Override                                                  //~1A6fI~
        public View getViewCustom(int Ppos, View Pview,ViewGroup Pparent)//~1A6fI~
        {                                                          //~1A6fI~
        //*******************                                      //~1A6fI~
            if (Dump.Y) Dump.println("ListBT:getViewCustom Ppos="+Ppos+"CheckedItemPos="+((ListView)Pparent).getCheckedItemPosition());//~1A6fI~
            View v=Pview;                                          //~1A6fI~
            if (v == null)                                         //~1A6fI~
			{                                                      //~1A6fI~
                v=AG.inflater.inflate(rowId/*super*/,null);            //~1A65I~//~1A6fI~
            }                                                      //~1A6fI~
            TextView v1=(TextView)v.findViewById(BTROW_NAME);      //~1A6fI~
            TextView v2=(TextView)v.findViewById(BTROW_STATUS);    //~1A6fI~
            if (font!=null)                                        //~1A6fI~
            {                                                      //~1A6fI~
                font.setFont(v1);                                  //~1A6fI~
                font.setFont(v2);                                  //~1A6fI~
            }                                                      //~1A6fI~
            ListData ld=getListData(Ppos);                         //~1A6fI~
            v1.setText(ld.itemtext);                               //~1A6fI~
            if (Ppos==selectedpos)                                 //~1A6fI~
            {                                                      //~1A6fI~
                v1.setBackgroundColor(bgColorSelected.getRGB());   //~1A6fI~
                v1.setTextColor(bgColor.getRGB());                 //~1A6fI~
            }                                                      //~1A6fI~
            else                                                   //~1A6fI~
            {                                                      //~1A6fI~
                v1.setBackgroundColor(bgColor.getRGB());           //~1A6fI~
                v1.setTextColor(ld.itemcolor.getRGB());            //~1A6fI~
            }                                                      //~1A6fI~
            String status;                                         //~1A6fI~
            if (ld.itemtext2==null)                                //~1A6fI~
            	status="";                                         //~1A6fI~
            else                                                   //~1A6fI~
            	status=ld.itemtext2;                               //~1A6fI~
            v2.setText(status);                                    //~1A6fI~
            v2.setBackgroundColor(bgColor.getRGB());               //~1A6fI~
            if (status.equals(statusPaired))                       //~1A6fI~
                v2.setTextColor(COLOR_STATUS_PAIRED.getRGB());     //~1A6fI~
            else                                                   //~1A6fI~
            if (status.equals(statusConnected))                    //~1A6kR~
                v2.setTextColor(COLOR_STATUS_CONNECTED.getRGB());  //~1A6kR~
            else                                                   //~1A6kR~
                v2.setTextColor(COLOR_STATUS_DISCOVERED.getRGB()); //~1A6fI~
            return v;                                              //~1A6fI~
        }                                                          //~1A6fI~
    }//class                                                       //~1A6fI~
	//**********************************************************************//~1AbuI~
    private boolean getSecureOption()                              //~1AbuI~
    {                                                              //~1AbuI~
    	return cbSecureOption.isChecked();                         //~1AbuI~
    }                                                              //~1AbuI~
//    //**********************************************************************//~1AbuI~//~1AbUR~
//    private void setSecureOption(boolean Psecure)               //~1AbuI~//~1AbUR~
//    {                                                              //~1AbuI~//~1AbUR~
//        cbSecureOption.setChecked(Psecure);                 //~1AbuI~//~1AbUR~
//    }                                                              //~1AbuI~//~1AbUR~
	//**********************************************************************//~1AbuI~
    private void setFromPropSecureOption()                      //~1AbuI~
    {                                                              //~1AbuI~
    	swSecure=Prop.getPreference(PKEY_BTSECURE,BTSECURE_DEFAULT)!=0;//~1AbuI~
    	cbSecureOption.setChecked(swSecure);                //~1AbuI~
    }                                                              //~1AbuI~
	//**********************************************************************//~1AbuI~
    private void setToPropSecureOption()                           //~1AbuR~
    {                                                              //~1AbuI~
    	swSecure=getSecureOption();                                 //~1AbuI~
    	Prop.putPreference(PKEY_BTSECURE,swSecure?1:0);            //~1AbuR~
    }                                                              //~1AbuI~
    //***********************************************************************//~1AbvI~
    public static void closeDialog()                               //~1AbvI~
    {                                                              //~1AbvI~
    	if (Dump.Y) Dump.println("BluetoothConnection:closeDialog");//~1AbvI~
        if (AG.aBTConnection!=null && AG.aBTConnection.androidDialog.isShowing())//~1AbvI~
        {                                                          //~1AbvI~
	    	if (Dump.Y) Dump.println("BluetoothConnection:closeDialog dismiss");//~1AbvI~
		    AG.aBTConnection.waitingDialog=0;               //~1AbvI~
        	AG.aBTConnection.dismiss();                     //~1AbvI~
        }                                                          //~1AbvI~
    }                                                              //~1AbvI~
	//***********************************************************************************//~1AbEI~
    private void callSettingsBT()                                  //~1AbEI~
    {                                                              //~1AbEI~
   	    if (Dump.Y) Dump.println("BluetoothConnection:callSettingsBT");//~1AbEI~
        if (AG.aBT.mBTC==null)                                     //~1AbWR~
        {                                                          //~1AbWR~
			ABT.BTNotAvailable();                                      //~1AbRI~//~1AbWI~
        	return;                                                //~1AbWR~
        }                                                          //~1AbWR~
    	Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);//~1AbEI~
        AG.activity.startActivity(intent);                         //~1AbEI~
    }                                                              //~1AbEI~
	//***********************************************************************************//~1AbRR~
	//*maintain connected devices to list additional to paired device//~1AbRI~
	//***********************************************************************************//~1AbRI~
    public static void addConnectedDevice(String Pname,String Paddr)//~1AbRI~
    {                                                              //~1AbRI~
	    if (Dump.Y) Dump.println("BluetoothConnection addConnectedDevice name="+Pname+",addr="+Paddr);//~1AbRI~//~1AbSR~
    	if (Paddr==null)                                           //~1AbRI~
        	return;                                                //~1AbRI~
    	String nm=Pname;                                           //~1AbRI~
        if (nm==null || nm.equals(""))                                              //~1AbRI~//~1AbUR~
        	nm=Paddr;                                              //~1AbRI~
//      addToDeviceList(nm,Paddr);                                 //~1AbRI~//~1AbSR~
        synchronized(SdeviceListLRU)                                   //~1AbRI~//~1AbUR~
        {                                                          //~1AbRI~
        	if (SdeviceListLRU.get(nm)!=null)	//if dup ,insert to last   //~1AbSI~//~1AbUR~
            	SdeviceListLRU.remove(nm);                          //~1AbSI~//~1AbUR~
        	SdeviceListLRU.put(nm,Paddr);                              //~1AbRR~//~1AbUR~
        }                                                          //~1AbRI~
    }                                                              //~1AbRI~
//    //***********************************************************************************//~1AbSI~//~1AbUR~
//    //*maintain connected devices to list additional to paired device//~1AbSI~//~1AbUR~
//    //***********************************************************************************//~1AbSI~//~1AbUR~
//    private void deleteConnectedDevice(String Pname,int Pindex)    //~1AbSR~//~1AbUR~
//    {                                                              //~1AbSI~//~1AbUR~
//        int pairctr;                                               //~1AbSI~//~1AbUR~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice name="+Pname+",index="+Pindex);//~1AbSR~//~1AbUR~
//        if (DeviceList==null)                                      //~1AbSI~//~1AbUR~
//            pairctr=0;                                             //~1AbSI~//~1AbUR~
//        else                                                       //~1AbSI~//~1AbUR~
//            pairctr=DeviceList.length/2;                           //~1AbSI~//~1AbUR~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice name="+Pname+",index="+Pindex+",DeviceList size="+pairctr);//~1AbSI~//~1AbUR~
//        if (Pindex<pairctr)                                        //~1AbSR~//~1AbUR~
//        {                                                          //~1AbSI~//~1AbUR~
//            String addr=DeviceList[Pindex*2+1];                    //~1AbSI~//~1AbUR~
//            deleteFromDeviceList(addr);                            //~1AbSI~//~1AbUR~
//            return;                                                //~1AbSI~//~1AbUR~
//        }                                                          //~1AbSI~//~1AbUR~
//        int idx=Pindex-pairctr;                                    //~1AbSI~//~1AbUR~
//        if (SdeviceListLRU==null)                                      //~1AbSI~//~1AbUR~
//            return;                                                //~1AbSI~//~1AbUR~
//        synchronized(SdeviceListLRU)                                   //~1AbSR~//~1AbUR~
//        {                                                          //~1AbSR~//~1AbUR~
//            int sz=SdeviceListLRU.size();                              //~1AbSI~//~1AbUR~
//            if (idx<sz)                                            //~1AbSI~//~1AbUR~
//            {                                                      //~1AbSI~//~1AbUR~
//                ArrayList<String> listv=new ArrayList<String>(SdeviceListLRU.values());//~1AbSI~//~1AbUR~
//                if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice idx on SdeviceListLRU="+idx+"="+listv.get(idx));//~1AbSI~//~1AbUR~
//                if (Pname.equals(listv.get(idx)))                       //~1AbSI~//~1AbUR~
//                {                                                  //~1AbSI~//~1AbUR~
//                    ArrayList<String> listk=new ArrayList<String>(SdeviceListLRU.keySet());//~1AbSI~//~1AbUR~
//                    String addr=listk.get(idx);                        //~1AbSI~//~1AbUR~
//                    SdeviceListLRU.remove(addr);                       //~1AbSI~//~1AbUR~
//                    if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice remove addr="+addr);//~1AbSI~//~1AbUR~
//                }                                                  //~1AbSI~//~1AbUR~
//            }                                                      //~1AbSI~//~1AbUR~
//        }//synch                                                   //~1AbSR~//~1AbUR~
//    }                                                              //~1AbSI~//~1AbUR~
	//***********************************************************************************//~1AbTI~
    private void deleteDeviceList(String Pname)                    //~1AbTI~
    {                                                              //~1AbTI~
	    if (Dump.Y) Dump.println("BluetoothConnection deleteDeviceList name="+Pname);//~1AbTI~
        DeviceData data=SdeviceList.get(Pname);                         //~1AbTI~//~1AbUR~
        if (data!=null)                                            //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        	SdeviceList.remove(Pname);                                  //~1AbTI~//~1AbUR~
	    	if (Dump.Y) Dump.println("BluetoothConnection deleteConnectedDevice deleted");//~1AbTI~
        }                                                          //~1AbTI~
        synchronized(SdeviceListLRU)                               //~1AbUI~
        {                                                          //~1AbUI~
        	SdeviceListLRU.remove(Pname);                          //~1AbUI~
        }                                                          //~1AbUI~
    }                                                              //~1AbTI~
//    //***********************************************************************************//~1AbRI~//~1AbUR~
//    private static void addToDeviceList(String Pname,String Paddr)      //~1AbRI~//~1AbUR~
//    {                                                              //~1AbRI~//~1AbUR~
//        int ctr2;                                                  //~1AbRI~//~1AbUR~
//        if (DeviceList==null)                                      //~1AbRI~//~1AbUR~
//            ctr2=0;                                                //~1AbRI~//~1AbUR~
//        else                                                       //~1AbRI~//~1AbUR~
//            ctr2=DeviceList.length/2;                              //~1AbRI~//~1AbUR~
//        int jj;                                                    //~1AbRI~//~1AbUR~
//        for (jj=0;jj<ctr2;jj++)                                    //~1AbRI~//~1AbUR~
//        {                                                          //~1AbRI~//~1AbUR~
//            if (Paddr.equals(DeviceList[jj*2+1]))                  //~1AbRI~//~1AbUR~
//                break;                                             //~1AbRI~//~1AbUR~
//        }                                                          //~1AbRI~//~1AbUR~
//        if (jj==ctr2)   //to be add to DeviceList                    //~1AbRI~//~1AbUR~
//        {                                                          //~1AbRI~//~1AbUR~
//            if (Dump.Y) Dump.println("BluetoothConnection added"); //~1AbRI~//~1AbUR~
//            if (DeviceList==null)                                  //~1AbRI~//~1AbUR~
//            {                                                      //~1AbRI~//~1AbUR~
//                DeviceList=new String[2];                          //~1AbRI~//~1AbUR~
//                DeviceList[0]=Pname;                                  //~1AbRI~//~1AbUR~
//                DeviceList[1]=Paddr;                               //~1AbRI~//~1AbUR~
//            }                                                      //~1AbRI~//~1AbUR~
//            else                                                   //~1AbRI~//~1AbUR~
//            {                                                      //~1AbRI~//~1AbUR~
//                String[] nl=new String[(ctr2+1)*2];                //~1AbRI~//~1AbUR~
//                System.arraycopy(DeviceList,0,nl,0,ctr2*2);        //~1AbRI~//~1AbUR~
//                nl[ctr2*2]=Pname;                                     //~1AbRI~//~1AbUR~
//                nl[ctr2*2+1]=Paddr;                                //~1AbRI~//~1AbUR~
//                DeviceList=nl;                                     //~1AbRI~//~1AbUR~
//            }                                                      //~1AbRI~//~1AbUR~
//        }                                                          //~1AbRI~//~1AbUR~
//    }                                                              //~1AbRR~//~1AbUR~
//    //***********************************************************************************//~1AbSI~//~1AbUR~
//    private boolean deleteFromDeviceList(String Paddr)             //~1AbSI~//~1AbUR~
//    {                                                              //~1AbSI~//~1AbUR~
//        boolean rc=false;                                          //~1AbSI~//~1AbUR~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteFromDeviceList addr="+Paddr);//~1AbSI~//~1AbUR~
//        int ctr2;                                                  //~1AbSI~//~1AbUR~
//        if (DeviceList==null)                                      //~1AbSI~//~1AbUR~
//            return rc;                                             //~1AbSI~//~1AbUR~
//        ctr2=DeviceList.length/2;                                  //~1AbSI~//~1AbUR~
//        if (ctr2==0)                                               //~1AbSI~//~1AbUR~
//            return rc;                                             //~1AbSI~//~1AbUR~
//        int jj;                                                    //~1AbSI~//~1AbUR~
//        for (jj=0;jj<ctr2;jj++)                                    //~1AbSI~//~1AbUR~
//        {                                                          //~1AbSI~//~1AbUR~
//            if (Paddr.equals(DeviceList[jj*2+1]))                  //~1AbSI~//~1AbUR~
//                break;                                             //~1AbSI~//~1AbUR~
//        }                                                          //~1AbSI~//~1AbUR~
//        if (jj==ctr2)   //to be add to DeviceList                  //~1AbSI~//~1AbUR~
//            return rc;                                             //~1AbSI~//~1AbUR~
//        if (Dump.Y) Dump.println("BluetoothConnection deleteFromDeviceList found idx="+jj);//~1AbSI~//~1AbUR~
//        String[] nl=new String[(ctr2-1)*2];                        //~1AbSI~//~1AbUR~
//        if (jj!=0)                                                 //~1AbSI~//~1AbUR~
//            System.arraycopy(DeviceList,0,nl,0,jj*2);              //~1AbSI~//~1AbUR~
//        if (jj!=ctr2-1)                                            //~1AbSI~//~1AbUR~
//            System.arraycopy(DeviceList,(jj+1)*2,nl,jj*2,((ctr2-1)-jj)*2);//~1AbSR~//~1AbUR~
//        DeviceList=nl;                                             //~1AbSI~//~1AbUR~
//        return true;                                               //~1AbSI~//~1AbUR~
//    }                                                              //~1AbSI~//~1AbUR~
	//***********************************************************************************//~1AbSI~
    private void deleteFromListView(int Pindex)                    //~1AbSR~
    {                                                              //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection deleteFromListView idx="+Pindex);//~1AbSR~
        DL.remove(Pindex);                                         //~1AbSR~
    }                                                              //~1AbSI~
	//***********************************************************************************//~1AbUI~
    private void updateDeviceStatus(int PstatFrom,int PstatTo)     //~1AbUI~
    {                                                              //~1AbUI~
        if (Dump.Y) Dump.println("BluetoothConnection updateDeviceStatus");//~1AbUI~
        SdeviceList.updateStatusAll(PstatFrom,PstatTo);                  //~1AbUI~
    }                                                              //~1AbUI~
//    //***********************************************************************************//~1AbRI~//~1AbTR~
//    //*at open dialog,merge once connected addr                    //~1AbRI~//~1AbTR~
//    //***********************************************************************************//~1AbRI~//~1AbTR~
//    private void mergeConnectedDevices()                           //~1AbRI~//~1AbTR~
//    {                                                              //~1AbRI~//~1AbTR~
//        int hmctr;                                                 //~1AbRI~//~1AbTR~
//        synchronized(SdeviceListLRU)                                   //~1AbSI~//~1AbTR~//~1AbUR~
//        {                                                          //~1AbRI~//~1AbTR~
//            if (!swLRULoaded)                                      //~1AbSI~//~1AbTR~
//            {                                                      //~1AbSI~//~1AbTR~
//                swLRULoaded=true;                                  //~1AbSI~//~1AbTR~
//                getLRU();   //read preference                      //~1AbSM~//~1AbTR~
//            }                                                      //~1AbSI~//~1AbTR~
//            hmctr=SdeviceListLRU.size();                               //~1AbRR~//~1AbTR~//~1AbUR~
//            if (Dump.Y) Dump.println("mergeConnectedDevices size="+hmctr);//~1AbRR~//~1AbTR~
//            String[] sa=DeviceList;                                //~1AbRR~//~1AbTR~
//            if (sa!=null && hmctr!=0)                                          //~1AbRR~//~1AbSR~//~1AbTR~
//            {                                                      //~1AbRR~//~1AbTR~
//                int ctr=sa.length/2;                               //~1AbRR~//~1AbTR~
//                for (int ii=0;ii<ctr;ii++)                         //~1AbRR~//~1AbTR~
//                {                                                  //~1AbRR~//~1AbTR~
//                    String nm=sa[ii*2];                            //~1AbRR~//~1AbTR~
//                    String addr=sa[ii*2+1];                        //~1AbRR~//~1AbTR~
//                  if (chkPaired(addr)!=null)                     //~1AbTR~
//                  {                                              //~1AbTR~
//                    if (Dump.Y) Dump.println("mergerConnectedDevices delete because this is paired="+nm+"="+addr);//~1AbRR~//~1AbTR~
//                    SdeviceListLRU.remove(addr);                       //~1AbRR~//~1AbTR~//~1AbUR~
//                  }                                              //~1AbTR~
//                }                                                  //~1AbRR~//~1AbTR~
//                hmctr=SdeviceListLRU.size();                           //~1AbSI~//~1AbTR~//~1AbUR~
//            }                                                      //~1AbRR~//~1AbTR~
//            if (Dump.Y) Dump.println("mergeConnectedDevices after removed paired,size="+hmctr);//~1AbRR~//~1AbTR~
//            if (hmctr!=0)                                          //~1AbRR~//~1AbTR~
//            {                                                      //~1AbRR~//~1AbTR~
//                ArrayList<String> list=new ArrayList<String>(SdeviceListLRU.keySet()); //~1AbSI~//~1AbTR~//~1AbUR~
//                for (int ii=hmctr-1;ii>=0;ii--)                    //~1AbSI~//~1AbTR~
//                {                                                  //~1AbRR~//~1AbTR~
//                    String addr=list.get(ii);                          //~1AbSI~//~1AbTR~
//                    String nm=SdeviceListLRU.get(addr);                //~1AbRR~//~1AbTR~//~1AbUR~
//                  if (chkListed(addr)==null)    //               //~1AbTR~
//                  {                                              //~1AbTR~
//                    if (Dump.Y) Dump.println("mergeConnectedDevices add top list nm="+nm+",addr="+addr);//~1AbRR~//~1AbTR~
//                    DL.add(nm,statusConnectedOnce,ID_STATUS_CONNECTED_ONCE);//~1AbRR~//~1AbTR~
//                  }                                              //~1AbTR~
////                  addToDeviceList(nm,addr);                      //~1AbSR~//~1AbTR~
//                }                                                  //~1AbRR~//~1AbTR~
//            }                                                      //~1AbRR~//~1AbTR~
//        }//synch                                                   //~1AbRI~//~1AbTR~
//    }                                                              //~1AbRI~//~1AbTR~
	//***********************************************************************************//~1AbTI~
	//*at open dialog,merge once connected addr                    //~1AbTI~
	//***********************************************************************************//~1AbTI~
    private void mergeLRU()                                        //~1AbTI~
    {                                                              //~1AbTI~
        if (!swLRULoaded)                                          //~1AbTI~
        {                                                          //~1AbTI~
            swLRULoaded=true;                                      //~1AbTI~
            getLRU();	//read preference                          //~1AbTI~
        }                                                          //~1AbTI~
        synchronized(SdeviceListLRU)                                   //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        	SdeviceList.merge(SdeviceListLRU,ID_STATUS_CONNECTED_ONCE,false);//~1AbTR~//~1AbUR~
        }                                                          //~1AbTI~
    }                                                              //~1AbTI~
	//***********************************************************************************//~1AbSI~
    private void putLRU()                                          //~1AbSI~
    {                                                              //~1AbSI~
	    int hmctr;                                                 //~1AbSI~
        StringBuffer sb=new StringBuffer("");                      //~1AbSI~
        synchronized(SdeviceListLRU)                                   //~1AbSI~//~1AbUR~
        {                                                          //~1AbSI~
            hmctr=SdeviceListLRU.size();                               //~1AbSI~//~1AbUR~
            if (Dump.Y) Dump.println("BluetoothConnection putLRU,size="+hmctr);//~1AbSI~
            if (hmctr!=0)                                          //~1AbSI~
            {                                                      //~1AbSI~
            	ArrayList<String> list=new ArrayList<String>(SdeviceListLRU.keySet()); //~1AbSI~//~1AbUR~
                for (int ii=hmctr-1,lructr=0;ii>=0;ii--)           //~1AbSR~
                {                                                  //~1AbSI~
                    String name=list.get(ii);                          //~1AbSI~//~1AbUR~
                    String addr=SdeviceListLRU.get(name);              //~1AbSI~//~1AbUR~
			        if (Dump.Y) Dump.println("BluetoothConnection putLRU,size="+hmctr);//~1AbSI~
                    if (lructr==0)                                 //~1AbSI~
	                    sb.append(name+"\t"+addr);                 //~1AbSR~
                    else                                           //~1AbSI~
	                    sb.append("\n"+name+"\t"+addr);            //~1AbSI~
                    lructr++;                                      //~1AbSI~
                    if (lructr>=MAX_LRU)                           //~1AbSI~
                    	break;                                     //~1AbSI~
                }                                                  //~1AbSI~
            }                                                      //~1AbSI~
        }//synch                                                   //~1AbSI~
        String s=sb.toString();                                    //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection putLRU Prop data="+s);//~1AbSI~
        Prop.putPreference(PROPKEY_BTLRU,s);                       //~1AbSI~
    }                                                              //~1AbSI~
	//***********************************************************************************//~1AbSI~
    private void getLRU()                                          //~1AbSI~
    {                                                              //~1AbSI~
        String s=Prop.getPreference(PROPKEY_BTLRU,"");             //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection getLRU Prop data="+s);//~1AbSI~
        if (s.equals(""))                                          //~1AbSI~
        	return;                                                //~1AbSI~
        String lines[]=s.split("\n");                              //~1AbSI~
        if (lines==null)                                           //~1AbSI~
        	return;                                                //~1AbSI~
	    int ctr=lines.length;                                      //~1AbSI~
        if (Dump.Y) Dump.println("BluetoothConnection getLRU,size="+ctr);//~1AbSI~
        if (ctr==0)                                                //~1AbSI~
        	return;                                                //~1AbSI~
        synchronized(SdeviceListLRU)                                   //~1AbSI~//~1AbUR~
        {                                                          //~1AbSI~
            for (int ii=0;ii<ctr;ii++)                             //~1AbSR~
            {                                                      //~1AbSI~
                String name_addr[]=lines[ii].split("\t");          //~1AbSR~
                if (name_addr==null || name_addr.length!=2)          //~1AbSI~
                    continue;                                      //~1AbSI~
                String name=name_addr[0];                          //~1AbSI~
                String addr=name_addr[1];                          //~1AbSI~
                if (Dump.Y) Dump.println("BluetoothConnection getLRU name="+name+"="+addr);//~1AbSI~
        		SdeviceListLRU.put(name,addr);                         //~1AbSI~//~1AbUR~
            }                                                      //~1AbSI~
        }//synch                                                   //~1AbSI~
    }                                                              //~1AbSI~
//    //***********************************************************************************//~1AbTR~
//    private String chkPaired(String Paddr)                       //~1AbTR~
//    {                                                            //~1AbTR~
//        String name=null;                                        //~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection chkPaired addr="+Paddr);//~1AbTR~
//        if (pairedList!=null)                                    //~1AbTR~
//        {                                                        //~1AbTR~
//            String[] sa=pairedList;                              //~1AbTR~
//            int ctr=sa.length/2;                                 //~1AbTR~
//            for (int ii=0;ii<ctr;ii++)                           //~1AbTR~
//            {                                                    //~1AbTR~
//                String addr=sa[ii*2+1];                          //~1AbTR~
//                if (Dump.Y) Dump.println("BluetoothConnection chkPaired addr="+addr);//~1AbTR~
//                if (Paddr.equals(addr))                          //~1AbTR~
//                {                                                //~1AbTR~
//                    name=sa[ii*2];                               //~1AbTR~
//                    break;                                       //~1AbTR~
//                }                                                //~1AbTR~
//            }                                                    //~1AbTR~
//        }                                                        //~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection chkPaired return name="+name);//~1AbTR~
//        return name;                                             //~1AbTR~
//    }                                                            //~1AbTR~
//    //***********************************************************************************//~1AbTR~
//    private void mergePairedDevice()                             //~1AbTR~
//    {                                                            //~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection mergePaired");//~1AbTR~
//        String[] sapd=pairedList;                                //~1AbTR~
//        String[] sadl=DeviceList;                                //~1AbTR~
//        if (sapd==null || sadl==null)                            //~1AbTR~
//            return;                                              //~1AbTR~
//        int ctrpd=sapd.length/2;                                 //~1AbTR~
//        int ctr=sadl.length/2;                                   //~1AbTR~
//        int addctr=0;                                            //~1AbTR~
//        for (int ii=0;ii<ctrpd;ii++)                             //~1AbTR~
//        {                                                        //~1AbTR~
//            String addr=sapd[ii*2+1];                            //~1AbTR~
//            if (chkListed(addr)==null)                           //~1AbTR~
//                addctr++;                                        //~1AbTR~
//        }                                                        //~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection mergePaired addctr="+addctr);//~1AbTR~
//        if (addctr==0)                                           //~1AbTR~
//            return;                                              //~1AbTR~
//        String[] nl=new String[(ctr+addctr)*2];                  //~1AbTR~
//        System.arraycopy(sadl,0,nl,0,ctr*2);                     //~1AbTR~
//        addctr=0;                                                //~1AbTR~
//        for (int ii=0;ii<ctr;ii++)                               //~1AbTR~
//        {                                                        //~1AbTR~
//            String addr=sapd[ii*2+1];                            //~1AbTR~
//            String name=chkListed(addr);                         //~1AbTR~
//            if (name!=null)                                      //~1AbTR~
//                continue;                                        //~1AbTR~
//            sadl[(ctr+addctr)*2]=name;                           //~1AbTR~
//            sadl[(ctr+addctr)*2+1]=addr;                         //~1AbTR~
//            addctr++;                                            //~1AbTR~
//        }                                                        //~1AbTR~
//        DeviceList=nl;                                           //~1AbTR~
//    }                                                            //~1AbTR~
//    //***********************************************************************************//~1AbTR~
//    private String chkListed(String Paddr)                       //~1AbTR~
//    {                                                            //~1AbTR~
//        String name=null;                                        //~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection chkListed addr="+Paddr);//~1AbTR~
//        if (DeviceList!=null)                                    //~1AbTR~
//        {                                                        //~1AbTR~
//            String[] sa=DeviceList;                              //~1AbTR~
//            int ctr=sa.length/2;                                 //~1AbTR~
//            for (int ii=0;ii<ctr;ii++)                           //~1AbTR~
//            {                                                    //~1AbTR~
//                String addr=sa[ii*2+1];                          //~1AbTR~
//                if (Dump.Y) Dump.println("BluetoothConnection chkListed add="+addr);//~1AbTR~
//                if (Paddr.equals(addr))                          //~1AbTR~
//                {                                                //~1AbTR~
//                    name=sa[ii*2];                               //~1AbTR~
//                    break;                                       //~1AbTR~
//                }                                                //~1AbTR~
//            }                                                    //~1AbTR~
//        }                                                        //~1AbTR~
//        if (Dump.Y) Dump.println("BluetoothConnection chkListed return name="+name);//~1AbTR~
//        return name;                                             //~1AbTR~
//    }                                                            //~1AbTR~
    //****************************************************         //~1AbTI~
    class DeviceDataList                             //~1AbTI~     //~1AbUR~
    {                                                              //~1AbTI~
	    public Map<String,DeviceData> devlist=new LinkedHashMap<String,DeviceData>();//~1AbTI~//~1AbUR~
    //*****************                                            //~1AbTI~
        public DeviceDataList()                                             //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbTI~
        public DeviceData get(String Pname)                        //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
        	return devlist.get(Pname);                             //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbTI~
        public void put(String[] Plist,int Pstat)                  //~1AbTR~
        {                                                          //~1AbTI~
        	for (int ii=0;ii<Plist.length/2;ii++)                  //~1AbTI~
            {                                                      //~1AbTI~
            	String name=Plist[ii*2];                           //~1AbTI~
            	String addr=Plist[ii*2+1];                         //~1AbTI~
            	DeviceData data=new DeviceData(name,addr,Pstat);   //~1AbTR~//~1AbUR~
                if (Dump.Y) Dump.println("DeviceDataList:put key="+name+",addr="+addr+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
    			devlist.put(name,data);                            //~1AbTR~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbUI~
        public void remove(String Pname)                           //~1AbUI~
        {                                                          //~1AbUI~
            if (Dump.Y) Dump.println("DeviceDataList:remove key="+Pname);//~1AbUR~
        	devlist.remove(Pname);                                 //~1AbUI~
        }                                                          //~1AbUI~
	    //*************************************************************//~1AbTI~
	    //*for Paired/Discovered device list                       //~1AbUI~
	    //*************************************************************//~1AbUI~
        public void merge(String[] Plist,int Pstat)                //~1AbTI~
        {                                                          //~1AbTI~
        	for (int ii=0;ii<Plist.length/2;ii++)                  //~1AbTI~
            {                                                      //~1AbTI~
            	String name=Plist[ii*2];                           //~1AbTI~
            	String addr=Plist[ii*2+1];                         //~1AbTI~
                DeviceData data=get(addr);                         //~1AbTR~//~1AbUR~
                if (data==null)                                    //~1AbTI~
                {                                                  //~1AbTI~
                	if (Dump.Y) Dump.println("DeviceDataList:merge add key="+addr+",name="+name+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
            		DeviceData adddata=new DeviceData(name,addr,Pstat);//~1AbTI~//~1AbUR~
    				devlist.put(name,adddata);                     //~1AbTI~
                }                                                  //~1AbTI~
                else                                               //~1AbTI~
                {                                                  //~1AbTI~
                	if (Dump.Y) Dump.println("DeviceDataList:merge rep key="+addr+",name="+name+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
                	data.addr=addr;                                //~1AbUR~
					data.stat=Pstat;                               //~1AbUR~
                }                                                  //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbTI~
	    //*for ConnectedDeviceLRU                                  //~1AbUI~
	    //*************************************************************//~1AbUI~
        public void merge(Map<String,String> Pmap,int Pstat,boolean Preplace)//~1AbTR~
        {                                                          //~1AbTI~
            ArrayList<String> src=new ArrayList<String>(Pmap.keySet());    //names//~1AbTR~
        	for (int ii=0;ii<src.size();ii++)                      //~1AbTR~
            {                                                      //~1AbTI~
            	String name=src.get(ii);                           //~1AbTI~//~1AbUR~
            	String addr=Pmap.get(name);                        //~1AbTR~//~1AbUR~
                DeviceData data=get(name);                         //~1AbTR~//~1AbUR~
                if (data==null)                                    //~1AbTI~
                {                                                  //~1AbTI~
	                if (Dump.Y) Dump.println("DeviceDataList:merge Pmap add name="+name+",addr="+addr+",stat="+Pstat);//~1AbTR~//~1AbUR~
            		DeviceData adddata=new DeviceData(name,addr,Pstat);//~1AbTI~//~1AbUR~
    				devlist.put(name,adddata);                     //~1AbTI~
                }                                                  //~1AbTI~
                else                                               //~1AbTI~
                if (Preplace)                                      //~1AbTI~
                {                                                  //~1AbTI~
	                if (Dump.Y) Dump.println("DeviceDataList:merge Plist rep key="+addr+",name="+name+",stat="+Pstat+",DeviceData="+data);//~1AbTI~//~1AbUR~
                	data.name=name;                                //~1AbTR~
                    data.addr=addr;                                //~1AbTI~
					data.stat=Pstat;                               //~1AbTI~
                }                                                  //~1AbTI~
            }                                                      //~1AbTI~
        }                                                          //~1AbTI~
	    //*************************************************************//~1AbUI~
        public int search(String Pname)                            //~1AbUI~
        {                                                          //~1AbUI~
        	int idx=-1;                                            //~1AbUI~
            ArrayList<String> keys=new ArrayList<String>(devlist.keySet());    //names//~1AbUI~
        	for (int ii=0;ii<keys.size();ii++)                     //~1AbUI~
            {                                                      //~1AbUI~
            	String name=keys.get(ii);                          //~1AbUI~
                if (name.equals(Pname))                            //~1AbUI~
                {                                                  //~1AbUI~
                	idx=ii;                                        //~1AbUI~
                	break;                                         //~1AbUI~
                }                                                  //~1AbUI~
            }                                                      //~1AbUI~
	        if (Dump.Y) Dump.println("DeviceDataList:search name="+Pname+",idx="+idx);//~1AbUI~
            return idx;                                            //~1AbUI~
        }                                                          //~1AbUI~
	    //*************************************************************//~1AbUI~
        public int updateStatusAll(int Pfrom,int Pto)                 //~1AbUI~
        {                                                          //~1AbUI~
        	int ctr=0;                                             //~1AbUI~
            ArrayList<String> keys=new ArrayList<String>(devlist.keySet());    //names//~1AbUI~
        	for (int ii=0;ii<keys.size();ii++)                     //~1AbUI~
            {                                                      //~1AbUI~
            	String name=keys.get(ii);                          //~1AbUI~
                DeviceData data=get(name);                         //~1AbUI~
                if (data.stat==Pfrom)                              //~1AbUI~
                {                                                  //~1AbUI~
			        if (Dump.Y) Dump.println("DeviceDataList:updateStatus updated="+name);//~1AbUI~
                	data.stat=Pto;                                 //~1AbUI~
                    ctr++;                                         //~1AbUI~
                }                                                  //~1AbUI~
            }                                                      //~1AbUI~
	        if (Dump.Y) Dump.println("DeviceDataList:updateStatus updatectr="+ctr);//~1AbUI~
            return ctr;                                            //~1AbUI~
        }                                                          //~1AbUI~
    }//class                                                       //~1AbTI~
    //****************************************************         //~1AbTI~
    class DeviceData                                               //~1AbTI~//~1AbUR~
    {                                                              //~1AbTI~
    //*****************                                            //~1AbTI~
    	public String name,addr;                                  //~1AbTI~
        public int stat;                                          //~1AbTI~
        public DeviceData(String Pname,String Paddr,int Pstat)     //~1AbTI~//~1AbUR~
        {                                                          //~1AbTI~
    		name=Pname;addr=Paddr;stat=Pstat;                      //~1AbTI~
        }                                                          //~1AbTI~
    }//class                                                       //~1AbTI~
	//*************************************************************************//~1Ac5I~
    private boolean chkDiscoverable(boolean Psecure,boolean Pserver)//~1Ac5I~
	{                                                              //~1Ac5I~
    	boolean rc=true;                                           //~1Ac5I~
    	if (swAlertAction)                                         //~1Ac5I~
        	return rc;                                             //~1Ac5I~
        if (Dump.Y) Dump.println("BluetoothConnection.chkDiscoverable secure="+Psecure);//~1Ac5I~
        if (Psecure)                                               //~1Ac5I~
        {                                                          //~1Ac5I~
        	if (!ABT.BTisDiscoverable())                           //~1Ac5I~
            {                                                      //~1Ac5I~
			    showNotDiscoverableAlert(Pserver);                 //~1Ac5I~
                rc=false;  //DialogNFCBT from alert Action         //~1Ac5I~
            }                                                      //~1Ac5I~
        }                                                          //~1Ac5I~
        return rc;                                                 //~1Ac5I~
    }                                                              //~1Ac5I~
	//*************************************************************************//~1Ac5I~
    private void showNotDiscoverableAlert(boolean Pserver)         //~1Ac5I~
    {                                                              //~1Ac5I~
    	swServer=Pserver;                                          //~1Ac5I~
        AlertI ai=new AlertI()                                     //~1Ac5I~
                            {                                      //~1Ac5I~
                                @Override                          //~1Ac5I~
                                public int alertButtonAction(int Pbuttonid,int Pitempos)//~1Ac5I~
                                {                                  //~1Ac5I~
                                	swAlertAction=true;            //~1Ac5I~
                                    if (Dump.Y) Dump.println("BluetoothConnection swServer="+swServer+",buttonid="+Integer.toHexString(Pbuttonid));//~1Ac5I~
                                    if (Pbuttonid==Alert.BUTTON_POSITIVE)//~1Ac5I~
                                    {                              //~1Ac5I~
                                    	if (swServer)              //~1Ac5I~
											doAction(AG.resource.getString(STRID_ACCEPT));//~1Ac5I~
                                        else                       //~1Ac5I~
											doAction(AG.resource.getString(STRID_CONNECT));//~1Ac5I~
                                    }                              //~1Ac5I~
                                	swAlertAction=false;           //~1Ac5I~
                                    return 1;                      //~1Ac5I~
                                }                                  //~1Ac5I~
                            };                                     //~1Ac5I~
        Alert.simpleAlertDialog(ai,R.string.Title_Bluetooth,R.string.WarningBTNotDiscoverable,//~1Ac5R~
                            Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE);//~1Ac5I~
    }                                                              //~1Ac5I~
    //******************************************                   //~1AedI~
	public static String getMsgStringBTAccepting(String Pdevice,boolean Psecure)//~1AedI~
    {                                                              //~1AedI~
    	String secureopt=AG.resource.getString(Psecure ? R.string.BTSecure : R.string.BTInSecure);//~1AedI~
		String msg=AG.resource.getString(R.string.Msg_WaitingAccept,Pdevice,secureopt);//~1AedI~
        return msg;                                                //~1AedI~
    }                                                              //~1AedI~
    //******************************************                   //~1AedI~
	public static String getMsgStringBTConnecting(String Pdevice,boolean Psecure)//~1AedI~
    {                                                              //~1AedI~
    	String secureopt=AG.resource.getString(Psecure ? R.string.BTSecure : R.string.BTInSecure);//~1AedI~
        String msg=AG.resource.getString(R.string.Msg_WaitingConnect,Pdevice,secureopt);//~1AedI~
        return msg;                                                //~1AedI~
    }                                                              //~1AedI~
    //******************************************                   //~1Af1I~
	public static void onReceive(String Paction)                   //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.onReceive action="+Paction);//~1Af1I~
        try                                                        //~1Af1I~
        {                                                          //~1Af1I~
            if (AG.aBTConnection!=null && AG.aBTConnection.androidDialog.isShowing())//~1Af1I~
            {                                                      //~1Af1I~
                BluetoothConnection btc=AG.aBTConnection;          //~1Af1I~
                btc.runRenewal();                                  //~1Af1I~
            }                                                      //~1Af1I~
        }                                                          //~1Af1I~
        catch(Exception e)                                         //~1Af1I~
        {                                                          //~1Af1I~
        	Dump.println(e,"BluetoothConnection:onReceive");       //~1Af1I~
        }                                                          //~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af1I~
    //* request run on UIThread                                    //~1Af1I~
    //******************************************                   //~1Af1I~
	public void runRenewal()                                       //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.renewal");   //~1Af1I~
        URunnableI ur=new URunnableI()                             //~1Af1I~
								{                                  //~1Af1I~
								 	public void runFunc(Object parmObj,int parmInt)//~1Af1I~
									{                              //~1Af1I~
								        if (Dump.Y) Dump.println("BluetoothConnection.runRenewal.runFunc");//~1Af1I~
								        try                        //~1Af1I~
        								{                          //~1Af1I~
                                        	renewal();             //~1Af1I~
                                        }                          //~1Af1I~
        								catch(Exception e)         //~1Af1I~
        								{                          //~1Af1I~
        									Dump.println(e,"BluetoothConnection:runFunc");//~1Af1I~
        								}                          //~1Af1I~
									}                              //~1Af1I~
                                };                                 //~1Af1I~
        URunnable.setRunFuncDirect(ur,this/*parm obj*/,0/*parm int*/);//widthout delay//~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af1I~
    //*on MainThread                                               //~1Af1I~
    //******************************************                   //~1Af1I~
	public void renewal()                                          //~1Af1I~
    {                                                              //~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.renewal");   //~1Af1I~
//        super(Pgf,AG.resource.getString(R.string.Title_Bluetooth),//~1Af1I~
//                (AG.screenDencityMdpiSmallV || AG.screenDencityMdpiSmallH/*mdpi and height or width <=320*/ ? R.layout.bluetooth_mdpi : R.layout.bluetooth),//~1Af1I~
//                true,false);                                     //~1Af1I~
//        GF=Pgf;                                                  //~1Af1I~
//        statusPaired=AG.resource.getString(ID_STATUS_PAIRED);    //~1Af1I~
//        statusDiscovered=AG.resource.getString(ID_STATUS_DISCOVERED);//~1Af1I~
//        statusConnected=AG.resource.getString(ID_STATUS_CONNECTED);//~1Af1I~
//        statusConnectedOnce=AG.resource.getString(ID_STATUS_CONNECTED_ONCE);//~1Af1I~
//        tvRemoteDevice=(TextView)(findViewById(R.id.RemoteDevice));//~1Af1I~
//        cbSecureOption=(CheckBox)(findViewById(R.id.BTSecureOption));//~1Af1I~
        displayDevice();                                           //~1Af1I~
//        if (SdeviceList==null)                                   //~1Af1I~
//            SdeviceList=new DeviceDataList();                    //~1Af1I~
	    getDeviceList();                                           //~1Af1I~
	    setSelection();                                            //~1Af1I~
//        new ButtonAction(this,0,R.id.BTSettings);                //~1Af1I~
//        acceptButton=new ButtonAction(this,0,R.id.BTAccept);     //~1Af1I~
//        gameButton=new ButtonAction(this,0,R.id.BTGame);         //~1Af1I~
//        connectButton=new ButtonAction(this,0,R.id.BTConnect);   //~1Af1I~
//        disconnectButton=new ButtonAction(this,0,R.id.BTDisConnect);//~1Af1I~
//        new ButtonAction(this,0,R.id.Delete);                    //~1Af1I~
//        new ButtonAction(this,0,R.id.Discoverable);              //~1Af1I~
//        new ButtonAction(this,0,R.id.Discover);                  //~1Af1I~
//        new ButtonAction(this,0,R.id.Cancel);                    //~1Af1I~
//        new ButtonAction(this,0,R.id.Help);                      //~1Af1I~
    	if (AG.RemoteStatus==AG.RS_BTCONNECTED)                    //~1Af1I~
        {                                                          //~1Af1I~
            acceptButton.setEnabled(false);                        //~1Af1I~
            connectButton.setEnabled(false);                       //~1Af1I~
        }                                                          //~1Af1I~
        else                                                       //~1Af1I~
    	if ((AG.RemoteStatusAccept & (AG.RS_BTLISTENING_SECURE|AG.RS_BTLISTENING_INSECURE))!=0)//~1Af1I~
        {                                                          //~1Af1I~
  	        gameButton.setEnabled(false);                          //~1Af1I~
            disconnectButton.setEnabled(false);                    //~1Af1I~
        	acceptButton.setAction(R.string.BTStopAccept);         //~1Af1I~
        }                                                          //~1Af1I~
        else                                                       //~1Af1I~
        {                                                          //~1Af1I~
            gameButton.setEnabled(false);                          //~1Af1I~
            disconnectButton.setEnabled(false);                    //~1Af1I~
        }                                                          //~1Af1I~
//        setDismissActionListener(this/*DoActionListener*/);      //~1Af1I~
//        setFromPropSecureOption();                               //~1Af1I~
//        validate();                                              //~1Af1I~
//        show();                                                  //~1Af1I~
//        AG.aBTConnection=this;  //used when PartnerThread detected err//~1Af1I~
        if (Dump.Y) Dump.println("BluetoothConnection.renewal return");//~1Af1I~
    }                                                              //~1Af1I~
    //******************************************                   //~1Af3I~
    private void displayDiscoverableStatus()                       //~1Af3I~
    {                                                              //~1Af3I~
        String dev;                                                //~1Af3I~
        TextView v;                                                //~1Af3I~
    //************************                                     //~1Af3I~
        v=(TextView)(findViewById(R.id.LocalDeviceDiscoverable));  //~1Af3I~
        if (AG.aBT.mBTC==null)                                 //+1Af6I~
        {                                                          //+1Af6I~
        	dev=AG.resource.getString(R.string.noBTadapter);       //+1Af6I~
        }                                                          //+1Af6I~
        else                                                       //+1Af6I~
        if (ABT.BTisDiscoverable())                                //~1Af3I~
        {                                                          //~1Af3I~
        	dev=AG.resource.getString(R.string.StatusDiscoverable);//~1Af3I~
            discoverableButton.setEnabled(false);                  //~1Af3I~
        }                                                          //~1Af3I~
        else                                                       //~1Af3I~
        {                                                          //~1Af3I~
        	dev=AG.resource.getString(R.string.StatusNotDiscoverable);//~1Af3I~
            discoverableButton.setEnabled(true);                   //~1Af3I~
        }                                                          //~1Af3I~
        v.setText(dev);                                            //~1Af3I~
    }                                                              //~1Af3I~
}

