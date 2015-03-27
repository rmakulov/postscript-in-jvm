package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by user on 11/1/14.
 */
public class ScaleFontOp extends AbstractGraphicOperator {
    public static final ScaleFontOp instance = new ScaleFontOp();

    protected ScaleFontOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject oNum = context.popFromOperandStack();
        PSObject oDict = context.popFromOperandStack();

        if (oNum == null || oDict == null || oNum.getType() != Type.INTEGER
                || oDict.getType() != Type.DICTIONARY) {
            context.pushToOperandStack(oDict);
            context.pushToOperandStack(oNum);
            return;
        }

        PSObject key = new PSObject(new PSName("scale"));
        context.pushToOperandStack(new PSObject(((PSDictionary) oDict.getValue()).put(key, oNum)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("scalefont");
    }
}
