package operators;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBytecode;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by user on 31.03.15.
 */
public class NewThreadOp extends Operator {
    public static final NewThreadOp instance = new NewThreadOp();

    protected NewThreadOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) {
            fail();
            return;
        }
        PSObject proc = context.popFromOperandStack();
        PSObject argsCount = context.popFromOperandStack();
        int count = ((PSInteger) argsCount.getValue()).getIntValue();

        PSObject[] psObjects = new PSObject[count];
        for (int i = 0; i < count; i++) {
            psObjects[i] = context.popFromOperandStack();
        }
        Context threadContext = new Context();
        for (int i = count - 1; i > -1; i--) {
            threadContext.pushToOperandStack(psObjects[i]);
        }
        if (proc.isBytecode()) {
            ((PSBytecode) proc.getValue()).setContext(context);
        }
//        try {
//            threadContext.pushToCallStack(new StringProcedure(threadContext,new PSObject(new PSString("(graphicsEngine/basics/glib.ps) (r) file run"))));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        runtime.startNewTask(threadContext, proc);

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("newThread");
    }
}