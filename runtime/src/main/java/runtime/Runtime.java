package runtime;

import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.Snapshot;
import psObjects.values.composite.dictionaries.DefaultDicts;
import psObjects.values.composite.dictionaries.PSDictionary;
import psObjects.values.reference.GlobalRef;
import psObjects.values.reference.LocalRef;
import psObjects.values.reference.Reference;
import psObjects.values.simple.PSNull;
import runtime.graphics.save.GSave;
import runtime.stack.DictionaryStack;
import runtime.stack.GraphicStack;
import runtime.stack.OperandStack;
import runtime.stack.PSStack;

import static psObjects.Type.*;


public class Runtime {
    private static Runtime ourInstance = new Runtime();


    private LocalVM localVM = new LocalVM();
    private OperandStack operandStack = new OperandStack();
    private DictionaryStack dictionaryStack = new DictionaryStack();
    private GraphicStack graphicStack = new GraphicStack();
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
        Snapshot snapshot = new Snapshot(localVM, operandStack);
        operandStack = operandStack.push(new PSObject(snapshot));
        gsave(false);

    }

    public void gsave(boolean b) {
        GSave gsave = new GSave(b);
        gsave.getSnapshot();
        pushToGraphicStack(gsave);

    }

    /*
    * getting snapshot from top of operandStack
    */
    public boolean restore() {
        PSObject object = popFromOperandStack();
        if (object.getType() != SAVE) return false;
        Snapshot snapshot = (Snapshot) object.getValue();
        PSStack savedOperandStack = snapshot.getOperandStack();
        for (PSObject current : operandStack) {
            Value curValue = current.getValue();
            //if operand stack contains reference to composite object which was created after saving, we can't restore
            if (localVM.contains(curValue) && !savedOperandStack.contains(current)) {
                return false;
            }
        }
        localVM = snapshot.getTable();
        operandStack = snapshot.getOperandStack();
        GSave gsave = popFromGraphicStack();
        gsave.setSnapshot();
        return true;
    }

    public int addToLocalVM(CompositeValue value) {
        localVM = localVM.add(value);
        return localVM.size() - 1;
    }

    public void pushToOperandStack(PSObject psObject) {
        operandStack = operandStack.push(psObject);
    }

    public PSObject popFromOperandStack() {
        PSObject object = operandStack.peek();
        if (object == null)
            return object;
        operandStack = operandStack.removeTop();
        return object;
    }

    public void pushToGraphicStack(GSave gsave) {
        graphicStack = graphicStack.push(gsave);
    }

    public GSave popFromGraphicStack() {
        GSave gsave = graphicStack.peek();
        graphicStack = graphicStack.removeTop();
        return gsave;
    }

    public GSave peekFromGraphicStack() {
        return graphicStack.peek();
    }

    public PSObject peekFromOperandStack() {
        return operandStack.peek();
    }


    public void pushToDictionaryStack(PSObject dict) {
        dictionaryStack = dictionaryStack.push(dict);
    }

    public PSObject peekFromDictionaryStack() {
        return dictionaryStack.peek();
    }

    public boolean removeFromDictionaryStack() {
        if (dictionaryStack.size() > 3) {
            dictionaryStack = dictionaryStack.removeTop();
            return true;
        }
        return false;
    }


/*    public Value getPSObjectFromLocalVM(int index) {
        return localVM.get(index);
    }*/

    /*
    * Find array in Local VM by LocalRef and change value by index = valueIndex
     */
    public LocalRef createLocalRef(CompositeValue value) {
        return new LocalRef(addToLocalVM(value));
    }

    public PSObject setValueArrayAtIndex(PSObject arrayObject, int valueIndex, PSObject value) {
        if (arrayObject.getType() != ARRAY) {
            //todo throw type exception
            return null;
        }
        PSArray psArray = (PSArray) arrayObject.getValue();
        PSArray newValue = psArray.setValue(valueIndex, value);
        return arrayObject.setValue(newValue);
    }

    /*
   * Find array in Local VM by LocalRef and get interval by startIndex and length
    */
    public PSObject getArrayInterval(PSObject arrayObject, int startIndex, int length) {
        if (arrayObject.getType() != ARRAY) {
            //todo throw type exception
            return null;
        }
        PSArray psArray = (PSArray) arrayObject.getValue();
        Type type = arrayObject.getType();
        Attribute attr = arrayObject.getAttribute();
        return new PSObject(psArray.getInterval(startIndex, length), type, attr);
    }

    //dict key value put – Associate key with value in dict
    public PSObject putValueAtDictionaryKey(PSObject dictObject, PSObject key, PSObject value) {
        if (dictObject.getType() != DICTIONARY) {
            return dictObject;
        }
        PSDictionary dict = (PSDictionary) dictObject.getValue();
        PSDictionary newDict = dict.put(key, value);
        dictObject.setValue(newDict);
        return dictObject;
    }

    //dict key undef – Remove key and its value from dict
    public PSObject undefValueAtDictionaryKey(PSObject dictObject, PSObject key) {
        //todo check
        if (dictObject.getType() != Type.DICTIONARY) return dictObject;
        PSDictionary dict = (PSDictionary) dictObject.getValue();
        PSDictionary newDict = dict.undef(key);
        dictObject.setValue(newDict);
        return dictObject;

    }

    public PSObject copy(PSObject srcDictRef, PSObject dstDictRef) {
        //todo check
        if (srcDictRef.getType() != Type.DICTIONARY) return dstDictRef;
        PSDictionary srcDict = (PSDictionary) srcDictRef.getValue();
        PSDictionary dstDict = (PSDictionary) dstDictRef.getValue();
        PSDictionary resDict = dstDict.copy(srcDict);
        return new PSObject(resDict);
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
    public PSObject getValueAtDictionary(PSObject dictObject, PSObject key) {
        return ((PSDictionary) dictObject.getValue()).get(key);
    }


    public boolean exchangeTopOfOperandStack() {
        OperandStack stack = operandStack.exch();
        if (stack == null) return false;
        operandStack = stack;
        return true;
    }

    //clear operandStack;
    public void clear() {
        operandStack.clear();
    }

    public void clearAll() {
        operandStack.clear();
        localVM.clear();
    }

    public CompositeValue getValueByLocalRef(LocalRef ref) {
        return localVM.get(ref.getTableIndex());
    }

/*    public LocalRef putPSObjectToLocalVM(Value object) {
        localVM.add(object);
        return new LocalRef(localVM.size() - 1);
    }*/

    public void setGlobal(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    public boolean currentGlobal() {
        return isGlobal;
    }

    public Reference createReference(CompositeValue object) {
        if (isGlobal) return new GlobalRef(object);
        else return createLocalRef(object);
    }

    public PSObject findValue(PSObject key) {
        //todo order of value search
        PSObject found;
        for (PSObject dictObj : dictionaryStack) {
            found = getValueAtDictionary(dictObj, key);
            if (found != null) return found;
        }
        return new PSObject(PSNull.NULL);
    }

    public int getOperandStackSize() {
        return operandStack.size();
    }

    public int getDictionaryStackSize() {
        return dictionaryStack.size();
    }

    public void initDefaultDictionaries() {
        dictionaryStack.push(new PSObject(DefaultDicts.getSystemDict()));
        dictionaryStack.push(new PSObject(DefaultDicts.getGlobalDict()));
        dictionaryStack.push(new PSObject(DefaultDicts.getUserDict()));
    }
}