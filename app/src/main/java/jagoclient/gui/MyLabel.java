//*CID://+v101R~:                             update#=    8;       //~@@@2I~//~v101R~
//**************************************************************   //~v101I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//**************************************************************   //~v101I~
package jagoclient.gui;

//import java.awt.*;                                               //~1112R~

import com.Asgts.awt.Container;                                     //~2C27R~//~v101R~
import com.Asgts.awt.Graphics;                                      //~2C27R~//+v101R~
import com.Asgts.awt.Label;                                         //~2C27R~//+v101R~

import jagoclient.Global;

/**
A label in a specified font.
*/

public class MyLabel extends Label                                 //~1112R~
{   public MyLabel (String s)
    {   super(s);                                                  //~1112R~
        setFont(Global.SansSerif);                                 //~1112R~//~1216R~
    }
    public MyLabel (Container Pcontainer,int Presid)               //~@@@2I~//~v101R~
    {   super(Pcontainer,Presid);                                  //~@@@2I~//~v101R~
        setFont(Global.SansSerif);                                 //~@@@2I~
    }                                                              //~@@@2I~
    public MyLabel (Container Pcontainer,int Presid,String s)      //~v101I~
    {   super(Pcontainer,Presid,s);                                //~v101I~
        setFont(Global.SansSerif);                                 //~v101I~
    }                                                              //~v101I~
//****************************************                         //~@@@2I~
    public MyLabel (int Presid)                                    //~@@@2I~
    {                                                              //~@@@2I~
        super(Presid);                                             //~@@@2R~
        setFont(Global.SansSerif);                                 //~@@@2I~
    }                                                              //~@@@2I~
    public void paint (Graphics g)                               //~1112R~//~1216R~
    {	Container c=getParent();
    	if (c!=null) setBackground(c.getBackground());
//        super.paint(g);                                          //~2C27R~
    }                                                            //~1112R~//~1216R~
}
