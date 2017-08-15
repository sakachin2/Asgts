//*CID://+1A0eR~:                             update#=   13;       //~@@@@I~//+1A0eR~
//****************************************************************************//~@@@@I~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~@@@@I~
//****************************************************************************//~@@@@I~
package jagoclient.board;

import com.Asgts.awt.Color;                                        //~@@@@R~
import com.Asgts.awt.Font;                                         //~@@@@R~

//import java.awt.Color;
//import java.awt.Font;

/**
Encorporated the board into an environment (such as
GoFrame).
*/

public interface BoardInterface
{	public boolean boardShowing ();
		// board displays already or not
	
	public void activate ();
		// board is painted and displayed for the first time
		
	// Various Color settings:
	public boolean bwColor (); // black and white only?
	public boolean blackOnly ();
	public Color boardColor ();
	public Color blackColor ();
	public Color blackSparkleColor ();
	public Color whiteColor ();
	public Color whiteSparkleColor ();
	public Color markerColor (int color);
	public Color labelColor (int color);
	public Color backgroundColor ();
	
	public boolean blocked ();
		// blocks changed at the end of main variation
		
	// Board sets two labels, which may be used in a frame
	public void setLabelM (String s); // position of cursor
	public void setLabel (String s); // next move prompt
	
//    public void advanceTextmark ();                              //~@@@@R~
		// request setting of the next textmark A,B,C,... or 1,2,3,...
	
	public void setState (int n, boolean flag);
		// enable/disable navigation buttons 
		// 1=left, 2=right, 3=varup, 4=varmain, 5=down, 6=up, 7=main
		
	public void setState (int n);
		// check the menu item for the current state
		// 1=black, 2=white, 3=setblack, 4=setwhite,
		// 5=mark, 6=letter, 7=hide, 10=textmark
	
	public void setMarkState (int marker);
		// enable the correct marker item
		// marker is from FIELD.SQUARE etc.
	
	// Comment area:
//    public String getComment ();                                 //~@@@@R~
		// get the content of the comment area
//    public void setComment (String s);                           //~@@@@R~
		// set the comment area to that string
//    public void appendComment (String s);                        //~@@@@R~
		// append something to the comment area only
//    public void addComment (String s);                           //~@@@@R~
		// used to notify that board did "Pass" and "Undo"
		// usually should call Board.addcomment()

	// get flags:
	public boolean showTarget (); // flag for target rectangle
	public boolean lastNumber (); // flag to show last number

//    public boolean askUndo ();                                   //~@@@@R~
		// should open an "Delete Moves" modal dialog and return,
		// if undo was allowed
	
//    public boolean askInsert ();                                 //~@@@@R~
		// should open an "Change Game Tree" modal dialog and return,
		// if the node insertion was allowed
	
	public void yourMove (boolean f);
		// called if a move was received at end of main variation,
		// but current position is not visible
	
	public void result (int b, int w);
		// sends the result of a game back from the done function
		// when counted at the end of the main tree.
		
	public String resourceString (String S);
		// translate the Resource for me
		// check Board.java for necessary translations
	
	public boolean getParameter (String S, boolean f);
		// get a named parameter with boolean value
		// check Board.java for necessary parameters
		// default is f

	public Color getColor (String S, int red, int green, int blue);
		// get a named parameter with Color value
		// check Board.java for necessary parameters
		// default is the given color

	public String version ();
		// return the program version for SGF versioning
	
	public Font boardFont ();
		// return a font for the board
	public Font boardFont (int Psize);                             //~@@@@I~
    //***************************************                      //~@@@@I~
//    public void updateCapturedList(int Pcolor,int Ppiece);	//to Canvas through GoFrame blocking next input//+1A0eR~
    public void updateCapturedList(int Pcolor,int Ppiece,int Pcount);	//to Canvas through GoFrame blocking next input//+1A0eR~
    public void setBlock(boolean Pblock);	//to Canvas through GoFrame blocking next input//~@@@@I~
    public void gameover(int Prequest,int Pcolor);	//to Canvas through GoFrame blocking next input//~@@@@I~

}
