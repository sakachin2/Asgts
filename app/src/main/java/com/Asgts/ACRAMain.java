//*CID://+1A3dR~:                             update#=    9;       //~1A3dR~
//*****************************************************************************************
//1A3d 2013/06/04 ACR                                              //~1A3dI~
//*****************************************************************************************
package com.Asgts;                                                  //~1A3dR~

import org.acra.*;
import static org.acra.ReportField.*;
import org.acra.annotation.ReportsCrashes;
import android.app.Application;

@ReportsCrashes(formKey="",
                mailTo="sak21997jp@gmail.com",                     //~1A3dR~
                customReportContent={
//CrashReports-Template.csv
//REPORT_ID, APP_VERSION_CODE, APP_VERSION_NAME, PACKAGE_NAME, FILE_PATH, PHONE_MODEL, BRAND, PRODUCT, ANDROID_VERSION, BUILD, TOTAL_MEM_SIZE, AVAILABLE_MEM_SIZE, CUSTOM_DATA, IS_SILENT, STACK_TRACE, INITIAL_CONFIGURATION, CRASH_CONFIGURATION, DISPLAY, USER_COMMENT, USER_EMAIL, USER_APP_START_DATE, USER_CRASH_DATE, DUMPSYS_MEMINFO, LOGCAT, INSTALLATION_ID, DEVICE_FEATURES, ENVIRONMENT, SHARED_PREFERENCES, SETTINGS_SYSTEM, SETTINGS_SECURE, SETTINGS_GLOBAL
  REPORT_ID, APP_VERSION_CODE, APP_VERSION_NAME, PACKAGE_NAME, FILE_PATH, PHONE_MODEL, BRAND, PRODUCT, ANDROID_VERSION,        TOTAL_MEM_SIZE, AVAILABLE_MEM_SIZE,              IS_SILENT, STACK_TRACE,                        CRASH_CONFIGURATION, DISPLAY,                           USER_APP_START_DATE, USER_CRASH_DATE,                  LOGCAT,                                   ENVIRONMENT,                                                       SETTINGS_GLOBAL//~1A41R~//+1A3dI~
                },
                mode=ReportingInteractionMode.TOAST,
                resToastText=R.string.Err_ACRA
               )
public class ACRAMain extends Application                          //~1A3dR~
{
    @Override
    public void onCreate()
	{
    	super.onCreate();
    	try
        {
	    	ACRA.init(this);
        }
        catch(Exception e)
        {
        }
    }
}//class