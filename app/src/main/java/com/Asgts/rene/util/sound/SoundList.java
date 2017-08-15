//*CID://+1Ah4R~:                             update#=   46;       //~1Ah4R~
//*************************************************************************//~v106I~
//1Ah4*2016/10/30 Mediaplayer err msg on logcat "should have subtitle controler already set"//~1Ah4I~
//1A3b 2013/05/30 MediaPlayer exception:setdataSourceFD failed status=0x80000000//~1A3bI~
//                if access file on sdcard(/storage/emulated/0/Asgts/sounds.//~1A3bI~
//                try /sdcard -->no effect                         //~1A3bI~
//                reason may be file format because other wav file in raw dir is OK when put on sdcard.//~1A3bI~
//                (same as when set in raw folder)                 //~1A3bI~
//                Issue toast and return to avoid NPE(player)      //~1A3bI~
//                .mp3 by wav-->mp3 converter is succerssful       //~1A3bI~
//                android support wav from 4.1 and mp3 is for all android//~1A3bI~
//                convert wav-->mp3 for data on res/raw            //~1A3bI~
//1A0a 2013/03/03 issue "check" sound                              //~1A0aI~
//1A08 2013/03/02 add sound "gameover" (chk also sdcard)           //~1A08I~
//1062:121121 Warning:MediaPlayer finalized without being released //~v106I~
//*************************************************************************//~v106I~
package com.Asgts.rene.util.sound;                                 //~v106R~

//import java.awt.BorderLayout;
//import java.awt.Button;
//import java.awt.Panel;
//import java.awt.Toolkit;

import java.io.File;
import java.io.FileInputStream;

import jagoclient.Dump;

import com.Asgts.AG;                                               //~v106R~
import com.Asgts.Prop;
import com.Asgts.AView;
import com.Asgts.R;
import com.Asgts.Tables;
import com.Asgts.awt.Toolkit;                                      //~v106R~

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
//class SoundElement extends ListElement                           //~1A08R~
//{   Sound S;                                                     //~1A08R~
//    public SoundElement (String name)                            //~1A08R~
//    {   super(name);                                             //~1A08R~
//        S=null;                                                  //~1A08R~
//        S=new Sound13(name);                                     //~1A08R~
//    }                                                            //~1A08R~
//    public String name ()                                        //~1A08R~
//    {   return (String)content();                                //~1A08R~
//    }                                                            //~1A08R~
//    public void play ()                                          //~1A08R~
//    {   S.start();                                               //~1A08R~
//    }                                                            //~1A08R~
//}                                                                //~1A08R~
/**
This is a Sound class to play and store sounds from resources. The class
keeps a list of loaded sounds.
*/

//public class SoundList implements Runnable                       //~1A08R~
public class SoundList implements MediaPlayer.OnCompletionListener //~1327I~
{                                                                  //~1327R~
//    ListClass SL;                                                  //~1327I~//~1A08R~
//    Thread T;                                                    //~1A08R~
//****************                                                 //~1A08I~
	public static final String SOUND_DIR="sounds";                 //~1A08I~
	public static final String GAMEOVERWIN="gameoverwin";          //~1A08I~
	public static final String GAMEOVERLOSE="gameoverlose";        //~1A08I~
	public static final String CHECK="check";                      //~1A0aI~
	private static  Tables[] Ssoundtbl={                           //~1A08I~
					new Tables("click"         ,              R.raw.click),//~1A08I~
					new Tables("stone"         ,              R.raw.stone),//~1A08I~
					new Tables("wip"           ,              R.raw.wip),//~1A08I~
					new Tables("pieceup"       ,              R.raw.pieceup),//~1A08I~
					new Tables("piecedown"     ,              R.raw.piecedown),//~1A08I~
					new Tables(CHECK           ,              R.raw.check),//~1A0aI~
                    new Tables(GAMEOVERWIN     ,              R.raw.gameoverwin),//~1A08R~
                    new Tables(GAMEOVERLOSE    ,              R.raw.gameoverlose),//~1A08R~
                    };                                             //~1A08I~
    int tblidx;                                                    //~1A08I~
	boolean Busy;

	@SuppressLint("ParserError")
	private static int playCtr,releaseCtr;                                 //~v106R~
                                                          //~1327I~
	String Name,Queued;
                                                            //~1327I~
	
