package runtime.graphics.paths;

import runtime.graphics.figures.PSPoint;

/**
 * Created by Дмитрий on 19.03.14.
 */
public class BoundingBox {
    public final double leftX, rightX, upperY, downY;

    public BoundingBox(PSPoint leftUpperPoint, PSPoint rightDownPoint) {
        leftX = leftUpperPoint.getX();
        upperY = leftUpperPoint.getY();
        rightX = rightDownPoint.getX();
        downY = rightDownPoint.getY();
    }

    public BoundingBox(double leftX, double upperY, double rightX, double downY) {
        this.leftX = leftX;
        this.rightX = rightX;
        this.upperY = upperY;
        this.downY = downY;
    }

    public PSPoint getLeftUpperPoint() {
        return new PSPoint(leftX, upperY);
    }

    public PSPoint getRightDownPoint() {
        return new PSPoint(rightX, downY);
    }
}
