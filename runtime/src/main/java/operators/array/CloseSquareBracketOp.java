package operators.array;

import org.objectweb.asm.MethodVisitor;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.Runtime;

import java.util.ArrayList;

public class CloseSquareBracketOp extends Operator {

    public static final CloseSquareBracketOp instance = new CloseSquareBracketOp();

    protected CloseSquareBracketOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject psObject = context.popFromOperandStack();
        ArrayList<PSObject> array = new ArrayList<PSObject>();
        while (!PSMark.OPEN_SQUARE_BRACKET.equals(psObject.getValue())) {
            array.add(psObject);
            psObject = context.popFromOperandStack();
            // todo correct check is not null
            if (psObject == null)
                return;
        }
        PSArray result = new PSArray(array.size());
        for (int i = 0; i < array.size(); i++) {
            result = result.setValue(i, array.get(array.size() - i - 1));
        }
        context.pushToOperandStack(new PSObject(result, Attribute.TreatAs.LITERAL));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("]");
    }

    public void compile(Context context) {
        runtime.Runtime runtime = Runtime.getInstance();
//        CloseSquareBracketOp.instance.interpret();
        MethodVisitor mv = context.bcGenManager.mv;
        String name = context.bcGenManager.bytecodeName;
        mv.visitFieldInsn(GETSTATIC, "operators/array/CloseSquareBracketOp", "instance", "Loperators/array/CloseSquareBracketOp;");
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "operators/array/CloseSquareBracketOp", "interpret", "(Lruntime/Context;)V", false);
    }
}
