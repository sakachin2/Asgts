//*CID://+1Ah0R~:                             update#=  120;       //~1AesR~//~1Ah0R~
//***********************************************************************
//1Ah0*2016/10/15 bonanza                                          //~1Ah0I~
//1Aes*2016/06/29 (Bug) replay file saved by clipboard-save dose not show move msg//~1AesI~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4sI~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//1A30 2013/04/06 kif,ki2 format support                           //~1A30I~
//1A2m 2013/04/06 register gameover reason at last entry of ActionMove//~1A2eI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A2c 2013/03/27 display previous move description for reloaded game//~1A2cI~
//1A23 2013/03/23 File Dialog on PartnerGoFrame                    //~1A23I~
//1A1b 2013/03/13 FreeBoard:multiple snapshot                      //~1A1bI~
//1A14 2013/03/10 FreeBoard Title                                  //~1A14I~
//1A10 2013/02/13 Asgts                                            //~v1A0R~
//***********************************************************************
package jagoclient.board;

import static jagoclient.board.Field.*;
import jagoclient.Dump;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;                                        //~v1A0R~
import java.util.ArrayList;

import com.Asgts.AG;
import com.Asgts.Alert;
import com.Asgts.R;
import com.Asgts.Utils;
import com.Asgts.awt.FileDialog;

import rene.util.list.ListClass;
import rene.util.list.ListElement;
import rene.util.parser.StringParser;

