//*CID://+1Aj4R~:                             update#=   45;       //+1Aj4R~
//************************************************************************//~v102I~
//1Aj4 2017/02/04 (Ajagoc)Error Expected Resouce of Type string[ResourceType]//+1Aj4I~
//                It may occurs when inporting from eclipse to androidStudio.//+1Aj4I~
//                But finally this method is not used.             //+1Aj4I~
//                AjagoProp.java                                   //+1Aj4I~
//1Ah0 2016/10/15 v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~1Ah0I~
//1A42 2014/09/19 save to private if sdcard is not available       //~1A42I~
//1A3b 2013/05/30 MediaPlayer exception:setdataSourceFD failed status=0x80000000//~1A3bI~
//                if access file on sdcard(/storage/emulated/0/Asgts/sounds.//~1A3bI~
//                try /sdcard                                      //~1A3bI~
//1A1j 2013/03/19 change Help file encoding to utf8 (path change drop jagoclient from jagoclient/helptexts)//~1A1jI~
//1077:121208 control greeting by app start counter                //~v107I~
//1075:121207 control dumptrace by manifest debuggable option      //~v107I~
//1074:121207 no detail exception info for SDcard/resources        //~v107I~
//v102:120718 (Axe)miss serverlist when SDcard missing             //~v102I~
//************************************************************************//~v102I~
package com.Asgts;                                                 //~1110I~//~v107R~//~@@@@R~
                                                                   //~1110I~
                                                                   //~1110I~
import jagoclient.Dump;
import jagoclient.Global;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources.NotFoundException;
import android.os.Environment;
                                                                   //~1110I~
