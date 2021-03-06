package procedures;

import psObjects.PSObject;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class RepeatProcedure extends ArrayProcedure {
    private int times;
    private int repeatedTimes = 0;

    public RepeatProcedure(Context context, int times, PSObject arrayObj) {
        super(context, "Repeat", arrayObj);
        this.times = times;
    }

    @Override
    public boolean hasNext() {
        return super.hasNext() && repeatedTimes < times;
    }

    @Override
    protected PSObject next() {
        if (getArray()[nextIndex] == null) return null;
        PSObject nextObject = getArray()[nextIndex];
        if (nextIndex == getArray().length - 1) {
            nextIndex = 0;
            repeatedTimes++;
        } else {
            nextIndex++;
        }
        return nextObject;
    }

    @Override
    public boolean isExitable() {
        return true;
    }

    @Override
    public String toString() {
        return "RepeatProcedure{" +
                "repeatedTimes=" + repeatedTimes +
                ", next Index = " + nextIndex +
                '}';
    }
}
