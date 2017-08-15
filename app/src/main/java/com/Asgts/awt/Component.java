//*CID://+1A4vR~:                             update#=   65;       //+1A4vR~
//**********************************************************************//~v105I~
//1A4v 2014/12/07 dislay comment area for replyboard               //+1A4vI~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A00 2013/02/13 Asgts                                            //~1A00I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//1089:121117 confirm requestFocus runOnUiThread                   //~v108I~
//1086:121216 avoid automatic ime popup for ConnectionFrame and PartnerGoFrame IgsGoFrame//~v108I~
//1084:121215 connection frame input field is untachable when restored after Who frame//~v108I~
//1053:121113 exception(wrong thread) when filelist up/down for sgf file read//~v105I~
//**********************************************************************//~v105I~
package com.Asgts.awt;                                            //~1112I~//~v108R~//~v101R~

import jagoclient.Dump;
import jagoclient.gui.DoActionListener;

import com.Asgts.AG;                                                //~@@@@R~//~v101R~
import com.Asgts.UiThread;                                          //~@@@@R~//~v101R~
import com.Asgts.UiThreadI;                                         //~@@@@R~//~v101R~

import android.graphics.Bitmap;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import android.widget.RadioButton;                               //~v105R~
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.BaseAdapter;                                 //~@@@@I~
import android.widget.ListAdapter;                                 //~@@@@I~
import android.widget.ListView;                                    //~@@@@I~
import android.widget.ToggleButton;                                //~1A00I~
import android.widget.Button;                                      //~1A00I~
// Object-->awt.Component-->Container-->Panel                      //~1116I~
//                                   -->Window-->Frame             //~1116I~
                                                                   //~1112I~
