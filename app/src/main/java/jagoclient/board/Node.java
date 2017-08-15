//*CID://+v1A0R~:                             update#=    2;       //~v1A0I~
//***********************************************************************//~v1A0I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//***********************************************************************//~v1A0I~
package jagoclient.board;

//import java.io.PrintWriter;

import rene.util.list.ListClass;
import rene.util.list.ListElement;
import rene.util.list.Tree;
//import rene.util.xml.XmlWriter;                                  //~2C26R~

/**
A node has 
<UL>
<LI> a list of actions and a number counter (the number is the number
of the next expected move in the game tree),
<LI> a flag, if the node is in the main game tree,
<LI> a list of changes in this node to be able to undo the node,
<LI> the changes in the prisoner count in this node.
</UL>
@see jagoclient.board.Action
@see jagoclient.board.Change
*/

class Node
{	ListClass Actions; // actions and variations
	int N; // next exptected number
	boolean Main; // belongs to main variation
	ListClass Changes;
	public int Pw,Pb; // changes in prisoners in this node
	
	/** initialize with the expected number */
	public Node (int n)
	{	Actions=new ListClass();
		N=n;
		Main=false;
		Changes=new ListClass();
		Pw=Pb=0;
	}
	
	/** add an action (at end) */
	public void addaction (Action a)
	{	Actions.append(new ListElement(a));
	}

	/** expand an action of the same type as a, else generate a new action */
	public void expandaction (Action a)
	{	ListElement p=find(a.type());
		if (p==null) addaction(a);
		else
		{	Action pa=(Action)p.content();
			pa.addargument(a.argument());
		}
	}

	/**
	Expand an action of the same type as a, else generate a new action.
	If the action is already present with the same argument, delete
	that argument from the action.
	*/
	public void toggleaction (Action a)
	{	ListElement p=find(a.type());
		if (p==null) addaction(a);
		else
		{	Action pa=(Action)p.content();
			pa.toggleargument(a.argument());
		}
	}

	/** find the list element containing the action of type s */
	ListElement find (String s)
	{	ListElement p=Actions.first();
		while (p!=null)
		{	Action a=(Action)p.content();
			if (a.type().equals(s)) return p;
			p=p.next();
		}
		return null;
	}
	
	/** find the action and a specified tag */
	public boolean contains (String s, String argument)
	{	ListElement p=find(s);
		if (p==null) return false;
		Action a=(Action)p.content();
		return a.contains(argument);
	}
	
	/** see if the list contains an action of type s */
	public boolean contains (String s)
	{	return find(s)!=null;
	}

	/** add an action (at front) */
	public void prependaction (Action a)
	{	Actions.prepend(new ListElement(a));
	}
	
	/** 
	Insert an action after p.
	p <b>must</b> have content type action.
	*/
	public void insertaction (Action a, ListElement p)
	{	Actions.insert(new ListElement(a),p);
	}

	/** remove an action */
	public void removeaction (ListElement la)
	{	Actions.remove(la);
	}

	/**
	If there is an action of the type:
	Remove it, if arg is "", else set its argument to arg.
	Else add a new action in front (if it is true)
	*/
//  public void setaction (String type, String arg, boolean front) //~v1A0R~
    public void setaction (String type, String arg, boolean front,int Ppiece)//~v1A0R~
	{	ListElement l=Actions.first();
		while (l!=null)
		{	Action a=(Action)l.content();
			if (a.type().equals(type))
			{	if (arg.equals(""))
				{	Actions.remove(l);
					return;
				}
				else
				{	ListElement la=a.arguments();
					if (la!=null) la.content(arg);
					else a.addargument(arg);
				}
				return;
			}
			l=l.next();
		}
//  	if (front) prependaction(new Action(type,arg));            //~v1A0R~
//  	else addaction(new Action(type,arg));                      //~v1A0R~
    	if (front) prependaction(new Action(type,arg,Ppiece));     //~v1A0R~
    	else addaction(new Action(type,arg,Ppiece));               //~v1A0R~
	}

	/** set the action of this type to this argument */
//  public void setaction (String type, String arg)                //~v1A0R~
    public void setaction (String type, String arg,int Ppiece)     //~v1A0I~
//  {	setaction(type,arg,false);                                 //~v1A0R~
    {	setaction(type,arg,false,Ppiece);                          //~v1A0I~
	}

