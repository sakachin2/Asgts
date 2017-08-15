//*CID://+1AexR~:                                   update#=  414; //~1AexR~
//****************************************************************************//~@@@1I~
//1Aex 2016/07/02 (Bug)ReplayClipboard piece direction is reverse when Color=-1(white)//~1AexI~
//1Aes 2016/06/29 (Bug) replay file saved by clipboard-save dose not show move msg//~1AesI~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//1A35 2013/04/19 show mark of last moved from position            //~1A35I~
//1A31 2013/04/16 (BUG)title of ReplayBoard is of FreeBoard after replayLast//~1A31I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
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

import android.view.View;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.Component;
import jagoclient.board.ActionMove;
import jagoclient.board.Notes;
import jagoclient.board.NotesTree;
import jagoclient.board.ReplayBoard;
import jagoclient.board.ConnectedBoard;                            //~1AesI~
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;

/**
The go frame for partner connections.
*/

public class ReplayGoFrame extends FreeGoFrame                      //~1A10R~//~1A2dR~
{                                                                  //~@@@@R~
	FreeFrame PF;                                             //~1A10R~
//  int Handicap;                                                //~@@@@R~
    private ButtonAction ButtonFree,ButtonBack;                    //~1A2dI~
    private View ButtonLineFree,ButtonLineReplay,ButtonClearCapturedW,ButtonClearCapturedB;//~1A2dR~
    private boolean swSmall=AG.portrait;	//se small button if portrait and small pixel device//~1A10R~
    public NotesTree replayTree;                                  //~1A2dR~
    private int treeSize;                                          //~1A2dI~
    private int curPos;                                            //~1A2dR~
    private static final int MULTI_STEP=10;                        //~1A2dI~
    private ReplayBoard RB;                                        //~1A2dI~
    private int savelasti=-1,savelastj=-1;                         //~1A2dI~
    private int headerCtr,trailerCtr,movemsgCtr;                   //~1A4vR~
//*********************************************************************//~@@@@I~
//*********************************************************************//~@@@@I~
	public ReplayGoFrame (FreeFrame pf,                     //~@@@@M~//~1A10R~//~1A2dR~
    	int col,Notes Pnotes)//~@@@@R~//~v1A0R~ //~1A10R~          //~1A2dR~
    {                                                              //~@@@@I~
        super(pf,col,Pnotes);                                      //~1A2dR~
        RB=(ReplayBoard)B;                                         //~1A2dI~
        ConnectedBoard.setInitialPieceReverse(replayNotes,col);    //+1AexI~
        replayTree=replayNotes.getTree();                   //~1A2dI~
        treeSize=replayTree.size();                                //~1A2dR~
        replayTree.changeCoord(replayNotes,Col);                   //~1A2eR~
        if (changeCoordLang(replayNotes,replayTree))	//borad update by redo//~1A2eR~
        {                                                          //~1A2eI~
        	curPos=treeSize;                                      //~1A2eI~
		  	replayFirst(true);                                     //~1A2eI~//~1A30R~
        }	                                                       //~1A2eI~
        showAllComment(replayNotes);                               //~1A4vI~
        showSetStone(false);                                       //~1A2dR~
        FB.swFreeBoardStarted=true; //Canvas                       //~1A2dI~
        setLabel(R.string.InfoUndoReachedToInit);                  //~1A2dI~
        ActionMove a=replayTree.firstElement();                    //~1A2dI~
        if (a!=null)                                               //~1A31I~
	        setMainColor(a.color);    //TGF                            //~1A2dR~//~1A31R~
        drawInitialCaptured();	 //CGF                             //~1A2dI~
    }                                                              //~1A10I~
    //***********************************                          //~1A2dI~
    @Override //FGF                                                //~1A2dI~
    protected void showFrame()                                       //~1A10I~
    {                                                              //~1A10I~
       	boardName=replayNotes.name;                                //~1A2dR~
        setTitle(null/*notes*/,boardName);                     //~1A24I~//~1A2dR~
   		setupButtons();                                            //~1A10R~
    	setButtonsReplay();                                  //~1A10I~//~1A2dR~
		addKeyListener(this);                                      //~@@@1I~
		setVisible(true);
		repaint();
	}
    //***********************************                          //~1A10I~
    private void setupButtons()                                    //~1A10R~
    {                                                              //~1A10I~
//      View v;                                                    //~1A2dI~
    //********************                                         //~1A2dI~
        ButtonSuspend.setVisibility(View.GONE);                    //~1A26I~//~1A2dM~
        ButtonResign.setAction(R.string.Close);                    //~1A2dR~
                                                                   //~1A2dI~
        new ButtonAction(this,0,R.id.ReplayFirst);                 //~1A2dI~
        new ButtonAction(this,0,R.id.ReplayLast);                  //~1A2dI~
        new ButtonAction(this,0,R.id.ReplayNext);                  //~1A2dI~
        new ButtonAction(this,0,R.id.ReplayNext1);                 //~1A2dI~
        new ButtonAction(this,0,R.id.ReplayPrev);                  //~1A2dI~
        new ButtonAction(this,0,R.id.ReplayPrev1);                 //~1A2dI~
        setupFreeBoardImageButtons();                               //~1A2dI~
                                                                   //~1A2dI~
        PromotionButton.setVisibility(View.GONE);	//CGF      //~1A10I~//~1A2dI~
        ButtonFree=new ButtonAction(this,0,R.id.Variation,swSmall/*smallbutton*/);//~1A2dI~
        ButtonBack=new ButtonAction(this,0,R.id.BackToRecord,swSmall/*smallbutton*/);//~1A2dI~
        ButtonLineReplay=findViewById(R.id.ReplayBoardButtonLine); //~1A2dI~
        ButtonLineFree=findViewById(R.id.StudyBoardImageButtons);  //~1A2dI~
        ButtonClearCapturedW=findViewById(R.id.StudyClearCaptureWLayout);//~1A2dR~
        ButtonClearCapturedB=findViewById(R.id.StudyClearCaptureBLayout);//~1A2dR~
//      v=findViewById(R.id.CommentPanelContainer);                         //~1A10I~//~1A1aM~//~1A1bR~//~1A4vR~
//      setVisibility(v,View.GONE);                                //~1A10I~//~1A4vR~
    }                                                              //~1A10I~
    //***********************************                          //~1A10I~//~1A2dM~
    private void setButtonsReplay()                            //~1A10I~//~1A2dI~
    {                                                              //~1A10I~//~1A2dM~
        ButtonBack.setVisibility(View.GONE);                       //~1A2dI~
        ButtonFree.setVisibility(View.VISIBLE);                    //~1A2dI~
    	showSetStone(false);                                       //~1A2dR~
        setVisibility(ButtonLineReplay,View.VISIBLE);              //~1A2dI~
        setVisibility(ButtonLineFree,View.GONE);                   //~1A2dI~
        setVisibility(ButtonClearCapturedW,View.GONE);                             //~1A10I~//~1A2dR~
        setVisibility(ButtonClearCapturedB,View.GONE);             //~1A2dR~
        aCapturedList.setTrayVisible(false/*invisible if ctr=0*/); //~1A10I~//~1A2dM~
    }                                                              //~1A10I~//~1A2dM~
    private void showSetStone(boolean Pvisible)                    //~1A2dR~
    {                                                              //~1A2dI~
    	if (RB!=null)	//null at showFrame                        //~1A2dI~
        {                                                          //~1A2dI~
        	RB.swReplayBoardReplay=!Pvisible;	//canvas ignore board touch//~1A2dI~
	        setVisibility(RB.buttonSetStone.androidButton,Pvisible?View.VISIBLE:View.GONE);//~1A2dR~
        }                                                          //~1A2dI~
    }                                                              //~1A2dI~
    //***********************************                          //~1A10I~
    private void setButtonsFree()                          //~1A10I~//~1A2dR~
    {                                                              //~1A10I~
        ButtonBack.setVisibility(View.VISIBLE);                    //~1A2dI~
        ButtonFree.setVisibility(View.GONE);                       //~1A2dI~
    	showSetStone(true);                                        //~1A2dR~
        setVisibility(ButtonLineReplay,View.GONE);                 //~1A2dI~
        setVisibility(ButtonLineFree,View.VISIBLE);                //~1A2dI~
        setVisibility(ButtonClearCapturedW,View.VISIBLE);          //~1A2dR~
        setVisibility(ButtonClearCapturedB,View.VISIBLE);          //~1A2dR~
        aCapturedList.initFreeBoardTray();                         //~1A10I~
    }                                                              //~1A10I~
    //***********************************                          //~1A10I~
	public void doAction (String o)
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("ReplayGoFrame doaction:"+o);     //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.ReplayFirst)))//~1A10I~//~1A2dR~
		{                                                          //~1A10I~
        	replayFirst(false);                                    //~1A2dI~//~1A30R~
		}                                                          //~1A10I~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.ReplayLast)))  //~1A2dI~
		{                                                          //~1A2dI~
        	replayLast();                                          //~1A2dI~
		}                                                          //~1A2dI~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.ReplayNext)))  //~1A2dI~
		{                                                          //~1A2dI~
        	replayNext(MULTI_STEP);                                //~1A2dI~
		}                                                          //~1A2dI~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.ReplayNext1))) //~1A2dI~
		{                                                          //~1A2dI~
        	replayNext(1);                                         //~1A2dI~
		}                                                          //~1A2dI~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.ReplayPrev)))  //~1A2dI~
		{                                                          //~1A2dI~
        	replayPrev(MULTI_STEP);                                //~1A2dI~
		}                                                          //~1A2dI~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.ReplayPrev1))) //~1A2dI~
		{                                                          //~1A2dI~
        	replayPrev(1);                                         //~1A2dI~
		}                                                          //~1A2dI~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.Variation)))   //~1A2dI~
		{                                                          //~1A2dI~
        	tryVariation();                                        //~1A2dI~
		}                                                          //~1A2dI~
        else                                                       //~1A2dI~
        if (o.equals(AG.resource.getString(R.string.BackToRecord)))//~1A2dI~
		{                                                          //~1A2dI~
        	backToRecord();                                        //~1A2dI~
		}                                                          //~1A2dI~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~1A2eI~
		{                                                          //~1A2eI~
        	new HelpDialog(this,R.string.Title_ReplayGoFrame,"ReplayBoard");//~1A2eI~
		}                                                          //~1A2eI~
		else super.doAction(o);
	}

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
		aCapturedList.reset(Pcolor);// reset capturedlist selection//~v1A0R~//~1A15I~
        clickSound();                                              //~1A10R~
    }                                                              //~@@@@I~//~1A10R~
