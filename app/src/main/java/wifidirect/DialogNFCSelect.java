//*CID://+1AgcR~:                           update#=  265;         //+1AgcR~
//*************************************************************************
//1Agc 2016/10/11 2016/10/11 avoid to show nfc dialog when nfc is not attached, and set visibility=gone to nfc button//+1AgcI~
//1Ad3 2015/07/19 Bypass NFCSelect, by NFS-WD and NFC-BT button directly.//~1Ad3I~
//1Ac5 2015/07/09 NFCBT:confirmation dialog is not show and fails to pairig//~1Ac5I~
//                (LocalBluetoothPreference:"Found no reason to show dialog",requires discovaring status in the 60 sec before)//~1Ac5I~
//                Issue waring whne NFCBT-Secure                   //~1Ac5I~
//1AbY 2015/07/05 BT:prepare dislognfcselect_mdpi.xml              //~1AbYI~
//1AbW 2015/07/05 BT:chk Bluetooth supported foy system bluetooth  //~1AbWI~
//1AbC 2015/06/26 add WiFi setting to Dialog select to chk Wi-Fi setting//~1AbCI~
//1Abq 2015/06/16 NFCBT:Secure connection failes if remote is not on available list. Prepare Bluetooth Setting button.//~1AbqI~
//1Abg 2015/06/15 NFCBT:transfer to NFCBT or NFCWD if active session exist//~1AbgI~
//1Aba 2015/06/14 (Bug)"Cancel on NFCSelect dialog" display NFC beam dialog//~1AbaI~
//1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//1Ab7 2015/05/03 NFC Bluetooth handover v2                        //~1Ab7I~
//1Ab1 2015/05/03 NFC Bluetooth handover
//*************************************************************************
package wifidirect;

//import java.nio.charset.Charset;                                 //~1Ad3R~
//import java.util.Locale;                                         //~1Ad3R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.MainFrame;
import jagoclient.dialogs.HelpDialog;
//import jagoclient.dialogs.Message;                               //~1Ad3R~
import android.annotation.TargetApi;
//import android.app.PendingIntent;                                //~1Ad3R~
import android.content.Context;
import android.content.Intent;
//import android.content.IntentFilter;                             //~1Ad3R~
//import android.content.IntentFilter.MalformedMimeTypeException;  //~1Ad3R~
import android.nfc.NdefMessage;
//import android.nfc.NdefRecord;                                   //~1Ad3R~
import android.nfc.NfcAdapter;
//import android.nfc.NfcAdapter.CreateNdefMessageCallback;         //~1Ad3R~
//import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;        //~1Ad3R~
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
//import android.os.Parcelable;                                    //~1Ad3R~
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.provider.Settings;                                  //~1Ab7I~

import com.Asgts.ABT;
import com.Asgts.AG;
import com.Asgts.AView;
import com.Asgts.Alert;
import com.Asgts.AlertI;
import com.Asgts.Prop;
import com.Asgts.R;
import com.Asgts.awt.UButton;
//import com.Asgts.awt.UTextView;                                  //~1Ad3R~
import com.axe.AxeDialog;

