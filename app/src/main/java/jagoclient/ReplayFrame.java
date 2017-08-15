//*CID://+1A4sR~:                                   update#=   99; //~1A4sR~
//****************************************************************************//~@@@1I~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//1A44 2014/10/11 NPE when note has no record                      //~1A44I~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A14 2013/03/10 FreeBoard Title                                  //~1A14I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//****************************************************************************//~@@@1I~
package jagoclient;                                                //~@@@2R~

import jagoclient.board.Notes;
import jagoclient.gui.CloseDialog;
import com.Asgts.awt.FileDialog;
import com.Asgts.awt.FileDialogClipboard;
import com.Asgts.awt.FileDialogI;
import com.Asgts.AG;
import com.Asgts.AView;
import com.Asgts.R;

public class ReplayFrame extends FreeFrame implements FileDialogI                             //~@@@2R~//~1A10R~//~1A2dR~
{	                                                               //~v107I~
	private int replayColor=1;                                         //~1A2dI~
    private FileDialog fd;
    private FileDialogClipboard fdc;                               //~1A4sI~
    private boolean swClipboard;                                   //~1A4sI~
    public boolean swClipboardErr;                                 //~1A4sI~
    //***********************************************              //~1A2dI~
    public ReplayFrame (MainFrame Pmf)                             //~1A2dI~
    {                                                              //~1A2dI~
//		super(new FreeBoardQuestion(Pmf));    //FreeFrame          //~1A2dR~
  		super(Pmf);    //FreeFrame                                 //~1A2dI~
        swClipboard=Pmf.boardType==jagoclient.MainFrame.BT_REPLAY_CLIPBOARD;  //~1A4sI~
      if (swClipboard)                                             //~1A4sI~
      {                                                            //~1A4sI~
      	loadClipboard();                                           //~1A4sI~
      }                                                            //~1A4sI~
      else                                                         //~1A4sI~
      {                                                            //~1A4sI~
    	fd=new FileDialog(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_ReplayFile),FileDialog.LOAD_REPLAY);//~1A2dR~
        fd.setFilterString(FileDialog.GAMES_EXT);                  //~1A2dI~
        fd.show();  //callback loadFileNotes                       //~1A2dI~
      }                                                            //~1A4sI~
    }                                                              //~1A2dI~
    //***********************************************              //~1A2dI~
    //*as LocalFrame                                               //~1A2dI~
    //***********************************************              //~1A2dI~
    protected void openGF(int Pcolor,Notes Pnotes)                                        //~1A10I~//~1A2dR~
    {                                                              //~1A10I~
		boardName=Pnotes.name;                                     //~1A2dR~
        if (Pnotes.getTree()==null || Pnotes.getTree().size()==0)  //~1A2dI~
        {                                                          //~1A2dI~
            AView.showToastLong(R.string.ErrReplayFileHasNoRecord);//~1A2dI~
        	return;                                                //~1A44R~
        }                                                          //~1A2dI~
		FGF=new ReplayGoFrame(this,                                //~@@@2R~//~1A10R~//~1A2dR~
				Pcolor,Pnotes);                                    //~1A2dR~
        FGF.start();                                               //~1A10R~
	}
    //***********************************************              //~1A2dI~

	public void doAction (String o)
	{                                                              //~v108I~
		super.doAction(o);                                         //~1A2dR~
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

//********************************************************         //~@@@@I~
//*from PartnerGoFrame:doclose                                     //~@@@@I~
//********************************************************         //~@@@@I~
	public void boardclosed (FreeGoFrame pgf)		               //~1A10R~
	{                                                              //~@@@2R~
	}
//************************************************************     //~1A2dI~
//*FileDialogI                                                     //~1A4sI~
//*for clipboard called with note=null even when load failed       //~1A4sI~
//************************************************************     //~1A4sI~
    @Override //GoFrame                                            //~1A2dI~
    public void fileDialogLoaded(CloseDialog Pgq,Notes Pnotes) //GameQuestion request Reload File//~1A2dI~
    {                                                              //~1A2dI~
    	if (Pnotes==null)                                          //~1A4sI~
        	return;                                                //~1A4sI~
	   if (swClipboard)                                            //~1A4sI~
    	replayColor=fdc.replaycolorwhite?-1:1;                     //~1A4sI~
       else                                                        //~1A4sI~
    	replayColor=fd.replaycolorwhite?-1:1;                      //~1A2dI~
    	if (Dump.Y) Dump.println("ReplayFrame fileDialogLoaded white="+replayColor);//~1A4sI~
        openGF(replayColor,Pnotes);                                //~1A2dR~
    }                                                              //~1A2dI~
	@Override
	public void fileDialogSaved(String Pnotename) {}
	@Override
	public void fileDialogNotSaved() {}
//************************************************************     //~1A4sI~
//*load from clipboard                                             //~1A4sI~
//************************************************************     //~1A4sI~
    private void loadClipboard()                                //~1A4sI~
    {                                                              //~1A4sI~
    	fdc=new FileDialogClipboard(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_ReplayClipboard),FileDialogClipboard.FDC_RECORD);//+1A4sR~
        fdc.show();  //callback FileDialogLoaded                   //~1A4sR~
    }                                                              //~1A4sI~
//    private boolean loadClipboard()                              //~1A4sI~
//    {                                                            //~1A4sI~
//        if (Dump.Y) Dump.println("ReplayFrame loadClipboard");   //~1A4sI~
//        Notes notes=loadClipboardNotes();                        //~1A4sI~
//        if (notes==null)                                         //~1A4sI~
//            return false;                                        //~1A4sI~
//        replayColor=Note.yourcolor;                              //~1A4sI~
//        openGF(replayColor,notes);                               //~1A4sI~
//        return true;                                             //~1A4sI~
//    }                                                            //~1A4sI~
////************************************************************   //~1A4sI~
//    private Notes loadClipboardNotes(String Pformat,String Pencoding)//~1A4sI~
//    {                                                            //~1A4sI~
//        if (Dump.Y) Dump.println("ReplayFrame loadClipboardNotes");//~1A4sI~
//        Notes notes=Notes.load(Pformat,Pencoding);               //~1A4sI~
//        if (return notes;                                        //~1A4sI~
//            return null;                                         //~1A4sI~
//    }                                                            //~1A4sI~
}

