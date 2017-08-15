//*CID://+1Af1R~:                             update#=   82;       //+1Af1R~
//*************************************************************************//~v101I~
//1Af1 2016/07/05 (Ajagot1w)update bluetooth connection dialog from bluetooth receiver//+1Af1I~
//1AbQ 2015/07/03 BT:Warning when Bluetooth was set OFF by System:settings//~1AbQI~
//1AbP 2015/07/03 cancelBondProcess set Bonduing-->Avalable, but startDiscovery dose not work(No Found/END broadcast msg delivered)//~1AbPI~
//1AbL 2015/07/02 try cancelBondProcess at scan if bonding         //~1AbLI~
//1AbG 2015/07/01 add receiver of scanMode change for edbug        //~1AbGI~
//101a 2013/01/30 IP connection                                    //~v101I~
//*************************************************************************//~v101I~
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.Asgts.BT;                                               //~@@@@R~//~v101R~

import jagoclient.Dump;
import jagoclient.gui.DoActionListener;
import jagoclient.partner.BluetoothConnection;                     //+1Af1I~

import java.lang.reflect.Method;
import java.util.LinkedList;
import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.Alert;
import com.Asgts.R;                                                //~v101R~
import com.Asgts.URunnable;                                        //~v101R~
import com.Asgts.URunnableI;                                       //~v101R~

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.os.Parcelable;

/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class BTDiscover extends BroadcastReceiver                 //~@@@@R~
	implements URunnableI                                          //~v101I~
{
	private static BTDiscover instBTD;                             //~v101I~
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
//    private ArrayAdapter<String> mPairedDevicesArrayAdapter;     //~v101R~
//    private ArrayAdapter<String> mNewDevicesArrayAdapter;        //~v101R~
    private DoActionListener doActionListener;                     //~@@@@I~
    private LinkedList<String> devicelist;                         //~v101R~
    public static String[] newDevice;                              //~@@@@I~
    private boolean swDiscover;                                    //~v101I~
    private static String SbondingDeviceAddr,SbondingDeviceName;   //~1AbGI~
    public BTDiscover()                                            //~@@@@R~
	{                                                              //~@@@@I~
//        super.onCreate(savedInstanceState);                      //~@@@@R~

//        // Setup the window                                      //~@@@@R~
//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//~@@@@R~
//        setContentView(R.layout.device_list);                    //~@@@@R~

        // Set result CANCELED incase the user backs out
//        setResult(Activity.RESULT_CANCELED);                     //~@@@@R~

//        // Initialize the button to perform device discovery     //~@@@@R~
//        Button scanButton = (Button) findViewById(R.id.button_scan);//~@@@@R~
//        scanButton.setOnClickListener(new OnClickListener() {    //~@@@@R~
//            public void onClick(View v) {                        //~@@@@R~
//                doDiscovery();                                   //~@@@@R~
//                v.setVisibility(View.GONE);                      //~@@@@R~
//            }                                                    //~@@@@R~
//        });                                                      //~@@@@R~

//        // Initialize array adapters. One for already paired devices and//~@@@@R~
//        // one for newly discovered devices                      //~@@@@R~
//        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);//~@@@@R~
//        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);//~@@@@R~

//        // Find and set up the ListView for paired devices       //~@@@@R~
//        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);//~@@@@R~
//        pairedListView.setAdapter(mPairedDevicesArrayAdapter);   //~@@@@R~
//        pairedListView.setOnItemClickListener(mDeviceClickListener);//~@@@@R~

//        // Find and set up the ListView for newly discovered devices//~@@@@R~
//        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);//~@@@@R~
//        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);  //~@@@@R~
//        newDevicesListView.setOnItemClickListener(mDeviceClickListener);//~@@@@R~

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        this.registerReceiver(mReceiver, filter);                //~@@@@R~
        AG.activity.registerReceiver(this, filter);                //~@@@@I~

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        this.registerReceiver(mReceiver, filter);                //~@@@@R~
        AG.activity.registerReceiver(this, filter);           //~@@@@I~
                                                                   //~1AbGI~
        filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//api5//~1AbGR~
        AG.activity.registerReceiver(this, filter);                //~1AbGI~
        addFilter_ActionParing();                                  //~1AbGI~
        addFilter_ActionUuid();                                    //~1AbGI~

        // Register for scan mdoe change                           //~1AbGI~
        filter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//~1AbGI~
        AG.activity.registerReceiver(this, filter);                //~1AbGI~
                                                                   //~1AbGI~
        // Register state change(bluetooth off/on)                 //~1AbGI~
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);//~1AbGI~
        AG.activity.registerReceiver(this, filter);                //~1AbGI~
                                                                   //~1AbGI~
        if (Dump.Y) Dump.println("BTDiscover constructor register receiver");//~1AbGI~
        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

//        // Get a set of currently paired devices                 //~@@@@R~
//        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();//~@@@@R~

//        // If there are paired devices, add each one to the ArrayAdapter//~@@@@R~
//        if (pairedDevices.size() > 0) {                          //~@@@@R~
//            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//~@@@@R~
//            for (BluetoothDevice device : pairedDevices) {       //~@@@@R~
//                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());//~@@@@R~
//            }                                                    //~@@@@R~
//        } else {                                                 //~@@@@R~
//            String noDevices = getResources().getText(R.string.none_paired).toString();//~@@@@R~
//            mPairedDevicesArrayAdapter.add(noDevices);           //~@@@@R~
//        }                                                        //~@@@@R~
    }

