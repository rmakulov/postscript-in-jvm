package components;

public class PSWindow extends PSComponent {

    public PSWindow(int x, int y, int w, int h, String labelString) {
        super("window");
        generatedString = String.format("/%s %d %d %d %d (%s) scene events %s",
                name, x, y, w, h,labelString, componentType);
    }

}
