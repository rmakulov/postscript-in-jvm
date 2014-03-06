package runtime.stack;

import psObjects.reference.Reference;

import java.util.Stack;

public class  OperandStack extends PSStack<Reference> {
    public OperandStack() {
    }

    public OperandStack(Stack<Reference> stack) {
        super(stack);
    }

    @Override
    public OperandStack removeTop() {
        return new OperandStack(super.removeTopAndGetStack());
    }

    @Override
    public OperandStack push(Reference psObjectRef) {
        return new OperandStack(super.pushAndGetStack(psObjectRef));
    }

    @Override
    public OperandStack exch() {
        return new OperandStack(super.exchAndGetStack());
    }
}
