package psObjects.values.simple;

import psObjects.Type;
import psObjects.values.Value;

public class PSBoolean extends SimpleValue {
    private boolean flag;
    public static PSBoolean TRUE = new PSBoolean(true);
    public static PSBoolean FALSE = new PSBoolean(false);

    private PSBoolean(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PSBoolean{" +
                "flag=" + flag +
                "} " + super.toString();
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public Type determineType() {
        return Type.BOOLEAN;

    }

    /*true > false
      true = true
      false = false
      false < true
      Bool < others
     */

    public Integer compareTo(Value o) {
        return o instanceof PSBoolean ?
                flag == ((PSBoolean) o).getFlag() ?
                        0 :
                        (flag ? 1 : -1) :
                super.compareTo(o);
    }

    public int compareGrade() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSBoolean)) return false;

        PSBoolean psBoolean = (PSBoolean) o;

        if (flag != psBoolean.flag) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (flag ? 1 : 0);
    }

    public static PSBoolean get(boolean b) {
        if (b) return TRUE;
        return FALSE;
    }

    public PSBoolean not() {
        return flag ? FALSE : TRUE;
    }
}