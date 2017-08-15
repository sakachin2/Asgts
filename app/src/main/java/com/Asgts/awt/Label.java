//*CID://+v101R~:                             update#=    8;       //~@@@@I~//~@@@2R~//~v101R~
//***********************************************************************//~v101I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//***********************************************************************//~v101I~
package com.Asgts.awt;                                            //~1112I~//~2C26R~//~v101R~

import com.Asgts.AG;                                                //~2C26R~//~v101R~

import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;                                    //~1112R~

                                                                   //~1112I~
public class Label extends Component                               //~1112R~//~1216R~
                 //~1216I~
{                                                                  //~1112I~
    public TextView textView=null;                                        //~1216R~//~1401R~
    public String label;                                           //~1401I~
    private Container parent=null;                                         //~1216I~
    static int passwordctr=0;                                      //~1216I~
//*******************************                                  //~1216I~
    public Label()                                                 //~1112R~
    {                                                              //~1112I~
        init(null,null);                                           //~1216R~
    }                                                              //~1112I~
    public Label(String Pname)                                         //~1112I~
    {                                                              //~1112I~
        init(findView(Pname),Pname);                                    //~1216R~//~1221R~
    }                                                              //~1112I~
//**from MyLabel****                                               //~@@@2I~
    public Label(int Presid)                                       //~@@@2I~
    {                                                              //~@@@2I~
        init(findView(Presid),null);                               //~@@@2I~
    }                                                              //~@@@2I~
    public Label(Container Pcontainer,int Presid)                  //~@@@2I~
    {                                                              //~@@@2I~
        init(findView(Pcontainer,Presid),null);                    //~@@@2I~
    }                                                              //~@@@2I~
//********************************************                     //~v101I~
    public Label(Container Pcontainer,int Presid,String Plabel)    //~v101I~
    {                                                              //~v101I~
        init(findView(Pcontainer,Presid),Plabel);                  //~v101I~
    }                                                              //~v101I~
//********************************************                     //~v101I~
    public Label(String Pname, int align)    //from rene.MyLabel    //~1511I~
    {                                                              //~1511I~
        this(Pname);                                               //~1511I~
    }                                                              //~1511I~
//********************************************                     //~@@@2I~
    public TextView findView(String Pname)                                     //~1216R~//~1221R~
    {                                                              //~1216I~
    	if (Pname==null || Pname.equals(""))                       //~1221I~
        	return null;                                           //~1221I~
        return (TextView)AG.findLabelView();                       //~1216R~
    }                                                              //~1216I~
//********************************************                     //~@@@2I~
    public TextView findView(int Presid)                           //~@@@2I~
    {                                                              //~@@@2I~
        return (TextView)AG.findViewById(Presid);                  //~@@@2I~
    }                                                              //~@@@2I~
//********************************************                     //~@@@2I~
    public TextView findView(Container Pcontainer,int Presid)      //~@@@2I~
    {                                                              //~@@@2I~
        return (TextView)Pcontainer.findViewById(Presid);          //~@@@2I~
    }                                                              //~@@@2I~
//********************************************                     //~@@@2I~
    private void init(TextView PtextView,String Pname)             //~1216R~
    {   
    	if (PtextView==null)                                       //~1216R~
	        return;                                                //~1216R~
        textView=PtextView;                                        //~1216R~
    	label=Pname;                                               //~1401I~
        if (Pname!=null)                                           //~1216I~
        	setText(Pname);                                        //~1216R~
//      textView.setVisibility(View.VISIBLE);	//reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle//~2C29R~//~@@@@R~
        setVisibility((View)textView,View.VISIBLE);	//reset visibility="gone" on xml of ConnectedGoFrame if !TimerInTitle//~2C29I~//~@@@@R~
    }                                                              //~1216I~
//************                                                     //~1216I~
    public void setText(String Ptext)                              //~1216I~//~1221R~
    {                                                              //~1216I~//~1221R~
        setText(textView,Ptext);	//Component:                                   //~1216I~//~1221R~
    }                                                              //~1216I~//~1221R~
//************                                                     //+v101I~
    public void setText(SpannableString Ptext)                     //+v101I~
    {                                                              //+v101I~
        setText(textView,Ptext);	//Component:                   //+v101I~
    }                                                              //+v101I~
//**for MyLabel                                                    //~1216I~//~1221R~
    public void setFont(Font Pfont)                                     //~1216I~
    {                                                              //~1216I~
    	if (Pfont!=null && textView!=null)                                           //~1216I~//~1221R~
//      	Pfont.setFont(textView);                               //~1216R~//~@@@@R~
      		setFont((View)textView,Pfont);	//by Component,OnUiThread//~@@@@I~
    }                                                              //~1216I~
    public void setBackground(Color Pcolor)                             //~1216I~
    {                                                              //~1216I~
    	if (Pcolor!=null)                                          //~1216I~
        	Pcolor.setBackground(textView);                        //~1216R~
    }                                                              //~1216I~
    public Container getParent()                                   //~1216I~
    {                                                              //~1216I~
    	if (parent==null)                                          //~1216I~
        	parent=new Container();                                //~1216I~
        return parent;                                             //~1216I~
    }                                                              //~1216I~

}//class                                                           //~1112I~
