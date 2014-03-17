package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.frame.PSFrame;

/**
 * Created by user on 17.03.14.
 */
public class ShowPageOp extends AbstractGraphicOperator{

    public static final ShowPageOp instance = new ShowPageOp();

    protected ShowPageOp() {
        super();
    }

    @Override
    public void execute() {
       PSFrame frame = PSFrame.instance ;
       frame.repaint();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("showpage");
    }
}
