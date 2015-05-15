package runtime;

import procedures.ArrayProcedure;
import procedures.Procedure;
import psObjects.PSObject;

/**
 * Created by User on 21/3/2015.
 */
public class PSThread extends Thread {
    private PSObject procedure;
    private Context context;
    private Procedure initProcedure;

    public  PSThread(Context context, Procedure procedure) {
        this.context = context;
        this.initProcedure = procedure;
    }

    public PSThread(Context context, PSObject procedure) {
        this.context = context;
        this.procedure = procedure;
    }

    @Override
    public void run() {

        super.run();
        Runtime runtime = Runtime.getInstance();
        // runtime.addContext(context);
//        if (Runtime.getInstance().isCompiling) {
//            while (initProcedure.hasNext()) {
//                initProcedure.execNext();
//            }
//        } else {
        //context.clearCallStack();
//        if (initProcedure != null) {
//            if (!runtime.isCompiling) {
//                context.pushToCallStack(initProcedure);
//                context.executeCallStack();
//            } else {
//                initProcedure.execute();
//            }
//
//        } else if (!runtime.isCompiling) {
//            Procedure initProcedure = new ArrayProcedure(context, "Thread", procedure);
//            context.pushToCallStack(initProcedure);
//            context.executeCallStack();
//        } else {
//            procedure.execute(context, 0);
//        }

        if (!runtime.isCompiling) {
            if (initProcedure == null) {
                initProcedure = new ArrayProcedure(context, "Thread", procedure);
            }
            context.pushToCallStack(initProcedure);
            context.executeCallStack();
        } else {
            if (initProcedure == null) {
                procedure.interpret(context, 0);
            } else {
                initProcedure.execute();
            }
        }
        runtime.removeContext(context);
//        }
//        Runtime.getInstance().repaintMainContext();
    }

    public Context getContext() {
        return context;
    }
}
