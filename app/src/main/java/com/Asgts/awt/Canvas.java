//*CID://+1Ai4R~: update#= 508;                                    //+1Ai4R~
//**********************************************************************//~1107I~
//1Ai4 2016/12/29 OutOfMemoy at ImageCanvas:onDraw bm.copy()       //+1Ai4I~
//1Ai3 2016/12/29 CastException,reason is unknown but chk it.      //~1Ai3I~
//1Ahd 2016/11/23 onDraw corruption;onDraw,use copy for drawBitmap. it is good now.//~1AhdI~
//1Ahc 2016/11/23 onDraw corruption;try specify density same as display//~1AhaI~
//1Aha 2016/11/19 add option trace X for special debug in notrace mode//~1AhaI~
//1Ah5 2016/11/09 dumplicated onDraw when one touch mode           //~1Ah5I~
//1Ah0 2016/10/15 v1B7 2014/08/11 display komi for "local count";(stackoverflowerror may occur if no stone set)//~v1B7I~//~1Ah0I~
//1Ag9 2016/10/09 (Ajagoc)Bitmap OutOfMemory;JNI Global reference remains..java//~1Ag9I~
//                try to clear ref to bitmap from Image:fieldBitmap, Graphics:targetBitmap, android.Graphics.Canvas(<--Image:androidCanvas, Graphics:androidCanvas)//~1Ag9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing//~1A6AI~
//1A4h 2014/12/03 catch OutOfMemory                                //~1A4hI~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A17 2013/03/12 slide for piece remove on freeboard              //~1A17I~
//1A13 2013/03/10 1touch option                                    //~1A13R~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A02 2013/02/16 recycle image for mainframe after drawn          //~1A02I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//1073:121207 NPE at Canvas:enqRequest                             //~v107I~
//v101:120514 (Axe)android3(honeycomb) tablet has System bar at bottom that hide xe button line with 48pix height//~v101I~
//**********************************************************************//~v101I~
//*Canvas for Board                                                //~1225R~
//*  AWT APP:repaint()-->( update() )-->paint()                    //~1225I~
//**********************************************************************//~1107I~
package com.Asgts.awt;                                         //~1107R~  //~1108R~//~1109R~//~1117R~//~@@@@R~//~v101R~

import java.util.ArrayList;
import java.util.LinkedList;                                       //~1420I~
                                                                   


//~1420I~
import jagoclient.Dump;
import jagoclient.StopThread;
import jagoclient.board.Board;
import jagoclient.board.ConnectedBoard;
import jagoclient.board.BoardInterface;
import jagoclient.board.GoFrame;
import jagoclient.FreeGoFrame;
import jagoclient.gui.ActionTranslator;
import jagoclient.gui.BigLabel;
import jagoclient.gui.DoActionListener;

import com.Asgts.AG;                                                //~@@@@R~//~v101R~
import com.Asgts.AKey;                                              //~@@@@R~//~v101R~
import com.Asgts.AKeyI;                                       //~1401I~//~@@@@R~//~v101R~
import com.Asgts.UiThreadI;                                         //~@@@@R~//~v101R~
import com.Asgts.Utils;                                             //~@@@@R~//~v101R~
import com.Asgts.AView;                                             //~@@@@R~//~v101R~
import com.Asgts.R;                                                 //~@@@@R~//~v101R~
import com.Asgts.awt.Dimension;                                     //~@@@@R~//~v101R~
import com.Asgts.awt.Image;                                         //~@@@@R~//~v101R~
import com.Asgts.awt.KeyListener;                                 //~1117I~//~@@@@R~//~v101R~
import com.Asgts.awt.FontMetrics;                                   //~@@@@R~//~v101R~
import com.Asgts.awt.Component;                                     //~@@@@R~//~v101R~



