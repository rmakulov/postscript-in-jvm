package runtime;

import psObjects.PSObject;
import psObjects.composite.CompositeObject;
import psObjects.composite.PSArray;
import psObjects.composite.Snapshot;

public class Runtime {
    PSTable table = new PSTable();
    PSStack operandStack = new PSStack();

    /*
     * save snapshot to operandStack
     */
    public void save() {
        Snapshot snapshot = new Snapshot(table, operandStack);
        operandStack = operandStack.push(snapshot);
    }

    /*
    * getting snapshot from top of operandStack
    */
    public boolean restore() {
        PSObject psObject = popFromOperandStack();
        if (!(psObject instanceof Snapshot)) return false;
        Snapshot snapshot = (Snapshot) psObject;
        PSStack savedOperandStack = snapshot.getOperandStack();
        for (PSObject current : operandStack) {
            //if operand stack contains reference to composite object which was created after saving, we can't restore
            if (current instanceof CompositeObject && table.contains(current) && !savedOperandStack.contains(current)) {
                return false;
            }
        }
        table = snapshot.getTable();
        return true;
    }

    public int addToLocalVM(PSObject psObject) {
        table = table.add(psObject);
        return table.size() - 1;
    }

    public void pushToOperandStack(PSObject psObject) {
        operandStack = operandStack.push(psObject);
    }

    public PSObject popFromOperandStack() {
        PSObject object = operandStack.peek();
        operandStack = operandStack.removeTop();
        return object;
    }

    public PSObject peekFromOperandStack() {
        return operandStack.peek();
    }

    public PSObject getPSObjectFromLocalVM(int index) {
        return table.get(index);
    }

    public int setValueArrayAtIndex(int arrIndex, int valueIndex, PSObject value) {
        PSObject psObject = getPSObjectFromLocalVM(arrIndex);
        if (psObject instanceof PSArray) {
            PSArray array = (PSArray) psObject;
            int newArrIndex = addToLocalVM(array.setValue(valueIndex, value));
            return newArrIndex;
        }
        //todo throw type exception
        return -1;
    }

    public int getArrayInterval(int arrIndex, int startIndex, int length) {
        PSObject psObject = getPSObjectFromLocalVM(arrIndex);
        if (psObject instanceof PSArray) {
            PSArray array = (PSArray) psObject;
            int subArrIndex = addToLocalVM(array.getInterval(startIndex, length));
            return subArrIndex;
        }
        //todo throw type exception
        return -1;
    }

    public boolean exchangeTopOfOperandStack() {
        PSStack stack = operandStack.exch();
        if (stack == null) return false;
        operandStack = stack;
        return true;
    }
}