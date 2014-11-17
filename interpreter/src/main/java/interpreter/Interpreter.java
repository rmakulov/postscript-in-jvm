package interpreter;

import procedures.MainProcedure;
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
//        System.out.println("DictStackVersion " + runtime.getDictStackVersion());

        return (System.currentTimeMillis() - startTime);

//        System.out.println("\nProgram lasted for " + ((System.currentTimeMillis() - startTime)) + " milliseconds");
    }

    public void clearRuntime() {
        instance.runtime.clearAll();
    }

    public void setCompilingMode(boolean compilingMode) {
        runtime.setCompilingMode(compilingMode);
    }

    public boolean isCompilingMode() {
        return runtime.isCompiling;
    }

    public static void main(String[] args) {
        instance.setCompilingMode(true);
//        instance.setCompilingMode(false);
        try {
            if (args.length == 0) {
                  /*main examples*/
//                Interpreter.instance.run(new File("tests/operandStackExamples/5thStep.ps"));
//                  instance.run(new File("tests/FontExamples/1.ps"));
//                instance.run(new File("tests/FontExamples/2.ps"));
//                instance.run(new File("tests/FontExamples/3.ps"));
//                instance.run(new File("tests/FontExamples/4.ps"));
//                instance.run(new File("tests/FontExamples/5.ps"));
//                instance.run(new File("tests/FontExamples/6.ps"));
//                instance.run(new File("tests/FontExamples/8.ps"));
//                instance.run(new File("tests/otherExamples/Diagram.ps"));
//                instance.run(new File("tests//simpleExample_0.eps"));
//                instance.run(new File("tests/otherExamples/simpleExample.eps"));
//                instance.run(new File("tests/operandStackExamples/0Step.ps"));
//                instance.run(new File("tests/otherExamples/cells.ps"));
//                instance.run(new File("Examples/рисунок.ps"));
//                instance.run(new File("Examples/masterpiece.ps"));
//                instance.run(new File("Examples/Ship_Demo.ps"));
//                System.out.println(instance.run(new File("Examples/tiger.eps")));
//                System.out.println(Interpreter.instance.run(new File("Examples/flower.ps")));
//                instance.run(new File("Examples/plant2.ps"));
//                instance.run(new File("Examples/FractalByAlunJones.ps"));
//                instance.run(new File("FractalByAlunJones2.ps"));
//                instance.run(new File("Examples/chupcko.ps"));
//                instance.run(new File("Examples/snowflak.ps"));

//                System.out.println(instance.run(new File("tests/speedTestExamples/7_ellipses.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/snowflakByPozdin.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/chupcko.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/WireFrame.eps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/mandel.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/6_arcs.ps")));

//             System.out.println(instance.run(new File("tests/speedTestExamples/hard/mandelbrotset.ps")));
                // System.out.println(instance.run(new File("tests/speedTestExamples/flower.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/simpleGraphicsTest1.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/4_circles.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/psRay.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/Butterfly-Vector_Sample.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/6_Fractal_Arrow.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/henon.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/drawing.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/1_clip.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/1_rectangles.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/simpleGraphicsTest.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/gsaveTest.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/mandelbrotseterxample.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/FractalByAlunJones2.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/gingerbread.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/julia.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/colorcir.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/tiger.eps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/2_trapezoid.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/FractalByAlunJones.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/bytecode.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/doretree.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/snowflak.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/drop.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/5_star.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/appolonain_net.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/bindTest.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/1dca.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/bubbles.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/plant2.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/tiger.eps")));

//                instance.run(new File("tests/otherExamples/cube6.ps"));
//                                instance.run(new File("tests/operandStackExamples/36Step.ps"));

//                System.out.println(instance.run(new File("tests/otherExamples/vasarely.ps")));


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