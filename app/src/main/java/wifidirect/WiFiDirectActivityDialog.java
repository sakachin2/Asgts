//*CID://+1Ac0R~:                             update#=  136;       //~1Ac0R~
//*************************************************************************
//1Ac0 2015/07/06 for mutual exclusive problem of IP and wifidirect;try to use connectivityManager API//~1Ac0I~
//1Abx 2015/06/21 NFCWD:dismiss waiting dialog of makepair at disconnected(with no Accesspoint status)//~1AbxI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame
//*************************************************************************
package wifidirect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jagoclient.Dump;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

import com.Asgts.AG;
import com.Asgts.AView;
import com.Asgts.R;
import com.Asgts.UiThread;
import com.Asgts.UiThreadI;
import com.Asgts.Utils;

//*************************************************************************
//*************************************************************************
@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14
class WiFiDirectActivityDialog extends WiFiDirectActivity
        implements ConnectionInfoListener,GroupInfoListener,PeerListListener
{
    private DialogNFC dialogNFC;
    ReceiverDialog receiver;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    public WiFiDirectActivityDialog(DialogNFC Pnfc)
    {
        super();
        if (Dump.Y) Dump.println("DialogNFC:WiFiDirectActivityDialog:cobstructor");
        dialogNFC=Pnfc;
    }
	//*************************************************************************
    @Override
    public void registerReceiver()
    {
    	if (Dump.Y) Dump.println("WiFiDirectActivityDialog:registerReceiver");
        receiver=new ReceiverDialog(manager,channel,this,dialogNFC);
        AG.context.registerReceiver(receiver,intentFilter);
    }
	//*************************************************************************
    @Override
    public void unregisterReceiver()
    {
    	if (Dump.Y) Dump.println("WiFiDirectActivityDialog:unregisterReceiver");
		if (receiver==null)
        	return;
        AG.context.unregisterReceiver(receiver);
        receiver=null;
    }
    //*****************************************************************
    //*from DialogNFC:onDismiss()
    //*****************************************************************
    public void onDismiss()
    {
    	if (Dump.Y) Dump.println("WiFiDirectActivityDialog:onDismiss");
    	unregisterReceiver();
    }
    //*****************************************************************
    public boolean discoverButton()
    {
        if (Dump.Y) Dump.println("WiFiDirectActivityDialog:discover");
        try
        {
            if (!isWifiP2pEnabled)
            {
                AView.showToast(R.string.p2p_off_warning);
                return false;
            }
	        if (Dump.Y) Dump.println("WiFiDirectActivityDialog:discoverButton issue manager.discoverPeer");
            manager.discoverPeers(channel,new WifiP2pManager.ActionListener()
            {
                @Override
                public void onSuccess()
                {
//                  AView.showToast(R.string.DiscoveryInitiated);
                    if (Dump.Y) Dump.println("WiFiDirectActivityDialog:discover onSuccess");
                }
                @Override
                public void onFailure(int reasonCode)
                {
                    AView.showToastLong(R.string.DiscoveryFailedReason,Integer.toString(reasonCode));
                    if (Dump.Y) Dump.println("WiFiDirectActivity discover onFailure reason="+reasonCode);
                }
            });
        }
        catch(Exception e)
        {
            Dump.println(e,"WiFiDirectActivityDialog:discover");
            return false;
        }
        return true;
    }
    //*****************************************************************//~5218I~
    @Override                                                      //~5218I~
    public void disconnect()                                       //~5218I~
	{                                                              //~5218I~
        try                                                          //~1A65I~//~5218I~
        {                                                            //~1A65I~//~5218I~
            if (Dump.Y) Dump.println("WiFiActivityDialog:disconnect issue removegroup");       //~1A65I~//~5218I~
            manager.removeGroup(channel, new ActionListener()      //~5218I~
			{                                                      //~5218I~
                @Override                                          //~5218I~
                public void onFailure(int reasonCode)              //~5218I~
				{                                                  //~5218I~
                    if (Dump.Y) Dump.println("WiFiDirectActivityDialog:disconnect failure reason="+reasonCode);//~1A65I~//~5218I~
                    String msg="Disconnect failed. Reason :" + reasonCode;//~1A6aI~//~5218I~
                    notifyConnected(DISCONNECT_ER,msg);                 //~1A6aR~//~5218I~
                }                                                  //~5218I~
                @Override                                          //~5218I~
                public void onSuccess()                            //~5218I~
				{                                                  //~5218I~
                    notifyConnected(DISCONNECT_OK,null);               //~1A6aR~//~5218I~
                }                                                  //~5218I~
                                                                   //~5218I~
            });                                                    //~5218I~
        }                                                            //~1A65I~//~5218I~
        catch(Exception e)                                           //~1A65I~//~5218I~
        {                                                            //~1A65I~//~5218I~
          	Dump.println(e,"WiFiDirectActivityDialog:disconnect");           //~1A65I~//~5218I~
        }                                                            //~1A65I~//~5218I~
    }                                                              //~5218I~
    //*****************************************************************
    @Override
    protected void notifyConnected(int Presult,String Pmsg)
    {
        if (Dump.Y) Dump.println("WiFiDirectActivity notifyConnected rc="+Presult);//~5218R~
        try
        {
            switch(Presult)
            {
            case CONNECT_OK:
                dialogNFC.connected();
                break;
            case CONNECT_ER:
                dialogNFC.connectError();
                break;
            case DISCONNECT_OK:
                dialogNFC.disconnected();
                break;
            case DISCONNECT_ER:
                break;
            case DISCONNECT_CANCEL_OK:
                break;
            case DISCONNECT_CANCEL_ER:
                break;
            case DISCONNECTED:                                     //~5219I~
                dialogNFC.disconnected();                          //~5219I~
                break;                                             //~5219I~
            }
//          Utils.chkNetwork();//@@@@test                          //~1AbxI~//+1Ac0R~
        }
        catch(Exception e)
        {
            Dump.println(e,"WiFiDirectActivityDialog:notifyConnected");
        }
    }
    @Override
    public void onGroupInfoAvailable(final WifiP2pGroup Pgroup)
    {
    //*******************
        try
        {
            String peerDevice;
            boolean owner=Pgroup.isGroupOwner();
            WifiP2pDevice deviceOwner=Pgroup.getOwner();
            if (Dump.Y) Dump.println("WiFiDirectActivityDialog:onGroupInfoAvailable owner="+owner+",ownername="+deviceOwner.deviceName);
            Collection<WifiP2pDevice> clients=Pgroup.getClientList();
            List<WifiP2pDevice> clientList=new ArrayList<WifiP2pDevice>();
            clientList.addAll(clients);
            int sz=clientList.size();
            if (Dump.Y) Dump.println("DeviceDetailFragment:onGroupInfoAvailable client list ctr="+sz);
            int clientCtr=0;
            String peers="";
            for (int ii=0;ii<sz;ii++)
            {
                WifiP2pDevice client=clientList.get(ii);
                int status=client.status;
                if (Dump.Y) Dump.println("DeviceDetailFragment:onGroupInfoAvailable clientList name="+client.deviceName+",addr="+client.deviceAddress+",status="+DeviceListFragment.getDeviceStatus(status)+",owner="+client.isGroupOwner());
                if (status==WifiP2pDevice.CONNECTED
                &&  !client.isGroupOwner())
                {
                    clientCtr++;
                    peers+=DeviceDetailFragment.getDeviceName(client)+" ";
                }
            }
            if (owner)
                if (clientCtr!=0)
                    peerDevice=peers.trim();
                else
                    peerDevice="None";
            else
            {
                peerDevice=DeviceDetailFragment.getDeviceName(deviceOwner);
            }
            dialogNFC.updatePeer(peerDevice);
        }
        catch(Exception e)
        {
            Dump.println(e,"DeviceDetailFragment:onGroupInfoAvailable");
        }
    }
	//*******************************************************************************************************
	//*return status,-1:not found,1:connected,0:do connect
	//*******************************************************************************************************
    public int chkDiscovered(String Pmacaddr)
    {
    	int rc=-1;  //not found
    //*****************************
	    if (Dump.Y) Dump.println("WiFiDirectActivityDialog:chkDiscovered perr search="+Pmacaddr);
        int ctr=peers.size();
        for (int ii=0;ii<ctr;ii++)
        {
        	WifiP2pDevice dev=peers.get(ii);
	        if (Dump.Y) Dump.println("DeviceListFragment:chkDiscovered perr device="+dev.deviceAddress);
            if (Pmacaddr.equals("")/*nfc sender*/ || Pmacaddr.equals(dev.deviceAddress)/*nfc receiver*/)//~5218R~
            {
                int status=dev.status;
            	rc=0;	//issue connect
                switch(status)
                {
            	case WifiP2pDevice.CONNECTED:    //0
					rc=1; 	//alread paired
					break;
            	case WifiP2pDevice.INVITED:      //1
					break;
                case WifiP2pDevice.FAILED:       //2
					break;
            	case WifiP2pDevice.AVAILABLE:    //3
					break;
            	case WifiP2pDevice.UNAVAILABLE:  //4
					break;
                default:
                }
            }
        }
        if (Dump.Y) Dump.println("WiFiDirectActivityDialog:chkDiscovered for "+Pmacaddr+",rc="+rc);
        return rc;
    }
    //***********************************************************************************
    //*ConnectionInfoListener
    //***********************************************************************************
    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info)
    {
        try
        {
            String ownerIPAddr=info.groupOwnerAddress.getHostAddress();
            if (Dump.Y) Dump.println("onConnectionInfoAvailable:infoToString="+info.toString());
            dialogNFC.updateThisOwner(info.isGroupOwner,ownerIPAddr);
        }
        catch(Exception e)
        {
            Dump.println(e,"DeviceDetailFragment:onConnectionInfoAvailable");
        }
    }
    //***************************************************************************
    //*PeerListListener
    //***************************************************************************
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList)
    {
    	try
        {
            Collection<WifiP2pDevice> devices=peerList.getDeviceList();
            if (Dump.Y) Dump.println("WiFiDirectActivity onPeersAvailable list count="+devices.size());
	        peers.clear();
            if (devices.size() == 0)
			{
                if (Dump.Y) Dump.println("No devices found");
            }
            else
            {
		        peers.addAll(devices);
            }
//          dialogNFC.onPeersAvailable();                          //~1AbxR~
            dialogNFC.onPeersAvailable(devices.size());            //~1AbxI~
      	}
      	catch(Exception e)
      	{
      		Dump.println(e,"WiFiDirectActivityDialog:onPeerAvailable");
      	}
    }
	//*************************************************************************
	@TargetApi(AG.ICE_CREAM_SANDWICH)   //api14
    class ReceiverDialog extends WiFiDirectBroadcastReceiver
			implements UiThreadI
    {
    	private DialogNFC dialogNFC;
	    WiFiDirectActivityDialog activity;
    	public ReceiverDialog(
					WifiP2pManager Pmanager,
					Channel Pchannel,
					WiFiDirectActivityDialog Pactivity,
                    DialogNFC Pnfc)
        {
    		super(Pmanager,Pchannel,(WiFiDirectActivity)Pactivity);
        	dialogNFC=Pnfc;
        	activity=Pactivity;
        }
		//*************************************************************************
	    @Override
    	public void onReceive(Context context, Intent intent)
		{
      		try
      		{
            	received(context,intent);
      		}
      		catch(Exception e)
      		{
      			Dump.println(e,"WiFiDirectBroadcastReceiver:onReceive");
      		}
        }
		//*************************************************************************
    	private void received(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (Dump.Y) Dump.println("WifiDirectActibityDialog received broadcast msg action="+action);
            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action))
            {
                // UI update to indicate wifi p2p status.
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
				{
                    activity.setIsWifiP2pEnabled(true);
                }
				else
				{
                    activity.setIsWifiP2pEnabled(false);
                }
                if (Dump.Y) Dump.println("DialogNFC:ReceiverDialog received:P2P state changed - " + state);
            }
            else
            if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action))
            {
                if (Dump.Y) Dump.println("WiFiDirectBroadCastReceiver:onReceive:P2P peers changed");
            	if (manager != null)
				{
                	manager.requestPeers(channel,(PeerListListener)activity);
	        		if (Dump.Y) Dump.println("WiFiDirectActivityDialog:received issue manager.requestPeers");
            	}
            }
            else
            if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
            {
            	if (Dump.Y) Dump.println("DialogNFC:onReceive:requestConnectionInfo");
                if (manager == null) {
                    return;
                }
                NetworkInfo networkInfo = (NetworkInfo) intent
                        .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnected())
				{

                    // we are connected with the other device, request connection
                    // info to find group owner IP
	            	if (Dump.Y) Dump.println("DialogNFC:onReceive:requestConnectionInfo network.isConnected");

                    manager.requestConnectionInfo(channel,(ConnectionInfoListener)activity);
	        		if (Dump.Y) Dump.println("WiFiDirectActivityDialog:received issue manager.requestConnectionInfo");
                    manager.requestGroupInfo(channel,(GroupInfoListener)activity);
	        		if (Dump.Y) Dump.println("WiFiDirectActivityDialog:received issue manager.requestGroupInfo");
                }
                else
                {
	            	if (Dump.Y) Dump.println("DialogNFC:onReceive:requestConnectionInfo !network.isConnected");
                    notifyConnected(DISCONNECTED,null);            //~5219R~
                    // It's a disconnect
                }
            }
            else
            if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
            {
                if (Dump.Y) Dump.println("WiFiDirectBroadCastReceiver:onReceive:P2P this device changed");
				Object parm=intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
      			UiThread.runOnUiThreadXfer(this/*UiThreadI*/,parm);
            }
      	}
		//*************************************************************************
        @Override
        public void runOnUiThread(Object Pparm)
        {
	       	if (Dump.Y) Dump.println("WiFiDirectActivityDialog:rrunOnUiThread call updateThisDevice");
			WifiP2pDevice device=(WifiP2pDevice)Pparm;
            dialogNFC.updateThisDevice(device);
        }
    }//subclass
}
