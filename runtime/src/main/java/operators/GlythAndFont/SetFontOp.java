package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;

/**
 * Created by user on 11/1/14.
 */
public class SetFontOp extends AbstractGraphicOperator {
    public static final SetFontOp instance = new SetFontOp();

    protected SetFontOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject oDict = runtime.popFromOperandStack();
        if (oDict == null || oDict.getType() != Type.DICTIONARY) {
            runtime.pushToOperandStack(oDict);
        }
        runtime.getGState().font = oDict;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setfont");
    }
}
