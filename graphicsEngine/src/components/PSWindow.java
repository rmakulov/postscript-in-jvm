package components;

public class PSWindow extends PSComponent {

    public PSWindow(int x, int y, int w, int h, String labelString) {
        super("Window"+PSComponent.getNewId());
        generatedString = String.format("/%s %d %d %d %d (%s) scene << >> window", name, x, y, w, h,labelString);
    }

    public void setLabel(String label){
        //todo
    }
}
