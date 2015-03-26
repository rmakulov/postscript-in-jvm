package operators.control;

import psObjects.values.simple.Operator;
import psObjects.values.simple.PSName;
import runtime.Context;

/**
 * Created by Дмитрий on 28.03.14.
 */
public class QuitOp extends Operator {
    public static final QuitOp instance = new QuitOp();

    protected QuitOp() {
        super();
    }

    @Override
    public void interpret(Context context) {
        System.out.println("Interpreter quited");
        System.exit(0);
    }

    @Override
    public PSName getDefaultKeyName() {
        return new PSName("quit");
    }
}
