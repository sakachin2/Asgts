//*CID://+v101R~:                             update#=  12;        //~v108I~//~@@@@R~//~@@@2R~//~v101R~
//**********************************************************************//~v105I~//~v108I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//1089:121117 confirm requestFocus runOnUiThread                   //~v108I~
//**********************************************************************//~v108I~
package com.Asgts.awt;                                            //~1112I~//~v108R~//~v101R~

import jagoclient.Dump;

import com.Asgts.AG;                                                //~v108R~//~v101R~
import com.Asgts.AKey;                                          //~v108R~//~@@@@R~//+v101R~
import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

                                                                   //~1112I~
public class TextField extends TextComponent// extends TextView                            //~1112R~//~1116R~//~1127R~//~1216R~
                                     //~1114I~
{                                                                  //~1112I~
    private Color defaultFG=Color.black;                           //~1307I~//~1312R~
    private Color defaultBG=Color.gray;                            //~1502I~
                                                                   //~1216I~
    private AKey  tfkl;                                        //~1425R~//~@@@@R~
    private int row=-1;                                            //~1216I~
    private Dialog containerDialog;                                //~1425R~
    private int colWidth;                                              //~1503I~
    private int resId;                                             //~@@@@I~
//*********************                                            //~1216I~
    public TextField()                                             //~1112R~
    {                                                              //~1112I~
        init(null,0);                                              //~1216I~
    }                                                              //~1112I~
    public TextField(String Ptext)                                     //~1113I~//~1312R~
    {                                                              //~1113I~
        init(Ptext,0);                                                 //~1216I~//~1312R~
    }                                                              //~1113I~
    public TextField(int n)                                        //~1113I~//~1216M~
    {                                                              //~1113I~//~1216M~
    	init(null,n);                                              //~1216I~
    }                                                              //~1113I~//~1216M~
    public TextField(String Ptext,int n)                               //~1113I~//~1216M~//~1312R~
    {                                                              //~1113I~//~1216M~
    	init(Ptext,n);                                                 //~1216I~//~1312R~
    }                                                              //~1113I~//~1216M~
    public TextField(int Prow,int Pcol) //for text Area            //~1216I~
    {                                                              //~1216I~
    	row=Prow;                                                  //~1216I~
    	init(null,Pcol);                                           //~1216I~
    }                                                              //~1216I~
    public TextField(String Ptext,int Prow,int Pcol)                   //~1216I~//~1312R~
    {                                                              //~1216I~
    	row=Prow;                                                  //~1216I~
    	init(Ptext,Pcol);                                              //~1216I~//~1312R~
    }                                                              //~1216I~
//************************************************************     //~@@@2I~
//**from TextArea(<--MyTextArea)                                   //~@@@2I~
//************************************************************     //~@@@2I~
    public TextField(String Ptext,int Presid,int Prow,int Pcol)    //~@@@2I~
    {                                                              //~@@@2I~
    	resId=Presid;                                              //~@@@2I~
    	row=Prow;                                                  //~@@@2I~
    	init(Ptext,Pcol);                                          //~@@@2I~
    }                                                              //~@@@2I~
//************************************************************     //~v101I~
//**from TextArea                                                  //~v101I~
//************************************************************     //~v101I~
    public TextField(Container Pcontainer,String Ptext,int Presid,int Prow,int Pcol)//~v101I~
    {                                                              //~v101I~
    	super(Pcontainer);	//component                            //~v101I~
    	resId=Presid;                                              //~v101I~
    	row=Prow;                                                  //~v101I~
    	init(Ptext,Pcol);                                          //~v101I~
    }                                                              //~v101I~
//************************************************************     //~@@@@I~
//**from GrayTextField<--FormTextfield                             //~@@@@I~
//************************************************************     //~@@@@I~
    public TextField(int n,int PresId,String Pdummy)               //~@@@@I~
    {                                                              //~@@@@I~
    	resId=PresId;                                              //~@@@@I~
    	init("",n);                                              //~@@@@I~//~@@@2R~
    }                                                              //~@@@@I~
//************************************************************     //~v101I~
    public TextField(Container Pcontainer,int n,int PresId,String Pdummy)     //~v101I~
    {                                                              //~v101I~
    	super(Pcontainer);	//component                            //~v101I~
    	resId=PresId;                                              //~v101I~
    	init("",n);                                                //~v101I~
    }                                                              //~v101I~
//************************************************************     //~@@@@I~
    private TextView findView(String Ptext)                         //~1216I~//~1219R~//~1312R~
    {                                                              //~1216I~
    	TextView view=null;                                        //~1216I~
        if (resId!=0)                                              //~@@@@I~
          if (parentContainer!=null && parentContainer.containerLayoutView!=null)//~v101I~
        	view=(TextView)parentContainer.findViewById(resId);    //~v101I~
          else                                                     //~v101I~
        	view=(TextView)AG.findViewById(resId);                 //~@@@@I~
        else                                                       //~@@@@I~
    	if (Ptext!=null && !Ptext.equals("") && !Ptext.equals(" "))//~1331R~
        	view=(TextView)AG.findViewByName(Ptext);               //~1216I~//~1312R~
        if (view==null)                                            //~1216I~
        {                                                          //~1416I~
        	if (row>=0)	//TextArea                                 //~1416R~
	        	view=(TextView)AG.findTextAreaView();              //~1416I~
            if (view==null)                                        //~1416I~
	        	view=(TextView)AG.findTextFieldView();             //~1416R~
        }                                                          //~1416I~
        return view;                                               //~1216I~
    }                                                              //~1216I~
//**************                                                   //~1216I~
    private void init(String Ptext,int Pcolwidth)                    //~1216I~//~1312R~
    {                                                              //~1216I~
        if (Dump.Y) Dump.println("TextField init col="+Pcolwidth+",row="+row);//~1506R~
        colWidth=Pcolwidth;                                        //~1503I~
    	textView=findView(Ptext);                                  //~1216I~//~1312R~
        componentView=textView;	//for Component.requestFocus;      //~1405I~
    	if (textView==null)                                        //~1216I~
	        return;                                                //~1216I~
        if (parentWindow instanceof Dialog)                    //~1408I~
	        containerDialog=(Dialog)parentWindow;	//for redo chk//~1408R~
        if (row<0)	//not TextArea                                 //~1216I~
        	textView.setLines(1);                                               //~1127I~//~1216I~
        else                                                       //~1216I~
        if (row>0)	                                               //~1216I~
        	textView.setLines(row);	//height is just the line                                //~1216I~//~1218R~
        else	                                                   //~1218I~
        	textView.setSingleLine(false);                         //~1218I~
        textView.setText(Ptext);                                   //~1312I~
    }                                                              //~1216I~
////**************                                                   //~1216I~//~1310R~
    public void setFont(Font c)                                         //~1112I~//~1503R~
    {                                                              //~1112I~//~1503R~
    	super.setFont(c);                                          //~1503I~
        if (colWidth>0)                                            //~1503I~
        {                                                          //~1503I~
            int sz=c.getSize()*colWidth;       //sp               //~1503I~
            textView.setMinWidth(sz);                              //~1503I~
            if (Dump.Y) Dump.println("TextFiled setFont col="+colWidth+",setMinWidth="+sz);//~1506R~
        }                                                          //~1503I~
    }                                                              //~1112I~//~1503R~
//***************                                                  //~1113I~
    public void addKeyListener(KeyListener Pkl)                    //~1113I~
    {                                                              //~1113I~
        if (tfkl==null)                                            //~1113I~
        	tfkl=AKey.addKeyListener(textView);                     //~1114R~//~@@@@R~
        tfkl.setKeyListener(Pkl);                                  //~1114I~
    }                                                              //~1113I~
    public void addActionListener(/*TextFieldActionListener protected*/ActionListener Ptfal)              //~1113R~//~1216R~
    {                                                              //~1113I~
        if (tfkl==null)                                            //~1114I~
        	tfkl=AKey.addKeyListener(textView);                     //~1114I~//~@@@@R~
        tfkl.setActionListener((ActionListener)Ptfal);                               //~1114I~//~1216R~
    }                                                              //~1113I~
    public void setEditable(boolean Psw)                                //~1116I~
    {                                                              //~1116I~
        if (Psw)                                                   //~1403I~
        {                                                          //~1403I~
	        textView.setFocusableInTouchMode(true);                 //~1403I~
        }                                                          //~1403I~
        else                                                       //~1403I~
        {                                                          //~1403I~
	        textView.setEnabled(false);                            //~1403I~
        }                                                          //~1403I~
    }                                                              //~1116I~
    public void setEchoChar(char Pecho)                            //~1125I~
    {                                                              //~1125I~
//    	textView.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());                             //~1125I~//~1216R~
	}                                                              //~1127R~
//********************                                             //~1127I~
    public String getText()                                        //~1116I~//~1127I~
    {                                                              //~1116I~//~1127I~
    	Dialog dialog=containerDialog;                             //~1410I~
    	String tellString=textView.getText().toString();     //editable to string//~1325I~
        if (dialog!=null)	//TextField indialog                   //~1410I~
        {                                                          //~1410I~
            if (dialog.isAfterDismiss())   //resheduled Dialog     //~1410M~
            {                                                      //~1410M~
                tellString=dialog.modalGetText();   //multiple field on EditPartner Dialog//~1410M~
            }                                                      //~1410M~
            else                                                   //~1410M~
            if (dialog.isWaitForInput())  //textField in modal dialog//~1410R~
            {                                                      //~1410R~
                dialog.modalSaveText(tellString);     //editable to string//~1410R~
                tellString="";                              //wait Dialog input complete then dismiss//~1410R~
            }                                                      //~1410R~
        }                                                          //~1410I~
        if (Dump.Y) Dump.println("TextField:getText="+tellString); //~1506R~
    	return tellString;                                         //~1325R~
    }                                                                  //~1125I~
//**********                                                       //~1411I~
    public String getText(boolean PignoreDismiss)                  //~1411I~
    {                                                              //~1411I~
    	return textView.getText().toString();     //for FileDialog;anyway get input for dir up/down//~1411I~
    }                                                              //~1411I~
//********************                                             //~1127I~//~1310R~//~1312R~
//*draw text by Black                                              //~1312I~
//********************                                             //~1312I~
    public void setBackground(Color Pcolor)                        //~1127I~//~1310R~//~1312R~
    {
    	if (Dump.Y) Dump.println("TextField:setBackground "+Pcolor.getRGB()+",defaultBG="+defaultFG.getRGB());//~1127I~//~1310R~//~1506R~
    	if (Pcolor.getRGB()==defaultFG.getRGB())  //BG:black//~1502R~
        	super.setBackground(defaultBG,defaultFG);             //change BG to gray//~1502R~
        else                                                       //~1312I~
        	super.setBackground(Pcolor,defaultFG);	//replace dialog FG(white) by black//~1502R~
    }                                                              //~1127I~//~1310R~//~1312R~
//********************                                             //~1128I~
//*from HistoryTextField                                           //~1427I~
//********************                                             //~1427I~
//    public void add(PopupMenu Ppopupmenu)                          //~1128I~//~@@@@R~
//    {                                                              //~1128I~//~@@@@R~
//    }                                                              //~1128I~//~@@@@R~
    public void setLength(int n)                                   //~1129I~
    {                                                              //~1129I~
    	textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n)});//~1129I~//~1216R~
    }                                                              //~1129I~
//********************                                             //~1215I~
    public void transferFocus()	//from FormTextField               //~1215I~
    {                                                              //~1215I~
        View next;                                                 //~1420R~
        next=textView.focusSearch(TextView.FOCUS_RIGHT);           //~1420I~
        if (next==null)                                            //~1420I~
        	next=textView.focusSearch(TextView.FOCUS_DOWN);        //~1420I~
        if (next!=null)                                            //~1420I~
//      	next.requestFocus();                                   //~1420I~//~v108R~
      		requestFocus(next);				                       //~v108I~
        if (Dump.Y) Dump.println("TextField transferFocus next="+((next==null)?"null":next.toString()));//~2C15I~
    }                                                              //~1215I~
}//class                                                           //~1112I~