public class Notes                                                 //~v1A0R~
{
	private static final String FILE_ACRONYM="Snapshot";           //~1A1bI~
	private static final String GTP_ACRONYM="BonanzaOption";       //~1Ah0I~
	private static final String GTP_ACRONYM_SUBCMD="BonanzaSubcmd";//~1Ah0I~
    //****************************************
	ListClass notesList;                                           //~1A14I~
    public String name;                                        //~v1A0R~//~1A14R~
    public String blackName,whiteName;                             //~1A30I~
    public int color;	//current color P.color() at save                                              //~1A14I~//~1A2cR~
    public int yourcolor;                                          //~1A1bI~
    public int seq;         //snapshot seqNo                       //~1A1bR~
    public int moves,moves0,gameoptions;       //moves in the snapshot         //~1A1bR~
    public int handicap;                                           //~1A2cI~
    public int coordType;    //movedescription by LangJP           //~1A2eR~
    public boolean changeCoord;    //at load coord lang change required//~1A2eR~
    public int white,black,whiteExtra,blackExtra,extraTime,totalTime;//~1A1bR~
	ListElement listElement;                                       //~1A1bI~
    public int[][] tray=new int[2][MAX_PIECE_TYPE_CAPTURE];       //~1A14R~
    public int[][] pieces=new int[AG.BOARDSIZE_SHOGI][AG.BOARDSIZE_SHOGI];//~1A14R~
    public int[][] trayAtStart;                                    //~1A30I~
    public int[][] piecesAtStart;                                  //~1A30I~
    private NotesTree notesTree;                                   //~1A2cI~
    public int filetype;                                           //~1A30I~
//  public int winner,gameoverReasonId;                            //~1A4vR~
    public String filename;                                        //~1A30I~
    public String filenameSaved;                                   //~1A4sI~
    public boolean swClipboard;	//set at NoteFmt                   //~1A4sI~
    public int headerlineCtr,trailerlineCtr,movelineCtr;           //~1A4vR~
    public int limitMode,limitTimeTotal,limitTimeExtra,limitTimeDepth;  //bonanza computer limit//~1Ah0R~
    public ArrayList<String> subcmdList;                           //~1Ah0R~
    public boolean swGtpGame;                                      //~1Ah0I~
    //****************************************
	public Notes(String Pname,int Pyourcolor,int Pcolor)           //~1A1bR~
	{
    	if (Pname==null || Pname.equals(""))
        	name=getDefaultName();                                 //~v1A0R~
        else
        	name=Pname;
        yourcolor=Pyourcolor;                                      //~1A1bR~
        color=Pcolor;                                              //~1A1bI~
        coordType=getCoordType();                                  //~1A2eR~
	}
	public static String getDefaultName()                          //~v1A0I~
	{                                                              //~v1A0I~
        return Utils.getTimeStamp(Utils.TS_DATE_TIME);             //~v1A0I~
	}                                                              //~v1A0I~
    //****************************************                     //~1A2cI~
	public void setTree(NotesTree Ptree)                           //~1A2cI~
	{                                                              //~1A2cI~
        notesTree=Ptree;                                            //~1A2cI~
 	}                                                              //~1A2cI~
    //****************************************                     //~1A2cI~
	public NotesTree getTree()                                          //~1A2cI~
	{                                                              //~1A2cI~
        return notesTree;                                          //~1A2cI~
 	}                                                              //~1A2cI~
    //****************************************
	public void save(ConnectedGoFrame Pcgf)                     //~v1A0R~//~1A14R~//~1A1bR~
	{
        Pcgf.aCapturedList.getCapturedList(tray);       //~v1A0R~
        ((ConnectedBoard)Pcgf.B).getAllBoardPieces(pieces);              //~v1A0R~   //~1A1bR~
 	}
    //****************************************                     //~v1A0I~
	public int[][] getTray()                     //~v1A0R~         //~1A14R~
	{                                                              //~v1A0I~
		return tray;                                    //~v1A0R~  //~1A14R~
	}                                                              //~v1A0I~
    //****************************************                     //~v1A0I~
	public int[][] getPieces()                   //~v1A0R~         //~1A14R~
	{                                                              //~v1A0I~
		return pieces;                                 //~v1A0R~   //~1A14R~
	}                                                              //~v1A0I~
    //****************************************
	public void add(ListClass Plist)                               //~1A14R~
	{
		ListElement e=new ListElement(this);                       //~1A1bI~
		listElement=e;                                             //~1A1bI~
		Plist.append(e);                       //~1A14R~           //~1A1bR~
        e=e.previous();                                            //~1A1bR~
        if (e==null)                                               //~1A1bR~
        {                                                          //~1A1bI~
        	seq=1;                                                 //~1A1bI~
        }                                                          //~1A1bI~
        else                                                       //~1A1bI~
        {                                                          //~1A1bI~
	        Notes n=(Notes)e.content();                           //~1A1bI~
        	seq=n.seq+1;                                           //~1A1bR~
        }                                                          //~1A1bI~
	}
    public static Notes find (ListClass Plist,String s)                          //~1A14I~//~1A1bR~
    {                                                              //~1A14I~
        ListElement p=Plist.first();                               //~1A14I~
        while (p!=null)                                            //~1A14I~
        {                                                          //~1A14I~
            Notes a=(Notes)p.content();                              //~1A14I~
            if (a.name.equals(s)) return a;                               //~1A14I~
            p=p.next();                                            //~1A14I~
        }                                                          //~1A14I~
        return null;                                               //~1A14I~
    }                                                              //~1A14I~
    public static Notes findFirst(ListClass Plist)                 //~1A1bI~
    {                                                              //~1A1bI~
        ListElement p=Plist.first();                               //~1A1bI~
        if (p!=null)                                               //~1A1bI~
        {                                                          //~1A1bI~
            Notes a=(Notes)p.content();                            //~1A1bI~
		    if (Dump.Y) Dump.println("Notes:first ="+a.name);      //~1A1bI~
            return a;                                              //~1A1bI~
        }                                                          //~1A1bI~
        return null;                                               //~1A1bI~
    }                                                              //~1A1bI~
    public static Notes findLast(ListClass Plist)                  //~1A1bI~
    {                                                              //~1A1bI~
        ListElement p=Plist.last();                                //~1A1bI~
        if (p!=null)                                               //~1A1bI~
        {                                                          //~1A1bI~
            Notes a=(Notes)p.content();                            //~1A1bI~
		    if (Dump.Y) Dump.println("Notes:last ="+a.name);       //~1A1bI~
            return a;                                              //~1A1bI~
        }                                                          //~1A1bI~
        return null;                                               //~1A1bI~
    }                                                              //~1A1bI~
    public static Notes next(ListClass Plist,Notes Pnotes)         //~1A1bI~
    {                                                              //~1A1bI~
    	if (Dump.Y) Dump.println("next notes of "+Pnotes.name);    //~1A1bI~
//      ListElement p=findElement(Plist,Pnotes.name);              //~1A1bR~
        ListElement p=Pnotes.listElement;                          //~1A1bI~
        if (p!=null)                                               //~1A1bI~
        {                                                          //~1A1bI~
        	p=p.next();                                            //~1A1bI~
            if (p!=null)                                           //~1A1bI~
            {                                                      //~1A1bI~
		    	if (Dump.Y) Dump.println("next ="+((Notes)p.content()).name);//~1A1bI~
	            return (Notes)p.content();                         //~1A1bI~
            }                                                      //~1A1bI~
        }                                                          //~1A1bI~
        return null;                                               //~1A1bI~
     }                                                              //~1A1bI~
    public static Notes prev(ListClass Plist,Notes Pnotes)         //~1A1bI~
    {                                                              //~1A1bI~
    	if (Dump.Y) Dump.println("prev notes of "+Pnotes.name);    //~1A1bI~
//      ListElement p=findElement(Plist,Pnotes.name);              //~1A1bR~
        ListElement p=Pnotes.listElement;                          //~1A1bI~
        if (p!=null)                                               //~1A1bI~
        {                                                          //~1A1bI~
        	  p=p.previous();                                            //~1A1bI~
            if (p!=null)                                           //~1A1bI~
            {                                                      //~1A1bI~
		    	if (Dump.Y) Dump.println("prev ="+((Notes)p.content()).name);//~1A1bI~
	            return (Notes)p.content();                         //~1A1bI~
            }                                                      //~1A1bI~
        }                                                          //~1A1bI~
        return null;                                               //~1A1bI~
   }                                                              //~1A1bI~
//***************************************************************************//~1A1bI~
    public boolean save(String Pfnm)                               //~1A1bI~
    {                                                              //~1A1bI~
    	boolean rc=true;                                           //~1A1bI~
        if (Dump.Y) Dump.println("Notes:save fnm="+Pfnm);          //~1A1bI~
        filenameSaved=Pfnm;                                        //~1A4sI~
        try // print out using the board class               //~@@@@R~//~1A1bI~
        {                                                          //~1A1bI~
			PrintWriter fo=new PrintWriter(new OutputStreamWriter(new FileOutputStream(Pfnm),"UTF-8"));//~1A1bI~
            saveNotes(fo);                                         //~1A1bI~
            fo.close();                                      //~@@@@R~//~1A1bI~
        }                                                    //~@@@@R~//~1A1bI~
        catch (IOException e)                               //~@@@@R~//~1A1bI~
        {                                                          //~1A1bI~
        	Dump.println(e,"Notes:save IO err");                   //~1A1bI~
			Alert.simpleAlertDialog(null,"File Save",e.toString(),Alert.BUTTON_POSITIVE);//~1A1bI~
            rc=false;                                              //~1A1bI~
        }                                                    //~@@@@R~//~1A1bI~
        return rc;                                                 //~1A1bI~
    }//save                                                        //~1A1bI~
//***************************************************************************//~1A1bI~
	private String sendPrefix;                                     //~1A23I~
    public int saveNotes(PrintWriter Pw)                           //~1A1bI~//~1A23R~
    {                                                              //~1A1bI~
        int lineno=0;                                              //~1A23I~
    //**************************                                   //~1A23I~
        if (Dump.Y) Dump.println("Notes:save name="+name+",seq="+seq);//~1A1bI~
        if (sendPrefix!=null)                                      //~1A23I~
	        Pw.print(sendPrefix+lineno++);     //0                 //~1A23R~
        Pw.println(FILE_ACRONYM+coordType+name);                             //~1A1bI~//~1A2eR~
        if (sendPrefix!=null)                                      //~1A23I~
	        Pw.print(sendPrefix+lineno++);     //1                 //~1A23R~
        String bn=(blackName==null?"nullB":blackName);             //~1Ah0I~
        String wn=(whiteName==null?"nullW":whiteName);             //~1Ah0I~
        if (Dump.Y) Dump.println("Notes.saveNotes blacknName="+bn+",whiteName="+wn);
        Pw.println(seq+" "+moves0+" "+yourcolor+" "+color+" "+white+" "+whiteExtra+" "+black+" "+blackExtra//~1A1bR~
//      +" "+totalTime+" "+extraTime+" "+gameoptions+" "+handicap);             //~1A1bI~//~1A2cR~//~1Ah0R~
        +" "+totalTime+" "+extraTime+" "+gameoptions+" "+handicap  //~1Ah0I~
        +" "+ "\""+bn+"\" \""+wn+"\"");                            //~1Ah0R~
        if (swGtpGame)                                             //~1Ah0I~
            saveNotesGtpOption(Pw);                                //~1Ah0I~
        if (sendPrefix!=null)                                      //~1A23I~
	        Pw.print(sendPrefix+lineno++);      //2                //~1A23R~
        for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)                  //~1A1bI~
        {                                                          //~1A1bI~
	        for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)              //~1A1bI~
    	    {                                                      //~1A1bI~
                int p=pieces[ii][jj];                               //~1A1bI~
                Pw.print(" "+p);                                   //~1A1bI~
            }                                                      //~1A1bI~
        }                                                          //~1A1bI~
        Pw.println("");                                      //~1A1bI~//~1A23R~
        if (sendPrefix!=null)                                      //~1A23I~
	        Pw.print(sendPrefix+lineno++);       //3               //~1A23R~
        Pw.println("Tray");                                        //~1A23I~
        if (sendPrefix!=null)                                      //~1A23I~
	        Pw.print(sendPrefix+lineno++);       //4               //~1A23R~
        for (int ii=0;ii<2;ii++)                                   //~1A1bI~
        {                                                          //~1A1bI~
	        for (int jj=0;jj<MAX_PIECE_TYPE_CAPTURE;jj++)          //~1A1bI~
    	    {                                                      //~1A1bI~
                int t=tray[ii][jj];                                //~1A1bI~
                Pw.print(" "+t);                                   //~1A1bI~
            }                                                      //~1A1bI~
        }                                                          //~1A1bI~
        Pw.println("");                                            //~1A23I~
        if (notesTree==null)                                       //~1A2cM~
