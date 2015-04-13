package operators.miscellaneous;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSArray;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSBytecode;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.compiler.DynamicClassLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 1 on 19.03.14.
 */
public class BindOp extends Operator {
    public static final BindOp instance = new BindOp();
    private int replacingInstrCount;

    protected BindOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 1) return;
        PSObject o = context.popFromOperandStack();
        if (!o.isProc() && !o.isBytecode()) {
            fail();
            context.pushToOperandStack(o);
            return;
        }
        if (o.isProc()) {
            procBind(context, o);
        } else {
            bytecodeBind(context, o);
        }
    }

    private void bytecodeBind(Context context, PSObject o) {
        String className = ((PSBytecode) o.getValue()).getStrValue();
        int classNumber = Integer.parseInt(className);
        DynamicClassLoader l = DynamicClassLoader.instance;
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(l.getBytecodes().get(className));
        cr.accept(classNode, 0);

        reformMethods(context, classNumber, l, classNode);

        ClassWriter out = new ClassWriter(0);
        classNode.accept(out);
        l.putClass(className, out.toByteArray());

        context.pushToOperandStack(o);
    }

    private void reformMethods(Context context, int classNumber, DynamicClassLoader l, ClassNode classNode) {
        //peek at classNode and modifier
        List<MethodNode> methods = (List<MethodNode>) classNode.methods;
        for (MethodNode method : methods) {
            if (!method.name.startsWith("run_")) continue;

            int methodNumber = Integer.parseInt(method.name.substring(4));
            String suspectOperatorName = l.getSuspectOperatorName(classNumber, methodNumber);

            if (suspectOperatorName != null) {
                int length = suspectOperatorName.length();
                //operator name without suffix "Op" and in lower case
                int previousIndex = suspectOperatorName.lastIndexOf("/");
                String name = suspectOperatorName.substring(previousIndex + 1, length - 2).toLowerCase();
                if (context.checkIsOperator(name)) {
                    transformMethod(context, method, suspectOperatorName);
                }
            }
        }
    }

    private void transformMethod(Context context, MethodNode method, String suspectOperatorName) {
        InsnList insnList = method.instructions;
        Iterator ite = insnList.iterator();
        int numberOfMethodEndingInstructions = 3;
        replacingInstrCount = insnList.size() - numberOfMethodEndingInstructions;
        for (int i = 0; i < replacingInstrCount; i++) {

            AbstractInsnNode insn = (AbstractInsnNode) ite.next();
            if (i == (replacingInstrCount - 1)) {
                InsnList tempList = new InsnList();
//                (new PSObject(AddOp.instance)).interpret(0);
                tempList.add(new TypeInsnNode(NEW, "psObjects/PSObject"));
                tempList.add(new InsnNode(DUP));
                tempList.add(new FieldInsnNode(GETSTATIC, suspectOperatorName, "instance", "L" + suspectOperatorName + ";"));
                tempList.add(new MethodInsnNode(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false));
                String name = context.bcGenManager.bytecodeName;
                tempList.add(new FieldInsnNode(GETSTATIC, name, "context", "Lruntime/Context;"));
                tempList.add(new InsnNode(ICONST_0));
                tempList.add(new MethodInsnNode(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(Lruntime/Context;I)Z", false));
                LabelNode l8 = new LabelNode();
                tempList.add(new JumpInsnNode(IFNE, l8));
//                mv.visitJumpInsn(IFNE, l8);
                tempList.add(new InsnNode(ICONST_0));
//                mv.visitInsn(ICONST_0);
                tempList.add(new InsnNode(IRETURN));
//                mv.visitInsn(IRETURN);
                tempList.add(l8);
//                mv.visitLabel(l8);
                insnList.insert(insn, tempList);
                method.maxStack += 2;
//                System.out.println("replaced " + method.name);
            }
            insnList.remove(insn);
            //insn.
        }


    }

    private void procBind(Context context, PSObject o) {
        PSArray psArr = (PSArray) o.getValue();
        ArrayList<PSObject> resArray = new ArrayList<PSObject>();
        for (PSObject innerObj : psArr.getArray()) {
            if (innerObj.getType() == Type.NAME && innerObj.treatAs() == Attribute.TreatAs.EXECUTABLE) {
                PSObject value = context.search(innerObj);
                if (value != null && value.getType() == Type.OPERATOR) {
                    resArray.add(value);
                } else {
                    resArray.add(innerObj);
                }
            } else {
                resArray.add(innerObj);
            }
        }
        PSArray result = new PSArray(resArray);
        o.setValue(result);
        context.pushToOperandStack(o);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bind");
    }
}
