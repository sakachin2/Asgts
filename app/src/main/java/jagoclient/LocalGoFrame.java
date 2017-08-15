//*CID://+1Ah0R~:                                   update#=  159; //+1Ah0R~
//****************************************************************************//~@@@1I~
//1Ah6 2016/11/14 (Bug of 1A10)Localboard time is invalid(runtime is White+Black)//+1Ah0I~
//1A4q 2014/12/05 (Bug)Cancel save dose ot change to Close button enabled//~1A4qI~
//1A2i 2013/04/03 (Bug)Resig on Local board; Endgame question is End game?//~1A2iI~
//1A27 2013/03/25 File button --> Suspend button on Local/Remote board//~1A27I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A1j 2013/03/19 change Help file encoding to utf8 (path change drop jagoclient from jagoclient/helptexts)//~1A10I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0g 2013/03/06 judge checkmate                                  //~1A0gI~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//****************************************************************************//~@@@1I~
package jagoclient;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.Alert;
import com.Asgts.AlertI;
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.KeyEvent;                                      //~v107R~//~@@@@R~
import com.Asgts.awt.KeyListener;                                   //~v107R~//~@@@@R~
import jagoclient.board.TimedGoFrame;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.Message;
import jagoclient.partner.EndGameQuestion;
import jagoclient.sound.JagoSound;

/**
The go frame for partner connections.
*/

//public class LocalGoFrame extends TimedGoFrame                   //~@@@@R~
public class LocalGoFrame extends TimedGoFrame                     //~@@@@I~
	implements KeyListener                                                  //~@@@1I~
    ,AlertI                                                        //~1A00I~
{                                                                  //~@@@@R~
	LocalFrame PF;
    private String matchName;                                              //~1A22I~
//    int Handicap;                                                //~@@@@R~
	
//*********************************************************************//~@@@@I~
//from LocalGoFrame                                                //~@@@@I~
//*********************************************************************//~@@@@I~
//    public PartnerGoFrame (PartnerFrame pf, String s,            //~@@@@R~
//  	int col, int si, int tt, int et, int em, int ha)           //~@@@@R~
//  public LocalGoFrame (LocalFrame pf,                     //~@@@@M~//~1A10R~
    public LocalGoFrame (LocalFrame pf,int Ptitleid,                //~1A10I~
    	int col, int si,int tt,int et,int Pgameoptions,int Phandicap)//~@@@@R~//~v1A0R~
//  {	super(s,si,Global.resourceString("End_Game"),Global.resourceString("Count"),false,false);//~@@@@R~
    {                                                              //~@@@@I~
//        super(s,si,Global.resourceString("End_Game"),Global.resourceString("Count"),Pgameover,Pgameover2,Pbishop,Pknight,Pgameoptions,col);//~@@@@R~
//      super(pf,AG.frameId_ConnectedGoFrame,AG.resource.getString(R.string.Title_LocalViewer),//~@@@@R~//~1A10R~
        super(pf,AG.frameId_ConnectedGoFrame,AG.resource.getString(Ptitleid),//~1A10I~
			col,si,tt,et,Pgameoptions,Phandicap);//~@@@@R~         //~v1A0R~
		PF=pf;
		if (col==1)                                                //~@@@@I~
        {                                                          //~@@@@I~
        	BlackName=AG.YourName;                                 //~@@@@I~
        	WhiteName=AG.LocalOpponentName;                        //~@@@@R~
        }                                                          //~@@@@I~
        else                                                       //~@@@@I~
        {                                                          //~@@@@I~
        	WhiteName=AG.YourName;                                 //~@@@@I~
        	BlackName=AG.LocalOpponentName;                        //~@@@@R~
        }                                                          //~@@@@I~
        showFrame();	//FGF will override                        //~1A10I~
    }                                                              //~1A10I~
    private void showFrame()                                            //~1A10I~
    {                                                              //~1A10I~
		addKeyListener(this);                                      //~@@@1I~
		setVisible(true);
		repaint();
	}

	public void doAction (String o)
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.Resign))) //~@@@@I~
		{                                                          //~@@@1I~
//  		PF.endgame();                                          //~@@@1I~//~1A2iR~
    		PF.endgame(true);                                      //~1A2iI~
			return;                                                //~@@@1I~
		}                                                          //~@@@1I~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~@@@@I~
		{                                                          //~@@@@I~
        	new HelpDialog(this,R.string.HelpTitle_GoFrame,"BoardFrame");//~1A10I~
		}                                                          //~@@@@I~
		else super.doAction(o);
	}

	public boolean blocked ()
	{	return false;
	}

