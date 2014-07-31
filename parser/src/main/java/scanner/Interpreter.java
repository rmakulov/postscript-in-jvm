package scanner;

import runtime.Runtime;

import java.io.File;
import java.io.IOException;

public class Interpreter {

    public static Interpreter instance = new Interpreter();
    public static final int READ_BUFFER_SIZE = 1024;
    protected static byte[] content;
    private runtime.Runtime runtime = Runtime.getInstance();


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

            if (args.length == 0) {
//main examples
//                Interpreter.instance.run(new File("Examples/bytecode.ps"));
//                Interpreter.instance.run(new File("Examples/psRay.ps"));
//                Interpreter.instance.run(new File("Examples/plant2.ps"));
//                Interpreter.instance.run(new File("Examples/FractalByAlunJones.ps"));
//                Interpreter.instance.run(new File("FractalByAlunJones2.ps"));
//                Interpreter.instance.run(new File("Examples/chupcko.ps"));
                Interpreter.instance.run(new File("Examples/snowflak.ps"));
//                Interpreter.instance.run(new File("Examples/mandelbrotset.ps"));
//                Interpreter.instance.run(new File("Examples/mandel.ps"));
//                Interpreter.instance.run(new File("Examples/doretree.ps"));

            } else {
                Interpreter.instance.run(new File(args[0]));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }


    }
}