package operators.typeAttributeConvertation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class CvxOp extends Operator {

    public static final CvxOp instance = new CvxOp();

    protected CvxOp() {
        super();
    }

    @Override
    public void execute() {
        PSObject o = runtime.popFromOperandStack();
        if (o == null) return;
        runtime.pushToOperandStack(o.cvx());
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cvx");
    }
}
