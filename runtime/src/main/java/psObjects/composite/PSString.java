package psObjects.composite;

import java.util.Random;


public class PSString extends CompositeObject implements Comparable<PSString> {
    private String s = null;
    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public PSString() {

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
                "} " + super.toString();
    }

    public String getString() {
        return s;
    }

    @Override
    public int compareTo(PSString str) {
        return s.compareTo(str.getString());
    }
}
