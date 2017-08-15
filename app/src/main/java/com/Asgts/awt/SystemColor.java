package com.Asgts.awt;                                                //~1108R~//~1109R~//~2C26R~//+3213R~

public final class SystemColor extends Color                       //~1109I~
{                                                                  //~1109I~
 	private int indexvalue;                                        //~1502R~
    public final static int DESKTOP = 0;                           //~1108R~
    public final static int ACTIVE_CAPTION = 1;                    //~1108R~
    public final static int ACTIVE_CAPTION_TEXT = 2;               //~1108R~
    public final static int ACTIVE_CAPTION_BORDER = 3;             //~1108R~
    public final static int INACTIVE_CAPTION = 4;                  //~1108R~
    public final static int INACTIVE_CAPTION_TEXT = 5;             //~1108R~
    public final static int INACTIVE_CAPTION_BORDER = 6;           //~1108R~
    public final static int WINDOW = 7;                            //~1108R~
    public final static int WINDOW_BORDER = 8;                     //~1108R~
    public final static int WINDOW_TEXT = 9;                       //~1108R~
    public final static int MENU = 10;                             //~1108R~
    public final static int MENU_TEXT = 11;                        //~1108R~
    public final static int TEXT = 12;                             //~1108R~
    public final static int TEXT_TEXT = 13;                        //~1108R~
    public final static int TEXT_HIGHLIGHT = 14;                   //~1108R~
    public final static int TEXT_HIGHLIGHT_TEXT = 15;              //~1108R~
    public final static int TEXT_INACTIVE_TEXT = 16;               //~1108R~
    public final static int CONTROL = 17;                          //~1108R~
    public final static int CONTROL_TEXT = 18;                     //~1108R~
    public final static int CONTROL_HIGHLIGHT = 19;                //~1108R~
    public final static int CONTROL_LT_HIGHLIGHT = 20;             //~1108R~
    public final static int CONTROL_SHADOW = 21;                   //~1108R~
    public final static int CONTROL_DK_SHADOW = 22;                //~1108R~
    public final static int SCROLLBAR = 23;                        //~1108R~
    public final static int INFO = 24;                             //~1108R~
    public final static int INFO_TEXT = 25;                        //~1108R~
    public final static int NUM_COLORS = 26;                       //~1108R~
    public final static SystemColor desktop = new SystemColor((byte)DESKTOP);//~1108R~
    public final static SystemColor activeCaption = new SystemColor((byte)ACTIVE_CAPTION);//~1108R~
    public final static SystemColor activeCaptionText = new SystemColor((byte)ACTIVE_CAPTION_TEXT);//~1108R~
    public final static SystemColor activeCaptionBorder = new SystemColor((byte)ACTIVE_CAPTION_BORDER);//~1108R~
    public final static SystemColor inactiveCaption = new SystemColor((byte)INACTIVE_CAPTION);//~1108R~
    public final static SystemColor inactiveCaptionText = new SystemColor((byte)INACTIVE_CAPTION_TEXT);//~1108R~
    public final static SystemColor inactiveCaptionBorder = new SystemColor((byte)INACTIVE_CAPTION_BORDER);//~1108R~
    public final static SystemColor window = new SystemColor((byte)WINDOW);//~1108R~
    public final static SystemColor windowBorder = new SystemColor((byte)WINDOW_BORDER);//~1108R~
    public final static SystemColor windowText = new SystemColor((byte)WINDOW_TEXT);//~1108R~
    public final static SystemColor menu = new SystemColor((byte)MENU);//~1108R~
    public final static SystemColor menuText = new SystemColor((byte)MENU_TEXT);//~1108R~
    public final static SystemColor text = new SystemColor((byte)TEXT);//~1108R~
    public final static SystemColor textText = new SystemColor((byte)TEXT_TEXT);//~1108R~
    public final static SystemColor textHighlight = new SystemColor((byte)TEXT_HIGHLIGHT);//~1108R~
    public final static SystemColor textHighlightText = new SystemColor((byte)TEXT_HIGHLIGHT_TEXT);//~1108R~
    public final static SystemColor textInactiveText = new SystemColor((byte)TEXT_INACTIVE_TEXT);//~1108R~
    public final static SystemColor control = new SystemColor((byte)CONTROL);//~1108R~
    public final static SystemColor controlText = new SystemColor((byte)CONTROL_TEXT);//~1108R~
    public final static SystemColor controlHighlight = new SystemColor((byte)CONTROL_HIGHLIGHT);//~1108R~
    public final static SystemColor controlLtHighlight = new SystemColor((byte)CONTROL_LT_HIGHLIGHT);//~1108R~
    public final static SystemColor controlShadow = new SystemColor((byte)CONTROL_SHADOW);//~1108R~
    public final static SystemColor controlDkShadow = new SystemColor((byte)CONTROL_DK_SHADOW);//~1108R~
    public final static SystemColor scrollbar = new SystemColor((byte)SCROLLBAR);//~1108R~
    public final static SystemColor info = new SystemColor((byte)INFO);//~1108R~
    public final static SystemColor infoText = new SystemColor((byte)INFO_TEXT);//~1108R~
                                                                   //~1127I~
    private static int[] systemColors = {                          //~1108R~
        0xFF005C5C,  // desktop = new Color(0,92,92);              //~1108R~
        0xFF000080,  // activeCaption = new Color(0,0,128);        //~1108R~
        0xFFFFFFFF,  // activeCaptionText = Color.white;           //~1108R~
        0xFFC0C0C0,  // activeCaptionBorder = Color.lightGray;     //~1108R~
        0xFF808080,  // inactiveCaption = Color.gray;              //~1108R~
        0xFFC0C0C0,  // inactiveCaptionText = Color.lightGray;     //~1108R~
        0xFFC0C0C0,  // inactiveCaptionBorder = Color.lightGray;   //~1108R~
        0xFFFFFFFF,  // window = Color.white;                      //~1108R~
        0xFF000000,  // windowBorder = Color.black;                //~1108R~
        0xFF000000,  // windowText = Color.black;                  //~1108R~
        0xFFC0C0C0,  // menu = Color.lightGray;                    //~1108R~
        0xFF000000,  // menuText = Color.black;                    //~1108R~
        0xFFC0C0C0,  // text = Color.lightGray;                    //~1108R~
        0xFF000000,  // textText = Color.black;                    //~1108R~
        0xFF000080,  // textHighlight = new Color(0,0,128);        //~1108R~
        0xFFFFFFFF,  // textHighlightText = Color.white;           //~1108R~
        0xFF808080,  // textInactiveText = Color.gray;             //~1108R~
        0xFFC0C0C0,  // control = Color.lightGray;                 //~1108R~
        0xFF000000,  // controlText = Color.black;                 //~1108R~
        0xFFFFFFFF,  // controlHighlight = Color.white;            //~1108R~
        0xFFE0E0E0,  // controlLtHighlight = new Color(224,224,224);//~1108R~
        0xFF808080,  // controlShadow = Color.gray;                //~1108R~
        0xFF000000,  // controlDkShadow = Color.black;             //~1108R~
        0xFFE0E0E0,  // scrollbar = new Color(224,224,224);        //~1108R~
        0xFFE0E000,  // info = new Color(224,224,0);               //~1108R~
        0xFF000000,  // infoText = Color.black;                    //~1108R~
    };                                                             //~1108R~

    private SystemColor(byte index)                                //~1127R~
	{                                                              //~1127I~
        super(0, 0, 0);                                            //~1108R~
    	indexvalue = index;                                                 //~1108R~//~1502R~
    }                                                              //~1108R~

    public int getARGB()                                           //~1127R~
	{                                                              //~1127I~
    	value=systemColors[indexvalue];	//variable on super        //~1502R~
    	return value;                                              //~1502I~
    }                                                              //~1108R~
}
