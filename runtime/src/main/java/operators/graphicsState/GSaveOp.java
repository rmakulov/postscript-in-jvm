package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 16.03.14.
 */
public class GSaveOp extends AbstractGraphicOperator {
    public static final GSaveOp instance = new GSaveOp();

    protected GSaveOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // -- gsave --
        runtime.gsave(context, true);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("gsave");
    }
}
