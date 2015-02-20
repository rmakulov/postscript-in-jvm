package operators.customs;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.graphics.frame.PSImage;

/**
 * Created by user on 18.02.15.
 */
public class InitOp extends Operator {

    public static final InitOp instance = new InitOp();

    protected InitOp() {
        super();
    }

    @Override
    public void interpret() {
        PSImage.reset();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("init");
    }
}