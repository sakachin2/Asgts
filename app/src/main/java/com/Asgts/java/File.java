//*CID://+1Ah0R~:                             update#=    2;       //~1Ah0I~
//*********************************************************************//~1Ah0I~
//1Ah0 2016/10/15 bonanza                                          //~1Ah0I~
//*********************************************************************//~1Ah0I~
package com.Asgts.java;                                               //~1108R~//~1321R~//~1Ah0R~

import com.Asgts.AGMP;                                            //+1Ah0R~
                                                                   //~1327I~
@SuppressWarnings("serial")
public class File extends java.io.File                              //~1516R~
{                                                                  //~1111I~
	public File(String Ppgmname)                                   //~1516R~
    {                                                              //~1510I~
    	super(AGMP.checkDefaultProgram(Ppgmname));             //~1516R~//+1Ah0R~
    }                                                              //~1510I~
}
