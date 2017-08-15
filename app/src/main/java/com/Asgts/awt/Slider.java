package com.Asgts.awt;                                             //~3221R~

import jagoclient.Dump;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
                                                                   //~1726I~
//************************************                             //~1726I~
public class Slider extends Component                                                //~1726I~//~3221R~
	implements OnSeekBarChangeListener, EditTextListener           //~1730R~
{                                                                  //~1726I~
    private SliderListener listener;                               //~1726R~
    private EditText edittext;                                     //~1726I~
    private Dialog dialog;                                           //~1726I~//~3221R~
    private int  sliderid;                                         //~1726I~
    private SeekBar seekbar;                                                               //~1726I~
    private int minValue;                                          //+3221I~
    public Slider(SliderListener Plistener,int PidEditText,int PidSeekbar,int PinitValue,int Pmin,int Pmax)        //~1213I~//~1726R~
    {                                                              //~1726I~
    	listener=Plistener;                                        //~1726R~
        minValue=Pmin;                                             //+3221I~
        dialog=(Dialog)Plistener;                                  //~3221I~
        sliderid=PidSeekbar;                                       //~1726I~
    	seekbar=(SeekBar)dialog.findViewById(PidSeekbar);         //~1726I~//~3221R~
    	edittext=(EditText)dialog.findViewById(PidEditText);      //~1726I~//~3221R~
    	seekbar.setProgress(PinitValue-minValue);                           //~1726I~//+3221R~
    	seekbar.setMax(Pmax-minValue);	//min is always 0          //+3221R~
        edittext.setText(Integer.toString(PinitValue));            //~1726I~
    	seekbar.setOnSeekBarChangeListener(this); //request callback this//~1726I~
  		EditTextField.setListener(this,edittext);                  //~1730R~
    }                                                              //~1726I~
    @Override                                                      //~1730I~
    public void onEditTextChanged(int Pviewid,int Pvalue)         //~1730I~
    {                                                              //~1730I~
		if (Dump.Y) Dump.println("Slider EditText cahnged value="+Pvalue);//~1730I~
        onProgressChanged(seekbar,Pvalue,false);                   //~1730I~
    }                                                              //~1730I~
    public int getValue()                                          //~1726I~
    {                                                              //~1726I~
    	return seekbar.getProgress()+minValue;                              //~1726I~//+3221R~
    }                                                              //~1726I~
    public void setValue(int Pvalue)                               //~1726I~
    {                                                              //~1726I~
    	seekbar.setProgress(Pvalue-minValue);                               //~1726I~//+3221R~
    }                                                              //~1726I~
//****************************                                     //~1726I~
    @Override                                                      //~1726I~
    public void onProgressChanged(SeekBar PseekBar,int Pvalue,boolean PfromUser)//~1726I~
    {                                                              //~1726I~
    	if (Dump.Y) Dump.println("Slider OnProgressChanged value="+Pvalue+",fromUser="+PfromUser);//~1730R~
        edittext.setText(Integer.toString(Pvalue));                //~1726I~
        listener.onSliderChanged(sliderid,Pvalue);	//notify to dialog to draw sample panel//~1726I~
    }                                                              //~1726I~
//****************************                                     //~1726I~
    @Override                                                      //~1726I~
    public void onStartTrackingTouch(SeekBar PseekBar)             //~1726I~
    {                                                              //~1726I~
    }                                                              //~1726I~
    @Override                                                      //~1726I~
    public void onStopTrackingTouch(SeekBar PseekBar)              //~1726I~
    {                                                              //~1726I~
    	if (Dump.Y) Dump.println("Slider OnOnStop value="+getValue());//~1726I~
    }                                                              //~1726I~
}//class                                                           //~1726I~
