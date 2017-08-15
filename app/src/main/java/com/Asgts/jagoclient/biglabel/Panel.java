//*CID://+1A6AR~: update#= 223;                                    //~1A6AR~
//**********************************************************************//~1107I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing//~1A6AI~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//**********************************************************************//~v101I~
//*for BigLabel drawing                                            //~1414R~
//*  IgsGoFrame:settime()-->settitle1():repaint()-->BigLabel:paint()-->BigTimer:drawString()//~1414R~
//**********************************************************************//~1107I~
package com.Asgts.jagoclient.biglabel;                                         //~1107R~  //~1108R~//~1109R~//~1117R~//~1414R~//~@@@@R~//~v101R~

import jagoclient.Dump;
import jagoclient.board.TimedBoard;
import jagoclient.gui.BigLabel;

import com.Asgts.AG;                                                //~@@@@R~//~v101R~
import com.Asgts.awt.Canvas;                                        //~@@@@R~//~v101R~
import com.Asgts.awt.Container;                                    //~v101R~
import com.Asgts.awt.Dimension;                                     //~@@@@R~//~v101R~
import com.Asgts.awt.Frame;                                         //~@@@@R~//~v101R~
import com.Asgts.awt.Graphics;                                      //~@@@@R~//~v101R~
import com.Asgts.awt.Image;                                         //~@@@@R~//~v101R~

import android.view.View;
import android.widget.ImageView;


