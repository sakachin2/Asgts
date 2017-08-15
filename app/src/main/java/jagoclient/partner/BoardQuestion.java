//*CID://+1Aa9R~:                             update#=   26;       //+1Aa9R~
//**********************************************************************//~v101I~
//1Aa9 2015/04/21 (BUG)Button of GameQuestion except Help dose not work after ChangeBK button//+1Aa9I~
//                GameQuestion(ModalDialog) run on subthread at connection complete.//+1Aa9I~
//                Modal latch is posted when ChangeBK button pushed,but dose not dismiss dialog//+1Aa9I~
//                Push button except help do latch.countdown which is already posted(subthread was already posted)//+1Aa9I~
//                -->GameQuestionDialog is not requred as modal.   //+1Aa9I~
//                (LocalGameQuestion is not subthread but reset modal also)//+1Aa9I~
//                !! Modal option is assuming Button click follows dismiss dialog.//+1Aa9I~
//                !! Check class extended CloseDialog(modal=true)  //+1Aa9I~
//1Aa8 2015/04/20 put in layout the gamequestion/boardquestion for mdpi//~1Aa8I~
//1A4f 2014/11/30 fail to restart partner game(Cast err PGF to RGF).//~1A24I~
//                mainFrame:boardType is upredictable when requested game from partner//~1A24I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//**********************************************************************//~v101I~
package jagoclient.partner;

//import com.Asgts.awt.GridLayout;                                  //~2C26R~//~v101R~

import android.view.View;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.awt.Checkbox;                                     //~v101R~
import com.Asgts.awt.Color;
import com.Asgts.awt.Component;
//import jagoclient.gui.ButtonAction;                              //~2C26R~
import jagoclient.MainFrame;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
//import jagoclient.gui.FormTextField;                             //~2C26R~
//import jagoclient.gui.MyLabel;                                   //~2C26R~
//import jagoclient.gui.MyPanel;                                   //~2C26R~
//import jagoclient.gui.Panel3D;                                   //~2C26R~
import jagoclient.gui.FormTextField;
import static jagoclient.partner.GameQuestion.*;

//import java.awt.GridLayout;
//import java.awt.Panel;
//import java.awt.TextField;

/**
Question to accept or decline a game with received parameters.
*/

