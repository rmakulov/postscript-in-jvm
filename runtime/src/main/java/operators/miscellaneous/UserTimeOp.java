package operators.miscellaneous;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by Дмитрий on 25.03.14.
 */
public class UserTimeOp extends Operator {
    public static final UserTimeOp instance = new UserTimeOp();
    public static long beginTime = System.currentTimeMillis();


    protected UserTimeOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(new PSInteger((int) UserTimeOp.getUserTime())));
    }

    public static long getUserTime() {
        return System.currentTimeMillis() - beginTime;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("usertime");
    }
}
