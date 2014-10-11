package scanner;

import operators.array.CloseSquareBracketOp;
import operators.array.OpenSquareBracketOp;
import operators.common.CloseCurlyBraceOp;
import operators.dictionary.CloseChevronOp;
import operators.dictionary.OpenChevronOp;
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
    public void execNext() {
        if (runtime.bcGenManager.isSleep()) {
            super.execNext();
        } else {
            compileNext();
        }
    }

    private void compileNext() {
        if (!hasNext()) return;
        String text = nextYytoken.m_text;
        Tokens m_type = nextYytoken.m_type;
        nextYytoken = null;

        switch (m_type) {
            case INTEGER:
                PSInteger.compile(Integer.parseInt(text));
                break;
            case REAL:
                PSReal.compile(Double.parseDouble(text));
                break;
            case HEX:
                //hex
                PSInteger.compile(Integer.parseInt(text, 16));
                break;
            case RADIX:
                //radix
                String[] args = text.split("#");
                int radix = Integer.parseInt(args[0]);
                PSInteger.compile(Integer.parseInt(args[1], radix));
                break;
            case EXEC_NAME:
                // name without "/". it is executable by default
                PSName.executiveCompile(text);
                break;
            case LIT_NAME:
                // name with "/". it is executable by default
                PSName.literalCompile(text);
                break;
            case STRINGS:
                // strings
                String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                PSString.compile(s);
                break;

            case OPEN_SQUARE_BRACKET:
                // array
                OpenSquareBracketOp.instance.compile();
//                return new PSObject(PSMark.OPEN_SQUARE_BRACKET);
                break;
            case CLOSE_SQUARE_BRACKET:
                CloseSquareBracketOp.instance.compile();
//                return new PSObject(PSMark.CLOSE_SQUARE_BRACKET);
                break;
            case OPEN_CURLY_BRACE:
                runtime.bcGenManager.startCodeGenerator();
                procDepth++;
                break;
            case CLOSE_CURLY_BRACE:
                if (procDepth == 1) {
                    CloseCurlyBraceOp.instance.interpret();
                } else {
                    runtime.bcGenManager.endBytecode();
                    CloseCurlyBraceOp.compile();
                }
                procDepth--;
                break;
            case OPEN_CHEVRON_BRACKET:
                OpenChevronOp.instance.compile();
                break;
            case CLOSE_CHEVRON_BRACKET:
                CloseChevronOp.instance.compile();
//                return new PSObject(PSMark.CLOSE_CHEVRON_BRACKET);
                break;
            case COMMENTS:
                break;
            default:
                break;
        }

        switch (m_type) {
            case EXEC_NAME:
                break;
            case INTEGER:
            case HEX:
            case RADIX:
            case REAL:
            case LIT_NAME:
            case STRINGS:
            case OPEN_SQUARE_BRACKET:
            case CLOSE_SQUARE_BRACKET:
            case OPEN_CHEVRON_BRACKET:
            case CLOSE_CHEVRON_BRACKET:
                runtime.bcGenManager.incInstrCounter();
            case OPEN_CURLY_BRACE:
            case CLOSE_CURLY_BRACE:
            case COMMENTS:
            case STRING_TEXT:
                break;
        }
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
                return new PSObject(new PSInteger(Integer.parseInt(text)));
            case REAL:
                return new PSObject(new PSReal(Double.parseDouble(text)));
            case HEX:
                //hex
                return new PSObject(new PSInteger(Integer.parseInt(text, 16)));
            case RADIX:
                //radix
                String[] args = text.split("#");
                int radix = Integer.parseInt(args[0]);
                return new PSObject(new PSInteger(Integer.parseInt(args[1], radix)));
            case EXEC_NAME:
                // name without "/". it is executable by default
                return new PSObject(new PSName(text), EXECUTABLE);
            case LIT_NAME:
                // name with "/". it is executable by default
                return new PSObject(new PSName(text), LITERAL);
            case STRINGS:
                // strings
                String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                return new PSObject(new PSString(s), LITERAL);
            case OPEN_SQUARE_BRACKET:
                // array
                return new PSObject(PSMark.OPEN_SQUARE_BRACKET);
            case CLOSE_SQUARE_BRACKET:
                return new PSObject(PSMark.CLOSE_SQUARE_BRACKET);
            case OPEN_CURLY_BRACE:
                return new PSObject(PSMark.OPEN_CURLY_BRACE);
            case CLOSE_CURLY_BRACE:
                return new PSObject(PSMark.CLOSE_CURLY_BRACE);
            case OPEN_CHEVRON_BRACKET:
                return new PSObject(PSMark.OPEN_CHEVRON_BRACKET);
            case CLOSE_CHEVRON_BRACKET:
                return new PSObject(PSMark.CLOSE_CHEVRON_BRACKET);
            case COMMENTS:
                break;
            default:
                return null;
        }
        return null;
    }
}
