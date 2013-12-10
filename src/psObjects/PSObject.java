package psObjects;


public abstract class PSObject implements Comparable<PSObject>{

    public int compareTo(PSObject key) {
        return this.hashCode()-key.hashCode();
    }

    public boolean isNull(){
        return this instanceof PSNull;
    }
}
