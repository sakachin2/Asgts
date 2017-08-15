//*CID://+1Ah0R~:                                   update#=  241; //~1Ah0R~
//****************************************************************************//~@@@1I~
//1Ah0 2014/12/07 dislay comment area for replyboard               //~1Ah0I~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//1A4f 2014/11/30 fail to restart partner game(Cast err PGF to RGF).//~1A4fI~
//                mainFrame:boardType is upredictable when requested game from partner//~1A4fI~
//1A35 2013/04/19 show mark of last moved from position            //~1A35I~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A2c 2013/03/27 display previous move description for reloaded game//~1A2cI~
//1A27 2013/03/25 File button --> Suspend button on Local/Remote board//~1A27I~
//1A23 2013/03/23 File Dialog on PartnerGoFrame                    //~1A23I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0g 2013/03/06 judge checkmate                                  //~1A0gI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~1A0eI~
//1A0c 2013/03/05 mach info in title                               //~1A0cI~
//1A08 2013/03/02 add sound "gameover"                             //~1A08I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//101f 2013/02/09 drawPiece NPE                                    //~v101I~
//*@@@1 20110430 add "resign"/"pass" to Finish_Game menu(Pass is duplicated to "Set")//~@@@1R~
//****************************************************************************//~@@@1I~
package jagoclient.board;

import android.view.View;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.Alert;                                            //~v101R~
import com.Asgts.AlertI;
import com.Asgts.R;                                                //~v101R~
import com.Asgts.awt.Menu;                                          //~@@@1R~//~v101R~
import com.Asgts.awt.Panel;                                         //~@@@1R~//~v101R~
import com.Asgts.awt.TextArea;                                    //~@@@1R~//~v101R~
import com.Asgts.awt.TextField;                                    //~v101R~
import com.Asgts.awt.WindowEvent;                                   //~@@@1R~//~v101R~
import com.Asgts.awt.ToggleButton;                                 //~v1A0I~
import com.Asgts.rene.util.sound.SoundList;

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.MainFrame;
//import jagoclient.gui.CheckboxMenuItemAction;
//import jagoclient.gui.HistoryTextField;                          //~@@@1R~
//import jagoclient.gui.MenuItemAction;
//import jagoclient.gui.MyLabel;                                   //~@@@1R~
//import jagoclient.gui.MyMenu;
import jagoclient.dialogs.Message;
import jagoclient.gui.ButtonAction;
//import jagoclient.gui.MyMenu;                                    //~@@@1R~
//import jagoclient.gui.MyPanel;                                   //~@@@1R~
import jagoclient.gui.MyTextArea;                                                           //~1116I~//~1117M~//~@@@1R~
//import jagoclient.gui.Panel3D;                                   //~@@@1R~
//import jagoclient.gui.SimplePanel;                               //~@@@1R~
//import java.awt.BorderLayout;                                    //~1416R~
//import java.awt.CardLayout;                                      //~1416R~
//import java.awt.CheckboxMenuItem;                                //~1416R~
//import java.awt.Component;                                       //~1416R~
//import java.awt.GridLayout;                                      //~1416R~
//import java.awt.Label;                                           //~1416R~
//import java.awt.Menu;                                            //~1416R~
//import java.awt.MenuBar;                                         //~1416R~
//import java.awt.Panel;                                           //~1416R~
//import java.awt.TextArea;                                        //~1416R~
//import java.awt.event.WindowEvent;                               //~1416R~
//import rene.gui.IconBar;                                         //~1221R~
import jagoclient.gui.MyLabel;
import jagoclient.partner.GameQuestion;
import jagoclient.sound.JagoSound;

//import com.Asgts.rene.gui.IconBar;                                //~1221I~//~@@@1R~//~v101R~

/**
A subclass of GoFrame, which has a different menu layout.
Moreover, it contains a method to add a comment from an
external source (a distributor).
*/

