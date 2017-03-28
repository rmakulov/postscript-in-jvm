package psObjects;

/**
 * Created by Дмитрий on 14.03.14.
 */
public class Attribute {
    public Access access = Access.UNLIMITED;
    public TreatAs treatAs = TreatAs.EXECUTABLE;

    public Attribute() {
    }

    public Attribute(Access access, TreatAs treatAs) {
        this.access = access;
        this.treatAs = treatAs;
    }

    public Attribute(TreatAs treatAs) {
        this.treatAs = treatAs;
    }

    public enum Access {
        UNLIMITED, READ_ONLY, EXECUTE_ONLY, NONE
    }

    public enum TreatAs {
        EXECUTABLE, LITERAL
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "access=" + access +
                ", treatAs=" + treatAs +
                '}';
    }

    public int getAttributeTypeIndex() {
        int accessIndex = 0, treatAsIndex = 0;
        switch (access) {
            case UNLIMITED:
                accessIndex = 0;
                break;
            case READ_ONLY:
                accessIndex = 1;
                break;
            case EXECUTE_ONLY:
                accessIndex = 2;
                break;
            case NONE:
                accessIndex = 3;
                break;
        }
        switch (treatAs) {
            case EXECUTABLE:
                treatAsIndex = 0;
                break;
            case LITERAL:
                treatAsIndex = 1;
                break;
        }
        return accessIndex + 4 * treatAsIndex;
    }

    public static Attribute getAttributeByIndex(int index) {
        switch (index) {
            case 0:
                return new Attribute(Access.UNLIMITED, TreatAs.EXECUTABLE);
            case 1:
                return new Attribute(Access.READ_ONLY, TreatAs.EXECUTABLE);
            case 2:
                return new Attribute(Access.EXECUTE_ONLY, TreatAs.EXECUTABLE);
            case 3:
                return new Attribute(Access.NONE, TreatAs.EXECUTABLE);
            case 4:
                return new Attribute(Access.UNLIMITED, TreatAs.LITERAL);
            case 5:
                return new Attribute(Access.READ_ONLY, TreatAs.LITERAL);
            case 6:
                return new Attribute(Access.EXECUTE_ONLY, TreatAs.LITERAL);
            case 7:
                return new Attribute(Access.NONE, TreatAs.LITERAL);
            default:
                return new Attribute();
        }
    }
}
