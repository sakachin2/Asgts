//*CID://+1A30R~:                             update#=    1;       //+1A30I~
//**********************************************************************//+1A30I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //+1A30I~
//**********************************************************************//+1A30I~
package com.Asgts.awt;                                            //~1112I~//~2C26R~//~3213R~

import jagoclient.gui.ChoiceAction;

//**drop down list ***                                                                  //~1112I~//~1219R~
public class Choice extends Component                               //~1112R~//~1216R~//~1219R~
{                                                                  //~1112I~
	private Spinner spinner;                                       //~1219I~
//*******************************                                  //~1216I~
//  public Choice()                                                 //~1112R~//~1219R~//+1A30R~
//  {                                                              //~1112I~//+1A30R~
//  	spinner=new Spinner();                                     //~1219I~//+1A30R~
//  }                                                              //~1112I~//+1A30R~
    public Choice(Container Pcontainer,int Presid)                 //+1A30I~
    {                                                              //+1A30I~
    	spinner=new Spinner(Pcontainer,Presid);                    //+1A30I~
    }                                                              //+1A30I~
//***************                                                  //~1220I~
	public void add(String Pentry)                                 //~1220I~
    {                                                              //~1220I~
    	spinner.add(Pentry);                                       //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
	public String getSelectedItem()                                //~1220I~
    {                                                              //~1220I~
    	return spinner.getSelectedItem();                                 //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
	public int getItemCount()                                      //~1220I~
    {                                                              //~1220I~
    	return spinner.getItemCount();                                    //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
	public String getItem(int Ppos)                                //~1220I~
    {                                                              //~1220I~
    	return spinner.getItem(Ppos);                                     //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1220I~
    public void setFont(Font Pfont)                                //~1219I~
    {                                                              //~1219I~
    	spinner.setFont(Pfont);                                    //~1220I~
    }                                                              //~1219I~
//***************                                                  //~1220I~
    public void addItemListener(ChoiceAction.ChoiceTranslator Pct)              //~1220I~
    {                                                              //~1220I~
    	spinner.addItemListener(Pct);                              //~1220I~
    }                                                              //~1220I~
//***************                                                  //~1306I~
    public void select(int Pindex)                                 //~1306I~
    {                                                              //~1306I~
    	spinner.setSelection(Pindex);                   //~1306R~
    }                                                              //~1306I~
//***************                                                  //~1314I~
    public void select(String Psearchstring)                       //~1314I~
    {                                                              //+1314I~                             //~1314I~
    	spinner.setSelection(Psearchstring);                       //~1314I~
    }                                                              //~1314I~
//***************                                                  //~1306I~
    public int getSelectedIndex()                                  //~1306R~
    {                                                              //~1306I~
    	return spinner.getSelectedItemPosition();                  //~1306R~
    }                                                              //~1306I~
}//class                                                           //~1112I~
