//*CID://+1AesR~:                             update#=  366;       //~1AesR~
//****************************************************************************//~v101I~
//1Aes 2016/06/29 (Bug) replay file saved by clipboard-save dose not show move msg//~1AesI~
//1A4v 2014/12/07 dislay comment area for replyboard               //~1A4vI~
//1A35 2013/04/19 show mark of last moved from position            //~1A35I~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2m 2013/04/06 register gameover reason at last entry of ActionMove//~1A2mI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A2d 2013/03/29 replay mode on Free Board                        //~1A2dI~
//1A1b 2013/03/13 FreeBoard:multiple snapshot                      //~1A1bI~
//1A17 2013/03/12 slide for piece remove on freeboard              //~1A17I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A0f 2013/03/05 check Chackmate for gameover                     //~1A0fI~
//1A0e 2013/03/05 (BUG)captured list of partner is not maintained at drop//~1A0eI~
//1A0a 2013/03/03 issue "check" sound                              //~1A0aI~
//101g 2013/02/09 captured mark remains at after partner move after I captured//~v101I~
//****************************************************************************//~v101I~
package jagoclient.board;


import com.Asgts.AG;
import com.Asgts.R;
import jagoclient.Dump;
import jagoclient.ReplayGoFrame;

public class ReplayBoard extends FreeBoard                         //~1A2dR~
{                                                                  //~@@@2R~
	ConnectedGoFrame CGF;                                          //~@@@2I~
	ReplayGoFrame RGF;                                             //~1A2dI~
	public ReplayBoard (int size,ConnectedGoFrame gf)               //~1A10R~//~1A2dR~
	{	super(size,gf);
		CGF=(ConnectedGoFrame)gf;                                  //~1A2dR~
		RGF=(ReplayGoFrame)gf;                                                     //~1A2dI~
        aRules=new Rules(CGF,this,S);                             //~v1A0R~//~1A00I~//~v101I~
	}
//**************************************************************** //~1A10I~
	@Override //FreeBoard-Board                                              //~1A10I~//~1A2dR~
	public void showinformation ()
	{                                                              //~1A10R~
        if (Dump.Y) Dump.println("ReplyBoard:showInformation");     //~1A1bI~//~1A2dR~
	}
//**************************************************************** //~1A2dI~
//*from ReplayGoFrame                                              //~1A2dI~
//**************************************************************** //~1A2dI~
//  public void replayRedo(ActionMove Paction,boolean Plastupdate)//~1A2dR~//~1A2eR~
    public void replayRedo(Notes Pnotes,ActionMove Paction,boolean Plastupdate)//~1A2eR~
    {                                                              //~1A2dI~
    	int ii,jj,piece,color;                           //~1A2dR~
    //*****************************                                //~1A2dI~
//  	yourcolor=RGF.replayNotes.yourcolor;                        //~1A2dI~//~1A2eR~
        ii=Paction.iFrom; jj=Paction.jFrom;                        //~1A2dI~
        piece=Paction.pieceTo; color=Paction.color;                //~1A2dR~
//        if (yourcolor!=RGF.Col)                                    //~1A2dR~//~1A2eR~
//        {                                                          //~1A2dI~//~1A2eR~
//            ii=ActionMove.reverseIndex(ii);                        //~1A2dI~//~1A2eR~
//            jj=ActionMove.reverseIndex(jj);                        //~1A2dI~//~1A2eR~
//        }                                                          //~1A2dI~//~1A2eR~
        if (Paction.drop==0)                                       //~1A2dI~
        {                                                          //~1A2dI~
        	P.piece(ii,jj,0,0);	//delete                           //~1A2dI~
            update(ii,jj);                                         //~1A2dI~
        }                                                          //~1A2dI~
        ii=Paction.iTo; jj=Paction.jTo;                            //~1A2dI~
//        if (yourcolor!=RGF.Col)                                    //~1A2dR~//~1A2eR~
//        {                                                          //~1A2dI~//~1A2eR~
//            ii=ActionMove.reverseIndex(ii);                        //~1A2dI~//~1A2eR~
//            jj=ActionMove.reverseIndex(jj);                        //~1A2dI~//~1A2eR~
//        }                                                          //~1A2dI~//~1A2eR~
        P.piece(ii,jj,color,piece); //move                         //~1A2dI~
        if (Plastupdate)                                           //~1A2dI~
        {                                                          //~1A2dI~
            removeLastMark();                                      //~1A2dI~
        	lasti=ii; lastj=jj;                                    //~1A2dI~
        }                                                          //~1A2dI~
        update(ii,jj);                                             //~1A2dI~
        if (Paction.drop!=0)                                       //~1A2dI~
        	GF.updateCapturedList(color,piece,-1);                 //~1A2dI~
        piece=Paction.capturedPiece;                               //~1A2dI~
        if (piece!=0)                                              //~1A2dI~
        	GF.updateCapturedList(-color/*captured piece color*/,Field.nonPromoted(piece),1/*countup*/);//~1A2dR~
        if (Plastupdate)                                           //~1A2dI~
        {                                                          //~1A2dI~
	        if (Paction.drop==0)                                   //~1A35I~
				setLastMarkFrom(Paction);                          //~1A35I~
//        	displayMoveDescription(Paction,RGF.replayNotes);       //~1A2eR~
          	displayMoveDescription(Paction,Pnotes);                //~1A2eI~
        	copy();                                                //~1A2dI~
        	RGF.setMainColor(-color);    //TGF,capturedlist displayCurrentcolor                             //~1A1bM~//~1A2dI~
        }                                                          //~1A2dI~
    }                                                              //~1A2dI~
//**************************************************************** //~1A2eI~
//*from ReplayGoFrame                                              //~1A2eI~
//**************************************************************** //~1A2eI~
    public static void replayRedo_ReceiveNotes(TimedGoFrame Ptgf,Notes Pnotes,ActionMove Paction,boolean Plastupdate)//~1A2eI~
    {                                                              //~1A2eI~
    	int ii,jj,piece,color;                           //~1A2eI~
        Board B=Ptgf.B;                                            //~1A2eI~
        Position P=B.P;                                            //~1A2eI~
    //*****************************                                //~1A2eI~
        ii=Paction.iFrom; jj=Paction.jFrom;                        //~1A2eI~
        piece=Paction.pieceTo; color=Paction.color;                //~1A2eI~
        if (Paction.drop==0)                                       //~1A2eI~
        {                                                          //~1A2eI~
        	P.piece(ii,jj,0,0);	//delete                           //~1A2eI~
            B.update(ii,jj);                                       //~1A2eI~
        }                                                          //~1A2eI~
        ii=Paction.iTo; jj=Paction.jTo;                            //~1A2eI~
        P.piece(ii,jj,color,piece); //move                         //~1A2eI~
        B.update(ii,jj);                                           //~1A2eI~
        if (Paction.drop!=0)                                       //~1A2eI~
        	Ptgf.updateCapturedList(color,piece,-1);               //~1A2eI~
        piece=Paction.capturedPiece;                               //~1A2eI~
        if (piece!=0)                                              //~1A2eI~
        	Ptgf.updateCapturedList(-color/*captured piece color*/,Field.nonPromoted(piece),1/*countup*/);//~1A2eI~
    }                                                              //~1A2eI~
//**************************************************************** //~1A2eI~
//*from ReplayGoFrame                                              //~1A2eI~
//**************************************************************** //~1A2eI~
	public void replayRedoDescription(Notes Pnotes,ActionMove Paction)//~1A2eR~
    {                                                              //~1A2eI~
        String msg;                                                //~1A2mI~
    //*****************************                                //~1A2eI~
        int color=Paction.color;                                   //~1A2eM~
	    replayRedo(Pnotes,Paction,false/*no copy,no descmsg*/);    //~1A2eM~
        if (Pnotes.filetype!=0)                                    //~1A30R~
        	msg=((ConnectedBoard)RGF.B).getMoveDescription_OtherLang(Paction,true);//~1A30R~
        else                                                       //~1A30R~
        	msg=((ConnectedBoard)RGF.B).getMoveDescription_OtherLang(Paction);//CGF//~1A2eI~//~1A2mR~
        if (msg!=null)                                             //~1A2eI~
//      	Paction.actionMsg[0]=msg;    //msgCtr was not updated  //~1A4vR~
        	Paction.setMsg(msg);                                   //~1A4vI~
        RGF.setMainColor(-color);    //TGF,capturedlist displayCurrentcolor//~1A2eI~
    }                                                              //~1A2eI~
//**************************************************************** //~1AesI~
//*from ReplayGoFrame when no actionMsg                            //~1AesI~
//**************************************************************** //~1AesI~
	public void replayRedoDescription2(Notes Pnotes,ActionMove Paction)//~1AesI~
    {                                                              //~1AesI~
        String msg;                                                //~1AesI~
    //*****************************                                //~1AesI~
        int color=Paction.color;                                   //~1AesI~
	    replayRedo(Pnotes,Paction,false/*no copy,no descmsg*/);    //~1AesI~
        if (Paction.actionMsg==null || Paction.actionMsg[0]==null) //+1AesI~
        {                                                          //+1AesI~
        	msg=((ConnectedBoard)RGF.B).getMoveDescription_OtherLang(Paction,true);//~1AesI~
        	if (msg!=null)                                         //+1AesR~
        		Paction.setMsg(msg);                               //+1AesR~
        	if (Dump.Y) Dump.println("RB:replayRedoDescription setmsg movemsg="+msg);//+1AesI~
        }                                                          //+1AesI~
        RGF.setMainColor(-color);    //TGF,capturedlist displayCurrentcolor//~1AesI~
    }                                                              //~1AesI~
//**************************************************************** //~1A2eI~
//*from TGF,partner recive note                                    //~1A2eI~
//**************************************************************** //~1A2eI~
	public static void replayRedoDescription_ReceiveNotes(TimedGoFrame Ptgf,Notes Pnotes,ActionMove Paction)//~1A2eI~
    {                                                              //~1A2eI~
    //*****************************                                //~1A2eI~
        int color=Paction.color;                                   //~1A2eI~
	    replayRedo_ReceiveNotes(Ptgf,Pnotes,Paction,false/*no copy,no descmsg*/);//~1A2eI~
        String msg=((ConnectedBoard)Ptgf.B).getMoveDescription_OtherLang(Paction);//CGF//~1A2eI~
        if (msg!=null)                                             //~1A2eI~
        	Paction.actionMsg[0]=msg;                              //~1A2eI~
        Ptgf.setMainColor(-color);    //TGF,capturedlist displayCurrentcolor//~1A2eI~
    }                                                              //~1A2eI~
//**************************************************************** //~1A2dI~
//*from ReplayGoFrame                                              //~1A2dI~
//**************************************************************** //~1A2dI~
	public void replayUndo(ActionMove Paction,ActionMove Pprevaction,boolean Plastupdate)//~1A2dR~
    {                                                              //~1A2dI~
    	int ii,jj,piece,color,piececaptured;             //~1A2dR~
    //*****************************                                //~1A2dI~
       ii=Paction.iTo; jj=Paction.jTo;                            //~1A2dR~
        piece=Paction.piece; color=Paction.color;                  //~1A2dR~
        piececaptured=Paction.capturedPiece;                       //~1A2dI~
//        if (yourcolor!=RGF.Col)                                    //~1A2dI~//~1A2eR~
//        {                                                          //~1A2dI~//~1A2eR~
//            ii=ActionMove.reverseIndex(ii);                        //~1A2dI~//~1A2eR~
//            jj=ActionMove.reverseIndex(jj);                        //~1A2dI~//~1A2eR~
//        }                                                          //~1A2dI~//~1A2eR~
        if (piececaptured==0)                                      //~1A2dI~
        {                                                          //~1A2dI~
        	P.piece(ii,jj,0,0); //undo del                         //~1A2dI~
        }                                                          //~1A2dI~
        else                                                       //~1A2dI~
        {                                                          //~1A2dI~
        	P.piece(ii,jj,-color,piececaptured); //undo del        //~1A2dI~
        	CGF.aCapturedList.updateCapturedList_Undo(-color/*captured piece color*/,piececaptured,-1/*countup*/);//~1A2dR~
        }                                                          //~1A2dI~
        update(ii,jj);                                             //~1A2dI~
        if (Paction.drop==0)                                       //~1A2dI~
        {                                                          //~1A2dI~
	        ii=Paction.iFrom; jj=Paction.jFrom;                    //~1A2dI~
//            if (yourcolor!=RGF.Col)                                //~1A2dI~//~1A2eR~
//            {                                                      //~1A2dI~//~1A2eR~
//                ii=ActionMove.reverseIndex(ii);                    //~1A2dI~//~1A2eR~
//                jj=ActionMove.reverseIndex(jj);                    //~1A2dI~//~1A2eR~
//            }                                                      //~1A2dI~//~1A2eR~
        	P.piece(ii,jj,color,piece);	//undo                     //~1A2dR~
            update(ii,jj);                                         //~1A2dI~
        }                                                          //~1A2dI~
        else                                                       //~1A2dI~
        	GF.updateCapturedList(-color/*captured piece color is like as opponent captured*/,piece,1);//~1A2dR~
        if (Plastupdate)                                           //~1A2dM~
        {                                                          //~1A2dM~
            removeLastMark();                                      //~1A2dM~
            if (Pprevaction!=null)                                 //~1A2dI~
            {                                                      //~1A2dI~
		        ii=Pprevaction.iTo; jj=Pprevaction.jTo;            //~1A2dI~
//                if (yourcolor!=RGF.Col)                            //~1A2dI~//~1A2eR~
//                {                                                  //~1A2dI~//~1A2eR~
//                    ii=ActionMove.reverseIndex(ii);                //~1A2dI~//~1A2eR~
//                    jj=ActionMove.reverseIndex(jj);                //~1A2dI~//~1A2eR~
//                }                                                  //~1A2dI~//~1A2eR~
                lasti=ii; lastj=jj;                                //~1A2dI~
	            update(ii,jj);                                     //~1A2dI~
		        if (Pprevaction.drop==0)                            //~1A35R~
					setLastMarkFrom(Pprevaction);                   //~1A35R~
            }                                                      //~1A2dI~
        }                                                          //~1A2dM~
        if (Plastupdate)                                           //~1A2dR~
        {                                                          //~1A2dI~
	        if (Pprevaction!=null)                                 //~1A2dI~
        		displayMoveDescription(Pprevaction,RGF.replayNotes);//~1A2eR~
            else                                                   //~1A2dI~
        		CGF.setLabel(R.string.InfoUndoReachedToInit);      //~1A2dI~
        	copy();                                                //~1A2dI~
        	RGF.setMainColor(color);    //TGF,capturedlist displayCurrentcolor//~1A2dR~
        }                                                          //~1A2dI~
    }                                                              //~1A2dI~
//**************************************************************** //~1A2dI~
	public void removeLastMark()                                   //~1A2dI~
    {                                                              //~1A2dI~
        if (lasti>=0 && (AG.Options & AG.OPTIONS_SHOW_LAST)!=0)    //~1A2dI~
        {                                                          //~1A2dI~
            int iii=lasti; int jjj=lastj;                          //~1A2dI~
            lasti=-1; lastj=-1;  //no last mark                    //~1A2dI~
            update(iii,jjj);    //delete last moved mark           //~1A2dI~
        }                                                          //~1A2dI~
        removeLastMarkFrom();                                      //~1A35I~
    }                                                              //~1A2dI~
//**************************************************************** //~1A35I~
	public void removeLastMarkFrom()                               //~1A35I~
    {                                                              //~1A35I~
        swCursorMovedFrom=false;                                   //~1A35M~
        if (lastifrom>=0)                                          //~1A35R~
        {                                                          //~1A35I~
            int iii=lastifrom; int jjj=lastjfrom;                  //~1A35I~
            lastifrom=-1; lastjfrom=-1;  //no last mark            //~1A35I~
            update(iii,jjj);    //delete last moved mark           //~1A35I~
        }                                                          //~1A35I~
    }                                                              //~1A35I~
//**************************************************************** //~1A2dI~
	public void setLastMark(ActionMove Paction)                    //~1A2dI~
    {                                                              //~1A2dI~
    	int ii,jj;                           //~1A2dI~
    //*********************************                            //~1A2dI~
        if (!(lasti>=0 && (AG.Options & AG.OPTIONS_SHOW_LAST)!=0)) //~1A2dI~
        	return;                                                //~1A2dI~
        ii=Paction.iTo; jj=Paction.jTo;                            //~1A2dI~
//        if (yourcolor!=RGF.Col)                                    //~1A2dI~//~1A2eR~
//        {                                                          //~1A2dI~//~1A2eR~
//            ii=ActionMove.reverseIndex(ii);                        //~1A2dI~//~1A2eR~
//            jj=ActionMove.reverseIndex(jj);                        //~1A2dI~//~1A2eR~
//        }                                                          //~1A2dI~//~1A2eR~
        lasti=ii; lastj=jj;                                        //~1A2dI~
        update(ii,jj);    //delete last moved mark                 //~1A2dI~
    }                                                              //~1A2dI~
//**************************************************************** //~1A35I~
	public void setLastMarkFrom(ActionMove Paction)                //~1A35I~
    {                                                              //~1A35I~
    	int ii,jj;                                                 //~1A35I~
    //*********************************                            //~1A35I~
        if ((AG.Options & AG.OPTIONS_SHOW_LAST)==0)                //~1A35R~
        	return;                                                //~1A35I~
        if (Paction.drop!=0)                                       //~1A35R~
        	return;                                                //~1A35I~
        swCursorMovedFrom=true;                                    //~1A35R~
        lastifrom=Paction.iFrom; lastjfrom=Paction.jFrom;          //~1A35R~
        update(lastifrom,lastjfrom);    //delete last moved mark   //~1A35R~
    }                                                              //~1A35I~
//**************************************************************** //~1A2dI~
//  public void displayMoveDescription(ActionMove Paction)         //~1A2eR~
    public void displayMoveDescription(ActionMove Paction,Notes Pnotes)//~1A2eR~
    {                                                              //~1A2dI~
    //*********************************                            //~1A2dI~
    	String msg=Paction.actionMsg[0];                           //~1A2dI~
        if (msg!=null)                                             //~1A2dI~
        {                                                          //~1A2dI~
        	CGF.setLabel(msg);                                     //~1A2dI~
        }                                                          //~1A2dI~
        NotesTree tree=Pnotes.getTree();                           //~1A2mR~
        if (tree!=null && tree.size()!=0 && Paction==tree.lastElement())//~1A2mR~
        {                                                          //~1A2mR~
        	int color=tree.winner;                                 //~1A2mR~
            int msgid=tree.gameoverMsgid;                          //~1A2mR~
            if (color!=0)                                          //~1A2mR~
            {                                                      //~1A2mR~
                int id;
            	if (color>0)                                       //~1A2mR~
                    id=R.string.WinnerBlack;                       //~1A2mR~
                else                                               //~1A2mR~
                    id=R.string.WinnerWhite;                       //~1A2mR~
                String winner=AG.resource.getString(id);           //~1A2mR~
                	String gomsg;
                if (msgid!=0)                                      //~1A30R~
        			gomsg=winner+" : "+AG.resource.getString(msgid);//~1A2mR~
                else                                               //~1A30R~
        			gomsg=winner;                           //~1A30R~
        		CGF.setLabel(gomsg,true);    //GF                  //~1A2mR~
            }                                                      //~1A2mR~
        }                                                          //~1A2mR~
    }                                                              //~1A2dI~
}
