package runtime;

import procedures.Procedure;
import procedures.StringProcedure;
import psObjects.PSObject;
import psObjects.values.composite.PSString;

import java.io.UnsupportedEncodingException;

/**
 * Created by User on 21/3/2015.
 */
public class PSThread extends Thread {
    private Context context;
    private Procedure initProcedure;

    public PSThread(Context context, Procedure procedure) {
        this.context = context;
        this.initProcedure = procedure;
    }

    @Override
    public void run() {

        super.run();
        Runtime runtime = Runtime.getInstance();
        runtime.addContext(context);
//        if (Runtime.getInstance().isCompiling) {
//            while (initProcedure.hasNext()) {
//                initProcedure.execNext();
//            }
//        } else {
        //context.clearCallStack();
        context.pushToCallStack(initProcedure);
        try {
            context.pushToCallStack(new StringProcedure(context, new PSObject(new PSString("(graphicsEngine/basics/glib.ps) (r) file run"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!runtime.isCompiling) {
            context.executeCallStack();
        }
        runtime.removeContext(context);
//        }
//        Runtime.getInstance().repaintMainContext();
    }

    public Context getContext() {
        return context;
    }
}
