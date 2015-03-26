package runtime;

import procedures.Procedure;

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
//        if (Runtime.getInstance().isCompiling) {
//            while (initProcedure.hasNext()) {
//                initProcedure.execNext();
//            }
//        } else {
        context.clearCallStack();
        context.pushToCallStack(initProcedure);
        context.executeCallStack();
//        }

    }

    public Context getContext() {
        return context;
    }
}
