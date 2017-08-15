//*CID://+1AhbR~:                             update#=  188;       //~1AhbR~
//****************************************************************************//~@@@1I~
//1Ahb 2016/11/20 piece on move dest is displayed at next touch(image was drawn after copy()/ondraw)//~1AhbI~
//1Ah2*2016/10/28 (Bug)process @@!move was not on Synchthread      //~1Ah2I~
//1Ah0 2015/10/15 bonanza                                          //~1Ah0I~
//1A4q 2014/12/05 (Bug)Cancel save dose ot change to Close button enabled//~1A4qI~
//1A2i 2013/04/03 (Bug)Resig on Local board; Endgame question is End game?//~1A2iI~
//1A27 2013/03/25 File button --> Suspend button on Local/Remote board//~1A27I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A1j 2013/03/19 change Help file encoding to utf8 (path change drop jagoclient from jagoclient/helptexts)//~1A10I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0g 2013/03/06 judge checkmate                                  //~1A0gI~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//****************************************************************************//~@@@1I~
package com.Asgts.gtp;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.AView;
import com.Asgts.Alert;
import com.Asgts.AlertI;
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.KeyEvent;                                      //~v107R~//~@@@@R~
import com.Asgts.awt.KeyListener;                                   //~v107R~//~@@@@R~

import jagoclient.Dump;
import jagoclient.board.Board;
import jagoclient.board.ConnectedBoard;
import jagoclient.board.Field;
import jagoclient.board.Notes;
import jagoclient.board.TimedGoFrame;
import jagoclient.dialogs.HelpDialog;
import jagoclient.partner.EndGameQuestion;
import jagoclient.sound.JagoSound;
import jagoclient.MainFrame;

/**
The go frame for partner connections.
*/

public class GtpGoFrame extends TimedGoFrame                     //~@@@@I~//~1Ah0R~
	implements KeyListener                                                  //~@@@1I~
    ,AlertI                                                        //~1A00I~
{                                                                  //~@@@@R~
	private static final int TITLEID=R.string.GtpGoFrameTitle;     //~1Ah0I~
	public GtpFrame PF;
//    private String matchName;                                              //~1A22I~
//    int Handicap;                                                //~@@@@R~
    private boolean saveAfterGameover;                             //~1A27I~//~1Ah0I~
    public  boolean swFileSaved;                                   //~1A28I~//~1Ah0I~
    private GtpGoParm goParm;                                      //~1Ah0R~
	
//*********************************************************************//~@@@@I~
//from LocalGoFrame                                                //~@@@@I~
//*********************************************************************//~@@@@I~
//  public LocalGoFrame (LocalFrame pf,int Ptitleid,                //~1A10I~//~1Ah0R~
    public GtpGoFrame(MainFrame pf,GtpFrame Pconnection,int Ptitleid,GtpGoParm Pparm)//~1Ah0I~
    {                                                              //~@@@@I~
        super(Pconnection,AG.frameId_ConnectedGoFrame,AG.resource.getString(TITLEID),//~1A10I~//~1Ah0R~
			Pparm.color,Pparm.size,Pparm.totaltime,Pparm.extratime,Pparm.gameoptions,Pparm.handicap);//~@@@@R~         //~v1A0R~//~1Ah0R~
        goParm=Pparm;                                              //~1Ah0I~
		PF=Pconnection;
//  	if (col==1)                                                //~@@@@I~//~1Ah0R~
    	if (Pparm.color==Board.COLOR_BLACK)                         //~1Ah0I~
        {                                                          //~@@@@I~
        	BlackName=Pparm.yourname;                               //~1Ah0R~
        	WhiteName=Pparm.opponentname;                           //~1Ah0R~
        }                                                          //~@@@@I~
        else                                                       //~@@@@I~
        {                                                          //~@@@@I~
        	BlackName=Pparm.opponentname;                                 //~@@@@I~//~1Ah0R~
        	WhiteName=Pparm.yourname;                              //~1Ah0R~
        }                                                          //~@@@@I~
        settitleNoTimer(); 	//TGF                                  //~1Ah0I~
		addKeyListener(this);                                      //~@@@1I~//~1Ah0I~
		setVisible(true);                                          //~1Ah0I~
		repaint();                                                 //~1Ah0I~
    }                                                              //~1A10I~
//    private void showFrame()                                            //~1A10I~//~1Ah0R~
//    {                                                              //~1A10I~//~1Ah0R~
//        addKeyListener(this);                                      //~@@@1I~//~1Ah0R~
//        setVisible(true);                                        //~1Ah0R~
//        repaint();                                               //~1Ah0R~
//    }                                                            //~1Ah0R~

	public void doAction (String o)
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.Resign))) //~@@@@I~
		{                                                          //~@@@1I~
        	if (Dump.Y) Dump.println("GtpGoFrame.doAction resign mycolor="+PF.MyColor+",maincolor="+maincolor());//~1Ah0I~
			if (maincolor()!=PF.MyColor)                           //~1Ah0I~
            {                                                      //~1Ah0I~
	        	AView.showToast(R.string.WarningNotYourTurn);      //~1Ah0I~
            	return;                                            //~1Ah0I~
            }                                                      //~1Ah0I~
    		PF.resign();                                      //~1A2iI~//~1Ah0R~
			return;                                                //~@@@1I~
		}                                                          //~@@@1I~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~@@@@I~
		{                                                          //~@@@@I~
        	new HelpDialog(this,R.string.HelpTitle_GoFrame,"BoardFrame");//~1A10I~
		}                                                          //~@@@@I~
