//*CID://+v1A0R~:                             update#=    6;       //~@@@@I~//~v1A0R~
//********************************************************************//~v1A0I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//********************************************************************//~v1A0I~
package jagoclient.board;

//import java.io.PrintWriter;
//import java.util.Vector;

import rene.util.list.ListClass;
import rene.util.list.ListElement;
//import rene.util.parser.StringParser;
//import rene.util.xml.XmlWriter;                                  //~@@@@R~

/** 
Has a type and arguments (as in SGF, e.g. B[ih] of type "B" and
Methods include the printing on a PrintWriter.
*/
public class Action
{	String Type; // the type
	ListClass Arguments; // the list of argument strings
    int Piece;                                                     //~v1A0I~
	
	/**
	Initialize with type only
	*/
	public Action (String s)
	{	Type=s;
		Arguments=new ListClass();
	}

	/**
	Initialize with type and one argument to that type tag.
	*/
//    public Action (String s, String arg)                         //+v1A0R~
//    {   Type=s;                                                  //+v1A0R~
//        Arguments=new ListClass();                               //+v1A0R~
//        addargument(arg);                                        //+v1A0R~
//    }                                                            //+v1A0R~
    public Action (String s, String arg,int Ppiece)                //~v1A0I~
    {   Type=s;                                                    //~v1A0I~
    	Piece=Ppiece;                                              //~v1A0I~
        Arguments=new ListClass();                                 //~v1A0I~
        addargument(arg);                                          //~v1A0I~
    }                                                              //~v1A0I~
	
	public void addargument (String s)
	// add an argument ot the list (at end)
	{	Arguments.append(new ListElement(s));
	}
	
	public void toggleargument (String s)
	// add an argument ot the list (at end)
	{	ListElement ap=Arguments.first();
		while (ap!=null)
		{	String t=(String)ap.content();
			if (t.equals(s))
			{	Arguments.remove(ap);
				return;
			}
			ap=ap.next();
		}
		Arguments.append(new ListElement(s));
	}

	/** Find an argument */	
	public boolean contains (String s)
	{	ListElement ap=Arguments.first();
		while (ap!=null)
		{	String t=(String)ap.content();
			if (t.equals(s)) return true;
			ap=ap.next();
		}
		return false;
	}
	
//    public void print (PrintWriter o)                            //~@@@@R~
//    // print the action                                          //~@@@@R~
//    {   if (Arguments.first()==null ||                           //~@@@@R~
//            (Arguments.first()==Arguments.last() &&              //~@@@@R~
//            ((String)Arguments.first().content()).equals("")))   //~@@@@R~
//            return;                                              //~@@@@R~
//        o.println();                                             //~@@@@R~
//        o.print(Type);                                           //~@@@@R~
//        ListElement ap=Arguments.first();                        //~@@@@R~
//        while (ap!=null)                                         //~@@@@R~
//        {   o.print("[");                                        //~@@@@R~
//            String s=(String)ap.content();                       //~@@@@R~
//            StringParser p=new StringParser(s);                  //~@@@@R~
//            Vector v=p.wrapwords(60);                            //~@@@@R~
//            for (int i=0; i<v.size(); i++)                       //~@@@@R~
//            {   s=(String)v.elementAt(i);                        //~@@@@R~
//                if (i>0) o.println();                            //~@@@@R~
//                int k=s.indexOf(']');                            //~@@@@R~
//                while (k>=0)                                     //~@@@@R~
//                {   if (k>0) o.print(s.substring(0,k));          //~@@@@R~
//                    o.print("\\]");                              //~@@@@R~
//                    s=s.substring(k+1);                          //~@@@@R~
//                    k=s.indexOf(']');                            //~@@@@R~
//                }                                                //~@@@@R~
//                o.print(s);                                      //~@@@@R~
//            }                                                    //~@@@@R~
//            o.print("]");                                        //~@@@@R~
//            ap=ap.next();                                        //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
	
