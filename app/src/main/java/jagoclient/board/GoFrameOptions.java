//*CID://+1A1jR~:                             update#=    5;       //+1A1jR~
//*******************************************************************************//+1A1jI~
//1A1j 2013/03/19 change Help file encoding to utf8 (path change drop jagoclient from jagoclient/helptexts)//+1A1jI~
//*******************************************************************************//+1A1jI~
package jagoclient.board;
import com.Asgts.AG;                                               //~3213R~
import com.Asgts.Prop;                                             //~3213R~
import com.Asgts.R;                                                //~3213R~
import com.Asgts.awt.Checkbox;                                     //~3213R~
import com.Asgts.awt.Frame;                                        //~3213R~

import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;

//*CID://+@@@@R~:                             update#=    5;       //~@@@@I~

class GoFrameOptions extends CloseDialog                           //~@@@@I~
{                                                                  //~@@@@R~
	Checkbox NoSound,BeepOnly,TimerWarning,ShowLast;               //~@@@@I~//~3114R~
	Checkbox NoGameoverSound,AutoCheckmate;                        //~1A09R~
	private ConnectedGoFrame CGF;
	public GoFrameOptions(ConnectedGoFrame f)                                 //~@@@@I~
	{                                                              //~@@@@I~
		super(f,AG.resource.getString(R.string.GoFrameOptions),R.layout.goframeoptions,false/*no modal*/,false/*no reshedule at close*/);//~@@@@I~//~3115R~
        CGF=f;
//        NoSound=new Checkbox(R.id.NoSound);                        //~@@@@I~//~3208R~
//        BeepOnly=new Checkbox(R.id.BeepOnly);                      //~@@@@I~//~3208R~
//        TimerWarning=new Checkbox(R.id.TimerWarning);              //~@@@@I~//~3208R~
//        ShowLast=new Checkbox(R.id.ShowLast);                      //~@@@@I~//~3208R~
//        ButtonAction.init(this,0,R.id.OK);                         //~@@@@I~//~3208R~
//        ButtonAction.init(this,0,R.id.Cancel);                     //~@@@@I~//~3208R~
//        ButtonAction.init(this,0,R.id.Help);                       //~@@@@I~//~3208R~
        NoSound=new Checkbox(this,R.id.NoSound);                   //~3208I~
        BeepOnly=new Checkbox(this,R.id.BeepOnly);                 //~3208I~
        NoGameoverSound=new Checkbox(this,R.id.NoGameoverSound);   //~1A09I~
        TimerWarning=new Checkbox(this,R.id.TimerWarning);         //~3208I~
        ShowLast=new Checkbox(this,R.id.ShowLast);                 //~3208I~
        AutoCheckmate=new Checkbox(this,R.id.AutoCheckmate);       //~1A09I~
        new ButtonAction(this,0,R.id.OK);                     //~3208I~
        new ButtonAction(this,0,R.id.Cancel);                 //~3208I~
        new ButtonAction(this,0,R.id.Help);                   //~3208I~
        NoSound.setState((AG.Options & AG.OPTIONS_NOSOUND)!=0);       //~@@@@I~
        BeepOnly.setState((AG.Options & AG.OPTIONS_BEEP_ONLY)!=0);     //~@@@@I~
        NoGameoverSound.setState((AG.Options & AG.OPTIONS_NO_GAMEOVER_SOUND)!=0);//~1A09I~
        TimerWarning.setState((AG.Options & AG.OPTIONS_TIMER_WARNING)!=0);//~@@@@I~
        ShowLast.setState((AG.Options & AG.OPTIONS_SHOW_LAST)!=0);     //~@@@@I~
        AutoCheckmate.setState((AG.Options & AG.OPTIONS_AUTO_CHECKMATE)!=0);//~1A09I~
		show();                                                    //~@@@@I~
	}                                                              //~@@@@I~
//*****************************************                        //~@@@@R~
    private void getOptions()                                      //~@@@@I~
    {                                                              //~@@@@I~
        if (NoSound.getState())	    	AG.Options|=AG.OPTIONS_NOSOUND;    		else AG.Options&=~AG.OPTIONS_NOSOUND;//~@@@@I~//~3114R~
        if (BeepOnly.getState())		AG.Options|=AG.OPTIONS_BEEP_ONLY;  		else AG.Options&=~AG.OPTIONS_BEEP_ONLY;//~@@@@I~//~3114R~
        if (NoGameoverSound.getState())	AG.Options|=AG.OPTIONS_NO_GAMEOVER_SOUND;  		else AG.Options&=~AG.OPTIONS_NO_GAMEOVER_SOUND;//~1A09I~
        if (TimerWarning.getState())	AG.Options|=AG.OPTIONS_TIMER_WARNING;  	else AG.Options&=~AG.OPTIONS_TIMER_WARNING;//~@@@@I~//~3114R~
        if (ShowLast.getState())		AG.Options|=AG.OPTIONS_SHOW_LAST;  		else AG.Options&=~AG.OPTIONS_SHOW_LAST;//~@@@@I~//~3114R~
        if (AutoCheckmate.getState())	AG.Options|=AG.OPTIONS_AUTO_CHECKMATE;  else AG.Options&=~AG.OPTIONS_AUTO_CHECKMATE;//~1A09I~
        Prop.putPreference(AG.PKEY_OPTIONS,AG.Options);            //~@@@@I~
    }                                                              //~@@@@I~
//*****************************************                        //~@@@@I~
	public void doAction (String o)                                //~@@@@I~
	{	if (o.equals(AG.resource.getString(R.string.OK)))          //~@@@@I~
		{                                                          //~@@@@I~
            getOptions();                                          //~@@@@I~
			setVisible(false); dispose();                          //~@@@@I~
		}                                                          //~@@@@I~
    	else if (o.equals(AG.resource.getString(R.string.Cancel))) //~@@@2I~//~@@@@I~
		{                                                          //~@@@@I~
			setVisible(false); dispose();                          //~@@@@I~
		}                                                          //~@@@@I~
    	else if (o.equals(AG.resource.getString(R.string.Help)))   //~@@@2I~//~@@@@I~
		{                                                          //~@@@@I~
//      	new HelpDialog(null,R.string.Help_GoFrameOptions);          //~@@@@I~//~3208R~//~1A09R~
//      	new HelpDialog(null,R.string.HelpTitle_GoFrameOptions,"GoFrameOptions");//~1A09R~//+1A1jR~
        	new HelpDialog(null,R.string.HelpTitle_GoFrameOptions,"BoardFrameOptions");//+1A1jI~
		}                                                          //~@@@@I~
	}                                                              //~@@@@I~
}
