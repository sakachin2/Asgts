//*CID://+dateR~:                             update#=  217;       //~3406R~
//***********************************************************************
//***********************************************************************
package jagoclient.board;

import jagoclient.Dump;
import static jagoclient.board.Field.*;

public class NotesFmtGam extends NotesFmt                                              //~v1A0R~//~3406R~//~3409R~
{
    protected static final char[] PIECENAME_ENGLISH={'P','L','N','S','G','B','R','K'};//~3408I~//~3409I~//~3410M~
    protected static final char GAM_DROP='*';                      //~3410I~
    protected static final char GAM_CAPTURE='x';                   //~3410I~
    protected static final char GAM_PROMOTE='+';                   //~3410I~
    protected static final char GAM_NONPROMOTE='=';                //~3411I~
    protected static final char GAM_MOVETO='-';                    //~3410I~
                                                                   //~3410I~
    private static final String[] GAM_GAMEOVERNAME={               //~3409I~
			"resign","quit","sennichite","time out",               //~3409I~
			"-0b","-0d","jishogi","KACHI",                         //~3409R~
			"HIKIWAKE","mated","-0h","ERROR"};                     //~3409I~
    private static final int[] GAM_GAMEOVER_WINNER={               //~3409I~
    	//*-1:winer is last move,1:winner is next move/            //~3409I~
			-1/*"TORYO"*/,0/*"CHUDAN"*/,0/*"SENNICHITE"*/,-1/*"TIME_UP"*/,//~3409I~
			-1/*"ILLEGAL_MOVE"*/,1/*"-ILLEGAL_ACTION"*/,0/*"JISHOGI"*/,-1/*"KACHI"*/,//~3409I~
			0/*"HIKIWAKE"*/,-1/*"TSUMI"*/,0/*"FUZUMI"*/,0/*"ERROR"*/};//~3409I~
    private static final int[] GAM_GAMEOVER_ID={                   //~3409I~
    		GAMEOVERID_RESIGN,                                     //~3409I~
    		GAMEOVERID_SUSPEND,                                    //~3409I~
    		GAMEOVERID_LOOP,                                       //~3409I~
    		GAMEOVERID_TIMEOUT,                                     //~3409I~
    		GAMEOVERID_FOUL,                                       //~3409I~
    		GAMEOVERID_FOUL,                                       //~3409I~
    		GAMEOVERID_INFINITE,                                   //~3409I~
    		GAMEOVERID_WIN,                                        //~3409I~
    		GAMEOVERID_EVEN,                                       //~3409I~
    		GAMEOVERID_CHECKMATE,                                  //~3409I~
    		GAMEOVERID_NOCHECKMATE,                                //~3409I~
    		GAMEOVERID_ERROR,                                      //~3409I~
			};                                                     //~3409I~
    //~3409I~
    private static final String GAM_BLACK="Black: ";
    private static final String GAM_WHITE="White: ";
    private static final String GAM_MOVEFIRST_BLACK="Black to Move";//~3416I~
    private static final String GAM_MOVEFIRST_WHITE="White to Move";//~3416I~
    private static final String GAM_HANDICAP="Handicap: ";         //~3409I~
    private static final String GAM_TRAY_BLACK="Black in hand: ";  //~3412I~
    private static final String GAM_TRAY_WHITE="White in hand: ";  //~3412I~
    private static final String GAM_TRAY_NONE="nothing";           //~3412I~
    private static final String GAM_PASS="...";                     //~3408I~
    private static final char GAM_COMMENT='*';
    private static final int GAM_BOARD_LAYOUT='|';//~3409I~
    private static final char GAM_BOARD_LAYOUT_BLACK='b';          //~3412I~
    private static final char GAM_BOARD_LAYOUT_WHITE='w';          //~3412I~
 
