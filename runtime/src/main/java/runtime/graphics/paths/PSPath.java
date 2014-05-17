package runtime.graphics.paths;

/**
 * Created by user on 15.03.14.
 */

import runtime.graphics.figures.PSPoint;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class PSPath {
    private GeneralPath generalPath;
    //public GraphicsSettings graphicsSettings;


    public PSPath() {
        generalPath = new GeneralPath();
    }

    public PSPath(GeneralPath generalPath) {
        this.generalPath = generalPath;
    }

    public GeneralPath getGeneralPath() {
        return generalPath;
    }

    public Rectangle getBBox() {
        return generalPath.getBounds();
    }

    //absolute coordinates in postscript
    public void addLine(PSPoint start, PSPoint end) {
        generalPath.lineTo(end.getX(), end.getY());
    }

    //absolute coordinates in postscript
    public void addCurve(PSPoint p0, PSPoint p1, PSPoint p2, PSPoint p3) {
        generalPath.moveTo(p0.getX(), p0.getY());
        generalPath.curveTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
    }

    //absolute coordinates in postscript
    public void addArc(PSPoint absBegin, PSPoint absEnd, PSPoint absCenter, double absXRadius, double absYRadius,
                       double relAngle1, double RelAngle2, boolean clockwise, boolean connect) {
//        generalPath.moveTo(100,100);

        double x = absCenter.getX() - absXRadius;
        double y = absCenter.getY() - absYRadius;
        double w = 2 * absXRadius;
        double h = 2 * absYRadius;
        double extent = relAngle1 - RelAngle2;
//todo correct angles without minus
        Arc2D.Double arcDouble = new Arc2D.Double(x, y, w,
                h, -relAngle1, extent, Arc2D.OPEN);

        generalPath.append(arcDouble, connect);

    }

    public void closePath() {
        generalPath.closePath();
    }

    public PSPath clone() {
        return new PSPath((GeneralPath) generalPath.clone());
    }

}
