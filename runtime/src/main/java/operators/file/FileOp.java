package operators.file;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSFile;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by User on 9/2/2015.
 */
public class FileOp extends Operator {
    public static final FileOp instance = new FileOp();

    protected FileOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 2) {
            fail();
            return;
        }
        PSObject access = runtime.popFromOperandStack();
        PSObject filename = runtime.popFromOperandStack();
        if (access.getType() != Type.STRING || filename.getType() != Type.STRING) {
            fail();
            return;
        }
        String name = ((PSString) filename.getValue()).getString();
        String acc = ((PSString) access.getValue()).getString();
        runtime.pushToOperandStack(new PSObject(new PSFile(name, acc)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("file");
    }
}
