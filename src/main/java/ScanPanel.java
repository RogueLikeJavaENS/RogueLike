import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class ScanPanel extends JFrame implements KeyListener{
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(key);
    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {}

    public ScanPanel() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        setVisible(true);
        setSize(600,600);
    }
    public static void main(String[] args) {
        ScanPanel t = new ScanPanel();
    }
}


