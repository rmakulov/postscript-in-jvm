import scanner.Interpreter;

import java.io.File;
import java.io.IOException;

/**
 * Created by User on 23/9/2014.
 */
public class SpeedTests {

    private static Interpreter interpreter = new Interpreter();
    static final int TEST_COUNTS = 1;

    public static void main(String[] args) {
        final File folder = new File("tests/speedTestExamples");
        iterateExamples(folder);
    }

    public static void iterateExamples(final File folder) {

        for (final File exampleFile : folder.listFiles()) {
            System.out.print(exampleFile.getName() + " ");
            if (!exampleFile.isDirectory()) {
                performTestInInterpreter(exampleFile);
                performTestInCompiler(exampleFile);
            } else {
                System.out.println(" is directory");
            }
        }
    }


    private static void performTestInInterpreter(File exampleFile) {
        int i = 0;
        try {
            double totalSum = 0;
            interpreter.setCompilingMode(false);
            for (i = 0; i < TEST_COUNTS; i++) {
                interpreter.clearRuntime();
                totalSum += interpreter.run(exampleFile);
            }
            System.out.print("\t\t Interpreter " + totalSum / TEST_COUNTS + " vs ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Interpreter failed at " + i + " step from " + TEST_COUNTS);
        }
    }

    private static void performTestInCompiler(File exampleFile) {
        int i = 0;
        try {
            double totalSum = 0;
            interpreter.setCompilingMode(true);
            for (i = 0; i < TEST_COUNTS; i++) {
                interpreter.clearRuntime();
                totalSum += interpreter.run(exampleFile);
            }
            System.out.println("\t\t Compiler " + totalSum / TEST_COUNTS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Interpreter failed at " + i + " step from " + TEST_COUNTS);
        }
    }

}
