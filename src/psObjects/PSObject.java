package psObjects;


public abstract class PSObject implements Cloneable, Comparable<PSObject>{
    public abstract PSObject clone();

    public int compareTo(PSObject key) {
        return this.hashCode()-key.hashCode();
    }

    public boolean isNull(){
        return this instanceof PSNull;
    }
}
