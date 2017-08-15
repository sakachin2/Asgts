//*CID://+1Ah0R~: update#=  141;                                   //~1Ah0R~
//*********************************                                //~@@@1I~
//1Ah0 2016/10/15 add robot:bonanza                                //~1Ah0I~
//*********************************                                //~@@@1I~

package com.Asgts.gtp;                                             //~1AfmR~

import jagoclient.Dump;
import jagoclient.board.Board;
import jagoclient.board.Notes;

import java.util.ArrayList;

import com.Asgts.AG;

//*****************************************************************//~v1B6I~
class GtpGoParm
{                                       //~1Ah0R~
    	public int color,size,totaltime,extratime,gameoptions,handicap;//~1Ah0I~
//  	public boolean verbose;                                    //~1Ah0R~
    	public String yourname="",opponentname="";                 //~1Ah0I~
        public boolean forReload;                         //~1Ah0R~
        public ArrayList<String> subcmdList;                       //~1Ah0I~
        public Notes reloadNotes;                                 //~1Ah0I~
        public int limitMode,limitTimeTotal,limitTimeExtra,limitTimeDepth;//~1Ah0I~
//      public GtpGoParm(int Pcolor,int Psize,int Ptotaltime,int Pextratime,int Pgameoptions,int Phandicap,boolean Pverbose)//~1Ah0R~
        public GtpGoParm(int Pcolor,int Psize,int Ptotaltime,int Pextratime,int Pgameoptions,int Phandicap)//~1Ah0I~
        {                                                          //~v1BaI~
        	color=Pcolor; size=Psize; totaltime=Ptotaltime; extratime=Pextratime; gameoptions=Pgameoptions;handicap=Phandicap;//~1Ah0R~
//      	verbose=Pverbose;                                      //~1Ah0R~
        }                                                          //~v1BaI~
        public GtpGoParm()                                         //~1Ah0R~
        {                                                          //~1Ah0I~
        	forReload=true;                                        //~1Ah0I~
        }                                                          //~1Ah0I~
        public void setName(String Pyou,String Popponent)          //~1Ah0I~
        {                                                          //~1Ah0I~
        	yourname=Pyou; opponentname=Popponent;                 //~1Ah0I~
        }                                                          //~1Ah0I~
        public void setBonanzaOption(ArrayList<String> Psubcmdlist,int Plimitmode,int Plimittimetotal,int Plimittimeextra,int Plimittimedepth)//~1Ah0I~
        {                                                          //~1Ah0I~
        	subcmdList=Psubcmdlist;                                //~1Ah0I~
            limitMode=Plimitmode;                                  //~1Ah0I~
            limitTimeTotal=Plimittimetotal; limitTimeExtra=Plimittimeextra; limitTimeDepth=Plimittimedepth;//~1Ah0I~
            if (Dump.Y) Dump.println("GtpGoParm:setBonanzaOption limitmode="+limitMode+",total="+limitTimeTotal+",extra="+limitTimeExtra+",depth="+limitTimeDepth);//~1Ah0I~
        }                                                          //~1Ah0I~
        public void reload(Notes Pnotes)                           //~1Ah0I~
        {                                                          //~1Ah0I~
        	reloadNotes=Pnotes;                                    //~1Ah0I~
                                                                   //~1Ah0I~
        	color=Pnotes.yourcolor;                                //~1Ah0I~
 			size=AG.BOARDSIZE_SHOGI;                               //~1Ah0I~
 			totaltime=Pnotes.totalTime;                            //~1Ah0I~
 			extratime=Pnotes.extraTime;                            //~1Ah0I~
 			gameoptions=Pnotes.gameoptions;                        //~1Ah0I~
			handicap=Pnotes.handicap;                              //~1Ah0I~
            if (color==Board.COLOR_BLACK)                         //~1Ah0I~
            {                                                      //~1Ah0I~
            	yourname=Pnotes.blackName;                         //~1Ah0I~
                opponentname=Pnotes.whiteName;                     //~1Ah0I~
            }                                                      //~1Ah0I~
            else                                                   //~1Ah0I~
            {                                                      //~1Ah0I~
            	yourname=Pnotes.whiteName;                         //~1Ah0I~
                opponentname=Pnotes.blackName;                     //~1Ah0I~
            }                                                      //~1Ah0I~
            subcmdList=Pnotes.subcmdList;                          //~1Ah0I~
            if (subcmdList==null)                                  //+1Ah0I~
            	subcmdList=new ArrayList<String>();                //+1Ah0I~
            limitMode=Pnotes.limitMode;                            //~1Ah0I~
            limitTimeTotal=Pnotes.limitTimeTotal;                  //~1Ah0I~
            limitTimeExtra=Pnotes.limitTimeExtra;                  //~1Ah0I~
            limitTimeDepth=Pnotes.limitTimeDepth;                  //~1Ah0I~
            if (Dump.Y) Dump.println("GtpGoParm:reload color="+color+",totaltime="+totaltime+",extratime="+extratime+",gameoptions="+Integer.toHexString(gameoptions)+",handicap="+Integer.toHexString(handicap));//~1Ah0I~
            if (Dump.Y) Dump.println("GtpGoParm:reload limitMode="+limitMode+",total="+limitTimeTotal+",extra="+limitTimeExtra+",depth="+limitTimeDepth);//~1Ah0I~
        }                                                          //~1Ah0I~
}
