package procedures;

import psObjects.PSObject;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class LoopProcedure extends ArrayProcedure {
    public LoopProcedure(Context context, PSObject arrayObj) {
        super(context, "Loop", arrayObj);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    protected PSObject next() {
        int length = getArray().length;
        if (length == 0) return null;
        PSObject nextObject = getArray()[nextIndex];
        if (nextIndex == length - 1) {
            nextIndex = 0;
        } else {
            nextIndex++;
        }
        return nextObject;
    }

    @Override
    public boolean isExitable() {
        return true;
    }
}
