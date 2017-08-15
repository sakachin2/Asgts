//*CID://+1Ad3R~:                           update#=  222;         //~1Ad3R~
//*************************************************************************
//1Af2 2016/07/06 (Ajagot1w)Msg "Not NFC dialog" is not shown when NFC dialog proceeed to connected dialog//~1Af2I~
//1Ad3 2015/07/19 Bypass NFCSelect, by NFS-WD and NFC-BT button directly.//~1Ad3I~
//1Aec 2015/07/26 set connection type for Server                   //~1AecI~
//1AbB 2015/06/22 mask mac addr for security                       //~1AbBI~
//1Abz 2015/06/21 NFCWD:use helpdialog for help                    //~1AbzI~
//1Abx 2015/06/21 NFCWD:dismiss waiting dialog of makepair at disconnected(with no Accesspoint status)//~1AbxI~
//1Abw 2015/06/21 NFCWD:no need of NFC button because it is set on NFCSelect//~1AbwI~
//1Abs 2015/06/17 NFC:Ignore NFC msg when already connected        //~1AbsI~
//1Abr 2015/06/16 NFC:colored background only when accept NFC tap. //~1AbrI~
//1Abk 2015/06/16 NFC:errmsg when intent is for other side of NFC/NFCBT//~1AbkI~
//1Ab0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //~1Ab0I~
//1A79 2015/02/24 (Bug of 1A77)no progress dialog waiting start game request after paired//~1A79I~
//1A78 2015/02/24 When NFC was enabled,no response for contact; issuen warning//~1A78I~
//1A77 2015/02/24 progressdialog is not closed when already paired at NFC touch//~1A77I~
//1A6K 2015/02/23 NFC:invisible button when disconnected           //~1A6KI~
//1A6E 2015/02/22 NFC setting is not ACTION_NFCSHARING_SETTINGS(beam) but ACTION_WIRESS_SETTING(nfc)//~1A6BI~
//                beam requires NFC on.                            //~1A6BI~
//1A6B 2015/02/21 IP game title;identify IP and WifiDirect(WD)     //~1A6BI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame
//*************************************************************************
package wifidirect;

import java.nio.charset.Charset;
import java.util.Locale;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.Message;
import jagoclient.partner.ConnectPartner;
//import jagoclient.partner.IPConnection;                          //~1Ab0R~
//import jagoclient.partner.PartnerFrame;                          //~1Ab0R~
import wifidirect.PartnerFrame;                                    //~1A84I~//~1A90I~//~1Ab0I~
//import jagoclient.partner.Server;                                //~1Ab0R~
import wifidirect.Server;                                          //~1Ab0I~
import jagoclient.partner.partner.Partner;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.os.Parcelable;
import android.provider.Settings;                                  //+1Ad3I~
import android.view.View;
import android.view.ViewGroup;
import com.Asgts.AG;                                               //~1A6BR~
import com.Asgts.AView;                                            //~1A6BR~
import com.Asgts.Alert;
import com.Asgts.ProgDlg;                                          //~1A6BR~
import com.Asgts.ProgDlgI;                                         //~1A6BR~
import com.Asgts.R;                                                //~1A6BR~
import com.Asgts.URunnable;                                        //~1A6BR~
import com.Asgts.URunnableData;                                    //~1A6BR~
import com.Asgts.awt.Component;                                    //~1A6BR~
import com.Asgts.awt.UButton;                                       //~5218I~//~1A6BR~
import com.Asgts.awt.UTextView;                                     //~5218I~//~1A6BR~
import com.axe.AxeDialog;

