//*CID://+@@@@R~:                             update#=    8;       //~v107I~//~@@@@R~
//*************************************************************************//~v107I~
//1074:121207 no detail exception info for SDcard/resources        //~v107I~
//*************************************************************************//~v107I~
package com.Asgts.rene.gui;                                         //~v107R~//~@@@@R~


import com.Asgts.AG;                                                //~v107R~//+@@@@R~
import com.Asgts.Prop;                                         //~v107R~//+@@@@R~
//~1108I~
import jagoclient.Dump;

import java.io.FileNotFoundException;                              //~v107R~
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

//**************************************************************************//~1407R~
//***res/raw dir is read only;option change is saved to data/data/../files *//~1407R~
//**************************************************************************//~1407R~

public class Global extends rene.gui.Global                        //~1308R~
{   
//************************************************                 //~1326I~
//JagoResouce.property from assets                                 //~1326I~
//getBundle is final it could not be override                      //~1326I~
//************************************************                 //~1326I~
	public static void initBundle (String file, boolean language)  //~1326I~
	{	try                                                        //~1326I~
		{                                                          //~1326I~
//  		B=ResourceBundle.getBundle(file);                      //~1326I~
    		B=getBundle(file);                                     //~1326I~
//            String lang=getParameter("language","");               //~1326I~//~@@@@R~
//            if (language && !lang.equals("") && !lang.equals("default"))//~1326I~//~@@@@R~
//            {   String langsec="";                                 //~1326I~//~@@@@R~
//                if (lang.length()>3 && lang.charAt(2)=='_')        //~1326I~//~@@@@R~
//                {   langsec=lang.substring(3);                     //~1326I~//~@@@@R~
//                    lang=lang.substring(0,2);                      //~1326I~//~@@@@R~
//                }                                                  //~1326I~//~@@@@R~
//                Locale.setDefault(new Locale(lang,langsec));       //~1326I~//~@@@@R~
//                initBundle(file,false);                            //~1326I~//~@@@@R~
//            }                                                      //~1326I~//~@@@@R~
		}                                                          //~1326I~
		catch (RuntimeException e)                                 //~1326I~
		{	B=null;                                                //~1326I~
		}
		catch(Exception e)
		{
			Dump.println(e,"initBundle:"+file);
			B=null;//~1326I~
		}
	}                                                              //~1326I~
	public static void initBundle(String file)                     //~1326I~
	{	initBundle(file,false);                                    //~1326I~
	}                                                              //~1326I~
//************************************************                 //~1326I~
	private static ResourceBundle getBundle(String Pfile) throws IOException           //~1326I~
	{                                                                  //~1326I~
		InputStream is;                                                //~1326I~
        Locale locale=Locale.getDefault();                         //~1326I~
        String lang=locale.getLanguage();                          //~1326I~
        String bundlename=Pfile+"_"+lang+".properties";            //~1326I~
        is=Prop.openInputSD(bundlename); //check SD card      //~1327R~//~@@@@R~
        if (Dump.Y) Dump.println("getBundle-Lang on SD card"+bundlename+(is==null ? " none" : " found"));//~1327I~//~1506R~
        if (is==null)                                              //~1327I~
        {                                                          //~1327I~
            try                                                        //~1326I~//~1327R~
            {                                                          //~1326I~//~1327R~
                is=AG.resource.getAssets().open(bundlename);           //~1326R~//~1327R~
                if (Dump.Y) Dump.println("getBundle:Lang on Asset is available "+bundlename);//~1506R~
            }                                                          //~1326I~//~1327R~
            catch (Exception e)                                        //~1326I~//~1327R~
            {                                                          //~1326I~//~1327R~
              if (e instanceof FileNotFoundException)              //~v107I~
                Dump.println("@@@@ rene/Global/getBundle FileNotFoundException:"+bundlename);//~v107I~
              else                                                 //~v107I~
                Dump.println(e,"Exception getBundle getAsset:"+bundlename);     //~1326I~//~1327R~//~1329R~
                bundlename=Pfile+".properties";  //~1326I~         //~1327R~
        		is=Prop.openInputSD(bundlename); //check SD card//~1327R~//~@@@@R~
                if (Dump.Y) Dump.println("getBundle:no Lang on SD card"+bundlename+(is==null ? " none" : " found"));//~1327I~//~1506R~
                if (is==null)                                      //~1327I~
                {                                                  //~1327I~
                	is=AG.resource.getAssets().open(bundlename);   //~1327R~
                	if (Dump.Y) Dump.println("getAsset available getBundle No Lang:"+bundlename);//~1327R~//~1506R~
                }                                                  //~1327I~
            }                                                          //~1326I~//~1327R~
        }                                                          //~1327I~
		PropertyResourceBundle prb=new PropertyResourceBundle(is);     //~1326I~
		return (java.util.ResourceBundle)prb;                          //~1326I~
	}
}//~1326I~
