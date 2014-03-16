package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;
import runtime.graphics.point.PSPoint;

/**
 * Created by user on 16.03.14.
 */
public class PathBBoxOp extends AbstractGraphicOperator {
    @Override
    public void execute() { // -- pathbbox llx lly urx ury
        PSPoint[] bBox = gState.currentPath.getBBox() ;
        double llx = bBox[0].getX(), lly = bBox[0].getY() ;
        double upx = bBox[1].getX(), upy = bBox[1].getY() ;
        runtime.pushToOperandStack(new PSObject(new PSReal(llx)));
        runtime.pushToOperandStack(new PSObject(new PSReal(lly)));
        runtime.pushToOperandStack(new PSObject(new PSReal(upx)));
        runtime.pushToOperandStack(new PSObject(new PSReal(upy)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("pathbbox");
    }
}
