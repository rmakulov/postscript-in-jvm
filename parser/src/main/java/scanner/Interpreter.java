package scanner;

import operators.control.ExecOp;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.Runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static psObjects.Attribute.TreatAs.LITERAL;

/**
 * Example showing how to convert a Postscript document to PDF using the high level API.
 *
 * @author Gilles Grousset (gi.grousset@gmail.com)
 */
public class Interpreter {
    public static Interpreter instance = new Interpreter();
    public static final int READ_BUFFER_SIZE = 1024;
    protected static byte[] content;
    private static runtime.Runtime runtime = Runtime.getInstance();

    private Interpreter() {

    }

    public void run(File file) throws IOException {
        runtime.initDefaultDictionaries();
        Yylex scanner = new Yylex(new InputStreamReader(new FileInputStream(file)));
        Yytoken yytoken;

        while ((yytoken = scanner.yylex()) != null) {
            String text = yytoken.m_text;
            //System.out.print(text + " ");
            switch (yytoken.m_index) {
                case 42:
                    runtime.pushToOperandStack(new PSObject(new PSInteger(Integer.parseInt(text))));
                    break;
                case 11:
                    //hex
                    runtime.pushToOperandStack(new PSObject(new PSInteger(Integer.parseInt(text, 16))));
                    break;
                case 22:
                    //radix
                    String[] args = text.split("#");
                    int radix = Integer.parseInt(args[0]);
                    runtime.pushToOperandStack(new PSObject(new PSInteger(Integer.parseInt(args[1], radix))));
                    break;
                case 23:
                    //real
                    runtime.pushToOperandStack(new PSObject(new PSReal(Double.parseDouble(text))));
                    break;
                case 43:
                    // name without "/". it is executable by default
                    runtime.pushToOperandStack(new PSObject(new PSName(text)));
                    if (scanner.getProcDepth() == 0) ExecOp.instance.execute();
                    break;
                case 44:
                    // name with "/". it is executable by default
                    runtime.pushToOperandStack(new PSObject(new PSName(text), LITERAL));
                    break;
                case 46:
                    // strings
                    String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                    runtime.pushToOperandStack(new PSObject(new PSString(s), LITERAL));
                    break;
                case 5:
                    // array
                    runtime.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
                    break;
                case 6:
                    runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_SQUARE_BRACKET));
                    if (scanner.getProcDepth() == 0) {
                        ExecOp.instance.execute();
                    }
                    break;
                case 7:
                    runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CURLY_BRACE));
                    break;
                case 8:
                    runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_CURLY_BRACE));
                    if (scanner.getProcDepth() == 0) {
                        ExecOp.instance.execute();
                    }
                    break;
                case 9:
                    runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CHEVRON_BRACKET));
                    break;
                case 10:
                    runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_CHEVRON_BRACKET));
                    if (scanner.getProcDepth() == 0) {
                        ExecOp.instance.execute();
                    }
                    break;

                default:
                    break;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        //Interpreter.instance.run(new File("test.ps"));
        Interpreter.instance.run(new File("simpleGraphicsTest.ps"));
        //Interpreter.instance.run(new File("colorcir.ps"));

//        System.out.print("Done");
//        Yylex scanner = new Yylex(new InputStreamReader(new FileInputStream("test.ps")));
//        Yytoken yytoken;
//
//        while ((yytoken = scanner.yylex()) != null) {
//            String text = yytoken.toString();
//            System.out.print(text + " ");
//
//        }
    }
}