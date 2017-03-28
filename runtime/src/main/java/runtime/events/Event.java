package runtime.events;

/**
 * Created by User on 16/2/2015.
 */
public abstract class Event {
    private EventType type;

    public Event(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
