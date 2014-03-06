package psObjects.simple;


public abstract class PSNumber extends SimpleObject implements Comparable<PSNumber> {
    @Override
    public int compareTo(PSNumber n) {
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
