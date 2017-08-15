//*CID://+v1B6R~: update#=  13;                                    //~v1B6I~
//*********************************                                //~v1B6I~
//v1B6 2014/08/01 bot:Apachi(pachi:gtp server) non v7a(hard floating)//~v1B6I~
//*********************************                                //~v1B6I~
// MessageDialogs.java

//package net.sf.gogui.gui;                                        //~v1B6R~
package com.Asgts.gtp;                                             //+v1B6R~

//import java.awt.Component;
//import java.util.TreeSet;                                        //~v1B6R~
//import java.util.Set;                                            //~v1B6R~
//import java.util.prefs.Preferences;                              //~v1B6R~
//import javax.swing.Box;
//import javax.swing.JComponent;
//import javax.swing.JCheckBox;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import static com.Asgts.gtp.I18n.i18n;                           //+v1B6R~
//import static com.Asgts.gtp.GuiUtil.insertLineBreaks;            //+v1B6R~
//import net.sf.gogui.util.Platform;
//import net.sf.gogui.util.PrefUtil;
import com.Asgts.Alert;                                       //+v1B6R~
import com.Asgts.gtp.StringUtil;                                   //+v1B6R~

/** Simple message dialogs. */
public final class MessageDialogs
{
    public MessageDialogs()
    {
    }

    public void showError(Object frame, String mainMessage,        //~v1B6R~
                          String optionalMessage)
    {
        showError(frame, mainMessage, optionalMessage, true);
    }

    public void showError(Object frame, String mainMessage,        //~v1B6R~
                          String optionalMessage, boolean isCritical)
    {
//        int type;                                                //~v1B6R~
//        if (isCritical)                                          //~v1B6R~
//            type = JOptionPane.ERROR_MESSAGE;                    //~v1B6R~
//        else                                                     //~v1B6R~
//            type = JOptionPane.PLAIN_MESSAGE;                    //~v1B6R~
//        Object[] options = { i18n("LB_CLOSE") };                 //~v1B6R~
//        Object defaultOption = options[0];                       //~v1B6R~
//        show(null, frame, "", mainMessage, optionalMessage, type,//~v1B6R~
//             JOptionPane.DEFAULT_OPTION, options, defaultOption, -1);//~v1B6R~
		String critical;                                           //~v1B6I~
        if (isCritical)                                            //~v1B6I~
            critical="Error";                                      //~v1B6I~
        else                                                       //~v1B6I~
            critical="";                                           //~v1B6I~
	    int flag=Alert.BUTTON_CLOSE|Alert.SHOW_DIALOG;   //~v1B6R~
        Alert.simpleAlertDialog(null/*callback*/,critical,    //~v1B6R~
        			mainMessage+"\n"+optionalMessage,flag);        //~v1B6I~
    }

    public void showError(Object frame, String message, Exception e)//~v1B6R~
    {
        showError(frame, message, e, true);
    }

