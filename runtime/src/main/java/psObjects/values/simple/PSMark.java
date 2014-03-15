package psObjects.values.simple;

import psObjects.Type;


public class PSMark extends SimpleValue {
    private char markChar;
    public static PSMark OPEN_SQUARE_BRACKET = new PSMark('[');
    public static PSMark CLOSE_SQUARE_BRACKET = new PSMark('[');
    public static PSMark OPEN_CURLY_BRACE = new PSMark('{');
    public static PSMark CLOSE_CURLY_BRACE = new PSMark('}');

    private PSMark(char markChar) {
        this.markChar = markChar;
    }

    public char getMarkChar() {
        return markChar;
    }

    @Override
    public Type determineType() {
        return Type.MARK;
    }

    @Override
    public String toString() {
        return "PSMark{" +
                "markChar=" + markChar +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSMark)) return false;

        PSMark psMark = (PSMark) o;

        if (markChar != psMark.markChar) return false;

        return true;
    }

}
