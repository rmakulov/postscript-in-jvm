package runtime.graphics.frame;

import runtime.Runtime;
import runtime.events.EventType;
import runtime.events.PSKeyEvent;
import runtime.events.PSMouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Created by Дмитрий on 24.03.14.
 */
public class PSFrame extends JFrame {
    private static PSFrame ourInstance;
    //    public int psHeight = 700;
    public int psHeight = 1085;
    //    public int psWidth = 500;
    public int psWidth = 815;

    public static PSFrame getInstance() {
        if (ourInstance == null) {
            ourInstance = new PSFrame();
        }
        return ourInstance;
    }


    private PSFrame() {
        super();
        setVisible(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Runtime.getInstance().addEvent(new PSKeyEvent(e.getKeyChar(), EventType.KEYBOARD));
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
//                g2.scale(1.3,1.3);
                g2.drawImage(PSImage.getInstanceImage(), null, 0, 0);


            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                synchronized (this) {
                    Runtime runtime = Runtime.getInstance();
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        runtime.addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.CLICK));
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        runtime.addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.RIGHT_CLICK));
                    }


                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                synchronized (this) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Runtime.getInstance().addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.PRESS));
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                synchronized (this) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Runtime.getInstance().addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.RELEASE));
                    }
                }
            }
        });
        setSize(psWidth, psHeight);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.GRAY);

//        panel.setPreferredSize(new Dimension(PSImage.width, PSImage.height));
        add(new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

}