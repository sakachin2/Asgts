//*CID://+1Ad9R~:                             update#=   24;       //~1A6AR~//~1Ad9R~
//******************************************************************//~1A4hR~
//1Ad9 2015/07/21 additional to 1Ad6(OutOfMemory)                  //~1Ad9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing//~1A6AI~
//1A4h 2014/12/03 catch OutOfMemory                                //~1A4hR~
//******************************************************************//~1A4hR~
package com.Asgts.awt;                                             //~@@@@R~

import jagoclient.Dump;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.awt.Graphics;                                     //~@@@@R~
import com.Asgts.awt.MemoryImageSource;                            //~@@@@R~
import com.Asgts.awt.Component;                                    //~@@@@R~

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//Image:for Board                                                  //~1117R~
public class Image                                                 //~1421R~
{                                                                  //~1112I~
    public static final int BOARD  =0;                             //~1227I~
    public static final int WOOD   =1;                             //~1227I~
    public static final int STONE  =2;                             //~1227I~
    public static final int OTHER  =3;                             //~1414I~
	private Graphics graphics;                                     //~1224R~
    public int width,height;                                       //~1425R~
	public  android.graphics.Canvas androidCanvas;                 //~1228I~
    public Bitmap bitmap;                                          //~1425R~
    public String iconFilename;                                    //~1425R~
//****************                                                 //~1417I~
    public Image(int Pw,int Ph)                                    //~1224R~
    {                                                              //~1117I~
    	width=Pw;
    	height=Ph;                                        //~1117I~
    	bitmap=createBitmap(null,width,height);                    //~1421I~
        androidCanvas=new android.graphics.Canvas(bitmap);        //~1226I~//~1312R~
    	graphics=new Graphics(this);                               //~1228R~
    	if (Dump.C) Dump.println("Bitmap w="+bitmap.getWidth()+",h="+bitmap.getHeight());//~1506R~//~1A6AR~
    }                                                              //~1117I~
//****************                                                 //~1417I~
//from CloseFrame.seticon-->Hashtable                              //~1417I~
//****************                                                 //~1417I~
    public Image(String Pfilename)                                 //~1417I~
    {                                                              //~1417I~
        if (Dump.C) Dump.println("Image Iconfilename="+Pfilename); //~1506R~//~1A6AR~
        iconFilename=Pfilename;                                    //~1417I~
    }                                                              //~1417I~
//****************                                                 //~1312I~
    public Image(int Pw,int Ph,Bitmap Pbitmap)                     //~1312I~
    {                                                              //~1312I~
    	width=Pw;                                                  //~1312I~
    	height=Ph;                                                 //~1312I~
    	bitmap=Pbitmap;                                            //~1312I~
        androidCanvas=null;	//immutable                            //~1312R~
    	graphics=new Graphics(this);                               //~1312I~
    	if (Dump.C) Dump.println("Image:Bitmap from image file w="+bitmap.getWidth()+",h="+bitmap.getHeight()+"="+bitmap.toString());//~@@@@R~//~1A6AR~
    }                                                              //~1312I~
//*from recycleImage                                               //~1227I~
    private Image(int Pimagetype,int Pw,int Ph)                    //~1227I~
    {                                                              //~1227I~
    	width=Pw;                                                  //~1227I~
    	height=Ph;                                                 //~1227I~
        androidCanvas=null;	//immutable                            //~1227I~
        graphics=null;                                             //~1227I~
    }                                                              //~1227I~
//**********                                                       //~1227I~
//from Canvas<--Board for board                                    //~1227I~
    public static Image createImage(int Pw,int Ph,Canvas Pcanvas)  //~1227R~
    {                                                              //~1227I~
    	int width=Pw;                                                  //~1227I~
    	int height=Ph;                                                 //~1227I~
        Image image;                                               //~1308I~
    //******************:                                          //~1308I~
      image=new Image(BOARD,width,height);                         //~1524R~
        image.bitmap=createBitmap(null,width,height);//~1227I~     //~1524R~
        if (Dump.C) Dump.println("Board Bitmap created w="+Pw+",h="+Ph);//~1227I~//~1524R~//~1A6AR~
        image.androidCanvas=new android.graphics.Canvas(image.bitmap);//~1524R~
        image.androidCanvas.setBitmap(image.bitmap); //required?     //~1301I~//~1524R~
        image.graphics=new Graphics(image);                    //~1228R~//~1524R~
        if (Dump.C) Dump.println("CreateImage@@@@ Board bitmap="+((Object)image.bitmap).toString()+",androidCanvas="+((Object)image.androidCanvas).toString());//~1228I~//~1524R~//~1A6AR~
    	if (Dump.C) Dump.println("Bitmap w="+image.bitmap.getWidth()+",h="+image.bitmap.getHeight());//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("createImage Pcanvas"+((Object)image.bitmap).toString());//~1506R~//~1A6AR~
        return image;                                              //~1227I~
    }                                                              //~1227I~
//for BigTimer                                                     //~1414I~
    public static Image createImage(int Pw,int Ph)                 //~1414I~
    {                                                              //~1414I~
    	int width=Pw;                                              //~1414I~
    	int height=Ph;                                             //~1414I~
        Image image;                                               //~1414I~
    //******************:                                          //~1414I~
		image=new Image(OTHER,Pw,Ph);                              //~1414I~
    	image.bitmap=createBitmap(null,width,height);              //~1421R~
        if (Dump.C) Dump.println("BigTimer Bitmap created w="+Pw+",h="+Ph);//~1506R~//~1A6AR~
        image.androidCanvas=new android.graphics.Canvas(image.bitmap);//~1414I~
        image.androidCanvas.setBitmap(image.bitmap); //required?   //~1414I~
        image.graphics=new Graphics(image);                        //~1414I~
        if (Dump.C) Dump.println("CreateImage BigTimer bitmap="+image.bitmap.toString()+",androidCanvas="+image.androidCanvas.toString());//~1506R~//~1A6AR~
        return image;                                              //~1414I~
    }                                                              //~1414I~
//from Canvas<--Board for stone or woodpaint                       //~1227R~
    public static Image createImage(MemoryImageSource Pmis,Canvas Pcanvas)	//for stone//~1227R~
    {                                                              //~1227I~
    	int width=Pmis.width;                                      //~1227R~
    	int height=Pmis.height;                                    //~1227R~
        Image image;                                               //~1308I~
    //**********                                                   //~1308I~
        if (width>AG.scrWidth/2)	//woodpaint                    //~1227I~
        	return createImage(Pmis,(Component)Pcanvas);	//for Woodpaint//~1227I~
    //*stone                                                       //~1227I~
        int [] bitmapsrc=Pmis.pixel;                               //~1227I~
        if (Dump.C) Dump.println("MemorySourceImage stone width="+width);//~1506R~//~1A6AR~
        image=new Image(STONE,width,height);                       //~1524R~
        image.bitmap=createBitmap(bitmapsrc,width,height);         //~1524R~
        if (Dump.C) Dump.println("Stone Bitmap created w="+width+",h="+height);//~1227I~//~1524R~//~1A6AR~
    	if (Dump.C) Dump.println("Bitmap w="+image.bitmap.getWidth()+",h="+image.bitmap.getHeight());//~1227I~//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("createImage@@@@ stone image="+((Object)image).toString()+",bitmap="+((Object)image.bitmap).toString());//~1228R~//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("createImage@@@@ stone bitmap="+((Object)(image.bitmap)).toString()+",bitmap="+((Object)image.bitmap).toString());//~1506R~//~1A6AR~
        return image;                                              //~1227I~
    }                                                              //~1227I~
//from Component<--woodpaint ***                                   //~1227I~
    public static Image createImage(MemoryImageSource Pmis,Component Pcomconent)	//for Woodpaint//~1227R~
    {
    	int width=Pmis.width;                                      //~1227R~
    	int height=Pmis.height;                                    //~1227R~
        int [] bitmapsrc=Pmis.pixel;                              //~1120I~
        Image image;                                               //~1308I~
    //************                                                 //~1308I~
    	if (Dump.C) Dump.println("Woodpaint Bitmap w="+width+",height="+height);//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("MemorySourceImage createwood width="+width);//~1506R~//~1A6AR~
        image=new Image(WOOD,width,height);                        //~1524R~
        image.bitmap=createBitmap(bitmapsrc,width,height);         //~1524R~
        if (Dump.C) Dump.println("Woodpaint created w="+width+",h="+height);//~1227I~//~1524R~//~1A6AR~
    	if (Dump.C) Dump.println("Bitmap w="+image.bitmap.getWidth()+",h="+image.bitmap.getHeight());//~1120I~//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("createImage@@@@ Wood image ="+((Object)(image)).toString());//~1227I~//~1506R~//~1A6AR~
        if (Dump.C) Dump.println("createImage@@@@ Wood bitmap="+((Object)(image.bitmap)).toString());//~1506R~//~1A6AR~
        return image;                                              //~1227I~
    }                                                              //~1120I~
//*****************                                                //~1227R~
    public Graphics getGraphics()  //from BigLabel,Lister,TextDisplay//~1224R~
    {                                                              //~1224R~
        return graphics;    //Asgts.Graphics;null if immutable    //~1224R~//~@@@@R~
    }                                                              //~1224R~
    public int getWidth(Object PimageObserver)                     //~1120R~
    {                                                              //~1120I~
    	return width;                                              //~1120I~
    }                                                              //~1120I~
    public int getHeight(Object PimageObserver)                             //~1214R~//~1215R~
    {                                                              //~1214I~
    	return height;                                             //~1214I~
    }                                                              //~1214I~
    public void recycle()                                          //~1227I~
    {                                                              //~1227I~
        if (bitmap==null)                                          //~1422I~
        	return;                                                //~1422I~
        if (Dump.C) Dump.println("Image recycle Image="+this.toString());//~@@@@R~//~1A6AR~
        if (!bitmap.isRecycled())                                  //~1422I~
        {                                                          //~1422I~
         	if (Dump.C) Dump.println("Image:recycled w="+bitmap.getWidth()+",h="+bitmap.getHeight()+",byte="+getByteCount(bitmap)+"="+bitmap.toString());//~1A4hR~//~1A6AR~
	    	bitmap.recycle();                                      //~1422R~
        }                                                          //~1422I~
    }                                                              //~1227I~
    private static Bitmap createBitmap(int [] Pbitmapsrc,int Pw,int Ph)//~1421R~
    {                                                              //~1421I~
    	Bitmap bm;                                                 //~1421I~
    	if (Pbitmapsrc==null)                                      //~1421I~
    		bm=android.graphics.Bitmap.createBitmap(Pw,Ph,Bitmap.Config.ARGB_8888);//~1421I~
        else                                                       //~1421I~
    		bm=android.graphics.Bitmap.createBitmap(Pbitmapsrc,Pw,Ph,Bitmap.Config.ARGB_8888);//~1421I~
        if (Dump.C) Dump.println("Image:createBitmap w="+Pw+",h="+Ph+"byte="+getByteCount(bm)+"="+bm.toString()+",density="+bm.getDensity());//~1A4hR~//+1Ad9R~
        return bm;
    }                                                              //~1421I~
    public static Image loadPieceImage(int Presid,int Pw,int Ph)  //~@@@@R~
    {                                                              //~@@@@I~
	    if (Dump.C) Dump.println("Image:loadPieceImage resid="+Integer.toHexString(Presid)+",w="+Pw+",h="+Ph);//~@@@@I~//~1A6AR~
        Bitmap bm=BitmapFactory.decodeResource(AG.resource,Presid);//~@@@@R~
        Bitmap bmscaled=Bitmap.createScaledBitmap(bm,Pw,Ph,true/*filter*/);       //~@@@@I~
        if (bmscaled!=bm)                                          //~@@@@I~
        {                                                          //~@@@@I~
        	bm.recycle();                                          //~@@@@I~
  	        if (Dump.C) Dump.println("Image:loadPieceImage decoderesource recycle w="+bm.getWidth()+",h="+bm.getHeight()+"byte="+getByteCount(bm)+"="+bm.toString());//~1A4hR~//~1A6AR~
            bm=null;	//for GC ?                                 //~1Ad9I~
        }                                                          //~@@@@I~
        if (Dump.C) Dump.println("Image:loadPieceImage bmscaled w="+Pw+",h="+Ph+",bmscaled w="+bmscaled.getWidth()+",h="+bmscaled.getHeight()+",byte="+getByteCount(bmscaled)+",bitmap="+bmscaled.toString());//~1A4hR~//~1A6AR~
    	Image image=new Image(Pw,Ph,bmscaled);                     //~@@@@I~
        return image;                                              //~@@@@R~
    }                                                              //~@@@@I~
    //*******************************************                  //~1A4hR~
    static public int getByteCount(Bitmap Pbm)                     //~1A4hR~
    {                                                              //~1A4hR~
    	if (Pbm==null)                                             //~1A4hR~
        	return 0;                                              //~1A4hR~
        if (AG.osVersion>=AG.HONEYCOMB_MR1)  //android3.1          //~1A4hR~
            return getByteCount_V12(Pbm);                          //~1A4hR~
        return 0;                                                  //~1A4hR~
    }                                                              //~1A4hR~
	@TargetApi(AG.HONEYCOMB_MR1)                                   //~1A4hR~
    static private int getByteCount_V12(Bitmap Pbm)                //~1A4hR~
	{                                                              //~1A4hR~
        return Pbm.getByteCount();                                 //~1A4hR~
    }                                                              //~1A4hR~
}//class                                                           //~1112I~
