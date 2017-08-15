//*CID://+1AhaR~:                             update#=   25;       //~1AhaR~
//*****************************************************************************//~3309I~
//1Aha 2016/11/19 add option trace X for special debug in notrace mode//~1AhaR~
//1Ad7 2015/07/20 Canvas/UiThread TraceOption was not effective if OptionDialog is opened//~1Ad7I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing, (Dump.T) for UiThread//~1A6AI~
//1A4r 2014/12/05 change savedir to editable                       //~1A4rI~
//1A26 2013/03/25 save folder fix option(no dislog popup for save game)//~1A26I~
//1A13 2013/03/10 1touch option                                    //~1A13R~
//1A12 2013/03/08 Option to diaplay time is for tiomeout=0(if not 0 diaplay timer)//~3309I~
//*****************************************************************************//~3309I~
package jagoclient;
import java.io.File;

import android.view.View;
import com.Asgts.AG;                                               //~3213R~
import com.Asgts.AView;
import com.Asgts.Alert;
import com.Asgts.AlertI;
import com.Asgts.Prop;                                             //~3213R~
import com.Asgts.R;                                                //~3213R~
import com.Asgts.awt.Checkbox;                                     //~3213R~
import com.Asgts.awt.FileDialog;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CheckboxAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.DoActionListener;
import jagoclient.gui.FormTextField;

//*CID://+@@@@R~:                             update#=    5;       //~@@@@I~

