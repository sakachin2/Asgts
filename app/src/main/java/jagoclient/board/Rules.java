//*CID://+1A1gR~:                             update#=  284;       //~1A1gR~
//****************************************************************************//~v101I~
//1A1g 2013/03/18 FreeBoard:chkmate msg is override by check msg   //~1A1gI~
//1A15 2013/03/10 Checkmate button for freeboard                   //~1A15I~
//1A0h 2013/03/06 chkPromotion() is not called for @@move from partner(se non promoted gold etc)//~1A0hI~
//1A0f 2013/03/05 check Chackmate for gameover                     //~1A0FI~
//101g 2013/02/09 captured mark remains at after partner move after I captured//~v101I~
//****************************************************************************//~v101I~
package jagoclient.board;                                          //~v101I~
                                                                   import com.Asgts.AG;                                               //~v101I~
import com.Asgts.R;

import jagoclient.Dump;                                            //~v101I~
import static jagoclient.board.Field.*;                            //~v101I~
                                                                   //~v101I~
public class Rules                                                 //~v101I~
{                                                                  //~@@@2R~//~v101I~
    private static final int PROMOTION_LINE=3;                     //~1A00I~//~v101I~
    //******************************************                   //~v101I~
    private ConnectedGoFrame CGF;                                  //~v101R~
	private ConnectedBoard B;                                      //~v101R~
	private Position P;                                            //~v101R~
    private int Col/*MyColor*/,S/*BoardSize*/,CC/*CurrentColor*/,cc;  //~v101R~
    private int iFrom,jFrom;                                       //~v101I~
    private int[][] checkTb,offenseTb,defenseTb,testTb;                   //~v101R~//~1A0FR~
    private static final int CT_FREE              =0;              //~v101R~
    private static final int CT_OFFENSE           =1;  //non supported my piece//~v101I~
    private static final int CT_WORKLINE          =3;  //empty on workline//~v101R~
    private static final int CT_WORKLINE_OFFENSE  =4;  //supported my piece//~v101R~
    private static final int CT_WORKLINE_DEFENSE  =6;  //opponent piece on workline//~v101R~
    private static final int CT_WORKLINE_KING     =7;  //opponent king on workline//~v101R~
    private static final int CT_DEFENSE           =9;  //free opponent piece//~v101R~
                                                                   //~v101I~
    private int[]  descWorkTb;                                     //~1A0FR~
    private static final int MAX_DESC_WORK=8;                      //~v101M~
    public static final int SB_UP          =0x01;                 //~v101I~//~1A1gR~
    public static final int SB_DOWN        =0x02;                 //~v101I~//~1A1gR~
    public static final int SB_LEFT        =0x04;                 //~v101I~//~1A1gR~
    public static final int SB_RIGHT       =0x08;                 //~v101I~//~1A1gR~
    public static final int SB_HORIZONTAL  =0x10;                 //~v101I~//~1A1gR~
    public static final int SB_VERTICAL    =0x20;                 //~v101R~//~1A1gR~
    private boolean kingCheck;                                     //~v101I~
    private int iKingCheck,jKingCheck;	//opponent checked King position   //~1A0FI~
    private static final int MAX_CHECK=8;	//8 direction          //~1A0FI~
    private static int checkStackCtr;                              //~1A0FI~
    private static int checkStack_i[]=new int[MAX_CHECK];          //~1A0FI~
    private static int checkStack_j[]=new int[MAX_CHECK];          //~1A0FI~
    private static int checkStack_piece[]=new int[MAX_CHECK];      //~1A0FI~
    private static int checkStack_icopy[]=new int[MAX_CHECK];      //~1A0FI~
    private static int checkStack_jcopy[]=new int[MAX_CHECK];      //~1A0FI~
    private static int checkStack_piececopy[]=new int[MAX_CHECK];  //~1A0FI~
    private static int capturedList[]=new int[MAX_PIECE_TYPE-1];   //~1A0FI~
    private static String Sup,Sdown,Sleft,Sright,Shorizontal,Svertical,Sdropped,Ssame;//~v101R~
    private static String Sleftup,Srightup,Sleftdown,Srightdown,Spromoted,Snotpromoted;//~v101R~
    private static String[] Spiecenamemove;                        //~v101I~
    private int prevPosI=-1,prevPosJ=-1;                               //~v101I~
    private boolean notPromoted;                                   //~v101I~
    private boolean swSame;                                        //~v101I~
    public boolean isCheckmated;                                   //~1A1gI~
    public Rules(ConnectedGoFrame Pcgf,ConnectedBoard Pboard,int Psize)//~v101R~
    {                                                              //~v101I~
    	CGF=Pcgf;                                                  //~v101I~
        S=Psize;                                                   //~v101I~
    	B=Pboard;                                                  //~v101R~
        P=B.P;                                                     //~v101I~
        Col=CGF.Col;                                                //~v101I~
        offenseTb=new int[S][S];                                   //~v101R~
        defenseTb=new int[S][S];                                   //~v101I~
        testTb=new int[S][S];                                      //~1A0FI~
        descWorkTb=new int[MAX_DESC_WORK*2];                       //~v101I~
        if (Sup==null)                                             //~v101I~
        	getDescWord();                                         //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~@@@@I~//~@@@2M~
//chk path is available from (iFrom,jFrom) to (i,j)        //~@@@@I~//~@@@2M~//~v101R~
//rc:0 no path,1:path ok,-1:dup ignore                             //~v101I~
//**************************************************************** //~@@@@I~//~@@@2M~
	public int isOnPiecePath(boolean Perrmsg,int Ppiececolor,int i,int j,int PiFrom,int PjFrom)                     //~@@@@I~//~@@@2M~//~v101R~//~1A0FR~
    {                                                              //~@@@@I~//~@@@2M~
    	int rc=0,dest,ccolor,piece;                                              //~@@@@I~//~@@@2M~//~v101R~
    //*****************************************                    //~v101I~
    	iFrom=PiFrom;                                              //~v101I~
    	jFrom=PjFrom;                                              //~v101I~
        if (Dump.Y) Dump.println("Rules:isOnPiecePath to ("+i+","+j+"),color="+P.color(i,j)+",from=("+PiFrom+","+PjFrom+"),piece="+P.piece(iFrom,jFrom));//~v101R~//~1A0FR~//~1A1gR~
//      ccolor=B.currentColor();                                   //~v101R~
        ccolor=Ppiececolor;                                        //~v101I~
//      if (P.color(i,j)!=0)    //filled                           //~@@@@I~//~@@@2M~//~v101R~
      if (Perrmsg)                                        //~v101I~//~1A0FR~
        if (P.color(i,j)==ccolor)	//my staff                     //~v101R~
        	return 0;                                          //~@@@@I~//~@@@2M~//~v101R~
//      dest=(Col==maincolor())?-1:1;                              //~v101R~
        dest=(Col==ccolor)?-1:1;                                   //~v101R~
//        if (P.isSetPiece(i,j))  //not yet set color but move duplicate check//~v101R~
//        if (P.piece(i,j)!=0)  //not yet set color but move duplicate check//~v101R~
//            return -1;                                           //~v101R~
		if (i==iFrom && j==jFrom)	//no move                      //~v101R~
        	return 0;                                              //~v101I~
    	piece=P.piece(iFrom,jFrom);                                //~v101R~
    	switch(piece)                                              //~v101I~
        {                                                          //~@@@@I~//~@@@2M~
        case PIECE_PAWN:                                         //~@@@@I~//~@@@2M~//~v101R~
        	rc=isOnPiecePathPawn(i,j,dest)?1:0;                           //~@@@@I~//~@@@2M~//~v101R~
            break;                                                 //~@@@@I~//~@@@2M~
        case PIECE_LANCE:                                          //~v101I~
        	rc=isOnPiecePathLance(i,j,dest)?1:0;                   //~v101R~
            break;                                                 //~v101I~
        case PIECE_KNIGHT:                                         //~v101I~
        	rc=isOnPiecePathKnight(i,j,dest)?1:0;                  //~v101R~
            break;                                                 //~v101I~
        case PIECE_SILVER:                                         //~v101I~
        	rc=isOnPiecePathSilver(i,j,dest)?1:0;                       //~v101R~
            break;                                                 //~v101I~
        case PIECE_GOLD:                                         //~@@@@I~//~@@@2M~//~v101R~
        	rc=isOnPiecePathGold(i,j,dest)?1:0;                           //~@@@@I~//~@@@2M~//~v101R~
            break;                                                 //~@@@@I~//~@@@2M~
        case PIECE_BISHOP:                                         //~v101I~
        	rc=isOnPiecePathBishop(i,j)?1:0;                       //~v101R~
            break;                                                 //~v101I~
        case PIECE_ROOK:                                           //~v101I~
        	rc=isOnPiecePathRook(i,j)?1:0;                         //~v101R~
            break;                                                 //~v101I~
        case PIECE_KING:                                           //~v101I~
        	rc=isOnPiecePathKing(i,j)?1:0;                         //~v101R~
            break;                                                 //~v101I~
        case PIECE_PBISHOP: //promoted                             //~v101I~
        	rc=(isOnPiecePathBishop(i,j)||isOnPiecePathKing(i,j))?1:0;//~v101I~
            break;                                                 //~v101I~
        case PIECE_PROOK:  	//promoted                             //~v101I~
        	rc=(isOnPiecePathRook(i,j)||isOnPiecePathKing(i,j))?1:0;//~v101I~
            break;                                                 //~v101I~
        default: //promoted                                        //~v101I~
        	rc=isOnPiecePathGold(i,j,dest)?1:0;                    //~v101R~
            break;                                                 //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
        if (Perrmsg)	//errmsg                                   //~1A0FR~
        {                                                          //~v101I~
        	if (rc==0)                                             //~v101I~
            {                                                      //~v101I~
				int rc2=B.errNoPath(PiFrom,PjFrom,piece,Ppiececolor,i,j);//0:if gameover ,-1 if not//~v101I~
                rc=(rc2==-1)?0:1;	//0 if rc2==-1,if gameover rc=1(ok,gameover process later)//~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            {                                                      //~v101I~
				rc=isLeaveCheck(piece,Ppiececolor,i,j,PiFrom,PjFrom);//~v101R~
            }	                                                   //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("Rules:isOnPiecePath rc="+rc+",from ("+iFrom+","+jFrom+"),color="+P.color(iFrom,jFrom)+",piece="+P.piece(iFrom,jFrom));//~v101R~//~1A1gR~
        return rc;                                                 //~@@@@I~//~@@@2M~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~v101I~
	private boolean isOnPiecePathPawn(int Pi,int Pj,int Pdest)     //~v101R~
    {                                                              //~v101I~
    	boolean rc;                                                //~v101I~
        rc=(Pi==iFrom) && Pj==jFrom+Pdest;                         //~v101R~
    	if (Dump.Y) Dump.println("Rules:isOnPiecePathPawn Fu rc="+rc);                     //~v101I~//~1A1gR~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private boolean isOnPiecePathLance(int Pi,int Pj,int Pdest)    //~v101R~
    {                                                              //~v101I~
    	boolean rc;                                                //~v101I~
        if (Pdest<0)                                               //~v101I~
        {                                                          //~v101I~
            rc=(Pi==iFrom) && Pj<jFrom;                            //~v101R~
            if (rc)                                                //~v101R~
            {                                                      //~v101R~
                for (int jj=jFrom-1;jj>=0 && jj>Pj;jj--)           //~v101R~
                    if (P.color(Pi,jj)!=0)                         //~v101R~
                    {                                              //~v101R~
                        rc=false;                                  //~v101R~
                        break;                                     //~v101R~
                    }                                              //~v101R~
            }                                                      //~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
            rc=(Pi==iFrom) && Pj>jFrom;                            //~v101R~
            if (rc)                                                //~v101I~
            {                                                      //~v101I~
                for (int jj=jFrom+1;jj<S && jj<Pj;jj++)            //~v101R~
                    if (P.color(Pi,jj)!=0)                         //~v101I~
                    {                                              //~v101I~
                        rc=false;                                  //~v101I~
                        break;                                     //~v101I~
                    }                                              //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
    	if (Dump.Y) Dump.println("Rules:Kyo rc="+rc);                    //~v101I~//~1A1gR~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private boolean isOnPiecePathKnight(int Pi,int Pj,int Pdest)   //~v101R~
    {                                                              //~v101I~
    	boolean rc;                                                //~v101R~
        int is,js;                                                 //~v101I~
    //**********************                                       //~v101I~
        is=Pi-iFrom;                                               //~v101R~
        js=Pj-jFrom;                                               //~v101R~
        rc=(is==1||is==-1) && (js==(Pdest<0?-2:2));                //~v101R~
    	if (Dump.Y) Dump.println("Kei rc="+rc);                    //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private boolean isOnPiecePathSilver(int Pi,int Pj,int Pdest)   //~v101R~
    {                                                              //~v101I~
    	boolean rc;                                                //~v101R~
        int is,js;                                                 //~v101I~
    //**********************                                       //~v101I~
        is=Pi-iFrom;                                               //~v101R~
        js=Pj-jFrom;                                               //~v101R~
        if (js==Pdest)                                             //~v101I~
        	rc=(is<=1&&is>=-1) ;                                   //~v101R~
        else                                                       //~v101I~
        	rc=(js==-Pdest) && (is==1||is==-1);                    //~v101I~
    	if (Dump.Y) Dump.println("Gin rc="+rc);                    //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private boolean isOnPiecePathGold(int Pi,int Pj,int Pdest)     //~v101R~
    {                                                              //~v101I~
    	boolean rc;                                                //~v101R~
        int is,js;                                                 //~v101I~
    //**********************                                       //~v101I~
        is=Pi-iFrom;                                               //~v101R~
        js=Pj-jFrom;                                               //~v101R~
        if (js==-Pdest)  //back                                     //~v101R~
        	rc=is==0;                                              //~v101I~
        else                                                       //~v101I~
        	rc=(is<=1&&is>=-1) && (js==0||js==Pdest);              //~v101R~
    	if (Dump.Y) Dump.println("Kin rc="+rc);                    //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private boolean isOnPiecePathKing(int Pi,int Pj)     //~v101R~
    {                                                              //~v101I~
    	boolean rc;                                                //~v101R~
        int is,js;                                                 //~v101I~
    //**********************                                       //~v101I~
        is=Pi-iFrom;                                               //~v101R~
        js=Pj-jFrom;                                               //~v101R~
        rc=(is<=1&&is>=-1) && (js<=1&&js>=-1);                     //~v101R~
    	if (Dump.Y) Dump.println("Oh rc="+rc);                     //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~@@@@I~//~@@@2M~
	private boolean isOnPiecePathRook(int Pi,int Pj)               //~@@@@I~//~@@@2M~//~v101R~
    {                                                              //~@@@@I~//~@@@2M~
    	boolean rc=true;                                          //~@@@@I~//~@@@2M~//~v101R~
        int ii,jj;                                                 //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
        if (Pi==iFrom)  //vertical                             //~@@@@I~//~@@@2M~//~v101R~
        {                                                          //~@@@@I~//~@@@2M~
        	if (Pj>jFrom)               //up                   //~@@@@I~//~@@@2M~//~v101R~
            {                                                      //~@@@@I~//~@@@2M~
		        for (jj=jFrom+1;jj<Pj;jj++)                    //~@@@@I~//~@@@2M~//~v101R~
		        	if (P.color(Pi,jj)!=0)	//filled               //~@@@@I~//~@@@2M~
                    {                                              //~v101I~
                    	rc=false;                                  //~v101I~
                    	break;                                     //~@@@@I~//~@@@2M~
                    }                                              //~v101I~
            }                                                      //~@@@@I~//~@@@2M~
            else      //Pj<jFrom        //down                 //~@@@@I~//~@@@2M~//~v101R~
            {                                                      //~@@@@I~//~@@@2M~
		        for (jj=jFrom-1;jj>Pj;jj--)                    //~@@@@I~//~@@@2M~//~v101R~
		        	if (P.color(Pi,jj)!=0)	//filled               //~@@@@I~//~@@@2M~
                    {                                              //~v101I~
                    	rc=false;                                  //~v101I~
                    	break;                                     //~@@@@I~//~@@@2M~
                    }                                              //~v101I~
            }                                                      //~@@@@I~//~@@@2M~
        }                                                          //~@@@@I~//~@@@2M~
        else                                                       //~@@@@R~//~@@@2M~
        if (Pj==jFrom)  //horizontal                           //~@@@@I~//~@@@2M~//~v101R~
        {                                                          //~@@@@I~//~@@@2M~
        	if (Pi>iFrom)               //to right             //~@@@@I~//~@@@2M~//~v101R~
            {                                                      //~@@@@I~//~@@@2M~
		        for (ii=iFrom+1;ii<Pi;ii++)                    //~@@@@I~//~@@@2M~//~v101R~
		        	if (P.color(ii,Pj)!=0)	//filled               //~@@@@I~//~@@@2M~
                    {                                              //~v101I~
                    	rc=false;                                  //~v101I~
                    	break;                                     //~@@@@I~//~@@@2M~
                    }                                              //~v101I~
            }                                                      //~@@@@I~//~@@@2M~
            else      //Pi<iFrom                               //~@@@@I~//~@@@2M~//~v101R~
            {                                                      //~@@@@I~//~@@@2M~
		        for (ii=iFrom-1;ii>Pi;ii--)                    //~@@@@I~//~@@@2M~//~v101R~
		        	if (P.color(ii,Pj)!=0)	//filled               //~@@@@I~//~@@@2M~
                    {                                              //~v101I~
                    	rc=false;                                  //~v101I~
                    	break;                                     //~@@@@I~//~@@@2M~
                    }                                              //~v101I~
            }                                                      //~@@@@I~//~@@@2M~
        }                                                          //~@@@@I~//~@@@2M~
        else                                                       //~v101I~
        	rc=false;                                              //~v101I~
    	if (Dump.Y) Dump.println("Hi rc="+rc);                     //~v101I~
        return rc;                                                 //~@@@@I~//~@@@2M~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	private boolean isOnPiecePathBishop(int Pi,int Pj)             //~@@@@I~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~
        boolean rc=true;                                             //~@@@@I~//~@@@2R~//~v101R~
    //**********************                                       //~@@@@I~//~@@@2M~
		if (isOnPiecePathBishopDR(iFrom+1,jFrom+1,Pi,Pj)!=0) //down right//~@@@@I~//~@@@2R~//~v101R~
		if (isOnPiecePathBishopDL(iFrom-1,jFrom+1,Pi,Pj)!=0) //down left//~v101R~
		if (isOnPiecePathBishopUR(iFrom+1,jFrom-1,Pi,Pj)!=0)//up right//~@@@@I~//~@@@2R~//~v101R~
		if (isOnPiecePathBishopUL(iFrom-1,jFrom-1,Pi,Pj)!=0)//up right//~@@@@I~//~@@@2M~//~v101R~
        	rc=false;                                              //~v101I~
    	if (Dump.Y) Dump.println("kaku rc="+rc);                   //~v101I~
        return rc;                                              //~@@@@I~//~@@@2M~//~v101R~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
//Bishop path up-right                                             //~@@@@I~//~@@@2M~
//rc=0:reached,(ii)>0:reached horizontal edge,(-jj)<0:reached vertical edge,=S:unreachable//~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	private int isOnPiecePathBishopDR(int Pi1,int Pj1,int Pi2,int Pj2)//~@@@@I~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~
     	int rc=0;                                                    //~@@@@I~//~@@@2M~//~v101R~
        int ii,jj;                                                 //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("PathBishop-DR from=("+Pi1+","+Pj1+"),to=("+Pi2+","+Pj2);//~@@@@I~//~@@@2M~
    	for (ii=Pi1,jj=Pj1;ii!=Pi2||jj!=Pj2;ii++,jj++)	//down-right//~@@@@I~//~@@@2M~
        {                                                          //~@@@@I~//~@@@2M~
        	if (ii>=S||jj>=S	//beyond right edge                        //~@@@@I~//~@@@2M~//~v101R~
            ||  P.color(ii,jj)!=0)          //blocked              //~v101I~
            {                                                      //~v101I~
            	rc=1;                                              //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
    	if (Dump.Y) Dump.println("kaku DR rc="+rc);                //~v101I~
        return rc;                                                 //~v101R~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	private int isOnPiecePathBishopDL(int Pi1,int Pj1,int Pi2,int Pj2)//~@@@@I~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~
      	int rc=0;                                                    //~@@@@I~//~@@@2M~//~v101R~
        int ii,jj;                                                 //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("PathBishop-DL from=("+Pi1+","+Pj1+"),to=("+Pi2+","+Pj2);//~@@@@I~//~@@@2M~
    	for (ii=Pi1,jj=Pj1;ii!=Pi2||jj!=Pj2;ii--,jj++)	//down-left//~@@@@I~//~@@@2M~
        {                                                          //~@@@@I~//~@@@2M~
        	if (ii<=0||jj>=S	//beyond left edge                 //~v101R~
            ||  P.color(ii,jj)!=0)          //blocked              //~v101I~
            {                                                      //~v101I~
            	rc=1;                                              //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
    	if (Dump.Y) Dump.println("kaku DL rc="+rc);                //~v101I~
        return rc;                                                 //~v101R~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	private int isOnPiecePathBishopUR(int Pi1,int Pj1,int Pi2,int Pj2)//~@@@@I~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~
      	int rc=0;                                                  //~v101I~
        int ii,jj;                                                 //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("PathBishop-UR from=("+Pi1+","+Pj1+"),to=("+Pi2+","+Pj2);//~@@@@I~//~@@@2M~
    	for (ii=Pi1,jj=Pj1;ii!=Pi2||jj!=Pj2;ii++,jj--)	//up-right //~@@@@I~//~@@@2M~
        {                                                          //~@@@@I~//~@@@2M~
        	if (ii>=S||jj<=0	//beyond right edge                //~v101R~
            ||  P.color(ii,jj)!=0)          //blocked              //~v101I~
            {                                                      //~v101I~
            	rc=1;                                              //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
    	if (Dump.Y) Dump.println("kaku UR rc="+rc);                //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~@@@@I~//~@@@2M~
	private int isOnPiecePathBishopUL(int Pi1,int Pj1,int Pi2,int Pj2)//~@@@@I~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~
      	int rc=0;                                                  //~v101I~
        int ii,jj;                                                 //~@@@@I~//~@@@2M~
    //**********************                                       //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("PathBishop-UL from=("+Pi1+","+Pj1+"),to=("+Pi2+","+Pj2);//~@@@@I~//~@@@2M~
    	for (ii=Pi1,jj=Pj1;ii!=Pi2||jj!=Pj2;ii--,jj--)	//up-left  //~@@@@I~//~@@@2M~
        {                                                          //~@@@@I~//~@@@2M~
        	if (ii<=0||jj<=0	//beyond left/up edge              //~v101R~
            ||  P.color(ii,jj)!=0)          //blocked              //~v101I~
            {                                                      //~v101I~
            	rc=1;                                              //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        }                                                          //~@@@@I~//~@@@2M~
    	if (Dump.Y) Dump.println("kaku UL rc="+rc);                //~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~@@@@I~//~@@@2M~
//**************************************************************** //~v101I~
	public int chkPromotion(int Pi,int Pj,int Pifrom,int Pjfrom,int Ppiece)//~v101R~
    {                                                              //~v101I~
    	int piece,color;                                           //~v101R~
        boolean promotion,btnchk=false;                                  //~v101R~
    //**********************                                       //~v101I~
    	notPromoted=false;                                         //~v101I~
        if (Dump.Y) Dump.println("Rules:chkPromotion i="+Pi+",j="+Pj+",piece="+Ppiece+",dropped="+CGF.B.swDropped);//~v101I~//~1A1gR~
    	if (Ppiece>PIECE_PROMOTED)                                 //~v101R~
        	return Ppiece;                                          //~v101I~
    	if (CGF.B.swDropped)                                             //~v101I~
        	return Ppiece;                                          //~v101I~
        if (Ppiece==PIECE_KING||Ppiece==PIECE_GOLD)                //~1A0hI~
        	return Ppiece;                                         //~1A0hI~
//      color=B.currentColor();                                    //~v101R~
        color=P.color(Pifrom,Pjfrom);                              //~v101I~
        if (color==Col)                                            //~v101R~
            promotion=Pj    <PROMOTION_LINE                        //~v101R~
            		||Pjfrom<PROMOTION_LINE;                       //~v101I~
        else                                                       //~v101R~
            promotion=Pj    >=S-PROMOTION_LINE                     //~v101R~
            		||Pjfrom>=S-PROMOTION_LINE;                    //~v101R~
        piece=Ppiece;                                              //~v101I~
        if (promotion)                                             //~v101R~
        {                                                          //~v101I~
        	promotion=false;                                       //~v101I~
        	if (CGF.PromotionButton.isChecked()                   //~v101R~
//            ||  Ppiece==PIECE_BISHOP	//case not promote to avoid checkmate by dropPawn//~v101R~
//            ||  Ppiece==PIECE_ROOK                               //~v101R~
            )
            	promotion=true;                                    //~v101I~
            else                                                   //~v101I~
            if (Ppiece==PIECE_PAWN||Ppiece==PIECE_LANCE)  //if on 1st line              //+v101I~//~v101R~
            {                                                      //~v101I~
        		if (color==Col)                                    //~v101R~
            		promotion=Pj==0;                               //~v101I~
        		else                                               //~v101I~
            		promotion=Pj==(AG.BOARDSIZE_SHOGI-1);          //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            if (Ppiece==PIECE_KNIGHT)	//if on <2nd line          //~v101R~
            {                                                      //~v101I~
        		if (color==Col)                                    //~v101R~
            		promotion=Pj<=1;                               //~v101I~
        		else                                               //~v101I~
            		promotion=Pj>=(AG.BOARDSIZE_SHOGI-2);          //~v101I~
            }                                                      //~v101I~
            if (promotion)                                         //~v101I~
	            piece=Field.promoted(Ppiece);                      //~v101R~
            else                                                   //~v101I~
            	notPromoted=true;	//display not Promoted         //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("chkPromotion end -->"+piece+",color="+color+",btn="+btnchk);//~v101R~
        return piece;                                              //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~1A0hR~
//*set not ptomoted  flag for @@move from partner                  //~1A0hI~
//**************************************************************** //~1A0hI~
	public void chkPromotion(int Pi,int Pj,int Pifrom,int Pjfrom,int Ppiece/*after moved*/,int Pcolor)//~1A0FI~//~1A0hR~
    {                                                              //~1A0hR~
    	int piece;                                                 //~1A0hI~
        boolean promotion;                                         //~1A0hI~
    //**********************                                       //~1A0hI~
    	notPromoted=false;                                         //~1A0hI~
        if (Dump.Y) Dump.println("Rules:chkPromotion(@@move at partner) i="+Pi+",j="+Pj+",piece="+Ppiece+"<--"+P.piece(Pifrom,Pjfrom)+",dropped="+CGF.B.swDropped+",color="+Pcolor);//~1A0hR~//+1A1gR~
    	if (Ppiece>PIECE_PROMOTED)                                 //~1A0hI~
        	return;                                                //~1A0hI~
    	if (CGF.B.swDropped)                                       //~1A0hI~
        	return;                                                //~1A0hI~
        if (Ppiece==PIECE_KING||Ppiece==PIECE_GOLD)                //~1A0hI~
        	return;                                         //~1A0hI~
        if (Pcolor==Col)                                           //~1A0hI~
            promotion=Pj    <PROMOTION_LINE                        //~1A0hI~
            		||Pjfrom<PROMOTION_LINE;                       //~1A0hI~
        else                                                       //~1A0hI~
            promotion=Pj    >=S-PROMOTION_LINE                     //~1A0hI~
            		||Pjfrom>=S-PROMOTION_LINE;                    //~1A0hI~
        if (promotion)                                             //~1A0hI~
            notPromoted=true;	//used at moveDescription          //~1A0hI~
        if (Dump.Y) Dump.println("Rules:chkPromotion(@@move from partner) notPromoted="+notPromoted);//~1A0hI~//+1A1gR~
    }                                                              //~1A0hR~
//**************************************************************** //~v101I~
//*1:ok,0:reject,-1:foul                                           //~v101I~
//**************************************************************** //~v101I~
	public int chkDroppable(int Pi,int Pj,int Pcolor,int Ppiece)   //~v101R~
    {
		int rc=1,jj,colidx;                                            //~v101R~
    //***********************************                          //~v101I~
//      CC=B.currentColor();                                       //~v101R~
        CC=Pcolor;                                                 //~v101I~
        colidx=(CC==Col)?1:0;	//lower List(1) if current is my color//~v101R~
		if (!chkDropPosition(Pi,Pj,Ppiece,colidx))                 //~v101I~
        	return  B.errUnmovableDrop(Pi,Pj,Ppiece,CC);            //~v101R~
        if (Ppiece==PIECE_PAWN)                                    //~v101I~
        {                                                          //~v101I~
    	//* 2 pawn chk in th same column                           //~v101I~
    		for (jj=0;jj<S;jj++)                                   //~v101I~
            {                                                      //~v101I~
            	if (P.piece(Pi,jj)==PIECE_PAWN && P.color(Pi,jj)==CC)//~v101R~
                {	                                               //~v101I~
                	rc=B.err2Pawn(Pi,Pj,Ppiece,CC);                //~v101I~
                    break;                                         //~v101I~
                }                                                  //~v101I~
            }                                                      //~v101I~
            if (rc!=-1)                                            //~v101I~
            {                                                      //~v101I~
        //*chakmate by dropped pawn                                //~v101I~
                if (colidx==1)                                     //~v101I~
                    jj=Pj-1;                                       //~v101I~
                else                                               //~v101I~
                    jj=Pj+1;                                       //~v101I~
                if (P.piece(Pi,jj)==PIECE_KING) //jj>=0 && jj<S is already chked//~v101R~
                {                                                  //~v101I~
                    if (isCheckmate(Pi,jj,Pi,Pj,colidx))           //~v101R~
	                	rc=B.errDropPawnCheckmate(Pi,Pj,Ppiece,CC);//~v101I~
                }                                                  //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
        if (rc==1)	//ok                                           //~v101I~
        {                                                          //~v101I~
			rc=isLeaveCheck(Ppiece,Pcolor,Pi,Pj,-1,-1);            //~v101R~
        }                                                          //~v101I~
		return rc;//~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
//*0:ok,-1:                                                        //~v101I~
//**************************************************************** //~v101I~
	private boolean chkDropPosition(int Pi,int Pj,int Ppiece,int Pcolidx)//~v101R~
    {                                                              //~v101I~
		boolean rc=true;                                           //~v101I~
		int step;                                            //~v101I~
    //***********************************                          //~v101I~
        if (Ppiece==PIECE_PAWN||Ppiece==PIECE_LANCE)               //~v101R~
        	step=1;                                                //~v101R~
        else                                                       //~v101I~
        if (Ppiece==PIECE_KNIGHT)                                  //~v101R~
        	step=2;                                                //~v101I~
        else                                                       //~v101I~
        	step=0;                                                //~v101I~
        if (step>0)                                                //~v101I~
            if (Pcolidx==1)   //foward to j=0                       //~v101I~
            {                                                      //~v101I~
                if (Pj<step)                                       //~v101I~
                    rc=false;                                      //~v101I~
            }                                                      //~v101I~
            else             //forward to j=9                      //~v101I~
            {                                                      //~v101I~
                if (Pj>=(S-step))                                  //~v101R~
                    rc=false;                                      //~v101I~
            }                                                      //~v101I~
        if (Dump.Y) Dump.println("Rules chkDropPosition i="+Pi+",j="+Pj+",rc="+rc);//~v101I~
        return rc;                                                 //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
//*checkmate check for drop Pawn                                   //~v101I~//~1A0FR~
//**************************************************************** //~v101I~
	private boolean isCheckmate(int Pi/*king pos*/,int Pj,int Pidrop,int Pjdrop,int Pcolidx/*0,1*/)//~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101I~
    //***********************************                          //~v101I~
    	isCheckmated=false;                                        //~1A1gI~
        if (Dump.Y) Dump.println("isCheckmate by dropped pawn king=("+Pi+","+Pj+",drop=("+Pidrop+","+Pjdrop+",colidx="+Pcolidx);//~v101I~//~1A0FR~
    	cc=-CC;                                                    //~v101I~
    	setCT(defenseTb,Pcolidx==0?1:0);                           //~v101I~
        if (defenseTb[Pidrop][Pjdrop]==CT_WORKLINE)  //empty for drop pawn//~v101R~
        {                                                          //~v101I~
        	if (Dump.Y) Dump.println("isCheckmate false by defense");//~v101I~
        	return false;                //can be captured         //~v101I~
        }                                                          //~v101I~
    	cc=CC;                                                     //~v101I~
    	setCT(offenseTb,Pcolidx);                                  //~v101R~
    	for (int iii=-1;iii<=1;iii++)                              //~v101I~
    	{                                                          //~v101I~
    		for (int jjj=-1;jjj<=1;jjj++)                          //~v101I~
            {                                                      //~v101I~
            	ii=Pi+iii;                                         //~v101I~
                jj=Pj+jjj;                                         //~v101I~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~v101I~
	                if (iii!=0 || jjj!=0)   //not king itself      //~v101I~//~1A0FR~
                    {                                              //~v101I~
                        int check=checkTb[ii][jj];                 //~v101I~
                        if (check==CT_FREE||check==CT_OFFENSE)	//empty or free offense piece//~v101R~
                        {                                          //~v101I~
					        if (Dump.Y) Dump.println("isCheckmate false free at ("+ii+","+jj+")");//~v101R~
                        	return false;                          //~v101I~
                        }                                          //~v101I~
                    }                                              //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
		if (Dump.Y) Dump.println("isCheckmate true");              //~v101I~
    	isCheckmated=true;                                         //~1A1gI~
		return true;
    }                                                              //~v101I~
//**************************************************************** //~1A0FI~
//*checkmate check from isCheck                                    //~1A0FR~
//*base on offenseTb set by isCheck                                //~1A0FI~
//**************************************************************** //~1A0FI~
	public boolean isCheckmate(int Pcolor/*offense*/)             //~1A0FR~//~1A0hR~
    {                                                              //~1A0FI~
        if (Dump.Y) Dump.println("isCheckmate color="+Pcolor);     //~1A0FI~
    	isCheckmated=false;                                        //~1A1gI~
        System.arraycopy(checkStack_piece,0,checkStack_piececopy,0,checkStackCtr);//~1A0FI~
        System.arraycopy(checkStack_i,0,checkStack_icopy,0,checkStackCtr);//~1A0FI~
        System.arraycopy(checkStack_j,0,checkStack_jcopy,0,checkStackCtr);//~1A0FI~
        int ctr=checkStackCtr;                                     //~1A0FI~
        int iking=iKingCheck;                                      //~1A0FI~
        int jking=jKingCheck;                                      //~1A0FI~
        if (checkmateMove(Pcolor,iking,jking))	//king movable?    //~1A0FI~
        	return false;                                          //~1A0FI~
        if (checkmateCapture(Pcolor,ctr))	//capture offense?     //~1A0FI~
        	return false;                                          //~1A0FI~
        if (checkmateIntercept(Pcolor,ctr,iking,jking))	//intercept offense?//~1A0FI~
        	return false;                                          //~1A0FI~
        if (Dump.Y) Dump.println("isCheckmate true");              //~1A0FI~
    	isCheckmated=true;                                         //~1A1gI~
        return true;                                               //~1A0FR~
    }                                                              //~1A0FI~
//**************************************************************** //~v101I~
//*isCheck                                                         //~v101R~
//**************************************************************** //~v101I~
	public boolean isCheck(int Pcolor/*moved piece color*/)                             //~v101R~//~1A0FR~
    {                                                              //~v101I~
        boolean rc;                                                //~1A0FI~
    //***********************************                          //~v101I~//~1A0FR~
        cc=Pcolor;                                                 //~v101R~
        int colidx=(cc==Col)?1:0;	//lower List(1) if current is my color//~v101R~
        if (Dump.Y) Dump.println("isCheck move color="+cc+",idx="+colidx);//~v101R~
    	setCT(offenseTb,colidx);                                   //~v101I~
		if (Dump.Y) Dump.println("isCheck="+kingCheck);            //~v101I~
        rc=kingCheck;	//kingcheck may change by isCheckmate()    //~1A0FI~
        if (rc)                                                    //~1A0FR~
			if ((AG.Options & AG.OPTIONS_AUTO_CHECKMATE)!=0)       //~1A0FI~
            {                                                      //~1A0FI~
            	if (CGF.swLocalGame || Pcolor==Col)	//no chk move received side//~1A0FI~
            		if (isCheckmate(Pcolor))                       //~1A0FR~
						B.errCheckmate(Pcolor/*winner*/);          //~1A0FR~
            }                                                      //~1A0FI~
        return rc;                                          //~v101I~//~1A0FR~
    }                                                              //~v101I~
//**************************************************************** //~1A15R~
//*isCheckmateFB(freeBoard checkmate button)                       //~1A15R~
//**************************************************************** //~1A15R~
	public boolean isCheckmateFB(int Pcolor/*moved piece color*/)  //~1A15R~
    {                                                              //~1A15R~
        boolean rc=false;                                          //~1A15R~
    //***********************************                          //~1A15R~
        cc=Pcolor;                                                 //~1A15R~
        int colidx=(cc==Col)?1:0;	//lower List(1) if current is my color//~1A15R~
        if (Dump.Y) Dump.println("isCheck move color="+cc+",idx="+colidx);//~1A15R~
    	setCT(offenseTb,colidx);                                   //~1A15R~
		if (Dump.Y) Dump.println("isCheck="+kingCheck);            //~1A15R~
        if (kingCheck)	//kingcheck may change by isCheckmate()    //~1A15R~
            if (isCheckmate(Pcolor))                               //~1A15R~
				rc=true;                                           //~1A15R~
        return rc;                                                 //~1A15R~
    }                                                              //~1A15R~
//**************************************************************** //~v101I~
//*Opponent isCheck                                                //~v101I~
//*1:ok,0:ng                                                       //~v101R~
//**************************************************************** //~v101I~
	public int isLeaveCheck(int Ppiece,int Pcolor,int Pi,int Pj,int Pifrom,int Pjfrom)//~v101R~
    {                                                              //~v101I~
    	int rc=1;                                                  //~v101I~
    	//***********************************                      //~v101I~
        cc=-Pcolor;                                                //~v101I~
        int colidx=(cc==Col)?1:0;	//lower List(1) if current is my color//~v101I~
        if (Dump.Y) Dump.println("isLeaveCheck move piece="+Ppiece+",color="+cc+",idx="+colidx);//~v101R~
    	setCTMoveCheck(offenseTb,colidx,Ppiece,Pi,Pj,Pifrom,Pjfrom);        //~v101R~//~1A0FR~
		if (Dump.Y) Dump.println("isCheck="+kingCheck);            //~v101I~
        if (kingCheck)                                             //~v101I~
        	rc=B.errLeaveCheck(Pcolor,Ppiece,Pi,Pj);	//0:err reject,1 ok(gameover)//~v101R~//~1A0hR~
        return rc;                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
//*checkmate check                                                 //~v101I~
//**************************************************************** //~v101I~
	private void setCTMoveCheck(int[][] Pchktb,int Pcolidx,int Ppiece,int Pi,int Pj,int Pifrom,int Pjfrom)//~v101I~//~1A0FR~
    {                                                              //~v101I~
    	int oldpieceto=0,oldpiecefrom=0,oldcolorto=0,oldcolorfrom=0;       //~v101I~
		//***********************************                      //~v101I~
        if (Pifrom<0)	//drop                                     //~v101I~
        {                                                          //~v101I~
        	P.setPiece(Pi,Pj,-cc,Ppiece);	//set temporary        //~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	oldcolorto=P.color(Pi,Pj);                             //~v101I~
        	oldpieceto=P.piece(Pi,Pj);                             //~v101I~
        	oldcolorfrom=P.color(Pifrom,Pjfrom);                   //~v101I~
        	oldpiecefrom=P.piece(Pifrom,Pjfrom);                   //~v101I~
        	P.setPiece(Pi,Pj,-cc,Ppiece);	//set temporary        //~v101R~
        	P.setPiece(Pifrom,Pjfrom,0,0);	//set temporary move from position//~v101I~
        }                                                          //~v101I~
		setCT(Pchktb,Pcolidx);                                     //~v101I~
        if (Pifrom<0)	//drop                                     //~v101I~
        {                                                          //~v101I~
        	P.setPiece(Pi,Pj,0,0);	//restore before drop status   //~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
        	P.setPiece(Pi,Pj,oldcolorto,oldpieceto);               //~v101R~
        	P.setPiece(Pifrom,Pjfrom,oldcolorfrom,oldpiecefrom);   //~v101R~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("setCTMoveCheck kingCheck="+kingCheck);//~v101I~//~1A0FR~
    }                                                              //~v101I~
////****************************************************************//~1A0FR~
////*create offense tb after dropping opponent king                //~1A0FR~
////****************************************************************//~1A0FR~
//    private void setCTCheckMate(int[][] Pchktb,int Pcolidx,int Pi,int Pj)//~1A0FR~
//    {                                                            //~1A0FR~
//        int oldpiece,oldcolor;                                   //~1A0FR~
//        //***********************************                    //~1A0FR~
//        oldcolor=P.color(Pi,Pj);                                 //~1A0FR~
//        oldpiece=P.piece(Pi,Pj);                                 //~1A0FR~
//        setCT(Pchktb,Pcolidx);                                   //~1A0FR~
//        P.setPiece(Pi,Pj,oldcolor,oldpiece);                     //~1A0FR~
//        if (Dump.Y) Dump.println("setCT for checkCheckmate");    //~1A0FR~
//    }                                                            //~1A0FR~
//**************************************************************** //~v101I~
//*checkmate check                                                 //~v101I~
//**************************************************************** //~v101I~
	private void setCT(int[][] Pchktb,int Pcolidx)                //~v101R~
    {                                                              //~v101I~
		//***********************************                          //~v101I~
    	kingCheck=false;                                           //~v101I~
    	checkStackCtr=0;                                           //~1A0FI~
    	checkTb=Pchktb;                                            //~v101I~
		clearCT(cc);                                               //~v101R~
        for (int ii=0;ii<S;ii++)                                         //~v101I~
        {                                                          //~v101I~
        	for (int jj=0;jj<S;jj++)                                   //~v101I~
            {                                                      //~v101I~
            	if (P.color(ii,jj)==cc)                            //~v101R~
                   setOffensive(Pcolidx,ii,jj,P.piece(ii,jj));             //~v101R~
            }                                                      //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) traceCT(Pchktb);                               //~v101I~
    }                                                              //~v101I~
    //*****************************************                    //~v101I~
	private void traceCT(int[][] Pchktb)                           //~v101R~
    {                                                              //~v101I~
    	Dump.println("traceCT");                                   //~v101I~
        for (int jj=0;jj<S;jj++)                                   //~v101R~
        {                                                          //~v101I~
        	Dump.println(Pchktb[0][jj]+","+Pchktb[1][jj]+","+Pchktb[2][jj]+","+Pchktb[3][jj]+","+Pchktb[4][jj]+","+Pchktb[5][jj]+","+Pchktb[6][jj]+","+Pchktb[7][jj]+","+Pchktb[8][jj]);//~v101R~
        }                                                          //~v101I~
    }                                                              //~v101I~
    //*****************************************                    //~v101I~
	private void clearCT(int Pcolor/*current*/)                    //~v101R~
    {                                                              //~v101I~
        for (int ii=0;ii<S;ii++)                                   //~v101I~
        	for (int jj=0;jj<S;jj++)
            {                                                      //~v101I~
            	int color=P.color(ii,jj);                          //~v101I~
                if (color==Pcolor)                                 //~v101R~
        			checkTb[ii][jj]=CT_OFFENSE;                    //~v101R~
                else                                               //~v101M~
                if (color==-Pcolor)                                //~v101I~
        			checkTb[ii][jj]=CT_DEFENSE;                    //~v101I~
                else                                               //~v101I~
        			checkTb[ii][jj]=CT_FREE;	//for also offence piece place//~v101I~
            }                                                      //~v101I~
        	
    }                                                              //~v101I~
//**************************************************************** //~v101I~
//*flag empty square and own staff on my work line                 //~v101R~
//**************************************************************** //~v101I~
	private void setOffensive(int Pcolidx,int Pi,int Pj,int Ppiece)//~v101R~
    {                                                              //~v101I~
    //***********************************                          //~v101I~
    	int dest=Pcolidx==1?-1:1;                                  //~v101I~
    	switch(Ppiece)                                             //~v101I~
        {                                                          //~v101I~
        case PIECE_PAWN:                                           //~v101I~
			setCTPawn(Pi,Pj,dest);                                 //~v101R~
        	break;                                                 //~v101I~
        case PIECE_LANCE:                                          //~v101I~
			setCTLance(Pi,Pj,dest);                                //~v101R~
        	break;                                                 //~v101I~
        case PIECE_KNIGHT:                                         //~v101I~
			setCTKnight(Pi,Pj,dest);                               //~v101R~
        	break;                                                 //~v101I~
        case PIECE_SILVER:                                         //~v101I~
			setCTSilver(Pi,Pj,dest);                               //~v101R~
        	break;                                                 //~v101I~
        case PIECE_GOLD:                                           //~v101I~
        case PIECE_PPAWN:                                          //~v101I~
        case PIECE_PLANCE:                                         //~v101I~
        case PIECE_PKNIGHT:                                        //~v101I~
        case PIECE_PSILVER:                                        //~v101I~
			setCTGold(Pi,Pj,dest);                                 //~v101R~
        	break;                                                 //~v101I~
        case PIECE_BISHOP:                                         //~v101I~
			setCTBishop(Pi,Pj);                                    //~v101R~
        	break;                                                 //~v101I~
        case PIECE_PBISHOP:                                        //~v101I~
			setCTPBishop(Pi,Pj);                                   //~v101R~
        	break;                                                 //~v101I~
        case PIECE_ROOK:                                           //~v101I~
			setCTRook(Pi,Pj);                                      //~v101R~
        	break;                                                 //~v101I~
        case PIECE_PROOK:                                          //~v101I~
			setCTPRook(Pi,Pj);                                     //~v101R~
        	break;                                                 //~v101I~
        case PIECE_KING:                                           //~v101I~
            if (checkTb==offenseTb)                                //~v101I~
				setCTKing(Pi,Pj);                                  //~v101R~
        	break;                                                 //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTPawn(int Pi,int Pj,int Pdest)                //~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101I~
    //**********************                                       //~v101I~
    	ii=Pi;                                                     //~v101I~
    	jj=Pj+Pdest;                                           //~v101I~
        if (jj<0 || jj>=S)                                         //~v101I~
        	return;                                                //~v101I~
        ii=Pi;                                                     //~v101I~
    	int color=P.color(ii,jj);                                 //~v101R~
        if (color==0)                                              //~v101I~
            checkTb[ii][jj]=CT_WORKLINE;                           //~v101R~
        else                                                       //~v101I~
        if (color==cc)                                             //~v101I~
            checkTb[ii][jj]=CT_WORKLINE_OFFENSE;                   //~v101R~
        else                                                       //~v101I~
        if (P.piece(ii,jj)==PIECE_KING)                            //~v101R~
        {                                                          //~v101I~
//          checkTb[ii][jj]=CT_WORKLINE_KING;                      //~v101R~//~1A0FR~
    		stackCheck(PIECE_PAWN,Pi,Pj,ii,jj);                    //~1A0FI~
        }                                                          //~v101I~
        else                                                       //~v101I~
            checkTb[ii][jj]=CT_WORKLINE_DEFENSE;                   //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTLance(int Pi,int Pj,int Pdest)               //~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101R~
    //**********************                                       //~v101I~
    	ii=Pi;                                                     //~v101I~
        for (jj=Pj+Pdest;jj>=0&&jj<S;jj+=Pdest)                //~v101R~
        {                                                          //~v101I~
	    	int color=P.color(ii,jj);                              //~v101I~
            if (color==0)                                          //~v101I~
                checkTb[ii][jj]=CT_WORKLINE;                       //~v101R~
            else                                                   //~v101I~
            if (color==cc)                                         //~v101I~
            {                                                      //~v101I~
                checkTb[ii][jj]=CT_WORKLINE_OFFENSE;               //~v101R~
                break;                                             //~v101I~
            }                                                      //~v101I~
            else                                                   //~v101I~
            {                                                      //~v101I~
                if (P.piece(ii,jj)==PIECE_KING)                    //~v101R~
                {                                                  //~v101I~
//                  checkTb[ii][jj]=CT_WORKLINE_KING;              //~v101R~//~1A0FR~
		    		stackCheck(PIECE_LANCE,Pi,Pj,ii,jj);           //~1A0FI~
                }                                                  //~v101I~
		        else                                               //~v101I~
        		    checkTb[ii][jj]=CT_WORKLINE_DEFENSE;           //~v101I~
            	break;                                             //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTKnight(int Pi,int Pj,int Pdest)           //~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101I~
    //**********************                                       //~v101I~
        jj=Pj+Pdest*2;                                             //~v101I~
        if (jj<0 || jj>=S)                                         //~v101R~
        	return;                                                //~v101I~
        ii=Pi-1;                                                   //~v101I~
        if (ii>=0)                                                 //~v101R~
        {                                                          //~v101I~
	    	int color=P.color(ii,jj);                              //~v101I~
            if (color==0)                                          //~v101I~
                checkTb[ii][jj]=CT_WORKLINE;                       //~v101R~
            else                                                   //~v101I~
            if (color==cc)                                         //~v101I~
                checkTb[ii][jj]=CT_WORKLINE_OFFENSE;               //~v101R~
            else                                                   //~v101I~
            if (P.piece(ii,jj)==PIECE_KING)                        //~v101R~
            {                                                      //~v101I~
//              checkTb[ii][jj]=CT_WORKLINE_KING;                  //~v101R~//~1A0FR~
		    	stackCheck(PIECE_KNIGHT,Pi,Pj,ii,jj);              //~1A0FI~
            }                                                      //~v101I~
		    else                                                   //~v101I~
        	    checkTb[ii][jj]=CT_WORKLINE_DEFENSE;               //~v101I~
        }                                                          //~v101I~
        ii=Pi+1;                                                   //~v101I~
        if (ii<S)                                                  //~v101R~
        {                                                          //~v101I~
	    	int color=P.color(ii,jj);                              //~v101I~
            if (color==0)                                          //~v101I~
                checkTb[ii][jj]=CT_WORKLINE;                       //~v101R~
            else                                                   //~v101I~
            if (color==cc)                                         //~v101I~
                checkTb[ii][jj]=CT_WORKLINE_OFFENSE;               //~v101R~
            else                                                   //~v101I~
            if (P.piece(ii,jj)==PIECE_KING)                        //~v101R~
            {                                                      //~v101I~
//              checkTb[ii][jj]=CT_WORKLINE_KING;                  //~v101R~//~1A0FR~
		    	stackCheck(PIECE_KNIGHT,Pi,Pj,ii,jj);              //~1A0FI~
            }                                                      //~v101I~
		    else                                                   //~v101I~
        	    checkTb[ii][jj]=CT_WORKLINE_DEFENSE;               //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTSilver(int Pi,int Pj,int Pdest)           //~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101I~
    //**********************                                       //~v101I~
    	for (int iii=-1;iii<=1;iii++)                                  //~v101I~
    	{
    		for (int jjj=-1;jjj<=1;jjj++)                              //~v101I~
            {                                                      //~v101I~
            	ii=Pi+iii;                                         //~v101I~
                jj=Pj+jjj;                                         //~v101I~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~v101I~
	                if (iii!=0 || jjj!=0)                          //~v101I~
		                if (!(jjj==0))				//left,right   //~v101I~
		                if (!(jjj==-Pdest && iii==0)) //back         //~v101I~
                        {                                          //~v101I~
                            int color=P.color(ii,jj);              //~v101I~
                            if (color==0)                          //~v101I~
                                checkTb[ii][jj]=CT_WORKLINE;       //~v101R~
                            else                                   //~v101I~
                            if (color==cc)                         //~v101I~
                                checkTb[ii][jj]=CT_WORKLINE_OFFENSE;//~v101R~
                            else                                   //~v101I~
                            if (P.piece(ii,jj)==PIECE_KING)        //~v101R~
                            {                                      //~v101I~
//                              checkTb[ii][jj]=CT_WORKLINE_KING;  //~v101R~//~1A0FR~
						    	stackCheck(PIECE_SILVER,Pi,Pj,ii,jj);//~1A0FI~
                            }                                      //~v101I~
						    else                                   //~v101I~
        	    				checkTb[ii][jj]=CT_WORKLINE_DEFENSE;//~v101I~
                        }                                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTGold(int Pi,int Pj,int Pdest)           //~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101I~
    //**********************                                       //~v101I~
    	for (int iii=-1;iii<=1;iii++)                                  //~v101I~
    	{
    		for (int jjj=-1;jjj<=1;jjj++)                              //~v101I~
            {                                                      //~v101I~
            	ii=Pi+iii;                                         //~v101I~
                jj=Pj+jjj;                                         //~v101I~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~v101I~
	                if (iii!=0 || jjj!=0)                          //~v101I~
		                if (!(jjj==-Pdest && iii!=0))	//left/right back//~v101I~
                        {                                          //~v101I~
                            int color=P.color(ii,jj);              //~v101I~
                            if (color==0)                          //~v101I~
                                checkTb[ii][jj]=CT_WORKLINE;       //~v101R~
                            else                                   //~v101I~
                            if (color==cc)                         //~v101I~
                                checkTb[ii][jj]=CT_WORKLINE_OFFENSE;//~v101R~
                            else                                   //~v101I~
                            if (P.piece(ii,jj)==PIECE_KING)        //~v101R~
                            {                                      //~v101I~
//                              checkTb[ii][jj]=CT_WORKLINE_KING;  //~v101R~//~1A0FR~
						    	stackCheck(PIECE_GOLD,Pi,Pj,ii,jj);//~1A0FI~
                            }                                      //~v101I~
						    else                                   //~v101I~
        	    				checkTb[ii][jj]=CT_WORKLINE_DEFENSE;//~v101I~
                        }                                          //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTKing(int Pi,int Pj)                       //~v101R~
    {                                                              //~v101I~
    	int ii,jj;                                                 //~v101R~
    //**********************                                       //~v101I~
    	for (int iii=-1;iii<=1;iii++)                                  //~v101I~
    	{
    		for (int jjj=-1;jjj<=1;jjj++)                              //~v101I~
            {                                                      //~v101I~
            	ii=Pi+iii;                                         //~v101I~
                jj=Pj+jjj;                                         //~v101I~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~v101I~
	                if (iii!=0 || jjj!=0)                          //~v101R~
                    {                                              //~v101I~
                        int color=P.color(ii,jj);                  //~v101I~
                        if (color==0)                              //~v101I~
                            checkTb[ii][jj]=CT_WORKLINE;           //~v101R~
                        else                                       //~v101I~
                        if (color==cc)                             //~v101I~
                            checkTb[ii][jj]=CT_WORKLINE_OFFENSE;   //~v101R~
                        else                                       //~v101I~
                        if (P.piece(ii,jj)==PIECE_KING)            //~v101R~
                        {                                          //~v101I~
//                          checkTb[ii][jj]=CT_WORKLINE_KING;      //~v101R~//~1A0FR~
						    stackCheck(PIECE_KING,Pi,Pj,ii,jj);    //~1A0FI~
                        }                                          //~v101I~
					    else                                       //~v101I~
        	    			checkTb[ii][jj]=CT_WORKLINE_DEFENSE;   //~v101I~
                    }                                              //~v101I~
            }                                                      //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTBishop(int Pi,int Pj)                     //~v101R~
    {                                                              //~v101I~
        int ii,jj;                                                 //~v101I~
    //**********************                                       //~v101I~
        for (int iii:new int[]{-1,1})                             //~v101M~
        {
    		for (int jjj:new int[]{-1,1})                          //~v101M~
                for (ii=Pi+iii,jj=Pj+jjj;ii>=0 && ii<S && jj>=0 && jj<S;ii+=iii,jj+=jjj)//~v101M~
                {                                                  //~v101M~
                    int color=P.color(ii,jj);                      //~v101I~
                    if (color==0)                                  //~v101I~
                        checkTb[ii][jj]=CT_WORKLINE;               //~v101R~
                    else                                           //~v101I~
                    if (color==cc)                                 //~v101I~
                    {                                              //~v101I~
                        checkTb[ii][jj]=CT_WORKLINE_OFFENSE;       //~v101R~
                        break;                                     //~v101M~
                    }                                              //~v101I~
                    else                                           //~v101I~
                    {                                              //~v101I~
                        if (P.piece(ii,jj)==PIECE_KING)            //~v101R~
                        {                                          //~v101I~
//                          checkTb[ii][jj]=CT_WORKLINE_KING;      //~v101R~//~1A0FR~
						    stackCheck(PIECE_BISHOP,Pi,Pj,ii,jj);  //~1A0FI~
                        }                                          //~v101I~
					    else                                       //~v101I~
        	    			checkTb[ii][jj]=CT_WORKLINE_DEFENSE;   //~v101I~
                    	break;                                     //~v101I~
                    }                                              //~v101I~
                }                                                  //~v101M~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTPBishop(int Pi,int Pj)                    //~v101R~
    {                                                              //~v101I~
		setCTBishop(Pi,Pj);                                        //~v101R~
		setCTKing(Pi,Pj);                                          //~v101R~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTRook(int Pi,int Pj)                       //~v101R~
    {                                                              //~v101I~
        int ii,jj;                                                 //~v101I~
    //**********************                                       //~v101I~
        int[][] iijj=new int[][]{ {1,0},{-1,0},{0,1},{0,-1}};      //~v101I~
        for (int kk=0;kk<4;kk++)                                   //~v101I~
        {                                                          //~v101I~
            int iii=iijj[kk][0];                                   //~v101I~
            int jjj=iijj[kk][1];                                   //~v101I~
            for (ii=Pi+iii,jj=Pj+jjj;ii>=0 && ii<S && jj>=0 && jj<S;ii+=iii,jj+=jjj)//~v101R~
            {                                                      //~v101R~
                int color=P.color(ii,jj);                          //~v101I~
                if (color==0)                                      //~v101I~
                	checkTb[ii][jj]=CT_WORKLINE;                   //~v101R~
				else                                               //~v101I~
				if (color==cc)                                     //~v101I~
                {                                                  //~v101I~
                	checkTb[ii][jj]=CT_WORKLINE_OFFENSE;           //~v101R~
                    break;                                         //~v101R~
                }                                                  //~v101I~
                else                                               //~v101I~
                {                                                  //~v101I~
                    if (P.piece(ii,jj)==PIECE_KING)                //~v101R~
                    {                                              //~v101I~
//                      checkTb[ii][jj]=CT_WORKLINE_KING;          //~v101R~//~1A0FR~
						stackCheck(PIECE_ROOK,Pi,Pj,ii,jj);        //~1A0FI~
                    }                                              //~v101I~
				    else                                           //~v101I~
            			checkTb[ii][jj]=CT_WORKLINE_DEFENSE;       //~v101I~
                    break;                                         //~v101I~
                }                                                  //~v101I~
            }                                                      //~v101R~
        }                                                          //~v101I~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
	private void setCTPRook(int Pi,int Pj)                    //~v101R~
    {                                                              //~v101I~
		setCTRook(Pi,Pj);                                          //~v101R~
		setCTKing(Pi,Pj);                                          //~v101R~
    }                                                              //~v101I~
//**************************************************************** //~v101I~
//*moved piece is not there,dropped piece is thre                  //~v101I~
//**************************************************************** //~v101I~
    private void getDescWord()                                    //~v101I~
    {                                                              //~v101I~
    	Sup=AG.resource.getString(R.string.MoveDescUp);                //~v101I~
    	Sdown=AG.resource.getString(R.string.MoveDescDown);            //~v101I~
    	Sleft=AG.resource.getString(R.string.MoveDescLeft);            //~v101I~
    	Sright=AG.resource.getString(R.string.MoveDescRight);          //~v101I~
    	Shorizontal=AG.resource.getString(R.string.MoveDescHorizontal);//~v101I~
    	Svertical=AG.resource.getString(R.string.MoveDescVertical);
    	Sdropped=AG.resource.getString(R.string.MoveDropped);//~v101I~
    	Ssame=AG.resource.getString(R.string.MoveSame);            //~v101I~
    	Sleftup=AG.resource.getString(R.string.MoveLeftUp);        //~v101I~
    	Srightup=AG.resource.getString(R.string.MoveRightUp);      //~v101I~
    	Sleftdown=AG.resource.getString(R.string.MoveLeftDown);    //~v101I~
    	Srightdown=AG.resource.getString(R.string.MoveRightDown);
    	Spromoted=AG.resource.getString(R.string.MovePromoted);//~v101I~
    	Snotpromoted=AG.resource.getString(R.string.MoveNotPromoted);//~v101I~
		Spiecenamemove=AG.resource.getStringArray(R.array.PieceNameMove);//~v101I~
    }                                                              //~v101I~
//************************************************************     //~v101I~
	public String movePieceName(int Ppiece)                        //~v101I~
	{                                                              //~v101I~
    	String n="";                                               //~v101I~
        if (Ppiece>=PIECE_PAWN && Ppiece<=PIECE_KING_CHALLENGING)  //~v101I~
        {                                                          //~v101I~
        	if (Ppiece==PIECE_KING)                                //~v101I~
        		n=Spiecenamemove[PIECE_KING_CHALLENGING-PIECE_PAWN]; //use gyoku on kifu//~v101I~
            else                                                   //~v101I~
        		n=Spiecenamemove[Ppiece-PIECE_PAWN];               //~v101R~
        }                                                          //~v101I~
        else                                                       //~v101I~
        if (Ppiece>=PIECE_PPAWN && Ppiece<=PIECE_PROOK)            //~v101I~
        {                                                          //~v101I~
        	n=Spiecenamemove[Ppiece-PIECE_PPAWN+PIECE_KING_CHALLENGING];//~v101R~
        }                                                          //~v101I~
        return n;                                                  //~v101I~
	}                                                              //~v101I~
    //*****************************************************************//~1A0hI~
    public String moveDescription(int Ppiece,int Pi/*to*/,int Pj)  //~1A0hI~
    {                                                              //~1A0hI~
    	String pos,piece;                                          //~1A0hI~
    //***********************************                          //~1A0hI~
        piece=movePieceName(Ppiece);                               //~1A0hI~
        pos=movePositionDescription(Pi,Pj);                        //~1A0hI~
        return pos+piece;                                          //~1A0hI~
    }                                                              //~1A0hI~
    //*****************************************************************//~v101I~
    public String moveDescription(int Ppiececolor,int Ppiece,boolean Pdropped,int Pi1/*from*/,int Pj1,int Pi2/*to*/,int Pj2)//~v101I~
    {                                                              //~v101I~
    	String desc,pos,piece,move;                                //~v101I~
    //***********************************                          //~v101I~
        swSame=false;                                              //~v101I~
        piece=movePieceName(Ppiece);                               //~v101R~
        pos=movePositionDescription(Pi2,Pj2);        //~v101I~
        int promoted=P.piece(Pi2,Pj2);                             //~v101I~
        String promotion;                                          //~v101I~
        if (promoted>Ppiece)                                       //~v101I~
            promotion=Spromoted;                             //~v101I~
        else                                                       //~v101I~
        if (notPromoted)                                           //~v101I~
            promotion=Snotpromoted;                                //~v101I~
        else                                                       //~v101I~
            promotion="";                                          //~v101I~
        if (AG.isLangJP)                                           //~v101I~
        {                                                          //~v101I~
        	move=moveDescriptionJP(Ppiececolor,Ppiece,Pdropped,Pi1,Pj1,Pi2,Pj2);//~v101I~
        	desc=pos+piece+move+promotion;                         //~v101I~
        }                                                          //~v101I~
        else                                                       //~v101I~
        {                                                          //~v101I~
    	//* (Piece)(move/-)(pos)(promotion)                        //~v101I~
        	move=moveDescriptionEn(Ppiececolor,Ppiece,Pdropped,Pi1,Pj1,Pi2,Pj2);//~v101I~
        	desc=piece+move+pos+promotion;                         //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("moveDescription desc="+desc);     //~v101I~
        return desc;                                               //~v101I~
    }                                                              //~v101I~
    //*****************************************************************//~v101I~
    public String movePositionDescription(int Pi,int Pj)           //~v101I~
    {                                                              //~v101I~
    	String pos;                                                //~v101I~
    	if (Pi==prevPosI && Pj==prevPosJ)                          //~v101I~
        	if (AG.isLangJP)                                       //~v101I~
        		pos=Ssame;                                         //~v101R~
            else                                                   //~v101I~
            {                                                      //~v101I~
            	swSame=true;                                       //~v101I~
	        	pos=Field.coordinate(Pi,Pj,S,Col);                 //~v101I~
            }                                                      //~v101I~
        else                                                       //~v101I~
        	pos=Field.coordinate(Pi,Pj,S,Col);                     //~v101I~
        prevPosI=Pi;                                               //~v101I~
        prevPosJ=Pj;                                               //~v101I~
        if (Dump.Y) Dump.println("movePositionDescription pos="+pos);//~v101I~
        return pos;                                                //~v101I~
    }                                                              //~v101I~
    //************************                                     //~v101I~
    public String moveDescriptionEn(int Ppiececolor,int Ppiece,boolean Pdropped,int Pi1/*from*/,int Pj1,int Pi2/*to*/,int Pj2)//~v101I~
    {                                                              //~v101I~
    	int ii,jj,color,dupctr=0,piece;                       //~v101I~//~1A0FR~
    	String desc="-";                                           //~v101I~
    //************************                                     //~v101I~
        if (Dump.Y) Dump.println("Rules:moveDescriptionEn color="+Ppiececolor+",piece="+Ppiece+",("+Pi1+","+Pj1+")-->("+Pi2+","+Pj2+")");//~v101R~
        if (swSame)                                                //~v101I~
            desc=Ssame;                                            //~v101I~
        if (Ppiece==PIECE_PAWN)   //2pawn prohibit,no ambiguity    //~v101I~
            return desc;                                           //~v101I~
//        dest=(Col==Ppiececolor)?-1:1;                              //~v101I~//~1A0FR~
        for (jj=0;jj<S;jj++)                                       //~v101I~
        	for (ii=0;ii<S;ii++)                                   //~v101I~
            {                                                      //~v101I~
            	color=P.color(ii,jj);                              //~v101I~
//              if (color!=Col)                                    //~v101R~
                if (color!=Ppiececolor)                            //~v101I~
                	continue;                                      //~v101I~
            	piece=P.piece(ii,jj);                              //~v101I~
                if (piece!=Ppiece)                                 //~v101I~
                	continue;                                      //~v101I~
                if (ii==Pi2 && jj==Pj2)  //after moved or dropped  //~v101I~
                	continue;                                      //~v101I~
				if (isOnPiecePath(false,Ppiececolor,Pi2,Pj2,ii,jj)!=1)//~v101R~//~1A0FR~
                	continue;                                      //~v101I~
                dupctr++;                                          //~v101I~
                break;                                             //~v101I~
//                descWorkTb[dupctr++]=ii;                         //~v101I~
//                descWorkTb[dupctr++]=jj;                         //~v101I~
            }                                                      //~v101I~
//        dupctr/=2;                                               //~v101I~
//        for (;;)                                                 //~v101I~
//        {                                                        //~v101I~
//            if (dupctr==0)                                       //~v101I~
//                break;                                           //~v101I~
//            if (Pdropped)                                        //~v101I~
//            {                                                    //~v101I~
//                desc=Sdropped;                                   //~v101I~
//                break;                                           //~v101I~
//            }                                                    //~v101I~
//            if (Ppiece==PIECE_PAWN)   //for lance only 2 case dropped or upped//~v101I~
//                break;                                           //~v101I~
//            desc=chkStandBy(Ppiece,dest,dupctr,Pi1,Pj1,Pi2,Pj2); //~v101I~
//            break;                                               //~v101I~
//        }                                                        //~v101I~
		if (dupctr!=0)                                             //~v101I~
        {                                                          //~v101I~
            if (Pdropped)                                          //~v101I~
                desc=Sdropped;                                     //~v101I~
            else                                                   //~v101I~
        		desc=movePositionDescription(Pi1,Pj1)+desc;	//from position//~v101R~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("Rules:moveDescriptionEn rc="+desc);//~v101I~
        return desc;                                               //~v101I~
    }                                                              //~v101I~
    //************************                                     //~v101I~
    public String moveDescriptionJP(int Ppiececolor,int Ppiece,boolean Pdropped,int Pi1/*from*/,int Pj1,int Pi2/*to*/,int Pj2)//~v101R~
    {                                                              //~v101I~
    	int ii,jj,dest,color,dupctr=0,piece;                            //~v101I~
    	String desc="";                                            //~v101I~
    //************************                                     //~v101I~
        if (Dump.Y) Dump.println("Rules:moveDescriptionJP color="+Ppiececolor+",piece="+Ppiece+",("+Pi1+","+Pj1+")-->("+Pi2+","+Pj2+")");//~v101R~
        if (Ppiece==PIECE_PAWN)   //2pawn prohibit,no ambiguity    //~v101I~
            return desc;                                           //~v101I~
        dest=(Col==Ppiececolor)?-1:1;                              //~v101I~
        for (jj=0;jj<S;jj++)                                       //~v101I~
        	for (ii=0;ii<S;ii++)                                   //~v101I~
            {                                                      //~v101I~
            	color=P.color(ii,jj);                              //~v101I~
//              if (color!=Col)                                    //~v101R~
                if (color!=Ppiececolor)                            //~v101I~
                	continue;                                      //~v101I~
            	piece=P.piece(ii,jj);                              //~v101I~
                if (piece!=Ppiece)                                 //~v101I~
                	continue;                                      //~v101I~
                if (ii==Pi2 && jj==Pj2)  //after moved or dropped  //~v101I~
                	continue;                                      //~v101I~
				if (isOnPiecePath(false,Ppiececolor,Pi2,Pj2,ii,jj)!=1)//~v101R~//~1A0FR~
                	continue;                                      //~v101I~
		        descWorkTb[dupctr++]=ii;                           //~v101I~
		        descWorkTb[dupctr++]=jj;                           //~v101I~
            }                                                      //~v101I~
        dupctr/=2;                                                 //~v101I~
        for (;;)                                                   //~v101I~
        {                                                          //~v101I~
        	if (dupctr==0)                                         //~v101I~
            	break;                                             //~v101I~
            if (Pdropped)                                          //~v101I~
            {                                                      //~v101I~
            	desc=Sdropped;                                     //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
	        if (Ppiece==PIECE_PAWN)   //for lance only 2 case dropped or upped//~v101I~
	            break;                                             //~v101I~
			desc=chkStandBy(Ppiece,dest,dupctr,Pi1,Pj1,Pi2,Pj2);          //~v101I~
        	break;                                                 //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("Rules:moveDescription rc="+desc);//~v101I~
        return desc;                                               //~v101I~
    }                                                              //~v101I~
    //************************************************************ //~v101I~
    private String chkStandBy(int Ppiece,int Pdest,int Pdupctr,int Pi1/*from*/,int Pj1,int Pi2/*to*/,int Pj2)//~v101I~
    {                                                              //~v101I~
    	int standby0,standby=0;                                   //~v101I~
    	standby0=chkStandBySub(Pdest,Pi1,Pj1,Pi2,Pj2);              //~v101I~
        for (int kk=0;kk<Pdupctr;kk++)                             //~v101I~
        {                                                          //~v101I~
            int ii=descWorkTb[kk*2];                                   //~v101I~
            int jj=descWorkTb[kk*2+1];                                 //~v101I~
            standby|=chkStandBySub(Pdest,ii,jj,Pi2,Pj2);           //~v101I~
        }                                                          //~v101I~
        return chkStandBy(Ppiece,standby0,standby);                       //~v101I~
    }                                                              //~v101I~
    //************************************************************ //~v101I~
    private int chkStandBySub(int Pdest,int Pi1/*from*/,int Pj1,int Pi2/*to*/,int Pj2)//~v101I~
    {                                                              //~v101I~
    	int standby=0;                                             //~v101I~
        if (Pi1==Pi2)                                              //~v101I~
        	standby|=SB_VERTICAL;                                  //~v101I~
        else                                                       //~v101I~
        if (Pi1>Pi2)                                               //~v101I~
        	standby|=Pdest<0 ? SB_RIGHT :SB_LEFT;                  //~v101I~
        else                                                       //~v101I~
        	standby|=Pdest>0 ? SB_RIGHT :SB_LEFT;                  //~v101I~
                                                                   //~v101I~
        if (Pj1==Pj2)                                              //~v101I~
        	standby|=SB_HORIZONTAL;                                //~v101I~
        else                                                       //~v101I~
        if (Pj1>Pj2)                                               //~v101I~
        	standby|=Pdest<0 ? SB_UP :SB_DOWN;                     //~v101I~
        else                                                       //~v101I~
        	standby|=Pdest>0 ? SB_UP :SB_DOWN;                     //~v101I~
        if (Dump.Y) Dump.println("chkStandBySub rc="+Integer.toHexString(standby)+",("+Pi1+","+Pj1+")-->("+Pi2+","+Pj2+")");//~v101R~
        return standby;                                            //~v101I~
    }                                                              //~v101I~
    //************************************************************ //~v101I~
    private String chkStandBy(int Ppiece,int Pstandby0,int Pstandby)//~v101I~
    {                                                              //~v101I~
    	String desc="";                                            //~v101I~
    	for (;;)                                                   //~v101I~
        {                                                          //~v101I~
        	if (Ppiece==PIECE_KNIGHT)	//left or right only       //~v101I~
            {                                                      //~v101I~
            	desc=(Pstandby0 & SB_RIGHT)!=0?Sright:Sleft;       //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
            if (Ppiece==PIECE_PBISHOP||Ppiece==PIECE_PROOK)	//no vertical//~v101I~
			    return chkStandByBR(Ppiece,Pstandby0,Pstandby);    //~v101I~
        	if ((Pstandby0 & SB_HORIZONTAL)!=0)                         //~v101I~
            {                                                      //~v101I~
        		if ((Pstandby & SB_HORIZONTAL)!=0)                       //~v101I~
                	desc=(Pstandby0 & SB_RIGHT)!=0?Sright:Sleft;      //~v101I~
                else                                               //~v101I~
	                desc=Shorizontal;	//yori                     //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        	if ((Pstandby0 & SB_UP)!=0)                                 //~v101I~
            {                                                      //~v101I~
                if ((Pstandby0 & SB_VERTICAL)!=0)                  //~v101I~
                {                                                  //~v101I~
                    if ((Pstandby & SB_UP)!=0) //other up          //~v101I~
                        desc=Svertical;                            //~v101I~
                    else                                           //~v101I~
                        desc=Sup;   //only one Up                  //~v101I~
                    break;                                         //~v101I~
                }                                                  //~v101I~
        		if ((Pstandby & SB_UP)!=0)	//other up             //~v101R~
                {                                                  //~v101I~
        			if ((Pstandby0 & SB_LEFT)!=0)  //left up       //~v101R~
	                	desc=(Pstandby & SB_LEFT)!=0?Sleftup:Sleft;//~v101R~
                    else                       //right up          //~v101R~
	                	desc=(Pstandby & SB_RIGHT)!=0?Srightup:Sright;//~v101R~
                }                                                  //~v101I~
                else                                               //~v101I~
	            	desc=Sup;                                      //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        	if ((Pstandby0 & SB_DOWN)!=0)                          //~v101I~
            {                                                      //~v101I~
                if ((Pstandby0 & SB_VERTICAL)!=0)                  //~v101I~
                {                                                  //~v101I~
                    desc=Sdown;                                    //~v101I~
                    break;                                         //~v101I~
                }                                                  //~v101I~
                //not vertical,silver only                         //~v101I~
        		if ((Pstandby & SB_DOWN)!=0)                       //~v101R~
                {                                                  //~v101I~
        			if ((Pstandby0 & SB_LEFT)!=0)  //left down     //~v101R~
	                	desc=(Pstandby & SB_LEFT)!=0?Sleftdown:Sleft;//~v101I~
                    else                       //right down        //~v101R~
	                	desc=(Pstandby & SB_RIGHT)!=0?Srightdown:Sright;//~v101I~
                }                                                  //~v101I~
                else                                               //~v101I~
	            	desc=Sdown;                                    //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
            break;                                                 //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("chkStandBy rc="+desc+",stb0="+Integer.toString(Pstandby0)+",stb="+Integer.toHexString(Pstandby));//~v101R~
        return desc;                                               //~v101I~
    }                                                              //~v101I~
    //************************************************************ //~v101I~
    private String chkStandByBR(int Ppiece,int Pstandby0,int Pstandby)//~v101I~
    {                                                              //~v101I~
    	String desc="";                                            //~v101I~
    	for (;;)                                                   //~v101I~
        {                                                          //~v101I~
        	if ((Pstandby0 & SB_HORIZONTAL)!=0)                    //~v101I~
            {                                                      //~v101I~
        		if ((Pstandby & SB_HORIZONTAL)!=0)                 //~v101I~
                {                                                  //~v101I~
                	desc=(Pstandby0 & SB_RIGHT)!=0?Sright:Sleft;   //~v101I~
                    break;                                         //~v101I~
                }                                                  //~v101I~
                desc=Shorizontal;	//yori                         //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
        	if ((Pstandby0 & SB_UP)!=0 && (Pstandby & SB_UP)!=0   //~v101I~
        	||  (Pstandby0 & SB_DOWN)!=0 && (Pstandby & SB_DOWN)!=0)//~v101I~
            {                                                      //~v101I~
        		if ((Pstandby0 & SB_RIGHT)!=0)                            //~v101I~
	            	desc=Sright;                                   //~v101I~
                else                                               //~v101I~
        		if ((Pstandby0 & SB_LEFT)!=0)                             //~v101I~
	               	desc=Sleft;                                    //~v101I~
                else                                               //~v101I~
	            	desc=(Pstandby & SB_RIGHT)!=0?Sleft:Sright;    //~v101I~
                break;                                             //~v101I~
            }                                                      //~v101I~
	        desc=(Pstandby0 & SB_UP)!=0?Sup:Sdown;                 //~v101R~
            break;                                                 //~v101I~
        }                                                          //~v101I~
        if (Dump.Y) Dump.println("chkStandByBR rc="+desc);         //~v101I~
        return desc;                                               //~v101I~
    }                                                              //~v101I~
    //*******************************************************      //~1A0FI~
    //*stack checking piece                                        //~1A0FI~
    //*******************************************************      //~1A0FI~
    private void stackCheck(int Ppiece,int Pi/*piece pos*/,int Pj,int Piking,int Pjking)//~1A0FI~
    {                                                              //~1A0FI~
        iKingCheck=Piking; jKingCheck=Pjking;                                //~1A0FI~
        checkTb[iKingCheck][jKingCheck]=CT_WORKLINE_KING;                    //~1A0FI~
    	if (checkStackCtr>=MAX_CHECK)                              //~1A0FI~
        	return;                                                //~1A0FI~
    	checkStack_i[checkStackCtr]=Pi;                            //~1A0FI~
    	checkStack_j[checkStackCtr]=Pj;                            //~1A0FI~
    	checkStack_piece[checkStackCtr]=Ppiece;                    //~1A0FI~
    	checkStackCtr++;                                           //~1A0FI~
        kingCheck=true;                                            //~1A0FI~
    }                                                              //~1A0FI~
//**************************************************************** //~1A0FI~
//*avoid checkmate by king move                                    //~1A0FI~
//*rc:true if movable                                              //~1A0FI~
//**************************************************************** //~1A0FI~
	private boolean checkmateMove(int Pcolor/*offense*/,int Piking,int Pjking)//~1A0FI~
    {                                                              //~1A0FI~
        boolean rc=false;      //not movable                       //~1A0FI~
        int ii,jj,colidx;               //~1A0FI~
     //***********************************                          //~1A0FI~
        if (Dump.Y) Dump.println("checkmateMove color="+Pcolor+",king=("+Piking+","+Pjking+")");//~1A0FI~
        cc=Pcolor;                                                 //~1A0FI~
        colidx=(cc==Col)?1:0;	//lower List(1) if current is my color//~1A0FI~
        checkTb=offenseTb;                                         //~1A0FI~
    	for (int iii=-1;iii<=1;iii++)                              //~1A0FI~
    	{                                                          //~1A0FI~
    		for (int jjj=-1;jjj<=1;jjj++)                          //~1A0FI~
            {                                                      //~1A0FI~
            	ii=Piking+iii;                                         //~1A0FI~
                jj=Pjking+jjj;                                         //~1A0FI~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~1A0FI~
	                if (iii!=0 || jjj!=0)   //not king             //~1A0FI~
                    {                                              //~1A0FI~
                        int check=checkTb[ii][jj];                 //~1A0FI~
					    if (Dump.Y) Dump.println("checkmateMove offense checktb ("+ii+","+jj+")="+check);//~1A0FR~
                        switch(check)                              //~1A0FI~
                        {                                          //~1A0FI~
                        case CT_FREE:              //empty         //~1A0FI~
                        case CT_OFFENSE:           //not supported offense//~1A0FI~
					        if (Dump.Y) Dump.println("isCheckmate false free at ("+ii+","+jj+")");//~1A0FI~
							setCTMoveCheck(testTb,colidx,PIECE_KING,ii/*move to*/,jj,Piking/*move from*/,Pjking);//~1A0FI~
                            if (!kingCheck)                        //~1A0FI~
                            {                                      //~1A0FI~
								if (Dump.Y) Dump.println("checkmateMove rc=true");//~1A0FI~
                            	return true;	//moveable         //~1A0FI~
                            }                                      //~1A0FI~
                        	break;                                 //~1A0FI~
                        }
                    }    //~1A0FI~
            }                                                      //~1A0FI~
        }                                                          //~1A0FI~
		if (Dump.Y) Dump.println("checkmateMove rc="+rc);          //~1A0FI~
		return rc;                                                 //~1A0FI~
    }                                                              //~1A0FI~
//**************************************************************** //~1A0FI~
//*check path to checking piece                                    //~1A0FI~
//**************************************************************** //~1A0FI~
	private boolean moveTest(int Pi/*checking piece*/,int Pj,int Pcolor/*defense*/)//~1A0FI~
	{                                                              //~1A0FI~
    	boolean rc=false;                                          //~1A0FI~
    //*******************************                              //~1A0FI~
		if (Dump.Y) Dump.println("movetest defense color="+Pcolor+",piece at ("+Pi+","+Pj+")");//~1A0FI~
    	for (int ii=0;ii<S;ii++)                                   //~1A0FI~
    	{                                                          //~1A0FI~
    		for (int jj=0;jj<S;jj++)                               //~1A0FI~
            {                                                      //~1A0FI~
            	int color=P.color(ii,jj);                          //~1A0FI~
                if (color!=Pcolor)                                 //~1A0FR~
                	continue;                                      //~1A0FI~
            	int piece=P.piece(ii,jj);                          //~1A0FI~
                if (piece==PIECE_KING)                             //~1A0FI~
                	continue;                                      //~1A0FI~
        		int colidx=(Pcolor==Col)?1:0;	//lower List(1) if current is my color//~1A0FI~
				if (isOnPiecePath(false/*no errmsg*/,color,Pi/*move to*/,Pj,ii/*from*/,jj)==1)//~1A0FR~
                {                                                  //~1A0FI~
					if (Dump.Y) Dump.println("moveTest true piece="+piece+",from at ("+ii+","+jj+")");//~1A0FI~
					setCTMoveCheck(testTb,colidx,piece,Pi/*move to*/,Pj,ii/*move from*/,jj);//~1A0FI~
                    if (Dump.Y) Dump.println("moveTest kingcheck="+kingCheck);//~1A0FI~
                    rc=!kingCheck;                                 //~1A0FI~
                    if (rc)	//movable without other check       //~1A0FI~
	                	return true;                                    //~1A0FI~
                }                                                  //~1A0FI~
            }                                                      //~1A0FI~
        }                                                          //~1A0FI~
        if (Dump.Y) Dump.println("moveTest rc="+rc);               //~1A0FI~
        return rc;                                                 //~1A0FI~
    }                                                              //~1A0FI~
//**************************************************************** //~1A0FI~
//*avoid checkmate by capture checking piece                       //~1A0FI~
//*rc:true if checking piece is capturable                         //~1A0FI~
//**************************************************************** //~1A0FI~
	private boolean checkmateCapture(int Pcolor/*offense*/,int Pstackctr)//~1A0FI~
    {                                                              //~1A0FI~
        boolean rc=false;      //not movable                       //~1A0FI~
        int ii,jj;               //~1A0FI~
    //***********************************                          //~1A0FI~
        if (Dump.Y) Dump.println("checkmateCapture color="+Pcolor+",ctr="+Pstackctr);//~1A0FI~
        if (Pstackctr>1)	                                       //~1A0FI~
        {                                                          //~1A0FI~
	        if (Dump.Y) Dump.println("checkmateCapture false by multiple ctr");//~1A0FI~
            return false;	//checkmate                            //~1A0FI~
        }                                                          //~1A0FI~
        ii=checkStack_icopy[0];                                    //~1A0FI~
        jj=checkStack_jcopy[0];                                    //~1A0FI~
	    if (Dump.Y) Dump.println("checkmateCapture checking pos=("+ii+","+jj+") piece="+checkStack_piececopy[0]);//~1A0FI~
		rc=moveTest(ii/*checking piece*/,jj,-Pcolor/*defense*/);   //~1A0FI~
		if (Dump.Y) Dump.println("checkmateCapture rc="+rc);       //~1A0FI~
		return rc;                                                 //~1A0FI~
    }                                                              //~1A0FI~
//**************************************************************** //~1A0FI~
//*avoid checkmate by king move                                    //~1A0FI~
//*rc:true if inteceptable                                         //~1A0FI~
//**************************************************************** //~1A0FI~
	private boolean checkmateIntercept(int Pcolor/*offense*/,int Pstackctr,int Piking,int Pjking)//~1A0FI~
    {                                                              //~1A0FI~
        int ii,jj,iii,jjj,stepi,stepj,piece;               //~1A0FI~
        boolean rc=false;      //checkmate                         //~1A0FR~
    //***********************************                          //~1A0FI~
        if (Dump.Y) Dump.println("checkmateIntercept color="+Pcolor+",ctr="+Pstackctr);//~1A0FI~
        if (Pstackctr>1)                                           //~1A0FI~
        {                                                          //~1A0FI~
	        if (Dump.Y) Dump.println("checkmateIntercept false by multiple ctr");//~1A0FI~
            return false;	//checkmate                            //~1A0FI~
        }                                                          //~1A0FI~
        int trayctr=CGF.aCapturedList.getCapturedCount(-Pcolor,capturedList);//~1A0FR~
        piece=checkStack_piececopy[0];                             //~1A0FI~
        ii=checkStack_icopy[0];                                    //~1A0FI~
        jj=checkStack_jcopy[0];                                    //~1A0FI~
        if (Dump.Y) Dump.println("checkmateIntercept checking trayctr="+trayctr+",piece="+piece+" at("+ii+","+jj+")");//~1A0FI~
        switch(piece)                                              //~1A0FI~
        {                                                          //~1A0FI~
        case PIECE_ROOK:                                           //~1A0FI~
        case PIECE_PROOK:                                          //~1A0FI~
        	if (ii==Piking)   //vertical                           //~1A0FI~
            {	                                                   //~1A0FI~
            	stepi=0; stepj=jj<Pjking?-1:1;                     //~1A0FI~
                iii=Piking; jjj=Pjking+stepj;                      //~1A0FI~
            }                                                      //~1A0FI~
            else               //horizontal                        //~1A0FI~
            {                                                      //~1A0FI~
            	stepj=0;stepi=ii<Piking?-1:1;                      //~1A0FI~
                jjj=Pjking; iii=Piking+stepi;                      //~1A0FI~
            }                                                      //~1A0FI~
        	break;                                                 //~1A0FI~
        case PIECE_BISHOP:                                         //~1A0FI~
        case PIECE_PBISHOP:                                       //~1A0FI~
            stepi=ii<Piking?-1:1;                                  //~1A0FI~
            stepj=jj<Pjking?-1:1;                                  //~1A0FI~
            iii=Piking+stepi; jjj=Pjking+stepj;                    //~1A0FI~
        	break;                                                 //~1A0FI~
        default:                                                   //~1A0FI~
        	return false;      //not interceptable                 //~1A0FR~
        }                                                          //~1A0FI~
        for (;iii>=0 && iii <S && jjj>=0 && jjj<S;iii+=stepi,jjj+=stepj)//~1A0FI~
        {                                                          //~1A0FI~
        	if (iii==ii && jjj==jj)	//checking piece pos           //~1A0FI~
            	break;                                             //~1A0FI~
            if (trayctr!=0)  //defence has intercepting piece      //~1A0FI~
            {                                                      //~1A0FI~
                if (Dump.Y) Dump.println("chkmateIntercept by tray piece");//~1A0FI~
                if (checkDropInterceptable(iii,jjj,-Pcolor,capturedList))//~1A0FR~
                {                                                  //~1A0FI~
                	rc=true;                                       //~1A0FI~
                	break;                                         //~1A0FR~
                }                                                  //~1A0FI~
            }                                                      //~1A0FI~
            if (moveTest(iii,jjj,-Pcolor))                         //~1A0FI~
            {                                                      //~1A0FI~
                if (Dump.Y) Dump.println("chkmateIntercept intercept at ("+iii+","+jjj+")");//~1A0FR~
                rc=true;                                           //~1A0FI~
                break;                                             //~1A0FI~
            }                                                      //~1A0FI~
        }                                                          //~1A0FI~
		if (Dump.Y) Dump.println("checkmateIntercept rc="+rc);     //~1A0FR~
		return rc;                                                 //~1A0FI~
    }                                                              //~1A0FI~
//**************************************************************** //~1A0FI~
//*rc=true:droppable                                               //~1A0FR~
//**************************************************************** //~1A0FI~
	public boolean checkDropInterceptable(int Pi,int Pj,int Pcolor/*defence*/,int[] Pcapturedlist)//~1A0FR~
    {                                                              //~1A0FI~
		int ii,jj,colidx,piece;                                    //~1A0FR~
        boolean rc=false;                                          //~1A0FI~
    //***********************************                          //~1A0FI~
		if (Dump.Y) Dump.println("chkDropInterceptable drop to ("+Pi+","+Pj+") color="+Pcolor);//~1A0FI~
        colidx=(Pcolor==Col)?1:0;	//lower List(1) if current is my color//~1A0FR~
        for (ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)                  //~1A0FR~
        {                                                          //~1A0FI~
        	int ctr=Pcapturedlist[ii];                                 //~1A0FI~
            if (ctr==0)                                             //~1A0FI~
            	continue;                                          //~1A0FI~
            piece=MAX_PIECE_TYPE_CAPTURE-ii;	//piecetype        //~1A0FI~
			if (Dump.Y) Dump.println("chkDropInterceptable tray piece="+piece);//~1A0FI~
			if (!chkDropPosition(Pi,Pj,piece,colidx))	//unmovable drop//~1A0FI~
            {                                                      //~1A0FI~
				if (Dump.Y) Dump.println("chkDropInterceptable unmovabledrop");//~1A0FI~
        		continue;                                          //~1A0FI~
            }                                                      //~1A0FI~
            if (piece==PIECE_PAWN)                                //~1A0FI~
            {                                                      //~1A0FI~
            //* 2 pawn chk in th same column                       //~1A0FI~
                for (jj=0;jj<S;jj++)                               //~1A0FI~
                {                                                  //~1A0FI~
                    if (P.piece(Pi,jj)==PIECE_PAWN && P.color(Pi,jj)==Pcolor)//~1A0FR~
                        break;                                     //~1A0FI~
                }                                                  //~1A0FI~
                if (jj<S)	//2pawn                                //~1A0FI~
                {                                                  //~1A0FI~
					if (Dump.Y) Dump.println("chkDropInterceptable 2pawn");//~1A0FI~
	        		continue;                                      //~1A0FI~
                }                                                  //~1A0FI~
            	//*chakmate by dropped pawn is not checked;there may be no need//~1A0FR~
            }                                                      //~1A0FI~
            rc=true;                                               //~1A0FI~
        }                                                          //~1A0FI~
		if (Dump.Y) Dump.println("chkDropInterceptable rc="+rc);   //~1A0FI~
		return rc;                                                 //~1A0FI~
    }                                                              //~1A0FI~
//**************************************************************** //~1A15I~
//*chk foul at FB start
//return true if ok
//**************************************************************** //~1A15I~
	public boolean chkFoulFB(int Pcolor)                           //~1A15I~
    {                                                              //~1A15I~
    //***********************************                          //~1A15I~
		if (Dump.Y) Dump.println("chkFoulFB");
		if (!chk2PawnFB())
			return false;
		if (!isCheckFB(Pcolor))                                    //~1A15I~
			return false;                                          //~1A15I~
		if (Dump.Y) Dump.println("chkFoulFB true");                //~1A15I~
		return true;                                               //~1A15R~
    }                                                              //~1A15I~
//**************************************************************** //~1A15I~
//return true if ok
//**************************************************************** //~1A15I~
	public boolean chk2PawnFB()                                    //~1A15I~
    {                                                              //~1A15I~
        for (int col:new int[]{-1,1})                              //~1A15I~
        {                                                          //~1A15I~
            for (int ii=0;ii<S;ii++)                                   //~1A15I~
            {                                                      //~1A15I~
                int ctr=0;                                         //~1A15I~
                for (int jj=0;jj<S;jj++)                               //~1A15I~
                {                                                  //~1A15I~
                    if (P.piece(ii,jj)==PIECE_PAWN && P.color(ii,jj)==col)//~1A15I~
                    {                                              //~1A15I~
                        if (ctr!=0)                                //~1A15I~
                        {                                          //~1A15I~
					        String pos=Field.coordinate(ii,jj,S,col);//~1A15I~
                            String msg=AG.resource.getString(R.string.Err2PawnFB,pos);//~1A15I~
				            CGF.setLabel(msg,false/*no append*/);  //~1A15I~
                        	return false;                          //~1A15R~
                        }                                          //~1A15I~
                        ctr++;                                      //~1A15I~
                    }                                              //~1A15I~
                }                                                  //~1A15I~
            }                                                      //~1A15I~
        }                                                          //~1A15I~
        return true;                                               //~1A15R~
    }                                                              //~1A15I~
//**************************************************************** //~1A15I~
//*isCheck on FB                                                   //~1A15I~
//return true if ok                                                //~1A15I~
//**************************************************************** //~1A15I~
	public boolean isCheckFB(int Pcolor)                           //~1A15I~
    {                                                              //~1A15I~
        cc=Pcolor;                                                 //~1A15I~
        int colidx=(cc==Col)?1:0;	//lower List(1) if current is my color//~1A15I~
        if (Dump.Y) Dump.println("isCheckFB move color="+cc+",idx="+colidx);//~1A15I~
    	setCT(offenseTb,colidx);                                   //~1A15I~
		if (Dump.Y) Dump.println("isCheckFB="+kingCheck);          //~1A15I~
        if (kingCheck)                                             //~1A15I~
        {                                                          //~1A15I~
			CGF.setLabel(R.string.ErrCheckFB,false/*no append*/);  //~1A15I~
            return false;                                          //~1A15I~
        }                                                          //~1A15I~
        return true;                                               //~1A15I~
    }                                                              //~1A15I~
}
