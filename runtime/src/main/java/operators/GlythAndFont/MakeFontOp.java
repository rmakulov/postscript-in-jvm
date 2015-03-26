package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by User on 30/1/2015.
 */
public class MakeFontOp extends AbstractGraphicOperator {
    public static final MakeFontOp instance = new MakeFontOp();

    protected MakeFontOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject oMatrix = context.popFromOperandStack();
        PSObject oDict = context.popFromOperandStack();

        if (oMatrix == null || oDict == null || !oMatrix.isMatrix()
                || oDict.getType() != Type.DICTIONARY) {
            context.pushToOperandStack(oDict);
            context.pushToOperandStack(oMatrix);
            fail();
        }

        PSObject key = new PSObject(new PSName("matrix"));
        context.pushToOperandStack(new PSObject(((PSDictionary) oDict.getValue()).put(key, oMatrix)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("makefont");
    }
}

