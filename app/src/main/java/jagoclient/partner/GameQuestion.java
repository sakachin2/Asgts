//*CID://+1Ah0R~:                             update#=  132;       //~1Ab4R~//~1Ah0R~
//***********************************************************************//~v1A0I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//1Ab4 2015/05/04 (Bug of 1Aa8) FreebiardQuestion NPE;FreeboardQuestion extends GameQuestion and resid should be of FBQ)//~1Ab4R~
//1Aa8 2015/04/20 put in layout the gamequestion for mdpi          //~1Aa8I~
//1A49 2014/11/01 opponent name for local match(for savefile when asgts)(Ahsv:1A56,Ajagot1w:v1D9)//~1A49I~
//1A3c 2013/06/04 set default totaltime to 60min                   //~1A3cI~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2j 2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//                that cause RunTimEException not looper           //~1A2jI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A1h 2013/03/18 default gameoption:bigtimer                      //~1A1hI~
//1A12 2013/03/08 Option to diaplay time is for tiomeout=0(if not 0 diaplay timer)//~1A12I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//***********************************************************************//~v1A0I~
package jagoclient.partner;

import android.view.View;

import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.AView;                                            //~@@@2R~
import com.Asgts.ProgDlg;
import com.Asgts.Prop;                                             //~@@@2R~
import com.Asgts.R;                                                //~@@@2R~
import com.Asgts.URunnable;                                        //~@@@2R~
import com.Asgts.URunnableI;                                       //~@@@2R~
import com.Asgts.awt.Checkbox;                                     //~@@@2R~
import com.Asgts.awt.Color;                                        //~@@@2R~
import com.Asgts.awt.Component;
import com.Asgts.awt.FileDialog;
import com.Asgts.awt.FileDialogI;
import com.Asgts.awt.Frame;                                        //~@@@2R~

import jagoclient.Dump;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.SpinButtonDialog;
//import jagoclient.gui.ButtonAction;                              //~2C26R~
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.CloseFrame;
import jagoclient.gui.FormTextField;                             //~2C26R~//~@@@@R~
//import jagoclient.gui.MyLabel;                                   //~2C26R~
//import jagoclient.gui.MyPanel;                                   //~2C26R~
//import jagoclient.gui.Panel3D;                                   //~2C26R~

//import java.awt.Choice;                                          //~@@@@R~
//import java.awt.GridLayout;
//import java.awt.Panel;

/**
Question to start a game with user definable paramters (handicap etc.)
*/

public class GameQuestion extends CloseDialog
	implements URunnableI                                          //~@@@2I~
