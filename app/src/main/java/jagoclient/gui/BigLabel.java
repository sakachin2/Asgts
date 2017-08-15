//*CID://+1A07R~:                             update#=    6;       //+1A07R~
//*********************************************************************//~v101I~
//1A07 2013/03/02 distinguish color for extratime                  //+1A07I~
//101e 2013/02/08 findViewById to Container(super of Frame and Dialog)//~v101I~
//*********************************************************************//~v101I~
package jagoclient.gui;

//import java.awt.*;

import com.Asgts.awt.Color;                                         //~@@@@R~//~v101R~
import com.Asgts.awt.Container;                                    //~v101R~
import com.Asgts.awt.Dimension;                                     //~@@@@R~//~v101R~
import com.Asgts.awt.Font;                                          //~@@@@R~//~v101R~
import com.Asgts.awt.FontMetrics;                                   //~@@@@R~//~v101R~
import com.Asgts.awt.Graphics;                                      //~@@@@R~//~v101R~
import com.Asgts.awt.Image;                                         //~@@@@R~//~v101R~
import com.Asgts.jagoclient.biglabel.Panel;                       //~1414R~//~@@@@R~//~v101R~
                  //~1414I~

import jagoclient.Global;

public class BigLabel extends Panel
{	Image I=null;
	Graphics GI;
	FontMetrics FM;
	int Offset;
  protected                                                        //+1A07I~
	int W,H;
	Font F;
//    public BigLabel (Font f)                                     //~v101R~
//    {   F=f;                                                     //~v101R~
//        if (f!=null) setFont(f);                                 //~v101R~
//        FM=getFontMetrics(f);                                    //~v101R~
//    }                                                            //~v101R~
	public BigLabel (Container Pcontainer,Font f)                  //~v101I~
	{                                                              //~v101I~
		super(Pcontainer);                                         //~v101I~
		F=f;                                                       //~v101I~
		if (f!=null) setFont(f);                                   //~v101I~
		FM=getFontMetrics(f);                                      //~v101I~
	}                                                              //~v101I~
	public void paint (Graphics g)
	{	Dimension d=getSize();
		int w=d.width,h=d.height;
		if (I==null || w!=W || h!=H)
		{	W=w; H=h;
			I=createImage(W,H);
			if (I==null) return;
			GI=I.getGraphics();
			if (F!=null) GI.setFont(F);
			FM=GI.getFontMetrics();
			Offset=FM.charWidth('m')/2;
		}
		GI.setColor(Global.gray);
		GI.fillRect(0,0,W,H);
		GI.setColor(Color.black);
		drawString(GI,
			Offset,(H+FM.getAscent()-FM.getDescent())/2,FM);
		g.drawImage(I,0,0,W,H,this);
	}
	public void update (Graphics g)
	{	paint(g);
	}
	public void drawString (Graphics g, int x, int y, FontMetrics fm)
	{
	}
	public Dimension getPreferredSize ()
	{	return new Dimension(getSize().width,(FM.getAscent()+FM.getDescent())*3/2);
	}
	public Dimension getMinimumSize ()
	{	return getPreferredSize();
	}
}
