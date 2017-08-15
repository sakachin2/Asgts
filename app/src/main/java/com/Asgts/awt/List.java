//*CID://+1Ah8R~: update#= 214;                                    //~1Ah8R~
//**********************************************************************//~1107I~
//1Ah8 2016/11/15 FileDialog:support select all button             //~1Ah8I~
//1AbS 2015/07/03 BT:LeastRecentlyUsed List for once conneceted device list//~1AbSR~
//1A6n 2015/02/15 IP;identify connected device                     //~1A6nI~
//1A6f 2015/02/13 support cutom layout of ListView                 //~1A6fI~
//1A4n 2014/12/04 FileDialog:multiple delete support               //~1A4nI~
//1A4a 2014/11/29 FileDialog:open when selected item is clicked    //~1A4aI~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//101a 2013/01/30 Asgts IP connection                              //~v101R~
//1079:121208 Nexus7(Android4.2)listview touch dose not call getView(),litview item is not highlightened//~v107I~
//1053:121113 exception(wrong thread) when filelist up/down for sgf file read//~v105I~
//**********************************************************************//~v105I~
//*My ListView Adapter                                                     //~1107I~//~1109R~
//**********************************************************************//~1107I~
package com.Asgts.awt;                                         //~1107R~  //~1108R~//~1109R~//~1114R~//~v107R~//~v101R~

import jagoclient.Dump;

import java.util.ArrayList;

import com.Asgts.AG;                                                //~v107R~//~v101R~
import com.Asgts.awt.ActionListener;                                //~v107R~//~v101R~
import com.Asgts.awt.Color;                                         //~v107R~//~v101R~
import com.Asgts.awt.Font;                                          //~v107R~//~v101R~

import android.view.MotionEvent;
import android.view.View;                                          //~1109I~
import android.view.View.OnTouchListener;
import android.view.ViewGroup;                                     //~1109I~
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
                                                                   //~1109I~
