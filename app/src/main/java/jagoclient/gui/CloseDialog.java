//*CID://+dateR~:                             update#=    1;       //+4C07I~
//*************************************************************************//+4C07I~
//1A4s 2014/12/06 utilize clipboard(view dialog)                   //+4C07I~
//*************************************************************************//+4C07I~
package jagoclient.gui;

//import java.awt.*;                                               //~1112R~
//import java.awt.event.*;                                         //~1112R~

//import android.app.Dialog;


import android.graphics.Point;

import com.Asgts.AG;                                               //~3213R~
import com.Asgts.R;                                                //~3213R~
import com.Asgts.awt.ActionEvent;                                   //~2C26R~//~3213R~
import com.Asgts.awt.ActionListener;                                //~2C26R~//~3213R~
import com.Asgts.awt.Dialog;                                        //~2C26R~//~3213R~
import com.Asgts.awt.Dimension;                                     //~2C26R~//~3213R~
import com.Asgts.awt.Frame;                                         //~2C26R~//~3213R~
import com.Asgts.awt.KeyEvent;                                      //~2C26R~//~3213R~
import com.Asgts.awt.KeyListener;                                   //~2C26R~//~3213R~
import com.Asgts.awt.WindowEvent;                                   //~2C26R~//~3213R~
import com.Asgts.awt.WindowListener;                                //~2C26R~//~3213R~

import jagoclient.Global;

/**
A dialog, which can be closed by clicking on the close window
field (a cross on the top right corner in Windows 95). This
dialog does also simplify event processing by implementing
a DoActionListener. The "Close" resource is reserved to close
the dialog. The escape key will close the dialog too.
*/
public class CloseDialog extends Dialog
    implements WindowListener, ActionListener, DoActionListener, KeyListener//~1112R~
{	public CloseDialog (Frame f, String s, boolean modal)
	{	super(f,"",modal);
	    addWindowListener(this);
	    setTitle(s);
	}
	public CloseDialog (Frame f, String s, int Presid, boolean modal,boolean waitInput)//~3114I~
	{	super(f,s,Presid,modal,waitInput);                                   //~3114I~
	    addWindowListener(this);                                   //~3114I~
	}                                                              //~3114I~
	public CloseDialog (Dialog Pdialog, String s, int Presid, boolean modal,boolean waitInput)//+4C07I~
	{	super(Pdialog,s,Presid,modal,waitInput);                   //+4C07I~
	    addWindowListener(this);                                   //+4C07I~
	}                                                              //+4C07I~
	public void windowActivated (WindowEvent e) {}
	public void windowClosed (WindowEvent e) {}
	public void windowClosing (WindowEvent e)
	{   if (close()) { setVisible(false); dispose(); }
	}
	public void windowDeactivated (WindowEvent e) {}
	public void windowDeiconified (WindowEvent e) {}
	public void windowIconified (WindowEvent e) {}
	public void windowOpened (WindowEvent e) {}
	/**
	May be overwritten to ask for permission to close this dialog.
	@return true if the dialog is allowed to close.
	*/
	public boolean close ()
	{	return true;
	}
	public boolean escape ()
	{	return close();
	}
	public void actionPerformed (ActionEvent e)
	{   doAction(e.getActionCommand());
	}
	public void doAction (String o)
//  {   if (Global.resourceString("Close").equals(o) && close())   //~2C30R~
    {                                                              //~2C30I~
        if (o.equals(AG.resource.getString(R.string.Close)) && close())//~2C30I~
		{	setVisible(false); dispose();
		}
		else if ("Close".equals(o) && close())
		{	setVisible(false); dispose();
		}	
	}
//    public void center (Frame f)                                 //~2C26R~
//    {   Dimension                                                //~2C26R~
//            si=f.getSize(),                                      //~2C26R~
//            d=getSize(),                                         //~2C26R~
//            dscreen=getToolkit().getScreenSize();                //~2C26R~
//        Point lo=f.getLocation();                                //~2C26R~
//        int x=lo.x+si.width/2-d.width/2;                         //~2C26R~
//        int y=lo.y+si.height/2-d.height/2;                       //~2C26R~
//        if (x+d.width>dscreen.width) x=dscreen.width-d.width-10; //~2C26R~
//        if (x<10) x=10;                                          //~2C26R~
//        if (y+d.height>dscreen.height) y=dscreen.height-d.height-10;//~2C26R~
//        if (y<10) y=10;                                          //~2C26R~
//        setLocation(x,y);                                        //~2C26R~
//    }                                                            //~2C26R~
	public void itemAction (String o, boolean flag) {}
	public void keyPressed (KeyEvent e)
	{	if (e.getKeyCode()==KeyEvent.VK_ESCAPE && close()) dispose();
	}
	public void keyReleased (KeyEvent e) {}
	public void keyTyped (KeyEvent e) {}
}

