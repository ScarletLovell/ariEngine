package engine.ari.engine_main;

import javax.swing.*;

public class Dialog {
    public int YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION;
    public int YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
    public int YES_OPTION = JOptionPane.YES_OPTION;
    public int CLOSED_OPTION = JOptionPane.CLOSED_OPTION;
    public int NO_OPTION = JOptionPane.NO_OPTION;
    public int OK_OPTION = JOptionPane.OK_OPTION;
    public int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    public int OK_CANCEL_OPTION = JOptionPane.OK_CANCEL_OPTION;
    //public int PLAIN_MESSAGE = JOptionPane.PLAIN_MESSAGE;
    //public int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    //public int QUESTION_MESSAGE = JOptionPane.WARNING_MESSAGE;
    //public int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    public int showConfirmDialog(String title, String message, int optionType) {
        return JOptionPane.showConfirmDialog(null, message, title, optionType, JOptionPane.PLAIN_MESSAGE, null);
    }
    public Object showInputDialog(String title, String message, Integer optionType) {
        return JOptionPane.showInputDialog(null, message, title, optionType, null, null, null);
    }
}
