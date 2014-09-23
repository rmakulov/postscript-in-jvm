package psObjects.values.composite;

import procedures.ArrayProcedure;
import psObjects.PSObject;
import psObjects.Type;

import java.util.ArrayList;
import java.util.Arrays;

public class PSArray extends CompositeValue implements Cloneable {
    private ArrayElement[] array;

    public PSArray() {
    }

    public PSArray(int length) {
        array = new ArrayElement[length];
    }

    public PSArray(ArrayElement[] arrayElements) {
        array = arrayElements;
    }

    public PSObject get(int index) {
        return array[index].getElementObject();
    }

    public static PSArray initArray(int length) {
        PSArray psArray = new PSArray(length);
        for (int i = 0; i < length; i++) {
            psArray.setDirectValue(i, new PSObject(PSString.initString()));
        }
        return psArray;
    }

    public PSArray(PSObject[] psObjects) {
        array = new ArrayElement[psObjects.length];
        for (int i = 0; i < psObjects.length; i++) {
            array[i] = new ArrayElement(psObjects[i]);
        }
    }

    public PSArray(ArrayList<PSObject> psObjects) {
        array = new ArrayElement[psObjects.size()];
        for (int i = 0; i < psObjects.size(); i++) {
            array[i] = new ArrayElement(psObjects.get(i));
        }
    }

    public PSArray(PSObject obj) {
        array = new ArrayElement[1];
        array[0] = new ArrayElement(obj);
    }

    public PSObject[] getArray() {
        PSObject[] psObjects = new PSObject[array.length];
        for (int i = 0; i < psObjects.length; i++) {
            psObjects[i] = array[i].getElementObject();
        }
        return psObjects;
    }


    private ArrayElement[] getArrayElements() {
        return array;
    }

    public int length() {
        return array == null ? 0 : array.length;
    }

    public PSArray copy() {
        ArrayElement[] res = new ArrayElement[array.length];
        System.arraycopy(array, 0, res, 0, array.length);
        return new PSArray(res);
    }

    public PSArray clone() {
        ArrayElement[] res = new ArrayElement[array.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = new ArrayElement(array[i].getElementObject());
        }
        return new PSArray(res);
    }

    public PSArray getInterval(int start, int length) {
        ArrayElement[] res = new ArrayElement[length];
        for (int i = 0; i < length; i++) {
            res[i] = array[start + i];
        }
        return new PSArray(res);
    }

    public PSArray setValue(int index, PSObject value) {
        ArrayElement[] res = new ArrayElement[array.length];
        System.arraycopy(array, 0, res, 0, array.length);
        if (res[index] == null) {
            res[index] = new ArrayElement(value);
        } else {
            res[index].setElementObject(value);
        }
        return new PSArray(res);
    }

    private void setDirectValue(int index, PSObject value) {
        array[index].setElementObject(value);
    }

    public PSArray putInterval(int start, PSArray psArray) {
        ArrayElement[] newArray = new ArrayElement[array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        ArrayElement[] arrayFrom = psArray.getArrayElements();
        if (newArray.length - start < arrayFrom.length)
            return null;
        for (int i = 0; i < arrayFrom.length; i++) {
            if (newArray[i + start] == null) {
                newArray[i + start] = new ArrayElement(arrayFrom[i].getElementObject());
            } else {
                newArray[i + start].setElementObject(arrayFrom[i].getElementObject());
            }
        }
        return new PSArray(newArray);
    }

    @Override
    public boolean interpret(PSObject obj) {
        if (runtime.isCompiling) {
            return array[0].getElementObject().execute(0);
        } else {
            runtime.pushToCallStack(new ArrayProcedure(obj));
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSArray)) return false;
        return true;
    }

    @Override
    public String toStringView() {
        StringBuilder sb = new StringBuilder().append("[");
        for (ArrayElement arrayElement : array) {
            sb.append(arrayElement.getElementObject().toStringView()).append(" ");
        }
        return sb.toString().trim() + "]";
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public Type determineType() {
        return Type.ARRAY;
    }

    @Override
    public void compile(PSObject obj) {
        // we have only one element - it is bytecode, so just execute it
        array[0].getElementObject().compile();
    }

}
