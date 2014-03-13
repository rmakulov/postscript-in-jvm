package psObjects.simple;

import java.util.Random;

public class PSInteger extends PSNumber {
    private int value = 0;

    // Generate random int and add it into local VM
    public PSInteger() {

    }

    public PSInteger(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PSInteger{" +
                "value=" + value +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSInteger)) return false;

        PSInteger psInteger = (PSInteger) o;

        if (value != psInteger.value) return false;

        return true;
    }


    /*
    @Override
    public int hashCode() {
        return this.value;
    }
*/

    public static PSInteger initInteger() {
        Random rand = new Random();
        int      value = rand.nextInt();
        return new PSInteger(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public double getRealValue() {
        return (double) value;
    }

    public static PSNumber add(PSInteger i1, PSInteger i2){
        return new PSInteger(i1.value+i2.value);
    }

}