//      	NotesTree.writeHeader(Pw,sendPrefix,0/*ctr*/);         //~1A2cM~//~1A2mR~
        	NotesTree.writeHeader(Pw,sendPrefix,0/*ctr*/,notesTree);//~1A2mR~
        else                                                       //~1A2cM~
	    	notesTree.save(Pw,sendPrefix);                         //~1A2cM~
        if (sendPrefix!=null)                                      //~1A23I~
	        Pw.print(sendPrefix+lineno++);                         //~1A23I~
        Pw.println("Snapshot name="+name+"-"+seq+" End");        //~1A1bI~//~1A23R~
        if (sendPrefix!=null)                                      //~1A23I~
        {                                                          //~1A23I~
	        Pw.println(sendPrefix);      //eof                     //~1A23R~
        	if (Dump.Y) Dump.println("Notes:endeof "+sendPrefix);  //~1A23I~
        }                                                          //~1A23I~
        return lineno;//~1A23I~
    }//save                                                        //~1A1bI~
//***************************************************************************//~1A23I~
    public void sendNotes(PrintWriter Pw,String Prefix)            //~1A23I~
    {                                                              //~1A23I~
        if (Dump.Y) Dump.println("Notes:send name="+name+",seq="+seq);//~1A23I~
        sendPrefix=Prefix;                                         //~1A23I~
	    saveNotes(Pw);                                             //~1A23I~
        sendPrefix=null;                                           //~1A23I~
    }//save                                                        //~1A23I~
