package operators;

import listeners.PSEvent;
import listeners.Slots;
import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;
import runtime.events.EventType;

/**
 * Created by user on 5/27/15.
 */
public class SendEventToJavaOp extends Operator {
    public static final SendEventToJavaOp instance = new SendEventToJavaOp();

    protected SendEventToJavaOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        if (context.getOperandStackSize() < 2) {
            fail();
        }

        PSObject oType = context.popFromOperandStack();
        PSObject oDict = context.popFromOperandStack();
        if(!oType.isName() || !oDict.isDict()){
            context.pushToOperandStack(oDict);
            context.pushToOperandStack(oType);
            fail();
        }
        EventType signalType = EventType.valueOf(((PSName) oType.getValue()).getStrValue());

        PSDictionary dict = (PSDictionary) oDict.getValue();
        PSObject key = new PSObject(new PSName("name"));
        String gObjectName = ((PSName) dict.get(key).getValue()).getStrValue();

        Slots.getInstance().invokeSlot(gObjectName, signalType,new PSEvent(dict,signalType));
        //  <</CLICK [{/CLICK sendEventToJava}[]]>>
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("sendEventToJava");
    }
}
