//*CID://+1AfqR~:                                   update#=   40; //~1AfqR~
//***********************************************************************//~v1B6I~
//1Afq 2016/09/30 toast when undo is not supported(Ray)            //~1AfqI~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//***********************************************************************//~v1B6I~
// GtpSynchronizer.java

package com.Asgts.gtp;                                             //~1AfqR~

//import java.util.ArrayList;                                      //~v1B6R~
//import java.util.List;                                           //~v1B6R~
//import net.sf.gogui.game.TimeSettings;                           //~v1B6R~
//import net.sf.gogui.go.Board;                                    //~v1B6R~
//import net.sf.gogui.go.ConstBoard;                               //~v1B6R~
//import net.sf.gogui.go.ConstPointList;                           //~v1B6R~
//import net.sf.gogui.go.GoColor;                                  //~v1B6R~
import jagoclient.Dump;

import com.Asgts.AView;                                        //~1AfqR~
import com.Asgts.R;                                                //~1AfqR~
import com.Asgts.gtp.TimeSettings;                                 //~1AfqR~
import com.Asgts.gtp.ConstPointList;                               //~1AfqR~
//import com.Asgts.gtp.GoColor;                                    //~1AfqR~
//import static net.sf.gogui.go.GoColor.BLACK;                     //~v1B6R~
//import static net.sf.gogui.go.GoColor.WHITE;                     //~v1B6R~
//import static net.sf.gogui.go.GoColor.BLACK_WHITE;               //~v1B6R~
//import static net.sf.gogui.go.GoColor.WHITE_BLACK;               //~v1B6R~
//import static com.Asgts.gtp.GoColor.BLACK;                       //~1AfqR~
//import static com.Asgts.gtp.GoColor.WHITE;                       //~1AfqR~
//import static com.Asgts.gtp.GoColor.BLACK_WHITE;                 //~1AfqR~
//import static com.Asgts.gtp.GoColor.WHITE_BLACK;                 //~1AfqR~
//import net.sf.gogui.go.GoPoint;                                  //~v1B6R~
//import net.sf.gogui.go.Komi;                                     //~v1B6R~
//import net.sf.gogui.go.Move;                                     //~v1B6R~
//import net.sf.gogui.util.ObjectUtil;                             //~v1B6R~
import com.Asgts.gtp.GoPoint;                                      //~1AfqR~
import com.Asgts.gtp.Komi;                                         //~1AfqR~
//import com.Asgts.gtp.Move;                                       //~1AfqR~
import com.Asgts.gtp.ObjectUtil;                                   //~1AfqR~
import com.Asgts.gtp.GTPConnector;                                 //~1AfqR~

/** Synchronizes a GTP engine with a Go board.
    Handles different capabilities of different engines.
    If GtpSynchronizer is used, no game state changing GTP commands (like
    clear_board, play, undo, komi, time_settings) should be sent to this
    engine outside this class. */
public class GtpSynchronizer
{
    /** Callback that is called after each change in the engine's move
        number.
        Necessary, because sending multiple undo or play commands can be
        a slow operation. */
    public interface Listener
    {
        void moveNumberChanged(int moveNumber);
    }

    public GtpSynchronizer(GtpClientBase gtp)
    {
        this(gtp, null, false);
    }

    public GtpSynchronizer(GtpClientBase gtp, Listener listener,
                           boolean fillPasses)
    {
//      m_fillPasses = fillPasses;                                 //~v1B6R~
        m_gtp = gtp;
//      m_listener = listener;                                     //~v1B6R~
        m_isOutOfSync = true;
        m_komi = null;
        m_timeSettings = null;
    }
    public GtpSynchronizer(GTPConnector Pconnector)                //~v1B6I~
    {                                                              //~v1B6I~
//      m_fillPasses = false;                                      //~v1B6R~
        m_gtp = (GtpClientBase)Pconnector.gtpclient;                                        //~v1B6I~
//      m_listener = null;                                         //~v1B6R~
        m_isOutOfSync = true;                                      //~v1B6I~
        m_komi = null;                                             //~v1B6I~
        m_timeSettings = null;                                     //~v1B6I~
    }                                                              //~v1B6I~

