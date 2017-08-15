package jagoclient.gui;

//import java.awt.*;

import com.Asgts.awt.Menu;                                          //~2C27R~//+3213R~

import jagoclient.Global;

/**
A menu with a specified font.
*/

public class MyMenu extends Menu
{   public MyMenu (String l)
    {   super(l);
        setFont(Global.SansSerif);
    }
}