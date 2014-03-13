package runtime.stack;

import psObjects.PSObject;
import psObjects.reference.Reference;

import java.util.Stack;

/**
 * Created by 1 on 10.12.13.
 */
public class GraphicStack extends PSStack {
    public GraphicStack() {
    }

    public GraphicStack(Stack<PSObject> stack) {
         super(stack);
     }
 
     @Override
     public GraphicStack removeTop() {
         return new GraphicStack(super.removeTopAndGetStack());
     }
 
     @Override
     public GraphicStack push(Reference ref) {
         return new GraphicStack(super.pushAndGetStack(ref));
     }
 
     @Override
     public GraphicStack exch() {
         return new GraphicStack(super.exchAndGetStack());
     }
}
