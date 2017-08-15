//*CID://+1A4BR~:                             update#=  238;       //+1A4BR~
//***********************************************************************
//1A4B 2014/12/09 tsumeshogi mochigoma notaition may have not prefix sente/gote but simply mochigoma//+1A4BI~
//1A4u 2014/12/06 kif format variation; uwate/simote mochigoma +=gote/sente mochigoma//~1A4uI~
//***********************************************************************
package jagoclient.board;

import static jagoclient.board.Field.*;
import static jagoclient.board.Rules.*;
import jagoclient.Dump;

public class NotesFmtKif extends NotesFmt                                              //~v1A0R~//~3406R~//~3409R~
{
	private final static String BLACK_PREFIX="先手：";             //~3406I~//~3414R~
	private final static String WHITE_PREFIX="後手：";             //~3406I~//~3414R~
	private final static String BLACK_PREFIX2="下手：";            //~3406I~//~3414R~
	private final static String WHITE_PREFIX2="上手：";            //~3406I~//~3414R~
	private final static String WINNER_BLACK="先手";               //~3407I~//~3414R~
	private final static String WINNER_WHITE="後手";               //~3407I~//~3414R~
	private final static String WINNER_BLACK2="下手";              //~3407R~//~3414R~
	private final static String WINNER_WHITE2="上手";              //~3407R~//~3414R~
	private final static String HANDICAP_PREFIX="手合割：";        //~3407R~//~3414R~
	private final static String TRAY_WHITE_PREFIX="上手の持駒：";//~3412I~//~3414R~
	private final static String TRAY_WHITE_PREFIX2="後手の持駒："; //~1A4uI~
	private final static String TRAY_BLACK_PREFIX="下手の持駒：";//~3412I~//~3414R~
	private final static String TRAY_BLACK_PREFIX2="先手の持駒："; //~1A4uI~
	private final static String TRAY_BLACK_PREFIX3="持駒：";       //+1A4BI~
	private final static String TRAY_NONE="なし";                  //~3412I~//~3414R~
	private final static char COMMENT_PREFIX='*';//~3406I~
	private final static String VARIATION_PREFIX="変化";             //~3407I~//~3414R~
	private final static String[] HANDICAPNAM={                     //~3406I~//~3414R~
    		"平","香","右香","角","飛車","飛香","二","三","四","五","左五","六","八","十"};    //~3406I~//~3407R~//~3414R~
	private final static String ScoordV="一二三四五六七八九";      //~3406I~//~3407R~//~3414R~
	private final static char KANJI_10='十';                       //~3412I~//~3414R~
	private final static char BOARD_LAYOUT='|';                    //~3412I~
    private final static String[] SpieceName={                     //~3406I~
   			"歩","香","桂","銀","金","角","飛","王","玉",                            //~3406I~//~3414R~
  			"と","成香","成桂","成銀","馬","龍","竜"};                           //~3406I~//~3414R~//~3416R~
    private final static int[] SpieceId={                          //~3406I~
    			PIECE_PAWN,                                        //~3406I~
    			PIECE_LANCE,                                       //~3406I~
    			PIECE_KNIGHT,                                      //~3406I~
    			PIECE_SILVER,                                      //~3406I~
    			PIECE_GOLD,                                        //~3406I~
    			PIECE_BISHOP,                                      //~3406I~
    			PIECE_ROOK,                                        //~3406I~
    			PIECE_KING,                                        //~3406I~
//  			PIECE_KING_CHALLENGING,                            //~3406I~//~3411R~
    			PIECE_KING,                                        //~3411I~
    			PIECE_PPAWN,                                       //~3406I~
    			PIECE_PLANCE,                                      //~3406I~
    			PIECE_PKNIGHT,                                     //~3406I~
    			PIECE_PSILVER,                                     //~3406I~
    			PIECE_PBISHOP,                                     //~3406I~
    			PIECE_PROOK,     //2 char for Rook                 //~3416I~
    			PIECE_PROOK};                                      //~3406I~
    private static final char MOVE_PROMOTE='成';                     //~3406I~//~3414R~
    private static final char MOVE_SAME='同';                        //~3406I~//~3414R~
    private static final char MOVE_DROP='打';                     //~3414R~
    private static final String MOVE_NOTPROMOTE="不成";            //~3408I~//~3414R~
    private static final char MOVE_UP='上';                        //~3408I~//~3414R~
    private static final char MOVE_DOWN='引';                      //~3408I~//~3414R~
    private static final char MOVE_LEFT='左';                      //~3408I~//~3414R~
    private static final char MOVE_RIGHT='右';                     //~3408I~//~3414R~
    private static final char MOVE_HORIZONTAL='寄';                //~3408I~//~3414R~
    private static final char MOVE_VERTICAL='直';                  //~3408I~//~3414R~
    private static final char MOVE_FROM_PREFIX='(';
    private static final String START_KIF="1";
    private static final String START_KI2_BLACK="▲";              //~3408R~//~3414R~
    private static final String START_KI2_WHITE="△";              //~3408I~//~3414R~
    private static final String START_KI2_WHITE2="▽";             //~3408I~//~3414R~
    public  static final char BLACK_SIGN='▲';                   //~3407I~//~3411R~//~3414R~
    private static final char WHITE_SIGN='△';                   //~3407I~//~3414R~
    private static final char WHITE_SIGN2='▽';                  //~3407I~//~3414R~
    private static final String GAMEOVER_PREFIX="まで";            //~3407I~//~3414R~
    private static final String GAMEOVER_WIN="勝ち";               //~3407R~//~3414R~
    private static final String GAMEOVER_LOSE="負け";              //~3407I~//~3414R~
    private static final String GAMEOVER_RESIGN="投了";            //~3407I~//~3414R~
//    private static final String GAMEOVER_TIMEOUT_MOVE="切れ負け";  //~3407R~//~3414R~
    private static final String GAMEOVER_TIMEOUT="時間切れ";       //~3407I~//~3414R~
    private static final String GAMEOVER_FOUL="反則";              //~3407I~//~3414R~
    private static final String GAMEOVER_CHECKMATE="詰み";         //~3407I~//~3414R~
    private static final String GAMEOVER_NOCHECKMATE="不詰";       //~3407I~//~3414R~
    private static final String GAMEOVER_LOOP="千日手";            //~3407I~//~3414R~
    private static final String GAMEOVER_INFINITE="持将棋";        //~3407I~//~3414R~
    private static final String GAMEOVER_SUSPEND="中断";           //~3407I~//~3414R~
    private static final char DBCS_SPACE='　';//~3406I~           //~3414R~
    private static final char BOARD_LAYOUT_WHITE='v';              //~3414R~
    private static final String[] KIF_GAMEOVERNAME={               //~3416I~
			GAMEOVER_RESIGN,GAMEOVER_SUSPEND,GAMEOVER_LOOP,GAMEOVER_TIMEOUT,//~3416I~
			GAMEOVER_FOUL,"-0d",GAMEOVER_INFINITE,"KACHI",         //~3416I~
			"HIKIWAKE",GAMEOVER_CHECKMATE,GAMEOVER_NOCHECKMATE,"ERROR"};//~3416I~
    private static final int[] KIF_GAMEOVER_ID={                   //~3416I~
    		GAMEOVERID_RESIGN,         //                          //~3416I~
    		GAMEOVERID_SUSPEND,        //                          //~3416I~
    		GAMEOVERID_LOOP,           //                          //~3416I~
    		GAMEOVERID_TIMEOUT,        //                          //~3416I~
    		GAMEOVERID_FOUL,           //                          //~3416I~
    		GAMEOVERID_FOUL,                                       //~3416I~
    		GAMEOVERID_INFINITE,       //                          //~3416I~
    		GAMEOVERID_WIN,                                        //~3416I~
    		GAMEOVERID_EVEN,                                       //~3416I~
    		GAMEOVERID_CHECKMATE,      //                          //~3416I~
    		GAMEOVERID_NOCHECKMATE,    //                          //~3416I~
    		GAMEOVERID_ERROR,                                      //~3416I~
			};                                                     //~3416I~
                                                                   //~3407I~
    private boolean swKif;                                  //~3406I~//~3408M~
    private boolean swKi2;                                  //~3406I~//~3408M~
    private int posFrom,posPieceEnd;//~3407R~//~3408M~                 //~3409R~
    //********************************************************************************//~3409R~
    @Override                                                      //~3409I~
	protected boolean setTreeEntry(String Pline)//~3406R~//~3408R~//~3409R~
	{                                                              //~v1A0I~
        if (Dump.Y) Dump.println("setTreeEntry line="+Pline);       //~3406I~
    	boolean rc=true;                                           //~3406I~
    	if (Pline.startsWith(HANDICAP_PREFIX))                        //~3406I~
        	setHandicap(Pline);                                    //~3406I~//~3409R~
        else                                                       //~3406I~
    	if (Pline.startsWith(BLACK_PREFIX))                           //~3406I~
        	setBlack(Pline);                                       //~3406I~//~3409R~
        else                                                       //~3406I~
    	if (Pline.startsWith(BLACK_PREFIX2))                          //~3406I~
        	setBlack(Pline);                                       //~3406I~//~3409R~
        else                                                       //~3406I~
    	if (Pline.startsWith(WHITE_PREFIX))                           //~3406I~
        	setWhite(Pline);                                       //~3406I~//~3409R~
        else                                                       //~3406I~
    	if (Pline.startsWith(WHITE_PREFIX2))                          //~3406I~
        	setWhite(Pline);                                       //~3406I~//~3409R~
        else                                                       //~3406I~
    	if (Pline.startsWith(TRAY_BLACK_PREFIX))                   //~3412I~
        {                                                          //~3414I~
        	setTray(Pline,TRAY_WHITE_PREFIX.length(),1);           //~3412I~
			if (Dump.Y) showBoard();                                   //~3407M~//~3409M~//~3414M~
        }                                                          //~3414I~
        else                                                       //~1A4uI~
    	if (Pline.startsWith(TRAY_BLACK_PREFIX2))                  //~1A4uI~
        {                                                          //~1A4uI~
        	setTray(Pline,TRAY_WHITE_PREFIX2.length(),1);          //~1A4uI~
			if (Dump.Y) showBoard();                               //~1A4uI~
        }                                                          //~1A4uI~
        else                                                       //~3412I~
    	if (Pline.startsWith(TRAY_WHITE_PREFIX))                   //~3412I~
        {                                                          //~3414I~
        	setTray(Pline,TRAY_WHITE_PREFIX.length(),-1);          //~3412I~
        }                                                          //~3414I~
        else                                                       //~1A4uI~
    	if (Pline.startsWith(TRAY_WHITE_PREFIX2))                  //~1A4uI~
        {                                                          //~1A4uI~
        	setTray(Pline,TRAY_WHITE_PREFIX2.length(),-1);         //~1A4uI~
        }                                                          //~1A4uI~
        else                                                       //~3412I~
    	if (Pline.startsWith(TRAY_BLACK_PREFIX3))                  //+1A4BI~
        {                                                          //+1A4BI~
        	setTray(Pline,TRAY_BLACK_PREFIX3.length(),1/*black*/); //+1A4BI~
        }                                                          //+1A4BI~
        else                                                       //+1A4BI~
    	if (Pline.charAt(0)==BOARD_LAYOUT)                         //~3412I~
        	initialBoardLayout(Pline);                             //~3412I~
        else                                                       //~3412I~
        if (Pline.startsWith(START_KIF))                           //~3406I~
        {                                                          //~3406I~
        	swKif=true;                                            //~3406I~
            if (!swInitPiece)                                      //~3408I~
            	putInitialPiece(0/*handicap*/);                    //~3408I~
        	setKifAction(Pline);                             //~3406I~//~3409R~
        }                                                          //~3406I~
        else                                                       //~3406I~
        if (Pline.startsWith(START_KI2_BLACK)                  //~3406I~//~3408R~
        ||  Pline.startsWith(START_KI2_WHITE)                      //~3408I~
        ||  Pline.startsWith(START_KI2_WHITE2)                     //~3408I~
        )                                                          //~3408I~
        {                                                          //~3406I~
        	swKi2=true;                                            //~3406I~
            if (!swInitPiece)                                      //~3408I~
            	putInitialPiece(0/*handicap*/);                    //~3408I~
        	setKi2Action(Pline);                             //~3406I~//~3409R~
        }                                                          //~3406I~
        else                                                       //~3406I~
        if (swKif)                                                 //~3406I~
        {                                                          //~3406I~
        	rc=setKifAction(Pline);                          //~3406I~//~3409R~
        }                                                          //~3406I~
        else                                                       //~3406I~
        if (swKi2)                                                 //~3406I~
        {                                                          //~3406I~
        	rc=setKi2Action(Pline);                          //~3406I~//~3409R~
        }                                                          //~3406I~
        if (!swKif && !swKi2)                                      //~1A4uI~
        	appendHeaderComment(Pline);                            //~1A4uI~
        return rc;                                                 //~3406I~
	}                                                              //~v1A0I~
    //****************************************                     //~1A2cI~
	private void setHandicap(String Pline)                           //~1A2cI~//~3406R~//~3408R~//~3409R~
	{                                                              //~1A2cI~
    	int handicap=0;                                            //~3406I~
        String hc=Pline.substring(HANDICAP_PREFIX.length());           //~3406I~
    	for (int ii=0;ii<HANDICAPNAM.length;ii++)                //~3406I~//~3414R~
            if (hc.startsWith(HANDICAPNAM[ii]))                     //~3406I~//~3414R~
            {                                                      //~3406I~
                handicap=HANDICAPTB[ii];                            //~3406I~
                break;                                             //~3406I~
            }                                                      //~3406I~
        if (Dump.Y) Dump.println("Handicap="+Integer.toHexString(handicap));                                                           //~3406I~//~3407R~
        this.handicap=handicap;                                  //~3406I~//~3409R~//~3412R~
        if (handicap!=0)                                          //~3406I~//~3414R~
        {                                                          //~3414I~
        	move1st=-1;	//white                                    //~3406I~
			putInitialPiece(handicap);	//initialy put pieaces//~3406I~//~3414R~
        }                                                          //~3414I~
 	}                                                              //~1A2cI~
    //****************************************                     //~1A2cI~
	private void setBlack(String Pline)                       //~3406R~//~3408R~//~3409R~
	{                                                              //~1A2cI~
    	blackName=Pline.substring(BLACK_PREFIX.length());              //~3406I~
        if (Dump.Y) Dump.println("blackName="+blackName);          //~3407I~
 	}                                                              //~1A2cI~
    //****************************************                     //~3406I~
	private void setWhite(String Pline)                       //~3406I~//~3408R~//~3409R~
	{                                                              //~3406I~
    	whiteName=Pline.substring(WHITE_PREFIX.length());              //~3406I~
        if (Dump.Y) Dump.println("whiteName="+whiteName);          //~3407I~
 	}                                                              //~3406I~
    //******************************************************************//~3412I~
    //sample                                                       //~3412I~
	//| ・ ・v桂 ・v玉 ・ ・ ・ ・|一                              //~3412I~//~3414R~
	//| ・ 金 ・ ・ ・v銀 ・ ・ ・|二                              //~3412I~//~3414R~
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
		row=chkKanSuji(ch);                                        //~3412I~
        if (row<1||row>S)                                          //~3412I~
        	return rc;                                             //~3412I~
        row--;                                                     //~3412I~
    	for (pos=1,col=0;pos<len;pos++)                        //~3412I~
        {                                                          //~3412I~
	        ch=Pline.charAt(pos);                                  //~3412I~
            if (ch==' ')                                           //~3412I~
            {                                                      //~3412I~
            	color=1;	//black                                //~3412I~
                col++;                                             //~3412I~
            	continue;                                          //~3412I~
            }                                                      //~3412I~
            if (ch==BOARD_LAYOUT_WHITE)  //"v"                          //~3412I~
            {                                                      //~3412I~
            	color=-1;	//white                                //~3412I~
                col++;                                             //~3412I~
            	continue;                                          //~3412I~
            }                                                      //~3412I~
			piece=chkPiece(Pline,pos);                             //~3412I~
            if (piece==0)                                          //~3412I~
            	continue;                                          //~3412I~
			putInitialPiece1(col-1,row,color,piece);                 //~3412I~//~3414R~
        }
        pieceByPiece=true;                                         //~3416R~
    	return rc;//~3412I~
 	}                                                              //~3412I~
    //****************************************                     //~3412I~
	private void setTray(String Pline,int Ppos,int Pcolor)         //~3412I~
	{                                                              //~3412I~
    	int piece,ctr;                                     //~3412I~//~3414R~
    //*********************************                            //~3412I~
        if (Dump.Y) Dump.println("setTray line="+Pline+",color="+Pcolor);//~3414I~
    	int len=Pline.length();                                    //~3412I~
        if (len<=Ppos)                                             //~3412I~
        	return;                                                //~3412I~
        if (Pline.startsWith(TRAY_NONE,Ppos))                       //~3412I~
        	return;                                                //~3412I~
    	for (int pos=Ppos;pos<len;pos++)                           //~3412I~//~3414R~
        {                                                          //~3412I~
			piece=chkPiece(Pline,pos);                                 //~3412I~//~3414R~
            if (piece==0 || piece>MAX_PIECE_TYPE_CAPTURE)                  //~3412I~//~3414R~
            	continue;                                          //~3412I~
            pos++;                                                 //~3414R~
            ctr=getKanjiCtr(Pline,pos,len);                        //~3412I~
            if (ctr<0)                                             //~3412I~
            	ctr=1;                                             //~3412I~
			setToTrayInitial(Pcolor,piece,ctr,false/*set*/);       //~3412I~
            if (ctr>10)                                            //~3412I~
            	pos++;                                             //~3414R~
        }                                                          //~3412I~
        if (Dump.Y) Dump.println("whiteName="+whiteName);          //~3412I~
 	}                                                              //~3412I~
    //****************************************                     //~3406I~
    //*rc:0<-->8 ,-1 if err                                        //~3412I~
    //****************************************                     //~3412I~
	private int chkKanSuji(char Pch)                                //~3406I~//~3408R~
	{                                                              //~3406I~
    	int rc=-1;                                                 //~3407I~
    	for (int ii=0;ii<ScoordV.length();ii++)                    //~3406I~
        {                                                          //~3407I~
// 	    	if (Dump.Y) Dump.println("chkKansuji "+Integer.toHexString(ScoordV.charAt(ii)));//~3407R~
        	if (Pch==ScoordV.charAt(ii))                          //~3406I~
            {                                                      //~3407I~
            	rc=ii+1;                                             //~3407I~//~3414R~
            	break;                                             //~3407I~
            }                                                      //~3407I~
        }                                                          //~3407I~
        if (Dump.Y) Dump.println("chkKansuji="+Pch+"="+"="+Integer.toHexString(Pch)+"="+rc);//~3407R~
    	return rc;                                                 //~3406I~//~3407I~
 	}                                                              //~3406I~
    //****************************************                     //~3412I~
	private int chkKanSuji10(char Pch)                             //~3412I~
	{                                                              //~3412I~
    	int rc=-1;                                                 //~3412I~
    	if (Pch==KANJI_10)                                         //~3412I~
        	rc=10;                                                 //~3412I~
        else                                                       //~3412I~
        	rc=chkKanSuji(Pch);                                    //~3412I~
        if (Dump.Y) Dump.println("chkKansuji10 rc="+rc+",ch="+Character.valueOf(Pch));//~3412I~
    	return rc;                                                 //~3412I~
 	}                                                              //~3412I~
    //****************************************                     //~3412I~
	private int getKanjiCtr(String Pline,int Ppos,int Plen)        //~3412I~
	{                                                              //~3412I~
    	int rc=-1,pos;                                             //~3412I~
    //************************                                     //~3412I~
        if (Dump.Y) Dump.println("chkKanjictr pos="+Ppos+",len="+Plen+",line="+Pline);//~3414I~
    	if (Ppos>=Plen)                                            //~3412I~
        	return rc;                                             //~3412I~
        pos=Ppos;                                                  //~3412I~
        char ch=Pline.charAt(pos);                                 //~3412I~
		rc=chkKanSuji10(ch);                                       //~3412I~
        if (rc<0)                                                  //~3412I~
        	return rc;                                             //~3412I~
    	if (rc==10)                                                //~3412I~
        {                                                          //~3412I~
        	pos++;                                                 //~3412I~
            if (pos>=Plen)                                         //~3412I~
	        	return rc;                                         //~3412I~
        	ch=Pline.charAt(pos);                                  //~3412I~
			int rc2=chkKanSuji10(ch);                              //~3412I~
            if (rc2<0)                                             //~3412I~
            	return rc;                                         //~3412I~
            rc+=rc2;                                          //~3412I~//~3414R~
        }                                                          //~3412I~
        if (Dump.Y) Dump.println("chkKanjictr rc="+rc+"line="+Pline.substring(Ppos,Plen));//~3412I~//~3414R~
    	return rc;                                                 //~3412I~
 	}                                                              //~3412I~
    //****************************************                     //~3406I~
	private int chkPiece(String Pline,int Ppos)                     //~3406I~//~3408R~
	{                                                              //~3406I~
    	int rc=0;                                                  //~3407I~
    	for (int ii=0;ii<SpieceName.length;ii++)                 //~3406I~
        	if (Pline.startsWith(SpieceName[ii],Ppos))              //~3406I~
            {                                                      //~3407I~
                rc=SpieceId[ii];                                      //~3406I~//~3407R~
                posPieceEnd=SpieceName[ii].length()+Ppos;          //~3408I~
            	break;                                             //~3407I~
            }                                                      //~3407I~
        if (Dump.Y) Dump.println("chkPiece line="+Pline.substring(Ppos)+",rc="+rc);//~3407R~
    	return rc;                                                 //~3407I~
 	}                                                              //~3406I~
    //****************************************
	private boolean setKifAction(String Pline)      //~3406R~//~3408R~//~3409R~
	{
    	int pos;                               //~3406I~
        boolean rc=true;                                              //~3406I~//~3407R~
    //**********************************                           //~3406I~
        if (Dump.Y) Dump.println("setKifAction line="+Pline);      //~3407I~
        if (winner!=0)                                             //~3407I~
        	return false;                                          //~3407I~
        if (Pline.length()==0)                                     //~3407I~
            return rc;                                             //~3407I~
        if (Pline.charAt(0)==COMMENT_PREFIX)                       //~3407I~
        {                                                          //~3407I~
        	if (Dump.Y) Dump.println("setKifAction comment="+Pline);//~3407I~
            return rc;                                             //~3407I~
        }                                                          //~3407I~
        if (Pline.startsWith(VARIATION_PREFIX))                   //~3407I~
        {                                                          //~3407I~
        	if (Dump.Y) Dump.println("setKifAction variation="+Pline);//~3407I~
            return false;                                          //~3407I~
        }                                                          //~3407I~
        if (Pline.startsWith(GAMEOVER_PREFIX))                     //~3407I~
        {                                                          //~3407I~
    		return getGameoverReason(Pline);                        //~3407I~
        }                                                          //~3407I~
    	pos=Pline.indexOf(' ');                                    //~3406I~
        if (pos<=0)                                                //~3406I~
        	return rc;                                          //~3406I~//~3407R~
        int movenumber=Integer.valueOf(Pline.substring(0,pos)).intValue();//~3406I~
        if (movenumber>0)                                          //~3407I~
        {                                                          //~3407I~
            pos++;                                                     //~3406I~//~3407R~
            int color=movenumber%2!=0?1:-1;                        //~3407R~
            if (move1st<0)                                         //~3407R~
                color=-color;                                      //~3407R~
            moveNumber=movenumber;                                 //~3407I~
            if (!getMove(Pline,pos,color))                   //~3407R~//~3409R~
            	getGameoverReasonMove(Pline,pos);                   //~3407I~
        }                                                          //~3407I~
        return rc;                                                 //~3407R~
	}
    //**************************************************************************//~3407I~
	private boolean getMove(String Pline,int Ppos,int Pcolor)//~3407R~//~3408R~//~3409R~
	{                                                              //~3407I~
         boolean rc=true;                                      //~3407I~
     //*****************************************                    //~3407I~
    	if (!getMoveTo(Pline,Ppos))                                //~3407I~
        	return false;                                          //~3407I~
		if (!getMoveFrom(Pline,posFrom))                           //~3407R~
        	return false;                                          //~3407I~
        rc=getAction(Pcolor);                                //~3407I~//~3409R~
        lastColor=Pcolor;                                           //~3416I~
		return rc;                                                 //~3407I~
	}                                                              //~3407I~
    //**************************************************************************//~3407I~
	private boolean getMoveTo(String Pline,int Ppos)         //~3407I~//~3408R~
	{                                                              //~3407I~
    	int ii,jj,piece,pieceto,linelen;                                   //~3407I~//~3408R~
    	int pos=Ppos;                                         //~3407R~
        char ch;                                                   //~3407I~
        boolean drop=false,rc=true;                                      //~3407I~
    //*****************************************                    //~3407I~
    	pieceDrop=false;                                           //~3407I~
        pieceTo=0;                                                 //~3407I~
        pieceFrom=0;                                               //~3407I~
        iTo=0; jTo=0;                                              //~3407I~
                                                                   //~3408I~
                                                                   //~3407I~
		linelen=Pline.length();                                    //~3408I~
        ch=Pline.charAt(pos);                                      //~3407I~
        if (ch==MOVE_SAME)                                         //~3407I~
        {                                                          //~3407I~
        	if (Dump.Y) Dump.println("setKifAction same");         //~3407I~
//      	if (fmtTree.size()==0)                                   //~3407I~//~3409R~
//          	return false;                                      //~3407I~
//      	ActionMove a=fmtTree.lastElement();                      //~3407I~//~3409R~
            ii=lasti;                                              //~3407I~
            jj=lastj;                                              //~3407I~
            pos++;                                                 //~3407I~
	        ch=Pline.charAt(pos);                                  //~3407I~
        	if (Dump.Y) Dump.println("setKifAction same next="+ch);//~3407I~
            if (ch==DBCS_SPACE)                                    //~3407I~
            	pos++;                                             //~3407I~
        }                                                          //~3407I~
        else                                                       //~3407I~
        {                                                          //~3407I~
            if (ch>='1' && ch<='9')                                //~3407I~
                ii=ch-'1';                                         //~3407I~
            else                                                   //~3407I~
            if (ch>='１' && ch<='９')                              //~3407I~//~3414R~
                ii=ch-'１';                                        //~3407I~//~3414R~
            else                                                   //~3407I~
                return false;                                      //~3407I~
            ii=S-ii-1;                                             //~3407R~
            pos++;                                                 //~3407I~
	        ch=Pline.charAt(pos);                                  //~3407I~
            if (ch>='1' && ch<='9')                                //~3407I~
                jj=ch-'1';                                         //~3407I~
            else                                                   //~3407I~
            {                                                      //~3407I~
                jj=chkKanSuji(ch)-1;                               //~3407I~//~3415R~
                if (jj<0)                                          //~3407I~
                    return false;                                  //~3407I~
            }                                                      //~3407I~
            lasti=ii;                                              //~3407I~
            lastj=jj;                                              //~3407I~
            pos++;                                                 //~3407I~
        }                                                          //~3407I~
        piece=chkPiece(Pline,pos);                                 //~3407I~
        if (piece==0)                                              //~3407I~
        	return false;                                          //~3407I~
        pos=posPieceEnd;                                           //~3408I~
        if (pos<linelen && Pline.charAt(pos)==MOVE_DROP)                         //~3407R~//~3408R~
        {                                                          //~3408I~
        	drop=true;                                             //~3408R~
        	pos++;                                                 //~3408R~
        }                                                          //~3408I~
	    pieceto=piece;                                             //~3408I~
        if (pos<linelen && Pline.startsWith(MOVE_NOTPROMOTE,pos))     //~3408I~
        {                                                          //~3408I~
        	pos+=2;                                                //~3408I~
        }                                                          //~3408I~
        else                                                       //~3408I~
        {                                                          //~3408I~
	        if (pos<linelen && Pline.charAt(pos)==MOVE_PROMOTE)    //~3408I~
            {                                                      //~3408I~
                pieceto=Field.promoted(piece);                         //~3407I~//~3408R~
                pos++;                                             //~3408R~
            }                                                          //~3407I~//~3408R~
        }                                                          //~3408I~
		//*output                                                  //~3407I~
        pieceDrop=drop;                                            //~3407I~
        pieceTo=pieceto;                                           //~3407I~
        pieceFrom=piece;                                           //~3407I~
        iTo=ii;                                                    //~3407I~
        jTo=jj;                                                    //~3407I~
        posFrom=pos;                                               //~3407I~
        if (Dump.Y) Dump.println("getMoveTo drop="+pieceDrop+",piecefrom="+pieceFrom+",pieceto="+pieceTo+",ito="+iTo+",jTo="+jTo);//~3408I~
		return rc;                                                 //~3407I~
	}                                                              //~3407I~
    //**************************************************************************//~3407I~
	private boolean getMoveFrom(String Pline,int Ppos)       //~3407I~//~3408R~
	{                                                              //~3407I~
    	int ii2,jj2;                                   //~3407I~
    	int pos=Ppos;                                              //~3407I~
        boolean drop,rc=true;                                      //~3407I~
    //*****************************************                    //~3407I~
        drop=pieceDrop;                                            //~3407I~
        if (!drop)                                                 //~3407I~
        {                                                          //~3407I~
	        pos=Pline.indexOf(MOVE_FROM_PREFIX,pos);               //~3407I~
        	if (pos<=0)                                            //~3407I~
            	return false;                                      //~3407I~
            pos++;                                                 //~3407I~
            ii2=Pline.charAt(pos)-'1';                             //~3407I~
            ii2=S-ii2-1;                                           //~3407R~
            pos++;                                                 //~3407I~
            jj2=Pline.charAt(pos)-'1';                             //~3407I~
        }                                                          //~3407I~
    	else                                                       //~3407I~
        {                                                          //~3407I~
        	ii2=0;                                                 //~3407I~
            jj2=0;                                                 //~3407I~
        }                                                          //~3407I~
        iFrom=ii2;                                                 //~3407I~
        jFrom=jj2;                                                 //~3407I~
		return rc;                                                 //~3407I~
	}                                                              //~3407I~
	//****************************************************************//~3407I~
    private boolean getGameoverReason(String Pline)         //~3407I~//~3408R~
    {                                                              //~3407I~
        if (Pline.indexOf(GAMEOVER_WIN)>0||Pline.indexOf(GAMEOVER_LOSE)>0)//~3407I~
        {                                                          //~3407I~
            winner=0;                                              //~3407I~
            if (Pline.indexOf(WINNER_BLACK)>0) winner=1;           //~3407I~
            if (Pline.indexOf(WINNER_BLACK2)>0) winner=1;          //~3407I~
            if (Pline.indexOf(WINNER_WHITE)>0) winner=-1;          //~3407I~
            if (Pline.indexOf(WINNER_WHITE2)>0) winner=-1;         //~3407I~
            if (Pline.indexOf(GAMEOVER_LOSE)>0)                    //~3407I~
                winner=-winner;                                    //~3407I~
            if (Dump.Y) Dump.println("setKifAction winner="+winner);//~3407I~
        }                                                          //~3407I~
//        if (Pline.indexOf(GAMEOVER_RESIGN)>0)                      //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_RESIGN;                        //~3407I~//~3416R~
//        else                                                       //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_TIMEOUT)>0)                     //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_TIMEOUT;                       //~3407I~//~3416R~
//        else                                                       //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_FOUL)>0)                        //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_FOUL;                          //~3407I~//~3416R~
//        else                                                       //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_CHECKMATE)>0)                   //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_CHECKMATE;                     //~3407I~//~3416R~
//        else                                                       //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_NOCHECKMATE)>0)                 //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_NOCHECKMATE;                   //~3407I~//~3416R~
//        else                                                       //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_LOOP)>0)                        //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_LOOP;                          //~3407I~//~3416R~
//        else                                                       //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_INFINITE)>0)                    //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_INFINITE;                      //~3407I~//~3416R~
//        if (Pline.indexOf(GAMEOVER_SUSPEND)>0)                     //~3407I~//~3416R~
//            gameoverReason=GAMEOVER_SUSPEND;                       //~3407I~//~3416R~
    	int id=0,ii;                                               //~3416I~
//        boolean rc=false;                                          //~3416I~
        for (ii=0;ii<KIF_GAMEOVERNAME.length;ii++)                 //~3416I~
        {                                                          //~3416I~
    		if (Pline.indexOf(KIF_GAMEOVERNAME[ii])>0)             //~3416I~
            {                                                      //~3416I~
            	id=KIF_GAMEOVER_ID[ii];                            //~3416I~
                break;                                             //~3416I~
            }                                                      //~3416I~
        }                                                          //~3416I~
        if (id!=0)                                                 //~3416I~
	        gameoverReasonId=id;                                   //~3416R~
        if (Dump.Y) Dump.println("gameoverreasonId="+gameoverReasonId+",idx="+ii+",line"+Pline);//~3407I~//~3416R~
        return false;                                              //~3407I~
    }                                                              //~3407I~
	//****************************************************************//~3407I~
	//*gameover reason by move line                                //~3407I~
	//****************************************************************//~3407I~
    private boolean getGameoverReasonMove(String Pline,int Ppos)//~3407I~//~3408R~
    {                                                              //~3407I~
        if (Pline.startsWith(GAMEOVER_RESIGN,Ppos))                //~3407I~
        {                                                          //~3416I~
            gameoverReasonId=GAMEOVERID_RESIGN;                        //~3407I~
            winner=lastColor;                                      //~3416I~
        }                                                          //~3416I~
        else                                                       //~3407I~
        if (Pline.indexOf(GAMEOVER_CHECKMATE)>0)                   //~3407I~
        {                                                          //~3416I~
            gameoverReasonId=GAMEOVERID_CHECKMATE;                     //~3407I~
            winner=lastColor;                                      //~3416I~
        }                                                          //~3416I~
        else                                                       //~3407I~
        	return false;                                          //~3407I~
        if (Dump.Y) Dump.println("gameoverreasonMove reasonId="+gameoverReasonId+",line"+Pline);//~3407I~
        return true;                                               //~3407I~
    }                                                              //~3407I~
    //****************************************                     //~3406I~
    //*start by black triangle                                     //~3407I~
    //****************************************                     //~3407I~
	private boolean setKi2Action(String Pline)//~3406I~//~3408R~   //~3409R~
    {                                                              //~3406I~
    	int pos,color,posb,posw,posw2;                         //~3407R~//~3408R~
        char ch;                                                   //~3407I~
        boolean rc=true;                                      //~3407I~
    //**********************************                           //~3407I~
        if (Dump.Y) Dump.println("setKi2Action line="+Pline);      //~3407I~
        if (winner!=0)                                             //~3407I~
        	return false;                                          //~3407I~
        if (Pline.length()==0)                                     //~3407I~
            return rc;
        if (Pline.startsWith(GAMEOVER_PREFIX))                     //~3407I~
        {                                                          //~3407I~
    		rc=getGameoverReason(Pline);                        //~3407I~//~3408R~
            if (winner!=0 && gameoverReasonId==0)                 //~3408I~
            {                                                      //~3416I~
            	gameoverReasonId=GAMEOVERID_RESIGN;      //no Resign on ki2//~3408I~
	            winner=lastColor;                                  //~3416I~
            }                                                      //~3416I~
            return rc;                                             //~3408I~
        }                                                          //~3407I~
        for (pos=0;pos<Pline.length();)                        //~3407I~
        {                                                          //~3407I~
        	ch=Pline.charAt(pos);                                  //~3407I~
            if (ch==BLACK_SIGN)                                    //~3407I~
            {                                                      //~3407I~
            	color=1;                                           //~3407I~
                pos++;                                             //~3407I~
            }                                                      //~3407I~
            else                                                   //~3407I~
            if (ch==WHITE_SIGN||ch==WHITE_SIGN2)                   //~3407I~
            {                                                      //~3407I~
            	color=-1;                                          //~3407I~
                pos++;                                             //~3407I~
            }                                                      //~3407I~
            else                                                   //~3407I~
            	break;                                             //~3407I~
            moveNumber++;                                          //~3407I~
			rc=getMoveKi2(Pline,pos,color);                  //~3407R~//~3409R~
            if (!rc)                                               //~3407R~
            	break;                                             //~3407I~
            posb=Pline.indexOf(BLACK_SIGN,pos);                    //~3408R~
	        posw=Pline.indexOf(WHITE_SIGN,pos);                    //~3408I~
	        posw2=Pline.indexOf(WHITE_SIGN2,pos);                  //~3408I~
            if (posb>0)                                            //~3408R~
            {                                                      //~3408I~
            	if (posw>0)                                        //~3408I~
                	pos=Math.min(posb,posw);                       //~3408I~
                else                                               //~3408I~
            	if (posw2>0)                                       //~3408I~
                	pos=Math.min(posb,posw2);                      //~3408I~
                else                                               //~3408I~
                	pos=posb;                                      //~3408I~
            }                                                      //~3408I~
            else                                                   //~3408I~
            {                                                      //~3408I~
            	if (posw>0)                                        //~3408I~
                	pos=posw;                                      //~3408I~
                else                                               //~3408I~
            	if (posw2>0)                                       //~3408I~
                	pos=posw2;                                     //~3408I~
                else                                               //~3408I~
                	break;                                         //~3408I~
            }                                                      //~3408I~
            if (Dump.Y) Dump.println("next move="+Pline.substring(pos));//~3408I~
        }                                                          //~3407I~
        return rc;//~3407I~
    }                                                              //~3406I~
    //**************************************************************************//~3407I~
	private boolean getMoveKi2(String Pline,int Ppos,int Pcolor)//~3407I~//~3408R~//~3409R~
	{                                                              //~3407I~
         boolean rc=true;                                          //~3407I~
     //*****************************************                   //~3407I~
    	if (!getMoveTo(Pline,Ppos))                                //~3407I~
        	return false;                                          //~3407I~
     	if (pieceDrop)                                             //~3407I~//~3408M~
        {                                                          //~3407I~//~3408M~
        	iFrom=0;jFrom=0;                                       //~3407I~//~3408M~
        }                                                          //~3407I~//~3408M~
        else                                                       //~3407I~//~3408M~
    		if (!getMoveFromKi2(Pline,posFrom,Pcolor))                //~3407I~//~3408R~
        		return false;                                      //~3408I~
        rc=getAction(Pcolor);                                //~3407I~//~3409R~
        lastColor=Pcolor;                                           //~3416I~
        if (Dump.Y) Dump.println("getMoveKi2 rc="+rc);             //~3408I~
		return rc;                                                 //~3407I~
	}                                                              //~3407I~
    //**************************************************************************//~3407I~
	private boolean getMoveFromKi2(String Pline,int Ppos,int Pcolor)//~3407I~//~3408R~
	{                                                              //~3407I~
         boolean rc=true,swbreak;                                          //~3407I~//~3408R~
         int mod=0,pos;                                                //~3407I~
         char ch;                                                  //~3407I~
     //*****************************************                   //~3407I~
        for (pos=Ppos;pos<Pline.length();pos++)                //~3407I~//~3408R~
        {                                                      //~3407I~//~3408R~
            ch=Pline.charAt(Ppos);                             //~3407I~//~3408R~
            swbreak=false;                                         //~3408I~
            switch(ch)                                         //~3407I~//~3408R~
            {                                                  //~3407I~//~3408R~
            case MOVE_RIGHT:                                   //~3407I~//~3408R~
                mod|=SB_RIGHT;                                 //~3407I~//~3408R~
                break;                                         //~3407I~//~3408R~
            case MOVE_LEFT:                                    //~3407I~//~3408R~
                mod|=SB_LEFT;                                  //~3407I~//~3408R~
                break;                                         //~3407I~//~3408R~
            case MOVE_UP:                                      //~3407I~//~3408R~
                mod|=SB_UP;                                    //~3407I~//~3408R~
                break;                                         //~3407I~//~3408R~
            case MOVE_DOWN:                                    //~3407I~//~3408R~
                mod|=SB_DOWN;                                  //~3407I~//~3408R~
                break;                                         //~3407I~//~3408R~
            case MOVE_HORIZONTAL:                             //~3407I~//~3408R~
                mod|=SB_HORIZONTAL;                            //~3407I~//~3408R~
                break;                                         //~3407I~//~3408R~
            case MOVE_VERTICAL:                                //~3407I~//~3408R~
                mod|=SB_VERTICAL;                              //~3407I~//~3408R~
                break;                                         //~3407I~//~3408R~
            case ' ':                                              //~3408I~
            case BLACK_SIGN:                                       //~3408I~
            case WHITE_SIGN:                                       //~3408I~
            case WHITE_SIGN2:                                      //~3408I~
            	swbreak=true;                                      //~3408I~
                break;                                             //~3408I~
            }                                                  //~3407I~//~3408R~
            if (swbreak)                                           //~3408I~
                break;                                             //~3408I~
        }                                                      //~3407I~//~3408R~
        getMoveFromSub(mod,Pcolor);                        //~3407I~//~3408R~
        if (Dump.Y) Dump.println("Ki2Sub drop="+pieceDrop+",i="+iFrom+",j="+jFrom+",mod="+mod+",pos="+pos);//~3407I~//~3408R~
        if (pieceDrop)                                         //~3407I~//~3408R~
        {                                                      //~3407I~//~3408R~
            iFrom=0;jFrom=0;                                   //~3407I~//~3408R~
        }                                                      //~3407I~//~3408R~
        if (Dump.Y) Dump.println("moveFromKi2 rc="+rc+",i="+iFrom+",j="+jFrom);//~3407I~//~3408R~
        return rc;                                                 //~3407I~
	}                                                              //~3407I~
}//NotesFmt                                                        //~3407R~