//class MainFrameOptions extends CloseDialog                       //~1Ad7R~
public class MainFrameOptions extends CloseDialog                  //~1Ad7I~
	implements AlertI//~@@@@I~//~3114R~
{                                                                  //~@@@@R~
	private Checkbox Coordinate,/*BigTimer,*/Trace,DigitalCoordinate;               //~@@@@I~    //~3114R~//~3119R~//~3225R~//~3309R~
	private Checkbox TraceC,TraceT;                                //~1A6AI~
	private Checkbox TraceX;                                       //~1AhaI~
	private Checkbox OneTouchFB;                                   //~1A13I~
	private Checkbox OneTouch;                                     //~1A13I~
	private Checkbox FixSaveDir;                                   //~1A26I~
	private FormTextField YourName;                                             //~3114I~//~3119R~
	private FormTextField SaveDir;                                 //~1A4rI~
	private DoActionListener dal;                                  //~3119R~
    private boolean changeBoard;
    private String mkdirpath;                                      //~1A4rI~
    MainFrame MF;//~3119I~
    private String itemActionNameFixDir="cbFixDir";                //~1A4rI~
    private boolean swInitialized;                                  //~1A4rI~
	public MainFrameOptions(MainFrame f)                                 //~@@@@I~//~3114R~//~3119R~
	{                                                              //~@@@@I~
//  	super(f,AG.resource.getString(R.string.MainFrameOptions),R.layout.mainframeoptions,false/*modal*/,false/*modalInput*/);//~@@@@I~//~3114R~//~3115R~//~1A6AR~
    	super(f,AG.resource.getString(R.string.MainFrameOptions),  //~1A6AI~
				((AG.layoutMdpi/*mdpi and height or width <=320*/ && !AG.portrait)//~1A6AI~
                  ? R.layout.mainframeoptions_land_mdpi : R.layout.mainframeoptions),//~1A6AI~
				false/*modal*/,false/*modalInput*/);               //~1A6AI~
        MF=f;
		dal=(DoActionListener)f;                                                           //~3114I~//~3119R~
//        BigTimer=new Checkbox(this,R.id.BigTimer);                 //~3208I~//~3309R~
        Coordinate=new Checkbox(this,R.id.Coordinate);             //~3208I~
        DigitalCoordinate=new Checkbox(this,R.id.DigitalCoordinate);//~3225I~
        OneTouchFB=new Checkbox(this,R.id.OneTouchFB);             //~1A13I~
        OneTouch=new Checkbox(this,R.id.OneTouch);                 //~1A13I~
//      FixSaveDir=new Checkbox(this,R.id.FixSaveDir);             //~1A4rR~
        FixSaveDir=new CheckboxAction(this,R.id.FixSaveDir,itemActionNameFixDir);//~1A4rI~
        Trace=new Checkbox(this,R.id.Trace);                       //~3208I~
        TraceT=new Checkbox(this,R.id.TraceT);                     //~1A6AI~
        TraceC=new Checkbox(this,R.id.TraceC);                     //~1A6AI~
        TraceX=new Checkbox(this,R.id.TraceX);                     //~1AhaI~
//        BigTimer.setState((AG.Options & AG.OPTIONS_BIG_TIMER)!=0); //~3114I~//~3309R~
        Coordinate.setState((AG.Options & AG.OPTIONS_COORDINATE)!=0);//~3114I~
        DigitalCoordinate.setState((AG.Options & AG.OPTIONS_DIGITALCOORDINATE)!=0);//~3225I~
        OneTouchFB.setState((AG.Options & AG.OPTIONS_1TOUCH_FB)!=0);//~1A13I~
        OneTouch.setState((AG.Options & AG.OPTIONS_1TOUCH)!=0);    //~1A13I~
        FixSaveDir.setState((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0);//~1A26R~
        YourName=new FormTextField(this,R.id.YourName,AG.YourName);//~3208I~
//  	String path=FileDialog.getSaveDir();                       //~1A4rR~
//      new FormTextField(this,R.id.SaveDir,path);                 //~1A4rR~
    	String path=FileDialog.getFixedSaveDirPath();              //~1A4rR~
        SaveDir=new FormTextField(this,R.id.SaveDir,path);         //~1A4rI~
                                                                   //~3114I~
	    if (AG.isDebuggable)                                          //~v107R~//~3114I~
        {                                                          //~3114I~
        	Trace.setState((AG.Options & AG.OPTIONS_TRACE)!=0);    //~3114R~
        	Trace.setVisibility(Trace.androidCheckBox,View.VISIBLE);//~3114R~
        	TraceC.setState((AG.Options & AG.OPTIONS_TRACE_CANVAS)!=0);//~1A6AI~
        	TraceC.setVisibility(TraceC.androidCheckBox,View.VISIBLE);//~1A6AI~
        	TraceT.setState((AG.Options & AG.OPTIONS_TRACE_UITHREAD)!=0);//~1A6AI~
        	TraceT.setVisibility(TraceT.androidCheckBox,View.VISIBLE);//~1A6AI~
        	TraceX.setState((AG.Options & AG.OPTIONS_TRACE_X)!=0); //~1AhaI~
        	TraceX.setVisibility(TraceX.androidCheckBox,View.VISIBLE);//~1AhaI~
        }                                                          //~3114I~
        new ButtonAction(this,0,R.id.OK);                     //~3208I~
        new ButtonAction(this,0,R.id.Cancel);                 //~3208I~
        new ButtonAction(this,0,R.id.Help);                   //~3208I~
        setDismissActionListener(this/*DoActionListener*/);        //~3119I~
		show();                                                    //~@@@@I~
    	swInitialized=true;                                         //~1A4rI~
	}                                                              //~@@@@I~
//*****************************************                        //~@@@@R~
//*return false when err,dont dismiss                              //~1A4rI~
//*****************************************                        //~1A4rI~
//  private void getOptions()                                      //~1A4rR~
    private boolean getOptions()                                   //~1A4rI~
    {                                                              //~@@@@I~
        AG.YourName=YourName.getText().toString();                            //~3114I~
//        if (BigTimer.getState())                                   //~3118R~//~3309R~
//            AG.Options|=AG.OPTIONS_BIG_TIMER;                      //~3118I~//~3309R~
//        else                                                       //~3118I~//~3309R~
//            AG.Options&=~AG.OPTIONS_BIG_TIMER;                     //~3118I~//~3309R~
        if (Coordinate.getState())                                 //~3118R~
			AG.Options|=AG.OPTIONS_COORDINATE;                     //~3118I~
		else                                                       //~3118I~
			AG.Options&=~AG.OPTIONS_COORDINATE;                    //~3118I~
        if (DigitalCoordinate.getState())                          //~3225I~
        {                                                          //~3225I~
			AG.Options|=AG.OPTIONS_DIGITALCOORDINATE;              //~3225I~
            AG.SshogiV=AG.SshogiVN;	//digital rank                     //~3225I~
        }                                                          //~3225I~
		else                                                       //~3225I~
        {                                                          //~3225I~
			AG.Options&=~AG.OPTIONS_DIGITALCOORDINATE;             //~3225I~
            AG.SshogiV=AG.SshogiVJE;	//japanese nueral or letter        //~3225I~
        }                                                          //~3225I~
        if (OneTouchFB.getState())                                 //~1A13I~
			AG.Options|=AG.OPTIONS_1TOUCH_FB;                       //~1A13I~
		else                                                       //~1A13I~
			AG.Options&=~AG.OPTIONS_1TOUCH_FB;                      //~1A13I~
        if (OneTouch.getState())                                   //~1A13I~
			AG.Options|=AG.OPTIONS_1TOUCH;                         //~1A13I~
		else                                                       //~1A13I~
			AG.Options&=~AG.OPTIONS_1TOUCH;                        //~1A13I~
        if (FixSaveDir.getState())                                 //~1A26I~
			AG.Options|=AG.OPTIONS_FIXSAVEDIR;                     //~1A26I~
		else                                                       //~1A26I~
			AG.Options&=~AG.OPTIONS_FIXSAVEDIR;                    //~1A26I~
	    if (AG.isDebuggable)                                       //~3114I~
        {                                                          //~3114I~
        	int old=AG.Options;                                    //~3119I~
	        if (Trace.getState())                                  //~3118R~
				AG.Options|=AG.OPTIONS_TRACE;                      //~3118I~
  			else                                                   //~3118I~
				AG.Options&=~AG.OPTIONS_TRACE;                     //~3118I~
            if ((old & AG.OPTIONS_TRACE)!=(AG.Options & AG.OPTIONS_TRACE))	//redraw required//~3119I~
	            Dump.setOption((AG.Options & AG.OPTIONS_TRACE)!=0);     //~3114I~//~3119R~
	        if (TraceC.getState())                                 //~1A6AI~
				AG.Options|=AG.OPTIONS_TRACE_CANVAS;               //~1A6AI~
  			else                                                   //~1A6AI~
				AG.Options&=~AG.OPTIONS_TRACE_CANVAS;              //~1A6AI~
            if ((old & AG.OPTIONS_TRACE_CANVAS)!=(AG.Options & AG.OPTIONS_TRACE_CANVAS))	//redraw required//~1A6AI~
	            Dump.C=(AG.Options & AG.OPTIONS_TRACE_CANVAS)!=0;  //~1A6AI~
	        if (TraceT.getState())                                 //~1A6AI~
				AG.Options|=AG.OPTIONS_TRACE_UITHREAD;             //~1A6AI~
  			else                                                   //~1A6AI~
				AG.Options&=~AG.OPTIONS_TRACE_UITHREAD;            //~1A6AI~
            if ((old & AG.OPTIONS_TRACE_UITHREAD)!=(AG.Options & AG.OPTIONS_TRACE_UITHREAD))	//redraw required//~1A6AI~
	            Dump.T=(AG.Options & AG.OPTIONS_TRACE_UITHREAD)!=0;//~1A6AI~
	        if (TraceX.getState())                                 //~1AhaR~
				AG.Options|=AG.OPTIONS_TRACE_X;                    //~1AhaR~
  			else                                                   //~1AhaR~
				AG.Options&=~AG.OPTIONS_TRACE_X;                   //~1AhaR~
            if ((old & AG.OPTIONS_TRACE_X)!=(AG.Options & AG.OPTIONS_TRACE_X))	//redraw required//~1AhaR~
            {                                                      //~1AhaI~
                Dump.chngTraceX((AG.Options & AG.OPTIONS_TRACE_X)!=0);//+1AhaR~
            }                                                      //~1AhaI~
        }                                                          //~3114I~
        Prop.putPreference(AG.PKEY_OPTIONS,AG.Options);            //~@@@@I~
        Prop.putPreference(AG.PKEY_YOURNAME,AG.YourName);          //~3114I~
        String savedir=SaveDir.getText().toString();               //~1A4rM~
        if (!chkSaveDir(savedir))	//invalid dirname              //~1A4rM~
        	return false;                                          //~1A4rM~
		if ((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0)               //~1A4rI~
        {                                                          //~1A4rI~
        	if (!savedir.equals(""))                               //~1A4rI~
            {                                                      //~1A4rI~
        		AG.SaveDir=savedir;                                //~1A4rR~
	        	Prop.putPreference(AG.PKEY_SAVEDIR,savedir);       //~1A4rR~
            }                                                      //~1A4rI~
        }                                                          //~1A4rI~
        return true;                                               //~1A4rI~
    }                                                              //~@@@@I~
//*****************************************                        //~@@@@I~
	public void doAction (String o)                                //~@@@@I~
	{	if (o.equals(AG.resource.getString(R.string.OK)))          //~@@@@I~
		{                                                          //~@@@@I~
        	int old=AG.Options;                                    //~3119I~
          boolean rc=                                              //~1A4rI~
            getOptions();                                          //~@@@@I~
            if (!rc)	//failed                                   //~1A4rI~
            	return;	                                           //~1A4rI~
			setVisible(false); dispose();                          //~@@@@I~
            if ((old & AG.OPTIONS_COORDINATE)!=(AG.Options & AG.OPTIONS_COORDINATE))	//redraw required//~3119I~
            	changeBoard=true;                                  //~3119R~
            else                                                   //~3225I~
            if ((old & AG.OPTIONS_DIGITALCOORDINATE)!=(AG.Options & AG.OPTIONS_DIGITALCOORDINATE))	//redraw required//~3225I~
            	changeBoard=true;                                  //~3225I~
		}                                                          //~@@@@I~
    	else if (o.equals(AG.resource.getString(R.string.Cancel))) //~@@@2I~//~@@@@I~
		{                                                          //~@@@@I~
			setVisible(false); dispose();                          //~@@@@I~
		}                                                          //~@@@@I~
    	else if (o.equals(AG.resource.getString(R.string.Help)))   //~@@@2I~//~@@@@I~
		{                                                          //~@@@@I~
//      	new HelpDialog(null,R.string.Help_MainFrameOptions);          //~@@@@I~//~3114R~//~3208R~//~3303R~
			new HelpDialog(null,R.string.HelpTitle_MainFrameOptions,"MainFrameOptions");                      //~v1A0I~//~3303I~//~3304R~
		}                                                          //~@@@@I~
        else if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))	//modal but no inputWait//~3119R~
		{                                                          //~3119I~
        //*after framelayout restored                              //~3119I~
        	if (Dump.Y) Dump.println("MainFrameOptions dismissDoAction changeBoard="+changeBoard);//~3119I~//~1Ad7R~
            if (changeBoard)                                       //~3119I~
	        	dal.doAction(AG.resource.getString(R.string.ActionChangeCoordinate));//~3119R~
		}                                                          //~3119I~
	}                                                              //~@@@@I~
//*****************************************                        //~1A4rI~
//*DoActionListener for chkbox                                     //~1A4rI~
//*****************************************                        //~1A4rI~
	public void itemAction(String o,boolean Pflag)                 //~1A4rI~
	{                                                              //~1A4rI~
		if (o.equals(itemActionNameFixDir))                        //~1A4rI~
		{                                                          //~1A4rI~
        	if (!swInitialized)                                    //~1A4rI~
            	return;                                            //~1A4rI~
            if (!Pflag)//reset to not fix                          //~1A4rI~
            {                                                      //~1A4rI~
    			String path=FileDialog.getFixedSaveDirPath(Pflag); //~1A4rR~
        		SaveDir.setText(path);	//update to cyrrent        //~1A4rI~
        	}                                                      //~1A4rI~
		}                                                          //~1A4rI~
	}                                                              //~1A4rI~
//*****************************************                        //~1A4rI~
	private boolean chkSaveDir(String Ppath)                       //~1A4rI~
	{                                                              //~1A4rI~
    	if (Ppath.equals(""))                                      //~1A4rI~
        	return true;                                           //~1A4rI~
    	File f=new File(Ppath);                                    //~1A4rI~
        if (f.exists())                                            //~1A4rI~
        {                                                          //~1A4rI~
        	if (f.isDirectory())                                   //~1A4rI~
            	return true;                                       //~1A4rI~
            else                                                   //~1A4rI~
            {                                                      //~1A4rI~
            	AView.showToast(R.string.ErrSaveDirIsNotDir,Ppath);//~1A4rI~
            	return false;                                      //~1A4rI~
            }                                                      //~1A4rI~
        }                                                          //~1A4rI~
        mkdirpath=Ppath;                                           //~1A4rI~
    	String title=AG.resource.getString(R.string.Title_SaveDirMkdirQuestion);//~1A4rI~
        String msg=AG.resource.getString(R.string.SaveDirMkdirQuestion,Ppath);//~1A4rI~
		Alert.simpleAlertDialog(this,title,msg,Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE);//calback alertButtonAction//~1A4rI~
        return false;                                              //~1A4rI~
    }                                                              //~1A4rI~
    //* AlertI ********************************************************************//~1A4rI~
	public int alertButtonAction(int Pbuttonid,int Pparm/*not used,value set by setSimpleData*/)//~1A4rI~
    {                                                              //~1A4rI~
    	int rc;                                                    //~1A4rI~
		rc=makeSaveDir(Pbuttonid);                                 //~1A4rI~
    	return rc;                                                 //~1A4rI~
    }                                                              //~1A4rI~
	private int makeSaveDir(int Pbuttonid)                         //~1A4rI~
    {                                                              //~1A4rI~
    	boolean agree=(Pbuttonid==Alert.BUTTON_POSITIVE);          //~1A4rI~
        if (!agree)                                                //~1A4rI~
        	return 4;                                             //~1A4rI~
    	File f=new File(mkdirpath);                                //~1A4rI~
       	boolean rc=f.mkdir();                                      //~1A4rI~
        String msg;                                                //~1A4rI~
        if (rc)                                                    //~1A4rI~
        	msg=AG.resource.getString(R.string.InfoSaveDirMade,mkdirpath);//~1A4rI~
        else                                                       //~1A4rI~
        	msg=AG.resource.getString(R.string.ErrSaveDirMakeFailed,mkdirpath);//~1A4rI~
        AView.showToast(msg);                 //~1A4rI~
        return rc?0:4;
    }                                                              //~1A4rI~
//*****************************************                        //~1Ad7I~
//**from AG                                                        //~1Ad7I~
//*****************************************                        //~1Ad7I~
	public static void initialOptions()                            //~1Ad7I~
	{                                                              //~1Ad7I~
	    if (AG.isDebuggable)                                       //~1Ad7I~
        {                                                          //~1Ad7I~
	        Dump.C=(AG.Options & AG.OPTIONS_TRACE_CANVAS)!=0;      //~1Ad7I~
	        Dump.T=(AG.Options & AG.OPTIONS_TRACE_UITHREAD)!=0;    //~1Ad7I~
        }                                                          //~1Ad7I~
	}                                                              //~1Ad7I~
}
