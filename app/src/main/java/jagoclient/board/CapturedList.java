//*CID://+1A40R~:                                   update#=  222; //+1A40R~
//****************************************************************************//~@@@1I~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //+1A40I~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0f 2013/03/05 check Chackmate for gameover                     //~1A0eI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~v1AeI~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//101f 2013/02/09 drawPiece NPE                                    //~v101I~
//*@@@1 20110430 add "resign"/"pass" to Finish_Game menu(Pass is duplicated to "Set")//~@@@1R~
//****************************************************************************//~@@@1I~
package jagoclient.board;

//import com.Asgts.awt.BorderLayout;                                //~@@@1R~//~v101R~
//import com.Asgts.awt.CardLayout;                                  //~@@@1R~//~v101R~
//import com.Asgts.awt.GridLayout;                                  //~@@@1R~//~v101R~
import android.graphics.Point;
import android.view.View;
import android.widget.TextView;

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.R;                                                //~v101R~
import com.Asgts.awt.Component;                                    //~v101R~
import com.Asgts.awt.Color;                                        //~v101R~
import com.Asgts.awt.TextComponent;
import com.Asgts.awt.TextField;                                    //~v101R~
import com.Asgts.awt.ToggleButton;                                 //~v1A0I~
import jagoclient.Dump;
import jagoclient.gui.DoActionListener;
import jagoclient.sound.JagoSound;                                 //~v1A0I~
import static jagoclient.board.Field.*;                            //~v101I~


public class CapturedList                                          //~v1A0R~
		implements DoActionListener                                //~v1A0I~
{                                                                  //~@@@2I~
    private int Col;                                               //~v1A0I~
    private ConnectedGoFrame CGF;                                  //~v1A0I~
    private	TextField tfU,tfL;                                     //~v1A0I~
    private	int[] idxActive={-1,-1};                               //~v1A0I~
    private	int idxActiveFreeBoard=-1;                             //~1A10I~
                                                                   //~v1A0I~
    private static int maxCaptureType=MAX_PIECE_TYPE_CAPTURE;      //~v1A0R~
	private int[][] capturedUL=new int[2][MAX_PIECE_TYPE-1];                           //~@@@@R~//~@@@2M~//~v101R~
	static final int[] Scapturedlistidtb={                          //~@@@@M~//~@@@2R~
    	R.id.Captured11,R.id.Captured12,R.id.Captured13,R.id.Captured14,R.id.Captured15,R.id.Captured16,R.id.Captured17,//~@@@@M~//~@@@2M~//~v101R~
    	R.id.Captured21,R.id.Captured22,R.id.Captured23,R.id.Captured24,R.id.Captured25,R.id.Captured26,R.id.Captured27,//~@@@@M~//~@@@2M~//~v101R~
    };                                                             //~@@@@M~//~@@@2M~
	static final int[] Scapturedlistctridtb={                      //~v1A0I~
    	R.id.CapturedCtr11,R.id.CapturedCtr12,R.id.CapturedCtr13,R.id.CapturedCtr14,R.id.CapturedCtr15,R.id.CapturedCtr16,R.id.CapturedCtr17,//~v1A0I~
    	R.id.CapturedCtr21,R.id.CapturedCtr22,R.id.CapturedCtr23,R.id.CapturedCtr24,R.id.CapturedCtr25,R.id.CapturedCtr26,R.id.CapturedCtr27,//~v1A0I~
    };                                                             //~v1A0I~
    public static final int[] Spiecectr={1/*Rook*/,1/*Bishop*/,2/*Gold*/,2/*Silver*/,2/*Knight*/,2/*Lance*/,AG.BOARDSIZE_SHOGI/*Pawn*/};//~1A10I~//~1A2dR~
//  private ImageView[] capturedButtonList=new ImageView[MAX_PIECE_TYPE_CAPTURE*2];//~v101I~//~v1A0R~
//    private ToggleButton[] capturedButtonList=new ToggleButton[MAX_PIECE_TYPE_CAPTURE*2];//~v1A0R~
    private CapturedButton[] capturedButtonList=new CapturedButton[MAX_PIECE_TYPE_CAPTURE*2];//~v1A0R~
	private TextView[] capturedlistTextViewtb=new TextView[MAX_PIECE_TYPE_CAPTURE*2];//~v1A0I~
    private int selectedPieceIndex,selectedColorIndex;             //~v1A0I~
    private ConnectedBoard B;                                      //~1A10R~
    private Position P;                                            //~1A10I~
    private boolean swVisibleCtr;                                          //~1A10I~
//**************************************************************** //~@@@1I~
	public CapturedList(ConnectedGoFrame Pcgf,int Psize,int Pcolor)//~v1A0R~
	{                                                              //~v1A0R~
        CGF=Pcgf;                                                  //~v1A0R~
        Col=Pcolor;                                                //~v1A0I~
        B=(ConnectedBoard)(Pcgf.B);                                              //~1A10I~
        P=B.P;                                                     //~1A10I~
        tfU=new TextField(CGF,0,R.id.CapturedU,"");               //~@@@2I~//~v1A0R~
        tfL=new TextField(CGF,0,R.id.CapturedL,"");               //~@@@2I~//~v1A0R~
		initCapturedList();                                        //~v1A0I~
        if (AG.screenDencityMdpi)                                  //+1A40R~
			;                                                      //+1A40R~
        else                                                       //+1A40R~
		if (AG.portrait && AG.smallButton)    //both portrait and landscale       //~@@@2R~//~v1A0R~
			setSmallHeight();                                      //~@@@2R~
		if (!CGF.swLocalGame)                                       //~v1A0I~
			disableList(1,true);//always enable lower              //~v1A0R~
	}
//********************************************************         //~@@@2I~
	public void setSmallHeight()                                   //~@@@2R~
    {                                                              //~@@@2I~
        String labelU,labelL;                                      //~@@@2I~
        Component c=new Component();                               //~@@@2I~//~v1A0R~
        int hw;                                                    //~@@@2I~
    //*******************                                          //~@@@2I~
        if (Dump.Y) Dump.println("setSmallCapturedListHeight");    //~@@@2I~
        hw=AG.smallImageHeight;                                //~@@@2I~
    	for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE*2;ii++)                     //~@@@2I~//~v1A0R~
        {                                                          //~@@@2I~
//            View v=capturedButtonList[ii].view;                  //~v1A0R~
//            c.setLayoutWidthHeight(v,hw,hw,false/*no setTextSize*/);//~@@@2R~//~v1A0R~
            capturedButtonList[ii].setLayoutWidthHeight(hw,hw,false/*no setTextSize*/);//~v1A0R~
        }                                                          //~@@@2I~
        View v=(View)CGF.findViewById(R.id.SetStone);                  //~v101I~
        c.setLayoutHeight(v,hw,false/*setTextSize*/);              //~@@@2R~
        c.setLayoutWidthHeight(tfL.textView,hw,hw,false/*setTextSize*/);//~@@@2R~
        c.setLayoutWidthHeight(tfU.textView,hw,hw,false/*setTextSize*/);//~@@@2R~
    }                                                              //~@@@2I~
