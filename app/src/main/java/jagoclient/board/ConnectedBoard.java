//*CID://+1Ah0R~:                             update#=  314;       //~1Ah0R~
//****************************************************************************//~v101I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//1Aex 2016/07/02 (Bug)ReplayClipboard piece direction is reverse when Color=-1(white)//~1AexI~
//1A3a 2013/04/25 screen flicker by duplicate copy() by 1A37       //~1A3aI~
//1A38 2013/04/22 (BUG)When response(@@!move) delayed,accept next select.//~1A38I~
//                Then move confuse to move to old dest from new selected.//~1A38I~
//1A35 2013/04/19 show mark of last moved from position            //~1A35I~
//1A34 2013/04/19 (BUG) tray selection leave selected square mark  //~1A30I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A2c 2013/03/27 display previous move description for reloaded game//~1A1gI~
//1A1g 2013/03/18 FreeBoard:chkmate msg is override by check msg   //~1A1gI~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0f 2013/03/05 check Chackmate for gameover                     //~1A0fI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~1A0eI~
//1A0a 2013/03/03 issue "check" sound                              //~1A0aI~
//101g 2013/02/09 captured mark remains at after partner move after I captured//~v101I~
//****************************************************************************//~v101I~
package jagoclient.board;


import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;                                            //~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.awt.Graphics;
import com.Asgts.awt.Canvas;//~v101R~
import com.Asgts.rene.util.sound.SoundList;

import jagoclient.Dump;
import jagoclient.sound.JagoSound;
import static jagoclient.partner.GameQuestion.*;                     //~v101I~
import static jagoclient.board.Field.*;
import jagoclient.FreeGoFrame;                           //~v101I~

/**
This board overrides some methods from the Board class
for connected boards. It knows a ConnectedGoFrame to
ask for permission of operations and to report moves
to it.
*/

public class ConnectedBoard extends Board
{                                                                  //~@@@2R~
                                                                   //~@@@2I~
	ConnectedGoFrame CGF;                                          //~@@@2I~
    int iref,jref;                                                 //~@@@2I~
    public  int gameoverReason;                                    //~v101I~
    public  static final int GOR_NOPATH                      =0x01;//~v101I~
    public  static final int GOR_LEAVECHECK                  =0x02;//~v101I~
    public  static final int GOR_2PAWN                       =0x04;//~v101I~
    public  static final int GOR_UNMOVABLEDROP               =0x08;//~v101I~
    public  static final int GOR_DROPPAWNCHECKMATE           =0x10;//~v101I~
    public  static final int GOR_TIMEOUT                     =0x20;//~v101I~
    public  static final int GOR_CHECKMATE                   =0x40;//~v101I~
    public  static final int GOR_RESIGN                      =0x80;//~v101I~
    public  static final int GOR_CONNERR                   =0x0100;//~v101I~
    public  static final int GOR_CAPTUREDKING              =0x0200;//~1A0fI~
    public  static final String MOVE_DESCRIPTION="[";              //~1A2eI~
    boolean swCaptured;                                            //~@@@2M~
//    private boolean swDeletedCaptured;                             //~@@@2I~//~v101R~
    private int capturedPiece;           	//chk at infoMoved         //~1A2dI~
    private int capturedNumber;                                        //~1A2dI~
//***************************************************************************//~1A2cI~
	public ConnectedBoard (int size, ConnectedGoFrame gf)
	{	super(size,gf);
		CGF=gf;
        aRules=new Rules(CGF,this,S);                             //~v1A0R~//~1A00I~//~v101I~
	}
//***************************************************************************//~1A2cI~

//    public synchronized void setmouse (int i, int j, int c)      //~v101R~
//    {   if (Pos.isMain() && CGF.wantsmove()) return;             //~v101R~
//        super.setmouse(i,j,c);                                   //~v101R~
//    }                                                            //~v101R~

	/**
	In a ConnectedBoard you cannot fix the game tree this way.
	*/
//    public synchronized void setmousec (int i, int j, int c) {}  //~v101R~
    //*override Board:movemouse()******************************    //~@@@2R~
//  public synchronized void movemouse (int i, int j)              //~v101R~
    public synchronized void movemouse (int i, int j,int Ppiece)   //~v101I~
	{	if (Pos.haschildren()) return;
//      if (P.color(i,j)!=0) return;                               //~v101R~
//        int captured=Field.nonPromoted(P.piece(i,j));            //~v101R~
        if (P.color(i,j)!=0)                                       //~v101I~
        	capturePiece(i,j);	//delete captured                  //~v101I~
//        if (captured==1 && capturei==i && capturej==j &&         //~@@@2R~
//            GF.getParameter("preventko",true)) return;           //~@@@2R~
        int piece=aRules.chkPromotion(i,j,iSelected,jSelected,Ppiece);//~v101R~
		if (Pos.isMain() && CGF.wantsmove())
//  	{	if (CGF.moveset(i,j))                                  //~@@@2R~
    	{                                                          //~@@@2I~
//  		if (CGF.moveset(i,j,P.piece(i,j)))                     //~@@@2I~//~v101R~
//  		if (CGF.moveset(i,j,piece))                            //~v101R~
//            if (CGF.moveset(i,j,piece,captured))                 //~v101R~
//          if (CGF.moveset(i,j,piece,swDropped))                  //~1Ah0R~
            if (CGF.moveset(i,j,piece,swDropped,iSelected,jSelected))	//pass old position for CSA move//~1Ah0I~
			{	sendi=i; sendj=j;
				update(i,j); copy();
				MyColor=P.color();
                if (Dump.Y) Dump.println("CB:movemouse copyed MyColor="+MyColor);      //~v101I~//+1Ah0I~
			}
//            JagoSound.play("click","",false);                    //~@@@2R~
		}
//  	else set(i,j); // try to set a new move                    //~v101R~
    	else set(i,j,piece); // try to set a new move              //~v101R~
//        pieceMoved(i,j);                                         //~v101R~
	}

	/**
	Completely remove a group (at end of game, before count), and
	note all removals. This is only allowed in end nodes and if
	the GoFrame wants the removal, it gets it.
	*/
//    public synchronized void removegroup (int i0, int j0)        //~@@@2R~
//    {   if (Pos.haschildren()) return;                           //~@@@2R~
//        if (P.color(i0,j0)==0) return;                           //~@@@2R~
//        if (CGF.wantsmove() && ((Node)Pos.content()).main())     //~@@@2R~
//        {   CGF.moveset(i0,j0);                                  //~@@@2R~
//        }                                                        //~@@@2R~
//        super.removegroup(i0,j0);                                //~@@@2R~
//    }                                                            //~@@@2R~

	/**
	Take back the last move.
	*/
//    public synchronized void undo ()                             //~2C26R~
//    {   if (Pos.isMain()                                         //~2C26R~
//            && CGF.wantsmove())                                  //~2C26R~
//        {   if (!Pos.haschildren())                              //~2C26R~
//            {   if (State!=1 && State!=2) clearremovals();       //~2C26R~
//                CGF.undo();                                      //~2C26R~
//            }                                                    //~2C26R~
//            return;                                              //~2C26R~
//        }                                                        //~2C26R~
//        super.undo();                                            //~2C26R~
//    }                                                            //~2C26R~

	/**
	Pass (report to the GoFrame if necessary.
	*/
//    public synchronized void pass ()                             //~2C26R~
//    {   if (Pos.haschildren()) return;                           //~2C26R~
//        if (GF.blocked() && Pos.isMain()) return;                //~2C26R~
//        if (Pos.isMain() && CGF.wantsmove())                     //~2C26R~
//        {   CGF.movepass(); return;                              //~2C26R~
//        }                                                        //~2C26R~
//        super.pass();                                            //~2C26R~
//    }                                                            //~2C26R~