//************************************************************************//~@@@@I~//~1A10R~
    private void backToRecord()                                      //~1A10I~//~1A1aR~//~1A2dR~
    {                                                              //~1A10I~
    	if (Dump.Y) Dump.println("FGF Start");                     //~1A10I~
//        if (!FB.aRules.chkFoulFB(Col))                                   //~1A10R~//~1A2dR~
//            return; //some piece has count over                    //~1A10I~//~1A2dR~
//        if (!aCapturedList.chkTotalPieceCtr())                    //~1A10I~//~1A2dR~
//            return; //some piece has count over                    //~1A10I~//~1A2dR~
        MatchTitle="";//note:name                                  //~1A24I~
    	setButtonsReplay();                                    //~1A10I~//~1A2dR~
		RB.removeLastMark();                                       //~1A2dI~
        RB.lasti=savelasti; RB.lastj=savelastj;	//recover last mark position//~1A2dI~
        RB.update(savelasti,savelastj);	//draw last mark(restoreBoard skip update when piece and color is same)//~1A2dI~
        restoreSS();                                               //~1A2dI~
        restoreCurPos();                                           //~1A2dI~
		aCapturedList.reset(0);//reset freeboard tray selection    //~1A1fI~
        FB.swFreeBoardStarted=true; //Canvas                       //~1A10R~//~1A15R~//~1A2dR~
        setMainColor(lastNotes.color);    //TGF                             //~1A1bM~//~1A2dR~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //***************************                                  //~1A2dI~
    private void restoreCurPos()                                   //~1A2dI~
    {                                                              //~1A2dI~
    	ActionMove a=null;                                         //~1A2dR~
        if (treeSize>0)                                            //~1A2dI~
        {                                                          //~1A2dI~
    		if (curPos>treeSize)                                   //~1A2dR~
        		a=replayTree.get(treeSize-1);                      //~1A2dR~
        	else                                                   //~1A2dR~
        	if (curPos>0)	                                       //~1A2dR~
        		a=replayTree.get(curPos-1);                        //~1A2dR~
        }                                                          //~1A2dI~
        if (a==null)                                               //~1A2dI~
        	setLabel(R.string.InfoUndoReachedToInit);              //~1A2dI~
        else                                                       //~1A2dI~
        	RB.displayMoveDescription(a,replayNotes);                          //~1A2dI~//~1A2eR~
    }                                                              //~1A2dI~
    //***************************                                  //~1A1bI~
    private void tryVariation()                                       //~1A10I~//~1A2dR~
    {                                                              //~1A10I~
    	if (Dump.Y) Dump.println("FGF try variation");//~1A10I~    //~1A2dR~
 		setButtonsFree();                                  //~1A10I~//~1A2dR~
        FB.swFreeBoardStarted=false;	//Canvas                   //~1A10R~//~1A2dR~
        saveSS();	//save snapshot                                //~1A2dI~
       savelasti=RB.lasti; savelastj=RB.lastj;                          //~1A2dI~
        clickSound();                                              //~1A10I~
	}                                                              //~1A10I~
    //***************************                                  //~1A1bI~
    private void saveSS()                                       //~1A1bI~//~1A2dR~
    {                                                              //~1A1bI~
        lastNotes=saveNotes(FB.P.color(),boardName);                         //~1A2aI~//~1A2dR~
	}                                                              //~1A1bI~
    //***************************                                  //~1A2dI~
    private void restoreSS()                                       //~1A2dI~
    {                                                              //~1A2dI~
    	restoreNotes(lastNotes,false/*visible0*/);//FGF            //~1A2dI~
	}                                                              //~1A2dI~
    //***************************                                  //~1A2dI~
    private void replayFirst(boolean Pinit)                        //~1A2dI~//~1A30R~
    {                                                              //~1A2dI~
        if (Dump.Y) Dump.println("replayFirst cpos="+curPos);      //~1A2dI~
    	if (curPos!=0||Pinit)                                      //~1A2dI~//~1A30R~
        {                                                          //~1A2dI~
		    clearLabel();                                          //~1A2dI~
        	RB.removeLastMark();                                   //~1A2dI~
//  		setInitialPieces();	//FGF                              //~1A2dR~//~1A30R~
    		setInitialPieces(replayNotes);	//FGF,may init by piecebypiece specification//~1A30I~
	        aCapturedList.setTrayVisible(false/*invisible if ctr=0*/);//~1A2dI~
        	curPos=0;                                             //~1A2dR~
            if (!Pinit)                                            //~1A4vI~
	            scrollComment(curPos);                             //~1A4vR~
	        setLabel(R.string.InfoUndoReachedToInit);              //~1A35I~
        }                                                          //~1A2dI~
        else                                                       //~1A2dI~
        {                                                          //~1A2dI~
	        setLabel(R.string.ErrReplayReachedFirst,false/*no append*/);//~1A2dI~
        }                                                          //~1A2dI~
    }                                                              //~1A2dI~
    //***************************                                  //~1A2dI~
    private void replayLast()                                      //~1A2dI~
    {                                                              //~1A2dI~
        if (Dump.Y) Dump.println("replayLast cpos="+curPos);       //~1A2dI~
    	if (curPos<treeSize && treeSize!=0)                        //~1A2dR~
        {                                                          //~1A2dI~
		    clearLabel();                                          //~1A2dI~
        	RB.removeLastMark();                                   //~1A2dI~
            ActionMove a=replayTree.lastElement();                 //~1A2dM~
            if (a!=null)                                          //~1A31I~
            {                                                      //~1A31I~
            	RB.lasti=a.iTo;                                    //~1A35R~
            	RB.setLastMark(a);                                 //~1A35R~
            	RB.setLastMarkFrom(a);                             //~1A35M~
            	RB.displayMoveDescription(a,replayNotes);                          //~1A2dM~//~1A2eR~//~1A31R~
            }                                                      //~1A31I~
		    restoreNotes(replayNotes,false/*visible ctr=0*/);       //~1A2dI~
        	curPos=treeSize;                                      //~1A2dI~
            scrollComment(curPos);	                               //~1A4vI~
	        clickSound();                                          //~1A2dI~
        }                                                          //~1A2dI~
        else                                                       //~1A2dI~
        {                                                          //~1A2dI~
	        setLabel(R.string.ErrReplayReachedLast,false/*no append*/);//~1A2dI~
        }                                                          //~1A2dI~
    }                                                              //~1A2dI~
    //***************************                                  //~1A2dI~
    private void replayNext(int Pstep)                             //~1A2dI~
    {                                                              //~1A2dI~
    	int pos,ii,ctr=0;                                     //~1A2dR~
    //************************                                     //~1A2dI~
    	if (curPos>=treeSize)                                      //~1A2dI~
        {                                                          //~1A2dI~
	        setLabel(R.string.ErrReplayReachedLast,false/*no append*/);//~1A2dI~
            return;                                                //~1A2dI~
        }                                                          //~1A2dI~
        if (curPos<0)                                              //~1A2dI~
            curPos=0;                                              //~1A2dI~
	    clearLabel();                                              //~1A2dI~
        ctr=Math.min(Pstep,treeSize-curPos);                       //~1A2dI~
    	for (pos=curPos,ii=0;ii<ctr;ii++,pos++)                    //~1A2dR~
        {                                                          //~1A2dI~
        	replayRedo(pos,ii==(ctr-1)/*last*/);                   //~1A2dR~
        }                                                          //~1A2dI~
        if (Dump.Y) Dump.println("replayNext pos="+curPos+"-->"+pos);//~1A2dI~
        curPos=pos;                                                //~1A2dI~
        scrollComment(curPos);                                     //~1A4vI~
        clickSound();                                              //~1A2dI~
    }                                                              //~1A2dI~
    //***************************                                  //~1A2dI~
    private void replayPrev(int Pstep)                             //~1A2dI~
    {                                                              //~1A2dI~
    	int pos,ii,ctr=0;                                       //~1A2dR~
    //************************                                     //~1A2dI~
    	pos=curPos;                                                //~1A2dR~
    	if (pos>=treeSize)                                         //~1A2dI~
        	pos=treeSize;                                          //~1A2dR~
    	if (pos<=0)                                                //~1A2dR~
        {                                                          //~1A2dI~
	        setLabel(R.string.ErrReplayReachedFirst,false/*no append*/);//~1A2dI~
            return;                                                //~1A2dI~
        }                                                          //~1A2dI~
	    clearLabel();                                              //~1A2dI~
        ctr=Math.min(Pstep,pos);                                   //~1A2dR~
        pos--;                                                     //~1A2dI~
    	for (ii=0;ii<ctr;ii++,pos--)                               //~1A2dR~
        {                                                          //~1A2dI~
        	replayUndo(pos,ii==(ctr-1));                           //~1A2dR~
	        curPos=pos;                                            //~1A2dI~
        }                                                          //~1A2dI~
        scrollComment(curPos);                                     //~1A4vI~
        if (Dump.Y) Dump.println("replayPrev pos="+curPos+"-->"+pos);//~1A2dI~
        clickSound();                                              //~1A2dI~
    }                                                              //~1A2dI~
    //***************************                                  //~1A2dI~
    private void replayRedo(int Ppos,boolean Plastupdate)          //~1A2dR~
    {                                                              //~1A2dI~
    	ActionMove a=replayTree.get(Ppos);                          //~1A2dI~
//      RB.replayRedo(a,Plastupdate);         //~1A2dR~            //~1A2eR~
        RB.replayRedo(replayNotes,a,Plastupdate);                   //~1A2eI~
    }                                                              //~1A2dI~
    //***************************                                  //~1A2dI~
    private void replayUndo(int Ppos,boolean Plastupdate)          //~1A2dR~
    {                                                              //~1A2dI~
    	ActionMove a,prev;                                         //~1A2dR~
    	a=replayTree.get(Ppos);                                    //~1A2dI~
        if (Ppos>0)                                                //~1A2dI~
        	prev=replayTree.get(Ppos-1);	//set last mark        //~1A2dI~
        else                                                       //~1A2dI~
        	prev=null;                                             //~1A2dI~
        RB.replayUndo(a,prev,Plastupdate);                         //~1A2dR~
    }                                                              //~1A2dI~
    //***************************                                  //~1A24I~
//  private void setTitle(Notes Pnotes,String Pname)               //~1A24I~//~1A31R~
	@Override //FGF                                                //~1A31I~
    protected void setTitle(Notes Pnotes,String Pname)             //~1A31I~
    {                                                              //~1A24I~
        String title;                                              //~1A24I~
    	if (Pnotes!=null)                                          //~1A24I~
        	title=AG.resource.getString(R.string.Title_ReplayGoFrame)+":"+Pnotes.name+Pname;//~1A24I~//~1A2dR~//~1A31R~
        else                                                       //~1A24I~
        	title=AG.resource.getString(R.string.Title_ReplayGoFrame)+":"+Pname;//~1A24I~//~1A2dR~
        (new Component()).setTitle(title);                         //~1A24I~
    }                                                              //~1A24I~
//************************************************                 //~1A15I~//~1A2dI~
    protected void clearLabel()                                      //~1A15I~//~1A2dI~
    {                                                              //~1A15I~//~1A2dI~
        setLabel("");  //always show by setLabel                   //~1A2dR~
    }                                                              //~1A15I~//~1A2dI~
//************************************************                 //~1A2eI~
    public boolean changeCoordLang(Notes Pnotes,NotesTree Pnotestree)//~1A2eR~
    {                                                              //~1A2eI~
		ActionMove a;                                         //~1A2eI~
    //************************                                     //~1A2eI~
    	if (!Pnotes.changeCoord)                                   //~1A2eI~//~1AesR~
        {                                                          //~1AesI~
        	if (setMoveMsg(Pnotes,Pnotestree))                     //~1AesR~
            	return true;                                       //~1AesI~
        	return false;                                                //~1A2eI~
        }                                                          //~1AesI~
        if (Dump.Y) Dump.println("RGF:changeCoordLang");           //~1A2eR~
    	for (int ii=0;ii<Pnotestree.size();ii++)                            //~1A2eI~
        {                                                          //~1A2eI~
	    	a=Pnotestree.get(ii);                                  //~1A2eI~
        	RB.replayRedoDescription(Pnotes,a);                    //~1A2eR~
        }                                                          //~1A2eI~                                      //~1A2eI~
        return true;	//reinit board                             //~1A2eI~
    }                                                              //~1A2eI~
//************************************************                 //~1A4vI~
    private void showAllComment(Notes Pnotes)                      //~1A4vI~
    {                                                              //~1A4vI~
    	Pnotes.display(this,true/*display tree*/);                 //~1A4vR~
    	headerCtr=Pnotes.headerlineCtr;                            //~1A4vI~
    	trailerCtr=Pnotes.trailerlineCtr;                          //~1A4vI~
    	movemsgCtr=Pnotes.movelineCtr;                                  //~1A4vI~
        if (Dump.Y) Dump.println("RGF:showAllComment hdr="+headerCtr+",trailer="+trailerCtr);//~1A4vI~
    }                                                              //~1A4vI~
//************************************************                 //~1A4vI~
	protected void scrollComment(int Ppos)                         //~1A4vI~
    {                                                              //~1A4vI~
    	int pos=Ppos;                                              //~1A4vI~
    	int posmax=treeSize+headerCtr+trailerCtr;	//bottom       //~1A4vI~
        if (pos!=0)                                                //~1A4vI~
        	pos+=headerCtr-1;  //Ppos start from 1:                //~1A4vR~
    	scrollComment(pos,posmax);	//bottom                       //~1A4vR~
    }                                                              //~1A4vI~
//************************************************                 //~1AesI~
    private boolean setMoveMsg(Notes Pnotes,NotesTree Pnotestree)  //~1AesR~
    {                                                              //~1AesI~
		ActionMove a;                                              //~1AesI~
        String msg;                                                //~1AesI~
        boolean rc=false;                                          //~1AesI~
    //************************                                     //~1AesI~
        if (Dump.Y) Dump.println("RGF:setMoveMsg");                //~1AesI~
    	for (int ii=0;ii<Pnotestree.size();ii++)                   //~1AesI~
        {                                                          //~1AesI~
	    	a=Pnotestree.get(ii);                                  //~1AesI~
            if (a.actionMsg==null || a.actionMsg[0]==null)         //~1AesR~
            {                                                      //~1AesI~
            	rc=true;	//not all move msg was set             //~1AesI~
            	break;                                             //~1AesI~
            }                                                      //~1AesI~
        }                                                          //~1AesI~
        if (rc)                                                    //~1AesI~
            for (int ii=0;ii<Pnotestree.size();ii++)               //~1AesR~
            {                                                      //~1AesR~
                a=Pnotestree.get(ii);                              //~1AesR~
                RB.replayRedoDescription2(Pnotes,a);  //add if missing//~1AesR~
            }                                                      //~1AesR~
        return rc;                                                 //~1AesI~
    }                                                              //~1AesI~
}

