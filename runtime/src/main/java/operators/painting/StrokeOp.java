package operators.painting;

import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.GState;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by user on 16.03.14.
 */
public class StrokeOp extends PSPrimitive {
    public static final StrokeOp instance = new StrokeOp();

    protected StrokeOp() {
        super();
    }

    @Override
    public void interpret(Context context) { //-- setGraphicsSettings --

        GState gState = context.getGState();

        setPSPath(context.getGState().currentPath);
        setContext(context);

        gState.newCurrentPath();
        super.interpret(context);

    }

    @Override
    public void paint() {
        PSDrawer.getInstance().stroke(context, psPath);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stroke");
    }
}
