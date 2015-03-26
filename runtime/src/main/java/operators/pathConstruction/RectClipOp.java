package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import operators.arithmetic.unary.NegOp;
import operators.graphicsState.GRestoreOp;
import operators.graphicsState.GSaveOp;
import procedures.ArrayProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by user on 26.09.14.
 */
public class RectClipOp extends AbstractGraphicOperator {
    public static final Operator instance = new RectClipOp();

    @Override
    public void interpret(Context context) {
        //todo
        PSObject psObj = context.popFromOperandStack();
        if (psObj == null) {
            fail();
            return;
        }
        switch (psObj.getType()) {
            case ARRAY:
            case STRING:
                fail(); //todo implement numarray and numstring cases
                break;
            case INTEGER:
            case REAL:
                fourArguments(context, psObj);
                break;
            default:

        }

    }

    private void fourArguments(Context context, PSObject hObj) {
        PSObject wObj = context.popFromOperandStack();
        PSObject yObj = context.popFromOperandStack();
        PSObject xObj = context.popFromOperandStack();
        if (wObj == null || xObj == null || yObj == null ||
                !hObj.isNumber() || !wObj.isNumber() || !xObj.isNumber() || !yObj.isNumber()) {
            fail();
            return;
        }
        PSArray procArr = new PSArray(new PSObject[]{
                new PSObject(GSaveOp.instance),
                new PSObject(NewPathOp.instance),
                xObj, yObj, new PSObject(MoveToOp.instance),
                wObj, new PSObject(new PSInteger(0)), new PSObject(RLineToOp.instance),
                new PSObject(new PSInteger(0)), hObj, new PSObject(RLineToOp.instance),
                wObj, new PSObject(NegOp.instance), new PSObject(new PSInteger(0)), new PSObject(RLineToOp.instance),
                new PSObject(ClosePathOp.instance),
                new PSObject(ClipOp.instance),
                new PSObject(GRestoreOp.instance)
        });
        ArrayProcedure rectClip = new ArrayProcedure(context, "RectClip", new PSObject(procArr, Attribute.TreatAs.EXECUTABLE));
        if (!runtime.isCompiling) {
            context.pushToCallStack(rectClip);
        } else {
            while (rectClip.hasNext()) {
                rectClip.execNext();
            }
        }

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rectclip");
    }
}
