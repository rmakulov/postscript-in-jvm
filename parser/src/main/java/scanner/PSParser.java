package scanner;

import psObjects.reference.Reference;
import psObjects.simple.PSInteger;
import runtime.Runtime;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Example showing how to convert a Postscript document to PDF using the high level API.
 *
 * @author Gilles Grousset (gi.grousset@gmail.com)
 */
public class PSParser {
    public static final int READ_BUFFER_SIZE = 1024;
    protected static byte[] content;
    private static runtime.Runtime runtime = Runtime.getInstance();

    public static void main(String[] args) throws IOException {


        Yylex scanner = new Yylex(new InputStreamReader(new FileInputStream("test.ps")));
        Yytoken yytoken;

        while((yytoken = scanner.yylex())!=null) {
            String text = yytoken.m_text;
            System.out.print(text + " ");
            switch (yytoken.m_index){
                case 42:
                    runtime.pushToOperandStack(runtime.createReference(new PSInteger(Integer.parseInt(text))));
                    break;
                case 43:
                    if(text.equals("add")){
                       Reference r1 = runtime.popFromOperandStack();
                       Reference r2 = runtime.popFromOperandStack();
                        PSInteger i1 = (PSInteger) r1.getPSObject();
                        PSInteger i2 = (PSInteger) r2.getPSObject();
                       runtime.pushToOperandStack(runtime.createReference(PSInteger.add(i1,i2)));
                    }

                default:
                    break;
            }
        }

//        String[] argv = new String[1];
//        argv[0] = "test.ps";
//        Yylex.main(argv);


//        Yylex scanner = new Yylex(new InputStreamReader(new FileInputStream(argv[0])));
//        do {
//            System.out.println(scanner.yylex());
//        }
        System.out.print("Done");
    }
}