public class Component// extends LinearLayout                        //~1120R~//~1216R~
	implements UiThreadI, ImageObserver                                      //~1221I~//~1308R~//~@@@@R~
{                                                                  //~1112I~
	private int caseUiThread;                                      //~1425R~
    private final static int CASE_APPEND      =1;                  //~1221I~
    private final static int CASE_SETTEXT     =2;                  //~1221R~
    private final static int CASE_SHOWBOTTOM  =3;                  //~1221I~//~@@@@R~
    private final static int CASE_TITLE       =4;                  //~1221I~
    private final static int CASE_BGCOLOR     =5;                  //~1310I~
    private final static int CASE_FONT        =6;                  //~1310I~
    private final static int CASE_BGFGCOLOR   =7;                  //~1312I~
    private final static int CASE_SETICON     =8;                  //~1313I~
    private final static int CASE_ENABLE      =9;                  //~1322I~
    private final static int CASE_SETCHECK    =10;                 //~1322I~
    private final static int CASE_FOCUS       =11;                 //~1405I~
    private final static int CASE_SETITEMCHECKED=12;               //~v105I~//~@@@@R~
    private final static int CASE_FOCUSTOUCH   =13;                //~v108I~
    private final static int CASE_SETVISIBILITY=14;                //~@@@@I~
    private final static int CASE_SETIMAGEBITMAP=15;                  //~@@@@I~
    private final static int CASE_SETHEIGHT=16;                    //~@@@@I~
    private final static int CASE_SETLAYOUTHEIGHT=17;              //~@@@@I~
    private final static int CASE_APPENDSPAN=18;                   //~v101I~
    private final static int CASE_SETCHECKED_TB=19;     //togglebutton//~1A00R~
    private final static int CASE_SETBUTTONDRAWABLE=20;     //togglebutton//~1A00I~
    private final static int CASE_SCROLL=21;    //TextArea(Comment)//+1A4vI~
                                                                   //~v101I~
    private final static int DEFAULT_TEXT_SIZE=48;                 //~@@@@I~
    private String line;                                              //~1221R~//~1425R~
    private SpannableString spanline;                              //~v101I~
    private Color color;                                              //~1221R~//~1425R~
    private int   iconresid;                                      //~1313I~
                                                                   //~1310I~
    public  int  componentType=0;        //control UI sync/async   //~1310R~
    public  static final int COMPONENT_FRAME=1;                    //~1310I~
    public  static final int COMPONENT_DIALOG=2;                   //~1310I~
    public  Frame  frame;                                          //~1311R~
    public  Window parentWindow; 
    public Container parentContainer;//~1425R~
    public  Dialog dialog;                                         //~1310I~
                                                                   //~1310I~
    protected Font  font;                                            //~1310I~
    private Color bgcolor,fgcolor;                                         //~1310R~
    private Color imagebgcolor;                                    //~@@@@I~
    ScrollView scrollview;                                         //~1425R~
    int pos=0;                                                     //~1221I~
                                                                   //~1221I~
	public View layout;                                            //~1425R~
	public View componentView;	//frame,textfield,button for requestFocus//~1425R~
    public Component directParentComponent;                        //~1425R~
    public Color componentBackground; 
    private boolean buttonState;
    private boolean listItemState;                                 //~v105I~//~@@@@R~
    private int drawableResourceId; 
    private int visibility;//~2B13I~
    private int imagevisibility;                                //~@@@@I~
    private int viewHeight,viewWidth;                                        //~@@@@I~
    private boolean setTextSize;                                   //~@@@@I~
    private Bitmap bitmap;                                         //~@@@@I~
//*************                                                    //~1310I~
    public Component()                                                   //~1112I~//~1113R~
    {                                                              //~1112I~
          setParent();                                               //~1311I~//~@@@@R~
    }                                                              //~1112I~
    public Component(Container Pcontainer)                         //~v101R~
    {                                                              //~v101R~
        this();	//set parentWindow                                 //~v101I~
		parentContainer=Pcontainer; //frame or dialog                                  //~v101R~
    }                                                              //~v101R~
    public void setComponentType(Frame Pframe)                                 //~1310I~//~@@@@R~
    {                                                              //~1310I~//~@@@@R~
        componentType=COMPONENT_FRAME;                              //~1310I~//~@@@@R~
        frame=Pframe;                                              //~1310I~//~@@@@R~
        AG.setCurrentFrame(Pframe);                                    //~1408I~//~@@@@R~
    }                                                              //~1310I~//~@@@@R~
    public void setComponentType(Dialog Pdialog)                               //~1310I~//~@@@@R~
    {                                                              //~1310I~//~@@@@R~
        componentType=COMPONENT_DIALOG;                             //~1310I~//~@@@@R~
        dialog=Pdialog;                                            //~1310I~//~@@@@R~
        AG.setCurrentDialog(Pdialog);                               //~1408I~//~@@@@R~
    }                                                              //~1310I~//~@@@@R~
    public void setParent()                                        //~1311R~//~@@@@R~
    {                                                              //~1311I~//~@@@@R~
        if (AG.currentIsDialog)                                    //~1311I~//~@@@@R~
            parentWindow=AG.currentDialog;                         //~1408R~//~@@@@R~
        else                                                       //~1311I~//~@@@@R~
            parentWindow=AG.currentFrame;                          //~1408R~//~@@@@R~
    }                                                              //~1311I~//~@@@@R~
//    private void setDirectParent(Component Padded)                 //~1417I~//~@@@@R~
//    {                                                              //~1417I~//~@@@@R~
//        Padded.directParentComponent=this;                         //~1417I~//~@@@@R~
//    }                                                              //~1417I~//~@@@@R~
//    public Component getDirectParent()                             //~1417I~//~@@@@R~
//    {                                                              //~1417I~//~@@@@R~
//        return directParentComponent;                              //~1417I~//~@@@@R~
//    }                                                              //~1417I~//~@@@@R~
//******************                                               //~1310I~
//    public void add(View Pview)                                    //~1112I~//~@@@@R~
//    {                                                            //~@@@@R~
//    }                                                              //~1112I~//~@@@@R~
//    public void add(String Pname,Component Pcomponent)    //from CardPanel//~1217R~//~@@@@R~
//    {                                                              //~1124I~//~@@@@R~
//        if (Dump.Y) Dump.println("Component add:"+Pname);          //~1506R~//~@@@@R~
//        setDirectParent(Pcomponent);                               //~1417I~//~@@@@R~
//        if (Pname.equals(Global.resourceString("Server_Connections")))//~1217I~//~@@@@R~
//            AG.ajagov.initMainFrameTab(Frame.MainFrame);           //~1217I~//~@@@@R~
//                                                                   //~1217I~//~@@@@R~
//    }                                                              //~1124I~//~@@@@R~
//    public void add(String Pname,Viewer Pviewer)    //from ConnectionFrame//~1219I~//~@@@@R~
//    {                                                              //~1219I~//~@@@@R~
//    }                                                              //~1219I~//~@@@@R~
//    public void add(String Pname,List Plist)    //from SystemLister//~1220I~//~@@@@R~
//    {                                                              //~1220I~//~@@@@R~
//    }                                                              //~1220I~//~@@@@R~
//    public void add(Component Pcomponent)    //from Panel3D; Pcompnent is panel containing Button//~1217I~//~@@@@R~
//    {                                                              //~1217I~//~@@@@R~
//        setDirectParent(Pcomponent);                               //~1417I~//~@@@@R~
//    }                                                              //~1217I~//~@@@@R~
//    public void add(String s,Canvas Pcanvas)     //Canvas-->Object //~1116I~//~@@@@R~
//    {                                                              //~1116I~//~@@@@R~
//        setDirectParent(Pcanvas);                                  //~1417I~//~@@@@R~
//    }                                                              //~1116I~//~@@@@R~
//    public void add(String s,Go Pgo)                               //~1125I~//~@@@@R~
//    {                                                              //~1125I~//~@@@@R~
//    }                                                              //~1125I~//~@@@@R~
//    public void add(jagoclient.gui.ButtonAction Pbuttonaction)        //~1126I~//~@@@@R~
//    {                                                              //~1126I~//~@@@@R~
//        setDirectParent(Pbuttonaction);                            //~1417I~//~@@@@R~
//    }                                                              //~1126I~//~@@@@R~
//    public void remove(Component P1) //for GoFrame                 //~1216R~//~@@@@R~
//    {                                                              //~1216I~//~@@@@R~
//    }                                                              //~1216I~//~@@@@R~
//    public Component(View P1,double P2,View P3,double P4)  //GoFrame->SimplePanel//~1216R~//~@@@@R~
//    {                                                            //~@@@@R~

//    }                                                              //~1216I~//~@@@@R~
//*****************                                                //~1121I~
//    public void show()                                             //~1117I~//~@@@@R~
//    {                                                              //~1117I~//~@@@@R~
////      AG.ajagoc.setContentView(layout);                         //~1117I~//~1125R~//~@@@@R~
//    }                                                              //~1117I~//~@@@@R~
//*****************                                                //~1122I~
    public Image createImage(MemoryImageSource Ppixel)                       //~1117I~//~1120I~
    {                                                              //~1227R~
    	return Image.createImage(Ppixel,this);                          //~1117R~//~1227R~
    }                                                              //~1117I~//~1120I~
//***************** from Global                                    //~1417I~
    public Image createImage(int Pw,int Ph)                        //~1417I~
    {                                                              //~1417I~
    	if (Dump.Y) Dump.println("Component.createImage return null w="+Pw+",h="+Ph);//~1506R~
    	return null;                                               //~1417I~
    }                                                              //~1417I~
//*****************                                                //~1122I~
// for Global                                                      //~1417I~
//*****************                                                //~1417I~
//    public void pack()                                             //~1417R~//~@@@@R~
//    {                                                              //~1122I~//~@@@@R~
//    }                                                              //~1122I~//~@@@@R~
//    public Dimension getSize()                                     //~1417I~//~@@@@R~
//    {                                                              //~1417I~//~@@@@R~
//        return new Dimension(0,0);                                 //~1417I~//~@@@@R~
//    }                                                              //~1417I~//~@@@@R~
//    public void setBounds(int Px,int Py,int Pw,int Ph)             //~1417I~//~@@@@R~
//    {                                                              //~1417I~//~@@@@R~
//    }                                                              //~1417I~//~@@@@R~
//***********                                                      //~1417I~
//    public void setLayout(FlowLayout P1)                           //~1124M~//~1125R~//~@@@@R~
//    {                                                              //~1124M~//~@@@@R~
//    }                                                              //~1124M~//~1125R~//~@@@@R~
//    public void setLayout(GridLayout P1)                          //~1124M~//~1125R~//~@@@@R~
//    {                                                              //~1124M~//~@@@@R~
//    }                                                              //~1124M~//~@@@@R~
//    public void setLayout(BorderLayout P1)                        //~1124M~//~1125R~//~@@@@R~
//    {                                                              //~1124M~//~@@@@R~
//    }                                                              //~1124M~//~@@@@R~
//    public void setLayout(CardLayout P1)                           //~1125I~//~@@@@R~
//    {                                                              //~1125I~//~@@@@R~
//    }                                                              //~1125I~//~@@@@R~
//    public void doLayout()                                         //~1221I~//~@@@@R~
//    {                                                              //~1221I~//~@@@@R~
//    }                                                              //~1221I~//~@@@@R~
//*for rene.Lister                                                 //~1216I~
//    public void addKeyListener(KeyListener Pkl)                    //~1216I~//~@@@@R~
//    {                                                              //~1216I~//~@@@@R~
//    }                                                              //~1216I~//~@@@@R~
//*for TextDisplay,Panel3D                                         //~1417R~
    public void setBackground(Color Pcolor)                        //~1127I~//~1212I~//~1216I~
    {                                                              //~1127I~//~1212I~//~1216I~
      componentBackground=Pcolor;                                             //~1212I~//~1213R~//~1216I~
    }                                                              //~1127I~//~1212I~//~1216I~
//from TextComponent                                               //~1310R~
    public void setBackground(View Pview,Color Pcolor)             //~1310I~
    {                                                              //~1310I~
    	bgcolor=Pcolor;                                            //~1310I~
    	runOnUiThread(CASE_BGCOLOR,Pview);   //by Component        //~1310I~
    }                                                              //~1310I~
    public void setBackground(View Pview,Color Pcolor,Color Ptextcolor)//~1312I~
    {                                                              //~1312I~
    	bgcolor=Pcolor;                                            //~1312I~
    	fgcolor=Ptextcolor;                                        //~1312I~
    	runOnUiThread(CASE_BGFGCOLOR,Pview);   //by Component      //~1312I~
    }                                                              //~1312I~
    public void setBackgroundUI(Object Pparm)                     //~1310I~
    {                                                              //~1310I~
    	bgcolor.setBackground((View)Pparm);                        //~1310I~
    }                                                              //~1310I~
    public void setBGFGUI(Object Pparm)                            //~1312I~
    {                                                              //~1312I~
    	bgcolor.setBackground((View)Pparm);                        //~1312I~
    	fgcolor.setTextColor((View)Pparm);                         //~1312I~
    }                                                              //~1312I~
    public void setFont(View Pview,Font Pfont)                     //~1310I~
    {                                                              //~1310I~
    	font=Pfont;                                                //~1310I~
    	runOnUiThread(CASE_FONT,Pview);   //by Component           //~1310I~
    }                                                              //~1310I~
    public void setFontUI(Object Pparm)                            //~1310I~
    {                                                              //~1310I~
    	font.setFont((TextView)Pparm);                                 //~1310I~
    }                                                              //~1310I~
    public void setHeight(View Pview,int Pheight)                  //~@@@@I~
    {                                                              //~@@@@I~
    	viewHeight=Pheight;                                        //~@@@@I~
    	runOnUiThread(CASE_SETHEIGHT,Pview);   //by Component      //~@@@@I~
    }                                                              //~@@@@I~
    public void setHeightUI(Object Pparm)                          //~@@@@I~
    {                                                              //~@@@@I~
    	((TextView)Pparm).setHeight(viewHeight);                   //~@@@@I~
    }                                                              //~@@@@I~
    public void setLayoutHeight(View Pview,int Pheight,boolean Psettextsize)//~@@@@R~
    {                                                              //~@@@@I~
    	viewHeight=Pheight;	//height in pixel                      //~@@@@R~
    	viewWidth=0;	//height in pixel                          //~@@@@I~
        setTextSize=Psettextsize;                                  //~@@@@I~
    	runOnUiThread(CASE_SETLAYOUTHEIGHT,Pview);   //by Component//~@@@@I~
    }                                                              //~@@@@I~
    public void setLayoutWidthHeight(View Pview,int Pwidth,int Pheight,boolean Psettextsize)//~@@@@I~
    {                                                              //~@@@@I~
    	viewHeight=Pheight;	//height in pixel                      //~@@@@I~
    	viewWidth=Pwidth;	//height in pixel                      //~@@@@I~
        setTextSize=Psettextsize;                                  //~@@@@I~
    	runOnUiThread(CASE_SETLAYOUTHEIGHT,Pview);   //by Component//~@@@@I~
    }                                                              //~@@@@I~
    public void setLayoutHeightUI(Object Pparm)                    //~@@@@I~
    {                                                              //~@@@@I~
    	View v=(View)Pparm;                                        //~@@@@I~
        ViewGroup.LayoutParams params=v.getLayoutParams();         //~@@@@I~
        params.height=viewHeight;	//pixel                        //~@@@@R~
        if (viewWidth!=0)                                          //~@@@@I~
	        params.width=viewWidth;	//pixel                    //~@@@@I~
        v.setLayoutParams(params);                                 //~@@@@I~
        if (setTextSize)                                       //~@@@@R~
        {                                                          //~@@@@I~
        	TextView tv=(TextView)Pparm;                           //~@@@@I~
//            float textsz=tv.getTextSize();  //in pixel           //~@@@@R~
//            textsz*=(float)viewHeight/DEFAULT_TEXT_SIZE;         //~@@@@R~
//            textsz/=AG.sp2pix;  //unit sp                        //~@@@@R~
            tv.setTextSize((float)AG.smallTextSize/*sp*/);         //~@@@@R~
        }                                                          //~@@@@I~
    }                                                              //~@@@@I~
    public void setVisibility(View Pview,int Pvisibility)          //~@@@@I~
    {                                                              //~@@@@I~
    	visibility=Pvisibility;                                    //~@@@@I~
    	runOnUiThread(CASE_SETVISIBILITY,Pview);   //by Component  //~@@@@I~
    }                                                              //~@@@@I~
    public void setImageBitmap(View Pview,Bitmap Pbitmap,int Pvisibility)//~@@@@I~
    {                                                              //~@@@@I~
    	bitmap=Pbitmap;                                            //~@@@@I~
        imagebgcolor=null;                                         //~@@@@I~
    	imagevisibility=Pvisibility;                               //~@@@@I~
    	runOnUiThread(CASE_SETIMAGEBITMAP,Pview);                  //~@@@@I~
    }                                                              //~@@@@I~
    public void setImageBitmap(View Pview,Bitmap Pbitmap,int Pvisibility,Color Pbgcolor)//~@@@@I~
    {                                                              //~@@@@I~
    	bitmap=Pbitmap;                                            //~@@@@I~
        imagebgcolor=Pbgcolor;                                     //~@@@@I~
    	imagevisibility=Pvisibility;                               //~@@@@I~
    	runOnUiThread(CASE_SETIMAGEBITMAP,Pview);                  //~@@@@I~
    }                                                              //~@@@@I~
//***                                                              //~1310I~
//    public void resized()                                          //~1212I~//~@@@@R~
//    {                                                              //~1212I~//~@@@@R~
//                          //~1212I~                              //~@@@@R~
//    }                                                              //~1212I~//~@@@@R~
//*for rene.CloseFrame                                             //~1213I~
//    public Point getLocation()                                     //~1213I~//~@@@@R~
//    {                                                              //~1213I~//~@@@@R~
//        return new Point(0,0);                                     //~1213I~//~@@@@R~
//    }                                                              //~1213I~//~@@@@R~
//    public void setLocation(int Px,int Py)                         //~1213I~//~@@@@R~
//    {                                                              //~1213I~//~@@@@R~
//    }                                                              //~1213I~//~@@@@R~
//    public void setSize(int Pw,int Ph)                             //~1213I~//~@@@@R~
//    {                                                              //~1213I~//~@@@@R~
//    }                                                              //~1213I~//~@@@@R~
	public Toolkit getToolkit()                                       //~1213I~
    {                                                              //~1213I~
    	return new Toolkit();                                      //~1213I~
    }                                                              //~1213I~
    public void addMouseListener(MouseListener Pml)                //~1213I~
    {                                                              //~1213I~
    	if (Pml instanceof DoActionListener)
		 {
		}
    }                                                              //~1213I~
//    public void addMouseListener(MouseAdapter Pma) //rene.lister.ListerPanel//~1214I~//~@@@@R~
//    {                                                              //~1214I~//~@@@@R~
//    }                                                              //~1214I~//~@@@@R~
//    public void addMouseMotionListener(MouseMotionListener Pmml)   //~1213I~//~@@@@R~
//    {                                                            //~@@@@R~
//    }                                                              //~1213I~//~@@@@R~
//    public void addMouseWheelListener(Wheel Pw)                    //~1213R~//~@@@@R~
//    {                                                              //~1213I~//~@@@@R~
//    }                                                              //~1213I~//~@@@@R~
//    public void addActionListener(ActionListener Pal)              //~1213I~//~@@@@R~
//    {                                                              //~1213I~//~@@@@R~
//    }                                                              //~1213I~//~@@@@R~
//    public Dimension getMinimumSize()   //for Panel3D              //~1214R~//~@@@@R~
//    {                                                              //~1214I~//~@@@@R~
//        return new Dimension(0,0);                                 //~1214I~//~@@@@R~
//    }                                                              //~1214I~//~@@@@R~
//    public Dimension getPreferredSize()                            //~1214R~//~@@@@R~
//    {                                                              //~1214I~//~@@@@R~
//        return new Dimension(10,10);                               //~1214I~//~@@@@R~
//    }                                                              //~1214I~//~@@@@R~
    public Color getBackground()                                   //~1214R~
    {                                                              //~1214I~
    	return componentBackground;                                //~1214I~
    }                                                              //~1214I~
//    public void addFocusListener(FocusListener Pfl)                //~1213I~//~1214I~//~@@@@R~
//    {                                                              //~1213I~//~1214I~//~@@@@R~
//    }                                                              //~1213I~//~1214I~//~@@@@R~
//****************                                                 //~1405I~
    public void requestFocus()  //for Board ,moved to Canvas       //~1405R~
    {                                                              //~1405R~
    	Frame f=AG.getCurrentFrame();                              //~v108R~
        if (f!=null)                                               //~v108R~
        {                                                          //~v108I~
//        	int fid=f.framelayoutresourceid;                       //~v108I~
//            if (fid==AG.frameId_ConnectionFrame)  //"Input" TextField//~v108R~//~@@@@R~
//                return;                                            //~v108R~//~@@@@R~
//            if (fid==AG.frameId_ConnectedGoFrame) //"ExtraSendField"//~v108R~//~@@@@R~
//                return;                                            //~v108R~//~@@@@R~
        }                                                          //~v108I~
        if (componentView!=null)                                   //~1405R~
	    	runOnUiThread(CASE_FOCUS,null);                        //~1405I~
    }                                                              //~1405R~
//****************                                                 //~v108I~
    public void requestFocus(View Pview)  //for connectedGoFrame   //~v108R~
    {                                                              //~v108I~
        if (Pview!=null)                                           //~v108R~
	    	runOnUiThread(CASE_FOCUS,Pview);                       //~v108R~
    }                                                              //~v108I~
//****************                                                 //~v108I~
    public void requestFocusFromTouch()                            //~v108I~
    {                                                              //~v108I~
        if (componentView!=null)                                   //~v108I~
	    	runOnUiThread(CASE_FOCUSTOUCH,null);                   //~v108I~
    }                                                              //~v108I~
//****************                                                 //~v108I~
    private void requestFocusUI(Object Pparm)                      //~1405I~
    {                                                              //~1405I~
    	if (Pparm!=null)                                           //~v108I~
        {                                                          //~v108I~
	    	if (Dump.Y) Dump.println("Componet.requestFocus view="+Pparm.toString());//~v108I~
        	((View)Pparm).requestFocus();                          //~v108I~
        }                                                          //~v108I~
    	if (Dump.Y) Dump.println("Componet.requestFocus view="+componentView.toString());//~1506R~
        if (Dump.Y) Dump.println("focusable="+componentView.isFocusable()+",touch="+componentView.isFocusableInTouchMode()+",isfocused="+componentView.isFocused());//~1506R~
        componentView.requestFocus();                              //~1405I~
        if (Dump.Y) Dump.println("after focusable="+componentView.isFocusable()+",touch="+componentView.isFocusableInTouchMode()+",isfocused="+componentView.isFocused());//~1506R~
    }                                                              //~1405I~
//****************                                                 //~v108I~
    private void requestFocusFromTouchUI(Object Pparm)                 //~v108I~
    {                                                              //~v108I~
    	if (Dump.Y) Dump.println("Componet.requestFocusFromTouch view="+componentView.toString());//~v108I~
        componentView.requestFocusFromTouch();                     //~v108I~
    }                                                              //~v108I~
//****************                                                 //+1A4vI~
    public void scrollTo(ScrollView Pview,int Ppos)                //+1A4vI~
    {                                                              //+1A4vI~
    	if (Pview==null)                                      //+1A4vI~
        	return;                                                //+1A4vI~
        pos=Ppos;                                                  //+1A4vI~
    	runOnUiThread(CASE_SCROLL,Pview);                          //+1A4vI~
    }                                                              //+1A4vI~
    public void scrollToUI(Object Pparm)                           //+1A4vI~
    {                                                              //+1A4vI~
    	ScrollView view=(ScrollView)Pparm;                         //+1A4vI~
        int y=pos;                                                     //+1A4vI~
        if (pos<0)                                                 //+1A4vI~
        	y=32760;                                               //+1A4vI~
        view.scrollTo(0/*x*/,y);                                   //+1A4vI~
    }                                                              //+1A4vI~
//****************                                                 //~1405I~
//    public void validate()  //for Frame and Dialog                                       //~1125I~//~1215I~//~1216I~//~@@@@R~
//    {                                                              //~1125I~//~1215I~//~1216I~//~@@@@R~
//    }                                                              //~1125I~//~1215I~//~1216I~//~@@@@R~
//    public void paint(Graphics Pg)                                    //~1216I~//~1219I~//~@@@@R~
//    {                                                              //~1216I~//~1219I~//~@@@@R~
//    }                                                              //~1216I~//~1219I~//~@@@@R~
//********************************                                 //~1221I~
//* support All runOnUiThread                                      //~1221I~
//********************************                                 //~1221I~
    public void runOnUiThread(int Pcase,Object Pparm)              //~1221I~
    {                                                              //~1221I~
    	caseUiThread=Pcase;                                        //~1221I~
        boolean wait=waitmode(Pcase);                                   //~1310I~
		UiThread.runOnUiThread(wait,this,Pparm);               //~1221I~//~1310R~//~@@@@R~
    }                                                              //~1221I~
    boolean waitmode(int Pcase)                                    //~1310I~
    {                                                              //~1310I~
    	boolean waitmode=true;                                     //~1310I~
//        if ((parentWindow!=null)                                 //~1425R~
//        &&  (parentWindow instanceof Frame)                      //~1425R~
//        &&  ((Frame)parentWindow).componentType==COMPONENT_FRAME                         //~1310I~//~1425R~
//        &&  ((Frame)parentWindow).isBoardFrame                   //~1425R~
//        )                                                        //~1425R~
//            waitmode=false; //avoid deadlock on BoardFrame                                        //~1310I~//~1425R~
//		now Board operation executed on subtread, so wait dose not lock main threasd//~1425I~
        return waitmode;                                           //~1310I~
    }                                                              //~1310I~
//************                                                     //~1310I~
	@Override                                                      //~1221I~
    public void runOnUiThread(Object Pparm)                        //~1221I~
    {                                                              //~1221I~
        if (Dump.Y) Dump.println("Component runOnUi case="+caseUiThread);//~1506R~
        switch(caseUiThread)                                       //~1221I~
        {                                                          //~1221I~
        case CASE_APPEND:                                          //~1221I~
        	appendUI(Pparm);                                       //~1221I~
            break;                                                 //~1221I~
        case CASE_SETTEXT:                                         //~1221I~
        	setTextUI(Pparm);                                      //~1221I~
            break;                                                 //~1221I~
        case CASE_SHOWBOTTOM:                                      //~1221I~//~@@@@R~
            showBottomUI(Pparm);                                   //~1221I~//~@@@@R~
            break;                                                 //~1221I~//~@@@@R~
        case CASE_TITLE:                                           //~1221I~
        	setTitleUI(Pparm);                                     //~1221I~
            break;                                                 //~1221I~
        case CASE_BGCOLOR:                                         //~1310I~
        	setBackgroundUI(Pparm);                                //~1310I~
            break;                                                 //~1310I~
        case CASE_BGFGCOLOR:                                       //~1312I~
        	setBGFGUI(Pparm);                                      //~1312I~
            break;                                                 //~1312I~
        case CASE_FONT:                                            //~v108I~//~@@@@I~
//          setFontUI(Pparm);   //use android default button text size//~@@@@R~
            break;                                                 //~v108I~//~@@@@I~
        case CASE_SETICON:                                         //~1313I~
        	setIconImageUI(Pparm);                                 //~1417R~
            break;                                                 //~1313I~
        case CASE_ENABLE:                                          //~1322I~
        	setEnabledUI(Pparm);                                   //~1322I~
            break;                                                 //~1322I~
        case CASE_SETCHECK:                                        //~1322I~
        	setCheckedUI(Pparm);                                   //~1322I~
            break;                                                 //~1322I~
        case CASE_FOCUS:                                        //~1405I~
        	requestFocusUI(Pparm);                                 //~1405I~
            break;                                                 //~1405I~
        case CASE_FOCUSTOUCH:                                      //~v108I~
        	requestFocusFromTouchUI(Pparm);                        //~v108I~
            break;                                                 //~v108I~
        case CASE_SETVISIBILITY:                                   //~@@@@I~
        	setVisibilityUI(Pparm);                                //~@@@@I~
            break;                                                 //~@@@@I~
        case CASE_SETIMAGEBITMAP:                                  //~@@@@I~
        	setImageBitmapUI(Pparm);                               //~@@@@I~
            break;                                                 //~@@@@I~
        case CASE_SETHEIGHT:                                       //~@@@@I~
        	setHeightUI(Pparm);                                    //~@@@@I~
            break;                                                 //~@@@@I~
        case CASE_SETLAYOUTHEIGHT:                                 //~@@@@I~
        	setLayoutHeightUI(Pparm);                              //~@@@@I~
            break;                                                 //~@@@@I~
        case CASE_SETITEMCHECKED:                                  //~v105I~//~@@@@R~
            setItemCheckedUI(Pparm);                               //~v105I~//~@@@@R~
            break;                                                 //~v105I~//~@@@@R~
        case CASE_APPENDSPAN:                                      //~v101I~
            appendSpanUI(Pparm);                                   //~v101I~
            break;                                                 //~v101I~
        case CASE_SETCHECKED_TB:                                   //~1A00R~
            setChecked_TBUI(Pparm);                                //~1A00R~
            break;                                                 //~1A00I~
        case CASE_SETBUTTONDRAWABLE:                               //~1A00I~
            setButtonDrawableUI(Pparm);                            //~1A00I~
            break;                                                 //~1A00I~
        case CASE_SCROLL:                                          //+1A4vI~
            scrollToUI(Pparm);                                     //+1A4vI~
            break;                                                 //+1A4vI~
        }                                                          //~1221I~
    }                                                              //~1221I~
//*****************                                                //~1221I~
    public void append(TextView Ptextview,ScrollView Pscrollview,String Pline,Color Pcolor)//~1221R~//~@@@@R~
    {                                                              //~1221I~//~@@@@R~
        line=Pline;                                                //~1221I~//~@@@@R~
        color=Pcolor;                                              //~1221I~//~@@@@R~
        scrollview=Pscrollview;                                    //~1221I~//~@@@@R~
        runOnUiThread(CASE_APPEND,Ptextview);                      //~1221I~//~@@@@R~
    }                                                              //~1221I~//~@@@@R~
    private void appendUI(Object Pparm)                            //~1221I~//~@@@@R~
    {                                                              //~1221I~//~@@@@R~
        TextView textview=(TextView)Pparm;                         //~1221I~//~@@@@R~
        textview.append(line);                                     //~1221I~//~@@@@R~
        if (color!=null)                                           //~1221I~//~@@@@R~
        {                                                          //~1221I~//~@@@@R~
            textview.setTextColor(color.getRGB());                 //~1221I~//~@@@@R~
        }                                                          //~1221I~//~@@@@R~
        if (scrollview!=null)                                     //~1221I~//~@@@@R~
        {                                                          //~1221I~//~@@@@R~
            scrollview.scrollTo(0/*x*/,32760/*y:Bottom*/);         //~1221I~//~@@@@R~
        }                                                          //~1221I~//~@@@@R~
    }                                                              //~1221I~//~@@@@R~
//*****************                                                //~v101I~
    public void append(TextView Ptextview,ScrollView Pscrollview,SpannableString Pspanline)//~v101I~
    {                                                              //~v101I~
        spanline=Pspanline;                                        //~v101I~
        scrollview=Pscrollview;                                    //~v101I~
        runOnUiThread(CASE_APPENDSPAN,Ptextview);                  //~v101I~
    }                                                              //~v101I~
//*****************                                                //~v101I~
    private void appendSpanUI(Object Pparm)                        //~v101I~
    {                                                              //~v101I~
    	if (Dump.Y)Dump.println("TextComponent appendSpanUI"); 
    	TextView textview=(TextView)Pparm;                         //~v101I~
        textview.append(spanline);                                 //~v101I~
        if (scrollview!=null)                                      //~v101I~
        {                                                          //~v101I~
            scrollview.scrollTo(0/*x*/,32760/*y:Bottom*/);         //~v101I~
        }                                                          //~v101I~
    }                                                              //~v101I~
//*****************                                                //~1A00I~
    public void setChecked(ToggleButton Pview,boolean Pstate)   //~1A00I~
    {                                                              //~1A00I~
        buttonState=Pstate;                                        //~1A00I~
        runOnUiThread(CASE_SETCHECKED_TB,Pview);                       //~1A00I~
    }                                                              //~1A00I~
//*****************                                                //~1A00I~
    public void setButtonDrawable(CompoundButton Pview,int Presid,int Pvisible)//~1A00I~
    {                                                              //~1A00I~
        visibility=Pvisible;                                       //~1A00I~
        iconresid=Presid;                                          //~1A00I~
        runOnUiThread(CASE_SETBUTTONDRAWABLE,Pview);               //~1A00I~
    }                                                              //~1A00I~
//*****************                                                //~1A00I~
    public void setButtonDrawableUI(Object Pparm)                  //~1A00I~
    {                                                              //~1A00I~
    	if (Dump.Y)Dump.println("setButtonDrawable resid="+Integer.toHexString(iconresid)+",visibility="+visibility);//~1A00I~
    	android.widget.CompoundButton view=(android.widget.CompoundButton)Pparm;                                 //~1A00I~
        view.setButtonDrawable(iconresid);                             //~1A00I~
        view.setVisibility(visibility);                               //~1A00I~
    }                                                              //~1A00I~
//*****************                                                //~1A00I~
    private void setChecked_TBUI(Object Pparm)                     //~1A00R~
    {                                                              //~1A00I~
    	if (Dump.Y)Dump.println("ToggleButton setCheckedUI="+buttonState);//~1A00I~
    	ToggleButton view=(ToggleButton)Pparm;                     //~1A00I~
        view.setChecked(buttonState);                              //~1A00I~
    }                                                              //~1A00I~
//*****************                                                //~1221I~
    public void setText(TextView Ptextview,String Pline)           //~1221I~
    {                                                              //~1221I~
    	line=Pline;                                                //~1221I~
    	spanline=null;                                             //~1A00I~
    	runOnUiThread(CASE_SETTEXT,Ptextview);                //~1221I~
    }                                                              //~1221I~
//*****************                                                //~1A00I~
    public void setText(TextView Ptextview,SpannableString Pline)  //~1A00I~
    {                                                              //~1A00I~
    	line=null;                                                 //~1A00I~
    	spanline=Pline;                                            //~1A00I~
    	runOnUiThread(CASE_SETTEXT,Ptextview);                     //~1A00I~
    }                                                              //~1A00I~
//*****************                                                //~1A00I~
    private void setTextUI(Object Pparm)                           //~1221I~
    {                                                              //~1221I~
    	TextView textview=(TextView)Pparm;                         //~1221I~
      if (line!=null)                                              //~1A00I~
        textview.setText(line);                                    //~1221I~
      else                                                         //~1A00I~
      {                                                            //~1A00I~
      	if (Dump.Y) Dump.println("component:setTextUI spannable"); //~1A00I~
        textview.setText(spanline);                                //~1A00I~
      }                                                            //~1A00I~
    	spanline=null;                                             //~1A00I~
    }                                                              //~1221I~
//*****************                                                //~1221I~
    public void showList(ListView Plistview,int Ppos)            //~1221R~//~@@@@R~
    {                                                              //~1221I~//~@@@@R~
        if (Dump.Y) Dump.println("Component:showBottom pos="+pos); //~1506R~//~@@@@R~
        pos=Ppos;                                                  //~1221I~//~@@@@R~
        runOnUiThread(CASE_SHOWBOTTOM,Plistview);                  //~1221I~//~@@@@R~
    }                                                              //~1221I~//~@@@@R~
    private void showBottomUI(Object Pparm)                        //~1221I~//~@@@@R~
    {                                                              //~1221I~//~@@@@R~
        if (Dump.Y) Dump.println("Component:showBottomUI pos="+pos);//~1506R~//~@@@@R~
        ListView listview=(ListView)Pparm;                         //~1221I~//~@@@@R~
        ListAdapter adapter=listview.getAdapter();                 //~1221I~//~@@@@R~
        ((BaseAdapter)adapter).notifyDataSetChanged();             //~@@@@R~
        if (pos<0) //keep currenttop                               //~@@@@R~
            pos=listview.getFirstVisiblePosition();//~1221R~       //~@@@@R~
        listview.setSelectionFromTop(pos,0);                       //~1221I~//~@@@@R~
    }                                                              //~1221I~//~@@@@R~
//*****************                                                //~v105I~
    public void setItemChecked(ListView Plistview,int Ppos,boolean Pstate)//~v105I~//~@@@@R~
    {                                                              //~v105I~//~@@@@R~
        if (Dump.Y) Dump.println("Component:setItemChecked pos="+Ppos);//~v105I~//~@@@@R~
        pos=Ppos;                                                  //~v105I~//~@@@@R~
        listItemState=Pstate;                                      //~v105I~//~@@@@R~
        runOnUiThread(CASE_SETITEMCHECKED,Plistview);              //~v105I~//~@@@@R~
    }                                                              //~v105I~//~@@@@R~
    private void setItemCheckedUI(Object Pparm)                    //~v105I~//~@@@@R~
    {                                                              //~v105I~//~@@@@R~
        if (Dump.Y) Dump.println("Component:setItemChecked pos="+pos);//~v105I~//~@@@@R~
        ListView listview=(ListView)Pparm;                         //~v105I~//~@@@@R~
        listview.setItemChecked(pos,listItemState);                //~v105I~//~@@@@R~
    }                                                              //~v105I~//~@@@@R~
//*****************                                                //~1221I~
    public void setTitle(String Ptitle)                            //~1221I~
    {                                                              //~1221I~
    	runOnUiThread(CASE_TITLE,Ptitle);                          //~1221I~
    }                                                              //~1221I~
    private void setTitleUI(Object Pparm)                          //~1221I~
    {                                                              //~1221I~
	    AG.activity.setTitle((String)Pparm);                       //~1221I~
    }                                                              //~1221I~
//************************                                         //~1417I~
//*from CloseFrame:seticon, through Hashtable                      //~1417I~
//************************                                         //~1417I~
    public void setIconImage(Image Pimage)                         //~1417I~
    {                                                              //~1417I~
    	this.seticonImage(Pimage.iconFilename);                    //~1417R~
    }                                                              //~1417I~
//************************                                                //~1221I~//~1312R~
//*set title bar  icon                                             //~1312I~
//************************                                         //~1312I~
    public void seticonImage(String Pfilename)                     //~1417R~
    {                                                              //~1312I~
    	int resid;                                                 //~1326I~
    	if (Pfilename==null)                             //~1312I~ //~1326R~
        	resid=iconresid;	//restore                          //~1326I~
        else                                                       //~1326I~
    		resid=AG.findIconId(Pfilename);                             //~1312I~//~1326R~
        if (resid<=0)                                              //~1312I~
        	return;                                                //~1312I~
        iconresid=resid;                                           //~1313I~
    	runOnUiThread(CASE_SETICON,null);                          //~1313I~
    }                                                              //~1312I~
    public void setIconImageUI(Object Pparm)                       //~1417R~
    {                                                              //~1313I~
    	android.view.Window w; 
                   //~0914R~//~0915R~//~0A09R~//~1312I~//~1329R~//~1331I~
    	w=AG.activity.getWindow();                                 //~1313I~
    	w.setFeatureDrawableResource(android.view.Window.FEATURE_LEFT_ICON,iconresid);//~1313I~
    }                                                              //~1313I~
//*************************                                        //~1322I~
//*IconBar Button                                                  //~1322I~
//************************                                         //~1322I~
//*setEnabled requires Mainthread*                                 //~1425I~
    public void setEnabled(Button Pbutton,boolean Pstate)          //~1322I~//~@@@@R~
    {                                                              //~1322I~//~@@@@R~
        if (Dump.Y) Dump.println("Component setenabled="+Pbutton.toString());//~1506R~//~@@@@R~
        buttonState=Pstate;                                        //~1322I~//~@@@@R~
        runOnUiThread(CASE_ENABLE,Pbutton);                        //~1425R~//~@@@@R~
    }                                                              //~1322I~//~@@@@R~
    private void setEnabledUI(Object Pparm)                           //~1322I~//~@@@@R~
    {                                                              //~1322I~//~@@@@R~
        Button button=(Button)Pparm;                               //~1322I~//~@@@@R~
        button.setEnabled(buttonState);                            //~1322I~//~@@@@R~
    }                                                              //~1322I~//~@@@@R~
//*****************                                                //~1312I~
//    public void setChecked(RadioButton Pbutton,boolean Pstate)     //~1322I~//~2B13R~
//    {                                                              //~1322I~//~2B13R~
//        buttonState=Pstate;                                        //~1322I~//~2B13R~
//        runOnUiThread(CASE_SETCHECK,Pbutton);                      //~1322I~//~2B13R~
//    }                                                              //~1322I~//~2B13R~
//************                                                     //~2B13I~
    public void setChecked(android.widget.Button Pbutton,int PdrawableId)//Iconbar Toggle button//~2B13R~
    {                                                              //~2B13I~
    	if (PdrawableId==0)                                        //~2B13I~
        	return;                                                //~2B13I~
    	drawableResourceId=PdrawableId;                            //~2B13I~
    	runOnUiThread(CASE_SETCHECK,Pbutton);                      //~2B13I~
    }                                                              //~2B13I~
    private void setCheckedUI(Object Pparm)                          //~1322I~
    {                                                              //~1322I~
//  	RadioButton button=(RadioButton)Pparm;                     //~1322I~//~2B13R~
//      button.setChecked(buttonState);                            //~1322I~//~2B13R~
	    android.widget.Button button=(android.widget.Button)Pparm; //~2B13I~
        button.setBackgroundResource(drawableResourceId);          //~2B13I~
    }                                                              //~1322I~
//*****************                                                //~@@@@I~
    private void setVisibilityUI(Object Pparm)                       //~@@@@I~
    {                                                              //~@@@@I~
	    View v=(View)Pparm;                                        //~@@@@I~
        v.setVisibility(visibility);                               //~@@@@I~
    }                                                              //~@@@@I~
//*****************                                                //~@@@@I~
    private void setImageBitmapUI(Object Pparm)                      //~@@@@I~
    {                                                              //~@@@@I~
	    ImageView v=(ImageView)Pparm;                              //~@@@@I~
        if (imagebgcolor!=null)                                    //~@@@@I~
        	v.setBackgroundColor(imagebgcolor.getRGB());           //~@@@@I~
        v.setImageBitmap(bitmap);                                  //~@@@@I~
        v.setVisibility(imagevisibility);                          //~@@@@I~
    }                                                              //~@@@@I~
//*****************                                                //~1322I~
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
//ImageObserver Interface for EmptyPaint:save()
		return false;
	}
//*****************                                                //~1A2dI~
	public static void setVisible(View Pview,int Pvisible)         //~1A2dI~
    {                                                              //~1A2dI~
		(new Component()).setVisibility(Pview,Pvisible);           //~1A2dI~
	}                                                              //~1A2dI~
}//class                                                           //~1112I~
