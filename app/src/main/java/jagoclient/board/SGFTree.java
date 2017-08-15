package jagoclient.board;

/**
This is a class wich contains a TreeNode. It used to store complete
game trees.
@see jagoclient.board.TreeNode
*/

public class SGFTree
{	protected TreeNode History; // the game history

	/** initlialize with a specific Node */
	public SGFTree (Node n)
	{	History=new TreeNode(n);
		History.node().main(true);
	}
	
	/** return the top node of this game tree */
	public TreeNode top () { return History; }

	final int maxbuffer=4096;
	char[] Buffer=new char[maxbuffer]; // the buffer for reading of files
	int BufferN;
	BoardInterface GF;

//    char readnext (BufferedReader in) throws IOException         //~2C26R~
//    {   int c=readchar(in);                                      //~2C26R~
//        while (c=='\n' || c=='\t' || c==' ')                     //~2C26R~
//        {   if (c==-1) throw new IOException();                  //~2C26R~
//            c=readchar(in);                                      //~2C26R~
//        }                                                        //~2C26R~
//        return (char)c;                                          //~2C26R~
//    }                                                            //~2C26R~

	static int lastnl=0;
	
//    char readchar (BufferedReader in) throws IOException         //~2C26R~
//    {   int c;                                                   //~2C26R~
//        while (true)                                             //~2C26R~
//        {   c=in.read();                                         //~2C26R~
//            if (c==-1) throw new IOException();                  //~2C26R~
//            if (c==13)                                           //~2C26R~
//            {   if (lastnl==10) lastnl=0;                        //~2C26R~
//                else                                             //~2C26R~
//                {   lastnl=13; return '\n';                      //~2C26R~
//                }                                                //~2C26R~
//            }                                                    //~2C26R~
//            else if (c==10)                                      //~2C26R~
//            {   if (lastnl==13) lastnl=0;                        //~2C26R~
//                else                                             //~2C26R~
//                {   lastnl=10; return '\n';                      //~2C26R~
//                }                                                //~2C26R~
//            }                                                    //~2C26R~
//            else                                                 //~2C26R~
//            {   lastnl=0;                                        //~2C26R~
//                return (char)c;                                  //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~

	// read a node assuming that ; has been found
	// return the character, which did not fit into node properties,
	// usually ;, ( or )
//    char readnode (TreeNode p, BufferedReader in) throws IOException//~2C26R~
//    {   boolean sgf=GF.getParameter("sgfcomments",false);        //~2C26R~
//        char c=readnext(in);                                     //~2C26R~
//        Action a;                                                //~2C26R~
//        Node n=new Node(((Node)p.content()).number());           //~2C26R~
//        String s;                                                //~2C26R~
//        loop: while (true) // read all actions                   //~2C26R~
//        {   BufferN=0;                                           //~2C26R~
//            while (true)                                         //~2C26R~
//            {   if (c>='A' && c<='Z') store(c);                  //~2C26R~
//                    // note only capital letters                 //~2C26R~
//                else if (c=='(' || c==';' || c==')') break loop; //~2C26R~
//                    // last property reached                     //~2C26R~
//                    // BufferN should be 0 then                  //~2C26R~
//                else if (c=='[') break;                          //~2C26R~
//                    // end of porperty type, arguments starting  //~2C26R~
//                else if (c<'a' || c>'z') throw new IOException();//~2C26R~
//                    // this is an error                          //~2C26R~
//                c=readnext(in);                                  //~2C26R~
//            }                                                    //~2C26R~
//            if (BufferN==0) throw new IOException();             //~2C26R~
//            s=new String(Buffer,0,BufferN);                      //~2C26R~
//            if (s.equals("L")) a=new LabelAction(GF);            //~2C26R~
//            else if (s.equals("M")) a=new MarkAction(GF);        //~2C26R~
//            else a=new Action(s);                                //~2C26R~
//            while (c=='[')                                       //~2C26R~
//            {   BufferN=0;                                       //~2C26R~
//                while (true)                                     //~2C26R~
//                {   c=readchar(in);                              //~2C26R~
//                    if (c=='\\')                                 //~2C26R~
//                    {   c=readchar(in);                          //~2C26R~
//                        if (sgf && c=='\n')                      //~2C26R~
//                        {   if (BufferN>1 && Buffer[BufferN-1]==' ') continue;//~2C26R~
//                            else c=' ';                          //~2C26R~
//                        }                                        //~2C26R~
//                    }                                            //~2C26R~
//                    else if (c==']') break;                      //~2C26R~
//                    store(c);                                    //~2C26R~
//                }                                                //~2C26R~
//                c=readnext(in); // prepare next argument         //~2C26R~
//                String s1;                                       //~2C26R~
//                if (BufferN>0) s1=new String(Buffer,0,BufferN);  //~2C26R~
//                else s1="";                                      //~2C26R~
//                if (!expand(a,s1)) a.addargument(s1);            //~2C26R~
//            }                                                    //~2C26R~
//            // no more arguments                                 //~2C26R~
//            n.addaction(a);                                      //~2C26R~
//            if (a.type().equals("B") || a.type().equals("W"))    //~2C26R~
//            {   n.number(n.number()+1);                          //~2C26R~
//            }                                                    //~2C26R~
//        } // end of actions has been found                       //~2C26R~
//        // append node                                           //~2C26R~
//        n.main(p);                                               //~2C26R~
//        TreeNode newp;                                           //~2C26R~
//        if (((Node)p.content()).actions()==null)                 //~2C26R~
//            p.content(n);                                        //~2C26R~
//        else                                                     //~2C26R~
//        {   p.addchild(newp=new TreeNode(n));                    //~2C26R~
//            n.main(p);                                           //~2C26R~
//            p=newp;                                              //~2C26R~
//            if (p.parentPos()!=null && p!=p.parentPos().firstChild())//~2C26R~
//                ((Node)p.content()).number(2);                   //~2C26R~
//        }                                                        //~2C26R~
//        return c;                                                //~2C26R~
//    }                                                            //~2C26R~

