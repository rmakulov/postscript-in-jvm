import interpreter.Interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by User on 23/9/2014.
 */
public class SpeedTests {

    private static Interpreter interpreter;
    static final int TEST_COUNTS = 15;
//    static final int TEST_COUNTS = 2;


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        final File folder = new File("tests/speedTestExamples");
        final File folder = new File("tests/speedTestExamples/hard");
        iterateExamples(folder);
        long ms = System.currentTimeMillis() - startTime;
        System.out.println("All tests have been finished in " + ((double) ms) / (1000 * 60 * 60) + " hours.");
    }

    public static void iterateExamples(final File folder) {
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%s\n",
                "Filename",
                "Interpreter(avg)", "Interpreter(disp)",
                "Compiler(avg)", "Compiler(disp)",
                "Without Caching(avg)", "Without Caching(disp)");

        File[] fileList = folder.listFiles();
        assert fileList != null;
        Arrays.sort(fileList);

        for (final File exampleFile : fileList) {
            if(!exampleFile.getName().equals("psRay.ps")) continue;
            if (TEST_COUNTS == 0) {
                System.out.println(exampleFile.getName());
            } else {
                if (!exampleFile.isDirectory()) {
                    TestResult result1 = performTest(false,true, exampleFile);
                    TestResult result2 = performTest(true,true, exampleFile);
                    TestResult result3 = performTest(true,false, exampleFile);
                    System.out.printf("%-30s%-20.3f%-20.1f%-20.3f%-20.1f%-20.3f%.1f\n",
                            exampleFile.getName(),
                            result1.avg, (result1.dispersion*100),
                            result2.avg, (result2.dispersion*100),
                            result3.avg, (result3.dispersion*100));
                } else {
                    System.out.println(" is directory");
                }
            }
        }
    }


    private static TestResult performTest(boolean isCompiling,boolean enableNameCaching, File exampleFile) {
        int i = 0;
        double totalSum = 0;
        interpreter = new Interpreter();
        long[] times = new long[TEST_COUNTS];
        double avg = 0;
        double dispersion = 0;
        try {
            interpreter.setCompilingMode(isCompiling);
            interpreter.setNameCaching(enableNameCaching);
            prepareJavaVM(exampleFile);
            for (i = 0; i < TEST_COUNTS; i++) {
                interpreter.clearRuntime();
                long time = interpreter.runWithTime(exampleFile);
                times[i] = time;
                totalSum += time;
            }
            avg = totalSum / TEST_COUNTS;
            for (i = 0; i < TEST_COUNTS; i++) {
                double v = times[i] - avg;
                dispersion += v * v;
            }
            dispersion = dispersion / (TEST_COUNTS - 1);
            dispersion = Math.sqrt(dispersion);
            dispersion = dispersion / avg;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            String executor = isCompiling ? "Compiler" : "Interpreter";
            System.out.println(executor + " (" + exampleFile.getName() + ") failed at " + i + " step from " + (TEST_COUNTS - 1));
        }
        return new TestResult(avg, dispersion);
    }

    private static void prepareJavaVM(File exampleFile) throws IOException {
        interpreter.clearRuntime();
        interpreter.runWithTime(exampleFile);
    }

    private static class TestResult {
        double avg;
        double dispersion;

        public TestResult(double avg, double dispersion) {
            this.avg = avg;
            this.dispersion = dispersion;
        }
    }
}
