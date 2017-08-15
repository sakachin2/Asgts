package jagoclient.board;

/**
Holds position changes at one field.
*/

public class Change
{	public int I,J,C;
	public int N;
	public int Piece;                                              //+3214I~
	/**
	Board position i,j changed from c.
	*/
	public Change (int i, int j, int c, int n)
	{	I=i; J=j; C=c; N=n;
    	Piece=0;                                                   //+3214I~
	}
//    public Change (int  i, int j, int c)                         //+3214R~
//    {   this(i,j,c,0);                                           //+3214R~
//    }                                                            //+3214R~
    public Change (int  i, int j, int c,int n,int Ppiece)                //+3214I~
    {   this(i,j,c,n);                                             //+3214I~
    	Piece=Ppiece;                                              //+3214I~
    }                                                              //+3214I~
}
