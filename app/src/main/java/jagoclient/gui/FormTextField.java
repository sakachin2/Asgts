//*CID://+1A69R~:                             update#=    8;       //~@@@@I~//~v101R~//+1A69R~
//******************************************************************//~v101I~
//1A69 2015/02/08 (Bug)EditText width was set 25 colomn for IPConnection portNo field//+1A69I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//101a 2013/01/30 IP connection                                    //~v101I~
//******************************************************************//~v101I~
package jagoclient.gui;

import com.Asgts.awt.Container;                                    //~v101R~

import jagoclient.Global;

/**
A text field, which can transfer focus to the next text field,
when return is pressed.
*/

public class FormTextField extends GrayTextField
    implements DoActionListener
{	public FormTextField (String s)
	{	super();
		TextFieldActionListener T=
		    new TextFieldActionListener(this,"");
		addActionListener(T);
		setFont(Global.SansSerif);
		setText(s);
	}
//****************************************************             //~@@@@R~
	public FormTextField (int PresId,String s)                     //~@@@@R~
	{                                                              //~@@@@R~
		super(0,PresId);                                           //~@@@@R~
		TextFieldActionListener T=                                 //~@@@@R~
		    new TextFieldActionListener(this,"");                  //~@@@@R~
		addActionListener(T);                                      //~@@@@R~
		setFont(Global.SansSerif);                                 //~@@@@R~
		setText(s);                                                //~@@@@R~
	}                                                              //~@@@@R~
//****************************************************             //~v101I~
	public FormTextField (Container Pcontainer,int PresId,String s)//~v101I~
	{                                                              //~v101I~
		super(Pcontainer,0,PresId);                                //~v101I~
		TextFieldActionListener T=                                 //~v101I~
		    new TextFieldActionListener(this,"");                  //~v101I~
		addActionListener(T);                                      //~v101I~
		setFont(Global.SansSerif);                                 //~v101I~
		setText(s);                                                //~v101I~
	}                                                              //~v101I~
//****************************************************             //+1A69I~
	public FormTextField (Container Pcontainer,int PresId,String s,int Pcols)//+1A69I~
	{                                                              //+1A69I~
		super(Pcontainer,Pcols,PresId);	//GrayTextField            //+1A69I~
		TextFieldActionListener T=                                 //+1A69I~
		    new TextFieldActionListener(this,"");                  //+1A69I~
		addActionListener(T);                                      //+1A69I~
		setFont(Global.SansSerif);                                 //+1A69I~
		setText(s);                                                //+1A69I~
	}                                                              //+1A69I~
//****************************************************             //~v101I~
	public FormTextField (String s,int PresId,int Prow,int Pcol)   //~v101I~
	{                                                              //~v101I~
		super(PresId,Prow,Pcol);                                   //~v101I~
		TextFieldActionListener T=                                 //~v101I~
		    new TextFieldActionListener(this,"");                  //~v101I~
		addActionListener(T);                                      //~v101I~
		setFont(Global.SansSerif);                                 //~v101I~
		setText(s);                                                //~v101I~
	}                                                              //~v101I~
//****************************************************             //~v101I~
	public FormTextField (Container Pcontainer,String s,int PresId,int Prow,int Pcol)//~v101I~
	{                                                              //~v101I~
		super(Pcontainer,PresId,Prow,Pcol);                        //~v101I~
		TextFieldActionListener T=                                 //~v101I~
		    new TextFieldActionListener(this,"");                  //~v101I~
		addActionListener(T);                                      //~v101I~
		setFont(Global.SansSerif);                                 //~v101I~
		setText(s);                                                //~v101I~
	}                                                              //~v101I~
//****************************************************             //~@@@@R~
	public void doAction (String o)
	{	transferFocus();
	}
	public void itemAction (String o, boolean flag) {}
}