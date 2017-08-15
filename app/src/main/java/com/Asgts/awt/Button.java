//*CID://+1A10R~:                             update#=   63;       //~v108R~//~@@@@R~//~v101R~//~1A10R~
//*************************************************************************//~v106I~
//1A10 2013/03/07 free board(for same action string,set resid)     //~1A10I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//1087:121217 accept long click for icon button                    //~v108I~
//1052:121112 Iconbar2 button sensibility;RadioButton could not be scaled.//~v106I~
//            change to Button(no need to be RadioButton, status is controled by GoFrame)//~v106I~
//*************************************************************************//~v106I~
package com.Asgts.awt;                                              //~@@@@R~//~v101R~

import com.Asgts.AG;                                               //~v101R~
import com.Asgts.awt.Font;//~@@@@R~                                //~v101R~
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
//import android.widget.ToggleButton;                              //~v106R~

import jagoclient.Dump;
import jagoclient.gui.ActionTranslator;

public class Button extends Component                              //~1217R~
{                                                                  //~1217R~
	private static final int SMALL_BUTTON_HEIGHT=24;	//pix      //~@@@@R~
	public android.widget.Button androidButton=null;                             //~1217I~//~1321R~
	public static int SbuttonActionResId;                          //~1A10I~
                                                                   //~1217I~
    private String name;                                           //~1425R~
    private int resId;                                             //~@@@@I~
    public String label;                                          //~1425R~//~@@@@R~
    private ActionTranslator AT;                                   //~1425R~
//    public int androidRid;                                       //~@@@@R~
//    private static final int[] toggleIconId={                          //~v105I~//~v106I~//~@@@@R~
//            R.id.mark,                                             //~v105I~//~v106M~//~@@@@R~
//            R.id.square,                                           //~v105I~//~v106M~//~@@@@R~
//            R.id.triangle,                                         //~v105I~//~v106M~//~@@@@R~
//            R.id.circle,                                           //~v105I~//~v106M~//~@@@@R~
//            R.id.letter,                                           //~v105I~//~v106M~//~@@@@R~
//            R.id.text,                                             //~v105I~//~v106M~//~@@@@R~
//            R.id.black,                                            //~v105I~//~v106M~//~@@@@R~
//            R.id.white,                                            //~v105I~//~v106M~//~@@@@R~
//            R.id.setblack,                                         //~v105I~//~v106M~//~@@@@R~
//            R.id.setwhite,                                         //~v105I~//~v106M~//~@@@@R~
//            R.id.delete                                            //~v105I~//~v106M~//~@@@@R~
//        };                                                          //~v105I~//~v106I~//~@@@@R~
//    private static final int[] drawableOn={                            //~v105I~//~v106I~//~@@@@R~
//            R.drawable.mark_on,                                       //~v105I~//~v106R~//~@@@@R~
//            R.drawable.square_on,                                     //~v105I~//~v106R~//~@@@@R~
//            R.drawable.triangle_on,                                   //~v105I~//~v106R~//~@@@@R~
//            R.drawable.circle_on,                                     //~v105I~//~v106R~//~@@@@R~
//            R.drawable.letter_on,                                     //~v105I~//~v106R~//~@@@@R~
//            R.drawable.text_on,                                       //~v105I~//~v106R~//~@@@@R~
//            R.drawable.black_on,                                      //~v105I~//~v106R~//~@@@@R~
//            R.drawable.white_on,                                      //~v105I~//~v106R~//~@@@@R~
//            R.drawable.setblack_on,                                   //~v105I~//~v106R~//~@@@@R~
//            R.drawable.setwhite_on,                                   //~v105I~//~v106R~//~@@@@R~
//            R.drawable.delete_on                                      //~v105I~//~v106R~//~@@@@R~
//        };                                                          //~v105I~//~v106I~//~@@@@R~
//        static final int[] drawableOff={                           //~v105I~//~v106I~//~@@@@R~
//            R.drawable.mark_enable,                                      //~v105I~//~v106M~//~@@@@R~
//            R.drawable.square_enable,                                    //~v105I~//~v106M~//~@@@@R~
//            R.drawable.triangle_enable,                                  //~v105I~//~v106M~//~@@@@R~
//            R.drawable.circle_enable,                                    //~v105I~//~v106M~//~@@@@R~
//            R.drawable.letter_enable,                                    //~v105I~//~v106M~//~@@@@R~
//            R.drawable.text_enable,                                      //~v105I~//~v106M~//~@@@@R~
//            R.drawable.black_enable,                                     //~v105I~//~v106M~//~@@@@R~
//            R.drawable.white_enable,                                     //~v105I~//~v106M~//~@@@@R~
//            R.drawable.setblack_enable,                                  //~v105I~//~v106M~//~@@@@R~
//            R.drawable.setwhite_enable,                                  //~v105I~//~v106M~//~@@@@R~
//            R.drawable.delete_enable                                     //~v105I~//~v106M~//~@@@@R~
//        };                                                          //~v105I~//~v106I~//~@@@@R~
//***************                                                  //~1114I~
    public Button(String Plabel)                                   //~1217R~
    {                                                            //~1126R~//~1217R~
    	label=Plabel;                                              //~1217I~
//*CardPanel dose not call addActionListener(ActionTranslator) but AddActionListener(ActionListener)//~1331I~
//        if (label.equals(Global.resourceString("Server_Connections"))) //~1217I~//~@@@@R~
//        {                                                          //~1331I~//~@@@@R~
//            androidButton=AG.ajagov.getTabButton(0);    //clicklistener by TabHost//~1217I~//~1306R~//~@@@@R~
//            setText((TextView)androidButton,label);                //~1331I~//~@@@@R~
//        }                                                          //~1331I~//~@@@@R~
//        else                                                       //~1217I~//~@@@@R~
//        if (label.equals(Global.resourceString("Partner_Connections")))//~1217I~//~@@@@R~
//        {                                                          //~1331I~//~@@@@R~
//            androidButton=AG.ajagov.getTabButton(1);                      //~1217I~//~1306R~//~@@@@R~
//            setText((TextView)androidButton,label);                //~1331I~//~@@@@R~
//        }                                                          //~1331I~//~@@@@R~
    }                                                              //~1217I~
//*********************************                                //~@@@@I~
//**find view by resid                                             //~@@@@I~
//*********************************                                //~@@@@I~
    public Button(String Plabel,int PresId)                        //~@@@@I~
    {                                                              //~@@@@I~
    	label=Plabel;                                              //~@@@@I~
    	resId=PresId;                                              //~@@@@I~
    }                                                              //~@@@@I~
//*********************************                                //~v101I~
//**find view by layoutView and resid                              //~v101I~
//*********************************                                //~v101I~
    public Button(Container Pcontainer,int Ptextid,int Presid)     //~v101I~
    {                                                              //~v101I~
    	super(Pcontainer);	//component                            //~v101I~
        if (Ptextid==0)                                            //~v101I~
	        label=null;                                            //~v101I~
        else                                                       //~v101I~
	        label=AG.resource.getString(Ptextid);	//doAction string//~v101I~
    	resId=Presid;                                              //~v101I~
    }                                                              //~v101I~
//*********************************                                //~@@@@I~
//*from Canvas                                                     //~@@@@I~
//*********************************                                //~@@@@I~
    public Button(int PresId)                                      //~@@@@I~
    {                                                              //~@@@@I~
    	resId=PresId;                                              //~@@@@I~
    }                                                              //~@@@@I~
//*********************************                                //~@@@@I~
    public static Button srchButton(int PresId)                    //~@@@@I~
    {                                                              //~@@@@I~
    	Button b=new Button(PresId);                               //~@@@@I~
        android.widget.Button ab=(android.widget.Button)AG.findViewById(PresId);//~@@@@I~
    	if (ab==null)                                              //~@@@@I~
        	b=null;                                                //~@@@@I~
        return b;                                                  //~@@@@I~
    }                                                              //~@@@@I~
//**************                                                   //~1217I~
    public void addActionListener(ActionTranslator Pat)                 //~1217I~
    {                                                              //~1217I~
    	AT=Pat;                                                    //~1217I~
    	name=Pat.Name;                                             //~1217I~
        init();                                                    //~1217I~
    }                                                              //~1217I~
//**************                                                   //~1217I~
//    public void addActionListener(ActionListener Pal)   //from gui.CardPanel,changed to TabLayout//~1217I~//~@@@@R~
//    {                                                              //~1217I~//~@@@@R~
//    }                                                            //~@@@@R~
//    public void addKeyListener(KeyListener Pkl) //from gui.CardPanel,changed to TabLayout//~1217I~//~@@@@R~
//    {                                                              //~1217I~//~@@@@R~
//    }                                                                                  //~1217I~//~@@@@R~
//**************                                                   //~1217I~
    private void init()                                            //~1217I~
    {                                                              //~1217I~
      if (resId!=0)                                                //~@@@@I~
      {                                                            //~v101I~
        if (parentContainer!=null && parentContainer.containerLayoutView!=null)//~v101I~
        androidButton=(android.widget.Button)parentContainer.findViewById(resId);//~v101R~
        else                                                       //~v101I~
        androidButton=(android.widget.Button)AG.findViewById(resId);//~@@@@I~
      }                                                            //~v101I~
      else                                                         //~@@@@I~
    	androidButton=(android.widget.Button)AG.findViewByName(name);	//search by Action name not by label                                     //~1217I~//~1306R~//~1331R~
        if (androidButton==null)   //specified label(=name) only   //~1331R~
        {                                                          //~1317I~
            if (androidButton==null)                               //~@@@@I~
	        	androidButton=(android.widget.Button)AG.findButtonView();//~1306R~//~@@@@R~
//            if (( AG.currentLayoutId==AG.dialogId_EditConnection   //~1318R~//~@@@@R~
//                ||AG.currentLayoutId==AG.dialogId_EditPartner      //~1318I~//~@@@@R~
//                )                                                  //~1318I~//~@@@@R~
//            &&  androidButton.getId()==R.id.Button1                //~1317I~//~@@@@R~
//            &&  name.equals(Global.resourceString("Add"))   //AddServer:(Set),Add,cancel//~1317I~//~@@@@R~
//            )                                                      //~1317I~//~@@@@R~
//            {                                                      //~1317I~//~@@@@R~
//                androidButton.setVisibility(View.GONE); //disable Set  //~1317I~//~@@@@R~
//                androidButton.setClickable(false);  //             //~1317I~//~@@@@R~
//                androidButton=(android.widget.Button)AG.findButtonView(); //1st visble is Add//~1317I~//~@@@@R~
//            }                                                      //~1317I~//~@@@@R~
        }                                                          //~1317I~
        componentView=androidButton;                               //~1405I~
        if (label!=null)                                           //~1331I~
        {                                                          //~1401I~
//          if (oldlabel.startsWith(AG.COMMON_BUTTON_NAME))        //~@@@@R~
            	setText((TextView)androidButton,label);            //~@@@@I~
        	if (AG.getCurrentDialog()==null && !AG.getCurrentFrame().isBoardFrame)	//not icon button//~1401I~
            	setText((TextView)androidButton,label);                //~1331I~//~1401R~
        }                                                          //~1401I~
        else                                                       //~@@@@I~
        	label=androidButton.getText().toString();              //~@@@@I~
//        if (name.equals("Help")     //for rene.CloseDialog,rene.dialog.GetParameter,rene.dialog.Warning//~1404R~//~@@@@R~
//        ||  name.equals(Global.resourceString("Help"))  //for GetParameter(GetPassword)//~1404I~//~@@@@R~
//        )                                               //Help button is optional//~1404I~//~@@@@R~
//      	androidButton.setVisibility(View.VISIBLE);	//reset visibility="gone" on xml if accessed//~1404R~//~@@@@R~
        	setVisibility((View)androidButton,View.VISIBLE);	//reset visibility="gone" on xml if accessed//~@@@@I~
        androidButton.setOnClickListener(                                 //~1217I~//~1306R~
			 new OnClickListener()                                 //~1217I~
                    {                                      //~1114I~//~1217I~
                        @Override                          //~1114I~//~1217I~
                        public void onClick(View Pv)            //~1114I~//~1217I~
                        {                                  //~1114I~//~1217I~
                            	onClickButtonAction(Pv);       //~1114I~//~1217I~//~v101R~
                         }                                  //~1114I~//~1217I~
                    }                                      //~1114I~//~1217I~
                                 );                                       //~1114I~//~1217I~
    }                                                              //~1217I~
//*************                                                    //~1114M~
	public void onClickButtonAction(View Pview)                    //~1114M~
    {                                                              //~1114M~
    	if (Dump.Y) Dump.println("onClickButtonAction button="+name);//~1506R~
		if (AT!=null)                                              //~1114M~
        {                                                          //~1114I~
//          android.widget.Button b=null;                          //+1A10R~
//          b.setText("AA");    //@@@@testACRA                     //+1A10R~
            try                                                    //~v101I~
            {                                                      //~v101I~
				SbuttonActionResId=resId;                          //~1A10I~
        		ActionEvent.actionPerformed(AT,this);                             //~1114R~//~1124R~//~1524R~//~v101R~
            }                                                      //~v101I~
            catch(Exception e)                                     //~v101I~
            {                                                      //~v101I~
                Dump.println(e,"OnClickButton name="+name);        //~v101I~
            }                                                      //~v101I~
        }                                                          //~1114I~
    }                                                              //~1114M~
//*************************************                            //~v108R~
//*from Asgts/rene/gui/IconBar                                    //~v108I~//~@@@@R~//~v101R~
//*************************************                            //~v108I~
    public void addLongClickListener()                             //~v108I~
    {                                                              //~v108I~
        androidButton.setOnLongClickListener(                      //~v108M~
			 new View.OnLongClickListener()                        //~v108M~
                    {                                              //~v108M~
                        @Override                                  //~v108M~
                        public boolean onLongClick(View Pv)        //~v108M~
                        {                                          //~v108M~
                            return onLongClickButtonAction(Pv);    //~v108M~
                        }                                          //~v108M~
                    }                                              //~v108M~
                                 );                                //~v108M~
    }                                                              //~v108I~
//*************                                                    //~v108I~
	public boolean onLongClickButtonAction(View Pview)                //~v108I~
    {                                                              //~v108I~
    	boolean rc=false;                                                //~v108I~
    	if (Dump.Y) Dump.println("onLongClickButtonAction button="+name);//~v108I~
        if (AT!=null)                                              //~v108R~
        {                                                          //~v108I~
        	onClickButtonAction(Pview);                            //~v108I~
            rc=true;                                               //~v108I~
        }                                                          //~v108I~
        return rc;                                                 //~v108I~
    }                                                              //~v108I~
//************                                                     //~1126I~
    public void setFont(Font Pfont)                              //~1126I~
    {                                                              //~1126I~
//    	Pfont.setFont(androidButton);                               //~1126I~//~1217R~//~1306R~//~@@@@R~
      	setFont((View)androidButton,Pfont);	//OnUiThread           //~@@@@I~
    }                                                              //~1126I~
//************                                                     //~v106I~
//    public static boolean getToggleButtonDrawableId(android.widget.Button Pview,int [] Pids)                        //~v105I~//~v106I~//~@@@@R~
//    {                                                              //~v105I~//~v106I~//~@@@@R~
//        boolean rc=false;                                          //~v106R~//~@@@@R~
//    //****************                                             //~v106I~//~@@@@R~
//        int id=Pview.getId();                                      //~v105I~//~v106I~//~@@@@R~
//        for (int ii=0;ii<toggleIconId.length;ii++)                 //~v105I~//~v106I~//~@@@@R~
//            if (id==toggleIconId[ii])                              //~v105I~//~v106I~//~@@@@R~
//            {                                                      //~v105I~//~v106I~//~@@@@R~
//                Pids[0]=drawableOn[ii];                          //~v105I~//~v106I~//~@@@@R~
//                Pids[1]=drawableOff[ii];                        //~v105I~//~v106I~//~@@@@R~
//                rc=true;                                           //~v106I~//~@@@@R~
//                break;                                             //~v105I~//~v106I~//~@@@@R~
//            }                                                      //~v105I~//~v106I~//~@@@@R~
//        return rc;                                                 //~v106I~//~@@@@R~
//    }                                                              //~v105I~//~v106I~//~@@@@R~
////************************************************************** //~@@@@R~
////*set ID for ActionListener                                     //~@@@@R~
////************************************************************** //~@@@@R~
//    public setResId(int Pid)                                     //~@@@@R~
//    {                                                            //~@@@@R~
//        androidRid=Pid;                                          //~@@@@R~
//        androidButton.setId(Pid);                                //~@@@@R~
//    }                                                            //~@@@@R~
    //****************************************************************************//~@@@@I~
    public void setEnabled(boolean Penable)                        //~@@@@I~
    {                                                              //~@@@@I~
        setEnabled(androidButton,Penable);	//by component         //~@@@@I~//~v101R~
    }                                                              //~@@@@I~
}//class                                                           //~1121R~
