package components;

/**
 * Created by user on 5/27/15.
 */
public class PSTextField extends PSComponent {
    public PSTextField(int x, int y, int w, int h) {
        super("textField");
        generatedString = String.format("/%s %d %d %d %d (TextField) scene events %s",
                name, x, y, w, h, componentType);
    }
}
