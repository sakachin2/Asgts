//*CID://+v1B6R~: update#=   2;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// GtpResponseFormatError.java

//package net.sf.gogui.gtp;                                        //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

/** Error used if parsing a GTP response fails.
    This error is used if the response to a GTP command is expected to be
    in a particular format (e.g. a point), but is in a different format. */
public class GtpResponseFormatError
    extends Exception
{
    public GtpResponseFormatError(String s)
    {
        super(s);
    }
}
