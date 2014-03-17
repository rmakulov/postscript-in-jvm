package operators.array;

import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;

import java.util.ArrayList;

public class CloseSquareBracketOp extends Operator {

    public static final CloseSquareBracketOp instance = new CloseSquareBracketOp();

    protected CloseSquareBracketOp() {
        super();
    }

    @Override
    public void execute() {
        if (runtime.getOperandStackSize() < 2) return;
        PSObject closeBracket = runtime.popFromOperandStack();
        if (!closeBracket.getValue().equals(PSMark.CLOSE_SQUARE_BRACKET)) {
            runtime.pushToOperandStack(closeBracket);
            return;
        }
        PSObject psObject = runtime.popFromOperandStack();
        ArrayList<PSObject> array = new ArrayList<PSObject>();
        while (!PSMark.OPEN_SQUARE_BRACKET.equals(psObject)) {
            array.add(psObject);
            psObject = runtime.popFromOperandStack();
            // todo correct check is not null
            if (psObject.getType() == null)
                return;
        }
        PSArray result = new PSArray(array.size());
        for (int i = 0; i < array.size(); i++) {
            result.setValue(i, array.get(array.size() - i - 1));
        }
        // todo literal, not executable
        runtime.pushToOperandStack(new PSObject(result));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("]");
    }
}
