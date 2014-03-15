package psObjects.values.composite;

import psObjects.PSObject;
import psObjects.Type;
import runtime.LocalVM;
import runtime.stack.OperandStack;

public class Snapshot extends CompositeValue {
    private LocalVM table = new LocalVM();
    private OperandStack operandStack = new OperandStack();
    private Integer[] tableIndexes;

    public Snapshot(LocalVM table, OperandStack operandStack) {
        this.table = table;
        this.operandStack = operandStack;
        saveIndexesFromOperandStack(operandStack);
    }

    private void saveIndexesFromOperandStack(OperandStack operandStack) {
        tableIndexes = new Integer[operandStack.size()];
        int i = 0;
        for (PSObject obj : operandStack) {
            tableIndexes[i] = obj.getIndexInLocalVM();
            i++;
        }
    }

    public LocalVM getTable() {
        return table;
    }

    public OperandStack getOperandStack() {
        restoreIndexesFromOperandStack();
        return operandStack;
    }

    @Override
    public Type determineType() {
        return Type.SAVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Snapshot)) return false;
        return true;
    }

    public void restoreIndexesFromOperandStack() {
        int i = 0;
        for (PSObject obj : operandStack) {
            obj.setIndexInLocalVM(tableIndexes[i]);
            i++;
        }
    }
}
