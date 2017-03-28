package operators.dictionary;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import runtime.Context;
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
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        ArrayList<PSObject> entries = new ArrayList<PSObject>();
        PSObject obj2, obj1;
        while ((obj2 = context.popFromOperandStack()).getValue() != PSMark.OPEN_CHEVRON_BRACKET &&
                (obj1 = context.popFromOperandStack()).getValue() != PSMark.OPEN_CHEVRON_BRACKET) {
            entries.add(obj1);
            entries.add(obj2);
        }
        PSDictionary dict = new PSDictionary(entries);
        context.pushToOperandStack(new PSObject(dict));
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName(">>");
    }


    public void compile(Context context) {
        runtime.Runtime runtime = Runtime.getInstance();
//        CloseChevronOp.instance.interpret();
        MethodVisitor mv = context.bcGenManager.mv;
        String name = context.bcGenManager.bytecodeName;
        mv.visitFieldInsn(GETSTATIC, "operators/dictionary/CloseChevronOp", "instance", "Loperators/dictionary/CloseChevronOp;");
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "operators/dictionary/CloseChevronOp", "interpret", "(Lruntime/Context;)V", false);
    }
}
