package operators.common;

import javafx.util.Pair;
import operators.control.ExecOp;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.avl.AvlTree;

public class ForAllOp extends Operator {

    public static final ForAllOp instance = new ForAllOp();

    protected ForAllOp() {
        super();
    }

    @Override
    public void execute() {

        PSObject proc = runtime.popFromOperandStack();
        if (proc == null)
            return;

        PSObject elems = runtime.popFromOperandStack();
        if (elems == null || !proc.isProc()) {
            runtime.pushToOperandStack(proc);
            return;
        }
        switch (elems.getType()) {
            case ARRAY:
            case PACKEDARRAY:
                PSObject[] array = ((PSArray) elems.getValue()).getArray();
                for (PSObject psObject : array) {
                    runtime.pushToOperandStack(psObject);
                    ExecOp.instance.execute();
                }
                break;
            case DICTIONARY:
                AvlTree tree = ((PSDictionary) elems.getValue()).getTree();
                for (Pair<PSObject, PSObject> pair : tree) {
                    runtime.pushToOperandStack(pair.getKey());
                    runtime.pushToOperandStack(pair.getValue());
                    ExecOp.instance.execute();
                }
                break;
            case STRING:
                String s = ((PSString) elems.getValue()).getString();
                for (int i = 0; i < s.length(); i++) {
                    runtime.pushToOperandStack(new PSObject(new PSString(s.charAt(i) + "")));
                    ExecOp.instance.execute();
                }
                break;
            default:
                runtime.pushToOperandStack(elems);
                runtime.pushToOperandStack(proc);
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("forall");
    }
}
