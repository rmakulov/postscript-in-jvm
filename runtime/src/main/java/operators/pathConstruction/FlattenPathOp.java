package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 26.03.14.
 */
public class FlattenPathOp extends AbstractGraphicOperator {
    public static final FlattenPathOp instance = new FlattenPathOp();

    protected FlattenPathOp() {
        super();
    }

    @Override
    public void execute() {
        //do nothing
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("flattenpath");
    }
}
