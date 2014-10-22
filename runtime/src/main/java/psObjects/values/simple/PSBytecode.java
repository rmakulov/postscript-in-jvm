package psObjects.values.simple;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import runtime.DynamicClassLoader;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by User on 13/08/2014.
 */
public class PSBytecode extends PSName {

    public PSBytecode(String strValue) {
        super(strValue);
    }

    @Override
    public boolean interpret(PSObject obj) {
        try {
            Class c = DynamicClassLoader.instance.loadClass(strValue);
            return (Boolean) c.getMethod("run").invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Bytecode# " + strValue + " is so wrong!");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void compile(PSObject obj) {
        //runtime.pushToOperandStack(new PSObject(new PSBytecode(this.strValue)));
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = runtime.bcGenManager.bytecodeName;
        MethodVisitor mv = runtime.bcGenManager.mv;
        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSBytecode");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(this.strValue);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSBytecode", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
//        mv.visitInsn(ICONST_0);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
        //runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/simple/PSBytecode", "interpret", "(LpsObjects/PSObject;)V", false);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toStringView() {
        return "bytecode{#" + strValue + "}";
    }

}
