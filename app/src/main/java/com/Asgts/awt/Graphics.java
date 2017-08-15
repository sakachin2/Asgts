//*CID://+1Ag9R~:                             update#=   44;       //~1Ag9R~
//**************************************************************************//~1A6AI~
//1Ag9 2016/10/09 (Ajagoc)Bitmap OutOfMemory;JNI Global reference remains..java//~1Ag9I~
//                try to clear ref to bitmap from Image:fieldBitmap, Graphics:targetBitmap, android.Graphics.Canvas(<--Image:androidCanvas, Graphics:androidCanvas)//~1Ag9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing//~1A6AI~
//**************************************************************************//~1A6AI~
package com.Asgts.awt;                                             //~@@@@R~

import jagoclient.Dump;
import jagoclient.gui.BigLabel;

import com.Asgts.AG;
import com.Asgts.UiThread;                                         //~@@@@R~
import com.Asgts.UiThreadI;                                        //~@@@@R~

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.view.View;
import android.widget.ImageView;
//Graphics:for paint routine parameter                             //~1117R~
public class Graphics
    implements UiThreadI                                    //~1226R~//~@@@@R~
{                                                                  //~1112I~
    private int caseUiThread;                                      //~1420R~
    private final static int CASE_DRAWBITMAP  =1;                  //~1301R~
    private final static int CASE_SETBITMAP   =2;                  //~1414I~
    private boolean swForeground;                                  //~1420R~
    private Bitmap tgtbitmap;                                         //~1228I~//~1420R~
    Rect tgtbitmaprect;                                            //~1304I~
                                                                   //~1224I~
	public Canvas androidCanvas;   //android canvas:awt graphics         //~1117R~//~1120R~//~1309R~
	private com.Asgts.awt.Canvas ajagocCanvas;   //android canvas:awt graphics//~1421R~//~@@@@R~
    private Paint paint,painttext,paintfill;                  //~1117R~//~1421R~
    private Paint paintStrokeRect=new Paint();                     //~@@@@R~
    private Paint paintWidthLine=new Paint();                      //~@@@@I~
    private Paint paintfillbg=new Paint();;                        //~1417I~
	private	Paint paintbitmap=new Paint(Paint.ANTI_ALIAS_FLAG);    //~1304I~
    private Font font;                                             //~1215I~
    public Graphics()
    {
    	
    }
    public Graphics(Canvas PandroidCanvas)//from Graphics2D                                //~1117I~//~1228R~
    {                                                              //~1117I~
    	androidCanvas=PandroidCanvas;                                            //~1117I~//~1228R~
        if (Dump.Y) Dump.println("Graphics constructor:androidCanvas androidCanvas="+androidCanvas.toString());//~1Ag9I~
    	//tgtbitmap=Pimage.bitmap;                                   //~1228I~
        paint=new Paint();                                         //~1117R~//~1304R~//~1305R~
        paintfill=new Paint();                                     //~1117I~//~1304R~//~1305R~
        painttext=new Paint(Paint.ANTI_ALIAS_FLAG);                //~1117R~
        paintStrokeRect.setStyle(Style.STROKE);                    //~@@@@I~
    }                                                
    public Graphics(Image Pimage)                                //~1117I~//~1228R~
    {                                                              //~1117I~
    	androidCanvas=Pimage.androidCanvas;                                            //~1117I~//~1228R~
    	tgtbitmap=Pimage.bitmap;                                   //~1228I~
        tgtbitmaprect=new Rect(0,0,tgtbitmap.getWidth(),tgtbitmap.getHeight());//~1304I~
        paint=new Paint();                                         //~1117R~//~1304R~//~1305R~
        paintfill=new Paint();                                     //~1117I~//~1304R~//~1305R~
        painttext=new Paint(Paint.ANTI_ALIAS_FLAG);                //~1117R~
        paintStrokeRect.setStyle(Style.STROKE);                    //~@@@@I~
        if (Dump.Y) Dump.println("Graphics constructor Image andoidCanvas="+(androidCanvas==null? "null":androidCanvas.toString()));//~1Ag9R~
        if (Dump.Y) Dump.println("Graphics constructor Image tgtbitmap="+tgtbitmap.toString()+",tgtbitmaprect="+tgtbitmaprect.toString());//~1Ag9I~
    }                                                              //~1117I~
//***************	                                               //~1421I~
    public void setCanvas(com.Asgts.awt.Canvas PajagocCanvas) //~1421R~//~@@@@R~
    {                                                              //~1421I~
        swForeground=true;	//call Canvas.drawBitmap()             //~1421I~
		ajagocCanvas=PajagocCanvas; //foregraound Canvas           //~1421I~
        if (Dump.Y) Dump.println("Graphic:setCanvas swForground=true ajagocCanvas="+ajagocCanvas.toString());//~1Ag9I~
    }                                                              //~1421I~
//**************                                                   //~1224I~
    public void setCanvas(android.graphics.Canvas PandroidCanvas)//from ImageCanvas:onDraw//~1224I~
    {                                                              //~1224I~
    	androidCanvas=PandroidCanvas;                              //~1224I~
        if (Dump.Y) Dump.println("Graphic:setCanvas androidCanvas="+androidCanvas.toString());//~1Ag9I~
	}                                                              //~1224I~
//**************                                                   //~1117I~
    public void setColor(Color Pcolor)                             //~1117R~
    {                                                              //~1117I~
        paint.setColor(Pcolor.getRGB());                           //~1117R~
        paint.setStyle(Paint.Style.STROKE);                       //~1117I~
        paintfill.setColor(Pcolor.getRGB());                       //~1117I~
        paintfill.setStyle(Paint.Style.FILL);                     //~1117I~
        painttext.setColor(Pcolor.getRGB());                       //~1117I~
	}                                                              //~1117I~
//**************                                                   //~1117I~
    public void setFont(Font Pfont)                                //~1224R~
    {                                                              //~1224R~
        font=Pfont;                                                //~1224R~
        if (swForeground && ajagocCanvas!=null)                    //~1224R~
            ajagocCanvas.setFont(Pfont);    //for TextDisplay      //~1224R~
        else                                                       //~1224I~
        {          //BigTimer                                      //~1502R~
        	painttext.setTypeface(Pfont.getTypefaceStyle());       //~1224R~
	        painttext.setTextSize(Pfont.getSize());                    //~1117M~//~1502I~
        }                                                          //~1502I~
    }                                                              //~1224R~
//**************                                                   //~1117I~
    public void fillRect(int Px,int Py,int Pw,int Ph)                   //~1117I~
    {                                                              //~1117I~
    	if (Dump.X) Dump.println("Graphics fillRect pos=("+Px+","+Py+"),w="+Pw+",h="+Ph);//~1506R~//~1A6AR~//~1Ag9R~
    	Rect rect=new Rect(Px/*left*/,Py/*top*/,Px+Pw/*right*/,Py+Ph/*bottom*/);//~1117I~
        testDumpX("fillrect",Px,Py);                                //~1Ag9I~
    	androidCanvas.drawRect(rect,paintfill);                           //~1117R~//~1120R~
	}                                                              //~1117I~
    public void fill3DRect(int Px,int Py,int Pw,int Ph,boolean Psw)//for panel3D//~1214I~
    {                                                              //~1214I~
        testDumpX("fill3DRect",Px,Py);                              //~1Ag9I~
    	fillRect(Px,Py,Pw,Ph);                                     //~1214I~
	}                                                              //~1214I~
//**************                                                   //~1212I~
//*fill by bg color                                                //~1212I~
//**************                                                   //~1212I~
    public void clearRect(int Px,int Py,int Pw,int Ph)             //~1212I~
    {                                                              //~1212I~
    	if (Dump.X) Dump.println("Graphics clearRect pos=("+Px+","+Py+"),w="+Pw+",h="+Ph);//~1506R~//~1A6AR~//~1Ag9R~
    	Rect rect=new Rect(Px/*left*/,Py/*top*/,Px+Pw/*right*/,Py+Ph/*bottom*/);//~1212I~
        testDumpX("clearRect",Px,Py);                               //~1Ag9I~
    	androidCanvas.drawRect(rect,paintfillbg);                  //~1212I~
	}                                                              //~1212I~
//**************                                                   //~1117I~
    public void setBackground(Color Pcolor)	//from NavigationPanel:MyPanel//~1417I~
    {                                                              //~1417I~
    	if (Dump.C) Dump.println("Graphics.setBackground color="+Integer.toHexString(Pcolor.getRGB()));//~1506R~//~1A6AR~
		paintfillbg.setColor(Pcolor.getRGB());                     //~1417I~
	}                                                              //~1417I~
//**************                                                   //~1417I~
    public void drawLine(int Px1,int Py1,int Px2,int Py2)          //~1117I~
    {                                                              //~1117I~
        testDumpX("drawLine",Px1,Py1);                              //~1Ag9I~
    	drawLine((float)Px1,(float)Py1,(float)Px2,(float)Py2);//~1117R~
	}                                                              //~1117I~
//**************                                                   //~1117I~
    public void drawLine(float Px1,float Py1,float Px2,float Py2)  //~1117I~
    {                                                              //~1117I~
    	if (Dump.X) Dump.println("Graphics.drawLine androidCanvas="+androidCanvas.toString());//~@@@@I~//~1A6AR~//~1Ag9R~
        testDumpX("drawLine float",(int)Px1,(int)Py1);              //~1Ag9I~
    	androidCanvas.drawLine(Px1,Py1,Px2,Py2,paint);                    //~1117R~//~1120R~
    }                                                              //~1117I~
//**************                                                   //~@@@@I~
    public void drawLine(Color Pcolor,int Plinewidth,int Px1,int Py1,int Px2,int Py2)//~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.X) Dump.println("X:Graphics.drawLine color");     //~1Ag9I~
        paintWidthLine.setColor(Pcolor.getRGB());                  //~@@@@R~
        paintWidthLine.setStrokeWidth((float)Plinewidth);          //~@@@@R~
        testDumpX("drawLine color",Px1,Py1);                        //~1Ag9I~
    	androidCanvas.drawLine((float)Px1,(float)Py1,(float)Px2,(float)Py2,paintWidthLine);//~@@@@R~
	}                                                              //~@@@@I~