	/**
	Store c into the Buffer extending its length, if necessary.
	This is a fix by Bogdar Creanga from 2000-10-17 (Many Thanks)
	*/
//    private void store (char c)                                  //~2C26R~
//    {   try                                                      //~2C26R~
//        {   Buffer[BufferN]=c;                                   //~2C26R~
//            BufferN++;                                           //~2C26R~
//        }                                                        //~2C26R~
//        catch (ArrayIndexOutOfBoundsException e)                 //~2C26R~
//        {   int newLength = Buffer.length + maxbuffer;           //~2C26R~
//            char[] newBuffer = new char[newLength];              //~2C26R~
//            System.arraycopy(Buffer,0,newBuffer,0,Buffer.length);//~2C26R~
//            Buffer = newBuffer;                                  //~2C26R~
//            Buffer[BufferN++]=c;                                 //~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~

	// Check for the terrible compressed point list and expand into
	// single points
//    boolean expand (Action a, String s)                          //~2C26R~
//    {   String t=a.type();                                       //~2C26R~
//        if (!(t.equals("MA") || t.equals("SQ") || t.equals("TR") ||//~2C26R~
//             t.equals("CR") || t.equals("AW") || t.equals("AB") ||//~2C26R~
//              t.equals("AE") || t.equals("SL"))) return false;   //~2C26R~
//        if (s.length()!=5 || s.charAt(2)!=':') return false;     //~2C26R~
//        String s0=s.substring(0,2),s1=s.substring(3);            //~2C26R~
//        int i0=Field.i(s0),j0=Field.j(s0);                       //~2C26R~
//        int i1=Field.i(s1),j1=Field.j(s1);                       //~2C26R~
//        if (i1<i0 || j1<j0) return false;                        //~2C26R~
//        int i,j;                                                 //~2C26R~
//        for (i=i0; i<=i1; i++)                                   //~2C26R~
//            for (j=j0; j<=j1; j++)                               //~2C26R~
//            {   a.addargument(Field.string(i,j));                //~2C26R~
//            }                                                    //~2C26R~
//        return true;                                             //~2C26R~
//    }                                                            //~2C26R~

	/**
	Read the nodes belonging to a tree.
	this assumes that ( has been found.
	*/
//    void readnodes (TreeNode p, BufferedReader in)               //~2C26R~
//        throws IOException                                       //~2C26R~
//    {   char c=readnext(in);                                     //~2C26R~
//        while (true)                                             //~2C26R~
//        {   if (c==';')                                          //~2C26R~
//            {   c=readnode(p,in);                                //~2C26R~
//                if (p.haschildren()) p=p.lastChild();            //~2C26R~
//                continue;                                        //~2C26R~
//            }                                                    //~2C26R~
//            else if (c=='(')                                     //~2C26R~
//            {   readnodes(p,in);                                 //~2C26R~
//            }                                                    //~2C26R~
//            else if (c==')') break;                              //~2C26R~
//            c=readnext(in);                                      //~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~
	
