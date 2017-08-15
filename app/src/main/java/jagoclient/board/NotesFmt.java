//*CID://+1Ah0R~:                             update#=  317;       //+1Ah0R~
//***********************************************************************
//1Ah0 2016/06/29 (Bug)NPE when format was not selected            //+1Ah0I~
//1Aev 2016/06/29 (Bug)NPE when format was not selected            //~1AevI~
//1Aeu 2016/06/29 set japanese of "Format" to file format of clipboard//~1AeuI~
//1Aer 2016/06/28 (Bug:reported by e54p25:2015/12/20) undo misses promoted status of captured piece//~1AerI~//~1AeuR~
//1A4z 2014/12/09 FileDialog:view file by click twice              //~1A4zI~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//1A4s 2014/12/06 utilize clipboard                                //~1A4sI~
//***********************************************************************
package jagoclient.board;

import static jagoclient.board.Field.*;
import jagoclient.Dump;
import jagoclient.partner.GameQuestion;
import jagoclient.partner.HandicapQuestion;
import static jagoclient.partner.HandicapQuestion.*;
import static jagoclient.board.Rules.*;
import static jagoclient.board.CapturedList.Spiecectr;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;


import com.Asgts.AG;
import com.Asgts.AView;
import com.Asgts.R;
import com.Asgts.Utils;

