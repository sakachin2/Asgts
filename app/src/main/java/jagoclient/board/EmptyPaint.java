//*CID://+1Ad9R~:                                   update#=   17; //+1Ad9R~
//*****************************************************************************//~@@@1I~
//1Ad9 2015/07/21 additional to 1Ad6(OutOfMemory)                  //+1Ad9I~
//1A4h 2014/12/03 catch OutOfMemory                                //~1A4hI~
//*@@@1 20110514                                                   //~@@@1I~
//*(1)for performance:save/restore bitmap                          //~@@@1I~
//*(2)to avoid outofmemory:not keeping static bitmap, recycle when copyed to board//~@@@1I~
//*   (ignore call from WoodPaint<--Go.java:Bitmap would be saved if same board size)//~@@@1I~
//*****************************************************************************//~@@@1I~
package jagoclient.board;


import com.Asgts.jagoclient.board.EmptyIO;                          //~@@@1R~//~@@@2R~
import com.Asgts.awt.Color;                                         //~@@@1R~//~@@@2R~
import com.Asgts.awt.ColorModel;                                    //~@@@1R~//~@@@2R~
import com.Asgts.awt.Component;                                     //~@@@1R~//~@@@2R~
import com.Asgts.awt.Image;                                         //~@@@1R~//~@@@2R~
import com.Asgts.awt.ImageObserver;                                 //~@@@1R~//~@@@2R~
import com.Asgts.awt.MemoryImageSource;                             //~@@@1R~//~@@@2R~


import jagoclient.Dump;
import jagoclient.Global;
import jagoclient.StopThread;

//import java.awt.Color;                                           //~1417R~
//import java.awt.Component;                                       //~1417R~
//import java.awt.Image;                                           //~1417R~
//import java.awt.image.ColorModel;                                //~1417R~
//import java.awt.image.ImageObserver;                             //~1417R~
//import java.awt.image.MemoryImageSource;                         //~1417R~

/**
This is used to create an image of the empty bard at startup or when
a board is needed (request from Board via WoodPaint).
@see jagoclient.board.WoodPaint
*/

public class EmptyPaint extends StopThread
{	Board B;
	static int W,H;
	public static int Ox,Oy,D;
	static Color C;
	boolean Shadows;
	EmptyPaint (Board b, int w, int h, Color c, boolean shadows, int ox, int oy, int d)
	{	B=b; W=w; H=h; C=c; Shadows=shadows; Ox=ox; Oy=oy; D=d;
		start();
	}
	public void run ()
	{                                                              //~1513R~
    	if (Dump.Y) Dump.println("EmptyPaint run start:"+this.toString());//~1513I~
		try                                                        //~1513I~
		{	setPriority(getPriority()-1);
		}
		catch (Exception e) { Dump.println(e,"EmptyPaint exception"); }//~1511R~
		try { sleep(100); } catch (Exception e) {}
      try                                                          //~1513I~
      {                                                            //~1513I~
		createwood(this,B,W,H,C,Shadows,Ox,Oy,D);
		if (!stopped()) B.updateboard();                           //~1217I~
      }                                                            //~1513I~
      catch(OutOfMemoryError me)                                   //~1A4hI~
      {                                                            //~1A4hI~
		Dump.println(me,"EmptyPaint:createwood");                  //~1A4hI~
      }                                                            //~1A4hI~
      catch(Exception e)                                           //~1513I~
      {                                                            //~1513I~
		Dump.println(e,"EmptyPaint exception2");                   //~1513I~
      }                                                            //~1513I~
    	if (Dump.Y) Dump.println("EmptyPaint run end:"+this.toString());//~1513I~
	}

	public static Image StaticImage=null,StaticShadowImage=null;