//{   FormTextField BoardSize,Handicap,TotalTime,ExtraTime,ExtraMoves;//~@@@@R~
{                                                                  //~@@@@I~
    public  final static String PKEY_GAMEOPTIONS="GameOptions";//~@@@@R~//~@@@2R~//~1Ah0R~
    public  final static String PKEY_TOTAL_TIME="TotalTime";       //~@@@@R~//~1Ah0R~
    public  final static String PKEY_EXTRA_TIME="ExtraTime";       //~@@@@R~//~1Ah0R~
    public  final static String PKEY_HANDICAP="handicap";          //~v1A0I~//~1Ah0R~
    public  final static Color  COLOR_YOURNAME=new Color(0x80,0xc0,0x80);//~@@@2I~
    private final static String PKEY_LOCAL_OPPONENT_NAME="LocalOpponentName";//~1A49I~
    public  final static int    DEFAULT_TOTALTIME=60; //min        //~1A3cI~
    public  final static int    DEFAULT_EXTRATIME=30; //sec        //~@@@@R~//~v1A0R~
    public  final static int    TOTALTIME_MIN=0;                   //~v1A0I~
    public  final static int    TOTALTIME_MAX=1440; //1day by min  //~v1A0I~
    public  final static int    EXTRATIME_MIN=0;                   //~v1A0I~
    public  final static int    EXTRATIME_MAX=3600; //1hour by seconds//~v1A0I~
                                                                   //~v1A0I~
    public  final static int    GAMEOPT_MOVEFIRST=0x01;            //~@@@2I~
    public  final static int    GAMEOPT_NOTIFYCHECK=0x02;          //~v1A0R~
    public  final static int    GAMEOPT_FOUL_PATHERR          =0x10;         //~@@@2R~//~v1A0R~
    public  final static int    GAMEOPT_FOUL_2PAWN            =0x20;//~v1A0I~
    public  final static int    GAMEOPT_FOUL_UNMOVABLEDROP    =0x40;//~v1A0I~
    public  final static int    GAMEOPT_FOUL_DROPPAWNCHECKMATE=0x80;//~v1A0I~
    public  final static int    GAMEOPT_FOUL_MISSMOVE       =0x0100;//~v1A0I~
    public  final static int    GAMEOPT_FOUL_LEAVECHECK     =0x0200;//~v1A0I~
    public  final static int    GAMEOPT_FREEBOARD           =0x0400;    //request to CGF to create FreeBoard//~1A10I~
    public  final static int    GAMEOPT_BIGTIMER            =0x0800;//~1A12I~
    public  final static int    GAMEOPT_NOTES               =0x1000;//~1A24R~
    public  final static int    GAMEOPT_RECEIVENOTES        =0x2000;//~1A2eI~
    public  final static int    GAMEOPT_NOTESFILE           =0x4000;//~1A30I~
    public  final static int    GAMEOPT_NOBIGTIMER          =0x8000; //for gtp//~1Ah0I~
    public  final static int    GAMEOPT_COMPUTERFIRST     =0x010000; //for gtp//+1Ah0I~
                                                                   //~1Ah0I~
    private static final int RF_WAITINGMSG=1;                      //~v1A0I~
    protected FormTextField TotalTime,ExtraTime;                   //~@@@2I~
    protected FormTextField OpponentName;                          //~1A49I~
    protected FormTextField YourName;                              //~@@@2I~
    protected FormTextField HandicapText;                                          //~v1A0I~//~1A12R~
    protected Checkbox MoveFirst,Foul2Pawn,FoulUnmovableDrop,FoulDropPawnCheckmate;//~v1A0R~//~1A12R~
    protected Checkbox NotifyCheck,FoulMissMove,FoulLeaveCheck;      //~v1A0R~//~1A12R~
    protected Checkbox BigTimer;                                   //~1A12R~
	private PartnerFrame PF;                                       //~v1A0R~//~1A12R~
    public int totaltime,extratime;                        //~@@@2R~//~v1A0R~
    protected int foul_2pawn,foul_unmovabledrop,foul_droppawncheckmate;//~v1A0R~//~1A12R~
    protected int foul_missmove,foul_leavecheck;                     //~v1A0R~//~1A12R~
	public int gameoptions;
	protected int movefirst,boardsz,notifycheck;                              //~@@@2R~//~v1A0R~//~1A12R~
	protected int bigtimer;                                        //~1A12R~
//    public Notes reloadNotes;                                    //~1A24R~
	public int Handicap;                                           //~v1A0I~
    private Frame frameForLocalGame;                               //~v1A0I~
    private CloseFrame parentFrame;                                //~1A24I~
    protected boolean swReloadGame;                                        //~1A24I~
	private boolean swLocalGame;                                   //~1A49I~
    protected  String pkey_GAMEOPTIONS,pkey_TOTAL_TIME,pkey_EXTRA_TIME,pkey_HANDICAP,pkey_LOCAL_OPPONENT_NAME;//~1Ah0I~
//******************************                                   //~1A10I~
//*for FreeBoardQuestion                                           //~1A10I~
//******************************                                   //~1A10I~
	public GameQuestion(Frame Pframe,int Presid,String Ptitle)     //~1A10I~
	{                                                              //~1A10I~
//      super(Pframe,Ptitle,Presid,false/*non modal*/,false);      //~1A10I~//~1Aa8R~
        super(Pframe,Ptitle,                                       //~1Aa8I~
//  		(AG.layoutMdpi ? R.layout.gamequestion_mdpi : R.layout.gamequestion),//~1Aa8I~//~1Ab4R~
    		Presid,   //layout.freeboardquestion                   //~1Ab4I~
//  		true,false);                                           //~1Aa8I~
    		false,false);                                          //~1Aa8I~
//*if modal latch was posted by button to popup child dialog when subthread(partner thread)//~1A10I~
        frameForLocalGame=Pframe;	//mainframe;                   //~1A10I~
        parentFrame=(CloseFrame)Pframe;                                        //~1A24I~
    }                                                              //~1A10I~
//******************************                                   //~1Ah0I~
//*for GtpGameQuestion                                             //~1Ah0I~
//******************************                                   //~1Ah0I~
	public GameQuestion(Frame Pframe,int Presid,String Ptitle,boolean Pmodal)//~1Ah0I~
	{                                                              //~1Ah0I~
        super(Pframe,Ptitle,                                       //~1Ah0I~
    		Presid,                                                //~1Ah0I~
    		Pmodal,false);                                         //~1Ah0I~
        parentFrame=(CloseFrame)Pframe;                            //~1Ah0I~
    }                                                              //~1Ah0I~
//******************************                                   //~@@@2I~
//*for LocalGameQuestion                                           //~@@@2I~
//******************************                                   //~@@@2I~
	public GameQuestion(Frame Pframe,String Ptitle)                //~@@@2R~
	{                                                              //~@@@2I~
//        super(Pframe,Ptitle,R.layout.gamequestion,true,false);     //~@@@2R~//~v1A0R~
//      super(Pframe,Ptitle,R.layout.gamequestion,false/*non modal*/,false);//~v1A0I~//~1Aa8R~
          super(Pframe,Ptitle,//~1Aa8I~
				(AG.layoutMdpi ? R.layout.gamequestion_mdpi : R.layout.gamequestion),//~1Aa8I~
    			false,false);                                      //~1Aa8I~
//*if modal latch was posted by button to popup child dialog when subthread(partner thread)//~v1A0I~
        frameForLocalGame=Pframe;	//mainframe;                   //~v1A0R~
        parentFrame=(CloseFrame)Pframe;                                        //~1A24I~
        swLocalGame=true;                                          //~1A49I~
    }                                                              //~@@@2I~
//******************************                                   //~@@@2I~
//*for PartnerFrame                                                //~1Ab4I~
//******************************                                   //~1Ab4I~
	public GameQuestion (PartnerFrame pf)
//  {   super(pf,Global.resourceString("Game_Setup),true);         //~@@@2R~
    {                                                              //~@@@2R~
//      super(pf,AG.resource.getString(R.string.Title_GameQuestion),R.layout.gamequestion,true,false);//~@@@2R~//~v1A0R~
//      super(pf,AG.resource.getString(R.string.Title_GameQuestion),R.layout.gamequestion,false/*non modal*/,false);//~v1A0I~//~1Aa8R~
        super(pf,AG.resource.getString(R.string.Title_GameQuestion),//~1Aa8I~
				(AG.layoutMdpi ? R.layout.gamequestion_mdpi : R.layout.gamequestion),//~1Aa8I~
				false/*non modal*/,false);                         //~1Aa8I~
		PF=pf;
        parentFrame=pf;                                            //~1A24I~
    	setGameData();                                             //~@@@2I~
                                                                   //~@@@@I~
        new ButtonAction(this,0,R.id.EditTotalTime);  //Request    //~v1A0I~
        new ButtonAction(this,0,R.id.EditExtraTime);  //Request    //~v1A0I~
        new ButtonAction(this,0,R.id.HandicapButton);  //Request   //~v1A0I~
        new ButtonAction(this,0,R.id.Request);  //Request     //~@@@2I~
        new ButtonAction(this,0,R.id.ButtonReload);  //open saved file//~1A24I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel       //~@@@2I~
        new ButtonAction(this,0,R.id.Help);  //Help           //~@@@2I~
        setDismissActionListener(this/*DoActionListener*/);        //~@@@@I~//~1A24I~
		validate();
		show();
	}
    protected void initvars()                                      //~1Ah0I~
    {                                                              //~1Ah0I~
    	pkey_GAMEOPTIONS=PKEY_GAMEOPTIONS;                         //~1Ah0I~
    	pkey_TOTAL_TIME=PKEY_TOTAL_TIME;                           //~1Ah0I~
    	pkey_EXTRA_TIME=PKEY_EXTRA_TIME;                           //~1Ah0I~
    	pkey_HANDICAP=PKEY_HANDICAP;                               //~1Ah0I~
    	pkey_LOCAL_OPPONENT_NAME=PKEY_LOCAL_OPPONENT_NAME;         //~1Ah0I~
    }                                                              //~1Ah0I~
    public void setGameData()                                      //~@@@2R~
    {                                                              //~@@@2I~
        initvars();                                                //~1Ah0I~
		int oldint;          //~@@@@I~//~@@@2R~
        YourName=new FormTextField(this,R.id.YourName,AG.YourName);//~@@@2R~
        YourName.setBackground(COLOR_YOURNAME);                //~@@@2I~
//  	oldint=Prop.getPreference(PKEY_TOTAL_TIME,0);              //~@@@@M~//~@@@2M~//~1A3cR~
//  	oldint=Prop.getPreference(PKEY_TOTAL_TIME,DEFAULT_TOTALTIME);//~1A3cI~//~1Ah0R~
    	oldint=Prop.getPreference(pkey_TOTAL_TIME,DEFAULT_TOTALTIME);//~1Ah0I~
        TotalTime=new FormTextField(this,R.id.TotalTime,Integer.toString(oldint));//~@@@@R~//~@@@2R~
//  	oldint=Prop.getPreference(PKEY_EXTRA_TIME,DEFAULT_EXTRATIME);//~@@@@I~//~@@@2M~//~1Ah0R~
    	oldint=Prop.getPreference(pkey_EXTRA_TIME,DEFAULT_EXTRATIME);//~1Ah0I~
        ExtraTime=new FormTextField(this,R.id.ExtraTime,Integer.toString(oldint));//~@@@@R~//~@@@2R~
                                                                   //~v1A0I~
//  	oldint=Prop.getPreference(PKEY_GAMEOPTIONS,GAMEOPT_MOVEFIRST);//~@@@2R~//~v1A0M~//~1A1hR~
//  	oldint=Prop.getPreference(PKEY_GAMEOPTIONS,GAMEOPT_MOVEFIRST|GAMEOPT_BIGTIMER);//~1A1hI~//~1Ah0R~
    	oldint=Prop.getPreference(pkey_GAMEOPTIONS,GAMEOPT_MOVEFIRST|GAMEOPT_BIGTIMER);//~1Ah0I~
                                                                   //~v1A0I~
        MoveFirst=new Checkbox(this,R.id.MoveFirst);                                  //~@@@@I~//~@@@2R~
        MoveFirst.setState((oldint & GAMEOPT_MOVEFIRST)!=0);       //~@@@2R~
        BigTimer=new Checkbox(this,R.id.BigTimer);                 //~1A12I~
        BigTimer.setState((oldint & GAMEOPT_BIGTIMER)!=0);         //~1A12I~
        NotifyCheck=new Checkbox(this,R.id.NotifyCheck);           //~v1A0I~
        NotifyCheck.setState((oldint & GAMEOPT_NOTIFYCHECK)!=0);   //~v1A0I~
        FoulMissMove=new Checkbox(this,R.id.FoulMissMove);         //~v1A0I~
        FoulMissMove.setState((oldint & GAMEOPT_FOUL_MISSMOVE)!=0);//~v1A0I~
        FoulLeaveCheck=new Checkbox(this,R.id.FoulLeaveCheck);     //~v1A0I~
        FoulLeaveCheck.setState((oldint & GAMEOPT_FOUL_LEAVECHECK)!=0);//~v1A0I~
        Foul2Pawn=new Checkbox(this,R.id.Foul2Pawn);                //~v1A0I~
        Foul2Pawn.setState((oldint & GAMEOPT_FOUL_2PAWN)!=0);      //~v1A0R~
        FoulUnmovableDrop=new Checkbox(this,R.id.FoulUnmovableDrop);//~v1A0I~
        FoulUnmovableDrop.setState((oldint & GAMEOPT_FOUL_UNMOVABLEDROP)!=0);//~v1A0R~
        FoulDropPawnCheckmate=new Checkbox(this,R.id.FoulDropPawnCheckmate);//~v1A0I~
        FoulDropPawnCheckmate.setState((oldint & GAMEOPT_FOUL_DROPPAWNCHECKMATE)!=0);//~v1A0R~
                                                                   //~v1A0I~
//  	Handicap=Prop.getPreference(PKEY_HANDICAP,0);              //~v1A0I~//~1Ah0R~
    	Handicap=Prop.getPreference(pkey_HANDICAP,0);              //~1Ah0I~
        String hcs=getHadicapString(Handicap);                    //~v1A0I~
		HandicapText=new FormTextField(this,R.id.HandicapView,hcs);//~v1A0I~
        if (swLocalGame)                                           //~1A49I~
            setGameDataLocal();                                    //~1A49I~
        else                                                       //~1A49I~
            setGameDataNonLocal();                                 //~1A49I~
	}                                                              //~@@@2R~
    private void setGameDataLocal()                                //~1A49I~
    {                                                              //~1A49I~
		String oldstr;                                             //~1A49I~
        View v;                                                    //~1A49I~
//  	oldstr=Prop.getPreference(PKEY_LOCAL_OPPONENT_NAME,AG.resource.getString(R.string.DefaultOpponentName));//~1A49I~//~1Ah0R~
    	oldstr=Prop.getPreference(pkey_LOCAL_OPPONENT_NAME,AG.resource.getString(R.string.DefaultOpponentName));//~1Ah0I~
        OpponentName=new FormTextField(this,R.id.OpponentName,oldstr);//~1A49I~
        v=findViewById(R.id.LocalOpponentNameLine);                //~1A49I~
        Component dmy=new Component(); //no callback(dialog has callback then cause invalidcast exception at Dialog:runOnUithread)//~1A49I~
        dmy.setVisibility(v,View.VISIBLE);                         //~1A49I~
	}                                                              //~1A49I~
    private void setGameDataNonLocal()                             //~1A49I~
    {                                                              //~1A49I~
	}                                                              //~1A49I~
	public void doAction (String o)
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.Request)))//partner game only     //~@@@@I~//~v1A0R~
		{                                                          //~@@@@R~
        	if (!getGameData())                                    //~@@@2I~
            	return;                                            //~@@@2I~
       		URunnable.setRunFuncDirect(this,null,RF_WAITINGMSG/*parmint*/); //waiting dialog on UI thread//~v1A0R~
			String c;                                              //~@@@2I~
  			c=movefirst!=0?"b":"w";                            //~@@@2I~//~v1A0R~
		    URunnable.setRunFuncSubthread(this,0/*deley*/,c/*parmobj*/,0/*parmint*/);//~@@@2I~
			setVisible(false); dispose();
		}