//**************                                                   //~1117I~
    public void setClip(int Px,int Py,int Pw,int Ph)               //~1117I~
    {                                                              //~1117I~
    	Rect rect=new Rect(Px,Py,Px+Pw,Py+Ph);                    //~1303I~
        if (Dump.C) Dump.println("Graphics setclip rect="+rect.toString());//~1506R~//~2C15R~//~1A6AR~//~1Ag9R~
    	androidCanvas.clipRect(rect,Op.REPLACE);//~1117R~//~1120R~//~1303R~
        testDumpX("setClip",Px,Py);                                 //~1Ag9I~
        rect=androidCanvas.getClipBounds();                   //~1303I~
        if (Dump.C) Dump.println("Graphics canvas="+((Object)androidCanvas).toString());//~1506R~//~2C15R~//~1A6AR~//~1Ag9R~
        if (Dump.C) Dump.println("Graphics setclip rect="+rect.top+"-"+rect.left+","+rect.bottom+"-"+rect.right);//~1506R~//~2C15R~//~1A6AR~//~1Ag9R~
    }//~1117I~
//**************                                                   //~1117I~
    public void drawImage(Image Pimage,int Pdx1,int Pdy1,int Pdx2,int Pdy2,int Psx1,int Psy1,int Psx2,int Psy2,Object/*ImageObserver*/ Pobserver)//~1117R~
    {                                                              //~1117I~
    	Bitmap srcbitmap=Pimage.bitmap;                                      //~1224R~//~1302R~
        if (Dump.C) Dump.println("Graphics drawImage srcimage="+((Object)Pimage).toString()+",srcbitmap="+((Object)srcbitmap).toString());//~1228R~//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("Graphics drawImage dest Pdx1="+Pdx1+",Pdy1="+Pdy1+",Pdx2="+Pdx2+",Pdy2="+Pdy2);//~1Ag9I~
        if (Dump.X) Dump.println("X:Graphics drawImage src  Psx1="+Psx1+",Psy1="+Psy1+",Psx2="+Psx2+",Psy2="+Psy2);//~1Ag9R~
    	Rect srcrect=new Rect(Psx1,Psy1,Psx2,Psy2);//~v@@@I~            //~1224R~
    	Rect destrect=new Rect(Pdx1,Pdy1,Pdx2,Pdy2);                   //~v@@@R~//~1224R~
        if (Dump.C) Dump.println("Graphics:drawImage src="+srcrect.toString()+",dest="+destrect.toString());//~1506R~//~1A6AR~
            if (Dump.C) Dump.println("Graphics drawImage to androidcanvas="+((Object)androidCanvas).toString());//~1506R~//~1A6AR~
    	drawBitmapUI(new UiData(srcbitmap,srcrect,destrect));      //~1524R~
        if (swForeground)                                          //~1421I~
        {                                                          //~1421I~
            if (Dump.C) Dump.println("Graphics:notify draw to imageCanvas Graphics="+this.toString()+",imagecanvas="+ajagocCanvas.toString());//~1506R~//~1A6AR~
	        testDumpX("drawImage call repaintBitmap",Pdx1,Pdy1);    //~1Ag9I~
            ajagocCanvas.repaintBitmap(Pimage);                    //~1421R~
        }                                                          //~1421I~
	}                                                              //~1117I~
    public void drawBitmapUI(Object Pparm)                         //~1301R~
    {                                                              //~1301R~
    	UiData uidata=(UiData)Pparm;                               //~1302I~
        Bitmap srcbitmap=uidata.srcbitmap;                            //~1302R~
        if (srcbitmap.isRecycled())  //board close(set recycled) is by asynclonouse thread//~1517I~
        {                                                          //~1517I~
        	if (Dump.C) Dump.println("Graphics:skip drawBitmap by  bitmap is recycled:"+srcbitmap.toString());//~1517I~//~1A6AR~
        	return;                                                //~1517I~
        }                                                          //~1517I~
        Rect srcrect=uidata.srcrect;                               //~1302I~
        Rect destrect=uidata.destrect;
        if (Dump.C) Dump.println("Graphic drawBitmapUI androidcanvas="+androidCanvas.toString());//~1301I~//~1513R~//~@@@@R~//~1A6AR~
        if (Dump.X) Dump.println("X:Graphic drawBitmapUI srcbitmap="+srcbitmap.toString()+",density="+srcbitmap.getDensity());//~1Ag9R~
        if (Dump.C) Dump.println("src bitmap rect="+srcbitmap.getWidth()+","+srcbitmap.getHeight());//~1506R~//~1A6AR~
        if (Dump.X) Dump.println("X:Graphics.drawBitmapUI drawImageUI image srcrect="+srcrect.toString()+",dest="+destrect.toString());//~1228R~//~1506R~//~1A6AR~//~1Ag9R~
        if (Dump.C) Dump.println("Canvas drawBitmapUI width="+androidCanvas.getWidth()+",matrix="+androidCanvas.getMatrix().toString());//~1506R~//~1A6AR~
        try                                                        //~1513I~
        {                                                          //~1513I~
	        testDumpX("drawBitmapUI",destrect.left,destrect.top);        //~1Ag9I~
        	androidCanvas.drawBitmap(srcbitmap,srcrect,destrect,paintbitmap);    //~1301R~//~1303R~//~1513R~
	        testDumpX("drawBitmapUI end",destrect.left,destrect.top);//~1Ag9I~
        }                                                          //~1513I~
        catch(Exception e)                                        //~1513I~
        {                                                          //~1513I~
        	Dump.println(e,"Graphics:drawBitmapUI");               //~1513I~
        }                                                          //~1513I~
    }                                                              //~1301R~
