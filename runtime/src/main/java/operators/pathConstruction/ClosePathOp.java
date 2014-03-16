package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 15.03.14.
 */
public class ClosePathOp extends AbstractGraphicOperator {
    @Override
    public void execute() {
        gState.currentPath.closePath();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("closepath");
    }
}
