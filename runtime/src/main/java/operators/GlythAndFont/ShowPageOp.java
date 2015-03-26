package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSDrawer;

/**
 * Created by user on 17.03.14.
 */
public class ShowPageOp extends AbstractGraphicOperator {

    public static final ShowPageOp instance = new ShowPageOp();

    protected ShowPageOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSDrawer.getInstance().showPage();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("showpage");
    }
}
