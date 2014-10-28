package runtime.stack;

import psObjects.PSObject;
import psObjects.Type;
import psObjects.values.composite.PSDictionary;

import java.util.Stack;


public class DictionaryStack extends PSStack<PSObject> {

    private int version = 0;
    private boolean sizeChanged = false;

    public DictionaryStack() {
    }

    public DictionaryStack(Stack<PSObject> stack) {
        super(stack);
    }

    @Override
    public void push(PSObject psDictionary) {
        if (psDictionary.getType() != Type.DICTIONARY) {
            //todo throw exception: this is not a dictionary reference
            return;
        }
        super.push(psDictionary);
        sizeChanged = true;
    }

    public int getVersion() {
        updateVersion();

        return version;
    }

    private void updateVersion() {
        if (!sizeChanged && version == PSDictionary.getLastVersion()) {
            //already updated
        } else {
            for (PSObject o : this) {
                version = Math.max(version, ((PSDictionary) o.getValue()).getVersion());
            }
            sizeChanged = false;
        }
    }

    @Override
    public PSObject pop() {
        sizeChanged = true;
        return super.pop();
    }

    @Override
    public void clear() {
        super.clear();
        PSDictionary.clearLastVersion();
    }
}
