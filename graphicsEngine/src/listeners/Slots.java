package listeners;

import runtime.avl.Pair;
import runtime.events.EventType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class Slots {
    private HashMap<Pair<String,EventType>,ArrayList<PSListener>>  links=new HashMap<Pair<String, EventType>, ArrayList<PSListener>>();
    private static Slots ourInstance = new Slots();

    public static Slots getInstance() {
        return ourInstance;
    }

    private Slots() {
    }

    public void addListener(String gObjectName,EventType signalType, PSListener listener){
        Pair<String,EventType> elementSignal = new Pair<String, EventType>(gObjectName,signalType);
        if(links.containsKey(elementSignal)){
            links.get(elementSignal).add(listener);
        }   else{
            ArrayList<PSListener> arrayList = new ArrayList<PSListener>();
            arrayList.add(listener);
            links.put(elementSignal, arrayList);
        }
    }

    public void invokeSlot(String gObjectName,EventType signalType,PSEvent event){
        for (PSListener psListener : links.get(new Pair<String, EventType>(gObjectName, signalType))) {
            psListener.actionPerformed(event);
        }
    }

}
