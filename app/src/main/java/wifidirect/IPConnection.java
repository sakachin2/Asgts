//*CID://+1AecR~:                             update#=  149;       //+1AecR~
//**********************************************************************************//~1A05I~
//1Aec 2015/07/26 set connection type for Server                   //+1AecI~
//1Ac3 2015/07/06 WD:Unpare after active session was closed        //~1Ac3I~
//1Aad 2015/04/29 change WiFiDirect(+NFC) portNo 6971-->6975       //~1AadI~
//                Ajagoc use 6970,6971,6972; Asgts use 6974, Ahsv now use 6973 and 6975//~1AadI~
//1A90 2015/04/18 (like as 1A84)WiFiDirect from Top panel          //~1A90I~
//1A8k 2015/03/06 delete unused fuinction                          //~1A8kI~
//1A8ck2015/03/01 extends PartnerFrame/PartnerThread to wifidirect //~1A8cI~
//1A8ak2015/02/28 (BUG) partnername was null                       //~1A8aI~
//1A84 2015/02/25 WiFiDirect from openpartner frame                //~1A84I~
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
package wifidirect;

import rene.util.list.ListClass;
import wifidirect.WDA;
import android.content.Intent;
import android.provider.Settings;

import com.Asgts.AG;                                               //~v101R~//~1A84R~
import com.Asgts.Prop;
import com.Asgts.Utils;
import com.Asgts.AView;                                            //~v101R~//~1A84R~
import com.Asgts.ProgDlg;                                          //~v101R~//~1A84R~
import com.Asgts.ProgDlgI;                                         //~v101R~//~1A84R~
import com.Asgts.R;                                                //~v101R~//~1A84R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.MainFrame;
import jagoclient.partner.ConnectPartner;
//import jagoclient.partner.PartnerFrame;                          //~1A8cR~
import wifidirect.PartnerFrame;                                    //~1A8cI~
import wifidirect.Server;                                          //~1A84R~
import jagoclient.partner.partner.Partner;

