package jagoclient.partner;

import com.Asgts.AG;                                               //~3213R~
import com.Asgts.R;                                                //+3213R~

import jagoclient.dialogs.Question;

/**
Question to accept a result or decline it.
*/

public class ResultQuestion extends Question
{	int B,W;
	PartnerFrame G;
	/**
	@param b,w Black and White results
	*/
	public ResultQuestion (PartnerFrame g, String m, int b, int w)
//  {	super(g,m,Global.resourceString("Result"),g,true); B=b; W=w; G=g;//~3119R~
    {                                                              //~3119I~
    	super(g,m,AG.resource.getString(R.string.Title_ResultQuestion),g,true,false);//~3119I~
		B=b; W=w; G=g;                                             //~3119I~
		show();
	}
	public void tell (Question q, Object o, boolean f)
	{	q.setVisible(false); q.dispose();
	    if (f) G.doresult(B,W);
		else G.declineresult();
	}
	public boolean close ()
	{	G.declineresult();
		return true;
	}	
}

