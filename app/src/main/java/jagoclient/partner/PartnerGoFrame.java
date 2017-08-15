//*CID://+1Ah0R~:                                   update#=  167; //~1Ah0R~
//****************************************************************************//~@@@1I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//1Ab3 2015/05/04 save file folder may differ for partnermatch     //~1Ab3I~
//1A75 2015/02/23 (Ahsv:1A6B) is not proper to Asgts (WD/NFC on title)//~1A74I~
//1A74 2015/02/23 toast for delayed response(Game not started,already end)//~1A74I~
//1A6B 2015/02/21 IP game title;identify IP and WifiDirect(WD)     //~1A6BI~
//1A29 2013/03/25 confirmation also on suspend requester when initiate suspwend//~1A29I~
//1A28 2013/03/25 when received adjourn,not close boad for availability to save game//~1A28I~
//1A27 2013/03/25 File button --> Suspend button on Local/Remote board//~1A27I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A23 2013/03/23 File Dialog on PartnerGoFrame                    //~1A23I~
//1A1j 2013/03/19 change Help file encoding to utf8 (path change drop jagoclient from jagoclient/helptexts)//~1A1jI~
//1A0g 2013/03/06 judge checkmate                                  //~1A0gI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~1A0eI~
//1A0c 2013/03/05 mach info in title                               //~1A0cI~
//1A06 2013/03/02 remaining ExtraTime was accounted.               //~1A06I~
//1A04 2013/03/01 after timeout,setstone ignored by Block=true remains(new PGF under one PF)//~1A04I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//107b:121209 (OriginalBug)PartnerFrame did not stop GoTimer       //~v107I~
//1065:121124 PartnerConnection;FinishGame-->EndGame:send EndGame req and if responsed, allow RemoveGroup.//~v106I~//~1A23R~
//            Igs and GMP is FinishGame-->Remove groupe/prisoner   //~v106I~
//            change Menu Item Text for partner connection(send End game request)//~v106I~
//            Isue reply msg and notify "Remove Prisoner" avalable //~v106I~
//v106:1064:121124 beep also on partner connection(no beep even when beeponly for partnerconnection)//~v106I~
//*@@@1 20110430 change GoTimer interval 100-->1000ms              //~@@@1I~
//*@@@1 20110430 FunctionKey support                               //~@@@1I~
//*@@@1 20110430 add "resign" to FinishGame menu                   //~@@@1I~
//****************************************************************************//~@@@1I~
//****************************************************************************//~@@@1I~
package jagoclient.partner;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.AView;
import com.Asgts.Alert;
import com.Asgts.AlertI;
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.FileDialog;
import com.Asgts.awt.Frame;                                        //~@@@@R~
import com.Asgts.awt.KeyEvent;                                      //~v107R~//~@@@@R~
import com.Asgts.awt.KeyListener;                                   //~v107R~//~@@@@R~
import com.Asgts.gtp.GtpFrame;

import jagoclient.Dump;
import jagoclient.board.ConnectedBoard;
import jagoclient.board.Field;
import jagoclient.board.TimedGoFrame;
import jagoclient.dialogs.HelpDialog;


