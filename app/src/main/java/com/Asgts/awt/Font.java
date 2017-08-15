/*
 * @(#)Font.java	1.181 03/01/23
 *
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.Asgts.awt;                                                //~1108R~//~1109R~//~2C26R~//+3213R~
import java.util.Hashtable;                                        //~1108R~

import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class Font                                                  //~1108I~
{
    public static final int PLAIN   = Typeface.NORMAL;             //~1108R~

    public static final int BOLD    = Typeface.BOLD;               //~1108R~

    public static final int ITALIC  = Typeface.ITALIC;             //~1108R~
    public static final int STYLE=(PLAIN|BOLD|ITALIC);             //~1502I~

    protected String name;                                         //~1108R~
    protected Typeface typeface;                                  //~1108I~

    protected int style;                                           //~1108R~

    protected int size;                                            //~1108R~

                                 //~1108R~

    private void initializeFont(Hashtable attributes) {            //~1108R~
        if (this.name == null) {                                   //~1108R~
            this.name = "Default";                                 //~1108R~
        }                                                          //~1108R~
    }                                                              //~1108R~

    public static final String MONOSPACE="Monospaced";             //~1108I~
    public static final String BOLDMONOSPACE="BoldMonospaced";     //~1108I~
    public static final String SANS="SansSerif";                   //~1108I~
    public static final String SERIF="Serif";                      //~1108I~
                                                                   //~1428I~
    public Font(String name, int style, int size) {                //~1108R~
    	this.typeface= name2typeface(name);                        //~1108I~
    	this.name = name;                                          //~1108R~
    	this.style = (style & ~STYLE);                             //~1502R~
    	this.size = size;                                          //~1108R~
                                //~1108R~
    	initializeFont(null);                                      //~1108R~
    }                                                              //~1108R~
    public Font(Font Pfont,float Psize) {                             //~1428I~
    	this.typeface= name2typeface(Pfont.name);                  //~1428I~
    	this.name = Pfont.name;                                    //~1428I~
    	this.style = (style & ~0x03) == 0 ? style : 0;             //~1428I~
    	this.size = (int)Psize;                                          //~1428I~
                                   //~1428I~
    	initializeFont(null);                                      //~1428I~
    }                                                              //~1428I~
    private Typeface name2typeface(String name)                    //~1108I~
    {                                                              //~1108I~
    	Typeface tf;                                               //~1108I~
    	if (MONOSPACE.equals(name))                                //~1108I~
        	tf=Typeface.MONOSPACE;                                 //~1108I~
        else                                                       //~1108I~
    	if (SANS.equals(name))                                     //~1108R~
        	tf=Typeface.SANS_SERIF;                                      //~1108I~
        else                                                       //~1108I~
    	if (SERIF.equals(name))                                    //~1108R~
        	tf=Typeface.SERIF;                                     //~1108R~
        else                                                       //~1108I~
        	tf=Typeface.DEFAULT;                                   //~1108R~
        return tf;                                                 //~1108I~
    }//name2typeface                                               //~1108I~

    public int getStyle() {                                        //~1108R~
    	return style;                                              //~1108R~
    }                                                              //~1108R~
    public Typeface getTypeface() {                                //~1108I~
    	return typeface;                                           //~1108I~
    }                                                              //~1108I~
    public Typeface getTypefaceStyle() {                           //~1109I~
    	return Typeface.create(typeface,style);                    //~1109I~
    }                                                              //~1109I~

    public int getSize() {                                         //~1108R~
    return size;                                                   //~1108R~
    }                                                              //~1108R~
	public void setFont(TextView Pview)                                     //~1111I~
    {                                                              //~1111I~
    	if (getTypeface()!=null);  //style:bold,italic,..|typeface:monospace,serif,...//~1111R~
        	Pview.setTypeface(getTypefaceStyle());                 //~1111R~
        if (getSize()!=0);                                         //~1111R~
        	Pview.setTextSize(getSize());                          //~1111R~
    }                                                              //~1111I~
	public void setFont(Button Pview)                              //~1111I~
    {                                                              //~1111I~
    	setFont((TextView)Pview);                                  //~1111I~
    }                                                              //~1111I~
}
