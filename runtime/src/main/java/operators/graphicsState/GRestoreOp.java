package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.GraphicsState;
import runtime.graphics.save.GSave;

/**
 * Created by user on 16.03.14.
 */
public class GRestoreOp extends AbstractGraphicOperator {
    public static final GRestoreOp instance = new GRestoreOp();

    protected GRestoreOp() {
        super();
    }

    @Override
    public void execute() {
        GSave gsave = runtime.peekFromGraphicStack();
        if (gsave.isMadeByGSaveOp()) {
            runtime.popFromGraphicStack();
        }
        GraphicsState.getInstance().setSnapshot(gsave);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("grestore");
    }
}
