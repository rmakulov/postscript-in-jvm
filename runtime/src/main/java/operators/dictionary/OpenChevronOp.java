package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class OpenChevronOp extends Operator {
    public static final OpenChevronOp instance = new OpenChevronOp();

    protected OpenChevronOp() {
        super();
    }

    @Override
    public void execute() {
        runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CHEVRON_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("<<");
    }
}
