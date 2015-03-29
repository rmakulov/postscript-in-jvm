package runtime.events;

import operators.painting.PSPrimitive;

/**
 * Created by rustam on 3/29/15.
 */
public class PrimitiveQueueItem {
    public PSPrimitive primitive;
    public PrimitiveQueueItem next;


    public PrimitiveQueueItem(PSPrimitive primitive) {
        this.primitive = primitive;
    }
}
