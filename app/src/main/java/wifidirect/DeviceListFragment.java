//*CID://+1Aa5R~:                             update#=   89;       //+1Aa5R~
//*************************************************************************//~1A65I~
//1Aa5 2015/04/20 test function for mdpi listview                  //+1Aa5I~
//1A8p 2015/04/10 listview devcice list for mdpi                   //~1A8pI~
//1A8n 2015/04/09 Wi-Fi direct ip addr is not shown                //~1A8nI~
//1A6t 2015/02/18 (BUG)simpleProgress Dialog thread err exception  //~1A6tI~
//1A6e 2015/02/13 runOnUiThread for processingdialog               //~1A6eI~
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

import android.annotation.TargetApi;                               //~1A65I~
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
//import android.content.res.Resources;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
//import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import jagoclient.Dump;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.Asgts.AG;
import com.Asgts.R;
import com.Asgts.URunnable;
import com.Asgts.URunnableData;                                    //~1A6tR~

/**
 * A ListFragment that displays available peers on discovery and requests the
 * parent activity to handle user interaction events
 */
@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14                           //~1A65R~
//public class DeviceListFragment extends ListFragment implements PeerListListener {//~1A65R~
public class DeviceListFragment implements PeerListListener {      //~1A65R~

	private static final int LAYOUTID_LIST_ROW=R.layout.textrowdevice_wd;//~1A65I~
	private static final int LAYOUTID_LIST_ROW_MDPI=R.layout.textrowdevice_wd_mdpi;//~1A6pI~//~1A8pI~
	private static final int COLOR_SELECTED=0xff000080;            //~1A65I~
	public  static final int BTNID_DISCOVER=R.id.atn_direct_discover;//~1A65I~
	public  static final int BTNID_P2PENABLE=R.id.atn_direct_enable;//~1A67I~
	public  static final int BTNID_NFC=R.id.WiFiNFCButton;         //~1A6aI~
                                                                   //~1A65I~
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
//  ProgressDialog progressDialog = null;                          //~1A6tR~
    URunnableData progressDialog = null;                           //~1A6tI~
    View mContentView = null;
    private WifiP2pDevice device;
    private ListView listView;                                     //~1A65I~
	private WiFiPeerListAdapter adapter;                           //~1A65I~
	private ViewGroup layoutView;                                  //~1A65I~
	private TextView tvMyName,tvMyStatus,tvEmptyMsg,tvMyOwner;     //~1A65R~
	private int selectedPos=-1;                                    //~1A65I~
    public String thisDevice;                                      //~1A65R~
    public String thisDeviceAddr;                                  //~1A6aI~
    public int thisStatus=-1;                                       //~1A65I~
    public int thisOwner=-1;                                       //~1A65I~
    private Button btnDiscover;                                    //~1A65I~
    private Button btnP2PEnable;                                   //~1A67I~
    private Button btnNFC;                                         //~1A6aI~
    private int resid_textrow;                                     //~1A8pI~
    //******************************************                   //~1A65I~
    public DeviceListFragment()                                    //~1A65I~
    {                                                              //~1A65I~
    }                                                              //~1A65I~
    //******************************************                   //~1A65I~
//    @Override                                                    //~1A65R~
//    public void onActivityCreated(Bundle savedInstanceState) {   //~1A65R~
//        super.onActivityCreated(savedInstanceState);             //~1A65R~
//        this.setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.row_devices, peers));//~1A65R~

//    }                                                            //~1A65R~

//    @Override                                                    //~1A65R~
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//~1A65R~
//        mContentView = inflater.inflate(R.layout.device_list, null);//~1A65R~
//        return mContentView;                                     //~1A65R~
//    }                                                            //~1A65R~
    //******************************************                   //~1A65I~
    public void initFragment(ViewGroup Playoutview)                //~1A65R~
    {                                                              //~1A65I~
        resid_textrow=AG.layoutMdpi ? LAYOUTID_LIST_ROW_MDPI : LAYOUTID_LIST_ROW;//~1A8pI~
    	layoutView=Playoutview;                                    //~1A65I~
		tvMyName=(TextView)layoutView.findViewById(R.id.my_name);  //~1A65I~
		tvMyStatus=(TextView)layoutView.findViewById(R.id.my_status);//~1A65I~
		tvMyOwner=(TextView)layoutView.findViewById(R.id.my_groupowner);//~1A65I~
		tvEmptyMsg=(TextView)layoutView.findViewById(R.id.emptymsg);//~1A65I~
    	listView=(ListView)layoutView.findViewById(R.id.device_list);//~1A65R~
//  	adapter=new WiFiPeerListAdapter(getActivity(),LAYOUTID_LIST_ROW,peers);//~1A65R~//~1A8pR~
    	adapter=new WiFiPeerListAdapter(getActivity(),resid_textrow,peers);//~1A8pI~
        listView.setAdapter(adapter);                                 //~1A65I~
        OnItemClickListener itemlistener=new ListItemClickListener(this);//~1A65I~
        listView.setOnItemClickListener(itemlistener);             //~1A65I~
    	btnDiscover=(Button)layoutView.findViewById(BTNID_DISCOVER);//~1A65I~
        WDA.SWDA.setButtonListener(btnDiscover);                   //~1A65I~
    	btnP2PEnable=(Button)layoutView.findViewById(BTNID_P2PENABLE);//~1A67I~
        WDA.SWDA.setButtonListener(btnP2PEnable);                  //~1A67I~
    	btnNFC=(Button)layoutView.findViewById(BTNID_NFC);         //~1A6aI~
        WDA.SWDA.setButtonListener(btnNFC);                        //~1A6aI~
    }                                                              //~1A65I~
    //******************************************                   //~1A65I~