	/**
	Read the tree from an BufferedReader in SGF format.
	The BoardInterfaces is only used to determine the "sgfcomments" parameter.
	*/	
//    public static Vector load (BufferedReader in, BoardInterface gf)//~2C26R~
//        throws IOException                                       //~2C26R~
//    {   Vector v=new Vector();                                   //~2C26R~
//        boolean linestart=true;                                  //~2C26R~
//        int c;                                                   //~2C26R~
//        reading : while (true)                                   //~2C26R~
//        {   SGFTree T=new SGFTree(new Node(1));                  //~2C26R~
//            while (true) // search for ( at line start           //~2C26R~
//            {   try                                              //~2C26R~
//                {   c=T.readchar(in);                            //~2C26R~
//                }                                                //~2C26R~
//                catch (IOException ex)                           //~2C26R~
//                {   break reading;                               //~2C26R~
//                }                                                //~2C26R~
//                if (linestart && c=='(') break;                  //~2C26R~
//                if (c=='\n') linestart=true;                     //~2C26R~
//                else linestart=false;                            //~2C26R~
//            }                                                    //~2C26R~
//            T.GF=gf;                                             //~2C26R~
//            T.readnodes(T.History,in); // read the nodes         //~2C26R~
//            v.addElement(T);                                     //~2C26R~
//        }                                                        //~2C26R~
//        return v;                                                //~2C26R~
//    }                                                            //~2C26R~

	/*
	XML Reader Stuff
	*/
	
	// Assumption on Boardsize of xml file, if <BoardSize> is not found
	static int BoardSize=19;	
	static String GameName="";
	