//    @Override                                                    //~@@@@R~
    protected void onDestroy() {
//        super.onDestroy();                                       //~@@@@R~
        if (Dump.Y) Dump.println("BTDiscover onDestroy");          //~v101I~

        // Make sure we're not doing discovery anymore
//        if (mBtAdapter != null) {                                //~v101R~
//            mBtAdapter.cancelDiscovery();                        //~v101R~
//        }                                                        //~v101R~
        cancelDiscover(mBtAdapter);                                //~v101I~

        // Unregister broadcast listeners
//        this.unregisterReceiver(mReceiver);                      //~@@@@R~
       if (Dump.Y) Dump.println("BTDiscover onDestroy return");   //~v101R~
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
//    private void doDiscovery() {                                 //~@@@@R~
    public void doDiscovery(DoActionListener Pdal)                //~@@@@I~
	{                                                              //~@@@@I~
    	doActionListener=Pdal;                                     //~@@@@I~
        if (Dump.Y) Dump.println("BTDiscover doDiscovery");        //~@@@@R~

//        // Indicate scanning in the title                        //~@@@@R~
//        setProgressBarIndeterminateVisibility(true);             //~@@@@R~
//        setTitle(R.string.scanning);                             //~@@@@R~

//        // Turn on sub-title for new devices                     //~@@@@R~
//        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);//~@@@@R~

        // If we're already discovering, stop it
//        if (mBtAdapter.isDiscovering()) {                        //~v101R~
//            mBtAdapter.cancelDiscovery();                        //~v101R~
//        }                                                        //~v101R~
//        cancelDiscover(mBtAdapter);                              //~v101R~
//      resetBonding();                                            //~1AbLR~//~1AbPR~
        // Request discover from BluetoothAdapter
        if (Dump.Y) Dump.println("request startDiscovery adapter="+mBtAdapter.toString());//~v101R~
//        mBtAdapter.startDiscovery();                             //~v101R~
    	devicelist=new LinkedList<String>();                       //~v101I~
	    swDiscover=true;                                           //~v101I~
        startDiscover(mBtAdapter);                                 //~v101R~
        if (Dump.Y) Dump.println("request startDiscovery return"); //~v101I~
    }

//    // The on-click listener for all devices in the ListViews    //~@@@@R~
//    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {//~@@@@R~
//        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {//~@@@@R~
//            // Cancel discovery because it's costly and we're about to connect//~@@@@R~
//            mBtAdapter.cancelDiscovery();                        //~@@@@R~

//            // Get the device MAC address, which is the last 17 chars in the View//~@@@@R~
//            String info = ((TextView) v).getText().toString();   //~@@@@R~
//            String address = info.substring(info.length() - 17); //~@@@@R~

//            // Create the result Intent and include the MAC address//~@@@@R~
//            Intent intent = new Intent();                        //~@@@@R~
//            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);      //~@@@@R~

//            // Set result and finish this Activity               //~@@@@R~
//            setResult(Activity.RESULT_OK, intent);               //~@@@@R~
//            finish();                                            //~@@@@R~
//        }                                                        //~@@@@R~
//    };                                                           //~@@@@R~

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Dump.Y) Dump.println("Broardcast receiver action="+action+",destroy="+AG.aBT.swDestroy+",swDiscover="+swDiscover);//~@@@@I~//~v101R~//~1AbGR~
            if (AG.aBT.swDestroy)                                  //~v101R~
            	return;                                            //~v101I~
            BluetoothConnection.onReceive(action);                 //+1Af1I~
