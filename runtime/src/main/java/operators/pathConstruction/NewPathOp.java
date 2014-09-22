package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.paths.PSPath;

/**
 * Created by user on 15.03.14.
 */
public class NewPathOp extends AbstractGraphicOperator {
    public static final NewPathOp instance = new NewPathOp();

    protected NewPathOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.getGState().currentPath = new PSPath();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("newpath");
    }
}
