//*CID://+1A10R~:                             update#=   32;       //~@@@@I~//~v101R~//+1A10R~
//***********************************************************************//~v101I~
//1A10 2013/03/07 free board                                       //+1A10I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//***********************************************************************//~v101I~
package jagoclient.gui;

import jagoclient.Dump;
import jagoclient.Global;

//import java.awt.Button;                                          //~1217R~
import com.Asgts.awt.Button;                                        //~@@@@I~//~v101R~
import com.Asgts.awt.Container;                                    //~v101R~
import com.Asgts.AG;                                                //~@@@@I~//~v101R~

/**
Similar to ChoiceAction but for buttons.
@see jagoclient.gui.ChoiceAction
*/

public class ButtonAction extends Button
{   DoActionListener C;
    String Name;
    ActionTranslator AT;
    public ButtonAction (DoActionListener c, String s, String name)
    {   super(s);
        C=c; Name=name;
//        ressouceId=androidButton.getId();                        //~@@@@R~
        addActionListener(AT=new ActionTranslator(c,name));        //~@@@@R~
        setFont(Global.SansSerif);
    }
    //****************************************************************************//~@@@@I~
    public ButtonAction (DoActionListener c, String name,int Presid)//~@@@@I~
    {                                                              //~@@@@I~
       	super(name,Presid);                                        //~@@@@R~
        C=c; Name=name;                                            //~@@@@I~
        addActionListener(AT=new ActionTranslator(c,name));        //~@@@@I~
        if (name==null)                                            //~@@@@I~
        {                                                          //~@@@@I~
        	name=label;		//super:Button:                        //~@@@@I~
            AT.Name=label;                                         //~@@@@I~
        }                                                          //~@@@@I~
        setFont(Global.SansSerif);                                 //~@@@@I~
    }                                                              //~@@@@I~
    //****************************************************************************//~@@@@I~
    public ButtonAction (DoActionListener c, String s)
    {   this(c,s,s);
    }
//    //****************************************************************************//~@@@@I~//~v101R~
//    //*findview by specified layout                              //~v101R~
//    //****************************************************************************//~v101R~
//    public ButtonAction (Container Pcontainer,DoActionListener c,int Ptextid,int Presid)//~v101R~
//    {                                                            //~v101R~
//        super(Pcontainer,Ptextid,Presid);                        //~v101R~
//        C=c;                                                     //~v101R~
//        Name=label;  //by textid                                 //~v101R~
//        addActionListener(AT=new ActionTranslator(c,Name));      //~v101R~
//        if (Name==null)                                          //~v101R~
//        {                                                        //~v101R~
//            Name=label;     //super:Button:getText()             //~v101R~
//            AT.Name=label;                                       //~v101R~
//        }                                                        //~v101R~
//        setFont(Global.SansSerif);                               //~v101R~
//    }                                                            //~v101R~
    //****************************************************************************//~v101I~
    //*findview by specified layout                                //~v101I~
    //****************************************************************************//~v101I~
    public ButtonAction (Container Pcontainer,int Ptextid,int Presid)//~v101I~
    {                                                              //~v101I~
       	super(Pcontainer,Ptextid,Presid);                          //~v101I~
        C=(DoActionListener)Pcontainer;                             //~v101I~
        Name=label;  //by textid                                   //~v101I~
        addActionListener(AT=new ActionTranslator(C,Name));        //~v101I~
        if (Name==null)                                            //~v101I~
        {                                                          //~v101I~
        	Name=label;		//super:Button:getText()               //~v101I~
            AT.Name=label;                                         //~v101I~
        }                                                          //~v101I~
        setFont(Global.SansSerif);                                 //~v101I~
    }                                                              //~v101I~
//    //****************************************************************************//~v101R~
//    public ButtonAction (Container Pcontainer,DoActionListener c,int Ptextid,int Presid,boolean Psmallbutton)//~v101R~
//    {                                                            //~v101R~
//        this(Pcontainer,c,Ptextid,Presid);                       //~v101R~
//        if (Psmallbutton && AG.smallButton)                      //~v101R~
//        {                                                        //~v101R~
//            setLayoutHeight(androidButton,AG.smallViewHeight,true);//by Component//~v101R~
//        }                                                        //~v101R~
//    }                                                            //~v101R~
    //****************************************************************************//~v101I~
    public ButtonAction (Container Pcontainer,int Ptextid,int Presid,boolean Psmallbutton)//~v101I~
    {                                                              //~v101I~
	    this(Pcontainer,Ptextid,Presid);                           //~v101I~
        if (Psmallbutton && AG.smallButton)                        //~v101I~
        {                                                          //~v101I~
	        setLayoutHeight(androidButton,AG.smallViewHeight,true);//by Component//~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
    //****************************************************************************//~@@@@I~
//    public ButtonAction (DoActionListener c, String s, int Presid)//~@@@@R~
//    {   this(c,s,s);                                             //~@@@@R~
//        setResId(Ptextid);  //awt:Button                         //~@@@@R~
//    }                                                            //~@@@@R~
//    //****************************************************************************//~@@@@I~//~v101R~
//    //*set doaction text by string id                              //~@@@@R~//~v101R~
//    //****************************************************************************//~@@@@I~//~v101R~
//    public static ButtonAction init(DoActionListener Pl,int Ptextid)//~@@@@R~//~v101R~
//    {                                                              //~@@@@R~//~v101R~
//        String name;                                               //~@@@@R~//~v101R~
//    //**********                                                   //~@@@@R~//~v101R~
//        if (Dump.Y) Dump.println("BuattonAction:init textid="+Integer.toHexString(Ptextid));//~@@@@R~//~v101R~
//        if (Ptextid==-1)    //disable following button             //~@@@@R~//~v101R~
//        {                                                          //~@@@@R~//~v101R~
//            AG.disableButton();                                    //~@@@@R~//~v101R~
//            return null;                                           //~@@@@R~//~v101R~
//        }                                                          //~@@@@R~//~v101R~
//    //  ButtonAction ba=new ButtonAction(Pl,name,Ptextid);         //~@@@@R~//~v101R~
//        name=AG.resource.getString(Ptextid);                       //~@@@@R~//~v101R~
//        ButtonAction ba=new ButtonAction(Pl,name);                 //~@@@@R~//~v101R~
//        return ba;                                                 //~@@@@R~//~v101R~
//    }                                                              //~@@@@R~//~v101R~
//    //****************************************************************************//~@@@@I~//~v101R~
//    //*set doaction text by string id                              //~@@@@I~//~v101R~
//    //****************************************************************************//~@@@@I~//~v101R~
//    public static ButtonAction init(DoActionListener Pl,int Ptextid,int Presid)//~@@@@I~//~v101R~
//    {                                                              //~@@@@I~//~v101R~
//        String name;                                               //~@@@@I~//~v101R~
//    //**********                                                   //~@@@@I~//~v101R~
//        if (Dump.Y) Dump.println("BuattonAction:init textid="+Ptextid);//~@@@@I~//~v101R~
//        if (Ptextid==-1)    //disable following button             //~@@@@I~//~v101R~
//        {                                                          //~@@@@I~//~v101R~
//            AG.disableButton(Presid);                              //~@@@@R~//~v101R~
//            return null;                                           //~@@@@I~//~v101R~
//        }                                                          //~@@@@I~//~v101R~
//        if (Ptextid==0)                                            //~@@@@I~//~v101R~
//            name=null;                                             //~@@@@I~//~v101R~
//        else                                                       //~@@@@I~//~v101R~
//            name=AG.resource.getString(Ptextid);    //doAction string//~@@@@R~//~v101R~
//        ButtonAction ba=new ButtonAction(Pl,name,Presid);          //~@@@@I~//~v101R~
//        return ba;                                                 //~@@@@I~//~v101R~
//    }                                                              //~@@@@I~//~v101R~
//    //****************************************************************************//~@@@@I~//~v101R~
//    //*set doaction text by string id                              //~@@@@I~//~v101R~
//    //****************************************************************************//~@@@@I~//~v101R~
//    public static ButtonAction init(DoActionListener Pl,int Ptextid,int Presid,boolean Psmallbutton/*apply for low device pixel*/)//~@@@@I~//~v101R~
//    {                                                              //~@@@@I~//~v101R~
//        ButtonAction ba=ButtonAction.init(Pl,Ptextid,Presid);       //~@@@@I~//~v101R~
//        if (Psmallbutton && AG.smallButton)                                        //~@@@@R~//~@@@2I~//~@@@@I~//~v101R~
//        {                                                          //~@@@2I~//~@@@@I~//~v101R~
//            ba.setLayoutHeight(ba.androidButton,AG.smallViewHeight,true);//by Component       //~@@@2I~//~@@@@R~//~v101R~
//        }                                                          //~@@@2I~//~@@@@I~//~v101R~
//        return ba;                                                 //~@@@@I~//~v101R~
//    }                                                              //~@@@@I~//~v101R~
    //****************************************************************************//~@@@@I~
    //*set doaction text by string id                              //~@@@@I~
    //****************************************************************************//~@@@@I~
    public void setAction(int Ptextid)                             //~@@@@I~
    {                                                              //~@@@@I~
        String name;                                               //~@@@@I~
    //**********                                                   //~@@@@I~
        if (Dump.Y) Dump.println("BuattonAction:setAction textid="+Ptextid);//~@@@@I~
	    Name=AG.resource.getString(Ptextid);	//doAction string  //~@@@@I~
        AT.Name=Name;                                              //~@@@@I~
        setText(androidButton,Name);	//by component             //~@@@@R~
    }                                                              //~@@@@I~
    //****************************************************************************//+1A10I~
    public void setVisibility(int Pvisible)                        //+1A10I~
    {                                                              //+1A10I~
        if (Dump.Y) Dump.println("BuattonAction:setVisibility name="+Name+",visible="+Pvisible);//+1A10I~
        setVisibility(androidButton,Pvisible);//by component       //+1A10I~
    }                                                              //+1A10I~
}