    /** Did the last GtpSynchronizer.synchronize() fail? */
    public boolean isOutOfSync()
    {
        return m_isOutOfSync;
    }

//  public void init(ConstBoard board, Komi komi, TimeSettings timeSettings)//~v1B6R~
    public void init(int Psize,Komi komi,TimeSettings timeSettings)//~v1B6I~
        throws GtpError
    {
        if (Dump.Y) Dump.println("GtpSynchronizer:init1");         //+1AfqI~
        initSupportedCommands();
        m_isOutOfSync = true;
//      int size = board.getSize();                                //~v1B6R~
        int size = Psize;                                          //~v1B6I~
//      m_engineState = null;                                      //~v1B6R~
//      m_gtp.sendBoardsize(size);                                 //+1AfqR~
//      m_engineState = new Board(size);                           //~v1B6R~
//      m_gtp.sendClearBoard(size);                                //+1AfqR~
//      sendGameInfo(komi, timeSettings);                          //+1AfqR~
//      ConstBoard targetState = computeTargetState(board);        //~v1B6R~
//      setup(targetState);                                        //~v1B6R~
//      ArrayList<Move> moves = new ArrayList<Move>();             //~v1B6R~
//        for (int i = 0; i < targetState.getNumberMoves(); ++i)   //~v1B6R~
//            moves.add(targetState.getMove(i));                   //~v1B6R~
//      play(moves);
        m_isOutOfSync = false;
    }
    public void init(GTPConnector Pconnector, Komi komi, TimeSettings timeSettings)//~v1B6I~
        throws GtpError                                            //~v1B6I~
    {                                                              //~v1B6I~
        if (Dump.Y) Dump.println("GtpSynchronizer:init2");         //+1AfqI~
        initSupportedCommands();                                   //~v1B6I~
        m_isOutOfSync = true;                                      //~v1B6I~
 //     int size = board.getSize();                                //~v1B6I~
        int size = Pconnector.gtpconnection.getBoardSize();             //~v1B6I~
//      m_engineState = null;                                      //~v1B6R~
//      m_gtp.sendBoardsize(size);                                 //~v1B6I~//+1AfqR~
//      m_engineState = new Board(size);                           //~v1B6I~
//      m_gtp.sendClearBoard(size);                                //~v1B6I~//+1AfqR~
//      sendGameInfo(komi, timeSettings);                          //~v1B6I~//+1AfqR~
//      ConstBoard targetState = computeTargetState(board);        //~v1B6I~
//      setup(targetState);                                        //~v1B6I~
//      ArrayList<Move> moves = new ArrayList<Move>();             //~v1B6R~
//      for (int i = 0; i < targetState.getNumberMoves(); ++i)     //~v1B6I~
//          moves.add(targetState.getMove(i));                     //~v1B6I~
//      play(moves);                                               //~v1B6I~
        m_isOutOfSync = false;                                     //~v1B6I~
    }                                                              //~v1B6I~

//    public void synchronize(ConstBoard board, Komi komi,         //~v1B6R~
//                            TimeSettings timeSettings) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
//        int size = board.getSize();                              //~v1B6R~
//        ConstBoard targetState = computeTargetState(board);      //~v1B6R~
//        if (m_engineState == null || size != m_engineState.getSize()//~v1B6R~
//            || isSetupDifferent(targetState))                    //~v1B6R~
//        {                                                        //~v1B6R~
//            init(board, komi, timeSettings);                     //~v1B6R~
//            return;                                              //~v1B6R~
//        }                                                        //~v1B6R~
//        m_isOutOfSync = true;                                    //~v1B6R~
//        ArrayList<Move> moves = new ArrayList<Move>();           //~v1B6R~
//        int numberUndo = computeToPlay(moves, targetState);      //~v1B6R~
//        if (numberUndo == 0 || m_isSupportedUndo || m_isSupportedGGUndo)//~v1B6R~
//        {                                                        //~v1B6R~
//            try                                                  //~v1B6R~
//            {                                                    //~v1B6R~
//                undo(numberUndo);                                //~v1B6R~
//            }                                                    //~v1B6R~
//            catch (GtpError e)                                   //~v1B6R~
//            {                                                    //~v1B6R~
//                // According to the GTP standard, undo may fail even if it is//~v1B6R~
//                // supported and there were moves played. In this case, we//~v1B6R~
//                // fall back to a full initialization.           //~v1B6R~
//                init(board, komi, timeSettings);                 //~v1B6R~
//                return;                                          //~v1B6R~
//            }                                                    //~v1B6R~
//            // Send komi/time_settings before play commands, some engines//~v1B6R~
//            // cannot handle them otherwise                      //~v1B6R~
//            sendGameInfo(komi, timeSettings);                    //~v1B6R~
//            play(moves);                                         //~v1B6R~
//            m_isOutOfSync = false;                               //~v1B6R~
//        }                                                        //~v1B6R~
//        else                                                     //~v1B6R~
//            init(board, komi, timeSettings);                     //~v1B6R~
//    }                                                            //~v1B6R~

//    /** Send human move to engine.                               //~v1B6R~
//        The move is not played on the board yet. This function is useful,//~v1B6R~
//        if it should be first tested, if the engine accepts a move, before//~v1B6R~
//        playing it on the board, e.g. after a new human move was entered. *///~v1B6R~
//    public void updateHumanMove(ConstBoard board, Move move) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
//        ConstBoard targetState = computeTargetState(board);      //~v1B6R~
//        assert findNumberCommonMoves(targetState)                //~v1B6R~
//            == targetState.getNumberMoves();                     //~v1B6R~
//        if (m_fillPasses && m_engineState.getNumberMoves() > 0)  //~v1B6R~
//        {                                                        //~v1B6R~
//            Move lastMove = m_engineState.getLastMove();         //~v1B6R~
//            GoColor c = move.getColor();                         //~v1B6R~
//            if (lastMove.getColor() == c)                        //~v1B6R~
//                play(Move.getPass(c.otherColor()));              //~v1B6R~
//        }                                                        //~v1B6R~
//        play(move);                                              //~v1B6R~
//    }                                                            //~v1B6R~

