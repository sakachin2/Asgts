//*CID://+1A4yR~:                                   update#=   86; //~1A4yR~
//****************************************************************************//~@@@1I~
//1A4y 2014/12/08 (Bug)Local game reload by game question;warning if gameovered//~1A4yI~
//1A2i 2013/04/03 (Bug)Resig on Local board; Endgame question is End game?//~1A2iI~
//1A2g 2013/04/03 your color not by gameoptoions but by notes.yourcolor//~1A2gI~
//                (gameoption for partner is same as requester)    //~1A2gI~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A14 2013/03/10 FreeBoard Title                                  //~1A14I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//****************************************************************************//~@@@1I~
package jagoclient;                                                //~@@@2R~

import jagoclient.board.Notes;
import jagoclient.board.NotesTree;
import jagoclient.gui.CloseFrame;
import jagoclient.partner.EndGameQuestion;
import jagoclient.partner.GameQuestion;
import jagoclient.FreeBoardQuestion;
import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.AView;
import com.Asgts.R;                                                //~@@@2R~
import com.Asgts.awt.Component;


public class LocalFrame extends CloseFrame                              //~@@@2R~
{	                                                               //~v107I~
    public LocalGoFrame PGF;                                       //~@@@2I~
    protected LocalGameQuestion LGQ;                               //~1A14I~
	String Dir;
	boolean Block;                                                 //~@@@2I~
    private Notes notes;
    private boolean swFGQ;                                                 //~1A4yI~
    //**********************************************               //~1A24I~
    //*from LocalFrame<--ReplayFrame                               //~1A24I~
    //**********************************************               //~1A24I~
    public LocalFrame (MainFrame Pmf)                              //~1A24I~
	{                                                              //~1A24I~
  		super(0/*layout resid,having no frame*/,"ReplayFrame");    //~1A24I~
        if (Dump.Y) Dump.println("LocalFrame:ReplayFrame");        //~1A24I~
		Dir="";                                                    //~1A24I~
    }                                                              //~1A24I~
    //************************                                     //~1A24I~
    public LocalFrame (LocalGameQuestion Plgq)                     //~@@@2R~
	{                                                              //~@@@2R~
    	this(Plgq,null);                                           //~1A24I~
    }                                                              //~1A24I~
    //************************                                     //~1A24I~
    public LocalFrame (GameQuestion Plgq,Notes Pnotes)             //~1A24I~
    {                                                              //~1A24I~
//		super("LocalFrame");                                       //~@@@2R~
//		super(0/*layout resid,having no frame*/,"LocalFrame");     //~@@@2I~//~1A10R~
  		super(0/*layout resid,having no frame*/,                   //~1A10I~
				(Plgq instanceof FreeBoardQuestion)?"FreeGoFrame":"LocalFrame");//~1A10I~
		swFGQ=(Plgq instanceof FreeBoardQuestion);                 //~1A4yI~
        LGQ=(LocalGameQuestion)Plgq;                                                  //~1A14I~
        notes=Pnotes;	//GoFrame                              //~1A24R~
		int totaltime=Plgq.totaltime;                              //~@@@2R~
		int extratime=Plgq.extratime;                              //~@@@2I~
		int gameoptions=Plgq.gameoptions;                          //~@@@2I~
		int handicap=Plgq.Handicap;                                //~v1A0I~
        int sz;                                                    //~@@@2I~
    	int color=((gameoptions & GameQuestion.GAMEOPT_MOVEFIRST)!=0? 1:-1);//~1A2gI~
        if (notes!=null)                                           //~1A24I~
        {                                                          //~1A24I~
        	gameoptions=notes.gameoptions;	//determin color       //~1A24I~
        	totaltime=notes.totalTime;                             //~1A24I~
        	extratime=notes.extraTime;                             //~1A24I~
        	handicap=0;                                            //~1A24I~
            color=notes.yourcolor;                                 //~1A2gI~
            if (!chkNotesValidity(notes))                          //~1A4yI~
            	return;                                            //~1A4yI~
        }                                                          //~1A24I~
		Block=false;
		Dir="";
//  	int color=((gameoptions & GameQuestion.GAMEOPT_MOVEFIRST)!=0? 1:-1);//~@@@2I~//~1A2gR~
        sz=AG.BOARDSIZE_SHOGI;                                      //~@@@2R~//~v1A0R~
        openGF(color,sz,totaltime,extratime,gameoptions,handicap); //~1A10R~
    }                                                              //~1A10I~
    protected void openGF(int color,int sz,int totaltime,int extratime,int gameoptions,int handicap)//~1A10R~
    {                                                              //~1A10I~
		PGF=new LocalGoFrame(this,                                //~@@@2R~
        		R.string.Title_LocalViewer,                        //~1A10I~
				color,sz,                                          //~@@@2R~
				totaltime*60,extratime,gameoptions,handicap);//~@@@2R~//~v1A0R~
        if (notes!=null)
        {
        	PGF.reloadNotes=notes;//GoFrame//~1A24R~
        	PGF.loadFileNotes(notes); //TGF remaining time,tray,piece//~1A24R~
            setGameTitle(notes.name);                                  //~1A24I~
        }
        PGF.start();                                               //~@@@2I~
	}
    //***************************                                  //~1A24I~
    private void setGameTitle(String Pname)               //~1A24I~
    {                                                              //~1A24I~
        String title;                                              //~1A24I~
        title=AG.resource.getString(R.string.Title_LocalViewer)+":"+Pname;//~1A24I~
        (new Component()).setTitle(title);                         //~1A24I~
    }                                                              //~1A24I~
    //***************************                                  //~1A24I~
	public void doAction (String o)
	{                                                              //~v108I~
		super.doAction(o);
	}