	/**
	Print the node content in XML form.
	*/
//    public void print (XmlWriter xml, int size, int number)      //~@@@@R~
//    {   if (Type.equals("C"))                                    //~@@@@R~
//        {   xml.startTagNewLine("Comment");                      //~@@@@R~
//            printTextArgument(xml);                              //~@@@@R~
//            xml.endTagNewLine("Comment");                        //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("GN")                               //~@@@@R~
//            || Type.equals("AP")                                 //~@@@@R~
//            || Type.equals("FF")                                 //~@@@@R~
//            || Type.equals("GM")                                 //~@@@@R~
//            || Type.equals("N")                                  //~@@@@R~
//            || Type.equals("SZ")                                 //~@@@@R~
//            || Type.equals("PB")                                 //~@@@@R~
//            || Type.equals("BR")                                 //~@@@@R~
//            || Type.equals("PW")                                 //~@@@@R~
//            || Type.equals("WR")                                 //~@@@@R~
//            || Type.equals("HA")                                 //~@@@@R~
//            || Type.equals("KM")                                 //~@@@@R~
//            || Type.equals("RE")                                 //~@@@@R~
//            || Type.equals("DT")                                 //~@@@@R~
//            || Type.equals("TM")                                 //~@@@@R~
//            || Type.equals("US")                                 //~@@@@R~
//            || Type.equals("WL")                                 //~@@@@R~
//            || Type.equals("BL")                                 //~@@@@R~
//            || Type.equals("CP")                                 //~@@@@R~
//            )                                                    //~@@@@R~
//        {                                                        //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("B"))                               //~@@@@R~
//        {   xml.startTagStart("Black");                          //~@@@@R~
//            xml.printArg("number",""+number);                    //~@@@@R~
//            xml.printArg("at",getXMLMove(size));                 //~@@@@R~
//            xml.finishTagNewLine();                              //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("W"))                               //~@@@@R~
//        {   xml.startTagStart("White");                          //~@@@@R~
//            xml.printArg("number",""+number);                    //~@@@@R~
//            xml.printArg("at",getXMLMove(size));                 //~@@@@R~
//            xml.finishTagNewLine();                              //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("AB"))                              //~@@@@R~
//        {   printAllFields(xml,size,"AddBlack");                 //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("AW"))                              //~@@@@R~
//        {   printAllFields(xml,size,"AddWhite");                 //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("AE"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Delete");                   //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("MA"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Mark");                     //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("M"))                               //~@@@@R~
//        {   printAllFields(xml,size,"Mark");                     //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("SQ"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Mark","type","square");     //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("CR"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Mark","type","circle");     //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("TR"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Mark","type","triangle");   //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("TB"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Mark","territory","black"); //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("TW"))                              //~@@@@R~
//        {   printAllFields(xml,size,"Mark","territory","white"); //~@@@@R~
//        }                                                        //~@@@@R~
//        else if (Type.equals("LB"))                              //~@@@@R~
//        {   printAllSpecialFields(xml,size,"Mark","label");      //~@@@@R~
//        }                                                        //~@@@@R~
//        else                                                     //~@@@@R~
//        {   xml.startTag("SGF","type",Type);                     //~@@@@R~
//            ListElement ap=Arguments.first();                    //~@@@@R~
//            while (ap!=null)                                     //~@@@@R~
//            {   xml.startTag("Arg");                             //~@@@@R~
//                String s=(String)ap.content();                   //~@@@@R~
//                StringParser p=new StringParser(s);              //~@@@@R~
//                Vector v=p.wrapwords(60);                        //~@@@@R~
//                for (int i=0; i<v.size(); i++)                   //~@@@@R~
//                {   s=(String)v.elementAt(i);                    //~@@@@R~
//                    if (i>0) xml.println();                      //~@@@@R~
//                    xml.print(s);                                //~@@@@R~
//                }                                                //~@@@@R~
//                ap=ap.next();                                    //~@@@@R~
//                xml.endTag("Arg");                               //~@@@@R~
//            }                                                    //~@@@@R~
//            xml.endTagNewLine("SGF");                            //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Print the node content of a move in XML form and take care of times
	and names.
	*/
//    public void printMove (XmlWriter xml, int size, int number, Node n)//~@@@@R~
//    {   String s="";                                             //~@@@@R~
//        if (Type.equals("B")) s="Black";                         //~@@@@R~
//        else if (Type.equals("W")) s="White";                    //~@@@@R~
//        else return;                                             //~@@@@R~
//        xml.startTagStart(s);                                    //~@@@@R~
//        xml.printArg("number",""+number);                        //~@@@@R~
//        if (n.contains("N")) xml.printArg("name",n.getaction("N"));//~@@@@R~
//        if (s.equals("Black") && n.contains("BL"))               //~@@@@R~
//            xml.printArg("timeleft",n.getaction("BL"));          //~@@@@R~
//        if (s.equals("White") && n.contains("WL"))               //~@@@@R~
//            xml.printArg("timeleft",n.getaction("WL"));          //~@@@@R~
//        xml.printArg("at",getXMLMove(size));                     //~@@@@R~
//        xml.finishTagNewLine();                                  //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Test, if this action contains printed information
	*/
//    public boolean isRelevant ()                                 //~@@@@R~
//    {   if (Type.equals("GN")                                    //~@@@@R~
//            || Type.equals("AP")                                 //~@@@@R~
//            || Type.equals("FF")                                 //~@@@@R~
//            || Type.equals("GM")                                 //~@@@@R~
//            || Type.equals("N")                                  //~@@@@R~
//            || Type.equals("SZ")                                 //~@@@@R~
//            || Type.equals("PB")                                 //~@@@@R~
//            || Type.equals("BR")                                 //~@@@@R~
//            || Type.equals("PW")                                 //~@@@@R~
//            || Type.equals("WR")                                 //~@@@@R~
//            || Type.equals("HA")                                 //~@@@@R~
//            || Type.equals("KM")                                 //~@@@@R~
//            || Type.equals("RE")                                 //~@@@@R~
//            || Type.equals("DT")                                 //~@@@@R~
//            || Type.equals("TM")                                 //~@@@@R~
//            || Type.equals("US")                                 //~@@@@R~
//            || Type.equals("CP")                                 //~@@@@R~
//            || Type.equals("BL")                                 //~@@@@R~
//            || Type.equals("WL")                                 //~@@@@R~
//            || Type.equals("C")                                  //~@@@@R~
//            )                                                    //~@@@@R~
//        return false;                                            //~@@@@R~
//        else return true;                                        //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	Print all arguments as field positions with the specified tag.
	*/
//    public void printAllFields (XmlWriter xml, int size, String tag)//~@@@@R~
//    {   ListElement ap=Arguments.first();                        //~@@@@R~
//        while (ap!=null)                                         //~@@@@R~
//        {   String s=(String)ap.content();                       //~@@@@R~
//            xml.startTagStart(tag);                              //~@@@@R~
//            xml.printArg("at",getXMLMove(ap,size));              //~@@@@R~
//            xml.finishTagNewLine();                              //~@@@@R~
//            ap=ap.next();                                        //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~
	
//    public void printAllFields (XmlWriter xml, int size, String tag,//~@@@@R~
//        String argument, String value)                           //~@@@@R~
//    {   ListElement ap=Arguments.first();                        //~@@@@R~
//        while (ap!=null)                                         //~@@@@R~
//        {   String s=(String)ap.content();                       //~@@@@R~
//            xml.startTagStart(tag);                              //~@@@@R~
//            xml.printArg(argument,value);                        //~@@@@R~
//            xml.printArg("at",getXMLMove(ap,size));              //~@@@@R~
//            xml.finishTagNewLine();                              //~@@@@R~
//            ap=ap.next();                                        //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~

//    public void printAllSpecialFields (XmlWriter xml, int size, String tag,//~@@@@R~
//        String argument)                                         //~@@@@R~
//    {   ListElement ap=Arguments.first();                        //~@@@@R~
//        while (ap!=null)                                         //~@@@@R~
//        {   String s=(String)ap.content();                       //~@@@@R~
//            StringParser p=new StringParser(s);                  //~@@@@R~
//            s=p.parseword(':');                                  //~@@@@R~
//            p.skip(":");                                         //~@@@@R~
//            String value=p.parseword();                          //~@@@@R~
//            xml.startTagStart(tag);                              //~@@@@R~
//            xml.printArg(argument,value);                        //~@@@@R~
//            xml.printArg("at",getXMLMove(ap,size));              //~@@@@R~
//            xml.finishTagNewLine();                              //~@@@@R~
//            ap=ap.next();                                        //~@@@@R~
//        }                                                        //~@@@@R~
//    }                                                            //~@@@@R~

