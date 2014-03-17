package psObjects;

import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSString;
import psObjects.values.composite.dictionaries.PSDictionary;
import psObjects.values.reference.GlobalRef;
import psObjects.values.reference.LocalRef;
import psObjects.values.reference.Reference;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;

public class PSObject implements Comparable<PSObject> {
    private Value value;
    private Type type;
    private Attribute attribute;

    public Value getValue() {
        return value.getValue();
    }

    public Attribute.TreatAs treatAs() {
        return attribute.treatAs;
    }

    public Value getDirectValue() {
        return value;
    }

    public PSObject(Value value, Type type, Attribute attribute) {
        if ((value.determineType() == Type.ARRAY && type == Type.PACKEDARRAY)) {
            value = runtime.Runtime.getInstance().createReference((CompositeValue) value);
            this.value = value;
            this.type = type;
            this.attribute = new Attribute(Attribute.Access.READ_ONLY, attribute.treatAs);
            return;
        } else if (value.determineType() != type) {
            //todo throw exception
            return;
        }
        if (value instanceof CompositeValue) {
            value = runtime.Runtime.getInstance().createReference((CompositeValue) value);
        }
        this.value = value;
        this.type = type;
        this.attribute = attribute;
    }

    public PSObject(Value value) {
        if (value instanceof CompositeValue) {
            value = runtime.Runtime.getInstance().createReference((CompositeValue) value);
        }
        this.value = value;
        type = value.determineType();
        if (isComposite() || type == Type.NAME) attribute = new Attribute();
    }

    public PSObject(Value value, Attribute.TreatAs treatAs) {
        if (value instanceof CompositeValue) {
            value = runtime.Runtime.getInstance().createReference((CompositeValue) value);
        }
        this.value = value;
        type = value.determineType();
        if (isComposite()) attribute = new Attribute(treatAs);
    }


    public boolean isComposite() {
        switch (type) {
            case NAME:
            case INTEGER:
            case REAL:
            case BOOLEAN:
            case FONT_ID:
            case NULL:
            case MARK:
            case OPERATOR:
                return false;
            default:
                return true;
        }
    }

    public boolean isGeneralComposite() {
        switch (type) {
            case ARRAY:
            case PACKEDARRAY:
            case FILE:
            case STRING:
            case DICTIONARY:
                return true;
            default:
                return false;
        }
    }

    public boolean isExecutableType() {
        switch (type) {
            case ARRAY:
            case PACKEDARRAY:
            case FILE:
            case STRING:
                return true;
            default:
                return false;
        }
    }

    public boolean isComparable() {
        switch (type) {
            case NULL:
            case MARK:
            case OPERATOR:
                return false;
            default:
                return true;
        }
    }

    public boolean isInLocalVM() {
        return isComposite() && value instanceof LocalRef;
    }

    public Integer getIndexInLocalVM() {
        if (!isInLocalVM()) return null;
        return ((LocalRef) value).getTableIndex();
    }

    public void setIndexInLocalVM(Integer index) {
        if (index == null) return;
        if (!isInLocalVM()) return;
        ((LocalRef) value).setTableIndex(index);

    }

    public boolean isNumber() {
        return type == Type.INTEGER || type == Type.REAL;
    }

    public Type getType() {
        return type;
    }

    public String type() {
        return type.toString().toLowerCase() + "type";
    }

    //convert to executable
    public PSObject cvx() {
        Attribute newAttr = new Attribute(attribute.access, Attribute.TreatAs.EXECUTABLE);
        return new PSObject(value, type, newAttr);
    }

    //convert to literal
    public PSObject cvlit() {
        if (attribute.access == Attribute.Access.EXECUTE_ONLY) {
            return null;
        }
        Attribute newAttr = new Attribute(attribute.access, Attribute.TreatAs.LITERAL);
        return new PSObject(value, type, newAttr);
    }

    // check executable attribute
    public boolean xcheck() {
        if (!isGeneralComposite()) {
            return false;
        }
        return attribute.treatAs == Attribute.TreatAs.EXECUTABLE;
    }


    // check read attribute
    public boolean rcheck() {
        if (!isGeneralComposite()) {
            return false;
        }
        return attribute.access == Attribute.Access.READ_ONLY;
    }

    public boolean gcheck() {
        return !isComposite() || value instanceof GlobalRef;
    }


    // check write attribute
    public boolean wcheck() {
        return attribute.access == Attribute.Access.UNLIMITED;
    }


    public PSObject noaccess() {
        if (!isGeneralComposite()) {
            return null;
        }
        Attribute newAttr = new Attribute(Attribute.Access.NONE, attribute.treatAs);
        return new PSObject(value, type, newAttr);
    }

    public PSObject executeonly() {
        if (!isExecutableType() && xcheck()) {
            return null;
        }
        Attribute newAttr = new Attribute(Attribute.Access.EXECUTE_ONLY, attribute.treatAs);
        return new PSObject(value, type, newAttr);
    }

    public PSObject readOnly() {
        if (!isGeneralComposite()) {
            return null;
        }
        Attribute newAttr = new Attribute(Attribute.Access.READ_ONLY, attribute.treatAs);
        return new PSObject(value, type, newAttr);
    }


    public static PSObject initString() {
        return new PSObject(PSString.initString());
    }

    public static PSObject initInteger() {
        return new PSObject(PSInteger.initInteger());
    }

    public static PSObject initArray(int n) {
        return new PSObject(PSArray.initArray(n));
    }

    public static PSObject initDict(int n) {
        return new PSObject(PSDictionary.initDict(n));
    }

    //do not return new object. Method just changes it
    public PSObject setValue(CompositeValue newValue) {
        if (!isComposite() || !wcheck()) {
            //todo throw exception
            return null;
        }
        Reference ref = (Reference) value;
        Type valueType = newValue.determineType();
        if (type == Type.PACKEDARRAY && valueType == Type.ARRAY) {
            // type doesn't change
        } else {
            type = valueType;
        }
        ref.setCompositeValue(newValue);
        return this;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int compareTo(PSObject o) {
        return value.compareTo(o.getDirectValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSObject)) return false;

        PSObject psObject = (PSObject) o;
        if (type == Type.NAME && xcheck()) {
            PSObject obj = runtime.Runtime.getInstance().findValue(this);
            return psObject.equals(obj);
        }

        if (psObject.getType() == Type.NAME && psObject.xcheck()) {
            PSObject obj = runtime.Runtime.getInstance().findValue(psObject);
            return this.equals(obj);
        }

        if (value != null ? !value.equals(psObject.value) : psObject.value != null) return false;

        return true;
    }

    //convert PSString to PSName
    public PSObject cvn() {
        if (type != Type.STRING) {
            return null;
        }
        String str = ((PSString) value.getValue()).getString();
        if (str.length() > 127) str = str.substring(0, 127);
        return new PSObject(new PSName(str));
    }

    public boolean isProc() {
        return (type == Type.ARRAY) || (type == Type.PACKEDARRAY) && xcheck();
    }


    public boolean isDictKey() {
        switch (type) {
            case NULL:
            case MARK:
            case OPERATOR:
                return false;
            default:
                return true;
        }
    }
}
