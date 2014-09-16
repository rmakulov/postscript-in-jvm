package procedures;

import psObjects.PSObject;
import psObjects.values.simple.PSMark;
import runtime.Runtime;

/**
 * Created by Дмитрий on 28.03.14.
 */
public abstract class Procedure {
    private String name = "Unnamed";
    protected int procDepth = 0;
    protected Runtime runtime = Runtime.getInstance();

    protected Procedure(String name) {
        this.name = name;
    }

    protected Procedure() {
    }

    public abstract boolean hasNext();

    public void execNext() {
        if (!hasNext()) return;
        PSObject nextObject = next();
        if (nextObject == null) {
            return;
        }
        if (nextObject.getValue().equals(PSMark.CLOSE_CURLY_BRACE)) {
            procDepth--;
        }
        nextObject.execute(procDepth);
        if (nextObject.getValue().equals(PSMark.OPEN_CURLY_BRACE)) {
            procDepth++;
        }

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
}
