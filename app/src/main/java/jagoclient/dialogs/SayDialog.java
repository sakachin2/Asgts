//*CID://+v101R~:                             update#=   37;       //+v101R~
//*********************************************************************//~v101I~
//1A6w 2015/02/20 (Bug)SayDialog miss multiline msg(show only top);(thread timeing) So send at onece//~1A6wI~
//1A6v 2015/02/18 (Bug)Coversation dialog at bottom is hidden by IME dialog//~1A6vI~
//                Relative layout fix footer to bottom on sceeen;TextView pushout bottom buttons is hidden by IME dialog//~1A6vI~
//                change to use ListView                           //~1A6vI~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//*********************************************************************//~v101I~
//package jagoclient.igs;                                          //~@@@2R~
package jagoclient.dialogs;                                         //~@@@2I~

import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.CloseDialog;
import jagoclient.gui.FormTextField;
//import jagoclient.gui.HistoryTextField;
//import jagoclient.gui.MyLabel;
//import jagoclient.gui.MyPanel;
//import jagoclient.gui.Panel3D;

//import java.awt.BorderLayout;
//import java.awt.Graphics;
//import java.awt.Panel;
//import java.awt.TextArea;
//import java.awt.TextField;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.util.LinkedList;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import com.Asgts.AG;                                               //~1A6wR~
import com.Asgts.AView;                                            //~1A6wR~
import com.Asgts.R;                                                //~1A6wR~
import com.Asgts.awt.Frame;                                        //~1A6wR~
//import com.Asgts.awt.BorderLayout;                                  //~@@@2R~//~1A6wR~
import com.Asgts.awt.Color;                                        //~1A6wR~
import jagoclient.partner.partner.MsgThread;
//+1A6vI~
import com.Asgts.rene.viewer.Viewer;                                //~1A6vI~//~1A6wR~