public class NotesFmt                                              //~v1A0R~//~3406R~
{
	protected final static int S=AG.BOARDSIZE_SHOGI;                 //~3407I~//~3409R~
	private final static String ENCODING_PREFIX="encoding=";       //~3406I~
//  private final static String DEFAULT_ENCODING="Shift_JIS";      //~1A4zR~
//  private final static String UTF8_ENCODING="UTF-8";             //~1A4zR~
    public  final static String DEFAULT_ENCODING="Shift_JIS";      //~1A4zI~
    public  final static String UTF8_ENCODING="UTF-8";             //~1A4zI~
	private final static String ENCODING_LABEL="Encoding";         //~3415I~
	private final static String CLIPBOARD_FILENAME="Clipboard";    //~1A4sI~
	private final static String CLIPBOARD_ENCODING=UTF8_ENCODING;  //~1A4sI~
//  public static final String[] ENCODINGTB={                      //~3415I~//~1AeuR~
    public static       String[] ENCODINGTB={                      //~1AeuI~
						ENCODING_LABEL,                            //~3415I~
						DEFAULT_ENCODING,                          //~3415I~
						UTF8_ENCODING,                             //~3415I~
    };                                                              //~3415I~
    private static final int FILETYPE_KIF=1;                       //~3408I~
    private static final int FILETYPE_KI2=2;                       //~3408I~
    private static final int FILETYPE_CSA=3;                       //~3408I~
    private static final int FILETYPE_GAM=4;                       //~3408I~
    private static final int FILETYPE_PSN=9;                       //~3408I~
    private static final String FILEID_KIF="kif";                   //~3408I~
    private static final String FILEID_KIFU="kifu";                 //~3408I~
    private static final String FILEID_KI2="ki2";                   //~3408I~
    private static final String FILEID_KI2U="ki2u";                 //~3408I~
    private static final String FILEID_CSA="csa";                   //~3408I~
    private static final String FILEID_PSN="psn";                  //~3408I~
    private static final String FILEID_PSN2="psn2";                //~3411I~
    private static final String FILEID_GAM="gam"; 	//by Kifu For Window(English)//~3408I~
    private static final String FILEID_LABEL="Format"; 	//by Kifu For Window(English)//~3415I~
//  public static final String[] FILETYPENAMETB={                  //~3415R~//~1AeuR~
    public static       String[] FILETYPENAMETB={                  //~1AeuI~
					FILEID_LABEL,                                  //~3415R~
					FILEID_KIF,                                    //~3415I~
    				FILEID_GAM,                                    //~3415M~
    				FILEID_CSA,                                    //~3415I~
    				FILEID_PSN,                                    //~3415I~
    };                                                             //~3415I~
    public static final int[] FILETYPEIDTB={                       //~3415I~
					-1,                                            //~3415I~
					FILETYPE_KIF,                                  //~3415I~
    				FILETYPE_GAM,                                  //~3415I~
    				FILETYPE_CSA,                                  //~3415I~
    				FILETYPE_PSN,                                  //~3415I~
    };                                                             //~3415I~
    protected static final int GAMEOVERID_RESIGN               =R.string.GameoverByResign;//~3409R~
    protected static final int GAMEOVERID_SUSPEND              =R.string.GameoverBySuspend;//~3409R~
    protected static final int GAMEOVERID_LOOP                 =R.string.GameoverByLoop;//~3409R~
    protected static final int GAMEOVERID_TIMEOUT              =R.string.GameoverByTimeout;//~3409R~
    protected static final int GAMEOVERID_FOUL                 =R.string.GameoverByFoul;//~3409R~
    protected static final int GAMEOVERID_INFINITE             =R.string.GameoverByInfinite;//~3409R~
    protected static final int GAMEOVERID_WIN                  =R.string.GameoverByDeclareWin;//~3409R~
    protected static final int GAMEOVERID_EVEN                 =R.string.GameoverByDeclareEven;//~3409R~
    protected static final int GAMEOVERID_CHECKMATE            =R.string.GameoverByCheckmate;//~3409R~
    protected static final int GAMEOVERID_NOCHECKMATE          =R.string.GameoverByNoCheckmate;//~3409R~
    protected static final int GAMEOVERID_ERROR                =R.string.GameoverByError;//~3409R~
                                                                   //~3409I~
	protected static final int HANDICAPTB[]={                          //~3406I~//~3409I~
            	0,          //0        hira                        //~3406I~//~3409M~
            	HC_LANCE1,	//1        kyou                        //~3406I~//~3409M~
            	HC_LANCE2,	//2        migi-kyou                   //~3406I~//~3409M~
            	HC_BISHOP,	//3        kaku                        //~3406I~//~3409M~
            	HC_ROOK,	//4        hisha                       //~3406I~//~3409M~
            	HC_ROOK+HC_LANCE1,						//5    hi+kyou//~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP,						//6    2   //~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1,			//7    3   //~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2,	//8    4   //~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2+HC_KNIGHT2,  //9    5(R-Knight)//~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2+HC_KNIGHT1,  //10   L-5(L-Knight)//~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2+HC_KNIGHT1+HC_KNIGHT2,//11 6//~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2+HC_KNIGHT1+HC_KNIGHT2//~3406I~//~3409M~
						+HC_SILVER1+HC_SILVER2,							//12 8//~3406I~//~3409M~
            	HC_ROOK+HC_BISHOP+HC_LANCE1+HC_LANCE2+HC_KNIGHT1+HC_KNIGHT2//~3406I~//~3409M~
						+HC_SILVER1+HC_SILVER2+HC_GOLD1+HC_GOLD2,		//13 10//~3406I~//~3409M~
            };                                                     //~3406I~//~3409M~
                                                                   //~3409I~
	protected static boolean swTest;                               //~3412I~
    protected String whiteName,blackName/*gameoverReason*/;                     //~3406I~//~3408M~//~3416R~
    protected static String filenameEncoding,readEncoding;                               //~3408M~//~3411R~
    protected String parmEncoding;                                 //~3415I~
    protected int fileType,lastColor,gameoverReasonId,handicap;                      //~3408R~//~3416R~
    protected Notes fmtNotes;                                       //~3409I~
    protected NotesTree fmtTree;                                   //~3409I~
    protected Position fmtPos;                                          //~3408M~//~3409I~
    protected int pieceTo,pieceFrom,iTo,jTo,iFrom,jFrom,posI,posJ;           //~3409I~//~3410R~
    protected int move1st=1,lasti,lastj,winner,moveNumber;         //~3409I~
    protected boolean pieceDrop,swInitPiece,swInitSaved;                       //~3409R~
    protected int[][] fmtTray;                                     //~3410I~
	protected int[] fmtUsedPieceCtr;	//for all remaining to White tray//~3412R~
    protected boolean pieceByPiece;	//initial position by piece by piece//~3416I~
    private boolean swClipboard;                                   //~1A4sI~
    private StringBuffer headerComment;                            //~1A4vI~
    private int headerCommentCtr;                                  //~1A4vI~
    private static final int headerCommentCtrMax=30;           //~1A4vI~
    //****************************************
	static                                                         //~1AeuR~
	{
        String fmt=AG.resource.getString(R.string.FileType);       //~1AeuR~
    	FILETYPENAMETB[0]=fmt;                                     //~1AeuI~
        String encoding=AG.resource.getString(R.string.FileEncoding);//~1AeuI~
    	ENCODINGTB[0]=encoding;                                    //~1AeuI~
    }                                                              //~1AeuI~
    //****************************************                     //~1AeuI~
	private String getFileEncoding(String Pfnm)                    //~1AeuI~
	{                                                              //~1AeuI~
    	String encoding=null;                                      //~3406I~
    	try                                                        //~3406I~
        {                                                          //~3406I~
    		FileReader fr=new FileReader(Pfnm);                    //~3406R~
        	BufferedReader br=new BufferedReader(fr);              //~3406R~
            String line=br.readLine();                             //~3406I~
            if (Dump.Y) Dump.println("getFileEncoding line="+line); //~3406I~//~3408R~
            int pos=line.indexOf(ENCODING_PREFIX);                 //~3406I~
            if (pos>0)                                             //~3406I~
                encoding=line.substring(pos+ENCODING_PREFIX.length());//~3406I~
            if (encoding==null)                                    //~3406I~
            {                                                      //~3407I~
//                if (!swTest)                                     //~3412R~
//                    AView.showToast(AG.resource.getString(R.string.ErrNotesFmtKifEncodingNotDefined,Pfnm));//~3412R~
                if (Dump.Y) Dump.println("no encoding on 1st line="+line+",readEncoding="+readEncoding+",filenameEncoding="+filenameEncoding);//~3407I~//~3411R~
                if (!parmEncoding.equals(""))                       //~3415I~
		            encoding=parmEncoding;                         //~3415I~
                else                                               //~3415I~
                if (filenameEncoding!=null)                        //~3408I~
		            encoding=filenameEncoding;                     //~3408I~
                else                                               //~3408I~
                if (readEncoding!=null)                            //~3411I~
		            encoding=readEncoding;                         //~3411I~
                else                                               //~3411I~
                {                                                  //~3412I~
                	if (isLangJP())                                //~3412I~
	                    encoding=DEFAULT_ENCODING;                 //~3412R~
                    else                                           //~3412I~
    	                encoding=UTF8_ENCODING;	//no encoding      //~3412R~
                }                                                  //~3412I~
                if (Dump.Y) Dump.println("no encoding on 1st line encoding="+encoding);//~3412I~
            }                                                      //~3407I~
            br.close();                                            //~3406I~
        }                                                          //~3406I~
		catch (Exception e)                                        //~3406I~
		{                                                          //~3406I~
        	Dump.println(e,"NotesFmt:getFileEncoding"+Pfnm);        //~3406I~//~3408R~
            if (!swTest)                                           //~3412I~
  				AView.showToast(AG.resource.getString(R.string.ErrNotesFmtOpenErr,Pfnm));//~3406I~//~3407R~//~3412R~
		}                                                          //~3406I~
        if (Dump.Y) Dump.println("getFileEncoding ="+encoding);    //~3411I~
        return encoding;                                           //~3406I~
	}
    //*******************************************************      //~3406I~
	private BufferedReader getEncodingStream(String Pfnm,String Pencoding)//~3406I~//~3408R~
	{                                                              //~3406I~
		BufferedReader br=null;                                    //~3406I~
		try                                                        //~3406I~
		{                                                          //~3406I~
			FileInputStream fis=new FileInputStream(Pfnm);         //~3406I~
			InputStreamReader isr=new InputStreamReader(fis,Pencoding);//~3406I~
			br=new BufferedReader(isr);                            //~3406I~
		}                                                          //~3406I~
		catch (UnsupportedEncodingException e)                     //~3406I~
		{                                                          //~3406I~
        	Dump.println(e,"NotesFmt:getEncodingStream"+Pfnm+",encoding="+Pencoding);//~3406I~//~3408R~
            if (!swTest)                                           //~3412I~
            	AView.showToast(AG.resource.getString(R.string.ErrNotesFmtNotSupportedEncoding,Pfnm,Pencoding));//~3406I~//~3407R~//~3412R~
		}                                                          //~3406I~
		catch (Exception e)                                        //~3406I~
		{                                                          //~3406I~
        	Dump.println(e,"NotesFmt:getEncodingStream"+Pfnm+",encoding="+Pencoding);//~3406I~//~3408R~
            if (!swTest)                                           //~3412I~
            	AView.showToast(AG.resource.getString(R.string.ErrNotesFmtOpenErr,Pfnm));//~3406I~//~3407R~//~3412R~
		}                                                          //~3406I~
        return br;                                                 //~3406I~
	}                                                              //~3406I~
    //*******************************************************      //~1A4sI~
	private BufferedReader getEncodingStreamString(String Ptext)   //~1A4sI~
	{                                                              //~1A4sI~
    	String encoding=CLIPBOARD_ENCODING;                        //~1A4sI~
    	String fnm=CLIPBOARD_FILENAME;                             //~1A4sI~
		BufferedReader br=null;                                    //~1A4sI~
		try                                                        //~1A4sI~
		{                                                          //~1A4sI~
        	InputStream is=new ByteArrayInputStream(Ptext.getBytes(encoding));//~1A4sI~
			InputStreamReader isr=new InputStreamReader(is,encoding);//~1A4sI~
			br=new BufferedReader(isr);                            //~1A4sI~
		}                                                          //~1A4sI~
		catch (UnsupportedEncodingException e)                     //~1A4sI~
		{                                                          //~1A4sI~
        	Dump.println(e,"NotesFmt:getEncodingStreamString ,encoding="+encoding);//~1A4sI~
            if (!swTest)                                           //~1A4sI~
            	AView.showToast(AG.resource.getString(R.string.ErrNotesFmtNotSupportedEncoding,fnm,encoding));//~1A4sR~
		}                                                          //~1A4sI~
//        catch (Exception e)                                      //~1A4sI~
//        {                                                        //~1A4sI~
//            Dump.println(e,"NotesFmt:getEncodingStreamString,encoding="+Pencoding);//~1A4sI~
//            if (!swTest)                                         //~1A4sI~
//                AView.showToast(AG.resource.getString(R.string.ErrNotesFmtOpenErr,fnm));//~1A4sR~
//        }                                                        //~1A4sI~
        return br;                                                 //~1A4sI~
	}                                                              //~1A4sI~
    //*************************************************************//~3411I~
    private int readFileType(String Pfnm)                      //~3411I~
	{                                                              //~3411I~
    	int filetype=0;                                            //~3411I~
        String line;                                               //~3411I~
    //********************************                             //~3411I~
    //*try SJIS                                                    //~3411I~
    	for (;;)
    	{//~3411I~
            line=readFileTypeSub(Pfnm,DEFAULT_ENCODING);           //~3411I~
            if (line!=null)                                        //~3411I~
                if (line.indexOf(NotesFmtKif.BLACK_SIGN)>=0)       //~3411I~
                {                                                  //~3411I~
                    filetype=FILETYPE_KI2;                         //~3411I~
                    readEncoding=DEFAULT_ENCODING;                 //~3411I~
                    break;                                         //~3411I~
                }                                                  //~3411I~
            line=readFileTypeSub(Pfnm,UTF8_ENCODING);              //~3411I~
            if (line!=null)                                        //~3411I~
            {                                                      //~3411I~
                if (line.indexOf(NotesFmtKif.BLACK_SIGN)>=0)       //~3411I~
                {                                                  //~3411I~
                    filetype=FILETYPE_KI2;                         //~3411I~
                    readEncoding=UTF8_ENCODING;                    //~3411I~
                }                                                  //~3411I~
                else                                               //~3412I~
                if (line.indexOf(NotesFmtCsa.CSA_COMMENT)>=0) //1st char mey be BOM(0xfeff)//~3411I~
                {                                                  //~3411I~
                    filetype=FILETYPE_CSA;   //contains encoding line comment//~3411I~
                }                                                  //~3411I~
                else                                               //~3412I~
                if (line.indexOf(NotesFmtPsn.PSN_HEADER)>=0        //~3411I~
                ||  line.indexOf(NotesFmtPsn.PSN_COMMENT)>=0)      //~3411I~
                {                                                  //~3411I~
                    filetype=FILETYPE_PSN;                         //~3411I~
                    readEncoding=UTF8_ENCODING;	//no encoding      //~3411I~
                    break;                                         //~3411I~
                }                                                  //~3411I~
                if (readEncoding==null)                            //~3412I~
                {                                                  //~3411I~
                	if (isLangJP())                                //~3411R~
	                    readEncoding=DEFAULT_ENCODING;             //~3411I~
                    else                                           //~3411I~
    	                readEncoding=UTF8_ENCODING;	//no encoding  //~3411I~
            	} 
                if (filetype==0)                                   //~3412I~
                {                                                  //~3412I~
                	if (isLangJP())                                //~3412I~
        				filetype=FILETYPE_KI2;                     //~3412I~
                    else                                           //~3412I~
	                    filetype=FILETYPE_PSN;                     //~3412I~
            	}                                                  //~3412I~
            }//~3411R~
        	break;                                                 //~3411I~
    	}
        if (Dump.Y) Dump.println("readFileType filetype="+filetype+",encoding="+readEncoding);//~3411I~
    	return filetype;//~3411I~
    }                                                              //~3411I~
    //*************************************************************//~3411I~
    private String readFileTypeSub(String Pfnm,String Pencoding)   //~3411I~
	{                                                              //~3411I~
    	String line=null;                                          //~3411I~
    //********************************                             //~3411I~
		BufferedReader br=getEncodingStream(Pfnm,Pencoding);       //~3411I~
        if (br!=null)                                              //~3411I~
        {                                                          //~3411I~
            try                                                    //~3411I~
            {                                                      //~3411I~
                line=br.readLine();                                //~3411I~
                if (Dump.Y) Dump.println("readFileType "+Pencoding+" line="+line);//~3411R~
                br.close();                                        //~3411I~
            }                                                      //~3411I~
            catch (Exception e)                                    //~3411I~
            {                                                      //~3411I~
                Dump.println(e,"NotesFmt:readFileType"+Pfnm);      //~3411I~
            }                                                      //~3411I~
        }                                                          //~3411I~
        return line;                                               //~3411I~
    }                                                              //~3411I~
    //*************************************************************//~3411I~
    private boolean chkFileType(String Pfnm)                        //~3408I~//~3409R~
	{                                                              //~3408I~
    	boolean rc=true;
    //********************************                             //~3409R~
        if (Pfnm.endsWith("."+FILEID_KIF))                             //~3408I~//~3415R~
        {                                                          //~3408I~
        	filenameEncoding=DEFAULT_ENCODING;	//shiftjis         //~3408I~
        	fileType=FILETYPE_KIF;                                 //~3408I~
        }                                                          //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_KIFU))                            //~3408I~//~3415R~
        {                                                          //~3408I~
        	filenameEncoding=UTF8_ENCODING;	//shiftjis             //~3408I~
        	fileType=FILETYPE_KIF;                                 //~3408I~
        }                                                          //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_KI2))                             //~3408I~//~3415R~
        {                                                          //~3408I~
        	filenameEncoding=DEFAULT_ENCODING;	//shiftjis         //~3408I~
        	fileType=FILETYPE_KI2;                                 //~3408I~
        }                                                          //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_KI2U))                            //~3408I~//~3415R~
        {                                                          //~3408I~
        	filenameEncoding=UTF8_ENCODING;	//shiftjis             //~3408I~
        	fileType=FILETYPE_KI2;                                 //~3408I~
        }                                                          //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_CSA))                             //~3408I~//~3415R~
        	fileType=FILETYPE_CSA;                                 //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_GAM))                             //~3408I~//~3415R~
        	fileType=FILETYPE_GAM;                                 //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_PSN))                             //~3408I~//~3415R~
        	fileType=FILETYPE_PSN;                                 //~3408I~
        else                                                       //~3408I~
        if (Pfnm.endsWith("."+FILEID_PSN2))                            //~3411I~//~3415R~
        	fileType=FILETYPE_PSN;                                 //~3411I~
        else                                                       //~3411I~
        {                                                          //~3411I~
        	fileType=readFileType(Pfnm);
        }                                                          //~3411I~
        if (fileType==0)                                           //~3408I~
        {
        	rc=false;//~3408I~
            if (!swTest)                                           //~3412I~
            	AView.showToast(AG.resource.getString(R.string.ErrNotesFmtUnknownFileFormat,Pfnm));//~3408I~//~3412R~
        	if (Dump.Y) Dump.println("7chkFileType err fnm="+Pfnm);//~1A4sR~
        }
        return rc;//~3408I~
    }                                                              //~3408I~
    //*************************************************************//~1A4zI~
    public static String getEncodingFromFilename(String Pfnm)      //~1A4zI~
	{                                                              //~1A4zI~
    	String encoding=null;                                      //~1A4zI~
    //********************************                             //~1A4zI~
        if (Pfnm.endsWith("."+FILEID_KIF))                         //~1A4zI~
        	encoding=DEFAULT_ENCODING;	//shiftjis                 //~1A4zI~
        else                                                       //~1A4zI~
        if (Pfnm.endsWith("."+FILEID_KIFU))                        //~1A4zI~
        	encoding=UTF8_ENCODING;                                //~1A4zI~
        else                                                       //~1A4zI~
        if (Pfnm.endsWith("."+FILEID_KI2))                         //~1A4zI~
        	encoding=DEFAULT_ENCODING;	//shiftjis                 //~1A4zI~
        else                                                       //~1A4zI~
        if (Pfnm.endsWith("."+FILEID_KI2U))                        //~1A4zI~
        	encoding=UTF8_ENCODING;                                //~1A4zI~
        if (Dump.Y) Dump.println("getEncodingFromFilename fnm="+Pfnm+",encoding="+encoding);//~1A4zI~
        return encoding;                                           //~1A4zI~
    }                                                              //~1A4zI~
    //*************************************************************//~3406I~******//~3415I~
	public static Notes createNotes(String Pfnm,int Pfiletype,String Pencoding)//~3415I~
	{                                                              //~3415I~
    	filenameEncoding=null;	//clear static;                    //~3415I~
    	readEncoding=null;                                         //~3415I~
        NotesFmt fmt=new NotesFmt();                               //~3415I~
        if (Pfiletype<0)                                          //~3415I~
        {                                                          //~3415I~
            if (!fmt.chkFileType(Pfnm))                            //~3415I~
                return null;                                       //~3415I~
        }                                                          //~3415I~
        else                                                       //~3415I~
	        fmt.fileType=Pfiletype;                                //~3415I~
		return fmt.createNotesSub(Pfnm,Pencoding);
	}//~3415I~
    //*************************************************************//~3406I~******//~3409I~
	public static Notes createNotes(String Pfnm)                     //~3409I~
	{                                                              //~3409I~
    	filenameEncoding=null;	//clear static;                    //~3411I~
    	readEncoding=null;                                         //~3411I~
        NotesFmt fmt=new NotesFmt();                               //~3409R~
		if (!fmt.chkFileType(Pfnm))                                //~3409R~
        	return null;                                                //~3409I~
		return fmt.createNotesSub(Pfnm,""/*encoding*/);     //~3415I~
    }                                                              //~3415I~
    //*************************************************************//~3406I~******//~1A4sI~
	public static Notes createNotesClipboard(String Ptext,int Pfiletype)//~1A4sI~
	{                                                              //~1A4sI~
    	filenameEncoding=null;	//clear static;                    //~1A4sI~
    	readEncoding=null;                                         //~1A4sI~
        NotesFmt fmt=new NotesFmt();                               //~1A4sI~
        fmt.fileType=Pfiletype;                                    //~1A4sR~
		return fmt.createNotesSubClipboard(Ptext);                 //~1A4sI~
    }                                                              //~1A4sI~
    //*************************************************************//~3406I~******//~1A4sI~
	private Notes createNotesSub(String Pfnm,String Pencoding)     //~3415I~
    {                                                              //~3415I~
        NotesFmt fmt=null;
		switch(fileType)                                       //~3409R~
        {                                                          //~3409I~
        case FILETYPE_CSA:                                         //~3409R~
            fmt=new NotesFmtCsa();                                 //~3409R~
            break;                                                 //~3409R~
        case FILETYPE_KIF:                                         //~3409I~
        case FILETYPE_KI2:                                         //~3409I~
        	fmt=new NotesFmtKif();                                 //~3409R~
            break;                                                 //~3409I~
        case FILETYPE_GAM:                                         //~3409R~
            fmt=new NotesFmtGam();                                 //~3409R~
            break;                                                 //~3409R~
        case FILETYPE_PSN:                                       //~3409I~//~3410R~
            fmt=new NotesFmtPsn();                               //~3409R~//~3410R~
            break;                                               //~3409I~//~3410R~
        }                                                          //~3409I~
        fmt.parmEncoding=Pencoding;                                //~3415I~
    	fmt.createTree(Pfnm);                                      //~3409I~
        fmt.setupNotes(fileType);                                   //~3412I~//~3415R~
        fmt.fmtNotes.filename=Pfnm;                                //~3417I~
       return fmt.fmtNotes;                                           //~3412I~
    }                                                              //~3409I~
    //*************************************************************//~3406I~******//~1A4sI~
	private Notes createNotesSubClipboard(String Ptext)            //~1A4sI~
    {                                                              //~1A4sI~
        NotesFmt fmt=null;                                         //~1A4sI~
		switch(fileType)                                           //~1A4sI~
        {                                                          //~1A4sI~
        case FILETYPE_CSA:                                         //~1A4sI~
            fmt=new NotesFmtCsa();                                 //~1A4sI~
            break;                                                 //~1A4sI~
        case FILETYPE_KIF:                                         //~1A4sI~
        case FILETYPE_KI2:                                         //~1A4sI~
        	fmt=new NotesFmtKif();                                 //~1A4sI~
            break;                                                 //~1A4sI~
        case FILETYPE_GAM:                                         //~1A4sI~
            fmt=new NotesFmtGam();                                 //~1A4sI~
            break;                                                 //~1A4sI~
        case FILETYPE_PSN:                                         //~1A4sI~
            fmt=new NotesFmtPsn();                                 //~1A4sI~
            break;                                                 //~1A4sI~
        default:                                                   //~1AevI~
        	return null;                                           //~1AevI~
        }                                                          //~1A4sI~
    	fmt.swClipboard=true;                                      //~1A4sI~
        fmt.parmEncoding=CLIPBOARD_ENCODING;                       //~1A4sI~
    	fmt.createTreeClipboard(CLIPBOARD_FILENAME,Ptext);         //~1A4sI~
        fmt.setupNotes(fileType);                                  //~1A4sI~
        fmt.fmtNotes.filename=CLIPBOARD_FILENAME;                  //~1A4sI~
        fmt.fmtNotes.swClipboard=true;                             //~1A4sI~
       return fmt.fmtNotes;                                        //~1A4sI~
    }                                                              //~1A4sI~
    //******************************************************************************//~3412I~
	protected void setupNotes(int Pfiletype)                                    //~3412I~//~3415R~
	{                                                              //~3412I~
        fmtNotes.setTree(fmtTree);                                 //~3412I~
        fmtNotes.handicap=handicap;                                //~3412I~
        fmtNotes.blackName=blackName; fmtNotes.whiteName=whiteName;//~3412I~
        fmtNotes.filetype=Pfiletype;                               //~3415I~
        fmtNotes.gameoptions|=GameQuestion.GAMEOPT_NOTESFILE;      //~3417I~
		saveInitialStatus();	//for the case no ActionMove added //~3414I~
    	saveTray(false);         //ending status to Notes.tray     //~3415I~
        savePieceColor(false);   //ending status                   //~3415I~
        fmtTree.gameoverMsgid=gameoverReasonId;                    //~3416R~
        fmtTree.winner=winner;                                     //~3416R~
        fmtTree.headerCommentCtr=headerCommentCtr;                 //~1A4vI~
        fmtTree.headerComment=headerComment;                       //~1A4vI~
    }                                                              //~3412I~
    //******************************************************************************//~3414I~
	protected void saveInitialStatus()                             //~3414I~
	{                                                              //~3414I~
    	if (swInitSaved)                                           //~3414I~
        	return;                                                //~3414I~
    	swInitSaved=true;                                          //~3414I~
    	if (Dump.Y) Dump.println("saveInitialStatus");             //~3414I~
        fmtNotes.changeCoord=true;   //force to set actionMsg[0]        //~3415I~//~3416R~
        if (pieceByPiece)   //not by handicap                      //~3416I~
        {                                                          //~3416I~
	    	saveTray(true);	                                         //~3414I~//~3415R~//~3416R~
    	    savePieceColor(true);  //notes initial arrange by pieceAtStart                                 //~3414I~//~3415R~//~3416R~
        }                                                          //~3416I~
    	if (Dump.Y) showBoard();                                   //~3414I~
    }                                                              //~3414I~
    //******************************************************************************//~3414I~
	private void saveTray(boolean Pstart)                               //~3414I~//~3415R~
	{                                                              //~3414I~
        int[][] tray;                                              //~3415I~
    	if (Pstart)                                                //~3415I~
	    	tray=fmtNotes.trayAtStart=new int[2][MAX_PIECE_TYPE_CAPTURE];//~3415I~
        else                                                       //~3415I~
        	tray=fmtNotes.tray;                                    //~3415R~
    	for (int ii=0;ii<2;ii++)                                   //~3414I~
        	for (int jj=0;jj<MAX_PIECE_TYPE_CAPTURE;jj++)          //~3414I~
            	tray[ii][jj]=fmtTray[ii][jj];                      //~3414I~
    }                                                              //~3414I~
    //******************************************************************************//~3414I~
	private void savePieceColor(boolean Pstart)                         //~3414I~//~3415R~
	{                                                              //~3414I~
	    int[][] p;                                            //~3415I~
    	if (Dump.Y) Dump.println("saveInitialPieceColor");
    	if (Pstart)//~3415I~
        	p=fmtNotes.piecesAtStart=new int[AG.BOARDSIZE_SHOGI][AG.BOARDSIZE_SHOGI];//~3415I~
        else                                                       //~3415I~
        	p=fmtNotes.pieces;                                //~3415I~
    	ConnectedBoard.getAllBoardPieces(p,fmtPos);           //~3415R~
    }                                                              //~3414I~
    //******************************************************************************//~3409R~
	protected void createTree(String Pfnm)                 //~3406I~  //~3408R~//~3409R~
	{   
    //************************************                         //~3409I~
    	fmtNotes=new Notes(Pfnm,1,1);    //@@@@                    //~3409R~
    	fmtTree=new NotesTree(1/*color*/);                         //~3409R~
        fmtPos=new Position(S);                                    //~3409R~
        fmtTray=new int[2][MAX_PIECE_TYPE_CAPTURE];                //~3410I~
	    fmtUsedPieceCtr=new int[MAX_PIECE_TYPE_CAPTURE];	//for all remaining to White tray//~3412R~
    	String encoding=getFileEncoding(Pfnm);                      //~3406I~//~3408R~
        if (encoding==null)                                        //~3406I~
        	return;                                                //~3406I~
        BufferedReader br=getEncodingStream(Pfnm,encoding);             //~3406I~//~3408R~
		createTreeSub(br);                                         //~1A4sI~
    }                                                              //~1A4sI~
    //******************************************************************************//~1A4sI~
	protected void createTreeClipboard(String Pfnm,String Ptext)   //~1A4sI~
	{                                                              //~1A4sI~
    //************************************                         //~1A4sI~
    	fmtNotes=new Notes(Pfnm,1,1);    //@@@@                    //~1A4sI~
    	fmtTree=new NotesTree(1/*color*/);                         //~1A4sI~
        fmtPos=new Position(S);                                    //~1A4sI~
        fmtTray=new int[2][MAX_PIECE_TYPE_CAPTURE];                //~1A4sI~
	    fmtUsedPieceCtr=new int[MAX_PIECE_TYPE_CAPTURE];	//for all remaining to White tray//~1A4sI~
        BufferedReader br=getEncodingStreamString(Ptext);          //~1A4sI~
		createTreeSub(br);                                         //~1A4sI~
    }                                                              //~1A4sI~
    //******************************************************************************//~1A4sI~
	protected void createTreeSub(BufferedReader Pbr)               //~1A4sI~
    {                                                              //~1A4sI~
    	boolean rc;                                                //~1A4sI~
        String line="";                                            //~1A4sI~
    //************************************                         //~1A4sI~
        BufferedReader br=Pbr;                                     //~1A4sI~
        if (br==null)                                              //~3406I~
        	return;                                                //~3406I~
    	try                                                        //~3406I~
        {                                                          //~3406I~
            for (;;)                                               //~3406I~
            {                                                      //~3406I~
            	line=br.readLine();                         //~3406I~
                if (line==null)  //eof                             //~3407I~
                	break;                                         //~3407I~
                if (line.length()==0)                              //~3416I~
                	continue;                                      //~3416I~
            	if (Dump.Y) Dump.println("getLine line="+line);//~3406I~//~3409R~
            	line=line.trim();    //~3408I~         //~3409R~   //~3416R~
                if (line.length()==0)                              //~3416I~
                	continue;                                      //~3416I~
            	rc=setTreeEntry(line.trim());                      //~3416I~
                if (!rc)                                           //~3408I~
                	break;                                         //~3408I~
            }                                                      //~3406I~
            br.close();                                            //~3406I~
            if (blackName!=null && whiteName!=null)                //~3406I~
            {                                                      //~3416I~
//          	fmtNotes.name+=":"+whiteName+"-"+blackName;           //~3406I~//~3409R~//~3416R~
              if (swClipboard)                                     //~1A4sI~
              {                                                    //~1A4sI~
			    String ts=Utils.getTimeStamp(Utils.TS_DATE_TIME);  //~1A4sI~
            	fmtNotes.name=ts+":"+whiteName+"-"+blackName;      //~1A4sI~
              }                                                    //~1A4sI~
              else                                                 //~1A4sI~
              {                                                    //~1A4sI~
                String fnm=fmtNotes.name;                          //~3416I~
                int pos=fnm.lastIndexOf("/");                      //~3416I~
                if (pos>0&&(pos+1)<fnm.length())                   //~3416R~
//                  fnm=fnm.substring(pos+1)+"("+fnm.substring(0,pos+1)+")";//~3416R~//~1A4vR~
                    fnm=fnm.substring(pos+1);                      //~1A4vI~
            	fmtNotes.name=whiteName+":=:"+blackName+"("+fnm+")";//~3416R~
              }                                                    //~1A4sI~
            }                                                      //~3416I~
            else                                                   //~3416I~
            {                                                      //~3416I~
                String fnm=fmtNotes.name;                          //~3416I~
              if (swClipboard)                                     //~1A4sI~
              {                                                    //~1A4sI~
			    String ts=Utils.getTimeStamp(Utils.TS_DATE_TIME);  //~1A4sI~
            	fmtNotes.name=ts+":"+fnm;                          //~1A4sI~
              }                                                    //~1A4sI~
              else                                                 //~1A4sI~
              {                                                    //~1A4sI~
                int pos=fnm.lastIndexOf("/");                      //~3416I~
                if (pos>0&&(pos+1)<fnm.length())                   //~3416I~
//                  fnm=fnm.substring(pos+1)+"("+fnm.substring(0,pos+1)+")";//~3416I~//~1A4vR~
                    fnm=fnm.substring(pos+1);                      //~1A4vI~
            	fmtNotes.name=fnm;                                 //~3416I~
              }                                                    //~1A4sI~
            }                                                      //~3416I~
        	if (Dump.Y) Dump.println("name="+fmtNotes.name);          //~3407I~
        	if (Dump.Y) Dump.println("gameover="+gameoverReasonId+",winner="+winner);//~3407I~
        }                                                          //~3406I~
		catch (Exception e)                                        //~3406I~
		{                                                          //~3406I~
        	Dump.println(e,"NotesFmt:createKifTree:");        //~3406I~
            if (!swTest)                                           //~3412I~
            	AView.showToast(AG.resource.getString(R.string.ErrNotesFmtInvalidLine,line));//~3406I~//~3407R~//~3412R~
		}                                                          //~3406I~
	}                                                              //~3406I~
    //**************************************************************************//~3407I~//~3409M~
	protected boolean getAction(int Pcolor)                        //~3409I~
    {                                                              //~3407I~//~3409M~
        boolean rc=true;                                           //~3409M~
        int capturedpiece;//~3407I~                                //~3409M~
        int capturedpieceNP;    //non-promoted to set to tray      //~1AerI~
    //*****************************************                    //~3407I~//~3409M~
    	if (moveNumber==1)                                         //~3414I~
			saveInitialStatus();                                   //~3414I~
        if (!pieceDrop)                                            //~3407I~//~3409M~
        {                                                          //~3407I~//~3409M~
	        fmtPos.setPiece(iFrom,jFrom,0,0);    //del moved from   //~3407I~//~3408R~//~3409M~
//      	capturedpiece=Field.nonPromoted(fmtPos.piece(iTo,jTo));//~3407I~//~3408R~//~3409M~//~1AerR~
        	capturedpiece=fmtPos.piece(iTo,jTo);                   //~1AerI~
        	capturedpieceNP=Field.nonPromoted(capturedpiece);      //~1AerI~
            if (capturedpiece!=0)                                  //~3411I~
//              setToTray(Pcolor,capturedpiece,1/*addctr*/);       //~3411I~//~1AerR~
                setToTray(Pcolor,capturedpieceNP,1/*addctr*/);     //~1AerI~
        }                                                          //~3407I~//~3409M~
        else                                                       //~3407I~//~3409M~
        {                                                          //~3411I~
        	capturedpiece=0;                                       //~3407I~//~3409M~
            pieceTo=Field.nonPromoted(pieceTo); //avoid exception by invalid data//~3416I~
            setToTray(Pcolor,pieceTo,-1/*addctr*/);                //~3411I~
        }                                                          //~3411I~
        fmtPos.setPiece(iTo,jTo,Pcolor,pieceTo);            //~3407I~ //~3408I~//~3409M~
        int yourcolor=1;                                           //~3407M~//~3409M~
        if (Dump.Y) Dump.println("yc="+yourcolor+",move="+moveNumber+",col="+Pcolor+",piece="+pieceFrom+",drop="+pieceDrop+",iTo="+iTo+",jTo="+jTo+",iFrom="+iFrom+",jFrom="+jFrom+//~3407I~//~3409M~//~3411R~
        			",capturedp="+capturedpiece+",pieceto="+pieceTo);//~3407I~//~3409M~
		if (Dump.Y) showBoard();                                   //~3407M~//~3409M~
        if (!swTest)                                               //~3412I~
        {                                                          //~3412I~
        	ActionMove a=new ActionMove(yourcolor,moveNumber,Pcolor,pieceFrom,pieceDrop,iFrom,jFrom,iTo,jTo,//~3407M~//~3409M~//~3412R~
        				capturedpiece,pieceTo);                        //~3407M~//~3409M~//~3412R~
        		fmtTree.add(a);                                              //~3407M~//~3409M~//~3412R~
		}                                                          //~3412I~
        if (Dump.Y) Dump.println("getAction rc="+rc);              //~3408I~//~3409M~
		return rc;                                                 //~3407I~//~3409M~
	}                                                              //~3407I~//~3409M~
    //**************************************************************************//~3411I~
	protected void setToTray(int Pcolor,int Ppiece,int Pctr/*addctr*/)       //~3411I~//~3412R~
    {                                                              //~3411I~
    	int colidx=Pcolor>0?1:0;                                   //~3411I~
    	int pieceidx=MAX_PIECE_TYPE_CAPTURE-Ppiece;                //~3411R~
    	fmtTray[colidx][pieceidx]+=Pctr;                           //~3411I~
        if (Dump.Y) Dump.println("setToTray colidx="+colidx+",piece="+Ppiece+",addctr="+Pctr+",totalnewctr="+fmtTray[colidx][pieceidx]);//~3411I~
    }                                                              //~3411I~
    //**************************************************************************//~3412I~
    //*initialize tray                                             //~3412I~
    //*Paccum: true:+/-, false:=                                   //~3412I~
    //**************************************************************************//~3412I~
	protected void setToTrayInitial(int Pcolor,int Ppiece,int Pctr/*addctr*/,boolean Paccum)//~3412I~
    {                                                              //~3412I~
        if (Dump.Y) Dump.println("setToTrayInitial accum="+Paccum+",piece="+Ppiece+",ctr="+Pctr+",color="+Pcolor);//~3414I~
    	int colidx=Pcolor>0?1:0;                                   //~3412I~
    	int pieceidx=MAX_PIECE_TYPE_CAPTURE-Ppiece;                //~3412I~
        if (Paccum)                                                //~3412I~
	    	fmtTray[colidx][pieceidx]+=Pctr;                       //~3412I~
        else                                                       //~3412I~
	    	fmtTray[colidx][pieceidx]=Pctr;                        //~3412I~
        if (Ppiece>0 && Ppiece<=MAX_PIECE_TYPE_CAPTURE)                   //~3412I~
			fmtUsedPieceCtr[Ppiece-1]+=Pctr;                        //~3412R~
        if (Dump.Y) Dump.println("setToTrayInitial accum="+Paccum+",colidx="+colidx+",piece="+Ppiece+",ctr="+Pctr+",totalnewctr="+fmtTray[colidx][pieceidx]);//~3412I~
    }                                                              //~3412I~
    //**************************************************************************//~3412I~
	protected void setToTrayAllRemaining(int Pcolor)               //~3412I~
    {                                                              //~3412I~
    	int colidx=Pcolor>0?1:0;                                   //~3412I~
        for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)              //~3412I~
        {                                                          //~3412I~
            int ctr=fmtUsedPieceCtr[MAX_PIECE_TYPE_CAPTURE-ii-1];  //~3412I~
            if (Dump.Y) Dump.println("FEN Tray remaining="+ctr);   //~3412I~
            fmtTray[colidx][ii]=Math.max(0,Spiecectr[ii]*2-ctr);   //~3412I~
        }                                                          //~3412I~
    }                                                              //~3412I~
    //**************************************************************************//~3407I~//~3409M~
	protected boolean getMoveFromSub(int Pmode,int Pcolor)  //~3407I~//~3408R~//~3409I~
	{                                                              //~3407I~//~3409M~
         boolean rc=true;                                          //~3407I~//~3409M~
     //*****************************************                   //~3407I~//~3409M~
     	if (Dump.Y) Dump.println("getMoveFRomSub mode="+Pmode+",color="+Pcolor);//~3409I~
        switch(pieceFrom)                                          //~3407I~//~3409M~
        {                                                          //~3407I~//~3409M~
        case PIECE_PAWN:                                           //~3407I~//~3409M~
        	getMoveFrom_Pawn(Pmode,Pcolor);                    //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_PPAWN:                                          //~3407I~//~3409M~
        	getMoveFrom_PPawn(Pmode,Pcolor);                   //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_LANCE:                                          //~3407I~//~3409M~
        	getMoveFrom_Lance(Pmode,Pcolor);                   //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_PLANCE:                                         //~3407I~//~3409M~
        	getMoveFrom_PLance(Pmode,Pcolor);                  //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_KNIGHT:                                         //~3407I~//~3409M~
        	getMoveFrom_Knight(Pmode,Pcolor);                  //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_PKNIGHT:                                        //~3407I~//~3409M~
        	getMoveFrom_PKnight(Pmode,Pcolor);                 //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_SILVER:                                         //~3407I~//~3409M~
        	getMoveFrom_Silver(Pmode,Pcolor);                  //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_PSILVER:                                        //~3407I~//~3409M~
        	getMoveFrom_PSilver(Pmode,Pcolor);                 //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_GOLD:                                           //~3407I~//~3409M~
        	getMoveFrom_Gold(Pmode,Pcolor);                    //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_BISHOP:                                         //~3407I~//~3409M~
        	getMoveFrom_Bishop(Pmode,Pcolor);                  //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_PBISHOP:                                        //~3407I~//~3409M~
        	getMoveFrom_PBishop(Pmode,Pcolor);                 //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_ROOK:                                           //~3407I~//~3409M~
        	getMoveFrom_Rook(Pmode,Pcolor);                    //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_PROOK:                                           //~3407I~//~3409M~
        	getMoveFrom_PRook(Pmode,Pcolor);                   //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        case PIECE_KING:                                           //~3407I~//~3409M~
        case PIECE_KING_CHALLENGING:                                //~3407I~//~3409M~
        	getMoveFrom_King(Pmode,Pcolor);                    //~3407I~//~3409I~
            break;                                                 //~3407I~//~3409M~
        default:                                                   //~3407I~//~3409M~
        	rc=false;                                              //~3407I~//~3409M~
        }                                                          //~3407I~//~3409M~
     	if (Dump.Y) Dump.println("getMoveFRomSub rc="+rc+",pieceFrom="+pieceFrom+",mode="+Pmode+",color="+Pcolor);//~3409I~
        return rc;                                                 //~3407I~//~3409M~
	}                                                              //~3407I~//~3409M~
    //**************************************************************************//~3407I~//~3409M~
	private boolean getMoveFrom_Pawn(int Pmode,int Pcolor)//~3407I~//~3408R~//~3409I~
	{                                                              //~3407I~//~3409M~
         boolean rc=true,drop=true;                                //~3407I~//~3409M~
         int ii,jj,piece,color;                                          //~3407I~//~3408R~//~3409M~
     //*****************************************                   //~3407I~//~3409M~
     	ii=iTo; jj=jTo;                                            //~3407I~//~3409M~
        jj+=Pcolor;                                                //~3408R~//~3409M~
        if (jj>=0 && jj<S)                                         //~3407I~//~3409M~
        {                                                          //~3407I~//~3409M~
        	piece=fmtPos.piece(ii,jj);                           //~3407I~//~3408R~//~3409M~
            color=fmtPos.color(ii,jj);                           //~3407I~//~3408R~//~3409M~
            if (color==Pcolor && piece==pieceFrom)                 //~3407I~//~3409M~
            {	                                                   //~3407I~//~3409M~
            	iFrom=ii; jFrom=jj;                                //~3407I~//~3409M~
                drop=false;	                                       //~3407I~//~3409M~
            }                                                      //~3407I~//~3409M~
        }                                                          //~3407I~//~3409M~
        pieceDrop=drop;                                            //~3407I~//~3409M~
        return rc;                                                 //~3407I~//~3409M~
	}                                                              //~3407I~//~3409M~
    //**************************************************************************//~3407I~//~3409M~
	private boolean getMoveFrom_Lance(int Pmode,int Pcolor)//~3407I~//~3408R~//~3409I~
	{                                                              //~3407I~//~3409M~
         boolean rc=true,drop=true;                                //~3407I~//~3409M~
         int ii,jj,jstep,color,piece;                                          //~3407I~//~3409M~
     //*****************************************                   //~3407I~//~3409M~
     	ii=iTo; jj=jTo;                                            //~3407I~//~3409M~
        jstep=Pcolor;     //back position                   //~3407I~//~3408R~//~3409M~
        for (;;)                                                   //~3407I~//~3409M~
        {                                                          //~3407I~//~3409M~
        	jj+=jstep;                                             //~3407I~//~3409M~
	        if (jj<0 || jj>=S)                                     //~3407I~//~3408R~//~3409M~
            	break;                                             //~3408I~//~3409M~
            color=fmtPos.color(ii,jj);                       //~3407I~//~3408R~//~3409M~
            if (color==0)                                          //~3408I~//~3409M~
            	continue;                                          //~3408I~//~3409M~
            if (color!=Pcolor)                                     //~3408I~//~3409M~
            	break;                                             //~3408I~//~3409M~
            piece=fmtPos.piece(ii,jj);                       //~3407I~//~3408R~//~3409M~
            if (piece!=pieceFrom)                                  //~3408I~//~3409M~
                break;                                             //~3408I~//~3409M~
            iFrom=ii; jFrom=jj;                            //~3407I~//~3408R~//~3409M~
            drop=false;                                    //~3407I~//~3408R~//~3409M~
            break;                                                 //~3408I~//~3409M~
        }                                                          //~3407I~//~3409M~
        pieceDrop=drop;                                            //~3407I~//~3409M~
        return rc;                                                 //~3407I~//~3409M~
	}                                                              //~3407I~//~3409M~
    //**************************************************************************//~3407I~//~3409M~
	private boolean getMoveFrom_Knight(int Pmode,int Pcolor)//~3407I~//~3408R~//~3409I~
	{                                                              //~3407I~//~3409M~
         boolean rc=true,drop=true;                                //~3407I~//~3409M~
         int ii,jj,istep,jstep,piece,color,mod;                                          //~3407I~//~3408R~//~3409M~
     //*****************************************                   //~3407I~//~3409M~
     	ii=iTo; jj=jTo;                                            //~3407I~//~3409M~
        jstep=Pcolor*2;     //back position                        //~3408I~//~3409M~
        jj+=jstep;                                                 //~3407I~//~3409M~
        mod=SB_LEFT;                                               //~3408I~//~3409M~
        istep=-Pcolor;                                              //~3408I~//~3409M~
        for (int kk=0;kk<2;kk++)                                   //~3408I~//~3409M~
        {                                                          //~3408I~//~3409M~
        	ii=iTo+istep;                                          //~3408I~//~3409M~
            jj=jTo+jstep;                                           //~3408I~//~3409M~
            if (ii>=0 && ii<S && jj>=0 && jj<S)                    //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
                piece=fmtPos.piece(ii,jj);                            //~3408R~//~3409M~
                color=fmtPos.color(ii,jj);                            //~3408R~//~3409M~
                if (color==Pcolor && piece==pieceFrom)             //~3408I~//~3409M~
                {                                                  //~3408I~//~3409M~
                    if (Pmode==0                                   //~3408I~//~3409M~
					||  (mod&Pmode)==Pmode                         //~3408I~//~3409M~
                    )                                              //~3408I~//~3409M~
                    {                                              //~3408I~//~3409M~
                		drop=false;                                //~3408I~//~3409M~
        				iFrom=ii; jFrom=jj;                        //~3408I~//~3409M~
                        break;                                     //~3408I~//~3409M~
                    }                                              //~3408I~//~3409M~
                }                                                  //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
        	mod=SB_RIGHT;                                          //~3408I~//~3409M~
            istep=Pcolor;                                           //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        pieceDrop=drop;                                            //~3407I~//~3409M~
        return rc;                                                 //~3407I~//~3409M~
	}                                                              //~3407I~//~3409M~
    //**************************************************************************//~3407I~//~3409M~
    private static final int[] BACKSTEP_SILVER_BLACK={-1,-1/*LD*/,  1,-1/*RD*/,  -1, 1/*LU*/, 0, 1/*VU*/,   1, 1/*RU*/ };//~3407I~//~3408R~//~3409M~
    private static final int[] BACKSTEP_SILVER={SB_LEFT|SB_DOWN,SB_RIGHT|SB_DOWN,SB_LEFT|SB_UP,SB_VERTICAL|SB_UP,SB_RIGHT|SB_UP};//~3407I~//~3408R~//~3409M~
	private boolean getMoveFrom_Silver(int Pmode,int Pcolor)//~3407I~//~3408R~//~3409I~
	{                                                              //~3407I~//~3409M~
         int[] step;                                               //~3407I~//~3409M~
         int[] mod;                                                //~3409M~
         boolean rc=true;//~3407I~                                 //~3409M~
     //*****************************************                   //~3407I~//~3409M~
     	step=BACKSTEP_SILVER_BLACK;                                //~3407I~//~3409M~
     	mod=BACKSTEP_SILVER;                                       //~3407I~//~3409M~
		rc=getMoveFrom_SG(Pmode,Pcolor,step,mod);               //~3408I~//~3409I~
        return rc;                                                 //~3407I~//~3409M~
    }                                                              //~3407I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
    private static final int[] BACKSTEP_GOLD_BLACK={-1, 1/*LU*/,  0, 1/*VU*/,  1, 1/*RU*/,  -1, 0/*HL*/,  1, 0/*HR*/,  0, -1/*VD*/ };//~3407I~//~3408M~//~3409M~
    private static final int[] BACKSTEP_GOLD={SB_LEFT|SB_UP,SB_VERTICAL|SB_UP,SB_RIGHT|SB_UP,SB_HORIZONTAL|SB_LEFT,SB_HORIZONTAL|SB_RIGHT,SB_VERTICAL|SB_DOWN};//~3407I~//~3408M~//~3409M~
	private boolean getMoveFrom_Gold(int Pmode,int Pcolor)      //~3408R~//~3409I~
	{                                                              //~3408I~//~3409M~
         int[] step;                                               //~3408I~//~3409M~
         int[] mod;                                                //~3409M~
         boolean rc;//~3408I~                                      //~3409M~
     //*****************************************                   //~3408I~//~3409M~
     	step=BACKSTEP_GOLD_BLACK;                                  //~3408I~//~3409M~
     	mod=BACKSTEP_GOLD;                                         //~3408I~//~3409M~
		rc=getMoveFrom_SG(Pmode,Pcolor,step,mod);               //~3408I~//~3409I~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_PPawn(int Pmode,int Pcolor)     //~3408R~//~3409I~
	{                                                              //~3408I~//~3409M~
        return getMoveFrom_Gold(Pmode,Pcolor);                      //~3408I~//~3409I~
	}                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_PLance(int Pmode,int Pcolor)    //~3408R~//~3409I~
	{                                                              //~3408I~//~3409M~
        return getMoveFrom_Gold(Pmode,Pcolor);                      //~3408I~//~3409I~
	}                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_PKnight(int Pmode,int Pcolor)   //~3408R~//~3409I~
	{                                                              //~3408I~//~3409M~
        return getMoveFrom_Gold(Pmode,Pcolor);                      //~3408I~//~3409I~
	}                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_PSilver(int Pmode,int Pcolor)   //~3408R~//~3409I~
	{                                                              //~3408I~//~3409M~
        return getMoveFrom_Gold(Pmode,Pcolor);                      //~3408I~//~3409I~
	}                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_SG(int Pmode,int Pcolor,int[] Pstep,int[] Pmod)//~3408I~//~3409I~
	{                                                              //~3408I~//~3409M~
         boolean rc=true,drop=true;                                //~3408I~//~3409M~
         int ii,jj,istep,jstep,sz,color,piece;                  //~3408I~//~3409M~
         int[] step;                                               //~3408I~//~3409M~
         int[] mod;                                                //~3408I~//~3409M~
     //*****************************************                   //~3408I~//~3409M~
     	step=Pstep;                                                //~3408I~//~3409M~
     	mod=Pmod;                                                 //~3408I~//~3409M~
        sz=step.length/2;                                          //~3408I~//~3409M~
    	for (int kk=0;kk<sz;kk++)                                  //~3408I~//~3409M~
        {                                                          //~3408I~//~3409M~
    		istep=step[kk*2]*Pcolor;                               //~3408I~//~3409M~
    		jstep=step[kk*2+1]*Pcolor;                             //~3408I~//~3409M~
            ii=iTo+istep;                                          //~3408I~//~3409M~
            jj=jTo+jstep;                                          //~3408I~//~3409M~
            if (ii>=0 && ii<S && jj>=0 && jj<S)                    //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
                color=fmtPos.color(ii,jj);                            //~3408R~//~3409M~
                piece=fmtPos.piece(ii,jj);                            //~3408R~//~3409M~
                if (color==Pcolor && piece==pieceFrom)              //~3408I~//~3409M~
                {                                                  //~3408I~//~3409M~
                    if (Pmode==0                                   //~3408I~//~3409M~
					||  (mod[kk]&Pmode)==Pmode                     //~3408I~//~3409M~
                    )                                              //~3408I~//~3409M~
                    {                                              //~3408I~//~3409M~
                    	drop=false;                                //~3408I~//~3409M~
                    	iFrom=ii;                                  //~3408I~//~3409M~
                    	jFrom=jj;                                  //~3408I~//~3409M~
                        break;                                     //~3408I~//~3409M~
                    }                                              //~3408I~//~3409M~
                }                                                  //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        pieceDrop=drop;                                            //~3408I~//~3409M~
        return rc;                                                 //~3408I~//~3409M~
	}                                                              //~3407I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_Bishop(int Pmode,int Pcolor)//~3408I~//~3409I~
	{                                                              //~3408I~//~3409M~
         boolean rc=true;                                          //~3408I~//~3409M~
         int mod;                                                  //~3408I~//~3409M~
     //*****************************************                   //~3408I~//~3409M~
        mod=SB_LEFT|SB_DOWN;                                       //~3408I~//~3409M~
		getMoveFrom_BishopSub(Pmode,Pcolor,-Pcolor/*istep*/,-Pcolor/*jstep*/,mod);//~3408I~//~3409I~
        if (!pieceDrop)                                            //~3408R~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        mod=SB_LEFT|SB_UP;                                         //~3408I~//~3409M~
		getMoveFrom_BishopSub(Pmode,Pcolor,-Pcolor/*istep*/,+Pcolor/*jstep*/,mod);//~3408I~//~3409I~
        if (!pieceDrop)                                            //~3408R~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        mod=SB_RIGHT|SB_DOWN;                                      //~3408I~//~3409M~
		getMoveFrom_BishopSub(Pmode,Pcolor,Pcolor/*istep*/,-Pcolor/*jstep*/,mod);//~3408I~//~3409I~
        if (!pieceDrop)                                            //~3408R~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        mod=SB_RIGHT|SB_UP;                                        //~3408I~//~3409M~
		getMoveFrom_BishopSub(Pmode,Pcolor,Pcolor/*istep*/,Pcolor/*jstep*/,mod);//~3408I~//~3409I~
        if (Dump.Y) Dump.println("From_Bishop rc="+rc+",drop="+pieceDrop);//~3409I~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_BishopSub(int Pmode,int Pcolor,int Pistep,int Pjstep,int Pchkmode)//~3408I~//~3409I~
	{                                                              //~3408I~//~3409M~
         boolean rc=true,drop=true;                                //~3408I~//~3409M~
         int ii,jj;                                                //~3408I~//~3409M~
    //**********************                                       //~3408I~//~3409M~
        for (ii=iTo+Pistep,jj=jTo+Pjstep;ii>=0 && ii<S && jj>=0 && jj<S;ii+=Pistep,jj+=Pjstep)//~3408I~//~3409M~
        {                                                          //~3408I~//~3409M~
            if (Dump.Y) Dump.println("From_BishopSub ii="+ii+",jj="+jj+",color="+fmtPos.color(ii,jj)+",piece="+fmtPos.piece(ii,jj)+",piecefrom="+pieceFrom+",Pcolor="+Pcolor);//~3408R~//~3409I~
            int color=fmtPos.color(ii,jj);                            //~3408R~//~3409M~
            if (color==0)                                          //~3408I~//~3409M~
                continue;                                          //~3408I~//~3409M~
            if (color!=Pcolor)                                     //~3408I~//~3409M~
                break;                                             //~3408I~//~3409M~
            int piece=fmtPos.piece(ii,jj);                            //~3408R~//~3409M~
            if (piece!=pieceFrom)                                  //~3408I~//~3409M~
                break;                                             //~3408I~//~3409M~
            if (Pmode==0                                           //~3408I~//~3409M~
            ||  (Pchkmode&Pmode)==Pmode)                           //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
                drop=false;                                        //~3408I~//~3409M~
                iFrom=ii;                                          //~3408I~//~3409M~
                jFrom=jj;                                          //~3408I~//~3409M~
                break;                                             //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        pieceDrop=drop;                                            //~3408I~//~3409M~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_PBishop(int Pmode,int Pcolor)//~3408I~//~3409I~
	{                                                              //~3408I~//~3409M~
    	boolean rc=true;                                           //~3408I~//~3409M~
    //**********************                                       //~3408I~//~3409M~
		getMoveFrom_Bishop(Pmode,Pcolor);                       //~3408I~//~3409I~
        if (pieceDrop)                                             //~3408I~//~3409M~
			getMoveFrom_PBR(Pmode,Pcolor);                      //~3408I~//~3409I~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_Rook(int Pmode,int Pcolor)//~3408I~//~3409I~
	{                                                              //~3408I~//~3409M~
         boolean rc=true;                                          //~3408I~//~3409M~
         int mod;                                           //~3408I~//~3409M~
     //*****************************************                   //~3408I~//~3409M~
        mod=SB_LEFT|SB_HORIZONTAL;                                 //~3408R~//~3409M~
		getMoveFrom_RookSub(Pmode,Pcolor,-Pcolor/*istep*/,0/*jstep*/,mod);//~3408I~//~3409I~
        if (!pieceDrop)                                            //~3408R~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        mod=SB_RIGHT|SB_HORIZONTAL;                                //~3408R~//~3409M~
		getMoveFrom_RookSub(Pmode,Pcolor,Pcolor/*istep*/,0/*jstep*/,mod);//~3408I~//~3409I~
        if (!pieceDrop)                                            //~3408R~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        mod=SB_UP|SB_VERTICAL;                                     //~3408R~//~3409M~
		getMoveFrom_RookSub(Pmode,Pcolor,0/*istep*/,Pcolor/*jstep*/,mod);//~3408I~//~3409I~
        if (!pieceDrop)                                            //~3408R~//~3409M~
        	return rc;                                             //~3408I~//~3409M~
        mod=SB_DOWN|SB_VERTICAL;                                   //~3408R~//~3409M~
		getMoveFrom_RookSub(Pmode,Pcolor,0/*istep*/,-Pcolor/*jstep*/,mod);//~3408I~//~3409I~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //***************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_RookSub(int Pmode,int Pcolor,int Pistep,int Pjstep,int Pchkmode)//~3408I~//~3409I~
    {                                                              //~3408I~//~3409M~
         boolean rc=true,drop=true;                                //~3408I~//~3409M~
     //*****************************************                   //~3408I~//~3409M~
        for (int ii=iTo+Pistep,jj=jTo+Pjstep;ii>=0 && ii<S && jj>=0 && jj<S;ii+=Pistep,jj+=Pjstep)//~3408R~//~3409M~
        {                                                          //~3408I~//~3409M~
            if (Dump.Y) Dump.println("From_RookSub ii="+ii+",jj="+jj+",color="+fmtPos.color(ii,jj)+",piece="+fmtPos.piece(ii,jj)+",piecefrom="+pieceFrom+",Pcolor="+Pcolor);//~3408R~//~3409I~
        	int color=fmtPos.color(ii,jj);                            //~3408R~//~3409M~
            if (color==0)                                          //~3408I~//~3409M~
                continue;                                          //~3408I~//~3409M~
            if (color!=Pcolor)                                     //~3408I~//~3409M~
            	break;                                             //~3408I~//~3409M~
        	int piece=fmtPos.piece(ii,jj);                            //~3408R~//~3409M~
            if (piece!=pieceFrom)                                     //~3408I~//~3409M~
            	break;                                             //~3408I~//~3409M~
            if (Pmode==0                                           //~3408I~//~3409M~
            ||  (Pchkmode&Pmode)==Pmode)                          //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
                drop=false;                                        //~3408I~//~3409M~
                iFrom=ii;                                          //~3408I~//~3409M~
                jFrom=jj;                                          //~3408I~//~3409M~
                break;                                             //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
        }                                                          //~3409M~
        pieceDrop=drop;//~3408I~                                   //~3409M~
        return rc;                                                 //~3409M~
    }                                                              //~3408I~//~3409M~
    //**************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_PRook(int Pmode,int Pcolor)//~3408I~//~3409I~
	{                                                              //~3408I~//~3409M~
    	boolean rc=true;                                           //~3408I~//~3409M~
    //**********************                                       //~3408I~//~3409M~
		getMoveFrom_Rook(Pmode,Pcolor);                         //~3408I~//~3409I~
        if (pieceDrop)                                             //~3408I~//~3409M~
			getMoveFrom_PBR(Pmode,Pcolor);                      //~3408I~//~3409I~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //***************************************************************************//~3408I~//~3409M~
    //*promoted Bishop/Rook like King move                         //~3408I~//~3409M~
    //***************************************************************************//~3408I~//~3409M~
    private static final int[] BACKSTEP_PBR={                      //~3408I~//~3409M~
				/*-1, -1/0/1*/ SB_LEFT|SB_DOWN,SB_LEFT|SB_HORIZONTAL,SB_LEFT|SB_UP,//~3408I~//~3409M~
				/* 0, -1/  1*/ SB_VERTICAL|SB_DOWN,SB_VERTICAL|SB_UP,//~3408I~//~3409M~
				/* 1, -1/0/1*/ SB_RIGHT|SB_DOWN,SB_RIGHT|SB_HORIZONTAL,SB_RIGHT|SB_UP};//~3408I~//~3409M~
	private boolean getMoveFrom_PBR(int Pmode,int Pcolor)//~3408R~ //~3409I~
    {                                                              //~3408I~//~3409M~
    	int ii,jj,kk=0;                                            //~3408R~//~3409M~
         boolean rc=true,drop=true;                                //~3408I~//~3409M~
    //**********************                                       //~3408I~//~3409M~
    	for (int iii=-Pcolor,kki=0;kki<3;iii+=Pcolor,kki++)              //~3408R~//~3409M~
    	{                                                          //~3408I~//~3409M~
    		for (int jjj=-Pcolor,kkj=0;kkj<3;jjj+=Pcolor,kkj++)          //~3408R~//~3409M~
            {                                                      //~3408I~//~3409M~
            	ii=iTo+iii;                                        //~3408I~//~3409M~
                jj=jTo+jjj;                                        //~3408I~//~3409M~
				if (Dump.Y) Dump.println("_PBR ii="+ii+",jj="+jj+",Pcolor="+Pcolor);//~3409I~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~3408I~//~3409M~
	                if (iii!=0 || jjj!=0)                          //~3408I~//~3409M~
                    {                                              //~3408I~//~3409M~
                        int color=fmtPos.color(ii,jj);                //~3408R~//~3409M~
                        int piece=fmtPos.piece(ii,jj);                //~3408R~//~3409M~
				        if (Dump.Y) Dump.println("_PBR ii="+ii+",jj="+jj+",piece="+piece+",color="+color+",pieceFrom="+pieceFrom);//~3409I~
		                if (color==Pcolor && piece==pieceFrom)      //~3408I~//~3409M~
                		{                                          //~3408I~//~3409M~
				            if (Pmode==0                           //~3408I~//~3409M~
				            ||  (BACKSTEP_PBR[kk]&Pmode)==Pmode)   //~3408I~//~3409M~
                            {                                      //~3408I~//~3409M~
                                drop=false;                        //~3408R~//~3409M~
                                iFrom=ii;                          //~3408R~//~3409M~
                                jFrom=jj;                          //~3408R~//~3409M~
                                break;                             //~3408R~//~3409M~
                            }                                      //~3408I~//~3409M~
                    	}                                          //~3408I~//~3409M~
                        kk++;                                      //~3408I~//~3409M~
                	}                                              //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
            if (!drop)                                             //~3408I~//~3409M~
            	break;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        pieceDrop=drop;                                            //~3408I~//~3409M~
        if (Dump.Y) Dump.println("_PBR rc="+rc+",drop="+pieceDrop+",mod="+Pmode);//~3409I~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
    //***************************************************************************//~3408I~//~3409M~
	private boolean getMoveFrom_King(int Pmode,int Pcolor)//~3408I~//~3409I~
    {                                                              //~3408I~//~3409M~
    	int ii,jj;                                                 //~3408I~//~3409M~
         boolean rc=true,drop=true;                                //~3408I~//~3409M~
    //**********************                                       //~3408I~//~3409M~
    	for (int iii=-1;iii<=1;iii++)                              //~3408I~//~3409M~
    	{                                                          //~3408I~//~3409M~
    		for (int jjj=-1;jjj<=1;jjj++)                          //~3408I~//~3409M~
            {                                                      //~3408I~//~3409M~
            	ii=iTo+iii;                                        //~3408I~//~3409M~
                jj=jTo+jjj;                                        //~3408I~//~3409M~
                if (ii>=0 && ii<S && jj>=0 && jj<S)                //~3408I~//~3409M~
	                if (iii!=0 || jjj!=0)                          //~3408I~//~3409M~
                    {                                              //~3408I~//~3409M~
                        int color=fmtPos.color(ii,jj);                //~3408R~//~3409M~
                        int piece=fmtPos.piece(ii,jj);                //~3408R~//~3409M~
		                if (color==Pcolor && piece==pieceFrom)     //~3408I~//~3409M~
                		{                                          //~3408I~//~3409M~
                    		drop=false;                            //~3408I~//~3409M~
                    		iFrom=ii;                              //~3408I~//~3409M~
                    		jFrom=jj;                              //~3408I~//~3409M~
                        	break;                                 //~3408I~//~3409M~
                    	}                                          //~3408I~//~3409M~
                	}                                              //~3408I~//~3409M~
            }                                                      //~3408I~//~3409M~
            if (!drop)                                             //~3408I~//~3409M~
            	break;                                             //~3408I~//~3409M~
        }                                                          //~3408I~//~3409M~
        pieceDrop=drop;                                            //~3408I~//~3409M~
        return rc;                                                 //~3408I~//~3409M~
    }                                                              //~3408I~//~3409M~
	//**************************************************************** //~1A30I~//~3406I~//~3409M~//~3411M~
	protected void putInitialPiece(int Phandicap)              //~3406I~//~3408R~//~3409M~//~3411M~
    {                                                              //~1A30I~//~3406I~//~3409M~//~3411M~
    	int piece;                                                 //~1A30I~//~3406I~//~3409M~//~3411M~
    //***********************                                      //~1A30I~//~3406I~//~3409M~//~3411M~
		for (int jj=0;jj<Field.INITIAL_STAFF_LINE;jj++)                  //~1A30I~//~3406I~//~3409M~//~3411M~//~3412R~
            for (int ii=0;ii<S;ii++)                         //~1A30I~//~3406I~//~3407R~//~3409M~//~3411M~
            {                                                      //~1A30I~//~3406I~//~3409M~//~3411M~
                piece=Field.INITIAL_STAFF[jj][ii];                //~1A30I~//~3406I~//~3409M~//~3411M~//~3412R~
                if (piece==0)                                  //~1A30I~//~3406I~//~3409M~//~3411M~
                	continue;                                      //~1A30I~//~3406I~//~3409M~//~3411M~
              	if (!HandicapQuestion.isHandicapPiece(Phandicap,jj,ii))//~1A30I~//~3406I~//~3409M~//~3411M~//~3412R~
					fmtPos.setPiece(S-ii-1,jj,-1/*color*/,piece);//~3406I~//~3408R~//~3409M~//~3411M~
//              fmtPos.setPiece(ii,S-jj-1,1/*color*/,piece);        //~1A30I~//~3406I~//~3408R~//~3409M~//~3411M~//~3412R~
                putInitialPiece1(ii,S-jj-1,1/*color*/,piece);      //~3412I~
            }                                                      //~1A30I~//~3406I~//~3409M~//~3411M~
		if (Dump.Y) showBoard();                                   //~3407I~//~3409M~//~3411M~
    }                                                              //~1A30I~//~3406I~//~3409M~//~3411M~
    //*************************************************************//~3407I~*****//~3412I~
	protected void putInitialPiece1(int Pi,int Pj,int Pcolor,int Ppiece)//~3412I~
    {                                                              //~3412I~
    	fmtPos.setPiece(Pi,Pj,Pcolor,Ppiece);                      //~3412I~
        fmtNotes.pieces[Pi][Pj]=Ppiece;			//initial piece arrange//~3412I~
        if (Dump.Y) Dump.println("putInitialPiece1 i="+Pi+",j="+Pj+",color="+Pcolor+",piece="+Ppiece);//~3414I~
        int piece=Field.nonPromoted(Ppiece);                           //~3412I~
        if (piece>=1 && piece<=PIECE_ROOK)                         //~3412I~
		fmtUsedPieceCtr[piece-1]++;                                //~3412I~
        swInitPiece=true;                                          //~3408M~//~3409M~//~3411M~//~3412M~
    }                                                              //~3412I~
    //*************************************************************//~3407I~*****//~3408R~//~3409M~
	protected void showBoard()                              //~3407I~//~3408R~//~3409I~
    {                                                              //~3407I~//~3409M~
        Dump.println("      9  8  7  6  5  4  3  2  1");                   //~3411I~
        Dump.println("-------------------------------");
        for (int jj=0;jj<S;jj++)                                   //~3407R~//~3409M~
        {                                                          //~3407I~//~3409M~
            Dump.print(Character.valueOf((char)('a'+jj)).toString()+"="+(jj+1)+"=");                                    //~3407I~//~3409M~//~3411R~
    		for (int ii=0;ii<S;ii++)                               //~3407R~//~3409M~
            {                                                      //~3407I~//~3409M~
            	int piece=fmtPos.piece(ii,jj);                  //~3407R~//~3408R~//~3409M~
                if (piece>10)                                      //~3407I~//~3409M~
            		Dump.print(" "+piece);                         //~3407I~//~3409M~
                else                                               //~3407I~//~3409M~
            		Dump.print("  "+piece);                        //~3407I~//~3409M~
            }                                                      //~3407I~//~3409M~
            Dump.println("");                                      //~3407I~//~3409M~
        }                                                          //~3407I~//~3409M~
        showTray();                                                //~3411I~
    }                                                              //~3407I~//~3409M~
    //*************************************************************//~3407I~*****//~3411I~
	protected void showTray()                                      //~3411I~
    {                                                              //~3411I~
    	for (int jj=0;jj<2;jj++)                                   //~3411I~
        {                                                          //~3411I~
    		for (int ii=0;ii<MAX_PIECE_TYPE_CAPTURE;ii++)          //~3411I~
            	Dump.print(" "+fmtTray[jj][ii]);                    //~3411I~
            Dump.println("");                                      //~3411I~
        }                                                          //~3411I~
    }                                                              //~3411I~
	//*******************************************************************************************//~3411I~//~3412R~
    private boolean isLangJP()                                     //~3411I~
    {                                                              //~3411I~
        Locale locale=Locale.getDefault();                         //~3411I~
        String language=locale.getLanguage();   //ja(Locale.JAPANESE) or ja_JP(Locale.JAPAN)//~3411I~
        boolean rc=language.substring(0,2).equals(Locale.JAPANESE.getLanguage());//~3411I~
        if (Dump.Y) Dump.println("isLangJP="+rc);                  //~3411I~
        return rc;                                                 //~3411I~
    }                                                              //~3411I~
	//*******************************************************************************************//~3409I~//~3412R~
    protected boolean setTreeEntry(String Pline){return true;}//override this  //~3409I~
	//*******************************************************************************************//~3408R~//~3412M~
    protected void appendHeaderComment(String Pline)                      //~1A4vI~
    {                                                              //~1A4vI~
    	if (headerComment==null)                                   //~1A4vI~
        {                                                          //~1A4vI~
        	headerComment=new StringBuffer(Pline);                 //~1A4vI~
            headerCommentCtr=1;                                    //~1A4vI~
        }                                                          //~1A4vI~
        else                                                       //~1A4vI~
        {                                                          //~1A4vI~
        	if (headerCommentCtr<headerCommentCtrMax)              //~1A4vI~
            {                                                      //~1A4vI~
        		headerComment.append("\n"+Pline);                  //~1A4vR~
            	headerCommentCtr++;                               //~1A4vR~
            }                                                      //~1A4vI~
        }                                                          //~1A4vI~
	}                                                              //~1A4vI~
	//*******************************************************************************************//+1Ah0I~
	//*******************************************************************************************//~3408I~//~3412M~
	//*******************************************************************************************//~3408I~//~3412M~
	//*******************************************************************************************//~3408I~//~3412M~
///*@@@@ not Test                                                  //~3412R~//~3415R~//+1Ah0R~
//    public static void main (String args[])                        //~3406I~//~3408R~//+1Ah0R~
//    {                                                              //~3406I~//+1Ah0R~
//        swTest=true;                                               //~3412I~//+1Ah0R~
//        Dump.open(null);    //System.out                           //~3412I~//+1Ah0R~
//        Dump.println("main start");                                //~3412I~//+1Ah0R~
//        NotesFmt.createNotes(args[0]);                                 //~3408I~//~3409R~//+1Ah0R~
//    }                                                              //~3406I~//+1Ah0R~
//class Notes                                                        //~3407I~//~3414R~//+1Ah0R~
//{                                                                  //~3407I~//~3414R~//+1Ah0R~
//    //****************************************                     //~3407I~//~3414R~//+1Ah0R~
//    ListClass notesList;                                           //~3407I~//~3414R~//+1Ah0R~
//    public String name;                                            //~3407I~//~3414R~//+1Ah0R~
//    public String blackName,whiteName;                             //~3412I~//~3414R~//+1Ah0R~
//    public int color;   //current color P.color() at save          //~3407I~//~3414R~//+1Ah0R~
//    public int yourcolor;                                          //~3407I~//~3414R~//+1Ah0R~
//    public int seq;         //snapshot seqNo                       //~3407I~//~3414R~//+1Ah0R~
//    public int moves,moves0,gameoptions;       //moves in the snapshot//~3407I~//~3414R~//+1Ah0R~
//    public int handicap;                                           //~3407I~//~3414R~//+1Ah0R~
//    public int coordType;    //movedescription by LangJP           //~3407I~//~3414R~//+1Ah0R~
//    public boolean changeCoord;    //at load coord lang change required//~3407I~//~3414R~//+1Ah0R~
//    public int white,black,whiteExtra,blackExtra,extraTime,totalTime;//~3407I~//~3414R~//+1Ah0R~
//    private int[][] tray=new int[2][MAX_PIECE_TYPE_CAPTURE];       //~3412I~//~3414R~//+1Ah0R~
//    private int[][] pieces=new int[AG.BOARDSIZE_SHOGI][AG.BOARDSIZE_SHOGI];//~3412I~//~3414R~//+1Ah0R~
//    private NotesTree notesTree;                                   //~3412I~//~3414R~//+1Ah0R~
//    ListElement listElement;                                       //~3407I~//~3414R~//+1Ah0R~
//    //****************************************                     //~3407I~//~3414R~//+1Ah0R~
//    public Notes(String Pname,int Pyourcolor,int Pcolor)           //~3407I~//~3414R~//+1Ah0R~
//    {                                                              //~3407I~//~3414R~//+1Ah0R~
////      if (Pname==null || Pname.equals(""))                       //~3407I~//~3414R~//+1Ah0R~
////          name=getDefaultName();                                 //~3407I~//~3414R~//+1Ah0R~
////        else                                                       //~3407I~//~3414R~//+1Ah0R~
//            name=Pname;                                            //~3407I~//~3414R~//+1Ah0R~
//        yourcolor=Pyourcolor;                                      //~3407I~//~3414R~//+1Ah0R~
//        color=Pcolor;                                              //~3407I~//~3414R~//+1Ah0R~
////      coordType=getCoordType();                                  //~3407I~//~3414R~//+1Ah0R~
//        //****************************************                 //~3412I~//~3414R~//+1Ah0R~
//    }                                                              //~3407I~//~3414R~//+1Ah0R~
//    public void setTree(NotesTree Ptree)                       //~3412I~//~3414R~//+1Ah0R~
//    {                                                          //~3412I~//~3414R~//+1Ah0R~
//        notesTree=Ptree;                                       //~3412I~//~3414R~//+1Ah0R~
//    }                                                          //~3412I~//~3414R~//+1Ah0R~
//}//Notes                                                           //~3407R~//~3414R~//+1Ah0R~
//static class HandicapQuestion                                      //~3407I~//~3412R~//+1Ah0R~
//{                                                                  //~3407I~//~3412R~//+1Ah0R~
//    public int Handicap;                                           //~3407I~//~3412R~//+1Ah0R~
//    //************************************************************     //~3407I~//~3412R~//+1Ah0R~
//    public static boolean isHandicapPiece(int Phandicap,int Prow,int Pcol)//~3407I~//~3412R~//+1Ah0R~
//    {                                                              //~3407I~//~3412R~//+1Ah0R~
//        boolean rc=false;                                          //~3407I~//~3412R~//+1Ah0R~
//        if (Prow==0)                                               //~3407I~//~3412R~//+1Ah0R~
//        {                                                          //~3407I~//~3412R~//+1Ah0R~
//            switch(Pcol)                                           //~3407I~//~3412R~//+1Ah0R~
//            {                                                      //~3407I~//~3412R~//+1Ah0R~
//            case 0://lance1 pos                                    //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_LANCE1)!=0;                     //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 8://lance2 pos                                    //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_LANCE2)!=0;                     //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 1://knight1 pos                                   //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_KNIGHT1)!=0;                    //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 7://knight2 pos                                   //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_KNIGHT2)!=0;                    //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 2://silver1 pos                                   //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_SILVER1)!=0;                    //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 6://silver2 pos                                   //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_SILVER2)!=0;                    //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 3://gold1 pos                                     //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_GOLD1)!=0;                      //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 5://gold2 pos                                     //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_GOLD2)!=0;                      //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            }                                                      //~3407I~//~3412R~//+1Ah0R~
//        }                                                          //~3407I~//~3412R~//+1Ah0R~
//        else                                                       //~3407I~//~3412R~//+1Ah0R~
//        if (Prow==1)                                               //~3407I~//~3412R~//+1Ah0R~
//        {                                                          //~3407I~//~3412R~//+1Ah0R~
//            switch(Pcol)                                           //~3407I~//~3412R~//+1Ah0R~
//            {                                                      //~3407I~//~3412R~//+1Ah0R~
//            case 1://bishop pos                                    //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_BISHOP)!=0;                     //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            case 7://lance2 pos                                    //~3407I~//~3412R~//+1Ah0R~
//                rc=(Phandicap & HC_ROOK)!=0;                       //~3407I~//~3412R~//+1Ah0R~
//                break;                                             //~3407I~//~3412R~//+1Ah0R~
//            }                                                      //~3407I~//~3412R~//+1Ah0R~
//        }                                                          //~3407I~//~3412R~//+1Ah0R~
//        return rc;                                                 //~3407I~//~3412R~//+1Ah0R~
//    }                                                              //~3407I~//~3412R~//+1Ah0R~
//}//Handicap                                                        //~3407I~//~3412R~//+1Ah0R~
//@@@@*/                                                           //~3412R~//~3415R~//+1Ah0R~
}//NotesFmt                                                        //~3407R~
