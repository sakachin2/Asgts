//*CID://+1Ah0R~: update#=   4;                                    //+1Ah0R~
//*********************************                                //~v1B6I~
//1Ah0 2016/10/15 bonanza                                          //+1Ah0I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// Move.java
package com.Asgts.gtp;                                             //~v1B6R~

//import static net.sf.gogui.go.GoColor.BLACK;                     //~v1B6R~
//import static net.sf.gogui.go.GoColor.WHITE;                     //~v1B6R~
import static com.Asgts.gtp.GoColor.BLACK;                         //~v1B6R~
import static com.Asgts.gtp.GoColor.WHITE;                         //~v1B6R~
import jagoclient.board.NotesFmtCsa;

/** Move containing a point and a color.
    The point can be <code>null</code> (for pass move).
    The color is black or white.
    This class is immutable, references are unique. */
public final class Move
{
    /** Factory method for constructing a move.
        @param color The color of the move
        @param x Column in <code>[0..GoPoint.MAX_SIZE - 1]</code>
        @param y Row in <code>[0..GoPoint.MAX_SIZE - 1]</code>
        @return Reference to this move */
    public static Move get(GoColor color, int x, int y)
    {
        return get(color, GoPoint.get(x, y));
    }

    /** Factory method for constructing a move.
        @param color The color of the move (empty can still be used for
        removing a stone on the board, but this will be deprecated in the
        future)
        @param point Location of the move (null for pass move)
        @return Reference to this move */
    public static Move get(GoColor color, GoPoint point)
    {
        assert color.isBlackWhite();
        if (point == null)
        {
            if (color == BLACK)
                return s_passBlack;
            else
                return s_passWhite;
        }
        int x = point.getX();
        int y = point.getY();
        if (color == BLACK)
            return s_movesBlack[x][y];
        else
            return s_movesWhite[x][y];
    }

    /** Factory method for constructing a pass move.
        @param c The color of the move.
        @return Reference to this move */
    public static Move getPass(GoColor c)
    {
        assert c.isBlackWhite();
        return get(c, null);
    }

    /** Get color of move.
        @return Color of move */
    public GoColor getColor()
    {
        return m_color;
    }

    /** Get stone location of move.
        @return Location of move; null for pass move */
    public GoPoint getPoint()
    {
        return m_point;
    }

    /** Get string representation of move.
        @return String representation using an uppercase letter for the color
        and an uppercase string for the point (e.g. B C3, W PASS). This
        representation is also guaranteed to be compatible with the formats
        used in the Go Text Protocol. */
    public String toString()
    {
        return m_string;
    }

    private static Move s_passBlack;

    private static Move s_passWhite;

    private static Move[][] s_movesBlack;

    private static Move[][] s_movesWhite;

    private final GoColor m_color;

    private final GoPoint m_point;

    private final String m_string;

    static
    {
        s_passBlack = new Move(BLACK, null, "B PASS");
        s_passWhite = new Move(WHITE, null, "W PASS");
        s_movesBlack = init(BLACK);
        s_movesWhite = init(WHITE);
    }

    private static Move[][] init(GoColor color)
    {
        Move[][] result = new Move[GoPoint.MAX_SIZE][GoPoint.MAX_SIZE];
        StringBuilder buffer = new StringBuilder(8);
        String colorString = color.getUppercaseLetter();
        for (int x = 0; x < GoPoint.MAX_SIZE; ++x)
            for (int y = 0; y < GoPoint.MAX_SIZE; ++y)
            {
                GoPoint p = GoPoint.get(x, y);
                buffer.setLength(0);
                buffer.append(colorString);
                buffer.append(' ');
                buffer.append(p);
                result[x][y] = new Move(color, p, buffer.toString());
            }
        return result;
    }

    private Move(GoColor color, GoPoint point, String string)
    {
        m_point = point;
        m_color = color;
        m_string = string;
    }
    //***********************************************************  //+1Ah0I~
    public static String get(GoColor color, GoPoint point, int Ppiece, boolean Pdrop,GoPoint Ppointold)//+1Ah0I~
    {                                                              //+1Ah0I~
        assert color.isBlackWhite();                               //+1Ah0I~
        if (point == null)                                         //+1Ah0I~
        {                                                          //+1Ah0I~
            if (color == BLACK)                                    //+1Ah0I~
                return "Black PASS";                               //+1Ah0I~
            else                                                   //+1Ah0I~
                return "White PASS";                               //+1Ah0I~
        }                                                          //+1Ah0I~
        int x = point.getX();                                      //+1Ah0I~
        int y = point.getY();                                      //+1Ah0I~
        int xold=Ppointold.getX();                                 //+1Ah0I~
        int yold=Ppointold.getY();                                 //+1Ah0I~
        String csamove=NotesFmtCsa.getMoveString(Ppiece,x,y,xold,yold,Pdrop);//+1Ah0I~
        return csamove;                                            //+1Ah0I~
    }                                                              //+1Ah0I~
}
