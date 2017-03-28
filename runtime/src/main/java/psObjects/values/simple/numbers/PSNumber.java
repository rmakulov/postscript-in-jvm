package psObjects.values.simple.numbers;


import psObjects.compareableInterfaces.PSComparable;
import psObjects.values.Value;
import psObjects.values.simple.SimpleValue;

public abstract class PSNumber extends SimpleValue implements PSComparable<PSNumber> {
    @Override
    public Integer compareTo(Value o) {
        return o instanceof PSNumber ?
            psCompareTo((PSNumber) o):
            super.compareTo(o);
    }

    public int compareGrade() {
        return 2;
    }

    public int psCompareTo(PSNumber n) {
        double thisValue = getRealValue();
        double nValue = n.getRealValue();
        double res = thisValue - nValue;
        if (res > 0) return 1;
        if (res < 0) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSNumber)) return false;

        PSNumber psNumber = (PSNumber) o;

        if (getRealValue() != psNumber.getRealValue()) return false;

        return true;
    }

    public abstract double getRealValue();

}