    /**
     * @return this device
     */
    public WifiP2pDevice getDevice() {
        return device;
    }

//  private static String getDeviceStatus(int deviceStatus) {      //~1A65R~
    public static String getDeviceStatus(int deviceStatus)         //~1A65I~
    {                                                              //~1A65I~
//      Log.d(WiFiDirectActivity.TAG, "Peer status :" + deviceStatus);//~1A65R~
        if (Dump.Y) Dump.println("DeviceListFragment:getDeviceStatus:Peer status :" + deviceStatus);//~1A65R~
        switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
//              return "Available";                                //~1A65R~
                return WDA.getResourceString(R.string.DeviceAvailable);//~1A65I~
            case WifiP2pDevice.INVITED:
//              return "Invited";                                  //~1A65R~
                return WDA.getResourceString(R.string.DeviceInvited);//~1A65I~
            case WifiP2pDevice.CONNECTED:
//              return "Connected";                                //~1A65R~
                return WDA.getResourceString(R.string.DeviceConnected);//~1A65I~
            case WifiP2pDevice.FAILED:
//              return "Failed";                                   //~1A65R~
                return WDA.getResourceString(R.string.DeviceFailed);//~1A65I~
            case WifiP2pDevice.UNAVAILABLE:
//              return "Unavailable";                              //~1A65R~
                return WDA.getResourceString(R.string.DeviceUnavailable);//~1A65I~
            default:
//              return "Unknown";                                  //~1A65R~
                return WDA.getResourceString(R.string.DeviceUnknown);//~1A65I~

        }
    }
    //******************************************************************************//~1A65R~
    /**
     * Initiate a connection with the peer.
     */
