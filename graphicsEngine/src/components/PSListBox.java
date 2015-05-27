package components;

/**
 * Created by user on 5/27/15.
 */
public class PSListBox extends PSComponent {
    public PSListBox(int x, int y, int w, int h, String[] labels, int curLabelNumber) {
        super("listBox");
        StringBuilder builder = new StringBuilder("[");
        for (String label : labels) {
            builder.append(" (").append(label).append(")");
        }
        builder.append("]");
        generatedString = String.format("/%s %d %d %d %d %s %d scene events %s",
                name, x, y, w, h, builder.toString(), curLabelNumber,componentType);
    }
}