//          if (!swDiscover)                                       //~v101I~//~1AbGR~
//          	return;                                            //~v101I~//~1AbGR~
          try                                                      //~v101I~
          {                                                        //~v101I~
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if (!swDiscover)                                   //~1AbGI~
              		return;                                        //~1AbGI~
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());//~@@@@R~
//                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());//~@@@@I~//~v101R~
                    String name=device.getName();                  //~@@@@I~
                    String addr=device.getAddress();               //~@@@@I~
   		   	      	if (Dump.Y) Dump.println("Broarcast receiver device="+name+",addr="+addr);//~@@@@I~
		        	devicelist.add(name);                          //~@@@@I~
		        	devicelist.add(addr);                          //~@@@@I~
                }
            // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                setProgressBarIndeterminateVisibility(false);    //~@@@@R~
//                setTitle(R.string.select_device);                //~@@@@R~
//                if (mNewDevicesArrayAdapter.getCount() == 0) {   //~@@@@R~
//                    String noDevices = getResources().getText(R.string.none_found).toString();//~@@@@R~
//                    mNewDevicesArrayAdapter.add(noDevices);      //~@@@@R~
//                }                                                //~@@@@R~
                if (!swDiscover)                                   //~1AbGI~
              		return;                                        //~1AbGI~
	            swDiscover=false;                                  //~v101I~
				if (devicelist.size()>0)                           //~@@@@I~
					newDevice=devicelist.toArray(new String[0]);   //~@@@@R~
                else                                               //~@@@@I~
					newDevice=null;                                //~@@@@I~
//              unregister();                                      //~v101R~
				doActionListener.doAction(AG.resource.getString(R.string.ActionDiscovered));//~@@@@I~
