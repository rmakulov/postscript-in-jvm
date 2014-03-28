package scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class MainProcedure extends InputStreamReaderProcedure {
    public MainProcedure(File file) throws FileNotFoundException {
        super("Main procedure", new InputStreamReader(new FileInputStream(file)));
    }
}
