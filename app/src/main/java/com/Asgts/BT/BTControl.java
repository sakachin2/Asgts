//*CID://+1AbHR~:                             update#=  151;       //~1AbJR~//~1AbHR~
//****************************************************************************//~v101I~
//1Ac5 2015/07/09 NFCBT:confirmation dialog is not show and fails to pairig//+1AbHI~
//                (LocalBluetoothPreference:"Found no reason to show dialog",requires discovaring status in the 60 sec before)//+1AbHI~
//                Issue waring whne NFCBT-Secure                   //+1AbHI~
//1AbJ 2015/07/02 stop discoverable when connection complete       //~1AbJI~
//1AbH 2015/07/02 change diiscoverable duaration from 300 to 60(default=120)//~1AbHR~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6aI~
//1A60 2015/01/25 also support Bluetooth secure SPP(Bluetooth 2.1) //~1A60I~
//101i 2013/02/09 for the case Bluetoothe disabled at initial      //~v101I~
//101a 2013/01/30 IP connection                                    //~v101I~
//****************************************************************************//~v101I~
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

import com.Asgts.AG;                                                //~@@@@R~//~v101R~
import com.Asgts.AView;                                             //~@@@@R~//~v101R~
import com.Asgts.R;                                                //~v101R~

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
//import android.os.Bundle;                                        //~@@@@R~
import android.os.Handler;
import android.os.Message;
//import android.util.Log;                                         //~@@@@R~
//import android.view.KeyEvent;                                    //~@@@@R~
//import android.view.Menu;                                        //~@@@@R~
//import android.view.MenuInflater;                                //~@@@@R~
//import android.view.MenuItem;                                    //~@@@@R~
//import android.view.View;                                        //~@@@@R~
//import android.view.Window;                                      //~@@@@R~
//import android.view.View.OnClickListener;                        //~@@@@R~
//import android.view.inputmethod.EditorInfo;                      //~@@@@R~
//import android.widget.ArrayAdapter;                              //~@@@@R~
//import android.widget.Button;                                    //~@@@@R~
//import android.widget.EditText;                                  //~@@@@R~
//import android.widget.ListView;                                  //~@@@@R~
//import android.widget.TextView;                                  //~@@@@R~
//import android.widget.Toast;                                     //~@@@@R~

/**
 * This is the main Activity that displays the current chat session.
 */
//public class BluetoothChat extends Activity {                    //~@@@@R~
public class BTControl {                                           //~@@@@I~
    // Debugging
//    private static final String TAG = "BluetoothChat";           //~@@@@R~
//    private static final boolean D = true;                       //~@@@@R~

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_FAILURE = 6;                   //~@@@2R~
    public static final int MESSAGE_FAILUREACCEPT = 7;             //~v101I~

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String ACCEPT_TYPE = "secure";             //~v101I~

    // Intent request codes
//  private static final int REQUEST_CONNECT_DEVICE = 1;           //~1A6aR~
//  private static final int REQUEST_ENABLE_BT = 2;                //~1A6aR~
                                                                   //~@@@@I~
    public static final int BTA_ENABLE=1;                          //~@@@@I~
//    public static final int BTA_SCAN=2;                            //~@@@@I~//~v101R~
    public static final int BTA_DISCOVERABLE=3;                    //~@@@@I~
    public static final int BTA_DISCOVERABLE_STOP=4;               //~1AbJI~
    private static final int DEFAULT_DISCOVERABLE_DUARATION=120;   //~1AbHR~
    private static final int DISCOVERABLE_DUARATION=120;           //~1AbHR~
    private static final int STOP_DISCOVERABLE_DUARATION=1;        //~1AbJI~

    // Layout Views
//    private TextView mTitle;                                     //~@@@@R~
//    private ListView mConversationView;                          //~@@@@R~
//    private EditText mOutEditText;                               //~@@@@R~
//    private Button mSendButton;                                  //~@@@@R~

    // Name of the connected device
//    private String mConnectedDeviceName = null;                  //~@@@@R~
    private static String mConnectedDeviceName = null;             //~@@@@I~//~@@@2R~
    public String mLocalDeviceName;                                //~@@@@I~
    // Array adapter for the conversation thread
//    private ArrayAdapter<String> mConversationArrayAdapter;      //~@@@@R~
//    private static ArrayAdapter<String> mConversationArrayAdapter;//~@@@@R~
    // String buffer for outgoing messages
//    private StringBuffer mOutStringBuffer;                       //~@@@@R~
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
//  private BluetoothChatService mChatService = null;              //~@@@@R~
    private BTService mChatService = null;                  //~@@@@R~//~@@@2R~//~v101R~
	private Activity activity;                                     //~@@@@I~//~@@@2R~
    private Handler mHandler;                                      //~@@@@R~
//    private Server parnerServer;                                 //~@@@@R~
    private BluetoothSocket mBTSocket;                             //~@@@@R~
 
//*************************************************************************//~@@@@I~
    public BTControl()                                             //~@@@@I~//~@@@2R~
    {                                                              //~@@@@I~
    	activity=AG.activity;                                       //~@@@@I~
    	mConnectedDeviceName=null;	//called at main create        //~@@@2I~
        mHandler=new BTHandler();	//on MainThread                //~@@@@I~
    }                                                              //~@@@@I~
//*************************************************************************//~@@@@R~
//    @Override                                                    //~@@@@R~
//    public void onCreate(Bundle savedInstanceState) {            //~@@@@R~
//        super.onCreate(savedInstanceState);                      //~@@@@R~
//        if(D) Log.e(TAG, "+++ ON CREATE +++");                   //~@@@@R~

//        // Set up the window layout                              //~@@@@R~
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);       //~@@@@R~
//        setContentView(R.layout.main);                           //~@@@@R~
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);//~@@@@R~

