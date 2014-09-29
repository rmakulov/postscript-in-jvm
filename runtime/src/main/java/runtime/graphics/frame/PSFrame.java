package runtime.graphics.frame;

import javax.swing.*;
import java.awt.*;

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
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        psHeight = screenSize.height;
//        psWidth = psHeight *2/3;
        setSize(psWidth, psHeight);
        setLocation(0, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.GRAY);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
//                g2.scale(1.3,1.3);
                g2.drawImage(PSImage.getInstanceImage(), null, 0, 0);
            }
        };
//        panel.setPreferredSize(new Dimension(PSImage.width, PSImage.height));
        panel.setPreferredSize(new Dimension(PSImage.width, PSImage.height));
        add(new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
    }

    public static void reset() {
//        ourInstance.dispose();
//        ourInstance.setVisible(false);
//        ourInstance.repaint();
        ourInstance = new PSFrame();
    }
}