public class PartnerGoFrame extends TimedGoFrame                   //~@@@@I~
		implements KeyListener                                     //~@@@@I~
        , AlertI                                                   //~1A0gI~
{                                                                   //~@@@@R~
	PartnerFrame PF;
    private boolean saveAfterGameover;                             //~1A27I~
    public  boolean swFileSaved;                                   //~1A28I~
//*********************************************************************//+@@@@I~                                                             //~@@@@I~
    public PartnerGoFrame (PartnerFrame pf,                        //~@@@@I~
//  	int col, int si,int Pgameover,int Pgameover2, int tt,int et, int Pbishop, int Pknight, int Pgameoptions)//~@@@@R~//~v1A0R~
    	int col, int si,int tt,int et,int Pgameoptions,int Phandicap)            //~v1A0I~
    {                                                              //~@@@@I~
//  	super((Frame)pf,AG.frameId_ConnectedGoFrame,AG.resource.getString(R.string.Title_PartnerMatch),//~@@@@R~//~1A0cR~
    	super((Frame)pf,AG.frameId_ConnectedGoFrame,               //~1A0cI~
//  			AG.resource.getString(pf.BTConnection?R.string.Title_PartnerMatchBT:R.string.Title_PartnerMatchIP),//~1A0cI~//~1A75R~
    			AG.resource.getString(                             //~1A75R~
				    ( (pf.connectionType==IPConnection.NFC_SERVER||pf.connectionType==IPConnection.NFC_CLIENT)//~1A75R~
                      ? R.string.Title_PartnerMatchNFC             //~1A75R~
                      : ( (pf.connectionType==IPConnection.WD_SERVER||pf.connectionType==IPConnection.WD_CLIENT)//~1A75R~
                          ? R.string.Title_PartnerMatchWD          //~1A75R~
                          : ( (pf.BTConnection)                    //~1A75R~
                              ? R.string.Title_PartnerMatchBT      //~1A75R~
                              : R.string.Title_PartnerMatchIP      //~1A75R~
                            )                                      //~1A75R~
                        )                                          //~1A75R~
                    )                ), //set TimedGoFrame:frameTitle//~1A75R~
//                col,si,Pgameover,Pgameover2,tt,et,Pbishop,Pknight,Pgameoptions);//~@@@@I~//~v1A0R~
                col,si,tt,et,Pgameoptions,Phandicap);                        //~v1A0I~
        if (Dump.Y) Dump.println("@@@@PartnerGoFrame@@@@");        //~@@@@I~
		PF=pf;
        PF.initPF();                                               //~1A04I~
//  	Col=col; TotalTime=tt; ExtraTime=et; ExtraMoves=em;        //~@@@@R~
//        Col=col; TotalTime=tt; ExtraTime=et; ExtraMoves=AG.EXTRA_MOVE;//~@@@@R~
//        Col=col;                                                 //~@@@@R~
//        BlackTime=TotalTime; WhiteTime=TotalTime;                //~@@@@R~
//  	Handicap=ha;                                               //~@@@@R~
//        BlackRun=0; WhiteRun=0;                                  //~@@@@R~
//        Started=false; Ended=false;                              //~@@@@R~
//        if (Col==1) BlackName=Global.resourceString("You");      //~@@@@R~
//        else BlackName=Global.resourceString("Opponent");        //~@@@@R~
//        if (Col==-1) WhiteName=Global.resourceString("You");     //~@@@@R~
//        else WhiteName=Global.resourceString("Opponent");        //~@@@@R~
        if (Col==1)                                                //~@@@@I~
        {                                                          //~@@@@I~
            BlackName=AG.YourName;                                 //~@@@@R~
            WhiteName=PF.PartnerName;                              //~@@@@R~
        }                                                          //~@@@@I~
        else                                                       //~@@@@I~
        {                                                          //~@@@@I~
            WhiteName=AG.YourName;                                 //~@@@@R~
            BlackName=PF.PartnerName;                              //~@@@@R~
        }                                                          //~@@@@I~
//        ShowTarget=true; //cursor is drawn by Canvas             //~@@@@R~
		addKeyListener(this);                                      //~@@@1I~
		setVisible(true);
//      if (PF.connectionType==IPConnection.NFC_SERVER||PF.connectionType==IPConnection.NFC_CLIENT)//~1A6BI~//~1A75R~
//        setTitle("NFC"+": "+WhiteName+" vs "+BlackName);           //~1A6BI~//~1A75R~
//      else                                                         //~1A6BI~//~1A75R~
//      if (PF.connectionType==IPConnection.WD_SERVER||PF.connectionType==IPConnection.WD_CLIENT)//~1A6BI~//~1A75R~
//        setTitle("WD"+": "+WhiteName+" vs "+BlackName);            //~1A6BI~//~1A75R~
//      else                                                         //~1A6BI~//~1A75R~
		setTitle(((AG.RemoteStatus & AG.RS_IP)!=0?"IP":"BT")+": "+WhiteName+" vs "+BlackName);//~@@@@I~
		repaint();
	}
//*********************************************************************//~1Ah0I~
//*for GtpGoFrame                                                  //~1Ah0I~
//*********************************************************************//~1Ah0I~
    public PartnerGoFrame (GtpFrame gf,int Playout,int Ptitleid,       //~1Ah0I~
    	int col, int si,int tt,int et,int Pgameoptions,int Phandicap)//~1Ah0I~
    {                                                              //~1Ah0I~
    	super((Frame)gf,Playout,AG.resource.getString(Ptitleid),   //~1Ah0I~
                col,si,tt,et,Pgameoptions,Phandicap);              //~1Ah0I~
        if (Dump.Y) Dump.println("PartnerGoFrame constructor for GtpGoFrame");//~1Ah0I~
		addKeyListener(this);                                      //~1Ah0I~
		setVisible(true);                                          //~1Ah0I~
		repaint();                                                 //~1Ah0I~
	}                                                              //~1Ah0I~
	public void doAction (String o)
//  {	if (Global.resourceString("Send").equals(o) || (ExtraSendField && Global.resourceString("ExtraSendField").equals(o)))//~@@@@R~
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.Resign)))      //~@@@@R~
		{                                                          //~@@@1I~
            PF.resign();   //confirm by dialog                     //~@@@@I~
			return;                                                //~@@@1I~
		}                                                          //~@@@1I~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~@@@@I~
		{                                                          //~@@@@I~
//      	new HelpDialog(this,R.string.Help_GoFrame);                 //~@@@@R~//~1A06R~
//      	new HelpDialog(this,R.string.HelpTitle_GoFrame,"GoFrame");//~1A06R~//~1A1jR~
        	new HelpDialog(this,R.string.HelpTitle_GoFrame,"BoardFrame");//~1A1jI~
		}                                                          //~@@@@I~
