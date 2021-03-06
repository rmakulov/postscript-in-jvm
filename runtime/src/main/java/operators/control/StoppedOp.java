package operators.control;

import procedures.StoppedArrayProcedure;
import procedures.StoppedStringProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

import java.io.UnsupportedEncodingException;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StoppedOp extends Operator {
    public static final StoppedOp instance = new StoppedOp();

    protected StoppedOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject psObject = context.popFromOperandStack();
        if (psObject == null) {
            return;
        }
        if (psObject.isProc()) {
            context.pushToCallStack(new StoppedArrayProcedure(context, psObject));
        } else if (psObject.getType() == Type.STRING && psObject.xcheck()) {
            try {
                context.pushToCallStack(new StoppedStringProcedure(context, psObject));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            PSObject[] singleArray = new PSObject[]{psObject};
            PSObject psExecArray = new PSObject(new PSArray(singleArray), Attribute.TreatAs.EXECUTABLE);
            context.pushToCallStack(new StoppedArrayProcedure(context, psExecArray));
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stopped");
    }
}