@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14
public class DialogNFC extends AxeDialog
		implements ProgDlgI //onCancelProgDlg                      //~5218I~
{
	public static DialogNFC SdialogNFC;
//  public static final String  DATA_PREFIX="MacAddress=";         //~1AbkR~
    public static final String  DATA_PREFIX="MacAddressWD=";       //~1AbkI~
    public static final String  DATA_SEP=",";                      //~5218R~
	private static final int LAYOUTID=R.layout.dialognfc;
	private static final int LAYOUTID_MDPI=R.layout.dialognfc_mdpi;//~1A6BI~
    private static final int TITLEID=R.string.DialogTitle_NFC;
    private static final int TEXTID_INITIAL=R.string.WifiNFCInitialMsg;
//  private static final int TEXTID_HELP=R.string.Help_WifiNFC;    //~1AbzR~
    private static final int HELPTITLEID=R.string.HelpTitle_NFCWiFi;//~1AbzI~
    private static final int ID_ACCEPT=R.id.IPAccept;              //~5219I~
    private static final int ID_CONNECT=R.id.IPConnect;            //~5219I~
    private static final int ID_GAME=R.id.IPGame;                  //~5219I~
    private static final int ID_DISCONNECT=R.id.IPDisconnect;      //~5219I~
//  private static final int ID_SETTING=R.id.WifiNFCSettings;      //~5219I~//~1Ad3R~
    private static final int ID_SETTING=R.id.WiFiSettings;         //~1Ad3I~
    private static final int ID_SETTING_NFC=R.id.NFCSettings;      //~1Ad3I~
    private static final int COLOR_NFCMSG=0xffffff90;	//yellow   //~1AbsI~
    private UTextView tvMsg,tvName,tvAddr,tvStat;                  //~5218R~
    private UTextView tvPeerName,tvPeerStat,tvThisIP,tvPeerIP;     //~5218R~
    private UButton btnSettings,btnAccept,btnGame,btnDisconnect,btnConnect;            //~5218R~//~5219R~
    private UButton btnSettingsNFC;                                //~1Ad3I~
    private ViewGroup vgUpperButtons;                              //~5219I~
 	String mimetype,macAddr;
	public WiFiDirectActivityDialog  wifiActivity;
    public boolean swWifiEnable;
    String deviceName,deviceAddr,deviceStatus,peerAddr,peerName="",ownerIPAddr;//~5219R~
    private WifiP2pDevice thisDevice;
//  private ProgressDialog progressDialog;                         //~5218R~
    private URunnableData progressDialog;                          //~5218I~
    private int thisOwner=-1;	//1:owner,0:client
    private boolean  swWaitingPeerDiscovery;                       //~5218R~
    private boolean  swWaitingConnection;                          //~1AbxI~
    private boolean  swPairingRequester;                            //~5218I~
    private int portNo;                                            //~5218I~
    private boolean swStartIPConnection;                           //~5218I~
    private boolean swTouch;  //to chk already paired at NFC button//~5218I~
    private NfcAdapter mNfcAdapter;                                //~1A78I~
    private boolean isNFCenabled;                                  //~1A78I~
    private boolean swPaired;                                      //~1AbsI~
    private int bgMsgInitialColor;                               //~1AbsI~
	//***********************************************************************************
	public static DialogNFC showDialog()
    {
    	DialogNFC dlg=new DialogNFC();
        String title=AG.resource.getString(TITLEID);
        if (!dlg.swWifiEnable)
        	return null;
		dlg.showDialog(title);
        SdialogNFC=dlg;
        return dlg;
    }
	//***********************************************************************************
    public DialogNFC()
    {
//  	super( LAYOUTID);                                          //~1A6BR~
    	super(AG.layoutMdpi ? LAYOUTID_MDPI:LAYOUTID);             //~1A6BI~
		init();
    }
    private void init()
    {
        chkNFCenabled();                                           //~1A78I~
 		mimetype=("application/"+AG.pkgName).toLowerCase(Locale.ENGLISH);
		portNo=getPortNo();                                        //~5218I~
        getMacAddr();
    }
	//***********************************************************************************
    @Override
	protected void setupDialogExtend(ViewGroup PlayoutView)
    {
		btnSettings=new UButton(layoutView,ID_SETTING);  //~5218R~ //~5219R~
		btnSettingsNFC=new UButton(layoutView,ID_SETTING_NFC);     //~1Ad3I~
		btnAccept=new UButton(layoutView,ID_ACCEPT);               //~5219R~
		btnConnect=new UButton(layoutView,ID_CONNECT);             //~5219R~
		btnDisconnect=new UButton(layoutView,ID_DISCONNECT);       //~5219R~
		btnGame=new UButton(layoutView,ID_GAME);     //~5218R~     //~5219R~
                                                                   //~5219I~
		vgUpperButtons=(ViewGroup)layoutView.findViewById(R.id.UpperButtons);//~5219R~
		setButtonListenerAll(R.id.UpperButtons);                   //~5219I~
        setButtonListener(btnSettings.androidButton);              //~5218R~
        setButtonListener(btnSettingsNFC.androidButton);           //~1Ad3I~
        tvMsg=new UTextView(layoutView,R.id.Msg);                  //~5218R~
        tvName=new UTextView(layoutView,R.id.DeviceName);          //~5218R~
        tvAddr=new UTextView(layoutView,R.id.DeviceAddr);          //~5218R~
        tvStat=new UTextView(layoutView,R.id.DeviceStatus);        //~5218R~
        tvPeerName=new UTextView(layoutView,R.id.PeerName);        //~5218R~
        tvPeerStat=new UTextView(layoutView,R.id.PeerStatus);    //~5218R~
        tvThisIP=new UTextView(layoutView,R.id.ThisIPAddr);        //~5218R~
        tvPeerIP=new UTextView(layoutView,R.id.PeerIPAddr);     //~5218R~
        new Component().setVisibility(vgUpperButtons,View.GONE);   //~5219I~
        setUpperButtonsVisiblity(View.GONE);                       //~5219I~
    	bgMsgInitialColor=COLOR_NFCMSG;                            //~1AbsI~
        showMsg(TEXTID_INITIAL);
    }
	//***********************************************************************************
    private void setUpperButtonsVisiblity(int Pvisible)            //~5219I~
    {                                                              //~5219I~
        new Component().setVisibility(vgUpperButtons,Pvisible);    //~5219I~
    }                                                              //~5219I~
	//***********************************************************************************//~5219I~
    @Override
    protected void onDismiss()
    {
    	if (Dump.Y) Dump.println("DialogNFC:onDismiss");           //~5218I~
    	wifiActivity.onDismiss();
        SdialogNFC=null;
        if (swStartIPConnection)                                   //~5218I~
		    setIPConnection(thisOwner==1);                   //~5218I~
    	return;
    }
	//***********************************************************************************//~5219I~
	//*                                                            //~5219I~
	//***********************************************************************************//~5219I~
    public static void closeDialog()                               //~5219I~
    {                                                              //~5219I~
    	if (Dump.Y) Dump.println("DialogNFC:closeDialog");         //~5219I~
        if (SdialogNFC!=null && SdialogNFC.isShowing())           //~5219I~
        {                                                          //~5219I~
	        SdialogNFC.swStartIPConnection=false;                             //~5219I~
	    	if (Dump.Y) Dump.println("DialogNFC:closeDialog dismiss");//~5219I~
        	SdialogNFC.dismiss();                                  //~5219I~
        }                                                          //~5219I~
    }                                                              //~5219I~
	//**********************************                           //~5218I~
	@Override                                                      //~5218I~
    protected boolean onClickHelp()                                //~5218I~
    {                                                              //~5218I~
//      showMsg(TEXTID_HELP);                                      //~5218I~//~1AbzR~
  		new HelpDialog(Global.frame(),HELPTITLEID,"nfcwifi");      //~1AbzI~
        return false;	//not dismiss                              //~5218I~
    }                                                              //~5218I~
	//***********************************************************************************
    @Override
    protected boolean onClickOther(int PbuttonId)
    {
        boolean dismiss=false;
    //********************
    	if (Dump.Y) Dump.println("DialogNFC:onClickOther btn="+Integer.toHexString(PbuttonId));//~5218I~
        switch(PbuttonId)                                          //~5218I~
        {                                                          //~5218I~
//      case ID_SETTING:                                 //~5218R~ //~5219R~//~1AbwR~
//      	callSettings();                                        //~5218R~//~1AbwR~
//          break;                                                 //~5218I~//~1AbwR~
        case ID_SETTING:                                           //~1Ad3I~
        	callSettings();                                        //~1Ad3I~
            break;                                                 //~1Ad3I~
        case ID_SETTING_NFC:                                       //~1Ad3I~
        	callSettingsNFC();                                     //~1Ad3I~
            break;                                                 //~1Ad3I~
        case ID_DISCONNECT:                                        //~5219R~
        	disconnect();                                          //~5219I~
            break;                                                 //~5219I~
        case ID_ACCEPT:                                   //~5218I~//~5219R~
        	dismiss=doAccept();                                              //~5218I~//~5219R~
            break;                                                 //~5218I~//~5219M~
        case ID_GAME:                                              //~5219R~
        	dismiss=startGame();                                   //~5219R~
            break;                                                 //~5219I~
        case ID_CONNECT:                                           //~5219I~
        	dismiss=doConnect();                                   //~5219I~
            break;                                                 //~5219I~
        }                                                          //~5218I~
        return dismiss;
    }
//    //***********************************************************************************//~1AbxR~
//    private void callSettings()                                  //~1AbxR~
//    {                                                            //~1AbxR~
////      Intent intent = new Intent(Settings.ACTION_NFCSHARING_SETTINGS);//~1A6BR~//~1AbxR~
//        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);//~1A6BI~//~1AbxR~
//        AG.activity.startActivity(intent);                       //~1AbxR~
//    }                                                            //~1AbxR~
    //***********************************************************************************//~1Ad3I~
    private void callSettings()                                    //~1Ad3I~
    {                                                              //~1Ad3I~
    	DialogNFCSelect.callSettingsWiFi();  //NFC                 //~1Ad3I~
    }                                                              //~1Ad3I~
	//***********************************************************************************//~1Ad3I~
    private void callSettingsNFC()                                 //~1Ad3I~
    {                                                              //~1Ad3I~
    	DialogNFCSelect.callSettings();    //NFC                   //~1Ad3I~
    }                                                              //~1Ad3I~
	//*************************************************************************
    private void showMsg(int Presid)
    {
        String msg=AG.resource.getString(Presid);
    	if (Dump.Y) Dump.println("DialogNFC:showDialog swPaired="+swPaired+",msg="+msg);//~1AbsI~
        tvMsg.setText(msg);
//      if (AG.activeSessionType!=0)                               //~1AbrI~//~1AbsR~
        if (swPaired)                                              //~1AbsR~
            tvMsg.textView.setBackgroundColor(Color.WHITE);        //~1AbrR~
        else                                                       //~1AbsI~
            tvMsg.textView.setBackgroundColor(bgMsgInitialColor);  //~1AbsI~
    }
	//*************************************************************************
	//*from WDANFC;macaddr receiver
	//*************************************************************************
    public static NdefMessage createNFCMsg(NfcEvent Pevent)
    {
    	if (Dump.Y) Dump.println("DialogNFC:createNFCMsg");
//      if (SdialogNFC==null)                                      //~1Af2R~
        if (SdialogNFC==null                                       //~1Af2I~
        ||  SdialogNFC.swPaired)                                   //~1Af2I~
        {                                                          //~5218I~
			new Message(Global.frame(),R.string.InfoCreateNullNFCMsg);//~5218R~
        	return null;
        }                                                          //~5218I~
        SdialogNFC.swTouch=true;	//NFC contact                              //~5218I~
 		String mimetype=SdialogNFC.mimetype;
        NdefMessage msg=null;
        try
        {
		    String macaddr="";
            String addr=SdialogNFC.deviceAddr;                     //~5218R~
            String name=SdialogNFC.deviceName;                     //~5218I~
            WifiManager mgr=(WifiManager)AG.context.getSystemService(Context.WIFI_SERVICE);//~5218I~
		    if (Dump.Y) Dump.println("DialogNFC:wifiinfo="+((WifiInfo)mgr.getConnectionInfo()).toString());//~5218I~
//            if (addr==null)                                      //~5218R~
//            {                                                    //~5218R~
//                WifiInfo info=mgr.getConnectionInfo();           //~5218R~
//                if (Dump.Y) Dump.println("DialogNFC:addr from ConnectionInfo="+info.getMacAddress());//~5218R~
//                macaddr=DATA_PREFIX+info.getMacAddress();//dis addr is for wifi but nbot wifi-direct(it can be make to by 0x20(Local) bit on)//~5218R~
//            }                                                    //~5218R~
//            else                                                 //~5218R~
//            {                                                    //~5218R~
                if (name!=null && !name.equals(""))                //~5218I~
	        		macaddr=DATA_PREFIX+addr+DATA_SEP+name;        //~5218I~
                else                                               //~5218I~
	        		macaddr=DATA_PREFIX+addr;                      //~5218R~
//            }                                                    //~5218R~
    	    String text=macaddr;
	   	    if (Dump.Y) Dump.println("DialogNFC:createNFCMessage mime="+mimetype+",text="+text);//~5218R~
//      	NdefRecord mime=NdefRecord.createMime(MIMETYPE,text.getBytes());//~API16
        	NdefRecord mime=SdialogNFC.createMimeRecord(mimetype,text.getBytes());//~API16
//      	NdefRecord app=NdefRecord.createApplicationRecord("com.example.android.beam");//AAR,start app if specified
        	NdefRecord records[]=new NdefRecord[]{mime};
			msg = new NdefMessage(records);
		}
        catch (Exception e)
        {
			Dump.println(e,"DialogNFC:createNFCMessage");
        	return null;
		}
        return msg;
    }
	//*************************************************************************
	//*from WDANFC;macaddr sender
	//*************************************************************************
    public static void sendNFCMsg(NfcEvent Pevent)
    {
    	if (Dump.Y) Dump.println("DialogNFC:sendNFCMsg");
        if (SdialogNFC==null)
        	return;
        try
        {
    		DialogNFC dlg=SdialogNFC;
        	if (dlg==null)
        		return;
    		if (dlg.thisDevice==null)	//not received broadcast msg
        		return;
        	SdialogNFC.discover(false);//to enable connect from macaddr receiver//~5218R~
	        dlg.swTouch=true;	//NFC contact                          //~5218I~
		}
        catch (Exception e)
        {
			Dump.println(e,"DialogNFC:createNFCMessage");
		}
    }
	//*************************************************************************
	//*NdefRecord.createMime is from API16
	//*************************************************************************
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
//              NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, null/*api20*/, payload);
        return mimeRecord;
    }
	//*************************************************************************
    private void getMacAddr()
    {
    	wifiActivity=new WiFiDirectActivityDialog(this);
//      deviceListFragment=new DeviceListFragment();
//      deviceDetailFragment=new DeviceDetailFragment();
//      boolean rc=enableWiFi();                                   //~1A84R~//~1A90I~//~1Ab0I~
        boolean rc=WDA.enableWiFi();                               //~1A84I~//~1A90I~//~1Ab0I~
        swWifiEnable=rc;                                           //~1A84I~//~1A90I~//~1Ab0I~
        if (!rc)
        	return;
        wifiActivity.registerReceiver();
    }
