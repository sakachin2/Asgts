//*CID://+@@@2R~:                             update#=   21;       //~@@@@I~//~@@@2R~
package com.Asgts.awt;                                                //~1108R~//~1109R~//~@@@@R~//~@@@2R~

import jagoclient.Dump;
import java.util.ArrayList;
import java.util.LinkedList;
import android.graphics.Bitmap;

import com.Asgts.AG;                                                //~@@@@R~//~@@@2R~
import com.Asgts.Utils;                                             //~@@@@R~//~@@@2R~
import com.Asgts.AView;                                             //~@@@@R~//~@@@2R~

public class Window extends Container //Container-->Window-->Frame                                                 //~1111R~//~1116R~//~1124R~
{                                                                  //~1111I~
    static private LinkedList<Frame> framestack=new LinkedList<Frame>();  //~1314R~
    public static final int WINDOW_CREATED=0;                      //~1128I~
    public static final int WINDOW_OPENED=1;                       //~1128I~
    public static final int WINDOW_ACTIVE=2;                       //~1128R~
    public static final int WINDOW_INACTIVE=3;                     //~1128R~
    public static final int WINDOW_CLOSING=4;                      //~1516R~
    public static final int WINDOW_CLOSED=5;                       //~1516I~
    public static final int WINDOW_RESTORE=6;                      //~1516R~
                                                                   //~1408I~
	public ActionEvent scheduledAE;                                //~1425R~
	public ActionEvent scheduledDialogAE;                          //~1425R~
	private ArrayList<Bitmap> recycleBitmaps;                      //~1425R~
//****************************************                         //~1128I~
//*control frame for windowsListener                               //~1128I~
//****************************************                         //~1128I~
	static public void setVisible(Object Pobject,boolean Pvisible) //~1128I~
    {                                                              //~1128I~
    	int status;                                                //~1128I~
        WindowListener listener;                                   //~1128R~
        Frame frame;                                               //~1128I~
        Dialog dialog;                                             //~1128I~
    //**************                                               //~1128I~
    	if (Dump.Y) Dump.println("Window:setVisible="+Pvisible+",object="+Pobject.toString());//~1511R~
    	if (Pobject instanceof Frame)                              //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible for Frame");//~1506R~
            frame=(Frame)Pobject;                                  //~1128I~
            status=frame.framestatus;                              //~1128R~
        	listener=frame.windowlistener;                         //~1128I~
        }                                                          //~1128I~
        else                                                       //~1128I~
    	if (Pobject instanceof Dialog)                             //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible for Dialog");//~1506R~
            dialog=(Dialog)Pobject;                                //~1128I~
            status=dialog.dialogstatus;                            //~1128I~
        	listener=dialog.windowlistener;                        //~1128I~
        }                                                          //~1128I~
        else                                                       //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible for Unknown");//~1506R~
        	return;                                                //~1128I~
        }                                                          //~1128I~
        if (listener==null)                                        //~1128I~
        {                                                          //~1128I~
	    	if (Dump.Y) Dump.println("Window:setVisible no listener defined");//~1506R~
        	return;                                                //~1128I~
        }                                                          //~1128I~
        if (Pvisible)                                              //~1128I~
        {                                                          //~1128I~
        	if (status==WINDOW_CREATED)                            //~1128I~
            {                                                      //~1128I~
            	status=WINDOW_OPENED;                        //~1128I~
		    	if (Dump.Y) Dump.println("Window:setVisible call windowOpened");//~1506R~
			    kickWindowListener(status,listener);               //~1128I~
            }                                                      //~1128I~
            else                                                   //~1128I~
            if (status!=WINDOW_ACTIVE)                             //~1503R~
            {                                                      //~1503R~
                if (Dump.Y) Dump.println("Window:setVisible call windowActivated");//~1506R~
                status=WINDOW_ACTIVE;                          //~1128R~//~1503R~
                kickWindowListener(status,listener);               //~1503R~
            }                                                      //~1503R~
            else    //reopen                                       //~1503I~
            {                                                      //~1503I~
		    	if (Dump.Y) Dump.println("Window:setVisible on Active:ReOpen");//~1506R~
			    kickWindowListener(WINDOW_RESTORE,listener);       //~1503I~
            }                                                      //~1503I~
        }                                                          //~1128I~
        else                                                       //~1128I~
        {                                                          //~1128I~
        	if (status==WINDOW_ACTIVE||status==WINDOW_OPENED)      //~1128I~
            {                                                      //~1128I~
            	status=WINDOW_INACTIVE;                      //~1128I~
		    	if (Dump.Y) Dump.println("Window:setVisible call windowInactivated");//~1506R~
                kickWindowListener(status,listener);               //~1128I~
            }                                                      //~1128I~
        }                                                          //~1128I~
    }                                                              //~1128I~
