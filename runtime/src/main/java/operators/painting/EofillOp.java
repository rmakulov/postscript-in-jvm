package operators.painting;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class EofillOp extends Operator {
    public static final EofillOp instance = new EofillOp();

    protected EofillOp() {
        super();
    }

    @Override
    public void execute() { // Fill current path with current color
        //todo
        PSDrawer.getInstance().eofill();
    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("eofill");
    }
}
