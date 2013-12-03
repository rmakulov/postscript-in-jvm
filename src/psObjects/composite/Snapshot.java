package psObjects.composite;

import psObjects.PSObject;
import runtime.PSStack;
import runtime.PSTable;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 03.12.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
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

    @Override
    public PSObject clone() {
        return this;
    }
}
