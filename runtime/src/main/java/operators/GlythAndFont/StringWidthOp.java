package operators.GlythAndFont;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;
import runtime.graphics.frame.PSDrawer;
import runtime.graphics.frame.PSImage;

import java.awt.*;

/**
 * Created by user on 16.03.14.
 */
public class StringWidthOp extends Operator {
    public static final StringWidthOp instance = new StringWidthOp();

    protected StringWidthOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // string stringwidth w_x w_y
        PSObject oStr = context.popFromOperandStack();
        if (oStr == null || !(oStr.getType() == Type.STRING)) {
            fail();
            return;
        }
        String text = ((PSString) oStr.getValue()).getString();

        Graphics2D g2 = (Graphics2D) PSImage.getDefaultGraphics();
        Font font = PSDrawer.getInstance().getFont(context);

        FontMetrics metrics = g2.getFontMetrics(font);
        int wx = metrics.stringWidth(text);
        //todo
        int wy = 0;
        context.pushToOperandStack(new PSObject(new PSInteger(wx)));
        context.pushToOperandStack(new PSObject(new PSInteger(wy)));

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("stringwidth");
    }
}
