package interpreter;

import procedures.MainProcedure;
import procedures.StringProcedure;
import psObjects.PSObject;
import runtime.Context;
import runtime.Runtime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Interpreter {

    private boolean defaultDictInitState = true;
    private ArrayList<PSObject> customOperators = new ArrayList<PSObject>();
    public static Interpreter instance = new Interpreter();

    public void run(File file) throws IOException {
        runtime.Runtime runtime = Runtime.getInstance();
        if (defaultDictInitState) {
            runtime.initSystemDict(customOperators);
            defaultDictInitState = false;
        }

        Context context = new Context();
        MainProcedure mainProcedure = new MainProcedure(context, file);
        runtime.startMainTask(context, mainProcedure);
    }

    public void run(String string) throws IOException {
        runtime.Runtime runtime = Runtime.getInstance();
        if (defaultDictInitState) {
            runtime.initSystemDict(customOperators);
            defaultDictInitState = false;
        }
        Context context = new Context();
        StringProcedure stringProcedure = new StringProcedure(context, string);
        runtime.startMainTask(context, stringProcedure);
    }

    public void clearRuntime() {
        Runtime.getInstance().clearAll();
    }

    public boolean isCompilingMode() {
        return Runtime.getInstance().isCompiling;
    }

    public void setCompilingMode(boolean compilingMode) {
        Runtime.getInstance().setCompilingMode(compilingMode);
    }

    public static void main(String[] args) {
//        instance.setCompilingMode(true);
        instance.setCompilingMode(false);
        try {
            if (args.length == 0) {
                  /*main examples*/
//                Interpreter.instance.run(new File("tests/operandStackExamples/5thStep.ps"));
                //
//                  instance.run(new File("tests/FontExamples/0.ps"));
//                instance.run(new File("tests/FontExamples/1.ps"));
//                instance.run(new File("tests/FontExamples/2.ps"));
//                instance.run(new File("tests/FontExamples/3.ps"));
//                instance.run(new File("tests/FontExamples/4.ps"));
//                instance.run(new File("tests/FontExamples/5.ps"));
//                instance.run(new File("tests/FontExamples/6.ps"));
//                instance.run(new File("tests/FontExamples/7.ps"));
//                instance.run(new File("tests/FontExamples/8.ps"));
//                instance.run(new File("tests/FontExamples/9_clip.ps"));
//                instance.run(new File("tests/FontExamples/10.ps"));
//                instance.run(new File("tests/operandStackExamples/0Step.ps"));
//                instance.run(new File("Examples/рисунок.ps"));
//                instance.run(new File("Examples/masterpiece.ps"));
//                System.out.println(instance.run(new File("tests/otherExamples/affineIfs.ps")));
//                System.out.println(instance.run(new File("tests/otherExamples/invertStr.ps")));
//                System.out.println(instance.run(new File("tests/otherExamples/drawing.ps")));
//                instance.run(new File("tests/speedTestExamples/Ship_Demo.ps"));
//                System.out.println(instance.run(new File("Examples/tiger.eps")));
//                System.out.println(Interpreter.instance.run(new File("Examples/flower.ps")));
//                instance.run(new File("Examples/plant2.ps"));
//                instance.run(new File("Examples/chupcko.ps"));
//                instance.run(new File("Examples/snowflak.ps"));

//                System.out.println(instance.run(new File("tests/speedTestExamples/7_ellipses.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/snowflakByPozdin.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/chupcko.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/WireFrame.eps")));
//                instance.run(new File("tests/speedTestExamples/hard/mandel.ps"));
//                System.out.println(instance.run(new File("tests/speedTestExa mples/escher.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/8_cube1.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/8_cube2.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/6_arcs.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/mandelbrotset.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/flower.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/simpleGraphicsTest1.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/4_circles.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/psRay.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/Butterfly-Vector_Sample.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/6_Fractal_Arrow.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/henon.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/drawing.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/1_clip.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/1_rectangles.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/simpleGraphicsTest.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/gsaveTest.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/mandelbrotseterxample.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/FractalByAlunJones2.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/gingerbread.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/julia.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/colorcir.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/tiger.eps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/2_trapezoid.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/FractalByAlunJones.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/bytecode.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/doretree.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/snowflak.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/drop.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/5_star.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/appolonain_net.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/bindTest.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/1dca.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/hard/bubbles.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/plant2.ps")));
//                System.out.println(instance.run(new File("tests/speedTestExamples/tiger.eps")));

//                instance.run(new File("tests/otherExamples/cube6.ps"));
//                                instance.run(new File("tests/operandStackExamples/36Step.ps"));

//                System.out.println(instance.run(new File("tests/otherExamples/vasarely.ps")));


            } else {
                String fileName = args[0];
                String mode = args[1];
                if (mode.equals("-i")) {
                    instance.setCompilingMode(false);
                } else if (mode.equals("-c")) {
                    instance.setCompilingMode(false);
                }
                Interpreter.instance.run(new File(fileName));
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public String operandStackToString() {
        return Runtime.getInstance().getMainContext().operandStackToString();
    }

    public void setCustomOperators(ArrayList<PSObject> customOperators) {
        this.customOperators = customOperators;
    }
}