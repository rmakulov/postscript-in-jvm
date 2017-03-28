package listeners;

import psObjects.values.composite.PSDictionary;
import runtime.events.EventType;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class PSEvent {
    protected PSDictionary dictionary;
    protected EventType type;

    public PSEvent(PSDictionary dictionary, EventType type) {
        this.dictionary = dictionary;
        this.type = type;
    }

    public PSDictionary getDictionaryValue(){
        return dictionary;
    }

}
