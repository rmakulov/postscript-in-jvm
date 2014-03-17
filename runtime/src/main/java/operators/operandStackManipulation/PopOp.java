package operators.operandStackManipulation;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 16.03.14.
 */
public class PopOp extends Operator {
    public static final PopOp instance = new PopOp();

    protected PopOp() {
        super();
    }

    @Override
    public void execute() {
        runtime.popFromOperandStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("pop");
    }
}
