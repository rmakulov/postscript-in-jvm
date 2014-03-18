package runtime.graphics;

import runtime.graphics.figures.PSPoint;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;

import java.awt.*;

/**
 * Created by use 1r on 14.03.14.
 */
public class GraphicsState {
    private static GraphicsState instance = new GraphicsState();

    public double lineWidth  ; // 1/72 inch
    public PSPoint currentPoint ;
    public PSPath currentPath ;
    public PSPath clippingPath ;
    public Color color ;
    public TransformMatrix cTM ;
    public int lineJoin ;
    public int lineCap ;
    public double miterLimit ;

    private GraphicsState(){
        currentPath = new PSPath() ;
        //clippingPath = new PSPath() ; //todo page size rectangle
        lineWidth = psUnitToPixel(1.0) ;
        color = Color.BLACK ;
        cTM = new TransformMatrix();
        lineJoin = 0 ;
        lineCap = 0 ;
        miterLimit = 10.0 ;
    }
    public static GraphicsState getInstance(){
        return instance ;
    }



//---------------------Fonts
    private static double psUnitToPixel(double psUnits){
        return psUnits / 72 * 96 ;
        //96 - это число пикселей в дюйме
    }
    private static double pixelToPSUnit(double pixels){
        return pixels / 96 * 72 ;
    }
}
