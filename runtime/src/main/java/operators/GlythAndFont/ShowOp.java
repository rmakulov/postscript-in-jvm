package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by user on 16.03.14.
 */
public class ShowOp extends AbstractGraphicOperator {
    public static final ShowOp instance = new ShowOp();

    protected ShowOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // string show --
        PSObject oStr = context.popFromOperandStack();
        if (oStr == null || !(oStr.getType() == Type.STRING) || context.getGState().getFont() == null) {
            context.pushToOperandStack(oStr);
            return;
        }
        String str = ((PSString) oStr.getValue()).getString();
        PSDrawer.getInstance().showOld(context, str);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("show");
    }
}
