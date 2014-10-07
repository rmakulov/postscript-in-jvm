package runtime;

import runtime.avl.Pair;

import java.util.HashMap;

public class DynamicClassLoader extends ClassLoader {
    public static DynamicClassLoader instance = new DynamicClassLoader();
    private final HashMap<String, byte[]> bytecodes = new HashMap<String, byte[]>();
    // <<ClassName, MethodName>,OperatorName>
    private HashMap<Pair<Integer, Integer>, String> suspectOperatorIndexes = new HashMap<Pair<Integer, Integer>, String>();

    private DynamicClassLoader() {
        super();
    }

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void putClass(String name, byte[] bytecode) {
        bytecodes.put(name, bytecode);
    }

    public HashMap<String, byte[]> getBytecodes() {
        return bytecodes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //remove here, because the class won't be loaded another time anyway
        byte[] bytecode = bytecodes.remove(name);
        if (bytecode != null) {
            return defineClass(name, bytecode, 0, bytecode.length);
        } else {
            return super.findClass(name);
        }
    }

    public void putSuspectOperatorIndex(int className, int methodName, String operatorPath) {
        suspectOperatorIndexes.put(new Pair<Integer, Integer>(className, methodName), operatorPath);
    }

    public String getSuspectOperatorName(int className, int methodName) {
        return suspectOperatorIndexes.get(new Pair<Integer, Integer>(className, methodName));
    }

}