//*****************************************************            //~@@@@I~
	public boolean wantsmove ()
    {                                                              //~@@@@R~
    	if (Ended)                                                 //~@@@@I~
        {                                                          //~@@@@I~
	    	if (Dump.Y) Dump.println("LocalGoFrame wantsmove return true after Ended");//~@@@@I~
        	return true;	//reques callback moveset to ignore movemouse()//~@@@@I~
        }                                                          //~@@@@I~
		return false;  //no request ConnectedBoard to call CGF.moveset(i,j) to get move event//~@@@@I~
	}
//*from ConnectedBoard**************************************       //~@@@@I~
	public boolean moveset(int i,int j)                            //~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("LocalGoFrame moveset return false");//~@@@@I~
    	return false;	//nop movemouse                            //~@@@@I~
    }                                                              //~@@@@I~
//*****************************************************            //~@@@@I~
//*from ConnectedBoard:selectPiece                                 //~@@@@I~
//*****************************************************            //~@@@@I~
	@Override //ConnectedGoFrame                                   //~@@@@I~
    protected void pieceMoved(int Pcolor/*last moved color*/)      //~@@@@R~
    {                                                              //~@@@@R~
    	super.pieceMoved(Pcolor);                                  //~v1A0I~
    	settimesLocalColorSwitch(Pcolor);                          //~@@@@I~//~1A10R~//+1Ah0R~
    	JagoSound.play("piecedown",false/*not change to beep when beeponly option is on*/);//~@@@@I~
    }                                                              //~@@@@I~
	@Override //ConnectedGoFrame                                   //~@@@@I~
    protected void pieceSelected(int Pi,int Pj,int Pcolor)         //~@@@@R~//~v1A0R~
    {                                                              //~@@@@I~
    	super.pieceSelected(Pi,Pj,Pcolor);	//reset Capturedlist selection//~v1A0I~
    	JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~@@@@I~
    }                                                              //~@@@@I~
//*****************************************************            //~@@@@I~
	public void doclose ()
	{	setVisible(false); dispose();
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


	public void yourMove (boolean notinpos)
    {                                                              //~@@@@I~
        JagoSound.play("stone","click",true);                      //~@@@@I~
    }                                                              //~@@@@I~
//*FunctionKey support                                             //~@@@1I~
	public void keyPressed (KeyEvent e) {}                         //~@@@1I~
	public void keyTyped (KeyEvent e) {}                           //~@@@1I~
	public void keyReleased (KeyEvent e)                           //~@@@1I~
	{                                                              //~@@@@I~
	}                                                              //~@@@1I~
//************************************************************************//~@@@@I~
	protected void start ()                                        //~@@@@I~
    {                                                              //~@@@@I~
    	super.start();                                             //~@@@@I~
    }                                                              //~@@@@I~
//************************************************************************//~@@@@I~
	@Override                                                      //~@@@@I~
	protected void errPass()                                       //~@@@@I~
    {                                                              //~@@@@I~
		B.setpass();                                               //~@@@@I~
    }                                                              //~@@@@I~
//************************************************************************//~1A0gR~
	@Override	//CGF                                              //~1A0gR~
	public void errCheckmate(int Pcolor/*winner*/)	           //~1A0gR~
    {                                                              //~1A0gR~
    	checkmateQuestion(this,Pcolor);                            //~1A0gR~
    }                                                              //~1A0gR~
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
    	if (Dump.Y) Dump.println("LGF saveFile");                  //~1A22R~
		new EndGameQuestion(this);                                 //~1A22I~
	}                                                              //~1A22I~
//************************************************************************//~1A27I~
	@Override //TGF                                                //~1A27I~
	public void fileDialogSaved(String Pnotesname)                 //~1A27I~
	{                                                              //~1A27I~
    	super.fileDialogSaved(Pnotesname);                         //~1A27I~
        infoGameSaved(Pnotesname);                                 //~1A27I~
	}                                                              //~1A27I~
//************************************************************************//~1A4qI~
	@Override //TGF                                                //~1A4qI~
	public void fileDialogNotSaved()                               //~1A4qI~
	{                                                              //~1A4qI~
    	fileNotSaved();	//TGF                                      //~1A4qI~
	}                                                              //~1A4qI~
//************************************************************************//~1A27I~
	@Override //TGF                                                //~1A27I~
    protected void saveGameover()                                  //~1A27I~
    {                                                              //~1A27I~
    	if (Dump.Y) Dump.println("LGF saveGameover");              //~1A27I~
		saveGame(null);	//TGF                                          //~1A27I~
	}                                                              //~1A27I~
}

