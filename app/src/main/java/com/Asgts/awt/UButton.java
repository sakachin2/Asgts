//*CID://+DATER~:                             update#=   73;
//*************************************************************************
//1A6u 2015/02/18 new parts for runonuithread
//*************************************************************************
package com.Asgts.awt;

import com.Asgts.AG;
import com.Asgts.UiThread;
import com.Asgts.UiThreadI;
import android.view.ViewGroup;
import android.widget.Button;
import jagoclient.Dump;

public class UButton                                               //~5219R~
{
	private static final int FUNC_SETVISIBILITY=1;
	private static final int FUNC_SETENABLED=2;
	private static final int FUNC_SETTEXT=3;                       //+5219I~
	public Button androidButton;
	public String initText,setText;                                //+5219R~
    private int visibility;                                        //~5219R~
    private boolean enabled;
    //****************************************************************************
    public UButton(ViewGroup Playoutview,int Pbuttonid)            //~5218R~
    {
    	this((Button)Playoutview.findViewById(Pbuttonid));    //~5219R~
    }
    //****************************************************************************//~5219I~
    public UButton(Button Pbutton)                                 //~5219I~
    {                                                              //~5219I~
    	androidButton=Pbutton;                                     //~5219I~
        initText=androidButton.getText().toString();               //+5219R~
        if (Dump.Y) Dump.println("UButton id="+Integer.toHexString(androidButton.getId())+",inittext="+initText);//+5219R~
    }                                                              //~5219I~
    //****************************************************************************
    public void setEnabled(boolean Penabled)
    {
        if (Dump.Y) Dump.println("UButton setEnabled text="+initText+",enabled="+Penabled);//~5218R~//+5219R~
        enabled=Penabled;
        UButtonUIT uit=new UButtonUIT(this,FUNC_SETENABLED);       //~5219R~
        UiThread.runOnUiThread(uit,null);
    }
    //****************************************************************************
    public void setVisibility(int Pvisibility)                     //~5218R~
    {
        if (Dump.Y) Dump.println("UButton setVisibility text="+initText+",visibility="+Pvisibility);//~5218R~//+5219R~
        visibility=Pvisibility;
        UButtonUIT uit=new UButtonUIT(this,FUNC_SETVISIBILITY);    //~5219R~
        UiThread.runOnUiThread(uit,null);
    }
    //****************************************************************************//+5219I~
    public void setText(int Presid)                                //+5219I~
    {                                                              //+5219I~
        if (Dump.Y) Dump.println("UButton setText resid="+Integer.toHexString(Presid));//+5219I~
        String text=AG.resource.getString(Presid);                 //+5219I~
	    setText(text);                                             //+5219I~
    }                                                              //+5219I~
    //****************************************************************************//+5219I~
    public void setText(String Ptext)                              //+5219I~
    {                                                              //+5219I~
        if (Dump.Y) Dump.println("UButton setText ="+Ptext);       //+5219I~
        setText=Ptext;                                             //+5219I~
        UButtonUIT uit=new UButtonUIT(this,FUNC_SETTEXT);          //+5219I~
        UiThread.runOnUiThread(uit,null);                          //+5219I~
    }                                                              //+5219I~
//***************************************************************************
    class UButtonUIT implements UiThreadI                          //~5218R~
    {
    	private UButton btn;                                               //~5218R~//~5219R~
        private int funcid;                                        //~5219R~
	    public UButtonUIT(UButton Pbtn,int Pfuncid)                            //~5218R~//~5219R~
        {
        	funcid=Pfuncid;                                        //~5219I~
        	btn=Pbtn;
        }
        @Override
		public void runOnUiThread(Object Pparm)
        {
        	if (Dump.Y) Dump.println("UButton runonUiThread funcid="+funcid);//~5218R~//~5219R~
            switch(funcid)                                         //~5219R~
            {
            case FUNC_SETVISIBILITY:
            	btn.androidButton.setVisibility(btn.visibility);
                break;
            case FUNC_SETENABLED:
            	btn.androidButton.setEnabled(btn.enabled);
                break;
            case FUNC_SETTEXT:                                     //+5219I~
            	btn.androidButton.setText(setText);                //+5219I~
                break;                                             //+5219I~
            }
        }
	}
}//class
