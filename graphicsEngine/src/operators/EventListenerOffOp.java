package operators;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSFrame;

/**
 * Created by stack on 30.03.17.
 */
public class EventListenerOffOp extends Operator {
    public static final EventListenerOffOp instance = new EventListenerOffOp();

    protected EventListenerOffOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSFrame.getInstance().setEventListener(false);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("eventlisteneroff");
    }
}