//********************************************************         //~@@@@I~//~@@@2M~
	public void drawInitialCaptured()                             //~@@@@R~//~@@@2M~
    {                                                              //~@@@@I~//~@@@2M~                                                 //~@@@2I~
        String labelU,labelL;                                      //~@@@2I~
    //*******************                                          //~@@@@I~//~@@@2M~
        if (Dump.Y) Dump.println("drawInitialcaptured start");     //~@@@2I~
//        int hh=tfU.textView.getHeight();                                      //~@@@@I~//~@@@2R~//~1A10R~
//        if (hh==0)                                                 //~@@@2I~//~1A10R~
//            return; //cause exception by invalid bitmapsize        //~@@@2I~//~1A10R~
//        if (Col>0)  //black                                    //~@@@2R~//~v1A0R~//~1A10R~
//        {                                                          //~v1A0R~//~1A10R~
//            labelL=AG.BlackSign;labelU=AG.WhiteSign;               //~v1A0I~//~1A10R~
//        }                                                          //~v1A0I~//~1A10R~
//        else                                                   //~@@@2R~//~v1A0R~//~1A10R~
//        {                                                          //~v1A0R~//~1A10R~
//            labelL=AG.WhiteSign;labelU=AG.BlackSign;               //~v1A0I~//~1A10R~
//        }                                                          //~v1A0I~//~1A10R~
//        tfU.setText(labelU+":");                                   //~@@@2R~//~v1A0M~//~1A10R~
//        tfL.setText(labelL+":");                                   //~@@@2R~//~v1A0M~//~1A10R~
		setCapturedListHeader();                                   //~1A10I~
		displayCurrentColor(CGF.B.maincolor());                    //~v1A0R~
        if (Dump.Y) Dump.println("DrawInitialCaptured end");       //~@@@2I~
    }                                                              //~@@@@I~//~@@@2M~
//********************************************************         //~1A10I~
	public void setCapturedListHeader()                            //~1A10I~
    {                                                              //~1A10I~
        String labelU,labelL;                                      //~1A10I~
    //*******************                                          //~1A10I~
        if (Dump.Y) Dump.println("setcapturedListHeader");         //~1A10I~
        int hh=tfU.textView.getHeight();                           //~1A10I~
    /*                                                             //~1A10I~
        if (hh==0)                                                 //~1A10I~
        	return;	//cause exception by invalid bitmapsize        //~1A10I~
    */                                                             //~1A10I~
        if (Col>0)  //black                                        //~1A10I~
        {                                                          //~1A10I~
			labelL=AG.BlackSign;labelU=AG.WhiteSign;               //~1A10I~
		}                                                          //~1A10I~
        else                                                       //~1A10I~
        {                                                          //~1A10I~
			labelL=AG.WhiteSign;labelU=AG.BlackSign;               //~1A10I~
		}                                                          //~1A10I~
        tfU.setText(labelU+":");                                   //~1A10I~
        tfL.setText(labelL+":");                                   //~1A10I~
    }                                                              //~1A10I~
//********************************************************         //~v1A0I~
    private void drawCapturedPiece(int Pidx,int Pvisible)                   //~v1A0I~
    {                                                              //~v1A0I~
//        ToggleButton v=capturedButtonList[Pidx];                 //~v1A0R~
        CapturedButton v=capturedButtonList[Pidx];                 //~v1A0R~
//        v.setVisibility(Pvisible);//by component                 //~v1A0R~
//        v.setDrawable(B.Sidtbl_shogi[0][0]);                     //~v1A0R~
        v.draw(Pvisible);                                          //~v1A0I~
        if (Dump.Y) Dump.println("drawCaptuedPiece visible="+Pvisible);//~v1A0I~
    }                                                              //~v1A0I~
