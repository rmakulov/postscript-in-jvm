package operators;

import psObjects.values.simple.Operator;
import runtime.graphics.GState;

/**
 * Created by user on 15.03.14.
 */
public abstract class AbstractGraphicOperator extends Operator {
    protected GState gState = GState.getInstance();

}
