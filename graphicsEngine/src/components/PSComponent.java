package components;

import listeners.PSListener;
import listeners.Slots;
import runtime.avl.Pair;
import runtime.events.EventType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 20/5/2015.
 */
public class PSComponent {
    protected String name;
    protected String generatedString;
    protected ArrayList<PSComponent> children = new ArrayList<PSComponent>();
    protected PSComponent owner;
    private static int id = 1;
    protected String componentType;
    private HashMap<EventType, ArrayList<PSListener>> listeners = new HashMap<EventType, ArrayList<PSListener>>();

    public PSComponent(String componentType) {
        name = componentType + getNewId();
        this.componentType = componentType;
    }

    public PSComponent() {
    }

    public void add(PSComponent component) {
        children.add(component);
        component.setOwner(this);
    }



    public String getName() {
        return name;
    }

    public String getGeneratedString() {
        updateGeneratedString();
        StringBuilder stringBuilder = new StringBuilder(generatedString);
        for (PSComponent component : children) {
            stringBuilder.append("\n").append(component.getGeneratedString());
        }
        return stringBuilder.toString();
    }

    protected void updateGeneratedString() {
        if (owner != null) {
            generatedString = generatedString.replaceAll("scene", owner.getName());
        }
        generateEventsString();
        //to override
    }

    protected void generateEventsString() {
        StringBuilder stringBuilder = new StringBuilder("<<");
        if (!listeners.isEmpty()) {
            for (EventType eventType : listeners.keySet()) {
                ArrayList<PSListener> listenersList = listeners.get(eventType);
                stringBuilder.append(" /").append(eventType.toString()).append(" ");
                for (PSListener listener : listenersList) {
                    stringBuilder.append("[ { /").append(eventType.toString()).append(" sendEventToJava}");
                    Slots.getInstance().addListener(name, eventType, listener);
                }
                stringBuilder.append("[]");
                for (PSListener listener : listenersList) {
                    stringBuilder.append("]");
                }
            }
        }
        stringBuilder.append(">>");
        String replacement = stringBuilder.toString();
        generatedString = generatedString.replaceAll("events", replacement);
    }

    public void setOwner(PSComponent owner) {
        this.owner = owner;
    }

    public static int getNewId() {
        return id++;
    }

    public void addListener(EventType eventType, PSListener listener) {
        if (listeners.containsKey(eventType)) {
            listeners.get(eventType).add(listener);
        } else {
            ArrayList<PSListener> arrayList = new ArrayList<PSListener>();
            arrayList.add(listener);
            listeners.put(eventType, arrayList);
        }
    }
}
