package com.Asgts.awt;                                              //~2C26R~//+3213R~

public class ItemEvent                                             //~1124R~
{                                                                  //~1112I~
	public static final int ITEM_STATE_CHANGED=1;                  //~1214I~
	public static final int SELECTED          =2;                  //~1219I~
                                                                   //~1220I~
    int state=0;                                                   //~1220I~
    String item;                                                   //~1220I~
	public ItemEvent()
	{
	
	}
	public ItemEvent(Object P1/*Spinner*/, int Ppos, String selectedItem,int itemStateChanged)//~1220R~
	{
    	item=selectedItem;                                         //~1220I~
    	state=itemStateChanged;                                   //~1220R~
	}
	public int getStateChange()                                    //~1219R~
	{                                                              //~1219I~
    	return state;	/*@@@@*/                                       //~1219I~//~1220R~
	}                                                              //~1219I~
}                                                                  //~1112I~