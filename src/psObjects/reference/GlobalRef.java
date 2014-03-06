package psObjects.reference;

import psObjects.PSObject;


public class GlobalRef extends Reference {
    private PSObject psObject;
    private int id;
    private static int lastId = 0;

    public GlobalRef(PSObject psObject) {
        this.psObject = psObject;
        id = lastId;
        lastId++;
    }

    @Override
    public PSObject getPSObject() {
        return psObject;
    }

    @Override
    public void setPSObject(PSObject obj) {
        psObject = obj;
    }

    @Override
    public int compareTo(Reference r) {
        try{
           return psObjectCompareTo(r);
        }   catch (Exception e){
        if(r instanceof  LocalRef) return 1; // we think global reference is more than local one.
        return globalCompareTo((GlobalRef) r);
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

    public int globalCompareTo(GlobalRef ref){
        return id-ref.getId();
    }
}