    protected int posI,posJ;//out of getPositionEnglish                      //~3408R~//~3411R~
    //*****************************************************************************//~3408I~
    //*****************************************************************************//~3409I~
    //*****************************************************************************//~3409I~
    @Override                                                      //~3409I~
	protected boolean setTreeEntry(String Pline)//~3408I~//~3409R~
	{                                                              //~3408I~
    	boolean rc=true;                                           //~3408I~
    //****************************                                 //~3408I~
        if (Dump.Y) Dump.println("setGamTreeEntry line="+Pline);   //~3408I~
        int linelen=Pline.length();                                //~3408I~
        if (linelen==0)                                            //~3408I~
        	return rc;                                             //~3408I~
        if (Pline.startsWith(GAM_HANDICAP))                        //~3409I~
        {                                                          //~3409I~
        	handicap=getHandicapGam(Pline,GAM_HANDICAP.length());  //~3409I~
            if (Dump.Y) Dump.println("Gam handicap="+Integer.toHexString(handicap));//~3409I~
            if (handicap!=0)                                       //~3414I~
				putInitialPiece(handicap);	//initialy put pieaces     //~3409I~//~3414R~
        	return rc;                                             //~3409I~
        }                                                          //~3409I~
        if (Pline.startsWith(GAM_BLACK))                           //~3408I~
        {                                                          //~3408I~
        	blackName=Pline.substring(GAM_BLACK.length());           //~3408I~
            if (Dump.Y) Dump.println("Gam black="+blackName);      //~3409I~
        	return rc;                                             //~3408I~
        }                                                          //~3408I~
        if (Pline.startsWith(GAM_WHITE))                           //~3408I~
        {                                                          //~3408I~
        	whiteName=Pline.substring(GAM_BLACK.length());           //~3408I~
            if (Dump.Y) Dump.println("Gam white="+whiteName);      //~3409I~
        	return rc;                                             //~3408I~
        }                                                          //~3408I~
    	if (Pline.charAt(0)==GAM_BOARD_LAYOUT)                         //~3416I~
        	initialBoardLayout(Pline);                             //~3416I~
        else                                                       //~3416I~
        if (Pline.startsWith(GAM_TRAY_BLACK))                      //~3412I~
        {                                                          //~3412I~
        	setTray(Pline,GAM_TRAY_BLACK.length(),1);              //~3412I~
			if (Dump.Y) showBoard();                               //~3414I~
        	return rc;                                             //~3412I~
        }                                                          //~3412I~
        if (Pline.startsWith(GAM_TRAY_WHITE))                      //~3412I~
        {                                                          //~3412I~
        	setTray(Pline,GAM_TRAY_WHITE.length(),-1);              //~3412I~//~3414R~
			if (Dump.Y) showBoard();                               //~3414I~
        	return rc;                                             //~3412I~
        }                                                          //~3412I~
        if (Pline.startsWith(GAM_MOVEFIRST_BLACK))                  //~3416I~
        {                                                          //~3416I~
        	if (Dump.Y) Dump.println("Move1st BLACK");             //~3416I~
        	move1st=1;                                             //~3416I~
    	    if (!swInitPiece)                                          //~3409I~//~3416I~
				putInitialPiece(handicap);	//initialy put pieaces     //~3409I~//~3416I~
        	return rc;                                             //~3416I~
        }                                                          //~3416I~
        if (Pline.startsWith(GAM_MOVEFIRST_WHITE))                  //~3416I~
        {                                                          //~3416I~
        	if (Dump.Y) Dump.println("Move1st WHITE");             //~3416I~
        	move1st=-1;                                            //~3416I~
    	    if (!swInitPiece)                                      //~3416I~
				putInitialPiece(handicap);	//initialy put pieaces //~3416I~
        	return rc;                                             //~3416I~
        }                                                          //~3416I~
        if (Pline.charAt(0)==GAM_COMMENT)                          //~3409I~
        	return rc;                                             //~3409I~
        rc=getMoveGam(Pline);                           //~3408I~  //~3410R~
        return rc;                                                 //~3408I~
	}                                                              //~3408I~
    //*****************************************************************************//~3409I~
	private final static String[] HANDICAP_GAM={                   //~3409I~
    		"Lance","Right Lance","Bishop","Rook","Lance&Rook","2","3","4","5","Left 5","6","8","10"};//~3409I~
	private int getHandicapGam(String Pline,int Ppos)          //~3409I~
	{                                                              //~3409I~
    	int hc=0;                                                  //~3409I~
    //****************************                                 //~3409I~
    	for (int ii=0;ii<HANDICAP_GAM.length;ii++)                 //~3409I~
	    	if (Pline.startsWith(HANDICAP_GAM[ii],Ppos))            //~3409R~
            {                                                      //~3409I~
            	hc=HANDICAPTB[ii+1];                                //~3409I~
            }                                                      //~3409I~
        return hc;                                                 //~3409I~
    }                                                              //~3409I~
    //******************************************************************//~3412I~
    //sample                                                       //~3412I~
	//| * bG  *  *  * wS  *  *  * |b                               //~3412I~
	//| *  * wR  * wP  *  *  *  * |c                               //~3412I~
    //******************************************************************//~3412I~
	private boolean initialBoardLayout(String Pline)               //~3412I~//~3416R~
	{                                                              //~3412I~
    	boolean rc=true;                                           //~3412I~
    	int col,row,color=0,pos,piece;                                       //~3412I~
    //*********************************                            //~3412I~
    	int len=Pline.length();                                    //~3412I~
        if (len==0)                                                //~3412I~
        	return rc;                                             //~3412I~
        char ch=Pline.charAt(len-1);                                    //~3412I~
		if (ch<'a'||ch>'i')                                        //~3412I~
        	return rc;                                             //~3412I~
        row=ch-'a';                                                //~3412I~
    	for (pos=1,col=0;pos+3<=len;pos++)                        //~3412I~//~3416R~
        {                                                          //~3412I~
	        ch=Pline.charAt(pos);                                  //~3412I~
            if (ch==' ') //empty                                   //~3416I~
            {                                                      //~3416I~
            	pos+=2;                                            //~3416I~
                col++;                                             //~3416I~
            	continue;                                          //~3416I~
            }                                                      //~3416I~
            if (ch==GAM_BOARD_LAYOUT_BLACK) //"b"                  //~3412I~
            {                                                      //~3412I~
            	color=1;	//black                                //~3412I~
                col++;                                             //~3412I~
            	continue;                                          //~3412I~
            }                                                      //~3412I~
            if (ch==GAM_BOARD_LAYOUT_WHITE)                        //~3412I~
            {                                                      //~3412I~
            	color=-1;	//white                                //~3412I~
                col++;                                             //~3412I~
            	continue;                                          //~3412I~
            }                                                      //~3412I~
			piece=getPieceEnglish(ch);                             //~3412I~
            pos++;                                                 //~3416I~
            if (piece==0)                                          //~3412I~
            	continue;                                          //~3412I~
	        ch=Pline.charAt(pos);                                  //~3416I~
            if (ch==GAM_PROMOTE)                                   //~3416R~
            	piece=Field.promoted(piece);                       //~3416I~
			putInitialPiece1(col-1,row,color,piece);                 //~3412I~//~3416R~
        } 
        pieceByPiece=true;                                         //~3416I~
    	return rc;//~3412I~
 	}                                                              //~3412I~
    //****************************************                     //~3412I~
	private void setTray(String Pline,int Ppos,int Pcolor)         //~3412I~
	{                                                              //~3412I~
    	int piece=0,ctr=0;                                     //~3412I~
    //*********************************                            //~3412I~
    	int len=Pline.length();                                    //~3412I~
        if (len<=Ppos)                                             //~3412I~
        	return;                                                //~3412I~
        if (Pline.startsWith(GAM_TRAY_NONE,Ppos))                   //~3412I~
        	return;                                                //~3412I~
    	for (int pos=Ppos;pos<len;pos++)                           //~3412I~
        {                                                          //~3412I~
        	char ch=Pline.charAt(pos);                             //~3412I~
			piece=getPieceEnglish(ch);                                  //~3412I~
            if (piece==0 || piece>MAX_PIECE_TYPE_CAPTURE)                  //~3412I~
            	continue;                                          //~3412I~
            pos++;                                                 //~3412I~
            ctr=getTrayCtr(Pline,pos,len);                         //~3412I~
            if (ctr<0)                                             //~3412I~
            	ctr=1;                                             //~3412I~
			setToTrayInitial(Pcolor,piece,ctr,false/*set*/);       //~3412I~
            if (ctr>10)                                            //~3412I~
            	pos++;                                             //~3412I~
        }                                                          //~3412I~
        if (Dump.Y) Dump.println("whiteName="+whiteName);          //~3412I~
 	}                                                              //~3412I~
    //****************************************                     //~3412I~
	private int getTrayCtr(String Pline,int Ppos,int Plen)         //~3412I~
	{                                                              //~3412I~
    	int rc=-1,pos;                                             //~3412I~
    //************************                                     //~3412I~
    	if (Ppos>=Plen)                                            //~3412I~
        	return rc;                                             //~3412I~
        pos=Ppos;                                                  //~3412I~
        char ch=Pline.charAt(pos);                                 //~3412I~
		if (ch<'0' || ch>'9')                                      //~3412I~
        	return rc;                                             //~3412I~
        rc=ch-'0';                                                 //~3412I~
        pos++;                                                     //~3412I~
        if (pos>=Plen)                                             //~3412I~
	        return rc;                                             //~3412I~
        ch=Pline.charAt(pos);                                      //~3412I~
		if (ch<'0' || ch>'9')                                      //~3412I~
        	return rc;                                             //~3412I~
        rc=rc*10+ch-'0';                                           //~3412I~
        if (Dump.Y) Dump.println("chkTrayCtr rc="+rc+"line="+Pline.substring(Ppos,Plen));//~3412I~//~3414R~
    	return rc;                                                 //~3412I~
 	}                                                              //~3412I~
    //*****************************************************************************//~3408I~
	private boolean getMoveGam(String Pline)//~3408I~              //~3410R~
	{                                                              //~3408I~
    	boolean rc=true;                                           //~3408I~
        char ch;                                               //~3408I~
        int pos,pos2;                                              //~3408I~
    //****************************                                 //~3408I~
        if (Dump.Y) Dump.println("getMoveGam line="+Pline);         //~3408I~//~3410R~
        int linelen=Pline.length();                                  //~3408I~
        for (pos=0;pos<linelen;pos++)                              //~3408I~
        {                                                          //~3408I~
        	ch=Pline.charAt(pos);                                  //~3408I~
            if (ch==' ')                                           //~3408I~
            	continue;                                          //~3408I~
            if (ch>='0'&&ch<='9')	//move number                  //~3408I~
            {                                                      //~3408I~
            	pos++;                                             //~3408I~
                for (;pos<linelen;pos++)                           //~3408I~
                {                                                  //~3408I~
		        	ch=Pline.charAt(pos);                          //~3408I~
                    if (ch=='.')                                   //~3408I~
                    	break;                                     //~3408I~
                }                                                  //~3408I~
                continue;                                          //~3408I~
            }                                                      //~3408I~
            for (pos2=pos;pos2<linelen;pos2++)                     //~3408I~
            {                                                      //~3408I~
                ch=Pline.charAt(pos2);                              //~3408I~//~3409R~
                if (ch!=' ')                                       //~3408I~
                    break;                                         //~3408I~
            }                                                      //~3408I~
            pos=pos2;                                              //~3408I~
            for (pos2=pos;pos2<linelen;pos2++)                     //~3408I~
            {                                                      //~3408I~
                ch=Pline.charAt(pos2);                              //~3408I~//~3409R~
                if (ch==' ')                                       //~3408I~
                    break;                                         //~3408I~
            }                                                      //~3408I~
            if (!getMoveGam1(Pline,pos,pos2-pos))      //~3408I~   //~3410R~
            	break;                                             //~3408I~
            pos=pos2;                                              //~3408I~
        }                                                          //~3408I~
        return rc;                                                 //~3408I~
	}                                                              //~3408I~
    //*****************************************************************************//~3408I~
	private boolean getFromPositionGam(int Ppiece,int Pcolor)      //~3408I~
	{                                                              //~3408I~
		boolean rc=getMoveFromSub(0/*modifier*/,Pcolor);         //~3408I~
        if (Dump.Y) Dump.println("getFromPosgam rc="+rc);          //~3409I~
        if (pieceDrop)                                             //~3411I~
        {                                                          //~3411I~
        	iFrom=0; jFrom=0;                                      //~3411I~
        }                                                          //~3411I~
        return rc;                                                 //~3408I~
    }                                                              //~3408I~
    //*************************************************************//~3409I~
    //*-1:winner is last move,1:winner is next move                //~3409I~
    //*************************************************************//~3409I~
	protected int getWinnerGam(int Pwinner)                          //~3409I~//~3410R~
	{                                                              //~3409I~
    	int winner;                                                //~3409I~
    	if (Pwinner==0)                                            //~3409I~
        	winner=0;                                              //~3409I~
        else                                                       //~3409I~
    	if (Pwinner<0)                                             //~3409I~
        	winner=lastColor;                                      //~3409I~
        else                                                       //~3409I~
        	winner=-lastColor;                                     //~3409I~
        if (Dump.Y) Dump.println("getWinnerCsa Pwinner="+Pwinner+",lastColor="+lastColor+",winner="+winner);//~3409I~
        return winner;                                             //~3409I~
    }                                                              //~3409I~
    //*****************************************************************************//~3409I~
	private boolean getGameoverReasonGam(String Pline,int Ppos)    //~3409I~
	{                                                              //~3409I~
    	int id=0,ii;                                               //~3409I~
        boolean rc=false;                                          //~3409I~
        for (ii=0;ii<GAM_GAMEOVERNAME.length;ii++)                 //~3409I~
        {                                                          //~3409I~
    		if (Pline.startsWith(GAM_GAMEOVERNAME[ii],Ppos))       //~3409I~
            {                                                      //~3409I~
            	id=GAM_GAMEOVER_ID[ii];                            //~3409I~
                break;                                             //~3409I~
            }                                                      //~3409I~
        }                                                          //~3409I~
        if (id!=0)                                                 //~3409I~
        {                                                          //~3409I~
        	gameoverReasonId=id;                                   //+3416I~
        	winner=getWinnerGam(GAM_GAMEOVER_WINNER[ii]);          //~3409R~
            rc=true;                                               //~3409I~
        }                                                          //~3409I~
        if (Dump.Y) Dump.println("getgameoverReasonGam rc="+rc+",idx="+ii+",id="+id+",line="+Pline.substring(Ppos));//~3409I~
        return rc;                                                 //~3409I~
    }                                                              //~3409I~
    //*****************************************************************************//~3408I~
	private boolean getMoveGam1(String Pline,int Ppos,int Plen)//~3408I~//~3410R~
	{                                                              //~3408I~
    	boolean rc=true,promote;                                           //~3408I~
        char ch;                                               //~3408I~
        int pos,len,piece,pieceto,ii1,ii2,jj1,jj2,color;                                               //~3408I~
        boolean drop=false;                                        //~3409I~
    //****************************                                 //~3408I~
        if (Dump.Y) Dump.println("getMoveGam1 line="+Pline.substring(Ppos,Ppos+Plen));//~3408I~//~3410R~
        pos=Ppos;                                                  //~3408I~
        len=Plen;                                                  //~3408I~
        if (Pline.startsWith(GAM_PASS,pos))                         //~3408I~
        {                                                          //~3408I~
        	move1st=-move1st;                                      //~3408I~
            return rc;                                             //~3408I~
        }                                                          //~3408I~
        if (getGameoverReasonGam(Pline,pos))                       //~3409I~
            return rc;                                             //~3409I~
        ch=Pline.charAt(pos);                                      //~3408I~
        if (ch==GAM_PROMOTE)                                       //~3408I~
        {                                                          //~3408I~
        	promote=true;                                          //~3408I~
            pos++;len--;                                           //~3408I~
        }                                                          //~3408I~
        else                                                       //~3408I~
        	promote=false;                                         //~3408I~
        if (len==0)                                                //~3408I~
        	return rc;                                             //~3408I~
        ch=Pline.charAt(pos);                                      //~3408I~
        piece=getPieceEnglish(ch);                                     //~3408I~
        if (piece==0)                                              //~3408I~
        	return rc;                                             //~3408I~
        if (promote)                                               //~3408I~
        	piece=Field.promoted(piece);                           //~3408I~
        pieceto=piece;                                             //~3408I~
        pos++;len--;                                               //~3408I~
        if (len==0)                                                 //~3408I~//~3409R~
        	return rc;                                             //~3408I~
        ch=Pline.charAt(pos);                                      //~3408I~
        ii1=-1; jj1=-1;                                                   //~3408I~
        pos++;len--;                                               //~3409I~
        if (len<2)                                                 //~3409I~
        	return rc;                                             //~3409I~
        switch(ch)                                                 //~3408I~
        {                                                          //~3408I~
        case GAM_DROP:       //"*"                                 //~3408I~
        	drop=true;                                             //~3409I~
        	ii1=0; jj1=0;    //From                                //~3408I~
			if (!getPositionEnglish(Pline,pos))                      //~3408I~//~3409R~//~3411R~
            	return rc;                                         //~3408I~
            ii2=posI; jj2=posJ;   //To                             //~3408I~
            pos+=2; len-=2;                                        //~3408I~//~3409R~
        	break;                                                 //~3408I~
        case GAM_CAPTURE:    //"x"                                 //~3408I~
			if (!getPositionEnglish(Pline,pos))                      //~3408I~//~3409R~//~3411R~
            	return rc;                                         //~3408I~
            ii2=posI; jj2=posJ;   //To                             //~3408I~
            pos+=2; len-=2;                                        //~3408I~//~3409R~
        	break;                                                 //~3408I~
        case GAM_MOVETO:       //"-"                               //~3408I~
			if (!getPositionEnglish(Pline,pos))                      //~3408I~//~3409R~//~3411R~
            	return rc;                                         //~3408I~
            ii2=posI; jj2=posJ;   //To                             //~3408I~
            pos+=2; len-=2;                                        //~3408I~//~3409R~
        	break;                                                 //~3408I~
        default:			//from addr                            //~3408I~
	        pos--;len++;                                           //~3409I~
			if (!getPositionEnglish(Pline,pos))                        //~3408I~//~3409R~//~3411R~
            	return rc;                                         //~3408I~
            pos+=2; len-=2;                                        //~3408I~
            ii2=posI; jj2=posJ;
            if (len==0)                                            //~3408I~
                break;                                             //~3408I~
	        ch=Pline.charAt(pos);                                  //~3408I~
            if (ch==' '||ch==GAM_PROMOTE)                          //~3408I~
                break;                                             //~3408I~
            if (ch==GAM_MOVETO)                                    //~3408I~//~3409R~
            {                                                      //~3408I~
            	pos++; len--;                                      //~3408I~
            }                                                      //~3408I~
            if (len<2)                                             //~3408I~
            	break;                                             //~3408I~
            ii1=posI; jj1=posJ;                                      //~3408I~
			if (!getPositionEnglish(Pline,pos))                        //~3408I~//~3411R~
            	return rc;                                         //~3408I~
	        ii2=posI; jj2=posJ;                                    //~3408I~
            pos+=2; len-=2;                                        //~3408I~
        }                                                          //~3408I~
        if (len>0)                                                 //~3408I~
        {                                                          //~3408I~
	        ch=Pline.charAt(pos);                                  //~3408I~
            if (ch==GAM_PROMOTE)                                  //~3408I~
            {                                                      //~3408I~
                pieceto=Field.promoted(piece);                     //~3408I~
                pos++; len--;                                      //~3408I~
            }                                                      //~3408I~
        }                                                          //~3408I~
        moveNumber++;                                              //~3408I~
        color=moveNumber%2==1?1:-1;                                //~3408I~
        if (move1st==-1)                                           //~3408I~
        	color=-color;                                           //~3408I~
        pieceTo=pieceto;                                           //~3409M~
        pieceFrom=piece;                                           //~3409M~
        pieceDrop=drop;                                            //~3409M~
        iTo=ii2; jTo=jj2;                                          //~3409M~
        if (ii1<0)	//from serach required                         //~3408M~
        {                                                          //~3408M~
			if (!getFromPositionGam(piece,color))//set iFrom,jFrom //~3408I~
            	return rc;                                         //~3408I~
        }	                                                       //~3408M~
        else                                                       //~3409I~
        {                                                          //~3409I~
			iFrom=ii1; jFrom=jj1;                                  //~3409I~
        }                                                          //~3409I~
        lastColor=color;                                           //~3409I~
        getAction(color);                                    //~3408I~
        return rc;                                                 //~3408I~
	}                                                              //~3408I~
    //*****************************************************************************//~3408I~//~3409M~//~3410M~
	protected int getPieceEnglish(char Ppieceid)                     //~3408I~//~3409I~//~3410M~
	{                                                              //~3408I~//~3409M~//~3410M~
    	int piece=0;                                               //~3408I~//~3409M~//~3410M~
    	for (int ii=0;ii<PIECENAME_ENGLISH.length;ii++)              //~3408I~//~3409I~//~3410M~
        	if (Ppieceid==PIECENAME_ENGLISH[ii])                       //~3408I~//~3409I~//~3410M~
            {	                                                   //~3408I~//~3409M~//~3410M~
            	piece=ii+1;                                        //~3408I~//~3409M~//~3410M~
            }                                                      //~3408I~//~3409M~//~3410M~
        if (Dump.Y) Dump.println("getPieceEnglish id="+Ppieceid+",piece="+piece);//~3408I~//~3409I~//~3410M~
        return piece;                                              //~3408I~//~3409M~//~3410M~
    }                                                              //~3408I~//~3409M~//~3410M~
    //*****************************************************************************//~3410M~
	protected boolean getPositionEnglish(String Pline,int Ppos)    //~3410M~
	{                                                              //~3410M~
    	boolean rc=true;                                           //~3410M~
    //*********************************                            //~3410M~
        char ch=Pline.charAt(Ppos);                                //~3410M~
        int ii=S-(ch-'0');                                         //~3410M~
        int jj=Pline.charAt(Ppos+1);                               //~3410M~
        if (jj>='1' && jj<='9')                                    //~3410M~
            jj=jj-'1';                                             //~3410M~
        else                                                       //~3410M~
        if (jj>='a' && jj<='i')                                    //~3410M~
            jj=jj-'a';                                             //~3410M~
        else                                                       //~3410M~
        	rc=false;                                              //~3410M~
        posI=ii;                                                   //~3410M~
        posJ=jj;                                                   //~3410M~
        if (Dump.Y) Dump.println("getPositionEnglish rc="+rc+",ii="+ii+",jj="+jj+",line="+Pline.substring(Ppos,Ppos+2));//~3410M~
        return rc;                                                 //~3410M~
    }                                                              //~3410M~
}//NotesFmt                                                        //~3407R~
