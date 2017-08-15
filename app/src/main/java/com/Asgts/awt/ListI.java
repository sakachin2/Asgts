//*CID://+1A4nR~:                             update#=   11;       //~1A4nR~
//*****************************************************************
//1A4n 2014/12/04 FileDialog:multiple delete support               //~1A4aI~
//1A4a 2014/11/29 FileDialog:open when selected item is clicked    //~1A4aI~
//*****************************************************************
package com.Asgts.awt;

public interface ListI                                             //~1A4aR~
{
    void onListItemClicked(int Ppos,int Pselectedpos);             //~1A4aR~
    int  onListItemClickedMultiple(int Ppos);                      //+1A4nR~
}