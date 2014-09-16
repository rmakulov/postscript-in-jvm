package operators.array;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Runtime;

public class OpenSquareBracketOp extends Operator {

    public static final OpenSquareBracketOp instance = new OpenSquareBracketOp();

    protected OpenSquareBracketOp() {
        super();
    }

    public static void compile() {
        runtime.Runtime runtime = Runtime.getInstance();
//        OpenSquareBracketOp.instance.interpret();
        runtime.bcGen.mv.visitFieldInsn(GETSTATIC, "operators/array/OpenSquareBracketOp", "instance", "Loperators/array/OpenSquareBracketOp;");
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "operators/array/OpenSquareBracketOp", "interpret", "()V", false);
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("[");
    }
}