//              instBTD=null;                                      //~v101R~
            }
            else                                                   //~1AbGI~
            if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action))//~1AbGI~
            {                                                      //~1AbGI~
	            int scanmode=intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR);//~1AbGI~
	            int scanmodeold=intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE,BluetoothAdapter.ERROR);//~1AbGI~
                String smode="";                                   //~1AbGI~
                switch(scanmode)                                   //~1AbGI~
                {                                                  //~1AbGI~
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE://~1AbGI~
                	smode="ScanMode:CONNECTABLE_DISCOVERABLE";//~1AbGI~
	        		AView.showToastLong(R.string.InfoBTDiscoverable);//~1AbGI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.SCAN_MODE_CONNECTABLE:       //~1AbGI~
                	smode="ScanMode:CONNECTABLE";           //~1AbGI~
	        		AView.showToastLong(R.string.InfoBTConnectable);//~1AbGI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.SCAN_MODE_NONE:              //~1AbGI~
	        		AView.showToastLong(R.string.InfoBTNone);      //~1AbGI~
                	smode="ScanMode:NONE";                  //~1AbGI~
                    break;                                         //~1AbGI~
                }                                                  //~1AbGI~
	            if (Dump.Y) Dump.println("Broardcast receiver ACTION_SCAN_MODE_CHANGED scanmode old="+scanmodeold+"-->"+scanmode+"="+smode);//~1AbGR~
            }                                                      //~1AbGI~
            else                                                   //~1AbGI~
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action))//~1AbGI~
            {                                                      //~1AbGI~
	            int state=intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);//~1AbGI~
                String sstate="";                                  //~1AbGI~
                switch(state)                                      //~1AbGI~
                {                                                  //~1AbGI~
                case BluetoothAdapter.STATE_DISCONNECTED://0       //~1AbGI~
                	sstate="Bluetooth State disconnected";         //~1AbGI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.STATE_CONNECTING://1         //~1AbGI~
                	sstate="Bluetooth State connecting";           //~1AbGI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.STATE_CONNECTED://2          //~1AbGI~
                	sstate="Bluetooth State connected";            //~1AbGI~
                    break;                                         //~1AbGM~
                case BluetoothAdapter.STATE_DISCONNECTING://3      //~1AbGI~
                	sstate="Bluetooth State disconnecting";        //~1AbGI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.STATE_OFF:     //10          //~1AbGI~
                	sstate="Bluetooth State changed to OFF";       //~1AbGI~
		            String msg=AG.resource.getString(R.string.WarningBluetoothTurnedOff);//~1AbQI~
			    	int flag=Alert.BUTTON_POSITIVE;                //~1AbQI~
					Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1AbQI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.STATE_TURNING_ON:  //11      //~1AbGI~
                	sstate="Bluetooth State changed to Turning ON";//~1AbGI~
                    break;                                         //~1AbGI~
                case BluetoothAdapter.STATE_ON:      //12          //~1AbGR~
                	sstate="Bluetooth State changed to ON";        //~1AbGR~
                    break;                                         //~1AbGR~
                case BluetoothAdapter.STATE_TURNING_OFF: //13      //~1AbGR~
                	sstate="Bluetooth State changed to Turning OFF";//~1AbGI~
                    break;                                         //~1AbGI~
                }                                                  //~1AbGI~
	            if (Dump.Y) Dump.println("Broardcast receiver ACTION_STATE_CHANGED to "+state+"="+sstate);//~1AbGI~
            }                                                      //~1AbGI~
            else                                                   //~1AbGI~
            if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action))//~1AbGI~
            {                                                      //~1AbGI~
	            if (Dump.Y) Dump.println("Broardcast receiver ACTION_PARING_REQUEST");//~1AbGI~
            }                                                      //~1AbGI~
            else                                                   //~1AbGI~
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action))//~1AbGI~
            {                                                      //~1AbGI~
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);//~1AbGI~
                String name="";                                    //~1AbGI~
                String addr="";                                    //~1AbGI~
                if (device!=null)                                  //~1AbGI~
                {                                                  //~1AbGI~
                	name=device.getName();                         //~1AbGR~
                	addr=device.getAddress();                      //~1AbGR~
                }                                                  //~1AbGI~
	            int state=intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,BluetoothAdapter.ERROR);//~1AbGI~
	            int stateold=intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE,BluetoothAdapter.ERROR);//~1AbGI~
	            if (Dump.Y) Dump.println("Broardcast receiver ACTION_BOND_STATE_CHANGED name="+name+"="+addr+",state="+stateold+"->"+state);//~1AbGI~
                if (state==BluetoothDevice.BOND_BONDING)            //~1AbGI~
                {                                                  //~1AbGI~
                	SbondingDeviceAddr=addr;                       //~1AbGI~
                	SbondingDeviceName=name;                       //~1AbGI~
                }                                                  //~1AbGI~
            }                                                      //~1AbGI~
            else                                                   //~1AbGI~
            if (BluetoothDevice.ACTION_UUID.equals(action))       //~1AbGI~
            {                                                      //~1AbGI~
            	process_ActionUuid(intent);                        //~1AbGR~
            }                                                      //~1AbGI~
		  }//try                                                   //~v101I~
          catch(Exception e)                                       //~v101R~
          {                                                        //~v101I~
          	Dump.println(e,"BTDiscover:onReceive");                //~v101I~
          }                                                        //~v101I~
          if (Dump.Y) Dump.println("Broardcast receiver return");  //~v101I~
    };
    //*********************************************************    //~@@@@I~
    public static void discover(DoActionListener Pdal)             //~@@@@I~
    {                                                              //~@@@@I~
        if (Dump.Y) Dump.println("BTDiscover start");              //~@@@@I~
    	instBTD=AG.aBT.mBTD;                                       //~v101R~
        instBTD.doDiscovery(Pdal);                                     //~@@@@I~//~v101R~
        return;                                                    //~@@@@I~
    }                                                              //~@@@@I~
    //*********************************************************    //~v101I~
    public static void cancelDiscover()       //~v101I~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("BTDiscover cancelDiscover");     //~v101I~
        if (instBTD!=null)                                         //~v101I~
        	instBTD.onDestroy();	//cancel discovery and unregister//~v101I~
        return;                                                    //~v101I~
    }                                                              //~v101I~
    //*********************************************************    //~v101I~
    public void unregister()                                       //~v101I~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("BTDiscover unregister");         //~v101I~
		AG.activity.unregisterReceiver(this);                      //~@@@@I~//~v101I~
    }                                                              //~v101I~
    //*********************************************************    //~v101I~
    private void startDiscover(BluetoothAdapter Padapter)          //~v101R~
    {                                                              //~v101I~
    	requestDiscover(Padapter,1);                               //~v101I~
    }                                                              //~v101I~
    //*********************************************************    //~v101I~
    public void cancelDiscover(BluetoothAdapter Padapter)          //~v101I~
    {                                                              //~v101I~
    	requestDiscover(Padapter,0);                               //~v101I~
    }                                                              //~v101I~
    //*********************************************************    //~v101I~
    public void requestDiscover(BluetoothAdapter Padapter,int Pstart)//~v101I~
    {                                                              //~v101I~
    	if (Padapter==null)                                        //~v101I~
        	return;                                                //~v101I~
        try                                                        //~v101I~
        {                                                          //~v101I~
//            if (Dump.Y) Dump.println("BTDiscover cancelDiscover adapter="+Padapter);//~v101R~
//            if (Padapter.isDiscovering())                        //~v101R~
//            {                                                    //~v101R~
//                if (Dump.Y) Dump.println("BTDiscover:cancelDiscover issue cancel");//~v101R~
//                Padapter.cancelDiscovery();                      //~v101R~
//                Utils.sleep(200);   //for safety                 //~v101R~
//            }                                                    //~v101R~
            if (Dump.Y) Dump.println("BTDiscover requestDiscovery start="+Pstart+",setRunFuncSubThread:"+this);//~1AbGR~
		    URunnable.setRunFuncSubthread(this,0/*deley*/,Padapter,Pstart);//~v101R~
        }                                                          //~v101I~
        catch(Exception e)                                         //~v101I~
        {                                                          //~v101I~
        	Dump.println(e,"cancelDiscover for adapter");          //~v101I~
        }                                                          //~v101I~
	    if (Dump.Y) Dump.println("BTDiscover cancelDiscover return");
    }//~v101I~
    //*********************************************************    //~v101I~
    public void runFunc(Object Pparmobj,int Pparmint)                   //~v101I~
    {                                                              //~v101I~
	    if (Dump.Y) Dump.println("BTDiscover runFunc:start");      //~v101R~
		BluetoothAdapter adapter=(BluetoothAdapter)Pparmobj;       //~v101I~
		try
		{
        	AG.aBT.mBTC.requestDiscover(adapter,Pparmint);         //~v101R~
		}//~v101I~
        catch(Exception e)                                         //~v101I~
        {                                                          //~v101I~
        	Dump.println(e,"cancelDiscover:runFunc for adapter");  //~v101I~
        }                                                          //~v101I~
	    if (Dump.Y) Dump.println("BTDiscover runFunc:end");        //~v101R~
    }                                                              //~v101I~
