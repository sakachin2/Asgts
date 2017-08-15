package com.Asgts.awt;                                                //~1108R~//~1109R~//~2C26R~//+3213R~

import jagoclient.gui.DoActionListener;

public class MouseEvent                                        //~1212R~//~1213R~
{   
    private int x,y;                                               //~1213I~
//    private MouseListener ml;                                      //~1524R~
    private DoActionListener dal;                                  //~1524R~
    public MouseEvent(DoActionListener Pdal)                       //~1213I~
    {                                                              //~1213I~
    	dal=Pdal;                                                  //~1213I~
    }                                                              //~1213I~
//    public MouseEvent(MouseListener Pml)                           //~1213I~
//    {                                                              //~1213I~
//    	ml=Pml;                                                    //~1213I~
//    }                                                              //~1213I~
    public MouseEvent(int Px,int Py)                               //~1317I~
    {                                                              //~1317I~
    	x=Px;y=Py;                                                 //~1317I~
    }                                                              //~1317I~
	public int getX()                                              //~1213I~
    {                                                              //~1213I~
    	return x;                                                  //~1213I~
    }                                                              //~1213I~
	public int getY()                                              //~1213I~
    {                                                              //~1213I~
    	return y;                                                  //~1213I~
    }                                                              //~1213I~
	public boolean isMetaDown()                                    //~1213I~
    {                                                              //~1213I~
    	return false;                                              //~1213I~
    }                                                              //~1213I~
	public boolean isPopupTrigger()                                //~1213I~
    {                                                              //~1213I~
    	return false;                                              //~1213I~
    }                                                              //~1213I~
	public boolean isControlDown()                                 //~1214I~
    {                                                              //~1214I~
    	return false;                                              //~1214I~
    }                                                              //~1214I~
	public boolean isShiftDown()                                   //~1214I~
    {                                                              //~1214I~
    	return false;                                              //~1214I~
    }                                                              //~1214I~
	public boolean isScrollDown()                                  //~1214I~
    {                                                              //~1214I~
    	return false;                                              //~1214I~
    }                                                              //~1214I~
	public int getClickCount()                                     //~1214I~
    {                                                              //~1214I~
    	return 0;                                                  //~1214I~
    }                                                              //~1214I~
    public DoActionListener getComponent()                         //~1213R~
    {                                                              //~1213R~
        return dal;                                                //~1213R~
    }                                                              //~1213R~
    public String paramString()                                    //~1214I~
    {                                                              //~1214I~
        return "";                                                 //~1214I~
    }                                                              //~1214I~
}//class                                                           //~1212R~