    /** Update internal state after genmove.
        Needs to be called after each genmove (or kgs-genmove_cleanup)
        command. The computer move is expected to be already played on the
        board, but does not need to be transmitted to the program anymore,
        because genmove already executes the move at the program's side. */
//    public void updateAfterGenmove(ConstBoard board)             //~v1B6R~
//    {                                                            //~v1B6R~
//        Move move = board.getLastMove();                         //~v1B6R~
//        assert move != null;                                     //~v1B6R~
//        m_engineState.play(move);                                //~v1B6R~
//        try                                                      //~v1B6R~
//        {                                                        //~v1B6R~
//            ConstBoard targetState = computeTargetState(board);  //~v1B6R~
//            assert findNumberCommonMoves(targetState)            //~v1B6R~
//                == targetState.getNumberMoves();                 //~v1B6R~
//        }                                                        //~v1B6R~
//        catch (GtpError e)                                       //~v1B6R~
//        {                                                        //~v1B6R~
//            // computeTargetState should not throw (no new setup //~v1B6R~
//            assert false;                                        //~v1B6R~
//        }                                                        //~v1B6R~
//    }                                                            //~v1B6R~

//    private boolean m_fillPasses;                                //~v1B6R~

    private boolean m_isOutOfSync;

//    private boolean m_isSupportedHandicap;                       //~v1B6R~

//    private boolean m_isSupportedPlaySequence;                   //~v1B6R~

//    private boolean m_isSupportedSetupPlayer;                    //~v1B6R~

//    private boolean m_isSupportedGGUndo;                         //~v1B6R~

    private boolean m_isSupportedUndo;

//    private boolean m_isSupportedSetup;                          //~v1B6R~

    private Komi m_komi;

    private TimeSettings m_timeSettings;

//    private final Listener m_listener;                           //~v1B6R~

