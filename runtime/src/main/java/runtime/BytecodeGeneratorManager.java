package runtime;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.values.simple.PSBytecode;

import java.util.Stack;

/**
 * Created by User on 13/08/2014.
 */
public class BytecodeGeneratorManager implements Opcodes {
    private Stack<BytecodeGenerator> bytecodeGenerators = new Stack<BytecodeGenerator>();
    private boolean sleep = true;
    //private Map<Integer, PSObject> bytecodes = new HashMap<Integer, PSObject>();

    public ClassWriter cw;
    public MethodVisitor mv;
    public MethodVisitor clinitMV;
    public String bytecodeName;
    private BytecodeGenerator bcGen;

    private int lastNumber = 0;
    public int blockNumber = 0;


    public BytecodeGeneratorManager() {
    }

    private PSBytecode cur = null;

    public PSBytecode getCur() {
        if (cur != null) {
            PSBytecode bytecode = cur;
            cur = null;
            return bytecode;
        } else {
            return null;
        }
    }

    public void startCodeGenerator() {
        bcGen = new BytecodeGenerator(lastNumber++);
        bytecodeGenerators.push(bcGen);
        mv = bcGen.getMethodVisitor();
        cw = bcGen.getClassWriter();
        clinitMV = bcGen.getClinitMV();
        bytecodeName = bcGen.getBytecodeName();

    }

    public void endBytecode() {
        cur = bcGen.endBytecode();
        //bytecodes.put(bcGenManager.getNumber(), cur);

        popBytecodeGenerator();
    }

    private void popBytecodeGenerator() {
        bytecodeGenerators.pop();
        if (bytecodeGenerators.empty()) {
            bcGen = null;
            mv = null;
            cw = null;
            bytecodeName = null;
        } else {
            bcGen = bytecodeGenerators.peek();
            mv = bcGen.getMethodVisitor();
            cw = bcGen.getClassWriter();
            clinitMV = bcGen.getClinitMV();
            bytecodeName = bcGen.getBytecodeName();
        }
    }


    public boolean isSleep() {
        return bytecodeGenerators.empty();
    }

    public void startMethod() {
        bcGen.startMethod();
        mv = bcGen.getMethodVisitor();
        blockNumber = bcGen.getBlockNumber();

    }

    public void endMethod() {
        bcGen.endMethod();
    }

    public void incInstrCounter() {
        bcGen.incInstrCounter();
    }

    public boolean lastMethodIsEmpty() {
        return bcGen.lastMethodIsEmpty();
    }
}
