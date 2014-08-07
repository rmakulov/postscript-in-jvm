package runtime;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.simple.PSBytecode;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Created by user on 29.07.14.
 */
public class BytecodeGenerator implements Opcodes {

    private boolean sleep = false;

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
    }

    public BytecodeGenerator() {
        argsCount = 0;
        args = new ArrayDeque<Double>();
        cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, Integer.toString(i), null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "(Lruntime/Runtime;)V", null, null);
        mv.visitCode();
    }

    private PSBytecode cur = null;

    public PSObject getCur() {
        if (cur != null) {
            PSObject psObject = new PSObject(cur, Attribute.TreatAs.EXECUTABLE);
            cur = null;
            return psObject;
        } else {
            return null;
        }
    }

    public ClassWriter cw;
    public MethodVisitor mv;
    public int argsCount;
    public Queue<Double> args;
    private Map<String, Integer> generatedCode = new HashMap<String, Integer>();
    private int i = 0;
    private StringBuilder curPattern = new StringBuilder();

    public void appendPattern(String s) {
        curPattern.append(s).append(" ");
    }

    public StringBuilder getCurPattern() {
        return curPattern;
    }

    public void resetCodeGenerator() {
        if (sleep) return;
        Runtime runtime = Runtime.getInstance();

        String cg = getCurPattern().toString();
//         Check if we collect smth, have some args and smth contains not only from numbers.
        if (args.size() > 0 && cg.split(" ").length > args.size()) {
            runFragment(cg);
        } else {
            while (args.size() > 0) {

                double tmp = args.remove();
                if ((tmp == Math.floor(tmp)) && !Double.isInfinite(tmp)) {
                    runtime.pushToOperandStack(new PSObject(new PSInteger((int) (tmp))));
                } else {
                    runtime.pushToOperandStack(new PSObject(new PSReal(tmp)));
                }
            }
        }


        curPattern.delete(0, curPattern.capacity());
        argsCount = 0;


        args = new ArrayDeque<Double>();
        cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, Integer.toString(i), null, "java/lang/Object", null);
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "run", "(Lruntime/Runtime;)V", null, null);
        mv.visitCode();
        sleep = true;
    }

    private void runFragment(String str) {
        if (!generatedCode.containsKey(str)) {
            // if argsCount equals 0, visitMaxs doesn't have to be 0
            final int Maxs = Math.max((argsCount + 1) * 5, 7);
            // push into args unused nubers from frame
            while (argsCount > 0) {
                mv.visitIntInsn(DSTORE, 5);
                mv.visitIntInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "bcGen", "Lruntime/BytecodeGenerator;");
                mv.visitFieldInsn(GETFIELD, "runtime/BytecodeGenerator", "args", "Ljava/util/Queue;");
                mv.visitTypeInsn(CHECKCAST, "java/util/ArrayDeque");
                mv.visitIntInsn(DLOAD, 5);
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayDeque", "addFirst", "(Ljava/lang/Object;)V", false);
                argsCount--;

            }

            mv.visitInsn(RETURN);
            mv.visitMaxs(Maxs, Maxs);
            mv.visitEnd();

            DynamicClassLoader.instance.putClass(Integer.toString(i), cw.toByteArray());

            generatedCode.put(str, i);
            System.out.println("Got it: " + i + " " + str);
            i++;
        }

        int classNumber = generatedCode.get(str);
        cur = new PSBytecode(Integer.toString(classNumber), args);
    }
}