//        else if (o.equals(AG.resource.getString(R.string.SuspendGame)))//~1A23I~//~1A27R~
//        {                                                          //~1A23I~//~1A27R~
//            sendSuspend();                                         //~1A23R~//~1A27R~
//        }                                                          //~1A23I~//~1A27R~
		else super.doAction(o);
	}

	public boolean blocked ()
	{	return false;
	}

	public boolean wantsmove ()
	{	return true;
	}

	@Override //ConnectedGoFrame                                   //~@@@@I~
//  public boolean moveset (int i, int j)                          //~@@@@R~
//  public boolean moveset (int i, int j,int Ppiece,int Pcaptured)               //~@@@@I~//~v1A0R~
//  public boolean moveset (int i, int j,int Ppiece,boolean Pdropped)//~1Ah0R~
    public boolean moveset (int i, int j,int Ppiece,boolean Pdropped,int Poldi,int Poldj)//~1Ah0I~
//  {   if (!Started || Ended) return false;                       //~@@@@R~//~1A74R~
    {                                                              //~1A74I~
        if (!Started || Ended)                                     //~1A74I~
        {                                                          //~1A74I~
        	if (!Started)                                          //~1A74I~
	        	AView.showToast(R.string.InfoGameNotStarted);           //~1A74I~
            else                                                   //~1A74I~
	        	AView.showToast(R.string.InfoGameAlreadyEnded);         //~1A74I~
        	return false;                                          //~1A74I~
        }                                                          //~1A74I~
        String color;                                              //~@@@@R~
        int enteredExtraTime=0;                                    //~1A06I~
        if (B.maincolor()!=Col) return false;                      //~@@@@R~
        if (B.maincolor()>0) color="b";                            //~@@@@R~
        else color="w";                                            //~@@@@R~
        if (Timer!=null && Timer.isAlive()) alarm();               //~@@@@R~
        int bm=BlackMoves,wm=WhiteMoves;                           //~@@@@R~
        if (Col>0)                                                 //~1A06R~
		{                                                          //~1A06I~
			if (BlackMoves>0) BlackMoves--;                        //~1A06I~
            enteredExtraTime=resetExtraTime(Col)?1:0;               //~1A06I~
		}                                                          //~1A06I~
        else                                                       //~1A06R~
		{                                                          //~1A06I~
			if (WhiteMoves>0) WhiteMoves--;                        //~1A06I~
            enteredExtraTime=resetExtraTime(Col)?1:0;               //~1A06I~
		}                                                          //~1A06I~
//        if (!PF.moveset(color,i,j,BlackTime-BlackRun,BlackMoves, //~@@@@R~
//        if (!PF.moveset(color,Ppiece,i,j,BlackTime-BlackRun,BlackMoves,//~@@@@I~//~v1A0R~
//      if (!PF.moveset(color,Ppiece,Pcaptured,i,j,BlackTime-BlackRun,BlackMoves,//~v1A0R~
        if (!PF.moveset(color,Ppiece,Pdropped?1:0,i,j,BlackTime-BlackRun,BlackMoves,//~v1A0I~
//          WhiteTime-WhiteRun,WhiteMoves))                        //~@@@@R~//~1A06R~
            WhiteTime-WhiteRun,WhiteMoves,enteredExtraTime))       //~1A06I~
        {   BlackMoves=bm; WhiteMoves=wm;                          //~@@@@R~
            if (Timer.isAlive()) alarm();                          //~@@@@R~
            return false;                                          //~@@@@R~
        }                                                          //~@@@@R~
        return true;                                               //~@@@@R~
    }                                                              //~@@@@R~
