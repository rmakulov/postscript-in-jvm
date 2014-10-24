package psObjects.values.simple;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import procedures.ArrayProcedure;
import procedures.StringProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.PSString;
import runtime.BytecodeGeneratorManager;
import runtime.DynamicClassLoader;
import runtime.Runtime;

import java.io.UnsupportedEncodingException;

import static psObjects.Attribute.TreatAs.EXECUTABLE;

public class PSName extends SimpleValue {


    protected String strValue;

    // private ArrayList<Integer> operatorIndexes = new ArrayList<Integer>();
    public PSName(String strValue) {
        this.strValue = strValue;
    }


    public String getStrValue() {
        return strValue;
    }

    @Override
    public boolean interpret(PSObject obj) {
        //operatorIndexes.add(2);
        PSObject value = runtime.findValue(obj);
        String procName = ((PSName) obj.getValue()).getStrValue();
        while (value.getType() == Type.NAME && !(value.isBytecode()) && value.treatAs() == EXECUTABLE) {
            procName = procName + " -> " + ((PSName) value.getValue()).getStrValue();
            value = runtime.findValue(value);
        }
        if (value.isProc()) {
            runtime.pushToCallStack(new ArrayProcedure(procName, value));
        } else if (value.isBytecode()) {
            return value.execute(0);
        } else if (value.getType() == Type.OPERATOR) {
            Operator operator = (Operator) value.getValue();
            return operator.interpret(null);
        } else if (value.getType() == Type.STRING && value.xcheck()) {
            try {
                runtime.pushToCallStack(new StringProcedure(((PSString) value.getValue()).getString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            runtime.pushToOperandStack(value);
        }
        //value cannot be a Mark
//        todo error handler
        return true;
    }

    public static void executiveCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
        PSObject obj = runtime.search(new PSObject(new PSName(strValue)));
        boolean isOperator = (obj != null && obj.getType() == Type.OPERATOR);
        BytecodeGeneratorManager bcGenManager = runtime.bcGenManager;

        String className = bcGenManager.bytecodeName;
        if (isOperator) {// && !bcGenManager.lastMethodIsEmpty()) {
            if (!bcGenManager.lastMethodIsEmpty()) {
                bcGenManager.endMethod();
                bcGenManager.startMethod();
            }
            saveSuspectOperatorIndex(obj, bcGenManager, className);
        }

        bcGenManager.incInstrCounter();
        writeExecutiveBytecode(strValue, className);

        if (isOperator) {
            bcGenManager.endMethod();
            bcGenManager.startMethod();
        }

    }

    private static void writeExecutiveBytecode(String strValue, String className) {
        MethodVisitor mv = Runtime.getInstance().bcGenManager.mv;
        // for bindOP operator: next 10 replacing instructions
        mv.visitFieldInsn(GETSTATIC, className, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSName");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(strValue);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSName", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        // here they ends
        Label l8 = new Label();
        mv.visitJumpInsn(IFNE, l8);
        mv.visitInsn(ICONST_0);
        mv.visitInsn(IRETURN);
        mv.visitLabel(l8);
    }

    private static void saveSuspectOperatorIndex(PSObject obj, BytecodeGeneratorManager bcGenManager, String className) {
        DynamicClassLoader l = DynamicClassLoader.instance;
        String operatorPath = obj.getValue().getClass().getCanonicalName().replace(".", "/");
        int classNumber = Integer.parseInt(className);
        int methodNumber = bcGenManager.methodNumber;
        l.putSuspectOperatorIndex(classNumber, methodNumber, operatorPath);
    }


    public static void literalCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
//        PSObject psObject = new PSObject(new PSName(strValue), LITERAL);
//        runtime.pushToOperandStack(psObject);
        String name = runtime.bcGenManager.bytecodeName;
        MethodVisitor mv = runtime.bcGenManager.mv;
        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSName");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(strValue);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSName", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitFieldInsn(GETSTATIC, "psObjects/Attribute$TreatAs", "LITERAL", "LpsObjects/Attribute$TreatAs;");
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;LpsObjects/Attribute$TreatAs;)V", false);
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        //mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
//
    }

    @Override
    public void compile(PSObject obj) {
        Attribute attribute = obj.getAttribute();
        Attribute.TreatAs treatAs = attribute.treatAs;
        if (treatAs == EXECUTABLE) {
            executiveCompile(strValue);
        } else {
            literalCompile(strValue);
        }
    }

    @Override
    public String toString() {
        return "PSName{" +
                "strValue='" + strValue + '\'' +
                "} ";
    }

    @Override
    public Type determineType() {
        return Type.NAME;
    }

    public Integer compareTo(Value o) {
        return o instanceof PSName ?
                strValue.compareTo(((PSName) o).getStrValue()) :
                super.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSName)) return false;

        PSName PSName = (PSName) o;

        if (!strValue.equals(PSName.strValue)) return false;

        return true;
    }

    @Override
    public String toStringView() {
        return "/" + strValue;
    }

    public int compareGrade() {
        return 0;
    }

    public int length() {
        return strValue.length();
    }
}