//*doaction:supend at TimedGoFrame is call suspendGame overriden by GtpGoFrame//~1Ah0R~
		else super.doAction(o);
	}

	public boolean blocked ()
	{	return false;
	}

//*****************************************************            //~@@@@I~
	public boolean wantsmove ()
    {                                                              //~@@@@R~
		return true;  //request ConnectedBoard to call CGF.moveset(i,j) to get move event//~1Ah0R~
	}
//*from ConnectedBoard**************************************       //~@@@@I~
//    public boolean moveset(int i,int j)                            //~@@@@I~//~1Ah0R~
//    {                                                              //~@@@@I~//~1Ah0R~
//        if (Dump.Y) Dump.println("LocalGoFrame moveset return false");//~@@@@I~//~1Ah0R~
//        return false;   //nop movemouse                            //~@@@@I~//~1Ah0R~
//    }                                                              //~@@@@I~//~1Ah0R~
//    //****************************************************************//~1Ah0I~
//    @Override                                                    //~1Ah0I~
//    protected boolean moveset(int Pi,int Pj,int Ppiece,boolean Pdropped,int Poldi,int Poldj)//~1Ah0I~
//    {                                                            //~1Ah0I~
//        if (Dump.Y)Dump.println("GtpGoFrame:moveset");           //~1Ah0I~
//        PF.moveset(Pi,Pj,Ppiece,Pdropped,Poldi,Poldj);           //~1Ah0I~
//        return true;                                             //~1Ah0I~
//    }                                                            //~1Ah0I~
//**********************************************************       //~1Ah0I~
	@Override //ConnectedGoFrame                                   //~@@@@I~//~1Ah0I~
    public boolean moveset (int i, int j,int Ppiece,boolean Pdropped,int Poldi,int Poldj)//~1Ah0I~
    {                                                              //~1A74I~//~1Ah0I~
    	if (Dump.Y)Dump.println("GtpGoFrame:moveset i="+i+",j="+j+",piece="+Ppiece+",drop="+Pdropped+",oldi="+Poldi+",oldj="+Poldj);//~1Ah0I~
    	if (Dump.Y)Dump.println("GtpGoFrame:moveset Started="+Started+",Ended="+Ended);//~1Ah0I~
        if (!Started || Ended)                                     //~1A74I~//~1Ah0I~
        {                                                          //~1A74I~//~1Ah0I~
        	if (!Started)                                          //~1A74I~//~1Ah0I~
	        	AView.showToast(R.string.InfoGameNotStarted);           //~1A74I~//~1Ah0I~
            else                                                   //~1A74I~//~1Ah0I~
	        	AView.showToast(R.string.InfoGameAlreadyEnded);         //~1A74I~//~1Ah0I~
        	return false;                                          //~1A74I~//~1Ah0I~
        }                                                          //~1A74I~//~1Ah0I~
//        String color;                                              //~@@@@R~//~1Ah0I~
//        int enteredExtraTime=0;                                    //~1A06I~//~1Ah0I~
//        if (B.maincolor()!=Col) return false;                      //~@@@@R~//~1Ah0I~
//        if (B.maincolor()>0) color="b";                            //~@@@@R~//~1Ah0I~
//        else color="w";                                            //~@@@@R~//~1Ah0I~
//        if (Timer!=null && Timer.isAlive()) alarm();               //~@@@@R~//~1Ah0I~
//        int bm=BlackMoves,wm=WhiteMoves;                           //~@@@@R~//~1Ah0I~
//        if (Col>0)                                                 //~1A06R~//~1Ah0I~
//        {                                                          //~1A06I~//~1Ah0I~
//            if (BlackMoves>0) BlackMoves--;                        //~1A06I~//~1Ah0I~
//            enteredExtraTime=resetExtraTime(Col)?1:0;               //~1A06I~//~1Ah0I~
//        }                                                          //~1A06I~//~1Ah0I~
//        else                                                       //~1A06R~//~1Ah0I~
//        {                                                          //~1A06I~//~1Ah0I~
//            if (WhiteMoves>0) WhiteMoves--;                        //~1A06I~//~1Ah0I~
//            enteredExtraTime=resetExtraTime(Col)?1:0;               //~1A06I~//~1Ah0I~
//        }                                                          //~1A06I~//~1Ah0I~
//        if (!PF.moveset(color,Ppiece,Pdropped?1:0,i,j,BlackTime-BlackRun,BlackMoves,//~v1A0I~//~1Ah0I~
//            WhiteTime-WhiteRun,WhiteMoves,enteredExtraTime))       //~1A06I~//~1Ah0I~
//        {   BlackMoves=bm; WhiteMoves=wm;                          //~@@@@R~//~1Ah0I~
//            if (Timer.isAlive()) alarm();                          //~@@@@R~//~1Ah0I~
//            return false;                                          //~@@@@R~//~1Ah0I~
//        }                                                          //~@@@@R~//~1Ah0I~
//        PF.moveset(i,j,Ppiece,Pdropped,Poldi,Poldj);             //~1Ah0R~
		GtpMove m=new GtpMove(GtpMove.GTPMOVE_HUMAN_MOVE,PF.MyColor,Ppiece,i,j,Poldi,Poldj,Pdropped);//~1Ah0I~
    	requestCallback(m);                                        //~1Ah0I~
        return true;                                               //~@@@@R~//~1Ah0I~
    }                                                              //~@@@@R~//~1Ah0I~