//******************                                               //~1128I~
	static public void pushFrame(Frame Pframe)                     //~1128R~
    {                                                              //~1128I~
        if (Pframe.framelayoutview==null)                          //~1218I~
			Pframe.framelayoutview=AView.inflateView(Pframe.framelayoutresourceid);//~1218I~//~@@@@R~
		if (framestack.size()==0)                                  //~1314I~
        	AG.mainframe=Pframe;                                   //~1314I~
        framestack.add(0,Pframe);                                  //~1314I~
        listStack();                                               //~1329I~
        AG.aView.setContentView(Pframe);	//set android context//~1128I~//~@@@@R~
    	if (Dump.Y) Dump.println("Window pushed ctr="+framestack.size()+",name="+Pframe.framename);                       //~1217I~//~1218R~//~1506R~
    }                                                              //~1128I~
//*********                                                        //~1329I~
	static public void listStack()                     //~1329I~
    {                                                              //~1329I~
		int ctr=framestack.size();                                 //~1329I~
        for (int ii=0;ii<ctr;ii++)                                     //~1329I~
        	if (Dump.Y) Dump.println("listframestack "+framestack.get(ii).framename);//~1506R~
    }                                                              //~1329I~
//******************                                               //~1314I~
	static public Frame getCurrentFrame()                          //~1314I~
    {                                                              //~1314I~
        return framestack.getFirst();                              //~1420R~
    }                                                              //~1314I~
//******************                                               //~1128I~
	static public Frame popFrame(boolean Pclose)                   //~1314I~
    {                                                              //~1314I~
    	return popupFrame(Pclose);                                        //~1314I~
    }                                                              //~1314I~
	static public Frame popFrame(Frame Pframe)                     //~1512I~
    {                                                              //~1512I~
        if (Pframe!=framestack.getFirst())                         //~1512I~
        {                                                          //~1512I~
        	if (!framestack.remove(Pframe))       //not exist      //~1512I~
            	return null;                                       //~1512I~
	    	framestack.addFirst(Pframe);		//push to Top      //~1512I~
        }                                                          //~1512I~
	    return popupFrame(true);                                   //~1512I~
    }                                                              //~1512I~
	static public Frame popFrame()                                 //~1128R~
    {                                                              //~1128I~
    	return popupFrame(false);                                         //~1314I~
    }                                                              //~1314I~
	static public Frame wrapFrameByTouch(int Pdestination)         //~1412I~
    {                                                              //~1412I~
		if (AG.getCurrentDialog()!=null)	//dialog open              //~1412I~
        	return null;				//ignore touch action      //~1412I~
    	if (Pdestination<0)		//back to prev                 //~1412I~
    		return popupFrame(false);                              //~1412I~
        return pushLastFrame();                                    //~1412I~
    }                                                              //~1412I~
                            //~1314I~
	static public Frame popupFrame(boolean Pclose)                                 //~1314I~
    {                                                              //~1314I~
        Frame frame,closeFrame;                                    //~1424R~
        int ctr;                                                   //~1314I~
    //********************:                                        //~1217I~
		ctr=framestack.size();                                     //~1314I~
    	if (Dump.Y) Dump.println("Window pop request ctr="+ctr);                  //~1217I~//~1506R~
    	if (ctr<=1)                                                //~1314R~
        {                                                          //~1330I~
            if (Pclose)                                            //~1330I~
	            Utils.finish();//~1212I~//~1309R~             //~1330I~//~@@@@R~
        	return null;   //1:last frame                          //~1128I~//~1314R~
        }                                                          //~1330I~
        frame=framestack.getFirst();	//get top                  //~1424M~
        if (Pclose)                                                //~1424I~
        {                                                          //~1424I~
        	closeFrame=frame;                                      //~1424I~
        	closeFrame.onDestroy();          //before contentview active//~1424R~
        }
        else 
        	closeFrame=null;//~1424I~
        framestack.remove(frame);    //pick from list               //~1314I~
        if (!Pclose)                                               //~1314I~
	        framestack.add(frame);		//wrap to last             //~1314R~
        frame=framestack.getFirst();	//new top                  //~1420R~
		AG.aView.setContentView(frame);           //~1128I~        //~@@@@R~
        if (closeFrame!=null)                                                //~1424I~
        	closeFrame.onDestroy2();         //after contentview updated//~1424R~
        frame.onRestore();                                         //~1503R~
    	if (Dump.Y) Dump.println("Window poped old ctr="+ctr+",new top="+(frame.framename==null?"null":frame.framename));                        //~1217I~//~1506R~
		return frame;                                              //~1128I~
    }                                                              //~1128I~
