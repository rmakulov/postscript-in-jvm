package psObjects.values.simple;

import org.objectweb.asm.Label;
import procedures.ArrayProcedure;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import runtime.BytecodeGenerator;
import runtime.Runtime;

import static psObjects.Attribute.TreatAs.EXECUTABLE;

public class PSName extends SimpleValue {
    private static boolean isBinded;


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
        runtime.bcGenManager.mv.visitVarInsn(ALOAD, 0);
        runtime.bcGenManager.mv.visitLdcInsn(strValue);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "findValue", "(Ljava/lang/String;)LpsObjects/PSObject;", false);
        runtime.bcGenManager.mv.visitInsn(ICONST_0);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
//          end  Runtime.getInstance().findValue(str).interpret(0);
    }*/

    public static void executiveCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
//      begin   Runtime.getInstance().findValue(strValue).interpret(0);
        boolean isOperator = runtime.checkIsOperator(strValue);
        String name = runtime.bcGenManager.bytecodeName;
        if (isOperator) {
            runtime.bcGenManager.endMethod();
            runtime.bcGenManager.startMethod();

            String operatorIndexesName = BytecodeGenerator.operatorIndexesName;
            String operatorIndexesType = BytecodeGenerator.operatorIndexesType;

            runtime.bcGenManager.clinitMV.visitFieldInsn(GETSTATIC, name, operatorIndexesName, "L" + operatorIndexesType + ";");
            runtime.bcGenManager.clinitMV.visitLdcInsn(runtime.bcGenManager.blockNumber);
            runtime.bcGenManager.clinitMV.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            runtime.bcGenManager.clinitMV.visitLdcInsn(strValue);
            runtime.bcGenManager.clinitMV.visitMethodInsn(INVOKEVIRTUAL, operatorIndexesType, "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
        }

        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        runtime.bcGenManager.mv.visitLdcInsn(strValue);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "findValue", "(Ljava/lang/String;)LpsObjects/PSObject;", false);
        runtime.bcGenManager.mv.visitInsn(ICONST_0);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        Label l8 = new Label();
        runtime.bcGenManager.mv.visitJumpInsn(IFNE, l8);
        runtime.bcGenManager.mv.visitInsn(ICONST_0);
        runtime.bcGenManager.mv.visitInsn(IRETURN);
        runtime.bcGenManager.mv.visitLabel(l8);
        if (isOperator) {
            runtime.bcGenManager.endMethod();
            runtime.bcGenManager.startMethod();
        }
//          end  Runtime.getInstance().findValue(str).interpret(0);
    }


    public static void literalCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
//        PSObject psObject = new PSObject(new PSName(strValue), LITERAL);
//        runtime.pushToOperandStack(psObject);
        String name = runtime.bcGenManager.bytecodeName;
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/PSObject");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitTypeInsn(NEW, "psObjects/values/simple/PSName");
        runtime.bcGenManager.mv.visitInsn(DUP);
        runtime.bcGenManager.mv.visitLdcInsn(strValue);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSName", "<init>", "(Ljava/lang/String;)V", false);
        runtime.bcGenManager.mv.visitFieldInsn(GETSTATIC, "psObjects/Attribute$TreatAs", "LITERAL", "LpsObjects/Attribute$TreatAs;");
        runtime.bcGenManager.mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;LpsObjects/Attribute$TreatAs;)V", false);
        runtime.bcGenManager.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
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