//*******************************************************************//~1Ah0I~
//*callback from Canvas  when humanmove,send move to computer      //~1Ah0I~
//*******************************************************************//~1Ah0I~
	private void canvasCallbackHumanMove(GtpMove Pgtpmove)         //~1Ah0I~
    {                                                              //~1Ah0I~
//      B.setCopySW(true/*do not copy*/);	//@@@@test             //+1AhbR~
//  	B.copy();	//copy and invalidate   //@@@@test             //+1AhbR~
    	GtpMove m=Pgtpmove;                                        //~1Ah0I~
    	if (Dump.Y)Dump.println("GtpGoFrame:sendPlay i="+m.xx+",j="+m.yy+",piece="+m.piece+",drop="+m.drop+",oldi="+m.oldxx+",oldj="+m.oldyy);//~1Ah0I~
    	if (Dump.X)Dump.println("GtpGoFrame:sendPlay i="+m.xx+",j="+m.yy+",piece="+m.piece+",drop="+m.drop+",oldi="+m.oldxx+",oldj="+m.oldyy);//~1AhbI~
        PF.moveset(m.xx,m.yy,m.piece,m.drop,m.oldxx,m.oldyy);      //~1Ah0I~
    }                                                              //~1Ah0I~
//*****************************************************            //~@@@@I~
//*from ConnectedBoard:selectPiece                                 //~@@@@I~
//*****************************************************            //~@@@@I~
	@Override //ConnectedGoFrame                                   //~@@@@I~
    protected void pieceMoved(int Pcolor/*last moved color*/)      //~@@@@R~
    {                                                              //~@@@@R~
    	if (Dump.Y) Dump.println("GtpGoFrame:pieceMoved color="+Pcolor);//~1Ah0I~
    	super.pieceMoved(Pcolor);                                  //~v1A0I~
//        JagoSound.play("piecedown",false/*not change to beep when beeponly option is on*/);//~@@@@I~//~1Ah0R~
    }                                                              //~@@@@I~
//*****************************************************            //~@@@@I~
	public void doclose ()
	{	setVisible(false); dispose();
		if (Timer!=null && Timer.isAlive()) Timer.stopit();        //~v107I~
		PF.toFront();
		PF.boardclosed(this);
		PF.F=null; //GtpGoFrame ptr
	}

  	public int maincolor ()                                        //~1Ah0R~
  	{	return B.maincolor();                                      //~1Ah0R~
  	}                                                              //~1Ah0R~
//************************************************************************//~@@@@I~//~1Ah0I~
    protected void start ()                                        //~@@@@I~//~1Ah0I~
    {                                                              //~@@@@I~//~1Ah0I~
        super.start();                                             //~@@@@I~//~1Ah0I~
    }                                                              //~@@@@I~//~1Ah0I~
    @Override //GoFrame                                            //~1Ah0I~
	public void yourMove (boolean notinpos)
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("GtpGoFrame:yourMove notinpos="+notinpos);//~1Ah0R~
 	  	JagoSound.play("piecedown",false/*not change to beep when beeponly option is on*/);//~1A09I~//~1Ah0R~
    }                                                              //~@@@@I~
//*FunctionKey support                                             //~@@@1I~
	public void keyPressed (KeyEvent e) {}                         //~@@@1I~
	public void keyTyped (KeyEvent e) {}                           //~@@@1I~
	public void keyReleased (KeyEvent e)                           //~@@@1I~
	{                                                              //~@@@@I~
	}                                                              //~@@@1I~
//*******************************************************************//~1A37I~//~1Ah0M~
    public void receiveSelected(int Pcolor,int Pi,int Pj)                    //~1A37I~//~1Ah0M~//~1Ah2R~
    {                                                              //~1A37I~//~1Ah0M~
        if (Dump.Y) Dump.println("GGF:receiveSelected at "+Pi+","+Pj+",color="+Pcolor); //~1A37I~//~1Ah0M~//~1Ah2R~
    	((ConnectedBoard)B).receiveSelected(Pi,Pj);                  //~@@@@R~//~1Ah0M~//~1Ah2R~//~1Ah0R~
        if (Dump.Y) Dump.println("GGF:receiveSelected return");    //~1Ah2I~
	}                                                              //~1A37I~//~1Ah0M~
