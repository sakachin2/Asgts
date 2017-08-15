//*CID://+v101R~:                             update#=    8;       //~@@@@I~//~v101R~
//*****************************************************************************//~v101I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//101a 2013/01/30 IP connection                                    //~v101I~
//*****************************************************************************//~v101I~
package jagoclient.gui;

//import java.awt.*;

import com.Asgts.awt.Container;                                    //~v101R~
import com.Asgts.awt.TextField;                                     //~2C26R~//+v101R~

import jagoclient.Global;

/**
A TextField with a background and font as specified in the Global class.
*/

public class GrayTextField extends TextField
{	public GrayTextField (String s)
	{	super(s,25);
		setBackground(Global.gray);
		setFont(Global.SansSerif);
	}
	public GrayTextField ()
	{	super(25);
		setBackground(Global.gray);
		setFont(Global.SansSerif);
	}
	public GrayTextField (int n)
	{	super(n);
		setBackground(Global.gray);
		setFont(Global.SansSerif);
	}
//***************************************                          //~@@@@I~
	public GrayTextField (int n,int Presid)                        //~@@@@R~
	{                                                              //~@@@@I~
		super(n==0?25:n,Presid,"");                                //~@@@@R~
		setBackground(Global.gray);                                //~@@@@I~
		setFont(Global.SansSerif);                                 //~@@@@I~
	}                                                              //~@@@@I~
//***************************************                          //~v101I~
	public GrayTextField (Container Pcontainer,int n,int Presid)   //~v101I~
	{                                                              //~v101I~
		this(Pcontainer,Presid,-1/*row=1*/,n==0?25:n);             //~v101I~
	}                                                              //~v101I~
//***************************************                          //~v101I~
	public GrayTextField (int Presid,int Prow,int Pcol)            //~v101I~
	{                                                              //~v101I~
		super("",Presid,Prow,Pcol);                                //~v101I~
		setBackground(Global.gray);                                //~v101I~
		setFont(Global.SansSerif);                                 //~v101I~
	}                                                              //~v101I~
//***************************************                          //~v101I~
	public GrayTextField (Container Pcontainer,int Presid,int Prow,int Pcol)//~v101I~
	{                                                              //~v101I~
		super(Pcontainer,"",Presid,Prow,Pcol);                     //~v101I~
		setBackground(Global.gray);                                //~v101I~
		setFont(Global.SansSerif);                                 //~v101I~
	}                                                              //~v101I~
}
