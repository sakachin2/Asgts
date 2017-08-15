package com.Asgts.awt;                                            //~1112I~//~2C26R~//+3213R~

import jagoclient.Dump;
import jagoclient.gui.ActionTranslator;

public class MenuItem                                              //~1213R~
{                                                                  //~1112I~
	public String name;                                                   //~1122I~//~1123R~
	public Font font=null;                                                     //~1122I~//~1123R~//~1124R~
	public ActionTranslator actiontranslator=null;                 //~1124I~
	public ActionListener actionlistener=null;                     //~1213I~
//*****************************
    public MenuItem(String Pname)                                      //~1121R~//~1122R~
    {                                                              //~1112I~
    	name=Pname;                                                //~1121I~
    }                                                              //~1112I~
//*****************************                                    //~1124I~
    public void setFont(Font Pfont)                                //~1122I~
    {                                                              //~1122I~
    	font=Pfont;                                                //~1122I~
    }                                                              //~1122I~
//*****************************                                    //~1405I~
    public void setLabel(String Plabel) //from MainFrame           //~1405I~
    {                                                              //~1405I~
        if (Dump.Y) Dump.println("MenuItem setLabel: old="+name+",new="+Plabel);//~1506R~
    	name=Plabel;                                               //~1405I~
        if (actiontranslator!=null)                                //~1405I~
            actiontranslator.Name=name;                            //~1405I~
    }                                                              //~1405I~
//*****************************                                    //~1124I~
    public void addActionListener(ActionTranslator Pat)            //~1124I~
    {                                                              //~1124I~
    	actiontranslator=Pat;                                      //~1124I~
    }                                                              //~1124I~
//*****************************                                    //~1213I~
    public void addActionListener(ActionListener Pal)              //~1213I~
    {                                                              //~1213I~
    	actionlistener=Pal;	//@@@@ not used?                       //~1324R~
    }                                                              //~1213I~
}//class                                                           //~1112I~
