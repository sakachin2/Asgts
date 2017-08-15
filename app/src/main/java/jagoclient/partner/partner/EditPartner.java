//*CID://+1A45R~:                             update#=   26;       //~1A45R~
//*************************************************************************//~v107I~
//1A45 2014/10/13 isuue err when partner list name is space which could not select to delete//~1A45I~
//1A36 2013/04/19 set selection after IP partner list update/add   //~1A36I~
//101a 2013/01/30 IP connection                                    //~v101I~
//1072:121206 partner add diaplay default is 6970,but set 6969     //~v107I~
//*************************************************************************//~v107I~
package jagoclient.partner.partner;

//import java.awt.*;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.AView;
import com.Asgts.R;                                                //~v101R~
import com.Asgts.awt.Choice;                                       //~v101R~
import com.Asgts.awt.Frame;                                        //~v101R~
//import com.Asgts.awt.GridLayout;                                 //~v101R~
import com.Asgts.awt.Panel;                                        //~v101R~
import com.Asgts.awt.TextField;                                    //~v101R~

import jagoclient.gui.*;
import jagoclient.partner.IPConnection;
import jagoclient.dialogs.*;
import jagoclient.Go;
import jagoclient.Global;

import rene.util.list.*;

public class EditPartner extends CloseDialog
{	ListClass PList;
	Partner C;
	TextField Name, Server, Port;
    private boolean add;                                           //~v101I~
//  Go G;                                                          //~v107R~
	IPConnection G;                                                //~v107I~
//	Choice State;
	Frame F;
	public EditPartner (CloseFrame f, ListClass plist, Partner c,
//        Go go)                                                   //~v107R~
        IPConnection Pipc)                                         //~v107R~
                                                                   //~v107I~
//  {	super(f,Global.resourceString("Edit_Connection"),true);    //~v101R~
    {                                                              //~v101I~
        super(f,AG.resource.getString(R.string.Title_EditPartner),R.layout.editpartner,true,false);//~v101I~
//        G=go; F=f;                                               //~v107R~
        F=f;                                                       //~v107I~
        G=Pipc;                                                    //~v107R~
		PList=plist; C=c;
        add=false;                                                 //~v101I~
//        Panel p1=new MyPanel();                                  //~v107R~
//        p1.setLayout(new GridLayout(0,2));                       //~v107R~
//        p1.add(new MyLabel(Global.resourceString("Name")));      //~v107R~
//        p1.add(Name=new FormTextField(""+C.Name));               //~v107R~
//        p1.add(new MyLabel(Global.resourceString("Server")));    //~v107R~
//        p1.add(Server=new FormTextField(C.Server));              //~v107R~
//        p1.add(new MyLabel(Global.resourceString("Port")));      //~v107R~
//        p1.add(Port=new FormTextField(""+C.Port));               //~v107R~
//        p1.add(new MyLabel(Global.resourceString("State")));     //~v107R~
//        p1.add(State=new Choice());                              //~v107R~
//        State.setFont(Global.SansSerif);                         //~v107R~
//        State.add(Global.resourceString("silent"));              //~v107R~
//        State.add(Global.resourceString("private"));             //~v107R~
//        State.add(Global.resourceString("local"));               //~v107R~
//        State.add(Global.resourceString("public"));              //~v107R~
//        State.select(C.State);                                   //~v107R~
//        add("Center",new Panel3D(p1));                           //~v107R~
//        Panel p=new MyPanel();                                   //~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Set")));//~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Add")));//~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Cancel")));//~v107R~
//        p.add(new MyLabel(" "));                                 //~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Help")));//~v107R~
//        add("South",new Panel3D(p));                             //~v107R~
//        Global.setpacked(this,"editp",300,200,F);                //~v107R~
//        Name=new FormTextField(R.id.PartnerName,C.Name);           //~v107I~//~v101R~
//        Server=new FormTextField(R.id.Server,C.Server);            //~v107I~//~v101R~
//        Port=new FormTextField(R.id.Port,""+C.Port);                 //~v107I~//~v101R~
        Name=new FormTextField(this,R.id.PartnerName,C.Name);      //~v101I~
        Server=new FormTextField(this,R.id.Server,C.Server);       //~v101I~
        Port=new FormTextField(this,R.id.Port,""+C.Port);          //~v101I~
//        ButtonAction.init(this,0,R.id.OK);  //Request             //~v107I~//~v101R~
//        ButtonAction.init(this,0,R.id.Cancel);  //Cancel           //~v107I~//~v101R~
//        ButtonAction.init(this,0,R.id.Help);  //Help               //~v107I~//~v101R~
        new ButtonAction(this,0,R.id.OK);  //Request          //~v101I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel       //~v101I~
        new ButtonAction(this,0,R.id.Help);  //Help           //~v101I~
		validate(); show();
//        Name.requestFocus();                                     //~v107R~
	}
	public EditPartner (CloseFrame f, ListClass plist,
//  	Go go)                                                     //~v107R~
        IPConnection Pipc)                                         //~v107I~
//  {	super(f,Global.resourceString("Edit_Connection"),true);    //~v101R~
    {                                                              //~v101I~
        super(f,AG.resource.getString(R.string.Title_EditPartner),R.layout.editpartner,true,false);//~v101I~
//  	G=go; F=f;                                                 //~v107R~
        F=f;                                                       //~v107I~
        G=Pipc;                                                    //~v107I~
		PList=plist;
        add=true;                                                  //~v101I~
//        Panel p1=new MyPanel();                                  //~v107R~
//        p1.setLayout(new GridLayout(0,2));                       //~v107R~
//        p1.add(new MyLabel(Global.resourceString("Name")));      //~v107R~
//        p1.add(Name=new FormTextField(Global.resourceString("Name_for_list")));//~v107R~
//        p1.add(new MyLabel(Global.resourceString("Server")));    //~v107R~
//        p1.add(Server=new FormTextField(Global.resourceString("Internet_server_name")));//~v107R~
//        p1.add(new MyLabel(Global.resourceString("Port")));      //~v107R~
//        p1.add(Port=new FormTextField("Port (default 6970)"));   //~v107R~
//        p1.add(new MyLabel(Global.resourceString("State")));     //~v107R~
//        p1.add(State=new Choice());                              //~v107R~
//        State.setFont(Global.SansSerif);                         //~v107R~
//        State.add(Global.resourceString("silent"));              //~v107R~
//        State.add(Global.resourceString("private"));             //~v107R~
//        State.add(Global.resourceString("local"));               //~v107R~
//        State.add(Global.resourceString("public"));              //~v107R~
//        State.select(1);                                         //~v107R~
//        add("Center",new Panel3D(p1));                           //~v107R~
//        Panel p=new MyPanel();                                   //~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Add")));//~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Cancel")));//~v107R~
//        p.add(new MyLabel(" "));                                 //~v107R~
//        p.add(new ButtonAction(this,Global.resourceString("Help")));//~v107R~
//        add("South",new Panel3D(p));                             //~v107R~
//        Global.setpacked(this,"editp",300,200,F);                //~v107R~
		String s;                                                  //~v101I~
        if (AG.PartnerName!=null)                                  //~v101I~
			s=AG.PartnerName;                                      //~v101I~
        else                                                       //~v101I~
			s=AG.resource.getString(R.string.Name_for_list);       //~v101I~
//        Name=new FormTextField(R.id.PartnerName,s);//~v107I~     //~v101R~
        Name=new FormTextField(this,R.id.PartnerName,s);           //~v101I~
        if (AG.RemoteInetAddress!=null)                            //~v101I~
			s=AG.RemoteInetAddress;                                //~v101I~
        else                                                       //~v101I~
			s=AG.resource.getString(R.string.Internet_server_name);//~v101I~
//        Server=new FormTextField(R.id.Server,s);//~v107I~        //~v101R~
//        Port=new FormTextField(R.id.Port,AG.resource.getString(R.string.Port_default,AG.DEFAULT_SERVER_PORT));  //~v107I~//~v101R~
        Server=new FormTextField(this,R.id.Server,s);              //~v101I~
        Port=new FormTextField(this,R.id.Port,AG.resource.getString(R.string.Port_default,AG.DEFAULT_SERVER_PORT));//~v101I~
        new ButtonAction(this,0,R.id.OK);  //Add                 //~v107I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel           //~v107I~
        new ButtonAction(this,0,R.id.Help);  //Help               //~v107I~
		validate(); show();
//        Name.requestFocus();                                     //~v107R~
	}
  	public void doAction (String o)
//  	{	Global.notewindow(this,"editp");
  	{
//        if (Global.resourceString("Set").equals(o))              //~v101R~
        if (o.equals(AG.resource.getString(R.string.OK)) && !add)   //~v101I~
//		{	C.Name=Name.getText();                                 //~1A45R~
  		{                                                          //~1A45I~
            String name=Name.getText();                            //+1A45R~
            if (name.equals(""))                                   //~1A45R~
            {                                                      //~1A45I~
        		AView.showToast(R.string.ErrNameIsSpace);          //~1A45I~
                return;                                            //~1A45I~
            }                                                      //~1A45I~
            C.Name=name;                                           //~1A45I~
  			C.Server=Server.getText();
//  			C.State=State.getSelectedIndex();
  			try
  			{	C.Port=Integer.parseInt(Port.getText());
  			}
  			catch (NumberFormatException ex)
//			{	C.Port=6970;                                       //~v101R~
  			{	C.Port=AG.DEFAULT_SERVER_PORT;                     //~v101I~
  			}
  			finally
//			{	G.updateplist();                                   //~1A36R~
  			{                                                      //~1A36R~
  			    G.updateplist(C.Name);                             //~1A36R~
  				setVisible(false); dispose();
  			}
  		}
//		else if (Global.resourceString("Add").equals(o))           //~v101R~
        else if (o.equals(AG.resource.getString(R.string.OK)) && add)//~v101I~
  		{	Partner C=new Partner("[?] [?] [?]");
//			C.Name=Name.getText();                                 //~1A45R~
            String name=Name.getText();                            //~1A45I~
            if (name.equals(""))                                   //~1A45I~
            {                                                      //~1A45I~
        		AView.showToast(R.string.ErrNameIsSpace);          //~1A45I~
                return;                                            //~1A45I~
            }                                                      //~1A45I~
            C.Name=name;                                           //~1A45I~
  			C.Server=Server.getText();
//  			C.State=State.getSelectedIndex();
  			try
  			{	C.Port=Integer.parseInt(Port.getText());
  			}
  			catch (NumberFormatException ex)
//			{	C.Port=6969;                                       //~v107R~
//			{	C.Port=6970;                                       //~v107I~//~v101R~
  			{	C.Port=AG.DEFAULT_SERVER_PORT;                     //~v101I~
  			}
  			finally
  			{	if (G.pfind(C.Name)!=null)
  				{	C.Name=C.Name+" DUP";
  				}
  				PList.append(new ListElement(C));
//				G.updateplist();                                   //~1A36R~
  				G.updateplist(C.Name);                             //~1A36R~
  				setVisible(false); dispose();
  			}
  		}
//        else if (Global.resourceString("Cancel").equals(o))      //~v101R~
        else if (o.equals(AG.resource.getString(R.string.Cancel))) //~v101I~
  		{	setVisible(false); dispose(); 
  		}
//        else if (Global.resourceString("Help").equals(o))        //~v101R~
//        {   new HelpDialog(F,"confpartner");                     //~v101R~
//        }                                                        //~v101R~
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~v101I~
        {                                                          //~v101I~
//      	new HelpDialog(null,R.string.Help_EditPartner);        //~v101R~
        	new HelpDialog(null,R.string.HelpTitle_EditPartner,"EditPartner");//~v101R~
        }                                                          //~v101I~
		else super.doAction(o);
  	}
}

