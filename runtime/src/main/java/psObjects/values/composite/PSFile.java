package psObjects.values.composite;

import procedures.InputStreamProcedure;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.Type;
import runtime.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static psObjects.Attribute.TreatAs.EXECUTABLE;

/**
 * Created by User on 9/2/2015.
 */
public class PSFile extends CompositeValue {

    private File file;

    public PSFile(String name) {
        file = new File(name);
    }

    public PSFile(String name, String access) {
        file = new File(name);
        if (access.equals("w")) {
            file.setWritable(true);
            file.setReadable(true);
        } else if (access.equals("r")) {
            file.setReadOnly();
        } else if (access.equals("a")) {

        } else if (access.equals("w+")) {
            file.setWritable(true);
            file.setReadable(false);
        } else if (access.equals("r+")) {
            file.setWritable(false);
            file.setReadable(true);
        } else if (access.equals("a+")) {

        }
    }

    @Override
    public Type determineType() {
        return Type.FILE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSFile)) return false;
        return true;
    }

    @Override
    public String toStringView(PSObject object) {
        return "-file-";
    }

    public File getFile() {
        return file;
    }

    @Override
    public boolean interpret(Context context, PSObject obj) {
        Attribute attribute = obj.getAttribute();
        Attribute.TreatAs treatAs = attribute.treatAs;
        if (treatAs == EXECUTABLE) {
            try {
                InputStreamProcedure fileProcedure = new InputStreamProcedure(context, "fileProcedure", new InputStreamReader(new FileInputStream(file)));
                if (runtime.isCompiling) {
                    while (fileProcedure.hasNext()) {
                        if (!fileProcedure.execNext()) return false;
                    }
                } else {
                    context.pushToCallStack(fileProcedure);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            context.pushToOperandStack(obj);
        }
        return true;
    }
}
