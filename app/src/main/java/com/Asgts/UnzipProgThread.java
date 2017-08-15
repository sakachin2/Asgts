//*CID://+1Ah0R~:                             update#=   59;       //~1Ah0R~
//**********************************************************************//~vag0I~
//1Ah0:161015 bonanza                                              //~1Ah0I~
//vag0:120719 (Axe)function to download asset from web             //~vag0I~
//**********************************************************************//~vag0I~
//*DownloaderThread                                                //~vag0R~
//**********************************************************************//~vag0I~
package com.Asgts;                                                 //~1Ah0R~
import android.app.Activity;                                       //~vag0I~
import jagoclient.Dump;

import java.io.File;
import java.util.ArrayList;

import com.Asgts.R;

import android.os.Handler;
import android.os.Message;
public class UnzipProgThread extends Thread                        //~1Ah0R~
{
	private static final int INTERVAL=1000;    //ms                //~1Ah0R~
	
    private Activity parentActivity;                               //~vag0I~
	private Handler activityHandler;                               //~vag0I~
	private String downloadUrl;
	private String localFile;                                      //~vag0I~
	private String localDir;                                       //~1Ah0I~
	private long unzipSize;                                        //~1Ah0I~
	private boolean swCompleted;                                   //~1Ah0I~
	private ArrayList<String> nameList;                            //~1Ah0I~
    private StringBuffer wksb=new StringBuffer(4096);                           //~1Ah0I~
	
    public UnzipProgThread(Activity inParentActivity,Handler Phandler,String Ppath,String PlocalFile,long PunzipSize,ArrayList<String> Plist)//~1Ah0R~
	{
    	nameList=Plist;                                            //~1Ah0I~
    	unzipSize=PunzipSize;                                      //~1Ah0I~
    	localDir=Ppath;                                            //~1Ah0R~
    	localFile=PlocalFile;                                      //~1Ah0I~
		parentActivity = inParentActivity;
		activityHandler= Phandler;                                 //~vag0I~
	}
    public void completed()                                        //~1Ah0I~
    {                                                              //~1Ah0I~
    	swCompleted=true;                                          //~1Ah0I~
    }                                                              //~1Ah0I~
	
	@Override
	public void run()
	{
		String fileName;
		Message msg;
		
		// we're going to connect now
		fileName=localFile;                                        //~1Ah0R~
		try
		{
			// notify download start
    		int fileSizeInKB = (int)(unzipSize/1024);                           //~vag0I~//+1Ah0R~
    		msg = Message.obtain(activityHandler,                  //~vag0I~
    				UnzipProgMsg.MESSAGE_DOWNLOAD_STARTED,         //~1Ah0R~
					fileSizeInKB, 0, fileName);
    		activityHandler.sendMessage(msg);                      //~vag0I~
			
			// start download
    		long totalRead=0;                                      //~1Ah0I~
    		while(!swCompleted)                                    //~1Ah0R~
			{
				
				// update progress bar
    			totalRead=getunzipsize();                          //~1Ah0R~
    			int totalReadInKB=(int)(totalRead/1024);           //~1Ah0I~
                if (Dump.Y) Dump.println("UnzipProgThread:run totalrRead="+totalRead);//~1Ah0R~
    			msg = Message.obtain(activityHandler,              //~vag0I~
    					UnzipProgMsg.MESSAGE_UPDATE_PROGRESS_BAR,  //~1Ah0R~
						totalReadInKB, 0,null);                    //~1Ah0R~
    			activityHandler.sendMessage(msg);                  //~vag0I~
                try                                                //~1Ah0I~
                {                                                  //~1Ah0I~
                	Thread.sleep(INTERVAL);                        //~1Ah0I~
                }                                                  //~1Ah0I~
                catch(InterruptedException e)                      //~1Ah0I~
                {                                                  //~1Ah0I~
                }                                                  //~1Ah0I~
                                                                   //~1Ah0I~
			}
            if (Dump.Y) Dump.println("UnzipProgThread:run completed totalrRead="+totalRead);//~1Ah0R~
			
			
				// notify completion
    			msg = Message.obtain(activityHandler,              //~vag0I~
  						UnzipProgMsg.MESSAGE_DOWNLOAD_COMPLETE);   //~1Ah0R~
    			activityHandler.sendMessage(msg);                  //~vag0I~
		}
		catch(Exception e)
		{
	        Dump.println(e,"DownloaderThread otherEE:"+downloadUrl);//~vag0I~
			String errMsg = parentActivity.getString(R.string.error_message_general);
            String info=e.getMessage();                            //~vag0I~
            if (info!=null)                                        //~vag0I~
            	errMsg+=":"+info;                                  //~vag0I~
    		msg = Message.obtain(activityHandler,                  //~vag0I~
    				UnzipProgMsg.MESSAGE_ENCOUNTERED_ERROR,        //~1Ah0R~
					0, 0, errMsg);
    		activityHandler.sendMessage(msg);                      //~vag0I~
		}
	}
    private long getunzipsize()                                    //~1Ah0I~
    {                                                              //~1Ah0I~
    	long total=0;                                              //~1Ah0I~
        wksb.setLength(0);                                         //~1Ah0I~
        wksb.append(localDir);                                     //~1Ah0I~
        int pos=wksb.length();                                     //~1Ah0I~
    	for (int ii=0;ii<nameList.size();ii++)                         //~1Ah0I~
        {                                                          //~1Ah0I~
        	String name=nameList.get(ii);                          //~1Ah0I~
	        wksb.setLength(pos);                                   //~1Ah0I~
	        wksb.append(name);                                     //~1Ah0I~
            name=new String(wksb);                                 //~1Ah0I~
            File f=new File(name);                                 //~1Ah0I~
            long sz=f.length();                                    //~1Ah0I~
            if (Dump.Y) Dump.println("UnzipProgThread:getunzipsize name="+name+",sz="+sz+",total="+total);//~1Ah0R~
            total+=sz;                                             //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("UnzipProgThread:getunzipsize return total="+total);//~1Ah0R~
        return total;                                              //~1Ah0I~
    }                                                              //~1Ah0I~
	public void changeMaxvalue(long Pmaxvalue,ArrayList<String> Pnamelist)//~1Ah0R~
	{                                                              //~1Ah0I~
        nameList=Pnamelist;                                        //~1Ah0I~
		String fileName;                                           //~1Ah0I~
		fileName=localFile;                                        //~1Ah0I~
		Message msg;                                               //~1Ah0I~
    	int fileSizeInKB = (int)(Pmaxvalue/1024);                  //~1Ah0I~
    	msg = Message.obtain(activityHandler,                      //~1Ah0I~
    			UnzipProgMsg.MESSAGE_UNZIPSIZE_GOTTEN,             //~1Ah0R~
				fileSizeInKB, 0, fileName);                        //~1Ah0R~
    	activityHandler.sendMessage(msg);                          //~1Ah0I~
                                                                   //~1Ah0I~
	}                                                              //~1Ah0I~
}
