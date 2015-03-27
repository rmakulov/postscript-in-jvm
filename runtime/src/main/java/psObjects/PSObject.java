package psObjects;

import org.objectweb.asm.Opcodes;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSArray;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.reference.GlobalRef;
import psObjects.values.reference.LocalRef;
import psObjects.values.reference.Reference;
import psObjects.values.simple.PSBytecode;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;
import runtime.Runtime;

//import psObjects.values.simple.BytecodeProc;

public class PSObject implements Comparable<PSObject>, Opcodes {
    private Value value;
    private Type type;
    private Attribute attribute;
    private runtime.Runtime runtime = Runtime.getInstance();

    private static int objExecutionCounter = 0;
    private int executionsBeforeGarbageCleaning = 10000;
    private int maxLocalVMSize = 200;

    public boolean execute(Context context, int procDepth) {
        if (!runtime.isCompiling || runtime.bcGenManager.isSleep()) {
            return interpret(context, procDepth);
        } else {
            compile(context);
            return true;
        }

    }

    public boolean interpret(Context context, int procDepth) {
//        cleanGarbageByExecutionCounter();
//        cleanGarbageByLocalVMSize();
        boolean aLoading = context.getALoading();
        if ((attribute.treatAs == Attribute.TreatAs.LITERAL || procDepth > 0 || aLoading)
                /*&& !(procDepth == 1 && getValue().equals(PSMark.CLOSE_CURLY_BRACE))*/) {
            context.pushToOperandStack(this);
            return true;
        } else {
            return value.interpret(context, this);
        }
    }

    private void cleanGarbageByExecutionCounter() {
        objExecutionCounter++;
        if (objExecutionCounter % executionsBeforeGarbageCleaning == 0) {
//            System.out.println("Local vm argsCount before gc " + runtime.getLocalVMSize());
            runtime.cleanGarbage();
//            System.out.println("Local vm argsCount after gc " + runtime.getLocalVMSize());

        }
    }

    private void cleanGarbageByLocalVMSize() {
        int size = runtime.getLocalVMSize();
        if (size % maxLocalVMSize == 0) {
//            System.out.println("Local vm argsCount before gc " + size);
            runtime.cleanGarbage();
//            System.out.println("Local vm argsCount after gc " + runtime.getLocalVMSize());

        }
    }

    public void compile(Context context) {
        value.compile(context, this);
    }

    public Value getValue() {
        return value.getValue();
    }

    public Attribute.TreatAs treatAs() {
        return attribute.treatAs;
    }

    public PSObject copy() {
        return new PSObject(value, type, attribute);
    }

    public Value getDirectValue() {
        return value;
    }

    public PSObject(Value value, Type type, Attribute attribute) {
        if ((value.determineType() == Type.ARRAY && type == Type.PACKEDARRAY)) {
            value = runtime.createReference((CompositeValue) value);
            this.value = value;
            this.type = type;
            this.attribute = new Attribute(Attribute.Access.READ_ONLY, attribute.treatAs);
            return;
        } else if (value.determineType() != type) {
            //todo throw exception
            return;
        }
        if (value instanceof CompositeValue) {
            value = runtime.createReference((CompositeValue) value);
        }
        this.value = value;
        this.type = type;
        this.attribute = attribute;
    }

    public PSObject(Value value, Attribute attribute) {
        this.value = value;
        type = value.determineType();
        this.attribute = attribute;
    }

    public PSObject(Value value) {
        if (value instanceof CompositeValue) {
            value = runtime.createReference((CompositeValue) value);
        }
        this.value = value;
        type = value.determineType();
        //if (isComposite() || type == Type.NAME)
        attribute = new Attribute();

    }

    public PSObject(Value value, Attribute.TreatAs treatAs) {
        if (value instanceof CompositeValue) {
            value = runtime.createReference((CompositeValue) value);
        }
        this.value = value;
        type = value.determineType();
        if (isComposite() || type == Type.NAME) attribute = new Attribute(treatAs);
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
        if (type != newValue.determineType()) {
            System.out.println("wrong set value");
            System.exit(0);
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
        return new PSObject(new PSName(str), Attribute.TreatAs.EXECUTABLE);
    }

    public boolean isProc() {
        return ((type == Type.ARRAY) || (type == Type.PACKEDARRAY)) && xcheck();
    }

    public boolean isBytecode() {
        return value instanceof PSBytecode;
    }//|| value instanceof BytecodeProc;}

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

    public boolean isExecutableString() {
        return type == Type.STRING && xcheck();
    }

    public boolean isFile() {
        return type == Type.FILE;
    }

    @Override
    public String toString() {
        return "PSObject{" +
                "value=" + value +
//                ", type=" + type +
//                ", attribute=" + attribute +
                '}';
    }

    /*public boolean isBytecodeProc() {
        return value instanceof BytecodeProc;
    }*/

//    public void deepCompile() {
//        value.deepCompile(this);
//    }

    public String toStringView() {
        return getValue().toStringView(this);
    }

    public boolean isMatrix() {
        return type == Type.ARRAY && ((PSArray) getValue()).length() == 6;
    }

    public boolean isExecutable() {
        return attribute.treatAs == Attribute.TreatAs.EXECUTABLE;
    }

    public static void resetExecutionCounts() {
        objExecutionCounter = 0;
    }

    public boolean isString() {
        return type == Type.STRING;
    }

    public boolean isInteger() {
        return type == Type.INTEGER;
    }

    public boolean isName() {
        return type == Type.NAME;
    }
}
