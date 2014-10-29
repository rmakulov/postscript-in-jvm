package psObjects.values.simple;

import com.sun.istack.internal.NotNull;
import operators.array.CloseSquareBracketOp;
import operators.common.AloadOp;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.PSArray;

/**
 * Created by user on 29.10.14.
 */
public class BytecodeProc extends SimpleValue {
    private PSBytecode origin;
    // it is NOT like bind in postscript, it substitutes all names (not only operators) from definitions
    private PSBytecode bound; // third form of bind  in English lol =)
    private int version; // version of dictionary stack, when bound
    private int boundCount = 0; // times of binding
    private static final int MAX_BOUND_COUNT = 0;

    public BytecodeProc(@NotNull String originName, String boundName, int versionNumber, int boundCountValue) {
        origin = new PSBytecode(originName);
        if (boundName == null) {
            bound = null;
            version = 0;
            boundCount = 0;
        } else {
            bound = new PSBytecode(boundName);
            version = versionNumber;
            boundCount = boundCountValue;
        }

    }

    public BytecodeProc(PSBytecode originBytecode) {
        origin = originBytecode;

    }

    @Override
    public boolean interpret(PSObject obj) {
        if (bound != null && version == runtime.getDictStackVersion()) {
            System.out.println("interpreting bound");
            return bound.interpret(null);
        } else {
            if (boundCount < MAX_BOUND_COUNT) {
                bindIfCan();
                return bound.interpret(null);
            } else {
                return origin.interpret(null);
            }
        }
    }

    private void bindIfCan() {
        bound = deepBind();
        version = runtime.getDictStackVersion();
    }

    private PSBytecode deepBind() {
        runtime.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
        runtime.pushToOperandStack(new PSObject(this));
        AloadOp.instance.interpret();
        runtime.popFromOperandStack();
        CloseSquareBracketOp.instance.interpret();
        PSObject[] arr = ((PSArray) runtime.popFromOperandStack().getValue()).getArray();
        runtime.bcGenManager.startCodeGenerator();
        for (PSObject o : arr) {
//            if (o.getType() == Type.NAME && !(o.isBytecode()) && o.treatAs() == EXECUTABLE) {
//                PSObject realValue = runtime.findValue(o);
//                while (realValue.getType() == Type.NAME && !(realValue.isBytecode()) && realValue.treatAs() == EXECUTABLE) {
//                    realValue = runtime.findValue(realValue);
//                }
//                String name = runtime.bcGenManager.bytecodeName;
//                MethodVisitor mv = runtime.bcGenManager.mv;
//                mv.visitInsn(ICONST_0);
//                mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "execute", "(I)Z", false);
//            } else {
//                o.deepCompile();
//            }
            o.deepCompile();
        }
        runtime.bcGenManager.endBytecode();
        boundCount++;
        return runtime.bcGenManager.getCur();
    }

    @Override
    public Type determineType() {
        return Type.BYTECODE_PROC;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BytecodeProc)) return false;
        return this == o;
    }

    @Override
    public String toStringView() {
        return "-BytecodeProc-";
    }

    @Override
    public Value getValue() {
        return origin;
    }

    @Override
    public void compile(PSObject obj) {
        if (bound != null && version == runtime.getDictStackVersion()) {
            System.out.println("compiling bound");
            bound.compile(null);
        } else {
            if (boundCount < MAX_BOUND_COUNT) {
                bindIfCan();
                bound.compile(null);
            } else {
                origin.compile(null);
            }
            MethodVisitor mv = runtime.bcGenManager.mv;
            String name = runtime.bcGenManager.bytecodeName;
            mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "popFromOperandStack", "()LpsObjects/PSObject;", false);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/simple/PSBytecode", "interpret", "(LpsObjects/PSObject;)V", false);

            Label l8 = new Label();
            mv.visitJumpInsn(IFNE, l8);
            mv.visitInsn(ICONST_0);
            mv.visitInsn(IRETURN);
            mv.visitLabel(l8);
        }
//        //runtime.pushToOperandStack(new PSObject(new BytecodeProc("a","b",2,3)));
//        String name = runtime.bcGenManager.bytecodeName;
//        MethodVisitor mv = runtime.bcGenManager.mv;
//        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
//        mv.visitTypeInsn(NEW, "psObjects/PSObject");
//        mv.visitInsn(DUP);
//        mv.visitTypeInsn(NEW, "psObjects/values/simple/BytecodeProc");
//        mv.visitInsn(DUP);
//        mv.visitLdcInsn(origin.strValue);
//        if (bound == null) {
//            mv.visitInsn(ACONST_NULL);
//        } else {
//            mv.visitLdcInsn(bound.strValue);
//        }
//        mv.visitLdcInsn(version);
//        mv.visitLdcInsn(boundCount);
//        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/BytecodeProc", "<init>", "(Ljava/lang/String;Ljava/lang/String;II)V", false);
//        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
////        mv.visitInsn(ICONST_0);
////        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }

    @Override
    public String toString() {
        return "BytecodeProc{" +
                "origin=" + origin.strValue +
                ", bound=" + (bound == null ? null : bound.strValue) +
                ", version=" + version +
                ", boundCount=" + boundCount +
                '}';
    }
}
