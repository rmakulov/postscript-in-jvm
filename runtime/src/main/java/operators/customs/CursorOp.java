package operators.customs;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSFrame;

import java.awt.*;

/**
 * Created by user on 3/25/15.
 */
public class CursorOp extends Operator {
    public static final CursorOp instance = new CursorOp();

    protected CursorOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        //doNothing
        PSObject oStr = context.popFromOperandStack();
        if (oStr == null || !(oStr.getType() == Type.STRING)) {
            context.pushToOperandStack(oStr);
            fail();
        }
        String str = ((PSString) oStr.getValue()).getString();
        if (str.equals("move")) {
            PSFrame.getInstance().setCursor(new Cursor(Cursor.MOVE_CURSOR));
        } else if (str.equals("default")) {
            PSFrame.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else if (str.equals("e_resize")) {
            PSFrame.getInstance().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        } else {
            context.pushToOperandStack(oStr);
            fail();
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("cursor");
    }
}
