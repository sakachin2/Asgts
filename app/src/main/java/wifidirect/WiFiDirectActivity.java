//*CID://+1Ac4R~:                             update#=   66;       //~1Ac4R~
//*************************************************************************//~1A65I~
//1Ac4 2015/07/06 WD:try disable wifi direct at unpair             //~1Ac4I~
//1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//~1Ac0I~
//1Aby 2015/06/21 NFCWD:system settings id is not ACTION_WIREESS_SETTING but ACTION_WIFI_SETTINGS//~1AbyI~
//1Aa5 2015/04/20 test function for mdpi listview                  //~1Aa5I~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6a 2015/02/10 NFC+Wifi support                                 //~1A6aI~
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

import jagoclient.Dump;
import android.annotation.TargetApi;                               //~1A65I~
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
//import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import wifidirect.DeviceListFragment.DeviceActionListener;
import wifidirect.WDANFC;
                                                                   //~1A65I~
import com.Asgts.AG;                                                //~1A65I~
import com.Asgts.R;
import com.Asgts.Utils;

/**
 * An activity that uses WiFi Direct APIs to discover and connect with available
 * devices. WiFi Direct APIs are asynchronous and rely on callback mechanism
 * using interfaces to notify the application of operation success or failure.
 * The application should also register a BroadcastReceiver for notification of
 * WiFi state related events.
 */
@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14                           //~1A65R~
//public class WiFiDirectActivity extends Activity implements ChannelListener, DeviceActionListener {//~1A65R~
public class WiFiDirectActivity implements ChannelListener, DeviceActionListener {//~1A65I~
    public static boolean Stestdevicelist=false;    //expand device list @@@@test//~1Aa5R~
    public static boolean Stestdevicelist_mdpi=false;    //set empty devcicelist for emulator test of mdpi @@@@test//~1Aa5R~
    public static final String TAG = "wifidirectdemo";
    protected static final int CONNECT_OK=1;                         //~1A6aI~//~1A6sR~
    protected static final int CONNECT_ER=2;                         //~1A6aI~//~1A6sR~
    protected static final int DISCONNECT_OK=3;                      //~1A6aI~//~1A6sR~
    protected static final int DISCONNECT_ER=4;                      //~1A6aI~//~1A6sR~
    protected static final int DISCONNECT_CANCEL_OK=5;               //~1A6aI~//~1A6sR~
    protected static final int DISCONNECT_CANCEL_ER=6;               //~1A6aI~//~1A6sR~
    protected static final int DISCONNECTED=7;                     //~1A6sI~
//  private WifiP2pManager manager;                                //~1A6sR~
//  protected WifiP2pManager manager;                              //~1A6sI~//~1Ac4R~
    public WifiP2pManager manager;                                 //~1Ac4I~
//  private boolean isWifiP2pEnabled = false;                      //~1A6sR~
    protected boolean isWifiP2pEnabled = false;                    //~1A6sI~
    private boolean retryChannel = false;

//  private final IntentFilter intentFilter = new IntentFilter();  //~1A6sR~
    protected final IntentFilter intentFilter = new IntentFilter();//~1A6sI~
//  private Channel channel;                                       //~1A6sR~
//  protected Channel channel;                                     //~1A6sI~//~1Ac4R~
    public Channel channel;                                        //~1Ac4I~
    private BroadcastReceiver receiver = null;
    //*******************************************************************//~1A65I~
	public WiFiDirectActivity()                                    //~1A65I~
    {                                                              //~1A65R~
	    onCreate();                                                //~1A65M~
    }                                                              //~1A65I~
    //*******************************************************************//~1A65I~
    /**
     * @param isWifiP2pEnabled the isWifiP2pEnabled to set
     */
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    //*******************************************************************//~1A65I~
//  @Override                                                      //~1A65R~
//  public void onCreate(Bundle savedInstanceState) {              //~1A65R~
    private void onCreate() {                                      //~1A65I~
//      super.onCreate(savedInstanceState);                        //~1A65R~
//      setContentView(R.layout.main);                             //~1A65R~

        // add necessary intent values to be matched.

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
//      channel = manager.initialize(this, getMainLooper(), null); //~1A65R~
        channel = manager.initialize(getContext(),getMainLooper(),this);//~1A65R~
    }

