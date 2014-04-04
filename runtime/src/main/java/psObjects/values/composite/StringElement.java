package psObjects.values.composite;

/**
 * Created by user on 02.04.14.
 */
public class StringElement {
    private byte character;

    public StringElement(byte character) {
        this.character = character;
    }

    public byte getCharacter() {
        return character;
    }

    public void setCharacter(byte character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return (char) character + "";
    }
}