//  @Override                                                      //~1A65R~
    public void onListItemClick(ListView l, View v, int position, long id) {
        WifiP2pDevice device = (WifiP2pDevice) getListAdapter().getItem(position);
//      ((DeviceActionListener) getActivity()).showDetails(device);//~1A65R~
        ((DeviceActionListener) WDA.getWDActivity()).showDetails(device);//~1A65I~
        selectedPos=position;                                      //~1A65I~
        ((WiFiPeerListAdapter)getListAdapter()).notifyDataSetChanged(); //call getView()//~1A67I~
        if (Dump.Y) Dump.println("DeviceListFragment onListItemClick selectedpos="+position);//~1A67I~
    }

    //******************************************************************************//~1A65I~
    /**
     * Array adapter for ListFragment that maintains WifiP2pDevice list.
     */
    private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {

        private List<WifiP2pDevice> items;

        /**
         * @param context
         * @param textViewResourceId
         * @param objects
         */
        public WiFiPeerListAdapter(Context context, int textViewResourceId,
                List<WifiP2pDevice> objects) {
            super(context, textViewResourceId, objects);
            items = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
          try                                                      //~1A65I~
          {                                                        //~1A65I~
          	if (Dump.Y) Dump.println("DeviceListFragment:getView pos="+position);//~1A65I~
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
//              v = vi.inflate(R.layout.row_devices, null);        //~1A65R~
//              v = vi.inflate(LAYOUTID_LIST_ROW,null);            //~1A65I~//~1A8pR~
                v = vi.inflate(resid_textrow,null);                //~1A8pI~
            }
            WifiP2pDevice device = items.get(position);
            if (device != null) {
                TextView top = (TextView) v.findViewById(R.id.device_name);
                TextView bottom = (TextView) v.findViewById(R.id.device_details);
                if (top != null) {
//                  top.setText(device.deviceName);                //~1A67R~
                    top.setText(DeviceDetailFragment.getDeviceName(device));//~1A67I~
                    if (position==selectedPos)                     //~1A65I~
                    {                                              //~1A65I~
                		top.setBackgroundColor(COLOR_SELECTED);    //~1A65I~
                		top.setTextColor(Color.WHITE);             //~1A65I~
                        if (Dump.Y) Dump.println("DeviceListFragment:getView pos==selcted="+position);//~1A67I~
                    }                                              //~1A65I~
                    else                                           //~1A65I~
                    {                                              //~1A65I~
                        if (Dump.Y) Dump.println("DeviceListFragment:getView pos!=selcted="+selectedPos);//~1A67R~
                		top.setBackgroundColor(Color.WHITE);       //~1A65I~
                		top.setTextColor(Color.BLACK);             //~1A65I~
                    }                                              //~1A65I~
                }
                if (bottom != null) {
                    String ipa=IPConnection.getRemoteIPAddr(true/*return null if N/A*/);//~1A6tR~//~1A8nI~
//                  if (ipa==null)                                 //~1A6tI~//~1A6nR~//~1A8nI~
                    if (ipa!=null)                                 //~1A6nI~//~1A8nI~
                    	bottom.setText(ipa);                       //~1A6tI~//~1A8nI~
                    else                                           //~1A6tI~//~1A8nI~
                    bottom.setText(getDeviceStatus(device.status));
                }
//                TextView owner=(TextView) v.findViewById(R.id.device_owner);//~1A65R~
//                if (owner != null) {                             //~1A65R~
//                    updateOwner(device,owner);                   //~1A65R~
//                }                                                //~1A65R~
            }
          }                                                        //~1A65R~
          catch(Exception e)                                       //~1A65I~
          {                                                        //~1A65I~
          	Dump.println(e,"DeviceListFragment:getView");          //~1A65I~
          }                                                        //~1A65I~
            return v;

        }
    }

    /**
     * Update UI for this device.
     * 
     * @param device WifiP2pDevice object
     */
    public void updateThisDevice(WifiP2pDevice device) {
        this.device = device;
        thisStatus=device.status;                                  //~1A65I~
        if (Dump.Y) Dump.println("DeviceListFragment:updateThis status="+thisStatus);//~1A65I~
        if (Dump.Y) Dump.println("DeviceListFragment updateThis:name="+device.deviceName+",addr="+device.deviceAddress);//~1A6aI~
//      TextView view = (TextView) mContentView.findViewById(R.id.my_name);//~1A65R~
        TextView view =tvMyName;                                   //~1A65I~
//      view.setText(device.deviceName);                           //~1A67R~
        view.setText(DeviceDetailFragment.getDeviceName(device));                        //~1A67I~
        thisDevice=device.deviceName;                              //~1A65I~
        thisDeviceAddr=device.deviceAddress;                      //~1A6aI~
                                                                   //~1A65I~
//      view = (TextView) mContentView.findViewById(R.id.my_status);//~1A65R~
        view =tvMyStatus;                                          //~1A65I~
        view.setText(getDeviceStatus(device.status));
        view =tvMyOwner;                                           //~1A65I~
        updateThisOwner(device);                               //~1A65R~
        if (Dump.Y) Dump.println("DeviceListFragment:updateThisDevice device="+device.deviceName+",owner="+device.isGroupOwner());//~1A65I~
	    setEmptyMsg();                                             //~1A65I~
    }

    //***************************************************************************//~1A65I~
    //*PeerListListener                                            //~1A65I~
    //***************************************************************************//~1A65I~
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {
      try                                                          //~1A65I~
      {                                                            //~1A65I~
      	Collection<WifiP2pDevice> devices=peerList.getDeviceList();//~1A65I~
      	if (Dump.Y) Dump.println("onPeersAvailable list count="+devices.size());//~1A65I~
//      if (progressDialog != null && progressDialog.isShowing()) {//~1A6eR~
//          progressDialog.dismiss();                              //~1A6eR~
//      }                                                          //~1A6eR~
    	dismissProgressDialog();                                   //~1A6eI~
        selectedPos=-1;                                            //~1A65I~
        if (Dump.Y) Dump.println("DeviceListFragment:onPeerAvailable slectedpos="+selectedPos);//~1A67I~
        peers.clear();
//      peers.addAll(peerList.getDeviceList());                    //~1A65R~
        peers.addAll(devices);                                     //~1A65I~
        if (peers.size() != 0)                                     //+1Aa5I~
        {                                                          //+1Aa5I~
            if (WiFiDirectActivity.Stestdevicelist)                //+1Aa5I~
            {                                                      //+1Aa5I~
                WifiP2pDevice dup=peers.get(0);                    //+1Aa5I~
                peers.add(dup);                                    //+1Aa5I~
                peers.add(dup);                                    //+1Aa5I~
                peers.add(dup);                                    //+1Aa5I~
                peers.add(dup);                                    //+1Aa5I~
                peers.add(dup);                                    //+1Aa5I~
            }                                                      //+1Aa5I~
        }                                                          //+1Aa5I~
        else                                                       //+1Aa5I~
        {                                                          //+1Aa5I~
            if (WiFiDirectActivity.Stestdevicelist_mdpi)           //+1Aa5I~
            {                                                      //+1Aa5I~
        		WifiP2pDevice dup=new WifiP2pDevice();             //+1Aa5I~
            	peers.add(dup);                                    //+1Aa5I~
            	peers.add(dup);                                    //+1Aa5I~
            	peers.add(dup);                                    //+1Aa5I~
            	peers.add(dup);                                    //+1Aa5I~
            	peers.add(dup);                                    //+1Aa5I~
            }                                                      //+1Aa5I~
        }                                                          //+1Aa5I~
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
//      WDA.SWDA.peerUpdated();	//issue connect                    //~1A6aI~//~1A6tI~
        WDA.SWDA.peerUpdated(peers.size());	//issue connect        //~1A6tI~
        if (peers.size() == 0) {
//          Log.d(WiFiDirectActivity.TAG, "No devices found");     //~1A65R~
            if (Dump.Y) Dump.println("No devices found");          //~1A65I~
	        tvEmptyMsg.setText(WDA.getResourceString(R.string.empty_message));//~1A65R~
            WDA.getDeviceDetailFragment().getView().setVisibility(View.GONE);	//drop right half//~1A65R~
            return;
        }
        else                                                       //~1A65I~
        {                                                          //~1A65I~
	    	setEmptyMsg();                                         //~1A65I~
        }                                                          //~1A65I~
      }                                                            //~1A65I~
      catch(Exception e)                                           //~1A65I~
      {                                                            //~1A65I~
      	Dump.println(e,"DeviceListFragment:onPeerAvailable");      //~1A65I~
      }                                                            //~1A65I~

    }

    public void clearPeers() {
        peers.clear();
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    /**
     * 
     */
    public void onInitiateDiscovery() {
//      if (progressDialog != null && progressDialog.isShowing()) {//~1A6eR~
//          progressDialog.dismiss();                              //~1A6eR~
//      }                                                          //~1A6eR~
    	dismissProgressDialog();                                   //~1A6eI~
//      progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel", "finding peers", true,//~1A65R~
//      progressDialog = ProgressDialog.show(getActivity(),WDA.getResourceString(R.string.ProgressDialogTitle),WDA.getResourceString(R.string.ProgressDialogMsgFindingPeer), true,//~1A65I~//~1A6eR~
//              true, new DialogInterface.OnCancelListener() {     //~1A6eR~
//                                                                 //~1A6eR~
//                  @Override                                      //~1A6eR~
//                  public void onCancel(DialogInterface dialog) { //~1A6eR~
//                      if (Dump.Y) Dump.println("onInitiateDiscovery ProgressDialog canceled");//~1A65R~//~1A6eR~
//                  }                                              //~1A6eR~
//              });                                                //~1A6eR~

        progressDialog=progressDialogShow(R.string.ProgressDialogTitle,//~1A6eI~
					     					WDA.getResourceString(R.string.ProgressDialogMsgFindingPeer),//~1A6eI~
						    				true,true);//onCancel ignored//~1A6eI~
    }
    /**
     * An interface-callback for the activity to listen to fragment interaction
     * events.
     */
    public interface DeviceActionListener {

        void showDetails(WifiP2pDevice device);

        void cancelDisconnect();

        void connect(WifiP2pConfig config);

        void disconnect();
    }
//**********************************************************************//~1A65I~
//*itemclicklistener                                               //~1A65I~
//**********************************************************************//~1A65I~
    class ListItemClickListener implements OnItemClickListener     //~1A65I~
    {                                                              //~1A65I~
    	DeviceListFragment parent;                                 //~1A65I~
    	public ListItemClickListener(DeviceListFragment Pparent)//~1A65I~
        {                                                          //~1A65I~
        	parent=Pparent;                                        //~1A65I~
        }                                                          //~1A65I~
    	@Override                                                  //~1A65I~
        public void onItemClick(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1A65I~
        {                                                          //~1A65I~
        	try                                                    //~1A65I~
            {                                                      //~1A65I~
		    	parent.onListItemClick(null,Ptextview,Ppos,Pid);   //~1A65R~
            }                                                      //~1A65I~
            catch(Exception e)                                     //~1A65I~
            {                                                      //~1A65I~
            	Dump.println(e,"DeviceListFragment:onItemClick");  //~1A65I~
            }                                                      //~1A65I~
        }                                                          //~1A65I~
    }//subclass                                                    //~1A65R~
	//*******************************************************************************************************//~1A65I~
	//*from updateThis                                             //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public boolean updateThisOwner(WifiP2pDevice device)           //~1A65R~
    {                                                              //~1A65I~
    	boolean owner=updateOwner(device,tvMyOwner);
    	thisOwner=owner?1:0;//~1A65R~
	    setEmptyMsg();                                            //~1A65R~
        if (Dump.Y) Dump.println("DeviceListFragment:updateThisOwner="+thisOwner);//~1A65I~
        return owner;                                          //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65I~
	//*from updateThis                                             //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public boolean updateOwner(WifiP2pDevice device,TextView Pview)//~1A65R~
    {                                                              //~1A65I~
    	boolean rc=false;                                          //~1A65I~
        if (Dump.Y) Dump.println("updateOwner name="+device.deviceName+",device.status="+device.status+",isGroupOwner="+device.isGroupOwner());//~1A8pI~
        if (device.status!=WifiP2pDevice.CONNECTED)                //~1A65I~
	        Pview.setText("");                                     //~1A65I~
        else                                                       //~1A65I~
        	rc=updateOwner(device.isGroupOwner(),Pview);           //~1A65R~
        return rc;                                                 //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65I~
	//*from DetailFragment                                         //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public boolean updateThisOwner(boolean Powner)                 //~1A65R~
    {                                                              //~1A65I~
	    updateOwner(Powner,tvMyOwner);                             //~1A65R~
    	thisOwner=Powner?1:0;                                      //~1A65I~
	    setEmptyMsg();                                             //~1A65I~
        if (Dump.Y) Dump.println("DeviceListFragment:updateThisOwner="+Powner);//~1A65I~
        return Powner;                                             //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public boolean updateOwner(boolean Powner,TextView Pview)      //~1A65R~
    {                                                              //~1A65I~
        Pview.setText(Powner ? WDA.getResourceString(R.string.MyOwnerYes) : WDA.getResourceString(R.string.MyOwnerNo));//~1A65I~
        if (Dump.Y) Dump.println("updateOwner owner="+Powner); //~1A65I~
        return Powner;                                             //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65I~
    public void setEmptyMsg()                                      //~1A65I~
    {                                                              //~1A65I~
        int msgid=R.string.empty;                                  //~1A65I~
        if (thisStatus==WifiP2pDevice.CONNECTED)                   //~1A65I~
        {                                                          //~1A65I~
        	if (thisOwner==0)                                      //~1A65I~
            	msgid=R.string.performIPConnect;                   //~1A65I~
            else                                                   //~1A65I~
            	msgid=R.string.performIPAccept;                    //~1A65I~
        }                                                          //~1A65I~
        else                                                       //~1A65I~
        	if (peers.size()!=0)                                   //~1A65I~
	        	msgid=R.string.deviceFound;                        //~1A65I~
	    tvEmptyMsg.setText(WDA.getResourceString(msgid));          //~1A65I~
        if (Dump.Y) Dump.println("DeviceListFragment:setEmptyMsg="+tvEmptyMsg.getText().toString());//~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A6aI~
	//*return status,-1:not found,1:connected,0:do connect         //~1A6aR~
	//*******************************************************************************************************//~1A6aI~
    public int chkDiscovered(String Pmacaddr)                      //~1A6aI~
    {                                                              //~1A6aI~
    	int rc=-1;  //not found                                    //~1A6aR~
    //*****************************                                //~1A6aI~
        int ctr=peers.size();                                      //~1A6aR~
        for (int ii=0;ii<ctr;ii++)                                  //~1A6aI~
        {                                                          //~1A6aI~
        	WifiP2pDevice dev=peers.get(ii);                       //~1A6aI~
	        if (Dump.Y) Dump.println("DeviceListFragment:chkDiscovered perr device="+dev.deviceAddress);//~1A6aI~
            if (Pmacaddr.equals(dev.deviceAddress))                //~1A6aI~
            {                                                      //~1A6aI~
                int status=dev.status;                             //~1A6aI~
            	rc=0;	//issue connect                            //~1A6aI~
                switch(status)                                     //~1A6aI~
                {                                                  //~1A6aI~
            	case WifiP2pDevice.CONNECTED:    //0               //~1A6aI~
					rc=1; 	//alread paired                        //~1A6aM~
					break;                                         //~1A6aM~
            	case WifiP2pDevice.INVITED:      //1               //~1A6aI~
					break;                                         //~1A6aM~
                case WifiP2pDevice.FAILED:       //2               //~1A6aI~
					break;                                         //~1A6aM~
            	case WifiP2pDevice.AVAILABLE:    //3               //~1A6aR~
					break;                                         //~1A6aI~
            	case WifiP2pDevice.UNAVAILABLE:  //4               //~1A6aR~
					break;                                         //~1A6aI~
                default:                                           //~1A6aI~
                }                                                  //~1A6aI~
            }                                                      //~1A6aI~
        }   	                                                   //~1A6aI~
        if (Dump.Y) Dump.println("DeviceListFragment:chkDiscovered for "+Pmacaddr+",rc="+rc);//~1A6aI~
        return rc;                                                 //~1A6aR~
    }                                                              //~1A6aI~
	//*******************************************************************************************************//~1A65R~
    private WiFiPeerListAdapter getListAdapter()                   //~1A65I~
    {                                                              //~1A65I~
        return adapter;                                            //~1A65I~
    }                                                              //~1A65I~
	//*******************************************************************************************************//~1A65R~
//    private View getView()                                       //~1A65R~
//    {                                                            //~1A65R~
//        return layoutView.findViewById(R.id.device_list_wd);//AxeDialog:layoutView//~1A65R~
//    }                                                            //~1A65R~
	//*******************************************************************************************************//~1A65I~
	private Activity getActivity()                                 //~1A65I~
    {                                                              //~1A65I~
        return AG.activity;                                        //~1A65I~
    }                                                              //~1A65I~
//    //*******************************************************************************************************//~1A65R~
//    private Resources getResources()                             //~1A65R~
//    {                                                            //~1A65R~
//        return AG.resource;                                      //~1A65R~
//    }                                                            //~1A65R~
	//*******************************************************************************************************//~1A67I~//~1A6eI~
    private void dismissProgressDialog()                           //~1A67I~//~1A6eI~
    {                                                              //~1A67I~//~1A6eI~
        URunnable.dismissDialog(progressDialog);                       //~1A67I~//~1A6eI~
    }                                                              //~1A67I~//~1A6eI~
//****************************************                         //~1A6eI~
//  private ProgressDialog progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A6eI~//~1A6tR~
    private URunnableData progressDialogShow(int Ptitleid,String Pmsg,boolean Pindeterminate,boolean Pcancelable)//~1A6tI~
    {                                                              //~1A6eI~
        if (Dump.Y) Dump.println("ProgDialog:simpleProgressDialogShow");//~1A6eI~
        return URunnable.simpleProgressDialogShow(Ptitleid,Pmsg,Pindeterminate,Pcancelable);//~1A6eI~
    }                                                              //~1A6eI~
}
