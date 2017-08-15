//*CID://+1A4gR~:                             update#=   37;       //~1A4gR~
//************************************************************************//~1A2cI~//~1A2eR~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4gI~
//1A4g 2014/11/30 replay of sg file(not kifu but asgts fmt);exchange black/white piece direction as following seanario..//~1A4gI~
//                start local match as white(actionMove coord is by black view).//~1A4gI~
//                interrupt and save(black view). local match reload saved(revesePosition is done).//~1A4gI~
//                local match restart reloading saved sg file(load as blackview,but revesePosition is done by your color is white).//~1A4gI~
//                interupt and save(file is by white view).        //~1A4gI~
//                replay the saved game(replay moves white piece as black piece)//~1A4gI~
//                ==>on memory keep coordinate as you see,on file keep black view//~1A4gI~
//1A2m 2013/04/06 register gameover reason at last entry of ActionMove//~1A2eI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A2c 2013/03/27 display previous move description for reloaded game//~1A2cI~
//************************************************************************//~1A2cI~
package jagoclient.board;

import jagoclient.Dump;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

import com.Asgts.awt.TextArea;

public class NotesTree extends Vector<ActionMove>                  //~1A2cR~
{                                                                  //~1A2cR~
    public static final String TREE_PREFIX="T:";                   //~1A2cI~
    public static final String TREE_PREFIX_TOP="TOP:";             //~1A2cI~
    public static final int NOTESTREE_INITCTR=100;                              //~1A2cI~
    public static final int NOTESTREE_INCREMENT=20;
    static int /*Snumber,*/Ssize/*,Slineno*/;                      //~1A2cR~
    public int winner,gameoverMsgid;                               //+1A4gR~
    int yourcolor;                                                 //~1A2cI~
    public int headerCommentCtr;                                   //~1A4gI~
    public StringBuffer headerComment;                            //~1A4gI~
	public NotesTree(int Pcolor)                                   //~1A2cR~
	{                                                              //~1A2cR~
		this(Pcolor,NOTESTREE_INITCTR,NOTESTREE_INCREMENT);        //~1A2cR~
	}
//***************************************************************************//~1A2cI~
	public NotesTree(int Pcolor,int Pinitialctr,int Pincrement)    //~1A2cR~
	{                                                              //~1A2cI~
		super(Pinitialctr,Pincrement);                             //~1A2cI~
        yourcolor=Pcolor;                                          //~1A2cI~
	}                                                              //~1A2cI~
//***************************************************************************//~1A2cI~
    public boolean add(ActionMove Paction)                            //~1A2cI~
    {                                                              //~1A2cI~
        if (Dump.Y) Dump.println("NotesTree:add");                  //~1A2cI~
//      if (yourcolor==-1)                                         //~1A2cI~//~1A2eR~
//      	Paction.reversePosition();                             //~1A2cI~//~1A2eR~
        return super.add(Paction);                                        //~1A2cI~
    }                                                              //~1A2cI~
//***************************************************************************//~1A2cI~
    public void setMsg(int Pnumber,String Pmsg)                    //~1A2cI~
    {                                                              //~1A2cI~
        if (Dump.Y) Dump.println("NotesTree:setMsg move="+Pnumber+",msg="+Pmsg);//~1A2cR~
        if (size()==0)                                             //~1A2cI~
            return;                                                //~1A2cI~
        ActionMove a=lastElement();                                //~1A2cI~
    	if (a.moveNumber!=Pnumber)                                     //~1A2cR~
        	return;                                                //~1A2cI~
    	a.setMsg(Pmsg);                                            //~1A2cI~
    }                                                              //~1A2cI~
//***************************************************************************//~1A2mR~
    public void setGameover(int Pcolor/*winner*/,int Pmsgid)       //~1A2mR~
    {                                                              //~1A2mR~
        if (Dump.Y) Dump.println("NotesTree:setGameover color="+Pcolor+",msgid="+Integer.toHexString(Pmsgid));//~1A2mR~
        winner=Pcolor;                                             //~1A2mR~
        gameoverMsgid=Pmsgid;                                      //~1A2mR~
    }                                                              //~1A2eI~
//***************************************************************************//~1A2cI~
    public void save(PrintWriter Pw,String Pprefix)                //~1A2cI~
    {                                                              //~1A2cI~
        if (Dump.Y) Dump.println("NotesTree:save move prefix="+Pprefix);//~1A2cR~
        int sz=size();                                             //~1A2cI~
        String prefix=NotesTree.writeHeader(Pw,Pprefix,sz,this);                                 //~1A2cR~
        for (Iterator<ActionMove> it=iterator();it.hasNext();)                //~1A2cI~
        {                                                          //~1A2cI~
        	ActionMove a=it.next();                                 //~1A2cI~
//            if (a.actionMsg.endsWith("\n"))                      //~1A2cR~
//                Pw.print(prefix+a.actionMsg);      //eof         //~1A2cR~
//            else                                                 //~1A2cR~
//                Pw.println(prefix+a.actionMsg);      //eof       //~1A2cR~
//          a.print(Pw,prefix);                                    //~1A2cI~//~1A4gR~
            a.print(Pw,prefix,yourcolor);                          //~1A4gI~
        }                                                          //~1A2cI~
	    Pw.println(prefix+"EOF");         //EOF                    //~1A2cI~
    }//save                                                        //~1A2cI~
//***************************************************************************//~1A2cI~
//  public void display(ConnectedGoFrame Pcgf)                     //~1A4gR~
    public int  display(ConnectedGoFrame Pcgf)                     //~1A4gI~
    {                                                              //~1A2cI~
        int msgctr=0;                                              //~1A4gI~
        if (Dump.Y) Dump.println("NotesTree:display");             //~1A2cI~
        for (Iterator<ActionMove> it=iterator();it.hasNext();)     //~1A2cI~
        {                                                          //~1A2cI~
        	ActionMove a=it.next();                                //~1A2cI~
//            String msg=a.actionMsg;                              //~1A2cR~
//            if (msg.endsWith("\n"))                              //~1A2cR~
//                Pcgf.appendComment(msg); //set also to new tree  //~1A2cR~
//            else                                                 //~1A2cR~
//                Pcgf.appendComment(msg+"\n");                    //~1A2cR~
          msgctr+=                                                 //~1A4gI~
  			a.display(Pcgf);                                       //~1A2cI~
        }                                                          //~1A2cI~
        return msgctr;                                             //~1A4gI~
    }//save                                                        //~1A2cI~
//***************************************************************************//~1A2cI~
//*write ctr hdr                                                   //~1A2cI~
//***************************************************************************//~1A2cI~
//  public static String writeHeader(PrintWriter Pw,String Pprefix,int Pctr)//~1A2cI~//~1A2eR~
    public static String writeHeader(PrintWriter Pw,String Pprefix,int Pctr,NotesTree Ptree)//~1A2eI~
    {                                                              //~1A2cI~
        if (Dump.Y) Dump.println("NotesTree:save0 move prefix="+Pprefix);//~1A2cI~
        String prefix=Pprefix==null?TREE_PREFIX:Pprefix+TREE_PREFIX;//~1A2cI~
      if (Ptree!=null)                                             //~1A2mR~
        Pw.println(prefix+TREE_PREFIX_TOP+Pctr+":"+Ptree.winner+":"+Ptree.gameoverMsgid);//~1A2mR~
      else                                                         //~1A2mR~
        Pw.println(prefix+TREE_PREFIX_TOP+Pctr+":");                   //~1A2cI~//~1A2mR~
        return prefix;                                             //~1A2cI~
    }//save                                                        //~1A2cI~
//***************************************************************************//~1A2cI~
//*construct tree                                                  //~1A2cI~
//*return true:eof                                                 //~1A2cI~
//***************************************************************************//~1A2cI~
    public static boolean setTree(Notes Pnotes,String Pline)       //~1A2cI~
    {                                                              //~1A2cI~
    //***********************************************              //~1A2cI~
        if (Dump.Y) Dump.println("NotesTree:setTree line="+Pline); //~1A2cI~
        if (!Pline.startsWith(TREE_PREFIX))                        //~1A2cI~
        	return true;       //eof                               //~1A2cI~
        if (Pline.indexOf(TREE_PREFIX_TOP)==TREE_PREFIX.length())  //~1A2cI~
        {                                                          //~1A2cI~
//      	Snumber=0;                                             //~1A2cR~
//          Slineno=0;                                             //~1A2cR~
//      	int sz=Integer.valueOf(Pline.substring(TREE_PREFIX.length()+TREE_PREFIX_TOP.length())).intValue();//~1A2cI~//~1A2mR~
        	int pos1=TREE_PREFIX.length()+TREE_PREFIX_TOP.length();//~1A2mR~
        	int pos2=Pline.indexOf(':',pos1);                      //~1A2mR~
            int sz;                                                //~1A2mI~
            if (pos2>0)                                            //~1A2mI~
        		sz=Integer.valueOf(Pline.substring(pos1,pos2)).intValue();//~1A2mR~
            else                                                   //~1A2mI~
        		sz=Integer.valueOf(Pline.substring(pos1)).intValue();//~1A2mI~
            if (sz==0)                                             //~1A2cI~
            	return true;                                       //~1A2cI~
            Ssize=sz;           //Action counter                   //~1A2cR~
            sz=Math.max(sz,NOTESTREE_INITCTR);//~1A2cI~
        	NotesTree tree=new NotesTree(Pnotes.yourcolor,sz,NOTESTREE_INCREMENT);//~1A2cR~
            if (pos2>0)                                            //~1A2mI~
            {                                                      //~1A2mI~
                pos2++;                                            //~1A2mI~
                int pos3=Pline.indexOf(':',pos2);                  //~1A2mR~
                if (pos3>0)                                        //~1A2mR~
                {                                                  //~1A2mR~
                    int goc=Integer.valueOf(Pline.substring(pos2,pos3)).intValue();//~1A2mR~
                    pos3++;                                        //~1A2mR~
                    int msgid=Integer.valueOf(Pline.substring(pos3)).intValue();//~1A2mR~
                    tree.setGameover(goc,msgid);                   //~1A2mR~
                }                                                  //~1A2mR~
            }                                                      //~1A2mI~
        	Pnotes.setTree(tree);                                  //~1A2cI~
        	return false;	//more                                 //~1A2cI~
        }                                                          //~1A2cI~
        String msg=Pline.substring(TREE_PREFIX.length());          //~1A2cI~
//        if (msg.charAt(0)==ActionMove.ACTION_MOVE_MOVE)          //~1A2cR~
//            ++Snumber;                                           //~1A2cR~
//        ActionMove a=new ActionMove(ActionMove.ACTION_MOVE_CMT,Snumber,msg);//~1A2cR~
//        Pnotes.getTree().add(a);                                 //~1A2cR~
    	ActionMove a=ActionMove.add(Pnotes.getTree(),msg);         //~1A2cR~//~1A2eR~
//      return (++Slineno>=Ssize);                                 //~1A2cR~
        return a==null;                                            //~1A2cI~
    }//setTree                                                     //~1A2cR~
    //*******************************************                  //~1A2eI~
    public void changeCoord(Notes Pnotes,int Pcolor/*yourColor*/)  //~1A2eI~
    {                                                              //~1A2eI~
		ActionMove a;                                              //~1A2eI~
    //************************                                     //~1A2eI~
//        if (Pnotes.yourcolor==Pcolor)                            //~1A2eR~
//            return;                                              //~1A2eR~
        if (Pcolor>0)                                              //~1A2eI~
            return;                                                //~1A2eI~
    	for (int ii=0;ii<size();ii++)                            //~1A2eI~
        {                                                          //~1A2eI~
        	a=get(ii);                                             //~1A2eI~
	    	a.reversePosition();                                   //~1A2eI~
        }                                                          //~1A2eI~
    }                                                              //~1A2eI~
    //*******************************************                  //~1A2mI~
    @Override                                                      //~1A2mI~
    public ActionMove firstElement()                               //~1A2mI~
    {                                                              //~1A2mI~
//		ActionMove a;                                              //~1A2mI~
    //************************                                     //~1A2mI~
    	if (size()==0)                                             //~1A2mI~
        	return null;                                           //~1A2mI~
        return super.firstElement();                               //~1A2mI~
    }                                                              //~1A2mI~
    //*******************************************                  //~1A2mI~
    @Override                                                      //~1A2mI~
    public ActionMove lastElement()                                //~1A2mI~
    {                                                              //~1A2mI~
//		ActionMove a;                                              //~1A2mI~
    //************************                                     //~1A2mI~
    	if (size()==0)                                             //~1A2mI~
        	return null;                                           //~1A2mI~
        return super.lastElement();                                //~1A2mI~
    }                                                              //~1A2mI~
}
