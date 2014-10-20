package operators.common;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

public class AloadOp extends Operator {

    public static final AloadOp instance = new AloadOp();

    protected AloadOp() {
        super();
    }

    @Override
    public void interpret() {
        PSObject psObject = runtime.popFromOperandStack();
        if (psObject == null)
            return;
        Type type = psObject.getType();
        if (type == Type.ARRAY || type == Type.PACKEDARRAY) {
            PSObject[] array = ((PSArray) psObject.getValue()).getArray();
//            System.out.print("Aload: ");
            for (PSObject object : array) {
                runtime.pushToOperandStack(object);
//                System.out.print(object.getDirectValue() + " ");
            }
//            System.out.println();
            runtime.pushToOperandStack(psObject);
//        } else if (psObject.isBytecode()){
//            psObject.interpret(0);
        } else if (psObject.isBytecode() && runtime.isCompiling) {
            runtime.setALoading(true);
            psObject.getValue().interpret(psObject);
//            psObject.interpret(0);
            runtime.setALoading(false);
            runtime.pushToOperandStack(psObject);

//            MethodVisitor mv = runtime.bcGenManager.mv;
//            String className = runtime.bcGenManager.bytecodeName;
//            mv.visitFieldInsn(GETSTATIC, className, "runtime", "Lruntime/Runtime;");
//            mv.visitInsn(ICONST_1);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "setALoading", "(Z)V", false);
//            psObject.getValue().interpret(psObject);
////            mv.visitVarInsn(ALOAD, 1);
////            mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "getValue", "()LpsObjects/values/Value;", false);
////            mv.visitVarInsn(ALOAD, 1);
////            mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/Value", "interpret", "(LpsObjects/PSObject;)Z", false);
////            mv.visitInsn(POP);
////            mv.visitFieldInsn(GETSTATIC, className, "runtime", "Lruntime/Runtime;");
////            mv.visitFieldInsn(GETFIELD, "operators/common/AloadOp", "runtime", "Lruntime/Runtime;");
//            mv.visitInsn(ICONST_0);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "setALoading", "(Z)V", false);
//            mv.visitFieldInsn(GETSTATIC, className, "runtime", "Lruntime/Runtime;");
//            mv.visitFieldInsn(GETFIELD, "operators/common/AloadOp", "runtime", "Lruntime/Runtime;");
//            mv.visitVarInsn(ALOAD, 1);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
        } else {
            fail();
            runtime.pushToOperandStack(psObject);
            return;
        }
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("aload");
    }
}
