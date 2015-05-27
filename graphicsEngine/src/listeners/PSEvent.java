package listeners;

import psObjects.values.composite.PSDictionary;
import runtime.events.EventType;

/**
 * Created by Дмитрий on 24.05.15.
 */
public class PSEvent {
    private PSDictionary dictionary;
    private EventType type;

    public PSEvent(PSDictionary dictionary, EventType type) {
        this.dictionary = dictionary;
        this.type = type;
    }

}
