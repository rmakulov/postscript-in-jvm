package psObjects.values.simple;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import runtime.Runtime;
import runtime.compiler.BytecodeGeneratorManager;
import runtime.compiler.DynamicClassLoader;

import static psObjects.Attribute.TreatAs.EXECUTABLE;
import static psObjects.Attribute.TreatAs.LITERAL;

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
//        System.out.println("DictStackVersion in PSName " + strValue + ": " + runtime.getDictStackVersion());
        PSObject value = runtime.findValue(obj);
        String procName = ((PSName) obj.getValue()).getStrValue();
        while (value.getType() == Type.NAME && !(value.isBytecode()) && value.treatAs() == EXECUTABLE) {
            procName = procName + " -> " + ((PSName) value.getValue()).getStrValue();
            value = runtime.findValue(value);
        }
        return value.execute(0);
//        if (value.isProc()) {
//            if(runtime.isCompiling){
//                return value.execute(0);
//            }else {
//                runtime.pushToCallStack(new ArrayProcedure(procName, value));
//            }
//        }  else if (value.getType() == Type.OPERATOR) {
//            Operator operator = (Operator) value.getValue();
//            return operator.interpret(null);
//        } else if (value.getType() == Type.STRING && value.xcheck()) {
//            try {
//                runtime.pushToCallStack(new StringProcedure(((PSString) value.getValue()).getString()));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        } else {
//            runtime.pushToOperandStack(value);
//        }
        //value cannot be a Mark
//        todo error handler
        //return true;
    }

    public void executiveCompile(String strValue) {
        runtime.Runtime runtime = Runtime.getInstance();
        BytecodeGeneratorManager bcGenManager = runtime.bcGenManager;
        //int version = runtime.getNameVersion(strValue);
        int version = runtime.getDictStackVersion();


        String className = bcGenManager.bytecodeName;

        PSObject object = runtime.search(strValue);
        MethodVisitor mv = bcGenManager.mv;
        if (object != null) {
//       java: if (runtime.getNameVersion(strValue) - version == 0) {
            mv.visitFieldInsn(GETSTATIC, className, "runtime", "Lruntime/Runtime;");
//            mv.visitLdcInsn(strValue);
            //mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "getNameVersion", "(Ljava/lang/String;)I", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "getDictStackVersion", "()I", false);
            mv.visitLdcInsn(version);
            mv.visitInsn(ISUB);

//            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitLdcInsn(strValue);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            Label l1 = new Label();
            mv.visitJumpInsn(IFNE, l1);


            //start then block
            object.compile(0);
            //end then block

            //java:  } else {
            Label l2 = new Label();
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l1);
            mv.visitFrame(F_SAME, 0, null, 0, null);

            //start else block
            writeExecutiveBytecode(strValue);
            //end else block

            // java :}
            mv.visitLabel(l2);
            mv.visitFrame(F_SAME, 0, null, 0, null);
        } else {
            writeExecutiveBytecode(strValue);
        }

    }
//    public static void executiveCompile(String strValue) {
//        runtime.Runtime runtime = Runtime.getInstance();
//        PSObject obj = runtime.search(new PSObject(new PSName(strValue)));
//        boolean isOperator = (obj != null && obj.getType() == Type.OPERATOR);
//        BytecodeGeneratorManager bcGenManager = runtime.bcGenManager;
//
//        String className = bcGenManager.bytecodeName;
//        if (isOperator) {// && !bcGenManager.lastMethodIsEmpty()) {
//            if (!bcGenManager.lastMethodIsEmpty()) {
//                bcGenManager.endMethod();
//                bcGenManager.startMethod();
//            }
//            saveSuspectOperatorIndex(obj, bcGenManager, className);
//        }
//
//        bcGenManager.incInstrCounter();
//        writeExecutiveBytecode(strValue, className);
//
//        if (isOperator) {
//            bcGenManager.endMethod();
//            bcGenManager.startMethod();
//        }
//
//    }

    private void writeExecutiveBytecode(String strValue) {
        MethodVisitor mv = Runtime.getInstance().bcGenManager.mv;
        // for bindOP operator: next 10 replacing instructions
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
        checkExitCompile();
    }

    private void saveSuspectOperatorIndex(PSObject obj, BytecodeGeneratorManager bcGenManager, String className) {
        DynamicClassLoader l = DynamicClassLoader.instance;
        String operatorPath = obj.getValue().getClass().getCanonicalName().replace(".", "/");
        int classNumber = Integer.parseInt(className);
        int methodNumber = bcGenManager.methodNumber;
        //l.putSuspectOperatorIndex(classNumber, methodNumber, operatorPath);
    }


    public void literalCompile(String strValue, Attribute.TreatAs treatAs, int procDepth) {
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

        switch (treatAs) {
            case EXECUTABLE:
                mv.visitFieldInsn(GETSTATIC, "psObjects/Attribute$TreatAs", "EXECUTABLE", "LpsObjects/Attribute$TreatAs;");
                break;
            case LITERAL:
                mv.visitFieldInsn(GETSTATIC, "psObjects/Attribute$TreatAs", "LITERAL", "LpsObjects/Attribute$TreatAs;");
                break;
        }

        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;LpsObjects/Attribute$TreatAs;)V", false);
        mv.visitLdcInsn(procDepth);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);

        //mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
        checkExitCompile();
//
    }

    @Override
    public void compile(PSObject obj, int procDepth) {

        Attribute attribute = obj.getAttribute();
        Attribute.TreatAs treatAs = attribute.treatAs;
        if (treatAs == EXECUTABLE) {
            if (procDepth == 0) {
                executiveCompile(strValue);
            } else {
                literalCompile(strValue, EXECUTABLE, procDepth);
            }
        } else {
            literalCompile(strValue, LITERAL, procDepth);
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
    public String toStringView(PSObject obj) {
        Attribute.TreatAs treatAs = obj.getAttribute().treatAs;
        switch (treatAs) {
            case EXECUTABLE:
                return strValue;
            default:
                return "/" + strValue;
        }
    }

    public int compareGrade() {
        return 0;
    }

    public int length() {
        return strValue.length();
    }

//    @Override
//    public void deepCompile(PSObject obj) {
//        //todo split into literal and executive
//        Attribute attribute = obj.getAttribute();
//        Attribute.TreatAs treatAs = attribute.treatAs;
//        if (treatAs == EXECUTABLE) {
//            PSObject realValue = runtime.findValue(obj);
//            while (realValue.getType() == Type.NAME && !(realValue.isBytecode()) && realValue.treatAs() == EXECUTABLE) {
//                realValue = runtime.findValue(realValue);
//            }
////        String name = runtime.bcGenManager.bytecodeName;
////        MethodVisitor mv = runtime.bcGenManager.mv;
////        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
//            realValue.deepCompile();
//            MethodVisitor mv = runtime.bcGenManager.mv;
//            String name = runtime.bcGenManager.bytecodeName;
//            mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
//            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "popFromOperandStack", "()LpsObjects/PSObject;", false);
//            mv.visitInsn(ICONST_0);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
//
//            Label l8 = new Label();
//            mv.visitJumpInsn(IFNE, l8);
//            mv.visitInsn(ICONST_0);
//            mv.visitInsn(IRETURN);
//            mv.visitLabel(l8);
//        } else {
//            literalCompile(strValue);
//        }
//
//
////        mv.visitInsn(ICONST_0);
////        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "execute", "(I)Z", false);
//    }
}
