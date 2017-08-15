//*CID://+1Ah0R~:                             update#=   21;       //~1Ab0R~//+1Ah0R~
//*****************************************************************************
//1Ah0 2016/10/15 bonanza                                          //+1Ah0I~
//1Ab0 2015/04/18 (like as Ajagoc:1A84)WiFiDirect from Top panel   //~1Ab0I~
//1A4s 2014/12/06 utilize clipboard(view dialog)                   //~1A4sI~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320
//*****************************************************************************
package com.Asgts;
import com.Asgts.AG;
import com.Asgts.R;
import com.Asgts.awt.Frame;

import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.DoActionListener;

class AMenuDialog extends CloseDialog
{
	private DoActionListener dal;
    private int helptitleid,dialogid;                              //~1A40R~
    private String helpfile;                                       //~1A40I~
	public AMenuDialog(Frame Pframe,int Ptitleid,int Pdialogid,String Phelpfile,int Phelptitleid)//~1A40R~
	{
		super(Pframe,AG.resource.getString(Ptitleid),Pdialogid,false/*modal*/,false/*modalInput*/);
        helpfile=Phelpfile;                                        //~1A40R~
        helptitleid=Phelptitleid;
        dialogid=Pdialogid;
        dal=(DoActionListener)Pframe;
        new ButtonAction(this,0,R.id.StudyBoard); //doaction string was set by getText() on Button
        new ButtonAction(this,0,R.id.ReplayFile);
        new ButtonAction(this,0,R.id.ReplayFileClipboard);         //~1A4sI~
        new ButtonAction(this,0,R.id.LocalGame);
        new ButtonAction(this,0,R.id.RemoteGame);
      ButtonAction btnWiFiDirect=                                  //~1Ab0I~
        new ButtonAction(this,0,R.id.MenuitemWiFiDirect);          //~1Ab0I~
        new ButtonAction(this,0,R.id.RemoteIP);
        new ButtonAction(this,0,R.id.VsBonanza);                   //+1Ah0I~
        new ButtonAction(this,0,R.id.Cancel);
        new ButtonAction(this,0,R.id.Help);
        if (AG.osVersion<AG.ICE_CREAM_SANDWICH)  //android4        //~1Ab0I~
        {                                                          //~1A90I~//~1Ab0I~
	        btnWiFiDirect.setEnabled(false);                       //~1A90I~//~1Ab0I~
        }                                                          //~1A90I~//~1Ab0I~
		show();
	}
	public AMenuDialog(Frame Pframe,int Ptitleid,int Pdialogid)//filemenu on freeboard//~1A40I~
	{                                                              //~1A40I~
		super(Pframe,AG.resource.getString(Ptitleid),Pdialogid,false/*modal*/,false/*modalInput*/);//~1A40I~
        dialogid=Pdialogid;                                        //~1A40I~
        dal=(DoActionListener)Pframe;                              //~1A40I~
        new ButtonAction(this,0,R.id.LoadFile); //doaction string was set by getText() on Button//~1A40I~
        new ButtonAction(this,0,R.id.SaveFile);                    //~1A40I~
        new ButtonAction(this,0,R.id.LoadNotesFile);               //~1A40I~
        new ButtonAction(this,0,R.id.LoadNotesFileClipboard);      //~1A4sI~
        new ButtonAction(this,0,R.id.CloseBoard);                  //~1A40I~
        new ButtonAction(this,0,R.id.Cancel);                      //~1A40I~
		show();                                                    //~1A40I~
	}                                                              //~1A40I~
//*****************************************
	public void doAction (String o)
	{
    	if (o.equals(AG.resource.getString(R.string.Cancel)))
		{
			setVisible(false); dispose();
		}
        if (dialogid==R.layout.menudialogplay)                     //~1A40R~
        {
    		if (o.equals(AG.resource.getString(R.string.Help)))     //~1A40I~
			{                                                      //~1A40I~
				new HelpDialog(null,helptitleid,helpfile);         //~1A40I~
			}                                                      //~1A40I~
            else                                                   //~1A40I~
            {                                                      //~1A40I~
        		dal.doAction(o);                                   //~1A40R~
				setVisible(false); dispose();                      //~1A40R~
            }                                                      //~1A40I~
        }
        if (dialogid==R.layout.menudialogfiletsumego)              //~1A40I~
        {                                                          //~1A40I~
        	dal.doAction(o);                                       //~1A40I~
			setVisible(false); dispose();                          //~1A40I~
        }                                                          //~1A40I~
	}
}
