//*CID://+1Ab5R~:                             update#=   69;       //~1Ab5R~
//***********************************************************************//~1A10I~
//1Ab5 2015/05/05 mdpi for FreeBoardQuestion                       //+1Ab5R~
//1A32 2013/04/17 chaneg to open FreeBoardQuestion at start button on FreeGoFrame//~1A32I~
//1A1a 2013/03/13 FreeBoard:start W/B button                       //~1A1aI~
//1A14 2013/03/10 FreeBoard Title                                  //~1A14I~
//1A10 2013/03/07 free board                                       //~1A10I~
//***********************************************************************//~1A10I~
package jagoclient;                                                 //~@@@@R~

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.AView;
import com.Asgts.Prop;
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.Checkbox;                                     //~@@@@R~
import com.Asgts.awt.Frame;                                        //~@@@@R~
import jagoclient.Global;
import jagoclient.board.GoFrame;
import jagoclient.board.Notes;
import jagoclient.dialogs.HelpDialog;
import jagoclient.partner.GameQuestion;                            //~@@@@I~
import jagoclient.gui.ButtonAction;
import jagoclient.gui.FormTextField;                             //~2C26R~//~@@@@R~


public class FreeBoardQuestion extends LocalGameQuestion                //~@@@@R~//~1A10R~
{                                                                  //~@@@@I~
//  FormTextField TotalTime,BoardName;                             //~1A14R~//~1A1aR~
    FormTextField BoardName;                                       //~1A1aI~
//  Checkbox Reflectable,Reselectable/*,MoveFirst*/;                   //~@@@@R~//~1A1aR~
//  MainFrame MF;                                                  //~@@@@R~//~1A32R~
    FreeGoFrame FGF;                                               //~1A32I~
    public String boardName;                                       //~1A14R~
	private boolean swStartGame;                                   //~@@@@I~
    private int startColor;                                        //~1A32I~
    //****************************************************************//~1A32I~
    //*for FreeBoard                                               //~1A32I~
    //****************************************************************//~1A32I~
	public FreeBoardQuestion(MainFrame Pframe)                     //~@@@@R~//~1A10R~
	{                                                              //~@@@@R~
//      super(Pframe,R.layout.freeboardquestion,AG.resource.getString(R.string.Title_FreeBoardQuestion));//~@@@@R~//~1A10R~//~1Ab5R~
        super(Pframe,                                              //~1Ab5I~
				(AG.layoutMdpi ? R.layout.freeboardquestion_mdpi : R.layout.freeboardquestion),//~1Ab5I~
				AG.resource.getString(R.string.Title_FreeBoardQuestion));//~1Ab5I~
//      MF=Pframe;                                                 //~@@@@I~//~1A32R~
        setGameData();                                               //~@@@@I~
        new ButtonAction(this,0,R.id.EditTotalTime);  //Request    //~@@@@I~
        new ButtonAction(this,0,R.id.OK);  //Request//~@@@@I~      //~1A10R~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel       //~@@@@I~
        new ButtonAction(this,0,R.id.Help);  //Help           //~@@@@I~
        setDismissActionListener(this/*DoActionListener*/);        //~@@@@I~
		validate();
		show();
	}
    //****************************************************************//~1A32I~
    //*from FreeGoFrame at start FreeBoard                        //~1A32I~//~1Ab5R~
    //****************************************************************//~1A32I~
	public FreeBoardQuestion(FreeGoFrame Pframe,int Pcolor)        //~1A32R~
	{                                                              //~1A32I~
//      super(Pframe,R.layout.freeboardquestion,AG.resource.getString(R.string.Title_FreeBoardQuestion));//~1A32I~//~1Ab5R~
        super(Pframe,                                              //~1Ab5I~
				(AG.layoutMdpi ? R.layout.freeboardquestion_mdpi : R.layout.freeboardquestion),//~1Ab5I~
				AG.resource.getString(R.string.Title_FreeBoardQuestion));//~1Ab5I~
        FGF=Pframe;                                                //~1A32I~
        startColor=Pcolor;                                         //~1A32I~
        setGameData();                                             //~1A32I~
        new ButtonAction(this,0,R.id.EditTotalTime);  //Request    //~1A32I~
        new ButtonAction(this,0,R.id.OK);  //Request               //~1A32I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel            //~1A32I~
        new ButtonAction(this,0,R.id.Help);  //Help                //~1A32I~
        setDismissActionListener(this/*DoActionListener*/);        //~1A32I~
		validate();                                                //~1A32I~
		show();                                                    //~1A32I~
	}                                                              //~1A32I~
    //****************************************************************//~1A14I~
    @Override	//GameQuestion                                                      //~1A14I~//~1A32R~
    public void setGameData()                                      //~1A14I~
    {                                                              //~1A14I~
		int oldint;                                                //~1A14I~
      if (FGF!=null && !FGF.boardName.equals(""))                  //~1A32I~
    	boardName=FGF.boardName;                                   //~1A32I~
      else                                                         //~1A32I~
    	boardName=Notes.getDefaultName();                          //~1A14M~
    	BoardName=new FormTextField(this,R.id.FreeBoardName,boardName);//~1A14M~
		oldint=Prop.getPreference(PKEY_TOTAL_TIME,0);              //~1A14I~
        TotalTime=new FormTextField(this,R.id.TotalTime,Integer.toString(oldint));//~1A14I~
                                                                   //~1A14I~
     	oldint=Prop.getPreference(PKEY_GAMEOPTIONS,0/*default*/);  //~1A1aI~
                                                                   //~1A14I~
        BigTimer=new Checkbox(this,R.id.BigTimer);                 //~1A14I~//~1A32R~
        BigTimer.setState((oldint & GAMEOPT_BIGTIMER)!=0);         //~1A14I~//~1A32R~
        NotifyCheck=new Checkbox(this,R.id.NotifyCheck);           //~1A14I~
        NotifyCheck.setState((oldint & GAMEOPT_NOTIFYCHECK)!=0);   //~1A14I~
	}                                                              //~1A14I~
    //****************************************************************//~1A14I~
    @Override                                                      //~1A14I~
    public boolean getGameData()                                   //~1A14I~
    {                                                              //~1A14I~
    	    boardsz=AG.BOARDSIZE_SHOGI;                            //~1A14I~
            String s="";                                           //~1A14I~
			try                                                    //~1A14I~
            {                                                      //~1A14I~
                s=TotalTime.getText();                             //~1A14I~
                if (s.trim().equals(""))                           //~1A14I~
	                totaltime=0;                                   //~1A14I~
                else                                               //~1A14I~
                	totaltime=Integer.parseInt(s);                 //~1A14I~
                movefirst=GAMEOPT_MOVEFIRST;	//Black is lower   //~1A1aI~
                bigtimer=BigTimer.getState()?GAMEOPT_BIGTIMER:0;   //~1A14I~
                notifycheck=NotifyCheck.getState()?GAMEOPT_NOTIFYCHECK:0;//~1A14I~
                gameoptions=movefirst+notifycheck+foul_2pawn+foul_unmovabledrop+foul_droppawncheckmate//~1A14I~
                            +foul_missmove+foul_leavecheck+bigtimer;//~1A14I~
				Prop.putPreference(PKEY_TOTAL_TIME,totaltime);     //~1A14I~
				Prop.putPreference(PKEY_EXTRA_TIME,extratime);     //~1A14I~
				Prop.putPreference(PKEY_GAMEOPTIONS,gameoptions);  //~1A14I~
			}                                                      //~1A14I~
			catch (NumberFormatException ex)                       //~1A14I~
            {                                                      //~1A14I~
            	AView.showToastLong(R.string.ParmErr,s);           //~1A14I~
				return false;                                      //~1A14I~
			}                                                      //~1A14I~
            return true;                                           //~1A14I~
    }                                                              //~1A14I~
//**************************************************************	//~@@@@R~
	public void doAction (String o)
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.OK)))   //~@@@@R~//~1A10R~
		{                                                          //~@@@@R~
        	if (!getGameData())                                    //~@@@@I~
            	return;                                            //~@@@@I~
	    	boardName=BoardName.getText();                         //~1A14I~
			setVisible(false); dispose();
			swStartGame=true;                                      //~@@@@R~
		}
        else                                                       //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))	//modal but no inputWait//~@@@@R~
		{   			//callback from Dialog after currentLaypout restored//~@@@@I~
        	if (swStartGame)                                       //~@@@@I~
            {                                                      //~1A32I~
              if (FGF==null)                                       //~1A32I~
				new FreeFrame(this);                              //~@@@@I~//~1A10R~
              else                                                 //~1A32I~
                FGF.studyStart(startColor,this);                   //~1A32R~
            }                                                      //~1A32I~
		}                                                          //~@@@@I~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~2C30I~//~@@@@I~//~1A14I~
		{                                                          //~2C30I~//~@@@@I~//~1A14I~
            new HelpDialog(null,R.string.HelpTitle_GameQuestion,"FreeBoardQuestion");                   //~v1A0I~//~1A00R~//~1A14I~
		}                                                          //~2C30I~//~@@@@I~//~1A14I~
		else super.doAction(o);
	}
//**************************************************************   //~1A32I~
//*from FreeFrame to open FreeGoFrame                              //~1A32I~
//**************************************************************   //~1A32I~
    public static void startFGQ(FreeFrame Pff)                     //~1A32I~
    {                                                              //~1A32I~
		int totaltime=0;                                           //~1A32I~
		int extratime=0;                                           //~1A32I~
        int movefirst=GAMEOPT_MOVEFIRST;	//Black is lower       //~1A32I~
        int bigtimer=0;//		BigTimer.getState()?GAMEOPT_BIGTIMER:0;//~1A32I~
        int notifycheck=0;//NotifyCheck.getState()?GAMEOPT_NOTIFYCHECK:0;//~1A32I~
        int gameoptions=movefirst+notifycheck/*+foul_2pawn+foul_unmovabledrop+foul_droppawncheckmate//~1A32I~
                            +foul_missmove+foul_leavecheck*/+bigtimer;//~1A32I~
        int color=1;                                               //~1A32I~
        int sz=AG.BOARDSIZE_SHOGI;                                 //~1A32I~
        int handicap=0;                                            //~1A32I~
		Pff.openGF(color,sz,totaltime,extratime,gameoptions,handicap);//~1A32I~
    }                                                              //~1A32I~
}

