package procedures;

import psObjects.PSObject;
import psObjects.values.simple.PSMark;
import runtime.Context;
import runtime.Runtime;

/**
 * Created by Дмитрий on 28.03.14.
 */
public abstract class Procedure {
    private String name = "Unnamed";
    protected int procDepth = 0;
    protected Runtime runtime = Runtime.getInstance();
    Context context;

    protected Procedure(Context context, String name) {
        this.name = name;
        this.context = context;
    }

    protected Procedure() {
    }

    public abstract boolean hasNext();

    public boolean execNext() {
        if (!hasNext()) return true;
        PSObject nextObject = next();
        if (nextObject == null) {
            return true;
        }
        if (nextObject.getValue().equals(PSMark.CLOSE_CURLY_BRACE)) {
            procDepth--;
        }
        boolean isNotExited = nextObject.execute(context, procDepth);
        if (nextObject.getValue().equals(PSMark.OPEN_CURLY_BRACE)) {
            procDepth++;
        }
        return isNotExited;
    }

    protected abstract PSObject next();

    public void procBreak() {
        // override in stoppedProcedure
    }

    public void procTerminate() {
        // override in stoppedProcedure
    }

    @Override
    public String toString() {
        return "Procedure{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public boolean isExitable() {
        return false;
    }

    public void execute() {
        while (hasNext()) {
            execNext();
        }
    }
}
