package psObjects.values.simple;

import psObjects.Type;


public class PSMark extends SimpleValue {
    private String markRecord;
    public static PSMark OPEN_SQUARE_BRACKET = new PSMark("[");
    public static PSMark CLOSE_SQUARE_BRACKET = new PSMark("]");
    public static PSMark OPEN_CURLY_BRACE = new PSMark("{");
    public static PSMark CLOSE_CURLY_BRACE = new PSMark("}");
    public static PSMark OPEN_CHEVRON_BRACKET = new PSMark("<<");
    public static PSMark CLOSE_CHEVRON_BRACKET = new PSMark(">>");

    private PSMark(String markRecord) {
        this.markRecord = markRecord;
    }

    public String getMarkRecord() {
        return markRecord;
    }

    @Override
    public Type determineType() {
        return Type.MARK;
    }

    @Override
    public String toString() {
        return "PSMark{" +
                "markRecord=\"" + markRecord +
                "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSMark)) return false;

        PSMark psMark = (PSMark) o;

        if (markRecord != psMark.markRecord) return false;

        return true;
    }

}