////*************************************************                //~@@@@I~//~@@@2R~
////From PartnerThread                                               //~@@@@I~//~@@@2R~
////close connectedGoFrame when endgame/connection closed            //~@@@@I~//~@@@2R~
////*************************************************                //~@@@@I~//~@@@2R~
//    static public boolean popConnectedGoFrame()                    //~@@@@I~//~@@@2R~
//    {                                                              //~@@@@I~//~@@@2R~
//        Frame frame,closeFrame;                                    //~@@@@I~//~@@@2R~
//    //********************:                                        //~@@@@I~//~@@@2R~
//        frame=framestack.getFirst();    //get top                  //~@@@@I~//~@@@2R~
//        if (frame.frameDoActionListener==null)                     //~@@@@I~//~@@@2R~
//            return false;                                          //~@@@@I~//~@@@2R~
//        if (frame.framelayoutresourceid==AG.frameId_ConnectedGoFrame)//~@@@@R~//~@@@2R~
//            frame.frameDoActionListener.doAction(AG.resource.getString(R.string.Close));//~@@@@M~//~@@@2R~
//        else                                                       //~@@@@I~//~@@@2R~
//        if (frame.framelayoutresourceid==AG.frameId_MainFrame)     //~@@@@I~//~@@@2R~
//            frame.frameDoActionListener.doAction(AG.resource.getString(R.string.RestoreFrame));//~@@@@R~//~@@@2R~
//        return true;                                               //~@@@@I~//~@@@2R~
//    }                                                              //~@@@@I~//~@@@2R~
//*************************************************                //~@@@@I~
	static public Frame pushLastFrame()                            //~1412I~
    {                                                              //~1412I~
        Frame frame;                                               //~1412I~
        int ctr;                                                   //~1412I~
    //********************:                                        //~1412I~
		ctr=framestack.size();                                     //~1412I~
    	if (Dump.Y) Dump.println("Window pushLast ctr="+ctr);      //~1506R~
    	if (ctr<=1)                                                //~1412I~
        {                                                          //~1412I~
        	return null;   //1:last frame                          //~1412I~
        }                                                          //~1412I~
        frame=framestack.get(ctr-1);	//get last                 //~1412I~
        framestack.remove(frame);       //pick from list           //~1416R~
	    framestack.addFirst(frame);		//push to Top              //~1416R~
		AG.aView.setContentView(frame);                           //~1412I~//~@@@@R~
        frame.onRestore();                                         //~1504I~
    	if (Dump.Y) Dump.println("Window pushLast new top="+(frame.framename==null?"null":frame.framename));//~1506R~
		return frame;                                              //~1412I~
    }                                                              //~1412I~
//******************                                               //~1128I~
	static public void windowClosed(Frame Pframe)                   //~1128I~
    {                                                              //~1128I~
        WindowListener listener=Pframe.windowlistener;              //~1128I~
        if (listener==null)                                        //~1128I~
        	return;                                                //~1128I~
        if (Dump.Y) Dump.println("WindowClosed");                  //~1506R~
        Pframe.framestatus=WINDOW_CLOSED;                           //~1128I~
        kickWindowListener(Pframe.framestatus,listener);            //~1128I~
    }                                                              //~1128I~
//******************                                               //~1128I~
	static public void onFocusChanged(boolean Phasfocus)           //~1513R~
    {                                                              //~1128I~
        if (framestack.isEmpty())                                  //~1314I~
        	return;                                                //~1128I~
    	Frame frame=(Frame)framestack.getFirst();  //get top       //~1420R~
        WindowListener listener=frame.windowlistener;               //~1128I~
        if (listener==null)                                        //~1128I~
        	return;                                                //~1128I~
        if (Dump.Y) Dump.println("Window:onFocusChange Act/Inact="+Phasfocus);//~1506R~
        if (Phasfocus)                                            //~1128I~
        {                                                          //~1128I~
        	if (frame.framestatus==WINDOW_INACTIVE)           //~1128R~
            {                                                      //~1128I~
		    	if (Dump.Y) Dump.println("Window:onFocusChanged call windowActivated");//~1513R~
            	frame.framestatus=WINDOW_ACTIVE;                    //~1128I~
                kickWindowListener(frame.framestatus,listener);    //~1128I~
         	}                                                      //~1128I~
        }                                                          //~1128I~
        else                                                       //~1128I~
        {                                                          //~1128I~
		    if (Dump.Y) Dump.println("Window:onFocusChanged call windowInActivated");//~1513R~
            frame.framestatus=WINDOW_INACTIVE;                      //~1128I~
            kickWindowListener(frame.framestatus,listener);        //~1128I~
        }                                                          //~1128I~
    }                                                              //~1128I~
