//*CID://+1A32R~:                             update#=   54;       //~1A32R~
//**************************************************************** //~1A24I~
//1A32 2013/04/17 chaneg to open FreeBoardQuestion at start button on FreeGoFrame//~1A32I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//**************************************************************** //~1A24I~
package jagoclient;                                                 //~@@@@R~

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.awt.Checkbox;                                     //~@@@@R~
import com.Asgts.awt.Frame;                                        //~@@@@R~
import jagoclient.Global;
import jagoclient.board.GoFrame;
import jagoclient.board.Notes;
import jagoclient.partner.GameQuestion;                            //~@@@@I~
import jagoclient.gui.ButtonAction;
import jagoclient.gui.FormTextField;                             //~2C26R~//~@@@@R~


public class LocalGameQuestion extends GameQuestion                //~@@@@R~
{                                                                  //~@@@@I~
//  FormTextField Bishop,Knight,TotalTime,ExtraTime,Gameover,Gameover2;//~@@@@R~
//  Checkbox Reflectable,Reselectable,MoveFirst;                   //~@@@@R~
//  MainFrame MF;                                                  //~@@@@R~//~1A32R~
	private boolean swStartGame;                                   //~@@@@I~
    //*************************************************************//~@@@@I~
    //*from FreeBoardQuestion                                      //~@@@@I~
    //*************************************************************//~@@@@I~
  	public LocalGameQuestion(FreeGoFrame Pframe,int Presid,String Ptitle)//+1A32I~
	{                                                              //+1A32I~
        super(Pframe,Presid,Ptitle);                               //+1A32I~
    }                                                              //+1A32I~
  	public LocalGameQuestion(MainFrame Pframe,int Presid,String Ptitle)//~@@@@I~//+1A32R~
	{                                                              //~@@@@I~
        super(Pframe,Presid,Ptitle);                               //~@@@@I~
    }                                                              //~@@@@I~
    //*************************************************************//+1A32I~
	public LocalGameQuestion(MainFrame Pframe)                     //~@@@@R~
	{                                                              //~@@@@R~
        super(Pframe,AG.resource.getString(R.string.Title_LocalGameQuestion));//~@@@@R~//~1A24R~
//      MF=Pframe;                                                 //~@@@@I~//~1A32R~
        setGameData();                                               //~@@@@I~
        new ButtonAction(this,0,R.id.EditTotalTime);  //Request    //~@@@@I~
        new ButtonAction(this,0,R.id.EditExtraTime);  //Request    //~@@@@I~
        new ButtonAction(this,0,R.id.HandicapButton);  //Request   //~@@@@I~
        new ButtonAction(this,R.string.StartGame,R.id.Request);  //Request//~@@@@I~
        new ButtonAction(this,0,R.id.ButtonReload);  //open saved file//~1A24I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel       //~@@@@I~
        new ButtonAction(this,0,R.id.Help);  //Help           //~@@@@I~
        setDismissActionListener(this/*DoActionListener*/);        //~@@@@I~
		validate();
		show();
	}
//**************************************************************	//~@@@@R~
	public void doAction (String o)
    {                                                              //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.StartGame)))   //~@@@@R~
		{                                                          //~@@@@R~
        	if (!getGameData())                                    //~@@@@I~
            	return;                                            //~@@@@I~
			setVisible(false); dispose();
			swStartGame=true;                                      //~@@@@R~
		}
        else                                                       //~@@@@I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))	//modal but no inputWait//~@@@@R~
		{   			//callback from Dialog after currentLaypout restored//~@@@@I~
        	if (swReloadGame)                                      //~1A24I~
            	reloadFileDialog();	//GameQuestion                 //~1A24I~
            else                                                   //~1A24I~
        	if (swStartGame)                                       //~@@@@I~
				new LocalFrame(this);                              //~@@@@I~
		}                                                          //~@@@@I~
		else super.doAction(o);
	}
////************************************************************   //~1A24R~
//    @Override //GameQuestion                                     //~1A24R~
//    public void fileDialogLoaded(Notes Pnotes)                   //~1A24R~
//    {                                                            //~1A24R~
//        super.fileDialogLoaded(Pnotes); //set gameoptions        //~1A24R~
//        setVisible(false); dispose();                            //~1A24R~
//        swStartGame=true;                                        //~1A24R~
//    }                                                            //~1A24R~
}

