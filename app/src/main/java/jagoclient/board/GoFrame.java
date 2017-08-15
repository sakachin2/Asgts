//*CID://+1Ah0R~:                             update#=  129;       //~1Ah0R~
//**************************************************************   //~v1A0I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0R~
//1A38 2013/04/22 (BUG)When response(@@!move) delayed,accept next select.//~1A38I~
//                Then move confuse to move to old dest from new selected.//~1A38I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A1e 2013/03/18 "Check" msg etc is override by showinformation msg//~1A1eI~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//**************************************************************   //~v1A0I~
package jagoclient.board;

//import jagoclient.BMPFile;                                       //~@@@@R~
import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.FreeGoFrame;
import jagoclient.dialogs.*;
import jagoclient.gui.*;
//import jagoclient.mail.MailDialog;                               //~@@@@R~

import java.io.*;
//import com.Asgts.awt.BorderLayout;                                //~@@@@R~//~@@@2R~
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.R;                                                //~@@@2R~
import com.Asgts.URunnable;                                        //~@@@2R~
import com.Asgts.URunnableI;                                       //~@@@2R~
import com.Asgts.awt.CheckboxMenuItem;                              //~@@@@R~//~@@@2R~
//import com.Asgts.awt.Clipboard;                                   //~@@@@R~//~@@@2R~
//import com.Asgts.awt.ClipboardOwner;                              //~@@@@R~//~@@@2R~
import com.Asgts.awt.Color;                                         //~@@@@R~//~@@@2R~
//import com.Asgts.awt.FileDialog;                                  //~@@@@R~//~@@@2R~
import com.Asgts.awt.Font;                                          //~@@@@R~//~@@@2R~
import com.Asgts.awt.Frame;                                         //~@@@@R~//~@@@2R~
//import com.Asgts.awt.GridLayout;                                  //~@@@@R~//~@@@2R~
import com.Asgts.awt.FileDialogI;
import com.Asgts.awt.KeyEvent;                                      //~@@@@R~//~@@@2R~
import com.Asgts.awt.KeyListener;                                   //~@@@@R~//~@@@2R~
import com.Asgts.awt.Label;                                         //~@@@@R~//~@@@2R~
import com.Asgts.awt.MenuBar;                                       //~@@@@R~//~@@@2R~
import com.Asgts.awt.Panel;                                         //~@@@@R~//~@@@2R~
//import com.Asgts.awt.StringSelection;                             //~@@@@R~//~@@@2R~
import com.Asgts.awt.TextArea;                                    //~@@@@R~//~@@@2R~
import com.Asgts.awt.TextField;                                     //~@@@@R~//~@@@2R~

//import rene.gui.IconBar;                                         //~1221R~
//import com.Asgts.rene.gui.IconBar;                                //~1221I~//~@@@@R~//~@@@2R~
//import rene.gui.IconBarListener;                                 //~@@@@R~
//import rene.util.FileName;                                       //~@@@@R~
import static jagoclient.board.Field.*;                            //~v1A0I~
                                                                   //~1116I~
                                                            //~1116I~
//import rene.util.xml.XmlReader;                                  //~@@@@R~
//import rene.util.xml.XmlReaderException;                         //~@@@@R~
                                                            //~1116I~

/**
Display a dialog to edit game information.
*/

class EditInformation extends CloseDialog
{	Node N;
	TextField Black,White,BlackRank,WhiteRank,Date,Time,
		Komi,Result,Handicap,GameName;
	GoFrame F;
	public EditInformation (GoFrame f, Node n)
	{	super(f,Global.resourceString("Game_Information"),false);
		N=n; F=f;
		show();
	}
	public void doAction (String o)
//  {	Global.notewindow(this,"editinformation");                 //~@@@@R~
    {                                                              //~@@@@I~
//  	Global.notewindow(this,"editinformation");                 //~@@@@I~
		if (Global.resourceString("OK").equals(o))
//        {   N.setaction("GN",GameName.getText());                //~v1A0R~
//            N.setaction("PB",Black.getText());                   //~v1A0R~
//            N.setaction("PW",White.getText());                   //~v1A0R~
//            N.setaction("BR",BlackRank.getText());               //~v1A0R~
//            N.setaction("WR",WhiteRank.getText());               //~v1A0R~
//            N.setaction("DT",Date.getText());                    //~v1A0R~
//            N.setaction("TM",Time.getText());                    //~v1A0R~
//            N.setaction("KM",Komi.getText());                    //~v1A0R~
//            N.setaction("RE",Result.getText());                  //~v1A0R~
//            N.setaction("HA",Handicap.getText());                //~v1A0R~
        {   N.setaction("GN",GameName.getText(),0);                //~v1A0I~
            N.setaction("PB",Black.getText(),0);                   //~v1A0I~
            N.setaction("PW",White.getText(),0);                   //~v1A0I~
            N.setaction("BR",BlackRank.getText(),0);               //~v1A0I~
            N.setaction("WR",WhiteRank.getText(),0);               //~v1A0I~
            N.setaction("DT",Date.getText(),0);                    //~v1A0I~
            N.setaction("TM",Time.getText(),0);                    //~v1A0I~
            N.setaction("KM",Komi.getText(),0);                    //~v1A0I~
            N.setaction("RE",Result.getText(),0);                  //~v1A0I~
            N.setaction("HA",Handicap.getText(),0);                //~v1A0I~
			if (!GameName.getText().equals(""))
				F.setTitle(GameName.getText());
		}
		setVisible(false); dispose();
	}
}

