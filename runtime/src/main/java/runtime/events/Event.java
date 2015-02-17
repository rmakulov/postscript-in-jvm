package runtime.events;

import psObjects.values.simple.numbers.PSInteger;
import runtime.graphics.frame.PSImage;

/**
 * Created by User on 16/2/2015.
 */
public class Event {
    private int x;
    private int y;
    private EventType type;

    public Event(int x, int y, EventType type) {
        this.y = PSImage.height - y;
        this.x = x;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EventType getType() {
        return type;
    }
}
