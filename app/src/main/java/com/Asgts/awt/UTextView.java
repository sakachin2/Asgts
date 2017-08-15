//*CID://+DATER~:                             update#=   70;
//*************************************************************************
//1A6u 2015/02/18 new parts for runonuithread
//*************************************************************************
package com.Asgts.awt;

import com.Asgts.UiThread;
import com.Asgts.UiThreadI;

import android.view.ViewGroup;
import android.widget.TextView;
import jagoclient.Dump;

public class UTextView                                             //~5219R~
{
	private static final int FUNC_SETTEXT=1;
	public TextView textView;                                      //~5218R~
	public String initText;
	private String setText;
    //****************************************************************************
    public UTextView(ViewGroup Playoutview,int Presid)             //~5218R~
    {
    	this((TextView)Playoutview.findViewById(Presid));       //~5218R~//~5219R~
    }
    //****************************************************************************//~5219I~
    public UTextView(TextView Pview)                               //~5219I~
    {                                                              //~5219I~
    	textView=Pview;                                            //~5219I~
        initText=textView.getText().toString();                    //~5219I~
        if (Dump.Y) Dump.println("UTextView id="+Integer.toHexString(textView.getId())+",inittext="+initText);//~5219I~
    }                                                              //~5219I~
    //****************************************************************************
    public CharSequence getText()                              //~5218R~//+5219R~
    {
    	return textView.getText();                                        //+5219I~
    }
    //****************************************************************************//+5219I~
    public void setText(String Ptext)                              //+5219I~
    {                                                              //+5219I~
        if (Dump.Y) Dump.println("UTextView setText inittext="+initText+",text="+Ptext);//+5219I~
        setText=Ptext;                                             //+5219I~
        UTextViewUIT uit=new UTextViewUIT(this,FUNC_SETTEXT);      //+5219I~
        UiThread.runOnUiThread(uit,null);                          //+5219I~
    }                                                              //+5219I~
//***************************************************************************
    class UTextViewUIT implements UiThreadI                        //~5218R~
    {
    	private UTextView utv;                                             //~5218R~//~5219R~
		private int funcid;                                        //~5219I~
	    public UTextViewUIT(UTextView Putv,int Pfuncid)                        //~5218R~//~5219R~
        {
        	funcid=Pfuncid;                                        //~5219I~
        	utv=Putv;                                              //~5218R~
        }
        @Override
		public void runOnUiThread(Object Pparm)
        {
        	if (Dump.Y) Dump.println("UTextView runonUiThread func="+funcid);//~5218R~
            switch(funcid)                                         //~5219R~
            {
            case FUNC_SETTEXT:                                     //~5218R~
            	textView.setText(utv.setText);                     //~5218R~
                break;
            }
        }
	}
}//class
