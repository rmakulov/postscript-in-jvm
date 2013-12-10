package runtime.stack;

import psObjects.composite.PSDictionary;

import java.util.Stack;


public class DictionaryStack extends PSStack<PSDictionary> {
    public DictionaryStack() {
    }

    public DictionaryStack(Stack<PSDictionary> stack) {
        super(stack);
    }

    @Override
    public DictionaryStack removeTop() {
        return new DictionaryStack(super.removeTopAndGetStack());
    }

    @Override
    public DictionaryStack push(PSDictionary psDictionary) {
        return new DictionaryStack(super.pushAndGetStack(psDictionary));
    }

    @Override
    public DictionaryStack exch() {
        return new DictionaryStack(super.exchAndGetStack());
    }
}
