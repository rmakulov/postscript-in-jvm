package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 26.09.14.
 */
public class RectClipOp extends AbstractGraphicOperator {
    public static final Operator instance = new RectClipOp();

    @Override
    public void interpret() {
        //todo
//        PSObject psObj = runtime.popFromOperandStack();
//        if(psObj==null) {
//            fail();
//            return;
//        }
//        switch (psObj.getType()){
//            case ARRAY:
//            case STRING:
//                fail(); //todo implement numarray and numstring cases
//                break;
//            case INTEGER:
//            case REAL:
//                fourArguments(psObj);
//                break;
//            default:
//
//        }

    }

    private void fourArguments(PSObject heightObj) {
        PSObject widthObj = runtime.popFromOperandStack();
        PSObject yObj = runtime.popFromOperandStack();
        PSObject xObj = runtime.popFromOperandStack();
        if (widthObj == null || xObj == null || yObj == null ||
                !heightObj.isNumber() || !widthObj.isNumber() || !xObj.isNumber() || !yObj.isNumber()) {
            fail();
            return;
        }


    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rectclip");
    }
}