public class Prop                                             //~1110I~//~v107R~
{                                                                  //~1110I~
    public static final String GAMEFILE="GameFileFolder";          //~1402I~
                                                                   //~1402I~
	public static final String DATAFILE_PREFIX="save";             //~1309I~//~1313R~
	public static final String SDFILE_PREFIX="rename";             //~1423I~
	private static final int BUFFSZ=1024;                          //~1511R~
	private static final int BUFFSZ2=32760;                        //~1511I~
	private static boolean availableAjagoSD=true;                  //~1313I~
	private static String dirAjagoSD;                              //~1425R~
	private static int rawFileId;                                  //~1425R~
//********************************************************         //~1401I~
//*to intercept FileOuputStream of Global                          //~1401I~
//* return FileOutputStream for cfgfile on /data/data              //~1401I~
//********************************************************         //~1401I~
	public static String getOutputFilenameData(String Pfilename)   //~1401I~
	{                                                              //~1401I~
        String path,filename;                                      //~1401R~
                                           //~1401I~
    //***************************                                  //~1401I~
        if (Dump.Y) Dump.println("getFileOutputStreamData:"+Pfilename);//~1506R~
		filename=getConfigFilename(Pfilename);                     //~1401R~
        path=AG.context.getFilesDir()+"/"+filename;                //~1401I~
        if (Dump.Y) Dump.println("GetFileOutputStreamData path="+path);//~1506R~
        return path;                                               //~1401R~
	}                                                              //~1401I~
////********************************************************         //~1329I~//+1Aj4R~
////*to intercept FileInputStream of Global                          //~1329I~//+1Aj4R~
////* return filename for  cfgfile                                   //~1329I~//+1Aj4R~
////*   save.xxx.cfg on /data/data                                   //~1329I~//+1Aj4R~
////*   if not save copy from /res/raw to /data/data                 //~1329I~//+1Aj4R~
////    for other than cfg file return /data/data/fnm                //~1329I~//+1Aj4R~
////* return helpfilename on SDcard                                  //~1412I~//+1Aj4R~
////********************************************************         //~1329I~//+1Aj4R~
//    public static String getInputFilenameDataRaw(String Pfilename) //~1329R~//+1Aj4R~
//        throws NotFoundException                                   //~1329I~//+1Aj4R~
//    {                                                              //~1329I~//+1Aj4R~
//        String path,filename;                 //~1329I~            //~1401R~//+1Aj4R~
//    //***************************                                  //~1329I~//+1Aj4R~
//        if (Dump.Y) Dump.println("getFileInputFilenameDataRaw:"+Pfilename);//~1506R~//+1Aj4R~
//        if ((path=getHelpFilename(Pfilename))!=null)               //~1412I~//+1Aj4R~
//            return path;                                           //~1412I~//+1Aj4R~
//        filename=getConfigFilename(Pfilename);                     //~1401R~//+1Aj4R~
//        if (Dump.Y) Dump.println("GetInputFilenameDataRaw filename="+filename);//~1329I~//~1506R~//+1Aj4R~
//        path=AG.context.getFilesDir()+"/"+filename;                //~1401I~//+1Aj4R~
//        if (rawFileId==0)   //not cfg file                                 //~1329I~//~1401R~//+1Aj4R~
//            return path;                                       //~1329I~//~1401R~//+1Aj4R~
//        if ((new File(path)).exists())  //file on /data/data   //~1329I~//~1423R~//+1Aj4R~
//        {                                                          //~1423R~//+1Aj4R~
//            if (Dump.Y) Dump.println("GetFileInputFilenameDataRaw exist path="+path);//~1329I~//~1506R~//+1Aj4R~
//            return path;                                       //~1329I~//~1423R~//+1Aj4R~
//        }                                                          //~1423R~//+1Aj4R~
//        copyToDataDir(rawFileId,filename);                         //~1423R~//+1Aj4R~
//        return path;                                           //~1329I~//~1401R~//+1Aj4R~
//    }                                                              //~1329I~//+1Aj4R~
//********************************************************         //~1419R~
//*to intercept FileInputStream of Global                          //~1419R~
//* return inputStream for cfg/help file                           //~1419R~
//*   save.xxx.cfg on /data/data                                   //~1419R~
//*   if not save copy from /res/raw to /data/data                 //~1419R~
//    for other than cfg file return    /data/data/fnm             //~1419R~
//* return helpfile InputSream on SDcardm,if not found of asset/   //~1419R~
//********************************************************         //~1419R~
    public static InputStream getInputStreamDataRaw(String Pfilename)//~1419R~
        throws FileNotFoundException                               //~1419R~
    {                                                              //~1419R~
        String path,filename;                                      //~1419R~
        InputStream is;                                            //~1419R~
    //***************************                                  //~1419R~
        if (Dump.Y) Dump.println("getInputStreamDataRaw:"+Pfilename);//~1506R~
        if ((is=getInputStreamHelpFile(Pfilename))!=null)          //~1419R~
            return is;                                             //~1419R~
        filename=getConfigFilename(Pfilename);                     //~1419R~
        if (Dump.Y) Dump.println("getFileInputStreamDataRaw filename="+filename);//~1506R~
        path=AG.context.getFilesDir()+"/"+filename;                //~1419R~
        if (rawFileId==0)   //not cfg file                         //~1419R~
            return new FileInputStream(path);                      //~1419R~
        if (!chkConfigOnSD(filename))	//chk new file on SDcard   //~1423R~
        {                                                          //~1423M~
            if ((new File(path)).exists())  //file on /data/data   //~1423R~
            {                                                      //~1423R~
                if (Dump.Y) Dump.println("getInputStreamDataRaw exist path="+path);//~1506R~
                return new FileInputStream(path);                  //~1423R~
            }                                                      //~1423R~
            copyToDataDir(rawFileId,filename);                     //~1423R~
        }                                                          //~1423M~
        return new FileInputStream(path);                          //~1419R~
    }                                                              //~1419R~
//********************************************************         //~1401I~
//*get real filename                                               //~1401I~
//********************************************************         //~1401I~
	public static String getConfigFilename(String Pfilename)       //~1401I~
	{                                                              //~1401I~
        String filename,home;            //~1401I~
		int id=0;                                                  //~1401I~
    //***************************                                  //~1401I~
        if (Dump.Y) Dump.println("getConfigFilename:"+Pfilename);  //~1506R~
        home=Global.home();                                        //~1401I~
    	if (Pfilename.equals(home+".go.cfg"))                      //~1401I~
        	id=R.raw.go;                                           //~1401I~
        else                                                       //~1401I~
    	if (Pfilename.equals(home+".filter.cfg"))                  //~1401I~
        	id=R.raw.filter;                                       //~1401I~
        else                                                       //~1401I~
    	if (Pfilename.equals(home+".server.cfg"))                  //~1401I~
        	id=R.raw.server;                                       //~1401I~
        else                                                       //~1401I~
    	if (Pfilename.equals(home+".partner.cfg"))                 //~1401I~
        	id=R.raw.partner;                                      //~1401I~
        if (id!=0)                                                 //~1401I~
        	filename=DATAFILE_PREFIX+Pfilename.substring(home.length());//~1401I~
        else                                                       //~1401I~
        	filename=Pfilename;                                    //~1401I~
        rawFileId=id;                                              //~1401I~
        if (Dump.Y) Dump.println("getConfigFilename  filename="+filename+",id="+Integer.toHexString(id));//~1506R~
        return filename;                                           //~1401R~
	}                                                              //~1401I~
//********************************************************         //~1423I~
//*chk new cfg file on SDcard                                      //~1423I~
//*	if exit cpy to data/data then rename                           //~1423I~
//********************************************************         //~1423I~
	public static boolean chkConfigOnSD(String Pfilename)          //~1423R~
	{                                                              //~1423I~
    //***************************                                  //~1423I~
        if (Dump.Y) Dump.println("chkConfigOnSD:"+Pfilename);      //~1506R~
        String filenameSD=Pfilename.substring(DATAFILE_PREFIX.length()+1); //last name//~1423R~
        String cfgSDfile=getSDPath(filenameSD);   //path           //~1423R~
        if (cfgSDfile==null)                                       //~v102I~
        	return false;                                          //~v102I~
        if (!(new File(cfgSDfile)).exists())	//file on /data/data//~1423I~
        	return false;                                          //~1423I~
	    if (Dump.Y) Dump.println("chkConfigOnSD exist path="+cfgSDfile);//~1506R~
        if (!copyToDataDir(filenameSD,Pfilename))                  //~1423R~
        	return false;                                          //~1423I~
        if (renameFile(cfgSDfile))                             //~1423I~
        	return false;                                          //~1423I~
        return true;                                               //~1423I~
	}                                                              //~1423I~
////********************************************************         //~1412I~//+1Aj4R~
////*return SDCARD/jagoclient/hexlptext/subjext.txt if exist         //~1418R~//+1Aj4R~
////********************************************************         //~1412I~//+1Aj4R~
//    public static String getHelpFilename(String Pfilename)         //~1412I~//+1Aj4R~
//    {                                                              //~1412I~                                     //~1412I~                                               //~1412I~//+1Aj4R~
//    //***************************                                  //~1412I~//+1Aj4R~
//        if (Dump.Y) Dump.println("getHelpFilename:"+Pfilename);    //~1506R~//+1Aj4R~
////      if (!Pfilename.startsWith(Global.home()+"jagoclient/helptexts"))//~1412R~//~1A1jR~//+1Aj4R~
//        if (!Pfilename.startsWith(Global.home()+"helptexts"))      //~1A1jI~//+1Aj4R~
//            return null;                                           //~1412I~//+1Aj4R~
//        String helpfile=getSDPath(Pfilename);                             //~1412I~//+1Aj4R~
//      if (helpfile!=null)                                          //~v102I~//+1Aj4R~
//        if ((new File(helpfile)).exists())  //file on /data/data   //~1418I~//+1Aj4R~
//        {                                                          //~1418I~//+1Aj4R~
//            if (Dump.Y) Dump.println("GetHelpFilename exist path="+helpfile);//~1506R~//+1Aj4R~
//            return helpfile;                                           //~1418I~//+1Aj4R~
//        }                                                          //~1418I~//+1Aj4R~
//        helpfile=AG.resource.getText(R.raw.go).toString();         //~1418R~//+1Aj4R~
//        if ((new File(helpfile)).exists())  //file on /data/data   //~1418I~//+1Aj4R~
//        {                                                          //~1418I~//+1Aj4R~
//            if (Dump.Y) Dump.println("gethelpFilename raw/folder filename="+helpfile);//~1506R~//+1Aj4R~
//            return helpfile;                                       //~1418I~//+1Aj4R~
//        }                                                          //~1418I~//+1Aj4R~
//        if ((new File("/"+helpfile)).exists())  //file on /data/data//~1418I~//+1Aj4R~
//        {                                                          //~1418I~//+1Aj4R~
//            if (Dump.Y) Dump.println("gethelpFilename raw/folder filename="+helpfile);//~1506R~//+1Aj4R~
//            return helpfile;                                       //~1418I~//+1Aj4R~
//        }                                                          //~1418I~//+1Aj4R~
//        if ((new File("Ajagot1/"+helpfile)).exists())   //file on /data/data//~1418I~//+1Aj4R~
//        {                                                          //~1418I~//+1Aj4R~
//            if (Dump.Y) Dump.println("gethelpFilename raw/folder filename="+helpfile);//~1506R~//+1Aj4R~
//            return helpfile;                                       //~1418I~//+1Aj4R~
//        }                                                          //~1418I~//+1Aj4R~
//        AG.resource.openRawResource(R.raw.go);                     //~1418I~//+1Aj4R~
//        if (Dump.Y) Dump.println("gethelpFilename return filename="+Pfilename);//~1506R~//+1Aj4R~
//        return Pfilename;                                          //~1418R~//+1Aj4R~
//    }                                                              //~1412I~//+1Aj4R~
//********************************************************         //~1419I~
//*return InputStream for helpfile; SDCARD or asset folder         //~1419R~
//********************************************************         //~1419I~
	public static InputStream getInputStreamHelpFile(String Pfilename)
		throws FileNotFoundException
	{                                                              //~1419I~
    	InputStream is;                                            //~1419I~
        String filename,home;                                           //~1419I~
    //***************************                                  //~1419I~
        if (Dump.Y) Dump.println("getHelpFilename:"+Pfilename);    //~1506R~
        home=Global.home();//~1419I~
//  	if (!Pfilename.startsWith(home+"jagoclient/helptexts"))//~1419I~//~1A1jR~
    	if (!Pfilename.startsWith(home+"helptexts"))               //~1A1jI~
        	return null;                                           //~1419I~
        filename=Pfilename.substring(home.length());                //~1419I~
		is=openInputSD(filename);   //check SD card                //~1419I~
		if (is==null)                                              //~1419I~
			try
			{	
				is=AG.resource.getAssets().open(filename);             //~1419I~
			}
			catch(Exception e)
			{
				throw new FileNotFoundException();
			}
		return is;                                                 //~1419R~
	}                                                              //~1419I~
//********************************************************         //~1511I~
//*open Assetfile for Agnugo copy                                  //~1511I~
//********************************************************         //~1511I~
	public static AssetFileDescriptor openAssetFileFD(String Pfilename)//~1511I~
	{                                                              //~1511I~
    	AssetFileDescriptor fd;                                    //~1511I~
    //***************************                                  //~1511I~
        if (Dump.Y) Dump.println("openAssetFileFD:"+Pfilename);    //~1511I~
        try                                                        //~1511I~
        {                                                          //~1511I~
            fd=AG.resource.getAssets().openFd(Pfilename);          //~1511I~
        }                                                          //~1511I~
        catch(Exception e)                                         //~1511I~
        {                                                          //~1511I~
	        if (Dump.Y) Dump.println("openAssetFileFD failed:"+Pfilename);//~1511I~
            fd=null;                                               //~1511I~
        }                                                          //~1511I~
		return fd;                                                 //~1511I~
	}                                                              //~1511I~
//********************************************************         //~1511I~
//*open Assetfile for Agnugo copy                                  //~1511I~
//********************************************************         //~1511I~
	public static InputStream openAssetFile(String Pfilename)      //~1511I~
	{                                                              //~1511I~
    	InputStream is;                                            //~1511I~
    //***************************                                  //~1511I~
        if (Dump.Y) Dump.println("openAssetFile:"+Pfilename);      //~1511I~
        try                                                        //~1511I~
        {                                                          //~1511I~
            is=AG.resource.getAssets().open(Pfilename);            //~1511I~
        }                                                          //~1511I~
        catch(Exception e)                                         //~1511I~
        {                                                          //~1511I~
	        if (Dump.Y) Dump.println("openAssetFile failed:"+Pfilename);//~1511I~
            is=null;                                               //~1511I~
        }                                                          //~1511I~
		return is;                                                 //~1511I~
	}                                                              //~1511I~
//********************************************************         //~1511I~
//*open Assetfile for Agnugo copy                                  //~1511I~
//********************************************************         //~1511I~
	public static long getAssetFileSize(String Pfilename)          //~1511I~
	{                                                              //~1511I~
    	AssetFileDescriptor fd;                                    //~1511I~
        long sz;                                                   //~1511I~
    //***************************                                  //~1511I~
        fd=openAssetFileFD(Pfilename);                             //~1511I~
        if (fd==null)                                              //~1511I~
//      	sz=-1;                                                 //~1511I~//~1Ah0R~
        	return -1;  //no need to close                         //~1Ah0I~
        else                                                       //~1511I~
        	sz=fd.getLength();                                      //~1511I~
        try
         {
			fd.close();
		 }
        catch (IOException e)
         {
			Dump.println(e,"xception Asset openFD"+Pfilename);
			e.printStackTrace();
		}                                                //~1511I~
        return sz;                                                 //~1511I~
	}                                                              //~1511I~
//********************************************************         //~1327I~
//*copy /res/raw file to /data/data private dir                    //~1329I~
//*retur success/false                                             //~1329I~
//********************************************************         //~1329I~
	public static boolean copyToDataDir(int Prawresid,String Pfname)//~1327I~
	{                                                              //~1327I~
        InputStream is;                                       //~1327I~
		FileOutputStream fos;                                      //~1327I~
        int len;                                                   //~1327I~
        boolean success=true;                                      //~1327I~
        byte [] buff;                                              //~1327I~
	//*********************                                        //~1327I~
    	if (Dump.Y) Dump.println("copyToDataDir:"+Pfname);         //~1506R~
		is=AG.resource.openRawResource(Prawresid);	//read from res/raw//~1327I~
        if (is==null)  //1st time    no save.xxx on data/data     //~1327I~
        	return false;                                          //~1327I~
		fos=openOutputData(Pfname);	///data/data (no search SD)    //~1329R~
        if (fos==null)                                             //~1327I~
        	return false;                                          //~1327I~
        buff=new byte[BUFFSZ];                                     //~1327I~
        try                                                        //~1327I~
        {                                                          //~1327I~
        	for (;;)                                               //~1327I~
            {                                                      //~1327I~
        		len=is.read(buff);	                               //~1327I~
            	if (len<0)                                         //~1327I~
            		break;                                         //~1327I~
            	fos.write(buff,0,len);                             //~1327I~
            }                                                      //~1327I~
        }                                                          //~1327I~
        catch (Exception e)                                        //~1327I~
        {                                                          //~1327I~
        	success=false;                                         //~1327I~
            Dump.println(e,"copyToDataDir output exception "+Pfname);//~1327I~
        }//catch                                                   //~1327I~
        try
        {
        	is.close();                                               //~1327I~
        	fos.close();
        	if (Dump.Y) Dump.println("close copy to SDfailed"+Pfname);//~1506R~
        }
        catch(IOException e)
        {
        	success=false;
        	Dump.println(e,"close failed"+Pfname);//~1327I~
        }
        return success;                                            //~1327I~
	}                                                              //~1327I~
//********************************************************         //~1423I~
//*copy /sdcard/Ajagoc/cfg file to /data/data private dir          //~1423I~
//*retur success/false                                             //~1423I~
//********************************************************         //~1423I~
	public static boolean copyToDataDir(String Pfname,String PfnameData)//~1423I~
	{                                                              //~1423I~
        InputStream is;                                            //~1423I~
		FileOutputStream fos;                                      //~1423I~
        int len;                                                   //~1423I~
        boolean success=true;                                      //~1423I~
        byte [] buff;                                              //~1423I~
	//*********************                                        //~1423I~
    	if (Dump.Y) Dump.println("copyToDataDir:SD="+Pfname+",data="+PfnameData);//~1506R~
	    is=openInputSD(Pfname);                  //~1423I~
        if (is==null)                                             //~1423I~
        	return false;                                          //~1423I~
		fos=openOutputData(PfnameData);	///data/data (no search SD)//~1423R~
        if (fos==null)                                             //~1423I~
        	return false;                                          //~1423I~
        buff=new byte[BUFFSZ];                                     //~1423I~
        try                                                        //~1423I~
        {                                                          //~1423I~
        	for (;;)                                               //~1423I~
            {                                                      //~1423I~
        		len=is.read(buff);                                //~1423I~
            	if (len<0)                                         //~1423I~
            		break;                                         //~1423I~
            	fos.write(buff,0,len);                             //~1423I~
            }                                                      //~1423I~
        }                                                          //~1423I~
        catch (Exception e)                                        //~1423I~
        {                                                          //~1423I~
        	success=false;                                         //~1423I~
            Dump.println(e,"copyToDataDir output exception "+Pfname);//~1423I~
        }//catch                                                   //~1423I~
        try                                                        //~1423I~
        {                                                          //~1423I~
        	is.close();                                           //~1423I~
        	fos.close();                                           //~1423I~
        	if (Dump.Y) Dump.println("close copy to Data failed"+Pfname);//~1506R~
        }                                                          //~1423I~
        catch(IOException e)                                       //~1423I~
        {                                                          //~1423I~
        	success=false;                                         //~1423I~
        	Dump.println(e,"close failed"+Pfname);                 //~1423I~
        }                                                          //~1423I~
        return success;                                            //~1423I~
	}                                                              //~1423I~
//********************************************************         //~1511I~
//*copy /sdcard/Ajagoc/cfg file to /data/data private dir          //~1511I~
//*retur success/false                                             //~1511I~
//********************************************************         //~1511I~
	public static boolean copyAssetToDataDir(String Pfname,String PfnameData)//~1511I~
	{                                                              //~1511I~
        InputStream is;                                            //~1511I~
		FileOutputStream fos;                                      //~1511I~
        int len;                                                   //~1511I~
        boolean success=true;                                      //~1511I~
        byte [] buff;                                              //~1511I~
	//*********************                                        //~1511I~
    	if (Dump.Y) Dump.println("copyToDataDir:SD="+Pfname+",data="+PfnameData);//~1511I~
	    is=openAssetFile(Pfname);                                  //~1511I~
        if (is==null)                                              //~1511I~
        	return false;                                          //~1511I~
		fos=openOutputData(PfnameData);	///data/data (no search SD)//~1511I~
        if (fos==null)                                             //~1511I~
        	return false;                                          //~1511I~
        buff=new byte[BUFFSZ2];                                    //~1511I~
        try                                                        //~1511I~
        {                                                          //~1511I~
        	for (;;)                                               //~1511I~
            {                                                      //~1511I~
        		len=is.read(buff);                                 //~1511I~
            	if (len<0)                                         //~1511I~
            		break;                                         //~1511I~
            	fos.write(buff,0,len);                             //~1511I~
            }                                                      //~1511I~
        }                                                          //~1511I~
        catch (Exception e)                                        //~1511I~
        {                                                          //~1511I~
        	success=false;                                         //~1511I~
            Dump.println(e,"copyToDataDir output exception "+Pfname);//~1511I~
        }//catch                                                   //~1511I~
        try                                                        //~1511I~
        {                                                          //~1511I~
        	is.close();                                            //~1511I~
        	fos.close();                                           //~1511I~
        }                                                          //~1511I~
        catch(IOException e)                                       //~1511I~
        {                                                          //~1511I~
        	success=false;                                         //~1511I~
        	Dump.println(e,"close failed"+Pfname);                 //~1511I~
        }                                                          //~1511I~
        return success;                                            //~1511I~
	}                                                              //~1511I~
//********************************************************         //~1423I~
//*rename cfg on sdcard after copyed to data/data                  //~1423I~
//*retur success/false                                             //~1423I~
//********************************************************         //~1423I~
	public static boolean renameFile(String Pfname)                //~1423R~
	{                                                              //~1423I~
        boolean success=true;                                      //~1423I~                                            //~1423I~
	//*********************                                        //~1423I~
    	if (Dump.Y) Dump.println("renameFile="+Pfname);            //~1506R~
        File oldfile=new File(Pfname);                             //~1423R~
        if (!oldfile.exists())	//file on /data/data               //~1423I~
        	return false;                                          //~1423I~
        String ts=Utils.getTimeStamp(Utils.TS_DATE_TIME);            //~1425R~//~@@@@R~
        String newname=Pfname+"."+SDFILE_PREFIX+"."+ts;            //~1423R~
        File newfile=new File(newname);                            //~1423I~
        success=oldfile.renameTo(newfile);                              //~1423I~
        return success;                                            //~1423I~
	}                                                              //~1423I~
//*******************************************                      //~1423R~
    public static FileInputStream openInputData(String Pfname)        //~1309I~//~1312R~//~1327R~
    {                                                              //~1309I~
	    FileInputStream in=null;                                       //~1309I~//~1327R~
        if (Dump.Y) Dump.println("openInputStream:"+Pfname);       //~1506R~
        try                                                        //~1309I~
        {                                                          //~1309I~
            in=AG.context.openFileInput(Pfname);                   //~1309I~
	    	File f=new File(Pfname);                               //~1309I~
	    	if (Dump.Y) Dump.println("OpenInputData:file="+Pfname+",fullpath="+f.getAbsolutePath());//~1309I~//~1506R~
	    	if (Dump.Y) Dump.println("OpenInputData:file="+Pfname+",fullpathname="+f.getAbsoluteFile());//~1309I~//~1506R~
	    	if (Dump.Y) Dump.println("Test isiexst by File()="+f.exists());             //~1309I~//~1506R~
	    	if (Dump.Y) Dump.println("Test tiimestamp="+Long.toHexString(f.lastModified()));//~1309I~//~1506R~
            f=Environment.getDataDirectory();                      //~1309I~
	    	if (Dump.Y) Dump.println("DataDir Path="+f.getAbsolutePath());//~1309I~//~1506R~
	    	f=new File(AG.context.getFilesDir(),Pfname);	//android.app.contextImple//~1309I~
	    	if (Dump.Y) Dump.println(f.toString()+":isiexst="+f.exists());//~1309I~//~1506R~
	    	if (Dump.Y) Dump.println("tiimestamp="+Long.toHexString(f.lastModified()));//~1309I~//~1506R~
        }                                                          //~1309I~
        catch(FileNotFoundException e)                             //~1309I~
        {                                                          //~1309I~
//          Dump.println(e,"openInputData failed "+Pfname);  //~1309I~//~1312R~//~1329R~//~v107R~
            Dump.println("openInputData NotFound:"+Pfname);        //~v107I~
        }                                                          //~1309I~
        catch (Exception e)                                        //~1309I~
        {                                                          //~1309I~
            Dump.println(e,"openInputData exception "+Pfname);              //~1309I~//~1312R~//~1329R~
        }//catch                                                   //~1309I~
    	return in;                                                 //~1309I~
    }                                                              //~1309I~
//*********************************************                                                       //~1309I~//~1329R~
//*output to SD if avale else private *********                    //~1329I~
//*********************************************                    //~1329I~
    public static FileOutputStream openOutputData(String Pdir,String Pfname)//~1313I~
    {                                                              //~1313I~
        String fnm,path;                                           //~1313I~
    	if (Dump.Y) Dump.println("openOutputData dir="+Pdir+",file="+Pfname);//~1313I~//~1506R~
		path=getSDPath(Pdir);                                      //~1313R~
        if (path==null)	//no SDCard available                          //~1313I~
        	return openOutputData(Pfname);                         //~1313I~
        fnm=path+System.getProperty("file.separator")+Pfname;      //~1313R~
        if (Dump.Y) Dump.println("openoutputData on SDcard fnm="+fnm);                       //~1313I~//~1506R~
	    FileOutputStream out=null;
		try {
			out = new FileOutputStream(fnm);
		} catch (Exception e)                                      //~1329R~
		{
			Dump.println(e,"openOutputData Exception:"+fnm);       //~1329R~
		}                //~1313I~
    	return out;                                                //~1313I~
    }                                                              //~1313I~
//*********************************************                    //~1511I~
//*get private data file path                                      //~1511I~
//*********************************************                    //~1511I~
    public static String getDataFileFullpath(String Pfname)        //~1511I~
    {                                                              //~1511I~
    	if (Dump.Y) Dump.println("getDataFileFullpath:"+Pfname);   //~1511I~
		String path=AG.context.getFilesDir()+"/"+Pfname;                      //~1511I~
    	if (Dump.Y) Dump.println("getDataFileFullpath:"+path);     //~1511I~
        return path;                                               //~1511I~
    }                                                              //~1511I~
//*********************************************                    //~1511I~
//*get private data file size                                      //~1511I~
//*********************************************                    //~1511I~
    public static long getDataFileSize(String Pfname)              //~1511I~
    {                                                              //~1511I~
    	if (Dump.Y) Dump.println("getDataFileSize:"+Pfname);       //~1511I~
		String path=AG.context.getFilesDir()+"/"+Pfname;                      //~1511I~
        File f=new File(path);                                     //~1511I~
        long sz;                                                   //~1511I~
        if (f.exists())                                            //~1511I~
        	sz=f.length();                                         //~1511I~
        else                                                       //~1511I~
        	sz=-1;                                                 //~1511I~
        if (Dump.Y) Dump.println("getDataFileSize:"+path+",sz="+sz);//~1511I~
    	return sz;                                                 //~1511I~
    }                                                              //~1511I~
//**********                                                       //~1401I~
    public static InputStream openInputSD(String Pfname)           //~1327I~
    {                                                              //~1327I~
        String fnm;                                           //~1327I~
    	if (Dump.Y) Dump.println("open Input SD="+Pfname);         //~1506R~
		fnm=getSDPath(Pfname);                                      //~1327I~
        if (fnm==null)	//no SDCard available                      //~1327I~
        	return null;                                           //~1327I~
                                        //~1327I~
        if (Dump.Y) Dump.println("openInputSD fnm="+fnm);                       //~1327I~//~1506R~
	    FileInputStream is=null;                                   //~1327I~
		try                                                        //~1327I~
		{                                                          //~1327I~
			is=new FileInputStream(fnm);                           //~1327I~
		}                                                          //~1327I~
		catch (FileNotFoundException e)                            //~1327I~
		{                                                          //~1327I~
//  		Dump.println(e,"openInputSD Exception:"+fnm);                     //~1327I~//~1329R~//~v107R~
    		Dump.println("@@@@ AjagoProp:openInputSD FileNotFoundException:"+fnm);//~v107I~
		}                                                          //~1327I~
    	return (InputStream)is;                                    //~1327I~
    }                                                              //~1327I~
//*********************************************                    //~1329I~
//*output to private data/data        *********                    //~1329I~//~1401R~
//*********************************************                    //~1329I~
//***********************************                              //~1401I~
//*from FileOutputStream(Ajagoc.java)                              //~1401I~
//***********************************                              //~1401I~
    public static FileOutputStream openOutputDataCfg(String Pfname)//~1401I~
    {                                                              //~1401I~
    	if (Dump.Y) Dump.println("open Output private file from FileOutputStream="+Pfname);//~1506R~
        String home=Global.home();                                 //~1401I~
        String filename=DATAFILE_PREFIX+Pfname.substring(home.length());//~1401I~
    	return openOutputData(filename);                           //~1401I~
    }                                                              //~1401I~
//***********************************                              //~1401I~
    public static FileOutputStream openOutputData(String Pfname)      //~1309I~
    {                                                              //~1309I~
    	if (Dump.Y) Dump.println("open Output private file="+Pfname);    //~1309I~//~1506R~
	    FileOutputStream out=null;	//FileOutputStream extend OutputStream//~1309I~
        try                                                        //~1309I~
        {                                                          //~1309I~
            out=AG.context.openFileOutput(Pfname,Context.MODE_PRIVATE);//~1309I~
        }                                                          //~1309I~
        catch (Exception e)                                        //~1309I~
        {                                                          //~1309I~
            Dump.println(e,"open output exception "+Pfname);       //~1309I~
        }//catch                                                   //~1309I~
    	return out;                                                //~1309I~
    }                                                              //~1309I~
//***********************************                              //~1A42I~
    public static String getOutputDataFilesPath(String Ppath)      //~1A42I~
    {                                                              //~1A42I~
    	if (Dump.Y) Dump.println("get private dir="+Ppath);        //~1A42I~
	    String dir=getDataFileFullpath(Ppath);                      //~1A42I~
        File f=new File(dir);                                      //~1A42I~
        if (!f.exists())                                           //~1A42I~
    		if (!makePath(dir))                                    //~1A42I~
            	return null;                                       //~1A42I~
    	if (Dump.Y) Dump.println("get private fpath="+dir);        //~1A42I~
        return dir;                                                //~1A42I~
    }                                                              //~1A42I~
//**********                                                       //~1312I~
    public static boolean writeOutputData(String Pfname,byte[] Pbytedata)//~1511R~
    {                                                              //~1312I~
    	boolean rc=false;                                          //~1511I~
    	if (Dump.Y) Dump.println("write Output private file="+Pfname);//~1506R~
		FileOutputStream os=openOutputData(Pfname);                    //~1312I~
        if (os==null)                                              //~1312I~
        	return rc;                                             //~1511R~
        try                                                        //~1312I~
        {                                                          //~1312I~
            os.write(Pbytedata,0,Pbytedata.length);              //~1312I~
            os.close();                                            //~1312I~
            rc=true;                                               //~1511I~
        }                                                          //~1312I~
        catch (Exception e)                                        //~1312I~
        {                                                          //~1312I~
            Dump.println(e,"write output exception "+Pfname);      //~1312I~
        }//catch                                                   //~1312I~
        return rc;                                                 //~1511I~
    }                                                              //~1312I~
//*******************************                                  //~1313I~
//*SD card                      *                                  //~1313I~
//*Manifest setting                                                //~1313I~
//* <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>//~1313I~
//*******************************                                  //~1313I~
    public static boolean isSDMounted()                            //~1313I~
    {                                                              //~1313I~
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);//~1313I~
    }                                                              //~1313I~
    public static String getSDPath(String Pfile)                   //~1313R~
    {                                                              //~1313I~
		String path;                                               //~1313I~
		
    //************                                                 //~1313I~
        if (!availableAjagoSD)                                     //~1313I~
        	return null;                                           //~1313I~
        path=dirAjagoSD;                                           //~1313I~
        if (path==null)                                            //~1313I~
        {                                                          //~1313I~
			String approot=AG.appName;//~1323I~                    //~1402R~
            if (!isSDMounted())                                    //~1313I~
            {                                                      //~1313I~
                availableAjagoSD=false;                            //~1313I~
                return null;                                       //~1313I~
            }                                                      //~1313I~
//          if (Dump.Y) Dump.println("sdcard emulated="+Environment.isExternalStorageEmulated()); //Api11//~1A3bR~
            File sdpf=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//~1A3bI~
//          path=getSDPathLegacy(approot);                         //~1A3bR~
//        if (path==null)                                          //~1A3bR~
        	path=Environment.getExternalStorageDirectory().getPath()+System.getProperty("file.separator")+approot;//~1323R~
            File f=new File(path);
            if (Dump.Y) Dump.println("getSDPath path="+path+",abs="+f.getAbsolutePath());//~1A42R~
        	if (!f.exists())	                                   //~1313I~
            {                                                      //~1313I~
            	if (!f.mkdir())                                 //~1313I~
                {                                                  //~1313I~
		        	availableAjagoSD=false;                        //~1313I~
			        if (Dump.Y) Dump.println("getSDpath mkdir failed:"+path);//~1506R~
        			return null;                                   //~1313I~
                }                                                  //~1313I~
            }                                                      //~1313I~
        	dirAjagoSD=path;                                       //~1313I~
        }                                                          //~1313I~
        if (!Pfile.equals(""))                                     //~1313I~
        	if (Pfile.startsWith("/"))                            //~1412I~
        		path+=Pfile;                                       //~1412I~
            else                                                   //~1412I~
        		path+="/"+Pfile;                                   //~1412R~
        if (Dump.Y) Dump.println("GetSDpath:"+path);               //~1506R~
        if (Dump.Y) Dump.println("abs="+new File(path).getAbsolutePath());//~1A42I~
        return path;                                               //~1313R~
    }                                                              //~1313I~
