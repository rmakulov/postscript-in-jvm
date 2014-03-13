package runtime;

import java.io.IOException;

/**
 * Created by Дмитрий on 11.03.14.
 */
public class Example {
    public static void main(String[] args) {
        try {
            CLexGen lg = new CLexGen("input.ps");
            lg.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
