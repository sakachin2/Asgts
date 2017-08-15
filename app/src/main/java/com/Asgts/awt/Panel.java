package com.Asgts.awt;                                            //~1112I~//~2C26R~//+3213R~

import jagoclient.Dump;
//import jagoclient.gui.MyLabel;                                   //~2C27R~
                                                                   //~1112I~
public class Panel extends Container                               //~1116R~
{                                                                  //~1112I~
	private Font font=null;                                                //~1214I~//~1401R~
    private Label colorExampleLabel=null;                                //~1401I~
    public Panel()                                                 //~1118R~
    {                                                              //~1118I~
        super();                                                   //~1118I~
    }                                                              //~1118I~
//***************                                                  //~1216I~
//    public void add(MyLabel Plabel) //for ColorEdit                //~1401I~//~2C27R~
//    {                                                              //~1401I~//~2C27R~
//    //******************                                           //~1401I~//~2C27R~
//        super.add(Plabel);                                         //~1401I~//~2C27R~
//        if (Plabel.label!=null &&                                  //~1401I~//~2C27R~
//            Plabel.label.equals(Global.resourceString("Your_Color")))//~1401I~//~2C27R~
//            colorExampleLabel=Plabel;                              //~1401I~//~2C27R~
//    }                                                              //~1401I~//~2C27R~
//***************                                                  //~1217I~
    public Dimension getSize()  //for BigLabel<--BigTimerLabel<--ConnectedGoFrame//~1414R~
    {                                                              //~1414R~
        if (Dump.Y) Dump.println("Panel getSize return (0,0)");    //~1511R~
        return new Dimension(0,0);                                 //~1414R~
    }                                                              //~1414R~

    public void setBackground(Color Pcolor)//for ColorEdit Example color field//~1401I~
    {                                                              //~1401I~
	    if (colorExampleLabel!=null                                //~1401I~
	    &&  colorExampleLabel.textView!=null)                      //~1401I~
        	setBackground(colorExampleLabel.textView,Pcolor);   //by Component//~1401I~
        else                                                       //~1401I~
            super.setBackground(Pcolor);                           //~1401I~
    }                                                              //~1401I~
    public void setLayout(Component Ppanel3d)                      //~1214I~
    {                                                              //~1214I~
    }                                                              //~1214I~
//    public void setLayout(GridBagLayout P1)                        //~1215I~//~2C27R~
//    {                                                              //~1215I~//~2C27R~
//    }                                                              //~1215I~//~2C27R~
    public Graphics getGraphics()                                  //~1214I~
    {                                                              //~1214I~
    	return new Graphics();                                     //~1214I~
    }                                                              //~1214I~
    public void repaint()	//rene.lister.ListerPanel              //~1214I~
    {                                                              //~1214I~
    }                                                              //~1214I~
    public Image createImage(int Pw,int Ph)	//rene.lister.ListerPanel//~1214I~
    {                                                              //~1214I~
		return new Image(Pw,Ph);              //~1117R~//~1214I~
    }                                                              //~1214I~
    public Font getFont()	//rene.lister.ListerPanel              //~1214I~
    {                                                              //~1214I~
		return font;                                               //~1214R~
    }                                                              //~1214I~
    public void setFont(Font Pfont)	//rene.lister.ListerPanel      //~1214I~
    {                                                              //~1214I~
		font=Pfont;                                                //~1214I~
    }                                                              //~1214I~
    public FontMetrics getFontMetrics(Font Pfont)           //~1117M~//~1214I~
    {                                                              //~1214I~
    	return Canvas.getFontMetrics(Pfont);                       //~1214I~
    }                                                              //~1214I~
}//class                                                           //~1112I~
