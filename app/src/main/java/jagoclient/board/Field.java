//*CID://+1a30R~:                             update#=   29;       //+1a30R~
//****************************************************************************//~v1a0I~
//1A30 2013/04/06 kif,ki2 format support                           //+1a30I~
//1a00 2013/02/13 Asgts                                            //~v1a0I~
//****************************************************************************//~v1a0I~
package jagoclient.board;

import com.Asgts.AG;                                               //~@@@2R~


// ************** Field **************

/**
A class to hold a single field in the game board.
Contains data for labels, numbers, marks etc. and
of course the color of the stone on the board.
<P>
It may contain a reference to a tree, which is a variation
starting at this (empty) board position.
<P>
The Mark field is used for several purposes, like marking
a group of stones or a territory.
*/

public class Field
{	int C; // the state of the field (-1,0,1), 1 is black
	boolean Mark; // for several purposes (see Position.markgroup)
	TreeNode T; // Tree that starts this variation
	int Letter; // Letter to be displayed
	String LabelLetter; // Strings from the LB tag.
	boolean HaveLabel; // flag to indicate there is a label
	int Territory; // For Territory counting
	int Marker; // emphasized field
	static final int NONE=0;
    static final int CROSS=1;                                    //~2C31R~//~@@@2R~
    static final int SQUARE=2;                                   //~2C31R~//~@@@2R~
    static final int TRIANGLE=3;                                 //~2C31R~//~@@@2R~
    static final int CIRCLE=4;                                   //~2C31R~//~@@@2R~
	int Piece;                                                     //~@@@2I~
    public  static final int PIECE_PAWN=1;                         //~v1a0R~
    public  static final int PIECE_LANCE=2;                        //~v1a0R~
    public  static final int PIECE_KNIGHT=3;                       //~v1a0R~
    public  static final int PIECE_SILVER=4;                       //~v1a0R~
    public  static final int PIECE_GOLD=5;                         //~v1a0R~
    public  static final int PIECE_BISHOP=6;                       //~v1a0R~
    public  static final int PIECE_ROOK=7;                         //~v1a0R~
    public  static final int PIECE_KING=8;                         //~v1a0R~
    public  static final int MAX_PIECE_TYPE=8;                     //~v1a0M~
    public  static final int MAX_PIECE_TYPE_CAPTURE=7;             //~v1a0I~
    public  static final int PIECE_KING_CHALLENGING=9;             //~v1a0R~
                                                                   //~v1a0I~
    public  static final int PIECE_PROMOTED=10;                    //~v1a0I~
    public  static final int PIECE_PPAWN=PIECE_PROMOTED+PIECE_PAWN;//~v1a0R~
    public  static final int PIECE_PLANCE=PIECE_PROMOTED+PIECE_LANCE;//~v1a0R~
    public  static final int PIECE_PKNIGHT=PIECE_PROMOTED+PIECE_KNIGHT;//~v1a0R~
    public  static final int PIECE_PSILVER=PIECE_PROMOTED+PIECE_SILVER;//~v1a0R~
    public  static final int PIECE_PBISHOP=PIECE_PROMOTED+PIECE_BISHOP;//~v1a0R~
    public  static final int PIECE_PROOK=PIECE_PROMOTED+PIECE_ROOK;//~v1a0R~
    public  static final int INITIAL_STAFF_LINE=3;                 //+1a30I~
    public  static final int INITIAL_STAFF[][]={                   //+1a30I~
        {PIECE_LANCE,PIECE_KNIGHT,PIECE_SILVER,PIECE_GOLD,PIECE_KING,PIECE_GOLD,PIECE_SILVER,PIECE_KNIGHT,PIECE_LANCE},//+1a30I~
        {0,PIECE_BISHOP,0,0,0,0,0,PIECE_ROOK,0},                   //+1a30I~
        {PIECE_PAWN,PIECE_PAWN,PIECE_PAWN,PIECE_PAWN,PIECE_PAWN,PIECE_PAWN,PIECE_PAWN,PIECE_PAWN,PIECE_PAWN}//+1a30I~
        };                                                         //+1a30I~
                                                                   //~v1a0I~
	boolean Captured;                                              //~@@@2I~
//    boolean PieceSet;                                              //~@@@2I~//~v1a0R~
	int Number;

	//** set the field to 0 initially */
	public Field ()
	{	C=0;
		T=null;
		Letter=0;
		HaveLabel=false;
		Number=0;
        Piece=0;                                                   //~@@@2R~
        Captured=false;                                            //~@@@2I~
//        PieceSet=false;                                            //~@@@2I~//~v1a0R~
	}

	/** return its state */
	int color ()
	{	return C;
	}

