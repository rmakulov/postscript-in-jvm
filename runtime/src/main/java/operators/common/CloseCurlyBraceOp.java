package operators.common;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

import java.util.ArrayList;

public class CloseCurlyBraceOp extends Operator {
    public static final CloseCurlyBraceOp instance = new CloseCurlyBraceOp();

    protected CloseCurlyBraceOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject closeBracket = runtime.popFromOperandStack();
        if (!closeBracket.getValue().equals(PSMark.CLOSE_CURLY_BRACE)) {
            runtime.pushToOperandStack(closeBracket);
            return;
        }
        PSObject psObject = runtime.popFromOperandStack();
        ArrayList<PSObject> array = new ArrayList<PSObject>();
        while (!PSMark.OPEN_CURLY_BRACE.equals(psObject.getValue())) {
            array.add(psObject);
            psObject = runtime.popFromOperandStack();
            // todo correct check is not null
            if (psObject == null)
                return;
        }
        PSArray result = new PSArray(array.size());
        for (int i = 0; i < array.size(); i++) {
            result = result.setValue(i, array.get(array.size() - i - 1));
        }
        // todo literal, not executable
        runtime.pushToOperandStack(new PSObject(result, Attribute.TreatAs.EXECUTABLE));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("}");
    }
}
