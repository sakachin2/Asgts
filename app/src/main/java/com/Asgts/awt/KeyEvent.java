package com.Asgts.awt;                                            //~1112I~//~1312I~//~2C26R~//+3213R~
                                                                   //~1312I~
import android.view.View;
import android.widget.EditText;
import jagoclient.Dump;                                            //~1312I~
                                                                   //~1312I~
public class KeyEvent                                              //~1127R~//~1312I~
{                                                                  //~1112I~//~1312I~
	private android.view.KeyEvent androidkeyevent;                         //~1127I~//~1428R~
    private static int Sprevupkey;                                 //~1428R~
    private static int Saltkey;                                    //~1428R~
    public boolean exausted;    //return true                      //~1428R~
    public boolean enterFn;    //return true                       //~1428I~
    public boolean canceled;                                       //~1428I~
                                                                   //~1428I~
//*******************************************************          //~1428R~
//*from AjgoKey:KeyUp                                              //~1428R~
//*soft kbd dosenot create event, so chk text string               //~1428I~
//********************************************************         //~1428R~
    public KeyEvent(View Pview,android.view.KeyEvent Pev)          //~1428I~
	{                                                           
    	this(Pev);                                                 //~1428R~
  		int keycode=Pev.getKeyCode();                              //~1428I~
		if (keycode==KEY_ENTER2)                                   //~1430R~
        {                                                          //~1430I~
	    	exausted=true;    //return true                        //~1430I~
            keycode=VK_ENTER;                                      //~1430R~
		}                                                          //~1430I~
    	if (Pview instanceof EditText                              //~1428I~
        &&  keycode==VK_ENTER)	//POUND is IME so could not capture event hen softky input//~1428I~
        {                                                          //~1428I~
        	String str=((EditText)Pview).getText().toString();	           //~1428I~
	        if (Dump.Y) Dump.println("edittext str="+str);         //~1506R~
            if (str.length()==1)                                   //~1428I~
            {                                                      //~1428I~
            	char digit=str.charAt(0);                          //~1428I~
                if (digit>='0' && digit<='9')                      //~1428I~
                {                                                  //~1428I~
                	if (digit=='0')                                //~1428I~
                    	Saltkey=VK_F10;                            //~1428I~
                    else                                           //~1428I~
						Saltkey=VK_F1+(digit-'1');                 //~1428I~
	                if (Dump.Y) Dump.println("detected Fn Saltkey="+Integer.toString(Saltkey));//~1506R~
	    			exausted=true;    //return true                //~1428I~
	    			enterFn=true;     //no Enter key process       //~1428I~
                }                                                  //~1428I~
            }                                                      //~1428I~
        }                                                          //~1428I~
    }                                                              //~1428I~
    public KeyEvent(android.view.KeyEvent Pev)                     //~1115I~//~1312I~
	{                                                          	    //~1115I~//~1312I~
//  	long eventtime,timespan;                                   //~1428I~
    //*************************                                    //~1428I~
  		int action=Pev.getAction();                                //~1312I~
  		int keycode=Pev.getKeyCode();                              //~1312I~
  		int flags=Pev.getFlags();                                   //~1428I~
//eventtime=Pev.getEventTime();                              //~1428I~
	    if (Dump.Y) Dump.println("KeyEvent action="+action+",flag=x"+Integer.toHexString(flags)+",keycode=x"+Integer.toHexString(keycode)+",keychar="+Integer.toHexString(Pev.getUnicodeChar()));//~1506R~
        canceled=((flags & CANCELED)!=0);	//touch slide to out of virtual keyboard//~1428I~
    	androidkeyevent=Pev;                                       //~1127I~//~1312M~
    	Saltkey=0;                                                 //~1428M~
		if (keycode==KEY_ENTER2)                                   //~1430R~
        {                                                          //~1430I~
	    	exausted=true;    //return true                        //~1430I~
            keycode=VK_ENTER;                                      //~1430R~
		}                                                          //~1430I~
  		if (action==android.view.KeyEvent.ACTION_DOWN)             //~1428I~
        {                                                          //~1428I~
			;                                                      //~1428I~
        }                                                          //~1428I~
        else                                                       //~1428I~
  		if (action==android.view.KeyEvent.ACTION_UP)               //~1428R~
        {                                                          //~1312I~
            if (keycode==KEY_CENTER)	//center button            //~1428R~
            {                                                      //~1428R~
                if (Sprevupkey==VK_0)                              //~1428R~
                {                                                  //~1428R~
                    Saltkey=VK_F10;                                //~1428R~
                }                                                  //~1428R~
                else                                               //~1428R~
                if (Sprevupkey>=VK_1 && Sprevupkey<=VK_9)   //Fx is from API level11//~1428R~
                {                                                  //~1428R~
                    Saltkey=VK_F1+(Sprevupkey-VK_1);           //use minus as alternative//~1428R~
                }                                                  //~1428R~
            }                                                      //~1428R~
            Sprevupkey=keycode;                                    //~1428M~
        }                                                          //~1312I~
    	if (Dump.Y) Dump.println("keyEvent flag="+Integer.toHexString(Pev.getFlags())+",key="+keycode);//~1506R~
    }                                                              //~1115I~//~1312I~
    public int getKeyChar()                                        //~1116I~//~1312I~
    {                                                              //~1116I~//~1312I~
    	return androidkeyevent.getUnicodeChar();                                //~1116I~//~1127R~//~1312I~
    }                                                              //~1116I~//~1312I~
    public int getKeyCode()                                        //~1127I~//~1312I~
    {                                                              //~1127I~//~1312I~
    	int key;                                                   //~1312I~
        if (canceled)                                              //~1428I~
        	key=-1;                                                //~1428I~
        else                                                       //~1428I~
    	if (Saltkey!=0)                                            //~1428R~
        	key=Saltkey;                                           //~1428R~
        else                                                       //~1312I~
        {                                                          //~1430I~
    		key=androidkeyevent.getKeyCode();                      //~1312R~
			if (key==KEY_ENTER2)                                   //~1430R~
            	key=VK_ENTER;                                      //~1430R~
        }                                                          //~1430I~
        if (Dump.Y) Dump.println("getKeyCode code="+key);          //~1506R~
    	return key;                       //~1127I~                //~1312I~
    }                                                              //~1127I~//~1312I~
//****************************************                         //~1428I~
//*for GoFrame/Board                                               //~1428I~
//****************************************                         //~1428I~
    public boolean isActionKey()                                   //~1116I~//~1312I~
    {                                                              //~1116I~//~1312I~
        switch (androidkeyevent.getKeyCode())                                    //~1428I~
        {   case KeyEvent.VK_DOWN :              //~1428I~
            case KeyEvent.VK_UP :                   //~1428I~
            case KeyEvent.VK_LEFT :              //~1428I~
            case KeyEvent.VK_RIGHT :            //~1428I~
            case KeyEvent.VK_PAGE_DOWN :    //~1428I~
            case KeyEvent.VK_PAGE_UP :          //~1428I~
            case KeyEvent.VK_BACK_SPACE :                          //~1428I~
            case KeyEvent.VK_DELETE :              //~1428I~
            case KeyEvent.VK_HOME :              //~1428I~
            case KeyEvent.VK_END :           //~1428I~
            	return true;                                       //~1428I~
        }                                                          //~1428I~
        return false;                                              //~1428I~
    }                                                              //~1116I~//~1312I~
    public boolean isControlDown()                                 //~1213R~//~1312I~
    {                                                              //~1213I~//~1312I~
    	int metastate=androidkeyevent.getMetaState();              //~1214I~//~1312I~
    	return (metastate & CTRL_ON)!=0;                     //~1214I~//~1428R~
    }                                                              //~1213I~//~1312I~
//****************                                                 //~1116I~//~1312I~
    static private final int CANCELED    =0x20;           //APILEVEL=5//~1220I~//~1428M~
//    static private final int LONG_PRESS  =0x80;           //APILEVEL=5//~1428I~
    static private final int CTRL_ON     =0x01000;        //APILEVEL=11//~1428I~
//    static private final int SOFT_KBD    =android.view.KeyEvent.FLAG_SOFT_KEYBOARD;//~1428I~
                                                                   //~1428I~
    static public  final int KEY_POUND   =android.view.KeyEvent.KEYCODE_POUND; //"#"  0x12//~1428I~
    static public  final int KEY_CENTER  =android.view.KeyEvent.KEYCODE_DPAD_CENTER;//~1428I~
    static private final int KEY_SEARCH  =android.view.KeyEvent.KEYCODE_SEARCH;//~1430R~
    static public final int KEY_ENTER2   =KEY_SEARCH;              //~1430I~
                                                                   //~1428I~
    static public final int VK_ENTER     =android.view.KeyEvent.KEYCODE_ENTER; //~1114I~//~1116R~//~1312I~
    static public final int VK_SPACE     =android.view.KeyEvent.KEYCODE_SPACE;//~1116R~//~1312I~
    static public final int VK_TAB       =android.view.KeyEvent.KEYCODE_TAB;//~1116I~//~1312I~
    static public final int VK_UP        =android.view.KeyEvent.KEYCODE_DPAD_UP;//~1116R~//~1312I~
    static public final int VK_DOWN      =android.view.KeyEvent.KEYCODE_DPAD_DOWN;//~1116R~//~1312I~
    static public final int VK_LEFT      =android.view.KeyEvent.KEYCODE_DPAD_LEFT;//~1116I~//~1312I~
    static public final int VK_RIGHT     =android.view.KeyEvent.KEYCODE_DPAD_RIGHT;//~1116I~//~1312I~
	static public final int VK_BACK_SPACE=android.view.KeyEvent.KEYCODE_DEL;//~1116I~//~1312I~
    static public final int VK_C         =android.view.KeyEvent.KEYCODE_C;//~1213I~//~1312I~
    static public final int VK_0         =android.view.KeyEvent.KEYCODE_0;//~1428I~
    static public final int VK_1         =android.view.KeyEvent.KEYCODE_1;//~1428I~
    static public final int VK_9         =android.view.KeyEvent.KEYCODE_9;//~1428I~
    static public final int VK_PAGE_UP   =0x5c;  //apilevel9 android.view.KeyEvent.KEYCODE_PAGE_UP;//~1116I~//~1215I~//~1428R~
    static public final int VK_PAGE_DOWN =0x5d;  //apilevel9 android.view.KeyEvent.KEYCODE_PAGE_DOWN;//~1116I~//~1215I~//~1428R~
	static public final int VK_HOME      =android.view.KeyEvent.KEYCODE_HOME;//~1116I~//~1428M~
    static public final int VK_END       =0x06;  //apilevel11 ENDCALL//~1428R~
    static public final int VK_DELETE    =0x70;  //Apilevel11 FORMARD_DEL//~1428R~
    static public final int VK_INSERT    =0x7c; //KEYCODE_INSERT:API Leve1 11//~1428R~
    static public final int VK_F1        =131;  //0x83;                    //~1220I~//~1427R~
    static public final int VK_F2        =132;                    //~1220I~//~1427R~
    static public final int VK_F3        =133;                    //~1220I~//~1427R~
    static public final int VK_F4        =134;                    //~1220I~//~1427R~
    static public final int VK_F5        =135;                    //~1220I~//~1427R~
    static public final int VK_F6        =136;                    //~1220I~//~1427R~
    static public final int VK_F7        =137;                    //~1220I~//~1427R~
    static public final int VK_F8        =138;                    //~1220I~//~1427R~
    static public final int VK_F9        =139;                    //~1220I~//~1427R~
    static public final int VK_F10       =140;
    
	static public final int VK_ESCAPE    =0x6f; //111 APILEVEL=11   //~1428R~
}//class                                                           //~1112I~//~1312I~
