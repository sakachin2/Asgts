//*CID://+1A30R~:                             update#=    4;       //~1A30I~
//********************************************************************//~1A30I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//********************************************************************//~1A30I~
package jagoclient.gui;

//import java.awt.*;

import com.Asgts.awt.Choice;                                        //~2C26R~//~3213R~
import com.Asgts.awt.Container;
import com.Asgts.awt.ItemEvent;                                     //~2C26R~//~3213R~
import com.Asgts.awt.ItemListener;                                  //~2C26R~//~3213R~
//import java.awt.event.*;

import jagoclient.Global;

public class ChoiceAction extends Choice	//ChoiceTranslator as inner class for accessivility from awt.Choice//~1219I~
{                                                                   //~1219I~
public class ChoiceTranslator implements ItemListener
{   DoActionListener C;
    String S;
    public Choice Ch;
    public ChoiceTranslator
        (Choice ch, DoActionListener c, String s)
    {   C=c; S=s; Ch=ch;
    }
    public void itemStateChanged (ItemEvent e)
    {   C.itemAction(S,e.getStateChange()==ItemEvent.SELECTED);
    }
}

/**
This is a choice item, which sets a specified font and translates
events into strings, which are passed to the doAction method of the
DoActionListener.
@see jagoclient.gui.CloseFrame#doAction
@see jagoclient.gui.CloseDialog#doAction
*/

//public class ChoiceAction extends Choice                         //~1219R~
//    public ChoiceAction (DoActionListener c, String s)           //~1A30R~
//    {   addItemListener(new ChoiceTranslator(this,c,s));         //~1A30R~
//        setFont(Global.SansSerif);                               //~1A30R~
//    }                                                            //~1A30R~
    public ChoiceAction (DoActionListener c, String s,int Presid)  //~1A30R~
    {                                                              //~1A30I~
    	super((Container)c,Presid);                                //~1A30R~
        addItemListener(new ChoiceTranslator(this,c,s));           //~1A30I~
//      setFont(Global.SansSerif);                                 //+1A30R~
    }                                                              //~1A30I~
}
