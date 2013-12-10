package psObjects.composite;

import runtime.stack.PSStack;
import runtime.PSTable;

public class Snapshot extends CompositeObject {
    private PSTable table = new PSTable();
    private PSStack operandStack = new PSStack();

    public Snapshot(PSTable table, PSStack operandStack) {
        this.table = table;
        this.operandStack = operandStack;
    }

    public PSTable getTable() {
        return table;
    }

    public PSStack getOperandStack() {
        return operandStack;
    }
}
