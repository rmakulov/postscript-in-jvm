package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.figures.PSPoint;
import runtime.graphics.paths.PSPath;

/**
 * Created by user on 16.03.14.
 */
public class FillOp extends AbstractGraphicOperator {
    public static final FillOp instance = new FillOp();

    protected FillOp() {
        super();
    }

    @Override
    public void execute() { // Fill current path with current color
        PSPath curPath = gState.currentPath;
        PSPath cliPath = gState.clippingPath;
        PSPoint[] curPathBBox = curPath.getBBox();
        PSPoint[] cliPathBBox = cliPath.getBBox();
        for (int x = (int) curPathBBox[0].getX(); x < curPathBBox[1].getX(); x++) {
            for (int y = (int) curPathBBox[0].getY(); y < curPathBBox[1].getY(); y++) {
                PSPoint point = new PSPoint(x, y);
                //if(curPathBBox.isInside(figures) && cliPathBBox.isInside(figures) ){
                //fill this figures with currentcolor
                //}
            }
        }
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("fill");
    }
}
