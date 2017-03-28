package components;

/**
 * Created by user on 5/27/15.
 */
public class PSCheckBox extends PSComponent {
    public PSCheckBox(int x, int y, int h, String text) {
        super("checkBox");
        setLabel(text);
        generatedString = String.format("/%s %d %d  %d (%s) scene events %s",
                name, x, y, h, text, componentType);
    }
}