public class ConnectedGoFrame extends GoFrame
//{   protected boolean TimerInTitle,ExtraSendField,BigTimer;      //~@@@2R~
{                                                                  //~@@@2I~
//    protected HistoryTextField SendField;                        //~@@@1R~
//	private static final int WIN_MSG_TEXTSIZE=80;                  //~@@@2I~
	protected String MatchTitle;                                   //~@@@2I~
//    protected Label TL;                                          //~1A0cR~
	protected BigTimerLabel BL;
	protected Menu /*FileMenu,*/Options;                           //~1A2dR~
	protected Panel CommentPanel;
    public int Col;                                                    //~@@@2R~
//  protected int Bishop,Knight,GameOptions,Gameover,Gameover2;    //~@@@2I~//~v101R~
    protected int GameOptions,Handicap;                                     //~v101I~
    protected	TextField tfU,tfL;                                 //~@@@2I~
    protected	ButtonAction ButtonResign;                         //~@@@2I~
//  protected	ButtonAction ButtonFile;                           //~1A22I~//~1A27R~
    protected	ButtonAction ButtonSuspend;                        //~1A27I~
    protected	ToggleButton PromotionButton;                      //~v1A0I~
    protected boolean swLocalGame;
    protected boolean swGtpGame;                                   //~1Ah0I~
    public  CapturedList aCapturedList;//~@@@2I~
    public NotesTree notesTree;                                    //~1A2cM~
//    public Notes loadNotes; //set by FileDialog                    //~1A22I~//~1A23R~
//    protected TextArea AllComments;                              //~@@@1R~
//    CardLayout CommentPanelLayout;                               //~@@@1R~
//  protected CheckboxMenuItem KibitzWindow,Teaching,Silent;       //~@@@1R~
//  private static Image SimageGameover,SimageGameover2;           //~@@@@R~//~@@@2M~//~v101R~
	/**
	Will modify the menues of the GoFrame.
	"endgame" is used for for the menu entry to end a game
	(e.g. "remove groups").
	"count" is the string to count a game (e.g. "send done").
	<P>
	Optionally, the comment uses a card layout and a second
	text area to hold the Kibitz window.
	*/
//**************************************************************** //~@@@1I~
	public ConnectedGoFrame
//        (String s, int si, String endgame, String count,         //~@@@2R~
        (int Presid,String s, int si,                              //~@@@2R~
//  	boolean kibitzwindow, boolean canteach)                    //~@@@1R~
		int Pgameoptions,int col,int Phandicap)          //~@@@1R~ //~v1A0R~
	{	super(Presid,s);                                           //~@@@2R~
//        setLayout(new BorderLayout());                           //~@@@1R~
        if (Dump.Y) Dump.println("@@@@ConnectedGoFrame@@@@");      //~@@@1I~
        GameOptions=Pgameoptions;                                  //~@@@1R~
        Handicap=Phandicap;                                        //~v1A0I~
        Col=col;                                                   //~@@@1I~
//        TimerInTitle=Global.getParameter("timerintitle",true);   //~@@@2R~
//        ExtraSendField=Global.getParameter("extrasendfield",true);//~@@@2R~
//        BigTimer=Global.getParameter("bigtimer",true);           //~@@@2R~
		// Menu
        L=new MyLabel(this,R.id.GameInfo);                         //~@@@2I~
//        Lm=new MyLabel("--");                                      //~1118R~//~@@@1R~
//        Lm=new MyLabel(R.id.SetStone);                             //~@@@2I~//~@@@1I~//~@@@2R~
        Lm=new MyLabel(this,R.id.SetStone);                        //~@@@2I~
//        Comment=new MyTextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//~@@@1R~//~@@@2R~
//        Comment=new MyTextArea("",R.id.Comment,0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//~@@@2R~
        Comment=new MyTextArea(this,"",R.id.Comment,0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//~@@@2I~
        Comment.setFont(Global.SansSerif);                       //~@@@1R~//~@@@2R~
                                                                   //~1A4fI~
        if (((MainFrame)(AG.mainframe)).partnerGameRestartRequestedBoardType!=0)//~1A4fI~
          B=new ConnectedBoard(si,this);                           //~1A4fI~
        else                                                       //~1A4fI~
        if (((MainFrame)(AG.mainframe)).boardType==jagoclient.MainFrame.BT_REPLAY)                     //~1A2dI~
          B=(ConnectedBoard)(new ReplayBoard(si,this));            //~1A2dI~
        else                                                       //~1A2dI~
        if (((MainFrame)(AG.mainframe)).boardType==jagoclient.MainFrame.BT_REPLAY_CLIPBOARD)//~1A4sI~
          B=(ConnectedBoard)(new ReplayBoard(si,this));            //~1A4sI~
        else                                                       //~1A4sI~
        if ((Pgameoptions & GameQuestion.GAMEOPT_FREEBOARD)!=0)                  //~1A10R~
          B=(ConnectedBoard)(new FreeBoard(si,this));              //~1A10R~
        else                                                       //~1A10R~
          B=new ConnectedBoard(si,this);                           //~@@@1R~
//        B.putInitialPiece(Col,Bishop,Knight,Gameover,Gameover2,GameOptions);//~@@@1R~//~v101R~
          B.putInitialPiece(Col,GameOptions,Handicap);                      //~v101I~//~v1A0R~
        Comment.setBackground(Global.gray);                      //~@@@1R~//~@@@2R~
		// Directory for FileDialog
//        TL=new MyLabel(this,R.id.TimerLabel);                    //~1A0cR~
        BL=new BigTimerLabel(this);                                //~@@@2I~
        AG.setBWSign(L.textView);                                  //~v1A0M~
        aCapturedList=new CapturedList(this,si,Col);              //~v1A0I~
        boolean swSmall=AG.portrait;	//se small button if portrait and small pixel device//~@@@2I~
//        ButtonResign=ButtonAction.init(this,R.string.Resign,R.id.Resign,swSmall/*smallbutton*/);       //~@@@1R~//~@@@2R~
//        ButtonAction.init(this,0,R.id.Options,swSmall);          //~@@@2R~
//        ButtonAction.init(this,0,R.id.Help,swSmall);           //~@@@1R~//~@@@2R~
//      ButtonFile=new ButtonAction(this,0,R.id.File,swSmall/*smallbutton*/);//~1A22I~//~1A27R~
        ButtonSuspend=new ButtonAction(this,0,R.id.SuspendGame,swSmall/*smallbutton*/);//~1A27I~
        ButtonResign=new ButtonAction(this,R.string.Resign,R.id.Resign,swSmall/*smallbutton*/);//~@@@2I~
        new ButtonAction(this,0,R.id.Options,swSmall);        //~@@@2I~
        new ButtonAction(this,0,R.id.Help,swSmall);           //~@@@2I~
        PromotionButton=new ToggleButton(this,R.id.Promotion);     //~v1A0R~
 //       validate();                                                //~@@@1R~
		Show=true;
		B.addKeyListener(this);
	    notesTree=new NotesTree(Col);	//default size             //~1A2cR~
	}
    //*******************************************************************//~1A2cI~
    @Override //GF                                                 //~1A2cI~
    public void appendComment (String s)                           //~1A2cI~
    {   Comment.append(s);                                         //~1A2cI~
      if (notesTree!=null)                                               //~1A2cI~
        notesTree.setMsg(B.moveNumber,s);                          //~1A2cR~
    }                                                              //~1A2cI~
    //*******************************************************************//~1A2cI~
    //** from Action Move at restore                               //~1A2cI~
    //*******************************************************************//~1A2cI~
    public void appendComment (String s,boolean Psettree)          //~1A2cI~
    {   Comment.append(s);                                         //~1A2cI~
    	if (Psettree)                                              //~1A2cI~
			if (notesTree!=null)                                         //~1A2cI~
        		notesTree.setMsg(B.moveNumber,s);                  //~1A2cI~
    }                                                              //~1A2cI~
    //*******************************************************************//~1A2cI~
	
//    public void addSendForward(IconBar I)                        //~@@@1R~
//    {                                                            //~@@@1R~
//    }                                                            //~@@@1R~
	
//    public void iconPressed (String s)                           //~@@@1R~
//    {   if (s.equals("send")) doAction(Global.resourceString("Send"));//~@@@1R~
//        else super.iconPressed(s);                               //~@@@1R~
//    }                                                            //~@@@1R~
	
//    public void addComment (String s)                            //~@@@1R~
//    // add a comment to the board (called from external sources) //~@@@1R~
//    {   B.addcomment(s);                                         //~@@@1R~
//        if (AllComments!=null) AllComments.append(s+"\n");       //~@@@1R~
//    }                                                            //~@@@1R~

//    public void addtoallcomments (String s)                      //~@@@1R~
//    // add something to the allcomments window                   //~@@@1R~
//    {   if (AllComments!=null) AllComments.append(s+"\n");       //~@@@1R~
//    }                                                            //~@@@1R~

	public void doAction (String o)
//  {	if (o.equals(Global.resourceString("Clear_Kibitz_Window")))//~@@@1R~
	{                                                              //~@@@1I~
//        if (o.equals(Global.resourceString("Clear_Kibitz_Window")))//~@@@1I~
//        {   AllComments.setText("");                             //~@@@1R~
//        }                                                        //~@@@1R~
        if (o.equals(AG.resource.getString(R.string.Options)))   //~@@@@I~//~@@@2I~
        {                                                          //~@@@2I~
            new GoFrameOptions(this);                              //~@@@2R~
        }                                                          //~@@@2I~
		else super.doAction(o);
	}

	public void itemAction (String o, boolean flag)
//  {	if (Global.resourceString("Kibitz_Window").equals(o))      //~@@@1R~
	{                                                              //~@@@1I~
//        if (Global.resourceString("Kibitz_Window").equals(o))    //~@@@1I~
//        {   if (KibitzWindow.getState())                         //~@@@1R~
//                CommentPanelLayout.show(CommentPanel,"AllComments");//~@@@1R~
//            else                                                 //~@@@1R~
//                CommentPanelLayout.show(CommentPanel,"Comment"); //~@@@1R~
//            Global.setParameter("kibitzwindow",KibitzWindow.getState());//~@@@1R~
//        }                                                        //~@@@1R~
//        else if (Global.resourceString("Silent").equals(o))      //~@@@1R~
//        {   if (flag) Global.Silent++;                           //~@@@1R~
//            else Global.Silent--;                                //~@@@1R~
//            Global.setParameter("boardsilent",flag);             //~@@@1R~
//        }                                                        //~@@@1R~
                                              //~@@@1I~
		/*else*/ super.itemAction(o,flag);
	}
	
	public void windowOpened (WindowEvent e)
//  {	if (ExtraSendField) SendField.requestFocus();              //~@@@1R~
    {                                                              //~@@@1I~
//  	if (ExtraSendField) SendField.requestFocus();              //~@@@1I~
	}
	
	public void activate ()
	{
	}
	
	public boolean wantsmove ()
	{	return false;
	}
	
	public void doclose ()
	{	Global.Silent--;
		super.doclose();
	}
//********************************************************         //~@@@@I~//~@@@2M~
//********************************************************         //~@@@@I~//~@@@2M~
    @Override //GoFrame                                            //~@@@@I~//~@@@2M~
    public void gameover(int Prequest,int Pcolor/*winner*/)                  //~@@@@I~//~@@@2M~//~v1A0R~
    {                                                              //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
    	switch(Prequest)                                           //~@@@2M~
    	{//~@@@@I~                                                 //~@@@2M~
        case 0: //chk                                              //~@@@@I~//~@@@2R~
//            if (capturedUL[0]>=Gameover)                          //~@@@@I~//~@@@2R~//~v101R~
//                gameover(2,-Col);                                  //~@@@@R~//~@@@2M~//~v101R~
//            else                                                   //~@@@@I~//~@@@2M~//~v101R~
//            if (capturedUL[1]>=Gameover)                          //~@@@@I~//~@@@2R~//~v101R~
//                gameover(2,Col);                                   //~@@@@R~//~@@@2M~//~v101R~
            break;                                                 //~@@@@I~//~@@@2M~
        case 1:		//captured king                                //~1A0eR~
	    	gameoverMessage(Pcolor,1);                             //~@@@@I~//~@@@2M~
            break;                                                 //~@@@@I~//~@@@2M~
        case 2:		//checkmate                                    //~1A0eR~
	    	gameoverMessage(Pcolor,2);                             //~@@@@I~//~@@@2M~
            break;                                                 //~@@@@I~//~@@@2M~
        }                                                          //~@@@@I~//~@@@2M~
    }                                                              //~@@@@I~//~@@@2M~
//********************************************************         //~@@@@I~//~@@@2M~
    public void gameoverMessage(int Pcolor/*winner*/,int Ptype)                  //~@@@@I~//~@@@2M~//~1A08R~
    {                                                              //~@@@@I~//~@@@2M~
    	int id;                                                    //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("CGF.gameoverMessage winner color="+Pcolor+",type="+Ptype);//+1Ah0I~
    	if (Pcolor>0)                                              //~@@@@I~//~@@@2M~
        	id=R.string.WinnerBlack;                               //~@@@@I~//~@@@2M~
        else                                                       //~@@@@I~//~@@@2M~
        	id=R.string.WinnerWhite;                              //~@@@@I~//~@@@2R~
        String winner=AG.resource.getString(id);                   //~@@@@I~//~@@@2M~
    	switch(Ptype)                                              //~@@@@I~//~@@@2R~
        {                                                          //~@@@2I~
        case 1:                                                    //~@@@2I~
        	id=R.string.GameoverKing;                            //~@@@@I~//~@@@2M~//~v1A0R~
            ((ConnectedBoard)B).gameoverReason|=ConnectedBoard.GOR_CAPTUREDKING;//~v1A0R~//~1A0gR~
            break;                                                 //~@@@2I~
        case 2:                                                    //~@@@2R~//~v1A0R~//~1A0eR~
            id=R.string.GameoverByCheckmate;                        //~@@@@I~//~@@@2M~//~v1A0R~//~1A0eR~//~1A0gR~
            ((ConnectedBoard)B).gameoverReason|=ConnectedBoard.GOR_CHECKMATE;//~1A0gI~
            break;                                                 //~@@@2I~//~v1A0R~//~1A0eR~
        case 3:                                                    //~@@@2I~
        	id=R.string.GameoverByTimeout;                         //~@@@2I~
            ((ConnectedBoard)B).gameoverReason|=ConnectedBoard.GOR_TIMEOUT;//~v1A0R~
            break;                                                 //~@@@2I~
        case 4:                                                    //~@@@2I~
        	id=R.string.GameoverByResign;                          //~@@@2I~
            ((ConnectedBoard)B).gameoverReason|=ConnectedBoard.GOR_RESIGN;//~v1A0R~
            break;                                                 //~@@@2I~
        default:                                                   //~@@@2I~//~v1A0R~
//            return;                                                //~@@@2I~//~v1A0R~
        	id=Ptype;                                              //~v1A0I~
        }                                                          //~@@@2I~
		if (notesTree!=null)                                       //~1A2dI~
        	notesTree.setGameover(Pcolor,id);                      //~1A2dI~
        String msg=winner+" : "+AG.resource.getString(id);                     //~@@@@I~//~@@@2R~//~v1A0R~
        ((ConnectedBoard) B).infoComment(msg);                                        //~@@@2I~
//        new Message(this,msg,WIN_MSG_TEXTSIZE);                  //~@@@2R~
		Alert.simpleAlertDialog(null,MatchTitle,msg,Alert.BUTTON_POSITIVE);//~@@@2R~
    	gameoverSound(Pcolor);                                     //~1A08I~
//      ButtonResign.setAction(R.string.Close);                    //~@@@2M~//~1A27R~
    	changeButton();                                            //~1A27I~
		gameovered();                                              //~@@@2R~//~v1A0R~
    }                                                              //~@@@@I~//~@@@2M~
//********************************************************         //~1A27I~
    private void changeButton()                                    //~1A27I~
    {                                                              //~1A27I~
        ButtonResign.setAction(R.string.Close);                    //~1A27I~
        ButtonSuspend.setAction(R.string.ButtonSaveGame);          //~1A27I~
    }                                                              //~1A27I~
//********************************************************         //~v1A0I~
//** msg from partner                                              //~v1A0I~
//********************************************************         //~v1A0I~
    public void gameoverNotified(int Pcolor/*winner*/,int Preason)           //~v1A0I~
    {                                                              //~v1A0I~
    	int id;                                                    //~v1A0I~
    //**********************                                       //~v1A0I~
    	if (Pcolor>0)                                              //~v1A0I~
        	id=R.string.WinnerBlack;                               //~v1A0I~
        else                                                       //~v1A0I~
        	id=R.string.WinnerWhite;                               //~v1A0I~
        String winner=AG.resource.getString(id);                   //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_CAPTUREDKING)!=0)      //~v1A0R~//~1A0gR~
        	id=R.string.GameoverKing;                              //~v1A0I~
        else                                                       //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_TIMEOUT)!=0)        //~v1A0R~
        	id=R.string.GameoverByTimeout;                         //~v1A0I~
        else                                                       //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_NOPATH)!=0)         //~v1A0R~
	        id=R.string.ErrNoPathPieceGameover;                    //~v1A0I~
        else                                                       //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_LEAVECHECK)!=0)     //~v1A0R~
      		id=R.string.ErrLeaveCheckGameover;                     //~v1A0I~
        else                                                       //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_2PAWN)!=0)          //~v1A0R~
	        id=R.string.Err2PawnGameover;                          //~v1A0I~
        else                                                       //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_UNMOVABLEDROP)!=0)  //~v1A0R~
	        id=R.string.ErrUnmovableDropGameover;                  //~v1A0I~
        else                                                       //~v1A0I~
    	if ((Preason & ConnectedBoard.GOR_DROPPAWNCHECKMATE)!=0)//~v1A0R~
	        id=R.string.ErrDropPawnCheckmateGameover;              //~v1A0I~
        else                                                       //~v1A0I~
        	return;                                                //~v1A0I~
		if (notesTree!=null)                                       //~1A2dI~
        	notesTree.setGameover(Pcolor,id);                      //~1A2dI~
        String msg=winner+" : "+AG.resource.getString(id);         //~v1A0I~
        ((ConnectedBoard) B).infoComment(msg);                     //~v1A0I~
		Alert.simpleAlertDialog(null,MatchTitle,msg,Alert.BUTTON_POSITIVE);//~v1A0I~
    	gameoverSound(Pcolor);                                     //~1A08I~
//      ButtonResign.setAction(R.string.Close);                    //~v1A0I~//~1A27R~
    	changeButton();                                            //~1A27I~
    }                                                              //~v1A0I~
//*****************************************************************************//~1A0eI~
//*overridden by PGF & LGF and called by super                                          //~1A0eI~//~1A0gR~
//*****************************************************************************//~1A0eI~
    public void errCheckmate(int Pcolor/*winner*/,boolean Pagree)                 //~1A0eI~//~1A0gR~
    {                                                              //~1A0eI~
    	if (Pagree)                                                //~1A0gI~
			gameover(2,Pcolor);                                        //~1A0eI~//~1A0gR~
        else                                                       //~1A0gI~
        {                                                          //~1A0gI~
            ((ConnectedBoard)B).gameoverReason&=~ConnectedBoard.GOR_CHECKMATE;//~1A0gI~
        	setLabel(R.string.CheckmateCancel,false/*no append*/);   //~1A0fI~//~1A0gI~
	    	AView.showToast(R.string.CheckmateCancel);             //~1A0gI~
        }                                                          //~1A0gI~
    }                                                              //~1A0eI~
//*****************************************************************************//~1A0gR~
    public void checkmateQuestion(AlertI Pcallback,int Pcolor/*winner*/)//~1A0gR~
    {                                                              //~1A0gR~
    	String title=AG.resource.getString(R.string.Title_CheckmateQuestion);//~1A0gR~
        String winner=Pcolor>0?AG.BlackName:AG.WhiteName;          //~1A0gR~
    	String msg=AG.resource.getString(R.string.CheckmateQuestion,winner);//~1A0gR~
		Alert alert=Alert.simpleAlertDialog(Pcallback,title,msg,Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE);//~1A0gR~
        alert.setSimpleData(Pcolor);	//callback param           //~1A0gI~
    }                                                              //~1A0gR~
//*****************************************************************************//~@@@2R~
//*from com.partnerFrame when PartnerThread got exception(Connection failed etc)//~@@@2I~
//******************************************************************************//~@@@2I~
    public void gameInterrupted(int Pmsgid)                        //~@@@2I~
    {                                                              //~@@@2I~
        String type=AG.resource.getString(Pmsgid);                     //~@@@2I~
		new Message(this,type);                                    //~@@@2I~
//      ButtonResign.setAction(R.string.Close);                    //~@@@2I~//~1A27R~
    	changeButton();                                            //~1A27I~
		gameovered();                                              //~@@@2I~//~v1A0R~
    }                                                              //~@@@2I~
//***************************************************************************//~v1A0R~
    @Override //GoFrame                                            //~v1A0I~
//  public void updateCapturedList(int Pcolor/*captured piece color*/,int Ppiece)//~v1A0I~//~1A0eR~
    public void updateCapturedList(int Pcolor/*captured piece color*/,int Ppiece,int Pcount)//~1A0eI~
    {                                                              //~v1A0I~
//  	aCapturedList.updateCapturedList(Pcolor,Ppiece);       //~v1A0I~//~1A0eR~
    	aCapturedList.updateCapturedList(Pcolor,Ppiece,Pcount);    //~1A0eI~
    }                                                              //~v1A0I~
//***************************************************************************//~v1A0I~
	public void drawInitialCaptured()                              //~v1A0I~
    {                                                              //~v1A0I~
    	aCapturedList.drawInitialCaptured();                       //~v1A0I~
    }                                                              //~v1A0I~
//***************************************************************************//~v1A0I~
	public void displayCurrentColor(int Pcolor/*moved*/)           //~v1A0R~
    {                                                              //~v1A0I~
    	aCapturedList.displayCurrentColor(-Pcolor/*next*/);        //~v1A0R~
    }                                                              //~v1A0I~
//***************************************************************************//~v1A0I~
    protected void pieceMoved(int Pcolor)                          //~v1A0R~
    {                                                              //~v1A0I~
//        displayCurrentColor(Pcolor);                             //~v1A0R~
    }                                                              //~v1A0I~
//***************************************************************************//~v1A0I~
    protected void pieceSelected(int Pi,int Pj,int Pcolor)	//LocalGoFrame will override//~v1A0R~
	{                                                              //~v1A0I~
    	if (Dump.Y) Dump.println("CGF:pieceSeleced i="+Pi+",j="+Pj+",color="+Pcolor);//~1A35R~
		aCapturedList.reset(Pcolor);// reset capturedlist selection//~v1A0R~
//	    ((ConnectedBoard)B).resetMovedFrom();                      //~1A35R~
    }                                                              //~v1A0I~
//***************************************************************************//~v1A0I~
    protected void gameoverSound(int Pcolor/*winner*/)                     //~1A08I~
    {                                                              //~1A08I~
    	if ((AG.Options & AG.OPTIONS_NO_GAMEOVER_SOUND)!=0)           //~1A08I~
        	return;                                                //~1A08I~
        String fnm;
    	if (Pcolor==Col)                                           //~1A08I~
        	fnm=SoundList.GAMEOVERWIN;                             //~1A08R~
        else                                                       //~1A08I~
        	fnm=SoundList.GAMEOVERLOSE;                            //~1A08R~
    	JagoSound.play(fnm,false/*not change to beep when beeponly option is on*/);//~1A08I~
    }                                                              //~1A08I~
//***************************************************************************//~1A08I~
//  protected boolean moveset(int Pi,int Pj,int Ppiece,int Pcaptured){return true;}//~@@@2I~//~v1A0R~
//  protected boolean moveset(int Pi,int Pj,int Ppiece,boolean Pdropped){return true;}//~1Ah0R~
    protected boolean moveset(int Pi,int Pj,int Ppiece,boolean Pdropped,int Poldi,int Poldj)//~1Ah0R~
	{                                                              //~1Ah0I~
    	if (Dump.Y)Dump.println("ConnectedGoFrame:moveset dummy"); //~1Ah0I~
		return true;                                               //~1Ah0I~
	}                                                              //~1Ah0I~
    protected void errPass(){}                                     //~@@@2I~
	protected void gameovered(){}                                  //~@@@2R~
	public void errCheckmate(int Pcolor){}                         //~1A0gR~
//************************************************                 //~1A4vM~
	protected void scrollComment(int Ppos,int Pmaxpos)             //~1A4vR~
    {   
        float frh,fpaddingtop;                                     //~1A4vI~
	    int scrolly;                                               //~1A4vI~
		int bottom=Comment.textView.getHeight();                   //~1A4vR~
    	int height=Comment.scrollView.getHeight();                 //~1A4vR~
        if (Ppos>=Pmaxpos)                                         //~1A4vI~
	        scrolly=bottom-height; //to center                     //~1A4vI~
        else                                                       //~1A4vI~
        if (Ppos==0)                                               //~1A4vI~
	        scrolly=0;                                             //~1A4vI~
        else                                                       //~1A4vI~
        if (bottom<=height)                                        //~1A4vI~
	        scrolly=0;                                             //~1A4vI~
        else                                                       //~1A4vI~
        {                                                          //~1A4vI~
        	frh=(float)bottom/Pmaxpos;	//row height               //~1A4vR~
            fpaddingtop=(height-frh)/2;                            //~1A4vR~
            if (fpaddingtop<0)   //view heigh is smaller than line height//~1A4vR~
            	scrolly=(int)(frh*Ppos);                           //~1A4vI~
            else                                                   //~1A4vI~
            	scrolly=(int)(frh*Ppos-fpaddingtop);                //~1A4vR~
        }                                                          //~1A4vI~
        if (scrolly<0)                                          //~1A4vI~
        	scrolly=0;                                             //~1A4vI~
        if (Dump.Y) Dump.println("scrollComment old="+Comment.scrollView.getScrollY()+",new="+scrolly);//~1A4vI~
    	Comment.scrollTo(scrolly);                                 //~1A4vR~
    }                                                              //~1A4vM~
//    //****************************************************************//~1Ah0R~
//    public void notifyMoved(ActionMove Pam)                      //~1Ah0R~
//    {                                                            //~1Ah0R~
//    //GtpGoFrame override this                                   //~1Ah0R~
//        if (Dump.Y) Dump.println("ConnectedGoFrame:notifyMoved nop");//~1Ah0R~
//    }                                                            //~1Ah0R~
}