    private GtpClientBase m_gtp;

//    private Board m_engineState;                                 //~v1B6R~

//    /** Computes all actions to execute.                         //~v1B6R~
//        Replaces setup stones by moves, if setup is not supported.//~v1B6R~
//        Fills in passes between moves of same color if m_fillPasses. *///~v1B6R~
//    private Board computeTargetState(ConstBoard board) throws GtpError//~v1B6R~
//    {                                                            //~v1B6R~
//        int size = board.getSize();                              //~v1B6R~
//        Board targetState = new Board(size);                     //~v1B6R~
//        ConstPointList setupBlack = board.getSetup(BLACK);       //~v1B6R~
//        ConstPointList setupWhite = board.getSetup(WHITE);       //~v1B6R~
//        GoColor setupPlayer = board.getSetupPlayer();            //~v1B6R~
//        if (setupBlack.size() > 0 || setupWhite.size() > 0)      //~v1B6R~
//        {                                                        //~v1B6R~
//            targetState.clear();                                 //~v1B6R~
//            boolean isHandicap = board.isSetupHandicap();        //~v1B6R~
//            if (isHandicap && m_isSupportedHandicap)             //~v1B6R~
//                targetState.setupHandicap(setupBlack);           //~v1B6R~
//            else if (m_isSupportedSetup)                         //~v1B6R~
//                targetState.setup(setupBlack, setupWhite, setupPlayer);//~v1B6R~
//            else                                                 //~v1B6R~
//            {                                                    //~v1B6R~
//                // Translate setup into moves                    //~v1B6R~
//                // Send moves of color to move first, such that the right color//~v1B6R~
//                // is to move after the setup (works only if there are setup//~v1B6R~
//                // stones by both colors)                        //~v1B6R~
//                List<GoColor> colors;                            //~v1B6R~
//                if (setupPlayer == BLACK)                        //~v1B6R~
//                    colors = BLACK_WHITE;                        //~v1B6R~
//                else                                             //~v1B6R~
//                    colors = WHITE_BLACK;                        //~v1B6R~
//                for (GoColor c : colors)                         //~v1B6R~
//                {                                                //~v1B6R~
//                    for (GoPoint p : board.getSetup(c))          //~v1B6R~
//                    {                                            //~v1B6R~
//                        if (targetState.isCaptureOrSuicide(c, p))//~v1B6R~
//                        {                                        //~v1B6R~
//                            String message =                     //~v1B6R~
//                                "cannot transmit setup as " +    //~v1B6R~
//                                "move if stones are captured";   //~v1B6R~
//                            throw new GtpError(message);         //~v1B6R~
//                        }                                        //~v1B6R~
//                        targetState.play(Move.get(c, p));        //~v1B6R~
//                    }                                            //~v1B6R~
//                }                                                //~v1B6R~
//            }                                                    //~v1B6R~
//        }                                                        //~v1B6R~
//        for (int i = 0; i < board.getNumberMoves(); ++i)         //~v1B6R~
//        {                                                        //~v1B6R~
//            Move move = board.getMove(i);                        //~v1B6R~
//            GoColor toMove = targetState.getToMove();            //~v1B6R~
//            if (m_fillPasses && move.getColor() != toMove)       //~v1B6R~
//                targetState.play(Move.getPass(toMove));          //~v1B6R~
//            targetState.play(move);                              //~v1B6R~
//        }                                                        //~v1B6R~
//        return targetState;                                      //~v1B6R~
//    }                                                            //~v1B6R~

//    /** Compute number of moves to undo and moves to execute.    //~v1B6R~
//        @return Number of moves to undo. */                      //~v1B6R~
//    private int computeToPlay(ArrayList<Move> moves, ConstBoard targetState)//~v1B6R~
//        throws GtpError                                          //~v1B6R~
//    {                                                            //~v1B6R~
//        int numberCommonMoves = findNumberCommonMoves(targetState);//~v1B6R~
//        int numberUndo = m_engineState.getNumberMoves() - numberCommonMoves;//~v1B6R~
//        moves.clear();                                           //~v1B6R~
//        for (int i = numberCommonMoves; i < targetState.getNumberMoves(); ++i)//~v1B6R~
//            moves.add(targetState.getMove(i));                   //~v1B6R~
//        return numberUndo;                                       //~v1B6R~
//    }                                                            //~v1B6R~

//    private int findNumberCommonMoves(ConstBoard targetState)    //~v1B6R~
//    {                                                            //~v1B6R~
//        int i;                                                   //~v1B6R~
//        for (i = 0; i < targetState.getNumberMoves(); ++i)       //~v1B6R~
//        {                                                        //~v1B6R~
//            if (i >= m_engineState.getNumberMoves())             //~v1B6R~
//                break;                                           //~v1B6R~
//            Move move = (Move)targetState.getMove(i);            //~v1B6R~
//            if (! move.equals(m_engineState.getMove(i)))         //~v1B6R~
//                break;                                           //~v1B6R~
//        }                                                        //~v1B6R~
//        return i;                                                //~v1B6R~
//    }                                                            //~v1B6R~

//    private boolean isSetupDifferent(ConstBoard targetState)     //~v1B6R~
//    {                                                            //~v1B6R~
//        if (m_engineState.isSetupHandicap() != targetState.isSetupHandicap())//~v1B6R~
//            return true;                                         //~v1B6R~
//        if (! ObjectUtil.equals(m_engineState.getSetupPlayer(),  //~v1B6R~
//                                targetState.getSetupPlayer()))   //~v1B6R~
//            return true;                                         //~v1B6R~
//        for (GoColor c : BLACK_WHITE)                            //~v1B6R~
//            if (! m_engineState.getSetup(c).equals(targetState.getSetup(c)))//~v1B6R~
//                return true;                                     //~v1B6R~
//        return false;                                            //~v1B6R~
//    }                                                            //~v1B6R~

