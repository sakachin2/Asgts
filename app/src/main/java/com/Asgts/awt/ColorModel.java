package com.Asgts.awt;                                              //~2C26R~//+3213R~

public class ColorModel
{//~1120R~
    private static Color RGBdefault=null;                            //~1120I~
    public static int getRGBdefault()                              //~1127R~
    {                            //~1120R~
    	if (RGBdefault == null)
    	{
//        RGBdefault = new DirectColorModel(32,                    //~1120R~
//                          0x00ff0000,   // Red                   //~1120R~
//                          0x0000ff00,   // Green                 //~1120R~
//                          0x000000ff,   // Blue                  //~1120R~
//                          0xff000000    // Alpha                 //~1120R~
//                          );                                     //~1120R~
    		RGBdefault =new Color(0xff,0xff,0xff);            //~1120I~
    	}
		return RGBdefault.getRGB();                               //~1127R~
    }
}
