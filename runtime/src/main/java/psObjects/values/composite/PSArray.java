package psObjects.values.composite;

import psObjects.PSObject;
import psObjects.Type;

import java.util.Arrays;

public class PSArray extends CompositeValue implements Cloneable {
    private PSObject[] array;

    public PSArray() {
    }

    public PSArray(int length) {
        array = new PSObject[length];
    }

    public static PSArray initArray(int length) {
        PSArray psArray = new PSArray(length);
        for (int i = 0; i < length; i++) {
            psArray.setDirectValue(i, new PSObject(PSString.initString()));
        }
        return psArray;
    }

    public PSArray(PSObject[] array) {
        this.array = array;
    }

    public PSObject[] getArray() {
        return array;
    }

    public PSArray getInterval(int start, int length) {
        PSObject[] res = new PSObject[length];
        for (int i = 0; i < length; i++) {
            res[i] = array[start + i];
        }
        return new PSArray(res);
    }

    public PSArray setValue(int index, PSObject value) {
        //array[index] = value;
        PSObject[] newArray = new PSObject[array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[index] = value;
        return new PSArray(newArray);
    }

    private void setDirectValue(int index, PSObject value) {
        array[index] = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSArray)) return false;
        //PSArray psArray = (PSArray) o;
       // if (!Arrays.equals(array, psArray.array)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public Type determineType() {
        return Type.ARRAY;
    }
}
