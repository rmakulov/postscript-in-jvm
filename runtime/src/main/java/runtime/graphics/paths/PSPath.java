package runtime.graphics.paths;

/**
 * Created by user on 15.03.14.
 */

import runtime.graphics.GraphicsSettings;
import runtime.graphics.figures.PSPoint;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

public class PSPath {
    private GeneralPath generalPath;
    public GraphicsSettings graphicsSettings;
    private PaintingState paintingState = PaintingState.NONE;

    public enum PaintingState {
        FILL, NONE, STROKE
    }

    public void setPaintingState(PaintingState paintingState) {
        this.paintingState = paintingState;
    }

    public PaintingState getPaintingState() {
        return paintingState;
    }

    public PSPath() {
        generalPath = new GeneralPath();
        graphicsSettings = new GraphicsSettings();
    }

    private PSPath(GeneralPath generalPath, GraphicsSettings graphicsSettings) {
        this.generalPath = generalPath;
        this.graphicsSettings = graphicsSettings;
    }


    public Rectangle getBBox() {
        if (generalPath == null) {
            return null;
        }
        return generalPath.getBounds();
    }

    public void setGraphicsSettings(GraphicsSettings settings) {
        this.graphicsSettings = settings;
    }

    //absolute coordinates in postscript
    public void addLineSegment(PSPoint begining, PSPoint end, GraphicsSettings settings) {
        generalPath.lineTo(end.getX(), end.getY());
    }

    //absolute coordinates in postscript
    public void addArc(PSPoint absBegin, PSPoint absEnd, PSPoint absCenter, double absXRadius, double absYRadius,
                       double relAngle1, double RelAngle2, boolean clockwise, GraphicsSettings settings) {
//        generalPath.moveTo(100,100);

        double x = absCenter.getX() - absXRadius;
        double y = absCenter.getY() + absYRadius;
        double w = 2 * absXRadius;
        double h = 2 * absYRadius;
        double extent = relAngle1 - RelAngle2;
//todo correct angles without minus
        Arc2D.Double arcDouble = new Arc2D.Double(x, y, w,
                h, -relAngle1, extent, Arc2D.OPEN);

        generalPath.append(arcDouble, true);
//        generalPath.append(new Arc2D.Double(100,100,100,100,0,-90,Arc2D.OPEN),true);
    }

    public void closePath() {
        generalPath.closePath();
    }


    public PSPath clone() {
        return new PSPath((GeneralPath) this.generalPath.clone(), (GraphicsSettings) this.graphicsSettings.clone());
    }

    public GeneralPath getGeneralPath() {
        return generalPath;
    }
}
