package runtime;

import operators.DefaultDicts;
import operators.graphicsState.GRestoreAllOp;
import procedures.ArrayProcedure;
import procedures.Procedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.Snapshot;
import psObjects.values.reference.GlobalRef;
import psObjects.values.reference.LocalRef;
import psObjects.values.reference.Reference;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;
import runtime.avl.Pair;
import runtime.graphics.GState;
import runtime.stack.CallStack;
import runtime.stack.DictionaryStack;
import runtime.stack.GraphicStack;
import runtime.stack.OperandStack;

import java.util.HashSet;
import java.util.Set;

import static psObjects.Type.*;


public class Runtime {

    private static Runtime ourInstance = new Runtime();

    public boolean isCompiling;
    public BytecodeGeneratorManager bcGen = new BytecodeGeneratorManager();

    private int executionCount = 0;
    private int executionsBeforeGarbageCleaning = 10000;

    private LocalVM localVM = new LocalVM();
    private OperandStack operandStack = new OperandStack();
    private DictionaryStack dictionaryStack = new DictionaryStack();
    private GraphicStack graphicStack = new GraphicStack();
    private CallStack callStack = new CallStack();
    private boolean isGlobal = false;
    private PSObject userDict, globalDict, systemDict;

    private Runtime() {
        isCompiling = false;
//        isCompiling = true;
    }

    public void setCompilingMode(boolean compilingMode) {
        ourInstance.isCompiling = compilingMode;
    }

    public Runtime(boolean isCompiling) {
        this.isCompiling = isCompiling;
    }

    public static Runtime getInstance() {
        return ourInstance;
    }

    /*
    * save snapshot to operandStack
    */
    public void save() {
        //localVM.clearGarbage(getRootSet());

        Snapshot snapshot = new Snapshot(localVM.clone(), getGState());
        gsave(false);
        localVM.initDefaultKeys();
        operandStack.push(new PSObject(snapshot));
    }

    public void gsave(boolean isMadeByGsave) {
        getGState().setMadeByGSaveOp(isMadeByGsave);
        GState gState = getGState().getSnapshot();
        pushToGraphicStack(gState);
    }

    /*
    * getting snapshot from top of operandStack
    */
    public boolean restore() {
        PSObject object = popFromOperandStack();
        if (object.getType() != SAVE) return false;
        Snapshot snapshot = (Snapshot) object.getValue();
        LocalVM savedLocalVM = snapshot.getLocalVM();
        for (PSObject current : operandStack) {
            Value curValue = current.getValue();
            //if operand stack contains reference to composite object which was created after saving, we can't restore
            if (current.isComposite() && localVM.contains(curValue) && !savedLocalVM.contains(curValue)) {
                System.out.println("invalid restore");
                System.exit(0);
                return false;
            }
        }
        savedLocalVM.updateStringValues(localVM); //string values don't restores
        localVM = savedLocalVM;

        GRestoreAllOp.instance.interpret();
        graphicStack.setGState(snapshot);
        return true;
    }

    public int addToLocalVM(CompositeValue value) {
        return localVM.add(value);
    }

    public int setNewValueAtLocalVM(int index, CompositeValue value) {
        localVM.setNewValueAtIndex(index, value);
        return index;
    }

    public void pushToOperandStack(PSObject psObject) {
        //System.out.println("\t\t\t\t\t\t\t\t\t\t\t" + psObject.getValue().toString());
        if (!(psObject == null)) {
            operandStack.push(psObject);
        }
    }

    public PSObject popFromOperandStack() {
        return operandStack.pop();
    }

    public void pushToGraphicStack(GState gsave) {
        graphicStack.push(gsave);
    }

    public GState popFromGraphicStack() {
        return graphicStack.pop();
    }

    public GState peekFromGraphicStack() {
        return graphicStack.peek();
    }

    public PSObject peekFromOperandStack() {
        return operandStack.peek();
    }

    public Procedure popFromCallStack() {
//        Procedure procedure = callStack.peek();
//        callStack = callStack.pop();
        return callStack.pop();
    }

    public Procedure peekFromCallStack() {
        return callStack.peek();
    }

    public int getCallStackSize() {
        return callStack.size();
    }

