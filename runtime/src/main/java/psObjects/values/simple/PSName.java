package psObjects.values.simple;

import psObjects.Type;
import psObjects.values.Value;

public class PSName extends SimpleValue {


    private String strValue;

    public PSName(String strValue) {
        this.strValue = strValue;
    }


    public String getStrValue() {
        return strValue;
    }

    @Override
    public String toString() {
        return "PSName{" +
                "strValue='" + strValue + '\'' +
                "} ";
    }

    @Override
    public Type determineType() {
        return Type.NAME;
    }

    public Integer compareTo(Value o) {
        return o instanceof PSName ?
                strValue.compareTo(((PSName) o).getStrValue()) :
                super.compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSName)) return false;

        PSName PSName = (PSName) o;

        if (!strValue.equals(PSName.strValue)) return false;

        return true;
    }

    public int compareGrade() {
        return 0;
    }

    public int length() {
        return strValue.length();
    }
}
