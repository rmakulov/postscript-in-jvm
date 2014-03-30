package procedures;

import psObjects.PSObject;
import psObjects.values.simple.PSBoolean;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StoppedArrayProcedure extends ArrayProcedure {
    public StoppedArrayProcedure(PSObject arrayObj) {
        super("StoppedArray", arrayObj);
    }

    @Override
    public void procTerminate() {
        super.procTerminate();
        runtime.pushToOperandStack(new PSObject(PSBoolean.FALSE));
    }

    @Override
    public void procBreak() {
        super.procBreak();
        runtime.pushToOperandStack(new PSObject(PSBoolean.TRUE));
    }
}