	/**
	This is used to fix the game tree (after confirmation).
	Will not be possible, if the GoFrame wants moves.
	*/
//    public synchronized void insertnode ()                       //~2C26R~
//    {   if (Pos.isLastMain() && CGF.wantsmove()) return;         //~2C26R~
//        super.insertnode();                                      //~2C26R~
//    }                                                            //~2C26R~
	
	/**
	In a ConnectedBoard you cannot delete stones.
	*/
	public synchronized void deletemousec (int i, int j) {}
//**************************************************************** //~@@@@I~//~@@@2M~
//*Board.java is too large;shift to ConnectedBoard                 //~@@@2I~
//**************************************************************** //~@@@2I~
////****************************************************************//~@@@@R~//~@@@2M~
////*draw piece:Bishop and knight                                  //~@@@@R~//~@@@2M~
////****************************************************************//~@@@@R~//~@@@2M~
//    private void drawPiece(Graphics Pg,int Pcolor,int Pmark,int Pi,int Pj)//~@@@@R~//~@@@2M~
//    {                                                            //~@@@@R~//~@@@2M~
//        int colidx;                                              //~@@@@R~//~@@@2M~
//    //*******************                                        //~@@@@R~//~@@@2M~
//        if (Dump.Y) Dump.println("drawPiece i="+Pi+",j="+Pj+",Grafics="+Pg.toString());//~@@@@R~//~@@@2M~
//        if (YourColor>0)    //black                              //~@@@@R~//~@@@2M~
//            colidx=Pcolor>0?0:3;    //black or white-down        //~@@@@R~//~@@@2M~
//        else                                                     //~@@@@R~//~@@@2M~
//            colidx=Pcolor>0?2:1;    //black-down or white        //~@@@@R~//~@@@2M~
//        Image piece=SpieceImage[colidx][getPieceIndex(Pmark)];   //~@@@@R~//~@@@2M~
//        int xi=O+OTU+Pi*D+PIECE_MARGIN+1;                        //~@@@@R~//~@@@2M~
//        int xj=O+OTU+Pj*D+PIECE_MARGIN+1;;                       //~@@@@R~//~@@@2M~
//        Pg.drawImage(piece,xi,xj);                               //~@@@@R~//~@@@2M~
//        if (Dump.Y) Dump.println("drawPiece end i="+Pi+",j="+Pj);//~@@@@R~//~@@@2M~
//    }                                                            //~@@@@R~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
//*piece selection chk                                             //~@@@@I~//~@@@2M~
//*rc:-1:err,1:selected,0:do move                                  //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	@Override //Board                                              //~@@@2I~
//  protected int selectPiece(int Pstate,int i,int j)                //~@@@@I~//~@@@2R~//~1Ah0R~
    public int selectPiece(int Pstate,int i,int j)                 //~1Ah0I~
    {                                                              //~@@@@I~//~@@@2M~
    	int col,rc=-1,rc2;                                             //~@@@@I~//~@@@2M~//~v101R~
    //*******************                                          //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("selectPiece state="+Pstate+",swSelected="+swSelected+",i="+i+",j="+j);//~@@@@I~//~@@@2M~
//        if (moveNumber==1)  //before 1st move                    //~@@@2R~
//            CGF.drawInitialCaptured();  //height is not determined at constructor//~@@@2R~
    	if (Pstate==1)                                             //~@@@@I~//~@@@2M~
        	col=1;   //black                                       //~@@@@I~//~@@@2M~
        else                                                       //~@@@@I~//~@@@2M~
        	col=-1;  //white                                       //~@@@@I~//~@@@2M~
		if (!CGF.swLocalGame && col!=YourColor)	//your turn in partner match//~@@@2I~
        {                                                          //~@@@2I~
			errNotYourTurn(-YourColor);                            //~@@@2R~
            return rc;    	                                       //~@@@2I~
        }                                                          //~@@@2I~
        if (CGF.swWaitingPartnerResponse)                          //~1A38I~
        {                                                          //~1A38I~
			errWaitingPartnerResponse();                           //~1A38I~
            return rc;                                             //~1A38I~
        }                                                          //~1A38I~
    	if (swSelected)                                            //~@@@@I~//~@@@2M~
        {                                                          //~@@@@I~//~@@@2M~
			if (col==P.color(i,j))	//your piece                   //~@@@@I~//~@@@2R~
            {                                                      //~@@@@I~//~@@@2M~
//                if ((GameOptions & GAMEOPT_RESELECTABLE)==0)//~@@@@R~//~@@@2M~//~v101R~
//                {                                                  //~@@@@I~//~@@@2M~//~v101R~
//                    if (iSelected==i && jSelected==j)              //~@@@2I~//~v101R~
//                        rc=1;   //ignore if duplicated             //~@@@2I~//~v101R~
//                    else                                           //~@@@2I~//~v101R~
//                    {                                              //~@@@2I~//~v101R~
//                        errMissTouch(iSelected,jSelected);                                 //~@@@@I~//~@@@2R~//~v101R~
//                        rc=-2;                                         //~@@@@I~//~@@@2R~//~v101R~
//                    }                                              //~@@@2I~//~v101R~
//                }                                                  //~@@@@I~//~@@@2M~//~v101R~
//                else                                               //~@@@@I~//~@@@2M~//~v101R~
                {                                                  //~@@@@I~//~@@@2M~
            		if (iSelected==i && jSelected==j)	//resetreq //~@@@@I~//~@@@2M~
                	{	                                           //~@@@@I~//~@@@2M~
//                        swSelected=false;                          //~@@@@I~//~@@@2M~//~v101R~
//                        update(iSelected,jSelected);    //clear mark on old square//~@@@@I~//~@@@2M~//~v101R~
                        if (Dump.Y) Dump.println("select reset(touch same pos)");//~v101I~
                        rc=-1;                                     //~@@@@I~//~@@@2M~
                	}                                              //~@@@@I~//~@@@2M~
                    else                                           //~@@@@I~//~@@@2M~
                    {                                              //~@@@@I~//~@@@2M~
				    	updateSelected(i,j);                       //~@@@2I~
						rc=1;	//selected                         //~@@@@I~//~@@@2M~
                    }                                              //~@@@@I~//~@@@2M~
                }                                                  //~@@@@I~//~@@@2M~
            }                                                      //~@@@@I~//~@@@2M~
            else                                                   //~@@@@I~//~@@@2M~
            {                                                      //~v101I~
                rc2=aRules.isOnPiecePath(true/*errmsg*/,col,i,j,iSelected,jSelected);                               //~@@@@I~//~@@@2M~//~v101R~//~1A0fR~
                if (rc2==1)                                        //~v101R~
                {                                                      //~@@@@I~//~@@@2M~//~v101R~
    //              swSelected=false; set after color was set          //~@@@@R~//~@@@2M~//~v101R~
//                  P.setPiece(i,j,P.piece(iSelected,jSelected));         //~@@@@I~//~@@@2M~//~v101R~
//                  P.setPiece(i,j,P.color(iSelected,jSelected),P.piece(iSelected,jSelected));//later set(i,j,piece) is called//~v101R~
                    rc=0;   //do move                                              //~@@@@I~//~@@@2M~//~v101R~
//                    rc=aRules.isLeaveCheck(P.piece(iSelected,jSelected),col,i,j)==1?0:-1;//1:ok,0:err//~v101R~
                }                                                      //~@@@@I~//~@@@2M~//~v101R~
//                else                                                   //~@@@@I~//~@@@2M~//~v101R~
//                {                                                //~v101R~
//                    rc=errNoPath(iSelected,jSelected,P.piece(iSelected,jSelected),P.color(iSelected,jSelected),i,j);//0:if gameover ,-1 if not                                       //~@@@@I~//~@@@2R~//~v101R~
//                }                                                //~v101R~
            }                                                      //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
        else              //do select                              //~@@@@I~//~@@@2M~
        {                                                          //~@@@@I~//~@@@2M~
			if (col==P.color(i,j)) 	//your piece                   //~@@@2R~
            {                                                      //~@@@@I~//~@@@2M~
            	swSelected=true;            	//clear mark on old square//~@@@@I~//~@@@2M~                       //~@@@@I~//~@@@2M~
                iSelected=i; jSelected=j;                          //~@@@@I~//~@@@2M~
                iSelectedPiece=P.piece(i,j);                       //~v101I~
				update(iSelected,jSelected);
				rc=1;	//selected                                 //~@@@@I~//~@@@2M~
            }                                                      //~@@@@I~//~@@@2M~
            else                                                   //~@@@2M~
            if (P.color(i,j)!=0)                                   //~@@@2M~
				errNotYourTurn(col);                               //~@@@2R~
            else	//free space                                   //~v101I~
            {                                                      //~v101I~
	        	int dropPiece=CGF.aCapturedList.chkSelected(col);	//ca drop captured//~v101R~
	        	if (dropPiece>=0)                                  //~v101I~
                {                                                  //~v101I~
                	rc2=aRules.chkDroppable(i,j,col,dropPiece); //1:ok,0:reject,-1:gameover//~v101R~
                	if (rc2>0)	//drop OK                          //~v101R~
                    {                                              //~v101I~
	                	iSelectedPiece=CGF.aCapturedList.getSelectedPiece(col);	//ca drop captured//~v101R~
                        rc=2;                                      //~v101R~
//                        rc=aRules.isLeaveCheck(dropPiece,col,i,j)==1?2:-1;//1:ok,0:err//~v101R~
                    }                                              //~v101I~
                    else                                           //~v101I~
						rc=rc2-1;    //-1:err,-2:gameover          //~v101R~
                }                                                  //~v101I~
            }                                                      //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("selectPiece rc="+rc+",swSelected="+swSelected+",i="+iSelected+",j="+jSelected);//~@@@@I~//~@@@2M~
        if (rc==1)	//selected                                     //~@@@2I~
        {                                                          //~@@@2I~
//            if (swDeletedCaptured)                 //~@@@@I~       //~@@@2I~//~v101R~
//            {                                          //~@@@@I~   //~@@@2I~//~v101R~
//                swDeletedCaptured=false;           //~@@@@I~       //~@@@2I~//~v101R~
//            }                                          //~@@@@I~   //~@@@2I~//~v101R~
        	CGF.pieceSelected(iSelected,jSelected,P.color(iSelected,jSelected));                //~@@@2R~//~v101R~
        }                                                          //~@@@2I~
        else                                                       //~@@@2I~
        if (rc==0)	//will be moved                                //~@@@2I~
        {                                                          //~@@@2I~
        	swDropped=false;                                       //~v101I~
//          CGF.pieceMoved(P.color(iSelected,jSelected));          //~@@@2I~//~v101R~
            CGF.pieceMoved(col);                                   //~v101I~
        }                                                          //~@@@2I~
        else                                                       //~v101I~
        if (rc==2)	//drop piece                                   //~v101I~
        {                                                          //~v101I~
        	swDropped=true;                                        //~v101I~
            CGF.pieceMoved(col);                                   //~v101I~
            rc=0;                                                  //~v101I~
        }                                                          //~v101I~
        return rc;                                                 //~@@@@I~//~@@@2M~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~v101I~
//*from CapturedList at selcect piece on tray                      //~1A30I~
//*or partner dropped piece                                        //~1A30I~
//**************************************************************** //~1A30I~
	public void resetSelectedPiece()                            //~v101I~
    {                                                              //~v101I~
        if (Dump.Y) Dump.println("resetSelected Piece swSeleccted="+swSelected+",iselected="+iSelected+",jSelected="+jSelected);//~v101I~
        if (swSelected)                                            //~1A34R~
        {                                                          //~1A34R~
       		swSelected=false;                                          //~v101I~//~1A34R~
        	update(iSelected,jSelected);    //clear mark on old square//~1A34R~
            copy();                                                //~1A34I~
        }                                                          //~1A34R~
//      if (swCursorMovedFrom)                                     //~1A35R~
//  		resetMovedFrom();	//clear mark of last moved from    //~1A35R~
    }                                                              //~v101I~
//**************************************************************** //~@@@@I~//~@@@2M~
	@Override //Board                                              //~@@@2I~
	protected void deleteMovedPiece(int i,int j)                     //~@@@@R~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~
    //*******************                                          //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("deleteMoved i="+iSelected+",j="+jSelected);//~@@@@I~//~@@@2M~//~1Ah0R~
       	swSelected=false;                                          //~@@@@I~//~@@@2M~
		if (!isIdleFreeBoard())                                    //~1A35I~
	        swCursorMovedFrom=true;                                //~1A35R~
        lastifrom=iSelected; lastjfrom=jSelected;	//draw mark of last moved from//~1A35I~
		deletemousecPiece(iSelected,jSelected);                    //~@@@@R~//~@@@2M~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	@Override //Board                                              //~@@@2I~
	protected void drawSelected (Graphics g,int i, int j)            //~@@@@R~//~@@@2M~
	{                                                              //~@@@@I~//~@@@2M~
        int xx,yy;                                                 //~@@@@I~//~@@@2M~
		if (g==null) return;                                       //~@@@@I~//~@@@2M~
		xx=O+OTU+i*D; yy=O+OTU+j*D;                                //~@@@@R~//~@@@2M~
//        g.setColor(selectedColor);                                   //~@@@@R~//~@@@2R~
//        g.drawRect(xx+1,yy+1,D-2,D-2);                             //~@@@@R~//~@@@2R~
//        g.drawRect(xx+2,yy+2,D-4,D-4);                             //~@@@@I~//~@@@2R~
          g.drawRect(AG.selectedColor,2/*line Width*/,xx+5,yy+5,D-9/*width*/,D-9/*height*/);//~@@@2R~
        if (Dump.Y) Dump.println("drawSelected i="+i+",j="+j+",x="+xx+",y="+yy+",D="+D+",g="+g.toString());//~@@@@R~//~@@@2M~
	}                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~1A35I~
	@Override //Board                                              //~1A35I~
	protected void drawLastFrom (Graphics g,int i, int j)          //~1A35I~
	{                                                              //~1A35I~
        int xx,yy;                                                 //~1A35I~
		if (g==null) return;                                       //~1A35I~
		xx=O+OTU+i*D; yy=O+OTU+j*D;                                //~1A35I~
          g.drawRect(AG.lastFromColor,2/*line Width*/,xx+5,yy+5,D-9/*width*/,D-9/*height*/);//~1A35I~
        if (Dump.Y) Dump.println("drawLastFrom i="+i+",j="+j+",x="+xx+",y="+yy+",D="+D+",g="+g.toString());//~1A35I~//~1Ah0R~
	}                                                              //~1A35I~
//**************************************************************** //~@@@2I~
	protected void capturePiece(int Pi,int Pj)                     //~@@@2R~//~v101R~
    {                                                              //~@@@2I~
    //**********************                                       //~@@@2M~
		int color=P.color(Pi,Pj); //captured color                 //~v101I~
		int piece=P.piece(Pi,Pj);                                  //~v101I~
        if (Dump.Y) Dump.println("capturedPiece i="+Pi+",j="+Pj+",piece="+piece+",color="+color);//~v101R~
//        infoCaptured(Pi,Pj,piece,-color);                        //~v101R~
        capturedPiece=piece;           	//promoted status for undo //~1A2dI~
        capturedNumber=moveNumber+1;  //up at pieceMoved(after this func)//~1A2dM~
        piece=Field.nonPromoted(piece);                            //~v101R~
        if (piece<PIECE_KING)                                      //~v101I~
//          GF.updateCapturedList(color,piece); //add to captured List//~v101R~//~1A0eR~
            GF.updateCapturedList(color,piece,1/*count up*/); //add to captured List//~1A0eI~
        else                                                       //~v101I~
        	CGF.gameover(1/*captured King*/,-color/*winner*/);     //~v101R~
        P.setPiece(Pi,Pj,0/*color*/,0/*piece*/);                   //~v101I~
    }                                                              //~@@@2I~
//**************************************************************** //~v101I~
	public void partnerCapturePiece(int Pi,int Pj,int Pcolor/*captured*/,int Ppiece)//~v101R~
    {                                                              //~v101I~
    //**********************                                       //~v101I~
        if (Dump.Y) Dump.println("PartnerCapturedPiece i="+Pi+",j="+Pj+",piece="+Ppiece+",color="+Pcolor);//~v101I~
//      GF.updateCapturedList(Pcolor,Ppiece); //add to captured List//~v101I~//~1A0eR~
        GF.updateCapturedList(Pcolor,Ppiece,1/*count up*/); //add to captured List//~1A0eI~
    }                                                              //~v101I~
//**************************************************************** //~1A0aI~
	public void partnerDroppedPiece(int Pi,int Pj,int Pcolor/*dropped*/,int Ppiece)//~1A0aI~
    {                                                              //~1A0aI~
    //**********************                                       //~1A0aI~
        if (Dump.Y) Dump.println("PartnerDroppedPiece i="+Pi+",j="+Pj+",piece="+Ppiece+",color="+Pcolor);//~1A0aI~
        GF.updateCapturedList(Pcolor,Ppiece,-1/*count down*/); //count down      //~1A0aI~//~1A0eR~
		resetSelectedPiece();	//reset selected square mark       //~1A30I~
    }                                                              //~1A0aI~
////**************************************************************** //~@@@2I~//~v101R~
////*draw 1 by 1 captured piece                                      //~@@@2I~//~v101R~
////**************************************************************** //~@@@2I~//~v101R~
//    public void deleteCapturedPiece(int Pi,int Pj)                 //~@@@2R~//~v101R~
//    {                                                              //~@@@2I~//~v101R~
//        int color=P.color(Pi,Pj);                                  //~@@@2I~//~v101R~
//        int piece=P.piece(Pi,Pj);                                  //~@@@2I~//~v101R~
//        infoCaptured(Pi,Pj,piece,color);                           //~@@@2R~//~v101R~
//        piece=Field.nonPromoted(piece);                          //~v101R~
//        if (piece<PIECE_KING)                                    //~v101R~
//            GF.updateCapturedList(color,piece); //add to captured List //~@@@2R~//~v101R~
//        P.setPiece(Pi,Pj,0/*color*/,0/*piece*/);                 //~v101R~
//    }                                                              //~@@@2I~//~v101R~
//**************************************************************** //~@@@2I~
//*partner send piece selected                                   * //~@@@2I~
//**************************************************************** //~@@@2I~
	public void receiveSelected(int Pi,int Pj)                     //~@@@2R~
    {                                                              //~@@@2I~
//        if (swDeletedCaptured)                                   //~v101R~
//        {                                                        //~v101R~
//            swDeletedCaptured=false;                             //~v101R~
//            clearCapturedMark();    //for the case captured partner piece,next event is partner's move//~v101R~
//        }                                                        //~v101R~
//      if (swCursorMovedFrom)                                     //~1A35R~
//  		resetMovedFrom();	//clear mark of last moved from    //~1A35R~
    	updateSelected(Pi,Pj);                                      //~@@@2I~
		showinformation();                                         //~@@@2I~
//  	copy(); // show position                                   //~@@@2I~//~1A3aR~
    }                                                              //~@@@2I~
//**************************************************************** //~@@@2I~
//*draw selected piece                                             //~@@@2I~
//**************************************************************** //~@@@2I~
    void updateSelected(int Pi,int Pj)                             //~@@@2I~
    {                                                              //~@@@2I~
        swSelected=false;                                          //~@@@2I~
        update(iSelected,jSelected);    //clear mark on old square //~@@@2I~
        iSelected=Pi; jSelected=Pj;                                //~@@@2I~
        iSelectedPiece=P.piece(Pi,Pj);                             //~v101I~
        swSelected=true;                                           //~@@@2I~
        if (Dump.Y) Dump.println("CB:updateSelected i="+Pi+",j="+Pj+",piece="+iSelectedPiece);//~1Ah0I~
        update(iSelected,jSelected);    //clear mark on old square //~@@@2I~
    }                                                              //~@@@2I~
//**************************************************************** //~1A35I~
//*clear last moved from                                           //~1A35R~
//**************************************************************** //~1A35I~
	@Override //Board                                              //~1A35I~
    public void resetMovedFrom()                                   //~1A35R~
    {                                                              //~1A35I~
    	if (Dump.Y) Dump.println("CB:resetMovedFrom sw="+swCursorMovedFrom+",lastifrom="+lastifrom+",j="+lastjfrom);//~1A35I~
    	if (swCursorMovedFrom)                                     //~1A35I~
        {                                                          //~1A35I~
            swCursorMovedFrom=false;                               //~1A35I~
        	update(lastifrom,lastjfrom);                           //~1A35I~
        }                                                          //~1A35I~
    }                                                              //~1A35I~
//**************************************************************** //~@@@2M~
//*rc:-1:err reject,0:gameover after moved                         //~v101I~
//**************************************************************** //~v101I~
	public int errNoPath(int Pi1,int Pj1,int Ppiece,int Pcolor,int Pi2,int Pj2)                                       //~@@@@I~//~@@@2R~//~v101R~
    {                                                              //~@@@@I~//~@@@2M~
    	int rc=-1;   //err reject                                  //~v101I~
    //*********************                                        //~v101I~
//        if ((GameOptions & GAMEOPT_NOTREMOVABLE)!=0)  //~@@@2I~  //~v101R~
//        {                                                          //~@@@2I~//~v101R~
//            errPass(Pi1,Pj1,Ppiece,Pcolor,Pi2,Pj2);                //~@@@2R~//~v101R~
//            return;                                                //~@@@2I~//~v101R~
//        }                                                          //~@@@2I~//~v101R~
//        String msg=AG.resource.getString(R.string.ErrNoPathPiece); //~@@@2M~//~v101R~
        String pos1=Field.coordinate(Pi1,Pj1,S,YourColor);         //~@@@2M~
        String pos2=Field.coordinate(Pi2,Pj2,S,YourColor);         //~@@@2M~
        String piece=CGF.pieceName(Ppiece);                        //~@@@2M~
//        msg=msg+": "+piece+" "+pos1+" --> "+pos2;                  //~@@@2M~//~v101R~
        String msg=AG.resource.getString(R.string.ErrNoPathPiece,piece,pos1,pos2);//~v101I~
//        CGF.appendComment(msg+"\n");                               //~@@@2M~//~v101R~
    	if ((GameOptions & GAMEOPT_FOUL_MISSMOVE)==0)	//gameover or reject drop//~v101R~
        {                                                          //~1A0aI~
            CGF.setLabel(msg,false/*not append*/);                 //~1A0aI~
	    	AView.showToast(msg);                  //~@@@@I~           //~@@@2M~//~v101I~
        }                                                          //~1A0aI~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	if ((gameoverReason & GOR_NOPATH)!=0)	//2nd call     //~v101I~
            {                                                      //~v101I~
	        	CGF.gameoverMessage(-Pcolor/*winner*/,R.string.ErrNoPathPieceGameover);//~v101R~
            }                                                      //~v101I~
            else	//1st time, gameover msg later                 //~v101I~
            {                                                      //~v101I~
	        	gameoverReason|=GOR_NOPATH;                        //~v101I~
                rc=0;	//once move piece                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~@@@@I~//~@@@2M~
////**************************************************************** //~@@@2I~//~v101R~
//    private void errPass(int Pi1,int Pj1,int Ppiece,int Pcolor,int Pi2,int Pj2)//~@@@2R~//~v101R~
//    {                                                              //~@@@2I~//~v101R~
//        String msg=AG.resource.getString(R.string.ErrPass);        //~@@@2I~//~v101R~
//        AView.showToast(msg);                                      //~@@@2I~//~v101R~
//        String pos1=Field.coordinate(Pi1,Pj1,S,YourColor);         //~@@@2I~//~v101R~
//        String pos2=Field.coordinate(Pi2,Pj2,S,YourColor);         //~@@@2I~//~v101R~
//        String piece=CGF.pieceName(Ppiece);                        //~@@@2I~//~v101R~
//        String color=Pcolor>0 ? AG.BlackSign : AG.WhiteSign;       //~@@@2I~//~v101R~
//        msg="["+Integer.toString(moveNumber+1)+"] "+color+" ("+msg+") "+pos1+" --> "+pos2+ " ("+piece+")";//~@@@2R~//~v101R~
//        CGF.appendComment(msg+"\n");                               //~@@@2I~//~v101R~
//        CGF.errPass();                                             //~@@@2I~//~v101R~
//    }                                                              //~@@@2I~//~v101R~
//**************************************************************** //~@@@2I~
//*pass notified from partner (@@pass)                             //~@@@2I~
//**************************************************************** //~@@@2I~
//	public void notifiedPass()                                    //~@@@2I~
//    {                                                              //~@@@2I~
//        String msg=AG.resource.getString(R.string.ErrPass);        //~@@@2I~
//    	AView.showToast(msg);                                      //~@@@2I~
//        String color=maincolor()>0 ? AG.BlackSign : AG.WhiteSign;  //~@@@2I~
//        msg="["+Integer.toString(moveNumber+1)+"] "+color+" ("+msg+")";//~@@@2I~
//        CGF.appendComment(msg+"\n");                               //~@@@2I~
//    }                                                              //~@@@2I~
//**************************************************************** //~@@@2M~
	@Override //Board                                                  //~@@@2I~
	protected void infoMoved(int Pi1/*from*/,int Pj1,int Ppiece,int Pcolor/*movedPieceColor*/,int Pi2/*to*/,int Pj2)//~@@@2R~//~v101R~
    {                                                              //~@@@2M~
        if (Dump.Y) Dump.println("infoMoved color="+Pcolor+",piece="+Ppiece+",i="+Pi2+",j="+Pj2+"<--("+Pi1+","+Pj1+")");//~1Ah0I~
    //*after moved piece is deleted                                //~v101I~
//        String pos1=Field.coordinate(Pi1,Pj1,S,YourColor);         //~@@@2M~//~v101R~
//        String pos2=Field.coordinate(Pi2,Pj2,S,YourColor);         //~@@@2M~//~v101R~
//        String piece=CGF.pieceName(Ppiece);                         //~@@@2M~//~v101R~
//        String color=Pcolor>0 ? AG.BlackSign : AG.WhiteSign;       //~@@@2R~//~v101R~
//        int promoted=P.piece(Pi1,Pj1);                           //~v101R~
//        String promotion;                                        //~v101R~
//        if (promoted>Ppiece)                                     //~v101R~
//            promotion=" "+AG.resource.getString(R.string.Promoted);//~v101R~
//        else                                                     //~v101R~
//            promotion="";                                        //~v101R~
//        String msg="["+Integer.toString(moveNumber)+"] "+color+" "+pos1+" --> "+pos2+ " ("+piece+promotion+")";//~@@@2R~//~v101R~
		String check="";                                           //~v101I~
        boolean checkmateFB=false;                                 //~1A1gI~
        if (aRules.isCheck(Pcolor))	//chk also chkmate                            //~v101R~//~1A0fR~
        {                                                      //~1A0aI~//~1A0fR~
	        if ((GameOptions & GAMEOPT_NOTIFYCHECK)!=0)                //~v101R~//~1A0fI~
            {                                                      //~1A0fI~
                check=AG.resource.getString(R.string.CallCheck);   //~v101I~//~1A0fR~
                if (CGF instanceof FreeGoFrame)                    //~1A1gM~
                {                                                  //~1A1gI~
                	if (aRules.isCheckmated)                       //~1A1gR~
                        checkmateFB=true;                          //~1A1gM~
                    else                                           //~1A1gI~
	                	CGF.setLabel(check,true/*append*/);        //~1A1gM~
                }                                                  //~1A1gI~
                else                                               //~1A1gM~
        		if ((gameoverReason & GOR_CHECKMATE)==0)//~1A0fI~
                	CGF.setLabel(check,true/*append*/);                //~1A0aR~//~1A0fR~
              if (checkmateFB)                                     //~1A1gI~
    			CGF.gameoverSound(Pcolor);                                     //~1A08I~//~1A1gI~
              else                                                 //~1A1gI~
                JagoSound.play(SoundList.CHECK,false/*not change to beep when beeponly option is on*/,true/*enq if busy*/);//~1A0aR~//~1A0fR~
            }                                                      //~1A0fI~
            else                                                   //~1A1gI~
            {                                                      //~1A1gI~
                if (CGF instanceof FreeGoFrame)                    //~1A1gI~
                {                                                  //~1A1gI~
                	if (aRules.isCheckmated)                       //~1A1gI~
		    			CGF.gameoverSound(Pcolor);                 //~1A1gI~
                }                                                  //~1A1gI~
            }                                                      //~1A1gI~
        }                                                      //~1A0aI~//~1A0fR~
//        String pos2=aRules.movePositionDescription(Pi2,Pj2);     //~v101R~
//        String piece=CGF.pieceName(Ppiece);                      //~v101R~
        String color=Pcolor>0 ? AG.BlackSign : AG.WhiteSign;       //~v101I~
//        int promoted=P.piece(Pi2,Pj2);                           //~v101R~
//        String promotion;                                        //~v101R~
//        if (promoted>Ppiece)                                     //~v101R~
//            promotion=AG.Promoted;                               //~v101R~
//        else                                                     //~v101R~
//            promotion="";                                        //~v101R~
        int capturedpiece=(capturedNumber==moveNumber)?capturedPiece:0;//~1A2dI~
        int pieceTo=swDropped?Ppiece:P.piece(Pi2,Pj2);             //~1A2dI~
//      ActionMove a=new ActionMove(moveNumber,Pcolor,Ppiece,swDropped,Pi1,Pj1,Pi2,Pj2,capturedpiece,pieceTo);//~1A1gI~//~1A2dR~//~1A2eR~
        ActionMove a=new ActionMove(YourColor,moveNumber,Pcolor,Ppiece,swDropped,Pi1,Pj1,Pi2,Pj2,capturedpiece,pieceTo);//~1A2eI~
//      CGF.notifyMoved(a); 	//pass data to GtpGoFrame          //~1Ah0R~
        CGF.notesTree.add(a);                                          //~1A1gI~
        String desc=aRules.moveDescription(Pcolor,Ppiece,swDropped,Pi1,Pj1,Pi2,Pj2);//~v101I~
//        String msg="["+Integer.toString(moveNumber)+"] "+color+pos2+piece+desc+promotion//~v101R~
//                   +" "+check;                                   //~v101R~
//      String msg="["+Integer.toString(moveNumber)+"] "+color+desc+" "+check;//~v101I~//~1A0aR~
//      String msg="["+Integer.toString(moveNumber)+"] "+color+desc+" ";//~1A0aI~//~1A2eR~
        String msg=MOVE_DESCRIPTION/*"["*/+Integer.toString(moveNumber)+"] "+color+desc+" ";//~1A2eI~
        if (Dump.Y) Dump.println("infoMoved msg="+msg);            //~v101I~
        CGF.appendComment(msg+"\n");                               //~@@@2M~
        if (isGameover(Pi1,Pj1,Ppiece,Pcolor,Pi2,Pj2))         //~v101R~
        	return;                                                //~v101I~
        CGF.displayCurrentColor(Pcolor);                           //~v101I~
    }                                                              //~@@@2M~
//**************************************************************** //~1A2eI~
//*from ActionMove when reloaded Notes lang is different           //~1A2eI~
//**************************************************************** //~1A2eI~
    public String getMoveDescription_OtherLang(ActionMove Paction) //~1A30I~
    {                                                              //~1A30I~
		return getMoveDescription_OtherLang(Paction,false);        //~1A30I~
    }                                                              //~1A30I~
    public String getMoveDescription_OtherLang(ActionMove Paction,boolean Pnotesrecord) //~1A2eR~//~1A30R~
    {                                                              //~1A2eI~
        String desc=null;                                          //~1A2eI~
//  	if (Paction.actionMsg!=null && Paction.actionMsg[0]!=null  //~1A2eI~//~1A30R~
//      &&  Paction.actionMsg[0].charAt(0)==MOVE_DESCRIPTION.charAt(0))//~1A2eI~//~1A30R~
		if (Pnotesrecord                                           //~1A30I~
        ||                                                         //~1A30I~
    	       (Paction.actionMsg!=null && Paction.actionMsg[0]!=null//~1A30I~
            &&  Paction.actionMsg[0].charAt(0)==MOVE_DESCRIPTION.charAt(0))//~1A30I~
        )                                                          //~1A30I~
        {                                                          //~1A2eI~
	        String color=Paction.color>0 ? AG.BlackSign : AG.WhiteSign;//~1A2eI~
        	desc=aRules.moveDescription(Paction.color,Paction.piece,Paction.drop==1,Paction.iFrom,Paction.jFrom,Paction.iTo,Paction.jTo);//~1A2eR~
        	desc=MOVE_DESCRIPTION/*"["*/+Integer.toString(Paction.moveNumber)+"] "+color+desc+" ";//~1A2eI~
        }                                                          //~1A2eI~
        return desc;                                               //~1A2eI~
    }                                                              //~1A2eI~
//**************************************************************** //~v101I~
    private boolean isGameover(int Pi1/*from*/,int Pj1,int Ppiece,int Pcolor/*movedPieceColor*/,int Pi2/*to*/,int Pj2)//~v101R~
    {                                                              //~v101I~
		if ((gameoverReason & GOR_NOPATH)!=0)                      //~v101M~
        {                                                          //~v101M~
			errNoPath(Pi1,Pj1,Ppiece,Pcolor,Pi2,Pj2);              //~v101M~
            return true;                                           //~v101I~
        }                                                          //~v101M~
		if ((gameoverReason & GOR_2PAWN)!=0)                       //~v101M~
        {                                                          //~v101M~
			err2Pawn(Pi2,Pj2,Ppiece,Pcolor);//from Rules           //~v101M~
            return true;                                           //~v101I~
        }                                                          //~v101M~
		if ((gameoverReason & GOR_UNMOVABLEDROP)!=0)               //~v101M~
        {                                                          //~v101M~
			errUnmovableDrop(Pi2,Pj2,Ppiece,Pcolor);               //~v101M~
            return true;                                           //~v101I~
        }                                                          //~v101M~
		if ((gameoverReason & GOR_DROPPAWNCHECKMATE)!=0)           //~v101M~
        {                                                          //~v101M~
			errDropPawnCheckmate(Pi2,Pj2,Ppiece,Pcolor);           //~v101M~
            return true;                                           //~v101I~
        }                                                          //~v101M~
		if ((gameoverReason & GOR_LEAVECHECK)!=0)                  //~v101M~
        {                                                          //~v101M~
			errLeaveCheck(Pcolor,Ppiece,Pi2,Pj2);                                 //~v101M~
            return true;                                           //~v101I~
        }                                                          //~v101M~
        return false;                                              //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~@@@2I~
	private void infoCaptured(int Pi,int Pj,int Ppiece,int Pcolor) //~@@@2R~
    {                                                              //~@@@2I~
//        String msg=AG.resource.getString(R.string.InfoCaptured);   //~@@@2R~//~v101R~
//        String pos=Field.coordinate(Pi,Pj,S,Pcolor);               //~@@@2I~//~v101R~
//        String piece=CGF.pieceName(Ppiece);                        //~@@@2I~//~v101R~
//        String color=Pcolor>0 ? AG.BlackSign : AG.WhiteSign;       //~@@@2I~//~v101R~
//        msg=color+" "+msg+" "+pos+" ("+piece+")";                  //~@@@2R~//~v101R~
//        JagoSound.play("piececaptured",false/*not change to beep when beeponly option is on*/);//~@@@@I~//~@@@2I~//~v101R~
//        CGF.appendComment(msg+"\n");                               //~@@@2I~//~v101R~
    }                                                              //~@@@2I~
////**************************************************************** //~v101I~//~1A0aR~
//    private void errDrop(int Pi,int Pj,int Ppiece,int Pcolor)                 //~v101I~//~1A0aR~
//    {                                                              //~v101I~//~1A0aR~
//        String pos=Field.coordinate(Pi,Pj,S,Pcolor);               //~v101I~//~1A0aR~
//        String piece=CGF.pieceName(Ppiece);                        //~v101I~//~1A0aR~
//        String msg=AG.resource.getString(R.string.ErrDrop,pos,piece);//~v101I~//~1A0aR~
//        AView.showToast(msg);                                      //~v101I~//~1A0aR~
//    }                                                              //~v101I~//~1A0aR~
//**************************************************************** //~v101I~
//*rc:0:err,reject,-1:gameover,1:ok                                //~v101I~
//**************************************************************** //~v101I~
	public int err2Pawn(int Pi,int Pj,int Ppiece,int Pcolor)//from Rules//~v101I~
    {                                                              //~v101I~
    	int rc=(GameOptions & GAMEOPT_FOUL_2PAWN)!=0?-1:0;	//gameover or reject drop//~v101I~
        if (rc==0)                                                 //~v101I~
        {                                                          //~1A0aI~
            CGF.setLabel(R.string.Err2Pawn,true/*append*/);        //~1A0aI~
    		AView.showToast(R.string.Err2Pawn);                    //~v101I~
        }                                                          //~1A0aI~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	if ((gameoverReason & GOR_2PAWN)!=0)	//2nd call     //~v101I~
            {                                                      //~v101I~
	        	CGF.gameoverMessage(-Pcolor/*winner*/,R.string.Err2PawnGameover);//~v101I~
            }                                                      //~v101I~
            else	//1st time, gameover msg later                 //~v101I~
            {                                                      //~v101I~
	        	gameoverReason|=GOR_2PAWN;                         //~v101I~
                rc=1;	//once move piece                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
//*rc:0 err reject,1:ok                                            //~v101R~
//**************************************************************** //~v101I~
	public int errLeaveCheck(int Pcolor,int Ppiece,int Pi,int Pj)//from Rules               //~v101I~//~1A0fR~
    {                                                              //~v101I~
    	int rc=(GameOptions & GAMEOPT_FOUL_LEAVECHECK)!=0?-1:0;	//gameover or reject drop//~v101I~
	    String desc=aRules.moveDescription(Ppiece,Pi,Pj);  //~1A0fI~
        if (rc==0)  //not gameover                                 //~v101R~
        {                                                          //~1A0aI~
            CGF.setLabel(desc+" "+AG.resource.getString(R.string.ErrLeaveCheck),true/*append*/);   //~1A0aI~//~1A0fR~
    		AView.showToast(R.string.ErrLeaveCheck);               //~v101I~
        }                                                          //~1A0aI~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	if ((gameoverReason & GOR_LEAVECHECK)!=0)	//2nd call //~v101I~
            {                                                      //~v101I~
      		  	CGF.gameoverMessage(-Pcolor/*winner*/,R.string.ErrLeaveCheckGameover);//~v101R~
            }                                                      //~v101I~
            else	//1st time, gameover msg later                 //~v101I~
            {                                                      //~v101I~
	        	gameoverReason|=GOR_LEAVECHECK;                    //~v101I~
                rc=1;	//once move piece                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	public int errUnmovableDrop(int Pi,int Pj,int Ppiece,int Pcolor)//from Rules//~v101I~
    {                                                              //~v101I~
    	int rc=(GameOptions & GAMEOPT_FOUL_UNMOVABLEDROP)!=0?-1:0;	//gameover or reject drop//~v101I~
        String pos=Field.coordinate(Pi,Pj,S,YourColor);               //~v101I~//~1A0aR~
        String piece=CGF.pieceName(Ppiece);                        //~v101I~
        String msg=AG.resource.getString(R.string.ErrUnmovableDrop,piece,pos);//~v101I~
        if (rc==0)                                                 //~v101I~
        {                                                          //~1A0aI~
            CGF.setLabel(msg,false/*not append*/);                 //~1A0aI~
	    	AView.showToast(msg);                                  //~v101R~
        }                                                          //~1A0aI~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	if ((gameoverReason & GOR_UNMOVABLEDROP)!=0)	//2nd call//~v101I~
            {                                                      //~v101I~
	        	CGF.gameoverMessage(-Pcolor/*winner*/,R.string.ErrUnmovableDropGameover);//~v101I~
            }                                                      //~v101I~
            else	//1st time, gameover msg later                 //~v101I~
            {                                                      //~v101I~
	        	gameoverReason|=GOR_UNMOVABLEDROP;                 //~v101I~
                rc=1;	//once move piece                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	public int errDropPawnCheckmate(int Pi,int Pj,int Ppiece,int Pcolor)//from Rules//~v101I~
    {                                                              //~v101I~
    	int rc=(GameOptions & GAMEOPT_FOUL_DROPPAWNCHECKMATE)!=0?-1:0;	//gameover or reject drop//~v101I~
        if (rc==0)                                                 //~v101I~
        {                                                          //~1A0aI~
            CGF.setLabel(R.string.ErrDropPawnCheckmate,true/*append*/);//~1A0aI~
    		AView.showToast(R.string.ErrDropPawnCheckmate);        //~v101I~
        }                                                          //~1A0aI~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	if ((gameoverReason & GOR_DROPPAWNCHECKMATE)!=0)	//2nd call//~v101I~
            {                                                      //~v101I~
	        	CGF.gameoverMessage(-Pcolor/*winner*/,R.string.ErrDropPawnCheckmateGameover);//~v101I~
            }                                                      //~v101I~
            else	//1st time, gameover msg later                 //~v101I~
            {                                                      //~v101I~
	        	gameoverReason|=GOR_DROPPAWNCHECKMATE;                 //~v101I~
                rc=1;	//once move piece                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
////**************************************************************** //~@@@@I~//~@@@2M~//~v101R~
//    private void errMissTouch(int i,int j)                                    //~@@@@I~//~@@@2M~//~v101R~
//    {                                                              //~@@@@I~//~@@@2M~//~v101R~
//        String msg=AG.resource.getString(R.string.ErrNotReselectable)//~@@@2M~//~v101R~
//                    +Field.coordinate(i,j,S,YourColor);            //~@@@2M~//~v101R~
//        AView.showToast(msg);                                      //~@@@2M~//~v101R~
//        CGF.appendComment(msg+"\n");                               //~@@@2M~//~v101R~
//    }                                                              //~@@@@I~//~@@@2M~//~v101R~
//**************************************************************** //~@@@2I~
	private void errNotYourTurn(int Pcolor/*your color*/)          //~@@@2I~
    {                                                              //~@@@2I~
        String color=Pcolor>0 ? AG.BlackName : AG.WhiteName;       //~@@@2R~
        String msg=AG.resource.getString(R.string.ErrNotYourTurn,color);//~@@@2R~
        CGF.setLabel(msg,true/*append*/);                          //~1A0aR~
//  	AView.showToast(msg);                                      //~@@@2I~//~1A0aR~
//        CGF.appendComment(msg+"\n");                               //~@@@2I~//~v101R~
    }                                                              //~@@@2I~
//**************************************************************** //~1A38I~
	private void errWaitingPartnerResponse()                       //~1A38I~
    {                                                              //~1A38I~
    //*nothing to do(protect new seelction before get @@!move response)//~1A38I~
    }                                                              //~1A38I~
//**************************************************************** //~1A10R~
	protected void errSelectPiece()                                //~1A10R~
    {                                                              //~1A10R~
        String msg=AG.resource.getString(R.string.ErrSelectPiece); //~1A10R~
        setLabel(msg,false/*not append*/);                         //~1A10R~
    }                                                              //~1A10R~
//**************************************************************** //~1A10R~
	protected void setLabel(String Pmsg,boolean Pappend)           //~1A10R~
    {                                                              //~1A10R~
        CGF.setLabel(Pmsg,Pappend);                                //~1A10R~
    }                                                              //~1A10R~
//**************************************************************** //~@@@2I~
	public void infoMsg(String Pmsg)                               //~@@@2R~
    {                                                              //~@@@2I~
    	AView.showToast(Pmsg);                                     //~@@@2I~
        CGF.appendComment(Pmsg+"\n");                              //~@@@2I~
    }                                                              //~@@@2I~
//**************************************************************** //~@@@2I~
	public void infoComment(String Pmsg)                           //~@@@2I~
    {                                                              //~@@@2I~
        CGF.appendComment(Pmsg+"\n");                              //~@@@2I~
    }                                                              //~@@@2I~
//**************************************************************** //~@@@2I~
	public void infoMsg(int Presid)                                //~@@@2R~
    {                                                              //~@@@2I~
    	String s=AG.resource.getString(Presid);                    //~@@@2I~
        infoMsg(s);                                                //~@@@2I~
    }                                                              //~@@@2I~
//**************************************************************** //~@@@2I~
	public void infoMsg(String Ps,int Presid)                      //~@@@2I~
    {                                                              //~@@@2I~
    	String s=Ps+AG.resource.getString(Presid);                 //~@@@2I~
        infoMsg(s);                                                //~@@@2I~
    }                                                              //~@@@2I~
//**************************************************************** //~1A0fI~
//*from Rules                                                      //~1A0fI~
//**************************************************************** //~1A0fI~
	public void errCheckmate(int Pcolor)                           //~1A0fI~
    {                                                              //~1A0fI~
        gameoverReason|=GOR_CHECKMATE;             //~1A0fI~
        CGF.setLabel(R.string.CheckmateSend,false/*no append*/);   //~1A0fI~
    	CGF.errCheckmate(Pcolor);                                  //~1A0fI~
    }                                                              //~1A0fI~
//**************************************************************** //~1A10R~
	public boolean isIdleFreeBoard()                               //~1A10R~
    {                                                              //~1A10R~
    	return swFreeBoardCanvas && !swFreeBoardStarted;           //~1A10R~
    }                                                              //~1A10R~
////************************************************                 //~1A10I~//~1A1gM~//~1A30R~
////*get save data for restore                                       //~1A10I~//~1A1gM~//~1A30R~
////************************************************                 //~1A10I~//~1A1gM~//~1A30R~
//    public void getAllBoardPieces(int[][] Pout)                    //~1A10R~//~1A1gM~//~1A30R~
//    {                                                              //~1A10I~//~1A1gM~//~1A30R~
//        int piece,color;                                           //~1A10I~//~1A1gM~//~1A30R~
//    //*****************************                                //~1A10I~//~1A1gM~//~1A30R~
//        if (Dump.Y) Dump.println("FreeGoFrame getPieces");         //~1A10I~//~1A1gM~//~1A30R~
//        for (int ii=0;ii<S;ii++)                  //~1A10I~        //~1A1gM~//~1A30R~
//        {                                                          //~1A10I~//~1A1gM~//~1A30R~
//            for (int jj=0;jj<S;jj++)              //~1A10I~        //~1A1gM~//~1A30R~
//            {                                                      //~1A10I~//~1A1gM~//~1A30R~
//                color=P.color(ii,jj);                              //~1A10I~//~1A1gM~//~1A30R~
//                piece=P.piece(ii,jj);                              //~1A10I~//~1A1gM~//~1A30R~
//                Pout[ii][jj]=((color+1)<<8)|piece;//2:black:1:free,0:white//~1A10I~//~1A1gM~//~1A30R~
//            }                                                      //~1A10I~//~1A1gM~//~1A30R~
//        }                                                          //~1A10I~//~1A1gM~//~1A30R~
//    }                                                              //~1A10I~//~1A1gM~//~1A30R~
    public void getAllBoardPieces(int[][] Pout)                    //~1A30I~
    {                                                              //~1A30I~
	    getAllBoardPieces(Pout,P);                         //~1A30I~
    }                                                              //~1A30I~
//************************************************                 //~1A30I~
//*get save data for restore                                       //~1A30I~
//************************************************                 //~1A30I~
    public static void getAllBoardPieces(int[][] Pout,Position Pposition)//~1A30I~
    {                                                              //~1A30I~
    	int piece,color;                                           //~1A30I~
 	//*****************************                                //~1A30I~
        if (Dump.Y) Dump.println("ConnectedBoard:getAllBoardPieces");         //~1A30I~//~1AexR~
        for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)                                   //~1A30I~
        {                                                          //~1A30I~
            for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)                               //~1A30I~
            {                                                      //~1A30I~
                color=Pposition.color(ii,jj);                      //~1A30I~
                piece=Pposition.piece(ii,jj);                      //~1A30I~
                Pout[ii][jj]=((color+1)<<8)|piece;//2:black:1:free,0:white//~1A30I~
            }                                                      //~1A30I~
        }                                                          //~1A30I~
    }                                                              //~1A30I~
//**************************************************************** //~1A10M~//~1A1gM~
//*reset board for to status at started                            //~1A10M~//~1A1gM~
//**************************************************************** //~1A10M~//~1A1gM~
	public void restoreBoard(int[][] Ppieces)                      //~1A10R~//~1A1gM~
    {                                                              //~1A10M~//~1A1gM~
    	int piece,color;                                           //~1A10M~//~1A1gM~
 	//*****************************                                //~1A10M~//~1A1gM~
    	if (Dump.Y) Dump.println("ConnectedBoard:restoreBoard");                  //~1A10M~//~1A1gM~//~1AexR~
        for (int ii=0;ii<S;ii++)                                   //~1A10M~//~1A1gM~
        {                                                          //~1A10M~//~1A1gM~
	        for (int jj=0;jj<S;jj++)                               //~1A10M~//~1A1gM~
            {                                                      //~1A10M~//~1A1gM~
            	piece=Ppieces[ii][jj];                             //~1A10M~//~1A1gM~
                color=(piece>>8)-1;	//2:black,1:free,0:white       //~1A10I~//~1A1gM~
                piece=piece&255;                                   //~1A10M~//~1A1gM~
                int oldp=P.piece(ii,jj);                           //~1A17I~//~1A1gM~
                int oldc=P.color(ii,jj);                           //~1A17I~//~1A1gM~
                if (piece==oldp && color==oldc)                    //~1A17I~//~1A1gM~
                	continue;                                      //~1A17I~//~1A1gM~
	            P.piece(ii,jj,color,piece);                        //~1A10I~//~1A1gM~
				update(ii,jj);                                     //~1A10I~//~1A1gM~
            }                                                      //~1A10M~//~1A1gM~
        }                                                          //~1A10M~//~1A1gM~
		copy();                                                    //~1A10M~//~1A1gM~
    }                                                              //~1A10M~//~1A1gM~
//************************************************                 //~1AexI~
//*set reverse for initial piece                                   //~1AexI~
//************************************************                 //~1AexI~
    public static void setInitialPieceReverse(Notes Pnotes,int Pcol)//~1AexR~
    {                                                              //~1AexI~
    	int yourcolor,sz;                                          //~1AexR~
        int[][] pieces;                                            //~1AexI~
        int[][] piecesR;                                           //~1AexI~
 	//*****************************                                //~1AexI~
        yourcolor=Pcol;                                            //~1AexR~
        if (Dump.Y) Dump.println("ConnectedBoard gsetInitialPieceReverse Notes.yourcolor="+yourcolor);//~1AexI~
        if (yourcolor>0)	//black                                //~1AexR~
        	return;                                                //~1AexI~
        pieces=Pnotes.piecesAtStart;                               //~1AexI~
        if (Dump.Y) Dump.println("ConnectedBoard setInitialPieceReverse Notes.piecesAtstart="+pieces);//~1AexR~
        if (pieces!=null)                                          //~1AexR~
        {                                                          //~1AexI~
            sz=AG.BOARDSIZE_SHOGI;                                 //~1AexR~
            piecesR=new int[sz][sz];                               //~1AexR~
            for (int ii=0;ii<sz;ii++)                              //~1AexR~
            {                                                      //~1AexR~
                for (int jj=0;jj<sz;jj++)                          //~1AexR~
                {                                                  //~1AexR~
                    piecesR[sz-ii-1][sz-jj-1]=pieces[ii][jj];      //~1AexR~
                }                                                  //~1AexR~
            }                                                      //~1AexR~
            Pnotes.piecesAtStart=piecesR;                          //~1AexR~
        }                                                          //~1AexI~
        pieces=Pnotes.trayAtStart;    //[0]:upper:Opponent, [1]:lower:your color//~1AexI~
        if (Dump.Y) Dump.println("ConnectedBoard setInitialPieceReverse Notes.trayAtstart="+pieces);//~1AexI~
        if (pieces!=null)                                          //~1AexI~
        {                                                          //~1AexI~
            for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)          //~1AexI~
            {                                                      //~1AexI~
                int u=pieces[0][ii];                                //~1AexI~
                int l=pieces[1][ii];                                //~1AexI~
                pieces[0][ii]=l;                                    //~1AexI~
                pieces[1][ii]=u;                                    //~1AexI~
            }                                                      //~1AexI~
        }                                                          //~1AexI~
    }                                                              //~1AexI~
}
