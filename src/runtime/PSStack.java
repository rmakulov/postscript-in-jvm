package runtime;

import psObjects.PSObject;

import java.util.Iterator;
import java.util.Stack;


public class PSStack implements Iterable<PSObject>, Cloneable {
    private Stack<PSObject> stack = new Stack<PSObject>();

    public PSStack() {

    }

    public PSStack(Stack<PSObject> stack) {
        this.stack = stack;
    }

    public PSStack removeTop() {
        Iterator<PSObject> iterator = stack.iterator();
        Stack<PSObject> newStack = new Stack<PSObject>();
        while (iterator.hasNext()) {
            newStack.push(iterator.next());
        }
        newStack.pop();
        return new PSStack(newStack);
    }

    public PSStack push(PSObject psObject) {
        Iterator<PSObject> iterator = stack.iterator();
        Stack<PSObject> newStack = new Stack<PSObject>();
        while (iterator.hasNext()) {
            newStack.push(iterator.next());
        }
        newStack.push(psObject);
        return new PSStack(newStack);
    }

    public Iterator<PSObject> iterator() {
        return stack.iterator();
    }

    public boolean contains(PSObject psObject) {
        return stack.contains(psObject);
    }

    public String toString() {
        return stack.toString();
    }

    public PSStack exch() {
        if (stack.size() < 2) {
             //todo throw exception
            return null;
        }
        Iterator<PSObject> iterator = stack.iterator();
        Stack<PSObject> newStack = new Stack<PSObject>();
        while (iterator.hasNext()) {
            newStack.push(iterator.next());
        }
        PSObject top = newStack.pop();
        PSObject down = newStack.pop();
        newStack.push(top);
        newStack.push(down);
        return new PSStack(newStack);
    }

    public PSObject peek() {
        return stack.peek();
    }
}
