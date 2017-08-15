//*CID://+1Ai0R~:                             update#=  265;       //~1Ai0R~
//***********************************************************************//~v1A0I~
//1Ai0 2016/12/28 Abonaza killed just after start. It may be by large bss section. Reduces memory usage. and also max thread 12-->4.
//1Ah0 2015/05/04 bonanza                                          //~1Ah0I~
//1Ab4 2015/05/04 (Bug of 1Aa8) FreebiardQuestion NPE;FreeboardQuestion extends GameQuestion and resid should be of FBQ)//~1Ab4R~
//1Aa8 2015/04/20 put in layout the gamequestion for mdpi          //~1Aa8I~
//1A49 2014/11/01 opponent name for local match(for savefile when asgts)(Ahsv:1A56,Ajagot1w:v1D9)//~1A49I~
//1A3c 2013/06/04 set default totaltime to 60min                   //~1A3cI~
//1A30 2013/04/06 kif,ki2,gam,csa,psn format support               //~1A30I~
//1A2j 2013/04/03 (Bug)sendsuspend(not main thread) call ProgDialog//~1A2jI~
//                that cause RunTimEException not looper           //~1A2jI~
//1A2e 2013/04/01 move description on record by japanese and english format//~1A2eI~
//1A24 2013/03/23 move reload button to gamequetion                //~1A24I~
//1A1h 2013/03/18 default gameoption:bigtimer                      //~1A1hI~
//1A12 2013/03/08 Option to diaplay time is for tiomeout=0(if not 0 diaplay timer)//~1A12I~
//1A10 2013/03/07 free board                                       //~1A10I~
//1A00 2013/02/13 Asgts                                            //~v1A0I~
//***********************************************************************//~v1A0I~
package com.Asgts.gtp;                                             //~1Ah0R~

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Asgts.AG;                                               //~@@@2R~
import com.Asgts.AView;
import com.Asgts.Prop;
import com.Asgts.R;                                                //~@@@2R~
import com.Asgts.Unzipper;
import com.Asgts.UnzipperI;
import com.Asgts.awt.ButtonRG;
import com.Asgts.awt.Checkbox;
import com.Asgts.awt.FileDialog;
import com.Asgts.awt.FileDialogI;
import com.Asgts.awt.Frame;                                        //~@@@2R~

import jagoclient.Dump;
import jagoclient.MainFrame;
import jagoclient.board.Board;
import jagoclient.board.Field;
import jagoclient.board.Notes;
import jagoclient.board.NotesFmtCsa;
import jagoclient.dialogs.HelpDialog;
import jagoclient.gui.ButtonAction;
import jagoclient.gui.FormTextField;
//import jagoclient.gui.MyLabel;                                   //~2C26R~
//import jagoclient.gui.MyPanel;                                   //~2C26R~
//import jagoclient.gui.Panel3D;                                   //~2C26R~
import jagoclient.partner.GameQuestion;
import jagoclient.partner.HandicapQuestion;

//import java.awt.Choice;                                          //~@@@@R~
//import java.awt.GridLayout;
//import java.awt.Panel;

//***************************************************************************************//~1Ah0I~
//*subcmd lis                                                      //~1Ah0I~
//***************************************************************************************//~1Ah0I~
//- book off                                                       //~1Ah0I~
//  序盤データベースの参照を禁止．book on を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//- book on                                                        //~1Ah0I~
//  序盤データベースを参照（初期設定）book off を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//- book narrow                                                    //~1Ah0I~
//  序盤データベースの指手のうち，頻度の低い手を採用しない．book wide を//~1Ah0R~
//  入力するまで有効．                                    //~1Ah0R~
                                                                   //~1Ah0I~
//- book wide                                                      //~1Ah0I~
//  序盤データベースから幅広く指し手を選ぶ (初期設定)．book narrow を入//~1Ah0R~
//  力するまで有効．                                       //~1Ah0R~
                                                                   //~1Ah0I~
//- display                                                        //~1Ah0I~
//  bonanza.exe が保持する盤面を CSA 形式で出力．思考・予測読み中は出力//~1Ah0R~
//  結果不定．                                                //~1Ah0R~
                                                                   //~1Ah0I~
//* hash [num]                                                     //~1Ah0I~
//  ハッシュテーブルとして確保するメモリ領域の大きさを指定．大きさは大体，//~1Ah0R~
//  2 の num 乗かける 48 バイト程度，初期値は 18．思考中は無効，予測読み//~1Ah0R~
//  中は読み直し．                                          //~1Ah0R~
                                                                   //~1Ah0I~
//* limit depth [num]                                              //~1Ah0I~
//  思考時間を探索の探索基準深さ [num] で制限．思考中は無効，予測読み中//~1Ah0R~
//  は読み直し．limit nodes [num], limit time [num] を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//* limit nodes [num]                                              //~1Ah0I~
//  思考時間を探索ノード数 [num] で制限．思考中は無効，予測読み中は読み//~1Ah0R~
//  直し．limit depth [num], limit time [num] を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//* limit time [num1] [num2]                                       //~1Ah0I~
//  思考時間を持ち時間で制限 (初期設定)．num1 分，切れてから一手 num2 秒//~1Ah0R~
//  に設定．初期設定は 0分 - 10秒．思考中は無効，予測読み中は読み直し．//~1Ah0R~
//  limit depth [num], limit node [num] を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//* limit time strict                                              //~1Ah0I~
//  思考時間を持ち時間で制限した場合，切れてから一手辺りの秒数を厳守 (初//~1Ah0R~
//  期設定)．思考中は無効，予測読み中は読み直し．limit time extendable//~1Ah0R~
//  を入力するまで有効．                                 //~1Ah0R~
                                                                   //~1Ah0I~
//* limit time extendable                                          //~1Ah0I~
//  思考時間を持ち時間で制限した場合，より良い指手が見つかりそうな場合に，//~1Ah0R~
//  切れてから一手辺りの秒数を超えて思考することを許す．思考中は無効，予//~1Ah0R~
//  測読み中は読み直し．limit time strict を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//* time remain [num1] [num2]                                      //~1Ah0I~
//  思考時間を持ち時間で制限した場合，持ち時間の残りを秒単位で変更．//~1Ah0R~
//  num1  先手残り時間                                       //~1Ah0R~
//  num2  後手残り時間                                       //~1Ah0R~
                                                                   //~1Ah0I~
//* move                                                           //~1Ah0I~
//  次の一手を思考し，bonanza.exe が保持する棋譜を一手進める．思考中は無//~1Ah0R~
//  効，予測読み中は読み直し．                        //~1Ah0R~
                                                                   //~1Ah0I~
//- [move]                                                         //~1Ah0I~
//  対局相手の指し手 [move] を受け取り，次の一手を思考．更に bonanza.exe//~1Ah0R~
//  が保持する棋譜を一手進める．手を表す文字列 [move] の形式は CSA 形式//~1Ah0R~
//  の指手から +, - を除いたもになります．(例：7776FU, 0087FU) 思考中は//~1Ah0R~
//  無効．直前まで行っていた予測読みがヒットした場合，その結果を利用して//~1Ah0R~
//  思考する．                                                //~1Ah0R~
                                                                   //~1Ah0I~
//* move [move]                                                    //~1Ah0I~
//  現在の棋譜から一手進める．手を表す文字列 [move] の形式は CSA 形式の//~1Ah0R~
//  指手から +, - を除いたもになります．(例：move 7776FU, move 0087FU)//~1Ah0R~
//  思考中は無効，予測読み中は読み直し．         //~1Ah0R~
                                                                   //~1Ah0I~
//* move restraint                                                 //~1Ah0I~
//  次の一手を思考．但し，restraint.dat 内に羅列されている合法手を指さな//~1Ah0R~
//  いようにする．ファイル内の文字列は以下のようにして下さい．//~1Ah0R~
//  -------- restraint.dat --------                                //~1Ah0I~
//2726FU(改行)                                                   //~1Ah0R~
//7776FU(改行)                                                   //~1Ah0R~
//(EOF)                                                            //~1Ah0I~
//  -------------------------------                                //~1Ah0I~
//  思考中は無効，予測読み中は読み直し．         //~1Ah0R~
                                                                   //~1Ah0I~
//* new                                                            //~1Ah0I~
//  bonanza.exe が保持する棋譜を平手の初期状態に復帰 (初期設定)．思考中//~1Ah0R~
//  は無効，予測読み中断．                              //~1Ah0R~
                                                                   //~1Ah0I~
//- ping                                                           //~1Ah0I~
//  pong の出力を促す．                                     //~1Ah0R~
                                                                   //~1Ah0I~
//- ponder on                                                      //~1Ah0I~
//  予測読み機能オン（初期設定）．ponder off を入力するまで有効．//~1Ah0R~
                                                                   //~1Ah0I~
//* ponder off                                                     //~1Ah0I~
//  予測読み機能オフ．予測読み中は中断．ponder on を入力するまで有効//~1Ah0R~
                                                                   //~1Ah0I~
//* read [filename] t [num]                                        //~1Ah0I~
//  CSA 形式の棋譜ファイルを読む．bonanza.exe が保持する棋譜を更新．思考//~1Ah0R~
//  中は無効，予測読み中は新しい局面から読み直し．//~1Ah0R~
//  [filename]  ファイル名を指定．ピリオド "." を指定した場合は現在の棋//~1Ah0R~
//              譜を読む．                                    //~1Ah0R~
//  [num]       手数を指定．省略した場合は終端まで読む．//~1Ah0R~
                                                                   //~1Ah0I~
//- resign [num]                                                   //~1Ah0I~
//  投了するスコアの下限 num を指定．32600 に設定すると投了しない．初期//~1Ah0R~
//  設定は 533．        Minimum=174                            //~1Ah0R~
//**   Minimum=174 (Dpawn+DPawn)                                   //~1Ah0I~
                                                                   //~1Ah0I~
//- s                                                              //~1Ah0I~
//  次の一手をできるだけ早く指させる．思考中でない場合は無効．//~1Ah0R~
                                                                   //~1Ah0I~
                                                                   //~1Ah0I~
//【出力】　（一行単位で行います．最後に必ず改行コードが存在します）//~1Ah0R~
                                                                   //~1Ah0I~
//move[move]                                                       //~1Ah0I~
//  思考結果の指手．手を表す文字列 [move] の形式は CSA 形式の指手から +,//~1Ah0R~
//  - を除いたもになります．(例：move7776FU, move0087FU)//~1Ah0R~
                                                                   //~1Ah0I~
//info ponder start                                                //~1Ah0I~
//  予測読みを開始するときに出力                     //~1Ah0R~
                                                                   //~1Ah0I~
//info ponder end                                                  //~1Ah0I~
//  予測読みが終了した時きに出力．'info ponder start' が出力されてから//~1Ah0R~
//  '[move]' コマンドを入力するまでが実際に予測読みに費やした時間．予測//~1Ah0R~
//  読みの中断・読み直しを行った場合，途中で 'info ponder end' が出力さ//~1Ah0R~
//  れる．                                                      //~1Ah0R~
                                                                   //~1Ah0I~
//info[statistics]                                                 //~1Ah0I~
//  思考した局面が序盤データベース内に見つかった場合に出力される統計情報．//~1Ah0R~
//  (例：info 7776FU(86%) 2726FU(14%))                           //~1Ah0R~
                                                                   //~1Ah0I~