//		else if (Global.resourceString("Cancel").equals(o))        //~@@@@R~
        else if (o.equals(AG.resource.getString(R.string.Cancel))) //~@@@@I~
		{	setVisible(false); dispose();
		}
        else if (o.equals(AG.resource.getString(R.string.HandicapButton)))//~v1A0I~
		{                                                          //~v1A0I~
			new HandicapQuestion(PF==null?frameForLocalGame:PF,this);//~v1A0R~
		}                                                          //~v1A0I~
        else if (o.equals(AG.resource.getString(R.string.ActionHandicapChanged)))//~v1A0R~
		{                                                          //~v1A0I~
	        String hcs=getHadicapString(Handicap);                //~v1A0I~
			HandicapText.setText(hcs);                             //~v1A0R~
		}                                                          //~v1A0I~
        else if (o.equals(AG.resource.getString(R.string.ActionEditTotalTime)))//~v1A0I~
		{                                                          //~v1A0I~
			new SpinButtonDialog(PF==null?frameForLocalGame:PF,TOTALTIME_MIN,TOTALTIME_MAX,TotalTime);//~v1A0R~
		}                                                          //~v1A0I~
        else if (o.equals(AG.resource.getString(R.string.ActionEditExtraTime)))//~v1A0I~
		{                                                          //~v1A0I~
			new SpinButtonDialog(PF==null?frameForLocalGame:PF,EXTRATIME_MIN,EXTRATIME_MAX,ExtraTime);//~v1A0R~
		}                                                          //~v1A0I~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~2C30I~//~@@@@I~
		{                                                          //~2C30I~//~@@@@I~
//            new HelpDialog(null,R.string.Help_GameQuestion);           //~2C30I~//~@@@@I~//~v1A0R~
            new HelpDialog(null,R.string.HelpTitle_GameQuestion,"GameQuestion");                   //~v1A0I~//~1A00R~
		}                                                          //~2C30I~//~@@@@I~
        else if (o.equals(AG.resource.getString(R.string.ButtonReload)))//~1A24I~
		{                                                          //~1A24I~
        	swReloadGame=true;		//after dismiss ,call FileDialog//~1A24I~
			setVisible(false); dispose(); //dismiss                //~1A24I~
		}                                                          //~1A24I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))	//modal but no inputWait//~@@@@R~//~1A24I~
		{   			//callback from Dialog after currentLaypout restored//~@@@@I~//~1A24I~
        	if (swReloadGame)                                      //~1A24I~
	            reloadFileDialog();                                //~1A24R~
		}                                                          //~@@@@I~//~1A24I~
		else super.doAction(o);
	}
    //*********************************************************    //~@@@2I~
    public void runFunc(Object Pparmobj,int Pparmint)              //~@@@2I~
    {                                                              //~@@@2I~
    	if (Dump.Y) Dump.println("GameQuestion runFunc parmint="+Pparmint);//~v1A0I~
        if (Pparmint==RF_WAITINGMSG)                               //~v1A0I~
        {                                                          //~v1A0I~
        	waitingResponse();                                     //~v1A0I~
            return;                                                //~v1A0I~
        }                                                          //~v1A0I~
    	String c=(String)Pparmobj;
    	if (Dump.Y) Dump.println("GameQuestion runFunc call dorequest");//~@@@2I~//~1A12R~
  		PF.dorequest(boardsz,c,totaltime,extratime,gameoptions,Handicap);//~v1A0R~
    	if (Dump.Y) Dump.println("GameQuestion runFunc call dorequest end");//~@@@2I~//~v1A0R~
    }                                                              //~@@@2I~
    //*********************************************************    //~v1A0I~
    public static void waitingResponse()                                 //~v1A0I~//~1A24R~
    {                                                              //~v1A0I~
//        AG.progDlg=new ProgDlg(R.string.Title_GameQuestionWaitingResponse,R.string.Msg_GameQuestionWaitingResponse,true/*cancelable*/);//~v1A0I~//~1A2jR~
//        AG.progDlg.show();                                         //~v101I~//~v1A0I~//~1A2jR~
        ProgDlg.showProgDlg(true/*set to AG*/,R.string.Title_GameQuestionWaitingResponse,R.string.Msg_GameQuestionWaitingResponse,true/*cancelable*/);//~1A2jI~
    }                                                              //~v1A0I~
    //*********************************************************    //~@@@2I~
    public boolean getGameData()                                   //~@@@2I~
    {                                                              //~@@@2I~
    	    boardsz=AG.BOARDSIZE_SHOGI;                            //~v1A0I~
            String s="";                                           //~@@@2I~
			try                                                    //~@@@2I~
            {                                                      //~@@@2I~
                s=TotalTime.getText();                             //~@@@2I~
                if (s.trim().equals(""))                           //~@@@2I~
	                totaltime=0;                                   //~@@@2I~
                else                                               //~@@@2I~
                	totaltime=Integer.parseInt(s);                 //~@@@2I~
                s=ExtraTime.getText();                             //~@@@2I~
                if (s.trim().equals(""))                           //~@@@2I~
	                extratime=0;                                          //~@@@2I~
                else                                               //~@@@2I~
                	extratime=Integer.parseInt(s);                        //~@@@2I~
                movefirst=MoveFirst.getState()?GAMEOPT_MOVEFIRST:0;//~@@@2R~//~v1A0M~
                bigtimer=BigTimer.getState()?GAMEOPT_BIGTIMER:0;   //~1A12I~
                notifycheck=NotifyCheck.getState()?GAMEOPT_NOTIFYCHECK:0;//~v1A0I~
                foul_missmove=FoulMissMove.getState()?GAMEOPT_FOUL_MISSMOVE:0;//~v1A0I~
                foul_leavecheck=FoulLeaveCheck.getState()?GAMEOPT_FOUL_LEAVECHECK:0;//~v1A0I~
                foul_2pawn=Foul2Pawn.getState()?GAMEOPT_FOUL_2PAWN:0;//~@@@2R~//~v1A0R~
                foul_unmovabledrop=FoulUnmovableDrop.getState()?GAMEOPT_FOUL_UNMOVABLEDROP:0;//~v1A0I~
                foul_droppawncheckmate=FoulDropPawnCheckmate.getState()?GAMEOPT_FOUL_DROPPAWNCHECKMATE:0;//~v1A0I~
                gameoptions=movefirst+notifycheck+foul_2pawn+foul_unmovabledrop+foul_droppawncheckmate//~v1A0R~
                            +foul_missmove+foul_leavecheck+bigtimer;        //~v1A0I~//~1A12R~
//  			Prop.putPreference(PKEY_TOTAL_TIME,totaltime);     //~@@@2I~//~1Ah0R~
    			Prop.putPreference(pkey_TOTAL_TIME,totaltime);     //~1Ah0I~
//  			Prop.putPreference(PKEY_EXTRA_TIME,extratime);            //~@@@2I~//~1Ah0R~
    			Prop.putPreference(pkey_EXTRA_TIME,extratime);     //~1Ah0I~
//  			Prop.putPreference(PKEY_GAMEOPTIONS,gameoptions);  //~@@@2R~//~1Ah0R~
    			Prop.putPreference(pkey_GAMEOPTIONS,gameoptions);  //~1Ah0I~
				Prop.putPreference(PKEY_HANDICAP,Handicap);        //~v1A0I~
    	    	if (swLocalGame)                                   //~1A49I~
        	    	getGameDataLocal();                            //~1A49I~
			}                                                      //~@@@2I~
			catch (NumberFormatException ex)                       //~@@@2I~
            {                                                      //~@@@2I~
            	AView.showToastLong(R.string.ParmErr,s);           //~@@@2I~
				return false;                                      //~@@@2R~
			}                                                      //~@@@2I~
            return true;                                           //~@@@2I~
    }                                                              //~@@@2I~
    //*********************************************************    //~1A49I~
    private boolean getGameDataLocal()                             //~1A49I~
    {                                                              //~1A49I~
        String s=OpponentName.getText();                           //~1A49I~
        if (s==null || s.trim().equals(""))                        //~1A49I~
        	s=AG.resource.getString(R.string.DefaultOpponentName); //~1A49I~
//      Prop.putPreference(PKEY_LOCAL_OPPONENT_NAME,s);            //~1A49I~//~1Ah0R~
        Prop.putPreference(pkey_LOCAL_OPPONENT_NAME,s);            //~1Ah0I~
        AG.LocalOpponentName=s;                                    //~1A49I~
        return true;                                               //~1A49I~
    }                                                              //~1A49I~
