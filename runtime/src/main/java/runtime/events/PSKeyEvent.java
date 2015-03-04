package runtime.events;

/**
 * Created by user on 03.03.15.
 */
public class PSKeyEvent extends Event {
    //private int code;
    private char c;

    public PSKeyEvent(EventType type) {
        super(type);
    }

    public PSKeyEvent(char c, EventType type) {
        super(type);
        this.c = c;
    }

    public char getChar() {
        return c;
    }
}
