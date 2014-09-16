package operators.operandStackManipulation;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class ExchOp extends Operator {
    public static final ExchOp instance = new ExchOp();

    protected ExchOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.exchangeTopOfOperandStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exch");
    }
}
