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

    private void test(int i) {
        String expectedString = null;
        try {
            Interpreter.instance.run(new File(path + i + srcSuffix));
            expectedString = new Scanner(new File(path + i + resSuffix)).nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String real = Interpreter.instance.operandStackToString().trim();

        Assert.assertEquals(expectedString, real);
    }

    @Test(timeout = 100)
   /*check exec operator*/
    public void execTest() {
        int i = 0;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check compile name calling*/
    public void compileNameCallingTest() {
        int i = 1;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check combination of previous two test*/
    public void compile2Test() {
        int i = 2;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check operator redefinition*/
    public void compile3Test() {
        int i = 3;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check definition and invocation in exec procedure*/
    public void compile4Test() {
        int i = 4;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check repeat and redefinition of system operator*/
    public void compile5Test() {
        int i = 5;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check for inside repeat and sum all numbers*/
    public void compile6Test() {
        int i = 6;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check ifelse inside repeat*/
    public void compile7Test() {
        int i = 7;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check forall*/
    public void compile8Test() {
        int i = 8;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check for inside repeat*/
    public void compile9Test() {
        int i = 9;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check exec inside repeat*/
    public void compile10Test() {
        int i = 10;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check triple repeat*/
    public void compile11Test() {
        int i = 11;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check if inside repeat*/
    public void compile12Test() {
        int i = 12;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check (ifelse with condition) inside repeat*/
    public void compile13Test() {
        int i = 13;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check forall inside repeat*/
    public void compile14Test() {
        int i = 14;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check for with real(double in java)*/
    public void compile15Test() {
        int i = 15;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check redefinition of def*/
    public void compile16Test() {
        int i = 16;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check exit in for*/
    public void compile17Test() {
        int i = 17;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check loop and exit*/
    public void compile18Test() {
        int i = 18;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check sequence of variables and calculations*/
    public void compile19Test() {
        int i = 19;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check sequence of run*/
    public void compile20Test() {
        int i = 20;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check simple for*/
    public void compile21Test() {
        int i = 21;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check bind between interpreting and compiling*/
    public void compile22Test() {
        int i = 22;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check bind*/
    public void compile23Test() {
        int i = 23;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check cvx*/
    public void compile24Test() {
        int i = 24;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check inner cvx*/
    public void compile25Test() {
        int i = 25;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check inner cvx with dictionaries string and boolean*/
    public void compile26Test() {
        int i = 26;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check inner cvx with boolean (compile for boolean)*/
    public void compile27Test() {
        int i = 27;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check inner cvx with literal name */
    public void compile28Test() {
        int i = 28;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check inner cvx for global objects */
    public void compile29Test() {
        int i = 29;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check aload for bytecode */
    public void compile30Test() {
        int i = 30;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check inner cvx for array */
    public void compile31Test() {
        int i = 31;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check string execution */
    public void compile32Test() {
        int i = 32;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check innner string execution */
    public void compile33Test() {
        int i = 33;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check  string execution in the procedure */
    public void compile34Test() {
        int i = 34;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check  bindtest */
    public void compile35Test() {
        int i = 35;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check  compile pop */
    public void compile36Test() {
        int i = 36;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check  exec string */
    public void compile37Test() {
        int i = 37;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
   /*check  exit in executable  string */
    public void compile38Test() {
        int i = 38;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  4 defs for dictstack version debug */
    public void compile39Test() {
        int i = 39;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  save and restore */
    public void compile40Test() {
        int i = 40;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  file and run */
    public void compile41Test() {
        int i = 41;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  file and exec */
    public void compile42Test() {
        int i = 42;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  int inverted for  */
    public void compile43Test() {
        int i = 43;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  int inverted for  && inner exit in double if */
    public void compile44Test() {
        int i = 44;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check  for with name redefinition  */
    public void compile45Test() {
        int i = 45;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check load in procedure  */
    public void compile46Test() {
        int i = 46;
        interpreter.clearRuntime();
        test(i);
    }

    @Test(timeout = 100)
    /*check 2 3 add  */
    public void compile47Test() {
        int i = 47;
        interpreter.clearRuntime();
        test(i);
    }

}