public class IPConnection                                          //~1A84R~
		implements ProgDlgI//~3105R~//~v101R~
{                                                                  //~2C29R~
//  public  static final int NFC_SERVER=1;    //identify connection type//~1A6BI~//~1AadR~
//  public  static final int NFC_CLIENT=2;                         //~1A6BI~//~1AadR~
//  public  static final int WD_SERVER=3;                          //~1A6BI~//~1AadR~
//  public  static final int WD_CLIENT=4;                          //~1A6BI~//~1AadR~
//  public  static final String PKEY_SERVER_PORT="ServerPort";     //~1A6sI~//~1AadR~
//  private static final String WDCLIENT_IPPREFIX="ClientOf-";     //~1A67I~//~1AadR~
	public Server S=null;                                          //~v101I~
	private WDA aWDA;                                              //~1A65I~
	private int portNo;                                  //~v101R~
	private String myIPAddr;                                       //~v101I~
    private boolean wifiStat;                                      //~1A05I~
    private int waitingDialog=0;                                   //~v101I~
    private Partner partner;                                       //~v101I~
	ListClass PartnerList;
//  FormTextField ServerPort;//~v101I~                             //~1A84R~
    MainFrame F;                                                   //~v101I~
//  private String partnerprefix;                                  //~1A6nI~//~1A8aR~
//  public IPConnection(MainFrame Pgf)                            //~v101I~//~1A84R~
    public IPConnection()                                          //~1A84R~
	{                                                              //~3105R~
//        super(Pgf,AG.resource.getString(R.string.Title_IPConnection),//~1A61I~//~1A84R~
//                (AG.layoutMdpi/*mdpi and height or width <=320*/ ? R.layout.ipconnection_mdpi : R.layout.ipconnection),//~1A61I~//~1A84R~
//                true,false);                                       //~1A61I~//~1A84R~
//        setWindowSize(90/*W:90%*/,0/*H=wrap content*/,!AG.layoutMdpi/*for landscape,use ScrHeight for width limit if not mdpi*/);//~1A6qI~//~1A84R~
//        F=Pgf;                                                     //~v101I~//~1A84R~
//        partnerprefix=AG.resource.getString(R.string.PartnerNamePeer)+"-";//~1A6nI~//~1A84R~
//        tvSession=(TextView)(findViewById(R.id.Session));          //~1A6nI~//~1A84R~
//        displayIPAddress();                                        //~v101I~//~1A84R~
//        getPartnerList();                                          //~v101I~//~1A84R~
//        displaySession();                                          //~1A67I~//~1A84R~
//        if (!setSelection())                                       //~v101R~//~1A84R~
//            if (!setSelection(SlastConnectName))                   //~v101R~//~1A84R~
//            {                                                      //~v101R~//~1A84R~
//                if (PartnerList.first()!=null)                     //~v101R~//~1A84R~
//                    PL.select(0);                                  //~v101R~//~1A84R~
//            }                                                      //~v101R~//~1A84R~
//        portNo=Prop.getPreference(PKEY_SERVER_PORT,AG.DEFAULT_SERVER_PORT);//~v101R~//~1A84R~
//        oldportNo=portNo;                                          //~v101I~//~1A84R~
//        ServerPort=new FormTextField(this,R.id.ServerPort,Integer.toString(portNo),-1/*cols by xml*/);//~1A69I~//~1A84R~
//        new ButtonAction(this,0,R.id.Edit);                   //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.Add);                    //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.Delete);                 //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.ListUp);                 //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.ListDown);               //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.OK);                     //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.Cancel);                 //~v101I~//~1A84R~
//        new ButtonAction(this,0,R.id.Help);                   //~v101I~//~1A84R~
//        gameButton=new ButtonAction(this,0,R.id.IPGame);      //~v101R~//~1A84R~
//        acceptButton=new ButtonAction(this,0,R.id.AcceptIPConnection);//~v101R~//~1A84R~
//        connectButton=new ButtonAction(this,0,R.id.IPConnect);//~v101R~//~1A84R~
//        if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~v101R~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            acceptButton.setEnabled(false);                        //~v101R~//~1A84R~
//            connectButton.setAction(R.string.IPDisConnect);        //~v101R~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        else                                                       //~v101I~//~1A84R~
//        if (AG.RemoteStatusAccept==AG.RS_IPLISTENING)              //~v101R~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            gameButton.setEnabled(false);                          //~v101R~//~1A84R~
//            acceptButton.setAction(R.string.StopAcceptIPConnection);//~v101R~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        else                                                       //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            gameButton.setEnabled(false);                          //~v101R~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        btnDirect=                                                 //~1A6oI~//~1A84R~
//            new ButtonAction(this,0,R.id.WiFiDirectButton); //change visibility from gone to visible//~1A65R~//~1A84R~
//        btnSetting=new ButtonAction(this,0,R.id.CallWiFiSetting);   //change visibility from gone to visible//~1A6DM~//~1A84R~
//        if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android3        //~1A6oI~//~1A84R~
//        {                                                          //~1A6DI~//~1A84R~
//            btnDirect.setEnabled(false);                           //~1A6oI~//~1A84R~
//            btnSetting.setEnabled(false);                          //~1A6DI~//~1A84R~
//        }                                                          //~1A6DI~//~1A84R~
//        setDismissActionListener(this/*DoActionListener*/);        //~@@@@I~//~v101I~//~1A84R~
//        validate();                                              //~1A84R~
//        show();                                                  //~1A84R~
//        AG.aIPConnection=this;  //used when PartnerThread detected err//~1A6kI~//~1A84R~
        myIPAddr=Utils.getIPAddressDirect(); //WiFiDirect IP addr(with local macaddr)//~1A8cI~
    	portNo=getPortNo();                                        //~1A84I~
		startWD();                                                 //~1A84I~
	}
//    //******************************************                   //~1A6kI~//~1A84R~
//    public void updateViewDisconnected()                           //~1A6kI~//~1A84R~
//    {                                                              //~1A6kI~//~1A84R~
//        acceptButton.setEnabled(true);                             //~1A6kI~//~1A84R~
//        acceptButton.setAction(R.string.AcceptIPConnection);       //~1A6kI~//~1A84R~
//        connectButton.setEnabled(true);                            //~1A6kI~//~1A84R~
//        connectButton.setAction(R.string.IPConnect);               //~1A6kI~//~1A84R~
//        gameButton.setEnabled(false);                              //~1A6kI~//~1A84R~
//        displayIPAddress();                                        //~1A6kI~//~1A84R~
//        TextView v=tvSession;                                      //~1A6nR~//~1A84R~
//        String s=AG.resource.getString(R.string.NoSession);        //~1A6kI~//~1A84R~
//        new Component().setText(v,s);                              //~1A6kI~//~1A84R~
//        setSelection(); //draw listview agter connection lost      //~1A6rI~//~1A84R~
//    }                                                              //~1A6kI~//~1A84R~
//    public void doAction (String o)                              //~1A84R~
//    {                                                              //~2C26R~//~1A84R~
//        try                                                        //~3117I~//~1A84R~
//        {                                                          //~3117I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.IPGame)))  //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                if (startGame())                                 //~1A84R~
//                {                                                //~1A84R~
//                    setVisible(false); dispose();//~v101I~       //~1A84R~
//                }                                                //~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.IPConnect)))     //~@@@@I~//~2C30I~//~3105R~//~3117R~//~v101R~//~1A84R~
//            {                                                          //~2C30R~//~3117R~//~1A84R~
//                if (connectPartnerTest())                          //~v101R~//~1A84R~
//                {                                                  //~v101I~//~1A84R~
//                    writeplist();                                  //~1A6xI~//~1A84R~
//                    setVisible(false); dispose();                  //~v101R~//~1A84R~
//                    waitingDialog=R.string.IPConnect;              //~v101I~//~1A84R~
//                }                                                  //~v101I~//~1A84R~
//            }                                                      //~3117R~//~1A84R~
//            else                                                   //~3117R~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.IPDisConnect)))//~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                setVisible(false); dispose();                      //~v101I~//~1A84R~
//                waitingDialog=R.string.IPDisConnect;               //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.AcceptIPConnection)))//~3117R~//~v101R~//~1A84R~
//            {                                                      //~3117R~//~1A84R~
//                savePort();                                        //~v101I~//~1A84R~
//                if (startServer())                                         //~3105I~//~3117R~//~v101I~//~1A84R~
//                {                                                  //~v101I~//~1A84R~
//                    setVisible(false); dispose();                  //~v101R~//~1A84R~
//                    waitingDialog=R.string.AcceptIPConnection;     //~v101I~//~1A84R~
//                    acceptButton.setEnabled(false);                //~v101R~//~1A84R~
//                }                                                  //~v101I~//~1A84R~
//            }                                                      //~3117R~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.StopAcceptIPConnection)))//~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                stopServer();                                      //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.Edit)))//~3117R~//~v101R~//~1A84R~
//            {                                                          //~3105R~//~3117R~//~1A84R~
//                editPartner();                                      //~v101I~//~1A84R~
//            }                                                      //~3117R~//~1A84R~
//            else                                                   //~3117R~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.Add)))     //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                addPartner();                                       //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.Delete)))  //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                deletePartner();                                    //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.ListUp)))  //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                listUp();                                          //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.ListDown)))//~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                listDown();                                        //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.OK)))      //~v101R~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                setVisible(false); dispose();                      //~v101I~//~1A84R~
//                writeplist();                                      //~v101R~//~1A84R~
//                savePort();                                        //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~3117R~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.Cancel)))  //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                setVisible(false); dispose();                      //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.Help)))    //~3117R~//~1A84R~
//            {                                                      //~3117R~//~1A84R~
//                new HelpDialog(null,R.string.HelpTitle_IPConnection,"IPConnection");//~1A05R~//~1A84R~
//            }                                                      //~3117R~//~1A84R~
//            else                                                   //~v101I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~v101I~//~1A84R~
//            {               //callback from Dialog after currentLayout restored//~v101I~//~1A84R~
//                afterDismiss(waitingDialog);                       //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            else                                                   //~1A65I~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.WiFiDirectButton)))  //modal but no inputWait//~1A65I~//~1A84R~
//            {               //callback from Dialog after currentLayout restored//~1A65I~//~1A84R~
//                startWD();                                         //~1A65I~//~1A84R~
//            }                                                      //~1A65I~//~1A84R~
//            else                                                   //~1A6DI~//~1A84R~
//            if (o.equals(AG.resource.getString(R.string.CallWiFiSetting)))  //modal but no inputWait//~1A6DI~//~1A84R~
//            {               //callback from Dialog after currentLayout restored//~1A6DI~//~1A84R~
//                callWiFiSetting();                                 //~1A6DI~//~1A84R~
//            }                                                      //~1A6DI~//~1A84R~
//        }                                                          //~3117I~//~1A84R~
//        catch(Exception e)                                         //~3117I~//~1A84R~
//        {                                                          //~3117I~//~1A84R~
//            Dump.println(e,"IPConnection:doAction:"+o);     //~3117I~//~v101R~//~1A84R~
//        }                                                          //~3117I~//~1A84R~
//    }                                                            //~1A84R~
    //***********************************************************************
    //*from WDA
    //***********************************************************************
    public boolean buttonAction(int Pbuttonid)                            //~1A84I~
    {                                                              //~1A84I~
        boolean dismiss=false;                                     //~1A84I~
        switch(Pbuttonid)                                          //~1A84I~
        {                                                          //~1A84I~
        case WDA.ID_GAME:                                          //~1A84I~
            if (startGame())                                       //~1A84I~
                dismiss=true;                                      //~1A84I~
            break;                                                 //~1A84I~
        case WDA.ID_CONNECT:                                       //~1A84I~
            if (connectPartnerTest())                              //~1A84I~
            {                                                      //~1A84I~
                dismiss=true;                                      //~1A84I~
                waitingDialog=R.string.IPConnect;                  //~1A84I~
            }                                                      //~1A84I~
            break;                                                 //~1A84I~
        case WDA.ID_ACCEPT:                                        //~1A84I~
            if (startServer())                                     //~1A84I~
            {                                                      //~1A84I~
                dismiss=true;                                      //~1A84I~
                waitingDialog=R.string.AcceptIPConnection;         //~1A84I~
            }                                                      //~1A84I~
            break;                                                 //~1A84I~
        case WDA.ID_STOPACCEPT:                                    //~1A84I~
			stopServer();                                          //~1A84I~
            break;                                                 //~1A84I~
        case WDA.ID_DISCONNECT:                                    //~1A84I~
            dismiss=true;                                          //~1A84I~
            waitingDialog=R.string.IPDisConnect;                   //~1A84I~
            break;                                                 //~1A84I~
        }                                                          //~1A84I~
        if (dismiss)                                               //~1A84I~
			onCloseWDA();	//get parm                             //~1A84I~
        return dismiss;                                            //~1A84I~
    }                                                              //~1A84I~
//    //******************************************                   //~v101I~//~1A84R~
//    private void displayIPAddress()                                //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        TextView v=(TextView)(findViewById(R.id.YourIPA));         //~v101I~//~1A84R~
//        String ipa=AjagoUtils.getIPAddress(false/*ipv4 only*/);         //~v101R~//~1A84R~
//        myIPAddr=ipa;                                              //~v101I~//~1A84R~
//        wifiStat=!myIPAddr.equals(AjagoUtils.IPA_NA);                    //~1A05I~//~1A84R~
//        new Component().setText(v,ipa);                            //~1A6kI~//~1A84R~
//        if (!wifiStat)                                             //~1A05I~//~1A84R~
//            errWifi();                                             //~1A05I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private void displaySession()                                  //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        TextView v=tvSession;                                      //~1A6nI~//~1A84R~
//        String ipa;                                                //~v101I~//~1A84R~
//        if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            ipa=AG.RemoteInetAddress;                     //~v101I~//~1A84R~
//            v.setTextColor(connectedColor);                        //~v101I~//~1A84R~
//            updateWDClient(ipa);                                   //~1A67I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        else                                                       //~v101I~//~1A84R~
//            ipa=AG.resource.getString(R.string.NoSession);         //~v101I~//~1A84R~
//        v.setText(ipa);                                            //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
    //******************************************                   //~v101I~
    private boolean startGame()                                    //~v101I~
    {                                                              //~v101I~
    	if (AG.aPartnerFrameIP!=null && AG.aPartnerFrameIP.PT!=null//~v101I~
    	&&  AG.aPartnerFrameIP.PT.isAlive())                       //~v101I~
        {                                                          //~v101I~
            AG.aPartnerFrameIP.doAction(AG.resource.getString(R.string.Game));//~v101I~
            return true;                                           //~v101I~
        }                                                          //~v101I~
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
//        String s=getSelected();                                    //~v101I~//~1A84R~
//        if (s==null)                                               //~v101I~//~1A84R~
//            return false;                                          //~v101I~//~1A84R~
//        Partner c=pfind(s);                                        //~v101I~//~1A84R~
//        if (c==null) // try connecting to this partner server, if not trying already//~v101I~//~1A84R~
//            return false;                                          //~v101I~//~1A84R~
//        partner=c;                                                 //~v101I~//~1A84R~
        return true;                                               //~v101I~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
    private boolean/*dispose*/ connectPartner()                    //~v101R~
    {                                                              //~v101I~
        if (partner!=null) // try connecting to this partner server, if not trying already//~v101R~
        {                                                          //~v101I~
//  		writeplist();                                          //~v101I~//~1A84R~
        	Partner c=partner;                                     //~v101I~
//          int connectiontype=(c.Name.startsWith(partnerprefix)) ? WD_CLIENT : 0;//~1A6BI~//~1A84R~
//          int connectiontype=WD_CLIENT;                          //~1A84I~//~1AadR~
            int connectiontype=jagoclient.partner.IPConnection.WD_CLIENT;//~1AadI~
//          SlastConnectName=c.Name;                               //~v101I~
            PartnerFrame cf=                                       //~v101I~
                new PartnerFrame(                                  //~v101I~
                    Global.resourceString("Connection_to_")+c.Name,false,connectiontype);//~1A6BI~
            new ConnectPartner(c,cf);                              //~v101I~
            return true;                                           //~v101I~
        }                                                          //~v101I~
        return false;                                              //~v101I~
    }                                                              //~v101I~
    //***********************************************************************
    //*from WDA
    //***********************************************************************
    public void disconnectPartner()                               //~v101M~//~1A87R~
    {                                                              //~v101M~
    	if (AG.aPartnerFrameIP!=null)                              //~v101R~
        {                                                          //~1A87I~
        	if (Dump.Y) Dump.println("IPConnection:disconnectPartner");//~1A87I~
        	AG.aPartnerFrameIP.disconnect();                       //~v101M~
        }                                                          //~1A87I~
  	}                                                              //~v101M~
    //***********************************************************************//~1Ac3I~
    //*from WDA                                                    //~1Ac3I~
    //***********************************************************************//~1Ac3I~
    public void disconnectPartner(boolean Punpair)                 //~1Ac3I~
    {                                                              //~1Ac3I~
    	if (AG.aPartnerFrameIP!=null)                              //~1Ac3I~
        {                                                          //~1Ac3I~
        	if (Dump.Y) Dump.println("IPConnection:disconnectPartner Punpair="+Punpair);//~1Ac3I~
        	AG.aPartnerFrameIP.disconnect(Punpair);                //~1Ac3I~
        }                                                          //~1Ac3I~
  	}                                                              //~1Ac3I~
    //***********************************************************************
    //*from WDA
    //***********************************************************************
    public void unpaired()                                         //~1A87I~
    {                                                              //~1A87I~
        if (Dump.Y) Dump.println("IPConnection:unpaired remotestatus="+AG.RemoteStatus);//~1A87R~
		if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~1A87I~
            disconnectPartner();                                   //~1A87I~
  	}                                                              //~1A87I~
    //******************************************                   //~v101I~
    private void stopServer()                                      //~v101I~
    {                                                              //~v101I~
        Server.cancel();                                           //~v101I~
//      acceptButton.setAction(R.string.AcceptIPConnection);       //~v101I~//~1A84R~
  	}                                                              //~v101I~
    //******************************************                   //~v101I~
    private boolean/*dispose*/ startServer()                       //~v101R~
    {                                                              //~v101R~
        if (!wifiStat)                                             //~1A05I~
        {                                                          //~1A05I~
        	errWifi();                                             //~1A05I~
            return false;                                          //~1A05I~
        }                                                          //~1A05I~
//  	portNo=getPortNo();                                        //~v101I~//~1A84R~
        if (S==null)                                               //~v101R~
//      	S=new Server(portNo,false);                            //~v101R~//+1AecR~
        	S=new Server(portNo,false,false/*not NFC*/);           //+1AecI~
		return true; 	//dispose at boardquestion                 //~v101R~
  	}                                                              //~v101I~
//    //******************************************                   //~v101I~//~1A84R~
//    private void editPartner()                                          //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String s=getSelected();                                    //~v101R~//~1A84R~
//        if (s==null)                                               //~v101I~//~1A84R~
//            return;                                                //~v101I~//~1A84R~
//        Partner c=pfind(PL.getSelectedItem());                 //~1524R~//~v101I~//~1A84R~
//        if (c!=null)                                               //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            new EditPartner(F,PartnerList,c,this);                 //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private void addPartner()                                      //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        new EditPartner(F,PartnerList,this);                       //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private void deletePartner()                                   //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String s=getSelected();                                    //~v101R~//~1A84R~
//        if (s==null)                                               //~v101I~//~1A84R~
//            return;                                                //~v101I~//~1A84R~
//        ListElement lc=PartnerList.first();                        //~v101I~//~1A84R~
//        Partner co;                                                //~v101I~//~1A84R~
//        while (lc!=null)                                           //~v101I~//~1A84R~
//        {   co=(Partner)lc.content();                              //~v101I~//~1A84R~
//            if (co.Name.equals(s))          //~1111R~//~1114R~     //~v101R~//~1A84R~
//            {   PartnerList.remove(lc);                            //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            lc=lc.next();                                          //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        updateplist();                                             //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private void listUp()                                          //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String s=getSelected();                                    //~v101I~//~1A84R~
//        if (s==null)                                               //~v101I~//~1A84R~
//            return;                                                //~v101I~//~1A84R~
//        ListElement lc=PartnerList.first();                        //~v101I~//~1A84R~
//        ListElement prev;                                          //~v101I~//~1A84R~
//        Partner co;                                                //~v101I~//~1A84R~
//        while (lc!=null)                                           //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            co=(Partner)lc.content();                              //~v101I~//~1A84R~
//            if (co.Name.equals(s))                                 //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                prev=lc.previous();                                    //~v101I~//~1A84R~
//                if (prev==null)                                    //~v101I~//~1A84R~
//                    break;                                         //~v101I~//~1A84R~
//                prev=prev.previous();                              //~v101I~//~1A84R~
//                PartnerList.remove(lc);                            //~v101I~//~1A84R~
//                PartnerList.insert(lc,prev);                       //~v101I~//~1A84R~
//                break;                                             //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            lc=lc.next();                                          //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        updateplist();                                             //~v101I~//~1A84R~
//        setSelection(s);                                           //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private void listDown()                                        //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String s=getSelected();                                    //~v101I~//~1A84R~
//        if (s==null)                                               //~v101I~//~1A84R~
//            return;                                                //~v101I~//~1A84R~
//        ListElement lc=PartnerList.first();                        //~v101I~//~1A84R~
//        ListElement next;                                          //~v101I~//~1A84R~
//        Partner co;                                                //~v101I~//~1A84R~
//        while (lc!=null)                                           //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            co=(Partner)lc.content();                              //~v101I~//~1A84R~
//            if (co.Name.equals(s))                                 //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                next=lc.next();                                    //~v101I~//~1A84R~
//                if (next==null)                                    //~v101I~//~1A84R~
//                    break;                                         //~v101I~//~1A84R~
//                PartnerList.remove(lc);                            //~v101I~//~1A84R~
//                PartnerList.insert(lc,next);                       //~v101I~//~1A84R~
//                break;                                             //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            lc=lc.next();                                          //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        updateplist();                                             //~v101I~//~1A84R~
//        setSelection(s);                                           //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private void getPartnerList()                                  //~v101I~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String l;                                                //~1A84R~
//        PL=new ListIP(this,R.id.PartnerList,R.layout.textrowlist); //~1A6nI~//~1A84R~
//        PL.addActionListener(this);                            //~1524R~//~v101I~//~1A84R~
//        PL.setBackground(Global.gray);                         //~1524R~//~v101I~//~1A84R~
//        Partner cp;                                                //~v101I~//~1A84R~
//        PartnerList=new ListClass();                               //~v101I~//~1A84R~
//        try // read connections from partner.cfg                   //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            BufferedReader in=Global.getStream(".partner.cfg");    //~v101I~//~1A84R~
//            while (true)                                           //~v101I~//~1A84R~
//            {                                                      //~v101I~//~1A84R~
//                l=in.readLine();                                   //~v101I~//~1A84R~
//                if (l==null || l.equals("")) break;                //~v101I~//~1A84R~
//                cp=new Partner(l);                                 //~v101I~//~1A84R~
//                if (cp.valid())                                    //~v101I~//~1A84R~
//                {                                                  //~v101I~//~1A84R~
//                    PL.add(cp.Name);                               //~v101I~//~1A84R~
//                    PartnerList.append(new ListElement(cp));       //~v101I~//~1A84R~
//                }                                                  //~v101I~//~1A84R~
//                else break;                                        //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            in.close();                                            //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        catch (Exception ex) {}                                    //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private boolean setSelection()                                 //~v101R~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String  ipa=AG.RemoteInetAddress;                          //~v101I~//~1A84R~
//        if (ipa==null)                                             //~v101I~//~1A84R~
//            return false;                                          //~v101R~//~1A84R~
//        int idx=pfindByAddr(ipa);                                  //~v101I~//~1A84R~
//        if (idx<0)                                                 //~v101I~//~1A84R~
//            return false;                                          //~v101R~//~1A84R~
//        PL.select(idx);                                            //~v101I~//~1A84R~
//        return true;                                               //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private boolean setSelection(String Plastname)                 //~v101R~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        int idx=pfindidx(Plastname);                               //~v101I~//~1A84R~
//        if (idx<0)                                                 //~v101I~//~1A84R~
//            return false;                                          //~v101R~//~1A84R~
//        PL.select(idx);                                            //~v101I~//~1A84R~
//        return true;                                               //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    /** find a specific partner server by name */                  //~v101I~//~1A84R~
//    public Partner pfind (String s)                                //~v101I~//~1A84R~
//    {   ListElement lc=PartnerList.first();                        //~v101I~//~1A84R~
//        Partner c;                                                 //~v101I~//~1A84R~
//        while (lc!=null)                                           //~v101I~//~1A84R~
//        {   c=(Partner)lc.content();                               //~v101I~//~1A84R~
//            if (c.Name.equals(s)) return c;                        //~v101I~//~1A84R~
//            lc=lc.next();                                          //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        return null;                                               //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    public int pfindidx(String s)                                  //~v101I~//~1A84R~
//    {                                                              //~v101R~//~1A84R~
//        if (s==null)                                               //~v101I~//~1A84R~
//            return -1;                                             //~v101I~//~1A84R~
//        ListElement lc=PartnerList.first();                        //~v101I~//~1A84R~
//        Partner c;                                                 //~v101I~//~1A84R~
//        int idx=0;                                                 //~v101I~//~1A84R~
//        while (lc!=null)                                           //~v101I~//~1A84R~
//        {   c=(Partner)lc.content();                               //~v101I~//~1A84R~
//            if (c.Name.equals(s)) return idx;                      //~v101I~//~1A84R~
//            lc=lc.next();                                          //~v101I~//~1A84R~
//            idx++;                                                 //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        return -1;                                                 //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private int pfindByAddr(String s)                              //~v101I~//~1A84R~
//    {   ListElement lc=PartnerList.first();                        //~v101I~//~1A84R~
//        Partner c;                                                 //~v101I~//~1A84R~
//        int idx=0;                                                //~v101I~//~1A84R~
//        while (lc!=null)                                           //~v101I~//~1A84R~
//        {   c=(Partner)lc.content();                               //~v101I~//~1A84R~
//            if (c.Server.equals(s)) return idx;                      //~v101I~//~1A84R~
//            lc=lc.next();                                        //~1A84R~
//            idx++;//~v101I~                                      //~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        return -1;                                                //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    /** update the list of partners */                             //~v101I~//~1A84R~
//    //******************************************                   //~1A36I~//~1A84R~
//    public void updateplist ()                                     //~v101R~//~1A84R~
//    {                                                              //~v101R~//~1A84R~
//        swUpdate=true;                                             //~v101I~//~1A84R~
//            ListElement lc=PartnerList.first();                    //~v101I~//~1A84R~
//            PL.removeAll();                                        //~v101I~//~1A84R~
//            while (lc!=null)                                       //~v101I~//~1A84R~
//            {   Partner c=(Partner)lc.content();                   //~v101I~//~1A84R~
//                PL.add(c.Name);                                    //~v101I~//~1A84R~
//                lc=lc.next();                                      //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~1A36I~//~1A84R~
//    //** from EditPartner                                          //~1A36R~//~1A84R~
//    //******************************************                   //~1A36I~//~1A84R~
//    public void updateplist (String Pname)                         //~1A36I~//~1A84R~
//    {                                                              //~1A36I~//~1A84R~
//        updateplist ();                                             //~1A36I~//~1A84R~
//        setSelection(Pname);                                       //~1A36I~//~1A84R~
//    }                                                              //~1A36I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    /** update the list of partners */                             //~v101I~//~1A84R~
//    private void writeplist()                                      //~v101R~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        if (!swUpdate)                                           //~1A84R~
//            return;//~v101I~                                     //~1A84R~
//        try                                                        //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            PrintWriter out=new PrintWriter(                        //~v101I~//~1A84R~
//                new FileOutputStream(Global.home()+".partner.cfg"));//~v101I~//~1A84R~
//            ListElement lc=PartnerList.first();                    //~v101I~//~1A84R~
//            while (lc!=null)                                       //~v101I~//~1A84R~
//            {   Partner c=(Partner)lc.content();                   //~v101I~//~1A84R~
//                c.write(out);                                      //~v101I~//~1A84R~
//                lc=lc.next();                                      //~v101I~//~1A84R~
//            }                                                      //~v101I~//~1A84R~
//            out.close();                                           //~v101I~//~1A84R~
//            swUpdate=false;                                        //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        catch (Exception e)                                        //~v101I~//~1A84R~
//        {   if (F!=null) new Message(F,                            //~v101I~//~1A84R~
//                Global.resourceString("Could_not_write_to_partner_cfg"));//~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    private String getSelected()                                   //~v101R~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        String s=PL.getSelectedItem();                             //~v101I~//~1A84R~
//        if (s==null || s.equals(""))                               //~v101I~//~1A84R~
//        {                                                          //~v101I~//~1A84R~
//            errNotSelected();                                       //~v101I~//~1A84R~
//            s=null;                                                //~v101I~//~1A84R~
//        }                                                          //~v101I~//~1A84R~
//        return s;                                                  //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
//    //******************************************                   //~v101I~//~1A84R~
//    public static void errNotSelected()                            //~v101R~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        AView.showToast(R.string.ErrNotSelected);                  //~v101I~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
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
	public static int getPortNo()                                        //~v101R~//~1A84R~
    {                                                              //~v101I~
//      String port=ServerPort.getText();                          //~v101R~//~1A84R~
//      int n=0;                                                   //~v101I~//~1A84R~
//      if (port!=null)                                            //~v101I~//~1A84R~
//      	n=Integer.parseInt(port);                              //~v101I~//~1A84R~
//  	return n;                                                  //~v101R~//~1A84R~
//  	int n=Global.getParameter("serverport",6970)+1;            //~1A84R~//~1AadR~
        int n=Prop.getPreference(jagoclient.partner.IPConnection.PKEY_SERVER_PORT,AG.DEFAULT_SERVER_PORT)+2;//~1AadR~
        if (Dump.Y) Dump.println("IPConnection getPortNo:"+n);     //~1A84I~
        return n;                                                  //~1A84I~
    }                                                              //~v101I~
//    //******************************************                   //~v101I~//~1A84R~
//    private void savePort()                                        //~v101R~//~1A84R~
//    {                                                              //~v101I~//~1A84R~
//        portNo=getPortNo();                                        //~v101I~//~1A84R~
//        if (portNo!=oldportNo)                                     //~v101I~//~1A84R~
//            Prop.putPreference(PKEY_SERVER_PORT,portNo);           //~v101R~//~1A84R~
//    }                                                              //~v101I~//~1A84R~
    //******************************************                   //~v101I~
//  public void afterDismiss(int Pwaiting)                         //~v101R~//~1A8kR~
    private void afterDismiss(int Pwaiting)                        //~1A8kI~
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
//      AG.aIPConnection=null;                                     //~1A6kI~//~1A84R~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,int Pmsgresid)    //~v101R~
    {                                                              //~v101I~
    	ProgDlg.showProgDlg(this/*ProgDlgI*/,false/*cancelCB*/,Ptitleresid,Pmsgresid,true/*cancelable*/);//~1A2jI~
    }                                                              //~v101I~
    //******************************************                   //~v101I~
	private void waitingResponse(int Ptitleresid,String Pmsg)      //~v101I~
    {                                                              //~v101I~
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
    //***********************************************************************//~1A65I~//~1A84R~
    private void startWD()                                         //~1A65I~//~1A84R~
    {                                                              //~1A65I~//~1A84R~
        if (Dump.Y) Dump.println("IPConnection:startWD");          //~1A65I~//~1A84R~
        try                                                        //~1A65I~//~1A84R~
        {                                                          //~1A65I~//~1A84R~
            aWDA=WDA.showDialog(this);                             //~1A65R~//~1A84R~
            wifiStat=aWDA.swWifiEnable;                            //~1A84I~
        }                                                          //~1A65I~//~1A84R~
        catch(Exception e)                                         //~1A65I~//~1A84R~
        {                                                          //~1A65I~//~1A84R~
            Dump.println(e,"IPConnection:startWD");                //~1A65I~//~1A84R~
        }                                                          //~1A65I~//~1A84R~
    }                                                              //~1A65I~//~1A84R~
    //***********************************************************************//~1A65I~
//  public void onCloseWDA()                                        //~1A67R~//~1A8kR~
    private void onCloseWDA()                                      //~1A8kI~
    {                                                              //~1A65I~
        boolean owner=aWDA.WDAowner;                               //~1A65I~
    	if (Dump.Y) Dump.println("IPConnection:closedWD owner="+owner);//~1A65I~
        try                                                        //~1A65I~
        {                                                          //~1A65I~
            String serveripa=aWDA.WDAipa;                           //~1A65I~
            String peername=aWDA.WDApeer;                        //~1A65I~//~1A67R~
            if (!owner)                                            //~1A65I~
            {                                                      //~1A65I~
		    	if (Dump.Y) Dump.println("IPConnection:closedWD client server="+peername+",addr="+serveripa);//~1A65I~//~1A67R~
//              String partnername=partnerprefix+peername;       //~1A65I~//~1A67R~//~1A8aR~
                String partnername=peername;                       //~1A8aI~
//          	Partner p=new Partner(partnername,serveripa,portNo,Partner.PRIVATE);//~1A65I~//~1A84R~
            	partner=new Partner(partnername,serveripa,portNo,Partner.PRIVATE);//~1A84I~
//              updatePeer(p,partnerprefix);                       //~1A65I~//~1A84R~
            }                                                      //~1A65I~
            else                                                   //~1A67I~
            {                                                      //~1A67I~
		    	if (Dump.Y) Dump.println("IPConnection:closedWD server server="+peername+",addr="+serveripa);//~1A67I~
//              String partnername=partnerprefix+peername;         //~1A67I~//~1A8aR~
                String partnername=peername;                       //~1A8aI~
//          	Partner p=new Partner(partnername,WDCLIENT_IPPREFIX+serveripa,portNo,Partner.PRIVATE);//~1A67I~//~1A84R~
//          	partner=new Partner(partnername,WDCLIENT_IPPREFIX+serveripa,portNo,Partner.PRIVATE);//~1A84I~//~1AadR~
            	partner=new Partner(partnername,jagoclient.partner.IPConnection.WDCLIENT_IPPREFIX+serveripa,portNo,Partner.PRIVATE);//~1AadI~
//              updatePeer(p,partnerprefix);                       //~1A67I~//~1A84R~
            }                                                      //~1A67I~
//          partner=p;                                             //~1A84R~
        }                                                          //~1A65I~
        catch(Exception e)                                         //~1A65I~
        {                                                          //~1A65I~
        	Dump.println(e,"IPConnection:closedWD");               //~1A65I~
        }                                                          //~1A65I~
    }                                                              //~1A65I~
//    //***********************************************************************//~1A65I~//~1A84R~
//    private void updatePeer(Partner Ppartner,String Pprefix)       //~1A65I~//~1A84R~
//    {                                                              //~1A65I~//~1A84R~
//        Partner p;                                                 //~1A65I~//~1A84R~
//        boolean swupdate=false;                                    //~1A65I~//~1A84R~
//    //**************************                                   //~1A65I~//~1A84R~
//        p=pfind(Ppartner.Name);                                     //~1A67I~//~1A84R~
//        if (p!=null)                                               //~1A67I~//~1A84R~
//        {                                                          //~1A67I~//~1A84R~
//            p.Server=Ppartner.Server;                          //~1A65I~//~1A67R~//~1A84R~
//            p.Port=Ppartner.Port;                              //~1A65I~//~1A67R~//~1A84R~
//            swupdate=true;                                     //~1A65I~//~1A67R~//~1A84R~
//        }                                                          //~1A65I~//~1A84R~
//        if (!swupdate)                                             //~1A65R~//~1A84R~
//        {                                                          //~1A65I~//~1A84R~
//            PartnerList.prepend(new ListElement(Ppartner));        //~1A65I~//~1A84R~
//        }                                                          //~1A65I~//~1A84R~
//        updateplist();                                             //~1A65I~//~1A84R~
//        writeplist();   //update partner.cfg                       //~1A67I~//~1A84R~
//        if (!swupdate)                                             //~1A67I~//~1A84R~
//            PL.select(0);                                              //~1A65I~//~1A67R~//~1A84R~
//        else                                                       //~1A67I~//~1A84R~
//            setSelection(Ppartner.Name);                           //~1A67I~//~1A84R~
//    }                                                              //~1A65I~//~1A84R~
//    //***********************************************************************//~1A67I~//~1A84R~
//    private void updateWDClient(String Pipaddr)                    //~1A67I~//~1A84R~
//    {                                                              //~1A67I~//~1A84R~
//        Partner p;                                                 //~1A67I~//~1A84R~
//    //**************************                                   //~1A67I~//~1A84R~
//        if (Dump.Y) Dump.println("IPConnection:updateWDClient this ip="+Pipaddr);//~1A67I~//~1A84R~
//        p=pfindByNetMask(WDCLIENT_IPPREFIX+Pipaddr);               //~1A67I~//~1A84R~
//        if (p==null)                                               //~1A67I~//~1A84R~
//            return;                                                //~1A67I~//~1A84R~
//        p.Server=Pipaddr;                                          //~1A67I~//~1A84R~
//        updateplist();                                             //~1A67I~//~1A84R~
//    }                                                              //~1A67I~//~1A84R~
//    //******************************************                   //~1A67I~//~1A84R~
//    private Partner pfindByNetMask(String s)                       //~1A67I~//~1A84R~
//    {                                                              //~1A67I~//~1A84R~
//        int offs=s.lastIndexOf(".");                               //~1A67I~//~1A84R~
//        if (offs<=0)                                               //~1A67I~//~1A84R~
//            return null;                                           //~1A67I~//~1A84R~
//        String mask=s.substring(0,offs+1);                         //~1A67I~//~1A84R~
//        ListElement lc=PartnerList.first();                        //~1A67I~//~1A84R~
//        Partner c;                                                 //~1A67I~//~1A84R~
//        while (lc!=null)                                           //~1A67I~//~1A84R~
//        {   c=(Partner)lc.content();                               //~1A67I~//~1A84R~
//            if (c.Server.startsWith(mask)) return c;              //~1A67I~//~1A84R~
//            lc=lc.next();                                          //~1A67I~//~1A84R~
//        }                                                          //~1A67I~//~1A84R~
//        return null;                                               //~1A67I~//~1A84R~
//    }                                                              //~1A67I~//~1A84R~
    //***********************************************************************//~1A67I~
    //*from WDA                                                    //~1A84I~
    //***********************************************************************//~1A84I~
    public void dismissWDA()                                       //~1A67I~
    {                                                              //~1A67I~
//        displayIPAddress();                                        //~1A67I~//~1A84R~
//        displaySession();                                          //~1A67I~//~1A84R~
        afterDismiss(waitingDialog);                               //~1A84I~
    }                                                              //~1A67I~
//    //***********************************************************************//~1A6nI~//~1A84R~
//    class ListIP extends List                                                  //~1114//~1A6nI~//~1A84R~
//    {                                                              //~1A6nI~//~1A84R~
//    //*****************                                            //~1A6nI~//~1A84R~
//        public ListIP(Container Pcontainer,int Presid,int Prowresid)//~1A6nI~//~1A84R~
//        {                                                          //~1A6nI~//~1A84R~
//            super(Pcontainer,Presid,Prowresid);                    //~1A6nI~//~1A84R~
//        }                                                          //~1A6nI~//~1A84R~
//    //**********************************************************************//~1A6nI~//~1A84R~
//        @Override                                                  //~1A6nI~//~1A84R~
//        protected void getViewAdjust(int Ppos,TextView Pview,ViewGroup Pparent,ListData Plistdata,int Pselectedpos)//~1A6nI~//~1A84R~
//        {                                                          //~1A6nI~//~1A84R~
//            TextView tv;                                           //~1A6nI~//~1A84R~
//        //*******************                                      //~1A6nI~//~1A84R~
//            if (Dump.Y) Dump.println("ListIP:getViewCustom Ppos="+Ppos);//~1A6nI~//~1A84R~
//            tv=Pview;                                              //~1A6nI~//~1A84R~
//            ListData ld=Plistdata;                                 //~1A6nI~//~1A84R~
//            if (chkDirect(ld,Ppos))                                //~1A6nI~//~1A84R~
//            {                                                      //~1A6nI~//~1A84R~
//                tv.setTextColor(connectedLineColor.getRGB());      //~1A6nI~//~1A84R~
//            }                                                      //~1A6nI~//~1A84R~
//        }                                                          //~1A6nI~//~1A84R~
//        //***************************************                  //~1A6nI~//~1A84R~
//        private boolean chkDirect(ListData Pld,int Ppos)           //~1A6nI~//~1A84R~
//        {                                                          //~1A6nI~//~1A84R~
//            if (Pld.itemtext.startsWith(partnerprefix))            //~1A6nI~//~1A84R~
//            {                                                      //~1A6nI~//~1A84R~
//                Partner p=pfind(Pld.itemtext);                     //~1A6nI~//~1A84R~
//                if (p!=null)                                       //~1A6nI~//~1A84R~
//                {                                                  //~1A6nI~//~1A84R~
//                    String session=tvSession.getText().toString(); //~1A6nI~//~1A84R~
//                    if (session!=null && session.equals(p.Server)) //~1A6nI~//~1A84R~
//                        return true;                               //~1A6nI~//~1A84R~
//                }                                                  //~1A6nI~//~1A84R~
//            }                                                      //~1A6nI~//~1A84R~
//            return false;                                          //~1A6nI~//~1A84R~
//        }                                                          //~1A6nI~//~1A84R~
//    }//class                                                       //~1A6nI~//~1A84R~
//    //***********************************************************************//~1A6yI~//~1A84R~
//    public static void closeDialog()                               //~1A6yI~//~1A84R~
//    {                                                              //~1A6yI~//~1A84R~
//        if (Dump.Y) Dump.println("IPConnection:closeDialog");      //~1A6yI~//~1A84R~
//        if (AG.aIPConnection!=null && AG.aIPConnection.androidDialog.isShowing())//~1A6yI~//~1A84R~
//        {                                                          //~1A6yI~//~1A84R~
//            if (Dump.Y) Dump.println("IPConnection:closeDialog dismiss");//~1A6yI~//~1A84R~
//            AG.aIPConnection.waitingDialog=0;                      //~1A6yI~//~1A84R~
//            AG.aIPConnection.dismiss();                            //~1A6yI~//~1A84R~
//        }                                                          //~1A6yI~//~1A84R~
//    }                                                              //~1A6yI~//~1A84R~
//    //***********************************************************************//~1A6DI~//~1AadR~
//    private void callWiFiSetting()                                 //~1A6DI~//~1AadR~
//    {                                                              //~1A6DI~//~1AadR~
//        if (Dump.Y) Dump.println("IPConnection:callWiFiSetting");  //~1A6DI~//~1AadR~
//        AG.activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//~1A6DI~//~1AadR~
//    }                                                              //~1A6DI~//~1AadR~
//****************************************                         //~1A6tI~//~1A84I~
	public static String getRemoteIPAddr(boolean Pnull)           //~1A6tR~//~1A84I~
    {                                                              //~1A6tI~//~1A84I~
    	String ipa=null;                                           //~1A6tI~//~1A84I~
    	if (AG.RemoteStatus==AG.RS_IPCONNECTED)                    //~5219R~//~1A6tI~//~1A84I~
			ipa=AG.RemoteInetAddress;              //~5219I~       //~1A6tI~//~1A84I~
        if (Dump.Y) Dump.println("DeviceListFragment:getRemoteIPAddr:"+ipa);//~1A6tI~//~1A84I~
        if (!Pnull)                                                //~1A6tI~//~1A84I~
        	if (ipa==null)                                         //~1A6tI~//~1A84I~
            	return "";                                         //~1A6tI~//~1A84I~
        return ipa;                                                //~1A6tI~//~1A84I~
    }                                                              //~1A6tI~//~1A84I~
}

