package runtime.stack;

import psObjects.PSObject;

import java.util.Stack;

public class OperandStack extends PSStack<PSObject> {
    public OperandStack() {
    }

    public OperandStack(Stack<PSObject> stack) {
        super(stack);
    }


}

