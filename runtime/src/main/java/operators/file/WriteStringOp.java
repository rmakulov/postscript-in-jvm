package operators.file;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSFile;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by user on 5/16/15.
 */
public class WriteStringOp extends Operator {
    public static final WriteStringOp instance = new WriteStringOp();


    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) {
            fail();
            return;
        }
        PSObject strObj = context.popFromOperandStack();
        PSObject fileObj = context.popFromOperandStack();
        if (strObj.getType() != Type.STRING || fileObj.getType() != Type.FILE) {
            fail();
            return;
        }
        File file = ((PSFile) fileObj.getValue()).getFile();
        String str = ((PSString) strObj.getValue()).getString();



        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
            out.println(str);
            out.close();
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
//        System.out.println(System.getProperty("user.dir"));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("writestring");
    }
}