//info[score] [move sequence]                                      //~1Ah0I~
//  思考中に多数回出力される思考の途中経過      //~1Ah0R~
//  [score]                                                        //~1Ah0I~
//    出力された時点での bonanza の推定評価値       //~1Ah0R~
//     325.99            - 先手にとって優勢な繰り返し //~1Ah0R~
//     325.98 ~  300.01  - 先手玉詰み                         //~1Ah0R~
//     300.00 ~ -300.00  - 評価値．1点が歩一枚分に相当//~1Ah0R~
//    -300.01 ~ -325.98  - 後手玉詰み                         //~1Ah0R~
//    -325.99            - 後手にとって優勢な繰り返し //~1Ah0R~
//  [move sequence]                                                //~1Ah0I~
//    出力された時点での bonanza の推定最善応手．CSA 形式の指手をスペー//~1Ah0R~
//    スで区切って出力                                     //~1Ah0R~
//  (例：info+0.43 -5142OU +2726FU -4232OU +2625FU -7162GI)      //~1Ah0R~
                                                                   //~1Ah0I~
//info tt [num]                                                    //~1Ah0I~
//  思考に費やした時間 num を出力                      //~1Ah0R~
                                                                   //~1Ah0I~
//pong                                                             //~1Ah0I~
//  ping 入力を与えた際に出力                            //~1Ah0R~
                                                                   //~1Ah0I~
//statsatu=[num1] cpu=[num2] nps=[num3]                            //~1Ah0I~
//  思考終了時に出力される bonanza のコンピュータ資源使用状況推定値．現//~1Ah0R~
//  局面局が序盤データベース内で見つかった場合は出力されません．//~1Ah0R~
//  [num1]  ハッシュ領域使用パーセンテージ          //~1Ah0R~
//  [num2]  CPU 時間占有パーセンテージ                  //~1Ah0R~
//  [num3]  一秒当たりの検索ノード数 / 1000            //~1Ah0R~
//  (例：statsatu=22 cpu=98 nps=886.28)                          //~1Ah0R~
                                                                   //~1Ah0I~
                                                                   //~1Ah0I~
//【例】                                                        //~1Ah0R~
//入出力の一例．[out]: 以降の行は出力，[in]: 以降の行は入力を表す．//~1Ah0R~
                                                                   //~1Ah0I~
// 1 [out]: Bonanza Version 2.1 - The Computer Shogi Engine        //~1Ah0I~
// 2 [in ]: book off                                               //~1Ah0I~
// 3 [in ]: 5968OU                                                 //~1Ah0I~
// 4 [out]: info+0.47 -5142OU +7776FU -3334FU +3948GI ...          //~1Ah0I~
// 5 [out]: info+0.26 -5142OU +7776FU -7162GI +6878OU ...          //~1Ah0I~
// 6 [out]: info+0.47 -5142OU +7776FU -3334FU +6878OU ...          //~1Ah0I~
// 7 [out]: info+0.15 -5142OU +7776FU -4232OU +7978GI ...          //~1Ah0I~
// 8 [out]: statsatu=54 cpu=100 nps=461.92                         //~1Ah0I~
// 9 [out]: move5142OU                                             //~1Ah0I~
//10 [out]: info tt 000:10                                         //~1Ah0I~
//11 [out]: info ponder start                                      //~1Ah0I~
//12 [out]: info+0.27 +7776FU -3334FU +6878OU -4232OU ...          //~1Ah0I~
//13 [out]: info+0.27 +7776FU -3334FU +6878OU -4232OU ...          //~1Ah0I~
//14 [out]: info+0.33 +7776FU -3334FU +2726FU -7162GI ...          //~1Ah0I~
//15 [out]: info+0.32 +7776FU -3132GI +6878OU -4231OU ...          //~1Ah0I~
//16 [in ]: 7776FU                                                 //~1Ah0I~
//17 [out]: info ponder end                                        //~1Ah0I~
//18 [out]: info+0.13 -8384FU +2726FU -8485FU +2625FU ...          //~1Ah0I~
                                                                   //~1Ah0I~
                                                                   //~1Ah0I~
// 1     バージョン情報                                     //~1Ah0R~
// 2     序盤データベースの参照を禁止させる       //~1Ah0R~
// 3     先手の初手▲６八王．後手の次の指し手を bonanza に思考させる//~1Ah0R~
// 4-  7 評価値，読み筋途中経過                         //~1Ah0R~
// 8     コンピュータ資源使用状況推定値             //~1Ah0R~
// 9     思考結果の指手                                     //~1Ah0R~
//10     思考に費やした時間                               //~1Ah0R~
//11     予測読み開始の合図                               //~1Ah0R~
//12- 15 評価値，読み筋途中経過．読み筋第一手目▲７六歩は予測手．//~1Ah0R~
//16     先手の２手目▲７六歩．後手の次の指し手を bonanza に思考させる//~1Ah0R~
//17     予測読み終了の合図                               //~1Ah0R~
//18     評価値，読み筋途中経過                         //~1Ah0R~
//***************************************************************************************//~1Ah0I~
//4. How to build Bonanza                                          //~1Ah0I~
//-----------------------                                          //~1Ah0I~
                                                                   //~1Ah0I~
//You can build Bonanza by means of GNU Make on Linux or Microsoft NMAKE//~1Ah0I~
//on Windows. Here is some examples:                               //~1Ah0I~
                                                                   //~1Ah0I~
//- GCC on Linux                                                   //~1Ah0I~
//> make -f Makefile gcc                                           //~1Ah0I~
                                                                   //~1Ah0I~
//- Intel C++ Compiler on Linux                                    //~1Ah0I~
//> make -f Makefile icc                                           //~1Ah0I~
                                                                   //~1Ah0I~
//- Microsoft C/C++ Compiler on Windows                            //~1Ah0I~
//> nmake -f Makefile.vs cl                                        //~1Ah0I~
                                                                   //~1Ah0I~
//- Intel C++ Compiler on Windows                                  //~1Ah0I~
//> nmake -f Makefile.vs icl                                       //~1Ah0I~
                                                                   //~1Ah0I~
//The C source codes are written by using ANSI C plus a small number of//~1Ah0I~
//new features in ISO C99. Therefore, I think this can be easily built//~1Ah0I~
//in many platforms without much effort.                           //~1Ah0I~
                                                                   //~1Ah0I~
//It may be necessary to define some macros in Makefile or         //~1Ah0I~
//Makefile.vs. The macros are:                                     //~1Ah0I~
                                                                   //~1Ah0I~
//- NDEBUG (DEBUG)    builds release (debug) version of Bonanza    //~1Ah0I~
                                                                   //~1Ah0I~
//- MINIMUM           disables some auxiliary functions that are not//~1Ah0I~
//                    necessary to play a game, e.g., book composition//~1Ah0I~
//                    and optimization of evaluation functions     //~1Ah0I~
                                                                   //~1Ah0I~
//- TLP               enables thread-level parallel search         //~1Ah0I~
                                                                   //~1Ah0I~
//- MPV               enables multi-PV search                      //~1Ah0I~
                                                                   //~1Ah0I~
//- CSA_LAN           enables Bonanza to communicate by CSA Shogi TCP/IP//~1Ah0I~
//                    protcol                                      //~1Ah0I~
                                                                   //~1Ah0I~
//- DEKUNOBOU         enables dekunobou interface (available only for//~1Ah0I~
//                    Windows)                                     //~1Ah0I~
                                                                   //~1Ah0I~
//- CSASHOGI          builds an engine for CSA Shogi (available only for//~1Ah0I~
//                    Windows)                                     //~1Ah0I~
                                                                   //~1Ah0I~
//- NO_LOGGING        suppresses dumping log files                 //~1Ah0I~
                                                                   //~1Ah0I~
//- HAVE_SSE2         use SSE2 instructions for speed              //~1Ah0I~
                                                                   //~1Ah0I~
//- HAVE_SSE4         use SSE2 and SSE4.1 instructions for speed   //~1Ah0I~
                                                                   //~1Ah0I~
//- MNJ_LAN           enables a client-mode of cluster searches    //~1Ah0I~
                                                                   //~1Ah0I~
//- INANIWA_SHIFT     enables an Inaniwa strategy detection        //~1Ah0I~
                                                                   //~1Ah0I~
//- DFPN              build the DFPN worker of mate-problems server//~1Ah0I~
                                                                   //~1Ah0I~
//- DFPN_CLIENT       enables the client-mode of mate-problem server//~1Ah0I~
                                                                   //~1Ah0I~
                                                                   //~1Ah0I~
//Bonanza is an application that does not provide graphical user   //~1Ah0I~
//interface. If you could build "bonanza.exe" properly without CSASHOGI//~1Ah0I~
//macro, it shows a prompt "Black 1>" when you execute it at a computer//~1Ah0I~
//console.                                                         //~1Ah0I~
                                                                   //~1Ah0I~
//Bonanza uses three binary files: a feature vector of static evaluation//~1Ah0I~
//function "fv.bin",  an opening book "book.bin", and a            //~1Ah0I~
//position-learning database "hash.bin". You can find these in "winbin/"//~1Ah0I~
//directory. Without the NO_LOGGING option, Bonanza must find "log/"//~1Ah0I~
//directory to dump log files.                                     //~1Ah0I~
                                                                   //~1Ah0I~
                                                                   //~1Ah0I~
//5. Command List                                                  //~1Ah0I~
//---------------                                                  //~1Ah0I~
                                                                   //~1Ah0I~
//- beep on                                                        //~1Ah0I~
//- beep off                                                       //~1Ah0I~
//    These commands enable (on) or disable (off) a beep when Bonanza//~1Ah0I~
//    makes a move.  The default is on.                            //~1Ah0I~
                                                                   //~1Ah0I~
//- book on                                                        //~1Ah0I~
//- book off                                                       //~1Ah0I~
//    These commands enable (on) or disable (off) to probe the opening//~1Ah0I~
//    book, "./book.bin".  The default is on.                      //~1Ah0I~
                                                                   //~1Ah0I~
//- book narrow                                                    //~1Ah0I~
//- book wide                                                      //~1Ah0I~
//    When the command with "narrow" is used, Bonanza selects a book//~1Ah0I~
//    move from a small set of opening moves. The default is "wide". The//~1Ah0I~
//    narrowing of the opening moves is useful if you want Bonanza //~1Ah0I~
//    choose a common opening line.                                //~1Ah0I~
                                                                   //~1Ah0I~
//- book create                                                    //~1Ah0I~
//    This command creates the opening book file, "./book.bin", by using//~1Ah0I~
//    numerous experts' games in a single CSA record file, "./book.csa".//~1Ah0I~
//    It also uses another CSA record file, "book_anti.csa", where you//~1Ah0I~
//    can register bad moves that may appear in the experts' games at//~1Ah0I~
//    the last moves in the record file. Here is the example:      //~1Ah0I~
                                                                   //~1Ah0I~
//    ----------------------------------------                     //~1Ah0I~
//    PI, +, +6978KI, %TORYO                                       //~1Ah0I~
//    /                                                            //~1Ah0I~
//    PI, +, +6978KI, -8384FU, %TORYO                              //~1Ah0I~
//    /                                                            //~1Ah0I~
//    PI, +, +7776FU, -4132KI, %TORYO                              //~1Ah0I~
//    /                                                            //~1Ah0I~
//    PI, +, +7776FU, -4132KI, +2726FU, %TORYO                     //~1Ah0I~
//    ----------------------------------------                     //~1Ah0I~
                                                                   //~1Ah0I~
//    This command becomes effective when MINIMUM macro is not defined//~1Ah0I~
//    in the Makefile.                                             //~1Ah0I~
                                                                   //~1Ah0I~
