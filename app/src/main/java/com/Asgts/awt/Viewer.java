//*CID://+1AefR~:                             update#=   14;       //+1AefR~
//**************************************************************** //~v101I~
//1Aef 2015/07/30 Exception(Only the original thread that created a view hierarchy can touch its views.) for viewer(not always)//+1AefI~
//1A6v 2015/02/18 (Bug)Coversation dialog at bottom is hidden by IME dialog//~1A6vI~
//                Relative layout fix footer to bottom on sceeen;TextView pushout bottom buttons is hidden by IME dialog//~1A6vI~
//                change to use ListView                           //~1A6vI~
//1A0b 2013/03/04 expand viewer maxline to 1000 from 200           //~1A0bI~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//**************************************************************** //~v101I~
package com.Asgts.awt;                                              //~2C26R~//~v101R~

import jagoclient.Dump;
import jagoclient.Global;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.Asgts.AG;                                                //~2C26R~//~v101R~
import com.Asgts.UiThread;                                     //~2C26R~//~@@@@R~//~v101R~
import com.Asgts.UiThreadI;                                    //~2C26R~//~@@@@R~//~v101R~

import android.text.SpannableString;                               //~1A6vI~
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView;

public class   Viewer extends Component 
                    implements UiThreadI//~1220R~           //~1221R~//~1301R~//~@@@@R~
{
    private int caseUiThread;                                    //~1219I~//~1220M~//~1221R~//~1422R~
    private final static int CASE_APPEND      		=1;                //~1221R~//~1301R~//~1304R~
    private final static int CASE_SETTEXT     		=2;                //~1221R~//~1301R~//~1304R~
    private final static int CASE_APPENDSPAN   		=3;            //~1A6vI~
    private final static int CASE_SETMODE     		=4;            //+1AefI~
//  private final static int VIEWER_MAXLINE =200;	//by storage limitation//~1219I~//~1220M~//~1A0bR~
    private final static int VIEWER_MAXLINE =1000;	//by storage limitation//~1A0bI~
    private final static String ENDOFHEADER="Login:";              //~1228I~
                                                                   //~1220I~
	private ListView listview;                                     //~1422R~
	private ArrayAdapter<String> adapter;                          //~1422R~
	private ArrayList<String> strarray;                            //~1422R~
	private ArrayList<Color> colorlist;                            //~1422R~
	private ArrayList<SpannableString> spanlist;                   //~1A6vI~
	public  boolean  dataStacked;                                  //~1422R~
	protected Font font;                                           //~1422R~
	protected boolean viewermode;                                  //~1422R~
    private int rowId=AG.viewerRowId;                              //~1220I~
    protected Color textColor=Color.black;                         //~1220I~
	protected Color bgColor=Color.white;                           //~1220I~
	protected Color bgColorSelected=Color.blue.darker().darker();  //~1220I~
//    private boolean swConnectionFrameViewer;                       //~1422R~//~@@@@R~
	private float   titleTextSize;                                 //~1422R~
	private int     lastHeaderLine;                                //~1422R~
	private boolean swEndOfHeader;                                 //~1422R~
	private String  endOfHeader;                                   //~1506I~
	private int resId;                                             //~v101I~
                                                                   //~1301I~
//*************************************************************    //~1304I~
    public class LineData                                                 //~1301I~//~1304R~
    {                                                              //~1301I~
        String line;                                               //~1301I~
        Color  color;                                              //~1301I~
        SpannableString spanStr;                                   //~1A6vI~
        public LineData()                                          //~1304I~
		{                                                          //~1304I~
        }                                                          //~1304I~
        public LineData(String Pline,Color Pcolor)                 //~1301I~
		{                                                          //~1301I~
        	line=Pline;                                            //~1301I~
            color=Pcolor;                                          //~1301I~
        }                                                          //~1301I~
    }                                                              //~1301I~
//***************                                                  //~1304I~
    public class AddayData                                         //~1418R~
    {                                                              //~1304I~
    	public ArrayList<String> lines=null;                       //~1304I~
    	public ArrayList<Color>  colors=null;                      //~1304I~
        public AddayData()                                         //~1418R~
        {                                                          //~1304I~
        }                                                          //~1304I~
    }                                                              //~1304I~
                                                                   //~1224I~
//***************                                                  //~1114I~                                  //~1124I~//~1215R~
    public Viewer()                                                //~1215R~
    {                                                              //~1112R~
        init("Viewer");    	//default view name                    //~1219I~
        if (Dump.Y) Dump.println("new awt default Viewer");        //~1506R~//~@@@@R~
    }
//**************                                                   //~v101I~
    public Viewer(String Pname)                                    //~1216R~
    {                                                              //~1215I~
        init(Pname);                                               //~1219I~
        if (Dump.Y) Dump.println("new awt viewer="+Pname);         //~1506R~
    }                                                              //~1215I~
//**************                                                   //~v101I~
    public Viewer(Container Pcontainer,int Presid)                     //~v101I~
    {                                                              //~v101I~
    	super(Pcontainer);                                         //~v101I~
        resId=Presid;                                              //~v101I~
        init("Viewer");                                               //~v101I~
        if (Dump.Y) Dump.println("new awt Viewer resid="+Integer.toHexString(Presid));         //~v101I~
    }                                                              //~v101I~
//**************                                                   //~1219I~
    private void init(String Pname)                                     //~1219I~
    {                                                              //~1219I~
    	strarray=new ArrayList<String>();                          //~1220I~
    	colorlist=new ArrayList<Color>();                          //~1220I~
    	spanlist=new ArrayList<SpannableString>();                 //~1A6vI~
	    setAdapter(Pname);                                         //~1220I~
    	setMode(listview);                                         //~1220I~
//        swConnectionFrameViewer=(AG.currentLayoutId==AG.frameId_ConnectionFrame);//~1224I~//~@@@@R~
        frame=AG.currentFrame;                                     //~1304I~
        endOfHeader=Global.resourceString("____Enter_Login_____n");//~1506I~
        endOfHeader=endOfHeader.substring(0,endOfHeader.length()-1);//~1506R~
    }                                                              //~1219I~
//*****************                                                //~1220I~
    public void setFont(Font Pfont)                                //~1220I~
    {                                                              //~1220I~
    	font=Pfont;                                                //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void setBackground(Color Pcolor)                        //~1220I~
    {                                                              //~1220I~
    	bgColor=Pcolor;                                            //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    private ListView setAdapter(String Pviewname)                  //~1220I~
    {                                                              //~1220I~
      if (resId!=0 && parentContainer!=null && parentContainer.containerLayoutView!=null)//~v101I~
        listview=(ListView)parentContainer.findViewById(resId);    //~v101I~
      else                                                         //~v101I~
        listview=(ListView)AG.findViewByName(Pviewname);           //~1220I~
        adapter=new ListArrayAdapter(strarray);                    //~1220I~
        return listview;                                           //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    private void setMode(ListView Plv)                             //~1220I~
    {                                                              //~1220I~
    	if (Dump.Y) Dump.println("Viewer:setMode listview="+Plv);  //+1AefI~
        runOnUiThread(CASE_SETMODE,Plv);                           //+1AefI~
    }                                                              //+1AefI~
    private void setModeUI(ListView Plv)                           //+1AefI~
    {                                                              //+1AefI~
        if (Dump.Y) Dump.println("Viewer:setModeUI listview="+Plv);//+1AefI~
        Plv.setAdapter(adapter);                                   //~1220I~
        Plv.setChoiceMode(ListView.CHOICE_MODE_NONE);              //~1220I~
        Plv.setDividerHeight(0);	//seperator==0pix              //~1220I~
        Plv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL); //scroll to bottom//~1304I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void append(String Pline)                               //~1220I~
    {                                                              //~1220I~
    	append(Pline,null);                                        //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void append(String Pline,Color Pcolor)                  //~1220I~
    {                                                              //~1220I~
    	if (Dump.Y) Dump.println("Viewer:append="+Pline);          //~1A6vI~
			runOnUiThread(CASE_APPEND,new LineData(Pline,Pcolor));      //~1301R~//~1304R~
    }                                                              //~1220I~
//*****************                                                //~1A6vI~
    public void append(SpannableString Pspan)                      //~1A6vI~
    {                                                              //~1A6vI~
		String str=Pspan.subSequence(0,Pspan.length()).toString(); //~1A6vI~
    	if (Dump.Y) Dump.println("Viewer:append spannable="+str);  //~1A6vI~
		LineData ld=new LineData(str,Color.black/*fg*/);           //~1A6vI~
        ld.spanStr=Pspan;                                          //~1A6vI~
		runOnUiThread(CASE_APPENDSPAN,ld);                         //~1A6vI~
    }                                                              //~1A6vI~
//*****************                                                //~1220I~
    private void appendUI(Object/*Color*/ Pparm)                   //~1220I~//~1221R~//~1301R~
    {                                                              //~1220I~//~1221R~//~1301R~
		LineData lineData=(LineData)Pparm;                         //~1301I~
		String appendstr=lineData.line;                            //~1301I~
        if (appendstr.endsWith("\n"))                              //~1301I~
        	appendstr=appendstr.substring(0,appendstr.length()-1); //~1301I~
//**strarray.add cause IllegalStateException(adapter and array count unmatch) when append by subthread//~1301I~
//      strarray.add(appendstr);                                  //~1220I~//~1221R~//~1301R~//~1326I~
        adapter.add(appendstr);                           //~1301R~
        if (Dump.Y) Dump.println("Viewer appendUI ="+appendstr);   //~1506R~
        if (!swEndOfHeader)                                        //~1506R~
            if (appendstr.startsWith(ENDOFHEADER)                  //~1506I~
            ||  appendstr.equals(endOfHeader)                      //~1506R~
            )                                                      //~1506R~
            {                                          //~1224I~   //~1506R~
                swEndOfHeader=true;                                //~1506R~
                lastHeaderLine=adapter.getCount()-1;                   //~1224R~//~1506R~
                if (Dump.Y) Dump.println("LastHeaderLine="+lastHeaderLine);//~1506R~
            }                                          //~1224I~   //~1506R~
        colorlist.add(lineData.color);                               //~1220I~//~1221R~//~1301R~
        spanlist.add(lineData.spanStr);                            //~1A6vI~
        int ii=adapter.getCount()-VIEWER_MAXLINE;                  //~1301R~
        if (ii>0)                                                  //~1301I~
        {                                                          //~1301I~
            colorlist.subList(0,ii).clear();                       //~1220I~//~1221R~//~1301I~
            spanlist.subList(0,ii).clear();                        //~1A6vI~
            for (;ii>0;ii--)                                       //~1301I~
            {                                                          //~1220I~//~1221R~//~1301R~
	            if (Dump.Y) Dump.println("Viewer remove="+adapter.getItem(0));//~1506R~
                adapter.remove(adapter.getItem(0));                //~1301R~
            }                                                          //~1220I~//~1221R~//~1301R~
            lastHeaderLine-=ii;                                    //~1304R~
        }                                                          //~1301I~
//      adapter.notifyDataSetChanged();                            //~1220I~//~1221R~//~1301R~//~1326R~
    }                                                              //~1220I~//~1221R~//~1301R~
//*****************                                                //~1A6vI~
    private void appendSpanUI(Object/*Color*/ Pparm)               //~1A6vI~
    {                                                              //~1A6vI~
	    appendUI(Pparm);                                           //~1A6vI~
    }                                                              //~1A6vI~
//*****************                                                //~1220I~
    public void setText(String Ptext)                              //~1220I~
    {                                                              //~1220I~
        runOnUiThread(CASE_SETTEXT,new LineData(Ptext,null));                             //~1220I~//~1221R~//~1301R~
    }                                                              //~1220I~
//*****************                                                //~1220I~//~1221R~//~1301R~
    private void setTextUI(Object/*Color*/ Pparm)                  //~1220I~//~1221R~//~1301R~
    {                                                              //~1220I~//~1221R~//~1301R~
        adapter.clear();  //removeRange is protected              //~1220I~//~1221R~//~1301R~
        colorlist.clear();                                         //~1220I~//~1221R~//~1301R~
        spanlist.clear();                                          //~1A6vI~
        appendUI(Pparm);                                           //~1220I~//~1221R~//~1301R~
    }                                                              //~1220I~//~1221R~//~1301R~
//**********************************************************************//~1220I~
//*ArrayAdapter class                                              //~1220I~
//**********************************************************************//~1220I~
    class ListArrayAdapter extends ArrayAdapter<String>            //~1220I~
    {                                                              //~1220I~
        public ListArrayAdapter(ArrayList<String> Pstrarray)       //~1220I~
        {                                                          //~1220I~
            super(AG.context,rowId,Pstrarray);                     //~1220I~
        }                                                          //~1220I~
        @Override                                                  //~1220I~
        public View getView(int Ppos, View Pview,ViewGroup Pparent)//~1220I~
        {                                                          //~1220I~
	        TextView heldview,convertview;                                           //~1220I~//~1304R~
	        String item;                                           //~1224I~
            ViewHolder holder;                                     //~1304I~
        //*******************                                      //~1220I~
            if (Dump.Y) Dump.println("Viewer:ListAdapter getview Ppos="+Ppos+",CheckedItemPos="+((ListView)Pparent).getCheckedItemPosition());//~1225R~//~1506R~//~2C15R~
            convertview=(TextView)Pview;                      //~1221I~//~1304R~
          try                                                      //~1A6vI~
          {                                                        //~1A6vI~
            if (convertview==null)      //shown 1st time                                    //~1221I~//~1304R~
            {                                                      //~1221I~
                convertview=(TextView)AG.inflater.inflate(rowId,Pparent,false/*attachToRoot*/);//~1304R~
                holder=new ViewHolder(convertview);                //~1304I~
            	if (Dump.Y) Dump.println("Viewer Pview=null pos="+Ppos);           //~1221I~//~1506R~
                convertview.setTag(holder);                        //~1304I~
            }                                                      //~1221I~
            else                                                   //~1304I~
            {                                                      //~1304I~
                holder=(ViewHolder)convertview.getTag();           //~1304I~
            }                                                      //~1304I~
            item=strarray.get(Ppos);
            heldview=holder.tv;                                    //~1304I~
            if (font!=null)                                        //~1220I~//~1221R~
            {                                                      //~1224I~
                font.setFont(heldview);                                  //~1220I~//~1221R~//~1304R~
//                if (swConnectionFrameViewer)                       //~1224I~//~@@@@R~
//                {                                                  //~1224I~//~@@@@R~
//                    adjustHeaderTextSize(Ppos,font,item,heldview); //~1428R~//~@@@@R~
//                }                                                  //~1224I~//~@@@@R~
            }                                                      //~1224I~
            SpannableString span=spanlist.get(Ppos);               //~1A6vI~
          if (span==null)                                          //~1A6vI~
          {                                                        //~1A6vI~
	    	if (Dump.Y) Dump.println("Viewer:getview str="+item);  //~1A6vI~
            heldview.setText(item);                                      //~1224I~//~1304R~
          }                                                        //~1A6vI~
            heldview.setBackgroundColor(bgColor.getRGB());               //~1220I~//~1304R~
            Color appendtextcolor=colorlist.get(Ppos);             //~1220I~
            if (appendtextcolor!=null)                             //~1220I~
                heldview.setTextColor(appendtextcolor.getRGB());         //~1220I~//~1304R~
            else                                                   //~1220I~
                heldview.setTextColor(textColor.getRGB());               //~1220I~//~1304R~
          	if (span!=null)                                        //~1A6vI~
          	{                                                      //~1A6vI~
            	heldview.setText(span);                            //~1A6vI~
	    		if (Dump.Y) Dump.println("Viewer:getview spannable="+span.subSequence(0,span.length()).toString());//~1A6vI~
          	}                                                      //~1A6vI~
          }                                                        //~1A6vI~
          catch(Exception e)                                       //~1A6vI~
          {                                                        //~1A6vI~
            Dump.println(e,"Viewer:getView");                      //~1A6vI~
          }                                                        //~1A6vI~
            return convertview;                                             //~1220I~//~1304R~
        }                                                          //~1220I~
    }//inner class                                                 //~1220I~
    public class ViewHolder                                        //~1304I~
    {                                                              //~1304I~
    	public TextView tv;                                        //~1304I~
        public ViewHolder(TextView Ptv)                            //~1304I~
        {                                                          //~1304I~
        	tv=Ptv;                                                //~1304I~
        }                                                          //~1304I~
    }                                                              //~1304I~
    private boolean adjustHeaderTextSize(int Ppos,Font Pfont,String Pitem,TextView Pview)//~1428R~
    {                                                              //~1428I~
    	boolean rc=false;                                          //~1428I~
        if (!swEndOfHeader || Ppos<lastHeaderLine)                 //~1428I~
        {                                                          //~1428I~
			if (titleTextSize==0)   //1st time                     //~1428I~
            {                                                      //~1428I~
				FontMetrics fm=Canvas.getFontMetrics(Pfont);       //~1428R~
				int sz1=fm.stringWidth("H");	//"H":save as TetView//~1428R~
				float strsz=sz1*80*AG.dip2pix;                       //~1428R~
//				if (Dump.Y) Dump.println("Viewer sz1="+sz1+",fontsz="+Pfont.getSize()+",fontH="+fm.getHeight());//~1506R~//~2C15R~
                if (strsz>AG.scrWidth)                             //~1428R~
                {                                                  //~1428I~
                    float sz=Pview.getTextSize(); //Unit:pixel     //~1428R~
                    float ftextsz=sz/(strsz/AG.scrWidth);   //~1428R~
                    if (Dump.Y) Dump.println("Viewer init Titletextsz="+sz+",newsz="+ftextsz);//~1506R~
                    titleTextSize=ftextsz;                         //~1428R~
                }                                                  //~1428I~
            }                                                      //~1428I~
			if (titleTextSize!=0)   //1st time                     //~1428I~
            {                                                      //~1428I~
                                                                   //~1428I~
				Pview.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleTextSize);//default is UNIT_SP//~1428I~
				if (Dump.Y) Dump.println("Viewer aftersetTextSize old="+titleTextSize+",new textsz="+Pview.getTextSize());//~1506R~
            }                                                      //~1428I~
            rc=true;                                               //~1428I~
        }                                                          //~1428I~
        return rc;                                                 //~1428I~
    }                                                              //~1428I~
//**********************************************************************//~1220I~//~1221R~//~1301R~
//*run on UI thread                                                //~1220I~//~1221R~//~1301R~
//**********************************************************************//~1220I~//~1221R~//~1301R~
    public void runOnUiThread(int Pcase,Object Pparm)              //~1220I~//~1221R~//~1301R~
    {                                                              //~1220I~//~1221R~//~1301R~
        caseUiThread=Pcase;                                        //~1220I~//~1221R~//~1301R~
        UiThread.runOnUiThreadWait(this,Pparm);               //~1220I~//~1221R~//~1301R~//~@@@@R~
    }                                                              //~1220I~//~1221R~//~1301R~
    @Override                                                      //~1220I~//~1221R~//~1301R~
    public void runOnUiThread(Object Pparm)                        //~1220I~//~1221R~//~1301R~
    {                                                              //~1220I~//~1221R~//~1301R~
        if (Dump.Y) Dump.println("Viewer runOnUi case="+caseUiThread);//~1220I~//~1221R~//~1506R~//~@@@@R~
        switch(caseUiThread)                                       //~1220I~//~1221R~//~1301R~
        {                                                          //~1220I~//~1221R~//~1301R~
        case CASE_APPEND:                                          //~1220I~//~1221R~//~1301R~
            appendUI(Pparm);                                       //~1220I~//~1221R~//~1301R~
            break;                                               //~1221R~//~1301R~
        case CASE_SETTEXT:                                          //~1220I~//~1221R~//~1301R~
            setTextUI(Pparm);                                       //~1220I~//~1221R~//~1301R~
            break;            //~1220I~                          //~1221R~//~1301R~
        case CASE_APPENDSPAN:                                      //~1A6vI~
            appendSpanUI(Pparm);                                   //~1A6vI~
            break;                                                 //~1A6vI~
        case CASE_SETMODE:                                         //+1AefI~
            setModeUI((ListView)Pparm);                            //+1AefI~
            break;                                                 //+1AefI~
        }                                                          //~1220I~//~1221R~//~1301R~
        if (Dump.Y) Dump.println("Viewer runOnUi return case="+caseUiThread);//~@@@@I~
    }                                                              //~1220I~//~1221R~//~1301R~
//*****************************************************            //~1418I~
//*ConnectionFrame Asgts.rene.viewer.Viewer.save()                //~1418I~//~2C26R~//~v101R~
//*****************************************************            //~1418I~
    public void save(PrintWriter Ppw)                              //~1418I~
    {                                                              //~1418I~
    	String line;                                               //~1418I~
        int ctr;                                                   //~1418I~
    //***********************                                      //~1418I~
        ctr=strarray.size();                                       //~1418R~
		for (int ii=0;ii<ctr;ii++)                                     //~1418I~
		{                                                          //~1418I~
        	line=strarray.get(ii);                                 //~1418R~
			Ppw.println(line);                                     //~1418I~
		}                                                          //~1418I~
    }                                                              //~1418I~
}//class                                                           //~1121R~