//************************************************************************//~@@@@I~
	@Override  //ConnectedGoFrame from ConnectedBoard              //~@@@@I~
	protected void errPass()                                       //~@@@@I~
    {                                                              //~@@@@I~
		movepass();     //to PF.pass(),Out @@pass                  //~@@@@I~
    }                                                              //~@@@@I~
//************************************************************************//~@@@@I~
	public void movepass ()
	{	if (!Started || Ended) return;
		if (B.maincolor()!=Col) return;
      if (Timer!=null)                                             //~@@@@I~
		if (Timer.isAlive()) alarm();
		if (Col>0) { if (BlackMoves>0) BlackMoves--; }
		else { if (WhiteMoves>0) WhiteMoves--; }		
		PF.pass(BlackTime-BlackRun,BlackMoves,
			WhiteTime-WhiteRun,WhiteMoves);
	}
//************************************************************************//~@@@@I~
//*@@pass,@@!pass process                                          //~@@@@R~
    public void dopass ()                                          //~@@@@R~
    {   B.setpass();                                               //~@@@@R~
    }                                                              //~@@@@R~
////************************************************************************//~@@@@I~//~v1A0R~
//    public void notifiedPass()  //notified pass from partner       //~@@@@R~//~v1A0R~
//    {                                                              //~@@@@I~//~v1A0R~
//        ((ConnectedBoard)B).notifiedPass(); //to ConnectedBoard                    //~@@@@R~//~v1A0R~
//    }                                                              //~@@@@I~//~v1A0R~
                                                             //~@@@@I~


	public void doclose ()
	{	setVisible(false); dispose();
    	if (Dump.Y) Dump.println("PGF doclose");                   //~1A29I~
		if (Timer!=null && Timer.isAlive()) Timer.stopit();        //~v107I~
		PF.toFront();
		PF.boardclosed(this);
		PF.PGF=null;
	}
//********************************************************         //~@@@@I~
//*from PartnerFrame:adjourn                                       //~@@@@I~
//********************************************************         //~@@@@I~
	public void acceptClosed()                                     //~@@@@I~
	{	setVisible(false); dispose();                              //~@@@@I~
		if (Timer!=null && Timer.isAlive()) Timer.stopit();        //~@@@@I~
		PF.toFront();                                              //~@@@@I~
		PF.PGF=null;                                               //~@@@@I~
	}                                                              //~@@@@I~


	int maincolor ()
	{	return B.maincolor();
	}
	protected void start ()                                        //~@@@@I~
    {                                                              //~@@@@I~
    	super.start();                                             //~@@@@I~
    }                                                              //~@@@@I~
	

	public void result (int b, int w)
	{	PF.out("@@result "+b+" "+w);
	}


	public void yourMove (boolean notinpos)
    {                                                              //~@@@@I~
//        JagoSound.play("stone","click",true);                    //~@@@@R~
    }                                                              //~@@@@I~
