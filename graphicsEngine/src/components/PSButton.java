package components;

/**
 * Created by User on 28/1/2015.
 */
public class PSButton extends PSComponent {

    public PSButton(int x, int y, int w, int h, String buttonLabel) {
        super("button");
        generatedString = String.format("/%s %d %d %d %d (%s) scene events %s",
                name, x, y, w, h, buttonLabel,componentType);
    }

}
