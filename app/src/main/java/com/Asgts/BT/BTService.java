//*CID://+1AebR~:                             update#=  111;       //+1AebR~
//********************************************************************************//~v101I~
//1Aeb 2015/07/26 When Ahsv BT conecction active Ajagoc BT connection fail by "RFCOMM_CreateConnection - already opened state:2, RFC state:4, MCB state:5"//+1AebI~
//1AbZ 2015/07/05 BT:try for pairing loop;cancel discovery not before connect but after connected//~1AbZI~
//1AbN 2015/07/03 BT:(BUG)"ASSERT btif_dm.c unhandled search devicve"(cancelDiscovery at not discovering status)//~1AbNI~
//1AbK 2015/07/02 1AbJ popup dialog to accept duartion change,so do not call stopDiscover//~1AbKI~
//1AbJ 2015/07/02 stop discoverable when connection complete       //~1AbJI~
//1AbF 2015/06/28 Bluetooth ramins status of Pairing for InSecure connection;try same NAME for both Secure and Insecure case//~1AbFI~
//1Abt 2015/06/18 BT:Accept not both of Secure/Insecure but one of two.//~1AbtI~
//1Abc 2015/06/15 BT:no need to issue accept for connection request side//~1AbcI~
//1Abb 2015/06/15 NFCBT:try insecure only(if accepting both,connect insecure request is accepted by secure socket)//~1AbbI~
//1A64 2015/01/27 socket.connect fails by "IOException:service discovery failed". inserter 300ms sleep before connect()//~1A64I~
//                issue confirm msg that Accept was issued on another dvice//~1A64I~
//1A60 2015/01/25 also support Bluetooth secure SPP(Bluetooth 2.1) //~1A60I~
//101c 2013/02/05 try to insecure bluetooth                        //~v101I~
//********************************************************************************//~v101I~
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.R;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
//import android.util.Log;                                         //~@@@@R~

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
//public class BluetoothChatService {                              //~@@@@R~
public class BTService {                                           //~@@@@I~
    // Debugging
//    private static final String TAG = "BluetoothChatService";
//    private static final boolean D = true;

    // Name for the SDP record when creating server socket
//    private static final String NAME = "BluetoothChat";          //~v101R~
//  private static final String NAME_SECURE = "BluetoothChatSecure";//~v101I~//~1AbFR~
//  private static final String NAME_INSECURE = "BluetoothChatInsecure";//~v101I~//~1AbFR~
    private static final String NAME_SECURE = AG.appName+"_Bluetooth";//~1AbFI~
    private static final String NAME_INSECURE = AG.appName+"_Bluetooth";//~1AbFI~
    private static final String CONFIRM_ACCEPT="Service discovery failed";//~1A64I~

    // Unique UUID for this application
//  private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");//~@@@@R~
//  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//~2C04R~//~@@@@I~//~v101R~
    private static final UUID MY_UUID_SECURE =                     //~v101I~
//        UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66"); //~v101I~
          UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //~v101I~
    private static final UUID MY_UUID_INSECURE =                   //~v101I~
//        UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66"); //~v101I~
          UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //~v101I~

    // Member fields
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private AcceptThread mAcceptThreadInSecure;                    //~v101I~
    private ConnectThread mConnectThread;
//    private ConnectedThread mConnectedThread;                    //~@@@@R~
    private int mState;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device
    public static final int CONN_FAILED=1;                         //~@@@2I~
    public static final int CONN_LOST=2;                           //~@@@2I~
    public static BluetoothSocket  mConnectedSocket;               //~@@@@R~
//  private BluetoothServerSocket mmServerSocket;            //~@@@@I~//~v101R~

    /**
     * Constructor. Prepares a new BluetoothChat session.
     * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
     */
//  public BluetoothChatService(Context context, Handler handler) {//~@@@@R~
    public BTService(Context context, Handler handler) {           //~@@@@I~
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }

    /**
     * Set the current state of the chat connection
     * @param state  An integer defining the current connection state
     */
//  private synchronized void setState(int state) {                //~v101R~
    private void setState(int state) {                             //~v101I~
//      if (D) Log.d(TAG, "setState() " + mState + " -> " + state);//~@@@@R~
        if (Dump.Y) Dump.println("setState() SYNC " + mState + " -> " + state);//~@@@@I~//~v101R~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~
        mState = state;
	    if (AG.aBT.swDestroy)                                      //~@@@2I~
        	return;                                                //~@@@2I~
        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(BTControl.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();//~@@@@R~
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("setState() SYNC return");        //~v101I~
    }
    private void notifyFailure(int flag)              //~@@@2I~    //~v101R~
    {                                                              //~@@@2I~
        if (Dump.Y) Dump.println("Notifyfailure SYNC");                 //~@@@2I~//~v101R~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~
	    if (AG.aBT.swDestroy)                                      //~@@@2I~
        	return;                                                //~@@@2I~
        mHandler.obtainMessage(BTControl.MESSAGE_FAILURE, flag, -1).sendToTarget();//~@@@2I~
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("notifyFailure SYNC return");     //~v101I~
    }                                                              //~@@@2I~
    private void notifyFailureAccept(String Pinfo)                 //~v101R~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("NotifyfailureAccept SYNC");      //~v101I~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~
	    if (AG.aBT.swDestroy)                                      //~v101I~
        	return;                                                //~v101I~
        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_FAILUREACCEPT);//~v101I~
        Bundle bundle = new Bundle();                              //~v101I~
        bundle.putString(BTControl.ACCEPT_TYPE,Pinfo);              //~v101I~
        msg.setData(bundle);                                       //~v101I~
        mHandler.sendMessage(msg);                                 //~v101I~
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("notifyFailureAccept SYNC return");//~v101I~
    }                                                              //~v101I~

    /**
     * Return the current connection state. */
