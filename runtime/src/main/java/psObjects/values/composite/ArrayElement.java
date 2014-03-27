package psObjects.values.composite;

import psObjects.PSObject;

/**
 * Created by 1 on 27.03.2014.
 */
public class ArrayElement {
    private PSObject elementObject;

    public void setElementObject(PSObject elementObject) {
        this.elementObject = elementObject;
    }

    public PSObject getElementObject() {

        return elementObject;
    }

    public ArrayElement(PSObject elementObject) {

        this.elementObject = elementObject;
    }
}
