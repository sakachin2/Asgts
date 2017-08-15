//*CID://+1Af9R~:                             update#=  114;       //~1Af9R~
//**********************************************************************************//~1A05I~
//1Af9 2016/07/12 (Ajagoc)Additional to Server/Partner List update fuction, undelete.//~1Af9I~
//1Aad 2015/04/29 change WiFiDirect(+NFC) portNo 6971-->6975       //~1AadI~
//                Ajagoc use 6970,6971,6972; Asgts use 6974, Ahsv now use 6973 and 6975//~1AadI~
//1Ab0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //~1Ab0I~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A6D 2015/02/22 1A6C is not good(connection faile by unreachable),prepare button set on/off/ airplane mode on ip connection
//1A6C 2015/02/22 after WiFiDirect,standard IP Connection is too late. try WiFi Off before std IPConnection.
//1A6B 2015/02/21 IP game title;identify IP and WifiDirect(WD)     //~1A6BI~
//1A6y 2015/02/20 dismiss ipconnection dialog when boardquestiondialog up by opponent game requested//~1A6yI~
//1A6x 2015/02/20 save update of IPConnection when not only ok but also connect requested//~1A6xI~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A6r 2015/02/16 update connection status when lost connection    //~1A6rI~
//1A6q 2015/02/16 for IPConnection dialog, expand to landscape width when landspace//~1A6qI~
//1A6o 2015/02/16 not invisible but disable for WiFiDirect button  //~1A6oI~
//1A6n 2015/02/15 IP;identify connected device                     //~1A6nI~
//1A6k 2015/02/15 re-open IPConnection/BTConnection dialog when diconnected when dislog is opened.//~1A69I~//~1A6kI~
//1A69 2015/02/08 (Bug)EditText width was set 25 colomn for IPConnection portNo field//~1A69I~
//1A68 2015/02/06 set dialog size(Why IPConnection fill screen?)   //~1A68I~
//1A67 2015/02/05 (kan)
//1A65 2015/01/29 implement Wifi-Direct function(>=Api14:android4.0)//~1A65I~
//1A61 2015/01/27 avoid to fill screen when listview has few entry.//~1A61I~
//1A36 2013/04/19 set selection after IP partner list update/add   //~1A36I~
//1A2j 2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//1A05 2013/03/02 reject Accept/Connect if IP addr not avail       //~1A05I~
//101a 2013/01/30 IP connection                                    //~v101I~
//**********************************************************************************//~1A05I~
package jagoclient.partner;

//import com.Asgts.awt.GridLayout;                                  //~2C26R~//~v101R~
import java.io.BufferedReader;
import java.io.PrintWriter;

import rene.util.list.ListClass;
import rene.util.list.ListElement;
import wifidirect.WDA;

import android.content.Intent;
import android.provider.Settings;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;                                            //~v101R~
import com.Asgts.ProgDlg;                                          //~v101R~
import com.Asgts.ProgDlgI;                                         //~v101R~
import com.Asgts.Prop;                                             //~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.Utils;                                            //~v101R~
import com.Asgts.awt.Color;
import com.Asgts.awt.Component;
import com.Asgts.awt.Container;
import com.Asgts.awt.List;                                         //~v101R~
import com.Asgts.awt.ListData;
import com.Asgts.java.FileOutputStream;                            //~v101R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.MainFrame;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.Message;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.FormTextField;
import jagoclient.partner.partner.EditPartner;
import jagoclient.partner.partner.Partner;

//import java.awt.GridLayout;
//import java.awt.Panel;
//import java.awt.TextField;

/**
Question to accept or decline a game with received parameters.
*/

