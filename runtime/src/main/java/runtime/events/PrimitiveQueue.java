package runtime.events;

import operators.painting.PSPrimitive;

/**
 * Created by rustam on 3/29/15.
 */
public class PrimitiveQueue {

    private PrimitiveQueueItem first;
    private PrimitiveQueueItem last;
    private boolean isAwake;

    public PrimitiveQueue() {

    }

    public boolean add(PSPrimitive primitive) {
        if (first == null) {
            first = new PrimitiveQueueItem(primitive);
            last = first;
        } else {

            PrimitiveQueueItem primitiveQueueItem = new PrimitiveQueueItem(primitive);
            last.next = primitiveQueueItem;
            last = primitiveQueueItem;

        }
        if (!isAwake) {
            process();
        }
        return true;
    }


    public PSPrimitive poll() {
        if (first == null) {
            return null;
        }
        PrimitiveQueueItem ans = first;
        first = first.next;
        return ans.primitive;
    }

    public boolean isEmpty() {
        return first == null;
    }


    public boolean isAwake() {
        return isAwake;
    }

    public void process() {
        isAwake = true;
        while (!isEmpty()) {
            PSPrimitive primitive = poll();
            primitive.paint();

        }
        isAwake = false;
    }

}
