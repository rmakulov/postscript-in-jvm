package scanner;

import org.objectweb.asm.Opcodes;
import procedures.Procedure;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import static psObjects.Attribute.TreatAs.EXECUTABLE;
import static psObjects.Attribute.TreatAs.LITERAL;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class InputStreamProcedure extends Procedure implements Opcodes {
    Yylex scanner;
    Yytoken nextYytoken;

    public InputStreamProcedure(String name, InputStreamReader reader) {
        super(name);
        scanner = new Yylex(reader);
    }

    @Override
    public boolean hasNext() {
        if (nextYytoken != null) return true;
        try {
            nextYytoken = scanner.yylex();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return nextYytoken != null;
    }

    @Override
    protected PSObject next() {
        if (!hasNext()) return null;
        String text = nextYytoken.m_text;
        Tokens m_type = nextYytoken.m_type;
        nextYytoken = null;
        switch (m_type) {
            case INTEGER:
            case REAL:
                //real
                runtime.appendBytecode("ARG");
                runtime.argsCount++;

                final double t2 = Double.parseDouble(text);
                runtime.args.add(t2);

                runtime.mv.visitIntInsn(ALOAD, 0);
                runtime.mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "args", "Ljava/util/Queue;");
                runtime.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Queue", "remove", "()Ljava/lang/Object;", true);
                runtime.mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
                runtime.mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);

                if ((t2 == Math.floor(t2)) && !Double.isInfinite(t2)) {
                    return new PSObject(new PSInteger((int) (t2)));
                } else {
                    return new PSObject(new PSReal(t2));
                }

            case HEX:
                //hex
                runtime.resetCodeGenerator();
                return new PSObject(new PSInteger(Integer.parseInt(text, 16)));
            case RADIX:
                //radix
                String[] args = text.split("#");
                int radix = Integer.parseInt(args[0]);
                runtime.resetCodeGenerator();
                return new PSObject(new PSInteger(Integer.parseInt(args[1], radix)));
            case EXEC_NAME:
                // name without "/". it is executable by default
                PSObject psObject = new PSObject(new PSName(text), EXECUTABLE);

                if (text.equals("add") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(DADD);
//                    return psObject;
                    return null;

                } else if (text.equals("mul") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(DMUL);
//                    return psObject;
                    return null;

                } else if (text.equals("div") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(DDIV);
//                    return psObject;
                    return null;

                } else if (text.equals("idiv") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitVarInsn(DSTORE, 2);
                    runtime.mv.visitInsn(D2L);
                    runtime.mv.visitVarInsn(DLOAD, 2);
                    runtime.mv.visitInsn(D2L);
                    runtime.mv.visitInsn(LDIV);
                    runtime.mv.visitInsn(L2D);
//                    return psObject;
                    return null;

                } else if (text.equals("sub") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(DSUB);
//                    return psObject;
                    return null;

                } else if (text.equals("mod") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitVarInsn(DSTORE, 2);
                    runtime.mv.visitInsn(D2L);
                    runtime.mv.visitVarInsn(DLOAD, 2);
                    runtime.mv.visitInsn(D2L);
                    runtime.mv.visitInsn(LREM);
                    runtime.mv.visitInsn(L2D);
//                    return psObject;
                    return null;

                } else if (text.equals("abs") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
//                    runtime.mv.visitVarInsn(DSTORE, 3);
//                    runtime.mv.visitIntInsn(DLOAD, 3);
//                    runtime.mv.visitInsn(DCONST_0);
//                    runtime.mv.visitInsn(DCMPG);
//                    Label label = new Label();
//                    runtime.mv.visitJumpInsn(IFGT, label);
//                    runtime.mv.visitInsn(DCONST_0);
//                    runtime.mv.visitIntInsn(DLOAD, 3);
//                    runtime.mv.visitInsn(DSUB);
//                    Label end = new Label();
//                    runtime.mv.visitJumpInsn(GOTO, end);
//                    runtime.mv.visitLabel(label);
//                    runtime.mv.visitFrame(F_SAME, 0, null, 0, null);
//                    runtime.mv.visitIntInsn(DLOAD, 3);
//                    runtime.mv.visitLabel(end);
//                    runtime.mv.visitFrame(F_SAME, 0, null, 0, null);

                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "abs", "(D)D");

//                    return psObject;
                    return null;

                } else if (text.equals("neg") && runtime.argsCount > 0) {
//                    if (runtime.args.argsCount() != 0){
//                        PSObject psObject1 = runtime.popFromOperandStack();
//                        final double t3 = ((PSNumber)psObject1.getValue()).getRealValue();
//                        runtime.args.add(t3);
//                        runtime.appendBytecode("ARG");
//                        runtime.mv.visitIntInsn(ALOAD, 0);
//                        runtime.mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "args", "Ljava/util/Queue;");
//                        runtime.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Queue", "remove", "()Ljava/lang/Object;", true);
//                        runtime.mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
//                        runtime.mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
//                    }
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(DNEG);
//                    return psObject;
                    return null;

                } else if (text.equals("ceiling") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "ceil", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("floor") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "floor", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("round") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "round", "(D)J");
//                    return psObject;
                    return null;

                } else if (text.equals("truncate") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(D2I);
                    runtime.mv.visitInsn(I2D);
//                    return psObject;
                    return null;

                } else if (text.equals("sqrt") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "sqrt", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("atan") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "atan2", "(DD)D");
                    runtime.mv.visitLdcInsn(3.141592653589793d);
                    runtime.mv.visitInsn(DMUL);
                    runtime.mv.visitLdcInsn(180.0d);
                    runtime.mv.visitInsn(DDIV);
