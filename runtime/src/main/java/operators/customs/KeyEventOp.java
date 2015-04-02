package operators.customs;

import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 03.03.15.
 */
public class KeyEventOp extends Operator {
    public static final KeyEventOp instance = new KeyEventOp();

    protected KeyEventOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) {
            fail();
            return;
        }
//        PSDictionary dict = ((PSDictionary) runtime.findValue("gelements").getValue());
        try {
            context.pushToCallStack(new StringProcedure(context, new PSObject(new PSString("(graphicsEngine/basics/events/keyEvent.ps) (r) file run"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!runtime.isCompiling) {
            context.executeCallStack();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("keyEvent");
    }
}
