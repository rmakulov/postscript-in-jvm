package listeners;

import psObjects.values.composite.PSDictionary;
import runtime.events.EventType;

/**
 * Created by user on 5/28/15.
 */
public class PSUpdateValueEvent extends PSEvent {
    public PSUpdateValueEvent(PSDictionary dictionary, EventType type) {
        super(dictionary, type);
    }

    public PSDictionary getDictionaryValue(){
        return dictionary;
    }
}
