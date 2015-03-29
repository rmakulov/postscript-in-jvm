package operators.painting;

import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.GState;
import runtime.graphics.frame.PSDrawer;


/**
 * Created by user on 16.03.14.
 */
public class FillOp extends PSPrimitive {
    public static final FillOp instance = new FillOp();

    protected FillOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // Fill current path with current color

        GState gState = context.getGState();

        setPSPath(context.getGState().currentPath);
        setContext(context);

        gState.newCurrentPath();
        super.interpret(context);

    }

    @Override
    public void paint() {
        PSDrawer.getInstance().fill(context, psPath);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("fill");
    }
}
