package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;

/**
 * Created by User on 30/1/2015.
 */
public class SelectFontOp extends AbstractGraphicOperator {
    public static final SelectFontOp instance = new SelectFontOp();

    protected SelectFontOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject fontParam = runtime.popFromOperandStack();
        PSObject oName = runtime.popFromOperandStack();
        if (oName == null || oName.getType() != Type.NAME || fontParam == null ||
                !(fontParam.isNumber() || fontParam.isMatrix())) {
            runtime.pushToOperandStack(oName);
            runtime.pushToOperandStack(fontParam);
            fail();
        }

        if (fontParam.isNumber()) {
            runtime.pushToOperandStack(oName);
            FindFontOp.instance.interpret();
            runtime.pushToOperandStack(fontParam);
            ScaleFontOp.instance.interpret();
            SetFontOp.instance.interpret();
        } else {
            runtime.pushToOperandStack(oName);
            FindFontOp.instance.interpret();
            runtime.pushToOperandStack(fontParam);
            MakeFontOp.instance.interpret();
            SetFontOp.instance.interpret();
        }

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("selectfont");
    }
}
