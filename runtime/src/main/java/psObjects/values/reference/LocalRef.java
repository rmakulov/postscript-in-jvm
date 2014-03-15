package psObjects.values.reference;


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
        try{
           return psObjectCompareTo(v);
        }   catch (Exception e){
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

    @Override
    public Type setCompositeValue(CompositeValue obj) {
        tableIndex = runtime.Runtime.getInstance().addToLocalVM(obj);
        return obj.determineType();
    }

    public CompositeValue getValue() {
        //todo in runtime
        return runtime.Runtime.getInstance().getValueByLocalRef(this);
    }

    public int localCompareTo(LocalRef localRef) {
        return tableIndex - localRef.getTableIndex();
    }

    @Override
    public int compareGrade() {
        return 5;
    }
}
