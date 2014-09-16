package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by Дмитрий on 24.03.14.
 */
public class ClipOp extends AbstractGraphicOperator {
    public static final ClipOp instance = new ClipOp();

    protected ClipOp() {
        super();
    }

    @Override
    public void interpret() {
        PSDrawer.getInstance().clip();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("clip");
    }
}
