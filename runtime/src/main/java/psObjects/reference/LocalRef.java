package psObjects.reference;


import psObjects.PSObject;

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
    public int compareTo(Reference r) {
        try {
            return psObjectCompareTo(r);
        } catch (Exception e) {
            if (r instanceof GlobalRef) return -1; // we think global reference is more than local one.
            return localCompareTo((LocalRef) r);
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
    public void setPSObject(PSObject obj) {
        tableIndex = runtime.Runtime.getInstance().addToLocalVM(obj);
    }

    public PSObject getPSObject() {
        return runtime.Runtime.getInstance().getPSObjectByLocalRef(this);
    }

    public int localCompareTo(LocalRef localRef) {
        return tableIndex - localRef.getTableIndex();
    }
}
