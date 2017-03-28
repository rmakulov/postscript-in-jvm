package operators.dictionary;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class OpenChevronOp extends Operator {
    public static final OpenChevronOp instance = new OpenChevronOp();

    protected OpenChevronOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        context.pushToOperandStack(new PSObject(PSMark.OPEN_CHEVRON_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("<<");
    }

    public void compile(Context context) {
        // runtime.Runtime runtime = Runtime.getInstance();
//        OpenChevronOp.instance.interpret();
        MethodVisitor mv = context.bcGenManager.mv;
        String name = context.bcGenManager.bytecodeName;
        mv.visitFieldInsn(GETSTATIC, "operators/dictionary/OpenChevronOp", "instance", "Loperators/dictionary/OpenChevronOp;");
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "operators/dictionary/OpenChevronOp", "interpret", "(Lruntime/Context;)V", false);
    }
}
