package operators.painting;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 16.03.14.
 */
public class StrokeOp extends AbstractGraphicOperator {
    public static final StrokeOp instance = new StrokeOp();

    protected StrokeOp() {
        super();
    }

    @Override
    public void execute() { //-- stroke --
        gState.currentPath.stroke() ;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stroke");
    }
}