//  public synchronized int getState() {                           //~v101R~
    public int getState() {                                        //~v101I~
        if (Dump.Y) Dump.println("getState() SYNC " + mState );    //~v101I~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~
          if (Dump.Y) Dump.println("getState() SYNC return");         //~v101I~
        return mState;
      }                                                            //~v101I~
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
//  public synchronized void start() {                             //~v101R~
    public boolean start() {                                       //~v101R~
//      if (D) Log.d(TAG, "start");                                //~@@@@R~
        if (Dump.Y) Dump.println("BTService SYNC:start()");               //~@@@@I~//~v101R~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~

        // Start the thread to listen on a BluetoothServerSocket
       if (!AG.aBT.swConnect)                                      //~1AbcI~
       {                                                           //~1AbcI~
        if (mAcceptThread == null) {
//          mAcceptThread = new AcceptThread();                    //~@@@@R~
//          mAcceptThread.start();                                 //~@@@@R~
			acceptNext();                                          //~@@@@I~
        }
        if (mAcceptThreadInSecure == null) {                       //~v101I~
			acceptNextInSecure();                                  //~v101I~
        }                                                          //~v101I~
        setState(STATE_LISTEN);
       }                                                           //~1AbcI~
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("BTService SYNC:start() return swConnect="+AG.aBT.swConnect); //~v101I~//~1AbcR~
        return (mAcceptThread!=null||mAcceptThreadInSecure!=null); //~v101I~
    }
//********************************************************************//~@@@2R~
    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     */
//    public synchronized void connect(BluetoothDevice device) {   //~v101R~
//  public synchronized void connect(BluetoothDevice device)       //~v101R~
//    public  void connect(BluetoothDevice device)                   //~v101I~//~1A60R~
//    {                                                              //~v101I~//~1A60R~
//        if (Dump.Y) Dump.println("BTService connect() SYNC:start");//~v101I~//~1A60R~
//      synchronized(this)                                           //~v101I~//~1A60R~
//      {                                                            //~v101I~//~1A60R~
//        connect(device,false);                   //~v101I~       //~1A60R~
//      }                                                            //~v101I~//~1A60R~
//        if (Dump.Y) Dump.println("BTService connect() SYNC:return");//~v101I~//~1A60R~
//    }                                                              //~v101I~//~1A60R~
//  public synchronized void connect(BluetoothDevice device, boolean secure) {//~v101R~
    public void connect(BluetoothDevice device, boolean secure) {//~v101I~
//      if (D) Log.d(TAG, "connect to: " + device);                //~@@@@R~
        if (Dump.Y) Dump.println("BTService:SYNC connect connect to: " + device);//~@@@@I~//~v101R~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~

        // Start the thread to connect with the given device
//      mConnectThread = new ConnectThread(device);                //~v101R~
        mConnectThread = new ConnectThread(device,secure);         //~v101I~
        mConnectThread.start();
        setState(STATE_CONNECTING);
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("BTService:SYNC connect return"); //~v101I~
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
//  public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {//~v101R~
    public void connected(BluetoothSocket socket, BluetoothDevice device) {//~v101I~
//      if (D) Log.d(TAG, "connected");                            //~@@@@R~
        if (Dump.Y) Dump.println("BTService:SYNC connected start");           //~@@@@I~//~v101R~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (Dump.Y) Dump.println("BTService:connected, after connectthread cancel");//~@@@2I~//~v101R~

        // Cancel any thread currently running a connection
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~

        // Cancel the accept thread because we only want to connect to one device
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        if (Dump.Y) Dump.println("BTService:connected after acceptthread cancel");//~@@@2I~//~v101R~
                                                                   //~v101I~
        if (mAcceptThreadInSecure != null) {mAcceptThreadInSecure.cancel(); mAcceptThreadInSecure = null;}//~v101I~
        if (Dump.Y) Dump.println("BTService:connected after acceptthreadInSecure cancel");//~v101R~

        // Start the thread to manage the connection and perform transmissions
//        mConnectedThread = new ConnectedThread(socket);          //~@@@@R~
//        mConnectedThread.start();                                //~@@@@R~

        // Send the name of the connected device back to the UI Activity
        mConnectedSocket=socket;  //before sendMessage             //~@@@@I~
        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_DEVICE_NAME);//~@@@@R~
        Bundle bundle = new Bundle();
        bundle.putString(BTControl.DEVICE_NAME, device.getName()); //~@@@@R~
        msg.setData(bundle);
        if (Dump.Y) Dump.println("BTService:before sendmsg");      //~@@@2I~
        mHandler.sendMessage(msg);

        if (Dump.Y) Dump.println("BTService:connected return");    //~@@@2I~
        setState(STATE_CONNECTED);
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("BTService:SYNC connected return");//~v101I~
    }

    /**
     * Stop all threads
     */
