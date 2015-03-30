package operators.painting;

import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.GState;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by Дмитрий on 26.03.14.
 */
public class EofillOp extends PSPrimitive {
    public static final EofillOp instance = new EofillOp();

    protected EofillOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // Fill current path with current color
        //todo

        GState gState = context.getGState();

        setPSPath(context.getGState().currentPath);
        setContext(context);

        gState.newCurrentPath();
        super.interpret(context);
    }


    @Override
    public void paint() {
        PSDrawer.getInstance().eofill(context, psPath);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("eofill");
    }
}
