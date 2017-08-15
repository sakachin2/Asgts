//*CID://+1A24R~:                             update#=    6;       //~1A24I~
//*****************************************************************//~1A24I~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//*****************************************************************//~1A24I~
package com.Asgts.awt;                                              //~2C26R~//~3213R~//~3324I~

import jagoclient.board.Notes;
import jagoclient.gui.CloseDialog;
                                                                   //~3324I~
public interface FileDialogI                                       //~3324I~
{                                                                  //~1112I~//~3324I~
//*scheduled after FileDialog dismissed*************               //~1A24I~
    void fileDialogLoaded(CloseDialog Pdlg,Notes Pnote);                                 //~3324I~//~1A24R~
    void fileDialogSaved(String Pnotename);                                  //~3324I~//+1A24R~
    void fileDialogNotSaved();                                     //~1A24I~
}                                                                  //~1112I~//~3324I~