//*******************************                                  //~1A3bI~
//*SD card check                     *                             //~1A3bI~
//* try /sdcard,/mnt/sdcard                                        //~1A3bI~
//*******************************                                  //~1A3bI~
    public static String getSDPathLegacy(String Proot)             //~1A3bI~
    {                                                              //~1A3bI~
		String[] pathLegacy={"/sdcard",                            //~1A3bI~
					  "/mnt/sdcard",                               //~1A3bI~
					  "/mnt/sdcard/external_sd",                   //~1A3bI~
					  "/storage/sdcard0",                          //~1A3bI~
                      };                                           //~1A3bI~
        String path=null;                                          //~1A3bI~
    //************                                                 //~1A3bI~
    	for (int ii=0;ii<pathLegacy.length;ii++)                   //~1A3bI~
        {                                                          //~1A3bI~
    		path=pathLegacy[ii];                                   //~1A3bI~
            File f=new File(path);                                 //~1A3bI~
        	if (f.exists())                                        //~1A3bR~
        		break;                                             //~1A3bI~
        }                                                          //~1A3bI~
        path+=System.getProperty("file.separator")+Proot;          //~1A3bI~
        if (Dump.Y) Dump.println("GetSDpathLegacy:"+path);         //~1A3bI~
        return path;                                               //~1A3bI~
    }                                                              //~1A3bI~
