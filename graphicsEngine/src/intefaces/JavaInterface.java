package intefaces;

import components.*;
import listeners.PSEvent;
import listeners.PSListener;
import runtime.events.EventType;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class JavaInterface {
    public static void main(String[] args) {
        GraphicInterface graphicInterface = new GraphicInterface();

        PSComponent window = new PSWindow(100, 100, 300, 300, "window");
        PSComponent button = new PSButton(110, 110, 50, 20, "close");
        PSComponent toggleButton = new PSToggleButton(110, 220, 116, 50, "on", "off");
        graphicInterface.add(window);
        window.add(button);
        window.add(toggleButton);
        button.addListener(EventType.CLICK,new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                System.exit(0);
            }
        });
        graphicInterface.setVisible(true);
    }
}
