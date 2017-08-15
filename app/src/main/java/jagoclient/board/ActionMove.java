//*CID://+1A4vR~:                             update#=   47;       //~1A4vR~
//********************************************************************//~v1A0I~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//1A4g 2014/11/30 replay of sg file(not kifu but asgts fmt);exchange black/white piece direction as following seanario..//~1A4gI~
//                start local match as white(actionMove coord is by black view).//~1A4gI~
//                interrupt and save(black view). local match reload saved(revesePosition is done).//~1A4gI~
//                local match restart reloading saved sg file(load as blackview,but revesePosition is done by your color is white).//~1A4gI~
//                interupt and save(file is by white view).        //~1A4gI~
//                replay the saved game(replay moves white piece as black piece)//~1A4gI~
//                ==>on memory keep coordinate as you see,on file keep black view//~1A4gI~
//1A2c 2013/03/27 display previous move description for reloaded game//~1A2cI~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//********************************************************************//~v1A0I~
package jagoclient.board;

import jagoclient.Dump;
import static jagoclient.board.Field.*;

import java.io.PrintWriter;

import com.Asgts.AG;

public class ActionMove                                            //~1A2cR~
{                                                                  //~1A2cR~
	public int moveNumber;                                         //~1A2cR~
    public int piece,color,iFrom,jFrom,iTo,jTo;                    //~1A2cI~
    public int capturedPiece,pieceTo;                              //~1A2cR~
    public int drop;                                               //~1A2cR~
	public String [] actionMsg;                                    //~1A2cR~
    private int msgCtr,maxMsgCtr;                                  //~1A2cI~
    public static final String PREFIX_ACTIONMOVE="M:";              //~1A2cR~
    public static final String PREFIX_ACTIONCMT="C:";               //~1A2cI~
    public static final String PREFIX_ACTIONEOF="End";             //~1A2cI~
    public static final String PREFIX_SEP=":";                     //~1A2cI~
    //********************************************************************//~1A2cI~
	public ActionMove (int Pyourcolor,int Pnumber,int Pcolor,int Ppiece,boolean Pdrop,int Pifrom,int Pjfrom,int Pito,int Pjto,int Pcapturedpiece,int Ppieceto)//~1A2cR~
	{                                                              //~1A2cR~
		if (Dump.Y)Dump.println("ActionMove num="+Pnumber+",col="+Pcolor+",piece="+Ppiece+",drop="+Pdrop+",("+Pifrom+","+Pjfrom+")-->("+Pito+","+Pjto+")"+",Pcapturedpiece="+Pcapturedpiece+"Ppieceto="+Ppieceto);//+1A4vR~
		moveNumber=Pnumber;                                      //~1A2cI~
        piece=Ppiece; color=Pcolor;                                //~1A2cI~
        iFrom=Pifrom; jFrom=Pjfrom; iTo=Pito; jTo=Pjto;            //~1A2cI~
//      if (Pyourcolor<0)                                          //~1A2cI~//~1A4gR~
//  		reversePosition();	//alway view from black            //~1A2cI~//~1A4gR~
        capturedPiece=Pcapturedpiece;                              //~1A2cI~
        pieceTo=Ppieceto;                                          //~1A2cI~
        drop=Pdrop?1:0;                                            //~1A2cR~
        maxMsgCtr=1;                                               //~1A2cI~
        actionMsg=new String[maxMsgCtr];                           //~1A2cI~
        msgCtr=0;                                                  //~1A2cI~
	}
    //********************************************************************//~1A2cI~
	public void reversePosition()                                  //~1A2cI~
	{                                                              //~1A2cI~
		if (Dump.Y)Dump.println("ActionMove reversePosition old from=("+iFrom+","+jFrom+"),to=("+iTo+","+jTo+")");//~1A4vI~
        iFrom=AG.BOARDSIZE_SHOGI-iFrom-1;                          //~1A2cI~
        jFrom=AG.BOARDSIZE_SHOGI-jFrom-1;                          //~1A2cI~
        iTo=AG.BOARDSIZE_SHOGI-iTo-1;                              //~1A2cI~
        jTo=AG.BOARDSIZE_SHOGI-jTo-1;                              //~1A2cI~
		if (Dump.Y)Dump.println("ActionMove reversePosition new from=("+iFrom+","+jFrom+"),to=("+iTo+","+jTo+")");//~1A4vI~
	}                                                              //~1A2cI~
    //********************************************************************//~1A2cI~
	public static int reverseIndex(int Pi)                             //~1A2cI~
	{                                                              //~1A2cI~
        return AG.BOARDSIZE_SHOGI-Pi-1;                            //~1A2cI~
	}                                                              //~1A2cI~
    //********************************************************************//~1A2cI~
	public void setMsg(String Pmsg)                        //~1A2cI~
	{                                                              //~1A2cI~
    	if (msgCtr>=maxMsgCtr)                                     //~1A2cI~
        {                                                          //~1A2cI~
	        String[] newActionMsg=new String[maxMsgCtr+1];           //~1A2cI~
            System.arraycopy(actionMsg,0,newActionMsg,0,maxMsgCtr);//~1A2cI~
            actionMsg=newActionMsg;                                //~1A2cI~
            maxMsgCtr++;                                           //~1A2cI~
        }                                                          //~1A2cI~
		if (Pmsg.endsWith("\n"))                            //~1A2cI~
        	actionMsg[msgCtr++]=Pmsg.substring(0,Pmsg.length()-1); //~1A2cR~
        else                                                       //~1A2cI~
        	actionMsg[msgCtr++]=Pmsg;                              //~1A2cI~
	}                                                              //~1A2cI~
    //********************************************************************//~1A2cI~
    //*move#:color,piece(2),pieceto(2),piececaptured(2),drop,i,j,ito,jto:msgctr//~1A2cI~
    //called from NotesTree:save                                   //~1A4gI~
    //********************************************************************//~1A2cI~
	public void print(PrintWriter Pw,String Pprefix)                  //~1A2cI~
	{                                                              //~1A2cI~
    	Pw.println(Pprefix+PREFIX_ACTIONMOVE+moveNumber+PREFIX_SEP+(color+1)//~1A2cR~
    		+(piece>PIECE_PROMOTED?piece:"0"+piece)                 //~1A2cI~
    		+(pieceTo>PIECE_PROMOTED?pieceTo:"0"+pieceTo)           //~1A2cI~
    		+(capturedPiece>PIECE_PROMOTED?capturedPiece:"0"+capturedPiece)//~1A2cI~
    		+drop+iFrom+jFrom+iTo+jTo+PREFIX_SEP+msgCtr);           //~1A2cI~
        for (int ii=0;ii<msgCtr;ii++)                              //~1A2cI~
	    	Pw.println(Pprefix+PREFIX_ACTIONCMT+actionMsg[ii]);     //~1A2cI~
	}                                                              //~1A2cI~
    //***********                                                  //~1A4gI~
	public void print(PrintWriter Pw,String Pprefix,int Pyourcolor)//~1A4gI~
	{                                                              //~1A4gI~
        int iiFrom,iiTo,jjFrom,jjTo;                               //~1A4gI~
    	if (Pyourcolor>0)                                          //~1A4gI~
        {                                                          //~1A4gI~
			print(Pw,Pprefix);//by black view                      //~1A4gI~
        	return;                                                //~1A4gI~
        }                                                          //~1A4gI~
        iiFrom=AG.BOARDSIZE_SHOGI-iFrom-1;	//by black view        //~1A4gI~
        jjFrom=AG.BOARDSIZE_SHOGI-jFrom-1;                         //~1A4gI~
        iiTo=AG.BOARDSIZE_SHOGI-iTo-1;                             //~1A4gI~
        jjTo=AG.BOARDSIZE_SHOGI-jTo-1;                             //~1A4gI~
    	Pw.println(Pprefix+PREFIX_ACTIONMOVE+moveNumber+PREFIX_SEP+(color+1)//~1A4gI~
    		+(piece>PIECE_PROMOTED?piece:"0"+piece)                //~1A4gI~
    		+(pieceTo>PIECE_PROMOTED?pieceTo:"0"+pieceTo)          //~1A4gI~
    		+(capturedPiece>PIECE_PROMOTED?capturedPiece:"0"+capturedPiece)//~1A4gI~
    		+drop+iiFrom+jjFrom+iiTo+jjTo+PREFIX_SEP+msgCtr);      //~1A4gI~
        for (int ii=0;ii<msgCtr;ii++)                              //~1A4gI~
	    	Pw.println(Pprefix+PREFIX_ACTIONCMT+actionMsg[ii]);    //~1A4gI~
	}                                                              //~1A4gI~
    //********************************************************************//~1A2cI~
    //*return line ctr;                                            //~1A4vI~
    //********************************************************************//~1A4vI~
//  public void display(ConnectedGoFrame Pcgf)                     //~1A4vR~
    public int display(ConnectedGoFrame Pcgf)                      //~1A4vI~
	{                                                              //~1A2cI~
        for (int ii=0;ii<msgCtr;ii++)                              //~1A2cI~
        	Pcgf.appendComment(actionMsg[ii]+"\n",false);          //~1A2cR~
        return msgCtr;                                             //~1A4vI~
	}                                                              //~1A2cI~
    //********************************************************************//~1A2cI~
    //*read file line, create ActionMove,add to tree **************//~1A2cR~
    //********************************************************************//~1A2cI~
	public static ActionMove add(NotesTree Ptree,String Pline)     //~1A2cR~
	{                                                              //~1A2cI~
    	int pos;                                                   //~1A2cI~
        ActionMove a;                                              //~1A2cI~
    //*****************************                                //~1A2cI~
        if (Dump.Y)Dump.println("ActionMove add line="+Pline); 
        if (Pline.startsWith(PREFIX_ACTIONMOVE))                   //~1A2cI~
        {                                                          //~1A2cI~
            pos=PREFIX_ACTIONMOVE.length();                        //~1A2cI~
            int pos2=Pline.indexOf(PREFIX_SEP,pos);                //~1A2cI~
            int number=Integer.valueOf(Pline.substring(pos,pos2)).intValue();//~1A2cR~
            pos=pos2+1;                                            //~1A2cR~
            int color=Pline.charAt(pos++)-'1';                   //~1A2cM~
            int piece=(Pline.charAt(pos++)-'0')*PIECE_PROMOTED     //~1A2cR~
            		     +Pline.charAt(pos++)-'0';                //~1A2cI~
            int pt=(Pline.charAt(pos++)-'0')*PIECE_PROMOTED        //~1A2cI~
            		     +Pline.charAt(pos++)-'0';                //~1A2cI~
            int cp=(Pline.charAt(pos++)-'0')*PIECE_PROMOTED        //~1A2cI~
            		     +Pline.charAt(pos++)-'0';                //~1A2cI~
            int drop=Pline.charAt(pos++)-'0';                      //~1A2cI~
            int ifr=Pline.charAt(pos++)-'0';                        //~1A2cI~
            int jfr=Pline.charAt(pos++)-'0';                        //~1A2cI~
            int ito=Pline.charAt(pos++)-'0';                        //~1A2cI~
            int jto=Pline.charAt(pos++)-'0';                        //~1A2cI~
            a=new ActionMove(1/*file is written by black View*/,number,color,piece,drop==1,ifr,jfr,ito,jto,cp,pt);//~1A2cR~
			Ptree.add(a);                                          //~1A2cI~
            return a;                                              //~1A2cI~
        }                                                          //~1A2cI~
        if (Pline.startsWith(PREFIX_ACTIONCMT))                    //~1A2cI~
        {                                                          //~1A2cI~
            pos=PREFIX_ACTIONCMT.length();                         //~1A2cI~
            String msg=Pline.substring(pos);                       //~1A2cI~
            if (Ptree.size()==0)    //lastElement throw exception if Vector is empty//~1A2cI~
                return null;                                       //~1A2cI~
            a=Ptree.lastElement();                                 //~1A2cI~
            a.setMsg(msg);                                         //~1A2cI~
            return a;                                              //~1A2cI~
        }                                                          //~1A2cI~
        return null;                                               //~1A2cI~
	}                                                              //~1A2cI~
}
