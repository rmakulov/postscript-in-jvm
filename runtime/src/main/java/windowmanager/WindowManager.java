package windowmanager;

import operators.painting.PSPrimitive;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSName;
import runtime.*;
import runtime.Runtime;
import runtime.events.Event;
import runtime.events.EventQueue;
import runtime.events.PrimitiveQueue;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 5/5/15.
 */
public class WindowManager {

    public long lasttime;
    private EventQueue eventQueue = new EventQueue();
    private PrimitiveQueue primitiveQueue = new PrimitiveQueue();


    public WindowManager(){
        lasttime = System.currentTimeMillis();
    }

    public void repaint(){
        long diff = System.currentTimeMillis() - lasttime;
        if( diff < 5 ){
            return;
            //todo activate repaint after small time in this case
        }
        lasttime = System.currentTimeMillis();
        Runtime runtime = Runtime.getInstance();
        Context mainContext = runtime.getMainContext();
        if (!runtime.isCompiling) {
            try {
                mainContext.pushToCallStack(new StringProcedure(mainContext, new PSObject(new PSString("repaintAll"))));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mainContext.executeCallStack();
        } else {
            new PSObject(new PSName("repaintAll")).interpret(runtime.getMainContext(), 0);
        }
    }

    public void addEvent(Event event) {
        eventQueue.add(event);
    }

    public void addPrimitive(PSPrimitive psPrimitive) {
        primitiveQueue.add(psPrimitive);
    }
}
