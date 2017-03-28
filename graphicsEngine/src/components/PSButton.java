package components;

import runtime.*;
import runtime.Runtime;
import runtime.events.Event;
import runtime.events.UserEvent;

import java.awt.*;

/**
 * Created by User on 28/1/2015.
 */
public class PSButton extends PSComponent {
    Color color = Color.BLACK;

    public PSButton(int x, int y, int w, int h, String buttonLabel) {
        super("button");
        setLabel(buttonLabel);
        generatedString = String.format("/%s %d %d %d %d (%s) scene events %s\n",
                name, x, y, w, h, buttonLabel, componentType);
    }

    @Override
    protected void updateGeneratedString() {
        super.updateGeneratedString();
        generatedString = generatedString + String.format("%s /color [%f %f %f] put",
                name, color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);

    }

    public static void setColor(Color color, PSButton... buttons){
        for(PSButton button : buttons){
            button.setColor(color);
        }
    }
    public void setColor(Color color) {
        this.color = color;
//        String str = String.format("%s /color [%f %f %f] put", name, color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);
//        Event userEvent = new UserEvent(str);
//        runtime.Runtime runtime = Runtime.getInstance();
//        runtime.getWindowManager().addEvent(userEvent);
//        runtime.getWindowManager().repaint();
    }
}
