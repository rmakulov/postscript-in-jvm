package runtime.events;

import operators.customs.KeyEventOp;
import operators.customs.MouseEventOp;
import psObjects.Attribute;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSName;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;
import runtime.Runtime;

/**
 * Created by User on 16/2/2015.
 */
public class EventQueue {
    private EventQueueItem first;
    private EventQueueItem last;
    private boolean isAwake;
    private Context mainContext;

    public EventQueue() {

    }

    public synchronized boolean add(Event event) {
        Context context = getMainContext();
        if (context.search(new PSObject(new PSName("gelements"))) == null) {
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

    public synchronized Event poll() {
        if (first == null) {
            return null;
        } else if (first == last) {
            last = null;
            Event event = first.event;
            first = null;
            return event;
        } else {
            Event event = first.event;
            first = first.next;
            return event;
        }
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

    private void process() {
        Context context = getMainContext();
        isAwake = true;
        while (!isEmpty()) {
            Event event = poll();
            EventType evType = event.getType();
            String s = evType.toString();
            PSObject type = new PSObject(new PSName(s), Attribute.TreatAs.LITERAL);
            switch (evType) {
                case KEYBOARD_CHAR:
                    PSKeyEvent charKeyEvent = (PSKeyEvent) event;
                    String aChar = charKeyEvent.getChar() + "";
                    PSObject symbol = new PSObject(new PSString(aChar), Attribute.TreatAs.LITERAL);
                    context.pushToOperandStack(symbol);
                    context.pushToOperandStack(type);
                    KeyEventOp.instance.interpret(context);
                    break;
                case KEYBOARD_CONTROL:
                    PSKeyEvent controlKeyEvent = (PSKeyEvent) event;
                    context.pushToOperandStack(new PSObject(new PSInteger(controlKeyEvent.getCode())));
                    context.pushToOperandStack(type);
                    KeyEventOp.instance.interpret(context);
                    break;
                default:
                    PSMouseEvent mouseEvent = (PSMouseEvent) event;
                    PSObject x = new PSObject(new PSInteger(mouseEvent.getX()));
                    PSObject y = new PSObject(new PSInteger(mouseEvent.getY()));

                    context.pushToOperandStack(x);
                    context.pushToOperandStack(y);
                    context.pushToOperandStack(type);

//                    try {
//                        context.pushToCallStack(new StringProcedure(context, new PSObject(new PSString("mouseEvent"))));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    if (!Runtime.getInstance().isCompiling) {
//                        context.executeCallStack();
//                    }
                    MouseEventOp.instance.interpret(context);
                    break;
            }

        }
        isAwake = false;
    }

    private Context getMainContext() {
        if (mainContext == null) {
            mainContext = Runtime.getInstance().getMainContext();
        }
        return mainContext;
    }
}
