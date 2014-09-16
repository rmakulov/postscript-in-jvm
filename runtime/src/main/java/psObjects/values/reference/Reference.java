package psObjects.values.reference;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.Value;
import psObjects.values.composite.CompositeValue;
import psObjects.values.composite.PSString;


public abstract class Reference extends Value {
    @Override
    public abstract Value getValue();

    public abstract Type setCompositeValue(CompositeValue obj);


    protected int psObjectCompareTo(Value v) throws Exception {
        Value thisValue = getValue();
        Value refValue = v.getValue();
        if (thisValue instanceof PSString && refValue instanceof PSString) {
            PSString thisString = (PSString) thisValue;
            PSString refString = (PSString) refValue;
            return thisString.psCompareTo(refString);
        }
        throw new Exception();
    }

    protected boolean psObjectEquals(Object o) throws Exception {
        if (!(o instanceof Reference)) return false;
        Reference r = (Reference) o;
        Value thisValue = getValue();
        Value refValue = r.getValue();
        if (thisValue instanceof PSString && refValue instanceof PSString) {
            PSString thisString = (PSString) thisValue;
            PSString refString = (PSString) refValue;
            return thisString.equals(refString);
        }
        throw new Exception();
    }

    @Override
    public Type determineType() {
        return getValue().determineType();
    }

    @Override
    public boolean interpret(PSObject obj) {
        return getValue().interpret(obj);
    }

    @Override
    public void compile(PSObject obj) {
        getValue().compile(obj);
    }
}
