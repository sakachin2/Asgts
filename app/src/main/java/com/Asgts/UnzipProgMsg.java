//*CID://+1Ah0R~:                             update#=   56;       //~1Ah0R~
//**********************************************************************//~vag0I~
//1Ah0:161015 bonanza                                              //~1Ah0I~
//vag0:120719 (Axe)function to download asset from web             //~vag0I~
//**********************************************************************//~vag0I~
//*Download control                                                //~vag0R~
//**********************************************************************//~vag0I~
package com.Asgts;                                                 //~1Ah0R~
import jagoclient.Dump;

import java.util.ArrayList;

import com.Asgts.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
public class UnzipProgMsg                                 //~vag0I~//~1Ah0R~
{
	// Used to communicate state changes in the DownloaderThread
	public static final int MESSAGE_DOWNLOAD_STARTED = 1000;
	public static final int MESSAGE_DOWNLOAD_COMPLETE = 1001;
	public static final int MESSAGE_UPDATE_PROGRESS_BAR = 1002;
//    public static final int MESSAGE_DOWNLOAD_CANCELED = 1003;    //~1Ah0R~
//    public static final int MESSAGE_CONNECTING_STARTED = 1004;   //~1Ah0R~
	public static final int MESSAGE_ENCOUNTERED_ERROR = 1005;
//    public static final int MESSAGE_DOWNLOAD_COMPLETE2= 1006;      //~vag0I~//~1Ah0R~
	public static final int MESSAGE_UNZIPSIZE_GOTTEN = 1007;       //~1Ah0I~
	
	// instance variables
    private Activity thisActivity;                                 //~vag0I~
    public  Thread downloaderThread;                               //~vag0I~
	private ProgressDialog progressDialog;
	private int maxValue;                                          //~1Ah0I~
	
