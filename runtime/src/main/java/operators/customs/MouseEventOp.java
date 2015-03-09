package operators.customs;

import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by User on 16/2/2015.
 */
public class MouseEventOp extends Operator {

    public static final MouseEventOp instance = new MouseEventOp();

    protected MouseEventOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 3) {
            fail();
            return;
        }
        new PSObject(new PSString("(graphicsEngine/basics/mouseEvent.ps) (r) file run")).interpret(0);
        if (!runtime.isCompiling) {
            runtime.executeCallStack();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mouseEvent");
    }
}
