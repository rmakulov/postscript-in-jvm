package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class InitClipOp extends AbstractGraphicOperator {
    public static final InitClipOp instance = new InitClipOp();

    protected InitClipOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.getGState().initClip();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("initclip");
    }
}