//*FunctionKey support                                             //~@@@1I~
	public void keyPressed (KeyEvent e) {}                         //~@@@1I~
	public void keyTyped (KeyEvent e) {}                           //~@@@1I~
	public void keyReleased (KeyEvent e)                           //~@@@1I~
	{                                                              //~@@@@I~
	}                                                              //~@@@1I~
//*****************************************************            //~@@@@I~
    protected void receiveSelected(int Pi,int Pj)                  //~@@@@I~
    {                                                              //~@@@@I~
    	int i=B.S-Pi-1; int j=B.S-Pj-1;                                //~@@@@I~
    	if (Dump.Y) Dump.println("receiveSelected my i="+i+",j="+j);//~@@@@I~
    	((ConnectedBoard)B).receiveSelected(i,j);                  //~@@@@R~
//        JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~@@@@I~//~v1A0R~
    }                                                              //~@@@@I~
//  public void black(int Pi,int Pj,boolean Preverse,int Ppiece)   //~@@@@R~//~1A0eR~
    public void black(int Pi,int Pj,boolean Preverse,int Ppiece,int Pdropped)//~1A0eI~
    {                                                              //~@@@@I~
    	int i,j;                                                   //~@@@@R~
    //*******************                                          //~@@@@I~
        if (Preverse)                                              //~@@@@I~
    	{	i=B.S-Pi-1;j=B.S-Pj-1;	}                              //~@@@@I~
        else                                                       //~@@@@I~
    	{	i=Pi;j=Pj;	}                                          //~@@@@I~
        int captured=B.P.piece(i,j);                               //~v1A0I~
    	if (Dump.Y) Dump.println("received black my i="+i+",j="+j+",captured="+captured+",piece="+Ppiece);//~@@@@I~//~v1A0R~//~1A0cR~
        if (captured!=0)                                           //~v1A0I~
        {                                                          //~v1A0I~
			captured=Field.nonPromoted(captured);                  //~v1A0R~
			((ConnectedBoard)B).partnerCapturePiece(i,j,-1/*captured piece color*/,captured);//~v1A0I~
        }                                                          //~v1A0I~
        if (Pdropped!=0)                                           //~1A0eI~
        {                                                          //~1A0eI~
			((ConnectedBoard)B).partnerDroppedPiece(i,j,1/*dropped piece color*/,Ppiece);//~1A0eR~
        }                                                          //~1A0eI~
//  	B.P.setPiece(i,j,Ppiece);                                  //~@@@@R~
    	B.P.setPiece(i,j,1/*color*/,Ppiece);                       //~@@@@I~
//      B.black(i,j);                                              //~@@@@R~
        B.black(i,j,Ppiece);                                       //~@@@@I~
    }                                                              //~@@@@I~
    public void white(int Pi,int Pj,boolean Preverse,int Ppiece,int Pdropped)   //~@@@@R~
    {                                                              //~@@@@I~
    	int i,j;                                                   //~@@@@I~
    //*******************                                          //~@@@@I~
        if (Preverse)                                              //~@@@@I~
    	{	i=B.S-Pi-1;j=B.S-Pj-1;	}                              //~@@@@I~
        else                                                       //~@@@@I~
    	{	i=Pi;j=Pj;	}                                          //~@@@@I~
        int captured=B.P.piece(i,j);                               //~v1A0I~
    	if (Dump.Y) Dump.println("received white my i="+i+",j="+j+",cpatured="+captured);//~@@@@I~//~v1A0R~
        if (captured!=0)                                           //~v1A0I~
        {                                                          //~v1A0I~
			captured=Field.nonPromoted(captured);                  //~v1A0I~
			((ConnectedBoard)B).partnerCapturePiece(i,j,1/*captured piece color*/,captured);//~v1A0I~
        }                                                          //~v1A0I~
        if (Pdropped!=0)                                           //~1A0eI~
        {                                                          //~1A0eI~
			((ConnectedBoard)B).partnerDroppedPiece(i,j,-1/*dropped piece color*/,Ppiece);//~1A0eR~
        }                                                          //~1A0eI~
//  	B.P.setPiece(i,j,Ppiece);         //~@@@@I~//~@@@2M~       //~@@@@R~
    	B.P.setPiece(i,j,-1/*color*/,Ppiece);                      //~@@@@I~
//      B.white(i,j);                                              //~@@@@R~
        B.white(i,j,Ppiece);                                       //~@@@@I~
    }                                                              //~@@@@I~