	/**
	Read all games from a tree.
	@return Vector of trees.
	*/
//    static Vector readnodes (XmlTree tree, BoardInterface gf)    //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   Vector v=new Vector();                                   //~2C26R~
//        Enumeration root=tree.getContent();                      //~2C26R~
//        while (root.hasMoreElements())                           //~2C26R~
//        {   tree=(XmlTree)root.nextElement();                    //~2C26R~
//            XmlTag tag=tree.getTag();                            //~2C26R~
//            if (tag instanceof XmlTagPI) continue;               //~2C26R~
//            testTag(tag,"Go");                                   //~2C26R~
//            Enumeration trees=tree.getContent();                 //~2C26R~
//            while (trees.hasMoreElements())                      //~2C26R~
//            {   tree=(XmlTree)trees.nextElement();               //~2C26R~
//                tag=tree.getTag();                               //~2C26R~
//                testTag(tag,"GoGame");                           //~2C26R~
//                if (tag.hasParam("name"))                        //~2C26R~
//                {   GameName=tag.getValue("name");               //~2C26R~
//                }                                                //~2C26R~
//                Enumeration e=tree.getContent();                 //~2C26R~
//                if (!e.hasMoreElements()) xmlMissing("Information");//~2C26R~
//                XmlTree information=(XmlTree)e.nextElement();    //~2C26R~
//                testTag(information.getTag(),"Information");     //~2C26R~
//                getBoardSize(information);                       //~2C26R~
//                SGFTree t=new SGFTree(new Node(1));              //~2C26R~
//                t.GF=gf;                                         //~2C26R~
//                TreeNode p=t.readnodes(e,null,tree,true,1);      //~2C26R~
//                if (p!=null) setInformation(p,information);      //~2C26R~
//                t.History=p;                                     //~2C26R~
//                if (p!=null) v.addElement(t);                    //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//        return v;                                                //~2C26R~
//    }                                                            //~2C26R~
	
//    public static void setInformation (TreeNode p, XmlTree information)//~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   Enumeration e=information.getContent();                  //~2C26R~
//        while (e.hasMoreElements())                              //~2C26R~
//        {   XmlTree tree=(XmlTree)e.nextElement();               //~2C26R~
//            XmlTag tag=tree.getTag();                            //~2C26R~
//            if (tag.name().equals("BoardSize"))                  //~2C26R~
//            {   p.addaction(new Action("SZ",""+BoardSize));      //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("BlackPlayer"))           //~2C26R~
//            {   p.addaction(new Action("PB",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("BlackRank"))             //~2C26R~
//            {   p.addaction(new Action("BR",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("WhitePlayer"))           //~2C26R~
//            {   p.addaction(new Action("PW",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("WhiteRank"))             //~2C26R~
//            {   p.addaction(new Action("WR",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Date"))                  //~2C26R~
//            {   p.addaction(new Action("DT",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Time"))                  //~2C26R~
//            {   p.addaction(new Action("TM",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Komi"))                  //~2C26R~
//            {   p.addaction(new Action("KM",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Result"))                //~2C26R~
//            {   p.addaction(new Action("RE",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Handicap"))              //~2C26R~
//            {   p.addaction(new Action("HA",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("User"))                  //~2C26R~
//            {   p.addaction(new Action("US",getText(tree)));     //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Copyright"))             //~2C26R~
//            {   p.addaction(new Action("CP",parseComment(tree)));//~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//        if (!GameName.equals(""))                                //~2C26R~
//            p.addaction(new Action("GN",GameName));              //~2C26R~
//    }                                                            //~2C26R~
	
//    public static String getText (XmlTree tree)                  //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   Enumeration e=tree.getContent();                         //~2C26R~
//        if (!e.hasMoreElements()) return "";                     //~2C26R~
//        XmlTree t=(XmlTree)e.nextElement();                      //~2C26R~
//        XmlTag tag=t.getTag();                                   //~2C26R~
//        if (!(tag instanceof XmlTagText) || e.hasMoreElements()) //~2C26R~
//            throw new XmlReaderException(                        //~2C26R~
//                "<"+tree.getTag().name()+"> has wrong content.");//~2C26R~
//        return ((XmlTagText)tag).getContent();                   //~2C26R~
//    }                                                            //~2C26R~
	
//    public static void getBoardSize (XmlTree tree)               //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   Enumeration e=tree.getContent();                         //~2C26R~
//        BoardSize=19;                                            //~2C26R~
//        while (e.hasMoreElements())                              //~2C26R~
//        {   tree=(XmlTree)e.nextElement();                       //~2C26R~
//            if (tree.getTag().name().equals("BoardSize"))        //~2C26R~
//            {   tree=tree.xmlFirstContent();                     //~2C26R~
//                XmlTag tag=tree.getTag();                        //~2C26R~
//                if (tag instanceof XmlTagText)                   //~2C26R~
//                {   try                                          //~2C26R~
//                    {   BoardSize=Integer.parseInt(              //~2C26R~
//                            ((XmlTagText)tag).getContent());     //~2C26R~
//                    }                                            //~2C26R~
//                    catch (Exception ex)                         //~2C26R~
//                    {   throw new XmlReaderException(            //~2C26R~
//                            "Illegal <BoardSize>");              //~2C26R~
//                    }                                            //~2C26R~
//                }                                                //~2C26R~
//                else                                             //~2C26R~
//                    throw new XmlReaderException(                //~2C26R~
//                            "Illegal <BoardSize>");              //~2C26R~
//                break;                                           //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~
	
//    TreeNode readnodes (Enumeration e, TreeNode p, XmlTree father, boolean main,//~2C26R~
//        int number)                                              //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   TreeNode ret=null;                                       //~2C26R~
//        while (e.hasMoreElements())                              //~2C26R~
//        {   XmlTree tree=(XmlTree)e.nextElement();               //~2C26R~
//            XmlTag tag=tree.getTag();                            //~2C26R~
//            if (tag.name().equals("Nodes"))                      //~2C26R~
//            {   return readnodes(tree.getContent(),p,father,main,number);//~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Node"))                  //~2C26R~
//            {   if (p!=null) number=((Node)p.content()).number();//~2C26R~
//                Node n=readnode(number,tree);                    //~2C26R~
//                n.main(main);                                    //~2C26R~
//                TreeNode newp=new TreeNode(n);                   //~2C26R~
//                if (p==null) ret=newp;                           //~2C26R~
//                if (p!=null) p.addchild(newp);                   //~2C26R~
//                p=newp;                                          //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("White"))                 //~2C26R~
//            {   if (p!=null) number=((Node)p.content()).number();//~2C26R~
//                Node n=new Node(number);                         //~2C26R~
//                try                                              //~2C26R~
//                {   n.addaction(new Action("W",xmlToSgf(tree))); //~2C26R~
//                    n.number(n.number()+1);                      //~2C26R~
//                    n.main(main);                                //~2C26R~
//                }                                                //~2C26R~
//                catch (XmlReaderException ey) { n.addaction(new Action("C","Pass")); }//~2C26R~
//                if (tag.hasParam("name"))                        //~2C26R~
//                {   n.addaction(new Action("N",tag.getValue("name")));//~2C26R~
//                }                                                //~2C26R~
//                if (tag.hasParam("timeleft"))                    //~2C26R~
//                {   n.addaction(new Action("WL",tag.getValue("timeleft")));//~2C26R~
//                }                                                //~2C26R~
//                TreeNode newp=new TreeNode(n);                   //~2C26R~
//                if (p==null) ret=newp;                           //~2C26R~
//                if (p!=null) p.addchild(newp);                   //~2C26R~
//                p=newp;                                          //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Black"))                 //~2C26R~
//            {   if (p!=null) number=((Node)p.content()).number();//~2C26R~
//                Node n=new Node(number);                         //~2C26R~
//                try                                              //~2C26R~
//                {   n.addaction(new Action("B",xmlToSgf(tree))); //~2C26R~
//                    n.number(n.number()+1);                      //~2C26R~
//                    n.main(main);                                //~2C26R~
//                }                                                //~2C26R~
//                catch (XmlReaderException ey) { n.addaction(new Action("C","Pass")); }//~2C26R~
//                if (tag.hasParam("name"))                        //~2C26R~
//                {   n.addaction(new Action("N",tag.getValue("name")));//~2C26R~
//                }                                                //~2C26R~
//                if (tag.hasParam("timeleft"))                    //~2C26R~
//                {   n.addaction(new Action("BL",tag.getValue("timeleft")));//~2C26R~
//                }                                                //~2C26R~
//                TreeNode newp=new TreeNode(n);                   //~2C26R~
//                if (p==null) ret=newp;                           //~2C26R~
//                if (p!=null) p.addchild(newp);                   //~2C26R~
//                p=newp;                                          //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Comment"))               //~2C26R~
//            {   if (p==null)                                     //~2C26R~
//                {   Node n=new Node(number);                     //~2C26R~
//                    n.main(main);                                //~2C26R~
//                    p=new TreeNode(n);                           //~2C26R~
//                    ret=p;                                       //~2C26R~
//                }                                                //~2C26R~
//                Node n=(Node)p.content();                        //~2C26R~
//                n.addaction(new Action("C",parseComment(tree))); //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Variation"))             //~2C26R~
//            {   TreeNode parent=(TreeNode)p.parent();            //~2C26R~
//                if (parent==null)                                //~2C26R~
//                    throw new XmlReaderException("Root node cannot have variation");//~2C26R~
//                TreeNode newp=readnodes(tree.getContent(),null,tree,false,1);//~2C26R~
//                parent.addchild(newp);                           //~2C26R~
//            }                                                    //~2C26R~
//            else                                                 //~2C26R~
//            {   throw new XmlReaderException(                    //~2C26R~
//                    "Illegal Node or Variation <"+tag.name()+">");//~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//        return ret;                                              //~2C26R~
//    }                                                            //~2C26R~
	
//    public Node readnode (int number, XmlTree tree)              //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   Node n=new Node(number);                                 //~2C26R~
//        XmlTag tag=tree.getTag();                                //~2C26R~
//        if (tag.hasParam("name"))                                //~2C26R~
//        {   n.addaction(new Action("N",tag.getValue("name")));   //~2C26R~
//        }                                                        //~2C26R~
//        if (tag.hasParam("blacktime"))                           //~2C26R~
//        {   n.addaction(new Action("BL",tag.getValue("blacktime")));//~2C26R~
//        }                                                        //~2C26R~
//        if (tag.hasParam("whitetime"))                           //~2C26R~
//        {   n.addaction(new Action("WL",tag.getValue("whitetime")));//~2C26R~
//        }                                                        //~2C26R~
//        Enumeration e=tree.getContent();                         //~2C26R~
//        while (e.hasMoreElements())                              //~2C26R~
//        {   XmlTree t=(XmlTree)e.nextElement();                  //~2C26R~
//            tag=t.getTag();                                      //~2C26R~
//            if (tag.name().equals("Black"))                      //~2C26R~
//            {   try                                              //~2C26R~
//                {   n.addaction(new Action("B",xmlToSgf(t)));    //~2C26R~
//                    n.number(n.number()+1);                      //~2C26R~
//                }                                                //~2C26R~
//                catch (XmlReaderException ey) {}                 //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("White"))                 //~2C26R~
//            {   try                                              //~2C26R~
//                {   n.addaction(new Action("W",xmlToSgf(t)));    //~2C26R~
//                    n.number(n.number()+1);                      //~2C26R~
//                }                                                //~2C26R~
//                catch (XmlReaderException ey) {}                 //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("AddBlack"))              //~2C26R~
//            {   n.addaction(new Action("AB",xmlToSgf(t)));       //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("AddWhite"))              //~2C26R~
//            {   n.addaction(new Action("AW",xmlToSgf(t)));       //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Delete"))                //~2C26R~
//            {   n.expandaction(new Action("AE",xmlToSgf(t)));    //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Mark"))                  //~2C26R~
//            {   if (tag.hasParam("type"))                        //~2C26R~
//                {   String s=tag.getValue("type");               //~2C26R~
//                    if (s.equals("triangle"))                    //~2C26R~
//                    {   n.expandaction(new Action("TR",xmlToSgf(t)));//~2C26R~
//                    }                                            //~2C26R~
//                    else if (s.equals("square"))                 //~2C26R~
//                    {   n.expandaction(new Action("SQ",xmlToSgf(t)));//~2C26R~
//                    }                                            //~2C26R~
//                    else if (s.equals("circle"))                 //~2C26R~
//                    {   n.expandaction(new Action("CR",xmlToSgf(t)));//~2C26R~
//                    }                                            //~2C26R~
//                }                                                //~2C26R~
//                else if (tag.hasParam("label"))                  //~2C26R~
//                {   String s=tag.getValue("label");              //~2C26R~
//                    n.expandaction(new Action("LB",              //~2C26R~
//                        xmlToSgf(t)+":"+s));                     //~2C26R~
//                }                                                //~2C26R~
//                else if (tag.hasParam("territory"))              //~2C26R~
//                {   String s=tag.getValue("territory");          //~2C26R~
//                    if (s.equals("white"))                       //~2C26R~
//                    {   n.expandaction(new Action("TW",xmlToSgf(t)));//~2C26R~
//                    }                                            //~2C26R~
//                    else if (s.equals("black"))                  //~2C26R~
//                    {   n.expandaction(new Action("TB",xmlToSgf(t)));//~2C26R~
//                    }                                            //~2C26R~
//                }                                                //~2C26R~
//                else n.expandaction(new MarkAction(xmlToSgf(t),GF));//~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("BlackTimeLeft"))         //~2C26R~
//            {   n.addaction(new Action("BL",getText(t)));        //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("WhiteTimeLeft"))         //~2C26R~
//            {   n.addaction(new Action("WL",getText(t)));        //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("Comment"))               //~2C26R~
//            {   n.addaction(new Action("C",parseComment(t)));    //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag.name().equals("SGF"))                   //~2C26R~
//            {   if (!tag.hasParam("type"))                       //~2C26R~
//                    throw new XmlReaderException("Illegal <SGF> tag.");//~2C26R~
//                Action a;                                        //~2C26R~
//                if (tag.getValue("type").equals("M")) a=new MarkAction(GF);//~2C26R~
//                else a=new Action(tag.getValue("type"));         //~2C26R~
//                Enumeration eh=t.getContent();                   //~2C26R~
//                while (eh.hasMoreElements())                     //~2C26R~
//                {   XmlTree th=(XmlTree)eh.nextElement();        //~2C26R~
//                    XmlTag tagh=th.getTag();                     //~2C26R~
//                    if (!tagh.name().equals("Arg"))              //~2C26R~
//                        throw new XmlReaderException("Illegal <SGF> tag.");//~2C26R~
//                    if (!th.isText())                            //~2C26R~
//                        throw new XmlReaderException("Illegal <SGF> tag.");//~2C26R~
//                    else a.addargument(th.getText());            //~2C26R~
//                }                                                //~2C26R~
//                n.addaction(a);                                  //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//        return n;                                                //~2C26R~
//    }                                                            //~2C26R~
	
//    public static String parseComment (XmlTree t)                //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   StringBuffer s=new StringBuffer();                       //~2C26R~
//        Enumeration e=t.getContent();                            //~2C26R~
//        while (e.hasMoreElements())                              //~2C26R~
//        {   XmlTree tree=(XmlTree)e.nextElement();               //~2C26R~
//            XmlTag tag=tree.getTag();                            //~2C26R~
//            if (tag.name().equals("P"))                          //~2C26R~
//            {   if (!tree.haschildren()) s.append("\n");         //~2C26R~
//                else                                             //~2C26R~
//                {   XmlTree h=tree.xmlFirstContent();            //~2C26R~
//                    String k=((XmlTagText)h.getTag()).getContent();//~2C26R~
//                    k=k.replace('\n',' ');                       //~2C26R~
//                    StringParser p=new StringParser(k);          //~2C26R~
//                    Vector v=p.wraplines(1000);                  //~2C26R~
//                    for (int i=0; i<v.size(); i++)               //~2C26R~
//                    {   s.append((String)v.elementAt(i));        //~2C26R~
//                        s.append("\n");                          //~2C26R~
//                    }                                            //~2C26R~
//                }                                                //~2C26R~
//            }                                                    //~2C26R~
//            else if (tag instanceof XmlTagText)                  //~2C26R~
//            {   String k=((XmlTagText)tag).getContent();         //~2C26R~
//                    k=k.replace('\n',' ');                       //~2C26R~
//                    StringParser p=new StringParser(k);          //~2C26R~
//                    Vector v=p.wraplines(1000);                  //~2C26R~
//                    for (int i=0; i<v.size(); i++)               //~2C26R~
//                    {   s.append((String)v.elementAt(i));        //~2C26R~
//                        s.append("\n");                          //~2C26R~
//                    }                                            //~2C26R~
//            }                                                    //~2C26R~
//            else                                                 //~2C26R~
//                throw new XmlReaderException("<"+tag.name()+"> not proper here.");//~2C26R~
//        }                                                        //~2C26R~
//        return s.toString();                                     //~2C26R~
//    }                                                            //~2C26R~
	
//    public String xmlToSgf (XmlTree tree)                        //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   XmlTag tag=tree.getTag();                                //~2C26R~
//        if (tag.hasParam("at"))                                  //~2C26R~
//        {   return xmlToSgf(tag.getValue("at"));                 //~2C26R~
//        }                                                        //~2C26R~
//        Enumeration e=tree.getContent();                         //~2C26R~
//        if (!e.hasMoreElements())                                //~2C26R~
//            throw new XmlReaderException("Missing board position.");//~2C26R~
//        tag=((XmlTree)e.nextElement()).getTag();                 //~2C26R~
//        if (tag instanceof XmlTagText)                           //~2C26R~
//        {   String pos=((XmlTagText)tag).getContent();           //~2C26R~
//            return xmlToSgf(pos);                                //~2C26R~
//        }                                                        //~2C26R~
//        else if (tag.name().equals("at"))                        //~2C26R~
//        {   String pos=((XmlTagText)tag).getContent();           //~2C26R~
//            return xmlToSgf(pos);                                //~2C26R~
//        }                                                        //~2C26R~
//        else                                                     //~2C26R~
//            throw new XmlReaderException(tag.name()              //~2C26R~
//                +" contains wrong board position.");             //~2C26R~
//    }                                                            //~2C26R~
	
//    public String xmlToSgf (String pos)                          //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   if (pos.length()<2) wrongBoardPosition(pos);             //~2C26R~
//        int n=pos.indexOf(",");                                  //~2C26R~
//        if (n>0 && n<pos.length())                               //~2C26R~
//        {   String s1=pos.substring(0,n),s2=pos.substring(n+1);  //~2C26R~
//            try                                                  //~2C26R~
//            {   int i=Integer.parseInt(s1)-1;                    //~2C26R~
//                int j=Integer.parseInt(s2);                      //~2C26R~
//                j=BoardSize-j;                                   //~2C26R~
//                if (i<0 || i>=BoardSize || j<0 || j>=BoardSize)  //~2C26R~
//                    wrongBoardPosition(pos);                     //~2C26R~
//                return Field.string(i,j);                        //~2C26R~
//            }                                                    //~2C26R~
//            catch (Exception ex)                                 //~2C26R~
//            {   wrongBoardPosition(pos);                         //~2C26R~
//            }                                                    //~2C26R~
//        }                                                        //~2C26R~
//        char c=Character.toUpperCase(pos.charAt(0));             //~2C26R~
//        if (c>='J') c--;                                         //~2C26R~
//        int i=c-'A';                                             //~2C26R~
//        int j=0;                                                 //~2C26R~
//        try                                                      //~2C26R~
//        {   j=Integer.parseInt(pos.substring(1));                //~2C26R~
//        }                                                        //~2C26R~
//        catch (Exception ex)                                     //~2C26R~
//        {   wrongBoardPosition(pos);                             //~2C26R~
//        }                                                        //~2C26R~
//        j=BoardSize-j;                                           //~2C26R~
//        if (i<0 || i>=BoardSize || j<0 || j>=BoardSize)          //~2C26R~
//            wrongBoardPosition(pos);                             //~2C26R~
//        return Field.string(i,j);                                //~2C26R~
//    }                                                            //~2C26R~
	
//    public void wrongBoardPosition (String s)                    //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   throw new XmlReaderException("Wrong Board Position "+s); //~2C26R~
//    }                                                            //~2C26R~
	
//    public static void xmlMissing (String s)                     //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   throw new XmlReaderException("Missing <"+s+">");         //~2C26R~
//    }                                                            //~2C26R~
	
//    public static void testTag (XmlTag tag, String name)         //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   if (!tag.name().equals(name))                            //~2C26R~
//        {   throw new XmlReaderException(                        //~2C26R~
//                "<"+name+"> expected instead of <"+tag.name()+">");//~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~

