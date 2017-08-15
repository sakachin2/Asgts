//*CID://+v101R~:                             update#=    4;       //~@@@2I~//~v101R~
//***********************************************************************//~v101I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//***********************************************************************//~v101I~
package jagoclient.gui;

//import java.awt.*;                                               //~1524R~
import java.io.*;
                                                                   //~1524I~
import com.Asgts.awt.Container;                                    //~v101R~
import com.Asgts.awt.TextArea;                                     //+v101R~
import jagoclient.Global;
import rene.util.list.*;

/**
A text area that takes care of the maximal length imposed by Windows
and other OSs. This should be replaced by jagoclient.viewer.Viewer
<p>
The class works much like TextArea, but takes care of its length.
@see jagoclient.viewer.Viewer
*/

public class MyTextArea extends TextArea
{   ListClass L;
	public int MaxLength;
	int Length=0;
	public MyTextArea ()
    {	setFont(Global.Monospaced);
        setBackground(Global.gray);
    	L=new ListClass();
    	MaxLength=Global.getParameter("maxlength",10000);
    }
	public MyTextArea (String s, int x, int y, int f)
    {	super(s,x,y,f);
    	setFont(Global.Monospaced);
        setBackground(Global.gray);
    	L=new ListClass();
    	MaxLength=Global.getParameter("maxlength",10000);
    	setText(s);
    }
    //*******************************************************************//~@@@2I~
    //*find view by res id*                                        //~@@@2I~
    //*******************************************************************//~@@@2I~
	public MyTextArea (String s,int Presid, int x, int y, int f)   //~@@@2I~
    {	super(s,Presid,x,y,f);                                     //~@@@2I~
    	setFont(Global.Monospaced);                                //~@@@2I~
        setBackground(Global.gray);                                //~@@@2I~
    	L=new ListClass();                                         //~@@@2I~
    	MaxLength=Global.getParameter("maxlength",10000);          //~@@@2I~
    	setText(s);                                                //~@@@2I~
    }                                                              //~@@@2I~
    //*******************************************************************//~v101I~
	public MyTextArea (Container Pcontainer,String s,int Presid, int x, int y, int f)//~v101I~
    {                                                              //~v101I~
    	super(Pcontainer,s,Presid,x,y,f);                          //~v101I~
    	setFont(Global.Monospaced);                                //~v101I~
        setBackground(Global.gray);                                //~v101I~
    	L=new ListClass();                                         //~v101I~
    	MaxLength=Global.getParameter("maxlength",10000);          //~v101I~
    	setText(s);                                                //~v101I~
    }                                                              //~v101I~
    public void append (String s)
    {	Length+=s.length();
    	L.append(new ListElement(s));
		if (Length>MaxLength)
		{	setVisible(false);
			super.setText("");
			ListElement e=L.last();
			Length=0;
			while (Length<MaxLength/4)
			{	Length+=((String)e.content()).length();
				if (e.previous()==null) break;
				e=e.previous();
			}
			while (e!=null)
			{	super.append((String)e.content());
				e=e.next();
			}
			setVisible(true);
		}
    	else super.append(s);
    }
    public void save (PrintWriter s)
    {	ListElement e=L.first();
    	while (e!=null)
    	{	s.print((String)e.content());
    		e=e.next();
    	}
    }
    public void setText (String s)
    {	Length=s.length();
    	super.setText(s);
    	L=new ListClass();
    	L.append(new ListElement(s));
    }
    public void setEditable (boolean flag)
    {	super.setEditable(flag);
    	if (!flag) setBackground(Global.gray.brighter());
    }
}
