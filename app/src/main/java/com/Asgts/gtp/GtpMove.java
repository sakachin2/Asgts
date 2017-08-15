//*CID://+1Ah0R~: update#=  152;                                   //~1Ah0R~
//*********************************************************************//~1Ah0R~
//1Ah0 2016/10/15 add robot:bonanza                                //~1Ah0I~
//*********************************************************************//~1Ah0I~
package com.Asgts.gtp;                                             //~1AfmR~

import com.Asgts.AG;

import android.graphics.Point;
import jagoclient.Dump;
import jagoclient.board.NotesFmtCsa;

public class GtpMove                                               //~1Ah0R~
{                                                                  //~1Ah0I~
    public int xx,yy,oldxx,oldyy;                                  //~1Ah0R~
    public int moveid;                                             //~1Ah0R~
    public static final int MOVEID_MOVED=0;                        //~1Ah0I~
    public static final int MOVEID_RESIGNED=1;                     //~1Ah0I~
    public static final int MOVEID_MATED=2;                        //~1Ah0I~
    public boolean drop;                                           //~1Ah0I~
                                                                   //~1Ah0I~
    public int funcid;                                             //~1Ah0R~
	public static final int GTPMOVE_COMPUTER_MOVE=1;               //~1Ah0I~
	public static final int GTPMOVE_HUMAN_MOVE=2;                 //~1Ah0I~
    public int status;                                             //~1Ah0I~
	public static final int GTPMOVE_STATUS_SENDPLAY=1;               //~1Ah0I~
	public static final int GTPMOVE_STATUS_SENDPLAY_RESPONSE=2;    //+1Ah0I~
                                                                   //~1Ah0I~
    public int piece,color;                                              //~1Ah0R~
    public GtpMove(int Pfunc,int Pcolor,String Pcsamove,int Pmoveid)//~1Ah0R~
    {                                                              //~1Ah0R~
        moveid=Pmoveid;                                            //~1Ah0R~
        if (moveid==MOVEID_MOVED)                                  //~1Ah0R~
        	parseMove(Pcsamove,this);                              //~1Ah0R~
        funcid=Pfunc; color=Pcolor;//~1Ah0R~
    }                                                              //~1Ah0R~
    public GtpMove(int Pfunc,int Pcolor,int Ppiece,int Px,int Py,int Poldx,int Poldy,boolean Pdrop)//~1Ah0R~
    {                                                              //~1Ah0I~
    	funcid=Pfunc;                                              //~1Ah0I~
        piece=Ppiece; xx=Px; yy=Py; oldxx=Poldx; oldyy=Poldy; drop=Pdrop;//~1Ah0I~
        color=Pcolor;                                              //~1Ah0I~
    }                                                              //~1Ah0I~
    public void reverseCoord()                                          //~1Ah0I~
    {                                                              //~1Ah0I~
    	if (!drop)                                                 //~1Ah0I~
        {                                                          //~1Ah0I~
        	oldxx=AG.BOARDSIZE_SHOGI-oldxx-1;                      //~1Ah0I~
        	oldyy=AG.BOARDSIZE_SHOGI-oldyy-1;                      //~1Ah0I~
        }                                                          //~1Ah0I~
        xx=AG.BOARDSIZE_SHOGI-xx-1;                                //~1Ah0I~
        yy=AG.BOARDSIZE_SHOGI-yy-1;                                //~1Ah0I~
        if (Dump.Y) Dump.println("GtpMove:reverseCoord drop="+drop+",new xx="+xx+",yy="+yy+",oldxx="+oldxx+",oldyy="+oldyy);//~1Ah0I~
    }                                                              //~1Ah0I~
    public static GtpMove create(int Pfunc,int Pcolor,int Ppiece,int Ppos,int Poldpos,boolean Pdrop)//~1Ah0R~
    {                                                              //~1Ah0I~
        Point p1=I2P(Ppos);                                        //~1Ah0I~
        Point p2=I2P(Poldpos);                                     //~1Ah0I~
    	return new GtpMove(Pfunc,Pcolor,Ppiece,p1.x,p1.y,p2.x,p2.y,Pdrop);//~1Ah0R~
    }                                                              //~1Ah0I~
    public static int P2I(int Px,int Py)                           //~1Ah0I~
    {                                                              //~1Ah0I~
        return Py*AG.BOARDSIZE_SHOGI+Px;                           //~1Ah0I~
    }                                                              //~1Ah0I~
    public static Point I2P(int Ppos)                              //~1Ah0I~
    {                                                              //~1Ah0I~
        int x=(Ppos)%AG.BOARDSIZE_SHOGI;                           //~1Ah0I~
        int y=(Ppos)/AG.BOARDSIZE_SHOGI;                           //~1Ah0I~
        return new Point(x,y);                                     //~1Ah0I~
    }                                                              //~1Ah0I~
    public void parseMove(String Pcsamove,GtpMove Pmove)           //~1Ah0I~
    {                                                              //~1Ah0I~
        NotesFmtCsa.getMoveCsa(Pcsamove,Pmove);                    //~1Ah0I~
    }                                                              //~1Ah0I~
    public String getMoveString()                                  //~1Ah0R~
    {                                                              //~1Ah0I~
        return NotesFmtCsa.getMoveString(piece,xx,yy,oldxx,oldyy,drop);//~1Ah0I~
    }                                                              //~1Ah0I~
    //*********************************************************    //~1Ah0I~
    //*dimension index -->Board index  ex (0,0)-->(9,1)            //~1Ah0I~
    //*********************************************************    //~1Ah0I~
    public static Point coord2BoardPos(int Px,int Py)              //~1Ah0I~
    {                                                              //~1Ah0I~
       int x=AG.BOARDSIZE_SHOGI-Px; int y=Py+1;                          //~1Ah0I~
       if (Dump.Y) Dump.println("GtpMove:coord2BoardPos ("+Px+","+Py+")-->("+x+","+y+")");//~1Ah0R~
       return new Point(x,y);                                      //~1Ah0I~
    }                                                              //~1Ah0I~
    //*********************************************************    //~1Ah0I~
    public static int point2Pos(int Px,int Py)                     //~1Ah0I~
    {                                                              //~1Ah0M~
    	int pos=Py*AG.BOARDSIZE_SHOGI+Px+1;                        //~1Ah0I~
        if (Dump.Y) Dump.println("point2Pos pos="+pos+"<==i="+Px+",j="+Py);//~1Ah0M~
        return pos;                                                //~1Ah0M~
    }                                                              //~1Ah0M~
    //*********************************************************    //~1Ah0I~
    public static Point pos2Point(int Ppos)                        //~1Ah0I~
    {                                                              //~1Ah0I~
    	int x=(Ppos-1)%AG.BOARDSIZE_SHOGI;                         //~1Ah0I~
    	int y=(Ppos-1)/AG.BOARDSIZE_SHOGI;                         //~1Ah0I~
        if (Dump.Y) Dump.println("pos2Point pos="+Ppos+"==>i"+x+",j="+y);//~1Ah0I~
    	return new Point(x,y);                                     //~1Ah0I~
    }                                                              //~1Ah0I~
}
