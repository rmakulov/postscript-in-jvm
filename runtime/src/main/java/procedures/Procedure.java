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

    public boolean execNext() {
        if (!hasNext()) return true;
        PSObject nextObject = next();
        if (nextObject == null) {
            return true;
        }
        if (nextObject.getValue().equals(PSMark.CLOSE_CURLY_BRACE)) {
            procDepth--;
        }
        boolean isNotExited = nextObject.execute(procDepth);
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
}
