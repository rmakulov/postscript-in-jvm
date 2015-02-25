package operators.customs;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.graphics.frame.PSImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by user on 25.02.15.
 */
public class GetColorOp extends Operator {
    public static final GetColorOp instance = new GetColorOp();

    protected GetColorOp() {
        super();
    }

    @Override
    public void interpret() {

        if (runtime.getOperandStackSize() < 2) {
            fail();
            return;
        }
        PSObject oY = runtime.popFromOperandStack();
        PSObject oX = runtime.popFromOperandStack();
        if (!(oY.isInteger() && oX.isInteger())) {
            runtime.pushToOperandStack(oX);
            runtime.pushToOperandStack(oY);
            fail();
        }
        int y = ((PSInteger) oY.getValue()).getIntValue();
        int x = ((PSInteger) oX.getValue()).getIntValue();

/*        runtime.pushToOperandStack(oX);
        runtime.pushToOperandStack(oY);
        runtime.findValue("getGelement").interpret(0);
        runtime.executeCallStack();
        System.out.print(runtime.popFromOperandStack());*/

        BufferedImage image = PSImage.getInstanceImage();

        //    y = PSImage.height - y;
        Color rgb = new Color(image.getRGB(x, y));
        double green = rgb.getGreen();
        double red = rgb.getRed();
        double blue = rgb.getBlue();

        runtime.pushToOperandStack(new PSObject(new PSReal(red / 255)));
        runtime.pushToOperandStack(new PSObject(new PSReal(green / 255)));
        runtime.pushToOperandStack(new PSObject(new PSReal(blue / 255)));

/*        Color lastColor =Color.WHITE;
        for (int i = 0; i < 650; i++) {
            for (int j = 0; j < 842; j++) {
                Color color = new Color(image.getRGB(i, j));
                if (!color.equals(Color.WHITE) && !color.equals(lastColor)){
                    System.out.println(color);
                    lastColor=color;
                }
            }
        }*/
//        System.out.println(red + " " + green + " " + blue);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("getcolor");
    }
}
