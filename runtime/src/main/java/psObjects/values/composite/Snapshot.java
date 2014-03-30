package psObjects.values.composite;

import psObjects.Type;
import runtime.LocalVM;

public class Snapshot extends CompositeValue {
    private LocalVM table = new LocalVM();
    // private OperandStack operandStack = new OperandStack();
    //private Integer[] tableIndexes;

    public Snapshot(LocalVM table) {
        this.table = table;
    }
/*
    private void saveIndexesFromOperandStack(OperandStack operandStack) {
        tableIndexes = new Integer[operandStack.size()];
        int i = 0;
        for (PSObject obj : operandStack) {
            tableIndexes[i] = obj.getIndexInLocalVM();
            i++;
        }
    }*/

    public LocalVM getLocalVM() {
        return table;
    }

/*    public OperandStack getOperandStack() {
        restoreIndexesFromOperandStack();
        return operandStack;
    }*/

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

/*    public void restoreIndexesFromOperandStack() {
        int i = 0;
        for (PSObject obj : operandStack) {
            obj.setIndexInLocalVM(tableIndexes[i]);
            i++;
        }
    }*/

    @Override
    public String toString() {
        return "Snapshot{}";
    }
}