	public void doclose ()
    {                                                              //~v108I~
		super.doclose();
	}

	public boolean close ()
    {                                                              //~@@@2I~
    	return true;                                               //~@@@2I~
	}
	public boolean moveset (String c, int i, int j, int bt, int bm,
		int wt, int wm)
	{	if (Block) return false;
		return true;
	}


	public void set (int i, int j)
	{
	}


	public void endgame ()
	{	if (Block) return;
		Block=true;
		new EndGameQuestion(this);                                 //~@@@2I~
	}
	public void endgame (boolean Presign)                          //~1A2iI~
	{	if (Block) return;                                         //~1A2iI~
		Block=true;                                                //~1A2iI~
		new EndGameQuestion(this,Presign);                         //~1A2iI~
	}                                                              //~1A2iI~

	public void doendgame ()
	{                                                              //~@@@2I~
//  	PGF.doscore();                                             //~@@@2R~
    	PGF.doscore(-PGF.B.currentColor());	//winner is opponent of current color//~@@@2I~
		Block=false;
//        AView.endGameConfirmed();                                //~@@@2R~
	}

	public void declineendgame ()
	{                                                              //~@@@2I~
		Block=false;
	}

	public void doresult (int b, int w)
	{                                                              //~@@@2I~
		Block=false;
	}

	public void declineresult ()
	{                                                              //~@@@2I~
		Block=false;
	}


//********************************************************         //~@@@@I~
//*from PartnerGoFrame:doclose                                     //~@@@@I~
//********************************************************         //~@@@@I~
	public void boardclosed (LocalGoFrame pgf)		
	{                                                              //~@@@2R~
	}
//********************************************************         //~1A4yI~
//** ret false if invalid Notes                                    //~1A4yI~
//********************************************************         //~1A4yI~
    private boolean chkNotesValidity(Notes Pnotes)                  //~1A4yR~
    {                                                              //~1A4yI~
    	boolean rc=true;                                           //~1A4yI~
    //***********************                                      //~1A4yI~
        NotesTree nt=Pnotes.getTree();                               //~1A4yI~
        if (nt!=null)                                              //~1A4yI~
        {                                                          //~1A4yI~
        	int treesz=nt.size();                          //~1A4yI~
            if (!swFGQ)//LocalGame reload note                     //~1A4yI~
            {                                                      //~1A4yI~
                if (treesz>0 && Pnotes.moves0==0) //not interupted file//~1A4yI~
                {                                                  //~1A4yI~
                    AView.showToastLong(R.string.ErrNotRestartNotes,Pnotes.getSavedFilename());//~1A4yI~
                    return false;   //ignore this file             //+1A4yR~
                }                                                  //~1A4yI~
            }                                                      //~1A4yI~
        	if (nt.winner!=0)                                      //~1A4yI~
            {                                                      //~1A4yI~
		    	AView.showToastLong(R.string.ErrGameOveredNotes,Pnotes.getSavedFilename());//~1A4yI~
            }                                                      //~1A4yI~
        }                                                          //~1A4yI~
        return rc;                                                 //~1A4yI~
    }                                                              //~1A4yI~
}

