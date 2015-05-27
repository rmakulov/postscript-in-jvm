package components;

/**
 * Created by user on 5/27/15.
 */
public class PSToggleButton extends PSComponent {
    public PSToggleButton(int x, int y, int w, int h, String onLabel,String offLabel) {
        super("toggleButton");
        generatedString = String.format("/%s %d %d %d %d (%s) (%s) scene events %s", name, x, y, w, h, onLabel, offLabel, componentType);
    }
}
