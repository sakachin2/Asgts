//*CID://+1A6fR~:                             update#=    1;       //+1A6fI~
//**********************************************************************************//+1A6fI~
//1A6f 2015/02/13 support custom layout of ListView for BluetoothConnection to show available/paired status//+1A6fI~
//**********************************************************************************//+1A6fI~
package com.Asgts.awt;                                                //~1108R~//~1109R~//~2C26R~//~3213R~

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

//wraper of awt.Color
public class Color extends Drawable/* view.getBackground returns Drawable*/ //android.graphics.Color                  //~1127R~//~1214R~
{   
    public final static Color white     = new Color(255, 255, 255);//~1127I~
    public final static Color gray      = new Color(128, 128, 128);//~1108R~//~1127I~
    public final static Color black     = new Color(0, 0, 0);      //~1127I~
    public final static Color red       = new Color(255, 0, 0);    //~1127I~
    public final static Color green     = new Color(0, 255, 0);    //~1127I~
    public final static Color blue      = new Color(0, 0, 255);    //~1127I~
    public final static Color pink      = new Color(255, 175, 175);//~1127I~
    public final static Color darkGray  = new Color(64, 64, 64);   //~1212I~
    public final static Color yellow    = new Color(255, 255, 0);  //~3325I~
    public final static Color orange    = new Color(255,100, 0);   //+1A6fI~
                                                                   //~1127I~
    private static final double FACTOR = 0.7;                      //~1108R~//~1127M~
//***********************************                              //~1127I~
	protected int value;
//***********************************                              //~1127I~
	public Color(int r,int g,int b)                     //~1127R~
    {                                                              //~1127I~
    	value=android.graphics.Color.argb(0xff,r,g,b);                                   //~1127R~
    }
	public Color(int Prgb)                                         //~1219I~
    {                                                              //~1219I~
    	value=Prgb|0xff000000;                                     //~1219I~
    }                                                              //~1219I~
	public Color(float r,float g,float b)                          //~1214I~
    {                                                              //~1214I~
    	this((int)r,(int)g,(int)b);                         //~1214I~
    }                                                              //~1214I~
	public int getRGB()                                            //~1127R~
	{
		return getARGB();                                          //~1417R~
	}
	public int getARGB()                                           //~1417I~
	{                                                              //~1417I~
		return value;                                              //~1417I~
	}                                                              //~1417I~
    public int getRed()                                            //~1127I~
	{                                                              //~1127I~
    	return (getRGB() >> 16) & 0xFF;                            //~1127R~
    }                                                              //~1127I~
    public int getGreen()                                          //~1127I~
	{                                                              //~1127I~
    	return (getRGB() >> 8) & 0xFF;                             //~1127R~
    }                                                              //~1127I~
    public int getBlue()                                           //~1127I~
	{                                                              //~1127I~
    	return (getRGB() >> 0) & 0xFF;                             //~1127R~
    }                                                              //~1127I~
    public int getAlpha()                                          //~1127I~
	{                                                              //~1127I~
        return (getRGB() >> 24) & 0xff;                            //~1127R~
    }                                                              //~1127I~
//**********************                                           //~1127I~
    public Color brighter() {                                      //~1127I~
        int r = getRed();                                          //~1127I~
        int g = getGreen();                                        //~1127I~
        int b = getBlue();                                         //~1127I~
                                                                   //~1127I~
        /* From 2D group:                                          //~1127I~
         * 1. black.brighter() should return grey                  //~1127I~
         * 2. applying brighter to blue will always return blue, brighter//~1127I~
         * 3. non pure color (non zero rgb) will eventually return white//~1127I~
         */                                                        //~1127I~
        int i = (int)(1.0/(1.0-FACTOR));                           //~1127I~
        if ( r == 0 && g == 0 && b == 0) {                         //~1127I~
           return new Color(i, i, i);                              //~1127I~
        }                                                          //~1127I~
        if ( r > 0 && r < i ) r = i;                               //~1127I~
        if ( g > 0 && g < i ) g = i;                               //~1127I~
        if ( b > 0 && b < i ) b = i;                               //~1127I~
                                                                   //~1127I~
        return new Color(Math.min((int)(r/FACTOR), 255),           //~1127I~
                         Math.min((int)(g/FACTOR), 255),           //~1127I~
                         Math.min((int)(b/FACTOR), 255));          //~1127I~
    }                                                              //~1127I~
//**********************                                           //~1127I~
    public Color darker() {                                        //~1127I~
    return new Color(Math.max((int)(getRed()  *FACTOR), 0),        //~1127I~
             Math.max((int)(getGreen()*FACTOR), 0),                //~1127I~
             Math.max((int)(getBlue() *FACTOR), 0));               //~1127I~
    }                                                              //~1127I~
	@Override
	public void draw(Canvas arg0)
	{
	}
	@Override
	public int getOpacity()
	{
		return 0;
	}
	@Override
	public void setAlpha(int alpha)
	{
	}
	@Override
	public void setColorFilter(ColorFilter cf)
	{
	}
	public void setBackground(View Pview)                          //~1216I~
	{                                                              //~1216I~
    	Pview.setBackgroundColor(value);                           //~1216I~
	}                                                              //~1216I~
	public void setTextColor(View Pview)                           //~1312I~
	{                                                              //~1312I~
    	((TextView)Pview).setTextColor(value);                                 //~1312I~
	}                                                              //~1312I~

}
