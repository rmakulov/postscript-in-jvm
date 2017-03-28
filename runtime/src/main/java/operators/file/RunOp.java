package operators.file;


import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSFile;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by User on 9/2/2015.
 */
public class RunOp extends Operator {
    public static final RunOp instance = new RunOp();

    protected RunOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) {
            fail();
            return;
        }
        PSObject fileObject = context.popFromOperandStack();
        if (fileObject.getType() != Type.FILE) {
            fail();
            return;
        }
        PSFile psFile = (PSFile) fileObject.getValue();
        psFile.interpret(context, fileObject);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("run");
    }
}
