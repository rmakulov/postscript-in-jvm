package procedures;

import psObjects.PSObject;
import psObjects.values.simple.PSBoolean;
import runtime.Context;

import java.io.UnsupportedEncodingException;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StoppedStringProcedure extends StringProcedure {
    public StoppedStringProcedure(Context context, PSObject stringObj) throws UnsupportedEncodingException {
        super(context, stringObj);
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
