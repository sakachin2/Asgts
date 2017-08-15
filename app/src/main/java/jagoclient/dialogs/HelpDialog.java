//*CID://+1A73R~:                             update#=   39;       //+1A73R~
//*********************************************************************//~v101I~
//1A73 2015/02/23 apply 1A68 to Help dialog(fill screen width)     //+1A73I~
//1A72 2015/02/23 use smaller help dialog font size for mdpi       //~1A72I~
//1A4A 2014/12/09 function to show youtube                         //~1A4AI~
//1A4t 2014/12/06 show filename on HelpFileNotFound dialog         //~1A4tI~
//1A41 2014/09/19 avoid exceoption msg for help text not found     //~1A1jI~
//1A1j 2013/03/19 change Help file encoding to utf8                //~1A1jI~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//*********************************************************************//~v101I~
package jagoclient.dialogs;

//import java.awt.*;                                               //~1418R~
import java.io.*;

import android.view.View;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.R;                                                //~v101R~
import com.Asgts.Utils;
import com.Asgts.awt.Font;
import com.Asgts.awt.Frame;                                         //~@@@@R~//~v101R~
//import com.Asgts.rene.viewer.SystemViewer;                        //~1327I~//~@@@@R~//~v101R~
import com.Asgts.rene.viewer.Viewer;                              //~1327I~//~@@@@R~//~v101R~

import jagoclient.gui.*;
import jagoclient.Dump;
import jagoclient.Global;

//import rene.viewer.*;                                            //~1327R~

/**
The same as Help.java but as a dialog. This is for giving help in
modal dialogs. 
@see jagoclient.dialogs.Help
*/

