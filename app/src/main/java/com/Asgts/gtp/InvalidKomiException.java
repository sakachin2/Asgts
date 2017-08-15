//*CID://+v1B6R~: update#=   4;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// InvalidKomiException.java

//package net.sf.gogui.go;                                         //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

//import net.sf.gogui.util.ErrorMessage;                           //~v1B6R~
import com.Asgts.gtp.ErrorMessage;                                 //+v1B6R~

/** Exception thrown if parsing a komi from a string fails. */
public class InvalidKomiException
    extends ErrorMessage
{
    public InvalidKomiException(String s)
    {
        super("Invalid komi: " + s);
    }
}
