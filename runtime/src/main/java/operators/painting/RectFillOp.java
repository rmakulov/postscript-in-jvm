package operators.painting;

import operators.AbstractGraphicOperator;
import operators.arithmetic.unary.NegOp;
import operators.graphicsState.GRestoreOp;
import operators.graphicsState.GSaveOp;
import operators.pathConstruction.ClosePathOp;
import operators.pathConstruction.MoveToOp;
import operators.pathConstruction.NewPathOp;
import operators.pathConstruction.RLineToOp;
import procedures.ArrayProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by Дмитрий on 01.04.14.
 */
public class RectFillOp extends AbstractGraphicOperator {
    public static final RectFillOp instance = new RectFillOp();

    protected RectFillOp() {
        super();
    }

    @Override
    public void interpret(Context context) { // Fill current path with current color
        if (context.getOperandStackSize() < 1) return;
        PSObject o = context.peekFromOperandStack();
        if (o.isNumber()) {
            if (context.getOperandStackSize() < 4) return;
            PSObject hObj = context.popFromOperandStack();
            PSObject wObj = context.popFromOperandStack();
            PSObject yObj = context.popFromOperandStack();
            PSObject xObj = context.popFromOperandStack();
            if (!hObj.isNumber() || !wObj.isNumber() || !yObj.isNumber() || !xObj.isNumber()) {
                context.pushToOperandStack(xObj);
                context.pushToOperandStack(yObj);
                context.pushToOperandStack(wObj);
                context.pushToOperandStack(hObj);
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
                    new PSObject(FillOp.instance),
                    new PSObject(GRestoreOp.instance)
            });
            context.pushToCallStack(new ArrayProcedure(context, "RectFill", new PSObject(procArr, Attribute.TreatAs.EXECUTABLE)));
        } else {
            //todo maybe another arguments
        }

    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rectfill");
    }
}
