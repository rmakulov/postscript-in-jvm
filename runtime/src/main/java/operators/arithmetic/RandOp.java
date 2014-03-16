package operators.arithmetic;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Runtime;

import java.util.Random;

/**
 * Created by Дмитрий on 15.03.14.
 */
public class RandOp extends Operator {
    public static final RandOp instance = new RandOp();

    protected RandOp() {
        super();
    }
    @Override
    public void execute() {
        runtime.Runtime runtime = Runtime.getInstance();
        runtime.pushToOperandStack(new PSObject(new PSInteger(new Random().nextInt())));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rand");
    }

    public final static char getSymbolicChar = 'R';
}
