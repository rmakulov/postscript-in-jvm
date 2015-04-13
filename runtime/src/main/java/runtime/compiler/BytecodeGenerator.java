package runtime.compiler;

import org.objectweb.asm.*;
import psObjects.values.simple.PSBytecode;
import runtime.Context;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 10/9/2014.
 */
public class BytecodeGenerator implements Opcodes {

    private ClassWriter cw;
    private MethodVisitor mv;
    private MethodVisitor mainMV;
    private MethodVisitor clinitMV;
    private FieldVisitor fv;
    private int classNumber;
    private int methodNumber = 1;
    private String name;
    public ArrayList<Integer> operatorIndexes = new ArrayList<Integer>();
    public HashMap<Integer, String> operatorIndexesTest = new HashMap<Integer, String>();

    private int instrCounter = 0;
    private boolean consoleOutput = false;
    private Context context;


    public BytecodeGenerator(Context context, int classNumber) {
        this.context = context;
        operatorIndexesTest.put(1, "one");
        this.classNumber = classNumber;
        name = classNumber + "";

        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, Integer.toString(classNumber), null, "java/lang/Object", null);
        mainMV = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "()Z", null, null);

        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "runtime", "Lruntime/Runtime;", null, null);
        fv.visitEnd();
        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "context", "Lruntime/Context;", null, null);
        fv.visitEnd();

        mainMV.visitCode();

        startClinitMethod(classNumber);
        startMethod();
    }

    private void startClinitMethod(int classNumber) {
        String className = Integer.toString(classNumber);
        clinitMV = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        clinitMV.visitCode();

        clinitMV.visitMethodInsn(INVOKESTATIC, "runtime/Runtime", "getInstance", "()Lruntime/Runtime;", false);
        clinitMV.visitFieldInsn(PUTSTATIC, className, "runtime", "Lruntime/Runtime;");

        //defining context field
        clinitMV.visitMethodInsn(INVOKESTATIC, "runtime/Runtime", "getInstance", "()Lruntime/Runtime;", false);
        clinitMV.visitLdcInsn(context.getId());
        clinitMV.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "getContext", "(I)Lruntime/Context;", false);
        clinitMV.visitFieldInsn(PUTSTATIC, className, "context", "Lruntime/Context;");

    }

    private void endClinitMethod() {
        clinitMV.visitInsn(RETURN);
        clinitMV.visitMaxs(0, 0);
        clinitMV.visitEnd();
    }


    public void endMethod() {
        if (consoleOutput) {
            System.out.print("run_" + methodNumber + ": " + instrCounter + "| ");
        }
        instrCounter = 0;
        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(0, 0);
        methodNumber++;

    }

    public void startMethod() {
//        System.out.println("blockNum"+methodNumber);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run_" + methodNumber, "()Z", null, null);
        mv.visitCode();
        instrCounter = 0;
    }

    public PSBytecode endBytecode() {
        //just debug output


        endMethod();
        endClinitMethod();
        //System.out.println("Output bcGen");
        //mainMV.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //mainMV.visitLdcInsn("end bytecode");
        //mainMV.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label[] l = new Label[methodNumber + 1];
        for (int i = 1; i < methodNumber; i++) {
            mainMV.visitMethodInsn(INVOKESTATIC, getBytecodeName(), "run_" + i, "()Z", false);
            l[i] = new Label();
            mainMV.visitJumpInsn(IFNE, l[i]);
            mainMV.visitInsn(ICONST_0);
            mainMV.visitInsn(IRETURN);
            mainMV.visitLabel(l[i]);
        }

        mainMV.visitInsn(ICONST_1);
        mainMV.visitInsn(IRETURN);
        mainMV.visitMaxs(0, 0);
        mainMV.visitEnd();
        DynamicClassLoader.instance.putClass(Integer.toString(classNumber), cw.toByteArray());
        cw = null;
        mainMV = null;
        if (consoleOutput) {
            System.out.print("for " + classNumber + "\n");
        }
        return new PSBytecode(Integer.toString(classNumber));

    }

    public MethodVisitor getMethodVisitor() {
        return mv;
    }

    public MethodVisitor getClinitMV() {
        return clinitMV;
    }

    public ClassWriter getClassWriter() {
        return cw;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public String getBytecodeName() {

        return name;
    }

    public int getMethodNumber() {
        return methodNumber;
    }

    public static void printOperatorIndexes(ArrayList<Integer> arr) {
        System.out.println("printOperatorIndexes_begin ");
        for (Integer integer : arr) {
            System.out.print(integer + ", ");
        }
        System.out.println(" printOperatorIndexes_end");
    }

    public void incInstrCounter() {
        instrCounter++;
    }

    public boolean lastMethodIsEmpty() {
        return instrCounter == 0;
    }


}


