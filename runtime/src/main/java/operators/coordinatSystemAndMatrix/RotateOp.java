package operators.coordinatSystemAndMatrix;

import com.sun.javafx.scene.layout.region.Margins;
import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSNumber;

/**
 * Created by user on 15.03.14.
 */
public class RotateOp extends AbstractGraphicOperator{
    @Override
    public void execute() {
        PSObject oAngle = runtime.popFromOperandStack() ;
        if(oAngle == null || !oAngle.isNumber()){
            runtime.pushToOperandStack(oAngle) ;
            return ;
        }
        double angle = ((PSNumber)oAngle.getValue()).getRealValue() ;
        double newAngle = angle * Math.PI / 180 ;
        gState.cTM.rotate(newAngle) ;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("rotate");
    }
}
