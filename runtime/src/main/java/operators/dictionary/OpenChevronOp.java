package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Runtime;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class OpenChevronOp extends Operator {
    public static final OpenChevronOp instance = new OpenChevronOp();

    protected OpenChevronOp() {
        super();
    }

    @Override
    public void interpret() {
        runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CHEVRON_BRACKET));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("<<");
    }

    public static void compile() {
        runtime.Runtime runtime = Runtime.getInstance();
//        OpenChevronOp.instance.interpret();
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, "operators/dictionary/OpenChevronOp", "instance", "Loperators/dictionary/OpenChevronOp;");
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "operators/dictionary/OpenChevronOp", "interpret", "()V", false);
    }
}