//        // Set up the custom title                               //~@@@@R~
//        mTitle = (TextView) findViewById(R.id.title_left_text);  //~@@@@R~
//        mTitle.setText(R.string.app_name);                       //~@@@@R~
//        mTitle = (TextView) findViewById(R.id.title_right_text); //~@@@@R~

//        // Get local Bluetooth adapter                           //~@@@@R~
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//~@@@@R~

//        // If the adapter is null, then Bluetooth is not supported//~@@@@R~
//        if (mBluetoothAdapter == null) {                         //~@@@@R~
//            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();//~@@@@R~
//            finish();                                            //~@@@@R~
//            return;                                              //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
//*************************************************************************//~@@@@I~
//*create Adapter(bluetooth support chk)                           //~@@@@I~
//*************************************************************************//~@@@@I~
    public boolean BTCreate() {                                    //~@@@@R~
        if(Dump.Y) Dump.println("+++ BTCreate+++");                //~@@@@M~
        if (mBluetoothAdapter != null)                             //~@@@@I~
        	return true;                                           //~@@@@I~
        // Get local Bluetooth adapter                             //~@@@@R~
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();  //~@@@@I~
                                                                   //~@@@@I~
        // If the adapter is null, then Bluetooth is not supported //~@@@@I~
        if (mBluetoothAdapter == null) {                           //~@@@@I~
	        AView.showToastLong(R.string.noBTadapter);             //~@@@@R~
            return false;                                          //~@@@@I~
        }                                                          //~@@@@I~
        mLocalDeviceName=mBluetoothAdapter.getName();              //~@@@@I~
        return true;                                               //~@@@@I~
    }                                                              //~@@@@I~

//*************************************************************************//~@@@@I~
//    @Override                                                    //~@@@@R~
//    public void onStart() {                                      //~@@@@R~
//        super.onStart();                                         //~@@@@R~
//        if(D) Log.e(TAG, "++ ON START ++");                      //~@@@@R~

