package runtime.graphics.frame;

import runtime.graphics.GraphicsState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ColorModel;

/**
 * Created by user on 17.03.14.
 */
public class PSFrame extends JFrame {

    public static PSFrame instance = new PSFrame() ;

    private PSFrame(){
        super() ;
        setSize(445, 631);
    }

    public void paint(Graphics g){
        GraphicsState gState = GraphicsState.getInstance();
        gState.currentPath.draw(g) ;
    }

    public void showpage(){
        this.setVisible(true) ;
    }

    public static void main(String[] args){
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        PSFrame frame = new PSFrame();

        frame.setBackground(Color.WHITE);
        ColorModel cm = frame.getColorModel() ;

        frame.setVisible(true);
    }


}
