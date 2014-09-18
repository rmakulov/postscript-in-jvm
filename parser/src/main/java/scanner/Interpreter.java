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
        return (System.currentTimeMillis() - startTime);

//        System.out.println("\nProgram lasted for " + ((System.currentTimeMillis() - startTime)) + " milliseconds");
    }

    public static void main(String[] args) {
        try {

            if (args.length == 0) {
                  /*main examples*/
//                Interpreter.instance.run(new File("0thStep.ps"));
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
//                Interpreter.instance.run(new File("Examples/bytecode.ps"));
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

                String fileName = "Examples/snowflak.ps";
                System.out.print(fileName + "\t");
                double totalSum = 0;
                int testCounts = 1000;
                for (int i = 0; i < testCounts; i++) {
                    totalSum += Interpreter.instance.run(new File(fileName));
//                    Interpreter.instance.runtime.clearAll();
                }
                System.out.print(totalSum / testCounts + " vs ");

                totalSum = 0;
                Interpreter.instance.runtime.switchCompiling();
                for (int i = 0; i < testCounts; i++) {
                    totalSum += Interpreter.instance.run(new File(fileName));
                }
                System.out.println(totalSum / testCounts + " ");


            } else {
                Interpreter.instance.run(new File(args[0]));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }


    }
}