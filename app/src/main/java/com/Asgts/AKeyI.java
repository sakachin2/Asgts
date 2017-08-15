package com.Asgts;                                                //~1401R~//~2C26R~//+3213R~
import com.Asgts.awt.KeyEvent;                                         //~1114R~//~2C26R~//+3213R~

//*************************************************                //~1401I~
//* it can return boolean rc to android KeyListener                //~1401I~
//*************************************************                //~1401I~
public interface AKeyI  extends com.Asgts.awt.KeyListener              //~1317R~//~1401R~//~2C26R~//~2C27R~//+3213R~
{                                                                  //~1112I~
    boolean keyPressedRc(KeyEvent ev);                               //~1317R~
    boolean keyReleasedRc(KeyEvent ev);                              //~1317R~
}                                                                  //~1112I~