@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14
public class DialogNFCSelect extends AxeDialog
{
	private static final int LAYOUTID=R.layout.dialognfcselect;
	private static final int LAYOUTID_MDPI=R.layout.dialognfcselect_mdpi;//~1AbYI~
    private static final int TITLEID=R.string.DialogTitle_NFCSelect;
    private static final int HELPTITLEID=R.string.HelpTitle_NFCSelect;//~1Ab1I~
    private static final int RB_SELECT_IP=R.id.NFCHandoverIP;      //~1Ab1I~
    private static final int RB_SELECT_BT_SECURE=R.id.NFCHandoverBTSecure;      //~1Ab1I~//~1Ab7R~
    private static final int RB_SELECT_BT_INSECURE=R.id.NFCHandoverBTInSecure;//~1Ab7I~
    public  static final int NFCTYPE_NOT_SELECTED=0;               //~1Ab7R~
    public  static final int NFCTYPE_BT_SECURE=1;                  //~1Ab7I~
    public  static final int NFCTYPE_BT_INSECURE=2;                //~1Ab7R~
    public  static final int NFCTYPE_IP=3;                         //~1Ab7R~
    private static final int RB_SELECT_DEFAULT=RB_SELECT_BT_SECURE;//~1Ab7I~
    private static final int NFCTYPE_DEFAULT=NFCTYPE_BT_SECURE;    //~1Ab7I~
    private static final String	PKEY_NFC_SELECT="SelectNFC";       //~1Ab1I~
    private static final int ID_SETTING=R.id.NFCSettings;          //~1Ab7I~
    private static final int ID_SETTING_WIFI=R.id.WiFiSettings;    //~1AbCI~
    private static final int ID_SETTING_BT=R.id.BluetoothSettings; //~1AbqI~
//  private static final String  DATA_PREFIX="NFCSelect=";         //~1Ab7I~//~1Ad3R~
    private MainFrame MF;
    private RadioGroup rgSelectNFC;
//  private UTextView tvMsg;                                       //~1Ab7R~//~1Ad3R~
    private UButton btnSettings;                                   //~1Ab7R~
    private UButton btnSettingsBT;                                 //~1AbqI~
    private UButton btnSettingsWiFi;                               //~1AbCI~
//  private String mimetype;                                       //~1Ab7I~//~1Ad3R~
//  private boolean isNFCenabled;                                  //~1Ab7I~//~1Ad3R~
	public static DialogNFCSelect SdialogNFC;                      //~1Ab7R~
    private static NfcAdapter mNfcAdapter;                                //~1Ab7I~
//  private static PendingIntent mPendingIntent;                   //~1Ab7I~//~1Ad3R~
//  private static IntentFilter[] mFilters;                        //~1Ab7I~//~1Ad3R~
    private boolean swOK;                                          //~1Ab8I~
    private int selectedType;                                      //~1Ab7I~
//  private String additionalData;                                 //~1Ab7I~//~1Ad3R~
    private static boolean swNoNFC;                                //+1AgcI~
	//***********************************************************************************
	public static DialogNFCSelect showDialog(MainFrame Pmf)
    {
        if (transferToCurrentActive(Pmf))                          //~1AbgI~
            return null;                                                //~1AbgI~
    	DialogNFCSelect dlg=new DialogNFCSelect();                       //~1Ab7R~
        String title=AG.resource.getString(TITLEID);
        dlg.MF=Pmf;
		dlg.showDialog(title);
        SdialogNFC=dlg;                                            //~1Ab7R~
        return dlg;
    }
	//***********************************************************************************//~1Ad3I~
	public static DialogNFCSelect showDialogNFCWD(MainFrame Pmf)   //~1Ad3I~
    {                                                              //~1Ad3I~
    	if (Dump.Y) Dump.println("DialogNFCSelect showDialogNFCWD");//~1Ad3I~
        if (transferToCurrentActive(Pmf))                          //~1Ad3I~
            return null;                                           //~1Ad3I~
    	DialogNFCSelect dlg=new DialogNFCSelect();                 //~1Ad3I~
        dlg.MF=Pmf;                                                //~1Ad3I~
        dlg.selectedType=NFCTYPE_IP;                               //~1Ad3I~
        SdialogNFC=dlg;                                            //~1Ad3I~
      if (!swNoNFC)                                                //+1AgcI~
        dlg.MF.selectedNFCHandover(dlg.selectedType);              //~1Ad3I~
        return dlg;                                                //~1Ad3I~
    }                                                              //~1Ad3I~
	//***********************************************************************************//~1Ad3I~
	public static DialogNFCSelect showDialogNFCBT(MainFrame Pmf)   //~1Ad3I~
    {                                                              //~1Ad3I~
    	if (Dump.Y) Dump.println("DialogNFCSelect showDialogNFCBT");//~1Ad3I~
        if (transferToCurrentActive(Pmf))                          //~1Ad3I~
            return null;                                           //~1Ad3I~
    	DialogNFCSelect dlg=new DialogNFCSelect();                 //~1Ad3I~
        dlg.MF=Pmf;                                                //~1Ad3I~
//      dlg.selectedType=NFCTYPE_BT_INSECURE;                      //~1Ad3I~
        dlg.selectedType=DialogNFCBT.getSecureOptionFromProf() ? NFCTYPE_BT_SECURE : NFCTYPE_BT_INSECURE;//~1Ad3I~
        SdialogNFC=dlg;                                            //~1Ad3I~
      if (!swNoNFC)                                                //+1AgcI~
        dlg.MF.selectedNFCHandover(dlg.selectedType);              //~1Ad3I~
        return dlg;                                                //~1Ad3I~
    }                                                              //~1Ad3I~
	//***********************************************************************************
    public DialogNFCSelect()
    {
//  	super(LAYOUTID);                                           //~1AbYR~
    	super((AG.layoutMdpi/*mdpi and height or width <=320*/ ? LAYOUTID_MDPI : LAYOUTID));//~1AbYI~
		init();                                                    //~1Ab7I~
    }
    private void init()                                            //~1Ab7I~
    {                                                              //~1Ab7I~
        chkNFCenabled();                                           //~1Ab7I~
//  	mimetype=("application/"+AG.pkgName).toLowerCase(Locale.ENGLISH);//~1Ab7I~//~1Ad3R~
//      registerReceiver();                                        //~1Ab7I~
    }                                                              //~1Ab7I~
	//***********************************************************************************
    @Override
	protected void setupDialogExtend(ViewGroup PlayoutView)
    {
        rgSelectNFC=(RadioGroup)PlayoutView.findViewById(R.id.NFCHandoverRG);//~1Ab7R~
        btnSettings=new UButton(PlayoutView,ID_SETTING);           //~1Ab7R~
        setButtonListener(btnSettings.androidButton);              //~1Ab7I~
        btnSettingsBT=new UButton(PlayoutView,ID_SETTING_BT);      //~1AbqM~
        setButtonListener(btnSettingsBT.androidButton);            //~1AbqI~
        btnSettingsWiFi=new UButton(PlayoutView,ID_SETTING_WIFI);  //~1AbCI~
        setButtonListener(btnSettingsWiFi.androidButton);          //~1AbCI~
    	int type=Prop.getPreference(PKEY_NFC_SELECT,NFCTYPE_DEFAULT);       //~1Ab1I~//~1Ab7R~
        int btnid=RB_SELECT_DEFAULT;                               //~1Ab7R~
        switch (type)                                              //~1Ab7I~
        {                                                          //~1Ab7I~
        case NFCTYPE_BT_SECURE:                                    //~1Ab7I~
        	btnid=RB_SELECT_BT_SECURE;                             //~1Ab7I~
        	break;                                                 //~1Ab7I~
        case NFCTYPE_BT_INSECURE:                                  //~1Ab7I~
        	btnid=RB_SELECT_BT_INSECURE;                           //~1Ab7I~
        	break;                                                 //~1Ab7I~
        case NFCTYPE_IP:                                           //~1Ab7I~
        	btnid=RB_SELECT_IP;                                    //~1Ab7I~
        	break;                                                 //~1Ab7I~
        }                                                          //~1Ab7I~
	    rgSelectNFC.check(btnid);                  //~v1B6I~//~1Ab1I~//~1Ab7R~
        chkSelection();                                            //~1Ab7I~
    }
	//***********************************************************************************//~1Ab7I~
    protected boolean onClickClose()                               //~1Ab7R~
    {
    //*****************************
    	chkSelection();                                            //~1Ab7I~
    	if (!chkDiscoverable(selectedType==NFCTYPE_BT_SECURE))     //~1Ac5I~
        	return false;	//not dismiss                          //~1Ac5I~
        swOK=true;                                                 //~1Ab8I~
        return true;  //not dismiss                                //~1Ab7R~
    }
	//***********************************************************************************//~1Ab7I~
    private void chkSelection()                                    //~1Ab7I~
    {                                                              //~1Ab7I~
    //*****************************                                //~1Ab7I~
        int btnid=rgSelectNFC.getCheckedRadioButtonId();           //~1Ab7I~
        int type=NFCTYPE_DEFAULT;                                  //~1Ab7I~
        switch (btnid)                                             //~1Ab7I~
        {                                                          //~1Ab7I~
        case RB_SELECT_BT_SECURE:                                  //~1Ab7I~
        	type=NFCTYPE_BT_SECURE;                                //~1Ab7I~
        	break;                                                 //~1Ab7I~
        case RB_SELECT_BT_INSECURE:                                //~1Ab7I~
        	type=NFCTYPE_BT_INSECURE;                              //~1Ab7I~
        	break;                                                 //~1Ab7I~
        case RB_SELECT_IP:                                         //~1Ab7I~
        	type=NFCTYPE_IP;                                       //~1Ab7I~
        	break;                                                 //~1Ab7I~
        }                                                          //~1Ab7I~
    	if (Dump.Y) Dump.println("DialogNFCSelect chkSelection type="+type+",btnid="+Integer.toHexString(btnid));//~1Ab7R~
        if (type!=selectedType)                                    //~1Ab7I~
	    	Prop.putPreference(PKEY_NFC_SELECT,type);              //~1Ab7I~
        selectedType=type;                                         //~1Ab7I~
    }                                                              //~1Ab7I~
	//**********************************
	@Override
    protected boolean onClickHelp()
    {
  		new HelpDialog(Global.frame(),HELPTITLEID,"nfcselect");   //~1Ab1R~
        return false;	//not dismiss
    }
	//***********************************************************************************//~1Ab7I~
    @Override                                                      //~1Ab7I~
    protected boolean onClickOther(int PbuttonId)                  //~1Ab7I~
    {                                                              //~1Ab7I~
        boolean dismiss=false;                                     //~1Ab7I~
    //********************                                         //~1Ab7I~
    	if (Dump.Y) Dump.println("DialogNFC:onClickOther btn="+Integer.toHexString(PbuttonId));//~1Ab7I~
        switch(PbuttonId)                                          //~1Ab7I~
        {                                                          //~1Ab7I~
        case ID_SETTING:                                           //~1Ab7I~
        	callSettings();                                        //~1Ab7I~
            break;                                                 //~1Ab7I~
        case ID_SETTING_BT:                                        //~1AbqI~
        	callSettingsBT();                                      //~1AbqI~
            break;                                                 //~1AbqI~
        case ID_SETTING_WIFI:                                      //~1AbCI~
        	callSettingsWiFi();                                    //~1AbCI~
            break;                                                 //~1AbCI~
        }                                                          //~1Ab7I~
        return dismiss;                                            //~1Ab7I~
    }                                                              //~1Ab7I~
	//***********************************************************************************//~1Ab1I~
    @Override                                                      //~1Ab1I~
    protected void onDismiss()                                     //~1Ab1I~
    {                                                              //~1Ab1I~
    	if (Dump.Y) Dump.println("DialogNFCSelect:onDismiss swOK="+swOK+",selectedType="+selectedType);         //~1Ab1I~//~1Ab7R~//~1Ab8R~
        if (!swOK)                                                 //~1AbaI~
        	return;                                                //~1AbaI~
        MF.selectedNFCHandover(selectedType);//~1Ab7R~             //~1Ab8R~
        SdialogNFC=null;                                           //~1Ab7M~
    	return;                                                    //~1Ab1I~
    }                                                              //~1Ab1I~
    //******************************************                   //~1Ab7I~
	private void chkNFCenabled()                                   //~1Ab7I~
    {                                                              //~1Ab7I~
        NfcManager mgr=(NfcManager)AG.context.getSystemService(Context.NFC_SERVICE);//~1Ab7I~
        mNfcAdapter=mgr.getDefaultAdapter();                       //~1Ab7I~
        if (mNfcAdapter==null)                                     //~1Ab7I~
        {                                                          //+1AgcI~
	    	AView.showToast(R.string.ErrNoNFCAttachment);          //~1Ab7I~
            swNoNFC=true;                                          //+1AgcI~
        }                                                          //+1AgcI~
        else                                                       //~1Ab7I~
        if (!mNfcAdapter.isEnabled())                              //~1Ab7I~
	    	AView.showToast(R.string.ErrNFCDisabled);              //~1Ab7I~
//      else                                                       //~1Ab7I~//~1Ad3R~
//      	isNFCenabled=true;                                     //~1Ab7I~//~1Ad3R~
    }                                                              //~1Ab7I~
	//***********************************************************************************//~1Ab7I~
//  private void callSettings()                                    //~1Ab7I~//~1Ad3R~
    public static void callSettings()   //NFC                      //~1Ad3I~
    {                                                              //~1Ab7I~
   	    if (Dump.Y) Dump.println("DialogNFCSelect:callSettings");  //~1Ab7I~
    	Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);//~1Ab7I~
        AG.activity.startActivity(intent);                         //~1Ab7I~
    }                                                              //~1Ab7I~
	//***********************************************************************************//~1AbqI~
