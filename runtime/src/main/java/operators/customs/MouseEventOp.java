package operators.customs;

import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

import java.io.UnsupportedEncodingException;

/**
 * Created by User on 16/2/2015.
 */
public class MouseEventOp extends Operator {

    public static final MouseEventOp instance = new MouseEventOp();

    protected MouseEventOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 3) {
            fail();
            return;
        }
        try {
            context.pushToCallStack(new StringProcedure(context, new PSObject(new PSString("(graphicsEngine/basics/events/mouseEvent.ps) (r) file run"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!runtime.isCompiling) {
            context.executeCallStack();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("mouseEvent");
    }
}
