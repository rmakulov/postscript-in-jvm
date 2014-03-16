package operators;

import psObjects.values.simple.Operator;
import runtime.graphics.GraphicsState;

/**
 * Created by user on 15.03.14.
 */
public abstract class AbstractGraphicOperator extends Operator {
    protected GraphicsState gState = GraphicsState.getInstance();

}
