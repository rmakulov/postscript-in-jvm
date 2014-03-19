package runtime.graphics.paths;

import runtime.graphics.figures.PSPoint;

/**
 * Created by Дмитрий on 19.03.14.
 */
public class BoundingBox {
    public final double leftX, rightX, upperY, lowerY;

    public BoundingBox(PSPoint leftLowerPoint, PSPoint rightUpperPoint) {
        leftX = leftLowerPoint.getX();
        upperY = rightUpperPoint.getY();
        rightX = rightUpperPoint.getX();
        lowerY = leftLowerPoint.getY();
    }

    public BoundingBox(double leftX, double lowerY, double rightX, double upperY) {
        this.leftX = leftX;
        this.rightX = rightX;
        this.upperY = upperY;
        this.lowerY = lowerY;
    }

    public PSPoint getLeftLowerPoint() {
        return new PSPoint(leftX, lowerY);
    }

    public PSPoint getRightDownPoint() {
        return new PSPoint(rightX, upperY);
    }

    public BoundingBox expand(BoundingBox box) {
        return new BoundingBox(Math.min(box.leftX, leftX),
                Math.min(box.lowerY, lowerY),
                Math.max(box.rightX, rightX),
                Math.max(box.upperY, upperY)
        );
    }


}
