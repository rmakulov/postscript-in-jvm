package intefaces;

import components.GraphicInterface;
import components.PSButton;
import components.PSComponent;
import components.PSWindow;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class JavaInterface {
    public static void main(String[] args) {
        GraphicInterface graphicInterface = new GraphicInterface();
        PSComponent window = new PSWindow(100, 100, 300, 300, "window");
        PSComponent button = new PSButton(110, 110, 50, 20, "ok");
        graphicInterface.add(window);
        window.add(button);
        graphicInterface.finishConstruction();
        graphicInterface.run();
    }
}