//**************************************************************************//~@@@@I~//~v101I~
//*get device list pair of name,addr                               //~@@@@I~//~v101I~
//**************************************************************************//~@@@@I~//~v101I~
    public static String[] getPairDeviceList()                     //~@@@@R~//~v101I~
    {                                                              //~@@@@I~//~v101I~
        String[] sa=null;                                          //~v101I~
	    if (Dump.Y) Dump.println("BTDiscover getpairedDeviceList:start");//~v101I~
		try                                                        //~v101I~
		{                                                          //~v101I~
        	sa=AG.aBT.mBTC.getPairedDeviceList();                  //~v101R~
		}                                                          //~v101I~
        catch(Exception e)                                         //~v101I~
        {                                                          //~v101I~
        	Dump.println(e,"BTDiscover getPairedDeviceLst");       //~v101I~
        }                                                          //~v101I~
	    if (Dump.Y) Dump.println("BTDiscover getpairedDeviceList:end");//~v101I~
        return sa;                                                 //~@@@@I~//~v101I~
    }                                                              //~@@@@I~//~v101I~
//**************************************************************************//~1AbGI~
	private void addFilter_ActionParing()                          //~1AbGI~
    {                                                              //~1AbGI~
        if (AG.osVersion>=19)  //android4                          //~1AbGI~
        	addFilter_ActionParing19();                            //~1AbGI~
    }                                                              //~1AbGI~
