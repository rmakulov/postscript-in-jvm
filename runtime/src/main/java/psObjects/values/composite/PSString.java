package psObjects.values.composite;

import psObjects.Type;
import psObjects.compareableInterfaces.PSComparable;
import psObjects.values.simple.numbers.PSInteger;

import java.util.Random;


public class PSString extends CompositeValue implements PSComparable<PSString> {
    //    private String s = null;
    private StringElement[] characters;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public PSString() {
        characters = new StringElement[0];
    }

    public PSString(int size) {
        characters = new StringElement[size];
        for (int i = 0; i < size; i++) {
            characters[i] = new StringElement((byte) 0);
        }
    }

    public PSString(StringElement[] stringElements) {
        characters = stringElements;
    }

    public PSString getInterval(int start, int length) {
        StringElement[] newArray = new StringElement[length];
        System.arraycopy(characters, start, newArray, 0, length);
        return new PSString(newArray);
    }

    public PSInteger get(int index) {
        return new PSInteger(characters[index].getCharacter());
    }

    // Generate random string and add it into local VM
    public static PSString initString() {
        byte[] temp = new byte[10];
        Random generator = new java.util.Random();
        return new PSString(generateString(generator, CHARACTERS, 10));
    }

    private byte[] getBytes() {
        byte[] arr = new byte[characters.length];
        for (int i = 0; i < characters.length; i++) {
            arr[i] = characters[i].getCharacter();
        }
        return arr;
    }

    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public PSString(String s) {
        byte[] bytes = s.getBytes();
        characters = new StringElement[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            characters[i] = new StringElement(bytes[i]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSString)) return false;

        PSString psString = (PSString) o;

        if (!getString().equals(psString.getString())) return false;

        return true;
    }


    @Override
    public String toString() {
        return "PSString{" +
                "s='" + getString() + '\'' +
                "}";
    }

    public String getString() {
        return new String(getBytes());
    }


    public int psCompareTo(PSString str) {
        return getString().compareTo(str.getString());
    }

    @Override
    public Type determineType() {
        return Type.STRING;
    }

    public PSString copy() {
        StringElement[] newArray = new StringElement[characters.length];
        System.arraycopy(characters, 0, newArray, 0, characters.length);
        return new PSString(newArray);
    }

    public String showString() {
        String res = "";
        for (StringElement c : characters) {
            res += charToCode(c.getCharacter());
        }
        return res;
    }

    private static String charToCode(byte b) {
        if (b < 128) {
            return (char) b + "";
        }
        return String.format("\\%o", b);
    }

    public int length() {
        return characters.length;
    }

    public PSString setSubstring(int start, String substr) {
        byte[] substrBytes = substr.getBytes();
        StringElement[] newArray = new StringElement[characters.length];
        System.arraycopy(characters, 0, newArray, 0, characters.length);

        for (int i = 0; i < substrBytes.length; i++) {
            newArray[i + start] = new StringElement(substrBytes[i]);
        }
        return new PSString(newArray);
    }

    private StringElement[] getCharacters() {
        return characters;
    }

    public PSString putInterval(int start, PSString srcString) {
        StringElement[] newArray = new StringElement[characters.length];
        System.arraycopy(characters, 0, newArray, 0, characters.length);
        StringElement[] elements = srcString.getCharacters();
        for (int i = 0; i < srcString.length(); i++) {
            if (newArray[i] == null) {
                newArray[i + start] = new StringElement(elements[i].getCharacter());
            } else {
                newArray[i + start].setCharacter(elements[i].getCharacter());
            }
        }
        return new PSString(newArray);
    }

    public PSString put(int index, byte character) {
        StringElement[] newArray = new StringElement[characters.length];
        System.arraycopy(characters, 0, newArray, 0, characters.length);
        if (newArray[index] == null) {
            newArray[index] = new StringElement(character);
        } else {
            newArray[index].setCharacter(character);
        }
        return new PSString(newArray);
    }
}
