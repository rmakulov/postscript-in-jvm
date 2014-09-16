package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSReal;

import java.awt.*;

/**
 * Created by user on 16.03.14.
 */
public class PathBBoxOp extends AbstractGraphicOperator {
    public static final PathBBoxOp instance = new PathBBoxOp();

    protected PathBBoxOp() {
        super();
    }

    @Override
    public void interpret() { // -- pathbbox llx lly urx ury
        Rectangle bBox = gState.currentPath.getBBox();
        double llx = bBox.getX(), lly = bBox.getY();
        double urx = bBox.getX() + bBox.width, ury = bBox.getY() + bBox.height;
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
