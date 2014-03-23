package runtime.graphics.paths;

import runtime.graphics.GraphicsSettings;

/**
 * Created by user on 23.03.14.
 */
public class DrawPath {
    public PSPath path;

    public enum PaintingState {
        FILL, NONE, STROKE
    }

    public GraphicsSettings graphicsSettings;
    public PaintingState paintingState;

    public DrawPath() {
        path = new PSPath();
        paintingState = PaintingState.NONE;
    }

    public DrawPath(PSPath drawPath, PaintingState state, GraphicsSettings settings) {
        this.path = drawPath;
        this.paintingState = state;
        this.graphicsSettings = settings;
    }
}