//**************                                                   //~1117I~
    public void drawImage(Image Pimage,int Pdx1,int Pdy1,Object/*ImageObserver*/ Pobserver)//~1117I~
    {                                                              //~1117I~
  		Bitmap srcbitmap=Pimage.bitmap;                              //~1117I~//~1302R~//~1524R~
  		int w=srcbitmap.getWidth();                                   //~1117I~//~1524R~
  		int h=srcbitmap.getHeight();                                  //~1117I~//~1524R~
        if (Dump.X) Dump.println("X:Graphic:drawImage Object dx1="+Pdx1+",dy1="+Pdy1+",srcImage="+Pimage.toString()+",srcBitmap="+srcbitmap.toString());//~1Ag9R~
	    testDumpX("drawImage ovserver",Pdx1,Pdy1);                  //~1Ag9I~
    	drawImage(Pimage,Pdx1,Pdy1,Pdx1+w,Pdy1+h,0,0,w,h,Pobserver);//~1117I~//~1524R~
        if (Dump.X) Dump.println("X:Graphic:drawImage Object end");//~1Ag9R~
	}                                                              //~1117I~
//**************	rene.lister.ListerPanel                        //~1214I~
    public void drawImage(Image Pimage,int Pdx1,int Pdy1,int Pw,int Ph,Panel Ppanel)//~1214R~
    {                                                              //~1214I~
    	                                                           //~1Ag9I~
        if (Dump.C) Dump.println("Graphic:drawImage panel");       //~1Ag9I~
		if (Ppanel instanceof BigLabel)                            //~1414I~
        {                                                          //~1414I~
		    testDumpX("drawImage panel panel",Pdx1,Pdy1);           //~1Ag9I~
        	((BigLabel)Ppanel).drawImage(Pimage,Pdx1,Pdy1,Pw,Ph);   //~1414I~
        }                                                          //~1414I~
		testDumpX("drawImage panel",Pdx1,Pdy1);                     //~1Ag9I~
	    drawImage(Pimage,Pdx1,Pdy1,Pdx1+Pw,Pdy1+Ph,0,0,Pw,Ph,null);//~1214R~
	}                                                              //~1214I~
