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
import runtime.Context;

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
    public boolean interpret(Context context, PSObject obj) {
        if (bound != null && version == context.getDictStackVersion()) {
            System.out.println("interpreting bound");
            return bound.interpret(context, null);
        } else {
            if (boundCount < MAX_BOUND_COUNT) {
                bindIfCan(context);
                return bound.interpret(context, null);
            } else {
                return origin.interpret(context, null);
            }
        }
    }


    private void bindIfCan(Context context) {
        bound = deepBind(context);
        version = context.getDictStackVersion();
    }

    private PSBytecode deepBind(Context context) {
        context.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
        context.pushToOperandStack(new PSObject(this));
        AloadOp.instance.interpret(context);
        context.popFromOperandStack();
        CloseSquareBracketOp.instance.interpret(context);
        PSObject[] arr = ((PSArray) context.popFromOperandStack().getValue()).getArray();
        context.bcGenManager.startCodeGenerator(context);
//        for (PSObject o : arr) {
//            if (o.getType() == Type.NAME && !(o.isBytecode()) && o.treatAs() == EXECUTABLE) {
//                PSObject realValue = context.findValue(o);
//                while (realValue.getType() == Type.NAME && !(realValue.isBytecode()) && realValue.treatAs() == EXECUTABLE) {
//                    realValue = context.findValue(realValue);
//                }
//                String name = context.bcGenManager.bytecodeName;
//                MethodVisitor mv = context.bcGenManager.mv;
//                mv.visitInsn(ICONST_0);
//                mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "execute", "(I)Z", false);
//            } else {
//                o.deepCompile();
//            }
//            o.deepCompile();
// }
        context.bcGenManager.endBytecode();
        boundCount++;
        return context.bcGenManager.getCur();
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
    public String toStringView(PSObject object) {
        return "-BytecodeProc-";
    }

    @Override
    public Value getValue() {
        return origin;
    }

    @Override
    public void compile(Context context, PSObject obj) {
        if (bound != null && version == context.getDictStackVersion()) {
            System.out.println("compiling bound");
            bound.compile(context, null);
        } else {
            if (boundCount < MAX_BOUND_COUNT) {
                bindIfCan(context);
                bound.compile(context, null);
            } else {
                origin.compile(context, null);
            }
            MethodVisitor mv = context.bcGenManager.mv;
            String name = context.bcGenManager.bytecodeName;
            mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "popFromOperandStack", "()LpsObjects/PSObject;", false);
            mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/simple/PSBytecode", "interpret", "(Lruntime/ContextLpsObjects/PSObject;)V", false);

            //todo maybe checkExitCompile
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