//  public synchronized void stop() {                              //~v101R~
    public void stop() {                                           //~v101I~
//      if (D) Log.d(TAG, "stop");                                 //~@@@@R~
        if (Dump.Y) Dump.println("BTService:stop SYNC");                //~@@@@I~//~v101R~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~
        if (mConnectThread != null)                                //~@@@2R~
		{                                                          //~@@@2I~
        	if (Dump.Y) Dump.println("BTService:cancel connectThread");//~@@@2I~
			mConnectThread.cancel(); mConnectThread = null;        //~@@@2I~
		}                                                          //~@@@2I~
//        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//~@@@@R~
        if (mAcceptThread != null)                                 //~@@@2R~
		{                                                          //~@@@2I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThread");//~@@@2I~
			mAcceptThread.cancel(); mAcceptThread = null;         //~@@@2I~
		}                                                          //~@@@2I~
        if (mAcceptThreadInSecure != null)                         //~v101I~
		{                                                          //~v101I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThreadInSecure");//~v101I~
			mAcceptThreadInSecure.cancel(); mAcceptThreadInSecure = null;//~v101I~
		}                                                          //~v101I~
        setState(STATE_NONE);
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("BTService:stop SYNC return");    //~v101I~
    }

//    /**                                                          //~@@@@R~
//     * Write to the ConnectedThread in an unsynchronized manner  //~@@@@R~
//     * @param out The bytes to write                             //~@@@@R~
//     * @see ConnectedThread#write(byte[])                        //~@@@@R~
//     */                                                          //~@@@@R~
//    public void write(byte[] out) {                              //~@@@@R~
//        // Create temporary object                               //~@@@@R~
//        ConnectedThread r;                                       //~@@@@R~
//        // Synchronize a copy of the ConnectedThread             //~@@@@R~
//        synchronized (this) {                                    //~@@@@R~
//            if (mState != STATE_CONNECTED) return;               //~@@@@R~
//            r = mConnectedThread;                                //~@@@@R~
//        }                                                        //~@@@@R~
//        // Perform the write unsynchronized                      //~@@@@R~
//        r.write(out);                                            //~@@@@R~
//    }                                                            //~@@@@R~

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        if (AG.aBT.swDestroy)                                      //~@@@2I~
        	return;                                                //~@@@2I~
    	notifyFailure(CONN_FAILED);                                 //~@@@2I~
        setState(STATE_LISTEN);

        // Send a failure message back to the Activity
