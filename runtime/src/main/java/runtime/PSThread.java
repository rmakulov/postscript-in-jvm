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
        Runtime runtime = Runtime.getInstance();
        runtime.addContext(context);
//        if (Runtime.getInstance().isCompiling) {
//            while (initProcedure.hasNext()) {
//                initProcedure.execNext();
//            }
//        } else {
        //context.clearCallStack();
        if (!runtime.isCompiling) {
            context.pushToCallStack(initProcedure);
//            try {
//                StringProcedure glibProcedure = new StringProcedure(context, new PSObject(new PSString("(graphicsEngine/basics/glib.ps) (r) file run")));
//                context.pushToCallStack(glibProcedure);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            context.executeCallStack();
        } else {
            initProcedure.execute();
        }
        runtime.removeContext(context);
//        }
//        Runtime.getInstance().repaintMainContext();
    }

    public Context getContext() {
        return context;
    }
}
