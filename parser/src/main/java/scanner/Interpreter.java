package scanner;

import runtime.Runtime;

import java.io.File;
import java.io.IOException;

public class Interpreter {

    public static Interpreter instance = new Interpreter();
    private runtime.Runtime runtime = Runtime.getInstance();

    public long run(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        runtime.initDefaultDictionaries();
        MainProcedure mainProcedure = new MainProcedure(file);
        runtime.pushToCallStack(mainProcedure);
        runtime.executeCallStack();
//        System.out.println(runtime.operandStackToString());
        return (System.currentTimeMillis() - startTime);

//        System.out.println("\nProgram lasted for " + ((System.currentTimeMillis() - startTime)) + " milliseconds");
    }

    public void clearRuntime() {
        instance.runtime.clearAll();
    }

    public void setCompilingMode(boolean compilingMode) {
        runtime.setCompilingMode(compilingMode);
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                  /*main examples*/
                Interpreter.instance.run(new File("tests/operandStackExamples/5thStep.ps"));

//                Interpreter.instance.run(new File("1stStep.ps"));
//                Interpreter.instance.run(new File("2ndStep.ps"));
//                Interpreter.instance.run(new File("3rdStep.ps"));
//                Interpreter.instance.run(new File("4thStep.ps"));
//                Interpreter.instance.run(new File("5thStep.ps"));
//                Interpreter.instance.run(new File("6thStep.ps"));
//                Interpreter.instance.run(new File("7thStep.ps"));
//                Interpreter.instance.run(new File("8thStep.ps"));
//                Interpreter.instance.run(new File("9thStep.ps"));
//                Interpreter.instance.run(new File("10thStep.ps"));
//                Interpreter.instance.run(new File("11thStep.ps"));
//                Interpreter.instance.run(new File("12thStep.ps"));
//                Interpreter.instance.run(new File("13thStep.ps"));
//                Interpreter.instance.run(new File("14thStep.ps"));
//                Interpreter.instance.run(new File("15thStep.ps"));
//                Interpreter.instance.run(new File("16thStep.ps"));
//                Interpreter.instance.run(new File("17thStep.ps"));
//                Interpreter.instance.run(new File("18thStep.ps"));
//                Interpreter.instance.run(new File("19thStep.ps"));
//                System.out.println(Interpreter.instance.run(new File("Examples/flower.ps")));
//                Interpreter.instance.run(new File("Examples/psRay.ps"));
//                Interpreter.instance.run(new File("Examples/plant2.ps"));
//                Interpreter.instance.run(new File("Examples/FractalByAlunJones.ps"));
//                Interpreter.instance.run(new File("FractalByAlunJones2.ps"));
//                Interpreter.instance.run(new File("Examples/chupcko.ps"));
//                Interpreter.instance.run(new File("Examples/snowflak.ps"));
//                Interpreter.instance.run(new File("Examples/mandelbrotset.ps"));
//                Interpreter.instance.run(new File("Examples/5_star.ps"));
//                Interpreter.instance.run(new File("Examples/6_arcs.ps"));
//                Interpreter.instance.run(new File("Examples/6_Fractal_Arrow.ps"));
//                Interpreter.instance.run(new File("Examples/mandel.ps"));
//                Interpreter.instance.run(new File("Examples/doretree.ps"));
//                Interpreter.instance.run(new File("Examples/WireFrame.eps"));
            } else {
                Interpreter.instance.run(new File(args[0]));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public String operandStackToString() {
        return runtime.operandStackToString();
    }
}