//**********************************************************************//~@@@@I~
//*for diaply sdcard folder                                        //~@@@@R~
//*SDcard for nexus 7(Android4.2)                                  //~@@@@I~
//*by shell /storage/emulated/legacy-->/mnt/shell/emulated/0       //~@@@@I~
//*by Environment.getExternalStorageDirectory() /storage/emulated/0; this is "no such file" on shell//~@@@@I~
//*try /sdcard and /mnt/sdcard                                     //~@@@@I~
//**********************************************************************//~@@@@I~
    public static String getSDPath42()                             //~@@@@R~
    {                                                              //~@@@@I~
		String path=null,path1,path2,path3,cpath1=null,cpath2=null,cpath3=null;//~@@@@R~
        File f1,f2,f3;                                             //~@@@@R~
    //************                                                 //~@@@@I~
      try                                                          //~@@@@I~
      {                                                            //~@@@@I~
        String approot=AG.appName;                                 //~@@@@I~
        if (!isSDMounted())                                        //~@@@@I~
            return null;                                           //~@@@@I~
        f1=Environment.getExternalStorageDirectory();              //~@@@@I~
        if (f1==null)                                              //~@@@@I~
            return null;                                           //~@@@@I~
        if (!f1.exists())                                          //~@@@@I~
        	return null;                                           //~@@@@I~
        path1=f1.getPath();                                        //~@@@@I~
        path=path1+"/"+approot;                                    //~@@@@I~
        try{                                                       //~@@@@I~
        	 cpath1=f1.getCanonicalPath();                         //~@@@@R~
		     if (Dump.Y) Dump.println("GetSDpath42 "+path1+"="+cpath1);//~@@@@I~
        }catch(Exception e){Dump.println(e,"Sdcard canonicalpath:"+path1);}//~@@@@R~
        path2="/sdcard";                                           //~@@@@I~
        f2=new File(path2);                                        //~@@@@I~
        if (f2.exists())                                           //~@@@@I~
        {                                                          //~@@@@I~
            try{                                                   //~@@@@I~
                 cpath2=f2.getCanonicalPath();                     //~@@@@I~
                 if (cpath1!=null && cpath2!=null && !cpath2.equals(cpath1))//~@@@@I~
                 	path+="; /sdcard("+cpath2+")";                 //~@@@@I~
		        if (Dump.Y) Dump.println("GetSDpath42 /sdcard="+cpath2);//~@@@@I~
            }catch(Exception e){Dump.println(e,"Sdcard canonicalpath:"+path2);}//~@@@@I~
        }                                                          //~@@@@I~
        if (cpath2==null)                                          //~@@@@I~
        {                                                          //~@@@@I~
            path3="/mnt/sdcard";                                   //~@@@@R~
            f3=new File(path3);                                    //~@@@@R~
            if (f3.exists())                                       //~@@@@R~
            {                                                      //~@@@@R~
                try{                                               //~@@@@I~
                     cpath3=f3.getCanonicalPath();                 //~@@@@I~
				     if (Dump.Y) Dump.println("GetSDpath42/mnt/sdcard="+cpath3);//~@@@@I~
    	             if (cpath1!=null && cpath3!=null && !cpath3.equals(cpath1))//~@@@@I~
        	         	path+="; /mnt/sdcard("+cpath3+")";         //~@@@@I~
                }catch(Exception e){Dump.println(e,"Sdcard canonicalpath:"+path3);}//~@@@@I~
            }                                                      //~@@@@R~
        }                                                          //~@@@@I~
      }                                                            //~@@@@I~
      catch(Exception e)                                           //~@@@@I~
      {                                                            //~@@@@I~
      	Dump.println(e,"getSDpath42");                             //~@@@@I~
      }                                                            //~@@@@I~
		if (Dump.Y) Dump.println("GetSDpath42 return="+path);      //~@@@@I~
        return path;                                               //~@@@@R~
    }                                                              //~@@@@I~
