package scanner;

import org.objectweb.asm.*;
import runtime.DynamicClassLoader;
import runtime.Runtime;

import java.io.File;
import java.io.IOException;

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
        System.out.println("\nProgram lasted for " + ((System.currentTimeMillis() - startTime)) + " milliseconds");
    }

    public static void main(String[] args) {
        try {

            DynamicClassLoader.instance.putClass("ASM", AsmBuilder.ASM);
            asm = DynamicClassLoader.instance.loadClass("ASM");

            if (args.length == 0) {
//main examples
                Interpreter.instance.run(new File("bytecode.ps"));
//                Interpreter.instance.run(new File("psRay.ps"));
//                Interpreter.instance.run(new File("plant2.ps"));
//                Interpreter.instance.run(new File("FractalByAlunJones.ps"));
//                Interpreter.instance.run(new File("FractalByAlunJones2.ps"));
//                Interpreter.instance.run(new File("chupcko.ps"));
//                Interpreter.instance.run(new File("snowflak.ps"));
//                Interpreter.instance.run(new File("mandelbrotset.ps"));
//                Interpreter.instance.run(new File("mandel.ps"));
//                Interpreter.instance.run(new File("doretree.ps"));

            } else {
                Interpreter.instance.run(new File(args[0]));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
        }


    }
}

class AsmBuilder implements Opcodes {
    public static final byte[] ASM = ASM();

    private static byte[] ASM() {
        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        cw.visit(V1_6, ACC_PUBLIC | ACC_SUPER, "ASM", null, "java/lang/Object", null);
        cw.visitSource("<generated>", null);

        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "random", "Ljava/util/Random;", null, null);
        fv.visitEnd();

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
        ASM_rand(cw);
        ASM_srand(cw);
        ASM_rrand(cw);
        ASM_clinit(cw);

        return cw.toByteArray();
    }

    private static void ASM_rand(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "rand", "()I", null, null);
        mv.visitCode();

        mv.visitFieldInsn(GETSTATIC, "ASM", "random", "Ljava/util/Random;");
        mv.visitLdcInsn(2147483647);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Random", "nextInt", "(I)I", false);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 0);
        mv.visitEnd();
    }

    private static void ASM_srand(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "srand", "(I)V", null, null);
        mv.visitCode();

        mv.visitTypeInsn(NEW, "java/util/Random");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ILOAD, 0);
        mv.visitInsn(I2L);
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/Random", "<init>", "(J)V", false);
        mv.visitFieldInsn(PUTSTATIC, "ASM", "random", "Ljava/util/Random;");
        mv.visitInsn(RETURN);

        mv.visitMaxs(4, 2);
        mv.visitEnd();
    }

    private static void ASM_rrand(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "rrand", "()I", null, null);
        mv.visitCode();

        mv.visitTypeInsn(NEW, "java/util/Random");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/Random", "<init>", "()V", false);
        mv.visitFieldInsn(PUTSTATIC, "ASM", "random", "Ljava/util/Random;");
        mv.visitFieldInsn(GETSTATIC, "ASM", "random", "Ljava/util/Random;");
        mv.visitLdcInsn(2147483647);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Random", "nextInt", "(I)I", false);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 0);
        mv.visitEnd();
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

        mv.visitInsn(1);
        mv.visitIntInsn(DLOAD, 0);
        mv.visitLdcInsn(3.141592653589793d);
        mv.visitInsn(DMUL);
        mv.visitLdcInsn(180.0d);
        mv.visitInsn(DDIV);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/StrictMath", "cos", "(D)D");
        mv.visitInsn(DRETURN);

        mv.visitMaxs(5, 2);
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
        Label start = new Label();
        mv.visitJumpInsn(IFGT, start);
        mv.visitInsn(DCONST_0);
        mv.visitIntInsn(DLOAD, 0);
        mv.visitInsn(DSUB);
        Label end = new Label();
        mv.visitJumpInsn(GOTO, end);
        mv.visitLabel(start);
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

    private static void ASM_add(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "add", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DADD);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private static void ASM_iadd(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "iadd", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IADD);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_sub(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "sub", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DSUB);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private static void ASM_isub(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "isub", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(ISUB);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_mul(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "mul", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DMUL);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private static void ASM_imul(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "imul", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IMUL);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_div(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "div", "(DD)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitIntInsn(DLOAD, 2);
        mv.visitInsn(DDIV);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }

    private static void ASM_idiv(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "idiv", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IDIV);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_mod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "mod", "(II)I", null, null);
        mv.visitCode();

        mv.visitIntInsn(ILOAD, 0);
        mv.visitIntInsn(ILOAD, 1);
        mv.visitInsn(IREM);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_neg(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "neg", "(D)D", null, null);
        mv.visitCode();

        mv.visitIntInsn(DLOAD, 0);
        mv.visitInsn(DNEG);
        mv.visitInsn(DRETURN);

        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }

    private static void ASM_clinit(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        mv.visitCode();

        mv.visitTypeInsn(NEW, "java/util/Random");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/Random", "<init>", "()V", false);
        mv.visitFieldInsn(PUTSTATIC, "ASM", "random", "Ljava/util/Random;");
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 0);
        mv.visitEnd();
    }
}