	/**
	@return The readable coordinate version (Q16) of a move,
	stored in first argument.
	*/
//    public String getXMLMove (ListElement ap, int size)          //~@@@@R~
//    {   if (ap==null) return "";                                 //~@@@@R~
//        String s=(String)ap.content();                           //~@@@@R~
//        if (s==null) return "";                                  //~@@@@R~
//        int i=Field.i(s),j=Field.j(s);                           //~@@@@R~
//        if (i<0 || i>=size || j<0 || j>=size) return "";         //~@@@@R~
//        return Field.coordinate(Field.i(s),Field.j(s),size);     //~@@@@R~
//    }                                                            //~@@@@R~
	
//    public String getXMLMove (int size)                          //~@@@@R~
//    {   ListElement ap=Arguments.first();                        //~@@@@R~
//        return getXMLMove(ap,size);                              //~@@@@R~
//    }                                                            //~@@@@R~

//    public void printTextArgument (XmlWriter xml)                //~@@@@R~
//    {   ListElement ap=Arguments.first();                        //~@@@@R~
//        if (ap==null) return;                                    //~@@@@R~
//        xml.printParagraphs((String)ap.content(),60);            //~@@@@R~
//    }                                                            //~@@@@R~

	// modifiers
	public void type (String s) { Type=s; }

	// access methods:
	public String type () { return Type; }
	public ListElement arguments () { return Arguments.first(); }
	public String argument ()
	{	if (arguments()==null) return "";
		else return (String)arguments().content();
	}
}
