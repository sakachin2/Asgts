//*CID://+1A08R~:                             update#=    6;       //~1A08R~
//*************************************************************************//~1A08I~
//1A08 2013/03/02 add sound "gameover" (chk also sdcard)           //~1A08I~
//*************************************************************************//~1A08I~
package com.Asgts;                                                 //~1A08M~
                                                                   //~1A08M~
import jagoclient.Dump;                                            //~1A08M~
                                                                   //~1A08M~
public class Tables
{
    public String name;                                            //+1A08R~
    private int id;                                                //+1A08I~
    private Object obj;                                            //~1A08R~
//****************
    public Tables(String Pname,int Pid,Object Pobj)
    {
        name=Pname; id=Pid; obj=Pobj;
    }
//****************
    public Tables(String Pname,int Pid)
    {
        this(Pname,Pid,null);
    }
//****************
	public static int find(Tables[] Ptbl,String Pname)
    {
    	int idx=-1,sz=Ptbl.length;
    	for (int ii=0;ii<sz;ii++)
    		if (Pname.equals(Ptbl[ii].name))
            {
            	idx=ii;
                break;
            }
    	if (Dump.Y) Dump.println("Tables:find name="+Pname+",idx="+idx);
    	return idx;
    }
//****************
	public static int find(Tables[] Ptbl,String Pname,int Pnotfoundid)
    {
    	int id;
    	int idx=find(Ptbl,Pname);
        if (idx==-1)
        	id=Pnotfoundid;
        else
        	id=Ptbl[idx].id;
    	if (Dump.Y) Dump.println("Tables:find name="+Pname+",id="+id);
        return id;
    }
//****************
	public static Object find(Tables[] Ptbl,String Pname,Object Pnotfoundid)
    {
    	Object obj;
    	int idx=find(Ptbl,Pname);
        if (idx==-1)
        	obj=Pnotfoundid;
        else
        	obj=Ptbl[idx].obj;
    	if (Dump.Y) Dump.println("Tables:find name="+Pname+",obj="+(obj==null?"null":obj.toString()));
        return obj;
    }
//****************                                                 //~1A08I~
	public void setObject(Object Pobj)                             //~1A08R~
    {                                                              //~1A08I~
    	obj=Pobj;                                                  //~1A08I~
    }                                                              //~1A08I~
//****************                                                 //~1A08I~
	public Object getObject()                                      //~1A08I~
    {                                                              //~1A08I~
    	return obj;                                                //~1A08I~
    }                                                              //~1A08I~
//****************                                                 //~1A08I~
	public int getId()                                             //~1A08I~
    {                                                              //~1A08I~
    	return id;                                                 //~1A08I~
    }                                                              //~1A08I~
//****************
}
