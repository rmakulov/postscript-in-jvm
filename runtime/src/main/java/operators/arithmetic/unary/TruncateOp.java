package operators.arithmetic.unary;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class TruncateOp extends Operator{
    @Override
    public void execute() {
         //todo
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("truncate");
    }

    public final static char getSymbolicChar = 'T';
}
