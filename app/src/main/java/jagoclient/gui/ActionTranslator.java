//*CID://+@@@@R~:                             update#=    6;       //~2C26I~//~@@@@R~
package jagoclient.gui;

import jagoclient.Dump;

import com.Asgts.awt.ActionEvent;                                   //~2C26R~//~@@@@R~

//import java.awt.event.ActionEvent;                               //~1112R~
//import java.awt.event.ActionListener;                            //~1112R~

//public class ActionTranslator implements ActionListener          //~1112R~
public class ActionTranslator                                      //~1112I~
{                                                                  //~1217R~
//  String Name;                                                   //~1217I~
    public String Name; //for Asgts.awt.Button @@@@               //~1217I~//~2C26R~//+@@@@R~
    DoActionListener C;                                            //~1325R~//~1330R~
//    int resourceId;                                                //~2C27I~//~@@@@R~
    public ActionTranslator (DoActionListener c, String name)
    {   Name=name; C=c;
    }
//    public ActionTranslator (DoActionListener c, String name, int PresId)//~2C27I~//~@@@@R~
//    {   Name=name; C=c;                                            //~2C27I~//~@@@@R~
//        resiurceId=PresId;                                         //~2C27I~//~@@@@R~
//    }                                                              //~2C27I~//~@@@@R~
    public void actionPerformed (ActionEvent e)                    //~1112R~//~1114R~
    {                                                              //~1324R~
    	if (Dump.Y) Dump.println("ActionTranslator cmd="+Name);    //~1506R~
      try                                                          //~@@@@I~
      {                                                            //~@@@@I~
        e.setActionCommand(Name);                 //@@@@ for AjagoModal//~1524R~
		C.doAction(Name);                                          //~1324I~
      }                                                            //~@@@@I~
      catch (Exception ex)                                          //~@@@@I~
      {                                                            //~@@@@I~
    	Dump.println(ex,"ActionTranslator:actionPerformed name="+Name);//~@@@@I~
      }                                                            //~@@@@I~
    }
    public void setString (String s)
    {	Name=s;
    }
}
