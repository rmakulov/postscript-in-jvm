package components;

/**
 * Created by user on 5/27/15.
 */
public class PSComboBox extends PSComponent {
    public PSComboBox(int x, int y, int w, int h, String[] labels, int curLabelNumber) {
        super("comboBox");
        StringBuilder builder = new StringBuilder("[");
        for (String label : labels) {
            builder.append(" (").append(label).append(")");
        }
        builder.append("]");
        generatedString = String.format("/%s %d %d %d %d %s %d scene events %s",
                name, x, y, w, h, builder.toString(), curLabelNumber,componentType);
    }
}
