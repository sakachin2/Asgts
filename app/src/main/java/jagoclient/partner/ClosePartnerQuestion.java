package jagoclient.partner;

import com.Asgts.AG;                                               //~3213R~
import com.Asgts.R;                                                //+3213R~

import jagoclient.dialogs.Question;

class ClosePartnerQuestion extends Question
{	public ClosePartnerQuestion (PartnerFrame g)
//	{	super(g,Global.resourceString("This_will_close_your_connection_"),Global.resourceString("Close"),g,true);//~3119R~
	{	super(g,AG.resource.getString(R.string.This_will_close_your_connection),AG.resource.getString(R.string.Title_ClosePartnerQuestion),g,true,false);//~3119I~
		show();
	}
	public void tell (Question q, Object o, boolean f)
	{	q.setVisible(false); q.dispose();
	    if (f) ((PartnerFrame)o).doclose();
	}
}
