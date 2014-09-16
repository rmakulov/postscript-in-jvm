package runtime;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.values.simple.PSBytecode;

/**
 * Created by User on 10/9/2014.
 */
public class BytecodeGenerator implements Opcodes{
    private ClassWriter cw;
    private MethodVisitor mv;
    private int number;

    public BytecodeGenerator(int number) {
        this.number = number;
        cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, Integer.toString(number), null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "(Lruntime/Runtime;)Z", null, null);
        mv.visitCode();
    }

    public PSBytecode endBytecode() {
        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(10, 10);
        mv.visitEnd();
        DynamicClassLoader.instance.putClass(Integer.toString(number), cw.toByteArray());
        cw = null;
        mv = null;
        return new PSBytecode(Integer.toString(number));
    }

    public MethodVisitor getMethodVisitor() {
        return mv;
    }


    public ClassWriter getClassWriter() {
        return cw;
    }

    public int getNumber() {
        return number;
    }
}
