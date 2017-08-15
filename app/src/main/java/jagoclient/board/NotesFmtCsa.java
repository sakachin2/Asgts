//*CID://+1Ah0R~:                             update#=  231;       //~1Ah0R~
//***********************************************************************
//1Ah0 2014/12/07 dislay comment area for replyboard               //~1Ah0I~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//***********************************************************************
package jagoclient.board;

import static jagoclient.board.Field.*;
import static jagoclient.partner.HandicapQuestion.*;
import android.graphics.Point;

import com.Asgts.AG;
import com.Asgts.gtp.GtpMove;

import jagoclient.Dump;

public class NotesFmtCsa extends NotesFmt                                              //~v1A0R~//~3406R~
{
    private static final String[] CSA_PIECENAME={"FU","KY","KE","GI","KI","KA","HI","OU","TO","NY","NK","NG","KI","UM","RY"};//~3408R~//~3409R~//~3412R~
    private static final String[] CSA_GAMEOVERNAME={                   //~3408I~
			"TORYO","CHUDAN","SENNICHITE","TIME_UP",               //~3408R~
			"ILLEGAL_MOVE","-ILLEGAL_ACTION","JISHOGI","KACHI",    //~3408R~
			"HIKIWAKE","TSUMI","FUZUMI","ERROR"};                  //~3408I~
    private static final int[] CSA_GAMEOVER_WINNER={            //~3408I~
    	//*-1:winer is last move,1:winner is next move/            //~3408I~
			-1/*"TORYO"*/,0/*"CHUDAN"*/,0/*"SENNICHITE"*/,-1/*"TIME_UP"*/,//~3408I~
			-1/*"ILLEGAL_MOVE"*/,1/*"-ILLEGAL_ACTION"*/,0/*"JISHOGI"*/,-1/*"KACHI"*/,//~3408I~
			0/*"HIKIWAKE"*/,-1/*"TSUMI"*/,0/*"FUZUMI"*/,0/*"ERROR"*/};//~3408I~
    private static final int[] CSA_GAMEOVER_ID={                   //~3408I~
    		GAMEOVERID_RESIGN,                                     //~3409I~
    		GAMEOVERID_SUSPEND,                                    //~3409R~
    		GAMEOVERID_LOOP,                                       //~3409R~
    		GAMEOVERID_TIMEOUT,                                     //~3409R~
    		GAMEOVERID_FOUL,                                       //~3409R~
    		GAMEOVERID_FOUL,                                       //~3409R~
    		GAMEOVERID_INFINITE,                                   //~3409R~
    		GAMEOVERID_WIN,                                        //~3409R~
    		GAMEOVERID_EVEN,                                       //~3409R~
    		GAMEOVERID_CHECKMATE,                                  //~3409R~
    		GAMEOVERID_NOCHECKMATE,                                //~3409R~
    		GAMEOVERID_ERROR,                                      //~3409R~
			};                                                     //~3408I~
    public  static final char CSA_COLOR_BLACK='+';                 //~3408I~//~1Ah0R~
    public  static final char CSA_COLOR_WHITE='-';                 //~3408I~//~1Ah0R~
    public  static final char CSA_COMMENT='\'';                    //~3408I~//~3411R~
    private static final char CSA_VERSION='V';                     //~3408I~
    private static final char CSA_NAME='N';                        //~3408I~
    private static final char CSA_INFO='$';                        //~3408I~
    private static final char CSA_GAMEOVER='%';                    //~3408I~
    private static final char CSA_PIECE='P';                       //~3408I~
    private static final char CSA_HANDICAP='I';
    private static final char CSA_MULTISTMT=',';
    private static final String CSA_TRAY_ALL="AL";                  //~3412I~
    private String csaVersion;                                     //~3408I~
    private boolean swMove1st;                                             //~3414I~
    private boolean swStartPhase;                                  //~1A4vI~
                                                                   //~1Ah0I~
    protected static final String INITIAL_PIECE=                   //~1Ah0I~
"P1-KY-KE-GI-KI-OU-KI-GI-KE-KY\n"+                                 //~1Ah0R~
"P2 * -HI *  *  *  *  * -KA * \n"+                                 //~1Ah0R~
"P3-FU-FU-FU-FU-FU-FU-FU-FU-FU\n"+                                 //~1Ah0R~
"P4 *  *  *  *  *  *  *  *  * \n"+                                 //~1Ah0R~
"P5 *  *  *  *  *  *  *  *  * \n"+                                 //~1Ah0R~
"P6 *  *  *  *  *  *  *  *  * \n"+                                 //~1Ah0R~
"P7+FU+FU+FU+FU+FU+FU+FU+FU+FU\n"+                                 //~1Ah0R~
"P8 * +KA *  *  *  *  * +HI * \n"+                                 //~1Ah0R~
"P9+KY+KE+GI+KI+OU+KI+GI+KE+KY\n";                                 //~1Ah0I~
    protected static final int noteLineWidth=30; //INITIAL_PIECE line width//~1Ah0I~
    protected static final int notePiecePos1=2;   //left most pos   //~1Ah0I~
    protected static final int notePieceWidth=3;   //len for a piece //~1Ah0I~
    protected static final String emptySpace=" * ";                //~1Ah0I~
    //*************************************************************//~3408I~
    protected void appendHeaderComment(String Pline)               //~1A4vI~
    {                                                              //~1A4vI~
        if (!swStartPhase)  //before StartPosition                 //~1A4vI~
    		super.appendHeaderComment(Pline);                      //~1A4vI~
    }                                                              //~1A4vI~
    //*************************************************************//~1A4vI~
    @Override                                                      //~3409I~
	protected boolean setTreeEntry(String Pline)//~3408I~            //~3409R~
	{                                                              //~3408I~//~3409M~
    	boolean rc=true;                                           //~3408I~//~3409M~
        char ch,ch2;                                               //~3408I~//~3409M~
    //****************************                                 //~3408I~//~3409M~
        if (Dump.Y) Dump.println("NotesFmtCsa:setCsaTreeEntry line="+Pline);   //~3408I~//~3409M~//~1Ah0R~
        int linelen=Pline.length();                                //~3408I~//~3409M~
        if (linelen==0)                                            //~3408I~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        ch=Pline.charAt(0);                                        //~3408I~//~3409M~
        if (ch==CSA_COMMENT)                 //"'"                 //~3408I~//~3409M~
        {                                                          //~1A4vI~
    	    appendHeaderComment(Pline);                            //~1A4vI~
        	return rc;                                             //~3408I~//~3409M~
        }                                                          //~1A4vI~
        if (ch==CSA_VERSION && csaVersion==null)    //V             //~3408I~//~3409M~
        {                                                          //~3408I~//~3409M~
        	csaVersion=Pline;                                      //~3408I~//~3409M~
    	    appendHeaderComment(Pline);                            //~1A4vI~
        	return rc;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        if (ch==CSA_NAME&&linelen>1)     //N                        //~3408I~//~3409M~
        {                                                          //~3408I~//~3409M~
        	ch2=Pline.charAt(1);                                   //~3408I~//~3409M~
        	if (ch2==CSA_COLOR_BLACK)                                    //~3408I~//~3409M~
            	blackName=Pline.substring(2);                      //~3408I~//~3409M~
            else                                                   //~3408I~//~3409M~
        	if (ch2==CSA_COLOR_WHITE)                                    //~3408I~//~3409M~
            	whiteName=Pline.substring(2);                      //~3408I~//~3409M~
    	    appendHeaderComment(Pline);                            //~1A4vI~
            return rc;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        if (ch==CSA_INFO)     //$                                  //~3408I~//~3409M~
        {                                                          //~1A4vI~
    	    appendHeaderComment(Pline);                            //~1A4vI~
	        return rc;                                             //~3408I~//~3409M~
        }                                                          //~1A4vI~
        if (ch==CSA_PIECE && linelen>1)     //P                    //~3408I~//~3409M~
        {                                                          //~3408I~//~3409M~
            swStartPhase=true;                                     //~1A4vI~
        	ch2=Pline.charAt(1);                                   //~3408I~//~3409M~
            if (ch2==CSA_HANDICAP)     //I                            //~3408I~//~3409M~//~3412R~
            {                                                      //~3408I~//~3409M~
            	setHandicapCsa(Pline,2/*pos*/);             //~3408I~//~3409R~
            }                                                      //~3408I~//~3409M~
            else                                                   //~3408I~//~3409M~
            if (ch2>='1' && ch2<='9')                              //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
            	putInitialPieceCsa(Pline,2,ch2-'0');               //~3408R~//~3409M~
            }                                                      //~3408I~//~3409M~
            else                                                   //~3412I~
            if (ch2==CSA_COLOR_BLACK)                              //~3412I~
            {                                                      //~3412I~
            	putInitialPieceCsaPieceByPiece(Pline,2,1/*color*/);//~3412I~
            }                                                      //~3412I~
            else                                                   //~3412I~
            if (ch2==CSA_COLOR_WHITE)                              //~3412I~
            {                                                      //~3412I~
            	putInitialPieceCsaPieceByPiece(Pline,2,-1/*color*/);//~3412I~
            }                                                      //~3412I~
	        return rc;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        if (ch==CSA_COLOR_BLACK)                        //~3408I~//~3409M~//~3412R~
        {                                                          //~3408I~//~3409M~
        	if (linelen==1)                                        //~3412R~
 			{                                                      //~3412I~
            	if (Dump.Y) Dump.println("NotesFmtCsa:Move1st= black");          //~3412I~//~1Ah0R~
            	move1st=1;                                         //~3412I~
                swMove1st=true;                                    //~3414I~
			}                                                      //~3412R~
			else                                                   //~3412I~
        	if (linelen>=7)                                        //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
            	getMoveCsa(Pline,1/*pos*/,1/*color*/);             //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
	        return rc;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        if (ch==CSA_COLOR_WHITE)                        //~3408I~//~3409M~//~3412R~
        {                                                          //~3408I~//~3409M~
        	if (linelen==1)                                        //~3412R~
 			{                                                      //~3412I~
            	if (Dump.Y) Dump.println("NotesFmtCsa:Move1st white");           //~3412I~//~1Ah0R~
            	move1st=-1;                                        //~3412I~
                swMove1st=true;                                    //~3414I~
			}                                                      //~3412I~
			else                                                   //~3408I~//~3409M~//~3412R~
            	getMoveCsa(Pline,1/*pos*/,-1/*color*/);            //~3408I~//~3409M~
	        return rc;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        if (swMove1st && !swInitPiece)	//after move1st defined                                          //~3412I~//~3414I~
			putInitialPiece(0);	//initialy put pieaces             //~3412I~//~3414M~
        if (ch==CSA_GAMEOVER && linelen>1)     //%      //~3408I~//~3409M~//~3412R~
        {                                                          //~3408I~//~3409M~
            getGameoverReasonCsa(Pline,1);                         //~3408I~//~3409M~
	        return rc;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
//        if (gameoverReasonId!=0)                                   //~3408R~//~3409R~
//        {                                                          //~3408I~//~3409R~
//            rc=setKi2Action(Pline);                          //~3408R~//~3409R~
//        }                                                          //~3408I~//~3409R~
        return rc;                                                 //~3408I~//~3409M~
	}                                                              //~3408I~//~3409M~
    //*************************************************************//~3408I~
//  private int getPieceCsa(String Pline,int Ppos)                 //~1Ah0R~
    private static int getPieceCsa(String Pline,int Ppos)          //~1Ah0I~
	{                                                              //~3408I~
    	int piece=0;                                               //~3408R~
        for (int ii=0;ii<CSA_PIECENAME.length;ii++)                  //~3408I~
        {                                                          //~3408I~
			if (Pline.startsWith(CSA_PIECENAME[ii],Ppos))          //~3408R~
            {                                                      //~3408I~
                if (ii>=MAX_PIECE_TYPE)                            //~3408R~
            		piece=PIECE_PROMOTED+(ii-MAX_PIECE_TYPE)+1;     //~3408I~
                else                                               //~3408I~
            		piece=ii+1;                                    //~3408R~
                break;                                             //~3408I~
            }                                                      //~3408I~
        }                                                          //~3408I~
        if (Dump.Y) Dump.println("NotesFmtCsa:getCsaPiece piece="+piece+",line="+Pline);//~3408I~//~1Ah0R~
        return piece;                                              //~3408I~
    }                                                              //~3408I~
    //*************************************************************//~3412I~
    //*piece by piece                                              //~3412I~
    //*P[+|-]nmPT...      nm=00 for tray   ex)00HI00KA                        //~3412I~//~3414R~
    //*************************************************************//~3412I~
	private boolean putInitialPieceCsaPieceByPiece(String Pline,int Ppos,int Pcolor)//~3412R~
	{                                                              //~3412I~
    	boolean rc=true;
		int piece=0,ii,jj,len,pos;                                           //~3412I~
    //*******************************                              //~3412I~
        len=Pline.length();                                        //~3412I~
        for (pos=Ppos;pos+4<=len;)                                 //~3412I~
        {                                                          //~3412I~
	        ii=Pline.charAt(pos)-'0';                              //~3412I~
            pos++;                                                 //~3412I~
        	jj=Pline.charAt(pos)-'0';                              //~3412I~
            pos++;                                                 //~3412I~
            if (Pline.startsWith(CSA_TRAY_ALL,pos))                     //~3412I~
            {                                                      //~3412I~
            	setToTrayAllRemaining(Pcolor);                       //~3412I~
                break;                                             //~3412I~
            }                                                      //~3412I~
			piece=getPieceCsa(Pline,pos);                          //~3412I~
            pos+=2;                                                //~3414I~
            if (piece==0)                                          //~3412I~
            	break;                                             //~3412I~
            if (ii==0 && jj==0)	//tray                             //~3412I~
            {                                                      //~3412I~
            	if (piece>MAX_PIECE_TYPE_CAPTURE)                  //~3412I~
                	break;                                         //~3412I~
	            setToTrayInitial(Pcolor,piece,1/*add*/,true/*accum*/);//~3412R~
            }                                                      //~3412I~
            else                                                   //~3412I~
            if (ii>=1 && ii<=9 && jj>=1 && jj<=9)                  //~3412I~
            {                                                      //~3412I~
            	ii=S-ii;                                           //~3412I~
            	jj--;                                              //~3412I~
    			putInitialPiece1(ii,jj,Pcolor,piece);              //~3412I~
		        pieceByPiece=true;                                 //~3416I~
            }                                                      //~3412I~
            else                                                   //~3412I~
            	break;                                             //~3412I~
        }                                                          //~3412I~
        if (Dump.Y) Dump.println("NotesFmtCSa:getCsaPiece piece="+piece+",line="+Pline);//~3412I~//~1Ah0R~
        if (Dump.Y) showBoard();                                   //~3414M~
        return rc;                                              //~3412I~
    }                                                              //~3412I~
    //*************************************************************//~3408I~
	private void setHandicapCsa(String Pline,int Ppos)//~3408I~    //~3409R~
	{                                                              //~3408I~
    	int handicap=0,pos,piece;                                            //~3408I~
        int linelen=Pline.length();                                //~3408I~
        for (pos=Ppos;pos+4<=linelen;)                             //~3408I~
        {                                                          //~3408I~
        	int ii=Pline.charAt(pos)-'0';	                       //~3408I~
            pos++;                                                  //~3408I~
//        	int jj=Pline.charAt(pos+1)-'0';                        //~3408I~
            pos++;                                                  //~3408I~
            piece=getPieceCsa(Pline,pos);                          //~3408I~
            if (piece==PIECE_LANCE && ii==1)                        //~3408I~
                handicap|=HC_LANCE1;                               //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_LANCE && ii==9)                        //~3408I~
                handicap|=HC_LANCE2;                               //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_KNIGHT&& ii==2)                        //~3408I~
                handicap|=HC_KNIGHT1;                              //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_KNIGHT&& ii==8)                        //~3408I~
                handicap|=HC_KNIGHT2;                                //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_SILVER&& ii==3)                        //~3408I~
                handicap|=HC_SILVER1;                              //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_SILVER&& ii==7)                        //~3408I~
                handicap|=HC_SILVER2;                              //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_GOLD  && ii==4)                        //~3408I~
                handicap|=HC_GOLD1  ;                              //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_GOLD  && ii==6)                        //~3408I~
                handicap|=HC_GOLD2;                                //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_BISHOP)                               //~3408I~
                handicap|=HC_BISHOP;                               //~3408I~
            else                                                   //~3408I~
            if (piece==PIECE_ROOK)                                 //~3408I~
                handicap|=HC_ROOK;
        }//~3408I~
        if (Dump.Y) Dump.println("NotesFmtCsa:CsaHandicap="+Integer.toHexString(handicap));//~3408I~//~1Ah0R~
        this.handicap=handicap;                                  //~3408I~//~3409R~//~3412R~
        if (handicap!=0);                                          //~3408I~
        	putInitialPiece(handicap);	//initialy put pieaces         //~3408I~
 	}                                                              //~3408I~
    //*************************************************************//~3408I~
    //*-1:winner is last move,1:winner is next move                //~3408I~
    //*************************************************************//~3408I~
	private int getWinnerCsa(int Pwinner)                         //~3408I~
	{                                                              //~3408I~
    	int winner;                                                //~3408I~
    	if (Pwinner==0)                                            //~3408I~
        	winner=0;                                              //~3408I~
        else                                                       //~3408I~
    	if (Pwinner<0)                                             //~3408I~
        	winner=lastColor;                                      //~3408I~
        else                                                       //~3408I~
        	winner=-lastColor;                                     //~3408I~
        if (Dump.Y) Dump.println("NotesFmtCsa:getWinnerCsa Pwinner="+Pwinner+",lastColor="+lastColor+",winner="+winner);//~3408I~//~1Ah0R~
        return winner;                                             //~3408I~
    }                                                              //~3408I~
    //*************************************************************//~3408I~
	private void getGameoverReasonCsa(String Pline,int Ppos)       //~3408I~
	{                                                              //~3408I~
    	int id=0,ii;                                               //~3408R~
        for (ii=0;ii<CSA_GAMEOVERNAME.length;ii++)                 //~3408R~
        {                                                          //~3408I~
    		if (Pline.startsWith(CSA_GAMEOVERNAME[ii],Ppos))       //~3408R~
            {                                                      //~3408I~
            	id=CSA_GAMEOVER_ID[ii];                            //~3408R~
                break;                                             //~3408I~
            }                                                      //~3408I~
        }                                                          //~3408I~
        if (Dump.Y) Dump.println("NotesFmtCsa:getgameoverReason idx="+ii+",id="+id+",line="+Pline+",pos="+Ppos);//~3408R~//~3409R~//~1Ah0R~
        if (id!=0)                                                 //~3408I~
        {                                                          //~3408I~
	        gameoverReasonId=id;                                   //~3416I~
        	winner=getWinnerCsa(CSA_GAMEOVER_WINNER[ii]);           //~3408I~
        }                                                          //~3408I~
 	}                                                              //~3408I~
	//****************************************************************//~3408I~
	private void putInitialPieceCsa(String Pline,int Ppos,int Prow)//~3408I~
    {                                                              //~3408I~
    	int piece,color,ii,jj,col=0,pos;                               //~3408I~
        char ch;                                                   //~3408I~
    //***********************                                      //~3408I~
    	if (Dump.Y) Dump.println("NotesFmtCsa:putInitialPieceCsa line="+Pline+",row="+Prow);//~3408R~//~1Ah0R~
        int linelen=Pline.length();                                //~3408I~
        for (pos=Ppos;pos+3<=linelen;pos+=3)                       //~3408I~
        {                                                          //~3408I~
        	col++;                                                 //~3408I~
        	ch=Pline.charAt(pos);                                  //~3408I~
            if (ch==CSA_COLOR_BLACK)                               //~3408I~
            	color=1;                                           //~3408I~
            else                                                   //~3408I~
            if (ch==CSA_COLOR_WHITE)                               //~3408I~
            	color=-1;                                          //~3408I~
            else                                                   //~3408I~
            	color=0;                                           //~3408I~
            if (color==0)                                          //~3408I~
            	continue;                                          //~3408I~
            piece=getPieceCsa(Pline,pos+1);                        //~3408I~
            if (piece==0)                                          //~3408I~
            	continue;                                          //~3408I~
            ii=col-1;                                              //~3408I~
            jj=Prow-1;                                             //~3408I~
    		if (Dump.Y) Dump.println("NotesFmtCsa:putInitialPieceCsa setpiece i="+ii+",j="+jj+",color="+color+",piece="+piece);//~3408I~//~1Ah0R~
//  		fmtPos.setPiece(ii,jj,color,piece);                       //~3408I~//~3412R~
    		putInitialPiece1(ii,jj,color,piece);                   //~3412I~
        }                                                          //~3408I~
		pieceByPiece=true;                                         //~3416I~
		if (Dump.Y) showBoard();                                   //~3408I~
    }                                                              //~3408I~
	//****************************************************************//~3408I~
	private void getMoveCsa(String Pline,int Ppos,int Pcolor)//~3408I~
    {                                                              //~3408I~
    	int piece,color,ii,jj,ii2,jj2,pos;                   //~3408I~
        char ch;                                                   //~3408I~
    //***********************                                      //~3408I~
    	if (Dump.Y) Dump.println("NotesFmtCsa:getMoveCsa lien="+Pline+",color="+Pcolor);//~3408I~//~1Ah0R~
    	color=Pcolor;                                              //~3408I~
        int linelen=Pline.length();	                               //~3408I~
        for (pos=Ppos;pos+6<=linelen;)                             //~3408I~
        {                                                          //~3408I~
	        ii=Pline.charAt(pos)-'0';                          //~3408I~
            pos++;                                                 //~3408I~
        	jj=Pline.charAt(pos)-'0';                              //~3408R~
            pos++;                                                 //~3408I~
	        ii2=Pline.charAt(pos)-'0';                         //~3408I~
            pos++;                                                 //~3408I~
        	jj2=Pline.charAt(pos)-'0';                             //~3408R~
            pos++;                                                 //~3408I~
            piece=getPieceCsa(Pline,pos);	//after moved          //~3408I~
            pieceDrop=(ii==0 && jj==0);                          //~3408I~
            iTo=S-ii2;                                             //~3408I~
            jTo=jj2-1;                                             //~3408I~
            if (pieceDrop)                                      //~3408I~
            {                                                      //~3408I~
            	iFrom=0; jFrom=0;                                  //~3408I~
                pieceFrom=piece;                                   //~3408I~
                pieceTo=piece;                                     //~3408I~
            }                                                      //~3408I~
            else                                                   //~3408I~
            {                                                      //~3408I~
            	iFrom=S-ii;                                        //~3408I~
            	jFrom=jj-1;                                        //~3408I~
                pieceFrom=fmtPos.piece(iFrom,jFrom);               //~3408I~
                pieceTo=piece;                                     //~3408I~
            }                                                      //~3408I~
            moveNumber++;                                          //~3412I~
        	if (Dump.Y) Dump.println("NotesFmtCsa:getMoveCsa col="+color+",pieceFrom="+pieceFrom+",drop="+pieceDrop+",ito="+iTo+",jTo="+jTo+",iFrom="+iFrom+",jFrom="+jFrom+//~3408I~//~1Ah0R~
        			",pieceto="+pieceTo);                          //~3408I~
        	if (!getAction(color))                          //~3408I~
            	break;                                             //~3408I~
            lastColor=color;                                       //~3408I~
            pos+=2;                                                //~3408I~
            if (pos+8<=linelen && Pline.charAt(pos)==CSA_MULTISTMT)//~3408I~
            {                                                      //~3408I~
            	ch=Pline.charAt(pos);                              //~3408I~
                if (ch==CSA_COLOR_BLACK)                           //~3408I~
                	color=1;                                       //~3408I~
                else                                               //~3408I~
                if (ch==CSA_COLOR_WHITE)                           //~3408I~
                	color=-1;                                      //~3408I~
                else                                               //~3408I~
                	break;                                         //~3408I~
                pos++;                                             //~3408I~
            }                                                      //~3408I~
        }                                                          //~3408I~
    }                                                              //~3408I~
	//****************************************************************//~1Ah0I~
	public static void getMoveCsa(String Pline,GtpMove Pcbparm)//~1Ah0I~
    {                                                              //~1Ah0I~
    	int piece=0,ii,jj,ii2,jj2,iFrom=0,jFrom=0,iTo=0,jTo=0,pos=0;     //~1Ah0I~
        boolean pieceDrop=false;                                   //~1Ah0I~
    //***********************                                      //~1Ah0I~
    	if (Dump.Y) Dump.println("NotesFmtCsa:getMoveCsa lien="+Pline);//~1Ah0R~
        int linelen=Pline.length();                                //~1Ah0I~
        if (linelen>=6)                                            //~1Ah0I~
        {                                                          //~1Ah0I~
	        ii=Pline.charAt(pos)-'0';                              //~1Ah0I~
            pos++;                                                 //~1Ah0I~
        	jj=Pline.charAt(pos)-'0';                              //~1Ah0I~
            pos++;                                                 //~1Ah0I~
	        ii2=Pline.charAt(pos)-'0';                             //~1Ah0I~
            pos++;                                                 //~1Ah0I~
        	jj2=Pline.charAt(pos)-'0';                             //~1Ah0I~
            pos++;                                                 //~1Ah0I~
            piece=getPieceCsa(Pline,pos);	//after moved          //~1Ah0I~
            pieceDrop=(ii==0 && jj==0);                            //~1Ah0I~
            iTo=S-ii2;                                             //~1Ah0I~
            jTo=jj2-1;                                             //~1Ah0I~
            if (pieceDrop)                                         //~1Ah0I~
            {                                                      //~1Ah0I~
            	iFrom=0; jFrom=0;                                  //~1Ah0I~
            }                                                      //~1Ah0I~
            else                                                   //~1Ah0I~
            {                                                      //~1Ah0I~
            	iFrom=S-ii;                                        //~1Ah0I~
            	jFrom=jj-1;                                        //~1Ah0I~
            }                                                      //~1Ah0I~
        	if (Dump.Y) Dump.println("NotesFmtCsa:getMoveCsa piece="+piece+",ito="+iTo+",jTo="+jTo+",iFrom="+iFrom+",jFrom="+jFrom);//~1Ah0R~
        }                                                          //~1Ah0I~
        Pcbparm.xx=iTo; Pcbparm.yy=jTo; Pcbparm.oldxx=iFrom; Pcbparm.oldyy=jFrom;//~1Ah0I~
        Pcbparm.drop=pieceDrop;                                    //~1Ah0I~
        Pcbparm.piece=piece;                                       //~1Ah0I~
    }                                                              //~1Ah0I~
	//****************************************************************//~1Ah0I~
	public static String getMoveString(int Ppiece,int Px,int Py,int Poldx,int Poldy,boolean Pdrop)//~1Ah0I~
    {                                                              //~1Ah0I~
    	String movestr;                                            //~1Ah0I~
        char[] c=new char[6];                                      //~1Ah0R~
        int piece;                                                 //~1Ah0I~
    //***********************                                      //~1Ah0I~
    	if (Dump.Y) Dump.println("NotesFmtCSA:getMoveString piece="+Ppiece+",pos=("+Px+","+Py+")<--("+Poldx+","+Poldy+"),drop="+Pdrop);//~1Ah0R~
    	Point pTo=GtpMove.coord2BoardPos(Px,Py);                   //~1Ah0I~
    	Point pFrom=GtpMove.coord2BoardPos(Poldx,Poldy);           //~1Ah0I~
        int x=pTo.x;                                          //~1Ah0I~
        int y=pTo.y;                                         //~1Ah0I~
    	if (Pdrop)                                                 //~1Ah0M~
        {                                                          //~1Ah0I~
            c[0]='0';                                              //~1Ah0I~
            c[1]='0';                                              //~1Ah0I~
            c[2]=(char) ('0'+x);                                            //~1Ah0I~
            c[3]=(char) ('0'+y);                                            //~1Ah0I~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
            c[0]=(char) ('0'+pFrom.x);                                      //~1Ah0I~
            c[1]=(char) ('0'+pFrom.y);                                      //~1Ah0I~
            c[2]=(char) ('0'+x);                                            //~1Ah0I~
            c[3]=(char) ('0'+y);                                            //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Ppiece<PIECE_PROMOTED)                                 //~1Ah0I~
        	piece=Ppiece-1;                                        //~1Ah0I~
        else                                                       //~1Ah0I~
            piece=((Ppiece-PIECE_PROMOTED)-1)+MAX_PIECE_TYPE/*from TOKIN*/;//~1Ah0I~
        String pieceStr=CSA_PIECENAME[piece];                      //~1Ah0R~
        c[4]=pieceStr.charAt(0);                                   //~1Ah0I~
        c[5]=pieceStr.charAt(1);                                   //~1Ah0I~
        movestr=new String(c);                                     //~1Ah0R~
        return movestr;                                            //~1Ah0I~
    }                                                              //~1Ah0I~
	//*******************************************************************************************//~1Ah0I~
    public static String  getInitialPieceArangement()              //~1Ah0R~
    {                                                              //~1Ah0I~
        return INITIAL_PIECE;                                      //~1Ah0I~
    }                                                              //~1Ah0I~
	//*******************************************************************************************//~1Ah0I~
    public static void removeHandicapPiece(StringBuffer Psb,int Pposx,int Pposy)//~1Ah0R~
    {                                                              //~1Ah0I~
        int pos1=noteLineWidth*Pposy+notePieceWidth*Pposx+notePiecePos1;   ////~1Ah0I~
        int pos2=pos1+notePieceWidth;                                //~1Ah0I~
        Psb.replace(pos1,pos2,emptySpace);                        //~1Ah0I~
    }                                                              //~1Ah0I~
	//*******************************************************************************************//~1Ah0I~
    public static StringBuffer getPieceLayout(Notes Pnotes)         //~1Ah0I~
    {                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("NotesFmtCsa:getPieceLayout");    //~1Ah0I~
        StringBuffer sb=new StringBuffer(INITIAL_PIECE.length()*2);             //~1Ah0I~
        int yourcolor=Pnotes.yourcolor;                            //~1Ah0M~
        for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)  //row           //~1Ah0R~
        {                                                          //~1Ah0I~
        	sb.append("P"+(ii+1));                                 //~1Ah0R~
	        for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)   //cols     //~1Ah0R~
    	    {                                                      //~1Ah0I~
                int p;                                             //~1Ah0I~
                if (yourcolor>0)    //black                        //~1Ah0I~
                	p=Pnotes.pieces[jj][ii];                       //~1Ah0R~
                else                                               //~1Ah0I~
                	p=Pnotes.pieces[AG.BOARDSIZE_SHOGI-jj-1][AG.BOARDSIZE_SHOGI-ii-1];//+1Ah0R~
                int piece=p & 0xff;                                //~1Ah0I~
                int color=(p & 0x0300)>>8;	//2:black,1:free,0:white by ConnectedBoard:getAllBoardPieces//~1Ah0R~
            	if (piece==0)                                      //~1Ah0I~
		        	sb.append(emptySpace);                   //~1Ah0I~
                else                                               //~1Ah0I~
                {                                                  //~1Ah0I~
                	if (color==2)  //black                         //~1Ah0R~
    					sb.append(CSA_COLOR_BLACK);                //~1Ah0I~
                    else           //white                         //~1Ah0R~
    					sb.append(CSA_COLOR_WHITE);                //~1Ah0I~
	        		if (piece<PIECE_PROMOTED)                      //~1Ah0I~
    	    			piece--;                                   //~1Ah0I~
        			else                                           //~1Ah0I~
            			piece=(piece-PIECE_PROMOTED)-1+MAX_PIECE_TYPE/*from TOKIN*/;//~1Ah0I~
        			sb.append(CSA_PIECENAME[piece]);               //~1Ah0I~
                }                                                  //~1Ah0I~
            }                                                      //~1Ah0I~
        	sb.append("\n");                                       //~1Ah0I~
        }                                                          //~1Ah0I~
        for (int ii=0;ii<2;ii++)                                   //~1Ah0I~
        {                                                          //~1Ah0I~
	        boolean trayPiece=false;                               //~1Ah0I~
	        for (int jj=0;jj<MAX_PIECE_TYPE_CAPTURE;jj++)          //~1Ah0R~
    	    {                                                      //~1Ah0I~
                int piecectr=Pnotes.tray[ii][jj];                         //~1Ah0I~
                if (piecectr!=0)                                   //~1Ah0I~
                {                                                  //~1Ah0I~
                	if (!trayPiece)	//fist time	                   //~1Ah0I~
                    {                                              //~1Ah0I~
                    	if (yourcolor>0/*black*/ && ii==1/*lower tray*///~1Ah0R~
                        ||  yourcolor<0/*white*/ && ii==0/*upper tray*/)//~1Ah0I~
        					sb.append("P"+CSA_COLOR_BLACK);        //~1Ah0I~
                        else                                       //~1Ah0I~
        					sb.append("P"+CSA_COLOR_WHITE);        //~1Ah0I~
       		         	trayPiece=true;                            //~1Ah0I~
                    }                                              //~1Ah0I~
                    int pieceidx=MAX_PIECE_TYPE_CAPTURE-jj-1;      //~1Ah0I~
                    for (int kk=0;kk<piecectr;kk++)                    //~1Ah0I~
                    {                                              //~1Ah0I~
	                    sb.append("00"+CSA_PIECENAME[pieceidx]);   //~1Ah0R~
                    }                                              //~1Ah0I~
                }                                                  //~1Ah0I~
            }                                                      //~1Ah0I~
            if (trayPiece)                                         //~1Ah0I~
		    	sb.append("\n");                                   //~1Ah0I~
        }                                                          //~1Ah0I~
        return sb;                                                 //~1Ah0I~
    }                                                              //~1Ah0I~
}//NotesFmt                                                        //~3407R~