//        // If BT is not on, request that it be enabled.          //~@@@@R~
//        // setupChat() will then be called during onActivityResult//~@@@@R~
//        if (!mBluetoothAdapter.isEnabled()) {                    //~@@@@R~
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);//~@@@@R~
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);//~@@@@R~
//        // Otherwise, setup the chat session                     //~@@@@R~
//        } else {                                                 //~@@@@R~
//            if (mChatService == null) setupChat();               //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
//*************************************************************************//~@@@@M~
//*enable bluetooth                                                //~@@@@I~
//*rc:true if start activity-enableBluetooth                       //~@@@@M~
//*************************************************************************//~@@@@I~
    public boolean BTStart() {                                     //~@@@@R~
    	boolean rc=false;                                          //~@@@@I~
        if(Dump.Y) Dump.println("+++ BTStart+++");                 //~@@@@M~
        // If BT is not on, request that it be enabled.            //~@@@@I~
        // setupChat() will then be called during onActivityResult //~@@@@I~
//        if (!mBluetoothAdapter.isEnabled()) {                      //~@@@@I~//~v101R~
//            startBTActivity(BTA_ENABLE);                           //~@@@@I~//~v101R~
		if (!BTEnable(false)){	//requested "enable"                   //~v101I~
            rc=true;                                               //~@@@@I~
        // Otherwise, setup the chat session                       //~@@@@I~
        } else {                                                   //~@@@@I~
            if (mChatService == null) setupChat();//accept                 //~@@@@R~//~v101R~
        }                                                          //~@@@@I~
        return rc;                                                 //~@@@@I~
    }                                                              //~@@@@I~
    //************************************                         //~v101I~
    public boolean BTEnable(boolean Presume)                       //~v101I~
    {                                                              //~v101I~
    	boolean rc=true;                                           //~v101I~
        if(Dump.Y) Dump.println("+++ BTEnable+++");                //~v101I~
        if (!mBluetoothAdapter.isEnabled())                        //~v101I~
        {                                                          //~v101I~
            startBTActivity(BTA_ENABLE);                           //~v101I~
            rc=false;                                              //~v101I~
        }                                                          //~v101I~
        if(Dump.Y) Dump.println("BTEnable rc="+rc);                //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~

//*************************************************************************//~@@@@I~
//*from Asgts                                                       //~@@@@R~//~v101R~
//*************************************************************************//~@@@@I~
//    @Override                                                    //~@@@@R~
//    public synchronized void onResume() {                        //~@@@@R~
//        super.onResume();                                        //~@@@@R~
//        if(D) Log.e(TAG, "+ ON RESUME +");                       //~@@@@R~

//        // Performing this check in onResume() covers the case in which BT was//~@@@@R~
//        // not enabled during onStart(), so we were paused to enable it...//~@@@@R~
//        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.//~@@@@R~
//        if (mChatService != null) {                              //~@@@@R~
//            // Only if the state is STATE_NONE, do we know that we haven't started already//~@@@@R~
//            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {//~@@@@R~
//              // Start the Bluetooth chat services               //~@@@@R~
//              mChatService.start();                              //~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
    public synchronized void BTResume() {                          //~@@@@I~
        if(Dump.Y) Dump.println("+++ BTResume+++");                //~@@@@I~
                                                                   //~@@@@I~
        // Performing this check in onResume() covers the case in which BT was//~@@@@I~
        // not enabled during onStart(), so we were paused to enable it...//~@@@@I~
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.//~@@@@I~
//        startService(); //accept                                   //~@@@@R~//~v101R~
//        BTEnable(true);	//OnResume will be repeated if operater replyed "NO"//~v101R~
    }                                                              //~@@@@I~
//*************************************************************************//~@@@@I~
    private void startService() {                                  //~@@@@I~
        if(Dump.Y) Dump.println("+++ startService+++");            //~@@@@I~
        if (mChatService != null) {                                //~@@@@I~
            // Only if the state is STATE_NONE, do we know that we haven't started already//~@@@@I~
            if (mChatService.getState() == BTService.STATE_NONE) { //~@@@@I~
              // Start the Bluetooth chat services                 //~@@@@I~
              mChatService.start();	//issue accept                 //~@@@@R~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~

//*************************************************************************//~@@@@I~
//    private void setupChat() {                                   //~@@@@R~
//        Log.d(TAG, "setupChat()");                               //~@@@@R~

//        // Initialize the array adapter for the conversation thread//~@@@@R~
//        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);//~@@@@R~
//        mConversationView = (ListView) findViewById(R.id.in);    //~@@@@R~
//        mConversationView.setAdapter(mConversationArrayAdapter); //~@@@@R~

//        // Initialize the compose field with a listener for the return key//~@@@@R~
//        mOutEditText = (EditText) findViewById(R.id.edit_text_out);//~@@@@R~
//        mOutEditText.setOnEditorActionListener(mWriteListener);  //~@@@@R~

//        // Initialize the send button with a listener that for click events//~@@@@R~
//        mSendButton = (Button) findViewById(R.id.button_send);   //~@@@@R~
//        mSendButton.setOnClickListener(new OnClickListener() {   //~@@@@R~
//            public void onClick(View v) {                        //~@@@@R~
//                // Send a message using content of the edit text widget//~@@@@R~
//                TextView view = (TextView) findViewById(R.id.edit_text_out);//~@@@@R~
//                String message = view.getText().toString();      //~@@@@R~
//                sendMessage(message);                            //~@@@@R~
//            }                                                    //~@@@@R~
//        });                                                      //~@@@@R~

//        // Initialize the BluetoothChatService to perform bluetooth connections//~@@@@R~
//        mChatService = new BluetoothChatService(this, mHandler); //~@@@@R~

//        // Initialize the buffer for outgoing messages           //~@@@@R~
//        mOutStringBuffer = new StringBuffer("");                 //~@@@@R~
//    }                                                            //~@@@@R~
//*************************************************************************//~@@@@M~
//* init Service(issue accept)                                     //~@@@@M~
//* from BTStart and diffetred enableBT                            //~@@@@I~
//*************************************************************************//~@@@@I~
    public void setupChat() {                                      //~@@@@R~
        if (Dump.Y) Dump.println("BTC:setupChat");                 //~@@@@R~
                                                                   //~@@@@I~
//        // Initialize the BluetoothChatService to perform bluetooth connections//~@@@@I~
//        mChatService = new BluetoothChatService(this, mHandler); //~@@@@I~
	      mChatService = new BTService(activity, mHandler);        //~@@@@I~
    	startService();                                            //~@@@@I~
		return;                                                    //~@@@@R~
    }                                                              //~@@@@I~

//    @Override                                                    //~@@@@R~
//    public synchronized void onPause() {                         //~@@@@R~
//        super.onPause();                                         //~@@@@R~
//        if(D) Log.e(TAG, "- ON PAUSE -");                        //~@@@@R~
//    }                                                            //~@@@@R~

//    @Override                                                    //~@@@@R~
//    public void onStop() {                                       //~@@@@R~
//        super.onStop();                                          //~@@@@R~
//        if(D) Log.e(TAG, "-- ON STOP --");                       //~@@@@R~
//    }                                                            //~@@@@R~

//*************************************************************************//~@@@@I~
//*from Asgts                                                       //~@@@@R~//~v101R~
//*************************************************************************//~@@@@I~
//    @Override                                                    //~@@@@R~
//    public void onDestroy() {                                    //~@@@@R~
//        super.onDestroy();                                       //~@@@@R~
//        // Stop the Bluetooth chat services                      //~@@@@R~
//        if (mChatService != null) mChatService.stop();           //~@@@@R~
//        if(D) Log.e(TAG, "--- ON DESTROY ---");                  //~@@@@R~
//    }                                                            //~@@@@R~
    public void BTDestroy() {                                      //~@@@@R~
//        super.onDestroy();                                       //~@@@@I~
        // Stop the Bluetooth chat services                        //~@@@@I~
        if(Dump.Y) Dump.println("BTC destroy");                    //~@@@2I~
        if (mChatService != null) mChatService.stop();             //~@@@@I~
//        if(D) Log.e(TAG, "--- ON DESTROY ---");                  //~@@@@I~
    }                                                              //~@@@@I~


//*************************************************************************//~@@@@I~
    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
//    private void sendMessage(String message) {                   //~@@@@R~
//        // Check that we're actually connected before trying anything//~@@@@R~
////      if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {//~@@@@R~
//        if (mChatService.getState() != BTService.STATE_CONNECTED) {//~@@@@R~
////          Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();//~@@@@R~
//            AjagoView.showToast(R.string.not_connected);         //~@@@@R~
//            return;                                              //~@@@@R~
//        }                                                        //~@@@@R~

//        // Check that there's actually something to send         //~@@@@R~
//        if (message.length() > 0) {                              //~@@@@R~
//            // Get the message bytes and tell the BluetoothChatService to write//~@@@@R~
//            byte[] send = message.getBytes();                    //~@@@@R~
//            mChatService.write(send);                            //~@@@@R~

//            // Reset out string buffer to zero and clear the edit text field//~@@@@R~
//            mOutStringBuffer.setLength(0);                       //~@@@@R~
//            mOutEditText.setText(mOutStringBuffer);              //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~

//*************************************************************************//~@@@@I~
//*Handler on MainThread                                           //~@@@@I~
//*************************************************************************//~@@@@I~
    // The Handler that gets information back from the BluetoothChatService
//  private final Handler mHandler = new Handler() {               //~@@@@R~
    private static class BTHandler extends Handler {               //~@@@@R~
        @Override
        public void handleMessage(Message msg) {
        	try                                                    //~@@@@I~
            {                                                      //~@@@@I~
                switch (msg.what) {                                //~@@@@R~
                case MESSAGE_STATE_CHANGE:                         //~@@@@R~
                    if(Dump.Y) Dump.println("MESSAGE_STATE_CHANGE: " + msg.arg1);//~@@@@R~
                    switch (msg.arg1) {                            //~@@@@R~
    //              case BluetoothChatService.STATE_CONNECTED:     //~@@@@R~
                    case BTService.STATE_CONNECTED:                //~@@@@R~
    //                    mTitle.setText(R.string.title_connected_to);//~@@@@R~
    //                    mTitle.append(mConnectedDeviceName);     //~@@@@R~
    //                  Toast.makeText(activity.getApplicationContext(), "State:Connected to "+mConnectedDeviceName,Toast.LENGTH_SHORT).show();//~@@@@R~
//                        AView.showToast(R.string.title_connected_to,mConnectedDeviceName);//~@@@@R~//~v101R~
    //                    mConversationArrayAdapter.clear();       //~@@@@R~
                        break;                                     //~@@@@R~
    //              case BluetoothChatService.STATE_CONNECTING:    //~@@@@R~
                    case BTService.STATE_CONNECTING:               //~@@@@R~
    //                    mTitle.setText(R.string.title_connecting);//~@@@@R~
    //                  Toast.makeText(activity.getApplicationContext(), "State:Connecting",Toast.LENGTH_SHORT).show();//~@@@@R~
//                        AView.showToast(R.string.title_connecting);//~@@@@R~//~v101R~
                        break;                                     //~@@@@R~
    //              case BluetoothChatService.STATE_LISTEN:        //~@@@@R~
                    case BTService.STATE_LISTEN:                   //~@@@@R~
    //                  Toast.makeText(activity.getApplicationContext(), "State:Listen",Toast.LENGTH_SHORT).show();//~@@@@R~
                        break;                                     //~@@@@R~
    //              case BluetoothChatService.STATE_NONE:          //~@@@@R~
                    case BTService.STATE_NONE:                     //~@@@@R~
    //                    mTitle.setText(R.string.title_not_connected);//~@@@@R~
//                        AView.showToastLong(R.string.title_not_connected);//~@@@@R~//~v101R~
                        break;                                     //~@@@@R~
                    }                                              //~@@@@R~
                    break;                                         //~@@@@R~
    //            case MESSAGE_WRITE:                              //~@@@@R~
    //                byte[] writeBuf = (byte[]) msg.obj;          //~@@@@R~
    //                // construct a string from the buffer        //~@@@@R~
    //                String writeMessage = new String(writeBuf);  //~@@@@R~
    ////                mConversationArrayAdapter.add("Me:  " + writeMessage);//~@@@@R~
    //                break;                                       //~@@@@R~
    //            case MESSAGE_READ:                               //~@@@@R~
    //                byte[] readBuf = (byte[]) msg.obj;           //~@@@@R~
    //                // construct a string from the valid bytes in the buffer//~@@@@R~
    //                String readMessage = new String(readBuf, 0, msg.arg1);//~@@@@R~
    ////                mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);//~@@@@R~
    //                break;                                       //~@@@@R~
                case MESSAGE_DEVICE_NAME:                          //~@@@@R~
                    // save the connected device's name            //~@@@@R~
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);//~@@@@R~
    //              Toast.makeText(getApplicationContext(), "Connected to "//~@@@@R~
//                    AView.showToast(R.string.title_connected_to,mConnectedDeviceName);//~@@@@R~//~v101R~
                    AG.aBT.connected(BTService.mConnectedSocket,mConnectedDeviceName);//~@@@@R~
                    break;                                         //~@@@@R~
                case MESSAGE_TOAST:                                //~@@@@R~
    //              Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),//~@@@@R~
                    AView.showToast(R.string.dev_act_msg,msg.getData().getString(TOAST));//~@@@@R~
                    break;                                         //~@@@@R~
                case MESSAGE_FAILURE:                              //~@@@2I~
                	if (Dump.Y) Dump.println("MESSAGE_FAILURE="+msg.arg1);//~@@@2I~
                    AG.aBT.connFailed(msg.arg1);	               //~@@@2I~
                    break;                                         //~@@@2I~
                case MESSAGE_FAILUREACCEPT:                        //~v101I~
                	if (Dump.Y) Dump.println("MESSAGE_FAILUREACCEPT="+msg.arg1);//~v101I~
                    String secure=msg.getData().getString(ACCEPT_TYPE);//~v101I~
                    AG.aBT.acceptFailed(secure);                   //~v101R~
                    break;                                         //~v101I~
                }                                                  //~@@@@R~
            }                                                      //~@@@@I~
            catch(Exception e)                                     //~@@@@I~
            {                                                      //~@@@@I~
                Dump.println(e,"AjagoBTHandler:handleMessage");    //~@@@@I~
                AView.showToastLong(R.string.failedBluetooth);     //~@@@@R~
            }                                                      //~@@@@I~
        }
//  };                                                             //~@@@@R~
    } //BTHandler Class                                            //~@@@@M~

//*************************************************************************//~@@@@I~
//*from Asgts                                                       //~@@@@R~//~v101R~
//*************************************************************************//~@@@@I~
//  public void onActivityResult(int requestCode, int resultCode, Intent data) {//~@@@@R~
    public void BTActivityResult(int requestCode, int resultCode, Intent data) {//~@@@@R~
//        switch (requestCode) {                                   //~@@@@I~
//        case REQUEST_CONNECT_DEVICE:                             //~@@@@I~
//            // When DeviceListActivity returns with a device to connect//~@@@@I~
//            if (resultCode == Activity.RESULT_OK) {              //~@@@@I~
//                // Get the device MAC address                    //~@@@@I~
//                String address = data.getExtras()                //~@@@@I~
//                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);//~@@@@I~
//                // Get the BLuetoothDevice object                //~@@@@I~
//                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);//~@@@@I~
//                // Attempt to connect to the device              //~@@@@I~
//                mChatService.connect(device);                    //~@@@@I~
//            }                                                    //~@@@@I~
//            break;                                               //~@@@@I~
//        case REQUEST_ENABLE_BT:                                  //~@@@@I~
//            // When the request to enable Bluetooth returns      //~@@@@I~
//            if (resultCode == Activity.RESULT_OK) {              //~@@@@I~
//                // Bluetooth is now enabled, so set up a chat session//~@@@@I~
//                setupChat();                                     //~@@@@I~
//            } else {                                             //~@@@@I~
//                // User did not enable Bluetooth or an error occured//~@@@@I~
//                Log.d(TAG, "BT not enabled");                    //~@@@@I~
//                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();//~@@@@I~
//                finish();                                        //~@@@@I~
//            }                                                    //~@@@@I~
//        }                                                        //~@@@@I~
        if(Dump.Y) Dump.println("onActivityResult reqcode=" +requestCode+ ",resultcode="+ resultCode); //~@@@@R~//~1A60I~
        switch (requestCode) {
//        case REQUEST_CONNECT_DEVICE:                             //~1A60R~
//            // When DeviceListActivity returns with a device to connect//~1A60R~
//            if (resultCode == Activity.RESULT_OK) {              //~1A60R~
//                // Get the device MAC address                    //~1A60R~
//                String address = data.getExtras()                //~1A60R~
////                                 .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);//~@@@@R~//~1A60R~
////                                   .getString(BTList.EXTRA_DEVICE_ADDRESS);//~@@@@I~//~v101R~//~1A60R~
//                                   .getString(BTDiscover.EXTRA_DEVICE_ADDRESS);//~v101I~//~1A60R~
//                // Get the BLuetoothDevice object                //~1A60R~
//                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);//~1A60R~
//                // Attempt to connect to the device              //~1A60R~
//                mChatService.connect(device);                    //~1A60R~
//            }                                                    //~1A60R~
//            break;                                               //~1A60R~
//      case REQUEST_ENABLE_BT:                                    //~1A6aR~
        case AG.ACTIVITY_REQUEST_ENABLE_BT:                        //~1A6aI~
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
//                if (mChatService == null) setupChat();             //~@@@@I~//~v101R~
                AG.aBT.enabled();                                  //~@@@@R~
            } else {
                // User did not enable Bluetooth or an error occured
                if (Dump.Y) Dump.println("BT not enabled");        //~@@@@R~
	            AView.showToastLong(R.string.bt_not_enabled_leaving);  //~v107I~//~@@@@R~
            }
        }
    }

//*************************************************************************//~@@@@I~
    public boolean startBTActivity(int Pfuncid)                    //~@@@@I~
    {                                                              //~@@@@I~
        Intent intent=null;                                        //~@@@@R~
//      switch (item.getItemId()) {                                //~@@@@I~
        switch (Pfuncid) {                                         //~@@@@I~
        case BTA_ENABLE:                                           //~@@@@I~
            intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);//~@@@@I~
//          activity.startActivityForResult(intent, REQUEST_ENABLE_BT);//~@@@@I~//~1A6aR~
            activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_ENABLE_BT);//~1A6aI~
            return true;                                           //~@@@@I~
//        case BTA_SCAN:                                             //~@@@@R~//~v101R~
//            // Launch the DeviceListActivity to see devices and do scan//~@@@@I~//~v101R~
////          serverIntent = new Intent(this, DeviceListActivity.class);//~@@@@I~//~v101R~
//            intent = new Intent(activity,BTList.class);            //~@@@@R~//~v101R~
//            activity.startActivityForResult(intent, REQUEST_CONNECT_DEVICE);//~@@@@R~//~v101R~
//            return true;                                           //~@@@@I~//~v101R~
        case BTA_DISCOVERABLE:                                     //~@@@@R~
            intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);//~@@@@I~
//          intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);//~@@@@I~//~1AbHR~
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,DISCOVERABLE_DUARATION);//~1AbHR~
            activity.startActivity(intent);                        //~@@@@I~
            return true;                                           //~@@@@I~
        case BTA_DISCOVERABLE_STOP:                                //~1AbJI~
            intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);//~1AbJI~
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,STOP_DISCOVERABLE_DUARATION);//~1AbJI~
            activity.startActivity(intent);                        //~1AbJI~
            return true;                                           //~1AbJI~
        }                                                          //~@@@@I~
        return false;                                              //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~@@@@I~
