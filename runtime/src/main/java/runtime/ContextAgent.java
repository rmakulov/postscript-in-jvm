package runtime;

import psObjects.PSObject;
import psObjects.values.simple.numbers.PSInteger;

/**
 * Created by 1 on 14.03.2017.
 */
public class ContextAgent {
    public static void pushToStack(Context context, int num){
        context.pushToOperandStack(new PSObject(new PSInteger(5)));
    }

}