//********************************************************         //~@@@@I~//~@@@2M~
//  public void updateCapturedList(int Pcolor/*captured piece color*/,int Ppiece)                             //~@@@2I~//~@@@@I~//~@@@2R~//~v1A0R~//~1A0eR~
    public void updateCapturedList(int Pcolor/*captured piece color*/,int Ppiece,int Pcount)//~1A0eR~
    {                                                              //~@@@2I~//~@@@@I~//~@@@2M~
    	int imageidx,listidx,piece,pieceidx;                                      //~@@@@I~//~@@@2M~//~v1A0R~
        Color bgcolor=Color.black;                                 //~@@@2R~
    //**********************	                                                           //~@@@2I~//~@@@@I~//~@@@2M~
    	if (Dump.Y) Dump.println("updateCapturedList color="+Pcolor+",piece="+Ppiece+",count="+Pcount);//~1A0eR~
        if (Pcount<0)	//partner dropped                          //~1A0eR~
        {                                                          //~1A0eR~
        	partnerSelectedPiece(Pcolor,Ppiece);                   //~1A0eR~
            return;                                                //~1A0eR~
        }                                                          //~1A0eR~
        piece=nonPromoted(Ppiece);                                //~v1A0I~
        if (Col!=Pcolor)   //oponent captured                                        //~@@@@I~//~@@@2R~//~v101R~
        {                                                          //~v1A0I~
        	listidx=1;	//captured list lower                      //~@@@@I~//~@@@2M~
        }                                                          //~v1A0I~
        else                                                       //~@@@@I~//~@@@2M~
        {                                                          //~v1A0I~
        	listidx=0;	//captured list upper                      //~@@@@I~//~@@@2M~
        }                                                          //~v1A0I~
        pieceidx=MAX_PIECE_TYPE_CAPTURE-piece;                     //~v1A0R~
        updateCapturedListTextView(listidx,pieceidx,1/*add*/);     //~v1A0R~
        drawCapturedPiece(listidx*MAX_PIECE_TYPE_CAPTURE+pieceidx,View.VISIBLE);//~v1A0I~
                                                                   //~v1A0I~
    }                                                              //~@@@2I~//~@@@@I~//~@@@2M~
//********************************************************         //~1A2dI~
//*for ReplayBoard Undo                                            //~1A2dI~
//********************************************************         //~1A2dI~
    public void updateCapturedList_Undo(int Pcolor/*captured piece color*/,int Ppiece,int Pcount)//~1A2dR~
    {                                                              //~1A2dI~
    	int imageidx,listidx,piece,pieceidx;                       //~1A2dI~
        Color bgcolor=Color.black;                                 //~1A2dI~
    //**********************                                       //~1A2dI~
    	if (Dump.Y) Dump.println("updateCapturedList_Undo color="+Pcolor+",piece="+Ppiece+",count="+Pcount);//~1A2dI~
//        if (Pcount<0)   //partner dropped                        //~1A2dI~
//        {                                                        //~1A2dI~
//            partnerSelectedPiece(Pcolor,Ppiece);                 //~1A2dI~
//            return;                                              //~1A2dI~
//        }                                                        //~1A2dI~
        piece=nonPromoted(Ppiece);                                 //~1A2dI~
        if (Col!=Pcolor)   //oponent captured                      //~1A2dI~
        {                                                          //~1A2dI~
        	listidx=1;	//captured list lower                      //~1A2dI~
        }                                                          //~1A2dI~
        else                                                       //~1A2dI~
        {                                                          //~1A2dI~
        	listidx=0;	//captured list upper                      //~1A2dI~
        }                                                          //~1A2dI~
        pieceidx=MAX_PIECE_TYPE_CAPTURE-piece;                     //~1A2dI~
        int remaining=updateCapturedListTextView(listidx,pieceidx,Pcount/*add*/);//~1A2dR~
        drawCapturedPiece(listidx*MAX_PIECE_TYPE_CAPTURE+pieceidx,(remaining>0?View.VISIBLE:View.INVISIBLE));//~1A2dR~
    }                                                              //~1A2dI~
//********************************************************         //~v1A0I~
    public int updateCapturedListTextView(int Pcolidx,int Ppieceidx,int Padd)//~v1A0R~
    {                                                              //~1A10I~
    //**********************                                       //~v1A0I~
        capturedUL[Pcolidx][Ppieceidx]+=Padd;                                     //~@@@@I~//~@@@2M~//~v101R~//~v1A0I~
        int ctr=capturedUL[Pcolidx][Ppieceidx];                      //~v1A0I~
		TextView tv=capturedlistTextViewtb[Pcolidx*MAX_PIECE_TYPE_CAPTURE+Ppieceidx];    //~v1A0I~
        TextComponent tc=new TextComponent(tv);                        //~v1A0I~
//      if (ctr<=1)                                                //~v1A0I~//~1A10R~
        if (ctr<=1 && !swVisibleCtr)                               //~1A10I~
        {                                                          //~v1A0I~
            tc.setVisibility(tv,View.INVISIBLE);                   //~v1A0R~
        }                                                          //~v1A0I~
        else                                                       //~v1A0I~
        {                                                          //~v1A0I~
            tc.setVisibility(tv,View.VISIBLE);                     //~v1A0I~
            tc.setText(Integer.toString(ctr));                      //~v1A0I~
        }                                                          //~v1A0I~
        if (Dump.Y) Dump.println("updateCapturedListTextView colidx="+Pcolidx+",pieceidx="+Ppieceidx+",add="+Padd+",ctr="+ctr);//~v1A0I~
        return ctr;                                                //~v1A0I~
  	}      	                                                       //~v1A0R~