//*rc:true:accept issued                                           //~v101I~
//***************************************************************************//~v101I~
//    public BluetoothSocket BTstartServer()                         //~@@@@R~//~v101R~
    public boolean BTstartServer()                                 //~v101I~
    {                                                              //~@@@@I~
//        BluetoothSocket socket=null;                             //~v101R~
        boolean rc=false;		//failed                           //~v101I~
    	int state;//~@@@@I~
        boolean acceptThreadStarted=false;                         //~@@@@I~
	//************************                                     //~@@@@I~
        if(Dump.Y) Dump.println("BTstartServer");                  //~@@@@I~
        if (BTCreate())	//!supported                               //~@@@@R~
        {                                                          //~@@@@I~
        	if (!BTStart())		//already enable adapter           //~@@@@R~
            {                                                      //~@@@@I~
    			state=mChatService.getState();                     //~@@@@I~
    			if (state==BTService.STATE_CONNECTED)              //~@@@@I~
                	AView.showToast(R.string.alreadyconnected);    //~@@@@R~
                else                                               //~@@@@I~
                {                                                  //~@@@@I~
//                    acceptThreadStarted=mChatService.acceptNext();  //restart AcceptThread//~@@@@R~//~v101R~
                    acceptThreadStarted=mChatService.start();  //restart AcceptThread//~v101I~
//                    if (acceptThreadStarted)                       //~@@@@R~//~v101R~
//                    {                                              //~@@@@R~//~v101R~
//                        AView.showToast(R.string.nowlistening);    //~@@@@R~//~v101R~
//                    }                                              //~@@@@R~//~v101R~
//                    else                                           //~@@@@R~//~v101R~
//                        AView.showToast(R.string.listening);       //~@@@@R~//~v101R~
					rc=true;                                       //~v101I~
                }                                                  //~@@@@I~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
        if(Dump.Y) Dump.println("BTstartServer acceptstarted="+acceptThreadStarted+",rc="+rc);//~@@@@R~//~v101R~
//        return socket;                                             //~@@@@R~//~v101R~
        return rc;                                                 //~v101I~
    }                                                              //~@@@@R~
////***************************************************************************//~@@@@I~//~v101R~
////*from AjaghoBT                                                   //~@@@@R~//~v101R~
////*rc:-1:BT not avail,1:duplicated connect                         //~@@@@R~//~v101R~
////***************************************************************************//~@@@@I~//~v101R~
//    public int BTconnect()                                         //~@@@@R~//~v101R~
//    {                                                              //~@@@@I~//~v101R~
//        int rc=0,state=-1;                                         //~@@@@R~//~v101R~
//    //*************************                                    //~@@@@I~//~v101R~
//        if(Dump.Y) Dump.println("BTconnect");                      //~@@@@I~//~v101R~
//        if (!BTCreate())                                           //~@@@@R~//~v101R~
//            rc=-1;                                                 //~@@@@R~//~v101R~
//        else                                                       //~@@@@I~//~v101R~
//            if (!BTStart())     //already enable adapter           //~@@@@I~//~v101R~
//            {                                                      //~@@@@I~//~v101R~
//                state=mChatService.getState();                     //~@@@@I~//~v101R~
//                if (state==BTService.STATE_LISTEN                 //~@@@@R~//~@@@2R~//~v101R~
//                ||  state==BTService.STATE_NONE)                   //~@@@2I~//~v101R~
//                {                                                  //~@@@@I~//~v101R~
//                    startBTActivity(BTA_SCAN);                     //~@@@@I~//~v101R~
//                }                                                  //~@@@@I~//~v101R~
//                else                                               //~@@@@I~//~v101R~
//                {                                                  //~@@@@I~//~v101R~
//                    rc=1;                                          //~@@@@I~//~v101R~
//                }                                                  //~@@@@I~//~v101R~
//            }                                                      //~@@@@I~//~v101R~
//        if(Dump.Y) Dump.println("BTconnect rc="+rc+",state="+state);//~@@@@R~//~v101R~
//        return rc;                                                 //~@@@@I~//~v101R~
//    }                                                              //~@@@@M~//~v101R~
//***************************************************************************//~@@@2I~//~v101R~
//*MAC addr specified connect                                      //~v101R~
//***************************************************************************//~@@@2I~//~v101R~
//  public int BTconnect(String Paddr)                             //~v101R~//~1A60R~
    public int BTconnect(String Paddr,boolean Psecure)             //~1A60I~
    {                                                              //~v101R~
        int rc=0,state=-1;                                         //~v101R~
    //*************************                                    //~v101R~
        if(Dump.Y) Dump.println("BTconnect addr="+Paddr);          //~v101R~
        if (!BTCreate())                                           //~v101R~
            rc=-1;                                                 //~v101R~
        else                                                       //~v101R~
            if (!BTStart())     //already enable adapter           //~v101R~
            {                                                      //~v101R~
                state=mChatService.getState();                     //~v101R~
                if (state==BTService.STATE_LISTEN                  //~v101R~
                ||  state==BTService.STATE_NONE)                   //~v101R~
                {                                                  //~v101R~
                	BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(Paddr);//~v101I~
//                  mChatService.connect(device);                  //~v101R~//~1A60R~
                    mChatService.connect(device,Psecure);          //~1A60I~
                }                                                  //~v101R~
                else                                               //~v101R~
                {                                                  //~v101R~
                    rc=1;                                          //~v101R~
                }                                                  //~v101R~
            }                                                      //~v101R~
        if(Dump.Y) Dump.println("BTconnect rc="+rc+",state="+state);//~v101R~
        return rc;                                                 //~v101R~
    }                                                              //~v101R~
//*************************************************************************//~@@@@M~
//*ret -1:not discoverable,1:requested discoverable,0:discoverable //~@@@@M~
//*************************************************************************//~@@@@M~
    public int BTensureDiscoverable(boolean PrequestSet)           //~@@@@R~
	{                                                              //~@@@@M~
    	int rc=0;                                                  //~@@@@M~
    //*********************                                        //~@@@@M~
        if(Dump.Y) Dump.println("ensure discoverable req="+PrequestSet);//~@@@@M~
        if (!BTCreate())                                           //~@@@@I~
            rc=-1;                                                 //~@@@@I~
        else                                                       //~@@@@I~
        {                                                          //~1A6aI~
	        if(Dump.Y) Dump.println("ensure discoverable scanmoed="+mBluetoothAdapter.getScanMode());//~1A6aI~
        if (mBluetoothAdapter.getScanMode()!=BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)//~@@@@M~
		{                                                          //~@@@@M~
        	if (PrequestSet)                                       //~@@@@M~
            {                                                      //~@@@@M~
            	startBTActivity(BTA_DISCOVERABLE);                 //~@@@@M~
                rc=1;                                              //~@@@@M~
            }                                                      //~@@@@M~
            else                                                   //~@@@@M~
            	rc=-1;                                             //~@@@@R~
        }                                                          //~@@@@M~
        }                                                          //~1A6aI~
        return rc;                                                 //~@@@@I~
    }                                                              //~@@@@M~
//*************************************************************************//+1AbHI~
//*************************************************************************//+1AbHI~
    public int BTisDiscoverable()                                  //+1AbHI~
	{                                                              //+1AbHI~
    	int rc=0;                                                  //+1AbHI~
    //*********************                                        //+1AbHI~
        if(Dump.Y) Dump.println("BTC:BTisDiscoverable");           //+1AbHI~
        if (!BTCreate())                                           //+1AbHI~
            rc=-1;                                                 //+1AbHI~
        else                                                       //+1AbHI~
        {                                                          //+1AbHI~
	        if(Dump.Y) Dump.println("ensure discoverable scanmoed="+mBluetoothAdapter.getScanMode());//+1AbHI~
        	if (mBluetoothAdapter.getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)//+1AbHI~
			{                                                      //+1AbHI~
        		rc=1;                                              //+1AbHI~
        	}                                                      //+1AbHI~
        }                                                          //+1AbHI~
        return rc;                                                 //+1AbHI~
    }                                                              //+1AbHI~
//*************************************************************************//~1AbJI~
    public int BTstopDiscoverable()                                //~1AbJI~
	{                                                              //~1AbJI~
    	int rc=0;                                                  //~1AbJI~
    //*********************                                        //~1AbJI~
        if(Dump.Y) Dump.println("stop discoverable");              //~1AbJI~
        if (!BTCreate())                                           //~1AbJI~
            rc=-1;                                                 //~1AbJI~
        else                                                       //~1AbJI~
        {                                                          //~1AbJI~
	        if(Dump.Y) Dump.println("stop discoverable scanmode="+mBluetoothAdapter.getScanMode());//~1AbJR~
	        if (mBluetoothAdapter.getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)//~1AbJR~
            {                                                      //~1AbJI~
		        if(Dump.Y) Dump.println("stop discoverable request duration=1");//~1AbJI~
            	startBTActivity(BTA_DISCOVERABLE_STOP);            //~1AbJI~
                rc=1;                                              //~1AbJI~
            }                                                      //~1AbJI~
        }                                                          //~1AbJI~
        return rc;                                                 //~1AbJI~
    }                                                              //~1AbJI~
//*************************************************************************//~@@@@I~
    public String getDeviceName()                                  //~@@@@R~
    {                                                              //~@@@@I~
	    return (mLocalDeviceName!=null?mLocalDeviceName:"");       //~@@@@R~
    }                                                              //~@@@@I~
//*************************************************************************//~@@@@I~
    public void connectionLost()                                   //~@@@@I~
    {                                                              //~@@@@I~
        if (mChatService != null)                                  //~@@@@I~
        {                                                          //~@@@@I~
	    	mChatService.connectionLost();                        //~@@@@I~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~
//*************************************************************************//~@@@@I~
    public boolean isConnectionAlive()                                //~@@@@I~
    {                                                              //~@@@@I~
        return (mChatService!=null) && (mChatService.getState()==BTService.STATE_CONNECTED);//~@@@@I~
    }                                                              //~@@@@I~
//*************************************************************************//~@@@2I~
    public boolean stopListen()                                    //~@@@2I~
    {                                                              //~@@@2I~
        return (mChatService!=null) && (mChatService.stopListen());//~@@@2I~
    }                                                              //~@@@2I~
//*************************************************************************//~v101I~
    public boolean discover(DoActionListener Pdal)                 //~v101I~
    {                                                              //~v101I~
        BTDiscover.discover(Pdal);                                 //~v101R~
		return true;//~v101R~
    }                                                              //~v101I~
//*************************************************************************//~v101I~
    public boolean cancelDiscover()                                //~v101I~
    {                                                              //~v101I~
        BTDiscover.cancelDiscover();                           //~v101I~
		return true;                                               //~v101I~
    }                                                              //~v101I~
//*************************************************************************//~v101I~
    public void requestDiscover(BluetoothAdapter Padapter,int Pdiscover)//~v101I~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("BTControl:requestDiscover Pdiscover="+Pdiscover+",mChatService="+mChatService);//~1AbHI~
        BTService.requestDiscover(mChatService,Padapter,Pdiscover);//~v101R~
    }                                                              //~v101I~
//*************************************************************************//~v101I~
    public String[] getPairedDeviceList()                          //~v101I~
    {                                                              //~v101I~
        return BTService.getPairedDeviceList(mChatService);        //~v101R~
    }                                                              //~v101I~
}