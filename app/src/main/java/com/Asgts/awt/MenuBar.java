//*CID://+v107R~:                             update#=   13;       //~v107I~
//*************************************************************************//~v107I~
//1071:121204 partner connection using Bluetooth SPP               //~v107I~
//*************************************************************************//~v107I~
package com.Asgts.awt;                                            //~1112I~//~v107R~

import java.util.ArrayList;

import android.view.View;
import com.Asgts.AG;                                               //~v107R~
import com.Asgts.R;                                                //+v107R~

public class MenuBar                                               //~1121R~
{                                                                  //~1112I~
	public  static MenuBar currentMenuBar;                         //~1425R~
	public Frame frame;                                            //~1425R~
	public int seqno;                                              //~1425R~
	public int frameid;                                            //~1425R~
    public View relatedView;      //layout menu attached to                            //~1122I~//~1123R~//~1425R~
    public  ArrayList<Menu> menuList;                              //~1425R~
    int itemcount;                                                 //~1425R~
    public View childView;                                         //~1425R~
    public  Menu popupSubmenu;	//3rd level menu popup requested   //~1425R~
    private boolean swShowHelpOnly;                                //~1425R~
    public Menu helpMenu;                                          //~1425R~
//**************                                                   //~1411I~
    public  MenuBar()                                              //~1122R~
    {                                                              //~1112I~
        menuList=new ArrayList<Menu>();	//menuitem  list           //~1121R~
    }                                                              //~1112I~
    public  MenuBar(View Pchild)                                   //~1307R~
    {                                                              //~1307I~
    	this();                                                    //~1307I~
        frame=AG.currentFrame;                                     //~1307I~
    	childView=Pchild;                                          //~1307R~
    }                                                              //~1307I~
////*************                                                  //~1125I~
    public void setMenuBar(Frame Pframe)    //from Frame           //~1125I~
    {                                                              //~1125I~
        frame=Pframe;                                              //~1125I~
        AG.aMenu.registerMenuBar(this);                        //~1125I~//~v107R~
    }                                                              //~1125I~
    public void resetMenuBar(Frame Pframe)    //from Frame         //~1402I~
    {                                                              //~1402I~
        AG.aMenu.removeMenuBar(this);	//delete from menubarlist//~1402I~//~v107R~
        frame=null;                                                //~1402M~
    }                                                              //~1402I~
//*************                                                    //~1123I~
    public void add(Menu Pmenu)                                         //~1121I~
    {                                                              //~1121I~
        menuList.add(Pmenu);                                       //~1121I~
//  	if (Pmenu.name.equals(Global.resourceString("Help")))      //~1411I~//~v107R~
    	if (Pmenu.name.equals(AG.resource.getString(R.string.Help)))//~v107I~
	    	helpMenu=Pmenu;	//WhoFrame,gamesFrame dose not use setHelpMenu()//~1411I~
    }
//*************                                                    //~1123I~
    public void setHelpMenu(Menu Pmenu)                            //~1122I~
    {                                                              //~1122I~
        if (AG.currentFrame.framelayoutresourceid==AG.frameId_MainFrame) //~1507I~
        {                                                          //~1507I~
//            if (AG.aBT!=null)                                    //~v107R~
//                AG.aBT.addBTMenu(this);                             //~2C06I~//~v107R~
        	AG.aMenu.addAjagoOptionMenu(this);                   //~1507I~//~v107R~
        }                                                          //~1507I~
    	helpMenu=Pmenu;                                            //~1411I~
        menuList.add(Pmenu);                                       //~1122I~
    }                                                              //~1122I~
//**************************************************               //~1123I~
    public Menu getMenu(int Pidx)                                  //~1123I~
    {                                                              //~1123I~
    	if (Pidx>=menuList.size())                                 //~1123I~
        	return null;                                           //~1123I~
        return menuList.get(Pidx);                                 //~1123I~
    }                                                              //~1123I~
    public void setShowHelpOnly(boolean Psw)                       //~1411I~
    {                                                              //~1411I~
    	swShowHelpOnly=Psw;                                        //~1411I~
    }                                                              //~1411I~
    public boolean getShowHelpOnly()                                  //~1411I~
    {                                                              //~1411I~
    	return swShowHelpOnly;                                     //~1411I~
    }                                                              //~1411I~

}//class                                                           //~1112I~
