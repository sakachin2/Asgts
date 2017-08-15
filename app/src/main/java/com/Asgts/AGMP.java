//*CID://+1Ah0R~:                             update#=   17;       //+1Ah0R~
//************************************************************************//~v106I~
//1Ah0 2016/10/15 bonanza                                          //+1Ah0I~
//1Afs 2014/09/01 Asset file open err (getAsset().openFD:it is probably compressed) for Agnugo when size over 64k//~1AfsI~
//v1C8 2014/09/01 Agnugo.png-->Agnugo.zip(unzip at first time)     //~v1C8I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//v1B5 2014/07/30 gmp issues errmsg "GMP server respond err message"//~v1B5I~
//1066:121128 GMP connection run exception(gnugo end by AGUNGO_PARM open failed)//~v106I~
//************************************************************************//~v106I~
package com.Asgts;                                                 //~1110I~//+1Ah0R~
                                                                   //~1110I~
                                                                   //~1110I~
import java.io.File;
import java.io.IOException;

import jagoclient.Dump;
import jagoclient.Global;

                                                                   //~1110I~
public class AGMP                                              //~1510R~//+1Ah0R~
{                                                                  //~1110I~
	public static final String GMP_PGMID="Agnugo";                 //~1511I~
//  public static final String GMP_PARM="--mode gmp";               //~1511I~//~v106R~
	public static final String GMP_PARM_ENV="AGNUGO_PARM";	//same on Agnugo.c//~1511I~
	public static final String GMP_PARM_FILE="AGNUGO_PARM";	//same on Agnugo.c//~1511I~
    private static final String PREFKEY_GMPSERVER_ZIPSIZE="GMPserverZipSize";//~v1C8R~
                                                                   //~1511I~
	public static void setup()                                     //~1511R~
	{                                                              //~1401I~
        String path,pgm;                                           //~1511R~
        int pgmid;                                                 //~1511R~
        File f;                                                    //~1511I~
    //***************************                                  //~1401I~
        if (Dump.Y) Dump.println("AGMP:setup");                //~1511I~//+1Ah0R~
        path=Prop.getDataFileFullpath(GMP_PGMID);              //~1511I~//+1Ah0R~
		pgm=Global.getParameter("gmpserver",GMP_PGMID);           //~1511I~
        if (pgm.equals(GMP_PGMID))                                 //~1511I~
        {                                                          //~1511I~
            pgmid=1;                                               //~1511I~
            pgm=path;                                              //~1511I~
        }                                                          //~1511I~
        else                                                       //~1511I~
        if (pgm.startsWith(GMP_PGMID+" "))                         //~1511I~
        {                                                          //~1511I~
            pgmid=2;                                               //~1511I~
            pgm=path+pgm.substring(GMP_PGMID.length());             //~1511I~
        }                                                          //~1511I~
        else                                                       //~1511I~
        if (pgm.equals(path))                                      //~1511I~
            pgmid=3;                                               //~1511I~
        else                                                       //~1511I~
        if (pgm.startsWith(path+" "))                              //~1511I~
            pgmid=4;                                               //~1511I~
        else                                                       //~1511I~
        	pgmid=0;                                               //~1511I~
        if (pgmid==0)	                                           //~1511I~
        {                                                          //~1511I~
        	f=new File(pgm);                                       //~1511R~
            if (!f.exists())                                        //~1511I~
            {                                                      //~1511I~
            	pgm=path;                                          //~1511R~
	            pgmid=1;                                           //~1511I~
            }                                                      //~1511I~
        }                                                          //~1511I~
        if (pgmid==1||pgmid==2)                                    //~1511R~
        {                                                          //~1511I~
			Global.setParameter("gmpserver",pgm);  //notify to GMPConnection//~1511R~
	        if (Dump.Y) Dump.println("AGMP:setup path="+pgm);  //~1511R~//+1Ah0R~
        }                                                          //~1511I~
        if (pgmid!=0)	//Agnugo                                   //~1511I~
        {                                                          //~1511I~
        	f=new File(path);                                      //~1511I~
            if (!f.exists())                                       //~1511I~
            {                                                      //~1511I~
            	touchGMP(GMP_PGMID);	//avoid not found error at GMPconnector//~1511R~
            }                                                      //~1511I~
        }                                                          //~1511I~
    }                                                              //~1511I~
	public static String checkDefaultProgram(String Ppgm)          //~1516I~
	{                                                              //~1516I~
        String fpath,pgm;                             //+1516I~                                          //~1516I~
    //***************************                                  //~1516I~
        if (Dump.Y) Dump.println("AGMP:checkDefaultProgram cmd="+Ppgm);//~1516I~//+1Ah0R~
        pgm=Ppgm;                                                  //~1516I~
        fpath=Prop.getDataFileFullpath(GMP_PGMID);            //~1516I~//+1Ah0R~
        if (pgm.equals(GMP_PGMID))                                 //~1516I~
        	pgm=fpath;                                             //~1516I~
        else                                                       //~1516I~
        if (!pgm.equals(fpath))                                    //~1516I~
        {                                                          //~1516I~
	        if (Dump.Y) Dump.println("AGMP:checkDefaultProgram return pgm="+pgm);//~1516I~//+1Ah0R~
        	return pgm;	 //!Agnugo                                 //~1516I~
        }                                                          //~1516I~
        File f=new File(pgm);                                      //~1516I~
        if (!f.exists())                                           //~1516I~
        {                                                          //~1516I~
        	touchGMP(GMP_PGMID);	//avoid not found error at GMPconnector//~1516I~
        }                                                          //~1516I~
        if (Dump.Y) Dump.println("AGMP:checkDefaultProgram return pgm="+pgm);//~1516I~//+1Ah0R~
        return pgm;                                                //~1516I~
	}                                                              //~1516I~
	public static String checkProgram(String Ppgm)                 //~1511R~
	{                                                              //~1511I~
        String path,pgm,assetfnm;                             //~1511R~
        long fsz,fsza;                                             //~1511R~
        String defaultpgm;                                         //~v1C8I~
    //***************************                                  //~1511I~
        if (Dump.Y) Dump.println("AGMP:checkProgram cmd="+Ppgm);//~1516R~//+1Ah0R~
        pgm=Ppgm;	                                               //~1511I~
        path=Prop.getDataFileFullpath("");                    //~v1C8I~//+1Ah0R~
		defaultpgm=Prop.getDataFileFullpath(GMP_PGMID);       //~v1C8I~//+1Ah0R~
        if (pgm.startsWith(path))                                  //~1511R~
        {                                                          //~1511I~
        	long fszunzip=Prop.getDataFileSize(GMP_PGMID);         //~v1C8I~//+1Ah0R~
        	fsz=Prop.getPreference(PREFKEY_GMPSERVER_ZIPSIZE,0);//~v1C8I~//+1Ah0R~
            assetfnm=GMP_PGMID+".png";                             //~1AfsI~
        	fsza=Prop.getAssetFileSize(assetfnm);             //~1511I~//+1Ah0R~
            if (fsz!=fsza||fszunzip<=0)  //updated or unzip file was deleted//~v1C8I~
            {                                                      //~1511I~
		        if (Dump.Y) Dump.println("AGMP:copy to datadir success");//~1511I~//+1Ah0R~
				Prop.unzipAsset(path,assetfnm,0777);          //~v1C8I~//+1Ah0R~
	        	Prop.putPreference(PREFKEY_GMPSERVER_ZIPSIZE,(int)fsza);//~v1C8I~//+1Ah0R~
            }                                                      //~1511I~
    		pgm=defaultpgm;                                        //~v1C8I~
        }                                                          //~1511I~
                                  //~1511I~
        if (Dump.Y) Dump.println("AGMP:checkProgram normal return cmd="+pgm);//~1511R~//+1Ah0R~
        return pgm;                                                //~1511R~
	}                                                              //~1511I~
//***********************************                              //~1511I~
    public  static boolean chmodX(String Pfname,String Pmask)      //~v1B6I~
    {                                                              //~1511I~
        String cmd="chmod "+Pmask+" "+Pfname;                      //~1511R~
        return execShell(cmd);                                     //~1511R~
    }                                                              //~1511I~
    private static boolean touchGMP(String Pfname)                 //~1511I~
    {                                                              //~1511I~
		byte[] nulldata=new byte[0];                               //~1511I~
		boolean rc=Prop.writeOutputData(Pfname,nulldata);               //~1511R~//+1Ah0R~
        return rc;                                                 //~1511I~
    }                                                              //~1511I~
//*****************                                                //~1511I~
    private static boolean execShell(String Pcmd)                  //~1511I~
    {                                                              //~1511I~
    	if (Dump.Y) Dump.println("AGMP:execShell cmd"+Pcmd);   //~1511I~//+1Ah0R~
    	Runtime rt=Runtime.getRuntime();                           //~1511I~
        Process p=null;                                            //~1511I~
        boolean rc=false;                                          //~1511I~
        try                                                        //~1511I~
        {                                                          //~1511I~
        	p=rt.exec(Pcmd);                                        //~1511I~
           	p.waitFor();                                           //~1511I~
            rc=true;                                               //~1511I~
        }                                                          //~1511I~
        catch (IOException e)                                      //~1511I~
        {                                                          //~1511I~
           	Dump.println(e,"AGMP:execShell exception:"+Pcmd);  //~1511I~//+1Ah0R~
		}                                                          //~1511I~
		catch (InterruptedException e)                             //~1511I~
		{                                                          //~1511I~
			Dump.println(e,"AGMP:execShell interrupted exception:"+Pcmd);//~1511I~//+1Ah0R~
		}                                                          //~1511I~
       	if (Dump.Y) Dump.println("AGMP:chmod cmd return rc="+rc);//~1511I~//+1Ah0R~
        return rc;                                                 //~1511I~
    }                                                              //~1511I~
    //*****************                                            //~1512I~
    public  static void warning(int Pmsgid,byte[] Pbyte,int Plen)  //~1513R~
    {                                                              //~1512I~
    	int rid=0;                                                 //~1513I~
        if (Dump.Y)	Dump.println("AGMP:warning msgid="+Pmsgid);//~v1B5I~//+1Ah0R~
    	if (Pmsgid==1)	//no Answer                                //~1513I~
        	rid=R.string.GMP_NoAnswer;                             //~1513I~
        else                                                       //~1513I~
    	if (Pmsgid==2)	//sudden deth                              //~1513R~
        	rid=R.string.GMP_Death;                                //~1513I~
        else                                                       //~1514I~
    	if (Pmsgid==3)	//sudden deth                              //~1514I~
        	rid=R.string.GMP_ErrMsg;                             //~1514I~
        if (rid>0)                                                 //~1513I~
        {                                                          //~1513I~
        	String stderrmsg="";                                   //~1513I~
            if (Plen>0)                                            //~1513I~
            {                                                      //~1513I~
                try                                                //~1513R~
                {                                                  //~1513R~
                    stderrmsg=new String(Pbyte,0,Plen,"UTF-8");    //~1513R~
                }                                                  //~1513R~
                catch(Exception e)                                 //~1513R~
                {                                                  //~1513R~
                    Dump.println(e,"AGMP:warning stderr byte2String");//~1513R~//+1Ah0R~
                }                                                  //~1513R~
            }                                                      //~1513I~
			Alert.simpleAlertDialog(null/*Pcallback*/,null/*Ptitle*/,AG.resource.getString(rid)+":"+stderrmsg,Alert.BUTTON_POSITIVE);//~v106I~//~v1B5I~//+1Ah0R~
        }                                                          //~1513I~
    }                                                              //~1512I~
}//class                                                           //~1110I~