    private void initSupportedCommands()
    {
//        m_isSupportedPlaySequence =                              //~v1B6R~
//            GtpClientUtil.isPlaySequenceSupported(m_gtp);        //~v1B6R~
        m_isSupportedUndo = isSupported("undo");
        if (Dump.Y) Dump.println("GtpSynchronizer:initSupportedCommand undo="+m_isSupportedUndo);//~v1B6I~
//        m_isSupportedGGUndo = isSupported("gg-undo");            //~v1B6R~
//        m_isSupportedSetup = isSupported("gogui-setup");         //~v1B6R~
//        m_isSupportedSetupPlayer = isSupported("gogui-setup_player");//~v1B6R~
//        m_isSupportedHandicap = isSupported("set_free_handicap");//~v1B6R~
    }

    private boolean isSupported(String command)
    {
        return m_gtp.isSupported(command);
    }

//    private void play(Move move) throws GtpError                 //~v1B6R~
//    {                                                            //~v1B6R~
//        m_gtp.sendPlay(move);                                    //~v1B6R~
//        m_engineState.play(move);                                //~v1B6R~
//    }                                                            //~v1B6R~

//    private void play(ArrayList<Move> moves) throws GtpError     //~v1B6R~
//    {                                                            //~v1B6R~
//        if (moves.isEmpty())                                     //~v1B6R~
//            return;                                              //~v1B6R~
//        if (moves.size() > 1 && m_isSupportedPlaySequence)       //~v1B6R~
//        {                                                        //~v1B6R~
//            String cmd = GtpClientUtil.getPlaySequenceCommand(m_gtp, moves);//~v1B6R~
//            m_gtp.send(cmd);                                     //~v1B6R~
//            for (int i = 0; i < moves.size(); ++i)               //~v1B6R~
//                m_engineState.play(moves.get(i));                //~v1B6R~
//        }                                                        //~v1B6R~
//        else                                                     //~v1B6R~
//        {                                                        //~v1B6R~
//            for (int i = 0; i < moves.size(); ++i)               //~v1B6R~
//            {                                                    //~v1B6R~
//                play(moves.get(i));                              //~v1B6R~
//                updateListener();                                //~v1B6R~
//            }                                                    //~v1B6R~
//        }                                                        //~v1B6R~
//    }                                                            //~v1B6R~

