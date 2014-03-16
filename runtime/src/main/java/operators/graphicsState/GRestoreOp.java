package operators.graphicsState;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;
import runtime.graphics.save.GSave;

/**
 * Created by user on 16.03.14.
 */
public class GRestoreOp extends AbstractGraphicOperator {

    @Override
    public void execute() {
        GSave gsave = runtime.peekFromGraphicStack() ;
        if(gsave.isMadeByGSaveOp()){
            runtime.popFromGraphicStack() ;
        }
        gsave.setSnapshot();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("grestore");
    }
}
