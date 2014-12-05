package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;

/**
 * Created by user on 11/1/14.
 */
public class ScaleFontOp extends AbstractGraphicOperator {
    public static final ScaleFontOp instance = new ScaleFontOp();

    protected ScaleFontOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject oNum = runtime.popFromOperandStack();
        PSObject oDict = runtime.popFromOperandStack();

        if (oNum == null || oDict == null || oNum.getType() != Type.INTEGER
                || oDict.getType() != Type.DICTIONARY) {
            runtime.pushToOperandStack(oDict);
            runtime.pushToOperandStack(oNum);
            return;
        }

        PSObject key = new PSObject(new PSName("scale"));
        runtime.pushToOperandStack(new PSObject(((PSDictionary) oDict.getValue()).put(key, oNum)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("scalefont");
    }
}
