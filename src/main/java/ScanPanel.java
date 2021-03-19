import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * This class create a JFrame that extend KeyListener. The player will focus the frame to
 * play. The Frame listen the keyboard and return the keys released.
 *
 * @see JFrame
 * @see KeyListener
 *
 * @author Antoine
 */

public class ScanPanel extends JFrame implements KeyListener{
    int pressed;

    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
        pressed = e.getKeyCode();
    }
    public void keyTyped(KeyEvent e) {
    }

    public ScanPanel() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        setVisible(true);
        setSize(300,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public int getKeyPressed() {
        int a = pressed;
        reset();
        return a;
    }

    public void reset() {
        pressed = 0;
    }

    public static void main(String[] args) {
        ScanPanel t = new ScanPanel();
    }
}


