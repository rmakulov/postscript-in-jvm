package runtime.graphics;

import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.point.PSPoint;
import runtime.graphics.paths.* ;


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
        //clippingPath = new PSPath() ; null
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
    private void psShowPage(){
        //.setvisible(true)
    }

    private void psShow(String s){
        //Java output to frame
    }
//---------------------Fonts
    private PSPoint psStringWidth(String s){
        PSPoint width = new PSPoint() ;
        //todo
        return width ;
    }
    private static double psUnitToPixel(double psUnits){
        return psUnits / 72 * 96 ;
        //96 - это число пикселей в дюйме
    }
    private static double pixelToPSUnit(double pixels){
        return pixels / 96 * 72 ;
    }
}
