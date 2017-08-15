//*CID://+1AbDR~:                             update#=  115;       //+1AbDR~
//*************************************************************************//~1A65I~
//1AbD 2015/06/27 (Bug)DialogNFC.sendNFCMsg was called twice.      //+1AbDI~
//1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8R~
//1A81 2015/02/24 ANFC is not used now                             //~1A81I~
//1A6G 2015/02/22 (BUG)on emulater mAdapter is null(NPE)           //~1A6GI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6aI~
//1A6j 2015/02/14 set dummy NFC function for main Activity to avoid current activity re-created.//~1A6jI~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6aI~
//*************************************************************************//~1A65I~
package wifidirect;                                                //~1A6aR~

import wifidirect.DeviceListFragment;
import wifidirect.WDA;
import jagoclient.Dump;
import android.annotation.TargetApi;                               //~1A65I~
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
                                                                   //~1A65I~
import com.Asgts.AG;                                                //~1A65I~//~1A6GR~
//import com.Asgts.ANFC;                                             //~1A6GR~//~1A81R~

@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14                           //~1A65R~
public class WDANFC                                                //~1A6aR~
{                                                                  //~1A65I~
	public static final String NFC_WIFIDATA="MacAddress";          //~@@@@I~//~1A6aI~                                                                   //~1A6aI~
	public static final int CONNECTED=1;                           //~1A6aI~
	public static final int CONNECTERR=0;                          //~1A6aI~                                     //~1A6aI~
    private String myDeviceAddr;                                   //~1A6aI~
    private DeviceListFragment deviceListFragment;                         //~1A6aI~
    private static NfcAdapter mAdapter;                            //~1A6jR~
    private static PendingIntent mPendingIntent;                          //~1A6jI~
    private static IntentFilter[] mFilters;                        //~1A6jI~

