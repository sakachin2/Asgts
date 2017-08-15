package com.Asgts.awt;                                              //~2C26R~//+3213R~

import java.util.Arrays;



public class MemoryImageSource    //~1120R~
{                                                                  //~1112I~
	int [] pixel;                                               //~1120I~
    int width,height;                                              //~1120I~
    public MemoryImageSource(int Pw,int Ph,int Prgb,int [] Psrcpixel,int Poffs,int Pscan)//~1120R~
    {                                                              //~1117I~
    	width=Pw;                                                  //~1120I~
        height=Ph;                                                 //~1120I~
        int destlen=Pw*Ph;                                         //~1120R~
    	pixel=new int[destlen];                   //~1120R~
        int srclen=Psrcpixel.length;                               //~1120I~
        if (destlen>srclen)                                        //~1120I~
        {                                                          //~1120I~
        	Arrays.fill(pixel,Prgb);                                      //~1120I~
        	System.arraycopy(Psrcpixel,Poffs,pixel,0,srclen);        //~1120R~
        }                                                          //~1120I~
        else                                                       //~1120I~
        	System.arraycopy(Psrcpixel,Poffs,pixel,0,destlen);       //~1120I~
    }                                                              //~1117I~
}//class                                                           //~1112I~