//************** for piece on board                                //~@@@@I~
    public void drawImage(Image Pimage,int Pdx1,int Pdy1)          //~@@@@I~
    {                                                              //~@@@@I~
  		Bitmap srcbitmap=Pimage.bitmap;                            //~@@@@I~
  		int w=srcbitmap.getWidth();                                //~@@@@I~
  		int h=srcbitmap.getHeight();                               //~@@@@I~
        if (Dump.C) Dump.println("Graphics drawImage x="+Pdx1+",y="+Pdy1+",src w="+w+",h="+h);//~@@@@I~//~1A6AR~
	    drawImage(Pimage,Pdx1,Pdy1,Pdx1+w,Pdy1+h,0,0,w,h,null);    //~@@@@I~
	}                                                              //~@@@@I~
//************** for BigLabel draw bitmap to nadroid ImageView     //~1414I~
    public void setBitmap(ImageView Pview,Image Pimage)            //~1414I~
    {                                                              //~1414I~
    	UiData data=new UiData(Pimage.bitmap,Pview);              //~1414I~
		runOnUiThread(CASE_SETBITMAP,data);                        //~1414I~
	}                                                              //~1414I~
//**************                                                   //~1414I~
    public void setBitmapUI(Object Pparm)                          //~1414I~
    {                                                              //~1414I~
    	UiData uidata=(UiData)Pparm;                               //~1414I~
        Bitmap bitmap=uidata.srcbitmap;                            //~1414I~
        if (bitmap.isRecycled())  //board close(set recycled) is by asynclonouse thread//~1517I~
        {                                                          //~1517I~
        	if (Dump.C) Dump.println("Graphics:skip setBitmap by  bitmap is recycled:"+bitmap.toString());//~1517I~//~1A6AR~
        	return;                                                //~1517I~
        }                                                          //~1517I~
        ImageView v=(ImageView)(uidata.view);                                        //~1414I~
        if (Dump.X) Dump.println("X:Graphics setBitMapUi");        //~1Ag9I~
		testDumpX("setBitmapUI",0,0);                               //~1Ag9I~
	    v.setImageBitmap(bitmap);                                  //~1414I~
	}                                                              //~1414I~
