//*CID://+1Ah0R~:                             update#=   29;       //~1Ah0R~
//**************************************************************************//~1A1dI~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//1A2i 2013/04/03 (Bug)Resig on Local board; Endgame question is End game?//~1A29I~
//1A29 2013/03/25 confirmation also on suspend requester when initiate suspwend//~1A29I~
//1A23 2013/03/23 File Dialog on PartnerGoFrame                    //~1A23I~
//1A22 2013/03/23 File Dialog on Local Board                       //~1A22I~
//1A1d 2013/03/17 popup at partner when decliened resign           //~1A1dI~
//**************************************************************************//~1A1dI~
package jagoclient.partner;

import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.R;                                                //~@@@@R~
import com.Asgts.gtp.GtpFrame;
import com.Asgts.gtp.GtpGoFrame;

import jagoclient.Dump;
import jagoclient.LocalFrame;
import jagoclient.LocalGoFrame;
import jagoclient.dialogs.Question;

/**
Question to end the game, or decline that.
*/

public class EndGameQuestion extends Question
{	PartnerFrame G;
	PartnerGoFrame PGF;                                            //~1A29I~
	LocalFrame LG;                                                    //~@@@@I~
	LocalGoFrame LGF;                                              //~1A22I~
    GtpFrame GF;                                                   //+1Ah0R~
	GtpGoFrame GGF;                                                //~1Ah0I~
    boolean resign,swSuspend;                                                //~@@@@I~
	public EndGameQuestion (PartnerFrame g)
//  {	super(g,Global.resourceString("End_the_game_"),Global.resourceString("End"),g,true); G=g;//~@@@@R~
    {                                                              //~@@@@R~
    	super(g,AG.resource.getString(R.string.End_the_game),AG.resource.getString(R.string.Title_EndGameQuestion),g,true,false); G=g;//~@@@@I~
		show();
	}
	public EndGameQuestion (PartnerGoFrame Ppgf)                   //~1A29I~
    {                                                              //~1A29I~
    	super(Ppgf,AG.resource.getString(R.string.Suspend_the_game),AG.resource.getString(R.string.Title_EndGameQuestion),Ppgf,true,false);//~1A29I~
		PGF=Ppgf;                                                  //~1A29I~
		show();                                                    //~1A29I~
	}                                                              //~1A29I~
	public EndGameQuestion (PartnerFrame g,boolean Psuspend)       //~1A23I~
    {                                                              //~1A23I~
    	super(g,AG.resource.getString(R.string.PartnerSuspendReq),AG.resource.getString(R.string.Title_SuspendGame),g,true,false); G=g;//~1A23R~
        swSuspend=Psuspend;                                       //~1A23I~
		show();                                                    //~1A23I~
	}                                                              //~1A23I~
	public EndGameQuestion (PartnerFrame g,String Pmsg,boolean Presign)//~@@@@I~
	{	super(g,Pmsg,AG.resource.getString(R.string.Title_EndGameQuestion),g,true,false);//~@@@@R~
		G=g;                                                       //~@@@@I~
    	resign=Presign;                                            //~@@@@R~
		show();                                                    //~@@@@I~
	}                                                              //~@@@@I~
	public EndGameQuestion (LocalFrame g)                            //~@@@@I~
	{                                                              //~@@@@I~
//		super(g,Global.resourceString("End_the_game_"),Global.resourceString("End"),g,true,false);//~@@@@R~
  		super(g,AG.resource.getString(R.string.End_the_game),AG.resource.getString(R.string.Title_EndGameQuestion),g,true,false);//~@@@@I~
		LG=g;                                                     //~@@@@I~
		show();                                                    //~@@@@I~
	}                                                              //~@@@@I~
	public EndGameQuestion (LocalFrame g,boolean Presign)          //~1A29I~
	{                                                              //~1A29I~
  		super(g,AG.resource.getString(R.string.EndgameResign),AG.resource.getString(R.string.Title_EndGameQuestion),g,true,false);//~1A29I~
		LG=g;                                                      //~1A29I~
		show();                                                    //~1A29I~
	}                                                              //~1A29I~
	public EndGameQuestion (LocalGoFrame g)                        //~1A22I~
	{                                                              //~1A22I~
  		super(g,AG.resource.getString(R.string.Suspend_the_game),AG.resource.getString(R.string.Title_SuspendGame),g,true,false);//~1A22I~
		LGF=g;                                                     //~1A22I~
		show();                                                    //~1A22I~
	}                                                              //~1A22I~
    public EndGameQuestion (GtpFrame g,String Pmsg,String Ptitle,boolean Presign,boolean Psuspend)//+1Ah0R~
    {                                                              //+1Ah0R~
        super(g,Pmsg,Ptitle,g,true,false);                         //+1Ah0R~
        resign=Presign;                                            //+1Ah0R~
        swSuspend=Psuspend;                                        //+1Ah0R~
        GF=g;                                                      //+1Ah0R~
        show();                                                    //+1Ah0R~
    }                                                              //+1Ah0R~
	public EndGameQuestion (GtpGoFrame Pggf)                       //~1Ah0I~
    {                                                              //~1Ah0I~
    	super(Pggf,AG.resource.getString(R.string.Suspend_the_game),AG.resource.getString(R.string.Title_EndGameQuestion),Pggf,true,false);//~1Ah0I~
		GGF=Pggf;                                                  //~1Ah0I~
        swSuspend=true;                                            //~1Ah0I~
		show();                                                    //~1Ah0I~
	}                                                              //~1Ah0I~
	public void tell (Question q, Object o, boolean f)
	{	q.setVisible(false); q.dispose();
    	if (Dump.Y) Dump.println("EndGameQuestion:tell() accept="+f+",resign="+resign);//~@@@@R~
      if (LGF!=null)                                               //~1A22I~
      {                                                            //~1A22I~
	    if (f) LGF.saveGame(null);                                     //~1A22R~
      }                                                            //~1A22I~
      else                                                         //~1A22I~
      if (LG!=null)                                                //~@@@@I~
      {                                                            //~@@@@I~
	    if (f) LG.doendgame();                                     //~@@@@I~
		else LG.declineendgame();                                  //~@@@@I~
      }                                                            //~@@@@I~
      else                                                         //~@@@@I~
      if (PGF!=null)                                               //~1A29I~
      {                                                            //~1A29I~
	    if (f)                                                     //~1A29I~
			PGF.doSuspendGame();                                   //~1A29I~
      }                                                            //~1A29I~
      else                                                         //~1A29I~//+1Ah0R~
      if (GF!=null) //GtpFrame                                     //+1Ah0R~
      {                                                            //+1Ah0R~
        if (f)                                                     //+1Ah0R~
        {                                                          //+1Ah0R~
            if (resign)                                            //+1Ah0R~
                GF.doresign();                                     //+1Ah0R~
//            else                                                 //+1Ah0R~
//            if (swSuspend)                                       //+1Ah0R~
//                GF.dosuspend();                                  //+1Ah0R~
            else                                                   //+1Ah0R~
                G.doendgame();                                     //+1Ah0R~
        }                                                          //+1Ah0R~
      }                                                            //+1Ah0R~
      else                                                         //~1Ah0I~
      if (GGF!=null)	//GtpGoFrame                               //~1Ah0I~
      {                                                            //~1Ah0I~
	    if (f)                                                     //~1Ah0I~
        {                                                          //~1Ah0I~
            if (swSuspend)                                         //~1Ah0I~
	 			GGF.doSuspendGame();                                   //~1Ah0R~
        }                                                          //~1Ah0I~
      }                                                            //~1Ah0I~
      else                                                         //~1Ah0I~
//      if (f) G.doendgame();                                      //~@@@@R~
        if (f)                                                     //~@@@@I~
        {                                                          //~1A1dI~
        	if (resign)                                            //~@@@@I~
	 			G.doresign();                                      //~@@@@R~
            else                                                   //~@@@@I~
            if (swSuspend)                                         //~1A23I~
	 			G.dosuspendgame();                                 //~1A23I~
            else                                                   //~1A23I~
	 			G.doendgame();                                     //~@@@@I~
        }                                                          //~1A1dI~
//  	else G.declineendgame();                                   //~1A1dR~
    	else                                                       //~1A1dI~
        {                                                          //~1A1dI~
          if (!resign)                                              //~1A1dI~
           if (swSuspend)                                          //~1A23I~
	 		G.declinesuspendgame();                                //~1A23I~
           else                                                    //~1A23I~
			G.declineendgame();                                    //~1A1dI~
        }                                                          //~1A1dI~
	}
	public boolean close ()
	{	G.declineendgame();
		return true;
	}
}

