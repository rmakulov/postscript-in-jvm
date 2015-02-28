package operators.customs;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 27.02.15.
 */
public class DebugOp extends Operator {

    public static final DebugOp instance = new DebugOp();

    protected DebugOp() {
        super();
    }

    @Override
    public void interpret() {
        //doNothing
        System.out.println("Operand stack size " + runtime.getOperandStackSize());
        System.out.println("Call stack size " + runtime.getCallStackSize() + "\n");
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("debug");
    }
}