//**************                                                   //~1117I~
    public void drawArc(int Px,int Py,int Pw,int Ph,int Pstartangle,int Parcangle/*"-":clockwise*/)//~1117I~
    {                                                              //~1117I~
    	RectF oval=new RectF(new Rect(Px,Py,Px+Pw,Py+Ph));         //~1117I~
		testDumpX("drawArc",Px,Py);                                 //~1Ag9I~
	    androidCanvas.drawArc(oval,(float)Pstartangle,-(float)Parcangle,true/*incluse center*/,paint);//~1117I~//~1120R~
	}                                                              //~1117I~
//**************                                                   //~1117I~
    public void fillOval(int Px,int Py,int Pw,int Ph)              //~1117I~
    {                                                              //~1117I~
    	RectF oval=new RectF(new Rect(Px,Py,Px+Pw,Py+Ph));         //~1117I~
		testDumpX("fillOval",Px,Py);                                //~1Ag9I~
	    androidCanvas.drawOval(oval,paintfill);                           //~1117R~//~1120R~
	}                                                              //~1117I~
//**************                                                   //~1117I~
    public void drawOval(int Px,int Py,int Pw,int Ph)              //~1117I~
    {                                                              //~1117I~
    	RectF oval=new RectF(new Rect(Px,Py,Px+Pw,Py+Ph));         //~1117I~
		testDumpX("drawOval",Px,Py);                                //~1Ag9I~
	    androidCanvas.drawOval(oval,paint);                               //~1117I~//~1120R~
	}                                                              //~1117I~
