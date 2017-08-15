//*CID://+v101R~:                             update#=   10;       //~v106I~//~@@@2R~//~v101R~
//************************************************************************//~v106I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//1067:121128 GMP connection NPE(currentLayout is intercepted by showing dialog:GMPWait)//~v106I~
//            doAction("play")-->gotOK(new GMPGoFrame) & new GMPWait()(MainThread)//~v106I~
//************************************************************************//~v106I~
package com.Asgts.awt;                                            //~1112I~//+v101R~

import jagoclient.Dump;
import android.widget.ScrollView;

                                                                   //~1112I~
public class TextArea extends TextField                            //~1116R~
                                                                   //~1118R~
{                                                                  //~1112I~
	public static final int SCROLLBARS_VERTICAL_ONLY=1;	//not used defined on layout xml//~1118R~
	public TextArea()                                              //~1116I~
    {                                                              //~1116I~
    	super(0,0);   //Row,Col                                                //~1116I~//~1216R~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~v106I~
        {                                                          //~v106I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~v106I~
        	return;                                                //~v106I~
        }                                                          //~v106I~
        if (textView.getParent() instanceof ScrollView)            //~1311I~
        	scrollView=(ScrollView)textView.getParent();           //~1311I~
    }                                                              //~1116I~
    //************************************************************ //~v101I~
	public TextArea(int Presid)                                    //~@@@2I~
    {                                                              //~@@@2I~
    	super("",Presid,0,0);   //Row,Col                          //~@@@2I~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~@@@2I~
        {                                                          //~@@@2I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~@@@2I~
        	return;                                                //~@@@2I~
        }                                                          //~@@@2I~
        if (textView.getParent() instanceof ScrollView)            //~@@@2I~
        	scrollView=(ScrollView)textView.getParent();           //~@@@2I~
    }                                                              //~@@@2I~
    //************************************************************ //~v101I~
    //*container for findViewById                                  //~v101I~
    //************************************************************ //~v101I~
	public TextArea(Container Pcontainer,int Presid)               //~v101I~
    {                                                              //~v101I~
    	super(Pcontainer,"",Presid,0,0);   //Row,Col               //~v101I~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~v101I~
        {                                                          //~v101I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~v101I~
        	return;                                                //~v101I~
        }                                                          //~v101I~
        if (textView.getParent() instanceof ScrollView)            //~v101I~
        	scrollView=(ScrollView)textView.getParent();           //~v101I~
    }                                                              //~v101I~
    //************************************************************ //~v101I~
	public TextArea(String s,int Prow,int Pcols,int Pscrollbarflag)                    //~1116I~//~1216R~
    {                                                              //~1116I~
    	super(s,Prow,Pcols);                                                  //~1116I~//~1216R~
    	if (Dump.Y) Dump.println("TextArea:name="+s+",row="+Prow+",col="+Pcols+",textView="+(textView==null?"@@@@@null":textView));//~v106I~
//  	if (Dump.Y) Dump.println("AG.currentlayout="+Integer.toHexString(AG.currentLayoutId)+",currentFrame="+AG.currentFrame.framename);//~v106R~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~v106I~
        {                                                          //~v106I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~v106I~
        	return;                                                //~v106I~
        }                                                          //~v106I~
        if (textView.getParent() instanceof ScrollView)              //~1218I~
        	scrollView=(ScrollView)textView.getParent();              //~1218I~
    }                                                              //~1116I~                              //~1116I~
    //*****************************************************************//~@@@2I~
    //*find view by res id                                         //~@@@2I~
    //*****************************************************************//~@@@2I~
	public TextArea(String s,int Presid,int Prow,int Pcols,int Pscrollbarflag)//~@@@2I~
    {                                                              //~@@@2I~
    	super(s,Presid,Prow,Pcols);                                //~@@@2I~
    	if (Dump.Y) Dump.println("TextArea:name="+s+",row="+Prow+",col="+Pcols+",textView="+(textView==null?"@@@@@null":textView));//~@@@2I~
//  	if (Dump.Y) Dump.println("AG.currentlayout="+Integer.toHexString(AG.currentLayoutId)+",currentFrame="+AG.currentFrame.framename);//~@@@2I~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~@@@2I~
        {                                                          //~@@@2I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~@@@2I~
        	return;                                                //~@@@2I~
        }                                                          //~@@@2I~
        if (textView.getParent() instanceof ScrollView)            //~@@@2I~
        	scrollView=(ScrollView)textView.getParent();           //~@@@2I~
    }                                                              //~@@@2I~
    //*****************************************************************//~v101I~
    //*find view by res id                                         //~v101I~
    //*****************************************************************//~v101I~
	public TextArea(Container Pcontainer,String s,int Presid,int Prow,int Pcols,int Pscrollbarflag)//~v101I~
    {                                                              //~v101I~
    	super(Pcontainer,s,Presid,Prow,Pcols);                     //~v101I~
    	if (Dump.Y) Dump.println("TextArea:name="+s+",row="+Prow+",col="+Pcols+",textView="+(textView==null?"@@@@@null":textView));//~v101I~
        if (textView==null)//inserted sleep into GMPConnection but to avoid NPE in debugging mode//~v101I~
        {                                                          //~v101I~
	    	if (Dump.Y) Dump.println("TextArea:@@@@ textView is null");//~v101I~
        	return;                                                //~v101I~
        }                                                          //~v101I~
        if (textView.getParent() instanceof ScrollView)            //~v101I~
        	scrollView=(ScrollView)textView.getParent();           //~v101I~
    }                                                              //~v101I~
//***************                                                  //~1216I~
	public void repaint()                                          //~1125I~
    {                                                              //~1125I~
    }                                                              //~1125I~
//***************                                                  //~1216I~
    public void setVisible(boolean Pvisible)                            //~1128I~
    {                                                              //~1128I~
    	if (Dump.Y) Dump.println("@@@@ TextArea:setVisble ="+Pvisible);//~1506R~
    }                                                              //~1128I~
//***************                                                  //~1219I~
    public void appendText(String Ptext)  //deplicated but         //~1219I~
    {                                                              //~1219I~
    	append(Ptext);                                             //~1219I~
    }                                                              //~1219I~
                        //~1212I~
}//class                                                           //~1112I~
