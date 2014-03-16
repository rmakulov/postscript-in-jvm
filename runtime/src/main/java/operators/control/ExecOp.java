package operators.control;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class ExecOp extends Operator {
    public static final ExecOp instance = new ExecOp();

    protected ExecOp() {
        super();
    }

    @Override
    public void execute() {

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("exec");
    }
}
