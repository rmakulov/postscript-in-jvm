package psObjects.values.simple;

import operators.array.CloseSquareBracketOp;
import operators.array.OpenSquareBracketOp;
import operators.common.CloseCurlyBraceOp;
import operators.common.OpenCurlyBraceOp;
import operators.dictionary.CloseChevronOp;
import operators.dictionary.OpenChevronOp;
import org.objectweb.asm.MethodVisitor;
import psObjects.PSObject;
import psObjects.Type;

import static psObjects.values.simple.PSMark.Bracket.*;


public class PSMark extends SimpleValue {
    private Bracket bracket;

    public static PSMark OPEN_SQUARE_BRACKET = new PSMark(OPEN_SQUARE);
    public static PSMark CLOSE_SQUARE_BRACKET = new PSMark(CLOSE_SQUARE);
    public static PSMark OPEN_CURLY_BRACE = new PSMark(OPEN_CURLY);
    public static PSMark CLOSE_CURLY_BRACE = new PSMark(CLOSE_CURLY);
    public static PSMark OPEN_CHEVRON_BRACKET = new PSMark(OPEN_CHEVRON);
    public static PSMark CLOSE_CHEVRON_BRACKET = new PSMark(CLOSE_CHEVRON);

    public enum Bracket {
        OPEN_SQUARE, CLOSE_SQUARE, OPEN_CURLY, CLOSE_CURLY, OPEN_CHEVRON, CLOSE_CHEVRON;


    }

    public PSMark(Bracket bracket) {
        this.bracket = bracket;
    }

    public Bracket getBracket() {
        return bracket;
    }

    @Override
    public boolean interpret(PSObject obj) {
        getBracketOperator().interpret(obj);
        return true;
/*
        if (mark.equals(PSMark.CLOSE_CURLY_BRACE)) {
            CloseCurlyBraceOp.instance.interpret();

        } else if (mark.equals(PSMark.CLOSE_SQUARE_BRACKET)) {
            CloseSquareBracketOp.instance.interpret();
        } else if (mark.equals(PSMark.CLOSE_CHEVRON_BRACKET)) {
            CloseChevronOp.instance.interpret();
        } else if (mark.equals(PSMark.OPEN_CURLY_BRACE)) {
            if (runtime.isCompiling) {
                OpenCurlyBraceOp.instance.interpret();
            } else {
                runtime.pushToOperandStack(obj);
            }
        } else if (mark.equals(PSMark.OPEN_SQUARE_BRACKET) ||
                mark.equals(PSMark.OPEN_CHEVRON_BRACKET)) {
            mark.interpret(obj);
        }
*/
    }

    @Override
    public void compile(PSObject obj, int procDepth) {
        //new PSObject((new PSMark(Bracket.valueOf("OPEN_SQUARE")))).interpret(procDepth);
        MethodVisitor mv = runtime.bcGenManager.mv;

        mv.visitTypeInsn(NEW, "psObjects/PSObject");
        mv.visitInsn(DUP);
        mv.visitTypeInsn(NEW, "psObjects/values/simple/PSMark");
        mv.visitInsn(DUP);
        mv.visitLdcInsn(bracket.name());
        mv.visitMethodInsn(INVOKESTATIC, "psObjects/values/simple/PSMark$Bracket", "valueOf", "(Ljava/lang/String;)LpsObjects/values/simple/PSMark$Bracket;", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/values/simple/PSMark", "<init>", "(LpsObjects/values/simple/PSMark$Bracket;)V", false);
        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
        mv.visitLdcInsn(procDepth);
        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
        checkExitCompile();
//                runtime.pushToOperandStack(new PSObject(PSMark.));

//        String name = runtime.bcGenManager.bytecodeName;
//        MethodVisitor mv = runtime.bcGenManager.mv;
////        mv.visitFieldInsn(GETSTATIC, name, "runtime", "Lruntime/Runtime;");
//        mv.visitTypeInsn(NEW, "psObjects/PSObject");
//        mv.visitInsn(DUP);
//        mv.visitFieldInsn(GETSTATIC, "psObjects/values/simple/PSNull", "NULL", "LpsObjects/values/simple/PSMA;");
//        mv.visitMethodInsn(INVOKESPECIAL, "psObjects/PSObject", "<init>", "(LpsObjects/values/Value;)V", false);
////        mv.visitMethodInsn(INVOKEVIRTUAL, "runtime/Runtime", "pushToOperandStack", "(LpsObjects/PSObject;)V", false);
//        mv.visitInsn(ICONST_0);
//        mv.visitMethodInsn(INVOKEVIRTUAL, "psObjects/PSObject", "interpret", "(I)Z", false);
    }

    @Override
    public Type determineType() {
        return Type.MARK;
    }

    @Override
    public String toString() {
        return "PSMark{" +
                "bracket=\"" + bracket +
                "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSMark)) return false;

        PSMark psMark = (PSMark) o;

        if (bracket != psMark.bracket) return false;

        return true;
    }

    @Override
    public String toStringView(PSObject obj) {
        switch (bracket) {
            case OPEN_CURLY:
                return "{";
            case CLOSE_CURLY:
                return "}";
            default:
                return "--mark--";
        }


    }

    private Operator getBracketOperator() {
        switch (bracket) {
            case OPEN_SQUARE:
                return OpenSquareBracketOp.instance;
            case CLOSE_SQUARE:
                return CloseSquareBracketOp.instance;
            case OPEN_CURLY:
                return OpenCurlyBraceOp.instance;
            case CLOSE_CURLY:
                return CloseCurlyBraceOp.instance;
            case OPEN_CHEVRON:
                return OpenChevronOp.instance;
            case CLOSE_CHEVRON:
                return CloseChevronOp.instance;
            default:
                return null;
        }
    }

}
