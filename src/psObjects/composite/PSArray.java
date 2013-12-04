package psObjects.composite;

import psObjects.PSObject;

import java.util.Arrays;

public class PSArray extends CompositeObject implements Cloneable {
    private PSObject[] array;

    public PSArray() {
    }

    public PSArray(int length) {
        array = new PSObject[length];
    }

    public static PSArray initArray(int length) {
        PSArray psArray = new PSArray(length);
        for (int i = 0; i < length; i++) {
            psArray.setValue(i, PSString.initString());
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

    public void setValue(int index, PSObject psObject) {
        array[index] = psObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSArray)) return false;

        PSArray psArray = (PSArray) o;

        if (!Arrays.equals(array, psArray.array)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public PSArray clone() {
        int length = array.length;
        PSObject[] arr = new PSObject[length];
        for (int i = 0; i < length; i++) {
            arr[i] = array[i].clone();
        }
        return new PSArray(arr);
    }
}
