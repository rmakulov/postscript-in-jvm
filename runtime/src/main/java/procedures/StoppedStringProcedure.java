package procedures;

import psObjects.PSObject;
import psObjects.values.simple.PSBoolean;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StoppedStringProcedure extends StringProcedure {
    public StoppedStringProcedure(PSObject stringObj) {
        super("StoppedStringProcedure", stringObj);
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
