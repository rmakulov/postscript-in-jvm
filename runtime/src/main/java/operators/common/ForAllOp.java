package operators.common;

import procedures.ForAllProcedure;
import psObjects.PSObject;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.avl.AvlTree;
import runtime.avl.Pair;

public class ForAllOp extends Operator {

    public static final ForAllOp instance = new ForAllOp();

    protected ForAllOp() {
        super();
    }

    @Override
    public void interpret() {

        PSObject proc = runtime.popFromOperandStack();
        PSObject elems = runtime.popFromOperandStack();
        if (wrongArgs(proc, elems)) return;

        PSObject[] beforeArray = getBeforeArray(elems);
        if (beforeArray == null) {
            fail();
            return;
        }
        runtime.pushToCallStack(new ForAllProcedure(beforeArray, proc));
        if (runtime.isCompiling) {
            for (PSObject psObj : beforeArray) {
                runtime.pushToOperandStack(psObj);
                if (!proc.execute(0)) break;
            }
            runtime.popFromCallStack();
        }/* else {
            runtime.pushToCallStack(new ForAllProcedure(beforeArray, proc));
        }*/
    }

    private PSObject[] getBeforeArray(PSObject elems) {
        PSObject[] beforeArray;
        switch (elems.getType()) {
            case ARRAY:
            case PACKEDARRAY:
                beforeArray = ((PSArray) elems.getValue()).getArray();
                break;
            case DICTIONARY:
                AvlTree tree = ((PSDictionary) elems.getValue()).getTree();
                beforeArray = new PSObject[tree.getCount() * 2];
                int i = 0;
                for (Pair<PSObject, PSObject> pair : tree) {
                    beforeArray[i] = pair.getKey();
                    beforeArray[i + 1] = pair.getValue();
                    i += 2;
                }
                break;
            case STRING:
                String s = ((PSString) elems.getValue()).getString();
                beforeArray = new PSObject[s.length()];
                for (int j = 0; j < s.length(); j++) {
                    beforeArray[j] = new PSObject(new PSString(s.charAt(j) + ""));
                }
                break;
            default:
                beforeArray = null;
        }
        return beforeArray;
    }

    private boolean wrongArgs(PSObject proc, PSObject elems) {
        if (elems == null || !(proc.isProc())) {
            fail();
            runtime.pushToOperandStack(proc);
            return true;
        }
        return false;
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("forall");
    }
}
