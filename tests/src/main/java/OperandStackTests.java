import org.junit.Assert;
import org.junit.Test;
import scanner.Interpreter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by user on 23.09.14.
 */
public class OperandStackTests {
    private static Interpreter interpreter = new Interpreter();
    private final static String path = "tests/operandStackExamples/";
    private String srcSuffix = "Step.ps";
    private String resSuffix = "Step.txt";

    static {
        interpreter.setCompilingMode(true);
    }

    @Test
   /*check exec operator*/
    public void execTest() {
        int i = 0;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check compile name calling*/
    public void compileNameCallingTest() {
        int i = 1;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile2Test() {
        int i = 2;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile3Test() {
        int i = 3;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile4Test() {
        int i = 4;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile5Test() {
        int i = 5;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile6Test() {
        int i = 6;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile7Test() {
        int i = 7;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile8Test() {
        int i = 8;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile9Test() {
        int i = 9;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile10Test() {
        int i = 10;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile11Test() {
        int i = 11;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile12Test() {
        int i = 12;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile13Test() {
        int i = 13;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile14Test() {
        int i = 14;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile15Test() {
        int i = 15;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile16Test() {
        int i = 16;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile17Test() {
        int i = 17;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile18Test() {
        int i = 18;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

    @Test
   /*check number compiling*/
    public void compile19Test() {
        int i = 19;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(real, expectedString);
    }

}
