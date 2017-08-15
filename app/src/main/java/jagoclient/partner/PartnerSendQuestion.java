package jagoclient.partner;

import com.Asgts.AG;                                               //~3213R~
import com.Asgts.R;                                                //+3213R~
import com.Asgts.awt.TextField;                                     //~2C26R~//+3213R~

import jagoclient.Global;
//import jagoclient.gui.ButtonAction;                              //~2C26R~
import jagoclient.gui.CloseDialog;

//import java.awt.Panel;
//import java.awt.TextField;

/**
Displays a send dialog, when the partner presses "Send" in the
GoFrame. The message is appended to the PartnerFrame.
*/

public class PartnerSendQuestion extends CloseDialog
{	PartnerGoFrame F;
	TextField T;
	PartnerFrame PF;
	public PartnerSendQuestion (PartnerGoFrame f, PartnerFrame pf)
	{	super(f,Global.resourceString("Send"),false);
		F=f; PF=pf;
//        add("North",new MyLabel(Global.resourceString("Message_")));//~2C26R~
//        add("Center",T=new GrayTextField(25));                   //~2C26R~
//        Panel p=new MyPanel();                                   //~2C26R~
//        p.add(new ButtonAction(this,Global.resourceString("Say")));//~2C26R~
//        p.add(new ButtonAction(this,Global.resourceString("Cancel")));//~2C26R~
//        add("South",new Panel3D(p));                             //~2C26R~
//        Global.setpacked(this,"partnersend",200,150);            //~2C26R~
		validate(); show();
	}
	public void doAction (String o)
//  {	Global.notewindow(this,"partnersend");	                   //~2C26R~
  	{                                                              //~2C26I~
//        Global.notewindow(this,"partnersend");                   //~2C26I~
		if (Global.resourceString("Say").equals(o))
		{	if (!T.getText().equals(""))
			{	PF.out(T.getText());
//                F.addComment(Global.resourceString("Said__")+T.getText());//~2C26R~
			}
			setVisible(false); dispose();
		}
//  	else if (Global.resourceString(Global.resourceString("Cancel")).equals(o))//~3113R~
    	else if (Global.resourceString(AG.resource.getString(R.string.Cancel)).equals(o))//~3113I~
		{	setVisible(false); dispose();
		}
		else super.doAction(o);
	}
}
