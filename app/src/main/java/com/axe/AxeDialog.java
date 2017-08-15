//*CID://+1Ah0R~: update#= 191;                                    //+1Ah0R~
//**********************************************************************//~1107I~
//1Ah0 2016/10/15 bonanza                                          //+1Ah0I~
//1A6J 2015/02/22 custom title for AxeDialog like as awt\Dialog    //~1A6JI~
//1A65 2015/01/29 implement Wifi-Direct function(>=Api14:android4.0)//~1A65I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//vagF:120920 (Axe)local html viewer fail by permission err(uid of process of HtmlViewer was checked)//~vagFI~
//vaaC:120110 close preedit when IM was closed by back key         //~2111I~
//**********************************************************************//~2111I~
//*AlerDialog                                                      //~1527R~
//**********************************************************************//~1107I~
//package com.Ajagoc.axe;                                            //~v1B6R~//~1A65R~
package com.axe;                                                   //~1A65I~

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;                            //~2111I~
                                                                   //~v1B6I~
import jagoclient.Dump;                                            //~v1B6I~
import com.Asgts.AG;                                              //~v1B6I~//~1A65R~//~1A6JR~
import com.Asgts.Alert;                                             //~1A65R~//~1A6JR~
import com.Asgts.R;                                                 //~1A65R~//~1A6JR~

