package procedures;

import psObjects.PSObject;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class StringProcedure extends Procedure {
    PSObject psStringObject;

    public StringProcedure(String procName, PSObject psObject) {
        super(procName);
        psStringObject = psObject;
    }

    public StringProcedure(PSObject psStringObject) {
        super("String procedure");
        this.psStringObject = psStringObject;
    }

    @Override
    public boolean hasNext() {
        return false;        //todo
    }

    @Override
    protected PSObject next() {
        return null;        //todo
    }
}