//**********************************************************************//~1402I~
//*Preference read/write                                               *//~1402I~
//**********************************************************************//~1402I~
    public static String getPreference(String Pkey)                      //~1402I~
    {                                                              //~1402I~
	    return getPreference(Pkey,"");                             //~1402I~
    }                                                              //~1402I~
    //******************                                           //~v107I~
    public static boolean getPreference(String Pkey,boolean Pdefault)//~v107I~
    {                                                              //~v107I~
    	SharedPreferences pref=getPreferenceName();                //~v107I~
        boolean value=pref.getBoolean(Pkey,Pdefault);              //~v107I~
        return value;                                              //~v107I~
    }                                                              //~v107I~
    //******************                                           //~v107I~
    public static int getPreference(String Pkey,int Pdefault)      //~v107I~
    {                                                              //~v107I~
    	SharedPreferences pref=getPreferenceName();                //~v107I~
        int value=pref.getInt(Pkey,Pdefault);                      //~v107I~
        return value;                                              //~v107I~
    }                                                              //~v107I~
    //******************                                           //~v107I~
    public static String getPreference(String Pkey,String Pdefault)//~1402I~
    {                                                              //~1402I~
    	SharedPreferences pref=getPreferenceName();                 //~1402I~
        String value=pref.getString(Pkey,Pdefault/*default value*/);//~1402R~
        if (Dump.Y) Dump.println("getPreference:"+Pkey+"="+value); //~1506R~
        return value;                                              //~1402I~
    }//readwriteQNo                                                //~1402I~
    //******************                                           //~1402I~
    public static void putPreference(String Pkey,String Pvalue)        //~1402I~
    {                                                              //~1402I~
        if (Dump.Y) Dump.println("putPreference:"+Pkey+"="+Pvalue);//~1506R~
    	SharedPreferences pref=getPreferenceName();                 //~1402I~
        SharedPreferences.Editor editor=pref.edit();               //~1402I~
        editor.putString(Pkey,Pvalue);                             //~1402I~
        editor.commit();                                           //~1402I~
    }                                                              //~v107R~
    //******************                                           //~1402I~
    public static void putPreference(String Pkey,boolean Pvalue)   //~v107I~
    {                                                              //~v107I~
    	SharedPreferences pref=getPreferenceName();                //~v107I~
        SharedPreferences.Editor editor=pref.edit();               //~v107I~
        editor.putBoolean(Pkey,Pvalue);                            //~v107I~
        editor.commit();                                           //~v107I~
    }                                                              //~v107I~
    //******************                                           //~v107I~
    public static void putPreference(String Pkey,int Pvalue)       //~v107I~
    {                                                              //~v107I~
    	SharedPreferences pref=getPreferenceName();                //~v107I~
        SharedPreferences.Editor editor=pref.edit();               //~v107I~
        editor.putInt(Pkey,Pvalue);                                //~v107I~
        editor.commit();                                           //~v107I~
    }                                                              //~v107I~
    //******************                                           //~v107I~
    private static SharedPreferences getPreferenceName()                   //~1402I~
    {                                                              //~1402I~
        return AG.context.getSharedPreferences(AG.appName+"-PrivatePreference",Context.MODE_PRIVATE);//~1402I~
    }                                                              //~1402I~
