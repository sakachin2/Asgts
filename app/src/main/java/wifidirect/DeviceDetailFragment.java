//*CID://+1AbBR~:                             update#=   90;       //+1AbBR~
//*************************************************************************//~1A65I~
//1AbB 2015/06/22 mask mac addr for security                       //+1AbBI~
//1Ab0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //~1Ab0I~
//1Aa4 2015/04/20 show also empty msg for server side              //~1Aa4I~
//1A89k2015/03/01 Ajagoc:2015/02/28 confirm session disconnect when unpair//~1A89I~
//1A6t 2015/02/18 (BUG)simpleProgress Dialog thread err exception  //~1A6tI~
//1A6e 2015/02/13 runOnUiThread for processingdialog               //~1A67I~
//1A67 2015/02/05 (kan)                                            //~1A67I~
//1A65 2015/01/29 implement Wifi-Direct function(>=Api14:android4.0)//~1A65I~
//*************************************************************************//~1A65I~
/*
 * Copyright (C) 2011 The Android Open Source Project
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

package wifidirect;                                                //~1A67R~

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.annotation.TargetApi;                               //~1A65R~
//import android.content.Context;
//import android.content.DialogInterface;                          //~1A65R~
//import android.content.Intent;
import android.content.res.Resources;                              //~1A65R~
//import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
//import android.os.AsyncTask;
//import android.os.Bundle;                                        //~1A65R~
//import android.os.Environment;
//import android.util.Log;
//import android.view.LayoutInflater;                              //~1A65R~
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.Asgts.R;
import com.Asgts.AG;                                                //~1A65I~
import com.Asgts.URunnable;
import com.Asgts.URunnableData;                                    //~1A6tR~

import wifidirect.DeviceListFragment.DeviceActionListener;

import jagoclient.Dump;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;

/**
 * A fragment that manages a particular peer and allows interaction with device
 * i.e. setting up network connection and transferring data.
 */
