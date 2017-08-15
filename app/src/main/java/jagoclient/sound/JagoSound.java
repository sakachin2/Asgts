//*CID://+1A16R~:                             update#=   12;       //+1A16R~
//****************************************************************************//~1A0aI~
//1A16 2013/03/11 avoid sound busy when one touch mode             //+1A16I~
//1A0a 2013/03/03 issue "check" sound                              //~1A0aI~
//****************************************************************************//~1A0aI~
package jagoclient.sound;

import jagoclient.Dump;
import jagoclient.Global;
//import rene.util.sound.SoundList;                                //~1327R~
import com.Asgts.AG;                                               //~@@@@R~
import com.Asgts.rene.util.sound.SoundList;                       //~1327I~//~@@@@R~

public class JagoSound
{	static SoundList SL=new SoundList();
//*always use simple sound(2nd parameter)***************************//~@@@@I~
	static synchronized public void play 
		(String file, String simplefile, boolean beep)
//  {   if (Global.getParameter("nosound",true)) return;           //~@@@@R~
    {                                                              //~@@@@R~
    	if (Dump.Y) Dump.println("JagoSound file="+file+",simplefile="+simplefile+",beep="+beep+",AGopt="+Integer.toHexString(AG.Options));//~@@@@I~
        if ((AG.Options & AG.OPTIONS_NOSOUND)!=0) return;          //~@@@@I~
//  	if (Global.getParameter("beep",true))                      //~@@@@R~
        if ((AG.Options & AG.OPTIONS_BEEP_ONLY)!=0)                //~@@@@I~
		{	if (beep) SoundList.beep();
			return;
		}
//        if (Global.getParameter("simplesound",true)) file=simplefile;//~@@@@R~
        file=simplefile;                                           //~@@@@I~
        if (file.equals("")) return;                               //~@@@@R~
//      if (SL.busy()) return;                                     //~@@@@R~//+1A16R~
        if (SL.busy(file)) return;                                 //+1A16I~
//      if (Global.getJavaVersion()>=1.3)                          //~@@@@R~
            SL.play("/jagoclient/au/"+file+".wav");                //~@@@@R~
//      else                                                       //~@@@@R~
//          SL.play("/jagoclient/au/"+file+".au");                 //~@@@@R~
	}
    static public void play (String file)                          //~@@@@R~
//  {   if (SL.busy()) return;                                     //~@@@@R~//+1A16R~
    {                                                              //+1A16I~
        if (SL.busy(file)) return;                                 //+1A16I~
        play(file,"wip",false);                                    //~@@@@R~
    }                                                              //~@@@@R~
//********************************************************         //~@@@@I~
    static public void play (String file,boolean beep)             //~@@@@I~
    {                                                              //~@@@@I~
        play(file,file,beep);                                      //~@@@@I~
    }                                                              //~@@@@I~
//********************************************************         //~1A0aI~
	static synchronized public void play                           //~1A0aI~
		(String file, boolean beep,boolean Penq)                   //~1A0aI~
    {                                                              //~1A0aI~
    	if (Dump.Y) Dump.println("JagoSound file="+file+",beep="+beep+",enq="+Penq+",AGopt="+Integer.toHexString(AG.Options));//~1A0aI~
        if ((AG.Options & AG.OPTIONS_NOSOUND)!=0) return;          //~1A0aI~
        if ((AG.Options & AG.OPTIONS_BEEP_ONLY)!=0)                //~1A0aI~
		{                                                          //~1A0aI~
			return;                                                //~1A0aI~
		}                                                          //~1A0aI~
        if (file.equals("")) return;                               //~1A0aI~
        if (SL.busy(file)) return;                                 //~1A0aI~
            SL.play("/jagoclient/au/"+file+".wav");                //~1A0aI~
    }                                                              //~1A0aI~
}

