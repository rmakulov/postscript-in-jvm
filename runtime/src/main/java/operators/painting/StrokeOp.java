package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by user on 16.03.14.
 */
public class StrokeOp extends AbstractGraphicOperator {
    public static final StrokeOp instance = new StrokeOp();

    protected StrokeOp() {
        super();
    }

    @Override
    public void interpret(Context context) { //-- setGraphicsSettings --
        PSDrawer.getInstance().stroke(context);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stroke");
    }
}
