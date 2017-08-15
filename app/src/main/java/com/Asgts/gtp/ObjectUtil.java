//*CID://+v1B6R~: update#=   2;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// ObjectUtil.java

//package net.sf.gogui.util;                                       //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

/** Utils for using class java.lang.Object. */
public final class ObjectUtil
{
    /** Compare including the case that arguments can be null. */
    public static boolean equals(Object object1, Object object2)
    {
        if (object1 == null && object2 == null)
            return true;
        if (object1 == null && object2 != null)
            return false;
        if (object1 != null && object2 == null)
            return false;
        assert object1 != null && object2 != null;
        return object1.equals(object2);
    }

    /** Make constructor unavailable; class is for namespace only. */
    private ObjectUtil()
    {
    }
}
