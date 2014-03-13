package psObjects;


public abstract class PSObject /*implements Comparable<PSObject>*/{
/*
    public int compareTo(PSObject object) {
        return this.hashCode()-object.hashCode();
    }*/

    public boolean isNull(){
        return this instanceof PSNull;
    }


}
