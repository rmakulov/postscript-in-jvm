package listeners;

import runtime.avl.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class Slots {
    private HashMap<Pair<String,String>,ArrayList<PSListener>>  links=new HashMap<Pair<String, String>, ArrayList<PSListener>>();
    private static Slots ourInstance = new Slots();

    public static Slots getInstance() {
        return ourInstance;
    }

    private Slots() {
    }

    public void addListener(String gObjectName,String signalType, PSListener listener){
        Pair<String,String> elementSignal = new Pair<String, String>(gObjectName,signalType);
        if(links.containsKey(elementSignal)){
            links.get(elementSignal).add(listener);
        }   else{
            ArrayList<PSListener> arrayList = new ArrayList<PSListener>();
            arrayList.add(listener);
            links.put(elementSignal, arrayList);
        }
    }

}