//***********                                                      //~1128I~
    public static void kickWindowListener(final int Pcase,final WindowListener Plistener)//~1128I~
    {                                                              //~1128I~
        if (AG.isMainThread())                                     //~1128I~
        {                                                          //~1128I~
    		callWindowListener(Pcase,Plistener);  //~1128I~
        	return;                                                //~1128I~
        }                                                          //~1128I~
        AG.activity.runOnUiThread(              //execute on mainthread after posted//~1128I~
            new Runnable()                                         //~1128I~
            {                                                      //~1128I~
                @Override                                          //~1128I~
                public void run()                                  //~1128I~
                {                                                  //~1128I~
			        if (Dump.Y) Dump.println("WindowListener on UIThread");//~1506R~
		    		callWindowListener(Pcase,Plistener);//~1128I~
                }                                                  //~1128I~
            }                                                      //~1128I~
                                  );                               //~1128I~
    }                                                              //~1128I~
    public static void callWindowListener(int Pcase,WindowListener Plistener)//~1128I~
    {                                                              //~1128I~
        WindowEvent ev=new WindowEvent();                          //~1516R~
        switch(Pcase)                                              //~1128I~
        {                                                          //~1128I~
        case WINDOW_OPENED:                                        //~1128I~
            Plistener.windowOpened(ev);                            //~1128I~
            break;                                                 //~1128I~
        case WINDOW_RESTORE:                                       //~1503I~
            Plistener.windowOpened(ev);                            //~1503I~
            break;                                                 //~1503I~
        case WINDOW_ACTIVE:                                     //~1128I~
            Plistener.windowActivated(ev);                         //~1128I~
            break;                                                 //~1128I~
        case WINDOW_INACTIVE:                                   //~1128I~
            Plistener.windowDeactivated(ev);                       //~1128I~
            break;                                                 //~1516I~
        case WINDOW_CLOSING:                                       //~1516R~
            Plistener.windowClosing(ev);                           //~1516R~
            break;                                                 //~1516I~
        case WINDOW_CLOSED:                                        //~1516I~
            Plistener.windowClosed(ev);                            //~1516I~
            break;                                                 //~1128I~
        }                                                          //~1128I~
    }                                                              //~1128I~
//*****************************************************            //~1415I~
//*recycle bitmap to avoid OutOfMemory ****************            //~1415I~
//*****************************************************            //~1415I~
    public void recyclePrepare(Bitmap Pbitmap)                     //~1415I~
    {                                                              //~1415I~
    	if (Dump.Y) Dump.println("Window:recyclePrepare Bitmap w="+Pbitmap.getWidth()+",h="+Pbitmap.getHeight()+"="+Pbitmap.toString());//~@@@2R~
        if (recycleBitmaps==null)                                  //~1415I~
        {                                                          //~@@@2I~
        	recycleBitmaps=new ArrayList<Bitmap>();                  //~1415I~
	    	if (Dump.Y) Dump.println("Window:recyclePrepare arraylist:null");//~@@@2I~
        }                                                          //~@@@2I~
    	if (Dump.Y) Dump.println("Window:recyclePrepare arraylist="+recycleBitmaps.toString());//~@@@2I~
    	recycleBitmaps.add(Pbitmap);                             //~1415I~
    }                                                              //~1415I~
    public void recycleBitmap()                                   //~1415I~
    {                                                              //~1415I~
    	if (Dump.Y) Dump.println("Window:recycleBitmap");          //~1506R~
    	if (recycleBitmaps==null)                                  //~1415I~
        	return;                                                //~1415I~
        int ctr=recycleBitmaps.size();                             //~1415I~
    	if (Dump.Y) Dump.println("Window:recycleBitmap ctr="+ctr); //~1506R~
    	if (ctr==0)                                                //~1415I~
        	return;                                                //~1415I~
        for (int ii=0;ii<ctr;ii++)                                 //~1415I~
        {                                                          //~1415I~
        	Bitmap bitmap=recycleBitmaps.get(ii);                  //~1415I~
    		if (Dump.Y) Dump.println("Window:recycleBitmap ii="+ii+",isrecycled="+bitmap.isRecycled()+")"+",w="+bitmap.getWidth()+",h="+bitmap.getHeight()+"="+bitmap.toString());//~@@@2I~
            if (!bitmap.isRecycled())                              //~1415I~
            {                                                      //~1415I~
            	bitmap.recycle();                                  //~1415I~
    			if (Dump.Y) Dump.println("Window:recycleBitmap ii="+ii+",w="+bitmap.getWidth()+",h="+bitmap.getHeight()+"="+bitmap.toString());//~@@@2R~
            }                                                      //~1415I~
        }                                                          //~1415I~
        recycleBitmaps.clear();                                    //~1415I~
    }                                                              //~1415I~
