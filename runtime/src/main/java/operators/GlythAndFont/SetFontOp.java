package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 11/1/14.
 */
public class SetFontOp extends AbstractGraphicOperator {
    public static final SetFontOp instance = new SetFontOp();

    protected SetFontOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject oDict = context.popFromOperandStack();
        if (oDict == null || oDict.getType() != Type.DICTIONARY) {
            context.pushToOperandStack(oDict);
        }
        context.getGState().setFont(oDict);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setfont");
    }
}
