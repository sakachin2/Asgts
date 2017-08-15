//*CID://+1Ah0R~:                             update#=  150;       //+1Ah0R~
//***********************************************************************//~v101I~
//1Ah0 2016/07/09 (Ahsv:2015/07/19)Bypass NFCSelect, by NFS-WD and NFC-BT button directly.//+1Ah0I~
//1Ad3 2016/07/09 (Ahsv:2015/07/19)Bypass NFCSelect, by NFS-WD and NFC-BT button directly.//~1Ad3I~
//1Aez 2016/07/02 (Bug)CastException at ReplayBoard:L33.Reason is not clear but correct boardType mix.//~1AbzI~
//1Abd 2015/06/15 use not Toast but Dialog for err of Alive Session exist//~1AbdI~
//1Ab8 2015/05/08 NFC Bluetooth handover v3(DialogNFCSelect distributes only)//~1Ab8I~
//1Ab7 2015/05/03 NFC Bluetooth handover v2                        //~1Ab7I~
//1Ab6 2015/05/03 NFC Bluetooth handover change(once delete 1Ab1)  //~1Ab6I~
//1Ab1 2015/05/03 NFC Bluetooth handover                           //~1Ab1I~
//1Ab0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //~1Ab0I~
//1A91 2015/04/19 dislay session type on msg:only one alive session//~1A91I~
//1A8g 2015/03/05 chk only one session alive(Ip,Direct,BT)         //~1A8gI~
//1A71 2015/02/23 drop exit from mainframe(now actin bar hast Exit)//~1A71I~
//1A6s 2015/02/17 move NFC starter from WifiDirect dialog to MainFrame//~1A6sI~
//1A66 2015/01/31 layout:mainframe both hdpi and mdpi in layout/    //~1A66I~//~1A65R~//~1A66I~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//1A4f 2014/11/30 fail to restart partnergame(Cast err PGF to RGF).//~1A4fI~
//                mainFrame:boardType is upredictable when requested game from partner//~1A4fI~
//1A32 2013/04/17 chaneg to open FreeBoardQuestion at start button on FreeGoFrame//~1A32I~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A20 2013/03/22 Exit button for the case emulator has no button  //~1A20I~
//1A1j 2013/03/19 change Help file encoding to utf8                //~1A1jI~
//1A11 2013/03/08 playMenu                                         //~1A11I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//101a 2013/01/30 IP connection                                    //~v101I~
//***********************************************************************//~v101I~
package jagoclient;

import wifidirect.DialogNFC;
import wifidirect.DialogNFCBT;
import wifidirect.DialogNFCSelect;                                 //~1Ab7I~

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.Alert;
import com.Asgts.Prop;                                             //~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.Utils;                                             //~@@@@R~//~v101R~
import com.Asgts.awt.CheckboxMenuItem;                              //~@@@@R~//~v101R~
import com.Asgts.awt.MenuItem;                                      //~@@@@R~//~v101R~
import com.Asgts.gtp.GtpGameQuestion;

import jagoclient.board.Board;
import jagoclient.board.GoFrame;
import jagoclient.board.Notes;
import jagoclient.dialogs.*;
import jagoclient.gui.*;
import jagoclient.partner.BluetoothConnection;
import jagoclient.partner.IPConnection;

public class MainFrame extends GoFrame                             //~@@@2I~
{	CheckboxMenuItem 
		StartPublicServer,TimerInTitle,BigTimer,ExtraInformation,ExtraSendField,
		DoSound,SimpleSound,BeepOnly,Warning,RelayCheck,Automatic,
		EveryMove,FineBoard,Navigation;
	MenuItem StartServer;
//    public Server S=null;                                        //~v101R~
    private GoFrame GF;                                            //~@@@@I~//~@@@2R~
    MyLabel Info;                                                  //~v1A0I~
    public  int boardType;                                         //~1A24I~//~1A2dR~
    public  static final int BT_FREEBOARD=1;                       //~1A24I~//~1A2dR~
    public  static final int BT_LOCAL    =2;                       //~1A24I~//~1A2dR~
    public  static final int BT_BT       =3;                       //~1A24I~//~1A2dR~
//  public  static final int BT_IP       =3;                       //~1A24I~//~1A2dR~//~1AbzR~
    public  static final int BT_REPLAY   =4;                       //~1A2dI~
    public  static final int BT_REPLAY_CLIPBOARD=5;                //~1A4sI~
    public  static final int BT_IP       =6;                       //~1AbzI~
    public  static final int BT_ROBOT    =7;                       //+1Ah0I~
    public  int partnerGameRestartRequestedBoardType;              //~1A4fI~
    private ButtonAction btnNFC;                                   //~1A6sI~
    private ButtonAction btnNFCBT;                                 //~1Ad3I~
//  private static  boolean swNFCBT=true;                          //~1Ab1I~//~1Ab6R~