//- connect 'addr' 'port' 'id' 'passwd' ['ngame']                  //~1Ah0I~
//    This command connects Bonanza to a shogi server by using the CSA//~1Ah0I~
//    protocol. The first four arguments specify the network address,//~1Ah0I~
//    port number, user ID, and password, respectively. The last   //~1Ah0I~
//    argument limits a number of games that will be played by Bonanza.//~1Ah0I~
//    This command becomes effective when CSA_LAN macro is defined in//~1Ah0I~
//    the Makefile.                                                //~1Ah0I~
                                                                   //~1Ah0I~
//- dfpn go                                                        //~1Ah0I~
//    Bonanza does DFPN search at a current position.              //~1Ah0I~
                                                                   //~1Ah0I~
//- dfpn hash 'num'                                                //~1Ah0I~
//    This command is used to initialize the transposition table of//~1Ah0I~
//    DFPN search and set the size of the table to 2^'num'. This   //~1Ah0I~
//    command becomes effective when DFPN macro is defined in the  //~1Ah0I~
//    Makefile.                                                    //~1Ah0I~
                                                                   //~1Ah0I~
//- dfpn connect 'hostname' 'port#' 'ID'                           //~1Ah0I~
//    This command is used to connect to the server script         //~1Ah0I~
//    dfpn_server.pl as a worker. This command becomes effective when//~1Ah0I~
//    DFPN macro is defined in the Makefile.                       //~1Ah0I~
                                                                   //~1Ah0I~
//- dfpn_client 'hostname' 'port#'                                 //~1Ah0I~
//    This command is used to connect to the server script         //~1Ah0I~
//    dfpn_server.pl as a client. With this, a root and its child  //~1Ah0I~
//    posittions are examined by the DFPN worker(s). This command  //~1Ah0I~
//    becomes effective when DFPN_CLIENT macro is defined in the   //~1Ah0I~
//    Makefile.                                                    //~1Ah0I~
                                                                   //~1Ah0I~
//- display ['num']                                                //~1Ah0I~
//    This command prints the shogi board. If you want to flip the //~1Ah0I~
//    board, set 'num' to 2. If not, set it to 1.                  //~1Ah0I~
                                                                   //~1Ah0I~
//- s                                                              //~1Ah0I~
//    Bonanza makes a prompt reply while thinking as soon as this  //~1Ah0I~
//    command is used.                                             //~1Ah0I~
                                                                   //~1Ah0I~
//- hash 'num'                                                     //~1Ah0I~
//    This command is used to initialize the transposition table and//~1Ah0I~
//    set the size of the table to 2^'num'.                        //~1Ah0I~
                                                                   //~1Ah0I~
//- hash learn create                                              //~1Ah0I~
//    This command is used to make a zero-filled position-lerning  //~1Ah0I~
//    database, "hash.bin". This command becomes effective when MINIMUM//~1Ah0I~
//    macro is not defined in the Makefile.                        //~1Ah0I~
                                                                   //~1Ah0I~
//- hash learn on                                                  //~1Ah0I~
//- hash learn off                                                 //~1Ah0I~
//    These commands enable (on) or disable (off) the position learning.//~1Ah0I~
//    The default is on.                                           //~1Ah0I~
                                                                   //~1Ah0I~
//- learn 'str' 'steps' ['games' ['iterations' ['num1' ['num2']]]] //~1Ah0I~
//    This command optimizes a feature vector of the static evaluation//~1Ah0I~
//    function by using numorous experts' games in a single CSA record//~1Ah0I~
//    file, "./records.csa". If you want to use a zero-filled vector as//~1Ah0I~
//    an initial guess of the optimization procedure, set 'str' to //~1Ah0I~
//    "ini". If not, set it to "no-ini". The third argument 'games' is a//~1Ah0I~
//    number of games to be read from the record file. If the third//~1Ah0I~
//    argument is negative or omitted, all games are read from the file.//~1Ah0I~
                                                                   //~1Ah0I~
//    The learning method iterates a set of procedures, and the number//~1Ah0I~
//    of iteration can be limited by the fourth argument. It continues//~1Ah0I~
//    as long as the argument is negative. The procedures consist of two//~1Ah0I~
//    parts. The first part reads the record file and creates principal//~1Ah0I~
//    variations by using 'num1' threads. The default value of 'num1' is//~1Ah0I~
//    1. The second part renews the feature vector 'steps' times by using//~1Ah0I~
//    'num2' threads in accord with the principal variations. The default//~1Ah0I~
//    value of 'steps' and 'num2' is 1. Note that each thread in the//~1Ah0I~
//    second procedure uses about 500MByte of the main memory. The two//~1Ah0I~
//    arguments 'num1' and 'num2' become effective when TLP macro is//~1Ah0I~
//    defined in the Makefile. After the procedures, the optimized //~1Ah0I~
//    vector is saved in "./fv.bin". This command become effective when//~1Ah0I~
//    MINIMUM macro is not defined in the Makefile.                //~1Ah0I~
                                                                   //~1Ah0I~
//- limit depth 'num'                                              //~1Ah0I~
//    This command is used to specify a depth, 'num', at which Bonanza//~1Ah0I~
//    ends the iterative deepening search.                         //~1Ah0I~
                                                                   //~1Ah0I~
//- limit nodes 'num'                                              //~1Ah0I~
//    When this command is used, Bonanza stops thinking after searched//~1Ah0I~
//    nodes reach to 'num'.                                        //~1Ah0I~
                                                                   //~1Ah0I~
//- limit time 'minute' 'second' ['depth']                         //~1Ah0I~
//    This command limits thinking time of Bonanza. It tries to make//~1Ah0I~
//    each move by consuming the time 'minute'. When the time is spent//~1Ah0I~
//    all, it makes each move in 'second'. The last argument 'depth' can//~1Ah0I~
//    be used if you want Bonanza to stop thinking after the iterative//~1Ah0I~
//    deepening searches reach sufficient depth.                   //~1Ah0I~
                                                                   //~1Ah0I~
//- limit time extendable                                          //~1Ah0I~
//- limit time strict                                              //~1Ah0I~
//    The command, "limit time extendable", allows Bonanza to think//~1Ah0I~
//    longer than the time limited by the previous command if it wishes//~1Ah0I~
//    to. The default is "strict".                                 //~1Ah0I~
                                                                   //~1Ah0I~
//- mnj 'sd' 'seed' 'addr' 'port' 'id' 'factor' 'stable_depth'     //~1Ah0I~
//    This command connects Bonanza to the majority or parallel server in//~1Ah0I~
//    src/server/. The first two integers specify the standard     //~1Ah0I~
//    deviation and initial seed of pseudo-random numbers which are//~1Ah0I~
//    added to the static evaluation function. Experiments suggested//~1Ah0I~
//    that an appropriate value for the standard deviation is 15. Note//~1Ah0I~
//    that all clients should use different seeds. The following three//~1Ah0I~
//    arguments are network address, port number, user ID,         //~1Ah0I~
//    respectively. You can specify factor as a voting weight. Also//~1Ah0I~
//    stable_depth is useful to detect opening positions. Here, when//~1Ah0I~
//    Bonanza reaches the specified depth, then this is reported to the//~1Ah0I~
//    server to reduce thinking time. This command becomes effective//~1Ah0I~
//    when MNJ_LAN macro is defined in the Makefile.               //~1Ah0I~
                                                                   //~1Ah0I~
//- move ['str']                                                   //~1Ah0I~
//    Bonanza makes a move of 'str'. If the argument is omitted, Bonanza//~1Ah0I~
//    thinks of its next move by itself.                           //~1Ah0I~
                                                                   //~1Ah0I~
//- mpv num 'nroot'                                                //~1Ah0I~
//- mpv width 'threshold'                                          //~1Ah0I~
//    These commands control the number of root moves, 'nroot', to //~1Ah0I~
//    constitute principal variations. The default number is 1. A root//~1Ah0I~
//    move that yields a smaller value than the best value by 'threshold'//~1Ah0I~
//    is neglected. The default threshold is about 200. These commands//~1Ah0I~
//    become effective when MPV macro is defined in the Makefile.  //~1Ah0I~
                                                                   //~1Ah0I~
//- new ['str']                                                    //~1Ah0I~
//    This command initializes the shogi board. The argument 'str' //~1Ah0I~
//    controls an initial configuration of the board.  If you want to//~1Ah0I~
//    play a no-handicapped game, set 'str' to "PI" and this is the//~1Ah0I~
//    default value. In a handicapped game, specify squares and pieces//~1Ah0I~
//    to drop, e.g. "new PI82HI22KA" or "new PI19KY".              //~1Ah0I~
                                                                   //~1Ah0I~
//- peek on                                                        //~1Ah0I~
//- peek off                                                       //~1Ah0I~
//    The command "peek on (off)" enables (disables) peeks at a buffer//~1Ah0I~
//    of the standard input file while Bonanza is thinking. The default//~1Ah0I~
//    is on. This command is useful when you want to process a set of//~1Ah0I~
//    commands as "> ./bonanza.exe < infile".                      //~1Ah0I~
                                                                   //~1Ah0I~
//- ping                                                           //~1Ah0I~
//    Prompt Bonanza to print "pong".                              //~1Ah0I~
                                                                   //~1Ah0I~
//- ponder on                                                      //~1Ah0I~
//- ponder off                                                     //~1Ah0I~
//    The command "ponder on (off)" enables (disables) thinks on the//~1Ah0I~
//    opponent's time. The default is on.                          //~1Ah0I~
                                                                   //~1Ah0I~
//- problem ['num']                                                //~1Ah0I~
//    This command is used to solve problems in "./problem.csa". Here//~1Ah0I~
//    is an example of the problem file.                           //~1Ah0I~
                                                                   //~1Ah0I~
//    -----------------------------                                //~1Ah0I~
//    $ANSWER:+0024FU                                              //~1Ah0I~
//    P1-KY-KE-OU-KI *  *  *  * -KY                                //~1Ah0I~
//    P2 *  *  *  *  * -KI *  *  *                                 //~1Ah0I~
//    P3 *  * -FU-GI-FU * -KE * -KA                                //~1Ah0I~
//    P4-FU *  * -FU-GI-FU-HI * -FU                                //~1Ah0I~
//    P5 *  *  *  *  *  *  * -FU+KY                                //~1Ah0I~
//    P6+FU+KA+FU+FU+GI+FU+KI *  *                                 //~1Ah0I~
//    P7 * +FU *  * +FU *  *  *  *                                 //~1Ah0I~
//    P8 * +OU+KI+GI *  * +HI *  *                                 //~1Ah0I~
//    P9+KY+KE *  *  *  *  * +KE *                                 //~1Ah0I~
//    P+00FU00FU                                                   //~1Ah0I~
//    P-00FU00FU00FU                                               //~1Ah0I~
//    +                                                            //~1Ah0I~
//    /                                                            //~1Ah0I~
//    $ANSWER:+0087KY:+0088KY                                      //~1Ah0I~
//    P1-OU-KE *  *  *  *  * +GI *                                 //~1Ah0I~
//    P2-KY-KI *  *  *  *  *  *  *                                 //~1Ah0I~
//    P3-FU-HI * -KI *  * -GI *  *                                 //~1Ah0I~
//    P4 *  * -KE *  *  *  *  * -FU                                //~1Ah0I~
//    P5 * +GI * -FU-FU-FU-FU-FU *                                 //~1Ah0I~
//    P6+FU+HI-FU *  *  *  *  *  *                                 //~1Ah0I~
//    P7 *  *  * +FU *  *  *  * +FU                                //~1Ah0I~
//    P8 *  * +OU+KI+KI *  *  *  *                                 //~1Ah0I~
//    P9+KY+KE *  *  *  *  * +KE+KY                                //~1Ah0I~
//    P+00KA00GI00KY00FU00FU                                       //~1Ah0I~
//    P-00KA00FU00FU00FU00FU00FU                                   //~1Ah0I~
//    +                                                            //~1Ah0I~
//    -----------------------------                                //~1Ah0I~
                                                                   //~1Ah0I~