    //*******************************************************************//~1A65I~
    /** register the BroadcastReceiver with the intent values to be matched */
//  @Override                                                      //~1A65R~
//  public void onResume() {                                       //~1A65R~
//      super.onResume();                                          //~1A65R~
//      receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);//~1A65R~
//      getContext().registerReceiver(receiver, intentFilter);     //~1A65R~
//  }                                                              //~1A65R~
    public void onResume() {                                       //~1A65I~
    	registerReceiver();                                        //~1A65I~
    }                                                              //~1A65I~
    public void registerReceiver()                                 //~1A65I~
    {                                                              //~1A65I~
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);//~1A65I~
        getContext().registerReceiver(receiver, intentFilter);     //~1A65I~
    }                                                              //~1A65I~
    //*******************************************************************//~1A65I~
//  @Override                                                      //~1A65R~
//  public void onPause() {                                        //~1A65R~
//      super.onPause();                                           //~1A65R~
//      unregisterReceiver(receiver);                              //~1A65R~
//  }                                                              //~1A65R~
    public void onPause()                                          //~1A65I~
	{                                                              //~1A65I~
        unregisterReceiver();                                      //~1A65I~
    }                                                              //~1A65I~
    public void unregisterReceiver()                               //~1A65I~
	{                                                              //~1A65I~
		if (receiver==null)                                        //~1A65I~
        	return;                                                //~1A65I~
        getContext().unregisterReceiver(receiver);                 //~1A65I~
        receiver=null;                                             //~1A65I~
    }                                                              //~1A65I~
    //*******************************************************************//~1A65I~
    /**
     * Remove all peers and clear all fields. This is called on
     * BroadcastReceiver receiving a state change event.
     */
    public void resetData() {
//      DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager()//~1A65R~
//              .findFragmentById(R.id.frag_list);                 //~1A65R~
        DeviceListFragment fragmentList =WDA.getDeviceListFragment();//~1A65R~
//      DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getFragmentManager()//~1A65R~
//              .findFragmentById(R.id.frag_detail);               //~1A65R~
        DeviceDetailFragment fragmentDetails =WDA.getDeviceDetailFragment();//~1A65I~
        if (fragmentList != null) {
            fragmentList.clearPeers();
        }
        if (fragmentDetails != null) {
            fragmentDetails.resetViews();
        }
    }

//    @Override                                                    //~1A65R~
//    public boolean onCreateOptionsMenu(Menu menu) {              //~1A65R~
//        MenuInflater inflater = getMenuInflater();               //~1A65R~
//        inflater.inflate(R.menu.action_items, menu);             //~1A65R~
//        return true;                                             //~1A65R~
//    }                                                            //~1A65R~

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
//  @Override                                                      //~1A65R~
//  public boolean onOptionsItemSelected(MenuItem item) {          //~1A65R~
    public boolean buttonAction(int Pbtnid){                       //~1A65R~
//      switch (item.getItemId()) {                                //~1A65R~
        switch (Pbtnid) {                                          //~1A65I~
//          case R.id.atn_direct_enable:                           //~1A67R~
            case DeviceListFragment.BTNID_P2PENABLE:               //~1A67I~
            	if (Dump.Y) Dump.println("WiFiDirectActibity:buttonAction:p2p enable");//~1A67I~
                if (manager != null && channel != null) {

                    // Since this is the system wireless settings activity, it's
                    // not going to send us a result. We will be notified by
                    // WiFiDeviceBroadcastReceiver instead.
                  getActivity().                                   //~1A65R~
//                  startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//~1AbyR~
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));//~1AbyR~
                } else {
//                  Log.e(TAG, "channel or manager is null");      //~1A65R~
                    if (Dump.Y) Dump.println("channel or manager is null");//~1A65I~
                }
                return true;

//          case R.id.atn_direct_discover:                         //~1A65R~
            case DeviceListFragment.BTNID_DISCOVER:                //~1A65R~
                if (!isWifiP2pEnabled) {
//                  Toast.makeText(WiFiDirectActivity.this, R.string.p2p_off_warning,//~1A65R~
                    Toast.makeText(getContext(), R.string.p2p_off_warning,//~1A65I~
                            Toast.LENGTH_SHORT).show();
                    if (Stestdevicelist_mdpi)//emulator missing wifi func//~1Aa5I~
                    {                                              //~1Aa5I~
                		final DeviceListFragment fragment = WDA.getDeviceListFragment();//~1A6sI~//~1Aa5I~
                		WifiP2pDeviceList pl=new WifiP2pDeviceList();//~1A6sI~//~1Aa5I~
                		fragment.onPeersAvailable(pl);             //~1A6sI~//~1Aa5I~
                    }                                              //~1Aa5I~
                    return true;
                }
                if (Dump.Y) Dump.println("WiFiDirectActivity buttonAction:discover");//~1A6aI~
