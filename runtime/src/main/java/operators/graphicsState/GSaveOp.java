package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.save.GSave;

/**
 * Created by user on 16.03.14.
 */
public class GSaveOp extends AbstractGraphicOperator {
    @Override
    public void execute() { // -- gsave --
        runtime.gsave(true);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("gsave");
    }
}
