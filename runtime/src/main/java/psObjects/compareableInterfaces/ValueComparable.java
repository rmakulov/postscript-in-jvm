package psObjects.compareableInterfaces;


public interface ValueComparable<T> {
    Integer compareTo(T o);

    //if(grade ==-1) compareTo return null;
    /*
    Boolean : 1
    Number  : 2
    Name    : 3
    String  : 4
    LocalRef: 5
    GlobalRef: 6
    other : -1


     */
    int compareGrade();
}