public class HelpDialog extends CloseDialog
{	Viewer V; // The viewer
	ButtonAction  btnMovie;                                       //~1A4AI~
	Frame F;
	/**
	Display the help from subject.txt,Global.url()/subject.txt
	or from the ressource /subject.txt.
	*/
//  public HelpDialog (Frame f, String subject)                    //~v101R~
    public HelpDialog (Frame f,int Ptitleid,String subject)        //~v101I~
//    {   super(f,Global.resourceString("Help"),true);             //~@@@@R~
    {                                                              //~@@@@I~
//      super(f,AG.resource.getString(R.string.Help),false/*not modal*/);//~@@@@R~
//      super(f,AG.resource.getString(R.string.Help),R.layout.helpdialog,false/*not modal*/,false/*no input wait*/);//~@@@@I~//~v101R~
//      super((f==null?AG.currentFrame:f),AG.resource.getString(R.string.Help),R.layout.helpdialog,false/*not modal*/,false/*no input wait*/);//~v101R~
        super((f==null?AG.currentFrame:f),                         //~v101I~
				AG.resource.getString(R.string.Help)+":"+AG.resource.getString(Ptitleid),//~v101I~
				R.layout.helpdialog,false/*not modal*/,false/*no input wait*/);//~v101I~
        setWindowSize(90/*W:90%*/,0/*H=wrap content*/,!AG.layoutMdpi/*for landscape,use ScrHeight for width limit if not mdpi*/);//+1A73R~
		F=f;
//  	V=Global.getParameter("systemviewer",false)?new SystemViewer():new Viewer();//~@@@@R~
//        V=new Viewer();                                          //~@@@@R~
		setButton(Ptitleid);                                       //~1A4AR~
        V=new Viewer(this,R.id.Viewer);                            //~@@@@I~
      if (AG.layoutMdpi)                                           //~1A72I~
      {                                                            //~1A72I~
        int fontsz=12;                                                 //~1A72I~
        Font helpfont=Global.createfont("monospaced","Monospaced",fontsz);//~1A72I~
		V.setFont(helpfont);                                       //~1A72I~
      }                                                            //~1A72I~
      else                                                         //~1A72I~
		V.setFont(Global.Monospaced);
		V.setBackground(Global.gray);
        String fnms="";                                            //~1A4tI~
        String fnm;                                                //~1A4tI~
		try
		{	BufferedReader in;
			String s;
            String help_suffix="_"+AG.language;                    //~1A1jI~
			AG.tryHelpFileOpen=true;                               //~1A41R~
    		fnm="helptexts/"+subject+help_suffix+".txt";           //~1A4tI~
            fnms+="\n"+fnm;                                        //~1A4tR~
			try
//@@@@  	{	in=Global.getStream(                               //~1327R~
        	{	in=Global.getEncodedStream(        //like as Help.java;required to display locale language//~1327I~
//  				"jagoclient/helptexts/"+subject+Global.resourceString("HELP_SUFFIX")+".txt");//~1A1jR~
//  				"helptexts/"+subject+help_suffix+".txt");      //~1A4tR~
    				fnm);                                           //~1A4tI~
				s=in.readLine();
			}
			catch (Exception e)
			{                                                      //~1A1jR~
//              Dump.println(e,"HelpDialog read1:"+"helptexts/"+subject+help_suffix+".txt");//~1A1jR~
//*             NullPtrException by file not found                 //~1A1jI~
                fnm=subject+help_suffix+".txt";                   //~1A4tI~
	            fnms+="\n"+fnm;                                    //~1A4tI~
			 	try                                                //~1A1jI~
//@@@@ 			{	in=Global.getStream(                           //~1327R~
    			{	in=Global.getEncodedStream(                    //~1327I~
//  					subject+Global.resourceString("HELP_SUFFIX")+".txt");//~1A1jR~
//  					subject+help_suffix+".txt");               //~1A4tR~
    					fnm);                                      //~1A4tI~
					s=in.readLine();
				}
				catch (Exception ex)
//@@@@  		{	in=Global.getStream("jagoclient/helptexts/"+subject+".txt");//~1327R~
//  			{	in=Global.getEncodedStream("jagoclient/helptexts/"+subject+".txt");//~1327I~//~1A1jR~
    			{                                                  //~1A1jR~
//              	Dump.println(ex,"HelpDialog read2:"+subject+help_suffix+".txt");//~1A1jR~
//*             NullPtrException by file not found                 //~1A1jI~
					AG.tryHelpFileOpen=false;                      //~1A41R~
    			 	fnm="helptexts/"+subject+".txt";               //~1A4tI~
		            fnms+="\n"+fnm;                                //~1A4tI~
//  			 	in=Global.getEncodedStream("helptexts/"+subject+".txt");//~1A4tR~
    			 	in=Global.getEncodedStream(fnm);               //~1A4tI~
					s=in.readLine();
				}
			}
			while (s!=null)
			{	V.appendLine(s);
				s=in.readLine();
			}
			in.close();
		}
		catch (Exception e)
		{	new Message(Global.frame(),
//  			Global.resourceString("Could_not_find_the_help_file_"));//~1A4tR~
    			Global.resourceString("Could_not_find_the_help_file_")+fnms);//~1A4tI~
			doclose();
			return;
		}
		AG.tryHelpFileOpen=false;                                  //~1A41R~
		display();
	}
	public void doclose ()
	{	setVisible(false); dispose();
	}
	
