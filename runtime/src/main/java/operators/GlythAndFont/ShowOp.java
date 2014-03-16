package operators.GlythAndFont;

import operators.AbstractGraphicOperator;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSName;

/**
 * Created by user on 16.03.14.
 */
public class ShowOp extends AbstractGraphicOperator {
    @Override
    public void execute() { // string show --
        PSObject oStr = runtime.popFromOperandStack() ;
        if(oStr == null && !(oStr.getType() == Type.STRING)){
            runtime.pushToOperandStack(oStr) ;
            return ;
        }
        String str = ((PSString) oStr.getValue()).getString() ;
        //frame.print string or something such
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("show");
    }
}