public class IPConnection extends CloseDialog 
		implements ProgDlgI//~3105R~//~v101R~
{                                                                  //~2C29R~
    public  static final int NFC_SERVER=1;    //identify connection type//~1A6BI~
    public  static final int NFC_CLIENT=2;                         //~1A6BI~
    public  static final int WD_SERVER=3;                          //~1A6BI~
    public  static final int WD_CLIENT=4;                          //~1A6BI~
//  private static final String PKEY_SERVER_PORT="ServerPort";     //~v101I~//~1A6sR~
    public  static final String PKEY_SERVER_PORT="ServerPort";     //~1A6sI~
//  private static final String WDCLIENT_IPPREFIX="ClientOf-";     //~1A67I~//~1AadI~
    public  static final String WDCLIENT_IPPREFIX="ClientOf-";     //~1AadI~
    private static String SlastConnectName;                        //~v101I~
    private static final int connectedColor=android.graphics.Color.argb(0xff,0xff,0xff,0x00);//~v101I~
    private static final Color connectedLineColor=Color.orange;    //~1A6nI~
	public Server S=null;                                          //~v101I~
//  private WDA aWDA;                                              //~1A65I~//~1A90R~//~1Ab0I~
	private int portNo,oldportNo;                                  //~v101R~
	private String myIPAddr;                                       //~v101I~
    private boolean swUpdate;                                             //~v101I~
    private boolean wifiStat;                                      //~1A05I~
    private int waitingDialog=0;                                   //~v101I~
//  private List PL;                                        //~1109I~//~1111R~//~1112R~//~1114R~//~v101I~//~1A6nR~
    private ListIP PL;   	//listview                             //~1A6nI~
    private Partner partner;                                       //~v101I~
	ListClass PartnerList;
	FormTextField ServerPort;//~v101I~
//    GoFrame GF;                                                    //~3105R~//~v101R~
    MainFrame F;                                                   //~v101I~
    private ButtonAction gameButton,acceptButton,connectButton;    //~v101R~
//  private ButtonAction btnDirect;                                //~1A6oI~//~1A90R~//~1Ab0I~
//  private ButtonAction btnSetting;                               //~1A6DI~//~1A90R~//~1Ab0I~
	private String partnerprefix;                                  //~1A6nI~
    private TextView tvSession;                                    //~1A6nI~
  	private ListElement deleted_partner,deleted_partner_prev;      //~1Af9I~
//	public IPConnection (GoFrame Pgf)                       //~3105R~//~v101R~
	public IPConnection (MainFrame Pgf)                            //~v101I~
	{                                                              //~3105R~
//  	super(Pgf,AG.resource.getString(R.string.Title_IPConnection),R.layout.ipconnection,true,false);//~3105I~//~3118R~//~v101R~//~1A61R~
    	super(Pgf,AG.resource.getString(R.string.Title_IPConnection),//~1A61I~
				(AG.layoutMdpi/*mdpi and height or width <=320*/ ? R.layout.ipconnection_mdpi : R.layout.ipconnection),//~1A61I~
				true,false);                                       //~1A61I~
//      setWindowSize(90/*W:90%*/,0/*H=wrap content*/,true/*for landscape,use ScrHeight for width limit*/);//~1A68I~//~1A6qR~
        setWindowSize(90/*W:90%*/,0/*H=wrap content*/,!AG.layoutMdpi/*for landscape,use ScrHeight for width limit if not mdpi*/);//~1A6qI~
//        GF=Pgf;                                                    //~3105R~//~v101R~
        F=Pgf;                                                     //~v101I~
		partnerprefix=AG.resource.getString(R.string.PartnerNamePeer)+"-";//~1A6nI~
	    tvSession=(TextView)(findViewById(R.id.Session));          //~1A6nI~
        displayIPAddress();                                        //~v101I~
//      displaySession();                                          //~v101I~//~1A67R~
	    getPartnerList();                                          //~v101I~
        displaySession();                                          //~1A67I~
	    if (!setSelection())                                       //~v101R~
            if (!setSelection(SlastConnectName))                   //~v101R~
            {                                                      //~v101R~
                if (PartnerList.first()!=null)                     //~v101R~
                    PL.select(0);                                  //~v101R~
            }                                                      //~v101R~
		portNo=Prop.getPreference(PKEY_SERVER_PORT,AG.DEFAULT_SERVER_PORT);//~v101R~
        oldportNo=portNo;                                          //~v101I~
//        ServerPort=new FormTextField(R.id.ServerPort,Integer.toString(portNo));//~v101R~
//      ServerPort=new FormTextField(this,R.id.ServerPort,Integer.toString(portNo));//~v101I~//~1A69R~
        ServerPort=new FormTextField(this,R.id.ServerPort,Integer.toString(portNo),-1/*cols by xml*/);//~1A69I~
//        ButtonAction.init(this,0,R.id.Edit);                   //~2C30I~//~3105R~//~3117R~//~v101R~
//        ButtonAction.init(this,0,R.id.Add);                      //~v101R~
//        ButtonAction.init(this,0,R.id.Delete);                   //~v101R~
//        ButtonAction.init(this,0,R.id.ListUp);                   //~v101R~
//        ButtonAction.init(this,0,R.id.ListDown);                 //~v101R~
//        ButtonAction.init(this,0,R.id.OK);                     //~2C30I~//~3105R~//~3117R~//~v101R~
//        ButtonAction.init(this,0,R.id.Cancel);                   //~v101R~
//        ButtonAction.init(this,0,R.id.Help);                       //~3117I~//~v101R~
        new ButtonAction(this,0,R.id.Edit);                   //~v101I~
        new ButtonAction(this,0,R.id.Add);                    //~v101I~
        new ButtonAction(this,0,R.id.Delete);                 //~v101I~
        new ButtonAction(this,0,R.id.UnDelete);                    //~1Af9I~
        new ButtonAction(this,0,R.id.ListUp);                 //~v101I~
        new ButtonAction(this,0,R.id.ListDown);               //~v101I~
        new ButtonAction(this,0,R.id.OK);                     //~v101I~
        new ButtonAction(this,0,R.id.Cancel);                 //~v101I~
        new ButtonAction(this,0,R.id.Help);                   //~v101I~
        gameButton=new ButtonAction(this,0,R.id.IPGame);      //~v101R~
	    acceptButton=new ButtonAction(this,0,R.id.AcceptIPConnection);//~v101R~
        connectButton=new ButtonAction(this,0,R.id.IPConnect);//~v101R~
    	if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~v101R~
        {                                                          //~v101I~
            acceptButton.setEnabled(false);                        //~v101R~
        	connectButton.setAction(R.string.IPDisConnect);        //~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
    	if (AG.RemoteStatusAccept==AG.RS_IPLISTENING)              //~v101R~
        {                                                          //~v101I~
	        gameButton.setEnabled(false);                          //~v101R~
        	acceptButton.setAction(R.string.StopAcceptIPConnection);//~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
            gameButton.setEnabled(false);                          //~v101R~
        }                                                          //~v101I~
//      if (AG.osVersion>=AG.ICE_CREAM_SANDWICH)  //android3       //~1A65I~//~1A6oR~
//      btnDirect=                                                 //~1A6oI~//~1A90R~//~1Ab0I~
//          new ButtonAction(this,0,R.id.WiFiDirectButton);	//change visibility from gone to visible//~1A65R~//~1A90R~//~1Ab0I~
//      btnSetting=new ButtonAction(this,0,R.id.CallWiFiSetting);	//change visibility from gone to visible//~1A6DI~//~1A90R~//~1Ab0I~
//      if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android3        //~1A6oI~//~1A90R~//~1Ab0I~
//      {                                                          //~1A6DI~//~1A90R~//~1Ab0I~
//          btnDirect.setEnabled(false);                           //~1A6oI~//~1A90R~//~1Ab0I~
//          btnSetting.setEnabled(false);                          //~1A6DI~//~1A90R~//~1Ab0I~
//      }                                                          //~1A6DI~//~1A90R~//~1Ab0I~
        setDismissActionListener(this/*DoActionListener*/);        //~@@@@I~//~v101I~
		validate();
		show();
        AG.aIPConnection=this;	//used when PartnerThread detected err//~1A6kI~
	}
    //******************************************                   //~1A6kI~
    public void updateViewDisconnected()                           //~1A6kI~
    {                                                              //~1A6kI~
    	acceptButton.setEnabled(true);                             //~1A6kI~
        acceptButton.setAction(R.string.AcceptIPConnection);       //~1A6kI~
        connectButton.setEnabled(true);                            //~1A6kI~
        connectButton.setAction(R.string.IPConnect);               //~1A6kI~
        gameButton.setEnabled(false);                              //~1A6kI~
        displayIPAddress();                                        //~1A6kI~
//      TextView v=(TextView)(findViewById(R.id.Session));         //~1A6kI~//~1A6nR~
        TextView v=tvSession;                                      //~1A6nR~
        String s=AG.resource.getString(R.string.NoSession);        //~1A6kI~
        new Component().setText(v,s);                              //~1A6kI~
		setSelection();	//draw listview agter connection lost      //~1A6rI~
    }                                                              //~1A6kI~
	public void doAction (String o)
	{                                                              //~2C26R~
    	try                                                        //~3117I~
        {                                                          //~3117I~
            if (o.equals(AG.resource.getString(R.string.IPGame)))  //~v101I~
            {                                                      //~v101I~
			    if (startGame())
			    {
			    	setVisible(false); dispose();//~v101I~
			    }
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.IPConnect)))     //~@@@@I~//~2C30I~//~3105R~//~3117R~//~v101R~
            {                                                          //~2C30R~//~3117R~
			    if (connectPartnerTest())                          //~v101R~
                {                                                  //~v101I~
					writeplist();                                  //~1A6xI~
	                setVisible(false); dispose();                  //~v101R~
                    waitingDialog=R.string.IPConnect;              //~v101I~
                }                                                  //~v101I~
            }                                                      //~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.IPDisConnect)))//~v101I~
            {                                                      //~v101I~
                setVisible(false); dispose();                      //~v101I~
                waitingDialog=R.string.IPDisConnect;               //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.AcceptIPConnection)))//~3117R~//~v101R~
            {                                                      //~3117R~
				savePort();                                        //~v101I~
                if (startServer())                                         //~3105I~//~3117R~//~v101I~
                {                                                  //~v101I~
	                setVisible(false); dispose();                  //~v101R~
                    waitingDialog=R.string.AcceptIPConnection;     //~v101I~
		            acceptButton.setEnabled(false);                //~v101R~
                }                                                  //~v101I~
            }                                                      //~3117R~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.StopAcceptIPConnection)))//~v101I~
            {                                                      //~v101I~
			    stopServer();                                      //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.Edit)))//~3117R~//~v101R~
            {                                                          //~3105R~//~3117R~
			  	editPartner();                                      //~v101I~
            }                                                      //~3117R~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Add)))     //~v101I~
            {                                                      //~v101I~
    			addPartner();                                       //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.Delete)))  //~v101I~
            {                                                      //~v101I~
				deletePartner();                                    //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.UnDelete)))//~1Af9I~
            {                                                      //~1Af9I~
				undeletePartner();                                 //~1Af9I~
            }                                                      //~1Af9I~
            else                                                   //~1Af9I~
            if (o.equals(AG.resource.getString(R.string.ListUp)))  //~v101I~
            {                                                      //~v101I~
				listUp();                                          //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.ListDown)))//~v101I~
            {                                                      //~v101I~
				listDown();                                        //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.OK)))      //~v101R~
            {                                                      //~v101I~
                setVisible(false); dispose();                      //~v101I~
				writeplist();                                      //~v101R~
				savePort();                                        //~v101I~
            }                                                      //~v101I~
            else                                                   //~3117R~
            if (o.equals(AG.resource.getString(R.string.Cancel)))  //~v101I~
            {                                                      //~v101I~
                setVisible(false); dispose();                      //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.Help)))    //~3117R~
            {                                                      //~3117R~
//              new HelpDialog(null,R.string.Help_IPConnection); //~3117R~//~v101R~//~1A05R~
                new HelpDialog(null,R.string.HelpTitle_IPConnection,"IPConnection");//~1A05R~
            }                                                      //~3117R~
            else                                                   //~v101I~
            if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~v101I~
            {               //callback from Dialog after currentLayout restored//~v101I~
                afterDismiss(waitingDialog);                       //~v101I~
            }                                                      //~v101I~
//          else                                                   //~1A65I~//~1A90R~//~1Ab0I~
//          if (o.equals(AG.resource.getString(R.string.WiFiDirectButton)))  //modal but no inputWait//~1A65I~//~1A90R~//~1Ab0I~
//          {               //callback from Dialog after currentLayout restored//~1A65I~//~1A90R~//~1Ab0I~
//              startWD();                                         //~1A65I~//~1A90R~//~1Ab0I~
//          }                                                      //~1A65I~//~1A90R~//~1Ab0I~
//          else                                                   //~1A6DI~//~1A90R~//~1Ab0I~
//          if (o.equals(AG.resource.getString(R.string.CallWiFiSetting)))  //modal but no inputWait//~1A6DI~//~1A90R~//~1Ab0I~
//          {               //callback from Dialog after currentLayout restored//~1A6DI~//~1A90R~//~1Ab0I~
//              callWiFiSetting();                                 //~1A6DI~//~1A90R~//~1Ab0I~
//          }                                                      //~1A6DI~//~1A90R~//~1Ab0I~
        }                                                          //~3117I~
        catch(Exception e)                                         //~3117I~
        {                                                          //~3117I~
            Dump.println(e,"IPConnection:doAction:"+o);     //~3117I~//~v101R~
        }                                                          //~3117I~
	}
    //******************************************                   //~v101I~
    private void displayIPAddress()                                //~v101I~
    {                                                              //~v101I~
//      TextView v=(TextView)(AG.findViewById(R.id.YourIPA));       //~v106R~//~v101R~
        TextView v=(TextView)(findViewById(R.id.YourIPA));         //~v101I~
        String ipa=Utils.getIPAddress(false/*ipv4 only*/);         //~v101R~
        myIPAddr=ipa;                                              //~v101I~
        wifiStat=!myIPAddr.equals(Utils.IPA_NA);                    //~1A05I~
//      v.setText(ipa);                       //~v106R~            //~v101I~//~1A6kR~
        new Component().setText(v,ipa);                            //~1A6kI~
        if (!wifiStat)                                             //~1A05I~
        	errWifi();                                             //~1A05I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private void displaySession()                                  //~v101I~
    {                                                              //~v101I~
//      TextView v=(TextView)(AG.findViewById(R.id.Session));      //~v101R~
//      TextView v=(TextView)(findViewById(R.id.Session));         //~v101I~//~1A6nR~
        TextView v=tvSession;                                      //~1A6nI~
        String ipa;                                                //~v101I~
    	if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~v101I~
        {                                                          //~v101I~
        	ipa=AG.RemoteInetAddress;                     //~v101I~
	        v.setTextColor(connectedColor);                        //~v101I~
            updateWDClient(ipa);                                   //~1A67I~
        }                                                          //~v101I~
        else                                                       //~v101I~
        	ipa=AG.resource.getString(R.string.NoSession);         //~v101I~
        v.setText(ipa);                                            //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private boolean startGame()                                    //~v101I~
    {                                                              //~v101I~
//        if (AG.aPartnerFrameIP!=null && AG.aPartnerFrameIP.PT!=null//~v101I~//~1A8cR~
//        &&  AG.aPartnerFrameIP.PT.isAlive())                       //~v101I~//~1A8cR~
//        {                                                          //~v101I~//~1A8cR~
//            AG.aPartnerFrameIP.doAction(AG.resource.getString(R.string.Game));//~v101I~//~1A8cR~
//            return true;                                           //~v101I~//~1A8cR~
//        }                                                          //~v101I~//~1A8cR~
        if (AG.aPartnerFrame!=null && AG.aPartnerFrame.PT!=null    //~1A8cI~
        &&  AG.aPartnerFrame.PT.isAlive())                         //~1A8cI~
        {                                                          //~1A8cI~
            AG.aPartnerFrame.doAction(AG.resource.getString(R.string.Game));//~1A8cI~
            return true;                                           //~1A8cI~
        }                                                          //~1A8cI~
        errNoThread();
        return false;//~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private boolean/*dispose*/ connectPartnerTest()                //~v101I~
    {                                                              //~v101I~
        if (!wifiStat)                                             //~1A05I~
        {                                                          //~1A05I~
        	errWifi();                                             //~1A05I~
            return false;                                          //~1A05I~
        }                                                          //~1A05I~
	    String s=getSelected();                                    //~v101I~
  		if (s==null)                                               //~v101I~
        	return false;                                          //~v101I~
        Partner c=pfind(s);                                        //~v101I~
        if (c==null) // try connecting to this partner server, if not trying already//~v101I~
        	return false;                                          //~v101I~
        partner=c;                                                 //~v101I~
        return true;                                               //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private boolean/*dispose*/ connectPartner()                    //~v101R~
    {                                                              //~v101I~
        if (partner!=null) // try connecting to this partner server, if not trying already//~v101R~
        {                                                          //~v101I~
			writeplist();                                          //~v101I~
        	Partner c=partner;                                     //~v101I~
            int connectiontype=(c.Name.startsWith(partnerprefix)) ? WD_CLIENT : 0;//~1A6BI~
//            if (connectiontype==0)                               //~1A6CR~//~1A6DR~
//            {                                                    //~1A6CR~//~1A6DR~
//                WDA.disableWiFi();                               //~1A6CR~//~1A6DR~
//                WDA.enableWiFi();                                //~1A6CR~//~1A6DR~
//            }                                                    //~1A6CR~//~1A6DR~
            SlastConnectName=c.Name;                               //~v101I~
            PartnerFrame cf=                                       //~v101I~
                new PartnerFrame(                                  //~v101I~
//                  Global.resourceString("Connection_to_")+c.Name,false);//~v101I~//~1A6BR~
                    Global.resourceString("Connection_to_")+c.Name,false,connectiontype);//~1A6BI~
            new ConnectPartner(c,cf);                              //~v101I~
            return true;                                           //~v101I~
        }                                                          //~v101I~
        return false;                                              //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101R~
    private void disconnectPartner()                               //~v101M~
    {                                                              //~v101M~
//        if (AG.RemoteStatus==AG.RS_IPLISTENING)                  //~v101R~
//        {                                                        //~v101R~
//            stopServer();                                        //~v101R~
//            return;                                              //~v101R~
//        }                                                        //~v101R~
//        if (AG.aPartnerFrameIP!=null)                              //~v101R~//~1A8cR~
//            AG.aPartnerFrameIP.disconnect();                       //~v101M~//~1A8cR~
        if (AG.aPartnerFrame!=null)                                //~1A8cI~
            AG.aPartnerFrame.disconnect();                         //~1A8cI~
  	}                                                              //~v101M~
    //******************************************                   //~v101I~
    private void stopServer()                                      //~v101I~
    {                                                              //~v101I~
        Server.cancel();                                           //~v101I~
//        ButtonAction.init(this,R.string.AcceptIPConnection,R.id.AcceptIPConnection);//~v101R~
        acceptButton.setAction(R.string.AcceptIPConnection);       //~v101I~
  	}                                                              //~v101I~
    //******************************************                   //~v101I~
    private boolean/*dispose*/ startServer()                       //~v101R~
    {                                                              //~v101R~
        if (!wifiStat)                                             //~1A05I~
        {                                                          //~1A05I~
        	errWifi();                                             //~1A05I~
            return false;                                          //~1A05I~
        }                                                          //~1A05I~
		portNo=getPortNo();                                        //~v101I~
        if (S==null)                                               //~v101R~
//              S=new Server(Global.getParameter("serverport",6970),//~v101R~
//                  Global.getParameter("publicserver",true));     //~v101R~
        	S=new Server(portNo,false);                            //~v101R~
//      S.open();                                                  //~v101R~
		return true; 	//dispose at boardquestion                 //~v101R~
  	}                                                              //~v101I~
    //******************************************                   //~v101I~
  	private void editPartner()                                          //~v101I~
  	{                                                              //~v101I~
		String s=getSelected();                                    //~v101R~
  		if (s==null)                                               //~v101I~
        	return;                                                //~v101I~
  		Partner c=pfind(PL.getSelectedItem());                 //~1524R~//~v101I~
  		if (c!=null)                                               //~v101I~
//      {   new EditPartner(this,PartnerList,c,this);              //~v101R~
        {                                                          //~v101I~
			new EditPartner(F,PartnerList,c,this);                 //~v101I~
  		}                                                          //~v101I~
  	}                                                              //~v101I~
    //******************************************                   //~v101I~
    private void addPartner()                                      //~v101I~
    {                                                              //~v101I~
  		new EditPartner(F,PartnerList,this);                       //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private void deletePartner()	                               //~v101I~
    {                                                              //~v101I~
        ListElement next;                                          //+1Af9M~
		String s=getSelected();                                    //~v101R~
  		if (s==null)                                               //~v101I~
        	return;                                                //~v101I~
        ListElement lc=PartnerList.first();                        //~v101I~
        Partner co;                                                //~v101I~
        while (lc!=null)                                           //~v101I~
        {   co=(Partner)lc.content();                              //~v101I~
            if (co.Name.equals(s))          //~1111R~//~1114R~     //~v101R~
//          {   PartnerList.remove(lc);                            //~v101I~//~1Af9R~
            {                                                      //~1Af9I~
                deleted_partner=lc;                                //~1Af9I~
                deleted_partner_prev=lc.previous();                //~1Af9I~
  				next=lc.next(); //set selection                    //~1Af9I~
  				if (next!=null)                                    //~1Af9I~
					s=((Partner)(next.content())).Name; //selected after delete//~1Af9I~
                PartnerList.remove(lc);                            //~1Af9I~
                break;                                             //~1Af9I~
            }                                                      //~v101I~
            lc=lc.next();                                          //~v101I~
        }                                                          //~v101I~
		updateplist();                                             //~v101I~
		setSelection(s);                                           //~1Af9I~
    }                                                              //~v101I~
    //******************************************                   //~1Af9I~
    private void undeletePartner()                                 //~1Af9I~
    {                                                              //~1Af9I~
        if (deleted_partner==null)                                 //~1Af9I~
        {                                                          //~1Af9I~
            AView.showToast(R.string.NoDeletedEntry);              //~1Af9I~
        }                                                          //~1Af9I~
        else                                                       //~1Af9I~
        {                                                          //~1Af9I~
			String s=((Partner)(deleted_partner.content())).Name;  //~1Af9I~
            PartnerList.insert(deleted_partner,deleted_partner_prev);//~1Af9I~
            updateplist();                                         //~1Af9I~
			setSelection(s);                                       //~1Af9I~
            deleted_partner=null;                                  //~1Af9I~
        }                                                          //~1Af9I~
    }                                                              //~1Af9I~
    //******************************************                   //~v101I~
    private void listUp()                                          //~v101I~
    {                                                              //~v101I~
		String s=getSelected();                                    //~v101I~
  		if (s==null)                                               //~v101I~
        	return;                                                //~v101I~
        ListElement lc=PartnerList.first();                        //~v101I~
        ListElement prev;                                          //~v101I~
        Partner co;                                                //~v101I~
        while (lc!=null)                                           //~v101I~
        {                                                          //~v101I~
            co=(Partner)lc.content();                              //~v101I~
            if (co.Name.equals(s))                                 //~v101I~
            {                                                      //~v101I~
            	prev=lc.previous();                                    //~v101I~
                if (prev==null)                                    //~v101I~
                	break;                                         //~v101I~
            	prev=prev.previous();                              //~v101I~
				PartnerList.remove(lc);                            //~v101I~
				PartnerList.insert(lc,prev);                       //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
            lc=lc.next();                                          //~v101I~
        }                                                          //~v101I~
		updateplist();                                             //~v101I~
		setSelection(s);                                           //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private void listDown()                                        //~v101I~
    {                                                              //~v101I~
		String s=getSelected();                                    //~v101I~
  		if (s==null)                                               //~v101I~
        	return;                                                //~v101I~
        ListElement lc=PartnerList.first();                        //~v101I~
        ListElement next;                                          //~v101I~
        Partner co;                                                //~v101I~
        while (lc!=null)                                           //~v101I~
        {                                                          //~v101I~
            co=(Partner)lc.content();                              //~v101I~
            if (co.Name.equals(s))                                 //~v101I~
            {                                                      //~v101I~
            	next=lc.next();                                    //~v101I~
                if (next==null)                                    //~v101I~
                	break;                                         //~v101I~
				PartnerList.remove(lc);                            //~v101I~
				PartnerList.insert(lc,next);                       //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
            lc=lc.next();                                          //~v101I~
        }                                                          //~v101I~
		updateplist();                                             //~v101I~
		setSelection(s);                                           //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private void getPartnerList()                                  //~v101I~
    {                                                              //~v101I~
        String l;
//  	PL=new List(this,R.id.PartnerList,R.layout.textrowlist);                                         //~1524R~//~v101R~//~1A6nR~
    	PL=new ListIP(this,R.id.PartnerList,R.layout.textrowlist); //~1A6nI~
        PL.addActionListener(this);                            //~1524R~//~v101I~
//        PL.setFont(Global.Monospaced);                         //~1524R~//~v101R~
        PL.setBackground(Global.gray);                         //~1524R~//~v101I~
        Partner cp;                                                //~v101I~
		PartnerList=new ListClass();                               //~v101I~
		try // read connections from partner.cfg                   //~v101I~
		{                                                          //~v101I~
			BufferedReader in=Global.getStream(".partner.cfg");    //~v101I~
			while (true)                                           //~v101I~
			{                                                      //~v101I~
				l=in.readLine();                                   //~v101I~
				if (l==null || l.equals("")) break;                //~v101I~
				cp=new Partner(l);                                 //~v101I~
				if (cp.valid())                                    //~v101I~
				{                                                  //~v101I~
					PL.add(cp.Name);                               //~v101I~
					PartnerList.append(new ListElement(cp));       //~v101I~
				}                                                  //~v101I~
				else break;                                        //~v101I~
			}                                                      //~v101I~
			in.close();                                            //~v101I~
		}                                                          //~v101I~
		catch (Exception ex) {}                                    //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private boolean setSelection()                                 //~v101R~
    {                                                              //~v101I~
//        if (AG.RemoteStatus!=AG.RS_IPCONNECTED)                  //~v101R~
//            return false;                                        //~v101R~
        String	ipa=AG.RemoteInetAddress;                          //~v101I~
        if (ipa==null)                                             //~v101I~
        	return false;                                          //~v101R~
		int idx=pfindByAddr(ipa);                                  //~v101I~
        if (idx<0)                                                 //~v101I~
        	return false;                                          //~v101R~
		PL.select(idx);                                            //~v101I~
        return true;                                               //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private boolean setSelection(String Plastname)                 //~v101R~
    {                                                              //~v101I~
		int idx=pfindidx(Plastname);                               //~v101I~
        if (idx<0)                                                 //~v101I~
        	return false;                                          //~v101R~
		PL.select(idx);                                            //~v101I~
        return true;                                               //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	/** find a specific partner server by name */                  //~v101I~
	public Partner pfind (String s)                                //~v101I~
	{	ListElement lc=PartnerList.first();                        //~v101I~
		Partner c;                                                 //~v101I~
		while (lc!=null)                                           //~v101I~
		{	c=(Partner)lc.content();                               //~v101I~
			if (c.Name.equals(s)) return c;                        //~v101I~
			lc=lc.next();                                          //~v101I~
		}                                                          //~v101I~
		return null;                                               //~v101I~
	}                                                              //~v101I~
    //******************************************                   //~v101I~
	public int pfindidx(String s)                                  //~v101I~
	{	                                                           //~v101R~
    	if (s==null)                                               //~v101I~
        	return -1;                                             //~v101I~
		ListElement lc=PartnerList.first();                        //~v101I~
		Partner c;                                                 //~v101I~
        int idx=0;                                                 //~v101I~
		while (lc!=null)                                           //~v101I~
		{	c=(Partner)lc.content();                               //~v101I~
			if (c.Name.equals(s)) return idx;                      //~v101I~
			lc=lc.next();                                          //~v101I~
			idx++;                                                 //~v101I~
		}                                                          //~v101I~
		return -1;                                                 //~v101I~
	}                                                              //~v101I~
    //******************************************                   //~v101I~
	private int pfindByAddr(String s)                              //~v101I~
	{	ListElement lc=PartnerList.first();                        //~v101I~
		Partner c;                                                 //~v101I~
        int idx=0;                                                //~v101I~
		while (lc!=null)                                           //~v101I~
		{	c=(Partner)lc.content();                               //~v101I~
 			if (c.Server.equals(s)) return idx;                      //~v101I~
			lc=lc.next();
			idx++;//~v101I~
		}                                                          //~v101I~
		return -1;                                                //~v101I~
	}                                                              //~v101I~
    //******************************************                   //~v101I~
	/** update the list of partners */                             //~v101I~
    //******************************************                   //~1A36I~
	public void updateplist ()                                     //~v101R~
	{                                                              //~v101R~
        swUpdate=true;                                             //~v101I~
//        try                                                      //~v101R~
//        {                                                        //~v101R~
//            PrintWriter out=new PrintWriter(                      //~v101I~
//                new FileOutputStream(Global.home()+".partner.cfg"));//~v101R~
			ListElement lc=PartnerList.first();                    //~v101I~
			PL.removeAll();                                        //~v101I~
			while (lc!=null)                                       //~v101I~
			{	Partner c=(Partner)lc.content();                   //~v101I~
				PL.add(c.Name);                                    //~v101I~
//                c.write(out);                                    //~v101R~
				lc=lc.next();                                      //~v101I~
			}                                                      //~v101I~
//                out.close();                                     //~v101R~
//        }                                                        //~v101R~
//        catch (Exception e)                                      //~v101R~
//        {   if (F!=null) new Message(F,                          //~v101R~
//                Global.resourceString("Could_not_write_to_partner_cfg"));//~v101R~
//        }                                                        //~v101R~
	}                                                              //~v101I~
    //******************************************                   //~1A36I~
	//** from EditPartner                                          //~1A36R~
    //******************************************                   //~1A36I~
	public void updateplist (String Pname)                         //~1A36I~
	{                                                              //~1A36I~
		updateplist ();                                             //~1A36I~
		setSelection(Pname);                                       //~1A36I~
	}                                                              //~1A36I~
    //******************************************                   //~v101I~
	/** update the list of partners */                             //~v101I~
	private void writeplist()                                      //~v101R~
	{                                                              //~v101I~
        if (!swUpdate)
        	return;//~v101I~
		try                                                        //~v101I~
		{                                                          //~v101I~
            PrintWriter out=new PrintWriter(                        //~v101I~
				new FileOutputStream(Global.home()+".partner.cfg"));//~v101I~
			ListElement lc=PartnerList.first();                    //~v101I~
			while (lc!=null)                                       //~v101I~
			{	Partner c=(Partner)lc.content();                   //~v101I~
				c.write(out);                                      //~v101I~
				lc=lc.next();                                      //~v101I~
			}                                                      //~v101I~
			out.close();                                           //~v101I~
        	swUpdate=false;                                        //~v101I~
		}                                                          //~v101I~
		catch (Exception e)                                        //~v101I~
		{	if (F!=null) new Message(F,                            //~v101I~
				Global.resourceString("Could_not_write_to_partner_cfg"));//~v101I~
		}                                                          //~v101I~
	}                                                              //~v101I~
    //******************************************                   //~v101I~
	private String getSelected()                                   //~v101R~
    {                                                              //~v101I~
	    String s=PL.getSelectedItem();                             //~v101I~
        if (s==null || s.equals(""))                               //~v101I~
        {                                                          //~v101I~
			errNotSelected();                                       //~v101I~
            s=null;	                                               //~v101I~
        }                                                          //~v101I~
        return s;                                                  //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	public static void errNotSelected()                            //~v101R~
    {                                                              //~v101I~
    	AView.showToast(R.string.ErrNotSelected);                  //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void errNoThread()                                     //~v101R~
    {                                                              //~v101I~
    	AView.showToast(R.string.ErrNoThread);                     //~v101R~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void errWifi()                                         //~1A05I~
    {                                                              //~1A05I~
    	if (Dump.Y) Dump.println("errWifi");                       //~1A05I~
    	AView.showToast(R.string.ErrWifi);                         //~1A05I~
    }                                                              //~1A05I~
    //******************************************                   //~1A05I~
	private int getPortNo()                                        //~v101R~
    {                                                              //~v101I~
        String port=ServerPort.getText();                          //~v101R~
        int n=0;                                                   //~v101I~
        if (port!=null)                                            //~v101I~
	    	n=Integer.parseInt(port);                              //~v101I~
		return n;                                                  //~v101R~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void savePort()                                        //~v101R~
    {                                                              //~v101I~
	    portNo=getPortNo();                                        //~v101I~
        if (portNo!=oldportNo)                                     //~v101I~
			Prop.putPreference(PKEY_SERVER_PORT,portNo);           //~v101R~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	public void afterDismiss(int Pwaiting)                         //~v101R~
    {                                                              //~v101I~
    	if (Dump.Y) Dump.println("IPConnection afterDismiss Pwaiting="+Integer.toHexString(Pwaiting));//~v101R~
        if (Pwaiting==R.string.AcceptIPConnection)                 //~v101I~
        {                                                          //~v101I~
            String msg=AG.resource.getString(R.string.Msg_WaitingIPAccept,myIPAddr,portNo);//~v101I~
			waitingResponse(R.string.Title_WaitingAccept,msg);     //~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
        if (Pwaiting==R.string.IPConnect)                          //~v101I~
        {                                                          //~v101I~
        	String n="";                                           //~v101I~
        	int p=0;                                               //~v101I~
        	if (partner!=null) // try connecting to this partner server, if not trying already//~v101I~
        	{                                                      //~v101I~
	            n=partner.Name;                                    //~v101I~
                p=partner.Port;                                    //~v101I~
            }                                                      //~v101I~
            String msg=AG.resource.getString(R.string.Msg_WaitingIPConnect,n,p);//~v101I~
			waitingResponse(R.string.Title_WaitingConnect,msg);    //~v101R~
			connectPartner();                                      //~v101I~
        }                                                          //~v101I~
        else                                                       //~v101I~
        if (Pwaiting==R.string.IPDisConnect)                       //~v101I~
			disconnectPartner();                                   //~v101I~
    	if (Dump.Y) Dump.println("IPConnection afterDismiss exit");//~v101I~
        AG.aIPConnection=null;                                     //~1A6kI~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,int Pmsgresid)    //~v101R~
    {                                                              //~v101I~
//        new MessageWaiting(parentFrame,AG.resource.getString(Ptitleresid),//~v101R~
//                            AG.resource.getString(Pmsgresid));   //~v101R~
//        AG.progDlg=new ProgDlg(Ptitleresid,Pmsgresid,true/*cancelable*/);//~v101I~//~1A2jR~
//        AG.progDlg.setCallback(this,false/*cancel CB*/,false/*dismisscallback*/);//~v101R~//~1A2jR~
//        AG.progDlg.show();                                         //~v101I~//~1A2jR~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,false/*cancelCB*/,Ptitleresid,Pmsgresid,true/*cancelable*/);//~1A2jI~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,String Pmsg)      //~v101I~
    {                                                              //~v101I~
//        AG.progDlg=new ProgDlg(Ptitleresid,Pmsg,true/*cancelable*/);//~v101I~//~1A2jR~
//        AG.progDlg.setCallback(this,false/*cancel CB*/,false/*dismisscallback*/);//~v101I~//~1A2jR~
//        AG.progDlg.show();                                         //~v101I~//~1A2jR~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,false/*cancel CB*/,Ptitleresid,Pmsg,true/*cancelable*/);//~1A2jI~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    //*reason:0:cancel,1:dismiss                                   //~v101I~
    //******************************************                   //~v101I~
    @Override                                                      //~v101I~
	public void onCancelProgDlg(int Preason)                       //~v101I~
    {                                                              //~v101I~
    	if (Dump.Y) Dump.println("onCancelProgDlgI reason="+Preason);//~v101I~
    }                                                              //~v101I~
//    //***********************************************************************//~1A65I~//~1Ab0R~
//    private void startWD()                                         //~1A65I~//~1Ab0R~
//    {                                                              //~1A65I~//~1Ab0R~
//        if (Dump.Y) Dump.println("IPConnection:startWD");          //~1A65I~//~1Ab0R~
//        try                                                        //~1A65I~//~1Ab0R~
//        {                                                          //~1A65I~//~1Ab0R~
//            aWDA=WDA.showDialog(this);                             //~1A65R~//~1Ab0R~
//        }                                                          //~1A65I~//~1Ab0R~
//        catch(Exception e)                                         //~1A65I~//~1Ab0R~
//        {                                                          //~1A65I~//~1Ab0R~
//            Dump.println(e,"IPConnection:startWD");                //~1A65I~//~1Ab0R~
//        }                                                          //~1A65I~//~1Ab0R~
//    }                                                              //~1A65I~//~1Ab0R~
//    //***********************************************************************//~1A65I~//~1Ab0R~
//    public void onCloseWDA()                                        //~1A67R~//~1Ab0R~
//    {                                                              //~1A65I~//~1Ab0R~
//        boolean owner=aWDA.WDAowner;                               //~1A65I~//~1Ab0R~
//        if (Dump.Y) Dump.println("IPConnection:closedWD owner="+owner);//~1A65I~//~1Ab0R~
//        try                                                        //~1A65I~//~1Ab0R~
//        {                                                          //~1A65I~//~1Ab0R~
//            String serveripa=aWDA.WDAipa;                           //~1A65I~//~1Ab0R~
//            String peername=aWDA.WDApeer;                        //~1A65I~//~1A67R~//~1Ab0R~
//            if (!owner)                                            //~1A65I~//~1Ab0R~
//            {                                                      //~1A65I~//~1Ab0R~
//                if (Dump.Y) Dump.println("IPConnection:closedWD client server="+peername+",addr="+serveripa);//~1A65I~//~1A67R~//~1Ab0R~
////              String partnerprefix=AG.resource.getString(R.string.PartnerNamePeer)+"-";//~1A65I~//~1A6nR~//~1Ab0R~
//                String partnername=partnerprefix+peername;       //~1A65I~//~1A67R~//~1Ab0R~
//                Partner p=new Partner(partnername,serveripa,portNo,Partner.PRIVATE);//~1A65I~//~1Ab0R~
//                updatePeer(p,partnerprefix);                       //~1A65I~//~1Ab0R~
//            }                                                      //~1A65I~//~1Ab0R~
//            else                                                   //~1A67I~//~1Ab0R~
//            {                                                      //~1A67I~//~1Ab0R~
//                if (Dump.Y) Dump.println("IPConnection:closedWD server server="+peername+",addr="+serveripa);//~1A67I~//~1Ab0R~
////              String partnerprefix=AG.resource.getString(R.string.PartnerNamePeer)+"-";//~1A67I~//~1A6nR~//~1Ab0R~
//                String partnername=partnerprefix+peername;         //~1A67I~//~1Ab0R~
//                Partner p=new Partner(partnername,WDCLIENT_IPPREFIX+serveripa,portNo,Partner.PRIVATE);//~1A67I~//~1Ab0R~
//                updatePeer(p,partnerprefix);                       //~1A67I~//~1Ab0R~
//            }                                                      //~1A67I~//~1Ab0R~
//        }                                                          //~1A65I~//~1Ab0R~
//        catch(Exception e)                                         //~1A65I~//~1Ab0R~
//        {                                                          //~1A65I~//~1Ab0R~
//            Dump.println(e,"IPConnection:closedWD");               //~1A65I~//~1Ab0R~
//        }                                                          //~1A65I~//~1Ab0R~
//    }                                                              //~1A65I~//~1Ab0R~
//    //***********************************************************************//~1A65I~//~1Ab0R~
//    private void updatePeer(Partner Ppartner,String Pprefix)       //~1A65I~//~1Ab0R~
//    {                                                              //~1A65I~//~1Ab0R~
//        Partner p;                                                 //~1A65I~//~1Ab0R~
//        boolean swupdate=false;                                    //~1A65I~//~1Ab0R~
//    //**************************                                   //~1A65I~//~1Ab0R~
//        p=pfind(Ppartner.Name);                                     //~1A67I~//~1Ab0R~
//        if (p!=null)                                               //~1A67I~//~1Ab0R~
//        {                                                          //~1A67I~//~1Ab0R~
//            p.Server=Ppartner.Server;                          //~1A65I~//~1A67R~//~1Ab0R~
//            p.Port=Ppartner.Port;                              //~1A65I~//~1A67R~//~1Ab0R~
//            swupdate=true;                                     //~1A65I~//~1A67R~//~1Ab0R~
//        }                                                          //~1A65I~//~1Ab0R~
//        if (!swupdate)                                             //~1A65R~//~1Ab0R~
//        {                                                          //~1A65I~//~1Ab0R~
//            PartnerList.prepend(new ListElement(Ppartner));        //~1A65I~//~1Ab0R~
//        }                                                          //~1A65I~//~1Ab0R~
//        updateplist();                                             //~1A65I~//~1Ab0R~
//        writeplist();   //update partner.cfg                       //~1A67I~//~1Ab0R~
//        if (!swupdate)                                             //~1A67I~//~1Ab0R~
//            PL.select(0);                                              //~1A65I~//~1A67R~//~1Ab0R~
//        else                                                       //~1A67I~//~1Ab0R~
//            setSelection(Ppartner.Name);                           //~1A67I~//~1Ab0R~
//    }                                                              //~1A65I~//~1Ab0R~
    //***********************************************************************//~1A67I~
    private void updateWDClient(String Pipaddr)                    //~1A67I~
    {                                                              //~1A67I~
		Partner p;                                                 //~1A67I~
    //**************************                                   //~1A67I~
        if (Dump.Y) Dump.println("IPConnection:updateWDClient this ip="+Pipaddr);//~1A67I~
		p=pfindByNetMask(WDCLIENT_IPPREFIX+Pipaddr);               //~1A67I~
        if (p==null)                                               //~1A67I~
        	return;                                                //~1A67I~
        p.Server=Pipaddr;                                          //~1A67I~
		updateplist();                                             //~1A67I~
    }                                                              //~1A67I~
    //******************************************                   //~1A67I~
	private Partner pfindByNetMask(String s)                       //~1A67I~
	{                                                              //~1A67I~
    	int offs=s.lastIndexOf(".");                               //~1A67I~
        if (offs<=0)                                               //~1A67I~
        	return null;                                           //~1A67I~
        String mask=s.substring(0,offs+1);                         //~1A67I~
		ListElement lc=PartnerList.first();                        //~1A67I~
		Partner c;                                                 //~1A67I~
		while (lc!=null)                                           //~1A67I~
		{	c=(Partner)lc.content();                               //~1A67I~
 			if (c.Server.startsWith(mask)) return c;              //~1A67I~
			lc=lc.next();                                          //~1A67I~
		}                                                          //~1A67I~
		return null;                                               //~1A67I~
	}                                                              //~1A67I~
    //***********************************************************************//~1A67I~
    public void dismissWDA()                                       //~1A67I~
    {                                                              //~1A67I~
        displayIPAddress();                                        //~1A67I~
        displaySession();                                          //~1A67I~
    }                                                              //~1A67I~
    //***********************************************************************//~1A6nI~
    class ListIP extends List                                                  //~1114//~1A6nI~
    {                                                              //~1A6nI~
    //*****************                                            //~1A6nI~
        public ListIP(Container Pcontainer,int Presid,int Prowresid)//~1A6nI~
        {                                                          //~1A6nI~
            super(Pcontainer,Presid,Prowresid);                    //~1A6nI~
        }                                                          //~1A6nI~
    //**********************************************************************//~1A6nI~
        @Override                                                  //~1A6nI~
		protected void getViewAdjust(int Ppos,TextView Pview,ViewGroup Pparent,ListData Plistdata,int Pselectedpos)//~1A6nI~
        {                                                          //~1A6nI~
	        TextView tv;                                           //~1A6nI~
        //*******************                                      //~1A6nI~
            if (Dump.Y) Dump.println("ListIP:getViewCustom Ppos="+Ppos);//~1A6nI~
            tv=Pview;	                                           //~1A6nI~
            ListData ld=Plistdata;                                 //~1A6nI~
            if (chkDirect(ld,Ppos))                                //~1A6nI~
            {                                                      //~1A6nI~
//              if (Ppos!=Pselectedpos)                            //~1A6nI~
//                  tv.setBackgroundColor(connectedBGColor);       //~1A6nI~
	            tv.setTextColor(connectedLineColor.getRGB());      //~1A6nI~
            }                                                      //~1A6nI~
        }                                                          //~1A6nI~
        //***************************************                  //~1A6nI~
        private boolean chkDirect(ListData Pld,int Ppos)           //~1A6nI~
        {                                                          //~1A6nI~
            if (Pld.itemtext.startsWith(partnerprefix))            //~1A6nI~
            {                                                      //~1A6nI~
                Partner p=pfind(Pld.itemtext);                     //~1A6nI~
                if (p!=null)                                       //~1A6nI~
                {                                                  //~1A6nI~
                    String session=tvSession.getText().toString(); //~1A6nI~
                    if (session!=null && session.equals(p.Server)) //~1A6nI~
                        return true;                               //~1A6nI~
                }                                                  //~1A6nI~
            }                                                      //~1A6nI~
            return false;                                          //~1A6nI~
        }                                                          //~1A6nI~
    }//class                                                       //~1A6nI~
    //***********************************************************************//~1A6yI~
    public static void closeDialog()                               //~1A6yI~
    {                                                              //~1A6yI~
    	if (Dump.Y) Dump.println("IPConnection:closeDialog");      //~1A6yI~
        if (AG.aIPConnection!=null && AG.aIPConnection.androidDialog.isShowing())//~1A6yI~
        {                                                          //~1A6yI~
	    	if (Dump.Y) Dump.println("IPConnection:closeDialog dismiss");//~1A6yI~
		    AG.aIPConnection.waitingDialog=0;                      //~1A6yI~
        	AG.aIPConnection.dismiss();                            //~1A6yI~
        }                                                          //~1A6yI~
    }                                                              //~1A6yI~
//    //***********************************************************************//~1A6DI~//~1Ab0R~
//    private void callWiFiSetting()                                 //~1A6DI~//~1Ab0R~
//    {                                                              //~1A6DI~//~1Ab0R~
//        if (Dump.Y) Dump.println("IPConnection:callWiFiSetting");  //~1A6DI~//~1Ab0R~
//        AG.activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//~1A6DI~//~1Ab0R~
//    }                                                              //~1A6DI~//~1Ab0R~
}

