package procedures;


import psObjects.PSObject;
import psObjects.values.composite.PSString;
import runtime.Context;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


/**
 * Created by Дмитрий on 28.03.14.
 */
public class StringProcedure extends InputStreamProcedure {
    public StringProcedure(Context context, PSObject o) throws UnsupportedEncodingException {
        this(context, ((PSString) o.getValue()).getString());
    }

    public StringProcedure(Context context, String s) throws UnsupportedEncodingException {
        super(context, "String procedure", new InputStreamReader(new ByteArrayInputStream(s.getBytes("UTF-8"))));
    }
}
