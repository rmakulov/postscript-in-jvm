package psObjects.values;


import psObjects.Type;
import psObjects.compareableInterfaces.ValueComparable;
import psObjects.values.simple.PSNull;

public abstract class Value implements ValueComparable<Value> {
/*
    public int compareTo(Value object) {
        return this.hashCode()-object.hashCode();
    }*/

    public boolean isNull() {
        return this instanceof PSNull;
    }


    public abstract Value getValue();

    public abstract Type determineType();

    public Integer compareTo(Value o) {
        return o.compareGrade() == -1 || compareGrade() == -1 ? null : compareGrade() - o.compareGrade();
    }

    //need to override in comparable sons
    public int compareGrade() {
        return -1;
    }

    public abstract boolean equals(Object o);
}
