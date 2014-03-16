package runtime.stack;

import psObjects.PSObject;
import psObjects.values.Value;
import runtime.graphics.save.GSave;

import java.util.Stack;

/**
 * Created by 1 on 10.12.13.
 */
public class GraphicStack extends PSStack<GSave> {
    public GraphicStack() {
    }

    public GraphicStack(Stack<GSave> stack) {
         super(stack);
     }
 
     @Override
     public GraphicStack removeTop() {
         return new GraphicStack(super.removeTopAndGetStack());
     }
 
     //@Override
     public GraphicStack push(GSave gsave) {
         return new GraphicStack(super.pushAndGetStack(gsave));
     }
 
     @Override
     public GraphicStack exch() {
         return new GraphicStack(super.exchAndGetStack());
     }
}
