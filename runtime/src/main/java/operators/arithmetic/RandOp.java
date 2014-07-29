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
    public static Random random = new Random();

    protected RandOp() {
        super();
    }

    @Override
    public void execute() {

        runtime.Runtime runtime = Runtime.getInstance();
        int randomInt = random.nextInt(Integer.MAX_VALUE);
//        int randomInt = 0;
//        try {
//            randomInt = (Integer) asm.getMethod("rand").invoke(null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        runtime.pushToOperandStack(new PSObject(new PSInteger(randomInt)));
    }

    public static void dropRandom() {
        random = new Random();
    }

    public static void setRandomSeed(int seed) {
        random = new Random(seed);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rand");
    }

    public final static char getSymbolicChar = 'R';
}
