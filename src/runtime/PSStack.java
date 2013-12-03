package runtime;

import psObjects.PSObject;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: 1
 * Date: 03.12.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class PSStack implements Iterable<PSObject>, Cloneable{
    private Stack<PSObject> stack = new Stack<PSObject>();

    public PSStack() {

    }

    public PSStack(Stack<PSObject> stack) {
        this.stack=stack;
    }

    public PSObject pop(){
        return stack.pop();
    }

    public void push(PSObject psObject){
        stack.push(psObject);
    }

    public PSStack clone(){
        Iterator<PSObject> iterator = stack.iterator();
        PSStack psStack = new PSStack();
        while(iterator.hasNext())       {
            psStack.push(iterator.next().clone());
        }
        return psStack;
    }


    public Iterator<PSObject> iterator(){
        return stack.iterator();
    }

    public boolean contains(PSObject psObject) {
        return stack.contains(psObject);
    }

    public String toString(){
        return stack.toString();
    }

    public boolean exch(){
        if (stack.size()<2) return false;
        PSObject top = stack.pop();
        PSObject down = stack.pop();
        stack.push(top);
        stack.push(down);
        return true;
    }
}
