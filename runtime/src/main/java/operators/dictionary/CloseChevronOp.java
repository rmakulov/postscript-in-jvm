package operators.dictionary;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Runtime;

import java.util.ArrayList;

/**
 * Created by Дмитрий on 17.03.14.
 */
public class CloseChevronOp extends Operator {
    public static final CloseChevronOp instance = new CloseChevronOp();

    protected CloseChevronOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        ArrayList<PSObject> entries = new ArrayList<PSObject>();
        PSObject obj2, obj1;
        while ((obj2 = runtime.popFromOperandStack()).getValue() != PSMark.OPEN_CHEVRON_BRACKET &&
                (obj1 = runtime.popFromOperandStack()).getValue() != PSMark.OPEN_CHEVRON_BRACKET) {
            entries.add(obj1);
            entries.add(obj2);
        }
        PSDictionary dict = new PSDictionary(entries);
        runtime.pushToOperandStack(new PSObject(dict));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName(">>");
    }


    public void compile() {
        runtime.Runtime runtime = Runtime.getInstance();
//        CloseChevronOp.instance.interpret();
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, "operators/dictionary/CloseChevronOp", "instance", "Loperators/dictionary/CloseChevronOp;");
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "operators/dictionary/CloseChevronOp", "interpret", "()V", false);
    }
}
