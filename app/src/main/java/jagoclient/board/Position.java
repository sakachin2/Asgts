//*CID://+v1a0R~:                             update#=   16;       //~@@@2I~//~v1a0R~
//************************************************************************//~v1a0I~
//1a00 2013/02/13 Asgts                                            //~v1a0I~
//************************************************************************//~v1a0I~
package jagoclient.board;

import jagoclient.Dump;


/**
Store a complete game position.
Contains methods for group determination and liberties.
*/

public class Position
{	int S; // size (9,11,13 or 19)
	int C; // next turn (1 is black, -1 is white)
	Field[][] F; // the board
	
	/** 
	Initialize F with an empty board, and set the next turn to black.
	*/
	public Position (int size)
	{	S=size;
		F=new Field[S][S];
		int i,j;
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
				F[i][j]=new Field();
		C=1;
	}

	/** 
	Initialize F with an empty board, and set
	the next turn to black.
	*/
	public Position (Position P)
	{	S=P.S;
		F=new Field[S][S];
		int i,j;
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
			{	F[i][j]=new Field();
			}
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
			{//	color(i,j,P.color(i,j));                           //~@@@2R~
			 	piece(i,j,P.color(i,j),P.piece(i,j));              //~@@@2I~//~v1a0R~
			    number(i,j,P.number(i,j));
			    marker(i,j,P.marker(i,j));
			    letter(i,j,P.letter(i,j));
			    if (P.haslabel(i,j)) setlabel(i,j,P.label(i,j));
			}
		color(P.color());
	}

	// Interface routines to set or ask a field:
	int color (int i, int j)
	{	return F[i][j].color();
	}
//    void color (int i, int j, int c)                             //~@@@2R~
//    {   F[i][j].color(c);                                        //~@@@2R~
//    }                                                            //~@@@2R~
    void piece(int i, int j, int c,int Ppiece)                      //~v1a0I~
    {                                                              //~v1a0I~
        if (Dump.Y) Dump.println("Position:piece i="+i+",j="+j+",color="+c+",piece="+Ppiece);//+v1a0I~
		F[i][j].piece(c,Ppiece);                                   //~v1a0I~
    }                                                              //~v1a0I~
	void number (int i, int j, int n) { F[i][j].number(n); }
	int number (int i, int j) { return F[i][j].number(); }
  public                                                           //~v1a0I~
    int color () { return C; }                                     //~@@@2R~
	void color (int c)                                             //~v1a0R~
	{                                                              //~v1a0I~
    	if (Dump.Y) Dump.println("Position set color="+c);         //~v1a0I~
		C=c;                                                       //~v1a0I~
	}                                                              //~v1a0I~

	/**
	Recursively mark all unmarked places with state c,
	starting from (i,j).
	*/
	void markrek (int i, int j, int c)
	{	if (F[i][j].mark() || F[i][j].color()!=c) return;
		F[i][j].mark(true);
		if (i>0) markrek(i-1,j,c);
		if (j>0) markrek(i,j-1,c);
		if (i<S-1) markrek(i+1,j,c);
		if (j<S-1) markrek(i,j+1,c);		
	}
	
	/**
	Mark a group at (n,m)
	*/
	public void markgroup (int n, int m)
	{	unmarkall();
		// recursively do the marking
		markrek(n,m,F[n][m].color());
	}
	
	/**
	Recursively mark a group of state c
	starting from (i,j) with the main goal to
	determine, if there is a neighbor of state ct to
	this group.
	If yes abandon the mark and return true.
	*/
	boolean markrektest (int i, int j, int c, int ct)
	{	if (F[i][j].mark()) return false;
	 	if (F[i][j].color()!=c)
	 	{	if (F[i][j].color()==ct) return true;
	 		else return false;
	 	}
		F[i][j].mark(true);
		if (i>0) { if (markrektest(i-1,j,c,ct)) return true; }
		if (j>0) { if (markrektest(i,j-1,c,ct)) return true; }
		if (i<S-1) { if (markrektest(i+1,j,c,ct)) return true; }
		if (j<S-1) { if (markrektest(i,j+1,c,ct)) return true; }
		return false;
	}
	
	/**
	Test if the group at (n,m) has a neighbor of state ct.
	If yes, mark all elements of the group.
	Else return false.
	*/
	public boolean markgrouptest (int n, int m, int ct)
	{	unmarkall();
		return markrektest(n,m,F[n][m].color(),ct);
	}

