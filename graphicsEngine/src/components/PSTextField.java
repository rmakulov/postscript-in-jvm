package components;

import listeners.PSEvent;
import listeners.PSListener;
import listeners.PSUpdateValueEvent;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.PSName;
import runtime.*;
import runtime.Runtime;
import runtime.events.Event;
import runtime.events.EventType;
import runtime.events.UserEvent;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 5/27/15.
 */
public class PSTextField extends PSComponent {
    private String text = null;
    private static final Object monitor = new Object();
    private static boolean ready = false;

    public PSTextField(int x, int y, int w, int h) {
        super("textField");
        generatedString = String.format("/%s %d %d %d %d (TextField) scene events %s",
                name, x, y, w, h, componentType);
        addListener(EventType.UPDATE_VALUE, new PSListener() {
            @Override
            public void actionPerformed(PSEvent event) {
                PSUpdateValueEvent updateValueEvent = ((PSUpdateValueEvent) event);
                PSDictionary dictionary = updateValueEvent.getDictionaryValue();
                text = ((PSString) dictionary.get(new PSObject(new PSName("text"))).getValue()).getString();
                synchronized (monitor) {
                    ready = true;
                    monitor.notifyAll();
                }
            }
        });
    }

    public String getText() {
        String str = String.format("%s /UPDATE_VALUE sendEventToJava", name);
//        String str = String.format("%s /UPDATE_VALUE findAncestorWithEventType", name);
//        UserEvent userEvent = new UserEvent(str);
//        runtime.Runtime.getInstance().getWindowManager().addEvent(userEvent);
        //todo rid of explicit pushing to callStack. It should not disturb event queue executing.
        String command = str;
        runtime.Runtime runtime = Runtime.getInstance();
        Context mainContext = runtime.getMainContext();
        StringProcedure procedure = null;
        try {
            procedure = new StringProcedure(mainContext, command);
            if (!runtime.isCompiling) {
                mainContext.pushToCallStack(procedure);
                mainContext.executeCallStack();
            } else {
                procedure.executeWithoutCallStack();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        synchronized (monitor) {
            while (!ready) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        String t = text;
        text = null;
        ready = false;
        return t;
    }

    public void setText(String text) {
        String str = String.format("%s (%s) setTextToTextField repaint", name, text);
        Event userEvent = new UserEvent(str);
        Runtime runtime = Runtime.getInstance();
        runtime.getWindowManager().addEvent(userEvent);
        runtime.getWindowManager().repaint();
    }
}