//public class SayDialog extends CloseFrame                        //~@@@2R~
public class SayDialog extends CloseDialog                         //~@@@2I~
//    implements CloseListener,KeyListener                         //~@@@2R~
{	PrintWriter Out;
	FormTextField SendField;                                    //~@@@2I~
    private static final int receiveMsgBGColor=new Color(0xff,0xff,0x00).getRGB();//~v101R~
//	TextField Answer;
//  TextArea T;                                                    //~1A6vR~
    Viewer   T;                                                    //~1A6vI~
	private Frame cf;
    private static LinkedList<String> msgList=new LinkedList<String>();//~@@@2I~
    private LinkedList<String> multiLine=new LinkedList<String>(); //~@@@2I~
    private static final int MAX_STACKSIZE=100;                    //~@@@2I~
    private static final int LINES_SENDFIELD=4;                    //~@@@2I~
    private static final String LINE_SPLITTER="\t";                //~1A6wI~
//    SayDistributor SD;                                           //~@@@2R~
//    ConnectionFrame CF;                                          //~@@@2R~
//    public SayDialog (ConnectionFrame cf, SayDistributor sd, String m,//~@@@2R~
//        PrintWriter out)                                         //~@@@2R~
    public SayDialog (Frame f)                                     //~@@@2I~
//    {   super(Global.resourceString("Say"));                     //~@@@2R~
    {                                                              //~@@@2I~
    	super(f,AG.resource.getString(R.string.Title_SayDialog)+" vs "//~@@@2R~
    		  +(AG.PartnerName!=null                               //~@@@2R~
					?AG.PartnerName                                //~@@@2I~
					:(AG.RemoteStatus==AG.RS_BTCONNECTED           //~@@@2I~
						?(AG.RemoteDeviceName!=null?AG.RemoteDeviceName:"?")//~@@@2I~
                        :(AG.RemoteStatus==AG.RS_IPCONNECTED       //~@@@2I~
							?(AG.RemoteInetAddress!=null?AG.RemoteInetAddress:"?")//~@@@2I~
                            :"?"                                   //~@@@2I~
                         )                                         //~@@@2I~
                     )                                             //~@@@2I~
               ),                                                   //~@@@2I~
    		R.layout.saydialog,false,false);
    		cf=f;//~@@@2R~
//        cf.addCloseListener(this);                               //~@@@2R~
//        SD=sd; CF=cf;                                            //~@@@2R~
//        add("North",new MyLabel(Global.resourceString("Opponent_said_")));//~@@@2R~
//        Panel pm=new MyPanel();                                  //~@@@2R~
//        pm.setLayout(new BorderLayout());                        //~@@@2R~
//        pm.add("Center",T=new TextArea());                       //~@@@2R~
//        T.setBackground(Global.gray.brighter());                 //~@@@2R~
//        T.setFont(Global.Monospaced);                            //~@@@2R~
//        T.setEditable(false);                                    //~@@@2R~
//        pm.add("South",Answer=new HistoryTextField(this,"Answer",40));//~@@@2R~
//        add("Center",pm);                                        //~@@@2R~
//        Panel p=new MyPanel();                                   //~@@@2R~
//        p.add(new ButtonAction(this,Global.resourceString("Close")));//~@@@2R~
//        p.add(new ButtonAction(this,Global.resourceString("Send_Answer")));//~@@@2R~
//        add("South",new Panel3D(p));                             //~@@@2R~
//        Out=out;                                                 //~@@@2R~
//        SD.MD=this;                                              //~@@@2R~
//        Global.setwindow(this,"say",400,200);                    //~@@@2R~
//        T=new TextArea(R.id.ReceiveMsg);                           //~@@@2I~//~v101R~
//      T=new TextArea(this,R.id.ReceiveMsg);                      //~v101I~//~1A6vR~
        T=new Viewer(this,R.id.ReceiveMsg);                        //~1A6vI~
        T.setBackground(Global.gray.brighter());                   //~@@@2I~
        T.setFont(Global.Monospaced);                              //~@@@2I~
//      T.setEditable(false);                                      //~@@@2I~//~1A6vR~
//        SendField=new FormTextField("",R.id.SendField,LINES_SENDFIELD,-1/*cols*/);//~@@@2R~//~v101R~
        SendField=new FormTextField(this,"",R.id.SendField,LINES_SENDFIELD,-1/*cols*/);//~v101I~
//        setActionListener(SendField);                            //~@@@2R~
//        ButtonAction.init(this,0,R.id.SendIcon);                   //~@@@2R~//~v101R~
//        ButtonAction.init(this,0,R.id.Send);                       //~@@@2I~//~v101R~
//        ButtonAction.init(this,0,R.id.Close);                      //~@@@2I~//~v101R~
//        ButtonAction.init(this,0,R.id.Help);                       //~@@@2I~//~v101R~
        new ButtonAction(this,0,R.id.SendIcon);               //~v101I~
        new ButtonAction(this,0,R.id.Send);                   //~v101I~
        new ButtonAction(this,0,R.id.Close);                  //~v101I~
        new ButtonAction(this,0,R.id.Help);                   //~v101I~
        AG.sayDialog=this;                                         //~@@@2I~
        setDismissActionListener(this/*DoActionListener*/);        //~@@@2I~
        displayInitial();                                          //~@@@2I~
        validate(); show();                                        //~@@@2R~
//        T.setText(m);                                            //~@@@2R~
//        Answer.addKeyListener(this);                             //~@@@2R~
	}
    //******************************************************       //~@@@2I~
	public void doAction (String o)
//  {	Global.notewindow(this,"say");                             //~@@@2R~
	{                                                              //~@@@2I~
        if (o.equals(AG.resource.getString(R.string.Send))         //~@@@2R~
        ||  o.equals(AG.resource.getString(R.string.SendIcon)))    //~@@@2I~
		{                                                          //~@@@2R~
        	try                                                    //~@@@2I~
            {                                                      //~@@@2I~
            	sendMsg();                                         //~@@@2I~
            }                                                      //~@@@2I~
            catch(Exception e)	                                   //~@@@2I~
            {                                                      //~@@@2I~
            	Dump.println(e,"SayDialog:sendMsg");               //~@@@2I~
                AView.showToast(R.string.SayDialogSendException);           //~@@@2I~
            }                                                      //~@@@2I~
		}
        else                                                       //~@@@2I~
        if (o.equals(AG.resource.getString(R.string.Close)))       //~@@@2I~
		{                                                          //~@@@2I~
			setVisible(false); dispose();                          //~@@@2I~
		}                                                          //~@@@2M~
        else                                                       //~@@@2I~
        if (o.equals(AG.resource.getString(R.string.Help)))        //~@@@2I~
		{                                                          //~@@@2I~
//  		new HelpDialog(null,R.string.Help_SayDialog);               //~@@@2I~//+v101R~
    		new HelpDialog(null,R.string.HelpTitle_SayDialog,"SayDialog");//+v101I~
		}                                                          //~@@@2I~
        else                                                       //~@@@2I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))	//modal but no inputWait//~@@@2I~
		{   			//callback from Dialog after currentLaypout restored//~@@@2I~
	        AG.sayDialog=null;                                     //~@@@2I~
		}                                                          //~@@@2I~
		else super.doAction(o);
	}
	//********************************************************************//~@@@2I~
    void sendMsg()                                                 //~@@@2I~
    {                                                              //~@@@2I~
//        sendMultiline();                                         //~@@@2R~
		String msg=SendField.getText().toString();                 //~@@@2I~
		SendField.setText("");                                     //~@@@2I~
        if (msg.equals(""))                                        //~@@@2R~
        	return;                                                //~@@@2I~
        String[] lines=msg.split("\n");                            //~@@@2I~
        String sendmsg="";                                         //~1A6wI~
        for (int ii=0;ii<lines.length;ii++)                        //~@@@2I~
        {                                                          //~@@@2I~
          	String line=lines[ii]+"\n";                            //~@@@2R~
            if (Dump.Y) Dump.println("SayDialog Send msg="+line+",seq="+ii);//~1A6vR~
	        append(line);                                          //~@@@2I~
    		stackMsg(line);                                        //~@@@2R~
//      	MsgThread.enqSendMsg("<"+AG.YourName+">"+line);        //~@@@2R~//~1A6wR~
        	sendmsg+="<"+AG.YourName+">"+lines[ii]+LINE_SPLITTER;  //~1A6wR~
        }                                                          //~@@@2I~
      	MsgThread.enqSendMsg(sendmsg);                             //~1A6wI~
    }                                                              //~@@@2I~
	//********************************************************************//~@@@2I~
    public void receiveMsg(String[] Psa)                           //~@@@2R~
    {                                                              //~@@@2I~
        try                                                        //~@@@2I~
        {                                                          //~@@@2I~
            displayMsg(Psa);                                       //~@@@2R~
        }                                                          //~@@@2I~
        catch(Exception e)                                         //~@@@2I~
        {                                                          //~@@@2I~
            Dump.println(e,"SayDialog:receiveMsg");                //~@@@2I~
        }                                                          //~@@@2I~
    }                                                              //~@@@2I~
	//********************************************************************//~@@@2I~
    public void displayMsg(String[] Psa)                           //~@@@2R~
    {                                                              //~@@@2I~
        if (Psa.length==0)                                         //~@@@2R~
        	return;                                                //~@@@2I~
        for (int ii=0;ii<Psa.length;ii++)                          //~@@@2R~
        {                                                          //~@@@2I~
        	String s=Psa[ii]+"\n";                                 //~@@@2R~
            if (Dump.Y) Dump.println("SayDialog Receive msg="+s+",seq="+ii);//~1A6vI~
//      	append(s);                                             //~@@@2I~//~1A6wR~
			if (Dump.Y) Dump.println("SayDailog displayMsg: line="+Psa[ii]);//~1A6wI~
        	String[] lines=Psa[ii].split(LINE_SPLITTER);           //~1A6wI~
         	for (int jj=0;jj<lines.length;jj++)                    //~1A6wI~
            {                                                      //~1A6wI~
				append(lines[jj]);                                  //~1A6wI~
            }                                                      //~1A6wI~
	    	stackMsg(s);                                           //~@@@2R~
        }                                                          //~@@@2I~
    }                                                              //~@@@2I~
	//********************************************************************//~@@@2I~
    public void displayInitial()                                   //~@@@2I~
    {                                                              //~@@@2I~
    	int size=msgList.size();                                  //~@@@2I~
        for (int ii=0;ii<size;ii++)                              //~@@@2I~
        {                                                          //~@@@2I~
        	append(msgList.get(ii));                                       //~@@@2I~
        }                                                          //~@@@2I~
    }                                                              //~@@@2I~
	//********************************************************************//~@@@2I~
	public void append (String s)
	{                                                              //~v101R~
    	int pos;                                                   //~v101I~
        String s2=s;                                               //~1A6vI~
        if (s.endsWith("\n"))                                      //~1A6vI~
            s2=s.substring(0,s.length()-1);                        //~1A6vI~
    	if (s.charAt(0)=='<' && (pos=s.indexOf('>'))>0)            //~v101R~
        {                                                          //~v101I~
//          SpannableString span=new SpannableString(s);                       //~v101I~//~1A6vR~
            SpannableString span=new SpannableString(s2);          //~1A6vI~
            span.setSpan(new BackgroundColorSpan(receiveMsgBGColor),0,pos+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//~v101R~
            T.append(span);                                        //~v101I~
        }                                                          //~v101I~
        else                                                       //~v101I~
//  		T.append(s);                                           //~v101I~//~1A6vR~
    		T.append(s2);                                          //~1A6vI~
	}
	//********************************************************************//~@@@2I~
	public void stackMsg(String s)                                 //~@@@2I~
	{                                                              //~@@@2I~
    	msgList.add(s);                                             //~@@@2I~
        int size=msgList.size();                                   //~@@@2I~
        if (size>MAX_STACKSIZE)                                      //~@@@2I~
        {                                                          //~@@@2I~
        	for (int ii=0;ii<size-MAX_STACKSIZE;ii++)              //~@@@2I~
            	msgList.remove();	//remove 1st                   //~@@@2I~
        }                                                          //~@@@2I~
	}                                                              //~@@@2I~
//    //********************************************************************//~@@@2I~//~v101R~
//    public void appendMultiline(String s)                          //~@@@2I~//~v101R~
//    {                                                              //~@@@2I~//~v101R~
//        if (Dump.Y) Dump.println("appendMultiline size="+(multiLine.size()+1)+"="+s);//~@@@2I~//~v101R~
//        multiLine.add(s);                                          //~@@@2I~//~v101R~
//        append(s);  //to TextArea                                  //~@@@2I~//~v101R~
//        stackMsg(s);                                               //~@@@2I~//~v101R~
//    }                                                              //~@@@2I~//~v101R~
//    //********************************************************************//~@@@2R~
//    public void sendMultiline()                                  //~@@@2R~
//    {                                                            //~@@@2R~
//        String[] sa;                                             //~@@@2R~
//        sa=multiLine.toArray(new String[0]);                     //~@@@2R~
//        multiLine.clear();                                       //~@@@2R~
//        for (int ii=0;ii<sa.length;ii++)                         //~@@@2R~
//        {                                                        //~@@@2R~
//            String msg=AG.YourName+":"+sa[ii]+"\n";              //~@@@2R~
//            MsgThread.enqSendMsg(msg);                           //~@@@2R~
//        }                                                        //~@@@2R~
//    }                                                            //~@@@2R~
//    //********************************************************************//~@@@2R~
//    public void setActionListener(TextField Pfld)                //~@@@2R~
//    {                                                            //~@@@2R~
//        EditText etx=(EditText)Pfld.textView;                    //~@@@2R~
//        etx.setOnEditorActionListener(                           //~@@@2R~
//            new OnEditorActionListener()                         //~@@@2R~
//            {                                                    //~@@@2R~
//                @Override                                        //~@@@2R~
//                public boolean onEditorAction(TextView Ptv,int Pactionid,KeyEvent Pkeyevent)//~@@@2R~
//                {                                                //~@@@2R~
//                    try                                          //~@@@2R~
//                    {                                            //~@@@2R~
//                        if (Dump.Y) Dump.println("OnEditorAction actionid="+Pactionid);//~@@@2R~
//                        if (Pactionid==EditorInfo.IME_ACTION_DONE)//~@@@2R~
//                        {                                        //~@@@2R~
//                            String line=Ptv.getText().toString();//~@@@2R~
//                            if (Dump.Y) Dump.println("OnEditorAction line="+line);//~@@@2R~
//                            appendMultiline(line);               //~@@@2R~
//                            Ptv.setText("");                     //~@@@2R~
//                            return true;                         //~@@@2R~
//                        }                                        //~@@@2R~
//                    }                                            //~@@@2R~
//                    catch(Exception e)                           //~@@@2R~
//                    {                                            //~@@@2R~
//                        Dump.println(e,"SayDialog OnEditActionListener");//~@@@2R~
//                    }                                            //~@@@2R~
//                    return false;                                //~@@@2R~
//                }                                                //~@@@2R~
//            }                                                    //~@@@2R~
//                                     );                          //~@@@2R~
//    }                                                            //~@@@2R~
//    public void paint (Graphics g)                               //~@@@2R~
//    {   if (SD.MD==null)                                         //~@@@2R~
//        {   CF.removeCloseListener(this);                        //~@@@2R~
//            setVisible(false); dispose(); return;                //~@@@2R~
//        }                                                        //~@@@2R~
//        super.paint(g);                                          //~@@@2R~
//    }                                                            //~@@@2R~
//    public boolean close ()                                      //~@@@2R~
//    {   return true;                                             //~@@@2R~
//    }                                                            //~@@@2R~
//    public void isClosed ()                                      //~@@@2R~
//    {   doclose();                                               //~@@@2R~
//    }                                                            //~@@@2R~
//    public void doclose ()                                       //~@@@2R~
//    {   SD.MD=null; CF.removeCloseListener(this);                //~@@@2R~
//        super.doclose();                                         //~@@@2R~
//    }                                                            //~@@@2R~
//    public void windowOpened (WindowEvent e)                     //~@@@2R~
//    {   Answer.requestFocus();                                   //~@@@2R~
//    }                                                            //~@@@2R~
//    public void keyPressed (KeyEvent e)                          //~@@@2R~
//    {   if (e.getKeyCode()==KeyEvent.VK_ESCAPE && close()) doclose();//~@@@2R~
//    }                                                            //~@@@2R~
//    public void keyTyped (KeyEvent e) {}                         //~@@@2R~
//    public void keyReleased (KeyEvent e)                         //~@@@2R~
//    {   String s=Global.getFunctionKey(e.getKeyCode());          //~@@@2R~
//        if (s.equals("")) return;                                //~@@@2R~
//        Answer.setText(s);                                       //~@@@2R~
//    }                                                            //~@@@2R~
}
