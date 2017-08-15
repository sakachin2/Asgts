//*CID://+v1B6R~: update#=   2;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// ConstPointList.java

//package net.sf.gogui.go;                                         //~4807R~
package com.Asgts.gtp;                                            //~v1B6I~//+v1B6R~

import java.util.Iterator;

/** Const functions of go.PointList.
    @see PointList */
public interface ConstPointList
    extends Iterable<GoPoint>
{
    boolean contains(Object elem);

    boolean equals(Object object);

    GoPoint get(int index);

    int hashCode();

    boolean isEmpty();

    Iterator<GoPoint> iterator();

    int size();

    String toString();
}