//**************                                                   //~1117I~
    public void drawRect(int Px,int Py,int Pw,int Ph)              //~1117I~
    {                                                              //~1117I~
    	if (Dump.C) Dump.println("Graphics.drawRect androidCanvas="+androidCanvas.toString());//~@@@@I~//~1A6AR~
    	if (Dump.X) Dump.println("Graphics.drawRect x="+Px+",y="+Py+",w="+Pw+"h="+Ph);//~@@@@I~//~1A6AR~//~1Ag9R~
    	Rect rect=new Rect(Px,Py,Px+Pw,Py+Ph);                         //~1117I~
		testDumpX("drawRect",Px,Py);                                //~1Ag9I~
	    androidCanvas.drawRect(rect,paint);                               //~1117I~//~1120R~
	}                                                              //~1117I~
//**************                                                   //~@@@@I~
    public void drawRect(Color Pcolor,int Plinewidth,int Px,int Py,int Pw,int Ph)//~@@@@I~
    {                                                              //~@@@@I~
    	if (Dump.C) Dump.println("Graphics.drawRect stroke androidCanvas="+androidCanvas.toString());//~@@@@I~//~1A6AR~
    	if (Dump.X) Dump.println("Graphics.drawRect stroke x="+Px+",y="+Py+",w="+Pw+"h="+Ph);//~@@@@I~//~1A6AR~//~1Ag9R~
        paintStrokeRect.setColor(Pcolor.getRGB());                 //~@@@@I~
        paintStrokeRect.setStrokeWidth((float)Plinewidth);         //~@@@@I~
    	Rect rect=new Rect(Px,Py,Px+Pw,Py+Ph);                     //~@@@@I~
		testDumpX("drawRect color",Px,Py);                          //~1Ag9I~
	    androidCanvas.drawRect(rect,paintStrokeRect);              //~@@@@I~
	}                                                              //~@@@@I~
//**************                                                   //~1117I~
    public void drawString(String s,int Px,int Py)                 //~1117I~
    {                                                              //~1117I~
		testDumpX("drawText",Px,Py);                                //~1Ag9I~
	    androidCanvas.drawText(s,(float)Px,(float)Py,painttext);          //~1117I~//~1120R~
	}                                                              //~1117I~
//************** for rene.viewer.Line                              //~1212I~
    public void drawChars(char [] Pchars,int Poffs,int Plen,int Px,int Py)//~1414R~
    {                                                              //~1212I~
//  	if (Dump.C) Dump.println("Graphics drawChars:offs="+Poffs+",len="+Plen+",x="+Px+",y="+Py);//~1506R~//~2C15R~//~1A6AR~
		testDumpX("drawChars",Px,Py);                               //~1Ag9I~
	    drawString(new String(Pchars,Poffs,Plen),Px,Py);           //~1212I~
	}                                                              //~1212I~
