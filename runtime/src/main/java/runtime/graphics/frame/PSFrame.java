package runtime.graphics.frame;

import javax.swing.*;

/**
 * Created by user on 17.03.14.
 */
public class PSFrame extends JFrame {

    public static PSFrame instance = new PSFrame();

    private PSFrame() {
        super();
        setSize(445, 631);
        setLocation(0, 0);
    }


}
