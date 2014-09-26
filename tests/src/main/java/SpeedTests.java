import scanner.Interpreter;

import java.io.File;
import java.io.IOException;

/**
 * Created by User on 23/9/2014.
 */
public class SpeedTests {

    private static Interpreter interpreter;
    static final int TEST_COUNTS = 1;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        final File folder = new File("tests/speedTestExamples");
        iterateExamples(folder);
        long ms = System.currentTimeMillis() - startTime;
        System.out.println("All tests have been finished in " + ms / (1000 * 60 * 60) + " hours.");
    }

    public static void iterateExamples(final File folder) {
        System.out.printf("%-20s%-20s%s\n", "Filename", "Interpreter", "Compiler");

        for (final File exampleFile : folder.listFiles()) {
            if (!exampleFile.isDirectory()) {
                double v1 = performTestInInterpreter(exampleFile);
                double v2 = performTestInCompiler(exampleFile);
                System.out.printf("%-30s%-20.3f%.3f\n", exampleFile.getName(), v1, v2);
            } else {
                System.out.println(" is directory");
            }
        }
    }


    private static double performTestInInterpreter(File exampleFile) {
        int i = 0;
        double totalSum = 0;
        interpreter = new Interpreter();
        try {
            interpreter.setCompilingMode(false);
            for (i = 0; i < TEST_COUNTS; i++) {
                interpreter.clearRuntime();
                totalSum += interpreter.run(exampleFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Interpreter failed at " + i + " step from " + TEST_COUNTS);
        }
        return totalSum / TEST_COUNTS;
    }

    private static double performTestInCompiler(File exampleFile) {
        int i = 0;
        double totalSum = 0;
        interpreter = new Interpreter();
        try {
            interpreter.setCompilingMode(true);
            for (i = 0; i < TEST_COUNTS; i++) {
                interpreter.clearRuntime();
                totalSum += interpreter.run(exampleFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Compiler failed at " + i + " step from " + TEST_COUNTS);
        }
        return totalSum / TEST_COUNTS;
    }
}
