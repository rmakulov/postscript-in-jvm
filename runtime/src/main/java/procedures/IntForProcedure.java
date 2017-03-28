package procedures;

import psObjects.PSObject;
import psObjects.values.simple.numbers.PSInteger;
import runtime.Context;

/**
 * Created by User on 15/9/2014.
 */
public class IntForProcedure extends ArrayProcedure {
    private int initial;
    private int increment;
    private double limit;
    private int repeatedTimes = 0;

    public IntForProcedure(Context context, int initial, int increment, double limit, PSObject arrayObj) {
        super(context, "For", arrayObj);
        this.initial = initial;
        this.increment = increment;
        this.limit = limit;
        this.nextIndex = -1; //for PSReal write before iteration
        //System.out.println("For "+initial+" "+increment+" "+limit);
    }

    @Override
    public boolean hasNext() {
        double v = initial + repeatedTimes * increment;
        return super.hasNext() && (increment > 0 ? (v <= limit) : (v >= limit));
    }

    @Override
    protected PSObject next() {
        PSObject nextObject;
        if (nextIndex == -1) {
            nextObject = new PSObject(new PSInteger(initial + repeatedTimes * increment));
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
