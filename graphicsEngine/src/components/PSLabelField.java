package components;

/**
 * Created by user on 5/27/15.
 */
public class PSLabelField extends PSComponent {
    public PSLabelField(int x, int y, int w, int h, String label) {
        super("labelField");
        generatedString = String.format("/%s %d %d %d %d (%s) scene events %s",
                name, x, y, w, h,label, componentType);
    }
}