//************************************************************************//~1Ah0I~
    public void black(int Pi,int Pj,int Ppiece,boolean Pdropped)//~1A0eI~//~1Ah0M~
    {                                                              //~@@@@I~//~1Ah0M~
    	int i,j;                                                   //~@@@@R~//~1Ah0M~
    //*******************                                          //~@@@@I~//~1Ah0M~
    	i=Pi;j=Pj;                                          //~@@@@I~//~1Ah0M~
        int captured=B.P.piece(i,j);                               //~v1A0I~//~1Ah0M~
    	if (Dump.Y) Dump.println("GtpGoFrame:black received black i="+i+",j="+j+",captured="+captured+",piece="+Ppiece+",drop="+Pdropped);//~@@@@I~//~v1A0R~//~1A0cR~//~1Ah0M~
        if (captured!=0)                                           //~v1A0I~//~1Ah0M~
        {                                                          //~v1A0I~//~1Ah0M~
			captured=Field.nonPromoted(captured);                  //~v1A0R~//~1Ah0M~
			((ConnectedBoard)B).partnerCapturePiece(i,j,-1/*captured piece color*/,captured);//~v1A0I~//~1Ah0M~
        }                                                          //~v1A0I~//~1Ah0M~
        if (Pdropped)                                           //~1A0eI~//~1Ah0M~
        {                                                          //~1A0eI~//~1Ah0M~
			((ConnectedBoard)B).partnerDroppedPiece(i,j,1/*dropped piece color*/,Ppiece);//~1A0eR~//~1Ah0M~
        }                                                          //~1A0eI~//~1Ah0M~
    	B.P.setPiece(i,j,1/*color*/,Ppiece);                       //~@@@@I~//~1Ah0M~
        B.black(i,j,Ppiece);                                       //~@@@@I~//~1Ah0M~
    }                                                              //~@@@@I~//~1Ah0M~
    public void white(int Pi,int Pj,int Ppiece,boolean Pdropped)   //~@@@@R~//~1Ah0M~
    {                                                              //~@@@@I~//~1Ah0M~
    	int i,j;                                                   //~@@@@I~//~1Ah0M~
    //*******************                                          //~@@@@I~//~1Ah0M~
    	if (Dump.Y) Dump.println("GtpGoFrame:white i="+Pi+",j="+Pj+",piece="+Ppiece+",drop="+Pdropped);//~1Ah0M~
    	i=Pi;j=Pj;                                          //~@@@@I~//~1Ah0M~
        int captured=B.P.piece(i,j);                               //~v1A0I~//~1Ah0M~
    	if (Dump.Y) Dump.println("GtpGoFrame:white received white i="+i+",j="+j+",cpatured="+captured+",drop="+Pdropped);//~@@@@I~//~v1A0R~//~1Ah0M~
        if (captured!=0)                                           //~v1A0I~//~1Ah0M~
        {                                                          //~v1A0I~//~1Ah0M~
			captured=Field.nonPromoted(captured);                  //~v1A0I~//~1Ah0M~
			((ConnectedBoard)B).partnerCapturePiece(i,j,1/*captured piece color*/,captured);//~v1A0I~//~1Ah0M~
        }                                                          //~v1A0I~//~1Ah0M~
        if (Pdropped)                                           //~1A0eI~//~1Ah0M~
        {                                                          //~1A0eI~//~1Ah0M~
			((ConnectedBoard)B).partnerDroppedPiece(i,j,-1/*dropped piece color*/,Ppiece);//~1A0eR~//~1Ah0M~
        }                                                          //~1A0eI~//~1Ah0M~
    	B.P.setPiece(i,j,-1/*color*/,Ppiece);                      //~@@@@I~//~1Ah0M~
        B.white(i,j,Ppiece);                                       //~@@@@I~//~1Ah0M~
    }                                                              //~@@@@I~//~1Ah0M~
//*****************************************************            //~@@@@I~//~1Ah0I~
//*from ConnectedBoard:selectPiece                                 //~@@@@I~//~1Ah0I~
//*****************************************************            //~@@@@I~//~1Ah0I~
	@Override //ConnectedGoFrame                                   //~@@@@I~//~1Ah0I~
    protected void pieceSelected(int Pi,int Pj,int Pcolor)                    //~@@@@I~//~1Ah0I~
    {                                                              //~@@@@I~//~1Ah0I~
    	super.pieceSelected(Pi,Pj,Pcolor);                         //~1Ah0I~
    	PF.sendSelected(Pi,Pj);                                    //~@@@@I~//~1Ah0I~
    }                                                              //~@@@@I~//~1Ah0I~
//************************************************************************//~1Ah0I~
//************************************************************************//~1A0gR~
	@Override	//CGF                                              //~1A0gR~
	public void errCheckmate(int Pcolor/*winner*/)	           //~1A0gR~
    {                                                              //~1A0gR~
    	checkmateQuestion(this,Pcolor);                            //~1A0gR~
    }                                                              //~1A0gR~
//************************************************************************//~1Ah0I~
	public int alertButtonAction(int Pbuttonid/*buttonid*/,int Pitempos/*winner color*/)//~1A0gR~
    {                                                              //~1A0gR~
    	boolean agree=(Pbuttonid==Alert.BUTTON_POSITIVE);          //~1A0gR~
	    super.errCheckmate(Pitempos,agree);                        //~1A0gR~
    	return 0;//~1A0gR~
    }                                                              //~1A0gR~
//************************************************************************//~1A22I~
	@Override //TGF                                                //~1A22I~
    protected void suspendGame()                                     //~1A22R~
    {                                                              //~1A22I~
    	if (Dump.Y) Dump.println("GtpGoFrame suspendGame");                  //~1A22R~//~1Ah0R~
 //  	PF.suspend();                                              //~1Ah0R~
    	new EndGameQuestion(this);                                 //~1A29I~//~1Ah0I~
	}                                                              //~1A22I~
    //***************************                                  //~1A29I~//~1Ah0I~
    public void doSuspendGame()                                    //~1A29I~//~1Ah0I~
    {                                                              //~1A29I~//~1Ah0I~
        PF.sendSuspend();   //callback to receivedSuspendOK        //~1Ah0R~
	}                                                              //~1A23I~//~1Ah0I~
