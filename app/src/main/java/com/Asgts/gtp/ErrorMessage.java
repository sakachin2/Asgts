//*CID://+v1B6R~: update#=   2;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// ErrorMessage.java
package com.Asgts.gtp;                                             //+v1B6R~


/** Error with error message.
    ErrorMessage are exceptions with a message meaningful for presentation
    to the user. */
public class ErrorMessage
    extends Exception
{
    /** Constructor.
        @param message The error message text. */
    public ErrorMessage(String message)
    {
        super(message);
    }
}
