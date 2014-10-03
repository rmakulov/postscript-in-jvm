package psObjects.values.simple;

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
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void compile(PSObject obj) {
//        interpret(obj);


       /* //runtime.pushToOperandStack(new PSObject(new PSArray(new PSObject(new PSBytecode(this.strValue)))));
        runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/values/composite/PSArray");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/values/simple/PSBytecode");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitLdcInsn(this.strValue);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSBytecode", "<init>", "(Ljava/lang/String;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/composite/PSArray", "<init>", "(LpsObjects/PSObject;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);*/

        //old version
        //runtime.pushToOperandStack(new PSObject(new PSBytecode(this.strValue)));
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = runtime.bcGenManager.bytecodeName;
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/values/simple/PSBytecode");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitLdcInsn(this.strValue);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSBytecode", "<init>", "(Ljava/lang/String;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
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
