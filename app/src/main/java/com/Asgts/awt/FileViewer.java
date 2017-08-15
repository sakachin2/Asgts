//*CID://+1A4zR~:                             update#=  176;
//*************************************************************************
//1A4z 2014/12/09 FileDialog:view file by click twice
//*************************************************************************
package com.Asgts.awt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;                                    //~1A4zI~
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.Asgts.AG;
import com.Asgts.Prop;
import com.Asgts.AView;
import com.Asgts.R;

import jagoclient.Dump;
import jagoclient.board.NotesFmt;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.ChoiceAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.MyTextArea;


public class FileViewer extends CloseDialog
{
    private final static String PKEY_FILEENCODING_VIEWER="ViewerFileEncoding";
    private final static String ITEMACTION_FILEENCODING="FileEncoding";
    private final static int    DIALOG_TITLEID=R.string.Title_FileViewer;
    private final static int    DIALOG_TITLEID_FILENAME=R.string.Title_FileViewerFilename;
    private final static int    DIALOG_LAYOUTID=R.layout.fileviewer;
    private final static int    DIALOG_LAYOUTID_MDPI=R.layout.fileviewer_mdpi;
    private final static int    VIEWER_MAXSIZE=4096*16;
    private final static int    TEXTVIEWID=R.id.FileContents;      //~1A4zI~
    private ChoiceAction ChoiceEncoding;
    private MyTextArea textArea;
    private String fileName;
    private boolean swInitEnd;
    private String fileEncoding="";
//***********
    public FileViewer(CloseDialog Pdialog,String Pfnm)
    { 
     	super(Pdialog,AG.resource.getString(DIALOG_TITLEID),
     		(AG.screenDencityMdpi ? DIALOG_LAYOUTID_MDPI : DIALOG_LAYOUTID),//~1A4zR~
     		false/*modal*/,false/*wait input*/);
     	if (Dump.Y) Dump.println("FileViewer:"+Pfnm);
        fileName=Pfnm;
        setupEncoding();
        new ButtonAction(this,0,R.id.Close);                       //~1A4zR~
        new ButtonAction(this,0,R.id.Help);                        //~1A4zI~
        showDialog();
        swInitEnd=true;
    }
    //*****************************************************************
	private void showDialog()
    {
        setTitleFilename();
		setFileContents();
    	super.show();
        if (Dump.Y) Dump.println("FileViwer:after show");
    }
    //*****************************************************************
	private void setupEncoding()
    {
		ChoiceEncoding=new ChoiceAction(this,ITEMACTION_FILEENCODING,R.id.FileEncoding);
        for (int ii=0;ii<NotesFmt.ENCODINGTB.length;ii++)
        	ChoiceEncoding.add(NotesFmt.ENCODINGTB[ii]);
        String encoding=NotesFmt.getEncodingFromFilename(fileName);
        if (encoding==null)	//not standard filename extension
        	encoding=Prop.getPreference(PKEY_FILEENCODING_VIEWER,""/*default*/);
        fileEncoding=encoding;
        if (fileEncoding.equals(""))
			ChoiceEncoding.select(0);
        else
			ChoiceEncoding.select(fileEncoding);
    }
    //*****************************************************************
	private void setFileContents()
    {
    	String text=getFileContents();
        if (text==null)                                            //~1A4zI~
        	text="";                                               //~1A4zI~
        else                                                       //~1A4zI~
        if (text.equals(""))                                       //~1A4zR~
       		AView.showToast(R.string.ErrFileViewerNoText);
        if (textArea==null)
    		textArea=new MyTextArea(this,text,TEXTVIEWID,0/*row*/,0/*col*/,TextArea.SCROLLBARS_VERTICAL_ONLY);//~1A4zR~
        else
    		textArea.setText(text);
    }
	//***************************************
	public void doAction (String o)
	{
    	boolean swdispose=true;                                    //~1A4zI~
    //***************************
      	try
      	{
			if (o.equals(AG.resource.getString(R.string.Help)))    //~1A4zI~
			{                                                      //~1A4zI~
    			new HelpDialog(null,R.string.HelpTitle_FileViewer,"FileViewer"/*help text filename*/);//~1A4zI~
                swdispose=false;                                   //~1A4zI~
			}                                                      //~1A4zI~
            else                                                   //~1A4zI~
				super.doAction(o);	//Cancel                       //~1A4zR~
            if (swdispose)                                         //~1A4zI~
	        	dispose();           //dismiss                     //~1A4zR~
      	}
      	catch (Exception e)
      	{
      		Dump.println(e,"FileViewer:doActionException:"+o);
       		AView.showToast(R.string.Exception);
	        dispose();           //dismiss
      	}
    }
    //**************************************
    //*Spinner(ChiceAction)
    //**************************************
    @Override
	public void itemAction (String s, boolean flag)
	{
		String item="";
    //******************
		if (Dump.Y) Dump.println("FileDialog itemAction string="+s+",flag="+flag);
        if (!swInitEnd)
        	return;
        if (s.equals(ITEMACTION_FILEENCODING))
        {
        	int pos=ChoiceEncoding.getSelectedIndex();
            if (pos!=0)
				item=ChoiceEncoding.getSelectedItem();
            if (!item.equals(fileEncoding))	//changed
            {
                fileEncoding=item;
                Prop.putPreference(PKEY_FILEENCODING_VIEWER,item);
                setFileContents();
            }
        }
	}
    //**********************************************************************
    private void setTitleFilename()
    {
        String fnm=new File(fileName).getName();                   //+1A4zI~
        String dlgtitle=AG.resource.getString(DIALOG_TITLEID_FILENAME,fnm);//+1A4zR~
    	setTitle(dlgtitle);  //of Dialog
    }
    //**********************************************************************
    private String getFileContents()
    {
        BufferedReader br=getEncodingStream(fileName,fileEncoding);
        if (br==null)
        	return null;                                           //~1A4zR~
        StringBuffer sb=new StringBuffer("");                      //~1A4zR~
        readFile(br,VIEWER_MAXSIZE,sb);
        return new String(sb);
    }
    //*******************************************************
	private BufferedReader getEncodingStream(String Pfnm,String Pencoding)
	{
		InputStreamReader isr;
		BufferedReader br=null;
		try
		{
			FileInputStream fis=new FileInputStream(Pfnm);
        	if (Pencoding.equals(""))
				isr=new InputStreamReader(fis);
            else
				isr=new InputStreamReader(fis,Pencoding);
			br=new BufferedReader(isr);
		}
		catch (UnsupportedEncodingException e)
		{
        	Dump.println(e,"NotesFmt:getEncodingStream"+Pfnm+",encoding="+Pencoding);
            AView.showToastLong(AG.resource.getString(R.string.ErrFileViewerNotSupportedEncoding,Pfnm,Pencoding));
		}
		catch (Exception e)
		{
        	Dump.println(e,"NotesFmt:getEncodingStream"+Pfnm+",encoding="+Pencoding);//~3406I~//+1A4zI~            if (!swTest)
            AView.showToastLong(AG.resource.getString(R.string.ErrFileViewerOpen,Pfnm));
		}
        return br;
	}
    //******************************************************************************
	private int readFile(BufferedReader Pbr,int Pmaxsize,StringBuffer Pbuff)
    {
        String line;
    //************************************
        BufferedReader br=Pbr;
    	try
        {
            for (;;)
            {
            	line=br.readLine();
                if (line==null)  //eof
                	break;
                if (Pbuff.length()+line.length()>Pmaxsize)
                {
		            AView.showToastLong(AG.resource.getString(R.string.ErrFileViewerTooLarge,Pmaxsize));
                	return 4;
                }
                Pbuff.append(line+"\n");                           //~1A4zR~
            }
            br.close();
        }
		catch (Exception e)
		{
        	Dump.println(e,"FileViewer:readFile:"+fileName);
            AView.showToastLong(AG.resource.getString(R.string.ErrFileViewerRead));
		}
		return 0;
	}
}//class
