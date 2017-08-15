package com.Asgts.awt;                                                //~1108R~//~1109R~//~2C26R~//+3213R~

import android.graphics.Paint;
                      //~1117I~
public class FontMetrics
{   
	Font font;                                                     //~1425R~
    Font oldfont;                                                  //~1425R~
	android.graphics.Paint.FontMetrics fontmetrics;                //~1425R~
    int [] width256;                                               //~1425R~
	public FontMetrics(Font Pfont,android.graphics.Paint.FontMetrics Pfontmetrics)//~1117R~
	{
    	font=Pfont;                                                //~1117I~
		fontmetrics=Pfontmetrics;
	}
	public int stringWidth(String s)                               //~1117I~
	{                                                              //~1117I~
        Paint painttext=new Paint(Paint.ANTI_ALIAS_FLAG);          //~1117I~
        painttext.setTypeface(font.getTypefaceStyle());            //~1117I~
        painttext.setTextSize(font.getSize());	//by SP            //~1428R~
//      if (Dump.Y) Dump.println(font.name+" stringwidth="+s+"="+painttext.measureText(s)+",font.getSize()="+font.getSize());//~1212I~//~1303R~//~1506R~//~2C15R~
        return (int)painttext.measureText(s);                      //~1117I~
	}                                                              //~1117I~
	public int getAscent()                                         //~1117I~
	{                                                              //~1117I~
    	int ceiling;                                               //~1309I~
        ceiling=(int)fontmetrics.ascent;	//android minus;awt plus//~1309I~
        if ((float)ceiling>fontmetrics.ascent)	//minus cut        //~1309I~
        	ceiling--;                                             //~1309I~
        ceiling=-ceiling;	//android minus;awt plus               //~1309I~
//      if (Dump.Y) Dump.println(font.name+" ascent="+fontmetrics.ascent+",return int="+ceiling);//~1212I~//~1506R~//~2C15R~
        return ceiling;                                            //~1309R~
	}                                                              //~1117I~
	public int getDescent()                                        //~1212I~
	{                                                              //~1212I~
//      if (Dump.Y) Dump.println(font.name+" descent="+fontmetrics.descent);//~1506R~//~2C15R~
        return (int)fontmetrics.descent;                           //~1212I~
	}                                                              //~1212I~
	public int getLeading()                                        //~1212I~
	{                                                              //~1212I~
//      if (Dump.Y) Dump.println(font.name+" leading="+fontmetrics.leading);//~1506R~//~2C15R~
        return (int)fontmetrics.leading;                           //~1212I~
	}                                                              //~1212I~
	public int getHeight()                                         //~1212I~
	{                                                              //~1212I~
    	int height=(int)Math.ceil(Math.abs(fontmetrics.ascent)+Math.abs(fontmetrics.descent));//~1212I~
//      if (Dump.Y) Dump.println(font.name+" height="+height);     //~1506R~//~2C15R~
        return height;                                             //~1212I~
	}                                                              //~1212I~
	public int[] getWidths()                                       //~1212I~
	{                                                              //~1212I~
    	if (oldfont!=null && oldfont==font)                        //~1217I~
        	return width256;                                       //~1217I~
        oldfont=font;//set when font chaged                        //~1217I~
    	if (width256==null)                                        //~1212I~
        {                                                          //~1212I~
        	width256=new int[256];                                 //~1212I~
        }                                                          //~1217I~
        for (int ii=0;ii<256;ii++)                             //~1212I~//~1217R~
        {                                                      //~1212I~//~1217R~
            width256[ii]=charWidth((char)ii);                  //~1212I~//~1217R~
        }                                                      //~1212I~//~1217R~
    	return width256;                                           //~1212I~
	}                                                              //~1212I~
//*** rene.viewer.Line                                             //~1212I~
	public int charsWidth(char [] Pchars,int Poffs,int Plen)       //~1212R~
	{                                                              //~1212I~
        return stringWidth(new String(Pchars,Poffs,Plen));         //~1212I~
	}                                                              //~1212I~
	public int charWidth(char Pchar)                               //~1212R~
	{                                                              //~1212I~
    	char[] chars=new char[1];
    	chars[0]=Pchar;
        return stringWidth(new String(chars,0,1));                 //~1212I~
	}                                                              //~1212I~
}