//    //*************************************************************************//~1Ab0R~
//    private boolean enableWiFi()                                 //~1Ab0R~
//    {                                                            //~1Ab0R~
//        boolean rc=true;                                         //~1Ab0R~
//    //**********************                                     //~1Ab0R~
//        WifiManager wm=(WifiManager) AG.context.getSystemService(Context.WIFI_SERVICE);//~1Ab0R~
//        if (!wm.isWifiEnabled())                                 //~1Ab0R~
//        {                                                        //~1Ab0R~
//            if (wm.setWifiEnabled(true))//success                //~1Ab0R~
//                AView.showToastLong(R.string.InfoWifiEnabled);   //~1Ab0R~
//            else                                                 //~1Ab0R~
//            {                                                    //~1Ab0R~
//                AView.showToastLong(R.string.ErrWifiEnable);     //~1Ab0R~
//                rc=false;                                        //~1Ab0R~
//            }                                                    //~1Ab0R~
//        }                                                        //~1Ab0R~
//        if (Dump.Y) Dump.println("enableWiFi rc="+rc);           //~1Ab0R~
//        swWifiEnable=rc;                                         //~1Ab0R~
//        return rc;                                               //~1Ab0R~
//    }                                                            //~1Ab0R~
	//*************************************************************************
	//*on MainThread
	//*************************************************************************
    public void updateThisDevice(WifiP2pDevice Pdevice)
    {
    	thisDevice=Pdevice;
        deviceName=DeviceDetailFragment.getDeviceName(Pdevice);
        deviceAddr=Pdevice.deviceAddress;
        deviceStatus=DeviceListFragment.getDeviceStatus(Pdevice.status);
        if (Dump.Y) Dump.println("DialogNFC:updateThisDevice:name="+deviceName+",deviceAddr="+deviceAddr+",stat="+deviceStatus);
        swPaired=(Pdevice.status==WifiP2pDevice.CONNECTED);        //~1AbsI~
        if (Dump.Y) Dump.println("DialogNFC:swPaired="+swPaired);  //~1AbsI~
        updateView();
    }
	//*************************************************************************
    private void updateView()
    {
        tvName.setText(deviceName);
//      tvAddr.setText(deviceAddr);                                //~1AbBR~
        tvAddr.setText(maskMacAddr(deviceAddr));                   //~1AbBI~
        tvStat.setText(deviceStatus);
    }
	//*************************************************************************
    private void updatePeer()
    {
        if (Dump.Y) Dump.println("DialogNFC:updatePeer thisOwner="+thisOwner);
        tvPeerName.setText(peerName);
        if (thisOwner==1)
        {
        	tvStat.setText(AG.resource.getString(R.string.MyOwnerYes));
        	tvPeerStat.setText(AG.resource.getString(R.string.MyOwnerNo));
		    tvThisIP.setText(ownerIPAddr);                         //~5218I~
        }
        else
        if (thisOwner==0)
        {
        	tvStat.setText(AG.resource.getString(R.string.MyOwnerNo));
        	tvPeerStat.setText(AG.resource.getString(R.string.MyOwnerYes));
		    tvPeerIP.setText(ownerIPAddr);                         //~5218I~
        }
        else	//not yet get updateThisOwner()
        {
            if (peerAddr==null)
		    	tvPeerStat.setText("");
            else
    	    	tvPeerStat.setText(AG.resource.getString(R.string.DeviceAvailable));
		    tvThisIP.setText("");                                  //~5218I~
		    tvPeerIP.setText("");                                  //~5218I~
        }
    }
    public void updateThisOwner(boolean Powner,String Pownerip)    //~5218M~
    {                                                              //~5218M~
        if (Dump.Y) Dump.println("DialogNFC:updatethisowner="+Powner+",owner ip="+Pownerip);//~5218M~
        ownerIPAddr=Pownerip;                                      //~5218M~
        thisOwner=Powner?1:0;                                      //~5218M~
        updatePeer();                                              //~5218M~
        setIPConnection();                                         //~5218R~
    }                                                              //~5218M~
    public void connected()
    {
        if (Dump.Y) Dump.println("DialogNFC:connected");
    }
    public void connectError()
    {
        if (Dump.Y) Dump.println("DialogNFC:connectedError");
    }
    public void disconnected()
    {
        if (Dump.Y) Dump.println("DialogNFC:disconnected");
		dismissProgressDialog(progressDialog);                     //~1AbxI~
        if (!tvPeerName.getText().equals(""))             //~5219R~
	        tvPeerStat.setText(AG.resource.getString(R.string.DeviceDisconnected));//~5219R~
        setUpperButtonsVisiblity(View.GONE);                       //~1A6KI~
        tvThisIP.setText("");                                      //~5219I~
        tvPeerIP.setText("");                                      //~5219I~
        showMsg(TEXTID_INITIAL);                                   //~5219I~
		disconnectPartner();	//server side                      //~5219R~
		swWaitingConnection=false;                                 //~1AbxI~
    }
    public void updatePeer(String Ppeername)
    {
        if (Dump.Y) Dump.println("DialogNFC:updatepeer:"+Ppeername);
        peerName=Ppeername;
        updatePeer();
    }
	//*************************************************************************
	//*from AMain
	//*return true if processed intent
	//*************************************************************************
    public static boolean onNewIntent(Intent Pintent)
    {
    	DialogNFC dlg=SdialogNFC;
        if (dlg==null)
        	return false;
    	if (dlg.thisDevice==null)	//not received broadcast msg
        	return false;
	    dlg.swTouch=true;	//NFC contact                              //~5218I~
        return dlg.processIntent(Pintent);
    }
	//*************************************************************************
    private boolean processIntent(Intent Pintent)
    {
        if (Dump.Y) Dump.println("DialogNFC:processIntent:discovered tag width intent:"+Pintent+",swPaired="+swPaired);//~1AbsR~
        try
        {
        	if (swPaired)                                          //~1AbsR~
            {                                                      //~1AbsI~
                String msg=AG.resource.getString(R.string.WarningIgnoredNewIntentConnected);//~1AbsI~
		    	int flag=Alert.BUTTON_POSITIVE;                    //~1AbsI~
				Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbsI~
            	return false;                                      //~1AbsI~
            }                                                      //~1AbsI~
            String nfcdata=getNFCData(Pintent);
//  		if (nfcdata==null || !nfcdata.startsWith(DATA_PREFIX)) //~1AbkR~
    		if (nfcdata==null)                                     //~1AbkI~
            	return false;
    		if (nfcdata.startsWith(DialogNFCBT.DATA_PREFIX))       //~1AbkI~
            {                                                      //~1AbkI~
                String msg=AG.resource.getString(R.string.WarningIgnoredNewIntentConflict);//~1AbkI~
		    	int flag=Alert.BUTTON_POSITIVE;                    //~1AbkI~
				Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbkI~
            	return false;                                      //~1AbkI~
            }                                                      //~1AbkI~
    		if (!nfcdata.startsWith(DATA_PREFIX))                  //~1AbkI~
            	return false;                                      //~1AbkI~
            peerAddr=nfcdata.substring(DATA_PREFIX.length());
            int offs=peerAddr.indexOf(DATA_SEP);                   //~5218R~
            if (offs>0)
            {
            	peerName=peerAddr.substring(offs+1);
            	peerAddr=peerAddr.substring(0,offs);
            }
            else
            	peerName="";
            receivedFromPeer();
        }
        catch (Exception e)
        {
			Dump.println(e,"DialogNFC:processIntent");
           	return false;
        }
        return true;
    }
	//*************************************************************************
    private String getNFCData(Intent Pintent)
    {
    	Parcelable[] rawmsgs=Pintent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg=(NdefMessage)rawmsgs[0];
        NdefRecord[] recs=msg.getRecords();
        NdefRecord rec0=recs[0];
        String msgout=new String(rec0.getPayload());
        if (Dump.Y) Dump.println("DialogNFC:getNFCData:"+msgout);
        return msgout;
    }
	//*************************************************************************
    private void receivedFromPeer()
    {
        if (Dump.Y) Dump.println("DialogNFC:receivedFromPeer");
        if (!discover(true))                                       //~5218R~
        	return;
        if (Dump.Y) Dump.println("DialogNFC:receivedFromPeer issued discover waiting bradcast msg");//~5218I~
    }
	//*************************************************************************//~5218I~
