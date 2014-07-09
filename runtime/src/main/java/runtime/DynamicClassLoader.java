package runtime;

import java.util.HashMap;

public class DynamicClassLoader extends ClassLoader {
    public static DynamicClassLoader instance = new DynamicClassLoader();
    private final HashMap<String, byte[]> bytecodes = new HashMap<String, byte[]>();

    private DynamicClassLoader() {
        super();
    }

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    public void putClass(String name, byte[] bytecode) {
        bytecodes.put(name, bytecode);
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
}