//        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_TOAST);//~@@@@R~//~v101R~
//        Bundle bundle = new Bundle();                            //~v101R~
//        bundle.putString(BTControl.TOAST, "Unable to connect device");//~@@@@R~//~v101R~
//        msg.setData(bundle);                                     //~v101R~
//        mHandler.sendMessage(msg);                               //~v101R~
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
//  private void connectionLost() {                                //~@@@@R~
    public  void connectionLost() {                                //~@@@@I~
        if (AG.aBT.swDestroy)                                      //~@@@2I~
            return;                                                //~@@@2I~
    	notifyFailure(CONN_LOST);                                   //~@@@2I~
        setState(STATE_LISTEN);
        if (AG.aBT.swDestroy)                                         //~@@@2I~
            return;                                                //~@@@2I~
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(BTControl.MESSAGE_TOAST);//~@@@@R~
        Bundle bundle = new Bundle();
        bundle.putString(BTControl.TOAST, "Device connection was lost");//~@@@@R~
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
//      public final BluetoothServerSocket mmServerSocket;        //~@@@@R~//~v101R~//+1AebR~
        private BluetoothServerSocket mmServerSocket;              //+1AebI~
        private String mSocketType;                                //~v101I~
        private boolean swCancel=false;                            //~@@@@I~
        private boolean acceptSecure;                              //~v101I~

//      public AcceptThread() {                                    //~v101R~
//      public AcceptThread(){this(false);}                        //~v101I~//~1A64R~
        public AcceptThread(boolean secure) {                      //~v101I~
            BluetoothServerSocket tmp = null;
            mSocketType = secure ? "Secure":"Insecure";            //~v101I~
            acceptSecure=secure;                                   //~v101I~

            // Create a new listening server socket
            try {
//              tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);//~v101R~
              	if (secure)                                        //~v101I~
		        	tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);//~v101I~
                else                                               //~v101I~
		        	tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);//~v101I~
            } catch (IOException e) {
//              Log.e(TAG, "listen() failed", e);                  //~@@@@R~
                Dump.println(e,"AcceptThread:listenUsingRfcommWithServiceRecord");//~@@@@I~
                // Send a failure message back to the Activity     //~@@@@I~
//                Message msg = mHandler.obtainMessage(BTControl.MESSAGE_TOAST);//~@@@@I~//~v101R~
//                Bundle bundle = new Bundle();                      //~@@@@I~//~v101R~
//                bundle.putString(BTControl.TOAST, "Accept Failed,Adaptor may be closed,reboot may be required");//~@@@@I~//~v101R~
//                msg.setData(bundle);                               //~@@@@I~//~v101R~
//                mHandler.sendMessage(msg);                         //~@@@@I~//~v101R~
			    notifyFailureAccept(mSocketType);                  //~v101R~
            }
            mmServerSocket = tmp;
        }

        public void run() {
//          if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);       //~@@@@R~
            if (Dump.Y) Dump.println("AcceptThread:run() BEGIN mAcceptThread" + this);//~@@@@I~
//            setName("AcceptThread");                             //~v101R~
            setName("AcceptThread" + mSocketType);                 //~v101I~
            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
	            	if (Dump.Y) Dump.println("AcceptThread:accept() secure="+acceptSecure+",mmServerSocket="+mmServerSocket.toString());//~@@@2I~//~1AbcR~
                    AG.RemoteStatusAccept=(acceptSecure?AG.RS_BTLISTENING_SECURE:AG.RS_BTLISTENING_INSECURE);                //~@@@2R~//~v101R~
                    socket = mmServerSocket.accept();
                    AG.RemoteStatusAccept&=~(acceptSecure?AG.RS_BTLISTENING_SECURE:AG.RS_BTLISTENING_INSECURE);//~v101I~
	            	if (Dump.Y) Dump.println("AcceptThread:accept() accepted socket="+socket.toString()+",secure="+acceptSecure);//~@@@2I~//~v101R~
//              } catch (IOException e) {                          //~@@@2R~
                } catch (Exception e) {                            //~@@@2I~
//                  Log.e(TAG, "accept() failed", e);              //~@@@@R~
                    AG.RemoteStatusAccept&=~(acceptSecure?AG.RS_BTLISTENING_SECURE:AG.RS_BTLISTENING_INSECURE);//~v101I~
					if (swCancel)                                  //~@@@@I~
                    {                                              //~@@@@I~
	                    if (Dump.Y) Dump.println("AcceptThread:serverSocket accept canceled");//~@@@@I~
                    }                                              //~@@@@I~
                    else                                           //~@@@@I~
                    {                                              //~@@@2I~
	                    Dump.println(e,"AcceptThread:run accept()");//~@@@@R~
                    }                                              //~@@@2M~
//libc free crash                        try {                     //~@@@2R~
//                            if (Dump.Y) Dump.println("AcceptThread mmServerSoket close()="+mmServerSocket.toString());//~@@@2R~
//                            mmServerSocket.close();              //~@@@2R~
//                        } catch (Exception ex) {                 //~@@@2R~
//                              Dump.println(ex,"AcceptThread:Server Socket Close at IOException");//~@@@2R~
//                        }                                        //~@@@2R~
                    break;
                }
                try                                                //+1AebI~
                {                                                  //+1AebI~
                    if (Dump.Y) Dump.println("AcceptThread mmServerSoket close()="+mmServerSocket.toString());//+1AebI~
                    mmServerSocket.close();                        //+1AebI~
                } catch (Exception ex) {                           //+1AebI~
                      Dump.println(ex,"AcceptThread:Server Socket Close at IOException");//+1AebI~
                }                                                  //+1AebI~
                mmServerSocket=null;                               //+1AebI~

                // If a connection was accepted
		        if (Dump.Y) Dump.println("AcceptThread:cancel discovery after accepted");//~1AbZI~
				requestDiscover(mAdapter,0/*not start discovery*/); //~1AbZI~
                if (socket != null) {
            		if (Dump.Y)  Dump.println("acceptThread run SYNC accepted start");//~v101R~
                    synchronized (BTService.this) {                //~@@@@R~
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
            				if (Dump.Y)  Dump.println("acceptThread run connected socket="+socket.toString());//~@@@2I~
                            if (acceptSecure)                      //~1A60I~
						        AView.showToastLong(AG.resource.getString(R.string.AcceptedBTSecureConnection,socket.getRemoteDevice().getName()));//~1A60I~
                            else                                   //~1A60I~
						        AView.showToastLong(AG.resource.getString(R.string.AcceptedBTInSecureConnection,socket.getRemoteDevice().getName()));//~1A60I~
                            connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
            				    if (Dump.Y)  Dump.println("acceptThread run connected,but close() by mstate="+mState+",socket="+socket.toString());//~@@@2R~
                                socket.close();
                            } catch (IOException e) {
//                              Log.e(TAG, "Could not close unwanted socket", e);//~@@@@R~
                                Dump.println(e,"AcceptThread:run Could not close unwanted socket");//~@@@@I~
                            }
                            break;
                        }
                    }
            		if (Dump.Y)  Dump.println("acceptThread run SYNC accepted exit mState="+mState);//~v101I~//~1AbFR~
//                  AG.aBT.mBTC.BTstopDiscoverable();              //~1AbJR~//~1AbKR~
                }
            }
