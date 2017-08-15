//*CID://+v1A0R~:                                   update#=  156; //~v1A0R~
//****************************************************************************//~@@@1I~
//****************************************************************************//~@@@1I~
package jagoclient.board;

import com.Asgts.awt.Component;                                    //~v101R~
import com.Asgts.awt.ToggleButton;                                 //~v1A0I~
import jagoclient.Dump;
import jagoclient.gui.DoActionListener;


public class CapturedButton extends Component                      //~v1A0R~
{                                                                  //~@@@2I~
    ToggleButton aButton;                                          //~v1A0R~
//**************************************************************** //~@@@1I~
	public CapturedButton(ConnectedGoFrame Pcgf,int Presid,DoActionListener PcapturedList,int Pid)//~v1A0R~
	{                                                              //~v1A0R~
    	aButton=new ToggleButton(Pcgf,Presid);                        //~v1A0I~
        aButton.addListener(PcapturedList,Integer.toString(Pid));   //~v1A0I~
	}
////********************************************************         //~@@@@I~//~@@@2M~//~v1A0R~
//    private void drawPiece(int Pididx,Image Pimage)                //~@@@@R~//~@@@2M~//~v1A0R~
//    {                                                              //~@@@@I~//~@@@2M~//~v1A0R~
////      ImageView v=(ImageView)AG.findViewById(Scapturedlistidtb[Pididx]);//~@@@@I~//~@@@2R~//~v101I~//~v1A0R~
//        ImageView v;                                               //~v101I~//~v1A0R~
//        v=capturedlistViewtb[Pididx];                              //~v101I~//~v1A0R~
////        if (v==null)                                               //~v101I~//~v1A0R~
////        {                                                          //~v101I~//~v1A0R~
////            v=(ImageView)findViewById(Scapturedlistidtb[Pididx]);  //~v101I~//~v1A0R~
////            capturedlistViewtb[Pididx]=v;                          //~v101I~//~v1A0R~
////        }                                                          //~v101I~//~v1A0R~
//        setImageBitmap(v,Pimage.bitmap,View.VISIBLE);              //~@@@@R~//~@@@2M~//~v1A0R~
//        if (Dump.Y) Dump.println("drawCaptuedPiece idxx="+Pididx); //~@@@@I~//~@@@2M~//~v1A0R~
//    }                                                              //~@@@@I~//~@@@2M~//~v1A0R~
////********************************************************         //~@@@2I~//~v1A0R~
//    private void drawPiece(Color Pbgcolor,int Pididx,Image Pimage) //~@@@2I~//~v1A0R~
//    {                                                              //~@@@2I~//~v1A0R~
////      ImageView v=(ImageView)AG.findViewById(Scapturedlistidtb[Pididx]);//~v101I~//~v1A0R~
//        ImageView v;                                               //~v101I~//~v1A0R~
//        v=capturedlistViewtb[Pididx];                              //~v101I~//~v1A0R~
//        if (v==null)                                               //~v101I~//~v1A0R~
//        {                                                          //~v101I~//~v1A0R~
//            v=(ImageView)findViewById(Scapturedlistidtb[Pididx]);//~@@@2R~//~v101R~//~v1A0R~
//            capturedlistViewtb[Pididx]=v;                          //~v101I~//~v1A0R~
//        }                                                          //~v101I~//~v1A0R~
//        setImageBitmap(v,Pimage.bitmap,View.VISIBLE,Pbgcolor);//by component//~@@@2R~//~v1A0R~
//        if (Dump.Y) Dump.println("drawCaptuedPiece with bgcolor idxx="+Pididx);//~@@@2I~//~v1A0R~
//    }                                                              //~@@@2I~//~v1A0R~
//********************************************************         //~v1A0I~
    public void draw(int Pvisible)                                //~v1A0R~
    {                                                              //~v1A0I~
        if (Dump.Y) Dump.println("CapturedButton draw visible="+Pvisible);//~v1A0M~
    	aButton.setVisibility(aButton.view,Pvisible);              //~v1A0R~
    }                                                              //~v1A0I~
//********************************************************         //~v1A0I~
	public void setLayoutWidthHeight(int Pw,int Ph,boolean Psettextsz)//~v1A0I~
    {                                                              //~v1A0I~
        if (Dump.Y) Dump.println("CapturedButton setLayoutWidthHeight w="+Pw+",h="+Ph+",settextsz="+Psettextsz);//~v1A0I~
    	aButton.setLayoutWidthHeight(aButton.view,Pw,Ph,Psettextsz);                    //~v1A0I~
    }                                                              //~v1A0I~
//********************************************************         //~v1A0I~
	public boolean isChecked()                                     //~v1A0I~
    {                                                              //~v1A0I~
    	boolean rc=aButton.isChecked();                            //~v1A0I~
        if (Dump.Y) Dump.println("CapturedButton isChecked rc="+rc);//~v1A0I~
    	return rc;                                                 //~v1A0I~
    }                                                              //~v1A0I~
//********************************************************         //~v1A0I~
	public void setChecked(boolean Pstat)                          //~v1A0I~
    {                                                              //~v1A0I~
        if (Dump.Y) Dump.println("CapturedButton setChecked stat="+Pstat);//~v1A0I~
    	aButton.setChecked(Pstat);                                 //~v1A0I~
    }                                                              //~v1A0I~
//********************************************************         //+v1A0I~
	public void setEnabled(boolean Pstat)                          //+v1A0I~
    {                                                              //+v1A0I~
        if (Dump.Y) Dump.println("CapturedButton setEnabled stat="+Pstat);//+v1A0I~
    	aButton.setEnabled(Pstat);                                 //+v1A0I~
    }                                                              //+v1A0I~
}