	/**
	Create an image of the wooden board. The component is used
	to create the image.
	*/
	static public void createwood (StopThread EPT, 
		Component comp, int w, int h, Color c, boolean shadows, int ox, int oy, int d)
	{	if (w==0 || h==0) return;
    	if (Dump.Y) Dump.println("EmptyPaint createwood EPT="+EPT.toString());//~@@@1R~
        if (EPT instanceof WoodPaint)  //from Go-->WoodPaint for precrete//~@@@1I~
			return;             //for recycle, create eachtime     //~@@@1I~
		StaticImage=StaticShadowImage=null;
		Image newSImage=null,newSSImage=null;                     //~@@@1I~
		
		int p[]=new int[w*h];
		int ps[]=null;
		if (shadows) ps=new int[w*h];
		int i,j;
		double f=9e-1;
		int col=c.getRGB();
		int blue=col&0x0000FF,green=(col&0x00FF00)>>8,red=(col&0xFF0000)>>16;
		double r,g,b;
		double x,y,dist;
//      boolean fine=Global.getParameter("fineboard",true);        //~@@@2R~
        boolean fine=true;                                         //~@@@2I~
		for (i=0; i<h; i++)
			for (j=0; j<w; j++)
			{	if (fine)
					f=((Math.sin(18*(double)j/w)+1)/2
					+(Math.sin(3*(double)j/w)+1)/10
					+0.2*Math.cos(5*(double)i/h)+
					+0.1*Math.sin(11*(double)i/h))
					*20+0.5;
				else
					f=((Math.sin(14*(double)j/w)+1)/2
					+0.2*Math.cos(3*(double)i/h)+
					+0.1*Math.sin(11*(double)i/h))
					*10+0.5;
				f=f-Math.floor(f);
				if (f<2e-1) f=1-f/2;
				else if (f<4e-1) f=1-(4e-1-f)/2;
				else f=1;
				if (i==w-1 || (i==w-2 && j<w-2) || j==0
					|| (j==1 && i>1)) f=f/2;
				if (i==0 || (i==1 && j>1) || j>=w-1
					|| (j==w-2 && i<h-1))
				{	r=128+red*f/2; g=128+green*f/2; b=128+blue*f/2;
				}
				else
				{	r=red*f; g=green*f; b=blue*f;
				}
				p[w*i+j]=(255<<24)|((int)(r)<<16)|((int)(g)<<8)|(int)(b);
				if (shadows)
				{	f=1;
					y=Math.abs((i-(ox+d/2+(i-ox)/d*(double)d)));
					x=Math.abs((j-(oy+d/2+(j-oy)/d*(double)d)));
					dist=Math.sqrt(x*x+y*y)/d*2;
					if (dist<1.0) f=0.9*dist;
					ps[w*i+j]=(255<<24)|((int)(r*f)<<16)|((int)(g*f)<<8)|(int)(b*f);
				}
				if (EPT.stopped()) return;
			}
		if (shadows)
			newSSImage=  //non static,static created will not be refered//~@@@1R~
			StaticShadowImage=comp.createImage(
				new MemoryImageSource(w,h,ColorModel.getRGBdefault(),
						ps,0,w));
		newSImage=	//recycled when copyed to EmptyImage		   //~@@@1R~
		StaticImage=comp.createImage(
				new MemoryImageSource(w,h,ColorModel.getRGBdefault(),
						p,0,w));
        EmptyIO.save(((EmptyPaint)EPT).B,newSSImage,newSImage,w,h,c,shadows,ox,oy,d);//~@@@1R~
    	if (Dump.Y) Dump.println("EmptyPaint createwood static="+newSImage.toString());//~@@@1R~//~@@@2R~
        newSSImage=null;                                           //+1Ad9I~
        newSImage=null;                                            //+1Ad9I~
		W=w; H=h; D=d; Ox=ox; Oy=oy; C=c;
		savesize(comp);
	}
	
	static void savesize (ImageObserver C)
	{	Image i=StaticImage;
		if (i!=null)
		{	Global.setParameter("sboardwidth",i.getWidth(C));
			Global.setParameter("sboardheight",i.getHeight(C));
			Global.setParameter("sboardox",EmptyPaint.Ox);
			Global.setParameter("sboardoy",EmptyPaint.Oy);
			Global.setParameter("sboardd",EmptyPaint.D);
		}
                                                           //~1120I~
	}
	static boolean haveImage (int w, int h, Color c, int ox, int oy, int d)
	{                                                              //~@@@1R~
//  	if (StaticImage==null) return false;                       //~@@@1I~
//  	return (w==W && h==H && ox==Ox && oy==Oy && D==d && C.getRGB()==c.getRGB());//~@@@1R~
//* from Board.trywood with synchronized                           //~@@@1I~
//      boolean shadows=true; 	//by Board.trywood                 //~@@@1I~//~@@@2R~
        boolean shadows=false;	//no shadow                        //~@@@2I~
        EmptyIO ae=new EmptyIO();                                  //~@@@1R~
      try                                                          //~1A4hI~
      {                                                            //~1A4hI~
	 	if (ae.load(w,h,c,shadows,ox,oy,d))                        //~@@@1I~
        {                                                          //~@@@1I~
			StaticImage=ae.staticImage;               //set to static//~@@@1I~
			StaticShadowImage=ae.staticShadowImage;   //synchronous same hread as board//~@@@1I~
    		if (Dump.Y) Dump.println("EmptyPaint haveImage=true"); //~@@@2I~
            return true;     //recycled just after copyed to EmptyImage at return//~@@@1R~
		}                                                          //~@@@1I~
      }                                                            //~1A4hI~
      catch(OutOfMemoryError me)                                   //~1A4hI~
      {                                                            //~1A4hI~
		Dump.println(me,"EmptyPaint:haveImage");                   //~1A4hI~
      }                                                            //~1A4hI~
    	if (Dump.Y) Dump.println("EmptyPaint haveImage=false");    //~@@@2I~
        return false;                                              //~@@@1I~
	}
}
