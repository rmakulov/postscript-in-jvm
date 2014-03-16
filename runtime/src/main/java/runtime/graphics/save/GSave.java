package runtime.graphics.save;

import runtime.graphics.GraphicsState;
import runtime.graphics.matrix.TransformMatrix;
import runtime.graphics.paths.PSPath;
import runtime.graphics.point.PSPoint;

import java.awt.*;

/**
 * Created by user on 16.03.14.
 */

public class GSave {
    private double lineWidth  ; // 1/72 inch
    private PSPoint currentPoint ;
    private PSPath currentPath ;
    private PSPath clippingPath ;
    private Color color ;
    private TransformMatrix cTM ;
    private int lineJoin ;
    private int lineCap ;
    private double miterLimit ;
    private boolean madeByGSaveOp ;

    public GSave(boolean made){
        madeByGSaveOp = made;
    }

    public boolean isMadeByGSaveOp(){
        return madeByGSaveOp ;
    }

    public void setSnapshot(){
        GraphicsState inst = GraphicsState.getInstance() ;
        inst.lineWidth = lineWidth ;
        inst.currentPoint = currentPoint ;
        inst.clippingPath = clippingPath ;
        inst.color = color ;
        inst.cTM = cTM ;
        inst.lineJoin = lineJoin ;
        inst.lineCap = lineCap ;
        inst.miterLimit = miterLimit ;
    }

    public void getSnapshot(){
        GraphicsState inst = GraphicsState.getInstance() ;
        lineWidth = inst.lineWidth ;
        currentPoint = inst.currentPoint ;
        currentPath = inst.currentPath ;
        clippingPath = inst.clippingPath ;
        color = inst.color ;
        cTM = inst.cTM ;
        lineJoin = inst.lineJoin ;
        miterLimit = inst.miterLimit ;
    }

}