//              final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()//~1A65R~
//                      .findFragmentById(R.id.frag_list);         //~1A65R~
                final DeviceListFragment fragment = WDA.getDeviceListFragment();//~1A65I~
                fragment.onInitiateDiscovery();
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
//                      Toast.makeText(WiFiDirectActivity.this, "Discovery Initiated",//~1A65R~
                        Toast.makeText(getContext(),WDA.getResourceString(R.string.DiscoveryInitiated),//~1A65R~
                                Toast.LENGTH_SHORT).show();
                        if (Dump.Y) Dump.println("WiFiDirectActivity discover onSuccess");//~1A6aI~
                    }

                    @Override
                    public void onFailure(int reasonCode) {
//                      Toast.makeText(WiFiDirectActivity.this, "Discovery Failed : " + reasonCode,//~1A65R~
                        Toast.makeText(getContext(),WDA.getResourceString(R.string.DiscoveryFailedReason) + reasonCode,//~1A65R~
//                              Toast.LENGTH_SHORT).show();        //~1A65R~
                                Toast.LENGTH_LONG).show();         //~1A65I~
                        if (Dump.Y) Dump.println("WiFiDirectActivity discover onFailure reason="+reasonCode);//~1A6aI~
                    }
                });
                return true;
            case DeviceListFragment.BTNID_NFC:                     //~1A6aI~
                startNFC();                                        //~1A6aI~
                return true;                                       //~1A6aI~
            default:
