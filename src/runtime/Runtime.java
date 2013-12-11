package runtime;

import psObjects.PSObject;
import psObjects.composite.CompositeObject;
import psObjects.composite.PSArray;
import psObjects.composite.PSDictionary;
import psObjects.composite.Snapshot;
import runtime.stack.DictionaryStack;
import runtime.stack.OperandStack;
import runtime.stack.PSStack;

public class Runtime {
    PSTable table = new PSTable();
    OperandStack operandStack = new OperandStack();
    DictionaryStack dictionaryStack = new DictionaryStack();

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

    public void pushToDictionaryStack(PSDictionary dict){
        dictionaryStack = dictionaryStack.push(dict);
    }

    public PSObject peekFromDictionaryStack() {
        return dictionaryStack.peek();
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

    //dict key value put – Associate key with value in dict
    public PSDictionary putValueAtDictionaryKey(PSDictionary dict, PSObject key, PSObject value) {
        PSDictionary newDict = dict.put(key, value);
        replaceDictionaryInStack(dict, newDict);
        return newDict;
    }

    //dict key undef – Remove key and its value from dict
    public PSDictionary undefValueAtDictionaryKey(PSDictionary dict, PSObject key) {
        PSDictionary newDict = dict.undef(key);
        replaceDictionaryInStack(dict, newDict);
        return newDict;

    }

    private void replaceDictionaryInStack(PSDictionary dict, PSDictionary newDict) {
        DictionaryStack tempDictStack = new DictionaryStack();
        while (true) {
            PSDictionary curDict = dictionaryStack.peek();
            if (curDict == null) break;
            if (dict == curDict) {
                dictionaryStack.removeTop();
                dictionaryStack.push(newDict);
                break;
            } else {
                tempDictStack.push(curDict);
                dictionaryStack.removeTop();
            }
        }
        while (true) {
            PSDictionary curDict = tempDictStack.peek();
            if (curDict == null) break;
            dictionaryStack.push(curDict);
            tempDictStack.removeTop();
        }
    }

    //    dict key get any Return value associated with key in dict
    public PSObject getValueAtDictionary(PSDictionary dict, PSObject key){
        return dict.get(key);
    }



    public boolean exchangeTopOfOperandStack() {
        OperandStack stack = operandStack.exch();
        if (stack == null) return false;
        operandStack = stack;
        return true;
    }
}