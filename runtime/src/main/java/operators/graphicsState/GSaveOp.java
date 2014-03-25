package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 16.03.14.
 */
public class GSaveOp extends AbstractGraphicOperator {
    public static final GSaveOp instance = new GSaveOp();

    protected GSaveOp() {
        super();
    }

    @Override
    public void execute() { // -- gsave --
        runtime.gsave(true);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("gsave");
    }
}