//  private void callSettingsBT()                                  //~1AbqI~//~1Ad3R~
    public static void callSettingsBT()  //Bluetooth               //~1Ad3I~
    {                                                              //~1AbqI~
   	    if (Dump.Y) Dump.println("DialogNFCSelect:callSettingsBT");//~1AbqI~
        if (AG.aBT.mBTC==null)                                     //~1AbWI~
        {                                                          //~1AbWI~
			ABT.BTNotAvailable();                                      //~1AbRI~//~1AbWI~
        	return;                                                //~1AbWI~
        }                                                          //~1AbWI~
    	Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);//~1AbqR~
        AG.activity.startActivity(intent);                         //~1AbqI~
    }                                                              //~1AbqI~
	//***********************************************************************************//~1AbCI~
//  private void callSettingsWiFi()                                //~1AbCI~//~1Ad3R~
    public static void callSettingsWiFi()   //WiFi                 //~1Ad3I~
    {                                                              //~1AbCI~
   	    if (Dump.Y) Dump.println("DialogNFCSelect:callSettingsWiFi");//~1AbCI~
    	Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS); //~1AbCI~
        AG.activity.startActivity(intent);                         //~1AbCI~
    }                                                              //~1AbCI~
	//*************************************************************************//~1Ab8I~
	//*from WDANFC:onResume(createNdefMessage) for NFCBT           //~1Ad3I~
	//*************************************************************************//~1Ab8I~
    public static NdefMessage createNFCMsg(NfcEvent Pevent)        //~1Ab8I~
    {                                                              //~1Ab8I~
    	if (Dump.Y) Dump.println("DialogNFCSelect:createNFCMsg");  //~1Ab8I~
        if (DialogNFC.SdialogNFC!=null)                            //~1Ab8I~
        	return DialogNFC.createNFCMsg(Pevent);                 //~1Ab8I~
        return DialogNFCBT.createNFCMsg(Pevent);                   //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*from WDANFC:onResume(onNdefPushComplete) for NFCBT          //~1Ad3I~
	//*************************************************************************//~1Ab8I~
    public static void sendNFCMsg(NfcEvent Pevent)                 //~1Ab8I~
    {                                                              //~1Ab8I~
    	if (Dump.Y) Dump.println("DialogNFCSelect:sendNFCMsg");    //~1Ab8I~
        if (DialogNFC.SdialogNFC!=null)                            //~1Ab8I~
			DialogNFC.sendNFCMsg(Pevent);                  //~1Ab8I~
        else                                                       //~1Ab8I~
			DialogNFCBT.sendNFCMsg(Pevent);                //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1Ab8I~
	//*from AMain                                                  //~1Ab8I~
	//*************************************************************************//~1Ab8I~
    public static boolean onNewIntent(Intent intent)               //~1Ab8R~
    {                                                              //~1Ab8I~
        if (DialogNFC.onNewIntent(intent))                         //~1Ab8I~
        	return true;                                            //~1Ab8I~
        return DialogNFCBT.onNewIntent(intent);                    //~1Ab8I~
    }                                                              //~1Ab8I~
	//*************************************************************************//~1AbgI~
	//*transfer to NFCWD or NFCBT if active session exist          //~1AbgI~
	//*************************************************************************//~1AbgI~
    public static boolean transferToCurrentActive(MainFrame Pmf)   //~1AbgI~
    {                                                              //~1AbgI~
        boolean rc=true; //transfered                              //~1AbgI~
    	int active=AG.activeSessionType;                           //~1AbgI~
                                                                   //~1AbgI~
        switch(active)                                             //~1AbgI~
        {                                                          //~1AbgI~
        case AG.AST_IP:                                            //~1AbgI~
		    MainFrame.isAliveOtherSession(0/*not AST_IP*/,false/*not duperr*/);	//alert dialog//~1AbgI~
            break;                                                 //~1AbgI~
        case AG.AST_WD:                                            //~1AbgI~
	        Pmf.selectedNFCHandover(NFCTYPE_IP);	//transfer     //~1AbgI~
            break;                                                 //~1AbgI~
        case AG.AST_BT:                                            //~1AbgI~
	        int selected=AG.swSecureNFCBT ? NFCTYPE_BT_SECURE : NFCTYPE_BT_INSECURE;	//transfer//~1AbgR~
	        Pmf.selectedNFCHandover(selected);	//transfer         //~1AbgI~
            break;                                                 //~1AbgI~
        default:                                                   //~1AbgI~
            rc=false;   //show DialogNFCSelect                     //~1AbgI~
        }                                                          //~1AbgI~
        return rc;                                                 //~1AbgI~
    }                                                              //~1AbgI~
	//*************************************************************************//~1Ac5I~
    private boolean chkDiscoverable(boolean Psecure)               //~1Ac5M~
	{                                                              //~1Ac5M~
    	boolean rc=true;                                           //~1Ac5M~
        if (Dump.Y) Dump.println("DialogNFCSelect.chkDiscoverable secure="+Psecure);//~1Ac5I~
        if (Psecure)                                               //~1Ac5M~
        {                                                          //~1Ac5M~
        	if (!ABT.BTisDiscoverable())                           //~1Ac5M~
            {                                                      //~1Ac5M~
			    showNotDiscoverableAlert();                        //~1Ac5I~
                rc=false;  //DialogNFCBT from alert Action         //~1Ac5I~
            }                                                      //~1Ac5M~
        }                                                          //~1Ac5M~
        return rc;                                                 //~1Ac5M~
    }                                                              //~1Ac5M~
	//*************************************************************************//~1Ac5I~
    private void showNotDiscoverableAlert()                        //~1Ac5I~
    {                                                              //~1Ac5I~
        AlertI ai=new AlertI()                                     //~1Ac5I~
                            {                                      //~1Ac5I~
                                @Override                          //~1Ac5I~
                                public int alertButtonAction(int Pbuttonid,int Pitempos)//~1Ac5I~
                                {                                  //~1Ac5I~
                                    if (Dump.Y) Dump.println("DialogNFCSelect buttonid="+Integer.toHexString(Pbuttonid));//~1Ac5I~
                                    if (Pbuttonid==Alert.BUTTON_POSITIVE)//~1Ac5I~
                                    {                              //~1Ac5I~
                                    	swOK=true;                 //~1Ac5I~
                                        dismiss();	//DialogNFCBT from OnDismiss//~1Ac5I~
                                    }                              //~1Ac5I~
                                    return 1;                      //~1Ac5I~
                                }                                  //~1Ac5I~
                            };                                     //~1Ac5I~
        Alert.simpleAlertDialog(ai,TITLEID,R.string.WarningNFCBTNotDiscoverable,//~1Ac5I~
                            Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE);//~1Ac5I~
    }                                                              //~1Ac5I~
}
