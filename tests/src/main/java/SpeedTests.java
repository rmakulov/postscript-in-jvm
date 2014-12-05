import interpreter.Interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by User on 23/9/2014.
 */
public class SpeedTests {

    private static Interpreter interpreter;
    static final int TEST_COUNTS = 10;


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        final File folder = new File("tests/speedTestExamples");
        final File folder = new File("tests/speedTestExamples/hard");
        iterateExamples(folder);
        long ms = System.currentTimeMillis() - startTime;
        System.out.println("All tests have been finished in " + ((double) ms) / (1000 * 60 * 60) + " hours.");
    }

    public static void iterateExamples(final File folder) {
        System.out.printf("%-20s%-20s%s\n", "Filename", "Interpreter", "Compiler");

        File[] fileList = folder.listFiles();
        assert fileList != null;
        Arrays.sort(fileList);

        for (final File exampleFile : fileList) {
            if (TEST_COUNTS == 0) {
                System.out.println(exampleFile.getName());
            } else {
                if (!exampleFile.isDirectory()) {
                    double v1 = performTest(false, exampleFile);
                    double v2 = performTest(true, exampleFile);
                    System.out.printf("%-30s%-20.3f%.3f\n", exampleFile.getName(), v1, v2);
                } else {
                    System.out.println(" is directory");
                }
            }
        }
    }


    private static double performTest(boolean isCompiling, File exampleFile) {
        int i = 0;
        double totalSum = 0;
        interpreter = new Interpreter();
        try {
            interpreter.setCompilingMode(isCompiling);
            prepareJavaVM(exampleFile);
            for (i = 0; i < TEST_COUNTS; i++) {
                interpreter.clearRuntime();
                totalSum += interpreter.run(exampleFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            String executor = isCompiling ? "Compiler" : "Interpreter";
            System.out.println(executor + " (" + exampleFile.getName() + ") failed at " + i + " step from " + (TEST_COUNTS - 1));
        }
        return totalSum / TEST_COUNTS;
    }

    private static void prepareJavaVM(File exampleFile) throws IOException {
        interpreter.clearRuntime();
        interpreter.run(exampleFile);
    }
}
