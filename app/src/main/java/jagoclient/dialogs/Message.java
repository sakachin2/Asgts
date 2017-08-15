//*CID://+v101R~:                             update#=   17;       //~@@@@I~//~v101R~
//**********************************************************************//~v101I~
//101d 2013/02/07 duplicated dislog is troublesaome by current layoutid//~v101I~
//**********************************************************************//~v101I~
package jagoclient.dialogs;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.Alert;                                            //+v101R~
import com.Asgts.R;                                                //+v101R~
import com.Asgts.awt.Frame;                                         //~2C26R~//+v101R~
import com.Asgts.awt.Panel;                                         //~2C26R~//+v101R~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.gui.ButtonAction;                              //~@@@@R~
import jagoclient.gui.CloseDialog;
//import jagoclient.gui.MyPanel;                                   //~@@@@R~
//import jagoclient.gui.Panel3D;

//import java.awt.Frame;
//import java.awt.Panel;

//import rene.viewer.SystemViewer;                                 //~1214R~
//import rene.viewer.Viewer;                                       //~1214R~//~@@@@R~
//import com.Asgts.rene.viewer.SystemViewer;                        //~1214I~//~2C26R~//+v101R~
import com.Asgts.rene.viewer.Viewer;                              //~1214I~//~2C26R~//~@@@@R~//+v101R~

/**
This class is used to display messages from the go server.
The message can have several lines.
*/

//public class Message extends CloseDialog                         //~@@@@R~
//{   Viewer T;                                                    //~@@@@R~
//    public Message (Frame f, int Presid)                         //~@@@@R~
//    {                                                            //~@@@@R~
//        this(f,AG.resource.getString(Presid));                   //~@@@@R~
//    }                                                            //~@@@@R~
//    public Message (Frame f, String m)                           //~@@@@R~
//    {   super(f,Global.resourceString("Message"),false);         //~@@@@R~
////        add("Center",T=                                          //~2C26R~//~@@@@R~
////            Global.getParameter("systemviewer",false)?           //~2C26R~//~@@@@R~
////                new SystemViewer():new Viewer());                //~2C26R~//~@@@@R~
//        T=new Viewer();                                            //~2C26I~//~@@@@R~
//        T.setFont(Global.Monospaced);                            //~@@@@R~
////        Panel p=new MyPanel();                                 //~@@@@R~
////        p.add(new ButtonAction(this,Global.resourceString("OK")));//~@@@@R~
////        add("South",new Panel3D(p));                           //~@@@@R~
////        Global.setwindow(this,"message",300,150);                //~2C26R~//~@@@@R~
//        T.setText(m);                                            //~@@@@R~
//        if (Dump.Y) Dump.println("Message msg="+m);              //~@@@@R~
//        ButtonAction.init(this,0,R.id.OK);                       //~@@@@R~
//        validate();                                              //~@@@@R~
//        show();                                                  //~@@@@R~
//    }                                                            //~@@@@R~
//    public void doAction (String o)                              //~@@@@R~
////  {   Global.notewindow(this,"message");                       //~@@@@R~
//    {                                                            //~@@@@R~
////      Global.notewindow(this,"message");                       //~@@@@R~
////      if (Global.resourceString("OK").equals(o))               //~@@@@R~
//        if (o.equals(AG.resource.getString(R.string.OK)))        //~@@@@R~
//        {   setVisible(false); dispose();                        //~@@@@R~
//        }                                                        //~@@@@R~
//        else super.doAction(o);                                  //~@@@@R~
//    }                                                            //~@@@@R~
//}                                                                //~@@@@R~
public class Message                       //~@@@@I~
{                                                                  //~v101R~
    Viewer T;                                                      //~v101I~
    public Message (Frame f, int Presid)                           //~@@@@I~
    {                                                              //~@@@@I~
        this(f,AG.resource.getString(Presid));                     //~@@@@I~
    }                                                              //~@@@@I~
    public Message (Frame f, String m)                             //~@@@@I~
    {                                                              //~v101R~
        String title=Global.resourceString("Message");             //~v101I~
		Alert.simpleAlertDialog(null,title,m,Alert.BUTTON_CLOSE);//~@@@2R~//~v101I~
        if (Dump.Y) Dump.println("Message msg="+m);                //~@@@@I~
    }                                                              //~@@@@I~
}                                                                  //~@@@@I~