//              return super.onOptionsItemSelected(item);          //~1A65R~
                return false;                                      //~1A65I~
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {
      try                                                          //~1A65I~
      {                                                            //~1A65I~
//      DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()//~1A65R~
//              .findFragmentById(R.id.frag_detail);               //~1A65R~
        DeviceDetailFragment fragment = WDA.getDeviceDetailFragment();//~1A65R~
        fragment.showDetails(device);
      }                                                            //~1A65R~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
        Dump.println(e,"WiFiDirectActivity:showDetail");           //~1A65I~
      }                                                            //~1A65I~
    }

    @Override
    public void connect(WifiP2pConfig config) {
      try                                                          //~1A65I~
      {                                                            //~1A65I~
      	if (Dump.Y) Dump.println("WiFiDirectActivity:connect device="+config.deviceAddress);    //~1A65I~//~1A6aR~
        manager.connect(channel, config, new ActionListener() {

            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
		      	if (Dump.Y) Dump.println("WiFiDirectActivity:connect:onSuccess");//~1A65I~
                notifyConnected(CONNECT_OK,null);                  //~1A6aR~
            }

            @Override
            public void onFailure(int reason) {
		      	if (Dump.Y) Dump.println("WiFiDirectActivity:connect:onFailure reason="+reason);//~1A65I~//~1A6aR~
//              Toast.makeText(WiFiDirectActivity.this, "Connect failed. Retry.",//~1A65R~
//              Toast.makeText(getContext(),WDA.getResourceString(R.string.ConnectFailedRetry),//~1A65R~//~1A6aR~
//                      Toast.LENGTH_SHORT).show();                //~1A65R~
//                      Toast.LENGTH_LONG).show();                 //~1A65I~//~1A6aR~
                String msg=WDA.getResourceString(R.string.ConnectFailedRetry);//~1A6aI~
                notifyConnected(CONNECT_ER,msg);                   //~1A6aR~
        		if (Dump.Y) Dump.println("WiFiDirectActivity:connect failure reason="+reason);//~1A65I~
            }
        });
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
        Dump.println(e,"WiFiDirectActivity:connect");              //~1A65I~
      }                                                            //~1A65I~
    }

    @Override
    public void disconnect() {
      try                                                          //~1A65I~
      {                                                            //~1A65I~
      	if (Dump.Y) Dump.println("WiFiActivity:disconnect");       //~1A65I~
//      final DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()//~1A65R~
//              .findFragmentById(R.id.frag_detail);               //~1A65R~
        final DeviceDetailFragment fragment =WDA.getDeviceDetailFragment();//~1A65R~
        fragment.resetViews();
        manager.removeGroup(channel, new ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
//              Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);//~1A65R~
        		if (Dump.Y) Dump.println("WiFiDirectActivity:disconnect removeGroup failure reason="+reasonCode);//~1A65I~//~1Ac0R~
				String msg="Disconnect failed. Reason :" + reasonCode;//~1A6aI~
    			notifyConnected(DISCONNECT_ER,msg);                 //~1A6aR~
            }

            @Override
            public void onSuccess() {
        		if (Dump.Y) Dump.println("WiFiDirectActivity:disconnect removeGroup() success");//~1Ac0I~
                fragment.getView().setVisibility(View.GONE);
    			notifyConnected(DISCONNECT_OK,null);               //~1A6aR~
//              WDA.setWifiDirectStatus(false);//@@@@test          //~1Ac4R~
            }

        });
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
        Dump.println(e,"WiFiDirectActivity:disconnect");           //~1A65I~
      }                                                            //~1A65I~
    }

    @Override
    public void onChannelDisconnected() {
      try                                                          //~1A65I~
      {                                                            //~1A65I~
      	if (Dump.Y) Dump.println("WiFiActivity:onChannelDisconnected");//~1A67I~
        // we will try once more
        if (manager != null && !retryChannel) {
//          Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_LONG).show();//~1A65R~
            Toast.makeText(getContext(),WDA.getResourceString(R.string.ChannelLostRetry), Toast.LENGTH_LONG).show();//~1A65R~
            resetData();
            retryChannel = true;
//          manager.initialize(this, getMainLooper(), this);       //~1A65R~
            manager.initialize(getContext(), getMainLooper(), this);//~1A65I~
        } else {
//          Toast.makeText(this,                                   //~1A65R~
            Toast.makeText(getContext(),                           //~1A65I~
//                  "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",//~1A65R~
                    WDA.getResourceString(R.string.ChannelLostEnableP2P),//~1A65I~
                    Toast.LENGTH_LONG).show();
        }
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
        Dump.println(e,"WiFiDirectActivity:onChannelDisconnected");//~1A65I~
      }                                                            //~1A65I~
    }

    @Override
    public void cancelDisconnect() {

      try                                                          //~1A65I~
      {                                                            //~1A65I~
        /*
         * A cancel abort request by user. Disconnect i.e. removeGroup if
         * already connected. Else, request WifiP2pManager to abort the ongoing
         * request
         */
        if (manager != null) {
//          final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()//~1A65R~
//                  .findFragmentById(R.id.frag_list);             //~1A65R~
            final DeviceListFragment fragment = WDA.getDeviceListFragment();//~1A65I~
            if (fragment.getDevice() == null
                    || fragment.getDevice().status == WifiP2pDevice.CONNECTED) {
                disconnect();
            } else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
                    || fragment.getDevice().status == WifiP2pDevice.INVITED) {

                manager.cancelConnect(channel, new ActionListener() {

                    @Override
                    public void onSuccess() {
//                      Toast.makeText(WiFiDirectActivity.this, "Aborting connection",//~1A65R~
//                      Toast.makeText(getContext(),WDA.getResourceString(R.string.AbortingConnection),//~1A65R~//~1A6aR~
//                              Toast.LENGTH_SHORT).show();        //~1A6aR~
                        String msg=WDA.getResourceString(R.string.AbortingConnection);//~1A6aI~
    					notifyConnected(DISCONNECT_CANCEL_OK,msg);   //~1A6aR~
                    }

                    @Override
                    public void onFailure(int reasonCode) {
//                      Toast.makeText(WiFiDirectActivity.this,    //~1A65R~
//                      Toast.makeText(getContext(),               //~1A65I~//~1A6aR~
//                              "Connect abort request failed. Reason Code: " + reasonCode,//~1A65R~
//                              WDA.getResourceString(R.string.ConnectAbortReason) + reasonCode,//~1A65I~//~1A6aR~
//                              Toast.LENGTH_SHORT).show();        //~1A6aR~
                        String msg=WDA.getResourceString(R.string.ConnectAbortReason) + reasonCode;//~1A6aI~
    					notifyConnected(DISCONNECT_CANCEL_ER,msg);   //~1A6aR~
                    }
                });
            }
        }
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
        Dump.println(e,"WiFiDirectActivity:cancelDisconnect");     //~1A65I~
      }                                                            //~1A65I~

    }
    //*****************************************************************//~1A65I~
    private Object getSystemService(String Pservice)               //~1A65I~
    {                                                              //~1A65I~
    	return AG.context.getSystemService(Pservice);              //~1A65I~
    }                                                              //~1A65I~
    //*****************************************************************//~1A65I~
    private Looper getMainLooper()                                 //~1A65I~
    {                                                              //~1A65I~
    	return AG.context.getMainLooper();                         //~1A65I~
    }                                                              //~1A65I~
    //*****************************************************************//~1A65I~
    private Context getContext()                                   //~1A65I~
    {                                                              //~1A65I~
    	return AG.context;                                         //~1A65I~
    }                                                              //~1A65I~
    //*****************************************************************//~1A65I~
    private Activity getActivity()                                 //~1A65I~
    {                                                              //~1A65I~
    	return AG.activity;                                        //~1A65I~
    }                                                              //~1A65I~
    //*****************************************************************//~1A65I~
    private void startNFC()                                        //~1A6aI~
    {                                                              //~1A6aI~
    	if (Dump.Y) Dump.println("WiFiDirectActivity startNFC");   //~1A6aI~
        try                                                        //~1A6aI~
        {                                                          //~1A6aI~
	        new WDANFC();                                          //~1A6aR~
        }                                                          //~1A6aI~
        catch(Exception e)                                         //~1A6aI~
        {                                                          //~1A6aI~
        	Dump.println(e,"WiFiDirectActivity");                  //~1A6aI~
        }                                                          //~1A6aI~
    }                                                              //~1A6aI~
    //*****************************************************************//~1A6aI~
