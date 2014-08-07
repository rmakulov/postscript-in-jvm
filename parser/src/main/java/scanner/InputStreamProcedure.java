package scanner;

import org.objectweb.asm.Opcodes;
import procedures.Procedure;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;

import java.io.IOException;
import java.io.InputStreamReader;

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
                runtime.bcGen.appendPattern("ARG");
                runtime.bcGen.argsCount++;
                runtime.bcGen.setSleep(false);

                final double t2 = Double.parseDouble(text);
                runtime.bcGen.args.add(t2);

                runtime.bcGen.mv.visitIntInsn(ALOAD, 0);
                runtime.bcGen.mv.visitFieldInsn(GETFIELD, "runtime/Runtime", "bcGen", "Lruntime/BytecodeGenerator;");
                runtime.bcGen.mv.visitFieldInsn(GETFIELD, "runtime/BytecodeGenerator", "args", "Ljava/util/Queue;");
                runtime.bcGen.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Queue", "remove", "()Ljava/lang/Object;", true);
                runtime.bcGen.mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
                runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);

                return new PSObject(new PSReal(t2));
//                }

            case HEX:
                //hex
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(new PSInteger(Integer.parseInt(text, 16)));
            case RADIX:
                //radix
                String[] args = text.split("#");
                int radix = Integer.parseInt(args[0]);
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(new PSInteger(Integer.parseInt(args[1], radix)));
            case EXEC_NAME:
                // name without "/". it is executable by default
                PSObject psObject = new PSObject(new PSName(text), EXECUTABLE);
                return createBytecodeByPattern(text, psObject);

            case LIT_NAME:
                // name with "/". it is executable by default
                runtime.bcGen.resetCodeGenerator();
                PSObject psObject1 = new PSObject(new PSName(text), LITERAL);
                return psObject1;
            case STRINGS:
                // strings
                String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(new PSString(s), LITERAL);
            case OPEN_SQUARE_BRACKET:
                // array
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(PSMark.OPEN_SQUARE_BRACKET);
            case CLOSE_SQUARE_BRACKET:
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(PSMark.CLOSE_SQUARE_BRACKET);
            case OPEN_CURLY_BRACE:
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(PSMark.OPEN_CURLY_BRACE);
            case CLOSE_CURLY_BRACE:
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(PSMark.CLOSE_CURLY_BRACE);
            case OPEN_CHEVRON_BRACKET:
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(PSMark.OPEN_CHEVRON_BRACKET);
            case CLOSE_CHEVRON_BRACKET:
                runtime.bcGen.resetCodeGenerator();
                return new PSObject(PSMark.CLOSE_CHEVRON_BRACKET);
            case COMMENTS:
                runtime.bcGen.resetCodeGenerator();
                break;
            case STRING_TEXT:
                runtime.bcGen.resetCodeGenerator();
                break;
            default:
                runtime.bcGen.resetCodeGenerator();
                return null;
        }
        return null;
    }

    private PSObject createBytecodeByPattern(String text, PSObject psObject) {
        // can gen only if operator doesn't changed
        if (runtime.findDict(psObject) == null || !runtime.findDict(psObject).equals(runtime.getSystemDict())) {
            runtime.bcGen.resetCodeGenerator();
            return psObject;
        }

        if (text.equals("add") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(DADD);
            return null;

        } else if (text.equals("mul") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(DMUL);
            return null;

        } else if (text.equals("div") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(DDIV);
            return null;

        } else if (text.equals("idiv") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitVarInsn(DSTORE, 2);
            runtime.bcGen.mv.visitInsn(D2L);
            runtime.bcGen.mv.visitVarInsn(DLOAD, 2);
            runtime.bcGen.mv.visitInsn(D2L);
            runtime.bcGen.mv.visitInsn(LDIV);
            runtime.bcGen.mv.visitInsn(L2D);
            return null;

        } else if (text.equals("sub") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(DSUB);
            return null;

        } else if (text.equals("mod") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitVarInsn(DSTORE, 2);
            runtime.bcGen.mv.visitInsn(D2L);
            runtime.bcGen.mv.visitVarInsn(DLOAD, 2);
            runtime.bcGen.mv.visitInsn(D2L);
            runtime.bcGen.mv.visitInsn(LREM);
            runtime.bcGen.mv.visitInsn(L2D);
            return null;

        } else if (text.equals("abs") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "abs", "(D)D");
            return null;

        } else if (text.equals("neg") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(DNEG);
            return null;

        } else if (text.equals("ceiling") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "ceil", "(D)D");
            return null;

        } else if (text.equals("floor") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "floor", "(D)D");
            return null;

        } else if (text.equals("round") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "round", "(D)J");
            return null;

        } else if (text.equals("truncate") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(D2I);
            runtime.bcGen.mv.visitInsn(I2D);
            return null;

        } else if (text.equals("sqrt") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "sqrt", "(D)D");
            return null;

        } else if (text.equals("atan") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "atan2", "(DD)D");
            runtime.bcGen.mv.visitLdcInsn(3.141592653589793d);
            runtime.bcGen.mv.visitInsn(DMUL);
            runtime.bcGen.mv.visitLdcInsn(180.0d);
            runtime.bcGen.mv.visitInsn(DDIV);
            return null;

        } else if (text.equals("cos") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitLdcInsn(3.141592653589793d);
            runtime.bcGen.mv.visitInsn(DMUL);
            runtime.bcGen.mv.visitLdcInsn(180.0d);
            runtime.bcGen.mv.visitInsn(DDIV);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "cos", "(D)D");
            return null;

        } else if (text.equals("sin") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitLdcInsn(3.141592653589793d);
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.mv.visitInsn(DMUL);
            runtime.bcGen.mv.visitLdcInsn(180.0d);
            runtime.bcGen.mv.visitInsn(DDIV);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "sin", "(D)D");
            return null;

        } else if (text.equals("exp") && runtime.bcGen.argsCount > 1) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "pow", "(DD)D");
            return null;

        } else if (text.equals("ln") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "log", "(D)D");
            return null;

        } else if (text.equals("log") && runtime.bcGen.argsCount > 0) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "log10", "(D)D");
            return null;

        } else if (text.equals("rand")) {
            runtime.bcGen.appendPattern(text);
            runtime.bcGen.setSleep(false);

            runtime.bcGen.mv.visitFieldInsn(GETSTATIC, "operators/arithmetic/RandOp", "instance", "Loperators/arithmetic/RandOp;");
            runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "operators/arithmetic/RandOp", "execute", "()V", false);
            runtime.bcGen.mv.visitVarInsn(ALOAD, 0);
            runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "popFromOperandStack", "()LpsObjects/PSObject;", false);
            runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "getValue", "()LpsObjects/values/Value;", false);
            runtime.bcGen.mv.visitTypeInsn(CHECKCAST, "psObjects/values/simple/numbers/PSNumber");
            runtime.bcGen.mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/values/simple/numbers/PSNumber", "getRealValue", "()D", false);
            runtime.bcGen.argsCount++;
            return null;

        } else if (text.equals("srand") && runtime.bcGen.argsCount > 0) {

            runtime.bcGen.appendPattern(text);
            runtime.bcGen.argsCount--;
            runtime.bcGen.setSleep(false);
            runtime.bcGen.mv.visitInsn(D2I);
            runtime.bcGen.mv.visitIntInsn(ISTORE, 6);
            runtime.bcGen.mv.visitFieldInsn(GETSTATIC, "operators/arithmetic/RandOp", "instance", "Loperators/arithmetic/RandOp;");
            runtime.bcGen.mv.visitIntInsn(ILOAD, 6);
            runtime.bcGen.mv.visitMethodInsn(INVOKESTATIC, "operators/arithmetic/RandOp", "setRandomSeed", "(I)V", false);
            return null;

        } else {
            runtime.bcGen.resetCodeGenerator();
            return psObject;
        }
    }

}