//********************************************************         //~v1A0I~
	public void initCapturedList()                                 //~v1A0I~
    {                                                              //~v1A0I~
        String labelU,labelL;                                      //~v1A0I~
        Component c=new Component();                               //~v1A0I~
        int hw;                                                    //~v1A0I~
    //*******************                                          //~v1A0I~
        if (Dump.Y) Dump.println("initCapturedListHeight");        //~v1A0I~
    	for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE*2;ii++)            //~v1A0I~
        {                                                          //~v1A0I~
            capturedButtonList[ii]=new CapturedButton(CGF,Scapturedlistidtb[ii],this,ii);//~v1A0R~
            TextView v=(TextView)CGF.findViewById(Scapturedlistctridtb[ii]); //by container//~v1A0R~
            capturedlistTextViewtb[ii]=v;                          //~v1A0R~
        }                                                          //~v1A0I~
    }                                                              //~v1A0I~
//********************************************************         //~v1A0I~
	private void disableList(int Pcolidx,boolean Pinit)            //~v1A0R~
    {                                                              //~v1A0I~
        String labelU,labelL;                                      //~v1A0I~
        Component c=new Component();                               //~v1A0I~
        int hw;                                                    //~v1A0I~
    //*******************                                          //~v1A0I~
        if (Dump.Y) Dump.println("disableList colidx="+Pcolidx);   //~v1A0I~
        int jj=Pcolidx*MAX_PIECE_TYPE_CAPTURE;		//enable       //~v1A0R~
        int kk=(Pcolidx==0?1:0)*MAX_PIECE_TYPE_CAPTURE; //disable  //~v1A0R~
    	for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++,jj++,kk++)    //~v1A0I~
        {                                                          //~v1A0I~
//            if (CGF.swLocalGame || jj>MAX_PIECE_TYPE_CAPTURE || Pinit)//~v1A0R~
//                capturedButtonList[jj].setEnabled(true);         //~v1A0R~
//            if (CGF.swLocalGame || kk>MAX_PIECE_TYPE_CAPTURE || Pinit)//~v1A0R~
//                capturedButtonList[kk].setEnabled(false);        //~v1A0R~
              capturedButtonList[jj].setEnabled(true);             //~v1A0I~
              capturedButtonList[kk].setEnabled(false);            //~v1A0I~
        }                                                          //~v1A0I~
    }                                                              //~v1A0I~
//********************************************************         //~1A10I~
	public void enableList(boolean Pforce)                         //~1A10R~
    {                                                              //~1A10I~
    	int ctr;                                                   //~1A10I~
    //*******************                                          //~1A10I~
        if (Dump.Y) Dump.println("enableList");                    //~1A10I~
    	for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A10I~
        {                                                          //~1A10I~
			enableList(0,ii,Pforce);                               //~1A10R~
			enableList(1,ii,Pforce);                               //~1A10R~
        }                                                          //~1A10I~
    }                                                              //~1A10I~
