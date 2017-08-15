//*CID://+1Ah0R~:                             update#=  171;       //~1Ah0R~
//***********************************************************************//~v1A0I~
//1Ah0 2015/05/04 bonanza                                          //~1Ah0I~
//1Ab4 2015/05/04 (Bug of 1Aa8) FreebiardQuestion NPE;FreeboardQuestion extends GameQuestion and resid should be of FBQ)//~1Ab4R~
//1Aa8 2015/04/20 put in layout the gamequestion for mdpi          //~1Aa8I~
//1A49 2014/11/01 opponent name for local match(for savefile when asgts)(Ahsv:1A56,Ajagot1w:v1D9)//~1A49I~
//1A3c 2013/06/04 set default totaltime to 60min                   //~1A3cI~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2j 2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//                that cause RunTimEException not looper           //~1A2jI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A1h 2013/03/18 default gameoption:bigtimer                      //~1A1hI~
//1A12 2013/03/08 Option to diaplay time is for tiomeout=0(if not 0 diaplay timer)//~1A12I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//***********************************************************************//~v1A0I~
package com.Asgts;                                             //~1Ah0R~

import java.util.ArrayList;

import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.Prop;
import com.Asgts.URunnable;
import com.Asgts.URunnableI;
import com.Asgts.UnzipProgMsg;
import com.Asgts.UnzipProgThread;
import jagoclient.Dump;

public class Unzipper                                              //~1Ah0R~
	implements URunnableI                                          //~1Ah0I~
{                                                                  //~@@@@I~
    private long zipSize,unzipSize;                                //~1Ah0R~
    private UnzipProgMsg unzipProgMsg;                             //~1Ah0R~
    private UnzipProgThread unzipProgThread;                       //~1Ah0I~
    private String path,assetFilename;                              //~1Ah0R~
    private int chmod;                                             //~1Ah0R~
	private ArrayList<String> nameList;                         //~1Ah0I~
    private UnzipperI callback;                                    //~1Ah0I~
//***************************************************************  //~1Ah0R~
	public Unzipper(UnzipperI Pcallback,String Ppath,String Passetfnm,int Pchmodmask,long Pzipsize)//~1Ah0I~
    {                                                              //~1Ah0M~
    	if (Dump.Y) Dump.println("Unzipper:unzipAsset path="+Ppath+",asset name="+Passetfnm);//~1Ah0I~
        callback=Pcallback;                                        //~1Ah0I~
        path=Ppath;                                                //~1Ah0I~
        assetFilename=Passetfnm;                                   //~1Ah0I~
        chmod=Pchmodmask;                                          //~1Ah0I~
        zipSize=Pzipsize;                                          //~1Ah0I~
                                                                   //~1Ah0I~
        showProgressDialog();                                      //~1Ah0I~
		URunnable.setRunFuncSubthread(this,0/*deley*/,this/*parmobj*/,0/*parmint*/);//~1Ah0M~
    }                                                              //~1Ah0M~
    //********************************************                 //~1Ah0I~
    public void dismissMsgDialog()                                 //~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("Unzipper:dismissMsgDialog");     //~1Ah0R~
        unzipProgThread.completed();                               //~1Ah0I~
    }                                                              //~1Ah0I~
    //********************************************                 //~1Ah0I~
	public void showProgressDialog()                               //~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpgameQuestion:showProgressDialog fnm="+assetFilename);//~1Ah0R~
		nameList=new ArrayList<String>();                          //+1Ah0M~
    	unzipProgMsg=UnzipProgMsg.onCreate(AG.activity,path,assetFilename,zipSize,nameList);//~1Ah0R~
        unzipProgThread=(UnzipProgThread)unzipProgMsg.downloaderThread;//~vag0I~//~1Ah0R~
        unzipProgThread.start();                                               //~vag0I~//~1Ah0R~
    }                                                              //~1Ah0I~
    //*****************************************************************//~1Ah0I~
	private long getAssetUnzipSize()                               //~1Ah0R~
    {                                                              //~1Ah0I~
		nameList=new ArrayList<String>();	//another namelist because it was chekking by ProgThread for zipsize//+1Ah0I~
    	long value=Prop.getAssetUnzipSize(assetFilename,nameList);       //~1Ah0R~
        if (Dump.Y) Dump.println("Unzipper:getAssetUnzipSize fnm="+assetFilename+",size="+value);//~1Ah0R~
        return value;                                              //~1Ah0I~
    }                                                              //~1Ah0I~
    //********************************************                 //~1Ah0I~
    //*Get unzipsie from file                                      //~1Ah0I~
    //*Not Used(entry name not gotten                              //~1Ah0I~
    //********************************************                 //~1Ah0I~
	private long getAssetUnzipSize(String Pfnm)                         //~1Ah0I~
    {                                                              //~1Ah0I~
    	String sizefile=Pfnm+".size";                              //~1Ah0I~
        if (Dump.Y) Dump.println("Unzipper:getAssetUnzipSize fnm="+Pfnm);//~1Ah0R~
		String record=Prop.getAssetRecord(sizefile);                   //~1Ah0I~
	    long value;                                                //~1Ah0I~
        if (record!=null)                                          //~1Ah0I~
        {                                                          //~1Ah0I~
        	try                                                    //~1Ah0I~
            {                                                      //~1Ah0I~
            	int pos=record.indexOf(' ');                        //~1Ah0I~
                if (pos>=0)                                           //~1Ah0I~
                	record=record.substring(0,pos);                //~1Ah0I~
	    		value=Long.parseLong(record);                      //~1Ah0R~
            	if (Dump.Y) Dump.println("Unzipper:getAssetUnzipSize size="+value);//~1Ah0R~
            }                                                      //~1Ah0I~
            catch(Exception e)                                     //~1Ah0I~
            {                                                      //~1Ah0I~
            	Dump.println(e,"Unzipper:getAssetUnzipSzie");      //~1Ah0R~
                value=-1;                                          //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        	value=-1;                                              //~1Ah0I~
        return value;
    }//~1Ah0I~
    //********************************************                 //~1Ah0I~
    public void runFunc(Object Pparmobj,int Pparmint)              //~1Ah0I~
    {                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("Unzipper runFunc parmint="+Pparmint);//~1Ah0R~
        unzipSize=getAssetUnzipSize();    //get from another file//~1Ah0R~
        if (unzipSize>0)	//failed to get unzipsize                  //~1Ah0I~
	    	unzipProgThread.changeMaxvalue(unzipSize,nameList);    //~1Ah0R~
		Prop.unzipAsset(path,assetFilename,chmod);                      //~1Ah0R~
    	if (Dump.Y) Dump.println("Unzipper runFunc call unzipasset end");//~1Ah0R~
        unzipCompleted();                                          //~1Ah0R~
    }                                                              //~1Ah0I~
    //********************************************                 //~1Ah0I~
	public void unzipCompleted()                                   //~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("Unzipper:unzipCompleted");       //~1Ah0R~
    	dismissMsgDialog();                                        //~1Ah0R~
//      doAction(AG.resource.getString(R.string.StartGame));       //~1Ah0R~
        callback.unzipCompleted();                                 //~1Ah0I~
    }                                                              //~1Ah0I~
}

