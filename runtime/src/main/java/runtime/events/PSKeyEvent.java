package runtime.events;

/**
 * Created by user on 03.03.15.
 */
public class PSKeyEvent extends Event {
    //private int code;
    private char c;
    private int code;

    public PSKeyEvent(EventType type) {
        super(type);
    }

    public PSKeyEvent(char c, EventType type) {
        super(type);
        this.c = c;
    }

    public PSKeyEvent(char c, int code, EventType type) {
        super(type);
        this.c = c;
        this.code = code;
    }

    public char getChar() {
        return c;
    }

    public int getCode() {
        return code;
    }
}