//********************************************************         //~1A10I~
	private void enableList(int Pcolidx,int Ppieceidx,boolean Pforce)//~1A10R~
    {                                                              //~1A10I~
    	int ctr;                                                   //~1A10I~
    //*******************                                          //~1A10I~
        ctr=capturedUL[Pcolidx][Ppieceidx];                        //~1A10I~
        if (Dump.Y) Dump.println("enableList colidx="+Pcolidx+",pieceidx="+Ppieceidx+",ctr="+ctr);//~1A10I~
        capturedButtonList[MAX_PIECE_TYPE_CAPTURE*Pcolidx+Ppieceidx].setEnabled(Pforce||ctr>0);//~1A10R~
    }                                                              //~1A10I~
    //************************************                         //~v1A0I~
	@Override
	public void doAction (String o)                                //~v1A0I~
	{                                                              //~v1A0I~
    	int btnseq=Integer.parseInt(o);                            //~v1A0I~
        if (B.isIdleFreeBoard())                                   //~1A10I~
        {                                                          //~1A10I~
        	pieceActionFreeBoard(btnseq);                          //~1A10I~
            return;                                                //~1A10I~
        }                                                          //~1A10I~
        CapturedButton btn=capturedButtonList[btnseq];             //~v1A0I~
        boolean staton=btn.isChecked();                            //~v1A0I~
        int color=(btnseq>=maxCaptureType)?1:0;                    //~v1A0I~
        if (Dump.Y) Dump.println("CapturedList click old="+idxActive[color]+",new="+btnseq+",stat="+staton);//~v1A0I~
        if (staton)                                                //~v1A0I~
        {                                                          //~v1A0I~
        	int old=idxActive[color];                              //~v1A0I~
            idxActive[color]=btnseq;                               //~v1A0I~
            if (old>=0)                                            //~v1A0I~
			    capturedButtonList[old].setChecked(false);         //~v1A0I~
            ((ConnectedBoard)(CGF.B)).resetSelectedPiece();                            //~v1A0I~
    		JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~v1A0I~
        }                                                          //~v1A0I~
        else                                                       //~v1A0I~
        	idxActive[color]=-1;
	}//~v1A0I~
    //***********************************************************************//~1A10M~
    //*selection is only through lower/upper list                  //~1A10M~
    //***********************************************************************//~1A10M~
	public void pieceActionFreeBoard(int Pbtnseq)                  //~1A10M~
	{                                                              //~1A10M~
        CapturedButton btn=capturedButtonList[Pbtnseq];            //~1A10M~
        boolean staton=btn.isChecked();                            //~1A10M~
        if (Dump.Y) Dump.println("CapturedList:pieceActionFreeBoard click old="+idxActiveFreeBoard+",new="+Pbtnseq+",stat="+staton);//~1A10M~
        int old=idxActiveFreeBoard;                                //~1A10M~
        if (staton)                                                //~1A10M~
        {                                                          //~1A10M~
            idxActiveFreeBoard=Pbtnseq;                            //~1A10M~
            if (old>=0)                                            //~1A10M~
				capturedButtonList[old].setChecked(false);         //~1A10M~
            ((ConnectedBoard)(CGF.B)).resetSelectedPiece();	//swSelected=fales//~1A10M~
        	if (Dump.Y) Dump.println("CapturedList:active changed");//~1A10M~
        }                                                          //~1A10M~
        else                                                       //~1A10M~
        {                                                          //~1A10M~
        	if (Pbtnseq==old)	//double touch	                   //~1A10M~
            {                                                      //~1A10M~
            	countupCaptured(Pbtnseq);                          //~1A10M~
				capturedButtonList[old].setChecked(true);          //~1A10M~
	        	if (Dump.Y) Dump.println("CapturedList:count up"); //~1A10M~
            }                                                      //~1A10M~
        }                                                          //~1A10M~
    	JagoSound.play("pieceup",false/*not change to beep when beeponly option is on*/);//~1A10M~
	}                                                              //~1A10M~
    //************************************                         //~v1A0I~
	public void reset(int Pcolor)                                  //~v1A0I~
	{                                                              //~v1A0I~
        if (B.isIdleFreeBoard())                                   //~1A10I~
        {                                                          //~1A10I~
        	resetFreeBoard();                                      //~1A10I~
            return;                                                //~1A10I~
        }                                                          //~1A10I~
    	int idxcolor=(Pcolor==Col)?1:0;                            //~v1A0R~
        if (Dump.Y) Dump.println("CapturedList reset color="+Pcolor+",old="+idxActive[idxcolor]);//~v1A0R~
        if (idxActive[idxcolor]>=0)                                //~v1A0I~
        {                                                          //~v1A0I~
		    capturedButtonList[idxActive[idxcolor]].setChecked(false);//~v1A0I~
        	idxActive[idxcolor]=-1;                                //~v1A0I~
        }                                                          //~v1A0I~
    }                                                              //~v1A0I~
    //************************************                         //~1A10I~
	public void resetFreeBoard()                                   //~1A10I~
	{                                                              //~1A10I~
        if (Dump.Y) Dump.println("CapturedList resetFreeBoard idxActiveFreeBoard="+idxActiveFreeBoard);//~1A10I~
        if (idxActiveFreeBoard>=0)                                 //~1A10I~
        {                                                          //~1A10I~
		    capturedButtonList[idxActiveFreeBoard].setChecked(false);//~1A10I~
        	idxActiveFreeBoard=-1;                                 //~1A10I~
        }                                                          //~1A10I~
    }                                                              //~1A10I~
    //************************************                         //~v1A0I~
	public int getSelectedPiece(int Pcolor)                        //~v1A0R~
	{                                                              //~v1A0I~
    	int rc=-1;                                                 //~v1A0I~
    //*****************************                                //~v1A0I~
		if ((rc=chkSelected(Pcolor))>=0)                           //~v1A0R~
        {                                                          //~v1A0I~
			int remaining=updateCapturedListTextView(selectedColorIndex,selectedPieceIndex,-1/*subtract*/);//~v1A0R~
            if (remaining<=0)                                      //~v1A0I~
        		drawCapturedPiece(selectedColorIndex*MAX_PIECE_TYPE_CAPTURE+selectedPieceIndex,View.INVISIBLE);//~v1A0I~
        }                                                          //~v1A0I~
        if (Dump.Y) Dump.println("CapturedList getSelected piecetype="+rc);//~v1A0R~
        return rc;                                                 //~v1A0R~
    }                                                              //~v1A0I~
    //************************************                         //~1A10I~
    //*get selected piece from tray                                //~1A10I~
	//*return Point(color,piece)                                   //~1A10I~
    //************************************                         //~1A10I~
	public Point getSelectedPieceFreeBoard()                       //~1A10R~
	{                                                              //~1A10I~
        Point p=null;                                              //~1A10I~
    //*****************************                                //~1A10I~
        if (idxActiveFreeBoard>=0)                                 //~1A10I~
        {                                                          //~1A10I~
	        int colidx=idxActiveFreeBoard/MAX_PIECE_TYPE_CAPTURE;             //~1A10I~
            int col=colidx==1?Col:-Col;                            //~1A10I~
    	    int pieceidx=idxActiveFreeBoard%MAX_PIECE_TYPE_CAPTURE;           //~1A10I~
            int piece=MAX_PIECE_TYPE_CAPTURE-pieceidx;             //~1A10I~
            p=new Point(col,piece);                                //~1A10I~
        }                                                          //~1A10I~
    	return p;                                                  //~1A10I~
    }                                                              //~1A10I~
    //************************************                         //~1A10I~
    //*count down selected piece from tray                         //~1A10I~
    //************************************                         //~1A10I~
	public boolean getSelectedPieceFreeBoard(boolean Pcountdown)   //~1A10I~
	{                                                              //~1A10I~
    	boolean rc=true;                                           //~1A10I~
    //**********************************                           //~1A10I~
        if (idxActiveFreeBoard<0)                                  //~1A10I~
        	return false;                                          //~1A10I~
	    int colidx=idxActiveFreeBoard/MAX_PIECE_TYPE_CAPTURE;      //~1A10I~
    	int pieceidx=idxActiveFreeBoard%MAX_PIECE_TYPE_CAPTURE;    //~1A10I~
        int ctr=capturedUL[colidx][pieceidx];                      //~1A10I~
        if (ctr>0)                                                 //~1A10I~
            updateCapturedListTextView(colidx,pieceidx,-1/*ctr*/);   //countdown//~1A10I~
        else                                                       //~1A10I~
        	rc=false;                                              //~1A10I~
        return rc;                                                 //~1A10I~
    }                                                              //~1A10I~
    //************************************                         //~1A0eR~
	public void partnerSelectedPiece(int Pcolor,int Ppiece)        //~1A0eR~
	{                                                              //~1A0eR~
    //*****************************                                //~1A0eR~
		int remaining=updateCapturedListTextView(0/*selectedColorIndex:upper*/,maxCaptureType-Ppiece,-1/*subtract*/);//~1A0eR~
        if (remaining<=0)                                          //~1A0eR~
        	drawCapturedPiece(maxCaptureType-Ppiece,View.INVISIBLE);//~1A0eR~
        if (Dump.Y) Dump.println("CapturedList getSelected piece="+Ppiece+",color="+Pcolor);//~1A0eR~
    }                                                              //~1A0eR~
    //************************************                         //~v1A0I~
	public int chkSelected(int Pcolor)                             //~v1A0R~
	{                                                              //~v1A0I~
    	int rc=-1;                                             //~v1A0R~
    //*****************************                                //~v1A0I~
    	int idxcolor=(Pcolor==Col)?1:0;                            //~v1A0I~
        int idxpiece=idxActive[idxcolor];                          //~v1A0I~
        if (idxpiece>=0)                                           //~v1A0I~
        {                                                          //~v1A0I~
        	if (idxpiece>=maxCaptureType)                          //~v1A0R~
        		idxpiece-=maxCaptureType;                          //~v1A0I~
        	int ctr=capturedUL[idxcolor][idxpiece];                //~v1A0I~
            if (ctr>0)                                             //~v1A0I~
            {                                                      //~v1A0I~
                selectedColorIndex=idxcolor;                       //~v1A0I~
                selectedPieceIndex=idxpiece;                        //~v1A0I~
	        	rc=maxCaptureType-selectedPieceIndex;	//piecetype//~v1A0I~
        	}                                                      //~v1A0I~
        }                                                          //~v1A0I~
        if (Dump.Y) Dump.println("CapturedList isSelected rc="+rc+",color="+Pcolor+",active="+idxActive[idxcolor]);//~v1A0I~
        return rc;                                                 //~v1A0I~
    }                                                              //~v1A0I~
    //************************************                         //~v1A0I~
	public void displayCurrentColor(int Pcolor/*next color*/)      //~v1A0R~
	{                                                              //~v1A0I~
    	int rc=-1;                                                 //~v1A0I~
        int colidx;                                                //~v1A0I~
    //*****************************                                //~v1A0I~
        if (Dump.Y) Dump.println("CapturedList displayCurrentColor color="+Pcolor);//~v1A0I~
        if (B.isIdleFreeBoard())                                     //~1A10I~
        	return;                                                //~1A10I~
    	TextField tf1,tf2;                                         //~v1A0I~
        if (Pcolor==Col)                                           //~v1A0I~
        {                                                          //~v1A0I~
        	colidx=1;       //lower list                           //~v1A0I~
			tf1=tfL;tf2=tfU;                                       //~v1A0R~
        }                                                          //~v1A0I~
        else                                                       //~v1A0I~
        {                                                          //~v1A0I~
        	colidx=0;       //upper list                           //~v1A0I~
			tf1=tfU;tf2=tfL;                                       //~v1A0R~
        }                                                          //~v1A0I~
        tf1.setBackground(AG.cursorColor);                         //~v1A0I~
        tf2.setBackground(Color.white);                            //~v1A0R~
      if (CGF.swLocalGame)                                         //~v1A0I~
        disableList(colidx,false/*not init*/);                     //~v1A0R~
    }                                                              //~v1A0I~
    //************************************                         //~1A0fI~
	public int getCapturedCount(int Pcolor,int [] Poutlist)        //~1A0fR~
	{                                                              //~1A0fI~
    	int rc=0;                                                  //~1A0fI~
        int colidx;                                                //~1A0fI~
    //*****************************                                //~1A0fI~
        if (Dump.Y) Dump.println("CapturedList getCapturedCount color="+Pcolor);//~1A0fI~
        if (Pcolor==Col)                                           //~1A0fI~
        	colidx=1;       //lower list                           //~1A0fI~
        else                                                       //~1A0fI~
        	colidx=0;       //upper list                           //~1A0fI~
                                                                   //~1A0fI~
        for (int ii=0;ii<MAX_PIECE_TYPE-1;ii++)                    //~1A0fI~
        	rc+=capturedUL[colidx][ii];                            //~1A0fI~
        System.arraycopy(capturedUL[colidx],0,Poutlist,0,MAX_PIECE_TYPE-1);//~1A0fI~
        if (Dump.Y) Dump.println("CapturedList getCapturedCount color="+Pcolor+",ctr="+rc);//~1A0fI~
        return rc;                                                 //~1A0fI~
    }                                                              //~1A0fI~
    //************************************                         //~1A10I~
	public void getCapturedList(int [][] Poutlist)                 //~1A10I~
	{                                                              //~1A10I~
    //*****************************                                //~1A10I~
        if (Dump.Y) Dump.println("CapturedList getCapturedList");  //~1A10I~
        System.arraycopy(capturedUL[0],0,Poutlist[0],0,MAX_PIECE_TYPE_CAPTURE);//~1A10I~
        System.arraycopy(capturedUL[1],0,Poutlist[1],0,MAX_PIECE_TYPE_CAPTURE);//~1A10I~
    }                                                              //~1A10I~
    //***********************************************************************//~1A10I~
    //*restore tray                                                //~1A10I~
    //***********************************************************************//~1A10I~
	public void restoreTray(int [][] Ptray)                        //~1A10I~
	{                                                              //~1A10I~
        int piece,pieceidx;                                        //~1A10I~
    //*****************************                                //~1A10I~
        if (Dump.Y) Dump.println("CapturedList:restoreTray");      //~1A10I~
        System.arraycopy(Ptray[0],0,capturedUL[0],0,MAX_PIECE_TYPE_CAPTURE);//~1A10I~
        System.arraycopy(Ptray[1],0,capturedUL[1],0,MAX_PIECE_TYPE_CAPTURE);//~1A10R~
    }                                                              //~1A10I~
