package psObjects.values.simple.numbers;

import psObjects.PSObject;
import psObjects.Type;
import runtime.Runtime;

public class PSReal extends PSNumber {
    private double value;

    public PSReal(double value) {
        this.value = value;
    }

    @Override
    public double getRealValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PSReal{" +
                "value=" + value +
                "}";
    }

    @Override
    public Type determineType() {
        return Type.REAL;
    }

    @Override
    public void compile(PSObject obj) {
        runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGen.mv.visitInsn(DUP);
        runtime.bcGen.mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSReal");
        runtime.bcGen.mv.visitInsn(DUP);
        runtime.bcGen.mv.visitLdcInsn(value);
        runtime.bcGen.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSReal", "<init>", "(I)V", false);
        runtime.bcGen.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }

    @Override
    public String toStringView() {
        return value + "";
    }

    public static void compile(double value) {
        Runtime runtime = Runtime.getInstance();
//        runtime.pushToOperandStack(new PSObject(new PSReal(value)));
        runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGen.mv.visitInsn(DUP);
        runtime.bcGen.mv.visitTypeInsn(NEW, "psObjects/values/simple/numbers/PSReal");
        runtime.bcGen.mv.visitInsn(DUP);
        runtime.bcGen.mv.visitLdcInsn(value);
        runtime.bcGen.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/numbers/PSReal", "<init>", "(D)V", false);
        runtime.bcGen.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }
}
