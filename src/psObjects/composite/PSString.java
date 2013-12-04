package psObjects.composite;

import psObjects.PSObject;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 03.12.13
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class PSString extends CompositeObject {
    private String s = null;
    private static final String characters= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Generate random string and add it into local VM
    public PSString() {

        }

    public static PSString initString() {
           byte[] temp = new byte[10];
           Random generator = new java.util.Random();
           /*generator.nextBytes(temp);
           String s = new String(temp, 0);
           return new PSString(s);*/
        return new PSString(generateString(generator,characters,10));
       }

    public static String generateString(Random rng, String characters, int length)    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
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
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public String toString() {
        return "PSString{" +
                "s='" + s + '\'' +
                "} " + super.toString();
    }

    @Override
    public PSObject clone() {
        return new PSString(new String(s));
    }
}