//  public void onPeersAvailable()                                //~5218I~//~1AbxR~
    public void onPeersAvailable(int Pctr)                         //~1AbxI~
    {                                                              //~5218I~
        if (Dump.Y) Dump.println("DialogNFC:onPeersAvailable ctr="+Pctr+",swwaitingPeerDiscovery="+swWaitingPeerDiscovery+",swPairingRequester="+swPairingRequester+",swWaitingConnection="+swWaitingConnection);//~5218R~//~1AbxR~
        if (swWaitingPeerDiscovery)                                //~5218I~
        {                                                          //~5218I~
//	    	dismissProgressDialog(progressDialog);                 //~1A77I~//~1A79R~
	        swWaitingPeerDiscovery=false;                          //~5218I~
            String macaddr=(swPairingRequester ? peerAddr : "");    //~5218I~
            int rc=wifiActivity.chkDiscovered(macaddr);           //~5218I~
            if (rc==1)  //already paired                           //~5218I~
            {                                                      //~5218I~
        		String msg=AG.resource.getString(R.string.NFCAlreadyPaired);//~5218I~
        		tvMsg.setText(msg);                                //~5218I~
		    	dismissProgressDialog(progressDialog);             //~1A79I~
            }                                                      //~5218I~
            else                                                   //~5218I~
            if (rc==-1) //not found                                //~5218I~
                AView.showToastLong(R.string.InfoNoDeviceFoundNFC);//~5218I~
            else                                                   //~5218I~
            	if (swPairingRequester)                             //~5218I~
                {                                                  //~1AbxI~
			        swWaitingConnection=true;                      //~1AbxI~
	            	makePair();                                    //~5218R~
                }                                                  //~1AbxI~
        }                                                          //~5218I~
        else                                                       //~1AbxI~
		if (swWaitingConnection)                                   //~1AbxI~
        {                                                          //~1AbxI~
            if (swPairingRequester)                                //~1AbxI~
                if (Pctr==0)                                       //~1AbxR~
                {                                                  //~1AbxR~
                    initialConnectionFailed();                     //~1AbxR~
                }                                                  //~1AbxR~
        }                                                          //~1AbxI~
    }                                                              //~5218I~
	//*******************************************************************************************************//~1AbxI~
    private void initialConnectionFailed()                         //~1AbxI~
    {                                                              //~1AbxI~
        if (Dump.Y) Dump.println("DialogNFC:initialConnectionFailed");//~1AbxI~
    	disconnected();                                            //~1AbxI~
        String msg=AG.resource.getString(R.string.ErrNFCWDInitialConnection);//~1AbxI~
        int flag=Alert.BUTTON_POSITIVE;                            //~1AbxI~
        Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbxI~
    }                                                              //~1AbxI~
	//*******************************************************************************************************
	//*on macaddr received side
    //*rc:-1:not found,1:paired,0:not paired(do connect)
	//*******************************************************************************************************
	private boolean discover(boolean Prequester)                   //~5218R~
    {
	    swPairingRequester=Prequester;      //connect requester      //~5218I~
        boolean rc=wifiActivity.discoverButton();                  //~5218R~
        if (rc)                                                    //~5218I~
        {                                                          //~5218I~
    		dismissProgressDialog(progressDialog);                 //~5218I~
       	 	progressDialog=progressDialogShow(R.string.ProgressDialogTitle,//~5218I~
            		    				AG.resource.getString(R.string.DiscoveryInitiated),//~5218I~
												true, true);       //~5218I~
        }                                                          //~5218I~
	    swWaitingPeerDiscovery=rc;                                 //~5218I~
        return rc;                                                 //~5218I~
	}
	//*******************************************************************************************************
	public void makePair()                                         //~5218R~
    {
    //********************
        if (Dump.Y) Dump.println("DialogNFC:pairTo:"+peerAddr);
        WifiP2pConfig config=new WifiP2pConfig();
        config.deviceAddress=peerAddr;
        config.wps.setup = WpsInfo.PBC;
//      String tgt=peerName+" ( "+peerAddr+" )";                   //~1AbBR~
        String tgt=peerName+" ( "+DialogNFC.maskMacAddr(peerAddr)+" )";//~1AbBI~
//  	dismissProgressDialog(progressDialog);                     //~1A77R~
    	dismissProgressDialog(progressDialog);                     //~1A79I~
        progressDialog=progressDialogShow(R.string.ProgressDialogTitle,
                				AG.resource.getString(R.string.ProgressDialogMsgConnecting)+tgt,
												true, true);
        wifiActivity.connect(config);
	}
	//*******************************************************************************************************//~5219I~
	public void disconnect()                                       //~5219I~
    {                                                              //~5219I~
    //********************                                         //~5219I~
        if (Dump.Y) Dump.println("DialogNFC:disconnect");          //~5219I~
        setUpperButtonsVisiblity(View.GONE);                       //~5219I~
        unPair();                                                  //~5219I~
        disconnectPartner();                                       //~5219I~
	}                                                              //~5219I~
	//*******************************************************************************************************//~5218I~
	public void unPair()                                           //~5218I~
    {                                                              //~5218I~
    //********************                                         //~5218I~
        if (Dump.Y) Dump.println("DialogNFC:unPair");              //~5218I~
        wifiActivity.disconnect();                                 //~5218I~
	}                                                              //~5218I~
	//*******************************************************************************************************//~5219I~
	public void disconnectPartner()                                //~5219I~
    {                                                              //~5219I~
    //********************                                         //~5219I~
        if (Dump.Y) Dump.println("DialogNFC:closeConnection");     //~5219I~
    	if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~v101R~//~5219I~
        {                                                          //~5219I~
	    	if (AG.aPartnerFrameIP!=null)                              //~v101R~//~5219I~
    	    	AG.aPartnerFrameIP.disconnect();                       //~v101M~//~5219I~
        }                                                          //~5219I~
	}                                                              //~5219I~
	//*************************************************************************
