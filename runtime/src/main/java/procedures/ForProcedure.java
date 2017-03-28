package procedures;

import psObjects.PSObject;
import psObjects.values.simple.numbers.PSReal;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class ForProcedure extends ArrayProcedure {
    private double initial;
    private double increment;
    private double limit;
    private int repeatedTimes = 0;

    public ForProcedure(Context context, double initial, double increment, double limit, PSObject arrayObj) {
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
            nextObject = new PSObject(new PSReal(initial + repeatedTimes * increment));
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

    @Override
    public String toString() {
        return "ForProcedure{" +
                "repeatedTimes=" + repeatedTimes +
                ", next Index = " + nextIndex +
                '}';
    }
}
