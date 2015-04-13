package psObjects.values.reference;

import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import runtime.Context;


public class GlobalRef extends Reference {
    private CompositeValue value;
    private int id;
    private static int lastId = 0;

    public GlobalRef(CompositeValue value) {
        this.value = value;
        id = lastId;
        lastId++;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public Type setCompositeValue(CompositeValue obj) {
        value = obj;
        return obj.determineType();
    }

    @Override
    public Integer compareTo(Value v) {
        try {
            return psObjectCompareTo(v);
        } catch (Exception e) {
            return v instanceof GlobalRef ?
                    Integer.valueOf(globalCompareTo((GlobalRef) v)) :
                    super.compareTo(v);
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            return psObjectEquals(o);
        } catch (Exception e) {
            Reference r = (Reference) o;
            if (r instanceof LocalRef) return false;
            return id == ((GlobalRef) r).getId();
        }
    }

    public int getId() {
        return id;
    }

    public int globalCompareTo(GlobalRef ref) {
        return id - ref.getId();
    }

    @Override
    public int compareGrade() {
        return 6;
    }

    @Override
    public void compile(Context context, PSObject obj) {
        context.putCvxGlobalObject(id, obj);
//        runtime.pushToOperandStack(runtime.getCVXGlobalObject(id));

        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;

        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitLdcInsn(id);
        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "getCVXGlobalObject", "(I)LpsObjects/PSObject;", false);
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);
        checkExitCompile(context);
    }
}
