package psObjects.reference;

import psObjects.PSObject;
import psObjects.composite.CompositeObject;
import psObjects.composite.PSDictionary;
import psObjects.composite.PSString;
import psObjects.composite.Snapshot;
import psObjects.simple.PSNumber;
import psObjects.simple.SimpleObject;


public abstract class Reference implements Comparable<Reference> {
    public abstract PSObject getPSObject();

    public abstract int compareTo(Reference r);

    public abstract void setPSObject(PSObject obj);

    public boolean isDictionaryRef() {
        return getPSObject() instanceof PSDictionary;
    }

    public boolean isSnapshotRef() {
        return getPSObject() instanceof Snapshot;
    }

    public boolean isCompositeRef() {
        return getPSObject() instanceof CompositeObject;
    }

    public boolean isSimpleRef() {
        return getPSObject() instanceof SimpleObject;
    }

    protected int psObjectCompareTo(Reference r) throws Exception {
        PSObject thisPSObject = getPSObject();
        PSObject refPSObject = r.getPSObject();
        if (thisPSObject instanceof PSNumber && refPSObject instanceof PSNumber) {
            PSNumber thisNumber = (PSNumber) thisPSObject;
            PSNumber refNumber = (PSNumber) refPSObject;
            return thisNumber.compareTo(refNumber);
        }
        if (thisPSObject instanceof PSString && refPSObject instanceof PSString) {
            PSString thisString = (PSString) thisPSObject;
            PSString refString = (PSString) refPSObject;
            return thisString.compareTo(refString);
        }
        throw new Exception();
    }

    protected boolean psObjectEquals(Object o) throws Exception {
        if (!(o instanceof Reference)) return false;
        Reference r = (Reference) o;
        PSObject thisPSObject = getPSObject();
        PSObject refPSObject = r.getPSObject();
        if (thisPSObject instanceof PSNumber && refPSObject instanceof PSNumber) {
            PSNumber thisNumber = (PSNumber) thisPSObject;
            PSNumber refNumber = (PSNumber) refPSObject;
            return thisNumber.equals(refNumber);
        }
        if (thisPSObject instanceof PSString && refPSObject instanceof PSString) {
            PSString thisString = (PSString) thisPSObject;
            PSString refString = (PSString) refPSObject;
            return thisString.equals(refString);
        }
        throw new Exception();
    }
}
