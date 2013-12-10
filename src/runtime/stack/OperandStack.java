package runtime.stack;

import psObjects.PSObject;

import java.util.Stack;

public class OperandStack extends PSStack<PSObject> {
    public OperandStack() {
    }

    public OperandStack(Stack<PSObject> stack) {
        super(stack);
    }

    @Override
    public OperandStack removeTop() {
        return new OperandStack(super.removeTopAndGetStack());
    }

    @Override
    public OperandStack push(PSObject psObject) {
        return new OperandStack(super.pushAndGetStack(psObject));
    }

    @Override
    public OperandStack exch() {
        return new OperandStack(super.exchAndGetStack());
    }
}
