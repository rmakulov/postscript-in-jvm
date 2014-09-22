package runtime.stack;


import procedures.Procedure;

import java.util.Stack;

public class CallStack extends PSStack<Procedure> {
    public CallStack() {
    }

    public CallStack(Stack<Procedure> stack) {
        super(stack);
    }

}