	/**
	Read a number of trees from an XML file.
	*/
//    public static Vector load (XmlReader xml, BoardInterface gf) //~2C26R~
//        throws XmlReaderException                                //~2C26R~
//    {   XmlTree t=xml.scan();                                    //~2C26R~
//        if (t==null) throw new XmlReaderException("Illegal file format");//~2C26R~
//        Vector v=readnodes(t,gf);                                //~2C26R~
//        return v;                                                //~2C26R~
//    }                                                            //~2C26R~

	/**
	Print the tree to the specified PrintWriter.
	@param p the subtree to be printed
	*/
//    void printtree (TreeNode p, PrintWriter o)                   //~2C26R~
//    {   o.println("(");                                          //~2C26R~
//        while (true)                                             //~2C26R~
//        {   p.node().print(o);                                   //~2C26R~
//            if (!p.haschildren()) break;                         //~2C26R~
//            if (p.lastChild()!=p.firstChild())                   //~2C26R~
//            {   ListElement e=p.children().first();              //~2C26R~
//                while (e!=null)                                  //~2C26R~
//                {   printtree((TreeNode)e.content(),o);          //~2C26R~
//                    e=e.next();                                  //~2C26R~
//                }                                                //~2C26R~
//                break;                                           //~2C26R~
//            }                                                    //~2C26R~
//            p=p.firstChild();                                    //~2C26R~
//        }                                                        //~2C26R~
//        o.println(")");                                          //~2C26R~
//    }                                                            //~2C26R~

