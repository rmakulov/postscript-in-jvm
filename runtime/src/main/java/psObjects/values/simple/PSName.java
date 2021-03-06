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
import runtime.Context;
import runtime.compiler.BytecodeGeneratorManager;
import runtime.compiler.DynamicClassLoader;

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
    public boolean interpret(Context context, PSObject obj) {
        //operatorIndexes.add(2);
//        System.out.println("DictStackVersion in PSName " + strValue + ": " + runtime.getDictStackVersion());
        PSObject value = context.findValue(obj);
        String procName = ((PSName) obj.getValue()).getStrValue();
//        if (procName.equals("search")){
//            PSDictionary dict = ((PSDictionary) runtime.findValue("gelements").getValue());
//            System.out.println(dict);        }
        while (value.getType() == Type.NAME && !(value.isBytecode()) && value.treatAs() == EXECUTABLE) {
            procName = procName + " -> " + ((PSName) value.getValue()).getStrValue();
            value = context.findValue(value);
        }
        if (value.isProc()) {
            context.pushToCallStack(new ArrayProcedure(context, procName, value));
        } else if (value.isBytecode()) {
            return value.execute(context, 0);
        } else if (value.getType() == Type.OPERATOR) {
            Operator operator = (Operator) value.getValue();
            return operator.interpret(context, null);
        } else if (value.getType() == Type.STRING && value.xcheck()) {
            try {
                context.pushToCallStack(new StringProcedure(context, ((PSString) value.getValue()).getString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            context.pushToOperandStack(value);
        }
        //value cannot be a Mark
//        todo error handler
        return true;
    }

    public static void executiveCompile(Context context, String strValue) {
        //runtime.Runtime runtime = Runtime.getInstance();
        PSObject object = context.search(new PSObject(new PSName(strValue)));
        boolean isOperator = (object != null && object.getType() == Type.OPERATOR);
        BytecodeGeneratorManager bcGenManager = context.bcGenManager;

        String className = bcGenManager.bytecodeName;

        if (isOperator) {// && !bcGenManager.lastMethodIsEmpty()) {
            if (!bcGenManager.lastMethodIsEmpty()) {
                bcGenManager.endMethod();
                bcGenManager.startMethod();
            }
            saveSuspectOperatorIndex(object, bcGenManager, className);
        }
        bcGenManager.incInstrCounter();
        if (object != null) {
            MethodVisitor mv = bcGenManager.mv;
            int version = context.getNameVersion(strValue);
//            int version = runtime.getDictStackVersion();
//       java: if (runtime.getNameVersion(strValue) - version == 0) {
            mv.visitFieldInsn(GETSTATIC, className, "context", "Lruntime/Context;");
            mv.visitLdcInsn(strValue);
            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "getNameVersion", "(Ljava/lang/String;)I", false);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "getDictStackVersion", "()I", false);
            mv.visitLdcInsn(version);
            mv.visitInsn(ISUB);

            mv.visitFieldInsn(GETSTATIC, className, "context", "Lruntime/Context;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Context", "getALoading", "()Z", false);
            mv.visitInsn(IADD);
            //Если версии совпадают и aloading==true, то то на стеке останется 1 (=0+1) и происходит вызов имени, и оно просто кладется на стек

            Label l1 = new Label();
            //mv.visitInsn(POP);
            mv.visitJumpInsn(IFEQ, l1);

            //start else block
            writeExecutiveBytecode(context, strValue);
            //end else block


            //java:  } else {
            Label l2 = new Label();
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l1);
            mv.visitFrame(F_SAME, 0, null, 0, null);


            //start then block
            object.compile(context);
            //end then block
            // java :}
            mv.visitLabel(l2);
            mv.visitFrame(F_SAME, 0, null, 0, null);
        } else {
            writeExecutiveBytecode(context, strValue);
        }
        if (isOperator) {
            bcGenManager.endMethod();
            bcGenManager.startMethod();
        }
    }


    public static void executiveCompileWithoutCaching(Context context, String strValue) {
        //runtime.Runtime runtime = Runtime.getInstance();
        PSObject object = context.search(new PSObject(new PSName(strValue)));
        boolean isOperator = (object != null && object.getType() == Type.OPERATOR);
        BytecodeGeneratorManager bcGenManager = context.bcGenManager;
        String className = bcGenManager.bytecodeName;
        if (isOperator) {// && !bcGenManager.lastMethodIsEmpty()) {
            if (!bcGenManager.lastMethodIsEmpty()) {
                bcGenManager.endMethod();
                bcGenManager.startMethod();
            }
            saveSuspectOperatorIndex(object, bcGenManager, className);
        }
        bcGenManager.incInstrCounter();
        writeExecutiveBytecode(context, strValue);
        if (isOperator) {
            bcGenManager.endMethod();
            bcGenManager.startMethod();
        }
    }

    private static void writeExecutiveBytecode(Context context, String strValue) {
        MethodVisitor mv = context.bcGenManager.mv;
        String name = context.bcGenManager.bytecodeName;
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSName");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(strValue);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSName", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);
        checkExitCompile(context);
    }

    private static void saveSuspectOperatorIndex(PSObject obj, BytecodeGeneratorManager bcGenManager, String className) {
        DynamicClassLoader loader = DynamicClassLoader.instance;
        String operatorPath = obj.getValue().getClass().getCanonicalName().replace(".", "/");
        int classNumber = Integer.parseInt(className);
        int methodNumber = bcGenManager.methodNumber;
        loader.putSuspectOperatorIndex(classNumber, methodNumber, operatorPath);
    }


    public static void literalCompile(Context context, String strValue) {
//        PSObject psObject = new PSObject(new PSName(strValue), LITERAL);
//        context.pushToOperandStack(psObject);
        String name = context.bcGenManager.bytecodeName;
        MethodVisitor mv = context.bcGenManager.mv;
//        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSName");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(strValue);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSName", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitFieldInsn(GETSTATIC, "psObjects/Attribute$TreatAs", "LITERAL", "LpsObjects/Attribute$TreatAs;");
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;LpsObjects/Attribute$TreatAs;)V", false);
        mv.visitFieldInsn(GETSTATIC, name, "context", "Lruntime/Context;");
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false);
        checkExitCompile(context);
    }

    @Override
    public void compile(Context context, PSObject obj) {
        Attribute attribute = obj.getAttribute();
        Attribute.TreatAs treatAs = attribute.treatAs;
        if (treatAs == EXECUTABLE) {
            if(runtime.enableNameCaching) {
                executiveCompile(context, strValue);
            }else{
                executiveCompileWithoutCaching(context,strValue);
            }
        } else {
            literalCompile(context, strValue);
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

        try {
            return o instanceof PSName ?
                    strValue.compareTo(((PSName) o).getStrValue()) :
                    super.compareTo(o);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 1;
        }

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
}
