//*CID://+1AfaR~:                             update#=   18;       //+1AfaR~
//*********************************************************************//+1AfaI~
//1Afa 2016/10/11 2016/07/11 Delete main function to avoid selected main as entrypoint for eclips starter//+1AfaI~
//*********************************************************************//+1AfaI~
package jagoclient;


import jagoclient.board.Board;                                     //~1420R~
import jagoclient.board.GoFrame;
import jagoclient.board.WoodPaint;
import jagoclient.dialogs.Message;
import jagoclient.gui.CloseFrame;                                                           //~1112I~
import jagoclient.gui.DoActionListener;
import jagoclient.partner.ConnectPartner;
import jagoclient.partner.PartnerFrame;
import jagoclient.partner.partner.Partner;
import jagoclient.sound.JagoSound;

import java.io.BufferedReader;
import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.java.FileOutputStream;                            //~@@@@R~
import java.io.PrintWriter;                                        //~1401R~
import java.util.ArrayList;                                        //~1524R~
import java.util.Locale;

import com.Asgts.awt.ActionEvent;                                  //~@@@@R~
import com.Asgts.awt.ActionListener;                               //~@@@@R~
import com.Asgts.awt.Component;                                    //~@@@@R~
import com.Asgts.awt.Frame;                                        //~@@@@R~
import com.Asgts.awt.Panel;                                        //~@@@@R~
import com.Asgts.awt.Button;                                       //~@@@@R~

import rene.util.list.ListClass;
import rene.util.list.ListElement;


public class Go                                                    //~@@@@I~
	implements DoActionListener, ActionListener
{	int Test=0;
	Button CConnect,CEdit,CAdd,CDelete,                           //~1524R~
		PConnect,PEdit,PAdd,PDelete,POpen;
	static Go go;
	String Server="",MoveStyle="",Encoding="";
	int Port;
	public Go (String server, int port, String movestyle, String encoding)
	{	Server=server; Port=port;  MoveStyle=movestyle; Encoding=encoding;
	}

	public Go ()
	{	Server=""; Port=0; MoveStyle=""; Encoding="";
	}

	public void actionPerformed (ActionEvent e)                    //~1524R~
    {                                                              //~@@@@I~
		if (false);                                                //~@@@@I~
		else doAction(e.getActionCommand());                       //~1524R~
	}

	
    public void init ()
    {                                                              //~@@@@I~
	}
    
  	public void doAction (String o)
  	{                                                              //~@@@@I~
  	}
  	public void itemAction (String o, boolean flag) {}

	
	public static MainFrame F=null;
	
//  public static void main (String args[])                        //+1AfaR~
    public static void GoMain (String args[])                      //+1AfaI~
	{	// scan arguments
		int na=0;
		boolean homefound=false;
		String localgame="";
		// initialize some Global things
		Global.setApplet(false);
        Global.readparameter(".go.cfg"); // read setup             //~@@@@R~
		Global.createfonts();
		CloseFrame CF;
		Global.frame(CF=new CloseFrame("Global")); // a default invisible frame
    	F=new MainFrame(AG.appName);                               //~@@@@I~
    	go=new Go();                                               //~@@@@I~
		go.init();
		F.setVisible(true);
//        Global.loadmessagefilter(); // load message filters, if available//~@@@@R~
//        JagoSound.play("high","",true); // play a welcome sound  //~@@@@R~
//        if (!localgame.equals("")) openlocal(localgame);         //~@@@@R~
//            // open a SGF file, if there was a parameter         //~@@@@R~
//        else                                                     //~@@@@R~
//            if (Global.getParameter("beauty",false))             //~@@@@R~
				// start a board painter with the last known
				// board dimensions
			{	Board.woodpaint=new WoodPaint(F);
			}
	}



}                                                                  //~1318I~