	public MainFrame (String c)
//  {   super(c+" "+Global.resourceString("Version"));             //~1524R~//~@@@@R~
    {                                                              //~@@@@I~
//     	super(AG.frameId_MainFrame,c+" "+AG.appVersion);                                //~@@@@I~//~@@@2R~//~1A66R~
       	super( (AG.layoutMdpi ? R.layout.mainframe_mdpi : R.layout.mainframe),//~1A66I~
				c+" "+AG.appVersion);                              //~1A66I~
//        int boardsz,piecetype;                                     //~@@@@R~//~v1A0R~
//        if (AG.isLangJP)                                           //~@@@@I~//~v1A0R~
//        {                                                          //~@@@@I~//~v1A0R~
//            boardsz=AG.BOARDSIZE_SHOGI;  //default for JP                          //~@@@@I~//~@@@2R~//~v1A0R~
//            piecetype=Board.PIECETYPE_SHOGI;  //default for JP                     //~@@@@R~//~@@@2R~//~v1A0R~
//        }                                                          //~@@@@I~//~v1A0R~
//        else                                                       //~@@@@I~//~v1A0R~
//        {                                                          //~@@@@I~//~v1A0R~
//            boardsz=AG.BOARDSIZE_CHESS;  //default for US                         //~@@@@I~//~@@@2R~//~v1A0R~
//            piecetype=Board.PIECETYPE_CHESS;   //default for US                    //~@@@@R~//~@@@2R~//~v1A0R~
//        }                                                          //~@@@@I~//~v1A0R~
//        AG.propBoardSize=Prop.getPreference(AG.PKEY_BOARD_SIZE,boardsz);//~@@@@R~//~v1A0R~
//        Board.pieceType=Prop.getPreference(AG.PKEY_PIECE_TYPE,piecetype);//~@@@@R~//~v1A0R~
//        B=new Board(AG.propBoardSize,this);                        //~@@@2I~//~v1A0R~
        Info=new MyLabel(this,R.id.SDInfo);                        //~v1A0R~
		String sdpath=Prop.getSDPath42();                          //~v1A0R~
//		Info.setText("SDCard:"+(sdpath==null?"N/A":sdpath));       //~v1A0R~//~1A1jR~
        String info="SDCard:"+(sdpath==null?"N/A":sdpath);         //~1A1jI~
        info+="\nLang="+AG.language;                               //~1A1jI~
		Info.setText(info);                                        //~1A1jI~
        B=new Board(AG.BOARDSIZE_SHOGI,this);                      //~v1A0R~
//        int ic=AG.isChessBoard()?-1/*white*/:1/*Black*/;            //~@@@2I~//~v1A0R~
        B.putInitialPiece(1/*black*/,0/*gameoptions*/,0/*handicap*/);//~@@@2R~           //~v1A0R~
        boolean swSmall=AG.portrait;	//se small button if portrait and small pixel device//~@@@2I~
//        new ButtonAction(this,0,R.id.ChangeBoard,swSmall);         //~v101R~//~v1A0R~
//        new ButtonAction(this,0,R.id.ChangePiece,swSmall);         //~v101R~//~v1A0R~
        new ButtonAction(this,0,R.id.Options,swSmall);             //~v101R~
//        new ButtonAction(this,0,R.id.LocalGame,swSmall);           //~v101R~//~1A11R~
//        new ButtonAction(this,0,R.id.StudyBoard,swSmall);        //~1A11R~
//        new ButtonAction(this,0,R.id.RemoteGame,swSmall);          //~v101R~//~1A11R~
//        new ButtonAction(this,0,R.id.RemoteIP,swSmall);            //~v101R~//~1A11R~
        new ButtonAction(this,0,R.id.Play,swSmall);                //~1A11R~
        new ButtonAction(this,0,R.id.Help,swSmall);                //~v101R~
//        new ButtonAction(this,0,R.id.Exit,swSmall);                //~1A20I~//~1A71R~
        btnNFC=new ButtonAction(this,0,R.id.WiFiNFCButton,swSmall);//~1A6sI~
        btnNFCBT=new ButtonAction(this,0,R.id.BTNFCButton,swSmall);//~1Ad3I~
        if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android4        //~1A6sI~
        {                                                          //~1Ad3I~
	        btnNFC.setEnabled(false);                              //~1A6sI~
	        btnNFCBT.setEnabled(false);                            //~1Ad3I~
        }                                                          //~1Ad3I~
        Show=true;                                                 //~@@@2I~
		setVisible(true);                                          //~@@@2I~
		repaint();                                                 //~@@@2I~
	}