//************************************************************************//~1A27I~
//    @Override //TGF                                                //~1A27I~//~1Ah0R~
//    public void fileDialogSaved(String Pnotesname)                 //~1A27I~//~1Ah0R~
//    {                                                              //~1A27I~//~1Ah0R~
//        super.fileDialogSaved(Pnotesname);                         //~1A27I~//~1Ah0R~
//        infoGameSaved(Pnotesname);                                 //~1A27I~//~1Ah0R~
//    }                                                              //~1A27I~//~1Ah0R~
    //************************************************************ //~1A23I~//~1Ah0I~
    @Override //TGF                                                //~1A23I~//~1Ah0I~
    public void fileSaved(String Pnotesname)                                        //~1A23I~//~1A24R~//~1Ah0I~
    {                                                              //~1A23I~//~1Ah0I~
    	if (Dump.Y) Dump.println("GGF fileSaved");                 //~1A23I~//~1Ah0I~
        swFileSaved=true;                                          //~1A28I~//~1Ah0I~
	    super.fileSaved(Pnotesname);   //TGF,stop timer,CloseButton                                 //~1A23I~//~1A24R~//~1Ah0I~
    	if (saveAfterGameover)                                     //~1A27R~//~1Ah0I~
        	infoGameSaved(Pnotesname);                             //~1A27I~//~1Ah0I~
//        else                                                       //~1A27I~//~1Ah0I~
//        {                                                          //~1A27I~//~1Ah0I~
//            if (PF.swSuspendRequester)                                 //~1A24R~//~1A27R~//~1Ah0I~
//                PF.sendSuspendSync(Pnotesname);                                  //~1A24I~//~1A27R~//~1Ah0I~
//            else                                                       //~1A24I~//~1A27R~//~1Ah0I~
//                PF.sendSuspendOK(Pnotesname);                           //~1A24R~//~1A27R~//~1Ah0I~
//        }                                                          //~1A27I~//~1Ah0I~
	}                                                              //~1A23I~//~1Ah0I~
//************************************************************************//~1A4qI~
//    @Override //TGF                                                //~1A4qI~//~1Ah0R~
//    public void fileDialogNotSaved()                               //~1A4qI~//~1Ah0R~
//    {                                                              //~1A4qI~//~1Ah0R~
//        fileNotSaved(); //TGF                                      //~1A4qI~//~1Ah0R~
//    }                                                              //~1A4qI~//~1Ah0R~
    //***************************                                  //~1A24I~//~1Ah0I~
    @Override //GoFrame                                            //~1A23I~//~1Ah0I~
    public void fileDialogNotSaved()                               //~1A23I~//~1Ah0I~
    {                                                              //~1A23I~//~1Ah0I~
    	if (Dump.Y) Dump.println("GGF fileNotSaved");              //~1A23I~//~1Ah0I~
//        if (PF.swSuspendRequester)                                 //~1A24I~//~1Ah0I~
//        {                                                          //~1A24I~//~1Ah0I~
//            PF.sendSuspendNoSync();                                //~1A24I~//~1Ah0I~
//            AView.showToast(R.string.SuspendSyncErr);              //~1A24I~//~1Ah0I~
//        }                                                          //~1A24I~//~1Ah0I~
//        else                                                       //~1A24I~//~1Ah0I~
//        {                                                          //~1A24I~//~1Ah0I~
//            PF.declinesuspendgame();                                   //~1A23I~//~1A24R~//~1Ah0I~
//            AView.showToast(R.string.DeclineSuspend);             //~1A0gI~//~1A23I~//~1A24R~//~1Ah0I~
//        }                                                          //~1A24I~//~1Ah0I~
	}                                                              //~1A23I~//~1Ah0I~
//************************************************************************//~1Ah0I~
//* from GtpFrame.sendSuspend                                      //~1Ah0I~
//************************************************************************//~1Ah0I~
    public void receivedSuspendOK()                                //~1A23R~//~1A24R~//~1Ah0I~
    {                                                              //~1A23I~//~1Ah0I~
    	if (Dump.Y) Dump.println("GGF suspended");                 //~1A23I~//~1Ah0I~
        saveGame(null);                                            //~1Ah0I~
	}                                                              //~1A23I~//~1Ah0I~
//************************************************************************//~1A27I~
	@Override //TGF                                                //~1A27I~
    protected void saveGameover()                                  //~1A27I~
    {                                                              //~1A27I~
    	if (Dump.Y) Dump.println("LGF saveGameover");              //~1A27I~
    	saveAfterGameover=true;                                    //~1A27I~//~1Ah0I~
		saveGame(null);	//TGF                                          //~1A27I~
	}                                                              //~1A27I~
//*******************************************************************//~v1BaI~//~1Ah0I~
//*from GTPConnector:gotMoved-->Canvas                             //~1Ah0R~
//*callback request to run on BoardSync thread                     //~1Ah0I~
//*******************************************************************//~v1BaI~//~1Ah0I~
	public void requestCallback(GtpMove Pmove)  //~v1BaR~          //~1Ah0R~
    {                                                              //~v1BaI~//~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGoFrame;requestCallback");    //~1Ah0I~
    	B.requestCallback(this,Pmove);	                           //~v1BaR~//~1Ah0R~
    }                                                              //~v1BaI~//~1Ah0I~