//**********************************************************************//~1107I~
public class AxeDialog extends Dialog                              //~1830R~
{                                                                  //~0914I~
	private static final int buttonGroup          =R.id.DialogButtons;       //~1528I~
    public  static final String NO_TITLE="NoTitle";                //~vagFI~
	protected ViewGroup layoutView;                                  //~1818R~
//	protected AxeList axeList;                                     //~1818R~//~v1B6R~
    protected int layoutId;                                          //~1818R~
    private TextView customTitle;                                  //~1A6JI~
    //**********************************                               //~1211I~
	public AxeDialog(int Playoutid)                                //~1601R~
    {                                                              //~1211I~
        super(AG.context);                                         //~v1B6R~
		layoutId=Playoutid;                                        //~1601I~
    }                                                              //~1211I~
//**********************************
	public void showDialog(String Ptitle)                         //~1602R~
    {                                                              //~1528I~
    	layoutView=(ViewGroup)(AG.inflater.inflate(layoutId,null));//~v1B6I~
        customTitle=(TextView)layoutView.findViewById(R.id.CustomTitle);//~1A6JI~
        if (customTitle!=null)                                     //~1A6JI~
        {                                                          //~1A6JI~
           	requestWindowFeature(Window.FEATURE_NO_TITLE);  //~1A6JI~
    		setCustomTitle(Ptitle);                                //~1A6JI~
		}                                                          //~1A6JI~
        else                                                       //~1A6JI~
        {                                                          //~1A6JI~
      if (Ptitle.equals(NO_TITLE))                                 //~vagFI~
	    requestWindowFeature(Window.FEATURE_NO_TITLE);  //must before setcontent//~vagFI~
      else                                                         //~vagFI~
        setTitle(Ptitle);                                          //~vagFI~
        }                                                          //~1A6JI~
        setupDialogExtendPre(layoutView);	//before setContentView//~vagFI~
        setContentView(layoutView);                                //~1830I~
        switch(layoutId)                                            //~1601I~
        {                                                          //~1601I~
        default:                                                   //~1602I~
            setupDialogExtend(layoutView);                          //~1602I~
        }                                                          //~1601I~
        setButtonListenerAll(layoutView);                          //~1602R~
        setOnDismissListener(new dismissListener(this));   //~1215I~//~1410R~//~2111I~
        show();                                                    //~1830R~
    }                                                              //~1528I~
//*********                                                        //~1A6JI~
    private void setCustomTitle(String Ptitle)                     //~1A6JI~
    {                                                              //~1A6JI~
		customTitle.setText(Ptitle);                               //~1A6JI~
		customTitle.setVisibility(View.VISIBLE);                   //~1A6JI~
    }                                                              //~1A6JI~
//*********                                                        //~1831I~
	@Override                                                      //~1831I~
	public void onWindowFocusChanged(boolean PhasFocus)         //~1831I~
    {                                                              //~1831I~
		if (Dump.Y) Dump.println("onwindowFocusChanged:"+PhasFocus+","+this.toString());//~1831R~
    }                                                              //~1831I~
//*********                                                        //~1602I~
	protected void setupDialogExtend(ViewGroup PlayoutView)          //~1602R~
    {                                                              //~1602I~
//override by Extender                                             //~1602I~
    }                                                              //~1602I~
//*********                                                        //~vagFI~
	protected void setupDialogExtendPre(ViewGroup PlayoutView)     //~vagFI~
    {                                                              //~vagFI~
//override by Extender                                             //~vagFI~
    }                                                              //~vagFI~
//**********************************                               //~1528I~
	private void setButtonListenerAll(ViewGroup Pview)             //~1602R~
    {                                                              //~1528I~
        ViewGroup vg=(ViewGroup)(Pview.findViewById(buttonGroup));//~1528I~
        int ctr=vg.getChildCount();                                //~1528I~
        for (int ii=0;ii<ctr;ii++)                                     //~1528I~
        {                                                          //~1528I~
        	Button btn=(Button)(vg.getChildAt(ii));                //~1529R~
        	int btnid=btn.getId();                                 //~1528I~
            if (Dump.Y) Dump.println("AxeDialog layout="+Integer.toHexString(Pview.getId())+",btnid="+Integer.toString(btnid));//~1528I~
            setButtonListener(btn);                                //~1602R~
        }                                                          //~1528I~
    }                                                              //~1528I~
//**********************************                               //~1A65I~
	protected void setButtonListenerAll(int Pbuttongroupid)        //~1A65I~
    {                                                              //~1A65I~
        ViewGroup vg=(ViewGroup)(layoutView.findViewById(Pbuttongroupid));//~1A65I~
        int ctr=vg.getChildCount();                                //~1A65I~
        for (int ii=0;ii<ctr;ii++)                                 //~1A65I~
        {                                                          //~1A65I~
        	Button btn=(Button)(vg.getChildAt(ii));                //~1A65I~
        	int btnid=btn.getId();                                 //~1A65I~
            if (Dump.Y) Dump.println("AxeDialog SetButtonListenerAll btnid="+Integer.toString(btnid));//~1A65I~
            setButtonListener(btn);                                //~1A65I~
        }                                                          //~1A65I~
    }                                                              //~1A65I~
//*********************                                            //~1528I~
	protected void setButtonListener(View Pbutton)                 //~1919R~
    {                                                              //~1528I~
        ButtonListener cl=this.new ButtonListener(this);           //~1528I~
        Pbutton.setOnClickListener(cl);                            //~1528I~
    }                                                              //~1528I~
//************************                                         //~1602R~
//*dialog button Listener*                                         //~1602R~
//************************                                         //~1602R~
	private void onClickButtons(int PbuttonId)                     //~1821R~
    {                                                              //~1528I~
        boolean rc=true;                                           //~1528I~
    	if (Dump.Y) Dump.println("AxeDialog onClick layoutid="+Integer.toHexString(layoutId)+",buttonid="+Integer.toHexString(PbuttonId));//~1528I~
    	switch(PbuttonId)                                          //~1821I~
        {                                                          //~1821I~
        case R.id.Help:                                            //~1821I~
        	rc=onClickHelp();                                      //~1821I~
        	break;                                                 //~1821I~
        case R.id.Cancel:                                          //~1821I~
        	rc=onClickCancel();                                    //~1821I~
        	break;                                                 //~1821I~
        case R.id.Close:                                           //~1821I~
        	rc=onClickClose();                                     //~1821I~
        	break;                                                 //~1821I~
        default:                                                   //~1821I~
        	rc=onClickOther(PbuttonId);                            //~1821I~
        }                                                          //~1821I~
        if (rc)                                                    //~1528I~
          	dismiss();                                             //~1830I~
    }                                                              //~1528I~
//************                                                     //~1602I~
    protected boolean onClickHelp()                                //~1821R~
    {                                                              //~1602I~
//extender will override
    	return false;                                              //~1821R~
    }                                                              //~1602I~
//************                                                     //~1821I~
    protected boolean onClickCancel()                              //~1821I~
    {                                                              //~1821I~
//extender will override                                           //~1821I~
    	return true;                                               //~1821I~
    }                                                              //~1821I~
//************                                                     //~1821I~
    protected boolean onClickClose()                               //~1821I~
    {                                                              //~1821I~
    	return true;                                               //~1821I~
    }                                                              //~1821I~
//************                                                     //~1821I~
    protected boolean onClickOther(int PbuttonId)                  //~1821I~
    {                                                              //~1821I~
//extender will override                                           //~1821I~
    	return true;                                               //~1821I~
    }                                                              //~1821I~
//************                                                     //~2111I~
    protected void onDismiss()                                     //~2111I~
    {                                                              //~2111I~
//extender will override                                           //~2111I~
    	return;                                                    //~2111I~
    }                                                              //~2111I~
//*********************                                            //~1528I~
    public class ButtonListener implements View.OnClickListener         //~1528I~
    {                                                              //~1528I~
    	AxeDialog axeDialog;                                       //~1528I~
        public ButtonListener(AxeDialog PaxeDialog)                //~1528I~
        {                                                          //~1528I~
        	axeDialog=PaxeDialog;                                  //~1528I~
        }                                                          //~1528I~
        @Override                                                  //~1831I~
        public void onClick(View Pv)                               //~1528I~
        {                                                          //~1528I~
            try                                                    //~1831I~
            {                                                      //~1831I~
            	axeDialog.onClickButtons(Pv.getId());              //~1831R~
            }                                                      //~1831I~
            catch(Exception e)                                     //~1831I~
            {                                                      //~1831I~
                Dump.println(e,"AxeDialog.OnClick");               //~1831I~
            }                                                      //~1831I~
        }                                                          //~1528I~
    }                                                              //~1528I~
//************************************************                 //~1604I~
	public void showDialogHelp(int PtitleResId,int PmsgResId)      //~1604I~
    {                                                              //~1604I~
        String title=AG.resource.getString(PtitleResId);         //~1A11I~//~v1B6R~
//  	String helpmsg=Nls.getText(PmsgResId,title/*as textfile name*/);//~1A11I~//~v1B6R~
    	String helpmsg=AG.resource.getString(PmsgResId);   //~v1B6I~
    	int flag=Alert.BUTTON_CLOSE|Alert.SHOW_DIALOG;       //~1604I~//~v1B6R~//~1A65R~
        Alert.simpleAlertDialog(null/*callback*/,title,helpmsg,flag);//~1A11R~//~v1B6R~//~1A65R~
    }                                                              //~1604I~
//************************************************                 //~1604I~
	public void setEditTextEnable(EditText Pview,boolean Peditable)//~1604I~
    {                                                              //~1604I~
        Pview.setEnabled(Peditable); 	//currently cannot set editable=false  by setEnabled//~1604I~
        if (Peditable)                                             //~1604I~
	        Pview.setFocusableInTouchMode(true);                   //~1604I~
        else                                                       //~1604I~
	        Pview.setFocusable(false);                             //~1604I~
    }                                                              //~1604I~
//*******************************                                  //~1126M~//~1215M~//~2111I~
//*dismiss listener                                                //~1126I~//~1215M~//~2111I~
//*******************************                                  //~1126I~//~1215M~//~2111I~
    public class dismissListener                                   //~1126M~//~1215M~//~2111I~
    		implements OnDismissListener                           //~1126M~//~1215M~//~2111I~
	{                                                              //~1126M~//~1215M~//~2111I~
    	AxeDialog dlg;                                             //~2111I~
    	public dismissListener(AxeDialog Pdlg)                     //~2111I~
        {                                                          //~2111I~
        	dlg=Pdlg;                                              //~2111I~
        }                                                          //~2111I~
        @Override                                                  //~1126M~//~1215M~//~2111I~
        public void onDismiss(DialogInterface Pdialog)             //~1126M~//~1215M~//~2111I~
        {                                                          //~1126M~//~1215M~//~2111I~
			if (Dump.Y) Dump.println("AxeDialog onDismiss");        //~1127I~//~1506R~//~2111I~
          try                                                      //~1A65I~
          {                                                        //~1A65I~
            dlg.onDismiss();	                                   //~2111I~
          }                                                        //~1A65I~
          catch(Exception e)                                       //~1A65I~
          {                                                        //~1A65I~
          	Dump.println(e,"AxeDialog:onDismiss");                 //~1A65I~
          }                                                        //~1A65I~
        }                                                          //~1126M~//~1215M~//~2111I~
    }                                                              //~1126M~//~1215M~//~2111I~
}//class AxeDialog                                                 //~1528R~
