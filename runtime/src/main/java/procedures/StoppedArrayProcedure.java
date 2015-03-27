package procedures;

import psObjects.PSObject;
import psObjects.values.simple.PSBoolean;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StoppedArrayProcedure extends ArrayProcedure {
    public StoppedArrayProcedure(Context context, PSObject arrayObj) {
        super(context, "StoppedArray", arrayObj);
    }

    @Override
    public void procTerminate() {
        super.procTerminate();
        context.pushToOperandStack(new PSObject(PSBoolean.FALSE));
    }

    @Override
    public void procBreak() {
        super.procBreak();
        context.pushToOperandStack(new PSObject(PSBoolean.TRUE));
    }
}
