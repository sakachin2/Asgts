//*CID://+1A40R~:                                   update#=   29; //~1A40R~
//*******************************************************************//~@@@1I~
//1A41 2014/09/19 avoid exceoption msg for help text not found     //~1A41I~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//1A1j 2013/03/19 change Help file encoding to utf8                //~1A1jI~
//1A00 2013/02/13 Asgts                                            //~1A00I~
//*@@@1 intercept config/property file io                          //~@@@1I~
//*******************************************************************//~@@@1I~
package jagoclient;


import com.Asgts.AG;
import com.Asgts.awt.Color;                                           //~1109I~//~@@@1R~
import com.Asgts.awt.Frame;                                        //~@@@1R~
import com.Asgts.java.FileInputStream;                            //~1327I~//~@@@1R~
import com.Asgts.awt.Font;                                            //~1109I~//~@@@1R~
                                                             //~1109I~
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

class GlobalObject
{
}


public class Global extends com.Asgts.rene.gui.Global //@@@@ intercept Property file IO//~1308I~//~@@@1R~
{//	public static Component C;                                     //~1A40R~
	public static String Dir,Home;
    public static Frame F;                                         //~@@@1R~
	public static boolean UseUrl=false;
	public static boolean IsApplet=false;
	public static URL Url;
	public static boolean Busy=true;
	public static Color gray=Color.gray;
	public static Font SansSerif,Monospaced,BigMonospaced,BoardFont;
	public static int Silent=0;

	static
	{// WindowList=new Hashtable();                                //~@@@1R~
		Dir=""; Home="";
		initBundle("jagoclient/foreign/JagoResource");
		if (B==null) initBundle("jagoclient/JagoResource");
		if (B==null) initBundle("JagoResource");
	}
	

//    public static void setcomponent (Component c)                //~1A40R~
//    {   C=c;                                                     //~1A40R~
//    }                                                            //~1A40R~
	
	public static InputStream getDataStream (String filename)
	{	try
		{	if (useurl())
			{	return new URL(url(),filename).openStream();
			}
			else
			{	return new FileInputStream(Global.home()+filename);//~@@@1R~
			}
		}
		catch (Exception e)
		{	Object G=new GlobalObject();
		  if (AG.tryHelpFileOpen)                                     //~1A41I~
	        Dump.println("@@@@ Global:getDataStream Test help file existance failed for "+filename);//~1A41I~
          else                                                     //~1A41I~
        	Dump.println(e,"Global:getDataStream fnm="+filename);      //~1329I~//~1A1jR~
			return G.getClass().getResourceAsStream("/"+filename);
		}
	}
	
	public static BufferedReader getStream (String filename, String encoding)
	{	try
		{	return new BufferedReader(
				new InputStreamReader(getDataStream(filename),encoding));
		}
		catch (UnsupportedEncodingException e)
		{	return getStream(filename);
		}
	}
	
	public static BufferedReader getStream (String filename)
	{	return new BufferedReader(
			new InputStreamReader(getDataStream(filename)));       //~@@@1R~
	}
	
	public static BufferedReader getEncodedStream (String filename)
	{	// String encoding=getParameter("HELP_ENCODING","");
//        return getStream(filename);                                //~@@@1I~//~1A00R~
//  	String encoding=resourceString("HELP_ENCODING");           //~1A00I~//~1A1jR~
    	String encoding=AG.HELP_ENCODING;   //UTF-8                //~1A1jI~
		if (encoding.equals("")) return getStream(filename);       //~1A00I~
		else return getStream(filename,encoding);                  //~1A00I~
	}
	
	public static void readparameter (String filename)
	{// File f=new File(home()+filename);                          //~@@@1R~
		loadProperties(getDataStream(filename));			
		gray=getColor("globalgray",new Color(220,220,220));
	}
	
	/** get the current directory */
	public static String dir ()
	{	return Dir;
	}
	/** set the current directory */
	public static void dir (String dir)
	{	if (isApplet()) Dir=dir+"\\";
		else Dir=dir+System.getProperty("file.separator");
	}
	/** get the home directory */
	public static String home ()
	{	return Home;
	}
	/** set the home directory */
	public static void home (String dir)
	{	if (isApplet()) Home=dir+"\\";
		else Home=dir+System.getProperty("file.separator");
	}
	