//***************************************************************************//~1A1bI~
//*load ss/sg file                                                 //~1A4sI~
//***************************************************************************//~1A4sI~
    public static Notes load(String Pfnm)                          //~1A1bR~
    {                                                              //~1A1bI~
    	Notes notes=null;                                          //~1A1bI~
        if (Dump.Y) Dump.println("Notes:load fnm="+Pfnm);          //~1A1bI~
        try // print out using the board class                     //~1A1bI~
        {                                                          //~1A1bI~
	    	BufferedReader fi=new BufferedReader(new InputStreamReader(new FileInputStream(Pfnm),"UTF-8"));//~1A1bI~
            notes=loadNotes(fi);                                   //~1A1bR~
            notes.filename=Pfnm;                                   //~1A30I~
            fi.close();                                            //~1A1bI~
        }                                                          //~1A1bI~
        catch (IOException e)                                     //~1A1bI~
        {                                                          //~1A1bI~
        	Dump.println(e,"Notes:load IO err");                   //~1A1bI~
			Alert.simpleAlertDialog(null,"File Load",e.toString(),Alert.BUTTON_POSITIVE);//~1A1bI~
        }                                                          //~1A1bI~
        return notes;                                              //~1A1bI~
    }//load                                                        //~1A30R~
//***************************************************************************//~1A30I~
    public static Notes load(String Pfnm,int Pfiletype,String Pencoding)//~1A30I~
    {                                                              //~1A30I~
        if (Dump.Y) Dump.println("Notes:load fnm="+Pfnm+",filetype="+Pfiletype+",encoding="+Pencoding);//~1A30I~
    	if (Pfnm.endsWith("."+FileDialog.SNAPSHOT_EXT)             //~1A30I~
    	||  Pfnm.endsWith("."+FileDialog.GAMES_EXT)                //~1A30I~
        )                                                          //~1A30I~
    		return Notes.load(Pfnm);                               //~1A30I~
    	return NotesFmt.createNotes(Pfnm,Pfiletype,Pencoding);     //~1A30I~
    }//load                                                        //~1A30I~
//***************************************************************************//~1A4sI~
    public static Notes loadClipboard(String Ptext,int Pfiletype)  //~1A4sR~
    {                                                              //~1A4sI~
    	return NotesFmt.createNotesClipboard(Ptext,Pfiletype);     //~1A4sR~
    }//load                                                        //~1A4sI~
