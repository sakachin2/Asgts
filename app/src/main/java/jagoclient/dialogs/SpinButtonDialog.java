//*CID://+v1A0R~: update#=  193;                                   //~v1A0R~
package jagoclient.dialogs;                                     //~v1A0R~

import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.FormTextField;
import com.Asgts.AG;
import com.Asgts.R;
import com.Asgts.awt.Frame;
import com.Asgts.awt.Slider;
import com.Asgts.awt.SliderListener;

public class SpinButtonDialog extends CloseDialog
    implements SliderListener
{
	private static final int VALUE_UPDATE2=10;                     //~v1A0I~
    int minValue,maxValue;
    FormTextField outputText;
     FormTextField updateText;
    Slider slider;
    int value;
//**********************************
	public SpinButtonDialog(Frame Pf,int Pmin,int Pmax,FormTextField Pedittext/*copy result*/)
    {
        super(Pf,AG.resource.getString(R.string.Title_SpinButtonDialog),R.layout.spinbuttondialog,true,false);
        minValue=Pmin;
        maxValue=Pmax;
        outputText=Pedittext;
        String s=outputText.getText().toString();
        if (s.trim().equals(""))
	    	value=minValue;
        else
        	value=Integer.parseInt(s);
        String mm=AG.resource.getString(R.string.SliderMinMax,minValue,maxValue);//~v1A0I~
        new FormTextField(this,R.id.TextRange,mm);                  //~v1A0R~
        updateText=new FormTextField(this,R.id.EditNumText,s);     //~v1A0I~
    	slider=new Slider(this,R.id.EditNumText,R.id.Seekbar,value,minValue,maxValue);
        new ButtonAction(this,0,R.id.CountDown);                   //~v1A0R~
        new ButtonAction(this,0,R.id.CountUp);                     //~v1A0R~
        new ButtonAction(this,0,R.id.CountDown2);                  //~v1A0I~
        new ButtonAction(this,0,R.id.CountUp2);                    //~v1A0I~
        new ButtonAction(this,0,R.id.OK);                          //~v1A0R~
        new ButtonAction(this,0,R.id.Cancel);                      //~v1A0R~
        new ButtonAction(this,0,R.id.Help);                        //~v1A0R~
		validate();
		show();
    }
    //****************************************
	public void doAction (String o)
    {
        if (o.equals(AG.resource.getString(R.string.OK)))//partner game only
		{
	        int val=slider.getValue();
            if (val!=value)
            	outputText.setText(Integer.toString(val));
			setVisible(false); dispose();
		}
        else if (o.equals(AG.resource.getString(R.string.Cancel)))
		{	setVisible(false); dispose();
		}
        else if (o.equals(AG.resource.getString(R.string.Help)))
		{
            new HelpDialog(null,R.string.HelpTitle_SpinButtonDialog,"SpinButtonDialog");//+v1A0R~
		}
        else if (o.equals(AG.resource.getString(R.string.CountDown)))
		{
			updateCtr(-1);                                         //~v1A0I~
		}
        else if (o.equals(AG.resource.getString(R.string.CountDown2)))//~v1A0I~
		{                                                          //~v1A0I~
			updateCtr(-VALUE_UPDATE2);                             //~v1A0I~
		}                                                          //~v1A0I~
        else if (o.equals(AG.resource.getString(R.string.CountUp)))//~v1A0R~
		{
			updateCtr(1);                                          //~v1A0I~
		}
        else if (o.equals(AG.resource.getString(R.string.CountUp2)))//~v1A0I~
		{                                                          //~v1A0I~
			updateCtr(VALUE_UPDATE2);                              //~v1A0I~
		}                                                          //~v1A0I~
	}
	private void updateCtr(int Pctr)                               //~v1A0I~
    {                                                              //~v1A0I~
		int val=slider.getValue();                                 //~v1A0I~
        val+=Pctr;                                                 //~v1A0I~
        if (val<minValue)                                          //~v1A0I~
        	val=minValue;                                          //~v1A0I~
        if (val>maxValue)                                          //~v1A0I~
        	val=maxValue;                                          //~v1A0I~
	    slider.setValue(val);                                      //~v1A0I~
    }                                                              //~v1A0I~
//*********
    @Override
    public void onSliderChanged(int Psliderid,int Pvalue)
    {
    }
}
