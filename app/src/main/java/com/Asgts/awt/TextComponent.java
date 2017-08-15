//*CID://+1a4vR~:                             update#=    6;       //+1a4vR~
//********************************************************************//~v101I~
//1A4v 2014/12/07 dislay comment area for replyboard               //+1a4vI~
//1A00 2013/02/13 Asgts                                            //~1a00I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//********************************************************************//~v101I~
package com.Asgts.awt;                                            //~1112I~//~2C26R~//~v101R~

import android.text.SpannableString;
import android.widget.ScrollView;
import android.widget.TextView;

public class TextComponent extends Component                       //~1216R~
{                                                                  //~1112I~
                                                                   //~1218I~
	public TextView textView;	//for TextField                    //~1425R~
	public ScrollView scrollView;	//for TextField                //~1425R~
                                                                   //~1218I~
//***********************************                              //~1218I~
	public TextComponent()                                           //~1216I~
    {                                                              //~1216I~
    }                                                              //~1216I~
	public TextComponent(TextView Ptv)                             //~1a00I~
    {                                                              //~1a00I~
    	textView=Ptv;                                              //~1a00I~
    }                                                              //~1a00I~
	public TextComponent(Container Pcontainer)                     //~v101I~
    {                                                              //~v101I~
    	super(Pcontainer);                                         //~v101I~
    }                                                              //~v101I~
    public void setText(String Ptext)                              //~1216I~
    {                                                              //~1216I~
    	setText(textView,Ptext);	//by Component                                    //~1216I~//~1221R~//~1310R~
    }                                                              //~1216I~
//*****************                                                //~1218I~
    public void append(String Pline)        //~1218R~              //~1219R~
    {                                                              //~1218I~
		append(Pline,null);     //by Component                     //~1221R~
    }                                                              //~1218I~
//*****************                                                //~v101I~
    public void append(String Pline,Color Pcolor)                  //~1219I~
    {                                                              //~1219I~
		append(textView,scrollView,Pline,Pcolor);                  //~1221R~
    }                                                              //~1219I~
//*****************                                                //~v101I~
    public void append(SpannableString Pspanline)                  //~v101I~
    {                                                              //~v101I~
		append(textView,scrollView,Pspanline);                     //~v101I~
    }                                                              //~v101I~
//***********************                                          //~1310I~
    public void setBackground(Color Pcolor)                        //~1310I~
    {                                                              //~1310I~
    	setBackground(textView,Pcolor);   //by Component           //~1310I~
    }                                                              //~1310I~
//***********************                                          //~1312I~
    public void setBackground(Color Pcolor,Color Ptextcolor)       //~1312I~
    {                                                              //~1312I~
    	setBackground(textView,Pcolor,Ptextcolor);   //by Component//~1312I~
    }                                                              //~1312I~
//***********************                                          //~1310I~
    public void setFont(Font Pfont)                                //~1310I~
    {                                                              //~1310I~
    	setFont(textView,Pfont);   //by Component                  //~1310I~
    }                                                              //~1310I~
//***********************                                          //+1a4vI~
    public void scrollTo(int Ppos)                                 //+1a4vI~
    {                                                              //+1a4vI~
    	if (scrollView!=null)                                      //+1a4vI~
	    	scrollTo(scrollView,Ppos);	//Component                //+1a4vI~
    }                                                              //+1a4vI~
                                                                   //~1310I~
}//class                                                           //~1112I~
