package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;

/**
 * Created by User on 17/2/2015.
 */
public class InitGraphicsOp extends AbstractGraphicOperator {

    public static final InitGraphicsOp instance = new InitGraphicsOp();

    protected InitGraphicsOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.getGraphicStack().reset();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("initgraphics");
    }
}