    public void showError(Object frame, String message, Exception e,//~v1B6R~
                          boolean isCritical)
    {
        showError(frame, message, StringUtil.getErrorMessage(e), isCritical);
    }

//    public void showInfo(Object frame, String mainMessage,       //~v1B6R~
//                         String optionalMessage, boolean isCritical)//~v1B6R~
//    {                                                            //~v1B6R~
//        showInfo(null, frame, mainMessage, optionalMessage, isCritical);//~v1B6R~
//    }                                                            //~v1B6R~

//    public void showInfo(String disableKey, Object frame,        //~v1B6R~
//                         String mainMessage, String optionalMessage,//~v1B6R~
//                         boolean isCritical)                     //~v1B6R~
//    {                                                            //~v1B6R~
////        if (checkDisabled(disableKey))                         //~v1B6R~
////            return;                                            //~v1B6R~
////        int type;                                              //~v1B6R~
////        if (isCritical)                                        //~v1B6R~
////            type = JOptionPane.INFORMATION_MESSAGE;            //~v1B6R~
////        else                                                   //~v1B6R~
////            type = JOptionPane.PLAIN_MESSAGE;                  //~v1B6R~
////        Object[] options = { i18n("LB_CLOSE") };               //~v1B6R~
////        Object defaultOption = options[0];                     //~v1B6R~
////        show(disableKey, frame, "", mainMessage, optionalMessage,//~v1B6R~
////             type, JOptionPane.DEFAULT_OPTION, options, defaultOption, -1);//~v1B6R~
//        String critical;                                         //~v1B6R~
//        if (isCritical)                                          //~v1B6R~
//            critical="Info";                                     //~v1B6R~
//        else                                                     //~v1B6R~
//            critical="";                                         //~v1B6R~
//        int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG; //~v1B6R~
//        AjagoAlert.simpleAlertDialog(null/*callback*/,critical,  //~v1B6R~
//                    mainMessage+"\n"+optionalMessage,flag);      //~v1B6R~
//    }                                                            //~v1B6R~

//    public int showYesNoCancelQuestion(Object parent, String mainMessage,//~v1B6R~
//                                       String optionalMessage,   //~v1B6R~
//                                       String destructiveOption, //~v1B6R~
//                                       String nonDestructiveOption)//~v1B6R~
//    {                                                            //~v1B6R~
//        return showYesNoCancelQuestion(null, parent, mainMessage,//~v1B6R~
//                                       optionalMessage, destructiveOption,//~v1B6R~
//                                       nonDestructiveOption);    //~v1B6R~
//    }                                                            //~v1B6R~

//    /** Show a question with two options and cancel.             //~v1B6R~
//        @return 0 for the destructive option; 1 for the non-destructive//~v1B6R~
//        option; 2 for cancel */                                  //~v1B6R~
//    public int showYesNoCancelQuestion(String disableKey, Object parent,//~v1B6R~
//                                       String mainMessage,       //~v1B6R~
//                                       String optionalMessage,   //~v1B6R~
//                                       String destructiveOption, //~v1B6R~
//                                       String nonDestructiveOption)//~v1B6R~
//    {                                                            //~v1B6R~
////        if (checkDisabled(disableKey))                         //~v1B6R~
////            return 0;                                          //~v1B6R~
////        Object[] options = new Object[3];                      //~v1B6R~
////        int destructiveIndex;                                  //~v1B6R~
////        if (Platform.isMac())                                  //~v1B6R~
////        {                                                      //~v1B6R~
////            options[0] = nonDestructiveOption;                 //~v1B6R~
////            options[1] = i18n("LB_CANCEL");                    //~v1B6R~
////            options[2] = destructiveOption;                    //~v1B6R~
////            destructiveIndex = 2;                              //~v1B6R~
////        }                                                      //~v1B6R~
////        else                                                   //~v1B6R~
////        {                                                      //~v1B6R~
////            options[0] = nonDestructiveOption;                 //~v1B6R~
////            options[1] = destructiveOption;                    //~v1B6R~
////            options[2] = i18n("LB_CANCEL");                    //~v1B6R~
////            destructiveIndex = -1;                             //~v1B6R~
////        }                                                      //~v1B6R~
////        Object defaultOption = options[0];                     //~v1B6R~
////        int type = JOptionPane.QUESTION_MESSAGE;               //~v1B6R~
////        Object value = show(disableKey, parent, "", mainMessage,//~v1B6R~
////                            optionalMessage, type,             //~v1B6R~
////                            JOptionPane.YES_NO_CANCEL_OPTION, options,//~v1B6R~
////                            defaultOption, destructiveIndex);  //~v1B6R~
////        int result;                                            //~v1B6R~
////        if (value == destructiveOption)                        //~v1B6R~
////            result = 0;                                        //~v1B6R~
////        else if (value == nonDestructiveOption)                //~v1B6R~
////            result = 1;                                        //~v1B6R~
////        else                                                   //~v1B6R~
////            result = 2;                                        //~v1B6R~
//        int flag=AjagoAlert.BUTTON_YNC|AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_NEGATIVE|AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;//~v1B6R~
//        AjagoAlert.simpleAlertDialog(null/*callback*/,"",        //~v1B6R~
//                    mainMessage+"\n"+optionalMessage,flag);      //~v1B6R~
//        return result;                                           //~v1B6R~
//    }                                                            //~v1B6R~

//    public void showWarning(Object parent, String mainMessage,   //~v1B6R~
//                            String optionalMessage, boolean isCritical)//~v1B6R~
//    {                                                            //~v1B6R~
//        showWarning(null, parent, mainMessage, optionalMessage, isCritical);//~v1B6R~
//    }                                                            //~v1B6R~

//    public void showWarning(String disableKey, Object parent,    //~v1B6R~
//                            String mainMessage, String optionalMessage,//~v1B6R~
//                            boolean isCritical)                  //~v1B6R~
//    {                                                            //~v1B6R~
////        if (checkDisabled(disableKey))                         //~v1B6R~
////            return;                                            //~v1B6R~
////        int type;                                              //~v1B6R~
////        if (isCritical)                                        //~v1B6R~
////            type = JOptionPane.WARNING_MESSAGE;                //~v1B6R~
////        else                                                   //~v1B6R~
////            type = JOptionPane.PLAIN_MESSAGE;                  //~v1B6R~
////        Object[] options = { i18n("LB_CLOSE") };               //~v1B6R~
////        Object defaultOption = options[0];                     //~v1B6R~
////        show(disableKey, parent, "", mainMessage, optionalMessage, type,//~v1B6R~
////             JOptionPane.DEFAULT_OPTION, options, defaultOption, -1);//~v1B6R~
//        String critical;                                         //~v1B6R~
//        if (isCritical)                                          //~v1B6R~
//            critical="Warning";                                  //~v1B6R~
//        else                                                     //~v1B6R~
//            critical="";                                         //~v1B6R~
//        int flag=AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG; //~v1B6R~
//        AjagoAlert.simpleAlertDialog(null/*callback*/,critical,  //~v1B6R~
//                    mainMessage+"\n"+optionalMessage,flag);      //~v1B6R~
//    }                                                            //~v1B6R~

//    public boolean showQuestion(Object parent, String mainMessage,//~v1B6R~
//                                String optionalMessage,          //~v1B6R~
//                                String destructiveOption, boolean isCritical)//~v1B6R~
//    {                                                            //~v1B6R~
//        return showQuestion(null, parent, mainMessage, optionalMessage,//~v1B6R~
//                            destructiveOption, isCritical);      //~v1B6R~
//    }                                                            //~v1B6R~

//    public boolean showQuestion(String disableKey, Object parent,//~v1B6R~
//                                String mainMessage,              //~v1B6R~
//                                String optionalMessage,          //~v1B6R~
//                                String destructiveOption,        //~v1B6R~
//                                boolean isCritical)              //~v1B6R~
//    {                                                            //~v1B6R~
//        return showQuestion(disableKey, parent, mainMessage, optionalMessage,//~v1B6R~
////                          destructiveOption,i18n("LB_CANCEL"), //~v1B6R~
//                            destructiveOption,"CANCEL",          //~v1B6R~
//                            isCritical);                         //~v1B6R~
//    }                                                            //~v1B6R~

//    /** Show warning message to confirm destructive actions.     //~v1B6R~
//        @return true, if destructive was chosen; false if cancel was//~v1B6R~
//        chosen. */                                               //~v1B6R~
//    public boolean showQuestion(String disableKey, Object parent,//~v1B6R~
//                                String mainMessage,              //~v1B6R~
//                                String optionalMessage,          //~v1B6R~
//                                String affirmativeOption,        //~v1B6R~
//                                String cancelOption,             //~v1B6R~
//                                boolean isCritical)              //~v1B6R~
//    {                                                            //~v1B6R~
////        if (checkDisabled(disableKey))                         //~v1B6R~
////            return true;                                       //~v1B6R~
////        Object[] options = new Object[2];                      //~v1B6R~
////        if (Platform.isMac())                                  //~v1B6R~
////        {                                                      //~v1B6R~
////            options[0] = cancelOption;                         //~v1B6R~
////            options[1] = affirmativeOption;                    //~v1B6R~
////        }                                                      //~v1B6R~
////        else                                                   //~v1B6R~
////        {                                                      //~v1B6R~
////            options[0] = affirmativeOption;                    //~v1B6R~
////            options[1] = cancelOption;                         //~v1B6R~
////        }                                                      //~v1B6R~
////        Object defaultOption = affirmativeOption;              //~v1B6R~
////        int type;                                              //~v1B6R~
////        if (isCritical)                                        //~v1B6R~
////            // No reason to show a warning icon for confirmation dialogs//~v1B6R~
////            // of frequent actions                             //~v1B6R~
////            type = JOptionPane.QUESTION_MESSAGE;               //~v1B6R~
////        else                                                   //~v1B6R~
////            type = JOptionPane.PLAIN_MESSAGE;                  //~v1B6R~
////        Object result = show(disableKey, parent, "", mainMessage,//~v1B6R~
////                             optionalMessage, type, JOptionPane.YES_NO_OPTION,//~v1B6R~
////                             options, defaultOption, -1);      //~v1B6R~
////        return (result == affirmativeOption);                  //~v1B6R~
//        String critical;                                         //~v1B6R~
//        if (isCritical)                                          //~v1B6R~
//            critical="Question";                                 //~v1B6R~
//        else                                                     //~v1B6R~
//            critical="";                                         //~v1B6R~
//        int flag=AjagoAlert.BUTTON_YNC|AjagoAlert.BUTTON_POSITIVE|AjagoAlert.BUTTON_CLOSE|AjagoAlert.SHOW_DIALOG;//~v1B6R~
//        AjagoAlert.simpleAlertDialog(null/*callback*/,"",        //~v1B6R~
//                    mainMessage+"\n"+optionalMessage,flag);      //~v1B6R~
//        return false;                                            //~v1B6R~
//    }                                                            //~v1B6R~

//    public boolean showWarningQuestion(Object parent, String mainMessage,//~v1B6R~
//                                       String optionalMessage,   //~v1B6R~
//                                       String destructiveOption, //~v1B6R~
//                                       boolean isCritical)       //~v1B6R~
//    {                                                            //~v1B6R~
//        return showWarningQuestion(null, parent, mainMessage, optionalMessage,//~v1B6R~
//                                   destructiveOption, isCritical);//~v1B6R~
//    }                                                            //~v1B6R~

//    public boolean showWarningQuestion(String disableKey, Object parent,//~v1B6R~
//                                       String mainMessage,       //~v1B6R~
//                                       String optionalMessage,   //~v1B6R~
//                                       String destructiveOption, //~v1B6R~
//                                       boolean isCritical)       //~v1B6R~
//    {                                                            //~v1B6R~
//        return showWarningQuestion(disableKey, parent, mainMessage,//~v1B6R~
//                                   optionalMessage, destructiveOption,//~v1B6R~
////                                 i18n("LB_CANCEL"), isCritical);//~v1B6R~
//                                   "CANCEL", isCritical);        //~v1B6R~
//    }                                                            //~v1B6R~

//    /** Show warning message to confirm destructive actions.     //~v1B6R~
//        @return true, if destructive was chosen; false if cancel was chosen. *///~v1B6R~
//    public boolean showWarningQuestion(String disableKey, Object parent,//~v1B6R~
//                                       String mainMessage,       //~v1B6R~
//                                       String optionalMessage,   //~v1B6R~
//                                       String destructiveOption, //~v1B6R~
//                                       String nonDestructiveOption,//~v1B6R~
//                                       boolean isCritical)       //~v1B6R~
//    {                                                            //~v1B6R~
//        if (checkDisabled(disableKey))                           //~v1B6R~
//            return true;                                         //~v1B6R~
//        Object[] options = new Object[2];                        //~v1B6R~
//        if (Platform.isMac())                                    //~v1B6R~
//        {                                                        //~v1B6R~
//            options[0] = nonDestructiveOption;                   //~v1B6R~
//            options[1] = destructiveOption;                      //~v1B6R~
//        }                                                        //~v1B6R~
//        else                                                     //~v1B6R~
//        {                                                        //~v1B6R~
//            options[0] = destructiveOption;                      //~v1B6R~
//            options[1] = nonDestructiveOption;                   //~v1B6R~
//        }                                                        //~v1B6R~
//        Object defaultOption = nonDestructiveOption;             //~v1B6R~
//        int type;                                                //~v1B6R~
//        if (isCritical)                                          //~v1B6R~
//            type = JOptionPane.WARNING_MESSAGE;                  //~v1B6R~
//        else                                                     //~v1B6R~
//            type = JOptionPane.PLAIN_MESSAGE;                    //~v1B6R~
//        Object result = show(disableKey, parent, "", mainMessage,//~v1B6R~
//                             optionalMessage, type, JOptionPane.YES_NO_OPTION,//~v1B6R~
//                             options, defaultOption, -1);        //~v1B6R~
//        return (result == destructiveOption);                    //~v1B6R~
//    }

//    private final Set<String> m_disabled = new TreeSet<String>();//~v1B6R~

//    private static void addFiller(JComponent component)          //~v1B6R~
//    {                                                            //~v1B6R~
//        Box.Filler filler = GuiUtil.createFiller();              //~v1B6R~
//        filler.setAlignmentX(Component.LEFT_ALIGNMENT);          //~v1B6R~
//        component.add(filler);                                   //~v1B6R~
//    }                                                            //~v1B6R~

//    private boolean checkDisabled(String disableKey)             //~v1B6R~
//    {                                                            //~v1B6R~
//        if (disableKey == null)                                  //~v1B6R~
//            return false;                                        //~v1B6R~
//        Preferences prefs =                                      //~v1B6R~
//            PrefUtil.createNode("net/sf/gogui/gui/messagedialogs/disabled");//~v1B6R~
//        boolean permanentlyDisabled = prefs.getBoolean(disableKey, false);//~v1B6R~
//        if (permanentlyDisabled)                                 //~v1B6R~
//            return true;                                         //~v1B6R~
//        // Make sure this entry exists (right now these settings can only//~v1B6R~
//        // be directly edited in the backing store)              //~v1B6R~
//        prefs.putBoolean(disableKey, permanentlyDisabled);       //~v1B6R~
//        return m_disabled.contains(disableKey);                  //~v1B6R~
//    }                                                            //~v1B6R~

//    private Object show(String disableKey, Component parent, String title,//~v1B6R~
//                        String mainMessage, String optionalMessage,//~v1B6R~
//                        int messageType, int optionType, Object[] options,//~v1B6R~
//                        Object defaultOption, int destructiveIndex)//~v1B6R~
//    {                                                            //~v1B6R~
//        if (optionalMessage == null)                             //~v1B6R~
//            optionalMessage = "";                                //~v1B6R~
//        boolean isMac = Platform.isMac();                        //~v1B6R~
//        Box box = Box.createVerticalBox();                       //~v1B6R~

//        String css = GuiUtil.getMessageCss();                    //~v1B6R~

//        JLabel label =                                           //~v1B6R~
//            new JLabel("<html>" + css + "<b>" + insertLineBreaks(mainMessage)//~v1B6R~
//                       + "</b><p>"                               //~v1B6R~
//                       + insertLineBreaks(optionalMessage) + "</p>");//~v1B6R~
//        label.setAlignmentX(Component.LEFT_ALIGNMENT);           //~v1B6R~
//        box.add(label);                                          //~v1B6R~

//        addFiller(box);                                          //~v1B6R~
//        addFiller(box);                                          //~v1B6R~
//        JCheckBox disableCheckBox = null;                        //~v1B6R~
//        if (disableKey != null)                                  //~v1B6R~
//        {                                                        //~v1B6R~
//            if (messageType == JOptionPane.QUESTION_MESSAGE)     //~v1B6R~
//                disableCheckBox = new JCheckBox(i18n("LB_DO_NOT_ASK_AGAIN"));//~v1B6R~
//            else if (messageType == JOptionPane.WARNING_MESSAGE) //~v1B6R~
//                disableCheckBox =                                //~v1B6R~
//                    new JCheckBox(i18n("LB_DO_NOT_WARN_AGAIN")); //~v1B6R~
//            else                                                 //~v1B6R~
//                disableCheckBox =                                //~v1B6R~
//                    new JCheckBox(i18n("LB_DO_NOT_SHOW_AGAIN")); //~v1B6R~
//            disableCheckBox.setToolTipText(i18n("TT_DO_NOT_SHOW_AGAIN"));//~v1B6R~
//            disableCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);//~v1B6R~
//            box.add(disableCheckBox);                            //~v1B6R~
//        }                                                        //~v1B6R~
//        if (isMac)                                               //~v1B6R~
//            // Don't show icons on Mac, problem with icon generation in//~v1B6R~
//            // Quaqua 3.7.2                                      //~v1B6R~
//            messageType = JOptionPane.PLAIN_MESSAGE;             //~v1B6R~
//        JOptionPane optionPane =                                 //~v1B6R~
//            new JOptionPane(box, messageType, optionType, null, options,//~v1B6R~
//                            defaultOption);                      //~v1B6R~
//        if (destructiveIndex >= 0)                               //~v1B6R~
//        {                                                        //~v1B6R~
//            String key = "Quaqua.OptionPane.destructiveOption";  //~v1B6R~
//            optionPane.putClientProperty(key,                    //~v1B6R~
//                                         Integer.valueOf(destructiveIndex));//~v1B6R~
//        }                                                        //~v1B6R~
//        if (isMac && parent.isVisible())                         //~v1B6R~
//            // Dialogs don't have titles on the Mac              //~v1B6R~
//            title = null;                                        //~v1B6R~
//        JDialog dialog = optionPane.createDialog(parent, title); //~v1B6R~
//        dialog.setVisible(true);                                 //~v1B6R~
//        dialog.dispose();                                        //~v1B6R~
//        if (disableKey != null && disableCheckBox.isSelected())  //~v1B6R~
//            m_disabled.add(disableKey);                          //~v1B6R~
//        return optionPane.getValue();                            //~v1B6R~
//    }                                                            //~v1B6R~
}
