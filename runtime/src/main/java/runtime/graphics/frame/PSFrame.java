package runtime.graphics.frame;

import runtime.Runtime;
import runtime.events.EventType;
import runtime.events.PSKeyEvent;
import runtime.events.PSMouseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Created by Дмитрий on 24.03.14.
 */
public class PSFrame extends JFrame {
    public static void setEventListener(boolean eventListener) {
        PSFrame.eventListener = eventListener;
    }

    private static boolean eventListener = false;
    private static PSFrame ourInstance;
    public int psHeight = 743; //843
    public int psWidth = 651;
    private long lastMoveMouseTime=0;
    private long lastDragMouseTime=0;
    private int mouseMoveRefreshTime=100;
    private int mouseDragRefreshTime=10;

    static boolean mousePressed = false;
    static long mouseCounter = 0;

    public static PSFrame getInstance() {
        if (ourInstance == null) {
            ourInstance = new PSFrame();
        }
        return ourInstance;
    }


    private PSFrame() {
        super();
        getContentPane().setPreferredSize(new Dimension(651, 843));
        pack();
        //setResizable(false);
        setVisible(true);


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
//                g2.scale(1.3,1.3);
                g2.drawImage(PSImage.getInstanceImage(), null, 0, 0);
            }
        };
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                synchronized (this) {
                    if(!eventListener){return;}
                    String s = e.getKeyChar() + "";
//                if (!e.isActionKey()) {
                    if (s.matches("[а-яА-Яa-zA-Z0-9 ,.:;<>?!\"\'\\\\!@#$%^&*~/{}()`\\[\\]]")) {
                        Runtime.getInstance().addEvent(new PSKeyEvent(e.getKeyChar(), e.getKeyCode(), EventType.KEYBOARD_CHAR));
                    } else {
                        Runtime.getInstance().addEvent(new PSKeyEvent(e.getKeyChar(), e.getKeyCode(), EventType.KEYBOARD_CONTROL));
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        panel.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                synchronized (this) {
                    if(!eventListener){return;}
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
                    if(!eventListener){return;}
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        mousePressed = true;
                        Runtime.getInstance().addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.PRESS));
//                        PSFrame.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                synchronized (this) {
                    if(!eventListener){return;}
                    mousePressed = false;
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        Runtime.getInstance().addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.RELEASE));
                    }
                }
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(!eventListener){return;}
            }


            public void mouseDragged(MouseEvent e) {
                synchronized (this) {
                    if(!eventListener){return;}
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        long time=System.currentTimeMillis();
                        if((time-PSFrame.this.lastDragMouseTime) > mouseDragRefreshTime) {
                            Runtime.getInstance().addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.DRAG));
                            PSFrame.this.lastDragMouseTime=time;
                        }
                     }
                }
            }


            public void mouseMoved(MouseEvent e) {
                synchronized (this) {
                    if(!eventListener){return;} //|| !mousePressed){return;}

                    long time=System.currentTimeMillis();
                    if((time-PSFrame.this.lastMoveMouseTime) > mouseMoveRefreshTime) {
                        Runtime.getInstance().addEvent(new PSMouseEvent(e.getX(), e.getY(), EventType.MOVE));
                        PSFrame.this.lastMoveMouseTime=time;
                    }
                }
            }
        });

        setSize(psWidth, psHeight);
        setLocation(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

//        panel.setPreferredSize(new Dimension(PSImage.width, PSImage.height));
        add(new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }
}