//*CID://+1AevR~:                             update#=  171;       //~1AevR~
//*************************************************************************//~v105I~
//1Aev 2016/06/29 (Bug)NPE when format was not selected            //~1AevI~
//1Aet 2016/06/29 (Bug)clipboard.sg was written when format type err(no record err)//~1AetI~
//1Aes 2016/06/29 (Bug) replay file saved by clipboard-save dose not show move msg//~1AesI~//~1AetR~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//*************************************************************************//~v105I~
package com.Asgts.awt;                                                //~1108R~//~1109R~//~1A21R~

import com.Asgts.AG;
import com.Asgts.Prop;                                             //~1A21R~
import com.Asgts.AView;                                            //~1A21R~
import com.Asgts.Utils;
import com.Asgts.awt.Checkbox;                                     //~1A2dI~
import com.Asgts.R;                                                //~1A21R~

import jagoclient.Dump;
import jagoclient.board.Notes;
import jagoclient.board.NotesFmt;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.ChoiceAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.MyTextArea;

                                                                   //~1319R~
public class FileDialogClipboard extends CloseDialog               //~1A4sR~
	implements FileDialogI                                         //~1A4sI~
{                                                                  //~1111I~
    private final static String PKEY_FILETYPE_CLIPBOARD="ClipboardFileType";//~1A4sR~
    private final static String ITEMACTION_FILETYPE="FileType";    //~1A30I~
    private int parmFlags;                                         //~1A4sI~
    public static final int FDC_RECORD=0x01;                                     //~1A4sI~
    public static final int FDC_TSUMESHOGI=0x02;                                    //~1A4sI~
    private FileDialogI FDI;                                       //~1A24I~
    private CloseDialog parentDialog;                              //~1A24I~
    protected Checkbox ReplayColorWhite;                           //~1A2dR~
    public boolean replaycolorwhite;                               //~1A2dR~
    private boolean swOK;                                          //~1A4sI~
    private ChoiceAction ChoiceFileType;                           //~1A4sR~
    private String selectedItemFileType="";                        //~1A4sR~
    private int selectedItemFileTypeId=-1;                         //~1A30I~
    private Notes clipboardNotes;                                  //~1A4sI~
    private MyTextArea textArea;                                   //~1A4sI~
//***********                                                      //~1319I~
    public FileDialogClipboard(FileDialogI Pfdi,CloseDialog Pdialog,String Ptitle,int Pparmflags)//~1A4sR~
    { 
     	super((Frame)Pfdi,Ptitle,                                  //~1A4sR~
     		(AG.screenDencityMdpi ? R.layout.filedialogclipboard_mdpi : R.layout.filedialogclipboard),//~1A4sI~
     		false/*modal*/,false/*wait input*/);                   //~1A4sI~
     	if (Dump.Y) Dump.println("FileDialogClipboard");           //~1A4sR~
        parmFlags=Pparmflags;                                      //~1A4sI~
        parentDialog=Pdialog;                                      //~1A24I~
        FDI=Pfdi;                                                  //~1A24I~
        ReplayColorWhite=new Checkbox(this,R.id.ReplayColorWhite); //~1A4sI~
	    ReplayColorWhite.setState(replaycolorwhite);               //~1A4sI~
		setupLoadType();                                           //~1A4sI~
		setClipboardContents();                                    //~1A4sI~
        new ButtonAction(this,0,R.id.OK);                          //~1A4sR~
        new ButtonAction(this,0,R.id.Save);                        //~1A4sI~
        new ButtonAction(this,0,R.id.Paste);                       //~1A4sI~
        new ButtonAction(this,0,R.id.Cancel);                      //~1A21R~
        new ButtonAction(this,0,R.id.Help);                        //~1A21R~
        setDismissActionListener(this/*DoActionListener*/);	//callback from DialogClosed at dismiss//~1A4sI~
    }                                                              //~1214I~
    //*****************************************************************//~1A30I~
	private void setupLoadType()                                   //~1A30I~
    {                                                              //~1A30I~
		ChoiceFileType=new ChoiceAction(this,ITEMACTION_FILETYPE,R.id.FileType);//~1A30R~
        for (int ii=0;ii<NotesFmt.FILETYPENAMETB.length;ii++)      //~1A30R~
        	ChoiceFileType.add(NotesFmt.FILETYPENAMETB[ii]);       //~1A30R~
        selectedItemFileType=Prop.getPreference(PKEY_FILETYPE_CLIPBOARD,""/*default*/);//~1A4sR~
        if (selectedItemFileType.equals(""))                       //~1A30R~
			ChoiceFileType.select(0);                              //~1A30I~
        else                                                       //~1A30I~
			ChoiceFileType.select(selectedItemFileType);           //~1A30R~
    }                                                              //~1A30I~
    //*****************************************************************//~1A4sI~
	private void setClipboardContents()                            //~1A4sR~
    {                                                              //~1A21I~
    	String text=getClipboardText();                            //~1A4sR~
        if (text==null || text.equals(""))                         //~1A4sI~
        {                                                          //~1A4sI~
        	text="";                                               //~1A4sI~
       		AView.showToast(R.string.ErrClipboardNoText);          //~1A4sI~
        }                                                          //~1A4sI~
        if (textArea==null)                                        //~1A4sI~
    		textArea=new MyTextArea(this,text,R.id.ClipboardContents,0/*row*/,0/*col*/,TextArea.SCROLLBARS_VERTICAL_ONLY);//~1A4sR~
        else                                                       //~1A4sI~
    		textArea.setText(text);                                //~1A4sI~
    }                                                              //~1A21I~
	//***************************************                      //~1402R~
	public void doAction (String o)                                //~1403I~
	{                                                              //~1403I~
    //***************************                                  //~v105I~
      	try                                                        //~1A4sR~
      	{                                                          //~1A4sR~
			if (o.equals(AG.resource.getString(R.string.OK)))               //~1403R~//~1A4sR~
			{                                                      //~1A4sR~
    			if (fileOK())      //file is selected              //~1A4sR~
                {                                                  //~1A4sI~
                	swOK=true;                                     //~1A4sI~
	        		dispose();	         //dismiss                 //~1A4sR~
                }                                                  //~1A4sI~
			}                                                      //~1A4sR~
			else if (o.equals(AG.resource.getString(R.string.Save)))//~1A4sI~
			{                                                      //~1A4sI~
    			fileSave();      //file is selected                 //~1A4sI~
			}                                                      //~1A4sI~
			else if (o.equals(AG.resource.getString(R.string.Paste)))//~1A4sI~
			{                                                      //~1A4sI~
    			filePaste();      //file is selected                //~1A4sI~
			}                                                      //~1A4sI~
			else if (o.equals(AG.resource.getString(R.string.Cancel)))//~1A4sR~
			{                                                      //~1A4sR~
	        	dispose();	         //dismiss                     //~1A4sR~
			}                                                      //~1A4sR~
			else if (o.equals(AG.resource.getString(R.string.Help)))//~1A4sR~
			{                                                      //~1A4sR~
    			new HelpDialog(null,R.string.HelpTitle_FileDialogClipboard,"FileDialogClipboard"/*help text filename*/);//~1A4sR~
			}                                                      //~1A4sR~
        	else if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~1A4sR~
        	{               //callback from Dialog after currentLayout restored//~1A4sR~
		    	if (Dump.Y) Dump.println("FileDialogClipboard ActionDismissDialog note="+(clipboardNotes==null?"null":clipboardNotes.toString()));//~1A4sR~
            	callbackLoad();                                    //~1A4sR~
        	}                                                      //~1A4sR~
			else super.doAction(o);	//Cancel                       //~1A4sR~
      	}                                                          //~1A4sR~
      	catch (Exception e)                                        //~1A4sR~
      	{                                                          //~1A4sR~
      		Dump.println(e,"FileDialogClipboard:doActionException:"+o);//~1A4sR~
       		AView.showToast(R.string.Exception);                   //~v105I~//~1A4sR~
	    	dispose();	         //dismiss                         //~1A4sR~
      	}                                                          //~1A4sR~
    }                                                              //~1403R~
    //**************************************                       //~1A4sI~
    //*Spinner(ChiceAction)                                        //~1A4sI~
    //**************************************                       //~1A4sI~
    @Override                                                      //~1A4sI~
	public void itemAction (String s, boolean flag)                //~1A4sI~
	{                                                              //~1A4sI~
		String item="";                                            //~1A4sI~
    //******************                                           //~1A4sI~
		if (Dump.Y) Dump.println("FileDialog itemAction string="+s+",flag="+flag);//~1A4sI~
        if (s.equals(ITEMACTION_FILETYPE))                         //~1A4sI~
        {                                                          //~1A4sI~
        	int pos=ChoiceFileType.getSelectedIndex();             //~1A4sI~
            if (pos!=0)                                            //~1A4sI~
            {                                                      //~1A4sI~
				 item=ChoiceFileType.getSelectedItem();            //~1A4sI~
            }                                                      //~1A4sI~
            selectedItemFileTypeId=NotesFmt.FILETYPEIDTB[pos];	   //~1A4sI~
            if (!item.equals(selectedItemFileType))                //~1A4sI~
            {                                                      //~1A4sI~
                selectedItemFileType=item;                         //~1A4sI~
                Prop.putPreference(PKEY_FILETYPE_CLIPBOARD,item);  //~1A4sI~
            }                                                      //~1A4sI~
        }                                                          //~1A4sI~
	}                                                              //~1A4sI~
    //**********************************************************************//~1A4sI~
    //*                                                            //~1A4sI~
    //**********************************************************************//~1A4sI~
    private boolean fileOK()                                       //~1A4sI~
    {                                                              //~1A4sR~
	    boolean rc=false;                                          //~1A4sI~
		replaycolorwhite=ReplayColorWhite.getState();              //~1A4sI~
        if (selectedItemFileTypeId<0)                              //~1A4sI~
       		AView.showToast(R.string.ErrClipboardNoFileType);      //~1A4sI~
        else                                                       //~1A4sI~
	    	rc=createNotes();                                      //~1A4sR~
        return rc;                                                 //~1A4sI~
    }                                                              //~1A4sI~
    //************************                                     //~1A4sI~
    private void filePaste()                                       //~1A4sI~
    {                                                              //~1A4sI~
		setClipboardContents();                                    //~1A4sI~
    }                                                              //~1A4sI~
    //************************                                     //~1A4sI~
    private void fileSave()                                        //~1A4sM~
    {                                                              //~1A4sM~
        if (selectedItemFileTypeId<0)                              //~1AevI~
        {                                                          //~1AevI~
       		AView.showToast(R.string.ErrClipboardNoFileType);      //~1AevI~
            return;                                                //~1AevI~
        }                                                          //~1AevI~
	    boolean rc=createNotes();                                  //~1A4sM~
        if (rc)                                                    //~1A4sM~
        {                                                          //~1A4sI~
//            clipboardNotes.setMoveMsg();                           //~1A4sI~//~1AesR~
        	saveDialog(clipboardNotes);                            //~1A4sI~
        }                                                          //~1A4sI~
    }                                                              //~1A4sM~
    //************************                                     //~1A4sI~
    private void saveDialog(Notes Pnotes)                          //~1A4sR~
    {                                                              //~1A4sI~
    	if (Dump.Y) Dump.println("FileDialogClipboard:saveDialog");//~1A4sI~
    	if ((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0)               //~1A4sI~
        {                                                          //~1A4sI~
			saveGameDirect(Pnotes);                                //~1A4sI~
            return;                                                //~1A4sI~
        }                                                          //~1A4sI~
    	FileDialog fd=new FileDialog(this/*FileDialogI*/,AG.resource.getString(R.string.Title_SaveFile),FileDialog.SAVE);//~1A4sR~
        fd.setSaveNotes(Pnotes,FileDialog.GAMES_EXT);              //~1A4sI~
        fd.show();                                                 //~1A4sI~
    }                                                              //~1A4sI~
    //************************                                     //~1A4sI~
    public void saveGameDirect(Notes Pnotes)                       //~1A4sI~
    {                                                              //~1A4sI~
    	if (Dump.Y) Dump.println("FileDialogClipboard:saveGameDirect");//~1A4sI~
        FileDialog.saveDirect(this,Pnotes,FileDialog.GAMES_EXT);   //~1A4sI~
	}                                                              //~1A4sI~
    //**********************************************************************//~1A4sI~
    private String getClipboardText()                              //~1A4sI~
    {                                                              //~1A4sI~
		String text=Utils.clipboard_getText();                     //~1A4sI~
        return text;                                               //~1A4sI~
    }                                                              //~1A4sI~
    //**********************************************************************//~1403I~
    private boolean createNotes()                                  //~1A4sR~
    {                                                              //~1403I~
    	String text=textArea.getText();                            //~1A4sI~
      	Notes notes=Notes.loadClipboard(text,selectedItemFileTypeId);//~1A4sR~
        if (notes==null)                                            //~1A4sI~
        	return false;                                          //~1A4sI~
        int rc=notes.chkValidity();	//1:no move,2:no init piece    //~1A4sI~
    	if ( (parmFlags & FDC_RECORD)!=0 && rc>=1)                 //~1A4sR~
        {                                                          //~1A4sI~
        	AView.showToastLong(R.string.ErrClipboardHasNoRecord); //~1A4sI~
        	return false;                                          //~1AetI~
        }                                                          //~1A4sI~
        else                                                       //~1A4sI~
        if ( (parmFlags & FDC_TSUMESHOGI)!=0 && rc>=2)             //~1A4sR~
        {                                                          //~1A4sI~
        	AView.showToastLong(R.string.ErrClipboardHasNoPiece);  //~1A4sR~
        	return false;                                          //~1A4sR~
        }                                                          //~1A4sI~
        clipboardNotes=notes;                                      //~1A4sI~
        return true;                                               //~1A4sR~
    }                                                              //~1403I~
    //******************************************************************//~1A21I~
    //*after dismiss                                               //~1A22I~
    //******************************************************************//~1A22I~
    private void callbackLoad()                                    //~1A21I~
    {                                                              //~1A21I~
    	if (swOK)                                                  //~1A4sI~
	        FDI.fileDialogLoaded(parentDialog,clipboardNotes);     //~1A4sR~
    }                                                              //~1A21I~
//************************************************************     //~1A4sI~
//*FileDialogI for save                                            //~1A4sI~
//************************************************************     //~1A4sI~
    @Override //GoFrame                                            //~1A4sI~
    public void fileDialogLoaded(CloseDialog Pgq,Notes Pnotes){}   //~1A4sI~
	@Override                                                      //~1A4sI~
	public void fileDialogSaved(String Pnotename)                  //~1A4sI~
    {                                                              //~1A4sI~
    	if (Dump.Y) Dump.println("FileDialogClipboard:fileDialogSaved callback from FileDialog:save");//~1A4sI~
    }                                                              //~1A4sI~
	@Override                                                      //~1A4sI~
	public void fileDialogNotSaved()                               //~1A4sI~
    {                                                              //~1A4sI~
    	if (Dump.Y) Dump.println("FileDialogClipboard:fileDialogNotSaved callback from FileDialog:save");//~1A4sI~
    }                                                              //~1A4sI~
}//class                                                           //~1318R~
