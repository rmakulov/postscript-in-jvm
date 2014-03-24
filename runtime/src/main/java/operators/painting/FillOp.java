package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.frame.PSDrawer;


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
        PSDrawer.getInstance().fill();
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("fill");
    }
}
