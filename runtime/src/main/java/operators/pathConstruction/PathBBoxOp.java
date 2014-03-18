package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;
import runtime.graphics.figures.PSPoint;

/**
 * Created by user on 16.03.14.
 */
public class PathBBoxOp extends AbstractGraphicOperator {
    public static final PathBBoxOp instance = new PathBBoxOp();

    protected PathBBoxOp() {
        super();
    }

    @Override
    public void execute() { // -- pathbbox llx lly urx ury
        PSPoint[] bBox = gState.currentPath.getBBox();
        double llx = bBox[0].getX(), lly = bBox[0].getY();
        double urx = bBox[1].getX(), ury = bBox[1].getY();
        runtime.pushToOperandStack(new PSObject(new PSReal(llx)));
        runtime.pushToOperandStack(new PSObject(new PSReal(lly)));
        runtime.pushToOperandStack(new PSObject(new PSReal(urx)));
        runtime.pushToOperandStack(new PSObject(new PSReal(ury)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("pathbbox");
    }
}