//    //***********************************************************************//~1A10R~
//    //*put all piece for FreeBoard                               //~1A10R~
//    //***********************************************************************//~1A10R~
//    public void putAllPieces()                                   //~1A10R~
//    {                                                            //~1A10R~
//        int colidx;                                              //~1A10R~
//    //*****************************                              //~1A10R~
//        if (Dump.Y) Dump.println("CapturedList:putAllPiece);     //~1A10R~
//        for (int ii=0;ii<MAX_PIECE_TYPE-1;ii++)                  //~1A10R~
//        {                                                        //~1A10R~
//            capturedUL[0][ii]=ctr;                               //~1A10R~
//            capturedUL[1][ii]=ctr;                               //~1A10R~
//            updateCapturedListTextView(0,ii/*pieceidx*/,0/*add*/);//~1A10R~
//            updateCapturedListTextView(1,ii/*pieceidx*/,0/*add*/);//~1A10R~
//            drawCapturedPiece(ii,View.VISIBLE);         //upper  //~1A10R~
//            drawCapturedPiece(MAX_PIECE_TYPE_CAPTURE+ii,View.VISIBLE);//lower//~1A10R~
//        }                                                        //~1A10R~
//        clearBoard();                                            //~1A10I~
//    }                                                            //~1A10R~
//***********************************************************************//~1A10I~
//*FreeBoard                                                       //~1A10I~
//***********************************************************************//~1A10I~
    //***********************************************************************//~1A10I~
    //*set capturedlist visble/enable                              //~1A10I~
    //***********************************************************************//~1A10I~
	public void initFreeBoardTray()                                //~1A10R~
	{                                                              //~1A10R~
        if (Dump.Y) Dump.println("CapturedList:initFreeBoardTray");//~1A10I~
		setCapturedListHeader();                                   //~1A10I~
		setTrayVisible(true/*visible evenif ctr==0*/);              //~1A10I~
		enableList(true/*force evenif ctr==0*/);	//enable captured piece//~1A10R~
    }                                                              //~1A10I~
    //***********************************************************************//~1A10I~
    //*put all piece on board tray                                 //~1A10I~
    //***********************************************************************//~1A10I~
	public void setTrayVisible(boolean Pvisible)                   //~1A10I~
	{                                                              //~1A10I~
    	int stat;                                                  //~1A10I~
    //*****************************                                //~1A10I~
        if (Dump.Y) Dump.println("CapturedList:setTrayVisible visible="+Pvisible);//~1A10I~
        swVisibleCtr=Pvisible;                                     //~1A10I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE*2;ii++)            //~1A10R~
        {                                                          //~1A10I~
        	int colidx=ii/MAX_PIECE_TYPE_CAPTURE;                  //~1A10I~
        	int pieceidx=ii%MAX_PIECE_TYPE_CAPTURE;                //~1A10I~
        	int ctr=capturedUL[colidx][pieceidx];                  //~1A10R~
            updateCapturedListTextView(colidx,pieceidx,0/*ctr*/);  //~1A10I~
            if (!Pvisible && ctr==0)                               //~1A10I~
            	stat=View.INVISIBLE;                               //~1A10I~
            else                                                   //~1A10I~
            	stat=View.VISIBLE;                                 //~1A10I~
            drawCapturedPiece(ii,stat);                            //~1A10I~
        }                                                          //~1A10I~
    }                                                              //~1A10I~