//***************************************************************************//~1A1bI~
//*rc=4:file is not saved snapshot                                 //~1A1bI~
//*rc=8:io err                                                     //~1A1bI~
//*seq move yourcolor color wtime wtimeextra btime btimeextra totaltime extratime gameoptions handicap//~1A2eI~
//***************************************************************************//~1A1bI~
    private static Notes loadNotes(BufferedReader Pr)               //~1A1bR~//~1A2eR~
    {                                                              //~1A1bI~
    	int rc=0;                                                  //~1A1bI~
    	String line;                                               //~1A1bI~
    	StringParser p;                                            //~1A1bI~
        Notes notes=null;                                          //~1A1bR~
    //******************************                               //~1A1bI~
        try // print out using the board class                     //~1A1bR~
        {                                                          //~1A1bR~
            for (;;)                                               //~1A1bR~
            {                                                      //~1A1bR~
            //*hdr                                                 //~1A1bR~
                line=Pr.readLine();                                //~1A1bR~
                if (Dump.Y) Dump.println("loadNotes readline="+line);//~1A23I~
    //          Pw.println(FILE_ACRONYM+" "+name+" "+seq+" "+color);//~1A1bR~
                if (!line.startsWith(FILE_ACRONYM))                //~1A1bR~
                {                                                  //~1A1bR~
                    rc=4;                                          //~1A1bR~
                    break;                                         //~1A1bR~
                }                                                  //~1A1bR~
                int jp=line.charAt(FILE_ACRONYM.length())-'0';     //~1A2eI~
                String nm=line.substring(FILE_ACRONYM.length()+1);     //~1A1bI~//~1A2eR~
                line=Pr.readLine();                                //~1A1bI~
                if (Dump.Y) Dump.println("loadNotes readline="+line);//~1A23I~
                p=new StringParser(line);                          //~1A1bR~
                int sq=p.parseint();                               //~1A1bR~
                int mv=p.parseint();                               //~1A1bI~
                int yc=p.parseint();                               //~1A1bI~
                int col=p.parseint();                              //~1A1bR~
                int wt=p.parseint();    //white remaining time     //~1A1bI~
                int wte=p.parseint();   //white extra mode         //~1A1bI~
                int bt=p.parseint();    //                         //~1A1bI~
                int bte=p.parseint();   //                         //~1A1bI~
                int tt=p.parseint();    //totaltime                 //~1A1bR~
                int et=p.parseint();    //extratime                //~1A1bI~
                int go=p.parseint();    //gameoptions              //~1A1bI~
                int hc=p.parseint();    //handicap                 //~1A2cI~
        		String bn,wn;                                      //~1Ah0I~
        		bn=p.parsewordDQ();                                //~1Ah0I~
                if (bn.equals(""))                                 //~1Ah0I~
                {                                                  //~1Ah0I~
                	bn="nullB"; wn="nullW";                        //~1Ah0I~
                }                                                  //~1Ah0I~
                else                                               //~1Ah0I~
                {                                                  //~1Ah0I~
	        		wn=p.parsewordDQ();                            //~1Ah0I~
	                if (wn.equals(""))                             //~1Ah0I~
                    	wn="nullW";                                //~1Ah0I~
                }                                                  //~1Ah0I~
                if (Dump.Y) Dump.println("Notes.loadNotes　blackName="+bn+",whiteName="+wn);//+1Ah0R~
                notes=new Notes(nm,yc,col);                        //~1A1bR~
                notes.blackName=bn;                                //~1Ah0I~
                notes.whiteName=wn;                                //~1Ah0I~
                notes.coordType=jp;                                //~1A2eR~
                notes.seq=sq;                                      //~1A1bR~
                notes.moves0=mv;                                    //~1A1bI~
                notes.white=wt;                                    //~1A1bI~
                notes.whiteExtra=wte;                              //~1A1bI~
                notes.black=bt;                                    //~1A1bI~
                notes.blackExtra=bte;                              //~1A1bI~
                notes.totalTime=tt;                                //~1A1bR~
                notes.extraTime=et;                                //~1A1bI~
                notes.gameoptions=go;                              //~1A1bI~
                notes.handicap=hc;                                 //~1A2cI~
                int ct=getCoordType();                           //~1A2eR~
                notes.changeCoord=(ct!=notes.coordType);	//change movedescription language//~1A2eR~
            //*piece                                               //~1A1bR~
                line=Pr.readLine();                                //~1A1bR~
                if (line.startsWith(GTP_ACRONYM))                   //~1Ah0I~
                	line=loadNotesGtpOption(notes,line,Pr);        //~1Ah0R~
                if (Dump.Y) Dump.println("loadNotes readline="+line);//~1A23I~
                p=new StringParser(line);                          //~1A1bR~
                for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)          //~1A1bR~
                {                                                  //~1A1bR~
                    for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)      //~1A1bR~
                    {                                              //~1A1bR~
                        notes.pieces[ii][jj]=p.parseint();               //~1A1bR~
                    }                                              //~1A1bR~
                }                                                  //~1A1bR~
            //*tray                                                //~1A1bR~
                line=Pr.readLine();                                //~1A1bR~
                if (Dump.Y) Dump.println("loadNotes readline="+line);//~1A23I~
                line=Pr.readLine();                                //~1A1bR~
                if (Dump.Y) Dump.println("loadNotes readline="+line);//~1A23I~
                p=new StringParser(line);                          //~1A1bR~
                for (int ii=0;ii<2;ii++)                           //~1A1bR~
                {                                                  //~1A1bR~
                    for (int jj=0;jj<MAX_PIECE_TYPE_CAPTURE;jj++)  //~1A1bR~
                    {                                              //~1A1bR~
                                                                   //~1A1bR~
                        notes.tray[ii][jj]=p.parseint();                 //~1A1bR~
                    }                                              //~1A1bR~
                }                                                  //~1A1bR~
            //move tree                                            //~1A2cI~
                for (;;)                                           //~1A2cI~
                {                                                  //~1A2cI~
                	line=Pr.readLine();                            //~1A2cI~
                    if (NotesTree.setTree(notes,line))             //~1A2cI~
                    	break;                                     //~1A2cI~
                }                                                  //~1A2cI~
                break;                                             //~1A1bI~
            }                                                      //~1A1bR~
        }                                                          //~1A1bR~
        catch (IOException e)                                      //~1A1bR~
        {                                                          //~1A1bR~
            Dump.println(e,"Notes:load IO err");                   //~1A1bR~
            Alert.simpleAlertDialog(null,"File Load",e.toString(),Alert.BUTTON_POSITIVE);//~1A1bR~
            rc=8;                                                  //~1A1bR~
        }                                                          //~1A1bR~
	    if (Dump.Y) Dump.println("Notes:load name="+notes.name+",col="+notes.color+",seq="+notes.seq);//~1A1bI~
        if (rc!=0)                                                 //~1A1bI~
        	notes=null;                                            //~1A1bI~
    	return notes;                                              //~1A1bR~
    }//LoadNotes                                                   //~1A1bR~
