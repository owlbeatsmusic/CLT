package com.owlbeatsmusic;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {

    static Object[][] chars = new Object[100][2]; // {char, style}

    static JFrame root = new JFrame();
    static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static final int HEIGHT = 30;
    static final int TASKBAR_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    static final int X = 0;
    static final int Y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-HEIGHT-TASKBAR_HEIGHT;
    static JTextPane textPane = new JTextPane();
    static StyledDocument doc = textPane.getStyledDocument();

    static Style white = textPane.addStyle("", null);
    static Style fadedWhite = textPane.addStyle("", null);
    static Style green = textPane.addStyle("", null);
    static Style fadedGreen = textPane.addStyle("", null);
    static Style red = textPane.addStyle("", null);
    static Style fadedRed = textPane.addStyle("", null);
    static Style blue = textPane.addStyle("", null);
    static Style fadedBlue = textPane.addStyle("", null);

    static int index = 0;

    private static void renderScreen() {
        textPane.setText("");
        for (int x = 0; x < chars.length - 1; x++) {
            try {
                doc.insertString(doc.getLength(), String.valueOf(chars[x][0]), (AttributeSet) chars[x][1]);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearPixels() {

        // Clear text
        textPane.setText("");

        // Add all characters
        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < chars[0].length; x++) {
                chars[y] = new Object[]{" ", white};
            }
        }

    }


    public static void main(String[] args) throws BadLocationException, AWTException {
        root.setSize(WIDTH, HEIGHT);
        root.setLocation(X, Y);
        root.setUndecorated(true);
        root.setAlwaysOnTop(true);
        root.setVisible(true);

        StyleConstants.setForeground(white, new Color(255, 255, 255));
        StyleConstants.setForeground(fadedWhite, new Color(70, 70, 70));
        StyleConstants.setForeground(green, new Color(30, 255, 30));
        StyleConstants.setForeground(fadedGreen, new Color(0, 70, 0));
        StyleConstants.setForeground(red, new Color(255, 30, 30));
        StyleConstants.setForeground(fadedRed, new Color(70, 0, 0));
        StyleConstants.setForeground(blue, new Color(100, 100, 255));
        StyleConstants.setForeground(fadedBlue, new Color(0, 0, 70));
        textPane.setStyledDocument(doc);

        clearPixels();
        renderScreen();

        textPane.setFont(new Font("Consolas", Font.BOLD, 21));
        JPanel panel = new JPanel();
        root.add(panel);
        panel.setLayout(new BorderLayout(1,1));
        panel.add(textPane, BorderLayout.CENTER);
        textPane.setHighlighter(null);
        textPane.setEditable(false);
        Robot robot = new Robot();
        Color taskbarColor = robot.getPixelColor(0, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-1);
        textPane.setBackground(taskbarColor);
        textPane.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        root.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                chars[index][0] = e.getKeyChar();
                renderScreen();
                index++;
            }
        });

        textPane.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {}
            @Override public void mouseMoved(MouseEvent e) {
                root.requestFocus();
            }
        });
    }
}
