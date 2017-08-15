//*CID://+1A4nR~: update#= 193;                                    //+1A4nR~
//**********************************************************************//~1107I~
//1A6f 2015/02/13 support cutom layout of ListView                 //~1A6fI~
//1A4n 2014/12/04 FileDialog:multiple delete support               //~1A4nI~
//**********************************************************************//~1107I~
package com.Asgts.awt;                                         //~1107R~  //~1108R~//~1109R~//~1114R~//~v107R~//~1A6fR~

import com.Asgts.awt.Color;                                         //~v107R~
                                                                   //~1109I~
public class ListData                                                     //~1220I~//~1A6fR~
{                                                                  //~1220I~
	public String itemtext;                                        //~1220I~
    public Color  itemcolor;                                       //~1220I~
    public boolean choosed;                                        //~1A4nI~
    public String itemtext2;                                       //~1A6fI~
    public int    itemint;                                         //~1A6fI~
    public ListData(String Pitem,Color Pcolor)                     //~1220I~
    {                                                              //~1220I~
    	itemtext=Pitem;                                            //~1220I~
        itemcolor=Pcolor;                                          //~1220I~
    }                                                              //~1220I~
    public ListData(String Pitem,Color Pcolor,String Pitem2,int Pint)         //~1A6fI~
    {                                                              //~1A6fI~
    	itemtext=Pitem;                                            //~1A6fI~
        itemcolor=Pcolor;                                          //~1A6fI~
        itemtext2=Pitem2;                                          //~1A6fI~
    	itemint=Pint;                                              //~1A6fI~
    }                                                              //~1A6fI~
                                                                  //~1220I~
}//class                                                           //~1109I~