//**************************************************************************//~1AbGI~
	@TargetApi(19)                                                 //~1AbGI~
	private void addFilter_ActionParing19()                        //~1AbGI~
    {                                                              //~1AbGI~
    	if (Dump.Y) Dump.println("addFilter_ActionParing api19:android 4.4");//~1AbGI~
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);//api19 android 4.4//~1AbGI~
        AG.activity.registerReceiver(this, filter);                //~1AbGI~
    }                                                              //~1AbGI~
//**************************************************************************//~1AbGI~
	private void addFilter_ActionUuid()                            //~1AbGI~
    {                                                              //~1AbGI~
        if (AG.osVersion>=15)  //android4                          //~1AbGI~
        	addFilter_ActionUuid15();                              //~1AbGI~
    }                                                              //~1AbGI~
//**************************************************************************//~1AbGI~
	@TargetApi(15)                                                 //~1AbGI~
	private void addFilter_ActionUuid15()                          //~1AbGI~
    {                                                              //~1AbGI~
    	if (Dump.Y) Dump.println("addFilter_ActionUuid api15:android4.03/04");//~1AbGI~
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_UUID);//api15 android 4.03 4.04//~1AbGI~
        AG.activity.registerReceiver(this, filter);                //~1AbGI~
    }                                                              //~1AbGI~
//**************************************************************************//~1AbGI~
	private void process_ActionUuid(Intent intent)                 //~1AbGR~
    {                                                              //~1AbGI~
        if (AG.osVersion>=15)  //android4                          //~1AbGI~
        	process_ActionUuid15(intent);                                //~1AbGI~
    }                                                              //~1AbGI~
//**************************************************************************//~1AbGI~
	@TargetApi(15)                                                 //~1AbGI~
	private void process_ActionUuid15(Intent intent)               //~1AbGI~
    {                                                              //~1AbGI~
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);//~1AbGI~
        String name="";                                            //~1AbGI~
        String addr="";                                            //~1AbGI~
        if (device!=null)                                          //~1AbGI~
        {                                                          //~1AbGI~
            name=device.getName();                                 //~1AbGI~
            addr=device.getAddress();                              //~1AbGI~
        }                                                          //~1AbGI~
        if (Dump.Y) Dump.println("Broardcast receiver ACTION_UUID name="+name+"="+addr);//~1AbGI~
        Parcelable puuid[] = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);//~1AbGI~
        if (puuid!=null)                                           //~1AbGI~
        {                                                          //~1AbGI~
            for (int ii=0;ii<puuid.length;ii++)                    //~1AbGI~
                if (Dump.Y) Dump.println("Broardcast receiver uuid["+ii+"]="+((ParcelUuid)puuid[ii]).toString());//~1AbGI~
        }                                                          //~1AbGI~
    }                                                              //~1AbGI~