    public void executeCallStack() {
        while (!callStack.isEmpty()) {
            Procedure topProcedure = callStack.peek();
            if (topProcedure.hasNext()) {
                executionCount++;
                topProcedure.execNext();
                if (executionCount % executionsBeforeGarbageCleaning == 0) {
//                    System.out.println("Local vm argsCount before gc " + localVM.argsCount());
                    localVM.clearGarbage(getRootSet());
//                    System.out.println("Local vm argsCount after gc " + localVM.argsCount());
                }
            } else {
                topProcedure.procTerminate();
                popFromCallStack();
            }
        }
    }

    public Set<Integer> getRootSet() {
        Set<Integer> indexes = new HashSet<Integer>();
        //todo analyse combining global and local refs together
        //operandStack
        for (PSObject o : operandStack) {
            if (!(o.getDirectValue() instanceof LocalRef)) continue;
            LocalRef ref = (LocalRef) o.getDirectValue();
            getUsingLocalVMIndexesByRef(indexes, ref);
        }

        //graphicStack
        for (GState gState : graphicStack) {
            PSObject o = gState.cTM.getMatrix();
            PSArray arr = (PSArray) o.getValue();
            if (!(o.getDirectValue() instanceof LocalRef)) continue;
            LocalRef ref = (LocalRef) o.getDirectValue();
            getUsingLocalVMIndexesByRef(indexes, ref);
        }
        LocalRef locRefCTM = getGState().getLocalRefCTM();
        //LocalRef locRefDash = GState.getInstance().getLocalRefDash() ;
        if (locRefCTM != null) {
            getUsingLocalVMIndexesByRef(indexes, locRefCTM);
        }
        //if(locRefDash != null){
        //    getUsingLocalVMIndexesByRef(indexes, locRefDash);
        //}

        //dictStack
        for (PSObject o : dictionaryStack) {

            if (!(o.getDirectValue() instanceof LocalRef)) continue;
            LocalRef ref = (LocalRef) o.getDirectValue();
            if (o == systemDict) {
                // in systemdict we don't have any localRefs
                indexes.add(ref.getTableIndex());
            } else {
                getUsingLocalVMIndexesByRef(indexes, ref);
            }
        }
        //callStack
        for (Procedure proc : callStack) {
            if (!(proc instanceof ArrayProcedure)) continue;
            PSObject array = ((ArrayProcedure) proc).getArrayObject();
            if (!(array.getDirectValue() instanceof LocalRef)) continue;
            LocalRef ref = (LocalRef) array.getDirectValue();
            getUsingLocalVMIndexesByRef(indexes, ref);
        }
        return indexes;
        //todo same for graphicstack after making collection psObjects in graphicstack
    }

    private void getUsingLocalVMIndexesByRef(Set<Integer> indexes, LocalRef ref) {
        int index = ref.getTableIndex();
        if (indexes.contains(index)) return;
        indexes.add(index);
        LocalRef newRef;
        switch (ref.determineType()) {
            case PACKEDARRAY:
            case ARRAY:
                PSObject[] objects = ((PSArray) ref.getValue()).getArray();
                for (PSObject o : objects) {
                    if (!(o.getDirectValue() instanceof LocalRef)) continue;
                    newRef = (LocalRef) o.getDirectValue();
                    getUsingLocalVMIndexesByRef(indexes, newRef);
                }
                break;
            case DICTIONARY:
                PSDictionary dict = (PSDictionary) ref.getValue();
                for (Pair<PSObject, PSObject> pair : dict.getTree()) {
                    PSObject key = pair.getKey();
                    PSObject value = pair.getValue();
                    if ((key.getDirectValue() instanceof LocalRef)) {
                        newRef = (LocalRef) key.getDirectValue();
                        getUsingLocalVMIndexesByRef(indexes, newRef);
                    }
                    if ((value.getDirectValue() instanceof LocalRef)) {
                        newRef = (LocalRef) value.getDirectValue();
                        getUsingLocalVMIndexesByRef(indexes, newRef);
                    }
                }
                break;
            default:
                break;
        }
    }


    public void pushToCallStack(Procedure procedure) {
        callStack.push(procedure);
    }


    public void pushToDictionaryStack(PSObject dict) {
        dictionaryStack.push(dict);
    }

