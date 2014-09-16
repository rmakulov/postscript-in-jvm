

package operators.pathConstruction;

import operators.AbstractGraphicOperator;
import psObjects.values.simple.PSName;

/**
 * Created by user on 16.03.14.
 */
public class ClipPathOp extends AbstractGraphicOperator {
    public static final ClipPathOp instance = new ClipPathOp();

    protected ClipPathOp() {
        super();
    }

    @Override
    public void interpret() {
        gState.currentPath = gState.clippingPath.clone();
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("clippath");
    }
}