//          if (D) Log.i(TAG, "END mAcceptThread");                //~@@@@R~
            if (Dump.Y) Dump.println("END mAcceptThread");         //~@@@@I~
        }

        public void cancel() {
//          if (D) Log.d(TAG, "cancel " + this);                   //~@@@@R~
            if (Dump.Y) Dump.println("AcceptThread:cancel " + this);//~@@@@I~
            if (mmServerSocket==null)                              //~v101I~
            {                                                      //~v101I~
	            if (Dump.Y) Dump.println("AcceptThread:cancel mmserversocket:null");//~v101I~
            	return;                                            //~v101I~
            }                                                      //~v101I~
            if (Dump.Y) Dump.println("AcceptThread cancel() close() serversocket="+mmServerSocket.toString());//~@@@2R~
            try {
                swCancel=true;                                     //~@@@@I~
                mmServerSocket.close();
            } catch (IOException e) {
//                Log.e(TAG, "close() of server failed", e);       //~@@@@R~
                  Dump.println(e,"AcceptThread:cancel:close");     //~@@@@I~
            }
        }
    }

//***************************************************************************//~@@@2I~
    @TargetApi(15)                                                 //~1AbNI~
    private void displayMyUuid(BluetoothDevice Pdevice)              //~1AbNI~
    {                                                              //~1AbNI~
    	ParcelUuid[] uuids=Pdevice.getUuids();                     //~1AbNI~//~1AbZR~
    	for (int ii=0;ii<uuids.length;ii++)                        //~1AbNI~
        	if (Dump.Y) Dump.println("BTService connectThread my uuid"+ii+"="+uuids[ii].getUuid());//~1AbNI~
    }                                                              //~1AbNI~
    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;                                //~v101I~
        private boolean swCancel=false;                            //~@@@2I~
        private boolean swSecure;                                  //~1A60I~

//      public ConnectThread(BluetoothDevice device) {             //~v101R~
        public ConnectThread(BluetoothDevice device,boolean secure) {//~v101I~
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";          //~v101I~
            swSecure=secure;                                       //~1A60I~
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
//          displayMyUuid(device);//@@@@test                       //~1AbNR~
	        if (Dump.Y) Dump.println("ConnectThread:createRfComm secure="+secure);//~v101I~
            try {
//                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);//~v101R~
	                if (secure)                                    //~v101I~
  	              		tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);//~v101I~
                    else                                           //~v101I~
  	              		tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);//~v101I~
            } catch (IOException e) {
//              Log.e(TAG, "create() failed", e);                  //~@@@@R~
                Dump.println(e,"ConnectThread:createRfcommSocketToServiceRecord");//~@@@@I~
            }
            mmSocket = tmp;
            if (Dump.Y) Dump.println("BTService connectThread connected mmSocket="+mmSocket.toString());//~@@@2I~
        }

        public void run() {
//          Log.i(TAG, "BEGIN mConnectThread");                    //~@@@@R~
            if (Dump.Y) Dump.println("ConnectThread:BEGIN mConnectThread");//~@@@@I~
//            setName("ConnectThread");                            //~v101R~
            setName("ConnectThread" + mSocketType);                //~v101I~

            // Always cancel discovery because it will slow down a connection
	          if (Dump.Y) Dump.println("ConnectThread:connect() cancel discovery before connect discovering="+mAdapter.isDiscovering());//~1AbNR~
			if (mAdapter.isDiscovering())                          //~1AbNR~
			{                                                      //~1AbNR~
              mAdapter.cancelDiscovery();                          //~@@@2R~
              try                                                  //~1A64I~
              {                                                    //~1A64I~
              	Thread.sleep(300); //300ms                         //~1A64I~
              }                                                    //~1A64I~
              catch(InterruptedException e)                        //~1A64I~
              {                                                    //~1A64I~
              }                                                    //~1A64I~
	          if (Dump.Y) Dump.println("ConnectThread:connect() cancel discovery end before connect");//~v101I~
//            AG.aBT.mBTD.cancelDiscover(mAdapter);                //~@@@2R~
            }                                                      //~1AbNR~

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
	            if (Dump.Y) Dump.println("ConnectThread:connect() mmSocket="+mmSocket.toString());//~@@@2I~//~v101R~
                mmSocket.connect();
	            if (Dump.Y) Dump.println("ConnectThread:connect() returned");//~v101I~
//          } catch (IOException e) {                              //~@@@2R~
            } catch (Exception e) {                                //~@@@2I~
                if (!swCancel)                                     //~@@@2I~
	                Dump.println(e,"BTService:ConnectThread:run:connect");//~@@@@I~//~@@@2R~
		        String exceptionMsg=e.getMessage();                //~1A64I~
    			if (exceptionMsg.equals(CONFIRM_ACCEPT))	//"Service discovery failed"//~1A64I~
					AView.showToastLong(R.string.ErrConnectConfirmAccept);//~1A64I~
                connectionFailed();                                //~@@@2R~
//              if (!swCancel)                                     //~@@@2R~
//              {                                                  //~@@@2R~
                // Close the socket
	            if (Dump.Y) Dump.println("BTService:ConnectThread:mmSocket close()="+mmSocket.toString());//~@@@2I~
                try {
                    mmSocket.close();
                } catch (IOException e2) {
//                  Log.e(TAG, "unable to close() socket during connection failure", e2);//~@@@@R~
                	Dump.println(e,"BTService:ConnectThread:run:close");//~@@@@I~
                }
                // Start the service over to restart listening mode
//              }                                                  //~@@@2R~
//                BTService.this.start();                            //~@@@@R~//~@@@2R~
	            if (Dump.Y) Dump.println("BTService:ConnectThread:mmSocket closeed");//~v101I~
                return;
            }
	        if (Dump.Y) Dump.println("ConnectThread:cancel discovery after connected");//~1AbZI~
			requestDiscover(mAdapter,0/*not start discovery*/);     //~1AbZI~

            // Reset the ConnectThread because we're done
            if (Dump.Y)  Dump.println("connectThread run SYNC connected");//~v101I~
            synchronized (BTService.this) {                        //~@@@@R~
                mConnectThread = null;
            }
            if (Dump.Y)  Dump.println("connectThread run SYNC connected exit");//~v101I~

            // Start the connected thread
            if (Dump.Y)  Dump.println("connectThread run connected socket="+mmSocket.toString());//~@@@2I~
            if (swSecure)                                          //~1A60I~
				AView.showToastLong(AG.resource.getString(R.string.ConnectedBTSecureConnection,mmDevice.getName()));//~1A60I~
            else                                                   //~1A60I~
				AView.showToastLong(AG.resource.getString(R.string.ConnectedBTInSecureConnection,mmDevice.getName()));//~1A60I~
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
            	if (Dump.Y) Dump.println("ConnectThread cancel() close socket="+mmSocket.toString());//~@@@2I~
                swCancel=true;                                     //~@@@2I~
                mmSocket.close();
            } catch (IOException e) {
//              Log.e(TAG, "close() of connect socket failed", e); //~@@@@R~
                Dump.println(e,"ConnectThread:cancel close()");    //~@@@@I~
            }
            if (Dump.Y) Dump.println("ConnectThread cancel() closed");//~v101I~
        }
    }
