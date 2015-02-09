package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;

/**
 * Created by User on 30/1/2015.
 */
public class MakeFontOp extends AbstractGraphicOperator {
    public static final MakeFontOp instance = new MakeFontOp();

    protected MakeFontOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject oMatrix = runtime.popFromOperandStack();
        PSObject oDict = runtime.popFromOperandStack();

        if (oMatrix == null || oDict == null || !oMatrix.isMatrix()
                || oDict.getType() != Type.DICTIONARY) {
            runtime.pushToOperandStack(oDict);
            runtime.pushToOperandStack(oMatrix);
            fail();
        }

        PSObject key = new PSObject(new PSName("matrix"));
        runtime.pushToOperandStack(new PSObject(((PSDictionary) oDict.getValue()).put(key, oMatrix)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("makefont");
    }
}

