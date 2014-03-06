package runtime;

import psObjects.PSObject;
import psObjects.composite.PSArray;
import psObjects.composite.PSDictionary;
import psObjects.composite.Snapshot;
import psObjects.reference.GlobalRef;
import psObjects.reference.LocalRef;
import psObjects.reference.Reference;
import runtime.stack.DictionaryStack;
import runtime.stack.OperandStack;
import runtime.stack.PSStack;

//import psObjects.composite.PSDictionary;

public class Runtime {
    private static Runtime ourInstance = new Runtime();


    private PSTable table = new PSTable();
    private OperandStack operandStack = new OperandStack();
    private DictionaryStack dictionaryStack = new DictionaryStack();
    private boolean isGlobal = false;


    private Runtime() {
    }

    public static Runtime getInstance() {
        return ourInstance;
    }

    /*
    * save snapshot to operandStack
    */
    public void save() {
        Snapshot snapshot = new Snapshot(table, operandStack);
        operandStack = operandStack.push(createReference(snapshot));
    }

    /*
    * getting snapshot from top of operandStack
    */
    public boolean restore() {
        Reference ref = popFromOperandStack();
        if (!(ref.isSnapshotRef())) return false;
        Snapshot snapshot = (Snapshot) ref.getPSObject();
        PSStack savedOperandStack = snapshot.getOperandStack();
        for (Reference current : operandStack) {
            //if operand stack contains reference to composite object which was created after saving, we can't restore
            if (current.isCompositeRef() && table.contains(current) && !savedOperandStack.contains(current)) {
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

    public void pushToOperandStack(Reference psObjectRef) {
        operandStack = operandStack.push(psObjectRef);
    }

    public Reference popFromOperandStack() {
        Reference objectRef = operandStack.peek();
        operandStack = operandStack.removeTop();
        return objectRef;
    }

    public Reference peekFromOperandStack() {
        return operandStack.peek();
    }

/*    public void pushToDictionaryStack(PSDictionary dict) {
        Reference ref = putPSObjectToLocalMemory(dict);
        dictionaryStack = dictionaryStack.push(ref);
    }*/

    public void pushToDictionaryStack(Reference dictRef) {
        dictionaryStack = dictionaryStack.push(dictRef);
    }

    public Reference peekFromDictionaryStack() {
        return dictionaryStack.peek();
    }

    public void removeFromDictionaryStack() {
        dictionaryStack = dictionaryStack.removeTop();
    }


/*    public PSObject getPSObjectFromLocalVM(int index) {
        return table.get(index);
    }*/

    /*
    * Find array in Local VM by LocalRef and change value by index = valueIndex
     */
    public LocalRef createLocalRef(PSObject psObject) {
        return new LocalRef(addToLocalVM(psObject));
    }

    public LocalRef setValueArrayAtIndex(LocalRef localRefArray, int valueIndex, PSObject value) {
        PSObject psObject = getPSObjectByLocalRef(localRefArray);
        if (psObject instanceof PSArray) {
            PSArray array = (PSArray) psObject;
            LocalRef newArrLocalRef = createLocalRef(array.setValue(valueIndex, value));
            return newArrLocalRef;
        }
        //todo throw type exception
        return null;
    }

    /*
   * Find array in Local VM by LocalRef and get interval by startIndex and length
    */
    public LocalRef getArrayInterval(LocalRef localRefArray, int startIndex, int length) {
        PSObject psObject = getPSObjectByLocalRef(localRefArray);
        if (psObject instanceof PSArray) {
            PSArray array = (PSArray) psObject;
            LocalRef subArrLocalRef = createLocalRef(array.getInterval(startIndex, length));
            return subArrLocalRef;
        }
        //todo throw type exception
        return null;
    }

    //dict key value put – Associate key with value in dict
    public Reference putValueAtDictionaryKey(Reference dictRef, Reference key, Reference value) {
        if (!dictRef.isDictionaryRef()) {
            return dictRef;
        }
        PSDictionary dict = (PSDictionary) dictRef.getPSObject();
        PSDictionary newDict = dict.put(key, value);
        dictRef.setPSObject(newDict);
        return dictRef;
    }

    //dict key undef – Remove key and its value from dict
    public Reference undefValueAtDictionaryKey(Reference dictRef, Reference keyRef) {
        //todo check
        if (!dictRef.isDictionaryRef()) {
            return dictRef;
        }
        PSDictionary dict = (PSDictionary) dictRef.getPSObject();
        PSDictionary newDict = dict.undef(keyRef);
        dictRef.setPSObject(newDict);
        return dictRef;

    }
    
    public Reference copy(Reference srcDictRef, Reference dstDictRef){
        //todo check
        if(!srcDictRef.isDictionaryRef()) return dstDictRef;
        PSDictionary srcDict = (PSDictionary) srcDictRef.getPSObject();
        PSDictionary dstDict = (PSDictionary) dstDictRef.getPSObject();
        PSDictionary resDict = dstDict.copy(srcDict);
        Reference resDictRef = createReference(resDict);
        return resDictRef;
    }

/*    private void replaceDictionaryInStack(PSDictionary dict, PSDictionary newDict) {
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
    }*/

    //    dict key get any Return value associated with key in dict
    public Reference getValueAtDictionary(Reference dictRef, Reference key) {
        return ((PSDictionary) dictRef.getPSObject()).get(key);
    }


    public boolean exchangeTopOfOperandStack() {
        OperandStack stack = operandStack.exch();
        if (stack == null) return false;
        operandStack = stack;
        return true;
    }

    //clear operandStack;
    public void clear(){
        operandStack.clear();
    }

    public void clearAll(){
        operandStack.clear();
        table.clear();
    }
    
    public PSObject getPSObjectByLocalRef(LocalRef ref) {
        return table.get(ref.getTableIndex());
    }

/*    public LocalRef putPSObjectToLocalVM(PSObject object) {
        table.add(object);
        return new LocalRef(table.size() - 1);
    }*/

    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public Reference createReference(PSObject object) {
        if (isGlobal) return new GlobalRef(object);
        else return createLocalRef(object);
    }

}