//***************************************************************************//~@@@2R~
    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
//    private class ConnectedThread extends Thread {               //~@@@@R~
//        private final BluetoothSocket mmSocket;                  //~@@@@R~
//        private final InputStream mmInStream;                    //~@@@@R~
//        private final OutputStream mmOutStream;                  //~@@@@R~

//        public ConnectedThread(BluetoothSocket socket) {         //~@@@@R~
//            Log.d(TAG, "create ConnectedThread");                //~@@@@R~
//            mmSocket = socket;                                   //~@@@@R~
//            InputStream tmpIn = null;                            //~@@@@R~
//            OutputStream tmpOut = null;                          //~@@@@R~

//            // Get the BluetoothSocket input and output streams  //~@@@@R~
//            try {                                                //~@@@@R~
//                tmpIn = socket.getInputStream();                 //~@@@@R~
//                tmpOut = socket.getOutputStream();               //~@@@@R~
//            } catch (IOException e) {                            //~@@@@R~
//                Log.e(TAG, "temp sockets not created", e);       //~@@@@R~
//            }                                                    //~@@@@R~

//            mmInStream = tmpIn;                                  //~@@@@R~
//            mmOutStream = tmpOut;                                //~@@@@R~
//        }                                                        //~@@@@R~

//        public void run() {                                      //~@@@@R~
//            Log.i(TAG, "BEGIN mConnectedThread");                //~@@@@R~
//            byte[] buffer = new byte[1024];                      //~@@@@R~
//            int bytes;                                           //~@@@@R~

//            // Keep listening to the InputStream while connected //~@@@@R~
//            while (true) {                                       //~@@@@R~
//                try {                                            //~@@@@R~
//                    // Read from the InputStream                 //~@@@@R~
//                    bytes = mmInStream.read(buffer);             //~@@@@R~

//                    // Send the obtained bytes to the UI Activity//~@@@@R~
//                    mHandler.obtainMessage(BTControl.MESSAGE_READ, bytes, -1, buffer)//~@@@@R~
//                            .sendToTarget();                     //~@@@@R~
//                } catch (IOException e) {                        //~@@@@R~
//                    Log.e(TAG, "disconnected", e);               //~@@@@R~
//                    connectionLost();                            //~@@@@R~
//                    break;                                       //~@@@@R~
//                }                                                //~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
//        public void write(byte[] buffer) {                       //~@@@@R~
//            try {                                                //~@@@@R~
//                mmOutStream.write(buffer);                       //~@@@@R~

//                // Share the sent message back to the UI Activity//~@@@@R~
//                mHandler.obtainMessage(BTControl.MESSAGE_WRITE, -1, -1, buffer)//~@@@@R~
//                        .sendToTarget();                         //~@@@@R~
//            } catch (IOException e) {                            //~@@@@R~
//                Log.e(TAG, "Exception during write", e);         //~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~

