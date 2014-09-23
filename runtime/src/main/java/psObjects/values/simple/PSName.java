package psObjects.values.simple;

import org.objectweb.asm.Label;
import procedures.ArrayProcedure;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import runtime.Runtime;

import static psObjects.Attribute.TreatAs.EXECUTABLE;

public class PSName extends SimpleValue {


    protected String strValue;

    public PSName(String strValue) {
        this.strValue = strValue;
    }


    public String getStrValue() {
        return strValue;
    }

    @Override
    public boolean interpret(PSObject obj) {
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
            runtime.pushToCallStack(new StringProcedure(procName, value));
        } else {
            runtime.pushToOperandStack(value);
        }
        //value cannot be a Mark
//        todo error handler
        return true;
    }

/*    @Override
    public void compile(PSObject obj) {
//          begin  Runtime.getInstance().findValue(str).interpret(0);
        runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitLdcInsn(strValue);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "findValue", "(Ljava/lang/String;)LpsObjects/PSObject;", false);
        runtime.bcGen.mv.visitInsn(ICONST_0);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
//          end  Runtime.getInstance().findValue(str).interpret(0);
    }*/

    public static void executiveCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
//          begin  Runtime.getInstance().findValue(str).interpret(0);
        runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitLdcInsn(strValue);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "findValue", "(Ljava/lang/String;)LpsObjects/PSObject;", false);
        runtime.bcGen.mv.visitInsn(ICONST_0);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        Label l8 = new Label();
        runtime.bcGen.mv.visitJumpInsn(IFNE, l8);
        runtime.bcGen.mv.visitInsn(ICONST_0);
        runtime.bcGen.mv.visitInsn(IRETURN);
        runtime.bcGen.mv.visitLabel(l8);


//          end  Runtime.getInstance().findValue(str).interpret(0);
    }


    public static void literalCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
//        PSObject psObject = new PSObject(new PSName(strValue), LITERAL);
//        runtime.pushToOperandStack(psObject);
        runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGen.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGen.mv.visitInsn(DUP);
        runtime.bcGen.mv.visitTypeInsn(NEW, "psObjects/values/simple/PSName");
        runtime.bcGen.mv.visitInsn(DUP);
        runtime.bcGen.mv.visitLdcInsn(strValue);
        runtime.bcGen.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSName", "<init>", "(Ljava/lang/String;)V", false);
        runtime.bcGen.mv.visitFieldInsn(GETSTATIC, "psObjects/Attribute$TreatAs", "LITERAL", "LpsObjects/Attribute$TreatAs;");
        runtime.bcGen.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;LpsObjects/Attribute$TreatAs;)V", false);
        runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
//
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
