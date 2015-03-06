package runtime.events;

import operators.customs.KeyEventOp;
import operators.customs.MouseEventOp;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
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
        if (runtime.Runtime.getInstance().search(new PSObject(new PSName("gelements"))) == null) {
            return false;
        }
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
            runtime.Runtime runtime = Runtime.getInstance();
            EventType evType = event.getType();
            String s = evType.toString();
            PSObject type = new PSObject(new PSName(s), Attribute.TreatAs.LITERAL);
            switch (evType) {
                case KEYBOARD_CHAR:
                    PSKeyEvent charKeyEvent = (PSKeyEvent) event;
                    String aChar = charKeyEvent.getChar() + "";
                    PSObject symbol = new PSObject(new PSString(aChar), Attribute.TreatAs.LITERAL);
                    runtime.pushToOperandStack(symbol);
                    runtime.pushToOperandStack(type);
                    KeyEventOp.instance.interpret();
                    break;
                case KEYBOARD_CONTROL:
                    PSKeyEvent controlKeyEvent = (PSKeyEvent) event;
                    runtime.pushToOperandStack(new PSObject(new PSInteger(controlKeyEvent.getCode())));
                    runtime.pushToOperandStack(type);
                    KeyEventOp.instance.interpret();
                    break;
                default:
                    PSMouseEvent mouseEvent = (PSMouseEvent) event;
                    PSObject x = new PSObject(new PSInteger(mouseEvent.getX()));
                    PSObject y = new PSObject(new PSInteger(mouseEvent.getY()));

                    runtime.pushToOperandStack(x);
                    runtime.pushToOperandStack(y);
                    runtime.pushToOperandStack(type);
                    MouseEventOp.instance.interpret();
                    break;
            }

            if (event.getType() == EventType.KEYBOARD_CHAR) {

            } else {

            }

        }
        isAwake = false;
    }

}
