package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

import java.util.ArrayList;

/**
 * Created by user on 11/1/14.
 */
public class FindFontOp extends AbstractGraphicOperator {

    public static final FindFontOp instance = new FindFontOp();

    protected FindFontOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject oName = runtime.popFromOperandStack();
        if (oName == null || !(oName.getType() == Type.NAME) || oName.isExecutable()) {
            runtime.pushToOperandStack(oName);
            return;
        }
        String str = ((PSName) oName.getValue()).getStrValue();
        ArrayList<PSObject> arr = new ArrayList<PSObject>();
        // {name : namefont, scale: scalefont}
        arr.add(new PSObject(new PSName("name")));
        arr.add(oName);
        arr.add(new PSObject(new PSName("scale")));
        arr.add(new PSObject(new PSInteger(0)));

        PSObject oDict = new PSObject(new PSDictionary(arr));
        runtime.pushToOperandStack(oDict);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("findfont");
    }

}
