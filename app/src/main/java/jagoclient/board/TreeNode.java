//*CID://+dateR~:                             update#=    3;       //~2C26I~
package jagoclient.board;

import rene.util.list.Tree;

/**
This is a child class of Tree, with some help functions for
the content type Node.
@see jagoclient.list.Tree
@see jagoclient.board.Node
*/

public class TreeNode extends Tree
{	/** initialize with an empty node with the specified number */
	public TreeNode (int number)
	{	super(new Node(number));
	}
	/** initialize with a given Node */
	public TreeNode (Node n)
	{	super(n);
	}
	public Node node () { return ((Node)content()); }
	/**
	Set the action type in the node to the string s.
	@param flag determines, if the action is to be added, even of s is emtpy.
	*/
//  public void setaction (String type, String s, boolean flag)    //~3214R~
    public void setaction (String type, String s, boolean flag,int Ppiece)//~3214I~
//  {	node().setaction(type,s,flag);                             //~3214R~
    {	node().setaction(type,s,flag,Ppiece);                      //~3214I~
	}
//  public void setaction (String type, String s)                  //+3214R~
    public void setaction (String type, String s,int Ppiece)       //+3214I~
//  {	node().setaction(type,s);                                  //+3214R~
    {	node().setaction(type,s,Ppiece);                           //+3214I~
	}
	/** add this action to the node */
	public void addaction (Action a)
	{	node().addaction(a);
	}
	/** get the value of the action of this type */
	public String getaction (String type)
	{	return node().getaction(type);
	}
	
	/** @return true if it is a main node */
	public boolean isMain ()
	{	return node().main();
	}
	/** @return true if it the last node in the main tree */
	public boolean isLastMain ()
	{	return !haschildren() && isMain();
	}
	/** set the main flag in the node */
	public void main (boolean flag) { node().main(flag); }
	
	public TreeNode parentPos () { return (TreeNode)parent(); }
	public TreeNode firstChild () { return (TreeNode)firstchild(); }
	public TreeNode lastChild () { return (TreeNode)lastchild(); }
}
