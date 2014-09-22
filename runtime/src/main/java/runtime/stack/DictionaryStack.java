package runtime.stack;

import psObjects.PSObject;
import psObjects.Type;

import java.util.Stack;


public class DictionaryStack extends PSStack<PSObject> {
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
    }
}