//**********************************************************************//~1402I~
//*makePath                                                        //~1402I~
//**********************************************************************//~1402I~
    public static boolean makePath(String Ppath)                   //~1402I~
    {                                                              //~1402I~
        File f;                                                    //~1402I~
        boolean rc;                                                //~1402I~
    //**********************                                       //~1402I~
    	if (Dump.Y) Dump.println("makePath:"+Ppath);               //~1506R~
        f=new File(Ppath);                                             //~1402I~
        if (f.exists())                                            //~1402I~
        	if (f.isDirectory())                                   //~1402I~
        		rc=true;                                           //~1402R~
            else                                                   //~1402I~
            	rc=false;                                          //~1402R~
		else                                                       //~1402I~
        {                                                          //~1402I~
        	f.mkdirs();	//create also parent path                  //~1402R~
            rc=true;                                               //~1402I~
        }                                                          //~1402I~
    	if (Dump.Y) Dump.println("makePath:rc"+rc);                //~1506R~
        return rc;                                                 //~1402R~
    }                                                              //~1402I~
//********************************************************         //~v1BbI~//~1Ah0I~
//*unzip asset file:replace mode(no path in zip file)              //~v1BbI~//~1Ah0I~
//********************************************************         //~v1BbI~//~1Ah0I~
    public static boolean unzipAsset(String Ppath,String Pzipfile,int Pchmod)//~v1BbI~//~1Ah0I~
    {                                                              //~v1BbI~//~1Ah0I~
    	String zipFilename=Pzipfile;                               //~v1BbI~//~1Ah0I~
        boolean ret=false;                                         //~v1BbI~//~1Ah0I~
        if (Dump.Y) Dump.println("Unzip src="+Pzipfile+",folder="+Ppath);//~v1BbI~//~1Ah0I~
        File file=new File(Ppath);                                 //~v1BbI~//~1Ah0I~
        try                                                        //~v1BbI~//~1Ah0I~
        {                                                          //~v1BbI~//~1Ah0I~
            if (!file.exists())                                    //~v1BbI~//~1Ah0I~
            {                                                      //~v1BbI~//~1Ah0I~
                file.mkdirs();                                     //~v1BbI~//~1Ah0I~
            }                                                      //~v1BbI~//~1Ah0I~
            ZipInputStream inputStream=new ZipInputStream(AG.resource.getAssets().open(zipFilename));//~vag0R~//~v1BbI~//~1Ah0I~
                                                                   //~v1BbI~//~1Ah0I~
            for (ZipEntry entry=inputStream.getNextEntry(); entry!=null; entry=inputStream.getNextEntry())//~v1BbI~//~1Ah0I~
            {                                                      //~v1BbI~//~1Ah0I~
                String innerFileName = Ppath+File.separator+entry.getName();//~v1BbI~//~1Ah0I~
                if (Dump.Y) Dump.println("Prop:unzipAsset Unzip entry="+innerFileName);//~v1BbI~//~1Ah0R~
                File innerFile = new File(innerFileName);          //~v1BbI~//~1Ah0I~
                if (innerFile.exists())                            //~v1BbI~//~1Ah0I~
                {                                                  //~v1BbI~//~1Ah0I~
                    innerFile.delete();                            //~v1BbI~//~1Ah0I~
                }                                                  //~v1BbI~//~1Ah0I~
                if (entry.isDirectory())                           //~v1BbI~//~1Ah0I~
                {                                                  //~v1BbI~//~1Ah0I~
                    boolean rc=innerFile.mkdirs();                 //~v1BbI~//~1Ah0I~
                    if (Dump.Y) Dump.println("mkdir "+entry.getName()+"="+rc);//~v1BbI~//~1Ah0I~
                }                                                  //~v1BbI~//~1Ah0I~
                else                                               //~v1BbI~//~1Ah0I~
                {                                                  //~v1BbI~//~1Ah0I~
//			        if (unzipfilepopup!=null)                      //~v1BbI~//~1Ah0I~
//                    	AjagoView.showToast(unzipfilepopup+entry.getName());//~v1BbI~//~1Ah0I~
                    FileOutputStream outputStream = new FileOutputStream(innerFileName);//~v1BbI~//~1Ah0I~
                    final int BUFFER = 2048;                       //~v1BbI~//~1Ah0I~
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream,BUFFER);//~v1BbI~//~1Ah0I~
                    int count = 0;                                 //~v1BbI~//~1Ah0I~
                    byte[] data = new byte[BUFFER];                //~v1BbI~//~1Ah0I~
                    while ((count = inputStream.read(data, 0, BUFFER)) != -1)//~v1BbI~//~1Ah0I~
                    {                                              //~v1BbI~//~1Ah0I~
                        bufferedOutputStream.write(data, 0, count);//~v1BbI~//~1Ah0I~
                    }                                              //~v1BbI~//~1Ah0I~
                    bufferedOutputStream.flush();                  //~v1BbI~//~1Ah0I~
                    bufferedOutputStream.close();                  //~v1BbI~//~1Ah0I~
                    if (Pchmod!=-1)                                //~v1BbI~//~1Ah0I~
    					chmod(innerFile,0755);                     //~v1BbI~//~1Ah0I~
                }                                                  //~v1BbI~//~1Ah0I~
                inputStream.closeEntry();                          //~v1BbI~//~1Ah0I~
            }                                                      //~v1BbI~//~1Ah0I~
            inputStream.close();                                   //~v1BbI~//~1Ah0I~
            ret=true;                                              //~v1BbI~//~1Ah0I~
        }                                                          //~v1BbI~//~1Ah0I~
        catch (IOException e)                                      //~v1BbI~//~1Ah0I~
        {                                                          //~v1BbI~//~1Ah0I~
            Dump.println(e,"unzipAsset");                          //~v1BbI~//~1Ah0I~
        }                                                          //~v1BbI~//~1Ah0I~
        if (Dump.Y) Dump.println("unzipAsset rc="+ret);            //~v1BbI~//~1Ah0I~
        return ret;                                                //~v1BbI~//~1Ah0I~
    }                                                              //~v1BbI~//~1Ah0I~