    public PSObject peekFromDictionaryStack() {
        return dictionaryStack.peek();
    }

    public boolean removeFromDictionaryStack() {
        if (dictionaryStack.size() > 3) {
            dictionaryStack.pop();
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

    @Deprecated
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
    @Deprecated
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
    @Deprecated
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
    @Deprecated
    public PSObject undefValueAtDictionaryKey(PSObject dictObject, PSObject key) {
        //todo check
        if (dictObject.getType() != Type.DICTIONARY) return dictObject;
        PSDictionary dict = (PSDictionary) dictObject.getValue();
        PSDictionary newDict = dict.undef(key);
        dictObject.setValue(newDict);
        return dictObject;

    }

    @Deprecated
    public PSObject copy(PSObject srcDictRef, PSObject dstDictRef) {
        //todo check
        if (srcDictRef.getType() != Type.DICTIONARY) return dstDictRef;
        PSDictionary srcDict = (PSDictionary) srcDictRef.getValue();
        PSDictionary dstDict = (PSDictionary) dstDictRef.getValue();
        PSDictionary resDict = dstDict.copy(srcDict);
        return new PSObject(resDict);
    }


    //    dict key get any Return value associated with key in dict
    public PSObject getValueAtDictionary(PSObject dictObject, PSObject key) {
        return ((PSDictionary) dictObject.getValue()).get(key);
    }

    //clearOperandStack operandStack;
    public void clearOperandStack() {
        operandStack.clear();
    }

    public void clearDictionaryStack() {
        while (removeFromDictionaryStack()) {
            //removing done in condition automatically
        }
    }

    public void clearAll() {
        operandStack.clear();
        localVM.clear();
        graphicStack.clear();
        dictionaryStack.clear();
        graphicStack.reset();
    }

    public CompositeValue getValueByLocalRef(LocalRef ref) {
        return localVM.get(ref.getTableIndex());
    }

/*    public LocalRef putPSObjectToLocalVM(Value object) {
        localVM.add(object);
        return new LocalRef(localVM.argsCount() - 1);
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

    public PSObject findDict(PSObject key) {
        PSObject found;
        for (PSObject dictObj : dictionaryStack) {
            found = getValueAtDictionary(dictObj, key);
            if (found != null) return dictObj;
        }
        return null;
    }

    public PSObject findValue(PSObject key) {
        PSObject found = search(key);
        //if(key.equals(new PSObject(new PSName("xd"))) && key.xcheck())
        if (found != null) {
            return found;
        }
        try {
            throw new Exception(key + " is not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PSObject(PSNull.NULL);
    }

    public PSObject search(PSObject key) {
        PSObject found = null;
        for (PSObject dictObj : dictionaryStack) {
            found = getValueAtDictionary(dictObj, key);
            if (found != null) {
                return found;
            }
        }
        return found;
    }

    /*called through bytecode*/
    public PSObject findValue(String str) {
        return findValue(new PSObject(new PSName(str)));
    }

    public int getOperandStackSize() {
        return operandStack.size();
    }

    public int getDictionaryStackSize() {
        return dictionaryStack.size();
    }


    public void initDefaultDictionaries() {
        systemDict = new PSObject(DefaultDicts.getSystemDict(),
                Type.DICTIONARY,
                new Attribute(Attribute.Access.READ_ONLY, Attribute.TreatAs.LITERAL));
        pushToDictionaryStack(systemDict);
        globalDict = new PSObject(DefaultDicts.getGlobalDict());
        pushToDictionaryStack(globalDict);
        userDict = new PSObject(DefaultDicts.getUserDict());
        pushToDictionaryStack(userDict);
    }

    public void initRuntime() {
        initDefaultDictionaries();
        graphicStack.init();

    }

    public PSObject currentDict() {
        return dictionaryStack.peek();
    }

    public int getGraphicStackSize() {
        return graphicStack.size();
    }

    public PSObject getUserDict() {
        return userDict;
    }

    public PSObject getGlobalDict() {
        return globalDict;
    }

    public PSObject getSystemDict() {
        return systemDict;
    }

    public GState getGState() {
        if (graphicStack.isEmpty()) {
            graphicStack.init();
        }
        return graphicStack.peek();
    }
}