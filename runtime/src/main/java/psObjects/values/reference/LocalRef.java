package psObjects.values.reference;


import org.objectweb.asm.MethodVisitor;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;

public class LocalRef extends Reference {
    private int tableIndex;

    public LocalRef(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    @Override
    public Integer compareTo(Value v) {
        try {
            return psObjectCompareTo(v);
        } catch (Exception e) {
            return v instanceof LocalRef ?
                    Integer.valueOf(localCompareTo((LocalRef) v)) :
                    super.compareTo(v);
        }
    }

    @Override
    public boolean equals(Object o) {
        try {
            return psObjectEquals(o);
        } catch (Exception e) {
            Reference r = (Reference) o;
            if (r instanceof GlobalRef) return false;
            return tableIndex == ((LocalRef) r).getTableIndex();
        }
    }

    @Override
    public int hashCode() {
        return tableIndex;
    }

/*    @Override
    public Type setCompositeValue(CompositeValue obj) {
        tableIndex = runtime.Runtime.getInstance().addToLocalVM(obj);
        return obj.determineType();
    }*/

    @Override
    public Type setCompositeValue(CompositeValue obj) {
        tableIndex = runtime.setNewValueAtLocalVM(tableIndex, obj);
        return obj.determineType();
    }

    @Override
    public void compile(PSObject obj) {
        Attribute attribute = obj.getAttribute();
        int attributeIndex = attribute.getAttributeTypeIndex();

      /*  PSObject psObject = new PSObject(new LocalRef(tableIndex), Attribute.getAttributeByIndex(attributeIndex));
        runtime.pushToOperandStack(psObject);*/

        String name = runtime.bcGenManager.bytecodeName;
        MethodVisitor mv = runtime.bcGenManager.mv;

        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/reference/LocalRef");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(tableIndex);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/reference/LocalRef", "<init>", "(I)V", false);
        mv.visitLdcInsn(attributeIndex);
        mv.visitMethodInsn(INVOKESTATIC, "psObjects/Attribute", "getAttributeByIndex", "(I)LpsObjects/Attribute;", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;LpsObjects/Attribute;)V", false);
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        checkExitCompile();
//        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
    }

    public CompositeValue getValue() {
        return runtime.getValueByLocalRef(this);
    }

    public int localCompareTo(LocalRef localRef) {
        return tableIndex - localRef.getTableIndex();
    }

    @Override
    public int compareGrade() {
        return 5;
    }

    @Override
    public String toString() {
        return "LocalRef{" +
                "tableIndex=" + tableIndex +
                ", refValue= " + getValue().toString() + "}";
    }
}
