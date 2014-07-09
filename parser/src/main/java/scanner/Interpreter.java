package scanner;

import operators.control.ExecOp;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSMark;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import psObjects.values.simple.numbers.PSReal;
import runtime.DynamicClassLoader;
import runtime.Runtime;

import java.io.File;
import java.io.IOException;

import static psObjects.Attribute.TreatAs.EXECUTABLE;
import static psObjects.Attribute.TreatAs.LITERAL;

public class Interpreter {
    public static Interpreter instance = new Interpreter();
    public static final int READ_BUFFER_SIZE = 1024;
    protected static byte[] content;
    private runtime.Runtime runtime = Runtime.getInstance();
    public static Class<?> asm = null;


    private Interpreter() {

    }

    public void run(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        runtime.initDefaultDictionaries();
        MainProcedure mainProcedure = new MainProcedure(file);
        runtime.pushToCallStack(mainProcedure);
        runtime.executeCallStack();
        System.out.println("Program lasted for " + ((System.currentTimeMillis() - startTime)) + " milliseconds");
        /*Yylex scanner = new Yylex(new InputStreamReader(new FileInputStream(file)));
        Yytoken yytoken;
        while ((yytoken = scanner.yylex()) != null) {
            execToken(scanner, yytoken);
            runtime.executeCallStack();
        }*/
    }