//    //***********************************************************************//~1A10R~
//    //*put all piece on board tray                               //~1A10R~
//    //***********************************************************************//~1A10R~
//    public void clearToTray(int Pcolor/*Tray color*/)            //~1A10R~
//    {                                                            //~1A10R~
//        int piece,color;                                         //~1A10R~
//    //*****************************                              //~1A10R~
//        if (Dump.Y) Dump.println("CapturedList:clearToTray");    //~1A10R~
//        for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)                //~1A10R~
//        {                                                        //~1A10R~
//            for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)            //~1A10R~
//            {                                                    //~1A10R~
//                piece=P.piece(ii,jj);                            //~1A10R~
//                if (Pcolor==0)                                   //~1A10R~
//                    color=P.color(ii,jj);                        //~1A10R~
//                else                                             //~1A10R~
//                    color=-Pcolor;  //to opposit tray            //~1A10R~
//                if (piece!=0 && piece!=PIECE_KING)               //~1A10R~
//                {                                                //~1A10R~
//                    P.piece(ii,jj,0,0);     //clear              //~1A10R~
//                    updateCapturedList(color,piece,1/*ctr*/);    //~1A10R~
//                }                                                //~1A10R~
//            }                                                    //~1A10R~
//        }                                                        //~1A10R~
//        if (Pcolor!=0)                                           //~1A10R~
//            shiftTray(Pcolor);                                   //~1A10R~
//    }                                                            //~1A10R~
    //***********************************************************************//~1A10I~
    //*all remaining piece to white tray                           //~1A10R~
    //***********************************************************************//~1A10I~
	public void clearToWhiteTray()                                 //~1A10R~
	{                                                              //~1A10I~
        int piece,pieceidx;                       //~1A10R~
    //*****************************                                //~1A10I~
        if (Dump.Y) Dump.println("CapturedList:clearToWhiteTray"); //~1A10R~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A10I~
        {                                                          //~1A10I~
        	capturedUL[0][ii]=Spiecectr[ii]*2-capturedUL[1][ii];   //~1A10R~
        }                                                          //~1A10I~
        for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)                  //~1A10I~
        {                                                          //~1A10I~
            for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)              //~1A10I~
            {                                                      //~1A10I~
                piece=P.piece(ii,jj);                              //~1A10I~
                if (piece!=0 && piece!=PIECE_KING)                 //~1A10I~
                {                                                  //~1A10I~
                    piece=Field.nonPromoted(piece);                 //~1A10I~
                    pieceidx=MAX_PIECE_TYPE_CAPTURE-piece;         //~1A10I~
		        	capturedUL[0][pieceidx]--;                     //~1A10I~
                }                                                  //~1A10I~
            }                                                      //~1A10I~
        }                                                          //~1A10I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A10I~
        {                                                          //~1A10I~
		    if (capturedUL[0][ii]<0)                               //~1A10I~
				errPieceCtr(MAX_PIECE_TYPE_CAPTURE-ii);            //~1A10I~
            updateCapturedListTextView(0,ii/*pieceidx*/,0/*add*/); //update view//~1A10I~
            updateCapturedListTextView(1,ii/*pieceidx*/,0/*add*/); //~1A10I~
        }                                                          //~1A10I~
    }                                                              //~1A10I~
    //***********************************************************************//~1A10I~
    //*all remaining piece to white tray                           //~1A10I~
    //***********************************************************************//~1A10I~
	public boolean chkTotalPieceCtr()                              //~1A10R~
	{                                                              //~1A10I~
        int piece,pieceidx;                                        //~1A10I~
        boolean rc=true;                                           //~1A10R~
        int [] wk=new int[MAX_PIECE_TYPE_CAPTURE];                 //~1A10I~
    //*****************************                                //~1A10I~
        if (Dump.Y) Dump.println("CapturedList:chkTotalPieceCtr"); //~1A10I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A10I~
        {                                                          //~1A10I~
        	wk[ii]=Spiecectr[ii]*2-capturedUL[0][ii]-capturedUL[1][ii];//~1A10I~
        }                                                          //~1A10I~
        for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)                  //~1A10I~
        {                                                          //~1A10I~
            for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)              //~1A10I~
            {                                                      //~1A10I~
                piece=P.piece(ii,jj);                              //~1A10I~
                if (piece!=0 && piece!=PIECE_KING)                 //~1A10I~
                {                                                  //~1A10I~
                    piece=Field.nonPromoted(piece);                //~1A10I~
                    pieceidx=MAX_PIECE_TYPE_CAPTURE-piece;         //~1A10I~
		        	wk[pieceidx]--;                                //~1A10I~
                }                                                  //~1A10I~
            }                                                      //~1A10I~
        }                                                          //~1A10I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A10I~
        {                                                          //~1A10I~
		    if (wk[ii]<0)                                       //~1A10I~
            {                                                      //~1A10I~
            	rc=false;                                          //~1A10I~
				errPieceCtr(MAX_PIECE_TYPE_CAPTURE-ii);            //~1A10I~
            }                                                      //~1A10I~
        }                                                          //~1A10I~
        return rc;                                                 //~1A10I~
    }                                                              //~1A10I~
    //***********************************************************************//~1A10I~
    //*clear Tray                                                  //~1A10I~
    //***********************************************************************//~1A10I~
	public void clearTray(int Pcolor)                              //~1A10I~
	{                                                              //~1A10I~
        int colidx;                                                //~1A10I~
    //*****************************                                //~1A10I~
        if (Dump.Y) Dump.println("CapturedList:clearTray color="+Pcolor);//~1A10I~
        colidx=(Pcolor==Col)?1:0;                                  //~1A10I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~1A10R~
        {                                                          //~1A10I~
        	int ctr=capturedUL[colidx][ii];                            //~1A10I~
            if (ctr!=0)                                            //~1A10R~
            	updateCapturedListTextView(colidx,ii/*pieceidx*/,-ctr/*add*/);//~1A10I~
        }                                                          //~1A10I~
    }                                                              //~1A10I~
    //***********************************************************************//~1A10I~
	public void countupCaptured(int Pbtnseq)                       //~1A10I~
	{                                                              //~1A10I~
        int colidx=Pbtnseq/MAX_PIECE_TYPE_CAPTURE;                 //~1A10I~
        int pieceidx=Pbtnseq%MAX_PIECE_TYPE_CAPTURE;               //~1A10I~
        int ctr=capturedUL[colidx][pieceidx];                      //~1A10I~
    	if (ctr>=Spiecectr[pieceidx]*2)                            //~1A10I~
        	ctr=-ctr;	//to 0                                     //~1A10I~
        else                                                       //~1A10I~
        	ctr=1;		//1up                                      //~1A10I~
        if (Dump.Y) Dump.println("CapturedList:countupcaptured btnseq="+Pbtnseq+",ctr="+ctr);//~1A10I~
    	updateCapturedListTextView(colidx,pieceidx,ctr);           //~1A10I~
	}                                                              //~1A10I~
//**************************************************************** //~1A10I~
	protected void errPieceCtr(int Ppiece)                         //~1A10R~
    {                                                              //~1A10I~
    	String p=CGF.pieceName(Ppiece);                            //~1A10I~
    	String s=AG.resource.getString(R.string.ErrPieceCtr,p);    //~1A10I~
        CGF.setLabel(s,false/*no append*/);                        //~1A10R~
    }                                                              //~1A10I~
	//**************************************************************
	@Override
	public void itemAction(String o, boolean flag)
	{		
	}
}