//*****************************************************            //~@@@@I~
//*from ConnectedBoard:selectPiece                                 //~@@@@I~
//*****************************************************            //~@@@@I~
	@Override //ConnectedGoFrame                                   //~@@@@I~
    protected void pieceSelected(int Pi,int Pj,int Pcolor)                    //~@@@@I~
    {                                                              //~@@@@I~
    	super.pieceSelected(Pi,Pj,Pcolor);
    	PF.sendSelected(Pi,Pj);                                    //~@@@@I~
    }                                                              //~@@@@I~
	@Override	//CGF                                              //~v1A0I~
	protected void gameovered()                                    //~v1A0I~
	{                                                              //~v1A0I~
    	if (Dump.Y) Dump.println("PGF gameovered");                //~v1A0I~
       	int reason=((ConnectedBoard)B).gameoverReason;             //~v1A0R~
        if ((reason & ~(ConnectedBoard.GOR_RESIGN             //~v1A0R~
				 	   |ConnectedBoard.GOR_CONNERR             //~v1A0R~
                       ))!=0)                                       //~v1A0I~
    	   	PF.gameoverNotify(reason);                                 //~v1A0I~
    	super.gameovered();                                        //~v1A0I~
	}                                                              //~v1A0I~
//************************************************************************//~1A0gI~
	@Override	//CGF                                              //~1A0gI~
	public void errCheckmate(int Pcolor/*winner*/)	               //~1A0gI~
    {                                                              //~1A0gI~
    	PF.sendCheckmate();                                        //~1A0gR~
    }                                                              //~1A0gI~
//***************************************************************  //~1A0gI~
	public void receiveCheckmate()                                 //~1A0gR~
    {                                                              //~1A0gI~
    	checkmateQuestion(this,-Col/*winner is opponent*/);        //~1A0gR~
    }                                                              //~1A0gI~
//***************************************************************  //~1A0gI~
	public int alertButtonAction(int Pbuttonid/*buttonid*/,int Pitempos/*winner color*/)//~1A0gI~
    {                                                              //~1A0gI~
    	boolean agree=(Pbuttonid==Alert.BUTTON_POSITIVE);         //~1A0gR~
	    super.errCheckmate(Pitempos,agree);                        //~1A0gR~
        PF.sendResponseCheckmate(agree);                            //~1A0gR~
    	return 0;                                                  //~1A0gI~
    }                                                              //~1A0gI~
//***************************************************************  //~1A0gM~
	public void responseReceivedCheckmate(boolean Pagree)          //~1A0gM~
    {                                                              //~1A0gM~
    	if (Dump.Y) Dump.println("responseReceivedCheckmate agree="+Pagree);//~1A0gM~
	    super.errCheckmate(Col,Pagree);                            //~1A0gI~
	}                                                              //~1A0gM~
    //***************************                                  //~1A23I~
    @Override //TGF                                                //~1A27I~
