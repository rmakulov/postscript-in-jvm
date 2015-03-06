package operators.operandStackManipulation;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 06.03.15.
 */
public class PstackOp extends Operator {
    public static final PstackOp instance = new PstackOp();

    protected PstackOp() {
        super();
    }

    @Override
    public void interpret() {
        for (PSObject o : runtime.getOperandStack()) {

            System.out.println(o.toStringView());
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("pstack");
    }
}
