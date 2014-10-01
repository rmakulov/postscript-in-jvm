package runtime;

import org.objectweb.asm.*;
import psObjects.values.simple.PSBytecode;

/**
 * Created by User on 10/9/2014.
 */
public class BytecodeGenerator implements Opcodes {
    private ClassWriter cw;
    private MethodVisitor mv;
    private MethodVisitor mainMV;
    private FieldVisitor fv;
    private int number;
    private int blockNumber = 1;
    private static Runtime runtime = Runtime.getInstance();
    private String name;


    public BytecodeGenerator(int number) {
        this.number = number;
        name = number + "";
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, Integer.toString(number), null, "java/lang/Object", null);
        //cw.visitField(ACC_PRIVATE, "runtime", "Lruntime/Runtime;", null, null);
        mainMV = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "()Z", null, null);

        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "runtime", "Lruntime/Runtime;", null, null);
        fv.visitEnd();

        mainMV.visitCode();

        {
            MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();

            mv.visitMethodInsn(INVOKESTATIC, "runtime/Runtime", "getInstance", "()Lruntime/Runtime;", false);
            mv.visitFieldInsn(PUTSTATIC, Integer.toString(number), "runtime", "Lruntime/Runtime;");

            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 0);
            mv.visitEnd();
        }
        startMethod();
    }


    public void endMethod() {
        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(0, 0);
        blockNumber++;
    }

    public void startMethod() {
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run_" + blockNumber, "()Z", null, null);
        mv.visitCode();
    }

    public PSBytecode endBytecode() {
//        mainMV.visitVarInsn(ALOAD, 0);
        endMethod();
        Label[] l = new Label[blockNumber + 1];
//        mainMV.visitMethodInsn(INVOKESTATIC, getBytecodeName(), "run_"+1, "()Z", false);
//        mainMV.visitMethodInsn(INVOKESTATIC, getBytecodeName(), "run_"+2, "()Z", false);

        for (int i = 1; i < blockNumber; i++) {
            mainMV.visitMethodInsn(INVOKESTATIC, getBytecodeName(), "run_" + i, "()Z", false);
//            mainMV.visitInsn(POP);
            l[i] = new Label();
//            Label label = new Label();
            mainMV.visitJumpInsn(IFNE, l[i]);
//            mainMV.visitJumpInsn(IFNE, label);
            mainMV.visitInsn(ICONST_0);
            mainMV.visitInsn(IRETURN);
//            mainMV.visitLabel(label);
//            mainMV.visitLineNumber(800,label);
//            mv.visitFrame(F_FULL, 0, null, 0, null);
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
//        return mainMV ;
        return mv;
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
}
