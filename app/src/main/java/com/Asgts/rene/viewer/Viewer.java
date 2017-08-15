//*CID://+v101R~: update#=  60;                                    //~1107R~//~v101R~
//**********************************************************************//~1107I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//**********************************************************************//~v101I~
//*TextArea width Vertical ScrollView                              //~1214R~
//**********************************************************************//~1107I~
package com.Asgts.rene.viewer;                                         //~1107R~  //~1108R~//~1109R~//~1214R~//~2C26R~//~v101R~

import java.io.PrintWriter;

import jagoclient.Dump;
import jagoclient.gui.CloseFrame;

import com.Asgts.AG;                                                //~2C26R~//+v101R~
import com.Asgts.awt.Color;                                         //~2C26R~//+v101R~
import com.Asgts.awt.Container;                                    //+v101R~
import com.Asgts.awt.Dialog;                                        //~2C26R~//+v101R~

public class Viewer extends com.Asgts.awt.Viewer                                               //~1215R~//~2C26R~//+v101R~
{                                                              //~1111I~//~1112I~
    com.Asgts.awt.Viewer awtviewer;                          //~1215I~//~2C26R~//+v101R~
	CloseFrame closeframe=null;                                    //~1214I~
	Dialog     dialog=null;                                        //~1215I~
	String name=null;                                              //~1214I~
//*********************                                            //~1216I~
    public Viewer()                                                //~1214I~
    {                                                              //~1214I~
//*for dialog                                                      //~1215I~
        super();                                                   //~1214I~
        dialog=AG.getCurrentDialog();
        if (Dump.Y) Dump.println("Viewr dialog="+(dialog==null?"null":dialog.toString()));//~1216R~
    }                                                              //~1214I~
//******************                                               //~1215I~
    public  Viewer(String Pname)	//from Lister                  //~1220R~
    {                                                              //~1215I~
    	super(Pname);                                              //~1216R~
    }                                                              //~1215I~
//**************                                                   //~v101R~
    public Viewer(Container Pcontainer,int Presid)                 //~v101R~
    {                                                              //~v101R~
    	super(Pcontainer,Presid);                                  //~v101R~
    }                                                              //~v101R~
//******************                                               //~1220I~
    public  Viewer(CloseFrame Pcf,String Pname)                    //~1220I~
    {                                                              //~1220I~
    	super(Pname);                                              //~1220I~
	    initViewer(Pcf,Pname);                                     //~1220I~
                                                                   //~1220I~
    }                                                              //~1220I~
//******************                                               //~1215I~
    public void initViewer(CloseFrame Pcf,String Pname)            //~1215I~
    {                                                              //~1215I~
        closeframe=Pcf;                                            //~1215I~
        name=Pname;                                                //~1215I~
    }                                                              //~1215I~
//******************                                               //~1215I~
    public void setText(String Ptext)                              //~1215R~
    {                                                              //~1215I~
		if (dialog!=null)                                          //~1216I~
    		dialog.setViewerText(this,Ptext);
		if (Dump.Y) Dump.println("Viewer settext="+Ptext);//~1215R~//~1216R~
    }                                                              //~1215I~
//******************                                               //~1326I~
    public void appendLine(String Ptext)                           //~1326I~
    {                                                              //~1326I~
		append(Ptext,Color.black);                                 //~1326R~
    }                                                              //~1326I~
//******************                                               //~1418I~
    public void save(PrintWriter Ppw)//from  ConnectionFrame       //~1418I~
    {                                                              //~1418I~
		super.save(Ppw);                                           //~1418I~
    }                                                              //~1418I~
}//class                                                              //~1111I~//~1112I~
