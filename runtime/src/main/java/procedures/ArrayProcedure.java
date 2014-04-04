package procedures;

import psObjects.PSObject;
import psObjects.values.composite.PSArray;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class ArrayProcedure extends Procedure {
    protected int nextIndex = 0;
    protected PSObject arrayObj;

    public ArrayProcedure(String name, PSObject arrayObj) {
        super(name);
        this.arrayObj = arrayObj;
    }

    public ArrayProcedure(PSObject arrayObj) {
        this.arrayObj = arrayObj;
    }

    public boolean hasNext() {
        return nextIndex < getArray().length;
    }

    protected PSObject next() {
        PSObject nextObject = getArray()[nextIndex];
        nextIndex++;
        return nextObject;
    }

    protected PSObject[] getArray() {
        return ((PSArray) arrayObj.getValue()).getArray();
    }

    public PSObject getArrayObject() {
        return arrayObj;
    }
}
