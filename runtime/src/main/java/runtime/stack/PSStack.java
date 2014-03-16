package runtime.stack;

import java.util.Iterator;
import java.util.Stack;


public class PSStack<T> implements Iterable<T> {
    private Stack<T> stack = new Stack<T>();

    public PSStack() {

    }

    public PSStack(Stack<T> stack) {
        this.stack = stack;
    }

    public PSStack removeTop() {
        return new PSStack<T>(removeTopAndGetStack());
    }

    public PSStack push(T t) {
        return new PSStack<T>(pushAndGetStack(t));
    }

    public PSStack exch() {
        return new PSStack<T>(exchAndGetStack());
    }


    protected Stack<T> removeTopAndGetStack() {
        Iterator<T> iterator = stack.iterator();
        Stack<T> newStack = new Stack<T>();
        while (iterator.hasNext()) {
            newStack.push(iterator.next());
        }
        newStack.pop();
        return newStack;
    }

    protected Stack<T> pushAndGetStack(T t) {
        Iterator<T> iterator = stack.iterator();
        Stack<T> newStack = new Stack<T>();
        while (iterator.hasNext()) {
            newStack.push(iterator.next());
        }
        newStack.push(t);
        return newStack;
    }

    protected Stack<T> exchAndGetStack() {
        if (stack.size() < 2) {
            //todo throw exception
            return null;
        }
        Iterator<T> iterator = stack.iterator();
        Stack<T> newStack = new Stack<T>();
        while (iterator.hasNext()) {
            newStack.push(iterator.next());
        }
        T top = newStack.pop();
        T down = newStack.pop();
        newStack.push(top);
        newStack.push(down);
        return newStack;
    }

    public Iterator<T> iterator() {
        return stack.iterator();
    }

    public boolean contains(T T) {
        return stack.contains(T);
    }

    public String toString() {
        return stack.toString();
    }


    public T peek() {
        if (stack.empty())
            return null;
        return stack.peek();
    }

    public void clear() {
        stack.clear();
    }

    public int size(){
        return stack.size();
    }
}