//************************************************************     //~v1A0I~
    public static String getHadicapString(int Phandicap)           //~v1A0R~
	{                                                              //~v1A0I~
        String pieces="";                                          //~v1A0I~
        for (int ii=0;ii<AG.pieceNameHandicap.length;ii++)                             //~v1A0I~
        {                                                          //~v1A0I~
        	if ((Phandicap & (1<<ii))!=0)                                //~v1A0I~
            {                                                      //~v1A0I~
                if (pieces.equals(""))                             //~v1A0I~
                	pieces=AG.pieceNameHandicap[ii];               //~v1A0I~
                else                                               //~v1A0I~
                	pieces+=","+AG.pieceNameHandicap[ii];          //~v1A0R~
            }                                                      //~v1A0I~
        }                                                          //~v1A0I~
        return pieces;                                             //~v1A0I~
	}                                                              //~v1A0I~
//************************************************************     //~1A24I~
//*after Dismiss                                                   //~1A24I~
//************************************************************     //~1A24I~
    protected void reloadFileDialog()                              //~1A24R~
	{                                                              //~1A24I~
    	if (Dump.Y) Dump.println("GameQuestion reloadGame");       //~1A24I~
    	FileDialog fd=new FileDialog((FileDialogI)parentFrame,this,AG.resource.getString(R.string.Title_LoadFile),FileDialog.LOAD);//~1A24R~
        fd.setFilterString(FileDialog.GAMES_EXT);                  //~1A24I~
        fd.show();  //callback loadFileNotes                       //~1A24I~
	}                                                              //~1A24I~
////************************************************************   //~1A24R~
//    public void fileDialogLoaded(Notes Pnotes)                   //~1A24R~
//    {                                                            //~1A24R~
//        reloadNotes=Pnotes;                                      //~1A24R~
//        gameoptions=Pnotes.gameoptions;                          //~1A24R~
//    }                                                            //~1A24R~
////************************************************************   //~1A24R~
//    public void fileDialogSaved(){}                              //~1A24R~
}

