import interpreter.Interpreter;

import java.io.*;

/**
 * Created by user on 23.09.14.
 */
public class AnsGenerator {

    private static Interpreter interpreter = new Interpreter();
    private final static String path = "tests/operandStackExamples/";

    public static void main(String[] args) {
        final File folder = new File(path);
        makeAnswers(folder);
    }

    public static void makeAnswers(final File folder) {

        for (final File exampleFile : folder.listFiles()) {
            System.out.print(exampleFile.getName() + " ");
            if (!exampleFile.isDirectory() && exampleFile.getName().endsWith(".ps")) {
//                performTestInInterpreter(exampleFile);
                performTestInCompiler(exampleFile);
            } else {
                System.out.println(" is directory");
            }
        }
    }


//    private static void performTestInInterpreter(File exampleFile) {
//        int i = 0;
//        try {
//            double totalSum = 0;
//            interpreter.setCompilingMode(false);
//            for (i = 0; i < TEST_COUNTS; i++) {
//                interpreter.clearRuntime();
//                totalSum += interpreter.run(exampleFile);
//            }
//            System.out.print("\t\t Interpreter " + totalSum / TEST_COUNTS + " vs ");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Interpreter failed at " + i + " step from " + TEST_COUNTS);
//        }
//    }

    private static void performTestInCompiler(File exampleFile) {
        try {
            interpreter.setCompilingMode(true);
            interpreter.clearRuntime();
            interpreter.run(exampleFile);
            File fileDir = new File(exampleFile.getAbsolutePath().replaceAll(".ps", ".txt"));

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));

            out.append(interpreter.operandStackToString()).append("\n");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

