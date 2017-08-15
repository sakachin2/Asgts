//*CID://+1Ah1R~:                                   update#=  349; //+1Ah1R~
//****************************************************************************//~@@@1I~
//1Ah1*2016/10/25 TotalTime may be 0 with extratime!=0             //+1Ah1I~
//1Aey 2016/07/02 (Bug)NPE by FreeBoard from Clipboard when Dump.Y //~1AeyI~
//1A4C 2014/12/10 Freeboard->FileDialog; change filedialog title from Reopen-->File for Freeboard//~1A4CI~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//1A4s 2014/12/06 utilize clipboard(view dialog)                   //~1A4sI~
//1A4k 2014/12/04 memory leak:DAL_filemenu is continue to refer FGF after FGF closed(DAL_playmenu is MainFrame)//~1A4kI~
//1A4b 2014/11/29 issue msg also when freeboard saved snapshot     //~1A4bI~
//1A33 2013/04/17 load tsumego file on freeboard                   //~1A33I~
//1A32 2013/04/17 chaneg to open FreeBoardQuestion at start button on FreeGoFrame//~1A32I~
//1A31 2013/04/16 (BUG)title of ReplayBoard is of FreeBoard after replayLast//~1A31I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30M~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A2a 2013/03/25 FreeBoard:boardname to filename                  //~1A2aI~
//1A26 2013/03/25 save folder fix option(no dislog popup for save game)//~1A26I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A21 2013/03/22 File Dialog on Free Board                        //~1A21I~
//1A1i 2013/03/18 FreeBoard drop:"checkmate?" button               //~1A1iI~
//1A1f 2013/03/18 appiear tray selected at interrupt               //~1A1fI~
//1A1b 2013/03/13 FreeBoard:multiple snapshot                      //~1A1bI~
//1A1a 2013/03/13 FreeBoard:start W/B button                       //~1A1aI~
//1A19 2013/03/13 CloseQuestion when Close button on FreeGoFrame   //~1A19I~
//1A18 2013/03/13 FreeBoard:show Black timer only                  //~1A18I~
//1A17 2013/03/12 slide for piece remove on freeboard              //~1A17I~
//1A15 2013/03/10 Checkmate button for freeboard                   //~1A15I~
//1A14 2013/03/10 FreeBoard Title                                  //~1A14I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0g 2013/03/06 judge checkmate                                  //~1A0gI~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//****************************************************************************//~@@@1I~
package jagoclient;

import rene.util.list.ListClass;
import android.view.View;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.Alert;
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.Button;
import com.Asgts.awt.Component;
import com.Asgts.awt.FileDialog;
import com.Asgts.awt.FileDialogClipboard;
import com.Asgts.awt.KeyListener;                                   //~v107R~//~@@@@R~

import jagoclient.board.FreeBoard;
import jagoclient.board.Notes;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.partner.GameQuestion;
import jagoclient.sound.JagoSound;

/**
The go frame for partner connections.
*/

