package runtime;

import operators.DefaultDicts;
import operators.graphicsState.GRestoreAllOp;
import operators.painting.PSPrimitive;
import procedures.ArrayProcedure;
import procedures.Procedure;
import procedures.StringProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.*;
import psObjects.values.reference.GlobalRef;
import psObjects.values.reference.LocalRef;
import psObjects.values.reference.Reference;
import runtime.avl.Pair;
import runtime.compiler.BytecodeGeneratorManager;
import runtime.compiler.DynamicClassLoader;
import runtime.events.Event;
import runtime.events.EventQueue;
import runtime.events.PrimitiveQueue;
import runtime.graphics.GState;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static psObjects.Type.SAVE;


public class Runtime {

    private static Runtime ourInstance = new Runtime();

    public boolean isCompiling = false;
    public BytecodeGeneratorManager bcGenManager = new BytecodeGeneratorManager();
    private HashMap<String, Integer> nameVersions = new HashMap<String, Integer>();

    private int executionCount = 0;
    private int executionsBeforeGarbageCleaning = 10000;

    private LocalVM localVM = new LocalVM();
    //    private OperandStack operandStack = new OperandStack();
//    private DictionaryStack dictionaryStack = new DictionaryStack();
//    private GraphicStack graphicStack = new GraphicStack();
//    private CallStack callStack = new CallStack();
    private EventQueue eventQueue = new EventQueue();
    private PrimitiveQueue primitiveQueue = new PrimitiveQueue();
    private boolean isGlobal = false;
    private PSObject systemDict;
    private Context mainContext;
    private Set<Context> contextSet = new HashSet<Context>();

    private ExecutorService service = Executors.newFixedThreadPool(10);

    //        private Runtime() {
//        isCompiling = false;
////        isCompiling = true;
//    }
    private Runtime() {
        isCompiling = false;
//        isCompiling = true;
    }

    public void startNewTask(Context context, Procedure procedure) {
        context.initDictionaries(systemDict);

        PSThread thread = new PSThread(context, procedure);
        service.submit(thread);
//        service.execute(thread);
    }

    public void startMainTask(Context context, Procedure procedure) {
        mainContext = context;
        context.initDictionaries(systemDict);
        PSThread thread = new PSThread(context, procedure);
//        thread.run();
        service.submit(thread);
    }

    public Context getMainContext() {
        return mainContext;
    }

    public void setCompilingMode(boolean compilingMode) {
        isCompiling = compilingMode;
    }
//    public void setCompilingMode(boolean compilingMode) {
//        ourInstance.isCompiling = compilingMode;
//    }

    public Runtime(boolean isCompiling) {
        this.isCompiling = isCompiling;
    }

    /**
     * @return Runtime instance
     */
    public static Runtime getInstance() {
        return ourInstance;
    }


    /*
    * save snapshot to operandStack
    */
    public void save(Context context) {
        //localVM.clearGarbage(getRootSet());

        Snapshot snapshot = new Snapshot(localVM.clone(), context.getGState(), nameVersions);
        gsave(context, false);
        localVM.initDefaultKeys();
        context.pushToOperandStack(new PSObject(snapshot));
    }

    public void gsave(Context context, boolean isMadeByGsave) {
        context.getGState().setMadeByGSaveOp(isMadeByGsave);
        GState gState = context.getGState().getSnapshot();
        context.pushToGraphicStack(gState);
    }