/**
A dialog to get the present encoding.
*/


/**
Ask the user for permission to close the board frame.
*/

class CloseQuestion extends Question
{	GoFrame GF;
	boolean Result=false;
	public CloseQuestion (GoFrame g)
//  {	super(g,Global.resourceString("Really_trash_this_board_"), //~@@@2R~
    {                                                              //~@@@2I~
    	super(g,AG.resource.getString(R.string.Really_trash_this_board),//~@@@2I~
		AG.resource.getString(R.string.Title_Close_Board),g,true,false/*no reshedsule but callback doResultCloseQuestion()*/);//~@@@2R~
		GF=g;
		show();
	}
	public void tell (Question q, Object o, boolean f)
	{	q.setVisible(false); q.dispose();
		Result=f;
        GF.doResultCloseQuestion(Result);                          //~@@@2I~
	}
}

/**
The GoFrame class is a frame, which contains the board,
the comment window and the navigation buttons (at least).
<P>
This class implements BoardInterface. This is done to make clear
what routines are called from the board and to give the board a
beans appearance.
<P>
The layout is a  panel of class BoardCommentPanel, containing two panels
for the board (BoardPanel) and for the comments (plus the ExtraSendField
in ConnectedGoFrame). Below is a 3D panel for the buttons. The BoardCommentPanel
takes care of the layout for its components.
<P>
This class handles all actions in it, besides the mouse actions on the
board, which are handled by Board.
<P>
Note that the Board class modifies the appearance of buttons and
takes care of the comment window, the next move label and the board
position label.
<P>
Several private classes in GoFrame.java contain dialogs to enter game
information, copyright, text marks, etc.
@see jagoclient.board.Board
*/

// The parent class for a frame containing the board, navigation buttons
// and menus.
// This board has a constructor, which initiates menus to be used as a local
// board. For Partner of IGS games there is the ConnectedGoFrame child, which
// uses another menu structure.
// Furthermore, it has methods to handle lots of user actions.
public class GoFrame extends CloseFrame
//  implements FilenameFilter, KeyListener, BoardInterface, ClipboardOwner,//~@@@@R~
	implements  KeyListener, BoardInterface , URunnableI
	,FileDialogI//~v1A0R~