	/** get the argument of this action (or "") */
	public String getaction (String type)
	{	ListElement l=Actions.first();
		while (l!=null)
		{	Action a=(Action)l.content();
			if (a.type().equals(type))
			{	ListElement la=a.arguments();
				if (la!=null) return (String)la.content();
				else return "";
			}
			l=l.next();
		}
		return "";
	}
    //*********************************************                //+v1A0I~
	public int getpiece (String type)                           //+v1A0I~
	{	ListElement l=Actions.first();                             //+v1A0I~
		while (l!=null)                                            //+v1A0I~
		{	Action a=(Action)l.content();                          //+v1A0I~
			if (a.type().equals(type))                             //+v1A0I~
			{                                                      //+v1A0I~
				return a.Piece;                                    //+v1A0I~
			}                                                      //+v1A0I~
			l=l.next();                                            //+v1A0I~
		}                                                          //+v1A0I~
		return 0;                                                  //+v1A0I~
	}                                                              //+v1A0I~
	
	/** 
	Print the node in SGF.
	@see jagoclient.board.Action#print
	*/
//    public void print (PrintWriter o)                            //~2C31R~
//    {   o.print(";");                                            //~2C31R~
//        ListElement p=Actions.first();                           //~2C31R~
//        Action a;                                                //~2C31R~
//        while (p!=null)                                          //~2C31R~
//        {   a=(Action)p.content();                               //~2C31R~
//            a.print(o);                                          //~2C31R~
//            p=p.next();                                          //~2C31R~
//        }                                                        //~2C31R~
//        o.println("");                                           //~2C31R~
//    }                                                            //~2C31R~
	
//    public void print (XmlWriter xml, int size)                  //~2C26R~
//    {   int count=0;                                             //~2C26R~
//        Action ra=null,a;                                        //~2C26R~
//        ListElement p=Actions.first();                           //~2C26R~
//        while (p!=null)                                          //~2C26R~
//        {   a=(Action)p.content();                               //~2C26R~
//            if (a.isRelevant())                                  //~2C26R~
//            {   count++;                                         //~2C26R~
//                ra=a;                                            //~2C26R~
//            }                                                    //~2C26R~
//            p=p.next();                                          //~2C26R~
//        }                                                        //~2C26R~
//        if (count==0 && !contains("C"))                          //~2C26R~
//        {   xml.finishTagNewLine("Node");                        //~2C26R~
//            return;                                              //~2C26R~
//        }                                                        //~2C26R~
//        int number=N-1;                                          //~2C26R~
//        if (count==1)                                            //~2C26R~
//        {   if (ra.type().equals("B") || ra.type().equals("W"))  //~2C26R~
//            {   ra.printMove(xml,size,number,this);              //~2C26R~
//                number++;                                        //~2C26R~
//                if (contains("C"))                               //~2C26R~
//                {   a=((Action)find("C").content());             //~2C26R~
//                    a.print(xml,size,number);                    //~2C26R~
//                }                                                //~2C26R~
//                return;                                          //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//        xml.startTagStart("Node");                               //~2C26R~
//        if (contains("N")) xml.printArg("name",getaction("N"));  //~2C26R~
//        if (contains("BL")) xml.printArg("blacktime",getaction("BL"));//~2C26R~
//        if (contains("WL")) xml.printArg("whitetime",getaction("WL"));//~2C26R~
//        xml.startTagEndNewLine();                                //~2C26R~
//        p=Actions.first();                                       //~2C26R~
//        while (p!=null)                                          //~2C26R~
//        {   a=(Action)p.content();                               //~2C26R~
//            a.print(xml,size,number);                            //~2C26R~
//            if (a.type().equals("B") || a.type().equals("W")) number++;//~2C26R~
//            p=p.next();                                          //~2C26R~
//        }                                                        //~2C26R~
//        xml.endTagNewLine("Node");                               //~2C26R~
//    }                                                            //~2C26R~

	/** remove all actions */
	public void removeactions ()
	{	Actions=new ListClass();
	}

	/** add a new change to this node */
	public void addchange (Change c)
	{	Changes.append(new ListElement(c));
	}

	/** clear the list of changes */
	public void clearchanges ()
	{	Changes.removeall();
	}	

	// modification methods:
	public void main (boolean m) { Main=m; }
	/** 
	Set the Main flag
	@param Tree is the tree, which contains this node on root.
	*/
	public void main (Tree p)
	{   Main=false;
		try
		{	if (((Node)p.content()).main())
			{	Main=(this==((Node)p.firstchild().content()));
			}
			else if (p.parent()==null) Main=true;
		}
		catch (Exception e) {}
	}
	public void number (int n) { N=n; }
	
	/**
	Copy an action from another node.
	*/
	public void copyAction (Node n, String action)
	{	if (n.contains(action))
//  	{	expandaction(new Action(action,n.getaction(action)));  //~v1A0R~
    	{	expandaction(new Action(action,n.getaction(action),n.getpiece(action)));//+v1A0R~
		}
	}

	// access methods:
	public ListElement actions () { return Actions.first(); }
	public ListElement lastaction () { return Actions.last(); }
	public ListElement changes () { return Changes.first(); }
	public ListElement lastchange () { return Changes.last(); }
	public int number () { return N; }
	public boolean main () { return Main; }
}