//  private void notifyConnected(int Presult,String Pmsg)               //~1A6aR~//~1A6sR~
    protected void notifyConnected(int Presult,String Pmsg)        //~1A6sI~
    {                                                              //~1A6aI~
      try                                                          //~1Ac0I~
      {                                                            //~1Ac0I~
    	if (Dump.Y) Dump.println("WiFiDirectActivity notifyConnected rc="+Presult);//~1A6aI~//~1Ac0R~
        DeviceDetailFragment fragmentDetails=WDA.getDeviceDetailFragment();//~1A6aI~
        if (Pmsg!=null)                                            //~1A6aI~
	        fragmentDetails.connected(Presult,Pmsg);                //~1A6aI~
                                                                   //~1A6aI~
        switch(Presult)                                            //~1A6aI~
        {                                                          //~1A6aI~
        case CONNECT_OK:                                           //~1A6aI~
        	WDA.SWDA.connected();                              //~1A65I~//~1A6aI~
            break;                                                 //~1A6aI~
        case CONNECT_ER:                                           //~1A6aI~
        	WDA.SWDA.connectError();                               //~1A6aI~
            break;                                                 //~1A6aI~
        case DISCONNECT_OK:                                        //~1A6aI~
        	WDA.SWDA.disconnected();                               //~1A6aI~
            break;                                                 //~1A6aI~
        case DISCONNECT_ER:                                        //~1A6aI~
            break;                                                 //~1A6aI~
        case DISCONNECT_CANCEL_OK:                                 //~1A6aI~
            break;                                                 //~1A6aI~
        case DISCONNECT_CANCEL_ER:                                 //~1A6aI~
            break;                                                 //~1A6aI~
        }                                                          //~1A6aI~
//      Utils.chkNetwork();//@@@@test                              //~1Ac0I~//+1Ac4R~
      }                                                            //~1Ac0I~
      catch(Exception e)                                           //~1Ac0I~
      {                                                            //~1Ac0I~
          Dump.println(e,"WiFiDirectActivity:notifyConnected");    //~1Ac0I~
      }                                                            //~1Ac0I~
    }                                                              //~1A6aI~
    //*****************************************************************//~1A6aI~
    //*rc:-1:not found,1:paired,0:not paired(do connect)           //~1A6aI~
    //*****************************************************************//~1A6aI~
    public int discover(String Pmacaddr,boolean Pdodiscover)       //~1A6aR~
    {                                                              //~1A6aI~
    	if (Dump.Y) Dump.println("WiFiDirectActivity discover:"+Pmacaddr);//~1A6aI~
        DeviceListFragment fragmentList=WDA.getDeviceListFragment();//~1A6aI~
        int rc=fragmentList.chkDiscovered(Pmacaddr);               //~1A6aR~
        if (Pdodiscover && rc==-1)                                 //~1A6aR~
            buttonAction(DeviceListFragment.BTNID_DISCOVER);       //~1A6aR~
        return rc;                                                 //~1A6aR~
    }                                                              //~1A6aI~
    //*****************************************************************//~1A6aI~
    public void discover()                                          //~1A6aR~
    {                                                              //~1A6aI~
    	if (Dump.Y) Dump.println("WiFiDirectActivity discover sender side");//~1A6aI~
	    buttonAction(DeviceListFragment.BTNID_DISCOVER);           //~1A6aR~
    }                                                              //~1A6aI~
 }
