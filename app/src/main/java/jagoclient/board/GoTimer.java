//*CID://+1A18R~:                             update#=    4;       //~1A18R~
//**************************************************************   //~1A10I~
//1A18 2013/03/13 FreeBoard:show Black timer only                  //~1A18I~
//1A10 2013/03/07 free board                                       //~1A10I~
//**************************************************************   //~1A10I~
package jagoclient.board;

import jagoclient.Dump;
import jagoclient.StopThread;
import jagoclient.board.TimedGoFrame;

/**
A timer for the goboard. It will call the alarm method of the
board in regular time intervals. This is used to update the
timer.
@see jagoclient.board.TimedBoard
*/

public class GoTimer extends StopThread
{	public long Interval;
	TimedBoard B;
	public GoTimer (TimedBoard b, long i)
	{	Interval=i; B=b;
		start();
	}
	public void run ()
	{                                                              //+1A18R~
    	if (Dump.Y) Dump.println("GoTimer run() start");          //+1A18I~
		try                                                        //+1A18I~
		{	while (!stopped())
			{	sleep(Interval);
				if (stopped())                                     //~3124I~
        			break;                                         //~3124I~
//              if (!((TimedGoFrame)B).swSuspendTimer)                               //~1A10I~//~1A18R~
				B.alarm();
			}
		}
		catch (Exception e)
		{                                                          //~1506R~
          	Dump.println(e,"GoTimer Exception");                   //~1506R~
		}                                                          //~1506I~
        if (Dump.Y)	Dump.println("GoTimer stopped:"+this.toString());//~1A18R~
	}
}
