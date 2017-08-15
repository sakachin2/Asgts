//*CID://+1AhaR~:                                   update#=   57; //~1AhaR~
//***********************************************                  //~@@@1I~
//1Aha 2016/11/19 add option trace X for special debug in notrace mode//~1AhaI~
//1Ab9 2015/05/09 Dump byte[]                                      //~1Ab9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing//~1A6AI~
//1A4h 2014/12/03 catch OutOfMemory                                //~1A4hI~
//1A30 2013/04/06 kif,ki2 format support                           //~1A30I~
//1075:121207 control dumptrace by manifest debuggable option      //~v107I~
//@@@1 prepend timestamp/threadid                                  //~@@@1I~
//     exception dump                                              //~@@@1I~
//     optional dump by ajago menu option                          //~@@@1I~
//***********************************************                  //~@@@1I~
package jagoclient;

import java.io.*;
import java.util.*;

import com.Asgts.AG;                                               //~v@@@R~
import com.Asgts.AView;
import com.Asgts.Prop;                                             //~v@@@R~
import com.Asgts.Utils;                                            //~v@@@R~

public class Dump
{	static PrintWriter Out=null;
	static boolean Terminal=false;                                           //~1228I~
	static boolean noTid=false;                                    //~1A30I~
	static public boolean exceptionOnly=false;                     //~1507R~
	static public boolean oldExceptionOnly;                        //~1AhaI~
	static public boolean Y;   //test before call Dump to avoid memory for parameter String//~1506I~
	static public boolean C;   //split Canvas drawing              //~1A6AI~
	static public boolean T;   //split UiTHread trace              //~1A6AI~
	static public boolean X;   //special use                       //~1Ab9I~
	public static void openEx (String file)                        //~1504I~
	{                                                              //~1504I~
		open(file);                                                //~1504I~
      if (AG.isDebuggable                                          //~v107R~
      && Out!=null                                                 //~v107R~
//    && (AG.Options & AG.OPTIONS_TRACE)!=0                        //~v@@@I~//~1AhaR~
      && (AG.Options &                                             //~1AhaI~
						(AG.OPTIONS_TRACE                          //~1AhaI~
						|AG.OPTIONS_TRACE_CANVAS                   //~1AhaI~
						|AG.OPTIONS_TRACE_UITHREAD                 //~1AhaI~
						|AG.OPTIONS_TRACE_X                        //~1AhaI~
						)                                          //~1AhaR~
         )!=0                                                      //~1AhaI~
      )                                                            //~v@@@I~
      {                                                            //~v107R~
//      Y=true;                                                    //~v107R~//~1AhaR~
	    Y=(AG.Options & AG.OPTIONS_TRACE)!=0;                      //~1AhaI~
	    C=(AG.Options & AG.OPTIONS_TRACE_CANVAS)!=0;                     //~1AhaI~
	    T=(AG.Options & AG.OPTIONS_TRACE_UITHREAD)!=0;                   //~1AhaI~
	    X=(AG.Options & AG.OPTIONS_TRACE_X)!=0;                          //~1AhaI~
        println("Dump.openEx file="+file+",Y="+Y+"C="+C+",T="+T+",X="+X);//+1AhaI~
      }                                                            //~v107R~
      else                                                         //~v107R~
      {                                                            //~v107R~
    	exceptionOnly=true;                                        //~1506M~
        Y=false;//dont call Dump except case of Exceoption         //~1506I~
        C=false;                                                   //~1AhaI~
        T=false;                                                   //~1AhaI~
        X=false;                                                   //~1AhaI~
      }                                                            //~v107R~
    }                                                              //~1504I~
	public static void open (String file)
	{                                                              //~1329R~
    	exceptionOnly=false;//not exception only                   //~1506I~
        if (file==null)                                            //~1A30I~
        {	                                                       //~1A30I~
        	Out=null;                                              //~1A30I~
            Terminal=true;                                         //~1A30I~
            noTid=true;                                            //~1A30I~
            Y=true;                                                //~1A30I~
            return;                                                //~1A30I~
        }                                                          //~1A30I~
    	if (Out!=null)                                             //~1329I~
        	return;                                                //~1329I~
		try                                                        //~1329I~
		{                                                          //~1227R~
        	OutputStream out=Prop.openOutputData("",file);	//SD card//~1329R~//~v@@@R~
        	if (out!=null)
        	{//~1313R~
        		Out=new PrintWriter(new OutputStreamWriter(out,"UTF-8"),true/*autoFlash*/);//~1227I~//~1309R~
        		Out.println("Locale: "+Locale.getDefault()+"\n");
                Y=true; //call Dump                                //~1506I~
                Terminal=true;                                     //~1511I~
        	}
		}
		catch (IOException e)
		{	Out=null;
            System.out.println("Dump open failed");                //~1329I~
		}
	}
	/** dump a string in a line */
	public synchronized static void println (String s)             //~1305R~
	{                                                              //~1228R~
    	if (exceptionOnly)                                         //~1504I~
        	return;                                                //~1504I~
                                                                   //~1504I~
	    String tidts=null,tid;                                     //~v@@@I~
  		if (Out!=null)                                             //~1425R~
        {                                                          //~1425I~
	    	tidts=Utils.getThreadTimeStamp();          //~1425I~   //~v@@@R~
			Out.println(tidts+":"+s);                              //~1425I~
        }                                                          //~1425I~
  		if (Terminal)                                              //~1511R~
        {                                                          //~1425I~
          if (noTid)                                               //~1A30I~
          	tid="";                                                //~1A30I~
          else                                                     //~1A30I~
          {                                                        //~1A30I~
        	if (tidts==null)                                       //~v@@@I~
            	tid=Utils.getThreadId();                   //~1425I~//~v@@@R~
            else                                                   //~v@@@I~
            	tid=tidts;                                         //~v@@@I~
          }                                                        //~1A30I~
            System.out.println(tid+":"+s);                         //~1425I~//~v@@@R~
        }                                                          //~1425I~
	}
    private static void byte2string(StringBuffer Psb,int Poutoffs,byte[] Pbytes,int Pinpoffs,int Plen)//~1Ab9R~
    {                                                              //~1Ab9I~
    	String s;
		try {
			s = new String(Pbytes,Pinpoffs,Plen,"US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
            return;                                                //~1Ab9I~
		}       //~1Ab9I~
        for (int ii=0;ii<16;ii++)                                  //~1Ab9R~
        {                                                          //~1Ab9R~
            if (ii<Plen)                                           //~1Ab9M~
            {                                                      //~1Ab9I~
        		char ch=s.charAt(ii);                              //~1Ab9R~
//              System.out.println("ch="+ch);                      //~1Ab9R~
            	if (ch<' '|| ch>=0x7f)                             //~1Ab9R~
	            	Psb.setCharAt(ii+Poutoffs,'.');                //~1Ab9R~
            	else                                               //~1Ab9I~
            		Psb.setCharAt(ii+Poutoffs,ch);                 //~1Ab9R~
            }                                                      //~1Ab9I~
            else                                                   //~1Ab9I~
	            Psb.setCharAt(ii+Poutoffs,' ');                    //~1Ab9R~
        }                                                          //~1Ab9R~
    }                                                              //~1Ab9I~
//                                       00 00 00 00 - 00 00 00 00 - 00 00 00 00 - 00 00 00 00 - *0123456789abcdef*//~1Ab9I~
    private static final String dumpfmt="            -             -             -               *                *";//~1Ab9I~
	public synchronized static void println (String Ptitle,byte[] Pbytes)//~1Ab9I~
    {                                                              //~1Ab9I~
    	println(Ptitle,Pbytes,0,Pbytes.length);                    //~1Ab9I~
    }                                                              //~1Ab9I~
	public synchronized static void println (String Ptitle,byte[] Pbytes,int Poffs,int Plen)//~1Ab9R~
	{                                                              //~1Ab9I~
    	if (exceptionOnly)                                         //~1Ab9I~
        	return;                                                //~1Ab9I~
	    println(Ptitle+" : size="+Pbytes.length+"=0x"+Integer.toHexString(Pbytes.length)+",offs=0x"+Integer.toHexString(Poffs)+",len=0x"+Integer.toHexString(Plen));//~1Ab9I~
        StringBuffer sb=new StringBuffer(dumpfmt);                 //~1Ab9I~
        int intch,intch2;                                          //~1Ab9R~
        int fillsz=((Plen+15)/16)*16;                              //~1Ab9R~
        int lastinpoffs=0;                                         //~1Ab9I~
        for (int ii=0,offs=0;ii<fillsz;ii++,offs+=3)               //~1Ab9R~
        {                                                          //~1Ab9I~
        	if (ii!=0)                                             //~1Ab9I~
            {                                                      //~1Ab9I~
                if (ii%16==0)                                      //~1Ab9I~
                {                                                  //~1Ab9M~
                    byte2string(sb,(3*4+2)*4+1,Pbytes,Poffs+ii-16,16);//~1Ab9I~
                    String s=sb.toString();                        //~1Ab9M~
                    if (Out!=null)                                 //~1Ab9M~
                        Out.println(s);                            //~1Ab9M~
                    if (Terminal)                                  //~1Ab9M~
                        System.out.println(s);                     //~1Ab9M~
	                offs=0;                                        //~1Ab9M~
                    lastinpoffs=ii;                                //~1Ab9I~
                }                                                  //~1Ab9M~
                else                                               //~1Ab9M~
                if (ii%4==0)                                       //~1Ab9I~
                    offs+=2;                                       //~1Ab9M~
            }                                                      //~1Ab9I~
            if (ii<Plen)                                           //~1Ab9I~
            {                                                      //~1Ab9I~
            	intch=(Pbytes[Poffs+ii] & 0xff);                      //~1Ab9I~
            	intch2=intch/16;                                   //~1Ab9R~
                if (intch2<10)                                     //~1Ab9I~
            		sb.setCharAt(offs,(char) ('0'+intch2));        //~1Ab9I~
                else                                               //~1Ab9I~
            		sb.setCharAt(offs,(char) ('a'+intch2-10));     //~1Ab9R~
            	intch2=intch%16;                                   //~1Ab9R~
                if (intch2<10)                                     //~1Ab9I~
            		sb.setCharAt(offs+1,(char) ('0'+intch2));      //~1Ab9I~
                else                                               //~1Ab9I~
            		sb.setCharAt(offs+1,(char) ('a'+intch2-10));   //~1Ab9R~
            }                                                      //~1Ab9I~
            else                                                   //~1Ab9I~
            {                                                      //~1Ab9I~
            	sb.setCharAt(offs,' ');                            //~1Ab9I~
            	sb.setCharAt(offs+1,' ');                          //~1Ab9I~
            }                                                      //~1Ab9I~
        }                                                          //~1Ab9I~
        if (lastinpoffs<Plen)	//remaining char dump              //~1Ab9R~
        {                                                          //~1Ab9I~
	    	byte2string(sb,(3*4+2)*4+1,Pbytes,lastinpoffs,Plen-lastinpoffs);//~1Ab9R~
            String s=sb.toString();                                //~1Ab9I~
            if (Out!=null)                                         //~1Ab9I~
            	Out.println(s);                                    //~1Ab9I~
            if (Terminal)                                          //~1Ab9I~
            	System.out.println(s);                             //~1Ab9I~
        }                                                          //~1Ab9I~
	}                                                              //~1Ab9I~
//** Exception Dump                                                //~1309I~
	public synchronized static void println(Exception e,String s)  //~1309I~
	{                                                              //~1309I~
//	    String tidts=Utils.getThreadTimeStamp();              //~1425I~//~v@@@R~//~1A30R~
 	    String tidts;                                              //~1A30I~
      	if (noTid)                                                 //~1A30I~
          	tidts="";                                               //~1A30I~
      	else                                                       //~1A30I~
	    	tidts=Utils.getThreadTimeStamp();                      //~1A30I~
        System.out.println(tidts+":"+s);                                 //~1309I~//~1425R~
        e.printStackTrace();                                   //~1309I~//~1329I~
  		if (Out!=null)                                             //~1309I~
        {                                                          //~1309I~
			Out.println(tidts+":"+s+" Exception:"+e.toString());   //~1425R~
            StringWriter sw=new StringWriter();                    //~1311I~
            PrintWriter pw= new PrintWriter(sw);                   //~1311I~
            e.printStackTrace(pw); 
			Out.println(tidts+":"+sw.toString());                  //~1425R~
			Out.flush(); 
			pw.close();//~1309I~
        }                                                          //~1309I~
	}                                                              //~1309I~
	/** dump a string without linefeed */
	public static void print (String s)
	{                                                              //~1504R~
    	if (exceptionOnly)                                         //~1504I~
        	return;                                                //~1504I~
		if (Out!=null) Out.print(s);                               //~1504I~
		if (Terminal) System.out.print(s);
	}
	/** close the dump file */
	public static void close ()
	{                                                              //~1503R~
		if (Out!=null)                                             //~1503I~
        {                                                          //~1503I~
        	println("Dump closed");                               //~1503I~
			Out.close();                                           //~1503I~
        }                                                          //~1503I~
    	Out=null;                                                  //~1425I~
	}
	/** determine terminal dumps or not */
	public static void terminal (boolean flag)
	{	Terminal=flag;
	}
    public static void setOption(boolean Pflag)                    //~1507I~//~v@@@R~
    {                                                              //~1507I~//~v@@@R~
        println("DumpOption Changed to"+Pflag);                    //~1507I~//~v@@@R~
        Y=Pflag;    //debug dump                                   //~1507I~//~v@@@R~
        exceptionOnly=!Pflag;                                      //~1507I~//~v@@@R~
    }                                                              //~1507I~//~v@@@R~
    //**************************************************************//~1A4hI~
	public synchronized static void println(OutOfMemoryError e,String s)//~1A4hI~
	{                                                              //~1A4hI~
	    String tidts=Utils.getThreadTimeStamp();                   //~1A4hI~
        System.out.println(tidts+":"+s);                           //~1A4hI~
        e.printStackTrace();                                       //~1A4hI~
  		if (Out!=null)                                             //~1A4hI~
        {                                                          //~1A4hI~
			Out.println(tidts+":"+s+" Exception:"+e.toString());   //~1A4hI~
            StringWriter sw=new StringWriter();                    //~1A4hI~
            PrintWriter pw= new PrintWriter(sw);                   //~1A4hI~
            e.printStackTrace(pw);                                 //~1A4hI~
			Out.println(tidts+":"+sw.toString());                  //~1A4hI~
			Out.flush();                                           //~1A4hI~
			pw.close();                                            //~1A4hI~
        }                                                          //~1A4hI~
        AView.memoryShortage(s);                                   //~1A4hI~
	}                                                              //~1A4hI~
    //**************************************************************//~1AhaI~
	public static void chngTraceX(boolean PtraceX)                                  //~1AhaI~
	{                                                              //~1AhaI~
        if (PtraceX)                                               //~1AhaI~
        {                                                          //~1AhaI~
        	oldExceptionOnly=exceptionOnly;                        //~1AhaI~
            exceptionOnly=false;                                   //~1AhaI~
            println("Dump.X enabled");                             //~1AhaI~
        }                                                          //~1AhaI~
        else                                                       //~1AhaI~
            exceptionOnly=oldExceptionOnly;                        //~1AhaI~
        X=PtraceX;                                                 //~1AhaI~
	}                                                              //~1AhaI~
}