//*******************************************************************//~v1BaI~//~1Ah0I~
//*callback from Canvas  by computer move(on canvas thread)                                           //~v1BaI~//~1Ah0R~
//*-->CTPConnector-->GtpFrame:gotMove                              //~1Ah0I~
//*******************************************************************//~v1BaI~//~1Ah0I~
	@Override                                                      //~v1BaI~//~1Ah0I~
	public void canvasCallback(Object Pobj)                        //~v1BaR~//~1Ah0I~
    {                                                              //~v1BaI~//~1Ah0I~
        GtpMove m=(GtpMove)Pobj;                                   //~1Ah0I~
    	if (Dump.Y) Dump.println("GGF:canvasCallback funcid="+m.funcid+",status="+m.status);//~1Ah2I~//~1Ah0R~
    	if (Dump.X) Dump.println("X:GGF:canvasCallback funcid="+m.funcid+",status="+m.status);//~1Ah0I~
        if (m.funcid==GtpMove.GTPMOVE_HUMAN_MOVE)	//response for human move                                                      //~1Ah0I~//~1Ah2R~
        {                                                          //~1Ah2I~
        	if (m.status==GtpMove.GTPMOVE_STATUS_SENDPLAY)	//response for human move//~1Ah0I~
				canvasCallbackHumanMove(m);   //sendPlay           //~1Ah0I~
            else                                                   //~1Ah0I~
        	if (m.status==GtpMove.GTPMOVE_STATUS_SENDPLAY_RESPONSE)	//response for human move//~1Ah0I~
        	{                                                      //~1Ah0I~
    			if (Dump.Y) Dump.println("GGF:canvasCallback hunmanmove response");//~1Ah0I~
    			if (Dump.X) Dump.println("X:GGF:canvasCallback hunmanmove response");//~1Ah0I~
        	}                                                      //~1Ah0I~
            else                                                   //~1Ah0I~
            {                                                      //~1Ah0I~
	            gotMove(m.color,m.xx,m.yy,m.piece);                    //~1Ah2I~//~1Ah0R~
	        	m.status=GtpMove.GTPMOVE_STATUS_SENDPLAY;	//response for human move//~1Ah0I~
				requestCallback(m);//sendplay after draw by gotMove()//~1Ah0I~
            }                                                      //~1Ah0I~
            return;                                                //~1Ah2I~//~1Ah0R~
                                                                   //~1Ah0I~
        }                                                          //~1Ah2I~
//*GtpGoFrame.GTPConnector.canvasCallback                          //~1Ah0I~
//  	PF.C.canvasCallback(Pobj);           //~v1BaR~             //~1Ah0R~//~1Ah2R~
	    if (PF.MyColor==Board.COLOR_WHITE)                         //~1Ah0I~
        	m.reverseCoord();                                  //~1Ah0I~
        if (m.moveid==GtpMove.MOVEID_RESIGNED)   //computer resigned//~1Ah0I~
			gameoverMessage(-m.color/*winner*/,4/*resign*/);	//CGF//~1Ah0M~
        else                                                       //~1Ah0M~
        if (m.moveid==GtpMove.MOVEID_MATED)                        //~1Ah0I~
			gameoverMessage(m.color/*winner*/,2/*checkmate*/);  //CGF//~1Ah0I~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
			gotMove(m.color,m.xx,m.yy,m.piece,m.oldxx,m.oldyy,m.drop);//~1Ah2I~//~1Ah0R~
        }                                                          //~1Ah0I~
    }                                                              //~v1BaI~//~1Ah0I~
//**************************************************************** //~v1B6I~//~1Ah0I~
//*by computer move                                                //~1Ah0R~
//**************************************************************** //~v1B6I~//~1Ah0I~
	public void gotMove(int color, int i, int j,int Ppiece,int Poldi,int Poldj,boolean Pdrop)//~1Ah0R~
	{                                                              //~1Ah0R~
    	if (Dump.Y) Dump.println("GtpGoFrame:gotMove by ComputerMove color="+color+",maincolor="+B.maincolor()+",piece="+Ppiece+",i="+i+",j="+j+",oldi="+Poldi+",oldj="+Poldj);//~1Ah0I~
    	if (Dump.X) Dump.println("X:GtpGoFrame:gotMove by ComputerMove color="+color+",maincolor="+B.maincolor()+",piece="+Ppiece+",i="+i+",j="+j+",oldi="+Poldi+",oldj="+Poldj);//~1Ah0R~
		synchronized (B)                                           //~1Ah0I~
//  	{	if (B.maincolor()==color) return;                      //~1Ah0R~
    	{                                                          //~1Ah0I~
        	B.setCopySW(false/*do not copy*/);                     //~1AhbI~
//  	 	if (B.maincolor()==color) return;   //maincolor is not updated yet//~1Ah0I~
//  		yourMove(true);   //no sound for select                //~1Ah0R~
            if (!Pdrop)                                            //~1Ah0R~
            {                                                      //~1Ah0R~
                if (Dump.Y) Dump.println("GtpGoFrame:gotMove computer move, call receiveSelected");//~1Ah0R~
                receiveSelected(color,Poldi,Poldj);                     //~@@@2R~//~1Ah0R~
            }                                                      //~1Ah0R~
    		((ConnectedBoard)B).swDropped=Pdrop;                   //~1Ah0I~
//			if (color==GTPConnector.WHITE) white(i,j);             //~v1B6R~//~1Ah0I~
//  		else black(i,j);                                       //~v1B6I~//~1Ah0I~
  			if (color==Board.COLOR_WHITE)                         //~v1B6I~//~1Ah0R~
            {                                                      //~v1B6I~//~1Ah0I~
		    	if (Dump.Y) Dump.println("GtpGoFrame;gotMove call white()");//~1Ah0I~
//          	updateWhiteMoves();   //reset extramove(Go only)                              //~v1B6I~//~1Ah0R~
				white(i,j,Ppiece,Pdrop);    //GoFrame                                    //~v1B6I~//~1Ah0R~
            }                                                      //~v1B6I~//~1Ah0I~
    		else                                                   //~v1B6R~//~1Ah0I~
            {                                                      //~v1B6I~//~1Ah0I~
		    	if (Dump.Y) Dump.println("GtpGoFrame;gotMove call black()");//~1Ah0I~
//          	updateBlackMoves();   //reset extramove             //~v1B6I~//~1Ah0R~
				black(i,j,Ppiece,Pdrop);                                        //~v1B6I~//~1Ah0R~
            }                                                      //~v1B6I~//~1Ah0I~
      		updateTime(color);                                     //~1Ah0I~
			B.showinformation();                                   //~1Ah0I~
        	B.setCopySW(true/*do not copy*/);                      //~1AhbI~
			B.copy();                                              //~1Ah0I~
//      	B.setCopySW(true/*do not copy*/);      //@@@@test      //+1AhbR~
//  		B.copy();                              //@@@@test      //+1AhbR~
		}                                                          //~1Ah0I~
	    if (Dump.Y) Dump.println("GtpGoFrame:gotMove by ComputerMove end");//~1Ah0I~
	    if (Dump.X) Dump.println("X:GtpGoFrame:gotMove by ComputerMove end");//~1Ah0I~
	}
