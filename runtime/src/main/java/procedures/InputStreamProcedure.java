package procedures;

import operators.array.CloseSquareBracketOp;
import operators.array.OpenSquareBracketOp;
import operators.common.CloseCurlyBraceOp;
import operators.dictionary.CloseChevronOp;
import operators.dictionary.OpenChevronOp;
import org.objectweb.asm.Opcodes;
import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;
import scanner.Tokens;
import scanner.Yylex;
import scanner.Yytoken;

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

    public InputStreamProcedure(Context context, String name, InputStreamReader reader) {
        super(context, name);
        scanner = new Yylex(reader);
    }

    @Override
    public boolean execNext() {
        if (context.bcGenManager.isSleep()) {
            return super.execNext();
        } else {
            compileNext();
            return true;
        }
    }

    private void compileNext() {
        if (!hasNext()) return;
        String text = nextYytoken.m_text;
        Tokens m_type = nextYytoken.m_type;
        nextYytoken = null;

        switch (m_type) {
            case EXEC_NAME:
                // name without "/". it is executable by default
                if(runtime.enableNameCaching) {
                    PSName.executiveCompile(context, text);
                }else{
                    PSName.executiveCompileWithoutCaching(context, text);
                }
                break;
            case LIT_NAME:
                // name with "/". it is executable by default
                PSName.literalCompile(context, text);
                break;
            case INTEGER:
                PSInteger.compile(context, Integer.parseInt(text));
                break;
            case OPEN_CURLY_BRACE:
                context.bcGenManager.startCodeGenerator(context);
                procDepth++;
                break;
            case CLOSE_CURLY_BRACE:
                if (procDepth == 1) {
                    CloseCurlyBraceOp.instance.interpret(context);
                } else {

                    CloseCurlyBraceOp.compile(context);
                    //it is needed only in inner bytecode but after finishing more inner bytecode
                    context.bcGenManager.incInstrCounter();
                }
                procDepth--;
                break;
            case OPEN_SQUARE_BRACKET:
                // array
                OpenSquareBracketOp.instance.compile(context);
//                return new PSObject(PSMark.OPEN_SQUARE_BRACKET);
                break;
            case CLOSE_SQUARE_BRACKET:
                CloseSquareBracketOp.instance.compile(context);
//                return new PSObject(PSMark.CLOSE_SQUARE_BRACKET);
                break;
            case STRINGS:
                // strings
                String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                PSString.compile(context, s);
                break;
            case OPEN_CHEVRON_BRACKET:
                OpenChevronOp.instance.compile(context);
                break;
            case CLOSE_CHEVRON_BRACKET:
                CloseChevronOp.instance.compile(context);
//                return new PSObject(PSMark.CLOSE_CHEVRON_BRACKET);
                break;
            case REAL:
                PSReal.compile(context, Double.parseDouble(text));
                break;
            case HEX:
                //hex
                PSInteger.compile(context, Integer.parseInt(text, 16));
                break;
            case RADIX:
                //radix
                String[] args = text.split("#");
                int radix = Integer.parseInt(args[0]);
                PSInteger.compile(context, Integer.parseInt(args[1], radix));
                break;
            case OPERATOR:
                PSDictionary dict = (PSDictionary) runtime.getSystemDict().getValue();
                PSObject operatorName = new PSObject(new PSName(text));
                dict.get(operatorName).compile(context);
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
                context.bcGenManager.incInstrCounter();
            case CLOSE_CURLY_BRACE:
            case OPEN_CURLY_BRACE:
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
            case EXEC_NAME:
                // name without "/". it is executable by default
                return new PSObject(new PSName(text), EXECUTABLE);
            case LIT_NAME:
                // name with "/". it is executable by default
                return new PSObject(new PSName(text), LITERAL);
            case OPEN_CURLY_BRACE:
                return new PSObject(PSMark.OPEN_CURLY_BRACE);
            case CLOSE_CURLY_BRACE:
                return new PSObject(PSMark.CLOSE_CURLY_BRACE);
            case INTEGER:
                return new PSObject(new PSInteger(Integer.parseInt(text)));
            case OPEN_SQUARE_BRACKET:
                // array
                return new PSObject(PSMark.OPEN_SQUARE_BRACKET);
            case CLOSE_SQUARE_BRACKET:
                return new PSObject(PSMark.CLOSE_SQUARE_BRACKET);
            case STRINGS:
                // strings
                String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                return new PSObject(new PSString(s), LITERAL);
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
            case OPEN_CHEVRON_BRACKET:
                return new PSObject(PSMark.OPEN_CHEVRON_BRACKET);
            case CLOSE_CHEVRON_BRACKET:
                return new PSObject(PSMark.CLOSE_CHEVRON_BRACKET);
            case OPERATOR:
                PSDictionary dict = (PSDictionary) runtime.getSystemDict().getValue();
                PSObject operatorName = new PSObject(new PSName(text));
                return dict.get(operatorName);
            case COMMENTS:
                break;
            default:
                return null;
        }
        return null;
    }
}