    private void execToken(Yylex scanner, Yytoken yytoken) {
        String text = yytoken.m_text;
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


    public static void main(String[] args) {
        try {

            DynamicClassLoader.instance.putClass("ASM", AsmBuilder.ASM);
            asm = DynamicClassLoader.instance.loadClass("ASM");

            if (args.length == 0) {
//main examples
//                Interpreter.instance.run(new File("SimpleExample_0.eps"));
//                Interpreter.instance.run(new File("WireFrame.eps"));
//                Interpreter.instance.run(new File("colorcir.ps"));
                Interpreter.instance.run(new File("bytecode.ps"));
//                Interpreter.instance.run(new File("plant2.ps"));
//                Interpreter.instance.run(new File("FractalByAlunJones.ps"));
//                Interpreter.instance.run(new File("FractalByAlunJones2.ps"));
//                Interpreter.instance.run(new File("chupcko.ps"));
//                Interpreter.instance.run(new File("snowflak.ps"));
//                Interpreter.instance.run(new File("mandelbrotset.ps"));
//                Interpreter.instance.run(new File("mandel.ps"));
//                Interpreter.instance.run(new File("doretree.ps"));

//other examples
//              Interpreter.instance.run(new File("7_ellipses.ps"));
//              Interpreter.instance.run(new File("6_arcs.ps"));
//              Interpreter.instance.run(new File("5_star.ps"));
//              Interpreter.instance.run(new File("6_Fractal_Arrow.ps"));
//                Interpreter.instance.run(new File("4_circles.ps"));
//                Interpreter.instance.run(new File("mandelbrotset.ps"));
//              Interpreter.instance.run(new File("1_clip.ps"));
//              Interpreter.instance.run(new File("1_rectangles.ps"));
//              Interpreter.instance.run(new File("SimpleGraphicsTest.ps"));
//              Interpreter.instance.run(new File("SimpleGraphicsTest1.ps"));


//                for(int i = 45 ; i < 80 ; i++){
//                    TransformMatrix c = new TransformMatrix() ;
//                    c.rotate((double)i*Math*PI/180) ;
//                    c.scale(3,3) ;
//                    c.translate(23,43);
//                    c.scale(2,1) ;
//                    System.out.println(c.getRotateAngle() );
//                }


//                Interpreter.instance.run(new File("gsaveTest.ps"));
//                Interpreter.instance.run(new File("copyTest.ps"));

//                Interpreter.instance.run(new File("test.ps"));
                //Interpreter.instance.run(new File("bindTest.ps"));
                //Interpreter.instance.run(new File("test.ps"));
//                Interpreter.instance.run(new File("6_arcs.ps"));
//                Interpreter.instance.run(new File("2_trapezoid.ps"));
//                Interpreter.instance.run(new File("tiger_0.eps"));
//                Interpreter.instance.run(new File("cells.ps"));
//                Interpreter.instance.run(new File("6_arcs.ps"));
//                Interpreter.instance.run(new File("fractal.ps"));
//                Interpreter.instance.run(new File("psRay.ps"));
//                Interpreter.instance.run(new File("julia.ps"));
                //                Interpreter.instance.run(new File("6_arcs.ps"));
//                Interpreter.instance.run(new File("testim.ps"));
//                Interpreter.instance.run(new File("cube6.ps"));
                //Interpreter.instance.run(new File("sphere1.ps"));
//                Interpreter.instance.run(new File("appolonain_net.ps"));
//                Interpreter.instance.run(new File("flower.ps"));
//                Interpreter.instance.run(new File("tiger.eps"));
//                Interpreter.instance.run(new File("tiger0.eps"));
//                Interpreter.instance.run(new File("mandel.ps"));
//                Interpreter.instance.run(new File("6_arcs.ps"));
                //Interpreter.instance.run(new File("affineIfs.ps"));
                //Interpreter.instance.run(new File("3_setdash.ps"));

            } else {
                Interpreter.instance.run(new File(args[0]));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
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

class AsmBuilder implements Opcodes {
    public static final byte[] ASM = ASM();

    private static byte[] ASM() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, "ASM", null, "java/lang/Object", null);
        cw.visitSource("<generated>", null);

        ASM_add(cw);
        ASM_iadd(cw);
        ASM_sub(cw);
        ASM_isub(cw);
        ASM_mul(cw);
        ASM_imul(cw);
        ASM_div(cw);
        ASM_idiv(cw);
        ASM_mod(cw);
        ASM_neg(cw);
        ASM_atan(cw);
        ASM_exp(cw);
        ASM_abs(cw);
        ASM_sqrt(cw);
        ASM_ceiling(cw);
        ASM_cos(cw);
        ASM_sin(cw);
        ASM_floor(cw);
        ASM_ln(cw);
        ASM_log(cw);
        ASM_truncate(cw);
        ASM_round(cw);

        return cw.toByteArray();
    }

    private static void ASM_round(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "round", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "round", "(D)J");
        mv.visitInsn(L2D);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_truncate(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "truncate", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitInsn(D2I);
        mv.visitInsn(I2D);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_log(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "log", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "log10", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_ln(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "ln", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "log", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_floor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "floor", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "floor", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_sin(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "sin", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitLdcInsn(3.141592653589793d);
        mv.visitInsn(DMUL);
        mv.visitLdcInsn(180.0d);
        mv.visitInsn(DDIV);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "sin", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(4, 2);
        mv.visitEnd();
    }

    private static void ASM_cos(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "cos", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitLdcInsn(3.141592653589793d);
        mv.visitInsn(DMUL);
        mv.visitLdcInsn(180.0d);
        mv.visitInsn(DDIV);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "cos", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(4, 2);
        mv.visitEnd();
    }

    private static void ASM_ceiling(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "ceiling", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "ceil", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_sqrt(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "sqrt", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "sqrt", "(D)D");

        mv.visitInsn(DRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_abs(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "abs", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitInsn(DCONST_0);
        mv.visitInsn(DCMPG);
        Label label = new Label();
        mv.visitJumpInsn(IFGT, label);
        mv.visitInsn(DCONST_0);
        mv.visitIntInsn(DLOAD, 0);
        mv.visitInsn(DSUB);
        Label end = new Label();
        mv.visitJumpInsn(GOTO, end);
        mv.visitLabel(label);
        mv.visitFrame(F_SAME, 0, null, 0, null);
        mv.visitIntInsn(DLOAD, 0);
        mv.visitLabel(end);
        mv.visitFrame(F_SAME, 0, null, 0, null);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 2);
        mv.visitEnd();
    }

    private static void ASM_exp(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "exp", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "pow", "(DD)D");

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private static void ASM_atan(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "atan", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "atan2", "(DD)D");
        mv.visitLdcInsn(3.141592653589793d);
        mv.visitInsn(DMUL);
        mv.visitLdcInsn(180.0d);
        mv.visitInsn(DDIV);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    static void ASM_add(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "add", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DADD);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    public static void ASM_iadd(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "iadd", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IADD);

        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    public static void ASM_sub(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "sub", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DSUB);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    public static void ASM_isub(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "isub", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(ISUB);

        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    public static void ASM_mul(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "mul", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DMUL);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    public static void ASM_imul(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "imul", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IMUL);

        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    public static void ASM_div(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "div", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DDIV);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    public static void ASM_idiv(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "idiv", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IDIV);

        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    public static void ASM_mod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "mod", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IREM);

        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    public static void ASM_neg(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "neg", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitInsn(DNEG);

        mv.visitInsn(DRETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

}