//***************************************************************************//~1A23I~
    private static StringBuffer SrecvLines;                        //~1A23I~
    public static Notes receiveNotes(String Pline,boolean Pinit)   //~1A23R~
    {                                                              //~1A23I~
       Notes notes=null;                                          //~1A23I~
    //******************************                               //~1A23I~
    	if (Pinit)                                                 //~1A23I~
        {                                                          //~1A23I~
    		SrecvLines=new StringBuffer(Pline+"\n");               //~1A23I~
            return null;                                           //~1A23I~
        }                                                          //~1A23I~
        if (!Pline.equals("")) //not eof                              //~1A23I~
        {                                                          //~1A23I~
        	SrecvLines.append(Pline+"\n");                        //~1A23I~
            return null;                                           //~1A23I~
        }                                                          //~1A23I~
	    if (Dump.Y) Dump.println("Notes:receive lines="+SrecvLines);//~1A23R~
        try // print out using the board class                     //~1A23I~
        {                                                          //~1A23I~
	        InputStream is=new ByteArrayInputStream(SrecvLines.toString().getBytes("UTF-8"));//~1A23I~
		    BufferedReader fi=new BufferedReader(new InputStreamReader(is));              //~1A23I~
            notes=loadNotes(fi);                                   //~1A23I~
            fi.close();                                            //~1A23I~
        }                                                          //~1A23I~
        catch (IOException e)                                      //~1A23I~
        {                                                          //~1A23I~
        	Dump.println(e,"Notes:load IO err");                   //~1A23I~
			Alert.simpleAlertDialog(null,"File Load",e.toString(),Alert.BUTTON_POSITIVE);//~1A23I~
        }                                                          //~1A23I~
    	return notes;                                              //~1A23I~
    }//LoadNotes                                                   //~1A23I~
    //****************************************                     //~1A2eI~
    public static int getCoordType()                              //~1A2eR~
    {                                                              //~1A2eI~
    	int type;                                                  //~1A2eI~
		if ((AG.Options & AG.OPTIONS_DIGITALCOORDINATE)!=0)           //~1A2eI~
        	type=0;                                                //~1A2eI~
        else                                                       //~1A2eI~
        	type=AG.isLangJP?2:1;                                  //~1A2eR~
        return type;                                               //~1A2eI~
    }                                                              //~1A2eI~
    //****************************************                     //~1A4sI~
    //*rc=1:no move                                                //~1A4sI~
    //*rc=2:no initial piece                                       //~1A4sI~
    //****************************************                     //~1A4sI~
    public int chkValidity()                                       //~1A4sI~
    {                                                              //~1A4sI~
        int rc=2;                                                  //~1A4sI~
    	if (pieces!=null)                                          //~1A4sI~
        {                                                          //~1A4sI~
        	boolean swpiece=false;                                 //~1A4sI~
			for (int ii=0;ii<AG.BOARDSIZE_SHOGI;ii++)              //~1A4sI~
            {                                                      //~1A4sI~
				for (int jj=0;jj<AG.BOARDSIZE_SHOGI;jj++)          //~1A4sI~
                {                                                  //~1A4sI~
                	if ((pieces[ii][jj]&255)!=0)   //color 0x0300,piece 0xff//~1A4sR~
                    {                                              //~1A4sI~
                    	swpiece=true;                              //~1A4sI~
                    	break;                                     //~1A4sI~
                    }                                              //~1A4sI~
                }                                                  //~1A4sI~
                if (swpiece)                                       //~1A4sI~
                	break;                                         //~1A4sI~
            }                                                      //~1A4sI~
            if (swpiece)                                             //~1A4sI~
            {                                                      //~1A4sI~
            	rc=0;                                              //~1A4sI~
				if (notesTree==null || notesTree.size()==0)        //~1A4sR~
            		rc=1;                                          //~1A4sI~
            }                                                      //~1A4sI~
        }                                                          //~1A4sI~
        return rc;                                                 //~1A4sI~
    }                                                              //~1A4sI~
    public String getSavedFilename()                               //~1A4sI~
    {                                                              //~1A4sI~
        if (filenameSaved!=null)                                  //~1A4sI~
        	return filenameSaved;                                  //~1A4sI~
        return name;                                               //~1A4sI~
    }                                                              //~1A4sI~
    //****************************************                     //~1A4sI~
    public void display(ConnectedGoFrame Pcgf,boolean Pdisplaytree)//~1A4vR~
    {                                                              //~1A4vR~
    	displayNotes(Pcgf);                                        //~1A4vR~
    	if (Pdisplaytree)                                          //~1A4vR~
        	if (notesTree!=null)                                   //~1A4vR~
            {                                                      //~1A4vI~
              movelineCtr=                                         //~1A4vI~
	        	notesTree.display(Pcgf);	                       //~1A4vR~
    //*trailer                                                     //~1A4vI~
            	int gameoverreason=notesTree.gameoverMsgid;//by NotesFmt//~1A4vI~
            	if (gameoverreason!=0)                             //~1A4vI~
                {                                                  //~1A4vI~
                	trailerlineCtr=1;                              //~1A4vI~
	                String msg=AG.resource.getString(gameoverreason);//~1A4vR~
					Pcgf.appendComment(msg);                       //~1A4vI~
                }                                                  //~1A4vI~
                int winner=notesTree.winner;                       //~1A4vI~
                if (winner<0)                                      //~1A4vI~
                {                                                  //~1A4vI~
                    String msg="\n"+AG.resource.getString(R.string.WinnerWhite);//~1A4vR~
					Pcgf.appendComment(msg);                       //~1A4vI~
                	trailerlineCtr++;                              //~1A4vI~
                }                                                  //~1A4vI~
                else                                               //~1A4vI~
                if (winner>0)                                      //~1A4vI~
                {                                                  //~1A4vI~
                    String msg="\n"+AG.resource.getString(R.string.WinnerBlack);//~1A4vR~
					Pcgf.appendComment(msg);                       //~1A4vI~
                	trailerlineCtr++;                              //~1A4vI~
                }                                                  //~1A4vI~
            }                                                      //~1A4vI~
    }                                                              //~1A4vR~
    //****************************************                     //~1A4vR~
    //*return header line count                                    //~1A4vI~
    //****************************************                     //~1A4vI~
    public void displayNotes(ConnectedGoFrame Pcgf)                //~1A4vR~
    {                                                              //~1A4vR~
        String msg=name;                                           //~1A4vI~
        int lineno=1;                                              //~1A4vI~
        if (notesTree!=null && notesTree.headerCommentCtr!=0)      //~1A4vI~
        {                                                          //~1A4vI~
        	msg+="\n"+notesTree.headerComment.toString();	       //~1A4vR~
            lineno+=notesTree.headerCommentCtr;                   //~1A4vI~
        }                                                          //~1A4vI~
        else                                                       //~1A4vI~
        {                                                          //~1A4vI~
            if (whiteName!=null && !whiteName.equals("")           //~1A4vR~
            &&  blackName!=null && !blackName.equals(""))          //~1A4vR~
            {                                                      //~1A4vR~
                msg+="\n"+AG.resource.getString(R.string.White)+":"+whiteName+//~1A4vR~
                      "\n"+AG.resource.getString(R.string.Black)+":"+blackName;//~1A4vR~
                lineno+=2;                                         //~1A4vR~
            }                                                      //~1A4vR~
        }                                                          //~1A4vI~
        msg+="\n";                                                 //~1A4vR~
		Pcgf.appendComment(msg); 
        headerlineCtr=lineno;                                      //~1A4vI~
    }                                                              //~1A4vR~
