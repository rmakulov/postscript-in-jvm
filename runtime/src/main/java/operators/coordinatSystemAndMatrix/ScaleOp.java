package operators.coordinatSystemAndMatrix;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

/**
 * Created by user on 15.03.14.
 */
public class ScaleOp extends AbstractGraphicOperator {
    public static final ScaleOp instance = new ScaleOp();

    protected ScaleOp() {
        super();
    }
    @Override
    public void execute() {// s_x s_y scale --
        PSObject oSY = runtime.popFromOperandStack() ;
        PSObject oSX = runtime.popFromOperandStack() ;
        if(oSY == null || oSX == null || !(oSY.isNumber() && oSX.isNumber() )){
            runtime.pushToOperandStack(oSX) ;
            runtime.pushToOperandStack(oSY) ;
            return ;
        }
        double sY = ((PSNumber)(runtime.popFromOperandStack().getValue())).getRealValue() ;
        double sX = ((PSNumber)(runtime.popFromOperandStack().getValue())).getRealValue() ;
        gState.cTM.scale(sX, sY) ;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("scale");
    }
}
