//*CID://+1A6tR~: update#= 192;                                    //~1A6tR~
//**********************************************************************
//1A6t 2015/02/18 (BUG)simpleProgress Dialog thread err exception  //~1A6tI~
//**********************************************************************
package com.Asgts;                                                 //+1A6tR~

import android.app.ProgressDialog;
                                                                   //~1A6tI~
public class URunnableData                                         //~1A6tR~
{
    public ProgressDialog progressDialog;                                           //~1A6tI~
    public URunnableData()                                         //~1A6tR~
    {
    }
    public URunnableData(ProgressDialog Pdlg)                      //~1A6tI~
    {                                                              //~1A6tI~
    	progressDialog=Pdlg;                                                 //~1A6tI~
    }                                                              //~1A6tI~
}//class
