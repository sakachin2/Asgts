//*CID://+1Ah0R~:                                   update#=  246; //~1Ah1R~//~1Ah0R~
//****************************************************************************//~@@@1I~
//1Ah1*2016/10/25 TotalTime may be 0 with extratime!=0             //~1Ah1I~
//1A4q 2014/12/05 (Bug)Cancel save dose ot change to Close button enabled//~1A4qI~
//1A4i 2014/12/03 drop Black/White sign from title(white id:triangle contents differs by theme holo or holo.light)//~1A4iI~
//1A32 2013/04/17 chaneg to open FreeBoardQuestion at start button on FreeGoFrame//~1A32I~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A2c 2013/03/27 display previous move description for reloaded game//~1A2cI~
//1A27 2013/03/25 File button --> Suspend button on Local/Remote board//~1A27I~
//1A26 2013/03/25 save folder fix option(no dislog popup for save game)//~1A26I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A23 2013/03/23 File Dialog on PartnerGoFrame                    //~1A23I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A18 2013/03/13 FreeBoard:show Black timer only                  //~1A18I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1034 2013/03/07 time sign color did'nt change for remote game    //~1034I~
//1A0c 2013/03/05 mach info in title                               //~1A0cI~
//1A07 2013/03/02 distinguish color for extratime                  //~1A07I~
//1A06 2013/03/02 remaining ExtraTime was accounted.               //~1A06I~
//1A03 2013/03/01 2 timeout msg                                    //~1A03I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//*split from PartnerGoFrame for common to LocalGoFrame  *         //~@@@2I~
//****************************************************************************//~@@@1I~
package jagoclient.board;

import android.view.View;

import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.R;
import com.Asgts.URunnable;                                        //~@@@2R~
import com.Asgts.URunnableI;                                       //~@@@2R~
import com.Asgts.Utils;
import com.Asgts.awt.FileDialog;
import com.Asgts.awt.FileDialogI;
import com.Asgts.awt.Frame;                                        //~@@@2R~
import com.Asgts.gtp.GtpFrame;

import jagoclient.Dump;
import jagoclient.LocalFrame;
import jagoclient.board.GoTimer;                                   //~@@@2I~
import jagoclient.dialogs.Message;
import jagoclient.partner.GameQuestion;
import static jagoclient.board.Field.*;                            //~v101I~//~1A23I~


/**
A subclass of GoFrame, which has a different menu layout.
Moreover, it contains a method to add a comment from an
external source (a distributor).
*/

