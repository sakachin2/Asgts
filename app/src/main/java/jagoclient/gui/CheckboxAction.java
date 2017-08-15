//*CID://+1A4rR~:                             update#=    1;       //+1A4rI~
//******************************************************************//+1A4rI~
//1A4r 2014/12/05 change savedir to editable                       //+1A4rI~
//******************************************************************//+1A4rI~
package jagoclient.gui;

//import java.awt.*;
//import java.awt.event.*;

import com.Asgts.awt.Checkbox;                                      //~2C26R~//~3213R~
import com.Asgts.awt.ItemEvent;                                     //~2C26R~//~3213R~
import com.Asgts.awt.ItemListener;                                  //~2C26R~//~3213R~

import jagoclient.Global;

public class CheckboxAction extends Checkbox                       //~1215M~
{                                                                  //~1215I~
//made to inner class to avoid seperate public class to its own class//~1124I~//~1215I~
public class CheckboxActionTranslator implements ItemListener
{   DoActionListener C;
    String S;
    public Checkbox CB;
    public CheckboxActionTranslator
        (Checkbox cb, DoActionListener c, String s)
    {   C=c; S=s; CB=cb;
    }
    public void itemStateChanged (ItemEvent e)
    {   C.itemAction(S,CB.getState());
    }
}

/**
Similar to ChoiceAction, but for checkboxes.
@see jagoclient.gui.ChoiceAction
*/

    public CheckboxAction (DoActionListener c, String s)           //~1215I~
    {   super(s);
        addItemListener(new CheckboxActionTranslator(this,c,s));
        setFont(Global.SansSerif);
    }                                                              //~1215R~
    public CheckboxAction (DoActionListener c, String s, String h)
    {   super(s);
        addItemListener(new CheckboxActionTranslator(this,c,h));
    }
    public CheckboxAction(DoActionListener c,int Presid,String Pitemactionname)//~4C06I~
    {   super(Presid);                                             //~4C06I~
        addItemListener(new CheckboxActionTranslator(this,c,Pitemactionname));//~4C06I~
    }                                                              //~4C06I~
}