	public boolean close ()
	{	                                                           //~@@@@R~
//        if (Global.getParameter("confirmations",true))           //~@@@@I~
//        {                                                        //~@@@@R~
//            CloseMainQuestion CMQ=new CloseMainQuestion(this);   //~@@@@I~
//            if (CMQ.Result) doclose();                           //~@@@@R~
//            return false;                                        //~@@@@R~
//        }                                                        //~@@@@R~
//        else                                                     //~@@@@R~
		{	doclose();
			return false;
		}
	}
	
	public void doclose ()
    {                                                              //~@@@@I~
		super.doclose();
//        Global.writeparameter(".go.cfg");                        //~@@@@R~
//        if (S!=null) S.close();                                  //~v101R~
//  	if (!Global.isApplet()) System.exit(0);		               //~1309R~
    	if (!Global.isApplet()) Utils.exit(0);	//@@@@ redirect to finish()//~1309I~//~@@@@R~
	}

	public void doAction (String o)
	{                                                              //~@@@@I~
		if (Dump.Y)Dump.println("MainFrame doAction entry o="+o);  //~1AbdI~
//        if (o.equals(AG.resource.getString(R.string.ChangeBoard))) //~@@@@I~//~v1A0R~
//        {                                                          //~@@@@I~//~v1A0R~
//            changeBoard(true/*change board size*/);  //go to GoFrame//~@@@2R~//~v1A0R~
//            Prop.putPreference(AG.PKEY_BOARD_SIZE,AG.propBoardSize);//~@@@@R~//~v1A0R~
//        }                                                          //~@@@@I~//~v1A0R~
//        else                                                       //~@@@2I~//~v1A0R~
        if (o.equals(AG.resource.getString(R.string.ActionChangeCoordinate))) //from MainFrameOptions//~@@@2R~//~v1A0R~
        {                                                          //~@@@2I~//~v1A0R~
            changeBoard(false/*coordinate draw*/);  //go to GoFrame//~@@@2R~//~v1A0R~
        }                                                          //~@@@2I~//~v1A0R~
        else                                                       //~@@@@I~//~v1A0R~
//        if (o.equals(AG.resource.getString(R.string.ChangePiece))) //~@@@@I~//~v1A0R~
//        {                                                          //~@@@@I~//~v1A0R~
//            changePiece();                                         //~@@@2I~//~v1A0R~
//            Prop.putPreference(AG.PKEY_PIECE_TYPE,Board.pieceType);//~@@@@R~//~v1A0R~
//        }                                                          //~@@@@I~//~v1A0R~
//        else                                                       //~@@@@I~//~v1A0R~
        if (o.equals(AG.resource.getString(R.string.Options)))     //~@@@2I~
        {                                                          //~@@@2I~
            new MainFrameOptions(this);                            //~@@@2I~
        }                                                          //~@@@2I~
        else                                                       //~@@@2I~
        if (o.equals(AG.resource.getString(R.string.LocalGame)))   //~@@@2R~//~1A11R~
        {                                                          //~@@@2I~//~1A11R~
            boardType=BT_LOCAL; //callback from AMenu              //~1A24I~
            startLocalGame();                                      //~@@@2I~//~1A11R~
        }                                                          //~@@@2I~//~1A11R~
        else                                                       //~@@@2I~//~1A11R~
        if (o.equals(AG.resource.getString(R.string.StudyBoard)))  //~1A11R~
        {                                                          //~1A11R~
            boardType=BT_FREEBOARD;                                 //~1A24I~
            openStudyBoard();                                      //~1A11R~
        }                                                          //~1A11R~
        if (o.equals(AG.resource.getString(R.string.ReplayFile))) //~1A2dI~
        {                                                          //~1A2dI~
            boardType=BT_REPLAY;                                    //~1A2dI~
            openReplayBoard();                                     //~1A2dI~
        }                                                          //~1A2dI~
        else                                                       //~1A11R~
        if (o.equals(AG.resource.getString(R.string.ReplayFileClipboard)))//~1A4sI~
        {                                                          //~1A4sI~
            boardType=BT_REPLAY_CLIPBOARD;                         //~1A4sI~
            openReplayBoard();                                     //~1A4sI~
        }                                                          //~1A4sI~
        else                                                       //~1A4sI~
        if (o.equals(AG.resource.getString(R.string.RemoteGame)))  //~@@@2R~//~1A11R~
        {                                                          //~@@@2I~//~1A11R~
            boardType=BT_BT;                                       //~1A24I~
            startRemoteGame();                                     //~@@@2R~//~1A11R~
        }                                                          //~@@@2I~//~1A11R~
        else                                                       //~@@@2I~//~1A11R~
        if (o.equals(AG.resource.getString(R.string.RemoteIP)))    //~v101I~//~1A11R~
        {                                                          //~v101I~//~1A11R~
            boardType=BT_IP;                                       //~1A24I~
            startRemoteIP();                                       //~v101I~//~1A11R~
        }                                                          //~v101I~//~1A11R~
        else                                                       //~1Ab0I~
        if (o.equals(AG.resource.getString(R.string.MenuitemWiFiDirect)))//~1Ab0I~
        {                                                          //~1Ab0I~
            boardType=BT_IP;                                       //~1Ab0I~
            startRemoteIPDirect();                                 //~1Ab0I~
        }                                                          //~1Ab0I~
        else                                                       //~1Ab0I~
        if (o.equals(AG.resource.getString(R.string.Play)))        //~1A11R~
        {                                                          //~1A11R~
            playMenu();                                            //~1A11R~
        }                                                          //~1A11R~
        else                                                       //~v101I~
        if (o.equals(AG.resource.getString(R.string.Help)))        //~@@@@I~
        {                                                          //~@@@@I~
//  		new HelpDialog(this,R.string.Help_MainFrame);               //~@@@@I~//~v1A0R~
			new HelpDialog(null,R.string.HelpTitle_MainFrame,"MainFrame");//~v1A0R~
        }                                                          //~@@@@I~
        else                                                       //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.ActionRestoreFrame)))//~@@@@I~//~@@@2R~
        {                                                          //~@@@@I~
			restoreFrame();                                        //~@@@@I~
        }                                                          //~@@@@I~
        else                                                       //~1A20I~
        if (o.equals(AG.resource.getString(R.string.Exit)))        //~1A20I~
        {                                                          //~1A20I~
			AG.aMenu.stopApp();                                    //~1A20I~
        }                                                          //~1A20I~
        else                                                       //~1A6sI~
        if (o.equals(AG.resource.getString(R.string.WiFiNFCButton)))//~1A6sI~
        {                                                          //~1A6sI~
//        if (AG.swNFCBT)                                             //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ad3R~
    		selectNFCHandover();                                   //~1Ab1I~//~1Ab6R~//~1Ab7R~
//        else                                                     //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ad3R~
//  		prepareNFC();                                          //~1A6sI~//~1Ad3R~
        }                                                          //~1A6sI~
        else                                                       //~1Ad3I~
        if (o.equals(AG.resource.getString(R.string.BTNFCButton))) //~1Ad3I~
        {                                                          //~1Ad3I~
    		selectNFCHandoverBT();                                 //~1Ad3I~
        }                                                          //~1Ad3I~
        else                                                       //+1Ah0I~
        if (o.equals(AG.resource.getString(R.string.VsBonanza)))   //+1Ah0I~
        {                                                          //+1Ah0I~
            boardType=BT_ROBOT; //callback from AMenu              //+1Ah0I~
            startRobotGame();                                      //+1Ah0I~
        }                                                          //+1Ah0I~
		else super.doAction(o);
		if (Dump.Y)Dump.println("MainFrame doAction exit o="+o+",boardType="+boardType);//~1AbdI~
  	}
  	public void itemAction (String o, boolean flag)
  	{	if (Global.resourceString("Public").equals(o))
  		{	Global.setParameter("publicserver",flag);
  		}
  		else if (Global.resourceString("Timer_in_Title").equals(o))
  		{	Global.setParameter("timerintitle",flag);
  		}
  		else if (Global.resourceString("Beep_only").equals(o))
  		{	Global.setParameter("beep",flag);
  		}
  		else if (Global.resourceString("Timeout_warning").equals(o))
  		{	Global.setParameter("warning",flag);
  		}
  	}
    //***********************************************************************//~@@@@I~
    //*from Frame                                                  //~@@@@I~
    //***********************************************************************//~@@@@I~
    private void restoreFrame()                                    //~@@@@I~
    {                                                              //~@@@@I~
    }                                                              //~@@@@I~
    //***********************************************************************//~1A11R~
    //*play                                                        //~1A11R~
    //***********************************************************************//~1A11R~
    private void playMenu()                                        //~1A11R~
    {                                                              //~1A11R~
    	AG.aMenu.playMenu(this);                                   //~1A11R~
    }                                                              //~1A11R~
    //***********************************************************************//~@@@2I~
    private void startLocalGame()                                  //~@@@2I~
    {                                                              //~@@@2I~
    	new LocalGameQuestion(this);                               //~@@@2I~
    }                                                              //~@@@2I~
    //***********************************************************************//~1A10I~
    private void openStudyBoard()                                  //~1A10I~
    {                                                              //~1A10I~
      if (false)                                                   //~1A32R~
      	new FreeBoardQuestion(this);                               //~1A10I~//~1A32R~
      else  //FreeBoardQuestion at FreaBoard start                 //~1A32R~
      	new FreeFrame(this,boardType);                             //~1A32R~
    }                                                              //~1A10I~
    //***********************************************************************//~1A2dI~
    private void openReplayBoard()                                 //~1A2dI~
    {                                                              //~1A2dI~
    	new ReplayFrame(this);                                     //~1A2dR~
    }                                                              //~1A2dI~
    //***********************************************************************//~@@@2I~
    private void startRemoteGame()                                 //~@@@2R~
    {                                                              //~@@@2I~
        if ((AG.RemoteStatus & AG.RS_IP)!=0)                          //~v101I~
        {                                                          //~v101I~
        	new Message(this,R.string.ErrNowIPConnected);          //~v101I~
        	return;                                                //~v101I~
        }                                                          //~v101I~
		new BluetoothConnection(this);	//open dialog              //~@@@2R~
    }                                                              //~@@@2I~
    //***********************************************************************//~v101I~
    private void startRemoteIP()                                   //~v101I~
    {                                                              //~v101I~
        if ((AG.RemoteStatus & AG.RS_BT)!=0)                          //~v101I~
        {                                                          //~v101I~
        	new Message(this,R.string.ErrNowBTConnected);          //~v101I~
        	return;                                                //~v101I~
        }                                                          //~v101I~
    	if (isAliveOtherSession(AG.AST_IP,false/*dupok*/))         //~1A8gI~
            return;                                                //~1A8gI~
//  	isAliveOtherSession(AG.AST_IP,true/*duperr*/);	//issue toast if dup IP//~1A8gI~//~1AbdR~
		new IPConnection(this);	//open dialog                      //~v101I~
    }                                                              //~v101I~
    //***********************************************************************//~1Ab0I~
    private void startRemoteIPDirect()                             //~1Ab0I~
    {                                                              //~1Ab0I~
        if ((AG.RemoteStatus & AG.RS_BT)!=0)                       //~1Ab0I~
        {                                                          //~1Ab0I~
        	new Message(this,R.string.ErrNowBTConnected);          //~1Ab0I~
        	return;                                                //~1Ab0I~
        }                                                          //~1Ab0I~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1A8gI~
            return;                                                //~1A8gI~
		new wifidirect.IPConnection();	//open dialog              //~1Ab0I~
    }                                                              //~1Ab0I~
    //***********************************************************************//+1Ah0I~
    private void startRobotGame()                                  //+1Ah0I~
    {                                                              //+1Ah0I~
    	new GtpGameQuestion(this);                                 //+1Ah0I~
    }                                                              //+1Ah0I~
//************************************************************     //~1A24I~
    @Override //GoFrame                                            //~1A24I~
    public void fileDialogLoaded(CloseDialog Pgq,Notes Pnotes) //GameQuestion request Reload File//~1A24I~
    {                                                              //~1A24I~
        if (boardType==BT_LOCAL)                                   //~1A24I~
        {                                                          //~1A24I~
        	new LocalFrame((LocalGameQuestion)Pgq,Pnotes);                            //~1A24I~
        }                                                          //~1A24I~
    }                                                              //~1A24I~
    //***********************************************************************//~1A6sI~
    private void prepareNFC()                                      //~1A6sI~
    {                                                              //~1A6sI~
        if ((AG.RemoteStatus & AG.RS_BT)!=0)                       //~1A6sI~
        {                                                          //~1A6sI~
        	new Message(this,R.string.ErrNowBTConnected);          //~1A6sI~
        	return;                                                //~1A6sI~
        }                                                          //~1A6sI~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1A8gI~
            return;                                                //~1A8gI~
    	DialogNFC.showDialog();                                    //~1A6sI~
    }                                                              //~1A6sI~
//*****************************************************************************//~1A8gI~
//*sessiontype:1:LAN,2:WifiDirect,3:BT                             //~1A8gI~
//*****************************************************************************//~1A8gI~
    public static boolean isAliveOtherSession(int Psessiontype,boolean Pduperr)//~1A8gI~
    {                                                              //~1A8gI~
    	int active=AG.activeSessionType;                           //~1A8gI~
        if (active!=0)                                             //~1A8gI~
            if (active!=Psessiontype||Pduperr)                     //~1A8gI~
            {                                                      //~1A8gI~
//              String msg=AG.resource.getString(R.string.ErrOtherActiveSession);//~1A8gI~
//              new com.Ajagoc.gtp.MessageDialogs().showError(null/*frame*/,msg,""/*optionalmsg*/,false/*critical*/);//~1A8gI~
		        String session="";                                 //~1A91R~
                switch(active)                                     //~1A91R~
                {                                                  //~1A91R~
                case AG.AST_IP:                                    //~1A91R~
                    session=AG.resource.getString(R.string.RemoteIP);//~1A91R~
                	break;                                         //~1A91R~
                case AG.AST_WD:                                    //~1A91R~
                    session=AG.resource.getString(R.string.WiFiDirectButton)+"("+AG.resource.getString(R.string.WiFiNFCButton)+")";//~1A91R~
                	break;                                         //~1A91R~
                case AG.AST_BT:                                    //~1A91R~
                    session=AG.resource.getString(R.string.RemoteGame);//~1A91R~
                	break;                                         //~1A91R~
                }                                                  //~1A91R~
                String msg=AG.resource.getString(R.string.ErrOtherActiveSession,session);//~1A91R~
//              AView.showToast(msg);                              //~1A91R~//~1AbdR~
		    	int flag=Alert.BUTTON_POSITIVE;                    //~1AbdI~
				Alert.simpleAlertDialog(null/*callback*/,null/*title*/,msg,flag);//~1314I~//~@@@@R~//~1AbdI~
                return true;                                       //~1A8gI~
            }                                                      //~1A8gI~
                                                                   //~1A8gI~
        return false;                                              //~1A8gI~
  	}                                                              //~1A8gI~
//*****************************************************************************//~1Ab1I~//~1Ab6R~//~1Ab7R~
//*select WiFiDirect or Bluetooth for NFC handover                 //~1Ab1I~//~1Ab6R~//~1Ab7R~
//*****************************************************************************//~1Ab1I~//~1Ab6R~//~1Ab7R~
    private void selectNFCHandover()                               //~1Ab1I~//~1Ab6R~//~1Ab7R~
    {                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
//      DialogNFCSelect.showDialog(this);                          //~1Ab1R~//~1Ab6R~//~1Ab7R~//~1Ad3R~
    	prepareNFCAhsv();                                          //~1Ad3I~
    }                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
    private void selectNFCHandoverBT()                             //~1Ad3I~
    {                                                              //~1Ad3I~
    	prepareNFCBTAhsv();                                        //~1Ad3I~
    }                                                              //~1Ad3I~
    public void selectedNFCHandover(int PhandoverType)           //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    {                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
        if (PhandoverType==DialogNFCSelect.NFCTYPE_IP)                                           //~1Ab1I~//~1Ab6R~//~1Ab7R~
            prepareNFC();                                          //~1Ab1I~//~1Ab6R~//~1Ab7R~
        else                                                       //~1Ab1I~//~1Ab6R~//~1Ab7R~
            prepareNFCBT(PhandoverType);                                        //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    }                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
    //***********************************************************************//~1Ab1I~//~1Ab6R~//~1Ab7R~
    private void prepareNFCBT(int PhandoverType)                                    //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    {                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
        if ((AG.RemoteStatus & AG.RS_IP)!=0)                       //~1Ab1I~//~1Ab6R~//~1Ab7R~
        {                                                          //~1Ab1I~//~1Ab6R~//~1Ab7R~
            new Message(this,R.string.ErrNowIPConnected);          //~1Ab1I~//~1Ab6R~//~1Ab7R~
            return;                                                //~1Ab1I~//~1Ab6R~//~1Ab7R~
        }                                                          //~1Ab1I~//~1Ab6R~//~1Ab7R~
//      if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1Ab1I~//~1Ab6R~//~1Ab7R~
//          return;                                                //~1Ab1I~//~1Ab6R~//~1Ab7R~
        DialogNFCBT.showDialog(this,PhandoverType);                                  //~1Ab1I~//~1Ab6R~//~1Ab7R~//~1Ab8R~
    }                                                              //~1Ab1I~//~1Ab6R~//~1Ab7R~
    //***********************************************************************//~1Ad3I~
    private void prepareNFCAhsv()                                  //~1Ad3I~
    {                                                              //~1Ad3I~
        if ((AG.RemoteStatus & AG.RS_BT)!=0)                       //~1Ad3I~
        {                                                          //~1Ad3I~
            new Message(this,R.string.ErrNowBTConnected);          //~1Ad3I~
            return;                                                //~1Ad3I~
        }                                                          //~1Ad3I~
    	if (isAliveOtherSession(AG.AST_WD,false/*dupok*/))         //~1Ad3I~
            return;                                                //~1Ad3I~
        DialogNFCSelect.showDialogNFCWD(this);                     //~1Ad3I~
    }                                                              //~1Ad3I~
    //***********************************************************************//~1Ad3I~
    private void prepareNFCBTAhsv()                                //~1Ad3I~
    {                                                              //~1Ad3I~
        if ((AG.RemoteStatus & AG.RS_IP)!=0)                       //~1Ad3I~
        {                                                          //~1Ad3I~
            new Message(this,R.string.ErrNowIPConnected);          //~1Ad3I~
            return;                                                //~1Ad3I~
        }                                                          //~1Ad3I~
        DialogNFCSelect.showDialogNFCBT(this);                     //~1Ad3I~
    }                                                              //~1Ad3I~
}