public class TimedGoFrame extends ConnectedGoFrame                 //~@@@2R~
	implements TimedBoard, URunnableI                              //~@@@2R~
    ,FileDialogI                                                     //~1A24I~
{//~@@@2I~
 	protected String BlackName,WhiteName;                          //~@@@2R~
	protected int BlackTime,WhiteTime,BlackMoves,WhiteMoves;       //~@@@2M~
	protected int BlackRun,WhiteRun;                               //~@@@2R~
	protected int TotalTime,ExtraTime,ExtraMoves;                  //~@@@2R~
	protected GoTimer Timer;                                       //~@@@2M~
	protected long CurrentTime;                                    //~@@@2R~
	protected long CurrentTimeBlack,CurrentTimeWhite;              //~1Ah0I~
//    public boolean swSuspendTimer;                                 //~1A10R~//~1A18R~
	protected boolean Started,Ended;                               //~@@@2I~
	protected boolean /*TimerInTitle,*/BigTimer;                       //~@@@2I~//~1A0cR~
	private	int cc;	//current color                            //~@@@2I~
//	private	int prevColor;	//previous color                       //~@@@2I~
//    private LocalFrame LF;                                         //~@@@2R~
//    private PartnerFrame PF;                                       //~@@@2R~
    protected boolean extraB,extraW;	//timeover of TotalTime        //~@@@2I~//~1A06R~//~1A22R~
    private String frameTitle;                                     //~1A0cI~
    private int colorGtpColorSwitch=0;                             //~1Ah0R~
//**************************************************************** //~@@@1I~
	public TimedGoFrame (Frame pf,int Presid,String s,             //~@@@2R~
    	int col, int si,int tt,int et,int Pgameoptions,int Phandicap)//~@@@2I~//~v1A0R~
	{                                                              //~@@@2I~
    	super(Presid,s,si,Pgameoptions,col,Phandicap);//~@@@@R~//~@@@2R~//~v1A0R~
        if (Dump.Y) Dump.println("TimedGoFrame="+s);      //~@@@1I~//~@@@2R~//~1A0cR~//~1A4qR~
        if (Dump.Y) Dump.println("TimedGoFrame color="+col+",totaltime="+tt+",extratime="+et+",gameoption="+Integer.toHexString(Pgameoptions)+",handicap="+Integer.toHexString(Phandicap));//~1A4qI~
        frameTitle=s;                                              //~1A0cI~
 //     if (!(pf instanceof GtpFrame))                               //~1Ah1I~
        if (pf instanceof LocalFrame)                            //~@@@2M~
        {                                                          //~@@@2I~2
//	        LF=(LocalFrame)pf;                                     //~@@@2R~
        	swLocalGame=true;                                      //~@@@2I~
        }                                                          //~@@@2I~
        else                                                       //~1Ah0I~
        if (pf instanceof GtpFrame)                                //~1Ah0I~
        {                                                          //~@@@2I~2//~1Ah0I~
        	swGtpGame=true;                                        //~1Ah0I~
        }                                                          //~1Ah0I~
//        else                                                       //~@@@2I~
//	        PF=(PartnerFrame)pf;                                   //~@@@2R~
        TotalTime=tt; ExtraTime=et; ExtraMoves=AG.EXTRA_MOVE;      //~@@@2I~
//      BlackTime=TotalTime;                                       //~@@@2M~//~1A32R~
//      WhiteTime=TotalTime;                                       //~@@@2M~//~1A32R~
        BlackRun=0; WhiteRun=0;                                    //~@@@2I~
//      TimerInTitle=(AG.Options & AG.OPTIONS_TIMER_IN_TITLE)!=0;     //~@@@2I~//~1A0cR~
//      BigTimer=(AG.Options & AG.OPTIONS_BIG_TIMER)!=0;              //~@@@2I~//~1034R~
//      BigTimer=TotalTime!=0||(Pgameoptions & GameQuestion.GAMEOPT_BIGTIMER)!=0;//~1034I~//~1A32R~
		resetTimerOption();                                        //~1A32I~
//        prevColor=col;                                             //~@@@2I~
	}
//************************************************                 //~1A32I~
//*from FGF after FreeBoadQuestion                                 //~1A32I~
//************************************************                 //~1A32I~
	protected void resetTimerOption()                              //~1A32I~
    {                                                              //~1A32I~
        BlackTime=TotalTime;                                       //~1A32I~
        WhiteTime=TotalTime;                                       //~1A32I~
//      BigTimer=TotalTime!=0||(GameOptions & GameQuestion.GAMEOPT_BIGTIMER)!=0;//~1A32I~//~1Ah1R~
        BigTimer=(TotalTime!=0 || ExtraTime!=0)||(GameOptions & GameQuestion.GAMEOPT_BIGTIMER)!=0;//~1Ah1I~
        if ((GameOptions & GameQuestion.GAMEOPT_NOBIGTIMER)!=0)     //bonanza human time limit is max and no bigtimer option//~1Ah0I~
	        BigTimer=false;                                        //~1Ah0I~
        if (Dump.Y) Dump.println("TimedGoFrame:resetTimerOption BigTimer="+BigTimer+",TotalTime="+TotalTime+",GameOptions="+Integer.toHexString(GameOptions));//~1A4qI~
    }                                                              //~1A32I~
//************************************************                 //~@@@2I~
	protected void start ()                                        //~@@@2I~
	{	Started=true; Ended=false;                                 //~@@@2I~
		CurrentTime=System.currentTimeMillis();                    //~@@@2I~
		BlackRun=0; WhiteRun=0;                                    //~@@@2I~
		BlackMoves=-1; WhiteMoves=-1;                              //~@@@2I~
//    if (BigTimer && TotalTime!=0)                                //~v1A0R~//~1034R~
      if (BigTimer)                                                //~1034I~
    	Timer=new GoTimer(this,1000);        //same as IgsGoFrame  //~@@@2I~
        URunnable.setRunFunc(this,0/*deley*/,null/*parmobj*/,0/*parmint*/);//~@@@2R~
	}                                                              //~@@@2I~
//************************************************                 //~@@@2I~
	public void runFunc(Object Pparmobj,int Pparmint)              //~@@@2R~
    {                                                              //~@@@2I~
    	drawInitialCaptured();                                     //~@@@2I~
    }                                                              //~@@@2I~
//************************************************                 //~@@@2I~
	public void settimes (int bt, int bm, int wt, int wm)          //~@@@2I~
	{	BlackTime=bt; BlackRun=0;                                  //~@@@2I~
		WhiteTime=wt; WhiteRun=0;                                  //~@@@2I~
		WhiteMoves=wm; BlackMoves=bm;                              //~@@@2I~
		CurrentTime=System.currentTimeMillis();                    //~@@@2I~
        if (Dump.Y)Dump.println("TGF:settimes by receive bt="+bt+",wt="+wt+",bm="+bm+",wm="+wm);//~@@@2I~//~1A03R~//~1A4qR~
		settitle();                                                //~@@@2I~
	}                                                              //~@@@2I~
//************************************************                 //~@@@2I~
	public void settimesLocalColorSwitch(int Pcolor/*last moved color*/)//~@@@2R~
	{                         
		long now=System.currentTimeMillis();//elapsed time after swithed//~@@@2I~
		if (Pcolor>0)                                              //~@@@2I~
			BlackMoves--;	//for extratime period max move is 1   //~@@@2I~
        else                                                       //~@@@2I~
			WhiteMoves--;                                          //~@@@2I~
		alarm();	//evaluate timeover                             //~@@@2I~
		if (Pcolor>0)         //black moved,next is white          //~@@@2R~
        {                                                          //~@@@2I~
            if (extraB)  //in extratime period                     //~@@@2I~
            {                                                      //~@@@2I~
                BlackTime=ExtraTime;                               //~@@@2I~
                BlackRun=0;	//for settitle                         //~@@@2I~
                BlackMoves=ExtraMoves;                             //~@@@2I~
            }                                                      //~@@@2I~
            CurrentTime=now-WhiteRun*1000;//elapsed time after swithed//~@@@2R~
        }                                                          //~@@@2I~
        else	//next is black                                    //~@@@2R~
        {                                                          //~@@@2I~
        	if (extraW)                                            //~@@@2R~
            {                                                      //~@@@2I~
                WhiteTime=ExtraTime;                               //~@@@2R~
                WhiteRun=0;	//for settitle                         //~@@@2R~
                WhiteMoves=ExtraMoves;                             //~@@@2R~
            }                                                      //~@@@2I~
			CurrentTime=now-BlackRun*1000;//elapsed time after swithed//~@@@2R~
        }                                                          //~@@@2I~
		lastbeep=0;		//for black and white                      //~1A07I~
		settitle();	//display full extratime                       //~@@@2I~
        if (Dump.Y) Dump.println("ColorSwitch old="+Pcolor+",extraW="+extraW+",extraB="+extraB+",blackRun="+BlackRun+",whiteRun="+WhiteRun);//~@@@2I~
	}                                                              //~@@@2I~
//************************************************                 //~1Ah0I~
//* after set black()/white()                                      //~1Ah0I~
//************************************************                 //~1Ah0I~
	public void settimesGtpColorSwitch(int Pcolor)                 //~1Ah0I~
	{                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("TGF:settimesGtpColorSwitch color="+Pcolor);//~1Ah0I~
		alarm();	//evaluate timeover                            //~1Ah0I~
		settitle();	//display full extratime                       //~1Ah0I~
	}//settimesGtpColorSwitchCurrentTime                           //~1Ah0I~
//************************************************                 //~1Ah0I~
	private void settimesGtpColorSwitchCurrentTime(int Pcolor,long Pnow)//~1Ah0R~
	{                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("TGF:settimesGtpColorSwitchCurrentTime entery color="+Pcolor+",oldcolor="+colorGtpColorSwitch+",now/1000="+Pnow/1000);//~1Ah0R~
        if (Pcolor==colorGtpColorSwitch)	//not color switch     //~1Ah0I~
        	return;                                                //~1Ah0I~
        colorGtpColorSwitch=Pcolor;                                //~1Ah0I~
		if (Pcolor<0)         //switched to White                  //~1Ah0R~
        {                                                          //~1Ah0I~
			BlackMoves--;	//for extratime period max move is 1   //~1Ah0I~
            if (extraB)  //in extratime period                     //~1Ah0I~
            {                                                      //~1Ah0I~
                BlackTime=ExtraTime;                               //~1Ah0I~
                BlackRun=0;	//for settitle                         //~1Ah0I~
                BlackMoves=ExtraMoves;                             //~1Ah0I~
            }                                                      //~1Ah0I~
            CurrentTime=Pnow-WhiteRun*1000;//continnue BlackTime   //~1Ah0R~
        }                                                          //~1Ah0I~
        else	//switch to Black                                  //~1Ah0R~
        {                                                          //~1Ah0I~
			WhiteMoves--;                                          //~1Ah0I~
        	if (extraW)                                            //~1Ah0I~
            {                                                      //~1Ah0I~
                WhiteTime=ExtraTime;                               //~1Ah0I~
                WhiteRun=0;	//for settitle                         //~1Ah0I~
                WhiteMoves=ExtraMoves;                             //~1Ah0I~
            }                                                      //~1Ah0I~
			CurrentTime=Pnow-BlackRun*1000;//Continue White Time   //~1Ah0R~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("TGF:settimesGtpColorSwitchCurrentTime return color="+Pcolor+",CurrentTime/1000="+CurrentTime/1000);//~1Ah0I~
	}//settimesGtpColorSwitchCurrentTime                           //~1Ah0I~
//************************************************************************//~@@@@I~//~@@@2M~
//  public void alarm ()                                           //~@@@@I~//~@@@2M~//~1Ah0R~
    public synchronized void alarm ()     //GoTimer and BoardSync thread//~1Ah0I~
	{                                                              //~1Ah0R~
        if (Dump.Y) Dump.println("alarm entry CurrentTime/1000="+CurrentTime/1000);//~1Ah0I~
		long now=System.currentTimeMillis();                       //~1Ah0I~
        int mc; //my color                                         //~1A03I~
        if (swLocalGame)	                                       //~@@@2I~
        {                                                          //~1A03I~
			cc=B.currentColor();                                          //~@@@@I~//~@@@2I~
            mc=cc;                                                 //~1A03I~
        }                                                          //~1A03I~
        else		//PartnerGoFrame                               //~1Ah0I~
        {                                                          //~1A03I~
        	cc=B.maincolor();                                      //~@@@2R~//~v1A0R~//~1A03R~
            mc=Col; //timeout msg for my color only                //~1A03I~
        }                                                          //~1A03I~
        if (swGtpGame)                                             //~1Ah0R~
        {                                                          //~1Ah0M~
			settimesGtpColorSwitchCurrentTime(cc,now);	//update CurrentTime for Opponent//~1Ah0R~
        }                                                          //~1Ah0M~
//      if (B.maincolor()>0) BlackRun=(int)((now-CurrentTime)/1000);//~@@@@R~//~@@@2M~
        if (cc>0) BlackRun=(int)((now-CurrentTime)/1000);          //~@@@@I~//~@@@2M~
		else WhiteRun=(int)((now-CurrentTime)/1000);               //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("alarm swGtpGame="+swGtpGame+",swLocalGame="+swLocalGame+",color="+cc+",WhiteRun="+WhiteRun+",BlackRun="+BlackRun+",WhiteTime="+WhiteTime+",BlackTime="+BlackTime);//~@@@2R~//~1Ah0R~
//    if (TotalTime!=0)	//timeover was set                         //~@@@@R~//~@@@2M~//~1Ah1R~
      if (TotalTime!=0 || ExtraTime!=0)	//timeover was set         //~1Ah1I~
      {                                                            //~@@@@I~//~@@@2M~
//  	if (Col>0 && BlackTime-BlackRun<0)                         //~@@@@I~//~@@@2M~
//  	if (cc>0 && BlackTime-BlackRun<0)                          //~@@@@I~//~@@@2M~//~1A03R~
    	if (mc>0 && BlackTime-BlackRun<0)                          //~1A03I~
		{	if (BlackMoves<0)                                      //~@@@@I~//~@@@2M~
            {   BlackMoves=ExtraMoves;                             //~@@@@I~//~@@@2M~
                BlackTime=ExtraTime; BlackRun=0;                   //~@@@@I~//~@@@2M~
                CurrentTime=now;                                   //~@@@@I~//~@@@2M~
                extraB=true;                                       //~@@@2I~
                beep();	//entered extratime                        //~1A07I~
            }                                                      //~@@@@I~//~@@@2M~
            else if (BlackMoves>0)                                 //~@@@@R~//~@@@2M~
//          {   new Message(this,Global.resourceString("Black_looses_by_time_"));//~@@@@I~//~@@@2R~
            {                                                      //~@@@2I~
            	super.gameoverMessage(-1/*white is winner*/,3);    //~@@@2I~
//                Timer.stopit();                                    //~@@@@I~//~@@@2M~//~1A03R~
			}                                                      //~@@@@I~//~@@@2M~
            else                                                   //~@@@@I~//~@@@2M~
            {   BlackMoves=ExtraMoves;                             //~@@@@I~//~@@@2M~
                BlackTime=ExtraTime; BlackRun=0;                   //~@@@@I~//~@@@2M~
                CurrentTime=now;                                   //~@@@@I~//~@@@2M~
            }                                                      //~@@@@I~//~@@@2M~
		}                                                          //~@@@@I~//~@@@2M~
//  	else if (Col<0 && WhiteTime-WhiteRun<0)                    //~@@@@I~//~@@@2M~
//  	else if (cc<0 && WhiteTime-WhiteRun<0)                     //~@@@@I~//~@@@2M~//~1A03R~
    	else if (mc<0 && WhiteTime-WhiteRun<0)                     //~1A03I~
		{	if (WhiteMoves<0)                                      //~@@@@I~//~@@@2M~
            {   WhiteMoves=ExtraMoves;                             //~@@@@I~//~@@@2M~
                WhiteTime=ExtraTime; WhiteRun=0;                   //~@@@@I~//~@@@2M~
                CurrentTime=now;                                   //~@@@@I~//~@@@2M~
                extraW=true;                                       //~@@@2I~
                beep();	//entered extratime                        //~1A07I~
            }                                                      //~@@@@I~//~@@@2M~
            else if (WhiteMoves>0)                                 //~@@@@R~//~@@@2M~
//    		{	new Message(this,Global.resourceString("White_looses_by_time_"));//~@@@@I~//~@@@2R~
    		{                                                      //~@@@2R~
            	super.gameoverMessage(1/*black is winner*/,3);     //~@@@2I~
//                Timer.stopit();                                    //~@@@@I~//~@@@2M~//~1A03R~
			}                                                      //~@@@@I~//~@@@2M~
            else                                                   //~@@@@I~//~@@@2M~
            {   WhiteMoves=ExtraMoves;                             //~@@@@I~//~@@@2M~
                WhiteTime=ExtraTime; WhiteRun=0;                   //~@@@@I~//~@@@2M~
                CurrentTime=now;                                   //~@@@@I~//~@@@2M~
            }                                                      //~@@@@I~//~@@@2M~
		}                                                          //~@@@@I~//~@@@2M~
    	else if (mc<0 && BlackTime-BlackRun<0)  //opponent time    //~1A03I~
		{	if (BlackMoves<0)                                      //~1A03I~
            {   BlackMoves=ExtraMoves;                             //~1A03I~
                BlackTime=ExtraTime; BlackRun=0;                   //~1A03I~
                CurrentTime=now;                                   //~1A03I~
//                extraB=true;                                     //~1A03I~
            }                                                      //~1A03I~
            else if (BlackMoves>0)                                 //~1A03I~
            {                                                      //~1A03I~
//                super.gameoverMessage(-1/*white is winner*/,3);  //~1A03I~
			}                                                      //~1A03I~
            else                                                   //~1A03I~
            {   BlackMoves=ExtraMoves;                             //~1A03I~
                BlackTime=ExtraTime; BlackRun=0;                   //~1A03I~
                CurrentTime=now;                                   //~1A03I~
            }                                                      //~1A03I~
		}                                                          //~1A03I~
    	else if (mc>0 && WhiteTime-WhiteRun<0)                     //~1A03I~
		{	if (WhiteMoves<0)                                      //~1A03I~
            {   WhiteMoves=ExtraMoves;                             //~1A03I~
                WhiteTime=ExtraTime; WhiteRun=0;                   //~1A03I~
                CurrentTime=now;                                   //~1A03I~
//                extraW=true;                                     //~1A03I~
            }                                                      //~1A03I~
            else if (WhiteMoves>0)                                 //~1A03I~
    		{                                                      //~1A03I~
//                super.gameoverMessage(1/*black is winner*/,3);   //~1A03I~
			}                                                      //~1A03I~
            else                                                   //~1A03I~
            {   WhiteMoves=ExtraMoves;                             //~1A03I~
                WhiteTime=ExtraTime; WhiteRun=0;                   //~1A03I~
                CurrentTime=now;                                   //~1A03I~
            }                                                      //~1A03I~
		}                                                          //~1A03I~
      }//TotalTime!=0                                              //~@@@@I~//~@@@2R~
//      if (Dump.Y) Dump.println("alarm color=extraW="+extraW+",extraB="+extraB+",blackTime="+BlackTime+",whiteTime="+WhiteTime);//~@@@2R~
      if (Dump.Y) Dump.println("alarm exit CurrentTime/1000="+CurrentTime/1000);//~1Ah0I~
		settitle();                                                //~@@@@I~//~@@@2M~
	}                                                              //~@@@@I~//~@@@2M~
//************************************************                 //~@@@2I~
	int lastbeep=0;                                                //~@@@2I~
	public void beep (int s)                                       //~@@@2I~
//  {	if (s<0 || !Global.getParameter("warning",true)) return;   //~@@@2R~
    {                                                              //~@@@2I~
    	if (s<0 || (AG.Options & AG.OPTIONS_TIMER_WARNING)==0) return;//~@@@2I~
        if (s==60)                                                 //~1A07I~
        {                                                          //~1A07I~
			getToolkit().beep();                                   //~1A07I~
        }                                                          //~1A07I~
		else if (s<31 && s!=lastbeep)                              //~@@@2I~
		{	if (s%10==0)                                           //~@@@2I~
			{	getToolkit().beep();                               //~@@@2I~
				lastbeep=s;                                        //~@@@2I~
			}                                                      //~@@@2I~
		}                                                          //~@@@2I~
	}                                                              //~@@@2I~
//************************************************                 //~1A07I~
	public void beep ()                                            //~1A07I~
    {                                                              //~1A07I~
    	if ((AG.Options & AG.OPTIONS_TIMER_WARNING)==0) return;    //~1A07I~
		getToolkit().beep();                                       //~1A07I~
	}                                                              //~1A07I~
//************************************************                 //~1A07I~
	public void addtime (int s)                                    //~@@@2I~
//  {   if (Col>0) BlackTime+=s;                                   //~@@@2R~
    {                                                              //~@@@2I~
        if (swLocalGame)                                           //~@@@2I~
			cc=B.currentColor();                                   //~@@@2I~
        else		//PartnerGoFrame                               //~@@@2I~
        	cc=Col;                                                //~@@@2I~
        if (cc>0) BlackTime+=s;                                    //~@@@2I~
		else WhiteTime+=s;                                         //~@@@2I~
		settitle();                                                //~@@@2I~
	}                                                              //~@@@2I~
//************************************************                 //~@@@2M~
	String OldS="";                                                //~@@@2M~
	protected void settitle ()                                     //~@@@2M~
	{	String S;                                                  //~@@@2M~
      	if (swLocalGame)                                           //~@@@2M~
			cc=B.currentColor();                                   //~@@@2M~
        else                                                       //~@@@2M~
//      	cc=Col;                                                //~@@@2M~//~1034R~
			cc=B.maincolor();                                      //~1034I~
        if (Dump.Y) Dump.println("settitle color="+cc);            //~1Ah0I~
//        if (BigTimer)                                            //~@@@2R~
//            S=WhiteName+" "+formmoves(WhiteMoves)+" - "+         //~@@@2R~
//            BlackName+" "+formmoves(BlackMoves);                 //~@@@2R~
//        else                                                     //~@@@2R~
//            S=WhiteName+" "+formtime(WhiteTime-WhiteRun)+" "+formmoves(WhiteMoves)+" - "+//~@@@2R~
//            BlackName+" "+formtime(BlackTime-BlackRun)+" "+formmoves(BlackMoves);//~@@@2R~
//      S=" "+AG.WhiteSign+": "+WhiteName+" - "+AG.BlackSign+": "+BlackName;//~@@@2I~//~1A4iR~
        S=WhiteName+"-"+BlackName;                                 //~1A4iR~
        MatchTitle=S;                                              //~@@@2R~
		if (!S.equals(OldS))                                       //~@@@2M~
//  	{	if (!TimerInTitle) TL.setText(S);                      //~@@@2M~
		{                                                          //~@@@2M~
//  		if ((AG.Options & AG.OPTIONS_TIMER_IN_TITLE)==0) TL.setText(S);//~@@@2M~//~1A0cR~
//  		else setTitle(S);                                      //~@@@2M~//~1A0cR~
    		setTitle(frameTitle+": "+S);                           //~1A0cR~
			OldS=S;                                                //~@@@2M~
		}                                                          //~@@@2M~
		if (BigTimer)                                              //~@@@2M~
//  	{	BL.setTime(WhiteTime-WhiteRun,BlackTime-BlackRun,WhiteMoves,BlackMoves,Col);//~@@@2M~
    	{                                                          //~@@@2M~
//  		BL.setTime(WhiteTime-WhiteRun,BlackTime-BlackRun,WhiteMoves,BlackMoves,cc);//~@@@2M~//~1A07R~
    		BL.setTime(WhiteTime-WhiteRun,BlackTime-BlackRun,WhiteMoves,BlackMoves,cc,extraB,extraW);//~1A07I~
			BL.repaint();                                          //~@@@2M~
		}                                                          //~@@@2M~
      if (swLocalGame)                                             //~@@@2M~
      {                                                            //~@@@2M~
		if (cc>0) beep(BlackTime-BlackRun);                        //~@@@2M~
		if (cc<0) beep(WhiteTime-WhiteRun);                        //~@@@2M~
      }                                                            //~@@@2M~
      else                                                         //~@@@2M~
      {                                                            //~@@@2M~
//  	if (Col>0 && B.maincolor()>0) beep(BlackTime-BlackRun);    //~@@@2M~//~1034R~
//  	if (Col<0 && B.maincolor()<0) beep(WhiteTime-WhiteRun);    //~@@@2M~//~1034R~
    	if (Col>0 && cc>0) beep(BlackTime-BlackRun);               //~1034I~
    	if (Col<0 && cc<0) beep(WhiteTime-WhiteRun);               //~1034I~
      }                                                            //~@@@2M~
	}                                                              //~@@@2M~
    //***********************************************              //~1Ah0I~
	protected void settitleNoTimer ()                              //~1Ah0I~
	{	String S;                                                  //~1Ah0I~
        S=WhiteName+"-"+BlackName;                                 //~1Ah0I~
        MatchTitle=S;                                              //~1Ah0I~
    	setTitle(frameTitle+": "+S);                               //~1Ah0R~
	}                                                              //~1Ah0I~
                                                                   //~@@@2M~
	char form[]=new char[32];                                      //~@@@2M~
                                                                   //~@@@2M~
//******************************************************************//~@@@2I~
	String formmoves (int m)                                       //~@@@2M~
	{	if (m<0) return "";                                        //~@@@2M~
		form[0]='(';                                               //~@@@2M~
		int n=OutputFormatter.formint(form,1,m);                   //~@@@2M~
		form[n++]=')';                                             //~@@@2M~
		return new String(form,0,n);                               //~@@@2M~
	}                                                              //~@@@2M~
                                                                   //~@@@2M~
//******************************************************************//~@@@2I~
	String formtime (int sec)                                      //~@@@2M~
	{	int n=OutputFormatter.formtime(form,sec);                  //~@@@2M~
		return new String(form,0,n);                               //~@@@2M~
	}                                                              //~@@@2M~
//******************************************************************//~@@@2I~
	public void addothertime (int s)                               //~@@@2I~
//  {	if (Col>0) WhiteTime+=s;                                   //~@@@2R~
    {                                                              //~@@@2I~
        if (swLocalGame)                                           //~@@@2I~
			cc=B.currentColor();                                   //~@@@2I~
        else		//PartnerGoFrame                               //~@@@2I~
        	cc=Col;                                                //~@@@2I~
    	if (cc>0) WhiteTime+=s;                                    //~@@@2I~
		else BlackTime+=s;                                         //~@@@2I~
		settitle();                                                //~@@@2I~
	}                                                              //~@@@2I~
//******************************************************************//~@@@2I~
	public void doscore (int Pcolor/*winner*/)                            //~@@@2I~
	{                                                              //~@@@2I~
		B.score();                                                 //~@@@2I~
        if (Timer!=null)                                           //~@@@2I~
			Timer.stopit();                                        //~@@@2R~
		Ended=true;                                                //~@@@2I~
        super.gameoverMessage(Pcolor,4);                           //~@@@2I~
	}                                                              //~@@@2I~
//********************************************************         //~@@@2M~
	@Override	//CGF,ovrriden by PGF                                              //~@@@2I~//~v1A0R~
	protected void gameovered()                                    //~@@@2M~//~v1A0R~
	{                                                              //~@@@2M~
    	if (Dump.Y) Dump.println("TGF gameovered");                //~v1A0I~
    	B.score();                                                 //~@@@2M~
        stopBoard(false);	//false:stop thread at close           //~@@@2R~
	}                                                              //~@@@2M~
    public boolean stopBoard(Boolean Pstopthread)     //called from also AMain at destroy//~@@@2R~
	{                                                              //~@@@2M~
    	boolean rc=false;                                          //~@@@2I~
//      if ((((ConnectedBoard)B).gameoverReason & ConnectedBoard.GOR_TIMEOUT)==0)//~1A03R~
		if (Timer!=null && Timer.isAlive())                        //~@@@2R~
        {                                                          //~@@@2I~
			 Timer.stopit();     //stop BigTimer                   //~@@@2I~
             rc=true;	//need 1 sec wait                          //~@@@2I~
        }                                                          //~@@@2I~
        if (B!=null)	                                           //~@@@2M~
        {                                                          //~@@@2I~
        	if (Pstopthread)                                       //~@@@2I~
	        	B.stopThread();   	//from AMain when destroy      //~@@@2I~
            else                                                   //~@@@2I~
	        	B.inactivateCanvas();                              //~@@@2R~
        }                                                          //~@@@2I~
        return rc;//~@@@2M~
	}                                                              //~@@@2M~
////******************************************************************//~@@@2R~
////*PartnetrGoFrame will override to send to partner              //~@@@2R~
////******************************************************************//~@@@2R~
//    protected boolean moveset(String color,int i,int j,int bt,int bm,int wt,int wm){return true;}//~@@@2R~
////******************************************************************//~1A03R~
    public void gameoverNotified(int Pcolor/*winner*/,int Preason)           //~v1A0I~//~1A03R~
    {                                                              //~v1A0I~//~1A03R~
    //**********************                                       //~v1A0I~//~1A03R~
        if (Dump.Y) Dump.println("TimedGoFrame gameoverNotified color="+Pcolor+",reason=x"+Integer.toHexString(Preason));//~1A03R~
        if ((Preason & ConnectedBoard.GOR_TIMEOUT)!=0)        //~v1A0R~//~1A03R~
        {                                                          //~1A03R~
            if (Timer!=null && Timer.isAlive())                    //~1A03R~
                 Timer.stopit();     //stop BigTimer               //~1A03R~
        }                                                          //~1A03R~
        super.gameoverNotified(Pcolor,Preason);                    //~1A03R~
    }                                                              //~v1A0I~//~1A03R~
//******************************************************************//~1A06I~
    public boolean resetExtraTime(int Pcolor/*your color*/)        //~1A06I~
    {                                                              //~1A06I~
    	boolean rc;                                                //~1A06I~
    //**********************                                       //~1A06I~
        if (Dump.Y) Dump.println("TimedGoFrame resetExtraTime color="+Pcolor);//~1A06I~
		long now=System.currentTimeMillis();//elapsed time after swithed//~1A06I~
        if (Pcolor>0)	//you are black                            //~1A06I~
        {                                                          //~1A06I~
        	rc=extraB;                                             //~1A06I~
            if (rc)                                                //~1A06I~
            {                                                      //~1A06I~
                BlackMoves=ExtraMoves;                             //~1A06I~
                BlackTime=ExtraTime; BlackRun=0;                   //~1A06I~
                CurrentTime=now;                                   //~1A06I~
            }                                                      //~1A06I~
        }                                                          //~1A06I~
        else                                                       //~1A06I~
        {                                                          //~1A06I~
        	rc=extraW;                                             //~1A06I~
            if (rc)                                                //~1A06I~
            {                                                      //~1A06I~
                WhiteMoves=ExtraMoves;                             //~1A06I~
                WhiteTime=ExtraTime; WhiteRun=0;                   //~1A06I~
                CurrentTime=now;                                   //~1A06I~
            }                                                      //~1A06I~
        }                                                          //~1A06I~
        if (Dump.Y) Dump.println("TimedGoFrame resetExtraTime rc="+rc+",bt="+BlackTime+",wt="+WhiteTime);//~1A06I~
        return rc;                                                 //~1A06I~
    }                                                              //~1A06I~
	public void doAction (String o)                                //~1A23I~
    {                                                              //~1A23I~
//        if (o.equals(AG.resource.getString(R.string.File)))        //~1A23I~//~1A27R~
//        {                                                          //~1A23M~//~1A27R~
//            fileMenu();                                            //~1A23M~//~1A27R~
//        }                                                          //~1A23M~//~1A27R~
        if (o.equals(AG.resource.getString(R.string.SuspendGame))) //~1A27I~
        {                                                          //~1A27I~
            suspendGame();                                         //~1A27I~
        }                                                          //~1A27I~
        else                                                       //~1A27I~
        if (o.equals(AG.resource.getString(R.string.ButtonSaveGame)))//~1A27I~
        {                                                          //~1A27I~
            saveGameover();                                        //~1A27I~
        }                                                          //~1A27I~
//        else if (o.equals(AG.resource.getString(R.string.ReloadFile)))//from Amenu//~1A22R~//~1A23M~//~1A24R~
//        {                                                        //~1A22R~//~1A23M~//~1A24R~
//            loadFile();                                          //~1A22R~//~1A23M~//~1A24R~
//        }                                                        //~1A22R~//~1A23M~//~1A24R~
//        else if (o.equals(AG.resource.getString(R.string.ActionFileLoad)))//from FileDialog at dismiss(Open)//~1A23M~//~1A24R~
//        {                                                          //~1A23M~//~1A24R~
//            loadFileNotes();                                       //~1A23M~//~1A24R~
//        }                                                          //~1A23M~//~1A24R~
//        if (o.equals(AG.resource.getString(R.string.ActionFileSaved)))//from FileDialog at dismiss(Open)//~1A23I~//~1A24R~
//        {                                                          //~1A23I~//~1A24R~
//            fileSaved();                                           //~1A23I~//~1A24R~
//        }                                                          //~1A23I~//~1A24R~
		else                                                       //~1A23I~
			super.doAction(o);                                     //~1A23I~
    }                                                              //~1A23I~
//***************************************************************************//~1A23M~
//    public void fileMenu()                                         //~1A22I~//~1A23M~//~1A27R~
//    {                                                              //~1A22I~//~1A23M~//~1A27R~
//        if (Dump.Y) Dump.println("LGF File");                      //~1A22I~//~1A23M~//~1A27R~
//        AG.aMenu.fileMenuLGF(this);                                //~1A22I~//~1A23M~//~1A27R~
//    }                                                              //~1A22I~//~1A23M~//~1A27R~
    //***************************                                  //~1A22I~//~1A23M~
//    private void loadFile()                                        //~1A22I~//~1A23M~//~1A24R~
//    {                                                              //~1A22I~//~1A23M~//~1A24R~
//        if (Dump.Y) Dump.println("LGF loadFile");                  //~1A22I~//~1A23M~//~1A24R~
//        loadNotes=null;                                            //~1A22I~//~1A23M~//~1A24R~
//        FileDialog fd=new FileDialog(this,AG.resource.getString(R.string.Title_LoadFile),FileDialog.LOAD);//~1A22I~//~1A23M~//~1A24R~
//        fd.setFilterString(FileDialog.GAMES_EXT);                  //~1A22I~//~1A23M~//~1A24R~
//        fd.show();  //callback loadFileNotes                       //~1A22I~//~1A23M~//~1A24R~
//    }                                                              //~1A22I~//~1A23M~//~1A24R~
    //*************************************************************//~1Ah0R~
    //*from LocalFrame,PartnerFrame                                //~1Ah0R~
    //*************************************************************//~1A22I~//~1A23M~//~1Ah0R~
    public void loadFileNotes(Notes Pnotes)                                   //~1A22I~//~1A23M~//~1A24R~
    {                                                              //~1A22I~//~1A23M~
    	if (Dump.Y) Dump.println("TGF loadFileNote");              //~1A22I~//~1A23M~//~1A24R~
//        if (loadNotes==null)                                       //~1A22I~//~1A23M~//~1A24R~
//            return;                                                //~1A22I~//~1A23M~//~1A24R~
        Notes notes=Pnotes;                                     //~1A22I~//~1A23M~//~1A24R~
//      if ((notes.gameoptions & GameQuestion.GAMEOPT_RECEIVENOTES)!=0)//~1A2eR~
        if ((notes.gameoptions & GameQuestion.GAMEOPT_NOTES)!=0)   //~1A2eI~
        {                                                          //~1A2eI~
            NotesTree nt=notes.getTree();                          //~1A2eI~
            if (nt!=null)   //.ss file                             //~1A2eI~
            {                                                      //~1A2eI~
	        	nt.changeCoord(notes,Col);                         //~1A2eR~
        		changeCoordLang_ReceiveNotes(notes,nt);            //~1A2eR~
            }                                                      //~1A2eI~
        }                                                          //~1A2eI~
    	restoreNotes(notes);                                       //~1A22R~//~1A23M~
	    String msg=AG.resource.getString(R.string.InfoRestoredFile,notes.name);//~1A22I~//~1A23M~
        setLabel(msg,false/*no append*/);                          //~1A22I~//~1A23M~
	}                                                              //~1A22I~//~1A23M~
    //***************************                                  //~1A22I~//~1A23M~
    public void fileSaved(String Pnotename)                                        //~1A22I~//~1A23M~//~1A24R~
    {                                                              //~1A22I~//~1A23M~
    	if (Dump.Y) Dump.println("TGF fileSaved");                 //~1A22I~//~1A23M~//~1A27R~
        ButtonResign.setAction(R.string.Close);                    //~1A22I~//~1A23M~
//      ButtonFile.setVisibility(View.GONE);                       //~1A22I~//~1A23M~//~1A27R~
        ButtonSuspend.setVisibility(View.GONE);                    //~1A27I~
        if (Timer!=null)                                           //~1A22I~//~1A23M~
			Timer.stopit();                                        //~1A22I~//~1A23M~
	}                                                              //~1A22I~//~1A23M~
    //***************************                                  //~1A4qI~
    public void fileNotSaved()                                     //~1A4qI~
    {                                                              //~1A4qI~
    	if (Dump.Y) Dump.println("TGF fileNotSaved");              //~1A4qI~
        ButtonResign.setAction(R.string.Close);                    //~1A4qI~
        ButtonSuspend.setVisibility(View.GONE);                    //~1A4qI~
        if (Timer!=null)                                           //~1A4qI~
			Timer.stopit();                                        //~1A4qI~
	}                                                              //~1A4qI~
    //*************************************************            //~1A22I~//~1A23M~
    protected Notes saveNotes(int Pcolor,String Pnotesname)                            //~1A22I~//~1A23M~//~1A24R~
    {                                                              //~1A22I~//~1A23M~
        Notes notes;                                               //~1A24I~
    	if (Pnotesname==null)                                      //~1A24R~
        {                                                          //~1A24I~
        	String ts=Utils.getTimeStamp(Utils.TS_DATE_TIME); //yyyymmdd//~1A22R~//~1A23M~//~1A24R~
        	notes=new Notes(ts+":"+MatchTitle,Col,Pcolor);           //~1A22R~//~1A23R~//~1A24R~
        }                                                          //~1A24I~
        else                                                       //~1A24I~
        	notes=new Notes(Pnotesname,Col,Pcolor);          //~1A24I~
        notes.save(this);                                          //~1A22I~//~1A23M~
        notes.moves=-1;              //id of LGF/PGF               //~1A22I~//~1A23M~
        notes.moves0=B.moveNumber;	//starting move count in this snapshot//~1A22I~//~1A23M~
        notes.black=BlackTime-BlackRun;                            //~1A22R~//~1A23M~
        notes.white=WhiteTime-WhiteRun;                            //~1A22R~//~1A23M~
        notes.blackExtra=extraB?1:0;                               //~1A22I~//~1A23M~
        notes.whiteExtra=extraW?1:0;                              //~1A22I~//~1A23M~
        notes.extraTime=ExtraTime;                                 //~1A22I~//~1A23M~
        notes.totalTime=TotalTime;                                 //~1A24I~
        notes.gameoptions=B.GameOptions|GameQuestion.GAMEOPT_NOTES;	//reload id//~1A24R~
        notes.handicap=Handicap;                                   //~1A2cI~
        notes.blackName=BlackName;                                 //+1Ah0I~
        notes.whiteName=WhiteName;                                 //+1Ah0I~
        return notes;                                               //~1A22I~//~1A23M~
	}                                                              //~1A22I~//~1A23M~
    //************************************************************                                  //~1A22I~//~1A23R~
    //*except FreeBoard                                            //~1A2cI~
    //************************************************************ //~1A2cI~
    protected void restoreNotes(Notes Pnotes)                        //~1A22I~//~1A23M~//~1Ah0R~
    {                                                              //~1A22I~//~1A23M~
        B.moveNumber=Pnotes.moves0; //starting move count in this snapshot//~1A22I~//~1A23M~
        int[][] tray=Pnotes.getTray();                             //~1A22I~//~1A23M~
        int[][] pieces=Pnotes.getPieces();                         //~1A22I~//~1A23M~
        if (Dump.Y) Dump.println("TGF Col="+Col+",Note yourColor="+Pnotes.yourcolor);//~1A23I~
        if (Col==Pnotes.yourcolor)                                 //~1A23R~
        {                                                          //~1A23I~
//          B.YourColor=Pnotes.yourcolor;                          //~1A23R~//~1A2eR~
        }                                                          //~1A23I~
        else                                                       //~1A23I~
        {                                                          //~1A23I~
        	pieces=reverseColor(tray,pieces);                      //~1A23I~
//      	B.YourColor=-Pnotes.yourcolor;                         //~1A23I~//~1A2eR~
        }                                                          //~1A23I~
        aCapturedList.restoreTray(tray);                           //~1A23I~
        aCapturedList.setTrayVisible(false);                       //~1A23I~
        ((ConnectedBoard)B).restoreBoard(pieces);                  //~1A23I~
        setMainColor(Pnotes.color);                                //~1A22I~//~1A23M~
        ExtraTime=Pnotes.extraTime;                                //~1A22I~//~1A23M~
        TotalTime=Pnotes.totalTime;                                //~1A24I~
        extraB=Pnotes.blackExtra>0;                                //~1A22I~//~1A23M~
        extraW=Pnotes.whiteExtra>0;                                //~1A22I~//~1A23M~
        settimes(Pnotes.black,-1/*move*/,Pnotes.white,-1);         //~1A22I~//~1A23M~
        restoreTree(Pnotes);                                       //~1A2cR~
    }                                                              //~1A22I~//~1A23M~
    //************************************************************ //~1A23I~
    //*arrange from partners viewpoint                             //~1A23I~
    //************************************************************ //~1A23I~
    protected int[][] reverseColor(int[][] Ptray,int[][] Ppieces)   //~1A23I~//~1A2eR~
    {                                                              //~1A23I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A23R~
        {                                                          //~1A23I~
        	int u=Ptray[0][ii];                                    //~1A23I~
        	int l=Ptray[1][ii];                                    //~1A23I~
        	Ptray[0][ii]=l;                                        //~1A23I~
        	Ptray[1][ii]=u;                                        //~1A23I~
        }                                                          //~1A23I~
        int[][] pieces=new int[B.S][B.S];                          //~1A23I~
        for (int ii=0;ii<B.S;ii++)                                 //~1A23I~
	        for (int jj=0;jj<B.S;jj++)                             //~1A23I~
        	{                                                      //~1A23I~
        		pieces[ii][jj]=Ppieces[B.S-ii-1][B.S-jj-1];          //~1A23I~
            }                                                      //~1A23I~
        return pieces;                                             //~1A23I~
    }                                                              //~1A23I~
    //***************************                                  //~1A22I~//~1A23M~
    protected void setMainColor(int Pcolor)                          //~1A22I~//~1A23R~
    {                                                              //~1A22I~//~1A23M~
        if (Pcolor>0)                                              //~1A22I~//~1A23M~
        	B.black();		//State=1,P.color(1)                   //~1A22I~//~1A23M~
        else                                                       //~1A22I~//~1A23M~
        	B.white();     //State=2,P.color(-1)                  //~1A22I~//~1A23M~
        B.MainColor=Pcolor;                                       //~1A22I~//~1A23M~
		aCapturedList.displayCurrentColor(Pcolor);//after FreeBoardStarted//~1A22I~//~1A23M~
    }                                                              //~1A22I~//~1A23M~
    //***************************                                  //~1A2cI~
    private void restoreTree(Notes Pnotes)                         //~1A2cR~
    {                                                              //~1A2cI~
    	NotesTree nt=Pnotes.getTree();                             //~1A2cI~
        if (nt!=null)                                              //~1A2cI~
        {                                                          //~1A2cI~
        	notesTree=nt;	//CGF                                  //~1A2cI~
        	nt.display(this);	                                   //~1A2cR~
    		Pnotes.setTree(null);                                  //~1A2cR~
        }                                                          //~1A2cI~
    }                                                              //~1A2cI~
    //******************************************************                                  //~1A22I~//~1A23I~
    //*from EndGameQuestion/@@!suspend received                                        //~1A22I~//~1A23I~//~1A24R~
    //******************************************************                                  //~1A22I~//~1A23I~
    public void saveGame(String Pnotesname)                                         //~1A22R~//~1A23M~//~1A24R~
    {                                                              //~1A22I~//~1A23M~
    	if ((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0)               //~1A26I~
        {                                                          //~1A26I~
			saveGameDirect(Pnotesname);                            //~1A26I~
            return;                                                //~1A26I~
        }                                                          //~1A26I~
    	if (Dump.Y) Dump.println("LGF saveFile");                  //~1A22I~//~1A23M~
    	FileDialog fd=new FileDialog(this/*FileDialogI*/,null/*parentDialog*/,AG.resource.getString(R.string.Title_SaveFile),FileDialog.SAVE);//~1A22I~//~1A23M~
        int color=B.P.color();                                     //~1A22I~//~1A23M~
	    Notes notes=saveNotes(color,Pnotesname);                              //~1A22I~//~1A23M~//~1A24R~
        notes.setTree(notesTree);	//write to file                //~1A2cR~
        fd.setSaveNotes(notes,FileDialog.GAMES_EXT);               //~1A22I~//~1A23M~
        fd.show();                                                 //~1A22I~//~1A23M~
	}                                                              //~1A22I~//~1A23M~
    //******************************************************       //~1A26I~
    public void saveGameDirect(String Pnotesname)                  //~1A26I~
    {                                                              //~1A26I~
    	if (Dump.Y) Dump.println("LGF saveFileDirect");            //~1A26I~
        int color=B.P.color();                                     //~1A26I~
	    Notes notes=saveNotes(color,Pnotesname);                   //~1A26I~
        notes.setTree(notesTree);	//write to file                //~1A2cI~
        FileDialog.saveDirect(this,notes,FileDialog.GAMES_EXT);    //~1A26I~
	}                                                              //~1A26I~
    //******************************************************       //~1A27I~
    protected void infoGameSaved(String Pname)                     //~1A27I~
    {                                                              //~1A27I~
        if ((AG.Options & AG.OPTIONS_FIXSAVEDIR)!=0)               //~1A27I~
        {                                                          //~1A27I~
        	String msg=AG.resource.getString(R.string.InfoGameSaved,Pname);//~1A27I~
			new Message(this,msg);                                 //~1A27I~
        }                                                          //~1A27I~
    }                                                              //~1A27I~
    //******************************************************       //~1A24I~
	@Override
	public void fileDialogSaved(String Pnotesname)                  //~1A24R~
	{                                                              //~1A24I~
        fileSaved(Pnotesname);                                     //~1A24R~
	}                                                              //~1A24I~
//************************************************                 //~1A2eI~
//*chang movedescrliption language for receive note                //~1A2eI~
//************************************************                 //~1A2eI~
    protected void changeCoordLang_ReceiveNotes(Notes Pnotes,NotesTree Pnotestree)//~1A2eI~
    {                                                              //~1A2eI~
		ActionMove a;                                         //~1A2eI~
    //************************                                     //~1A2eI~
        if (Dump.Y) Dump.println("TGF:changeCoordLang");           //~1A2eI~
    	Pnotes.changeCoord=(Pnotes.coordType!=Notes.getCoordType());//~1A2eR~
    	if (!Pnotes.changeCoord)                                   //~1A2eI~
        	return;                                                //~1A2eI~
    	for (int ii=0;ii<Pnotestree.size();ii++)                   //~1A2eI~
        {                                                          //~1A2eI~
	    	a=Pnotestree.get(ii);                                  //~1A2eI~
        	ReplayBoard.replayRedoDescription_ReceiveNotes(this,Pnotes,a);//~1A2eR~
        }                                                          //~1A2eI~//~1A2eI~
    }                                                              //~1A2eI~
    //******************************************************       //~1A27I~
    protected void suspendGame(){}	//LGF,PGF will Override        //~1A27I~
    protected void saveGameover(){}	//LGF,PGF will Override        //~1A27I~
}
