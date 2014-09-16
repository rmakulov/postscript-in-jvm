package operators.GlythAndFont;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 16.03.14.
 */
public class StringWidthOp extends Operator {
    public static final StringWidthOp instance = new StringWidthOp();

    protected StringWidthOp() {
        super();
    }

    @Override
    public void interpret() { // stinrg stringwidth w_x w_y
        PSObject oStr = runtime.popFromOperandStack();
        if (oStr == null || !(oStr.getType() == Type.STRING)) {
            runtime.pushToOperandStack(oStr);
            return;
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stringwidth");
    }
}