//********************************************************         //~1Ah0I~
//*get unzip size                                                  //~1Ah0I~
//********************************************************         //~1Ah0I~
    public static long getUnzipSize(String Pzipfile,ArrayList<String> Plist)//~1Ah0R~
    {                                                              //~1Ah0I~
    	String zipFilename=Pzipfile;                               //~1Ah0I~
        boolean ret=false;                                         //~1Ah0I~
        if (Plist!=null)                                           //~1Ah0I~
	        Plist.clear();	                                       //~1Ah0I~
        if (Dump.Y) Dump.println("Prop:getUnzipSize zipfile="+Pzipfile);//~1Ah0I~
        long totalsize=0;                                          //~1Ah0I~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
            ZipFile zf=new ZipFile(Pzipfile);                      //~1Ah0I~
            Enumeration e=zf.entries();                             //~1Ah0I~
            while(e.hasMoreElements())                              //~1Ah0I~
            {                                                      //~1Ah0I~
                ZipEntry ze=(ZipEntry)e.nextElement();             //~1Ah0I~
                long unzipsize=ze.getSize();                       //~1Ah0I~
                String name=ze.getName();                                 //~1Ah0R~
		        if (Plist!=null)                                   //~1Ah0I~
        	        Plist.add(name);                               //~1Ah0I~
                if (Dump.Y) Dump.println("Prop:getUnzipSize entry="+name+",size="+unzipsize);//~1Ah0I~
                totalsize+=unzipsize;                              //~1Ah0I~
            }                                                      //~1Ah0I~
            zf.close();                                            //~1Ah0I~
        }                                                          //~1Ah0I~
        catch (IOException e)                                      //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"getUnzipSize");                        //~1Ah0I~
            totalsize=-1;                                          //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("Prop:getUnzipSize total="+totalsize);//~1Ah0I~
        return totalsize;                                          //~1Ah0I~
    }//getUnzipSize                                                //~1Ah0I~
    //***********************************************              //~1Ah0I~
    public static long getAssetUnzipSize(String Pzipfile,ArrayList<String> Plist)//~1Ah0R~
    {                                                              //~1Ah0I~
        if (Plist!=null)                                           //~1Ah0I~
	        Plist.clear();                                         //~1Ah0I~
    	String zipFilename=Pzipfile;                               //~1Ah0I~
        long totalsz=0;                                            //~1Ah0I~
        if (Dump.Y) Dump.println("Prop:getAssetUnzipSize zipfile="+Pzipfile);//~1Ah0R~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
            ZipInputStream inputStream=new ZipInputStream(AG.resource.getAssets().open(zipFilename));//~1Ah0I~
            for (ZipEntry entry=inputStream.getNextEntry(); entry!=null; entry=inputStream.getNextEntry())//~1Ah0I~
            {                                                      //~1Ah0I~
                long unzipsz=entry.getSize();                      //~1Ah0I~
                String name=entry.getName();                       //~1Ah0R~
                if (Dump.Y) Dump.println("Prop:getAssetUnzipSize Unzip entry="+name+",isDirectory="+entry.isDirectory()+",unzipsize="+unzipsz);//~1Ah0R~
                inputStream.closeEntry();                          //~1Ah0R~
                totalsz+=unzipsz;                                  //~1Ah0I~
		        if (Plist!=null)                                   //~1Ah0I~
        	        Plist.add(name);                               //~1Ah0I~
            }                                                      //~1Ah0I~
            inputStream.close();                                   //~1Ah0R~
        }                                                          //~1Ah0I~
        catch (IOException e)                                      //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"unzipAsset");                          //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("unzipAsset totalsize"+totalsz);
        return totalsz;//~1Ah0I~
    }//getAssetUnzipSize                                           //~1Ah0I~
    //**************************************************************//~v1BbI~//~1Ah0I~
    public static void chmod(File Pf,int Pattr)                    //~v1BbI~//~1Ah0I~
    {                                                              //~v1BbI~//~1Ah0I~
    	if ((Pattr & 0011)!=0)                                     //~v1BbI~//~1Ah0I~
			Pf.setExecutable(true/*owner*/,false/*owner only*/);   //~v1BbI~//~1Ah0I~
        else                                                       //~v1BbI~//~1Ah0I~
        if ((Pattr & 0100)!=0)                                     //~v1BbI~//~1Ah0I~
		    Pf.setExecutable(true/*owner*/,true/*other*/);         //~v1BbI~//~1Ah0I~
    	if ((Pattr & 0044)!=0)                                     //~v1BbI~//~1Ah0I~
			Pf.setReadable(true/*owner*/,false/*owner only*/);     //~v1BbI~//~1Ah0I~
        else                                                       //~v1BbI~//~1Ah0I~
        if ((Pattr & 0400)!=0)                                     //~v1BbI~//~1Ah0I~
		    Pf.setReadable(true/*owner*/,true/*other*/);           //~v1BbI~//~1Ah0I~
    	if ((Pattr & 0022)!=0)                                     //~v1BbI~//~1Ah0I~
			Pf.setWritable(true/*owner*/,false/*owner only*/);     //~v1BbI~//~1Ah0I~
        else                                                       //~v1BbI~//~1Ah0I~
        if ((Pattr & 0200)!=0)                                     //~v1BbI~//~1Ah0I~
		    Pf.setWritable(true/*owner*/,true/*other*/);           //~v1BbI~//~1Ah0I~
    }                                                              //~v1BbI~//~1Ah0I~
    //**************************************************************//~1Ah0I~
    public static String getAssetRecord(String Pfilename)          //~1Ah0I~
    {                                                              //~1Ah0I~
		InputStream is=openAssetFile(Pfilename);                   //~1Ah0I~
        if (is==null)                                              //~1Ah0I~
			return null;                                           //~1Ah0I~
        byte[] buff=new byte[BUFFSZ2];                                    //~1Ah0I~
        try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
        	int len=is.read(buff);                                     //~1Ah0I~
            if (len<0)                                             //~1Ah0I~
            	return null;                                       //~1Ah0I~
        	is.close();                                            //~1Ah0I~
        }                                                          //~1Ah0I~
        catch (Exception e)                                        //~1Ah0I~
        {                                                          //~1Ah0I~
            Dump.println(e,"copyToDataDir output exception "+Pfilename);//~1Ah0I~
            return null;                                           //~1Ah0I~
        }//catch                                                   //~1Ah0I~
        if (Dump.Y) Dump.println("Prop:getAssetRecord file="+Pfilename+",line="+new String(buff));//~1Ah0I~
        return new String(buff);                                   //~1Ah0R~
    }                                                              //~1Ah0I~
}//class                                                           //~1110I~
