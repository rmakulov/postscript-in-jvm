package procedures;

import runtime.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class MainProcedure extends InputStreamProcedure {
    public MainProcedure(Context context, File file) throws FileNotFoundException {
        super(context, "Main procedure", new InputStreamReader(new FileInputStream(file)));
    }
}