//        public void cancel() {                                   //~@@@@R~
//            try {                                                //~@@@@R~
//                mmSocket.close();                                //~@@@@R~
//            } catch (IOException e) {                            //~@@@@R~
//                Log.e(TAG, "close() of connect socket failed", e);//~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
//***************************************************************************//~@@@@I~
	public static InputStream getBTInputStream(BluetoothSocket Psocket)//~@@@@I~
    {                                                              //~@@@@I~
    	InputStream s=null;                                        //~@@@@I~
		try                                                        //~@@@@I~
    	{                                                          //~@@@@I~
			s=Psocket.getInputStream();                            //~@@@@I~
		}                                                          //~@@@@I~
		catch (Exception e)                                        //~@@@@I~
		{                                                          //~@@@@I~
        	Dump.println(e,"getBTInputStream");                    //~@@@@I~
		}                                                          //~@@@@I~
        return s;                                                  //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~@@@@I~
	public static OutputStream getBTOutputStream(BluetoothSocket Psocket)//~@@@@I~
    {                                                              //~@@@@I~
    	OutputStream s=null;                                       //~@@@@I~
		try                                                        //~@@@@I~
    	{                                                          //~@@@@I~
			s=Psocket.getOutputStream();                           //~@@@@I~
		}                                                          //~@@@@I~
		catch (Exception e)                                        //~@@@@I~
		{                                                          //~@@@@I~
        	Dump.println(e,"getBTOutputStream");                   //~@@@@I~
		}                                                          //~@@@@I~
        return s;                                                  //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~@@@@I~
	public boolean acceptNext()                                    //~@@@@R~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("acceptNext:"+mAcceptThread+",swNFC="+AG.aBT.swNFC+",secureNFC="+AG.aBT.swSecureNFC+",secureNonNfc="+AG.aBT.swSecureNonNFCAccept);     //~1A64I~//~1AbbR~//~1AbtR~
        if (AG.aBT.swNFC && !AG.aBT.swSecureNFC)                   //~1AbbI~
        	return false;                                                //~1AbbI~
        if (!AG.aBT.swNFC && !AG.aBT.swSecureNonNFCAccept)               //~1AbtI~
        	return false;                                          //~1AbtI~
        if (mAcceptThread == null) {                               //~@@@@I~
//            mAcceptThread = new AcceptThread();                    //~@@@@I~//~v101R~
            mAcceptThread = new AcceptThread(true/*secure*/);      //~v101I~
    	  	if (mAcceptThread.mmServerSocket==null)                              //~@@@@I~//~v101R~
            	mAcceptThread =null;                               //~@@@@I~
            else                                                   //~@@@@I~
            	mAcceptThread.start();                             //~@@@@R~
            return true;  //started thread                         //~@@@@I~
        }                                                          //~@@@@I~
        return false;	//now alive                                //~@@@@I~
    }                                                              //~@@@@I~
//***************************************************************************//~v101I~
	public boolean acceptNextInSecure()                            //~v101I~
    {                                                              //~v101I~
    	if (Dump.Y) Dump.println("acceptNextInSecure:"+mAcceptThread+",swNFC="+AG.aBT.swNFC+",secureNFC="+AG.aBT.swSecureNFC+",swSecureNonNFC="+AG.aBT.swSecureNonNFCAccept);//~1AbbI~//~1AbtR~//~1AbZR~
        if (AG.aBT.swNFC && AG.aBT.swSecureNFC)                    //~1AbbI~
        	return false;                                                //~1AbbI~
        if (!AG.aBT.swNFC && AG.aBT.swSecureNonNFCAccept)                //~1AbtI~
        	return false;                                          //~1AbtI~
        if (mAcceptThreadInSecure == null) {                       //~v101I~
            mAcceptThreadInSecure = new AcceptThread(false/*insecure*/);//~v101I~
    	  	if (mAcceptThreadInSecure.mmServerSocket==null)        //~v101R~
            	mAcceptThreadInSecure =null;                       //~v101I~
            else                                                   //~v101I~
            	mAcceptThreadInSecure.start();                      //~v101I~
            return true;  //started thread                         //~v101I~
        }                                                          //~v101I~
        return false;	//now alive                                //~v101I~
    }                                                              //~v101I~
//***************************************************************************//~@@@2I~
	public boolean stopListen()                                    //~@@@2I~//~v101R~
    {                                                              //~@@@2I~
    	boolean rc=false;                                          //~@@@2I~
        if (Dump.Y) Dump.println("stopListen SYNC start");         //~v101I~
      synchronized(this)                                           //~v101I~
      {                                                            //~v101I~
        if (mAcceptThread != null)                                 //~@@@2I~
		{                                                          //~@@@2I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThread");//~@@@2I~
			mAcceptThread.cancel();                                //~@@@2I~
			mAcceptThread = null;                                  //~@@@2I~
            rc=true;                                               //~@@@2I~
		}                                                          //~@@@2I~
        if (mAcceptThreadInSecure != null)                         //~v101I~
		{                                                          //~v101I~
        	if (Dump.Y) Dump.println("BTService:cancel AcceptThreadInsecure");//~v101I~
			mAcceptThreadInSecure.cancel();                        //~v101I~
			mAcceptThreadInSecure = null;                          //~v101I~
            rc=true;                                               //~v101I~
		}                                                          //~v101I~
      }                                                            //~v101I~
        if (Dump.Y) Dump.println("stopListen SYNC end");           //~v101I~
        return rc;                                                 //~@@@2I~
    }                                                              //~@@@2I~
//***************************************************************************//~v101I~
	public static void requestDiscover(BTService Pservice,BluetoothAdapter Padapter,int Pdiscover)//~v101R~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("static requestDiscovery start Pservice="+Pservice); //~v101R~//~1AbKR~
        if (Pservice!=null)                                        //~v101I~
      	synchronized(Pservice)                                     //~v101R~
      	{                                                          //~v101I~
			if (Dump.Y) Dump.println("BTService requestDiscovery SYNC start");//~v101I~
			requestDiscover(Padapter,Pdiscover);                    //~v101I~
			if (Dump.Y) Dump.println("BTService requestDiscovery SYNC end");//~v101I~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
			if (Dump.Y) Dump.println("BTService requestDiscovery not SYNC start");//~1AbKI~
			requestDiscover(Padapter,Pdiscover);                    //~v101I~
			if (Dump.Y) Dump.println("BTService requestDiscovery not SYNC end");//~1AbKI~
      	}                                                          //~v101I~
        if (Dump.Y) Dump.println("static requestDiscovery end");   //~v101R~
    }                                                              //~v101I~
