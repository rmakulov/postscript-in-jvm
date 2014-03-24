package runtime.graphics.frame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Дмитрий on 24.03.14.
 */
public class PSImage {
    public static final int width = 445, height = 631;
    private static PSImage ourInstance;
    private BufferedImage image;

    private PSImage() {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.setTransform(new AffineTransform(getJavaTransformMatrix()));
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

    public static Graphics getGraphics() {
        Graphics2D graphics = (Graphics2D) getInstanceImage().getGraphics();
        graphics.setTransform(new AffineTransform(getJavaTransformMatrix()));
        return graphics;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static double[] getJavaTransformMatrix() {
        double scale = 1;
        return new double[]{scale * 1., 0., 0., scale * (-1.), 0, height};
    }
}
