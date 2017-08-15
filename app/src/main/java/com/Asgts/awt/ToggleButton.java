//*CID://+1A02R~:                             update#=   14;       //~1A02R~
//**********************************************************************//~v101I~
//1A02 2013/02/16 recycle image for mainframe after drawn          //~1A02I~
//**********************************************************************//~v101I~
package com.Asgts.awt;                                            //~1215R~//~2C26R~//~v101R~



import jagoclient.Dump;
import jagoclient.gui.ActionTranslator;
import jagoclient.gui.DoActionListener;
import android.view.View.OnClickListener;                          //~1A02I~
import android.view.View;

public class ToggleButton extends Component                            //~1328R~//~1A02R~
{                                                                  //~1215R~
	public android.widget.ToggleButton view;           //~1328I~   //~1A02R~
    private int resId;                                             //~1A02I~
    private String Name;                                                               //~1328I~//~1A02R~
    private ActionTranslator AT;                                   //~1A02I~
//**************                                                   //~v101I~
    public ToggleButton(Container Pcontainer,int Presid)               //~v101I~//~1A02R~
    {                                                              //~v101I~
    	resId=Presid;                                              //~1A02I~
    	view=(android.widget.ToggleButton)Pcontainer.findViewById(Presid); //~v101I~//~1A02R~
    }                                                              //~v101I~
//**************                                                   //~1A02I~
    public void addListener(DoActionListener Pdal,String Pname)    //~1A02I~
    {                                                              //~1A02I~
    	Name=Pname;                                                //~1A02I~
    	AT=new ActionTranslator(Pdal,Pname);                       //~1A02I~
        addListener(AT);                                           //~1A02I~
    }                                                              //~1A02I~
    //*****************************************************        //~1A02I~
    private void addListener(ActionTranslator Pat)                      //~1A02I~
    {                                                              //~1A02I~
        view.setOnClickListener(                                   //~1A02I~
			 new OnClickListener()                                 //~1A02I~
                    {                                              //~1A02I~
                        @Override                                  //~1A02I~
                        public void onClick(View Pv)               //~1A02I~
                        {                                          //~1A02I~
                            	onClickButtonAction(Pv);           //~1A02I~
                        }                                          //~1A02I~
                    }                                              //~1A02I~
                                 );                                //~1A02I~
    }                                                              //~1A02I~
//*************                                                    //~1A02I~
	public void onClickButtonAction(View Pview)                    //~1A02I~
    {                                                              //~1A02I~
    	if (Dump.Y) Dump.println("ToggleButton onClickButtonAction buttonId="+Integer.toHexString(resId)+",name="+Name);//~1A02I~
		if (AT!=null)                                              //~1A02I~
        {                                                          //~1A02I~
            try                                                    //~1A02I~
            {                                                      //~1A02I~
        		ActionEvent.actionPerformed(AT,this);              //~1A02I~
            }                                                      //~1A02I~
            catch(Exception e)                                     //~1A02I~
            {                                                      //~1A02I~
                Dump.println(e,"ToggleButton:OnClickButton name="+Name);//~1A02I~
            }                                                      //~1A02I~
        }                                                          //~1A02I~
    }                                                              //~1A02I~
    //*****************************************************        //~1A02I~
    public void setEnabled(boolean Pactive)                                      //~1215I~//+1A02R~
    {                                                              //~1215I~
		setEnabled(view,Pactive);                                  //+1A02R~
    }                                                              //~1215I~
    //*****************************************************        //+1A02I~
    public boolean isChecked()                                     //+1A02I~
    {                                                              //+1A02I~
		return view.isChecked();                                   //+1A02I~
    }                                                              //+1A02I~
    public void setChecked(boolean Pstat)                            //~1215I~//~1A02R~
    {                                                              //~1215I~
		setChecked(view,Pstat); 	//by component             //~1A02I~
    }                                                              //~1215I~
    public void setVisibility(int Pvisibility)                     //~1A02I~
    {                                                              //~1A02I~
    	setVisibility(view,Pvisibility);                           //~1A02I~
    }                                                              //~1A02I~
    public void setDrawable(int Presid)                            //~1A02I~
    {                                                              //~1A02I~
    	setButtonDrawable(view,Presid,View.VISIBLE);               //~1A02R~
    }                                                              //~1A02I~
}