	/** set its state */
//    void color (int c)                                           //~v1a0R~
//    {   C=c;                                                     //~v1a0R~
//        Number=0;                                                //~v1a0R~
//        if (C==0)                                                  //~@@@2I~//~v1a0R~
//            PieceSet=false;                                        //~@@@2I~//~v1a0R~
//    }                                                            //~v1a0R~
////**********************************************                   //~@@@2I~//~v1a0R~
//    public void piece(int Ppiece)                                  //~@@@2I~//~v1a0R~
//    {                                                              //~@@@2I~//~v1a0R~
//        Piece=Ppiece;                                              //~@@@2I~//~v1a0R~
//        PieceSet=true;                                             //~@@@2I~//~v1a0R~
//    }                                                              //~@@@2I~//~v1a0R~
//**********************************************                   //~v1a0I~
    public void piece(int Pcolor,int Ppiece)                       //~v1a0I~
    {                                                              //~v1a0I~
        Piece=Ppiece;                                              //~v1a0I~
        C=Pcolor;                                                  //~v1a0I~
    }                                                              //~v1a0I~
////**********************************************                 //~v1a0R~
//    public boolean isSetPiece()                                  //~v1a0R~
//    {                                                            //~v1a0R~
//        return PieceSet;                                         //~v1a0R~
//    }                                                            //~v1a0R~
//**********************************************                   //~@@@2I~
	public int piece()                                             //~@@@2I~
	{                                                              //~@@@2I~
		return Piece;                                              //~@@@2I~
	}                                                              //~@@@2I~
//**********************************************                   //~@@@2I~
	public void captured(boolean Pcaptured)                        //~@@@2I~
	{                                                              //~@@@2I~
		Captured=Pcaptured;                                        //~@@@2I~
	}                                                              //~@@@2I~
//**********************************************                   //~@@@2I~
	public boolean captured()                                          //~@@@2I~
	{                                                              //~@@@2I~
		return Captured;                                           //~@@@2I~
	}                                                              //~@@@2I~
//**********************************************                   //~@@@2I~
	
	final static int az='z'-'a';

	/** return a string containing the coordinates in SGF */
	static String string (int i, int j)
	{	char[] a=new char[2];
		if (i>=az) a[0]=(char)('A'+i-az-1);
		else a[0]=(char)('a'+i); 
		if (j>=az) a[1]=(char)('A'+j-az-1);
		else a[1]=(char)('a'+j);
		return new String(a);
	}
//** Position string********************************************   //~@@@2I~
	static String coordinate (int i,int j,int Size,int Pcolor)     //~@@@2R~
	{                                                              //~@@@2I~
		char[] a=new char[2];                                      //~@@@2I~
//        if (Size==AG.BOARDSIZE_SHOGI)                              //~@@@2I~//~v1a0R~
//        {                                                          //~@@@2I~//~v1a0R~
        	if (Pcolor<0)	//white                                //~@@@2I~
            {	i=AG.BOARDSIZE_SHOGI-i-1; j=AG.BOARDSIZE_SHOGI-j-1;}//~@@@2I~
        	a[0]=AG.SshogiH.charAt(i);                             //~@@@2I~
//           if (AG.isLangJP)                                      //~v1a0R~
//            a[1]=AG.SshogiV.charAt(j);                             //~@@@2I~//~v1a0R~
//           else                                                  //~v1a0R~
//            a[1]=AG.SshogiVE.charAt(j);                          //~v1a0R~
            a[1]=AG.SshogiV.charAt(j);                             //~v1a0I~
//        }                                                          //~@@@2I~//~v1a0R~
//        else                                                       //~@@@2R~//~v1a0R~
//        {                                                          //~@@@2I~//~v1a0R~
//            if (Pcolor>0)   //black                                //~@@@2I~//~v1a0R~
//            {   i=AG.BOARDSIZE_CHESS-i-1; j=AG.BOARDSIZE_CHESS-j-1;}//~@@@2I~//~v1a0R~
//            a[0]=AG.SchessH.charAt(i);                             //~@@@2I~//~v1a0R~
//            a[1]=AG.SchessV.charAt(j);                             //~@@@2I~//~v1a0R~
//        }                                                          //~@@@2I~//~v1a0R~
		return new String(a);                                      //~@@@2I~
	}                                                              //~@@@2I~

	/** return a string containing the coordinates in SGF */
	static String coordinate (int i, int j, int s)
	{	if (s>25)
		{	return (i+1)+","+(s-j);
		}
		else
		{	if (i>=8) i++;
			return ""+(char)('A'+i)+(s-j);
		}
	}

	/** get the first coordinate from the SGF string */
	static int i (String s)
	{	if (s.length()<2) return -1;
		char c=s.charAt(0);
		if (c<'a')  return c-'A'+az+1;
		return c-'a';
	}

	/** get the second coordinate from the SGF string */
	static int j (String s)
	{	if (s.length()<2) return -1;
		char c=s.charAt(1);
		if (c<'a')  return c-'A'+az+1;
		return c-'a';
	}
    //***************************************                      //~v1a0I~
	public static int nonPromoted(int Ppiece)                             //~v1a0I~
	{                                                              //~v1a0I~
        return (Ppiece>PIECE_PROMOTED)?Ppiece-PIECE_PROMOTED:Ppiece;//~v1a0I~
	}                                                              //~v1a0I~
	static int promoted(int Ppiece)                                //~v1a0I~
	{                                                              //~v1a0I~
    	if (Ppiece==PIECE_KING||Ppiece==PIECE_KING_CHALLENGING||Ppiece==PIECE_GOLD)//~v1a0I~
        	return Ppiece;                                         //~v1a0I~
        return (Ppiece>PIECE_PROMOTED)?Ppiece:Ppiece+PIECE_PROMOTED;//~v1a0I~
	}                                                              //~v1a0I~

	// modifiers:
	void mark (boolean f) { Mark=f; } // set Mark
	void tree (TreeNode t) { T=t; } // set Tree
	void marker (int f) { Marker=f; }
	void letter (int l) { Letter=l; }
	void territory (int c) { Territory=c; }
	void setlabel (String s) { HaveLabel=true; LabelLetter=s; }
	void clearlabel () { HaveLabel=false; }
	void number (int n) { Number=n; }

	// access functions:
	boolean mark () { return Mark; } // ask Mark
	int marker () { return Marker; }
	TreeNode tree () { return T; }
	int letter () { return Letter; }
	int territory () { return Territory; }
	boolean havelabel () { return HaveLabel; }
	String label () { return LabelLetter; }
	int number() { return Number; }
}
