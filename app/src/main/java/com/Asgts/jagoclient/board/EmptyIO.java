//*CID://+1Ag6R~: update#= 142;                                    //+1Ag6R~
//**********************************************************************//~1107I~
//1Ag6 2016/1011  2016/10/09 It is cause of OutOfMemory? AjagoEmpty missed to close input stream//+1Ag6I~
//**********************************************************************//~1107I~
package com.Asgts.jagoclient.board;                                         //~1107R~  //~1108R~//~1109R~//~2C26R~//~2C27R~//~@@@@R~

import jagoclient.Dump;
import jagoclient.board.Board;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.Asgts.Prop;                                         //~2C26R~//~@@@@R~
import com.Asgts.awt.Color;                                        //~@@@@R~
import com.Asgts.awt.Image;                                         //~2C26R~//~@@@@R~





public class EmptyIO                                            //~1312R~//~2C26R~//~2C27R~//~@@@@R~
{                                                                  //~0914I~
	public Image staticImage;                                      //~1514R~
	public Image staticShadowImage;                                //~1514R~
    private static Integer lockword=new Integer(0);                //~1514R~
	public EmptyIO()                                //~0914R~//~dataR~//~1107R~//~1111R~//~1312R~//~2C26R~//~2C27R~//~@@@@R~
    {                                                              //~0914I~
    }                                                              //~0914I~
//*************************                                        //~1109I~//~1111I~//~1122M~
	public boolean load(int Pw,int Ph,Color Pc,boolean Pshadows,int Pox,int Poy,int Pd)//~1514R~
    {                                                              //~1120I~//~1122M~
    	InputStream is;                                            //~1312I~
        Bitmap bm;                                                 //~1312I~
        String filename=getFilename(Pw,Ph,Pc,Pox,Poy,Pd);           //~1312I~
        if (Dump.Y) Dump.println("Bitmap-load start:"+filename);   //~1506R~
        if (Pshadows)                                              //~1312I~
        {                                                          //~1312I~
        	synchronized(lockword)                                 //~1514I~
            {                                                      //~1514I~
                is=Prop.openInputData(filename+"-Shadow");    //~1514R~//~2C26R~
                if (is==null)                                      //~1514R~
                    return false;                                  //~1514R~
                bm=BitmapFactory.decodeStream(is);                 //~1514R~
            }                                                      //~1514I~
            try                                                    //+1Ag6I~
            {                                                      //+1Ag6I~
                is.close();                                        //+1Ag6I~
            }                                                      //+1Ag6I~
            catch(IOException e)                                   //+1Ag6I~
            {                                                      //+1Ag6I~
                Dump.println(e,"EmptyIO:load close failed"+filename);//+1Ag6I~
            }                                                      //+1Ag6I~
            if (bm==null)                                               //~1312I~
            	return false;                                      //~1312I~
//        	if (Dump.Y) Dump.println("Bitmap-load shadow end:"+filename+",bitmap="+((Object)bm).toString()+"bitmapByte="+bm.getByteCount());//Api12 //~1506R~//~@@@@R~
         	if (Dump.Y) Dump.println("Bitmap-load shadow end:"+filename+",bitmap="+((Object)bm).toString());//~@@@@I~
            staticShadowImage=new Image(Pw,Ph,bm);                 //~1312I~
        }                                                          //~1312I~
        synchronized(lockword)                                     //~1514I~
        {                                                          //~1514I~
        	is=Prop.openInputData(filename);                  //~1514R~//~2C26R~
        	if (is==null)                                          //~1514R~
            	return false;                                      //~1514R~
        	bm=BitmapFactory.decodeStream(is);                     //~1514R~
//        	if (Dump.Y) Dump.println("Bitmap-load end:"+filename+",bitmap="+((Object)bm).toString()+"bitmapByte="+bm.getByteCount());//~@@@@R~
        	if (Dump.Y) Dump.println("Bitmap-load end:"+filename+",bitmap="+((Object)bm).toString());//~@@@@I~
        }                                                          //~1514I~
        try                                                        //+1Ag6I~
        {                                                          //+1Ag6I~
            is.close();                                            //+1Ag6I~
        }                                                          //+1Ag6I~
        catch(IOException e)                                       //+1Ag6I~
        {                                                          //+1Ag6I~
            Dump.println(e,"EmptyIO:load close failed"+filename);  //+1Ag6I~
        }                                                          //+1Ag6I~
        if (bm==null)                                                   //~1312I~
            return false;                                          //~1312I~
        staticImage=new Image(Pw,Ph,bm);                           //~1312I~
        if (Dump.Y) Dump.println("Bitmap-load end:"+filename+",w="+bm.getWidth()+",h="+bm.getHeight()+"="+((Object)bm).toString());//~@@@@R~
        return true;                                               //~1312R~
    }                                                              //~1120I~//~1122M~
//*************************                                        //~1312I~
	public static void save(Board Pboard,Image PstaticShadow,Image Pstatic,int Pw,int Ph,Color Pc,boolean Pshadows,int Pox,int Poy,int Pd)//~1514R~
    {                                                              //~1312I~
		byte[] bytedata;                                           //~1312I~
        String filename=getFilename(Pw,Ph,Pc,Pox,Poy,Pd);           //~1312I~
        if (Dump.Y) Dump.println("Bitmap-save start:"+filename);   //~1506R~
        if (Pshadows)                                               //~1312I~
        {                                                          //~1312I~
			bytedata=bmp2byte(PstaticShadow.bitmap);               //~1312I~
	        synchronized(lockword)                                 //~1514I~
    	    {                                                      //~1514I~
    			Prop.writeOutputData(filename+"-Shadow",bytedata);//~1514R~//~2C26R~
        	}                                                      //~1514R~
	        Pboard.parentWindow.recyclePrepare(PstaticShadow.bitmap);	//recycled at trywood,for the case closed before it//~1514I~
        }                                                          //~1514I~
        bytedata=bmp2byte(Pstatic.bitmap);                         //~1312I~
	    synchronized(lockword)                                     //~1514I~
    	{                                                          //~1514I~
	        Prop.writeOutputData(filename,bytedata);          //~1514R~//~2C26R~
        }                                                          //~1514I~
        Pboard.parentWindow.recyclePrepare(Pstatic.bitmap);	       //~1514I~
        if (Dump.Y) Dump.println("Bitmap-save end:"+filename+",obj="+((Object)Pstatic.bitmap).toString());//~1506R~
    }                                                              //~1312I~
//*************************                                        //~1312I~
	public static String getFilename(int Pw,int Ph,Color Pc,int Pox,int Poy,int Pd)//~1312I~
    {                                                              //~1312I~
        return ("Emptybitmap.W"+Pw+"H"+Ph+"C"+Integer.toHexString(Pc.getRGB())//~1312R~
				+"Ox"+Pox+"Oy"+Poy+"D"+Pd);                        //~1312R~
    }                                                              //~1312I~
//*************************                                        //~1312I~
	public static byte[] bmp2byte(Bitmap Pbitmap)                         //~1312I~
    {                                                              //~1312I~
    	Bitmap.CompressFormat fmt=Bitmap.CompressFormat.PNG;       //~1312I~
        int quality=100;       //100% no meaning for PNG           //~1312I~
    	ByteArrayOutputStream os=new ByteArrayOutputStream();      //~1312I~
        Pbitmap.compress(fmt,quality,os);                          //~1312I~
        return os.toByteArray();                                   //~1312I~
    }                                                              //~1312I~
}//class AjagoView                                                 //~dataR~