    /*
    * getting snapshot from top of operandStack
    */
    public boolean restore(Context context) {
        PSObject object = context.popFromOperandStack();
        if (object.getType() != SAVE) return false;
        Snapshot snapshot = (Snapshot) object.getValue();
        LocalVM savedLocalVM = snapshot.getLocalVM();
        for (PSObject current : context.getOperandStack()) {
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
        nameVersions = snapshot.getNameVersions();

        GRestoreAllOp.instance.interpret(context);
        context.getGraphicStack().setGState(snapshot);
        return true;
    }

    public void repaintMainContext() {
        try {
            mainContext.pushToCallStack(new StringProcedure(mainContext, new PSObject(new PSString("repaintAll"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mainContext.executeCallStack();
//        System.out.println(mainContext.getCallStackSize());
    }

    public int addToLocalVM(CompositeValue value) {
        return localVM.add(value);
    }

    public int setNewValueAtLocalVM(int index, CompositeValue value) {
        localVM.setNewValueAtIndex(index, value);
        return index;
    }


    public void cleanGarbage() {
        //todo run at all contexts
//        System.err.println("Clean garbage error. It is not finished");
        localVM.clearGarbage(getRootSet());
    }

    public Set<Integer> getRootSet() {
        Set<Integer> indexes = new HashSet<Integer>();
        for(Context context:contextSet) {
            //todo analyse combining global and local refs together
            //operandStack
            for (PSObject o : context.getOperandStack()) {
                if (!(o.getDirectValue() instanceof LocalRef)) continue;
                LocalRef ref = (LocalRef) o.getDirectValue();
                getUsingLocalVMIndexesByRef(indexes, ref);
            }

            //graphicStack
            for (GState gState : context.getGraphicStack()) {
                PSObject oMatrix = gState.cTM.getMatrix();
                PSObject oFont = gState.getFont();
                //PSArray arr = (PSArray) oMatrix.getValue();
                if ((oMatrix.getDirectValue() instanceof LocalRef)) {
                    LocalRef ref = (LocalRef) oMatrix.getDirectValue();
                    getUsingLocalVMIndexesByRef(indexes, ref);
                }

                if (oFont != null && (oFont.getDirectValue() instanceof LocalRef)) {
                    LocalRef ref = (LocalRef) oFont.getDirectValue();
                    getUsingLocalVMIndexesByRef(indexes, ref);
                }
            }
            LocalRef locRefCTM = context.getGState().getLocalRefCTM();
            //LocalRef locRefDash = GState.getInstance().getLocalRefDash() ;
            if (locRefCTM != null) {
                getUsingLocalVMIndexesByRef(indexes, locRefCTM);
            }
            //if(locRefDash != null){
            //    getUsingLocalVMIndexesByRef(indexes, locRefDash);
            //}


            //dictStack
            for (PSObject o : context.getDictionaryStack()) {

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
            for (Procedure proc : context.getCallStack()) {
                if (!(proc instanceof ArrayProcedure)) continue;
                PSObject array = ((ArrayProcedure) proc).getArrayObject();
                if (!(array.getDirectValue() instanceof LocalRef)) continue;
                LocalRef ref = (LocalRef) array.getDirectValue();
                getUsingLocalVMIndexesByRef(indexes, ref);
            }
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

    /*
    * Find array in Local VM by LocalRef and change value by index = valueIndex
     */
    public LocalRef createLocalRef(CompositeValue value) {
        return new LocalRef(addToLocalVM(value));
    }

    public void clearAll() {
        List<Runnable> runnables = service.shutdownNow();
        for (Runnable runnable : runnables) {
            ((PSThread) runnable).getContext().clearAll();
        }
        localVM.clear();
        isGlobal = false;
/*todo remove*/
        systemDict = null;
/*todo*/

        bcGenManager = new BytecodeGeneratorManager();
        DynamicClassLoader.reset();


        PSObject.resetExecutionCounts();
//        executionCount = 0;

        nameVersions.clear();
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

   /* public void initDefaultDictionaries() {
        systemDict = new PSObject(DefaultDicts.getSystemDict(),
                Type.DICTIONARY,
                new Attribute(Attribute.Access.READ_ONLY, Attribute.TreatAs.LITERAL));
        pushToDictionaryStack(systemDict);
        globalDict = new PSObject(DefaultDicts.getGlobalDict());
        pushToDictionaryStack(globalDict);
        userDict = new PSObject(DefaultDicts.getUserDict());
        pushToDictionaryStack(userDict);
    }*/

    public void initSystemDict() {
        if (systemDict != null) return;
        systemDict = new PSObject(DefaultDicts.getSystemDict(),
                Type.DICTIONARY,
                new Attribute(Attribute.Access.READ_ONLY, Attribute.TreatAs.LITERAL));
    }

    public CompositeValue getValueByTableIndex(int tableIndex) {
        return localVM.get(tableIndex);
    }


    public int getNameVersion(String name) {
        Integer version = nameVersions.get(name);
        if (version == null) {
            version = -1;
            nameVersions.put(name, version);
        }
        return version;
    }

    public void updateNameVersions(String name) {
        int version = getNameVersion(name);
        nameVersions.put(name, version + 1);
    }

    public int getLocalVMSize() {
        return localVM.size();
    }

    public void addEvent(Event event) {
        eventQueue.add(event);
    }

    public void addPrimitive(PSPrimitive psPrimitive) {
        primitiveQueue.add(psPrimitive);
    }

    public PSObject getSystemDict() {
        if (systemDict == null) {
            initSystemDict();
        }
        return systemDict;
    }

    public void addContext(Context context) {
        contextSet.add(context);
    }

    public void removeContext(Context context) {
        if (context != mainContext) {
            contextSet.remove(context);
        }
    }



    /*    public Event getEvent(){
        return eventQueue.poll();
    }

    public boolean eventQueueIsEmpty(){
//        System.out.print("isEmpty ");
        return eventQueue.isEmpty();
    }*/
}