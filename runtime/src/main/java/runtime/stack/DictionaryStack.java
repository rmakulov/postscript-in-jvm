package runtime.stack;

import psObjects.reference.Reference;

import java.util.Stack;


public class DictionaryStack extends PSStack<Reference> {
    public DictionaryStack() {
    }

    public DictionaryStack(Stack<Reference> stack) {
        super(stack);
    }

    @Override
    public DictionaryStack removeTop() {
        return new DictionaryStack(super.removeTopAndGetStack());
    }

    @Override
    public DictionaryStack push(Reference psDictionaryRef) {
        if(psDictionaryRef.isDictionaryRef()){
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
