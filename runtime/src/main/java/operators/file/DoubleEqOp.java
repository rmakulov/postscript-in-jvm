package operators.file;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;


public class DoubleEqOp extends Operator {
    // todo norm: Write syntactic representation of any to standard output ﬁle
    public static final DoubleEqOp instance = new DoubleEqOp();

    protected DoubleEqOp() {
        super();
    }

    @Override
    public void execute() {
        runtime.popFromOperandStack();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("==");
    }
}