//*CID://+1Ah9R~:                             update#=  159;       //+1Ah9R~
//*************************************************************************//~v105I~
//1Ah9 2016/11/15 FileDialog:allow wildcard for filter             //+1Ah9I~
//1Ah8 2016/11/15 FileDialog:support select all button             //~1Ah8I~
//1Ah7 2016/11/15 more detail msg for invalid note data at reload(No move and no initial piece)//~1Ah7I~
//1A7a 2015/02/24 filedialog_mdpi.xml                              //~1A7aI~
//1A4z 2014/12/09 FileDialog:view file by click twice              //~1A4zI~
//1A4s 2014/12/06 utilize clipboard(view dialog)                   //~1A4sI~
//1A4r 2014/12/05 change savedir to editable                       //~1A4rI~
//1A4q 2014/12/05 (Bug)Cancel save dose ot change to Close button enabled//~1A4qI~
//1A4p 2014/12/04 FileDialog:keep cursor after deleted 1 entry     //~1A4nI~
//1A4n 2014/12/04 FileDialog:multiple delete support               //~1A4eI~
//1A4e 2014/11/29 FileDialog redundant putprefrence                //~1A4eI~
//1A4d 2014/11/29 FileDialog title:filter is not updated           //~1A4dI~
//1A4c 2014/11/29 FileDialog sort:IllegalArgumentException comparizonmethod violate(long to int may change sign)//~1A4cI~
//1A4a 2014/11/29 FileDialog:open when selected item is clicked    //~1A4aI~
//1A42 2014/09/19 save to private if sdcard is not available       //~1A42I~
//1A33 2013/04/17 load tsumego file on freeboard                   //~1A33I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2f 2013/04/02 display current dir for selection of filter changed case//~1A2fI~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A26I~
//1A26 2013/03/25 save folder fix option(no dislog popup for save game)//~1A26I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A21 2013/03/22 File Dialog on Free Board                        //~1A21I~
//1054:121114 filedialog could not traverse dir                    //~v105I~
//1053:121113 exception(wrong thread) when filelist up/down for sgf file read//~v105I~
//*************************************************************************//~v105I~
package com.Asgts.awt;                                                //~1108R~//~1109R~//~1A21R~

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.Asgts.AG;
import com.Asgts.Alert;                                            //~1A21R~
import com.Asgts.AlertI;
import com.Asgts.Prop;                                             //~1A21R~
import com.Asgts.AView;                                            //~1A21R~
import com.Asgts.awt.Checkbox;                                     //~1A2dI~
import com.Asgts.R;                                                //~1A21R~

import jagoclient.Dump;
import jagoclient.FreeGoFrame;
import jagoclient.Global;
import jagoclient.board.Notes;
import jagoclient.board.NotesFmt;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.ChoiceAction;
import jagoclient.gui.CloseDialog;
import jagoclient.partner.GameQuestion;

                                                                   //~1319R~
