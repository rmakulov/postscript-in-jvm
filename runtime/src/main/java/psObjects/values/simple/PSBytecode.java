package psObjects.values.simple;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import runtime.Context;
import runtime.compiler.DynamicClassLoader;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by User on 13/08/2014.
 */
public class PSBytecode extends PSName {

    public PSBytecode(String strValue) {
        super(strValue);
    }

    @Override
    public boolean interpret(Context context, PSObject obj) {
        try {
            Class c = DynamicClassLoader.instance.loadClass(strValue);

            c.getField("context").set(null, context);
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void compile(Context context, PSObject obj) {
        //runtime.pushToOperandStack(new PSObject(new PSBytecode(this.strValue)));
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;
//        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSBytecode");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(strValue);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSBytecode", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);

        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);

        checkExitCompile(context);
//        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
        //runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/simple/PSBytecode", "interpret", "(LpsObjects/PSObject;)V", false);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toStringView(PSObject object) {
        return "bytecode{#" + strValue + "}";
    }

    public void setContext(Context context) {

    }
}
