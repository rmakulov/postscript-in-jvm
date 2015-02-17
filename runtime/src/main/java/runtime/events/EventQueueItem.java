package runtime.events;

/**
 * Created by User on 16/2/2015.
 */
public class EventQueueItem {
    public Event event;
    public EventQueueItem next;


    public EventQueueItem(Event event) {
        this.event = event;
    }
}