//    IconBarListener                                                //~1221M~//~@@@@R~
//{	TextArea T; // For comments                                    //~@@@@R~
{                                                                  //~@@@@I~
//	TextArea T; // For comments                                    //~@@@@I~
	public boolean swFreeGoFrame;                                  //~1A10R~
	public Label L,Lm; // For board informations
    TextArea Comment; // For comments                            //~@@@@R~//~@@@2R~
//    String Dir; // FileDialog directory                          //~@@@@R~
	public Board B; // The board itself
	// menu check items:
//    CheckboxMenuItem SetBlack,SetWhite,Black,White,Mark,Letter,Hide,//~@@@@R~
//        Square,Cross,Circle,Triangle,TextMark;                   //~@@@@R~
	public Color BoardColor,BlackColor,BlackSparkleColor,
		WhiteColor,WhiteSparkleColor,MarkerColor,LabelColor;
	CheckboxMenuItem Coordinates,UpperLeftCoordinates,LowerRightCoordinates;
	CheckboxMenuItem PureSGF,CommentSGF,DoSound,BeepOnly,TrueColor,Alias,
		TrueColorStones,SmallerStones,MenuLastNumber,MenuTarget,Shadows,
		BlackOnly,UseXML,UseSGF;
	public boolean BWColor=false,LastNumber=false,ShowTarget=false;
	public boolean swWaitingPartnerResponse;                       //~1A38I~
	CheckboxMenuItem MenuBWColor,ShowButtons;
	CheckboxMenuItem VHide,VCurrent,VChild,VNumbers;
//    String Text=Global.getParameter("textmark","A");             //~@@@2R~
//    boolean Show;                                                //~@@@2R~
    protected boolean Show;                                        //~@@@2I~
//    TextMarkQuestion TMQ;                                        //~@@@@R~
//    IconBar IB;                                                  //~@@@@R~
	Panel ButtonP;
	String DefaultTitle="";
//    NavigationPanel Navigation;                                  //~@@@@R~

//    public GoFrame (String s)                                    //~@@@2R~
    public Notes reloadNotes;                                   //~1A24I~
    public String reloadMatchTitle;                                //~1A24I~
    public GoFrame (int Presid,String s)                           //~@@@2I~
		// For children, who set up their own menus
//  {   super(s);                                                  //~@@@2R~
    {                                                              //~@@@2I~
		super(Presid,s/*title*/);                                  //~@@@2I~
		DefaultTitle=s;
//        seticon("iboard.gif");                                   //~@@@2R~
		setcolors();
	}

	void setcolors ()
	{	// Take colors from Global parameters.
		BoardColor=Global.getColor("boardcolor",170,120,70);
		BlackColor=Global.getColor("blackcolor",30,30,30);
		BlackSparkleColor=Global.getColor("blacksparklecolor",120,120,120);
		WhiteColor=Global.getColor("whitecolor",210,210,210);
		WhiteSparkleColor=Global.getColor("whitesparklecolor",250,250,250);
		MarkerColor=Global.getColor("markercolor",Color.blue);
		LabelColor=Global.getColor("labelcolor",Color.pink.darker());
		Global.setColor("boardcolor",BoardColor);
		Global.setColor("blackcolor",BlackColor);
		Global.setColor("blacksparklecolor",BlackSparkleColor);
		Global.setColor("whitecolor",WhiteColor);
		Global.setColor("whitesparklecolor",WhiteSparkleColor);
		Global.setColor("markercolor",MarkerColor);
		Global.setColor("labelcolor",LabelColor);
	}
//************************************************************************//~@@@2I~
	public GoFrame (Frame f, String s)
    	                                     //~@@@2I~
		// Constructur for local board menus.
	{	super(s);
        swFreeGoFrame=f instanceof FreeGoFrame;                    //~1A10I~
		DefaultTitle=s;
		// Colors
		setcolors();
//        seticon("iboard.gif");                                   //~@@@2R~
//        setLayout(new BorderLayout());                           //~@@@@R~
		// Menu
		MenuBar M=new MenuBar();
		setMenuBar(M);
//        // Board                                                 //~@@@@R~
//          L=new MyLabel(Global.resourceString("New_Game"));        //~@@@@R~//~@@@2R~
//          Lm=new MyLabel("--");                                    //~@@@@R~//~@@@2R~
          L=new MyLabel(this,R.id.GameInfo);                       //~@@@2I~
          Lm=new MyLabel(this,R.id.SetStone);                      //~@@@2I~
//        B=new Board(19,this);                                    //~@@@@R~
//          B=new Board(AG.propBoardSize,this);  //boardsize was set at MainFrame//~@@@@R~//~v1A0R~
          B=new Board(AG.BOARDSIZE_SHOGI,this);  //boardsize was set at MainFrame//~v1A0I~
//          B.putInitialPiece(1/*color=Black*/,0/*bishop*/,0/*knight*/,0/*gameover*/,0/*gameover2*/,0/*gameoptions*/);//~@@@@I~//~@@@2R~
//        // Text Area                                             //~@@@@R~
//        Comment=new TextArea("",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//~@@@@R~
//        Comment=new TextArea("",R.id.Comment,0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//~@@@2R~
        Comment=new TextArea(this,"",R.id.Comment,0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);//~@@@2I~
        Comment.setFont(Global.SansSerif);                       //~@@@@R~//~@@@2R~
        Comment.setBackground(Global.gray);                      //~@@@@R~//~@@@2R~
          Show=true;                                               //~@@@@R~
          B.addKeyListener(this);                                  //~@@@@R~
//        if (Navigation!=null) Navigation.addKeyListener(B);      //~@@@@R~
//        addmenuitems();                                          //~@@@@R~
//        ButtonAction.init(this,0,R.id.Resign);  //setup doAction //~@@@2R~
//        ButtonAction.init(this,0,R.id.Help);  //setup doAction   //~@@@2R~
        new ButtonAction(this,0,R.id.Resign);  //setup doAction//~@@@2I~
        new ButtonAction(this,0,R.id.Help);  //setup doAction //~@@@2I~
		setVisible(true);
		repaint();
	}
	
	public void addmenuitems() 
		// for children to add menu items (because of bug in Linux Java 1.5)
	{
	}
	

	public void doAction (String o)
//  {	if (Global.resourceString("Undo").equals(o))               //~@@@@R~
	{                                                              //~@@@@I~
//        if (Global.resourceString("Undo").equals(o))             //~@@@@I~
//        {   B.undo();                                            //~@@@@R~
//        }                                                        //~@@@@R~
        if (o.equals(AG.resource.getString(R.string.Close)))       //~@@@2I~
		{                                                          //~@@@2I~
			close();                                               //~@@@2I~
		}                                                          //~@@@2I~
		else if (Global.resourceString("Close").equals(o))
		{	close();
		}
		else if (Global.resourceString("Score").equals(o))
		{	String s=B.done();
			if (s!=null) new Message(this,s);
		}
		else if (Global.resourceString("Local_Count").equals(o))
		{	new Message(this,B.docount());
		}
		else if (Global.resourceString("Game_Information").equals(o))
		{	new EditInformation(this,B.firstnode());
		}
//        else if (Global.resourceString("Game_Copyright").equals(o))//~@@@@R~
//        {   new EditCopyright(this,B.firstnode());               //~@@@@R~
//        }                                                        //~@@@@R~
		else if (Global.resourceString("Prisoner_Count").equals(o))
		{	String s=
			Global.resourceString("Black__")+B.Pw+
				Global.resourceString("__White__")+B.Pb+"\n"+
				Global.resourceString("Komi")+" "+B.getKomi();
			new Message(this,s);
		}
		else super.doAction(o);
	}


	public void itemAction (String o, boolean flag)
//  {	if (Global.resourceString("Save_pure_SGF").equals(o))      //~@@@@R~
	{                                                              //~@@@@I~
	}

	// This can be used to set a board position
	// The board is updated directly, if it is at the
	// last move.
	/** set a black move at i,j */
//  public void black (int i, int j)                               //~@@@2R~
    public void black (int i, int j,int Ppiece)                    //~@@@2I~
//  {   B.black(i,j);                                              //~@@@2R~
    {   B.black(i,j,Ppiece);                                       //~@@@2I~
	}
	/** set a white move at i,j */
//  public void white (int i, int j)                               //~@@@2R~
    public void white (int i, int j,int Ppiece)                    //~@@@2I~
//  {	B.white(i,j);                                              //~@@@2R~
    {	B.white(i,j,Ppiece);                                       //~@@@2I~
	}
//    /** set a black stone at i,j */                              //~v1A0R~
//    public void setblack (int i, int j)                          //~v1A0R~
//    {   B.setblack(i,j);                                         //~v1A0R~
//    }                                                            //~v1A0R~
//    /** set a black stone at i,j */                              //~v1A0R~
//    public void setwhite (int i, int j)                          //~v1A0R~
//    {   B.setwhite(i,j);                                         //~v1A0R~
//    }                                                            //~v1A0R~
	/** mark the field at i,j as territory */
	public void territory (int i, int j)
	{	B.territory(i,j);
	}
	/** Next to move */
	public void color (int c)
	{	if (c==-1) B.white();
		else B.black();
	}

	/**
	This called from the board to set the menu checks
	according to the current state.
	@param i the number of the state the Board is in.
	*/
	public void setState (int i)
	{                                                              //~1116R~
    	if (Dump.Y) Dump.println("GoFrame:setState init i="+i);      //~1506R~//~@@@2R~
//        Black.setState(false);                                     //~1116I~//~@@@@R~
//        White.setState(false);                                   //~@@@@R~
//        SetBlack.setState(false);                                //~@@@@R~
//        SetWhite.setState(false);                                //~@@@@R~
//        Mark.setState(false);                                    //~@@@@R~
//        Letter.setState(false);                                  //~@@@@R~
//        Hide.setState(false);                                    //~@@@@R~
//        Circle.setState(false);                                  //~@@@@R~
//        Cross.setState(false);                                   //~@@@@R~
//        Triangle.setState(false);                                //~@@@@R~
//        Square.setState(false);                                  //~@@@@R~
//        TextMark.setState(false);                                //~@@@@R~
//        switch (i)                                               //~@@@@R~
//        {   case 1 : Black.setState(true); break;                //~@@@@R~
//            case 2 : White.setState(true); break;                //~@@@@R~
//            case 3 : SetBlack.setState(true); break;             //~@@@@R~
//            case 4 : SetWhite.setState(true); break;             //~@@@@R~
//            case 5 : Mark.setState(true); break;                 //~@@@@R~
//            case 6 : Letter.setState(true); break;               //~@@@@R~
//            case 7 : Hide.setState(true); break;                 //~@@@@R~
//            case 10 : TextMark.setState(true); break;            //~@@@@R~
//        }                                                        //~@@@@R~
//        switch (i)                                               //~@@@@R~
//        {   case 1 : IB.setState("black",true); break;           //~@@@@R~
//            case 2 : IB.setState("white",true); break;           //~@@@@R~
//            case 3 : IB.setState("setblack",true); break;        //~@@@@R~
//            case 4 : IB.setState("setwhite",true); break;        //~@@@@R~
//            case 5 : IB.setState("mark",true); break;            //~@@@@R~
//            case 6 : IB.setState("letter",true); break;          //~@@@@R~
//            case 7 : IB.setState("delete",true); break;          //~@@@@R~
//            case 10 : IB.setState("text",true); break;           //~@@@@R~
//        }                                                        //~@@@@R~
	}

	/**
	Called from board to check the proper menu for markers.
	@param i the number of the marker type.
	*/
	public void setMarkState (int i)
	{                                                              //~1116R~
    	if (Dump.Y) Dump.println("GoFrame:setmarkState i="+i);       //~1506R~//~@@@2R~
		setState(0);                                               //~1116I~
//        switch (i)                                               //~@@@@R~
//        {   case Field.SQUARE : Square.setState(true); break;    //~@@@@R~
//            case Field.TRIANGLE : Triangle.setState(true); break;//~@@@@R~
//            case Field.CROSS : Cross.setState(true); break;      //~@@@@R~
//            case Field.CIRCLE : Circle.setState(true); break;    //~@@@@R~
//        }                                                        //~@@@@R~
//        switch (i)                                               //~@@@@R~
//        {   case Field.SQUARE : IB.setState("square",true); break;//~@@@@R~
//            case Field.TRIANGLE : IB.setState("triangle",true); break;//~@@@@R~
//            case Field.CROSS : IB.setState("mark",true); break;  //~@@@@R~
//            case Field.CIRCLE : IB.setState("circle",true); break;//~@@@@R~
//        }                                                        //~@@@@R~
	}

	/**
	Called from board to enable and disable navigation
	buttons.
	@param i the number of the button
	@param f enable/disable the button
	*/
	public void setState (int i, boolean f)
	{                                                              //~1116R~
    	if (Dump.Y) Dump.println("GoFrame:setState i="+i+"="+f);     //~1506R~//~@@@2R~
//        switch (i)                                                 //~1116I~//~@@@@R~
//        {   case 1 : IB.setEnabled("variationback",f); break;    //~@@@@R~
//            case 2 : IB.setEnabled("variationforward",f); break; //~@@@@R~
//            case 3 : IB.setEnabled("variationstart",f); break;   //~@@@@R~
//            case 4 : IB.setEnabled("main",f); break;             //~@@@@R~
//            case 5 :                                             //~@@@@R~
//                IB.setEnabled("fastforward",f);                  //~@@@@R~
//                IB.setEnabled("forward",f);                      //~@@@@R~
//                IB.setEnabled("allforward",f);                   //~@@@@R~
//                break;                                           //~@@@@R~
//            case 6 :                                             //~@@@@R~
//                IB.setEnabled("fastback",f);                     //~@@@@R~
//                IB.setEnabled("back",f);                         //~@@@@R~
//                IB.setEnabled("allback",f);                      //~@@@@R~
//                break;                                           //~@@@@R~
//            case 7 :                                             //~@@@@R~
//                IB.setEnabled("mainend",f);                      //~@@@@R~
//                IB.setEnabled("sendforward",!f);                 //~@@@@R~
//                break;                                           //~@@@@R~
//        }                                                        //~@@@@R~
	}

	/** tests, if a name is accepted as a SGF file name */
//    public boolean accept (File dir, String name)                //~@@@2R~
//    {   if (name.endsWith("."+Global.getParameter("extension","sgf"))) return true;//~@@@2R~
//        else return false;                                       //~@@@2R~
//    }                                                            //~@@@2R~

	/**
	Called from the edit marker label dialog, when its
	text has been entered by the user.
	@param s the marker to be used by the board
	*/
	void setTextmark (String s)
	{	B.textmark(s);
	}

	/** A blocked board cannot react to the user. */
	public boolean blocked ()
	{	return false;
	}

	// The following are used from external board
	// drivers to set stones, handicap etc. (like
	// distributors for IGS commands)
	/** see, if the board is already acrive */
	public void active (boolean f) {	B.active(f); }
	/** pass the Board */
//    public void pass () {   B.pass(); }                          //~@@@@R~
//    public void setpass () {    B.setpass(); }                   //~@@@@R~
	/** Notify about pass */
	public void notepass () {	}
	/**
	Set a handicap to the Board.
	@param n number of stones
	*/
//    public void handicap (int n) {  B.handicap(n); }             //~v1A0R~
	/** set a move at i,j (called from Board) */
	public boolean moveset (int i, int j) {	return true; }
	/** pass (only proceeded from ConnectedGoFrame) */
	public void movepass () {	}
	/**
	Undo moves on the board (called from a distributor e.g.)
	@param n numbers of moves to undo.
	*/
//    public void undo (int n) {  B.undo(n); }                     //~@@@@R~
	/** undo (only processed from ConnectedGoFrame) */
//    public void undo () {   }                                    //~@@@@R~

	public boolean close () // try to close
//  {	if (Global.getParameter("confirmations",true))             //~@@@2R~
	{                                                              //~@@@2R~
		if ((AG.Options & AG.OPTIONS_GOFRAME_CLOSE_CONFIRM)!=0)    //~@@@2I~
		{	/*CloseQuestion CQ=*/new CloseQuestion(this);
//            if (CQ.Result)                                       //~@@@2R~
////          {   Global.notewindow(this,"board");                   //~@@@@R~//~@@@2R~
//            {                                                      //~@@@@I~//~@@@2R~
////              Global.notewindow(this,"board");                   //~@@@@R~//~@@@2R~
//                doclose();                                       //~@@@2R~
//            }                                                    //~@@@2R~
			return false;
		}
		else
//  	{	Global.notewindow(this,"board");                       //~@@@@R~
    	{                                                          //~@@@@I~
//    		Global.notewindow(this,"board");                       //~@@@@R~
			doclose();
			return false;
		}
	}
//**************************************                           //~@@@2I~
	public void doResultCloseQuestion(boolean Presult)             //~@@@2I~
	{                                                              //~@@@2I~
		if (Presult)                                               //~@@@2I~
            doclose();                                             //~@@@2I~
	}                                                              //~@@@2I~

	/**
	Called from the BoardsizeQuestion dialog.
	@param s the size of the board.
	*/
//    public void doboardsize (int s)                              //~@@@@R~
//    {   B.setsize(s);                                            //~@@@@R~
//    }                                                            //~@@@@R~

	/** called by menu action, opens a SizeQuestion dialog */
//    public void boardsize ()                                     //~@@@@R~
//    {   new SizeQuestion(this);                                  //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Determine the board size (for external purpose)
	@return the board size
	*/
//    public int getboardsize ()                                   //~@@@@R~
//    {   return B.getboardsize();                                 //~@@@@R~
//    }                                                            //~@@@@R~

	/** add a comment to the board (called from external sources) */
//    public void addComment (String s)                            //~@@@@R~
//    {   B.addcomment(s);                                         //~@@@@R~
//    }                                                            //~@@@@R~

	public void result (int b, int w) {	}

    public void yourMove (boolean notinpos) {	}

	InputStreamReader LaterLoad=null;
	boolean LaterLoadXml;
	int LaterMove=0;
	String LaterFilename="";
	boolean Activated=false;


	public void setGameTitle (String filename)
	{		String s=((Node)B.firstnode()).getaction("GN");
			if (s!=null && !s.equals("")) 
			{	setTitle(s);
			}
			else
//  		{	((Node)B.firstnode()).addaction(new Action("GN",filename));//~v1A0R~
    		{	((Node)B.firstnode()).addaction(new Action("GN",filename,0/*piece*/));//~v1A0I~
				setTitle(filename);
			}
	}


	public synchronized void activate ()
	{	Activated=true;
//        if (LaterLoad!=null)                                     //~@@@@R~
//        {   if (LaterLoadXml) doloadXml(LaterLoad);              //~@@@@R~
//            else doload(LaterLoad);                              //~@@@@R~
//        }                                                        //~@@@@R~
//        LaterLoad=null;                                          //~@@@@R~
	}

//    /** Repaint the board, when color or font changes. */        //~v1A0R~
//    public void updateall ()                                     //~v1A0R~
//    {   setcolors();                                             //~v1A0R~
//        B.updateboard();                                         //~v1A0R~
//    }                                                            //~v1A0R~

	/**
	Opens a dialog to ask for deleting of game trees. This is
	called from the Board, if the node has grandchildren.
	*/
//    public boolean askUndo ()                                    //~@@@@R~
//    {   return new AskUndoQuestion(this).Result;                 //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Called from the board, when a node is to be inserted.
	Opens a dialog asking for permission.
	*/
//    public boolean askInsert ()                                  //~@@@@R~
//    {   return new AskInsertQuestion(this).Result;               //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Sets the name of the Board (called from a Distributor)
	@see jagoclient.igs.Distributor
	*/
	public void setname (String s)
	{	B.setname(s);
	}

	/**
	Sets the name of the current board name in a dialog
	(called from the menu)
	*/
//    public void callInsert ()                                    //~@@@@R~
//    {   new NodeNameEdit(this,B.getname());                      //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Remove a group at i,j in the board.
	*/
//    public void remove (int i, int j)                            //~@@@@R~
//    {   B.remove(i,j);                                           //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Called from the board to advance the text mark.
	*/
//    public void advanceTextmark ()                               //~@@@@R~
//    {   if (TMQ!=null) TMQ.advance();                            //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Called from the board to set the comment of a board in the Comment
	text area.
	*/
//    public void setComment (String s)                            //~@@@@R~
//    {   Comment.setText(s);                                      //~@@@@R~
//        Comment.append("");                                      //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Called from the Board to read the comment text area.
	*/
//    public String getComment ()                                  //~@@@@R~
//    {   return Comment.getText();                                //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Called from outside to append something to the comment
	text area (e.g. from a Distributor).
	*/
    public void appendComment (String s)                         //~@@@@R~//~@@@2R~
    {   Comment.append(s);                                       //~@@@@R~//~@@@2R~
    }                                                            //~@@@@R~//~@@@2R~

	/**
	Called from the board to set the Label below the board.
	*/
    String oldLabelText="";                                        //~v1A0I~//~1A1eR~
    boolean swSetLabelMsg;                                         //~1A1eI~
	public void setLabel (int Pstrid)                              //~1A24I~
    {                                                              //~1A24I~
    	setLabel(AG.resource.getString(Pstrid));                   //~1A24I~
    }                                                              //~1A24I~
	public void setLabel (String s)
//  {	L.setText(s);                                              //~@@@2R~
	{                                                              //~@@@2I~
        if (Dump.Y) Dump.println("GF:setLabel(gameInfo)="+s);       //~v1A0I~
      if (L!=null)                                                 //~@@@2I~
//     if (!s.equals(oldLabelText)) //avoid override spannabletext //~v1A0I~//~1A1eR~
       if (!swSetLabelMsg)                                         //~1A1eI~
		L.setText(s);                                              //~@@@2I~
//        if (Navigation!=null) Navigation.repaint();              //~@@@@R~
        oldLabelText=s;                                            //~v1A0I~//~1A1eR~
        swSetLabelMsg=false;                                       //~1A1eI~
	}
    //************************************************************ //~v1A0I~
    //*Notice msg to Gameinfo label                                //~v1A0I~
    //************************************************************ //~v1A0I~
    private static final int NOTICE_MSG_BG=new Color(0xff,0xff,0x00).getRGB();  //yellow//~v1A0I~
    private static final int NOTICE_MSG_FG=new Color(0xff,0x00,0x00).getRGB();  //red//~v1A0R~
	public void setLabel (int Presid,boolean Pappend)              //~v1A0I~
	{                                                              //~v1A0I~
		setLabel (AG.resource.getString(Presid),Pappend);          //~v1A0I~
    }                                                              //~v1A0I~
	public void setLabel (String s,boolean Pappend)                //~v1A0I~
	{                                                              //~v1A0I~
    	String usualmsg;                                           //~v1A0I~
        int len1,len2;                                             //~v1A0I~
    //***************************                                  //~v1A0I~
        if (Dump.Y) Dump.println("GF:setLabel(gameInfo)="+s+",append="+Pappend);//~v1A0I~
      	if (L==null)                                               //~v1A0I~
        	return;                                                //~v1A0I~
        if (Pappend)                                               //~v1A0I~
			usualmsg=oldLabelText+" ";                             //~v1A0R~
        else                                                       //~v1A0I~
        	usualmsg="";                                            //~v1A0I~
        len1=usualmsg.length();                                      //~v1A0I~
        len2=s.length();                                             //~v1A0I~
        SpannableString ss=new SpannableString(usualmsg+s);         //~v1A0I~
        ss.setSpan(new BackgroundColorSpan(NOTICE_MSG_BG),len1,len1+len2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//~v1A0I~
        ss.setSpan(new ForegroundColorSpan(NOTICE_MSG_FG),len1,len1+len2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//~v1A0I~
        L.setText(ss);                                             //~v1A0I~
        if (!s.equals(""))                                        //~1A1eI~
        	swSetLabelMsg=true;	//dont override by next move msg   //~1A1eI~
	}                                                              //~v1A0I~

	/**
	Called from the board to set the label for the cursor position.
	*/
	public void setLabelM (String s)
//  {	Lm.setText(s);                                             //~@@@2R~
    {                                                              //~@@@2I~
      if (Lm!=null)                                                //~@@@2I~
    	Lm.setText(s);                                             //~@@@2I~
	}

	public boolean boardShowing () {	return Show; }
	public boolean lastNumber () {	return LastNumber; }
	public boolean showTarget () {	return ShowTarget; }
	public Color blackColor () {	return BlackColor; }
	public Color whiteColor () {	return WhiteColor; }
	public Color whiteSparkleColor () {	return WhiteSparkleColor; }
	public Color blackSparkleColor () {	return BlackSparkleColor; }
	public Color markerColor (int color)
	{	switch (color)
		{	case 1 : return MarkerColor.brighter().brighter();
			case -1 : return MarkerColor.darker().darker();
			default : return MarkerColor;
		} 
	}
	public Color boardColor () { return BoardColor; }
	public Color labelColor (int color)
	{	switch (color)
		{	case 1 : return LabelColor.brighter().brighter();
			case -1 : return LabelColor.darker().darker();
			default : return LabelColor.brighter();
		} 
	}
	public boolean bwColor () {	return BWColor; }

	/**
	Process the insert key, which set the node name by opening
	the correspinding dialog.
	*/
	public void keyPressed (KeyEvent e)
//  {	if (e.isActionKey())                                       //~@@@@R~
	{                                                              //~@@@@I~
//        if (e.isActionKey())                                     //~@@@@I~
//        {   switch (e.getKeyCode())                              //~@@@@R~
//            {   case KeyEvent.VK_INSERT : callInsert(); break;   //~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~
//        else                                                     //~@@@@R~
//        {   switch (e.getKeyChar())                              //~@@@@R~
//            {   case 'f' :                                       //~@@@@R~
//                case 'F' :                                       //~@@@@R~
//                    B.search(Global.getParameter("searchstring","++")); break;//~@@@@R~
//            }                                                    //~@@@@R~
//        }                                                        //~@@@@R~
	}
	public void keyReleased (KeyEvent e) {	}
	public void keyTyped (KeyEvent e) {	}

	// interface routines for the BoardInterface

	public Color getColor (String S, int r, int g, int b)
	{	return Global.getColor(S,r,g,b);
	}

	public String resourceString (String S)
	{	return Global.resourceString(S);
	}

	public String version ()
	{	return "Version "+resourceString("Version");
	}

	public boolean getParameter (String s, boolean f)
	{	return Global.getParameter(s,f);
	}

	public Font boardFont ()
	{	return Global.BoardFont;
	}
	public Font boardFont (int Psize)                              //~@@@2I~
	{                                                              //~@@@2I~
		return Global.createfont("Bold","SansSerif",Psize);        //~@@@2R~
	}                                                              //~@@@2I~

	public Frame frame ()
	{	return Global.frame();
	}

	public boolean blackOnly ()
	{	if (BlackOnly!=null)
		return BlackOnly.getState();
		return false;
	}
	
	public Color backgroundColor ()
	{	return Global.gray;
	}
	public void canvasCallback(Object Pparm)                                   //~v1BaI~//~1Ah0I~
	{                                                              //~v1BaI~//~1Ah0I~
    //*override                                                    //~v1BaI~//~1Ah0I~
	}                                                              //~v1BaI~//~1Ah0I~
//************************************************************     //~@@@@I~
//*on UiThread                                                     //~@@@@I~
//*   if used "implements UiThreadI" all other function was intercepted//~@@@@I~
//************************************************************     //~@@@@I~
    public void changeBoard(boolean Pchangeboard)                                     //~@@@@I~//~@@@2R~//~v1A0R~
    {                                                              //~@@@@I~//~v1A0R~
//        if (Pchangeboard)                                          //~@@@2I~//~v1A0R~
//            if (AG.propBoardSize==AG.BOARDSIZE_SHOGI)                  //~@@@@R~//~@@@2R~//~v1A0R~
//            {                                                      //~@@@2R~//~v1A0R~
//                AG.propBoardSize=AG.BOARDSIZE_CHESS;                   //~@@@@R~//~@@@2R~//~v1A0R~
//            }                                                      //~@@@2R~//~v1A0R~
//            else                                                       //~@@@@I~//~@@@2R~//~v1A0R~
//            {                                                      //~@@@2R~//~v1A0R~
//                AG.propBoardSize=AG.BOARDSIZE_SHOGI;                   //~@@@@R~//~@@@2R~//~v1A0R~
//            }                                                      //~@@@2R~//~v1A0R~
//        if (AG.isMainThread())                                     //~1214M~//~@@@@I~//~@@@2R~
//            changeBoardUi();                                       //~@@@@R~//~@@@2R~
//        else                                                       //~@@@@I~//~@@@2R~
//        {                                                          //~@@@@I~//~@@@2R~
//            AG.activity.runOnUiThread(                             //~@@@@I~//~@@@2R~
//                new Runnable()                                         //~1214I~//~@@@@I~//~@@@2R~
//                {                                                      //~1214I~//~@@@@I~//~@@@2R~
//                    @Override                                          //~1214I~//~@@@@I~//~@@@2R~
//                    public void run()                                  //~1214I~//~@@@@I~//~@@@2R~
//                    {                                                  //~1214I~//~@@@@I~//~@@@2R~
//                        try                                        //~@@@@I~//~@@@2R~
//                        {                                          //~@@@@I~//~@@@2R~
//                            changeBoardUi();                       //~@@@@I~//~@@@2R~
//                        }                                          //~@@@@I~//~@@@2R~
//                        catch (Exception e)             //~1311I~  //~@@@@I~//~@@@2R~
//                        {                                          //~1311I~//~@@@@I~//~@@@2R~
//                            Dump.println(e,"changeBoardUi");//~1311I~//~@@@@I~//~@@@2R~
//                        }                                          //~1311I~//~@@@@I~//~@@@2R~
//                    }                                                  //~1214I~//~@@@@I~//~@@@2R~
//                }                                                      //~1214I~//~@@@@I~//~@@@2R~
//                                      );                               //~1214I~//~@@@@I~//~@@@2R~
//        }                                                          //~@@@@I~//~@@@2R~
        URunnable.setRunFuncDirect(this,null/*objparm*/,0/*intparm*/);            //~@@@2I~//~v1A0R~
    }                                                              //~@@@@I~//~v1A0R~
//************************************************************     //~@@@2I~//~v1A0R~
    @Override //URunnableI                                         //~@@@2I~//~v1A0R~
    public void runFunc(Object Pobj,int Pint)                                          //~@@@2I~//~v1A0R~
    {                                                              //~@@@2I~//~v1A0R~
        changeBoardUi();                                           //~@@@2I~//~v1A0R~
    }                                                              //~@@@2I~//~v1A0R~
//************************************************************     //~@@@@I~//~v1A0R~
    public void changeBoardUi()                                    //~@@@@R~//~v1A0R~
    {                                                              //~@@@@I~//~v1A0R~
        if (Dump.Y) Dump.println("changeBoardUi entry");           //~@@@2R~//~v1A0R~
        B.doUpdateboard();   //stop BoardSync thread and recycle Board bitmap//~@@@2R~//~v1A0R~
        if (Dump.Y) Dump.println("changeBoardUi return");          //~@@@2I~//~v1A0R~
    }                                                              //~@@@@I~//~v1A0R~
//************************************************************     //~@@@@I~
	public void repaint(boolean Pinital)                           //~@@@@I~
	{                                                              //~@@@@I~
        if (Dump.Y) Dump.println("GoFrame.repaint");               //+1Ah0I~
    	B.repaint();                                               //~@@@@I~
	}                                                              //~@@@@I~
//************************************************************     //~@@@2I~
	public String pieceName(int Ppiece)                            //~@@@2I~
	{                                                              //~@@@2I~
    	String n;                                                  //~v1A0I~
        if (Ppiece>=PIECE_PAWN && Ppiece<=PIECE_KING_CHALLENGING)                 //~v1A0I~
        	n=AG.pieceName[Ppiece-PIECE_PAWN];                     //~v1A0R~
        else                                                       //~v1A0I~
        if (Ppiece>=PIECE_PPAWN && Ppiece<=PIECE_PROOK)          //~v1A0I~
        	n=AG.pieceName[PIECE_KING_CHALLENGING+Ppiece-PIECE_PPAWN];//~v1A0R~
        else                                                       //~v1A0I~
        	n="";                                                  //~v1A0I~
        return n;                                                  //~v1A0I~
	}                                                              //~@@@2I~
//************************************************************     //~@@@2I~
//*LocalGoFrame/PartnerGoFrame will override                       //~@@@2I~
//************************************************************     //~@@@2I~
	public void setBlock(boolean Pblock){}                         //~@@@2I~
//  public void updateCapturedList(int Pcolor,int Ppiece){}              //~@@@2I~//~v1AeR~
    public void updateCapturedList(int Pcolor,int Ppiece,int Pcount){}//~v1AeI~
	public void gameover(int request,int Pcolor){}                 //~@@@2I~
	public void fileDialogLoaded(CloseDialog Pdlg,Notes Pnotes){}                   //~1A24I~
	public void fileDialogSaved(String Pnotename){}                //~1A24R~
	public void fileDialogNotSaved(){}//~1A24I~
}
