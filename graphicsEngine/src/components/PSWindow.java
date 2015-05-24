package components;

public class PSWindow extends PSComponent {

    public PSWindow(int x, int y, int w, int h, String name) {
        super(name);
        generatedString = String.format("/%s %d %d %d %d scene << >> window def", name, x, y, w, h);
    }

    public void setLabel(String label){
        //todo
    }
}