//***************************************************************************//~v101I~
	public static void requestDiscover(BluetoothAdapter Padapter,int Pdiscover)//~v101I~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("startDiscovery sub start Pdiscover="+Pdiscover);      //~v101I~//~1AbtR~
			if (Dump.Y) Dump.println("BTService requestDiscovery adapter="+Padapter);//~v101I~
			if (Padapter.isDiscovering())                          //~v101I~
			{                                                      //~v101I~
				if (Dump.Y) Dump.println("BTService:requestDiscovery cancelDiscover issue cancel");//~v101I~
				Padapter.cancelDiscovery();                        //~v101I~
			    if (Dump.Y) Dump.println("BTService requestDiscovery cancelDiscover end");//~v101I~//~1AbtR~
			}                                                      //~v101I~
            if (Pdiscover==1)    //start                           //~v101I~
            {                                                      //~v101I~
                if (Dump.Y) Dump.println("BTService requestDiscovery:startDiscovery");//~v101I~//~1AbtR~
              boolean rc=                                          //~1AbKI~
                Padapter.startDiscovery();                         //~v101I~
                if (Dump.Y) Dump.println("BTService requestDiscovery:startDiscovery end rc="+rc);//~v101I~//~1AbKR~
            }                                                      //~v101I~
        if (Dump.Y) Dump.println("requestDiscovery sub end");      //~v101I~
    }                                                              //~v101I~
//**************************************************************************//~v101I~
//*get device list pair of name,addr                               //~v101I~
//**************************************************************************//~v101I~
    public  static String[] getPairedDeviceList(BTService Pservice) //~v101R~
    {                                                              //~v101I~
      	String[] sa;                                               //~v101I~
        if (Dump.Y) Dump.println("BTService getPairDevice start"); //~v101R~
    	if (Pservice!=null)	//after service initialized            //~v101I~
        {                                                          //~v101I~
	        if (Dump.Y) Dump.println("BTService getPairDevice SYNC start");//~v101I~
	        synchronized(Pservice)                                 //~v101R~
      		{                                                      //~v101R~
	        	if (Dump.Y) Dump.println("BTService getPairDevice cancelDiscovery,isDiscovering="+Pservice.mAdapter.isDiscovering());//~1AbNR~
			  if (Pservice.mAdapter.isDiscovering())               //~1AbNR~
				Pservice.mAdapter.cancelDiscovery();               //~v101I~
	        	if (Dump.Y) Dump.println("BTService getPairDevice cancelDiscovery end");//~v101I~
				sa=getDeviceList(Pservice.mAdapter);                        //~v101I~
            }                                                      //~v101I~
	        if (Dump.Y) Dump.println("BTService getPairDevice SYNC end");//~v101I~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();//~v101I~
			sa=getDeviceList(adapter);                             //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("BTService getPairDevice end");   //~v101R~
        return sa;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************************//~v101I~
//*get device list pair of name,addr                               //~v101I~
//**************************************************************************//~v101I~
    public  static String[] getDeviceList(BluetoothAdapter Padapter)//~v101I~
    {                                                              //~v101I~
        BluetoothAdapter bta=Padapter;                             //~v101I~
        if (bta==null)                                             //~v101I~
        	return null;                                           //~v101I~
        if (Dump.Y) Dump.println("BTService getDeviceList start"); //~v101I~
        Set<BluetoothDevice> pairedDevices = bta.getBondedDevices();//~v101I~
                                                                   //~v101I~
        int sz=pairedDevices.size();                               //~v101I~
        if (sz==0)                                                 //~v101I~
            return null;                                           //~v101I~
        String[] sa=new String[sz*2];                              //~v101I~
        int ii=0;                                                  //~v101I~
        for (BluetoothDevice device : pairedDevices)               //~v101I~
        {                                                          //~v101I~
            sa[ii*2]=device.getName();                             //~v101I~
            sa[ii*2+1]=device.getAddress();                        //~v101I~
            if (Dump.Y) Dump.println("getPairDeviceList="+sa[ii*2]+"="+sa[ii*2+1]);//~v101I~
            ii++;                                                  //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("BTService getDevice end");       //~v101I~
        return sa;                                                 //~v101I~
    }                                                              //~v101I~
}
