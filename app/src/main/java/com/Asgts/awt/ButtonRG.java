//*CID://+1Ah0R~:                             update#=   42;       //+1Ah0R~
//*************************************************************************//~v106I~
//1Ah0 2016/10/15 1Afc 2016/09/22 like V1C1 fuego, add ray as player//+1Ah0I~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//*************************************************************************//~v106I~
//RadioButtonGroup:make group RadioButton at anyplace              //~1AfcI~
package com.Asgts.awt;                                             //+1Ah0R~

import android.view.ViewGroup;
import android.widget.CompoundButton;                              //~1AfcM~
import android.widget.CompoundButton.OnCheckedChangeListener;      //~1AfcM~
import android.widget.RadioButton;

import jagoclient.Dump;

public class ButtonRG                                              //~1AfcR~
{                                                                  //~1217R~
    private OnCheckedChangeListener rbListener;                    //~1AfcM~
    private int[] btnIDs,userIDs;                                  //~1AfcI~
    private RadioButton[] buttons;                                 //~1AfcI~
    private int btnctr=0,maxEntry;                                 //~1AfcI~
    private ViewGroup layoutView;                                  //~1AfcI~
    private int defaultIndex=0,currentIndex=0;                     //~1AfcR~
//***************                                                  //~1114I~
    public ButtonRG(ViewGroup Playout,int Pmaxentry)               //~1AfcR~
    {                                                            //~1126R~//~1217R~
    	layoutView=Playout;                                        //~1AfcI~
    	maxEntry=Pmaxentry;                                        //~1AfcI~
    	btnIDs=new int[Pmaxentry];                                 //~1AfcI~
    	userIDs=new int[Pmaxentry];                                //~1AfcI~
    	buttons=new RadioButton[Pmaxentry];                        //~1AfcI~
    	rbListener=new OnCheckedChangeListener()                   //~1AfcM~
        	{                                                      //~1AfcM~
				@Override                                          //~1AfcM~
            	public void onCheckedChanged(CompoundButton Pbtn,boolean Pchecked)//~1AfcM~
                {                                                  //~1AfcM~
                	RadioButton rb=(RadioButton)Pbtn;              //~1AfcM~
                	int id=rb.getId();                             //~1AfcM~
        			if (Dump.Y) Dump.println("ButtonRG:onCheckChanged Pchecked="+Pchecked+",layoutid="+Integer.toHexString(id));//~1AfcI~
                    if (Pchecked)                                  //~1AfcI~
	                	resetChecked(id);                          //~1AfcR~
                }                                                  //~1AfcM~
        	};                                                     //~1AfcM~
        if (Dump.Y) Dump.println("ButtonRG:constructor maxentry="+Pmaxentry);//~1AfcI~
    }                                                              //~1217I~
	//***************************************************************************//~1AfcI~
    public int add(int Puserid,int Playoutid)                      //~1AfcI~
    {                                                              //~1AfcI~
    	RadioButton btn;                                           //~1AfcI~
    	if (btnctr>=maxEntry)                                      //~1AfcI~
        	return -1;                                             //~1AfcI~
        userIDs[btnctr]=Puserid;                                   //~1AfcI~
        btnIDs[btnctr]=Playoutid;                                  //~1AfcI~
    	btn=(RadioButton)layoutView.findViewById(Playoutid);       //~1AfcI~
        if (btn==null)                                             //~1AfcI~
        	return -2;                                             //~1AfcI~
        buttons[btnctr]=btn;                                       //~1AfcI~
        btn.setOnCheckedChangeListener(rbListener);                //~1AfcI~
        if (Dump.Y) Dump.println("ButtonRG:add btnctr="+btnctr+",Puserid="+Puserid+",layoutid="+Integer.toHexString(Playoutid));//~1AfcI~
        btnctr++;                                                  //~1AfcI~
        return btnctr-1;                                           //~1AfcI~
    }                                                              //~1AfcI~
	//***************************************************************************//~1AfcI~
    public int setDefault(int Puserid)                             //~1AfcI~
    {                                                              //~1AfcI~
    	int rc=-1;                                                  //~1AfcI~
    	defaultIndex=0;                                            //~1AfcI~
    	for (int ii=0;ii<btnctr;ii++)                              //~1AfcR~
        {                                                          //~1AfcI~
            if (Puserid==userIDs[ii])                              //~1AfcI~
            {                                                      //~1AfcI~
		    	defaultIndex=ii;                                   //~1AfcI~
                rc=ii;                                             //~1AfcI~
                break;                                             //~1AfcI~
            }                                                      //~1AfcI~
        }                                                          //~1AfcI~
        currentIndex=defaultIndex;                                  //~1AfcI~
        if (Dump.Y) Dump.println("ButtonRG:setDefault Puserid="+Puserid+",defaultIndex="+defaultIndex+",rc="+rc);//~1AfcI~
        return rc;                                                 //~1AfcI~
    }//setDefault                                                  //~1AfcI~
	//***************************************************************************//~1AfcM~
	public void setChecked(int Puserid)                           //~1AfcR~
    {                                                              //~1AfcM~
    	RadioButton btn;                                           //~1AfcI~
        int idx=defaultIndex;                                      //~1AfcI~
    //******************                                           //~1AfcI~
        if (Dump.Y) Dump.println("ButtonRG:setChecked Puserid="+Puserid+",idx="+idx);//~1AfcI~
    	for (int ii=0;ii<btnctr;ii++)                              //~1AfcR~
        {                                                          //~1AfcI~
            if (Puserid==userIDs[ii])                              //~1AfcI~
                idx=ii;                                            //~1AfcI~
        }                                                          //~1AfcM~
        btn=buttons[idx];                                          //~1AfcI~
		btn.setChecked(true);  //listener reset other btn          //~1AfcI~
        currentIndex=idx;                                          //~1AfcI~
    }//setChecked                                                  //~1AfcI~
	//***************************************************************************//~1AfcI~
	private void resetChecked(int Playoutid)                       //~1AfcR~
    {                                                              //~1AfcI~
    	RadioButton btn;                                           //~1AfcI~
    //******************                                           //~1AfcI~
		if (Dump.Y) Dump.println("ButtonRG:resetChecked Playoutid="+Integer.toHexString(Playoutid));//~1AfcR~
    	for (int ii=0;ii<btnctr;ii++)                              //~1AfcR~
        {                                                          //~1AfcI~
        	if (Playoutid==btnIDs[ii])                             //~1AfcI~
            	currentIndex=ii;                                   //~1AfcI~
            else                                                   //~1AfcI~
            {                                                      //~1AfcI~
				buttons[ii].setChecked(false);                     //~1AfcR~
		        if (Dump.Y) Dump.println("ButtonRG:setChecked(false) idx="+ii);//~1AfcR~
            }                                                      //~1AfcI~
        }                                                          //~1AfcI~
    }//setChecked                                                  //~1AfcI~
	//***************************************************************************//~1AfcM~
    public int getChecked()                                        //~1AfcR~
    {                                                              //~1AfcM~
    	int userid=userIDs[currentIndex];                               //~1AfcR~
        if (Dump.Y) Dump.println("ButtonRG:getChecked userid="+userid+",currentIndex="+currentIndex);//~1AfcR~
        return userid;                                             //~1AfcI~
	}//getCheckedRadioButtonId                                     //~1AfcI~
}//class                                                           //~1121R~
