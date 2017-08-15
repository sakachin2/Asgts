//*CID://+1A32R~:                                   update#=   88; //~1A32R~
//****************************************************************************//~@@@1I~
//1A32 2013/04/17 chaneg to open FreeBoardQuestion at start button on FreeGoFrame//~1A32I~
//1A14 2013/03/10 FreeBoard Title                                  //~1A14I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//****************************************************************************//~@@@1I~
package jagoclient;                                                //~@@@2R~


public class FreeFrame extends LocalFrame                              //~@@@2R~//~1A10R~
{	                                                               //~v107I~
    public FreeGoFrame FGF;                                       //~@@@2I~//~1A10R~
	public String boardName="";                                       //~1A14R~//+1A32R~
    //****************************************************         //~1A32I~
    //*from MainFrame for FreeBoard                                //~1A32I~
    //****************************************************         //~1A32I~
    public FreeFrame (MainFrame Pmf,int Pboardtype)                //~1A32I~
	{                                                              //~1A32I~
  		super(Pmf);                                                //~1A32I~
        FreeBoardQuestion.startFGQ(this); //openGF by default option//~1A32R~
    }                                                              //~1A32I~
    //****************************************************         //~1A14R~
    //*from MainFrame for ReplayFrame                                            //~1A14I~//~1A32R~
    //****************************************************         //~1A14I~
    public FreeFrame (MainFrame Pmf)                               //~1A14I~
	{                                                              //~1A14I~
  		super(Pmf);                                                //~1A14I~
    }                                                              //~1A14I~
    //****************************************************         //~1A14I~
    public FreeFrame (FreeBoardQuestion Plgq)                     //~@@@2R~//~1A10R~
	{                                                              //~@@@2R~
  		super(Plgq);                                               //~1A10I~
    }                                                              //~1A10I~
    @Override                                                      //~1A10I~
    protected void openGF(int color,int sz,int totaltime,int extratime,int gameoptions,int handicap)                                        //~1A10I~
    {                                                              //~1A10I~
      if (LGQ!=null)	                                           //+1A32I~
		boardName=((FreeBoardQuestion)LGQ).boardName;                                   //~1A14I~
		FGF=new FreeGoFrame(this,                                //~@@@2R~//~1A10R~
				color,sz,                                          //~@@@2R~
				totaltime*60,gameoptions);//~@@@2R~//~v1A0R~       //~1A10R~
        FGF.start();                                               //~1A10R~
	}



	public void doAction (String o)
	{                                                              //~v108I~
        if (false);                                                //~v108I~
		else super.doAction(o);
	}

	public void doclose ()
    {                                                              //~v108I~
		super.doclose();
	}

	public boolean close ()
    {                                                              //~@@@2I~
    	return true;                                               //~@@@2I~
	}
	public boolean moveset (String c, int i, int j, int bt, int bm,
		int wt, int wm)
	{	if (Block) return false;
		return true;
	}


	public void set (int i, int j)
	{
	}

//********************************************************         //~@@@@I~
//*from PartnerGoFrame:doclose                                     //~@@@@I~
//********************************************************         //~@@@@I~
	public void boardclosed (FreeGoFrame pgf)		               //~1A10R~
	{                                                              //~@@@2R~
	}

}