//  public static void dismissProgressDialog(ProgressDialog Pdlg)  //~5218R~
    public static void dismissProgressDialog(URunnableData Pdlg)   //~5218I~
    {
        if (Dump.Y) Dump.println("dismissProgressDialog");         //~5218I~
        URunnable.dismissDialog(Pdlg);
    }
//  public static ProgressDialog progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~5218R~
    public static URunnableData progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~5218I~
    {
        if (Dump.Y) Dump.println("ProgDialog:simpleProgressDialogShow");
        return URunnable.simpleProgressDialogShow(Ptitleid,Pmsg,Pindeterminate,Pcancelable);
    }
    //******************************************                   //~5218I~
    private void startGameConfirmation()                        //~5218I~
    {                                                              //~5218I~
        if (Dump.Y) Dump.println("DialogNFC requestGameConfirmation");//~5218I~
	    setUpperButtonsVisiblity(View.VISIBLE);                     //~5219I~
    	if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~5219R~
        {                                                          //~5219M~
			tvPeerIP.setText(AG.RemoteInetAddress);              //~5219I~
			tvThisIP.setText(AG.LocalInetAddress);             //~5219I~
			btnGame.setEnabled(true);                              //~5219R~
            btnAccept.setEnabled(false);                           //~5219R~
            btnConnect.setEnabled(false);                          //~5219I~
	        AView.showToast(R.string.NFCInSession);          //~5219I~
		    showMsg(R.string.NFCPairedInSession);                  //~5219I~
        }                                                          //~5219M~
        else                                                       //~5219M~
        {                                                          //~5219I~
        	if (thisOwner==1)                                      //~5219I~
            {                                                      //~5219I~
				btnAccept.setEnabled(true);                        //~5219R~
				btnConnect.setEnabled(false);                      //~5219R~
            }                                                      //~5219I~
            else                                                   //~5219I~
            {                                                      //~5219I~
				btnAccept.setEnabled(false);                       //~5219R~
				btnConnect.setEnabled(true);                       //~5219I~
            }                                                      //~5219I~
            btnGame.setEnabled(false);                             //~5219I~
	        AView.showToast(R.string.NFCInPaired);         //~5219I~
		    showMsg(R.string.NFCPairedNoSession);                  //~5219I~
        }                                                          //~5219M~
        AView.showToast(R.string.NFCAlreadyPaired);               //~5218I~//~5219I~
		btnDisconnect.setEnabled(true);                            //~5219R~
  	}                                                              //~5218I~
    //******************************************                   //~5218I~
    private boolean doAccept()                                     //~5219R~
    {                                                              //~5218I~
        if (Dump.Y) Dump.println("DialogNFC doAccept");            //~5219I~
        swTouch=true;                                              //~5219I~
	    setIPConnection();//dismiss                                //~5219I~
        return false;	                                           //~5219I~
    }                                                              //~5219I~
    //******************************************                   //~5219I~
    private boolean doConnect()                                    //~5219I~
    {                                                              //~5219I~
        if (Dump.Y) Dump.println("DialogNFC doConnect");           //~5219I~
        swTouch=true;                                              //~5219I~
	    setIPConnection();//dismiss                                //~5219I~
        return false;                                              //~5219I~
    }                                                              //~5219I~
    //******************************************                   //~5219I~
    //*connection active                                           //~5219I~
    //******************************************                   //~5219I~
    private boolean startGame()                                    //~5219I~
    {                                                              //~5219I~
        if (Dump.Y) Dump.println("DialogNFC startGame");           //~5219I~
    	if (AG.aPartnerFrameIP!=null && AG.aPartnerFrameIP.PT!=null//~5219I~
    	&&  AG.aPartnerFrameIP.PT.isAlive())                       //~5219I~
        {                                                          //~5219I~
            AG.aPartnerFrameIP.doAction(AG.resource.getString(R.string.Game));//~5219I~
            return true;                                           //~5219I~
        }                                                          //~5219I~
        errNoThread();                                             //~5219I~
        return false;                                              //~5219I~
    }                                                              //~5219I~
    //******************************************                   //~5219I~
    private void setIPConnection()                                 //~5219I~
    {                                                              //~5219I~
        if (Dump.Y) Dump.println("DialogNFC setIPConnection issue dismiss swTouch="+swTouch);//~5218R~
        if (!swTouch)                                              //~5218R~
        {                                                          //~5218I~
        	startGameConfirmation();                             //~5218I~
            return;                                                //~5218I~
        }                                                          //~5218I~
        swStartIPConnection=true;                                  //~5218I~
        dismiss();                                                 //~5218I~
  	}                                                              //~5218I~
    private boolean/*dispose*/ setIPConnection(boolean Pthisowner)  //~5218I~
    {                                                              //~5218I~
    	boolean rc;                                                //~5218I~
        if (Dump.Y) Dump.println("DialogNFC setIPConnection from on dismiss");//~5218R~
        if (Pthisowner)                                            //~5218I~
        	rc=startServer();                                      //~5218I~
        else                                                       //~5218I~
        	rc=connectPartner();                                   //~5218I~
		return rc; 	//dispose at boardquestion                     //~5218I~
  	}                                                              //~5218I~
    //******************************************                   //~5218I~
    private boolean/*dispose*/ startServer()                       //~5218I~
    {                                                              //~5218I~
        if (Dump.Y) Dump.println("DialogNFC startServer");         //~5218I~
    	dismissProgressDialog(progressDialog);                 //~5218I~//~5219I~
		acceptingMsg();                                            //~5218I~
//      new Server(portNo,false/*not public*/);                    //~5218I~//~1AecR~
        new Server(portNo,false/*not public*/,true/*NFC*/);        //~1AecI~
		return true; 	//dispose at boardquestion                 //~5218I~
  	}                                                              //~5218I~
    //******************************************                   //~5218I~
    private boolean/*dispose*/ connectPartner()                    //~5218I~
    {                                                              //~5218I~
        if (Dump.Y) Dump.println("DialogNFC connectPartner");      //~5218I~
    	dismissProgressDialog(progressDialog);                     //~5219R~
		connectingMsg();                                           //~5219I~
        Partner c=new Partner(peerName,ownerIPAddr,portNo,Partner.PRIVATE);//~5218I~
//      PartnerFrame cf=new PartnerFrame(Global.resourceString("Connection_to_")+c.Name,false);//~5218I~//~1A6BR~
//      PartnerFrame cf=new PartnerFrame(Global.resourceString("Connection_to_")+c.Name,false,IPConnection.NFC_CLIENT);//~1A6BI~//~1A84R~//~1A90I~//~1Ab0I~
        PartnerFrame cf=new PartnerFrame(Global.resourceString("Connection_to_")+c.Name,false,PartnerFrame.NFC_CLIENT);//~1A84I~//~1A90I~//~1Ab0I~
        new ConnectPartner(c,cf);                                  //~5218I~
        return true;                                               //~5218I~
    }                                                              //~5218I~
    //******************************************                   //~5218I~
	private int getPortNo()                                        //~5218I~
    {                                                              //~5218I~
//  	int n=Prop.getPreference(IPConnection.PKEY_SERVER_PORT,AG.DEFAULT_SERVER_PORT);//~5218I~//~1A84R~//~1A90I~//~1Ab0I~
		int n=IPConnection.getPortNo();                            //~1A84R~//~1A90I~//~1Ab0I~
		return n;                                                  //~5218I~
    }                                                              //~5218I~
    //******************************************                   //~5218I~
	private void acceptingMsg()                                    //~5218R~
    {                                                              //~5218I~
        String msg=AG.resource.getString(R.string.NFCAccepting,ownerIPAddr,portNo);//~5218R~
		waitingResponse(R.string.Title_WaitingAccept,msg);         //~5218I~
    }                                                              //~5218I~
    //******************************************                   //~5219I~
	private void connectingMsg()                                   //~5219I~
    {                                                              //~5219I~
        String msg=AG.resource.getString(R.string.NFCConnecting,peerName,ownerIPAddr,portNo);//~5219R~
		waitingResponse(R.string.Title_WaitingConnect,msg);        //~5219I~
    }                                                              //~5219I~
    //******************************************                   //~5218I~
	private void waitingResponse(int Ptitleresid,String Pmsg)      //~5218I~
    {                                                              //~5218I~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,false/*cancel CB*/,Ptitleresid,Pmsg,true/*cancelable*/);//~5218I~
    }                                                              //~5218I~
    //******************************************                   //~5218I~
    //*reason:0:cancel,1:dismiss                                   //~5218I~
    //******************************************                   //~5218I~
    @Override                                                      //~5218I~
	public void onCancelProgDlg(int Preason)                       //~5218I~
    {                                                              //~5218I~
    	if (Dump.Y) Dump.println("DialogNFC:onCancelProgDlgI reason="+Preason);//~5218I~
    }                                                              //~5218I~
    //******************************************                   //~5219I~
	private void errNoThread()                                     //~5219I~
    {                                                              //~5219I~
    	AView.showToast(R.string.ErrNoThread);                     //~5219I~
    }                                                              //~5219I~
    //******************************************                   //~1A78I~
	private void chkNFCenabled()                                   //~1A78I~
    {                                                              //~1A78I~
        NfcManager mgr=(NfcManager)AG.context.getSystemService(Context.NFC_SERVICE);//~1A78R~
        mNfcAdapter=mgr.getDefaultAdapter();                       //~1A78I~
        if (mNfcAdapter==null)                                     //~1A78I~
	    	AView.showToast(R.string.ErrNoNFCAttachment);          //~1A78I~
        else                                                       //~1A78I~
        if (!mNfcAdapter.isEnabled())                              //~1A78I~
	    	AView.showToast(R.string.ErrNFCDisabled);            //~1A78I~
        else                                                       //~1A78I~
        	isNFCenabled=true;                                     //~1A78I~
    }                                                              //~1A78I~
    //******************************************                   //~1AbBI~
    public static String maskMacAddr(String Pmacaddr)              //~1AbBI~
    {                                                              //~1AbBI~
    	if (Pmacaddr==null)                                        //~1AbBI~
        	return "";                                             //~1AbBI~
        if (Pmacaddr.length()!=17)                                 //~1AbBI~
        	return Pmacaddr;                                       //~1AbBI~
    	return Pmacaddr.substring(0,6)+"...";                      //~1AbBI~
    }                                                              //~1AbBI~
}
