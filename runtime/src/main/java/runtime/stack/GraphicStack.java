package runtime.stack;

import psObjects.values.composite.Snapshot;
import runtime.graphics.GState;

import java.util.Stack;

/**
 * Created by 1 on 10.12.13.
 */
public class GraphicStack extends PSStack<GState> {
    public GraphicStack() {
    }

    public GraphicStack(Stack<GState> stack) {
         super(stack);
     }

    public void init() {
        push(new GState());

    }

    public void clear() {
        super.clear();
        init();
    }

    // for restore
    public void setGState(Snapshot snapshot) {
        GState savedGState = snapshot.getGState();
        while (size() > 0 && peek() != savedGState) {
            pop();
        }
        if (isEmpty()) {
            try {
                throw new Exception("Gsave is not found!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
