//*CID://+v1B6R~:                             update#=    8;       //~v1B6I~
//*************************************************************************//~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*************************************************************************//~v1B6I~
// StreamDiscard.java

//package net.sf.gogui.util;                                       //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

import java.io.InputStream;

/** Thread discarding an output stream. */
public class StreamDiscard
    extends Thread
{
    public StreamDiscard(InputStream src)
    {
        m_src = src;
    }

    /** Run method.
        Exceptions caught are written to stderr. */
    public void run()
    {
        try
        {
            byte buffer[] = new byte[1024];
            while (true)
            {
                int n = m_src.read(buffer);
                if (n < 0)
                    break;
                if (n == 0)
                {
                    // Not sure if this is necessary.
                    sleep(100);
                    continue;
                }
            }
        }
        catch (Throwable e)
        {
            StringUtil.printException(e);
        }
    }

    private final InputStream m_src;
}
