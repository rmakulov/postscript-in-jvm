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
    public DictionaryStack removeTop() {
        return new DictionaryStack(super.removeTopAndGetStack());
    }

    @Override
    public DictionaryStack push(PSObject psDictionaryRef) {
        if(psDictionaryRef.getType()!= Type.DICTIONARY){
            //todo throw exception: this is not a dictionary reference
            return this;
        }
        return new DictionaryStack(super.pushAndGetStack(psDictionaryRef));
    }

    @Override
    public DictionaryStack exch() {
        return new DictionaryStack(super.exchAndGetStack());
    }
}
