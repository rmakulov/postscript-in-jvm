package operators.file;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSFile;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
//        System.out.println(System.getProperty("user.dir"));
        String acc = ((PSString) access.getValue()).getString();

        String name = ((PSString) filename.getValue()).getString();
        Path path = Paths.get(name);
        if (!path.isAbsolute()) {
            name = System.getProperty("user.dir").concat(File.separator).concat(name);
        }
        runtime.pushToOperandStack(new PSObject(new PSFile(name, acc)));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("file");
    }
}