public class FreeGoFrame extends LocalGoFrame                      //~1A10R~
	implements KeyListener                                                  //~@@@1I~
{                                                                  //~@@@@R~
	FreeFrame PF;                                             //~1A10R~
	public FreeBoard FB;                                                  //~1A10I~
//  int Handicap;                                                //~@@@@R~
//  private ButtonAction StudyStartB,StudyStartW,StudyStop,StudyClose;//~1A10R~//~1A1aR~//~1A21R~
    protected ButtonAction StudyStartB,StudyStartW,StudyStop,StudyFile;//~1A21I~//~1A2aR~//~1A32R~
    protected	ButtonAction ButtonFile;                           //~1A26I~
    private boolean swSmall=AG.portrait;	//se small button if portrait and small pixel device//~1A10R~
    private boolean swInterrupted;
    private boolean swFBQ;                                         //~1A32I~
    private long StartTime;//~1A10I~
    private int totalBlackRun;                                     //~1A10I~
    public String boardName="";                                    //~1A10I~
//    public Notes note;                                              //~1A10I~//~1A1bR~
    private ListClass notesList=new ListClass();                   //~1A1bR~
    public Notes lastNotes;                                        //~1A1bR~
    public Notes lastNotesSaved;                                   //~1A1bI~
    public Notes replayNotes;                                      //~1A2dI~
    private FileDialogClipboard fdc;                               //~1A4sI~
//*********************************************************************//~1A2dI~
//from ReplayGoFrame                                               //~1A2dI~
//*********************************************************************//~1A2dI~
	public FreeGoFrame (FreeFrame pf,int col,Notes Pnotes)         //~1A2dI~
    {                                                              //~1A2dI~
        super(pf,R.string.Title_ReplayGoFrame,                            //~1A2dI~
			col,AG.BOARDSIZE_SHOGI,Pnotes.totalTime,Pnotes.extraTime,Pnotes.gameoptions|GameQuestion.GAMEOPT_FREEBOARD,Pnotes.handicap);//~1A2dI~
		PF=pf;                                                     //~1A2dI~
        FB=(FreeBoard)B;                                           //~1A2dI~
        replayNotes=Pnotes;                                        //~1A2dM~
		showFrame();                                               //~1A2dI~
    }                                                              //~1A2dI~
//*********************************************************************//~@@@@I~
//from FreeGoFrame                                                //~@@@@I~//~1A10R~
//*********************************************************************//~@@@@I~
	public FreeGoFrame (FreeFrame pf,                     //~@@@@M~//~1A10R~
    	int col, int si,int tt,int Pgameoptions)//~@@@@R~//~v1A0R~ //~1A10R~
    {                                                              //~@@@@I~
        super(pf,R.string.Title_FreeGoFrame, //~@@@@R~              //~1A10R~
			col,si,tt,0/*et*/,Pgameoptions|GameQuestion.GAMEOPT_FREEBOARD,0/*Phandicap*/);//~@@@@R~         //~v1A0R~//~1A10R~
		PF=pf;
        FB=(FreeBoard)B;                                           //~1A10I~
//        if (col==1)                                                //~@@@@I~//~1A10R~
//        {                                                          //~@@@@I~//~1A10R~
//            BlackName=AG.YourName;                                 //~@@@@I~//~1A10R~
//            WhiteName=AG.LocalOpponentName;                        //~@@@@R~//~1A10R~
//        }                                                          //~@@@@I~//~1A10R~
//        else                                                       //~@@@@I~//~1A10R~
//        {                                                          //~@@@@I~//~1A10R~
//            WhiteName=AG.YourName;                                 //~@@@@I~//~1A10R~
//            BlackName=AG.LocalOpponentName;                        //~@@@@R~//~1A10R~
//        }                                                          //~@@@@I~//~1A10R~
		showFrame();                                                //~1A10I~
    }                                                              //~1A10I~
    protected void showFrame()                                       //~1A10I~//~1A2dR~
    {                                                              //~1A10I~
    	if (PF.boardName!=null)                                    //~1A14R~
        {                                                          //~1A14R~
        	boardName=PF.boardName;                                //~1A14R~
            setTitle(null/*notes*/,boardName);                     //~1A24I~
        }                                                          //~1A14R~
   		setupButtons();                                            //~1A10R~
// 		setButtonsFreeMode();                                      //~1A10R~
    	setButtonsPrepareStudy();                                  //~1A10I~
		addKeyListener(this);                                      //~@@@1I~
		setVisible(true);
		repaint();
	}
    //***********************************                          //~1A10I~
    private void setupButtons()                                    //~1A10R~
    {                                                              //~1A10I~
//      StudyClose=new ButtonAction(this,0,R.id.Close,swSmall/*smallbutton*/);//~@@@2I~//~1A10R~//~1A21R~
        StudyFile=new ButtonAction(this,0,R.id.File,swSmall/*smallbutton*/);//~1A21I~
        StudyStartB=new ButtonAction(this,0,R.id.StudyStartB,swSmall/*smallbutton*/);//~1A10R~//~1A1aR~//~1A32R~
        StudyStartW=new ButtonAction(this,0,R.id.StudyStartW,swSmall/*smallbutton*/);//~1A1aI~//~1A32R~
        StudyStop=new ButtonAction(this,0,R.id.StudyStop,swSmall/*smallbutton*/);//~1A10I~
        setupFreeBoardImageButtons();                               //~1A2dI~
        new ButtonAction(this,0,R.id.StudyClearCaptureB);          //~1A10I~
        new ButtonAction(this,0,R.id.StudyClearCaptureW);          //~1A10R~
        new ButtonAction(this,0,R.id.StudyRestore);                //~1A10I~
//      new ButtonAction(this,0,R.id.StudyCheckmate);              //~1A10I~//~1A1iR~
        new ButtonAction(this,0,R.id.StudyRestoreNext);            //~1A1bI~
        new ButtonAction(this,0,R.id.StudyRestorePrev);            //~1A1bI~
        new ButtonAction(this,0,R.id.StudySave);            //~1A1bI~
                                                                   //~1A10I~
        ButtonResign.setVisibility(View.GONE);		//CGF          //~1A10M~
        View v=findViewById(R.id.CommentPanelContainer);                         //~1A10I~//~1A1aM~//~1A1bR~
        setVisibility(v,View.GONE);                                //~1A10I~//~1A1aM~
    }                                                              //~1A10I~
    protected void setupFreeBoardImageButtons()                    //~1A2dI~
    {                                                              //~1A2dI~
        new ButtonAction(this,0,R.id.StudyInit);                   //~1A2dI~
        new ButtonAction(this,0,R.id.StudyClear);                  //~1A2dI~
        new ButtonAction(this,0,R.id.StudyClearAllToW);            //~1A2dI~
        new ButtonAction(this,0,R.id.StudyBlackWhite);             //~1A2dI~
        new ButtonAction(this,0,R.id.StudyPromote);                //~1A2dI~
        new ButtonAction(this,0,R.id.StudyDelete);                 //~1A2dI~
    }                                                              //~1A2dI~
    //***********************************                          //~1A10I~
    private void setButtonsPrepareStudy()                          //~1A10I~
    {                                                              //~1A10I~
        View v;                                                    //~1A10I~
    //****************************                                 //~1A10I~
//      StudyClose.setVisibility(View.VISIBLE);                    //~1A10I~//~1A21R~
        StudyFile.setVisibility(View.VISIBLE);                     //~1A21I~//~1A22R~//~1A33R~
        StudyStartW.setVisibility(View.VISIBLE);                    //~1A10I~//~1A1aR~//~1A32R~
        StudyStartB.setVisibility(View.VISIBLE);                   //~1A1aI~//~1A32R~
        StudyStop.setVisibility(View.GONE);                        //~1A10I~
        ButtonSuspend.setVisibility(View.GONE);                    //~1A26I~
        PromotionButton.setVisibility(View.GONE);	//CGF          //~1A10M~
    //*image button                                                //~1A10I~
        v=findViewById(R.id.StudyBoardImageButtons);               //~1A10M~
        setVisibility(v,View.VISIBLE);                             //~1A10M~
        v=findViewById(R.id.StudyBoardImageButtonsStart);          //~1A10I~
        setVisibility(v,View.GONE);                                //~1A10I~
        v=findViewById(R.id.StudyClearCaptureWLayout);             //~1A10I~
        setVisibility(v,View.VISIBLE);                             //~1A10I~
        v=findViewById(R.id.StudyClearCaptureBLayout);             //~1A10I~
        setVisibility(v,View.VISIBLE);                             //~1A10I~
                                                                   //~1A10I~
        aCapturedList.initFreeBoardTray();                         //~1A10I~
    }                                                              //~1A10I~
    //***********************************                          //~1A10I~
    private void setButtonsStudyStart()                            //~1A10I~
    {                                                              //~1A10I~
        View v;                                                    //~1A10I~
    //****************************                                 //~1A10I~
//      StudyClose.setVisibility(View.GONE);                       //~1A10I~//~1A21R~
        StudyFile.setVisibility(View.GONE);                        //~1A33I~
        StudyStartW.setVisibility(View.GONE);                       //~1A10I~//~1A1aR~//~1A32R~
        StudyStartB.setVisibility(View.GONE);                      //~1A1aI~//~1A32R~
        StudyStop.setVisibility(View.VISIBLE);                     //~1A10I~
        PromotionButton.setVisibility(View.VISIBLE);	//CGF      //~1A10I~
        v=findViewById(R.id.StudyBoardImageButtons);               //~1A10I~
        setVisibility(v,View.GONE);                                //~1A10I~
        v=findViewById(R.id.StudyBoardImageButtonsStart);          //~1A10I~
        setVisibility(v,View.VISIBLE);                             //~1A10I~
        v=findViewById(R.id.StudyClearCaptureWLayout);             //~1A10I~
        setVisibility(v,View.GONE);                                //~1A10I~
        v=findViewById(R.id.StudyClearCaptureBLayout);             //~1A10I~
        setVisibility(v,View.GONE);                                //~1A10I~
        aCapturedList.setTrayVisible(false/*invisible if ctr=0*/); //~1A10I~
    }                                                              //~1A10I~
//************************************************************************//~1A10I~
//*initial call after created                                      //~1A10I~
//************************************************************************//~1A10I~
    protected void start ()                                        //~1A10I~
    {                                                              //~1A10I~
//      aCapturedList.initFreeBoardTray();  //after captured list height determind//~1A10R~
    }                                                              //~1A10I~
    //***********************************                          //~1A10I~
	public void doAction (String o)
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.Resign))) //~@@@@I~
		{                                                          //~@@@1I~
		}                                                          //~@@@1I~
        else if (o.equals(AG.resource.getString(R.string.StudyStartW)))//~1A10I~//~1A1aR~//~1A32R~
        {                                                          //~1A10I~//~1A32R~
            clearLabel();                                          //~1A10I~//~1A32R~
            studyStart(-1);                                          //~1A10I~//~1A1aR~//~1A32R~
        }                                                          //~1A10I~//~1A32R~
        else if (o.equals(AG.resource.getString(R.string.StudyStartB)))//~1A1aI~//~1A32R~
        {                                                          //~1A1aI~//~1A32R~
            clearLabel();                                          //~1A1aI~//~1A32R~
            studyStart(1);                                         //~1A1aI~//~1A32R~
        }                                                          //~1A1aI~//~1A32R~
        else if (o.equals(AG.resource.getString(R.string.StudyRestore)))//~1A10I~
		{                                                          //~1A10I~
			clearLabel();                                          //~1A10I~
        	studyRestore(0);                                        //~1A10I~//~1A1bR~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyRestorePrev)))//~1A1bI~
		{                                                          //~1A1bI~
			clearLabel();                                          //~1A1bI~
        	studyRestore(-1);                                      //~1A1bI~
		}                                                          //~1A1bI~
        else if (o.equals(AG.resource.getString(R.string.StudyRestoreNext)))//~1A1bI~
		{                                                          //~1A1bI~
			clearLabel();                                          //~1A1bI~
        	studyRestore(+1);                                      //~1A1bI~
		}                                                          //~1A1bI~
        else if (o.equals(AG.resource.getString(R.string.StudySave)))//~1A1bI~
		{                                                          //~1A1bI~
			clearLabel();                                          //~1A1bI~
        	studySave();                                           //~1A1bI~
		}                                                          //~1A1bI~
//        else if (o.equals(AG.resource.getString(R.string.StudyCheckmate)))//~1A15I~//~1A1iR~
//        {                                                          //~1A15I~//~1A1iR~
//            studyCheckmate();                                      //~1A15I~//~1A1iR~
//        }                                                          //~1A15I~//~1A1iR~
        else if (o.equals(AG.resource.getString(R.string.StudyStop)))//~1A10I~
		{                                                          //~1A10I~
			clearLabel();                                          //~1A10I~
        	studyStop();                                           //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyBlackWhite)))//~1A10I~
		{                                                          //~1A10I~
        	switchBlackWhite();                                    //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyPromote)))//~1A10I~
		{                                                          //~1A10I~
        	switchPromote();                                       //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyDelete)))//~1A10I~
		{                                                          //~1A10I~
        	deletePiece(true/*sound*/);                                         //~1A10I~//~1A1bR~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyInit)))//~1A10I~
		{                                                          //~1A10I~
			clearLabel();                                          //~1A10I~
        	setInitialPieces();                                    //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyClear)))//~1A10I~
		{                                                          //~1A10I~
			clearLabel();                                          //~1A10I~
        	int btnid=Button.SbuttonActionResId;                   //~1A10I~
        	if (Dump.Y) Dump.println("FreeGoFrame doaction clear resid="+Integer.toHexString(btnid));//~1A10I~
            if (btnid==R.id.StudyClear)                            //~1A10I~
	        	clearBoard();                                      //~1A10R~
            else                                                   //~1A10I~
            if (btnid==R.id.StudyClearCaptureB)	//When Button text string is "Clear"//~1A10R~
	        	clearTray(1);                                      //~1A10I~
            else                                                   //~1A10I~
            if (btnid==R.id.StudyClearCaptureW)                    //~1A10I~
	        	clearTray(-1);                                     //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyClearCaptureB)))//~1A10I~
		{                                                          //~1A10I~
	        clearTray(1);                                          //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyClearCaptureW)))//~1A10I~
		{                                                          //~1A10I~
	        clearTray(-1);                                         //~1A10I~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.StudyClearAllToW)))//~1A10I~
		{                                                          //~1A10I~
			clearLabel();                                          //~1A10I~
        	clearToWhiteTray();                                    //~1A10R~
		}                                                          //~1A10I~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~@@@@I~
		{                                                          //~@@@@I~
        	new HelpDialog(this,R.string.HelpTitle_FreeBoard,"FreeBoard");//~1A00R~//~1A10R~
		}                                                          //~@@@@I~
        else if (o.equals(AG.resource.getString(R.string.Close)))  //~1A19I~
		{                                                          //~1A19I~
        	studyClose(o);                                         //~1A19I~
		}                                                          //~1A19I~
        else if (o.equals(AG.resource.getString(R.string.File)))   //~1A21I~
		{                                                          //~1A21I~
        	studyFile();                                           //~1A21R~
		}                                                          //~1A21I~
        else if (o.equals(AG.resource.getString(R.string.LoadFile)))//from Amenu//~1A21R~
		{                                                          //~1A21I~
        	loadFile();                                            //~1A21I~
		}                                                          //~1A21I~
        else if (o.equals(AG.resource.getString(R.string.LoadNotesFile)))//from Amenu//~1A33I~
		{                                                          //~1A33I~
        	loadNotesFile();                                       //~1A33I~
		}                                                          //~1A33I~
        else if (o.equals(AG.resource.getString(R.string.LoadNotesFileClipboard)))//from Amenu//~1A4sI~
		{                                                          //~1A4sI~
        	loadNotesFileClipboard();                              //~1A4sI~
		}                                                          //~1A4sI~
//        else if (o.equals(AG.resource.getString(R.string.ActionFileLoad)))//from FileDialog at dismiss(Open)//~1A21I~//~1A22R~
//        {                                                          //~1A21I~//~1A22R~
//            loadFileNotes();                                       //~1A21I~//~1A22R~
//        }                                                          //~1A21I~//~1A22R~
        else if (o.equals(AG.resource.getString(R.string.SaveFile)))//~1A21I~
		{                                                          //~1A21I~
        	saveFile();                                            //~1A21I~
		}                                                          //~1A21I~
//        else if (o.equals(AG.resource.getString(R.string.ActionFileSaved)))//~1A22R~
//        {                                                        //~1A22R~
//        }                                                        //~1A22R~
        else if (o.equals(AG.resource.getString(R.string.CloseBoard)))//~1A21I~
		{                                                          //~1A21I~
        	studyClose(AG.resource.getString(R.string.Close));     //~1A21I~
		}                                                          //~1A21I~
		else super.doAction(o);
	}

//    public boolean blocked ()                                    //~1A10R~
//    {   return false;                                            //~1A10R~
//    }                                                            //~1A10R~

////*****************************************************            //~@@@@I~//~1A10R~
//    public boolean wantsmove ()                                  //~1A10R~
//    {                                                              //~@@@@R~//~1A10R~
//        if (Ended)                                                 //~@@@@I~//~1A10R~
//        {                                                          //~@@@@I~//~1A10R~
//            if (Dump.Y) Dump.println("FreeGoFrame wantsmove return true after Ended");//~@@@@I~//~1A10R~
//            return true;    //reques callback moveset to ignore movemouse()//~@@@@I~//~1A10R~
//        }                                                          //~@@@@I~//~1A10R~
//        return false;  //no request ConnectedBoard to call CGF.moveset(i,j) to get move event//~@@@@I~//~1A10R~
//    }                                                            //~1A10R~
////*from ConnectedBoard**************************************       //~@@@@I~//~1A10R~
//    public boolean moveset(int i,int j)                            //~@@@@I~//~1A10R~
//    {                                                              //~@@@@I~//~1A10R~
//        if (Dump.Y) Dump.println("FreeGoFrame moveset return false");//~@@@@I~//~1A10R~
//        return false;   //nop movemouse                            //~@@@@I~//~1A10R~
//    }                                                              //~@@@@I~//~1A10R~
//*****************************************************            //~@@@@I~//~1A10R~
//*from ConnectedBoard:selectPiece                                 //~@@@@I~//~1A10R~
//*****************************************************            //~@@@@I~//~1A10R~
    @Override //LGF                                                //~1A10R~
    protected void pieceMoved(int Pcolor/*last moved color*/)      //~@@@@R~//~1A10R~
    {                                                              //~@@@@R~//~1A10R~
	    clearLabel();                                              //~1A1aI~
    	if (!FB.isIdleFreeBoard())                                 //~1A10R~
        {                                                          //~1A10I~
        	super.pieceMoved(Pcolor);                                  //~v1A0I~//~1A10R~
            return;                                                //~1A10I~
        }                                                          //~1A10I~
//      settimesLocalColorSwitch(Pcolor);                          //~@@@@I~//~1A10R~
//      JagoSound.play("piecedown",false/*not change to beep when beeponly option is on*/);//~@@@@I~//~1A10R~
        clickSound();                                              //~1A10I~
    }                                                              //~@@@@I~//~1A10R~
//*****************************************************            //~1A15I~
    @Override //LGF                                                //~1A10R~
    protected void pieceSelected(int Pi,int Pj,int Pcolor)         //~@@@@R~//~v1A0R~//~1A10R~
    {                                                              //~@@@@I~//~1A10R~
	    clearLabel();                                              //~1A1aI~
   		if (!FB.isIdleFreeBoard())                                 //~1A15R~
        {                                                          //~1A10I~
        	super.pieceSelected(Pi,Pj,Pcolor);  //reset Capturedlist selection and sound//~1A15R~
            return;                                                //~1A10I~
        }                                                          //~1A10I~
//      JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~@@@@I~//~1A10I~
		aCapturedList.reset(Pcolor);// reset capturedlist selection//~v1A0R~//~1A15I~
        clickSound();                                              //~1A10R~
    }                                                              //~@@@@I~//~1A10R~
////*****************************************************            //~@@@@I~//~1A10R~
//    public void doclose ()                                       //~1A10R~
//    {   setVisible(false); dispose();                            //~1A10R~
//        if (Timer!=null && Timer.isAlive()) Timer.stopit();        //~v107I~//~1A10R~
//        PF.toFront();                                            //~1A10R~
//        PF.boardclosed(this);                                    //~1A10R~
//        PF.PGF=null;                                             //~1A10R~
//    }                                                            //~1A10R~
//*****************************************************            //~1A4kI~
    public void doclose ()                                         //~1A4kI~
    {                                                              //~1A4kI~
    	if (Dump.Y) Dump.println("FGF doclose");                   //~1A4kI~
      	AG.aMenu.fileMenuReset();                              //~1A4kR~
        super.doclose();                                           //~1A4kI~
    }                                                              //~1A4kI~
////********************************************************         //~@@@@I~//~1A10R~
////*from PartnerFrame:adjourn                                       //~@@@@I~//~1A10R~
////********************************************************         //~@@@@I~//~1A10R~
//    public void acceptClosed()                                     //~@@@@I~//~1A10R~
//    {   setVisible(false); dispose();                              //~@@@@I~//~1A10R~
//        if (Timer!=null && Timer.isAlive()) Timer.stopit();        //~@@@@I~//~1A10R~
//        PF.toFront();                                              //~@@@@I~//~1A10R~
//        PF.PGF=null;                                               //~@@@@I~//~1A10R~
//    }                                                              //~@@@@I~//~1A10R~

//    int maincolor ()                                             //~1A10R~
//    {   return B.maincolor();                                    //~1A10R~
//    }                                                            //~1A10R~


//    public void yourMove (boolean notinpos)                      //~1A10R~
//    {                                                              //~@@@@I~//~1A10R~
//        JagoSound.play("stone","click",true);                      //~@@@@I~//~1A10R~
//    }                                                              //~@@@@I~//~1A10R~
////*FunctionKey support                                             //~@@@1I~//~1A10R~
//    public void keyPressed (KeyEvent e) {}                         //~@@@1I~//~1A10R~
//    public void keyTyped (KeyEvent e) {}                           //~@@@1I~//~1A10R~
//    public void keyReleased (KeyEvent e)                           //~@@@1I~//~1A10R~
//    {                                                              //~@@@@I~//~1A10R~
//    }                                                              //~@@@1I~//~1A10R~
//************************************************************************//~@@@@I~//~1A10R~
    protected void startTimer()                                        //~@@@@I~//~1A10R~
    {                                                              //~@@@@I~//~1A10R~
    	if (!swInterrupted)                                        //~1A10I~
        {                                                          //~1A10I~
	        super.start();	//TimedGoFrame:start(); start timer                                             //~@@@@I~//~1A10R~
			StartTime=System.currentTimeMillis();                  //~1A10R~
        }                                                          //~1A10I~
		CurrentTime=System.currentTimeMillis();                    //~1A10M~
//        swSuspendTimer=false;       //TGF                          //~1A10M~//~1A18R~
    }                                                              //~@@@@I~//~1A10R~
////************************************************************************//~@@@@I~//~1A10R~
//    @Override                                                      //~@@@@I~//~1A10R~
//    protected void errPass()                                       //~@@@@I~//~1A10R~
//    {                                                              //~@@@@I~//~1A10R~
//        B.setpass();                                               //~@@@@I~//~1A10R~
//    }                                                              //~@@@@I~//~1A10R~
////************************************************************************//~1A0gR~//~1A10R~
//    @Override   //CGF                                              //~1A0gR~//~1A10R~
//    public void errCheckmate(int Pcolor/*winner*/)             //~1A0gR~//~1A10R~
//    {                                                              //~1A0gR~//~1A10R~
//        checkmateQuestion(this,Pcolor);                            //~1A0gR~//~1A10R~
//    }                                                              //~1A0gR~//~1A10R~
//    public int alertButtonAction(int Pbuttonid/*buttonid*/,int Pitempos/*winner color*/)//~1A0gR~//~1A10R~
//    {                                                              //~1A0gR~//~1A10R~
//        boolean agree=(Pbuttonid==Alert.BUTTON_POSITIVE);          //~1A0gR~//~1A10R~
//        super.errCheckmate(Pitempos,agree);                        //~1A0gR~//~1A10R~
//        return 0;//~1A0gR~                                       //~1A10R~
//    }                                                              //~1A0gR~//~1A10R~
//************************************************************************//~1A32I~
//*Open FreeBoardQuestion by Start Button ***********************************************************************//~1A32I~
//************************************************************************//~1A32I~
    private void boardQuestion(int Pcolor)                         //~1A32R~
    {                                                              //~1A32I~
    	if (Dump.Y) Dump.println("FGF boardQuestion");             //~1A32I~
      	new FreeBoardQuestion(this,Pcolor);                        //~1A32R~
    }                                                              //~1A32I~
//************************************************************************//~1A32I~
//*callback from FreeBoardQuestion                                 //~1A32I~
//************************************************************************//~1A32I~
    public void studyStart(int Pcolor,FreeBoardQuestion Pfbq)     //~1A32I~
    {                                                              //~1A32I~
    	if (Dump.Y) Dump.println("FGF studyStart from FBQ");       //~1A32I~
    	swFBQ=true;                                                //~1A32I~
		GameOptions=Pfbq.gameoptions|GameQuestion.GAMEOPT_FREEBOARD;//~1A32I~
		TotalTime=Pfbq.totaltime*60;                                  //~1A32I~//~1A33R~
        resetTimerOption();	//TGF                                  //~1A32I~
	    studyStart(Pcolor);                                        //~1A32I~
    }                                                              //~1A32I~
//************************************************************************//~1A32I~
    private void studyStart(int Pcolor)                                      //~1A10I~//~1A1aR~
    {                                                              //~1A10I~
    	if (Dump.Y) Dump.println("FGF Start");                     //~1A10I~
        if (!FB.aRules.chkFoulFB(Col))                                   //~1A10R~
        	return;	//some piece has count over                    //~1A10I~
        if (!aCapturedList.chkTotalPieceCtr())                    //~1A10I~
        	return;	//some piece has count over                    //~1A10I~
        if (!swFBQ)                                                //~1A32I~
        {                                                          //~1A32M~
            boardQuestion(Pcolor);                                 //~1A32M~
            return;                                                //~1A32M~
        }                                                          //~1A32M~
        MatchTitle="";//note:name                                  //~1A24I~
//      if (!swInterrupted)                                        //~1A1aI~//~1A1bR~
//          saveNotes(Pcolor,null);                                     //~1A1bR~//~1A2aR~
                                                                   //~1A33I~
      if (swInterrupted)    //not 1st start                        //~1A33I~
        saveNotes(Pcolor,boardName);                              //~1A2aI~
        setTitle(lastNotes,"/"+notesList.size());                      //~1A24I~
    	setButtonsStudyStart();                                    //~1A10I~
		aCapturedList.reset(0);//reset freeboard tray selection    //~1A1fI~
        FB.swFreeBoardStarted=true;	//Canvas                       //~1A10R~//~1A15R~
        setMainColor(Pcolor);                                      //~1A1bM~
        startTimer();	//start timer                              //~1A10R~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //***************************                                  //~1A1bI~
    private void studyStop()                                       //~1A10I~
    {                                                              //~1A10I~
    	if (Dump.Y) Dump.println("FGF Stop totalBalckRun="+totalBlackRun);//~1A10I~
    	swInterrupted=true;                                        //~1A10I~
//      setButtonsFreeMode();                                      //~1A10R~
 		setButtonsPrepareStudy();                                  //~1A10I~
//        swSuspendTimer=true;        //TGF                          //~1A10I~//~1A18R~
        totalBlackRun+=BlackRun;	                               //~1A10I~
        BlackRun=totalBlackRun;                                    //~1A10I~
		settitle();		//update bigtimer                          //~1A10I~
		aCapturedList.reset(1);//reset freeboard tray selection    //~1A1fI~
//  	aCapturedList.reset(FB.MainColor);//reset localview tray selection//~1A1fI~//~1A21R~
    	aCapturedList.reset(0);//reset localview tray selection    //~1A21I~
        FB.swFreeBoardStarted=false;	//Canvas                   //~1A10R~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //***************************                                  //~1A21I~
    private void studyRestore(int Pdest)                                    //~1A10I~//~1A1bR~
    {                                                              //~1A10I~
        Notes notes=null;                                          //~1A1bI~
	    String msg;                                                //~1A1bI~
        int seq=1;                                                 //~1A1bI~
        int sz=notesList.size();                                   //~1A1bI~
    	if (Dump.Y) Dump.println("FGF Restore dest="+Pdest+",ctr="+sz);                     //~1A10I~//~1A15R~//~1A1bR~
        if (Pdest==0)                                              //~1A1bI~
        	notes=Notes.findFirst(notesList);                      //~1A1bR~
        else                                                       //~1A1bI~
        if (Pdest<0)                                               //~1A1bI~
        {                                                          //~1A1bI~
        	if (lastNotes!=null)                                         //~1A1bI~
            	if (lastNotes.moves==0)	//no move in this snapshot //~1A1bI~
	        		notes=Notes.prev(notesList,lastNotes);         //~1A1bR~
                else                                               //~1A1bI~
                	notes=lastNotes;                               //~1A1bI~
            seq=1;	//if note==null                                //~1A1bR~
        }                                                          //~1A1bI~
        else                                                       //~1A1bI~
        {                                                          //~1A1bI~
        	if (lastNotes!=null)                                         //~1A1bI~
        		notes=Notes.next(notesList,lastNotes);             //~1A1bR~
            seq=sz;	//if already last                              //~1A1bR~
        }                                                          //~1A1bI~
        if (notes!=null)                                            //~1A10I~
        {                                                          //~1A10I~
   			restoreNotes(notes,false/*invisible if ctr=0*/);       //~1A21R~
            seq=notes.seq;                                         //~1A1bI~
	        msg=AG.resource.getString(R.string.InfoRestored,seq,sz);//~1A1bI~
        }                                                          //~1A10I~
        else                                                       //~1A1bI~
	        msg=AG.resource.getString(R.string.ErrRestore,seq,sz); //~1A1bI~
        setLabel(msg,false/*no append*/);                            //~1A1bI~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //***************************                                  //~1A21I~
    protected void restoreNotes(Notes Pnotes,boolean Pvisible0)      //~1A21R~//~1A2dR~
    {                                                              //~1A21I~
        lastNotes=Pnotes;                                          //~1A21I~
        lastNotes.moves=0;                                         //~1A21I~
        lastNotes.moves0=FB.moveNumber; //starting move count in this snapshot//~1A21I~
        int[][] tray=Pnotes.getTray();                             //~1A21I~
        int[][] pieces=Pnotes.getPieces();                         //~1A21I~
        if (Dump.Y) Dump.println("FGF Col="+Col+",Note yourColor="+Pnotes.yourcolor);//~1A2dI~
        if (Col==Pnotes.yourcolor)                                 //~1A2dI~
        {                                                          //~1A2dI~
//         FB.YourColor=Pnotes.yourcolor;                          //~1A2dI~
        }                                                          //~1A2dI~
        else                                                       //~1A2dI~
        {                                                          //~1A2dI~
        	pieces=reverseColor(tray,pieces);                      //~1A2dI~
//      	B.YourColor=-Pnotes.yourcolor;                         //~1A2dI~
        }                                                          //~1A2dI~
        aCapturedList.restoreTray(tray);                           //~1A21I~
        aCapturedList.setTrayVisible(Pvisible0);                   //~1A21R~
        FB.restoreBoard(pieces);	//CB                                   //~1A21I~//~1A2dR~
        setMainColor(Pnotes.color);                                 //~1A21I~
        setTitle(Pnotes,"/"+notesList.size());                        //~1A24I~
    }                                                              //~1A21I~
    //***************************                                  //~1A1bI~
    private void studySave()                                       //~1A1bI~
    {                                                              //~1A1bI~
        MatchTitle="";//note:name                                  //~1A24I~
//      saveNotes(FB.MainColor);                                    //~1A1bI~//~1A21R~
//      saveNotes(FB.P.color(),null);                                   //~1A21I~//~1A2aR~
        saveNotes(FB.P.color(),boardName);                         //~1A2aI~
        int sz=notesList.size();                                   //~1A1bI~
        String s=AG.resource.getString(R.string.InfoSaved,sz);      //~1A1bI~
        setLabel(s,false/*no append*/);                            //~1A1bI~
        setTitle(lastNotes,"/"+notesList.size());                  //~1A24I~
        clickSound();                                              //~1A1bI~
	}                                                              //~1A1bI~
    //***************************                                  //~1A1bI~
    private void studyClose(String o)                                      //~1A19I~
    {                                                              //~1A19I~
    	if (Dump.Y) Dump.println("FGF Close");                     //~1A19I~
		int opt=AG.Options & AG.OPTIONS_GOFRAME_CLOSE_CONFIRM;     //~1A19I~
        if (opt==0)                                                //~1A19I~
			AG.Options |= AG.OPTIONS_GOFRAME_CLOSE_CONFIRM;         //~1A19I~
        super.doAction(o);    	                                   //~1A19I~
        if (opt==0)                                                //~1A19I~
			AG.Options &= ~AG.OPTIONS_GOFRAME_CLOSE_CONFIRM;       //~1A19I~
    	if (Dump.Y) Dump.println("FGF Close return");              //~1A19I~
	}                                                              //~1A19I~
    //***************************                                  //~1A21I~
    private void studyFile()                                       //~1A21I~
    {                                                              //~1A21I~
    	if (Dump.Y) Dump.println("FGF File");                      //~1A21I~
      	AG.aMenu.fileMenu(this);                                   //~1A21I~
	}                                                              //~1A21I~
    //***************************                                  //~1A21I~
    private void loadFile()                                        //~1A21I~
    {                                                              //~1A21I~
    	if (Dump.Y) Dump.println("FGF loadFile");                  //~1A21I~
//        loadNotes=null;                                            //~1A21I~//~1A22R~
    	FileDialog fd=new FileDialog(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_LoadFile),FileDialog.LOAD);//~1A21R~
        fd.setFilterString(FileDialog.SNAPSHOT_EXT);               //~1A22I~
        fd.show();  //callback loadFileNotes                       //~1A21R~
	}                                                              //~1A21I~
    //***************************                                  //~1A33I~
    //*load tsumego file                                           //~1A33I~
    //***************************                                  //~1A33I~
    private void loadNotesFile()                                   //~1A33I~
    {                                                              //~1A33I~
    	if (Dump.Y) Dump.println("FGF loadNotesFile");             //~1A33I~
//  	FileDialog fd=new FileDialog(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_LoadFile),FileDialog.LOAD_FGFNOTES);//~1A4CR~
    	FileDialog fd=new FileDialog(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_LoadNotesFile),FileDialog.LOAD_FGFNOTES);//~1A4CI~
        fd.setFilterString("");	//no meaning,filter is reset by property file//~1A33I~
        fd.show();  //callback loadFileNotes                       //~1A33I~
	}                                                              //~1A33I~
    //***************************                                  //~1A4sI~
    private void loadNotesFileClipboard()                          //~1A4sI~
    {                                                              //~1A4sI~
    	if (Dump.Y) Dump.println("FGF loadNotesFileClipboard");    //~1A4sI~
    	fdc=new FileDialogClipboard(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_ReplayClipboard),FileDialogClipboard.FDC_TSUMESHOGI);//~1A4sR~
        fdc.show();  //callback FileDialogLoaded                   //~1A4sI~
	}                                                              //~1A4sI~
    //***************************                                  //~1A21I~
    @Override//TGF
    public void loadFileNotes(Notes Pnotes)                                    //~1A21I~//~1A22R~
    {                                                              //~1A21I~
    	if (Dump.Y) Dump.println("FGF loadFileNote");              //~1A21I~
        Notes notes=Pnotes;                                     //~1A21I~//~1A22R~
        if ((notes.gameoptions & GameQuestion.GAMEOPT_NOTESFILE)!=0)//~1A33I~
        {                                                          //~1A33I~
            boardName=notes.name;	                               //~1A33I~
        }                                                          //~1A33I~
        notesList.removeall();  //notes.seq=1 at add()             //~1A33I~
        notes.add(notesList);	//seq changes                      //~1A21I~
        boolean visible=FB.isIdleFreeBoard()?true:false;           //~1A21I~
    	restoreNotes(notes,visible);                               //~1A21R~
//      String msg=AG.resource.getString(R.string.InfoRestoredFile,notes.name);//~1A21I~//~1A33R~
//      String msg=AG.resource.getString(R.string.InfoRestoredFile,notes.filename);//~1A4vR~
        String msg=AG.resource.getString(R.string.InfoRestoredFile,notes.name);//~1A4vI~
        setLabel(msg,false/*no append*/);                          //~1A21I~
//      setTitle(notes,"/"+notesList.size()+FileDialog.SNAPSHOT_EXT);//~1A24I~//~1A33R~
      if ((notes.gameoptions & GameQuestion.GAMEOPT_NOTESFILE)!=0) //~1A33I~
        setTitle(notes,"/"+notesList.size());                      //~1A33I~
      else                                                         //~1A33I~
        setTitle(notes,"/"+notesList.size()+"("+FileDialog.SNAPSHOT_EXT+")");//~1A33I~
	}                                                              //~1A21I~
    //***************************                                  //~1A21I~
    private void saveFile()                                        //~1A21I~
    {                                                              //~1A21I~
        if ((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0)                //~1A26I~
        {                                                          //~1A26I~
        	saveFileDirect();                                       //~1A26I~
            return;                                                //~1A26I~
        }                                                          //~1A26I~
        int color=1;                                               //~1A21I~
    	if (Dump.Y) Dump.println("FGF saveFile");                  //~1A21I~
    	FileDialog fd=new FileDialog(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_SaveFile),FileDialog.SAVE);//~1A21R~
        if (!FB.isIdleFreeBoard())  //started                      //~1A21I~
        	color=FB.P.color();                                    //~1A21I~
        MatchTitle=FileDialog.SNAPSHOT_EXT;//note:name                        //~1A22R~//~1A24I~
	    saveNotes(color,boardName);                                          //~1A21R~//~1A2aR~
        setTitle(lastNotes,"/"+notesList.size());                  //~1A24I~
        fd.setSaveNotes(lastNotesSaved);                           //~1A21I~
        fd.show();                                                 //~1A21I~
	}                                                              //~1A21I~
    //***************************                                  //~1A26I~
    private void saveFileDirect()                                  //~1A26I~
    {                                                              //~1A26I~
    	if (Dump.Y) Dump.println("FGF saveFileDirect");            //~1A26I~
        int color=1;                                               //~1A26I~
        if (!FB.isIdleFreeBoard())  //started                      //~1A26I~
        	color=FB.P.color();                                    //~1A26I~
        MatchTitle=FileDialog.SNAPSHOT_EXT;//note:name             //~1A26I~
//	    Notes notes=saveNotes(color,null);                          //~1A26I~//~1A2aR~
  	    Notes notes=saveNotes(color,boardName);                    //~1A2aI~
        setTitle(notes,"/"+notesList.size());                  //~1A26I~
        FileDialog.saveDirect(this,notes);                         //~1A26R~
	}                                                              //~1A26I~
//    private void studyCheckmate()                                  //~1A15I~//~1A1iR~
//    {                                                              //~1A15I~//~1A1iR~
//        if (Dump.Y) Dump.println("FGF Checkmate");                 //~1A15R~//~1A1iR~
//        boolean cm=FB.aRules.isCheckmateFB(Col);                   //~1A15R~//~1A1iR~
//        if (cm)                                                    //~1A15I~//~1A1iR~
//            setLabel(R.string.ErrCheckmateFB,false/*no append*/);  //~1A15I~//~1A1iR~
//        else                                                       //~1A15I~//~1A1iR~
//            setLabel(R.string.ErrNoCheckmateFB,false/*no append*/);//~1A15I~//~1A1iR~
//        clickSound();                                              //~1A15I~//~1A1iR~
//    }                                                              //~1A15I~//~1A1iR~
    private void switchBlackWhite()                                //~1A10I~
    {                                                              //~1A10I~
    	FB.changePiece(true/*color*/,false/*promotion*/,false/*delete*/);//~1A10R~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    private void switchPromote()                                   //~1A10I~
    {                                                              //~1A10I~
    	FB.changePiece(false/*color*/,true/*promotion*/,false/*delete*/);//~1A10R~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    private void deletePiece(boolean Psound)                                     //~1A10I~//~1A1bR~
    {                                                              //~1A10I~
    	FB.changePiece(false/*color*/,false/*promotion*/,true/*delete*/);//~1A10R~
      if (Psound)                                                  //~1A1bI~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    protected void setInitialPieces()                               //~1A10I~//~1A2dR~
    {                                                              //~1A10I~
    	FB.resetBoard(Col,GameOptions,Handicap);                   //~1A10R~
    	aCapturedList.clearTray(1);                                //~1A10R~
    	aCapturedList.clearTray(-1);                               //~1A10I~
    	aCapturedList.reset(0);	//reset selected of tray           //~1A1bI~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //*************************************************            //~1A30I~
    //*for PieceByPiece specification                              //~1A30I~
    //*************************************************            //~1A30I~
    protected void setInitialPieces(Notes Pnotes)                  //~1A30I~
    {                                                              //~1A30I~
    	if (Pnotes.piecesAtStart==null)                            //~1A30I~
        	setInitialPieces();                                     //~1A30I~
        else                                                       //~1A30I~
        {                                                          //~1A30I~
        	if (Pnotes.trayAtStart!=null)                          //~1A30I~
				aCapturedList.restoreTray(Pnotes.trayAtStart);     //~1A30I~
            else                                                   //~1A30I~
            {                                                      //~1A30I~
    			aCapturedList.clearTray(1);                        //~1A30I~
    			aCapturedList.clearTray(-1);                       //~1A30I~
            }                                                      //~1A30I~
    		aCapturedList.reset(0);	//reset selected of tray       //~1A30I~
            FB.restoreBoard(Pnotes.piecesAtStart);                //~1A30I~
            clickSound();                                              //~1A30I~
        }                                                          //~1A30I~
 	}                                                              //~1A30I~
    //*************************************************            //~1A1bI~
    @Override //TGF
    public Notes saveNotes(int Pcolor,String Pnotesname)                             //~1A1bR~
    {                                                              //~1A1bI~
//      Notes notes=new Notes("",Col,Pcolor);                          //~1A1bR~//~1A22R~
//      notes.save(this);                                          //~1A1bI~//~1A22R~
        Notes notes=super.saveNotes(Pcolor,Pnotesname);                       //~1A22I~
        notes.add(notesList);                                      //~1A1bR~
        lastNotes=notes;                                           //~1A1bI~
        lastNotesSaved=notes;                                      //~1A1bI~
        lastNotes.moves=0;              //move count inthis snapshot//~1A1bR~
        lastNotes.moves0=FB.moveNumber;	//starting move count in this snapshot//~1A1bI~
        lastNotes.name+="-"+notes.seq;                             //~1A24I~
        return notes;
    }                                                              //~1A1bI~
    //*************************************************            //~1A10I~
    //move all piece to both tray                                  //~1A10I~
    //*************************************************            //~1A10I~
    private void clearBoard()                                      //~1A10R~
    {                                                              //~1A10I~
        FB.clearBoard();                                           //~1A10R~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //*************************************************            //~1A10I~
    //put all remaining to white tray                              //~1A10I~
    //*************************************************            //~1A10I~
    private void clearToWhiteTray()                                //~1A10I~
    {                                                              //~1A10I~
    	aCapturedList.clearToWhiteTray();                          //~1A10R~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //*************************************************            //~1A10I~
    //clear tray                                                   //~1A10I~
    //*************************************************            //~1A10I~
    private void clearTray(int Pcolor)                            //~1A10I~
    {                                                              //~1A10I~
    	aCapturedList.clearTray(Pcolor);                           //~1A10I~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
//************************************************************************//~1A10I~
//*Black Time only                                                 //~1A10I~
//************************************************************************//~1A10I~
	public void alarm ()                                           //~1A10I~
	{                                                              //~1A10I~
		long now=System.currentTimeMillis();                       //~1A10I~
//      BlackRun=(int)((now-CurrentTime)/1000)+totalBlackRun;      //~1A10R~//~1A18R~
        BlackRun=(int)((now-StartTime)/1000);                      //~1A18I~
    	if (Dump.Y) Dump.println("FGF alarm BlackRun="+BlackRun+",prev total="+totalBlackRun);//~1A10I~
//      WhiteRun=(int)((now-StartTime)/1000);                      //~1A10I~//~1A18R~
//    	if (TotalTime!=0)	//timeover was set                     //~1A10I~//+1Ah1R~
      	if (TotalTime!=0 || TotalTime!=0)	//timeover was set     //+1Ah1I~
      	{                                                          //~1A10I~
    		if (BlackTime-BlackRun<0)                              //~1A10I~
			{                                                      //~1A10I~
            	studyTimeout();                                    //~1A10R~
			}                                                      //~1A10I~
      	}//TotalTime!=0                                            //~1A10I~
		settitle();                                                //~1A10I~
	}                                                              //~1A10I~
//************************************************                 //~1A10I~
    public void studyTimeout()                                     //~1A10R~
    {                                                              //~1A10I~
    	if (Dump.Y) Dump.println("FGF studyTimeout");              //~1A10R~
        String msg=AG.resource.getString(R.string.ErrTimeoutFreeBoard);//~1A10I~
        FB.infoComment(msg);                                       //~1A10I~
		Alert.simpleAlertDialog(null,MatchTitle,msg,Alert.BUTTON_POSITIVE);//~1A10I~
    	gameoverSound(0);                                          //~1A10I~
        gameovered(); //stop timer                                 //~1A10R~
    }                                                              //~1A10I~
//************************************************                 //~1A10I~
	protected void settitle ()                                     //~1A10I~
	{                                                              //~1A10I~
		if (BigTimer)                                              //~1A10I~
    	{                                                          //~1A10I~
//    		BL.setTime(WhiteTime-WhiteRun,BlackTime-BlackRun,-1/*WhiteMoves*/,-1/*BlackMoves*/,1/*cc*/,false/*extraB*/,false/*extraW*/);//~1A10R~//~1A18R~
      		BL.setTime(-1/*dont show*/,BlackTime-BlackRun,-1/*WhiteMoves*/,-1/*BlackMoves*/,1/*cc*/,false/*extraB*/,false/*extraW*/);//~1A18I~
			BL.repaint();                                          //~1A10I~
			beep(BlackTime-BlackRun);                              //~1A10I~
		}                                                          //~1A10I~
	}                                                              //~1A10I~
    //***************************                                  //~1A24I~
//  private void setTitle(Notes Pnotes,String Pname)               //~1A24I~//~1A31R~
    protected void setTitle(Notes Pnotes,String Pname)             //~1A31I~
    {                                                              //~1A24I~
        String title;                                              //~1A24I~
    	if (Pnotes!=null)                                          //~1A24I~
//      	title=AG.resource.getString(R.string.Title_FreeGoFrame)+Pnotes.name+Pname;//~1A24I~//~1A33R~
  	    	title=AG.resource.getString(R.string.Title_FreeGoFrame)+":"+Pnotes.name+Pname;//~1A33I~
        else                                                       //~1A24I~
        	title=AG.resource.getString(R.string.Title_FreeGoFrame)+":"+Pname;//~1A24I~
        (new Component()).setTitle(title);                         //~1A24I~
    }                                                              //~1A24I~
//************************************************                 //~1A10I~
    protected void clickSound()                                      //~1A10I~//~1A2dR~
    {                                                              //~1A10I~
        JagoSound.play("click","click",false);                     //~1A10M~
    }                                                              //~1A10I~
//************************************************                 //~1A15I~
    protected void clearLabel()                                      //~1A15I~//~1A2dR~
    {                                                              //~1A15I~
        setLabel("",false/*no append*/);                           //~1A15I~
    }                                                              //~1A15I~
//************************************************                 //~1A17I~
//*pos>-S && <S                                                    //~1A17R~
//************************************************                 //~1A17I~
    public void mouseSwiped(int Pposx,int Pposy,boolean Psound)                   //~1A17R~//~1A1bR~
    {                                                              //~1A17I~
    	if (Dump.Y) Dump.println("FGF swiped x="+Pposx+",y="+Pposy);//~1A17R~
    	deletePiece(Psound);                                        //~1A17R~//~1A1bR~
    }                                                              //~1A17I~
//************************************************                 //~1A22I~
//*FileDialogI                                                     //~1A4sI~
//*for clipboard called with note=null even when load failed       //~1A4sI~
//************************************************                 //~1A4sI~
	@Override	//GoFrame                                          //~1A24R~
	public void fileDialogLoaded(CloseDialog Pdlg/*null*/,Notes Pnotes)                      //~1A22I~
	{                                                              //~1A22I~
//  	if (Dump.Y) Dump.println("FGF fileDialogClosed dlg="+Pdlg.toString());//~1A4sI~//~1AeyR~
    	if (Dump.Y) Dump.println("FGF fileDialogClosed dlg="+Pdlg);//~1AeyI~
        if (Pnotes==null)                                          //~1A4sI~
        	return;                                                //~1A4sI~
		loadFileNotes(Pnotes);                                     //~1A22R~
        clickSound();                                              //~1A26I~
	}                                                              //~1A22I~
//************************************************                 //~1A22I~
    @Override   //TGF                                              //~1A22R~
    public void fileDialogSaved(String Psavename)                  //~1A26R~
	{                                                              //~1A26I~
        clickSound();                                              //~1A26I~
        infoGameSaved(Psavename);                                  //~1A4bI~
	}//dont set FileButton to GONE                                 //~1A26I~
//************************************************                 //~1A26I~
    @Override   //TGF                                              //~1A26I~
    public void fileDialogNotSaved()                               //~1A26I~
	{                                                              //~1A26I~
	}                                                              //~1A26I~
}