//  private void sendSuspend()                                     //~1A23R~//~1A27R~
    protected void suspendGame()                                   //~1A27I~
    {                                                              //~1A23I~
    	if (Dump.Y) Dump.println("PGF saveFile");                  //~1A23I~//~1A27R~
//  	new EndGameQuestion(this);                                 //~1A23I~
    	new EndGameQuestion(this);                                 //~1A29I~
    }                                                              //~1A29I~
    //***************************                                  //~1A29I~
    public void doSuspendGame()                                    //~1A29I~
    {                                                              //~1A29I~
        PF.sendSuspend();   //send @@suspendgame                   //~1A23R~
	}                                                              //~1A23I~
    //***************************                                  //~1A23I~
    //*sent yes to suspend req                                     //~1A23I~
    //***************************                                  //~1A23I~
    public void acceptSuspend()                                    //~1A23R~
    {                                                              //~1A23I~
    	if (Dump.Y) Dump.println("PGF accepted suspend");          //~1A23R~
	    saveGame(null/*notename*/);   //TGF                                        //~1A23R~
	}                                                              //~1A23I~
    //***************************                                  //~1A23I~
    @Override //TGF                                                //~1A23I~
    public void fileSaved(String Pnotesname)                                        //~1A23I~//~1A24R~
    {                                                              //~1A23I~
    	if (Dump.Y) Dump.println("PGF fileSaved");                 //~1A23I~
        swFileSaved=true;                                          //~1A28I~
	    super.fileSaved(Pnotesname);   //TGF,stop timer,CloseButton                                 //~1A23I~//~1A24R~
    	if (saveAfterGameover)                                     //~1A27R~
        	infoGameSaved(Pnotesname);                             //~1A27I~
        else                                                       //~1A27I~
        {                                                          //~1A27I~
        	if (PF.swSuspendRequester)                                 //~1A24R~//~1A27R~
        		PF.sendSuspendSync(Pnotesname);                                  //~1A24I~//~1A27R~
        	else                                                       //~1A24I~//~1A27R~
        		PF.sendSuspendOK(Pnotesname);                           //~1A24R~//~1A27R~
        }                                                          //~1A27I~
	}                                                              //~1A23I~
    //***************************                                  //~1A24I~
    @Override //GoFrame                                            //~1A23I~
    public void fileDialogNotSaved()                               //~1A23I~
    {                                                              //~1A23I~
    	if (Dump.Y) Dump.println("PGF fileNotSaved");              //~1A23I~
        if (PF.swSuspendRequester)                                 //~1A24I~
        {                                                          //~1A24I~
        	PF.sendSuspendNoSync();                                //~1A24I~
	    	AView.showToast(R.string.SuspendSyncErr);              //~1A24I~
        }                                                          //~1A24I~
        else                                                       //~1A24I~
        {                                                          //~1A24I~
        	PF.declinesuspendgame();                                   //~1A23I~//~1A24R~
	    	AView.showToast(R.string.DeclineSuspend);             //~1A0gI~//~1A23I~//~1A24R~
        }                                                          //~1A24I~
	}                                                              //~1A23I~
    //***************************                                  //~1A23I~
    //*received yes to suspend req                                 //~1A23I~
    //***************************                                  //~1A23I~
    public void receivedSuspendOK(String Ppartnernotesname)                                //~1A23R~//~1A24R~
    {                                                              //~1A23I~
    	if (Dump.Y) Dump.println("PGF suspended");                 //~1A23I~
//      saveGame(Ppartnernotesname);   //send @@suspendgame                         //~1A23I~//~1A24R~//~1Ab3R~
        String partnernotesname=Ppartnernotesname;                 //~1Ab3R~
        if (partnernotesname!=null && !partnernotesname.equals(""))//~1Ab3R~
        {                                                          //~1Ab3R~
        	int li=partnernotesname.lastIndexOf('/');              //~1Ab3R~
            if (li>=0)                                             //~1Ab3R~
        		partnernotesname=partnernotesname.substring(li+1); //~1Ab3R~
            if (partnernotesname.endsWith("."+FileDialog.GAMES_EXT))//~1Ab3R~
            {                                                      //~1Ab3R~
	        	li=partnernotesname.lastIndexOf("."+FileDialog.GAMES_EXT);//~1Ab3R~
        		partnernotesname=partnernotesname.substring(0,li); //~1Ab3R~
            }                                                      //~1Ab3R~
        }                                                          //~1Ab3R~
        saveGame(partnernotesname);   //send @@suspendgame         //~1Ab3R~
	}                                                              //~1A23I~
    //***************************                                  //~1A27I~
    //*after Resigned           *                                  //~1A27I~
    //***************************                                  //~1A27I~
    @Override //TGF                                                //~1A27I~
    public void saveGameover()	                                   //~1A27I~
    {                                                              //~1A27I~
    	if (Dump.Y) Dump.println("PGF saveGameover");              //~1A27I~
    	saveAfterGameover=true;                                    //~1A27I~
        saveGame(null);                                            //~1A27I~
    }                                                              //~1A27I~
}