//                    return psObject;
                    return null;

                } else if (text.equals("cos") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitLdcInsn(3.141592653589793d);
                    runtime.mv.visitInsn(DMUL);
                    runtime.mv.visitLdcInsn(180.0d);
                    runtime.mv.visitInsn(DDIV);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "cos", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("sin") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitLdcInsn(3.141592653589793d);
                    runtime.appendBytecode(text);
                    runtime.mv.visitInsn(DMUL);
                    runtime.mv.visitLdcInsn(180.0d);
                    runtime.mv.visitInsn(DDIV);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "sin", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("exp") && runtime.argsCount > 1) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "pow", "(DD)D");
//                    return psObject;
                    return null;

                } else if (text.equals("ln") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "log", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("log") && runtime.argsCount > 0) {
                    runtime.appendBytecode(text);
                    runtime.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "log10", "(D)D");
//                    return psObject;
                    return null;

                } else if (text.equals("rand")) {
                    runtime.appendBytecode(text);
                    double rand = 0;
                    try {
                        rand = (Integer) Operator.asm.getMethod("rand").invoke(null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    runtime.mv.visitLdcInsn(rand);
//                    runtime.mv.visitFieldInsn(GETSTATIC, "ASM", "random", "Ljava/util/Random;");
//                    runtime.mv.visitLdcInsn(2147483647);
//                    runtime.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Random", "nextInt", "(I)I", false);
//                    runtime.mv.visitInsn(I2D);
                    runtime.argsCount++;
//                    return psObject;
                    return null;

                } else if (text.equals("srand") && runtime.argsCount > 0) {

                    runtime.appendBytecode(text);
                    int srand = runtime.args.peek().intValue();
                    try {
                        Operator.asm.getMethod("srand", int.class).invoke(null, srand);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
//                    runtime.mv.visitInsn(D2L);
//                    runtime.mv.visitVarInsn(LSTORE, 1);
//                    runtime.mv.visitTypeInsn(NEW, "java/util/Random");
//                    runtime.mv.visitInsn(DUP);
//                    runtime.mv.visitVarInsn(LLOAD, 1);
//                    runtime.mv.visitMethodInsn(INVOKESPECIAL, "java/util/Random", "<init>", "(J)V", false);
//                    runtime.mv.visitFieldInsn(PUTSTATIC, "ASM", "random", "Ljava/util/Random;");

//                    return psObject;
                    return null;

                } else {
                    runtime.resetCodeGenerator();
                    return psObject;
                }

            case LIT_NAME:
                // name with "/". it is executable by default
                runtime.resetCodeGenerator();
                PSObject psObject1 = new PSObject(new PSName(text), LITERAL);
                return psObject1;
            case STRINGS:
                // strings
                String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                runtime.resetCodeGenerator();
                return new PSObject(new PSString(s), LITERAL);
            case OPEN_SQUARE_BRACKET:
                // array
                runtime.resetCodeGenerator();
                return new PSObject(PSMark.OPEN_SQUARE_BRACKET);
            case CLOSE_SQUARE_BRACKET:
                runtime.resetCodeGenerator();
                return new PSObject(PSMark.CLOSE_SQUARE_BRACKET);
            case OPEN_CURLY_BRACE:
                runtime.resetCodeGenerator();
                return new PSObject(PSMark.OPEN_CURLY_BRACE);
            case CLOSE_CURLY_BRACE:
                runtime.resetCodeGenerator();
                return new PSObject(PSMark.CLOSE_CURLY_BRACE);
            case OPEN_CHEVRON_BRACKET:
                runtime.resetCodeGenerator();
                return new PSObject(PSMark.OPEN_CHEVRON_BRACKET);
            case CLOSE_CHEVRON_BRACKET:
                runtime.resetCodeGenerator();
                return new PSObject(PSMark.CLOSE_CHEVRON_BRACKET);
            case COMMENTS:
                runtime.resetCodeGenerator();
                break;
            case STRING_TEXT:
                runtime.resetCodeGenerator();
                break;
            default:
                runtime.resetCodeGenerator();
                return null;
        }
        return null;
    }

}
