package psObjects.values.simple.numbers;

import psObjects.Type;

public class PSReal extends PSNumber {
    private double value;

    public PSReal(double value) {
        this.value = value;
    }

    @Override
    public double getRealValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PSReal{" +
                "value=" + value +
                "}";
    }

    @Override
    public Type determineType() {
        return Type.REAL;
    }
}