//class ListData                                                     //~1220I~//~1A6fR~
//{                                                                  //~1220I~//~1A6fR~
//    public String itemtext;                                        //~1220I~//~1A6fR~
//    public Color  itemcolor;                                       //~1220I~//~1A6fR~
//    public boolean choosed;                                        //~1A4nI~//~1A6fR~
//    public ListData(String Pitem,Color Pcolor)                     //~1220I~//~1A6fR~
//    {                                                              //~1220I~//~1A6fR~
//        itemtext=Pitem;                                            //~1220I~//~1A6fR~
//        itemcolor=Pcolor;                                          //~1220I~//~1A6fR~
//    }                                                              //~1220I~//~1A6fR~
//}                                                                  //~1220I~//~1A6fR~
public class List                                                  //~1114           //~1220I~
{                                                                  //~1110I~
	public ListView listview;                                      //~1425R~
	private ArrayAdapter<ListData> adapter;                        //~1425R~
//  private ArrayList<ListData> arrayData;                         //~1425R~//~1A6fR~
    public  ArrayList<ListData> arrayData;                         //~1A6fI~
//  private int resourceid=AG.viewId_ListView;                     //~1220I~//~1A40R~
    private int resourceid;                                        //~1A40I~
    private Component component;                                                                //~1220I~
	protected Font font;                                           //~1425R~
	private ActionListener listener;                               //~1425R~
//  private int selectedpos=AdapterView.INVALID_POSITION;          //~1118I~//~1A6fR~
    protected int selectedpos=AdapterView.INVALID_POSITION;        //~1A6fI~
//  private int rowId=AG.listViewRowId;                            //~1220R~//~1A6fR~
    protected int rowId=AG.listViewRowId;                          //~1A6fI~
	protected Color bgColor=Color.white;                                      //~1112I~//~1219R~
	protected Color bgColorSelected=Color.blue.darker().darker();  //~1219R~
	protected Color bgColorChoosed=Color.yellow;                   //~1A4nI~
//  private View oldItemView;                                                               //~1220I~//~v107R~//~1A4aR~
    private Container container;                                   //~v101I~
    private int mode=ListView.CHOICE_MODE_NONE;                    //~1A4nI~
                                                                   //~v107I~
//*****************                                                //~1112I~
//    public List()                                               //~1112I~//~1114R~//~v101R~
//    public List(Container Pcontainer)                              //~v101I~//~1A40R~
//    {                                                              //~1112I~//~1A40R~
//        container=Pcontainer;                                      //~v101I~//~1A40R~
//        init();                                                    //~1220R~//~1A40R~
//    }                                                              //~1112I~//~1A40R~
//*****************                                                //~1220I~
//    public List(int Presid,int Prowresid)                                        //~1220I~//~v101R~
    public List(Container Pcontainer,int Presid,int Prowresid)     //~v101I~
    {                                                              //~1220I~
    	container=Pcontainer;                                      //~v101I~
	    rowId=Prowresid;                                           //~v101I~
    	resourceid=Presid;                                         //~1220I~
    	init();                                                    //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void init()                                   //~1220I~
    {   
    	component=new Component();//~1220I~
    	arrayData=new ArrayList<ListData>();                       //~1220R~
    	setAdapter();                                              //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1112I~
    public void add(String Pitem)                                   //~1112I~//~1220R~
    {                                                              //~1112I~
    	add(Pitem,Color.black);                                        //~1112I~//~1403R~
    }                                                              //~1112I~
//*****************                                                //~1A6fI~
    public void add(String Pitem,String Pitem2,int Pint)           //~1A6fI~
    {                                                              //~1A6fI~
    	ListData ld=add(Pitem,Color.black);                        //~1A6fI~
        ld.itemtext2=Pitem2;                                       //~1A6fI~
        ld.itemint=Pint;                                           //~1A6fI~
    }                                                              //~1A6fI~
//*****************                                                //~1220I~
//  public void add(String Pitem,Color Pcolor)                     //~1220I~//~1A6fR~
    public ListData add(String Pitem,Color Pcolor)                 //~1A6fI~
    {                                                              //~1220I~
//  	arrayData.add(new ListData(Pitem,Pcolor));                   //~1220I~//~1A6fR~
    	ListData ld=new ListData(Pitem,Pcolor);                    //~1A6fI~
    	arrayData.add(ld);                                         //~1A6fI~
        if (!(listview.isFocusable()))	//setFocusable is effective when not empty)//~1403R~
        {                                                          //~1403I~
            if (Dump.Y) Dump.println("List add selected position="+listview.getSelectedItemPosition());//~1506R~
			listview.setSelection(0);	//#### setSelection() is ignored if isInTouchMode()//~1403I~
            listview.setItemChecked(0,true); //call getView();setSelection() may move cursor//~1403I~
            if (Dump.Y) Dump.println("List add isFocusable="+listview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("List add isFocusableInTouchMode="+listview.isFocusableInTouchMode());//~1506R~
            listview.setFocusableInTouchMode(true);   //##### set desiredfocusableInTouchMode state if empty(set also focusable setting)//~1403R~
                                                      //#### setFocusableInToyuchMode() means also setFocusable()//~1403R~
            if (Dump.Y) Dump.println("List add selected position="+listview.getSelectedItemPosition());//~1506R~
            if (Dump.Y) Dump.println("List add isFocusable="+listview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("List add isFocusableInTouchMode="+listview.isFocusableInTouchMode());//~1506R~
        	listview.requestFocus();                               //~1403I~
    		listview.setItemChecked(selectedpos,true);             //~1403I~
        }                                                          //~1403I~
        return ld;                                                 //~1A6fI~
    }                                                              //~1220I~
//*****************                                                //~1A6fI~
    public ListData update(String Pitem,String Pitem2)             //~1A6fI~
    {                                                              //~1A6fI~
    	int sz=arrayData.size();                                   //~1A6fI~
        for (int ii=0;ii<sz;ii++)                                  //~1A6fI~
        {                                                          //~1A6fI~
    		ListData ld=arrayData.get(ii);                         //~1A6fI~
            if (Pitem.equals(ld.itemtext))                         //~1A6fI~
            {                                                      //~1A6fI~
            	ld.itemtext2=Pitem2;                               //~1A6fI~
                return ld;                                         //~1A6fI~
            }                                                      //~1A6fI~
        }                                                          //~1A6fI~
        return null;                                               //~1A6fI~
    }                                                              //~1A6fI~
//*****************                                                //~1AbSR~
    public void remove(int Pindex)                                 //~1AbSR~
    {                                                              //~1AbSR~
    	ArrayList<ListData> newlist=new ArrayList<ListData>();     //~1AbSR~
    	int sz=arrayData.size();                                   //~1AbSR~
        for (int ii=0;ii<sz;ii++)                                  //~1AbSR~
        {                                                          //~1AbSR~
        	if (ii==Pindex)                                        //~1AbSR~
            	continue;                                          //~1AbSR~
    		ListData ld=arrayData.get(ii);                         //~1AbSR~
	    	newlist.add(ld);                                       //~1AbSR~
        }                                                          //~1AbSR~
    	setAdapter(newlist);                                       //~1AbSR~
    }                                                              //~1AbSR~
//*****************                                                //~1112I~
    public void setFont(Font Pfont)                                //~1112I~
    {                                                              //~1112I~
    	font=Pfont;                                                //~1112I~                                //~1219I~
    }                                                              //~1112I~
//*****************                                                //~1220I~
    public void setText(String Ptext)                              //~1220I~
    {                                                              //~1220I~
        removeAll(); //removeRange is protected                    //~1220R~
    	add(Ptext);                                                //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1112I~
    public void setBackground(Color Pcolor)                        //~1112R~
    {                                                              //~1112I~
    	bgColor=Pcolor;                                              //~1112I~//~1219R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public void addActionListener(ActionListener Plistener)        //~1112I~
    {                                                              //~1112I~
    	listener=Plistener;             //@@@@ not used?           //~1324R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    private void setAdapter()            //~1112I~//~1114R~     //~1219R~//~1220R~
    {                                                              //~1112I~
//      listview=(ListView)AG.findViewById(resourceid);    //~1219I~//~1220R~//~v101R~
        listview=(ListView)container.findViewById(resourceid);     //~v101I~
        adapter=new ListArrayAdapter(arrayData);//~1112I~//~1114R~  //~1219R~//~1220R~
        listview.setAdapter(adapter);                                    //~1112I~//~1114R~
    	setMode(listview);                                         //~1219I~
    }                                                              //~1112I~
//*****************                                                //~1AbSR~
    private void setAdapter(ArrayList<ListData> Plistdata)         //~1AbSR~
    {                                                              //~1AbSR~
    	arrayData=Plistdata;                                       //~1AbSR~
        adapter=new ListArrayAdapter(arrayData);                   //~1AbSR~
        listview.setAdapter(adapter);                              //~1AbSR~
    }                                                              //~1AbSR~
//*****************                                                //~1112I~
    private void setMode(ListView Plv)                             //~1112I~
    {                                                              //~1112I~
        if (Dump.Y) Dump.println("List setMode isFocusable="+Plv.isFocusable());//~1506R~
        if (Dump.Y) Dump.println("List setmode isFocusableInTouchMode="+Plv.isFocusableInTouchMode());//~1506R~
        Plv.setFocusableInTouchMode(true); //fail if Array is empty//~1403R~
        if (Dump.Y) Dump.println("List setMode isFocusable="+Plv.isFocusable());//~1506R~
        if (Dump.Y) Dump.println("List setmode isFocusableInTouchMode="+Plv.isFocusableInTouchMode());//~1506R~
        Plv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);            //~1112I~
		Plv.setItemsCanFocus(true);                                 //~1115I~
        OnItemClickListener licl=new ListItemClickListener();          //~1115I~
        Plv.setOnItemClickListener(licl);                            //~1115I~
        OnItemLongClickListener lilcl=new ListItemLongClickListener();//~1307I~
        Plv.setOnItemLongClickListener(lilcl);                     //~1307I~
        OnItemSelectedListener lisl=new ListItemSelectedListener();//~1118I~
        Plv.setOnItemSelectedListener(lisl);                       //~1118I~
        if (Dump.Y) Dump.println("List setMode isFocusable="+Plv.isFocusable());//~1506R~
        if (Dump.Y) Dump.println("List setmode isFocusableInTouchMode="+Plv.isFocusableInTouchMode());//~1506R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public int getSelectedPos()                                    //~1403R~
    {                                                              //~1403I~
    	return selectedpos;                                        //~1403I~
    }                                                              //~1403I~
//*****************                                                //~v101I~
    public int getValidSelectedPos()                               //~v101I~
    {                                                              //~v101I~
    	int pos=selectedpos;                                       //~v101I~
        if (pos==AdapterView.INVALID_POSITION||pos>=arrayData.size())//~v101I~
        	return -1;                                             //~v101I~
        return pos;                                                //~v101I~
    }                                                              //~v101I~
    public String getSelectedItem()                                //~1112I~
    {                                                              //~1112I~
    	int pos;                                                   //~1118R~
    	pos=listview.getCheckedItemPosition();                     //~1118I~
        if (Dump.Y) Dump.println("Listview getCheckedItem pos="+pos);//~1506R~
    	pos=listview.getSelectedItemPosition();	//trackball selection//~1115I~//~1118R~
        if (Dump.Y) Dump.println("Listview getSelectedItem pos="+pos);//~1506R~
        if (Dump.Y) Dump.println("selected item="+listview.getSelectedItem());//~1506R~
        pos=selectedpos;                                            //~1118I~
        if (Dump.Y) Dump.println("Listview selectedpos="+pos);     //~1506R~
        if (pos==AdapterView.INVALID_POSITION||pos>=arrayData.size())                       //~1114I~//~1118R~//~1220R~
        	return "";                                             //~1114I~
//      String item=adapter.getItem(pos);                          //~1118R~
        String item=arrayData.get(pos).itemtext;                             //~1118I~//~1220R~
        if (Dump.Y) Dump.println("Listview selectedpos="+item);    //~1506R~
        return item;                                               //~1118R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public int getItemCount()                                      //~1112I~
    {                                                              //~1112I~
        return arrayData.size();                                  //~1112I~//~1220R~
    }                                                              //~1112I~
//*****************                                                //~1112I~
    public void select(int Ppos)                                 //~1112I~
    {                                                              //~1112I~
    	selectedpos=Ppos;                                          //~1118M~
        if (Dump.Y) Dump.println("Listview select(setSlectedItem) req="+Ppos+",slected="+listview.getSelectedItemPosition());//~1506R~
        if (Dump.Y) Dump.println("Listview select(setItemChecked) pos="+listview.getCheckedItemPosition());//~1506R~
        listview.requestFocus();                                   //~1118I~
//    	listview.setItemChecked(Ppos,true);                        //~1118I~//~1403I~//~v105R~
        setItemChecked(Ppos,true);                                 //~v105I~
        if (Dump.Y) Dump.println("Listview select isfocused="+listview.isFocused());//~1118I~//~1A4nR~
    }                                                              //~1112I~
//*****************                                                //~v105I~
    public void setItemChecked(int Ppos,boolean Pstate)            //~v105I~
    {                                                              //~v105I~
        component.setItemChecked(listview,Ppos,Pstate);            //~v105I~
    }                                                              //~v105I~
//*****************                                                //~1215I~
    public void removeAll()	//for messageFilter                    //~1215I~
    {                                                              //~1215I~
        arrayData.clear();  //removeRange is protected             //~1220I~
		selectedpos=AdapterView.INVALID_POSITION;                  //~1411I~
    	if (AG.isMainThread())                                     //~1317I~
			adapter.notifyDataSetChanged(); //delete "deleted entry" from list shown//~1317I~
    }                                                              //~1215I~
//*****************                                                //~1220I~
    public void showBottom()                                       //~1220I~
    {                                                              //~1220I~
        component.showList(listview,arrayData.size()-1);             //~1221I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void showList()                                         //~1220I~//~1221R~
    {                                                              //~1220I~
        component.showList(listview,-1/*keep currentpos*/);          //~1221I~
    }                                                              //~1220I~
//**********************************************************************//~1111I~
//*ArrayAdapter class                                              //~1111I~
//**********************************************************************//~1111I~
    class ListArrayAdapter extends ArrayAdapter<ListData>           //~1111I~//~1112R~//~1114R~
    {                                                              //~1111I~
        public ListArrayAdapter(ArrayList<ListData> ParrayData)//~1111I~//~1112R~//~1114R~//~1219R~
        {                                                          //~1111I~
            super(AG.context,rowId,ParrayData);         //~1111I~//~1211R~//~1219R~
        }                                                          //~1111I~
        @Override                                                  //~1111I~
        public View getView(int Ppos, View Pview,ViewGroup Pparent)//~1111I~
        {                                                          //~1111I~
	        TextView tv;                                           //~1115I~
                                         //~1219I~
        //*******************                                      //~1115I~
            if (Dump.Y) Dump.println("List:ListArrayAdapter getview Ppos="+Ppos+"CheckedItemPos="+((ListView)Pparent).getCheckedItemPosition());//~1506R~//~v107R~
            View customView=getViewCustom(Ppos,Pview,Pparent);     //~1A6fI~
            if (customView!=null) 	//overidden by ListCustom      //~1A6fI~
            	return customView;                                 //~1A6fI~
            tv=(TextView)super.getView(Ppos,Pview,Pparent);
//          if (Dump.Y) Dump.println("ListAdapter getview Pview==null");//~1115M~//~1219R~//~1506R~//~v107R~
            if (font!=null)                                        //~1111I~//~1115M~//~1219R~//~1220R~
                font.setFont(tv);
            ListData ld=arrayData.get(Ppos);//~1111I~//~1115M~//~1219R~//~1220R~
            tv.setText(ld.itemtext);
          if (mode==ListView.CHOICE_MODE_MULTIPLE)                 //~1A4nI~
          {                                                        //~1A4nI~
          	if (ld.choosed)                                        //~1A4nI~
            {                                                      //~1A4nI~
                tv.setBackgroundColor(bgColorChoosed.getRGB());    //~1A4nI~
            }                                                      //~1A4nI~
            else                                                   //~1A4nI~
            {                                                      //~1A4nI~
                tv.setBackgroundColor(bgColor.getRGB());           //~1A4nI~
            }                                                      //~1A4nI~
            tv.setTextColor(ld.itemcolor.getRGB());                //~1A4nI~
          }                                                        //~1A4nI~
          else                                                     //~1A4nI~
          {                                                        //~1A4nI~
            if (Ppos==selectedpos)                             //~1219I~//~1220R~
            {                                                  //~1219I~//~1220R~
                tv.setBackgroundColor(bgColorSelected.getRGB());//~1219R~//~1220R~
                tv.setTextColor(bgColor.getRGB());            //~1219R~//~1220R~
            }                                                  //~1219I~//~1220R~
            else                                               //~1219I~//~1220R~
            {                                                  //~1219I~//~1220R~
                tv.setBackgroundColor(bgColor.getRGB());       //~1219I~//~1220R~
                tv.setTextColor(ld.itemcolor.getRGB());           //~1219I~//~1220R~
            }                                                  //~1219I~//~1220R~
          }                                                        //~1A4nI~
			getViewAdjust(Ppos,tv,Pparent,ld,selectedpos);         //~1A6nR~
            return tv;                                             //~1111I~
        }                                                          //~1111I~
    }//inner class                                                 //~1111I~
//**********************************************************************//~1115I~
//*itemclicklistener                                               //~1115I~
//**********************************************************************//~1115I~
    class ListItemClickListener implements OnItemClickListener     //~1115I~
    {                                                              //~1115I~
    	@Override                                                  //~1115I~
        public void onItemClick(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1115R~
        {                                                              //~v@@@I~//~1115I~
          try                                                      //~v107I~
          {                                                        //~v107I~
            if (Dump.Y) Dump.println("List OnItemClick pos="+Ppos);                //~v@@@R~//~1506R~
                                                                   //~1A4nI~
            if (mode==ListView.CHOICE_MODE_MULTIPLE)               //~1A4nR~
            	updateChoice(Ppos);                                //~1A4nI~
            if (Dump.Y) Dump.println("Listview OnItemClick getCheckedItemPos="+Plistview.getSelectedItemPosition());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusable="+Plistview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusableInTouchMode="+Plistview.isFocusableInTouchMode());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick textview isInTouchMode="+Ptextview.isInTouchMode());//~1506R~
            Plistview.requestFocusFromTouch();                     //~1118R~
//          listview.setItemChecked(Ppos,true); //call getView();setSelection() may move cursor//~v105I~//~v107R~
			((BaseAdapter)Plistview.getAdapter()).notifyDataSetChanged(); //invalidate is not effective to call getView()//~v107I~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusable="+Plistview.isFocusable());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick listview isFocusableInTouchMode="+Plistview.isFocusableInTouchMode());//~1506R~
            if (Dump.Y) Dump.println("Listview OnItemClick textview isInTouchMode="+Ptextview.isInTouchMode());//~1506R~
//*how avoid listview scroll by setSelectionAfterHeader() after setSelection()?//~1118R~
            if (container.listInterface!=null)                     //~1A4aI~
	            container.listInterface.onListItemClicked(Ppos,selectedpos);//~1A4aI~
          }                                                        //~v107I~
          catch(Exception e)                                       //~v107I~
          {                                                        //~v107I~
          	Dump.println(e,"List:onItemClick");                    //~v107I~
          }                                                        //~v107I~
            selectedpos=Ppos;                                      //~1118I~
        }                                                          //~1115I~

    }//inner class                                                 //~1115I~
//**********************************************************************//~1307I~
//*itemclicklistener  LONG                                         //~1307R~
//**********************************************************************//~1307I~
    class ListItemLongClickListener implements OnItemLongClickListener//~1307I~
    {                                                              //~1307I~
    	@Override                                                  //~1307I~
        public boolean onItemLongClick(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1307I~
        {                                                          //~1307I~
            if (Dump.Y) Dump.println("List OnItemClick pos="+Ppos);//~1506R~
                                                                   //~1307I~
            if (Dump.Y) Dump.println("Listview OnItemLongClick getCheckedItemPos="+Plistview.getSelectedItemPosition());//~1506R~
            Plistview.requestFocusFromTouch();                     //~1307I~
            if (Dump.Y) Dump.println("Listview OnItemLongClick listview requestfocusfromtouch="+Plistview.requestFocusFromTouch());//~1506R~
//          Plistview.setSelection(Ppos);                          //~1307R~
            selectedpos=Ppos;   //setSelction() cause scroll       //~1307I~
//          Ptextview.invalidate();                                //~1307R~
			((BaseAdapter) Plistview.getAdapter()).notifyDataSetChanged(); //invalidate is not effective to call getView()//~1307I~
            return false;	//continue to ContextMenu processing   //~1307I~
        }                                                          //~1307I~
                                                                   //~1307I~
    }//inner class                                                 //~1307I~
//**********************************************************************//~1118I~
//*itemcselectedlistener  for keyboard up/down                     //~1403R~
//**********************************************************************//~1118I~
    class ListItemSelectedListener implements OnItemSelectedListener//~1118I~
    {                                                              //~1118I~
    	@Override                                                  //~1118I~
        public void onItemSelected(AdapterView<?> Plistview,View Ptextview,int Ppos,long Pid)//~1118I~
        {                                                          //~1118I~
            if (Dump.Y) Dump.println("List OnItemSelected pos="+Ppos);//~1506R~
            selectedpos=Ppos;                                      //~1118I~
            listview.setItemChecked(Ppos,true); //call getView();setSelection() may move cursor//~1403R~
        }                                                          //~1118I~
    	@Override
    	public void onNothingSelected(AdapterView<?> arg0) {
    		if (Dump.Y) Dump.println("List OnItemSelected Nothing");//~1506R~
    	}
    }//inner class                                                 //~1118I~
//**********************************************************************//~1115I~
//*itemclicklistener                                               //~1115I~
//**********************************************************************//~1115I~
    class ListTouchListener implements OnTouchListener             //~1115I~
    {                                                              //~1115I~
    	@Override                                                  //~1115I~
        public boolean onTouch(View view,MotionEvent event)        //~1115I~
        {                                                          //~1115I~
            if (Dump.Y) Dump.println("List OnTouch");              //~1506R~
            return false;                                              //~@@@@I~//~1115I~
        }                                                              //~0914I~//~1115I~
    }//ListTouchListener                                           //~1115I~
//**********************************************************************//~1A4nI~
    public void setChoiceMode(int Pchoicemode)                     //~1A4nR~
    {                                                              //~1A4nI~
    	if (Pchoicemode>1)                                         //~1A4nI~
        {                                                          //~1A4nI~
        	mode=ListView.CHOICE_MODE_MULTIPLE;                    //~1A4nI~
	        int pos=selectedpos;                                   //~1A4nI~
    	    listview.setItemChecked(pos,false); //call getView();setSelection() may move cursor//~1A4nI~
        }                                                          //~1A4nI~
        else                                                       //~1A4nI~
        {                                                          //~1A4nI~
        	clearChoosed();                                        //~1A4nI~
            if (Pchoicemode==1)                                    //~1A4nR~
                mode=ListView.CHOICE_MODE_SINGLE;                  //~1A4nR~
            else                                                   //~1A4nR~
                mode=ListView.CHOICE_MODE_NONE;                    //~1A4nR~
        }                                                          //~1A4nI~
	    selectedpos=AdapterView.INVALID_POSITION;                  //~1A4nR~
        listview.setChoiceMode(mode);                               //~1A4nI~
		adapter.notifyDataSetChanged(); //delete "deleted entry" from list shown//~1A4nI~
        if (Dump.Y) Dump.println("List:setChoiceMode: notifydataSetChanged sw="+Pchoicemode+",mode="+mode);//~1A4nI~//~1Ah8R~
    }                                                              //~1A4nI~
//**********************************************************************//~1A4nI~
    private void clearChoosed()                                     //~1A4nI~
    {                                                              //~1A4nI~
        int ctr=arrayData.size();                                  //~1A4nI~
        for (int ii=0;ii<ctr;ii++)                                 //~1A4nI~
        {                                                          //~1A4nI~
            ListData ld=arrayData.get(ii);                         //~1A4nI~
            ld.choosed=false;                                      //~1A4nI~
        }                                                          //~1A4nI~
    }                                                              //~1A4nI~
//*****************                                                //~1A4nI~
    public int[] getCheckedItemPositions()                         //~1A4nR~
    {                                                              //~1A4nI~
        int ctr=arrayData.size();                                  //~1A4nI~
        int selctr=0;                                              //~1A4nI~
        for (int ii=0;ii<ctr;ii++)                                 //~1A4nI~
        {                                                          //~1A4nI~
            ListData ld=arrayData.get(ii);                         //~1A4nI~
            if (ld.choosed)                                       //~1A4nI~
            	selctr++;                                          //~1A4nI~
        }                                                          //~1A4nI~
        int[] rc=new int[selctr];                                  //~1A4nI~
        selctr=0;                                                  //~1A4nI~
        for (int ii=0;ii<ctr;ii++)                                 //~1A4nI~
        {                                                          //~1A4nI~
            ListData ld=arrayData.get(ii);                         //~1A4nI~
            if (ld.choosed)                                       //~1A4nI~
            	rc[selctr++]=ii;                                   //~1A4nI~
        }                                                          //~1A4nI~
		return  rc;                                                //~1A4nR~
    }                                                              //~1A4nI~
//*****************                                                //~1A4nI~
    private int updateChoice(int Ppos)                             //~1A4nI~
    {                                                              //~1A4nI~
    	int rc=-1;                                                 //~1A4nI~
        if (Ppos>=0 && Ppos<arrayData.size())                      //~1A4nI~
        {                                                          //~1A4nI~
        	int rc2=4;                                             //~1A4nR~
            if (container.listInterface!=null)                     //~1A4nI~
	            rc2=container.listInterface.onListItemClickedMultiple(Ppos);//chk selectable//~1A4nR~
            if (rc2==0)                                            //~1A4nI~
            {                                                      //~1A4nI~
        		ListData ld=arrayData.get(Ppos);                   //~1A4nR~
            	boolean c=ld.choosed;                              //~1A4nR~
            	ld.choosed=!c;                                     //~1A4nR~
            	if (Dump.Y) Dump.println("List MultipleChoce pos="+Ppos+",choiced="+ld.choosed);//~1A4nR~
            	rc=ld.choosed?1:0;                                 //~1A4nR~
            }                                                      //~1A4nI~
        }                                                          //~1A4nI~
        return rc;                                                  //~1A4nI~
    }                                                              //~1A4nI~
//**********************************************************************//~1A6fI~
//*overridden by ListCustom                                        //~1A6fI~
//**********************************************************************//~1A6fI~
	protected View getViewCustom(int Ppos, View Pview,ViewGroup Pparent)//~1A6fI~
    {                                                              //~1A6fI~
    	return null;                                               //~1A6fI~
    }                                                              //~1A6fI~
//**********************************************************************//~1A6nR~
	protected void getViewAdjust(int Ppos, TextView Pview, ViewGroup Pparent,ListData Plistdata,int Pselectedpos)//~1A6nR~
	{                                                              //~1A6nR~
	}                                                              //~1A6nR~
//**********************************************************************//~1Ah8I~
	protected void selectAll(int Ppos,String Ppostfix)             //+1Ah8R~
	{                                                              //~1Ah8I~
        int sz=arrayData.size();                                   //~1Ah8I~
        if (Dump.Y) Dump.println("List:selectAll pos="+Ppos+",posfix="+Ppostfix+",size="+sz);//+1Ah8R~
        if (Ppos<sz)                                               //~1Ah8I~
        {                                                          //~1Ah8I~
            for (int ii=Ppos;ii<arrayData.size();ii++)             //~1Ah8I~
            {                                                      //~1Ah8I~
                ListData ld=arrayData.get(ii);                     //~1Ah8I~
                if (!ld.itemtext.endsWith(Ppostfix))    //skip diirectory//+1Ah8R~
                {                                                  //+1Ah8R~
                    ld.choosed=true;                               //+1Ah8R~
                    if (Dump.Y) Dump.println("List:selectAll selected pos="+ii+",itemtext="+ld.itemtext);//+1Ah8R~
                }                                                  //+1Ah8R~
            }                                                      //~1Ah8I~
            setChoiceMode(2/*multiple*/);      //do dataChanged    //~1Ah8I~
        }                                                          //~1Ah8I~
	}                                                              //~1Ah8I~
//**********************************************************************//+1Ah8I~
	protected void deselectAll()                                   //+1Ah8I~
	{                                                              //+1Ah8I~
        if (Dump.Y) Dump.println("List:deselectAll");              //+1Ah8I~
        setChoiceMode(1/*single*/);      //do dataChanged          //+1Ah8I~
	}                                                              //+1Ah8I~
}//class                                                           //~1109I~
