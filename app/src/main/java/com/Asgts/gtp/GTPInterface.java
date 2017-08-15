//*CID://+1Ah0R~:                             update#=    7;       //~1Ah0R~
//***************************************************************************//~v1B6I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//***************************************************************************//~v1B6I~
package com.Asgts.gtp;                                             //~v1B6R~

public interface GTPInterface                                      //~v1B6R~
//{	public int getHandicap ();                                     //~1Ah0R~
{                                                                  //~1Ah0I~
	public int getColor ();
//  public int getRules ();                                        //~1Ah0R~
    public int getBoardSize ();                                    //~1Ah0R~
//  public void gotMove (int color, int pos);                      //~1Ah0R~
//  public void gotMove (int color, int pos,int Pposfrom,int Ppiece,boolean Pdrop);//+1Ah0R~
	public void gotOk ();
	public void gotAnswer (int a);
}