//    The argument 'num' specifies the number of problems to solve.//~1Ah0I~
                                                                   //~1Ah0I~
//- quit                                                           //~1Ah0I~
//    The quit command and EOF character will exit Bonanza.        //~1Ah0I~
                                                                   //~1Ah0I~
//- read 'filename' [(t|nil) ['num']]                              //~1Ah0I~
//    This command is used to read a CSA record 'filename' up to 'num'//~1Ah0I~
//    moves. Set the second argument to "nil" when you want to ignore//~1Ah0I~
//    time information in the record. The default value is "t". Bonanza//~1Ah0I~
//    reads all move sequence if the last argument is neglected. If//~1Ah0I~
//    'filename' is ".", the command reads an ongoing game from the//~1Ah0I~
//    initial position.                                            //~1Ah0I~
                                                                   //~1Ah0I~
//- resign                                                         //~1Ah0I~
//    Use this command when you resign a game.                     //~1Ah0I~
                                                                   //~1Ah0I~
//- resign 'num'                                                   //~1Ah0I~
//    This command specifies the threshold to resign. 'num' is a value//~1Ah0I~
//    of the threshold. The default is around 1000.                //~1Ah0I~
                                                                   //~1Ah0I~
//- stress on                                                      //~1Ah0I~
//- stress off                                                     //~1Ah0I~
//    When the command "stress on" is used, last-move shown in shogi//~1Ah0I~
//    board is stressed. The default is on.                        //~1Ah0I~
                                                                   //~1Ah0I~
//- time remain 'num1' 'num2'                                      //~1Ah0I~
//    This command tells Bonanza the remaining time. 'num1' ('num2') is//~1Ah0I~
//    the remaining time of black (white) in seconds.              //~1Ah0I~
                                                                   //~1Ah0I~
//- time response 'num'                                            //~1Ah0I~
//    This command specifies a margin to control time. The time margin//~1Ah0I~
//    saves Bonanza from time up due to TCP/IP communication to a server//~1Ah0I~
//    program, sudden disc access, or imperfection of time control of//~1Ah0I~
//    Bonanza. 'num' is the time margin in milli-second. The default//~1Ah0I~
//    value is 200.                                                //~1Ah0I~
                                                                   //~1Ah0I~
//- tlp 'num'                                                      //~1Ah0I~
//    This command controls the number of threads to be created when//~1Ah0I~
//    Bonana considers a move to make. The command becomes effective//~1Ah0I~
//    when TLP macro is defined in the Makefile. 'num' is the number of//~1Ah0I~
//    threads. The default value is 1.                             //~1Ah0I~
                                                                   //~1Ah0I~
//- #                                                              //~1Ah0I~
//    A line beginning with # causes all characters on that line   //~1Ah0I~
//    to be ignored.                                               //~1Ah0I~
                                                                   //~1Ah0I~
//- [move command]                                                 //~1Ah0I~
//    A move command consists of four digits followed by two       //~1Ah0I~
//    capital alphabets, e.g. 7776FU. The first two digits         //~1Ah0I~
//    are a starting square and the last two are a target square. The//~1Ah0I~
//    starting square is "OO" if the  move is a dorp, e.g. 0087FU. The//~1Ah0I~
//    following two alphabets specify a piece type as the following,//~1Ah0I~
                                                                   //~1Ah0I~
//      FU - pawn             (Fuhyo)       TO - promoted pawn    (Tokin)//~1Ah0I~
//      KY - lance            (Kyousha)     NY - promoted lance   (Narikyo)//~1Ah0I~
//      KE - knight           (Keima)       NK - promoted knight  (Narikei)//~1Ah0I~
//      GI - silver general   (Ginsho)      NG - promoted silver  (Narigin)//~1Ah0I~
//      KI - gold general     (Kinsyo)                             //~1Ah0I~
//      KA - Bishop           (Kakugyo)     UM - Dragon horse     (Ryuma)//~1Ah0I~
//      HI - Rook             (Hisha)       RY - Dragon king      (Ryuo)//~1Ah0I~
//      OU - King             (Osho)                               //~1Ah0I~
                                                                   //~1Ah0I~
//    Here, words in parentheses are romanization of Japanese words.//~1Ah0I~
public class GtpGameQuestion extends GameQuestion                  //~1Ah0R~
	implements UnzipperI                                          //~1Ah0I~
