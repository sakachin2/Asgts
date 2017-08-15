package com.Asgts.awt;                                             //~1726I~//+3221R~

import jagoclient.Dump;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
                                                                   //~1726I~
//************************************                             //~1726I~
//*support SetupColor                *                             //~1726I~
//************************************                             //~1726I~
                                                                   //~1726I~
public class EditTextField                                         //~1730R~
	implements OnKeyListener                                       //~1730R~
{                                                                  //~1726I~
    private EditTextListener listener;                        //~1730R~
    private EditText edittext;                                     //~1726I~
    private int  value;                                            //~1730I~
    private int  viewid;                                           //~1730I~
                                                                   //~1726I~
    public EditTextField(EditTextListener Plistener,EditText PeditText)//~1730R~
    {                                                              //~1726I~
    	listener=Plistener;                                        //~1726R~
        edittext=PeditText;                                        //~1730R~
        viewid=edittext.getId();                                   //~1730I~
    	setkeylistener(edittext);                                  //~1726I~
    }                                                              //~1726I~
    public static EditTextField setListener(EditTextListener Plistener,EditText PeditText)//~1730R~
    {                                                              //~1730I~
    	EditTextField etf=new EditTextField(Plistener,PeditText);//~1730R~
    	return etf;                                                //~1730I~
    }                                                              //~1730I~
    private void setkeylistener(View Pv)                           //~1726I~
    {                                                              //~1726I~
        //********************                                         //~1726I~                                                             //~1726I~
        Pv.setOnKeyListener(this);                                 //~1726I~
        Pv.setFocusableInTouchMode(true);	//DOC says "FocusableInTouch then Focusable"//~1726I~
        Pv.setFocusable(true);                                     //~1726I~
        addFocusChangeListener(Pv);                                //~1726I~
    }                                                              //~1726I~
    private void addFocusChangeListener(View Pv)                   //~1726I~
    {                                                              //~1726I~ 
    	if (Dump.Y) Dump.println("EditTextField addFocuschange Listener viewid="+Integer.toString(Pv.getId(),16));//~1730R~
    	Pv.setOnFocusChangeListener(                               //~1726I~
        	new OnFocusChangeListener()                            //~1726I~
            	{                                                  //~1726I~
                  	@Override                                      //~1830R~
                    public void onFocusChange(View Pview,boolean Phasfocus)//~1726I~
                    {                                              //~1726I~
				        if (Dump.Y) Dump.println("focus changed "+Phasfocus+",viewid="+Integer.toString(Pview.getId(),16));//~1726I~
                        if (!Phasfocus)	//lost focus               //~1726I~
    						getEditTextValue(edittext);                  //~1726I~
                    }                                              //~1726I~
                }                                                  //~1726I~
                				);                                 //~1726I~
    }                                                              //~1726I~
    public int getValue()                                          //~1726I~
    {                                                              //~1726I~
    	return value;                                              //~1730R~
    }                                                              //~1726I~
    public void setValue(int Pvalue)                               //~1726I~
    {                                                              //~1726I~
    	value=Pvalue;                                              //~1730R~
        listener.onEditTextChanged(viewid,Pvalue);                 //~1730I~
    }                                                              //~1726I~
//****************************                                     //~1726I~
    public boolean onKey(View Pview,int Pkeycode,android.view.KeyEvent Pevent)//~v@@@I~//~1726I~
    {                                                      //~v@@@I~//~1726I~
        int action;                                //~v@@@I~       //~1726I~
        boolean rc=false;                                          //~1726I~
        //**********************                           //~v@@@I~//~1726I~
        try                                                        //~1726I~
        {                                                          //~1726I~
            if (Dump.Y) Dump.println("Onky keycode="+Pkeycode+",viewid="+Integer.toString(Pview.getId(),16)+",isfocus="+Pview.isFocused()); //~v@@@I~//~1116I~//~1726I~
            action=Pevent.getAction();                          //~v@@@R~//~1726I~
            switch(action)                                     //~v@@@I~//~1726I~
            {                                                  //~v@@@I~//~1726I~
            case android.view.KeyEvent.ACTION_DOWN:                //~1726I~
                rc=onKeyDown(Pview,Pkeycode,Pevent);               //~1726I~
                break;                                             //~1726I~
            default:                                           //~v@@@I~//~1726I~
            }                                                  //~v@@@I~//~1726I~
        }                                                          //~1726I~
        catch(Exception e)                                         //~1726I~
        {                                                          //~1726I~
            Dump.println(e,"EditTextField EditText.OnKey exception");//~1730R~
        }                                                          //~1726I~
        return rc;           //~v@@@I~                             //~1726I~
    }                                                      //~v@@@I~//~1726I~
//****************************                                     //~1726M~
    public boolean onKeyDown(View v,int keycode,KeyEvent ev)       //~1726M~
    {                                                          //~1113I~//~1114I~//~1726M~
    	boolean rc=false;                                          //~1726M~
    //*******************                                          //~1726M~
    	if (Dump.Y) Dump.println("EditTextField EditText keyDown code="+keycode);                 //~1114I~//~1730R~
        if (keycode==KeyEvent.KEYCODE_ENTER)                       //~1726I~
        {                                                          //~1726I~
            getEditTextValue(v);                                   //~1726I~
            rc=true;                                               //~1726I~
        }                                                          //~1726I~
    	if (Dump.Y) Dump.println("keyDown rc="+rc);                //~1726M~
        return rc;   //android process                      //~1113R~//~1114I~//~1726M~
    }                                                              //~1726M~
    //*************************************                        //~1726M~
    private void getEditTextValue(View Pview)                      //~1726M~
	{                                                              //~1726M~
        String text=((EditText)Pview).getText().toString();                    //~1726M~
        int value;                                                 //~1726M~
		try                                                        //~1726M~
		{                                                          //~1726M~
        	value=Integer.parseInt(text);                          //~1726M~
		}                                                          //~1726M~
		catch (NumberFormatException e)                            //~1726M~
		{                                                          //~1726M~
        	value=-1;                                              //~1726M~
		}                                                          //~1726M~
        if (value<0||value>255)                                    //~1726M~
        	return;                                                //~1726M~
        setValue(value);                                           //~1726M~
    	if (Dump.Y) Dump.println("EditTextField EditText value="+value);//~1730R~
	}                                                              //~1726M~
}//class                                                           //~1726I~
