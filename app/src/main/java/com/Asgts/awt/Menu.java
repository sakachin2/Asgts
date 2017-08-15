package com.Asgts.awt;                                            //~1112I~//~2C26R~//+3213R~

import jagoclient.gui.CheckboxMenuItemAction;
import jagoclient.gui.MenuItemAction;

import java.util.ArrayList;

public class Menu                                     //~1307R~
{                                                                  //~1112I~
    private ArrayList<Object> submenuList=null;                       //~1121R~//~1122R~//~1124R~
    public Font font=null;                                                //~1121I~//~1124R~
    public String name;                                           //~1307I~
    public int itemid=0;                                           //~1328I~
//**************************************************               //~1124I~
    public Menu(String Pname)                                      //~1121R~
    {   
    	name=Pname;                                                //~1121I~                  //~1123R~
        submenuList=new ArrayList<Object>();                  //~1121R~//~1122R~
    }                                                              //~1112I~
//**************************************************               //~1124I~
    public void add(Menu Pmenu)                                    //~1122I~//~1123M~
    {                                                              //~1122I~//~1123M~
        submenuList.add(Pmenu);                                    //~1122I~//~1123M~
    }                                                              //~1122I~//~1123M~
//**************************************************               //~1124I~
    public void add(MenuItemAction Pitemaction)                    //~1121R~
    {                                                              //~1121I~
        submenuList.add(Pitemaction);                                 //~1121R~//~1122R~
    }                                                              //~1121I~
//**************************************************               //~1124I~
    public void add(MenuItem Pitem)                                //~1122I~
    {                                                              //~1122I~
        submenuList.add(Pitem);                                    //~1122I~
    }                                                              //~1122I~
//**************************************************               //~1124I~
    public void add(CheckboxMenuItemAction Pitemaction)          //~1122I~
    {                                                              //~1122I~
        submenuList.add(Pitemaction);                              //~1122I~
    }                                                              //~1122I~
//**************************************************               //~1124I~
    public void add(CheckboxMenuItem Pitemaction)                  //~1122I~
    {                                                              //~1122I~
        submenuList.add(Pitemaction);                              //~1122I~
    }                                                              //~1122I~
//**************************************************               //~1124I~
    public void addSeparator()                                     //~1121I~
    {                                                              //~1121I~
    }                                                              //~1121I~
//**************************************************               //~1124I~
    public void setFont(Font Pfont)                                //~1121I~
    {                                                              //~1121I~
    	font=Pfont;                                                //~1121I~
    }                                                              //~1121I~
//**************************************************               //~1123I~
    public Object getItem(int Pidx)                                //~1123I~//~1124R~
    {                                                              //~1123I~
    	if (Pidx>=submenuList.size())                              //~1123I~
        	return null;                                           //~1123I~
        return submenuList.get(Pidx);                              //~1123I~
    }                                                              //~1123I~
//**************************************************               //~1123I~
    public int getItemCtr()                                        //~1123I~
    {                                                              //~1123I~
    	return submenuList.size();                                 //~1123I~
    }                                                              //~1123I~
}//class                                                           //~1112I~
