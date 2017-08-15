//*CID://+1A30R~:                             update#=   80;       //~@@@@I~//~@@@2R~//~v1A0R~//~1A30R~
//***********************************************************************//~v1A0I~
//1A30 2013/04/06 kif,ki2 format support                           //~1A30I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//***********************************************************************//~v1A0I~
package jagoclient.partner;

import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.Prop;
import com.Asgts.R;                                                //~@@@2R~
import com.Asgts.awt.Checkbox;                                     //~@@@2R~
import com.Asgts.awt.Frame;                                        //~@@@2R~

import jagoclient.Dump;
import jagoclient.dialogs.HelpDialog;
import jagoclient.dialogs.SpinButtonDialog;
//import jagoclient.gui.ButtonAction;                              //~2C26R~
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.FormTextField;                             //~2C26R~//~@@@@R~

public class HandicapQuestion extends CloseDialog
{                                                                  //~@@@@I~
	private static final String PKEY_RANKDIFF="RankDiff";
	private static final int RANKDIFF_MIN=0;
	private static final int RANKDIFF_MAX=10;
	private GameQuestion GQ;                                       //~v1A0R~
    protected FormTextField YourName;                              //~@@@2I~
    private Checkbox[] cbHandicap;                                 //~v1A0R~
    public int Handicap;
    private int rankdiff;//~v1A0I~
	private int maxhc;                                             //~v1A0I~
	private boolean buttonOK;                                      //~v1A0I~
    private Frame frame;                                           //~v1A0I~
    private FormTextField RankDiff;                                //~v1A0I~
    public static final int HC_LANCE1 =0x000001;                  //~v1A0I~//~1A30R~
    public static final int HC_LANCE2 =0x000002;                  //~v1A0I~//~1A30R~
    public static final int HC_KNIGHT1=0x000004;                  //~v1A0I~//~1A30R~
    public static final int HC_KNIGHT2=0x000008;                  //~v1A0I~//~1A30R~
    public static final int HC_SILVER1=0x000010;                  //~v1A0I~//~1A30R~
    public static final int HC_SILVER2=0x000020;                  //~v1A0I~//~1A30R~
    public static final int HC_GOLD1  =0x000040;                  //~v1A0I~//~1A30R~
    public static final int HC_GOLD2  =0x000080;                  //~v1A0I~//~1A30R~
    public static final int HC_BISHOP =0x000100;                  //~v1A0I~//~1A30R~
    public static final int HC_ROOK   =0x000200;                  //~v1A0I~//~1A30R~
    private static final int[] Shandicap=                          //~v1A0I~
    		{                                                      //~v1A0I~
            	0,          //0                                    //~v1A0I~
            	0,          //1                                    //~v1A0I~
            	HC_LANCE1,	//2                                    //~v1A0I~
            	HC_BISHOP,	//3                                    //~v1A0I~
            	HC_ROOK,	//4                                    //~v1A0I~
            	HC_ROOK+HC_LANCE1,	//5                            //~v1A0I~
            	HC_ROOK+HC_BISHOP,	//6                            //~v1A0I~
            	HC_ROOK+HC_BISHOP,	//7                            //~v1A0I~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2,//8          //~v1A0I~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2,//9          //~v1A0I~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2+HC_KNIGHT1+HC_KNIGHT2,//10//~v1A0I~
            };                                                     //~v1A0I~
//******************************                                   //~@@@2I~
//*for LocalGameQuestion                                           //~@@@2I~
//******************************                                   //~@@@2I~
	public HandicapQuestion(Frame Pframe,GameQuestion Pgq)         //~v1A0R~
	{                                                              //~@@@2I~
        super(Pframe,AG.resource.getString(R.string.Title_HandicapQuestion),R.layout.handicapquestion,true,false);//~@@@2R~//~v1A0R~
        frame=Pframe;                                              //~v1A0I~
		GQ=Pgq;                                                    //~v1A0I~
        String[] label=AG.pieceNameHandicap;                     //~v1A0I~
        maxhc=label.length;                                        //~v1A0I~
        cbHandicap=new Checkbox[maxhc];                             //~v1A0I~
        cbHandicap[0]=new Checkbox(this,R.id.HandicapLance1);      //~v1A0I~
        cbHandicap[1]=new Checkbox(this,R.id.HandicapLance2);      //~v1A0I~
        cbHandicap[2]=new Checkbox(this,R.id.HandicapKnight1);     //~v1A0I~
        cbHandicap[3]=new Checkbox(this,R.id.HandicapKnight2);     //~v1A0I~
        cbHandicap[4]=new Checkbox(this,R.id.HandicapSilver1);     //~v1A0I~
        cbHandicap[5]=new Checkbox(this,R.id.HandicapSilver2);     //~v1A0I~
        cbHandicap[6]=new Checkbox(this,R.id.HandicapGold1);       //~v1A0I~
        cbHandicap[7]=new Checkbox(this,R.id.HandicapGold2);       //~v1A0I~
        cbHandicap[8]=new Checkbox(this,R.id.HandicapBishop);      //~v1A0I~
        cbHandicap[9]=new Checkbox(this,R.id.HandicapRook);        //~v1A0I~
		 rankdiff=Prop.getPreference(PKEY_RANKDIFF,0);              //~v1A0I~
        RankDiff=new FormTextField(this,R.id.EditTextRankDiff,Integer.toString(rankdiff));//~v1A0I~
        Handicap=GQ.Handicap;                                      //~v1A0R~
        setHandicapState(Handicap);                                //~v1A0I~
        new ButtonAction(this,0,R.id.RankDiffChange);              //~v1A0R~
        new ButtonAction(this,0,R.id.RankDiffSetDefault);          //~v1A0I~
        new ButtonAction(this,0,R.id.OK);  //Request               //~v1A0I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel       //~@@@2I~
        new ButtonAction(this,0,R.id.Help);  //Help           //~@@@2I~
        setDismissActionListener(this/*DoActionListener*/);	//callback from DialogClosed at dismiss//~v1A0I~
		validate();
		show();
	}
	public void doAction (String o)
    {                                                              //~@@@@I~
    	if (Dump.Y) Dump.println("handicapQuestion doAction="+o);  //~v1A0I~
        if (o.equals(AG.resource.getString(R.string.OK)))     //~@@@@I~//~v1A0R~
		{                                                          //~@@@@R~
        	int hc=getHandicapState();                                  //~v1A0R~
            if (hc!=Handicap)                                      //~v1A0I~
            {                                                      //~v1A0I~
            	buttonOK=true;                                     //~v1A0I~
            	Handicap=hc;                                       //~v1A0R~
            }                                                      //~v1A0I~
			Prop.putPreference(PKEY_RANKDIFF,rankdiff);            //~v1A0I~
			setVisible(false); dispose();                          //~v1A0I~
		}
        else if (o.equals(AG.resource.getString(R.string.Cancel))) //~@@@@I~
		{	setVisible(false); dispose();
		}
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~2C30I~//~@@@@I~
		{                                                          //~2C30I~//~@@@@I~
            new HelpDialog(null,R.string.HelpTitle_HandicapQuestion,"HandicapQuestion");//~v1A0R~
		}                                                          //~2C30I~//~@@@@I~
        else                                                       //~v1A0I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))  //modal but no inputWait//~v101I~//~v1A0I~
        {               //callback from Dialog after currentLayout restored//~v101I~//~v1A0I~
        	if (buttonOK)                                             //~v1A0I~
            {                                                      //~v1A0I~
		    	if (Dump.Y) Dump.println("handicapQuestion dismissdialog handicap="+Handicap);//~v1A0I~
            	GQ.Handicap=Handicap;                              //~v1A0I~
            	GQ.doAction(AG.resource.getString(R.string.ActionHandicapChanged));//~v1A0I~
            }                                                      //~v1A0I~
        }                                                      //~v101I~//~v1A0I~
        else                                                       //~v1A0I~
        if (o.equals(AG.resource.getString(R.string.RankDiffChange)))  //modal but no inputWait//~v1A0I~
        {                                                          //~v1A0I~
			new SpinButtonDialog(frame,RANKDIFF_MIN,RANKDIFF_MAX,RankDiff);//~v1A0I~
        }                                                          //~v1A0I~
        else                                                       //~v1A0I~
        if (o.equals(AG.resource.getString(R.string.RankDiffSetDefault)))  //modal but no inputWait//~v1A0I~
        {                                                          //~v1A0I~
			setStandardHandicap();                                 //~v1A0I~
        }                                                          //~v1A0I~
	}
    //*********************************************************    //~@@@2I~
    public int getHandicapState()                                   //~@@@2I~//~v1A0R~
    {                                                              //~@@@2I~
    	int hc=0;                                                  //~v1A0I~
        for (int ii=0;ii<maxhc;ii++)                               //~v1A0I~
        {                                                          //~v1A0I~
        	if (cbHandicap[ii].getState())                         //~v1A0I~
            	hc|=(1<<ii);                                       //~v1A0I~
        }                                                          //~v1A0I~
        if (Dump.Y) Dump.println("HandicapQuestion hc="+Integer.toHexString(hc));//~v1A0I~
        return hc;                                                 //~v1A0I~
    }                                                              //~@@@2I~
//************************************************************     //~v1A0I~
    private void setHandicapState(int Phandicap)                   //~v1A0R~
	{                                                              //~v1A0I~
        for (int ii=0;ii<maxhc;ii++)                               //~v1A0R~
        {                                                          //~v1A0I~
        	cbHandicap[ii].setState((Phandicap & (1<<ii))!=0);     //~v1A0R~
        }                                                          //~v1A0I~
	}                                                              //~v1A0I~
//************************************************************     //~v1A0I~
    private void setStandardHandicap()                             //~v1A0I~
	{                                                              //~v1A0I~
        String s=RankDiff.getText();                               //~v1A0I~
        if (s.trim().equals(""))                                   //~v1A0I~
            rankdiff=0;                                            //~v1A0I~
        else                                                       //~v1A0I~
            rankdiff=Integer.parseInt(s);                          //~v1A0I~
	    int sz=Shandicap.length;                                 //~v1A0I~
        int hc;
	    if (rankdiff>=sz)                                          //~v1A0I~
        	hc=sz-1;                                               //~v1A0I~
        else                                                       //~v1A0I~
        	hc=rankdiff;                                           //~v1A0I~
        hc=Shandicap[hc];                                          //~v1A0I~
	    setHandicapState(hc);                                      //~v1A0I~
	}                                                              //~v1A0I~
//************************************************************     //~v1A0M~
    public static boolean isHandicapPiece(int Phandicap,int Prow,int Pcol)//~v1A0M~
	{                                                              //~v1A0M~
    	boolean rc=false;                                          //~v1A0M~
        if (Prow==0)                                               //~v1A0M~
        {                                                          //~v1A0M~
        	switch(Pcol)                                           //~v1A0M~
            {                                                      //~v1A0M~
            case 0://lance1 pos                                    //~v1A0M~
            	rc=(Phandicap & HC_LANCE1)!=0;                      //~v1A0M~
                break;                                             //~v1A0M~
            case 8://lance2 pos                                    //~v1A0M~
            	rc=(Phandicap & HC_LANCE2)!=0;                      //~v1A0M~
                break;                                             //~v1A0M~
            case 1://knight1 pos                                   //~v1A0M~
            	rc=(Phandicap & HC_KNIGHT1)!=0;                     //~v1A0M~
                break;                                             //~v1A0M~
            case 7://knight2 pos                                   //~v1A0M~
            	rc=(Phandicap & HC_KNIGHT2)!=0;                     //~v1A0M~
                break;                                             //~v1A0M~
            case 2://silver1 pos                                   //~v1A0M~
            	rc=(Phandicap & HC_SILVER1)!=0;                     //~v1A0M~
                break;                                             //~v1A0M~
            case 6://silver2 pos                                   //~v1A0M~
            	rc=(Phandicap & HC_SILVER2)!=0;                     //~v1A0M~
                break;                                             //~v1A0M~
            case 3://gold1 pos                                     //~v1A0M~
            	rc=(Phandicap & HC_GOLD1)!=0;                       //~v1A0M~
                break;                                             //~v1A0M~
            case 5://gold2 pos                                     //~v1A0M~
            	rc=(Phandicap & HC_GOLD2)!=0;                       //~v1A0M~
                break;                                             //~v1A0M~
            }                                                      //~v1A0M~
        }                                                          //~v1A0M~
		else                                                       //~v1A0M~
        if (Prow==1)                                               //~v1A0M~
        {                                                          //~v1A0M~
        	switch(Pcol)                                           //~v1A0M~
            {                                                      //~v1A0M~
//by Black View                                                    //+1A30I~
            case 1://bishop pos                                    //~v1A0M~
            	rc=(Phandicap & HC_BISHOP)!=0;                      //~v1A0M~
                break;                                             //~v1A0M~
            case 7://lance2 pos                                    //~v1A0M~
            	rc=(Phandicap & HC_ROOK)!=0;                        //~v1A0M~
                break;                                             //~v1A0M~
            }                                                      //~v1A0M~
        }                                                          //~v1A0M~
        return rc;                                                 //~v1A0M~
	}                                                              //~v1A0M~
}