	void display ()
//  {  	Global.setwindow(this,"help",500,400);                     //~@@@@R~
	{                                                              //~@@@@I~
//  	Global.setwindow(this,"help",500,400);                     //~@@@@I~
//        add("Center",V);                                         //~@@@@R~
//        Panel p=new MyPanel();                                   //~@@@@R~
//        p.add(new ButtonAction(this,Global.resourceString("Close")));//~@@@@R~
//        add("South",new Panel3D(p));                             //~@@@@R~
//        ButtonAction.init(this,R.string.Close);                  //~@@@@R~
        new ButtonAction(this,0,R.id.Close);                  //~@@@@R~
	  	setVisible(true);                                          //~@@@@R~
	}
	public void doAction (String o)
//  {  	Global.notewindow(this,"help");                            //~@@@@R~
	{                                                              //~@@@@I~
//  	Global.notewindow(this,"help");                            //~@@@@I~
	  if (o.equals(AG.resource.getString(R.string.ButtonYoutube))) //~1A4AI~
	  {                                                            //~1A4AI~
      	showMovie();                                               //~1A4AI~
      }                                                            //~1A4AI~
      else                                                         //~1A4AI~
		super.doAction(o);
	}
//**********************************                               //~@@@@I~
	public HelpDialog (Frame Pframe,int Presid)                    //~@@@@R~
	{                                                              //~@@@@I~
//  	super(AG.currentFrame,AG.resource.getString(R.string.Help),true);//~@@@@R~
//      super(AG.currentFrame,AG.resource.getString(R.string.Help),false);//~@@@@R~
        super((Pframe==null?AG.currentFrame:Pframe),AG.resource.getString(R.string.Help),R.layout.helpdialog,false/*not modal*/,false/*no input wait*/);//~@@@@R~
//        V=new Viewer();                                          //~@@@@R~
        setWindowSize(90/*W:90%*/,0/*H=wrap content*/,!AG.layoutMdpi/*for landscape,use ScrHeight for width limit if not mdpi*/);//+1A73R~
        F=Pframe==null?AG.currentFrame:Pframe;                     //~@@@@I~
        V=new Viewer(this,R.id.Viewer);                            //~@@@@I~
      if (AG.layoutMdpi)                                           //~1A72I~
      {                                                            //~1A72I~
        int fontsz=12;                                             //~1A72I~
        Font helpfont=Global.createfont("monospaced","Monospaced",fontsz);//~1A72I~
		V.setFont(helpfont);                                       //~1A72I~
      }                                                            //~1A72I~
      else                                                         //~1A72I~
		V.setFont(Global.Monospaced);                              //~@@@@I~
		V.setBackground(Global.gray);                              //~@@@@I~
		String s=AG.resource.getString(Presid);                    //~@@@@I~
        String[] sa=s.split("\n");                                 //~@@@@I~
        for (int ii=0;ii<sa.length;ii++)                           //~@@@@I~
        {                                                          //~@@@@I~
        	s=sa[ii];                                              //~@@@@I~
            if (s.endsWith("\n"))                                  //~@@@@I~
            	s=s.substring(0,s.length()-1);                       //~@@@@I~
        	V.appendLine(s);                                       //~@@@@I~
        }                                                          //~@@@@I~
		display();                                                 //~@@@@I~
    }                                                              //~@@@@I~
	//***************************************                      //~1A4AI~
    private void setButton(int Ptitleid)                           //~1A4AR~
    {                                                              //~1A4AI~
      	try                                                        //~1A4AI~
      	{                                                          //~1A4AI~
        	new ButtonAction(this,0,R.id.Close);                   //~1A4AI~
        	if (Ptitleid==R.string.HelpTitle_MainFrame)            //~1A4AI~
            {                                                      //~1A4AI~
        		btnMovie=new ButtonAction(this,0,R.id.ButtonYoutube);//~1A4AR~
            	btnMovie.setVisibility(View.VISIBLE);              //~1A4AR~
            }                                                      //~1A4AI~
                                                                   //~1A4AI~
      	}                                                          //~1A4AI~
      	catch (Exception e)                                        //~1A4AI~
      	{                                                          //~1A4AI~
      		Dump.println(e,"HelpDialog:setButtonMovie Exception"); //~1A4AI~
       		AView.showToast(R.string.Exception);                   //~1A4AI~
	    }                                                          //~1A4AI~
    }                                                              //~1A4AI~
	//***************************************                      //~1A4AI~
	private static final String URL_MOVIELIST_J="https://www.youtube.com/watch?v=HfbQvvwexu0&list=PL2clNB_BpXSVaKqwRaIm8nDb1she3k0ns";//~1A4AR~
	private static final String URL_MOVIELIST_E="https://www.youtube.com/watch?v=H8xu8LBdh60&list=PL2clNB_BpXSX_SgnSQ8cYFJ7UzKY3URiD";//~1A4AI~
	public void showMovie()                                        //~1A4AR~
	{                                                              //~1A4AI~
        String url="";                                                 //~1A4AI~
      	try                                                        //~1A4AI~
      	{                                                          //~1A4AI~
        	if (AG.isLangJP)                                       //~1A4AI~
        		url=URL_MOVIELIST_J;                               //~1A4AR~
            else                                                   //~1A4AI~
        		url=URL_MOVIELIST_E;                               //~1A4AI~
        	Utils.showWebSite(url);                                //~1A4AI~
      	}                                                          //~1A4AI~
      	catch (Exception e)                                        //~1A4AI~
      	{                                                          //~1A4AI~
      		Dump.println(e,"HelpDialog:showMovie Exception url="+url);//~1A4AR~
       		AView.showToast(R.string.Exception);                   //~1A4AI~
	    }                                                          //~1A4AI~
    }                                                              //~1A4AI~
}
