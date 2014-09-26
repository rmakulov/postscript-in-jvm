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
        instance.setCompilingMode(false);
        try {
            if (args.length == 0) {
                  /*main examples*/
//                Interpreter.instance.run(new File("tests/operandStackExamples/5thStep.ps"));

//                instance.run(new File("1stStep.ps"));
//                instance.run(new File("2ndStep.ps"));
//                instance.run(new File("3rdStep.ps"));
//                instance.run(new File("4thStep.ps"));
//                instance.run(new File("5thStep.ps"));
//                instance.run(new File("6thStep.ps"));
//                instance.run(new File("7thStep.ps"));
//                instance.run(new File("8thStep.ps"));
//                instance.run(new File("9thStep.ps"));
//                instance.run(new File("10thStep.ps"));
//                instance.run(new File("11thStep.ps"));
//                instance.run(new File("12thStep.ps"));
//                instance.run(new File("13thStep.ps"));
//                instance.run(new File("14thStep.ps"));
//                instance.run(new File("15thStep.ps"));
//                instance.run(new File("16thStep.ps"));
//                instance.run(new File("17thStep.ps"));
//                instance.run(new File("18thStep.ps"));
//                instance.run(new File("tests/speedTestExamples/Abstract_Floral_Butterfly_Vector_Graphic.ps"));
//                instance.run(new File("tests/speedTestExamples/Final_Composition.ps"));
//                instance.run(new File("tests/speedTestExamples/drop.ps"));
//                instance.run(new File("Examples/рисунок.ps"));
//                instance.run(new File("Examples/drawing.ps"));
//                instance.run(new File("Examples/triangle.ps"));
//                instance.run(new File("Examples/masterpiece.ps"));
//                instance.run(new File("Examples/Ship_Demo.ps"));
//                instance.run(new File("Examples/tiger.eps"));
//                System.out.println(Interpreter.instance.run(new File("Examples/flower.ps")));
//                instance.run(new File("Examples/psRay.ps"));
//                instance.run(new File("Examples/plant2.ps"));
//                instance.run(new File("Examples/FractalByAlunJones.ps"));
//                instance.run(new File("FractalByAlunJones2.ps"));
//                instance.run(new File("Examples/chupcko.ps"));
//                instance.run(new File("Examples/snowflak.ps"));
//                instance.run(new File("Examples/mandelbrotset.ps"));
//                instance.run(new File("Examples/5_star.ps"));
//                instance.run(new File("Examples/6_arcs.ps"));
//                instance.run(new File("Examples/6_Fractal_Arrow.ps"));
//                instance.run(new File("Examples/mandel.ps"));
//                instance.run(new File("Examples/doretree.ps"));
//                instance.run(new File("Examples/WireFrame.eps"));
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