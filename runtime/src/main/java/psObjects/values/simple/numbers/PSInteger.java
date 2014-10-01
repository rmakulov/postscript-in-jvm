package psObjects.values.simple.numbers;

import psObjects.PSObject;
import psObjects.Type;
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
    public String toStringView() {
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
    public void compile(PSObject obj) {
//        runtime.pushToOperandStack(new PSObject(new PSInteger(5)));
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = runtime.bcGenManager.bytecodeName;
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSInteger");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitLdcInsn(value);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSInteger", "<init>", "(I)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);

    }

    public static void compile(int value) {
        Runtime runtime = Runtime.getInstance();
        //runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        String name = runtime.bcGenManager.bytecodeName;
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSInteger");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitLdcInsn(value);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSInteger", "<init>", "(I)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }
}
