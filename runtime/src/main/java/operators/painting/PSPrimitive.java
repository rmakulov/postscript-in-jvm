package operators.painting;

import operators.AbstractGraphicOperator;
import procedures.ArrayProcedure;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.paths.PSPath;

/**
 * Created by rustam on 3/29/15.
 */
public class PSPrimitive extends AbstractGraphicOperator {

    protected PSPath psPath;
    protected ArrayProcedure procedure;
    protected Context context;

    protected PSPrimitive() {
        super();
    }

    @Override
    public void interpret(Context context) {
        runtime.addPrimitive(this);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("psprimitive");
    }

    public void paint() {

    }

    public void setPSPath(PSPath psPath) {
        this.psPath = psPath;
    }

    public void setProcedure(ArrayProcedure procedure) {
        this.procedure = procedure;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
