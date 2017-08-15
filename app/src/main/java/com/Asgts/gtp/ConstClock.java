//*CID://+v1B6R~:                                   update#=   23; //~v1B6I~
//***********************************************************************//~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//***********************************************************************//~v1B6I~
// ConstClock.java

//package net.sf.gogui.game;                                       //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

import com.Asgts.gtp.GoColor;                                      //+v1B6R~

/** Const functions of game.Clock.
    @see Clock */
public interface ConstClock
{
    int getMovesLeft(GoColor color);

    long getTimeLeft(GoColor color);

    TimeSettings getTimeSettings();

    String getTimeString(GoColor color);

    GoColor getToMove();

    boolean getUseByoyomi();

    boolean isInitialized();

    boolean isInByoyomi(GoColor color);

    boolean isRunning();

    boolean lostOnTime(GoColor color);
}