//************** for rene.lister.ListerPanel                       //~1214I~
    public void dispose()                                          //~1214I~
    {                                                              //~1214I~
        Rect rect;                                                 //~1304R~
        rect=androidCanvas.getClipBounds();                        //~1304I~
        if (Dump.C) Dump.println("Graphics Canvas dispose canvas="+((Object)androidCanvas).toString());//~1506R~//~2C15R~//~1A6AR~//~1Ag9R~
        if (Dump.C) Dump.println("Graphics Canvas dispose rect="+rect.top+"-"+rect.left+","+rect.bottom+"-"+rect.right);//~1506R~//~2C15R~//~1A6AR~//~1Ag9R~
		if (tgtbitmap!=null)                                       //~1304I~
        {                                                          //~1304I~
			testDumpX("dispose.setClip",0,0);                       //~1Ag9I~
    		setClip(0,0,tgtbitmaprect.right,tgtbitmaprect.bottom); //~1304I~
        }                                                          //~1304I~
        rect=androidCanvas.getClipBounds();                        //~1303R~//~1304R~
        if (Dump.C) Dump.println("Graphics Canvas dispose after restore rect="+rect.top+"-"+rect.left+","+rect.bottom+"-"+rect.right);//~1303R~//~1506R~//~2C15R~//~1A6AR~
	}                                                              //~1214I~
//************** for rene.lister.ListerPanel                       //~1215I~
    public FontMetrics getFontMetrics(Font Pfont)                  //~1215I~
    {                                                              //~1215I~
    	return com.Asgts.awt.Canvas.getFontMetrics(Pfont);                       //~1215I~//~@@@@R~
	}                                                              //~1215I~
                                //~1215I~
    public FontMetrics getFontMetrics()                            //~1215I~
    {                                                              //~1215I~
    	return com.Asgts.awt.Canvas.getFontMetrics(font);         //~1215I~//~@@@@R~
	}                                                              //~1215I~
//********************************                                 //~1301R~
//* support All runOnUiThread                                      //~1301R~
//********************************                                 //~1301R~
    public void runOnUiThread(int Pcase,Object Pparm)              //~1301R~
    {                                                              //~1301R~
        caseUiThread=Pcase;                                        //~1301R~
        UiThread.runOnUiThreadWait(this,Pparm);               //~1301R~//~@@@@R~
    }                                                              //~1301R~
    @Override                                                      //~1301R~
    public void runOnUiThread(Object Pparm)                        //~1301R~
    {                                                              //~1301R~
        if (Dump.C) Dump.println("Graphic runOnUi case="+caseUiThread);//~1506R~//~1A6AR~
        switch(caseUiThread)                                       //~1301R~
        {                                                          //~1301R~
        case CASE_DRAWBITMAP:                                      //~1301R~
            drawBitmapUI(Pparm);                                   //~1301R~
            break;                                                 //~1301R~
        case CASE_SETBITMAP:                                       //~1414I~
            setBitmapUI(Pparm);                                    //~1414I~
            break;                                                 //~1414I~
        }                                                          //~1301R~
    }                                                              //~1301R~
    class UiData                                                   //~1302I~
    {                                                              //~1302I~
        Bitmap srcbitmap;                                             //~1302I~
        View view;                                                 //~1414I~
        Rect srcrect;                                              //~1302I~
        Rect destrect;                                            //~1302I~
        public UiData(Bitmap Pbitmap,Rect Psrcrect,Rect Pdestrect)//~1302I~
        {                                                          //~1302I~
        	srcbitmap=Pbitmap;srcrect=Psrcrect;destrect=Pdestrect;    //~1302I~
        }                                                          //~1302I~
        public UiData(Bitmap Pbitmap,View Pview)                   //~1414I~
        {                                                          //~1414I~
        	srcbitmap=Pbitmap;view=Pview;                          //~1414I~
        }                                                          //~1414I~
    }                                                              //~1302I~
//*****************************************************************************//~1Ag9I~
    public void recycle()                                          //~1Ag9I~
    {                                                              //~1Ag9I~
    	if (tgtbitmap!=null)                                       //~1Ag9I~
        {                                                          //~1Ag9I~
	    	if (Dump.Y) Dump.println("Graphics:recycle ref cleared="+tgtbitmap.toString()+",size="+Image.getByteCount(tgtbitmap));//~1Ag9I~
    		tgtbitmap=null;                                        //~1Ag9I~
        }                                                          //~1Ag9I~
	}                                                              //~1Ag9I~
