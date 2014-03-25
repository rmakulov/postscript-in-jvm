package psObjects.values.composite;

import psObjects.Type;
import psObjects.compareableInterfaces.PSComparable;

import java.util.Random;


public class PSString extends CompositeValue implements PSComparable<PSString> {
    private String s = null;
    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public PSString() {

    }

    public PSString getInterval(int start, int length) {
        return new PSString(s.substring(start, start + length - 1));
    }

    public PSString get(int index) {
        return new PSString(s.charAt(index) + "");
    }

    // Generate random string and add it into local VM
    public static PSString initString() {
        byte[] temp = new byte[10];
        Random generator = new java.util.Random();
        return new PSString(generateString(generator, characters, 10));
    }

    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public PSString(String s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSString)) return false;

        PSString psString = (PSString) o;

        if (!s.equals(psString.s)) return false;

        return true;
    }


    @Override
    public String toString() {
        return "PSString{" +
                "s='" + s + '\'' +
                "}";
    }

    public String getString() {
        return s;
    }


    public int psCompareTo(PSString str) {
        return s.compareTo(str.getString());
    }

    @Override
    public Type determineType() {
        return Type.STRING;
    }

    public int length() {
        return s.length();
    }

    public PSString setSubstring(int start, String substr) {
        return new PSString(s.substring(0, start - 1) + substr + s.substring(start + substr.length()));
    }
}
