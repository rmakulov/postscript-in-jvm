package operators.customs;

import psObjects.PSObject;
import psObjects.values.composite.PSDictionary;
import psObjects.values.composite.PSString;
import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;

/**
 * Created by User on 16/2/2015.
 */
public class EventOp extends Operator {

    public static final EventOp instance = new EventOp();

    protected EventOp() {
        super();
    }

    @Override
    public void interpret() {

        if (runtime.getOperandStackSize() < 3) {
            fail();
            return;
        }
        PSDictionary dict = ((PSDictionary) runtime.findValue("gelements").getValue());
//        System.out.println("before \n" + dict);

        new PSObject(new PSString("(/home/user/dev/IdeaProjects/postscript-in-jvm/graphicsEngine/basics/event.ps) (r) file run")).interpret(0);
        if (!runtime.isCompiling) {
            runtime.executeCallStack();
        }

//        System.out.println("after \n" + dict);

    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("event");
    }
}