public class Panel extends com.Asgts.awt.Panel                        //~1414R~//~@@@@R~//~v101R~
{                                                                  //~0914I~
	private ImageView view;                                             //~1414I~
    private Dimension bigtimersize;                                //~1506R~
    private Image image;                                           //~1414I~
    private Graphics graphics;                                     //~1414I~
//******************************                                   //~1217I~
//    public Panel()                                                 //~1414R~//~v101R~
//    {                                                              //~1217R~//~v101R~
//        view=(ImageView)(AG.findViewById(AG.viewId_BigTimerLabel));             //~1414I~//~v101R~
////      view.setVisibility(View.VISIBLE);   //reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle//~1414I~//~@@@@R~//~v101R~
//        setVisibility((View)view,View.VISIBLE); //reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle//~@@@@I~//~v101R~
//        getPanelSize();                                             //~1414I~//~v101R~
//        image=initImage(bigtimersize.width,bigtimersize.height);   //~1414I~//~v101R~
//    }                                                              //~1414I~//~v101R~
	public Panel(Container Pcontainer)                             //~v101I~
    {                                                              //~v101I~
        view=(ImageView)(Pcontainer.findViewById(AG.viewId_BigTimerLabel));//~v101I~
        setVisibility((View)view,View.VISIBLE);	//reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle//~v101I~
        getPanelSize();                                            //~v101I~
        image=initImage(bigtimersize.width,bigtimersize.height);   //~v101I~
	}                                                              //~v101I~
	public void getPanelSize()                                     //~1414I~
    {                                                              //~1414I~
        int y=view.getMeasuredHeight();                            //~1414M~
        if (y==0)                                                  //~1414I~
        	y=view.getLayoutParams().height;                        //~1414I~
        int x=view.getMeasuredWidth();                             //~1414M~
        if (x==0)                                                  //~1414I~
        {                                                          //~1414I~
            x=AG.scrWidth;                                         //~1414I~
	        if (!AG.portrait)                                      //~1414I~
        	{                                                      //~1414I~
        		x-=Canvas.getBoardSize();                           //~1414I~
            }                                                      //~1414I~
        }                                                          //~1414I~
        bigtimersize=new Dimension(x,y);                           //~1414M~
        if (Dump.C) Dump.println("Biglabel-Panel getSize x="+x+",y="+y);//~1506R~//~1A6AR~
    }                                                              //~1414I~
    //************************                                     //~1221I~
    public Dimension getSize()	//for BigLabel<--BigTimerLabel<--ConnectedGoFrame//~1414I~
    {                                                              //~1414I~
    	if (bigtimersize.height==0||bigtimersize.width==0)         //~1506R~
			getPanelSize();                                        //~1414I~
    	if (Dump.C) Dump.println("BigLabel:Panel getSize size x="+bigtimersize.width+",y="+bigtimersize.height);//~1506R~//~1A6AR~
    	return bigtimersize;                                       //~1414I~
    }                                                              //~1414I~
    public Image initImage(int Pw,int Ph)                          //~1414R~
    {                                                              //~1414I~
	    if (Dump.C) Dump.println("BigLabel createImage ("+Pw+","+Ph+")");//~1506R~//~1A6AR~
    	image=Image.createImage(Pw,Ph);//setup androidCanvas for Bitmap update//~1414R~
    	graphics=image.getGraphics();                              //~1414I~
        parentWindow.recyclePrepare(image.bitmap);	//recycle at window close//~1422R~
	    if (Dump.C) Dump.println("BigLabel createImage image="+image.toString()+",g="+graphics.toString());//~1506R~//~1A6AR~
    	return image;                                              //~1414I~
    }                                                              //~1414I~
    public Image createImage(int Pw,int Ph)                        //~1414I~
    {                                                              //~1414I~
	    if (Dump.C) Dump.println("BigLabel createImage ("+Pw+","+Ph+")");//~1506R~//~1A6AR~
        if (Pw==bigtimersize.width && Ph==bigtimersize.height)     //~1414I~
            return image;                                          //~1414I~
        bigtimersize.width=Pw;                                     //~1414I~
        bigtimersize.height=Ph;                                    //~1414I~
    	image=initImage(Pw,Ph); //recreate if size changed         //~1422R~
    	return image;                                              //~1414I~
    }                                                              //~1414I~
//********************                                             //~1414I~
    public void repaint()                                          //~1414I~
    {                                                              //~1414I~
    	Frame f;                                                   //~1513I~
    //***************                                              //~1513I~
	    if (Dump.C) Dump.println("BigLabel repaint g="+graphics.toString());//~1506R~//~1A6AR~
	    if (Dump.C) Dump.println("BigLabel repaint parentWindow="+parentWindow.toString());//~1513I~//~1A6AR~
        f=(Frame)parentWindow;                                     //~1513I~
        if (parentWindow instanceof TimedBoard)                    //~1512I~
        {                                                          //~1512I~
		    if (Dump.C) Dump.println("isDestroyed="+f.isDestroyed);//~1513R~//~1A6AR~
        	if (f.isDestroyed)	//bitmap may be recycled at Time waken//~1513R~
            	return;                                            //~1512I~
        }                                                          //~1512I~
		Canvas c=f.boardCanvas;
      if (c!=null)    //null when Canvas.stopThread();             //~@@@@I~
      {                                                            //~@@@@I~
		if (Dump.C) Dump.println("biglabel.Panel:paint req canvas="+c.toString());//~1513I~//~1A6AR~
        c.drawBigLabel((BigLabel)this,graphics);	//draw on the thread same as recycle bitmap//~1513I~
      }                                                            //~@@@@I~
    }                                                              //~1414I~
//******************************                                   //~1422R~
//*BigPanel->Graphics->this                                        //~1422I~
//******************************                                   //~1422I~
    public void drawImage(Image Pimage,int Pdx1,int Pdy1,int Pw,int Ph)//~1414I~
    {                                                              //~1414I~
	    if (Dump.C) Dump.println("BigLabel drawImage Image="+Pimage.toString());//~1506R~//~1A6AR~
	    if (Dump.X) Dump.println("x="+Pdx1+",y="+Pdy1+",w="+Pw+",h="+Ph);//~1506R~//+1A6AR~
		if (parentWindow==AG.currentFrame)	//android canvas is the same for 2 board//~1506I~
    		graphics.setBitmap(view,Pimage);                       //~1506R~
    }                                                              //~1414I~
}//class Panel                                                     //~1414R~
