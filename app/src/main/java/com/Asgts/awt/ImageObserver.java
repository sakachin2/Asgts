package com.Asgts.awt;                                              //~2C26R~//+3213R~

public interface ImageObserver                   //~1308R~
{                                                                  //~1308I~
    public boolean imageUpdate(Image img, int infoflags,           //~1215I~
			       int x, int y, int width, int height);           //~1215I~
                                                                   //~1215I~
    public static final int WIDTH = 1;                             //~1215I~
    public static final int HEIGHT = 2;                            //~1215I~
    public static final int PROPERTIES = 4;                        //~1215I~
    public static final int SOMEBITS = 8;                          //~1215I~
    public static final int FRAMEBITS = 16;                        //~1215I~
    public static final int ALLBITS = 32;                          //~1215I~
    public static final int ERROR = 64;                            //~1215I~
    public static final int ABORT = 128;                           //~1215I~
}                                                                  //~1215I~
