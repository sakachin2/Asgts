package com.Asgts.awt;                                            //~1112I~//~2C26R~//+3213R~
import jagoclient.gui.CheckboxMenuItemAction;
                                                                   //~1123I~
public class CheckboxMenuItem extends MenuItem                      //~1123I~
{                                                                  //~1112I~
	private boolean status=false;                                                  //~1121I~//~1122R~//~1123R~
	public CheckboxMenuItemAction.CheckboxTranslator checkboxtranslator;                  //~1124I~//~1215R~
    public CheckboxMenuItem(String Pname)                          //~1121R~
    {                                                              //~1112I~
    	super(Pname);
    }                                                              //~1112I~
    public void setState(boolean Pstatus)                         //~1122I~
    {                                                              //~1122I~
    	status=Pstatus;                                            //~1122I~
    }                                                              //~1122I~
    public boolean getState()                                      //~1123I~
    {                                                              //~1123I~
    	return status;                                             //~1123R~
    }                                                              //~1123I~
    public void addItemListener(jagoclient.gui.CheckboxMenuItemAction.CheckboxTranslator Pct)//~1417I~
    {                                                              //~1124I~
    	checkboxtranslator=Pct;                                    //~1124I~
    }                                                              //~1124I~
}//class                                                           //~1112I~
