package runtime.stack;


import psObjects.PSObject;

import java.util.Stack;

public class CallStack extends PSStack {
    public CallStack() {
    }

    public CallStack(Stack<PSObject> stack) {
         super(stack);
     }
 
     @Override
     public CallStack removeTop() {
         return new CallStack(super.removeTopAndGetStack());
     }
 
     @Override
     public CallStack push(PSObject psObject) {
         return new CallStack(super.pushAndGetStack(psObject));
     }
 
     @Override
     public CallStack exch() {
         return new CallStack(super.exchAndGetStack());
     }
}