	/**
	Print the tree to the specified PrintWriter.
	@param p the subtree to be printed
	*/
//    void printtree (TreeNode p, XmlWriter xml, int size, boolean top)//~2C26R~
//    {   if (top)                                                 //~2C26R~
//        {   String s=p.getaction("GN");                          //~2C26R~
//            if (s!=null && !s.equals(""))                        //~2C26R~
//                xml.startTagNewLine("GoGame","name",s);          //~2C26R~
//            else                                                 //~2C26R~
//                xml.startTagNewLine("GoGame");                   //~2C26R~
//            xml.startTagNewLine("Information");                  //~2C26R~
//            printInformation(xml,p,"AP","Application");          //~2C26R~
//            printInformation(xml,p,"SZ","BoardSize");            //~2C26R~
//            printInformation(xml,p,"PB","BlackPlayer");          //~2C26R~
//            printInformation(xml,p,"BR","BlackRank");            //~2C26R~
//            printInformation(xml,p,"PW","WhitePlayer");          //~2C26R~
//            printInformation(xml,p,"WR","WhiteRank");            //~2C26R~
//            printInformation(xml,p,"DT","Date");                 //~2C26R~
//            printInformation(xml,p,"TM","Time");                 //~2C26R~
//            printInformation(xml,p,"KM","Komi");                 //~2C26R~
//            printInformation(xml,p,"RE","Result");               //~2C26R~
//            printInformation(xml,p,"HA","Handicap");             //~2C26R~
//            printInformation(xml,p,"US","User");                 //~2C26R~
//            printInformationText(xml,p,"CP","Copyright");        //~2C26R~
//            xml.endTagNewLine("Information");                    //~2C26R~
//        }                                                        //~2C26R~
//        else xml.startTagNewLine("Variation");                   //~2C26R~
//        if (top) xml.startTagNewLine("Nodes");                   //~2C26R~
//        while (true)                                             //~2C26R~
//        {   p.node().print(xml,size);                            //~2C26R~
//            if (!p.haschildren()) break;                         //~2C26R~
//            if (p.lastChild()!=p.firstChild())                   //~2C26R~
//            {   ListElement e=p.children().first();              //~2C26R~
//                p=p.firstChild();                                //~2C26R~
//                p.node().print(xml,size);                        //~2C26R~
//                e=e.next();                                      //~2C26R~
//                while (e!=null)                                  //~2C26R~
//                {   printtree((TreeNode)e.content(),xml,size,false);//~2C26R~
//                    e=e.next();                                  //~2C26R~
//                }                                                //~2C26R~
//                if (!p.haschildren()) break;                     //~2C26R~
//            }                                                    //~2C26R~
//            p=p.firstChild();                                    //~2C26R~
//        }                                                        //~2C26R~
//        if (top) xml.endTagNewLine("Nodes");                     //~2C26R~
//        if (top) xml.endTagNewLine("GoGame");                    //~2C26R~
//        else xml.endTagNewLine("Variation");                     //~2C26R~
//    }                                                            //~2C26R~

//    public void printInformation (XmlWriter xml, TreeNode p,     //~2C26R~
//        String tag, String xmltag)                               //~2C26R~
//    {   String s=p.getaction(tag);                               //~2C26R~
//        if (s!=null && !s.equals(""))                            //~2C26R~
//            xml.printTagNewLine(xmltag,s);                       //~2C26R~
//    }                                                            //~2C26R~

//    public void printInformationText (XmlWriter xml, TreeNode p, //~2C26R~
//        String tag, String xmltag)                               //~2C26R~
//    {   String s=p.getaction(tag);                               //~2C26R~
//        if (s!=null && !s.equals(""))                            //~2C26R~
//        {   xml.startTagNewLine(xmltag);                         //~2C26R~
//            xml.printParagraphs(s,60);                           //~2C26R~
//            xml.endTagNewLine(xmltag);                           //~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~

	/**
	Print this tree to the PrintWriter starting at the root node.
	*/
//    public void print (PrintWriter o)                            //~2C26R~
//    {   printtree(History,o);                                    //~2C26R~
//    }                                                            //~2C26R~
//                                                                 //~2C26R~
//    public void printXML (XmlWriter xml)                         //~2C26R~
//    {   printtree(History,xml,getSize(),true);                   //~2C26R~
//    }                                                            //~2C26R~
//                                                                 //~2C26R~
//    public int getSize ()                                        //~2C26R~
//    {   try                                                      //~2C26R~
//        {   return Integer.parseInt(History.getaction("SZ"));    //~2C26R~
//        }                                                        //~2C26R~
//        catch (Exception e)                                      //~2C26R~
//        {   return 19;                                           //~2C26R~
//        }                                                        //~2C26R~
//    }                                                            //~2C26R~
}
