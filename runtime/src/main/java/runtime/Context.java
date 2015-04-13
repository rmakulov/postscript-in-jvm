package runtime;

import operators.DefaultDicts;
import procedures.Procedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;
import psObjects.values.reference.GlobalRef;
import psObjects.values.simple.PSName;
import psObjects.values.simple.PSNull;
import runtime.compiler.BytecodeGeneratorManager;
import runtime.graphics.GState;
import runtime.stack.CallStack;
import runtime.stack.DictionaryStack;
import runtime.stack.GraphicStack;
import runtime.stack.OperandStack;

import java.util.HashMap;

/**
 * Created by User on 20/3/2015.
 */
public class Context {

    private OperandStack operandStack = new OperandStack();
    private DictionaryStack dictionaryStack = new DictionaryStack();
    private GraphicStack graphicStack = new GraphicStack();
    private CallStack callStack = new CallStack();
    public BytecodeGeneratorManager bcGenManager = new BytecodeGeneratorManager();
    private HashMap<String, Integer> nameVersions = new HashMap<String, Integer>();


    private PSObject userDict, globalDict, systemDict;


    //todo transfer these fields into context for compiling mode
    //costyl#1
    private HashMap<Integer, PSObject> cvxGlobalObjectMap = new HashMap<Integer, PSObject>();
    private boolean ALoading = false;
    private int id;

    public void pushToOperandStack(PSObject psObject) {
        //System.out.println("\t\t\t\t\t\t\t\t\t\t\t" + psObject.getValue().toString());
        if (!(psObject == null)) {
            operandStack.push(psObject);
        }
    }

    public PSObject popFromOperandStack() {
        PSObject object = operandStack.peek();
        if (object == null)
            return null;
        return operandStack.pop();
    }

    public DictionaryStack getDictionaryStack() {
        return dictionaryStack;
    }

    public CallStack getCallStack() {
        return callStack;
    }

    public void pushToGraphicStack(GState gsave) {
        graphicStack.push(gsave);
    }

    public void removeFromGraphicStack() {
        if (graphicStack.size() == 1) {
            graphicStack.clear();
        } else {
            graphicStack.pop();
        }
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
//                executionCount++;
                topProcedure.execNext();
//                if (executionCount % executionsBeforeGarbageCleaning == 0) {
////                    System.out.println("Local vm argsCount before gc " + localVM.argsCount());
//                    cleanGarbage();
////                    System.out.println("Local vm argsCount after gc " + localVM.argsCount());
//                }
            } else {
                topProcedure.procTerminate();
                popFromCallStack();
            }
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

    public GraphicStack getGraphicStack() {
        return graphicStack;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public GState getGState() {
        if (graphicStack.isEmpty()) {
            graphicStack.init();
        }
        return graphicStack.peek();
    }

    public String operandStackToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PSObject psObject : operandStack) {
            stringBuilder.append(psObject.toStringView()).append(" ");
        }
        return stringBuilder.toString().trim().concat("\n");
    }

    public PSObject currentDict() {
        return dictionaryStack.peek();
    }

    public int getGraphicStackSize() {
        return graphicStack.size();
    }

    public int getOperandStackSize() {
        return operandStack.size();
    }

    public int getDictionaryStackSize() {
        return dictionaryStack.size();
    }

    //clearOperandStack operandStack;
    public void clearOperandStack() {
        operandStack.clear();
    }

    public PSObject getCVXGlobalObject(int id) {
        return cvxGlobalObjectMap.get(id);
    }

    public void putCvxGlobalObject(int id, PSObject obj) {
        if (!(obj.getDirectValue() instanceof GlobalRef)) {
            try {
                throw new Exception("attempt put not global object");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cvxGlobalObjectMap.put(id, obj);
    }

    public void setALoading(boolean ALoading) {
        this.ALoading = ALoading;
    }

    public boolean getALoading() {
        return ALoading;
    }

    public void clearCallStack() {
        callStack.clear();
    }

    public void clearDictionaryStack() {
        while (removeFromDictionaryStack()) {
            //removing done in condition automatically
        }
    }

    //    dict key get any Return value associated with key in dict
    public PSObject getValueAtDictionary(PSObject dictObject, PSObject key) {
        return ((PSDictionary) dictObject.getValue()).get(key);
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
//            e.printStackTrace();
            System.err.println(e);
        }
        return new PSObject(PSNull.NULL);
    }

    public PSObject search(PSObject key) {
        PSObject found;
        for (PSObject dictObj : dictionaryStack) {
            found = getValueAtDictionary(dictObj, key);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /*called through bytecode*/
    public PSObject findValue(String str) {
        return findValue(new PSObject(new PSName(str)));
    }

    public boolean checkIsOperator(String str) {
        PSObject obj = search(new PSObject(new PSName(str)));
        return (obj != null && obj.getType() == Type.OPERATOR);
    }

    public void initDictionaries(PSObject systemDict) {
        this.systemDict = systemDict;
        pushToDictionaryStack(systemDict);
        globalDict = new PSObject(DefaultDicts.getGlobalDict());
        pushToDictionaryStack(globalDict);
        userDict = new PSObject(DefaultDicts.getUserDict());
        pushToDictionaryStack(userDict);
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

    public int getDictStackVersion() {
        return dictionaryStack.getVersion();
    }

    public void clearAll() {
        operandStack.clear();
        graphicStack.clear();
        dictionaryStack.clear();


        userDict = null;
        systemDict = null;
        globalDict = null;

        bcGenManager = new BytecodeGeneratorManager();
        nameVersions.clear();

    }

    public void init() {
        initDictionaries(Runtime.getInstance().getSystemDict());
        graphicStack.init();
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

    public BytecodeGeneratorManager getBcGenManager() {
        return bcGenManager;
    }

    public HashMap<String, Integer> getNameVersions() {
        return nameVersions;
    }

    public void setNameVersions(HashMap<String, Integer> nameVersions) {
        this.nameVersions = nameVersions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
