package operators.file;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSFile;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

import java.io.*;

/**
 * Created by user on 5/19/15.
 */
public class CloseFileOp extends Operator {
    public static final CloseFileOp instance = new CloseFileOp();


    protected CloseFileOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) {
            fail();
            return;
        }
        PSObject fileObj = context.popFromOperandStack();
        if (fileObj.getType() != Type.FILE) {
            context.pushToOperandStack(fileObj);
            fail();
            return;
        }
        File file = ((PSFile) fileObj.getValue()).getFile();
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            out.close();
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        //System.out.println(System.getProperty("user.dir"));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("closefile");
    }
}
