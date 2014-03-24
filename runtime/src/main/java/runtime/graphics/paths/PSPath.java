package runtime.graphics.paths;

/**
 * Created by user on 15.03.14.
 */

import runtime.graphics.figures.PSPoint;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class PSPath {
    private ArrayList<GeneralPath> generalPaths;
    //public GraphicsSettings graphicsSettings;

    public PSPath(ArrayList<GeneralPath> generalPaths) {
        this.generalPaths = generalPaths;
    }

    public PSPath() {
        generalPaths = new ArrayList<GeneralPath>();
        generalPaths.add(new GeneralPath());
//        graphicsSettings = new GraphicsSettings();
    }

    private PSPath(GeneralPath generalPath) {
        generalPaths.add(generalPath);
    }

    public GeneralPath getLastSequentialPath() {
        if (generalPaths.size() == 0) {
            return null;
        }
        return generalPaths.get(generalPaths.size() - 1);
    }

    public Rectangle getBBox() {
        Rectangle box = null;
        for (GeneralPath generalPath : generalPaths) {
            box = box == null ? generalPath.getBounds() : box.union(generalPath.getBounds());
        }
        return box;
    }

    //absolute coordinates in postscript
    public void addLineSegment(PSPoint start, PSPoint end) {
        if (getLastSequentialPath().getCurrentPoint().distance(start.getPoint2D()) > 0.0001) {
            GeneralPath newPath = new GeneralPath();
            newPath.moveTo(start.getX(), start.getY());
            newPath.lineTo(end.getX(), end.getY());
            generalPaths.add(newPath);
        }
        getLastSequentialPath().lineTo(end.getX(), end.getY());
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

        getLastSequentialPath().append(arcDouble, connect);
    }

    public void closePath() {
        for (GeneralPath generalPath : generalPaths) {
            generalPath.closePath();
        }
    }

    public PSPath clone() {
        ArrayList<GeneralPath> newPath = new ArrayList<GeneralPath>();
        newPath.addAll(generalPaths);
        for (GeneralPath generalPath : generalPaths) {
            newPath.add((GeneralPath) generalPath.clone());
        }
        return new PSPath(newPath);
    }

    public ArrayList<GeneralPath> getGeneralPaths() {
        return generalPaths;
    }
}
