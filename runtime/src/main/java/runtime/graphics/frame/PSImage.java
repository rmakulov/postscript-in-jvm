package runtime.graphics.frame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Дмитрий on 24.03.14.
 */
public class PSImage {
    //    public static final int width = 445 * 4 / 3, height = 631 * 4 / 3;
    public static final int width = 651, height = 843;
    private static PSImage ourInstance;
    private BufferedImage image;

    private PSImage() {
//        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Dimension dimension = PSFrame.getInstance().getContentPane().getSize();

        Graphics2D g2 = (Graphics2D) image.getGraphics();
//        Color c = new Color(1f, 0f, 0f, .5f);
//        g2.setColor(Color.WHITE);
        g2.setComposite(AlphaComposite.Clear);
//        System.out.println(new Color(255,255,255,255));
        g2.fillRect(0, 0, width, height);

    }

    public static PSImage getInstance() {
        if (ourInstance == null) {
            ourInstance = new PSImage();
        }

        return ourInstance;
    }

    public static BufferedImage getInstanceImage() {
        return getInstance().getImage();
    }

    public BufferedImage getImage() {
        return image;
    }

    public static Graphics getDefaultGraphics() {
        return getInstanceImage().getGraphics();
    }

    public static Graphics getGraphics() {
        Graphics2D graphics = (Graphics2D) getInstanceImage().getGraphics();
        graphics.setTransform(new AffineTransform(getJavaTransformMatrix()));
        graphics.setComposite(AlphaComposite.Src);
        return graphics;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static double[] getJavaTransformMatrix() {
        double scale = 1;
        return new double[]{scale * 1., 0., 0., scale * (-1.), 0, height};
    }

    public static void reset() {
        ourInstance = new PSImage();
    }
}