//**************************************************************** //~1Ah0I~
//*by human move                                                   //~1Ah0I~
//**************************************************************** //~1Ah0I~
	public void gotMove(int color, int i, int j,int Ppiece)        //~1Ah0R~
	{                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGoFrame;gotMove by human move color="+color+",maincolor="+B.maincolor()+",piece="+Ppiece+",i="+i+",j="+j);//~1Ah0R~
    	if (Dump.X) Dump.println("X:GtpGoFrame;gotMove by human move color="+color+",maincolor="+B.maincolor()+",piece="+Ppiece+",i="+i+",j="+j);//~1Ah0R~
		synchronized (B)                                           //~1Ah0I~
    	{                                                          //~1Ah0I~
        	B.setCopySW(false/*do not copy*/);                     //~1AhbR~
  			if (color==Board.COLOR_WHITE)                          //~1Ah0I~
            {                                                      //~1Ah0I~
		    	if (Dump.Y) Dump.println("GtpGoFrame:at response to human move;call white()");//~1Ah0I~
				white(i,j,Ppiece);    //GoFrame                    //~1Ah0I~
            }                                                      //~1Ah0I~
    		else                                                   //~1Ah0I~
            {                                                      //~1Ah0I~
		    	if (Dump.Y) Dump.println("GtpGoFrame:at response to human move;call black()");//~1Ah0I~
				black(i,j,Ppiece);                                 //~1Ah0I~
            }                                                      //~1Ah0I~
            updateTime(color);                                     //~1Ah0I~
        	B.setCopySW(true/*do not copy*/);                      //~1AhbR~
        	B.copy();	//copy and invalidate                      //~1AhbR~
		}                                                          //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGoFrame;gotMove by human move end");//~1Ah0I~
    	if (Dump.X) Dump.println("X:GtpGoFrame;gotMove by human move end");//~1Ah0I~
	}                                                              //~1Ah0I~
////**************************************************************** //~v1B6R~//~1Ah0R~
////*from GtpFrame by compouter move                               //~1Ah0R~
////**************************************************************** //~v1B6I~//~1Ah0R~
//    public void gotSet (int color, int i, int j,int Ppiece)      //~1Ah0R~
//    {                                                            //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpGoFrame;gotSet color="+color+",piece="+Ppiece+",i="+i+",j="+j);//~1Ah0R~
////      if (color==GTPConnector.WHITE) setwhite(i,j);              //~v1B6R~//~1Ah0R~
////      else setblack(i,j);                                        //~v1B6R~//~1Ah0R~
//        if (color==Board.COLOR_WHITE)                             //~v1B6I~//~1Ah0R~
//        {                                                          //~v1B6I~//~1Ah0R~
////            updateWhiteMoves();  //reset extramove count                                   //~v1B6I~//~1Ah0R~
////            setwhite(i,j,Ppiece);                                         //~v1B6I~//~1Ah0R~
//              white(i,j,Ppiece);                                 //~1Ah0R~
//        }                                                          //~v1B6I~//~1Ah0R~
//        else                                                       //~v1B6R~//~1Ah0R~
//        {                                                          //~v1B6I~//~1Ah0R~
////            updateBlackMoves();                                     //~v1B6I~//~1Ah0R~
////            setblack(i,j,Ppiece);                                         //~v1B6I~//~1Ah0R~
//              black(i,j,Ppiece);                                 //~1Ah0R~
//        }                                                          //~v1B6I~//~1Ah0R~
//    }                                                            //~1Ah0R~
////****************************************************************//~1Ah0R~
    private void updateTime(int Pcolor)                            //~1Ah0R~
    {                                                              //~1Ah0R~
//        long now=System.currentTimeMillis();                     //~1Ah0R~
//        if (B.maincolor()>0)                                     //~1Ah0R~
//        {   BlackTime+=(int)((now-CurrentTime)/1000);            //~1Ah0R~
//        }                                                        //~1Ah0R~
//        else                                                     //~1Ah0R~
//        {   WhiteTime+=(int)((now-CurrentTime)/1000);            //~1Ah0R~
//        }                                                        //~1Ah0R~
//        CurrentTime=now;                                         //~1Ah0R~
//        alarm();                                                 //~1Ah0R~
        if (Dump.Y) Dump.println("GtpGoFrame.updateTime color="+Pcolor);//~1Ah0R~
        settimesGtpColorSwitch(Pcolor/*lastMovedColor*/);          //~1Ah0R~
    }                                                              //~1Ah0R~
