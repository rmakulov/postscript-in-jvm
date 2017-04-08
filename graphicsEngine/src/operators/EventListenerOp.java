package operators;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.graphics.frame.PSFrame;

/**
 * Created by 1 on 09.03.2017.
 */
public class EventListenerOp extends Operator {
    public static final EventListenerOp instance = new EventListenerOp();

    protected EventListenerOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        PSFrame.getInstance().setEventListener(true);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("eventlistener");
    }

}
