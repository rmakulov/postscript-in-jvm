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

import static psObjects.Attribute.TreatAs.EXECUTABLE;
import static psObjects.Attribute.TreatAs.LITERAL;

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
            switch (yytoken.m_type) {
                case INTEGER:
                    runtime.pushToOperandStack(new PSObject(new PSInteger(Integer.parseInt(text))));
                    break;
                case HEX:
                    //hex
                    runtime.pushToOperandStack(new PSObject(new PSInteger(Integer.parseInt(text, 16))));
                    break;
                case RADIX:
                    //radix
                    String[] args = text.split("#");
                    int radix = Integer.parseInt(args[0]);
                    runtime.pushToOperandStack(new PSObject(new PSInteger(Integer.parseInt(args[1], radix))));
                    break;
                case REAL:
                    //real
                    runtime.pushToOperandStack(new PSObject(new PSReal(Double.parseDouble(text))));
                    break;
                case EXEC_NAME:
                    // name without "/". it is executable by default
                    runtime.pushToOperandStack(new PSObject(new PSName(text), EXECUTABLE));
                    if (scanner.getProcDepth() == 0) {
                        ExecOp.instance.execute();
                    }
                    break;
                case LIT_NAME:
                    // name with "/". it is executable by default
                    runtime.pushToOperandStack(new PSObject(new PSName(text), LITERAL));
                    break;
                case STRINGS:
                    // strings
                    String s = text.replaceAll("\\\\([\\r]?\\n|\\r)", "");
                    runtime.pushToOperandStack(new PSObject(new PSString(s), LITERAL));
                    break;
                case OPEN_SQUARE_BRACKET:
                    // array
                    runtime.pushToOperandStack(new PSObject(PSMark.OPEN_SQUARE_BRACKET));
                    break;
                case CLOSE_SQUARE_BRACKET:
                    runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_SQUARE_BRACKET));
                    if (scanner.getProcDepth() == 0) {
                        ExecOp.instance.execute();
                    }
                    break;
                case OPEN_CURLY_BRACE:
                    runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CURLY_BRACE));
                    break;
                case CLOSE_CURLY_BRACE:
                    runtime.pushToOperandStack(new PSObject(PSMark.CLOSE_CURLY_BRACE));
                    if (scanner.getProcDepth() == 0) {
                        ExecOp.instance.execute();
                    }
                    break;
                case OPEN_CHEVRON_BRACKET:
                    runtime.pushToOperandStack(new PSObject(PSMark.OPEN_CHEVRON_BRACKET));
                    break;
                case CLOSE_CHEVRON_BRACKET:
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


    public static void main(String[] args) {
        try {

            if (args.length == 0) {
//                for(int i = 45 ; i < 80 ; i++){
//                    TransformMatrix c = new TransformMatrix() ;
//                    c.rotate((double)i) ;
//                    c.scale(3,3) ;
//                    c.translate(23,43);
//                    c.scale(2,0) ;
//                    System.out.println(c.getRotateAngle() );
//                }
//                Interpreter.instance.run(new File("7_ellipses.ps"));
//                Interpreter.instance.run(new File("6_arcs.ps"));
//                Interpreter.instance.run(new File("5_star.ps"));
//                Interpreter.instance.run(new File("6_Fractal_Arrow.ps"));
//                  Interpreter.instance.run(new File("snowflak.ps"));

// Interpreter.instance.run(new File("snowflak.ps"));
                //Interpreter.instance.run(new File("gsaveTest.ps"));
                //Interpreter.instance.run(new File("SimpleGraphicsTest2.ps"));
                //Interpreter.instance.run(new File("SimpleGraphicsTest.ps"));
//                Interpreter.instance.run(new File("1_rectangles.ps"));
                //Interpreter.instance.run(new File("bindTest.ps"));
//                Interpreter.instance.run(new File("colorcir.ps"));
                //Interpreter.instance.run(new File("WireFrame.eps"));
//                Interpreter.instance.run(new File("6_arcs.ps"));

//                Interpreter.instance.run(new File("WireFrame.eps"));
//                Interpreter.instance.run(new File("6_arcs.ps"));
            } else {
                Interpreter.instance.run(new File(args[0]));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }


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