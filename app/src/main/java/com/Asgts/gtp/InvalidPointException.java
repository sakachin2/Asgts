//*CID://+v1B6R~: update#=   2;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// InvalidPointException.java

//package net.sf.gogui.go;                                         //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

/** Thrown if parsing a string representation of a GoPoint fails. */
public class InvalidPointException
    extends Exception
{
    /** Constructor.
        @param text The text that could not be parsed as a point. */
    public InvalidPointException(String text)
    {
        super("Invalid point \"" + text + "\"");
    }
}
