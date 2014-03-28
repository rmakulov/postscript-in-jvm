package scanner;


import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


/**
 * Created by Дмитрий on 28.03.14.
 */
public class StringProcedure extends InputStreamReaderProcedure {
    public StringProcedure(String s) throws UnsupportedEncodingException {
        super("String procedure", new InputStreamReader(new ByteArrayInputStream(s.getBytes("UTF-8"))));
    }
}
