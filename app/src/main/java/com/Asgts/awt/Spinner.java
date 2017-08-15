//*CID://+1A40R~:                             update#=    3;       //~1A40R~
//********************************************************************//~1A30I~
//1A40 2014/09/13 adjust for mdpi:HVGA:480x320                     //~1A40I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//********************************************************************//~1A30I~
package com.Asgts.awt;                                            //~1112I~//~2C26R~//~3213R~

import jagoclient.Dump;
import jagoclient.gui.ChoiceAction;

import java.util.ArrayList;



import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.Asgts.AG;                                                //~2C26R~//~3213R~
import com.Asgts.R;

public class Spinner extends Component//android.widget.Spinner                                                  //~1121R~//~1128R~//~1220R~
{                                                                  //~1112I~
                                          //~1128I~
	private android.widget.Spinner spinner;                        //~1425R~
	private ChoiceAction.ChoiceTranslator choiceTranslator;        //~1425R~
    private ArrayAdapter<String> adapter;                          //~1425R~
	private ArrayList<String> strarray;                            //~1425R~
//  private int rowid=android.R.layout.simple_spinner_item;        //~1220R~//+1A40R~
    private static int rowidHdpi=android.R.layout.simple_spinner_item;//+1A40I~
    private static int rowidMdpi=R.layout.spinner_item_mdpi;       //+1A40R~
    private static int rowid;                                      //+1A40I~
    private int itemresid=android.R.layout.simple_spinner_dropdown_item;//~1220R~
    private int selectedPos=-1;                                    //~1220R~
    private Font font;                                            //~1220I~
//*******************                                              //~1128I~
//    public Spinner()                                      //~1121R~//~1128R~//~1A30R~
//    {                                                            //~1A30R~
//        init("Spinner");                                            //~1220I~//~1A30R~
//    }                                                              //~1112I~//~1219R~//~1A30R~
    public Spinner(Container Pcontainer,int Presid)                //~1A30I~
    {                                                              //~1A30I~
    	super(Pcontainer);                                         //~1A30I~
        init(Presid);                                              //~1A30I~
    }                                                              //~1A30I~
//***************                                                  //~1220I~
//    public void init(String Pname)                                 //~1220R~//~1A30R~
//    {                                                            //~1A30R~
//        findView(Pname);                                           //~1220I~//~1A30R~
//        setAdapter();                                               //~1220I~//~1A30R~
//        setListener();                                              //~1220I~//~1A30R~
//    }                                                                                              //~1128I~//~1A30R~
    public void init(int Presid)                                   //~1A30I~
    {                                                              //~1A30I~
        findView(Presid);                                          //~1A30I~
        setAdapter();                                              //~1A30I~
        setListener();                                             //~1A30I~
    }                                                              //~1A30I~
//***************                                                  //~1220I~
//    private void findView(String Pname)                            //~1220I~//~1A30R~
//    {                                                              //~1220I~//~1A30R~
//        spinner=(android.widget.Spinner)AG.findSpinnerView();  //~1220I~//~1331R~//~1A30R~
//        componentView=spinner;  //for Component.requestFocus;      //~1405I~//~1A30R~
//    }                                                              //~1220I~//~1A30R~
    private void findView(int Presid)                              //~1A30I~
    {                                                              //~1A30I~
        spinner=(android.widget.Spinner)parentContainer.findViewById(Presid);//~1A30I~
        componentView=spinner;  //for Component.requestFocus;      //~1A30I~
    }                                                              //~1A30I~
//***************                                                  //~1220I~
    private void setAdapter()                                      //~1220I~
    {                                                              //~1220I~
    	strarray=new ArrayList<String>();                          //~1220I~
        adapter=new ListArrayAdapter(strarray);                    //~1220I~
        spinner.setAdapter(adapter);                               //~1220I~
        adapter.setDropDownViewResource(itemresid);                //~1220M~
    }                                                              //~1220I~
//***************                                                  //~1220I~
    public void setListener()                                           //~1220I~
    {                                                              //~1220I~
        spinner.setOnItemSelectedListener(                       //~1220R~//~1310R~
            new AdapterView.OnItemSelectedListener()             //~1220R~//~1310R~
                {                                                //~1220R~//~1310R~
                    @Override                                    //~1220R~//~1310R~
                    public void onItemSelected(AdapterView<?> Pparent,View Pview,int Ppos,long Pid)//~1220R~//~1310R~
                    {                                            //~1220R~//~1310R~
                        if (Dump.Y) Dump.println("Spinner selected pos="+Ppos);//~1220R~//~1506R~
                        selectedPos=Ppos;                        //~1220R~//~1310R~
                        if (choiceTranslator!=null)                //~1310I~
                        {                                          //~1310I~
                            ItemEvent ev=new ItemEvent(spinner,Ppos,strarray.get(Ppos),ItemEvent.SELECTED);//~1310I~
                            choiceTranslator.itemStateChanged(ev); //~1310I~
                        }                                          //~1310I~
                    }                                            //~1220R~//~1310R~
                    @Override                                    //~1220R~//~1310R~
                    public void onNothingSelected(AdapterView<?> Pparent)//~1220R~//~1310R~
                    {                                            //~1220R~//~1310R~
                        if (Dump.Y) Dump.println("Spinner nothing selected");//~1220R~//~1506R~
                        selectedPos=-1;                          //~1220R~//~1310R~
                    }                                            //~1220R~//~1310R~
                }                                                //~1220R~//~1310R~
                                        );                       //~1220R~//~1310R~
    }                                                              //~1220I~
//*************************                                        //~1128I~
//*************************                                        //~1220I~
    public void add(String Pentry)                                 //~1220I~
    {                                                              //~1220M~
    	adapter.add(Pentry);	                                   //~1220I~
    	if (selectedPos<0)                                          //~1220I~
        {	                                                       //~1220I~
        	setSelection(0);                                       //~1306R~
        }                                                          //~1220I~
    }                                                              //~1220M~
//*************************                                        //~1220I~
    public String getSelectedItem()                                //~1220I~
    {                                                              //~1220I~
	    return getItem(selectedPos);                               //~1220I~
    }                                                              //~1220I~
//*************************                                        //~1220I~
    public int getItemCount()                                      //~1220I~
    {                                                              //~1220I~
    	return strarray.size();                                    //~1220I~
    }                                                              //~1220I~
//*************************                                        //~1220I~
    public String getItem(int Ppos)                                    //~1220I~
    {                                                              //~1220I~
    	if (Ppos<0||Ppos>=strarray.size())                           //~1220I~
        	return null;                                           //~1220I~
    	return strarray.get(Ppos);                                  //~1220I~
    }                                                              //~1220I~
//*************************                                        //~1220I~
    public void setFont(Font Pfont)                              //~1220I~
    {                                                              //~1220I~
    	font=Pfont;                                                //~1220I~
    }                                                              //~1220I~
//*************************                                        //~1306I~
    public void setSelection(int Pindex)                           //~1306I~
    {                                                              //~1306I~
        selectedPos=Pindex;                                        //~1306I~
        spinner.setSelection(Pindex);                              //~1306I~
    }                                                              //~1306I~
//***************                                                  //~1314I~
    public void setSelection(String Psearchstring)                 //~1314I~
    {                                                              //~1314I~
    	int ctr=getItemCount();                                    //+1314I~                     //~1314I~
                                             //~1314I~
        for (int ii=0;ii<ctr;ii++)                                     //~1314I~
        {                                                          //~1314I~
    		if (Psearchstring.equals(getItem(ii)))                   //~1314I~
            {                                                      //~1314I~
            	setSelection(ii);                                        //~1314I~
				return;                                            //~1314I~
            }                                                      //~1314I~
        }                                                          //~1314I~
    }                                                              //~1314I~
//*************************                                        //~1306I~
    public int getSelectedItemPosition()                          //~1306I~
    {                                                              //~1306I~
        return selectedPos;                                        //~1306I~
    }                                                              //~1306I~
//*************************                                        //~1220I~
    public void setTitle(String Ptitle)                          //~1220I~
    {                                                              //~1220I~
    	spinner.setPrompt(Ptitle);                                 //~1220I~
    }                                                              //~1220I~
//*************************                                        //~1220I~
    public void addItemListener(ChoiceAction.ChoiceTranslator Pct) //~1220I~
    {                                                              //~1220I~
    	choiceTranslator=Pct;                                      //~1220I~
    }                                                              //~1220I~
//**********************************************************************//~1220I~
//*ArrayAdapter class                                              //~1220I~
//**********************************************************************//~1220I~
    class ListArrayAdapter extends ArrayAdapter<String>            //~1220I~
    {                                                              //~1220I~
        public ListArrayAdapter(ArrayList<String> Pstrarray)       //~1220I~
        {                                                          //~1220I~
            super(AG.context,rowid,Pstrarray);                     //~1220I~
        }                                                          //~1220I~
        @Override                                                  //~1220I~
        public View getView(int Ppos, View Pview,ViewGroup Pparent)//~1220I~
        {                                                          //~1220I~
	        TextView tv;                                           //~1220I~
        //*******************                                      //~1220I~
            if (Dump.Y) Dump.println("ListAdapter getview Ppos="+Ppos);//~1506R~
            tv=(TextView)super.getView(Ppos,Pview,Pparent);        //~1220I~
            if (Dump.Y) Dump.println("ListAdapter getview Pview==null");//~1506R~
            if (font!=null)                                        //~1220I~
                font.setFont(tv);                                  //~1220I~
            return tv;                                             //~1220I~
        }                                                          //~1220I~
    }//inner class                                                 //~1220I~
//**********************************************************************//+1A40I~
//*from AG                                                         //+1A40I~
//**********************************************************************//+1A40I~
    public static void setLayout()                                      //+1A40I~
    {                                                              //+1A40I~
    	if (AG.screenDencityMdpi)	                               //+1A40I~
            rowid=rowidMdpi;                                       //+1A40I~
         else                                                      //+1A40I~
            rowid=rowidHdpi;                                       //+1A40I~
    }                                                              //+1A40I~
}//class                                                           //~1112I~
