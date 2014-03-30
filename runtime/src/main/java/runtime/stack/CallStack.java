package runtime.stack;


import procedures.Procedure;

import java.util.Stack;

public class CallStack extends PSStack<Procedure> {
    public CallStack() {
    }

    public CallStack(Stack<Procedure> stack) {
        super(stack);
    }

    @Override
    public CallStack removeTop() {
        return new CallStack(super.removeTopAndGetStack());
    }

    // @Override
    public CallStack push(Procedure procedure) {
        return new CallStack(super.pushAndGetStack(procedure));
    }

    @Override
    public CallStack exch() {
        return new CallStack(super.exchAndGetStack());
    }
}
