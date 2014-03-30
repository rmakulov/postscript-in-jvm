package procedures;

import psObjects.PSObject;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class ForAllProcedure extends ArrayProcedure {
    private PSObject[] beforeArray;
    private int times;
    private int repeatedTimes = 0;

    public ForAllProcedure(PSObject[] beforeArray, PSObject arrayObj) {
        super("ForAll", arrayObj);
        this.beforeArray = beforeArray;
        times = beforeArray.length;
        nextIndex = -1;// for beforeArray elements
    }


    @Override
    public boolean hasNext() {
        return super.hasNext() && repeatedTimes < times;
    }

    @Override
    protected PSObject next() {
        if (times == 0) return null;
        PSObject nextObject;
        if (nextIndex == -1) {
            nextObject = beforeArray[repeatedTimes];
            nextIndex++;
        } else {
            nextObject = getArray()[nextIndex];
            if (nextIndex >= getArray().length - 1) {
                nextIndex = -1;
                repeatedTimes++;
            } else {
                nextIndex++;
            }
        }
        return nextObject;

    }

    @Override
    public boolean isExitable() {
        return true;
    }
}