	/** getParameter for color values */
	public static Color getColor (String a, int red, int green, int blue)
	{	return getParameter(a,red,green,blue);
	}
	/** getParameter for colors */
	public static Color getColor(String a, Color c)
	{	return getParameter(a,c);
	}
	/** setParameter for colors */
	public static void setColor (String a, Color c)
	{	setParameter(a,c);
	}
	
	/** set a default invisible frame */
    public static void frame (Frame f)                             //~@@@1R~
    {   F=f;                                                       //~@@@1R~
    }                                                              //~@@@1R~
	/** get the default frame */
    public static Frame frame ()                                   //~@@@1R~
    {   if (F==null) F=new Frame();                                //~@@@1R~
        return F;                                                  //~@@@1R~
    }                                                              //~@@@1R~
	
	/** 
	@return the used URL 
	*/
	public static URL url () { return Url; }
	/** set the used url */
	public static void url (URL url) { Url=url; UseUrl=true; IsApplet=true; }
	public static void setApplet (boolean flag) { IsApplet=flag; }
	public static boolean isApplet () { return IsApplet; }
	/**
	@return Flag, if URL is used
	*/
	public static boolean useurl () { return UseUrl; }
	
	/** create the user chosen fonts */
	public static void createfonts ()
	{   SansSerif=createfont("sansserif","SansSerif","ssfontsize",11);
	    Monospaced=createfont("monospaced","Monospaced","msfontsize",11);
      if (AG.screenDencityMdpi)                                    //~1A40I~
	    BigMonospaced=createfont("bigmonospaced","BoldMonospaced","bigmsfontsizemdpi",12);//~1A40I~
      else                                                         //~1A40I~
	    BigMonospaced=createfont("bigmonospaced","BoldMonospaced","bigmsfontsize",22);
	    BoardFont=createfont("boardfontname","SansSerif","boardfontsize",10);
	}
    //*for the case <=480 pix                                      //~@@@1I~
	public static void createfonts (int Pfontsz)                   //~@@@1I~
	{   SansSerif=createfont("sansserif","SansSerif",Pfontsz);     //~@@@1R~
	    Monospaced=createfont("monospaced","Monospaced",Pfontsz);  //~@@@1R~
	    BigMonospaced=createfont("bigmonospaced","BoldMonospaced",Pfontsz*2);//~@@@1R~
	    BoardFont=createfont("boardfontname","SansSerif",Pfontsz-1);//~@@@1R~
	}                                                              //~@@@1I~
	static Font createfont (String name, String def, String size, int sdef)
	{   name=getParameter(name,def);
	    if (name.startsWith("Bold"))
	    {   return new Font(name.substring(4),Font.BOLD,Global.getParameter(size,sdef));
	    }
	    else if (name.startsWith("Italic"))
	    {   return new Font(name.substring(5),Font.ITALIC,Global.getParameter(size,sdef));
	    }
	    else
	    {   return new Font(name,Font.PLAIN,Global.getParameter(size,sdef));
	    }	    
	}
	public static Font createfont (String name, String style, int Psize)//~@@@1R~
	{                                                              //~@@@1I~
	    if (name.equals("Bold"))                                   //~@@@1I~
	    {   return new Font(style,Font.BOLD,Psize);                //~@@@1I~
	    }                                                          //~@@@1I~
	    else if (name.equals("Italic"))                            //~@@@1I~
	    {   return new Font(style,Font.ITALIC,Psize);              //~@@@1I~
	    }                                                          //~@@@1I~
	    else                                                       //~@@@1I~
	    {   return new Font(style,Font.PLAIN,Psize);               //~@@@1I~
	    }                                                          //~@@@1I~
	}                                                              //~@@@1I~
	
	/**
	Get the national translation fot the string s.
	The resource strings contain _ instead of blanks.
	If the resource is not found, the strings s (with _ replaced
	by blanks) will be used.
	*/
	public static String resourceString (String s)
	{	String res;
		s=s.replace(' ','_');
		res=name(s,"???");
		if (res.equals("???"))
		{	res=s.replace('_',' ');
			if (res.endsWith(" n"))
			{	res=res.substring(0,res.length()-2)+"\n";
			}
		}
		return res;
	}
}
