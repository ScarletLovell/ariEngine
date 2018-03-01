package engine.ari.engine_main.external;

import engine.ari.engine_main.Console;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class External_Console extends Console {

    public static boolean open = false;

    public static JFrame jFrame = null;
    private static JTextPane jText = null;
    private static JTextPane enterable = null;
    private static StyledDocument doc;
    private static JScrollPane jScrollPane = null;
    public External_Console() {
        if(jText == null) {
            jText = new JTextPane();
            jText.setContentType("text/html");
            jText.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
            doc = (StyledDocument) jText.getDocument();
        }
    }
    public void Initialize() {
        jFrame = new JFrame("Console");
        jScrollPane = new JScrollPane(jText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jFrame.setSize(600, 350);
        jScrollPane.setAutoscrolls(true);
        jFrame.setBackground(new Color(20, 20, 20));
        jText.setVisible(true);
        jText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                    jText.setEditable(false);
                }
            }
        });
        jText.setMargin(new Insets(5, 5, 5, 5));
        jText.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        jText.setBackground(new Color(20, 20, 20));
        enterable = new JTextPane();
        enterable.setSize(jFrame.getSize().width, 55);
        enterable.setForeground(Color.BLACK);
        enterable.setBackground(Color.WHITE);
        //enterable.setLocation(0, jFrame.getSize().height-35);
        jFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent event) {
                jText.setSize(event.getComponent().getSize());
                Dimension d = event.getComponent().getSize();
                d.height -= 7;
                d.width -= 3;
                jScrollPane.setSize(d);
                //enterable.setLocation(0, jFrame.getSize().height-15);
                enterable.setSize(jFrame.getSize().width, 15);
            }
        });
        jFrame.setMinimumSize(new Dimension(600, 350));
        jFrame.setBackground(new Color(20, 20, 20));
        jFrame.add(jScrollPane);
        jFrame.add(enterable);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setResizable(true);
        jFrame.setVisible(true);
        jFrame.setFocusable(true);
        jFrame.setEnabled(true);

        open = true;
    }
    public void Deinitalize() {
        if(!open)
            return;
        jFrame.setEnabled(false);
        jFrame.removeAll();
        jFrame.dispose();
        jScrollPane = null;
        jFrame = null;

        open = false;
    }
    public void addToConsole(String s) {
        if(doc == null)
            return;

        //Color color = convertColorFromAnsi(s);
        //StyleContext sc = StyleContext.getDefaultStyleContext();
        //AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.WHITE);
        //int len = doc.getLength();
        //try {
            jText.setText("<html>" + jText.getText().replace("<html>", "").replace("</html>", "") + convertColorFromAnsi(s) + "</font></html>\n");
            //doc.insertString(len, s + "\n", aset);
            //doc.insertString(len+1, "</font>", aset);
        //} catch (BadLocationException e) {
        //    e.printStackTrace();
        //}
        jText.setCaretPosition(doc.getLength());
    }
}
