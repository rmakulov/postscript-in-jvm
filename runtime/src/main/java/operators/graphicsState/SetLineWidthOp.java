package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

/**
 * Created by user on 15.03.14.
 */
public class SetLineWidthOp extends AbstractGraphicOperator{
    public static final SetLineWidthOp instance = new SetLineWidthOp();

    protected SetLineWidthOp() {
        super();
    }

    @Override
    public void execute() {// num setlinewidth -
        PSObject oNum = runtime.popFromOperandStack() ;
        if(oNum == null || !(oNum.isNumber())){
            runtime.pushToOperandStack(oNum) ;
            return ;
        }
        double width = ((PSNumber) oNum.getValue()).getRealValue() ;
        gState.lineWidth = width ;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("setlinewidth");
    }
}
