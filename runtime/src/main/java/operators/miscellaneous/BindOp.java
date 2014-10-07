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
import runtime.DynamicClassLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 1 on 19.03.14.
 */
public class BindOp extends Operator {
    public static final BindOp instance = new BindOp();

    protected BindOp() {
        super();
    }

    @Override
    public void interpret() {
        if (runtime.getOperandStackSize() < 1) return;
        PSObject o = runtime.popFromOperandStack();
        if (!o.isProc() && !o.isBytecode()) {
            fail();
            runtime.pushToOperandStack(o);
            return;
        }
        if (o.isProc()) {
            procBind(o);
        } else {
//            runtime.pushToOperandStack(o);
            bytecodeBind(o);

        }
    }

    private void bytecodeBind(PSObject o) {
        String className = ((PSBytecode) o.getValue()).getStrValue();
        int classNumber = Integer.parseInt(className);
        DynamicClassLoader l = DynamicClassLoader.instance;
        ClassNode classNode = new ClassNode();
        ClassReader cr = new ClassReader(l.getBytecodes().get(className));
        cr.accept(classNode, 0);

        reformMethods(classNumber, l, classNode);

        ClassWriter out = new ClassWriter(0);
        classNode.accept(out);
        l.putClass(className, out.toByteArray());

        runtime.pushToOperandStack(o);
    }

    private void reformMethods(int classNumber, DynamicClassLoader l, ClassNode classNode) {
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
                if (runtime.checkIsOperator(name)) {
                    transformMethod(method, suspectOperatorName);
                }
            }
        }
    }

    private void transformMethod(MethodNode method, String suspectOperatorName) {
        InsnList insnList = method.instructions;
        Iterator ite = insnList.iterator();
        for (int i = 0; i < 5; i++) {
            AbstractInsnNode insn = (AbstractInsnNode) ite.next();
            if (i == 4) {
                InsnList tempList = new InsnList();
                tempList.add(new FieldInsnNode(GETSTATIC, suspectOperatorName, "instance", "L" + suspectOperatorName + ";"));
                tempList.add(new InsnNode(ACONST_NULL));
                tempList.add(new MethodInsnNode(INVOKEVIRTUAL, suspectOperatorName, "interpret", "(LpsObjects/PSObject;)Z", false));
                insnList.insert(insn, tempList);
                method.maxStack += 2;
            }
            insnList.remove(insn);

        }


//        while (ite.hasNext()) {
//            AbstractInsnNode insn = (AbstractInsnNode) ite.next();
//            int opcode = insn.getOpcode();
//            if (opcode == LDC) {
//                InsnList tempList = new InsnList();
//                tempList.add(new FieldInsnNode(GETSTATIC, suspectOperatorName, "instance", "L" + suspectOperatorName + ";"));
//                tempList.add(new InsnNode(NULL));
//                tempList.add(new MethodInsnNode(INVOKEVIRTUAL, suspectOperatorName, "interpret", "(LpsObjects/PSObject;)Z", false));
//                insnList.insert(insn, tempList);
//                            method.maxStack += 2;
//            } else if (opcode == GETSTATIC || opcode == INVOKEVIRTUAL || opcode == ICONST_0) {
//                insnList.remove(insn);
////            todo improve exit condition, now we exit when first 3 instructions removed
////            } else if (opcode == ICONST_0){
////                break;
//            }
//        }

    }

    private void procBind(PSObject o) {
        PSArray psArr = (PSArray) o.getValue();
        ArrayList<PSObject> resArray = new ArrayList<PSObject>();
        for (PSObject innerObj : psArr.getArray()) {
            if (innerObj.getType() == Type.NAME && innerObj.treatAs() == Attribute.TreatAs.EXECUTABLE) {
                PSObject value = runtime.search(innerObj);
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
        runtime.pushToOperandStack(o);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("bind");
    }
}
