package psObjects.values.composite;

import psObjects.PSObject;
import psObjects.Type;

/**
 * Created by user on 06.04.14.
 */
public class PSGState extends CompositeValue {

    @Override
    public Type determineType() {
        return Type.GSTATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PSArray)) return false;
        return true;
        //todo what if o is array and not equal
    }

    @Override
    public String toStringView(PSObject obj) {
        return "--gstate--";
    }
}
