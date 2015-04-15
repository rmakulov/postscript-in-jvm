package runtime.compiler;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.values.simple.PSBytecode;
import runtime.Context;

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
    public int methodNumber = 0;


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

    public void startCodeGenerator(Context context) {
        bcGen = new BytecodeGenerator(context, lastNumber++);
        bytecodeGenerators.push(bcGen);
        initEverything();
    }

    public void endBytecode() {
        cur = bcGen.endBytecode();
        //bytecodes.put(bcGenManager.getClassNumber(), cur);

        popBytecodeGenerator();
    }

    private void popBytecodeGenerator() {
        bytecodeGenerators.pop();
        if (bytecodeGenerators.empty()) {
            bcGen = null;
            mv = null;
            cw = null;
            bytecodeName = null;
            methodNumber = -1;
        } else {
            bcGen = bytecodeGenerators.peek();
            initEverything();
        }
    }

    private void initEverything() {
        mv = bcGen.getMethodVisitor();
        cw = bcGen.getClassWriter();
        clinitMV = bcGen.getClinitMV();
        bytecodeName = bcGen.getBytecodeName();
        methodNumber = bcGen.getMethodNumber();
    }


    public boolean isSleep() {
        return bytecodeGenerators.empty();
    }

    public void startMethod() {
        bcGen.startMethod();
        mv = bcGen.getMethodVisitor();
        methodNumber = bcGen.getMethodNumber();

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
