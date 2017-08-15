package jagoclient.gui;

//import java.awt.*;                                               //~1524R~

import com.Asgts.awt.CheckboxMenuItem;                              //~2C26R~//~3213R~
import com.Asgts.awt.ItemEvent;                                     //~2C26R~//~3213R~
import com.Asgts.awt.ItemListener;                                  //~2C26R~//~3213R~
//import java.awt.event.*;

import jagoclient.Global;

public class CheckboxMenuItemAction extends CheckboxMenuItem       //~1124M~
{                                                                  //~1122R~//~1124M~
//made to inner class to avoid "separate public class to its own class"//~1524R~
public class CheckboxTranslator implements ItemListener            //~1124R~
{   DoActionListener C;
    String S;
    public CheckboxMenuItem CB;
    public CheckboxTranslator 
        (CheckboxMenuItem cb, DoActionListener c, String s)
    {   C=c; S=s; CB=cb;
    }
    public void itemStateChanged (ItemEvent e)
    {   C.itemAction(S,CB.getState());
    }
}

/**
Similar to ChoiceAction, but for checkboxes in menus.
@see jagoclient.gui.ChoiceAction
*/
/*@@@@                                                             //~1124I~
public class CheckboxMenuItemAction extends CheckboxMenuItem       //~1124I~
{                                                                  //~1124I~
@@@@*/                                                             //~1124I~

   	public CheckboxMenuItemAction (DoActionListener c, String s,int Presid)   //~1122I~//+3415R~
    {   super(s);
        addItemListener(new CheckboxTranslator(this,c,s));
        setFont(Global.SansSerif);
    }
}
