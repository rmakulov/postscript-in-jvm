package operators;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSFrame;
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
    public void interpret(Context context) {
        PSImage.reset();
        PSFrame.getInstance().repaint();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("init");
    }
}