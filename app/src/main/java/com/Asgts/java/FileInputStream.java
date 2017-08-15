//*CID://+@@@@R~:                             update#=    3;       //~@@@@I~
package com.Asgts.java;                                               //~1108R~//~1321R~//+@@@@R~

import java.io.FileNotFoundException;

import com.Asgts.Prop;                                             //+@@@@R~
//***************************************                          //~1327I~
//* for jagoclient.Global               *                          //~1327I~
//***************************************                          //~1327I~
//***********                                                      //~1419I~
// constructor FileInmputStream(InputStream) is protected;         //~1419I~
// super(InputStream) is err from child class extended FileInputStream//~1419I~
//***********                                                      //~1419I~
public class FileInputStream extends java.io.BufferedInputStream   //~1419I~
{  
	public FileInputStream(String Pfile) throws FileNotFoundException//~1327R~
	{
//  	I want to construct by InputStream for files in asset/ or res/raw//~1419I~
		super(Prop.getInputStreamDataRaw(Pfile));             //~1419R~//~@@@@R~
	}
}
