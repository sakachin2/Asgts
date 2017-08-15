//*CID://+@@@2R~:                             update#=   12;       //~@@@@I~//~@@@2R~
package jagoclient.gui;

import jagoclient.Dump;
import jagoclient.Global;

//import java.awt.Frame;                                           //~1113R~
//import java.awt.Image;                                           //~1113R~
//import java.awt.MediaTracker;                                    //~1113R~
//import java.awt.Toolkit;                                         //~1113R~
//import java.awt.event.ActionEvent;                               //~1113R~
//import java.awt.event.ActionListener;                            //~1113R~
//import java.awt.event.WindowEvent;                               //~1113R~
//import java.awt.event.WindowListener;                            //~1113R~
import java.io.InputStream;
//import java.util.Hashtable;                                      //~1417R~
//import   com.Asgts.java.Hashtable;                                //~1417I~//~@@@@R~//~@@@2R~

import com.Asgts.AG;                                               //+@@@2R~
import com.Asgts.R;                                                //+@@@2R~
import com.Asgts.awt.ActionEvent;                                   //~@@@@R~//+@@@2R~
import com.Asgts.awt.ActionListener;                                //~@@@@R~//+@@@2R~
import com.Asgts.awt.Frame;                                         //~@@@@R~//+@@@2R~
import com.Asgts.awt.Image;                                         //~@@@@R~//+@@@2R~
import com.Asgts.awt.MediaTracker;                                  //~@@@@R~//+@@@2R~
import com.Asgts.awt.Toolkit;                                       //~@@@@R~//+@@@2R~
import com.Asgts.awt.WindowEvent;                                   //~@@@@R~//+@@@2R~
import com.Asgts.awt.WindowListener;                                //~@@@@R~//+@@@2R~

import rene.util.list.ListClass;
import rene.util.list.ListElement;

/**
A Frame, which can be closed with the close button in the window.
Moreover, event handling is simplified with the DoActionListnener
interface. There is also a method for setting the icon of this
window.
*/
public class CloseFrame extends Frame
    implements WindowListener, ActionListener, DoActionListener    //~1113R~//~1125R~
{	ListClass L;
	public CloseFrame (String s)                                   //~1122I~
	{	super("");                                                 //~1122I~
		L=new ListClass();                                         //~1122I~
	    addWindowListener(this);                                   //~1122I~
	    setTitle(s);                                               //~1122I~
	}                                                              //~1122I~
	public CloseFrame (int Presid,String s)                        //~@@@2I~
	{                                                              //~@@@2I~
    	super(Presid,s);	//setlayout by resid                   //~@@@2R~
		L=new ListClass();                                         //~@@@2I~
        if (Presid!=0)                                            //~@@@2I~
        {                                                          //~@@@2I~
	    	addWindowListener(this);                               //~@@@2R~
	    	setTitle(s);                                           //~@@@2R~
        }                                                          //~@@@2I~
	}                                                              //~@@@2I~
	public void windowActivated (WindowEvent e) {}
	public void windowClosed (WindowEvent e) {}
	public void windowClosing (WindowEvent e)
	{   if (close()) { doclose(); }
	}
	public void windowDeactivated (WindowEvent e) {}
	public void windowDeiconified (WindowEvent e) {}
	public void windowIconified (WindowEvent e) {}
	public void windowOpened (WindowEvent e) {}
	public boolean close ()
	{	return true;
	}
	public void actionPerformed (ActionEvent e)
	{   doAction(e.getActionCommand());
	}
	public void doAction (String o)
	{   if (Global.resourceString("Close").equals(o) && close())
		{	doclose();
		}
        else if (o.equals(AG.resource.getString(R.string.Close)))  //~@@@@I~
		{                                                          //~@@@@I~
			doclose();                                             //~@@@@I~
		}                                                          //~@@@@I~
	}
	public void doclose ()
//  {	if (Global.getParameter("menuclose",true)) setMenuBar(null);//~@@@@R~
	{	if (true) setMenuBar(null);                                //~@@@@I~
    	if (Dump.Y) Dump.println("CloseFrame doclose()");          //~@@@2I~
		setVisible(false); dispose();
	}
	public void addCloseListener (CloseListener cl)
	{	L.append(new ListElement(cl));
	}
	public void inform ()
	{	ListElement e=L.first();
		while (e!=null)
		{	try
			{	((CloseListener)e.content()).isClosed();
			}
			catch (Exception ex) {}
			e=e.next();
		}
	}
	public void removeCloseListener (CloseListener cl)
	{	ListElement e=L.first();
		while (e!=null)
		{	CloseListener l=(CloseListener)e.content();
			if (l==cl) { L.remove(e); break; }
			e=e.next();
		}
	}
	public void itemAction (String o, boolean flag) {}
	// the icon things
//    static Hashtable Icons=new Hashtable();                      //~@@@@R~
	public void seticon (String file)
	{	try
		{                                                          //~@@@@R~
//            Object o=Icons.get(file);                            //~@@@@I~
//            if (o==null)                                         //~@@@@R~
//            {   Image i;                                         //~@@@@R~
//                InputStream in=getClass().getResourceAsStream("/jagoclient/gifs/"+file);//~@@@@R~
//                int pos=0;                                       //~@@@@R~
//                int n=in.available();                            //~@@@@R~
//                byte b[]=new byte[20000];                        //~@@@@R~
//                while (n>0)                                      //~@@@@R~
//                {   int k=in.read(b,pos,n);                      //~@@@@R~
//                    if (k<0) break;                              //~@@@@R~
//                    pos+=k;                                      //~@@@@R~
//                    n=in.available();                            //~@@@@R~
//                }                                                //~@@@@R~
//                i=Toolkit.getDefaultToolkit().createImage(b,0,pos);//~@@@@R~
//                MediaTracker T=new MediaTracker(this);           //~@@@@R~
//                T.addImage(i,0);                                 //~@@@@R~
//                T.waitForAll();                                  //~@@@@R~
//                Icons.put(file,i);                               //~@@@@R~
//                setIconImage(i);                                 //~@@@@R~
//            }                                                    //~@@@@R~
//            else                                                 //~@@@@R~
//            {   setIconImage((Image)o);                          //~@@@@R~
//            }                                                    //~@@@@R~
            setIconImage(new Image(file));                         //~@@@@I~
		} catch (Exception e) {}
	}
}