//*****************************************************************************//~1Ag9I~
    public void recycle(boolean PclearCanvas)                      //~1Ag9I~
    {                                                              //~1Ag9I~
	    if (Dump.Y) Dump.println("Graphics:recycle claerCanvas");  //~1Ag9I~
        recycle();                                                 //~1Ag9I~
        if (PclearCanvas)                                          //~1Ag9I~
    		androidCanvas=null;                                    //~1Ag9I~
	}                                                              //~1Ag9I~
//*****************************************************************************//~1Ag9I~
    public void testDumpX(String Pfunc,int Px,int Py)	           //+1Ag9R~
    {                                                              //~1Ag9I~
        return;                                                    //~1Ag9I~
//@@@@test                                                         //+1Ag9I~
//        int boxSize=AG.ajagocCanvas.boxSize;                     //+1Ag9R~
//        if (AG.ajagocCanvas!=null && boxSize!=0)                 //~1Ag9R~
//        {                                                        //~1Ag9R~
//            int posX,posY,boardOrigin=AG.ajagocCanvas.boardOrigin;//~1Ag9R~
//            int redundancy=AG.ajagocCanvas.boxSize/4,boardEdge=AG.ajagocCanvas.boardEdge,paternSize=AG.ajagocCanvas.paternSize;//~1Ag9R~
//            int roundup=boxSize/2;                               //~1Ag9R~
//            posX=Px-boardOrigin;                                 //~1Ag9R~
//            posY=Py-boardOrigin;                                 //~1Ag9R~
//            if (posX<-redundancy)                                //~1Ag9R~
//                posX=Px;                                         //~1Ag9R~
//            else                                                 //~1Ag9R~
//            if (posX>boardEdge+redundancy)                       //~1Ag9R~
//                posX=Px;                                         //~1Ag9R~
//            else                                                 //~1Ag9R~
//                posX=(posX+roundup)/boxSize;                     //~1Ag9R~
//            if (posY<-redundancy)                                //~1Ag9R~
//                posY=Py;                                         //~1Ag9R~
//            else                                                 //~1Ag9R~
//            if (posY>boardEdge+redundancy)                       //~1Ag9R~
//                posY=Py;                                         //~1Ag9R~
//            else                                                 //~1Ag9R~
//                posY=(posY+roundup)/boxSize;                     //~1Ag9R~
//            if (this==AG.gBoardImage)                            //~1Ag9R~
//                Dump.println("X:Graphics.testDumpX BoardImage "+Pfunc+",i="+posX+",j="+posY);//~1Ag9R~
//            else                                                 //~1Ag9R~
//            if (this==AG.gActiveImage)                           //~1Ag9R~
//                Dump.println("X:Graphics.testDumpX ActiveImage "+Pfunc+",i="+posX+",j="+posY);//~1Ag9R~
//            else                                                 //~1Ag9R~
//                Dump.println("X:Graphics.testDumpX Not ActiveImage/BoardImage "+Pfunc+",i="+posX+",j="+posY);//~1Ag9R~
//        }                                                        //~1Ag9R~
//        else                                                     //~1Ag9R~
//        {                                                        //~1Ag9R~
//            if (this==AG.gBoardImage)                            //~1Ag9R~
//                Dump.println("X:Graphics.testDumpX BoardImage "+Pfunc+",x="+Px+",y="+Py);//~1Ag9R~
//            else                                                 //~1Ag9R~
//            if (this==AG.gActiveImage)                           //~1Ag9R~
//                Dump.println("X:Graphics.testDumpX ActiveImage "+Pfunc+",x="+Px+",y="+Py);//~1Ag9R~
//            else                                                 //~1Ag9R~
//                Dump.println("X:Graphics.testDumpX Not ActiveImage/BoardImage "+Pfunc+",x="+Px+",y="+Py);//~1Ag9R~
//        }                                                        //~1Ag9R~
    }                                                              //~1Ag9I~
}//class                                                           //~1112I~
