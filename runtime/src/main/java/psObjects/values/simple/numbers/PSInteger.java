package psObjects.values.simple.numbers;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;
import runtime.Context;
import runtime.Runtime;

import java.util.Random;

public class PSInteger extends PSNumber {
    private int value = 0;

    // Generate random int and add it into local VM
    public PSInteger() {

    }

    public PSInteger(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PSInteger{" +
                "value=" + value +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSInteger)) return false;

        PSInteger psInteger = (PSInteger) o;

        if (value != psInteger.value) return false;

        return true;
    }

    @Override
    public String toStringView(PSObject object) {
        return value + "";
    }


    /*
    @Override
    public int hashCode() {
        return this.value;
    }
*/

    public static PSInteger initInteger() {
        Random rand = new Random();
        int value = rand.nextInt();
        return new PSInteger(value);
    }

    public int getIntValue() {
        return value;
    }

    @Override
    public double getRealValue() {
        return (double) value;
    }

    public static PSInteger add(PSInteger i1, PSInteger i2) {
        return new PSInteger(i1.value + i2.value);
    }

    @Override
    public Type determineType() {
        return Type.INTEGER;
    }

    @Override
    public void compile(Context context, PSObject obj) {
//        runtime.pushToOperandStack(new PSObject(new PSInteger(5)));
        //(new PSObject(new PSInteger(5))).interpret(0);
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSInteger");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(value);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSInteger", "<init>", "(I)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);

        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);
        checkExitCompile(context);

    }

    public static void compile(Context context, int value) {
        Runtime runtime = Runtime.getInstance();
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");

        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSInteger");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(value);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSInteger", "<init>", "(I)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);

        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }
}
