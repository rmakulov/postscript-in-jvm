package runtime.stack;


import psObjects.PSObject;
import psObjects.values.Value;

import java.util.Stack;

public class CallStack extends PSStack {
    public CallStack() {
    }

    public CallStack(Stack<Value> stack) {
         super(stack);
     }
 
     @Override
     public CallStack removeTop() {
         return new CallStack(super.removeTopAndGetStack());
     }
 
    // @Override
     public CallStack push(PSObject ref) {
         return new CallStack(super.pushAndGetStack(ref));
     }
 
     @Override
     public CallStack exch() {
         return new CallStack(super.exchAndGetStack());
     }
}
