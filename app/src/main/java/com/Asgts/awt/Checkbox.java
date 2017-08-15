//*CID://+v101R~:                             update#=    5;       //~@@@@I~//~v101R~
//**********************************************************************//~v101I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//**********************************************************************//~v101I~
package com.Asgts.awt;                                            //~1215R~//~2C26R~//~v101R~

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import jagoclient.gui.CheckboxAction;

import com.Asgts.AG;                                                //~2C26R~//+v101R~

public class Checkbox extends Component                            //~1328R~
{                                                                  //~1215R~
	public android.widget.CheckBox androidCheckBox=null;           //~1328I~
                                                                   //~1328I~
	private CheckboxAction.CheckboxActionTranslator checkboxactiontranslator;                  //~1124I~//~1215I~
                                                                   //~1328I~
    public Checkbox()                                 //~1215I~    //~1328R~
    {                                                              //~1215I~
    	androidCheckBox=findView();                                //~1328R~
        componentView=androidCheckBox;	//for Component.requestFocus;//~1405I~
    }                                                              //~1215I~
//**************                                                   //~@@@@I~
    public Checkbox(String Ptext)                                              //~1328I~
    {                                                              //~1328I~
    	androidCheckBox=findView();                          //~1328I~
        setText(androidCheckBox,Ptext);                                  //~1328I~
    }                                                              //~1328I~
//**************                                                   //~@@@@I~
    public Checkbox(int Presid)                                    //~@@@@I~
    {                                                              //~@@@@I~
    	androidCheckBox=(CheckBox)AG.findViewById(Presid);                      //~@@@@I~
    }                                                              //~@@@@I~
//**************                                                   //~v101I~
    public Checkbox(Container Pcontainer,int Presid)               //~v101I~
    {                                                              //~v101I~
        if (Pcontainer.containerLayoutView!=null)                  //~v101I~
    	androidCheckBox=(CheckBox)Pcontainer.findViewById(Presid); //~v101I~
        else                                                       //~v101I~
    	androidCheckBox=(CheckBox)AG.findViewById(Presid);         //~v101I~
    }                                                              //~v101I~
//**************                                                   //~1328I~
    private android.widget.CheckBox findView()                     //~1328R~
    {                                                              //~1328I~
        return androidCheckBox=(android.widget.CheckBox)AG.findCheckBoxView();//~1328I~
    }                                                              //~1328I~
    public boolean getState()                                      //~1215I~
    {                                                              //~1215I~
		return androidCheckBox.isChecked();                                        //~1215I~//~1328R~
    }                                                              //~1215I~
    public void setState(boolean Pstat)                            //~1215I~
    {                                                              //~1215I~
		androidCheckBox.setChecked(Pstat);                                  //~1215I~//~1328R~
    }                                                              //~1215I~
    public void setState(boolean Pstat,boolean Penable)            //~@@@@I~
    {                                                              //~@@@@I~
		androidCheckBox.setChecked(Pstat);                         //~@@@@I~
		androidCheckBox.setEnabled(Penable);                       //~@@@@I~
    }                                                              //~@@@@I~
    public void setFont(Font c)                                         //~1112I~//~1215I~
    {                                                              //~1112I~//~1215I~
    	c.setFont(androidCheckBox);                                           //~1112I~//~1215I~//~1328R~
    }                                                              //~1112I~//~1215I~
    public void addItemListener(CheckboxAction.CheckboxActionTranslator Pcat)                 //~1124I~//~1215I~
    {                                                              //~1124I~//~1215I~
    	checkboxactiontranslator=Pcat;                                    //~1124I~//~1215R~
        androidCheckBox.setOnCheckedChangeListener                           //~1215I~//~1328R~
        	(	                                                   //~1215I~
        		new OnCheckedChangeListener()                      //~1215I~
            	{   
        			@Override//~1215I~
                	public void onCheckedChanged(CompoundButton PbuttonView,boolean isChecjed)//~1215I~
                    {                                              //~1215I~
                    	ItemEvent e=new ItemEvent();                         //~1215I~
                    	checkboxactiontranslator.itemStateChanged(e);//~1215I~
                    }                                              //~1215I~
                }                                                  //~1215I~
            );                                                     //~1215I~
    }                                                              //~1124I~//~1215I~
                                          //~1215I~
}