public class BoardQuestion extends CloseDialog
{                                                                  //~2C29R~
//	int BoardSize,Handicap,TotalTime,ExtraTime,ExtraMoves;         //~2C29I~
 	int BoardSize,TotalTime,Reselectable,ExtraTime,GameOptions;           //~2C29I~//~2C30R~//~3107R~//~v1A0R~
    int NotRemovable,Handicap;                                              //~3123I~
    String ColorChoice;                                            //~v1A0R~
	PartnerFrame PF;
    private FormTextField YourName,HandicapText,GameName;                   //~v1A0I~//~1A24R~
	public BoardQuestion (PartnerFrame pf,
//      int s, String c,int tt,int et,int Pgameoptions,int Phandicap,String Prequestername)//~v1A0I~//~1A24R~
        int s, String c,int tt,int et,int Pgameoptions,int Phandicap,String Prequestername,String Preloadmatchname)//~1A24I~
    {                                                              //~3118I~
//      super(pf,AG.resource.getString(R.string.Title_BoardQuestion),R.layout.boardquestion,true,false);//~@@@2I~//~3118R~//~3123R~//~1Aa8R~
        super(pf,AG.resource.getString(R.string.Title_BoardQuestion),//~1Aa8I~
				(AG.layoutMdpi ? R.layout.boardquestion_mdpi : R.layout.boardquestion),//~1Aa8I~
//  			true,false);                                       //+1Aa9R~
    			false/*non modal*/,false);                         //+1Aa9R~
		PF=pf;
        BoardSize=s; TotalTime=tt; ExtraTime=et;                //~2C30R~//~3104R~//~v1A0R~
        ColorChoice=c;                             //~2C29I~       //~v1A0R~
        GameOptions=Pgameoptions;
        Handicap=Phandicap;//~3107R~
        YourName=new FormTextField(this,R.id.YourName,Prequestername);//~v101I~
        YourName.setBackground(GameQuestion.COLOR_YOURNAME);       //~3119I~
        new FormTextField(this,R.id.TotalTime,Integer.toString(TotalTime));//~v101I~
        new FormTextField(this,R.id.ExtraTime,Integer.toString(ExtraTime));//~v101I~
        String hcs=GameQuestion.getHadicapString(Handicap);                     //~v1A0I~
		HandicapText=new FormTextField(this,R.id.HandicapView,hcs);//~v1A0I~
        boolean movefirst;
  		movefirst=ColorChoice.equals("b");                     //~3118I~//~v1A0R~
        new Checkbox(this,R.id.PartnerMoveFirst).setState(movefirst);//~v101I~
        new Checkbox(this,R.id.NotifyCheck).setState((GameOptions & GAMEOPT_NOTIFYCHECK)!=0);//~v1A0I~
        new Checkbox(this,R.id.FoulMissMove).setState((GameOptions & GAMEOPT_FOUL_MISSMOVE)!=0);//~v1A0I~
        new Checkbox(this,R.id.FoulLeaveCheck).setState((GameOptions & GAMEOPT_FOUL_LEAVECHECK)!=0);//~v1A0I~
        new Checkbox(this,R.id.Foul2Pawn).setState((GameOptions & GAMEOPT_FOUL_2PAWN)!=0);//~v101I~//~v1A0I~
        new Checkbox(this,R.id.FoulUnmovableDrop).setState((GameOptions & GAMEOPT_FOUL_UNMOVABLEDROP)!=0);//~v1A0I~
        new Checkbox(this,R.id.FoulDropPawnCheckmate).setState((GameOptions & GAMEOPT_FOUL_DROPPAWNCHECKMATE)!=0);//~v1A0I~
        new ButtonAction(this,0,R.id.Accept);                   //~2C30I~//~3118R~//~3208R~//~v101I~
        new ButtonAction(this,0,R.id.Decline);                  //~2C30I~//~3118R~//~3208R~//~v101I~
        new ButtonAction(this,0,R.id.Help);                     //~2C30I~//~3118R~//~3208R~//~v101I~
        if ((GameOptions & GAMEOPT_NOTES)!=0)	//reopen game          //~1A24I~
        {                                                          //~1A24I~
        	reopenBoardQuestion(Preloadmatchname);                  //~1A24I~
        }                                                          //~1A24I~
		validate();
		show();
	}
    //*************************************************            //~1A24I~
    private void reopenBoardQuestion(String Pmatchname)            //~1A24I~
    {                                                              //~1A24I~
        View v=findViewById(R.id.GameNameLayout);                  //~1A24I~
        (new Component()).setVisibility(v,View.VISIBLE);                             //~1A24I~
        GameName=new FormTextField(this,R.id.GameName,Pmatchname);  //~1A24I~
        GameName.setBackground(Color.yellow);                      //~1A24R~
    }                                                              //~1A24I~
	
	public void doAction (String o)
	{                                                              //~2C26R~
        if (o.equals(AG.resource.getString(R.string.Accept)))     //~@@@@I~//~2C30I~
  		{                                                          //~2C30R~
        	int boardtype;                                         //~1A4fR~
            if (PF instanceof com.Asgts.jagoclient.partner.PartnerFrame)//~1A4fR~
            	boardtype=jagoclient.MainFrame.BT_BT;              //~1A4fR~
            else                                                   //~1A4fR~
            	boardtype=jagoclient.MainFrame.BT_IP;              //~1A4fR~
	        ((MainFrame)(AG.mainframe)).partnerGameRestartRequestedBoardType=boardtype;//~1A4fR~
            PF.doboard(BoardSize,ColorChoice,                      //~v1A0I~
           TotalTime,ExtraTime,GameOptions,Handicap);                      //~v1A0I~
	        ((MainFrame)(AG.mainframe)).partnerGameRestartRequestedBoardType=0;//~1A4fR~
	        setVisible(false); dispose();
		}
        else if (o.equals(AG.resource.getString(R.string.Decline)))//~2C30I~
		{	PF.declineboard();
			setVisible(false); dispose();
		}
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~2C30I~
		{                                                          //~2C30I~
			new HelpDialog(null,R.string.HelpTitle_BoardQuestion,"BoardQuestion");           //~2C30I~//~v1A0R~//~1A00R~
		}                                                          //~2C30I~
		else super.doAction(o);
	}
}