	//***********************************************************************************//~1A65I~
    public WDANFC()                                  //~1A65R~     //~1A6aR~
    {                                                              //~1A65I~
//        init_ICE_CREAM_SANDWICH();                             //~1A65I~//~1A6aR~//~1A81R~
//        AG.aWDANFC=this;                                            //~1A6aI~//~1A81R~
//        deviceListFragment=WDA.getDeviceListFragment();            //~1A6aI~//~1A81R~
//        myDeviceAddr=deviceListFragment.thisDeviceAddr;             //~1A6aI~//~1A81R~
    }                                                              //~1A65I~
//    //***********************************************************************************//~1A65I~//~1A81R~
//    private void init_ICE_CREAM_SANDWICH()                         //~1A65I~//~1A81R~
//    {                                                              //~1A65I~//~1A81R~
//        if (Dump.Y) Dump.println("WDANFC:init_ICE_CREAM_SANDWITCH");//~1A6aI~//~1A81R~
//        try                                                        //~1A6aI~//~1A81R~
//        {                                                          //~1A6aI~//~1A81R~
//            startNFCA();                                            //~1A6aI~//~1A81R~
//        }                                                          //~1A6aI~//~1A81R~
//        catch(Exception e)                                         //~1A6aI~//~1A81R~
//        {                                                          //~1A6aI~//~1A81R~
//            Dump.println(e,"WDANFC:init_ICE_CREAM_SANDWITCH");     //~1A6aI~//~1A81R~
//        }                                                          //~1A6aI~//~1A81R~
//    }                                                              //~1A6aI~//~1A81R~
//    //***********************************************************************************//~1A6aI~//~1A81R~
//    private void startNFCA()                                       //~1A6aI~//~1A81R~
//    {                                                              //~1A6aI~//~1A81R~
//        if (Dump.Y) Dump.println("WDANFC:init startActivityForResult");//~@@@@R~//~1A6aI~//~1A81R~
//        int reqcode=AG.ACTIVITY_REQUEST_NFCBEAM;                               //~@@@@R~//~1A6aR~//~1A81R~
//        try                                                        //~@@@@I~//~1A6aI~//~1A81R~
//        {                                                          //~@@@@I~//~1A6aI~//~1A81R~
////            Intent intent=new Intent();                            //~1A6aR~//~1A81R~
////            intent.setClassName(PKGNAME,CLASSNAME_BEAM);         //~1A6aR~//~1A81R~
////            intent.setClassName((String)null,CLASSNAME_BEAM);              //~1A6aR~//~1A81R~
//            Intent intent=new Intent(AG.context,ANFC.class);       //~1A6aR~//~1A81R~
//            AG.activity.startActivityForResult(intent,reqcode);                //~@@@@I~//~1A6aI~//~1A81R~
//        }                                                          //~@@@@I~//~1A6aI~//~1A81R~
//        catch(ActivityNotFoundException e)                         //~@@@@I~//~1A6aI~//~1A81R~
//        {                                                          //~@@@@I~//~1A6aI~//~1A81R~
//            Dump.println(e,"WDANFC:startNFCA");             //~@@@@I~//~1A6aI~//~1A81R~
//        }                                                          //~@@@@I~//~1A6aI~//~1A81R~
//    }                                                              //~1A65I~//~1A81R~
//    //***********************************************************************************//~1A6aI~//~1A81R~
//    //*from AMain.onActivityResult()                               //~1A6aI~//~1A81R~
//    //***********************************************************************************//~1A6aI~//~1A81R~
//    public void activityResult(int Preqcode,int Presultcode,Intent Pintent)//~@@@@I~//~1A6aR~//~1A81R~
//    {                                                              //~@@@@I~//~1A6aI~//~1A81R~
//        if (Dump.Y) Dump.println("WDANFC:onActivityResult req="+Preqcode+",result="+Presultcode);//~@@@@R~//~1A6aR~//~1A81R~
//        switch(Preqcode)                                           //~@@@@I~//~1A6aI~//~1A81R~
//        {                                                          //~@@@@I~//~1A6aI~//~1A81R~
//        case AG.ACTIVITY_REQUEST_NFCBEAM:                                      //~@@@@I~//~1A6aR~//~1A81R~
//            if (Presultcode==Activity.RESULT_OK)                   //~@@@@I~//~1A6aI~//~1A81R~
//            {                                                      //~@@@@I~//~1A6aI~//~1A81R~
//                String macaddr=Pintent.getStringExtra(NFC_WIFIDATA);//~@@@@R~//~1A6aI~//~1A81R~
//                if (Dump.Y) Dump.println("WDANFC:onActivityResult data="+macaddr);//~@@@@I~//~1A6aR~//~1A81R~
//                macaddr=ANFC.parseNFCData(macaddr);                 //~1A6aI~//~1A81R~
//                if (macaddr!=null)                                 //~1A6aI~//~1A81R~
//                {                                                  //~1A6aI~//~1A81R~
//                    if (macaddr.length()<=1)    //send completed   //~1A6aI~//~1A81R~
//                        discover();                                //~1A6aR~//~1A81R~
//                    else                                           //~1A6aI~//~1A81R~
//                        connect(macaddr);                                  //~@@@@I~//~1A6aR~//~1A81R~
//                }                                                  //~1A6aI~//~1A81R~
//            }                                                      //~@@@@I~//~1A6aI~//~1A81R~
//            AG.aWDANFC=null;                                        //~1A6aI~//~1A81R~
//            break;                                                 //~@@@@I~//~1A6aI~//~1A81R~
//        }                                                          //~@@@@I~//~1A6aI~//~1A81R~
//    }                                                              //~@@@@I~//~1A6aI~//~1A81R~
	//***********************************************************************************//~1A6aI~
    private void discover()                                        //~1A6aI~
    {                                                              //~1A6aI~
		WDA.SWDA.discover();                                       //~1A6aI~
    }                                                              //~1A6aI~
	//***********************************************************************************//~1A6aI~
    private void connect(String Pmacaddr)                          //~1A6aR~
    {                                                              //~1A6aI~
    //********************                                         //~1A6aI~
		if (Dump.Y) Dump.println("WDANFC:connect tgt="+Pmacaddr);  //~1A6aI~
        if (WDA.SWDA.discover(this,Pmacaddr)==0)	//already discovered and not paired//~1A6aR~
	        WDA.SWDA.connect(Pmacaddr);                            //~1A6aR~
		if (Dump.Y) Dump.println("WDANFC:connect return");         //~1A6aI~
    }                                                              //~1A6aI~
	//***********************************************************************************//~1A6aI~
    public void discovered(String Pmacaddr,int Pstat)              //~1A6aR~
    {                                                              //~1A6aI~
    //********************                                         //~1A6aI~
		if (Dump.Y) Dump.println("WDANFC:discovered tgt="+Pmacaddr+",discovered="+Pstat);//~1A6aR~
        if (Pstat==0) //found(not paired)                          //~1A6aI~
		    WDA.SWDA.connect(Pmacaddr);                            //~1A6aR~
		if (Dump.Y) Dump.println("WDANFC:connect return");         //~1A6aI~
    }                                                              //~1A6aI~
//    //***********************************************************************************//~1A6aI~//~1A81R~
//    public void connected(int Presult)                            //~1A6aI~//~1A81R~
//    {                                                              //~1A6aI~//~1A81R~
//    //********************                                         //~1A6aI~//~1A81R~
//        if (Dump.Y) Dump.println("WDANFC:connected rc="+Presult);  //~1A6aI~//~1A81R~
//        AG.aANFC.finishActivity(Presult);                        //~1A6aI~//~1A81R~
//    }                                                              //~1A6aI~//~1A81R~
//    //***********************************************************************************//~1A6aI~//~1A81R~
//    public static String getMyMacAddr()                            //~1A6aI~//~1A81R~
//    {                                                              //~1A6aI~//~1A81R~
//    //********************                                         //~1A6aI~//~1A81R~
//        String addr=AG.aWDANFC.myDeviceAddr;                       //~1A6aI~//~1A81R~
//        if (Dump.Y) Dump.println("WDANFC:getMyMacAddr="+addr);     //~1A6aI~//~1A81R~
//        return addr;                                               //~1A6aI~//~1A81R~
//    }                                                              //~1A6aI~//~1A81R~
	//*************************************************************************//~1A6jI~
    public static void onResume() {                                //~1A6jR~
   	    if (Dump.Y) Dump.println("WDANFC:AMain:onResume");         //~1A6jR~
        if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android4        //~1A6jI~
        	return;                                                //~1A6jI~
        try                                                        //~1A6jI~
        {                                                          //~1A6jI~
        	if (mAdapter==null)                                     //~1A6jI~
            {                                                      //~1A6jI~
            	initAdapter();                                     //~1A6jI~
            }                                                      //~1A6jI~
        	if (mAdapter!=null)                                    //~1A6jI~
            {                                                      //~1A6jI~
                mAdapter.enableForegroundDispatch(AG.activity,mPendingIntent,mFilters,null/*mTechLists*/);//~1A6jR~
                if (Dump.Y) Dump.println("WDANFC:onResume:enableForgroundDispatch");//~1A6jI~
            }                                                      //~1A6jI~
        }                                                          //~1A6jI~
        catch (Exception e)                                        //~1A6jI~
        {                                                          //~1A6jI~
			Dump.println(e,"WDANFC:onResume");                     //~1A6jI~
        }                                                          //~1A6jI~
    }                                                              //~1A6jI~
	//*************************************************************************//~1A6jI~
    private static void initAdapter()                              //~1A6jR~
    {                                                              //~1A6jI~
        mAdapter = NfcAdapter.getDefaultAdapter(AG.context);       //~1A6jI~
        if (mAdapter==null)                                        //~1A6GI~
        {                                                          //~1A6GI~
    	    if (Dump.Y) Dump.println("WDANFC:initAdapter getDefaultAdapter:null");//~1A6GI~
            return;                                                //~1A6GI~
        }                                                          //~1A6GI~
        if (Dump.Y) Dump.println("WDANFC:initAdapter getDefaultAdapter"+mAdapter.toString());//~1A6jR~
        mPendingIntent = PendingIntent.getActivity(AG.context,0/*requestcode*/,//~1A6jI~
                new Intent(AG.context,AG.context.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),//~1A6jI~
				0/*PendingIntent.FLAG_NO_CREATE*/);                //~1A6jR~
        IntentFilter ndef=new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);//~1A6jI~
        try                                                        //~1A6jR~
		{                                                          //~1A6jI~
			ndef.addDataType("*/*");
		}                                                          //~1A6jR~
 		catch (MalformedMimeTypeException e)                       //~1A6jI~
 		{                                                          //~1A6jI~
			Dump.println(e,"WDANFC:initAdapter");                  //~1A6jR~
		}                                   //~1A6jI~
        mFilters = new IntentFilter[] {ndef};                      //~1A6jI~
//        mAdapter.setNdefPushMessageCallback(AG.aMain,AG.activity);//~1A6jR~
//        mAdapter.setOnNdefPushCompleteCallback(AG.aMain,AG.activity);//~1A6jR~
		setCreateMsgCallback();                                    //~1A6jI~
		setSendMsgCompCallback();                                  //~1A6jI~
    }                                                              //~1A6jI~
	//*************************************************************************//~1A6jI~
    public static void onPause() {                                 //~1A6jR~
   	    if (Dump.Y) Dump.println("AMAin:onPause");                 //~1A6jI~
        if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android4       //~1A6bI~//~1A6jI~
        	return;                                                //~1A6jI~
        try                                                        //~1A6jI~
        {                                                          //~1A6jI~
            if (mAdapter != null)                                  //~1A6jI~
            {                                                      //~1A6jI~
                 mAdapter.disableForegroundDispatch(AG.activity);  //~1A6jI~
                if (Dump.Y) Dump.println("WDANFC:onpause disableForgroundDispatch");//~1A6jI~
            }                                                      //~1A6jI~
        }                                                          //~1A6jI~
        catch (Exception e)                                        //~1A6jI~
        {                                                          //~1A6jI~
			Dump.println(e,"WDANFC:onPauset");                     //~1A6jI~
        }                                                          //~1A6jI~
    }                                                              //~1A6jI~
	//*************************************************************************//~1A6jI~
    public static void setCreateMsgCallback()                      //~1A6jI~
	{                                                              //~1A6jI~
    	CreateNdefMessageCallback cb=new CreateNdefMessageCallback()//~1A6jI~
                        {                                          //~1A6jI~
                            @Override 
    						public NdefMessage createNdefMessage(NfcEvent Pevent)//~1A6jR~
                             {                                     //~1A6jI~
                                if (Dump.Y) Dump.println("WDANFC:createNdefMessage callback");//~1A6jI~//~1A6sR~
       							if (AG.swNFCBT)                    //~1Ab8R~
	                                return DialogNFCSelect.createNFCMsg(Pevent);//~1Ab8R~
                                return DialogNFC.createNFCMsg(Pevent);//~1A6sR~
                             }                                     //~1A6jI~
                        };                                         //~1A6jI~
		mAdapter.setNdefPushMessageCallback(cb,AG.activity);       //~1A6jI~
	}                                                              //~1A6jI~
	//*************************************************************************//~1A6jI~
    public static void setSendMsgCompCallback()                     //~1A6jI~
	{                                                              //~1A6jI~
		OnNdefPushCompleteCallback cb=new OnNdefPushCompleteCallback()//~1A6jI~
                        {
							@Override//~1A6jI~
                            public void onNdefPushComplete(NfcEvent Pevent)//~1A6jI~
                            {                                      //~1A6jI~
                                if (Dump.Y) Dump.println("WDANFC:onNdefPushComplete");//~1A6jI~
       							if (AG.swNFCBT)                    //~1Ab8R~
	                                DialogNFCSelect.sendNFCMsg(Pevent);//~1Ab8R~
                                else                               //+1AbDI~
                                DialogNFC.sendNFCMsg(Pevent);      //~1A6sR~
                            }                                      //~1A6jI~

                        };                                         //~1A6jI~
		mAdapter.setOnNdefPushCompleteCallback(cb,AG.activity);    //~1A6jI~
	}                                                              //~1A6jI~
}
