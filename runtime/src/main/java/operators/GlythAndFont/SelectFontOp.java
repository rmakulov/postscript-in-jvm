package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by User on 30/1/2015.
 */
public class SelectFontOp extends AbstractGraphicOperator {
    public static final SelectFontOp instance = new SelectFontOp();

    protected SelectFontOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSObject fontParam = context.popFromOperandStack();
        PSObject oName = context.popFromOperandStack();
        if (oName == null || oName.getType() != Type.NAME || fontParam == null ||
                !(fontParam.isNumber() || fontParam.isMatrix())) {
            context.pushToOperandStack(oName);
            context.pushToOperandStack(fontParam);
            fail();
        }

        if (fontParam.isNumber()) {
            context.pushToOperandStack(oName);
            FindFontOp.instance.interpret(context);
            context.pushToOperandStack(fontParam);
            ScaleFontOp.instance.interpret(context);
            SetFontOp.instance.interpret(context);
        } else {
            context.pushToOperandStack(oName);
            FindFontOp.instance.interpret(context);
            context.pushToOperandStack(fontParam);
            MakeFontOp.instance.interpret(context);
            SetFontOp.instance.interpret(context);
        }

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("selectfont");
    }
}
