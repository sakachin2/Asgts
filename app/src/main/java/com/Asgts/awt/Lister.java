//*CID://+1Ah8R~: update#= 167;                                    //~1Ah8R~
//**********************************************************************//~1107I~
//1Ah8 2016/11/15 FileDialog:support select all button             //~1Ah8I~
//1A4n 2014/12/04 FileDialog:multiple delete support               //~1A4nI~
//1A21 2013/03/22 File Dialog on Free Board                        //~1A21I~
//**********************************************************************//~1A21I~
//*My ListView Adapter                                                     //~1107I~//~1109R~
//**********************************************************************//~1107I~
package com.Asgts.awt;                                         //~1107R~  //~1108R~//~1109R~//~1114R~//~1A21R~
                                                                   //~1109I~
public class Lister extends Component                                                  //~1114R~//~1220R~
{                                                                  //~1110I~
                                                                   //~1220I~
	private List list;                                             //~1425R~
	private boolean swSetText;                                     //~1425R~
//*****************                                                //~1112I~
//  public Lister()                                               //~1112I~//~1114R~//~1220R~//~1A21R~
    public Lister(Container Pcontainer,int Presid,int Prowresid)   //~1A21I~
    {                                                              //~1112I~
//  	list=new List(AG.viewId_Lister);                           //~1220I~//~1A21R~
    	list=new List(Pcontainer,Presid,Prowresid);                //~1A21I~
    }                                                              //~1112I~
//*****************                                                //~1220M~
    public void doUpdate(boolean Pshowlast)                        //~1220R~//~1221R~
    {                                         
		if (Pshowlast)                                             //~1221I~
			list.showBottom();                                     //~1221I~
        else                                                       //~1221I~
			list.showList();                                       //~1221R~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void setText(String Ptext)                              //~1220R~
    {                                                              //~1220I~
        list.setText(Ptext);                                       //~1221I~
        swSetText=true;                                            //~1224I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public void setFont(Font Pfont)                                //~1220R~
    {                                                              //~1220I~
        list.setFont(Pfont);                                       //~1220I~
    }                                                              //~1220I~
//*****************                                                //~1220I~
    public int getSelectedPos()                                    //~1403I~
    {                                                              //~1403I~
    	return list.getSelectedPos();                                 //~1403I~
    }                                                              //~1403I~
    public String getSelectedItem()                                //~1220I~
    {                                                              //~1220I~
        return list.getSelectedItem();                             //~1220I~
    }                                                              //~1220I~
    public void  setSelection(int Ppos)                            //~1411I~
    {                                                              //~1411I~
        list.select(Ppos);                                  //~1411I~
    }                                                              //~1411I~
//*****************                                                //~1220I~
    public void appendLine(String Pline)                           //~1403I~
    {                                                              //~1403I~
		appendLine(Pline,Color.black);                       //~1403I~
    }                                                              //~1403I~
    public void appendLine(String Pline,Color Pcolor)              //~1220I~
    {                                                              //~1220I~
		if (swSetText)                                             //~1224I~
        {	                                                       //~1224I~
        	swSetText=false;                                       //~1224I~
            list.removeAll();                                      //~1224R~
        }                                                          //~1224I~
        list.add(Pline,Pcolor);                                    //~1221I~
    }                                                              //~1220I~
    public void clearList()                                        //~1403I~
    {                                                              //~1403I~
        list.removeAll();                                          //~1403I~
    }                                                              //~1403I~
//*****************                                                //~1403I~
    public void setBackground(Color Pcolor)                        //~1403I~
    {                                                              //~1403I~
    	list.setBackground(Pcolor);                                //~1403I~
    }                                                              //~1403I~
//*****************                                                //~1A4nI~
    public void setChoiceMode(int Pchoicemode)                     //~1A4nR~
    {                                                              //~1A4nI~
    	list.setChoiceMode(Pchoicemode);                           //~1A4nR~
    }                                                              //~1A4nI~
//*****************                                                //~1A4nI~
    public int[] getCheckedItemPositions()                         //~1A4nR~
    {                                                              //~1A4nI~
		return  list.getCheckedItemPositions();                     //~1A4nI~
    }                                                              //~1A4nI~
//*****************                                                //~1Ah8I~
    public void selectAll(int Ppos,String Ppostfix)                //+1Ah8R~
    {                                                              //~1Ah8I~
		list.selectAll(Ppos,Ppostfix);                     //~1Ah8R~
    }                                                              //~1Ah8I~
//*****************                                                //+1Ah8I~
    public void deselectAll()                //+1Ah8I~
    {                                                              //+1Ah8I~
		list.deselectAll();                                        //+1Ah8I~
    }                                                              //+1Ah8I~
}//class                                                           //~1109I~