//    //****************************************************************//~1Ah0R~
//    //*hfrom ConnectedBoard                                      //~1Ah0R~
//    //****************************************************************//~1Ah0R~
//    @Override //ConnectedGoFrame                                 //~1Ah0R~
//    public void notifyMoved(ActionMove Pam)                      //~1Ah0R~
//    {                                                            //~1Ah0R~
//        int iFrom=Pam.iFrom;                                     //~1Ah0R~
//        int jFrom=Pam.jFrom;                                     //~1Ah0R~
//        int iTo  =Pam.iTo  ;                                     //~1Ah0R~
//        int jTo  =Pam.jTo  ;                                     //~1Ah0R~
//        int pieceTo=Pam.pieceTo;                                 //~1Ah0R~
//        int drop=Pam.drop;                                       //~1Ah0R~
//        if (Dump.Y) Dump.println("GtpGoFrame:notifyMoved:color="+Pam.color+" ("+iFrom+","+jFrom+")-->("+iTo+","+jTo+",pieceTo="+pieceTo+",drop="+drop);//~1Ah0R~
//        PF.moveset(iTo,jTo,pieceTo,drop!=0,iFrom,jFrom);//GtpFrame.moveset//~1Ah0R~
//    }                                                            //~1Ah0R~
//    //****************************************************************//~1Ah0I~
//    //*hfrom ConnectedBoard                                      //~1Ah0I~
//    //****************************************************************//~1Ah0I~
//    @Override //ConnectedGoFrame                                 //~1Ah0I~
//    public void notifyMoved(ActionMove Pam)                      //~1Ah0I~
//    {                                                            //~1Ah0I~
//        int iFrom=Pam.iFrom;                                     //~1Ah0I~
//        int jFrom=Pam.jFrom;                                     //~1Ah0I~
//        int iTo  =Pam.iTo  ;                                     //~1Ah0I~
//        int jTo  =Pam.jTo  ;                                     //~1Ah0I~
//        int pieceTo=Pam.pieceTo;                                 //~1Ah0I~
//        int drop=Pam.drop;                                       //~1Ah0I~
//        if (Dump.Y) Dump.println("GtpGoFrame:notifyMoved:color="+Pam.color+" ("+iFrom+","+jFrom+")-->("+iTo+","+jTo+",pieceTo="+pieceTo+",drop="+drop);//~1Ah0I~
//        PF.moveset(iTo,jTo,pieceTo,drop!=0,iFrom,jFrom);//GtpFrame.moveset//~1Ah0I~
//    }                                                            //~1Ah0I~
    //****************************************************************//~1Ah0I~
    @Override //TGF                                                //~1Ah0I~
    protected Notes saveNotes(int Pcolor,String Pnotesname)                            //~1A22I~//~1A23M~//~1A24R~//~1Ah0I~
    {                                                              //~1Ah0I~
        Notes notes=super.saveNotes(Pcolor,Pnotesname);            //~1Ah0I~
        notes.swGtpGame=true;                                      //~1Ah0R~
        notes.subcmdList=goParm.subcmdList;                        //~1Ah0I~
        notes.limitMode=goParm.limitMode;                          //~1Ah0I~
        notes.limitTimeTotal=goParm.limitTimeTotal;                //~1Ah0I~
        notes.limitTimeExtra=goParm.limitTimeExtra;                //~1Ah0I~
        notes.limitTimeDepth=goParm.limitTimeDepth;                //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGoFrame:saveNotes:note.limitMode="+notes.limitMode+",total="+notes.limitTimeTotal+",extra="+notes.limitTimeExtra+",depth="+notes.limitTimeDepth);//~1Ah0I~
        return notes;
    }                                                              //~1Ah0I~
    //****************************************************************//~1Ah0I~
    @Override //TGF                                                //~1Ah0I~
    protected void restoreNotes(Notes Pnotes)                      //~1Ah0I~
    {                                                              //~1Ah0I~
        super.restoreNotes(Pnotes);                                //~1Ah0I~
        goParm.subcmdList=Pnotes.subcmdList;                       //~1Ah0I~
        goParm.limitMode=Pnotes.limitMode;                         //~1Ah0I~
        goParm.limitTimeTotal=Pnotes.limitTimeTotal;               //~1Ah0I~
        goParm.limitTimeExtra=Pnotes.limitTimeExtra;               //~1Ah0I~
        goParm.limitTimeDepth=Pnotes.limitTimeDepth;               //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGoFrame:restoreNotes:note.limitMode="+Pnotes.limitMode+",total="+Pnotes.limitTimeTotal+",extra="+Pnotes.limitTimeExtra+",depth="+Pnotes.limitTimeDepth);//~1Ah0I~
    }                                                              //~1Ah0I~
}