//**************************************************************************//~1AbGI~
//*cancel bonding process                                          //~1AbLR~
//**************************************************************************//~1AbLR~
    public static void resetBonding()                              //~1AbLR~
    {                                                              //~1AbLR~
	    if (Dump.Y) Dump.println("BTDiscover resetBonding name="+SbondingDeviceName+",addr="+SbondingDeviceAddr);//~1AbLR~
        String addr=SbondingDeviceAddr;                            //~1AbLR~
    	if (addr!=null && !addr.equals(""))                                      //~1AbLR~//~1AbGR~
        {                                                          //~1AbLR~
	        BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();//~1AbLR~
			BluetoothDevice device=adapter.getRemoteDevice(addr);  //~1AbLR~
            int state=device.getBondState();                       //~1AbLR~
		    if (Dump.Y) Dump.println("BTDiscover resetBonding status="+state);//~1AbLR~
			if (state==BluetoothDevice.BOND_BONDING)               //~1AbLR~//~1AbGR~
            {                                                      //~1AbGI~
				cancelBonding(device);                             //~1AbLR~
//  			removeBonding(device); //apply only if BONDED device/+1AbGI~//~1AbGR~
            }                                                      //~1AbGI~
        }                                                          //~1AbLR~
    }                                                              //~1AbLR~
//**************************************************************************//~1AbLR~
//*cancel bonding process                                          //~1AbLR~
//**************************************************************************//~1AbLR~
    public static boolean cancelBonding(BluetoothDevice Pdevice)   //~1AbLR~
    {                                                              //~1AbLR~
    	boolean rc=false;                                          //~1AbLR~
	    if (Dump.Y) Dump.println("BTDiscover cancelBonding");      //~1AbLR~
    	Method method;                                             //~1AbLR~
		try                                                        //~1AbLR~
		{                                                          //~1AbLR~
			method = Pdevice.getClass().getMethod("cancelBondProcess",(Class[])null);//~1AbLR~
			try                                                    //~1AbLR~
			{                                                      //~1AbLR~
				rc=(Boolean)method.invoke(Pdevice);                //~1AbLR~
            }                                                      //~1AbLR~
            catch (Exception e)                                    //~1AbLR~
            {                                                      //~1AbLR~
                Dump.println(e,"BTDiscover cancelBonding invoke"); //~1AbLR~
            }                                                      //~1AbLR~
		    if (Dump.Y) Dump.println("BTDiscover cancelBonding rc="+rc);//~1AbLR~
		}                                                          //~1AbLR~
		catch (NoSuchMethodException e)                            //~1AbLR~
		{                                                          //~1AbLR~
		    Dump.println(e,"BTDiscover cancelBonding getMethod");  //~1AbLR~
        }                                                          //~1AbLR~
        return rc;                                                 //~1AbLR~
    }                                                              //~1AbLR~
//**************************************************************************//~1AbGI~
//*cancel bonding process                                          //~1AbGI~
//**************************************************************************//~1AbGI~
    public static boolean removeBonding(BluetoothDevice Pdevice)   //~1AbGI~
    {                                                              //~1AbGI~
    	boolean rc=false;                                          //~1AbGI~
	    if (Dump.Y) Dump.println("BTDiscover removeBonding");      //~1AbGI~
    	Method method;                                             //~1AbGI~
		try                                                        //~1AbGI~
		{                                                          //~1AbGI~
			method = Pdevice.getClass().getMethod("removeBond",(Class[])null);//~1AbGI~
			try                                                    //~1AbGI~
			{                                                      //~1AbGI~
				rc=(Boolean)method.invoke(Pdevice);                //~1AbGI~
            }                                                      //~1AbGI~
            catch (Exception e)                                    //~1AbGI~
            {                                                      //~1AbGI~
                Dump.println(e,"BTDiscover removeBond invoke");    //~1AbGI~
            }                                                      //~1AbGI~
		    if (Dump.Y) Dump.println("BTDiscover removeBond rc="+rc);//~1AbGI~
		}                                                          //~1AbGI~
		catch (NoSuchMethodException e)                            //~1AbGI~
		{                                                          //~1AbGI~
		    Dump.println(e,"BTDiscover removeBond getMethod");     //~1AbGI~
        }                                                          //~1AbGI~
        return rc;                                                 //~1AbGI~
    }                                                              //~1AbGI~
}