import com.Asgts.gtp.GtpFrame;
import com.Asgts.gtp.GtpGoFrame;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Canvas extends Component	//for createwood(Component:Board)//~1120R~//~1121R~
        implements UiThreadI                                  //~1425R~//~@@@@R~
{                                                                  //~0914I~
	private static final String ACTION_SETSTONE="SetStone";        //~1424R~
	private static final int ENQ_OVERFLOW_LIMIT=5;                 //~1427R~
    private static int SswipeLimit=200;	//ms for square size       //~1A17R~
	private Graphics graphics;		//Ajagoc.Graphic               //~1504R~
	private Font font;                                             //~1421R~
	private ImageCanvas imageCanvas;                               //~1421R~
    private int boardsz;                                               //~1221I~                        //~1227I~
    public Image 	boardImageCopy;                                //~1421R~
    public Image 	activeImage;                                   //~1421I~
    public boolean  drawBoardImageCopy;                            //~1421R~
    public Rect     boardRect;                                     //~1421I~
    private KeyListener boardKeyListener_a,boardKeyListener_b;     //~1503R~
    private MouseListener boardMouseListener;                      //~1421R~
    private MouseMotionListener boardMouseMotionListener;          //~1421R~//~@@@@R~
    public int paternSize,boxSize,boardOrigin,boardEdge;           //~1419R~
    private int curPosX,curPosY;                                   //~1421R~
//    private static final int cursorBitmapSize=6;                   //~1318R~//~@@@2R~
//    private static final int cursorBitmapSize2=cursorBitmapSize/2;  //~1317I~
    protected boolean swPainting=false;                              //~1318I~
	private	Paint cursorPaint=new Paint(Paint.ANTI_ALIAS_FLAG);    //~1317I~
//  private	static final Color cursorColor=Color.white;            //~1318R~                                                //~1415I~//~@@@2R~
	BoardSync boardSync;                                           //~1421I~
    Board  board;                                                  //~1422I~
    private boolean	swCloseRequested;                              //~1424I~
    public static long trackball_oldmovetime;                      //~1426R~
    public static float trackball_accumX,trackball_accumY;         //~1426R~
    protected Frame canvasFrame;                                     //~1503I~
    private static boolean SshortcutDirectionKey=false;                  //~1507R~//~@@@2R~
    private boolean swInactive;                                    //~@@@2I~
	private ArrayList<Bitmap> recycleMyStack;                      //~@@@2I~
    public boolean swWoodPainted;                                  //~1A02I~
    private boolean swMainFrameDrawn;                              //~1A02I~
    public boolean swFreeBoardCanvas;                             //~1A10I~
    public boolean swFreeBoardStarted;                            //~1A10I~
    public Button buttonSetStone;                                  //~1A2dI~
    public boolean swReplayBoardReplay;                            //~1A2dI~
    public boolean swStartTimer,swStartTimerDone;                  //~1Ah0R~
    private GtpFrame gtpFrame;                                     //~1Ah5I~
    private int ctrOnDraw;                                         //~1Ah5I~
//******************************                                   //~1217I~
//  public Canvas()                                                //~1117R~//~v101R~
	public Canvas(BoardInterface Pboardinterface)                            //~v101I~
    {                                                              //~1217R~
    //************************                                     //~1221I~
//  	canvasFrame=AG.currentFrame;                               //~1503R~//~v101R~
    	canvasFrame=(Frame)Pboardinterface;                             //~v101I~
        if (Dump.C) Dump.println("Canvas:currentFrame="+canvasFrame.toString());//~@@@2I~//~1A6AR~
        swFreeBoardCanvas=(Pboardinterface instanceof FreeGoFrame);          //~1A10I~
        swInactive=(canvasFrame==AG.mainframe);                    //~@@@2R~
    	canvasFrame.setFrameType(this);	//for UI thread ctl        //~1503I~
//        FrameLayout layout=(FrameLayout)AG.findViewById(AG.viewId_BoardPanel);//~1217I~//~1221M~//~v101R~
        FrameLayout layout=(FrameLayout)canvasFrame.findViewById(AG.viewId_BoardPanel);//~v101I~
        if (Dump.C) Dump.println("Canvas:framelayout="+layout.toString());//~1506R~//~1A6AR~
		boardsz=getBoardSize();                                    //~1414R~
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(boardsz,boardsz);//~1221I~
        boardImageCopy=createImage(boardsz,boardsz);               //~1421I~
        if (boardImageCopy==null)                                  //~1A4hI~
        {                                                          //~1A4hI~
            return;                                                //~1A4hI~
        }                                                          //~1A4hI~
        if (Dump.C) Dump.println("Canvas:init boardImageCopy="+boardImageCopy.toString());//~1506R~//~1A6AR~
        graphics=boardImageCopy.getGraphics();                          //~1421I~
        AG.gBoardImage=graphics;	//@@@X                             //~1AhaI~
        AG.ajagocCanvas=this;	//@@@X                             //~1AhaI~
        if (Dump.X) Dump.println("X:Canvas:init boardImageCopy graphics="+graphics.toString()+",bitmap="+boardImageCopy.bitmap+",density="+boardImageCopy.bitmap.getDensity());//~1AhaR~
        graphics.setCanvas(this);	//request callback drawBitmap()//~1421I~
                                                                   //~1421I~
        imageCanvas=new ImageCanvas(this);                         //~1226R~
        componentView=imageCanvas;	//for requestFocus()           //~1420I~
        if (Dump.C) Dump.println("Canvas:imageCanvas="+imageCanvas.toString());//~1506R~//~1A6AR~
        layout.addView(imageCanvas,lp);                                //~1118M~//~1121R~//~1217I~//~1226R~
        initImageView();                                           //~1217I~
        if (Dump.C) Dump.println("Canvas:graphics="+graphics.toString());//~1506R~//~1A6AR~
        cursorPaint.setColor(AG.cursorColor.getRGB());                //~1318R~//~@@@2R~
        cursorPaint.setStyle(Style.STROKE);                        //~@@@2I~
        cursorPaint.setStrokeWidth(2.0f);                     //~@@@2I~
	    boardSync=new BoardSync(this);                             //~1422R~
//        SshortcutDirectionKey=Global.getParameter(AMenu.DIRECTIONKEY_CFGKEY,false);//~1507R~//~@@@@R~//~@@@2R~
        requestFocus();	//avoid full ondraw at first touch after returned gamequestion for localgoframe//~@@@2R~
        if (Dump.C) Dump.println("Canvas:oncreate requestFocus end");//~@@@2I~//~1A6AR~
    }                                                              //~1118M~//~1121R~//~1217I~
//******************                                               //~1507I~
//*from Ajago option menu                                          //~1507I~
//******************                                               //~1507I~
	public static void optionChanged(boolean Pflag)                //~1507I~
    {                                                              //~1507I~
//        SshortcutDirectionKey=Pflag;                               //~1507I~//~@@@2R~
    }                                                              //~1507I~
//******************                                               //~1507I~
	public static int getBoardSize()		                       //~1504R~
    {                                                              //~1414I~
    	int boardsz;                                             //~1414I~
        if (AG.portrait)                                           //~1414I~
        	boardsz=AG.scrWidth;                                   //~1414I~
        else                                                       //~1414I~
        {                                                          //~1414I~
//          boardsz=AG.scrHeight-AView.getFramePosition();   //remaining of titlebar hight//~1502I~//~v101R~//~@@@@R~
            boardsz=AG.scrHeight-AView.getMargin();   //remaining of titlebar hight//~v101I~//~@@@@R~
        }                                                          //~1414I~
	    if (Dump.C) Dump.println("Canvas:getBoardSize boardsize="+boardsz);//~1506R~//~1A6AR~
        return boardsz;                                          //~1414I~
    }                                                              //~1414I~
//******************
	public Dimension getSize()                                     //~1117I~
    {                                                              //~1117I~
      	return new Dimension(boardsz,boardsz);                     //~1221I~
    }                                                              //~1117I~
//******************************************                       //~1216I~
    private void initImageView()
    {
    	if (Dump.C) Dump.println("init Canvas ImageView id="+Integer.toString(imageCanvas.getId(),16));//~1506R~//~1A6AR~
		imageCanvas.setWillNotDraw(false);	//reset default "don't call onDraw"//~1226R~
       	imageCanvas.setFocusableInTouchMode(true);                 //~1226R~
       	imageCanvas.setClickable(true);                            //~1426I~
    }
//******************************************                       //~1216I~
    public void addKeyListener(KeyListener Pkl)                    //~1116I~//~1117M~
    {                                                              //~1116I~//~1117M~                                     //~1216I~
        if (imageCanvas==null)  //when outofmemory                 //~1A4hI~
            return;                                                //~1A4hI~
        AKey akl=AKey.addKeyListener(imageCanvas);                   //~1116I~//~1226R~//~@@@@R~
        akl.setKeyListener(imageCanvas);                                   //~1116I~//~1317R~
        if (boardKeyListener_a==null)                              //~1503I~
        {                                                          //~1503I~
        	boardKeyListener_a=Pkl;                                //~1503R~
    	    if (Dump.C) Dump.println("Canvas:addKLeyListenr_a"+Pkl.toString());//~1506R~//~@@@2R~//~1A6AR~
        }                                                          //~1503I~
        else                                                       //~1503I~
        {                                                          //~1503I~
        	boardKeyListener_b=Pkl;       //call a then callb      //~1503I~
    	    if (Dump.C) Dump.println("CAnvas:addKLeyListenr_b"+Pkl.toString());//~1506R~//~1A6AR~
        }                                                          //~1503I~
    }                                                              //~1116I~//~1117M~
//******************************************                       //~1216I~
    public void addMouseListener(MouseListener Pml)                //~1216I~
    {                                                              //~1216I~
        boardMouseListener=Pml;                                    //~1317I~
        board=(Board)Pml;                                          //~1422I~
    }                                                              //~1216I~
//******************************************                       //~1216I~
    public void addMouseMotionListener(MouseMotionListener Pmml)   //~1216I~
    {                                                              //~1216I~
        boardMouseMotionListener=Pmml;                             //~1413I~
    }                                                              //~1216I~
    private static int repaintctr;                                             //~1425I~
    public void repaint()                                          //~1117I~
    {                                                              //~1117I~
        if (Dump.C) Dump.println("Canvas:repaint ctr="+repaintctr);//~1506R~//~1A6AR~
        if (imageCanvas!=null)                                     //~1313I~
        {                                                          //~1Ah0I~
	        if (Dump.C) Dump.println("Canvas:imageCanvas repaint");//~1Ah0I~
        	imageCanvas.repaint();                                 //~1313I~
        }                                                          //~1Ah0I~
    }                                                              //~1117I~
    public void repaintBitmap(Image Pimage)	//from Graphics.drawImage();Board.paint()/copy() updated screen(BoardImageCopy)//~1421R~
    {                                                              //~1421I~
	    drawBoardImageCopy=true;                                   //~1421I~
	    if (Dump.X) Dump.println("X:Canvas repaintBitmap set drawBoardImageCopy=true");//~@@@2R~//~1A6AR~//~1AhaR~
//      System.out.println("Canvas repaintBitmap set drawBoardImageCopy=true");//~1A02I~//~1A17R~
        activeImage=Pimage;                                        //~1421I~
        repaint();				//invalidate                       //~1421I~
    }                                                              //~1421I~
    public Image createImage(int Pw,int Ph) //OffscreenCanvas,ActiveImage,Empty,EmptyShadow//~1422R~
    {                                                              //~1117I~
	    if (Dump.C) Dump.println("Canvas createImage Board("+Pw+","+Ph+")");//~1506R~//~1A6AR~
      try                                                          //~1A4hI~
      {                                                            //~1A4hI~
    	Image image=Image.createImage(Pw,Ph,this);	//board        //~1415R~
//        parentWindow.recyclePrepare(image.bitmap);  //recycle at window close//~1422R~//~@@@2R~
        recycleMyStack=Window.recycleMyPrepare(image.bitmap,recycleMyStack);  //add to stack after created//~@@@2I~
        return image;                                              //~1415I~
      }                                                            //~1A4hI~
      catch(OutOfMemoryError me)                                   //~1A4hI~
      {                                                            //~1A4hI~
		Dump.println(me,"Canvas:createImage:OffScreen");           //~1A4hI~
      }                                                            //~1A4hI~
      return null;                                                 //~1A4hI~
    }                                                              //~1117I~
//*stone is by loadeImage                                          //~@@@2I~
//    public Image createImage(MemoryImageSource Pmis) //WhiteStone,BlackStone//~1422R~//~@@@2R~
//    {                                                              //~1120I~//~@@@2R~
//        if (Dump.C) Dump.println("Canvas createImage ImageSrc="+((Object)Pmis).toString());//~1506R~//~@@@2R~//~1A6AR~
//        Image image=Image.createImage(Pmis,this);//stone paint     //~1415R~//~@@@2R~
////        parentWindow.recyclePrepare(image.bitmap);  //recycle at window close//~1422R~//~@@@2R~
//        recycleMyStack=Window.recycleMyPrepare(image.bitmap,recycleMyStack);  //add to stack after created//~@@@2R~
//        return image;                                              //~1415I~//~@@@2R~
//    }                                                              //~1120I~//~@@@2R~
    public static FontMetrics getFontMetrics(Font Pfont)                  //~1504R~
    {                                                              //~1117M~
        Paint painttext=new Paint(Paint.ANTI_ALIAS_FLAG);          //~1117M~
        painttext.setTypeface(Pfont.getTypefaceStyle());           //~1117M~
        painttext.setTextSize(Pfont.getSize());                    //~1117M~
        return new FontMetrics(Pfont,painttext.getFontMetrics());        //~1117M~
    }                                                              //~1117M~
    public Graphics getGraphics()                                  //~1504R~
    {                                                              //~1120I~
        if (Dump.C) Dump.println("Canvas:getGraphic this="+this.toString()+",G="+graphics.toString());//~1506R~//~1A6AR~
    	return graphics;	//foreground canvas                    //~1224R~
    }                                                              //~1120I~
    public void dispose()                                          //~1226I~
    {                                                              //~1226I~
        if (Dump.C) Dump.println("Canvas:dispose()");              //~1506R~//~1A6AR~
    }                                                              //~1226I~
//*************************************                            //~1212R~
//*for TextDisplay ********************                            //~1212I~
//*************************************                            //~1212I~
//Canvas.getToolkit().getSystemClipboard()-->Clipboard
//    public void addComponentListener(ComponentListener l)          //~1212I~//~@@@@R~
//    {                                                              //~1212I~//~@@@@R~
//    }                                                              //~1212I~//~@@@@R~
    public void setFont(Font Pfont)                                //~1213I~
    {                                                              //~1213I~
    	font=Pfont;                                                //~1213I~
    }                                                              //~1213I~
    public Font getFont()                                //~1213I~
    {                                                              //~1213I~
    	return font;                                               //~1213I~
    }                                                              //~1213I~
//*************************************                            //~1317I~
    public void setGeometry(int PpaternSize,int PboxSize,int PboardOrigin)//~1317I~
    {                                                              //~1317I~
    	paternSize=PpaternSize;                                    //~1317I~
    	boxSize=PboxSize;                                          //~1317I~
    	boardOrigin=PboardOrigin;                                  //~1317I~
    	boardEdge=PboardOrigin+boxSize*(paternSize-1);             //~1419I~
        curPosX=paternSize/2;  //initialy cursor set to center     //~1318I~
        curPosY=paternSize/2;                                      //~1318I~
        if (Dump.C) Dump.println("Canvas:geometry origin="+PboardOrigin);//~1506R~//~1A6AR~
    }                                                              //~1317I~
//**********************************************************       //~1422I~
//*From Board:set recycle for update old Image Pointer             //~1422I~
//**********************************************************       //~1422I~
    public void recycle(Image Pimage)                             //~1422I~
    {                                                              //~1422I~
    	if (Pimage!=null)                                          //~1422I~
        {                                                          //~1A02I~
        	if (Dump.C) Dump.println("canvas:recycle");            //~1A02I~//~1A6AR~
    		Pimage.recycle();                                      //~1422I~
        }                                                          //~1A02I~
    }                                                              //~1422I~
//**********************************************************       //~1513I~
//*From biglabelPanel                                              //~1513I~
//**********************************************************       //~1513I~
    public void drawBigLabel(BigLabel Plabel,Graphics Pgraphics)   //~1513I~
    {                                                              //~1513I~
        enqRequest(new BoardRequest(BOARD_DRAW_BIGLABEL,Plabel,Pgraphics));//~1513I~
    }                                                              //~1513I~
//**********************************************************       //~@@@2I~
//*From ConnectedBoard                                             //~@@@2I~
//**********************************************************       //~@@@2I~
    public void drawCaptured(int Premains)                         //~@@@2R~
    {                                                              //~@@@2I~
        enqRequest(new BoardRequest(BOARD_DRAW_CAPTURED,new Integer(Premains),null));//~@@@2R~
    }                                                              //~@@@2I~

//**********************************************************       //~v1B7I~//~1Ah0I~
//*From GTPGoFrame                                                 //~v1B7I~//~1Ah0I~
//**********************************************************       //~v1B7I~//~1Ah0I~
    public void requestCallback(GoFrame Pgoframe,Object Pparm)     //~v1B7R~//~1Ah0I~
    {                                                              //~v1B7I~//~1Ah0I~
        enqRequest(new BoardRequest(BOARD_CALLBACK,Pgoframe,Pparm));//~v1B7R~//~1Ah0I~
    }                                                              //~v1B7I~//~1Ah0I~
//**********************************************************       //~1Ah0I~
//*From GtpFrame                                                   //~1Ah0I~
//**********************************************************       //~1Ah0I~
    public void requestStartTimer(GtpFrame Pgtpf)                  //~1Ah0R~
    {                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("Canvas:requestStartTimer ctrOnDraw="+ctrOnDraw);//~1Ah0R~
    	gtpFrame=Pgtpf;                                            //~1Ah0I~
        swStartTimer=true;                                         //~1Ah0R~
        enqRequest(new BoardRequest(BOARD_START_TIMER,null,null)); //~1Ah0R~
    }                                                              //~1Ah0I~
//**********************************************************       //~1217I~
//*InnerClass                                                      //~1425R~
//**********************************************************       //~1425I~
//**********************************************************       //~1425I~
//*to get android Canvas and get control of onDraw                 //~1425I~
//**********************************************************       //~1425I~
    class ImageCanvas extends ImageView                            //~1216I~//~1217R~
    		implements MouseListener,AKeyI,DoActionListener                   //~1317R~//~1424R~//~@@@@R~
    {                                                              //~1216I~
        private Canvas ajagoCanvas;                                //~1426R~
        private Board  board;                                      //~1426R~
        private Graphics graphics;                                 //~1426R~
    	private static final long TRACKBALL_MOVE_CHKTIME=1000;	//ignore remaining compiled movement value after 1second//~1426R~
    	//************                                                 //~1226I~
    	public ImageCanvas(Canvas Pcanvas)                         //~1216I~
        {   
    		super(AG.context);//~1216I~
            ajagoCanvas=Pcanvas;                                   //~1408I~
            board=(Board)Pcanvas;                                  //~1408I~
            graphics=Pcanvas.graphics;                             //~1408I~
	        initSetStoneButton();                                  //~1424I~
        }                                                          //~1216I~
//**************************************************************************//~1424I~
//* Label Lm is used as Button to setStone(alternative of mouse push)//~1424I~
//**************************************************************************//~1424I~
        private void initSetStoneButton()                          //~1424I~
        {                                                          //~1424I~
            if (Dump.C) Dump.println("init SetStoneButton");       //~1506R~//~1A6AR~
//          Button buttonSetStone=new Button("--");                //~1424I~//~@@@2R~
//        Button buttonSetStone=Button.srchButton(R.id.SetStone);//~@@@2I~//~1A2dR~
          buttonSetStone=Button.srchButton(R.id.SetStone);         //~1A2dI~
          if (buttonSetStone!=null)                                //~@@@2I~
          {                                                        //~@@@2I~
            ActionTranslator at=new ActionTranslator(this,ACTION_SETSTONE);//~1424I~
            buttonSetStone.addActionListener(at);                                //~1424I~
          }                                                        //~@@@2I~
        }                                                          //~1424I~
//*********************                                            //~1424I~
        public void doAction (String o)                            //~1424I~
        {   if (o.equals(ACTION_SETSTONE))                         //~1424I~
            {                                                      //~1424I~
	            if (Dump.C) Dump.println("ImageCAnvas:DoAction:setStone");//~1506R~//~1A6AR~
            	setStone();                                        //~1424I~
            }                                                      //~1424I~
        }                                                          //~1424I~
	    public void itemAction (String o, boolean flag){};         //~1424I~
        //************************************************         //~1313I~
		//*from Board:repaint(), offscreen updated                 //~1421R~
        //************************************************         //~1313I~
    	public void repaint()                                      //~1313I~
        {                                                          //~1313I~
	        if (Dump.C) Dump.println("imageCanvas repaint ctr="+repaintctr+",this="+this.toString());//~1506R~//~1A6AR~
	        if (Dump.X) Dump.println("imageCanvas repaint ctr="+repaintctr+",this="+this.toString());//~1AhaI~
    		if (AG.isMainThread())                                 //~1313I~
            {                                                      //~1313I~
            	this.invalidate();                                 //~1313I~
	            if (Dump.C) Dump.println("ImageCanvds UI Invalidate");//~1506R~//~1A6AR~
            }                                                      //~1313I~
        	else                                                   //~1313I~
            {                                                      //~1313I~
//                if (Dump.X) Dump.println("X:ImageCanvds postInvalidate sleep before");//~1AhaR~
//                Utils.sleep(100);//100ms @@@@test                //~1AhaR~
            	this.postInvalidate();	//call paint(),draw from ActiveImage//~1AhaR~
//                if (Dump.X) Dump.println("X:ImageCanvds prepaint:setImageBitmap");//~1AhaR~
//                AG.activity.runOnUiThread(                       //~1AhaR~
//                    new Runnable(){                              //~1AhaR~
//                                    @Override                    //~1AhaR~
//                                    public void run()            //~1AhaR~
//                                    {                            //~1AhaR~
//                                        if (Dump.X) Dump.println("X:ImageCanvds prepaint:runonuithread setImageBitmap");//~1AhaR~
//                                        setImageBitmap(boardImageCopy.bitmap);  //@@@@test//~1AhaR~
//                                    }                            //~1AhaR~
//                                  }      );                      //~1AhaR~
	            if (Dump.X) Dump.println("X:ImageCanvds postInvalidate");//~1506R~//~1AhaR~
//              ajagoCanvas.drawBoardImageCopy=true;	//@@@@test //~1AhaR~
//              if (Dump.X) Dump.println("X:ImageCanvds postInvalidate#2");//~1AhaR~
//          	this.postInvalidate();	//call paint(),draw from ActiveImage//~1AhaR~
            }                                                      //~1313I~
        }                                                          //~1313I~
//        //************************************************       //~@@@2R~
//        @Override                                                //~@@@2R~
//        protected void onFinishInflate()                         //~@@@2R~
//        {                                                        //~@@@2R~
//            super.onFinishInflate();                             //~@@@2R~
//            if (Dump.C) Dump.println("ImageCanvds onFinishInflate()");//~@@@2R~//~1A6AR~
//        }                                                        //~@@@2R~
        //************************************************         //~1304I~
        @Override                                                      //~0914R~//~1216I~
        protected void onDraw(android.graphics.Canvas Pcanvas)                           //~0914I~//~1117R~//~1216I~
        {                                                          //~1216I~
        	try                                                    //~1430I~
            {                                                      //~1430I~
                if (Dump.C) Dump.println("imageCanvas @onDraw frame current="+AG.currentFrame.toString()+",board frame="+ajagoCanvas.canvasFrame);//~1506R~//~@@@2M~//~1A6AR~
                if (ajagoCanvas.canvasFrame!=AG.currentFrame)   //android canvas is the same for 2 board//~@@@2I~
                	return;                                        //~@@@2I~
                if (Dump.C) Dump.println("imageCanvas @onDraw ctr="+repaintctr+",this="+this.toString());//~1506R~//~1A6AR~
                if (Dump.C) Dump.println("imageCanvas @onDraw system canvas="+Pcanvas.toString());//~1506R~//~1A6AR~
                repaintctr--;                                      //~1430R~
                if (swCloseRequested)   //protect to use recycled Bitmap(boardImageCopy)//~1430R~
                    return;                                        //~1430R~
	            if (swMainFrameDrawn)	//always boardImageCopy for MainFrame after Woodpainted//~1A02I~
                {                                                  //~1A02I~
                	ajagoCanvas.drawBoardImageCopy=true;           //~1A02I~
                	if (Dump.C) Dump.println("imageCanvas @onDraw drawBoardImageCopy set true by MainFrameDrawn");//~1A02I~//~1A6AR~
                }                                                  //~1A02I~
                if (Dump.C) Dump.println("imageCanvas @onDraw drawBoardImageCopy="+ajagoCanvas.drawBoardImageCopy+",ajagoCanvas="+ajagoCanvas.toString());//~@@@2I~//~1A6AR~
                if (!ajagoCanvas.drawBoardImageCopy)                           //~1430R~//~@@@2R~
                {                                                  //~1430R~
	                if (Dump.X) Dump.println("X:imageCanvas @onDraw enq BOARD_PAINT");//~@@@2I~//~1AhaR~
                      enqRequest(new BoardRequest(BOARD_PAINT,graphics,null));//~1430R~
                    return;                                        //~@@@2I~
                }                                                  //~1430R~
                if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy:"+boardImageCopy.toString());//~1506R~//~1A6AR~
                if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy:"+boardImageCopy.bitmap.toString());//~1506R~//~1A6AR~
                ajagoCanvas.drawBoardImageCopy=false;                          //~1503I~//~@@@2R~
                if (Dump.C) Dump.println("imageCanvas @onDraw drawBoardImageCopy set false");//~@@@2R~//~1A6AR~
//              System.out.println("imageCanvas @onDraw drawBoardImageCopy set false");//~1A02I~//~1A17R~
//              if (ajagoCanvas.canvasFrame==AG.currentFrame)   //android canvas is the same for 2 board//~1503I~//~@@@@R~//~@@@2R~
//              {                                                  //~1503I~//~@@@@R~//~@@@2R~
                    if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy.bitmap="+boardImageCopy.bitmap.toString());//~1Ah0I~
                    if (Dump.C) Dump.println("imageCanvas @onDraw Pcanvas="+Pcanvas.toString());//~1Ah0I~
//  		        if (Dump.C) Dump.println("imageCanvas @onDraw boardImageCopy rect="+boardImageCopy.bitmap.getWidth()+","+boardImageCopy.bitmap.getHeight());//~1Ah0R~
//   	   			Rect rect=Pcanvas.getClipBounds();	//@@@@test //~1Ah0R~
//      			if (Dump.C) Dump.println("@onDraw Pcanvas getClipBound rect="+rect.top+"-"+rect.left+","+rect.bottom+"-"+rect.right);//~1Ah0R~
//                  Pcanvas.drawBitmap(boardImageCopy.bitmap,0f,0f,null);//~1503R~//~1AhaR~
                    Pcanvas.setDensity(AG.displayDensity);         //~1AhaM~
                    Bitmap bm=boardImageCopy.bitmap;               //~1AhaI~
        			if (Dump.X) Dump.println("X:Pcanvas @onDraw drawBitmap start color="+board.maincolor()+",bm="+bm.toString()+",canvas.density="+Pcanvas.getDensity());//~1AhaR~
        			if (Dump.X) Dump.println("X:Pcanvas @onDraw density after setdensity canvas.density="+Pcanvas.getDensity());//~1AhaI~
//                    synchronized(bm)                             //~1AhdR~
//                    {                                            //~1AhdR~
//                        Pcanvas.drawBitmap(bm,0f,0f,null);       //~1AhdR~
//                    }                                            //~1AhdR~
                                                                   //~1AhdI~
                    Bitmap b;           //                         //~1AhdR~
        			if (Dump.X) Dump.println("X:Pcanvas @onDraw from bm.copy before copy");//~1AhdI~
                  	synchronized(bm)    //                         //~1AhdR~
                    {                                              //~1AhdR~
                        b=bm.copy(Bitmap.Config.ARGB_8888,true/*mutable*/);//~1AhdR~
                    }                                              //~1AhdR~
        			if (Dump.X) Dump.println("X:Pcanvas @onDraw from bm.copy after copy before drawbitmap bitmap="+b.toString()+",byte="+Image.getByteCount(b));//~1AhdI~
    				Pcanvas.drawBitmap(b,0f,0f,null);              //~1AhdR~
    				b.recycle();                                   //~1AhdR~
                                                                   //~1AhdI~
        			if (Dump.X) Dump.println("X:Pcanvas @onDraw drawBitmap end");//~1AhaR~
                    if (Dump.C) Dump.println("imageCanvas @onDraw BoardImageCopy:drawCursor");//~1506R~//~1A6AR~
                    drawCursor(Pcanvas);                           //~@@@2R~
//              }                                                  //~1503I~//~@@@@R~//~@@@2R~
                if (canvasFrame==AG.mainframe && swWoodPainted)    //~1A02I~
                {                                                  //~1A02I~
	                if (Dump.C) Dump.println("imageCanvas @onDraw MainFrameDrawn");//~1A02I~//~1A6AR~
        			enqRequest(new BoardRequest(BOARD_STOP_MAINFRAME,null,null));//~1A02I~
                	swMainFrameDrawn=true;                         //~1A02M~
                }                                                  //~1A02I~
                if (Dump.C) Dump.println("imageCanvas @onDraw system end drawCtr="+ctrOnDraw);//~1506R~//~1A6AR~//~1Ah5R~
			    ctrOnDraw++;                                       //~1Ah5I~
			    if (swStartTimer && !swStartTimerDone)             //~1Ah0R~
				    requestStartTimer(gtpFrame);                   //~1Ah0R~
                super.onDraw(Pcanvas);                             //~1AhaI~
            }                                                      //~1430I~
			catch(OutOfMemoryError me)                             //+1Ai4R~
      		{                                                      //+1Ai4R~
				Dump.println(me,"Canvas:ImageCanvas:onDraw:bitmap copy");  //Dump issue toast//+1Ai4R~
      		}                                                      //+1Ai4R~
            catch(Exception e)                                     //~1430I~
            {                                                      //~1430I~
                Dump.println(e,"Canvas.OnDraw exception");          //~1430I~//~@@@2R~
            }                                                      //~1430I~
        }                                                              //~0914I~//~1216I~
    //*********                                                    //~1317R~
        @Override                                                  //~1317R~
        public boolean keyPressedRc(KeyEvent e)                      //~1317R~
        {                                                          //~1317R~
        	boolean rc=false;                                            //~1317I~
            if (ajagoCanvas.swInactive)                            //~@@@2R~
            	return false;		//no action                    //~@@@2I~
            int poskeyid=getPositionChangeKeyId(e);                //~1317R~
            if (poskeyid<0)
            {                                                      //~1420I~
	        	enqRequest(new BoardRequest(BOARD_KEY_PRESS,e,null));//~1524R~
            }                                                      //~1420I~
            else                                                   //~1317I~
            	rc=true;                                           //~1317I~
            return rc;                                             //~1317I~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        @Override                                                  //~1317I~
        public boolean keyReleasedRc(KeyEvent e)                       //~1317R~
        {                                                          //~1317R~
            boolean rc=false;
            if (ajagoCanvas.swInactive)                            //~@@@2R~
            	return false;		//no action                    //~@@@2I~
        	int poskeyid=getPositionChangeKeyId(e);                //~1317R~
            if (poskeyid<0)  
            {                                                      //~1503I~
//              ajagoCanvas.boardKeyListener.keyReleased(e);       //~1420R~
            	;   //Booard has no special process for keyReleased//~1503R~
            }                                                      //~1503I~
            else
            {                                                      //~1317R~
            	if (poskeyid==0)                                   //~1317R~
                	setStone();                                    //~1317R~
            	else                                               //~1317R~
                	moveCursor(poskeyid);                          //~1317R~
              	rc=true;                                           //~1317R~
            }
            return rc;                                           //~1317I~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        @Override                                                  //~1317I~
        public void keyTyped (KeyEvent e)                          //~1317R~
        {                                                          //~1317R~
//          ajagoCanvas.boardKeyListener.keyTyped(e);              //~1420R~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        private int getPositionChangeKeyId(KeyEvent e)             //~1317R~
        {                                                          //~1317R~
            int rc=-1;                                             //~1317R~
            int keycode=e.getKeyCode();                            //~1317R~
            if (Dump.C) Dump.println("Canvas:getPositionChangeKeyId keycode="+keycode);//~1506R~//~1A6AR~
            switch(keycode)                                        //~1317R~
            {                                                      //~1317R~
                case KeyEvent.KEY_CENTER:                          //~1430R~
                case KeyEvent.VK_ENTER:                            //~1427I~
                    rc=0;                                          //~1317R~
                    break;                                         //~1317R~
                case KeyEvent.VK_DOWN :                            //~1317R~
                case KeyEvent.VK_UP :                              //~1317R~
                case KeyEvent.VK_LEFT :                            //~1317R~
                case KeyEvent.VK_RIGHT :                           //~1317R~
                	if (SshortcutDirectionKey)	//key used as shortcut on board//~1507I~
	                    break;		//return -1;                   //~1507I~
                    rc=keycode;                                    //~1317R~
                    break;                                         //~1317R~
            }                                                      //~1317R~
            if (Dump.C) Dump.println("Canvas:getPositionChangeKeyId rc="+rc);//~1506R~//~1A6AR~
            return rc;                                             //~1317R~
        }                                                          //~1317R~
    //*********                                                    //~1317R~
        private void moveCursor(int Pposkeyid)                     //~1317R~
        {                                                          //~1317R~
            int xx=curPosX,yy=curPosY;                             //~1317R~
            switch(Pposkeyid)                                      //~1317R~
            {                                                      //~1317R~
            case KeyEvent.VK_DOWN :                                //~1317R~
                yy++;                                              //~1317R~
                if (yy>=paternSize)                                //~1317I~
                {                                                  //~1317I~
                	yy=0;                                          //~1317I~
                    xx++;                                          //~1317I~
                    if (xx>=paternSize)                            //~1317I~
                    	xx=0;                                      //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            case KeyEvent.VK_UP :                                  //~1317R~
                yy--;                                              //~1317R~
                if (yy<0)                                          //~1317I~
                {                                                  //~1317I~
                	yy=paternSize-1;                               //~1317I~
                    xx--;                                          //~1317I~
                    if (xx<0)                                      //~1317I~
                    	xx=paternSize-1;                           //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            case KeyEvent.VK_LEFT :                                //~1317R~
                xx--;                                              //~1317R~
                if (xx<0)                                          //~1317I~
                {                                                  //~1317I~
                	xx=paternSize-1;                               //~1317I~
                	yy--;                                          //~1317I~
                    if (yy<0)                                      //~1317I~
                    	yy=paternSize-1;                           //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            case KeyEvent.VK_RIGHT :                               //~1317R~
                xx++;                                              //~1317R~
                if (xx>=paternSize)                                //~1317I~
                {                                                  //~1317I~
                	xx=0;                                          //~1317I~
                    yy++;                                          //~1317I~
                    if (yy>=paternSize)                            //~1317I~
                    	yy=0;                                      //~1317I~
                }                                                  //~1317I~
                break;                                             //~1317R~
            }                                                      //~1317R~
            curPosX=xx;                                            //~1317R~
            curPosY=yy;                                            //~1317R~
			mouseMoved(xx,yy);	//draw board:LabelM                //~1421I~
        }                                                          //~1317R~
    //*********                                                    //~1318I~
        private boolean moveCursor(int Px,int Py)                  //~1318I~
        {                                                          //~1318I~
            int xx=curPosX,yy=curPosY;                             //~1318I~
        //***************                                          //~1318I~
        	if (Dump.C) Dump.println("moveCursor old="+xx+","+yy+",add="+Px+","+Py);//~1506R~//~1A6AR~
            xx+=Px;                                                //~1318I~
            yy+=Py;                                                //~1318I~
            if (xx<0)                                              //~1318I~
            	xx=0;                                              //~1318I~
            if (yy<0)                                              //~1318I~
            	yy=0;                                              //~1318I~
            if (xx>=paternSize)                                    //~1318I~
            	xx=paternSize-1;                                   //~1318I~
            if (yy>=paternSize)                                    //~1318I~
            	yy=paternSize-1;                                   //~1318I~
        	if (Dump.C) Dump.println("moveCursor new="+xx+","+yy); //~1506R~//~1A6AR~
            if (xx==curPosX && yy==curPosY)                        //~1318I~
                return false;                                      //~1318I~
            curPosX=xx;                                            //~1318I~
            curPosY=yy;                                            //~1318I~
			mouseMoved(xx,yy);	//draw board:LabelM                //~1421I~
            return true;                                           //~1318I~
        }                                                          //~1318I~
        //************                                             //~1317I~
        private void invalidateBoard()                                  //~1317I~
        {                                                          //~1317I~
            if (Dump.C) Dump.println("Canvas:invalidateBoard");    //~1506R~//~1A6AR~
            if (AG.isMainThread())                                 //~1317I~
                this.invalidate();                                 //~1317I~
            else                                                   //~1317I~
            {                                                      //~1AhaI~
	            if (Dump.X) Dump.println("X:ImageCanvds postInvalidate sleep before");//~1AhaI~
//              Utils.sleep(100);//100ms @@@@test                  //~1AhaR~
                this.postInvalidate();                             //~1317I~
            }                                                      //~1AhaI~
        }                                                          //~1317I~
    //*********                                                    //~1317R~//~@@@2R~
        private void drawCursor(android.graphics.Canvas Pcanvas)   //~1317R~//~@@@2R~
        {                                                          //~1317R~//~@@@2R~
            if (Dump.C) Dump.println("Canvas:drawCursor curPosX="+curPosX+",curPosY="+curPosY);//~1506R~//~@@@2R~//~1A6AR~
            drawCursor(Pcanvas,true,curPosX,curPosY);              //~1317R~//~@@@2R~
        }                                                          //~1317R~//~@@@2R~
    //*********                                                    //~1317R~//~@@@2R~
        private boolean drawCursor(android.graphics.Canvas Pcanvas,boolean Pshow,int Pposx,int Pposy)//~1317R~//~@@@2R~
        {                                                          //~1317R~//~@@@2R~
            if (Pposx<0||Pposx>=paternSize)                        //~1317R~//~@@@2R~
                return false;                                      //~1317R~//~@@@2R~
            if (Pposy<0||Pposy>=paternSize)                        //~1317R~//~@@@2R~
                return false;                                      //~1317R~//~@@@2R~
            int xx=boardOrigin+Pposx*boxSize;                      //~1317R~//~@@@2R~
            int yy=boardOrigin+Pposy*boxSize;                      //~1317R~//~@@@2R~
            if (Dump.C) Dump.println("Canvas:drawCursor curPosX="+curPosX+",curPosY="+curPosY);//~1506R~//~@@@2R~//~1A6AR~//~1AhaI~
//          Rect rect=new Rect(xx-cursorBitmapSize2,yy-cursorBitmapSize2,xx+cursorBitmapSize2+1,yy+cursorBitmapSize2+1);//~1407R~//~@@@2R~
            Rect rect=new Rect(xx-boxSize/2+2,yy-boxSize/2+2,xx+boxSize/2-1,yy+boxSize/2-1);//~@@@2R~
            Pcanvas.drawRect(rect,cursorPaint);                    //~1407R~//~@@@2R~
            if (Dump.X) Dump.println("X:Canvas:drawCursor rect="+rect.toString());//~1506R~//~@@@2R~//~1A6AR~//~1AhaR~
            return true;                                           //~1407R~//~@@@2R~
        }                                                          //~1317R~//~@@@2R~
        //********************************************************************//~1A17I~
		private void setStone()                                    //~1317I~
		{                                                          //~1317I~
            int xx=boardOrigin+curPosX*boxSize;                      //~1317I~
            int yy=boardOrigin+curPosY*boxSize;                      //~1317I~
            if (Dump.C) Dump.println("Canvas:setstone xx="+xx+",yy="+yy);//~1506R~//~1A6AR~
        	MouseEvent e=new MouseEvent(xx,yy);                    //~1317I~
	        enqRequest(new BoardRequest(BOARD_MOUSE_UPDOWN,e,null));//~1425I~
        }                                                          //~1317I~
        //********************************************************************//~1A17I~
		private void swipeAction(Point Pdest,int Pposx,int Pposy)      //~1A17R~
		{                                                          //~1A17I~
            if (Dump.C) Dump.println("Canvas:swipe x="+Pdest.x+",y="+Pdest.y+",cposx="+Pposx+",cposy="+Pposy);//~1A17R~//~1A6AR~
        	MouseEvent e=new MouseEvent(Pposx,Pposy);                //~1A17R~
	        enqRequest(new BoardRequest(BOARD_SWIPE,e,Pdest));     //~1A17R~
        }                                                          //~1A17I~
//********************************************************************//~1404R~
//*no setStone by mouse but by key-press because screen is too small//~1404I~
//*******************************************************************//~1404I~
		int touchDownX,touchDownY,touchMoveX,touchMoveY;           //~1430I~
		boolean touchSlide;                                        //~1430I~
        private boolean swSwipe;                                   //~1A17I~
    	private long swipeTimestamp;                               //~1A17R~
    	private int swipeX,swipeY,swipeStartX,swipeStartY;                       //~1A17I~
    	private int[] accumMove=new int[2],accumTime=new int[2];   //~1A17R~
        Point movement=new Point();                                //~1A17I~
        Point swipeDest=new Point();                               //~1A17I~
        @Override                                                  //~1318I~
        public boolean onTouchEvent(MotionEvent event)             //~1318I~
        {                                                          //~1318I~
        	int xx,yy,action,rc;                                   //~1524R~
        //********************                                     //~1318I~
        	try                                                    //~1430I~
            {                                                      //~1430I~
            	if (ajagoCanvas.swInactive)                        //~@@@2R~
                {                                                  //~@@@2I~
                	inactiveWarning();                             //~@@@2I~
                	return false;		//no action                //~@@@2I~
                }                                                  //~@@@2I~
				if (swReplayBoardReplay)	//replay mode          //~1A2dI~
                {                                                  //~1A2dI~
                	inactiveWarning();                             //~1A2dI~
                	return false;		//no action                //~1A2dI~
                }                                                  //~1A2dI~
                action=event.getAction();                          //~1430R~
                xx=(int)event.getX();                              //~1430R~
                yy=(int)event.getY();                              //~1430R~
                if (Dump.C) Dump.println("Canvas:onTouch action="+action+",x="+xx+",y="+yy);//~1506R~//~1A6AR~
                if (action!=MotionEvent.ACTION_OUTSIDE)            //~1430R~
                {                                                  //~1430R~
                    if (action==MotionEvent.ACTION_DOWN)           //~1430R~
                    {                                              //~1430R~
                        touchDownX=xx;                             //~1430R~
                        touchDownY=yy;                             //~1430R~
                        touchSlide=false;                          //~1430R~
            			swipeTimestamp=0;                          //~1A17R~
                        swSwipe=false;                             //~1A17I~
		            	accumMove[0]=0;                            //~1A17R~
		            	accumMove[1]=0;                            //~1A17I~
		            	accumTime[0]=0;                            //~1A17R~
		            	accumTime[1]=0;                            //~1A17I~
                		if (Dump.C) Dump.println("Canvas:onTouch requestFocus");//~@@@2I~//~1A6AR~
                        requestFocus();    //done by ImageView;required for following kbd event//~1501R~
                		if (Dump.C) Dump.println("Canvas:onTouch requestFocus end");//~@@@2I~//~1A6AR~
                                                                   //~@@@2I~
                        return true;                               //~1430R~
                    }                                              //~1430R~
                    if (action==MotionEvent.ACTION_MOVE)           //~1430R~
                    {                                              //~1430R~
//                        Point movement=new Point();                //~1430R~//~1A17R~
                        if (!touchSlide && !swSwipe)//avoid flicker//~1A17R~
                        {                                          //~1A17I~
                       	    swSwipe=chkSwipe(xx,yy,swipeDest);     //~1A17I~
                        }                                          //~1A17I~
                        if (swSwipe)                              //~1A17I~
                            return true;                           //~1A17I~
                        if (!getSlideMovement(xx,yy,movement))     //~1430R~
                            return true;                           //~1430R~
                        touchSlide=true;                           //~1430R~
                        touchDownX=xx;  //new calc point           //~1430R~
                        touchDownY=yy;                             //~1430R~
                        moveCursor(movement.x,movement.y);         //~1430R~
                    }                                              //~1430R~
                    if (action==MotionEvent.ACTION_UP)             //~1430R~
                    {                                              //~1430R~
                        if (!touchSlide && !swSwipe)//avoid flicker//~1A17R~
                        {                                          //~1A17I~
                       	    swSwipe=chkSwipe(xx,yy,swipeDest);     //~1A17I~
                        }                                          //~1A17I~
                        if (swSwipe)                               //~1A17I~
                        {                                          //~1A17I~
                        	swSwipe=false;                         //~1A17I~
		                    rc=coord2PosSwipe(swipeStartX,swipeStartY);//~1A17I~
                            if (rc<=1)                             //~1A17I~
                            	swipeAction(swipeDest,curPosX,curPosY);//~1A17I~
                            return true;                           //~1A17I~
                        }                                          //~1A17I~
                        if (touchSlide)                            //~1430R~
                        {                                          //~1430R~
                            return true;                           //~1430R~
                        }                                          //~1430R~
                        rc=coord2Pos(xx,yy);                       //~1430R~
						if (Dump.C) Dump.println("coord2pos rc="+rc);//~1A02I~//~1A6AR~
                        switch(rc)                                 //~1430R~
                        {                                          //~1430R~
                        case 1:   //click on the same position     //~1430R~
                            setStone();                            //~1430R~
                            break;                                 //~1430R~
                        }                                          //~1430R~
                        return true;                                               //~0914I~//~1430R~
                    }                                              //~1430R~
                }                                                  //~1430R~
            }                                                      //~1430I~
            catch(Exception e)                                     //~1430I~
            {                                                      //~1430I~
                Dump.println(e,"Canvas.OnTouchEvent exception");   //~1430I~
            }                                                      //~1430I~
            return false;                                          //~1318I~
        }                                                          //~1318I~
//*******************************************                      //~1430I~
        public boolean getSlideMovement(int Px,int Py,Point Pmovement)//~1430I~
        {                                                          //~1430I~
                                                                   //~1430I~
            int xx,yy,axx,ayy,limit;                               //~1430I~
                                                                   //~1430I~
       //******************                                        //~1430I~
            xx=Px-touchDownX;                                      //~1430I~
            yy=Py-touchDownY;                                      //~1430I~
            if (Dump.C) Dump.println("getSlideMovement x="+Px+"<--"+touchDownX+",y="+Py+"<--"+touchDownY+",moveX="+xx+",moveY="+yy+",boxsize="+boxSize);//~1506R~//~1A17I~//~1A6AR~
            if (xx<0)   axx=-xx;    else    axx=xx;   //abs        //~1430I~
            if (yy<0)   ayy=-yy;    else    ayy=yy;                //~1430I~
            limit=boxSize;                                         //~1430I~
            if (axx>limit)	axx=1;	else 	axx=0;                 //~1430R~
            if (ayy>limit)	ayy=1;	else 	ayy=0;                 //~1430R~
            if (axx==0 && ayy==0)                                  //~1430R~
                return false;                                      //~1430I~
            if (xx<0)                                              //~1430R~
				axx=-axx;                                          //~1430I~
            if (yy<0)                                              //~1430I~
				ayy=-ayy;                                          //~1430I~
            Pmovement.x=axx;                                       //~1430R~
            Pmovement.y=ayy;                                       //~1430R~
            if (Dump.C) Dump.println("getSlideMovement true moved x="+axx+",y="+ayy);//~1A17R~//~1A6AR~
            return true;                                           //~1430I~
        }                                                          //~1430I~
//*******************************************                      //~1A17I~
        public boolean chkSwipe(int Px,int Py,Point Pdest)         //~1A17I~
        {                                                          //~1A17I~
            int xx,yy,axx,ayy,limit;                               //~1A17I~
       //******************                                        //~1A17I~
       		if (!(board instanceof ConnectedBoard))                //~1Ai3I~
            {                                                      //~1Ai3I~
            	if (Dump.Y) Dump.println("Canvas:board is not instanceof ConnectedBoard");//~1Ai3I~
            	return false;                                       //~1Ai3I~
            }                                                      //~1Ai3I~
            if (!((ConnectedBoard)board).isIdleFreeBoard())        //~1A17M~
            	return false;                                            //~1A17M~
            if (Dump.C) Dump.println("Canvas chkSwipe x="+Px+",y="+Py+",prev=("+swipeX+","+swipeY+")");//~1A17R~//~1A6AR~
            if (swipeTimestamp==0)                                 //~1A17I~
            {                                                      //~1A17I~
	        	swipeTimestamp=System.currentTimeMillis();         //~1A17I~
                swipeX=Px;                                         //~1A17I~
                swipeY=Py;
                swipeStartX=Px;
                swipeStartY=Py;//~1A17I~
            	return false;                                      //~1A17I~
            }                                                      //~1A17I~
        	int swipetime=(int)(System.currentTimeMillis()-swipeTimestamp);//~1A17I~
            xx=Px-swipeX;                                          //~1A17R~
            yy=Py-swipeY;                                          //~1A17R~
            swipeX=Px;                                             //~1A17M~
            swipeY=Py;                                             //~1A17M~
            if (Dump.C) Dump.println("Canvas swipe chk time="+swipetime+",moved x="+xx+",y="+yy);//~1A17I~//~1A6AR~
            if (swipetime==0)                                      //~1A17I~
            	return false;                                      //~1A17I~
            if (xx<0)   axx=-xx;    else    axx=xx;   //abs        //~1A17M~
            if (yy<0)   ayy=-yy;    else    ayy=yy;                //~1A17M~
            if (Dump.C) Dump.println("Canvas swipe velocity x="+(float)axx/swipetime+",y="+(float)ayy/swipetime+",limit="+(float)boxSize/SswipeLimit);	//velocity{//~1A17I~//~1A6AR~
            if (axx<=boxSize/2 && ayy<=boxSize/2)                  //~1A17I~
            	return false;                                            //~1A17I~
            if (chkAverage(0/*X*/,axx,swipetime))                  //~1A17R~
            	axx=1;                                             //~1A17I~
            else                                                   //~1A17I~
            	axx=0;                                             //~1A17I~
            if (chkAverage(1/*Y*/,ayy,swipetime))                  //~1A17R~
            	ayy=1;                                             //~1A17I~
            else                                                   //~1A17I~
            	ayy=0;                                             //~1A17I~
            if (axx==0 && ayy==0)                                  //~1A17I~
            {                                                      //~1A17I~
	        	swipeTimestamp=System.currentTimeMillis();         //~1A17I~
                swipeX=Px;                                         //~1A17I~
                swipeY=Py;                                         //~1A17I~
            	return false;                                      //~1A17I~
            }                                                      //~1A17I~
            if (xx<0)                                              //~1A17I~
				axx=-axx;                                          //~1A17I~
            if (yy<0)                                              //~1A17I~
				ayy=-ayy;                                          //~1A17I~
            Pdest.x=axx;                                           //~1A17I~
            Pdest.y=ayy;                                           //~1A17I~
            if (Dump.C) Dump.println("chkSwipe true moved x="+axx+",y="+ayy);//~1A17I~//~1A6AR~
            return true;                                           //~1A17I~
        }                                                          //~1A17I~
//*******************************************************************//~1A17I~
        private boolean chkAverage(int Pxy,int Pmove,int Ptime)    //~1A17R~
        {                                                          //~1A17I~
            if ((float)Pmove/Ptime>(float)boxSize/SswipeLimit)	//high velocity//~1A17I~
            {                                                      //~1A17I~
            	accumMove[Pxy]+=Pmove;                             //~1A17R~
            	accumTime[Pxy]+=Ptime;                             //~1A17R~
                if (accumMove[Pxy]>boxSize/2) //high speed through box size//~1A17R~
                {                                                  //~1A17I~
                	if (Dump.C) Dump.println("chkAverage true xy="+Pxy+",move="+accumMove[Pxy]+",time="+accumTime[Pxy]+",velocity="+(float)accumMove[Pxy]/accumTime[Pxy]+",limit="+(float)boxSize/SswipeLimit);//~1A17R~//~1A6AR~
                	return true;                                   //~1A17I~
                }                                                  //~1A17I~
            }                                                      //~1A17I~
            else                                                   //~1A17I~
            {                                                      //~1A17I~
            	accumMove[Pxy]=0;                                  //~1A17R~
            	accumTime[Pxy]=0;                                  //~1A17R~
            }                                                      //~1A17I~
            if (Dump.C) Dump.println("chkAverage false move="+Pmove+",time="+Ptime+",velocity="+(float)Pmove/Ptime+",limit="+(float)boxSize/SswipeLimit);//~1A17I~//~1A6AR~
            return false;                                          //~1A17I~
        }                                                          //~1A17I~
//*******************************************************************//~1A17I~
		boolean swSwipechk;                                        //~1A17I~
        private int coord2PosSwipe(int Px,int Py)                  //~1A17I~
        {                                                          //~1A17I~
        	swSwipechk=true;	//avoid flicker by duplicate invalidate()//~1A17R~
            int rc=coord2Pos(Px,Py);                               //~1A17R~
        	swSwipechk=false;                                      //~1A17I~
            if (Dump.C) Dump.println("coord2PosSwipe rc="+rc);     //~1A17I~//~1A6AR~
            return rc;                                             //~1A17I~
        }                                                          //~1A17I~
//*******************************************************************//~1419I~
//*rc:0 :pos in the board                                          //~1419I~
//*rc:1 :pos not changed on board                                  //~1419I~
//*rc:2 :pos out of board(ACTION_UP)                               //~1419I~
////*rc:3 :pos changed by push out of board(ACTION_UP)             //~1430R~
//*rc:-1:ignore                                                    //~1419I~
//*******************************************************************//~1419I~
        private int coord2Pos(int Px,int Py)                       //~1430R~
        {                                                          //~1318I~
            int posX,posY;                                         //~1419R~
            int redundancy=boxSize/4;                              //~1419I~
            int roundup=boxSize/2;                                 //~1419I~
		//********                                                 //~1419I~
            posX=Px-boardOrigin;                                   //~1419R~
            posY=Py-boardOrigin;                                                       //~1419I~
            if (posX<-redundancy)                                  //~1419R~
            	posX=-1;                                           //~1419I~
            else                                                   //~1419I~
            if (posX>boardEdge+redundancy)                         //~1419I~
            	posX=paternSize;                                   //~1419I~
            else                                                   //~1419I~
            	posX=(posX+roundup)/boxSize;                       //~1419R~
            if (posY<-redundancy)                                  //~1419I~
            	posY=-1;                                           //~1419I~
            else                                                   //~1419I~
            if (posY>boardEdge+redundancy)                         //~1419I~
            	posY=paternSize;                                   //~1419I~
            else                                                   //~1419I~
            	posY=(posY+roundup)/boxSize;                       //~1419I~
                                                                   //~1419I~
            if (Dump.C) Dump.println("Canvas.coord2Pos x="+Px+",y="+Py+",pos=("+posX+","+posY+")");//~1506R~//~1A6AR~
            if (posX>=0 && posX<paternSize                         //~1318I~
            &&  posY>=0 && posY<paternSize)                        //~1318I~
            {                                                      //~1318I~
//on board                                                         //~1419I~
				if (Dump.C) Dump.println("coord2pos posx="+posX+",posy="+posY+",cposX="+curPosX+",cposy="+curPosY);//~1A02R~//~1A6AR~
				if (posX==curPosX && posY==curPosY)                //~1419M~
                	return 1;                                      //~1419M~
                curPosX=posX;                                      //~1318I~
                curPosY=posY;                                      //~1318I~
                int rc=0;                                          //~1Ah5I~
                if ((AG.Options & AG.OPTIONS_1TOUCH)!=0)           //~1Ah5I~
                	rc=1;                                          //~1Ah5I~
                if ((AG.Options & AG.OPTIONS_1TOUCH_FB)!=0 && swFreeBoardCanvas)//~1Ah5I~
                	rc=1;                                          //~1Ah5I~
              if (!swSwipechk)                                     //~1A17I~
              {                                                    //~1Ah5I~
               if (rc==0)                                          //~1Ah5I~
				mouseMoved(posX,posY);	//draw board:LabelM        //~1422I~
               else                                                //~1Ah5I~
				mouseMoved2(posX,posY);	//do not invalidate        //~1Ah5I~
              }                                                    //~1Ah5I~
//                if ((AG.Options & AG.OPTIONS_1TOUCH)!=0)           //~1A13R~//~1Ah5R~
//                    return 1;                                      //~1A13I~//~1Ah5R~
//                if ((AG.Options & AG.OPTIONS_1TOUCH_FB)!=0 && swFreeBoardCanvas)//~1A13I~//~1Ah5R~
//                    return 1;                                      //~1A13I~//~1Ah5R~
//                return 0;                                          //~1419R~//~1Ah5R~
                return rc;                                         //~1Ah5I~
            }                                                      //~1318I~
            return 2;                                              //~1419R~
        }                                                          //~1318I~
//*******************************************************************//~1419I~
//        private Point pos2Coord(int Pposx,int Pposy)             //~1524R~
//        {                                                        //~1524R~
//            int xx=boardOrigin+Pposx*boxSize+boxSize/2;          //~1524R~
//            int yy=boardOrigin+Pposy*boxSize+boxSize/2;          //~1524R~
//            if (Pposx>=0 && Pposx<paternSize                     //~1524R~
//            &&  Pposy>=0 && Pposy<paternSize)                    //~1524R~
//            {                                                    //~1524R~
//                xx=boardOrigin+Pposx*boxSize+boxSize/2;          //~1524R~
//                yy=boardOrigin+Pposy*boxSize+boxSize/2;          //~1524R~
//                Point p=new Point(xx,yy);                        //~1524R~
//                return p;                                        //~1524R~
//            }                                                    //~1524R~
//            return new Point(0,0);                               //~1524R~
//        }                                                        //~1524R~
//**********************                                           //~1318I~
//*trackball************                                           //~1318R~
//**********************                                           //~1318I~
        @Override 
        public boolean onTrackballEvent(MotionEvent event)            //~0914I~//~1318R~
        {                                                          //~1318I~
                                        //~1318I~
            int xx=0,yy=0,action;                                      //~1426R~
            Point movement=new Point();                              //~1426I~
       //******************                                       //~1426I~
       		try                                                    //~1430I~
            {                                                      //~1430I~
	            if (ajagoCanvas.swInactive)                        //~@@@2R~
    	        	return false;		//no action                //~@@@2I~
                action=event.getAction();                              //~0914I~//~1430R~
                if (Dump.C) Dump.println("trackball action="+action);//~1506R~//~1A6AR~
                if (action==MotionEvent.ACTION_MOVE)               //~1430R~
                {                                                  //~1430R~
                    if (!getMovement(event,movement))              //~1430R~
                        return true;                               //~1430R~
                    xx=movement.x;                                 //~1430R~
                    yy=movement.y;                                 //~1430R~
                }                                                  //~1430R~
                switch(action)                                     //~1430R~
                {                                                  //~1430R~
                case MotionEvent.ACTION_MOVE:                      //~1430R~
                    requestFocus();    //by ImageView;required for following kbd event//~1501I~
                    moveCursor(xx,yy);                             //~1430R~
                    break;                                         //~1430R~
                case MotionEvent.ACTION_DOWN:                      //~1430R~
                    break;                                         //~1430R~
                case MotionEvent.ACTION_UP:                        //~1430R~
                    coord2Pos(xx,yy);//use latest position         //~1430R~
                    setStone();                                    //~1430R~
                }                                                  //~1430R~
            }                                                      //~1430I~
            catch(Exception e)                                     //~1430I~
            {                                                      //~1430I~
                Dump.println(e,"Canvas.OnTrackballEvent exception");//~1430I~
            }                                                      //~1430I~
            return true;                                               //~0914I~//~1318R~
        }                                                              //~0914I~//~1318R~
//********************                                             //~1426I~
        public boolean getMovement(MotionEvent event,Point Pmovement)//~1426I~
        {                                                          //~1426I~
                                                                   //~1426I~
            int xx,yy;                                             //~1426I~
            float fx,fy,afx,afy;                                   //~1426I~
            long eventtime,elapsed; //cut compiled movement when time expired//~1430R~
       //******************                                        //~1426I~
            eventtime=event.getEventTime();                        //~1426I~
            elapsed=eventtime-trackball_oldmovetime;
            if (Dump.C) Dump.println("trackball move eventtime="+Long.toHexString(eventtime)+",elapsed="+Long.toString(elapsed)+",x="+event.getX()+",y="+event.getY());//~1506R~//~1A6AR~
            if (elapsed>TRACKBALL_MOVE_CHKTIME)	//cut remaining of previous span//~1426I~
            {                                                      //~1426I~
	            trackball_accumX=0;                                //~1426I~
    	        trackball_accumY=0;                                //~1426I~
		        trackball_oldmovetime=eventtime;                   //~1426I~
            }                                                      //~1426I~
            fx=event.getX();    //getX nomarized by DpadKey 1 press//~1426I~
            fy=event.getY();                                       //~1426I~
            trackball_accumX+=fx;                                  //~1426I~
            trackball_accumY+=fy;                                  //~1426I~
            fx=trackball_accumX;                                   //~1426I~
            fy=trackball_accumY;                                   //~1426I~
            if (Dump.C) Dump.println("trackball accum x="+fx+",y="+fy);//~1506R~//~1A6AR~
            if (fx<0)   afx=-fx;    else    afx=fx;   //abs        //~1426I~
            if (fy<0)   afy=-fy;    else    afy=fy;                //~1426I~
            if (afx>=1.0f)	xx=1;	else 	xx=0;                  //~1426R~
            if (afy>=1.0f)	yy=1;	else 	yy=0;                  //~1426R~
            if (xx==0 && yy==0)                                     //~1426I~
                return false;                                      //~1426I~
            if (fx<0)                                              //~1426I~
                xx=-xx;                                            //~1426I~
            if (fy<0)                                              //~1426I~
                yy=-yy;                                            //~1426I~
            Pmovement.x=xx;                                        //~1426I~
            Pmovement.y=yy;                                        //~1426I~
            trackball_accumX=0;                                    //~1426I~
            trackball_accumY=0;                                    //~1426I~
            trackball_oldmovetime=eventtime;                        //~1426I~
            return true;                                           //~1426I~
        }                                                          //~1426I~
//** mouse                                                         //~1317I~
        @Override                                                  //~1317I~
		public void mouseClicked(MouseEvent e)                     //~1317I~
		{                                                          //~1317I~
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
//      	ajagoCanvas.boardMouseListener.mouseClicked(e);        //~1420R~
        }                                                          //~1317I~
        @Override                                                  //~1317I~
        public void mousePressed(MouseEvent e)                     //~1420R~
        {                                                          //~1317I~
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
            if (Dump.C) Dump.println("canvas:mousePressed");       //~1A02I~//~1A6AR~
	        enqRequest(new BoardRequest(BOARD_MOUSE_PRESS,e,null));//~1524R~
        }                                                          //~1317I~
        @Override                                                  //~1317I~
		public void mouseReleased(MouseEvent e)                    //~1420R~
		{                                                          //~1317I~
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
            if (Dump.C) Dump.println("canvas:mouseReleased");      //~1A02I~//~1A6AR~
	        enqRequest(new BoardRequest(BOARD_MOUSE_RELEASE,e,null));//~1524R~
		}                                                          //~1317I~
        @Override                                                  //~1317I~
		public void mouseEntered(MouseEvent e)                     //~1317I~
		{                                                          //~1317I~
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
        	ajagoCanvas.boardMouseListener.mouseEntered(e); //not synchronized function//~1420R~
		}                                                          //~1317I~
        @Override                                                  //~1317I~
		public void mouseExited(MouseEvent e)                      //~1317I~
		{                                                          //~1317I~
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
        	ajagoCanvas.boardMouseListener.mouseExited(e);  //not synchronized function//~1420R~
		}                                                          //~1317I~
		@Override
		public void keyPressed(KeyEvent ev) {
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
			//will not called from AKey                            //~@@@@R~
		}
		@Override
		public void keyReleased(KeyEvent ev) {
	        if (ajagoCanvas.swInactive)                            //~@@@2R~
    	    	return;		//no action                            //~@@@2I~
			//not called from AKey                                 //~@@@@R~
		}
		//***********************                                  //~1413I~
		public void mouseMoved(int Px,int Py)                      //~1413R~
        {                                                          //~1413I~
            if (Dump.C) Dump.println("Canvas mouseMoved x="+Px+",y="+Py);//~1A17I~//~1A6AR~
            if (Dump.X) Dump.println("X:Canvas mouseMoved x="+Px+",y="+Py);//~1AhaR~
            int xx=boardOrigin+Px*boxSize;                         //~1421I~
            int yy=boardOrigin+Py*boxSize;                         //~1421I~
            MouseEvent ev=new MouseEvent(xx,yy);                   //~1413R~
	        enqRequest(new BoardRequest(BOARD_MOUSE_MOVE,ev,null));//~1524R~
//        	invalidateBoard();	//Board.mouseMoved dose not request paint()//~1422I~//~@@@2R~
		}                                                          //~1413I~
		//***********************                                  //~1Ah5I~
		public void mouseMoved2(int Px,int Py)                     //~1Ah5I~
        {                                                          //~1Ah5I~
            if (Dump.C) Dump.println("Canvas mouseMoved2 x="+Px+",y="+Py);//~1Ah5I~
            if (Dump.X) Dump.println("X:Canvas mouseMoved2 x="+Px+",y="+Py);//~1AhaR~
            int xx=boardOrigin+Px*boxSize;                         //~1Ah5I~
            int yy=boardOrigin+Py*boxSize;                         //~1Ah5I~
            MouseEvent ev=new MouseEvent(xx,yy);                   //~1Ah5I~
	        enqRequest(new BoardRequest(BOARD_MOUSE_MOVE2,ev,null));//~1Ah5I~
		}                                                          //~1Ah5I~
		@Override
		public boolean mouseSwiped(MouseEvent e)                      //~1A17R~
		{	                                                       //~1A17R~
        	if (Dump.C) Dump.println("Canvas mouseSwiped");        //~1A6AR~
        	return false;//~1A17I~
		}
    }//ImageClase                                                  //~1317R~
//**********************************************************       //~1420R~
//*Board synchronization                                           //~1420I~
//*  do Board operation on subthread to avoid deadlock             //~1420R~
//*  Android limitation of GUI-API should execute on Mainthread    //~1420I~
//*  Board operation trigger comes from Igs receive thread/partner Thread/Main Thread(loval Viewer)//~1420I~
//*    deadlock scenario                                           //~1420I~
//*            sub thread                             main thread  //~1420I~
//*            -----------------------------------------------------------------//~1420I~
//*            synchronized                                        //~1420I~
//*              heavy function shch as updateall                  //~1420I~
//*                                                   call synchronized Board function//~1420I~
//*                                                     by Mouse/Keyboard operation//~1420I~
//*                                                     -->wait other synchronied function complete//~1420I~
//*              reqest GUI-API execution on MainThread            //~1420I~
//*                  -->wait MainThread free                       //~1420I~
//**********************************************************       //~1420R~
    //**********************                                       //~1420I~
	public class BoardRequest                                      //~1420I~
    {                                                              //~1420I~

		int opid;                                                  //~1420I~
        Object parm1,parm2;                                        //~1420I~
        public BoardRequest(int Popid,Object Pparm1,Object Pparm2)//~1420I~
        {                                                          //~1420I~
        	opid=Popid;                                       //~1420I~
            parm1=Pparm1;                                          //~1420I~
            parm2=Pparm2;                                          //~1420I~
        }                                                          //~1420I~
    }                                                              //~1420I~
    private LinkedList<BoardRequest> requestList=new LinkedList<BoardRequest>(); //~1420M~
    public static final int BOARD_MOUSE_PRESS  =1;                 //~1420I~
    public static final int BOARD_MOUSE_RELEASE=2;                 //~1420I~
    public static final int BOARD_MOUSE_MOVE   =3;                 //~1420I~
    public static final int BOARD_KEY_PRESS    =4;                 //~1420I~
    public static final int BOARD_PAINT        =5;                 //~1420I~
    public static final int BOARD_DOACTION     =6;                 //~1421I~
    public static final int BOARD_STOP         =7;                 //~1422I~
    public static final int BOARD_MOUSE_UPDOWN =8;                 //~1425I~
    public static final int BOARD_DRAW_BIGLABEL=9;                 //~1513I~
    public static final int BOARD_DRAW_CAPTURED=10;                //~@@@2I~
    public static final int BOARD_STOP_MAINFRAME=11;               //~1A02I~
    public static final int BOARD_SWIPE        =12;                //~1A17I~
    public static final int BOARD_CALLBACK     =13;                //~v1B7I~//~1Ah0I~
    public static final int BOARD_MOUSE_MOVE2  =14;                //~1Ah5I~
    public static final int BOARD_START_TIMER=15;                //~1Ah5I~//~1Ah0R~
    public int paintenqctr;                                        //~1513R~
    public int paintbytimerctr;                                    //~1513I~
    //*******************************************************      //~1424R~
    public void enqRequest(BoardRequest Preq)                      //~1420I~
    {                                                              //~1420I~
    	int ctr,ctr2,paintctr;                                     //~1427R~
    //**********************************                           //~1427I~
    	if (boardSync==null)                                       //~1503I~
        	return;                                                //~1503I~
        if (Preq.opid!=BOARD_STOP)                                 //~1A02I~
	    	if (swMainFrameDrawn)                                  //~1A02I~
        		return;                                            //~1A02I~
        if (Dump.C) Dump.println("Canvas:BoardSync enqRequest id="+Preq.opid);//~1514R~//~@@@2M~//~1A6AR~
    	paintctr=paintenqctr;                                       //~1427I~
        if (Preq.opid==BOARD_PAINT)                                //~1427I~
        	paintenqctr++;	                                       //~1427I~
        if (Preq.opid==BOARD_DRAW_BIGLABEL)                        //~1513I~
        	paintbytimerctr++;                                     //~1513I~
    	synchronized(requestList)	                               //~1420I~
        {                                                          //~1420I~
        	requestList.add(Preq);	//enq to last                  //~1420I~
            ctr=requestList.size();                                //~1421I~
            requestList.notifyAll();    //pos wait                 //~1421I~
        }                                                          //~1420I~
    	if (boardSync==null)     //after stopThread                //~v107I~
        	return;                                                //~v107I~
        if (boardSync.swSleep)                                   //~1427I~
            ctr2=ctr+1;                                            //~1427I~
        else                                                       //~1427I~
            ctr2=ctr;                                              //~1427I~
        if (paintctr>1 && (ctr2-paintbytimerctr)>ENQ_OVERFLOW_LIMIT)//~1513R~
        {                                                          //~1421I~
        	String ovfmsg=ctr+".";                                 //~1421I~
        	AView.showToast(R.string.BoardQWaiting,ovfmsg);    //~1421I~//~@@@@R~
        }                                                          //~1421I~
    }                                                              //~1420I~
    //**********************                                       //~1422I~
    public void  stopThread()                                      //~1422I~
    {                                                              //~1422I~
    	swCloseRequested=true;                                     //~1424I~
    	if (boardSync!=null)                                       //~1422I~
        {                                                          //~1422I~
        	boardSync.stopit();                                    //~1422I~
        	enqRequest(new BoardRequest(Canvas.BOARD_STOP,this,null));  //post wait//~1503R~
    		boardSync=null;	//of Frame                             //~1503M~
	    	canvasFrame.setFrameType(null);	//for UI thread ctl    //~@@@2I~
        }                                                          //~1422I~
    }                                                              //~1422I~
    //***********************************************              //~1Ag9I~
    //*from BoardSync at stop thread                               //~1Ag9I~
    //***********************************************              //~1Ag9I~
    private void onDestroy()                                       //~1Ag9I~
    {                                                              //~1Ag9I~
    	if (Dump.Y) Dump.println("Canvas:onDestroy");              //~1Ag9I~
    	board.onDestroy();                                         //~1Ag9I~
        boardImageCopy.recycle();                                  //~1Ag9R~
        boardImageCopy=null;                                       //~1Ag9I~
        activeImage=null;                                          //~1Ag9I~
        graphics.recycle(true); //clear ptr tp androidcanvas and bitmap of boardimage copy was recycled 2 lines before,this is required because subcras has also reference to graphics//~1Ag9I~
        graphics=null;                                             //~1Ag9I~
    }                                                              //~1Ag9I~
    //******************************************************       //~1424R~
    //*subthread Class                                             //~1424I~
    //******************************************************       //~1424I~
    public class BoardSync extends StopThread                      //~1420I~
    {                                                              //~1420I~
    	private Canvas ajagoCanvas;                                //~1421R~
        private Board board;                                       //~1421R~
        private Graphics graphics;                                 //~1424I~
        public boolean swSleep;                                    //~1422R~
	    private LinkedList<BoardRequest> requestList;              //~1424I~
    //**************************                                   //~1420I~
        BoardSync(Canvas Pcanvas)                                  //~1420R~
        {                                                          //~1420I~
			ajagoCanvas=Pcanvas;                                   //~1420R~
	        board=(Board)Pcanvas;                                  //~1420I~
	        graphics=Pcanvas.graphics;	//java confuse outer class valiable when multiple instance//~1424I~
	        requestList=Pcanvas.requestList;                       //~1424I~
            start();                                               //~1420I~
        }                                                          //~1420I~
        public void run ()                                         //~1420I~
        {                                                          //~1420I~
	        if (Dump.C) Dump.println("BoardSync run started");     //~1506R~//~1A6AR~
        	while(true)                                            //~1420I~
            {                                                      //~1420I~
                try                                                //~1420R~
                {                                                  //~1420R~
                    int opid=doRequest();                          //~1503R~
                    if (opid==BOARD_STOP)                          //~1503R~
                    {                                              //~1422I~
	                    if (Dump.C) Dump.println("BoardSync thread return:"+this.toString());//~1A4hR~//~1A6AR~
                        return;                                    //~1422M~
                    }                                              //~1422I~
                }                                                  //~1420R~
                catch (Exception e)                                //~1420R~
                {                                                  //~1420R~
                    Dump.println(e,"BoardSync Exception");         //~1420R~
                }                                                  //~1420R~
            }                                                      //~1420I~
        }                                                          //~1420I~
        private  BoardRequest deqRequest()                         //~1420I~
        {                                                          //~1420I~
            BoardRequest req;                                      //~1420I~
            synchronized(requestList)                              //~1420I~
            {                                                      //~1420I~
				while(requestList.size()==0)                       //~1421I~
                {                                                  //~1421I~
	                swSleep=true;                                  //~1422I~
                    try                                            //~1422R~
					{                                              //~1422I~
						requestList.wait();
					}                                              //~1422R~
					catch (InterruptedException e)                 //~1422I~
					{
						Dump.println(e,"requestList wait interrupted");
						return null;
					}//wait notufy              //~1421I~
	        		if (Dump.C) Dump.println("Canvas:BoardSync subthread posted residual ctr="+requestList.size()+",q="+requestList.toString());//~1506R~//~1A6AR~
                }                                                  //~1421I~
                req=requestList.getFirst(); //get top              //~1422R~
                swSleep=false;                                     //~1422I~
	            requestList.remove(req);//deq                      //~1422R~
            }                                                      //~1420I~
	        if (Dump.C) Dump.println("Canvas:BoardSync deqRequest id="+req.opid+",residual ctr="+requestList.size()+",board="+this.toString());//~1514R~//~1A6AR~
            return req;                                            //~1420I~
        }                                                          //~1420I~
        protected int doRequest()                                  //~1503R~
        {                                                          //~1420I~
            BoardRequest req;
            MouseEvent mev;
            KeyEvent kev;//~1420I~
            int opid=0;                                            //~1503I~
            while(true)                                            //~1420I~
            {                                                      //~1420I~
                req=deqRequest();                                  //~1420I~
                if (req==null)                                     //~1420I~
                    break;                                         //~1420I~
	            if (Dump.C) Dump.println("Canvas:BoardSync start id="+req.opid);//~1506R~//~1A6AR~
                opid=req.opid;                                     //~1503I~
                if (stopped())                                     //~1503I~
                {                                                  //~1514I~
                	if (Dump.C) Dump.println("BoardSync thread stopped opid="+opid);//~1A4hR~//~1A6AR~
                	if (opid!=BOARD_STOP)                          //~1503I~
                    	break;                                     //~1503I~
                }                                                  //~1514I~
                if (opid!=BOARD_STOP)                              //~1A02I~
                    if (swMainFrameDrawn)                          //~1A02I~
                    {                                              //~1A02I~
                        if (opid!=BOARD_STOP_MAINFRAME)            //~1A02I~
                            continue;   //ignore                   //~1A02I~
                    }                                              //~1A02I~
              try                                                  //~1Ah0I~
              {                                                    //~1Ah0I~
                switch(opid)                                       //~1503R~
                {                                                  //~1420I~
                case BOARD_MOUSE_PRESS:		//1                            //~1420I~//~@@@2R~
                    mev=(MouseEvent)(req.parm1);         //~1420I~
	        		ajagoCanvas.boardMouseListener.mousePressed(mev);//~1420I~
                    break;                                         //~1420I~
                case BOARD_MOUSE_RELEASE:	//2                          //~1420I~//~@@@2R~
                    mev=(MouseEvent)(req.parm1);         //~1420I~
	        		ajagoCanvas.boardMouseListener.mouseReleased(mev);//~1420I~
                    break;                                         //~1420I~
                case BOARD_MOUSE_UPDOWN:	//8                           //~1425I~//~@@@2R~
                    mev=(MouseEvent)(req.parm1);                   //~1425I~
			        if (Dump.X) Dump.println("X:Pcanvas.BoardSync BOARD_MOUSE_UPDOWN start");//~1AhaR~
	        		ajagoCanvas.boardMouseListener.mousePressed(mev);//~1425I~
	        		ajagoCanvas.boardMouseListener.mouseReleased(mev);//~1425I~
			        if (Dump.X) Dump.println("X:Pcanvas.BoardSync BOARD_MOUSE_UPDOWN end");//~1AhaR~
                    break;                                         //~1425I~
                case BOARD_MOUSE_MOVE:      //3                       //~1420I~//~@@@2R~
                    mev=(MouseEvent)(req.parm1);         //~1420I~
			        if (Dump.X) Dump.println("X:Pcanvas.BoardSync BOARD_MOUSE_MOVE start color="+board.maincolor());//~1AhaR~
	        		ajagoCanvas.boardMouseMotionListener.mouseMoved(mev);//~1420I~
	                ajagoCanvas.drawBoardImageCopy=true;  //mousemove draw on boardimagecopy//~@@@2R~
	        		if (Dump.C) Dump.println("MOUSE_MOVE set drawBoardImageCopy true");//~@@@2I~//~1A6AR~
            		ajagoCanvas.imageCanvas.invalidateBoard();	//requred if showTarget=false(if true,no need because copy() request repaint())//~@@@2R~
			        if (Dump.X) Dump.println("X:Pcanvas.BoardSync BOARD_MOUSE_MOVE end");//~1AhaR~
                    break;                                         //~1420I~
                case BOARD_MOUSE_MOVE2:      //14   do not invalidate//~1Ah5I~
                    mev=(MouseEvent)(req.parm1);                   //~1Ah5I~
			        if (Dump.X) Dump.println("X:Pcanvas.BoardSync BOARD_MOUSE_MOVE2 start color="+board.maincolor());//~1AhaR~
	        		ajagoCanvas.boardMouseMotionListener.mouseMoved(mev);//~1Ah5I~
//                  ajagoCanvas.drawBoardImageCopy=true;  //mousemove draw on boardimagecopy//~1Ah5I~
//          		ajagoCanvas.imageCanvas.invalidateBoard();	//requred if showTarget=false(if true,no need because copy() request repaint())//~1Ah5I~
			        if (Dump.X) Dump.println("X:Pcanvas.BoardSync BOARD_MOUSE_MOVE2 end");//~1AhaR~
                    break;                                         //~1Ah5I~
                case BOARD_KEY_PRESS:		//4                              //~1420I~//~@@@2R~
                    kev=(KeyEvent)(req.parm1);             //~1420I~
                    if (Dump.C) Dump.println("Canvas board keyPress Listener_a");//~1506R~//~1A6AR~
            		ajagoCanvas.boardKeyListener_a.keyPressed(kev);//~1503R~
                    if (ajagoCanvas.boardKeyListener_b!=null)      //~1503I~
                    {                                              //~1503I~
                    	if (Dump.C) Dump.println("Canvas board keyPress Listener_b");//~1506R~//~1A6AR~
	            		ajagoCanvas.boardKeyListener_b.keyPressed(kev);//~1503I~
                    }                                              //~1503I~
                    break;                                         //~1420I~
                case BOARD_PAINT:       //5                           //~1420I~//~@@@2R~
		        	paintenqctr--;                                 //~1427I~
                    ajagoCanvas.swPainting=true; //drawBitmap to android Canvas//~1420I~
		            if (Dump.C) Dump.println("Canvas:BoardSync call paint board="+board.toString()+",graphics="+graphics.toString());//~1506R~//~1A6AR~
                    board.paint(graphics);	//paint() callback drawBoard()//~1420I~
                    ajagoCanvas.swPainting=false;                  //~1420I~
                    break;                                         //~1420I~
                case BOARD_DOACTION:	//6                               //~1421I~//~@@@2R~
                    ActionEvent ev=(ActionEvent)(req.parm1);       //~1421I~
                    ev.scheduledAT.actionPerformed(ev);             //~1421I~
                    break;                                         //~1421I~
                case BOARD_STOP:        //7                                   //~1503I~//~@@@2R~
//                    Frame f=ajagoCanvas.canvasFrame;               //~1503I~//~@@@2R~
//                    if (f.isObserveGame)                           //~1503I~//~@@@2R~
//                    {                                              //~1503I~//~@@@2R~
//                        if (Dump.C) Dump.println("BoardSync stop wait 10sec for Observe game");//~1506R~//~@@@2R~//~1A6AR~
//                        Utils.sleep(10000); //see GoObserver,unchain distributor after 10sec wait//~1503R~//~@@@@R~//~@@@2R~
//                        if (Dump.C) Dump.println("BoardSync stop waken");//~1506R~//~@@@2R~//~1A6AR~
//                    }                                              //~1503I~//~@@@2R~
//                    f.recycleBitmap();                             //~1503I~//~@@@2R~
					if (Dump.C) Dump.println("Canvas:BOARD_STOP req Window:recycleMyBitmapStack");//~1A4hI~//~1A6AR~
			        Window.recycleMyBitmapStack(recycleMyStack);   //~@@@2I~
                    onDestroy();	//clear bitmap reference       //~1Ag9I~
                    break;                                         //~1503I~
                                                                 //~1420I~
                case BOARD_DRAW_BIGLABEL:    //9                      //~1513I~//~@@@2R~
                    if (Dump.C) Dump.println("BoardSync BigLabel paint");//~1513I~//~1A6AR~
                    BigLabel label=(BigLabel)(req.parm1);          //~1513I~
                    Graphics g=(Graphics)(req.parm2);              //~1513I~
                    label.paint(g);	                               //~1513I~
                    break;                                         //~1513I~
                case BOARD_DRAW_CAPTURED:	//10                   //~@@@2R~
                    if (Dump.C) Dump.println("BoardSync Drawcaptured");//~@@@2I~//~1A6AR~
                    Utils.sleep(200);                              //~@@@2I~
                    int remains=((Integer)req.parm1).intValue();   //~@@@2I~
                    deleteCapturedPiece(remains);   //ConnectedBoard//~@@@2R~
                    break;                                         //~@@@2I~
                case BOARD_STOP_MAINFRAME:	//11                   //~1A02I~
                    if (Dump.C) Dump.println("BoardSync StopMainFrame");//~1A02I~//~1A6AR~
                    board.recycleMainFrameBitmap();                 //~1A02I~
                    break;                                         //~1A02I~
                case BOARD_SWIPE:	//12                           //~1A17I~
                	Point dest=(Point)(req.parm2);//swipe detination//~1A17R~
                    mev=(MouseEvent)(req.parm1);  //swipe start x,y//~1A17R~
	        		ajagoCanvas.boardMouseMotionListener.mouseMoved(mev);//~1A17I~
                    int posx=mev.getX();                           //~1A17I~
                    int posy=mev.getY();                           //~1A17I~
	            	posx=(dest.x<0)?-posx:posx;                    //~1A17I~
	            	posy=(dest.y<0)?-posy:posy;                    //~1A17I~
        			mev=new MouseEvent(posx,posy);                 //~1A17I~
                    if (Dump.C) Dump.println("BoardSync Swipe x="+mev.getX()+",y="+mev.getY());//~1A17I~//~1A6AR~
	        		ajagoCanvas.boardMouseListener.mouseSwiped(mev);//~1A17I~
                    break;                                         //~1A17I~
                case BOARD_CALLBACK: //13                          //~v1B7I~//~1Ah0I~
                    if (Dump.C) Dump.println("BoardSync Callback");//~v1B7I~//~1A6AR~//~1Ah0I~
                    GoFrame gf=(GoFrame)(req.parm1);               //~v1B7I~//~1Ah0I~
                    Object parm=req.parm2;	//CanvascallbackParm   //~v1B7R~//~1Ah0I~
                    gf.canvasCallback(parm);                       //~v1B7R~//~1Ah0I~
                    break;                                         //~v1B7I~//~1Ah0I~
				case BOARD_START_TIMER: //15;                    //~1Ah5I~//~1Ah0R~
                    if (Dump.C) Dump.println("BoardSync:BOARD_START_TIMER swStartTimerDone="+swStartTimerDone+",ctrOnDraw="+ctrOnDraw);//~1Ah0R~
                	if (!swStartTimerDone)                        //~1Ah0R~
                    {                                              //~1Ah0I~
                		if (ctrOnDraw!=0)    //from GtpFrame,before once OnDraw//~1Ah0I~
                    	{                                          //~1Ah0I~
                			swStartTimerDone=true;                 //~1Ah0R~
                    		gtpFrame.startTimer();                //~1Ah5I~//~1Ah0R~
                        }                                          //~1Ah0I~
                    }                                              //~1Ah0I~
                }                                                  //~1513I~
              }                                                    //~1Ah0I~
			  catch (Exception e)                                  //~1Ah0I~
			  {                                                    //~1Ah0I~
			 	Dump.println(e,"BoardSync doreques");              //~1Ah0I~
			  }                                                    //~1Ah0I~
 	            if (Dump.C) Dump.println("Canvas:BoardSync end id="+req.opid);//~1506R~//~@@@2R~//~1A6AR~
                if (opid==BOARD_STOP)                              //~@@@2I~
                    break;  //thread return                        //~@@@2I~
            }                                                      //~1420I~
            return opid;                                           //~1503R~
        }                                                          //~1420I~
                                                                   //~@@@2I~
    }                                                              //~1420I~
    //*should be override                                          //~@@@2I~
    protected void deleteCapturedPiece(int Premains){};   //ConnectedBoard     //~@@@2I~
//******************************************************************//~@@@2I~
//*from TGF,inactivate until close                                 //~@@@2R~
//******************************************************************//~@@@2I~
    public void inactivateCanvas()                                     //~@@@2I~
    {                                                              //~@@@2I~
        if (Dump.C) Dump.println("Canvas:invactivateCanvas");      //~@@@2I~//~1A6AR~
    	swInactive=true;                                           //~@@@2I~
    }                                                               //~1420I~//~@@@2R~
//******************************************************************//~@@@2I~
    private void inactiveWarning()                                 //~@@@2I~
    {                                                              //~@@@2I~
    	int id;                                                    //~@@@2I~
    	if (canvasFrame==AG.mainframe)                             //~@@@2I~
        	id=R.string.WarnInactMain;                                 //~@@@2R~
        else                                                       //~@@@2I~
		if (swReplayBoardReplay)	//replay mode                  //~1A2dI~
        	id=R.string.WarnInactReplayBoard;                      //~1A2dI~
        else                                                       //~1A2dI~
        	id=R.string.WarnInactBoardClosed;                          //~@@@2R~
        AView.showToast(id);                                       //~@@@2R~
                                                                   //~@@@2I~
    }                                                              //~@@@2I~
}//class Canvas                                                    //~1213R~
