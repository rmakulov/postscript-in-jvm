package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.GState;

/**
 * repeatedly performs grestore operations until it encounters a graphics state that
 * was saved by a save operation (as opposed to gsave), leaving that state on the top
 * of the graphics state stack and resetting the current graphics state from it. If no
 * such graphics state is encountered , the current graphics state is clear to the
 * bottommost state on the stack and the stack is cleared to empty. If the graphics
 * state stack is empty, grestoreall has no effect.
 */
public class GRestoreAllOp extends AbstractGraphicOperator {
    public static final GRestoreAllOp instance = new GRestoreAllOp();

    protected GRestoreAllOp() {
        super();
    }

    @Override
    public void interpret(Context context) {//todo check errors with stack and type
        if (context.getGraphicStackSize() == 0) {
            return;
        }
        GState gState = context.peekFromGraphicStack();
        while (context.getGraphicStackSize() > 0 && gState.isMadeByGSaveOp()) {
//            GRestoreOp.instance.interpret();
            context.removeFromGraphicStack();
            gState = context.peekFromGraphicStack();
        }

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("grestoreall");
    }
}