//*****************************************************            //~@@@2I~
//*recycle bitmap to avoid OutOfMemory save to parm stack          //~@@@2I~
//*****************************************************            //~@@@2I~
    public static ArrayList<Bitmap> recycleMyPrepare(Bitmap Pbitmap,ArrayList<Bitmap> Pstack)//~@@@2I~
    {                                                              //~@@@2I~
    	ArrayList<Bitmap> stack;                                   //~@@@2I~
    //*******************                                          //~@@@2I~
    	if (Dump.Y) Dump.println("Window:recycleMyPrepare w="+Pbitmap.getWidth()+",h="+Pbitmap.getHeight()+",byte="+Image.getByteCount(Pbitmap)+",Bitmap="+Pbitmap.toString());//+@@@2R~
        stack=Pstack;                                              //~@@@2I~
        if (stack==null)                                           //~@@@2I~
        	stack=new ArrayList<Bitmap>();                         //~@@@2I~
    	stack.add(Pbitmap);                                        //~@@@2I~
        return stack;                                              //~@@@2I~
    }                                                              //~@@@2I~
    public static void recycleMyBitmapStack(ArrayList<Bitmap> Pstack)//~@@@2I~
    {                                                              //~@@@2I~
    	if (Dump.Y) Dump.println("Window:recycleMyBitmapStack");   //~@@@2I~
    	if (Pstack==null)                                          //~@@@2I~
        	return;                                                //~@@@2I~
        int ctr=Pstack.size();                                     //~@@@2I~
    	if (Dump.Y) Dump.println("Window:recycleMyBitmapStack ctr="+ctr);//~@@@2I~
    	if (ctr==0)                                                //~@@@2I~
        	return;                                                //~@@@2I~
        for (int ii=0;ii<ctr;ii++)                                 //~@@@2I~
        {                                                          //~@@@2I~
        	Bitmap bitmap=Pstack.get(ii);                          //~@@@2I~
    		if (Dump.Y) Dump.println("Window:recycleMyBitmapStack ii="+ii+",isrecycled="+bitmap.isRecycled()+",w="+bitmap.getWidth()+",h="+bitmap.getHeight()+",byte="+Image.getByteCount(bitmap)+",Bitmap="+bitmap.toString());//+@@@2I~
            if (!bitmap.isRecycled())                              //~@@@2I~
            {                                                      //~@@@2I~
    			if (Dump.Y) Dump.println("Window:recycleMyBitmapStack ii="+ii+",w="+bitmap.getWidth()+",h="+bitmap.getHeight()+",byte="+Image.getByteCount(bitmap)+",Bitmap="+bitmap.toString());//~@@@2I~
            	bitmap.recycle();                                  //~@@@2I~
            }                                                      //~@@@2I~
        }                                                          //~@@@2I~
        Pstack.clear();                                            //~@@@2I~
    }                                                              //~@@@2I~
//*****************************************************            //~@@@@I~
    public static void recycleBitmapNow(Bitmap Pbitmap)            //~@@@@I~
    {                                                              //~@@@@I~
        if (!Pbitmap.isRecycled())                                 //~@@@@I~
        {                                                          //~@@@@I~
            Pbitmap.recycle();                                     //~@@@@I~
    		if (Dump.Y) Dump.println("Window:recycleBitmapNow w="+Pbitmap.getWidth()+",h="+Pbitmap.getHeight()+"="+Pbitmap.toString());//+@@@2R~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~
}//class                                                           //~1415R~