public class FileDialog extends CloseDialog                        //~1403R~
	implements FilenameFilter                                      //~1A21I~
    , AlertI                                                       //~1A4nR~
    , ListI                                                        //~1A4aI~
{                                                                  //~1111I~
    private static final int LAYOUTID=R.layout.filedialog;         //~1A7aI~
    private static final int LAYOUTID_MDPI=R.layout.filedialog_mdpi;//~1A7aI~
    private final static String PKEY_FILETYPE="LoadFileType";      //~1A30I~
    private final static String PKEY_FILEENCODING="LoadFileEncoding";//~1A30I~
    private final static String PKEY_REPLAYFILTER="ReplayFilter";  //~1A30I~
    private final static String ITEMACTION_FILETYPE="FileType";    //~1A30I~
    private final static String ITEMACTION_FILEENCODING="FileEncoding";//~1A30I~
    private final static Color  COLOR_FILENAME=new Color(0x80,0xc0,0x80);//~1A21I~
//  private static final String SNAPSHOT_PATH="games";          //~1A21R~//~1A22R~
	private static final String GAMES_PATH="games";                 //~1A21I~
	public static final String SNAPSHOT_EXT="ss";                 //~1A21I~
	public static final String GAMES_EXT="sg";                    //~1A21I~
    private int mode;                                              //~1319I~
    public final static int LOAD   =0;	//read  permisson          //~1319I~
    public final static int SAVE   =1;	//write permisson          //~1319I~
    public final static int LOAD_REPLAY =2;                        //~1A26I~
    public final static int LOAD_FGFNOTES=3;                       //~1A33I~
                                                                   //~1402I~
    private String title;                                          //~1319I~
//    private CloseFrame parentFrame;                                          //~1319I~//~v105R~//~1A21R~
    private String dirname="";                                     //~1324R~
    private String filterString=null;                              //~1324R~
    private String inputFilterString="";                           //~1A4dI~
    private FilenameFilter filterCB=null;                          //~1407R~
    public  String[] namelist;                                      //~1319I~//~1402R~
	private File[] filelist;                                       //~1319I~
	private int[]  name2file;                                      //~1330I~
	private File   selectedFile;                                   //~1403R~
	private static String selectedFilename=null;                   //~1319I~//~1330R~
	private static String selectedDirname=null;                    //~1330I~
                          //~1402I~
    private String savefilename="";                                //~1402I~
//    private boolean afterDismiss;                                   //~1330I~//~1A21R~
    private TextField input;
    private TextField inputfilter;                                 //~1A21I~
	private Lister lister;                                         //~1403I~
    private ButtonAction buttonSave; //~1403I~
    private ButtonAction buttonDeleteMulti;                        //~1A4nR~
    private String updownpath;                                            //~1403I~
    private Color bgcolor=Global.gray;                             //~1403I~
//    private boolean swSaveBitmap;                                  //~1404I~//~1A21R~
    private boolean swSaveGame=false;                              //~1407I~
    private boolean swSaveTxt=false;                               //~1418I~
    private Notes saveNotes;                                       //~1A21I~
    private boolean swLoad;                                                //~1A21I~
    private boolean swSave;                                        //~1A22I~
    private String loadFilename;                                   //~1A21I~
    private static String Ssdpath;                                 //~1A21I~
    private FileDialogI FDI;                                       //~1A24I~
    private CloseDialog parentDialog;                              //~1A24I~
    private boolean swReplayFile;                                  //~1A2dR~
    protected Checkbox ReplayColorWhite;                           //~1A2dR~
    public boolean replaycolorwhite;                               //~1A2dR~
    private ChoiceAction ChoiceFileType,ChoiceEncoding;                      //~1A30I~
    private String selectedItemFileType="",selectedItemEncoding="";//~1A30R~
    private int selectedItemFileTypeId=-1;                         //~1A30I~
    private boolean swFGF;                                         //~1A33I~
    private boolean swDeleteMultiple;                              //~1A4nR~
    private boolean swCancel;                                      //~1A4qI~
    private Drawable btnBackground;                                //~1A4nR~
    private boolean swOpenByClick;                                 //~1A4zI~
//*****************************************************************//~1A4sI~
//*from Dialog                                                     //~1A4sI~
//*****************************************************************//~1A4sI~
    public FileDialog(CloseDialog Pdialog,String Ptitle,int Pmode) //~1A4sI~
    {                                                              //~1A4sI~
//   	super(Pdialog,Ptitle,R.layout.filedialog,false/*modal*/,false);//~1A4sI~//~1A7aR~
     	super(Pdialog,Ptitle,                                      //~1A7aI~
				(AG.layoutMdpi ? LAYOUTID_MDPI : LAYOUTID),        //~1A7aI~
				false/*modal*/,false);                             //~1A7aI~
        FileDialogI fdi=(FileDialogI)Pdialog;                      //~1A4sI~
        initThis(fdi,Pdialog,Ptitle,Pmode);                        //~1A4sI~
    }                                                              //~1A4sI~
//*****************************************************************//~1A4sI~
//*from Frame                                                      //~1A4sI~
//*****************************************************************//~1A4sI~
//  public FileDialog(CloseFrame  Pframe,String Ptitle,int Pmode)  //~1324I~//~1A24R~
//  public FileDialog(FileDialogI Pfdi,CloseFrame Pframe,String Ptitle,int Pmode)//~1A24R~
    public FileDialog(FileDialogI Pfdi,CloseDialog Pdialog,String Ptitle,int Pmode)//~1A24I~
    { 
//   	super(Pframe,"FileDialog",true/*modal*/);                  //~1403I~//~1A21R~
//   	super((Frame)Pfdi,Ptitle,R.layout.filedialog,false/*modal*/,false);//~1A21I~//~1A7aR~
     	super((Frame)Pfdi,Ptitle,                                  //~1A7aI~
				(AG.layoutMdpi ? LAYOUTID_MDPI : LAYOUTID),        //~1A7aI~
				false/*modal*/,false);                             //~1A7aI~
        initThis(Pfdi,Pdialog,Ptitle,Pmode);                       //~1A4sI~
    }                                                              //~1A4sI~
    public void initThis(FileDialogI Pfdi,CloseDialog Pdialog,String Ptitle,int Pmode)//~1A4sI~
    {                                                              //~1A4sI~
//   	afterDismiss=isAfterDismiss();                             //~1A21R~
//   	if (Dump.Y) Dump.println("FileDialog:afterdismiss="+afterDismiss);//~1506R~//~1A21R~
     	if (Dump.Y) Dump.println("FileDialog");                    //~1A21I~
//      parentFrame=Pframe;                                        //~1403M~//~v105R~//~1A21R~//~1A24R~
        parentDialog=Pdialog;                                      //~1A24I~
        listInterface=this;	//callback from List                   //~1A4aI~
        FDI=Pfdi;                                                  //~1A24I~
		title=Ptitle;                                              //~1403M~
        mode=Pmode;                                                //~1403M~
        if (Pmode==LOAD_REPLAY)                                    //~1A2dR~
        {                                                          //~1A2dR~
        	mode=LOAD;                                             //~1A2dR~
            swReplayFile=true;                                     //~1A2dR~
        }                                                          //~1A2dR~
        else                                                       //~1A33I~
        if (Pmode==LOAD_FGFNOTES) 	//tsumege file on freeboard    //~1A33R~
        {                                                          //~1A33I~
        	mode=LOAD;                                             //~1A33I~
            swFGF=true;                                            //~1A33I~
        }                                                          //~1A33I~
//        if (Pframe instanceof FilenameFilter)                      //~1407I~//~1A21R~
//            filterCB=(FilenameFilter)Pframe;                       //~1407R~//~1A21R~
//        else                                                       //~1407I~//~1A21R~
//            filterCB=null;                                         //~1407I~//~1A21R~
          filterCB=this;                                           //~1A21I~
//  	swSaveBitmap=(Global.resourceString("Save_Bitmap").equals(Ptitle)); // save to bitmap;//~1404I~//~1A21R~
                                                                   //~1403I~
//        if (afterDismiss)                                          //~1403M~//~1A21R~
//            return;                                                //~1403M~//~1A21R~
                                                                   //~1403I~
	 	if (mode==LOAD)                                            //~1403I~
        {                                                          //~1A30I~
    		new Label(this,R.id.FileNameLabel,AG.resource.getString(R.string.CurrDir));//~1A21I~
        }                                                          //~1A30I~
        else                                                       //~1403I~
//  		labelCD=new Label("Name");                             //~1403R~//~1A21R~
    		new Label(this,R.id.FileNameLabel,AG.resource.getString(R.string.FileName));//~1A21I~
//  	input=new TextField("New");                            //~1403I~//~1A21R~
    	input=new TextField(this,0,R.id.FileName,AG.resource.getString(R.string.NewFile));//~1A21I~
        input.setBackground(COLOR_FILENAME);                       //~1A21I~
    	inputfilter=new TextField(this,0,R.id.Filter,"");          //~1A21I~
        if (mode==LOAD)                                            //~1403I~
        	input.setEditable(false);                         //~1403I~
        else                                                       //~1403R~
        	input.setEditable(true);                               //~1403R~
//  	lister=new Lister();                                       //~1403I~//~1A21R~
    	lister=new Lister(this,R.id.Lister,R.layout.textrowfilelist);//~1A21R~
//        buttonOpen=new ButtonAction(this,Global.resourceString("Open")); //~1113R~//~1403I~//~1A21R~
//        buttonSave=new ButtonAction(this,Global.resourceString("Save"));//~1403I~//~1A21R~
//        buttonDel=new ButtonAction(this,Global.resourceString("Delete"));//~1403I~//~1A21R~
//        buttonCan=new ButtonAction(this,Global.resourceString("Cancel"));//~1403I~//~1A21R~
        new ButtonAction(this,0,R.id.UpdateList);                  //~1A30I~
        new ButtonAction(this,0,R.id.SelectAll);                   //~1Ah8I~
        new ButtonAction(this,0,R.id.DeSelectAll);                 //~1Ah8I~
        new ButtonAction(this,0,R.id.Open);                        //~1A21R~
        buttonSave=new ButtonAction(this,0,R.id.Save);                        //~1A21R~
        new ButtonAction(this,0,R.id.Delete);                      //~1A21R~
        buttonDeleteMulti=new ButtonAction(this,0,R.id.DeleteMulti);//~1A4nR~
        btnBackground=getBGC(buttonDeleteMulti.androidButton);     //~1A4nR~
        new ButtonAction(this,0,R.id.Cancel);                      //~1A21R~
        new ButtonAction(this,0,R.id.Help);                        //~1A21R~
        if (mode==LOAD)                                            //~v105I~
        {                                                          //~1A30I~
//      	((Component)buttonSave).setEnabled((Button)buttonSave,false);         //~v105I~//~1A21R~
        	buttonSave.setVisibility(View.GONE);                   //~1A4nR~
        	buttonDeleteMulti.setVisibility(View.VISIBLE);         //~1A4nR~
        	View v=findViewById(R.id.FileNameContainer);           //~1A30I~
	        Component.setVisible(v,View.GONE);	//avoid intercept by Dialog.UiThread;current dir is on entry "./"//~1A30R~
        }                                                          //~1A30I~
        else                                                       //~1A4nR~
        {                                                          //~1A4nR~
        	buttonSave.setVisibility(View.VISIBLE);                //~1A4nR~
        	buttonDeleteMulti.setVisibility(View.GONE);            //~1A4nR~
        }                                                          //~1A4nR~
        ReplayColorWhite=new Checkbox(this,R.id.ReplayColorWhite);                                  //~@@@@I~//~@@@2R~//~1A26I~//~1A2dI~
        if (swReplayFile)                                          //~1A2dR~
        {                                                          //~1A2dR~
            Component.setVisible(ReplayColorWhite.androidCheckBox,View.VISIBLE);//~1A2dI~
	        ReplayColorWhite.setState(replaycolorwhite);           //~1A2dR~
        }                                                          //~1A2dR~
        if (Dump.Y) Dump.println("FileDialog:title="+Ptitle+",selectedFilename="+selectedFilename);//~1324I~//~1506R~
        if (Ssdpath==null)                                         //~1A21I~
        {                                                          //~1A21I~
        	Ssdpath=Prop.getSDPath("");                            //~1A21I~
            if (Ssdpath!=null)                                     //~1A21I~
            {                                                      //~1A21I~
//          	Prop.makePath(Ssdpath+"/"+SNAPSHOT_PATH);          //~1A21I~//~1A22R~
            	Prop.makePath(Ssdpath+"/"+GAMES_PATH);             //~1A21I~
            }                                                      //~1A21I~
        }                                                          //~1A21I~
        if (swReplayFile)                                          //~1A30I~
        	setupLoadType();                                       //~1A30I~
        else                                                       //~1A33I~
        if (swFGF)                                                 //~1A33I~
        	setupLoadType();                                       //~1A33I~
    }                                                              //~1214I~
////***********                                                      //~1319I~//~1A21R~
//    public FileDialog(CloseFrame Pframe,String Ptitle,int Pmode,String Pdir)//~1319R~//~1A21R~
//    {                                                              //~1319I~//~1A21R~
//        this(Pframe,Ptitle,Pmode);                                //~1319R~//~1A21R~
//        setDirectory(Pdir);                                        //~1319I~//~1A21R~
//    }                                                              //~1319I~//~1A21R~
////***********                                                      //~1319I~//~1A21R~
//    public void setDirectory(String Pdir)                          //~1319I~//~1A21R~
//    {                                                              //~1319I~//~1A21R~
//        if (Dump.Y) Dump.println("FileDialog setDirectory :"+Pdir);//~1506R~//~1A21R~
//        dirname=""; //use Preference                               //~1402I~//~1A21R~
//    }                                                              //~1319I~//~1A21R~
//***********                                                      //~1319I~
    //*****************************************************************//~1A30I~
	private void setupLoadType()                                   //~1A30I~
    {                                                              //~1A30I~
        View v=findViewById(R.id.FileTypeContainer);               //~1A30I~
	    Component.setVisible(v,View.VISIBLE);	//avoid intercept by Dialog.UiThread;current dir is on entry "./"//~1A30R~
		ChoiceFileType=new ChoiceAction(this,ITEMACTION_FILETYPE,R.id.FileType);//~1A30R~
		ChoiceEncoding=new ChoiceAction(this,ITEMACTION_FILEENCODING,R.id.FileEncoding);//~1A30R~
        for (int ii=0;ii<NotesFmt.FILETYPENAMETB.length;ii++)      //~1A30R~
        	ChoiceFileType.add(NotesFmt.FILETYPENAMETB[ii]);       //~1A30R~
        for (int ii=0;ii<NotesFmt.ENCODINGTB.length;ii++)          //~1A30I~
        	ChoiceEncoding.add(NotesFmt.ENCODINGTB[ii]);           //~1A30I~
        selectedItemFileType=Prop.getPreference(PKEY_FILETYPE,""/*default*/);//~1A30R~
        selectedItemEncoding=Prop.getPreference(PKEY_FILEENCODING,""/*default*/);//~1A30R~
        if (selectedItemFileType.equals(""))                       //~1A30R~
			ChoiceFileType.select(0);                              //~1A30I~
        else                                                       //~1A30I~
			ChoiceFileType.select(selectedItemFileType);           //~1A30R~
        if (selectedItemEncoding.equals(""))                       //~1A30R~
			ChoiceEncoding.select(0);                              //~1A30I~
        else                                                       //~1A30I~
			ChoiceEncoding.select(selectedItemEncoding);           //~1A30R~
    }                                                              //~1A30I~
    //*****************************************************************//~1A30I~
	public String getDirectory()                                   //~1319I~
    {                                                              //~1319I~
    	String path;
//        if (afterDismiss)                                          //~1403R~//~1A21R~
        	path=selectedDirname;                                  //~1330I~
//        else                                                       //~1330I~//~1A21R~
//            path="";                                               //~1403R~//~1A21R~
//        if (Dump.Y) Dump.println("FileDialog getDirectory :"+path+",afterdismiss="+afterDismiss);//~1506R~//~1A21R~
        if (Dump.Y) Dump.println("FileDialog getDirectory :"+path);//~1A21I~
    	return path+"/";                                        //~1319I~//~1326R~//~1330R~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setFile(String Pfile)                              //~1319I~
    {                                                              //~1319I~
        if (Dump.Y) Dump.println("FileDialog:setFile="+Pfile);     //~1506R~
        filterString=Pfile;                                        //~1402I~
        savefilename="";                                           //~1403I~
		swSaveGame=(Pfile.equals("*.sto")); // save to bitmap;     //~1407R~
		swSaveTxt=(Pfile.equals("*.txt")); // save to bitmap;      //~1418I~
        if (mode==SAVE)                                            //~1402M~
        {                                                          //~1402I~
            savefilename=Pfile;                                    //~1404R~
            filterString="";                                       //~1404R~
        }                                                          //~1402I~
    }                                                              //~1319I~
//***********                                                      //~1A22I~
	public void setFilterString(String Pfilter)                    //~1A22I~
    {                                                              //~1A22I~
        if (Dump.Y) Dump.println("FileDialog:setFilterString ="+Pfilter);//~1A22I~
        String filter=Pfilter;                                     //~1A30I~
        if (swReplayFile)                                          //~1A30I~
        	filter=Prop.getPreference(PKEY_REPLAYFILTER,filter);   //~1A30I~
        else                                                       //~1A33I~
        if (swFGF)                                                 //~1A33I~
        	filter=Prop.getPreference(PKEY_REPLAYFILTER,filter);   //~1A33I~
        inputfilter.setText(filter);                              //~1A22I~//~1A30R~
        filterString=filter;                                      //~1A22I~//~1A30R~
    }                                                              //~1A22I~
//***********                                                      //~1A21I~
//*FGF                                                             //~1A22I~
//***********                                                      //~1A22I~
	public void setSaveNotes(Notes Pnotes)                         //~1A21I~
    {                                                              //~1A21I~
        if (Dump.Y) Dump.println("FileDialog:setSaveNote name=="+Pnotes.name);//~1A21I~
        saveNotes=Pnotes;                                          //~1A21I~
//      savefilename=Pnotes.name+"-"+Pnotes.seq+"."+SNAPSHOT_EXT;  //~1A21R~//~1A26R~
//      savefilename=Pnotes.name+"."+SNAPSHOT_EXT;                 //~1A26I~//~1A2dR~
        savefilename=(Pnotes.name+"."+SNAPSHOT_EXT).replace(" ","_");//~1A2dI~
        inputfilter.setText(SNAPSHOT_EXT);                         //~1A21I~
        filterString=SNAPSHOT_EXT;                                 //~1A21R~
    }                                                              //~1A21I~
//***********                                                      //~1A21I~
//*LGF                                                             //~1A22I~
//***********                                                      //~1A22I~
	public void setSaveNotes(Notes Pnotes,String Pfilter)                 //~1A21I~
    {                                                              //~1A21I~
        if (Dump.Y) Dump.println("FileDialog:setSaveNote name=="+Pnotes.name);//~1A21I~
        saveNotes=Pnotes;                                          //~1A21I~
//      savefilename=Pnotes.name+"."+Pfilter;                      //~1A21I~//~1A2dR~
        savefilename=(Pnotes.name+"."+Pfilter).replace(" ","_");   //~1A2dI~
        inputfilter.setText(Pfilter);                              //~1A21I~
        filterString=Pfilter;                                      //~1A21I~
    }                                                              //~1A21I~
//***********                                                      //~1319I~
	public String getFile()                                        //~1319I~
    {                                                              //~1319I~
//        if (Dump.Y) Dump.println("FileDialog getFile:"+selectedFilename+",afterdismiss="+afterDismiss);//~1506R~//~1A21R~
        if (Dump.Y) Dump.println("FileDialog getFile:"+selectedFilename);//~1A21I~
//        if (!afterDismiss)                                         //~1402I~//~1A21R~
//            return null;                                           //~1402I~//~1A21R~
    	return selectedFilename;                                   //~1319I~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setFilenameFilter(FilenameFilter Pfilter)         //~1319I~
    {                                                              //~1319I~
    	filterCB=Pfilter;                                          //~1407R~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void setLocation(int Px,int Py)                         //~1319I~
    {                                                              //~1319I~
    }                                                              //~1319I~
//***********                                                      //~1319I~
	public void show()                                             //~1319I~
    {                                                              //~1319I~
//        if (afterDismiss)                                          //~1324I~//~1A21R~
//            return;     //avoid redo dialog doAction schedule      //~1403R~//~1A21R~
    	createList(dirname);                                      //~1403I~
    	if (namelist==null)                                        //~1323I~
        	return;                                                //~1323I~
        setTitleFilename(dirname);                                 //~1403M~
    	setList();                                                 //~1403I~
        setDismissActionListener(this/*DoActionListener*/);	//callback from DialogClosed at dismiss//~1A21I~
    	super.show();                                              //~1403R~
//        afterDismiss=isAfterDismiss();  //true if subthread modal  //~1407I~//~1A21R~
//        if (Dump.Y) Dump.println("FileDialog:after show,afterDismiss="+afterDismiss);//~1506R~//~1A21R~
        if (Dump.Y) Dump.println("FileDialog:after show");          //~1A21I~
    }                                                              //~1319I~
//***********                                                      //~v105I~
	public void redraw()                                           //~v105I~
    {                                                              //~v105I~
    	shown=false;	//Dilaog                                       //~v105I~
    	super.show();                                              //~v105I~
        if (Dump.Y) Dump.println("FileDialog:redraw");              //~v105I~
    }                                                              //~v105I~
    //**********************************                           //~1403I~
    //* init ListView                                              //~1403I~
    //**********************************                           //~1403I~
    private void setList()                                         //~1403R~
    {                                                              //~1403I~
        int sz=namelist.length;                                    //~1411R~
        for (int ii=0;ii<sz;ii++)                                      //~1411I~
        {                                                          //~1403I~
        	lister.appendLine(namelist[ii]);                       //~1403R~
        }                                                          //~1403I~
        if (sz>0)                                                  //~1411I~
        {                                                          //~1411I~
//	        int pos=(sz==1?0:1);                                   //~1411I~//~1A2fR~
  	        int pos;                                               //~1A2fI~
//            if (sz==1)                                           //~1A4nR~
//                pos=0;                                           //~1A4nR~
//            else                                                 //~1A4nR~
//            if (sz==2)                                           //~1A4nR~
//                pos=1;                                           //~1A4nR~
//            else                                                 //~1A4nR~
//                pos=(mode==SAVE?1:2);                            //~1A4nR~
		  if (mode==SAVE)                                          //~1A4nI~
          {                                                        //~1A4nI~
          	pos=1;                                                 //~1A4nI~
			lister.setSelection(pos);//current dir for save,top file for load//~1411R~
          }                                                        //~1A4nI~
                                                                   //~1A4nI~
        }                                                          //~1411I~
        lister.setBackground(bgcolor);	                           //~1403I~
        selectedDirname=dirname;                                   //~1403I~
    }                                                              //~1403I~
    //**********************************                           //~1319I~
    //* file submenu selected;create submenu listview              //~1319I~
    //**********************************                           //~1319I~
    private void createList(String Pdirname)                       //~1402R~
    {                                                              //~1319I~
    	File fileCurDir;                                           //~1403R~
    //************************                                     //~1319I~
    	String path=Pdirname;                                      //~1402R~
        inputFilterString=inputfilter.getText();                   //~1A4dI~
        if (swReplayFile)                                          //~1A4eI~
            Prop.putPreference(PKEY_REPLAYFILTER,inputFilterString);              //~1A4eI~
        else                                                       //~1A4eI~
        if (swFGF)                                                 //~1A4eI~
            Prop.putPreference(PKEY_REPLAYFILTER,inputFilterString);              //~1A4eI~
        if (Dump.Y) Dump.println("createList path="+path);         //~1506R~
        if (path==null                                             //~1323I~
        ||  path.equals("")                                        //~1323I~
        )                                                          //~1323I~
        {                                                          //~1323I~
        	path=Prop.getPreference(Prop.GAMEFILE,null/*default*/);//~1402I~//~1A21R~
            if (path==null)                                        //~1402I~
            	path=Prop.getSDPath("");                          //~1323I~//~1402R~//~1A21R~
            if (path==null)                                        //~1A42I~
            {                                                      //~1A42I~
	        	path=Prop.getOutputDataFilesPath(Prop.GAMEFILE);   //~1A42I~
            }                                                      //~1A42I~
            if (path==null)                                        //~1323I~
            {                                                      //~1323I~
            	AView.showToast(R.string.NoSDCard);            //~1403I~//~1A21R~
                namelist=null;                                     //~1323I~
                return;                                            //~1323I~
            }                                                      //~1323I~
        }                                                          //~1323I~
        dirname=path;                                              //~1402I~
        try{                                                       //~1319I~
        	if (Dump.Y) Dump.println("FileDialog path="+path);     //~1506R~
            fileCurDir=new File(path);                             //~1403R~
            filelist=fileCurDir.listFiles();   //file and dir      //~1403R~
            if(filelist==null)                                     //~1319I~
            {                                                      //~1319I~
				Alert.simpleAlertDialog(/*AlerttI*/null,path,"No Entry",Alert.BUTTON_CLOSE);//~1319I~//~1A21R~
            }                                                      //~1319I~
            else                                                   //~1319I~
            {                                                      //~1319I~
                int count = 1;                                     //~1403M~
                String name;                                       //~1403M~
                                                                   //~1403I~
//              if (mode==SAVE)                                    //~1403I~//~1A2fR~
                	count++;                                       //~1403I~
            	name2file=new int[filelist.length+count];          //~1403R~
                                                                   //~1403I~
                for (File file : filelist)                         //~1325I~
                {                                                  //~1325I~
                    name=file.getName();                           //~1325I~
		        	if (Dump.Y) Dump.println("FileDialog file="+name);//~1506R~
                    if(!file.isDirectory())                        //~1404R~
                    {                                              //~1404I~
//                        if (swSaveBitmap)                          //~1404I~//~1A21R~
//                        {                                          //~1404I~//~1A21R~
//                            if (!bitmapAccept(fileCurDir,name)) //GoFrame accept() acept sgf or xml only//~1404I~//~1A21R~
//                                continue;                          //~1404I~//~1A21R~
//                        }                                          //~1404I~//~1A21R~
//                        else                                       //~1404R~//~1A21R~
                    	if (swSaveGame)                            //~1407I~
                        {                                          //~1407I~
							if (!saveGameAccept(fileCurDir,name))	//PartnerFrame//~1407I~
		                        continue;                          //~1407I~
                        }                                          //~1407I~
                   	 	else                                       //~1407I~
                    	if (swSaveTxt)                             //~1418I~
                        {                                          //~1418I~
							if (!saveTxtAccept(fileCurDir,name))	//PartnerFrame//~1418I~
		                        continue;                          //~1418I~
                        }                                          //~1418I~
                   	 	else                                       //~1418I~
                        {                                          //~1404I~
                        	filterString=inputFilterString;        //~1A4dI~
                        	if (filterCB!=null && !filterCB.accept(fileCurDir,name))//~1407I~
		                        continue;                          //~1404R~
                        }                                          //~1404I~
                    }                                              //~1404I~
                    count++;                                       //~1325I~
                }                                                  //~1325I~
                namelist=new String[count];            //~1323R~   //~1325R~
                                    //~1319I~
//                if (mode==LOAD)                                    //~1403I~//~1A2fR~
//                {                                                  //~1403I~//~1A2fR~
//                    namelist[0]="<--Up";                           //~1403R~//~1A2fR~
//                    count = 1;                                     //~1403I~//~1A2fR~
//                }                                                  //~1403I~//~1A2fR~
//                else                                               //~1403I~//~1A2fR~
//                {                                                  //~1403I~//~1A2fR~
                	                                               //~1403I~
                	namelist[0]="<--Up";                           //~1403I~
                	namelist[1]="./ ("+dirname+")";                //~1403I~
	                count = 2;                                     //~1403I~
//                }                                                  //~1403I~//~1A2fR~
                Integer[] sortout=sortFileList();                  //~1A21I~
//              for (File file : filelist)                         //~1319I~//~1A21R~
                for (int ii=0;ii<sortout.length;ii++)              //~1A21I~
                {                                                  //~1319I~
                	int idx=sortout[ii].intValue();                //~1A21I~
                	File file=filelist[idx];                       //~1A21I~
                    name=file.getName();                           //~1319I~
//                  name2file[count]=filelistctr++;                //~1330I~//~1A21R~
                    name2file[count]=idx;                          //~1A21I~
		        	if (Dump.Y) Dump.println("FileDialog file="+name);//~1506R~
                    if(file.isDirectory())                         //~1319I~
                    {                                              //~1319I~
                    	name+="/";	//linux File.pathSeparator;                  //~1319I~//~1325R~
                    }                                              //~1319I~
                    else                                           //~1325I~
                    {                                              //~1402I~
//                        if (swSaveBitmap)                          //~1404I~//~1A21R~
//                        {                                          //~1404I~//~1A21R~
//                            if (!bitmapAccept(fileCurDir,name)) //GoFrame accept() acept sgf or xml only//~1404I~//~1A21R~
//                                continue;                          //~1404I~//~1A21R~
//                        }                                          //~1404I~//~1A21R~
//                        else                                       //~1404I~//~1A21R~
                    	if (swSaveGame)                            //~1407I~
                        {                                          //~1407I~
							if (!saveGameAccept(fileCurDir,name))	//PartnerFrame//~1407I~
		                        continue;                          //~1407I~
                        }                                          //~1407I~
                   	 	else                                       //~1407I~
                    	if (swSaveTxt)                             //~1418I~
                        {                                          //~1418I~
							if (!saveTxtAccept(fileCurDir,name))	//PartnerFrame//~1418I~
		                        continue;                          //~1418I~
                        }                                          //~1418I~
                   	 	else                                       //~1418I~
                        {                                          //~1404I~
                    		if (filterCB!=null && !filterCB.accept(fileCurDir,name))//~1407R~
                        		continue;                          //~1404R~
                        }                                          //~1404I~
//                        int pos=name.lastIndexOf('.');             //~1402I~//~1A21R~
//                        if (pos>0)                                 //~1402I~//~1A21R~
//                            name=name.substring(0,pos);            //~1402I~//~1A21R~
                    }                                              //~1402I~
                    if (Dump.Y) Dump.println("aftersort ii="+count+",idx="+name2file[count]+",name="+name);//~1A4eI~
                    namelist[count++]=name;                        //~1319I~
                }                                                  //~1319I~
            }                                                      //~1319I~
        }                                                          //~1319I~
        catch(SecurityException e)                                 //~1319I~
        {                                                          //~1319I~
        	Dump.println(e,"FileDialog createList Security");      //~1A2fI~
            AView.showToast(R.string.FailedListDirBySecurity);   //~1403I~//~1A21R~
            namelist=null;                                         //~v105I~
        }                                                          //~1319I~
		catch(Exception e)                                         //~1319I~
		{                                                          //~1319I~
        	Dump.println(e,"FileDialog createList");               //~1A2fI~
            AView.showToast(R.string.FailedListDir);           //~1403I~//~1A21R~
            namelist=null;                                         //~v105I~
        }                                                          //~1319I~
    }//createList                                                  //~1402R~
    //*****************************************************************//~1A21I~
    private Integer[]  sortFileList()                                   //~1A21I~
    {                                                              //~1A21I~
    //***********************************************              //~1A21I~
    	int filectr=filelist.length;                               //~1A21I~
    	Integer[] idx=new Integer[filectr];                        //~1A21I~
        for (int ii=0;ii<filectr;ii++)                             //~1A21I~
        	idx[ii]=ii;                                            //~1A21I~
    	Arrays.sort(idx,new DataComparator());                      //~1A21I~
        return idx;                                                //~1A21I~
    }                                                              //~1A21I~
    //**********************************                           //~1A21I~
    class DataComparator implements Comparator<Integer>                      //~1A21I~
    {                                                              //~1A21I~
        public int compare(Integer P1,Integer P2)                     //~1A21I~
        {                                                          //~1A21I~
        	int rc=0;                                              //~1A21I~
        //*****************************                            //~1A21I~
        	int i1=P1.intValue();                       //~1A21I~
        	int i2=P2.intValue();                       //~1A21I~
        	File f1=filelist[i1];                                  //~1A21I~
        	File f2=filelist[i2];                                  //~1A21I~
            if (Dump.Y) Dump.println("FileDialog compare f1="+i1+"="+f1.getName()+",f2="+i2+"="+f2.getName());//~1A4aI~
            long dt1=f1.lastModified();                            //~1A21I~
            long dt2=f2.lastModified();                            //~1A21I~
            if(f1.isDirectory())                                   //~1A21I~
            	if (f2.isDirectory())                              //~1A21I~
                {	                                               //~1A21I~
                	rc=f1.getName().compareTo(f2.getName());       //~1A21R~
                }                                                  //~1A21I~
                else                                               //~1A21I~
                	rc=-1;	//dir first                            //~1A21I~
            else                                                   //~1A21I~
            	if (f2.isDirectory())                              //~1A21I~
                	rc=1;                                          //~1A21I~
                else                                               //~1A21I~
                {                                                  //~1A21I~
//             		rc=(int)(dt2-dt1);	//reverse seq              //~1A21I~//~1A4cR~
               	    if (dt2>dt1)	//reverse seq                  //~1A4cR~
                    	rc=1;                                      //~1A4cR~
                    else                                           //~1A4cR~
               	    if (dt2<dt1)	//reverse seq                  //~1A4cR~
                    	rc=-1;                                     //~1A4cR~
                    else                                           //~1A4cR~
                    	rc=0;                                      //~1A4cR~
//  	            if (Dump.Y) Dump.println("FileDialog compare date "+dt1+"-"+dt2+"=rc="+rc);//~1A4aR~//~1A4cR~
//  	            if (Dump.Y) Dump.println("FileDialog compare date diff="+(dt1-dt2)+",unmatch="+((rc<0 && dt1<dt2)||(rc>0 && dt1>dt2)||(rc==0 && dt1!=dt2)));//~1A4aR~//~1A4cR~
//                  int diff=(int)(dt2-dt1);                       //~1A4aI~//~1A4cR~
//  	            if (Dump.Y) Dump.println("FileDialog compare date int diff="+diff+",unmatch2="+((rc<0 && diff>0)||(rc>0 && diff<0)||(rc==0 && diff!=0)));//~1A4aI~//~1A4cR~
                }                                                  //~1A21I~
        	if (rc==0)                                             //~1A21I~
            	rc=i1-i2;                                          //~1A21I~
            if (Dump.Y) Dump.println("FileDialog compare rc="+rc); //~1A4aI~
            return rc;                                             //~1A21I~
        }                                                          //~1A21I~
    }                                                              //~1A21I~
    //**********************************                           //~1402I~
    private void updateList(String Pdirname)                    //~1402I~
    {                                                              //~1402I~
    	if (Dump.Y) Dump.println("FileDialog:updateAdapter dir="+Pdirname);//~1506R~
    	createList(Pdirname);                                      //~1402I~
        if (namelist==null)                                        //~1403I~
        	return;                                                //~1403I~
	    setTitleFilename(Pdirname);	//lis will be cleared at next append//~1403I~
        lister.clearList();                                        //~1403I~
    	setList();                                                 //~1403I~
        Prop.putPreference(Prop.GAMEFILE,Pdirname);      //~1403I~ //~1A21R~
    }//FileList                                                    //~1402R~
	//***************************************                      //~1402I~
    private void setTitleFilename(String Pdirname)                         //~1402I~
    {                                                              //~1402I~
        String dlgtitle;                                           //~1402I~
    //************************                                     //~1402I~
        dlgtitle=title;                                            //~1402I~
        if (mode==LOAD)                                            //~1402I~
        {                                                          //~1402I~
			if (filterString!=null && !filterString.equals(""))               //~1402I~
                dlgtitle+=" ("+filterString+")";                   //~1402I~
			input.setText(dirname);
        }
        else 
        	input.setText(savefilename);//~1402I~
    	setTitle(dlgtitle);  //of Dialog                           //+1403R~                           //~1403I~
    }                                                              //~1402I~
	//***************************************                      //~1402R~
	public void doAction (String o)                                //~1403I~
	{                                                              //~1403I~
    	boolean swshow=false;                                   //~v105I~
    //***************************                                  //~v105I~
      try                                                          //~v105I~
      {                                                            //~v105I~
    	selectedFilename=null;                                     //~1403R~
    	selectedDirname="";                                        //~1403I~
		if (o.equals(AG.resource.getString(R.string.UpdateList)))  //~1A30I~
		{                                                          //~1A30I~
			lister.setSelection(1);//current dir for save,top file for load//~1A30I~
    		openSelected();      //file is selected                //~1A30I~
        	swshow=true;                                           //~1A30I~
		}                                                          //~1A30I~
		else                                                       //~1A30I~
		if (o.equals(AG.resource.getString(R.string.SelectAll)))   //~1Ah8I~
		{                                                          //~1Ah8I~
        	selectAll();                                           //~1Ah8I~
        	swshow=true;                                           //~1Ah8I~
		}                                                          //~1Ah8I~
		else                                                       //~1Ah8I~
		if (o.equals(AG.resource.getString(R.string.DeSelectAll))) //~1Ah8I~
		{                                                          //~1Ah8I~
        	deselectAll();                                         //~1Ah8I~
        	swshow=true;                                           //~1Ah8I~
		}                                                          //~1Ah8I~
		else                                                       //~1Ah8I~
//  	if (o.equals(Global.resourceString("Open")))               //~1403R~//~1A21R~
		if (o.equals(AG.resource.getString(R.string.Open)))               //~1403R~//~1A21I~
		{                                                          //~1403I~
    		if (openSelected())      //file is selected            //~1403R~
	        	dispose();	         //dismiss                     //~1403I~
            else                                                   //~v105I~
            	swshow=true;                                       //~v105I~
            if (Dump.Y) Dump.println("FileDialog Open dir="+dirname+",file="+selectedFilename);//~1506R~
		}                                                          //~1403I~
//  	else if (o.equals(Global.resourceString("Save")))          //~1403I~//~1A21R~
		else if (o.equals(AG.resource.getString(R.string.Save)))   //~1A21I~
		{                                                          //~1403I~
        	if (mode==LOAD)                                        //~1403I~
 //         	return;	//ignore click                             //~1403I~//~v105R~
            	swshow=true;	//ignore click                     //~v105I~
            else                                                   //~v105I~
        	if (saveSelected()) //valid directory                  //~1403I~
	        	dispose();	         //dismiss                     //~1403I~
            else                                                   //~v105I~
            	swshow=true;	//ignore click                     //~v105I~
            if (Dump.Y) Dump.println("FileDialog save dir="+dirname+",file="+selectedFilename);//~1506R~
		}                                                          //~1403I~
//  	else if (o.equals(Global.resourceString("Delete")))        //~1403I~//~1A21R~
		else if (o.equals(AG.resource.getString(R.string.Delete))) //~1A21I~
		{                                                          //~1403I~
    		deleteSelected();                                       //~1403I~
            if (Dump.Y) Dump.println("FileDialog Delete dir="+dirname+",file="+selectedFilename);//~1506R~
            swshow=true;                                           //~v105I~
		}                                                          //~1403I~
		else if (o.equals(AG.resource.getString(R.string.DeleteMulti)))//~1A4nR~
		{                                                          //~1A4nR~
    		swapDeleteMultiple();                                  //~1A4nR~
            swshow=true;                                           //~1A4nR~
		}                                                          //~1A4nR~
//  	else if (o.equals(Global.resourceString("Cancel")))        //~1403I~//~1A21R~
		else if (o.equals(AG.resource.getString(R.string.Cancel))) //~1A21I~
		{                                                          //~1403I~
            if (Dump.Y) Dump.println("FileDialog Cancel");         //~1506R~
            swCancel=true;                                         //~1A4qI~
	        dispose();	         //dismiss                         //~1403I~
		}                                                          //~1403I~
		else if (o.equals(AG.resource.getString(R.string.Help)))   //~1A21I~
		{                                                          //~1A21I~
            if (Dump.Y) Dump.println("FileDialog Help");           //~1A21I~
    		new HelpDialog(null,R.string.HelpTitle_FileDialog,"FileDialog"/*help text file*/);//~1A21I~
		}                                                          //~1A21I~
        else if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~1A21I~
        {               //callback from Dialog after currentLayout restored//~1A21I~
        	if (swLoad)                                            //~1A21I~
            {                                                      //~1A21I~
		    	if (Dump.Y) Dump.println("FileDialog ActionDismissDialog");//~1A21I~
                callbackLoad();                                    //~1A21I~
            }                                                      //~1A21I~
            else                                                   //~1A24I~
        	if (swSave)                                            //~1A22I~
            {                                                      //~1A22I~
		    	if (Dump.Y) Dump.println("FileDialog ActionDismissDialog");//~1A22I~
                callbackSave(true);                                    //~1A22I~//~1A24R~
            }                                                      //~1A22I~
            else                                                   //~1A24I~
            if (mode==SAVE)                                        //~1A24I~
              if (swCancel)	//not dismiss by back button           //~1A4qI~
                callbackSave(false);                               //~1A24I~
        }                                                          //~1A21I~
		else super.doAction(o);	//Cancel                           //~1403I~
      }                                                            //~v105I~
      catch (Exception e)                                          //~v105I~
      {                                                            //~v105I~
      	Dump.println(e,"FileDialog:doAction Exception");           //~v105I~
       	AView.showToast(R.string.Exception);                   //~v105I~//~1A21R~
	    dispose();	         //dismiss                             //~v105I~
        swshow=false;                                              //~v105I~
      }                                                            //~v105I~
      if (swshow)                                                  //~v105I~
      	redraw();                                                  //~v105I~
    }                                                              //~1403R~
    //**********************************************************************//~1403I~
    //* open                                                       //~1403I~
    //**********************************************************************//~1403I~
    private boolean openSelected()                                 //~1403I~
    {                                                              //~1403I~
    	int selectedstatus;                                        //~1403I~
        boolean rc=false;                                          //~1403I~
    //***                                                          //~1403I~
    	selectedstatus=chkSelectedFilename();                      //~1403I~
        switch(selectedstatus)                                     //~1403I~
        {                                                          //~1403I~
        case -1:	//no selection                                 //~1403R~
            break;                                                 //~1403I~
        case 0:	//file selected	                                   //~1403I~
		    if (swOpenByClick)                                     //~1A4zI~
            {                                                      //~1A4zI~
		        selectedDirname=dirname;                           //~1A4zI~
                String fnm=selectedDirname+"/"+selectedFilename;   //~1A4zI~
                openFileViewer(fnm);                               //~1A4zI~
            	break;                                             //~1A4zI~
            }                                                      //~1A4zI~
        	if (mode==LOAD)                                        //~1403I~
            {                                                      //~1403I~
		        selectedDirname=dirname;                           //~1403I~
                swLoad=true;                                       //~1A21I~
                swSave=false;                                      //~1A22I~
                loadFilename=selectedDirname+"/"+selectedFilename; //~1A21I~
            	rc=true;	//dismiss                              //~1403R~
            }                                                      //~1403I~
            else                                                   //~1403I~
            	AView.showToast(R.string.NotDir);              //~1403I~//~1A21R~
            break;                                                 //~1403I~
        default:	//dir or up                                    //~1403R~
        	if (mode==SAVE)                                        //~1404I~
        		savefilename=input.getText(true);  //may updated on input field//~1411R~
        	updateList(updownpath);                                //~1403I~
        }                                                          //~1403I~
        return rc;                                                 //~1403I~
    }                                                              //~1403I~
    //**********************************************************************//~1402I~
    //* save                                                       //~1403R~
    //**********************************************************************//~1402I~
    private boolean saveSelected()                                 //~1403R~
    {                                                              //~1402I~
    	int selectedstatus;                                        //~1403I~
        String savepath=null;                                      //~1403I~
    //***                                                          //~1403I~
    	selectedstatus=chkSelectedFilename();                      //~1403I~
        switch(selectedstatus)                                     //~1403I~
        {                                                          //~1403I~
        case -1:                                                   //~1403I~
            savepath=dirname;                                      //~1403I~
            break;                                                 //~1403I~
        case 0:	//file selected                                    //~1403I~
            AView.showToast(R.string.NotDir);                  //~1403I~//~1A21R~
            return false;                                          //~1403I~                                               //~1403I~
        default:                                                   //~1403I~
        //case 1:	//dir selected                                 //~1403R~
        //case 2:	//up slected                                   //~1403R~
        //case 3:	//current dir                                  //~1403I~
            savepath=updownpath;                                   //~1403I~
        }                                                          //~1403I~
	    if (Dump.Y) Dump.println("FileDialog save savepath="+savepath);//~1506R~
        String newpath=input.getText(true).toString();             //~1411R~
        if (newpath==null || newpath.equals(""))                   //~1403R~
        	return false;                                          //~1403I~
        int pos=newpath.lastIndexOf('/');                          //~1403I~
        if (pos==0)                                                //~1403I~
        {                                                          //~1403I~
            savepath="/";                                          //~1403I~
            selectedFilename=newpath.substring(1);                 //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403I~
        if (pos>0)                                                 //~1403I~
        {                                                          //~1403I~
            savepath+="/"+newpath.substring(0,pos);                //~1403I~
            selectedFilename=newpath.substring(pos+1);             //~1403I~
            if (selectedFilename.length()==0)                            //~1403I~
                return false;                                      //~1403I~
//            mkdir=true;                                            //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403I~
        {                                                          //~1A21I~
            selectedFilename=newpath;                              //~1403I~
//            if (filterString.equals(SNAPSHOT_EXT) && !savepath.endsWith(SNAPSHOT_PATH))//~1A21R~
//            {                                                    //~1A21R~
//                savepath+="/"+SNAPSHOT_PATH;                     //~1A21R~
//                mkdir=true;                                      //~1A21R~
//            }                                                    //~1A21R~
//            else                                                 //~1A21R~
//            if (filterString.equals(GAMES_EXT) && !savepath.endsWith(GAMES_PATH))//~1A21R~
//            {                                                    //~1A21R~
//                savepath+="/"+GAMES_PATH;                        //~1A21R~
//                mkdir=true;                                      //~1A21R~
//            }                                                    //~1A21R~
        }                                                          //~1A21I~
        if (Dump.Y) Dump.println("FileDialog save newpath="+savepath+"file="+selectedFilename);//~1506R~
//        if (mkdir)                                                 //~1403I~//~1A21R~
//        {                                                          //~1403R~//~1A21R~
//            if (!Prop.makePath(savepath))                     //~1403R~//~1A21R~
//            {                                                      //~1403R~//~1A21R~
//                AView.showToast(R.string.FailedMkdir);         //~1403R~//~1A21R~
//                return false;                                      //~1403R~//~1A21R~
//            }                                                      //~1403R~//~1A21R~
//        }                                                          //~1403R~//~1A21R~
        selectedDirname=savepath;                                  //~1403R~
        if (Dump.Y) Dump.println("FileDialog save path="+selectedDirname+",file="+selectedFilename);//~1506R~
        saveNotes.save(selectedDirname+"/"+selectedFilename);      //~1A21R~
        swSave=true;                                               //~1A22I~
        swLoad=false;                                              //~1A22I~
//      AView.showToast(R.string.Saved,selectedDirname+"/"+selectedFilename);//~v105I~//~1A4sR~
        AView.showToast(R.string.Saved,saveNotes.filenameSaved);   //~1A4sI~
        return true;                                               //~1402I~
    }                                                              //~1402I~
    //**********************************************************************//~1402I~
    //* delete                                                     //~1403R~
    //**********************************************************************//~1402I~
    private boolean deleteSelected()                                  //~1402I~
    {                                                              //~1402I~
    	if (swDeleteMultiple)                                      //~1A4nR~
        	return deleteMultiple();                               //~1A4nR~
    	int selectedstatus=chkSelectedFilename();                      //~1403I~
        boolean rc;
    	switch(selectedstatus)                                     //~1403I~
        {                                                          //~1403I~
        case 0:	//file selected                                    //~1403I~
        	break;                                                 //~1403I~
        case 1:	//dir selected,delete if empty                     //~1403I~
        	File[] fl=selectedFile.listFiles();                         //~1403I~
            if (fl!=null && fl.length!=0)                          //~1403I~
            {                                                      //~1403I~
            	AView.showToast(R.string.NotEmptyDir);         //~1403I~//~1A21R~
                return false;                                      //~1403I~
            }                                                      //~1403I~
            break;                                                 //~1403I~
        default:                                                   //~1403I~
            return false;                                          //~1403I~
        }                                                          //~1403I~
    	try                                                        //~1402I~
        {                                                          //~1402I~
        	                                                       //~1403I~
        	int cpos=lister.getSelectedPos(); //~1A4pI~
        	selectedFile.delete();                                 //~1403R~
	        if (Dump.Y) Dump.println("FileDialog deleted file="+selectedFile.getName());//~1506R~
		    updateList(dirname);
            setSelected(cpos);                                     //~1A4pI~
		    rc=true;//~1402I~
        }                                                          //~1402I~
        catch (Exception e)                                        //~1402I~
        {                                                          //~1402I~
        	Dump.println(e,"FileDialog:deleteSelected Exception"); //~1402I~
    		if (selectedstatus==0)                                 //~1403I~
            	AView.showToast(R.string.FailedDelete);        //~1403I~//~1A21R~
            else                                                   //~1403I~
            	AView.showToast(R.string.FailedRemoveDir);     //~1403I~//~1A21R~
        	rc=false;
        }
        return rc;//~1402I~
    }                                                              //~1402I~
    //**********************************************************************//~1A4nR~
    //* delete multiple                                            //~1A4nR~
    //**********************************************************************//~1A4nR~
    private boolean swapDeleteMultiple()                           //~1A4nR~
    {                                                              //~1A4nR~
    	swDeleteMultiple=!swDeleteMultiple;                        //~1A4nR~
        Color bgcol=Color.yellow;                                  //~1A4nR~
        if (swDeleteMultiple)                                      //~1A4nR~
	    	buttonDeleteMulti.setBackground(buttonDeleteMulti.androidButton,bgcol);//~1A4nR~
        else                                                       //~1A4nR~
	    	buttonDeleteMulti.androidButton.setBackgroundDrawable(btnBackground);//~1A4nR~
        lister.setChoiceMode(swDeleteMultiple?2/*mult*/:0/*none*/); //~1A4nR~
	    if (Dump.Y) Dump.println("FileDialog:swapDeletedMultiple new status="+swDeleteMultiple);//~1A4nR~
        return true;                                               //~1A4nR~
    }                                                              //~1A4nR~
    //**********************************************************************//~1A4nR~
    //* delete                                                     //~1A4nR~
    //**********************************************************************//~1A4nR~
    private	int[] chklistDeleteMultiple;                           //~1A4nR~
    private boolean deleteMultiple()                               //~1A4nR~
    {                                                              //~1A4nR~
	    chklistDeleteMultiple=lister.getCheckedItemPositions();    //~1A4nR~
        int ctr=chklistDeleteMultiple.length;                      //~1A4nR~
	    if (Dump.Y) Dump.println("FileDialog deletedMultiple chk ctr="+ctr);//~1A4nR~
        if (ctr==0)                                                //~1A4nR~
        {                                                          //~1A4nR~
        	AView.showToast(R.string.NoDeleteItemChecked);         //~1A4nR~
        }                                                          //~1A4nR~
        else                                                       //~1A4nR~
        {                                                          //~1A4nR~
    		String title=AG.resource.getString(R.string.Title_DeleteMultipleQuestion);//~1A4nR~
        	String msg=AG.resource.getString(R.string.DeleteMultipleQuestion)+ctr;//~1A4nR~
			Alert.simpleAlertDialog(this,title,msg,Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE);//calback alertButtonAction//~1A4nR~
        }                                                          //~1A4nR~
        return true;                                               //~1A4nR~
    }                                                              //~1A4nR~
    //*********************************************************************//~1A4nR~
	public int doMultipleDelete(int Pbuttonid)                     //~1A4nR~
    {                                                              //~1A4nR~
    	int rc=0;                                                  //~1A4nR~
    	boolean agree=(Pbuttonid==Alert.BUTTON_POSITIVE);          //~1A4nR~
        if (!agree)                                                //~1A4nR~
        	return rc;                                             //~1A4nR~
	    if (chklistDeleteMultiple==null)                           //~1A4nR~
        	return rc;                                             //~1A4nR~
    	try                                                        //~1A4nR~
        {                                                          //~1A4nR~
            int ctr=chklistDeleteMultiple.length;                  //~1A4nR~
            int delctr=0;                                          //~1A4nR~
            int pos1st=0;                                          //~1A4nR~
            for (int ii=0;ii<ctr;ii++)                             //~1A4nR~
            {                                                      //~1A4nR~
                int pos=chklistDeleteMultiple[ii];                 //~1A4nR~
                if (Dump.Y) Dump.println("chklistDeleteMultiple ii="+ii+",pos="+pos);//~1A4nR~
    			int rc2=chkSelectedFilename(pos);                  //~1A4nR~
                if (rc2==0)	//file entry                           //~1A4nR~
                {                                                  //~1A4nR~
                	if (pos1st==0)                                 //~1A4nR~
                    	pos1st=pos;                                //~1A4nR~
        			selectedFile.delete();                         //~1A4nR~
                    delctr++;                                      //~1A4nR~
	        		if (Dump.Y) Dump.println("FileDialog deleted file="+selectedFile.getName());//~1A4nR~
                }                                                  //~1A4nR~
            }                                                      //~1A4nR~
        	String msg=AG.resource.getString(R.string.DeleteMultipleDeleted)+delctr;//~1A4nR~
            AView.showToast(msg);                                  //~1A4nR~
		    updateList(dirname);                                   //~1A4nR~
		    setSelected(pos1st);                                    //~1A4nR~
		    swapDeleteMultiple();                                  //~1A4nR~
        }                                                          //~1A4nR~
        catch (Exception e)                                        //~1A4nR~
        {                                                          //~1A4nR~
        	Dump.println(e,"FileDialog:deleteMultiple ButtonAction");//~1A4nR~
            AView.showToast(R.string.FailedDelete);                //~1A4nR~
        	rc=0;                                                  //~1A4nR~
        }                                                          //~1A4nR~
    	return rc;                                                 //~1A4nR~
    }                                                              //~1A4nR~
    //* AlertI ********************************************************************//~1A4nR~
	public int alertButtonAction(int Pbuttonid,int Pparm/*not used,value set by setSimpleData*/)//~1A4nR~
    {                                                              //~1A4nR~
    	int rc;                                                    //~1A4nR~
		rc=doMultipleDelete(Pbuttonid);                            //~1A4nR~
    	return rc;                                                 //~1A4nR~
    }                                                              //~1A4nR~
    //**********************************************************************//~1319I~
    //*chk list selection                                          //~1403I~
    //*rc:-1:err,0 file selected,1:dir selected,2:"up" selected 3:save to currentdir//~1403R~
    //**********************************************************************//~1403I~
    private int chkSelectedFilename()                              //~1403R~
    {                                                              //~1A4nR~
        return chkSelectedFilename(lister.getSelectedPos());       //~1A4nR~
    }                                                              //~1A4nR~
    private int chkSelectedFilename(int Ppos)                      //~1A4nR~
    {                                                              //~1319I~
        int pos,rc=0;                                              //~1403I~
    //************                                                 //~1403R~
    	selectedFilename=null;                                     //~1403I~
//      pos=lister.getSelectedPos();                               //~1A4nR~
        pos=Ppos;                                                  //~1A4nR~
        if (Dump.Y) Dump.println("FileDialog selectedPos="+pos);   //~1506R~
        if (pos<0)                                                 //~1403I~
        	return -1;                                             //~1403I~
        if (pos==0)                                                //~1403R~
        {                                                          //~1403R~
            updownpath=(new File(dirname)).getParent();            //~1403R~
            rc=2;                                                  //~1403I~
        }                                                          //~1403R~
        else                                                       //~1403I~
//      if (mode==SAVE && pos==1)	//currentdir                   //~1403I~//~1A2fR~
        if (pos==1)	//currentdir                                   //~1A2fI~
        {                                                          //~1403I~
            updownpath=dirname;                                    //~1403I~
        	rc=3;                                                   //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403R~
        {                                                          //~1403R~
            int filelistpos=name2file[pos];                        //~1403R~
            selectedFile=filelist[filelistpos];                             //~1319I~//~1403R~
	        if (Dump.Y) Dump.println("FileDialog chkSelectedFile index="+filelistpos);//~1A4nR~
            if(selectedFile.isDirectory())                                 //~1403R~
            {                                                      //~1403R~
                updownpath=selectedFile.getAbsolutePath();                   //~1319I~//~1324R~//~1403R~
                rc=1;                                              //~1403I~
            }                                                      //~1403R~
            else                                                   //~1403R~
            {                                                      //~1403R~
            	selectedFilename=selectedFile.getName();                   //~1403R~
            }                                                      //~1403R~
        }
        
        if (Dump.Y) Dump.println("FileDialog chkSelectedfile rc="+rc+",name="+selectedFilename);//~1319I~//~1506R~
        return rc;
    }
//    public boolean bitmapAccept(File dir, String name)             //~1404I~//~1A21R~
//    {                                                              //~1404I~//~1A21R~
//        if (name.endsWith("."+Global.getParameter("extension","bmp"))) return true;//~1404I~//~1A21R~
//        else return false;                                         //~1404I~//~1A21R~
//    }                                                              //~1404I~//~1A21R~
	public boolean saveGameAccept(File dir,String name)            //~1407I~
	{                                                              //~1407I~
		if (name.endsWith("."+Global.getParameter("extension","sto"))) return true;//~1407I~
		else return false;                                         //~1407I~
	}                                                              //~1407I~
	public boolean saveTxtAccept(File dir,String name)             //~1418I~
	{                                                              //~1418I~
		if (name.endsWith("."+Global.getParameter("extension","txt"))) return true;//~1418I~
		else return false;                                         //~1418I~
	}                                                              //~1418I~
	//** Interface:FileNameFilter method***************************************//~1A21I~
	public boolean accept (File dir, String name)                  //~1A21I~
//  {	if (name.endsWith("."+Global.getParameter("extension","sgf"))) return true;//~1A21R~
    {                                                              //~1A21I~
//      String fs=inputfilter.getText();                           //~1A21I~//~1A4dR~
        String fs=filterString;                              //~1A4dI~
//        if (swReplayFile)                                          //~1A30I~//~1A4eR~
//            Prop.putPreference(PKEY_REPLAYFILTER,fs);              //~1A30I~//~1A4eR~
//        else                                                       //~1A33I~//~1A4eR~
//        if (swFGF)                                                 //~1A33I~//~1A4eR~
//            Prop.putPreference(PKEY_REPLAYFILTER,fs);              //~1A33I~//~1A4eR~
        if (fs.equals(""))                                         //~1A21I~
        	return true;                                           //~1A21I~
        if (fs.indexOf('?')>=0 || fs.indexOf('*')>=0)                 //+1Ah9I~
        {                                                          //+1Ah9I~
            String regx=fs.replace("?",".?").replace("*",".*");    //+1Ah9I~
            return name.matches(regx);                             //+1Ah9I~
        }                                                          //+1Ah9I~
        else                                                       //+1Ah9I~
    	if (name.endsWith("."+fs)) return true;                    //~1A21R~
		else return false;                                         //~1A21I~
	}                                                              //~1A21I~
    //******************************************************************//~1A21I~
    //*after dismiss                                               //~1A22I~
    //******************************************************************//~1A22I~
    private void callbackLoad()                                    //~1A21I~
    {                                                              //~1A21I~
//    	Notes notes=Notes.load(loadFilename);                      //~1A21R~//~1A30R~
      	Notes notes=Notes.load(loadFilename,selectedItemFileTypeId,selectedItemEncoding);//~1A30R~
        if (notes==null)                                           //~1A21I~
        	return;                                                //~1A21I~
        if (swFGF)                                                 //~1A33I~
	        notes.gameoptions|=GameQuestion.GAMEOPT_FREEBOARD;     //~1A33I~
        if (!checkValidity(notes))	//load result chk              //~1A4zI~
        	return;                                                //~1A4zI~
        if (FDI instanceof FreeGoFrame && (notes.gameoptions & GameQuestion.GAMEOPT_FREEBOARD)==0)//~1A2fI~
        {                                                          //~1A2fI~
        	AView.showToast(R.string.ErrNotFileForFreeboard);      //~1A2fI~
            return;                                                //~1A2fI~
        }                                                          //~1A2fI~
        if (!(FDI instanceof FreeGoFrame) && (notes.gameoptions & GameQuestion.GAMEOPT_FREEBOARD)!=0)//~1A2fI~
        {                                                          //~1A2fI~
        	AView.showToast(R.string.ErrFileForFreeboard);         //~1A2fI~
            return;                                                //~1A2fI~
        }                                                          //~1A2fI~
        	                                                       //~1A2fI~
//  	TimedGoFrame tgf=(TimedGoFrame)parentFrame;                  //~1A21I~//~1A22R~//~1A24R~
//      tgf.loadNotes=notes;                                       //~1A21I~//~1A22R~//~1A24R~
//      tgf.doAction(AG.resource.getString(R.string.ActionFileLoad));//~1A21R~//~1A22R~//~1A24R~
		replaycolorwhite=ReplayColorWhite.getState();              //~1A2dR~
        FDI.fileDialogLoaded(parentDialog,notes);                  //~1A24R~
    }                                                              //~1A21I~
    //******************************************************************//~1A22I~
    //*after dismiss                                               //~1A22I~
    //******************************************************************//~1A22I~
    private void callbackSave(boolean Psaved)                                    //~1A22I~//~1A24R~
    {                                                              //~1A22I~
//        TimedGoFrame tgf=(TimedGoFrame)parentFrame;                //~1A22R~//~1A24R~
//        tgf.doAction(AG.resource.getString(R.string.ActionFileSaved));//~1A22R~//~1A24R~
		String fnm=saveNotes.getSavedFilename();                    //~1A4sI~
		if (Psaved)                                                //~1A24I~
//          FDI.fileDialogSaved(saveNotes.name);                   //~1A4sR~
            FDI.fileDialogSaved(fnm);                              //~1A4sI~
        else                                                       //~1A24I~
	        FDI.fileDialogNotSaved();                              //~1A24I~
    }                                                              //~1A22I~
    //******************************************************************//~1A26I~
    //*from MainFrameOption                                        //~1A26I~
    //******************************************************************//~1A26I~
    public static String getSaveDir()                              //~1A26I~
    {                                                              //~1A26I~
    	String sdpath,path;                                        //~1A26I~
    //************************                                     //~1A26I~
        if (Ssdpath==null)                                         //~1A26I~
        	Ssdpath=Prop.getSDPath("");                            //~1A21I~//~1A26I~
        sdpath=Ssdpath;                                            //~1A26I~
        if (sdpath!=null)                                     //~1A21I~//~1A26I~
			sdpath+="/"+GAMES_PATH;          //~1A21I~//~1A22R~    //~1A26I~
        path=Prop.getPreference(Prop.GAMEFILE,sdpath);             //~1A26I~
        if (path==null)                                            //~1A42I~
        {                                                          //~1A42I~
        	path=Prop.getOutputDataFilesPath(Prop.GAMEFILE);           //~1A42I~
        }                                                          //~1A42I~
        if (path==null)                                            //~1A26I~
        	path="sdcard not available";                           //~1A26I~
        return path;                                               //~1A26I~
    }                                                              //~1A26I~
    //*********************************************************************//~1A4rM~
    public static String getFixedSaveDirPath(boolean Pfixed)       //~1A4rR~
    {                                                              //~1A4rM~
    	String path;                                               //~1A4rM~
    	if (Pfixed)                                                //~1A4rR~
        	path=AG.SaveDir;                                       //~1A4rM~
        else                                                       //~1A4rM~
        	path=FileDialog.getSaveDir();	//last folder by filedialog//~1A4rM~
        return path;                                               //~1A4rI~
    }                                                              //~1A4rM~
    //*********************************************************************//~1A4rI~
    public static String getFixedSaveDirPath()                     //~1A4rI~
    {                                                              //~1A4rI~
		return getFixedSaveDirPath((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0);//~1A4rI~
    }                                                              //~1A4rI~
    //******************************************************************//~1A26I~
    //*from FreeGoFrame(Ext=.ss)	//no callback required         //~1A26I~
    //******************************************************************//~1A26I~
    public static void saveDirect(FreeGoFrame Pfgf,Notes Pnotes)   //~1A26R~
    {                                                              //~1A26I~
    	String path,fnm;                                           //~1A26I~
        boolean rc=true;                                           //~1A26I~
    //************************                                     //~1A26I~
//  	path=getSaveDir();                                         //~1A4rR~
    	path=getFixedSaveDirPath();                                //~1A4rI~
        try                                                        //~1A26I~
        {                                                          //~1A26I~
	        fnm=(Pnotes.name+"-"+Pnotes.seq+"."+SNAPSHOT_EXT).replace(" ","_");       //~1A26R~//~1A2dR~
    	    Pnotes.save(path+"/"+fnm);                             //~1A26R~
        }                                                          //~1A26I~
        catch(Exception e)                                         //~1A26I~
        {                                                          //~1A26I~
        	Dump.println(e,"FileDialog:saveDirect:ss");            //~1A26I~
            rc=false;                                              //~1A26I~
        }                                                          //~1A26I~
        FileDialogI fdi=(FileDialogI)Pfgf;                         //~1A26I~
		if (rc)                                                    //~1A26I~
        {                                                          //~1A4sI~
			fnm=Pnotes.getSavedFilename();                  //~1A4sI~
//          fdi.fileDialogSaved(Pnotes.name);                      //~1A4sR~
            fdi.fileDialogSaved(fnm);                              //~1A4sI~
        }                                                          //~1A4sI~
        else                                                       //~1A26I~
	        fdi.fileDialogNotSaved();                              //~1A26R~
    }                                                              //~1A26I~
    //******************************************************************//~1A26I~
    //*from TGF(Ext=.sg)	//callback required                    //~1A26I~
    //******************************************************************//~1A26I~
	public static void saveDirect(FileDialogI Pfdi,Notes Pnotes,String Pfilter)//~1A26I~
    {                                                              //~1A26I~
    	String path,fnm;                                           //~1A26I~
        boolean rc=true;                                           //~1A26I~
    //************************                                     //~1A26I~
        if (Dump.Y) Dump.println("FileDialog:saveDirect name=="+Pnotes.name);//~1A26I~
        try                                                        //~1A26I~
        {                                                          //~1A26I~
//  		path=getSaveDir();                                     //~1A4rR~
    		path=getFixedSaveDirPath();                            //~1A4rI~
//      	fnm=Pnotes.name+"."+Pfilter;                           //~1A26I~//~1A2dR~
        	fnm=(Pnotes.name+"."+Pfilter).replace(" ","_");        //~1A2dR~
        	Pnotes.save(path+"/"+fnm);                             //~1A26I~
    	    AView.showToast(R.string.Saved,Pnotes.filenameSaved);  //~1A4sI~
        }                                                          //~1A26I~
        catch(Exception e)                                         //~1A26I~
        {                                                          //~1A26I~
        	Dump.println(e,"FileDialog:saveDirect:sg");            //~1A26I~
            rc=false;                                              //~1A26I~
        }                                                          //~1A26I~
		if (rc)                                                    //~1A26I~
        {                                                          //~1A4sI~
			fnm=Pnotes.getSavedFilename();                         //~1A4sI~
//          Pfdi.fileDialogSaved(Pnotes.name);                     //~1A4sR~
            Pfdi.fileDialogSaved(fnm);                             //~1A4sI~
        }                                                          //~1A4sI~
        else                                                       //~1A26I~
	        Pfdi.fileDialogNotSaved();                             //~1A26I~
    }                                                              //~1A26I~
    //**************************************                       //~1A30I~
    //*Spinner(ChiceAction)                                        //~1A30I~
    //**************************************                       //~1A30I~
    @Override                                                      //~1A30I~
	public void itemAction (String s, boolean flag)                //~1A30I~
	{                                                              //~1A30I~
		String item="";                                            //~1A30I~
    //******************                                           //~1A30I~
		if (Dump.Y) Dump.println("FileDialog itemAction string="+s+",flag="+flag);//~1A30I~
        if (s.equals(ITEMACTION_FILETYPE))                          //~1A30I~
        {                                                          //~1A30I~
        	int pos=ChoiceFileType.getSelectedIndex();      //~1A30I~
            if (pos!=0)                                            //~1A30R~
            {                                                      //~1A30I~
				 item=ChoiceFileType.getSelectedItem();         //~1A30I~
            }                                                      //~1A30I~
            selectedItemFileTypeId=NotesFmt.FILETYPEIDTB[pos];	           //~1A30I~
            if (!item.equals(selectedItemFileType))                //~1A30R~
            {                                                      //~1A30R~
                selectedItemFileType=item;                         //~1A30R~
                Prop.putPreference(PKEY_FILETYPE,item);            //~1A30R~
            }                                                      //~1A30I~
        }                                                          //~1A30I~
        else                                                       //~1A30I~
        if (s.equals(ITEMACTION_FILEENCODING))                      //~1A30I~
        {                                                          //~1A30I~
        	int pos=ChoiceEncoding.getSelectedIndex();       //~1A30I~
            if (pos!=0)                                            //~1A30I~
				item=ChoiceEncoding.getSelectedItem();         //~1A30I~
            if (!item.equals(selectedItemEncoding))                //~1A30R~
            {                                                      //~1A30R~
                selectedItemEncoding=item;                         //~1A30R~
                Prop.putPreference(PKEY_FILEENCODING,item);        //~1A30R~
            }                                                      //~1A30R~
        }                                                          //~1A30I~
	}                                                              //~1A30I~
	//** Interface:ListI ***************************************   //~1A4aI~
    public void onListItemClicked(int Pcurpos,int Pselectedpos)    //~1A4aI~
    {                                                              //~1A4aI~
    	if (swDeleteMultiple)                                      //~1A4nI~
        	return;	//no process double click                      //~1A4nI~
    	String o;                                                  //~1A4aI~
    	if (Dump.Y) Dump.println("FileDialog:onListItemClicked cpos="+Pcurpos+",selectedpos="+Pselectedpos);//~1A4aI~
    	if (Pcurpos==Pselectedpos)                                 //~1A4aI~
        {                                                          //~1A4aI~
			o=AG.resource.getString(R.string.Open);                //~1A4aI~
		    swOpenByClick=true;                                    //~1A4zI~
			doAction(o);                                           //~1A4aI~
		    swOpenByClick=false;                                   //~1A4zI~
        }                                                          //~1A4aI~
    }                                                              //~1A4aI~
    //*********************************************************    //~1A4nI~
	//** Interface:ListI ***check selectable                       //~1A4nI~
	//*rc:4:not selectable                                         //~1A4nI~
    //*********************************************************    //~1A4nI~
    public int onListItemClickedMultiple(int Ppos)                 //~1A4nI~
    {                                                              //~1A4nI~
    	if (Ppos<=1||Ppos>=name2file.length)	//parent or current//~1A4nI~
        	return 4;                                              //~1A4nI~
        int pos=name2file[Ppos];                                   //~1A4nI~
        File f=filelist[pos];                                      //~1A4nI~
	    if (Dump.Y) Dump.println("FileDialog chkSelectedFileMultiple Pos="+Ppos+",index="+pos);//~1A4nI~
        if(f.isDirectory())                                        //~1A4nR~
        	return 4;                                              //~1A4nI~
        return 0;                                                  //~1A4nI~
    }                                                              //~1A4nI~
    //**************************************                       //~1A4nR~
    private Drawable getBGC(View Pview)                            //~1A4nR~
    {                                                              //~1A4nR~
//      int col=0xff808080;                                        //~1A4nR~
        Drawable dr=Pview.getBackground();                         //~1A4nR~
        if (Dump.Y) Dump.println("getBGC drawable="+dr.toString());//~1A4nR~
//      int col=dr.getPaint().getColor();                          //~1A4nR~
//      int col=Pview.getSolidColor();                             //~1A4nR~
//      return col;                                                //~1A4nR~
		return dr;                                                 //~1A4nR~
    }                                                              //~1A4nR~
    //**************************************                       //~1A4pI~
    private void setSelected(int Ppos)                             //~1A4pI~
    {                                                              //~1A4pI~
    	int pos=Ppos;                                                  //~1A4nI~
    	if (Ppos>=namelist.length)                                 //~1A4nR~
        	pos=namelist.length-1;                                 //~1A4nR~
        if (pos<0)                                                 //~1A4nI~
        	pos=0;                                                 //~1A4nI~
    	lister.setSelection(pos);                                  //~1A4nR~
        if (Dump.Y) Dump.println("FileDialog:setSelection Ppos="+Ppos+",pos="+pos+",list="+namelist.length);//~1A4nR~
    }                                                              //~1A4pI~
    //**************************************                       //~1A4zI~
    private void openFileViewer(String Pfnm)                       //~1A4zI~
    {                                                              //~1A4zI~
        if (Dump.Y) Dump.println("FileDialog:openFileViewer:"+Pfnm);//~1A4zI~
        new FileViewer(this,Pfnm);                                 //~1A4zI~
    }                                                              //~1A4zI~
    //**************************************                       //~1A4zI~
    private boolean checkValidity(Notes Pnotes)                        //~1A4zI~
    {                                                              //~1A4zI~
        int rc=Pnotes.chkValidity();	//1:no move,2:no init piece//~1A4zI~
    	if ( (!swFGF && rc>=1)  //replay but has no move           //~1A4zI~
        ||   (swFGF && rc>=2))  //tsumego allows no move but requires init piexe//~1A4zI~
        {                                                          //~1A4zI~
        	if (rc==1)                                             //~1Ah7I~
        		AView.showToastLong(R.string.ErrInvalidFileDataNoMove);//~1Ah7I~
            else                                                   //~1Ah7I~
        	if (rc==2)                                             //~1Ah7I~
        		AView.showToastLong(R.string.ErrInvalidFileDataNoPiece);//~1Ah7I~
            else                                                   //~1Ah7I~
        	AView.showToastLong(R.string.ErrInvalidFileData);      //~1A4zI~
        	return false;                                          //~1A4zI~
        }                                                          //~1A4zI~
        return true;                                               //~1A4zI~
    }                                                              //~1A4zI~
    //**************************************                       //~1Ah8I~
    private void selectAll()                                       //~1Ah8I~
    {                                                              //~1Ah8I~
        if (Dump.Y) Dump.println("FileDialog:selectAll swDeleteMultiple="+swDeleteMultiple);//~1Ah8R~
        if (!swDeleteMultiple)                                     //~1Ah8I~
        {                                                          //~1Ah8I~
	        swDeleteMultiple=true;                                 //~1Ah8I~
        	Color bgcol=Color.yellow;                              //~1Ah8I~
	    	buttonDeleteMulti.setBackground(buttonDeleteMulti.androidButton,bgcol);//~1Ah8I~
        }                                                          //~1Ah8I~
        lister.selectAll(2/*from pos*/,"/"/*avoid directory*/);    //~1Ah8R~
    }                                                              //~1Ah8I~
    //**************************************                       //~1Ah8I~
    private void deselectAll()                                     //~1Ah8I~
    {                                                              //~1Ah8I~
        if (Dump.Y) Dump.println("FileDialog:deselectAll");        //~1Ah8I~
        if (swDeleteMultiple)                                      //~1Ah8I~
        {                                                          //~1Ah8I~
	        swDeleteMultiple=false;   //singlechicemode            //~1Ah8I~
	    	buttonDeleteMulti.androidButton.setBackgroundDrawable(btnBackground);//~1Ah8I~
        }                                                          //~1Ah8I~
        lister.deselectAll();                                      //~1Ah8R~
    }                                                              //~1Ah8I~
}//class                                                           //~1318R~