	/** cancel all markings
	*/
	public void unmarkall ()
	{	int i,j;
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
			{	F[i][j].mark(false);
			}
	}
	
	/** mark and count
	*/
	public int count (int i, int j)
	{	unmarkall();
		markgroup(i,j);
		int count=0;
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
				if (F[i][j].mark()) count++;
		return count;
	}

	/**
	Find all B and W territory.
	Sets the territory flags to 0, 1 or -1.
	-2 is an intermediate state for unchecked points.
	*/
	public void getterritory ()
	{	int i,j,ii,jj;
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
			{	F[i][j].territory(-2);
			}
		for (i=0; i<S; i++)
			for (j=0; j<S; j++)
			{	if (F[i][j].color()==0)
				{	if (F[i][j].territory()==-2)
					{	if (!markgrouptest(i,j,1))
						{	for (ii=0; ii<S; ii++)
								for (jj=0; jj<S; jj++)
								{	if (F[ii][jj].mark())
										F[ii][jj].territory(-1);
								}
						}
						else if (!markgrouptest(i,j,-1))
						{	for (ii=0; ii<S; ii++)
								for (jj=0; jj<S; jj++)
								{	if (F[ii][jj].mark())
										F[ii][jj].territory(1);
								}
						}
						else
						{	markgroup(i,j);
							for (ii=0; ii<S; ii++)
								for (jj=0; jj<S; jj++)
								{	if (F[ii][jj].mark())
										F[ii][jj].territory(0);
								}
						}
					}
				}
			}
		
	}

	// Interface to determine field marks.
	boolean marked (int i, int j) { return F[i][j].mark(); }
	int marker (int i, int j) { return F[i][j].marker(); }
	void marker (int i, int j, int f) { F[i][j].marker(f); }
	void letter (int i, int j, int l) { F[i][j].letter(l); }
	int letter (int i, int j) { return F[i][j].letter(); }
	int territory (int i, int j) { return F[i][j].territory(); }
	boolean haslabel (int i, int j) { return F[i][j].havelabel(); }
	String label (int i, int j) { return F[i][j].label(); }
	void setlabel (int i, int j, String s) { F[i][j].setlabel(s); }
	void clearlabel (int i, int j) { F[i][j].clearlabel(); }

	// Interfact to variation trees
	TreeNode tree (int i, int j) { return F[i][j].tree(); }
	void tree (int i, int j, TreeNode t) { F[i][j].tree(t); }
////********************************************************       //~@@@2R~
//    public void setPiece(int i, int j, int Ppiece)               //~@@@2R~
//    {                                                            //~@@@2R~
//        F[i][j].piece(Ppiece);                                   //~@@@2R~
//    }                                                            //~@@@2R~
////********************************************************       //~@@@2R~
//    public boolean isSetPiece(int i, int j)                      //~@@@2R~
//    {                                                            //~@@@2R~
//        return F[i][j].isSetPiece();                             //~@@@2R~
//    }                                                            //~@@@2R~
//********************************************************         //~@@@2I~
	public void setPiece(int i, int j, int Pcolor,int Ppiece)      //~@@@2I~
    {                                                              //~@@@2I~
		F[i][j].piece(Pcolor,Ppiece);                              //~@@@2I~
        if (Dump.Y) Dump.println("Position:setPiece i="+i+",j="+j+",color="+Pcolor+",piece="+Ppiece);//~v1a0I~
    }                                                              //~@@@2I~
//********************************************************         //~@@@2I~
	public int piece(int i, int j)                                        //~@@@2I~//~v1a0R~
    {                                                              //~@@@2I~
		return F[i][j].piece();                                    //~@@@2I~
    }                                                              //~@@@2I~
//********************************************************         //~@@@2I~
	void setCaptured(int i, int j,boolean Pcaptured)               //~@@@2I~
    {                                                              //~@@@2I~
		F[i][j].captured(Pcaptured);                               //~@@@2I~
    }                                                              //~@@@2I~
//********************************************************         //~@@@2I~
	boolean captured(int i, int j)                                     //~@@@2I~
    {                                                              //~@@@2I~
		return F[i][j].captured();                                 //~@@@2I~
    }                                                              //~@@@2I~
}

