//*CID://+1Ah0R~:                             update#=    1;       //+1Ah0I~
//*********************************************************************//+1Ah0I~
//1Ah0 2016/10/15 bonanza                                          //+1Ah0I~
//*********************************************************************//+1Ah0I~
package com.Asgts.java;                                               //~1108R~//~1321R~//+1Ah0R~

import java.io.IOException;

import com.Asgts.AGMP;


//*********************************************                    //~1327R~
//* intercept Runtime.exec                                         //~1510R~
//*********************************************                    //~1327I~
                                                                   //~1327I~
public class Runtime                                               //~1510R~
{                                                                  //~1111I~
    private static java.lang.Runtime langRT;                                       //~1510I~
    Process P;                                                     //~1510I~
    public Runtime()                                               //~1510I~
    {                                                              //~1510I~
    }                                                              //~1510I~
	public static Runtime getRuntime()                             //~1510I~
    {                                                              //~1510I~
    	Runtime R=new Runtime();                                   //~1510I~
    	langRT=java.lang.Runtime.getRuntime();                             //~1510I~
        return R;                                                  //~1510I~
    }                                                              //~1510I~
    public Process exec(String Pcmdline) throws IOException                           //~1510R~
    {                                                              //~1417I~
    	String cmd=AGMP.checkProgram(Pcmdline);                //~1511R~
    	P=langRT.exec(cmd);                                        //~1511I~
        return P;                                                  //~1511R~
    }
}
