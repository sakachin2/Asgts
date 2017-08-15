//*CID://+v1B6R~: update#=   2;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// LineReader.java

//package net.sf.gogui.util;                                       //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

/** Allows to read lines from a buffer without blocking. */
public class LineReader
{
    /** Add text to buffer. */
    public void add(String s)
    {
        m_buffer.append(s);
    }

    /** Check if the buffer contains at least one line. */
    public boolean hasLines()
    {
        return m_buffer.toString().contains("\n");
    }

    public String getLine()
    {
        String s = m_buffer.toString();
        int pos = s.indexOf('\n');
        if (pos < 0)
            return "";
        String result = s.substring(0, pos + 1);
        m_buffer.delete(0, pos + 1);
        return result;
    }

    private final StringBuilder m_buffer = new StringBuilder(1024);
}