//  	implements ProgDlgI                                        //~1Ah0R~
//{   FormTextField BoardSize,Handicap,TotalTime,ExtraTime,ExtraMoves;//~@@@@R~
{                                                                  //~@@@@I~
    private static final int TITLEID=R.string.Title_GtpGameQuestion;     //~1Ah0I~
    private static final String HELPFILE="GameQuestionBonanza";    //~1Ah0I~
    private static final int LAYOUTID=R.layout.gamequestion_bonanza;           //~1Ah0I~
    private static final String DEFAULT_GTPSERVER_ORGNAME="bonanza";//~1Ah0I~
	private static final String DEFAULT_GTPSERVER="Abonanza";      //~1Ah0R~
    private static final boolean DEFAULT_NOPONDER=false;
    private static final boolean DEFAULT_PRINTBOARD=false;         //~1Ah0I~
//    private static final int DEFAULT_RESIGN=1000;                //~1Ah0R~
    private static final int DEFAULT_THREAD=1;                     //~1Ah0I~
    private static final int MAX_THREAD=4;                         //+1Ai0I~
    private static final int LIMITMODE_DEFAULT=0;                  //~1Ah0R~
    private static final int LIMITMODE_DEPTH=1;                    //~1Ah0I~
    private static final int LIMITMODE_NODES=2;                    //~1Ah0I~
    private static final int LIMITMODE_TIME=3;                     //~1Ah0I~
    private static final int DEFAULT_LIMITMODE=LIMITMODE_DEFAULT;  //~1Ah0R~
    private static final int BOOKUSE_NARROW=1;
    private static final int BOOKUSE_WIDE=2;
    private static final int BOOKUSE_OFF=3;
    private static final int STRICTTIME_TRUE=1;
    private static final int STRICTTIME_FALSE=2;
    private static final int DEFAULT_STRICT_TIME=STRICTTIME_TRUE;
    private static final int DEFAULT_BOOKUSE=BOOKUSE_WIDE; 
//  private static final int DEFAULT_TOTALTIME=0;      //0min      //~1Ah0R~
//  private static final int DEFAULT_EXTRATIME=10;     //15sec     //~1Ah0R~
    private static final int MAX_TOTALTIME=60;        //1 hour     //~1Ah0I~
    private static final int MAX_EXTRATIME=3600;      //1 hour     //~1Ah0I~
    private static final int DEFAULT_LIMIT_DEPTH=128;     //=shogi.h:PLY_MAX//~1Ah0I~
    private static final int DEFAULT_LIMIT_NODES=50000000;         //~1Ah0R~
    private static final int MIN_RESIGN_THRESHOLD=174;             //~1Ah0I~
    private static final String PASS_FIRST_WHITE=NotesFmtCsa.CSA_COLOR_WHITE+"\n";//~1Ah0R~
    private static final String PASS_FIRST_BLACK=NotesFmtCsa.CSA_COLOR_BLACK+"\n";//~1Ah0I~
                                                                   //~1Ah0I~
    private static final String PKEY_GAMEOPTIONS="GtpGameOptions"; //~1Ah0R~
    private static final String PKEY_TOTALTIME="GtpTotalTime";     //~1Ah0R~
    private static final String PKEY_EXTRATIME="GtpExtraTime";     //~1Ah0R~
    private static final String PKEY_TOTALTIME_BONANZA="GtpTotalTimeBonanza";//~1Ah0R~
    private static final String PKEY_EXTRATIME_BONANZA="GtpExtraTimeBonanza";//~1Ah0R~
    private static final String PKEY_LIMITTIME_DEPTH="GtpLimitTimeDepth";//~1Ah0I~
    private static final String PKEY_HANDICAP="Gtphandicap";       //~1Ah0R~
    private static final String PKEY_GTPSERVER_ZIPSIZE="GtpServerZipSize";//~1Ah0I~
	private static final String PKEY_PROGRAM="Gtpprogram";         //~1Ah0I~
    private static final String PKEY_GTPSERVER_VERSION="GtpServerVersion";//~1Ah0I~
    private static final String PKEY_STRICT_TIME="GtpStrictTime";
    private static final String PKEY_LIMITMODE="GtpLimitMode";
    private static final String PKEY_BOOKUSE="GtpBookUse";
    private static final String PKEY_NOPONDER="GtpNoPonder";
    private static final String PKEY_PRINTBOARD="GtpPrintBoard";   //~1Ah0I~
    private static final String PKEY_RESIGN="GtpResign";           //~1Ah0R~
    private static final String PKEY_THREAD="GtpThread";           //~1Ah0I~
    private static final String PKEY_LIMIT_DEPTH="GtpLimitDepth";  //~1Ah0I~
    private static final String PKEY_LIMIT_NODES="GtpLimitNodes";  //~1Ah0I~
    private static final String POSTFIX_PIE=".pie";	//pie executable posfix
                                                                   //~1Ah0I~
    private static final String SUBCMD_BOOK_NARROW="book narrow";  //~1Ah0I~
    private static final String SUBCMD_BOOK_OFF="book off";        //~1Ah0I~
    private static final String SUBCMD_PONDER_ON="ponder on";      //~1Ah0I~
    private static final String SUBCMD_PONDER_OFF="ponder off";    //~1Ah0I~
    private static final String SUBCMD_RESIGN="resign ";           //~1Ah0R~
    private static final String SUBCMD_THREAD="tlp ";              //~1Ah0I~
    private static final String SUBCMD_DEPTH="limit depth ";       //~1Ah0I~
    private static final String SUBCMD_NODES="limit nodes ";       //~1Ah0I~
    private static final String SUBCMD_TIME="limit time ";         //~1Ah0I~
    private static final String SUBCMD_STRICT_ON="limit time strict";//~1Ah0I~
    private static final String SUBCMD_STRICT_OFF="limit time extendable";//~1Ah0I~
    private static final String SUBCMD_HANDICAP_FILE="board.csa";  //~1Ah0R~
    public  static final String SUBCMD_HANDICAP="read "+SUBCMD_HANDICAP_FILE+ " t 1";//~1Ah0R~
                                                                   //~1Ah0I~
	private static final String CMDLINEOPT_NOPRINTBOARD="-noprintboard";//~1Ah0I~
//    private static final String SUBCMD_MOVEFIRST="move";           //~1Ah0I~
                                                                   //~1Ah0I~
    private String program;                                        //~1Ah0R~
    private String cmdlineParm=CMDLINEOPT_NOPRINTBOARD;            //~1Ah0R~
	        String default_GTPSERVER_ORGNAME;                      //~1Ah0I~
	        String default_GTPSERVER;                              //~1Ah0R~
	        String pkey_PROGRAM;                                   //~1Ah0I~
            String pkey_GTPSERVER_ZIPSIZE;                         //~1Ah0I~
            String pkey_GTPSERVER_VERSION;                         //~1Ah0I~
            String pkey_NOPONDER,pkey_RESIGN,pkey_LIMITMODE,pkey_BOOKUSE,pkey_STRICT_TIME;//~1Ah0R~
            String pkey_PRINTBOARD;                                //~1Ah0I~
            String pkey_TOTALTIME_BONANZA,pkey_EXTRATIME_BONANZA,pkey_LIMITTIME_DEPTH;//~1Ah0R~
            String pkey_LIMIT_NODES,pkey_LIMIT_DEPTH,pkey_THREAD;              //~1Ah0I~
            String Sversion,opponentName;                          //~1Ah0I~
            boolean verbose;                                       //~1Ah0I~
            boolean dataError;                                     //~1Ah0I~
                                                                   //~1Ah0I~
            ButtonRG rgLimit,rgStrictTime,rgBookUse;               //~1Ah0I~
            FormTextField YourName,Resign,Thread;                  //~1Ah0R~
            Checkbox NoPonder;                                     //~1Ah0R~
            Checkbox PrintBoard;                                   //~1Ah0I~
			FormTextField LimitDepth,LimitNodes,TotalTimeBonanza,ExtraTimeBonanza,LimitTimeDepth;//~1Ah0R~
			LinearLayout llDebug;                                  //~1Ah0I~
			TextView tvShowOptions;                                //~1Ah0I~
			ArrayList<String> subcmdList;                          //~1Ah0I~
                                                                   //~1Ah0I~
	private MainFrame PF;                                          //~1Ah0I~
    GtpGoParm goParm;                                              //~1Ah0I~
    private boolean swUnzipping;                                   //~1Ah0I~
    private long zipsize;                                          //~1Ah0I~
    public String parmPath,parmFilename,initialPieces;             //~1Ah0R~
    public int parmChmod;                                          //~1Ah0I~
    private Unzipper unzipper;                                     //~1Ah0I~
//  private boolean swGetRealoadData;                              //~1Ah0R~
    private int limit,totaltimebonanza,extratimebonanza,limittimedepth;//~1Ah0I~
//******************************                                   //~1Ah0I~
//*for MainFrame                                                   //~1Ah0I~
//******************************                                   //~1Ah0I~
    public GtpGameQuestion (Frame pf)                              //~1Ah0I~
    {                                                              //~1Ah0I~
        super(pf,LAYOUTID,AG.resource.getString(TITLEID),          //~1Ah0I~
                false/*non modal*/);                               //~1Ah0I~
        PF=(MainFrame)pf;                                          //~1Ah0I~
    	setGameData();                                             //~1Ah0I~
        new ButtonAction(this,R.string.StartGame,R.id.Request);  //action name Request-->Start//~1Ah0I~
        new ButtonAction(this,0,R.id.ButtonReload);  //open saved file//~1Ah0I~
        new ButtonAction(this,0,R.id.Cancel);  //Cancel            //~1Ah0I~
        new ButtonAction(this,0,R.id.Help);  //Help                //~1Ah0I~
        new ButtonAction(this,0,R.id.HandicapButton);  //Request   //~1Ah0I~
        new ButtonAction(this,0,R.id.SHOW_OPTIONS);  //Request     //~1Ah0I~
        setDismissActionListener(this/*DoActionListener*/);        //~1Ah0I~
		show();                                                    //~1Ah0I~
    }                                                              //~1Ah0I~
    @Override                                                      //~1Ah0I~
    protected void initvars()                                      //~1Ah0I~
    {                                                              //~1Ah0I~
    	pkey_GAMEOPTIONS=PKEY_GAMEOPTIONS;                         //~1Ah0I~
    	pkey_TOTALTIME_BONANZA=PKEY_TOTALTIME_BONANZA;             //~1Ah0I~
    	pkey_EXTRATIME_BONANZA=PKEY_EXTRATIME_BONANZA;             //~1Ah0I~
    	pkey_LIMITTIME_DEPTH=PKEY_LIMITTIME_DEPTH;                 //~1Ah0I~
    	pkey_TOTAL_TIME=PKEY_TOTALTIME;                            //~1Ah0I~
    	pkey_EXTRA_TIME=PKEY_EXTRATIME;                            //~1Ah0I~
    	pkey_HANDICAP=PKEY_HANDICAP;                               //~1Ah0I~
        pkey_GTPSERVER_VERSION=PKEY_GTPSERVER_VERSION;          //~1Ah0I~
        pkey_STRICT_TIME=PKEY_STRICT_TIME;
        pkey_LIMITMODE=PKEY_LIMITMODE;
        pkey_BOOKUSE=PKEY_BOOKUSE;
        pkey_NOPONDER=PKEY_NOPONDER;
        pkey_PRINTBOARD=PKEY_PRINTBOARD;                           //~1Ah0I~
        pkey_RESIGN=PKEY_RESIGN;                                   //~1Ah0R~
        pkey_THREAD=PKEY_THREAD;                                   //~1Ah0I~
        pkey_LIMIT_DEPTH=PKEY_LIMIT_DEPTH;                         //~1Ah0I~
        pkey_LIMIT_NODES=PKEY_LIMIT_NODES;                         //~1Ah0I~
                                                                           //~1Ah0I~
        pkey_PROGRAM=PKEY_PROGRAM;                                //~1Ah0I~
        pkey_GTPSERVER_ZIPSIZE=PKEY_GTPSERVER_ZIPSIZE;         //~1Ah0I~
	    default_GTPSERVER_ORGNAME=DEFAULT_GTPSERVER_ORGNAME;       //~1Ah0I~
	    default_GTPSERVER=DEFAULT_GTPSERVER;                       //~1Ah0I~
        Sversion=Prop.getPreference(pkey_GTPSERVER_VERSION,"");    //~1Ah0I~
        if (Sversion.equals(""))                                   //~1Ah0I~
        {                                                          //~1Ah0I~
        	Sversion=GtpFrame.Sversion;                       //~1Ah0I~
	        if (!Sversion.equals(""))                              //~1Ah0I~
		        Prop.putPreference(pkey_GTPSERVER_VERSION,Sversion);//~1Ah0I~
        }                                                          //~1Ah0I~
        subcmdList=new ArrayList<String>();                        //~1Ah0I~
    	boardsz=AG.BOARDSIZE_SHOGI;                                //~1Ah0I~
    }                                                              //~1Ah0I~
    @Override                                                      //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    public void setGameData()                                      //~1Ah0R~
    {                                                              //~1Ah0I~
		int oldint;                                                //~1Ah0I~
        boolean oldYN;                                             //~1Ah0I~
		String oldstr,s;                                             //~1Ah0I~
    //**********************                                       //~1Ah0I~
    	initvars();                                                //~1Ah0I~
    //*Yourname                                                    //~1Ah0I~
        YourName=new FormTextField(this,R.id.YourName,AG.YourName);//~1Ah0I~
        YourName.setBackground(COLOR_YOURNAME);                    //~1Ah0I~
    //*Opponent                                                    //~1Ah0I~
	    oldstr=default_GTPSERVER_ORGNAME+" "+Sversion;             //~1Ah0I~
        OpponentName=new FormTextField(this,R.id.OpponentName,oldstr);//~1Ah0I~
        opponentName=oldstr;                                       //~1Ah0I~
//      v=findViewById(R.id.LocalOpponentNameLine);                //~1Ah0I~
//      Component dmy=new Component(); //no callback(dialog has callback then cause invalidcast exception at Dialog:runOnUithread)//~1Ah0I~
//      dmy.setVisibility(v,View.VISIBLE);                         //~1Ah0I~
	//*handicap                                                    //~1Ah0I~
    	Handicap=Prop.getPreference(pkey_HANDICAP,0);              //~1Ah0I~
        String hcs=getHadicapString(Handicap);                     //~1Ah0I~
		HandicapText=new FormTextField(this,R.id.HandicapView,hcs);//~1Ah0I~
	//*totaltime                                                   //~1Ah0I~
    	oldint=Prop.getPreference(pkey_TOTAL_TIME,0);               //~1Ah0R~
        s=Integer.toString(oldint);                                //~1Ah0I~
        TotalTime=new FormTextField(this,R.id.TotalTime,s);        //~1Ah0I~
	//*extra                                                       //~1Ah0I~
    	oldint=Prop.getPreference(pkey_EXTRA_TIME,0);               //~1Ah0R~
        s=Integer.toString(oldint);                                //~1Ah0I~
        ExtraTime=new FormTextField(this,R.id.ExtraTime,s);        //~1Ah0I~
    //*Limit                                                       //~1Ah0I~
        initLimitRG();                                             //~1Ah0I~
    //*1stmove                                                     //~1Ah0I~
        MoveFirst=new Checkbox(this,R.id.MoveFirst);               //~1Ah0I~
    	oldint=Prop.getPreference(pkey_GAMEOPTIONS,GAMEOPT_MOVEFIRST|GAMEOPT_BIGTIMER);//~1Ah0I~
        gameoptions=oldint;                                         //~1Ah0I~
        MoveFirst.setState((gameoptions & GAMEOPT_MOVEFIRST)!=0);  //~1Ah0R~
    //*notify checkmate                                            //~1Ah0I~
        NotifyCheck=new Checkbox(this,R.id.NotifyCheck);           //~1Ah0I~
        NotifyCheck.setState((gameoptions & GAMEOPT_NOTIFYCHECK)!=0);//~1Ah0R~
    //*bigtimer                                                    //~1Ah0I~
        BigTimer=new Checkbox(this,R.id.BigTimer);                 //~1Ah0I~
        BigTimer.setState((gameoptions & GAMEOPT_BIGTIMER)!=0);    //~1Ah0I~
    //*book                                                        //~1Ah0I~
        rgBookUse=new ButtonRG((ViewGroup)layoutview,3);                 //~1Ah0I~
        rgBookUse.add(BOOKUSE_WIDE,R.id.BOOKUSE_ON);//~1AfcI~//~1Ah0I~
        rgBookUse.add(BOOKUSE_NARROW,R.id.BOOKUSE_NARROW);        //~1Ah0I~
        rgBookUse.add(BOOKUSE_OFF,R.id.BOOKUSE_OFF);              //~1Ah0I~
        rgBookUse.setDefault(DEFAULT_BOOKUSE);                     //~1Ah0R~
    	oldint=Prop.getPreference(pkey_BOOKUSE,DEFAULT_BOOKUSE);   //~1Ah0M~
    	rgBookUse.setChecked(oldint);                              //~1Ah0I~
    //*NoPonder                                                    //~1Ah0I~
        NoPonder=new Checkbox(this,R.id.NOPONDERING);              //~1Ah0M~
    	oldYN=Prop.getPreference(pkey_NOPONDER,DEFAULT_NOPONDER);  //~1Ah0I~
        NoPonder.setState(oldYN);                                //~1Ah0I~
    //*Resign                                                      //~1Ah0R~
    	oldint=Prop.getPreference(pkey_RESIGN,0);                  //~1Ah0R~
        oldstr=(oldint==0) ? "" : Integer.toString(oldint);        //~1Ah0I~
		Resign=new FormTextField(this,R.id.RESIGN,oldstr);         //~1Ah0I~
    //*Thread                                                      //~1Ah0I~
    	oldint=Prop.getPreference(pkey_THREAD,0);                  //~1Ah0R~
        oldstr=(oldint==0) ? "" : Integer.toString(oldint);        //~1Ah0I~
		Thread=new FormTextField(this,R.id.THREAD,oldstr);         //~1Ah0I~
    //*PrintBoard                                                  //~1Ah0I~
        PrintBoard=new Checkbox(this,R.id.PRINTBOARD);             //~1Ah0I~
    	oldYN=Prop.getPreference(pkey_PRINTBOARD,DEFAULT_PRINTBOARD);//~1Ah0I~
        PrintBoard.setState(oldYN);                                //~1Ah0I~
    //*showOptions                                                 //~1Ah0I~
	    llDebug=(LinearLayout)layoutview.findViewById(R.id.DEBUG_OPTIONS);//~1Ah0R~
	    if (!AG.isDebuggable)                                      //~1Ah0I~
        	llDebug.setVisibility(View.GONE);                      //~1Ah0I~
	    tvShowOptions=(TextView)layoutview.findViewById(R.id.OPTION_DISPLAY);//~1Ah0I~
	}                                                              //~1Ah0I~
    //**********************************************************************//~1Ah0I~
           void initLimitRG()                                      //~1Ah0R~
    {                                                              //~1Ah0I~
    	int oldint;                                                //~1Ah0I~
        String s;                                                  //~1Ah0I~
		rgLimit=new ButtonRG((ViewGroup)layoutview,4);                    //~1AfcI~//~1Ah0R~
        rgLimit.add(LIMITMODE_DEFAULT,R.id.LIMIT_DEFAULT_RB);      //~1Ah0I~
        rgLimit.add(LIMITMODE_DEPTH,R.id.LIMIT_DEPTH_RB);//~1AfcI~//~1Ah0I~
        rgLimit.add(LIMITMODE_NODES,R.id.LIMIT_NODES_RB);//~1AfcI~//~1Ah0I~
        rgLimit.add(LIMITMODE_TIME,R.id.LIMIT_TIME_RB);//~1AfcI~ //~1Ah0I~
        rgLimit.setDefault(DEFAULT_LIMITMODE);                   //~1AfcI~//~1Ah0R~
    	oldint=Prop.getPreference(pkey_LIMITMODE,DEFAULT_LIMITMODE);       //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:initLimitRG old limitid="+oldint);//~1Ah0I~
    	rgLimit.setChecked(oldint);                                //~1Ah0I~
                                                                   //~1Ah0I~
    	oldint=Prop.getPreference(pkey_LIMIT_DEPTH,DEFAULT_LIMIT_DEPTH);//~1Ah0R~
        if (oldint==0)                                             //~1Ah0R~
        	oldint=DEFAULT_LIMIT_DEPTH;                            //~1Ah0I~
        s=Integer.toString(oldint);                                //~1Ah0I~
		LimitDepth=new FormTextField(this,R.id.LIMIT_DEPTH_ET,s);  //~1Ah0R~
                                                                   //~1Ah0I~
    	oldint=Prop.getPreference(pkey_LIMIT_NODES,DEFAULT_LIMIT_NODES);//~1Ah0R~
        if (oldint==0)                                             //~1Ah0I~
        	oldint=DEFAULT_LIMIT_NODES;                            //~1Ah0I~
        s=Integer.toString(oldint);                                //~1Ah0R~
		LimitNodes=new FormTextField(this,R.id.LIMIT_NODES_ET,s);  //~1Ah0R~
                                                                   //~1Ah0I~
    	oldint=Prop.getPreference(pkey_TOTALTIME_BONANZA,0);       //~1Ah0R~
        s=Integer.toString(oldint);                                //~1Ah0R~
        TotalTimeBonanza=new FormTextField(this,R.id.TotalTimeBonanza,s);//~1Ah0R~
                                                                   //~1Ah0I~
    	oldint=Prop.getPreference(pkey_EXTRATIME_BONANZA,0);       //~1Ah0R~
        s=Integer.toString(oldint);                                //~1Ah0R~
        ExtraTimeBonanza=new FormTextField(this,R.id.ExtraTimeBonanza,s);//~1Ah0R~
                                                                   //~1Ah0I~
    	oldint=Prop.getPreference(pkey_LIMITTIME_DEPTH,0);         //~1Ah0I~
        s=Integer.toString(oldint);                                //~1Ah0I~
        LimitTimeDepth=new FormTextField(this,R.id.LimitTimeDepth,s);//~1Ah0I~
                                                                   //~1Ah0I~
		rgStrictTime=new ButtonRG((ViewGroup)layoutview,2);                   //~1Ah0I~
        rgStrictTime.add(STRICTTIME_TRUE,R.id.LIMIT_TIME_STRICT);  //~1Ah0I~
        rgStrictTime.add(STRICTTIME_FALSE,R.id.LIMIT_TIME_EXPANDABLE);//~1Ah0I~
        rgStrictTime.setDefault(DEFAULT_STRICT_TIME);              //~1Ah0I~
    	oldint=Prop.getPreference(pkey_STRICT_TIME,DEFAULT_STRICT_TIME);//~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:initLimitRG old stricttime="+oldint);//~1Ah0I~
    	rgStrictTime.setChecked(oldint);                           //~1Ah0I~
	}//initLimitRG()                                               //~1Ah0R~
    //*********************************************************    //~@@@2I~//~1Ah0M~
    @Override                                                      //~1Ah0M~
    public boolean getGameData()                                   //~@@@2I~//~1Ah0I~
    {                                                              //~@@@2I~//~1Ah0M~
    	int newint;                                        //~1Ah0I~
        boolean newYN;                                             //~1Ah0I~
        boolean swNoHumanTimeLimit=false;                          //~1Ah0I~
        String s;                                                  //~1Ah0I~
    //***************************                                  //~1Ah0I~
    	dataError=false;                                           //~1Ah0I~
    	subcmdList.clear();                                        //~1Ah0I~
        gameoptions=0;                                             //~1Ah0R~
	//*handicap                                                    //~1Ah0I~
        Prop.putPreference(pkey_HANDICAP,Handicap);                //~1Ah0R~
//  	if (!swGetRealoadData)                                     //~1Ah0R~
      		setHandicapPiece();                                    //~1Ah0R~
	//*totaltime                                                   //~1Ah0I~
		s=TotalTime.getText().trim();                              //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_TOTAL_TIME,newint);                //~1Ah0R~
        totaltime=newint;                                          //~1Ah0I~
	//*extratime                                                   //~1Ah0I~
		s=ExtraTime.getText().trim();                              //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_EXTRA_TIME,newint);                //~1Ah0R~
        extratime=newint;                                          //~1Ah0I~
                                                                   //~1Ah0I~
        if (totaltime==0 && extratime==0)                          //~1Ah0I~
        {                                                          //~1Ah0I~
        	swNoHumanTimeLimit=true;                               //~1Ah0I~
        	totaltime=MAX_TOTALTIME;                               //~1Ah0I~
        	extratime=MAX_EXTRATIME;                               //~1Ah0I~
        }                                                          //~1Ah0I~
    //*Limit                                                       //~1Ah0I~
        if (!getLimitRG())                                         //~1Ah0R~
        	dataError=true;                                        //~1Ah0I~
    //*MoveFirst                                                   //~1Ah0I~
        newYN=MoveFirst.getState();                                //~1Ah0I~
        if (newYN)                                             //~1Ah0I~
        	gameoptions|=GAMEOPT_MOVEFIRST;                        //~1Ah0R~
        else                                                       //~1Ah0I~
        	gameoptions&=~GAMEOPT_MOVEFIRST;                       //~1Ah0R~
    //*NotifyCheck                                                 //~1Ah0I~
        newYN=NotifyCheck.getState();                              //~1Ah0I~
        if (newYN)                                                 //~1Ah0I~
        	gameoptions|=GAMEOPT_NOTIFYCHECK;                      //~1Ah0R~
        else                                                       //~1Ah0I~
        	gameoptions&=~GAMEOPT_NOTIFYCHECK;                     //~1Ah0R~
    //*BigTimer                                                    //~1Ah0I~
        bigtimer=BigTimer.getState()?GAMEOPT_BIGTIMER:0;           //~1Ah0I~
        gameoptions|=bigtimer;                                     //~1Ah0I~
    //*book                                                        //~1Ah0I~
        newint=rgBookUse.getChecked();                                //~1Ah0I~
    	Prop.putPreference(pkey_BOOKUSE,newint);                   //~1Ah0I~
        if (newint==BOOKUSE_NARROW)                                //~1Ah0I~
	        subcmdList.add(SUBCMD_BOOK_NARROW);                     //~1Ah0I~
        else                                                       //~1Ah0I~
        if (newint==BOOKUSE_OFF)                                   //~1Ah0I~
	        subcmdList.add(SUBCMD_BOOK_OFF);                        //~1Ah0I~
    //*NoPonder                                                    //~1Ah0I~
        newYN=NoPonder.getState();                                 //~1Ah0I~
    	Prop.putPreference(pkey_NOPONDER,newYN);                   //~1Ah0R~
        if (newYN)                                                 //~1Ah0I~
	        subcmdList.add(SUBCMD_PONDER_OFF);                      //~1Ah0I~
        else                                                       //~1Ah0I~
	        subcmdList.add(SUBCMD_PONDER_ON);                       //~1Ah0I~
    //*Resign                                                      //~1Ah0R~
		s=Resign.getText().trim();                                 //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
        if (newint!=0 && newint<MIN_RESIGN_THRESHOLD)              //~1Ah0R~
        {                                                          //~1Ah0I~
            String msg=AG.resource.getString(R.string.ErrResignThresholdTooSmall,MIN_RESIGN_THRESHOLD);//~1Ah0I~
            AView.showToastLong(msg);                              //~1Ah0I~
        	dataError=true;                                        //~1Ah0I~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        {                                                          //~1Ah0I~
            Prop.putPreference(pkey_RESIGN,newint);                //~1Ah0I~
        	if (newint!=0)                                         //~1Ah0R~
	        	subcmdList.add(SUBCMD_RESIGN+newint);              //~1Ah0R~
        }                                                          //~1Ah0I~
    //*Thread                                                      //~1Ah0I~
		s=Thread.getText().trim();                                 //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
        if (newint>MAX_THREAD)                                     //+1Ai0I~
        {                                                          //+1Ai0I~
            String msg=AG.resource.getString(R.string.ErrThreadOver,MAX_THREAD);//+1Ai0I~
            AView.showToastLong(msg);                              //+1Ai0I~
        	dataError=true;                                        //+1Ai0I~
        }                                                          //+1Ai0I~
    	Prop.putPreference(pkey_THREAD,newint);                    //~1Ah0I~
        if (newint!=0 && newint!=DEFAULT_THREAD)                   //~1Ah0R~
	        subcmdList.add(SUBCMD_THREAD+newint);                  //~1Ah0I~
    //*PrintBoard                                                  //~1Ah0I~
	    cmdlineParm=getPrintOption();                              //~1Ah0I~
    //gameoptions                                                  //~1Ah0R~
    	Prop.putPreference(pkey_GAMEOPTIONS,gameoptions);           //~1Ah0I~
    //gameoptions not to write to pref                             //~1Ah0I~
        if (Handicap!=0)   //white 1st                             //~1Ah0M~
        {                                                          //~1Ah0M~
        	if ((gameoptions & GAMEOPT_MOVEFIRST)!=0)              //~1Ah0I~
        		gameoptions|=GAMEOPT_COMPUTERFIRST;                 //~1Ah0I~
        }                                                          //~1Ah0M~
        else	//black 1st                                        //~1Ah0M~
        {                                                          //~1Ah0M~
        	if ((gameoptions & GAMEOPT_MOVEFIRST)==0)              //~1Ah0I~
        		gameoptions|=GAMEOPT_COMPUTERFIRST;                 //~1Ah0I~
        }                                                          //~1Ah0M~
        if (bigtimer==0 && swNoHumanTimeLimit)                     //~1Ah0I~
        	gameoptions |= GAMEOPT_NOBIGTIMER;	//ignor max timelimit setting//~1Ah0I~
                                                                   //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:getGameData gameOption="+Integer.toHexString(gameoptions));//~1Ah0R~
    	return dataError;                                          //~1Ah0I~
    }//getGameData()                                               //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    private String getPrintOption()                                //~1Ah0I~
    {                                                              //~1Ah0I~
    	String parm="";                                            //~1Ah0I~
	    if (AG.isDebuggable)                                       //~1Ah0M~
        {                                                          //~1Ah0M~
            boolean newYN=PrintBoard.getState();                           //~1Ah0M~
            Prop.putPreference(pkey_PRINTBOARD,newYN);             //~1Ah0M~
            if (!newYN)                                            //~1Ah0I~
                parm=CMDLINEOPT_NOPRINTBOARD;                      //~1Ah0I~
        }                                                          //~1Ah0M~
        if (Dump.Y) Dump.println("GGQ.getPrintOption parm="+parm); //~1Ah0I~
        return parm;                                               //~1Ah0I~
    }                                                              //~1Ah0I~
    //**********************************************************************//~1Ah0I~
           boolean getLimitRG()                                    //~1Ah0R~
    {                                                              //~1Ah0I~
    	int newint,depth,nodes;                                    //~1Ah0R~
        String s;                                                  //~1Ah0I~
    //******************************                               //~1Ah0I~
        newint=rgLimit.getChecked();                               //~1Ah0I~
    	Prop.putPreference(pkey_LIMITMODE,newint);                 //~1Ah0I~
        limit=newint;                                              //~1Ah0I~
                                                                   //~1Ah0I~
		s=LimitDepth.getText().trim();                             //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_LIMIT_DEPTH,newint);               //~1Ah0I~
        depth=(newint==0) ? DEFAULT_LIMIT_DEPTH : newint;          //~1Ah0I~
                                                                   //~1Ah0I~
		s=LimitNodes.getText().trim();                             //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_LIMIT_NODES,newint);               //~1Ah0I~
        nodes=(newint==0) ? DEFAULT_LIMIT_NODES : newint;          //~1Ah0I~
                                                                   //~1Ah0I~
		s=TotalTimeBonanza.getText().trim();                       //~1Ah0R~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_TOTALTIME_BONANZA,newint);         //~1Ah0R~
        totaltimebonanza=newint;                                   //~1Ah0R~
                                                                   //~1Ah0I~
		s=ExtraTimeBonanza.getText().trim();                       //~1Ah0R~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_EXTRATIME_BONANZA,newint);         //~1Ah0R~
        extratimebonanza=newint;                                   //~1Ah0R~
                                                                   //~1Ah0I~
		s=LimitTimeDepth.getText().trim();                         //~1Ah0I~
    	newint=s2i(s);                                             //~1Ah0I~
    	Prop.putPreference(pkey_LIMITTIME_DEPTH,newint);           //~1Ah0I~
        limittimedepth=newint;                                     //~1Ah0I~
                                                                   //~1Ah0I~
        if (limit==LIMITMODE_DEFAULT)                              //~1Ah0I~
        	;                                                      //~1Ah0I~
        if (limit==LIMITMODE_DEPTH)                                //~1Ah0I~
        	subcmdList.add(SUBCMD_DEPTH+depth);                    //~1Ah0I~
        else                                                       //~1Ah0I~
        if (limit==LIMITMODE_NODES)                                //~1Ah0I~
        	subcmdList.add(SUBCMD_NODES+nodes);                    //~1Ah0I~
        else                                                       //~1Ah0I~
        if (limit==LIMITMODE_TIME)                                 //~1Ah0I~
        {                                                          //~1Ah0I~
        	if (extratimebonanza==0 && totaltimebonanza==0)        //~1Ah0R~
            {                                                      //~1Ah0I~
            	AView.showToast(R.string.ErrMissingTotalExtraTimeBonanza);//~1Ah0R~
            	return false;                                      //~1Ah0I~
            }                                                      //~1Ah0I~
            if (limittimedepth==0)                                 //~1Ah0I~
	        	subcmdList.add(SUBCMD_TIME+totaltimebonanza+" "+extratimebonanza);//~1Ah0R~
            else                                                   //~1Ah0I~
	        	subcmdList.add(SUBCMD_TIME+totaltimebonanza+" "+extratimebonanza+" "+limittimedepth);//~1Ah0I~
            newint=rgStrictTime.getChecked();                      //~1Ah0R~
            Prop.putPreference(pkey_STRICT_TIME,newint);           //~1Ah0R~
            if (newint==STRICTTIME_TRUE)                           //~1Ah0R~
                subcmdList.add(SUBCMD_STRICT_ON);                  //~1Ah0R~
            else                                                   //~1Ah0R~
                subcmdList.add(SUBCMD_STRICT_OFF);                 //~1Ah0R~
                                                                   //~1Ah0I~
//            if (extratimebonanza>=extratime || totaltimebonanza>=totaltime || strictmode!=STRICTTIME_TRUE)//~1Ah0R~
//            {                                                    //~1Ah0R~
//                AView.showToast(R.string.ErrHumanAndComputerTime);//~1Ah0R~
//                return false;                                    //~1Ah0R~
//            }                                                    //~1Ah0R~
        }                                                          //~1Ah0I~
        return true;                                               //~1Ah0I~
	}//getLimitRG()                                                //~1Ah0I~
    //*****************************************************************//~1Ah0I~
    @Override                                                      //~1Ah0I~
	public void doAction (String o)
    {                                                              //~@@@@I~
        if (Dump.Y) Dump.println("GtpGameQuestion:doAction="+o);   //~1Ah0I~
        if (o.equals(AG.resource.getString(R.string.StartGame)))   //~1Ah0I~
		{                                                          //~@@@@R~
            play();                                                //~1Ah0I~
		}
        else if (o.equals(AG.resource.getString(R.string.Help)))   //~2C30I~//~@@@@I~
		{                                                          //~2C30I~//~@@@@I~
            new HelpDialog(null,R.string.HelpTitle_GtpGameQuestion,HELPFILE);                   //~v1A0I~//~1A00R~//~1Ah0R~
		}                                                          //~2C30I~//~@@@@I~
        else if (o.equals(AG.resource.getString(R.string.ButtonReload)))//~1A24I~
		{                                                          //~1A24I~
//      	swGetRealoadData=true;                                 //~1Ah0R~
//   	   	if (!getGameData())  //get subcmdlist,no err           //~1Ah0R~
//          {	                                                   //~1Ah0R~
	        	swReloadGame=true;		//after dismiss ,call FileDialog//~1A24I~//~1Ah0I~
				setVisible(false); dispose(); //dismiss                //~1A24I~//~1Ah0R~
//          }                                                      //~1Ah0R~
//      	swGetRealoadData=false;                                //~1Ah0R~
		}                                                          //~1A24I~
        else if (o.equals(AG.resource.getString(R.string.ShowOptions)))//~1Ah0I~
		{                                                          //~1Ah0I~
			showOptions();                                         //~1Ah0I~
		}                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        if (o.equals(AG.resource.getString(R.string.ActionDismissDialog)))	//modal but no inputWait//~@@@@R~//~1A24I~
		{                                                          //~1Ah0R~
        	if (Dump.Y) Dump.println("GtpGameQuestion:dismiss");    //~1Ah0I~
            if (unzipper!=null)                                    //~1Ah0I~
	            unzipper.dismissMsgDialog();                       //~1Ah0R~
        	if (swReloadGame)                                      //~1A24I~//~1Ah0I~
	            reloadFileDialog();	//GameQuestion                                //~1A24R~//~1Ah0I~
		}                                                          //~@@@@I~//~1A24I~
		else super.doAction(o);
	}
    //*********************************************************    //~1Ah0I~
    	void showOptions()                                         //~1Ah0I~
    {                                                              //~1Ah0I~
        getparm();                                                 //~1Ah0I~
        StringBuffer sb=new StringBuffer();                       //~1Ah0I~
        sb.append("CmdlineOption="+ cmdlineParm+"\n");              //~1Ah0I~
        for (String s:subcmdList)                                  //~1Ah0I~
        {                                                          //~1Ah0I~
        	sb.append(s);                                          //~1Ah0I~
            sb.append(" ; ");                                      //~1Ah0R~
        }                                                          //~1Ah0I~
        String sc=sb.toString();                                   //~1Ah0I~
        if (initialPieces!=null)                                   //~1Ah0I~
        	sc=sc+"\n"+initialPieces;                              //~1Ah0R~
        tvShowOptions.setText(sc);                                 //~1Ah0R~
    }                                                              //~1Ah0I~
    //*********************************************************    //~1Ah0I~
    	void play()                                                //~1Ah0I~
    {                                                              //~1Ah0I~
		program=getCommand();                                      //~1Ah0I~
        getparm();                                                 //~1Ah0I~
        if (dataError)                                             //~1Ah0I~
        	return;                                                //~1Ah0I~
        if (!swUnzipping)                                          //~1Ah0I~
        {                                                          //~1Ah0I~
            setVisible(false); dispose();                          //~1Ah0I~
            gtpconnection();                                       //~1Ah0I~
        }                                                          //~1Ah0I~
    }//play                                                        //~1Ah0I~
    //*********************************************************    //~1Ah0I~
     private void getparm()                                         //~1Ah0I~
	{                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGameQuestion:gtpparm");       //~1Ah0I~
 	   	getGameData();                                              //~1Ah0I~
        int color=((gameoptions & GAMEOPT_MOVEFIRST)!=0) ? Board.COLOR_BLACK : Board.COLOR_WHITE;//~1Ah0R~
//      goParm=new GtpGoParm(color,boardsz,totaltime*60,extratime,gameoptions,Handicap,verbose);//~1Ah0R~
        goParm=new GtpGoParm(color,boardsz,totaltime*60,extratime,gameoptions,Handicap);//~1Ah0I~
        goParm.setName(AG.YourName,opponentName);                  //~1Ah0I~
        goParm.setBonanzaOption(subcmdList,limit,totaltimebonanza,extratimebonanza,limittimedepth);//~1Ah0I~
	}                                                              //~1Ah0I~
    //*********************************************************    //~1Ah0I~
    private void gtpconnection()                                   //~1Ah0I~
	{                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGameQuestion:gtpconnection"); //~1Ah0I~
        GtpFrame gf=new GtpFrame(PF,goParm);                       //~1Ah0R~
        gf.play(program,cmdlineParm,subcmdList);                   //~1Ah0R~
	}                                                              //~1Ah0I~
//************************************************************************//~1Ah0I~
	public String getCommand()                                     //~1Ah0I~
	{                                                              //~1Ah0I~
        String pgm;                                                //~1Ah0I~
    //***************************                                  //~1Ah0I~
        if (Dump.Y) Dump.println("GTPGameQuestion:getCommand");    //~1Ah0I~
		installDefaultProgram();//if first time                    //~1Ah0R~
    	pgm=Prop.getDataFileFullpath(getPgmPIE(default_GTPSERVER));//~1Ah0I~
        if (Dump.Y) Dump.println("GTPConnection:getCommand pgm="+pgm);//~1Ah0I~
		return pgm;                                                //~1Ah0I~
    }//getCommand                                                  //~1Ah0I~
//************************************************************************//~1Ah0I~
//*rc:false:async unzip shecduled                                  //~1Ah0I~
//************************************************************************//~1Ah0I~
    protected boolean installDefaultProgram()                      //~1Ah0R~
	{                                                              //~1Ah0I~
        String path,assetfnm;                                      //~1Ah0I~
        long fsz,fsza;                                             //~1Ah0I~
        boolean rc=true;                                           //~1Ah0I~
    //***************************                                  //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:installDefaultProgram");//~1Ah0I~
        long fszunzip=Prop.getDataFileSize(default_GTPSERVER);//~1Ah0I~
        path=Prop.getDataFileFullpath("");                    //~1Ah0I~
        fsz=Prop.getPreference(pkey_GTPSERVER_ZIPSIZE,0);  //~1Ah0I~
        assetfnm=default_GTPSERVER+".png";	//avoid compress at making apk; if compressed openAssetFD failes by FileNotFound with reason it is compressed file//~1Ah0I~
        if (Dump.Y) Dump.println(assetfnm);                        //~1Ah0I~
        fsza=Prop.getAssetFileSize(assetfnm);                 //~1Ah0I~
        zipsize=fsza;                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:installDefaultProgram getPref="+fsz+",assetfsz="+fsza+",unzipfnm="+default_GTPSERVER+",unzip="+fszunzip);//~1Ah0I~
        if (fsza==-1)	//no asset file                            //~1Ah0I~
        {                                                          //~1Ah0I~
        	AView.showToast(R.string.GtpErr_MissingAssetPGM,assetfnm);//~1Ah0I~
        }                                                          //~1Ah0I~
        else                                                       //~1Ah0I~
        if (fsz!=fsza||fszunzip<=0)  //updated                     //~1Ah0I~
        {                                                          //~1Ah0I~
            if (Dump.Y) Dump.println("GtpGameQuestion install start");//~1Ah0R~
            swUnzipping=true;                                      //~1Ah0R~
			unzipAsset(path,assetfnm,0777,zipsize);                        //~1Ah0I~
	        Prop.putPreference(pkey_GTPSERVER_ZIPSIZE,(int)fsza);//~1Ah0I~
	        Prop.putPreference(pkey_GTPSERVER_VERSION,"");  //reset registered version//~1Ah0I~
            Sversion="";                                           //~1Ah0I~
            fsz=fsza;                                              //~1Ah0M~
            rc=false;                                              //~1Ah0I~
        }                                                          //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:installProgram normal return rc="+rc);//~1Ah0R~
        return rc;                                                 //~1Ah0R~
	}//installDefaultProgram                                       //~1Ah0I~
//************************************************************************//~1Ah0I~
	private static String getPgmPIE(String Ppgmnonpie)             //~1Ah0R~
    {                                                              //~1Ah0I~
    	String pgm=Ppgmnonpie;                                     //~1Ah0I~
        boolean optPIE=false;                                    
        optPIE=(AG.osVersion>=AG.LOLLIPOP);                      
        if (optPIE)                                              
            pgm+=POSTFIX_PIE;                                    
        if (Dump.Y) Dump.println("GTPConnection:getPgmPIE in="+Ppgmnonpie+",out="+pgm+",osVersion="+AG.osVersion+",optPIE="+optPIE);
        return pgm;                                                //~1Ah0I~
    }                                                              //~1Ah0I~
    //********************************************                 //~1Ah0I~
	private void unzipAsset(String Ppath,String Passetfnm,int Pchmodmask,long Pzipsize)//~1Ah0I~
    {                                                              //~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGameQuestion:unzipAsset path="+Ppath+",asset name="+Passetfnm);//~1Ah0I~
        unzipper=new Unzipper(this,Ppath,Passetfnm,Pchmodmask,Pzipsize);//~1Ah0R~
    }                                                              //~1Ah0I~
	public void unzipCompleted()                                   //~1Ah0R~
    {                                                              //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:unzipCompleted");//~1Ah0I~
        swUnzipping=false;                                         //~1Ah0I~
        doAction(AG.resource.getString(R.string.StartGame));       //~1Ah0R~
    }                                                              //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    	int s2i(String Pstr)                                       //~1Ah0I~
    {                                                              //~1Ah0I~
    	int rc;                                                    //~1Ah0I~
        if (Pstr.equals(""))                                       //~1Ah0I~
        	return 0;                                              //~1Ah0I~
    	try                                                        //~1Ah0I~
        {                                                          //~1Ah0I~
        	rc=Integer.parseInt(Pstr.trim());                      //~1Ah0R~
        }                                                          //~1Ah0I~
        catch (NumberFormatException ex)                           //~1Ah0I~
        {                                                          //~1Ah0I~
        	dataError=true;                                        //~1Ah0I~
            AView.showToast(R.string.ParmErr,Pstr);                //~1Ah0R~
            return 0;                                              //~1Ah0I~
        }                                                          //~1Ah0I~
    	return rc;                                                 //~1Ah0I~
    }                                                              //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    private	void setHandicapPiece()                                //~1Ah0R~
    {                                                              //~1Ah0I~
    	int posx,posy;                                              //~1Ah0I~
    //************************                                     //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:setHandicapPiece Handicap="+Integer.toHexString(Handicap));//~1Ah0I~
        if (Handicap==0)                                           //~1Ah0I~
        {                                                          //~1Ah0I~
        	initialPieces=null;                                    //~1Ah0R~
            return;                                                //~1Ah0I~
        }                                                          //~1Ah0I~
        String initial=NotesFmtCsa.getInitialPieceArangement();   //~1Ah0I~
        StringBuffer sb=new StringBuffer(initial);                 //~1Ah0I~
		for (int jj=0;jj<Field.INITIAL_STAFF_LINE;jj++)            //~1Ah0I~
        {                                                          //~1Ah0I~
            for (int ii=0;ii<boardsz;ii++)                         //~1Ah0I~
            {                                                      //~1Ah0I~
                int piecetype=Field.INITIAL_STAFF[jj][ii];         //~1Ah0I~
                if (piecetype==0)                                  //~1Ah0I~
                	continue;                                      //~1Ah0I~
              	if (HandicapQuestion.isHandicapPiece(Handicap,jj,ii))//on black position from bellow//~1Ah0R~
                {                                                  //~1Ah0I~
                	if (Dump.Y) Dump.println("GtpGameQuestion:setHandicapPiece jj="+jj+",ii="+ii+",piece="+piecetype);//~1Ah0R~
//for bonanza, initail piese drop from n[1-3]                      //~1Ah0R~
                    posx=boardsz-ii-1;                         //~1Ah0R~                                                 m//~1Ah0R~
                    posy=jj;                                       //~1Ah0R~
    				NotesFmtCsa.removeHandicapPiece(sb,posx,posy);//~1Ah0I~
                	if (Dump.Y) Dump.println("GtpGameQuestion:setHandicapPiece after drop="+new String(sb));//~1Ah0I~
                }                                                  //~1Ah0I~
            }                                                      //~1Ah0I~
        }                                                          //~1Ah0I~
        sb.append(PASS_FIRST_WHITE);                               //~1Ah0R~
        initialPieces=new String(sb);                               //~1Ah0I~
        writeInitialPieceFile();                                    //~1Ah0I~
		subcmdList.add(SUBCMD_HANDICAP);                           //~1Ah0R~
    }//setInitialPiece                                             //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    private	void writeInitialPieceFile()                           //~1Ah0I~
    {                                                              //~1Ah0I~
		byte[] bytedata;                                           //~1Ah0I~
    //************************                                     //~1Ah0I~
		bytedata=initialPieces.getBytes();                         //~1Ah0I~
    	Prop.writeOutputData(SUBCMD_HANDICAP_FILE,bytedata);       //~1Ah0I~
    }//writeInitialPieceFile                                       //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    //*output handicap.csa,output subcmd                           //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    public static String outputPieceLayout(Notes Pnote)            //~1Ah0I~
    {                                                              //~1Ah0I~
		byte[] bytedata;                                           //~1Ah0I~
    //************************                                     //~1Ah0I~
    	StringBuffer sb=NotesFmtCsa.getPieceLayout(Pnote);         //~1Ah0I~
        if (sb==null)                                              //~1Ah0I~
        	return null;                                                //~1Ah0I~
        if (Pnote.color==Board.COLOR_BLACK)	//last color           //~1Ah0I~
	        sb.append(PASS_FIRST_BLACK);                           //~1Ah0R~
        else                                                       //~1Ah0I~
	        sb.append(PASS_FIRST_WHITE);                           //~1Ah0R~
		String s=new String(sb);                                   //~1Ah0R~
        if (Dump.Y) Dump.println("GtpGameQuestion:outputPieceLayout out="+s);//~1Ah0I~
		bytedata=s.getBytes();                                     //~1Ah0I~
    	Prop.writeOutputData(SUBCMD_HANDICAP_FILE,bytedata);       //~1Ah0I~
		return SUBCMD_HANDICAP;                                    //~1Ah0I~
    }//outputPieceLayout                                           //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    public static String getPGM()                                  //~1Ah0I~
    {                                                              //~1Ah0I~
    	String pgm=Prop.getDataFileFullpath(getPgmPIE(DEFAULT_GTPSERVER));//~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:getPGM pgm="+pgm);//~1Ah0R~
        return pgm;                                                //~1Ah0I~
    }//getPGM                                                      //~1Ah0I~
    //**********************************************************************//~1Ah0I~
    public static String getCmdlineParm()                          //~1Ah0I~
    {                                                              //~1Ah0I~
    	String parm="";                                            //~1Ah0I~
    	boolean oldYN=Prop.getPreference(PKEY_PRINTBOARD,DEFAULT_PRINTBOARD);//~1Ah0I~
        if (!oldYN)                                                //~1Ah0I~
        	parm=CMDLINEOPT_NOPRINTBOARD;                          //~1Ah0I~
        if (Dump.Y) Dump.println("GtpGameQuestion:getCmdlineParm parm="+parm);//~1Ah0I~
        return parm;                                               //~1Ah0I~
    }//getCmdlineParm                                              //~1Ah0I~
//************************************************************     //~1A24I~//~1Ah0I~
//*after Dismiss                                                   //~1A24I~//~1Ah0I~
//************************************************************     //~1A24I~//~1Ah0I~
    protected void reloadFileDialog()                              //~1A24R~//~1Ah0I~
	{                                                              //~1A24I~//~1Ah0I~
    	if (Dump.Y) Dump.println("GtpGameQuestion reloadGame");       //~1A24I~//~1Ah0I~
        GtpGoParm gp=new GtpGoParm(); 	//filled later             //~1Ah0R~
        GtpFrame gf=new GtpFrame(PF,gp);                           //~1Ah0I~
    	FileDialog fd=new FileDialog((FileDialogI)gf/*GtpFrame*/,this,AG.resource.getString(R.string.Title_LoadFile),FileDialog.LOAD);//~1A24R~//~1Ah0R~
        fd.setFilterString(FileDialog.GAMES_EXT);                  //~1A24I~//~1Ah0I~
        fd.show();  //callback loadFileNotes                       //~1A24I~//~1Ah0I~
	}                                                              //~1A24I~//~1Ah0I~
}

