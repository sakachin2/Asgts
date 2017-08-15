//*CID://+@@@@R~:                             update#=    8;       //~@@@@I~
package jagoclient.dialogs;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.R;                                                //+@@@@R~
import com.Asgts.awt.ActionListener;                                //~2C26R~//+@@@@R~
//import com.Asgts.awt.FlowLayout;                                  //~2C26R~//+@@@@R~
import com.Asgts.awt.Frame;                                         //~2C26R~//+@@@@R~
import com.Asgts.awt.Panel;                                         //~2C26R~//+@@@@R~

import jagoclient.Global;
//import jagoclient.gui.ButtonAction;                              //~2C26R~
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
//import jagoclient.gui.MyLabel;                                   //~2C26R~
//import jagoclient.gui.MyPanel;                                   //~2C26R~
import jagoclient.gui.MyLabel;

//import java.awt.FlowLayout;
//import java.awt.Frame;
//import java.awt.Panel;
//import java.awt.event.ActionListener;

/**
The Question dialog displays a question and yes/no buttons.
It can be modal or non-modal. If the dialog is used modal, there
is no need to subclass it. The result can be asked after the
show method returns (which must be called from the creating method).
If the dialog is non-modal, it should be subclassed and the tell
method needs to be redefined to do something useful.
*/


public class Question extends CloseDialog 
    implements ActionListener
{	Object O;
	Frame F;
	public boolean Result=false;
	/**
	@param o an object to be passed to the tell method (may be null)
	@param flag determines, if the dialog is modal or not
	*/
//    public Question (Frame f, String c, String title, Object o, boolean flag)//~@@@@R~
    public Question (Frame f, String c, String title, Object o, boolean flag,boolean waitinput)//~@@@@I~
//  {	super(f,title,flag);                                       //~@@@@R~
    {                                                              //~@@@@I~
    	super(f,title,R.layout.question,flag/*modal*/,waitinput);//~@@@@I~
		F=f;
//        Panel pc=new MyPanel();                                  //~2C26R~
//        FlowLayout fl=new FlowLayout();                          //~2C26R~
//        pc.setLayout(fl);                                        //~2C26R~
//        fl.setAlignment(FlowLayout.CENTER);                      //~2C26R~
//        pc.add(new MyLabel(" "+c+" "));                          //~2C26R~
//        add("Center",pc);                                        //~2C26R~
//        Panel p=new MyPanel();                                   //~2C26R~
//        p.add(new ButtonAction(this,Global.resourceString("Yes")));//~2C26R~
//        p.add(new ButtonAction(this,Global.resourceString("No")));//~2C26R~
//        add("South",p);                                          //~2C26R~
//        O=o;                                                     //~2C26R~
//        if (flag) Global.setpacked(this,"question",300,150,f);   //~2C26R~
//        else Global.setpacked(this,"question",300,150);          //~2C26R~
//        new MyLabel(" "+c+" ");                                  //~@@@@R~
        new MyLabel(this,R.id.QMsg," "+c+" ");                     //~@@@@I~
        new ButtonAction(this,0,R.id.Yes);               //~@@@@R~
        new ButtonAction(this,0,R.id.No);                //~@@@@R~
		validate();
	}
	public void doAction (String o)
//  {	Global.notewindow(this,"question");                        //~2C26R~
	{                                                              //~2C26I~
//  	Global.notewindow(this,"question");                        //~2C26I~
//		if (Global.resourceString("Yes").equals(o))                //~@@@@R~
        if (o.equals(AG.resource.getString(R.string.Yes)))         //~@@@@I~
  		{	tell(this,O,true);
  			Result=true;
  		}
//		else if (Global.resourceString("No").equals(o))            //~@@@@R~
        if (o.equals(AG.resource.getString(R.string.No)))          //~@@@@I~
  		{	tell(this,O,false);
  		}
  		setVisible(false); dispose();
  	}
  	/** callback for non-modal dialogs */
	public void tell (Question q, Object o, boolean f)
	{
	}
	/** to get the result of the question */
	public boolean result ()
	{	return Result;
	}
}
