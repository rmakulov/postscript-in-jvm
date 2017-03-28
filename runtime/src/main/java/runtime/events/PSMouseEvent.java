package runtime.events;

import runtime.graphics.frame.PSImage;

/**
 * Created by user on 03.03.15.
 */
public class PSMouseEvent extends Event {
    private int x;
    private int y;


    public PSMouseEvent(int x, int y, EventType type) {
        super(type);
        this.y = PSImage.height - y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