//    //****************************************                   //~1AesI~
//    //*return header line count                                  //~1AesI~
//    //****************************************                   //~1AesI~
//    public void setMoveMsg()                                     //~1AesI~
//    {                                                            //~1AesI~
//        ActionMove a;                                            //~1AesI~
//        String msg=name;                                         //~1AesI~
//    //************************                                   //~1AesI~
//        for (int ii=0;ii<notesTree.size();ii++)                  //~1AesI~
//        {                                                        //~1AesI~
//            a=notesTree.get(ii);                                 //~1AesI~
//            if (a.actionMsg==null || a.actionMsg[0]!=null)       //~1AesI~
//                continue;                                        //~1AesI~
//            String color=a.color>0 ? AG.BlackSign : AG.WhiteSign;//~1AesI~
//            desc=aRules.moveDescription(a.color,a.piece,a.drop==1,a.iFrom,a.jFrom,a.iTo,a.jTo);//~1AesI~
//            desc=MOVE_DESCRIPTION/*"["*/+Integer.toString(Paction.moveNumber)+"] "+color+desc+" ";//~1AesI~
//            a.actionMsg[0]=desc;                                 //~1AesI~
//        }                                                        //~1AesI~
//    }                                                            //~1AesI~
    public int saveNotesGtpOption(PrintWriter Pw)                  //~1Ah0I~
    {                                                              //~1Ah0I~
    	int lineno=1;                                              //~1Ah0I~
        if (Dump.Y) Dump.println("Notes:saveNotesGtpOption");      //~1Ah0I~
        Pw.println(GTP_ACRONYM+" "+limitMode+"  "+limitTimeTotal+" "+limitTimeExtra+" "+limitTimeDepth);//~1Ah0I~
        if (subcmdList!=null)                                      //~1Ah0I~
            for (String s:subcmdList)                              //~1Ah0R~
            {                                                      //~1Ah0R~
                Pw.println(GTP_ACRONYM_SUBCMD+" "+s);              //~1Ah0R~
                lineno++;                                          //~1Ah0R~
            }                                                      //~1Ah0R~
        return lineno;	//lineno                                   //~1Ah0I~
    }                                                              //~1Ah0I~
    private static String loadNotesGtpOption(Notes Pnotes,String Pline,BufferedReader Pr)//~1Ah0R~
        throws IOException                                         //~1Ah0I~
    {                                                              //~1Ah0I~
    	StringParser p;                                            //~1Ah0I~
        if (Dump.Y) Dump.println("Notes:saveNotesGtpOption");      //~1Ah0I~
        Pnotes.swGtpGame=true;                                     //~1Ah0I~
        String line=Pline.substring(GTP_ACRONYM.length()+1);              //~1Ah0I~
        p=new StringParser(line);                                  //~1Ah0I~
        Pnotes.limitMode=p.parseint();                             //~1Ah0R~
        Pnotes.limitTimeTotal=p.parseint();                        //~1Ah0R~
        Pnotes.limitTimeExtra=p.parseint();                        //~1Ah0R~
        Pnotes.limitTimeDepth=p.parseint();                        //~1Ah0R~
        if (Dump.Y) Dump.println("Notes:loadNotesGtpOption  limitMode="+Pnotes.limitMode+",total="+Pnotes.limitTimeTotal+",extra="+Pnotes.limitTimeExtra+",depth="+Pnotes.limitTimeDepth);//~1Ah0R~
        Pnotes.subcmdList=new ArrayList<String>();                  //~1Ah0R~
        for (;;)                                                   //~1Ah0I~
        {                                                          //~1Ah0I~
            line=Pr.readLine();                                    //~1Ah0I~
            if (!line.startsWith(GTP_ACRONYM_SUBCMD))              //~1Ah0I~
            	break;                                             //~1Ah0I~
	        String subcmd=line.substring(GTP_ACRONYM_SUBCMD.length()+1);//~1Ah0R~
	        Pnotes.subcmdList.add(subcmd);                         //~1Ah0I~
	        if (Dump.Y) Dump.println("Notes:loadNotesGtpOption subcmd="+subcmd);//~1Ah0I~
        }                                                          //~1Ah0I~
        return line;                                               //~1Ah0I~
    }                                                              //~1Ah0I~
}
