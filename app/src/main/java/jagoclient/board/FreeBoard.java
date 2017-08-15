//*CID://+1Ah0R~:                             update#=  299;       //~1A1bR~//+1Ah0R~
//****************************************************************************//~v101I~
//1Ah0 2016/10/15 bonanza                                          //+1Ah0I~
//1A1b 2013/03/13 FreeBoard:multiple snapshot                      //~1A1bI~
//1A17 2013/03/12 slide for piece remove on freeboard              //~1A17I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0f 2013/03/05 check Chackmate for gameover                     //~1A0fI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~1A0eI~
//1A0a 2013/03/03 issue "check" sound                              //~1A0aI~
//101g 2013/02/09 captured mark remains at after partner move after I captured//~v101I~
//****************************************************************************//~v101I~
package jagoclient.board;


import com.Asgts.AG;
import com.Asgts.R;
import com.Asgts.awt.MouseEvent;

import android.graphics.Point;

import jagoclient.Dump;
import jagoclient.FreeGoFrame;
import static jagoclient.board.Field.*;                            //~v101I~

public class FreeBoard extends ConnectedBoard                      //~1A10R~
{                                                                  //~@@@2R~
                                                                   //~@@@2I~
	ConnectedGoFrame CGF;                                          //~@@@2I~
	public FreeBoard (int size, ConnectedGoFrame gf)               //~1A10R~
	{	super(size,gf);
		CGF=gf;
        aRules=new Rules(CGF,this,S);                             //~v1A0R~//~1A00I~//~v101I~
	}

