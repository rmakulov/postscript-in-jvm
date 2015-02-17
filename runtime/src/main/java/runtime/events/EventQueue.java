package runtime.events;

import operators.customs.EventOp;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Runtime;

/**
 * Created by User on 16/2/2015.
 */
public class EventQueue {
    private EventQueueItem first;
    private EventQueueItem last;
    private boolean isAwake;

    public EventQueue() {

    }

    public boolean add(Event event) {
        if (first == null) {
            first = new EventQueueItem(event);
            last = first;
        } else {

            EventQueueItem eventQueueItem = new EventQueueItem(event);
            last.next = eventQueueItem;
            last = eventQueueItem;

        }
        if (!isAwake) {
            process();
        }
        return true;
    }

/*     public Event remove() {
        return null;
    }*/

    public Event poll() {
        if (first == null) {
            return null;
        }
        EventQueueItem ans = first;
        first = first.next;
        return ans.event;
    }

    public boolean isEmpty() {
        return first == null;
    }

/*    public Event element() {
        return null;
    }

    public Event peek() {
        return null;
    }*/

    public boolean isAwake() {
        return isAwake;
    }

    public void process() {
        isAwake = true;
        while (!isEmpty()) {
            Event event = poll();
            PSObject x = new PSObject(new PSInteger(event.getX()));
            PSObject y = new PSObject(new PSInteger(event.getY()));
            String s = event.getType().toString();
            PSObject type = new PSObject(new PSName(s), Attribute.TreatAs.LITERAL);

            runtime.Runtime runtime = Runtime.getInstance();
            runtime.pushToOperandStack(x);
            runtime.pushToOperandStack(y);
            runtime.pushToOperandStack(type);
            EventOp.instance.interpret();
        }
        isAwake = false;
    }

}
