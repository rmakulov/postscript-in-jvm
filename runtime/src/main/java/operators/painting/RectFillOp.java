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

/**
 * Created by Дмитрий on 01.04.14.
 */
public class RectFillOp extends AbstractGraphicOperator {
    public static final RectFillOp instance = new RectFillOp();

    protected RectFillOp() {
        super();
    }

    @Override
    public void execute() { // Fill current path with current color
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o = runtime.peekFromOperandStack();
        if (o.isNumber()) {
            if (runtime.getOperandStackSize() < 4) return;
            PSObject hObj = runtime.popFromOperandStack();
            PSObject wObj = runtime.popFromOperandStack();
            PSObject yObj = runtime.popFromOperandStack();
            PSObject xObj = runtime.popFromOperandStack();
            if (!hObj.isNumber() || !wObj.isNumber() || !yObj.isNumber() || !xObj.isNumber()) {
                runtime.pushToOperandStack(xObj);
                runtime.pushToOperandStack(yObj);
                runtime.pushToOperandStack(wObj);
                runtime.pushToOperandStack(hObj);
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
            runtime.pushToCallStack(new ArrayProcedure("RectFill", new PSObject(procArr, Attribute.TreatAs.EXECUTABLE)));
        } else {
            //todo maybe another arguments
        }

    }


    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rectfill");
    }
}
