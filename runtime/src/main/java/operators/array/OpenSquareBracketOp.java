package operators.array;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Context;

public class OpenSquareBracketOp extends Operator {

    public static final OpenSquareBracketOp instance = new OpenSquareBracketOp();

    protected OpenSquareBracketOp() {
        super();
    }

    public void compile(Context context) {
        //runtime.Runtime runtime = Runtime.getInstance();
//        OpenSquareBracketOp.instance.interpret();
        MethodVisitor mv = context.bcGenManager.mv;
        String name = context.bcGenManager.bytecodeName;
        mv.visitFieldInsn(GETSTATIC, "operators/array/OpenSquareBracketOp", "instance", "Loperators/array/OpenSquareBracketOp;");
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "operators/array/OpenSquareBracketOp", "interpret", "(Lruntime/Context;)V", false);
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("[");
    }
}
