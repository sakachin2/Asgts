//*CID://+dateR~:                             update#=  261;       //~3406R~
//***********************************************************************
//***********************************************************************
package jagoclient.board;
import static jagoclient.board.Field.*;                            //~3410I~
import jagoclient.Dump;
public class NotesFmtPsn extends NotesFmtGam                                           //~v1A0R~//~3406R~//~3409R~//~3410R~
{
    public  static final char PSN_HEADER='[';                      //~3411R~
    private static final char PSN_HEADER_CLOSE=']';
    private static final String PSN_BLACK="[Sente ";               //~3409I~
    private static final String PSN_WHITE="[Gote ";                //~3409I~
    private static final String PSN_HANDICAP="[Handicap ";         //~3409R~
    private static final String PSN_GAMEINIT="[FEN ";              //~3409I~
    private static final String PSN_GAMEINIT2="[SFEN ";            //~3409I~
    private static final String PSN_RESULT="[Result ";             //~3409I~
    private static final String PSN_WINNER_BLACK="1-0";            //~3410I~
    private static final String PSN_WINNER_WHITE="0-1";            //~3410I~
    private static final String PSN_WINNER_EVEN="1/2-1/2";         //~3411I~
    private static final char PSN_MOVENUMBER_DELM='.';
    public  static final char PSN_COMMENT='{';                     //~3411R~
    private static final char PSN_COMMENT_CLOSE='}';               //~3411I~
    private static final char PSN_TIME='(';
    private static final char PSN_TIME_CLOSE=')';//~3410I~
    private static final char PSN_SENTE='s';                       //~3410I~
    private static final char PSN_GOTE='g';                        //~3410I~
    private static final char PSN_SENTE2='b';                      //~3410I~
    private static final char PSN_GOTE2='w';                       //~3410I~
    private static final char PSN_PASS='.';
    private static final char PSN_NOTRAY='-';//~3410I~
                                                                   //~3410I~
	private final static String[] HANDICAP_PSN={ /*by NotesFmt:HANDICAPTB*/                  //~3409I~//~3410I~
    		"lance","right lance","bishop","rook","rook and lance","2","3","4","5",//~3410I~
			"left 5"/*not defined on PSN spec*/,"6","8","10"/*not on PSN spec*/};//~3410I~
        /* no support "7" which is on PSN spec*/                   //~3410I~
    private static final String[] PSN_GAMEOVERNAME={               //~3410I~
			"Resign","Interrupt","Sennichite","Timeup",               //~3410I~//~3411R~
			"IllegalMove","IllegalAction","Jishogi","KACHI",
			"HIKIWAKE","Mate","-0h","ERROR"};                     //~3410I~
    private static final int[] PSN_GAMEOVER_WINNER={               //~3410I~
    	//*-1:winer is last move,1:winner is next move/            //~3410I~
			-1/*"TORYO"*/,0/*"CHUDAN"*/,0/*"SENNICHITE"*/,-1/*"TIME_UP"*/,//~3410I~
			-1/*"ILLEGAL_MOVE"*/,1/*"-ILLEGAL_ACTION"*/,0/*"JISHOGI"*/,-1/*"KACHI"*/,//~3410I~
			0/*"HIKIWAKE"*/,-1/*"TSUMI"*/,0/*"FUZUMI"*/,0/*"ERROR"*/};//~3410I~
    private static final int[] PSN_GAMEOVER_ID={                   //~3410I~
    		GAMEOVERID_RESIGN,                                     //~3410I~
    		GAMEOVERID_SUSPEND,                                    //~3410I~
    		GAMEOVERID_LOOP,                                       //~3410I~
    		GAMEOVERID_TIMEOUT,                                    //~3410I~
    		GAMEOVERID_FOUL,                                       //~3410I~
    		GAMEOVERID_FOUL,                                       //~3410I~
    		GAMEOVERID_INFINITE,                                   //~3410I~
    		GAMEOVERID_WIN,                                        //~3410I~
    		GAMEOVERID_EVEN,                                       //~3410I~
    		GAMEOVERID_CHECKMATE,                                  //~3410I~
    		GAMEOVERID_NOCHECKMATE,                                //~3410I~
    		GAMEOVERID_ERROR,                                      //~3410I~
			};                                                     //~3410I~
 
