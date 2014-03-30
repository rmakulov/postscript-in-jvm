package runtime.graphics;

import java.awt.*;

/**
 * Created by Дмитрий on 19.03.14.
 */
public class GraphicsSettings {
    public static GraphicsSettings mainInstance = new GraphicsSettings();
    public Color color;
    public double lineWidth; // 1/72 inch
    public int lineJoin;
    public int lineCap;
    public double miterLimit;
    public float[] dash;
    public float dashPhase = 0;
    public int flatness;

    public GraphicsSettings(Color color, double lineWidth, int lineJoin, int lineCap, double miterLimit, float[] dash, float dashPhase) {
        this.color = color;
        this.lineWidth = lineWidth;
        this.lineJoin = lineJoin;
        this.lineCap = lineCap;
        this.miterLimit = miterLimit;
        this.dash = dash;
        if (dash != null && dash.length == 0) {
            this.dash = null;
        }
        this.dashPhase = dashPhase;
        this.flatness = 1;
    }

    public GraphicsSettings() {
        lineWidth = GraphicsState.psUnitToPixel(1.0);
        color = Color.BLACK;
        lineJoin = 0;
        lineCap = 0;
        miterLimit = 10.0;
    }

    public GraphicsSettings clone() {
        return new GraphicsSettings(new Color(color.getRGB()), lineWidth, lineJoin, lineCap, miterLimit, dash, dashPhase);
    }
}
