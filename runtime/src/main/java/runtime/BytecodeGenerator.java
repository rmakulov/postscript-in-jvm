package runtime;

import org.objectweb.asm.*;
import psObjects.values.simple.PSBytecode;

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
    private int number;
    private int blockNumber = 1;
    private String name;
    public ArrayList<Integer> operatorIndexes = new ArrayList<Integer>();
    public HashMap<Integer, String> operatorIndexesTest = new HashMap<Integer, String>();

    public static final String operatorIndexesType = "java/util/HashMap";
    public static final String operatorIndexesSignature = "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/String;>;";
    public static final String operatorIndexesName = "operatorIndexes";

    public BytecodeGenerator(int number) {
        operatorIndexesTest.put(1, "one");
        this.number = number;
        name = number + "";

        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, Integer.toString(number), null, "java/lang/Object", null);
        mainMV = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "()Z", null, null);

        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "runtime", "Lruntime/Runtime;", null, null);
        fv.visitEnd();

        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, operatorIndexesName, "L" + operatorIndexesType + ";", operatorIndexesSignature, null);
        fv.visitEnd();

        mainMV.visitCode();

        startClinitMethod(number);
        startMethod();
    }

    private void startClinitMethod(int number) {
        clinitMV = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        clinitMV.visitCode();

        clinitMV.visitMethodInsn(INVOKESTATIC, "runtime/Runtime", "getInstance", "()Lruntime/Runtime;", false);
        clinitMV.visitFieldInsn(PUTSTATIC, Integer.toString(number), "runtime", "Lruntime/Runtime;");

        clinitMV.visitTypeInsn(NEW, operatorIndexesType);
        clinitMV.visitInsn(DUP);
        clinitMV.visitMethodInsn(INVOKESPECIAL, operatorIndexesType, "<init>", "()V", false);
        clinitMV.visitFieldInsn(PUTSTATIC, Integer.toString(number), operatorIndexesName, "L" + operatorIndexesType + ";");
    }

    private void endClinitMethod() {
        clinitMV.visitInsn(RETURN);
        clinitMV.visitMaxs(2, 0);
        clinitMV.visitEnd();
    }


    public void endMethod() {
        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(0, 0);
        blockNumber++;
    }

    public void startMethod() {
//        System.out.println("blockNum"+blockNumber);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run_" + blockNumber, "()Z", null, null);
        mv.visitCode();
    }

    public PSBytecode endBytecode() {

//        mainMV.visitVarInsn(ALOAD, 0);
        endMethod();
        endClinitMethod();
        Label[] l = new Label[blockNumber + 1];
        for (int i = 1; i < blockNumber; i++) {
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
        DynamicClassLoader.instance.putClass(Integer.toString(number), cw.toByteArray());
        cw = null;
        mainMV = null;
        return new PSBytecode(Integer.toString(number));
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

    public int getNumber() {
        return number;
    }

    public String getBytecodeName() {

        return name;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public static void printOperatorIndexes(ArrayList<Integer> arr) {
        System.out.println("printOperatorIndexes_begin ");
        for (Integer integer : arr) {
            System.out.print(integer + ", ");
        }
        System.out.println(" printOperatorIndexes_end");
    }
}