	public SoundList ()
	{                                                              //~1327R~
//        SL=new ListClass();                                        //~1327I~//~1A08R~
//        T=new Thread(this);                                      //~1A08R~
//        T.start();                                               //~1A08R~
//        try { Thread.sleep(0); } catch (Exception e) {}          //~1A08R~
	}
//    public void run ()                                           //~1A08R~
//    {   Busy=false;                                              //~1A08R~
//        while (true)                                             //~1A08R~
//        {   try                                                  //~1A08R~
//            {   synchronized(this) { wait(); }                   //~1A08R~
//            }                                                    //~1A08R~
//            catch (Exception e)                                  //~1A08R~
//            {   Dump.println(e,"SoundList Exception");             //~1506R~//~1A08R~
//            }                                                    //~1A08R~
//            Busy=true;                                           //~1A08R~
//            while (Name!=null)                                   //~1A08R~
//            {   playNow(Name);                                   //~1A08R~
//                synchronized (this)                              //~1A08R~
//                {   Name=Queued;                                 //~1A08R~
//                    Queued=null;                                 //~1A08R~
//                }                                                //~1A08R~
//            }                                                    //~1A08R~
//            Busy=false;                                          //~1A08R~
//        }                                                        //~1A08R~
//    }                                                            //~1A08R~
	static synchronized public void beep ()
	{	Toolkit.getDefaultToolkit().beep();
	}
//    public SoundElement find (String name)                       //~1A08R~
//    {   SoundElement se=(SoundElement)SL.first();                //~1A08R~
//        while (se!=null)                                         //~1A08R~
//        {   if (se.name().equals(name))                          //~1A08R~
//            {   return se;                                       //~1A08R~
//            }                                                    //~1A08R~
//            se=(SoundElement)se.next();                          //~1A08R~
//        }                                                        //~1A08R~
//        return null;                                             //~1A08R~
//    }                                                            //~1A08R~
//    public SoundElement add (String name)                        //~1A08R~
//    {   SoundElement e=new SoundElement(name);                   //~1A08R~
//        SL.append(e);                                            //~1A08R~
//        return e;                                                //~1A08R~
//    }                                                            //~1A08R~
//    public void playNow (String name)                            //~1A08R~
//    {   SoundElement e=find(name);                               //~1A08R~
//        if (e==null) e=add(name);                                //~1A08R~
//        e.play();                                                //~1A08R~
//    }                                                            //~1A08R~
	public synchronized void play (String name)
	{	if (busy())
		{                                                          //~1327R~
//            synchronized(this) { Queued=name; }                    //~1327I~//~1A08R~
                                  //~1327I~
			return;
		}                                                           //~1327I~
		Name=name;
//        synchronized(this) { notify(); }                         //~1A08R~
		playSound();//~1327I~
	}
//***************************                                      //~1A0aI~
	public boolean busy ()
//  {	return Busy;                                               //~v106R~
    {                                                              //~v106I~
    	Busy=playCtr>releaseCtr;                                   //~v106M~
//      System.out.println("Busy="+Busy+",create="+playCtr+",release="+releaseCtr);//~v106I~
        if (Dump.Y) Dump.println("SoundList Busy="+Busy+",create="+playCtr+",release="+releaseCtr);//~1A08I~
		return Busy;                                               //~v106M~
	}
//***************************                                      //~1A0aI~
	public boolean busy (String Pfnm)                              //~1A0aI~
    {                                                              //~1A0aI~
    	Busy=playCtr>releaseCtr;                                   //~1A0aI~
        if (Dump.Y) Dump.println("SoundList fnm="+Pfnm+",Busy="+Busy+",create="+playCtr+",release="+releaseCtr);//~1A0aI~
        if (Busy)                                                  //~1A0aI~
           synchronized(this) { Queued=Pfnm; }                     //~1A0aI~
		return Busy;                                               //~1A0aI~
	}                                                              //~1A0aI~
//***************************                                      //~1327I~
//*Mediaplayer              *                                      //~1327I~
//***************************                                      //~1327I~
    MediaPlayer player=null;                                       //~1327I~
    private static int errCtr;                                     //~1A3bI~
    private static final int MAX_ERRCTR=4;                         //~1A3bI~
    synchronized                                                   //~1327I~
    private void playSound()                           //~1327I~
    {                                                              //~1327I~
  	     if (Dump.Y) Dump.println("playSound start:"+Name);        //~1A3bI~
		int id=getResourceId(Name);                               //~1327I~
        if (id<0)                                                  //~1327I~
        	return;                                                //~1327I~
        player=(MediaPlayer)(Ssoundtbl[tblidx].getObject());               //~1A08I~
        try                                                        //~1327I~
        {                                                          //~1327I~
//      	System.out.println("playSounde create="+(playCtr+1)+",release="+releaseCtr);//~v106R~
//	     	if (Dump.Y) Dump.println("playSound start:"+Name);     //~v106I~//~1A3bR~
         if (player==null)                                         //~1A08I~
         {                                                         //~1A08I~
          Uri sdfnmuri=chkSdcard(Name);                            //~1A08I~
          if (sdfnmuri!=null)                                      //~1A08I~
          {                                                        //~1A0aI~
          //*.wav or .mp3 exist                                    //~1A3bI~
        	player=MediaPlayer.create(AG.context,sdfnmuri);           //~1A08I~
            if (Dump.Y) Dump.println("playSound file="+sdfnmuri.toString()+",player="+player);//~1A0aI~
//            if (player==null)                                    //~1A0aR~
//            {                                                    //~1A0aR~
//                player=new MediaPlayer();                        //~1A0aR~
//                player.setDataSource("/sdcard/Asgts/sounds/pieceup");//~1A0aR~
//            }                                                    //~1A0aR~
			if (player==null                                       //~1A3bI~
            &&  !sdfnmuri.getPath().endsWith(".mp3"))               //~1A3bI~
	    		player=chkMp3(Name);          //~1A3bI~
            if (player==null)                                      //~1A3bI~
            {                                                      //~1A3bI~
            	errCtr++;                                          //~1A3bI~
                if (errCtr<MAX_ERRCTR)                             //~1A3bI~
                AView.showToast(AG.resource.getString(R.string.ErrWaveFile,sdfnmuri.getPath()));//~1A3bR~
                return;                                            //~1A3bI~
            }                                                      //~1A3bI~
          }                                                        //~1A0aI~
          else      //no .wav, .mp3 exist                          //~1A3bI~
        	player=MediaPlayer.create(AG.context,id);              //~1327I~
            Ssoundtbl[tblidx].setObject(player);                   //~1A08I~
         }                                                         //~1A08I~
            playCtr++;                                           //~v106I~
//          player.setAudioStreamType(AudioManager.STREAM_MUSIC);  //+1Ah4R~
        	player.seekTo(0);                                      //~1327I~
        	player.start();                                         //~1327I~
            player.setOnCompletionListener(this);                  //~1327I~
  	     	if (Dump.Y) Dump.println("playSound end:"+Name);       //~v106I~
        }                                                          //~1327I~
        catch(Exception e)                                          //~1327I~
        {                                                          //~1327I~
        	Dump.println(e,"Sound Play Exception ctr="+playCtr+",name="+Name);          //~1327I~//~1A08R~
        }                                                          //~1327I~
    }                                                              //~1327I~
//******************************************************           //~1A3bI~
    private  MediaPlayer chkMp3(String Pname)                      //~1A3bI~
    {                                                              //~1A3bI~
		MediaPlayer player=null;                                   //~1A3bI~
//                player=new MediaPlayer();                        //~1A3bM~
//                try                                              //~1A3bM~
//                {                                                //~1A3bM~
////                  player.setDataSource(AG.context,sdfnmuri);   //~1A3bM~
////                  player.setDataSource("/sdcard/Asgts/sounds/pieceup");//~1A3bM~
////                  File f=new File("/sdcard/Asgts/sounds/pieceup.wav");//~1A3bM~
//                    File f=new File("/sdcard/Asgts/sounds/pieceup.mp3");//~1A3bM~
//                    FileInputStream fis=new FileInputStream(f);  //~1A3bM~
//                    player.setDataSource(fis.getFD());           //~1A3bM~
//                    player.prepare();                            //~1A3bM~
//                }                                                //~1A3bM~
//                catch(Exception e)                               //~1A3bM~
//                {                                                //~1A3bM~
//                    Dump.println(e,"Sound Play prepare:"+Name);  //~1A3bM~
//                    player=null;                                 //~1A3bM~
//                }                                                //~1A3bM~
		int pos=Pname.lastIndexOf(".wav");                         //~1A3bI~
        if (pos>0)                                                 //~1A3bI~
        {                                                          //~1A3bI~
            Uri sdfnmurimp3=chkSdcard(Name.substring(0,pos)+".mp3");//~1A3bI~
            if (sdfnmurimp3!=null)                                 //~1A3bI~
            {                                                      //~1A3bI~
	            player=MediaPlayer.create(AG.context,sdfnmurimp3); //~1A3bR~
            	if (Dump.Y) Dump.println("playSound file="+sdfnmurimp3.toString()+",player="+player);//~1A3bR~
            }                                                      //~1A3bI~
        }                                                          //~1A3bI~
        return player;                                             //~1A3bI~
    }//mp3                                                         //~1A3bI~
	//*******************                                          //~1327I~
    @Override                                                      //~1327I~
    public void onCompletion(MediaPlayer Pplayer)                  //~1327I~
    {                                                              //~1327I~
  	    if (Dump.Y) Dump.println("playSound onCompletion before stop sound");         //~v106I~//~1A3bR~
        stopSound();                                               //~1327I~
  	    if (Dump.Y) Dump.println("playSound onCompletion after stopSound");//~v106I~
    }                                                              //~1327I~
	//*******************                                          //~1327I~
    synchronized                                                   //~1327I~
    private void stopSound()                                       //~1327I~
    {                                                              //~1327I~
    	if (player==null)                                          //~1327I~
        	return;                                                 //~1327I~
        try                                                        //~1327I~
        {                                                          //~1327I~
        	player.stop();                                         //~1327I~
        	player.prepare();                                      //~1A08M~
  /*@@@@                                                           //~1A08I~
        	player.reset();                                        //~v106I~
            player.setOnCompletionListener(null);                  //~1327I~
        	player.release();                                      //~1327I~
    @@@@*/                                                         //~1A08I~
            releaseCtr++;                                          //~v106I~
            player=null;                                           //~1327I~
            String qed=Queued;                                     //~1A0aI~
            Queued=null;                                           //~1A0aI~
            if (qed!=null)                                         //~1A0aI~
            	play(qed);                                         //~1A0aI~
        }                                                          //~1327I~
        catch(Exception e)                                          //~1327I~
        {                                                          //~1327I~
        	Dump.println(e,Name+"Sound Stop Exception");          //~1327I~
        }                                                          //~1327I~
    }                                                              //~1327I~
	//*******************                                          //~1A08I~
    synchronized                                                   //~1A08I~
    public static void release()                                   //~1A08R~
    {                                                              //~1A08I~
        if (Dump.Y)	Dump.println("SoundList release");             //~1A08I~
    	for (int ii=0;ii<Ssoundtbl.length;ii++)                    //~1A08I~
        {                                                          //~1A08I~
        	MediaPlayer p=(MediaPlayer)(Ssoundtbl[ii].getObject());//~1A08I~
            if (p==null)                                           //~1A08I~
            	continue;                                          //~1A08I~
	        try                                                    //~1A08I~
    	    {                                                      //~1A08I~
        		Dump.println("SoundList:release name="+Ssoundtbl[ii].name);//~1A08I~
        		p.reset();                                         //~1A08I~
            	p.setOnCompletionListener(null);                   //~1A08I~
        		p.release();                                       //~1A08I~
            }                                                      //~1A08I~
        	catch(Exception e)                                     //~1A08I~
        	{                                                      //~1A08I~
        		Dump.println(e,"SoundList:release Exception name="+Ssoundtbl[ii].name);//~1A08I~
        	}                                                      //~1A08I~
        }                                                          //~1A08I~
        if (Dump.Y)	Dump.println("SoundList release end");         //~1A08I~
    }                                                              //~1A08I~
	//*******************                                          //~1327I~
    private int getResourceId(String Pname)                        //~1327I~
    {                                                              //~1327I~
    	String basename=Pname;                                     //~1327I~
    	int pos;                                                //~1327I~
    //*********                                                    //~1327I~
    	pos=Pname.lastIndexOf('/');                                 //~1327I~
        if (pos>0)                                                 //~1327I~
        	basename=basename.substring(pos+1);                    //~1327I~
    	pos=basename.lastIndexOf('.');                                 //~1327I~
        if (pos>0)                                                 //~1327I~
        	basename=basename.substring(0,pos);                    //~1327I~
//      return AG.findSoundId(basename);                           //~1327I~//~1A08R~
        tblidx=Tables.find(Ssoundtbl,basename);                    //~1A08I~
        if (tblidx<0)                                              //~1A08I~
        	return -1;                                             //~1A08I~
        return Ssoundtbl[tblidx].getId();                                //~1A08R~
    }                                                              //~1327I~
	//*******************                                          //~1A08I~
    private Uri chkSdcard(String Pname)                            //~1A08I~
    {                                                              //~1A08I~
    	String fpath=Pname;                                        //~1A08I~
    	int pos=Pname.lastIndexOf('/');                            //~1A08I~
        if (pos>0)                                                 //~1A08I~
        	fpath=fpath.substring(pos+1);                          //~1A08I~
    	fpath=Prop.getSDPath(SOUND_DIR+"/"+fpath);                 //~1A08R~
        if (fpath==null)                                           //~1A08I~
        	return null;                                           //~1A08I~
        if (Dump.Y) Dump.println("soundfile on sdcard="+fpath);    //~1A08I~
        File f=new File(fpath);                                    //~1A08I~
        if (!f.exists()||f.isDirectory())                          //~1A08I~
        {                                                          //~1A3bI~
//        	return null;                                           //~1A08I~//~1A3bR~
          	return chkSdcardMp3(Pname);                            //~1A3bI~
        }                                                          //~1A3bI~
        Uri soundfileUri=Uri.fromFile(f);                          //~1A08I~
        return soundfileUri;                                       //~1A08I~
    }                                                              //~1A08I~
	//*******************                                          //~1A3bI~
    private Uri chkSdcardMp3(String Pname)                         //~1A3bI~
    {                                                              //~1A3bI~
    	String fpath=Pname;                                        //~1A3bI~
    	int pos=Pname.lastIndexOf('/');                            //~1A3bI~
        if (pos>0)                                                 //~1A3bI~
        	fpath=fpath.substring(pos+1);                          //~1A3bI~
		pos=fpath.lastIndexOf(".wav");                             //~1A3bI~
        if (pos<=0)                                                //~1A3bI~
        	return null;                                           //~1A3bI~
    	fpath=Prop.getSDPath(SOUND_DIR+"/"+fpath.substring(0,pos)+".mp3");//~1A3bI~
        if (fpath==null)                                           //~1A3bI~
        	return null;                                           //~1A3bI~
        if (Dump.Y) Dump.println("soundfile on sdcard MP3="+fpath);//~1A3bI~
        File f=new File(fpath);                                    //~1A3bI~
        if (!f.exists()||f.isDirectory())                          //~1A3bI~
        {                                                          //~1A3bI~
          	return null;                                           //~1A3bI~
        }                                                          //~1A3bI~
        Uri soundfileUri=Uri.fromFile(f);                          //~1A3bI~
        return soundfileUri;                                       //~1A3bI~
    }                                                              //~1A3bI~
//    static SoundList L=new SoundList();                          //~1A08R~
//    static public void main (String args[])                      //~1A08R~
//    {   if (Dump.Y) Dump.println("Java Version "+Global.getJavaVersion());//~1506R~//~1A08R~
//        String Sounds[]={"high","message","click","stone","wip","yourmove","game"};//~1A08R~
//        rene.gui.CloseFrame F=new rene.gui.CloseFrame()          //~1A08R~
//            {   public void doAction (String o)                  //~1A08R~
//                {   if (Global.getJavaVersion()>=1.3)            //~1A08R~
//                        L.play("/jagoclient/au/"+o+".wav");      //~1A08R~
//                    else                                         //~1A08R~
//                        L.play("/jagoclient/au/"+o+".au");       //~1A08R~
//                }                                                //~1A08R~
//                public void doclose ()                           //~1A08R~
//                {   System.exit(0);                              //~1A08R~
//                }                                                //~1A08R~
//            };                                                   //~1A08R~
//        F.setLayout(new BorderLayout());                         //~1A08R~
//        Panel p=new Panel();                                     //~1A08R~
//        F.add("Center",p);                                       //~1A08R~
//        for (int i=0; i<Sounds.length; i++)                      //~1A08R~
//        {   Button b=new Button(Sounds[i]);                      //~1A08R~
//            b.addActionListener(F);                              //~1A08R~
//            p.add(b);                                            //~1A08R~
//        }                                                        //~1A08R~
//        F.pack();                                                //~1A08R~
//        F.setVisible(true);                                      //~1A08R~
//    }                                                            //~1A08R~
}

