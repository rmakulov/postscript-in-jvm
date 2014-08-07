package runtime;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class Compiler implements Opcodes {

    private ClassWriter cw;
    private MethodVisitor mv;

    public Compiler(String fileName) {
        cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, fileName, null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "(Lruntime/Runtime;)V", null, null);
        mv.visitCode();
    }

    private void close() {
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

}
