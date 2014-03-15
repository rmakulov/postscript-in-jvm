package psObjects;

/**
 * Created by Дмитрий on 14.03.14.
 */
public class Attribute {
    public enum Access {
        UNLIMITED, READ_ONLY, EXECUTE_ONLY, NONE
    }

    public enum TreatAs {
        EXECUTABLE, LITERAL
    }

    public Access access = Access.UNLIMITED;
    public TreatAs treatAs = TreatAs.EXECUTABLE;

    public Attribute() {
    }

    public Attribute(Access access, TreatAs treatAs) {
        this.access = access;
        this.treatAs = treatAs;
    }
}