	@Override    //*override ConnectedBoard                        //~1A10R~
    public synchronized void movemouse (int i, int j,int Ppiece)   //~v101I~//~1A10R~
    {                                                              //~1A10R~
    	if (!isIdleFreeBoard())                                    //~1A10I~
        {                                                          //~1A10I~
        	super.movemouse(i,j,Ppiece);                           //~1A10I~
        	return;                                                //~1A10I~
        }                                                          //~1A10I~
//      if (Pos.haschildren()) return;                             //~1A10I~
        if (P.color(i,j)!=0) return;                               //~1A10I~
//      if (P.color(i,j)!=0)                                       //~v101I~//~1A10R~
//          capturePiece(i,j);  //delete captured                  //~v101I~//~1A10R~
//      int piece=aRules.chkPromotion(i,j,iSelected,jSelected,Ppiece);//~v101R~//~1A10R~
        int piece=Ppiece;                                          //~1A10I~
//      if (Pos.isMain() && CGF.wantsmove())                       //~1A10R~
//      {                                                          //~@@@2I~//~1A10R~
//          if (CGF.moveset(i,j,piece,swDropped))                  //~v101I~//~1A10R~
//          {   sendi=i; sendj=j;                                  //~1A10R~
//              update(i,j); copy();                               //~1A10R~
//              if (Dump.Y) Dump.println("movemouse copyed");      //~v101I~//~1A10R~
//              MyColor=P.color();                                 //~1A10R~
//          }                                                      //~1A10R~
//      }                                                          //~1A10R~
//      else set(i,j,piece); // try to set a new move              //~v101R~//~1A10R~
        set(i,j,piece); // try to set a new move                   //~1A10I~
    }                                                              //~1A10R~

////**************************************************************** //~@@@@I~//~@@@2M~//~1A10R~
////*piece selection chk                                             //~@@@@I~//~@@@2M~//~1A10R~
////*rc:-1:err,1:selected,0:do move                                  //~@@@@I~//~@@@2M~//~1A10R~
////**************************************************************** //~@@@@I~//~@@@2M~//~1A10R~
    @Override //Board,ConnectedBoard                                              //~@@@2I~//~1A10R~
    public int selectPiece(int Pstate,int i,int j)                //~@@@@I~//~@@@2R~//~1A10R~//+1Ah0R~
    {                                                              //~@@@@I~//~@@@2M~//~1A10R~
        int col,rc=-1;                                             //~@@@@I~//~@@@2M~//~v101R~//~1A10R~
    //*******************                                          //~@@@@I~//~@@@2M~//~1A10R~
    	if (!isIdleFreeBoard())                                    //~1A10R~
        	return super.selectPiece(Pstate,i,j);                  //~1A10I~
        if (Dump.Y) Dump.println("FreeBoard:selectPiece state="+Pstate+",swSelected="+swSelected+",i="+i+",j="+j);//~@@@@I~//~@@@2M~//~1A10R~
//      if (Pstate==1)                                             //~@@@@I~//~@@@2M~//~1A10R~
//          col=1;   //black                                       //~@@@@I~//~@@@2M~//~1A10R~
//      else                                                       //~@@@@I~//~@@@2M~//~1A10R~
//          col=-1;  //white                                       //~@@@@I~//~@@@2M~//~1A10R~
//      if (!CGF.swLocalGame && col!=YourColor) //your turn in partner match//~@@@2I~//~1A10R~
//      {                                                          //~@@@2I~//~1A10R~
//          errNotYourTurn(-YourColor);                            //~@@@2R~//~1A10R~
//          return rc;                                             //~@@@2I~//~1A10R~
//      }                                                          //~@@@2I~//~1A10R~
        if (swSelected)                                            //~@@@@I~//~@@@2M~//~1A10R~
        {                                                          //~@@@@I~//~@@@2M~//~1A10R~
//          if (col==P.color(i,j))  //your piece                   //~@@@@I~//~@@@2R~//~1A10R~
            col=P.color(i,j);       //target                       //~1A10I~
            if (col!=0)             //changed piece                //~1A10I~
            {                                                      //~@@@@I~//~@@@2M~//~1A10R~
                {                                                  //~@@@@I~//~@@@2M~//~1A10R~
                    if (iSelected==i && jSelected==j)   //resetreq //~@@@@I~//~@@@2M~//~1A10R~
                    {                                              //~@@@@I~//~@@@2M~//~1A10R~
                        if (Dump.Y) Dump.println("select reset(touch same pos)");//~v101I~//~1A10R~
                        rc=-1;                                     //~@@@@I~//~@@@2M~//~1A10R~
                    }                                              //~@@@@I~//~@@@2M~//~1A10R~
                    else                                           //~@@@@I~//~@@@2M~//~1A10R~
                    {                                              //~@@@@I~//~@@@2M~//~1A10R~
                        updateSelected(i,j);                       //~@@@2I~//~1A10R~
                        rc=1;   //selected                         //~@@@@I~//~@@@2M~//~1A10R~
                    }                                              //~@@@@I~//~@@@2M~//~1A10R~
                }                                                  //~@@@@I~//~@@@2M~//~1A10R~
            }                                                      //~@@@@I~//~@@@2M~//~1A10R~
            else                                                   //~@@@@I~//~@@@2M~//~1A10R~
            {                                                      //~v101I~//~1A10R~
//              rc2=aRules.isOnPiecePath(true/*errmsg*/,col,i,j,iSelected,jSelected);                               //~@@@@I~//~@@@2M~//~v101R~//~1A0fR~//~1A10R~
//              if (rc2==1)                                        //~v101R~//~1A10R~
                {                                                      //~@@@@I~//~@@@2M~//~v101R~//~1A10R~
                    rc=0;   //do move                                              //~@@@@I~//~@@@2M~//~v101R~//~1A10R~
                }                                                      //~@@@@I~//~@@@2M~//~v101R~//~1A10R~
            }                                                      //~v101I~//~1A10R~
        }                                                          //~@@@@I~//~@@@2M~//~1A10R~
        else              //do select                              //~@@@@I~//~@@@2M~//~1A10R~
        {                                                          //~@@@@I~//~@@@2M~//~1A10R~
//          if (col==P.color(i,j))  //your piece                   //~@@@2R~//~1A10R~
            col=P.color(i,j);      //double touch                 //~1A10I~
            if (col!=0)                                            //~1A10I~
            {                                                      //~@@@@I~//~@@@2M~//~1A10R~
                swSelected=true;                //clear mark on old square//~@@@@I~//~@@@2M~                       //~@@@@I~//~@@@2M~//~1A10R~
                iSelected=i; jSelected=j;                          //~@@@@I~//~@@@2M~//~1A10R~
                iSelectedPiece=P.piece(i,j);                       //~v101I~//~1A10R~
                update(iSelected,jSelected);                       //~1A10R~
                rc=1;   //selected                                 //~@@@@I~//~@@@2M~//~1A10R~
            }                                                      //~@@@@I~//~@@@2M~//~1A10R~
//          else                                                   //~@@@2M~//~1A10R~
//          if (P.color(i,j)!=0)                                   //~@@@2M~//~1A10R~
//              errNotYourTurn(col);                               //~@@@2R~//~1A10R~
            else    //free space                                   //~v101I~//~1A10R~
            {                                                      //~v101I~//~1A10R~
//              int dropPiece=CGF.aCapturedList.chkSelected(col);   //ca drop captured//~v101R~//~1A10R~
//              if (dropPiece>=0)                                  //~v101I~//~1A10R~
                Point dropPiece=CGF.aCapturedList.getSelectedPieceFreeBoard();//~1A10I~
                if (dropPiece!=null)                               //~1A10I~
                {                                                  //~v101I~//~1A10R~
//                  rc2=aRules.chkDroppable(i,j,col,dropPiece); //1:ok,0:reject,-1:gameover//~v101R~//~1A10R~
//                  if (rc2>0)  //drop OK                          //~v101R~//~1A10R~
                    {                                              //~v101I~//~1A10R~
//                      iSelectedPiece=CGF.aCapturedList.getSelectedPiece(col); //ca drop captured//~v101R~//~1A10R~
                        iSelectedPiece=dropPiece.y; //Point(color,piece)//~1A10I~
                        col=dropPiece.x;	//                         //~1A10I~
                        rc=2;                                      //~v101R~//~1A10R~
                    }                                              //~v101I~//~1A10R~
//                  else                                           //~v101I~//~1A10R~
//                      rc=rc2-1;    //-1:err,-2:gameover          //~v101R~//~1A10R~
                }                                                  //~v101I~//~1A10R~
            }                                                      //~v101I~//~1A10R~
        }                                                          //~@@@@I~//~@@@2M~//~1A10R~
        if (Dump.Y) Dump.println("selectPiece rc="+rc+",col="+col+",swSelected="+swSelected+",i="+iSelected+",j="+jSelected);//~@@@@I~//~@@@2M~//~1A10R~
        if (rc==1)  //selected                                     //~@@@2I~//~1A10R~
        {                                                          //~@@@2I~//~1A10R~
        	P.color(col);                                          //~1A10I~
            CGF.pieceSelected(iSelected,jSelected,P.color(iSelected,jSelected));                //~@@@2R~//~v101R~//~1A10R~
        }                                                          //~@@@2I~//~1A10R~
        else                                                       //~@@@2I~//~1A10R~
        if (rc==0)  //will be moved                                //~@@@2I~//~1A10R~
        {                                                          //~@@@2I~//~1A10R~
            swDropped=false;                                       //~v101I~//~1A10R~
            CGF.pieceMoved(col);                                   //~v101I~//~1A10R~
        }                                                          //~@@@2I~//~1A10R~
        else                                                       //~v101I~//~1A10R~
        if (rc==2)  //drop piece                                   //~v101I~//~1A10R~
        {                                                          //~v101I~//~1A10R~
        	P.color(col);                                          //~1A10I~
            swDropped=true;                                        //~v101I~//~1A10R~
            CGF.pieceMoved(col);                                   //~v101I~//~1A10R~
            rc=0;                                                  //~v101I~//~1A10R~
        }                                                          //~v101I~//~1A10R~
        return rc;                                                 //~@@@@I~//~@@@2M~//~1A10R~
    }                                                              //~@@@@I~//~@@@2M~//~1A10R~
//**************************************************************** //~1A10I~
	@Override //Board                                              //~1A10I~
	protected void infoMoved(int Pi1/*from*/,int Pj1,int Ppiece,int Pcolor/*movedPieceColor*/,int Pi2/*to*/,int Pj2)//~1A10I~
    {                                                              //~1A10I~
        if (Dump.Y) Dump.println("FreeBoard:infoMoved");       //~1A10I~//~1A1bM~
    	if (!isIdleFreeBoard())                                    //~1A10I~
        {                                                          //~1A10I~
			super.infoMoved(Pi1,Pj1,Ppiece,Pcolor,Pi2,Pj2);        //~1A10I~
        	return;                                                //~1A10I~
        }                                                          //~1A10I~
    }                                                              //~1A10I~
//**************************************************************** //~1A1bI~
	private void infoMovedFB(int Pstate)                            //~1A1bI~
    {                                                              //~1A1bI~
        if (Dump.Y) Dump.println("FreeBoard:infoMovedFB");         //~1A1bI~
        Notes nc=((FreeGoFrame)CGF).lastNotes;                                     //~1A1bI~
        Notes nm=((FreeGoFrame)CGF).lastNotesSaved;                                //~1A1bI~
        String s="";                                               //~1A1bI~
        if (nc!=null && nm!=null)                                  //~1A1bI~
        {                                                          //~1A1bI~
        	int moves=moveNumber-nm.moves0;                        //~1A1bR~
            nm.moves=moves;                                        //~1A1bI~
            String col=(Pstate==1?AG.BlackName:AG.WhiteName);        //~1A1bI~
        	s=AG.resource.getString(R.string.InfoMovedFB,col,nc.seq,nm.seq,moves+1);//~1A1bR~
        }                                                          //~1A1bI~
		CGF.setLabel(s);                                           //~1A1bR~
    }                                                              //~1A1bI~
//**************************************************************** //~1A10I~
	@Override //Board                                              //~1A10I~
	public void showinformation ()
	{                                                              //~1A10R~
        if (Dump.Y) Dump.println("FreeBoard:showInformation");     //~1A1bI~
    	if (isIdleFreeBoard())                                    //~1A10I~//~1A17R~
        	return;                                                //~1A10I~
		if (State==1 || State==2)                                  //~1A17I~
		{	if (P.color()==1) State=1;                             //~1A17I~
			else State=2;                                          //~1A17I~
			infoMovedFB(State);                                    //~1A1bI~
		}                                                          //~1A17I~
	}
//**************************************************************** //~1A1bI~
	@Override //Board                                              //~1A1bI~
    void pieceMoved(int Pi,int Pj)                                 //~1A1bI~
    {                                                              //~1A1bI~
        super.pieceMoved(Pi,Pj);                                   //~1A1bI~
    	if (isIdleFreeBoard())                                     //~1A1bI~
        {                                                          //~1A1bI~
            iSelected=Pi;                                       //~1A1bI~
            jSelected=Pj;    //accept button del/turn/rotate    //+1A1bI~            }                                                      //~1A1bI~
        }                                                          //~1A1bI~
	}                                                              //~1A1bI~
//**************************************************************** //~1A10R~
//*update board for freeboard                                      //~1A10R~
//**************************************************************** //~1A10R~
	public void clearBoard()                                       //~1A10R~
    {                                                              //~1A10R~
    	int piece;                                           //~1A10R~
	//*****************************                                //~1A10R~
    	if (Dump.Y) Dump.println("captureAll");                    //~1A10R~
        for (int ii=0;ii<S;ii++)                                   //~1A10R~
        {                                                          //~1A10R~
	        for (int jj=0;jj<S;jj++)                               //~1A10R~
	        {                                                      //~1A10R~
	            piece=P.piece(ii,jj);                              //~1A10R~
                if (piece!=0 && piece!=PIECE_KING)                 //~1A10R~
                {                                                  //~1A10R~
		            P.piece(ii,jj,0,0);                            //~1A10R~
					update(ii,jj);                                 //~1A10R~
                }                                                  //~1A10R~
            }                                                      //~1A10R~
        }                                                          //~1A10R~
		copy();                                                    //~1A10R~
    }                                                              //~1A10R~
//**************************************************************** //~1A10R~
//*reset board for freeboard                                       //~1A10R~
//**************************************************************** //~1A10R~
	public void resetBoard(int Pcol,int Pgameoptions,int Phandicap)//~1A10R~
    {                                                              //~1A10R~
 	//*****************************                                //~1A10R~
    	if (Dump.Y) Dump.println("resetBoard");                    //~1A10R~
        for (int ii=0;ii<S;ii++)                                   //~1A10R~
        {                                                          //~1A10R~
	        for (int jj=0;jj<S;jj++)                               //~1A10R~
	            P.piece(ii,jj,0,0);                                //~1A10R~
        }                                                          //~1A10R~
        putInitialPiece(Pcol,GameOptions,Phandicap);               //~1A10R~
		copy();                                                    //~1A10R~
    }                                                              //~1A10R~
//**************************************************************** //~1A10R~
//*reset board for freeboard                                       //~1A10R~
//**************************************************************** //~1A10R~
	public void changePiece(boolean Pswcolor,boolean Pswpromotion,boolean Pdelete)//~1A10R~
    {                                                              //~1A10R~
    	int piece,color;                                           //~1A10R~
	//*****************************                                //~1A10R~
    	if (Dump.Y) Dump.println("CB:changepiece col="+Pswcolor+",prom="+Pswpromotion);//~1A10R~
		setLabel("",false/*not append*/);                          //~1A10M~
    	if (iSelected<0||jSelected<0)                              //~1A10R~
        {                                                          //~1A10R~
        	if (CGF.aCapturedList.getSelectedPieceFreeBoard(true/*countdown*/))//~1A10I~
            	return;                                            //~1A10I~
        	errSelectPiece();                                      //~1A10R~
            return;                                                //~1A10R~
        }                                                          //~1A10R~
        color=P.color(iSelected,jSelected);                        //~1A10R~
        piece=P.piece(iSelected,jSelected);                        //~1A10R~
        if (piece==0)                                              //~1A10R~
        {                                                          //~1A10R~
        	if (CGF.aCapturedList.getSelectedPieceFreeBoard(true/*countdown*/))//~1A10I~
            	return;                                            //~1A10I~
        	errSelectPiece();                                      //~1A10R~
            return;                                                //~1A10R~
        }                                                          //~1A10R~
        if (Pdelete)                                               //~1A10R~
        {                                                          //~1A1bI~
		    P.piece(iSelected,jSelected,0,0);                      //~1A10R~
            swSelected=false;                                      //~1A1bI~
        }                                                          //~1A1bI~
        else                                                       //~1A10R~
        {                                                          //~1A10R~
            if (Pswcolor)                                          //~1A10R~
                color=-color;                                      //~1A10R~
            if (Pswpromotion)                                      //~1A10R~
            {                                                      //~1A10R~
                if (piece>PIECE_PROMOTED)                          //~1A10R~
                    piece=Field.nonPromoted(piece);                //~1A10R~
                else                                               //~1A10R~
                    piece=Field.promoted(piece);                   //~1A10R~
            }                                                      //~1A10R~
            P.piece(iSelected,jSelected,color,piece);              //~1A10R~
        }                                                          //~1A10R~
        update(iSelected,jSelected);                               //~1A10R~
		copy();                                                    //~1A10R~
    }                                                              //~1A10R~
//**************************************************************** //~1A17I~
	public boolean mouseSwiped(MouseEvent e)                       //~1A17I~//~1A1bR~
    {                                                              //~1A17I~
    	boolean rc=false;                                          //~1A1bI~
        int posx=e.getX();                                         //~1A17I~
        int posy=e.getY();                                         //~1A17I~
    	if (Dump.Y) Dump.println("FB swiped x="+posx+",y="+posy);  //~1A17I~
        if (posx>-S && posx<S && posy>-S && posy<S)                //~1A17I~
        {                                                          //~1A17I~
        	int ii=posx<0?-posx:posx;                           //~1A17I~//~1A1bR~
        	int jj=posy<0?-posy:posy;                           //~1A17I~//~1A1bR~
            if (P.piece(ii,jj)!=0)                                 //~1A1bI~
            {                                                      //~1A1bI~
    			int selectrc=selectPiece(1/*state black*/,ii,jj);  //~1A1bR~
                boolean sound_at_delete=selectrc!=1;	//1:selected:issue click//~1A1bR~
	        	((FreeGoFrame)CGF).mouseSwiped(posx,posy,sound_at_delete);             //~1A17I~//~1A1bR~
                rc=true;                                           //~1A1bI~
            }                                                      //~1A1bI~
        }                                                          //~1A17I~
    	if (Dump.Y) Dump.println("FB swiped rc="+rc);              //~1A1bI~
        return rc;                                                 //~1A1bI~
    }                                                              //~1A17I~
//**************************************************************** //~1A0fI~//~1A10I~
//*from Rules                                                      //~1A0fI~//~1A10I~
//**************************************************************** //~1A0fI~//~1A10I~
    @Override   //CGF                                              //~1A10I~
	public void errCheckmate(int Pcolor)                           //~1A0fI~//~1A10I~
    {                                                              //~1A0fI~//~1A10I~
//      gameoverReason|=GOR_CHECKMATE;             //~1A0fI~       //~1A10I~
//      CGF.setLabel(R.string.CheckmateSend,false/*no append*/);   //~1A0fI~//~1A10I~
//  	CGF.errCheckmate(Pcolor);                                  //~1A0fI~//~1A10I~
        CGF.setLabel(R.string.ErrCheckmateFB,false/*no append*/);  //~1A10I~
    }                                                              //~1A0fI~//~1A10I~
}