    /** Called when the activity is first created. */
	
//  public static AndroidFileDownloader onCreate(Activity Pactivity,String Purl,String Plocalfile)//~vag0R~//~1Ah0R~
    public static UnzipProgMsg onCreate(Activity Pactivity,String Ppath,String Plocalfile,long Punzipsize,ArrayList<String> Plist)//~1Ah0R~
    {                                                              //~vag0I~
    	if (Dump.Y) Dump.println("UnzipProgMsg localfile="+Plocalfile);//~1Ah0R~
//  	AndroidFileDownloader downloader=new AndroidFileDownloader();//~vag0R~//~1Ah0R~
    	UnzipProgMsg downloader=new UnzipProgMsg();                //~1Ah0R~
        downloader.thisActivity = Pactivity;                       //~vag0R~
//      downloader.downloaderThread=new DownloaderThread(Pactivity,downloader.activityHandler,Purl,Plocalfile);//~vag0R~//~1Ah0R~
        downloader.downloaderThread=new UnzipProgThread(Pactivity,downloader.activityHandler,Ppath,Plocalfile,Punzipsize,Plist);//~1Ah0R~
        return downloader;                                         //~vag0I~
    }                                                              //~vag0I~
                                                                   //~vag0I~
	public Handler activityHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				/*
				 * Handling MESSAGE_UPDATE_PROGRESS_BAR:
				 * 1. Get the current progress, as indicated in the arg1 field
				 *    of the Message.
				 * 2. Update the progress bar.
				 */
				case MESSAGE_UPDATE_PROGRESS_BAR:
					if(progressDialog != null)
					{
						int currentProgress = msg.arg1;
                        if (Dump.Y) Dump.println("UnzipMsgThread:activityHandler UPDATE %="+(maxValue!=0?(currentProgress*100)/maxValue:-1)+",current="+currentProgress+",max="+maxValue);//+1Ah0I~
						progressDialog.setProgress(currentProgress);
					}
					break;
				
					
				/*
				 * Handling MESSAGE_DOWNLOAD_STARTED:
				 * 1. Create a progress bar with specified max value and current
				 *    value 0; assign it to progressDialog. The arg1 field will
				 *    contain the max value.
				 * 2. Set the title and text for the progress bar. The obj
				 *    field of the Message will contain a String that
				 *    represents the name of the file being downloaded.
				 * 3. Set the message that should be sent if dialog is canceled.
				 * 4. Make the progress bar visible.
				 */
				case MESSAGE_UNZIPSIZE_GOTTEN:                     //~1Ah0I~
					// obj will contain a String representing the file name//~1Ah0I~
					if(msg.obj != null && msg.obj instanceof String)//~1Ah0I~
					{                                              //~1Ah0I~
 	 					maxValue = msg.arg1;                       //~1Ah0R~
                        if (Dump.Y) Dump.println("UnzipMsgThread:activityHandler UNZIPSIZE_GOTTEN max="+maxValue);//+1Ah0I~
						String fileName = (String) msg.obj;        //~1Ah0I~
						String pdTitle = thisActivity.getString(R.string.progress_dialog_title_unzipping);//~1Ah0R~
						String pdMsg = thisActivity.getString(R.string.progress_dialog_message_prefix_unzipping,maxValue);//~1Ah0R~
						pdMsg += " " + fileName;                   //~1Ah0I~
                                                                   //~1Ah0I~
						dismissCurrentProgressDialog();            //~1Ah0I~
						progressDialog = new ProgressDialog(thisActivity);//~1Ah0I~
						progressDialog.setTitle(pdTitle);          //~1Ah0I~
						progressDialog.setMessage(pdMsg);          //~1Ah0I~
						progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//~1Ah0I~
						progressDialog.setProgress(0);             //~1Ah0I~
						progressDialog.setMax(maxValue);           //~1Ah0I~
//  					progressDialog.setCancelable(true);        //~1Ah0R~
    					progressDialog.setCancelable(false);       //~1Ah0I~
						progressDialog.show();                     //~1Ah0I~
					}                                              //~1Ah0I~
					break;                                         //~1Ah0I~
				case MESSAGE_DOWNLOAD_STARTED:
					// obj will contain a String representing the file name
					if(msg.obj != null && msg.obj instanceof String)
					{
 	 					maxValue = msg.arg1;                       //~1Ah0R~
                        if (Dump.Y) Dump.println("UnzipMsgThread:activityHandler DOWNLOAD_STARTED max="+maxValue);//+1Ah0I~
						String fileName = (String) msg.obj;
						String pdTitle = thisActivity.getString(R.string.progress_dialog_title_downloading);
						String pdMsg = thisActivity.getString(R.string.progress_dialog_message_prefix_downloading,maxValue);//~1Ah0R~
						pdMsg += " " + fileName;
						
						dismissCurrentProgressDialog();
						progressDialog = new ProgressDialog(thisActivity);
						progressDialog.setTitle(pdTitle);
						progressDialog.setMessage(pdMsg);
						progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						progressDialog.setProgress(0);
						progressDialog.setMax(maxValue);
						// set the message to be sent when this dialog is canceled
//  					progressDialog.setCancelable(true);        //~1Ah0R~
    					progressDialog.setCancelable(false);       //~1Ah0I~
						progressDialog.show();
					}
					break;
				
				/*
				 * Handling MESSAGE_DOWNLOAD_COMPLETE:
				 * 1. Remove the progress bar from the screen.
				 * 2. Display Toast that says download is complete.
				 */
				case MESSAGE_DOWNLOAD_COMPLETE:
					dismissCurrentProgressDialog();
					displayMessage(thisActivity.getString(R.string.user_message_download_complete));//~vag0I~
					break;
				
				/*
				 * Handling MESSAGE_ENCOUNTERED_ERROR:
				 * 1. Check the obj field of the message for the actual error
				 *    message that will be displayed to the user.
				 * 2. Remove any progress bars from the screen.
				 * 3. Display a Toast with the error message.
				 */
				case MESSAGE_ENCOUNTERED_ERROR:
					// obj will contain a string representing the error message
					if(msg.obj != null && msg.obj instanceof String)
					{
						String errorMessage = (String) msg.obj;
						dismissCurrentProgressDialog();
						displayMessage(errorMessage);
					}
					break;
					
				default:
					// nothing to do here
					break;
			}
		}
	};
	
	public void dismissCurrentProgressDialog()
	{
		if(progressDialog != null)
		{
			progressDialog.hide();
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	public void displayMessage(String message)
	{
		if(message != null)
		{
  			Toast.makeText(thisActivity, message, Toast.LENGTH_LONG).show();//~vag0I~
		}
	}
}
