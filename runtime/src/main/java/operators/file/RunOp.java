package operators.file;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSFile;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by User on 9/2/2015.
 */
public class RunOp extends Operator {
    public static final RunOp instance = new RunOp();

    protected RunOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) {
            fail();
            return;
        }
        PSObject fileObject = runtime.popFromOperandStack();
        if (fileObject.getType() != Type.FILE) {
            fail();
            return;
        }
        PSFile psFile = (PSFile) fileObject.getValue();
        psFile.interpret(fileObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("run");
    }
}