    private void sendGameInfo(Komi komi, TimeSettings timeSettings)
    {
        if (! ObjectUtil.equals(komi, m_komi))
        {
            m_komi = komi;
            if (m_gtp.isSupported("komi"))
            {
                try
                {
                    // Use Komi.toString(Komi), which interprets null argument
                    // (undefined komi) as zero komi. If we did nothing if the
                    // komi value is not defined, the engine would use the
                    // old komi value if a komi was set previously (e.g. from a
                    // previously loaded file)
                    m_gtp.send("komi " + Komi.toString(komi));
                }
                catch (GtpError e)
                {
                }
            }
        }
        if (! ObjectUtil.equals(timeSettings, m_timeSettings))
        {
            m_timeSettings = timeSettings;
            if (m_gtp.isSupported("time_settings"))
            {
                try
                {
                    m_gtp.send(GtpUtil.getTimeSettingsCommand(timeSettings));
                }
                catch (GtpError e)
                {
                }
            }
        }
    }

//    private void setup(ConstBoard targetState) throws GtpError   //~v1B6R~
//    {                                                            //~v1B6R~
//        ConstPointList setupBlack = targetState.getSetup(BLACK); //~v1B6R~
//        ConstPointList setupWhite = targetState.getSetup(WHITE); //~v1B6R~
//        GoColor setupPlayer = targetState.getSetupPlayer();      //~v1B6R~
//        if (setupBlack.size() == 0 && setupWhite.size() == 0)    //~v1B6R~
//            return;                                              //~v1B6R~
//        if (targetState.isSetupHandicap())                       //~v1B6R~
//        {                                                        //~v1B6R~
//            StringBuilder command = new StringBuilder(128);      //~v1B6R~
//            command.append("set_free_handicap");                 //~v1B6R~
//            for (GoPoint p : setupBlack)                         //~v1B6R~
//            {                                                    //~v1B6R~
//                command.append(' ');                             //~v1B6R~
//                command.append(p);                               //~v1B6R~
//            }                                                    //~v1B6R~
//            m_gtp.send(command.toString());                      //~v1B6R~
//            m_engineState.setupHandicap(setupBlack);             //~v1B6R~
//        }                                                        //~v1B6R~
//        else                                                     //~v1B6R~
//        {                                                        //~v1B6R~
//            StringBuilder command = new StringBuilder(128);      //~v1B6R~
//            command.append("gogui-setup");                       //~v1B6R~
//            for (GoColor c : BLACK_WHITE)                        //~v1B6R~
//            {                                                    //~v1B6R~
//                for (GoPoint p : targetState.getSetup(c))        //~v1B6R~
//                {                                                //~v1B6R~
//                    command.append(' ');                         //~v1B6R~
//                    command.append(Move.get(c, p));              //~v1B6R~
//                }                                                //~v1B6R~
//            }                                                    //~v1B6R~
//            m_gtp.send(command.toString());                      //~v1B6R~
//            m_engineState.setup(setupBlack, setupWhite, setupPlayer);//~v1B6R~
//            if (setupPlayer != null && m_isSupportedSetupPlayer) //~v1B6R~
//                m_gtp.send("gogui-setup_player "                 //~v1B6R~
//                           + setupPlayer.getUppercaseLetter());  //~v1B6R~
//        }                                                        //~v1B6R~
//    }                                                            //~v1B6R~
//***************************************************************  //~v1B6I~
//*from GTPConnection:send  handicap                               //~v1B6I~
//***************************************************************  //~v1B6I~
    public void sendHandicap(ConstPointList PhandicapList) throws GtpError//~v1B6I~
    {                                                              //~v1B6I~
//      ConstPointList setupBlack = targetState.getSetup(BLACK);   //~v1B6I~
        ConstPointList setupBlack = PhandicapList;                 //~v1B6I~
//      ConstPointList setupWhite = targetState.getSetup(WHITE);   //~v1B6I~
//      GoColor setupPlayer = targetState.getSetupPlayer();        //~v1B6I~
//      if (setupBlack.size() == 0 && setupWhite.size() == 0)      //~v1B6I~
//          return;                                                //~v1B6I~
//      if (targetState.isSetupHandicap())                         //~v1B6I~
//      {                                                          //~v1B6I~
            StringBuilder command = new StringBuilder(128);        //~v1B6I~
            command.append("set_free_handicap");                   //~v1B6I~
            for (GoPoint p : setupBlack)                           //~v1B6I~
            {                                                      //~v1B6I~
                command.append(' ');                               //~v1B6I~
                command.append(p);                                 //~v1B6I~
            }                                                      //~v1B6I~
            m_gtp.send(command.toString());                        //~v1B6I~
//          m_engineState.setupHandicap(setupBlack);               //~v1B6R~
//      }                                                          //~v1B6I~
//      else                                                       //~v1B6I~
//      {                                                          //~v1B6I~
//          StringBuilder command = new StringBuilder(128);        //~v1B6I~
//          command.append("gogui-setup");                         //~v1B6I~
//          for (GoColor c : BLACK_WHITE)                          //~v1B6I~
//          {                                                      //~v1B6I~
//              for (GoPoint p : targetState.getSetup(c))          //~v1B6I~
//              {                                                  //~v1B6I~
//                  command.append(' ');                           //~v1B6I~
//                  command.append(Move.get(c, p));                //~v1B6I~
//              }                                                  //~v1B6I~
//          }                                                      //~v1B6I~
//          m_gtp.send(command.toString());                        //~v1B6I~
//          m_engineState.setup(setupBlack, setupWhite, setupPlayer);//~v1B6I~
//          if (setupPlayer != null && m_isSupportedSetupPlayer)   //~v1B6I~
//              m_gtp.send("gogui-setup_player "                   //~v1B6I~
//                         + setupPlayer.getUppercaseLetter());    //~v1B6I~
//      }                                                          //~v1B6I~
    }                                                              //~v1B6I~

//    private void undo(int n) throws GtpError                     //~v1B6R~
//    {                                                            //~v1B6R~
//        if (n == 0)                                              //~v1B6R~
//            return;                                              //~v1B6R~
//        if (m_isSupportedGGUndo && (n > 1 || ! m_isSupportedUndo))//~v1B6R~
//        {                                                        //~v1B6R~
//            m_gtp.send("gg-undo " + n);                          //~v1B6R~
//            m_engineState.undo(n);                               //~v1B6R~
//        }                                                        //~v1B6R~
//        else                                                     //~v1B6R~
//        {                                                        //~v1B6R~
//            assert m_isSupportedUndo;                            //~v1B6R~
//            for (int i = 0; i < n; ++i)                          //~v1B6R~
//            {                                                    //~v1B6R~
//                m_gtp.send("undo");                              //~v1B6R~
//                m_engineState.undo();                            //~v1B6R~
//                updateListener();                                //~v1B6R~
//            }                                                    //~v1B6R~
//        }                                                        //~v1B6R~
//    }                                                            //~v1B6R~
    public boolean undo(int n) throws GtpError                     //~v1B6M~
    {                                                              //~v1B6M~
        if (n == 0)                                                //~v1B6M~
            return false;                                          //~v1B6M~
//        if (m_isSupportedGGUndo && (n > 1 || ! m_isSupportedUndo))//~v1B6M~
//        {                                                        //~v1B6M~
//            m_gtp.send("gg-undo " + n);                          //~v1B6M~
//            m_engineState.undo(n);                               //~v1B6M~
//        }                                                        //~v1B6M~
//        else                                                     //~v1B6M~
        {                                                          //~v1B6M~
//          assert m_isSupportedUndo;                              //~v1B6M~
            if (!m_isSupportedUndo)                                //~v1B6M~
            {                                                      //~1AfqI~
            	AView.showToast(R.string.UndoNotSupported);    //~1AfqI~
	            return false;                                      //~v1B6M~
            }                                                      //~1AfqI~
            for (int i = 0; i < n; ++i)                            //~v1B6M~
            {                                                      //~v1B6M~
                m_gtp.send("undo");                                //~v1B6M~
//              m_engineState.undo();                              //~v1B6M~
//              updateListener();                                  //~v1B6M~
            }                                                      //~v1B6M~
        }                                                          //~v1B6M~
        return true;                                               //~v1B6M~
    }                                                              //~v1B6M~

//    private void updateListener()                                //~v1B6R~
//    {                                                            //~v1B6R~
//        if (m_listener != null)                                  //~v1B6R~
//            m_listener.moveNumberChanged(m_engineState.getNumberMoves());//~v1B6R~
//    }                                                            //~v1B6R~
}
