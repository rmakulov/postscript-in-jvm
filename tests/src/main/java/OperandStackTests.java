import interpreter.Interpreter;
import org.junit.Assert;
import org.junit.Test;

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
//        interpreter.setCompilingMode(false);
    }

    @Test
   /*check exec operator*/
    public void execTest() {
        int i = 0;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();

        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check compile name calling*/
    public void compileNameCallingTest() {
        int i = 1;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check combination of previous two test*/
    public void compile2Test() {
        int i = 2;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check operator redefinition*/
    public void compile3Test() {
        int i = 3;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check definition and invocation in exec procedure*/
    public void compile4Test() {
        int i = 4;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check repeat and redefinition of system operator*/
    public void compile5Test() {
        int i = 5;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check for inside repeat and sum all numbers*/
    public void compile6Test() {
        int i = 6;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check ifelse inside repeat*/
    public void compile7Test() {
        int i = 7;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check forall*/
    public void compile8Test() {
        int i = 8;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check for inside repeat*/
    public void compile9Test() {
        int i = 9;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check exec inside repeat*/
    public void compile10Test() {
        int i = 10;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check triple repeat*/
    public void compile11Test() {
        int i = 11;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check if inside repeat*/
    public void compile12Test() {
        int i = 12;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check (ifelse with condition) inside repeat*/
    public void compile13Test() {
        int i = 13;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check forall inside repeat*/
    public void compile14Test() {
        int i = 14;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check for with real(double in java)*/
    public void compile15Test() {
        int i = 15;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check redefinition of def*/
    public void compile16Test() {
        int i = 16;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check exit in for*/
    public void compile17Test() {
        int i = 17;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check loop and exit*/
    public void compile18Test() {
        int i = 18;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check sequence of variables and calculations*/
    public void compile19Test() {
        int i = 19;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check sequence of run*/
    public void compile20Test() {
        int i = 20;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check simple for*/
    public void compile21Test() {
        int i = 21;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check bind between interpreting and compiling*/
    public void compile22Test() {
        int i = 22;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check bind*/
    public void compile23Test() {
        int i = 23;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check cvx*/
    public void compile24Test() {
        int i = 24;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check inner cvx*/
    public void compile25Test() {
        int i = 25;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check inner cvx with dictionaries string and boolean*/
    public void compile26Test() {
        int i = 26;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check inner cvx with boolean (compile for boolean)*/
    public void compile27Test() {
        int i = 27;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check inner cvx with literal name */
    public void compile28Test() {
        int i = 28;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check inner cvx for global objects */
    public void compile29Test() {
        int i = 29;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check aload for bytecode */
    public void compile30Test() {
        int i = 30;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        if (interpreter.isCompilingMode()) {
            Assert.assertEquals(expectedString, real);
        } else Assert.assertTrue(true);
    }

    @Test
   /*check inner cvx for array */
    public void compile31Test() {
        int i = 31;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check string execution */
    public void compile32Test() {
        int i = 32;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check innner string execution */
    public void compile33Test() {
        int i = 33;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check  string execution in the procedure */
    public void compile34Test() {
        int i = 34;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check  bindtest */
    public void compile35Test() {
        int i = 35;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check  compile pop */
    public void compile36Test() {
        int i = 36;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
   /*check  exec string */
    public void compile37Test() {
        int i = 37;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test

   /*check  exit in executable  string */
    public void compile38Test() {
        int i = 38;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
    /*check  4 defs for dictstack version debug */
    public void compile39Test() {
        int i = 39;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
    /*check  save and restore */
    public void compile40Test() {
        int i = 40;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
    /*check  the simplest test */
    public void compile41Test() {
        int i = 41;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
    /*check  ifne in psname // part of/from 8_cube.ps*/
    public void compile42Test() {
        int i = 42;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }

    @Test
    /*check literal array definition and call*/
    public void compile43Test() {
        int i = 43;
        interpreter.clearRuntime();
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = interpreter.operandStackToString().trim();
        Assert.assertEquals(expectedString, real);
    }
}