@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14                           //~1A65R~
//public class DeviceDetailFragment extends Fragment implements ConnectionInfoListener {//~1A65R~
public class DeviceDetailFragment implements ConnectionInfoListener
			,GroupInfoListener
{//~1A65R~
	

    private static final int BTNID_CONNECT=R.id.btn_connect;       //~1A65R~
    private static final int BTNID_DISCONNECT=R.id.btn_disconnect; //~1A65I~
    private TextView /*tvGroupOwner,*/tvDeviceInfo,tvStatusText,/*tvDeviceAddress,*/tvGroupIP;//~1A65R~
    private TextView tvPeerStatus,tvPeerInfo;                        //~1A84I~//~1A90I~//~1Ab0I~
    private Button btnConnect/*,btnDisconnect*/;                       //~1A65R~
    private ViewGroup layoutView;                                  //~1A65I~
    public DeviceListFragment deviceListFragment;                  //~1A65R~
                                                                   //~1A65I~
    protected static final int CHOOSE_FILE_RESULT_CODE = 20;
//  private View mContentView = null;                              //~1A65R~
    private WifiP2pDevice device;
    private WifiP2pInfo info;
//  ProgressDialog progressDialog = null;                          //~1A6tR~
    URunnableData progressDialog = null;                           //~1A6tI~
    public String ownerIPAddress,peerDevice;                       //~1A65R~

//    @Override                                                    //~1A65R~
//    public void onActivityCreated(Bundle savedInstanceState) {   //~1A65R~
//        super.onActivityCreated(savedInstanceState);             //~1A65R~
//    }                                                            //~1A65R~
	public DeviceDetailFragment()                                  //~1A65R~
    {                                                              //~1A65I~
    }                                                              //~1A65R~
//    @Override                                                    //~1A65R~
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//~1A65R~
//        mContentView = inflater.inflate(R.layout.device_detail, null);//~1A65R~
//        mContentView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {//~1A65R~

//            @Override                                            //~1A65R~
//            public void onClick(View v) {                        //~1A65R~
//                WifiP2pConfig config = new WifiP2pConfig();      //~1A65R~
//                config.deviceAddress = device.deviceAddress;     //~1A65R~
//                config.wps.setup = WpsInfo.PBC;                  //~1A65R~
//                if (progressDialog != null && progressDialog.isShowing()) {//~1A65R~
//                    progressDialog.dismiss();                    //~1A65R~
//                }                                                //~1A65R~
//                progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",//~1A65R~
//                        "Connecting to :" + device.deviceAddress, true, true//~1A65R~
////                        new DialogInterface.OnCancelListener() {//~1A65R~
////                                                               //~1A65R~
////                            @Override                          //~1A65R~
////                            public void onCancel(DialogInterface dialog) {//~1A65R~
////                                ((DeviceActionListener) getActivity()).cancelDisconnect();//~1A65R~
////                            }                                  //~1A65R~
////                        }                                      //~1A65R~
//                        );                                       //~1A65R~
//                ((DeviceActionListener) getActivity()).connect(config);//~1A65R~

//            }                                                    //~1A65R~
//        });                                                      //~1A65R~

//        mContentView.findViewById(R.id.btn_disconnect).setOnClickListener(//~1A65R~
//                new View.OnClickListener() {                     //~1A65R~

//                    @Override                                    //~1A65R~
//                    public void onClick(View v) {                //~1A65R~
//                        ((DeviceActionListener) getActivity()).disconnect();//~1A65R~
//                    }                                            //~1A65R~
//                });                                              //~1A65R~

//        mContentView.findViewById(R.id.btn_start_client).setOnClickListener(//~1A65R~
//                new View.OnClickListener() {                     //~1A65R~

//                    @Override                                    //~1A65R~
//                    public void onClick(View v) {                //~1A65R~
//                        // Allow user to pick an image from Gallery or other//~1A65R~
//                        // registered apps                       //~1A65R~
//                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//~1A65R~
//                        intent.setType("image/*");               //~1A65R~
//                        startActivityForResult(intent, CHOOSE_FILE_RESULT_CODE);//~1A65R~
//                    }                                            //~1A65R~
//                });                                              //~1A65R~

//        return mContentView;                                     //~1A65R~
//    }                                                            //~1A65R~
	//***********************************************************************************//~1A65I~
	public void initFragment(ViewGroup PlayoutView)                //~1A65R~
    {                                                              //~1A65I~
    //********************                                         //~1A65I~
    	layoutView=PlayoutView;                                    //~1A65I~
//      TextView view = (TextView) mContentView.findViewById(R.id.group_owner);//~1A65I~
//      tvGroupOwner=(TextView)PlayoutView.findViewById(R.id.group_owner);//~1A65R~
//      view = (TextView) mContentView.findViewById(R.id.device_info);//~1A65I~
        tvDeviceInfo=(TextView)PlayoutView.findViewById(R.id.device_info);//~1A65R~
        tvPeerStatus=(TextView)PlayoutView.findViewById(R.id.peer_status);//~1A84I~//~1A90I~//~1Ab0I~
        tvPeerInfo=(TextView)PlayoutView.findViewById(R.id.peer_info);//~1A84R~//~1A90I~//~1Ab0I~
//          new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text))//~1A65I~
        tvStatusText=(TextView)PlayoutView.findViewById(R.id.status_text);//~1A65I~
//      TextView view = (TextView) mContentView.findViewById(R.id.device_address);//~1A65I~
//      tvDeviceAddress=(TextView)PlayoutView.findViewById(R.id.device_address);//~1A65R~
//          mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);//~1A65I~
        tvGroupIP=(TextView)PlayoutView.findViewById(R.id.group_ip);//~1A65I~
//      mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);//~1A65I~
        btnConnect=(Button)PlayoutView.findViewById(BTNID_CONNECT);//~1A65I~
//      btnDisconnect=(Button)PlayoutView.findViewById(BTNID_DISCONNECT);//~1A65I~
        btnConnect.setVisibility(View.VISIBLE);                    //~1A65I~
//      btnDisconnect.setVisibility(View.GONE);                    //~1A65R~
    }                                                              //~1A65I~
	//***********************************************************************************//~1A65I~
    public int buttonAction(int PbuttonId)                  //~1821I~//~1A65R~
    {                                                              //~1A65I~
        boolean rc;	                                               //~1A65R~
    //********************                                         //~1A65I~
        switch(PbuttonId)                                          //~1A65I~
        {                                                          //~1A65I~
        case BTNID_CONNECT:                                        //~1A65I~
        	rc=connect();                                          //~1A65R~
        	break;                                                 //~1A65I~
        case BTNID_DISCONNECT:                                     //~1A65I~
        	rc=disconnect();                                       //~1A65R~
        	break;                                                 //~1A65I~
        default:                                                   //~1A65I~
        	return -1;                                             //~1A65I~
        }                                                          //~1A65I~
    	return (rc ? 1 : 0);                                       //~1A65R~
    }                                                              //~1A65I~
	//***********************************************************************************//~1A65I~
    private boolean connect()                                      //~1A65R~
    {                                                              //~1A65I~
        boolean rc=false;   //no dismiss                           //~1A65I~
    //********************                                         //~1A65I~
                WifiP2pConfig config = new WifiP2pConfig();        //~1A65I~
                config.deviceAddress = device.deviceAddress;       //~1A65I~
                if (Dump.Y) Dump.println("DeviceDetailFragment connect:"+config.deviceAddress);//~1A67I~
                config.wps.setup = WpsInfo.PBC;                    //~1A65I~
//                if (progressDialog != null && progressDialog.isShowing()) {//~1A65I~//~1A67R~
//                    progressDialog.dismiss();                      //~1A65I~//~1A67R~
//                }                                                  //~1A65I~//~1A67R~
    			dismissProgressDialog();                           //~1A67I~
//              progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",//~1A65R~
//                      "Connecting to :" + device.deviceAddress, true, true//~1A65R~
//                      );                                         //~1A65R~
//  			String server=device.deviceName;                   //~1A67R~
//              peerDevice=server;                                 //~1A67R~
//              if (server==null)                                  //~1A67R~
//              	server=device.deviceAddress;                   //~1A67R~
    			peerDevice=getDeviceName(device);                  //~1A67R~
//              progressDialog = ProgressDialog.show(getActivity(),WDA.getResourceString(R.string.ProgressDialogTitle),//~1A65I~//~1A67R~
//                      WDA.getResourceString(R.string.ProgressDialogMsgConnecting)+peerDevice, true, true//~1A67R~
//                      );                                         //~1A65I~//~1A67R~
                progressDialog = progressDialogShow(R.string.ProgressDialogTitle,//~1A67I~
//                      						WDA.getResourceString(R.string.ProgressDialogMsgConnecting)+peerDevice,//~1A67I~//+1AbBR~
                        						WDA.getResourceString(R.string.ProgressDialogMsgConnecting)+DialogNFC.maskMacAddr(peerDevice),//+1AbBI~
												true, true);       //~1A67I~
//              ((DeviceActionListener) getActivity()).connect(config);//~1A65R~
                ((DeviceActionListener) WDA.getWDActivity()).connect(config);//~1A65R~
    	return rc;                                                 //~1A65R~
    }                                                              //~1A65I~
	//***********************************************************************************//~1A67I~
    public boolean connect(String Pmacaddr)                       //~1A67I~
    {                                                              //~1A67I~
        boolean rc=false;   //no dismiss                           //~1A67I~
    //********************                                         //~1A67I~
        if (Dump.Y) Dump.println("DeviceDetailFragment:connect to "+Pmacaddr);//~1A67I~
                WifiP2pConfig config = new WifiP2pConfig();        //~1A67I~
                config.deviceAddress = Pmacaddr;//device.deviceAddress;//~1A67I~
                if (Dump.Y) Dump.println("DeviceDetailFragment connect:"+config.deviceAddress);//~1A67I~
                config.wps.setup = WpsInfo.PBC;                    //~1A67I~
//              if (progressDialog != null && progressDialog.isShowing()) {//~1A67R~
//                  progressDialog.dismiss();                      //~1A67R~
//              }                                                  //~1A67R~
    			dismissProgressDialog();                           //~1A67I~
//  			peerDevice=getDeviceName(device);                  //~1A67I~
//              progressDialog = ProgressDialog.show(getActivity(),WDA.getResourceString(R.string.ProgressDialogTitle),//~1A67R~
//                      WDA.getResourceString(R.string.ProgressDialogMsgConnectingNFC)+Pmacaddr, true, true//~1A67R~
//                      );                                         //~1A67R~
                progressDialog = progressDialogShow(R.string.ProgressDialogTitle,//~1A67I~
//                      						WDA.getResourceString(R.string.ProgressDialogMsgConnectingNFC)+Pmacaddr,//~1A67I~//+1AbBR~
                        						WDA.getResourceString(R.string.ProgressDialogMsgConnectingNFC)+DialogNFC.maskMacAddr(Pmacaddr),//+1AbBI~
												true, true);       //~1A67I~
                ((DeviceActionListener) WDA.getWDActivity()).connect(config);//~1A67I~
        if (Dump.Y) Dump.println("DeviceDetailFragment:connect return");//~1A67I~
    	return rc;                                                 //~1A67I~
    }                                                              //~1A67I~
	//***********************************************************************************//~1A65I~
    private boolean disconnect()                                   //~1A65R~
    {                                                              //~1A65I~
        boolean rc=false;   //no dismiss                           //~1A65I~
    //********************                                         //~1A65I~
    	if (Dump.Y) Dump.println("DeviceDetailFragment:disconnect");//~1A84I~//~1A90I~//~1Ab0I~
      boolean noActiveSession=                                     //~1A89R~//~1A6tI~//~1A89I~
		WDA.SWDA.unpairRequest();	//close if session alive       //~1A87R~//~1A6tI~
//                      ((DeviceActionListener) getActivity()).disconnect();//~1A65R~
//                      ((DeviceActionListener) WDA.getWDActivity()).disconnect();//~1A65R~//~1A6tR~//~1A89I~
	  	if (noActiveSession)                                       //~1A89R~//~1A6tI~//~1A89I~
		    doUnpair();                                            //~1A89R~//~1A6tI~//~1A89I~
    	return rc;                                                 //~1A65I~
    }                                                              //~1A65I~
	//***********************************************************************************//~1A87I~//~1A6tI~//~1A89I~
    public void doUnpair()                                         //~1A89R~//~1A6tI~//~1A89I~
    {                                                              //~1A89R~//~1A6tI~//~1A89I~
    	if (Dump.Y) Dump.println("DeviceDetailFragment:doUnpair"); //~1A89R~//~1A6tI~//~1A89I~
    	((DeviceActionListener) WDA.getWDActivity()).disconnect(); //~1A89R~//~1A6tI~//~1A89I~
    }                                                              //~1A89R~//~1A6tI~//~1A89I~
	//***********************************************************************************//~1A65I~
//    @Override                                                    //~1A65R~
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {//~1A65R~

//        // User has picked an image. Transfer it to group owner i.e peer using//~1A65R~
//        // FileTransferService.                                  //~1A65R~
//        Uri uri = data.getData();                                //~1A65R~
////      TextView statusText = (TextView) mContentView.findViewById(R.id.status_text);//~1A65R~
//        TextView statusText =tvStatusText;                       //~1A65R~
//        statusText.setText("Sending: " + uri);                   //~1A65R~
//        Log.d(WiFiDirectActivity.TAG, "Intent----------- " + uri);//~1A65R~
//        Intent serviceIntent = new Intent(getActivity(), FileTransferService.class);//~1A65R~
//        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);//~1A65R~
//        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());//~1A65R~
//        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_ADDRESS,//~1A65R~
//                info.groupOwnerAddress.getHostAddress());        //~1A65R~
//        serviceIntent.putExtra(FileTransferService.EXTRAS_GROUP_OWNER_PORT, 8988);//~1A65R~
//        getActivity().startService(serviceIntent);               //~1A65R~
//    }                                                            //~1A65R~

	//***********************************************************************************//~1A65I~
	//*ConnectionInfoListener                                      //~1A65I~
	//***********************************************************************************//~1A65I~
    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
      try                                                          //~1A65I~
      {                                                            //~1A65I~
//      if (progressDialog != null && progressDialog.isShowing()) {//~1A67R~
//          progressDialog.dismiss();                              //~1A67R~
//      }                                                          //~1A67R~
    	dismissProgressDialog();                                   //~1A67I~
        this.info = info;
        this.getView().setVisibility(View.VISIBLE);
		if (Dump.Y) Dump.println("onConnectionInfoAvailable:infoToString="+info.toString());//~1A65I~

        // The owner IP is now known.
//      TextView view = (TextView) mContentView.findViewById(R.id.group_owner);//~1A65R~
//      TextView view=tvGroupOwner;                                //~1A65R~
        TextView view;                                             //~1A65I~
//      view.setText(getResources().getString(R.string.group_owner_text)//~1A65R~
//              + ((info.isGroupOwner == true) ? getResources().getString(R.string.yes)//~1A65R~
//                      : getResources().getString(R.string.no))); //~1A65R~
		WDA.getDeviceListFragment().updateThisOwner(info.isGroupOwner);//~1A65I~

//		if (Dump.Y) Dump.println("onConnectionInfoAvailable:groupowner="+view.getText().toString());//~1A65I~
        // InetAddress from WifiP2pInfo struct.
//      view = (TextView) mContentView.findViewById(R.id.device_info);//~1A65R~
        view=tvGroupIP;                                            //~1A65R~
//      view.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress());//~1A67R~
        view.setText(WDA.getResourceString(R.string.GroupOwnerIP) + info.groupOwnerAddress.getHostAddress());//~1A67I~
        ownerIPAddress=info.groupOwnerAddress.getHostAddress();    //~1A65I~
		if (Dump.Y) Dump.println("onConnectionInfoAvailable:groupip="+view.getText().toString());//~1A65R~
//        if (device!=null)   //showDetail parm                    //~1A65R~
//        {                                                        //~1A65R~
//            view=tvDeviceAddress;                                //~1A65R~
//            view.setText(device.deviceName+" (" + device.deviceAddress+" )");//~1A65R~
//            if (Dump.Y) Dump.println("onConnectionInfoAvailable:device!=null address="+view.getText().toString());//~1A65R~
//        }                                                        //~1A65R~

        // After the group negotiation, we assign the group owner as the file
        // server. The file server is single threaded, single connection server
        // socket.
        if (info.groupFormed && info.isGroupOwner) {
//          new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text))//~1A65R~
//            new FileServerAsyncTask(getActivity(),tvStatusText)  //~1A65R~
//                    .execute();                                  //~1A65R~
            tvStatusText.setText(getResources()                    //~1Aa4I~
                    .getString(R.string.server_text));             //~1Aa4I~
			if (Dump.Y) Dump.println("onConnectionInfoAvailable:Client:statusText="+tvStatusText.getText().toString());//~1A65I~
        } else if (info.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
//          mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);//~1A65R~
//          ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()//~1A65R~
            tvStatusText.setText(getResources()                    //~1A65I~
                    .getString(R.string.client_text));
			if (Dump.Y) Dump.println("onConnectionInfoAvailable:Server:statusText="+tvStatusText.getText().toString());//~1A65R~
        }

        // hide the connect button
//      mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);//~1A65R~
//  	WDA.SWDA.connected();	//update AG.RemotestatusWD     //~1A65I~//~1A84R~//~1A90I~//~1Ab0I~
        btnConnect.setVisibility(View.GONE);                       //~1A65I~
//      btnDisconnect.setVisibility(View.VISIBLE);                 //~1A65R~
    	WDA.SWDA.connected(info.isGroupOwner);                     //~1A84I~//~1A90I~//~1Ab0I~
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
      	Dump.println(e,"DeviceDetailFragment:onConnectionInfoAvailable");//~1A65I~
      }                                                            //~1A65I~
    }

    /**
     * Updates the UI with device data
     * 
     * @param device the device to be displayed
     */
    public void showDetails(WifiP2pDevice device) {
        this.device = device;
        if (Dump.Y) Dump.println("DeviceDetailFragment showDetail:name="+device.deviceName+",addr="+device.deviceAddress);//~1A67I~
//      peerDevice=device.deviceName;                              //~1A67R~
        peerDevice=getDeviceName(this.device);                     //~1A67R~
        this.getView().setVisibility(View.VISIBLE);
//      TextView view = (TextView) mContentView.findViewById(R.id.device_address);//~1A65R~
//      TextView view =tvDeviceAddress;                            //~1A65R~
//      view.setText(device.deviceAddress);                        //~1A65R~
//      view.setText(device.deviceName+" (" + device.deviceAddress+" )");//~1A65R~
//  	if (Dump.Y) Dump.println("DeviceDetailFragment:showDetails:deviceaddress="+view.getText().toString());//~1A65R~
//      view = (TextView) mContentView.findViewById(R.id.device_info);//~1A65R~
//      view =tvDeviceInfo;                                        //~1A65R~
//      view.setText(device.toString());                           //~1A65R~
//      view.setText(DeviceListFragment.getDeviceStatus(device.status));//~1A65R~
//  	if (Dump.Y) Dump.println("DeviceDetailFragment:showDetails:deviceinfo="+view.getText().toString());//~1A65R~

    }

    /**
     * Clears the UI fields after a disconnect or direct mode disable operation.
     */
    public void resetViews() {
		if (Dump.Y) Dump.println("showDetails:resetView");         //~1A65I~
//      mContentView.findViewById(R.id.btn_connect).setVisibility(View.VISIBLE);//~1A65R~
        btnConnect.setVisibility(View.VISIBLE);                    //~1A65I~
//      btnDisconnect.setVisibility(View.GONE);                    //~1A65R~
//      TextView view = (TextView) mContentView.findViewById(R.id.device_address);//~1A65R~
//      TextView view =tvDeviceAddress;                            //~1A65R~
        TextView view;                                             //~1A65I~
//      view.setText(R.string.empty);                              //~1A65R~
//      view = (TextView) mContentView.findViewById(R.id.device_info);//~1A65R~
//      view =tvDeviceInfo;                                        //~1A65R~
//      view.setText(R.string.empty);                              //~1A65R~
//      view = (TextView) mContentView.findViewById(R.id.group_owner);//~1A65R~
//      view =tvGroupOwner;                                        //~1A65R~
//      view.setText(R.string.empty);                              //~1A65R~
//      view = (TextView) mContentView.findViewById(R.id.status_text);//~1A65R~
        view =tvStatusText;                                        //~1A65I~
        view.setText(R.string.empty);
//      mContentView.findViewById(R.id.btn_start_client).setVisibility(View.GONE);//~1A65R~
        this.getView().setVisibility(View.GONE);
    }

    /**
     * A simple server socket that accepts connection and writes some data on
     * the stream.
     */
//    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {//~1A65R~

//        private Context context;                                 //~1A65R~
//        private TextView statusText;                             //~1A65R~

//        /**                                                      //~1A65R~
//         * @param context                                        //~1A65R~
//         * @param statusText                                     //~1A65R~
//         */                                                      //~1A65R~
//        public FileServerAsyncTask(Context context, View statusText) {//~1A65R~
//            this.context = context;                              //~1A65R~
//            this.statusText = (TextView) statusText;             //~1A65R~
//        }                                                        //~1A65R~

//        @Override                                                //~1A65R~
//        protected String doInBackground(Void... params) {        //~1A65R~
//            try {                                                //~1A65R~
//                if (Dump.Y) Dump.println("DeviceDetailFragment:doInBackground");//~1A65R~
//                ServerSocket serverSocket = new ServerSocket(8988);//~1A65R~
////              Log.d(WiFiDirectActivity.TAG, "Server: Socket opened");//~1A65R~
//                if (Dump.Y) Dump.println("Server: Socket opened");//~1A65R~
//                Socket client = serverSocket.accept();           //~1A65R~
////              Log.d(WiFiDirectActivity.TAG, "Server: connection done");//~1A65R~
//                if (Dump.Y) Dump.println("Server: connection done");//~1A65R~
//                final File f = new File(Environment.getExternalStorageDirectory() + "/"//~1A65R~
//                        + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()//~1A65R~
//                        + ".jpg");                               //~1A65R~

//                File dirs = new File(f.getParent());             //~1A65R~
//                if (!dirs.exists())                              //~1A65R~
//                    dirs.mkdirs();                               //~1A65R~
//                f.createNewFile();                               //~1A65R~

////              Log.d(WiFiDirectActivity.TAG, "server: copying files " + f.toString());//~1A65R~
//                if (Dump.Y) Dump.println("server: copying files " + f.toString());//~1A65R~
//                InputStream inputstream = client.getInputStream();//~1A65R~
//                copyFile(inputstream, new FileOutputStream(f));  //~1A65R~
//                serverSocket.close();                            //~1A65R~
//                return f.getAbsolutePath();                      //~1A65R~
//            } catch (IOException e) {                            //~1A65R~
////              Log.e(WiFiDirectActivity.TAG, e.getMessage());   //~1A65R~
//                Dump.println(e,"DeviceDetailFragment:doInBackground");//~1A65R~
//                return null;                                     //~1A65R~
//            }                                                    //~1A65R~
//        }                                                        //~1A65R~

//        /*                                                       //~1A65R~
//         * (non-Javadoc)                                         //~1A65R~
//         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)//~1A65R~
//         */                                                      //~1A65R~
//        @Override                                                //~1A65R~
//        protected void onPostExecute(String result) {            //~1A65R~
//            if (Dump.Y) Dump.println("onPostExecute");           //~1A65R~
//            if (result != null) {                                //~1A65R~
//                statusText.setText("File copied - " + result);   //~1A65R~
//                Intent intent = new Intent();                    //~1A65R~
//                intent.setAction(android.content.Intent.ACTION_VIEW);//~1A65R~
//                intent.setDataAndType(Uri.parse("file://" + result), "image/*");//~1A65R~
//                context.startActivity(intent);                   //~1A65R~
//            }                                                    //~1A65R~

//        }                                                        //~1A65R~

//        /*                                                       //~1A65R~
//         * (non-Javadoc)                                         //~1A65R~
//         * @see android.os.AsyncTask#onPreExecute()              //~1A65R~
//         */                                                      //~1A65R~
//        @Override                                                //~1A65R~
//        protected void onPreExecute() {                          //~1A65R~
//            if (Dump.Y) Dump.println("onPreExecute");            //~1A65R~
////          statusText.setText("Opening a server socket");       //~1A65R~
//            statusText.setText(WDA.getResourceString(R.string.OpeningServerSocket));//~1A65R~
//        }                                                        //~1A65R~

//    }                                                            //~1A65R~

//    public static boolean copyFile(InputStream inputStream, OutputStream out) {//~1A65R~
//        byte buf[] = new byte[1024];                             //~1A65R~
//        int len;                                                 //~1A65R~
//        try {                                                    //~1A65R~
//            while ((len = inputStream.read(buf)) != -1) {        //~1A65R~
//                out.write(buf, 0, len);                          //~1A65R~

//            }                                                    //~1A65R~
//            out.close();                                         //~1A65R~
//            inputStream.close();                                 //~1A65R~
//        } catch (IOException e) {                                //~1A65R~
//            Log.d(WiFiDirectActivity.TAG, e.toString());         //~1A65R~
//            return false;                                        //~1A65R~
//        }                                                        //~1A65R~
//        return true;                                             //~1A65R~
//    }                                                            //~1A65R~
	//***********************************************************************************//~1A65I~
	//*GroupInfoListener                                           //~1A65I~
	//***********************************************************************************//~1A65I~
    @Override                                                      //~1A65I~
    public void onGroupInfoAvailable(final WifiP2pGroup Pgroup)    //~1A65I~
	{                                                              //~1A65I~
    //*******************                                          //~1A65I~
      	try                                                        //~1A65I~
      	{                                                          //~1A65I~
        	boolean owner=Pgroup.isGroupOwner();                   //~1A65I~
	    	WifiP2pDevice deviceOwner=Pgroup.getOwner();           //~1A65I~
      		if (Dump.Y) Dump.println("DeviceDetailFragment:onGroupInfoAvailable owner="+owner+",ownername="+deviceOwner.deviceName);//~1A65I~
	    	Collection<WifiP2pDevice> clients=Pgroup.getClientList();//~1A65I~
		    List<WifiP2pDevice> clientList=new ArrayList<WifiP2pDevice>();//~1A65I~
        	clientList.addAll(clients);                            //~1A65I~
	        int sz=clientList.size();                              //~1A65I~
      		if (Dump.Y) Dump.println("DeviceDetailFragment:onGroupInfoAvailable client list ctr="+sz);//~1A65I~
            int clientCtr=0;                                       //~1A65I~
                                                                   //~1A65I~
		    String peers="";                                       //~1A65R~
            for (int ii=0;ii<sz;ii++)                              //~1A65I~
            {                                                      //~1A65I~
		    	WifiP2pDevice client=clientList.get(ii);     //~1A65I~
                int status=client.status;                          //~1A65I~
	      		if (Dump.Y) Dump.println("DeviceDetailFragment:onGroupInfoAvailable clientList name="+client.deviceName+",addr="+client.deviceAddress+",status="+DeviceListFragment.getDeviceStatus(status)+",owner="+client.isGroupOwner());//~1A65I~
                if (status==WifiP2pDevice.CONNECTED                //~1A65I~
                &&  !client.isGroupOwner())                        //~1A65I~
                {                                                  //~1A65I~
                	clientCtr++;                                   //~1A65I~
//                  peers+=client.deviceName+" ";                  //~1A67R~
                    peers+=getDeviceName(client)+" ";              //~1A67R~
                }                                                  //~1A65I~
            }                                                      //~1A65I~
            if (owner)                                             //~1A65I~
				if (clientCtr!=0)                                  //~1A65R~
	            	peerDevice=peers.trim();                       //~1A65R~
                else                                               //~1A65I~
	            	peerDevice="None";                             //~1A65I~
            else                                                   //~1A65I~
            {                                                      //~1A67R~
//          	peerDevice=deviceOwner.deviceName;                 //~1A67R~
	            peerDevice=getDeviceName(deviceOwner);             //~1A67R~
            }                                                      //~1A67R~
        	TextView tv=tvDeviceInfo;                              //~1A65I~
//          tv.setText(WDA.getResourceString(R.string.PeerDeviceName)+peerDevice);//~1A65R~//~1A84R~//~1A90I~//~1Ab0I~
            tv.setText(peerDevice);                                //~1A84I~//~1A90I~//~1Ab0I~
            String ip=IPConnection.getRemoteIPAddr(true/*return null if N/A*/);//~1A84R~//~1A90I~//~1Ab0I~
            if (ip==null)                                          //~1A84I~//~1A90I~//~1Ab0I~
            {                                                      //~1A84I~//~1A90I~//~1Ab0I~
            	tvPeerStatus.setText(AG.resource.getString(R.string.DeviceConnected));  //paired//~1A84I~//~1A90I~//~1Ab0I~
//              if (owner)                                         //~1A84I~//~1A8mR~//~1A90I~//~1Ab0I~
                if (!owner)   //i'm client -->peer is server       //~1A8mI~//~1A90I~//~1Ab0I~
            		tvPeerInfo.setText(AG.resource.getString(R.string.MyOwnerYes));//~1A84I~//~1A90I~//~1Ab0I~
                else                                               //~1A84I~//~1A90I~//~1Ab0I~
            		tvPeerInfo.setText(AG.resource.getString(R.string.MyOwnerNo));//~1A84I~//~1A90I~//~1Ab0I~
            }                                                      //~1A84I~//~1A90I~//~1Ab0I~
            else                                                   //~1A84I~//~1A90I~//~1Ab0I~
            {                                                      //~1A84I~//~1A90I~//~1Ab0I~
            	tvPeerStatus.setText(AG.resource.getString(R.string.IPInSession));//~1A84R~//~1A90I~//~1Ab0I~
            	tvPeerInfo.setText(ip);                            //~1A84I~//~1A90I~//~1Ab0I~
            }                                                      //~1A84I~//~1A90I~//~1Ab0I~
      		if (Dump.Y) Dump.println("DeviceDetailFragment:onGroupInfoAvailable settext deviceinfo "+peerDevice);//~1A65I~
      	}                                                          //~1A65I~
      	catch(Exception e)                                         //~1A65I~
      	{                                                          //~1A65I~
      		Dump.println(e,"DeviceDetailFragment:onGroupInfoAvailable");//~1A65I~
      	}                                                          //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public void setVisibility(int Pvisibility)                     //~1A65R~
    {                                                              //~1A65I~
    	View v=getView();                                          //~1A65I~
        v.setVisibility(Pvisibility);                              //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public View getView()                                          //~1A65M~
    {                                                              //~1A65M~
        return layoutView.findViewById(R.id.device_detail_wd);     //~1A65M~
    }                                                              //~1A65M~
	//*******************************************************************************************************//~1A65I~
    private Resources getResources()                               //~1A65I~
    {                                                              //~1A65I~
        return AG.resource;                                        //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A67R~
    public static String getDeviceName(WifiP2pDevice Pdevice)      //~1A67R~
    {                                                              //~1A67R~
    	String name;                                               //~1A67R~
		name=Pdevice.deviceName;                                   //~1A67R~
		if (name==null || name.equals(""))                         //~1A67R~
    		name=Pdevice.deviceAddress;                            //~1A67R~
        return name;                                               //~1A67R~
    }                                                              //~1A67R~
	//*******************************************************************************************************//~1A67I~
	//*from WiFiDirectActivity:notifyConnected                     //~1A67I~
	//*******************************************************************************************************//~1A67I~
    public void connected(int Presult,String Pmsg)                 //~1A67I~
    {                                                              //~1A67I~
    	dismissProgressDialog();                                   //~1A67I~
        tvStatusText.setText(Pmsg);                                //~1A67I~
    }                                                              //~1A67I~
	//*******************************************************************************************************//~1A67I~
    private void dismissProgressDialog()                           //~1A67I~
    {                                                              //~1A67I~
//  	if (progressDialog != null && progressDialog.isShowing())  //~1A67R~
//  	{                                                          //~1A67R~
//      	progressDialog.dismiss();                              //~1A67R~
//     	}                                                          //~1A67R~
        URunnable.dismissDialog(progressDialog);                       //~1A67I~
    }                                                              //~1A67I~
//****************************************                         //~1A67I~
//  private ProgressDialog progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A67I~//~1A6tR~
    private URunnableData progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A6tI~
    {                                                              //~1A67I~
        if (Dump.Y) Dump.println("ProgDialog:simpleProgressDialogShow");//~1A67I~
        return URunnable.simpleProgressDialogShow(Ptitleid,Pmsg,Pindeterminate,Pcancelable);//~1A67I~
    }                                                              //~1A67I~
}