    private int posWord;                                           //~3410I~//~3411R~
    private boolean swFEN;                                         //~3410I~
    private int lineLen;
    private boolean result,posStopper,posDquote;                             //~3410R~//~3411R~
    //*****************************************************************************//~3408I~
    //*****************************************************************************//~3409I~
    //*****************************************************************************//~3409I~
    @Override                                                      //~3409I~
	protected boolean setTreeEntry(String Pline)                   //~3410I~
	{                                                              //~3410I~
    	boolean rc=true;
     //****************************                                 //~3410I~
        if (Dump.Y) Dump.println("setPsnTreeEntry line="+Pline);   //~3410I~
        lineLen=Pline.length();                                    //~3410R~
        if (lineLen==0)                                            //~3410R~
        	return rc;                                             //~3410I~
        if (Pline.charAt(0)==PSN_HEADER)                           //~3410I~
        {                                                          //~3410I~
        	getHeader(Pline);                                      //~3410I~
            return rc;                                             //~3410I~
        }                                                          //~3410I~
        if (!swFEN && !swInitPiece)                                          //~3409I~//~3410M~//~3411R~
			putInitialPiece(handicap);	//initialy put pieaces     //~3409I~//~3410M~
        rc=getMovePsn(Pline);                           //~3408I~  //~3410M~
        return rc;                                                 //~3410I~
	}                                                              //~3410I~
    //*****************************************************************************//~3410I~
    //* multiline [xxx ], [xxx] allowed                            //~3410I~
    //*****************************************************************************//~3410I~
	private boolean getHeader(String Pline)                        //~3410I~
    {                                                              //~3410I~
    	boolean rc=true;                                           //~3410I~
        int pos=0,len;                                             //~3410I~
    //***********************                                      //~3410I~
        for (;;)                                                   //~3410I~
        {                                                          //~3410I~
            len=getWordPos(Pline,pos,PSN_HEADER_CLOSE);            //~3410I~
            if (!posStopper)   //closer not found                  //~3410I~
            	break;	                                           //~3410I~
            pos=posWord;                                           //~3410I~
            getHeader1(Pline.substring(pos,pos+len));              //~3410R~
            pos+=len;                                              //~3410I~
            len=getWordPos(Pline,pos,PSN_HEADER);                  //~3410I~
            if (!posStopper)   //closer not found                  //~3410I~
            	break;                                             //~3410I~
            pos=posWord+len-1;                                     //~3410R~
        }                                                          //~3410I~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3410I~
	protected boolean getHeader1(String Phdr)                      //~3410R~
	{                                                              //~3408I~
    	boolean rc=true;                                           //~3408I~
    //****************************                                 //~3408I~
        if (Dump.Y) Dump.println("getHeader1 hdr="+Phdr);   //~3408I~//~3410R~
        if (Phdr.startsWith(PSN_HANDICAP))                        //~3409I~//~3410R~
        {                                                          //~3409I~
        	handicap=getHandicapPsn(Phdr,PSN_HANDICAP.length());  //~3409I~//~3410R~
            if (Dump.Y) Dump.println("Gam handicap="+Integer.toHexString(handicap));//~3409I~
			putInitialPiece(handicap);	//initialy put pieaces     //~3409I~
        	return rc;                                             //~3409I~
        }                                                          //~3409I~
        if (Phdr.startsWith(PSN_BLACK))                           //~3408I~//~3410R~
        {                                                          //~3408I~
        	blackName=getWord(Phdr,PSN_BLACK.length());           //~3408I~//~3410R~
            if (Dump.Y) Dump.println("PSN black="+blackName);      //~3409I~//~3410R~
        	return rc;                                             //~3408I~
        }                                                          //~3408I~
        if (Phdr.startsWith(PSN_WHITE))                           //~3408I~//~3410R~
        {                                                          //~3408I~
        	whiteName=getWord(Phdr,PSN_WHITE.length());           //~3408I~//~3410R~
            if (Dump.Y) Dump.println("PSN white="+whiteName);      //~3409I~//~3410R~
        	return rc;                                             //~3408I~
        }                                                          //~3408I~
        if (Phdr.startsWith(PSN_RESULT))                            //~3410I~
        {                                                          //~3410I~
        	int len=getWordPos(Phdr,PSN_RESULT.length());          //~3410I~
            if (len==0)                                            //~3410I~
            	return rc;                                         //~3410I~
            if (Phdr.startsWith(PSN_WINNER_BLACK,posWord))         //~3410I~
            	winner=1;                                          //~3410I~
            else                                                   //~3410I~
            if (Phdr.startsWith(PSN_WINNER_WHITE,posWord))         //~3410I~
            	winner=-1;                                         //~3410I~
            else                                                   //~3410I~
            	winner=0;
            result=true;//~3410I~
            if (Dump.Y) Dump.println("PSN result winner="+winner); //~3410I~
        	return rc;                                             //~3410I~
        }                                                          //~3410I~
        if (Phdr.startsWith(PSN_GAMEINIT))     //FEN               //~3410R~
        {                                                          //~3410I~
        	gameinit(Phdr,PSN_GAMEINIT.length());                  //~3410R~
            swFEN=true;                                            //~3410I~
        	return rc;                                             //~3410I~
        }                                                          //~3410I~
        if (Phdr.startsWith(PSN_GAMEINIT2))   //SFEN               //~3410R~
        {                                                          //~3410I~
        	gameinit2(Phdr,PSN_GAMEINIT2.length());                //~3410R~
            swFEN=true;                                            //~3410I~
        	return rc;                                             //~3410I~
        }                                                          //~3410I~
        return rc;                                                 //~3408I~
	}                                                              //~3408I~
    //*****************************************************************************//~3409I~
    //*PSN spec                                                    //~3410I~
    //*****************************************************************************//~3410I~
	private int getHandicapPsn(String Phdr,int Ppos)          //~3409I~//~3410R~
	{                                                              //~3409I~
    	int hc=0;                                                  //~3409I~
    //****************************                                 //~3409I~
    	getWordPos(Phdr,Ppos);                             //~3410R~
        if (posWord>=0)                                            //~3410R~
        {                                                          //~3410I~
    		for (int ii=0;ii<HANDICAP_PSN.length;ii++)                 //~3409I~//~3410R~
	    		if (Phdr.startsWith(HANDICAP_PSN[ii],posWord))            //~3409R~//~3410R~
            	{                                                      //~3409I~//~3410R~
            		hc=HANDICAPTB[ii+1];	//NotesFmt                                //~3409I~//~3410R~
            	}                                                      //~3409I~//~3410R~
        }                                                          //~3410I~
        return hc;                                                 //~3409I~
    }                                                              //~3409I~
    //*****************************************************************************//~3410I~
    //*PSN [FEN  "boardpiece traypiece s|g(sente/gote) moveNumber" //~3410R~
    //*****************************************************************************//~3410I~
	private boolean gameinit(String Phdr,int Ppos)                 //~3410R~
	{                                                              //~3410I~
    	boolean rc=true;                                           //~3410I~
        int len,pos;                                               //~3410I~
        char ch;                                                   //~3410I~
    //****************************                                 //~3410I~
    	pos=Ppos;                                                  //~3410I~
    //*all SFEN string                                             //~3410I~
        len=getWordPos(Phdr,pos);                                  //~3410I~//~3411R~
        if (posWord<0)                                             //~3410I~//~3411R~
            return rc;                                             //~3410I~//~3411R~
        pos=posWord;                                               //~3410I~
    //*initial board piece arrange                                 //~3410I~
    	if (posDquote)                                             //~3411M~
        {                                                          //~3411M~
            len=getWordPos(Phdr,pos);                                  //~3410R~//~3411R~
            if (posWord<0)                                             //~3410R~//~3411R~
                return rc;                                             //~3410I~//~3411R~
            pos=posWord;                                               //~3410I~//~3411R~
        }                                                          //~3411M~
        getBoardPiece(Phdr,pos,len);                              //~3410I~
        pos+=len;                                                  //~3410I~
    //*tray piece                                                  //~3410I~
    	len=getWordPos(Phdr,pos);                                 //~3410I~
        if (posWord<0)                                             //~3410R~
        	return rc;                                             //~3410I~
        pos=posWord;                                               //~3410I~
        getTrayPiece(Phdr,pos,len);                               //~3410I~
        pos+=len;                                                  //~3410I~
    //*sente/gote                                                  //~3410I~
    	len=getWordPos(Phdr,pos);                                 //~3410I~
        if (posWord<0)                                             //~3410R~
        	return rc;                                             //~3410I~
        pos=posWord;                                               //~3410I~
        ch=Phdr.charAt(pos);                                       //~3410I~
        if (ch==PSN_SENTE)   //"s"                                 //~3410R~
        	move1st=1;                                             //~3410I~
        else                                                       //~3410I~
        if (ch==PSN_GOTE)    //"g"                                 //~3410I~
        	move1st=-1;                                            //~3410I~
        else                                                       //~3410I~
        	move1st=1;                                             //~3410I~
        if (Dump.Y) Dump.println("gameinit ch="+ch+",move1st="+move1st);//~3410I~
        pos+=len;                                                  //~3410I~
    //*ignore move number                                          //~3410I~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3410I~
    //*[SFEN  "boardpiece traypiece b|w(sente/gote) moveNumber"    //~3410I~
    //*****************************************************************************//~3410I~
	private boolean gameinit2(String Pline,int Ppos)               //~3410I~
	{                                                              //~3410I~
    	boolean rc=true;                                           //~3410I~
        int len,pos;                                               //~3410I~
        char ch;                                                   //~3410I~
    //****************************                                 //~3410I~
    	pos=Ppos;                                                  //~3410I~
    //*all SFEN string                                             //~3410I~
        len=getWordPos(Pline,pos);                                 //~3410I~//~3411R~
        if (posWord<0)                                             //~3410I~//~3411R~
            return rc;                                             //~3410I~//~3411R~
        pos=posWord;                                               //~3410I~//~3411R~
    //*initial board piece arrange                                 //~3410M~
    	if (posDquote)                                             //~3411I~
        {                                                          //~3411I~
    		len=getWordPos(Pline,pos);                                 //~3410I~//~3411R~
        	if (posWord<0)                                             //~3410I~//~3411R~
        		return rc;                                             //~3410I~//~3411R~
        }                                                          //~3411I~
        getBoardPiece(Pline,pos,len);                              //~3410I~
        pos+=len;                                                  //~3410I~
    //*sente/gote                                                  //~3410I~
    	len=getWordPos(Pline,pos);                                 //~3410I~
        if (posWord<0)                                             //~3410I~
        	return rc;                                             //~3410I~
        pos=posWord;                                               //~3410I~
        ch=Pline.charAt(pos);                                       //~3410I~
        if (ch==PSN_SENTE2)     //"b"                              //~3410R~
        	move1st=1;                                             //~3410I~
        else                                                       //~3410I~
        if (ch==PSN_GOTE2)     //"w"                               //~3410I~
        	move1st=-1;         //"w"                              //~3410R~
        else                                                       //~3410I~
        	move1st=1;                                             //~3410I~
        if (Dump.Y) Dump.println("gameinit2 ch="+ch+",move1st="+move1st);//~3410I~
        pos+=len;                                                  //~3410I~
    //*tray piece                                                  //~3410I~
    	len=getWordPos(Pline,pos);                                 //~3410M~
        if (posWord<0)                                             //~3410I~
        	return rc;                                             //~3410M~
        pos=posWord;                                               //~3410M~
        getTrayPiece2(Pline,pos,len);                              //~3410I~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3410I~
    //* n[+]P/.. for each row                                      //~3410I~
    //* n:space ctr,+:promoted,P pieceid PLNSGBRK:black,plnsgbrk:white//~3410I~
    //*****************************************************************************//~3410I~
	private boolean getBoardPiece(String Pline,int Ppos,int Plen)  //~3410I~
	{                                                              //~3410I~
    	boolean rc=true;                                           //~3410I~
        int pos,row=0,pos1,pose,len;
    //****************************                                 //~3410I~
    	for (pos=Ppos,pose=pos+Plen,pos1=pos;pos<=pose;pos++)      //~3410R~
        {                                                          //~3410I~
        	if (pos==pose||Pline.charAt(pos)=='/')                 //~3410R~
            {                                                      //~3410I~
            	len=pos-pos1;                                      //~3410I~
                getBoardPiece1(row,Pline,pos1,len);                //~3410R~
            	row++;                                             //~3410I~
                if (row==S)                                        //~3410I~
                	break;                                         //~3410I~
                pos1=pos+1;                                        //~3410I~
            }                                                      //~3410I~
        }                                                          //~3410I~
        if (handicap==0)                                           //~3416I~
	        pieceByPiece=true; //accept handicap at first          //~3416I~
		if (Dump.Y) showBoard();                                   //~3407M~//~3409M~//~3411M~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3410I~
	private boolean getBoardPiece1(int Prow,String Pline,int Ppos,int Plen)//~3410R~
	{                                                              //~3410I~
    	boolean rc=true,promoted=false;                            //~3410I~
        int pos,pose,col=0,piece,color;
        char ch;//~3410I~
    //****************************                                 //~3410I~
    	if (Dump.Y) Dump.println("getBoardPiece1 row="+Prow);      //~3410I~
    	for (pos=Ppos,pose=pos+Plen;pos<pose;pos++)                //~3410I~
        {                                                          //~3410I~
        	ch=Pline.charAt(pos);                                  //~3410I~
        	if (ch>='1' && ch<='9')	//empty space count            //~3410R~
            {                                                      //~3410I~
            	col+=ch-'0';                                       //~3411R~
                if (col>=S)                                        //~3410I~//~3411R~
                	break;                                         //~3410I~
                continue;                                          //~3410I~
            }                                                      //~3410I~
        	if (ch==GAM_PROMOTE)                                   //~3410I~
            {                                                      //~3410I~
            	promoted=true;                                     //~3410I~
                continue;                                          //~3410I~
            }                                                      //~3410I~
        	if (ch>='a' && ch<='z')                                //~3410I~
            {                                                      //~3410I~
            	ch=(char)('A'+(ch-'a'));                                   //~3410I~
                color=-1;                                          //~3410I~
            }                                                      //~3410I~
            else                                                   //~3410I~
                color=1;                                           //~3410I~
            piece=getPieceEnglish(ch);                             //~3410I~
            if (piece==0)                                          //~3410I~
            	break;                                             //~3410I~
//            if (piece<=PIECE_ROOK)                                 //~3410I~//~3412R~
//                remainingCtr[piece-1]++;    //for all white        //~3410R~//~3412R~
            if (promoted)                                          //~3410I~
            {                                                      //~3410I~
            	piece=Field.promoted(piece);                       //~3410I~
                promoted=false;                                    //~3410I~
            }                                                      //~3410I~
//          fmtPos.setPiece(col,Prow,color,piece);                 //~3410I~//~3412R~
            putInitialPiece1(col,Prow,color,piece);                //~3412I~
            col++;                                                  //~3410I~
            if (col==S)                                            //~3410I~
            	break;                                             //~3410I~
        }                                                          //~3410I~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3410I~
    //*ex 0012001/T (tray piece cpunt for PLNSGTBR, /T:remaining is to white//~3410I~
    //*****************************************************************************//~3410I~
	private boolean getTrayPiece(String Pline,int Ppos,int Plen)   //~3410I~
	{                                                              //~3410I~
    	boolean rc=true,allwhite=false;                            //~3410I~
        int pos,pose,piecetype=0,color=1;                         //~3411R~//~3412R~
        char ch;//~3410I~
    //****************************                                 //~3410I~
    	for (pos=Ppos,pose=pos+Plen;pos<pose;pos++)//~3410I~       //~3411R~
        {                                                          //~3410I~
        	ch=Pline.charAt(pos);                                  //~3410I~
        	if (ch>='0' && ch<='9' && piecetype<MAX_PIECE_TYPE_CAPTURE)                                //~3410I~//~3411R~
            {                                                      //~3411I~
            	int ctr=ch-'0';                      //~3410I~     //~3411R~
//                fmtTray[colidx][MAX_PIECE_TYPE_CAPTURE-piecetype-1]=ctr;//~3411R~//~3412R~
//                if (colidx==1)  //black                            //~3411I~//~3412R~
//                    remainingCtr[piecetype]+=ctr;                  //~3411I~//~3412R~
                piecetype++;                                        //~3411I~
				setToTrayInitial(color,piecetype,ctr,false/*set*/); //~3412I~
            }                                                      //~3411I~
            else                                                   //~3410I~
            if (ch=='/')                                           //~3411R~
            {                                                      //~3410I~
            	color=-1;	//white                                //~3411I~//~3412R~
                piecetype=0;                                       //~3411I~
            	continue;                                          //~3411R~
            }                                                      //~3410I~
            if (color==-1 && (ch=='T'||ch=='t'))                              //~3411I~//~3412R~
            {                                                      //~3411I~
            	allwhite=true;                                     //~3411I~
            	break;                                             //~3411I~
            }                                                      //~3411I~
        }                                                          //~3410I~
        if (allwhite)                                              //~3410I~
        {                                                          //~3410I~
			setToTrayAllRemaining(-1/*white*/);                     //~3412I~
        }                                                          //~3410I~
        if (Dump.Y) showTray();                                    //~3411I~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3410I~
    //*[n]P.. ex S2Pb3p                                            //~3410I~
    //*****************************************************************************//~3410I~
	private boolean getTrayPiece2(String Pline,int Ppos,int Plen)   //~3410I~
	{                                                              //~3410I~
    	boolean rc=true;
    	char ch;//~3410I~
        int pos,pose,ctr=1,piece,color;                 //~3410I~ //~3411R~//~3412R~
    //****************************                                 //~3410I~
        if (Dump.Y) Dump.println("tray piece2 pos="+Ppos);         //~3410I~
        if (Pline.charAt(Ppos)==PSN_NOTRAY)                     //~3410I~
        	return rc;                                             //~3410I~
    	for (pos=Ppos,pose=pos+Plen;pos<pose;pos++)                //~3410I~
        {                                                          //~3410I~
        	ch=Pline.charAt(pos);                                  //~3410I~
        	if (ch>='0' && ch<='9')                                //~3410I~
            {                                                      //~3410I~
            	ctr=ch-'0';                                       //~3410I~
                if (pos+1<pose)                                    //~3410I~
                {                                                  //~3410I~
                	ch=Pline.charAt(pos+1);                       //~3410I~
                    if (ch>='0' && ch<='9')                        //~3410I~
                    {                                              //~3410I~
                    	ctr=ctr*10+ch-'0';                         //~3410I~
                        pos++;                                     //~3410I~
                    }                                              //~3410I~
                }                                                  //~3410I~
                continue;                                          //~3410I~
            }                                                      //~3410I~
        	if (ch>='a' && ch<='z')                                //~3410I~
            {                                                      //~3410I~
            	ch=(char) ('A'+(ch-'a'));                                   //~3410I~
                color=-1;	//white                                //~3410I~//~3412R~
            }                                                      //~3410I~
            else	                                               //~3410I~
                color=1;	//black                                //~3410I~//~3412R~
            piece=getPieceEnglish(ch);                             //~3410I~
            if (piece==0 || piece>=MAX_PIECE_TYPE_CAPTURE)         //~3410I~
            	break;                                             //~3410I~
//            int pieceidx=MAX_PIECE_TYPE_CAPTURE-piece;                     //~1A2dI~//~3411I~//~3412R~
//            if (Dump.Y) Dump.println("tray2 piece="+piece+",ctr="+ctr);//~3411I~//~3412R~
//            fmtTray[colidx][pieceidx]=ctr;                            //~3410I~//~3411R~//~3412R~
			setToTrayInitial(color,piece,ctr,false/*set*/);         //~3412I~
            ctr=1;                                                 //~3411I~
        }                                                          //~3410I~
        if (Dump.Y) showTray();                                    //~3411I~
        return rc;                                                 //~3410I~
    }                                                              //~3410I~
    //*****************************************************************************//~3408I~
    //* [movenumber.]move [(time)]                                 //~3410I~
    //*****************************************************************************//~3410I~
	private boolean getMovePsn(String Pline)//~3408I~              //~3410R~
	{                                                              //~3408I~
    	boolean rc=true;                                           //~3408I~
        char ch;                                               //~3408I~
        int pos,pos2,len,pose;                                              //~3408I~//~3410R~
    //****************************                                 //~3408I~
        if (Dump.Y) Dump.println("getMovePSN line="+Pline);         //~3408I~//~3410R~
        for (pos=0;pos<lineLen;)                              //~3408I~ //~3410R~
        {                                                          //~3408I~
    		len=getWordPos(Pline,pos);                             //~3410I~
        	if (posWord<0)                                         //~3410R~
        		break;                                             //~3410I~
        	pos=posWord;                                           //~3410I~
            pose=pos+len;                                          //~3410I~
        	ch=Pline.charAt(pos);                                  //~3410I~
            if (ch==PSN_TIME)                                      //~3410I~
            {                                                      //~3410I~
				len=getWordPos(Pline,pos,PSN_TIME_CLOSE);          //~3410I~
	        	if (!posStopper)	//no stopper found             //~3410I~
                	break;                                         //~3410I~
                pos+=len;                                          //~3410R~
            	continue;                                          //~3410I~
            }                                                      //~3410I~
            if (ch==PSN_COMMENT)                                   //~3411I~
            {                                                      //~3411I~
				len=getWordPos(Pline,pos,PSN_COMMENT_CLOSE);       //~3411I~
	        	if (!posStopper)	//no stopper found             //~3411I~
                	break;                                         //~3411I~
                pos+=len;                                          //~3411I~
            	continue;                                          //~3411I~
            }                                                      //~3411I~
            if (ch>='0'&&ch<='9')	//move number                  //~3408I~
            {                                                      //~3408I~
                for (pos2=pos+1;pos2<lineLen;pos2++)                           //~3408I~//~3410R~
                {                                                  //~3408I~
		        	ch=Pline.charAt(pos2);                          //~3408I~//~3410R~
		            if (ch>='0'&&ch<='9')	//move number          //~3410I~
                    	continue;                                  //~3410I~
                    break;                                     //~3408I~//~3410R~
                }                                                  //~3408I~
                if (ch==PSN_MOVENUMBER_DELM)                       //~3410I~
                {                                                  //~3410I~
                	len-=pos2+1-pos;                                //~3410I~
                	pos=pos2+1;	//skip move number                 //~3410I~
                    if (len>0)                                     //~3410I~
                    {                                              //~3410I~
			        	ch=Pline.charAt(pos);                      //~3410I~
                        if (ch==PSN_PASS)	//2 dot ...            //~3410I~
                        {                                          //~3410I~
                        	pos=pose;                              //~3410I~
                            continue;                              //~3410I~
                        }                                          //~3410I~
                    }                                              //~3410I~
                }                                                  //~3410I~
            }                                                      //~3408I~
            if (len==0)                                            //~3411I~
            	continue;                                          //~3411I~
            if (!getMovePsn1(Pline,pos,len))      //~3408I~        //~3410R~
				break;                                             //~3410I~
            pos+=len;                                              //~3410R~
        }                                                          //~3408I~
        return rc;                                                 //~3408I~
	}                                                              //~3408I~
    //*****************************************************************************//~3408I~
    //* USI:[+]ijnm[+] / P*ij                                      //~3410I~
    //* ShogiDokoro: [+]Pij{-/x}nm[] / P*ij                        //~3410I~
    //* PSI: [+]P[ij]nm{+|=} / P*ij                                //~3410I~
    //*****************************************************************************//~3410I~
	private boolean getMovePsn1(String Pline,int Ppos,int Plen)//~3408I~//~3410R~
	{                                                              //~3408I~
    	boolean rc=true,promote;                                           //~3408I~
        char ch;                                               //~3408I~
        int pos,len,piece,pieceto,ii1,ii2,jj1,jj2,color;                                               //~3408I~
        boolean drop=false;                                        //~3409I~//~3410R~
    //****************************                                 //~3408I~
        if (Dump.Y) Dump.println("getMovePsn1 line="+Pline.substring(Ppos,Ppos+Plen));//~3408I~//~3410R~
        pos=Ppos;                                                  //~3408I~
        len=Plen;                                                  //~3408I~
        if (getGameoverReasonPsn(Pline,pos))                       //~3409I~//~3410R~
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
        piece=getPieceEnglish(ch);                                 //~3410I~
                                                                   //~3410I~
        if (piece==0)                                              //~3410I~
        {                                                          //~3410I~
        //*USI                                                     //~3410I~
            if (len<2)                                             //~3410I~
                return rc;                                         //~3410I~
            if (!getPositionEnglish(Pline,pos))                    //~3410I~
                return rc;                                         //~3410I~
            pos+=2;len-=2;                                         //~3410I~
            ii1=posI; jj1=posJ;                                    //~3410I~
            piece=fmtPos.piece(ii1,jj1);                           //~3410I~
	        if (Dump.Y) Dump.println("getpsn1 USI fmt ii1="+ii1+",jj1="+jj1+",piece="+piece);//~3411I~
            if (piece==0)                                          //~3410I~
                return rc;                                         //~3410I~
            if (promote)                                          //~3410I~
                piece=Field.promoted(piece);                       //~3410I~
            if (len<2)                                             //~3410I~
                return rc;                                         //~3410I~
            if (!getPositionEnglish(Pline,pos))                     //~3410I~//~3411R~
                return rc;
            pos+=2;len-=2;
            ii2=posI; jj2=posJ;//~3410I~
	        if (Dump.Y) Dump.println("getpsn1 USI fmt ii2="+ii2+",jj2="+jj2+",piece="+piece);//~3411I~
        }                                                          //~3410I~
        else                                                       //~3410I~
        {                                                          //~3410I~
            if (promote)                                           //~3410I~
                piece=Field.promoted(piece);                       //~3410I~
            pos++;len--;                                           //~3410I~
            if (len==0)                                            //~3410I~
                return rc;                                         //~3410I~
            ch=Pline.charAt(pos);                                  //~3410I~
            ii1=-1; jj1=-1;                                        //~3410I~
            if (ch==GAM_DROP)                                      //~3410I~
            {                                                      //~3410I~
                drop=true;                                         //~3410I~
                ii1=0; jj1=0;    //From                            //~3410I~
                pos++; len--;                                      //~3410I~
                if (len<2)                                         //~3410I~
                    return rc;                                     //~3410I~
                if (!getPositionEnglish(Pline,pos))                    //~3410I~
                    return rc;                                     //~3410I~
                ii2=posI; jj2=posJ;   //To                         //~3410I~
                pos+=2; len-=2;                                    //~3410I~
            }                                                      //~3410I~
            else                                                   //~3410I~
            {                                                      //~3410I~
                if (len<2)                                         //~3410I~
                    return rc;                                     //~3410I~
                if (!getPositionEnglish(Pline,pos))                    //~3410I~
                    return rc;                                     //~3410I~
 		        if (Dump.Y) Dump.println("getpsn1 len="+len+",ch="+Character.valueOf(ch)+",str="+Pline.substring(pos));//~3411I~
                pos+=2; len-=2;                                    //~3410I~
                ii2=posI; jj2=posJ;                                //~3410I~
                if (len!=0)                                        //~3410I~
                {                                                  //~3410I~
                    ch=Pline.charAt(pos);                          //~3410I~
                    if (ch==GAM_CAPTURE||ch==GAM_MOVETO)           //~3410I~
                    {                                              //~3410I~
                        pos++; len--;                              //~3410I~
                    }                                              //~3410I~
                    if (ch!=GAM_PROMOTE&&ch!=GAM_NONPROMOTE)                           //~3410I~//~3411R~
                    {                                              //~3410I~
                        if (len<2)                                 //~3410R~
                            return rc;                             //~3410R~
                        ii1=posI; jj1=posJ;                        //~3410R~
                        if (!getPositionEnglish(Pline,pos))        //~3410R~
                            return rc;                             //~3410R~
                        ii2=posI; jj2=posJ;                        //~3410R~
                        pos+=2; len-=2;                            //~3410R~
                    }                                              //~3410I~
                }                                                  //~3410I~
            }                                                      //~3410I~
        }                                                          //~3410I~
        pieceto=piece;
        if (len>0)                                                 //~3410I~
        {                                                          //~3410I~
	        ch=Pline.charAt(pos);                                  //~3410I~
            if (ch==GAM_PROMOTE)                                   //~3410I~
            {                                                      //~3410I~
                pieceto=Field.promoted(piece);                     //~3410I~
                pos++; len--;                                      //~3410I~
            }                                                      //~3410I~
            if (ch==GAM_NONPROMOTE)                                 //~3410I~//~3411R~
            {                                                      //~3410I~
                pos++; len--;                                      //~3410I~
            }                                                      //~3410I~
        }                                                          //~3410I~
        moveNumber++;                                              //~3410I~
        color=moveNumber%2==1?1:-1;                                //~3410I~
        if (move1st==-1)                                           //~3410I~
        	color=-color;                                          //~3410I~
        pieceTo=pieceto;                                           //~3410I~
        pieceFrom=piece;                                           //~3410I~
        pieceDrop=drop;                                            //~3410I~//~3411M~
        iTo=ii2; jTo=jj2;                                          //~3410I~
        if (Dump.Y) Dump.println("getpsn1 ii1="+ii1);              //~3411I~
        if (ii1<0)	//from serach required(not drop)                         //~3410I~//~3411R~
        {                                                          //~3410I~
			if (!getFromPositionPsn(piece,color))//set iFrom,jFrom //~3410I~//~3411R~
            	return rc;                                         //~3410I~
        }                                                          //~3410I~
        else                                                       //~3410I~
        {                                                          //~3410I~
			iFrom=ii1; jFrom=jj1;                                  //~3410I~
        }                                                          //~3410I~
        if (Dump.Y) Dump.println("getpsn1 iFrom="+iFrom+",jFrom="+jFrom+",iTo="+iTo+",jTo="+jTo);//~3411I~
        lastColor=color;                                           //~3410I~
        getAction(color);                                          //~3410I~
        return rc;                                                 //~3410I~
	}                                                              //~3410I~
    //*****************************************************************************//~3411I~
	private boolean getFromPositionPsn(int Ppiece,int Pcolor)      //~3411I~
	{                                                              //~3411I~
		boolean rc=getMoveFromSub(0/*modifier*/,Pcolor);           //~3411I~
        if (Dump.Y) Dump.println("getFromPosPsn rc="+rc+",drop="+pieceDrop);//~3411I~
        if (pieceDrop)                                             //~3411I~
        {                                                          //~3411I~
        	iFrom=0; jFrom=0;                                      //~3411I~
        }                                                          //~3411I~
        return rc;                                                 //~3411I~
    }                                                              //~3411I~
    //*****************************************************************************//~3409I~//~3410M~
	private boolean getGameoverReasonPsn(String Pline,int Ppos)    //~3409I~//~3410R~
	{                                                              //~3409I~//~3410M~
    	int id=0,ii;                                               //~3409I~//~3410M~
        boolean rc=false;                                          //~3409I~//~3410M~
        //*******************************                          //~3411I~
        if (Dump.Y) Dump.println("getgameoverReasonPsn str="+Pline.substring(Ppos));//~3411I~
    	if (Pline.startsWith(PSN_WINNER_BLACK,Ppos))               //~3411I~
        {                                                          //~3411I~
        	winner=1;                                              //~3411I~
        	if (Dump.Y) Dump.println("getgameoverReasonGam winner:black");//~3411I~
            return true;                                           //~3411I~
        }                                                          //~3411I~
    	if (Pline.startsWith(PSN_WINNER_WHITE,Ppos))               //~3411I~
        {                                                          //~3411I~
        	winner=-1;                                             //~3411I~
        	if (Dump.Y) Dump.println("getgameoverReasonGam winner:white");//~3411I~
            return true;                                           //~3411I~
        }                                                          //~3411I~
    	if (Pline.startsWith(PSN_WINNER_EVEN,Ppos))                //~3411I~
        {                                                          //~3411I~
        	winner=0;                                              //~3411I~
        	if (Dump.Y) Dump.println("getgameoverReasonGam winner:even");//~3411I~
            return true;                                           //~3411I~
        }                                                          //~3411I~
        for (ii=0;ii<PSN_GAMEOVERNAME.length;ii++)                 //~3409I~//~3410M~
        {                                                          //~3409I~//~3410M~
    		if (Pline.startsWith(PSN_GAMEOVERNAME[ii],Ppos))       //~3409I~//~3410M~
            {                                                      //~3409I~//~3410M~
            	id=PSN_GAMEOVER_ID[ii];                            //~3409I~//~3410M~
                break;                                             //~3409I~//~3410M~
            }                                                      //~3409I~//~3410M~
        }                                                          //~3409I~//~3410M~
        if (id!=0)                                                 //~3409I~//~3410M~
        {                                                          //~3409I~//~3410M~
	        gameoverReasonId=id;                                   //+3416I~
        	if (!result)
        		winner=getWinnerGam(PSN_GAMEOVER_WINNER[ii]);          //~3409R~//~3410M~
            rc=true;                                               //~3409I~//~3410M~
        }                                                          //~3409I~//~3410M~
        if (Dump.Y) Dump.println("getgameoverReasonGam rc="+rc+",idx="+ii+",id="+id);//~3409I~//~3410M~//~3411R~
        return rc;                                                 //~3409I~//~3410M~
    }                                                              //~3409I~//~3410M~
    //*****************************************************************************//~3410M~
    //*set posWord(word top pos or -1) , posStopper if stopper specified, posDquote:enclosed by dquote//~3411R~
    //*return datalen(len in dquote, or up to before space or after stopper)//~3410I~
    //*****************************************************************************//~3410I~
	private int getWordPos(String Pline,int Ppos)                  //~3410M~
	{                                                              //~3410M~
		return getWordPos(Pline,Ppos,(char)0);                     //~3410M~
    }                                                              //~3410M~
	private int getWordPos(String Pline,int Ppos,char Pstopper)    //~3410M~
	{                                                              //~3410M~
    	int pos,pos1,rc=0;                                         //~3410M~
        char ch=0,stopper;                                         //~3410M~
    //****************************                                 //~3410M~
    	int len=Pline.length();                                    //~3410M~
        stopper=Pstopper==0?' ':Pstopper;                          //~3410M~
        posWord=-1;                                                //~3410M~
        posStopper=false;                                          //~3410M~
        posDquote=false;                                           //~3411I~
    	for (pos=Ppos;pos<len;pos++)                               //~3410M~
        {                                                          //~3410M~
        	ch=Pline.charAt(pos);                                  //~3410M~
        	if (ch!=' ')                                           //~3410M~
            	break;                                             //~3410M~
        }                                                          //~3410M~
        if (pos<len)                                               //~3410M~
        {                                                          //~3410M~
            if (ch=='\"' && Pstopper==0)                           //~3410M~
            {                                                      //~3410M~
            	posDquote=true;                                    //~3411I~
                pos++;                                             //~3410M~
                for (pos1=pos;pos<len;pos++)                       //~3410M~
                {                                                  //~3410M~
                    ch=Pline.charAt(pos);                          //~3410M~
                    if (ch=='\"')                                  //~3410M~
                        break;                                     //~3410M~
                }                                                  //~3410M~
            }                                                      //~3410M~
            else                                                   //~3410M~
            {                                                      //~3410M~
                for (pos1=pos;pos<len;pos++)                       //~3410M~
                {                                                  //~3410M~
                    ch=Pline.charAt(pos);                          //~3410M~
                    if (ch==stopper)                               //~3410M~
                    {                                              //~3410M~
                    	if (Pstopper!=0)                           //~3411I~
                        {                                          //~3411I~
                    		posStopper=true;                           //~3410M~//~3411R~
                        	pos++;	//len include stopper              //~3410I~//~3411R~
                        }                                          //~3411I~
                        break;                                     //~3410M~
                    }                                              //~3410M~
                }                                                  //~3410M~
            }                                                      //~3410M~
            rc=pos-pos1;                                           //~3410M~
            posWord=pos1;                                          //~3410M~
	        if (Dump.Y) Dump.println("getWord len="+rc+",pos="+posWord+",str="+Pline.substring(posWord,posWord+rc));//~3410I~
        }                                                          //~3410M~
    	return rc;                                                 //~3410M~
    }                                                              //~3410M~
    //*****************************************************************************//~3410M~
	private String getWord(String Pline,int Ppos)                  //~3410M~
    {                                                              //~3410M~
		return getWord(Pline,Ppos,(char)0);                        //~3410M~
    }                                                              //~3410M~
	private String getWord(String Pline,int Ppos,char Pstopper)    //~3410M~
	{                                                              //~3410M~
    	String str;                                                //~3410M~
    //****************************                                 //~3410M~
    	int len=getWordPos(Pline,Ppos,Pstopper);                   //~3410M~
        if (posWord<0||(Pstopper!=0 && !posStopper))                  //~3410M~
        	str="";                                                //~3410M~
        else                                                       //~3410M~
        	str=Pline.substring(posWord,posWord+len);              //~3410M~
        if (Dump.Y) Dump.println("getWord rc="+str);               //~3410M~
    	return str;                                                //~3410M~
    }                                                              //~3410M~
}//NotesFmt                                                        //~3407R~
