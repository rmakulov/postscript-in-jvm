package operators.file;

import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBoolean;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

public class StackOp extends Operator {
    public static final StackOp instance = new StackOp();

    protected StackOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o1 = runtime.popFromOperandStack();
        String newString;
        switch (o1.getType()) {
            case BOOLEAN:
                newString = ((PSBoolean) o1.getValue()).getFlag() + "";
                break;
            case INTEGER:
                newString = ((PSInteger) o1.getValue()).getIntValue() + "";
                break;
            case REAL:
                newString = ((PSReal) o1.getValue()).getRealValue() + "";
                break;
            case STRING:
                newString = ((PSString) o1.getValue()).getString();
                break;
            default:
                newString = "--nostringval--";
        }
        System.out.println(newString);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stack");
    }
}
