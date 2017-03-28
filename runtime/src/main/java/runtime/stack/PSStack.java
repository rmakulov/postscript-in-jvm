package runtime.stack;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;


public class PSStack<T> implements Iterable<T> {
    private Stack<T> stack = new Stack<T>();

    public PSStack() {
    }

    public PSStack(Stack<T> stack) {
        this.stack = stack;
    }

    public T pop() {
        return stack.pop();
    }

    public void push(T t) {
        stack.push(t);
    }

    public void exch() {
        if (stack.size() < 2) {
            //todo throw exception
            return;
        }
        Iterator<T> iterator = stack.iterator();
        T top = stack.pop();
        T down = stack.pop();
        stack.push(top);
        stack.push(down);
    }

    public Iterator<T> iterator() {
        return inverseIterator();
    }

    private Iterator<T> inverseIterator() {
        return new Iterator<T>() {
            ListIterator<T> listIterator = stack.listIterator(stack.size());

            @Override
            public boolean hasNext() {
                return listIterator.hasPrevious();
            }

            @Override
            public T next() {
                return listIterator.previous();
            }

            @Override
            public void remove() {
            }
        };
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

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
